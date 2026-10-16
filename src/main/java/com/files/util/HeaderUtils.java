package com.files.util;

import com.files.FilesConfig;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class HeaderUtils {
  // This is NOT an ISO Date format, but an HTTP Date per https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Date
  public static final DateFormat HTTP_DATE_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");

  private static Integer cappedRetrySeconds(int retrySeconds) {
    return retrySeconds <= FilesConfig.getInstance().getMaximumRetrySeconds() ? retrySeconds : null;
  }

  public static Integer retryAfterSeconds(Map<String, List<String>> headers) {
    if (headers.containsKey("Retry-After")) {
      String retryValue = headers.get("Retry-After").get(0);
      try {
        return cappedRetrySeconds(Integer.parseInt(retryValue));
      } catch (NumberFormatException e) {
        try {
          // Round up to whole seconds, so the delay is always long enough; a time already past means
          // no delay.
          long millis = HTTP_DATE_FORMAT.parse(retryValue).getTime() - Date.from(Instant.now()).getTime();
          return cappedRetrySeconds((int) Math.max(0, (millis + 999) / 1000));
        } catch (ParseException e2) {
          return null;
        }
      }
    }

    return null;
  }
}
