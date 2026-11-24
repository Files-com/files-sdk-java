package com.files;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.files.net.HttpTransport;
import com.files.net.TransferProgress;
import com.files.net.UploadOptions;
import com.files.net.UploadResult;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.OptionalInt;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Test;

// Transfer tests that wait out real time: a download URL's expiry, and Files.com's completion of an upload in
// the background, asked about one, two, then three seconds apart. They are a class of their own so that the
// build can run them in one JVM while FilesTransferTest runs in another.
public class FilesTransferTimingTest extends FilesTransferTestBase {
  @Test(timeout = 120000)
  public void aRangeWhoseUrlExpiresWhileItWaitsForTheOnlyPooledConnectionIsAskedForAgainWithARenewedUrl() throws Exception {
    // One pooled connection for the API and storage together, and one part at a time. The download's
    // URL is valid for 40 seconds, so it is not within the 30 seconds before expiry in which it would
    // be renewed before a part is asked for. Bytes 5000-5999 are answered 503 at first; while that
    // answer holds the connection, an upload's begin_upload is seen waiting for it. The begin_upload
    // gets it as the part waits to retry, and Files.com holds it while the retry is seen waiting for the
    // connection behind it and until the URL has expired; then plans its parts as usual. The retry
    // must be refused before any of it is written, its connection given back before the URL is renewed,
    // which needs that one connection, and the bytes not yet written asked for again, of the same
    // version, with the renewed URL.
    //
    // The file is 6,000 bytes on storage that serves ranges of one version, fetched in parts of 1,000
    // bytes, so 5000-5999 is its last part: once that part is answered 503 nothing else is left to ask
    // for, and its retry is the only request that can wait behind the holder.
    byte[] contents = bytes(6000, 56);
    files.contents = contents;
    files.describedSize = (long) contents.length;
    files.ranges = true;
    files.etag = "\"v1\"";
    Instant signed = Instant.now().truncatedTo(ChronoUnit.SECONDS);
    Instant expires = signed.plusSeconds(40);
    files.downloadQuery = signedQuery(signed, 40);
    CountDownLatch holding = new CountDownLatch(1);
    CountDownLatch release = new CountDownLatch(1);
    AtomicBoolean holderWaited = new AtomicBoolean();
    AtomicReference<Future<UploadResult>> holder = new AtomicReference<>();
    ExecutorService callers = Executors.newFixedThreadPool(2);
    AtomicInteger heldForHolder = new AtomicInteger();
    // Once the first answer for 5000-5999 is on its way, a request for the one connection waits until the
    // holder's begin_upload has it at Files.com, so the retry queues behind the holder rather than racing it
    // for the connection the 503 gives back. The retry's own wait is still seen in the pool's statistics.
    PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager() {
      @Override
      public org.apache.http.conn.ConnectionRequest requestConnection(org.apache.http.conn.routing.HttpRoute route, Object state) {
        if (holderWaited.get() && holding.getCount() > 0) {
          heldForHolder.incrementAndGet();
          boolean holderHasIt;
          try {
            holderHasIt = holding.await(30, TimeUnit.SECONDS);
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError("interrupted while a request waited for the holder's begin_upload to have the connection", e);
          }
          if (!holderHasIt) {
            throw new AssertionError("the holder's begin_upload did not have the connection within 30 seconds of a request waiting for it");
          }
        }
        return super.requestConnection(route, state);
      }
    };
    pool.setMaxTotal(1);
    pool.setDefaultMaxPerRoute(1);
    // This request must keep waiting through the URL's expiry. The default ten-second acquisition
    // timeout could retry and renew it before the final-send guard gets the connection.
    try (FilesClient shared = FilesClient.builder().apiKey("transfer-key").apiRoot(files.root()).retryDelay(Duration.ofSeconds(1))
             .connectionAcquisitionTimeout(Duration.ofSeconds(60)).transport(HttpTransport.builder().connectionManager(pool)).build()) {
      final Path held = file("holder.bin", bytes(5, 57));
      files.beginAnswer = request -> {
        if (request.rawPath.endsWith("holder.bin") && release.getCount() > 0) {
          holding.countDown();
          release.await(60, TimeUnit.SECONDS);
        }
        return null;
      };
      files.rangeAnswer = (start, attempt) -> {
        if (start == 5000 && attempt == 1) {
          holder.set(callers.submit(() -> shared.transfers().upload(held, "holder.bin")));
          // This request holds the one connection: the holder's begin_upload is seen waiting for it.
          holderWaited.set(eventually(() -> pool.getTotalStats().getPending() == 1, Duration.ofSeconds(10)));
          return FakeFiles.Answer.status(503, "");
        }
        return null;
      };
      Path destination = file("waited.bin", "old".getBytes(StandardCharsets.UTF_8));
      final Future<com.files.net.DownloadResult> download = callers.submit(() -> shared.transfers().download("remote/waited.bin", destination,
          inParts().maxConcurrency(1).build()));
      assertTrue("the holder's begin_upload has the connection", holding.await(30, TimeUnit.SECONDS));
      assertTrue("it had waited for the connection the first answer held", holderWaited.get());
      // One snapshot of the pool shows the retry waiting, taken before the URL comes within the 30 seconds
      // before its expiry in which a part renews it before asking: until then the part asks storage with
      // it, so what waits is the retry's storage request, not a renewal of the URL. A sighting only once
      // the URL could have been renewed would not tell them apart, and fails.
      Instant renewableFrom = expires.minusSeconds(30);
      AtomicReference<org.apache.http.pool.PoolStats> queued = new AtomicReference<>();
      AtomicReference<Instant> queuedAt = new AtomicReference<>();
      final boolean retryQueued = eventually(() -> {
        org.apache.http.pool.PoolStats stats = pool.getTotalStats();
        queuedAt.set(Instant.now());
        queued.set(stats);
        return stats.getLeased() == 1 && stats.getPending() == 1;
      }, Duration.ofSeconds(20));
      if (!retryQueued) {
        System.err.println("POOL_QUEUE_DIAGNOSTIC downloadDone=" + download.isDone()
            + " cancelled=" + download.isCancelled() + " stats=" + pool.getTotalStats());
        System.err.println("POOL_QUEUE_DIAGNOSTIC requestsHeldForHolder=" + heldForHolder.get());
        if (download.isDone()) {
          try {
            download.get(0, TimeUnit.SECONDS);
            System.err.println("POOL_QUEUE_DIAGNOSTIC download completed successfully");
          } catch (Exception diagnosticFailure) {
            diagnosticFailure.printStackTrace(System.err);
          }
        }
        int printed = 0;
        for (java.util.Map.Entry<Thread, StackTraceElement[]> entry : Thread.getAllStackTraces().entrySet()) {
          String name = entry.getKey().getName();
          if ((name.startsWith("files-sdk") || name.startsWith("pool-")) && printed++ < 20) {
            System.err.println("POOL_QUEUE_DIAGNOSTIC thread=" + name + " state=" + entry.getKey().getState());
            StackTraceElement[] frames = entry.getValue();
            for (int frame = 0; frame < Math.min(12, frames.length); frame++) {
              System.err.println("  " + frames[frame]);
            }
          }
        }
      }
      assertTrue("the retry waits for the one connection: " + pool.getTotalStats(), retryQueued);
      assertEquals(1, queued.get().getMax());
      assertTrue("seen waiting at " + queuedAt.get() + ", before its URL could be renewed from " + renewableFrom,
          queuedAt.get().isBefore(renewableFrom));
      final int asked = files.rangeRequests.size();
      int described = files.describes.size();
      assertEquals("the retry has not reached storage", 1, files.rangeRequests.stream().filter(r -> r.part == 5000).count());
      assertTrue(eventually(() -> Instant.now().isAfter(expires), Duration.ofSeconds(60)));
      assertEquals("still waiting, with a URL that has expired", 1, pool.getTotalStats().getPending());
      assertEquals("not renewed while it waited", described, files.describes.size());
      // Files.com's next URL for the file is valid for an hour.
      files.downloadQuery = signedQuery(Instant.now().truncatedTo(ChronoUnit.SECONDS), 3600) + "&renewed=1";
      int describedBefore = files.describes.size();
      release.countDown();

      com.files.net.DownloadResult result = download.get(30, TimeUnit.SECONDS);
      assertTrue(result.isVersionBound());
      assertArrayEquals(contents, Files.readAllBytes(destination));
      assertTrue("renewed after the refusal", files.describes.size() > describedBefore);
      List<FakeFiles.Received> afterWait = files.rangeRequests.subList(asked, files.rangeRequests.size());
      assertFalse(afterWait.isEmpty());
      assertEquals("the retry is the only request after the wait: " + afterWait.size(), 1, afterWait.size());
      for (FakeFiles.Received range : afterWait) {
        assertTrue("only the renewed URL reached storage after the wait: " + range.rawQuery, range.rawQuery.endsWith("&renewed=1"));
      }
      FakeFiles.Received retried = afterWait.get(0);
      assertEquals("the bytes not yet written", "bytes=5000-5999", retried.header("Range"));
      assertEquals("of the same version", "\"v1\"", retried.header("If-Match"));
      assertEquals(2, files.rangeRequests.stream().filter(r -> r.part == 5000).count());
      assertEquals(5, holder.get().get(30, TimeUnit.SECONDS).getSize());
      assertEquals("every connection was given back", 0, pool.getTotalStats().getLeased());
      assertEquals(0, pool.getTotalStats().getPending());
    } finally {
      release.countDown();
      callers.shutdownNow();
      pool.shutdown();
    }
  }

  @Test(timeout = 40000)
  public void anUploadFilesComCompletesInTheBackgroundIsAskedAboutOneTwoThenThreeSecondsApartWithTheSameRequestUntilItAnswersWithTheFile()
      throws Exception {
    // Pending without a percentage, at 42, with values that are no percentage, then at 100, which is still
    // pending; then the file.
    List<String> percentages = Arrays.asList(null, "42", "\"60\"", "101", "100");
    List<Long> receivedAt = new CopyOnWriteArrayList<>();
    files.finish = request -> {
      receivedAt.add(System.nanoTime());
      int answered = files.finishes.size() - 1;
      return answered < percentages.size() ? FakeFiles.Answer.pending(request, percentages.get(answered)) : null;
    };
    List<TransferProgress> progress = new CopyOnWriteArrayList<>();
    HashMap<String, Object> requestOptions = new HashMap<>();
    requestOptions.put("api_key", "upload-key");

    UploadResult result = client.transfers().upload(new ByteArrayInputStream(bytes(8, 40)), "background.bin",
        UploadOptions.builder().requestOptions(requestOptions).progressListener(progress::add).build());

    assertEquals("background.bin", result.getFile().getPath());
    assertEquals(8, result.getFile().getSize().longValue());
    assertEquals(8, result.getSize());
    assertEquals("asked until the file came", percentages.size() + 1, files.finishes.size());
    FakeFiles.Received first = files.finishes.get(0);
    assertTrue(first.json().path("async_finalize").booleanValue());
    for (int i = 1; i < files.finishes.size(); i++) {
      FakeFiles.Received again = files.finishes.get(i);
      assertEquals("the same request", first.rawPath, again.rawPath);
      assertEquals(first.json(), again.json());
      for (String header : Arrays.asList("X-FilesAPI-Key", "X-Files-Workspace-Id", "User-Agent")) {
        assertEquals(header, first.header(header), again.header(header));
      }
      // A pending 100, or a percentage that is none, is still pending, and the waits grow to three seconds.
      long wait = 1000L * Math.min(i, 3);
      long waited = TimeUnit.NANOSECONDS.toMillis(receivedAt.get(i) - receivedAt.get(i - 1));
      assertTrue("asked again " + waited + " ms after answer " + i + ", not " + wait, waited >= wait - 50 && waited < wait + 900);
    }
    assertEquals("the upload's own credentials", "upload-key", first.header("X-FilesAPI-Key"));

    List<TransferProgress> finalizing = progress.stream().filter(TransferProgress::isFinalizing).collect(Collectors.toList());
    assertEquals("one report for each pending answer", Arrays.asList(OptionalInt.empty(), OptionalInt.of(42), OptionalInt.empty(),
        OptionalInt.empty(), OptionalInt.of(100)), finalizing.stream().map(TransferProgress::getFinalizationProgress).collect(Collectors.toList()));
    assertEquals("finalizing reports follow every byte report", progress.subList(progress.size() - finalizing.size(), progress.size()), finalizing);
    TransferProgress bytesDone = progress.get(progress.size() - finalizing.size() - 1);
    assertFalse(bytesDone.getFinalizationProgress().isPresent());
    for (TransferProgress report : finalizing) {
      assertEquals(bytesDone.getTotalBytes(), report.getTotalBytes());
      assertEquals(8, report.getTransferredBytes());
      assertEquals(bytesDone.getTransferredBytes(), report.getTransferredBytes());
      assertEquals(bytesDone.getSentBytes(), report.getSentBytes());
      assertEquals(bytesDone.getCompletedParts(), report.getCompletedParts());
      assertEquals(bytesDone.getRetries(), report.getRetries());
    }
    assertFalse("the result's progress is as of the parts' acceptance", result.getFinalProgress().isFinalizing());
    assertEquals(8, result.getFinalProgress().getTransferredBytes());

    // Another upload's completion starts again from one second.
    receivedAt.clear();
    int before = files.finishes.size();
    files.finish = request -> {
      receivedAt.add(System.nanoTime());
      return files.finishes.size() - before == 1 ? FakeFiles.Answer.pending(request, null) : null;
    };
    client.transfers().upload(new ByteArrayInputStream(bytes(8, 41)), "next.bin", UploadOptions.builder().requestOptions(requestOptions).build());
    assertEquals(before + 2, files.finishes.size());
    long next = TimeUnit.NANOSECONDS.toMillis(receivedAt.get(1) - receivedAt.get(0));
    assertTrue("the next upload asked again " + next + " ms after its first answer", next >= 950 && next < 1900);
  }
}
