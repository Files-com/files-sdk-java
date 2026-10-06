package com.files;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A WireMock server that test.sh runs in its own JDK 17 process (see test-server/servers.sh).
 * Tests stub and verify requests through WireMock's admin API, so they run on Java 8 with only the
 * SDK's dependencies. Admin calls bypass the SDK, leaving its connection pool to the requests under test.
 */
final class TestServer {
  private static final ObjectMapper JSON = new ObjectMapper();

  private final String baseUrl;

  private TestServer(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  static TestServer fromEnvironment(String variable) {
    String url = System.getenv(variable);
    if (url == null || url.isEmpty()) {
      throw new IllegalStateException(variable + " is not set; test.sh starts the test servers and sets it");
    }
    return new TestServer(url);
  }

  static Response response(int status) {
    return new Response(status);
  }

  static RequestPattern request(String method, String url) {
    return new RequestPattern(method, url);
  }

  String baseUrl() {
    return baseUrl;
  }

  String url(String path) {
    return baseUrl + path;
  }

  // Removes every stub and forgets every request received.
  void reset() throws IOException {
    admin("/__admin/reset", JSON.createObjectNode());
  }

  // Answers requests with this method and exactly this URL, path and query, with the response.
  void stub(String method, String url, Response response) throws IOException {
    ObjectNode mapping = JSON.createObjectNode();
    mapping.putObject("request").put("method", method).put("url", url);
    mapping.set("response", response.json);
    admin("/__admin/mappings", mapping);
  }

  void verify(int expected, RequestPattern pattern) throws IOException {
    assertEquals("requests matching " + pattern.json, expected, count(pattern));
  }

  // Requires at least one matching request.
  void verify(RequestPattern pattern) throws IOException {
    assertTrue("no request matching " + pattern.json, count(pattern) > 0);
  }

  private int count(RequestPattern pattern) throws IOException {
    return admin("/__admin/requests/count", pattern.json).get("count").asInt();
  }

  private JsonNode admin(String path, JsonNode body) throws IOException {
    HttpURLConnection connection = (HttpURLConnection)new URL(baseUrl + path).openConnection();
    try {
      connection.setRequestMethod("POST");
      connection.setDoOutput(true);
      connection.setRequestProperty("Content-Type", "application/json");
      try (OutputStream out = connection.getOutputStream()) {
        out.write(JSON.writeValueAsBytes(body));
      }
      int status = connection.getResponseCode();
      String text = read(status < 400 ? connection.getInputStream() : connection.getErrorStream());
      if (status / 100 != 2) {
        throw new IOException("WireMock " + path + " answered " + status + ": " + text);
      }
      return text.isEmpty() ? JSON.createObjectNode() : JSON.readTree(text);
    } finally {
      connection.disconnect();
    }
  }

  private static String read(InputStream in) throws IOException {
    if (in == null) {
      return "";
    }
    try (InputStream stream = in) {
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      byte[] buffer = new byte[8192];
      for (int read = stream.read(buffer); read != -1; read = stream.read(buffer)) {
        bytes.write(buffer, 0, read);
      }
      return new String(bytes.toByteArray(), StandardCharsets.UTF_8);
    }
  }

  static final class Response {
    private final ObjectNode json = JSON.createObjectNode();
    private ObjectNode headers;

    private Response(int status) {
      json.put("status", status);
    }

    Response withHeader(String name, String value) {
      if (headers == null) {
        headers = json.putObject("headers");
      }
      headers.put(name, value);
      return this;
    }

    Response withBody(String body) {
      json.put("body", body);
      return this;
    }
  }

  static final class RequestPattern {
    private final ObjectNode json = JSON.createObjectNode();
    private ObjectNode headers;

    private RequestPattern(String method, String url) {
      json.put("method", method).put("url", url);
    }

    RequestPattern withHeader(String name, String value) {
      headers().putObject(name).put("equalTo", value);
      return this;
    }

    RequestPattern withoutHeader(String name) {
      headers().putObject(name).put("absent", true);
      return this;
    }

    RequestPattern withRequestBody(byte[] body) {
      json.putArray("bodyPatterns").addObject().put("binaryEqualTo", Base64.getEncoder().encodeToString(body));
      return this;
    }

    private ObjectNode headers() {
      if (headers == null) {
        headers = json.putObject("headers");
      }
      return headers;
    }
  }
}
