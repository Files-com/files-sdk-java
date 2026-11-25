package com.files.net;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.files.exceptions.TransferCheckpointException;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// A download's checkpoint: a record in a checkpoint directory (CheckpointStore), held and locked for
// the whole run, from which a later run of the same download continues it, in the partial file its
// stage (DownloadStage) kept, instead of fetching every byte again.
//
// What it holds. A header binds the record to one download: the API root the call is set to start
// at, as the SHA-256 of the whole of it with its scheme, host and port beside (as an upload's record
// keeps it), the workspace, the remote path and the destination's absolute path, which also name the
// record. Then: the stage, by the name of its directory beside the destination and the identities of
// that directory and its file; the version of the file the download is bound to, by its strong
// entity tag, its size and the SHA-256 of the storage resource it came from; snapshots of the ranges
// of that version the stage holds durably; and whether the file was about to be published. It holds
// no credentials, URL, query or destination to use: a later run describes the file again, or takes
// the caller's File, for a fresh URL, sends the entity tag only in If-Match to confirm the version,
// and compares the rest with its own.
//
// How it is written. As an upload's record is (UploadCheckpoint): a journal of CRC-32 framed JSON
// entries, appended in place, a torn last frame dropped. The order keeps every claim true:
//
//   1. The stage is recorded, durably, once it is created, before anything is written to it. A crash
//      between creating it and recording it leaves a private stage directory no record names, which
//      nothing removes.
//   2. The version and its first range are recorded once that range is written and the stage made
//      durable.
//   3. A snapshot of the ranges written whole is taken, the stage made durable, and only then is the
//      snapshot recorded: the record never claims a byte a crash could lose.
//   4. Before the stage's bytes change for another version, or for a download in one request, the
//      record is truncated back to its stage, durably: claims of the old bytes never outlive them.
//      Wherever entries are replaced, by a new stage or by compaction, the truncation is made durable
//      before the new entries are written, so that no old entry can come back after them.
//   5. Before the file is published, that it is about to be is recorded, durably; once it is, the
//      record is deleted.
//
// Its growth is bounded. A snapshot holds every range written, coalesced, which the parts in
// progress bound, and at most MAX_RANGES of them, the lowest: bytes of any further range are fetched
// again, never claimed. Snapshots are taken at most once a second while parts are written
// (Downloader), and once the record's snapshots outgrow the larger of COMPACT_BYTES and four times
// the latest, it is truncated back to its version and the latest snapshot written again. A process
// that stops meanwhile loses only claims, which are fetched again, never the stage. A power loss may
// leave the record damaged, and refused as unreadable; discarding it then leaves the stage, to be
// removed by hand.
final class DownloadCheckpoint implements AutoCloseable {
  private static final Logger log = LoggerFactory.getLogger(DownloadCheckpoint.class);
  static final String FORMAT = "files-sdk-java-download-checkpoint";
  static final int VERSION = 1;
  // The most ranges a snapshot holds.
  static final int MAX_RANGES = 4096;
  // The bytes of snapshots past which the record is compacted, when four times the latest is less.
  static final long COMPACT_BYTES = 256 * 1024;
  private static final SecureRandom RANDOM = new SecureRandom();
  private static final Pattern DIGEST = Pattern.compile("[0-9a-f]{64}");

  private final CheckpointStore store;
  private final CheckpointStore.Record record;
  private final Path directory;
  private final String name;
  private final Saved saved;
  // The bytes of the record, and how many of them are durable; where its header ends; where its
  // stage entry ends, and its version entry, or -1 when it records none.
  private long written;
  private long durable;
  private final long headerEnd;
  private long stageEnd;
  private long versionEnd;
  private boolean closed;

  private DownloadCheckpoint(CheckpointStore store, CheckpointStore.Record record, String name, Saved saved, long written, long headerEnd) {
    this.store = store;
    this.record = record;
    this.directory = store.directory;
    this.name = name;
    this.saved = saved;
    this.written = written;
    this.durable = written;
    this.headerEnd = headerEnd;
    this.stageEnd = saved != null ? saved.stageEnd : -1;
    this.versionEnd = saved != null ? saved.versionEnd : -1;
  }

  // Opens the checkpoint of the download binding names, in directory: what an earlier run recorded,
  // or a new record.
  static DownloadCheckpoint open(Path directory, Binding binding) throws IOException {
    CheckpointStore store = CheckpointStore.open(directory);
    try {
      String name = binding.recordName();
      for (int attempt = 0; ; attempt++) {
        CheckpointStore.Record record = store.lock(name);
        try {
          DownloadCheckpoint checkpoint = open(store, record, binding, name);
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

  private static DownloadCheckpoint open(CheckpointStore store, CheckpointStore.Record record, Binding binding, String name) throws IOException {
    List<JsonNode> entries = new ArrayList<JsonNode>();
    List<Long> ends = new ArrayList<Long>();
    long size = record.size();
    long end = read(record, size, entries, ends, store.directory, name);
    if (end < size) {
      // The last entry was cut short by a crash: it is dropped, so new entries follow complete ones.
      record.truncate(end);
      record.force();
    }
    if (!entries.isEmpty()) {
      checkHeader(entries.get(0), binding, store.directory, name);
      if (!record.isNamed()) {
        return null;
      }
      return new DownloadCheckpoint(store, record, name, Saved.replay(entries, ends, store.directory, name), end, ends.get(0));
    }
    // A new record: its header, with a nonce no other record has, so that a record another run
    // created under the same name meanwhile is told apart from this one.
    ObjectNode header = entry("header");
    header.put("format", FORMAT);
    header.put("version", VERSION);
    header.put("nonce", Long.toUnsignedString(RANDOM.nextLong(), 36) + Long.toUnsignedString(RANDOM.nextLong(), 36));
    header.set("binding", binding.toJson());
    byte[] frame = UploadCheckpoint.frame(header);
    record.append(frame);
    record.force();
    record.forceName();
    if (!record.isNamed()) {
      return null;
    }
    return new DownloadCheckpoint(store, record, name, null, frame.length, frame.length);
  }

  // Reads the record's complete entries into entries, and where each ends into ends; returns where
  // the complete ones end.
  private static long read(CheckpointStore.Record record, long size, List<JsonNode> entries, List<Long> ends, Path directory, String name)
      throws IOException {
    if (size > CheckpointStore.MAX_RECORD_BYTES) {
      throw UploadCheckpoint.unreadable(directory, name, "it holds " + size + " bytes, more than a record may hold");
    }
    return size == 0 ? 0 : UploadCheckpoint.readEntries(record.read(), entries, ends, directory, name);
  }

  private static void checkHeader(JsonNode header, Binding binding, Path directory, String name) {
    if (!FORMAT.equals(header.path("format").asText()) || !"header".equals(header.path("type").asText())) {
      throw UploadCheckpoint.unreadable(directory, name, "it is not a download checkpoint this SDK wrote");
    }
    if (header.path("version").asInt() != VERSION) {
      throw UploadCheckpoint.unreadable(directory, name, "it was written in format " + header.path("version").asText() + ", which this"
          + " version of the SDK does not read");
    }
    if (!binding.sameTransfer(header.path("binding"))) {
      throw new TransferCheckpointException(TransferCheckpointException.Reason.OTHER_TRANSFER, directory, "The checkpoint record " + name
          + " in " + directory + " is of another transfer");
    }
  }

  // Deletes the record of the download binding names in directory, and the partial download it
  // records if its stage is still the download's own; anything else there is left in place. Returns
  // whether there was a record.
  static boolean discard(Path directory, Binding binding, Path destination) throws IOException {
    try (CheckpointStore store = CheckpointStore.open(directory)) {
      String name = binding.recordName();
      CheckpointStore.Record record = store.lock(name);
      try {
        long size = record.size();
        if (size == 0) {
          record.delete();
          return false;
        }
        DownloadStage.Identity stage = null;
        try {
          List<JsonNode> entries = new ArrayList<JsonNode>();
          List<Long> ends = new ArrayList<Long>();
          read(record, size, entries, ends, store.directory, name);
          if (!entries.isEmpty()) {
            checkHeader(entries.get(0), binding, store.directory, name);
            stage = Saved.replay(entries, ends, store.directory, name).stage;
          }
        } catch (TransferCheckpointException e) {
          log.warn("The discarded download checkpoint could not be read; any partial download it recorded was left in place");
        }
        if (stage != null) {
          try {
            DownloadStage reopened = DownloadStage.reopen(destination, stage);
            if (reopened != null) {
              reopened.close();
            }
          } catch (IOException | RuntimeException e) {
            log.warn("The partial download of a discarded checkpoint is no longer the download's own, or could not be opened; it was"
                + " left in place");
            log.debug("Reopening the partial download of checkpoint {} failed", name, e);
          }
        }
        record.delete();
        return true;
      } catch (IOException | RuntimeException | Error e) {
        record.close();
        throw e;
      }
    }
  }

  // What an earlier run recorded, or null for a new record.
  Saved saved() {
    return saved;
  }

  // Whether the record holds a version, whose ranges it may claim.
  synchronized boolean hasVersion() {
    return versionEnd >= 0;
  }

  // Records the stage, durably, with its name in the directory where the platform can, before
  // anything is written to it; replacing a stage recorded before, which is gone, once the record no
  // longer holds it, durably.
  synchronized void recordStage(DownloadStage.Identity stage) throws IOException {
    if (stageEnd >= 0) {
      truncate(headerEnd);
      forceAll();
    }
    ObjectNode entry = entry("stage");
    entry.put("directory", stage.directory);
    entry.put("directory_id", stage.directoryId);
    entry.put("file_id", stage.fileId);
    append(entry);
    forceAll();
    record.forceName();
    stageEnd = written;
    versionEnd = -1;
  }

  // Records the version the download is bound to, with the ranges of it the stage holds durably.
  synchronized void recordVersion(String etag, long size, String resource, List<long[]> ranges) throws IOException {
    ObjectNode entry = entry("version");
    entry.put("etag", etag);
    entry.put("size", size);
    entry.put("resource_sha256", digest(resource));
    append(entry);
    versionEnd = written;
    append(rangesEntry(ranges));
    forceAll();
  }

  // Records the ranges the stage holds durably, replacing the last snapshot; compacting the record,
  // durably, before this one is written, once its snapshots outgrow the larger of COMPACT_BYTES and
  // four times this one.
  synchronized void snapshot(List<long[]> ranges) throws IOException {
    byte[] frame = UploadCheckpoint.frame(rangesEntry(ranges));
    if (written + frame.length - versionEnd > Math.max(COMPACT_BYTES, 4L * frame.length)) {
      truncate(versionEnd);
      forceAll();
    }
    appendFrame(frame);
    forceAll();
  }

  // Forgets the version and its ranges, durably, before the stage's bytes change.
  synchronized void clearVersion() throws IOException {
    if (versionEnd < 0) {
      return;
    }
    truncate(stageEnd);
    versionEnd = -1;
    forceAll();
  }

  // Records, durably, that the file, every byte of which the record claims, is about to be
  // published.
  synchronized void publishing() throws IOException {
    append(entry("publishing"));
    forceAll();
  }

  // The download is over: published, or not to be continued. The record is deleted.
  synchronized void delete() {
    if (closed) {
      return;
    }
    closed = true;
    try {
      record.delete();
    } catch (IOException e) {
      log.warn("Could not delete the checkpoint record of a finished download; a later run of the same download finds the file published,"
          + " or starts over");
      log.debug("Deleting checkpoint record {} failed", name, e);
    } finally {
      store.close();
    }
  }

  // Keeps the record for a later run.
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
    appendFrame(UploadCheckpoint.frame(entry));
  }

  private void appendFrame(byte[] frame) throws IOException {
    if (written + frame.length > CheckpointStore.MAX_RECORD_BYTES) {
      throw new TransferCheckpointException(TransferCheckpointException.Reason.TOO_LARGE, directory, "The checkpoint record " + name + " in "
          + directory + " would hold more than " + CheckpointStore.MAX_RECORD_BYTES + " bytes, so the download could not go on recording it");
    }
    record.append(frame);
    written += frame.length;
  }

  private void truncate(long length) throws IOException {
    record.truncate(length);
    written = length;
    durable = Math.min(durable, length);
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

  // A snapshot: at most MAX_RANGES ranges, the lowest, each [start, end).
  private static ObjectNode rangesEntry(List<long[]> ranges) {
    ObjectNode entry = entry("ranges");
    ArrayNode done = entry.putArray("done");
    for (int i = 0; i < ranges.size() && i < MAX_RANGES; i++) {
      done.addArray().add(ranges.get(i)[0]).add(ranges.get(i)[1]);
    }
    return entry;
  }

  // The SHA-256 of text, as the record compares a storage resource.
  static String digest(String text) {
    MessageDigest digest = UploadCheckpoint.sha256();
    return UploadCheckpoint.hex(digest.digest(text.getBytes(StandardCharsets.UTF_8)));
  }

  // What a record is bound to: one download, to one destination.
  static final class Binding {
    // The API root, whole, which is never written or shown (see UploadCheckpoint.Binding).
    private final String apiRoot;
    final String apiRootDigest;
    final String apiOrigin;
    final String workspace;
    final String remotePath;
    final String destination;

    Binding(URI apiRoot, String workspace, String remotePath, String destination) {
      this.apiRoot = apiRoot.toString();
      this.apiRootDigest = UploadCheckpoint.apiRootDigest(apiRoot);
      this.apiOrigin = UploadCheckpoint.apiOrigin(apiRoot);
      this.workspace = workspace;
      this.remotePath = remotePath;
      this.destination = destination;
    }

    // The record's name: the SHA-256 of the download it records, so that no part of a path or of the
    // API root is in it.
    String recordName() {
      MessageDigest digest = UploadCheckpoint.sha256();
      for (String part : new String[] {"download", apiRoot, workspace, remotePath, destination}) {
        digest.update(part.getBytes(StandardCharsets.UTF_8));
        digest.update((byte) 0);
      }
      return UploadCheckpoint.hex(digest.digest()) + ".download";
    }

    ObjectNode toJson() {
      ObjectNode json = ClientRuntime.MAPPER.createObjectNode();
      json.put("api_root_sha256", apiRootDigest);
      json.put("api_origin", apiOrigin);
      json.put("workspace", workspace);
      json.put("remote_path", remotePath);
      json.put("destination", destination);
      return json;
    }

    boolean sameTransfer(JsonNode bound) {
      return apiRootDigest.equals(bound.path("api_root_sha256").asText(null)) && workspace.equals(bound.path("workspace").asText(null))
          && remotePath.equals(bound.path("remote_path").asText(null)) && destination.equals(bound.path("destination").asText(null));
    }
  }

  // The version of the file a record binds: its strong entity tag, its size and the SHA-256 of the
  // storage resource it came from.
  static final class Version {
    final String etag;
    final long size;
    final String resource;

    Version(String etag, long size, String resource) {
      this.etag = etag;
      this.size = size;
      this.resource = resource;
    }
  }

  // What an earlier run recorded.
  static final class Saved {
    // The stage, or null when none was recorded.
    final DownloadStage.Identity stage;
    // The version, or null when none is recorded.
    final Version version;
    // The ranges of the version the stage holds durably, [start, end), in order and apart.
    final List<long[]> ranges;
    // Whether the file was about to be published.
    final boolean publishing;
    final long stageEnd;
    final long versionEnd;

    private Saved(DownloadStage.Identity stage, Version version, List<long[]> ranges, boolean publishing, long stageEnd, long versionEnd) {
      this.stage = stage;
      this.version = version;
      this.ranges = ranges;
      this.publishing = publishing;
      this.stageEnd = stageEnd;
      this.versionEnd = versionEnd;
    }

    // Replays the entries after the header: a stage, recorded once; then a version, after the stage;
    // then snapshots of ranges of that version; and the mark that it was about to be published, once
    // every byte was claimed. Anything else makes the record unreadable.
    static Saved replay(List<JsonNode> entries, List<Long> ends, Path directory, String name) {
      DownloadStage.Identity stage = null;
      Version version = null;
      List<long[]> ranges = Collections.emptyList();
      boolean publishing = false;
      long stageEnd = -1;
      long versionEnd = -1;
      for (int i = 1; i < entries.size(); i++) {
        JsonNode entry = entries.get(i);
        String type = entry.path("type").asText();
        if ("stage".equals(type)) {
          String stageDirectory = entry.path("directory").asText("");
          String directoryId = entry.path("directory_id").asText("");
          String fileId = entry.path("file_id").asText("");
          if (stage != null || stageDirectory.isEmpty() || directoryId.isEmpty() || fileId.isEmpty()) {
            throw UploadCheckpoint.unreadable(directory, name, "it records its partial download more than once, or incompletely");
          }
          stage = new DownloadStage.Identity(stageDirectory, directoryId, fileId);
          stageEnd = ends.get(i);
        } else if ("version".equals(type)) {
          String etag = entry.path("etag").asText(null);
          long size = entry.path("size").asLong(-1);
          String resource = entry.path("resource_sha256").asText("");
          if (stage == null || version != null || !Downloader.isStrongEntityTag(etag) || size <= 0 || !DIGEST.matcher(resource).matches()) {
            throw UploadCheckpoint.unreadable(directory, name, "it records a version out of order, or incompletely");
          }
          version = new Version(etag, size, resource);
          versionEnd = ends.get(i);
          ranges = Collections.emptyList();
          publishing = false;
        } else if ("ranges".equals(type)) {
          if (version == null) {
            throw UploadCheckpoint.unreadable(directory, name, "it records ranges of no version");
          }
          ranges = parseRanges(entry.path("done"), version.size, directory, name);
          publishing = false;
        } else if ("publishing".equals(type)) {
          if (version == null || covered(ranges) != version.size) {
            throw UploadCheckpoint.unreadable(directory, name, "it records the publication of a file it does not hold whole");
          }
          publishing = true;
        } else {
          throw UploadCheckpoint.unreadable(directory, name, "it holds an entry this version of the SDK does not know");
        }
      }
      return new Saved(stage, version, ranges, publishing, stageEnd, versionEnd);
    }

    // Ranges [start, end) within a file of size, in order and apart, adjacent ones joined.
    private static List<long[]> parseRanges(JsonNode done, long size, Path directory, String name) {
      if (!done.isArray() || done.size() > MAX_RANGES) {
        throw UploadCheckpoint.unreadable(directory, name, "its ranges are not a list it could hold");
      }
      List<long[]> ranges = new ArrayList<long[]>();
      long previousEnd = 0;
      for (JsonNode range : done) {
        JsonNode start = range.path(0);
        JsonNode end = range.path(1);
        if (!range.isArray() || range.size() != 2 || !start.canConvertToLong() || !start.isIntegralNumber() || !end.canConvertToLong()
            || !end.isIntegralNumber() || start.asLong() < previousEnd || start.asLong() >= end.asLong() || end.asLong() > size) {
          throw UploadCheckpoint.unreadable(directory, name, "its ranges overlap, are out of order, or are outside the file");
        }
        if (!ranges.isEmpty() && ranges.get(ranges.size() - 1)[1] == start.asLong()) {
          ranges.get(ranges.size() - 1)[1] = end.asLong();
        } else {
          ranges.add(new long[] {start.asLong(), end.asLong()});
        }
        previousEnd = end.asLong();
      }
      return ranges;
    }

    private static long covered(List<long[]> ranges) {
      return ranges.size() == 1 && ranges.get(0)[0] == 0 ? ranges.get(0)[1] : -1;
    }
  }

  // Ranges [start, end) of a file, joined where they meet or overlap. Not safe for use by several
  // threads at once.
  static final class Ranges {
    private final TreeMap<Long, Long> ranges = new TreeMap<Long, Long>();
    private long bytes;

    void add(long start, long end) {
      Map.Entry<Long, Long> before = ranges.floorEntry(start);
      if (before != null && before.getValue() >= start) {
        start = before.getKey();
        end = Math.max(end, before.getValue());
        remove(before.getKey());
      }
      Map.Entry<Long, Long> after;
      while ((after = ranges.ceilingEntry(start)) != null && after.getKey() <= end) {
        end = Math.max(end, after.getValue());
        remove(after.getKey());
      }
      ranges.put(start, end);
      bytes += end - start;
    }

    private void remove(long start) {
      bytes -= ranges.remove(start) - start;
    }

    void clear() {
      ranges.clear();
      bytes = 0;
    }

    // The bytes the ranges hold.
    long bytes() {
      return bytes;
    }

    // The ranges, in order.
    List<long[]> list() {
      List<long[]> list = new ArrayList<long[]>(ranges.size());
      for (Map.Entry<Long, Long> range : ranges.entrySet()) {
        list.add(new long[] {range.getKey(), range.getValue()});
      }
      return list;
    }

    // The ranges of a file of size that are not held, in order.
    List<long[]> missing(long size) {
      List<long[]> missing = new ArrayList<long[]>();
      long next = 0;
      for (Map.Entry<Long, Long> range : ranges.entrySet()) {
        if (range.getKey() > next) {
          missing.add(new long[] {next, range.getKey()});
        }
        next = Math.max(next, range.getValue());
      }
      if (next < size) {
        missing.add(new long[] {next, size});
      }
      return missing;
    }
  }
}
