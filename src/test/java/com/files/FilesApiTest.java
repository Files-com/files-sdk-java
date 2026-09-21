package com.files;

import com.files.exceptions.ApiErrorException;
import com.files.models.Bundle;
import com.files.models.File;
import com.files.models.Folder;
import com.files.models.User;
import com.files.net.HttpMethods;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class FilesApiTest {
  private WireMockServer wireMockServer;

  @Before
  public void setUp() throws IOException {
    wireMockServer = new WireMockServer(WireMockConfiguration.options().dynamicPort());
    wireMockServer.start();

    String baseUrl = "http://localhost:" + wireMockServer.port();
    FilesClient.setProperty("apiRoot", baseUrl);
    FilesClient.apiKey = "test-key";
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
    WireMockServer storageServer = new WireMockServer(WireMockConfiguration.options().dynamicPort());
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
    final String body =
      "{"
    +    "\"error\": \"Error saving model.\","
    +    "\"http-code\": 422,"
    +    "\"instance\": \"508503b0-2423-4edc-a46f-77d77ac4a7e3\","
    +    "\"model_errors\": {"
    +      "\"username\": ["
    +        "\"Username must not contain multiple spaces together\","
    +        "\"Username must not begin or end with a space\""
    +      "]"
    +    "},"
    +    "\"model_error_keys\": {"
    +      "\"username\": ["
    +        "\"multiple_spaces\","
    +        "\"leading_trailing_space\""
    +      "]"
    +    "},"
    +    "\"errors\": ["
    +      "\"Username must not contain multiple spaces together\","
    +      "\"Username must not begin or end with a space\""
    +    "],"
    +    "\"title\": \"Model Save Error\","
    +    "\"type\": \"processing-failure/model-save-error\""
    +  "}";

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
      assert (e instanceof ApiErrorException.ModelSaveErrorException) : "Expected ModelSaveErrorException, but got: " + e.getClass().getName();
      ApiErrorException.ModelSaveErrorException exception = (ApiErrorException.ModelSaveErrorException)e;
      assert ("processing-failure/model-save-error".equals(exception.getType()));
    }
  }

  @Test
  public void handleNotFound() throws Exception {
    final String body =
        "{"
      +   "\"type\": \"not-found\","
      +   "\"http-code\": 404,"
      +   "\"error\": \"Not Found.  This may be related to your permissions.\""
      + "}";

    wireMockServer.stubFor(get(urlEqualTo("/api/rest/v1/folders/%2Fmissing"))
        .willReturn(aResponse()
            .withStatus(404)
            .withHeader("Content-Type", "application/json; charset=utf-8")
            .withBody(body)));

    try {
      Folder.listFor("/missing", null).all();
      fail("Expected exception did not occur");
    } catch (RuntimeException e) {
      assert (e instanceof ApiErrorException.NotFoundException) : "Expected NotFoundException, but got: " + e.getClass().getName();
      ApiErrorException.NotFoundException exception = (ApiErrorException.NotFoundException)e;
      assert ("not-found".equals(exception.getType()));
      assert (exception.getHttpCode() == 404);
      assert ("Not Found.  This may be related to your permissions.".equals(exception.getError()));
      assert exception.getHeaders().stream()
          .anyMatch(h -> "Content-Type".equalsIgnoreCase(h.getName())
                         && "application/json; charset=utf-8".equalsIgnoreCase(h.getValue()));
    }
  }

  @Test
  public void handleNoResponseData() throws Exception {
    wireMockServer.stubFor(post(urlEqualTo("/api/rest/v1/bundles/1/share"))
        .willReturn(aResponse()
            .withStatus(200)
            .withBody("")));

    // Should not throw an exception
    Bundle.share((long)1, null);
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
    assert (count == 0);
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
    } catch (RuntimeException e) {
      assert (e instanceof ApiErrorException.ServerErrorException) : "Expected ServerErrorException, but got: " + e.getClass().getName();
      ApiErrorException.ServerErrorException exception = (ApiErrorException.ServerErrorException)e;
      assert (e.getMessage().equals(body));
    }
  }

  @Test
  public void handleInvalidResponse() throws Exception {
    final String body =
      "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
    + "<text>"
    +   "<para>test</para>"
    + "</text>";
    wireMockServer.stubFor(get(urlEqualTo("/api/rest/v1/folders/%2F"))
        .willReturn(aResponse()
            .withStatus(400)
            .withBody(body)));

    try {
      Folder.listFor("/", null).all();
      fail("Expected exception did not occur");
    } catch (RuntimeException e) {
      assert(e instanceof ApiErrorException.InvalidResponseException);
      ApiErrorException.InvalidResponseException exception = (ApiErrorException.InvalidResponseException)e;
      assert(exception.getMessage().startsWith("Unexpected character"));
    }
  }

  @Test
  public void handleHostnameMismatch() throws Exception {
    final String body =
        "{"
      +   "\"type\": \"not-authenticated/lockout-region-mismatch\","
      +   "\"http-code\": 403,"
      +   "\"title\": \"Lockout Region Mismatch\","
      +   "\"error\":\"You have connected to a URL that has different security settings than those required for your site.\","
      +   "\"data\": {"
      +      "\"host\": \"test.host\""
      +   "}"
      + "}";

    wireMockServer.stubFor(get(urlEqualTo("/api/rest/v1/folders/%2F"))
      .willReturn(aResponse()
        .withStatus(403)
        .withHeader("x-files-host", "test.host")
        .withBody(body)));

    try {
      Folder.listFor("/", null).all();
      fail("Expected exception did not occur");
    } catch (RuntimeException e) {
      assert (e instanceof ApiErrorException.LockoutRegionMismatchException) : "Expected LockoutRegionMismatchException, but got: " + e.getClass().getName();
      ApiErrorException.LockoutRegionMismatchException exception = (ApiErrorException.LockoutRegionMismatchException)e;
      assert(exception.getHttpCode() == 403);
      assert("Lockout Region Mismatch".equals(exception.getTitle()));
      assert("not-authenticated/lockout-region-mismatch".equals(exception.getType()));
      assert("You have connected to a URL that has different security settings than those required for your site.".equals(exception.getError()));
      assert(exception.getData().get("host").equals("test.host"));
    }
  }


  @Test
  public void handleRegionMismatch() throws Exception {
    final String body =
        "{"
      +   "\"type\": \"not-authenticated/lockout-region-mismatch\","
      +   "\"http-code\": 401,"
      +   "\"title\": \"Lockout Region Mismatch\","
      +   "\"error\":\"Your account must login using a different server, test.host.\","
      +   "\"data\": {"
      +      "\"host\": \"test.host\""
      +   "}"
      + "}";
    wireMockServer.stubFor(get(urlEqualTo("/api/rest/v1/folders/%2F"))
      .willReturn(aResponse()
        .withStatus(401)
        .withHeader("Content-Type", "application/json; charset=utf-8")
        .withBody(body)));

    try {
      Folder.listFor("/", null).all();
      fail("Expected exception did not occur");
    } catch (RuntimeException e) {
      assert (e instanceof ApiErrorException.LockoutRegionMismatchException) : "Expected LockoutRegionMismatchException, but got: " + e.getClass().getName();
      ApiErrorException.LockoutRegionMismatchException exception = (ApiErrorException.LockoutRegionMismatchException)e;
      assert(exception.getHttpCode() == 401);
      assert("Lockout Region Mismatch".equals(exception.getTitle()));
      assert("not-authenticated/lockout-region-mismatch".equals(exception.getType()));
      assert("Your account must login using a different server, test.host.".equals(exception.getError()));
      assert(exception.getData().get("host").equals("test.host"));
    }
  }
}
