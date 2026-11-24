package com.files.nio;

import com.files.exceptions.NotImplementedException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.ProviderMismatchException;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * A path of a Files.com file system made by {@link FilesComFileSystems}: a remote path, bound to
 * that file system and so to the client it borrows.
 *
 * <p>Its lexical rules are the same on every host: the separator is {@code /}, an absolute path
 * starts with it, and repeated or trailing separators are dropped. Names are kept exactly as given,
 * with their case and Unicode form; the only character refused is NUL. Two paths are equal only if
 * they belong to the same file system and have the same names, compared exactly: this does not
 * claim the API compares paths the same way, and {@link #toRealPath} gives the path as the API
 * reports it. The file system's operations do follow Files.com's comparison where it decides which
 * remote file a path names: two spellings of one file have one writer, and copying or moving a file
 * to another spelling of itself changes nothing. A relative path is resolved against the file
 * system's remote working directory.
 * {@link #toUri()} names the file system by its opaque mount ID and never holds credentials.
 *
 * <p>A path is immutable and safe for use by several threads. It has no {@code java.io.File}:
 * {@link #toFile()} throws {@code UnsupportedOperationException}.
 */
public final class FilesPath implements Path {
  private static final String[] NO_NAMES = new String[0];

  private final FilesFileSystem fileSystem;
  private final boolean absolute;
  // The empty path has one empty name, as on Unix; the root has none.
  private final String[] names;

  FilesPath(FilesFileSystem fileSystem, boolean absolute, String[] names) {
    this.fileSystem = fileSystem;
    this.absolute = absolute;
    this.names = names;
  }

  static FilesPath parse(FilesFileSystem fileSystem, String first, String... more) {
    StringBuilder joined = new StringBuilder(first);
    for (String part : more) {
      if (!part.isEmpty()) {
        if (joined.length() > 0) {
          joined.append('/');
        }
        joined.append(part);
      }
    }
    String text = joined.toString();
    if (text.indexOf('\u0000') >= 0) {
      throw new InvalidPathException(text, "A Files.com path cannot contain the NUL character");
    }
    List<String> names = new ArrayList<String>();
    for (String name : text.split("/")) {
      if (!name.isEmpty()) {
        names.add(name);
      }
    }
    boolean absolute = text.startsWith("/");
    if (!absolute && names.isEmpty()) {
      return new FilesPath(fileSystem, false, new String[]{""});
    }
    return new FilesPath(fileSystem, absolute, names.toArray(NO_NAMES));
  }

  // The path as this file system's operations need it: a FilesPath of the same file system.
  static FilesPath of(Path path, FilesFileSystem fileSystem) {
    if (path == null) {
      throw new NullPointerException("path");
    }
    if (!(path instanceof FilesPath) || ((FilesPath) path).fileSystem != fileSystem) {
      throw new ProviderMismatchException("The path " + path + " is not of this Files.com file system");
    }
    return (FilesPath) path;
  }

  // The path the API names: absolute, normalized and without the leading separator; the root is "".
  String apiPath() {
    return join(((FilesPath) toAbsolutePath().normalize()).names);
  }

  boolean isRoot() {
    return ((FilesPath) toAbsolutePath().normalize()).names.length == 0;
  }

  private boolean isEmpty() {
    return !absolute && names.length == 1 && names[0].isEmpty();
  }

  @Override
  public FilesFileSystem getFileSystem() {
    return fileSystem;
  }

  @Override
  public boolean isAbsolute() {
    return absolute;
  }

  @Override
  public Path getRoot() {
    return absolute ? new FilesPath(fileSystem, true, NO_NAMES) : null;
  }

  @Override
  public Path getFileName() {
    return names.length == 0 ? null : new FilesPath(fileSystem, false, new String[]{names[names.length - 1]});
  }

  @Override
  public Path getParent() {
    if (names.length == 0 || (names.length == 1 && !absolute)) {
      return null;
    }
    return new FilesPath(fileSystem, absolute, Arrays.copyOf(names, names.length - 1));
  }

  @Override
  public int getNameCount() {
    return names.length;
  }

  @Override
  public Path getName(int index) {
    if (index < 0 || index >= names.length) {
      throw new IllegalArgumentException("No name at index " + index + " of " + this);
    }
    return new FilesPath(fileSystem, false, new String[]{names[index]});
  }

  @Override
  public Path subpath(int beginIndex, int endIndex) {
    if (beginIndex < 0 || beginIndex >= names.length || endIndex <= beginIndex || endIndex > names.length) {
      throw new IllegalArgumentException("No names from " + beginIndex + " to " + endIndex + " of " + this);
    }
    return new FilesPath(fileSystem, false, Arrays.copyOfRange(names, beginIndex, endIndex));
  }

  @Override
  public boolean startsWith(Path other) {
    if (!(other instanceof FilesPath) || ((FilesPath) other).fileSystem != fileSystem) {
      return false;
    }
    FilesPath prefix = (FilesPath) other;
    if (prefix.absolute != absolute || prefix.names.length > names.length) {
      return false;
    }
    for (int i = 0; i < prefix.names.length; i++) {
      if (!prefix.names[i].equals(names[i])) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean startsWith(String other) {
    return startsWith(fileSystem.getPath(other));
  }

  @Override
  public boolean endsWith(Path other) {
    if (!(other instanceof FilesPath) || ((FilesPath) other).fileSystem != fileSystem) {
      return false;
    }
    FilesPath suffix = (FilesPath) other;
    if (suffix.absolute) {
      return equals(suffix);
    }
    if (suffix.names.length > names.length) {
      return false;
    }
    int offset = names.length - suffix.names.length;
    for (int i = 0; i < suffix.names.length; i++) {
      if (!suffix.names[i].equals(names[offset + i])) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean endsWith(String other) {
    return endsWith(fileSystem.getPath(other));
  }

  @Override
  public Path normalize() {
    List<String> kept = new ArrayList<String>();
    for (String name : names) {
      if (name.equals(".") || name.isEmpty()) {
        continue;
      }
      if (name.equals("..")) {
        if (!kept.isEmpty() && !kept.get(kept.size() - 1).equals("..")) {
          kept.remove(kept.size() - 1);
          continue;
        }
        if (absolute) {
          // Above the root is the root.
          continue;
        }
      }
      kept.add(name);
    }
    if (!absolute && kept.isEmpty()) {
      return new FilesPath(fileSystem, false, new String[]{""});
    }
    return new FilesPath(fileSystem, absolute, kept.toArray(NO_NAMES));
  }

  @Override
  public Path resolve(Path other) {
    FilesPath child = of(other, fileSystem);
    if (child.absolute || isEmpty()) {
      return child;
    }
    if (child.isEmpty()) {
      return this;
    }
    String[] joined = Arrays.copyOf(names, names.length + child.names.length);
    System.arraycopy(child.names, 0, joined, names.length, child.names.length);
    return new FilesPath(fileSystem, absolute, joined);
  }

  @Override
  public Path resolve(String other) {
    return resolve(fileSystem.getPath(other));
  }

  @Override
  public Path resolveSibling(Path other) {
    of(other, fileSystem);
    Path parent = getParent();
    return parent == null ? other : parent.resolve(other);
  }

  @Override
  public Path resolveSibling(String other) {
    return resolveSibling(fileSystem.getPath(other));
  }

  @Override
  public Path relativize(Path other) {
    FilesPath target = of(other, fileSystem);
    if (target.absolute != absolute) {
      throw new IllegalArgumentException("Only two absolute or two relative paths can be relativized: " + this + ", " + other);
    }
    if (equals(target)) {
      return new FilesPath(fileSystem, false, new String[]{""});
    }
    if (isEmpty()) {
      return target;
    }
    String[] targetNames = target.isEmpty() ? NO_NAMES : target.names;
    int common = 0;
    while (common < names.length && common < targetNames.length && names[common].equals(targetNames[common])) {
      common++;
    }
    List<String> relative = new ArrayList<String>();
    for (int i = common; i < names.length; i++) {
      relative.add("..");
    }
    relative.addAll(Arrays.asList(targetNames).subList(common, targetNames.length));
    if (relative.isEmpty()) {
      return new FilesPath(fileSystem, false, new String[]{""});
    }
    return new FilesPath(fileSystem, false, relative.toArray(NO_NAMES));
  }

  /**
   * Returns a URI of this path: the {@code files-com} scheme, the file system's opaque mount ID as
   * its authority, and the absolute path. It holds no credentials, and is not resolved back to a
   * path: {@code Paths.get(URI)} finds no Files.com file system, which only
   * {@link FilesComFileSystems} makes.
   */
  @Override
  public URI toUri() {
    try {
      return new URI(fileSystem.provider().getScheme(), fileSystem.mountId(), toAbsolutePath().toString(), null, null);
    } catch (URISyntaxException e) {
      throw new IllegalStateException("The path " + this + " has no URI", e);
    }
  }

  @Override
  public Path toAbsolutePath() {
    return absolute ? this : fileSystem.workingDirectory().resolve(this);
  }

  /**
   * Returns the absolute, normalized path as the API reports it, once the API has confirmed that
   * it exists. There are no links, so {@code options} change nothing.
   *
   * @throws java.nio.file.NoSuchFileException if the API reports the file not found
   */
  @Override
  public Path toRealPath(LinkOption... options) throws IOException {
    return fileSystem.realPath(this);
  }

  /**
   * Throws {@code UnsupportedOperationException}, whose cause is a {@link NotImplementedException}:
   * a remote path has no local {@code java.io.File}.
   */
  @Override
  public java.io.File toFile() {
    String message = "A Files.com path has no java.io.File; use java.nio.file.Files with the path itself";
    throw new UnsupportedOperationException(message, new NotImplementedException(message));
  }

  /**
   * Throws {@code UnsupportedOperationException}, whose cause is a {@link NotImplementedException}:
   * a Files.com path cannot be watched.
   */
  @Override
  public WatchKey register(WatchService watcher, WatchEvent.Kind<?>[] events, WatchEvent.Modifier... modifiers) {
    if (watcher == null) {
      throw new NullPointerException("watcher");
    }
    String message = "A Files.com path cannot be watched";
    throw new UnsupportedOperationException(message, new NotImplementedException(message));
  }

  @Override
  public WatchKey register(WatchService watcher, WatchEvent.Kind<?>... events) {
    return register(watcher, events, new WatchEvent.Modifier[0]);
  }

  @Override
  public Iterator<Path> iterator() {
    List<Path> elements = new ArrayList<Path>();
    for (int i = 0; i < names.length; i++) {
      elements.add(getName(i));
    }
    return elements.iterator();
  }

  /**
   * Compares two paths: those of two file systems by their file systems' opaque mount IDs, so the
   * same path of two file systems, which are not equal, never compares as 0; then the two paths'
   * strings, as {@link String#compareTo} does.
   *
   * @throws ClassCastException if {@code other} is not a Files.com path
   */
  @Override
  public int compareTo(Path other) {
    FilesPath path = (FilesPath) other;
    if (path.fileSystem != fileSystem) {
      int byMount = fileSystem.mountId().compareTo(path.fileSystem.mountId());
      if (byMount != 0) {
        return byMount;
      }
    }
    return toString().compareTo(path.toString());
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof FilesPath)) {
      return false;
    }
    FilesPath path = (FilesPath) other;
    return path.fileSystem == fileSystem && path.absolute == absolute && Arrays.equals(path.names, names);
  }

  @Override
  public int hashCode() {
    return 31 * (31 * System.identityHashCode(fileSystem) + (absolute ? 1 : 0)) + Arrays.hashCode(names);
  }

  @Override
  public String toString() {
    return absolute ? "/" + join(names) : join(names);
  }

  private static String join(String[] names) {
    StringBuilder joined = new StringBuilder();
    for (String name : names) {
      if (joined.length() > 0) {
        joined.append('/');
      }
      joined.append(name);
    }
    return joined.toString();
  }
}
