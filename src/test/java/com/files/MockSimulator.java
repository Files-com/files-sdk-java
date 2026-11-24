package com.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * The simulation server that the mock server's launcher, {@code with-scenario.rb}, started for this
 * run, as the launcher's environment describes it:
 *
 * <pre>
 * ruby generated/mock-server/with-scenario.rb --scenario NAME --out OUT -- COMMAND
 * </pre>
 *
 * <p>Tests reset it and read its journal through its control endpoints. Their SDK calls go to
 * {@link #url()}, a loopback address, with {@link #apiKey()}, the launcher's placeholder key: the
 * simulator accepts any credential, and this is never a Files.com one.
 */
final class MockSimulator {
  private static final ObjectMapper JSON = new ObjectMapper();
  private static final int CONTRACT_VERSION = 3;

  private final String url;
  private final String apiKey;
  private final JsonNode scenario;
  private final Path evidence;

  private MockSimulator(String url, String apiKey, JsonNode scenario, Path evidence) {
    this.url = url;
    this.apiKey = apiKey;
    this.scenario = scenario;
    this.evidence = evidence;
  }

  /**
   * The server the launcher started with the scenario {@code scenarioName}, once it has answered
   * readiness as that run's server. The calling test is skipped when no launcher started one, and
   * fails when the launcher's environment names another scenario, is incomplete, or names a server
   * that is not the run's own on loopback.
   */
  static MockSimulator launchedWith(String scenarioName) throws IOException {
    String scenarioFile = System.getenv("FILES_MOCK_SCENARIO_FILE");
    assumeTrue("Runs only against the simulator that with-scenario.rb --scenario " + scenarioName + " starts",
        scenarioFile != null && !scenarioFile.isEmpty());
    assertEquals("the launcher's scenario", scenarioName, required("FILES_MOCK_SCENARIO"));
    String url = required("FILES_MOCK_SERVER_URL");
    URI address = URI.create(url);
    assertEquals("the simulator's scheme", "http", address.getScheme());
    assertEquals("the simulator's host, which is loopback only", "127.0.0.1", address.getHost());
    JsonNode scenario = JSON.readTree(Paths.get(scenarioFile).toFile());
    assertEquals("the scenario file's name", scenarioName, scenario.path("name").asText());
    MockSimulator simulator = new MockSimulator(url, required("FILES_MOCK_API_KEY"), scenario,
        Paths.get(required("FILES_MOCK_OUT"), scenarioName));
    JsonNode ready = simulator.request("GET", "/__files_mock/v1/ready", null, 200);
    assertEquals("ready", ready.path("status").asText());
    assertEquals("simulation", ready.path("mode").asText());
    assertEquals(CONTRACT_VERSION, ready.path("contract_version").asInt());
    assertEquals("the run's own server", required("FILES_MOCK_INSTANCE"), ready.path("instance").asText());
    assertEquals(required("FILES_MOCK_SCHEMA_SHA256"), ready.path("schema_sha256").asText());
    return simulator;
  }

  private static String required(String name) {
    String value = System.getenv(name);
    assertTrue(name + " must be set by with-scenario.rb", value != null && !value.isEmpty());
    return value;
  }

  String url() {
    return url;
  }

  String apiKey() {
    return apiKey;
  }

  JsonNode scenario() {
    return scenario;
  }

  /**
   * Resets the simulator to the scenario's {@code reset} body, which also empties its journal, and
   * returns the new epoch.
   */
  long reset() throws IOException {
    JsonNode answer = request("POST", "/__files_mock/v1/reset", scenario.get("reset"), 200);
    assertTrue("the reset's epoch", answer.path("epoch").canConvertToLong());
    return answer.path("epoch").asLong();
  }

  /**
   * The journal of the requests since the last reset, as served, also saved as
   * {@code name}.journal.json in the launcher's output directory, under the scenario's name.
   */
  JsonNode saveJournal(String name) throws IOException {
    JsonNode journal = request("GET", "/__files_mock/v1/journal", null, 200);
    Files.createDirectories(evidence);
    JSON.writerWithDefaultPrettyPrinter().writeValue(evidence.resolve(name + ".journal.json").toFile(), journal);
    return journal;
  }

  /**
   * The saved journal of the requests since the reset that began {@code epoch}, which must be
   * complete.
   *
   * @see #saveJournal(String)
   */
  JsonNode journal(long epoch, String name) throws IOException {
    JsonNode journal = saveJournal(name);
    assertEquals("the journal's epoch", epoch, journal.path("epoch").asLong());
    assertTrue("the journal is complete", journal.path("complete").asBoolean(false));
    assertEquals("dropped journal entries", 0, journal.path("dropped").asInt(-1));
    return journal;
  }

  /**
   * The journal entries for {@code operation}, in the order the simulator received them.
   */
  static List<JsonNode> entries(JsonNode journal, String operation) {
    List<JsonNode> found = new ArrayList<>();
    for (JsonNode entry : journal.path("entries")) {
      if (operation.equals(entry.path("operation").asText())) {
        found.add(entry);
      }
    }
    return found;
  }

  private JsonNode request(String method, String path, JsonNode body, int expectedStatus) throws IOException {
    HttpURLConnection connection = (HttpURLConnection) URI.create(url + path).toURL().openConnection(Proxy.NO_PROXY);
    try {
      connection.setConnectTimeout(5000);
      connection.setReadTimeout(30000);
      connection.setRequestMethod(method);
      if (body != null) {
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        try (OutputStream out = connection.getOutputStream()) {
          out.write(JSON.writeValueAsBytes(body));
        }
      }
      int status = connection.getResponseCode();
      InputStream stream = status >= 400 ? connection.getErrorStream() : connection.getInputStream();
      ByteArrayOutputStream answer = new ByteArrayOutputStream();
      if (stream != null) {
        try (InputStream in = stream) {
          in.transferTo(answer);
        }
      }
      assertEquals(method + " " + path + " answered " + answer, expectedStatus, status);
      return JSON.readTree(answer.toByteArray());
    } finally {
      connection.disconnect();
    }
  }
}
