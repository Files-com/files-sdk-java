package com.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.databind.JsonNode;
import com.files.models.File;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HexFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestName;

/**
 * Downloads and uploads through the File model's static calls, against the simulator that the mock
 * server's launcher starts with the {@code file-transfers} scenario
 * ({@code targets/mock-server/scenarios/file-transfers.json}):
 *
 * <pre>
 * ruby generated/mock-server/with-scenario.rb --scenario file-transfers --out OUT -- \
 *   mvn -Dtest=FilesFileTest surefire:test
 * </pre>
 *
 * <p>Each test resets the simulator for an epoch of its own and checks the bytes it read or the
 * simulator stored, through the simulator's journal, as well as the File the SDK returned. Each saves
 * its epoch's journal in the launcher's output directory. Without the launcher the tests are skipped.
 */
public class FilesFileTest {
  private static final String SCENARIO = "file-transfers";
  // A fixed modification time for the uploads, so that every run sends the same requests.
  private static final Date MODIFIED = Date.from(Instant.parse("2026-09-24T07:11:22Z"));

  @Rule
  public final TemporaryFolder temporaryFolder = new TemporaryFolder();

  @Rule
  public final TestName testName = new TestName();

  private MockSimulator simulator;
  private long epoch;
  private JsonNode journal;
  private String previousApiKey;
  private String previousApiRoot;

  @Before
  public void resetSimulator() throws IOException {
    simulator = MockSimulator.launchedWith(SCENARIO);
    previousApiKey = FilesClient.apiKey;
    previousApiRoot = FilesConfig.getInstance().getApiRoot();
    epoch = simulator.reset();
    FilesClient.apiKey = simulator.apiKey();
    FilesClient.setProperty("apiRoot", simulator.url());
  }

  // Keeps the epoch's journal as evidence even when the test stopped before checking it.
  @After
  public void restoreDefaultClient() throws IOException {
    try {
      if (epoch > 0 && journal == null) {
        simulator.saveJournal(testName.getMethodName());
      }
    } finally {
      if (previousApiRoot != null) {
        FilesClient.apiKey = previousApiKey;
        FilesClient.setProperty("apiRoot", previousApiRoot);
      }
    }
  }

  @Test
  public void getFileStream() throws IOException {
    JsonNode expected = simulator.scenario().get("expected_download");
    File file = File.download(expected.get("path").asText(), null);
    byte[] received;
    try (InputStream inputStream = file.getInputStream()) {
      received = inputStream.readAllBytes();
    }
    assertDownloaded(expected, received);
  }

  @Test
  public void saveLocalFile() throws IOException {
    JsonNode expected = simulator.scenario().get("expected_download");
    File file = File.download(expected.get("path").asText(), null);
    Path directory = temporaryFolder.newFolder("downloads").toPath();
    Path destination = directory.resolve(file.getDisplayName());
    file.saveAsLocalFile(destination.toString());
    // The file is saved under the remote file's name, and the download leaves nothing else behind.
    assertEquals(Collections.singletonList(expected.get("path").asText()), names(directory));
    assertDownloaded(expected, Files.readAllBytes(destination));
  }

  @Test
  public void putInputStream() throws IOException, InterruptedException {
    String exampleString = "Sample Test Data";
    byte[] exampleBytes = exampleString.getBytes(StandardCharsets.UTF_8);
    File transferred;
    try (InputStream inputStream = new ByteArrayInputStream(exampleBytes)) {
      transferred = File.create("testUpload.txt", null).putInputStream(inputStream, MODIFIED);
    }
    assertEquals(exampleBytes.length, (long) transferred.getSize());
    assertStored("testUpload.txt", exampleBytes, transferred);
  }

  @Test
  public void putBufferedInputStream() throws IOException {
    String exampleString = "Sample Test Data";
    byte[] exampleBytes = exampleString.getBytes(StandardCharsets.UTF_8);
    File transferred;
    try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(exampleBytes))) {
      transferred = File.create("testUpload2.txt", null).putBufferedInputStream(bufferedInputStream, exampleBytes.length, MODIFIED);
    }
    assertEquals(exampleBytes.length, (long) transferred.getSize());
    assertStored("testUpload2.txt", exampleBytes, transferred);
  }

  @Test
  public void putFile() throws IOException {
    Path source = temporaryFolder.newFile("README.md").toPath();
    Files.write(source, "# README test\n\nA local file that FilesFileTest owns and uploads.\n".getBytes(StandardCharsets.UTF_8));
    File transferred = File.create("README-test.md", null).putLocalFile(source.toString());
    assertEquals(new java.io.File(source.toString()).getAbsoluteFile().length(), (long) transferred.getSize());
    assertStored("README-test.md", Files.readAllBytes(source), transferred);
  }

  // The bytes are exactly the scenario's file, as UTF-8 text too, and the simulator sent them once.
  private void assertDownloaded(JsonNode expected, byte[] received) throws IOException {
    assertEquals(expected.get("size").asLong(), received.length);
    assertEquals(expected.get("sha256").asText(), sha256(received));
    assertEquals(expected.get("text").asText(), strictUtf8(received));
    journal = simulator.journal(epoch, testName.getMethodName());
    List<JsonNode> described = MockSimulator.entries(journal, "files.download");
    assertEquals(1, described.size());
    assertEquals(200, described.get(0).path("status").asInt());
    List<JsonNode> sent = MockSimulator.entries(journal, "transfers.download");
    assertEquals(1, sent.size());
    assertEquals(200, sent.get(0).path("status").asInt());
    assertEquals(expected.get("sha256").asText(), sent.get(0).path("sha256").asText());
  }

  // The simulator published exactly these bytes at the path, as a new file, once, and answered every
  // request of the upload with success. Only a finalize that published a version names one.
  private void assertStored(String path, byte[] bytes, File transferred) throws IOException {
    assertEquals(path, transferred.getPath());
    journal = simulator.journal(epoch, testName.getMethodName());
    List<JsonNode> published = new ArrayList<>();
    for (JsonNode entry : MockSimulator.entries(journal, "files.finalize_upload")) {
      if (entry.has("version")) {
        published.add(entry);
      }
    }
    assertEquals(1, published.size());
    assertEquals("/api/rest/v1/files/" + path, published.get(0).path("path").asText());
    assertEquals(201, published.get(0).path("status").asInt());
    assertEquals(bytes.length, published.get(0).path("bytes").asLong(-1));
    assertEquals(sha256(bytes), published.get(0).path("sha256").asText());
    for (JsonNode entry : journal.path("entries")) {
      int status = entry.path("status").asInt();
      assertTrue(entry.toString(), status >= 200 && status < 300);
    }
  }

  private static List<String> names(Path directory) throws IOException {
    try (Stream<Path> children = Files.list(directory)) {
      return children.map(child -> child.getFileName().toString()).sorted().collect(Collectors.toList());
    }
  }

  private static String strictUtf8(byte[] bytes) throws CharacterCodingException {
    return StandardCharsets.UTF_8.newDecoder()
        .onMalformedInput(CodingErrorAction.REPORT)
        .onUnmappableCharacter(CodingErrorAction.REPORT)
        .decode(ByteBuffer.wrap(bytes))
        .toString();
  }

  private static String sha256(byte[] bytes) {
    try {
      return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes));
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException(e);
    }
  }
}
