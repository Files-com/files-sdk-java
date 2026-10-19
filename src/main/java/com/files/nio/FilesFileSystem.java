package com.files.nio;

import com.files.CancellationToken;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.exceptions.ApiErrorException;
import com.files.exceptions.NotImplementedException;
import com.files.models.File;
import com.files.models.FileAction;
import com.files.net.ClientRuntime;
import com.files.net.DownloadOptions;
import com.files.net.UploadOptions;
import com.files.util.PathUtils;
import java.io.Closeable;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.ClosedFileSystemException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemException;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.WatchService;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * A {@code java.nio.file} file system of a Files.com site, bound to one {@link FilesClient}; made by
 * {@link FilesComFileSystems}, which describes what its operations do.
 *
 * <p>It borrows the client: closing the file system never closes the client or its transport, and
 * the client's other users are not affected. Its paths are {@link FilesPath}s, which carry an
 * opaque mount ID, never credentials.
 *
 * <p>It is safe for use by several threads. Closing it closes every channel and directory stream
 * it opened, once each: a channel open for writing publishes what it holds, as its own close
 * would, and a channel that another thread is already closing is waited for, so that its
 * publication finishes, or fails with its copy kept, before anything is cancelled. The first
 * failure of those closes is thrown, with the later ones suppressed in it; every handle is closed
 * even so. Then the requests the file system made with its own cancellation, such as a description
 * or a page of a listing, still in flight are cancelled. A copy, move or delete already waiting for
 * the API to finish its action is not: that wait ends when the action does, or at the file system's
 * action timeout. Using the file system afterwards throws {@code ClosedFileSystemException}; closing
 * it again has no effect.
 */
public final class FilesFileSystem extends FileSystem {
  // The operation a failure happened in, which decides what a structured API error means.
  enum Operation {
    STAT, READ, WRITE, LIST, CREATE_DIRECTORY, DELETE, COPY, MOVE, SET_TIMES
  }

  // The structured API errors that deny the user access to a path, and so are native access
  // denials; account, authentication and other errors are not.
  private static final Set<String> ACCESS_DENIED = new HashSet<String>(Arrays.asList(
      "not-authorized/api-key-is-path-restricted",
      "not-authorized/folder-admin-permission-required",
      "not-authorized/full-permission-required",
      "not-authorized/insufficient-permission-for-params",
      "not-authorized/preview-only-permission-cannot-download",
      "not-authorized/read-only-session",
      "not-authorized/read-permission-required",
      "not-authorized/site-files-are-immutable",
      "not-authorized/write-permission-required"));

  private final FilesFileSystemProvider provider;
  private final FilesClient client;
  private final String mountId = UUID.randomUUID().toString();
  private final FilesPath workingDirectory;
  private final Path stageDirectory;
  private final long stageQuotaBytes;
  private final Duration actionTimeout;
  // Cancels the file system's requests still in flight once it is closed.
  private final CancellationToken closing = new CancellationToken();

  private final Object lock = new Object();
  private volatile boolean open = true;
  private volatile boolean cancelled;
  // Guarded by lock.
  private long reservedBytes;
  private final Set<Closeable> handles = new LinkedHashSet<Closeable>();
  // The writer of each remote file, by the key Files.com compares paths with (writerKey): the
  // channel, or its claim while the channel opens.
  private final Map<String, Object> writers = new HashMap<String, Object>();

  FilesFileSystem(FilesFileSystemProvider provider, FilesClient client, String workingDirectory, Path stageDirectory,
      long stageQuotaBytes, Duration actionTimeout) {
    this.provider = provider;
    this.client = client;
    this.workingDirectory = FilesPath.parse(this, workingDirectory);
    this.stageDirectory = stageDirectory;
    this.stageQuotaBytes = stageQuotaBytes;
    this.actionTimeout = actionTimeout;
  }

  @Override
  public FilesFileSystemProvider provider() {
    return provider;
  }

  /**
   * Closes the file system, as the class description describes. The borrowed client stays open.
   *
   * @throws IOException the first failure of closing a channel, such as a publication that failed
   */
  @Override
  public void close() throws IOException {
    List<Closeable> owned;
    synchronized (lock) {
      if (!open) {
        return;
      }
      open = false;
      owned = new ArrayList<Closeable>(handles);
    }
    IOException first = null;
    for (Closeable handle : owned) {
      try {
        handle.close();
      } catch (IOException e) {
        if (first == null) {
          first = e;
        } else {
          first.addSuppressed(e);
        }
      }
    }
    cancelled = true;
    closing.cancel();
    if (first != null) {
      throw first;
    }
  }

  @Override
  public boolean isOpen() {
    return open;
  }

  /** Returns {@code false}: files can be written, as the user's Files.com permissions allow. */
  @Override
  public boolean isReadOnly() {
    return false;
  }

  @Override
  public String getSeparator() {
    return "/";
  }

  @Override
  public Iterable<Path> getRootDirectories() {
    return Collections.<Path>singletonList(new FilesPath(this, true, new String[0]));
  }

  /** Returns no file stores: Files.com reports none, nor their space. */
  @Override
  public Iterable<FileStore> getFileStores() {
    return Collections.emptyList();
  }

  /**
   * Returns {@code basic}, and {@code files}, which adds {@code permissions}, the Files.com
   * permission letters described by {@link FilesFileSystemProvider.Attributes#permissions()}.
   */
  @Override
  public Set<String> supportedFileAttributeViews() {
    return Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("basic", "files")));
  }

  @Override
  public Path getPath(String first, String... more) {
    return FilesPath.parse(this, first, more);
  }

  /**
   * Returns a matcher of the {@code glob} or {@code regex} syntax, as
   * {@link FileSystem#getPathMatcher} describes them, matched against the path's string. In a glob,
   * {@code *} and {@code ?} do not cross a {@code /}, {@code **} does, and {@code [...]},
   * {@code [!...]} and {@code {a,b}} are supported, without nested groups.
   *
   * @throws UnsupportedOperationException for any other syntax, with a
   *     {@link NotImplementedException} cause
   */
  @Override
  public PathMatcher getPathMatcher(String syntaxAndPattern) {
    int colon = syntaxAndPattern.indexOf(':');
    if (colon <= 0) {
      throw new IllegalArgumentException("Expected syntax:pattern, not " + syntaxAndPattern);
    }
    String syntax = syntaxAndPattern.substring(0, colon);
    String input = syntaxAndPattern.substring(colon + 1);
    final Pattern pattern;
    if (syntax.equalsIgnoreCase("glob")) {
      pattern = Pattern.compile(globToRegex(input));
    } else if (syntax.equalsIgnoreCase("regex")) {
      pattern = Pattern.compile(input);
    } else {
      throw unsupported("The path matcher syntax " + syntax + " is not supported; use glob or regex");
    }
    return path -> pattern.matcher(path.toString()).matches();
  }

  /** Throws {@code UnsupportedOperationException}: Files.com has no user principals here. */
  @Override
  public UserPrincipalLookupService getUserPrincipalLookupService() {
    throw unsupported("A Files.com file system has no user principal lookup");
  }

  /** Throws {@code UnsupportedOperationException}: a Files.com path cannot be watched. */
  @Override
  public WatchService newWatchService() {
    throw unsupported("A Files.com path cannot be watched");
  }

  static UnsupportedOperationException unsupported(String message) {
    return new UnsupportedOperationException(message, new NotImplementedException(message));
  }

  String mountId() {
    return mountId;
  }

  FilesPath workingDirectory() {
    return workingDirectory;
  }

  FilesClient client() {
    return client;
  }

  Path stageDirectory() {
    return stageDirectory;
  }

  void checkOpen() {
    if (!open) {
      throw new ClosedFileSystemException();
    }
  }

  // Adds a handle that closing the file system closes; false, leaving it out, once it is closed.
  boolean register(Closeable handle) {
    synchronized (lock) {
      if (!open) {
        return false;
      }
      handles.add(handle);
      return true;
    }
  }

  void unregister(Closeable handle) {
    synchronized (lock) {
      handles.remove(handle);
    }
  }

  // Claims path for one writer, before anything is changed; a second writer is refused.
  Object claimWriter(FilesPath path) throws FileSystemException {
    Object claim = new Object();
    synchronized (lock) {
      checkOpen();
      if (writers.containsKey(writerKey(path))) {
        throw new FileSystemException(path.toString(), null,
            "Another channel of this file system is writing the file; it is published when that channel closes");
      }
      writers.put(writerKey(path), claim);
    }
    return claim;
  }

  void bindWriter(FilesPath path, Object claim, FilesByteChannel channel) {
    synchronized (lock) {
      if (writers.get(writerKey(path)) == claim) {
        writers.put(writerKey(path), channel);
      }
    }
  }

  // Ends a claim, or a channel's writing, of path; another writer's is left as it is.
  void releaseWriter(FilesPath path, Object claimOrChannel) {
    synchronized (lock) {
      if (writers.get(writerKey(path)) == claimOrChannel) {
        writers.remove(writerKey(path));
      }
    }
  }

  boolean isBeingWritten(FilesPath path) {
    synchronized (lock) {
      return writers.containsKey(writerKey(path));
    }
  }

  // The channel writing path, once open; null when there is none.
  FilesByteChannel writer(FilesPath path) {
    synchronized (lock) {
      Object writer = writers.get(writerKey(path));
      return writer instanceof FilesByteChannel ? (FilesByteChannel) writer : null;
    }
  }

  // The remote file a path names, as Files.com compares paths (PathUtils): spellings that differ in
  // case, or in what its comparison map treats alike, name one file, and so have one writer. The
  // path itself, and what is sent to the API, keep their own spelling.
  private static String writerKey(FilesPath path) {
    return PathUtils.normalize_for_comparison(path.apiPath());
  }

  // Reserves bytes of the stage quota for path's local copy, or refuses them.
  void reserve(FilesPath path, long bytes) throws FileSystemException {
    if (bytes < 0) {
      throw new IllegalArgumentException("A reservation cannot be negative");
    }
    synchronized (lock) {
      if (bytes > stageQuotaBytes - reservedBytes) {
        throw new FileSystemException(path.toString(), null, "The local copy needs " + bytes
            + " more bytes of the stage quota of " + stageQuotaBytes + " bytes, of which " + reservedBytes + " are in use");
      }
      reservedBytes += bytes;
    }
  }

  void release(long bytes) {
    synchronized (lock) {
      reservedBytes -= bytes;
    }
  }

  // Request options for the file system's API calls, which closing it cancels.
  HashMap<String, Object> requestOptions() {
    HashMap<String, Object> options = new HashMap<String, Object>();
    options.put(ClientRuntime.CANCELLATION_OPTION, closing);
    return options;
  }

  DownloadOptions downloadOptions() {
    return DownloadOptions.builder().replaceExisting(false).cancellation(closing).build();
  }

  UploadOptions uploadOptions() {
    return UploadOptions.builder().mkdirParents(false).cancellation(closing).build();
  }

  // The file or folder at path, as the API describes it without a download URL.
  File stat(FilesPath path, Operation operation) throws IOException {
    HashMap<String, Object> parameters = new HashMap<String, Object>();
    parameters.put("action", "stat");
    try {
      return client.files().download(path.apiPath(), parameters, requestOptions());
    } catch (RuntimeException e) {
      throw nativeFailure(operation, path, null, e);
    }
  }

  // The file at path, as the API describes it with its download URL.
  File describeForDownload(FilesPath path, Operation operation) throws IOException {
    try {
      return client.files().download(path.apiPath(), new HashMap<String, Object>(), requestOptions());
    } catch (RuntimeException e) {
      throw nativeFailure(operation, path, null, e);
    }
  }

  // Waits, within the file system's action timeout, for a copy, move or delete to finish.
  void awaitAction(FileAction action, Operation operation, FilesPath path, FilesPath other) throws IOException {
    try {
      action.awaitCompletion(actionTimeout.toMillis(), TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      InterruptedIOException interrupted = new InterruptedIOException("Interrupted while waiting for the API to finish with " + path);
      interrupted.initCause(e);
      throw interrupted;
    } catch (TimeoutException e) {
      FileSystemException unconfirmed = new FileSystemException(path.toString(), other == null ? null : other.toString(),
          "The API did not confirm that the operation finished within " + actionTimeout + "; it may still finish");
      unconfirmed.initCause(e);
      throw unconfirmed;
    } catch (RuntimeException e) {
      throw nativeFailure(operation, path, other, e);
    }
  }

  // The path as the API reports it, once the API confirms that it exists.
  Path realPath(FilesPath path) throws IOException {
    checkOpen();
    FilesPath absolute = (FilesPath) path.toAbsolutePath().normalize();
    if (absolute.isRoot()) {
      return absolute;
    }
    File file = stat(absolute, Operation.STAT);
    return file.getPath() == null ? absolute : FilesPath.parse(this, "/" + file.getPath());
  }

  /*
   * The native exception for a failure of the SDK, which is kept as its cause. Only the exact
   * structured API error types below, in the operations named, become a specific native exception;
   * every other failure, an API error of another type, a network, authentication or format failure,
   * or a failure of the local stage, is a plain FileSystemException, never a missing file. Once the
   * file system is closed, a failure of a request its closing cancelled is ClosedFileSystemException,
   * except a failed publication's, which keeps naming its kept copy.
   */
  IOException nativeFailure(Operation operation, FilesPath path, FilesPath other, Exception failure) {
    if (cancelled) {
      ClosedFileSystemException closed = new ClosedFileSystemException();
      closed.initCause(failure);
      throw closed;
    }
    return nativeFailure(operation, path, other, failure, null);
  }

  // As above, for a failed publication, which names the directory that keeps its copy, when there is
  // one, as the other file, whether or not the file system has been closed since.
  IOException nativeFailure(Operation operation, FilesPath path, FilesPath other, Exception failure, String recovery) {
    String file = path == null ? null : path.toString();
    String otherFile = other != null ? other.toString() : recovery;
    String type = failure instanceof ApiErrorException ? ((ApiErrorException) failure).getType() : null;
    // A kept copy's directory, as the other file, is only the name it was created with, as the reason
    // says.
    String reason = FilesByteChannel.namingCopyDirectory(reason(failure), other == null ? recovery : null);
    boolean copyOrMove = operation == Operation.COPY || operation == Operation.MOVE;
    FileSystemException mapped;
    if ("not-found/file-not-found".equals(type)) {
      mapped = new NoSuchFileException(file, otherFile, reason);
    } else if ("processing-failure/destination-exists".equals(type) && operation == Operation.CREATE_DIRECTORY) {
      mapped = new FileAlreadyExistsException(file, null, reason);
    } else if ("processing-failure/destination-exists".equals(type) && copyOrMove) {
      mapped = new FileAlreadyExistsException(otherFile, file, reason);
    } else if ("processing-failure/folder-not-empty".equals(type) && operation == Operation.DELETE) {
      mapped = new DirectoryNotEmptyException(file);
    } else if ("processing-failure/folder-not-empty".equals(type) && copyOrMove) {
      mapped = new DirectoryNotEmptyException(otherFile);
    } else if ("bad-request/cannot-download-directory".equals(type)
        && (operation == Operation.READ || operation == Operation.WRITE)) {
      mapped = new FileSystemException(file, null, "Is a directory");
    } else if ("bad-request/folder-must-not-be-a-file".equals(type) && operation == Operation.LIST) {
      mapped = new NotDirectoryException(file);
    } else if ("bad-request/folder-must-not-be-a-file".equals(type) && operation == Operation.CREATE_DIRECTORY) {
      mapped = new FileAlreadyExistsException(file, null, reason);
    } else if (type != null && ACCESS_DENIED.contains(type)) {
      mapped = new AccessDeniedException(file, otherFile, reason);
    } else {
      mapped = new FileSystemException(file, otherFile, reason);
    }
    mapped.initCause(failure);
    return mapped;
  }

  private static String reason(Exception failure) {
    if (failure instanceof ApiErrorException) {
      ApiErrorException error = (ApiErrorException) failure;
      return error.getType() + (error.getTitle() == null ? "" : " (" + error.getTitle() + ")") + ": " + error.getMessage();
    }
    return failure.getClass().getSimpleName() + ": " + failure.getMessage();
  }

  // Opens a lazy listing of directory, which the caller has found to be a directory.
  DirectoryStream<Path> list(FilesPath directory, DirectoryStream.Filter<? super Path> filter) throws IOException {
    Listing listing = new Listing(directory, filter);
    try {
      listing.pages = client.folders().listFor(directory.apiPath(), new HashMap<String, Object>(), listing.requestOptions());
    } catch (RuntimeException e) {
      throw nativeFailure(Operation.LIST, directory, null, e);
    }
    if (!register(listing)) {
      throw new ClosedFileSystemException();
    }
    return listing;
  }

  /*
   * A directory's entries, a page at a time: a page is requested only when iteration reaches it,
   * and only the current page, and the next entry found, are held. Closing the stream cancels a
   * page request in flight, and no page is requested afterwards.
   */
  private final class Listing implements DirectoryStream<Path> {
    private final FilesPath directory;
    private final DirectoryStream.Filter<? super Path> filter;
    private final CancellationToken stop = new CancellationToken();
    private ListIterator<File> pages;
    private volatile boolean closed;
    private boolean iterated;

    Listing(FilesPath directory, DirectoryStream.Filter<? super Path> filter) {
      this.directory = directory;
      this.filter = filter;
    }

    HashMap<String, Object> requestOptions() {
      HashMap<String, Object> options = new HashMap<String, Object>();
      options.put(ClientRuntime.CANCELLATION_OPTION, stop);
      return options;
    }

    @Override
    public synchronized Iterator<Path> iterator() {
      if (closed) {
        throw new IllegalStateException("The directory stream is closed");
      }
      if (iterated) {
        throw new IllegalStateException("The directory stream's iterator has already been returned");
      }
      iterated = true;
      return new Entries();
    }

    @Override
    public void close() {
      if (closed) {
        return;
      }
      closed = true;
      stop.cancel();
      unregister(this);
    }

    private final class Entries implements Iterator<Path> {
      private Iterator<File> page;
      private boolean started;
      private Path next;

      @Override
      public boolean hasNext() {
        synchronized (Listing.this) {
          if (next == null && !closed) {
            next = advance();
          }
          return next != null;
        }
      }

      @Override
      public Path next() {
        synchronized (Listing.this) {
          if (!hasNext()) {
            throw new NoSuchElementException();
          }
          Path entry = next;
          next = null;
          return entry;
        }
      }

      private Path advance() {
        while (true) {
          if (page != null && page.hasNext()) {
            Path entry = entry(page.next());
            if (entry == null) {
              continue;
            }
            try {
              if (filter == null || filter.accept(entry)) {
                return entry;
              }
            } catch (IOException e) {
              throw new DirectoryIteratorException(e);
            }
            continue;
          }
          if (!loadPage()) {
            return null;
          }
        }
      }

      private boolean loadPage() {
        try {
          if (!started) {
            started = true;
            // A listing whose first page was loaded when it was made is read from that page.
            if (pages.data.isEmpty()) {
              try {
                pages.loadNextPage();
              } catch (NoSuchElementException e) {
                return false;
              }
            }
          } else if (pages.hasNextPage()) {
            pages.loadNextPage();
          } else {
            return false;
          }
          page = pages.iterator();
          return true;
        } catch (RuntimeException e) {
          if (closed) {
            return false;
          }
          throw new DirectoryIteratorException(nativeFailure(Operation.LIST, directory, null, e));
        }
      }

      private Path entry(File item) {
        String path = item.getPath();
        while (path != null && path.endsWith("/")) {
          path = path.substring(0, path.length() - 1);
        }
        if (path == null || path.isEmpty()) {
          throw new DirectoryIteratorException(new FileSystemException(directory.toString(), null,
              "The API listed an entry without a path"));
        }
        String name = path.substring(path.lastIndexOf('/') + 1);
        return name.equals(".") || name.equals("..") ? null : directory.resolve(name);
      }
    }
  }

  static String globToRegex(String glob) {
    StringBuilder regex = new StringBuilder("^");
    boolean inGroup = false;
    for (int i = 0; i < glob.length(); i++) {
      char c = glob.charAt(i);
      switch (c) {
        case '\\':
          if (++i >= glob.length()) {
            throw new PatternSyntaxException("No character to escape", glob, i - 1);
          }
          appendLiteral(regex, glob.charAt(i));
          break;
        case '*':
          if (i + 1 < glob.length() && glob.charAt(i + 1) == '*') {
            regex.append(".*");
            i++;
          } else {
            regex.append("[^/]*");
          }
          break;
        case '?':
          regex.append("[^/]");
          break;
        case '[':
          i = appendClass(glob, i, regex);
          break;
        case '{':
          if (inGroup) {
            throw new PatternSyntaxException("Cannot nest groups", glob, i);
          }
          regex.append("(?:(?:");
          inGroup = true;
          break;
        case '}':
          if (inGroup) {
            regex.append("))");
            inGroup = false;
          } else {
            appendLiteral(regex, c);
          }
          break;
        case ',':
          if (inGroup) {
            regex.append(")|(?:");
          } else {
            appendLiteral(regex, c);
          }
          break;
        default:
          appendLiteral(regex, c);
      }
    }
    if (inGroup) {
      throw new PatternSyntaxException("Missing '}'", glob, glob.length() - 1);
    }
    return regex.append('$').toString();
  }

  private static void appendLiteral(StringBuilder regex, char c) {
    if ("\\^$.|?*+()[]{}".indexOf(c) >= 0) {
      regex.append('\\');
    }
    regex.append(c);
  }

  // Appends the class that starts at glob[start], '[', and returns the index of its ']'. It never
  // matches the separator.
  private static int appendClass(String glob, int start, StringBuilder regex) {
    int i = start + 1;
    regex.append("[[^/]&&[");
    if (i < glob.length() && glob.charAt(i) == '^') {
      regex.append("\\^");
      i++;
    } else {
      if (i < glob.length() && glob.charAt(i) == '!') {
        regex.append('^');
        i++;
      }
      if (i < glob.length() && glob.charAt(i) == '-') {
        regex.append('-');
        i++;
      }
    }
    boolean rangeStart = false;
    char last = 0;
    while (i < glob.length()) {
      char c = glob.charAt(i++);
      if (c == ']') {
        regex.append("]]");
        return i - 1;
      }
      if (c == '/') {
        throw new PatternSyntaxException("A class cannot hold the separator", glob, i - 1);
      }
      if (c == '\\' || c == '[' || (c == '&' && i < glob.length() && glob.charAt(i) == '&')) {
        regex.append('\\');
      }
      regex.append(c);
      if (c == '-') {
        if (!rangeStart) {
          throw new PatternSyntaxException("Invalid range", glob, i - 1);
        }
        if (i >= glob.length()) {
          break;
        }
        c = glob.charAt(i++);
        if (c < last) {
          throw new PatternSyntaxException("Invalid range", glob, i - 3);
        }
        if (c == '\\' || c == '[') {
          regex.append('\\');
        }
        regex.append(c);
        rangeStart = false;
      } else {
        rangeStart = true;
        last = c;
      }
    }
    throw new PatternSyntaxException("Missing ']'", glob, glob.length() - 1);
  }
}
