package com.files.net;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessDeniedException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SecureDirectoryStream;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileOwnerAttributeView;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// The stage of a download where Java's own file system API keeps it private: on Linux and other
// POSIX systems, whose ACLs, if any, the creation modes below mask. The directory is created
// rwx------ and the file inside it rw-------, without following links.
//
// Where the file system can hold a directory open (a SecureDirectoryStream, as on Linux), the
// download holds its directory open from the start and reaches the file only through it: to check
// it, set its permissions, move it into place and delete it. Replacing the directory's name, or
// anything else in the destination's directory, then cannot change what is published or deleted.
// Elsewhere the file and the directory are reached by name, and before the file is published, and
// before it or the directory is deleted, their names must still lead to what the download created.
//
// A published file gets the permissions and group of the file it replaces; it does not replace a
// file it could not have written, or that belongs to another user. A new file gets the permissions
// a new file in the destination's directory normally gets. File identity comes from file keys.
final class PosixDownloadStage extends DownloadStage {
  private static final Logger log = LoggerFactory.getLogger(PosixDownloadStage.class);
  private static final Set<PosixFilePermission> OWNER_ONLY = PosixFilePermissions.fromString("rwx------");
  private static final Set<PosixFilePermission> OWNER_READ_WRITE = PosixFilePermissions.fromString("rw-------");
  private static final FileAttribute<Set<PosixFilePermission>> PRIVATE_FILE = PosixFilePermissions.asFileAttribute(OWNER_READ_WRITE);
  private static final SecureRandom RANDOM = new SecureRandom();

  private final Path directory;
  private final Object directoryKey;
  private final Path file;
  private final Object fileKey;
  // The directory, held open, or null where the file system cannot hold it.
  private final SecureDirectoryStream<Path> held;
  private SeekableByteChannel channel;
  // The destination's name while publication has taken it without yet moving the file there.
  private Path claim;
  private Object claimKey;

  private PosixDownloadStage(Path directory, Object directoryKey, Path file, Object fileKey, SecureDirectoryStream<Path> held,
      SeekableByteChannel channel) {
    this.directory = directory;
    this.directoryKey = directoryKey;
    this.file = file;
    this.fileKey = fileKey;
    this.held = held;
    this.channel = channel;
  }

  static DownloadStage create(Path destination) throws IOException {
    if (!destination.getFileSystem().supportedFileAttributeViews().contains("posix")) {
      throw new IOException("Downloads to a local path are not supported on this platform, whose files have no POSIX permissions");
    }
    Path directory = Files.createTempDirectory(destination.getParent(), directoryPrefix(destination));
    Path file = directory.resolve(NAME);
    Object directoryKey = null;
    Object fileKey = null;
    SecureDirectoryStream<Path> held = null;
    SeekableByteChannel channel = null;
    try {
      directoryKey = key(directory);
      held = hold(directory, directoryKey);
      Set<OpenOption> options = new HashSet<OpenOption>(Arrays.<OpenOption>asList(
          StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE, LinkOption.NOFOLLOW_LINKS));
      if (held != null) {
        channel = held.newByteChannel(file.getFileName(), options, PRIVATE_FILE);
        PosixFileAttributes created = held.getFileAttributeView(file.getFileName(), PosixFileAttributeView.class, LinkOption.NOFOLLOW_LINKS)
            .readAttributes();
        fileKey = created.fileKey();
        // A directory of another user that this one could open is not the one the download
        // created, whatever its permissions.
        if (!created.owner().equals(held.getFileAttributeView(PosixFileAttributeView.class).readAttributes().owner())) {
          throw replacedDirectory();
        }
      } else {
        channel = FileChannel.open(file, options, PRIVATE_FILE);
        fileKey = key(file);
      }
      return new PosixDownloadStage(directory, directoryKey, file, fileKey, held, channel);
    } catch (IOException | RuntimeException | Error e) {
      if (channel != null) {
        channel.close();
      }
      if (held != null) {
        deleteIfOwned(held, file.getFileName(), fileKey);
        held.close();
      } else if (channel != null) {
        deleteIfOwned(file, fileKey, false);
      }
      // A directory whose name no longer led to the one the download created is left alone.
      if (!(e instanceof StageReplaced)) {
        deleteIfOwned(directory, directoryKey, true);
      }
      throw e;
    }
  }

  // Reopens a retained stage (see DownloadStage.reopen): the directory, not a link, of the recorded
  // key, rwx------, which this user can create files in, so its own (only its owner can create files
  // in a directory of those permissions); held where the file system can; then the file in it, opened
  // without following a link, of the recorded key, this user's, rw-------.
  static DownloadStage reopen(Path destination, Identity identity) throws IOException {
    Path directory = destination.getParent().resolve(identity.directory);
    PosixFileAttributes named;
    try {
      named = Files.readAttributes(directory, PosixFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
    } catch (NoSuchFileException e) {
      return null;
    }
    if (!named.isDirectory() || !String.valueOf(named.fileKey()).equals(identity.directoryId) || !named.permissions().equals(OWNER_ONLY)) {
      throw changed(directory, "its directory is not the one the download created, or is no longer private");
    }
    SecureDirectoryStream<Path> held;
    try {
      held = hold(directory, named.fileKey());
    } catch (StageReplaced e) {
      throw changed(directory, "its directory was replaced");
    }
    Path file = directory.resolve(NAME);
    SeekableByteChannel channel = null;
    boolean returned = false;
    try {
      UserPrincipal user = createdBy(directory, held);
      if (user == null || !user.equals(named.owner())) {
        throw changed(directory, "its directory belongs to another user");
      }
      Set<OpenOption> options = new HashSet<OpenOption>(Arrays.<OpenOption>asList(StandardOpenOption.WRITE, LinkOption.NOFOLLOW_LINKS));
      try {
        channel = held != null ? held.newByteChannel(file.getFileName(), options) : FileChannel.open(file, options);
      } catch (NoSuchFileException e) {
        // Published, or removed: the directory, the download's own, is removed if it is empty.
        if (held != null) {
          held.close();
          held = null;
        }
        deleteIfOwned(directory, named.fileKey(), true);
        return null;
      } catch (IOException e) {
        if (Files.isSymbolicLink(file)) {
          throw changed(directory, "its file is a link");
        }
        throw e;
      }
      PosixFileAttributes attributes = held != null
          ? held.getFileAttributeView(file.getFileName(), PosixFileAttributeView.class, LinkOption.NOFOLLOW_LINKS).readAttributes()
          : Files.readAttributes(file, PosixFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
      if (!attributes.isRegularFile() || !String.valueOf(attributes.fileKey()).equals(identity.fileId) || !attributes.owner().equals(user)
          || !attributes.permissions().equals(OWNER_READ_WRITE)) {
        throw changed(directory, "its file is not the one the download wrote, or is no longer private");
      }
      PosixDownloadStage stage = new PosixDownloadStage(directory, named.fileKey(), file, attributes.fileKey(), held, channel);
      returned = true;
      return stage;
    } finally {
      if (!returned) {
        if (channel != null) {
          channel.close();
        }
        if (held != null) {
          held.close();
        }
      }
    }
  }

  // The user a file this run creates in the directory belongs to, which only the directory's owner
  // can do in a directory of permissions rwx------; null when this user cannot create one there.
  private static UserPrincipal createdBy(Path directory, SecureDirectoryStream<Path> held) throws IOException {
    Path probe = Paths.get(".probe-" + Long.toUnsignedString(RANDOM.nextLong(), 36));
    Set<OpenOption> options = new HashSet<OpenOption>(Arrays.<OpenOption>asList(StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW,
        LinkOption.NOFOLLOW_LINKS));
    try {
      if (held != null) {
        held.newByteChannel(probe, options, PRIVATE_FILE).close();
      } else {
        FileChannel.open(directory.resolve(probe), options, PRIVATE_FILE).close();
      }
    } catch (AccessDeniedException e) {
      return null;
    }
    try {
      return held != null
          ? held.getFileAttributeView(probe, PosixFileAttributeView.class, LinkOption.NOFOLLOW_LINKS).readAttributes().owner()
          : Files.readAttributes(directory.resolve(probe), PosixFileAttributes.class, LinkOption.NOFOLLOW_LINKS).owner();
    } finally {
      if (held != null) {
        held.deleteFile(probe);
      } else {
        Files.delete(directory.resolve(probe));
      }
    }
  }

  // The key of the regular file at path, not following a link (see DownloadStage.fileIdentity).
  static String fileIdentity(Path path) throws IOException {
    try {
      BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
      return attributes.isRegularFile() && attributes.fileKey() != null ? attributes.fileKey().toString() : null;
    } catch (NoSuchFileException e) {
      return null;
    }
  }

  // The stage's directory and file, by their file keys.
  @Override
  Identity identity() throws IOException {
    if (directoryKey == null || fileKey == null) {
      throw new IOException("A checkpoint cannot be kept for a download to " + directory.getParent() + ": its file system gives files no"
          + " identity to find them again by");
    }
    return new Identity(directory.getFileName().toString(), directoryKey.toString(), fileKey.toString());
  }

  // Opens the directory to hold it, where the file system can, once it is sure that it opened the
  // directory it created: the one its name led to, private to its owner.
  private static SecureDirectoryStream<Path> hold(Path directory, Object directoryKey) throws IOException {
    DirectoryStream<Path> stream = Files.newDirectoryStream(directory);
    if (!(stream instanceof SecureDirectoryStream)) {
      stream.close();
      return null;
    }
    SecureDirectoryStream<Path> held = (SecureDirectoryStream<Path>) stream;
    PosixFileAttributeView view = held.getFileAttributeView(PosixFileAttributeView.class);
    PosixFileAttributes attributes = view != null ? view.readAttributes() : null;
    if (attributes == null || !Objects.equals(attributes.fileKey(), directoryKey) || !attributes.permissions().equals(OWNER_ONLY)) {
      held.close();
      if (attributes == null) {
        return null;
      }
      throw replacedDirectory();
    }
    return held;
  }

  @Override
  void restart() throws IOException {
    channel.truncate(0);
    channel.position(0);
  }

  @Override
  void write(byte[] bytes, int count) throws IOException {
    ByteBuffer buffer = ByteBuffer.wrap(bytes, 0, count);
    while (buffer.hasRemaining()) {
      channel.write(buffer);
    }
  }

  @Override
  void writeAt(long position, byte[] bytes, int offset, int count) throws IOException {
    ByteBuffer buffer = ByteBuffer.wrap(bytes, offset, count);
    if (channel instanceof FileChannel) {
      // Positional writes, which do not move the channel's position, may run at once.
      FileChannel file = (FileChannel) channel;
      while (buffer.hasRemaining()) {
        file.write(buffer, position + buffer.position() - offset);
      }
      return;
    }
    synchronized (this) {
      channel.position(position);
      while (buffer.hasRemaining()) {
        channel.write(buffer);
      }
    }
  }

  @Override
  void force() throws IOException {
    if (channel instanceof FileChannel) {
      ((FileChannel) channel).force(true);
    }
  }

  @Override
  void publish(Path target, boolean replace) throws IOException {
    long size = channel.size();
    channel.close();
    channel = null;
    requireOwned(size);
    if (!replace && Files.exists(target, LinkOption.NOFOLLOW_LINKS)) {
      throw new FileAlreadyExistsException(target.toString());
    }
    // A link at target is replaced, not written through; what it leads to decides the access.
    if (Files.exists(target)) {
      keepRestrictionsOf(target);
    } else {
      giveNewFilePermissions();
    }
    if (held != null) {
      publishHeld(target, replace);
    } else if (replace) {
      Files.move(file, target, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
    } else {
      publishWithoutReplacing(target);
    }
  }

  // Moves the file from the held directory to target, which, being absolute, is found by its name
  // as usual. A move replaces what is at target, so to publish without replacing, the name is first
  // taken, atomically, with an empty file no one can open, which the move then replaces.
  private void publishHeld(Path target, boolean replace) throws IOException {
    if (!replace) {
      Files.createFile(target, PosixFilePermissions.asFileAttribute(EnumSet.noneOf(PosixFilePermission.class)));
      claim = target;
      claimKey = key(target);
    }
    held.move(file.getFileName(), held, target);
    claim = null;
  }

  // A hard link fails, atomically, if a file has appeared at target meanwhile; the file's own name
  // is then deleted by close. Where the file system has no hard links, a move that does not replace
  // is used instead.
  private void publishWithoutReplacing(Path target) throws IOException {
    try {
      Files.createLink(target, file);
    } catch (FileAlreadyExistsException e) {
      throw e;
    } catch (UnsupportedOperationException | FileSystemException e) {
      Files.move(file, target);
    }
  }

  private void requireOwned(long size) throws IOException {
    BasicFileAttributes directoryAttributes = held != null
        ? held.getFileAttributeView(BasicFileAttributeView.class).readAttributes()
        : Files.readAttributes(directory, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
    BasicFileAttributes fileAttributes = staged(BasicFileAttributeView.class).readAttributes();
    if (!directoryAttributes.isDirectory() || !Objects.equals(directoryAttributes.fileKey(), directoryKey)
        || !fileAttributes.isRegularFile() || !Objects.equals(fileAttributes.fileKey(), fileKey) || fileAttributes.size() != size) {
      throw replacedFile();
    }
  }

  // A view of the staged file, through the held directory where there is one.
  private <V extends FileAttributeView> V staged(Class<V> type) {
    return held != null
        ? held.getFileAttributeView(file.getFileName(), type, LinkOption.NOFOLLOW_LINKS)
        : Files.getFileAttributeView(file, type, LinkOption.NOFOLLOW_LINKS);
  }

  // The file being replaced keeps its access: the new file gets its permissions and its group. A
  // file this user could not write, or that belongs to another user, is not replaced.
  private void keepRestrictionsOf(Path target) throws IOException {
    if (!Files.isWritable(target)) {
      throw new AccessDeniedException(target.toString(), null, "The destination cannot be written, so the download did not replace it");
    }
    UserPrincipal owner = Files.getOwner(target);
    if (!owner.equals(staged(FileOwnerAttributeView.class).getOwner())) {
      throw new AccessDeniedException(target.toString(), null, "The destination belongs to another user, so the download did not replace it");
    }
    PosixFileAttributes existing = Files.readAttributes(target, PosixFileAttributes.class);
    exposed = true;
    PosixFileAttributeView staged = staged(PosixFileAttributeView.class);
    if (!existing.group().equals(staged.readAttributes().group())) {
      staged.setGroup(existing.group());
    }
    staged.setPermissions(existing.permissions());
  }

  // A new file created in the private directory gets the permissions new files normally get,
  // which the published file then takes on.
  private void giveNewFilePermissions() throws IOException {
    Path ordinary = directory.resolve(NEW_FILE);
    Set<PosixFilePermission> permissions;
    if (held != null) {
      held.newByteChannel(ordinary.getFileName(), EnumSet.of(StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)).close();
      try {
        permissions = held.getFileAttributeView(ordinary.getFileName(), PosixFileAttributeView.class, LinkOption.NOFOLLOW_LINKS)
            .readAttributes().permissions();
      } finally {
        held.deleteFile(ordinary.getFileName());
      }
    } else {
      Files.createFile(ordinary);
      try {
        permissions = Files.getPosixFilePermissions(ordinary, LinkOption.NOFOLLOW_LINKS);
      } finally {
        Files.delete(ordinary);
      }
    }
    exposed = true;
    staged(PosixFileAttributeView.class).setPermissions(new HashSet<PosixFilePermission>(permissions));
  }

  // Deletes what the download created: the file, unless publication moved it, a name it took for
  // publication and did not use, and then the directory. A replacement is left where it is. A
  // retained stage only closes its handles.
  @Override
  public void close() {
    if (channel != null) {
      try {
        channel.close();
      } catch (IOException e) {
        log.debug("Could not close the unfinished download", e);
      }
    }
    if (retained) {
      if (held != null) {
        try {
          held.close();
        } catch (IOException e) {
          log.debug("Could not close the unfinished download's directory", e);
        }
      }
      return;
    }
    if (claim != null) {
      deleteIfOwned(claim, claimKey, false);
    }
    if (held != null) {
      deleteIfOwned(held, file.getFileName(), fileKey);
      try {
        held.close();
      } catch (IOException e) {
        log.debug("Could not close the unfinished download's directory", e);
      }
    } else {
      deleteIfOwned(file, fileKey, false);
    }
    deleteIfOwned(directory, directoryKey, true);
  }

  // Deletes the file of that name in the held directory, if it is the one the download created.
  private static void deleteIfOwned(SecureDirectoryStream<Path> held, Path name, Object expectedKey) {
    try {
      BasicFileAttributes attributes = held.getFileAttributeView(name, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS)
          .readAttributes();
      if (!attributes.isRegularFile() || !Objects.equals(attributes.fileKey(), expectedKey)) {
        warnReplaced("file");
        return;
      }
      held.deleteFile(name);
    } catch (NoSuchFileException e) {
      // Published, or already gone.
    } catch (IOException e) {
      warnNotDeleted("file", e.getClass().getSimpleName());
    }
  }
}
