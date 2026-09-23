package com.files;

import static org.junit.Assert.assertEquals;

import com.files.util.PathUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Locale;
import java.nio.file.Paths;
import org.junit.Test;

public class PathUtilsTest {
  @Test
  public void validateNormalizationForComparison() throws FileNotFoundException, IOException {
    String jsonTestPairs = new String(Files.readAllBytes(Paths.get("shared/normalization_for_comparison_test_data.json")), StandardCharsets.UTF_8);

    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode pairList = objectMapper.readValue(jsonTestPairs, JsonNode.class);

    for (JsonNode pair : pairList) {
      String rawText = pair.get(0).asText();
      String normalizedText = pair.get(1).asText();
      assertEquals(normalizedText, PathUtils.normalize_for_comparison(rawText));
      assertEquals(normalizedText, PathUtils.normalize_for_comparison(normalizedText));
    }
  }

  @Test
  public void serverComparisonExamplesAreLocaleIndependent() throws IOException {
    JsonNode examples = new ObjectMapper().readTree(Paths.get("shared/comparison_examples.json").toFile());
    Locale previous = Locale.getDefault();
    try {
      Locale.setDefault(new Locale("tr", "TR"));
      assertEquals("ii", PathUtils.normalize_for_comparison("Iİ"));
      for (JsonNode pair : examples) {
        assertEquals(pair.get(1).asText(), PathUtils.normalize_for_comparison(pair.get(0).asText()));
      }
    } finally {
      Locale.setDefault(previous);
    }
  }

  @Test
  public void validateNormalizePreservesPathIdentity() {
    assertEquals("remote/path/to/file.txt", PathUtils.normalize("/../../remote\\path//./to/file.txt"));
    assertEquals("remote/path/to/file.txt", PathUtils.normalize("remote/../path/to/file.txt"));
    assertEquals("_/RemoteServers/42/remote/path/to/file.txt",
        PathUtils.normalize("_/RemoteServers/42/" + "/../../remote\\path//./to/file.txt"));
  }
}
