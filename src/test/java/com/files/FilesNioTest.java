package com.files;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.absent;
import static com.github.tomakehurst.wiremock.client.WireMock.anyRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.matchingJsonPath;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.patch;
import static com.github.tomakehurst.wiremock.client.WireMock.patchRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.putRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.files.exceptions.ApiErrorException;
import com.files.exceptions.NotImplementedException;
import com.files.exceptions.SdkException;
import com.files.models.File;
import com.files.nio.FilesComFileSystems;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.NonWritableChannelException;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.nio.file.ClosedFileSystemException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

// The java.nio.file binding, through java.nio.file.Files, with the API and storage mocked over HTTP.
public class FilesNioTest {
  private static final String API = "/api/rest/v1";

  @Rule
  public TemporaryFolder folder = new TemporaryFolder();

  private WireMockServer api;
  private WireMockServer storage;
  private FilesClient client;
  private Path stage;
  private FileSystem files;

  @Before
  public void setUp() throws IOException {
    api = FilesApiTest.loopbackServer();
    api.start();
    storage = FilesApiTest.loopbackServer();
    storage.start();
    client = FilesClient.builder().apiKey("nio-key").apiRoot(apiRoot()).retryDelay(Duration.ofMillis(1)).build();
    stage = folder.newFolder("stage").toPath();
    files = FilesComFileSystems.builder(client).stageDirectory(stage).stageQuotaBytes(1024).actionTimeout(Duration.ofSeconds(30)).build();
  }

  @After
  public void tearDown() throws IOException {
    try {
      files.close();
    } finally {
      client.close();
      api.stop();
      storage.stop();
    }
  }

  @Test
  public void aNewFileIsPublishedEmptyAtOpenThenOnceWhenClosedAndReadsBackWithSeeks() throws Exception {
    byte[] bytes = new byte[300];
    for (int i = 0; i < bytes.length; i++) {
      bytes[i] = (byte) (i * 7);
    }
    api.stubFor(stat("new.bin").willReturn(apiError(404, "not-found/file-not-found")));
    remoteFile("new.bin", new byte[0], false);
    acceptUploads("new.bin");
    Path file = files.getPath("/new.bin");

    // A stage directory that cannot hold the copy fails the open before the file is published.
    try (FileSystem unstaged = FilesComFileSystems.builder(client).stageDirectory(stage.resolve("missing")).stageQuotaBytes(1024).build()) {
      assertThrows(IOException.class, () -> Files.newOutputStream(unstaged.getPath("/new.bin")));
    }
    api.verify(0, postRequestedFor(urlPathEqualTo(API + "/file_actions/begin_upload/new.bin")));
    assertEquals(0, uploadsEnded("new.bin"));

    try (OutputStream out = Files.newOutputStream(file)) {
      assertEquals("created, empty, before the stream is returned", 1, uploadsEnded("new.bin"));
      out.write(bytes, 0, 100);
      out.flush();
      assertEquals("flushing publishes nothing", 1, uploadsEnded("new.bin"));
      out.write(bytes, 100, 200);
    }
    assertEquals(2, uploadsEnded("new.bin"));
    assertArrayEquals(bytes, lastUpload("new.bin"));
    assertEquals("the copy is deleted once published", Collections.emptyList(), listing(stage));
    // The empty copy is not downloaded: nothing describes it for a download, or asks storage for it.
    api.verify(0, getRequestedFor(urlPathEqualTo(API + "/files/new.bin")).withQueryParam("action", absent()));
    storage.verify(0, getRequestedFor(urlPathEqualTo("/storage/new.bin")));

    remoteFile("new.bin", bytes, true);
    try (SeekableByteChannel channel = Files.newByteChannel(file)) {
      assertEquals(300, channel.size());
      ByteBuffer buffer = ByteBuffer.allocate(50);
      assertEquals(channel, channel.position(250));
      assertEquals(50, channel.read(buffer));
      assertArrayEquals(Arrays.copyOfRange(bytes, 250, 300), buffer.array());
      buffer.clear();
      assertEquals(-1, channel.read(buffer));
      assertEquals(300, channel.position());
      channel.position(10);
      buffer.clear();
      buffer.limit(5);
      assertEquals(5, channel.read(buffer));
      assertEquals(15, channel.position());
      assertEquals(0, channel.read(ByteBuffer.allocate(0)));
      assertThrows(NonWritableChannelException.class, () -> channel.write(ByteBuffer.wrap(new byte[1])));
      assertThrows(NonWritableChannelException.class, () -> channel.truncate(0));
    }
    assertArrayEquals(bytes, Files.readAllBytes(file));
    assertEquals("reading publishes nothing", 2, uploadsEnded("new.bin"));

    SeekableByteChannel closed = Files.newByteChannel(file);
    closed.close();
    closed.close();
    assertFalse(closed.isOpen());
    assertThrows(ClosedChannelException.class, () -> closed.read(ByteBuffer.allocate(1)));
    assertThrows(ClosedChannelException.class, () -> closed.position(0));
    assertEquals(Collections.emptyList(), listing(stage));
  }

  @Test
  public void anExistingFileIsChangedInPlaceOrAppendedToAndPublishedWhole() throws Exception {
    remoteFile("doc.txt", "0123456789".getBytes(StandardCharsets.UTF_8), true);
    acceptUploads("doc.txt");
    Path file = files.getPath("doc.txt");

    try (SeekableByteChannel channel = Files.newByteChannel(file, StandardOpenOption.READ, StandardOpenOption.WRITE)) {
      assertEquals("nothing is published when an existing file is opened", 0, uploadsEnded("doc.txt"));
      channel.position(2);
      assertEquals(2, channel.write(ByteBuffer.wrap("ab".getBytes(StandardCharsets.UTF_8))));
      assertEquals(4, channel.position());
      ByteBuffer rest = ByteBuffer.allocate(6);
      assertEquals(6, channel.read(rest));
      assertEquals("456789", new String(rest.array(), StandardCharsets.UTF_8));
      channel.truncate(8);
      assertEquals(8, channel.size());
      assertEquals(8, channel.position());
      channel.truncate(20);
      assertEquals("truncation never grows the file", 8, channel.size());
    }
    assertEquals("01ab4567", new String(lastUpload("doc.txt"), StandardCharsets.UTF_8));

    remoteFile("doc.txt", "01ab4567".getBytes(StandardCharsets.UTF_8), true);
    try (OutputStream out = Files.newOutputStream(file, StandardOpenOption.APPEND)) {
      out.write("XY".getBytes(StandardCharsets.UTF_8));
    }
    assertEquals("01ab4567XY", new String(lastUpload("doc.txt"), StandardCharsets.UTF_8));

    int published = uploadsEnded("doc.txt");
    Files.newByteChannel(file, StandardOpenOption.WRITE).close();
    assertEquals("a file opened for writing but not changed is not published again", published, uploadsEnded("doc.txt"));
    assertEquals(Collections.emptyList(), listing(stage));

    // The copy is read, written and uploaded through the handle that created it. On Windows, which
    // opens its stage's directory without delete sharing, renaming that directory is refused, and the
    // copy is used as before; elsewhere, a directory put in its place, by name, with a file of the
    // copy's name in it, is neither written, uploaded nor deleted.
    remoteFile("doc.txt", "0123".getBytes(StandardCharsets.UTF_8), true);
    boolean windows = System.getProperty("os.name").startsWith("Windows");
    Path replacement = null;
    try (SeekableByteChannel channel = Files.newByteChannel(file, StandardOpenOption.READ, StandardOpenOption.WRITE)) {
      List<String> stages = listing(stage);
      assertEquals(1, stages.size());
      Path own = stage.resolve(stages.get(0));
      if (windows) {
        assertThrows("the open stage directory cannot be renamed", FileSystemException.class,
            () -> Files.move(own, stage.resolve("moved-away")));
        assertEquals("and stays where it is", stages, listing(stage));
      } else {
        Files.move(own, stage.resolve("moved-away"));
        Files.createDirectory(own);
        replacement = own.resolve("download");
        Files.write(replacement, "not yours".getBytes(StandardCharsets.UTF_8));
      }
      channel.write(ByteBuffer.wrap("AB".getBytes(StandardCharsets.UTF_8)));
      ByteBuffer rest = ByteBuffer.allocate(2);
      assertEquals(2, channel.read(rest));
      assertEquals("23", new String(rest.array(), StandardCharsets.UTF_8));
    }
    assertEquals("AB23", new String(lastUpload("doc.txt"), StandardCharsets.UTF_8));
    if (!windows) {
      assertEquals("not yours", new String(Files.readAllBytes(replacement), StandardCharsets.UTF_8));
    }
  }

  @Test
  public void oneChannelWritesAFileAtATimeAndTheFileSystemSeesItsPendingSize() throws Exception {
    remoteFile("busy.txt", "abc".getBytes(StandardCharsets.UTF_8), true);
    acceptUploads("busy.txt");
    Path file = files.getPath("/busy.txt");

    try (SeekableByteChannel writer = Files.newByteChannel(file, StandardOpenOption.WRITE)) {
      writer.position(3);
      writer.write(ByteBuffer.wrap("defg".getBytes(StandardCharsets.UTF_8)));
      assertEquals(7, Files.size(file));
      final int requests = api.getAllServeEvents().size();
      assertThrows(FileSystemException.class, () -> Files.newByteChannel(file, StandardOpenOption.WRITE));
      assertThrows(FileSystemException.class, () -> Files.newInputStream(file));
      assertThrows(FileSystemException.class, () -> Files.delete(file));
      // Another spelling of the same file, as Files.com compares paths, is the same file.
      Path alias = files.getPath("/BUSY.txt");
      assertThrows(FileSystemException.class, () -> Files.newByteChannel(alias, StandardOpenOption.WRITE));
      assertThrows(FileSystemException.class, () -> Files.newInputStream(alias));
      assertThrows(FileSystemException.class, () -> Files.delete(alias));
      assertEquals("the pending view, by either spelling", 7, Files.size(alias));
      assertEquals("each was refused before any request", requests, api.getAllServeEvents().size());
    }
    assertEquals("abcdefg", new String(lastUpload("busy.txt"), StandardCharsets.UTF_8));
  }

  @Test
  public void openCopyAndAttributeRequestsThatCannotBeMetAreRefusedBeforeAnyRequest() throws Exception {
    Path file = files.getPath("/any.bin");
    List<Set<OpenOption>> refused = Arrays.asList(
        options(StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE),
        options(StandardOpenOption.CREATE, StandardOpenOption.WRITE),
        options(StandardOpenOption.CREATE, StandardOpenOption.APPEND),
        options(StandardOpenOption.WRITE, StandardOpenOption.SYNC),
        options(StandardOpenOption.WRITE, StandardOpenOption.DSYNC),
        options(StandardOpenOption.READ, StandardOpenOption.DELETE_ON_CLOSE));
    for (Set<OpenOption> options : refused) {
      UnsupportedOperationException e = assertThrows(options.toString(), UnsupportedOperationException.class,
          () -> Files.newByteChannel(file, options));
      assertNotImplemented(e);
    }
    assertThrows(IllegalArgumentException.class, () -> Files.newByteChannel(file, options(StandardOpenOption.READ, StandardOpenOption.APPEND)));
    assertThrows(IllegalArgumentException.class,
        () -> Files.newByteChannel(file, options(StandardOpenOption.APPEND, StandardOpenOption.TRUNCATE_EXISTING)));
    Path other = files.getPath("/other.bin");
    assertNotImplemented(assertThrows(UnsupportedOperationException.class, () -> Files.move(file, other, StandardCopyOption.ATOMIC_MOVE)));
    assertNotImplemented(assertThrows(UnsupportedOperationException.class, () -> Files.copy(file, other, StandardCopyOption.COPY_ATTRIBUTES)));
    assertNotImplemented(assertThrows(UnsupportedOperationException.class,
        () -> Files.setAttribute(file, "basic:creationTime", FileTime.fromMillis(0))));
    assertNotImplemented(assertThrows(UnsupportedOperationException.class,
        () -> Files.createDirectory(files.getPath("/d"), PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwx------")))));
    assertNotImplemented(assertThrows(UnsupportedOperationException.class, () -> Files.createSymbolicLink(other, file)));
    assertNotImplemented(assertThrows(UnsupportedOperationException.class, () -> file.toFile()));
    assertNotImplemented(assertThrows(UnsupportedOperationException.class, () -> files.newWatchService()));

    api.verify(0, anyRequestedFor(anyUrl()));
    storage.verify(0, anyRequestedFor(anyUrl()));
    assertEquals(Collections.emptyList(), listing(stage));
  }

  @Test
  public void aDirectoryIsListedOnePageAtATimeAndClosingTheStreamStopsFurtherPages() throws Exception {
    api.stubFor(stat("inbox").willReturn(okJson(folderJson("inbox"))));
    api.stubFor(get(urlPathEqualTo(API + "/folders/inbox")).withQueryParam("cursor", absent())
        .willReturn(okJson("[" + fileJson("inbox/a.txt", 1) + "," + folderJson("inbox/b") + "]").withHeader("X-Files-Cursor-Next", "page-2")));
    api.stubFor(get(urlPathEqualTo(API + "/folders/inbox")).withQueryParam("cursor", equalTo("page-2"))
        .willReturn(okJson("[" + fileJson("inbox/c.txt", 3) + "]")));
    Path inbox = files.getPath("/inbox");

    List<String> names = new ArrayList<String>();
    try (DirectoryStream<Path> entries = Files.newDirectoryStream(inbox)) {
      for (Path entry : entries) {
        names.add(entry.toString());
      }
    }
    assertEquals(Arrays.asList("/inbox/a.txt", "/inbox/b", "/inbox/c.txt"), names);
    try (DirectoryStream<Path> texts = Files.newDirectoryStream(inbox, "*.txt")) {
      List<String> matched = new ArrayList<String>();
      texts.forEach(entry -> matched.add(entry.getFileName().toString()));
      assertEquals(Arrays.asList("a.txt", "c.txt"), matched);
    }

    api.resetRequests();
    final DirectoryStream<Path> entries = Files.newDirectoryStream(inbox);
    try {
      Iterator<Path> iterator = entries.iterator();
      assertThrows(IllegalStateException.class, entries::iterator);
      assertEquals("/inbox/a.txt", iterator.next().toString());
      assertTrue(iterator.hasNext());
      entries.close();
      assertEquals("the entry already found is still returned", "/inbox/b", iterator.next().toString());
      assertFalse(iterator.hasNext());
    } finally {
      entries.close();
    }
    api.verify(1, getRequestedFor(urlPathEqualTo(API + "/folders/inbox")).withQueryParam("cursor", absent()));
    api.verify(0, getRequestedFor(urlPathEqualTo(API + "/folders/inbox")).withQueryParam("cursor", equalTo("page-2")));

    api.stubFor(stat("plain.txt").willReturn(okJson(fileJson("plain.txt", 1))));
    assertThrows(NotDirectoryException.class, () -> Files.newDirectoryStream(files.getPath("/plain.txt")));
  }

  @Test
  public void foldersAreCreatedOneAtATimeAndOnlyAnEmptyOneIsDeleted() throws Exception {
    api.stubFor(stat("a").willReturn(okJson(folderJson("a"))));
    api.stubFor(stat("a%2Fb").willReturn(apiError(404, "not-found/file-not-found")));
    api.stubFor(post(urlPathEqualTo(API + "/folders/a%2Fb%2Fc")).inScenario("tree").whenScenarioStateIs(Scenario.STARTED)
        .willReturn(apiError(404, "not-found/folder-not-found")));
    api.stubFor(post(urlPathEqualTo(API + "/folders/a%2Fb")).inScenario("tree").whenScenarioStateIs(Scenario.STARTED)
        .willSetStateTo("parent created").willReturn(created(folderJson("a/b"))));
    api.stubFor(post(urlPathEqualTo(API + "/folders/a%2Fb%2Fc")).inScenario("tree").whenScenarioStateIs("parent created")
        .willReturn(created(folderJson("a/b/c"))));

    Files.createDirectories(files.getPath("/a/b/c"));

    api.verify(2, postRequestedFor(urlPathEqualTo(API + "/folders/a%2Fb%2Fc"))
        .withRequestBody(matchingJsonPath("$.mkdir_parents", equalTo("false"))));
    api.verify(1, postRequestedFor(urlPathEqualTo(API + "/folders/a%2Fb"))
        .withRequestBody(matchingJsonPath("$.mkdir_parents", equalTo("false"))));

    api.stubFor(post(urlPathEqualTo(API + "/folders/a")).willReturn(apiError(422, "processing-failure/destination-exists")));
    FileAlreadyExistsException exists = assertThrows(FileAlreadyExistsException.class, () -> Files.createDirectory(files.getPath("/a")));
    assertTrue(exists.getCause() instanceof ApiErrorException);

    api.stubFor(post(urlPathEqualTo(API + "/file_actions/delete/a")).willReturn(apiError(422, "processing-failure/folder-not-empty")));
    DirectoryNotEmptyException notEmpty = assertThrows(DirectoryNotEmptyException.class, () -> Files.delete(files.getPath("/a")));
    assertEquals("processing-failure/folder-not-empty", ((ApiErrorException) notEmpty.getCause()).getType());
    api.verify(postRequestedFor(urlPathEqualTo(API + "/file_actions/delete/a")).withRequestBody(matchingJsonPath("$.recursive", equalTo("false"))));

    api.stubFor(post(urlPathEqualTo(API + "/file_actions/delete/old")).willReturn(created("{\"status\":\"pending\",\"file_migration_id\":21}")));
    api.stubFor(get(urlPathEqualTo(API + "/file_migrations/21")).willReturn(okJson("{\"id\":21,\"operation\":\"delete\",\"status\":\"completed\"}")));
    api.stubFor(stat("old").willReturn(apiError(404, "not-found/file-not-found")));
    Files.delete(files.getPath("/old"));
    api.verify(1, getRequestedFor(urlPathEqualTo(API + "/file_migrations/21")));

    // A skipped delete is not a deletion: the folder is still there.
    api.stubFor(post(urlPathEqualTo(API + "/file_actions/delete/kept")).willReturn(created("{\"status\":\"skipped\"}")));
    api.stubFor(stat("kept").willReturn(okJson(folderJson("kept"))));
    FileSystemException kept = assertThrows(FileSystemException.class, () -> Files.delete(files.getPath("/kept")));
    assertEquals(FileSystemException.class, kept.getClass());

    // The request that starts a delete names a path the API cannot resolve: the file is missing, and
    // nothing is waited for or checked after that request.
    api.stubFor(post(urlPathEqualTo(API + "/file_actions/delete/absent")).willReturn(apiError(404, "not-found")));
    api.resetRequests();
    NoSuchFileException absent = assertThrows(NoSuchFileException.class, () -> Files.delete(files.getPath("/absent")));
    assertEquals("/absent", absent.getFile());
    assertEquals("not-found", ((ApiErrorException) absent.getCause()).getType());
    assertFalse(Files.deleteIfExists(files.getPath("/absent")));
    api.verify(2, postRequestedFor(urlPathEqualTo(API + "/file_actions/delete/absent")));
    api.verify(2, anyRequestedFor(anyUrl()));

    // Only with the code 404 in its body: the same type with another code, with none, or a bare 404
    // stays a failure.
    api.stubFor(post(urlPathEqualTo(API + "/file_actions/delete/coded")).willReturn(aResponse().withStatus(404)
        .withHeader("Content-Type", "application/json")
        .withBody("{\"type\":\"not-found\",\"http-code\":400,\"title\":\"not-found\",\"error\":\"not-found\"}")));
    api.stubFor(post(urlPathEqualTo(API + "/file_actions/delete/uncoded")).willReturn(aResponse().withStatus(404)
        .withHeader("Content-Type", "application/json").withBody("{\"type\":\"not-found\",\"title\":\"not-found\",\"error\":\"not-found\"}")));
    api.stubFor(post(urlPathEqualTo(API + "/file_actions/delete/bare")).willReturn(aResponse().withStatus(404)
        .withHeader("Content-Type", "text/html").withBody("<html>Not Found</html>")));
    for (String name : new String[]{"coded", "uncoded", "bare"}) {
      FileSystemException failure = assertThrows(name, FileSystemException.class, () -> Files.deleteIfExists(files.getPath("/" + name)));
      assertEquals(name, FileSystemException.class, failure.getClass());
      assertTrue(name, failure.getCause() instanceof SdkException);
    }

    // A not-found while the delete is waited for is not the file missing: the delete failed.
    api.stubFor(post(urlPathEqualTo(API + "/file_actions/delete/moving")).willReturn(created("{\"status\":\"pending\",\"file_migration_id\":22}")));
    api.stubFor(get(urlPathEqualTo(API + "/file_migrations/22")).willReturn(apiError(404, "not-found")));
    FileSystemException lost = assertThrows(FileSystemException.class, () -> Files.delete(files.getPath("/moving")));
    assertEquals(FileSystemException.class, lost.getClass());
    FileSystemException lostAgain = assertThrows(FileSystemException.class, () -> Files.deleteIfExists(files.getPath("/moving")));
    assertEquals(FileSystemException.class, lostAgain.getClass());
    assertFalse(api.findAll(getRequestedFor(urlPathEqualTo(API + "/file_migrations/22"))).isEmpty());
  }

  @Test
  public void copiesAndMovesWaitForTheApiAndAFolderCopyIsAnEmptyFolder() throws Exception {
    api.stubFor(stat("src.bin").willReturn(okJson(fileJson("src.bin", 4))));
    api.stubFor(stat("dst.bin").inScenario("copy").whenScenarioStateIs(Scenario.STARTED)
        .willReturn(apiError(404, "not-found/file-not-found")));
    api.stubFor(stat("dst.bin").inScenario("copy").whenScenarioStateIs("copied").willReturn(okJson(fileJson("dst.bin", 4))));
    api.stubFor(post(urlPathEqualTo(API + "/file_actions/copy/src.bin")).inScenario("copy").whenScenarioStateIs(Scenario.STARTED)
        .willSetStateTo("copied").willReturn(created("{\"status\":\"pending\",\"file_migration_id\":31}")));
    api.stubFor(get(urlPathEqualTo(API + "/file_migrations/31")).willReturn(okJson("{\"id\":31,\"operation\":\"copy\",\"status\":\"completed\"}")));

    Files.copy(files.getPath("/src.bin"), files.getPath("/dst.bin"));

    api.verify(1, postRequestedFor(urlPathEqualTo(API + "/file_actions/copy/src.bin"))
        .withRequestBody(matchingJsonPath("$.destination", equalTo("dst.bin")))
        .withRequestBody(matchingJsonPath("$.overwrite", equalTo("false"))));
    api.verify(1, getRequestedFor(urlPathEqualTo(API + "/file_migrations/31")));

    api.stubFor(stat("reports").willReturn(okJson(folderJson("reports"))));
    api.stubFor(stat("reports-copy").willReturn(apiError(404, "not-found/file-not-found")));
    api.stubFor(post(urlPathEqualTo(API + "/folders/reports-copy")).willReturn(created(folderJson("reports-copy"))));
    Files.copy(files.getPath("/reports"), files.getPath("/reports-copy"));
    api.verify(0, postRequestedFor(urlPathEqualTo(API + "/file_actions/copy/reports")));
    api.verify(1, postRequestedFor(urlPathEqualTo(API + "/folders/reports-copy")));

    Path a = files.getPath("/a.bin");
    Path b = files.getPath("/b.bin");
    api.stubFor(stat("a.bin").willReturn(okJson(fileJson("a.bin", 1))));
    api.stubFor(stat("b.bin").willReturn(okJson(fileJson("b.bin", 2))));
    api.stubFor(post(urlPathEqualTo(API + "/file_actions/move/a.bin")).willReturn(created("{\"status\":\"completed\"}")));
    assertThrows(FileAlreadyExistsException.class, () -> Files.move(a, b));
    api.verify(0, postRequestedFor(urlPathEqualTo(API + "/file_actions/move/a.bin")));
    Files.move(a, b, StandardCopyOption.REPLACE_EXISTING);
    api.verify(1, postRequestedFor(urlPathEqualTo(API + "/file_actions/move/a.bin"))
        .withRequestBody(matchingJsonPath("$.destination", equalTo("b.bin")))
        .withRequestBody(matchingJsonPath("$.overwrite", equalTo("true"))));

    // A copy or move the API skips did no work: the target already there is not taken as replaced.
    api.stubFor(post(urlPathEqualTo(API + "/file_actions/copy/a.bin")).willReturn(created("{\"status\":\"skipped\"}")));
    FileSystemException skippedCopy = assertThrows(FileSystemException.class, () -> Files.copy(a, b, StandardCopyOption.REPLACE_EXISTING));
    assertEquals(FileSystemException.class, skippedCopy.getClass());
    api.stubFor(post(urlPathEqualTo(API + "/file_actions/move/a.bin")).willReturn(created("{\"status\":\"skipped\"}")));
    FileSystemException skippedMove = assertThrows(FileSystemException.class, () -> Files.move(a, b, StandardCopyOption.REPLACE_EXISTING));
    assertEquals(FileSystemException.class, skippedMove.getClass());

    // A copy or move to another spelling of the same file, as Files.com compares paths, changes
    // nothing, even replacing: nothing is deleted, copied or moved.
    api.stubFor(stat("Reports").willReturn(okJson(folderJson("Reports"))));
    Files.move(files.getPath("/Reports"), files.getPath("/reports"), StandardCopyOption.REPLACE_EXISTING);
    Files.copy(files.getPath("/src.bin"), files.getPath("/SRC.BIN"), StandardCopyOption.REPLACE_EXISTING);
    api.verify(0, postRequestedFor(urlPathEqualTo(API + "/file_actions/delete/reports")));
    api.verify(0, postRequestedFor(urlPathEqualTo(API + "/file_actions/move/Reports")));
    api.verify(1, postRequestedFor(urlPathEqualTo(API + "/file_actions/copy/src.bin")));

    api.stubFor(stat("folder").willReturn(okJson(folderJson("folder"))));
    assertNotImplemented(assertThrows(UnsupportedOperationException.class,
        () -> Files.move(files.getPath("/folder"), b, StandardCopyOption.REPLACE_EXISTING)));
    api.verify(0, postRequestedFor(urlPathEqualTo(API + "/file_actions/move/folder")));
  }

  @Test
  public void onlyTheFileNotFoundTypeIsAMissingFileAndEverySdkCauseIsKept() throws Exception {
    api.stubFor(stat("gone.txt").willReturn(apiError(404, "not-found/file-not-found")));
    NoSuchFileException gone = assertThrows(NoSuchFileException.class, () -> Files.readAttributes(files.getPath("/gone.txt"), BasicFileAttributes.class));
    assertEquals("/gone.txt", gone.getFile());
    assertEquals("not-found/file-not-found", ((ApiErrorException) gone.getCause()).getType());
    assertTrue(Files.notExists(files.getPath("/gone.txt")));

    api.stubFor(stat("site.txt").willReturn(apiError(404, "not-found/site-not-found")));
    api.stubFor(stat("bare.txt").willReturn(aResponse().withStatus(404).withHeader("Content-Type", "text/html").withBody("<html>Not Found</html>")));
    api.stubFor(stat("login.txt").willReturn(apiError(401, "not-authenticated/invalid-api-key")));
    api.stubFor(stat("net.txt").willReturn(aResponse().withFault(Fault.CONNECTION_RESET_BY_PEER)));
    for (String name : new String[]{"site.txt", "bare.txt", "login.txt", "net.txt"}) {
      Path path = files.getPath("/" + name);
      FileSystemException failure = assertThrows(name, FileSystemException.class, () -> Files.readAttributes(path, BasicFileAttributes.class));
      assertEquals(name + " is neither missing nor denied", FileSystemException.class, failure.getClass());
      assertTrue(name, failure.getCause() instanceof SdkException);
      assertFalse(name, Files.notExists(path));
    }

    api.stubFor(stat("secret.txt").willReturn(apiError(403, "not-authorized/read-permission-required")));
    AccessDeniedException denied = assertThrows(AccessDeniedException.class, () -> Files.size(files.getPath("/secret.txt")));
    assertTrue(denied.getCause() instanceof ApiErrorException);

    api.stubFor(post(urlPathEqualTo(API + "/file_actions/delete/gone.txt")).willReturn(apiError(404, "not-found/file-not-found")));
    assertFalse(Files.deleteIfExists(files.getPath("/gone.txt")));
    api.stubFor(post(urlPathEqualTo(API + "/file_actions/delete/site.txt")).willReturn(apiError(404, "not-found/site-not-found")));
    assertThrows(FileSystemException.class, () -> Files.deleteIfExists(files.getPath("/site.txt")));
  }

  @Test
  public void aFailedPublicationKeepsTheCopyIsThrownOnceAndAnUnfinishedActionIsNotTakenAsDone() throws Exception {
    remoteFile("draft.txt", "old".getBytes(StandardCharsets.UTF_8), true);
    api.stubFor(post(urlPathEqualTo(API + "/file_actions/begin_upload/draft.txt"))
        .willReturn(apiError(403, "not-authorized/write-permission-required")));
    SeekableByteChannel channel = Files.newByteChannel(files.getPath("/draft.txt"), StandardOpenOption.WRITE);
    channel.write(ByteBuffer.wrap("new".getBytes(StandardCharsets.UTF_8)));

    AccessDeniedException failure = assertThrows(AccessDeniedException.class, channel::close);
    assertTrue(failure.getCause() instanceof ApiErrorException);
    assertFalse(channel.isOpen());
    Path kept = Paths.get(failure.getOtherFile());
    assertEquals(stage, kept.getParent());
    assertTrue("the written bytes are kept for recovery", contents(kept).contains("new"));
    int attempts = api.findAll(postRequestedFor(urlPathEqualTo(API + "/file_actions/begin_upload/draft.txt"))).size();
    channel.close();
    assertEquals("closing again publishes nothing", attempts,
        api.findAll(postRequestedFor(urlPathEqualTo(API + "/file_actions/begin_upload/draft.txt"))).size());

    acceptUploads("draft.txt");
    try (SeekableByteChannel next = Files.newByteChannel(files.getPath("/draft.txt"), StandardOpenOption.WRITE)) {
      next.write(ByteBuffer.wrap("NEW".getBytes(StandardCharsets.UTF_8)));
    }
    assertEquals("a failed writer does not hold the file", "NEW", new String(lastUpload("draft.txt"), StandardCharsets.UTF_8));

    api.stubFor(post(urlPathEqualTo(API + "/file_actions/delete/limbo.txt")).willReturn(created("{\"status\":\"queued\"}")));
    FileSystemException unfinished = assertThrows(FileSystemException.class, () -> Files.delete(files.getPath("/limbo.txt")));
    assertEquals(FileSystemException.class, unfinished.getClass());
    assertTrue(unfinished.getCause() instanceof IllegalStateException);
    api.verify(0, getRequestedFor(urlPathEqualTo(API + "/files/limbo.txt")));
  }

  @Test
  public void copiesAreBoundedByTheStageQuotaAndDeletedWhenTheirChannelsClose() throws Exception {
    api.stubFor(download("big.bin").willReturn(okJson(downloadJson("big.bin", 2048))));
    FileSystemException tooBig = assertThrows(FileSystemException.class, () -> Files.newInputStream(files.getPath("/big.bin")));
    assertTrue(tooBig.getReason(), tooBig.getReason().contains("quota"));
    storage.verify(0, anyRequestedFor(anyUrl()));

    remoteFile("half.bin", new byte[600], true);
    try (InputStream first = Files.newInputStream(files.getPath("/half.bin"))) {
      assertThrows("the two copies would not fit at once", FileSystemException.class, () -> Files.newInputStream(files.getPath("/half.bin")));
      assertEquals(1, listing(stage).size());
      assertEquals(600, first.skip(600));
    }
    assertEquals(Collections.emptyList(), listing(stage));
    try (InputStream again = Files.newInputStream(files.getPath("/half.bin"))) {
      assertEquals("the closed copy's quota is free again", 0, again.read());
    }

    // Storage holds more than Files.com described: the download fails before writing past it.
    api.stubFor(download("liar.bin").willReturn(okJson(downloadJson("liar.bin", 4))));
    storage.stubFor(get(urlPathEqualTo("/storage/liar.bin")).willReturn(aResponse().withStatus(200)
        .withHeader("Content-Length", "8").withBody(new byte[8])));
    FileSystemException mismatch = assertThrows(FileSystemException.class, () -> Files.readAllBytes(files.getPath("/liar.bin")));
    assertTrue(mismatch.getCause() instanceof ApiErrorException.ApiConnectionException);
    assertTrue(mismatch.getCause().getMessage(), mismatch.getCause().getMessage().contains("described it with 4"));
    storage.verify(1, getRequestedFor(urlPathEqualTo("/storage/liar.bin")));
    assertEquals(Collections.emptyList(), listing(stage));

    // The same, in chunks of no declared length: refused before a byte past the described size is
    // written, and not tried again.
    api.stubFor(download("chunked.bin").willReturn(okJson(downloadJson("chunked.bin", 4))));
    storage.stubFor(get(urlPathEqualTo("/storage/chunked.bin")).willReturn(aResponse().withStatus(200).withBody(new byte[8])
        .withChunkedDribbleDelay(2, 20)));
    FileSystemException oversized = assertThrows(FileSystemException.class, () -> Files.readAllBytes(files.getPath("/chunked.bin")));
    assertTrue(String.valueOf(oversized.getCause()), oversized.getCause().getMessage().contains("more than the 4"));
    storage.verify(1, getRequestedFor(urlPathEqualTo("/storage/chunked.bin")));
    assertEquals(Collections.emptyList(), listing(stage));
  }

  @Test
  public void theSamePathOnTwoClientsStaysApartAndEachUsesItsOwnClient() throws Exception {
    api.stubFor(stat("shared.txt").willReturn(okJson(fileJson("shared.txt", 3))));
    FilesClient other = FilesClient.builder().apiKey("other-key").apiRoot(apiRoot()).build();
    try {
      FileSystem otherFiles = FilesComFileSystems.builder(other).stageDirectory(stage).stageQuotaBytes(1024).build();
      Path mine = files.getPath("/shared.txt");
      Path theirs = otherFiles.getPath("/shared.txt");
      assertNotEquals(mine, theirs);
      assertFalse(Files.isSameFile(mine, theirs));
      assertNotEquals("ordered apart, as they are not equal", 0, mine.compareTo(theirs));
      assertEquals(-Integer.signum(mine.compareTo(theirs)), Integer.signum(theirs.compareTo(mine)));
      assertEquals(2, new TreeSet<Path>(Arrays.asList(mine, theirs)).size());
      assertEquals(0, mine.compareTo(files.getPath("/shared.txt")));
      assertNotEquals(mine.toUri(), theirs.toUri());
      assertFalse(mine.toUri().toString().contains("nio-key"));

      assertEquals(3, Files.size(mine));
      assertEquals(3, Files.size(theirs));
      api.verify(1, getRequestedFor(urlPathEqualTo(API + "/files/shared.txt")).withHeader("X-FilesAPI-Key", equalTo("nio-key")));
      api.verify(1, getRequestedFor(urlPathEqualTo(API + "/files/shared.txt")).withHeader("X-FilesAPI-Key", equalTo("other-key")));
      assertNotImplemented(assertThrows(UnsupportedOperationException.class, () -> Files.copy(mine, theirs)));

      otherFiles.close();
      assertThrows(ClosedFileSystemException.class, () -> Files.size(theirs));
      HashMap<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("action", "stat");
      File described = other.files().download("shared.txt", parameters, null);
      assertEquals("the closed file system's client still works", Long.valueOf(3), described.getSize());
      assertEquals("and so does this one", 3, Files.size(mine));
    } finally {
      other.close();
    }
  }

  @Test
  public void closingTheFileSystemClosesItsHandlesOnceAndPublishesButLeavesTheClientOpen() throws Exception {
    remoteFile("open.txt", "abc".getBytes(StandardCharsets.UTF_8), true);
    acceptUploads("open.txt");
    api.stubFor(stat("dir").willReturn(okJson(folderJson("dir"))));
    api.stubFor(get(urlPathEqualTo(API + "/folders/dir")).willReturn(okJson("[]")));
    SeekableByteChannel writer = Files.newByteChannel(files.getPath("/open.txt"), StandardOpenOption.WRITE);
    writer.write(ByteBuffer.wrap("x".getBytes(StandardCharsets.UTF_8)));
    DirectoryStream<Path> entries = Files.newDirectoryStream(files.getPath("/dir"));

    files.close();

    assertFalse(writer.isOpen());
    assertEquals("xbc", new String(lastUpload("open.txt"), StandardCharsets.UTF_8));
    assertThrows(IllegalStateException.class, entries::iterator);
    assertThrows(ClosedFileSystemException.class, () -> Files.size(files.getPath("/open.txt")));
    files.close();
    assertEquals(1, uploadsEnded("open.txt"));
    assertEquals(Collections.emptyList(), listing(stage));
    HashMap<String, Object> parameters = new HashMap<String, Object>();
    parameters.put("action", "stat");
    assertEquals("open.txt", client.files().download("open.txt", parameters, null).getPath());
  }

  @Test
  public void aWriterPublishingWhenTheFileSystemClosesFinishesItsOnePublicationFirst() throws Exception {
    remoteFile("late.txt", "abc".getBytes(StandardCharsets.UTF_8), true);
    acceptUploads("late.txt");
    SeekableByteChannel writer = Files.newByteChannel(files.getPath("/late.txt"), StandardOpenOption.WRITE);
    writer.write(ByteBuffer.wrap("xyz".getBytes(StandardCharsets.UTF_8)));
    ExecutorService closers = Executors.newFixedThreadPool(2);
    try (HeldService completion = new HeldService(201, fileJson("late.txt", 3))) {
      // The upload's completion is held where it is received, until released: the writer's close is
      // publishing while the file system closes. A completion answered before its release, such as
      // by a proxy timeout, fails the writer's close, and so this test.
      api.stubFor(post(urlPathEqualTo(API + "/files/late.txt")).willReturn(aResponse().proxiedFrom(completion.url())));
      Future<?> closed = closers.submit(() -> {
        writer.close();
        return null;
      });
      assertTrue("the completion is received, and held", completion.awaitEntered(10, TimeUnit.SECONDS));

      Future<?> fileSystemClosed = closers.submit(() -> {
        files.close();
        return null;
      });
      long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(10);
      while (files.isOpen() && System.nanoTime() < deadline) {
        Thread.sleep(10);
      }
      assertFalse("the file system's close has started", files.isOpen());
      assertFalse("and waits for the held publication", fileSystemClosed.isDone());
      assertFalse(closed.isDone());

      completion.release();
      closed.get(10, TimeUnit.SECONDS);
      fileSystemClosed.get(10, TimeUnit.SECONDS);
      assertEquals("one completion, answered once released", Collections.singletonList("POST " + API + "/files/late.txt"),
          completion.requests());
    } finally {
      closers.shutdownNow();
      closers.awaitTermination(10, TimeUnit.SECONDS);
    }
    assertFalse(writer.isOpen());
    assertEquals("published once, not cancelled", 1, uploadsEnded("late.txt"));
    assertEquals("xyz", new String(lastUpload("late.txt"), StandardCharsets.UTF_8));
    assertEquals(Collections.emptyList(), listing(stage));
    HashMap<String, Object> parameters = new HashMap<String, Object>();
    parameters.put("action", "stat");
    assertEquals("the borrowed client stays open", "late.txt", client.files().download("late.txt", parameters, null).getPath());
  }

  // A service that receives each request as the API would, reading it whole, and holds its answer,
  // a status with a JSON body, until released. It listens on loopback only, as the fixtures do.
  private static final class HeldService implements AutoCloseable {
    private final HttpServer server;
    private final ExecutorService handlers = Executors.newCachedThreadPool();
    private final CountDownLatch entered = new CountDownLatch(1);
    private final CountDownLatch released = new CountDownLatch(1);
    private final List<String> requests = Collections.synchronizedList(new ArrayList<String>());

    HeldService(int status, String json) throws IOException {
      byte[] body = json.getBytes(StandardCharsets.UTF_8);
      server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
      server.setExecutor(handlers);
      server.createContext("/", exchange -> {
        try {
          InputStream request = exchange.getRequestBody();
          byte[] chunk = new byte[8192];
          while (request.read(chunk) >= 0) {
            // Read whole, as the API reads a request before answering it.
          }
          requests.add(exchange.getRequestMethod() + " " + exchange.getRequestURI().getPath());
          entered.countDown();
          released.await();
          exchange.getResponseHeaders().set("Content-Type", "application/json");
          exchange.sendResponseHeaders(status, body.length);
          exchange.getResponseBody().write(body);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        } finally {
          exchange.close();
        }
      });
      server.start();
    }

    String url() {
      return "http://127.0.0.1:" + server.getAddress().getPort();
    }

    boolean awaitEntered(long timeout, TimeUnit unit) throws InterruptedException {
      return entered.await(timeout, unit);
    }

    void release() {
      released.countDown();
    }

    List<String> requests() {
      synchronized (requests) {
        return new ArrayList<String>(requests);
      }
    }

    // Releases what is held, then stops the service and its handlers, within a bound.
    @Override
    public void close() {
      released.countDown();
      server.stop(0);
      handlers.shutdownNow();
      try {
        assertTrue("held service handlers stop", handlers.awaitTermination(10, TimeUnit.SECONDS));
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new AssertionError("Interrupted while stopping the held service", e);
      }
    }
  }

  @Test
  public void attributesComeFromTheApiAndTheModifiedTimeIsSetAsProvidedMtime() throws Exception {
    api.stubFor(stat("dated.txt").willReturn(okJson("{\"path\":\"dated.txt\",\"type\":\"file\",\"size\":5,"
        + "\"mtime\":\"2024-01-02T03:04:05Z\",\"provided_mtime\":\"2020-01-01T00:00:00Z\",\"created_at\":\"2019-06-01T00:00:00Z\","
        + "\"permissions\":\"rwd\"}")));
    Path path = files.getPath("/dated.txt");

    BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
    assertEquals(FileTime.from(Instant.parse("2020-01-01T00:00:00Z")), attributes.lastModifiedTime());
    assertEquals(attributes.lastModifiedTime(), attributes.lastAccessTime());
    assertEquals(FileTime.from(Instant.parse("2019-06-01T00:00:00Z")), attributes.creationTime());
    assertTrue(attributes.isRegularFile());
    assertEquals(5, attributes.size());
    assertNull(attributes.fileKey());
    assertEquals("rwd", Files.getAttribute(path, "files:permissions"));
    assertTrue(Files.isReadable(path));
    assertTrue(Files.isWritable(path));
    assertFalse(Files.isExecutable(path));

    api.stubFor(patch(urlPathEqualTo(API + "/files/dated.txt")).willReturn(okJson(fileJson("dated.txt", 5))));
    Files.setLastModifiedTime(path, FileTime.from(Instant.parse("2021-05-06T07:08:09Z")));
    assertNotImplemented(assertThrows(UnsupportedOperationException.class,
        () -> Files.getFileAttributeView(path, BasicFileAttributeView.class).setTimes(FileTime.fromMillis(0), FileTime.fromMillis(0), null)));
    Files.getFileAttributeView(path, BasicFileAttributeView.class).setTimes(null, null, null);
    api.verify(1, patchRequestedFor(urlPathEqualTo(API + "/files/dated.txt"))
        .withRequestBody(matchingJsonPath("$.provided_mtime", equalTo("2021-05-06T07:08:09Z"))));
  }

  @Test
  public void pathsFollowTheSameLexicalRulesOnEveryHost() throws Exception {
    Path path = files.getPath("/a//b/", "c.txt");
    assertEquals("/a/b/c.txt", path.toString());
    assertEquals(3, path.getNameCount());
    assertEquals("c.txt", path.getFileName().toString());
    assertEquals("/a/b", path.getParent().toString());
    assertEquals("/", path.getRoot().toString());
    assertEquals(files.getPath("/a/x"), files.getPath("/a/b/../x/.").normalize());
    assertEquals(files.getPath("../c"), files.getPath("/a/b").relativize(files.getPath("/a/c")));
    assertEquals(files.getPath("/a/b/c"), files.getPath("/a/b").resolve("c"));
    assertEquals(files.getPath("/a/c"), files.getPath("/a/b").resolveSibling("c"));
    assertTrue(path.startsWith("/a/b"));
    assertTrue(path.endsWith("b/c.txt"));
    assertEquals("a\\b", files.getPath("a\\b").getFileName().toString());
    assertNotEquals("case is kept", files.getPath("/A.txt"), files.getPath("/a.txt"));
    assertThrows(InvalidPathException.class, () -> files.getPath("a\u0000b"));
    assertThrows(UnsupportedOperationException.class, () -> path.toFile());

    try (FileSystem working = FilesComFileSystems.builder(client).workingDirectory("/work").stageDirectory(stage).stageQuotaBytes(1).build()) {
      assertEquals(working.getPath("/work/r.txt"), working.getPath("r.txt").toAbsolutePath());
      assertNotEquals("paths of two file systems are never equal", files.getPath("/work/r.txt"), working.getPath("/work/r.txt"));
    }

    URI uri = path.toUri();
    assertEquals("files-com", uri.getScheme());
    assertEquals("/a/b/c.txt", uri.getPath());
    assertFalse(uri.toString().contains("nio-key"));

    PathMatcher typed = files.getPathMatcher("glob:/a/**/*.{txt,csv}");
    assertTrue(typed.matches(path));
    assertFalse(typed.matches(files.getPath("/a/c.bin")));
    assertTrue(files.getPathMatcher("glob:*.txt").matches(files.getPath("c.txt")));
    assertFalse("* does not cross a /", files.getPathMatcher("glob:*.txt").matches(files.getPath("b/c.txt")));
    assertTrue(files.getPathMatcher("glob:[!x]?.[a-z]*").matches(files.getPath("ab.txt")));
    assertTrue(files.getPathMatcher("regex:/a/[^/]+/c\\.txt").matches(path));
    assertNotImplemented(assertThrows(UnsupportedOperationException.class, () -> files.getPathMatcher("xml:anything")));
    api.verify(0, anyRequestedFor(anyUrl()));
  }

  private String apiRoot() {
    return "http://127.0.0.1:" + api.port();
  }

  private static MappingBuilder stat(String path) {
    return get(urlPathEqualTo(API + "/files/" + path)).withQueryParam("action", equalTo("stat"));
  }

  private static MappingBuilder download(String path) {
    return get(urlPathEqualTo(API + "/files/" + path)).withQueryParam("action", absent());
  }

  // The remote file: described, with a download URL, and served by storage; and, if described is
  // set, also described by stat.
  private void remoteFile(String path, byte[] contents, boolean described) {
    if (described) {
      api.stubFor(stat(path).willReturn(okJson(fileJson(path, contents.length))));
    }
    api.stubFor(download(path).willReturn(okJson(downloadJson(path, contents.length))));
    storage.stubFor(get(urlPathEqualTo("/storage/" + path)).willReturn(aResponse().withStatus(200).withBody(contents)));
  }

  // Uploads of path are accepted: begun, sent to storage in one part, and completed.
  private void acceptUploads(String path) {
    api.stubFor(post(urlPathEqualTo(API + "/file_actions/begin_upload/" + path)).willReturn(okJson("[{\"action\":\"put/write\","
        + "\"ask_about_overwrites\":false,\"available_parts\":10000,\"expires\":\"2099-01-01T00:00:00Z\",\"headers\":{},"
        + "\"http_method\":\"PUT\",\"next_partsize\":5242880,\"parallel_parts\":true,\"retry_parts\":true,\"parameters\":{},"
        + "\"part_number\":1,\"partsize\":5242880,\"path\":\"" + path + "\",\"ref\":\"put-" + path + "\","
        + "\"upload_uri\":\"http://127.0.0.1:" + storage.port() + "/upload/" + path + "\"}]")));
    storage.stubFor(put(urlPathEqualTo("/upload/" + path)).willReturn(aResponse().withStatus(200).withHeader("ETag", "\"part-1\"")));
    api.stubFor(post(urlPathEqualTo(API + "/files/" + path)).willReturn(created(fileJson(path, 0))));
  }

  // The uploads of path that were completed.
  private int uploadsEnded(String path) {
    return api.findAll(postRequestedFor(urlPathEqualTo(API + "/files/" + path))).size();
  }

  private byte[] lastUpload(String path) {
    List<LoggedRequest> parts = storage.findAll(putRequestedFor(urlPathEqualTo("/upload/" + path)));
    return parts.get(parts.size() - 1).getBody();
  }

  private static ResponseDefinitionBuilder apiError(int status, String type) {
    return aResponse().withStatus(status).withHeader("Content-Type", "application/json")
        .withBody("{\"type\":\"" + type + "\",\"http-code\":" + status + ",\"title\":\"" + type + "\",\"error\":\"" + type + "\"}");
  }

  private static ResponseDefinitionBuilder created(String json) {
    return aResponse().withStatus(201).withHeader("Content-Type", "application/json").withBody(json);
  }

  private static String fileJson(String path, long size) {
    return "{\"path\":\"" + path + "\",\"type\":\"file\",\"size\":" + size + "}";
  }

  private String downloadJson(String path, long size) {
    return "{\"path\":\"" + path + "\",\"type\":\"file\",\"size\":" + size + ",\"download_uri\":\"http://127.0.0.1:" + storage.port()
        + "/storage/" + path + "\"}";
  }

  private static String folderJson(String path) {
    return "{\"path\":\"" + path + "\",\"type\":\"directory\"}";
  }

  private static Set<OpenOption> options(OpenOption... options) {
    return new HashSet<OpenOption>(Arrays.asList(options));
  }

  private static void assertNotImplemented(UnsupportedOperationException e) {
    assertTrue(String.valueOf(e.getCause()), e.getCause() instanceof NotImplementedException);
    assertEquals(e.getMessage(), e.getCause().getMessage());
  }

  private static List<String> listing(Path directory) throws IOException {
    try (Stream<Path> entries = Files.list(directory)) {
      return entries.map(p -> p.getFileName().toString()).sorted().collect(Collectors.toList());
    }
  }

  // The contents of the regular files in directory, as text.
  private static String contents(Path directory) throws IOException {
    StringBuilder text = new StringBuilder();
    try (Stream<Path> entries = Files.list(directory)) {
      for (Path entry : entries.collect(Collectors.toList())) {
        if (Files.isRegularFile(entry)) {
          text.append(new String(Files.readAllBytes(entry), StandardCharsets.UTF_8));
        }
      }
    }
    return text.toString();
  }
}
