package com.files.nio;

import com.files.exceptions.ApiErrorException;
import com.files.exceptions.NotImplementedException;
import com.files.models.File;
import com.files.models.FileAction;
import com.files.util.PathUtils;
import java.io.IOException;
import java.net.URI;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessDeniedException;
import java.nio.file.AccessMode;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.ProviderMismatchException;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileTime;
import java.nio.file.spi.FileSystemProvider;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/**
 * The provider of the Files.com file systems that {@link FilesComFileSystems} makes. It is not
 * installed as a service and keeps no file systems: each file system is made, and bound to its
 * client, only by {@code FilesComFileSystems}, so a URI never finds one, nor authenticates
 * anything. The operations of {@code java.nio.file.Files} on a {@link FilesPath} reach it as for
 * any provider; {@link FilesComFileSystems} describes what each does.
 *
 * <p>A failure of the API is thrown as a native {@code IOException} with the SDK's exception as
 * its cause; only an exact structured error type, in the operation it is expected in, becomes
 * {@code NoSuchFileException}, {@code FileAlreadyExistsException},
 * {@code DirectoryNotEmptyException}, {@code NotDirectoryException} or
 * {@code AccessDeniedException}. What the SDK does not implement throws
 * {@code UnsupportedOperationException}, whose cause is a {@link NotImplementedException}, before
 * anything is changed.
 */
public final class FilesFileSystemProvider extends FileSystemProvider {
  static final FilesFileSystemProvider INSTANCE = new FilesFileSystemProvider();

  private FilesFileSystemProvider() {
  }

  /** Returns {@code files-com}, the scheme of a Files.com path's URI. */
  @Override
  public String getScheme() {
    return "files-com";
  }

  /** Throws {@code UnsupportedOperationException}: only {@link FilesComFileSystems} makes one. */
  @Override
  public FileSystem newFileSystem(URI uri, Map<String, ?> env) {
    throw FilesFileSystem.unsupported("A Files.com file system is made only by FilesComFileSystems, bound to its client");
  }

  /** Throws {@code FileSystemNotFoundException}: no file system is found by URI. */
  @Override
  public FileSystem getFileSystem(URI uri) {
    throw new FileSystemNotFoundException("A Files.com file system is not found by URI; keep the one FilesComFileSystems made");
  }

  /** Throws {@code FileSystemNotFoundException}: no file system is found by URI. */
  @Override
  public Path getPath(URI uri) {
    throw new FileSystemNotFoundException("A Files.com path is not found by URI; use the file system's getPath");
  }

  @Override
  public SeekableByteChannel newByteChannel(Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs)
      throws IOException {
    FilesPath file = filesPath(path);
    return FilesByteChannel.open(file.getFileSystem(), file, options, attrs);
  }

  /** Throws {@code UnsupportedOperationException}: a Files.com file has no {@code FileChannel}. */
  @Override
  public FileChannel newFileChannel(Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs) {
    throw FilesFileSystem.unsupported("A Files.com file has no FileChannel: it cannot be mapped or locked; use newByteChannel");
  }

  /** Throws {@code UnsupportedOperationException}: a Files.com file has no asynchronous channel. */
  @Override
  public AsynchronousFileChannel newAsynchronousFileChannel(Path path, Set<? extends OpenOption> options,
      ExecutorService executor, FileAttribute<?>... attrs) {
    throw FilesFileSystem.unsupported("A Files.com file has no asynchronous channel; use newByteChannel");
  }

  @Override
  public DirectoryStream<Path> newDirectoryStream(Path dir, DirectoryStream.Filter<? super Path> filter) throws IOException {
    FilesPath directory = filesPath(dir);
    if (!attributes(directory, FilesFileSystem.Operation.LIST).isDirectory()) {
      throw new NotDirectoryException(directory.toString());
    }
    return directory.getFileSystem().list(directory, filter);
  }

  @Override
  public void createDirectory(Path dir, FileAttribute<?>... attrs) throws IOException {
    if (attrs.length > 0) {
      throw FilesFileSystem.unsupported("A Files.com folder cannot be created with initial attributes");
    }
    FilesPath directory = filesPath(dir);
    FilesFileSystem fileSystem = directory.getFileSystem();
    fileSystem.checkOpen();
    if (directory.isRoot() || fileSystem.isBeingWritten(directory)) {
      throw new FileAlreadyExistsException(directory.toString());
    }
    HashMap<String, Object> parameters = new HashMap<String, Object>();
    parameters.put("mkdir_parents", false);
    try {
      fileSystem.client().folders().create(directory.apiPath(), parameters, fileSystem.requestOptions());
    } catch (RuntimeException e) {
      throw fileSystem.nativeFailure(FilesFileSystem.Operation.CREATE_DIRECTORY, directory, null, e);
    }
  }

  @Override
  public void delete(Path path) throws IOException {
    FilesPath file = filesPath(path);
    FilesFileSystem fileSystem = file.getFileSystem();
    fileSystem.checkOpen();
    if (file.isRoot()) {
      throw new FileSystemException(file.toString(), null, "The root cannot be deleted");
    }
    refuseIfBeingWritten(file);
    HashMap<String, Object> parameters = new HashMap<String, Object>();
    parameters.put("recursive", false);
    FileAction action;
    try {
      action = fileSystem.client().files().beginDelete(file.apiPath(), parameters, fileSystem.requestOptions());
    } catch (RuntimeException e) {
      IOException failure = fileSystem.nativeFailure(FilesFileSystem.Operation.DELETE, file, null, e);
      if (isUnresolvedPath(e)) {
        // The API cannot resolve the path this request names, so the file is missing as the API
        // names files: either nothing is there, or a stored path the API cannot tell apart, which it
        // reports alike. Only this first request is read so; a not-found while the delete is waited
        // for, or from the check after it, is not the file missing.
        NoSuchFileException missing = new NoSuchFileException(file.toString(), null, ((FileSystemException) failure).getReason());
        missing.initCause(e);
        throw missing;
      }
      throw failure;
    }
    fileSystem.awaitAction(action, FilesFileSystem.Operation.DELETE, file, null);
    // A finished, or skipped, action is not the deletion itself: the file must be gone.
    try {
      fileSystem.stat(file, FilesFileSystem.Operation.DELETE);
    } catch (NoSuchFileException gone) {
      return;
    }
    throw new FileSystemException(file.toString(), null,
        "The API finished the delete with status " + action.getStatus() + ", but the file is still there");
  }

  // Whether failure is the structured error the API gives a request whose path it cannot resolve:
  // exactly the type not-found, with the code 404 its body reports. That code is the body's; the
  // response's own status is not kept apart from it, nor compared with it.
  private static boolean isUnresolvedPath(RuntimeException failure) {
    if (!(failure instanceof ApiErrorException)) {
      return false;
    }
    ApiErrorException error = (ApiErrorException) failure;
    return error.getHttpCode() == 404 && "not-found".equals(error.getType());
  }

  @Override
  public void copy(Path source, Path target, CopyOption... options) throws IOException {
    boolean replace = false;
    for (CopyOption option : options) {
      if (option == StandardCopyOption.REPLACE_EXISTING) {
        replace = true;
      } else if (option == LinkOption.NOFOLLOW_LINKS) {
        // There are no links.
      } else if (option == StandardCopyOption.COPY_ATTRIBUTES) {
        throw FilesFileSystem.unsupported("COPY_ATTRIBUTES is not supported: the API copies what it copies, and keeps no"
            + " attributes this file system could confirm");
      } else if (option == null) {
        throw new NullPointerException("option");
      } else {
        throw FilesFileSystem.unsupported("The copy option " + option + " is not supported for a Files.com file");
      }
    }
    transfer(filesPath(source), target, replace, FilesFileSystem.Operation.COPY);
  }

  @Override
  public void move(Path source, Path target, CopyOption... options) throws IOException {
    boolean replace = false;
    for (CopyOption option : options) {
      if (option == StandardCopyOption.REPLACE_EXISTING) {
        replace = true;
      } else if (option == LinkOption.NOFOLLOW_LINKS) {
        // There are no links.
      } else if (option == StandardCopyOption.ATOMIC_MOVE) {
        throw FilesFileSystem.unsupported("ATOMIC_MOVE is not supported: the Files.com API does not promise an atomic move");
      } else if (option == null) {
        throw new NullPointerException("option");
      } else {
        throw FilesFileSystem.unsupported("The move option " + option + " is not supported for a Files.com file");
      }
    }
    transfer(filesPath(source), target, replace, FilesFileSystem.Operation.MOVE);
  }

  // Copies or moves from to target, within one file system, and waits until the target is there. A
  // target that Files.com compares as the same file as from (PathUtils.isSame), in another spelling
  // or the same, is a copy or move to itself, which changes nothing once from is found to exist:
  // nothing is deleted, copied or moved.
  private void transfer(FilesPath from, Path target, boolean replace, FilesFileSystem.Operation operation) throws IOException {
    if (!(target instanceof FilesPath)) {
      throw new ProviderMismatchException("The target " + target + " is not a Files.com path");
    }
    FilesPath to = (FilesPath) target;
    FilesFileSystem fileSystem = from.getFileSystem();
    if (to.getFileSystem() != fileSystem) {
      throw FilesFileSystem.unsupported("Copying or moving between two Files.com file systems, each with its own client, is not"
          + " supported; copy through streams instead");
    }
    fileSystem.checkOpen();
    if (operation == FilesFileSystem.Operation.MOVE && from.isRoot()) {
      throw new FileSystemException(from.toString(), to.toString(), "The root cannot be moved");
    }
    refuseIfBeingWritten(from);
    refuseIfBeingWritten(to);
    Attributes source = attributes(from, operation);
    if (PathUtils.isSame(from.apiPath(), to.apiPath())) {
      return;
    }
    if (to.isRoot()) {
      throw new FileAlreadyExistsException(to.toString());
    }
    Attributes existing;
    try {
      existing = attributes(to, operation);
    } catch (NoSuchFileException absent) {
      existing = null;
    }
    if (existing != null) {
      if (!replace) {
        throw new FileAlreadyExistsException(to.toString());
      }
      if (existing.isDirectory() != source.isDirectory()) {
        throw FilesFileSystem.unsupported("Replacing a folder with a file, or a file with a folder, is not supported");
      }
      if (existing.isDirectory()) {
        // Only an empty folder is replaced: deleting a folder that is not empty fails.
        delete(to);
      }
    }
    if (operation == FilesFileSystem.Operation.COPY && source.isDirectory()) {
      // A folder's copy is an empty folder; its entries are not copied.
      createDirectory(to);
      return;
    }
    HashMap<String, Object> parameters = new HashMap<String, Object>();
    parameters.put("destination", to.apiPath());
    parameters.put("overwrite", replace);
    FileAction action;
    try {
      action = operation == FilesFileSystem.Operation.COPY
          ? fileSystem.client().files().copy(from.apiPath(), parameters, fileSystem.requestOptions())
          : fileSystem.client().files().move(from.apiPath(), parameters, fileSystem.requestOptions());
    } catch (RuntimeException e) {
      throw fileSystem.nativeFailure(operation, from, to, e);
    }
    fileSystem.awaitAction(action, operation, from, to);
    if ("skipped".equals(action.getStatus())) {
      // A skipped action did no work: a target already there is not the copy or move asked for.
      throw new FileSystemException(from.toString(), to.toString(),
          "The API skipped the " + (operation == FilesFileSystem.Operation.COPY ? "copy" : "move") + ", so the target was not confirmed to"
              + " be replaced");
    }
    // A finished action is not the target being there: it must be.
    try {
      fileSystem.stat(to, operation);
    } catch (NoSuchFileException missing) {
      FileSystemException unconfirmed = new FileSystemException(from.toString(), to.toString(),
          "The API finished with status " + action.getStatus() + ", but the target is not there");
      unconfirmed.initCause(missing);
      throw unconfirmed;
    }
  }

  @Override
  public boolean isSameFile(Path path, Path path2) throws IOException {
    if (path.equals(path2)) {
      return true;
    }
    if (!(path instanceof FilesPath) || !(path2 instanceof FilesPath) || path.getFileSystem() != path2.getFileSystem()) {
      return false;
    }
    return path.toRealPath().equals(path2.toRealPath());
  }

  /** Returns {@code false}: Files.com has no hidden files. */
  @Override
  public boolean isHidden(Path path) {
    filesPath(path).getFileSystem().checkOpen();
    return false;
  }

  /** Throws {@code UnsupportedOperationException}: Files.com reports no file store, nor its space. */
  @Override
  public FileStore getFileStore(Path path) {
    throw FilesFileSystem.unsupported("Files.com reports no file store, nor its space");
  }

  /**
   * Checks that the file exists and, for {@code READ} and {@code WRITE}, that its Files.com
   * permissions include {@code r} (or {@code l}, for a folder) and {@code w}. Permissions the API
   * does not give are not taken as a denial. Files.com has no execute permission:
   * {@code EXECUTE} throws {@code AccessDeniedException}, whose cause is a
   * {@link NotImplementedException}.
   */
  @Override
  public void checkAccess(Path path, AccessMode... modes) throws IOException {
    FilesPath file = filesPath(path);
    Attributes attributes = attributes(file, FilesFileSystem.Operation.STAT);
    for (AccessMode mode : modes) {
      if (mode == AccessMode.EXECUTE) {
        AccessDeniedException denied = new AccessDeniedException(file.toString(), null, "Files.com has no execute permission");
        denied.initCause(new NotImplementedException("Files.com has no execute permission"));
        throw denied;
      }
      String letters = mode == AccessMode.WRITE ? "w" : attributes.isDirectory() ? "rl" : "r";
      if (!attributes.allows(letters)) {
        throw new AccessDeniedException(file.toString(), null, "The Files.com permissions " + attributes.permissions()
            + " do not allow " + mode);
      }
    }
  }

  @Override
  public <V extends FileAttributeView> V getFileAttributeView(Path path, Class<V> type, LinkOption... options) {
    FilesPath file = filesPath(path);
    return type == BasicFileAttributeView.class ? type.cast(new TimesView(file)) : null;
  }

  /**
   * Reads {@code BasicFileAttributes}, or {@link Attributes}, which adds the Files.com
   * permissions. There are no links, so {@code options} change nothing.
   */
  @Override
  public <A extends BasicFileAttributes> A readAttributes(Path path, Class<A> type, LinkOption... options) throws IOException {
    if (type != BasicFileAttributes.class && type != Attributes.class) {
      throw FilesFileSystem.unsupported("The attributes " + type.getName() + " are not supported for a Files.com file");
    }
    return type.cast(attributes(filesPath(path), FilesFileSystem.Operation.STAT));
  }

  /**
   * Reads the {@code basic} attributes, as {@code Files.readAttributes} names them, or the
   * {@code files} attributes: the basic ones and {@code permissions}.
   */
  @Override
  public Map<String, Object> readAttributes(Path path, String attributes, LinkOption... options) throws IOException {
    FilesPath file = filesPath(path);
    int colon = attributes.indexOf(':');
    String view = colon < 0 ? "basic" : attributes.substring(0, colon);
    if (!view.equals("basic") && !view.equals("files")) {
      throw FilesFileSystem.unsupported("The attribute view " + view + " is not supported for a Files.com file");
    }
    List<String> basic = Arrays.asList("lastModifiedTime", "lastAccessTime", "creationTime", "size", "isRegularFile",
        "isDirectory", "isSymbolicLink", "isOther", "fileKey");
    List<String> names = Arrays.asList(attributes.substring(colon + 1).split(","));
    for (String name : names) {
      if (!name.equals("*") && !basic.contains(name) && !(view.equals("files") && name.equals("permissions"))) {
        throw new IllegalArgumentException("'" + name + "' not recognized");
      }
    }
    Attributes read = attributes(file, FilesFileSystem.Operation.STAT);
    Map<String, Object> values = new HashMap<String, Object>();
    for (String name : names) {
      for (String each : name.equals("*") ? basic : Arrays.asList(name)) {
        values.put(each, read.value(each));
      }
      if (view.equals("files") && (name.equals("*") || name.equals("permissions"))) {
        values.put("permissions", read.permissions());
      }
    }
    return values;
  }

  /**
   * Sets {@code lastModifiedTime}, as {@link BasicFileAttributeView#setTimes} does; every other
   * attribute is either read-only, an {@code IllegalArgumentException}, or not supported.
   */
  @Override
  public void setAttribute(Path path, String attribute, Object value, LinkOption... options) throws IOException {
    FilesPath file = filesPath(path);
    int colon = attribute.indexOf(':');
    String view = colon < 0 ? "basic" : attribute.substring(0, colon);
    String name = attribute.substring(colon + 1);
    if (view.equals("files") && name.equals("permissions")) {
      throw FilesFileSystem.unsupported("Files.com permissions cannot be set through the file system");
    }
    if (!view.equals("basic") && !view.equals("files")) {
      throw FilesFileSystem.unsupported("The attribute view " + view + " is not supported for a Files.com file");
    }
    if (name.equals("lastModifiedTime")) {
      setTimes(file, (FileTime) value, null, null);
    } else if (name.equals("lastAccessTime")) {
      setTimes(file, null, (FileTime) value, null);
    } else if (name.equals("creationTime")) {
      setTimes(file, null, null, (FileTime) value);
    } else {
      throw new IllegalArgumentException("'" + name + "' not recognized");
    }
  }

  /** Throws {@code UnsupportedOperationException}: Files.com has no symbolic links. */
  @Override
  public void createSymbolicLink(Path link, Path target, FileAttribute<?>... attrs) {
    throw FilesFileSystem.unsupported("Files.com has no symbolic links");
  }

  /** Throws {@code UnsupportedOperationException}: Files.com has no hard links. */
  @Override
  public void createLink(Path link, Path existing) {
    throw FilesFileSystem.unsupported("Files.com has no hard links");
  }

  /** Throws {@code UnsupportedOperationException}: Files.com has no symbolic links. */
  @Override
  public Path readSymbolicLink(Path link) {
    throw FilesFileSystem.unsupported("Files.com has no symbolic links");
  }

  private static FilesPath filesPath(Path path) {
    if (path == null) {
      throw new NullPointerException("path");
    }
    if (!(path instanceof FilesPath)) {
      throw new ProviderMismatchException("The path " + path + " is not a Files.com path");
    }
    return (FilesPath) path;
  }

  private static void refuseIfBeingWritten(FilesPath path) throws FileSystemException {
    if (path.getFileSystem().isBeingWritten(path)) {
      throw new FileSystemException(path.toString(), null,
          "A channel of this file system is writing the file; it is published when that channel closes");
    }
  }

  // The file's attributes: the root's, the pending view of a file a channel is writing, or the API's.
  private static Attributes attributes(FilesPath path, FilesFileSystem.Operation operation) throws IOException {
    FilesFileSystem fileSystem = path.getFileSystem();
    fileSystem.checkOpen();
    if (path.isRoot()) {
      return Attributes.ROOT;
    }
    FilesByteChannel writer = fileSystem.writer(path);
    if (writer != null) {
      return writer.pendingAttributes();
    }
    return Attributes.of(fileSystem.stat(path, operation), null);
  }

  // Sets provided_mtime; an access or creation time is refused before anything is changed.
  private static void setTimes(FilesPath path, FileTime lastModifiedTime, FileTime lastAccessTime, FileTime createTime)
      throws IOException {
    if (lastAccessTime != null || createTime != null) {
      throw FilesFileSystem.unsupported("Files.com keeps no access or creation time to set; only the last-modified time can be set");
    }
    if (lastModifiedTime == null) {
      return;
    }
    FilesFileSystem fileSystem = path.getFileSystem();
    fileSystem.checkOpen();
    if (path.isRoot()) {
      throw new FileSystemException(path.toString(), null, "The root's time cannot be set");
    }
    refuseIfBeingWritten(path);
    HashMap<String, Object> parameters = new HashMap<String, Object>();
    parameters.put("provided_mtime", lastModifiedTime.toInstant().toString());
    try {
      fileSystem.client().files().update(path.apiPath(), parameters, fileSystem.requestOptions());
    } catch (RuntimeException e) {
      throw fileSystem.nativeFailure(FilesFileSystem.Operation.SET_TIMES, path, null, e);
    }
  }

  /**
   * The attributes of a Files.com file or folder, as the API describes it. The last-modified time is
   * its {@code provided_mtime} when it has one, else its {@code mtime}, else the epoch. Files.com
   * keeps no access time: the last-access time is the last-modified time. The creation time is its
   * {@code created_at}, else the last-modified time. The size is the API's, 0 when it gives none.
   * There are no links, and no file key. The root is a folder whose times are the epoch.
   *
   * <p>While a channel of the same file system writes a file, its attributes are a pending view:
   * as the API described the file when the channel opened, with the size of the channel's copy.
   */
  public static final class Attributes implements BasicFileAttributes {
    private static final FileTime EPOCH = FileTime.fromMillis(0);
    static final Attributes ROOT = new Attributes("directory", 0, EPOCH, EPOCH, null);

    private final String type;
    private final long size;
    private final FileTime lastModifiedTime;
    private final FileTime creationTime;
    private final String permissions;

    private Attributes(String type, long size, FileTime lastModifiedTime, FileTime creationTime, String permissions) {
      this.type = type;
      this.size = size;
      this.lastModifiedTime = lastModifiedTime;
      this.creationTime = creationTime;
      this.permissions = permissions;
    }

    // The attributes of file, with size in place of the API's when it is not null. A file the API did
    // not describe, as an empty publication can leave it, is a file with the defaults above.
    static Attributes of(File file, Long size) {
      if (file == null) {
        return new Attributes("file", size != null ? size : 0, EPOCH, EPOCH, null);
      }
      Date modified = file.getProvidedMtime() != null ? file.getProvidedMtime() : file.getMtime();
      FileTime lastModified = modified != null ? FileTime.fromMillis(modified.getTime()) : EPOCH;
      FileTime created = file.getCreatedAt() != null ? FileTime.fromMillis(file.getCreatedAt().getTime()) : lastModified;
      long bytes = size != null ? size : file.getSize() != null ? file.getSize() : 0;
      return new Attributes(file.getType(), bytes, lastModified, created, file.getPermissions());
    }

    @Override
    public FileTime lastModifiedTime() {
      return lastModifiedTime;
    }

    @Override
    public FileTime lastAccessTime() {
      return lastModifiedTime;
    }

    @Override
    public FileTime creationTime() {
      return creationTime;
    }

    @Override
    public boolean isRegularFile() {
      return "file".equals(type);
    }

    @Override
    public boolean isDirectory() {
      return "directory".equals(type);
    }

    @Override
    public boolean isSymbolicLink() {
      return false;
    }

    @Override
    public boolean isOther() {
      return !isRegularFile() && !isDirectory();
    }

    @Override
    public long size() {
      return size;
    }

    @Override
    public Object fileKey() {
      return null;
    }

    /**
     * Returns the current user's Files.com permissions on the file, as the API gives them: any of
     * {@code r} (read), {@code w} (write), {@code d} (delete) and {@code l} (list).
     *
     * @return the permission letters, or {@code null} when the API gives none
     */
    public String permissions() {
      return permissions;
    }

    // Whether the permissions include one of letters; permissions not given deny nothing.
    boolean allows(String letters) {
      if (permissions == null) {
        return true;
      }
      for (char letter : letters.toCharArray()) {
        if (permissions.indexOf(letter) >= 0) {
          return true;
        }
      }
      return false;
    }

    Object value(String name) {
      switch (name) {
        case "lastModifiedTime":
          return lastModifiedTime();
        case "lastAccessTime":
          return lastAccessTime();
        case "creationTime":
          return creationTime();
        case "size":
          return size();
        case "isRegularFile":
          return isRegularFile();
        case "isDirectory":
          return isDirectory();
        case "isSymbolicLink":
          return isSymbolicLink();
        case "isOther":
          return isOther();
        default:
          return fileKey();
      }
    }
  }

  // The basic view: reads the attributes, and sets the last-modified time as provided_mtime.
  private static final class TimesView implements BasicFileAttributeView {
    private final FilesPath path;

    TimesView(FilesPath path) {
      this.path = path;
    }

    @Override
    public String name() {
      return "basic";
    }

    @Override
    public BasicFileAttributes readAttributes() throws IOException {
      return attributes(path, FilesFileSystem.Operation.STAT);
    }

    @Override
    public void setTimes(FileTime lastModifiedTime, FileTime lastAccessTime, FileTime createTime) throws IOException {
      FilesFileSystemProvider.setTimes(path, lastModifiedTime, lastAccessTime, createTime);
    }
  }
}
