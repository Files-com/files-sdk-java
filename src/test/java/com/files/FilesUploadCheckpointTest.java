package com.files;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

import com.fasterxml.jackson.databind.JsonNode;
import com.files.exceptions.ApiErrorException;
import com.files.exceptions.OperationCancelledException;
import com.files.exceptions.TransferCheckpointException;
import com.files.exceptions.UploadCompletionUnknownException;
import com.files.models.FileUploadPart;
import com.files.net.ConcurrencyMode;
import com.files.net.PartSizing;
import com.files.net.TransferProgress;
import com.files.net.UploadOptions;
import com.files.net.UploadResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

// Uploads that keep a checkpoint (UploadOptions.Builder.checkpointDirectory), run against FakeFiles:
// what a later run continues, what it refuses, and what it never sends again.
public class FilesUploadCheckpointTest {
  private static final int MIB = 1024 * 1024;
  // Whether the SDK keeps checkpoints here in WindowsCheckpointStore (see CheckpointStore.open).
  private static final boolean WINDOWS = System.getProperty("os.name", "").toLowerCase(Locale.ROOT).startsWith("windows");

  @Rule
  public TemporaryFolder folder = new TemporaryFolder();

  private FakeFiles files;
  private FilesClient client;
  private Path checkpoints;

  @Before
  public void setUp() throws IOException {
    files = new FakeFiles();
    client = FilesClient.builder()
        .apiKey("transfer-key")
        .workspaceId(0L)
        .apiRoot(files.root())
        .retryDelay(Duration.ofMillis(1))
        .build();
    // Created by the first upload, private to this user.
    checkpoints = folder.getRoot().toPath().resolve("checkpoints");
  }

  @After
  public void tearDown() {
    client.close();
    files.close();
  }

  @Test(timeout = 20000)
  public void anInterruptedUploadContinuesFromItsCheckpointWithoutSendingAcceptedPartsAgain() throws Exception {
    byte[] contents = bytes(12, 1);
    Path source = file("report.bin", contents);
    failPartOnce(3);
    expectFailure(() -> client.transfers().upload(source, "report.bin", checkpointed().build()));
    assertEquals(Arrays.asList(1L, 2L, 3L), sentPartNumbers(0));
    int partsBefore = files.parts.size();
    int beginsBefore = files.begins.size();

    List<TransferProgress> progress = new ArrayList<>();
    UploadResult result = client.transfers().upload(source, "report.bin", checkpointed().progressListener(progress::add).build());
    assertEquals(12, result.getSize());
    assertEquals(3, result.getParts().size());

    // Only the part the first run did not get accepted is sent, with the same bytes.
    assertEquals(Arrays.asList(3L), sentPartNumbers(partsBefore));
    assertArrayEquals(Arrays.copyOfRange(contents, 10, 12), files.parts.get(files.parts.size() - 1).body);
    for (FakeFiles.Received begin : files.begins.subList(beginsBefore, files.begins.size())) {
      assertEquals("the recorded upload is continued", "put-1", begin.json().path("ref").asText());
    }
    // Completed with the first run's ETags for its parts.
    assertEquals(1, files.finishes.size());
    JsonNode finish = files.finishes.get(0).json();
    assertEquals("put-1", finish.path("ref").asText());
    assertEquals(12, finish.path("size").asLong());
    assertEquals(Arrays.asList("\"etag-1-1\"", "\"etag-2-1\"", "\"etag-3-2\""), etags(finish));

    // The first run's parts count as transferred and resumed, not as this run's own bytes.
    TransferProgress first = progress.get(0);
    assertEquals(10, first.getTransferredBytes());
    assertEquals(10, first.getResumedBytes());
    assertEquals(0, first.getBytesPerSecond(), 0);
    assertEquals(12, result.getFinalProgress().getTransferredBytes());
    assertEquals(10, result.getFinalProgress().getResumedBytes());
    assertEquals("deleted once completed", 0, listing(checkpoints).size());
  }

  @Test(timeout = 20000)
  public void aCancelledUploadContinuesFromItsCheckpointWithAFreshClient() throws Exception {
    byte[] contents = bytes(12, 5);
    Path source = file("cancelled.bin", contents);
    // Part 3 is held once it arrives, after parts 1 and 2 were accepted, and the caller cancels then.
    CountDownLatch arrived = new CountDownLatch(1);
    CountDownLatch release = new CountDownLatch(1);
    files.storage = (part, attempt) -> {
      if (part == 3 && attempt == 1) {
        arrived.countDown();
        return FakeFiles.Answer.holdUntil(release);
      }
      return null;
    };
    CancellationToken token = new CancellationToken();
    ExecutorService canceller = Executors.newSingleThreadExecutor();
    try {
      Future<?> cancelling = canceller.submit(() -> {
        assertTrue("part 3 arrived", arrived.await(10, TimeUnit.SECONDS));
        token.cancel();
        return null;
      });
      try {
        client.transfers().upload(source, "cancelled.bin", checkpointed().cancellation(token).build());
        fail("Expected the upload to be cancelled");
      } catch (OperationCancelledException e) {
        assertEquals(OperationCancelledException.Reason.CANCELLED, e.getReason());
      }
      cancelling.get(10, TimeUnit.SECONDS);
    } finally {
      release.countDown();
      canceller.shutdownNow();
      assertTrue("the canceller has ended", canceller.awaitTermination(10, TimeUnit.SECONDS));
    }
    assertEquals("nothing is completed", 0, files.finishes.size());
    assertEquals(1, files.partsNumbered(1).size());
    assertEquals(1, files.partsNumbered(2).size());
    assertFalse("the checkpoint is kept", listing(checkpoints).isEmpty());
    final int beginsBefore = files.begins.size();

    // Another client continues the upload from the checkpoint.
    files.storage = (part, attempt) -> null;
    List<TransferProgress> progress = new ArrayList<>();
    UploadResult result;
    try (FilesClient fresh = FilesClient.builder()
             .apiKey("transfer-key")
             .workspaceId(0L)
             .apiRoot(files.root())
             .retryDelay(Duration.ofMillis(1))
             .build()) {
      result = fresh.transfers().upload(source, "cancelled.bin", checkpointed().progressListener(progress::add).build());
    }
    assertEquals(12, result.getSize());
    assertEquals(3, result.getParts().size());

    // Only part 3 is sent again, once, with its bytes; the cancelled attempt may or may not have
    // reached the service before its connection closed.
    assertEquals(1, files.partsNumbered(1).size());
    assertEquals(1, files.partsNumbered(2).size());
    List<FakeFiles.Received> resent = files.partsNumbered(3).stream().filter(p -> p.attempt == 2).collect(Collectors.toList());
    assertEquals(1, resent.size());
    assertArrayEquals(Arrays.copyOfRange(contents, 10, 12), resent.get(0).body);
    assertTrue("no later attempt", files.partsNumbered(3).stream().allMatch(p -> p.attempt <= 2));
    List<FakeFiles.Received> begins = new ArrayList<>(files.begins);
    for (FakeFiles.Received begin : begins.subList(beginsBefore, begins.size())) {
      assertEquals("the recorded upload is continued", "put-1", begin.json().path("ref").asText());
    }
    assertEquals(1, files.finishes.size());
    JsonNode finish = files.finishes.get(0).json();
    assertEquals("put-1", finish.path("ref").asText());
    assertEquals(12, finish.path("size").asLong());
    assertEquals(Arrays.asList("\"etag-1-1\"", "\"etag-2-1\"", "\"etag-3-2\""), etags(finish));

    // The parts accepted before the cancellation count as transferred and resumed, and not in the rate:
    // the rate is the 2 bytes this run transferred per second of the time it reports.
    TransferProgress first = progress.get(0);
    assertEquals(10, first.getTransferredBytes());
    assertEquals(10, first.getResumedBytes());
    assertEquals(0, first.getBytesPerSecond(), 0);
    TransferProgress last = result.getFinalProgress();
    assertEquals(12, last.getTransferredBytes());
    assertEquals(10, last.getResumedBytes());
    assertTrue(String.valueOf(last.getElapsed()), last.getElapsed().toNanos() > 0);
    assertTrue(String.valueOf(last.getBytesPerSecond()), last.getBytesPerSecond() > 0 && Double.isFinite(last.getBytesPerSecond()));
    assertEquals(2, last.getBytesPerSecond() * last.getElapsed().toNanos() / 1e9, 1e-6);
    assertEquals("deleted once completed", 0, listing(checkpoints).size());
  }

  @Test(timeout = 20000)
  public void aContinuedUploadWhoseS3UploadChangedSendsNothingAndKeepsItsCheckpoint() throws Exception {
    byte[] contents = bytes(11 * MIB, 2);
    Path source = file("large.bin", contents);
    AtomicReference<String> uploadId = new AtomicReference<>("UPLOAD-1");
    s3Parts(uploadId);
    UploadOptions options = checkpointed().partSizing(PartSizing.fixed(5 * MIB)).build();
    failPartOnce(2);
    expectFailure(() -> client.transfers().upload(source, "large.bin", options));
    int partsBefore = files.parts.size();

    // The same reference, but another S3 upload: its parts would not be the ones recorded.
    uploadId.set("UPLOAD-2");
    try {
      client.transfers().upload(source, "large.bin", options);
      fail("Expected the changed upload to be refused");
    } catch (TransferCheckpointException e) {
      assertEquals(TransferCheckpointException.Reason.UPLOAD_CHANGED, e.getReason());
    }
    assertEquals("nothing sent", partsBefore, files.parts.size());
    assertEquals(0, files.finishes.size());

    // The checkpoint was kept: with the recorded S3 upload, the rest is sent in the recorded sizes.
    uploadId.set("UPLOAD-1");
    UploadResult result = client.transfers().upload(source, "large.bin", options);
    assertEquals(Arrays.asList(2L, 3L), sentPartNumbers(partsBefore));
    assertEquals(Arrays.asList((long) 5 * MIB, (long) 5 * MIB, (long) MIB),
        result.getParts().stream().map(p -> p.getLength()).collect(Collectors.toList()));
    assertArrayEquals(Arrays.copyOfRange(contents, 5 * MIB, 10 * MIB), files.partsNumbered(2).get(1).body);
  }

  @Test(timeout = 20000)
  public void aContinuedUploadWhosePlannedPartFilesComNowSizesOtherwiseSendsNothing() throws Exception {
    Path source = file("planned.bin", bytes(12, 3));
    failPartOnce(2);
    expectFailure(() -> client.transfers().upload(source, "planned.bin", checkpointed().build()));
    int partsBefore = files.parts.size();

    // Part 2 was planned, and may have been sent, as 5 bytes.
    files.partsize = 4;
    try {
      client.transfers().upload(source, "planned.bin", checkpointed().build());
      fail("Expected the changed plan to be refused");
    } catch (TransferCheckpointException e) {
      assertEquals(TransferCheckpointException.Reason.UPLOAD_CHANGED, e.getReason());
    }
    assertEquals(partsBefore, files.parts.size());
    assertEquals(0, files.finishes.size());
  }

  @Test(timeout = 20000)
  public void aPartAnEarlierRunMayHaveSentIsNotSentAgainWhileFilesComForbidsRetryingParts() throws Exception {
    byte[] contents = bytes(12, 16);
    Path source = file("once.bin", contents);
    files.retryParts = false;
    // Part 3 reaches the storage server, but its answer is lost: it may have been stored.
    files.storage = (part, attempt) -> part == 3 && attempt == 1 ? FakeFiles.Answer.drop() : null;
    expectFailure(() -> client.transfers().upload(source, "once.bin", checkpointed().build()));
    assertEquals("not retried within the run", 1, files.partsNumbered(3).size());
    byte[] recorded = Files.readAllBytes(record());
    int partsBefore = files.parts.size();

    // Files.com's answer to the next run still forbids retrying parts: part 3 is not sent again.
    assertNotResent(source, "once.bin", recorded, partsBefore);

    // Nor when its URL is about to expire and the new one Files.com gives forbids it.
    AtomicInteger answersForPart3 = new AtomicInteger();
    files.partEditor = part -> {
      if (part.get("part_number").asLong() == 3) {
        part.put("retry_parts", answersForPart3.incrementAndGet() == 1);
        part.put("expires", Instant.now().plusSeconds(10).toString());
      }
      return part;
    };
    assertNotResent(source, "once.bin", recorded, partsBefore);
    assertEquals("planned, then renewed", 2, answersForPart3.get());

    // Once Files.com's answer allows retrying parts, part 3 is sent again, with its recorded bytes.
    files.partEditor = part -> part;
    files.retryParts = true;
    UploadResult result = client.transfers().upload(source, "once.bin", checkpointed().build());
    assertEquals(Arrays.asList(3L), sentPartNumbers(partsBefore));
    assertArrayEquals(Arrays.copyOfRange(contents, 10, 12), files.parts.get(files.parts.size() - 1).body);
    assertEquals(10, result.getFinalProgress().getResumedBytes());
    assertEquals(1, files.finishes.size());
  }

  private void assertNotResent(Path source, String destination, byte[] recorded, int partsBefore) throws Exception {
    try {
      client.transfers().upload(source, destination, checkpointed().build());
      fail("Expected the part not to be sent again");
    } catch (TransferCheckpointException e) {
      assertEquals(TransferCheckpointException.Reason.UPLOAD_CHANGED, e.getReason());
      assertTrue(e.getMessage(), e.getMessage().contains("retry_parts") && e.getMessage().contains("Discard the checkpoint"));
    }
    assertEquals("nothing sent", partsBefore, files.parts.size());
    assertEquals(0, files.finishes.size());
    assertArrayEquals("kept as it was", recorded, Files.readAllBytes(record()));
  }

  @Test(timeout = 20000)
  public void aCheckpointSayingTheFinalRequestWasAboutToBeSentReportsAnUnknownOutcomeAndSendsNothing() throws Exception {
    Path source = file("final.bin", bytes(8, 4));
    AtomicReference<String> recordWhenSent = new AtomicReference<>();
    AtomicLong sizeWhenSent = new AtomicLong(-1);
    AtomicReference<Throwable> failureWhenSent = new AtomicReference<>();
    files.finish = request -> {
      try {
        Path held = record();
        sizeWhenSent.set(Files.size(held));
        // On Windows the run holds its record with no sharing (WindowsCheckpointStore): no one else can
        // open it until the run ends, so its bytes are read then, and its size here ties them to now.
        if (!WINDOWS) {
          recordWhenSent.set(new String(Files.readAllBytes(held), StandardCharsets.ISO_8859_1));
        }
      } catch (Exception | AssertionError e) {
        // Reported below; the server closes the connection without answering, as it would after drop().
        failureWhenSent.set(e);
        throw new IOException(e);
      }
      return FakeFiles.Answer.drop();
    };
    expectFailure(() -> client.transfers().upload(source, "final.bin", checkpointed().build()), UploadCompletionUnknownException.class);
    if (failureWhenSent.get() != null) {
      throw new AssertionError("The record could not be read when the final request arrived", failureWhenSent.get());
    }
    byte[] kept = Files.readAllBytes(record());
    assertEquals("the record when the final request arrived, as it was kept", kept.length, sizeWhenSent.get());
    if (WINDOWS) {
      recordWhenSent.set(new String(kept, StandardCharsets.ISO_8859_1));
    }
    assertTrue("recorded before the request reached Files.com", recordWhenSent.get().contains("\"type\":\"finalizing\""));
    int beginsBefore = files.begins.size();
    int partsBefore = files.parts.size();

    files.finish = request -> null;
    try {
      client.transfers().upload(source, "final.bin", checkpointed().build());
      fail("Expected the outcome to be unknown");
    } catch (UploadCompletionUnknownException e) {
      assertEquals("final.bin", e.getPath());
      assertEquals(2, e.getParts().size());
    }
    assertEquals("nothing sent", beginsBefore, files.begins.size());
    assertEquals(partsBefore, files.parts.size());
    assertEquals("the final request is never sent again", 1, files.finishes.size());

    // Once discarded, the next upload starts over.
    assertTrue(client.transfers().discardUploadCheckpoint(source, "final.bin", checkpointed().build()));
    assertFalse(client.transfers().discardUploadCheckpoint(source, "final.bin", checkpointed().build()));
    client.transfers().upload(source, "final.bin", checkpointed().build());
    assertEquals("put-2", files.finishes.get(1).json().path("ref").asText());
  }

  @Test(timeout = 20000)
  public void aFinalRequestFilesComThrottledIsSentByTheNextRunWithoutSendingAnyPart() throws Exception {
    Path source = file("throttled.bin", bytes(8, 5));
    files.finish = request -> FakeFiles.Answer.status(429, "{\"type\":\"rate-limited/too-many-requests\",\"http-code\":429,\"error\":\"Slow down\"}")
        .header("Retry-After", "0");
    expectFailure(() -> client.transfers().upload(source, "throttled.bin", checkpointed().build()),
        ApiErrorException.RateLimitedException.class);
    int finishesBefore = files.finishes.size();
    int partsBefore = files.parts.size();

    files.finish = request -> null;
    UploadResult result = client.transfers().upload(source, "throttled.bin", checkpointed().build());
    assertEquals(partsBefore, files.parts.size());
    assertEquals(finishesBefore + 1, files.finishes.size());
    assertEquals(Arrays.asList("\"etag-1-1\"", "\"etag-2-1\""), etags(files.finishes.get(finishesBefore).json()));
    assertEquals(8, result.getFinalProgress().getResumedBytes());
  }

  @Test(timeout = 20000)
  public void aFinalRequestThatNeverReachedFilesComIsSentByTheNextRunWithoutSendingAnyPart() throws Exception {
    Path source = file("unsent.bin", bytes(8, 15));
    FilesClient unconnected = FilesClient.builder().apiKey("transfer-key").workspaceId(0L).apiRoot(files.root())
        .retryDelay(Duration.ofMillis(1))
        .transport(com.files.net.HttpTransport.builder().customize(http -> http.addInterceptorLast(
            (org.apache.http.HttpRequestInterceptor) (request, context) -> {
              if (request.getRequestLine().getUri().startsWith("/api/rest/v1/files/")) {
                throw new java.net.ConnectException("refused before sending");
              }
            })))
        .build();
    try {
      expectFailure(() -> unconnected.transfers().upload(source, "unsent.bin", checkpointed().build()),
          ApiErrorException.ApiConnectionException.class);
    } finally {
      unconnected.close();
    }
    assertEquals(0, files.finishes.size());
    int partsBefore = files.parts.size();

    UploadResult result = client.transfers().upload(source, "unsent.bin", checkpointed().build());
    assertEquals(partsBefore, files.parts.size());
    assertEquals(1, files.finishes.size());
    assertEquals("put-1", files.finishes.get(0).json().path("ref").asText());
    assertEquals(8, result.getFinalProgress().getResumedBytes());
  }

  @Test(timeout = 20000)
  public void anUploadFilesComNoLongerRecognizesIsOfUnknownOutcomeAndKeepsItsCheckpoint() throws Exception {
    Path source = file("forgotten.bin", bytes(12, 6));
    failPartOnce(3);
    expectFailure(() -> client.transfers().upload(source, "forgotten.bin", checkpointed().build()));
    int partsBefore = files.parts.size();
    byte[] recorded = Files.readAllBytes(record());

    files.beginAnswer = request -> FakeFiles.Answer.status(404,
        "{\"type\":\"not-found/file-upload-not-found\",\"http-code\":404,\"error\":\"File Upload Not Found\"}");
    try {
      client.transfers().upload(source, "forgotten.bin", checkpointed().build());
      fail("Expected the outcome to be unknown");
    } catch (UploadCompletionUnknownException e) {
      assertTrue(e.getCause() instanceof ApiErrorException.FileUploadNotFoundException);
      assertEquals(2, e.getParts().size());
    }
    assertEquals(partsBefore, files.parts.size());
    assertEquals(0, files.finishes.size());
    assertArrayEquals("kept as it was", recorded, Files.readAllBytes(record()));
  }

  @Test(timeout = 20000)
  public void aFileChangedSinceItsCheckpointIsUploadedAgainFromTheStart() throws Exception {
    Path source = file("changed.bin", bytes(12, 7));
    failPartOnce(3);
    expectFailure(() -> client.transfers().upload(source, "changed.bin", checkpointed().build()));
    int partsBefore = files.parts.size();

    files.storage = (part, attempt) -> null;
    byte[] changed = bytes(12, 8);
    Files.write(source, changed);
    Files.setLastModifiedTime(source, FileTime.from(Instant.now().plusSeconds(3600)));
    UploadResult result = client.transfers().upload(source, "changed.bin", checkpointed().build());

    assertEquals(Arrays.asList(1L, 2L, 3L), sentPartNumbers(partsBefore));
    assertEquals("put-2", files.finishes.get(0).json().path("ref").asText());
    assertEquals(0, result.getFinalProgress().getResumedBytes());
  }

  @Test(timeout = 20000)
  public void aCheckpointIsBoundToItsWholeApiRootWithoutKeepingItsSecretsAndToItsPartSizing() throws Exception {
    Path source = file("sizing.bin", bytes(12, 9));
    failPartOnce(3);
    // A configured root that carries user information: synthetic markers only.
    FilesClient secret = clientAt("http://ROOT-USER-MARKER:ROOT-PASSWORD-MARKER@127.0.0.1:" + files.port());
    try {
      expectFailure(() -> secret.transfers().upload(source, "sizing.bin", checkpointed().build()));
      Path secretRecord = record();
      byte[] secretBytes = Files.readAllBytes(secretRecord);
      assertNoMarkers(secretBytes);

      // Another root, the same in all but its user information, does not continue that upload.
      int beginsOfSecret = files.begins.size();
      expectFailure(() -> client.transfers().upload(source, "sizing.bin", checkpointed().build()));
      for (FakeFiles.Received begin : files.begins.subList(beginsOfSecret, files.begins.size())) {
        assertFalse("put-1".equals(begin.json().path("ref").asText(null)));
      }
      assertEquals(Arrays.asList(1L, 2L, 3L), files.parts.stream().filter(p -> p.rawPath.contains("/put-2/")).map(p -> p.part).distinct()
          .sorted().collect(Collectors.toList()));
      assertArrayEquals(secretBytes, Files.readAllBytes(secretRecord));

      // Nor does it adopt that upload's record found under its own record's name.
      Path otherRecord = checkpoints.resolve(listing(checkpoints).stream().filter(name -> !name.equals(secretRecord.getFileName().toString()))
          .findFirst().get());
      Files.write(otherRecord, secretBytes);
      assertRefused(source, "sizing.bin", TransferCheckpointException.Reason.OTHER_TRANSFER);

      // Nor does the same root with other part sizing.
      int beginsBefore = files.begins.size();
      try {
        secret.transfers().upload(source, "sizing.bin", checkpointed().partSizing(PartSizing.serverPlanned()).build());
        fail("Expected other part sizing to be refused");
      } catch (TransferCheckpointException e) {
        assertEquals(TransferCheckpointException.Reason.OTHER_TRANSFER, e.getReason());
        assertNoMarkers(e.getMessage().getBytes(StandardCharsets.UTF_8));
      }
      assertEquals(beginsBefore, files.begins.size());
    } finally {
      secret.close();
    }

    // A root with a query and a fragment cannot reach Files.com's API, but its record is written
    // before its first request: it keeps none of them.
    Path elsewhere = folder.getRoot().toPath().resolve("query-checkpoints");
    FilesClient queried = clientAt("http://ROOT-USER-MARKER@127.0.0.1:" + files.port() + "/?ROOT-QUERY-MARKER#ROOT-FRAGMENT-MARKER");
    try {
      expectFailure(() -> queried.transfers().upload(source, "sizing.bin", checkpointed().checkpointDirectory(elsewhere).build()));
    } finally {
      queried.close();
    }
    List<String> written = listing(elsewhere);
    assertEquals(1, written.size());
    assertNoMarkers(Files.readAllBytes(elsewhere.resolve(written.get(0))));
  }

  private static void assertNoMarkers(byte[] bytes) {
    String text = new String(bytes, StandardCharsets.ISO_8859_1);
    for (String marker : Arrays.asList("ROOT-USER-MARKER", "ROOT-PASSWORD-MARKER", "ROOT-QUERY-MARKER", "ROOT-FRAGMENT-MARKER")) {
      assertFalse(marker, text.contains(marker));
    }
  }

  private FilesClient clientAt(String apiRoot) {
    return FilesClient.builder().apiKey("transfer-key").workspaceId(0L).apiRoot(apiRoot).retryDelay(Duration.ofMillis(1)).build();
  }

  @Test(timeout = 20000)
  public void aCheckpointCutShortByACrashIsContinuedAndOneDamagedTooLargeOrOfAnotherFormatIsRefused() throws Exception {
    Path source = file("torn.bin", bytes(12, 10));
    failPartOnce(3);
    expectFailure(() -> client.transfers().upload(source, "torn.bin", checkpointed().build()));
    byte[] recorded = Files.readAllBytes(record());

    // Damaged within: refused, read but not changed, nothing sent.
    byte[] damaged = recorded.clone();
    damaged[12] ^= 0x20;
    Files.write(record(), damaged);
    assertRefused(source, "torn.bin", TransferCheckpointException.Reason.UNREADABLE);
    assertArrayEquals(damaged, Files.readAllBytes(record()));

    // Larger than a record may hold: refused before it is read.
    try (RandomAccessFile file = new RandomAccessFile(record().toFile(), "rw")) {
      file.setLength(64L * MIB + 1);
    }
    assertRefused(source, "torn.bin", TransferCheckpointException.Reason.UNREADABLE);
    assertEquals(64L * MIB + 1, Files.size(record()));

    // Written in format 1, which held the API root itself: refused, not adopted.
    byte[] formerFormat = withHeaderVersion(recorded, 1);
    Files.write(record(), formerFormat);
    assertRefused(source, "torn.bin", TransferCheckpointException.Reason.UNREADABLE);
    assertArrayEquals(formerFormat, Files.readAllBytes(record()));

    // A last entry cut short, as a crash leaves one: dropped, and the upload continues.
    Files.write(record(), recorded);
    // Its length and checksum, and the first 3 of its 40 bytes.
    Files.write(record(), new byte[]{0, 0, 0, 40, 9, 9, 9, 9, 1, 2, 3}, StandardOpenOption.APPEND);
    int partsBefore = files.parts.size();
    client.transfers().upload(source, "torn.bin", checkpointed().build());
    assertEquals(Arrays.asList(3L), sentPartNumbers(partsBefore));
  }

  @Test(timeout = 20000)
  public void aSecondRunOfTheSameUploadAtTheSameTimeIsRefused() throws Exception {
    Path source = file("busy.bin", bytes(12, 11));
    CountDownLatch arrived = new CountDownLatch(1);
    CountDownLatch release = new CountDownLatch(1);
    files.storage = (part, attempt) -> {
      arrived.countDown();
      return FakeFiles.Answer.holdUntil(release);
    };
    ExecutorService background = Executors.newSingleThreadExecutor();
    try {
      Future<UploadResult> first = background.submit(() -> client.transfers().upload(source, "busy.bin", checkpointed().build()));
      assertTrue(arrived.await(10, TimeUnit.SECONDS));
      int beginsBefore = files.begins.size();
      assertRefused(source, "busy.bin", TransferCheckpointException.Reason.IN_USE);
      assertEquals(beginsBefore, files.begins.size());
      release.countDown();
      assertEquals(12, first.get(10, TimeUnit.SECONDS).getSize());
    } finally {
      release.countDown();
      background.shutdownNow();
    }
  }

  @Test(timeout = 20000)
  public void aCheckpointDirectoryThatIsNotPrivateOrIsALinkIsRefusedAndNothingInItIsRead() throws Exception {
    assumeTrue(folder.getRoot().toPath().getFileSystem().supportedFileAttributeViews().contains("posix"));
    Path source = file("shared.bin", bytes(12, 12));
    Path shared = Files.createDirectory(folder.getRoot().toPath().resolve("shared"),
        PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxr-xr-x")));
    Path planted = Files.write(shared.resolve("planted"), bytes(4, 13));
    try {
      client.transfers().upload(source, "shared.bin", UploadOptions.builder().checkpointDirectory(shared).build());
      fail("Expected the directory to be refused");
    } catch (TransferCheckpointException e) {
      assertEquals(TransferCheckpointException.Reason.UNTRUSTED, e.getReason());
    }
    assertEquals(Arrays.asList("planted"), listing(shared));
    assertArrayEquals(bytes(4, 13), Files.readAllBytes(planted));

    // A link to a private directory, at the checkpoint directory's own name.
    Path real = Files.createDirectory(folder.getRoot().toPath().resolve("real"),
        PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwx------")));
    Path link = Files.createSymbolicLink(folder.getRoot().toPath().resolve("link"), real);
    try {
      client.transfers().upload(source, "shared.bin", UploadOptions.builder().checkpointDirectory(link).build());
      fail("Expected the link to be refused");
    } catch (TransferCheckpointException e) {
      assertEquals(TransferCheckpointException.Reason.UNTRUSTED, e.getReason());
    }
    assertEquals(0, listing(real).size());
    assertEquals("nothing sent", 0, files.begins.size());

    // A directory the SDK creates is private.
    client.transfers().upload(source, "shared.bin", checkpointed().build());
    assertEquals("rwx------", PosixFilePermissions.toString(Files.getPosixFilePermissions(checkpoints)));
  }

  @Test(timeout = 20000)
  public void uploadsThatCannotKeepACheckpointRefuseOptionsThatAskForOne() throws Exception {
    Path source = file("stream.bin", bytes(4, 14));
    UploadOptions options = checkpointed().build();
    HashMap<String, Object> attributes = new HashMap<>();
    attributes.put("ref", "put-9");
    attributes.put("path", "stream.bin");
    FileUploadPart firstPart = new FileUploadPart(attributes);
    List<ThrowingRunnable> uploads = Arrays.asList(
        () -> client.transfers().upload(new ByteArrayInputStream(bytes(4, 14)), 4, "stream.bin", options),
        () -> client.transfers().upload(new ByteArrayInputStream(bytes(4, 14)), "stream.bin", options),
        () -> client.transfers().continueUpload(firstPart, source, options),
        () -> client.transfers().continueUpload(firstPart, new ByteArrayInputStream(bytes(4, 14)), 4, options));
    for (ThrowingRunnable upload : uploads) {
      try {
        upload.run();
        fail("Expected the checkpoint to be refused");
      } catch (IllegalArgumentException e) {
        assertTrue(e.getMessage(), e.getMessage().contains("cannot keep a checkpoint"));
      }
    }
    assertEquals(0, files.begins.size());
    assertEquals(0, files.parts.size());
    assertFalse("no directory created", Files.exists(checkpoints));
  }

  // The record with its header, the first frame (its body's length, the CRC-32 of its body, and its
  // body, JSON), stating the format version given.
  private static byte[] withHeaderVersion(byte[] record, int version) {
    java.nio.ByteBuffer frames = java.nio.ByteBuffer.wrap(record);
    byte[] body = new byte[frames.getInt()];
    frames.getInt();
    frames.get(body);
    String header = new String(body, StandardCharsets.UTF_8);
    String changed = header.replaceFirst("\"version\":\\d+", "\"version\":" + version);
    assertFalse(header.equals(changed));
    byte[] changedBody = changed.getBytes(StandardCharsets.UTF_8);
    java.util.zip.CRC32 crc = new java.util.zip.CRC32();
    crc.update(changedBody, 0, changedBody.length);
    java.nio.ByteBuffer rewritten = java.nio.ByteBuffer.allocate(8 + changedBody.length + frames.remaining());
    rewritten.putInt(changedBody.length).putInt((int) crc.getValue()).put(changedBody).put(frames);
    return rewritten.array();
  }

  private interface ThrowingRunnable {
    void run() throws Exception;
  }

  private UploadOptions.Builder checkpointed() {
    // One part at a time, so that a failing part ends the run with the parts before it accepted.
    return UploadOptions.builder().checkpointDirectory(checkpoints).concurrencyMode(ConcurrencyMode.STATIC).maxConcurrency(1);
  }

  // The part's first attempt is refused for good, ending the run.
  private void failPartOnce(long number) {
    files.storage = (part, attempt) -> part == number && attempt == 1 ? FakeFiles.Answer.status(403, "<Error>AccessDenied</Error>") : null;
  }

  private static void expectFailure(ThrowingRunnable upload) throws Exception {
    expectFailure(upload, Exception.class);
  }

  private static void expectFailure(ThrowingRunnable upload, Class<? extends Exception> expected) throws Exception {
    try {
      upload.run();
    } catch (Exception e) {
      if (!expected.isInstance(e) || e instanceof TransferCheckpointException) {
        throw e;
      }
      return;
    }
    fail("Expected the upload to fail");
  }

  private void assertRefused(Path source, String destination, TransferCheckpointException.Reason reason) throws Exception {
    int beginsBefore = files.begins.size();
    int partsBefore = files.parts.size();
    try {
      client.transfers().upload(source, destination, checkpointed().build());
      fail("Expected the checkpoint to be refused");
    } catch (TransferCheckpointException e) {
      assertEquals(reason, e.getReason());
    }
    assertEquals(beginsBefore, files.begins.size());
    assertEquals(partsBefore, files.parts.size());
  }

  // Makes every part a part of one S3 multipart upload, uploadId naming it, whose sizes the SDK
  // chooses (see FilesTransferTest.s3Parts).
  private void s3Parts(AtomicReference<String> uploadId) {
    files.partEditor = part -> {
      long number = part.get("part_number").asLong();
      part.put("upload_uri", files.root() + "/storage/" + part.get("ref").asText() + "/object?partNumber=" + number
          + "&uploadId=" + uploadId.get() + "&X-Amz-Algorithm=AWS4-HMAC-SHA256"
          + "&X-Amz-Credential=AKIDEXAMPLE%2F20261014%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-SignedHeaders=host&" + FakeFiles.SIGNATURE);
      return part;
    };
  }

  // The numbers of the parts the storage server received from the from'th on.
  private List<Long> sentPartNumbers(int from) {
    return files.parts.subList(from, files.parts.size()).stream().map(p -> p.part).distinct().sorted().collect(Collectors.toList());
  }

  private static List<String> etags(JsonNode finish) {
    List<String> etags = new ArrayList<>();
    for (JsonNode etag : finish.path("etags")) {
      etags.add(etag.path("etag").asText());
    }
    return etags;
  }

  // The checkpoint directory's one record.
  private Path record() throws IOException {
    List<String> names = listing(checkpoints);
    assertEquals(names.toString(), 1, names.size());
    return checkpoints.resolve(names.get(0));
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
}
