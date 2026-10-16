package com.files.net;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.files.exceptions.TransferCheckpointException;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.CRC32;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// An upload's checkpoint: a record in a checkpoint directory (CheckpointStore), held and locked for
// the whole run, from which a later run with the same arguments continues the upload instead of
// starting it again.
//
// What it holds. A header binds the record to one transfer: the API root the call is set to start at,
// the workspace, the remote path as given, and the local file's real path, which also name the
// record. The API root is held only as the SHA-256 of the whole of it, with its scheme, host and
// port beside, as the user information, query or fragment a configured root may carry is never
// written. The header also binds the record to the file as it was, by its file key where the platform has one, its length and its
// modification time to the precision the file system keeps, and to the part sizing asked for. Then,
// as the upload goes: its reference (ref) and, where the SDK chose the part sizes, a digest of the S3
// upload its parts go to; each part planned, by number, offset and length; each part accepted, with
// its ETag; and whether the request completing the upload is about to be sent. It holds no
// credentials, signed URL, query or session: nothing in it is ever sent, used as a destination, or
// trusted over what the resuming call gives. A later run compares the header with its own call and
// file; the reference is the only thing it takes from the record, and Files.com must still recognize
// it, and give the same S3 upload, before any part is sent.
//
// How it is written. The record is a journal of entries, each a frame of its length, a CRC-32 of its
// body, and its body, JSON, appended in place and never renamed. A frame cut short by a crash is the
// last, and is ignored and cut off when the record is next opened; one damaged in its middle makes the
// record unreadable, and it is refused rather than guessed at. Its order makes every crash safe:
//
//   1. The header is written and made durable before anything is sent.
//   2. The reference is recorded and made durable once Files.com begins the upload, before any part
//      is sent.
//   3. A part is recorded as planned, and made durable, before it is first sent, so a part that may
//      have been sent is never planned again with other bytes; parts made durable together share one
//      flush.
//   4. A part accepted is recorded, but not flushed for itself: if its entry does not last, a later run
//      sends the part again, with the same bytes.
//   5. Before the request completing the upload is sent, the entry saying it is about to be is
//      appended and made durable with everything before it. If that cannot be done, the request is not
//      sent. A later run that finds the entry never sends the request: it reports that it does not know
//      whether the upload was completed.
//   6. Once the upload is completed, or refused, the record is deleted. If the request did not reach
//      Files.com, an entry undoing step 5 is appended, so a later run may complete the upload.
//
// The entry of step 5 needs only its own file to be durable, never its directory, so the order holds
// wherever a file can be flushed. A record that is lost whole, such as one whose name did not last a
// crash on a platform that cannot flush a directory, only means a later run starts a new upload; it
// never sends the completing request of the lost one.
final class UploadCheckpoint implements AutoCloseable {
  private static final Logger log = LoggerFactory.getLogger(UploadCheckpoint.class);
  static final String FORMAT = "files-sdk-java-upload-checkpoint";
  // Version 1 held the API root itself; its records are refused as of a format this one does not read.
  static final int VERSION = 2;
  private static final int FRAME_HEADER_BYTES = 8;
  private static final SecureRandom RANDOM = new SecureRandom();

  private final CheckpointStore store;
  private final CheckpointStore.Record record;
  private final Path directory;
  private final String name;
  private final Saved saved;
  // How many bytes the record holds, and how many of them have been made durable.
  private long written;
  private long durable;
  private boolean closed;

  private UploadCheckpoint(CheckpointStore store, CheckpointStore.Record record, Path directory, String name, Saved saved, long written) {
    this.store = store;
    this.record = record;
    this.directory = directory;
    this.name = name;
    this.saved = saved;
    this.written = written;
    this.durable = written;
  }

  // Opens the checkpoint of the upload binding names, in directory: what an earlier run recorded, or
  // a new record. A record of a file that changed since is discarded, and the upload starts over.
  static UploadCheckpoint open(Path directory, Binding binding) throws IOException {
    CheckpointStore store = CheckpointStore.open(directory);
    try {
      String name = binding.recordName();
      for (int attempt = 0; ; attempt++) {
        CheckpointStore.Record record = store.lock(name);
        try {
          UploadCheckpoint checkpoint = open(store, record, binding, name);
          if (checkpoint != null) {
            return checkpoint;
          }
        } catch (IOException | RuntimeException | Error e) {
          record.close();
          throw e;
        }
        // Another run removed or replaced the record between its opening and its locking.
        record.close();
        if (attempt >= 2) {
          throw CheckpointStore.inUse(store.directory, name);
        }
      }
    } catch (IOException | RuntimeException | Error e) {
      store.close();
      throw e;
    }
  }

  private static UploadCheckpoint open(CheckpointStore store, CheckpointStore.Record record, Binding binding, String name) throws IOException {
    Path directory = store.directory;
    long size = record.size();
    if (size > CheckpointStore.MAX_RECORD_BYTES) {
      throw unreadable(directory, name, "it holds " + size + " bytes, more than a record may hold");
    }
    List<JsonNode> entries = new ArrayList<JsonNode>();
    long end = size == 0 ? 0 : readEntries(record.read(), entries, directory, name);
    if (end < size) {
      // The last entry was cut short by a crash: it is dropped, so new entries follow complete ones.
      record.truncate(end);
      record.force();
    }
    if (!entries.isEmpty()) {
      JsonNode header = entries.get(0);
      if (!FORMAT.equals(header.path("format").asText()) || !"header".equals(header.path("type").asText())) {
        throw unreadable(directory, name, "it is not an upload checkpoint this SDK wrote");
      }
      if (header.path("version").asInt() != VERSION) {
        throw unreadable(directory, name, "it was written in format " + header.path("version").asText() + ", which this version of the SDK"
            + " does not read");
      }
      JsonNode bound = header.path("binding");
      if (!binding.sameTransfer(bound)) {
        throw new TransferCheckpointException(TransferCheckpointException.Reason.OTHER_TRANSFER, directory, "The checkpoint record " + name
            + " in " + directory + " is of another transfer");
      }
      if (!binding.sameSettings(bound)) {
        throw new TransferCheckpointException(TransferCheckpointException.Reason.OTHER_TRANSFER, directory, "The checkpoint record " + name
            + " in " + directory + " records the upload with other part sizing (" + bound.path("sizing").asText() + "). Upload with"
            + " the same options, or discard the checkpoint to start over");
      }
      if (binding.sameSource(bound)) {
        if (!record.isNamed()) {
          return null;
        }
        return new UploadCheckpoint(store, record, directory, name, Saved.replay(entries, binding, directory, name), end);
      }
      log.info("The file of a checkpointed upload changed since the checkpoint was recorded; the upload starts over");
      log.debug("The file {} uploaded to {} changed since its checkpoint {} was recorded", binding.localPath, binding.remotePath, name);
      record.truncate(0);
    }
    // A new record: its header, with a nonce no other record has, so that a record another run
    // created under the same name meanwhile is told apart from this one.
    ObjectNode header = ClientRuntime.MAPPER.createObjectNode();
    header.put("type", "header");
    header.put("format", FORMAT);
    header.put("version", VERSION);
    header.put("nonce", Long.toUnsignedString(RANDOM.nextLong(), 36) + Long.toUnsignedString(RANDOM.nextLong(), 36));
    header.set("binding", binding.toJson());
    byte[] frame = frame(header);
    record.append(frame);
    record.force();
    record.forceName();
    if (!record.isNamed()) {
      return null;
    }
    return new UploadCheckpoint(store, record, directory, name, null, frame.length);
  }

  // What an earlier run recorded, or null for a new record.
  Saved saved() {
    return saved;
  }

  Path directory() {
    return directory;
  }

  // The upload was begun: its reference and, where the SDK chose the part sizes, the S3 upload they
  // go to. Durable before any part is sent.
  synchronized void begun(String ref, boolean sizesChosen, String uploadIdentity) throws IOException {
    ObjectNode entry = entry("upload");
    entry.put("ref", ref);
    entry.put("sizes_chosen", sizesChosen);
    if (uploadIdentity != null) {
      entry.put("s3_upload", uploadIdentity);
    }
    append(entry);
    forceAll();
  }

  // Records a part as planned; returns the position awaitDurable must reach before it is sent.
  synchronized long planned(long number, long offset, long length) throws IOException {
    ObjectNode entry = entry("planned");
    entry.putArray("part").add(number).add(offset).add(length);
    append(entry);
    return written;
  }

  // Waits until everything up to position is durable, flushing once for every part waiting.
  synchronized void awaitDurable(long position) throws IOException {
    if (durable < position) {
      forceAll();
    }
  }

  // Records a part as accepted, with its ETag, if it had one. Not flushed for itself.
  synchronized void accepted(long number, String etag) throws IOException {
    ObjectNode entry = entry("accepted");
    ArrayNode part = entry.putArray("part").add(number);
    if (etag != null) {
      part.add(etag);
    }
    append(entry);
  }

  // Records that the request completing the upload is about to be sent, durably with every entry
  // before it, or throws, NOT_DURABLE, so that the request is not sent.
  synchronized void finalizing() {
    try {
      append(entry("finalizing"));
      forceAll();
    } catch (IOException e) {
      throw new TransferCheckpointException(TransferCheckpointException.Reason.NOT_DURABLE, directory, "The checkpoint of the upload could not"
          + " record durably that its completing request was about to be sent, so that request was not sent", e);
    }
  }

  // The request completing the upload did not reach Files.com: a later run may send it.
  synchronized void finalNotSent() {
    try {
      append(entry("not-sent"));
      forceAll();
    } catch (IOException e) {
      // The record still says the request was about to be sent, so a later run reports that the
      // outcome is unknown rather than send it: cautious, never wrong.
      log.debug("Could not record that the completing request of a checkpointed upload was not sent", e);
    }
  }

  // The upload is over: completed, or refused by Files.com. The record is deleted.
  synchronized void delete() {
    if (closed) {
      return;
    }
    closed = true;
    try {
      record.delete();
    } catch (IOException e) {
      log.warn("Could not delete the checkpoint record of a finished upload; a later run of the same upload reports it as of unknown"
          + " outcome until the checkpoint is discarded");
      log.debug("Deleting checkpoint record {} failed", name, e);
    } finally {
      store.close();
    }
  }

  // Keeps the record for a later run: everything written is made durable, as far as it can be.
  @Override
  public synchronized void close() {
    if (closed) {
      return;
    }
    closed = true;
    try {
      if (durable < written) {
        record.force();
      }
    } catch (IOException e) {
      log.debug("Could not make the checkpoint record durable", e);
    } finally {
      record.close();
      store.close();
    }
  }

  private void append(ObjectNode entry) throws IOException {
    byte[] frame = frame(entry);
    if (written + frame.length > CheckpointStore.MAX_RECORD_BYTES) {
      throw new TransferCheckpointException(TransferCheckpointException.Reason.TOO_LARGE, directory, "The checkpoint record " + name + " in "
          + directory + " would hold more than " + CheckpointStore.MAX_RECORD_BYTES + " bytes, so the upload could not go on recording it");
    }
    record.append(frame);
    written += frame.length;
  }

  private void forceAll() throws IOException {
    record.force();
    durable = written;
  }

  private static ObjectNode entry(String type) {
    ObjectNode entry = ClientRuntime.MAPPER.createObjectNode();
    entry.put("type", type);
    return entry;
  }

  // An entry as a frame: its body's length, the CRC-32 of its body, and its body. Download checkpoints
  // (DownloadCheckpoint) use the same framing.
  static byte[] frame(JsonNode entry) {
    byte[] body = entry.toString().getBytes(StandardCharsets.UTF_8);
    CRC32 crc = new CRC32();
    crc.update(body, 0, body.length);
    ByteBuffer frame = ByteBuffer.allocate(FRAME_HEADER_BYTES + body.length);
    frame.putInt(body.length).putInt((int) crc.getValue()).put(body);
    return frame.array();
  }

  // Reads the entries of bytes into entries, and returns where the complete ones end: before a last
  // frame cut short, or of nothing but zeros, as a crash can leave one. Anything else that is not a
  // complete frame makes the record unreadable.
  private static long readEntries(byte[] bytes, List<JsonNode> entries, Path directory, String name) {
    return readEntries(bytes, entries, null, directory, name);
  }

  // As above, also adding to ends, when it is not null, where each entry's frame ends.
  static long readEntries(byte[] bytes, List<JsonNode> entries, List<Long> ends, Path directory, String name) {
    ByteBuffer buffer = ByteBuffer.wrap(bytes);
    while (buffer.remaining() > 0) {
      int start = buffer.position();
      if (buffer.remaining() < FRAME_HEADER_BYTES || isTail(bytes, start)) {
        return start;
      }
      int length = buffer.getInt();
      int checksum = buffer.getInt();
      if (length <= 0 || length > buffer.remaining()) {
        if (length > buffer.remaining()) {
          return start;
        }
        throw unreadable(directory, name, "it is damaged");
      }
      byte[] body = new byte[length];
      buffer.get(body);
      CRC32 crc = new CRC32();
      crc.update(body, 0, length);
      if ((int) crc.getValue() != checksum) {
        if (buffer.remaining() == 0) {
          return start;
        }
        throw unreadable(directory, name, "it is damaged");
      }
      try {
        JsonNode entry = ClientRuntime.MAPPER.readTree(body);
        if (entry == null || !entry.isObject()) {
          throw unreadable(directory, name, "it is damaged");
        }
        entries.add(entry);
        if (ends != null) {
          ends.add((long) buffer.position());
        }
      } catch (IOException e) {
        throw unreadable(directory, name, "it is damaged");
      }
    }
    return buffer.position();
  }

  // Whether the bytes from start on are all zeros: an end extended by a crash but never written.
  private static boolean isTail(byte[] bytes, int start) {
    for (int i = start; i < bytes.length; i++) {
      if (bytes[i] != 0) {
        return false;
      }
    }
    return true;
  }

  // The name of the record of an upload: the SHA-256 of the transfer it records, so that no part of a
  // path or of the API root is in it. Unchanged since version 1, so that a record of that version is
  // found, and refused, rather than left behind unseen.
  static String recordName(String apiRoot, String workspace, String remotePath, String localPath) {
    MessageDigest digest = sha256();
    for (String part : new String[] {"upload", apiRoot, workspace, remotePath, localPath}) {
      digest.update(part.getBytes(StandardCharsets.UTF_8));
      digest.update((byte) 0);
    }
    return hex(digest.digest()) + ".upload";
  }

  static MessageDigest sha256() {
    try {
      return MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException("SHA-256 is unavailable", e);
    }
  }

  static String hex(byte[] bytes) {
    StringBuilder hex = new StringBuilder();
    for (byte b : bytes) {
      hex.append(String.format("%02x", b & 0xff));
    }
    return hex.toString();
  }

  // The SHA-256 of the whole API root, which a record holds instead of the root.
  static String apiRootDigest(URI apiRoot) {
    return hex(sha256().digest(apiRoot.toString().getBytes(StandardCharsets.UTF_8)));
  }

  // The API root's scheme, host and port, without the user information, path, query or fragment it
  // may carry.
  static String apiOrigin(URI apiRoot) {
    return apiRoot.getScheme().toLowerCase(Locale.ROOT) + "://" + apiRoot.getHost() + (apiRoot.getPort() >= 0 ? ":" + apiRoot.getPort() : "");
  }

  // Deletes the record name from directory, unless a run holds it; returns whether it held anything.
  static boolean discard(Path directory, String name) throws IOException {
    try (CheckpointStore store = CheckpointStore.open(directory)) {
      CheckpointStore.Record record = store.lock(name);
      boolean held;
      try {
        held = record.size() > 0;
      } catch (IOException | RuntimeException | Error e) {
        record.close();
        throw e;
      }
      record.delete();
      return held;
    }
  }

  static TransferCheckpointException unreadable(Path directory, String name, String why) {
    return new TransferCheckpointException(TransferCheckpointException.Reason.UNREADABLE, directory, "The checkpoint record " + name + " in "
        + directory + " cannot be used: " + why + ". Discard the checkpoint to start the transfer over");
  }

  // What a record is bound to: one transfer, the file as it was, and the part sizing asked for.
  static final class Binding {
    // The API root as the SDK holds it, whole, which tells transfers apart. It may carry user
    // information, a query or a fragment, so it is never written or shown: the record holds its
    // SHA-256 (apiRootDigest) and, to be read, its scheme, host and port (apiOrigin).
    private final String apiRoot;
    final String apiRootDigest;
    final String apiOrigin;
    final String workspace;
    final String remotePath;
    final String localPath;
    final String fileKey;
    final long size;
    final String modified;
    final String sizing;

    Binding(URI apiRoot, String workspace, String remotePath, String localPath, String fileKey, long size, String modified, String sizing) {
      this.apiRoot = apiRoot.toString();
      this.apiRootDigest = apiRootDigest(apiRoot);
      this.apiOrigin = apiOrigin(apiRoot);
      this.workspace = workspace;
      this.remotePath = remotePath;
      this.localPath = localPath;
      this.fileKey = fileKey;
      this.size = size;
      this.modified = modified;
      this.sizing = sizing;
    }

    String recordName() {
      return UploadCheckpoint.recordName(apiRoot, workspace, remotePath, localPath);
    }

    ObjectNode toJson() {
      ObjectNode json = ClientRuntime.MAPPER.createObjectNode();
      json.put("api_root_sha256", apiRootDigest);
      json.put("api_origin", apiOrigin);
      json.put("workspace", workspace);
      json.put("remote_path", remotePath);
      json.put("local_path", localPath);
      if (fileKey != null) {
        json.put("file_key", fileKey);
      }
      json.put("size", size);
      json.put("modified", modified);
      json.put("sizing", sizing);
      return json;
    }

    boolean sameTransfer(JsonNode bound) {
      return apiRootDigest.equals(bound.path("api_root_sha256").asText(null)) && workspace.equals(bound.path("workspace").asText(null))
          && remotePath.equals(bound.path("remote_path").asText(null)) && localPath.equals(bound.path("local_path").asText(null));
    }

    boolean sameSettings(JsonNode bound) {
      return sizing.equals(bound.path("sizing").asText(null));
    }

    boolean sameSource(JsonNode bound) {
      return size == bound.path("size").asLong(-1) && modified.equals(bound.path("modified").asText(null))
          && (fileKey == null ? !bound.has("file_key") : fileKey.equals(bound.path("file_key").asText(null)));
    }
  }

  // What an earlier run of the upload recorded.
  static final class Saved {
    // The upload's reference, or null when the earlier run ended before Files.com began it.
    final String ref;
    final boolean sizesChosen;
    final String uploadIdentity;
    // Every part planned, by number: its offset and length.
    final TreeMap<Long, long[]> planned;
    // Every part accepted, by number: its ETag, or null when it had none.
    final TreeMap<Long, String> accepted;
    // Whether the request completing the upload was about to be sent.
    final boolean finalizing;
    final Path directory;

    private Saved(String ref, boolean sizesChosen, String uploadIdentity, TreeMap<Long, long[]> planned, TreeMap<Long, String> accepted,
        boolean finalizing, Path directory) {
      this.ref = ref;
      this.sizesChosen = sizesChosen;
      this.uploadIdentity = uploadIdentity;
      this.planned = planned;
      this.accepted = accepted;
      this.finalizing = finalizing;
      this.directory = directory;
    }

    // The parts accepted, in order.
    List<UploadedPart> acceptedParts() {
      List<UploadedPart> parts = new ArrayList<UploadedPart>();
      for (Map.Entry<Long, String> part : accepted.entrySet()) {
        long[] boundaries = planned.get(part.getKey());
        parts.add(new UploadedPart(part.getKey(), boundaries[0], boundaries[1], part.getValue(), 0));
      }
      return parts;
    }

    // Files.com no longer gives the upload what the record describes.
    TransferCheckpointException changed(String why) {
      return new TransferCheckpointException(TransferCheckpointException.Reason.UPLOAD_CHANGED, directory, why + ", so the upload its"
          + " checkpoint records cannot be continued, and the part concerned was not sent again. Discard the checkpoint to upload the"
          + " file again");
    }

    // Files.com does not let parts of the upload be sent again (retry_parts), and the run that
    // recorded part number may have sent it.
    TransferCheckpointException notResendable(long number) {
      return new TransferCheckpointException(TransferCheckpointException.Reason.UPLOAD_CHANGED, directory, "Part " + number + " of the"
          + " upload its checkpoint records may already have been sent by the run that recorded it, and Files.com does not allow"
          + " sending a part again (retry_parts), so the upload cannot be continued and the part was not sent again. Discard the"
          + " checkpoint, then upload the file again from the start");
    }

    // Replays the entries after the header, checking that they describe one upload of a file of the
    // binding's length: parts numbered from 1, contiguous, within the file, and accepted only once
    // planned.
    static Saved replay(List<JsonNode> entries, Binding binding, Path directory, String name) {
      String ref = null;
      boolean sizesChosen = false;
      String uploadIdentity = null;
      TreeMap<Long, long[]> planned = new TreeMap<Long, long[]>();
      TreeMap<Long, String> accepted = new TreeMap<Long, String>();
      boolean finalizing = false;
      long end = 0;
      for (JsonNode entry : entries.subList(1, entries.size())) {
        String type = entry.path("type").asText();
        if ("upload".equals(type)) {
          String recorded = entry.path("ref").asText(null);
          if (ref != null || recorded == null || recorded.isEmpty()) {
            throw unreadable(directory, name, "it records the upload's beginning more than once, or without its reference");
          }
          ref = recorded;
          sizesChosen = entry.path("sizes_chosen").asBoolean();
          uploadIdentity = entry.path("s3_upload").asText(null);
          if (sizesChosen && uploadIdentity == null) {
            throw unreadable(directory, name, "it records sizes the SDK chose without the S3 upload they went to");
          }
        } else if ("planned".equals(type)) {
          JsonNode part = entry.path("part");
          long number = part.path(0).asLong(-1);
          long offset = part.path(1).asLong(-1);
          long length = part.path(2).asLong(-1);
          if (ref == null || number != planned.size() + 1 || offset != end || length < 0 || end + length > binding.size) {
            throw unreadable(directory, name, "its planned parts are not in order within the file");
          }
          planned.put(number, new long[] {offset, length});
          end += length;
        } else if ("accepted".equals(type)) {
          JsonNode part = entry.path("part");
          long number = part.path(0).asLong(-1);
          if (!planned.containsKey(number)) {
            throw unreadable(directory, name, "it records an accepted part that was not planned");
          }
          accepted.put(number, part.has(1) ? part.path(1).asText() : null);
        } else if ("finalizing".equals(type)) {
          if (ref == null) {
            throw unreadable(directory, name, "it records the upload's completion before its beginning");
          }
          finalizing = true;
        } else if ("not-sent".equals(type)) {
          finalizing = false;
        } else {
          throw unreadable(directory, name, "it holds an entry this version of the SDK does not know");
        }
      }
      return new Saved(ref, sizesChosen, uploadIdentity, planned, accepted, finalizing, directory);
    }
  }
}
