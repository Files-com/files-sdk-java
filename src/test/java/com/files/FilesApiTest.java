package com.files;

import com.files.exceptions.ApiErrorException;
import com.files.models.Folder;
import com.files.models.User;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public class FilesApiTest {
  public static MockWebServer mockWebServer;
  public static HttpUrl baseUrl;

  @Before
  public void setUp() throws IOException {
    mockWebServer = new MockWebServer();
    mockWebServer.start();
    baseUrl = mockWebServer.url("/");
    FilesClient.setProperty("apiRoot", baseUrl.toString());
    FilesClient.apiKey = "test-key";
  }

  @After
  public void tearDown() throws IOException {
    mockWebServer.shutdown();
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
    mockWebServer.enqueue(new MockResponse().setResponseCode(400).addHeader("Content-Type", "application/json; charset=utf-8").setBody(body));
    HashMap<String, Object> parameters = new HashMap<String, Object>();
    parameters.put("username", "testuser");

    try {
      User user = new User(parameters);
      user.save();
    } catch (ApiErrorException e) {
      assert(e instanceof ApiErrorException.ModelSaveErrorException);
      ApiErrorException.ModelSaveErrorException exception = (ApiErrorException.ModelSaveErrorException)e;
      assert("processing-failure/model-save-error".equals(exception.getType()));
    }
  }

  @Test
  public void handleNotFound() throws Exception {
    final String body = 
        "{"
      +   "\"type\": \"not-found/folder-not-found\","
      +   "\"http-code\": 404,"
      +   "\"error\": \"Folder missing not found.\""
      + "}";
    mockWebServer.enqueue(new MockResponse().setResponseCode(404).addHeader("Content-Type", "application/json; charset=utf-8").setBody(body));

    try {
      Folder.listFor("/missing", null).all();
      fail("Expected exception did not occur");
    } catch (RuntimeException e) {
      assert(e instanceof ApiErrorException.FolderNotFoundException);
      ApiErrorException.FolderNotFoundException exception = (ApiErrorException.FolderNotFoundException)e;
      assert("not-found/folder-not-found".equals(exception.getType()));
      assert(exception.getHttpCode() == 404);
      assert("Folder missing not found.".equals(exception.getError()));
      assert("application/json; charset=utf-8".equals(exception.getHeaders().get("Content-Type").get(0)));
    }
  }

  @Test
  public void handleEmptyResponse() throws Exception {
    mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("[]"));

    int count = 0;
    for (Folder folder : Folder.listFor("/missing", null).listAutoPaging()) {
      count++;
    }
    assert(count == 0);
  }

  @Test
  public void handleBadGateway() throws Exception {
    mockWebServer.enqueue(new MockResponse().setResponseCode(502).setBody("Bad Gateway"));

    try {
      Folder.listFor("/", null).all();
      fail("Expected exception did not occur");
    } catch (RuntimeException e) {
      assert(e instanceof ApiErrorException.ServerErrorException);
      ApiErrorException.ServerErrorException exception = (ApiErrorException.ServerErrorException)e;
      assert("Bad Gateway".equals(e.getMessage()));
    }
  }

  @Test
  public void handleInvalidResponse() throws Exception {
    final String body =
      "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
    + "<text>"
    +   "<para>test</para>"
    + "</text>";
    mockWebServer.enqueue(new MockResponse().setResponseCode(400).setBody(body));

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
    final String body = "You have connected to a URL that has different security settings than those required for your site.";
    mockWebServer.enqueue(new MockResponse().addHeader("x-files-host", "test.example.com").setResponseCode(403).setBody(body));

    try {
      Folder.listFor("/", null).all();
      fail("Expected exception did not occur");
    } catch (RuntimeException e) {
      assert(e instanceof ApiErrorException.AuthenticationException);
      ApiErrorException.AuthenticationException exception = (ApiErrorException.AuthenticationException)e;
      assert(exception.getMessage().equals(body));
      assert("test.example.com".equals(exception.getHeaders().get("x-files-host").get(0)));
    }
  }

  @Test
  public void handleRegionMismatch() throws Exception {
    final String body = 
        "{"
      +   "\"type\": \"not-authenticated/lockout-region-mismatch\","
      +   "\"http-code\": 401,"
      +   "\"title\": \"Lockout Region Mismatch\""
      + "}";
    mockWebServer.enqueue(new MockResponse().setResponseCode(401).addHeader("Content-Type", "application/json; charset=utf-8").setBody(body));

    try {
      Folder.listFor("/", null).all();
      fail("Expected exception did not occur");
    } catch (RuntimeException e) {
      assert(e instanceof ApiErrorException.LockoutRegionMismatchException);
      ApiErrorException.LockoutRegionMismatchException exception = (ApiErrorException.LockoutRegionMismatchException)e;
      assert(exception.getHttpCode() == 401);
      assert("Lockout Region Mismatch".equals(exception.getTitle()));
    }
  }
}
