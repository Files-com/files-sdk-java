package com.files;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.files.util.UrlUtils;

import org.junit.Before;
import org.junit.Test;

public class UrlUtilsTest {
  private Map<String, List<String>> testData;
  private final ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .build();

  @Before
  public void before() {
    try {
      testData = objectMapper.readValue(new File("shared/url_test_data.json"), HashMap.class);
    } catch (IOException e) {
      fail("No test data found");
    }
  }

  @Test
  public void validLink() throws MalformedURLException {
    for (String url : testData.get("substitute_urls")) {
      assertFalse(UrlUtils.isExpired(new URL(String.format(url, UrlUtils.TIME_DATE_FORMAT.format(Date.from(Instant.now().plusSeconds(60))), 3 * 60))));
    }
  }

  @Test
  public void expiredLink() throws MalformedURLException {
    for (String url : testData.get("substitute_urls")) {
      assertTrue(UrlUtils.isExpired(new URL(String.format(url, UrlUtils.TIME_DATE_FORMAT.format(Date.from(Instant.now().minusSeconds(3 * 60))), 60))));
    }
  }

  @Test
  public void errorLink() throws MalformedURLException {
    for (String url : testData.get("error_urls")) {
      assertFalse(UrlUtils.isExpired(new URL(url)));
    }
  }
}
