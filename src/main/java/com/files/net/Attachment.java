package com.files.net;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * File content for an API parameter of type file, such as a user's {@code avatar_file} or a site's
 * {@code logo_file}.
 *
 * <pre>{@code
 * HashMap<String, Object> parameters = new HashMap<>();
 * parameters.put("avatar_file", Attachment.fromPath(Paths.get("avatar.png")).withContentType("image/png"));
 * User.update(userId, parameters);
 * }</pre>
 *
 * <p>These parameters also accept a {@code byte[]}, which is sent like {@link #fromBytes(byte[])}.
 *
 * <p>A request that retries sends the same content again: the SDK reads a file again and checks
 * that it has not changed, and it copies a stream before sending it. An attachment is immutable;
 * the {@code with} methods return a copy.
 */
public final class Attachment {
  /** The most bytes a stream attachment may have, unless {@link #withSpoolLimit(long)} changes it: 64 MiB. */
  public static final long DEFAULT_SPOOL_LIMIT = 64L * 1024 * 1024;

  /** How much of a stream's copy stays in memory, unless {@link #withMemoryThreshold(long)} changes it: 1 MiB. */
  public static final long DEFAULT_MEMORY_THRESHOLD = 1024L * 1024;

  private static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";

  // A media type such as image/png or text/css; charset=utf-8 (RFC 9110 section 8.3.1).
  private static final String TOKEN = "[!#$%&'*+.^_`|~0-9A-Za-z-]+";
  private static final String QUOTED = "\"(?:[^\"\\\\\\x00-\\x1f\\x7f]|\\\\[^\\x00-\\x1f\\x7f])*\"";
  private static final Pattern MEDIA_TYPE =
      Pattern.compile(TOKEN + "/" + TOKEN + "(?:[ \\t]*;[ \\t]*" + TOKEN + "=(?:" + TOKEN + "|" + QUOTED + "))*");

  private final Source source;
  private final String filename;
  private final String contentType;

  private Attachment(Source source, String filename, String contentType) {
    this.source = source;
    this.filename = filename;
    this.contentType = contentType;
  }

  /**
   * Content from a byte array. The attachment keeps a copy of the array as it is now. Its filename
   * defaults to the parameter's name, such as {@code avatar_file}.
   */
  public static Attachment fromBytes(byte[] content) {
    Objects.requireNonNull(content, "content");
    return new Attachment(new BytesSource(Arrays.copyOf(content, content.length)), null, DEFAULT_CONTENT_TYPE);
  }

  /**
   * Content from a local file, read when the request is sent. Before the first attempt, the SDK
   * reads the file once to record its length and SHA-256 digest; each attempt, including a retry
   * after one that was cut off, reads it again and fails the request with an
   * {@link java.io.UncheckedIOException}, before the request is complete, if the content differs.
   * The filename defaults to the file's name, without its directories.
   */
  public static Attachment fromPath(Path path) {
    Objects.requireNonNull(path, "path");
    Path name = path.getFileName();
    if (name == null) {
      throw new IllegalArgumentException("An attachment path must name a file: " + path);
    }
    return new Attachment(new PathSource(path), checkedFilename(name.toString()), DEFAULT_CONTENT_TYPE);
  }

  /**
   * Content from a stream, from its current position to its end. The SDK reads the stream before
   * sending the request, into a copy it deletes when the request ends, and does not close the
   * stream. The copy keeps up to {@link #DEFAULT_MEMORY_THRESHOLD} bytes in memory and the rest in a
   * temporary file; a stream longer than {@link #DEFAULT_SPOOL_LIMIT} bytes fails the request before
   * anything is sent. A second request with the same attachment sends what remains of the stream.
   * The filename defaults to the parameter's name.
   */
  public static Attachment fromInputStream(InputStream stream) {
    Objects.requireNonNull(stream, "stream");
    return new Attachment(new StreamSource(stream, DEFAULT_SPOOL_LIMIT, DEFAULT_MEMORY_THRESHOLD), null,
        DEFAULT_CONTENT_TYPE);
  }

  /**
   * Returns a copy with this filename. Unicode is fine; control characters, such as a line break,
   * are not allowed.
   */
  public Attachment withFilename(String filename) {
    return new Attachment(source, checkedFilename(filename), contentType);
  }

  /** Returns a copy with this media type, such as {@code image/png}. The default is {@code application/octet-stream}. */
  public Attachment withContentType(String contentType) {
    Objects.requireNonNull(contentType, "contentType");
    if (!MEDIA_TYPE.matcher(contentType).matches()) {
      throw new IllegalArgumentException("Not a media type: " + contentType);
    }
    return new Attachment(source, filename, contentType);
  }

  /** Returns a copy of this stream attachment that accepts a stream of up to {@code maxBytes} bytes. */
  public Attachment withSpoolLimit(long maxBytes) {
    StreamSource stream = streamSource("withSpoolLimit");
    return new Attachment(new StreamSource(stream.stream, checkedPositive(maxBytes, "spool limit"),
        stream.memoryThreshold), filename, contentType);
  }

  /**
   * Returns a copy of this stream attachment whose copy keeps up to {@code bytes} bytes in memory
   * before it moves to a temporary file. A threshold at or above the spool limit keeps the whole copy
   * in memory.
   */
  public Attachment withMemoryThreshold(long bytes) {
    StreamSource stream = streamSource("withMemoryThreshold");
    return new Attachment(new StreamSource(stream.stream, stream.spoolLimit, checkedPositive(bytes,
        "memory threshold")), filename, contentType);
  }

  /** The filename sent with the content, or null when the parameter's name is used. */
  public String getFilename() {
    return filename;
  }

  /** The media type sent with the content. */
  public String getContentType() {
    return contentType;
  }

  /** Describes the attachment without its content or the file's directories. */
  @Override
  public String toString() {
    return "Attachment[" + source.describe() + (filename == null ? "" : ", filename=" + filename)
        + ", contentType=" + contentType + "]";
  }

  // Reads what a request sends for this parameter, before the request sends anything. checkpoint
  // runs between reads, and throws when the request is cancelled.
  AttachmentContent prepare(String parameter, Runnable checkpoint) throws AttachmentSourceException {
    return source.prepare(parameter, checkpoint);
  }

  private StreamSource streamSource(String method) {
    if (!(source instanceof StreamSource)) {
      throw new IllegalStateException(method + " applies to an attachment from an InputStream; only a stream is copied");
    }
    return (StreamSource) source;
  }

  private static String checkedFilename(String filename) {
    Objects.requireNonNull(filename, "filename");
    if (filename.isEmpty()) {
      throw new IllegalArgumentException("An attachment filename cannot be empty");
    }
    for (int i = 0; i < filename.length(); i++) {
      char c = filename.charAt(i);
      if (Character.isISOControl(c)) {
        throw new IllegalArgumentException("An attachment filename cannot contain control characters");
      }
      boolean pairedSurrogate = Character.isHighSurrogate(c) && i + 1 < filename.length()
          && Character.isLowSurrogate(filename.charAt(i + 1));
      if (pairedSurrogate) {
        i++;
      } else if (Character.isSurrogate(c)) {
        throw new IllegalArgumentException("An attachment filename must be valid Unicode text");
      }
    }
    return filename;
  }

  private static long checkedPositive(long bytes, String name) {
    if (bytes <= 0) {
      throw new IllegalArgumentException("The " + name + " must be a positive number of bytes, not " + bytes);
    }
    return bytes;
  }

  private interface Source {
    AttachmentContent prepare(String parameter, Runnable checkpoint) throws AttachmentSourceException;

    String describe();
  }

  private static final class BytesSource implements Source {
    private final byte[] bytes;

    BytesSource(byte[] bytes) {
      this.bytes = bytes;
    }

    @Override
    public AttachmentContent prepare(String parameter, Runnable checkpoint) {
      return AttachmentContent.ofBytes(bytes);
    }

    @Override
    public String describe() {
      return bytes.length + " bytes";
    }
  }

  private static final class PathSource implements Source {
    private final Path path;

    PathSource(Path path) {
      this.path = path;
    }

    @Override
    public AttachmentContent prepare(String parameter, Runnable checkpoint) throws AttachmentSourceException {
      return AttachmentContent.ofFile(parameter, path, checkpoint);
    }

    @Override
    public String describe() {
      return "file";
    }
  }

  private static final class StreamSource implements Source {
    private final InputStream stream;
    private final long spoolLimit;
    private final long memoryThreshold;

    StreamSource(InputStream stream, long spoolLimit, long memoryThreshold) {
      this.stream = stream;
      this.spoolLimit = spoolLimit;
      this.memoryThreshold = memoryThreshold;
    }

    @Override
    public AttachmentContent prepare(String parameter, Runnable checkpoint) throws AttachmentSourceException {
      return AttachmentContent.spooled(parameter, stream, spoolLimit, memoryThreshold, checkpoint);
    }

    @Override
    public String describe() {
      return "stream, spool limit " + spoolLimit + " bytes";
    }
  }
}
