package com.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.Arrays;
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

  @Test
  public void sanitizeUrl() throws MalformedURLException {
    // Some test URLs in a list, like a literal list of three urls with spaces in paths
    List<String> urls = Arrays.asList(
        "https://example.com/path",
        "https://example.com/path with space",
        "https://example.com/path/with space/subdir"
    );
    List<String> sanitizedUrls = Arrays.asList(
        "https://example.com/path",
        "https://example.com/path%20with%20space",
        "https://example.com/path/with%20space/subdir"
    );
    // zip the urls and iterate through them
    for (int i = 0; i < urls.size(); i++) {
      String sanitizedUrl = null;
      try {
        sanitizedUrl = UrlUtils.sanitizeUrl(urls.get(i));
      } catch (Exception e) {
        e.printStackTrace();
        fail("Sanitization failed for URL: " + urls.get(i) + " with error: " + e.getClass().getName() + " : " + e.getMessage());
      }
      assertEquals(sanitizedUrls.get(i), sanitizedUrl);
    }
  }
}
