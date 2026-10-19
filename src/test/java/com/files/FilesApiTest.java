package com.files;

import static com.files.TestServer.request;
import static com.files.TestServer.response;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.absent;
import static com.github.tomakehurst.wiremock.client.WireMock.anyRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.binaryEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.deleteRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
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
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.files.exceptions.ApiErrorException;
import com.files.exceptions.SdkException;
import com.files.models.Automation;
import com.files.models.Bundle;
import com.files.models.File;
import com.files.models.FileAction;
import com.files.models.FileMigration;
import com.files.models.Folder;
import com.files.models.Group;
import com.files.models.Invoice;
import com.files.models.MetadataCategory;
import com.files.models.Permission;
import com.files.models.RemoteMountBackend;
import com.files.models.Style;
import com.files.models.User;
import com.files.models.Workspace;
import com.files.net.ClientRuntime;
import com.files.net.FilesHttpClient;
import com.files.net.FilesHttpExecutor;
import com.files.net.HttpMethods;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.http.QueryParameter;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.TimeZone;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;
import org.apache.http.Header;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.ProtocolException;
import org.apache.http.client.CircularRedirectException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.RedirectException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.UnsupportedSchemeException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FilesApiTest {
  private static final String[] UPLOAD_RESPONSE_BODIES = {"", "{}"};
  private static final byte[] UPLOAD_BYTES = new byte[20000];

  static {
    for (int i = 0; i < UPLOAD_BYTES.length; i++) {
      UPLOAD_BYTES[i] = (byte) i;
    }
  }

  private WireMockServer wireMockServer;
  // The WireMock servers test.sh runs in their own process, for the tests from the 1.x public
  // suite; the second is another origin, for per-request API roots and cross-origin redirects.
  private final TestServer server = TestServer.fromEnvironment("FILES_TEST_SERVER_URL");
  private final TestServer secondOrigin = TestServer.fromEnvironment("FILES_TEST_STORAGE_SERVER_URL");

  @Before
  public void setUp() throws IOException {
    server.reset();
    secondOrigin.reset();
    wireMockServer = loopbackServer();
    wireMockServer.start();

    String baseUrl = "http://localhost:" + wireMockServer.port();
    FilesClient.setProperty("apiRoot", baseUrl);
    FilesClient.apiKey = "test-key";
  }

  // The fixtures listen on loopback only and return stub bodies as written; no test uses WireMock's
  // response templates.
  static WireMockServer loopbackServer() {
    return new WireMockServer(WireMockConfiguration.options()
        .bindAddress("127.0.0.1")
        .dynamicPort()
        .templatingEnabled(false));
  }

  @After
  public void tearDown() throws IOException {
    FilesClient.workspaceId = null;
    wireMockServer.stop();
  }

  @Test
  public void deletesReturnedPathsContainingColons() throws IOException {
    wireMockServer.stubFor(get(urlEqualTo("/api/rest/v1/folders/%2F"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("[{\"path\":\"colon:name.txt\"},{\"path\":\"colon:folder/child.txt\"}]")));
    wireMockServer.stubFor(delete(urlEqualTo("/api/rest/v1/files/colon%3Aname.txt"))
        .willReturn(aResponse().withStatus(204)));
    wireMockServer.stubFor(delete(urlEqualTo("/api/rest/v1/files/colon%3Afolder%2Fchild.txt"))
        .willReturn(aResponse().withStatus(204)));

    List<File> files = Folder.listFor("/", null).all();
    assertEquals(2, files.size());
    for (File file : files) {
      file.delete(null);
    }

    wireMockServer.verify(1, deleteRequestedFor(urlEqualTo("/api/rest/v1/files/colon%3Aname.txt")));
    wireMockServer.verify(1, deleteRequestedFor(urlEqualTo("/api/rest/v1/files/colon%3Afolder%2Fchild.txt")));
  }

  @Test
  public void usesConfiguredWorkspaceId() throws Exception {
    FilesClient.workspaceId = 123L;

    wireMockServer.stubFor(get(urlEqualTo("/api/rest/v1/folders/%2F"))
        .willReturn(aResponse()
            .withStatus(200)
            .withBody("[]")));

    Folder.listFor("/", null).all();

    wireMockServer.verify(getRequestedFor(urlEqualTo("/api/rest/v1/folders/%2F"))
        .withHeader("X-Files-Workspace-Id", equalTo("123")));
  }

  @Test
  public void usesPerCallWorkspaceId() throws Exception {
    FilesClient.workspaceId = 123L;

    wireMockServer.stubFor(get(urlEqualTo("/api/rest/v1/folders/%2F"))
        .willReturn(aResponse()
            .withStatus(200)
            .withBody("[]")));

    HashMap<String, Object> options = new HashMap<>();
    options.put("workspace_id", 456L);

    Folder.listFor("/", null, options).all();

    wireMockServer.verify(getRequestedFor(urlEqualTo("/api/rest/v1/folders/%2F"))
        .withHeader("X-Files-Workspace-Id", equalTo("456")));
  }

  @Test
  public void allowsPerCallWorkspaceIdToBeCleared() throws Exception {
    FilesClient.workspaceId = 123L;

    wireMockServer.stubFor(get(urlEqualTo("/api/rest/v1/folders/%2F"))
        .willReturn(aResponse()
            .withStatus(200)
            .withBody("[]")));

    HashMap<String, Object> options = new HashMap<>();
    options.put("workspace_id", null);

    Folder.listFor("/", null, options).all();

    wireMockServer.verify(getRequestedFor(urlEqualTo("/api/rest/v1/folders/%2F"))
        .withoutHeader("X-Files-Workspace-Id"));
  }

  @Test
  public void stripsFilesAuthHeadersOnCrossOriginRedirects() throws Exception {
    WireMockServer storageServer = loopbackServer();
    storageServer.start();
    try {
      FilesClient.workspaceId = 123L;
      wireMockServer.stubFor(get(urlEqualTo("/api/rest/v1/redirect"))
          .willReturn(aResponse()
              .withStatus(302)
              .withHeader("Location", "http://localhost:" + storageServer.port() + "/download")));
      storageServer.stubFor(get(urlEqualTo("/download"))
          .willReturn(aResponse()
              .withStatus(302)
              .withHeader("Location", "http://localhost:" + wireMockServer.port() + "/returned")));
      wireMockServer.stubFor(get(urlEqualTo("/returned"))
          .willReturn(aResponse().withStatus(200).withBody("{}")));

      FilesClient.apiRequest(
          "http://localhost:" + wireMockServer.port() + "/api/rest/v1/redirect",
          HttpMethods.RequestMethods.GET,
          new HashMap<>(),
          new HashMap<>());

      storageServer.verify(getRequestedFor(urlEqualTo("/download"))
          .withoutHeader("X-FilesAPI-Key")
          .withoutHeader("X-FilesAPI-Auth")
          .withoutHeader("X-Files-Workspace-Id"));
      wireMockServer.verify(getRequestedFor(urlEqualTo("/returned"))
          .withoutHeader("X-FilesAPI-Key")
          .withoutHeader("X-FilesAPI-Auth")
          .withoutHeader("X-Files-Workspace-Id"));
    } finally {
      storageServer.stop();
    }
  }

  @Test
  public void keepsFilesAuthHeadersOnSameOriginRedirects() throws Exception {
    FilesClient.workspaceId = 123L;
    wireMockServer.stubFor(get(urlEqualTo("/api/rest/v1/redirect"))
        .willReturn(aResponse()
            .withStatus(302)
            .withHeader("Location", "/api/rest/v1/redirected")));
    wireMockServer.stubFor(get(urlEqualTo("/api/rest/v1/redirected"))
        .willReturn(aResponse().withStatus(200).withBody("{}")));

    FilesClient.apiRequest(
        "http://localhost:" + wireMockServer.port() + "/api/rest/v1/redirect",
        HttpMethods.RequestMethods.GET,
        new HashMap<>(),
        new HashMap<>());

    wireMockServer.verify(getRequestedFor(urlEqualTo("/api/rest/v1/redirected"))
        .withHeader("X-FilesAPI-Key", equalTo("test-key"))
        .withHeader("X-Files-Workspace-Id", equalTo("123")));
  }

  @Test
  public void handleSaveError() throws Exception {
    final String body = """
        {
          "error": "Error saving model.",
          "http-code": 422,
          "instance": "508503b0-2423-4edc-a46f-77d77ac4a7e3",
          "model_errors": {
            "username": [
              "Username must not contain multiple spaces together",
              "Username must not begin or end with a space"
            ]
          },
          "model_error_keys": {
            "username": [
              "multiple_spaces",
              "leading_trailing_space"
            ]
          },
          "errors": [
            "Username must not contain multiple spaces together",
            "Username must not begin or end with a space"
          ],
          "title": "Model Save Error",
          "type": "processing-failure/model-save-error"
        }""";

    wireMockServer.stubFor(post(urlEqualTo("/api/rest/v1/users"))
        .willReturn(aResponse()
            .withStatus(400)
            .withHeader("Content-Type", "application/json; charset=utf-8")
            .withBody(body)));

    HashMap<String, Object> parameters = new HashMap<>();
    parameters.put("username", "testuser");

    try {
      User user = new User(parameters);
      user.save();
    } catch (ApiErrorException e) {
      assertTrue("Expected ModelSaveErrorException, but got: " + e.getClass().getName(), e instanceof ApiErrorException.ModelSaveErrorException);
      ApiErrorException.ModelSaveErrorException exception = (ApiErrorException.ModelSaveErrorException) e;
      assertEquals("processing-failure/model-save-error", exception.getType());
    }
  }

  @Test
  public void saveCreatesANewWorkspaceThenUpdatesItInPlace() throws IOException {
    wireMockServer.stubFor(post(urlEqualTo("/api/rest/v1/workspaces"))
        .willReturn(okJson("{\"id\":7,\"name\":\"Ops\"}")));
    wireMockServer.stubFor(patch(urlEqualTo("/api/rest/v1/workspaces/7"))
        .willReturn(okJson("{\"id\":7,\"name\":\"Operations\"}")));

    Workspace workspace = new Workspace();
    workspace.setName(" ops ");
    workspace.save();

    assertEquals(Long.valueOf(7), workspace.getId());
    assertEquals("Ops", workspace.getName());

    workspace.setName("operations");
    workspace.save();

    assertEquals(Long.valueOf(7), workspace.getId());
    assertEquals("Operations", workspace.getName());
    wireMockServer.verify(1, postRequestedFor(urlEqualTo("/api/rest/v1/workspaces"))
        .withRequestBody(equalToJson("{\"name\":\" ops \"}")));
    wireMockServer.verify(1, patchRequestedFor(urlEqualTo("/api/rest/v1/workspaces/7"))
        .withRequestBody(matchingJsonPath("$.name", equalTo("operations"))));
    assertEquals(2, wireMockServer.getAllServeEvents().size());
  }

  @Test
  public void updateRefreshesTheUserAndReturnsTheServerResponse() throws IOException {
    wireMockServer.stubFor(patch(urlEqualTo("/api/rest/v1/users/5"))
        .willReturn(okJson("{\"id\":5,\"name\":\"Alex Canonical\",\"notes\":null,\"company\":\"\","
            + "\"require_password_change\":false,\"password_validity_days\":0}")));
    wireMockServer.stubFor(delete(urlEqualTo("/api/rest/v1/users/5"))
        .willReturn(aResponse().withStatus(204)));

    HashMap<String, Object> attributes = new HashMap<>();
    attributes.put("id", 5);
    attributes.put("name", "Alex");
    attributes.put("notes", "stale note");
    HashMap<String, Object> options = new HashMap<>();
    options.put("api_key", "per-call-key");
    User user = new User(attributes, options);

    HashMap<String, Object> changes = new HashMap<>();
    changes.put("name", "alex");
    User returned = user.update(changes);

    assertNotSame(user, returned);
    for (User refreshed : Arrays.asList(user, returned)) {
      assertEquals("Alex Canonical", refreshed.getName());
      assertNull("an explicit null in the response clears the old value", refreshed.getNotes());
      assertEquals("", refreshed.getCompany());
      assertEquals(Boolean.FALSE, refreshed.getRequirePasswordChange());
      assertEquals(Long.valueOf(0), refreshed.getPasswordValidityDays());
    }

    // Both objects keep sending the options of the request that produced them.
    user.delete(null);
    returned.delete(null);
    wireMockServer.verify(1, patchRequestedFor(urlEqualTo("/api/rest/v1/users/5"))
        .withHeader("X-FilesAPI-Key", equalTo("per-call-key"))
        .withRequestBody(equalToJson("{\"name\":\"alex\"}")));
    wireMockServer.verify(2, deleteRequestedFor(urlEqualTo("/api/rest/v1/users/5"))
        .withHeader("X-FilesAPI-Key", equalTo("per-call-key")));
  }

  @Test
  public void failedSaveLeavesTheWorkspaceUnchanged() throws IOException {
    wireMockServer.stubFor(patch(urlEqualTo("/api/rest/v1/workspaces/7"))
        .willReturn(aResponse()
            .withStatus(422)
            .withHeader("Content-Type", "application/json")
            .withBody("{\"type\":\"processing-failure/model-save-error\",\"error\":\"Name has already been taken\","
                + "\"http-code\":422}")));

    HashMap<String, Object> attributes = new HashMap<>();
    attributes.put("id", 7);
    attributes.put("name", "Ops");
    Workspace workspace = new Workspace(attributes);
    workspace.setName("Taken");

    try {
      workspace.save();
      fail("Expected the rejected update to throw");
    } catch (ApiErrorException.ModelSaveErrorException e) {
      assertEquals("Name has already been taken", e.getError());
    }

    assertEquals(Long.valueOf(7), workspace.getId());
    assertEquals("Taken", workspace.getName());

    // A success status without the record is not a successful save either.
    wireMockServer.stubFor(patch(urlEqualTo("/api/rest/v1/workspaces/7")).willReturn(okJson("null")));
    try {
      workspace.save();
      fail("Expected a null update response to be rejected");
    } catch (ApiErrorException.ApiConnectionException e) {
      assertEquals("The Files.com API response body is not the expected JSON object", e.getMessage());
    }

    assertEquals(Long.valueOf(7), workspace.getId());
    assertEquals("Taken", workspace.getName());
    assertEquals(2, wireMockServer.getAllServeEvents().size());
  }

  @Test
  public void modelsBuiltOnManyThreadsAtOnceKeepTheirOwnDatesAndDecimals() throws Exception {
    // All objects of a model class share one JSON mapper, so dates and decimals read or written for
    // one object at the same time as for others must still be exactly its own.
    int threads = 16;
    int perThread = 200;
    ExecutorService executor = Executors.newFixedThreadPool(threads);
    try {
      List<Future<Void>> results = new ArrayList<>();
      for (int t = 0; t < threads; t++) {
        final int thread = t;
        results.add(executor.submit(() -> {
          for (int i = 0; i < perThread; i++) {
            int n = thread * perThread + i;
            Date createdAt = new Date(1_700_000_000_000L + n * 1_000L);
            BigDecimal amount = new BigDecimal(n + ".25");
            HashMap<String, Object> attributes = new HashMap<>();
            attributes.put("created_at", createdAt);
            attributes.put("amount", amount);
            Invoice invoice = new Invoice(attributes);
            assertEquals(createdAt, invoice.getCreatedAt());
            assertEquals(amount, invoice.getAmount());
          }
          return null;
        }));
      }
      for (Future<Void> result : results) {
        result.get(60, TimeUnit.SECONDS);
      }
    } finally {
      executor.shutdownNow();
    }
  }

  @Test
  public void saveTreatsOnlyAServerAssignedIdAsAnExistingRecord() throws IOException {
    // A path says where a new file goes, so saving a File creates it even though the path is set.
    wireMockServer.stubFor(post(urlEqualTo("/api/rest/v1/files/report.txt")).willReturn(okJson("{}")));
    HashMap<String, Object> fileAttributes = new HashMap<>();
    fileAttributes.put("path", "report.txt");
    new File(fileAttributes).save();
    wireMockServer.verify(1, postRequestedFor(urlEqualTo("/api/rest/v1/files/report.txt")));

    // Permissions cannot be updated, so saving one that already exists must not create a duplicate.
    HashMap<String, Object> permissionAttributes = new HashMap<>();
    permissionAttributes.put("id", 3);
    permissionAttributes.put("path", "shared");
    try {
      new Permission(permissionAttributes).save();
      fail("Expected saving an existing Permission to be rejected");
    } catch (UnsupportedOperationException expected) {
      // No request is sent.
    }
    assertEquals(1, wireMockServer.getAllServeEvents().size());
  }

  @Test
  public void saveUpdatesAStyleAtItsPath() throws IOException {
    wireMockServer.stubFor(patch(urlEqualTo("/api/rest/v1/styles/brand"))
        .willReturn(okJson("{\"id\":2,\"path\":\"brand\",\"logo_click_href\":\"https://example.com/home\"}")));

    HashMap<String, Object> attributes = new HashMap<>();
    attributes.put("path", "brand");
    attributes.put("logo_click_href", "https://example.com");
    Style style = new Style(attributes);
    style.save();

    assertEquals(Long.valueOf(2), style.getId());
    assertEquals("https://example.com/home", style.getLogoClickHref());
    wireMockServer.verify(1, patchRequestedFor(urlEqualTo("/api/rest/v1/styles/brand"))
        .withRequestBody(matchingJsonPath("$.logo_click_href", equalTo("https://example.com"))));
  }

  @Test
  public void saveSendsHydratedArraysAsTheArraysTheOperationAccepts() throws IOException {
    wireMockServer.stubFor(post(urlEqualTo("/api/rest/v1/metadata_categories"))
        .willReturn(okJson("{\"id\":7,\"name\":\"Invoices\",\"default_columns\":[]}")));
    wireMockServer.stubFor(patch(urlEqualTo("/api/rest/v1/metadata_categories/7"))
        .willReturn(okJson("{\"id\":7,\"name\":\"Invoices\",\"default_columns\":[\"amount\"]}")));

    MetadataCategory category = new MetadataCategory();
    category.setName("Invoices");
    category.save();
    category.save();

    assertArrayEquals(new String[]{"amount"}, category.getDefaultColumns());
    wireMockServer.verify(1, postRequestedFor(urlEqualTo("/api/rest/v1/metadata_categories")));
    wireMockServer.verify(1, patchRequestedFor(urlEqualTo("/api/rest/v1/metadata_categories/7"))
        .withRequestBody(matchingJsonPath("$.default_columns", equalToJson("[]"))));
    assertEquals(2, wireMockServer.getAllServeEvents().size());
  }

  @Test
  public void saveSendsIdListsToStringParametersCommaDelimited() throws IOException {
    wireMockServer.stubFor(patch(urlEqualTo("/api/rest/v1/automations/9")).willReturn(okJson("{\"id\":9}")));
    // The API returns these ID lists as arrays, while its update takes them as comma-delimited text.
    HashMap<String, Object> attributes = new HashMap<>();
    attributes.put("id", 9);
    attributes.put("user_ids", Arrays.asList(4, 5));
    attributes.put("recurring_days", Arrays.asList(1, 15));
    new Automation(attributes).save();

    wireMockServer.verify(1, patchRequestedFor(urlEqualTo("/api/rest/v1/automations/9"))
        .withRequestBody(matchingJsonPath("$.user_ids", equalTo("4,5")))
        .withRequestBody(matchingJsonPath("$.recurring_days", equalToJson("[1,15]"))));
  }

  @Test
  public void saveSendsExactDecimalsAsTextWhileDoublesStayNumbers() throws IOException {
    String exact = "0.12345678901234567890123456789";
    String canonical = "{\"id\":3,\"min_free_cpu\":\"" + exact + "\",\"min_free_mem\":\"7.50\"}";
    wireMockServer.stubFor(post(urlEqualTo("/api/rest/v1/remote_mount_backends")).willReturn(okJson(canonical)));
    wireMockServer.stubFor(patch(urlEqualTo("/api/rest/v1/remote_mount_backends/3")).willReturn(okJson(canonical)));
    HashMap<String, Object> options = new HashMap<>();
    options.put("api_key", "per-call-key");

    RemoteMountBackend backend = new RemoteMountBackend(new HashMap<>(), options);
    backend.setRemoteServerMountId(2L);
    backend.setRemoteServerId(4L);
    backend.setCanaryFilePath("canary.txt");
    backend.setMinFreeCpu(new BigDecimal(exact));
    backend.setMinFreeMem(new BigDecimal("7.50"));
    backend.save();
    backend.save();

    assertEquals(new BigDecimal(exact), backend.getMinFreeCpu());
    assertEquals(new BigDecimal("7.50"), backend.getMinFreeMem());

    // Callers passing a Double keep sending a JSON number.
    HashMap<String, Object> parameters = new HashMap<>();
    parameters.put("min_free_cpu", 0.5d);
    parameters.put("min_free_mem", 1e-7d);
    RemoteMountBackend.update(3L, parameters);

    List<LoggedRequest> requests =
        wireMockServer.findAll(anyRequestedFor(urlPathMatching("/api/rest/v1/remote_mount_backends.*")));
    assertEquals(Arrays.asList("POST", "PATCH", "PATCH"), Arrays.asList(requests.get(0).getMethod().getName(),
        requests.get(1).getMethod().getName(), requests.get(2).getMethod().getName()));
    for (LoggedRequest save : requests.subList(0, 2)) {
      assertEquals("per-call-key", save.getHeader("X-FilesAPI-Key"));
      assertJsonText(exact, requestJson(save).get("min_free_cpu"));
      assertJsonText("7.50", requestJson(save).get("min_free_mem"));
    }
    String legacy = requests.get(2).getBodyAsString();
    assertTrue(legacy, legacy.contains("\"min_free_cpu\":0.5") && legacy.contains("\"min_free_mem\":1.0E-7"));
  }

  @Test
  public void remoteMountBackendDecimalsTakeLegacyDoublesAndExactTextOrBigDecimals() throws IOException {
    String exact = "0.123456789012345678901234567890";
    wireMockServer.stubFor(post(urlEqualTo("/api/rest/v1/remote_mount_backends"))
        .willReturn(okJson("{\"id\":3,\"min_free_cpu\":\"1.25\",\"min_free_mem\":\"" + exact + "\"}")));
    wireMockServer.stubFor(patch(urlEqualTo("/api/rest/v1/remote_mount_backends/3"))
        .willReturn(okJson("{\"id\":3,\"min_free_cpu\":\"" + exact + "\",\"min_free_mem\":\"0\"}")));

    HashMap<String, Object> create = new HashMap<>();
    create.put("remote_server_mount_id", 2L);
    create.put("remote_server_id", 4L);
    create.put("canary_file_path", "canary.txt");
    create.put("min_free_cpu", 1.25d);
    create.put("min_free_mem", exact);
    RemoteMountBackend created = RemoteMountBackend.create(create);
    assertEquals(new BigDecimal(exact), created.getMinFreeMem());

    HashMap<String, Object> update = new HashMap<>();
    update.put("min_free_cpu", new BigDecimal(exact));
    update.put("min_free_mem", "0");
    RemoteMountBackend.update(3L, update);
    HashMap<String, Object> omitted = new HashMap<>();
    omitted.put("canary_file_path", "canary.txt");
    RemoteMountBackend.update(3L, omitted);

    List<LoggedRequest> requests =
        wireMockServer.findAll(anyRequestedFor(urlPathMatching("/api/rest/v1/remote_mount_backends.*")));
    assertEquals(3, requests.size());
    assertTrue(requests.get(0).getBodyAsString(), requests.get(0).getBodyAsString().contains("\"min_free_cpu\":1.25"));
    assertJsonText(exact, requestJson(requests.get(0)).get("min_free_mem"));
    assertJsonText(exact, requestJson(requests.get(1)).get("min_free_cpu"));
    assertJsonText("0", requestJson(requests.get(1)).get("min_free_mem"));
    assertFalse(requestJson(requests.get(2)).has("min_free_cpu"));
    assertFalse(requestJson(requests.get(2)).has("min_free_mem"));

    // Other numbers are not accepted.
    HashMap<String, Object> unsupported = new HashMap<>();
    unsupported.put("min_free_cpu", 1);
    try {
      RemoteMountBackend.update(3L, unsupported);
      fail("Expected an Integer to be rejected");
    } catch (IllegalArgumentException e) {
      assertEquals("Bad parameter: min_free_cpu must be of type Double, BigDecimal or String parameters[\"min_free_cpu\"]",
          e.getMessage());
    }
    assertEquals(3, wireMockServer.getAllServeEvents().size());

    // The API validates decimal text; a rejected update leaves the backend as it was.
    wireMockServer.stubFor(patch(urlEqualTo("/api/rest/v1/remote_mount_backends/3"))
        .willReturn(aResponse().withStatus(422).withHeader("Content-Type", "application/json")
            .withBody("{\"type\":\"processing-failure/model-save-error\",\"error\":\"Min free cpu is not a number\","
                + "\"http-code\":422}")));
    HashMap<String, Object> invalid = new HashMap<>();
    invalid.put("min_free_cpu", "twelve");
    try {
      created.update(invalid);
      fail("Expected the API to reject the text");
    } catch (ApiErrorException.ModelSaveErrorException e) {
      assertEquals("Min free cpu is not a number", e.getError());
    }
    assertEquals(new BigDecimal("1.25"), created.getMinFreeCpu());
    assertEquals(new BigDecimal(exact), created.getMinFreeMem());
    assertJsonText("twelve", requestJson(wireMockServer.findAll(anyRequestedFor(anyUrl())).get(3)).get("min_free_cpu"));
  }

  private static void assertJsonText(String expected, JsonNode value) {
    assertTrue("sent as text: " + value, value.isTextual());
    assertEquals(expected, value.textValue());
  }

  @Test
  public void handleNotFound() throws Exception {
    final String body = """
        {
          "type": "not-found",
          "http-code": 404,
          "error": "Not Found.  This may be related to your permissions."
        }""";

    wireMockServer.stubFor(get(urlEqualTo("/api/rest/v1/folders/%2Fmissing"))
        .willReturn(aResponse()
            .withStatus(404)
            .withHeader("Content-Type", "application/json; charset=utf-8")
            .withBody(body)));

    try {
      Folder.listFor("/missing", null).all();
      fail("Expected exception did not occur");
    } catch (RuntimeException e) {
      assertTrue("Expected NotFoundException, but got: " + e.getClass().getName(), e instanceof ApiErrorException.NotFoundException);
      ApiErrorException.NotFoundException exception = (ApiErrorException.NotFoundException) e;
      assertEquals("not-found", exception.getType());
      assertEquals(404, exception.getHttpCode());
      assertEquals("Not Found.  This may be related to your permissions.", exception.getError());
      assertTrue(exception.getHeaders().stream()
          .anyMatch(h -> h.getName().equalsIgnoreCase("Content-Type")
              && h.getValue().equalsIgnoreCase("application/json; charset=utf-8")));
    }
  }

  @Test
  public void handleNoResponseData() throws Exception {
    wireMockServer.stubFor(post(urlEqualTo("/api/rest/v1/bundles/1/share"))
        .willReturn(aResponse()
            .withStatus(200)
            .withBody("")));

    // Should not throw an exception
    Bundle.share((long) 1, null);
  }

  @Test
  public void handleEmptyResponse() throws Exception {
    wireMockServer.stubFor(get(urlEqualTo("/api/rest/v1/folders/%2Fmissing"))
        .willReturn(aResponse()
            .withStatus(200)
            .withBody("[]")));

    int count = 0;
    for (File file : Folder.listFor("/missing", null).listAutoPaging()) {
      count++;
    }
    assertEquals(0, count);
  }

  @Test
  public void autoPagingContinuesPastEmptyPagesAndStopsAtTheEnd() {
    stubWorkspacePage(null, "c1", "[{\"id\":1}]");
    stubWorkspacePage("c1", "c2", "[]");
    stubWorkspacePage("c2", "c3", "[]");
    stubWorkspacePage("c3", null, "[{\"id\":4}]");

    Iterator<Workspace> workspaces = Workspace.list().listAutoPaging().iterator();
    List<Long> ids = new ArrayList<>();
    // Asking twice must neither skip an item nor request a page again.
    while (workspaces.hasNext() && workspaces.hasNext()) {
      ids.add(workspaces.next().getId());
    }

    assertEquals(Arrays.asList(1L, 4L), ids);
    assertFalse(workspaces.hasNext());
    assertNoNextItem(workspaces);
    assertEquals(Arrays.asList(null, "c1", "c2", "c3"), requestedCursors());
  }

  @Test
  public void autoPagingEndsAtATerminalEmptyPageAndStopsRequestingAfterABreak() {
    stubWorkspacePage(null, "c1", "[{\"id\":1},{\"id\":2}]");
    stubWorkspacePage("c1", null, "[]");

    for (Workspace workspace : Workspace.list().listAutoPaging()) {
      assertEquals(Long.valueOf(1), workspace.getId());
      break;
    }
    assertEquals(Collections.singletonList((String) null), requestedCursors());

    Iterator<Workspace> workspaces = Workspace.list().listAutoPaging().iterator();
    assertEquals(Long.valueOf(1), workspaces.next().getId());
    assertEquals(Long.valueOf(2), workspaces.next().getId());
    assertFalse(workspaces.hasNext());
    assertNoNextItem(workspaces);
    assertEquals(Arrays.asList(null, null, "c1"), requestedCursors());
  }

  @Test
  public void manualPagingReadsOnePageAtATimeAndResetStartsOver() {
    stubWorkspacePage(null, "c1", "[{\"id\":1}]");
    stubWorkspacePage("c1", "c2", "[{\"id\":2}]");
    stubWorkspacePage("c2", null, "[{\"id\":3}]");
    HashMap<String, Object> parameters = workspaceListParameters();
    ListIterator<Workspace> listing = Workspace.list(parameters, workspaceListOptions());

    assertEquals(Arrays.asList(1L), ids(listing.loadNextPage()));
    assertTrue(listing.hasNextPage());
    assertEquals(Arrays.asList(2L), ids(listing.loadNextPage()));

    assertSame(listing, listing.reset());
    assertFalse(listing.hasNextPage());
    assertEquals(Collections.emptyList(), ids(listing));

    assertEquals(Arrays.asList(1L), ids(listing.loadNextPage()));
    assertEquals(Arrays.asList(2L), ids(listing.loadNextPage()));
    assertEquals(Arrays.asList(3L), ids(listing.loadNextPage()));
    assertFalse(listing.hasNextPage());
    try {
      listing.loadNextPage();
      fail("Expected loading past the last page to fail");
    } catch (NoSuchElementException expected) {
      // No request is sent.
    }

    assertEquals(Arrays.asList(null, "c1", null, "c1", "c2"), requestedCursors());
    assertPageRequestsKeepFiltersAndOptions();
    assertEquals("the caller's parameters are not used for cursor state", workspaceListParameters(), parameters);
  }

  @Test
  public void autoPagingContinuesFromTheCurrentPageUntilTheListingIsReset() {
    stubWorkspacePage(null, "c1", "[{\"id\":1}]");
    stubWorkspacePage("c1", "c2", "[{\"id\":2}]");
    stubWorkspacePage("c2", null, "[{\"id\":3}]");
    ListIterator<Workspace> listing = Workspace.list();
    listing.loadNextPage();

    Iterator<Workspace> remaining = listing.listAutoPaging().iterator();
    assertEquals(Long.valueOf(2), remaining.next().getId());

    listing.reset();
    try {
      remaining.hasNext();
      fail("Expected an iterator from before reset() to be invalidated");
    } catch (ConcurrentModificationException expected) {
      // Iteration must start again from the reset listing.
    }
    assertEquals(Arrays.asList(1L, 2L, 3L), ids(listing.listAutoPaging()));
    assertEquals(Arrays.asList(null, "c1", null, "c1", "c2"), requestedCursors());
  }

  @Test
  public void allStartsFromTheFirstPageAfterManualPaging() {
    stubWorkspacePage(null, "c1", "[{\"id\":1}]");
    stubWorkspacePage("c1", "c2", "[{\"id\":2}]");
    stubWorkspacePage("c2", null, "[{\"id\":3}]");
    HashMap<String, Object> parameters = workspaceListParameters();
    ListIterator<Workspace> listing = Workspace.list(parameters, workspaceListOptions());
    listing.loadNextPage();
    listing.loadNextPage();

    assertEquals(Arrays.asList(1L, 2L, 3L), ids(listing.all()));
    assertEquals(Arrays.asList(null, "c1", null, "c1", "c2"), requestedCursors());
    assertPageRequestsKeepFiltersAndOptions();
    assertEquals("the caller's parameters are not used for cursor state", workspaceListParameters(), parameters);
  }

  @Test
  public void successfulResponsesThatCannotBeDecodedAreConnectionErrors() {
    for (String body : new String[]{"", "null", "{}", "[{\"id\":", "[{\"id\":1},null]", "[1]"}) {
      wireMockServer.stubFor(get(urlPathEqualTo("/api/rest/v1/workspaces")).willReturn(okJson(body)));
      ListIterator<Workspace> listing = Workspace.list();
      try {
        listing.loadNextPage();
        fail("Expected list page [" + body + "] to be rejected");
      } catch (ApiErrorException.ApiConnectionException e) {
        assertEquals("The Files.com API returned a list page that is not a JSON array of objects", e.getMessage());
      }
      assertEquals("a rejected page is not published", Collections.emptyList(), listing.data);
    }

    for (String body : new String[]{"{\"id\":", "null"}) {
      wireMockServer.stubFor(get(urlEqualTo("/api/rest/v1/workspaces/1")).willReturn(okJson(body)));
      try {
        Workspace.find(1L, null);
        fail("Expected Workspace response [" + body + "] to be rejected");
      } catch (ApiErrorException.ApiConnectionException e) {
        assertEquals("The Files.com API response body is not the expected JSON object", e.getMessage());
      }
    }
    wireMockServer.stubFor(get(urlEqualTo("/api/rest/v1/workspaces/1")).willReturn(okJson("{}")));
    assertNull("an empty object is still a Workspace", Workspace.find(1L, null).getId());

    wireMockServer.stubFor(get(urlEqualTo("/api/rest/v1/workspaces/1"))
        .willReturn(aResponse().withFault(Fault.MALFORMED_RESPONSE_CHUNK)));
    int leasedBefore = leasedConnections();
    try {
      Workspace.find(1L, null);
      fail("Expected an unreadable response body to be rejected");
    } catch (ApiErrorException.ApiConnectionException e) {
      // The parser's own message would quote the response bytes.
      assertEquals("The Files.com API response could not be read: its chunked encoding is malformed (response to GET /api/rest/v1/workspaces/1)", e.getMessage());
      assertNull(e.getCause());
    }
    assertEquals(leasedBefore, leasedConnections());
  }

  @Test(timeout = 10000)
  public void listingFailsWhenAnEmptyPageRepeatsItsCursor() {
    stubWorkspacePage(null, "c1", "[{\"id\":1}]");
    stubWorkspacePage("c1", "c1", "[]");

    try {
      Workspace.list().all();
      fail("Expected a cursor that does not advance to be rejected");
    } catch (ApiErrorException.ApiConnectionException e) {
      assertEquals("The Files.com API returned an empty page that repeats the requested cursor", e.getMessage());
    }
    assertEquals(Arrays.asList(null, "c1"), requestedCursors());
  }

  @Test
  public void handleBadGateway() throws Exception {
    final String body = "<html><head><title>502 Bad Gateway</title></head><body><center><h1>502 Bad Gateway</h1></center><hr><center>files.com</center></body></html>";
    wireMockServer.stubFor(get(urlEqualTo("/api/rest/v1/folders/%2F"))
        .willReturn(aResponse()
            .withStatus(502)
            .withBody(body)));

    try {
      Folder.listFor("/", null).all();
      fail("Expected exception did not occur");
    } catch (ApiErrorException.ApiConnectionException e) {
      assertEquals("HTTP returned status 502 without a structured error response (response to GET /api/rest/v1/folders/%2F)", e.getMessage());
    }
    wireMockServer.verify(FilesConfig.getInstance().getMaxNetworkRetries() + 1,
        getRequestedFor(urlEqualTo("/api/rest/v1/folders/%2F")));
  }

  @Test
  public void handleInvalidResponse() throws Exception {
    final String body =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<text>"
            + "<para>test</para>"
            + "</text>";
    wireMockServer.stubFor(get(urlEqualTo("/api/rest/v1/folders/%2F"))
        .willReturn(aResponse()
            .withStatus(400)
            .withBody(body)));

    try {
      Folder.listFor("/", null).all();
      fail("Expected exception did not occur");
    } catch (ApiErrorException.ApiConnectionException e) {
      assertEquals("HTTP returned status 400 without a structured error response (response to GET /api/rest/v1/folders/%2F)", e.getMessage());
    }
  }

  @Test
  public void handleHostnameMismatch() throws Exception {
    final String body = """
        {
          "type": "not-authenticated/lockout-region-mismatch",
          "http-code": 403,
          "title": "Lockout Region Mismatch",
          "error": "You have connected to a URL that has different security settings than those required for your site.",
          "data": {
            "host": "test.host"
          }
        }""";

    wireMockServer.stubFor(get(urlEqualTo("/api/rest/v1/folders/%2F"))
        .willReturn(aResponse()
            .withStatus(403)
            .withHeader("x-files-host", "test.host")
            .withBody(body)));

    try {
      Folder.listFor("/", null).all();
      fail("Expected exception did not occur");
    } catch (RuntimeException e) {
      assertTrue("Expected LockoutRegionMismatchException, but got: " + e.getClass().getName(), e instanceof ApiErrorException.LockoutRegionMismatchException);
      ApiErrorException.LockoutRegionMismatchException exception = (ApiErrorException.LockoutRegionMismatchException) e;
      assertEquals(403, exception.getHttpCode());
      assertEquals("Lockout Region Mismatch", exception.getTitle());
      assertEquals("not-authenticated/lockout-region-mismatch", exception.getType());
      assertEquals("You have connected to a URL that has different security settings than those required for your site.", exception.getError());
      assertEquals("test.host", exception.getData().get("host"));
    }
  }


  @Test
  public void handleRegionMismatch() throws Exception {
    final String body = """
        {
          "type": "not-authenticated/lockout-region-mismatch",
          "http-code": 401,
          "title": "Lockout Region Mismatch",
          "error": "Your account must login using a different server, test.host.",
          "data": {
            "host": "test.host"
          }
        }""";
    wireMockServer.stubFor(get(urlEqualTo("/api/rest/v1/folders/%2F"))
        .willReturn(aResponse()
            .withStatus(401)
            .withHeader("Content-Type", "application/json; charset=utf-8")
            .withBody(body)));

    try {
      Folder.listFor("/", null).all();
      fail("Expected exception did not occur");
    } catch (RuntimeException e) {
      assertTrue("Expected LockoutRegionMismatchException, but got: " + e.getClass().getName(), e instanceof ApiErrorException.LockoutRegionMismatchException);
      ApiErrorException.LockoutRegionMismatchException exception = (ApiErrorException.LockoutRegionMismatchException) e;
      assertEquals(401, exception.getHttpCode());
      assertEquals("Lockout Region Mismatch", exception.getTitle());
      assertEquals("not-authenticated/lockout-region-mismatch", exception.getType());
      assertEquals("Your account must login using a different server, test.host.", exception.getError());
      assertEquals("test.host", exception.getData().get("host"));
    }
  }

  @Test
  public void knownErrorTypesKeepTheirExceptionClassAtAnyStatus() {
    for (int status : new int[]{403, 500}) {
      SdkException e = findWorkspaceFailure(status,
          structuredError("\"not-authorized/folder-admin-permission-required\"", status));

      assertEquals(ApiErrorException.FolderAdminPermissionRequiredException.class, e.getClass());
      assertStructuredDetails((ApiErrorException) e, status);
    }
  }

  @Test
  public void unrecognizedErrorTypesFallBackToApiErrorExceptionWithTheirDetails() {
    // Each pair is the JSON "type" value and what getType() reports for it. Jackson reads a
    // number or boolean as text; an object or array cannot name an error type at all.
    String[][] types = {
        {"\"not-authorized/brand-new-error\"", "not-authorized/brand-new-error"},
        {null, null},
        {"null", null},
        {"\"\"", ""},
        {"\" \"", " "},
        {"\"/\"", "/"},
        {"\"-\"", "-"},
        {"\"not--found\"", "not--found"},
        {"123", "123"},
        {"true", "true"},
        {"{\"name\":\"not-found\"}", null},
        {"[\"not-found\"]", null},
    };
    for (String[] type : types) {
      SdkException e = findWorkspaceFailure(400, structuredError(type[0], 400));

      assertEquals("type " + type[0], ApiErrorException.class, e.getClass());
      assertEquals("type " + type[0], type[1], ((ApiErrorException) e).getType());
      assertStructuredDetails((ApiErrorException) e, 400);
    }
  }

  @Test
  public void responsesWithoutAStructuredErrorAreConnectionErrors() {
    String signedStorageError = "<Error><Code>SignatureDoesNotMatch</Code>"
        + "<SignatureProvided>signed-secret</SignatureProvided></Error>";
    String[] bodies = {"", "null", "[]", "\"not-found\"", "{\"type\":", signedStorageError};
    for (int status : new int[]{400, 403, 500}) {
      for (String body : bodies) {
        SdkException e = findWorkspaceFailure(status, body);

        String scenario = "HTTP " + status + " [" + body + "]";
        assertEquals(scenario, ApiErrorException.ApiConnectionException.class, e.getClass());
        assertEquals(scenario, "HTTP returned status " + status + " without a structured error response (response to GET /api/rest/v1/workspaces/1)",
            e.getMessage());
      }
    }
  }

  @Test
  public void defaultErrorDescriptionsOmitUrlSecretsAndResponseValues() {
    String signedUrl = "https://user:p'w@bucket.example/key.txt?X-Amz-Signature=fake-secret#part";
    for (String type : new String[]{"not-found", "brand-new-error"}) {
      SdkException e = findWorkspaceFailure(404, "{\"type\":\"" + type + "\",\"error\":\"Missing " + signedUrl + "\"}");

      assertEquals("[" + type + "] Missing https://bucket.example/key.txt", e.toString());
      assertEquals("the explicit field is unchanged", "Missing " + signedUrl, ((ApiErrorException) e).getError());
    }

    // An upload part whose numeric field holds text cannot be bound, and the text is not repeated.
    wireMockServer.stubFor(post(urlEqualTo("/api/rest/v1/files/report.txt"))
        .willReturn(okJson("{\"partsize\":\"fake-secret\"}")));
    try {
      File.create("report.txt", new HashMap<String, Object>());
      fail("Expected the malformed upload part to be rejected");
    } catch (ApiErrorException.ApiConnectionException e) {
      assertEquals("The Files.com API response body is not the expected JSON object", e.getMessage());
      assertNull(e.getCause());
    }
  }

  @Test
  public void transportFailureWarningsAndErrorsOmitUrlSecrets() throws IOException {
    // Nothing listens on the port, so every attempt fails to connect and is retried.
    int closedPort;
    try (java.net.ServerSocket socket = new java.net.ServerSocket(0, 1, java.net.InetAddress.getLoopbackAddress())) {
      closedPort = socket.getLocalPort();
    }
    String signedUrl = "http://user:PRIVATE_PASSWORD@127.0.0.1:" + closedPort
        + "/folder/key.txt?X-Amz-Signature=PRIVATE_QUERY#PRIVATE_FRAGMENT";
    String retryDelay = String.valueOf(FilesConfig.getInstance().getInitialRetryDelayMillis());
    FilesClient.setProperty("initialRetryDelayMillis", "1");
    SdkWarnings warnings = new SdkWarnings();
    try {
      FilesClient.getFileInputStream(signedUrl, 0, 0);
      fail("Expected the connection to fail");
    } catch (ApiErrorException.ApiConnectionException e) {
      StringWriter stack = new StringWriter();
      e.printStackTrace(new PrintWriter(stack));
      assertFalse(stack.toString(), stack.toString().contains("PRIVATE"));
      assertNull(e.getCause());
    } finally {
      warnings.close();
      FilesClient.setProperty("initialRetryDelayMillis", retryDelay);
    }

    assertEquals(warnings.events().toString(), 3, warnings.events().size());
    for (int retry = 1; retry <= 3; retry++) {
      String event = warnings.events().get(retry - 1);
      assertTrue(event, event.startsWith("WARN com.files.net.ClientRuntime Retrying the GET request (retry " + retry + " of 3) in "));
      assertFalse(event, event.contains("PRIVATE"));
    }
  }

  @Test
  public void aUrlThatCannotBeSentFailsWithoutRetriesOrUrlSecrets() {
    // HttpClient rejects the missing host before it connects, and names the URL in its message.
    String signedUrl = "http://user:PRIVATE_PASSWORD@/folder/key.txt?X-Amz-Signature=PRIVATE_QUERY#PRIVATE_FRAGMENT";
    SdkWarnings warnings = new SdkWarnings();
    try {
      FilesClient.getFileInputStream(signedUrl, 0, 0);
      fail("Expected the URL without a host to be rejected");
    } catch (ApiErrorException.ApiConnectionException | IOException e) {
      StringWriter stack = new StringWriter();
      e.printStackTrace(new PrintWriter(stack));
      assertEquals("The request failed: the request could not be sent as written (ClientProtocolException)", e.getMessage());
      assertFalse(stack.toString(), stack.toString().contains("PRIVATE"));
      assertNull(e.getCause());
    } finally {
      warnings.close();
    }
    assertEquals("a request that cannot be sent as written is not retried", Arrays.asList(), warnings.events());
  }

  @Test
  public void putBufferReleasesPooledConnectionAfterSuccessfulUpload() throws IOException {
    for (String responseBody : UPLOAD_RESPONSE_BODIES) {
      stubUploadResponse(responseBody);
      int leasedBefore = leasedConnections();

      long returned = FilesClient.putBuffer(uploadUrl(), HttpMethods.RequestMethods.PUT, "upload.bin",
          UPLOAD_BYTES, UPLOAD_BYTES.length);

      assertEquals(UPLOAD_BYTES.length, returned);
      assertEquals("leased connections after response [" + responseBody + "]", leasedBefore, leasedConnections());
    }
    wireMockServer.verify(UPLOAD_RESPONSE_BODIES.length,
        putRequestedFor(urlEqualTo("/upload")).withRequestBody(binaryEqualTo(UPLOAD_BYTES)));
  }

  @Test
  public void putBufferedInputStreamReleasesPooledConnectionAfterSuccessfulUpload() throws IOException {
    for (String responseBody : UPLOAD_RESPONSE_BODIES) {
      stubUploadResponse(responseBody);
      int leasedBefore = leasedConnections();

      long returned = FilesClient.putBufferedInputStream(uploadUrl(), HttpMethods.RequestMethods.PUT, "upload.bin",
          new BufferedInputStream(new ByteArrayInputStream(UPLOAD_BYTES)), UPLOAD_BYTES.length);

      assertEquals(0, returned);
      assertEquals("leased connections after response [" + responseBody + "]", leasedBefore, leasedConnections());
    }
    wireMockServer.verify(UPLOAD_RESPONSE_BODIES.length,
        putRequestedFor(urlEqualTo("/upload")).withRequestBody(binaryEqualTo(UPLOAD_BYTES)));
  }

  @Test
  public void putBufferedInputStreamNeverResendsAStreamItHasConsumed() throws IOException {
    wireMockServer.stubFor(put(urlEqualTo("/upload")).willReturn(aResponse().withStatus(503)));
    try {
      FilesClient.putBufferedInputStream(uploadUrl(), HttpMethods.RequestMethods.PUT, "upload.bin",
          new BufferedInputStream(new ByteArrayInputStream(UPLOAD_BYTES)), UPLOAD_BYTES.length);
      fail("Expected the upload to fail");
    } catch (ApiErrorException.ApiConnectionException e) {
      assertEquals("HTTP returned status 503 without a structured error response", e.getMessage());
    }
    wireMockServer.verify(1, putRequestedFor(urlEqualTo("/upload")));
  }

  private void stubUploadResponse(String body) {
    // Set Content-Length explicitly; WireMock would otherwise chunk even an empty body.
    wireMockServer.stubFor(put(urlEqualTo("/upload"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Length", String.valueOf(body.length()))
            .withBody(body)));
  }

  private String uploadUrl() {
    return "http://localhost:" + wireMockServer.port() + "/upload";
  }

  private static int leasedConnections() {
    return FilesClient.connectionManager.getTotalStats().getLeased();
  }

  private void stubWorkspacePage(String requestCursor, String nextCursor, String body) {
    ResponseDefinitionBuilder page = okJson(body);
    if (nextCursor != null) {
      page = page.withHeader("X-Files-Cursor", nextCursor);
    }
    wireMockServer.stubFor(get(urlPathEqualTo("/api/rest/v1/workspaces"))
        .withQueryParam("cursor", requestCursor == null ? absent() : equalTo(requestCursor))
        .willReturn(page));
  }

  private List<String> requestedCursors() {
    List<String> cursors = new ArrayList<>();
    for (LoggedRequest request : workspacePageRequests()) {
      QueryParameter cursor = request.queryParameter("cursor");
      cursors.add(cursor.isPresent() ? cursor.firstValue() : null);
    }
    return cursors;
  }

  private List<LoggedRequest> workspacePageRequests() {
    return wireMockServer.findAll(getRequestedFor(urlPathEqualTo("/api/rest/v1/workspaces")));
  }

  private static HashMap<String, Object> workspaceListParameters() {
    HashMap<String, Object> filter = new HashMap<>();
    filter.put("name", "ops");
    HashMap<String, Object> parameters = new HashMap<>();
    parameters.put("per_page", 1L);
    parameters.put("filter", filter);
    return parameters;
  }

  private static HashMap<String, Object> workspaceListOptions() {
    HashMap<String, Object> options = new HashMap<>();
    options.put("api_key", "per-call-key");
    return options;
  }

  private void assertPageRequestsKeepFiltersAndOptions() {
    for (LoggedRequest request : workspacePageRequests()) {
      assertEquals("1", request.queryParameter("per_page").firstValue());
      assertEquals("ops", request.queryParameter("filter[name]").firstValue());
      assertEquals("per-call-key", request.getHeader("X-FilesAPI-Key"));
    }
  }

  private static JsonNode requestJson(LoggedRequest request) throws IOException {
    return new ObjectMapper().readTree(request.getBodyAsString());
  }

  private static List<Long> ids(Iterable<Workspace> workspaces) {
    List<Long> ids = new ArrayList<>();
    for (Workspace workspace : workspaces) {
      ids.add(workspace.getId());
    }
    return ids;
  }

  private static void assertNoNextItem(Iterator<?> iterator) {
    try {
      iterator.next();
      fail("Expected next() to fail after the last item");
    } catch (NoSuchElementException expected) {
      // Java's Iterator contract.
    }
  }

  private static String structuredError(String typeJson, int status) {
    String type = typeJson == null ? "" : ",\"type\":" + typeJson;
    return "{\"error\":\"sentinel\",\"http-code\":" + status + type + "}";
  }

  private static void assertStructuredDetails(ApiErrorException e, int status) {
    assertEquals("sentinel", e.getError());
    assertEquals(status, e.getHttpCode());
    boolean hasRequestId = false;
    for (Header header : e.getHeaders()) {
      hasRequestId |= header.getName().equalsIgnoreCase("X-Request-Id") && "request-7".equals(header.getValue());
    }
    assertTrue("keeps the response headers", hasRequestId);
  }

  // Requests a Workspace that fails with the given response, checking that the failed response
  // does not keep a pooled connection leased.
  private SdkException findWorkspaceFailure(int status, String body) {
    wireMockServer.stubFor(get(urlEqualTo("/api/rest/v1/workspaces/1"))
        .willReturn(aResponse()
            .withStatus(status)
            .withHeader("X-Request-Id", "request-7")
            .withHeader("Content-Length", String.valueOf(body.length()))
            .withBody(body)));
    int leasedBefore = leasedConnections();
    try {
      Workspace.find(1L, null);
      throw new AssertionError("Expected HTTP " + status + " [" + body + "] to fail");
    } catch (SdkException e) {
      assertEquals("leased connections after HTTP " + status + " [" + body + "]", leasedBefore, leasedConnections());
      return e;
    }
  }

  // The tests below come from the 1.x public suite. They run against the WireMock servers that
  // test.sh starts in their own process (TestServer), with the default client's API root moved to
  // the first of them, and check what SDK 2.0 conserves from 1.x: per-request API roots, cursors,
  // which failures are retried and how often, request-named errors, time limits and file action
  // waits.

  private void useTestServer() {
    FilesClient.setProperty("apiRoot", server.baseUrl());
  }

  @Test
  public void sendsRequestsToPerCallApiRoot() throws Exception {
    useTestServer();
    secondOrigin.stub("GET", "/api/rest/v1/folders/%2F", response(200)
        .withBody("[]"));

    HashMap<String, Object> options = new HashMap<>();
    options.put("api_root", secondOrigin.baseUrl());
    options.put("api_key", "per-call-key");

    Folder.listFor("/", null, options).all();

    secondOrigin.verify(request("GET", "/api/rest/v1/folders/%2F")
        .withHeader("X-FilesAPI-Key", "per-call-key"));
    server.verify(0, request("GET", "/api/rest/v1/folders/%2F"));
    // The option moves only its own call: the client still starts at its own root.
    assertEquals(server.baseUrl(), FilesClient.defaultClient().apiRoot().toString());
  }

  @Test
  public void aPerCallApiRootMustBeAnHttpUrlString() throws Exception {
    useTestServer();
    HashMap<String, Object> notAString = new HashMap<>();
    notAString.put("api_root", java.net.URI.create(secondOrigin.baseUrl()));
    ApiErrorException.InvalidParameterException wrongType = assertThrows(
        ApiErrorException.InvalidParameterException.class,
        () -> Folder.listFor("/", null, notAString).all());
    assertEquals("Bad option: api_root must be of type String", wrongType.getMessage());

    // An http URL without a host is refused before anything is sent.
    HashMap<String, Object> noHost = new HashMap<>();
    noHost.put("api_root", "http://");
    ApiErrorException.InvalidParameterException notAUrl = assertThrows(
        ApiErrorException.InvalidParameterException.class,
        () -> Folder.listFor("/", null, noHost).all());
    assertEquals("Bad option: api_root must be an http or https URL", notAUrl.getMessage());
    server.verify(0, request("GET", "/api/rest/v1/folders/%2F"));
    secondOrigin.verify(0, request("GET", "/api/rest/v1/folders/%2F"));
  }

  @Test
  public void namesTheOperationWhenAnErrorResponseHasNoStructuredError() throws Exception {
    useTestServer();
    server.stub("GET", "/api/rest/v1/groups?per_page=1", response(400)
        .withHeader("Content-Type", "text/plain")
        .withBody("r89-complete-ordinary-non-json-failure"));

    HashMap<String, Object> parameters = new HashMap<>();
    parameters.put("per_page", 1);
    ApiErrorException.ApiConnectionException error = assertThrows(
        ApiErrorException.ApiConnectionException.class,
        () -> Group.list(parameters).all());

    // The request's method and path follow the status, without its query or host; the body is not
    // repeated.
    assertEquals("HTTP returned status 400 without a structured error response (response to GET /api/rest/v1/groups)",
        error.getMessage());
    server.verify(1, request("GET", "/api/rest/v1/groups?per_page=1"));

    // No entity: the response to a HEAD request has no body to read an error from.
    server.stub("HEAD", "/api/rest/v1/folders/%2Fheadless", response(404));
    ApiErrorException.ApiConnectionException noEntity = assertThrows(
        ApiErrorException.ApiConnectionException.class,
        () -> FilesClient.apiRequest(server.url("/api/rest/v1/folders/%2Fheadless"),
            HttpMethods.RequestMethods.HEAD, null, new HashMap<>()));
    assertEquals("HTTP returned status 404 without a structured error response"
        + " (response to HEAD /api/rest/v1/folders/%2Fheadless)", noEntity.getMessage());
  }

  @Test
  public void retriesAGetThatFailsWithATransientServerError() throws Exception {
    useTestServer();
    String groups = "/api/rest/v1/groups?per_page=1";
    stubFirstThen(groups, 500, "{\"type\":\"server-error\",\"error\":\"transient\"}", 200, "[]");

    HashMap<String, Object> parameters = new HashMap<>();
    parameters.put("per_page", 1);
    List<Group> page;
    List<String> warnings;
    // initialRetryDelayMillis is left unset, so the retry waits the default.
    int initialDelay = FilesConfig.getInstance().getInitialRetryDelayMillis();
    FilesClient.setProperty("initialRetryDelayMillis", "");
    try (SdkWarnings sdkWarnings = new SdkWarnings()) {
      page = Group.list(parameters).all();
      warnings = retriesAnnounced(sdkWarnings);
    } finally {
      FilesClient.setProperty("initialRetryDelayMillis", Integer.toString(initialDelay));
    }

    assertTrue(page.isEmpty());
    server.verify(2, request("GET", groups));
    // The retry is logged before it is sent, with its number, the default 100 ms wait before a
    // first retry and its reason.
    assertEquals(warnings.toString(), 1, warnings.size());
    assertTrue(warnings.toString(), warnings.get(0).contains("Retrying the GET request (retry 1 of "
        + FilesConfig.getInstance().getMaxNetworkRetries() + ") in 100 ms after HTTP status 500"));
  }

  @Test
  public void doesNotAnnounceARetryAfterTheLastAttempt() throws Exception {
    // A GET answered with a retryable 500 every time. With one attempt allowed it is not tried
    // again; with upstreamMaxAttempts unset, the default allows 4 attempts in all, the first
    // request and 3 retries. Each retry is announced before it is sent, so none is announced after
    // the last attempt.
    useTestServer();
    String groups = "/api/rest/v1/groups?per_page=1";
    server.stub("GET", groups, response(500)
        .withHeader("Content-Type", "application/json")
        .withBody("{\"type\":\"server-error\",\"error\":\"final attempt\"}"));
    HashMap<String, Object> parameters = new HashMap<>();
    parameters.put("per_page", 1);
    List<String> warnings;
    List<String> defaultWarnings;
    int attempts = FilesConfig.getInstance().getUpstreamMaxAttempts();
    int initialDelay = FilesConfig.getInstance().getInitialRetryDelayMillis();
    ApiErrorException error;
    ApiErrorException defaultError;
    try {
      FilesClient.setProperty("initialRetryDelayMillis", "1");
      FilesClient.setProperty("upstreamMaxAttempts", "1");
      try (SdkWarnings sdkWarnings = new SdkWarnings()) {
        error = assertThrows(ApiErrorException.class, () -> Group.list(parameters).all());
        warnings = retriesAnnounced(sdkWarnings);
      }
      server.verify(1, request("GET", groups));

      FilesClient.setProperty("upstreamMaxAttempts", "");
      try (SdkWarnings sdkWarnings = new SdkWarnings()) {
        defaultError = assertThrows(ApiErrorException.class, () -> Group.list(parameters).all());
        defaultWarnings = retriesAnnounced(sdkWarnings);
      }
    } finally {
      FilesClient.setProperty("upstreamMaxAttempts", Integer.toString(attempts));
      FilesClient.setProperty("initialRetryDelayMillis", Integer.toString(initialDelay));
    }

    assertEquals("server-error", error.getType());
    assertEquals("final attempt", error.getError());
    assertTrue(warnings.toString(), warnings.isEmpty());

    // The default: 4 more requests, 5 in all, ending with the same error after 3 announced retries.
    server.verify(5, request("GET", groups));
    assertEquals("server-error", defaultError.getType());
    assertEquals("final attempt", defaultError.getError());
    assertEquals(defaultWarnings.toString(), 3, defaultWarnings.size());
    for (int retry = 1; retry <= 3; retry++) {
      assertTrue(defaultWarnings.toString(), defaultWarnings.get(retry - 1).contains("(retry " + retry + " of 3)"));
    }
  }

  @Test(timeout = 30000)
  public void theDefaultClientReadsItsRetryLimitBeforeEachAttempt() throws Exception {
    // A GET answered with a retryable 500 every time. The call starts allowed 5 retries, each after
    // a 2-second wait; during its first wait the limit drops to 1, so the call ends after its
    // second attempt instead of its sixth. A client built with its own limit keeps it.
    useTestServer();
    String groups = "/api/rest/v1/groups?per_page=1";
    server.stub("GET", groups, response(500)
        .withHeader("Content-Type", "application/json")
        .withBody("{\"type\":\"server-error\",\"error\":\"keeps failing\"}"));
    HashMap<String, Object> parameters = new HashMap<>();
    parameters.put("per_page", 1);
    int initialDelay = FilesConfig.getInstance().getInitialRetryDelayMillis();
    ExecutorService executor = Executors.newSingleThreadExecutor(task -> {
      Thread thread = new Thread(task, "default-client-caller");
      thread.setDaemon(true);
      return thread;
    });
    try {
      FilesClient.setProperty("maxNetworkRetries", "5");
      FilesClient.setProperty("initialRetryDelayMillis", "2000");
      Future<ApiErrorException> call = executor.submit(
          () -> assertThrows(ApiErrorException.class, () -> Group.list(parameters).all()));
      while (server.count(request("GET", groups)) < 1) {
        Thread.sleep(20);
      }
      FilesClient.setProperty("maxNetworkRetries", "1");

      assertEquals("keeps failing", call.get(20, TimeUnit.SECONDS).getError());
      server.verify(2, request("GET", groups));

      // A built client read nothing from FilesConfig: it keeps its own limit of 2 retries.
      server.stub("GET", "/api/rest/v1/groups?per_page=2", response(500)
          .withHeader("Content-Type", "application/json")
          .withBody("{\"type\":\"server-error\",\"error\":\"keeps failing\"}"));
      HashMap<String, Object> perPageTwo = new HashMap<>();
      perPageTwo.put("per_page", 2);
      try (FilesClient client = FilesClient.builder().apiKey("client-key").apiRoot(server.baseUrl())
               .maxRetries(2).retryDelay(java.time.Duration.ofMillis(1)).build()) {
        FilesClient.setProperty("maxNetworkRetries", "0");
        assertThrows(ApiErrorException.class, () -> client.groups().list(perPageTwo, null).all());
      }
      server.verify(3, request("GET", "/api/rest/v1/groups?per_page=2"));
      executor.shutdown();
      assertTrue(executor.awaitTermination(10, TimeUnit.SECONDS));
    } finally {
      FilesClient.setProperty("maxNetworkRetries", "");
      FilesClient.setProperty("initialRetryDelayMillis", Integer.toString(initialDelay));
      executor.shutdownNow();
      executor.awaitTermination(10, TimeUnit.SECONDS);
    }
  }

  @Test
  public void pagesWithTheCursorFromXFilesCursorNext() throws Exception {
    // The first page gives the next page's cursor only in X-Files-Cursor-Next, and the second page
    // gives none. Each page answers only its own request: per_page 2 and, for the second, exactly
    // the opaque cursor. Any other request gets WireMock's 404.
    useTestServer();
    String cursor = "2:a+b&=opaque";
    postMapping(groupsPage(null, "[{\"id\":73},{\"id\":74}]", cursor));
    postMapping(groupsPage(cursor, "[{\"id\":75}]", null));

    HashMap<String, Object> parameters = new HashMap<>();
    parameters.put("per_page", 2);
    List<Group> groups = Group.list(parameters).all();

    List<Long> ids = new ArrayList<>();
    for (Group group : groups) {
      ids.add(group.getId());
    }
    assertEquals(Arrays.asList(73L, 74L, 75L), ids);
  }

  @Test
  public void reportsAResponseBodyCutShortAsAConnectionError() throws Exception {
    // A raw local server, since WireMock sends whole bodies: a 200 whose Content-Length promises 43
    // bytes, then only the 11 bytes of [{"id":73}], then a closed connection. The responder runs on
    // a daemon thread, and its Future reports whether it delivered that response or failed.
    ExecutorService executor = Executors.newSingleThreadExecutor(task -> {
      Thread thread = new Thread(task, "truncated-body-responder");
      thread.setDaemon(true);
      return thread;
    });
    try (ServerSocket listener = new ServerSocket(0, 1, InetAddress.getByName("127.0.0.1"))) {
      listener.setSoTimeout(10000);
      Callable<String> answer = () -> answerOnceWithABodyCutShort(listener);
      final Future<String> responder = executor.submit(answer);

      HashMap<String, Object> parameters = new HashMap<>();
      parameters.put("per_page", 1);
      HashMap<String, Object> options = new HashMap<>();
      options.put("api_root", "http://127.0.0.1:" + listener.getLocalPort());

      ApiErrorException.ApiConnectionException error = assertThrows(
          ApiErrorException.ApiConnectionException.class,
          () -> Group.list(parameters, options).all());

      // The responder answered this request with the whole truncated response and closed the
      // connection; get rethrows any failure of its own.
      assertEquals("GET /api/rest/v1/groups?per_page=1 HTTP/1.1", responder.get(10, TimeUnit.SECONDS));
      // The read failure is the cause. The message names the request's method and path, without
      // its query or host, and does not repeat the failure's own message.
      assertTrue(String.valueOf(error.getCause()), error.getCause() instanceof IOException);
      assertEquals("The Files.com API response could not be read (response to GET /api/rest/v1/groups)", error.getMessage());
      executor.shutdown();
      assertTrue(executor.awaitTermination(10, TimeUnit.SECONDS));
    } finally {
      // On every exit, without masking an earlier failure: stop the responder and wait for it. Its
      // accept and reads time out, and closing the listener ends a pending accept.
      executor.shutdownNow();
      executor.awaitTermination(10, TimeUnit.SECONDS);
    }
  }

  @Test
  public void namesTheOperationWhenTheLastAttemptTimesOut() throws Exception {
    // A raw local server that reads the request and never answers, so reading the response times
    // out. The request goes through the SDK's own executor with one attempt, so the timeout ends
    // the last attempt; only its read timeout is shortened, for this request alone, to 2 seconds,
    // which still leaves the responder time to read the request first. The responder runs on a
    // daemon thread, and its Future reports whether it received the request and saw the client give
    // up on the connection, or failed.
    ExecutorService executor = Executors.newSingleThreadExecutor(task -> {
      Thread thread = new Thread(task, "silent-responder");
      thread.setDaemon(true);
      return thread;
    });
    int attempts = FilesConfig.getInstance().getUpstreamMaxAttempts();
    try (ServerSocket listener = new ServerSocket(0, 1, InetAddress.getByName("127.0.0.1"))) {
      listener.setSoTimeout(10000);
      Callable<String> answer = () -> answerNothingUntilTheClientGivesUp(listener);
      final Future<String> responder = executor.submit(answer);

      FilesClient.setProperty("upstreamMaxAttempts", "1");
      String url = "http://127.0.0.1:" + listener.getLocalPort() + "/api/rest/v1/groups?per_page=1";
      HttpGet request = new HttpGet(url);
      request.setConfig(RequestConfig.custom()
          .setConnectTimeout(10000)
          .setConnectionRequestTimeout(10000)
          .setSocketTimeout(2000)
          .build());

      ApiErrorException.ApiConnectionException error = assertThrows(
          ApiErrorException.ApiConnectionException.class,
          () -> FilesHttpClient.getInstance().getHttpExecutor().executeWithRetry(request));

      // The responder received this request, sent nothing back and saw the client end the
      // connection; get rethrows any failure of its own.
      assertEquals("GET /api/rest/v1/groups?per_page=1 HTTP/1.1", responder.get(10, TimeUnit.SECONDS));
      // The kind of failure, then the request's method and path, without its query or host.
      assertEquals("The request failed: a time limit ran out (SocketTimeoutException) (response to GET /api/rest/v1/groups)",
          error.getMessage());
      executor.shutdown();
      assertTrue(executor.awaitTermination(10, TimeUnit.SECONDS));
    } finally {
      // On every exit, without masking an earlier failure: restore the attempts, then stop the
      // responder and wait for it. Its accept and reads time out, and closing the listener ends a
      // pending accept.
      FilesClient.setProperty("upstreamMaxAttempts", Integer.toString(attempts));
      executor.shutdownNow();
      executor.awaitTermination(10, TimeUnit.SECONDS);
    }
  }

  @Test
  public void buildsItsClientsWithThirtySecondConnectAndEightySecondReadTimeoutsByDefault() throws Exception {
    // With both timeouts unset, the default client waits 30 seconds to connect and 80 seconds for
    // data; the wait for a pooled connection is 10 seconds. 0 means no limit, and a negative value
    // leaves the limit to the system default. A built client has the same defaults.
    int connect = FilesConfig.getInstance().getConnectTimeoutMillis();
    int read = FilesConfig.getInstance().getReadTimeoutMillis();
    ClientRuntime.Settings defaults;
    ClientRuntime.Settings unlimited;
    try {
      FilesClient.setProperty("connectTimeoutMillis", "");
      FilesClient.setProperty("readTimeoutMillis", "");
      defaults = FilesClient.defaultClient().runtime().settings();
      FilesClient.setProperty("connectTimeoutMillis", "0");
      FilesClient.setProperty("readTimeoutMillis", "-1");
      unlimited = FilesClient.defaultClient().runtime().settings();
    } finally {
      FilesClient.setProperty("connectTimeoutMillis", Integer.toString(connect));
      FilesClient.setProperty("readTimeoutMillis", Integer.toString(read));
    }

    assertEquals(java.time.Duration.ofSeconds(30), defaults.connectTimeout());
    assertEquals(java.time.Duration.ofSeconds(80), defaults.readTimeout());
    assertEquals(java.time.Duration.ofSeconds(10), defaults.connectionAcquisitionTimeout());
    assertEquals(java.time.Duration.ZERO, unlimited.connectTimeout());
    assertTrue(unlimited.readTimeout().isNegative());
    try (FilesClient client = FilesClient.builder().build()) {
      ClientRuntime.Settings built = client.runtime().settings();
      assertEquals(java.time.Duration.ofSeconds(30), built.connectTimeout());
      assertEquals(java.time.Duration.ofSeconds(80), built.readTimeout());
      assertEquals(java.time.Duration.ofSeconds(10), built.connectionAcquisitionTimeout());
    }
  }

  @Test
  public void readsResponsesWithTheConfiguredReadTimeout() throws Exception {
    // The default client, with the timeouts set before the call. It sends a request, with one
    // attempt, to a raw local server that reads the request and never answers, so the configured
    // 2-second read timeout, not the default, ends the request. The responder runs on a daemon
    // thread, and its Future reports whether it received the request and saw the client give up on
    // the connection, or failed.
    ExecutorService executor = Executors.newSingleThreadExecutor(task -> {
      Thread thread = new Thread(task, "silent-responder");
      thread.setDaemon(true);
      return thread;
    });
    int connect = FilesConfig.getInstance().getConnectTimeoutMillis();
    int read = FilesConfig.getInstance().getReadTimeoutMillis();
    int attempts = FilesConfig.getInstance().getUpstreamMaxAttempts();
    try (ServerSocket listener = new ServerSocket(0, 1, InetAddress.getByName("127.0.0.1"))) {
      listener.setSoTimeout(10000);
      Callable<String> answer = () -> answerNothingUntilTheClientGivesUp(listener);
      final Future<String> responder = executor.submit(answer);

      FilesClient.setProperty("connectTimeoutMillis", "5000");
      FilesClient.setProperty("readTimeoutMillis", "2000");
      FilesClient.setProperty("upstreamMaxAttempts", "1");
      ClientRuntime.Settings settings = FilesClient.defaultClient().runtime().settings();
      assertEquals(java.time.Duration.ofMillis(5000), settings.connectTimeout());
      assertEquals(java.time.Duration.ofMillis(2000), settings.readTimeout());
      assertEquals(java.time.Duration.ofSeconds(10), settings.connectionAcquisitionTimeout());

      HashMap<String, Object> parameters = new HashMap<>();
      parameters.put("per_page", 1);
      HashMap<String, Object> options = new HashMap<>();
      options.put("api_root", "http://127.0.0.1:" + listener.getLocalPort());
      long start = System.nanoTime();
      ApiErrorException.ApiConnectionException error = assertThrows(
          ApiErrorException.ApiConnectionException.class,
          () -> Group.list(parameters, options).all());

      // The responder received this request, sent nothing back and saw the client end the
      // connection; get rethrows any failure of its own.
      assertEquals("GET /api/rest/v1/groups?per_page=1 HTTP/1.1", responder.get(10, TimeUnit.SECONDS));
      assertTrue("ended by the 2-second read timeout",
          TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start) < 10000);
      assertEquals("The request failed: a time limit ran out (SocketTimeoutException) (response to GET /api/rest/v1/groups)",
          error.getMessage());
      executor.shutdown();
      assertTrue(executor.awaitTermination(10, TimeUnit.SECONDS));
    } finally {
      // On every exit, without masking an earlier failure: restore the settings, then stop the
      // responder and wait for it.
      FilesClient.setProperty("connectTimeoutMillis", Integer.toString(connect));
      FilesClient.setProperty("readTimeoutMillis", Integer.toString(read));
      FilesClient.setProperty("upstreamMaxAttempts", Integer.toString(attempts));
      executor.shutdownNow();
      executor.awaitTermination(10, TimeUnit.SECONDS);
    }
  }

  @Test
  public void triesAnUnsupportedSchemeOnlyOnce() throws Exception {
    // An ftp URL with a port: Apache plans the route, then finds no socket factory for ftp when it
    // connects and throws UnsupportedSchemeException. The default client sends the request over a
    // connection pool that counts connects and hands each one to Apache's implementation. With
    // retries allowed, a retry would connect again.
    assertTrue("the default client must be allowed retries", FilesConfig.getInstance().getMaxNetworkRetries() > 0);
    AtomicInteger connects = new AtomicInteger();
    List<IOException> connectFailures = new CopyOnWriteArrayList<>();
    PoolingHttpClientConnectionManager counting = new PoolingHttpClientConnectionManager() {
      @Override
      public void connect(HttpClientConnection connection, HttpRoute route, int connectTimeout, HttpContext context)
          throws IOException {
        connects.incrementAndGet();
        try {
          super.connect(connection, route, connectTimeout, context);
        } catch (IOException e) {
          connectFailures.add(e);
          throw e;
        }
      }
    };
    PoolingHttpClientConnectionManager shared = FilesClient.connectionManager;
    // The port is one this test owns; nothing connects to it.
    try (ServerSocket listener = new ServerSocket(0, 1, InetAddress.getByName("127.0.0.1"))) {
      FilesClient.connectionManager = counting;
      String url = "ftp://127.0.0.1:" + listener.getLocalPort() + "/api/rest/v1/groups?per_page=1";

      ApiErrorException.ApiConnectionException error = assertThrows(
          ApiErrorException.ApiConnectionException.class,
          () -> FilesClient.apiRequest(url, HttpMethods.RequestMethods.GET, null, new HashMap<>()));

      // One connect, which Apache failed for the scheme: the client did not retry.
      assertEquals(1, connects.get());
      assertEquals(1, connectFailures.size());
      assertTrue(connectFailures.get(0).toString(), connectFailures.get(0) instanceof UnsupportedSchemeException);
      assertEquals("The request failed: the URL's scheme is not supported (UnsupportedSchemeException)"
          + " (response to GET /api/rest/v1/groups)", error.getMessage());
    } finally {
      FilesClient.connectionManager = shared;
      counting.close();
    }
  }

  @Test
  public void triesAnUnsupportedSchemeWithoutAPortOnlyOnce() throws Exception {
    // An ftp URL without a port: while planning the route, before any connection, Apache finds no
    // default port for ftp and throws a ClientProtocolException without a message, caused by an
    // HttpException saying the protocol is not supported. The executor sends it through a client
    // that counts its calls and passes each one to an Apache client that does not retry on its own.
    // With more than one attempt allowed, a retry would call it again.
    assertTrue("the executor must be allowed retries", FilesConfig.getInstance().getMaxNetworkRetries() > 0);
    try (CountingClient client = new CountingClient(HttpClients.custom().disableAutomaticRetries().build())) {
      HttpGet request = new HttpGet("ftp://127.0.0.1/api/rest/v1/groups?per_page=1");

      ApiErrorException.ApiConnectionException error = assertThrows(
          ApiErrorException.ApiConnectionException.class,
          () -> new FilesHttpExecutor(client).executeWithRetry(request));

      // One call, which Apache failed for the scheme: the executor did not try again.
      assertEquals(1, client.calls.get());
      assertEquals(1, client.failures.size());
      IOException failure = client.failures.get(0);
      assertTrue(failure.toString(), failure instanceof ClientProtocolException);
      assertTrue(String.valueOf(failure.getCause()), failure.getCause() instanceof HttpException);
      assertEquals("The request failed: the URL's scheme is not supported (ClientProtocolException)"
          + " (response to GET /api/rest/v1/groups)", error.getMessage());
    }
  }

  @Test
  public void triesAUrlWithoutAHostOnlyOnce() throws Exception {
    // An http URL without a host: Apache's client rejects it in execute, before it plans a route or
    // sends anything, with a ClientProtocolException naming the URL. That is before doExecute, so
    // this counts the executor's execute calls instead, on a client that passes each one to the
    // execute of an Apache client that does not retry on its own. With more than one attempt
    // allowed, a retry would call it again.
    assertTrue("the executor must be allowed retries", FilesConfig.getInstance().getMaxNetworkRetries() > 0);
    try (ExecuteCountingClient client = new ExecuteCountingClient(HttpClients.custom().disableAutomaticRetries().build())) {
      HttpGet request = new HttpGet("http:///api/rest/v1/groups?per_page=1");

      ApiErrorException.ApiConnectionException error = assertThrows(
          ApiErrorException.ApiConnectionException.class,
          () -> new FilesHttpExecutor(client).executeWithRetry(request));

      // One call, which Apache rejected for the URL: the executor did not try again.
      assertEquals(1, client.calls.get());
      assertEquals(1, client.failures.size());
      IOException failure = client.failures.get(0);
      assertTrue(failure.toString(), failure instanceof ClientProtocolException);
      assertEquals("The request failed: the request could not be sent as written (ClientProtocolException)"
          + " (response to GET /api/rest/v1/groups)", error.getMessage());
    }
  }

  @Test
  public void triesAnUntrustedServerCertificateOnlyOnce() throws Exception {
    // Three local TLS failures, with two attempts allowed. Each request goes through a
    // CountingClient over a real Apache client that does not retry on its own, which records the
    // native failure.
    // - A server that answers the TLS hello with a handshake_failure alert: a handshake failure
    //   that is not about the certificate, so the executor still retries it.
    // - A server whose self-signed certificate the JDK's default trust store does not trust.
    // - That certificate, trusted alone by a test client, presented for 127.0.0.1, a host it does
    //   not name; Apache's ordinary host-name check rejects it. This failure reaches the executor
    //   wrapped in eleven IOExceptions, as a client passed to the executor may wrap it.
    // A certificate failure fails the same way every time, so the executor tries each only once.
    KeyStore serverKeys = selfSignedServerKeys();
    ExecutorService executor = Executors.newFixedThreadPool(3, task -> {
      Thread thread = new Thread(task, "tls-responder");
      thread.setDaemon(true);
      return thread;
    });
    int attempts = FilesConfig.getInstance().getUpstreamMaxAttempts();
    SocketAnswer handshake = socket -> ((SSLSocket) socket).startHandshake();
    RequestConfig bounded = RequestConfig.custom().setConnectTimeout(10000).setSocketTimeout(10000).build();
    try (ServerSocket alerting = new ServerSocket(0, 50, InetAddress.getByName("127.0.0.1"));
         ServerSocket untrusted = tlsListener(serverKeys);
         ServerSocket misnamed = tlsListener(serverKeys);
         CloseableHttpClient defaultTrust = HttpClients.custom()
             .disableAutomaticRetries().setDefaultRequestConfig(bounded).build();
         CloseableHttpClient trustingOnlyThatCertificate = HttpClients.custom()
             .disableAutomaticRetries().setSSLContext(trustingOnly(serverKeys)).setDefaultRequestConfig(bounded).build()) {
      Callable<Integer> countAlerting = () -> countConnections(alerting, FilesApiTest::answerTlsHelloWithHandshakeFailure);
      Callable<Integer> countUntrusted = () -> countConnections(untrusted, handshake);
      Callable<Integer> countMisnamed = () -> countConnections(misnamed, handshake);
      final Future<Integer> alertingConnections = executor.submit(countAlerting);
      final Future<Integer> untrustedConnections = executor.submit(countUntrusted);
      final Future<Integer> misnamedConnections = executor.submit(countMisnamed);
      FilesClient.setProperty("upstreamMaxAttempts", "2");
      CountingClient alertingClient = new CountingClient(defaultTrust);
      CountingClient untrustedClient = new CountingClient(defaultTrust);
      CountingClient misnamedClient = new CountingClient(trustingOnlyThatCertificate, 11);
      HttpGet toAlerting = new HttpGet("https://127.0.0.1:" + alerting.getLocalPort() + "/api/rest/v1/groups?per_page=1");
      HttpGet toUntrusted = new HttpGet("https://127.0.0.1:" + untrusted.getLocalPort() + "/api/rest/v1/groups?per_page=1");
      HttpGet toMisnamed = new HttpGet("https://127.0.0.1:" + misnamed.getLocalPort() + "/api/rest/v1/groups?per_page=1");

      final ApiErrorException.ApiConnectionException alertingError = assertThrows(ApiErrorException.ApiConnectionException.class,
          () -> new FilesHttpExecutor(alertingClient).executeWithRetry(toAlerting));
      final ApiErrorException.ApiConnectionException untrustedError = assertThrows(ApiErrorException.ApiConnectionException.class,
          () -> new FilesHttpExecutor(untrustedClient).executeWithRetry(toUntrusted));
      final ApiErrorException.ApiConnectionException misnamedError = assertThrows(ApiErrorException.ApiConnectionException.class,
          () -> new FilesHttpExecutor(misnamedClient).executeWithRetry(toMisnamed));

      // Closing each listener ends its count; get rethrows a failure of its accept loop. Each
      // server saw exactly the client's calls, so no attempt was repeated beneath them.
      stopAccepting(alerting);
      stopAccepting(untrusted);
      stopAccepting(misnamed);
      assertEquals(Integer.valueOf(2), alertingConnections.get(10, TimeUnit.SECONDS));
      assertEquals(Integer.valueOf(1), untrustedConnections.get(10, TimeUnit.SECONDS));
      assertEquals(Integer.valueOf(1), misnamedConnections.get(10, TimeUnit.SECONDS));

      // The alert: two calls, each failing natively with an SSLHandshakeException without a cause.
      assertEquals(2, alertingClient.calls.get());
      assertEquals(2, alertingClient.failures.size());
      for (IOException failure : alertingClient.failures) {
        assertTrue(failure.toString(), failure instanceof SSLHandshakeException && failure.getCause() == null);
      }
      // Each SDK error names the kind of failure, never its message, then the request's method and
      // path.
      assertEquals("The request failed: the TLS connection failed (SSLHandshakeException)"
          + " (response to GET /api/rest/v1/groups)", alertingError.getMessage());

      // The untrusted certificate: one call, failing with an SSLHandshakeException caused by a
      // CertificateException.
      assertEquals(1, untrustedClient.calls.get());
      IOException untrustedFailure = untrustedClient.failures.get(0);
      assertTrue(untrustedFailure.toString(), untrustedFailure instanceof SSLHandshakeException
          && untrustedFailure.getCause() instanceof CertificateException);
      assertEquals("The request failed: the server's certificate or host name did not verify (SSLHandshakeException)"
          + " (response to GET /api/rest/v1/groups)", untrustedError.getMessage());

      // The host-name mismatch: one call, failing with an SSLPeerUnverifiedException, which the
      // executor saw inside the wrappers.
      assertEquals(1, misnamedClient.calls.get());
      IOException misnamedFailure = misnamedClient.failures.get(0);
      assertTrue(misnamedFailure.toString(), misnamedFailure instanceof SSLPeerUnverifiedException);
      assertEquals("The request failed: the server's certificate or host name did not verify (IOException)"
          + " (response to GET /api/rest/v1/groups)", misnamedError.getMessage());
      executor.shutdown();
      assertTrue(executor.awaitTermination(10, TimeUnit.SECONDS));
    } finally {
      // On every exit, without masking an earlier failure: restore the attempts, then stop the
      // servers and wait for them. Closing the listeners ends their accept loops; the test clients
      // are closed with them.
      FilesClient.setProperty("upstreamMaxAttempts", Integer.toString(attempts));
      executor.shutdownNow();
      executor.awaitTermination(10, TimeUnit.SECONDS);
    }
  }

  @Test
  public void triesExhaustedRedirectsOnlyOnce() throws Exception {
    // Three protocol failures, with two attempts allowed. Each request goes through a
    // CountingClient over an Apache client that does not retry on its own, which records the native
    // failure. Apache wraps each failure in a ClientProtocolException without a message of its own.
    // - A redirect chain longer than this request's limit of 2: Apache stops with a
    //   RedirectException.
    // - A path that redirects to itself: Apache stops with a CircularRedirectException. This
    //   failure reaches the executor wrapped in two IOExceptions, as a client passed to the
    //   executor may wrap it.
    // - A raw local server that answers without an HTTP status line: Apache stops with a
    //   ProtocolException that is not about redirects, so the executor still retries it.
    ExecutorService executor = Executors.newSingleThreadExecutor(task -> {
      Thread thread = new Thread(task, "garbled-responder");
      thread.setDaemon(true);
      return thread;
    });
    int attempts = FilesConfig.getInstance().getUpstreamMaxAttempts();
    try (ServerSocket garbled = new ServerSocket(0, 50, InetAddress.getByName("127.0.0.1"));
         CloseableHttpClient apacheClient = HttpClients.custom().disableAutomaticRetries().build()) {
      Callable<Integer> countGarbled = () -> countConnections(garbled, FilesApiTest::answerWithoutAStatusLine);
      final Future<Integer> garbledConnections = executor.submit(countGarbled);
      server.stub("GET", "/api/rest/v1/redirect-hop-1", response(302)
          .withHeader("Location", server.url("/api/rest/v1/redirect-hop-2")));
      server.stub("GET", "/api/rest/v1/redirect-hop-2", response(302)
          .withHeader("Location", server.url("/api/rest/v1/redirect-hop-3")));
      server.stub("GET", "/api/rest/v1/redirect-hop-3", response(302)
          .withHeader("Location", server.url("/api/rest/v1/redirect-hop-4")));
      server.stub("GET", "/api/rest/v1/redirect-loop", response(302)
          .withHeader("Location", server.url("/api/rest/v1/redirect-loop")));
      FilesClient.setProperty("upstreamMaxAttempts", "2");
      CountingClient chainClient = new CountingClient(apacheClient);
      CountingClient loopClient = new CountingClient(apacheClient, 2);
      CountingClient garbledClient = new CountingClient(apacheClient);
      HttpGet toChain = new HttpGet(server.url("/api/rest/v1/redirect-hop-1"));
      // A redirect limit for this request alone; its timeouts bound the fixture.
      toChain.setConfig(RequestConfig.custom().setMaxRedirects(2)
          .setConnectTimeout(10000).setSocketTimeout(10000).setConnectionRequestTimeout(10000).build());
      HttpGet toLoop = new HttpGet(server.url("/api/rest/v1/redirect-loop"));
      HttpGet toGarbled = new HttpGet("http://127.0.0.1:" + garbled.getLocalPort() + "/api/rest/v1/groups");

      final ApiErrorException.ApiConnectionException chainError = assertThrows(ApiErrorException.ApiConnectionException.class,
          () -> new FilesHttpExecutor(chainClient).executeWithRetry(toChain));
      final ApiErrorException.ApiConnectionException loopError = assertThrows(ApiErrorException.ApiConnectionException.class,
          () -> new FilesHttpExecutor(loopClient).executeWithRetry(toLoop));
      final ApiErrorException.ApiConnectionException garbledError = assertThrows(ApiErrorException.ApiConnectionException.class,
          () -> new FilesHttpExecutor(garbledClient).executeWithRetry(toGarbled));

      // Closing the listener ends its count; get rethrows a failure of its accept loop.
      stopAccepting(garbled);
      assertEquals(Integer.valueOf(2), garbledConnections.get(10, TimeUnit.SECONDS));

      // The redirect limit: one call, which followed each hop once and stopped with a
      // RedirectException.
      assertEquals(1, chainClient.calls.get());
      IOException chainFailure = chainClient.failures.get(0);
      assertTrue(chainFailure.toString(), chainFailure instanceof ClientProtocolException
          && chainFailure.getCause() instanceof RedirectException
          && !(chainFailure.getCause() instanceof CircularRedirectException));
      server.verify(1, request("GET", "/api/rest/v1/redirect-hop-1"));
      server.verify(1, request("GET", "/api/rest/v1/redirect-hop-2"));
      server.verify(1, request("GET", "/api/rest/v1/redirect-hop-3"));
      server.verify(0, request("GET", "/api/rest/v1/redirect-hop-4"));
      assertEquals("The request failed: the redirects looped or went past the limit (ClientProtocolException)"
          + " (response to GET /api/rest/v1/redirect-hop-1)", chainError.getMessage());

      // The loop: one call, which followed the redirect once and stopped with a
      // CircularRedirectException; the executor saw it inside the wrappers.
      assertEquals(1, loopClient.calls.get());
      IOException loopFailure = loopClient.failures.get(0);
      assertTrue(loopFailure.toString(), loopFailure instanceof ClientProtocolException
          && loopFailure.getCause() instanceof CircularRedirectException);
      server.verify(2, request("GET", "/api/rest/v1/redirect-loop"));
      assertEquals("The request failed: the redirects looped or went past the limit (IOException)"
          + " (response to GET /api/rest/v1/redirect-loop)", loopError.getMessage());

      // The response without a status line: two calls, each stopped by a ProtocolException that is
      // not a RedirectException.
      assertEquals(2, garbledClient.calls.get());
      assertEquals(2, garbledClient.failures.size());
      for (IOException failure : garbledClient.failures) {
        assertTrue(failure.toString(), failure instanceof ClientProtocolException
            && failure.getCause() instanceof ProtocolException
            && !(failure.getCause() instanceof RedirectException));
      }
      assertEquals("The request failed: the HTTP exchange failed (ClientProtocolException)"
          + " (response to GET /api/rest/v1/groups)", garbledError.getMessage());
      executor.shutdown();
      assertTrue(executor.awaitTermination(10, TimeUnit.SECONDS));
    } finally {
      // On every exit, without masking an earlier failure: restore the attempts, then stop the
      // responder and wait for it. Closing the listener ends its accept loop.
      FilesClient.setProperty("upstreamMaxAttempts", Integer.toString(attempts));
      executor.shutdownNow();
      executor.awaitTermination(10, TimeUnit.SECONDS);
    }
  }

  @Test
  public void waitsForACopyOnlyWhenAskedWithTheCopysOptions() throws Exception {
    // The copy is accepted with a pending file migration. File.copy returns at once without asking
    // about the migration; waitForCompletion then polls it until it completes. The copy's api_root
    // option names this server while the global one names another, so the polls reach this server
    // only if they use the copy's options.
    server.stub("POST", "/api/rest/v1/file_actions/copy/reports", response(201)
        .withHeader("Content-Type", "application/json")
        .withBody("{\"status\":\"pending\",\"file_migration_id\":7}"));
    stubFirstThen("/api/rest/v1/file_migrations/7",
        200, "{\"id\":7,\"operation\":\"copy\",\"status\":\"processing\",\"files_moved\":1,\"files_total\":3}",
        200, "{\"id\":7,\"operation\":\"copy\",\"status\":\"completed\",\"files_moved\":3,\"files_total\":3}");
    FilesClient.setProperty("apiRoot", secondOrigin.baseUrl());
    HashMap<String, Object> parameters = new HashMap<>();
    parameters.put("destination", "archive/reports");
    HashMap<String, Object> options = new HashMap<>();
    options.put("api_root", server.baseUrl());

    FileAction action = File.copy("reports", parameters, options);

    assertEquals("pending", action.getStatus());
    assertEquals(Long.valueOf(7), action.getFileMigrationId());
    server.verify(0, request("GET", "/api/rest/v1/file_migrations/7"));

    FileMigration migration = action.waitForCompletion(30, TimeUnit.SECONDS);

    assertEquals("completed", migration.getStatus());
    assertEquals(Long.valueOf(3), migration.getFilesMoved());
    server.verify(2, request("GET", "/api/rest/v1/file_migrations/7"));
  }

  @Test
  public void waitsForACopyWithTheClientThatReturnedIt() throws Exception {
    // A copy made with a built client, whose credentials differ from the static ones: the wait
    // asks about the migration with that client.
    server.stub("POST", "/api/rest/v1/file_actions/copy/reports", response(201)
        .withHeader("Content-Type", "application/json")
        .withBody("{\"status\":\"pending\",\"file_migration_id\":11}"));
    server.stub("GET", "/api/rest/v1/file_migrations/11", response(200)
        .withHeader("Content-Type", "application/json")
        .withBody("{\"id\":11,\"operation\":\"copy\",\"status\":\"completed\",\"files_moved\":1,\"files_total\":1}"));
    HashMap<String, Object> parameters = new HashMap<>();
    parameters.put("destination", "archive/reports");
    FileMigration migration;
    try (FilesClient client = FilesClient.builder().apiKey("client-key").apiRoot(server.baseUrl()).build()) {
      migration = client.files().copy("reports", parameters, null).waitForCompletion(30, TimeUnit.SECONDS);
    }

    assertEquals("completed", migration.getStatus());
    server.verify(1, request("GET", "/api/rest/v1/file_migrations/11").withHeader("X-FilesAPI-Key", "client-key"));
  }

  @Test
  public void reportsAMoveWhoseMigrationFailed() throws Exception {
    useTestServer();
    server.stub("POST", "/api/rest/v1/file_actions/move/inbox", response(201)
        .withHeader("Content-Type", "application/json")
        .withBody("{\"status\":\"pending\",\"file_migration_id\":8}"));
    server.stub("GET", "/api/rest/v1/file_migrations/8", response(200)
        .withHeader("Content-Type", "application/json")
        .withBody("{\"id\":8,\"operation\":\"move\",\"status\":\"failed\",\"failure_message\":\"Destination is read-only\"}"));
    HashMap<String, Object> parameters = new HashMap<>();
    parameters.put("destination", "archive/inbox");
    FileAction action = File.move("inbox", parameters);

    FileAction.UnsuccessfulMigrationException error = assertThrows(
        FileAction.UnsuccessfulMigrationException.class,
        () -> action.waitForCompletion(30, TimeUnit.SECONDS));

    assertEquals("failed", error.getFileMigration().getStatus());
    assertEquals("Destination is read-only", error.getFileMigration().getFailureMessage());
    assertEquals("File migration 8 ended with status failed: Destination is read-only", error.getMessage());
    server.verify(1, request("GET", "/api/rest/v1/file_migrations/8"));
  }

  @Test
  public void externalDestinationCopiesAndMovesSendTheBaseRequestOfTheirNormalizedPaths() throws Exception {
    // The calls' api_root option names another server than the global one, so their requests reach it
    // only if the helpers pass the options on.
    WireMockServer target = loopbackServer();
    target.start();
    try {
      HashMap<String, Object> options = new HashMap<>();
      options.put("api_root", "http://localhost:" + target.port());
      HashMap<String, Object> parameters = new HashMap<>();
      parameters.put("overwrite", true);
      // Both slash kinds separate folders, and empty, . and .. components are dropped.
      String destinationPath = "/archive\\./2026/../q1";

      assertDestinationActionAsBase(target, "copy", "remote-server", "_/RemoteServers/42/archive/2026/q1", parameters, options,
          () -> File.copyToRemoteServer("remote-server", 42L, destinationPath, parameters, options));
      assertDestinationActionAsBase(target, "copy", "snapshot", "_/Snapshots/7/archive/2026/q1", parameters, options,
          () -> File.copyToSnapshot("snapshot", 7L, destinationPath, parameters, options));
      assertDestinationActionAsBase(target, "copy", "child-site", "_/Sites/3/archive/2026/q1", parameters, options,
          () -> File.copyToChildSite("child-site", 3L, destinationPath, parameters, options));
      assertDestinationActionAsBase(target, "move", "remote-server", "_/RemoteServers/42/archive/2026/q1", parameters, options,
          () -> File.moveToRemoteServer("remote-server", 42L, destinationPath, parameters, options));
      assertDestinationActionAsBase(target, "move", "snapshot", "_/Snapshots/7/archive/2026/q1", parameters, options,
          () -> File.moveToSnapshot("snapshot", 7L, destinationPath, parameters, options));
      assertDestinationActionAsBase(target, "move", "child-site", "_/Sites/3/archive/2026/q1", parameters, options,
          () -> File.moveToChildSite("child-site", 3L, destinationPath, parameters, options));

      assertEquals("the parameters passed are not changed", Collections.singletonMap("overwrite", true), parameters);
    } finally {
      target.stop();
    }
  }

  @Test
  public void aFilesExternalDestinationCopiesAndMovesRunWithTheClientThatFoundIt() throws Exception {
    // The file is found with a built client whose credentials and API root are not the global ones:
    // its copies and moves, and the waits for their migrations, use that client.
    WireMockServer bound = loopbackServer();
    bound.start();
    try (FilesClient client = FilesClient.builder().apiKey("client-key").apiRoot("http://localhost:" + bound.port()).build()) {
      bound.stubFor(get(urlEqualTo("/api/rest/v1/file_actions/metadata/reports"))
          .willReturn(destinationAnswer(200, "{\"path\":\"reports\",\"type\":\"file\"}")));
      bound.stubFor(post(urlEqualTo("/api/rest/v1/file_actions/copy/reports"))
          .willReturn(destinationAnswer(201, "{\"status\":\"pending\",\"file_migration_id\":11}")));
      bound.stubFor(post(urlEqualTo("/api/rest/v1/file_actions/move/reports"))
          .willReturn(destinationAnswer(201, "{\"status\":\"pending\",\"file_migration_id\":12}")));
      bound.stubFor(get(urlEqualTo("/api/rest/v1/file_migrations/11"))
          .willReturn(destinationAnswer(200, "{\"id\":11,\"operation\":\"copy\",\"status\":\"completed\"}")));
      bound.stubFor(get(urlEqualTo("/api/rest/v1/file_migrations/12"))
          .willReturn(destinationAnswer(200, "{\"id\":12,\"operation\":\"move\",\"status\":\"completed\"}")));
      HashMap<String, Object> parameters = new HashMap<>();
      parameters.put("overwrite", true);

      // Each action starts from a file just found, so that none depends on what an earlier one did.
      List<FileAction> actions = Arrays.asList(
          client.files().find("reports", null, null).copyToRemoteServer(42L, "/archive\\./2026/../q1", parameters),
          client.files().find("reports", null, null).copyToSnapshot(7L, "archive//q1", parameters),
          client.files().find("reports", null, null).copyToChildSite(3L, "../../q1", parameters),
          client.files().find("reports", null, null).moveToRemoteServer(42L, "archive/q1"),
          client.files().find("reports", null, null).moveToSnapshot(7L, "\\archive\\q1"),
          client.files().find("reports", null, null).moveToChildSite(3L, "./archive/./q1/"));
      for (FileAction action : actions) {
        assertEquals("completed", action.waitForCompletion(30, TimeUnit.SECONDS).getStatus());
      }

      assertEquals(Arrays.asList("_/RemoteServers/42/archive/2026/q1", "_/Sites/3/q1", "_/Snapshots/7/archive/q1"),
          boundDestinations(bound, "copy", true));
      assertEquals(Arrays.asList("_/RemoteServers/42/archive/q1", "_/Sites/3/archive/q1", "_/Snapshots/7/archive/q1"),
          boundDestinations(bound, "move", false));
      List<LoggedRequest> finds = bound.findAll(getRequestedFor(urlEqualTo("/api/rest/v1/file_actions/metadata/reports")));
      assertEquals(6, finds.size());
      for (LoggedRequest find : finds) {
        assertEquals("client-key", find.getHeader("X-FilesAPI-Key"));
      }
      for (String migration : Arrays.asList("11", "12")) {
        List<LoggedRequest> polls = bound.findAll(getRequestedFor(urlEqualTo("/api/rest/v1/file_migrations/" + migration)));
        assertEquals("one wait for each of three actions", 3, polls.size());
        for (LoggedRequest poll : polls) {
          assertEquals("client-key", poll.getHeader("X-FilesAPI-Key"));
        }
      }
      assertEquals("nothing goes to the global API root", 0,
          wireMockServer.findAll(postRequestedFor(urlEqualTo("/api/rest/v1/file_actions/copy/reports"))).size()
              + wireMockServer.findAll(postRequestedFor(urlEqualTo("/api/rest/v1/file_actions/move/reports"))).size());
    } finally {
      bound.stop();
    }
  }

  // Runs a destination helper, then the base call it stands for, File.copy or File.move with the full
  // destination path and the same parameters and options: the two requests must be the same, sent
  // where the options say, with the destination normalized and the other parameters as given.
  private void assertDestinationActionAsBase(WireMockServer target, String action, String path, String destination,
      HashMap<String, Object> parameters, HashMap<String, Object> options, Callable<FileAction> helper) throws Exception {
    String url = "/api/rest/v1/file_actions/" + action + "/" + path;
    target.stubFor(post(urlEqualTo(url)).willReturn(destinationAnswer(201, "{\"status\":\"completed\"}")));
    assertEquals("completed", helper.call().getStatus());
    HashMap<String, Object> full = new HashMap<>(parameters);
    full.put("destination", destination);
    FileAction base = "copy".equals(action) ? File.copy(path, full, options) : File.move(path, full, options);
    assertEquals("completed", base.getStatus());

    List<LoggedRequest> sent = target.findAll(postRequestedFor(urlEqualTo(url)));
    assertEquals("the helper's request and the base request", 2, sent.size());
    ObjectMapper mapper = new ObjectMapper();
    JsonNode body = mapper.readTree(sent.get(0).getBodyAsString());
    assertEquals(mapper.readTree(sent.get(1).getBodyAsString()), body);
    assertEquals(destination, body.get("destination").asText());
    assertTrue("the other parameters are sent as given", body.get("overwrite").asBoolean());
    for (LoggedRequest request : sent) {
      assertEquals("test-key", request.getHeader("X-FilesAPI-Key"));
    }
    assertEquals("nothing goes to the global API root", 0, wireMockServer.findAll(postRequestedFor(urlEqualTo(url))).size());
  }

  // The destinations of the copies or moves of "reports" a server received, sorted, each sent with the
  // built client's credentials and, where they were given, the other parameters.
  private static List<String> boundDestinations(WireMockServer server, String action, boolean overwrite) throws IOException {
    List<String> destinations = new ArrayList<>();
    for (LoggedRequest request : server.findAll(postRequestedFor(urlEqualTo("/api/rest/v1/file_actions/" + action + "/reports")))) {
      assertEquals("client-key", request.getHeader("X-FilesAPI-Key"));
      JsonNode body = new ObjectMapper().readTree(request.getBodyAsString());
      assertEquals(overwrite, body.path("overwrite").asBoolean());
      destinations.add(body.get("destination").asText());
    }
    Collections.sort(destinations);
    return destinations;
  }

  private static ResponseDefinitionBuilder destinationAnswer(int status, String body) {
    return aResponse().withStatus(status).withHeader("Content-Type", "application/json").withBody(body);
  }

  @Test
  public void waitsForADeleteThatContinuesInTheBackgroundOnlyWhenAskedWithTheClientThatBeganIt() throws Exception {
    // The delete is accepted while its file migration is still processing. beginDelete returns at
    // once, without asking about the migration; awaitCompletion then polls it, with the client that
    // began the delete, and returns only once it has completed.
    server.stub("POST", "/api/rest/v1/file_actions/delete/archive", response(201)
        .withHeader("Content-Type", "application/json")
        .withBody("{\"status\":\"processing\",\"file_migration_id\":12}"));
    stubFirstThen("/api/rest/v1/file_migrations/12",
        200, "{\"id\":12,\"operation\":\"delete\",\"status\":\"processing\"}",
        200, "{\"id\":12,\"operation\":\"delete\",\"status\":\"completed\"}");
    HashMap<String, Object> parameters = new HashMap<>();
    parameters.put("recursive", true);
    try (FilesClient client = FilesClient.builder().apiKey("client-key").apiRoot(server.baseUrl()).build()) {
      FileAction action = client.files().beginDelete("archive", parameters, null);

      assertEquals("processing", action.getStatus());
      assertEquals(Long.valueOf(12), action.getFileMigrationId());
      server.verify(0, request("GET", "/api/rest/v1/file_migrations/12"));

      action.awaitCompletion(30, TimeUnit.SECONDS);

      // Returned only after the migration was seen completed, so a request that follows comes after.
      server.verify(2, request("GET", "/api/rest/v1/file_migrations/12").withHeader("X-FilesAPI-Key", "client-key"));
    }
    server.verify(1, request("POST", "/api/rest/v1/file_actions/delete/archive").withHeader("X-FilesAPI-Key", "client-key"));
  }

  @Test
  public void anActionThatEndedWhenTheApiAnsweredIsJudgedByItsOwnStatusWithoutPolling() throws Exception {
    // A delete that finished at once, or had nothing to do (skipped), returns; one that failed or was
    // cancelled throws its own outcome, not a missing migration ID. Neither asks about a migration,
    // even one the action names.
    String[] paths = {"completed", "skipped", "failed", "cancelled", "completed-migration", "cancelled-migration"};
    String[] bodies = {"{\"status\":\"completed\"}", "{\"status\":\"skipped\"}", "{\"status\":\"failed\"}",
        "{\"status\":\"cancelled\"}", "{\"status\":\"completed\",\"file_migration_id\":13}",
        "{\"status\":\"cancelled\",\"file_migration_id\":14}"};
    for (int i = 0; i < paths.length; i++) {
      wireMockServer.stubFor(post(urlEqualTo("/api/rest/v1/file_actions/delete/" + paths[i]))
          .willReturn(aResponse().withStatus(201).withHeader("Content-Type", "application/json").withBody(bodies[i])));
      final FileAction action = File.beginDelete(paths[i], new HashMap<>(), null);
      if ("completed".equals(action.getStatus()) || "skipped".equals(action.getStatus())) {
        action.awaitCompletion(30, TimeUnit.SECONDS);
        assertEquals("a skipped action stays told apart from one carried out", paths[i].replace("-migration", ""), action.getStatus());
        assertThrows("the arguments are checked even so", IllegalArgumentException.class, () -> action.awaitCompletion(0, TimeUnit.SECONDS));
      } else {
        FileAction.UnsuccessfulActionException error = assertThrows(FileAction.UnsuccessfulActionException.class,
            () -> action.awaitCompletion(30, TimeUnit.SECONDS));
        assertSame(action, error.getFileAction());
        assertEquals("The file action ended with status " + action.getStatus()
            + (action.getFileMigrationId() == null ? "" : " (file migration " + action.getFileMigrationId() + ")"), error.getMessage());
      }
      if (action.getFileMigrationId() == null) {
        // The released wait keeps its contract: without a migration it has nothing to return.
        assertThrows(paths[i], IllegalStateException.class, () -> action.waitForCompletion(30, TimeUnit.SECONDS));
      }
    }
    wireMockServer.verify(0, anyRequestedFor(urlPathMatching("/api/rest/v1/file_migrations.*")));
    assertEquals(paths.length, wireMockServer.getAllServeEvents().size());
  }

  @Test
  public void anActionThatHasNotFinishedAndNamesNoMigrationIsNotTakenAsFinished() throws Exception {
    // Pending, processing, a status the SDK does not know, or none, without a file migration ID:
    // nothing shows the action finished and nothing can be polled.
    String[] bodies = {"{\"status\":\"pending\"}", "{\"status\":\"processing\"}", "{\"status\":\"queued\"}", "{}"};
    for (int i = 0; i < bodies.length; i++) {
      wireMockServer.stubFor(post(urlEqualTo("/api/rest/v1/file_actions/delete/unresolved-" + i))
          .willReturn(aResponse().withStatus(201).withHeader("Content-Type", "application/json").withBody(bodies[i])));
      final FileAction action = File.beginDelete("unresolved-" + i, new HashMap<>(), null);

      IllegalStateException error = assertThrows(bodies[i], IllegalStateException.class,
          () -> action.awaitCompletion(30, TimeUnit.SECONDS));

      assertEquals("This file action has not finished and has no file migration to wait for; its status is " + action.getStatus(),
          error.getMessage());
    }
    wireMockServer.verify(0, anyRequestedFor(urlPathMatching("/api/rest/v1/file_migrations.*")));
  }

  @Test
  public void stopsWaitingAtTheTimeoutWhileAStatusRequestIsOutstanding() throws Exception {
    // A raw local server reads the status request and does not answer until the test releases it.
    // The SDK's own read timeout is much longer, so the wait can end at its one-second timeout only
    // by leaving that request unanswered.
    ExecutorService executor = Executors.newSingleThreadExecutor(task -> {
      Thread thread = new Thread(task, "held-status-responder");
      thread.setDaemon(true);
      return thread;
    });
    CountDownLatch release = new CountDownLatch(1);
    try (ServerSocket listener = new ServerSocket(0, 1, InetAddress.getByName("127.0.0.1"))) {
      listener.setSoTimeout(10000);
      AtomicReference<String> requestLine = new AtomicReference<>();
      CountDownLatch received = new CountDownLatch(1);
      Callable<Void> hold = () -> holdOneRequest(listener, requestLine, received, release);
      final Future<Void> responder = executor.submit(hold);
      FileAction action = pendingActionOn(listener, 9);

      TimeoutException timeout = assertThrows(TimeoutException.class,
          () -> action.waitForCompletion(1, TimeUnit.SECONDS));

      // The status request arrived and was still unanswered when the wait ended.
      assertTrue(received.await(10, TimeUnit.SECONDS));
      assertEquals("GET /api/rest/v1/file_migrations/9 HTTP/1.1", requestLine.get());
      assertFalse(responder.isDone());
      assertEquals("File migration 9 did not finish before the wait timed out; its last status was not yet received",
          timeout.getMessage());
      release.countDown();
      responder.get(10, TimeUnit.SECONDS);
      executor.shutdown();
      assertTrue(executor.awaitTermination(10, TimeUnit.SECONDS));
    } finally {
      release.countDown();
      executor.shutdownNow();
      executor.awaitTermination(10, TimeUnit.SECONDS);
    }
  }

  @Test
  public void stopsWaitingWhenInterruptedWhileAStatusRequestIsOutstanding() throws Exception {
    // As above, but the waiting thread is interrupted, with a long timeout, once the status request
    // has arrived and while it is unanswered.
    ExecutorService executor = Executors.newSingleThreadExecutor(task -> {
      Thread thread = new Thread(task, "held-status-responder");
      thread.setDaemon(true);
      return thread;
    });
    CountDownLatch release = new CountDownLatch(1);
    Thread waiter = null;
    try (ServerSocket listener = new ServerSocket(0, 1, InetAddress.getByName("127.0.0.1"))) {
      listener.setSoTimeout(10000);
      AtomicReference<String> requestLine = new AtomicReference<>();
      CountDownLatch received = new CountDownLatch(1);
      Callable<Void> hold = () -> holdOneRequest(listener, requestLine, received, release);
      final Future<Void> responder = executor.submit(hold);
      FileAction action = pendingActionOn(listener, 10);
      AtomicReference<Throwable> outcome = new AtomicReference<>();
      waiter = new Thread(() -> {
        try {
          action.waitForCompletion(30, TimeUnit.SECONDS);
        } catch (Throwable failure) {
          outcome.set(failure);
        }
      }, "file-migration-waiter");
      waiter.setDaemon(true);
      waiter.start();

      assertTrue(received.await(10, TimeUnit.SECONDS));
      waiter.interrupt();
      waiter.join(10000);

      assertFalse(waiter.isAlive());
      assertTrue(String.valueOf(outcome.get()), outcome.get() instanceof InterruptedException);
      assertEquals("GET /api/rest/v1/file_migrations/10 HTTP/1.1", requestLine.get());
      assertFalse(responder.isDone());
      release.countDown();
      responder.get(10, TimeUnit.SECONDS);
      executor.shutdown();
      assertTrue(executor.awaitTermination(10, TimeUnit.SECONDS));
    } finally {
      // On every exit, without masking an earlier failure: stop the waiter first, so it starts no
      // more status requests, then release and stop the responder.
      if (waiter != null) {
        waiter.interrupt();
        waiter.join(10000);
      }
      release.countDown();
      executor.shutdownNow();
      executor.awaitTermination(10, TimeUnit.SECONDS);
    }
  }

  // The warnings that announce a retry, as the SDK logged them while the capture was open.
  private static List<String> retriesAnnounced(SdkWarnings sdkWarnings) {
    List<String> retries = new ArrayList<>();
    for (String event : sdkWarnings.events()) {
      if (event.contains("Retrying")) {
        retries.add(event);
      }
    }
    return retries;
  }

  // Answers GET url with the first response once and with the second after that, using a WireMock
  // scenario; TestServer.stub answers every request the same way.
  private void stubFirstThen(String url, int firstStatus, String firstBody, int thenStatus, String thenBody)
      throws IOException {
    String scenario = "first-then " + url;
    postMapping(scenarioMapping(scenario, "Started", "answered", url, firstStatus, firstBody));
    postMapping(scenarioMapping(scenario, "answered", null, url, thenStatus, thenBody));
  }

  private static ObjectNode scenarioMapping(String scenario, String requiredState, String newState, String url,
      int status, String body) {
    ObjectNode mapping = new ObjectMapper().createObjectNode();
    mapping.put("scenarioName", scenario).put("requiredScenarioState", requiredState);
    if (newState != null) {
      mapping.put("newScenarioState", newState);
    }
    mapping.putObject("request").put("method", "GET").put("url", url);
    ObjectNode response = mapping.putObject("response").put("status", status).put("body", body);
    response.putObject("headers").put("Content-Type", "application/json");
    return mapping;
  }

  private void postMapping(ObjectNode mapping) throws IOException {
    HttpURLConnection connection = (HttpURLConnection) new URL(server.url("/__admin/mappings")).openConnection();
    try {
      connection.setRequestMethod("POST");
      connection.setDoOutput(true);
      connection.setRequestProperty("Content-Type", "application/json");
      try (OutputStream out = connection.getOutputStream()) {
        out.write(new ObjectMapper().writeValueAsBytes(mapping));
      }
      int status = connection.getResponseCode();
      if (status / 100 != 2) {
        throw new IOException("WireMock /__admin/mappings answered " + status);
      }
    } finally {
      connection.disconnect();
    }
  }

  // A page of GET /api/rest/v1/groups for per_page=2 and the given cursor, or for no cursor when it
  // is null, whose response gives nextCursor, unless it is null, in X-Files-Cursor-Next alone.
  // Query parameters are matched by name and decoded value, in any order.
  private static ObjectNode groupsPage(String cursor, String items, String nextCursor) {
    ObjectNode mapping = new ObjectMapper().createObjectNode();
    ObjectNode request = mapping.putObject("request").put("method", "GET").put("urlPath", "/api/rest/v1/groups");
    ObjectNode query = request.putObject("queryParameters");
    query.putObject("per_page").put("equalTo", "2");
    if (cursor == null) {
      query.putObject("cursor").put("absent", true);
    } else {
      query.putObject("cursor").put("equalTo", cursor);
    }
    ObjectNode response = mapping.putObject("response").put("status", 200).put("body", items);
    ObjectNode headers = response.putObject("headers").put("Content-Type", "application/json");
    if (nextCursor != null) {
      headers.put("X-Files-Cursor-Next", nextCursor);
    }
    return mapping;
  }

  // Accepts one connection, reads its request head and answers with a 200 whose Content-Length
  // promises 43 bytes but whose body is only the 11 bytes of [{"id":73}]; then closes the connection
  // and the listener and returns the request line. Accepting and each read give up after 10 seconds.
  private static String answerOnceWithABodyCutShort(ServerSocket listener) throws IOException {
    try (ServerSocket owned = listener; Socket socket = owned.accept()) {
      socket.setSoTimeout(10000);
      BufferedReader request = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.US_ASCII));
      String requestLine = request.readLine();
      String line = requestLine;
      while (line != null && !line.isEmpty()) {
        line = request.readLine();
      }
      if (line == null) {
        throw new IOException("The connection closed before the request head ended");
      }
      OutputStream response = socket.getOutputStream();
      response.write(("HTTP/1.1 200 OK\r\n"
          + "Content-Type: application/json\r\n"
          + "Content-Length: 43\r\n"
          + "\r\n"
          + "[{\"id\":73}]").getBytes(StandardCharsets.US_ASCII));
      response.flush();
      return requestLine;
    }
  }

  // Accepts one connection, reads its request head and sends nothing back, waiting until the client
  // closes or resets the connection; then closes the connection and the listener and returns the
  // request line. Accepting and each read give up after 10 seconds, so a client that never ends the
  // connection fails the responder.
  private static String answerNothingUntilTheClientGivesUp(ServerSocket listener) throws IOException {
    try (ServerSocket owned = listener; Socket socket = owned.accept()) {
      socket.setSoTimeout(10000);
      BufferedReader request = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.US_ASCII));
      String requestLine = request.readLine();
      String line = requestLine;
      while (line != null && !line.isEmpty()) {
        line = request.readLine();
      }
      if (line == null) {
        throw new IOException("The connection closed before the request head ended");
      }
      try {
        if (request.read() != -1) {
          throw new IOException("The client sent more after the request head");
        }
      } catch (SocketException e) {
        // The client reset the connection as it gave up on it, which also ends it.
      }
      return requestLine;
    }
  }

  // An Apache client that counts the calls made to it and passes each one, unchanged, to another
  // client, recording the I/O failures that client throws. It rethrows each failure as it is, or,
  // given a number of wrappers, inside that many IOExceptions, as a client passed to
  // FilesHttpExecutor may do. Closing it closes the other client.
  @SuppressWarnings("deprecation")
  private static final class CountingClient extends CloseableHttpClient {
    private final CloseableHttpClient client;
    private final int wrappers;
    final AtomicInteger calls = new AtomicInteger();
    final List<IOException> failures = new CopyOnWriteArrayList<>();

    CountingClient(CloseableHttpClient client) {
      this(client, 0);
    }

    CountingClient(CloseableHttpClient client, int wrappers) {
      this.client = client;
      this.wrappers = wrappers;
    }

    @Override
    protected CloseableHttpResponse doExecute(HttpHost target, HttpRequest request, HttpContext context)
        throws IOException {
      calls.incrementAndGet();
      try {
        return client.execute(target, request, context);
      } catch (IOException e) {
        failures.add(e);
        IOException thrown = e;
        for (int wrapped = 0; wrapped < wrappers; wrapped++) {
          thrown = new IOException(thrown);
        }
        throw thrown;
      }
    }

    @Override
    public org.apache.http.params.HttpParams getParams() {
      return client.getParams();
    }

    @Override
    public org.apache.http.conn.ClientConnectionManager getConnectionManager() {
      return client.getConnectionManager();
    }

    @Override
    public void close() throws IOException {
      client.close();
    }
  }

  // Like CountingClient, but counts calls to execute, before Apache's client checks the request's
  // URL, and passes each one, unchanged, to the other client's execute.
  @SuppressWarnings("deprecation")
  private static final class ExecuteCountingClient extends CloseableHttpClient {
    private final CloseableHttpClient client;
    final AtomicInteger calls = new AtomicInteger();
    final List<IOException> failures = new CopyOnWriteArrayList<>();

    ExecuteCountingClient(CloseableHttpClient client) {
      this.client = client;
    }

    @Override
    public CloseableHttpResponse execute(HttpUriRequest request) throws IOException {
      calls.incrementAndGet();
      try {
        return client.execute(request);
      } catch (IOException e) {
        failures.add(e);
        throw e;
      }
    }

    @Override
    protected CloseableHttpResponse doExecute(HttpHost target, HttpRequest request, HttpContext context)
        throws IOException {
      return client.execute(target, request, context);
    }

    @Override
    public org.apache.http.params.HttpParams getParams() {
      return client.getParams();
    }

    @Override
    public org.apache.http.conn.ClientConnectionManager getConnectionManager() {
      return client.getConnectionManager();
    }

    @Override
    public void close() throws IOException {
      client.close();
    }
  }

  private static final String SERVER_KEY_PASSWORD = "test";

  private interface SocketAnswer {
    void answer(Socket socket) throws IOException;
  }

  // Accepts connections until the listener is closed, passing each to answer and then closing it,
  // and returns how many it accepted. Accepting and each read give up after 10 seconds.
  private static int countConnections(ServerSocket listener, SocketAnswer answer) throws IOException {
    listener.setSoTimeout(10000);
    int connections = 0;
    while (true) {
      Socket accepted;
      try {
        accepted = listener.accept();
      } catch (SocketException closed) {
        return connections;
      }
      connections++;
      try (Socket socket = accepted) {
        socket.setSoTimeout(10000);
        answer.answer(socket);
      } catch (IOException e) {
        // The client gave up on this connection, as each test client here does.
      }
    }
  }

  // Closes a listener that countConnections is accepting on, which ends its count. The listener's
  // try-with-resources closes it again, which does nothing.
  private static void stopAccepting(ServerSocket listener) throws IOException {
    listener.close();
  }

  // Reads the client's first TLS record, its hello, and answers with a fatal handshake_failure
  // alert, as a server with no cipher suite in common with the client does. It then waits for the
  // client to close, so that closing the connection does not reset it first.
  private static void answerTlsHelloWithHandshakeFailure(Socket socket) throws IOException {
    DataInputStream in = new DataInputStream(socket.getInputStream());
    byte[] header = new byte[5];
    in.readFully(header);
    in.readFully(new byte[((header[3] & 0xff) << 8) | (header[4] & 0xff)]);
    OutputStream out = socket.getOutputStream();
    // An alert record (21) for TLS 1.2 (3, 3), 2 bytes long: fatal (2), handshake_failure (40).
    out.write(new byte[]{21, 3, 3, 0, 2, 2, 40});
    out.flush();
    while (in.read() != -1) {
      // Discard whatever the client sends before it closes.
    }
  }

  // A TLS listener on the loopback address that presents the key and certificate in serverKeys.
  private static ServerSocket tlsListener(KeyStore serverKeys) throws Exception {
    KeyManagerFactory keyManagers = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
    keyManagers.init(serverKeys, SERVER_KEY_PASSWORD.toCharArray());
    SSLContext tls = SSLContext.getInstance("TLS");
    tls.init(keyManagers.getKeyManagers(), null, null);
    return tls.getServerSocketFactory().createServerSocket(0, 50, InetAddress.getByName("127.0.0.1"));
  }

  // TLS that trusts the certificate in serverKeys and no other.
  private static SSLContext trustingOnly(KeyStore serverKeys) throws Exception {
    KeyStore trusted = KeyStore.getInstance("JKS");
    trusted.load(null, null);
    trusted.setCertificateEntry("server", serverKeys.getCertificate("server"));
    TrustManagerFactory trustManagers = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
    trustManagers.init(trusted);
    SSLContext tls = SSLContext.getInstance("TLS");
    tls.init(null, trustManagers.getTrustManagers(), null);
    return tls;
  }

  // A key store holding, as "server", a new RSA key and its certificate for CN=localhost, signed
  // only by that key, so the JDK's default trust store does not trust it. The X.509 version 1
  // certificate is encoded here, because the JDK has no public API that creates one.
  private static KeyStore selfSignedServerKeys() throws Exception {
    KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
    generator.initialize(2048);
    KeyPair keyPair = generator.generateKeyPair();
    byte[] sha256WithRsa = der(0x30,
        der(0x06, new byte[]{0x2a, (byte) 0x86, 0x48, (byte) 0x86, (byte) 0xf7, 0x0d, 0x01, 0x01, 0x0b}), der(0x05));
    byte[] name = der(0x30, der(0x31, der(0x30, der(0x06, new byte[]{0x55, 0x04, 0x03}),
        der(0x0c, "localhost".getBytes(StandardCharsets.US_ASCII)))));
    SimpleDateFormat utcTime = new SimpleDateFormat("yyMMddHHmmss'Z'", Locale.ROOT);
    utcTime.setTimeZone(TimeZone.getTimeZone("UTC"));
    long now = System.currentTimeMillis();
    byte[] validity = der(0x30,
        der(0x17, utcTime.format(new Date(now - TimeUnit.DAYS.toMillis(1))).getBytes(StandardCharsets.US_ASCII)),
        der(0x17, utcTime.format(new Date(now + TimeUnit.DAYS.toMillis(1))).getBytes(StandardCharsets.US_ASCII)));
    byte[] toBeSigned = der(0x30, der(0x02, new byte[]{1}), sha256WithRsa, name, validity, name,
        keyPair.getPublic().getEncoded());
    Signature signer = Signature.getInstance("SHA256withRSA");
    signer.initSign(keyPair.getPrivate());
    signer.update(toBeSigned);
    byte[] signature = signer.sign();
    byte[] signatureBits = new byte[signature.length + 1];
    System.arraycopy(signature, 0, signatureBits, 1, signature.length);
    Certificate certificate = CertificateFactory.getInstance("X.509").generateCertificate(
        new ByteArrayInputStream(der(0x30, toBeSigned, sha256WithRsa, der(0x03, signatureBits))));

    KeyStore keys = KeyStore.getInstance("JKS");
    keys.load(null, null);
    keys.setKeyEntry("server", keyPair.getPrivate(), SERVER_KEY_PASSWORD.toCharArray(), new Certificate[]{certificate});
    return keys;
  }

  // The DER encoding of one value: its tag, the length of its contents, then the contents.
  private static byte[] der(int tag, byte[]... contents) {
    ByteArrayOutputStream body = new ByteArrayOutputStream();
    for (byte[] part : contents) {
      body.write(part, 0, part.length);
    }
    int length = body.size();
    ByteArrayOutputStream value = new ByteArrayOutputStream();
    value.write(tag);
    if (length >= 0x100) {
      value.write(0x82);
      value.write(length >> 8);
    } else if (length >= 0x80) {
      value.write(0x81);
    }
    value.write(length & 0xff);
    value.write(body.toByteArray(), 0, length);
    return value.toByteArray();
  }

  // Reads the request head and answers with a line that is not an HTTP status line; closing the
  // connection afterwards leaves the client without a valid response.
  private static void answerWithoutAStatusLine(Socket socket) throws IOException {
    BufferedReader request = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.US_ASCII));
    String line = request.readLine();
    while (line != null && !line.isEmpty()) {
      line = request.readLine();
    }
    OutputStream out = socket.getOutputStream();
    out.write("this is not an HTTP response\r\n".getBytes(StandardCharsets.US_ASCII));
    out.flush();
  }

  // A pending file action whose status requests go to the local listener.
  private static FileAction pendingActionOn(ServerSocket listener, long migrationId) {
    HashMap<String, Object> fields = new HashMap<>();
    fields.put("status", "pending");
    fields.put("file_migration_id", migrationId);
    HashMap<String, Object> options = new HashMap<>();
    options.put("api_root", "http://127.0.0.1:" + listener.getLocalPort());
    return new FileAction(fields, options);
  }

  // Accepts one connection, reads its request head, records the request line and counts down
  // received; then answers nothing until release counts down, and closes the connection and the
  // listener. Accepting, each read and waiting for release give up after 10 seconds.
  private static Void holdOneRequest(ServerSocket listener, AtomicReference<String> requestLine,
      CountDownLatch received, CountDownLatch release) throws Exception {
    try (ServerSocket owned = listener; Socket socket = owned.accept()) {
      socket.setSoTimeout(10000);
      BufferedReader request = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.US_ASCII));
      String line = request.readLine();
      requestLine.set(line);
      while (line != null && !line.isEmpty()) {
        line = request.readLine();
      }
      if (line == null) {
        throw new IOException("The connection closed before the request head ended");
      }
      received.countDown();
      if (!release.await(10, TimeUnit.SECONDS)) {
        throw new IOException("The test did not release the request");
      }
      return null;
    }
  }
}
