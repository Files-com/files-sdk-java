package com.files.nio;

import com.files.FilesClient;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.time.Duration;

/**
 * Makes {@code java.nio.file} file systems of a Files.com site, so code written for
 * {@code java.nio.file.Files} reads, writes, lists, creates folders in, inspects, copies, moves
 * and deletes remote files once its first path comes from such a file system:
 *
 * <pre>{@code
 * try (FileSystem files = FilesComFileSystems.builder(client)
 *     .stageDirectory(Paths.get("/var/lib/app/files-stage"))
 *     .stageQuotaBytes(4L * 1024 * 1024 * 1024)
 *     .build()) {
 *   Path report = files.getPath("/reports/2024.csv");
 *   Files.write(report, bytes);
 *   try (DirectoryStream<Path> entries = Files.newDirectoryStream(report.getParent())) {
 *     for (Path entry : entries) {
 *       System.out.println(entry + " " + Files.size(entry));
 *     }
 *   }
 * }
 * }</pre>
 *
 * <p><b>Binding.</b> A file system borrows one {@link FilesClient}, and uses it, with its own
 * credentials, for every request; closing the file system never closes the client. It is found
 * only through the object returned here: there is no service provider, no registry and no URI
 * that finds or authenticates one. Its paths' URIs carry an opaque mount ID, never credentials.
 *
 * <p><b>Reading.</b> Opening a file for reading downloads it, with
 * {@code Transfers.download} to a stream, into a copy in the stage directory, which the channel
 * reads and seeks with fixed buffers. The copy's size, as the API describes it, is reserved in the
 * stage quota before it is downloaded, and the download is refused before it writes past it; a file
 * whose size the API does not give, or that does not fit, is refused first. The copy is deleted when
 * the channel closes.
 *
 * <p><b>Writing.</b> Writes are staged and published when the channel closes:
 * <ul>
 *   <li>{@code CREATE} with {@code TRUNCATE_EXISTING}, the default of {@code Files.write} and
 *       {@code Files.newOutputStream}, and {@code TRUNCATE_EXISTING} without {@code CREATE},
 *       prepare the empty local copy, then upload the file empty before the channel is returned, so
 *       the file exists, empty, once it is open; a stage directory that cannot hold the copy fails
 *       the open before the remote file is changed. Nothing is downloaded. This is not an atomic
 *       replacement.</li>
 *   <li>{@code WRITE}, {@code READ} with {@code WRITE}, and {@code APPEND}, on an existing file
 *       and without {@code CREATE} or {@code TRUNCATE_EXISTING}, download the file and change
 *       its copy: written bytes replace those at the position, or, with {@code APPEND}, are
 *       added at the end.</li>
 *   <li>Closing the channel uploads the changed copy once, with {@code Transfers.upload}, and
 *       returns once the upload has completed. Flushing publishes nothing. Until then, the
 *       file's remote content is what it was when the channel opened (empty, after a create or
 *       truncation), and other clients see that. Changes others make meanwhile are replaced, not
 *       merged: nothing is locked remotely.</li>
 *   <li>One channel of a file system writes a file at a time: opening a second one for writing
 *       throws {@code FileSystemException}, as do opening it for reading, deleting, copying,
 *       moving and setting its time while the first is open. Its attributes are then a pending
 *       view: as described when the writing channel opened, with the size of its copy. Paths that
 *       Files.com compares as one file, such as {@code /Report.csv} and {@code /report.csv}, are
 *       one file here.</li>
 *   <li>A failed close is described by {@link FilesByteChannel}: its copy is kept for recovery,
 *       and is not uploaded again. The exception's {@code getOtherFile()} is the name the copy's
 *       directory was created with, or null: a diagnostic name, not checked again, which may no
 *       longer lead to the copy once that directory is renamed or replaced. A copy whose change
 *       failed locally is not uploaded at all.</li>
 * </ul>
 *
 * <p><b>Folders and other operations.</b> {@code Files.createDirectory} creates one folder, with
 * {@code mkdir_parents} false, and {@code Files.createDirectories} creates each missing one.
 * {@code Files.delete} deletes a file or an empty folder, with {@code File.beginDelete} and
 * {@code recursive} false, waits for it to finish and checks that it is gone; a folder that is
 * not empty is not deleted. {@code Files.copy} and {@code Files.move} within one file system use
 * the API's copy and move, with {@code overwrite} as {@code REPLACE_EXISTING} asks, wait for them
 * to finish and check that the target is there; one the API skips is not taken as done, and throws
 * {@code FileSystemException}. Copying or moving a file to a path Files.com compares as the same
 * file does nothing. Copying a folder creates an empty folder and does not copy its entries. Each
 * wait lasts at most the {@link Builder#actionTimeout action timeout}.
 * A directory stream requests one page at a time, as iteration reaches it, and closing it stops
 * further requests. {@code Files.setLastModifiedTime} sets {@code provided_mtime}. Attributes are
 * described by {@link FilesFileSystemProvider.Attributes}.
 *
 * <p><b>Not supported.</b> {@code CREATE} without {@code TRUNCATE_EXISTING}, which includes
 * {@code CREATE} with {@code APPEND}, and {@code CREATE_NEW}, need an atomic create the API does
 * not offer; {@code ATOMIC_MOVE} an atomic move; {@code SYNC} and {@code DSYNC} synchronous
 * publication. Links, watching, file stores, user principals, POSIX permissions and ACLs,
 * {@code FileChannel} (mapping and locks), {@code DELETE_ON_CLOSE}, {@code COPY_ATTRIBUTES}, initial
 * attributes, access and creation times, replacing a file with a folder or a folder with a file,
 * and copying or moving between two file systems are not supported either. Each throws
 * {@code UnsupportedOperationException}, whose cause is a
 * {@link com.files.exceptions.NotImplementedException}, before anything is changed.
 *
 * <p><b>Errors.</b> The SDK's own APIs throw {@link com.files.exceptions.SdkException}s; these
 * native operations throw the native exceptions {@code java.nio.file} callers expect, with the
 * SDK's exception as the cause, as {@link FilesFileSystemProvider} describes. A failure is a
 * missing file, {@code NoSuchFileException}, only when the API says so with the structured type
 * {@code not-found/file-not-found}, or, for the request that starts a delete, with the structured
 * type {@code not-found} and the code 404 in the error's body: a status alone, an authentication or
 * network failure is never taken for one. That delete's {@code not-found} follows the API's names:
 * the API cannot resolve the path, whether nothing is there or a stored path it cannot tell apart is,
 * so {@code Files.deleteIfExists} returning {@code false} reflects that unresolved logical path. It
 * establishes neither physical absence nor the outcome of earlier request attempts. A not-found
 * while a delete is waited for is a failure, not a missing file.
 *
 * <p><b>Stage.</b> The stage directory must exist. Each copy is kept in a directory of its own
 * that the SDK's download stage creates in it, private to the current user as the stage's
 * platform facility makes it, and is read, written, downloaded and uploaded only through the handle
 * that created it, never by name. It is deleted when its channel closes, as far as that cleanup
 * can, unless kept for recovery. The stage quota bounds the copies of open channels; copies kept for
 * recovery are the caller's, and are not counted.
 */
public final class FilesComFileSystems {
  private FilesComFileSystems() {
  }

  /**
   * Starts a file system bound to {@code client}, which it borrows.
   *
   * @param client the client whose credentials and settings every request uses
   * @return a builder
   * @throws NullPointerException if {@code client} is {@code null}
   */
  public static Builder builder(FilesClient client) {
    if (client == null) {
      throw new NullPointerException("client");
    }
    return new Builder(client);
  }

  /** The settings of a Files.com file system. */
  public static final class Builder {
    private final FilesClient client;
    private String workingDirectory = "/";
    private Path stageDirectory;
    private long stageQuotaBytes;
    private Duration actionTimeout = Duration.ofMinutes(10);

    private Builder(FilesClient client) {
      this.client = client;
    }

    /**
     * The remote directory relative paths are resolved against. Default: {@code /}, the root.
     *
     * @param workingDirectory an absolute remote path, starting with {@code /}
     * @return this builder
     */
    public Builder workingDirectory(String workingDirectory) {
      if (workingDirectory == null || !workingDirectory.startsWith("/")) {
        throw new IllegalArgumentException("The working directory must be an absolute remote path, starting with /");
      }
      this.workingDirectory = workingDirectory;
      return this;
    }

    /**
     * The existing local directory the copies of open files are kept in, each in a private
     * directory of its own. Required. It is not the system's temporary directory unless chosen.
     *
     * @param stageDirectory an absolute local path
     * @return this builder
     */
    public Builder stageDirectory(Path stageDirectory) {
      if (stageDirectory == null || !stageDirectory.isAbsolute()) {
        throw new IllegalArgumentException("The stage directory must be an absolute local path");
      }
      this.stageDirectory = stageDirectory;
      return this;
    }

    /**
     * The most bytes the copies of open files may hold at once. Required.
     *
     * @param stageQuotaBytes a positive number of bytes
     * @return this builder
     */
    public Builder stageQuotaBytes(long stageQuotaBytes) {
      if (stageQuotaBytes <= 0) {
        throw new IllegalArgumentException("The stage quota must be positive");
      }
      this.stageQuotaBytes = stageQuotaBytes;
      return this;
    }

    /**
     * The longest a copy, move or delete is waited for. Default: ten minutes. When it elapses the
     * operation throws {@code FileSystemException}; the API may still finish it.
     *
     * @param actionTimeout at least one millisecond
     * @return this builder
     */
    public Builder actionTimeout(Duration actionTimeout) {
      if (actionTimeout == null || actionTimeout.toMillis() < 1) {
        throw new IllegalArgumentException("The action timeout must be at least one millisecond");
      }
      this.actionTimeout = actionTimeout;
      return this;
    }

    /**
     * Makes the file system.
     *
     * @return a new open file system, a {@link FilesFileSystem}
     * @throws IllegalStateException if the stage directory or quota was not set
     */
    public FileSystem build() {
      if (stageDirectory == null || stageQuotaBytes <= 0) {
        throw new IllegalStateException("A Files.com file system needs a stage directory and a stage quota");
      }
      return new FilesFileSystem(FilesFileSystemProvider.INSTANCE, client, workingDirectory, stageDirectory, stageQuotaBytes,
          actionTimeout);
    }
  }
}
