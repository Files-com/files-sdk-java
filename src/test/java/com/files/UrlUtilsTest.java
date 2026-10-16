package com.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.files.util.UrlUtils;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
      testData = objectMapper.readValue(new File("shared/url_test_data.json"),
          new TypeReference<Map<String, List<String>>>() {});
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
  public void encodeUrlPath() throws MalformedURLException {
    List<String> urls = Arrays.asList(
        "",
        "/",
        "path",
        "colon:name.txt",
        "colon:folder/child.txt",
        "path with space",
        "path/with space/subdir",
        "path/a+b%#?é.txt"
    );
    List<String> sanitizedUrls = Arrays.asList(
        "",
        "%2F",
        "path",
        "colon%3Aname.txt",
        "colon%3Afolder%2Fchild.txt",
        "path%20with%20space",
        "path%2Fwith%20space%2Fsubdir",
        "path%2Fa%2Bb%25%23%3F%C3%A9.txt"
    );
    // zip the urls and iterate through them
    for (int i = 0; i < urls.size(); i++) {
      String sanitizedUrl = null;
      try {
        sanitizedUrl = UrlUtils.encodeUrlPath(urls.get(i));
      } catch (Exception e) {
        e.printStackTrace();
        fail("Sanitization failed for URL: " + urls.get(i) + " with error: " + e.getClass().getName() + " : " + e.getMessage());
      }
      assertEquals(sanitizedUrls.get(i), sanitizedUrl);
    }
  }

  @Test
  public void redactUrlsRemovesCredentialsQueriesAndFragments() {
    assertEquals("Upload to https://bucket.example:8443/folder/key.txt failed",
        UrlUtils.redactUrls("Upload to https://user:p@ss@bucket.example:8443/folder/key.txt?X-Amz-Signature=secret#part failed"));
    assertEquals("Tried 'https://a.example/x' and <sftp://b.example/y>.",
        UrlUtils.redactUrls("Tried 'https://a.example/x?token=one' and <sftp://b.example/y#two>."));
    assertEquals("No URL here?key=value", UrlUtils.redactUrls("No URL here?key=value"));
    assertEquals(null, UrlUtils.redactUrls(null));
  }

  @Test
  public void redactUrlsKeepsApostrophesThatBelongToTheUrl() {
    assertEquals("Missing https://bucket.example/key.txt",
        UrlUtils.redactUrls("Missing https://user:private'secret@bucket.example/key.txt?token=PRIVATE_QUERY#PRIVATE_FRAGMENT"));
    assertEquals("Missing https://bucket.example/O'Brien/key.txt",
        UrlUtils.redactUrls("Missing https://bucket.example/O'Brien/key.txt?token=PRIVATE_QUERY#PRIVATE_FRAGMENT"));
    assertEquals("Tried 'https://bucket.example/O'Brien/key.txt'.",
        UrlUtils.redactUrls("Tried 'https://bucket.example/O'Brien/key.txt?token=PRIVATE_QUERY'."));
    // An apostrophe does not end a URL, but the next URL's scheme does.
    for (String separator : new String[]{"','", "';'"}) {
      assertEquals("Tried 'https://a.example/x" + separator + "https://b.example/y'.",
          UrlUtils.redactUrls("Tried 'https://a.example/x" + separator
              + "https://user:PRIVATE_PASSWORD@b.example/y?token=PRIVATE_QUERY'."));
    }
    assertEquals("Next: https://a.example/x", UrlUtils.redactUrls("Next: https://a.example/x?next=https://user:PRIVATE_PASSWORD@b.example/y"));
  }

  @Test
  public void redactUrlsReadsLongTextWithoutRescanningIt() {
    StringBuilder word = new StringBuilder();
    for (int i = 0; i < 100000; i++) {
      word.append(i % 2 == 0 ? 'a' : '1');
    }
    // Each position of a long word could otherwise start a new search for a scheme.
    assertEquals(word.toString(), UrlUtils.redactUrls(word.toString()));
    assertEquals(word + " https://host.example/path",
        UrlUtils.redactUrls(word + " https://user:pw@host.example/path?token=PRIVATE_QUERY"));
  }
}
