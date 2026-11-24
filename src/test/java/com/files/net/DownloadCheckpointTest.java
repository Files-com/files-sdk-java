package com.files.net;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.files.exceptions.TransferCheckpointException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.CRC32;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

// The record a download keeps its checkpoint in, on this platform's checkpoint store: how large it
// grows, what it reads back, and which ranges it refuses.
public class DownloadCheckpointTest {
  private static final long SIZE = 1L << 40;

  @Rule
  public TemporaryFolder folder = new TemporaryFolder();

  private Path directory;
  private DownloadCheckpoint.Binding binding;

  @Before
  public void setUp() {
    directory = folder.getRoot().toPath().resolve("checkpoints");
    binding = new DownloadCheckpoint.Binding(URI.create("https://example.files.com"), "", "remote/big.bin", "/tmp/big.bin");
  }

  @Test
  public void aRecordOfManySnapshotsStaysSmallAndReadsBackTheLatest() throws Exception {
    long largest = 0;
    List<long[]> latest = null;
    try (DownloadCheckpoint checkpoint = DownloadCheckpoint.open(directory, binding)) {
      checkpoint.recordStage(new DownloadStage.Identity(".big.bin.1", "1:2", "1:3"));
      checkpoint.recordVersion("\"v1\"", SIZE, "https://storage.example/big.bin", ranges(1, 0));
      // 500 snapshots of 200 to 700 ranges: several MiB were they all kept.
      for (int i = 0; i < 500; i++) {
        latest = ranges(200 + i, i);
        checkpoint.snapshot(latest);
        largest = Math.max(largest, Files.size(record()));
      }
    }
    assertTrue("at most " + largest + " bytes", largest < 300 * 1024);

    try (DownloadCheckpoint reopened = DownloadCheckpoint.open(directory, binding)) {
      DownloadCheckpoint.Saved saved = reopened.saved();
      assertEquals(".big.bin.1", saved.stage.directory);
      assertEquals("\"v1\"", saved.version.etag);
      assertEquals(SIZE, saved.version.size);
      assertEquals(latest.size(), saved.ranges.size());
      for (int i = 0; i < latest.size(); i++) {
        assertArrayEquals(latest.get(i), saved.ranges.get(i));
      }
    }
  }

  @Test
  public void aSnapshotHoldsAtMostItsLimitOfRangesTheLowestOnes() throws Exception {
    List<long[]> many = ranges(DownloadCheckpoint.MAX_RANGES + 500, 0);
    try (DownloadCheckpoint checkpoint = DownloadCheckpoint.open(directory, binding)) {
      checkpoint.recordStage(new DownloadStage.Identity(".big.bin.1", "1:2", "1:3"));
      checkpoint.recordVersion("\"v1\"", SIZE, "https://storage.example/big.bin", many);
    }
    try (DownloadCheckpoint reopened = DownloadCheckpoint.open(directory, binding)) {
      List<long[]> saved = reopened.saved().ranges;
      assertEquals(DownloadCheckpoint.MAX_RANGES, saved.size());
      assertArrayEquals(many.get(0), saved.get(0));
      assertArrayEquals(many.get(DownloadCheckpoint.MAX_RANGES - 1), saved.get(DownloadCheckpoint.MAX_RANGES - 1));
    }
  }

  @Test
  public void rangesThatOverlapOrLeaveTheFileAreRefused() throws Exception {
    for (String done : new String[]{"[[0,10],[5,20]]", "[[0,10],[20," + (SIZE + 1) + "]]", "[[10,5]]", "[[20,30],[0,10]]"}) {
      Files.deleteIfExists(directory.resolve(binding.recordName()));
      try (DownloadCheckpoint checkpoint = DownloadCheckpoint.open(directory, binding)) {
        checkpoint.recordStage(new DownloadStage.Identity(".big.bin.1", "1:2", "1:3"));
        checkpoint.recordVersion("\"v1\"", SIZE, "https://storage.example/big.bin", ranges(1, 0));
      }
      // An entry as the record frames it: its body's length, the CRC-32 of its body, and its body.
      byte[] body = ("{\"type\":\"ranges\",\"done\":" + done + "}").getBytes(StandardCharsets.UTF_8);
      CRC32 crc = new CRC32();
      crc.update(body, 0, body.length);
      Files.write(record(), ByteBuffer.allocate(8 + body.length).putInt(body.length).putInt((int) crc.getValue()).put(body).array(),
          StandardOpenOption.APPEND);
      byte[] written = Files.readAllBytes(record());
      try {
        DownloadCheckpoint.open(directory, binding).close();
        fail("Expected " + done + " to be refused");
      } catch (TransferCheckpointException e) {
        assertEquals(done, TransferCheckpointException.Reason.UNREADABLE, e.getReason());
      }
      assertArrayEquals("left as it was", written, Files.readAllBytes(record()));
    }
  }

  // count ranges of 10 bytes, 10 bytes apart, starting at 20 times shift.
  private static List<long[]> ranges(int count, long shift) {
    List<long[]> ranges = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      long start = 20 * (shift + i);
      ranges.add(new long[]{start, start + 10});
    }
    return ranges;
  }

  private Path record() throws Exception {
    try (Stream<Path> records = Files.list(directory)) {
      return records.findFirst().get();
    }
  }
}
