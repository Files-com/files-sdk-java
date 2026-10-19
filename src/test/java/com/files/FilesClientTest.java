package com.files;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.anyRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.deleteRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.patch;
import static com.github.tomakehurst.wiremock.client.WireMock.patchRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.files.exceptions.ApiErrorException;
import com.files.exceptions.OperationCancelledException;
import com.files.exceptions.SdkException;
import com.files.models.File;
import com.files.models.FileUploadPart;
import com.files.models.User;
import com.files.net.FilesApacheHttpInputStream;
import com.files.net.GuardedApacheClient;
import com.files.net.HttpTransport;
import com.files.net.UploadOptions;
import com.files.net.UploadResult;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import org.apache.http.HttpHost;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

public class FilesClientTest {
  private static final String REGION_MISMATCH = "{\"type\":\"not-authenticated/lockout-region-mismatch\",\"http-code\":403,"
      + "\"title\":\"Lockout Region Mismatch\",\"error\":\"Your account must login using a different server\","
      + "\"data\":{\"host\":\"%s\"}}";
  // The same error naming no host in its data; the response's X-Files-Host header names it instead.
  private static final String REGION_MISMATCH_WITHOUT_HOST = "{\"type\":\"not-authenticated/lockout-region-mismatch\","
      + "\"http-code\":403,\"title\":\"Lockout Region Mismatch\",\"error\":\"Your account must login using a different server\"}";
  // The plain text older servers answer with instead, naming the host in X-Files-Host.
  private static final String LEGACY_REGION_MISMATCH = "You have connected to a URL that is not valid for this site. "
      + "Connect to the server named in the X-Files-Host header instead.";

  private WireMockServer api;
  private final List<FilesClient> clients = new ArrayList<>();

  @Before
  public void setUp() {
    api = FilesApiTest.loopbackServer();
    api.start();
  }

  @After
  public void tearDown() {
    for (FilesClient client : clients) {
      client.close();
    }
    FilesClient.apiKey = null;
    FilesClient.workspaceId = null;
    api.stop();
  }

  private FilesClient.Builder builder(String apiKey) {
    return FilesClient.builder().apiKey(apiKey).apiRoot("http://localhost:" + api.port()).retryDelay(Duration.ofMillis(1));
  }

  private FilesClient client(FilesClient.Builder builder) {
    FilesClient client = builder.build();
    clients.add(client);
    return client;
  }

  private void stubUserPages() {
    api.stubFor(get(urlPathEqualTo("/api/rest/v1/users")).withQueryParam("cursor", equalTo("page-2"))
        .willReturn(okJson("[{\"id\":2,\"username\":\"second\"}]")));
    api.stubFor(get(urlPathEqualTo("/api/rest/v1/users")).atPriority(10)
        .willReturn(okJson("[{\"id\":1,\"username\":\"first\"}]").withHeader("X-Files-Cursor", "page-2")));
    api.stubFor(patch(urlPathEqualTo("/api/rest/v1/users/1")).willReturn(okJson("{\"id\":1,\"username\":\"renamed\"}")));
    api.stubFor(patch(urlPathEqualTo("/api/rest/v1/users/2")).willReturn(okJson("{\"id\":2,\"username\":\"renamed\"}")));
  }

  @Test(timeout = 20000)
  public void twoClientsKeepTheirOwnCredentialsThroughPagesAndSaves() throws Exception {
    stubUserPages();
    FilesClient first = client(builder("first-key").workspaceId(0L));
    FilesClient second = client(builder(null).sessionId("second-session").workspaceId(9L));
    FilesClient.apiKey = "static-key";

    ListIterator<User> firstUsers = first.users().list();
    ListIterator<User> secondUsers = second.users().list();
    List<User> firstAll = new ArrayList<>();
    List<User> secondAll = new ArrayList<>();
    for (User user : firstUsers.listAutoPaging()) {
      firstAll.add(user);
      // Changing the static settings does not change a client or what it returned.
      FilesClient.apiKey = "changed-static-key";
      for (User other : secondUsers.listAutoPaging()) {
        secondAll.add(other);
      }
    }
    firstAll.get(1).setUsername("renamed");
    firstAll.get(1).save();
    secondAll.get(0).update(new HashMap<>());

    List<LoggedRequest> requests = api.findAll(anyRequestedFor(anyUrl()));
    assertEquals(6, requests.size());
    for (LoggedRequest request : requests) {
      boolean fromFirst = "first-key".equals(request.getHeader("X-FilesAPI-Key"));
      if (fromFirst) {
        assertEquals(request.getUrl(), "0", request.getHeader("X-Files-Workspace-Id"));
        assertFalse(request.containsHeader("X-FilesAPI-Auth"));
      } else {
        assertEquals(request.getUrl(), "second-session", request.getHeader("X-FilesAPI-Auth"));
        assertEquals(request.getUrl(), "9", request.getHeader("X-Files-Workspace-Id"));
        assertFalse(request.getUrl(), request.containsHeader("X-FilesAPI-Key"));
      }
    }
    api.verify(1, patchRequestedFor(urlEqualTo("/api/rest/v1/users/2")).withHeader("X-FilesAPI-Key", equalTo("first-key")));
    api.verify(1, patchRequestedFor(urlEqualTo("/api/rest/v1/users/1")).withHeader("X-FilesAPI-Auth", equalTo("second-session")));
    assertEquals("the save refreshed the retained user", "renamed", firstAll.get(1).getUsername());
  }

  @Test(timeout = 20000)
  public void perCallOptionsWinAndAreCopiedIntoWhatTheCallReturns() throws Exception {
    stubUserPages();
    FilesClient client = client(builder("client-key").workspaceId(3L));
    HashMap<String, Object> options = new HashMap<>();
    options.put("api_key", "call-key");
    options.put("workspace_id", null);

    ListIterator<User> users = client.users().list(null, options);
    options.put("api_key", "mutated-key");
    options.put("workspace_id", 44L);
    List<User> all = users.all();
    all.get(0).update(new HashMap<>());

    for (LoggedRequest request : api.findAll(anyRequestedFor(anyUrl()))) {
      assertEquals(request.getUrl(), "call-key", request.getHeader("X-FilesAPI-Key"));
      assertFalse("a null workspace_id sends none: " + request.getUrl(), request.containsHeader("X-Files-Workspace-Id"));
    }
  }

  @Test(timeout = 20000)
  public void theStaticMethodsUseTheStaticSettingsAsTheyWereWhenTheCallWasMade() {
    stubUserPages();
    FilesClient.setProperty("apiRoot", "http://localhost:" + api.port());
    FilesClient.apiKey = "static-a";
    ListIterator<User> users = User.list();
    FilesClient.apiKey = "static-b";
    users.all();
    User.list().all();

    api.verify(2, getRequestedFor(urlPathEqualTo("/api/rest/v1/users")).withHeader("X-FilesAPI-Key", equalTo("static-a")));
    api.verify(2, getRequestedFor(urlPathEqualTo("/api/rest/v1/users")).withHeader("X-FilesAPI-Key", equalTo("static-b")));
    assertSame("unchanged settings reuse the default client", FilesClient.defaultClient(), FilesClient.defaultClient());
  }

  @Test(timeout = 20000)
  public void retriesUseOneBudgetAndLogOnlyTheRetriesThatHappen() throws Exception {
    api.stubFor(get(urlEqualTo("/api/rest/v1/users/1")).willReturn(aResponse().withStatus(503)));
    FilesClient client = client(builder("key"));
    SdkWarnings warnings = new SdkWarnings();
    try {
      client.users().find(1L, null);
      fail("Expected the request to fail");
    } catch (ApiErrorException.ApiConnectionException e) {
      assertEquals("HTTP returned status 503 without a structured error response (response to GET /api/rest/v1/users/1)", e.getMessage());
    } finally {
      warnings.close();
    }
    api.verify(4, getRequestedFor(urlEqualTo("/api/rest/v1/users/1")));
    assertEquals(Arrays.asList(
        "WARN com.files.net.ClientRuntime Retrying the GET request (retry 1 of 3) in 1 ms after HTTP status 503",
        "WARN com.files.net.ClientRuntime Retrying the GET request (retry 2 of 3) in 2 ms after HTTP status 503",
        "WARN com.files.net.ClientRuntime Retrying the GET request (retry 3 of 3) in 4 ms after HTTP status 503"), warnings.events());

    api.resetRequests();
    FilesClient once = client(builder("key").maxRetries(0));
    try {
      once.users().find(1L, null);
      fail("Expected the request to fail");
    } catch (ApiErrorException.ApiConnectionException e) {
      api.verify(1, getRequestedFor(urlEqualTo("/api/rest/v1/users/1")));
    }

    api.resetRequests();
    HashMap<String, Object> options = new HashMap<>();
    options.put("max_retries", 1);
    try {
      client.users().find(1L, null, options);
      fail("Expected the request to fail");
    } catch (ApiErrorException.ApiConnectionException e) {
      api.verify(2, getRequestedFor(urlEqualTo("/api/rest/v1/users/1")));
    }

    // The same through an Apache client of your own, which the client borrows: one budget still, with
    // no retries of the Apache client's.
    api.resetRequests();
    try (GuardedApacheClient http = HttpTransport.builder().buildClient()) {
      FilesClient adopted = client(builder("key").transport(HttpTransport.borrowed(http)));
      try {
        adopted.users().find(1L, null);
        fail("Expected the request to fail");
      } catch (ApiErrorException.ApiConnectionException e) {
        api.verify(4, getRequestedFor(urlEqualTo("/api/rest/v1/users/1")));
      }
    }
  }

  @Test(timeout = 20000)
  public void only429And500OrAboveButNot501AreRetriedWhateverTheMethod() {
    FilesClient client = client(builder("key"));
    for (int status : new int[]{400, 404, 409, 501}) {
      api.resetAll();
      api.stubFor(get(urlEqualTo("/api/rest/v1/users/1")).willReturn(aResponse().withStatus(status)));
      try {
        client.users().find(1L, null);
        fail("Expected HTTP " + status + " to fail");
      } catch (ApiErrorException.ApiConnectionException e) {
        api.verify(1, getRequestedFor(urlEqualTo("/api/rest/v1/users/1")));
      }
    }
    api.resetAll();
    api.stubFor(get(urlEqualTo("/api/rest/v1/users/1")).inScenario("throttle").whenScenarioStateIs(STARTED)
        .willReturn(aResponse().withStatus(429).withHeader("Retry-After", "1")).willSetStateTo("ok"));
    api.stubFor(get(urlEqualTo("/api/rest/v1/users/1")).inScenario("throttle").whenScenarioStateIs("ok")
        .willReturn(okJson("{\"id\":1}")));
    long start = System.nanoTime();
    assertEquals(Long.valueOf(1), client.users().find(1L, null).getId());
    assertTrue("waited for Retry-After", TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start) >= 1000);

    // HTTP 500 is retried in answer to a GET.
    api.resetAll();
    api.stubFor(get(urlEqualTo("/api/rest/v1/users/1")).inScenario("flaky").whenScenarioStateIs(STARTED)
        .willReturn(aResponse().withStatus(500)).willSetStateTo("ok"));
    api.stubFor(get(urlEqualTo("/api/rest/v1/users/1")).inScenario("flaky").whenScenarioStateIs("ok")
        .willReturn(okJson("{\"id\":1}")));
    assertEquals(Long.valueOf(1), client.users().find(1L, null).getId());
    api.verify(2, getRequestedFor(urlEqualTo("/api/rest/v1/users/1")));

    // In answer to another method too, HTTP 500 or above is retried, with the same body and
    // credentials, within the same budget of three retries, and HTTP 501 never.
    HashMap<String, Object> parameters = new HashMap<>();
    parameters.put("username", "new-user");
    for (int status : new int[]{500, 501, 502, 504}) {
      api.resetAll();
      api.stubFor(post(urlEqualTo("/api/rest/v1/users")).willReturn(aResponse().withStatus(status)));
      try {
        client.users().create(parameters, null);
        fail("Expected HTTP " + status + " to fail");
      } catch (ApiErrorException.ApiConnectionException e) {
        assertEquals("HTTP returned status " + status + " without a structured error response (response to POST /api/rest/v1/users)",
            e.getMessage());
        api.verify(status == 501 ? 1 : 4, postRequestedFor(urlEqualTo("/api/rest/v1/users"))
            .withHeader("X-FilesAPI-Key", equalTo("key")).withRequestBody(equalToJson("{\"username\":\"new-user\"}")));
        api.verify(status == 501 ? 1 : 4, postRequestedFor(urlEqualTo("/api/rest/v1/users")));
      }
    }

    // A POST answered once with HTTP 500 is completed by its retry.
    api.resetAll();
    api.stubFor(post(urlEqualTo("/api/rest/v1/users")).inScenario("flaky post").whenScenarioStateIs(STARTED)
        .willReturn(aResponse().withStatus(500)).willSetStateTo("ok"));
    api.stubFor(post(urlEqualTo("/api/rest/v1/users")).inScenario("flaky post").whenScenarioStateIs("ok")
        .willReturn(okJson("{\"id\":5,\"username\":\"new-user\"}")));
    assertEquals(Long.valueOf(5), client.users().create(parameters, null).getId());
    api.verify(2, postRequestedFor(urlEqualTo("/api/rest/v1/users")).withRequestBody(equalToJson("{\"username\":\"new-user\"}")));
  }

  @Test(timeout = 20000)
  public void aRegionMismatchMovesTheClientToTheNamedServerWithinTheSameRetryBudget() {
    WireMockServer region = FilesApiTest.loopbackServer();
    region.start();
    try {
      String regionRoot = "http://localhost:" + region.port();
      // The error's data.host names the server, even beside an X-Files-Host header naming another.
      api.stubFor(get(urlEqualTo("/api/rest/v1/users/1")).willReturn(aResponse().withStatus(403)
          .withHeader("X-Files-Host", "header.example.test").withBody(String.format(REGION_MISMATCH, "eu.example.test"))));
      region.stubFor(get(urlEqualTo("/api/rest/v1/users/1")).willReturn(okJson("{\"id\":1}")));
      region.stubFor(get(urlEqualTo("/api/rest/v1/users/2")).willReturn(okJson("{\"id\":2}")));
      List<String> asked = new ArrayList<>();
      RegionRelocationPolicy toRegion = (apiRoot, host) -> {
        asked.add(apiRoot + " " + host);
        return URI.create(regionRoot);
      };
      FilesClient client = client(builder("region-key").regionRelocation(toRegion));

      assertEquals(Long.valueOf(1), client.users().find(1L, null).getId());
      assertEquals(Long.valueOf(2), client.users().find(2L, null).getId());

      assertEquals(Arrays.asList("http://localhost:" + api.port() + " eu.example.test"), asked);
      assertEquals("the client keeps using the new server", URI.create(regionRoot), client.apiRoot());
      api.verify(1, getRequestedFor(urlEqualTo("/api/rest/v1/users/1")));
      region.verify(1, getRequestedFor(urlEqualTo("/api/rest/v1/users/1")).withHeader("X-FilesAPI-Key", equalTo("region-key")));
      region.verify(1, getRequestedFor(urlEqualTo("/api/rest/v1/users/2")));

      // The relocation and the retries after it share one budget of three.
      region.resetAll();
      region.stubFor(get(urlEqualTo("/api/rest/v1/users/3")).willReturn(aResponse().withStatus(503)));
      FilesClient budget = client(builder("region-key").regionRelocation(toRegion));
      api.stubFor(get(urlEqualTo("/api/rest/v1/users/3"))
          .willReturn(aResponse().withStatus(403).withBody(String.format(REGION_MISMATCH, "eu.example.test"))));
      try {
        budget.users().find(3L, null);
        fail("Expected the retries to run out");
      } catch (ApiErrorException.ApiConnectionException e) {
        assertEquals("HTTP returned status 503 without a structured error response (response to GET /api/rest/v1/users/3)", e.getMessage());
      }
      api.verify(1, getRequestedFor(urlEqualTo("/api/rest/v1/users/3")));
      region.verify(3, getRequestedFor(urlEqualTo("/api/rest/v1/users/3")));
    } finally {
      region.stop();
    }
  }

  @Test(timeout = 20000)
  public void aRegionMismatchNamingItsServerOnlyInItsHeaderResendsTheChangeAndMovesLaterCalls() {
    WireMockServer region = FilesApiTest.loopbackServer();
    region.start();
    try {
      String regionRoot = "http://localhost:" + region.port();
      api.stubFor(post(urlEqualTo("/api/rest/v1/users")).willReturn(aResponse().withStatus(403)
          .withHeader("X-Files-Host", "eu.example.test").withBody(REGION_MISMATCH_WITHOUT_HOST)));
      region.stubFor(post(urlEqualTo("/api/rest/v1/users")).willReturn(okJson("{\"id\":5,\"username\":\"new-user\"}")));
      region.stubFor(get(urlEqualTo("/api/rest/v1/users/2")).willReturn(okJson("{\"id\":2}")));
      List<String> asked = new ArrayList<>();
      FilesClient client = client(builder("region-key").workspaceId(7L).regionRelocation((apiRoot, host) -> {
        asked.add(apiRoot + " " + host);
        return URI.create(regionRoot);
      }));
      HashMap<String, Object> parameters = new HashMap<>();
      parameters.put("username", "new-user");

      assertEquals(Long.valueOf(5), client.users().create(parameters, null).getId());
      assertEquals(Long.valueOf(2), client.users().find(2L, null).getId());

      assertEquals(Arrays.asList("http://localhost:" + api.port() + " eu.example.test"), asked);
      assertEquals("the client keeps using the new server", URI.create(regionRoot), client.apiRoot());
      api.verify(1, postRequestedFor(urlEqualTo("/api/rest/v1/users")));
      region.verify(1, postRequestedFor(urlEqualTo("/api/rest/v1/users"))
          .withHeader("X-FilesAPI-Key", equalTo("region-key"))
          .withHeader("X-Files-Workspace-Id", equalTo("7"))
          .withRequestBody(equalToJson("{\"username\":\"new-user\"}")));
      region.verify(1, getRequestedFor(urlEqualTo("/api/rest/v1/users/2")).withHeader("X-FilesAPI-Key", equalTo("region-key")));
      api.verify(0, getRequestedFor(urlEqualTo("/api/rest/v1/users/2")));
    } finally {
      region.stop();
    }
  }

  @Test(timeout = 20000)
  public void aLegacyRegionMismatchResendsTheSameAttachmentBodyAndMovesOnlyThatCall() throws Exception {
    WireMockServer region = FilesApiTest.loopbackServer();
    region.start();
    try {
      String regionRoot = "http://localhost:" + region.port();
      api.stubFor(patch(urlEqualTo("/api/rest/v1/site")).willReturn(aResponse().withStatus(403)
          .withHeader("Content-Type", "text/plain").withHeader("X-Files-Host", "eu.example.test").withBody(LEGACY_REGION_MISMATCH)));
      api.stubFor(get(urlEqualTo("/api/rest/v1/users/2")).willReturn(okJson("{\"id\":2}")));
      region.stubFor(patch(urlEqualTo("/api/rest/v1/site")).willReturn(okJson("{}")));
      List<String> asked = new ArrayList<>();
      RegionRelocationPolicy toRegion = (apiRoot, host) -> {
        asked.add(apiRoot + " " + host);
        return URI.create(regionRoot);
      };
      FilesClient client = client(builder("client-key").regionRelocation(toRegion));
      final URI clientRoot = client.apiRoot();
      java.nio.file.Path temporary = java.nio.file.Paths.get(System.getProperty("java.io.tmpdir"));
      final List<java.nio.file.Path> before = attachmentCopies(temporary);
      HashMap<String, Object> parameters = new HashMap<>();
      // Copied to a file before anything is sent, so sending it again reads the copy again.
      parameters.put("logo_file", com.files.net.Attachment.fromInputStream(
          new ByteArrayInputStream("logo bytes".getBytes(StandardCharsets.US_ASCII))).withMemoryThreshold(1));
      HashMap<String, Object> siteKey = new HashMap<>();
      siteKey.put("api_key", "site-key");

      client.sites().update(parameters, siteKey);

      assertEquals(Arrays.asList(clientRoot + " eu.example.test"), asked);
      List<LoggedRequest> refused = api.findAll(patchRequestedFor(urlEqualTo("/api/rest/v1/site")));
      List<LoggedRequest> resent = region.findAll(patchRequestedFor(urlEqualTo("/api/rest/v1/site")));
      assertEquals(1, refused.size());
      assertEquals(1, resent.size());
      assertEquals("site-key", resent.get(0).getHeader("X-FilesAPI-Key"));
      assertEquals(refused.get(0).getHeader("Content-Type"), resent.get(0).getHeader("Content-Type"));
      assertTrue(refused.get(0).getBody().length > 0);
      assertArrayEquals("the same body is sent again", refused.get(0).getBody(), resent.get(0).getBody());
      assertEquals("the attachment's copy was deleted", before, attachmentCopies(temporary));

      // The call's own key moved only that call: the client's own calls stay at its server.
      assertEquals(clientRoot, client.apiRoot());
      assertEquals(Long.valueOf(2), client.users().find(2L, null).getId());
      api.verify(1, getRequestedFor(urlEqualTo("/api/rest/v1/users/2")).withHeader("X-FilesAPI-Key", equalTo("client-key")));

      // With no retry left, the call fails with the plain-text answer's own error, which code
      // catching ApiConnectionException still catches, and which keeps the host it named.
      region.resetRequests();
      FilesClient once = client(builder("client-key").maxRetries(0).regionRelocation(toRegion));
      try {
        once.sites().update(new HashMap<>(), siteKey);
        fail("Expected the region mismatch");
      } catch (ApiErrorException.ApiConnectionException e) {
        assertEquals("HTTP returned status 403 without a structured error response (response to PATCH /api/rest/v1/site)", e.getMessage());
        assertTrue(String.valueOf(e.getClass()), e instanceof ApiErrorException.LegacyRegionMismatchException);
        ApiErrorException.LegacyRegionMismatchException mismatch = (ApiErrorException.LegacyRegionMismatchException) e;
        assertEquals("eu.example.test", mismatch.getHost());
        assertEquals(Arrays.asList("eu.example.test"), filesHostHeaders(mismatch.getHeaders()));
      }
      region.verify(0, anyRequestedFor(anyUrl()));
    } finally {
      region.stop();
    }
  }

  @Test(timeout = 20000)
  public void aRegionMismatchForPerCallCredentialsMovesThatWholeCallButNotTheClient() throws Exception {
    WireMockServer loginSite = FilesApiTest.loopbackServer();
    loginSite.start();
    try (FakeFiles siteB = new FakeFiles()) {
      // The client's server answers the client's own key, and names other servers for site B's key
      // and for a login.
      api.stubFor(any(anyUrl()).withHeader("X-FilesAPI-Key", equalTo("site-b-key"))
          .willReturn(aResponse().withStatus(403).withBody(String.format(REGION_MISMATCH, "site-b.example.test"))));
      api.stubFor(post(urlEqualTo("/api/rest/v1/sessions"))
          .willReturn(aResponse().withStatus(403).withBody(String.format(REGION_MISMATCH, "login.example.test"))));
      api.stubFor(get(urlEqualTo("/api/rest/v1/users/2")).withHeader("X-FilesAPI-Key", equalTo("client-key"))
          .willReturn(okJson("{\"id\":2}")));
      api.stubFor(post(urlEqualTo("/api/rest/v1/files/other-options.bin")).withHeader("X-FilesAPI-Key", equalTo("client-key"))
          .willReturn(okJson("{\"path\":\"other-options.bin\",\"type\":\"file\",\"size\":4}")));
      loginSite.stubFor(post(urlEqualTo("/api/rest/v1/sessions")).willReturn(okJson("{\"id\":\"login-session\"}")));
      RegionRelocationPolicy servers = (apiRoot, host) -> "site-b.example.test".equals(host) ? URI.create(siteB.root())
          : "login.example.test".equals(host) ? URI.create("http://localhost:" + loginSite.port()) : null;
      FilesClient client = client(builder("client-key").regionRelocation(servers));
      final URI clientRoot = client.apiRoot();
      HashMap<String, Object> siteBKey = new HashMap<>();
      siteBKey.put("api_key", "site-b-key");

      // A call with site B's key is sent again to site B's server, with that key, and a login,
      // which carries no credentials, to the server named for it.
      FileUploadPart created = client.files().create("created.bin", new HashMap<>(), siteBKey);
      assertEquals("created.bin", created.getPath());
      api.verify(1, postRequestedFor(urlEqualTo("/api/rest/v1/files/created.bin")));
      assertEquals(1, siteB.creates.size());
      assertEquals("site-b-key", siteB.creates.get(0).header("X-FilesAPI-Key"));
      HashMap<String, Object> credentials = new HashMap<>();
      credentials.put("username", "someone");
      credentials.put("password", "not-a-secret");
      assertEquals("login-session", client.sessions().create(credentials).getId());
      api.verify(1, postRequestedFor(urlEqualTo("/api/rest/v1/sessions")));
      loginSite.verify(1, postRequestedFor(urlEqualTo("/api/rest/v1/sessions")).withoutHeader("X-FilesAPI-Key"));

      // The upload continued from the part that call returned goes to site B's server too. Its 4
      // bytes fit in that part, so nothing is negotiated that could relocate it: only its final
      // request, which is never relocated itself, must follow the part.
      byte[] four = "four".getBytes(StandardCharsets.US_ASCII);
      File small = created.putInputStream(new ByteArrayInputStream(four), null);
      assertEquals("created.bin", small.getPath());
      assertEquals(Long.valueOf(4), small.getSize());
      List<FakeFiles.Received> smallParts = forUpload(siteB.parts, "/storage/" + created.getRef() + "/");
      assertEquals(1, smallParts.size());
      assertArrayEquals(four, smallParts.get(0).body);
      List<FakeFiles.Received> smallFinishes = forUpload(siteB.finishes, "/api/rest/v1/files/created.bin");
      assertEquals(1, smallFinishes.size());
      assertEquals("site-b-key", smallFinishes.get(0).header("X-FilesAPI-Key"));
      // The client's server still saw only the call that created the upload, not its completion.
      api.verify(1, postRequestedFor(urlEqualTo("/api/rest/v1/files/created.bin")));

      // A part given other options, here the client's own credentials, no longer follows the call
      // that returned it: its upload starts at the client's server, like any other.
      FileUploadPart otherOptions = client.files().create("other-options.bin", new HashMap<>(), siteBKey);
      otherOptions.setOptions(new HashMap<>());
      assertEquals("other-options.bin", otherOptions.putInputStream(new ByteArrayInputStream(four), null).getPath());
      api.verify(1, postRequestedFor(urlEqualTo("/api/rest/v1/files/other-options.bin"))
          .withHeader("X-FilesAPI-Key", equalTo("client-key")));
      assertEquals(0, forUpload(siteB.finishes, "/api/rest/v1/files/other-options.bin").size());

      // None of these moved the client: its own calls still go to its own server.
      assertEquals(Long.valueOf(2), client.users().find(2L, null).getId());
      api.verify(1, getRequestedFor(urlEqualTo("/api/rest/v1/users/2")).withHeader("X-FilesAPI-Key", equalTo("client-key")));

      // An upload with site B's key starts at the client's server again. Once relocated, all of it
      // goes to site B's server: the part negotiations, made on the scope its part threads share,
      // and the final request, which is never sent twice and so cannot be relocated itself.
      byte[] contents = "twelve bytes".getBytes(StandardCharsets.US_ASCII);
      UploadResult result = client.transfers().upload(new ByteArrayInputStream(contents), contents.length, "moved.bin",
          UploadOptions.builder().requestOptions(siteBKey).build());

      assertEquals("moved.bin", result.getFile().getPath());
      assertEquals(12, result.getSize());
      api.verify(1, postRequestedFor(urlEqualTo("/api/rest/v1/file_actions/begin_upload/moved.bin")));
      api.verify(0, postRequestedFor(urlEqualTo("/api/rest/v1/files/moved.bin")));
      for (FakeFiles.Received begin : siteB.begins) {
        assertEquals("/api/rest/v1/file_actions/begin_upload/moved.bin", begin.rawPath);
        assertEquals("site-b-key", begin.header("X-FilesAPI-Key"));
      }
      List<FakeFiles.Received> movedFinishes = forUpload(siteB.finishes, "/api/rest/v1/files/moved.bin");
      assertEquals(1, movedFinishes.size());
      FakeFiles.Received movedFinish = movedFinishes.get(0);
      assertEquals("site-b-key", movedFinish.header("X-FilesAPI-Key"));
      assertEquals(12, movedFinish.json().get("size").asLong());
      List<FakeFiles.Received> movedParts = forUpload(siteB.parts, "/storage/" + movedFinish.json().get("ref").asText() + "/");
      assertEquals(3, movedParts.size());
      ByteArrayOutputStream stored = new ByteArrayOutputStream();
      for (FakeFiles.Received part : movedParts) {
        stored.write(part.body);
      }
      assertArrayEquals(contents, stored.toByteArray());
      assertEquals(clientRoot, client.apiRoot());
    } finally {
      loginSite.stop();
    }
  }

  // What a fake server received for one upload, in part order: the requests whose path starts with
  // prefix, such as its final request's path or its parts' storage path.
  private static List<FakeFiles.Received> forUpload(List<FakeFiles.Received> received, String prefix) {
    return received.stream().filter(request -> request.rawPath.startsWith(prefix))
        .sorted(Comparator.comparingLong((FakeFiles.Received request) -> request.part)).collect(Collectors.toList());
  }

  @Test(timeout = 20000)
  public void untrustedOrDisabledRelocationFailsWithTheRegionMismatch() {
    api.stubFor(get(urlEqualTo("/api/rest/v1/users/1"))
        .willReturn(aResponse().withStatus(403).withBody(String.format(REGION_MISMATCH, "files.com.attacker.test"))));
    // The same host named only by the X-Files-Host header, of the error and of the older plain text.
    api.stubFor(get(urlEqualTo("/api/rest/v1/users/2")).willReturn(aResponse().withStatus(403)
        .withHeader("X-Files-Host", "files.com.attacker.test").withBody(REGION_MISMATCH_WITHOUT_HOST)));
    api.stubFor(get(urlEqualTo("/api/rest/v1/users/3")).willReturn(aResponse().withStatus(403)
        .withHeader("X-Files-Host", "files.com.attacker.test").withBody(LEGACY_REGION_MISMATCH)));
    for (FilesClient client : new FilesClient[]{client(builder("key")), client(builder("key").regionRelocation(RegionRelocationPolicy.DISABLED))}) {
      try {
        client.users().find(1L, null);
        fail("Expected the region mismatch");
      } catch (ApiErrorException.LockoutRegionMismatchException e) {
        assertEquals("files.com.attacker.test", e.getData().get("host"));
      }
      try {
        client.users().find(2L, null);
        fail("Expected the region mismatch");
      } catch (ApiErrorException.LockoutRegionMismatchException e) {
        assertNull(e.getData());
        assertEquals(Arrays.asList("files.com.attacker.test"), filesHostHeaders(e.getHeaders()));
      }
      try {
        client.users().find(3L, null);
        fail("Expected the plain-text region mismatch");
      } catch (ApiErrorException.LegacyRegionMismatchException e) {
        assertEquals("HTTP returned status 403 without a structured error response (response to GET /api/rest/v1/users/3)", e.getMessage());
        assertEquals("files.com.attacker.test", e.getHost());
        assertEquals(Arrays.asList("files.com.attacker.test"), filesHostHeaders(e.getHeaders()));
      }
      assertEquals(URI.create("http://localhost:" + api.port()), client.apiRoot());
    }
    api.verify(2, getRequestedFor(urlEqualTo("/api/rest/v1/users/1")));
    api.verify(2, getRequestedFor(urlEqualTo("/api/rest/v1/users/2")));
    api.verify(2, getRequestedFor(urlEqualTo("/api/rest/v1/users/3")));
    // The default policy trusts only Files.com's own names, over HTTPS.
    RegionRelocationPolicy policy = RegionRelocationPolicy.FILES_COM_HOSTS;
    URI root = URI.create("https://app.files.com");
    assertEquals(URI.create("https://eu-site.files.com"), policy.relocate(root, "EU-Site.Files.com"));
    assertEquals(URI.create("https://files.com"), policy.relocate(root, "files.com"));
    for (String host : new String[]{null, "", "test.host", "files.com.attacker.test", "attackerfiles.com", "site.files.com:444",
        "user@site.files.com", "site.files.com/path", "site.files.com.", "-bad.files.com", "site files.com"}) {
      assertNull(host, policy.relocate(root, host));
    }
  }

  @Test(timeout = 20000)
  public void anUnrelatedErrorNeverMovesTheRequestEvenWhenItNamesAHost() {
    WireMockServer region = FilesApiTest.loopbackServer();
    region.start();
    try {
      String regionRoot = "http://localhost:" + region.port();
      List<String> asked = new ArrayList<>();
      FilesClient client = client(builder("key").regionRelocation((apiRoot, host) -> {
        asked.add(apiRoot + " " + host);
        return URI.create(regionRoot);
      }));
      final URI clientRoot = client.apiRoot();

      // Plain text that is not the older region mismatch: other text, the text after something
      // else, the text with another status, or the text without exactly one host.
      int[] statuses = {403, 403, 401, 403, 403};
      String[] bodies = {"Forbidden", "Error: " + LEGACY_REGION_MISMATCH, LEGACY_REGION_MISMATCH, LEGACY_REGION_MISMATCH,
          LEGACY_REGION_MISMATCH};
      String[][] hosts = {{"eu.example.test"}, {"eu.example.test"}, {"eu.example.test"}, {}, {"eu.example.test", "other.example.test"}};
      for (int i = 0; i < statuses.length; i++) {
        answerUserOne(statuses[i], bodies[i], hosts[i]);
        try {
          client.users().find(1L, null);
          fail("Expected HTTP " + statuses[i] + " to fail: " + bodies[i]);
        } catch (ApiErrorException.ApiConnectionException e) {
          assertEquals("HTTP returned status " + statuses[i] + " without a structured error response (response to GET /api/rest/v1/users/1)",
              e.getMessage());
          assertEquals("an ordinary error keeps no host", ApiErrorException.ApiConnectionException.class, e.getClass());
        }
        api.verify(1, getRequestedFor(urlEqualTo("/api/rest/v1/users/1")));
      }

      // Another error type, even beside the header.
      answerUserOne(403, "{\"type\":\"not-authorized/read-permission-required\",\"http-code\":403,"
          + "\"title\":\"Read Permission Required\",\"error\":\"You do not have read permission for this path\"}", "eu.example.test");
      try {
        client.users().find(1L, null);
        fail("Expected the permission error");
      } catch (ApiErrorException e) {
        assertEquals("not-authorized/read-permission-required", e.getType());
        assertFalse(e instanceof ApiErrorException.LockoutRegionMismatchException);
      }
      api.verify(1, getRequestedFor(urlEqualTo("/api/rest/v1/users/1")));

      // The region mismatch error with another status, or its name in another family, fails as
      // the error it is.
      String[] typedBodies = {String.format(REGION_MISMATCH, "eu.example.test"),
          String.format(REGION_MISMATCH, "eu.example.test").replace("not-authenticated/", "not-authorized/")};
      int[] typedStatuses = {401, 403};
      for (int i = 0; i < typedBodies.length; i++) {
        answerUserOne(typedStatuses[i], typedBodies[i], "eu.example.test");
        try {
          client.users().find(1L, null);
          fail("Expected HTTP " + typedStatuses[i] + " to fail: " + typedBodies[i]);
        } catch (ApiErrorException.LockoutRegionMismatchException e) {
          assertEquals("eu.example.test", e.getData().get("host"));
        }
        api.verify(1, getRequestedFor(urlEqualTo("/api/rest/v1/users/1")));
      }

      assertTrue("the policy was not asked: " + asked, asked.isEmpty());
      region.verify(0, anyRequestedFor(anyUrl()));
      assertEquals(clientRoot, client.apiRoot());
    } finally {
      region.stop();
    }
  }

  // Answers GET /api/rest/v1/users/1 with status and body, and an X-Files-Host header with each of
  // hosts, forgetting the server's earlier answers and requests.
  private void answerUserOne(int status, String body, String... hosts) {
    api.resetAll();
    ResponseDefinitionBuilder answer = aResponse().withStatus(status).withBody(body);
    if (hosts.length > 0) {
      answer = answer.withHeader("X-Files-Host", hosts);
    }
    api.stubFor(get(urlEqualTo("/api/rest/v1/users/1")).willReturn(answer));
  }

  // The values of the X-Files-Host headers among an error's response headers.
  private static List<String> filesHostHeaders(List<org.apache.http.Header> headers) {
    return headers.stream().filter(header -> "X-Files-Host".equalsIgnoreCase(header.getName()))
        .map(org.apache.http.Header::getValue).collect(Collectors.toList());
  }

  @Test(timeout = 20000)
  public void aRegionMismatchFromAnotherServerARedirectLedToIsNotFollowed() {
    WireMockServer other = FilesApiTest.loopbackServer();
    WireMockServer region = FilesApiTest.loopbackServer();
    other.start();
    region.start();
    try {
      String otherRoot = "http://localhost:" + other.port();
      String regionRoot = "http://localhost:" + region.port();
      for (int id : new int[]{1, 2}) {
        api.stubFor(get(urlEqualTo("/api/rest/v1/users/" + id))
            .willReturn(aResponse().withStatus(302).withHeader("Location", otherRoot + "/api/rest/v1/users/" + id)));
      }
      other.stubFor(get(urlEqualTo("/api/rest/v1/users/1")).willReturn(aResponse().withStatus(403)
          .withHeader("X-Files-Host", "eu.example.test").withBody(String.format(REGION_MISMATCH, "eu.example.test"))));
      other.stubFor(get(urlEqualTo("/api/rest/v1/users/2")).willReturn(aResponse().withStatus(403)
          .withHeader("X-Files-Host", "eu.example.test").withBody(LEGACY_REGION_MISMATCH)));
      region.stubFor(get(urlEqualTo("/api/rest/v1/users/4")).willReturn(okJson("{\"id\":4}")));
      List<String> asked = new ArrayList<>();
      FilesClient client = client(builder("key").regionRelocation((apiRoot, host) -> {
        asked.add(apiRoot + " " + host);
        return URI.create(regionRoot);
      }));
      final URI clientRoot = client.apiRoot();

      // The server a redirect led to is not the API root's, so neither its region mismatch nor its
      // older plain text moves the request: each is the call's error, and the policy is not asked.
      try {
        client.users().find(1L, null);
        fail("Expected the region mismatch");
      } catch (ApiErrorException.LockoutRegionMismatchException e) {
        assertEquals("eu.example.test", e.getData().get("host"));
      }
      try {
        client.users().find(2L, null);
        fail("Expected the plain-text region mismatch");
      } catch (ApiErrorException.ApiConnectionException e) {
        assertEquals("HTTP returned status 403 without a structured error response (response to GET /api/rest/v1/users/2)", e.getMessage());
        assertEquals("another server's host is not kept", ApiErrorException.ApiConnectionException.class, e.getClass());
      }
      assertTrue("the policy was not asked: " + asked, asked.isEmpty());
      other.verify(2, anyRequestedFor(anyUrl()).withoutHeader("X-FilesAPI-Key"));
      region.verify(0, anyRequestedFor(anyUrl()));
      assertEquals(clientRoot, client.apiRoot());

      // A redirect within the API root's own server leaves the answer the root's own: its region
      // mismatch is followed, with the request the caller made.
      api.stubFor(get(urlEqualTo("/api/rest/v1/users/4"))
          .willReturn(aResponse().withStatus(302).withHeader("Location", "/api/rest/v1/users/5")));
      api.stubFor(get(urlEqualTo("/api/rest/v1/users/5"))
          .willReturn(aResponse().withStatus(403).withBody(String.format(REGION_MISMATCH, "eu.example.test"))));
      assertEquals(Long.valueOf(4), client.users().find(4L, null).getId());
      assertEquals(Arrays.asList(clientRoot + " eu.example.test"), asked);
      region.verify(1, getRequestedFor(urlEqualTo("/api/rest/v1/users/4")).withHeader("X-FilesAPI-Key", equalTo("key")));
      region.verify(0, getRequestedFor(urlEqualTo("/api/rest/v1/users/5")));
    } finally {
      other.stop();
      region.stop();
    }
  }

  @Test(timeout = 20000)
  public void anIpv6ApiRootIsItsOwnServerForARegionMismatchHoweverItsAddressIsWritten() {
    Assume.assumeTrue("this machine cannot listen on the IPv6 loopback address", ipv6Loopback());
    WireMockServer ipv6 = new WireMockServer(WireMockConfiguration.options().dynamicPort().bindAddress("::1"));
    WireMockServer region = FilesApiTest.loopbackServer();
    WireMockServer foreign = FilesApiTest.loopbackServer();
    ipv6.start();
    region.start();
    foreign.start();
    try {
      String ipv6Root = "http://[::1]:" + ipv6.port();
      final String regionRoot = "http://localhost:" + region.port();
      ipv6.stubFor(get(urlEqualTo("/api/rest/v1/users/1"))
          .willReturn(aResponse().withStatus(403).withBody(String.format(REGION_MISMATCH, "eu.example.test"))));
      // The server redirects to itself, with its address written out in full.
      ipv6.stubFor(get(urlEqualTo("/api/rest/v1/users/4")).willReturn(aResponse().withStatus(302)
          .withHeader("Location", "http://[0:0:0:0:0:0:0:1]:" + ipv6.port() + "/api/rest/v1/users/5")));
      // It answers there only a request that still carries the client's key and workspace; one without
      // them gets WireMock's 404, not the mismatch.
      ipv6.stubFor(get(urlEqualTo("/api/rest/v1/users/5")).withHeader("X-FilesAPI-Key", equalTo("key"))
          .withHeader("X-Files-Workspace-Id", equalTo("7")).willReturn(aResponse().withStatus(403)
          .withHeader("X-Files-Host", "eu.example.test").withBody(LEGACY_REGION_MISMATCH)));
      // And it redirects to another origin, which redirects back to it, where it answers a mismatch.
      ipv6.stubFor(get(urlEqualTo("/api/rest/v1/users/6")).willReturn(aResponse().withStatus(302)
          .withHeader("Location", "http://127.0.0.1:" + foreign.port() + "/bounce")));
      foreign.stubFor(get(urlEqualTo("/bounce")).willReturn(aResponse().withStatus(302)
          .withHeader("Location", ipv6Root + "/api/rest/v1/users/7")));
      ipv6.stubFor(get(urlEqualTo("/api/rest/v1/users/7"))
          .willReturn(aResponse().withStatus(403).withBody(String.format(REGION_MISMATCH, "eu.example.test"))));
      region.stubFor(get(urlEqualTo("/api/rest/v1/users/1")).willReturn(okJson("{\"id\":1}")));
      region.stubFor(get(urlEqualTo("/api/rest/v1/users/4")).willReturn(okJson("{\"id\":4}")));
      List<String> asked = new ArrayList<>();
      RegionRelocationPolicy toRegion = (apiRoot, host) -> {
        asked.add(apiRoot + " " + host);
        return URI.create(regionRoot);
      };

      // The configured IPv6 root is its own origin: it receives the client's credentials, and its
      // region mismatch moves the request and the client.
      FilesClient client = client(FilesClient.builder().apiKey("key").apiRoot(ipv6Root).retryDelay(Duration.ofMillis(1))
          .regionRelocation(toRegion));
      assertEquals(Long.valueOf(1), client.users().find(1L, null).getId());
      ipv6.verify(1, getRequestedFor(urlEqualTo("/api/rest/v1/users/1")).withHeader("X-FilesAPI-Key", equalTo("key")));
      region.verify(1, getRequestedFor(urlEqualTo("/api/rest/v1/users/1")).withHeader("X-FilesAPI-Key", equalTo("key")));
      assertEquals(URI.create(regionRoot), client.apiRoot());

      // The same address written another way is the same server: the redirect to it keeps the
      // client's key and workspace, and its older plain-text mismatch moves the request the caller
      // made.
      FilesClient redirected = client(FilesClient.builder().apiKey("key").workspaceId(7L).apiRoot(ipv6Root)
          .retryDelay(Duration.ofMillis(1)).regionRelocation(toRegion));
      assertEquals(Long.valueOf(4), redirected.users().find(4L, null).getId());
      ipv6.verify(1, getRequestedFor(urlEqualTo("/api/rest/v1/users/4")).withHeader("X-FilesAPI-Key", equalTo("key"))
          .withHeader("X-Files-Workspace-Id", equalTo("7")));
      ipv6.verify(1, getRequestedFor(urlEqualTo("/api/rest/v1/users/5")).withHeader("X-FilesAPI-Key", equalTo("key"))
          .withHeader("X-Files-Workspace-Id", equalTo("7")));
      ipv6.verify(1, getRequestedFor(urlEqualTo("/api/rest/v1/users/5")));
      region.verify(1, getRequestedFor(urlEqualTo("/api/rest/v1/users/4")).withHeader("X-FilesAPI-Key", equalTo("key"))
          .withHeader("X-Files-Workspace-Id", equalTo("7")));
      region.verify(0, getRequestedFor(urlEqualTo("/api/rest/v1/users/5")));
      assertEquals(Arrays.asList(ipv6Root + " eu.example.test", ipv6Root + " eu.example.test"), asked);

      // A request that went to another origin is not the root's again when redirected back there: it
      // carries no credentials there, and the mismatch it gets is its error, without asking the policy.
      FilesClient bounced = client(FilesClient.builder().apiKey("key").workspaceId(7L).apiRoot(ipv6Root)
          .retryDelay(Duration.ofMillis(1)).regionRelocation(toRegion));
      try {
        bounced.users().find(6L, null);
        fail("Expected the region mismatch");
      } catch (ApiErrorException.LockoutRegionMismatchException e) {
        assertEquals("eu.example.test", e.getData().get("host"));
      }
      ipv6.verify(1, getRequestedFor(urlEqualTo("/api/rest/v1/users/6")).withHeader("X-FilesAPI-Key", equalTo("key")));
      foreign.verify(1, getRequestedFor(urlEqualTo("/bounce")).withoutHeader("X-FilesAPI-Key")
          .withoutHeader("X-Files-Workspace-Id"));
      ipv6.verify(1, getRequestedFor(urlEqualTo("/api/rest/v1/users/7")).withoutHeader("X-FilesAPI-Key")
          .withoutHeader("X-Files-Workspace-Id"));
      region.verify(0, getRequestedFor(urlEqualTo("/api/rest/v1/users/6")));
      assertEquals("the policy was not asked again", 2, asked.size());
      assertEquals(URI.create(ipv6Root), bounced.apiRoot());
    } finally {
      ipv6.stop();
      region.stop();
      foreign.stop();
    }
  }

  // Whether this machine can listen on the IPv6 loopback address.
  private static boolean ipv6Loopback() {
    try (java.net.ServerSocket socket = new java.net.ServerSocket(0, 1, java.net.InetAddress.getByName("::1"))) {
      return socket.isBound();
    } catch (java.io.IOException e) {
      return false;
    }
  }

  @Test(timeout = 20000)
  public void aCallStopsWhenItsTokenIsCancelledWhileWaitingForTheServer() throws Exception {
    api.stubFor(get(urlEqualTo("/api/rest/v1/users/1")).willReturn(okJson("{\"id\":1}").withFixedDelay(10000)));
    FilesClient client = client(builder("key"));
    CancellationToken token = new CancellationToken();
    HashMap<String, Object> options = new HashMap<>();
    options.put("cancellation", token);
    ExecutorService canceller = Executors.newSingleThreadExecutor();
    try {
      canceller.execute(() -> {
        sleep(300);
        token.cancel();
      });
      long start = System.nanoTime();
      try {
        client.users().find(1L, null, options);
        fail("Expected the call to be cancelled");
      } catch (OperationCancelledException e) {
        assertEquals(OperationCancelledException.Reason.CANCELLED, e.getReason());
        assertEquals("The request was cancelled.", e.getMessage());
      }
      assertTrue(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start) < 5000);
    } finally {
      canceller.shutdownNow();
    }
    api.verify(1, getRequestedFor(urlEqualTo("/api/rest/v1/users/1")));

    // A token that is already cancelled stops the call before anything is sent.
    api.resetRequests();
    try {
      client.users().find(1L, null, options);
      fail("Expected the call to be cancelled");
    } catch (OperationCancelledException e) {
      api.verify(0, getRequestedFor(urlEqualTo("/api/rest/v1/users/1")));
    }
  }

  @Test(timeout = 20000)
  public void aCallStopsWhileReadingASlowBodyOrWaitingToRetry() throws Exception {
    RawHttpServer server = new RawHttpServer(stalledBody(10000));
    try {
      FilesClient client = client(FilesClient.builder().apiKey("key").apiRoot(server.root()));
      HashMap<String, Object> timeout = new HashMap<>();
      timeout.put("timeout", Duration.ofMillis(500));
      long start = System.nanoTime();
      try {
        client.users().find(1L, null, timeout);
        fail("Expected the call to time out");
      } catch (OperationCancelledException e) {
        assertEquals(OperationCancelledException.Reason.TIMED_OUT, e.getReason());
        assertEquals("The request timed out after 500 ms.", e.getMessage());
      }
      assertTrue(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start) < 5000);
    } finally {
      server.close();
    }

    // The same through an Apache client of your own, which the client borrows: the request it sends
    // is the call's own, which the call's time limit aborts.
    RawHttpServer adoptedServer = new RawHttpServer(stalledBody(10000));
    try (GuardedApacheClient http = HttpTransport.builder().buildClient()) {
      FilesClient client = client(FilesClient.builder().apiKey("key").apiRoot(adoptedServer.root()).transport(HttpTransport.borrowed(http)));
      HashMap<String, Object> timeout = new HashMap<>();
      timeout.put("timeout", Duration.ofMillis(500));
      long start = System.nanoTime();
      try {
        client.users().find(1L, null, timeout);
        fail("Expected the call to time out");
      } catch (OperationCancelledException e) {
        assertEquals(OperationCancelledException.Reason.TIMED_OUT, e.getReason());
      }
      assertTrue(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start) < 5000);
    } finally {
      adoptedServer.close();
    }

    api.stubFor(get(urlEqualTo("/api/rest/v1/users/1")).willReturn(aResponse().withStatus(503)));
    FilesClient patient = client(builder("key").retryDelay(Duration.ofSeconds(10)).callTimeout(Duration.ofMillis(300)));
    long start = System.nanoTime();
    try {
      patient.users().find(1L, null);
      fail("Expected the call to time out");
    } catch (OperationCancelledException e) {
      assertEquals(OperationCancelledException.Reason.TIMED_OUT, e.getReason());
    }
    assertTrue("the wait before the retry ended", TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start) < 5000);
    api.verify(1, getRequestedFor(urlEqualTo("/api/rest/v1/users/1")));
  }

  // Answers with its headers and the start of its body, then sends nothing more for a while.
  private static RawHttpServer.Reply stalledBody(long stallMillis) {
    return (request, connection) -> {
      java.io.OutputStream out = connection.getOutputStream();
      out.write("HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nContent-Length: 100\r\n\r\n{\"id\":".getBytes(
          java.nio.charset.StandardCharsets.US_ASCII));
      out.flush();
      sleep(stallMillis);
    };
  }

  // As above, but instead of sleeping, waits up to stallMillis for the client to close the connection,
  // counting cut down if it does.
  private static RawHttpServer.Reply stalledBody(long stallMillis, CountDownLatch cut) {
    return (request, connection) -> {
      java.io.OutputStream out = connection.getOutputStream();
      out.write("HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nContent-Length: 100\r\n\r\n{\"id\":".getBytes(
          java.nio.charset.StandardCharsets.US_ASCII));
      out.flush();
      if (closedByClient(connection, (int) stallMillis)) {
        cut.countDown();
      }
    };
  }

  @Test(timeout = 20000)
  public void cancellingWhileAnAttachmentIsCopiedSendsNothingAndDeletesTheCopy() throws Exception {
    api.stubFor(patch(urlEqualTo("/api/rest/v1/site")).willReturn(okJson("{}")));
    FilesClient client = client(builder("key"));
    CancellationToken token = new CancellationToken();
    // The stream cancels the call itself on its third read, so the copy is partway through.
    AtomicReference<Integer> reads = new AtomicReference<>(0);
    java.io.InputStream stream = new java.io.InputStream() {
      @Override
      public int read() {
        return 0;
      }

      @Override
      public int read(byte[] buffer, int offset, int length) {
        reads.set(reads.get() + 1);
        if (reads.get() == 3) {
          token.cancel();
        }
        java.util.Arrays.fill(buffer, offset, offset + length, (byte) 'x');
        return length;
      }
    };
    java.nio.file.Path temporary = java.nio.file.Paths.get(System.getProperty("java.io.tmpdir"));
    final List<java.nio.file.Path> before = attachmentCopies(temporary);
    HashMap<String, Object> parameters = new HashMap<>();
    parameters.put("logo_file", com.files.net.Attachment.fromInputStream(stream).withMemoryThreshold(1));
    HashMap<String, Object> options = new HashMap<>();
    options.put("cancellation", token);
    try {
      client.sites().update(parameters, options);
      fail("Expected the call to be cancelled");
    } catch (OperationCancelledException e) {
      assertEquals(OperationCancelledException.Reason.CANCELLED, e.getReason());
    }
    assertEquals("the copy stopped at the cancellation", Integer.valueOf(3), reads.get());
    api.verify(0, anyRequestedFor(anyUrl()));
    assertEquals("the partial copy was deleted", before, attachmentCopies(temporary));
  }

  private static List<java.nio.file.Path> attachmentCopies(java.nio.file.Path directory) throws java.io.IOException {
    try (java.util.stream.Stream<java.nio.file.Path> files = java.nio.file.Files.list(directory)) {
      return files.filter(p -> p.getFileName().toString().startsWith("files-sdk-attachment-")).sorted().collect(Collectors.toList());
    }
  }

  @Test(timeout = 20000)
  public void interruptingTheCallingThreadAbortsTheRequestAndKeepsTheInterruptStatus() throws Exception {
    api.stubFor(get(urlEqualTo("/api/rest/v1/users/1")).willReturn(okJson("{\"id\":1}").withFixedDelay(10000)));
    FilesClient client = client(builder("key"));
    AtomicReference<String> outcome = new AtomicReference<>();
    Thread caller = new Thread(() -> {
      try {
        client.users().find(1L, null);
        outcome.set("completed");
      } catch (OperationCancelledException e) {
        outcome.set(e.getReason() + " " + Thread.currentThread().isInterrupted());
      }
    });
    caller.start();
    sleep(300);
    caller.interrupt();
    caller.join(5000);
    assertEquals("INTERRUPTED true", outcome.get());
  }

  @Test(timeout = 20000)
  public void closingAClientStopsItsCallsAndLeavesABorrowedTransportOpen() throws Exception {
    api.stubFor(get(urlEqualTo("/api/rest/v1/users/1")).willReturn(okJson("{\"id\":1}").withFixedDelay(10000)));
    api.stubFor(get(urlEqualTo("/api/rest/v1/users/2")).willReturn(okJson("{\"id\":2}")));
    HttpTransport shared = HttpTransport.builder().build();
    try {
      FilesClient first = builder("first").transport(shared).build();
      final FilesClient second = client(builder("second").transport(shared));
      ExecutorService pool = Executors.newSingleThreadExecutor();
      AtomicReference<Throwable> failure = new AtomicReference<>();
      pool.execute(() -> {
        try {
          first.users().find(1L, null);
        } catch (Throwable e) {
          failure.set(e);
        }
      });
      sleep(300);
      first.close();
      first.close();
      pool.shutdown();
      assertTrue(pool.awaitTermination(5, TimeUnit.SECONDS));
      assertEquals("The request was cancelled because its client was closed.", failure.get().getMessage());
      try {
        first.users();
        fail("Expected the closed client to refuse work");
      } catch (IllegalStateException e) {
        assertEquals("This FilesClient has been closed", e.getMessage());
      }
      assertFalse(shared.isClosed());
      assertEquals(Long.valueOf(2), second.users().find(2L, null).getId());
    } finally {
      shared.close();
    }
    assertTrue(shared.isClosed());
  }

  @Test(timeout = 20000)
  public void anApacheClientOfYourOwnServesTwoClientsThatBorrowItAndOutlivesEither() throws Exception {
    WireMockServer proxy = FilesApiTest.loopbackServer();
    proxy.start();
    try {
      proxy.stubFor(get(anyUrl()).willReturn(okJson("{\"id\":1}")));
      // Built before any Files.com client. Its proxy is what reaches the API, whose host name does not
      // resolve, and its interceptor marks every request.
      GuardedApacheClient http = HttpTransport.builder().customize(builder -> builder
          .setProxy(new HttpHost("127.0.0.1", proxy.port()))
          .addInterceptorFirst((org.apache.http.HttpRequestInterceptor) (request, context) -> request.addHeader("X-Trace", "traced")))
          .buildClient();
      try {
        assertEquals("used directly before any client borrows it", "{\"id\":1}", direct(http, "http://api.files.invalid/direct/before"));
        HttpTransport firstTransport = HttpTransport.borrowed(http);
        FilesClient first = FilesClient.builder().apiKey("first-key").apiRoot("http://api.files.invalid").transport(firstTransport).build();
        FilesClient second = client(FilesClient.builder().apiKey("second-key").apiRoot("http://api.files.invalid")
            .transport(HttpTransport.borrowed(http)));
        assertEquals(Long.valueOf(1), first.users().find(1L, null).getId());
        assertEquals("used directly while borrowed", "{\"id\":1}", direct(http, "http://api.files.invalid/direct/while"));
        assertEquals(Long.valueOf(1), second.users().find(1L, null).getId());

        first.close();
        firstTransport.close();
        assertTrue(firstTransport.isClosed());
        assertFalse("a borrowed client stays open", http.isClosed());
        assertEquals(Long.valueOf(1), second.users().find(1L, null).getId());
        assertEquals("used directly after a borrower closed", "{\"id\":1}", direct(http, "http://api.files.invalid/direct/after"));

        List<LoggedRequest> sent = proxy.findAll(anyRequestedFor(anyUrl()));
        assertEquals("three requests of the clients and three of your own", 6, sent.size());
        for (LoggedRequest request : sent) {
          assertTrue(request.getAbsoluteUrl(), request.getAbsoluteUrl().startsWith("http://api.files.invalid"));
          assertEquals("traced", request.getHeader("X-Trace"));
        }
        // Your own requests carry no Files.com credential, though they set one; the clients' each carry
        // their own.
        for (String step : new String[]{"before", "while", "after"}) {
          String target = "http://api.files.invalid/direct/" + step;
          List<LoggedRequest> own = sent.stream().filter(request -> request.getAbsoluteUrl().equals(target)).collect(Collectors.toList());
          assertEquals(target, 1, own.size());
          assertNull(target, own.get(0).getHeader("X-FilesAPI-Key"));
        }
        assertEquals("each client's own credentials", Arrays.asList("first-key", "second-key", "second-key"),
            sent.stream().filter(request -> !request.getAbsoluteUrl().contains("/direct/"))
                .map(request -> request.getHeader("X-FilesAPI-Key")).sorted().collect(Collectors.toList()));
      } finally {
        http.close();
      }
    } finally {
      proxy.stop();
    }
  }

  @Test(timeout = 20000)
  public void closingATransportThatBorrowsAClientStopsOnlyItsOwnRequestsEvenMidBody() throws Exception {
    api.stubFor(get(urlEqualTo("/api/rest/v1/users/2")).willReturn(okJson("{\"id\":2}")));
    CountDownLatch cut = new CountDownLatch(1);
    RawHttpServer stalled = new RawHttpServer(stalledBody(10000, cut));
    CountDownLatch bodyRead = new CountDownLatch(1);
    AtomicBoolean first = new AtomicBoolean(true);
    // Shows when the SDK reads the first response's body: its entity, as received, is read through a
    // stream that marks the first read.
    org.apache.http.HttpResponseInterceptor observeBody = (response, context) -> {
      if (first.compareAndSet(true, false)) {
        long length = response.getEntity().getContentLength();
        InputStream body = response.getEntity().getContent();
        InputStream observedBody = new FilterInputStream(body) {
          @Override
          public int read() throws IOException {
            bodyRead.countDown();
            return super.read();
          }

          @Override
          public int read(byte[] bytes, int offset, int count) throws IOException {
            bodyRead.countDown();
            return super.read(bytes, offset, count);
          }
        };
        response.setEntity(new InputStreamEntity(observedBody, length));
      }
    };
    GuardedApacheClient http = HttpTransport.builder().customize(builder -> builder.addInterceptorLast(observeBody)).buildClient();
    ExecutorService caller = Executors.newSingleThreadExecutor();
    try {
      HttpTransport closing = HttpTransport.borrowed(http);
      // Time limits beyond the test's own: only the transport's close can end the call.
      FilesClient reading = client(FilesClient.builder().apiKey("key").apiRoot(stalled.root())
          .readTimeout(Duration.ofSeconds(60)).callTimeout(Duration.ofSeconds(60)).transport(closing));
      final FilesClient other = client(builder("other").transport(HttpTransport.borrowed(http)));
      Future<?> read = caller.submit(() -> reading.users().find(1L, null));
      assertTrue("the call reads the response's body", bodyRead.await(5, TimeUnit.SECONDS));
      assertFalse("and waits for the rest of it", read.isDone());
      long start = System.nanoTime();
      closing.close();
      try {
        read.get(5, TimeUnit.SECONDS);
        fail("Expected the call to stop when its transport closed");
      } catch (ExecutionException e) {
        // Its body could not be read on, and not for a time limit.
        assertTrue(String.valueOf(e.getCause()), e.getCause() instanceof ApiErrorException.ApiConnectionException);
        assertEquals("The Files.com API response could not be read (response to GET /api/rest/v1/users/1)", e.getCause().getMessage());
        assertFalse(String.valueOf(e.getCause()), causedBy(e, java.net.SocketTimeoutException.class));
      }
      assertTrue(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start) < 5000);
      assertTrue("the transport cut the connection the body was read from", cut.await(5, TimeUnit.SECONDS));
      assertEquals("a response that was cut short is not requested again", 1, stalled.received().size());

      assertFalse("the borrowed client stays open", http.isClosed());
      assertEquals(Long.valueOf(2), other.users().find(2L, null).getId());
      assertEquals("used directly after a borrower closed mid-body", "{\"id\":2}",
          direct(http, "http://localhost:" + api.port() + "/api/rest/v1/users/2"));
      api.verify(1, getRequestedFor(urlEqualTo("/api/rest/v1/users/2")).withoutHeader("X-FilesAPI-Key"));
      try {
        reading.users().find(1L, null);
        fail("Expected the closed transport to refuse the request");
      } catch (IllegalStateException e) {
        assertEquals("The HTTP transport has been closed", e.getMessage());
      }
      try {
        builder("late").transport(closing).build();
        fail("Expected a closed transport to be refused");
      } catch (IllegalStateException e) {
        assertEquals("The transport has been closed", e.getMessage());
      }
    } finally {
      caller.shutdownNow();
      stalled.close();
      http.close();
    }
  }

  @Test(timeout = 20000)
  public void aResponseArrivingAfterItsTransportClosedIsRefusedWhileOtherTransportsGoOn() throws Exception {
    api.stubFor(delete(urlEqualTo("/api/rest/v1/api_key")).willReturn(aResponse().withStatus(204).withHeader("X-Hold", "once")));
    api.stubFor(get(urlEqualTo("/api/rest/v1/users/2")).willReturn(okJson("{\"id\":2}")));
    CountDownLatch arrived = new CountDownLatch(1);
    CountDownLatch release = new CountDownLatch(1);
    AtomicBoolean hold = new AtomicBoolean(true);
    // Holds the first response marked X-Hold as it arrives, before anything of it is used, until
    // released. A hold not released within 10 seconds, or interrupted, fails the request, and so this
    // test, instead of letting the response through.
    GuardedApacheClient http = HttpTransport.builder().customize(builder -> builder.addInterceptorLast(
        (org.apache.http.HttpResponseInterceptor) (response, context) -> {
          if (response.getFirstHeader("X-Hold") != null && hold.compareAndSet(true, false)) {
            arrived.countDown();
            try {
              if (!release.await(10, TimeUnit.SECONDS)) {
                throw new IOException("The held response was not released within 10 seconds");
              }
            } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
              throw new IOException("Interrupted while the response was held", e);
            }
          }
        })).buildClient();
    ExecutorService caller = Executors.newSingleThreadExecutor();
    try {
      HttpTransport closing = HttpTransport.borrowed(http);
      // No retries: its one request, and how that fails, are all the call does.
      FilesClient first = client(builder("first").maxRetries(0).transport(closing));
      final FilesClient second = client(builder("second").transport(HttpTransport.borrowed(http)));
      // A deletion answers 204 with no body: the response alone is the call's result.
      Future<?> deleting = caller.submit(() -> {
        first.apiKeys().deleteCurrent();
        return null;
      });
      assertTrue("the response arrived and is held", arrived.await(10, TimeUnit.SECONDS));
      assertFalse("the call waits for its held response", deleting.isDone());
      closing.close();
      release.countDown();
      try {
        deleting.get(10, TimeUnit.SECONDS);
        fail("Expected the response that arrived after its transport closed to be refused");
      } catch (ExecutionException e) {
        assertTrue(String.valueOf(e.getCause()), e.getCause() instanceof IllegalStateException);
        assertEquals("The HTTP transport has been closed", e.getCause().getMessage());
      }
      api.verify(1, deleteRequestedFor(urlEqualTo("/api/rest/v1/api_key")));

      assertFalse("the borrowed client stays open", http.isClosed());
      second.apiKeys().deleteCurrent();
      api.verify(2, deleteRequestedFor(urlEqualTo("/api/rest/v1/api_key")));
      assertEquals("used directly after a borrower closed", "{\"id\":2}", direct(http, "http://localhost:" + api.port() + "/api/rest/v1/users/2"));
    } finally {
      release.countDown();
      caller.shutdownNow();
      http.close();
    }
  }

  @Test(timeout = 20000)
  public void anOwnedTransportOverYourPoolStopsItsRequestsAndClosesItsClientButNotThePool() throws Exception {
    api.stubFor(get(urlEqualTo("/api/rest/v1/users/2")).willReturn(okJson("{\"id\":2}")));
    CountDownLatch arrived = new CountDownLatch(1);
    CountDownLatch cut = new CountDownLatch(1);
    // Receives the request and holds it, unanswered, until the client closes the connection, or for 10
    // seconds at most.
    RawHttpServer holding = new RawHttpServer((request, connection) -> {
      arrived.countDown();
      if (closedByClient(connection, 10000)) {
        cut.countDown();
      }
    });
    PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();
    ExecutorService caller = Executors.newSingleThreadExecutor();
    try {
      GuardedApacheClient http = HttpTransport.builder().connectionManager(pool).buildClient();
      HttpTransport owned = HttpTransport.owned(http);
      // No retries, and time limits beyond the test's own: only the transport's close can end the call.
      FilesClient slow = client(FilesClient.builder().apiKey("key").apiRoot(holding.root()).maxRetries(0)
          .readTimeout(Duration.ofSeconds(60)).callTimeout(Duration.ofSeconds(60)).transport(owned));
      final FilesClient neighbor = client(builder("key").transport(HttpTransport.borrowed(http)));
      assertEquals("used directly while a transport owns it", "{\"id\":2}", direct(http, "http://localhost:" + api.port() + "/api/rest/v1/users/2"));
      Future<?> waiting = caller.submit(() -> slow.users().find(1L, null));
      assertTrue("the request reached the service", arrived.await(5, TimeUnit.SECONDS));
      assertFalse("and its call waits for the answer", waiting.isDone());
      long start = System.nanoTime();
      owned.close();
      owned.close();
      try {
        waiting.get(5, TimeUnit.SECONDS);
        fail("Expected the owned transport's request to stop when it closed");
      } catch (ExecutionException e) {
        // The SDK's failure of a request whose connection was cut: no time limit, cancellation or
        // refusal before sending.
        assertTrue(String.valueOf(e.getCause()), e.getCause() instanceof SdkException);
        assertFalse(String.valueOf(e.getCause()), e.getCause() instanceof OperationCancelledException);
        assertFalse(String.valueOf(e.getCause()), causedBy(e, java.net.SocketTimeoutException.class));
      }
      assertTrue(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start) < 5000);
      assertTrue("the transport cut the request's connection, which closing a client over a pool you manage would not",
          cut.await(5, TimeUnit.SECONDS));

      assertTrue("the owned client is closed", http.isClosed());
      try {
        neighbor.users().find(2L, null);
        fail("Expected the closed client to refuse the request");
      } catch (IllegalStateException e) {
        assertEquals("The HTTP client has been closed", e.getMessage());
      }
      try {
        direct(http, "http://localhost:" + api.port() + "/api/rest/v1/users/2");
        fail("Expected the closed client to refuse a request of your own");
      } catch (IllegalStateException e) {
        assertEquals("The HTTP client has been closed", e.getMessage());
      }
      try {
        HttpTransport.borrowed(http);
        fail("Expected a closed client to be refused");
      } catch (IllegalStateException e) {
        assertEquals("The HTTP client has been closed", e.getMessage());
      }

      // The pool stays yours, and serves a client built on it again.
      HttpTransport again = HttpTransport.builder().connectionManager(pool).build();
      try {
        assertEquals(Long.valueOf(2), client(builder("key").transport(again)).users().find(2L, null).getId());
      } finally {
        again.close();
      }
    } finally {
      caller.shutdownNow();
      holding.close();
      pool.shutdown();
    }
  }

  @Test
  public void closingADownloadStreamClosesItsResponseEvenWhenItsBodyFailsToClose() throws Exception {
    IOException bodyFailure = new IOException("The body could not be closed");
    assertBodyCloseFailurePreserved(bodyFailure, null);
    assertBodyCloseFailurePreserved(bodyFailure, bodyFailure);
    assertBodyCloseFailurePreserved(new IOException("Another body close failure"), new AssertionError("The response could not be closed"));

    // A response that cannot be closed is read and closed as before.
    BasicHttpResponse plain = new BasicHttpResponse(HttpVersion.HTTP_1_1, 200, "OK");
    plain.setEntity(new InputStreamEntity(new ByteArrayInputStream("xyz".getBytes(StandardCharsets.UTF_8)), 3));
    FilesApacheHttpInputStream plainStream = new FilesApacheHttpInputStream(plain);
    assertEquals('x', plainStream.read());
    plainStream.close();
  }

  private static void assertBodyCloseFailurePreserved(IOException bodyFailure, Throwable responseFailure) throws Exception {
    ClosingResponse response = new ClosingResponse(responseFailure);
    InputStream body = new ByteArrayInputStream("abc".getBytes(StandardCharsets.UTF_8)) {
      @Override
      public void close() throws IOException {
        throw bodyFailure;
      }
    };
    response.setEntity(new InputStreamEntity(body, 3));
    FilesApacheHttpInputStream stream = new FilesApacheHttpInputStream(response);
    assertEquals('a', stream.read());
    try {
      stream.close();
      fail("Expected the body's failure");
    } catch (IOException e) {
      assertSame(bodyFailure, e);
    }
    assertTrue("the response is closed all the same", response.closed);
    if (responseFailure == null || responseFailure == bodyFailure) {
      assertEquals("the first failure is not suppressed onto itself", 0, bodyFailure.getSuppressed().length);
    } else {
      assertArrayEquals("a different cleanup failure is retained", new Throwable[]{responseFailure}, bodyFailure.getSuppressed());
    }
  }

  // A response its reader can close, recording the close even when it reports a cleanup failure.
  private static final class ClosingResponse extends BasicHttpResponse implements CloseableHttpResponse {
    volatile boolean closed;
    private final Throwable failure;

    ClosingResponse(Throwable failure) {
      super(HttpVersion.HTTP_1_1, 200, "OK");
      this.failure = failure;
    }

    @Override
    public void close() throws IOException {
      closed = true;
      if (failure instanceof IOException) {
        throw (IOException) failure;
      }
      if (failure instanceof Error) {
        throw (Error) failure;
      }
      if (failure instanceof RuntimeException) {
        throw (RuntimeException) failure;
      }
    }
  }

  @Test(timeout = 20000)
  public void yourRequestsSentWhileAClientSendsItsOwnCarryNoCredentialsAndLeaveItsCredentialsAlone() throws Exception {
    api.stubFor(get(urlEqualTo("/api/rest/v1/users/1")).willReturn(okJson("{\"id\":1}")));
    api.stubFor(get(urlEqualTo("/nested/same")).willReturn(okJson("{}")));
    api.stubFor(get(urlEqualTo("/nested/after")).willReturn(okJson("{}")));
    WireMockServer foreign = FilesApiTest.loopbackServer();
    foreign.start();
    try {
      foreign.stubFor(get(urlEqualTo("/nested/foreign")).willReturn(okJson("{}")));
      AtomicReference<GuardedApacheClient> self = new AtomicReference<>();
      AtomicBoolean once = new AtomicBoolean(true);
      // As the client's request to the API is about to be sent, its interceptor sends two requests of
      // its own with the same Apache client: one to the API's origin, and one to another.
      GuardedApacheClient http = HttpTransport.builder().customize(builder -> builder.addInterceptorFirst(
          (org.apache.http.HttpRequestInterceptor) (request, context) -> {
            if (once.compareAndSet(true, false)) {
              direct(self.get(), "http://localhost:" + api.port() + "/nested/same");
              direct(self.get(), "http://localhost:" + foreign.port() + "/nested/foreign");
            }
          })).buildClient();
      self.set(http);
      try {
        FilesClient client = client(builder("outer-key").transport(HttpTransport.borrowed(http)));
        assertEquals(Long.valueOf(1), client.users().find(1L, null).getId());
        assertEquals("{}", direct(http, "http://localhost:" + api.port() + "/nested/after"));

        api.verify(1, getRequestedFor(urlEqualTo("/nested/same")).withoutHeader("X-FilesAPI-Key"));
        foreign.verify(1, getRequestedFor(urlEqualTo("/nested/foreign")).withoutHeader("X-FilesAPI-Key"));
        api.verify(1, getRequestedFor(urlEqualTo("/api/rest/v1/users/1")).withHeader("X-FilesAPI-Key", equalTo("outer-key")));
        api.verify(1, getRequestedFor(urlEqualTo("/nested/after")).withoutHeader("X-FilesAPI-Key"));
      } finally {
        http.close();
      }
    } finally {
      foreign.stop();
    }
  }

  @Test(timeout = 20000)
  public void anOwnedTransportStopsItsIdleConnectionEvictorWhenTheClientCloses() {
    api.stubFor(get(urlEqualTo("/api/rest/v1/users/2")).willReturn(okJson("{\"id\":2}")));
    List<String> before = evictors();
    FilesClient client = builder("key").transport(HttpTransport.builder().idleConnectionTimeout(Duration.ofSeconds(1))).build();
    client.users().find(2L, null);
    assertTrue("the client's transport runs an evictor", evictors().size() > before.size());
    client.close();
    long end = System.nanoTime() + TimeUnit.SECONDS.toNanos(5);
    while (evictors().size() > before.size() && System.nanoTime() < end) {
      sleep(20);
    }
    assertEquals(before, evictors());
  }

  @Test(timeout = 20000)
  public void aCustomizedTransportAddsInstrumentationAndAProxyWithoutLosingTheCredentialRules() throws Exception {
    WireMockServer proxy = FilesApiTest.loopbackServer();
    proxy.start();
    try {
      proxy.stubFor(get(urlEqualTo("http://api.files.invalid/api/rest/v1/users/1")).atPriority(1).willReturn(okJson("{\"id\":1}")));
      proxy.stubFor(get(anyUrl()).atPriority(5).willReturn(okJson("{\"id\":1}")));
      FilesClient client = client(FilesClient.builder().apiKey("proxied-key").apiRoot("http://api.files.invalid")
          .transport(HttpTransport.builder().customize(http -> http
              .setProxy(new HttpHost("127.0.0.1", proxy.port()))
              .addInterceptorFirst((org.apache.http.HttpRequestInterceptor) (request, context) -> request.addHeader("X-Trace", "traced")))));

      assertEquals(Long.valueOf(1), client.users().find(1L, null).getId());

      LoggedRequest sent = proxy.findAll(anyRequestedFor(anyUrl())).get(0);
      assertEquals("traced", sent.getHeader("X-Trace"));
      assertEquals("proxied-key", sent.getHeader("X-FilesAPI-Key"));
      assertTrue(sent.getAbsoluteUrl(), sent.getAbsoluteUrl().startsWith("http://api.files.invalid"));
    } finally {
      proxy.stop();
    }
  }

  @Test(timeout = 20000)
  public void aClientsReadTimeoutCoversTheBodyAndIsNotAnSdkCancellation() throws Exception {
    RawHttpServer server = new RawHttpServer(stalledBody(3000));
    try {
      FilesClient client = client(FilesClient.builder().apiKey("key").apiRoot(server.root()).readTimeout(Duration.ofMillis(300)));
      try {
        client.users().find(1L, null);
        fail("Expected the read to time out");
      } catch (ApiErrorException.ApiConnectionException e) {
        assertEquals("The Files.com API response could not be read (response to GET /api/rest/v1/users/1)", e.getMessage());
        assertTrue(String.valueOf(e.getCause()), e.getCause() instanceof java.net.SocketTimeoutException);
      }
      assertEquals("a response that was cut short is not requested again", 1, server.received().size());
    } finally {
      server.close();
    }

    // The same through an Apache client of your own, which the client borrows: the client's time
    // limits are set on each request it sends with it.
    RawHttpServer adoptedServer = new RawHttpServer(stalledBody(3000));
    try (GuardedApacheClient http = HttpTransport.builder().buildClient()) {
      FilesClient client = client(FilesClient.builder().apiKey("key").apiRoot(adoptedServer.root()).readTimeout(Duration.ofMillis(300))
          .transport(HttpTransport.borrowed(http)));
      try {
        client.users().find(1L, null);
        fail("Expected the read to time out");
      } catch (ApiErrorException.ApiConnectionException e) {
        assertTrue(String.valueOf(e.getCause()), e.getCause() instanceof java.net.SocketTimeoutException);
      }
      assertEquals(1, adoptedServer.received().size());
    } finally {
      adoptedServer.close();
    }
  }

  @Test(timeout = 20000)
  public void aReplacedHttpProcessorStillCannotSendCredentialsToAnotherOrigin() throws Exception {
    WireMockServer other = FilesApiTest.loopbackServer();
    other.start();
    try {
      api.stubFor(get(urlEqualTo("/api/rest/v1/users/1"))
          .willReturn(aResponse().withStatus(302).withHeader("Location", "http://localhost:" + other.port() + "/sink")));
      other.stubFor(get(urlEqualTo("/sink")).willReturn(okJson("{\"id\":1}")));
      api.stubFor(get(urlEqualTo("/api/rest/v1/users/2"))
          .willReturn(aResponse().withStatus(302).withHeader("Location", "/api/rest/v1/users/3")));
      api.stubFor(get(urlEqualTo("/api/rest/v1/users/3")).willReturn(okJson("{\"id\":3}")));
      // An ordinary processor of Apache's standard request interceptors, replacing the builder's own.
      FilesClient client = client(builder("processor-key").transport(HttpTransport.builder().customize(http -> http.setHttpProcessor(
          org.apache.http.protocol.HttpProcessorBuilder.create()
              .add(new org.apache.http.protocol.RequestContent())
              .add(new org.apache.http.protocol.RequestTargetHost())
              .add(new org.apache.http.protocol.RequestConnControl())
              .add(new org.apache.http.protocol.RequestUserAgent("custom"))
              .add(new org.apache.http.protocol.RequestExpectContinue(true))
              .build()))));

      assertEquals(Long.valueOf(1), client.users().find(1L, null).getId());
      assertEquals(Long.valueOf(3), client.users().find(2L, null).getId());

      other.verify(1, getRequestedFor(urlEqualTo("/sink")).withoutHeader("X-FilesAPI-Key"));
      api.verify(1, getRequestedFor(urlEqualTo("/api/rest/v1/users/3")).withHeader("X-FilesAPI-Key", equalTo("processor-key")));

      // The same through an Apache client of your own, which the client borrows, whose processor adds a
      // credential after the SDK's headers: it still goes only to the API root.
      try (GuardedApacheClient http = HttpTransport.builder().customize(builder -> builder.setHttpProcessor(
               org.apache.http.protocol.HttpProcessorBuilder.create()
                   .add(new org.apache.http.protocol.RequestContent())
                   .add(new org.apache.http.protocol.RequestTargetHost())
                   .add((org.apache.http.HttpRequestInterceptor) (request, context) -> request.setHeader("X-Files-Workspace-Id", "injected"))
                   .build())).buildClient()) {
        FilesClient adopted = client(builder("adopted-key").transport(HttpTransport.borrowed(http)));
        assertEquals(Long.valueOf(1), adopted.users().find(1L, null).getId());
        assertEquals(Long.valueOf(3), adopted.users().find(2L, null).getId());
      }
      other.verify(2, getRequestedFor(urlEqualTo("/sink")).withoutHeader("X-FilesAPI-Key").withoutHeader("X-Files-Workspace-Id"));
      api.verify(1, getRequestedFor(urlEqualTo("/api/rest/v1/users/3")).withHeader("X-FilesAPI-Key", equalTo("adopted-key"))
          .withHeader("X-Files-Workspace-Id", equalTo("injected")));
    } finally {
      other.stop();
    }
  }

  @Test(timeout = 20000)
  public void aTransportFailuresOwnMessageIsNeverCopiedIntoWarningsOrErrors() {
    String sentinel = "SYNTHETIC_SECRET=value local=/private/input";
    FilesClient client = client(builder("key").maxRetries(1).transport(HttpTransport.builder().customize(http -> http.addInterceptorLast(
        (org.apache.http.HttpRequestInterceptor) (request, context) -> {
          throw new java.io.IOException(sentinel);
        }))));
    SdkWarnings logs = new SdkWarnings(org.apache.logging.log4j.Level.DEBUG);
    try {
      client.users().find(1L, null);
      fail("Expected the request to fail");
    } catch (ApiErrorException.ApiConnectionException e) {
      assertEquals("The request failed: an I/O failure (IOException) (response to GET /api/rest/v1/users/1)", e.getMessage());
      assertNull(e.getCause());
    } finally {
      logs.close();
    }
    List<String> ordinary = logs.events().stream().filter(event -> !event.startsWith("DEBUG")).collect(Collectors.toList());
    assertEquals(Arrays.asList("WARN com.files.net.ClientRuntime Retrying the GET request (retry 1 of 1) in 1 ms after an I/O failure (IOException)"),
        ordinary);
    assertTrue("the failure itself is logged at DEBUG", logs.events().stream()
        .anyMatch(event -> event.startsWith("DEBUG com.files.net.ClientRuntime The GET request failed") && event.contains(sentinel)));
  }

  @Test(timeout = 20000)
  public void refusedCallsOfAClosedClientKeepNothingOfTheirCallers() throws Exception {
    FilesClient client = builder("key").build();
    com.files.operations.UserOperations users = client.users();
    CancellationToken token = new CancellationToken();
    HashMap<String, Object> options = new HashMap<>();
    options.put("cancellation", token);
    options.put("timeout", Duration.ofMinutes(5));
    client.close();
    Thread caller = new Thread(() -> {
      for (int i = 0; i < 3; i++) {
        try {
          users.find(1L, null, options);
          throw new AssertionError("the closed client sent a request");
        } catch (IllegalStateException e) {
          assertEquals("This FilesClient has been closed", e.getMessage());
        }
      }
    });
    caller.start();
    caller.join(5000);
    java.lang.ref.WeakReference<Thread> finished = new java.lang.ref.WeakReference<>(caller);
    caller = null;
    for (int i = 0; i < 100 && finished.get() != null; i++) {
      System.gc();
      Thread.sleep(20);
    }
    assertNull("nothing still holds the refused calls' thread", finished.get());
  }

  private static List<String> evictors() {
    return Thread.getAllStackTraces().keySet().stream().filter(Thread::isAlive).map(Thread::getName)
        .filter(name -> name.startsWith("Connection evictor")).sorted().collect(Collectors.toList());
  }

  private static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  // Sends a GET with an Apache client of your own, as an application would, with a Files.com
  // credential it set itself, and returns the response's body.
  private static String direct(GuardedApacheClient http, String url) throws IOException {
    HttpGet request = new HttpGet(url);
    request.setHeader("X-FilesAPI-Key", "direct-key");
    try (CloseableHttpResponse response = http.execute(request)) {
      return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
    }
  }

  // Whether the client closes its end of a connection the server holds, within millis: a read of what
  // else it sends then ends, or fails as the connection is reset, instead of timing out.
  private static boolean closedByClient(java.net.Socket connection, int millis) throws IOException {
    connection.setSoTimeout(millis);
    try {
      return connection.getInputStream().read() < 0;
    } catch (java.net.SocketTimeoutException e) {
      return false;
    } catch (IOException e) {
      return true;
    }
  }

  // Whether failure, or any cause of it, is of type.
  private static boolean causedBy(Throwable failure, Class<? extends Throwable> type) {
    for (Throwable cause = failure; cause != null; cause = cause.getCause()) {
      if (type.isInstance(cause)) {
        return true;
      }
    }
    return false;
  }
}
