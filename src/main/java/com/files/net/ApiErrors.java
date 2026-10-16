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
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

// Turns Files.com API error responses into SDK exceptions.
final class ApiErrors {
  private static final ObjectMapper MAPPER = JsonMapper.builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .build();

  private ApiErrors() {
  }

  // Classifies an error response by the structured error in its body; the status code only
  // appears in the message. Without a structured error, no reliable API response was received.
  // The body is read, and the response's connection released, either way.
  static SdkException forResponse(HttpResponse response, int statusCode) {
    return forResponse(response, statusCode, null);
  }

  // As above. operation, when given, names the API request (ClientRuntime.operation) in the error
  // for a body that could not be read or holds no structured error.
  static SdkException forResponse(HttpResponse response, int statusCode, String operation) {
    ResponseError responseError;
    try {
      HttpEntity entity = response.getEntity();
      responseError = entity == null ? null : readResponseError(EntityUtils.toString(entity, StandardCharsets.UTF_8));
    } catch (IOException e) {
      if (operation != null) {
        return new ApiErrorException.ApiConnectionException(
            "HTTP returned status " + statusCode + ", and its error response could not be read " + operation);
      }
      responseError = null;
    } finally {
      EntityUtils.consumeQuietly(response.getEntity());
    }

    if (responseError == null) {
      return new ApiErrorException.ApiConnectionException(
          "HTTP returned status " + statusCode + " without a structured error response" + (operation != null ? " " + operation : ""));
    }
    return ApiErrorException.forType("HTTP returned status " + statusCode, responseError, Arrays.asList(response.getAllHeaders()));
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
}
