package com.files;

import static org.junit.Assert.assertEquals;

import com.files.util.PathUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Test;

public class PathUtilsTest {
  @Test
  public void validateNormalizationForComparison() throws FileNotFoundException, IOException {
    String jsonTestPairs = new String(Files.readAllBytes(Paths.get("shared/normalization_for_comparison_test_data.json")));

    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode pairList = objectMapper.readValue(jsonTestPairs, JsonNode.class);

    for (JsonNode pair : pairList) {
      String rawText = pair.get(0).asText();
      String normalizedText = pair.get(1).asText();
      assertEquals(PathUtils.normalize_for_comparison(rawText), normalizedText);
    }
  }
}
