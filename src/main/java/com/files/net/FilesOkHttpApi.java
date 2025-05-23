package com.files.net;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.exceptions.ApiErrorException;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilesOkHttpApi implements FilesApiInterface {
  private static final Logger log = LoggerFactory.getLogger(FilesOkHttpApi.class);

  private final ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .build();

  public <T> ListIterator<T> apiRequestList(String url, HttpMethods.RequestMethods requestType,
      TypeReference<List<T>> clazz,
      HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return new ListIterator<T>(url, requestType, clazz, parameters, options);
  }

  public <T> T apiRequestItem(String url, HttpMethods.RequestMethods requestType, TypeReference<T> clazz,
      HashMap<String, Object> parameters, HashMap<String, Object> options)
      throws IllegalArgumentException, RuntimeException {
    FilesResponse response = apiRequest(url, requestType, parameters, options);

    // Fix the issue where timestamps are getting appended twice to file upload
    // by replacing the response path with the original created path parameter
    if ("POST".equals(requestType.toString())
        && parameters != null
        && "put".equals(parameters.getOrDefault("action", "").toString())
        && "class com.files.models.FileUploadPart".equals(clazz.getType().toString())) {
      HashMap<String, Object> responseMap;
      try {
        responseMap = objectMapper.readValue(response.getBody(), HashMap.class);
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
      responseMap.replace("path", parameters.get("path"));

      return objectMapper.convertValue(responseMap, clazz);
    }

    try {
      return objectMapper.readValue(response.getBody(), clazz);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public FilesResponse apiRequest(String url, HttpMethods.RequestMethods requestType,
      HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    if (log.isDebugEnabled()) {
      log.debug(String.format("Sending a %s request to %s with parameters: %s and options %s", requestType, url,
          parameters, options));
    }
    Request.Builder request = new Request.Builder();

    switch (requestType) {
      case GET:
      case HEAD:
      case DELETE:
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();

        if (parameters != null) {
          parameters.forEach((key, value) -> {
            if (value instanceof Map) {
              ((Map<?, ?>) value).forEach((key2, value2) -> {
                httpBuilder.addQueryParameter(key + "[" + key2 + "]", value2.toString());
              });
            } else {
              httpBuilder.addQueryParameter(key, value.toString());
            }
          });
        }
        request.url(httpBuilder.build().url());
        break;
      default:
        request.url(url);
        break;
    }

    String body = null;
    if (parameters != null) {
      try {
        body = objectMapper.writeValueAsString(parameters);
      } catch (JsonProcessingException e) {
        throw new ApiErrorException.InvalidParameterException(e.getMessage());
      }
    }

    boolean requiresAuth = !(url.contains("sessions") && requestType == HttpMethods.RequestMethods.POST);

    if (requiresAuth) {
      if (options.containsKey("session_id")) {
        if (!(options.get("session_id") instanceof String)) {
          throw new ApiErrorException.InvalidParameterException("Bad option: session_id must be of type String");
        }
        request.header("X-FilesApi-Auth", (String) options.get("session_id"));
      } else if (options.containsKey("api_key")) {
        if (!(options.get("api_key") instanceof String)) {
          throw new ApiErrorException.InvalidParameterException("Bad option: api_key must be of type string");
        }
        request.header("X-FilesApi-Key", (String) options.get("api_key"));
      } else if (FilesClient.session != null && FilesClient.session.getId().length() > 0) {
        request.header("X-FilesApi-Auth", FilesClient.session.getId());
      } else if (FilesClient.apiKey != null && FilesClient.apiKey.length() > 0) {
        request.header("X-FilesApi-Key", FilesClient.apiKey);
      } else {
        throw new ApiErrorException.AuthenticationException(
            String.format("Authentication required for API request: %s %s", url, requestType), null);
      }
    }
    if (FilesClient.language != null && !FilesClient.language.isEmpty()) {
      request.header("Accept-Language", FilesClient.language);
    }
    updateRequestWithHttpMethod(request, RequestBody.create(MediaType.parse("application/json"), body), requestType);
    try {
      Response response = FilesHttpClient.getHttpClient().newCall(request.build()).execute();
      String responseBody = response.body().string();
      response.body().close();
      return new FilesResponse(response.code(), response.headers().toMultimap(), responseBody);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public FilesInputStream getFileInputStream(String url, long start, long end) throws IOException {
    Request.Builder request = new Request.Builder();
    request.url(url);
    Response response = FilesHttpClient.getHttpClient().newCall(request.build()).execute();
    return new FilesOkHttpInputStream(response);
  }

  @Override
  public long putBuffer(String url, HttpMethods.RequestMethods requestType, String name, byte[] buffer, long length)
      throws IOException {
    final MediaType type = MediaType.parse("application/octet-stream");
    final RequestBody body = RequestBody.create(type, buffer, 0, (int) length);
    sendRequest(url, requestType, body);
    return 0;
  }

  @Override
  public long putBufferedInputStream(String url, HttpMethods.RequestMethods requestType, String name,
      BufferedInputStream inputStream, long length) throws IOException {
    final MediaType type = MediaType.parse("application/octet-stream");
    final RequestBody body = create(type, inputStream, length);
    sendRequest(url, requestType, body);
    return 0;
  }

  private Response sendRequest(String url, HttpMethods.RequestMethods requestType, RequestBody body)
      throws IOException {
    final String uri = ModelUtils.forceMandatoryUriEncode(url);
    final Request.Builder request = new Request.Builder()
        .addHeader("Content-type", body.contentType().toString())
        .url(uri);
    updateRequestWithHttpMethod(request, body, requestType);
    return FilesHttpClient.getHttpClient().newCall(request.build()).execute();
  }

  public static void updateRequestWithHttpMethod(Request.Builder request, RequestBody body,
      HttpMethods.RequestMethods requestType) {
    switch (requestType) {
      case DELETE:
        request.delete(body);
        break;
      case GET:
        break;
      case HEAD:
        request.head();
        break;
      case PATCH:
        request.patch(body);
        break;
      case POST:
        request.post(body);
        break;
      case PUT:
        request.put(body);
        break;
      default:
        break;
    }
  }

  public static RequestBody create(final MediaType contentType,
      final BufferedInputStream inputStream,
      final long length) {
    if (inputStream == null) {
      throw new NullPointerException("inputStream == null");
    }

    return new RequestBody() {
      @Override
      public MediaType contentType() {
        return contentType;
      }

      @Override
      public long contentLength() {
        return length;
      }

      @Override
      public void writeTo(BufferedSink sink) throws IOException {
        Source source = Okio.source(inputStream);
        sink.writeAll(source);
      }
    };
  }
}
