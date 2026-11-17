package com.files;

import com.files.net.DownloadOptions;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

// The temporary folder, FakeFiles and client of it that each transfer test starts with, and the helpers the
// transfer test classes share.
public abstract class FilesTransferTestBase {
  @Rule
  public TemporaryFolder folder = new TemporaryFolder();

  protected FakeFiles files;
  protected FilesClient client;

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

  // Whether condition holds within limit, checked every 10 ms; false, not a failure, otherwise, so that
  // it can be asked on a server thread and asserted on the test's own.
  protected static boolean eventually(java.util.function.BooleanSupplier condition, Duration limit) {
    long deadline = System.nanoTime() + limit.toNanos();
    while (!condition.getAsBoolean()) {
      if (System.nanoTime() - deadline > 0) {
        return false;
      }
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        return false;
      }
    }
    return true;
  }

  // The query a URL signed at signed, valid for seconds, adds after its signature.
  protected static String signedQuery(Instant signed, long seconds) {
    return "&X-Amz-Date=" + java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'").withZone(java.time.ZoneOffset.UTC)
        .format(signed) + "&X-Amz-Expires=" + seconds;
  }

  protected static DownloadOptions.Builder inParts() {
    return DownloadOptions.builder().partSize(1000);
  }

  protected Path file(String name, byte[] contents) throws IOException {
    Path path = folder.getRoot().toPath().resolve(name);
    Files.write(path, contents);
    return path;
  }

  protected static byte[] bytes(int length, int seed) {
    byte[] bytes = new byte[length];
    for (int i = 0; i < length; i++) {
      bytes[i] = (byte) (i * 31 + seed);
    }
    return bytes;
  }
}
