package com.files;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.anyRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.patch;
import static com.github.tomakehurst.wiremock.client.WireMock.patchRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.files.exceptions.ApiErrorException;
import com.files.models.Behavior;
import com.files.models.Bundle;
import com.files.models.Site;
import com.files.models.Style;
import com.files.models.User;
import com.files.net.Attachment;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.Level;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * API parameters of type file, sent by the generated operations to a loopback API: the request
 * body each attachment produces, what a retry sends again, and what the SDK reads, keeps open,
 * copies and deletes.
 */
public class FilesAttachmentTest {
  // Binary content that looks like MIME framing, with a marker that must never reach a log or an error.
  private static final byte[] CONTENT = binary(5000, "FILE-BODY-SENTINEL\r\n--boundary\r\n\u0000ÿ");
  private static final Path TEMPORARY_DIRECTORY = Paths.get(System.getProperty("java.io.tmpdir"));

  @Rule
  public TemporaryFolder folder = new TemporaryFolder();

  private WireMockServer api;
  private String retryDelay;

  @Before
  public void setUp() {
    api = FilesApiTest.loopbackServer();
    api.start();
    useApi("http://localhost:" + api.port());
    FilesClient.apiKey = "test-key";
    retryDelay = String.valueOf(FilesConfig.getInstance().getInitialRetryDelayMillis());
    FilesClient.setProperty("initialRetryDelayMillis", "1");
  }

  @After
  public void tearDown() {
    FilesClient.setProperty("initialRetryDelayMillis", retryDelay);
    api.stop();
  }

  @Test
  public void aFileIsSentAsAFormPartWithItsMetadataAndTheOtherParameters() throws IOException {
    api.stubFor(patch(urlEqualTo("/api/rest/v1/users/5")).willReturn(okJson("{\"id\":5,\"name\":\"Canonical\"}")));
    Path avatar = folder.newFolder("private-directory").toPath().resolve("résumé \"1\".png");
    Files.write(avatar, CONTENT);
    Attachment attachment = Attachment.fromPath(avatar).withContentType("image/png");
    HashMap<String, Object> parameters = new HashMap<>();
    parameters.put("avatar_file", attachment);
    parameters.put("name", "Renée");
    parameters.put("disabled", false);
    parameters.put("notes", "");
    HashMap<String, Object> options = new HashMap<>();
    options.put("api_key", "per-call-key");
    options.put("workspace_id", 12L);

    User user = User.update(5L, parameters, options);

    assertEquals("Canonical", user.getName());
    LoggedRequest request = onlyRequest();
    assertEquals("PATCH", request.getMethod().getName());
    assertEquals("/api/rest/v1/users/5", request.getUrl());
    assertEquals("per-call-key", request.getHeader("X-FilesAPI-Key"));
    assertEquals("12", request.getHeader("X-Files-Workspace-Id"));
    assertTrue(request.getHeader("Content-Type"), request.getHeader("Content-Type").startsWith("multipart/form-data; boundary="));
    assertEquals(String.valueOf(request.getBody().length), request.getHeader("Content-Length"));

    Map<String, List<Part>> parts = formParts(request.getHeader("Content-Type"), request.getBody());
    Part file = single(parts, "avatar_file");
    assertEquals("résumé \"1\".png", file.filename);
    assertEquals("image/png", file.headers.get("content-type"));
    assertArrayEquals(CONTENT, file.content);
    assertEquals("Renée", single(parts, "name").text());
    assertEquals("false", single(parts, "disabled").text());
    assertEquals("", single(parts, "notes").text());
    assertEquals("the caller's parameters are left as they were", 4, parameters.size());
    assertSame(attachment, parameters.get("avatar_file"));
  }

  @Test
  public void everySchemaFileParameterReachesItsOperationFromEachKindOfSource() throws IOException {
    api.stubFor(any(anyUrl()).willReturn(okJson("{\"id\":1}")));
    Route[] routes = {
        new Route("PATCH", "/api/rest/v1/site", Site::update, "icon16_file", "icon32_file", "icon48_file",
            "icon128_file", "logo_file", "bundle_watermark_attachment_file", "login_page_background_image_file"),
        new Route("POST", "/api/rest/v1/users", User::create, "avatar_file").with("username", "sam"),
        new Route("PATCH", "/api/rest/v1/users/1", p -> User.update(1L, p), "avatar_file"),
        new Route("POST", "/api/rest/v1/behaviors", Behavior::create, "attachment_file")
            .with("path", "inbox").with("behavior", "watermark"),
        new Route("PATCH", "/api/rest/v1/behaviors/2", p -> Behavior.update(2L, p), "attachment_file"),
        new Route("POST", "/api/rest/v1/bundles", Bundle::create, "watermark_attachment_file")
            .with("paths", new String[]{"shared"}),
        new Route("PATCH", "/api/rest/v1/bundles/3", p -> Bundle.update(3L, p), "watermark_attachment_file"),
        new Route("PATCH", "/api/rest/v1/styles/brand", p -> Style.update("brand", p), "file"),
    };

    int occurrence = 0;
    for (Route route : routes) {
      api.resetRequests();
      HashMap<String, Object> parameters = new HashMap<>(route.companions);
      Map<String, byte[]> contents = new HashMap<>();
      Map<String, String> filenames = new HashMap<>();
      for (String field : route.fileFields) {
        // The first occurrence is empty; each later one differs from every other.
        byte[] content = binary(occurrence == 0 ? 0 : 300 + occurrence, field);
        contents.put(field, content);
        filenames.put(field, field);
        switch (occurrence++ % 4) {
          case 0:
            parameters.put(field, content);
            break;
          case 1:
            parameters.put(field, Attachment.fromBytes(content));
            break;
          case 2:
            Path file = folder.getRoot().toPath().resolve(field + "-" + occurrence + ".bin");
            Files.write(file, content);
            parameters.put(field, Attachment.fromPath(file));
            filenames.put(field, file.getFileName().toString());
            break;
          default:
            parameters.put(field, Attachment.fromInputStream(new ByteArrayInputStream(content)));
        }
      }

      route.call.apply(parameters);

      LoggedRequest request = onlyRequest();
      assertEquals(route.method + " " + route.path, request.getMethod().getName() + " " + request.getUrl());
      Map<String, List<Part>> parts = formParts(request.getHeader("Content-Type"), request.getBody());
      for (String field : route.fileFields) {
        Part part = single(parts, field);
        assertEquals(route.path + " " + field, filenames.get(field), part.filename);
        assertEquals(route.path + " " + field, "application/octet-stream", part.headers.get("content-type"));
        assertArrayEquals(route.path + " " + field, contents.get(field), part.content);
      }
    }
    assertEquals("the schema's canonical file parameters", 14, occurrence);
  }

  @Test
  public void savingANewAndThenAnExistingModelSendsItsFile() throws IOException {
    api.stubFor(post(urlEqualTo("/api/rest/v1/users")).willReturn(okJson("{\"id\":8,\"username\":\"sam\"}")));
    api.stubFor(patch(urlEqualTo("/api/rest/v1/users/8"))
        .willReturn(okJson("{\"id\":8,\"username\":\"sam\",\"name\":\"Sam\"}")));
    HashMap<String, Object> options = new HashMap<>();
    options.put("api_key", "per-call-key");
    User user = new User(new HashMap<>(), options);
    byte[] first = binary(1200, "first avatar");
    final byte[] second = binary(900, "second avatar");

    user.setUsername("sam");
    user.setAvatarFile(first);
    user.save();
    assertEquals(Long.valueOf(8), user.getId());
    assertNull("the server's representation replaces the attributes", user.getAvatarFile());

    user.setAvatarFile(second);
    user.setName("Sam");
    user.save();
    assertEquals("Sam", user.getName());

    List<LoggedRequest> requests = api.findAll(anyRequestedFor(anyUrl()));
    assertEquals(Arrays.asList("POST /api/rest/v1/users", "PATCH /api/rest/v1/users/8"), requests.stream()
        .map(r -> r.getMethod().getName() + " " + r.getUrl()).collect(Collectors.toList()));
    byte[][] sent = {first, second};
    for (int i = 0; i < 2; i++) {
      LoggedRequest request = requests.get(i);
      assertEquals("per-call-key", request.getHeader("X-FilesAPI-Key"));
      Map<String, List<Part>> parts = formParts(request.getHeader("Content-Type"), request.getBody());
      assertArrayEquals(sent[i], single(parts, "avatar_file").content);
      assertEquals("sam", single(parts, "username").text());
    }
  }

  @Test
  public void aRequestAFormCannotCarryExactlyIsSentAsJsonWithTheFileObject() throws IOException {
    api.stubFor(post(urlEqualTo("/api/rest/v1/bundles")).willReturn(okJson("{\"id\":4}")));
    List<JsonCase> cases = Arrays.asList(
        new JsonCase("a typed value inside an object", "watermark.png", map("watermark_value", map("transparency", 25L))),
        new JsonCase("a null inside an object", "watermark.png", map("watermark_value", map("position", null))),
        new JsonCase("an empty list", "watermark.png", map("paths", new String[0])),
        new JsonCase("an empty object", "watermark.png", map("watermark_value", new HashMap<String, Object>())),
        new JsonCase("an empty key inside an object", "watermark.png", map("watermark_value", map("", "center"))),
        new JsonCase("a list of objects", "watermark.png",
            map("watermark_value", map("layers", Arrays.asList(map("x", "1"), map("y", "2"))))),
        new JsonCase("a filename the form parser would change", "50%.png", map("note", "text")));

    for (JsonCase c : cases) {
      c.companions.putIfAbsent("paths", new String[]{"shared"});
      api.resetRequests();
      Bundle.create(new HashMap<>(c.companions));
      final LoggedRequest control = onlyRequest();

      api.resetRequests();
      HashMap<String, Object> parameters = new HashMap<>(c.companions);
      parameters.put("watermark_attachment_file", Attachment.fromBytes(CONTENT).withFilename(c.filename)
          .withContentType("image/png"));
      Bundle.create(parameters);
      LoggedRequest request = onlyRequest();

      assertEquals(c.description, "application/json; charset=UTF-8", request.getHeader("Content-Type"));
      assertEquals(c.description, String.valueOf(request.getBody().length), request.getHeader("Content-Length"));
      ObjectNode body = (ObjectNode) new ObjectMapper().readTree(request.getBody());
      JsonNode file = body.remove("watermark_attachment_file");
      assertArrayEquals(c.description, CONTENT, Base64.getDecoder().decode(file.get("encoded_content").textValue()));
      assertEquals(c.description, c.filename, file.get("filename").textValue());
      assertEquals(c.description, "image/png", file.get("type").textValue());
      assertEquals(c.description + ": the other parameters as JSON sends them without a file",
          new ObjectMapper().readTree(control.getBody()), body);
    }
  }

  @Test
  public void aRetryAfterAnErrorResponseSendsTheSameBodyFromTheCopyOfAStream() throws IOException {
    api.stubFor(patch(urlEqualTo("/api/rest/v1/site")).inScenario("retry").whenScenarioStateIs(STARTED)
        .willReturn(aResponse().withStatus(503).withBody("unavailable")).willSetStateTo("recovered"));
    api.stubFor(patch(urlEqualTo("/api/rest/v1/site")).inScenario("retry").whenScenarioStateIs("recovered")
        .willReturn(okJson("{}")));
    byte[] logo = binary(300 * 1024, "logo");
    BorrowedStream stream = new BorrowedStream(logo, 1000);
    Path icon = folder.newFolder("private-directory").toPath().resolve("icon.png");
    Files.write(icon, CONTENT);
    HashMap<String, Object> parameters = new HashMap<>();
    parameters.put("logo_file", Attachment.fromInputStream(stream).withMemoryThreshold(64 * 1024));
    parameters.put("icon16_file", Attachment.fromPath(icon));
    final List<Path> earlierCopies = temporaryCopies();

    SdkWarnings log = new SdkWarnings(Level.DEBUG);
    try {
      Site.update(parameters);
    } finally {
      log.close();
    }

    List<LoggedRequest> attempts = api.findAll(patchRequestedFor(urlEqualTo("/api/rest/v1/site")));
    assertEquals(2, attempts.size());
    assertArrayEquals("the retry sends the same bytes, framing included", attempts.get(0).getBody(),
        attempts.get(1).getBody());
    Map<String, List<Part>> parts = formParts(attempts.get(1).getHeader("Content-Type"), attempts.get(1).getBody());
    assertArrayEquals(logo, single(parts, "logo_file").content);
    assertArrayEquals(CONTENT, single(parts, "icon16_file").content);

    assertFalse("the SDK leaves the caller's stream open", stream.closed);
    assertEquals("the stream is read once, to its end", 1, stream.endsReached);
    stream.copiesAtEnd.removeAll(earlierCopies);
    assertEquals("the copy past the memory threshold went to one temporary file", 1, stream.copiesAtEnd.size());
    if (stream.copyPermissions != null) {
      assertEquals(PosixFilePermissions.fromString("rw-------"), stream.copyPermissions);
    }
    for (Path copy : stream.copiesAtEnd) {
      assertFalse("the temporary copy is deleted when the request ends: " + copy, Files.exists(copy));
    }
    String directory = icon.getParent().toString();
    assertTrue(log.events().toString(), log.events().stream()
        .anyMatch(e -> e.contains("Retrying the PATCH request (retry 1 of 3)") && e.endsWith("after HTTP status 503")));
    for (String event : log.events()) {
      assertFalse(event, event.contains("FILE-BODY-SENTINEL") || event.contains(directory));
    }
  }

  @Test
  public void aBodyCutOffWhileItWasBeingSentIsSentAgainWithTheSameBytesAsOneRetry() throws Exception {
    Path background = largerThanSocketBuffers("background.jpg");
    HashMap<String, Object> parameters = new HashMap<>();
    parameters.put("login_page_background_image_file", Attachment.fromPath(background));
    RawHttpServer server = new RawHttpServer(RawHttpServer.resetAfter(64 * 1024), RawHttpServer.respond(200, "{}"));
    SdkWarnings warnings = new SdkWarnings();
    try {
      useApi(server.root());
      Site.update(parameters);
    } finally {
      warnings.close();
      server.close();
    }

    List<RawHttpServer.Received> attempts = server.received();
    assertEquals(2, attempts.size());
    assertFalse("the first attempt was cut off", attempts.get(0).complete());
    assertTrue("the second attempt arrived in full", attempts.get(1).complete());
    byte[] first = attempts.get(0).body();
    byte[] second = attempts.get(1).body();
    assertArrayEquals("both attempts send the same bytes", first, Arrays.copyOf(second, first.length));
    Part part = single(formParts(attempts.get(1).header("Content-Type"), second), "login_page_background_image_file");
    assertArrayEquals(Files.readAllBytes(background), part.content);
    assertEquals("the SDK's own retry resent it, counted in its budget", 1, warnings.events().size());
    assertTrue(warnings.events().get(0), warnings.events().get(0).startsWith("WARN com.files.net.ClientRuntime Retrying the PATCH request (retry 1 of 3)"));
  }

  @Test
  public void aFileChangedAfterAnAttemptWasCutOffFailsTheResendInsteadOfSendingOtherContent() throws Exception {
    Path background = largerThanSocketBuffers("background.jpg");
    HashMap<String, Object> parameters = new HashMap<>();
    parameters.put("login_page_background_image_file", Attachment.fromPath(background));
    // The file keeps its size, so only its content shows the change.
    RawHttpServer server = new RawHttpServer((request, connection) -> {
      RawHttpServer.resetAfter(64 * 1024).to(request, connection);
      try (RandomAccessFile file = new RandomAccessFile(background.toFile(), "rw")) {
        file.write(binary(64 * 1024, "changed"));
      }
    }, RawHttpServer.respond(200, "{}"));
    try {
      useApi(server.root());
      Site.update(parameters);
      fail("Expected the changed file to fail the resend");
    } catch (UncheckedIOException e) {
      assertTrue(e.getMessage(), e.getMessage().contains("login_page_background_image_file changed while the request was being sent"));
    } finally {
      server.close();
    }

    List<RawHttpServer.Received> attempts = server.received();
    assertEquals(2, attempts.size());
    assertFalse("the first attempt was cut off", attempts.get(0).complete());
    assertFalse("the resend with the changed content is never completed", attempts.get(1).complete());
  }

  @Test
  public void aFileThatChangesBeforeARetryFailsTheRequestInsteadOfSendingOtherContent() throws Exception {
    Path directory = folder.newFolder("private-directory").toPath();
    Map<String, byte[]> changes = new LinkedHashMap<>();
    changes.put("different content, same size", binary(100 * 1024, "changed"));
    changes.put("more content", binary(100 * 1024 + 1, "logo"));
    for (Map.Entry<String, byte[]> change : changes.entrySet()) {
      Path logo = directory.resolve("logo.png");
      Files.write(logo, binary(100 * 1024, "logo"));
      HashMap<String, Object> parameters = new HashMap<>();
      parameters.put("logo_file", Attachment.fromPath(logo));
      RawHttpServer server = new RawHttpServer(
          RawHttpServer.respond(503, "{}", () -> write(logo, change.getValue())),
          RawHttpServer.respond(200, "{}"));
      SdkWarnings warnings = new SdkWarnings();
      try {
        useApi(server.root());
        Site.update(parameters);
        fail(change.getKey() + ": expected the changed file to fail the request");
      } catch (UncheckedIOException e) {
        assertTrue(e.getMessage(), e.getMessage().contains("logo_file changed while the request was being sent"));
        assertTrue(e.getCause().getMessage(), e.getCause().getMessage().startsWith("logo.png "));
        assertFalse(e.getCause().getMessage(), e.getCause().getMessage().contains(directory.toString()));
      } finally {
        warnings.close();
        server.close();
      }

      List<RawHttpServer.Received> attempts = server.received();
      assertEquals(change.getKey() + ": the SDK entered its retry", 1, warnings.events().size());
      assertTrue(warnings.events().get(0), warnings.events().get(0).startsWith("WARN com.files.net.ClientRuntime Retrying the PATCH request (retry 1 of 3)"));
      assertEquals(change.getKey() + ": only the original body is completed", 1L,
          attempts.stream().filter(RawHttpServer.Received::complete).count());
      assertTrue(change.getKey(), attempts.get(0).complete());
      // A rejected body may fail before the server reads the retry's headers.
      assertTrue(change.getKey(), attempts.size() <= 2);
      if (attempts.size() == 2) {
        assertFalse(change.getKey() + ": the retry's body is never completed", attempts.get(1).complete());
      }
    }
  }

  // A copy that ignored its limit could also keep reading forever.
  @Test(timeout = 10000)
  public void aStreamLongerThanItsSpoolLimitFailsBeforeAnythingIsSent() {
    BorrowedStream stream = new BorrowedStream(binary(4096, "avatar"), 4096);
    HashMap<String, Object> parameters = new HashMap<>();
    parameters.put("avatar_file", Attachment.fromInputStream(stream).withSpoolLimit(1000));

    try {
      User.update(1L, parameters);
      fail("Expected the stream to exceed its spool limit");
    } catch (IllegalArgumentException e) {
      assertTrue(e.getMessage(), e.getMessage().contains("avatar_file is a stream longer than its spool limit of 1000 bytes"));
    }

    assertEquals(0, api.getAllServeEvents().size());
    assertFalse(stream.closed);
    assertTrue("reads at most one byte past the limit: " + stream.bytesRead, stream.bytesRead <= 1001);
  }

  // One byte past Long.MAX_VALUE is not a long, and a copy that asked the stream for no bytes would never end.
  @Test(timeout = 10000)
  public void theLargestSpoolLimitStillCopiesTheWholeStream() throws IOException {
    api.stubFor(patch(urlEqualTo("/api/rest/v1/users/1")).willReturn(okJson("{\"id\":1}")));
    byte[] avatar = {1, 2, 3};
    BorrowedStream stream = new BorrowedStream(avatar, avatar.length);
    HashMap<String, Object> parameters = new HashMap<>();
    parameters.put("avatar_file", Attachment.fromInputStream(stream).withSpoolLimit(Long.MAX_VALUE));

    User.update(1L, parameters);

    LoggedRequest request = onlyRequest();
    assertArrayEquals(avatar, single(formParts(request.getHeader("Content-Type"), request.getBody()), "avatar_file").content);
    assertFalse(stream.closed);
  }

  @Test
  public void aSourceThatFailsStopsTheRequestWithItsOwnErrorAndRemovesEveryCopy() throws IOException {
    byte[] logo = binary(200 * 1024, "logo");
    BorrowedStream copied = new BorrowedStream(binary(200 * 1024, "icon"), 64 * 1024);
    IOException failure = new IOException("the caller's stream failed");
    BorrowedStream failing = new BorrowedStream(logo, 64 * 1024).failingAtEnd(failure);
    HashMap<String, Object> parameters = new HashMap<>();
    // The SDK copies icon16_file first, then fails on logo_file after copying part of it too.
    parameters.put("icon16_file", Attachment.fromInputStream(copied).withMemoryThreshold(1024));
    parameters.put("logo_file", Attachment.fromInputStream(failing).withMemoryThreshold(1024));
    final List<Path> earlierCopies = temporaryCopies();

    try {
      Site.update(parameters);
      fail("Expected the failing stream to stop the request");
    } catch (UncheckedIOException e) {
      assertEquals("Could not read attachment logo_file", e.getMessage());
      assertSame(failure, e.getCause());
    }

    assertEquals(0, api.getAllServeEvents().size());
    assertEquals("icon16_file had been copied", 1, copied.endsReached);
    failing.copiesAtEnd.removeAll(earlierCopies);
    assertEquals("both copies were on disk when logo_file failed", 2, failing.copiesAtEnd.size());
    for (Path copy : failing.copiesAtEnd) {
      assertFalse("copy removed: " + copy, Files.exists(copy));
    }
    assertFalse(copied.closed || failing.closed);

    parameters.clear();
    parameters.put("avatar_file", Attachment.fromPath(folder.getRoot().toPath().resolve("missing.png")));
    try {
      User.update(1L, parameters);
      fail("Expected the missing file to stop the request");
    } catch (UncheckedIOException e) {
      assertEquals("Could not read attachment avatar_file", e.getMessage());
      assertTrue(String.valueOf(e.getCause()), e.getCause() instanceof NoSuchFileException);
    }
    assertEquals(0, api.getAllServeEvents().size());
  }

  @Test(timeout = 60000)
  public void aCopyThatCannotBeDeletedLeavesTheRequestSuccessfulAndItsWarningWithoutThePath() throws Exception {
    // The API's hook replaces the SDK's copy, so the request runs in a child JVM whose temporary
    // directory this test creates and owns. No other directory is listed or changed.
    Path ownedTemporaryDirectory = folder.newFolder("child-tmp").toPath();
    List<Path> occupied = new ArrayList<>();
    // Before the API answers, the SDK's copy becomes a directory that is not empty, which it cannot delete.
    RawHttpServer server = new RawHttpServer(RawHttpServer.respond(200, "{}",
        () -> occupied.addAll(occupy(ownedTemporaryDirectory))));
    List<String> events;
    try {
      events = runInChildJvm(CopyThatCannotBeDeleted.class, ownedTemporaryDirectory, server.root());
    } finally {
      server.close();
      for (Path directory : occupied) {
        Files.delete(directory.resolve("occupied"));
        Files.delete(directory);
      }
    }

    assertEquals(1, occupied.size());
    String copy = occupied.get(0).getFileName().toString();
    List<String> warnings = events.stream().filter(event -> event.startsWith("WARN ")).collect(Collectors.toList());
    assertEquals(events.toString(), 1, warnings.size());
    String warning = warnings.get(0);
    assertTrue(warning, warning.contains("attachment logo_file (DirectoryNotEmptyException)"));
    assertFalse("the warning has no local path: " + warning,
        warning.contains(ownedTemporaryDirectory.toString()) || warning.contains(copy));
    assertTrue("DEBUG keeps the failure's detail: " + events,
        events.stream().anyMatch(event -> event.startsWith("DEBUG ") && event.contains(copy)));
  }

  /**
   * The request of the cleanup test, in a child JVM started with the test's own java.io.tmpdir.
   * Prints each event the SDK logs at DEBUG and above on a line of its own, then RESULT and the
   * outcome of the request.
   */
  public static final class CopyThatCannotBeDeleted {
    public static void main(String[] args) {
      FilesClient.apiKey = "test-key";
      FilesClient.setProperty("apiRoot", args[0]);
      HashMap<String, Object> parameters = new HashMap<>();
      parameters.put("logo_file", Attachment.fromInputStream(new ByteArrayInputStream(binary(8 * 1024, "logo")))
          .withMemoryThreshold(1024));
      SdkWarnings log = new SdkWarnings(Level.DEBUG);
      String result;
      try {
        Site.update(parameters);
        result = "success";
      } catch (RuntimeException e) {
        result = "failure " + e;
      } finally {
        log.close();
      }
      for (String event : log.events()) {
        System.out.println("EVENT " + event.replace("\n", "\\n"));
      }
      System.out.println("RESULT " + result);
      System.exit(0);
    }
  }

  @Test
  public void aRedirectedUpdateIsNotResentToTheRedirectTarget() {
    WireMockServer elsewhere = FilesApiTest.loopbackServer();
    elsewhere.start();
    try {
      elsewhere.stubFor(any(anyUrl()).willReturn(okJson("{\"id\":1}")));
      api.stubFor(patch(urlEqualTo("/api/rest/v1/users/1")).willReturn(aResponse().withStatus(307)
          .withHeader("Location", "http://localhost:" + elsewhere.port() + "/api/rest/v1/users/1")
          .withHeader("Content-Length", "0")));
      HashMap<String, Object> parameters = new HashMap<>();
      parameters.put("avatar_file", Attachment.fromBytes(CONTENT));

      try {
        User.update(1L, parameters);
        fail("Expected the redirect response to be rejected");
      } catch (ApiErrorException.ApiConnectionException e) {
        assertEquals("The Files.com API response body is not the expected JSON object", e.getMessage());
      }

      assertEquals(1, api.getAllServeEvents().size());
      assertEquals("HttpClient does not follow a redirect of a PATCH", 0, elsewhere.getAllServeEvents().size());
    } finally {
      elsewhere.stop();
    }
  }

  private LoggedRequest onlyRequest() {
    List<LoggedRequest> requests = api.findAll(anyRequestedFor(anyUrl()));
    assertEquals(1, requests.size());
    return requests.get(0);
  }

  private static void useApi(String root) {
    FilesClient.setProperty("apiRoot", root);
  }

  // Larger than the loopback socket buffers, so a reset arrives while the body is still being written.
  private Path largerThanSocketBuffers(String name) throws IOException {
    Path file = folder.getRoot().toPath().resolve(name);
    byte[] block = binary(1024 * 1024, name);
    try (OutputStream out = Files.newOutputStream(file)) {
      for (int i = 0; i < 24; i++) {
        out.write(block);
      }
    }
    return file;
  }

  // Replaces each SDK copy in a temporary directory this test owns with a directory holding a file.
  private static List<Path> occupy(Path ownedDirectory) {
    try {
      List<Path> copies = temporaryCopies(ownedDirectory);
      for (Path copy : copies) {
        Files.delete(copy);
        Files.createDirectory(copy);
        Files.write(copy.resolve("occupied"), new byte[1]);
      }
      return copies;
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  // Runs a main class in a child JVM with this test's class path and the given java.io.tmpdir, which
  // takes effect before anything in the child creates a temporary file. Returns its EVENT lines after
  // checking that it reported a successful request.
  private List<String> runInChildJvm(Class<?> main, Path temporaryDirectory, String... args) throws Exception {
    List<String> command = new ArrayList<>(Arrays.asList(Paths.get(System.getProperty("java.home"), "bin", "java").toString(),
        "-Djava.io.tmpdir=" + temporaryDirectory, "-cp", System.getProperty("java.class.path"), main.getName()));
    command.addAll(Arrays.asList(args));
    Path output = folder.newFile("child-output.txt").toPath();
    Process child = new ProcessBuilder(command).redirectErrorStream(true).redirectOutput(output.toFile()).start();
    if (!child.waitFor(50, TimeUnit.SECONDS)) {
      child.destroyForcibly();
      fail("The child JVM did not finish: " + new String(Files.readAllBytes(output), StandardCharsets.UTF_8));
    }
    List<String> lines = Files.readAllLines(output, StandardCharsets.UTF_8);
    assertEquals(lines.toString(), 0, child.exitValue());
    assertTrue(lines.toString(), lines.contains("RESULT success"));
    return lines.stream().filter(line -> line.startsWith("EVENT ")).map(line -> line.substring("EVENT ".length()))
        .collect(Collectors.toList());
  }

  private static void write(Path file, byte[] content) {
    try {
      Files.write(file, content);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  private static HashMap<String, Object> map(Object... entries) {
    HashMap<String, Object> map = new HashMap<>();
    for (int i = 0; i < entries.length; i += 2) {
      map.put((String) entries[i], entries[i + 1]);
    }
    return map;
  }

  // Deterministic content that differs for each seed and contains every byte value.
  private static byte[] binary(int length, String seed) {
    byte[] prefix = seed.getBytes(StandardCharsets.ISO_8859_1);
    byte[] bytes = new byte[length];
    for (int i = 0; i < length; i++) {
      bytes[i] = i < prefix.length ? prefix[i] : (byte) (i * 7 + seed.hashCode());
    }
    return bytes;
  }

  private record JsonCase(String description, String filename, HashMap<String, Object> companions) {
  }

  /** A generated operation and the file parameters it takes. */
  private static final class Route {
    final String method;
    final String path;
    final Function<HashMap<String, Object>, Object> call;
    final String[] fileFields;
    final HashMap<String, Object> companions = new HashMap<>();

    Route(String method, String path, Function<HashMap<String, Object>, Object> call, String... fileFields) {
      this.method = method;
      this.path = path;
      this.call = call;
      this.fileFields = fileFields;
    }

    Route with(String name, Object value) {
      companions.put(name, value);
      return this;
    }
  }

  /**
   * A caller's stream that cannot mark or reset, returns at most {@code maxRead} bytes per read and
   * records how it is used. At its end it notes the SDK's temporary copies that exist at that moment.
   */
  private static final class BorrowedStream extends InputStream {
    private final byte[] content;
    private final int maxRead;
    private IOException failure;
    int position;
    long bytesRead;
    int endsReached;
    boolean closed;
    List<Path> copiesAtEnd = new ArrayList<>();
    Set<PosixFilePermission> copyPermissions;

    BorrowedStream(byte[] content, int maxRead) {
      this.content = content;
      this.maxRead = maxRead;
    }

    BorrowedStream failingAtEnd(IOException failure) {
      this.failure = failure;
      return this;
    }

    @Override
    public int read() throws IOException {
      byte[] one = new byte[1];
      return read(one, 0, 1) == -1 ? -1 : one[0] & 0xff;
    }

    @Override
    public int read(byte[] buffer, int offset, int length) throws IOException {
      if (position == content.length) {
        endsReached++;
        copiesAtEnd = temporaryCopies();
        if (!copiesAtEnd.isEmpty() && Files.getFileStore(copiesAtEnd.get(0)).supportsFileAttributeView("posix")) {
          copyPermissions = Files.getPosixFilePermissions(copiesAtEnd.get(0));
        }
        if (failure != null) {
          throw failure;
        }
        return -1;
      }
      int count = Math.min(Math.min(length, maxRead), content.length - position);
      System.arraycopy(content, position, buffer, offset, count);
      position += count;
      bytesRead += count;
      return count;
    }

    @Override
    public void close() {
      closed = true;
    }
  }

  // The SDK's temporary copies of streams, which it names files-sdk-attachment-*.
  private static List<Path> temporaryCopies() throws IOException {
    return temporaryCopies(TEMPORARY_DIRECTORY);
  }

  private static List<Path> temporaryCopies(Path directory) throws IOException {
    try (Stream<Path> files = Files.list(directory)) {
      return files.filter(file -> file.getFileName().toString().startsWith("files-sdk-attachment-"))
          .collect(Collectors.toList());
    }
  }

  /** One part of a multipart/form-data body, decoded from its framing. */
  private static final class Part {
    final Map<String, String> headers = new HashMap<>();
    String name;
    String filename;
    byte[] content;

    String text() {
      return new String(content, StandardCharsets.UTF_8);
    }
  }

  private static Part single(Map<String, List<Part>> parts, String name) {
    List<Part> named = parts.get(name);
    assertTrue(name + " in " + parts.keySet(), named != null && named.size() == 1);
    return named.get(0);
  }

  private static final Pattern DISPOSITION_PARAMETER = Pattern.compile(";\\s*(\\w+)=\"((?:\\\\.|[^\"\\\\])*)\"");

  // Splits a body at its boundary delimiters and decodes each part's headers, as RFC 7578 frames them.
  private static Map<String, List<Part>> formParts(String contentType, byte[] body) {
    Matcher boundary = Pattern.compile("boundary=([^;]+)").matcher(contentType);
    assertTrue(contentType, boundary.find());
    byte[] delimiter = ("--" + boundary.group(1)).getBytes(StandardCharsets.US_ASCII);
    Map<String, List<Part>> parts = new LinkedHashMap<>();
    int start = indexOf(body, delimiter, 0);
    assertEquals("the body starts with a delimiter", 0, start);
    while (true) {
      int partStart = start + delimiter.length;
      if (body[partStart] == '-' && body[partStart + 1] == '-') {
        assertEquals("the closing delimiter ends the body", body.length, partStart + 4);
        return parts;
      }
      int next = indexOf(body, ("\r\n" + new String(delimiter, StandardCharsets.US_ASCII)).getBytes(StandardCharsets.US_ASCII),
          partStart);
      assertTrue("each part ends at a delimiter", next > 0);
      int headersEnd = indexOf(body, "\r\n\r\n".getBytes(StandardCharsets.US_ASCII), partStart);
      Part part = new Part();
      for (String header : new String(body, partStart + 2, headersEnd - partStart - 2, StandardCharsets.UTF_8).split("\r\n")) {
        int colon = header.indexOf(':');
        part.headers.put(header.substring(0, colon).trim().toLowerCase(), header.substring(colon + 1).trim());
      }
      Matcher parameter = DISPOSITION_PARAMETER.matcher(part.headers.get("content-disposition"));
      while (parameter.find()) {
        String value = parameter.group(2).replaceAll("\\\\(.)", "$1");
        if (parameter.group(1).equals("name")) {
          part.name = value;
        } else if (parameter.group(1).equals("filename")) {
          part.filename = value;
        }
      }
      part.content = Arrays.copyOfRange(body, headersEnd + 4, next);
      parts.computeIfAbsent(part.name, k -> new ArrayList<>()).add(part);
      start = next + 2;
    }
  }

  private static int indexOf(byte[] haystack, byte[] needle, int from) {
    outer:
    for (int i = from; i <= haystack.length - needle.length; i++) {
      for (int j = 0; j < needle.length; j++) {
        if (haystack[i + j] != needle[j]) {
          continue outer;
        }
      }
      return i;
    }
    return -1;
  }
}
