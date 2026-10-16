package com.files.util;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
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
  private static final DateTimeFormatter SIGNATURE_DATE = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");

  // Punctuation that ends a removed query or fragment belongs to the surrounding prose.
  private static final String CLOSING_PUNCTUATION = ".,;:!?)]'";

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

  /**
   * When a signed URL stops working, as its own query says: the signing time plus the lifetime of
   * an AWS, Files.com or Google signature ({@code X-Amz-Date} and {@code X-Amz-Expires}, and the
   * like), or an Azure signature's {@code se}. Returns null when the URL states no expiry, states
   * one twice, or states one that cannot be read. The earliest applies when several are stated.
   */
  public static Instant expiresAt(String url) {
    int query = url == null ? -1 : url.indexOf('?');
    if (query < 0) {
      return null;
    }
    int fragment = url.indexOf('#', query);
    Map<String, List<String>> parameters = new LinkedHashMap<String, List<String>>();
    for (String parameter : url.substring(query + 1, fragment < 0 ? url.length() : fragment).split("&")) {
      String[] parts = parameter.split("=", 2);
      if (parts.length == 2) {
        try {
          parameters.computeIfAbsent(URLDecoder.decode(parts[0], StandardCharsets.UTF_8.name()), key -> new ArrayList<String>())
              .add(URLDecoder.decode(parts[1], StandardCharsets.UTF_8.name()));
        } catch (UnsupportedEncodingException | IllegalArgumentException e) {
          return null;
        }
      }
    }
    Instant earliest = null;
    for (String prefix : new String[]{"X-Amz-", "X-Files-", "X-Goog-"}) {
      String date = single(parameters, prefix + "Date");
      String lifetime = single(parameters, prefix + "Expires");
      if (date != null && lifetime != null) {
        try {
          Instant signed = LocalDateTime.parse(date, SIGNATURE_DATE).toInstant(ZoneOffset.UTC);
          earliest = earlier(earliest, signed.plusSeconds(Long.parseLong(lifetime)));
        } catch (DateTimeException | NumberFormatException | ArithmeticException e) {
          return null;
        }
      }
    }
    String azure = single(parameters, "se");
    if (azure != null) {
      try {
        earliest = earlier(earliest, OffsetDateTime.parse(azure).toInstant());
      } catch (DateTimeException e) {
        return null;
      }
    }
    return earliest;
  }

  private static String single(Map<String, List<String>> parameters, String name) {
    List<String> values = parameters.get(name);
    return values != null && values.size() == 1 ? values.get(0) : null;
  }

  private static Instant earlier(Instant first, Instant second) {
    return first == null || second.isBefore(first) ? second : first;
  }

  public static boolean isExpired(URL url) {
    try {
      Instant expDate = expirationDate(url);
      return expDate == null ? false : Instant.now().isAfter(expDate);
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Removes the user info, query, and fragment, where credentials and signatures live, from every
   * absolute URL in the text. The scheme, host, port, and path remain, and so does the text around
   * each URL. The text is read once, so the cost grows linearly with its length.
   */
  public static String redactUrls(String text) {
    if (text == null) {
      return null;
    }
    StringBuilder redacted = new StringBuilder(text.length());
    int copied = 0;
    int separator = text.indexOf("://");
    while (separator >= 0) {
      int start = schemeStart(text, separator, copied);
      int searchFrom = separator + 3;
      if (start < separator) {
        int end = urlEnd(text, searchFrom);
        redacted.append(text, copied, start).append(withoutSecrets(text.substring(start, end)));
        copied = end;
        searchFrom = end;
      }
      separator = text.indexOf("://", searchFrom);
    }
    return redacted.append(text, copied, text.length()).toString();
  }

  // A scheme is a letter followed by letters, digits, "+", "-", or "."; it cannot begin at or before
  // the end of text that has already been copied.
  private static int schemeStart(String text, int separator, int limit) {
    int start = separator;
    while (start > limit && isSchemeCharacter(text.charAt(start - 1))) {
      start--;
    }
    while (start < separator && !isAsciiLetter(text.charAt(start))) {
      start++;
    }
    return start;
  }

  // A URL ends at whitespace, a double quote, or an angle bracket. Apostrophes are legal in user
  // info and paths, so they do not end one; before its query or fragment, a URL instead ends where
  // the next URL's scheme begins, as in 'https://a.example/x','https://b.example/y'. A query or
  // fragment runs on to the end, because it is removed whole.
  private static int urlEnd(String text, int from) {
    boolean inQueryOrFragment = false;
    for (int end = from; end < text.length(); end++) {
      char c = text.charAt(end);
      if (Character.isWhitespace(c) || "\"<>".indexOf(c) >= 0) {
        return end;
      }
      if (c == '?' || c == '#') {
        inQueryOrFragment = true;
      } else if (!inQueryOrFragment && text.startsWith("://", end)) {
        int nextScheme = schemeStart(text, end, from);
        if (nextScheme < end) {
          return nextScheme;
        }
      }
    }
    return text.length();
  }

  private static String withoutSecrets(String url) {
    int authority = url.indexOf("://") + 3;
    int path = indexOfAny(url, "/?#", authority);
    int host = Math.max(authority, url.lastIndexOf('@', path - 1) + 1);
    int query = indexOfAny(url, "?#", path);
    int prose = url.length();
    while (prose > query && CLOSING_PUNCTUATION.indexOf(url.charAt(prose - 1)) >= 0) {
      prose--;
    }
    return url.substring(0, authority) + url.substring(host, query) + url.substring(prose);
  }

  private static int indexOfAny(String text, String characters, int from) {
    for (int i = from; i < text.length(); i++) {
      if (characters.indexOf(text.charAt(i)) >= 0) {
        return i;
      }
    }
    return text.length();
  }

  private static boolean isSchemeCharacter(char c) {
    return isAsciiLetter(c) || (c >= '0' && c <= '9') || c == '+' || c == '-' || c == '.';
  }

  private static boolean isAsciiLetter(char c) {
    return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
  }

  public static String encodeUrlPath(String urlPath) {
    try {
      return URLEncoder.encode(urlPath, StandardCharsets.UTF_8.name()).replace("+", "%20");
    } catch (UnsupportedEncodingException e) {
      throw new IllegalStateException("UTF-8 is unavailable", e);
    }
  }
}
