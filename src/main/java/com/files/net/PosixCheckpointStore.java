package com.files.net;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessDeniedException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SecureDirectoryStream;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.nio.file.attribute.UserPrincipal;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// The checkpoint store on POSIX systems other than macOS, through Java's own file system API: the
// directory is held as a SecureDirectoryStream, through which every record is reached by its name
// relative to that handle (openat, fstatat, unlinkat), never by a path. Permissions of exactly
// rwx------ on the directory keep others out: with them, even an ACL's named entries get no access,
// since its mask is the group bits. Only the directory's owner (or root) can create a file in a
// directory of those permissions, and what it creates is its own, so the directory is the effective
// user's when a file this run creates in it has the directory's owner. A platform whose directories
// cannot be held this way is refused.
//
// A record is locked with FileChannel.tryLock, a POSIX record lock (fcntl), which belongs to the
// process: closing any other descriptor the process has open on the same file releases it. So a
// record held is never opened again in this JVM, not by another run (HELD) nor to check it: which file
// its name leads to is told by its file key, read through the directory before it is opened and after
// it is locked, never by opening it.
final class PosixCheckpointStore extends CheckpointStore {
  private static final Logger log = LoggerFactory.getLogger(PosixCheckpointStore.class);
  private static final Set<PosixFilePermission> OWNER_ONLY = PosixFilePermissions.fromString("rwx------");
  private static final Set<PosixFilePermission> OWNER_READ_WRITE = PosixFilePermissions.fromString("rw-------");
  private static final FileAttribute<Set<PosixFilePermission>> PRIVATE_FILE = PosixFilePermissions.asFileAttribute(OWNER_READ_WRITE);
  private static final Path HERE = Paths.get(".");
  private static final SecureRandom RANDOM = new SecureRandom();
  // The records this JVM holds, by directory and name.
  private static final Set<String> HELD = ConcurrentHashMap.newKeySet();

  private final SecureDirectoryStream<Path> held;
  private final UserPrincipal owner;
  private final String identity;

  private PosixCheckpointStore(Path directory, SecureDirectoryStream<Path> held, UserPrincipal owner, Object directoryKey) {
    super(directory);
    this.held = held;
    this.owner = owner;
    this.identity = (directoryKey != null ? directoryKey : directory).toString() + "/";
  }

  static CheckpointStore open(Path directory) throws IOException {
    if (!directory.getFileSystem().supportedFileAttributeViews().contains("posix")) {
      throw untrusted(directory, "its file system has no POSIX permissions to keep it private");
    }
    PosixFileAttributes before;
    try {
      before = Files.readAttributes(directory, PosixFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
    } catch (NoSuchFileException e) {
      try {
        Files.createDirectory(directory, PosixFilePermissions.asFileAttribute(OWNER_ONLY));
      } catch (FileAlreadyExistsException raced) {
        // Made meanwhile: checked below like any other.
      }
      before = Files.readAttributes(directory, PosixFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
    }
    if (!before.isDirectory()) {
      throw untrusted(directory, "it is not a directory");
    }
    DirectoryStream<Path> stream = Files.newDirectoryStream(directory);
    if (!(stream instanceof SecureDirectoryStream)) {
      stream.close();
      throw untrusted(directory, "this platform cannot hold a directory open to reach what is in it");
    }
    SecureDirectoryStream<Path> held = (SecureDirectoryStream<Path>) stream;
    try {
      PosixFileAttributes attributes = held.getFileAttributeView(PosixFileAttributeView.class).readAttributes();
      // The directory opened is the one its name led to, without a link: a link to another directory
      // would have another identity.
      if (!Objects.equals(attributes.fileKey(), before.fileKey())) {
        throw untrusted(directory, "it was replaced while it was opened");
      }
      if (!attributes.permissions().equals(OWNER_ONLY)) {
        throw untrusted(directory, "its permissions are " + PosixFilePermissions.toString(attributes.permissions()) + ", not rwx------");
      }
      if (!attributes.owner().equals(createdBy(directory, held))) {
        throw untrusted(directory, "it belongs to another user");
      }
      return new PosixCheckpointStore(directory, held, attributes.owner(), attributes.fileKey());
    } catch (IOException | RuntimeException | Error e) {
      held.close();
      throw e;
    }
  }

  // The owner of a file this run creates in the directory, which only the directory's owner can do in
  // a directory of permissions rwx------: the effective user.
  private static UserPrincipal createdBy(Path directory, SecureDirectoryStream<Path> held) throws IOException {
    Path probe = Paths.get(".probe-" + Long.toUnsignedString(RANDOM.nextLong(), 36));
    Set<OpenOption> options = new HashSet<OpenOption>(Arrays.<OpenOption>asList(StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW,
        LinkOption.NOFOLLOW_LINKS));
    try {
      held.newByteChannel(probe, options, PRIVATE_FILE).close();
    } catch (AccessDeniedException e) {
      throw untrusted(directory, "this user cannot create files in it, so it is not this user's");
    }
    try {
      return held.getFileAttributeView(probe, PosixFileAttributeView.class, LinkOption.NOFOLLOW_LINKS).readAttributes().owner();
    } finally {
      held.deleteFile(probe);
    }
  }

  // Opens and locks the record. A record that does not exist is created exclusively, so that the file
  // opened is the one created; one that exists is opened only if it is still the file its name led to
  // before, as its key shows once it is locked.
  @Override
  Record lock(String name) throws IOException {
    Path entry = Paths.get(name);
    String heldName = identity + name;
    if (!HELD.add(heldName)) {
      throw inUse(directory, name);
    }
    try {
      PosixFileAttributes before = attributesOf(entry);
      if (before != null && !before.isRegularFile()) {
        throw untrusted(directory, "its record " + name + " is not a regular file");
      }
      Set<OpenOption> options = new HashSet<OpenOption>(Arrays.<OpenOption>asList(StandardOpenOption.READ, StandardOpenOption.WRITE,
          before == null ? StandardOpenOption.CREATE_NEW : StandardOpenOption.READ, LinkOption.NOFOLLOW_LINKS));
      SeekableByteChannel channel;
      try {
        channel = held.newByteChannel(entry, options, PRIVATE_FILE);
      } catch (FileAlreadyExistsException | NoSuchFileException e) {
        // Created or removed meanwhile by another run.
        throw inUse(directory, name);
      } catch (IOException e) {
        if (Files.isSymbolicLink(directory.resolve(entry))) {
          throw untrusted(directory, "its record " + name + " is a link");
        }
        throw e;
      }
      try {
        if (!(channel instanceof FileChannel)) {
          throw untrusted(directory, "its records cannot be locked on this platform");
        }
        FileChannel file = (FileChannel) channel;
        FileLock lock;
        try {
          lock = file.tryLock();
        } catch (OverlappingFileLockException e) {
          lock = null;
        }
        if (lock == null) {
          throw inUse(directory, name);
        }
        PosixFileAttributes attributes = attributesOf(entry);
        if (attributes == null || !attributes.isRegularFile() || !attributes.owner().equals(owner)
            || !attributes.permissions().equals(OWNER_READ_WRITE)) {
          throw untrusted(directory, "its record " + name + " is not a private file of this user's (rw-------)");
        }
        // The file created, or the one the name led to before it was opened; another, or none,
        // once another run removed or replaced it in between.
        Object key = before == null ? attributes.fileKey() : before.fileKey();
        return new PosixRecord(entry, file, key, heldName);
      } catch (IOException | RuntimeException | Error e) {
        channel.close();
        throw e;
      }
    } catch (IOException | RuntimeException | Error e) {
      HELD.remove(heldName);
      throw e;
    }
  }

  // The attributes of the entry itself, not of what a link leads to; null when there is none.
  private PosixFileAttributes attributesOf(Path entry) throws IOException {
    try {
      return held.getFileAttributeView(entry, PosixFileAttributeView.class, LinkOption.NOFOLLOW_LINKS).readAttributes();
    } catch (NoSuchFileException e) {
      return null;
    }
  }

  @Override
  public void close() {
    try {
      held.close();
    } catch (IOException e) {
      log.debug("Could not close the checkpoint directory", e);
    }
  }

  private final class PosixRecord extends Record {
    private final Path entry;
    private final FileChannel file;
    private final Object key;
    private final String heldName;
    private boolean closed;

    PosixRecord(Path entry, FileChannel file, Object key, String heldName) {
      this.entry = entry;
      this.file = file;
      this.key = key;
      this.heldName = heldName;
    }

    @Override
    long size() throws IOException {
      return file.size();
    }

    @Override
    byte[] read() throws IOException {
      return readAll(file);
    }

    @Override
    void append(byte[] bytes) throws IOException {
      ByteBuffer buffer = ByteBuffer.wrap(bytes);
      long position = file.size();
      while (buffer.hasRemaining()) {
        position += file.write(buffer, position);
      }
    }

    @Override
    void truncate(long length) throws IOException {
      file.truncate(length);
    }

    @Override
    void force() throws IOException {
      file.force(true);
    }

    // Whether the name still leads to the file held. Java cannot tell which file an open channel is,
    // and opening the record again would release its lock, so the file key the name leads to now is
    // compared with the one it led to when the record was opened: while the file is held, no other
    // file can have its key. Where the platform gives no file keys, a record is taken as named.
    @Override
    boolean isNamed() throws IOException {
      if (key == null) {
        return true;
      }
      PosixFileAttributes now = attributesOf(entry);
      return now != null && key.equals(now.fileKey());
    }

    @Override
    void delete() throws IOException {
      try {
        if (!isNamed()) {
          throw new java.io.FileNotFoundException("The checkpoint record " + entry + " was removed or replaced while it was held");
        }
        held.deleteFile(entry);
        forceDirectory();
      } finally {
        close();
      }
    }

    @Override
    void forceName() {
      forceDirectory();
    }

    // Makes the directory's entries durable, where the platform can; a removal that does not last
    // leaves a record a later run finds and handles as it would before the removal.
    private void forceDirectory() {
      try (SeekableByteChannel directoryChannel = held.newByteChannel(HERE, EnumSet.of(StandardOpenOption.READ))) {
        if (directoryChannel instanceof FileChannel) {
          ((FileChannel) directoryChannel).force(true);
        }
      } catch (IOException e) {
        log.debug("Could not make the checkpoint directory's entries durable", e);
      }
    }

    @Override
    public void close() {
      if (closed) {
        return;
      }
      closed = true;
      try {
        file.close();
      } catch (IOException e) {
        log.debug("Could not close a checkpoint record", e);
      } finally {
        HELD.remove(heldName);
      }
    }
  }

  private static byte[] readAll(FileChannel channel) throws IOException {
    long size = channel.size();
    if (size > MAX_RECORD_BYTES) {
      throw new IOException("The checkpoint record holds " + size + " bytes, more than a record may hold");
    }
    ByteBuffer buffer = ByteBuffer.allocate((int) size);
    while (buffer.hasRemaining()) {
      if (channel.read(buffer, buffer.position()) < 0) {
        break;
      }
    }
    return Arrays.copyOf(buffer.array(), buffer.position());
  }
}
