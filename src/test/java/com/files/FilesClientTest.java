package com.files;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.anyRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
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
import com.files.models.File;
import com.files.models.FileUploadPart;
import com.files.models.User;
import com.files.net.HttpTransport;
import com.files.net.UploadOptions;
import com.files.net.UploadResult;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import org.apache.http.HttpHost;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FilesClientTest {
  private static final String REGION_MISMATCH = "{\"type\":\"not-authenticated/lockout-region-mismatch\",\"http-code\":403,"
      + "\"title\":\"Lockout Region Mismatch\",\"error\":\"Your account must login using a different server\","
      + "\"data\":{\"host\":\"%s\"}}";

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
  public void retriesUseOneBudgetAndLogOnlyTheRetriesThatHappen() {
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
  }

  @Test(timeout = 20000)
  public void only429And502OrAboveAndA500ToAGetAreRetried() {
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

    // HTTP 500 is retried in answer to a GET, which cannot have changed anything.
    api.resetAll();
    api.stubFor(get(urlEqualTo("/api/rest/v1/users/1")).inScenario("flaky").whenScenarioStateIs(STARTED)
        .willReturn(aResponse().withStatus(500)).willSetStateTo("ok"));
    api.stubFor(get(urlEqualTo("/api/rest/v1/users/1")).inScenario("flaky").whenScenarioStateIs("ok")
        .willReturn(okJson("{\"id\":1}")));
    assertEquals(Long.valueOf(1), client.users().find(1L, null).getId());
    api.verify(2, getRequestedFor(urlEqualTo("/api/rest/v1/users/1")));

    // In answer to another method, HTTP 500 is not retried, HTTP 502 or above is, and HTTP 501 never.
    HashMap<String, Object> parameters = new HashMap<>();
    parameters.put("username", "new-user");
    for (int status : new int[]{500, 501, 502, 504}) {
      api.resetAll();
      api.stubFor(post(urlEqualTo("/api/rest/v1/users")).willReturn(aResponse().withStatus(status)));
      try {
        client.users().create(parameters, null);
        fail("Expected HTTP " + status + " to fail");
      } catch (ApiErrorException.ApiConnectionException e) {
        api.verify(status >= 502 ? 4 : 1, postRequestedFor(urlEqualTo("/api/rest/v1/users")));
      }
    }
  }

  @Test(timeout = 20000)
  public void aRegionMismatchMovesTheClientToTheNamedServerWithinTheSameRetryBudget() {
    WireMockServer region = FilesApiTest.loopbackServer();
    region.start();
    try {
      String regionRoot = "http://localhost:" + region.port();
      api.stubFor(get(urlEqualTo("/api/rest/v1/users/1"))
          .willReturn(aResponse().withStatus(403).withBody(String.format(REGION_MISMATCH, "eu.example.test"))));
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
    for (FilesClient client : new FilesClient[]{client(builder("key")), client(builder("key").regionRelocation(RegionRelocationPolicy.DISABLED))}) {
      try {
        client.users().find(1L, null);
        fail("Expected the region mismatch");
      } catch (ApiErrorException.LockoutRegionMismatchException e) {
        assertEquals("files.com.attacker.test", e.getData().get("host"));
      }
    }
    api.verify(2, getRequestedFor(urlEqualTo("/api/rest/v1/users/1")));
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
}
