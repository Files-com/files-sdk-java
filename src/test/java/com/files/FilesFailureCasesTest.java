package com.files;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.files.exceptions.ApiErrorException;
import com.files.models.User;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

/**
 * The shared failure-case matrix (the mock server's {@code scenarios/failure-cases.json}) through
 * this SDK's public static {@link User#find(Long, HashMap)} and {@link User#update(Long, HashMap)}.
 * {@code legacy-file-tests.sh} runs it through the mock server's launcher, which resets the
 * simulator to the scenario's users and adds its fault rules. The default client sends only to the
 * launcher's simulator, with the definition's {@code max_network_retries} as
 * {@code maxNetworkRetries}. Each
 * call's result or exception is compared with the case's expectation: an
 * {@link ApiErrorException}'s status, type and message, an
 * {@link ApiErrorException.ApiConnectionException} (a request that got no answer) once the
 * retries are spent, or the user found. {@code FILES_MOCK_OUT/failure-cases.json} records each
 * case's outcome, and the launcher's failure cases check then compares the journal and the fault
 * rules' decisions with each case's expected wire outcome. Without that launcher the test is
 * skipped.
 */
public class FilesFailureCasesTest {
  private static final ObjectMapper JSON = new ObjectMapper();

  @Test
  public void everyFailureCase() throws IOException {
    final MockSimulator simulator = MockSimulator.launchedWith("failure-cases");
    final JsonNode definition = simulator.scenario().get("failure_cases");
    final String previousApiKey = FilesClient.apiKey;
    final FilesConfig config = FilesConfig.getInstance();
    final String previousApiRoot = config.getApiRoot();
    final String previousRetries = String.valueOf(config.getMaxNetworkRetries());
    final ArrayNode results = JSON.createArrayNode();
    final List<String> failures = new ArrayList<>();
    try {
      FilesClient.apiKey = simulator.apiKey();
      FilesClient.setProperty("apiRoot", simulator.url());
      FilesClient.setProperty("maxNetworkRetries", definition.get("max_network_retries").asText());
      for (JsonNode testCase : definition.get("cases")) {
        final List<String> problems = new ArrayList<>();
        int index = 0;
        for (JsonNode expected : testCase.get("expect")) {
          index++;
          // As JSON reads it back, so that numbers compare by value whatever type held them.
          final JsonNode actual = JSON.readTree(call(testCase).toString());
          if (!actual.equals(expected)) {
            problems.add("call " + index + " gave " + actual + ", not " + expected);
          }
        }
        final String id = testCase.get("id").asText();
        final ObjectNode result = results.addObject();
        result.put("id", id);
        result.put("outcome", problems.isEmpty() ? "passed" : "failed");
        result.put("failure", problems.isEmpty() ? null : String.join("; ", problems));
        if (!problems.isEmpty()) {
          failures.add(id + ": " + String.join("; ", problems));
        }
      }
    } finally {
      FilesClient.apiKey = previousApiKey;
      FilesClient.setProperty("apiRoot", previousApiRoot);
      FilesClient.setProperty("maxNetworkRetries", previousRetries);
    }

    final ObjectNode report = JSON.createObjectNode();
    report.put("format", "files-failure-cases/1");
    report.put("sdk", "java");
    report.put("runtime", System.getProperty("java.version"));
    report.set("cases", results);
    JSON.writerWithDefaultPrettyPrinter().writeValue(
        Paths.get(System.getenv("FILES_MOCK_OUT"), "failure-cases.json").toFile(), report);
    assertEquals("cases that did not pass", List.of(), failures);
  }

  // What one call did, in the expectation's terms.
  private static JsonNode call(JsonNode testCase) {
    final ObjectNode actual = JSON.createObjectNode();
    final long userId = testCase.get("user_id").asLong();
    final String call = testCase.get("call").asText();
    try {
      if ("users.update".equals(call)) {
        final HashMap<String, Object> parameters = new HashMap<>();
        for (Map.Entry<String, JsonNode> param : testCase.get("params").properties()) {
          parameters.put(param.getKey(), param.getValue().asText());
        }
        User.update(userId, parameters);
        actual.put("updated", true);
      } else if ("users.find".equals(call)) {
        final User user = User.find(userId, new HashMap<>());
        final ObjectNode found = actual.putObject("user");
        found.put("id", user.getId());
        found.put("username", user.getUsername());
      } else {
        actual.put("unknown call", call);
      }
    } catch (ApiErrorException.ApiConnectionException error) {
      actual.putObject("error").put("network", true);
    } catch (ApiErrorException error) {
      final ObjectNode answered = actual.putObject("error");
      answered.put("status", error.getHttpCode());
      answered.put("type", error.getType());
      answered.put("message", error.getError());
    } catch (RuntimeException error) {
      actual.put("unexpected", error.getClass().getName() + ": " + error.getMessage());
    }
    return actual;
  }
}
