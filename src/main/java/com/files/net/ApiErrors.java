package com.files.net;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.files.ResponseError;
import com.files.exceptions.ApiErrorException;
import com.files.exceptions.SdkException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

// Turns Files.com API error responses into SDK exceptions.
final class ApiErrors {
  private static final ObjectMapper MAPPER = JsonMapper.builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .build();

  // How Files.com answers, with HTTP 403, that a site must be reached through another server: an
  // error of this type, or, from older servers, plain text beginning with this sentence. The
  // response names the site's host in the error's data.host or in its X-Files-Host header.
  private static final String REGION_MISMATCH_TYPE = "not-authenticated/lockout-region-mismatch";
  private static final String LEGACY_REGION_MISMATCH = "You have connected to a URL";
  private static final String HOST_HEADER = "X-Files-Host";

  private ApiErrors() {
  }

  // Classifies an error response by the structured error in its body; the status code only
  // appears in the message. Without a structured error, no reliable API response was received.
  // The body is read, and the response's connection released, either way.
  static SdkException forResponse(HttpResponse response, int statusCode) {
    return read(response, statusCode, null, false).error;
  }

  // As above. operation, when given, names the API request (ClientRuntime.operation) in the error
  // for a body that could not be read or holds no structured error.
  static SdkException forResponse(HttpResponse response, int statusCode, String operation) {
    return read(response, statusCode, operation, false).error;
  }

  // As forResponse, also telling whether the response is a region mismatch, and the host it names.
  // Only a response from the API root the request was sent to, fromApiRoot, can be one: an HTTP 403
  // whose structured error has exactly the region mismatch type, naming the host in data.host when
  // that is non-empty text and otherwise in its X-Files-Host header (see hostHeader), or naming
  // none; or an HTTP 403 without a structured error whose body begins with the older plain text,
  // naming the host in its X-Files-Host header, which it must have. The typed error's exception is
  // the one forResponse returns. The plain text raises LegacyRegionMismatchException, an
  // ApiConnectionException with the same message that also keeps the host and the response's
  // headers; any other response without a structured error raises a plain ApiConnectionException.
  static Failure read(HttpResponse response, int statusCode, String operation, boolean fromApiRoot) {
    String body;
    try {
      HttpEntity entity = response.getEntity();
      body = entity == null ? null : EntityUtils.toString(entity, StandardCharsets.UTF_8);
    } catch (IOException e) {
      if (operation != null) {
        return Failure.of(new ApiErrorException.ApiConnectionException(
            "HTTP returned status " + statusCode + ", and its error response could not be read " + operation));
      }
      body = null;
    } finally {
      EntityUtils.consumeQuietly(response.getEntity());
    }

    ResponseError responseError = body == null ? null : readResponseError(body);
    if (responseError == null) {
      String message = "HTTP returned status " + statusCode + " without a structured error response"
          + (operation != null ? " " + operation : "");
      String host = fromApiRoot && statusCode == 403 && body != null && body.startsWith(LEGACY_REGION_MISMATCH)
          ? hostHeader(response) : null;
      if (host == null) {
        return Failure.of(new ApiErrorException.ApiConnectionException(message));
      }
      return Failure.regionMismatch(
          new ApiErrorException.LegacyRegionMismatchException(message, host, Arrays.asList(response.getAllHeaders())), host);
    }
    SdkException error = ApiErrorException.forType("HTTP returned status " + statusCode, responseError, Arrays.asList(response.getAllHeaders()));
    if (!fromApiRoot || statusCode != 403 || !REGION_MISMATCH_TYPE.equals(responseError.type)) {
      return Failure.of(error);
    }
    Object host = responseError.data == null ? null : responseError.data.get("host");
    return Failure.regionMismatch(error, host instanceof String && !((String) host).isEmpty() ? (String) host : hostHeader(response));
  }

  // The host the response's X-Files-Host header names: null without exactly one such header, or
  // when its value is empty or lists several values, which leaves the host in doubt.
  private static String hostHeader(HttpResponse response) {
    Header[] headers = response.getHeaders(HOST_HEADER);
    if (headers.length != 1) {
      return null;
    }
    String host = headers[0].getValue();
    return host == null || host.isEmpty() || host.contains(",") ? null : host;
  }

  // Returns null unless the body is a JSON object with the fields of a Files.com error. Jackson reads
  // a scalar "type" as text; an object or array cannot name an error, so it is dropped rather than
  // discarding the rest of the error's details.
  private static ResponseError readResponseError(String body) {
    try {
      JsonNode payload = MAPPER.readTree(body);
      if (payload == null || !payload.isObject()) {
        return null;
      }
      if (payload.path("type").isContainerNode()) {
        ((ObjectNode) payload).remove("type");
      }
      return MAPPER.treeToValue(payload, ResponseError.class);
    } catch (JsonProcessingException e) {
      return null;
    }
  }

  // An error response's exception, and whether the response is a region mismatch (see read) and
  // the host it names, which may be null for the typed error.
  static final class Failure {
    final SdkException error;
    final boolean regionMismatch;
    final String host;

    private Failure(SdkException error, boolean regionMismatch, String host) {
      this.error = error;
      this.regionMismatch = regionMismatch;
      this.host = host;
    }

    static Failure of(SdkException error) {
      return new Failure(error, false, null);
    }

    static Failure regionMismatch(SdkException error, String host) {
      return new Failure(error, true, host);
    }
  }
}
