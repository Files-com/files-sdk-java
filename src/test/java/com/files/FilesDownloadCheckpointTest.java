package com.files;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

import com.files.exceptions.OperationCancelledException;
import com.files.exceptions.TransferCheckpointException;
import com.files.models.File;
import com.files.net.ConcurrencyMode;
import com.files.net.DownloadOptions;
import com.files.net.DownloadResult;
import com.files.net.TransferProgress;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

// Downloads that keep a checkpoint (DownloadOptions.Builder.checkpointDirectory), run against
// FakeFiles' ranged storage: what a later run continues, what it refuses, and what it never mixes.
public class FilesDownloadCheckpointTest {
  private static final String REMOTE = "remote/big.bin";

  @Rule
  public TemporaryFolder folder = new TemporaryFolder();

  private FakeFiles files;
  private FilesClient client;
  private Path checkpoints;
  private Path destination;
  // While set, a run stops at the range from 5000: it is refused, and later ranges are held, then
  // aborted with the run, so the run writes exactly the bytes before 5000.
  private final AtomicBoolean interrupting = new AtomicBoolean();
  private final CountDownLatch held = new CountDownLatch(1);

  @Before
  public void setUp() throws IOException {
    files = new FakeFiles();
    client = FilesClient.builder()
        .apiKey("transfer-key")
        .workspaceId(0L)
        .apiRoot(files.root())
        .retryDelay(Duration.ofMillis(1))
        .build();
    checkpoints = folder.getRoot().toPath().resolve("checkpoints");
    destination = folder.getRoot().toPath().resolve("big.bin");
    files.rangeAnswer = (start, attempt) -> !interrupting.get() ? null
        : start == 5000 ? FakeFiles.Answer.status(403, "") : start > 5000 ? FakeFiles.Answer.holdUntil(held) : null;
  }

  @After
  public void tearDown() {
    held.countDown();
    client.close();
    files.close();
  }

  @Test(timeout = 30000)
  public void anInterruptedDownloadContinuesFromItsPartialFileFetchingOnlyTheMissingRanges() throws Exception {
    byte[] contents = rangedFile(1, "\"v1\"");
    interrupt();
    assertFalse("nothing published", Files.exists(destination));
    Path stage = stageDirectory();
    if (posix()) {
      assertEquals("rwx------", PosixFilePermissions.toString(Files.getPosixFilePermissions(stage)));
      assertEquals("rw-------", PosixFilePermissions.toString(Files.getPosixFilePermissions(stage.resolve("download"))));
    }
    assertEquals(1, listing(checkpoints).size());
    int before = files.rangeRequests.size();

    List<TransferProgress> progress = new ArrayList<>();
    DownloadResult result = client.transfers().download(REMOTE, destination, checkpointed().progressListener(progress::add).build());

    assertArrayEquals(contents, Files.readAllBytes(destination));
    List<FakeFiles.Received> resumed = files.rangeRequests.subList(before, files.rangeRequests.size());
    assertEquals("only the missing ranges", Arrays.asList(5000L, 6000L, 7000L, 8000L, 9000L), starts(resumed));
    for (FakeFiles.Received request : resumed) {
      assertEquals("each confirms the recorded version", "\"v1\"", request.header("If-Match"));
    }
    assertTrue(result.isVersionBound());
    assertEquals(5000, progress.get(0).getResumedBytes());
    assertEquals(10_000, result.getFinalProgress().getTransferredBytes());
    assertEquals(5000, result.getFinalProgress().getResumedBytes());
    assertEquals("deleted once published", 0, listing(checkpoints).size());
    assertEquals("the partial download is gone", Arrays.asList("big.bin", "checkpoints"), listing(folder.getRoot().toPath()));
  }

  @Test(timeout = 30000)
  public void aCancelledDownloadContinuesFromItsPartialFileWithAFreshClient() throws Exception {
    final byte[] contents = rangedFile(2, "\"v1\"");
    // The range from 5000 is held once it is asked for, after the ranges before it were written, and
    // the caller cancels then.
    CountDownLatch asked = new CountDownLatch(1);
    CountDownLatch release = new CountDownLatch(1);
    files.rangeAnswer = (start, attempt) -> {
      if (start == 5000 && attempt == 1) {
        asked.countDown();
        return FakeFiles.Answer.holdUntil(release);
      }
      return null;
    };
    CancellationToken token = new CancellationToken();
    ExecutorService canceller = Executors.newSingleThreadExecutor();
    try {
      Future<?> cancelling = canceller.submit(() -> {
        assertTrue("the range from 5000 was asked for", asked.await(10, TimeUnit.SECONDS));
        token.cancel();
        return null;
      });
      try {
        client.transfers().download(REMOTE, destination, checkpointed().cancellation(token).build());
        fail("Expected the download to be cancelled");
      } catch (OperationCancelledException e) {
        assertEquals(OperationCancelledException.Reason.CANCELLED, e.getReason());
      }
      cancelling.get(10, TimeUnit.SECONDS);
    } finally {
      release.countDown();
      canceller.shutdownNow();
      assertTrue("the canceller has ended", canceller.awaitTermination(10, TimeUnit.SECONDS));
    }
    assertFalse("nothing published", Files.exists(destination));
    stageDirectory();
    assertEquals("the checkpoint is kept", 1, listing(checkpoints).size());
    List<FakeFiles.Received> cancelled = new ArrayList<>(files.rangeRequests);
    assertEquals("the ranges before 5000, then 5000", Arrays.asList(0L, 1000L, 2000L, 3000L, 4000L, 5000L), starts(cancelled));

    // Another client continues the download from the partial file.
    files.rangeAnswer = (start, attempt) -> null;
    List<TransferProgress> progress = new ArrayList<>();
    DownloadResult result;
    try (FilesClient fresh = FilesClient.builder()
             .apiKey("transfer-key")
             .workspaceId(0L)
             .apiRoot(files.root())
             .retryDelay(Duration.ofMillis(1))
             .build()) {
      result = fresh.transfers().download(REMOTE, destination, checkpointed().progressListener(progress::add).build());
    }

    assertArrayEquals(contents, Files.readAllBytes(destination));
    List<FakeFiles.Received> all = new ArrayList<>(files.rangeRequests);
    List<FakeFiles.Received> resumed = all.subList(cancelled.size(), all.size());
    assertEquals("only the missing ranges", Arrays.asList(5000L, 6000L, 7000L, 8000L, 9000L), starts(resumed));
    for (FakeFiles.Received request : resumed) {
      assertEquals("each confirms the recorded version", "\"v1\"", request.header("If-Match"));
    }
    assertTrue(result.isVersionBound());

    // The ranges written before the cancellation count as transferred and resumed, and never in the
    // rate: each report's rate is the bytes this run had fetched by then per second of the time it reports.
    assertFalse("progress is reported", progress.isEmpty());
    long previous = 5000;
    for (TransferProgress report : progress) {
      assertEquals(5000, report.getResumedBytes());
      long transferred = report.getTransferredBytes();
      assertTrue(String.valueOf(transferred), transferred >= previous && transferred <= 10_000);
      previous = transferred;
      long fetched = transferred - report.getResumedBytes();
      if (fetched > 0) {
        assertTrue(String.valueOf(report.getElapsed()), report.getElapsed().toNanos() > 0);
        assertTrue(String.valueOf(report.getBytesPerSecond()), report.getBytesPerSecond() > 0 && Double.isFinite(report.getBytesPerSecond()));
        assertEquals(fetched, report.getBytesPerSecond() * report.getElapsed().toNanos() / 1e9, 1e-6);
      } else {
        assertEquals(0, report.getBytesPerSecond(), 0);
      }
    }
    TransferProgress last = result.getFinalProgress();
    assertEquals(10_000, last.getTransferredBytes());
    assertEquals(5000, last.getResumedBytes());
    assertTrue(String.valueOf(last.getElapsed()), last.getElapsed().toNanos() > 0);
    assertTrue(String.valueOf(last.getBytesPerSecond()), last.getBytesPerSecond() > 0 && Double.isFinite(last.getBytesPerSecond()));
    assertEquals(5000, last.getBytesPerSecond() * last.getElapsed().toNanos() / 1e9, 1e-6);
    assertEquals("deleted once published", 0, listing(checkpoints).size());
    assertEquals("the partial download is gone", Arrays.asList("big.bin", "checkpoints"), listing(folder.getRoot().toPath()));
  }

  @Test(timeout = 30000)
  public void aFileThatChangedSinceItsCheckpointIsDownloadedAgainWithoutMixingVersions() throws Exception {
    rangedFile(2, "\"v1\"");
    interrupt();
    byte[] second = changeTo(3, "\"v2\"");
    int before = files.rangeRequests.size();

    client.transfers().download(REMOTE, destination, checkpointed().build());

    assertArrayEquals(second, Files.readAllBytes(destination));
    List<FakeFiles.Received> run = files.rangeRequests.subList(before, files.rangeRequests.size());
    assertEquals("the first missing range, under the recorded version", 5000L, run.get(0).part);
    assertEquals("\"v1\"", run.get(0).header("If-Match"));
    assertEquals("then from the start, as a first run", 0L, run.get(1).part);
    assertNull(run.get(1).header("If-Match"));
    for (FakeFiles.Received request : run.subList(2, run.size())) {
      assertEquals("\"v2\"", request.header("If-Match"));
    }
    assertEquals(0, listing(checkpoints).size());

    // A storage server that ignores If-Match answers with its new version: its bytes are not taken
    // for the recorded version's.
    interrupt();
    files.ifMatch = false;
    byte[] third = changeTo(4, "\"v3\"");
    before = files.rangeRequests.size();
    DownloadResult result = client.transfers().download(REMOTE, destination, checkpointed().build());
    assertEquals(0, result.getFinalProgress().getResumedBytes());
    assertArrayEquals(third, Files.readAllBytes(destination));
    run = files.rangeRequests.subList(before, files.rangeRequests.size());
    assertEquals("\"v2\"", run.get(0).header("If-Match"));
    assertEquals(0L, run.get(1).part);
    assertNull(run.get(1).header("If-Match"));
  }

  @Test(timeout = 30000)
  public void aPartialDownloadReplacedSinceItWasKeptIsRefusedAndLeftInPlace() throws Exception {
    rangedFile(5, "\"v1\"");
    interrupt();
    Path partial = stageDirectory().resolve("download");
    byte[] kept = Files.readAllBytes(partial);
    // Another file at its name, even with the same bytes and access, is not the partial download. It
    // is written beside the partial download and moved over it, so the two exist at once and cannot
    // share an identity: deleting the partial download first would let the file system give the new
    // file its freed inode, as a POSIX file system may.
    Path replacement = partial.resolveSibling("replacement");
    Files.write(replacement, kept);
    if (posix()) {
      Files.setPosixFilePermissions(replacement, PosixFilePermissions.fromString("rw-------"));
    }
    Object original = fileKey(partial);
    Files.move(replacement, partial, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    if (original != null) {
      assertFalse("a file of another identity", original.equals(fileKey(partial)));
    }
    int before = files.rangeRequests.size();
    try {
      client.transfers().download(REMOTE, destination, checkpointed().build());
      fail("Expected the replaced partial download to be refused");
    } catch (TransferCheckpointException e) {
      assertEquals(TransferCheckpointException.Reason.STAGE_CHANGED, e.getReason());
    }
    assertEquals("nothing fetched", before, files.rangeRequests.size());
    assertArrayEquals("left as it is", kept, Files.readAllBytes(partial));
    assertFalse(Files.exists(destination));

    // Discarding the checkpoint leaves it in place too; the next download starts over.
    assertTrue(client.transfers().discardDownloadCheckpoint(REMOTE, destination, checkpointed().build()));
    assertArrayEquals(kept, Files.readAllBytes(partial));
    assertEquals(0, listing(checkpoints).size());
    before = files.rangeRequests.size();
    client.transfers().download(REMOTE, destination, checkpointed().build());
    assertArrayEquals(files.contents, Files.readAllBytes(destination));
    assertEquals(0L, files.rangeRequests.get(before).part);
    assertNull(files.rangeRequests.get(before).header("If-Match"));
  }

  @Test(timeout = 30000)
  public void aDiscardedCheckpointDeletesItsOwnPartialDownload() throws Exception {
    rangedFile(6, "\"v1\"");
    interrupt();
    Path stage = stageDirectory();

    assertTrue(client.transfers().discardDownloadCheckpoint(REMOTE, destination, checkpointed().build()));
    assertFalse(Files.exists(stage));
    assertEquals(0, listing(checkpoints).size());
    assertFalse(client.transfers().discardDownloadCheckpoint(REMOTE, destination, checkpointed().build()));
  }

  @Test(timeout = 30000)
  public void aWholePartialDownloadThatCouldNotBePublishedIsPublishedByTheNextRun() throws Exception {
    byte[] contents = rangedFile(7, "\"v1\"");
    // The destination appears while the last range is fetched; the download does not replace it.
    AtomicBoolean appear = new AtomicBoolean(true);
    files.rangeAnswer = (start, attempt) -> {
      if (start == 9000 && appear.getAndSet(false)) {
        Files.write(destination, "other".getBytes(StandardCharsets.UTF_8));
      }
      return null;
    };
    try {
      client.transfers().download(REMOTE, destination, checkpointed().replaceExisting(false).build());
      fail("Expected the destination not to be replaced");
    } catch (FileAlreadyExistsException e) {
      assertArrayEquals("other".getBytes(StandardCharsets.UTF_8), Files.readAllBytes(destination));
    }
    Files.delete(destination);
    int before = files.rangeRequests.size();

    DownloadResult result = client.transfers().download(REMOTE, destination, checkpointed().replaceExisting(false).build());

    assertArrayEquals(contents, Files.readAllBytes(destination));
    List<FakeFiles.Received> run = files.rangeRequests.subList(before, files.rangeRequests.size());
    assertEquals("one request, for the last byte, to confirm the version", Arrays.asList(9999L), starts(run));
    assertEquals("\"v1\"", run.get(0).header("If-Match"));
    assertEquals(10_000, result.getFinalProgress().getResumedBytes());
    assertEquals(0, listing(checkpoints).size());
  }

  @Test(timeout = 30000)
  public void aDownloadPublishedBeforeItsCheckpointWasDeletedIsCompleteWithoutFetchingWhileFilesComDescribesThatVersion() throws Exception {
    assumeTrue(posix());
    rangedFile(8, "\"v1\"");
    DownloadResult first = publishKeepingRecord();
    assertEquals(10_000, first.getSize());
    assertEquals("the record outlived the publication", 1, listing(checkpoints).size());
    int ranges = files.rangeRequests.size();
    int downloads = files.downloads.size();

    DownloadResult result = client.transfers().download(REMOTE, destination, checkpointed().build());

    assertEquals(10_000, result.getSize());
    assertEquals("nothing fetched", ranges, files.rangeRequests.size());
    assertEquals(downloads, files.downloads.size());
    assertArrayEquals(files.contents, Files.readAllBytes(destination));
    assertEquals(0, listing(checkpoints).size());

    // Files.com now describes the file with another size: what was published is not taken for it,
    // and the file is downloaded again, from its start.
    publishKeepingRecord();
    byte[] longer = bytes(12_000, 11);
    files.contents = longer;
    files.describedSize = 12_000L;
    files.etag = "\"v2\"";
    ranges = files.rangeRequests.size();
    result = client.transfers().download(REMOTE, destination, checkpointed().build());
    assertEquals(12_000, result.getSize());
    assertEquals(Long.valueOf(12_000), result.getFile().getSize());
    assertArrayEquals(longer, Files.readAllBytes(destination));
    assertEquals(0L, files.rangeRequests.get(ranges).part);
    assertNull(files.rangeRequests.get(ranges).header("If-Match"));
    assertEquals(0, listing(checkpoints).size());

    // Or of the same size, from another storage resource: downloaded again from there.
    publishKeepingRecord();
    files.downloadResource = files.root() + "/download/moved/big.bin";
    ranges = files.rangeRequests.size();
    result = client.transfers().download(REMOTE, destination, checkpointed().build());
    assertEquals(12_000, result.getSize());
    assertTrue("fetched again", files.rangeRequests.size() > ranges);
    assertEquals("/download/moved/big.bin", files.rangeRequests.get(ranges).rawPath);
    assertArrayEquals(longer, Files.readAllBytes(destination));
    assertEquals(0, listing(checkpoints).size());
  }

  // Downloads the file with its checkpoint directory made read-only once the record exists: the
  // record can still be written, but not deleted, as if the run had stopped between publishing the
  // file and deleting its record.
  private DownloadResult publishKeepingRecord() throws Exception {
    AtomicBoolean readOnly = new AtomicBoolean();
    DownloadResult result = client.transfers().download(REMOTE, destination, checkpointed().progressListener(progress -> {
      if (readOnly.compareAndSet(false, true)) {
        try {
          Files.setPosixFilePermissions(checkpoints, PosixFilePermissions.fromString("r-x------"));
        } catch (IOException e) {
          throw new IllegalStateException(e);
        }
      }
    }).build());
    Files.setPosixFilePermissions(checkpoints, PosixFilePermissions.fromString("rwx------"));
    assertEquals(1, listing(checkpoints).size());
    return result;
  }

  @Test(timeout = 30000)
  public void aSecondRunOfTheSameDownloadAtTheSameTimeIsRefused() throws Exception {
    rangedFile(9, "\"v1\"");
    CountDownLatch arrived = new CountDownLatch(1);
    CountDownLatch release = new CountDownLatch(1);
    files.rangeAnswer = (start, attempt) -> {
      arrived.countDown();
      return FakeFiles.Answer.holdUntil(release);
    };
    ExecutorService background = Executors.newSingleThreadExecutor();
    try {
      Future<DownloadResult> first = background.submit(() -> client.transfers().download(REMOTE, destination, checkpointed().build()));
      assertTrue(arrived.await(10, TimeUnit.SECONDS));
      int describes = files.describes.size();
      try {
        client.transfers().download(REMOTE, destination, checkpointed().build());
        fail("Expected the held checkpoint to be refused");
      } catch (TransferCheckpointException e) {
        assertEquals(TransferCheckpointException.Reason.IN_USE, e.getReason());
      }
      assertEquals("refused before any request", describes, files.describes.size());
      release.countDown();
      assertEquals(10_000, first.get(20, TimeUnit.SECONDS).getSize());
    } finally {
      release.countDown();
      background.shutdownNow();
    }
  }

  @Test(timeout = 30000)
  public void aDownloadInOneRequestKeepsNothingAndAFileWithoutAPathCannotKeepACheckpoint() throws Exception {
    byte[] contents = rangedFile(10, "\"v1\"");
    files.ranges = false;
    DownloadResult result = client.transfers().download(REMOTE, destination, checkpointed().build());
    assertFalse(result.isVersionBound());
    assertArrayEquals(contents, Files.readAllBytes(destination));
    assertEquals(0, listing(checkpoints).size());

    // One that fails is not kept either: there is nothing a later run could continue.
    files.download = attempt -> FakeFiles.Answer.status(403, "");
    try {
      client.transfers().download(REMOTE, folder.getRoot().toPath().resolve("failed.bin"), checkpointed().build());
      fail("Expected the download to fail");
    } catch (com.files.exceptions.ApiErrorException.ApiConnectionException e) {
      assertEquals(0, listing(checkpoints).size());
    }
    assertEquals(Arrays.asList("big.bin", "checkpoints"), listing(folder.getRoot().toPath()));

    HashMap<String, Object> attributes = new HashMap<>();
    attributes.put("download_uri", files.root() + "/download/x?" + FakeFiles.SIGNATURE);
    try {
      client.transfers().download(new File(attributes), destination, checkpointed().build());
      fail("Expected a File without a path to be refused a checkpoint");
    } catch (IllegalArgumentException e) {
      assertTrue(e.getMessage(), e.getMessage().contains("checkpoint"));
    }
  }

  // Runs the download until it stops at the range from 5000, with every byte before it written.
  private void interrupt() throws Exception {
    interrupting.set(true);
    try {
      client.transfers().download(REMOTE, destination, checkpointed().build());
      fail("Expected the download to stop");
    } catch (com.files.exceptions.ApiErrorException.ApiConnectionException e) {
      assertTrue(e.getMessage(), e.getMessage().contains("403"));
    } finally {
      interrupting.set(false);
    }
  }

  private DownloadOptions.Builder checkpointed() {
    // One range at a time, so that a run that stops has written exactly the ranges before.
    return DownloadOptions.builder().partSize(1000).concurrencyMode(ConcurrencyMode.STATIC).maxConcurrency(1)
        .checkpointDirectory(checkpoints);
  }

  private byte[] rangedFile(int seed, String etag) {
    files.contents = bytes(10_000, seed);
    files.describedSize = (long) files.contents.length;
    files.ranges = true;
    files.etag = etag;
    return files.contents;
  }

  private byte[] changeTo(int seed, String etag) {
    files.contents = bytes(10_000, seed);
    files.etag = etag;
    return files.contents;
  }

  // The partial download's directory, beside the destination.
  private Path stageDirectory() throws IOException {
    List<String> stages = listing(folder.getRoot().toPath()).stream().filter(name -> name.startsWith(".big.bin.")).collect(Collectors.toList());
    assertEquals(stages.toString(), 1, stages.size());
    return folder.getRoot().toPath().resolve(stages.get(0));
  }

  // The file's key, where its file system gives one: what tells one file from another.
  private static Object fileKey(Path path) throws IOException {
    return Files.readAttributes(path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS).fileKey();
  }

  private static List<Long> starts(List<FakeFiles.Received> requests) {
    return requests.stream().map(request -> request.part).collect(Collectors.toList());
  }

  private boolean posix() {
    return folder.getRoot().toPath().getFileSystem().supportedFileAttributeViews().contains("posix");
  }

  private static List<String> listing(Path directory) throws IOException {
    if (!Files.exists(directory)) {
      return new ArrayList<>();
    }
    try (Stream<Path> entries = Files.list(directory)) {
      return entries.map(p -> p.getFileName().toString()).sorted().collect(Collectors.toList());
    }
  }

  private static byte[] bytes(int length, int seed) {
    byte[] bytes = new byte[length];
    for (int i = 0; i < length; i++) {
      bytes[i] = (byte) (i * 31 + seed);
    }
    return bytes;
  }
}
