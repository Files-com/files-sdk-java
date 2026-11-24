package com.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.files.util.HeaderUtils;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
      testData = objectMapper.readValue(new File("shared/header_test_data.json"),
          new TypeReference<List<Map<String, Object>>>() {});
    } catch (IOException e) {
      fail("No test data found");
    }
  }

  @Test
  public void parsesHeaders() {
    for (Map<String, Object> data : testData) {
      @SuppressWarnings("unchecked")
      Map<String, List<String>> headers = (Map<String, List<String>>) data.get("headers");

      if (data.get("result") == null) {
        assertNull(HeaderUtils.retryAfterSeconds(headers));
        continue;
      }
      int expected = (int) data.get("result");
      List<String> retryValues = headers.get("Retry-After");
      if (!retryValues.get(0).contains("%s")) {
        assertEquals(expected, (int) HeaderUtils.retryAfterSeconds(headers));
        continue;
      }
      long before = System.currentTimeMillis();
      Date retryAt = new Date(before + expected * 1000L);
      retryValues.set(0, String.format(retryValues.get(0), HeaderUtils.HTTP_DATE_FORMAT.format(retryAt)));

      int delay = HeaderUtils.retryAfterSeconds(headers);
      long after = System.currentTimeMillis();
      // An HTTP date has whole seconds, so it names the start of the second retryAt is in. The
      // delay is what remained until then, rounded up, when the header was read: the expected
      // delay if that was at once, and less by however long reading it took.
      long named = retryAt.getTime() / 1000 * 1000;
      assertTrue(delay + " seconds", delay <= expected && delay >= secondsUntil(named, after));
    }
  }

  // The whole seconds from now until the time, rounded up, as HeaderUtils computes them.
  private static int secondsUntil(long time, long now) {
    return (int) Math.max(0, (time - now + 999) / 1000);
  }
}
