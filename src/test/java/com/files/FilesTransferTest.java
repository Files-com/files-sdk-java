package com.files;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.files.exceptions.ApiErrorException;
import com.files.exceptions.OperationCancelledException;
import com.files.exceptions.UnsupportedUploadException;
import com.files.exceptions.UploadCompletionUnknownException;
import com.files.models.File;
import com.files.models.FileUploadPart;
import com.files.net.ConcurrencyMode;
import com.files.net.DownloadOptions;
import com.files.net.HttpTransport;
import com.files.net.PartSizeSource;
import com.files.net.PartSizing;
import com.files.net.TransferConcurrency;
import com.files.net.TransferLimiter;
import com.files.net.TransferProgress;
import com.files.net.TransferProgressListener;
import com.files.net.TransferScope;
import com.files.net.TransferScopeTestAccess;
import com.files.net.UploadDestination;
import com.files.net.UploadOptions;
import com.files.net.UploadResult;
import com.files.net.UploadStartup;
import com.files.net.UploadedPart;
import com.files.net.Win32TestAccess;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SecureDirectoryStream;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryFlag;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.PosixFilePermissions;
import java.nio.file.attribute.UserPrincipal;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class FilesTransferTest {
  @Rule
  public TemporaryFolder folder = new TemporaryFolder();

  private FakeFiles files;
  private FilesClient client;

  @Before
  public void setUp() throws IOException {
    files = new FakeFiles();
    client = FilesClient.builder()
        .apiKey("transfer-key")
        .workspaceId(0L)
        .apiRoot(files.root())
        .retryDelay(Duration.ofMillis(1))
        .build();
  }

  @After
  public void tearDown() {
    client.close();
    files.close();
  }

  @Test(timeout = 20000)
  public void aFileIsSentInTheServersPartsAndCompletedWithItsSizeAndETags() throws Exception {
    byte[] contents = bytes(12, 1);
    Path source = file("report.bin", contents);
    Instant modified = Instant.parse("2024-03-04T05:06:07.890Z");
    Files.setLastModifiedTime(source, java.nio.file.attribute.FileTime.from(modified));

    // Four parts at once, so that the parts the file still needs are asked for together.
    UploadResult result = client.transfers().upload(source, "dir/report.bin",
        UploadOptions.builder().concurrencyMode(ConcurrencyMode.STATIC).build());

    assertEquals(12, result.getSize());
    assertEquals("dir/report.bin", result.getFile().getPath());
    assertEquals(Arrays.asList(5L, 5L, 2L), result.getParts().stream().map(p -> p.getLength()).collect(Collectors.toList()));

    // Part 1 comes with the upload; parts 2 and 3 are asked for together, as the file needs two more.
    assertEquals(2, files.begins.size());
    JsonNode begin = files.begins.get(0).json();
    assertEquals(12, begin.get("size").asLong());
    assertFalse("mkdir_parents is sent only when chosen", begin.has("mkdir_parents"));
    JsonNode next = files.begins.get(1).json();
    assertEquals("put-1", next.get("ref").asText());
    assertEquals(2, next.get("part").asLong());
    assertEquals(2, next.get("parts").asLong());
    for (FakeFiles.Received request : files.begins) {
      assertEquals("transfer-key", request.header("X-FilesAPI-Key"));
      assertEquals("0", request.header("X-Files-Workspace-Id"));
      assertEquals("/api/rest/v1/file_actions/begin_upload/dir%2Freport.bin", request.rawPath);
    }

    assertEquals(3, files.parts.size());
    for (long number = 1; number <= 3; number++) {
      FakeFiles.Received part = files.partsNumbered(number).get(0);
      assertArrayEquals(Arrays.copyOfRange(contents, (int) (number - 1) * 5, (int) Math.min(12, number * 5)), part.body);
      assertEquals("PUT", part.method);
      assertEquals(FakeFiles.SIGNATURE, part.rawQuery);
      assertNull("storage requests carry no Files.com credentials", part.header("X-FilesAPI-Key"));
      assertNull(part.header("X-FilesAPI-Auth"));
      assertNull(part.header("X-Files-Workspace-Id"));
      assertNull("no content type the server did not ask for", part.header("Content-Type"));
      assertTrue(part.header("User-Agent"), part.header("User-Agent").startsWith("Files.com Java SDK v"));
    }

    assertEquals(1, files.finishes.size());
    JsonNode end = files.finishes.get(0).json();
    assertEquals("end", end.get("action").asText());
    assertEquals("put-1", end.get("ref").asText());
    assertEquals(12, end.get("size").asLong());
    assertEquals(modified.truncatedTo(ChronoUnit.SECONDS).toString(), end.get("provided_mtime").asText());
    assertEquals("[{\"etag\":\"\\\"etag-1-1\\\"\",\"part\":1},{\"etag\":\"\\\"etag-2-1\\\"\",\"part\":2},{\"etag\":\"\\\"etag-3-1\\\"\",\"part\":3}]",
        end.get("etags").toString());
    assertEquals("transfer-key", files.finishes.get(0).header("X-FilesAPI-Key"));
  }

  @Test(timeout = 20000)
  public void anEmptyFileIsOneEmptyPart() throws Exception {
    UploadResult result = client.transfers().upload(file("empty.bin", new byte[0]), "empty.bin");

    assertEquals(0, result.getSize());
    assertEquals(1, files.parts.size());
    assertEquals(0, files.parts.get(0).body.length);
    assertEquals("0", files.parts.get(0).header("Content-Length"));
    assertEquals(0, files.finishes.get(0).json().get("size").asLong());
  }

  @Test(timeout = 20000)
  public void aTokenInTheRequestOptionsAlsoCancelsTheUpload() throws Exception {
    CancellationToken token = new CancellationToken();
    token.cancel();
    HashMap<String, Object> requestOptions = new HashMap<>();
    requestOptions.put("cancellation", token);
    try {
      client.transfers().upload(file("options.bin", bytes(3, 31)), "options.bin", UploadOptions.builder().requestOptions(requestOptions).build());
      fail("Expected the upload to be cancelled");
    } catch (OperationCancelledException e) {
      assertEquals(OperationCancelledException.Reason.CANCELLED, e.getReason());
    }
    assertEquals(0, files.begins.size());
  }

  @Test(timeout = 20000)
  public void aStreamIsReadInFullPartsDespiteShortReadsAndIsNeverClosed() throws Exception {
    files.partsize = 7;
    byte[] contents = bytes(30, 2);
    TrickleStream stream = new TrickleStream(contents);

    UploadResult result = client.transfers().upload(stream, 23, "stream.bin", UploadOptions.builder().mkdirParents(true).build());

    assertEquals(23, result.getSize());
    assertEquals(Arrays.asList(7L, 7L, 7L, 2L), result.getParts().stream().map(p -> p.getLength()).collect(Collectors.toList()));
    ByteArrayInputStreamCollector sent = new ByteArrayInputStreamCollector();
    for (long number = 1; number <= 4; number++) {
      sent.add(files.partsNumbered(number).get(0).body);
    }
    assertArrayEquals(Arrays.copyOf(contents, 23), sent.bytes());
    assertEquals("the stream is read no further than the upload", 23, stream.position);
    assertFalse("the SDK does not close the caller's stream", stream.closed);
    assertTrue(files.begins.get(0).json().get("mkdir_parents").asBoolean());
    assertTrue("the chosen mkdir_parents also completes the upload", files.finishes.get(0).json().get("mkdir_parents").asBoolean());
    assertFalse("a stream upload lets Files.com choose the time", files.finishes.get(0).json().has("provided_mtime"));
  }

  @Test(timeout = 20000)
  public void partsSentInParallelMayFinishInAnyOrderAndAreCompletedInPartOrder() throws Exception {
    List<Long> acceptedOrder = new CopyOnWriteArrayList<>();
    files.storage = (part, attempt) -> {
      if (part == 1) {
        Thread.sleep(400);
      }
      acceptedOrder.add(part);
      return null;
    };
    UploadResult result = client.transfers().upload(file("parallel.bin", bytes(15, 3)), "parallel.bin",
        UploadOptions.builder().concurrencyMode(ConcurrencyMode.STATIC).maxConcurrency(3).build());

    assertTrue("parts were sent at the same time", files.maxConcurrentParts() >= 2);
    assertEquals("part 1 was accepted last", Long.valueOf(1), acceptedOrder.get(acceptedOrder.size() - 1));
    assertTrue(result.isParallelParts());
    JsonNode etags = files.finishes.get(0).json().get("etags");
    for (int i = 0; i < 3; i++) {
      assertEquals(i + 1, etags.get(i).get("part").asInt());
    }
  }

  @Test(timeout = 20000)
  public void onlyTheFailedPartIsSentAgainWithTheSameBytes() throws Exception {
    files.storage = (part, attempt) -> part == 2 && attempt == 1 ? FakeFiles.Answer.status(503, "<Error>SlowDown</Error>") : null;
    List<TransferProgress> progress = new CopyOnWriteArrayList<>();

    UploadResult result = client.transfers().upload(file("retry.bin", bytes(15, 4)), "retry.bin",
        UploadOptions.builder().progressListener(progress::add).build());

    assertEquals(1, files.partsNumbered(1).size());
    assertEquals(2, files.partsNumbered(2).size());
    assertEquals(1, files.partsNumbered(3).size());
    assertArrayEquals(files.partsNumbered(2).get(0).body, files.partsNumbered(2).get(1).body);
    assertEquals(2, result.getParts().get(1).getAttempts());
    assertEquals("\"etag-2-2\"", result.getParts().get(1).getETag());
    assertEquals(1, result.getFinalProgress().getRetries());
    assertEquals(15 + 5, result.getSentBytes());
    assertEquals(15, result.getFinalProgress().getTransferredBytes());
    assertEquals("a report at the start and one per accepted part", 4, progress.size());
    assertEquals(0, progress.get(0).getTransferredBytes());
    assertEquals(15, progress.get(3).getTransferredBytes());
    assertEquals(1, files.finishes.size());
  }

  @Test(timeout = 20000)
  public void retryPartsFalseForbidsSendingAPartAgainEvenWhenOnlyItsRenewedUrlSaysSo() throws Exception {
    files.retryParts = false;
    files.storage = (part, attempt) -> part == 2 ? FakeFiles.Answer.status(503, "") : null;
    try {
      client.transfers().upload(file("no-retry.bin", bytes(15, 5)), "no-retry.bin");
      fail("Expected the failed part to fail the upload");
    } catch (ApiErrorException.ApiConnectionException e) {
      assertEquals("The storage server answered part 2 of the upload to no-retry.bin with HTTP status 503", e.getMessage());
    }
    assertEquals(1, files.partsNumbered(2).size());
    assertEquals("nothing is completed", 0, files.finishes.size());

    // Part 2's URLs allow retrying parts but are about to expire, so the URL of its retry is renewed
    // first; that renewal no longer allows it. The first attempt is sent, its retry is not.
    files.retryParts = true;
    files.storage = (part, attempt) -> part == 2 && attempt == 1 ? FakeFiles.Answer.status(503, "") : null;
    AtomicInteger answersForPart2 = new AtomicInteger();
    AtomicBoolean renewalAllows = new AtomicBoolean();
    files.partEditor = part -> {
      if (part.get("part_number").asLong() == 2) {
        part.put("expires", Instant.now().plusSeconds(10).toString());
        part.put("retry_parts", answersForPart2.incrementAndGet() <= 2 || renewalAllows.get());
      }
      return part;
    };
    try {
      client.transfers().upload(file("withdrawn.bin", bytes(15, 6)), "withdrawn.bin");
      fail("Expected the failed part not to be sent again");
    } catch (ApiErrorException.ApiConnectionException e) {
      assertEquals("The storage server answered part 2 of the upload to withdrawn.bin with HTTP status 503", e.getMessage());
    }
    assertEquals("negotiated, renewed before its first attempt and before its retry", 3, answersForPart2.get());
    assertEquals("sent once", 1, files.parts.stream().filter(p -> p.rawPath.equals("/storage/put-2/2")).count());
    assertEquals(0, files.finishes.size());

    // The same, but the renewal still allows retrying parts: the part is sent again and accepted.
    answersForPart2.set(0);
    renewalAllows.set(true);
    UploadResult result = client.transfers().upload(file("allowed.bin", bytes(15, 7)), "allowed.bin");
    assertEquals(2, files.parts.stream().filter(p -> p.rawPath.equals("/storage/put-3/2")).count());
    assertEquals(2, result.getParts().get(1).getAttempts());
    assertEquals(1, files.finishes.size());
  }

  @Test(timeout = 20000)
  public void withoutParallelPartsOnePartIsSentAtATimeInOrder() throws Exception {
    files.parallelParts = null;
    files.storage = (part, attempt) -> {
      Thread.sleep(50);
      return null;
    };
    UploadResult result = client.transfers().upload(file("serial.bin", bytes(18, 6)), "serial.bin",
        UploadOptions.builder().maxConcurrency(8).build());

    assertEquals(1, files.maxConcurrentParts());
    assertEquals(Arrays.asList(1L, 2L, 3L, 4L), files.parts.stream().map(p -> p.part).collect(Collectors.toList()));
    assertFalse(result.isParallelParts());
    for (FakeFiles.Received begin : files.begins) {
      assertFalse("serial parts are negotiated one at a time", begin.json().has("parts"));
    }
  }

  @Test(timeout = 20000)
  public void throttledPartsWaitForRetryAfterAndOtherwiseAreRetriedAtOnce() throws Exception {
    files.parallelParts = false;
    files.storage = (part, attempt) -> {
      if (attempt > 1) {
        return null;
      }
      if (part == 1) {
        return FakeFiles.Answer.status(429, "").header("Retry-After", "1");
      }
      return part == 2 ? FakeFiles.Answer.status(503, "") : null;
    };
    long start = System.nanoTime();
    client.transfers().upload(file("throttled.bin", bytes(12, 7)), "throttled.bin");
    long elapsedMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);

    assertTrue("waited for Retry-After: " + elapsedMillis, elapsedMillis >= 1000);
    assertTrue("503 without Retry-After is retried without a long wait: " + elapsedMillis, elapsedMillis < 4000);
    assertEquals(2, files.partsNumbered(1).size());
    assertEquals(2, files.partsNumbered(2).size());
  }

  @Test(timeout = 20000)
  public void anUploadNeedingMorePartsThanFilesComAllowsStopsBeforeAskingForThem() throws Exception {
    files.parallelParts = false;
    files.partEditor = part -> {
      part.put("available_parts", 2);
      return part;
    };
    try {
      client.transfers().upload(file("too-many.bin", bytes(12, 35)), "too-many.bin");
      fail("Expected the upload to need too many parts");
    } catch (ApiErrorException.ApiConnectionException e) {
      assertEquals("The upload to too-many.bin needs more than the 2 parts Files.com allows", e.getMessage());
    }
    assertEquals("no third part was asked for or sent", 2, files.begins.size());
    assertEquals(0, files.partsNumbered(3).size());
    assertEquals(0, files.finishes.size());
  }

  @Test(timeout = 20000)
  public void anExpiringUrlIsRenewedForTheSamePartWithoutANewUpload() throws Exception {
    AtomicInteger negotiations = new AtomicInteger();
    files.partEditor = part -> {
      if (negotiations.incrementAndGet() == 1) {
        part.put("expires", Instant.now().plusSeconds(10).toString());
      } else {
        part.put("upload_uri", part.get("upload_uri").asText() + "&renewed=" + negotiations.get());
      }
      return part;
    };
    client.transfers().upload(file("renew.bin", bytes(5, 8)), "renew.bin");

    assertEquals(2, files.begins.size());
    JsonNode renewal = files.begins.get(1).json();
    assertEquals("the same upload", "put-1", renewal.get("ref").asText());
    assertEquals(1, renewal.get("part").asLong());
    assertEquals(FakeFiles.SIGNATURE + "&renewed=2", files.parts.get(0).rawQuery);
    assertEquals("put-1", files.finishes.get(0).json().get("ref").asText());
  }

  @Test(timeout = 20000)
  public void aRenewedUrlThatRunsOutWhileThePartIsReadIsRenewedAgainBeforeThePartIsSent() throws Exception {
    // The first URL is about to expire, so it is renewed before the part is sent. That URL is valid
    // for less time than reading the part takes (here, none at all), so it has run out by the time the
    // part is first sent: storage would refuse it, and as Files.com does not allow retrying parts, the
    // upload would fail. Renewing a part not yet sent needs no permission to retry it.
    files.retryParts = false;
    AtomicInteger negotiations = new AtomicInteger();
    files.partEditor = part -> {
      int negotiation = negotiations.incrementAndGet();
      Instant expires = negotiation == 1 ? Instant.now().plusSeconds(10) : negotiation == 2 ? Instant.now().minusSeconds(1)
          : Instant.now().plus(1, ChronoUnit.HOURS);
      part.put("expires", expires.toString());
      part.put("upload_uri", part.get("upload_uri").asText() + "&negotiation=" + negotiation);
      return part;
    };
    client.transfers().upload(file("run-out.bin", bytes(5, 9)), "run-out.bin");

    assertEquals(3, files.begins.size());
    assertEquals("the same part of the same upload", "put-1", files.begins.get(2).json().get("ref").asText());
    assertEquals(1, files.begins.get(2).json().get("part").asLong());
    assertEquals(FakeFiles.SIGNATURE + "&negotiation=3", files.parts.get(0).rawQuery);
    assertEquals(1, files.parts.size());
  }

  @Test(timeout = 20000)
  public void storageUrlsAreSentExactlyAsGivenWithTheNegotiatedMethodAndHeaders() throws Exception {
    files.extraQuery = "&x-id=PutObject&weird=%2Fa%2Bb%3D&empty=&dup=1&dup=2&tilde=~&plus=a+b";
    files.httpMethod = "post";
    files.headersJson = "{\"x-ms-blob-type\":\"BlockBlob\",\"Content-Length\":\"4\"}";
    client.transfers().upload(file("exact.bin", bytes(4, 9)), "exact.bin");

    FakeFiles.Received part = files.parts.get(0);
    assertEquals(FakeFiles.SIGNATURE + files.extraQuery, part.rawQuery);
    assertEquals("POST", part.method);
    assertEquals("BlockBlob", part.header("x-ms-blob-type"));
    assertEquals("4", part.header("Content-Length"));
  }

  @Test(timeout = 20000)
  public void instructionsTheSdkCannotFollowStopTheUploadBeforeAnyBytesAreSent() throws Exception {
    files.sendJson = "{\"partsize\":\"required-parameter content-length\",\"partdata\":\"required-parameter file\"}";
    try {
      client.transfers().upload(file("form.bin", bytes(4, 10)), "form.bin");
      fail("Expected the form instructions to be refused");
    } catch (UnsupportedUploadException e) {
      assertTrue(e.getMessage(), e.getMessage().contains("cannot do"));
    }
    files.sendJson = "{\"partsize\":\"required-header Content-Length\",\"partdata\":\"body\"}";
    files.parametersJson = "{\"key\":\"value\"}";
    try {
      client.transfers().upload(file("parameters.bin", bytes(4, 10)), "parameters.bin");
      fail("Expected the parameters to be refused");
    } catch (UnsupportedUploadException e) {
      assertTrue(e.getMessage(), e.getMessage().contains("parameters"));
    }
    assertEquals(0, files.parts.size());
    assertEquals(0, files.finishes.size());
  }

  @Test(timeout = 20000)
  public void aLostAnswerToTheFinalRequestLeavesTheUploadUncertainAndIsNotRepeated() throws Exception {
    files.finish = request -> FakeFiles.Answer.drop();
    try {
      client.transfers().upload(file("lost.bin", bytes(8, 11)), "lost.bin");
      fail("Expected the outcome to be unknown");
    } catch (UploadCompletionUnknownException e) {
      assertEquals("lost.bin", e.getPath());
      assertEquals(8, e.getSize());
      assertEquals(2, e.getParts().size());
      assertTrue(e.getCause() instanceof ApiErrorException.ApiConnectionException);
    }
    assertEquals("sent once", 1, files.finishes.size());
    assertEquals("no replacement upload", 2, files.begins.size());
  }

  @Test(timeout = 20000)
  public void aFinalRequestThatCouldNotBeSentIsRetriedAndOneNeverSentCompletedNothing() throws Exception {
    AtomicInteger failuresLeft = new AtomicInteger(1);
    FilesClient failing = FilesClient.builder().apiKey("transfer-key").apiRoot(files.root()).retryDelay(Duration.ofMillis(1))
        .transport(com.files.net.HttpTransport.builder().customize(http -> http.addInterceptorLast(
            (org.apache.http.HttpRequestInterceptor) (request, context) -> {
              if (request.getRequestLine().getUri().startsWith("/api/rest/v1/files/") && failuresLeft.getAndDecrement() > 0) {
                throw new java.net.ConnectException("refused before sending");
              }
            })))
        .build();
    try {
      UploadResult result = failing.transfers().upload(file("unsent.bin", bytes(4, 33)), "unsent.bin");
      assertEquals(4, result.getSize());
      assertEquals("the unsent attempt was retried", 1, files.finishes.size());

      failuresLeft.set(Integer.MAX_VALUE);
      try {
        failing.transfers().upload(file("never.bin", bytes(4, 34)), "never.bin");
        fail("Expected the final request to fail");
      } catch (UploadCompletionUnknownException e) {
        fail("A request that never reached Files.com completed nothing: " + e);
      } catch (ApiErrorException.ApiConnectionException e) {
        assertEquals("The request failed: the connection could not be made (ConnectException) (response to POST /api/rest/v1/files/never.bin)", e.getMessage());
      }
      assertEquals(1, files.finishes.size());
    } finally {
      failing.close();
    }
  }

  @Test(timeout = 20000)
  public void aRefusedFinalRequestIsTheApiErrorItself() throws Exception {
    files.finish = request -> FakeFiles.Answer.status(422,
        "{\"type\":\"processing-failure/file-too-large\",\"http-code\":422,\"error\":\"Too large\"}");
    try {
      client.transfers().upload(file("refused.bin", bytes(3, 12)), "refused.bin");
      fail("Expected the refusal");
    } catch (UploadCompletionUnknownException e) {
      fail("A refusal is not an unknown outcome");
    } catch (ApiErrorException e) {
      assertEquals(422, e.getHttpCode());
    }
    assertEquals(1, files.finishes.size());
  }

  @Test(timeout = 20000)
  public void cancellingWhileAPartIsBeingSentAbortsItAndCompletesNothing() throws Exception {
    CountDownLatch never = new CountDownLatch(1);
    files.storage = (part, attempt) -> FakeFiles.Answer.holdUntil(never);
    CancellationToken token = new CancellationToken();
    ExecutorService canceller = Executors.newSingleThreadExecutor();
    try {
      canceller.execute(() -> {
        awaitTrue(() -> files.maxConcurrentParts() > 0);
        token.cancel();
      });
      long start = System.nanoTime();
      try {
        client.transfers().upload(file("cancel.bin", bytes(15, 13)), "cancel.bin", UploadOptions.builder().cancellation(token).build());
        fail("Expected the upload to be cancelled");
      } catch (OperationCancelledException e) {
        assertEquals(OperationCancelledException.Reason.CANCELLED, e.getReason());
        assertEquals("The upload to cancel.bin was cancelled.", e.getMessage());
      }
      assertTrue("stopped promptly", TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start) < 10000);
    } finally {
      never.countDown();
      canceller.shutdownNow();
    }
    assertEquals(0, files.finishes.size());
    assertEquals("the upload's threads have ended", Arrays.asList(), uploadThreads());
  }

  @Test(timeout = 20000)
  public void anUploadWaitingForASharedLimiterCanBeCancelledWithoutSendingAnything() throws Exception {
    CountDownLatch release = new CountDownLatch(1);
    files.storage = (part, attempt) -> FakeFiles.Answer.holdUntil(release);
    TransferLimiter limiter = new TransferLimiter(1, 1024);
    FilesClient other = FilesClient.builder().apiKey("other-key").apiRoot(files.root()).build();
    ExecutorService pool = Executors.newFixedThreadPool(2);
    try {
      final Future<UploadResult> first = pool.submit(() -> other.transfers().upload(file("first.bin", bytes(3, 14)), "first.bin",
          UploadOptions.builder().limiter(limiter).build()));
      awaitTrue(() -> limiter.getActiveRequests() == 1);
      CancellationToken token = new CancellationToken();
      Future<UploadResult> second = pool.submit(() -> client.transfers().upload(file("second.bin", bytes(3, 15)), "second.bin",
          UploadOptions.builder().limiter(limiter).cancellation(token).build()));
      awaitTrue(() -> limiter.getQueuedRequests() == 1);
      token.cancel();
      try {
        second.get(10, TimeUnit.SECONDS);
        fail("Expected the waiting upload to be cancelled");
      } catch (java.util.concurrent.ExecutionException e) {
        assertTrue(String.valueOf(e.getCause()), e.getCause() instanceof OperationCancelledException);
      }
      assertEquals("the waiting upload gave its place up", 0, limiter.getQueuedRequests());
      release.countDown();
      assertEquals(3, first.get(10, TimeUnit.SECONDS).getSize());
    } finally {
      release.countDown();
      pool.shutdownNow();
      other.close();
    }
    assertEquals("only the first upload sent a part", 1, files.parts.size());
    assertEquals("other-key", files.finishes.get(0).header("X-FilesAPI-Key"));
    assertEquals(1, files.finishes.size());
  }

  @Test(timeout = 20000)
  public void interruptingTheUploadingThreadStopsTheUploadAndKeepsItsInterruptStatus() throws Exception {
    CountDownLatch never = new CountDownLatch(1);
    files.storage = (part, attempt) -> FakeFiles.Answer.holdUntil(never);
    Path source = file("interrupt.bin", bytes(5, 16));
    AtomicReference<Object> outcome = new AtomicReference<>();
    Thread uploader = new Thread(() -> {
      try {
        client.transfers().upload(source, "interrupt.bin");
        outcome.set("completed");
      } catch (OperationCancelledException e) {
        outcome.set(e.getReason() + " interrupted=" + Thread.currentThread().isInterrupted());
      } catch (Exception e) {
        outcome.set(e);
      }
    });
    try {
      uploader.start();
      awaitTrue(() -> files.maxConcurrentParts() > 0);
      uploader.interrupt();
      uploader.join(10000);
    } finally {
      never.countDown();
    }
    assertEquals("INTERRUPTED interrupted=true", outcome.get());
    assertEquals(0, files.finishes.size());
  }

  @Test(timeout = 20000)
  public void interruptingTheUploadingThreadWhileItsFileIsReadToBeSentStopsTheUploadAndKeepsItsInterruptStatus() throws Exception {
    // One part, far larger than the connection holds: the storage server lets the sending fill the
    // connection, then interrupts the uploading thread and reads on, so the file is read again to be
    // sent right after the interruption.
    s3Parts();
    AtomicInteger attempts = new AtomicInteger();
    String outcome = uploadInterruptibly(file("interrupt-sending.bin", bytes(24 * MIB, 19)),
        UploadOptions.builder().partSizing(PartSizing.fixed(32L * MIB)).build(), uploader -> (part, attempt) -> {
          attempts.incrementAndGet();
          Thread.sleep(100);
          uploader.interrupt();
          return null;
        });
    assertEquals("INTERRUPTED interrupted=true", outcome);
    assertEquals("the part was not sent again", 1, attempts.get());
    assertEquals(0, files.finishes.size());
  }

  @Test(timeout = 20000)
  public void interruptingTheUploadingThreadBeforeItsPartFailsStopsTheUploadAsInterrupted() throws Exception {
    // The storage server interrupts the uploading thread, then drops the connection: the part fails,
    // and may not be sent again, right after the interruption.
    files.retryParts = false;
    AtomicInteger attempts = new AtomicInteger();
    String outcome = uploadInterruptibly(file("interrupt-failing.bin", bytes(5, 20)), UploadOptions.builder().build(), uploader -> (part, attempt) -> {
      attempts.incrementAndGet();
      uploader.interrupt();
      return FakeFiles.Answer.drop();
    });
    assertEquals("INTERRUPTED interrupted=true", outcome);
    assertEquals(1, attempts.get());
    assertEquals(0, files.finishes.size());
  }

  @Test(timeout = 20000)
  public void interruptingTheUploadingThreadWhileItsPartIsAcceptedStopsTheUploadBeforeItIsCompleted() throws Exception {
    // The storage server interrupts the uploading thread, then accepts the part.
    String outcome = uploadInterruptibly(file("interrupt-accepted.bin", bytes(5, 21)), UploadOptions.builder().build(), uploader -> (part, attempt) -> {
      uploader.interrupt();
      return null;
    });
    assertEquals("INTERRUPTED interrupted=true", outcome);
    assertEquals(1, files.parts.size());
    assertEquals(0, files.finishes.size());
  }

  // Uploads source on a thread of its own, which the storage server's answers may interrupt. Returns
  // "completed", the cancellation's reason with the thread's interrupt status, or the failure.
  private String uploadInterruptibly(Path source, UploadOptions options, java.util.function.Function<Thread, FakeFiles.Storage> storage)
      throws InterruptedException {
    AtomicReference<Object> outcome = new AtomicReference<>();
    Thread uploader = new Thread(() -> {
      try {
        client.transfers().upload(source, source.getFileName().toString(), options);
        outcome.set("completed");
      } catch (OperationCancelledException e) {
        outcome.set(e.getReason() + " interrupted=" + Thread.currentThread().isInterrupted());
      } catch (Exception e) {
        outcome.set(e);
      }
    });
    files.storage = storage.apply(uploader);
    uploader.start();
    uploader.join(10000);
    return String.valueOf(outcome.get());
  }

  @Test(timeout = 20000)
  public void anUploadThatRunsOutOfTimeStops() throws Exception {
    CountDownLatch never = new CountDownLatch(1);
    files.storage = (part, attempt) -> FakeFiles.Answer.holdUntil(never);
    try {
      client.transfers().upload(file("slow.bin", bytes(5, 17)), "slow.bin", UploadOptions.builder().timeout(Duration.ofMillis(300)).build());
      fail("Expected the upload to time out");
    } catch (OperationCancelledException e) {
      assertEquals(OperationCancelledException.Reason.TIMED_OUT, e.getReason());
    } finally {
      never.countDown();
    }
    assertEquals(0, files.finishes.size());
  }

  @Test(timeout = 20000)
  public void closingTheClientStopsItsUploadsAndWaitsForThem() throws Exception {
    CountDownLatch never = new CountDownLatch(1);
    files.storage = (part, attempt) -> FakeFiles.Answer.holdUntil(never);
    Path source = file("closing.bin", bytes(5, 18));
    ExecutorService pool = Executors.newSingleThreadExecutor();
    try {
      Future<UploadResult> upload = pool.submit(() -> client.transfers().upload(source, "closing.bin"));
      awaitTrue(() -> files.maxConcurrentParts() > 0);
      client.close();
      assertEquals("close waited for the upload's threads to end", Arrays.asList(), uploadThreads());
      try {
        upload.get();
        fail("Expected the upload to be cancelled");
      } catch (java.util.concurrent.ExecutionException e) {
        assertEquals("The upload to closing.bin was cancelled because its client was closed.", e.getCause().getMessage());
      }
    } finally {
      never.countDown();
      pool.shutdownNow();
    }
    try {
      client.transfers();
      fail("Expected the closed client to refuse new work");
    } catch (IllegalStateException e) {
      assertEquals("This FilesClient has been closed", e.getMessage());
    }
  }

  @Test(timeout = 20000)
  public void aStreamThatEndsEarlyFailsWithoutCompletingTheUpload() throws Exception {
    try {
      client.transfers().upload(new ByteArrayInputStream(bytes(12, 19)), 20, "short.bin", null);
      fail("Expected the short stream to fail the upload");
    } catch (EOFException e) {
      assertEquals("The stream ended after 12 of the 20 bytes to upload", e.getMessage());
    }
    assertEquals(0, files.finishes.size());
  }

  @Test(timeout = 20000)
  public void aPartLargerThanTheLimiterMayBufferFailsBeforeTheStreamIsRead() throws Exception {
    files.partsize = 100;
    TrickleStream stream = new TrickleStream(bytes(200, 20));
    try {
      client.transfers().upload(stream, 200, "big.bin", UploadOptions.builder().limiter(new TransferLimiter(2, 50)).build());
      fail("Expected the part to be too large");
    } catch (IllegalStateException e) {
      assertTrue(e.getMessage(), e.getMessage().startsWith("Part 1 of the upload to big.bin is 100 bytes, more than the 50 bytes"));
    }
    assertEquals(0, stream.position);
    assertEquals(0, files.parts.size());
  }

  @Test(timeout = 20000)
  public void aFileChangedDuringTheUploadIsNotCompleted() throws Exception {
    Path source = file("changing.bin", bytes(10, 21));
    files.storage = (part, attempt) -> {
      if (part == 1 && attempt == 1) {
        Files.write(source, bytes(3, 22), StandardOpenOption.APPEND);
      }
      return null;
    };
    try {
      client.transfers().upload(source, "changing.bin");
      fail("Expected the change to stop the upload");
    } catch (IOException e) {
      assertEquals("The file changed while it was being uploaded, so the upload was not completed", e.getMessage());
    }
    assertEquals(0, files.finishes.size());
  }

  @Test(timeout = 20000)
  public void putInputStreamSendsTheRestOfAnUploadStartedWithCreateWithoutClosingTheStream() throws Exception {
    files.partsize = 4;
    FileUploadPart first = client.files().create("legacy.bin", new HashMap<>());
    TrickleStream stream = new TrickleStream(bytes(10, 23));

    File file = first.putInputStream(stream, null);

    assertEquals("legacy.bin", file.getPath());
    assertEquals(1, files.creates.size());
    assertEquals("put-1", files.begins.get(0).json().get("ref").asText());
    assertEquals(2, files.begins.get(0).json().get("part").asLong());
    assertEquals(Arrays.asList(4L, 4L, 2L), Stream.of(1L, 2L, 3L).map(n -> (long) files.partsNumbered(n).get(0).body.length)
        .collect(Collectors.toList()));
    assertEquals(10, files.finishes.get(0).json().get("size").asLong());
    assertEquals("put-1", files.finishes.get(0).json().get("ref").asText());
    assertFalse(stream.closed);
  }

  @Test(timeout = 20000)
  public void putBufferedInputStreamResendsAFailedPartFromItsOwnCopyOfTheBytes() throws Exception {
    files.partsize = 6;
    files.storage = (part, attempt) -> part == 1 && attempt == 1 ? FakeFiles.Answer.drop() : null;
    FileUploadPart first = client.files().create("replay.bin", new HashMap<>());
    byte[] contents = bytes(9, 24);

    first.putBufferedInputStream(new java.io.BufferedInputStream(new ByteArrayInputStream(contents)), contents.length, null);

    assertEquals(2, files.partsNumbered(1).size());
    assertArrayEquals(Arrays.copyOf(contents, 6), files.partsNumbered(1).get(1).body);
    assertArrayEquals(Arrays.copyOfRange(contents, 6, 9), files.partsNumbered(2).get(0).body);
  }

  @Test(timeout = 20000)
  public void twoClientsUploadAtTheSameTimeWithTheirOwnCredentials() throws Exception {
    FilesClient second = FilesClient.builder().sessionId("session-b").workspaceId(7L).apiRoot(files.root()).build();
    ExecutorService pool = Executors.newFixedThreadPool(2);
    try {
      Future<UploadResult> a = pool.submit(() -> client.transfers().upload(file("a.bin", bytes(12, 25)), "a.bin"));
      Future<UploadResult> b = pool.submit(() -> second.transfers().upload(file("b.bin", bytes(12, 26)), "b.bin"));
      a.get(10, TimeUnit.SECONDS);
      b.get(10, TimeUnit.SECONDS);
    } finally {
      pool.shutdownNow();
      second.close();
    }
    List<FakeFiles.Received> api = new ArrayList<>(files.begins);
    api.addAll(files.finishes);
    for (FakeFiles.Received request : api) {
      if (request.rawPath.endsWith("a.bin")) {
        assertEquals("transfer-key", request.header("X-FilesAPI-Key"));
        assertNull(request.header("X-FilesAPI-Auth"));
        assertEquals("0", request.header("X-Files-Workspace-Id"));
      } else {
        assertEquals("session-b", request.header("X-FilesAPI-Auth"));
        assertNull(request.header("X-FilesAPI-Key"));
        assertEquals("7", request.header("X-Files-Workspace-Id"));
      }
    }
    assertEquals(2, files.finishes.size());
  }

  private static final int MIB = 1024 * 1024;

  // Makes every part begin_upload returns a part of one S3 multipart upload, uploadIdOfPart naming
  // its upload: a SigV4 UploadPart URL for the upload's object that signs only the host, so the
  // part's length and bytes are the SDK's to choose.
  private void s3Parts(LongFunction<String> uploadIdOfPart) {
    files.partEditor = part -> {
      long number = part.get("part_number").asLong();
      part.put("upload_uri", files.root() + "/storage/" + part.get("ref").asText() + "/object?partNumber=" + number
          + "&uploadId=" + uploadIdOfPart.apply(number) + "&X-Amz-Algorithm=AWS4-HMAC-SHA256"
          + "&X-Amz-Credential=AKIDEXAMPLE%2F20261014%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-SignedHeaders=host&" + FakeFiles.SIGNATURE);
      return part;
    };
  }

  private void s3Parts() {
    s3Parts(number -> "UPLOAD-1");
  }

  // The lengths of the parts the result was completed with, checking that the last request the
  // storage server received for each part held its length, and that together they held contents.
  private List<Long> partLengths(UploadResult result, byte[] contents) {
    ByteArrayOutputStream received = new ByteArrayOutputStream();
    List<Long> lengths = new ArrayList<>();
    for (UploadedPart part : result.getParts()) {
      lengths.add(part.getLength());
      List<FakeFiles.Received> attempts = files.partsNumbered(part.getNumber());
      byte[] body = attempts.get(attempts.size() - 1).body;
      assertEquals(part.getLength(), body.length);
      received.write(body, 0, body.length);
    }
    assertArrayEquals(contents, received.toByteArray());
    return lengths;
  }

  @Test(timeout = 30000)
  public void anS3PartGrantLetsTheSdkSizeThePartsAndAnyOtherGrantKeepsTheServersSizes() throws Exception {
    // An S3 grant: parts of at least 5 MiB but the last, which holds the rest, whatever partsize says.
    files.partsize = 5;
    s3Parts();
    byte[] large = bytes(11 * MIB, 3);
    UploadResult chosen = client.transfers().upload(file("chosen.bin", large), "chosen.bin");
    assertEquals(PartSizeSource.AUTOMATIC, chosen.getPartSizeSource());
    assertEquals(Arrays.asList(5L * MIB, 5L * MIB, 1L * MIB), partLengths(chosen, large));
    JsonNode finished = files.finishes.get(files.finishes.size() - 1).json();
    assertEquals(11L * MIB, finished.get("size").asLong());
    assertEquals(3, finished.get("etags").size());

    // A file smaller than S3's smallest part goes as one part under the S3 grant, but in Files.com's
    // 5-byte parts under any grant that does not let the SDK choose.
    byte[] small = bytes(12, 4);
    assertEquals(Collections.singletonList(12L), partLengths(client.transfers().upload(file("one.bin", small), "one.bin"), small));
    String sigV4 = "&X-Amz-SignedHeaders=host&";
    Map<String, Function<ObjectNode, ObjectNode>> keepServerSizes = new LinkedHashMap<>();
    keepServerSizes.put("a signature that covers the length", part -> part.put("upload_uri",
        part.get("upload_uri").asText().replace(sigV4, "&X-Amz-SignedHeaders=content-length%3Bhost&")));
    keepServerSizes.put("a checksum the URL requires", part -> part.put("upload_uri",
        part.get("upload_uri").asText().replace(sigV4, sigV4 + "x-amz-checksum-crc32=AAAAAA%3D%3D&")));
    keepServerSizes.put("headers the part must be sent with", part -> {
      part.putObject("headers").put("x-amz-server-side-encryption", "AES256");
      return part;
    });
    keepServerSizes.put("a Files Agent destination", part -> {
      ObjectNode direct = part.putObject("direct_connection_info");
      direct.put("version", 1).put("server_name", "agent").put("direct_uri", "https://agent.example:8443").put("ca_pem", "PEM");
      direct.putArray("addresses").add("192.0.2.10");
      return part;
    });
    Function<ObjectNode, ObjectNode> s3 = files.partEditor;
    for (Map.Entry<String, Function<ObjectNode, ObjectNode>> variant : keepServerSizes.entrySet()) {
      files.partEditor = s3.andThen(variant.getValue());
      UploadResult planned = client.transfers().upload(file("planned.bin", small), "planned.bin");
      assertEquals(variant.getKey(), PartSizeSource.SERVER, planned.getPartSizeSource());
      assertEquals(variant.getKey(), Arrays.asList(5L, 5L, 2L), partLengths(planned, small));
    }
    files.partEditor = s3;
    UploadResult asked = client.transfers().upload(file("asked.bin", small), "asked.bin",
        UploadOptions.builder().partSizing(PartSizing.serverPlanned()).build());
    assertEquals(PartSizeSource.SERVER, asked.getPartSizeSource());
    assertEquals(Arrays.asList(5L, 5L, 2L), partLengths(asked, small));
  }

  @Test(timeout = 20000)
  public void aLaterPartOfAnotherS3UploadStopsTheUploadBeforeThatPartIsSent() throws Exception {
    s3Parts(number -> number == 1 ? "UPLOAD-1" : "UPLOAD-2");
    try {
      client.transfers().upload(file("switched.bin", bytes(11 * MIB, 5)), "switched.bin");
      fail("Expected the upload to stop");
    } catch (ApiErrorException.ApiConnectionException e) {
      assertTrue(e.getMessage(), e.getMessage().contains("not for the same S3 multipart upload"));
    }
    assertTrue(files.partsNumbered(2).isEmpty());
    assertTrue(files.partsNumbered(3).isEmpty());
    assertTrue(files.finishes.isEmpty());
  }

  @Test(timeout = 30000)
  public void aStreamOfUnknownLengthIsSentInPartsSizedForTheMostItMayHold() throws Exception {
    s3Parts();
    byte[] contents = bytes(17 * MIB + 3, 9);
    CountingStream bounded = new CountingStream(contents);
    UploadResult result = client.transfers().upload(bounded, "piped.bin", UploadOptions.builder().maxStreamLength(64L * MIB).build());
    assertEquals(PartSizeSource.AUTOMATIC, result.getPartSizeSource());
    assertEquals(Arrays.asList(8L * MIB, 8L * MIB, 1L * MIB + 3), partLengths(result, contents));
    assertEquals(17L * MIB + 3, result.getSize());
    assertEquals(17L * MIB + 3, files.finishes.get(0).json().get("size").asLong());
    assertFalse("the stream is the caller's", bounded.closed);

    // By default a stream may hold 5 TiB, which 10,000 parts held in the limiter's 128 MiB of memory
    // cannot: Files.com's parts are sent instead.
    files.partsize = 6 * MIB;
    UploadResult planned = client.transfers().upload(new ByteArrayInputStream(contents), "planned.bin", UploadOptions.defaults());
    assertEquals(PartSizeSource.SERVER, planned.getPartSizeSource());
    assertEquals(Arrays.asList(6L * MIB, 6L * MIB, 5L * MIB + 3), partLengths(planned, contents));

    // Nor do the SDK's sizes plan for more than 5 TiB, however large its parts could be.
    UploadResult unbounded = client.transfers().upload(new ByteArrayInputStream(contents), "unbounded.bin",
        UploadOptions.builder().maxStreamLength(6L << 40).limiter(new TransferLimiter(4, 2L << 30)).build());
    assertEquals(PartSizeSource.SERVER, unbounded.getPartSizeSource());
    assertEquals(Arrays.asList(6L * MIB, 6L * MIB, 5L * MIB + 3), partLengths(unbounded, contents));
  }

  @Test(timeout = 30000)
  public void aStreamHoldingMoreThanItsBoundFailsWithoutSendingBytesPastIt() throws Exception {
    byte[] contents = bytes(12 * MIB, 4);
    s3Parts();
    for (PartSizing sizing : Arrays.asList(PartSizing.automatic(), PartSizing.serverPlanned())) {
      files.parts.clear();
      files.partsize = 4 * MIB;
      try {
        client.transfers().upload(new ByteArrayInputStream(contents), "too-long.bin",
            UploadOptions.builder().maxStreamLength(10L * MIB).partSizing(sizing).build());
        fail("Expected the upload to fail");
      } catch (IllegalStateException e) {
        assertTrue(e.getMessage(), e.getMessage().contains("maxStreamLength"));
      }
      long sent = files.parts.stream().mapToLong(part -> part.body.length).sum();
      assertTrue(sizing + " sent " + sent, sent <= 10L * MIB);
    }
    assertTrue(files.finishes.isEmpty());
  }

  @Test(timeout = 20000)
  public void aPartWaitingToBeSentAgainLetsAnotherPartBeSentMeanwhile() throws Exception {
    // Two parts at a time: part 1 is throttled for a second, and part 2 is answered only once part 3
    // has arrived, which can happen only if part 1 gives up its place while it waits. The upload is
    // static, so throttling pauses nothing; an automatic upload pauses new parts instead (below).
    CountDownLatch partThreeArrived = new CountDownLatch(1);
    files.storage = (part, attempt) -> {
      if (part == 1 && attempt == 1) {
        return FakeFiles.Answer.status(429, "").header("Retry-After", "1");
      }
      if (part == 2) {
        return FakeFiles.Answer.holdUntil(partThreeArrived);
      }
      if (part == 3) {
        partThreeArrived.countDown();
      }
      return null;
    };
    client.transfers().upload(file("waiting.bin", bytes(20, 6)), "waiting.bin",
        UploadOptions.builder().concurrencyMode(ConcurrencyMode.STATIC).maxConcurrency(2).build());

    int partThree = -1;
    int partOneAgain = -1;
    for (int i = 0; i < files.parts.size(); i++) {
      FakeFiles.Received received = files.parts.get(i);
      if (received.part == 3 && partThree < 0) {
        partThree = i;
      }
      if (received.part == 1 && received.attempt == 2) {
        partOneAgain = i;
      }
    }
    assertTrue("part 3 was sent while part 1 waited: " + partThree + " before " + partOneAgain, partThree >= 0 && partThree < partOneAgain);
    assertTrue(files.maxConcurrentParts() <= 2);
  }

  @Test(timeout = 30000)
  public void uploadsSharingATransportSendNoMorePartsToOneHostThanItHasConnectionsFor() throws Exception {
    // The storage host, localhost, is a route of its own, with two connections, and each part takes a
    // while; each static upload sends two parts at once. A part that had to wait for a pooled
    // connection would give up after 100 ms, and with no retries fail its upload; parts wait for their
    // turn instead.
    files.partEditor = part -> part.put("upload_uri", part.get("upload_uri").asText().replace("127.0.0.1", "localhost"));
    files.storage = (part, attempt) -> {
      Thread.sleep(300);
      return null;
    };
    try (FilesClient narrow = FilesClient.builder()
             .apiKey("transfer-key")
             .apiRoot(files.root())
             .maxRetries(0)
             .connectionAcquisitionTimeout(Duration.ofMillis(100))
             .transport(HttpTransport.builder().maxConnectionsPerRoute(2))
             .build()) {
      ExecutorService uploads = Executors.newFixedThreadPool(2);
      try {
        List<Future<UploadResult>> results = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
          Path source = file("shared-" + i + ".bin", bytes(30, i));
          results.add(uploads.submit(() -> narrow.transfers().upload(source, source.getFileName().toString(),
              UploadOptions.builder().concurrencyMode(ConcurrencyMode.STATIC).maxConcurrency(8).build())));
        }
        for (Future<UploadResult> result : results) {
          assertEquals(6, result.get(20, TimeUnit.SECONDS).getParts().size());
        }
      } finally {
        uploads.shutdownNow();
      }
    }
    assertTrue("at most two parts at once: " + files.maxConcurrentParts(), files.maxConcurrentParts() <= 2);
  }

  @Test(timeout = 30000)
  public void aFixedPartSizeIsSentWhereTheSdkMayChooseSizesAndRefusedElsewhere() throws Exception {
    s3Parts();
    byte[] contents = bytes(13 * MIB, 8);
    UploadResult fixed = client.transfers().upload(file("fixed.bin", contents), "fixed.bin",
        UploadOptions.builder().partSizing(PartSizing.fixed(6L * MIB)).build());
    assertEquals(PartSizeSource.FIXED, fixed.getPartSizeSource());
    assertEquals(Arrays.asList(6L * MIB, 6L * MIB, 1L * MIB), partLengths(fixed, contents));

    files.partEditor = part -> part;
    int partsBefore = files.parts.size();
    try {
      client.transfers().upload(file("refused.bin", bytes(12, 1)), "refused.bin",
          UploadOptions.builder().partSizing(PartSizing.fixed(6L * MIB)).build());
      fail("Expected fixed parts to be refused");
    } catch (IllegalStateException e) {
      assertTrue(e.getMessage(), e.getMessage().contains("fixed parts"));
    }
    assertEquals(partsBefore, files.parts.size());
    assertEquals(1, files.finishes.size());
  }

  // Holds every part's answer until release is counted down, recording how many have arrived.
  private CountDownLatch holdParts(CountDownLatch arrived, CountDownLatch release) {
    files.storage = (part, attempt) -> {
      arrived.countDown();
      return FakeFiles.Answer.holdUntil(release);
    };
    return release;
  }

  @Test(timeout = 20000)
  public void aDefaultUploadToS3SendsOnePartAtATimeUntilStorageAnswers() throws Exception {
    CountDownLatch arrived = new CountDownLatch(1);
    CountDownLatch release = holdParts(arrived, new CountDownLatch(1));
    List<TransferProgress> progress = new CopyOnWriteArrayList<>();
    ExecutorService uploads = Executors.newSingleThreadExecutor();
    try {
      Future<UploadResult> upload = uploads.submit(() -> client.transfers().upload(file("warm.bin", bytes(30, 2)), "warm.bin",
          UploadOptions.builder().progressListener(progress::add).build()));
      assertTrue(arrived.await(10, TimeUnit.SECONDS));
      Thread.sleep(300);
      assertEquals("one part in flight while none has been answered", 1, files.maxConcurrentParts());
      release.countDown();
      UploadResult result = upload.get(10, TimeUnit.SECONDS);

      assertEquals(6, result.getParts().size());
      TransferConcurrency concurrency = result.getFinalProgress().getConcurrency();
      assertEquals(ConcurrencyMode.AUTOMATIC, concurrency.getMode());
      assertEquals(UploadDestination.S3, concurrency.getDestination());
      assertEquals(UploadStartup.DEFAULT, concurrency.getStartup());
      assertTrue(concurrency.getMaxTarget() >= 150);
      assertTrue(concurrency.getPeakActiveAttempts() >= files.maxConcurrentParts());
      assertTrue("every report after the first part was planned says how parts are sent",
          progress.stream().skip(1).allMatch(report -> report.getConcurrency() != null));
    } finally {
      release.countDown();
      uploads.shutdownNow();
    }
  }

  @Test(timeout = 20000)
  public void anAggressiveUploadStartsWithAsManyPartsAtOnceAsItMaySend() throws Exception {
    CountDownLatch arrived = new CountDownLatch(4);
    CountDownLatch release = holdParts(arrived, new CountDownLatch(1));
    ExecutorService uploads = Executors.newSingleThreadExecutor();
    try {
      Future<UploadResult> upload = uploads.submit(() -> client.transfers().upload(file("fast.bin", bytes(30, 3)), "fast.bin",
          UploadOptions.builder().startup(UploadStartup.AGGRESSIVE).maxConcurrency(4).build()));
      assertTrue("four parts at once before any was answered", arrived.await(10, TimeUnit.SECONDS));
      Thread.sleep(200);
      assertEquals("no more than maxConcurrency", 4, files.maxConcurrentParts());
      release.countDown();
      TransferConcurrency concurrency = upload.get(10, TimeUnit.SECONDS).getFinalProgress().getConcurrency();
      assertEquals(UploadStartup.AGGRESSIVE, concurrency.getStartup());
      assertEquals(4, concurrency.getMaxTarget());
      assertFalse(concurrency.isWarmingUp());
    } finally {
      release.countDown();
      uploads.shutdownNow();
    }
  }

  @Test(timeout = 20000)
  public void anUploadToAFilesAgentStartsAtTwoPartsAtOnceWhateverItsStartup() throws Exception {
    files.partEditor = part -> {
      ObjectNode direct = part.putObject("direct_connection_info");
      direct.put("version", 1).put("server_name", "agent").put("direct_uri", "https://agent.example:8443").put("ca_pem", "PEM");
      direct.putArray("addresses").add("192.0.2.10");
      return part;
    };
    for (UploadStartup startup : UploadStartup.values()) {
      files.parts.clear();
      CountDownLatch arrived = new CountDownLatch(2);
      CountDownLatch release = holdParts(arrived, new CountDownLatch(1));
      ExecutorService uploads = Executors.newSingleThreadExecutor();
      try {
        Future<UploadResult> upload = uploads.submit(() -> client.transfers().upload(file("agent.bin", bytes(30, 4)), "agent.bin",
            UploadOptions.builder().startup(startup).build()));
        assertTrue(arrived.await(10, TimeUnit.SECONDS));
        Thread.sleep(300);
        assertEquals(startup + ": the Files Agent policy's start", 2, files.maxConcurrentParts());
        release.countDown();
        TransferConcurrency concurrency = upload.get(10, TimeUnit.SECONDS).getFinalProgress().getConcurrency();
        assertEquals(UploadDestination.FILES_AGENT, concurrency.getDestination());
        assertEquals(8, concurrency.getMaxTarget());
      } finally {
        release.countDown();
        uploads.shutdownNow();
      }
    }
  }

  @Test(timeout = 20000)
  public void aThrottledAutomaticUploadSendsNoNewPartUntilTheRetryAfterHasPassed() throws Exception {
    // Parts 1 and 2 are sent together; part 1 is throttled at once, for a second, and part 2 is answered
    // a little later, so that every other attempt is sent only after the throttling answer.
    java.util.Map<String, Long> arrivals = new java.util.concurrent.ConcurrentHashMap<>();
    files.storage = (part, attempt) -> {
      arrivals.put(part + "#" + attempt, System.nanoTime());
      if (part == 2 && attempt == 1) {
        return FakeFiles.Answer.status(200, "").header("ETag", "\"etag-2\"").after(300);
      }
      return part == 1 && attempt == 1 ? FakeFiles.Answer.status(429, "").header("Retry-After", "1") : null;
    };
    UploadResult result = client.transfers().upload(file("throttled.bin", bytes(30, 5)), "throttled.bin",
        UploadOptions.builder().startup(UploadStartup.AGGRESSIVE).maxConcurrency(2).build());

    long throttledAt = arrivals.get("1#1");
    assertEquals(arrivals.keySet().toString(), 7, arrivals.size());
    for (java.util.Map.Entry<String, Long> arrival : arrivals.entrySet()) {
      long after = TimeUnit.NANOSECONDS.toMillis(arrival.getValue() - throttledAt);
      assertTrue(arrival.getKey() + " was sent " + after + " ms after the throttling answer, during its pause",
          "1#1".equals(arrival.getKey()) || "2#1".equals(arrival.getKey()) || after >= 950);
    }
    assertEquals(6, result.getParts().size());
    TransferConcurrency concurrency = result.getFinalProgress().getConcurrency();
    assertEquals(1, concurrency.getThrottles());
    assertTrue(concurrency.getLastDecision() != null);
  }

  @Test(timeout = 20000)
  public void aStoragePartIsNotSentAgainWhenItsRetryAfterAsksForLongerThanTheSdkWaits() throws Exception {
    files.storage = (part, attempt) -> part == 1 ? FakeFiles.Answer.status(503, "").header("Retry-After", "2147484") : null;
    try {
      client.transfers().upload(file("later.bin", bytes(12, 6)), "later.bin");
      fail("Expected the upload to fail");
    } catch (ApiErrorException.ApiConnectionException e) {
      assertTrue(e.getMessage(), e.getMessage().contains("HTTP status 503"));
    }
    assertEquals(1, files.partsNumbered(1).size());
    assertTrue(files.finishes.isEmpty());
  }

  @Test
  public void anAggressiveStartupIsRefusedForAStaticUpload() {
    try {
      UploadOptions.builder().concurrencyMode(ConcurrencyMode.STATIC).startup(UploadStartup.AGGRESSIVE).build();
      fail("Expected the options to be refused");
    } catch (IllegalStateException e) {
      assertTrue(e.getMessage(), e.getMessage().contains("aggressive"));
    }
    assertEquals(ConcurrencyMode.STATIC, UploadOptions.builder().concurrencyMode(ConcurrencyMode.STATIC).build().getConcurrencyMode());
    assertEquals(UploadStartup.DEFAULT, UploadOptions.defaults().getStartup());
    assertNull(UploadOptions.defaults().getMaxConcurrency());
  }

  // A file of 10,000 bytes on storage that serves ranges of one version, in parts of 1,000 bytes.
  private byte[] rangedFile(int seed) {
    files.contents = bytes(10_000, seed);
    files.describedSize = (long) files.contents.length;
    files.ranges = true;
    files.etag = "\"v1\"";
    return files.contents;
  }

  private static DownloadOptions.Builder inParts() {
    return DownloadOptions.builder().partSize(1000);
  }

  @Test(timeout = 20000)
  public void aDownloadBoundToAVersionFetchesItsPartsAtOnceAndPublishesTheWholeFile() throws Exception {
    byte[] contents = rangedFile(40);
    // The parts after the first are answered only once four of them are in flight together.
    CountDownLatch release = new CountDownLatch(1);
    files.rangeAnswer = (start, attempt) -> start == 0 ? null : FakeFiles.Answer.holdUntil(release);
    Path destination = file("ranged.bin", "old".getBytes(StandardCharsets.UTF_8));
    List<TransferProgress> progress = new CopyOnWriteArrayList<>();
    ExecutorService downloads = Executors.newSingleThreadExecutor();
    try {
      Future<com.files.net.DownloadResult> download = downloads.submit(() -> client.transfers().download("remote/ranged.bin", destination,
          inParts().maxConcurrency(4).progressListener(progress::add).build()));
      awaitTrue(() -> files.rangeRequests.size() >= 5);
      Thread.sleep(200);
      assertEquals("four parts at once, no more than maxConcurrency", 4, files.maxConcurrentRanges());
      release.countDown();
      com.files.net.DownloadResult result = download.get(10, TimeUnit.SECONDS);

      assertTrue(result.isVersionBound());
      assertArrayEquals(contents, Files.readAllBytes(destination));
      assertEquals(10, files.rangeRequests.size());
      assertTrue("no request for the whole file", files.downloads.isEmpty());
      assertNull("the first part binds the version, without a condition", files.rangeRequests.get(0).header("If-Match"));
      for (FakeFiles.Received range : files.rangeRequests.subList(1, files.rangeRequests.size())) {
        assertEquals("every later part is of the version", "\"v1\"", range.header("If-Match"));
        assertNull("the storage request carries no credentials", range.header("X-FilesAPI-Key"));
      }
      TransferProgress last = result.getFinalProgress();
      assertEquals(10_000, last.getTotalBytes());
      assertEquals(10_000, last.getTransferredBytes());
      assertEquals(10, last.getCompletedParts());
      assertEquals(ConcurrencyMode.AUTOMATIC, last.getConcurrency().getMode());
      assertEquals(4, last.getConcurrency().getMaxTarget());
      assertTrue("reported as parts were written", progress.stream().anyMatch(p -> p.getTransferredBytes() > 1000 && p.getTransferredBytes() < 10_000));
    } finally {
      release.countDown();
      downloads.shutdownNow();
    }
  }

  @Test(timeout = 20000)
  public void aPartCutShortIsAskedForAgainFromItsFirstByteNotYetWritten() throws Exception {
    // Every part arrives in short reads, the last of them shorter still. The part at 3000 ends after
    // three: its connection closes before the length it declared, or its body, of no declared length,
    // ends.
    files.rangeFragmentBytes = 128;
    for (boolean declared : new boolean[]{true, false}) {
      byte[] contents = rangedFile(41);
      files.rangeRequests.clear();
      AtomicBoolean cut = new AtomicBoolean();
      files.rangeAnswer = (start, attempt) -> start == 3000 && cut.compareAndSet(false, true)
          ? (declared ? FakeFiles.Answer.cutShortAfter(300) : FakeFiles.Answer.endsEarlyAfter(300)) : null;
      Path destination = folder.getRoot().toPath().resolve("resumed-" + declared + ".bin");

      com.files.net.DownloadResult result = client.transfers().download("remote/resumed.bin", destination, inParts().build());

      String what = declared ? "connection closed" : "body ended";
      assertArrayEquals(what, contents, Files.readAllBytes(destination));
      List<String> asked = files.rangeRequests.stream().map(r -> r.header("Range")).collect(Collectors.toList());
      assertTrue(what + ": " + asked, asked.contains("bytes=3300-3999"));
      assertEquals(what + ": " + asked, 11, asked.size());
      assertEquals(what, 1, result.getFinalProgress().getRetries());
      assertEquals(what + ": no byte was received twice", 10_000, result.getFinalProgress().getSentBytes());
    }
  }

  @Test(timeout = 20000)
  public void aFileThatChangesWhileItIsDownloadedIsNotPutInPlace() throws Exception {
    // Another version, with another entity tag, from the second part on; the same, from storage that
    // ignores If-Match and answers with the version it has; and the same tag with another size.
    for (String change : new String[]{"tag", "tag, If-Match ignored", "size"}) {
      boolean sameTag = "size".equals(change);
      rangedFile(42);
      files.ifMatch = !change.contains("ignored");
      files.rangeRequests.clear();
      AtomicBoolean changed = new AtomicBoolean();
      files.rangeAnswer = (start, attempt) -> {
        if (start == 1000 && changed.compareAndSet(false, true)) {
          files.contents = bytes(sameTag ? 12_000 : 10_000, 43);
          files.etag = sameTag ? "\"v1\"" : "\"v2\"";
        }
        return null;
      };
      Path destination = file("changed.bin", "old".getBytes(StandardCharsets.UTF_8));
      try {
        client.transfers().download("remote/changed.bin", destination, inParts().concurrencyMode(ConcurrencyMode.STATIC).maxConcurrency(1).build());
        fail("Expected the changed file to fail the download");
      } catch (ApiErrorException.ApiConnectionException e) {
        assertTrue(change + ": " + e.getMessage(), e.getMessage().contains("changed while it was being downloaded"));
      }
      assertEquals("old", new String(Files.readAllBytes(destination), StandardCharsets.UTF_8));
      assertEquals(Arrays.asList("changed.bin"), directoryListing());
      assertEquals("not tried again", 2, files.rangeRequests.size());
      assertTrue(files.downloads.isEmpty());
      files.rangeAnswer = (start, attempt) -> null;
      file("changed.bin", "old".getBytes(StandardCharsets.UTF_8));
    }
  }

  @Test(timeout = 20000)
  public void storageThatBindsNoVersionGivesTheFileInOneRequest() throws Exception {
    // No entity tag, a weak one, and ranges ignored: each answer to the first part binds nothing.
    String[][] servers = {{"ranges", null}, {"ranges", "W/\"v1\""}, {"no ranges", "\"v1\""}};
    for (String[] server : servers) {
      final byte[] contents = rangedFile(44);
      files.ranges = "ranges".equals(server[0]);
      files.etag = server[1];
      files.rangeRequests.clear();
      files.downloads.clear();
      Path destination = folder.getRoot().toPath().resolve("whole-" + files.describes.size() + ".bin");

      com.files.net.DownloadResult result = client.transfers().download("remote/whole.bin", destination, inParts().build());

      String what = server[0] + ", " + server[1];
      assertFalse(what, result.isVersionBound());
      assertArrayEquals(what, contents, Files.readAllBytes(destination));
      assertEquals(what, 1, files.downloads.stream().filter(r -> r.header("Range") == null).count());
      assertEquals(what, files.ranges ? 1 : 0, files.rangeRequests.size());
    }
  }

  @Test(timeout = 20000)
  public void aBoundPartAnsweredWithTheWholeFileStartsTheDownloadOverInOneRequest() throws Exception {
    byte[] contents = rangedFile(45);
    files.rangeAnswer = (start, attempt) -> start == 5000 ? FakeFiles.Answer.status(200, "the whole file") : null;
    Path destination = folder.getRoot().toPath().resolve("ignored.bin");

    com.files.net.DownloadResult result = client.transfers().download("remote/ignored.bin", destination, inParts().build());

    assertFalse(result.isVersionBound());
    assertArrayEquals(contents, Files.readAllBytes(destination));
    assertEquals(1, files.downloads.size());
  }

  @Test(timeout = 20000)
  public void aDownloadUrlAboutToExpireIsRenewedForTheSameStorageResource() throws Exception {
    byte[] contents = rangedFile(46);
    String signed = java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'").withZone(java.time.ZoneOffset.UTC)
        .format(Instant.now());
    // Each URL Files.com gives expires within the 30 seconds the SDK renews URLs before.
    files.downloadQuery = "&X-Amz-Date=" + signed + "&X-Amz-Expires=20";
    Path destination = folder.getRoot().toPath().resolve("renewed.bin");

    com.files.net.DownloadResult result = client.transfers().download("remote/renewed.bin", destination, inParts().build());

    assertTrue(result.isVersionBound());
    assertArrayEquals(contents, Files.readAllBytes(destination));
    assertTrue("renewed: " + files.describes.size(), files.describes.size() > 2);

    // A renewed URL for another storage resource stops the download.
    files.rangeRequests.clear();
    files.rangeAnswer = (start, attempt) -> {
      if (start == 0) {
        files.downloadResource = files.root() + "/download/elsewhere.bin";
      }
      return null;
    };
    Path other = file("moved.bin", "old".getBytes(StandardCharsets.UTF_8));
    try {
      client.transfers().download("remote/moved.bin", other, inParts().build());
      fail("Expected the download to stop");
    } catch (ApiErrorException.ApiConnectionException e) {
      assertTrue(e.getMessage(), e.getMessage().contains("another storage resource"));
    }
    assertEquals("old", new String(Files.readAllBytes(other), StandardCharsets.UTF_8));
    assertEquals(1, files.rangeRequests.size());
  }

  @Test(timeout = 20000)
  public void uploadsInOneScopeShareOneTargetRatherThanEachHavingAllOfIt() throws Exception {
    CountDownLatch arrived = new CountDownLatch(4);
    CountDownLatch release = holdParts(arrived, new CountDownLatch(1));
    ExecutorService uploads = Executors.newFixedThreadPool(2);
    try (TransferScope job = new TransferScope()) {
      List<Future<UploadResult>> results = new ArrayList<>();
      for (int i = 0; i < 2; i++) {
        Path source = file("job-" + i + ".bin", bytes(30, 50 + i));
        results.add(uploads.submit(() -> client.transfers().upload(source, source.getFileName().toString(),
            UploadOptions.builder().startup(UploadStartup.AGGRESSIVE).maxConcurrency(4).scope(job).build())));
      }
      assertTrue(arrived.await(10, TimeUnit.SECONDS));
      Thread.sleep(300);
      assertEquals("four parts at once for the two uploads together", 4, files.maxConcurrentParts());
      release.countDown();
      for (Future<UploadResult> result : results) {
        TransferConcurrency concurrency = result.get(10, TimeUnit.SECONDS).getFinalProgress().getConcurrency();
        assertTrue(concurrency.isShared());
        assertEquals(4, concurrency.getTarget());
      }
    } finally {
      release.countDown();
      uploads.shutdownNow();
    }
  }

  @Test(timeout = 20000)
  public void whatOneUploadOfAScopeLearnsTheNextStartsFromAndNoOtherUploadDoes() throws Exception {
    UploadOptions.Builder options = UploadOptions.builder().startup(UploadStartup.AGGRESSIVE).maxConcurrency(20);
    try (TransferScope job = new TransferScope()) {
      files.storage = (part, attempt) -> part == 1 && attempt == 1 ? FakeFiles.Answer.status(429, "").header("Retry-After", "0") : null;
      UploadResult throttled = client.transfers().upload(file("first.bin", bytes(10, 52)), "first.bin", options.scope(job).build());
      assertEquals("lowered by 10% after throttling", 18, throttled.getFinalProgress().getConcurrency().getTarget());
      files.storage = (part, attempt) -> null;

      UploadResult next = client.transfers().upload(file("next.bin", bytes(10, 53)), "next.bin", options.scope(job).build());
      assertEquals("the next upload of the scope starts where the first left the target", 18,
          next.getFinalProgress().getConcurrency().getTarget());
      UploadResult alone = client.transfers().upload(file("alone.bin", bytes(10, 54)), "alone.bin", options.scope(null).build());
      assertEquals("an upload of no scope starts afresh", 20, alone.getFinalProgress().getConcurrency().getTarget());
      assertFalse(alone.getFinalProgress().getConcurrency().isShared());
    }
  }

  // Uploads of the scope, each of a file of three parts, from callers of their own.
  private List<Future<UploadResult>> uploadAll(ExecutorService callers, TransferScope job, String prefix, int count, UploadStartup startup)
      throws IOException {
    List<Future<UploadResult>> results = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      Path source = file(prefix + i + ".bin", bytes(15, 70 + i));
      results.add(callers.submit(() -> client.transfers().upload(source, source.getFileName().toString(),
          UploadOptions.builder().startup(startup).scope(job).build())));
    }
    return results;
  }

  // How many uploads named with prefix Files.com was asked to begin, whatever parts each asked for.
  private long begun(String prefix) {
    return files.begins.stream().map(request -> request.rawPath).filter(path -> path.contains(prefix)).distinct().count();
  }

  @Test(timeout = 30000)
  public void defaultStartupUploadsOfAScopeBeginAFewFilesAtATimeEvenBesideAnAggressiveOne() throws Exception {
    CountDownLatch release = new CountDownLatch(1);
    files.storage = (part, attempt) -> FakeFiles.Answer.holdUntil(release);
    ExecutorService callers = Executors.newFixedThreadPool(17);
    try (TransferScope job = new TransferScope()) {
      // An aggressive upload of the scope already sends its parts at a target of its own, which
      // admits no upload of the default startup.
      List<Future<UploadResult>> results = new ArrayList<>(uploadAll(callers, job, "aggressive-", 1, UploadStartup.AGGRESSIVE));
      awaitTrue(() -> files.maxConcurrentParts() == 3);
      results.addAll(uploadAll(callers, job, "default-", 16, UploadStartup.DEFAULT));
      // Six may begin before their shared target exists, four once it does, at one part at a time.
      awaitTrue(() -> begun("default-") + TransferScopeTestAccess.queuedUploads(job) == 16);
      long queuedSince = System.nanoTime();
      long first = begun("default-");
      assertTrue("a few began, not 16: " + first, first >= 4 && first <= 6);
      Thread.sleep(500);
      assertEquals("no more while their parts were waiting", first, begun("default-"));

      // Most of them begin only once the parts are released: their time includes the wait for their place.
      long held = System.nanoTime() - queuedSince;
      release.countDown();
      for (Future<UploadResult> result : results) {
        UploadResult uploaded = result.get(20, TimeUnit.SECONDS);
        assertEquals(15, uploaded.getSize());
        assertTrue(uploaded.getPath() + " took " + uploaded.getFinalProgress().getElapsed(),
            uploaded.getFinalProgress().getElapsed().toNanos() >= held);
      }
      assertEquals(16, begun("default-"));
      assertEquals(0, TransferScopeTestAccess.uploadsInProgress(job));
    } finally {
      release.countDown();
      callers.shutdownNow();
    }
  }

  @Test(timeout = 30000)
  public void aggressiveUploadsOfAScopeAllBeginAtOnce() throws Exception {
    CountDownLatch release = new CountDownLatch(1);
    files.storage = (part, attempt) -> FakeFiles.Answer.holdUntil(release);
    ExecutorService callers = Executors.newFixedThreadPool(16);
    try (TransferScope job = new TransferScope()) {
      List<Future<UploadResult>> results = uploadAll(callers, job, "aggressive-", 16, UploadStartup.AGGRESSIVE);
      awaitTrue(() -> begun("aggressive-") == 16);
      assertEquals(0, TransferScopeTestAccess.queuedUploads(job));

      release.countDown();
      for (Future<UploadResult> result : results) {
        assertEquals(15, result.get(20, TimeUnit.SECONDS).getSize());
      }
    } finally {
      release.countDown();
      callers.shutdownNow();
    }
  }

  @Test(timeout = 30000)
  public void anUploadCancelledWhileWaitingForItsPlaceAsksFilesComNothingAndKeepsNoPlace() throws Exception {
    CountDownLatch release = new CountDownLatch(1);
    files.storage = (part, attempt) -> FakeFiles.Answer.holdUntil(release);
    ExecutorService callers = Executors.newFixedThreadPool(7);
    try (TransferScope job = new TransferScope()) {
      final List<Future<UploadResult>> results = uploadAll(callers, job, "held-", 6, UploadStartup.DEFAULT);
      awaitTrue(() -> begun("held-") + TransferScopeTestAccess.queuedUploads(job) == 6);
      final int queuedBefore = TransferScopeTestAccess.queuedUploads(job);
      CancellationToken token = new CancellationToken();
      Path queued = file("queued.bin", bytes(15, 90));
      Future<UploadResult> waiting = callers.submit(() -> client.transfers().upload(queued, "queued.bin",
          UploadOptions.builder().scope(job).cancellation(token).build()));
      awaitTrue(() -> TransferScopeTestAccess.queuedUploads(job) == queuedBefore + 1);
      token.cancel();
      try {
        waiting.get(10, TimeUnit.SECONDS);
        fail("Expected the waiting upload to be cancelled");
      } catch (java.util.concurrent.ExecutionException e) {
        assertTrue(String.valueOf(e.getCause()), e.getCause() instanceof OperationCancelledException);
      }
      assertEquals(queuedBefore, TransferScopeTestAccess.queuedUploads(job));
      assertEquals("Files.com was asked nothing for it", 0, begun("queued"));

      release.countDown();
      for (Future<UploadResult> result : results) {
        assertEquals(15, result.get(20, TimeUnit.SECONDS).getSize());
      }
      assertEquals(0, TransferScopeTestAccess.uploadsInProgress(job));
      assertEquals(15, client.transfers().upload(file("later.bin", bytes(15, 91)), "later.bin", UploadOptions.builder().scope(job).build())
          .getSize());
    } finally {
      release.countDown();
      callers.shutdownNow();
    }
  }

  @Test(timeout = 30000)
  public void anUploadThatFailsToBeginOrToCompleteGivesItsPlaceBack() throws Exception {
    String refusal = "{\"type\":\"processing-failure/file-too-large\",\"http-code\":422,\"error\":\"Too large\"}";
    files.beginAnswer = request -> request.rawPath.contains("unplanned") ? FakeFiles.Answer.status(422, refusal) : null;
    files.finish = request -> request.rawPath.contains("unfinished") ? FakeFiles.Answer.status(422, refusal) : null;
    try (TransferScope job = new TransferScope()) {
      UploadOptions options = UploadOptions.builder().scope(job).build();
      // More failures, one after another, than the scope has places for.
      for (int i = 0; i < 7; i++) {
        for (String name : new String[]{"unplanned-" + i + ".bin", "unfinished-" + i + ".bin"}) {
          try {
            client.transfers().upload(file(name, bytes(15, 92)), name, options);
            fail("Expected " + name + " to be refused");
          } catch (ApiErrorException e) {
            assertEquals(name, 422, e.getHttpCode());
          }
        }
      }
      assertEquals(0, TransferScopeTestAccess.uploadsInProgress(job));
      assertEquals(15, client.transfers().upload(file("planned.bin", bytes(15, 93)), "planned.bin", options).getSize());
    }
  }

  @Test(timeout = 20000)
  public void downloadsInOneScopeShareOneTarget() throws Exception {
    rangedFile(55);
    CountDownLatch release = new CountDownLatch(1);
    CountDownLatch secondBinds = new CountDownLatch(1);
    // The parts of the download that binds a version first are held. The other's first range, which
    // binds its version and is asked for alone, outside the target, is answered only once four parts
    // are held: the other download then binds while the target is full.
    files.rangeAnswer = (start, attempt) -> start != 0 ? FakeFiles.Answer.holdUntil(release)
        : attempt == 1 ? null : FakeFiles.Answer.holdUntil(secondBinds);
    ExecutorService downloads = Executors.newFixedThreadPool(2);
    try (TransferScope job = new TransferScope()) {
      List<Future<com.files.net.DownloadResult>> results = new ArrayList<>();
      for (int i = 0; i < 2; i++) {
        Path destination = folder.getRoot().toPath().resolve("shared-" + i + ".bin");
        results.add(downloads.submit(() -> client.transfers().download("remote/shared.bin", destination,
            inParts().maxConcurrency(4).scope(job).build())));
      }
      awaitTrue(() -> files.rangeRequests.stream().filter(request -> request.header("If-Match") != null).count() >= 4);
      secondBinds.countDown();
      Thread.sleep(300);
      assertEquals("four parts at once for the two downloads together", 4, files.maxConcurrentBoundRanges());
      release.countDown();
      for (Future<com.files.net.DownloadResult> result : results) {
        assertTrue(result.get(10, TimeUnit.SECONDS).getFinalProgress().getConcurrency().isShared());
      }
    } finally {
      release.countDown();
      downloads.shutdownNow();
    }
  }

  @Test(timeout = 20000)
  public void aClosedScopeTakesNoNewTransferAndSendsNothingForIt() throws Exception {
    TransferScope job = new TransferScope();
    job.close();
    try {
      client.transfers().upload(file("late.bin", bytes(10, 56)), "late.bin", UploadOptions.builder().scope(job).build());
      fail("Expected the closed scope to refuse the upload");
    } catch (IllegalStateException e) {
      assertTrue(e.getMessage(), e.getMessage().contains("closed"));
    }
    assertTrue(files.begins.isEmpty());
    assertTrue(files.parts.isEmpty());
  }

  @Test(timeout = 20000)
  public void aDownloadWhoseStorageSizeIsNotTheDescribedSizeFailsBeforeWritingAndIsNotTriedAgain() throws Exception {
    // Files.com describes 12,000 bytes; storage serves 10,000, whether or not it binds a version.
    for (String etag : new String[]{"\"v1\"", "W/\"v1\""}) {
      rangedFile(61);
      files.etag = etag;
      files.describedSize = 12_000L;
      files.rangeRequests.clear();
      files.downloads.clear();
      Path destination = file("sized.bin", "old".getBytes(StandardCharsets.UTF_8));
      try {
        client.transfers().download("remote/sized.bin", destination, inParts().build());
        fail("Expected the size mismatch to fail the download");
      } catch (ApiErrorException.ApiConnectionException e) {
        assertTrue(etag + ": " + e.getMessage(), e.getMessage().contains("has 10000 bytes, but Files.com described it with 12000"));
      }
      assertEquals("old", new String(Files.readAllBytes(destination), StandardCharsets.UTF_8));
      assertEquals("nothing of the download is left", Arrays.asList("sized.bin"), directoryListing());
      assertEquals(etag + ": not tried again", 1, files.rangeRequests.size());
      assertTrue(etag + ": not fetched in one request instead", files.downloads.isEmpty());
    }
  }

  @Test(timeout = 20000)
  public void closingAScopeAfterATransferStartedLetsTheTransferFinish() throws Exception {
    // The scope closes while Files.com answers begin_upload, or storage the download's first part:
    // after each transfer started, before it would join the scope's shared target.
    TransferScope uploads = new TransferScope();
    files.partEditor = part -> {
      uploads.close();
      return part;
    };
    UploadResult uploaded = client.transfers().upload(file("closing.bin", bytes(12, 62)), "closing.bin",
        UploadOptions.builder().scope(uploads).build());
    assertEquals(3, uploaded.getParts().size());
    assertFalse(uploaded.getFinalProgress().getConcurrency().isShared());
    assertEquals(1, files.finishes.size());

    byte[] contents = rangedFile(63);
    TransferScope downloads = new TransferScope();
    files.rangeAnswer = (start, attempt) -> {
      downloads.close();
      return null;
    };
    Path destination = folder.getRoot().toPath().resolve("closing.bin");
    com.files.net.DownloadResult downloaded = client.transfers().download("remote/closing.bin", destination,
        inParts().scope(downloads).build());
    assertTrue(downloaded.isVersionBound());
    assertArrayEquals(contents, Files.readAllBytes(destination));
    assertFalse(downloaded.getFinalProgress().getConcurrency().isShared());

    // A transfer that starts once the scope is closed is still refused before it sends anything.
    try {
      client.transfers().download("remote/closing.bin", folder.getRoot().toPath().resolve("late.bin"), inParts().scope(downloads).build());
      fail("Expected the closed scope to refuse the download");
    } catch (IllegalStateException e) {
      assertTrue(e.getMessage(), e.getMessage().contains("closed"));
    }

    // An upload waiting for its place when the scope closes is still admitted, once a place is free.
    files.partEditor = part -> part;
    CountDownLatch release = new CountDownLatch(1);
    files.storage = (part, attempt) -> FakeFiles.Answer.holdUntil(release);
    TransferScope queueing = new TransferScope();
    ExecutorService callers = Executors.newFixedThreadPool(7);
    try {
      List<Future<UploadResult>> results = uploadAll(callers, queueing, "before-close-", 7, UploadStartup.DEFAULT);
      awaitTrue(() -> begun("before-close-") + TransferScopeTestAccess.queuedUploads(queueing) == 7
          && TransferScopeTestAccess.queuedUploads(queueing) >= 1);
      queueing.close();
      try {
        client.transfers().upload(file("after-close.bin", bytes(15, 94)), "after-close.bin", UploadOptions.builder().scope(queueing).build());
        fail("Expected the closed scope to refuse the upload");
      } catch (IllegalStateException e) {
        assertTrue(e.getMessage(), e.getMessage().contains("closed"));
      }
      release.countDown();
      for (Future<UploadResult> result : results) {
        assertEquals(15, result.get(20, TimeUnit.SECONDS).getSize());
      }
      assertEquals(7, begun("before-close-"));
      assertEquals(0, begun("after-close"));
    } finally {
      release.countDown();
      callers.shutdownNow();
    }
  }

  // A pool of the caller's whose connections take their time to lease, with the storage hosts
  // storage-a.test and storage-b.test resolving to the fake server. Waiting 100 ms for a lease, with no
  // retries, fails any transfer that ever waits for one while a range takes 150 ms.
  private PoolingHttpClientConnectionManager callerPool(int defaultPerRoute, int total) {
    DnsResolver storageHosts = host -> host.endsWith(".test") ? new java.net.InetAddress[]{java.net.InetAddress.getLoopbackAddress()}
        : SystemDefaultDnsResolver.INSTANCE.resolve(host);
    PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager(
        RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.getSocketFactory()).build(),
        null, null, storageHosts, -1, TimeUnit.MILLISECONDS);
    pool.setDefaultMaxPerRoute(defaultPerRoute);
    pool.setMaxTotal(total);
    return pool;
  }

  private FilesClient leaseIntolerantClient(PoolingHttpClientConnectionManager pool) {
    return FilesClient.builder()
        .apiKey("transfer-key")
        .apiRoot(files.root())
        .maxRetries(0)
        .connectionAcquisitionTimeout(Duration.ofMillis(100))
        .transport(HttpTransport.builder().connectionManager(pool))
        .build();
  }

  @Test(timeout = 30000)
  public void aCallersLowerLimitForTheStorageHostsRouteHoldsTheDownloadsToIt() throws Exception {
    byte[] contents = rangedFile(64);
    files.downloadUriEditor = uri -> uri.replace("127.0.0.1", "storage-a.test");
    files.rangeAnswer = (start, attempt) -> FakeFiles.Answer.delayed(150);
    PoolingHttpClientConnectionManager pool = callerPool(20, 100);
    pool.setMaxPerRoute(new HttpRoute(new HttpHost("storage-a.test", files.port(), "http")), 1);
    Path destination = folder.getRoot().toPath().resolve("narrow.bin");
    CountDownLatch release = new CountDownLatch(1);
    ExecutorService downloads = Executors.newFixedThreadPool(2);
    try (FilesClient narrow = leaseIntolerantClient(pool)) {
      com.files.net.DownloadResult result = narrow.transfers().download("remote/narrow.bin", destination,
          inParts().concurrencyMode(ConcurrencyMode.STATIC).maxConcurrency(8).build());

      assertTrue(result.isVersionBound());
      assertArrayEquals(contents, Files.readAllBytes(destination));
      assertEquals("one range at a time, as the pool's limit for the route", 1, files.maxConcurrentRanges());

      // One download's parts hold the route's one connection. Another download to the same storage
      // host then asks for its first range, which binds its version: it waits its turn at the
      // transport, rather than for a connection from the pool, which it would give up after 100 ms.
      files.rangeAnswer = (start, attempt) -> start == 0 ? null : FakeFiles.Answer.holdUntil(release);
      int earlier = files.rangeRequests.size();
      Future<com.files.net.DownloadResult> holding = downloads.submit(() -> narrow.transfers().download("remote/holding.bin",
          folder.getRoot().toPath().resolve("holding.bin"), inParts().concurrencyMode(ConcurrencyMode.STATIC).maxConcurrency(8).build()));
      awaitTrue(() -> files.rangeRequests.subList(earlier, files.rangeRequests.size()).stream()
          .anyMatch(request -> request.header("If-Match") != null));
      assertFalse("its part is held", holding.isDone());
      int ranges = files.rangeRequests.size();
      Future<com.files.net.DownloadResult> waiting = downloads.submit(() -> narrow.transfers().download("remote/waiting.bin",
          folder.getRoot().toPath().resolve("waiting.bin"), inParts().concurrencyMode(ConcurrencyMode.STATIC).maxConcurrency(8).build()));
      awaitTrue(() -> files.describes.stream().anyMatch(request -> request.rawPath.contains("waiting.bin")));
      try {
        waiting.get(1, TimeUnit.SECONDS);
        fail("Expected the second download to wait while the route is full");
      } catch (TimeoutException e) {
        // Still waiting, ten times as long as its pool would let it wait for a connection.
      }
      assertEquals("nothing more reached storage meanwhile", ranges, files.rangeRequests.size());

      release.countDown();
      assertTrue(holding.get(20, TimeUnit.SECONDS).isVersionBound());
      assertTrue(waiting.get(20, TimeUnit.SECONDS).isVersionBound());
      assertArrayEquals(contents, Files.readAllBytes(folder.getRoot().toPath().resolve("waiting.bin")));
      assertEquals("still one storage request at a time on the route", 1, files.maxConcurrentRanges());
    } finally {
      release.countDown();
      downloads.shutdownNow();
      pool.shutdown();
    }
  }

  @Test(timeout = 30000)
  public void downloadsFromTwoStorageHostsTogetherStayWithinTheCallersPoolTotal() throws Exception {
    byte[] contents = rangedFile(65);
    // Each storage host may have 2 connections, but the pool has 3 in all. The first parts are
    // answered once both downloads have asked for theirs, so both have been described before any part
    // after the first is fetched, and no API request needs a connection meanwhile.
    files.downloadUriEditor = uri -> uri.replace("127.0.0.1", uri.contains("first.bin") ? "storage-a.test" : "storage-b.test");
    CountDownLatch bothBound = new CountDownLatch(2);
    files.rangeAnswer = (start, attempt) -> {
      if (start == 0) {
        bothBound.countDown();
        return FakeFiles.Answer.holdUntil(bothBound);
      }
      return FakeFiles.Answer.delayed(150);
    };
    PoolingHttpClientConnectionManager pool = callerPool(2, 3);
    ExecutorService downloads = Executors.newFixedThreadPool(2);
    try (FilesClient narrow = leaseIntolerantClient(pool)) {
      List<Future<com.files.net.DownloadResult>> results = new ArrayList<>();
      for (String name : new String[]{"first.bin", "second.bin"}) {
        Path destination = folder.getRoot().toPath().resolve(name);
        results.add(downloads.submit(() -> narrow.transfers().download("remote/" + name, destination,
            inParts().concurrencyMode(ConcurrencyMode.STATIC).maxConcurrency(8).build())));
      }
      for (Future<com.files.net.DownloadResult> result : results) {
        assertTrue(result.get(20, TimeUnit.SECONDS).isVersionBound());
      }
      assertArrayEquals(contents, Files.readAllBytes(folder.getRoot().toPath().resolve("first.bin")));
      assertArrayEquals(contents, Files.readAllBytes(folder.getRoot().toPath().resolve("second.bin")));
      // Their four first ranges after the binding ones ask together: three go, one waits its turn.
      assertEquals("the pool's whole total, no more", 3, files.maxConcurrentRanges());
    } finally {
      downloads.shutdownNow();
      pool.shutdown();
    }
  }

  @Test(timeout = 30000)
  public void smallDownloadsSharingALimiterWaitForItsRequestSlotAndAWaitingOneCanBeCancelled() throws Exception {
    // Files small enough to come in one request each, and a limiter with one request slot; the
    // client's own pool would lease many more connections to the storage host. The first download's
    // body stops after its first bytes until it is released.
    String text = "the contents of a small file, fetched in one request";
    byte[] contents = text.getBytes(StandardCharsets.UTF_8);
    files.contents = contents;
    files.describedSize = (long) contents.length;
    CountDownLatch release = new CountDownLatch(1);
    files.download = attempt -> attempt == 1 ? FakeFiles.Answer.bodyHeldAfter(200, text, 8, release) : null;
    TransferLimiter limiter = new TransferLimiter(1, 1024 * 1024);
    Path root = folder.getRoot().toPath();
    ExecutorService downloads = Executors.newFixedThreadPool(2);
    try {
      final Future<com.files.net.DownloadResult> first = downloads.submit(() -> client.transfers().download("remote/first.bin",
          root.resolve("first.bin"), DownloadOptions.builder().limiter(limiter).build()));
      awaitTrue(() -> files.downloads.size() == 1);
      assertEquals("the first holds the request slot", 1, limiter.getActiveRequests());
      assertEquals("and its copy buffer's space", 64 * 1024, limiter.getBufferedBytes());

      CancellationToken token = new CancellationToken();
      final Future<com.files.net.DownloadResult> second = downloads.submit(() -> client.transfers().download("remote/second.bin",
          root.resolve("second.bin"), DownloadOptions.builder().limiter(limiter).cancellation(token).build()));
      awaitTrue(() -> limiter.getQueuedRequests() == 1);
      Thread.sleep(200);
      assertEquals("the second sends nothing while it waits", 1, files.downloads.size());
      assertEquals("holding its own copy buffer's space", 2 * 64 * 1024, limiter.getBufferedBytes());
      token.cancel();
      try {
        second.get(10, TimeUnit.SECONDS);
        fail("Expected the waiting download to be cancelled");
      } catch (java.util.concurrent.ExecutionException e) {
        assertTrue(String.valueOf(e.getCause()), e.getCause() instanceof OperationCancelledException);
      }
      assertEquals("the cancelled download gave its place up", 0, limiter.getQueuedRequests());
      assertEquals("and its buffer space", 64 * 1024, limiter.getBufferedBytes());
      assertTrue("and left nothing behind: " + directoryListing(), directoryListing().stream().noneMatch(name -> name.contains("second")));

      release.countDown();
      assertEquals(contents.length, first.get(10, TimeUnit.SECONDS).getSize());
      assertArrayEquals(contents, Files.readAllBytes(root.resolve("first.bin")));
      assertEquals(0, limiter.getActiveRequests());
      assertEquals(0, limiter.getBufferedBytes());

      // A later download with the limiter is held back by nothing the others took.
      client.transfers().download("remote/later.bin", root.resolve("later.bin"), DownloadOptions.builder().limiter(limiter).build());
      assertArrayEquals(contents, Files.readAllBytes(root.resolve("later.bin")));
    } finally {
      release.countDown();
      downloads.shutdownNow();
    }
    assertEquals("the cancelled download never reached storage", 2, files.downloads.size());
    assertEquals(0, limiter.getActiveRequests());
    assertEquals(0, limiter.getBufferedBytes());
  }

  @Test(timeout = 30000)
  public void aSmallDownloadWaitsForTheLimitersBufferSpaceBeforeItAsksStorage() throws Exception {
    String text = "another small file, fetched in one request";
    byte[] contents = text.getBytes(StandardCharsets.UTF_8);
    files.contents = contents;
    files.describedSize = (long) contents.length;
    CountDownLatch release = new CountDownLatch(1);
    files.download = attempt -> attempt == 1 ? FakeFiles.Answer.bodyHeldAfter(200, text, 8, release) : null;
    // Request slots to spare, but buffer space for one copy buffer.
    TransferLimiter limiter = new TransferLimiter(4, 64 * 1024);
    Path root = folder.getRoot().toPath();
    ExecutorService downloads = Executors.newFixedThreadPool(2);
    try {
      List<Future<com.files.net.DownloadResult>> results = new ArrayList<>();
      results.add(downloads.submit(() -> client.transfers().download("remote/first.bin", root.resolve("first.bin"),
          DownloadOptions.builder().limiter(limiter).build())));
      awaitTrue(() -> files.downloads.size() == 1);
      results.add(downloads.submit(() -> client.transfers().download("remote/second.bin", root.resolve("second.bin"),
          DownloadOptions.builder().limiter(limiter).build())));
      awaitTrue(() -> limiter.getQueuedBufferReservations() == 1);
      Thread.sleep(200);
      assertEquals("the second asks storage for nothing while it waits", 1, files.downloads.size());
      assertEquals("and holds no request slot", 1, limiter.getActiveRequests());

      release.countDown();
      for (Future<com.files.net.DownloadResult> result : results) {
        assertEquals(contents.length, result.get(10, TimeUnit.SECONDS).getSize());
      }
      assertArrayEquals(contents, Files.readAllBytes(root.resolve("second.bin")));
    } finally {
      release.countDown();
      downloads.shutdownNow();
    }
    assertEquals(2, files.downloads.size());
    assertEquals(0, limiter.getBufferedBytes());
  }

  @Test(timeout = 30000)
  public void theRangeThatBindsADownloadToAVersionWaitsForTheLimiterToo() throws Exception {
    byte[] contents = rangedFile(67);
    CountDownLatch release = new CountDownLatch(1);
    // A download of the file in one request, its part size being larger than the file, holds the
    // limiter's one request slot until it is released.
    files.download = attempt -> attempt == 1 ? FakeFiles.Answer.holdUntil(release) : null;
    TransferLimiter limiter = new TransferLimiter(1, 1024 * 1024);
    Path root = folder.getRoot().toPath();
    ExecutorService downloads = Executors.newFixedThreadPool(2);
    try {
      final Future<com.files.net.DownloadResult> whole = downloads.submit(() -> client.transfers().download("remote/whole.bin",
          root.resolve("whole.bin"), DownloadOptions.builder().limiter(limiter).build()));
      awaitTrue(() -> files.downloads.size() == 1);
      final Future<com.files.net.DownloadResult> bound = downloads.submit(() -> client.transfers().download("remote/bound.bin",
          root.resolve("bound.bin"), inParts().limiter(limiter).build()));
      awaitTrue(() -> limiter.getQueuedRequests() == 1);
      Thread.sleep(200);
      assertTrue("the range that would bind a version waits its turn", files.rangeRequests.isEmpty());

      release.countDown();
      assertFalse(whole.get(10, TimeUnit.SECONDS).isVersionBound());
      assertTrue(bound.get(10, TimeUnit.SECONDS).isVersionBound());
      assertArrayEquals(contents, Files.readAllBytes(root.resolve("bound.bin")));
    } finally {
      release.countDown();
      downloads.shutdownNow();
    }
    assertEquals("one storage request at a time, as the limiter allows", 1, files.maxConcurrentRanges());
    assertEquals(0, limiter.getBufferedBytes());
  }

  @Test(timeout = 20000)
  public void whatADownloadInOneRequestLearnsTheNextDownloadOfItsScopeStartsFrom() throws Exception {
    files.contents = bytes(100, 68);
    files.describedSize = 100L;
    Path root = folder.getRoot().toPath();
    DownloadOptions.Builder options = DownloadOptions.builder().maxConcurrency(20);
    try (TransferScope job = new TransferScope()) {
      files.download = attempt -> attempt == 1 ? FakeFiles.Answer.status(503, "").header("Retry-After", "0") : null;
      com.files.net.DownloadResult throttled = client.transfers().download("remote/first.bin", root.resolve("first.bin"),
          options.scope(job).build());
      TransferConcurrency learned = throttled.getFinalProgress().getConcurrency();
      assertTrue(learned.isShared());
      assertEquals("lowered by 10% after throttling", 18, learned.getTarget());
      assertEquals(1, learned.getThrottles());
      assertEquals(1, throttled.getFinalProgress().getRetries());
      files.download = attempt -> null;

      com.files.net.DownloadResult next = client.transfers().download("remote/next.bin", root.resolve("next.bin"), options.scope(job).build());
      assertEquals("the next download of the scope starts where the first left the target", 18,
          next.getFinalProgress().getConcurrency().getTarget());
      com.files.net.DownloadResult alone = client.transfers().download("remote/alone.bin", root.resolve("alone.bin"),
          options.scope(null).build());
      assertNull("a download in one request of no scope learns nothing", alone.getFinalProgress().getConcurrency());
    }
    assertArrayEquals(files.contents, Files.readAllBytes(root.resolve("next.bin")));
  }

  // A caller's stream that records whether it was closed.
  private static final class CountingStream extends ByteArrayInputStream {
    volatile boolean closed;

    CountingStream(byte[] contents) {
      super(contents);
    }

    @Override
    public void close() throws IOException {
      closed = true;
      super.close();
    }
  }

  @Test(timeout = 20000)
  public void aDownloadReplacesTheDestinationOnlyOnceEveryByteHasArrived() throws Exception {
    files.contents = bytes(300 * 1024, 27);
    files.describedSize = (long) files.contents.length;
    Path destination = file("download.bin", "old".getBytes(StandardCharsets.UTF_8));
    Path foreign = file(".download.bin.foreign.download", "someone else's".getBytes(StandardCharsets.UTF_8));

    client.transfers().download("remote/download.bin", destination);

    assertArrayEquals(files.contents, Files.readAllBytes(destination));
    assertEquals("the foreign file is untouched", "someone else's", new String(Files.readAllBytes(foreign), StandardCharsets.UTF_8));
    assertEquals(Arrays.asList(foreign.getFileName().toString(), destination.getFileName().toString()), directoryListing());
    assertNull("the storage request carries no credentials", files.downloads.get(0).header("X-FilesAPI-Key"));
  }

  @Test(timeout = 20000)
  public void aDownloadedFileGetsTheSamePermissionsAsAnyNewFile() throws Exception {
    org.junit.Assume.assumeTrue(folder.getRoot().toPath().getFileSystem().supportedFileAttributeViews().contains("posix"));
    files.contents = bytes(10, 32);
    Path destination = folder.getRoot().toPath().resolve("fresh.bin");
    client.transfers().download("remote/fresh.bin", destination);
    Path ordinary = Files.createFile(folder.getRoot().toPath().resolve("ordinary.bin"));
    assertEquals(Files.getPosixFilePermissions(ordinary), Files.getPosixFilePermissions(destination));
  }

  @Test(timeout = 20000)
  public void anIncompleteOrWrongDownloadNeverReplacesTheDestination() throws Exception {
    files.contents = bytes(1000, 28);
    Path destination = file("kept.bin", "old".getBytes(StandardCharsets.UTF_8));
    FakeFiles.Answer truncated = new FakeFiles.Answer();
    truncated.truncateTo = 500;
    files.download = attempt -> truncated;
    try {
      client.transfers().download("remote/kept.bin", destination);
      fail("Expected the short download to fail");
    } catch (ApiErrorException.ApiConnectionException e) {
      assertTrue(e.getMessage(), e.getMessage().contains("kept.bin"));
    }
    assertEquals("every retry started over", 4, files.downloads.size());
    assertEquals("old", new String(Files.readAllBytes(destination), StandardCharsets.UTF_8));

    files.download = attempt -> FakeFiles.Answer.status(200, "<html>app</html>").header("X-Files-Frontend-App", "1");
    try {
      client.transfers().download("remote/kept.bin", destination);
      fail("Expected the web application answer to fail the download");
    } catch (ApiErrorException.ApiConnectionException e) {
      assertTrue(e.getMessage(), e.getMessage().contains("web application"));
    }
    files.download = attempt -> null;
    files.describedSize = 999L;
    try {
      client.transfers().download("remote/kept.bin", destination);
      fail("Expected the size mismatch to fail the download");
    } catch (ApiErrorException.ApiConnectionException e) {
      assertTrue(e.getMessage(), e.getMessage().contains("described it with 999"));
    }
    assertEquals("old", new String(Files.readAllBytes(destination), StandardCharsets.UTF_8));
    assertEquals(Arrays.asList("kept.bin"), directoryListing());
  }

  @Test(timeout = 20000)
  public void aDownloadThatMayNotReplaceKeepsTheExistingFile() throws Exception {
    files.contents = bytes(10, 29);
    Path destination = file("existing.bin", "old".getBytes(StandardCharsets.UTF_8));
    try {
      client.transfers().download("remote/existing.bin", destination, DownloadOptions.builder().replaceExisting(false).build());
      fail("Expected the existing file to be kept");
    } catch (FileAlreadyExistsException e) {
      assertEquals("old", new String(Files.readAllBytes(destination), StandardCharsets.UTF_8));
    }
    assertEquals(Arrays.asList("existing.bin"), directoryListing());
  }

  @Test(timeout = 20000)
  public void aDownloadThatMayNotReplaceKeepsAFileThatAppearsMeanwhile() throws Exception {
    CountDownLatch release = new CountDownLatch(1);
    files.download = attempt -> FakeFiles.Answer.bodyHeldAfter(200, "PARTDONE", 4, release);
    Path destination = folder.getRoot().toPath().resolve("appearing.bin");
    ExecutorService pool = Executors.newSingleThreadExecutor();
    try {
      Future<?> download = pool.submit(() -> client.transfers().download(file(), destination,
          DownloadOptions.builder().replaceExisting(false).build()));
      awaitTrue(() -> stagedBytes() == 4);
      Files.write(destination, "theirs".getBytes(StandardCharsets.UTF_8));
      release.countDown();
      try {
        download.get(5, TimeUnit.SECONDS);
        fail("Expected the file that appeared to be kept");
      } catch (java.util.concurrent.ExecutionException e) {
        assertTrue(String.valueOf(e.getCause()), e.getCause() instanceof FileAlreadyExistsException);
      }
    } finally {
      release.countDown();
      pool.shutdownNow();
    }
    assertEquals("theirs", new String(Files.readAllBytes(destination), StandardCharsets.UTF_8));
    assertEquals(Arrays.asList("appearing.bin"), directoryListing());

    files.download = attempt -> null;
    files.contents = "FRESH".getBytes(StandardCharsets.UTF_8);
    Path fresh = folder.getRoot().toPath().resolve("fresh.bin");
    client.transfers().download(file(), fresh, DownloadOptions.builder().replaceExisting(false).build());
    assertEquals("FRESH", new String(Files.readAllBytes(fresh), StandardCharsets.UTF_8));
    if (posix()) {
      Path ordinary = Files.createFile(folder.getRoot().toPath().resolve("ordinary.bin"));
      assertEquals(Files.getPosixFilePermissions(ordinary), Files.getPosixFilePermissions(fresh));
    }
  }

  @Test(timeout = 20000)
  public void aCancelledDownloadRemovesOnlyItsOwnFile() throws Exception {
    files.contents = bytes(10, 30);
    CountDownLatch never = new CountDownLatch(1);
    files.download = attempt -> FakeFiles.Answer.holdUntil(never);
    Path destination = file("cancelled.bin", "old".getBytes(StandardCharsets.UTF_8));
    CancellationToken token = new CancellationToken();
    ExecutorService canceller = Executors.newSingleThreadExecutor();
    try {
      canceller.execute(() -> {
        awaitTrue(() -> !files.downloads.isEmpty());
        token.cancel();
      });
      client.transfers().download("remote/cancelled.bin", destination, DownloadOptions.builder().cancellation(token).build());
      fail("Expected the download to be cancelled");
    } catch (OperationCancelledException e) {
      assertEquals(OperationCancelledException.Reason.CANCELLED, e.getReason());
    } finally {
      never.countDown();
      canceller.shutdownNow();
    }
    assertEquals("old", new String(Files.readAllBytes(destination), StandardCharsets.UTF_8));
    assertEquals(Arrays.asList("cancelled.bin"), directoryListing());
  }

  @Test(timeout = 20000)
  public void aFinalRequestThatFailsAfterItsBodyWasWrittenIsNotSentAgain() throws Exception {
    CountDownLatch received = new CountDownLatch(1);
    files.finish = request -> {
      received.countDown();
      return null;
    };
    HttpRequestInterceptor failFinalAfterWriting = new FailAfterWriting("/api/rest/v1/files/", received);
    FilesClient failing = FilesClient.builder().apiKey("transfer-key").apiRoot(files.root()).retryDelay(Duration.ofMillis(1))
        .transport(HttpTransport.builder().customize(http -> http.addInterceptorLast(failFinalAfterWriting)))
        .build();
    try {
      failing.transfers().upload(file("ambiguous.bin", bytes(4, 36)), "ambiguous.bin");
      fail("Expected the outcome to be unknown");
    } catch (UploadCompletionUnknownException e) {
      assertEquals("ambiguous.bin", e.getPath());
    } finally {
      failing.close();
    }
    assertEquals("the final request was not sent again", 1, files.finishes.size());
  }

  @Test(timeout = 20000)
  public void aFilePartChangedInPlaceBetweenAttemptsIsNeverSentAgain() throws Exception {
    files.partsize = 4;
    Path source = file("mutable.bin", "AAAA".getBytes(StandardCharsets.UTF_8));
    java.nio.file.attribute.FileTime modified = Files.getLastModifiedTime(source);
    files.storage = (part, attempt) -> {
      if (attempt == 1) {
        // Same size and modification time: only the bytes show the change.
        Files.write(source, "BBBB".getBytes(StandardCharsets.UTF_8));
        Files.setLastModifiedTime(source, modified);
        return FakeFiles.Answer.status(503, "");
      }
      return null;
    };
    try {
      client.transfers().upload(source, "mutable.bin");
      fail("Expected the change to stop the upload");
    } catch (IOException e) {
      assertEquals("The file changed while it was being uploaded, so the upload was stopped", e.getMessage());
    }
    for (FakeFiles.Received part : files.parts) {
      assertEquals("only the original bytes arrived complete", "AAAA", new String(part.body, StandardCharsets.UTF_8));
    }
    assertEquals(0, files.finishes.size());
  }

  @Test(timeout = 20000)
  public void aFilePartChangedInPlaceAfterItWasReadIsNotSentEvenOnce() throws Exception {
    files.partsize = 4;
    Path source = file("mutable-first.bin", "AAAA".getBytes(StandardCharsets.UTF_8));
    java.nio.file.attribute.FileTime modified = Files.getLastModifiedTime(source);
    AtomicBoolean changed = new AtomicBoolean();
    // As the part's first request is about to be sent, once the part has been read: same size and
    // modification time, so only the bytes show the change.
    HttpRequestInterceptor changeBeforeSending = (request, context) -> {
      if (request.getRequestLine().getUri().contains("/storage/") && changed.compareAndSet(false, true)) {
        Files.write(source, "BBBB".getBytes(StandardCharsets.UTF_8));
        Files.setLastModifiedTime(source, modified);
      }
    };
    FilesClient changing = FilesClient.builder().apiKey("transfer-key").apiRoot(files.root()).retryDelay(Duration.ofMillis(1))
        .transport(HttpTransport.builder().customize(http -> http.addInterceptorLast(changeBeforeSending)))
        .build();
    try {
      changing.transfers().upload(source, "mutable-first.bin");
      fail("Expected the change to stop the upload");
    } catch (IOException e) {
      assertEquals("The file changed while it was being uploaded, so the upload was stopped", e.getMessage());
    } finally {
      changing.close();
    }
    assertTrue(changed.get());
    assertEquals("no part arrived complete", 0, files.parts.size());
    assertEquals(0, files.finishes.size());
  }

  @Test(timeout = 20000)
  public void cancellingAnUploadStopsAWaitForAPartsReplyBody() throws Exception {
    CountDownLatch release = new CountDownLatch(1);
    files.storage = (part, attempt) -> FakeFiles.Answer.bodyHeldAfter(200, "0123456789", 2, release).header("ETag", "\"held\"");
    CancellationToken token = new CancellationToken();
    ExecutorService pool = Executors.newSingleThreadExecutor();
    try {
      Future<UploadResult> upload = pool.submit(() -> client.transfers().upload(file("reply.bin", bytes(3, 37)), "reply.bin",
          UploadOptions.builder().cancellation(token).build()));
      awaitTrue(() -> !files.parts.isEmpty());
      token.cancel();
      try {
        upload.get(3, TimeUnit.SECONDS);
        fail("Expected the upload to be cancelled");
      } catch (java.util.concurrent.ExecutionException e) {
        assertTrue(String.valueOf(e.getCause()), e.getCause() instanceof OperationCancelledException);
      }
    } finally {
      release.countDown();
      pool.shutdownNow();
    }
    assertEquals(0, files.finishes.size());
  }

  @Test(timeout = 20000)
  public void closingWhileInterruptedStillWaitsForABorrowedReadAndSoDoesEveryOtherCloser() throws Exception {
    CountDownLatch reading = new CountDownLatch(1);
    CountDownLatch release = new CountDownLatch(1);
    AtomicBoolean borrowerClosed = new AtomicBoolean();
    InputStream blocking = new InputStream() {
      @Override
      public int read() {
        return -1;
      }

      @Override
      public int read(byte[] buffer, int offset, int length) throws IOException {
        reading.countDown();
        try {
          release.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
          throw new InterruptedIOException();
        }
        Arrays.fill(buffer, offset, offset + length, (byte) 'A');
        return length;
      }

      @Override
      public void close() {
        borrowerClosed.set(true);
      }
    };
    ExecutorService pool = Executors.newFixedThreadPool(3);
    try {
      Future<UploadResult> upload = pool.submit(() -> client.transfers().upload(blocking, 4, "borrowed.bin", null));
      assertTrue(reading.await(5, TimeUnit.SECONDS));
      Future<Boolean> interruptedCloser = pool.submit(() -> {
        Thread.currentThread().interrupt();
        client.close();
        return Thread.currentThread().isInterrupted();
      });
      Future<?> secondCloser = pool.submit(() -> client.close());
      Thread.sleep(300);
      assertFalse("close did not return while the borrowed read was still active", interruptedCloser.isDone());
      assertFalse("neither did a second close", secondCloser.isDone());
      assertFalse(upload.isDone());
      release.countDown();
      assertTrue("the interrupt status was kept", interruptedCloser.get(5, TimeUnit.SECONDS));
      secondCloser.get(5, TimeUnit.SECONDS);
      assertTrue("the upload had stopped when close returned", upload.isDone());
      try {
        upload.get();
        fail("Expected the upload to be cancelled");
      } catch (java.util.concurrent.ExecutionException e) {
        assertTrue(String.valueOf(e.getCause()), e.getCause() instanceof OperationCancelledException);
      }
    } finally {
      release.countDown();
      pool.shutdownNow();
    }
    assertFalse("the borrowed stream stays open", borrowerClosed.get());
    assertEquals(0, files.finishes.size());
  }

  @Test(timeout = 20000)
  public void progressListenersOfTwoUploadsCanCloseTheirClientAtTheSameTime() throws Exception {
    // A client and daemon threads of the test's own, so that were the closes to wait for each
    // other, only this test would fail.
    FilesClient closing = FilesClient.builder().apiKey("transfer-key").apiRoot(files.root()).build();
    CyclicBarrier bothReporting = new CyclicBarrier(2);
    AtomicInteger closesReturned = new AtomicInteger();
    ExecutorService pool = Executors.newFixedThreadPool(2, runnable -> {
      Thread thread = new Thread(runnable);
      thread.setDaemon(true);
      return thread;
    });
    try {
      List<Future<UploadResult>> uploads = new ArrayList<Future<UploadResult>>();
      for (String name : Arrays.asList("first.bin", "second.bin")) {
        Path source = file(name, bytes(6, name.length()));
        UploadOptions options = UploadOptions.builder().progressListener(closingOnce(closing, bothReporting, closesReturned)).build();
        uploads.add(pool.submit(() -> closing.transfers().upload(source, name, options)));
      }
      for (Future<UploadResult> upload : uploads) {
        try {
          upload.get(5, TimeUnit.SECONDS);
          fail("Expected the upload to be cancelled");
        } catch (java.util.concurrent.ExecutionException e) {
          assertTrue(String.valueOf(e.getCause()), e.getCause() instanceof OperationCancelledException);
        }
      }
      assertEquals(2, closesReturned.get());
      closing.close();
      assertEquals("an outside close waited for the upload's threads to end", Arrays.asList(), uploadThreads());
    } finally {
      pool.shutdownNow();
    }
    assertEquals(0, files.finishes.size());
  }

  // A progress listener that, when first called, waits for the other upload's first call and then
  // closes the client.
  private static TransferProgressListener closingOnce(FilesClient closing, CyclicBarrier bothReporting, AtomicInteger closesReturned) {
    AtomicBoolean called = new AtomicBoolean();
    return progress -> {
      if (called.getAndSet(true)) {
        return;
      }
      try {
        bothReporting.await(5, TimeUnit.SECONDS);
      } catch (InterruptedException | BrokenBarrierException | TimeoutException e) {
        throw new AssertionError("The other upload did not report", e);
      }
      closing.close();
      closesReturned.incrementAndGet();
    };
  }

  @Test(timeout = 20000)
  public void aDownloadTimeLimitCoversDescribingTheFileAndReadingIt() throws Exception {
    files.describeDelayMillis = 350;
    files.download = attempt -> FakeFiles.Answer.status(200, "BODY").after(350);
    Path destination = folder.getRoot().toPath().resolve("deadline.bin");
    try {
      client.transfers().download("remote/deadline.bin", destination, DownloadOptions.builder().timeout(Duration.ofMillis(550)).build());
      fail("Expected the download to time out");
    } catch (OperationCancelledException e) {
      assertEquals(OperationCancelledException.Reason.TIMED_OUT, e.getReason());
    }
    assertFalse(Files.exists(destination));
    assertEquals(Arrays.asList(), directoryListing());
  }

  @Test(timeout = 20000)
  public void cancellingADownloadStopsAReadOfItsBody() throws Exception {
    CountDownLatch release = new CountDownLatch(1);
    files.download = attempt -> FakeFiles.Answer.bodyHeldAfter(200, "PARTDONE", 4, release);
    Path destination = folder.getRoot().toPath().resolve("body.bin");
    CancellationToken token = new CancellationToken();
    ExecutorService pool = Executors.newSingleThreadExecutor();
    try {
      Future<?> download = pool.submit(() -> client.transfers().download(file(), destination,
          DownloadOptions.builder().cancellation(token).build()));
      awaitTrue(() -> stagedBytes() == 4);
      token.cancel();
      try {
        download.get(3, TimeUnit.SECONDS);
        fail("Expected the download to be cancelled");
      } catch (java.util.concurrent.ExecutionException e) {
        assertTrue(String.valueOf(e.getCause()), e.getCause() instanceof OperationCancelledException);
      }
    } finally {
      release.countDown();
      pool.shutdownNow();
    }
    assertFalse(Files.exists(destination));
    assertEquals(Arrays.asList(), directoryListing());
  }

  @Test(timeout = 20000)
  public void stagingIsPrivateAndAReplacedFileKeepsItsPermissions() throws Exception {
    org.junit.Assume.assumeTrue(posix());
    Path destination = file("private.bin", "OLD".getBytes(StandardCharsets.UTF_8));
    Files.setPosixFilePermissions(destination, PosixFilePermissions.fromString("rw-------"));
    CountDownLatch release = new CountDownLatch(1);
    files.download = attempt -> FakeFiles.Answer.bodyHeldAfter(200, "PARTDONE", 4, release);
    ExecutorService pool = Executors.newSingleThreadExecutor();
    try {
      Future<?> download = pool.submit(() -> client.transfers().download(file(), destination, null));
      awaitTrue(() -> stagedBytes() == 4);
      Path stage = stage();
      assertEquals("partial bytes are staged privately", "rw-------", PosixFilePermissions.toString(Files.getPosixFilePermissions(stage)));
      assertEquals("rwx------", PosixFilePermissions.toString(Files.getPosixFilePermissions(stage.getParent())));
      release.countDown();
      download.get(5, TimeUnit.SECONDS);
    } finally {
      release.countDown();
      pool.shutdownNow();
    }
    assertEquals("PARTDONE", new String(Files.readAllBytes(destination), StandardCharsets.UTF_8));
    assertEquals("rw-------", PosixFilePermissions.toString(Files.getPosixFilePermissions(destination)));
    assertEquals(Arrays.asList("private.bin"), directoryListing());
  }

  @Test(timeout = 20000)
  public void aReplacedStageIsNeverWrittenThroughPublishedOrDeleted() throws Exception {
    org.junit.Assume.assumeTrue(posix());
    Path destination = file("result.bin", "OLD".getBytes(StandardCharsets.UTF_8));
    Path canary = file("canary.bin", "CANARY".getBytes(StandardCharsets.UTF_8));
    CountDownLatch release = new CountDownLatch(1);
    files.download = attempt -> FakeFiles.Answer.holdUntil(release);
    files.contents = "BODY".getBytes(StandardCharsets.UTF_8);
    ExecutorService pool = Executors.newSingleThreadExecutor();
    Path stage;
    try {
      final Future<?> download = pool.submit(() -> client.transfers().download(file(), destination, null));
      awaitTrue(() -> !files.downloads.isEmpty());
      stage = stage();
      Files.delete(stage);
      Files.createSymbolicLink(stage, canary);
      release.countDown();
      try {
        download.get(5, TimeUnit.SECONDS);
        fail("Expected the replaced stage to stop the download");
      } catch (java.util.concurrent.ExecutionException e) {
        assertEquals("The download's own file was replaced before it could be published, so the destination was left as it was",
            e.getCause().getMessage());
      }
    } finally {
      release.countDown();
      pool.shutdownNow();
    }
    assertEquals("CANARY", new String(Files.readAllBytes(canary), StandardCharsets.UTF_8));
    assertEquals("OLD", new String(Files.readAllBytes(destination), StandardCharsets.UTF_8));
    assertFalse(Files.isSymbolicLink(destination));
    assertTrue("the replacement is left where it is", Files.isSymbolicLink(stage));
  }

  @Test(timeout = 20000)
  public void replacingTheStageDirectoryNeverPublishesTheReplacement() throws Exception {
    org.junit.Assume.assumeTrue(posix());
    Path destination = file("result.bin", "OLD".getBytes(StandardCharsets.UTF_8));
    CountDownLatch release = new CountDownLatch(1);
    files.download = attempt -> FakeFiles.Answer.bodyHeldAfter(200, "BODYDONE", 4, release);
    ExecutorService pool = Executors.newSingleThreadExecutor();
    Path directory;
    Path retained = folder.getRoot().toPath().resolve("retained");
    try {
      final Future<?> download = pool.submit(() -> client.transfers().download(file(), destination, null));
      awaitTrue(() -> stagedBytes() == 4);
      // Rename the stage's directory and put another in its place, as anyone who may change the
      // destination's directory could, without touching the stage itself.
      directory = stage().getParent();
      Files.move(directory, retained);
      Files.createDirectory(directory);
      Files.write(directory.resolve("download"), "EVIL".getBytes(StandardCharsets.UTF_8));
      release.countDown();
      if (holdsDirectories()) {
        // The download holds its directory open, so it still publishes its own bytes.
        download.get(5, TimeUnit.SECONDS);
        assertEquals("BODYDONE", new String(Files.readAllBytes(destination), StandardCharsets.UTF_8));
        assertEquals("nothing of the download's is left in its directory", Arrays.asList(), listing(retained));
      } else {
        try {
          download.get(5, TimeUnit.SECONDS);
          fail("Expected the replaced directory to stop the download");
        } catch (java.util.concurrent.ExecutionException e) {
          assertEquals("The download's own file was replaced before it could be published, so the destination was left as it was",
              e.getCause().getMessage());
        }
        assertEquals("OLD", new String(Files.readAllBytes(destination), StandardCharsets.UTF_8));
      }
    } finally {
      release.countDown();
      pool.shutdownNow();
    }
    assertEquals("the replacement is left as it was", "EVIL", new String(Files.readAllBytes(directory.resolve("download")),
        StandardCharsets.UTF_8));
  }

  @Test(timeout = 20000)
  public void onMacOsTheStageInheritsNoAclFromTheDestinationsDirectory() throws Exception {
    org.junit.Assume.assumeTrue(isOs("mac"));
    Path root = folder.getRoot().toPath();
    // Inheritable entries such as a shared folder might have, which macOS checks before the
    // permissions: one that lets everyone read new files and change new directories; one that keeps
    // a user out of new files only; one that new files inherit, limited to one level; and one for
    // new directories only.
    run("/bin/chmod", "+a", "everyone allow list,search,add_file,add_subdirectory,delete_child,read,write,append,delete,readattr,"
        + "writeattr,readextattr,writeextattr,readsecurity,file_inherit,directory_inherit", root.toString());
    run("/bin/chmod", "+a", "user:nobody deny read,write,append,file_inherit,only_inherit", root.toString());
    run("/bin/chmod", "+a", "user:daemon allow read,file_inherit,directory_inherit,limit_inherit", root.toString());
    run("/bin/chmod", "+a", "user:_www allow list,directory_inherit", root.toString());
    Path control = Files.createDirectory(root.resolve("control"));
    assertTrue("a new directory inherits the entry: " + acl(control), acl(control).contains("everyone"));
    // What the kernel itself gives a new file in the destination's directory.
    Path controlFile = Files.createFile(root.resolve("control.bin"));
    assertTrue("a new file inherits the entries: " + acl(controlFile), acl(controlFile).contains("deny") && acl(controlFile).contains("everyone"));
    Path destination = file("shared.bin", "OLD".getBytes(StandardCharsets.UTF_8));
    // The file to be replaced has an entry of its own as well as the inherited ones.
    run("/bin/chmod", "+a", "user:_www deny read", destination.toString());
    String replacedAcl = acl(destination);
    CountDownLatch release = new CountDownLatch(1);
    files.download = attempt -> FakeFiles.Answer.bodyHeldAfter(200, "SECRETDONE", 6, release);
    ExecutorService pool = Executors.newSingleThreadExecutor();
    try {
      final Future<?> download = pool.submit(() -> client.transfers().download(file(), destination, null));
      awaitTrue(() -> stagedBytes() == 6);
      Path stage = stage();
      assertEquals("the stage's directory has no ACL", "", acl(stage.getParent()));
      assertEquals("rwx------", PosixFilePermissions.toString(Files.getPosixFilePermissions(stage.getParent())));
      assertEquals("the unfinished file has no ACL", "", acl(stage));
      assertEquals("rw-------", PosixFilePermissions.toString(Files.getPosixFilePermissions(stage)));
      release.countDown();
      download.get(5, TimeUnit.SECONDS);
    } finally {
      release.countDown();
      pool.shutdownNow();
    }
    assertEquals("SECRETDONE", new String(Files.readAllBytes(destination), StandardCharsets.UTF_8));
    assertEquals("the replaced file's ACL is kept", replacedAcl, acl(destination));

    files.download = attempt -> null;
    files.contents = "FRESH".getBytes(StandardCharsets.UTF_8);
    Path fresh = root.resolve("fresh.bin");
    client.transfers().download(file(), fresh, null);
    assertEquals("FRESH", new String(Files.readAllBytes(fresh), StandardCharsets.UTF_8));
    assertEquals("a new file gets the entries a new file there inherits, in order", acl(controlFile), acl(fresh));
    assertEquals(Arrays.asList("control", "control.bin", "fresh.bin", "shared.bin"), directoryListing());
  }

  @Test(timeout = 30000)
  public void withoutItsNativeLibraryJnaStopsADownloadOnMacOsOrWindowsBeforeItCreatesAnything() throws Exception {
    org.junit.Assume.assumeTrue(isOs("mac") || isOs("windows"));
    Path nowhere = folder.newFolder("no-native-library").toPath();
    Path destination = folder.getRoot().toPath().resolve("unreached.bin");
    String java = Paths.get(System.getProperty("java.home"), "bin", "java").toString();
    // JNA then neither finds its native library nor extracts the one in its JAR.
    String output = run(java, "-Djna.nosys=true", "-Djna.nounpack=true", "-Djna.boot.library.path=" + nowhere,
        "-cp", System.getProperty("java.class.path"), DownloadWithoutNativeLibrary.class.getName(), destination.toString());
    assertTrue(output, output.contains("Downloading to a local path on this platform needs JNA (net.java.dev.jna:jna)"));
    assertFalse(output, output.contains("downloaded"));
    assertEquals(Arrays.asList("no-native-library"), directoryListing());
  }

  @Test(timeout = 30000)
  public void withoutTheJnaJarADownloadOnMacOsOrWindowsStopsBeforeItCreatesAnything() throws Exception {
    org.junit.Assume.assumeTrue(isOs("mac") || isOs("windows"));
    Path destination = folder.getRoot().toPath().resolve("unreached.bin");
    String launcher = Paths.get(System.getProperty("java.home"), "bin", "java").toString();
    // Surefire runs tests from a JAR that only names the class path; it gives the class path itself
    // in this property.
    String classPath = System.getProperty("surefire.test.class.path", System.getProperty("java.class.path"));
    List<String> withoutJna = Arrays.stream(classPath.split(java.io.File.pathSeparator))
        .filter(entry -> !entry.matches(".*[/\\\\]jna-[^/\\\\]*\\.jar"))
        .collect(Collectors.toList());
    assertEquals("the class path names one JNA JAR", classPath.split(java.io.File.pathSeparator).length - 1, withoutJna.size());
    String output = run(launcher, "-cp", String.join(java.io.File.pathSeparator, withoutJna), DownloadWithoutNativeLibrary.class.getName(),
        destination.toString());
    assertTrue(output, output.contains("Downloading to a local path on this platform needs JNA (net.java.dev.jna:jna)"));
    assertTrue("the missing class is named: " + output, output.contains("com.sun.jna.") || output.contains("com/sun/jna/"));
    assertFalse(output, output.contains("downloaded"));
    assertEquals(Arrays.asList(), directoryListing());
  }

  // Downloads to the path given, as the tests above run it, and prints what the download threw.
  public static final class DownloadWithoutNativeLibrary {
    public static void main(String[] arguments) {
      HashMap<String, Object> attributes = new HashMap<>();
      attributes.put("path", "remote.bin");
      attributes.put("download_uri", "http://127.0.0.1:9/never-requested");
      try (FilesClient client = FilesClient.builder().apiKey("key").build()) {
        client.transfers().download(new File(attributes), Paths.get(arguments[0]), null);
        System.out.println("downloaded");
      } catch (IOException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  @Test(timeout = 20000)
  public void onWindowsTheStageInheritsNoAceFromTheDestinationsDirectory() throws Exception {
    org.junit.Assume.assumeTrue(isOs("windows"));
    Path root = folder.getRoot().toPath();
    UserPrincipal everyone = root.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName("Everyone");
    AclFileAttributeView rootAcl = Files.getFileAttributeView(root, AclFileAttributeView.class);
    List<AclEntry> entries = new ArrayList<>(rootAcl.getAcl());
    entries.add(0, AclEntry.newBuilder().setType(AclEntryType.ALLOW).setPrincipal(everyone)
        .setPermissions(AclEntryPermission.READ_DATA, AclEntryPermission.READ_ATTRIBUTES, AclEntryPermission.WRITE_DATA,
            AclEntryPermission.APPEND_DATA, AclEntryPermission.DELETE_CHILD, AclEntryPermission.DELETE)
        .setFlags(AclEntryFlag.FILE_INHERIT, AclEntryFlag.DIRECTORY_INHERIT).build());
    rootAcl.setAcl(entries);
    Path control = Files.createDirectory(root.resolve("control"));
    assertTrue("a new directory inherits the entry", principals(control).contains(everyone));
    Path destination = root.resolve("shared.bin");
    CountDownLatch release = new CountDownLatch(1);
    files.download = attempt -> FakeFiles.Answer.bodyHeldAfter(200, "SECRETDONE", 6, release);
    ExecutorService pool = Executors.newSingleThreadExecutor();
    try {
      final Future<?> download = pool.submit(() -> client.transfers().download(file(), destination, null));
      awaitTrue(() -> stagedBytes() == 6);
      Path stage = stage();
      UserPrincipal owner = Files.getOwner(stage);
      assertEquals("only the owner may use the stage's directory", Collections.singleton(owner), principals(stage.getParent()));
      assertEquals("only the owner may open the unfinished file", Collections.singleton(owner), principals(stage));
      release.countDown();
      download.get(5, TimeUnit.SECONDS);
    } finally {
      release.countDown();
      pool.shutdownNow();
    }
    assertEquals("SECRETDONE", new String(Files.readAllBytes(destination), StandardCharsets.UTF_8));
    assertEquals(Arrays.asList("control", "shared.bin"), directoryListing());
  }

  @Test(timeout = 20000)
  public void onWindowsAReplacedFileKeepsItsAcl() throws Exception {
    org.junit.Assume.assumeTrue(isOs("windows"));
    files.contents = "NEW".getBytes(StandardCharsets.UTF_8);
    Path destination = file("kept.bin", "OLD".getBytes(StandardCharsets.UTF_8));
    UserPrincipal everyone = destination.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName("Everyone");
    AclFileAttributeView view = Files.getFileAttributeView(destination, AclFileAttributeView.class);
    List<AclEntry> entries = new ArrayList<>(view.getAcl());
    entries.add(AclEntry.newBuilder().setType(AclEntryType.ALLOW).setPrincipal(everyone).setPermissions(AclEntryPermission.READ_DATA).build());
    view.setAcl(entries);
    List<AclEntry> before = view.getAcl();
    String daclBefore = dacl(destination);

    client.transfers().download("remote/kept.bin", destination);

    assertEquals("NEW", new String(Files.readAllBytes(destination), StandardCharsets.UTF_8));
    assertEquals(before, Files.getFileAttributeView(destination, AclFileAttributeView.class).getAcl());
    // Its DACL as GetFileSecurityW reports it, entry for entry, with the flags Java's AclEntry leaves out:
    // whether each entry is marked inherited, and the DACL's P, AI and AR flags.
    assertEquals(daclBefore, dacl(destination));
  }

  private static Set<UserPrincipal> principals(Path path) throws IOException {
    return Files.getFileAttributeView(path, AclFileAttributeView.class).getAcl().stream().map(AclEntry::principal).collect(Collectors.toSet());
  }

  // The DACL of path in SDDL, as GetFileSecurityW, which the JDK's AclFileAttributeView calls on Windows,
  // reports it, with what Java's AclEntry leaves out: each entry's flags, ID (inherited) among them, and the
  // DACL's P, AI and AR flags; not the owner, group, SACL or integrity label. The SDK binds its Windows
  // functions first, so that JNA is first used as the SDK uses it, once it has decided where JNA may write
  // its native library.
  private static String dacl(Path path) throws IOException {
    Win32TestAccess.requireAvailable();
    com.sun.jna.WString name = new com.sun.jna.WString(path.toString());
    com.sun.jna.ptr.IntByReference needed = new com.sun.jna.ptr.IntByReference();
    Advapi32.INSTANCE.GetFileSecurityW(name, DACL_SECURITY_INFORMATION, null, 0, needed);
    assertTrue("GetFileSecurityW size " + path, needed.getValue() > 0);
    try (com.sun.jna.Memory descriptor = new com.sun.jna.Memory(needed.getValue())) {
      assertTrue("GetFileSecurityW " + path,
          Advapi32.INSTANCE.GetFileSecurityW(name, DACL_SECURITY_INFORMATION, descriptor, (int) descriptor.size(), needed));
      com.sun.jna.ptr.PointerByReference string = new com.sun.jna.ptr.PointerByReference();
      assertTrue("ConvertSecurityDescriptorToStringSecurityDescriptorW " + path,
          Advapi32.INSTANCE.ConvertSecurityDescriptorToStringSecurityDescriptorW(descriptor, SDDL_REVISION_1,
              DACL_SECURITY_INFORMATION, string, null));
      try {
        return string.getValue().getWideString(0);
      } finally {
        Kernel32.INSTANCE.LocalFree(string.getValue());
      }
    }
  }

  private static final int DACL_SECURITY_INFORMATION = 0x4;
  private static final int SDDL_REVISION_1 = 1;

  // The Windows API's calling convention, as the SDK binds it (Win32): stdcall only on 32-bit x86. Used
  // only when a Windows test first needs the API, so JNA is not loaded on other systems.
  private static Map<String, Object> win32Options() {
    return com.sun.jna.Platform.is64Bit() ? Collections.<String, Object>emptyMap()
        : Collections.<String, Object>singletonMap(com.sun.jna.Library.OPTION_CALLING_CONVENTION,
        com.sun.jna.win32.StdCallLibrary.STDCALL_CONVENTION);
  }

  private interface Advapi32 extends com.sun.jna.Library {
    Advapi32 INSTANCE = com.sun.jna.Native.load("advapi32", Advapi32.class, win32Options());

    boolean GetFileSecurityW(com.sun.jna.WString name, int information, com.sun.jna.Pointer descriptor, int length,
        com.sun.jna.ptr.IntByReference needed);

    boolean ConvertSecurityDescriptorToStringSecurityDescriptorW(com.sun.jna.Pointer descriptor, int revision, int information,
        com.sun.jna.ptr.PointerByReference string, com.sun.jna.Pointer length);
  }

  private interface Kernel32 extends com.sun.jna.Library {
    Kernel32 INSTANCE = com.sun.jna.Native.load("kernel32", Kernel32.class, win32Options());

    com.sun.jna.Pointer LocalFree(com.sun.jna.Pointer memory);
  }

  // The entries of the ACL of path, as macOS's ls shows them, one per line; empty if it has none.
  private static String acl(Path path) throws Exception {
    String listing = run("/bin/ls", "-led", path.toString());
    int firstLineEnd = listing.indexOf('\n');
    return firstLineEnd < 0 ? "" : listing.substring(firstLineEnd + 1).trim();
  }

  // Runs the command to its end, within ten seconds, and returns its output, failing unless it
  // succeeds.
  private static String run(String... command) throws Exception {
    Process process = new ProcessBuilder(command).redirectErrorStream(true).start();
    try (InputStream output = process.getInputStream()) {
      java.io.ByteArrayOutputStream collected = new java.io.ByteArrayOutputStream();
      byte[] buffer = new byte[8192];
      for (int read; (read = output.read(buffer)) >= 0; ) {
        collected.write(buffer, 0, read);
      }
      if (!process.waitFor(10, TimeUnit.SECONDS)) {
        throw new AssertionError("still running: " + Arrays.asList(command));
      }
      String text = new String(collected.toByteArray(), StandardCharsets.UTF_8);
      assertEquals(text, 0, process.exitValue());
      return text;
    } finally {
      process.destroyForcibly();
    }
  }

  private static boolean isOs(String prefix) {
    return System.getProperty("os.name", "").toLowerCase(Locale.ROOT).startsWith(prefix);
  }

  private boolean posix() {
    return folder.getRoot().toPath().getFileSystem().supportedFileAttributeViews().contains("posix");
  }

  // Whether downloads hold their stage's directory open: always on macOS and Windows, where they
  // reach it through the operating system, and elsewhere where Java can.
  private boolean holdsDirectories() throws IOException {
    if (isOs("mac") || isOs("windows")) {
      return true;
    }
    try (DirectoryStream<Path> entries = Files.newDirectoryStream(folder.getRoot().toPath())) {
      return entries instanceof SecureDirectoryStream;
    }
  }

  // The file of the download in progress, inside its hidden staging directory.
  private Path stage() throws IOException {
    try (Stream<Path> entries = Files.walk(folder.getRoot().toPath(), 2)) {
      return entries.filter(p -> !p.getParent().equals(folder.getRoot().toPath()) && p.getFileName().toString().equals("download"))
          .findFirst().orElseThrow(() -> new AssertionError("no stage"));
    }
  }

  private long stagedBytes() {
    try {
      return Files.size(stage());
    } catch (IOException | AssertionError e) {
      return -1;
    }
  }

  // A File as Files.com describes one, whose contents are the fake's download.
  private File file() {
    HashMap<String, Object> attributes = new HashMap<>();
    attributes.put("path", "remote.bin");
    attributes.put("download_uri", files.root() + "/download/remote.bin?" + FakeFiles.SIGNATURE);
    File described = new File(attributes);
    described.setClient(client);
    return described;
  }

  private List<String> directoryListing() throws IOException {
    return listing(folder.getRoot().toPath());
  }

  private static List<String> listing(Path directory) throws IOException {
    try (Stream<Path> entries = Files.list(directory)) {
      return entries.map(p -> p.getFileName().toString()).sorted().collect(Collectors.toList());
    }
  }

  private Path file(String name, byte[] contents) throws IOException {
    Path path = folder.getRoot().toPath().resolve(name);
    Files.write(path, contents);
    return path;
  }

  private static byte[] bytes(int length, int seed) {
    byte[] bytes = new byte[length];
    for (int i = 0; i < length; i++) {
      bytes[i] = (byte) (i * 31 + seed);
    }
    return bytes;
  }

  private static List<String> uploadThreads() {
    return Thread.getAllStackTraces().keySet().stream().map(Thread::getName)
        .filter(name -> name.startsWith("files-sdk-upload-")).collect(Collectors.toList());
  }

  private static void awaitTrue(java.util.function.BooleanSupplier condition) {
    long end = System.nanoTime() + TimeUnit.SECONDS.toNanos(10);
    while (!condition.getAsBoolean()) {
      if (System.nanoTime() > end) {
        throw new AssertionError("condition not reached");
      }
      try {
        Thread.sleep(5);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        return;
      }
    }
  }

  // Makes the first request whose path starts with a prefix fail once its whole body has been
  // written and flushed, and the server has received it: the request reached the server, though
  // the client saw only a failure.
  private static final class FailAfterWriting implements HttpRequestInterceptor {
    private final String prefix;
    private final CountDownLatch received;
    private final AtomicBoolean injected = new AtomicBoolean();

    FailAfterWriting(String prefix, CountDownLatch received) {
      this.prefix = prefix;
      this.received = received;
    }

    @Override
    public void process(org.apache.http.HttpRequest request, org.apache.http.protocol.HttpContext context) {
      if (request instanceof HttpEntityEnclosingRequest && request.getRequestLine().getUri().startsWith(prefix) && !injected.getAndSet(true)) {
        HttpEntityEnclosingRequest enclosing = (HttpEntityEnclosingRequest) request;
        enclosing.setEntity(new FailingAfterWrite(enclosing.getEntity(), received));
      }
    }
  }

  private static final class FailingAfterWrite extends HttpEntityWrapper {
    private final CountDownLatch received;

    FailingAfterWrite(org.apache.http.HttpEntity entity, CountDownLatch received) {
      super(entity);
      this.received = received;
    }

    @Override
    public void writeTo(OutputStream out) throws IOException {
      wrappedEntity.writeTo(out);
      out.flush();
      try {
        received.await(5, TimeUnit.SECONDS);
      } catch (InterruptedException e) {
        throw new InterruptedIOException();
      }
      throw new IOException("failed after the whole request was written");
    }
  }

  // A stream that hands out one to three bytes per read, and records how far it was read.
  private static final class TrickleStream extends InputStream {
    private final byte[] contents;
    int position;
    boolean closed;

    TrickleStream(byte[] contents) {
      this.contents = contents;
    }

    @Override
    public int read() {
      return position < contents.length ? contents[position++] & 0xff : -1;
    }

    @Override
    public int read(byte[] buffer, int offset, int length) {
      if (length == 0) {
        return 0;
      }
      if (position >= contents.length) {
        return -1;
      }
      int count = Math.min(Math.min(length, 1 + position % 3), contents.length - position);
      System.arraycopy(contents, position, buffer, offset, count);
      position += count;
      return count;
    }

    @Override
    public void close() {
      closed = true;
    }
  }

  private static final class ByteArrayInputStreamCollector {
    private final java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();

    void add(byte[] bytes) {
      out.write(bytes, 0, bytes.length);
    }

    byte[] bytes() {
      return out.toByteArray();
    }
  }
}
