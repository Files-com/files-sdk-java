package com.files.net;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectReader;
import com.files.exceptions.SdkException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.apache.http.HttpEntity;

// Whether a storage server's error answer to a transfer request says that the signed URL the request
// was sent to has expired, though the URL's own expiry, by this computer's clock, may still be to come.
// Only an HTTP 400 or 403 can. A body that starts as JSON does ({, [ or ", after any byte order mark and
// white space) says so only as exactly one JSON object, read strictly to its end, whose top-level type
// is download_request_expired or upload_request_expired, whatever its other members are: an array, a
// string, malformed JSON or JSON cut short never does. Any other body says so when its first
// PREFIX_BYTES bytes hold, without regard to case, a storage service's expiry text: "Request has
// expired" (Amazon S3), <Code>ExpiredToken</Code> (Amazon, Google) or "Signature not valid in the
// specified time frame" (Azure), however long the body. Nothing of the body is kept. A URL alone gives
// nothing to renew it with: only a transfer that has the Files.com request its URL came from renews it.
final class StorageExpiry {
  // How much of an error answer's body is looked at. An expiry answer is far smaller.
  static final int PREFIX_BYTES = 4096;

  private static final String[] PROVIDER_ANSWERS = {
      "request has expired",
      "<code>expiredtoken</code>",
      "signature not valid in the specified time frame"
  };

  private static final char BYTE_ORDER_MARK = 0xFEFF;

  private static final List<String> FILES_TYPES = Arrays.asList("download_request_expired", "upload_request_expired");

  // One JSON value with nothing after it but white space. ClientRuntime.MAPPER, as it reads API answers,
  // ignores whatever follows the first value.
  private static final ObjectReader STRICT_JSON =
      ClientRuntime.MAPPER.reader().with(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);

  private StorageExpiry() {
  }

  // Reads at most PREFIX_BYTES + 1 bytes of an error answer's body, the one more telling whether the body
  // ends within the first PREFIX_BYTES, and judges them; any other status reads nothing. The rest of the
  // body is left unread, and the stream is not closed: the caller releases the response as it otherwise
  // would. A body that cannot be read says nothing.
  static boolean saysExpired(int status, HttpEntity entity) {
    if (!isRefusal(status) || entity == null) {
      return false;
    }
    byte[] prefix = new byte[PREFIX_BYTES + 1];
    int length = 0;
    try {
      InputStream content = entity.getContent();
      while (content != null && length < prefix.length) {
        int read = content.read(prefix, length, prefix.length - length);
        if (read < 0) {
          break;
        }
        length += read;
      }
    } catch (IOException | IllegalStateException e) {
      return false;
    }
    String start = new String(prefix, 0, Math.min(length, PREFIX_BYTES), StandardCharsets.UTF_8);
    if (!startsAsJson(start)) {
      return namesProviderExpiry(start);
    }
    try {
      return length <= PREFIX_BYTES && isFilesExpiry(STRICT_JSON.readTree(prefix, 0, length));
    } catch (IOException e) {
      return false;
    }
  }

  // As above, for an error answer whose whole body the client has already read as text (ApiErrors).
  static boolean saysExpired(int status, String body) {
    if (!isRefusal(status) || body == null) {
      return false;
    }
    if (!startsAsJson(body)) {
      byte[] start = body.substring(0, Math.min(body.length(), PREFIX_BYTES)).getBytes(StandardCharsets.UTF_8);
      return namesProviderExpiry(new String(start, 0, Math.min(start.length, PREFIX_BYTES), StandardCharsets.UTF_8));
    }
    try {
      return isFilesExpiry(STRICT_JSON.readTree(body.charAt(0) == BYTE_ORDER_MARK ? body.substring(1) : body));
    } catch (IOException e) {
      return false;
    }
  }

  private static boolean isRefusal(int status) {
    return status == 400 || status == 403;
  }

  // Whether text starts as JSON does: with {, [ or ", after any byte order mark and JSON white space.
  private static boolean startsAsJson(String text) {
    for (int i = 0; i < text.length(); i++) {
      char c = text.charAt(i);
      if (c == '{' || c == '[' || c == '"') {
        return true;
      }
      if (c != BYTE_ORDER_MARK && c != ' ' && c != '\t' && c != '\r' && c != '\n') {
        return false;
      }
    }
    return false;
  }

  private static boolean namesProviderExpiry(String start) {
    String text = start.toLowerCase(Locale.ROOT);
    for (String answer : PROVIDER_ANSWERS) {
      if (text.contains(answer)) {
        return true;
      }
    }
    return false;
  }

  private static boolean isFilesExpiry(JsonNode json) {
    JsonNode type = json != null && json.isObject() ? json.get("type") : null;
    return type != null && type.isTextual() && FILES_TYPES.contains(type.textValue());
  }

  // Thrown where a storage download that a URL alone opens (ClientRuntime.openDownload) is answered that
  // its URL has expired, for a caller that can renew the URL; error is the answer's failure, which a
  // caller that cannot renew it throws as it is.
  static final class Refused extends RuntimeException {
    private static final long serialVersionUID = 1L;

    final SdkException error;

    Refused(SdkException error) {
      super(error.getMessage(), null, false, false);
      this.error = error;
    }
  }
}
