package com.files.net;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

import com.files.exceptions.TransferCheckpointException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

// The checkpoint stores, each as the platform reaches it: the one CheckpointStore.open chooses (on
// macOS, through the C library) and the one through Java's own file system API, which also runs on
// macOS. Windows' store is not run here.
public class CheckpointStoreTest {
  private interface Opener {
    CheckpointStore open(Path directory) throws IOException;
  }

  private static final List<Opener> STORES = Arrays.asList(CheckpointStore::open, PosixCheckpointStore::open);

  @Rule
  public TemporaryFolder folder = new TemporaryFolder();

  private Path root;

  @Before
  public void setUp() {
    assumeTrue(folder.getRoot().toPath().getFileSystem().supportedFileAttributeViews().contains("posix"));
    root = folder.getRoot().toPath();
  }

  @Test
  public void aDirectoryOthersCanUseOrALinkIsRefusedAndAMissingOneIsCreatedPrivate() throws Exception {
    Path shared = Files.createDirectory(root.resolve("shared"), PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxr-x---")));
    Path real = Files.createDirectory(root.resolve("real"), PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwx------")));
    Path link = Files.createSymbolicLink(root.resolve("link"), real);
    for (int i = 0; i < STORES.size(); i++) {
      Opener store = STORES.get(i);
      assertUntrusted(store, shared);
      assertUntrusted(store, link);
      Path created = root.resolve("created-" + i);
      store.open(created).close();
      assertEquals("rwx------", PosixFilePermissions.toString(Files.getPosixFilePermissions(created)));
      store.open(real).close();
    }
    assertEquals(0, entries(shared));
    assertEquals(0, entries(real));
  }

  @Test
  public void aRecordIsHeldByOneRunAtATimeAndPrivate() throws Exception {
    for (int i = 0; i < STORES.size(); i++) {
      Path directory = root.resolve("store-" + i);
      try (CheckpointStore store = STORES.get(i).open(directory)) {
        try (CheckpointStore.Record record = store.lock("a.upload")) {
          assertEquals("rw-------", PosixFilePermissions.toString(Files.getPosixFilePermissions(directory.resolve("a.upload"))));
          record.append(new byte[] {1, 2, 3});
          record.force();
          try (CheckpointStore second = STORES.get(i).open(directory)) {
            second.lock("a.upload").close();
            fail("Expected the held record to be refused");
          } catch (TransferCheckpointException e) {
            assertEquals(TransferCheckpointException.Reason.IN_USE, e.getReason());
          }
          assertTrue(record.isNamed());
        }
        // Released when closed.
        try (CheckpointStore.Record record = store.lock("a.upload")) {
          assertArrayEquals(new byte[] {1, 2, 3}, record.read());
          record.delete();
        }
        assertFalse(Files.exists(directory.resolve("a.upload")));
      }
    }
  }

  // A run checks, once it holds its record, that the record's name still leads to it, and another run
  // in the same JVM may try to take it: neither may let another process take it meanwhile, as closing
  // a second descriptor of the file would, where locks are POSIX record locks (fcntl).
  @Test(timeout = 60000)
  public void aRecordHeldIsRefusedToAnotherProcessAfterItsNameIsChecked() throws Exception {
    for (int i = 0; i < STORES.size(); i++) {
      Path directory = root.resolve("store-" + i);
      try (CheckpointStore store = STORES.get(i).open(directory);
          CheckpointStore.Record record = store.lock("a.upload")) {
        record.append(new byte[] {1});
        record.force();
        assertTrue(record.isNamed());
        try (CheckpointStore second = STORES.get(i).open(directory)) {
          second.lock("a.upload").close();
          fail("Expected the held record to be refused");
        } catch (TransferCheckpointException e) {
          assertEquals(TransferCheckpointException.Reason.IN_USE, e.getReason());
        }
        assertEquals("store " + i, "IN_USE", lockFromAnotherProcess(i, directory));
      }
      assertEquals("store " + i, "LOCKED", lockFromAnotherProcess(i, directory));
    }
  }

  // Run in another JVM by lockFromAnotherProcess: tries to lock the record a.upload of the directory
  // with the store numbered, and prints the outcome.
  public static void main(String[] args) throws Exception {
    try (CheckpointStore store = STORES.get(Integer.parseInt(args[0])).open(Paths.get(args[1]))) {
      store.lock("a.upload").close();
      System.out.println("LOCKED");
    } catch (TransferCheckpointException e) {
      System.out.println(e.getReason());
    }
  }

  private static String lockFromAnotherProcess(int store, Path directory) throws Exception {
    Process process = new ProcessBuilder(Paths.get(System.getProperty("java.home"), "bin", "java").toString(), "-cp",
        System.getProperty("java.class.path"), CheckpointStoreTest.class.getName(), String.valueOf(store), directory.toString())
        .redirectError(ProcessBuilder.Redirect.DISCARD).start();
    byte[] output = readAll(process.getInputStream());
    assertTrue(process.waitFor(30, java.util.concurrent.TimeUnit.SECONDS));
    return new String(output, java.nio.charset.StandardCharsets.UTF_8).trim();
  }

  private static byte[] readAll(java.io.InputStream in) throws IOException {
    java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
    byte[] buffer = new byte[8192];
    for (int n; (n = in.read(buffer)) > 0; ) {
      out.write(buffer, 0, n);
    }
    return out.toByteArray();
  }

  @Test
  public void aRecordThatIsALinkOrThatOthersCanReadIsRefused() throws Exception {
    for (int i = 0; i < STORES.size(); i++) {
      Path directory = root.resolve("store-" + i);
      STORES.get(i).open(directory).close();
      Path elsewhere = Files.write(root.resolve("elsewhere-" + i), new byte[] {7});
      Files.createSymbolicLink(directory.resolve("linked.upload"), elsewhere);
      Files.write(directory.resolve("readable.upload"), new byte[] {8});
      Files.setPosixFilePermissions(directory.resolve("readable.upload"), PosixFilePermissions.fromString("rw-r--r--"));
      try (CheckpointStore store = STORES.get(i).open(directory)) {
        for (String name : Arrays.asList("linked.upload", "readable.upload")) {
          try {
            store.lock(name).close();
            fail("Expected " + name + " to be refused");
          } catch (TransferCheckpointException e) {
            assertEquals(name, TransferCheckpointException.Reason.UNTRUSTED, e.getReason());
          }
        }
      }
      assertArrayEquals(new byte[] {7}, Files.readAllBytes(elsewhere));
    }
  }

  @Test
  public void aRecordReplacedAfterItWasOpenedIsNoLongerNamed() throws Exception {
    for (int i = 0; i < STORES.size(); i++) {
      Path directory = root.resolve("store-" + i);
      try (CheckpointStore store = STORES.get(i).open(directory);
          CheckpointStore.Record record = store.lock("a.upload")) {
        record.append(new byte[] {1});
        Files.delete(directory.resolve("a.upload"));
        Files.write(directory.resolve("a.upload"), new byte[] {2});
        assertFalse(record.isNamed());
      }
    }
  }

  private static long entries(Path directory) throws IOException {
    try (java.util.stream.Stream<Path> entries = Files.list(directory)) {
      return entries.count();
    }
  }

  private static void assertUntrusted(Opener store, Path directory) throws IOException {
    try {
      store.open(directory).close();
      fail("Expected " + directory + " to be refused");
    } catch (TransferCheckpointException e) {
      assertEquals(TransferCheckpointException.Reason.UNTRUSTED, e.getReason());
    }
  }
}
