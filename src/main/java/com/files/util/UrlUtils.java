package com.files.util;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UrlUtils {
  private static class Expire {
    public String expiresStartDate;
    public String expiresDuration;
    public String expiresDate;

    public Expire(String expiresStartDate, String expiresDuration, String expiresDate) {
      this.expiresStartDate = expiresStartDate;
      this.expiresDuration = expiresDuration;
      this.expiresDate = expiresDate;
    }
  }

  public static final DateFormat TIME_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");

  private static final List<Expire> EXPIRE_LIST = Collections.unmodifiableList(
      new ArrayList<Expire>() {
        {
          add(new Expire("X-Amz-Date", "X-Amz-Expires", null));
          add(new Expire("X-Files-Date", "X-Files-Expires", null));
          add(new Expire("X-Goog-Date", "X-Goog-Expires", null));
          add(new Expire("sp", null, "se"));
        }
      });

  protected UrlUtils() {
  }

  private static Map<String, List<String>> splitQuery(URL url) {
    if (url.getQuery().isEmpty() || url.getQuery() == null) {
      return Collections.emptyMap();
    }
    return Arrays.stream(url.getQuery().split("&"))
        .map(UrlUtils::splitQueryParameter)
        .collect(Collectors.groupingBy(SimpleImmutableEntry::getKey, LinkedHashMap::new,
            Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
  }

  private static SimpleImmutableEntry<String, String> splitQueryParameter(String parameter) {
    final String[] parts = parameter.split("=", 2);

    try {
      return new SimpleImmutableEntry<>(
          URLDecoder.decode(parts[0], StandardCharsets.UTF_8.name()),
          URLDecoder.decode(parts[1], StandardCharsets.UTF_8.name()));
    } catch (UnsupportedEncodingException e) {
      return new SimpleImmutableEntry<>(parts[0], parts[1]);
    }
  }

  private static Instant expirationDate(URL url) {
    Map<String, List<String>> queryParams = splitQuery(url);

    Date startDate = null;
    for (Expire expire : EXPIRE_LIST) {
      if (expire.expiresDate != null && queryParams.containsKey(expire.expiresStartDate)
          && queryParams.get(expire.expiresStartDate).size() == 1) {
        try {
          return TIME_DATE_FORMAT.parse(queryParams.get(expire.expiresStartDate).get(0).split("=", 2)[1]).toInstant();
        } catch (ParseException e) {
          continue;
        }
      } else if (queryParams.containsKey(expire.expiresStartDate)
          && queryParams.get(expire.expiresStartDate).size() == 1) {
        try {
          startDate = TIME_DATE_FORMAT.parse(queryParams.get(expire.expiresStartDate).get(0));
        } catch (ParseException e) {
          continue;
        }
      }

      if (startDate != null && expire.expiresDuration != null && queryParams.get(expire.expiresDuration).size() == 1) {
        return startDate.toInstant().plusSeconds(Long.parseLong(queryParams.get(expire.expiresDuration).get(0)));
      }
    }

    return null;
  }

  public static boolean isExpired(URL url) {
    try {
      Instant expDate = expirationDate(url);
      return expDate == null ? false : Instant.now().isAfter(expDate);
    } catch (Exception e) {
      return false;
    }
  }

  public static String encodeUrlPath(String urlPath) {
    try {
      return new URI(null, null, urlPath, null).getRawPath();
    } catch (URISyntaxException ex) {
      // NOOP
    }
    return urlPath;
  }
}
