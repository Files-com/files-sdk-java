package com.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.files.util.HeaderUtils;

import org.junit.Before;
import org.junit.Test;


public class HeaderUtilsTest {
  private List<Map<String, Object>> testData;
  private final ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .build();

  @Before
  public void before() {
    try {
      testData = objectMapper.readValue(new File("shared/header_test_data.json"), List.class);
    } catch (IOException e) {
      fail("No test data found");
    }
  }

  @Test
  public void parsesHeaders() {
    for (Map<String, Object> data : testData) {
      Map<String, List<String>> headers = (Map<String, List<String>>)data.get("headers");

      if (data.get("result") == null) {
        assertNull(HeaderUtils.retryAfterSeconds(headers));
      } else {
        List<String> retryValues = headers.get("Retry-After");
        String newValue = String.format(retryValues.get(0), HeaderUtils.HTTP_DATE_FORMAT.format(Date.from(Instant.now().plusSeconds((int)data.get("result")))));
        retryValues.set(0, newValue);

        assertEquals((int)HeaderUtils.retryAfterSeconds(headers), (int)data.get("result"));
      }
    }
  }
}
