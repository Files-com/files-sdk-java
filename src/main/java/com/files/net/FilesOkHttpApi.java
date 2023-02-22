package com.files.net;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.MapperFeature;
import com.files.FilesClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
import okhttp3.*;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;

public class FilesOkHttpApi implements FilesApiInterface {
  private final ObjectMapper objectMapper = JsonMapper
    .builder()
    .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
    .build();
  protected static final Logger log = LoggerFactory.getLogger(FilesOkHttpApi.class);

  public <T> List<T> apiRequestList(String url, HttpMethods.RequestMethods requestType, TypeReference<List<T>> clazz, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    String response = apiRequest(url, requestType, parameters, options);
    return objectMapper.readValue(response, clazz);
  }

  public <T> T apiRequestItem(String url, HttpMethods.RequestMethods requestType, TypeReference<T> clazz,
                              HashMap<String, Object> parameters, HashMap<String, Object> options) throws IllegalArgumentException, IOException {
    String response = apiRequest(url, requestType, parameters, options);

    // Fix the issue where timestamps are getting appended twice to file upload
    // by replacing the response path with the original created path parameter
    if (requestType.toString().equals("POST")
            && parameters != null
            && parameters.getOrDefault("action","").toString().equals("put")
            && clazz.getType().toString().equals("class com.files.models.FileUploadPart")
    ) {
      HashMap<String, Object> responseMap = objectMapper.readValue(response, HashMap.class);
      responseMap.replace("path", parameters.get("path"));

      return objectMapper.convertValue(responseMap, clazz);
    }

    return objectMapper.readValue(response, clazz);
  }

  private String apiRequest(String url, HttpMethods.RequestMethods requestType,
                              HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    if (log.isDebugEnabled()) {
      log.debug(String.format("Sending a %s request to %s with parameters: %s and options %s", requestType, url,
          parameters, options));
    }
    Request.Builder request = new Request.Builder();

    switch(requestType) {
      case GET:
      case HEAD:
      case DELETE:
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();

        if (parameters != null) {
          parameters.forEach((key, value) -> {
            httpBuilder.addQueryParameter(key, value.toString());
          });
        }
        request.url(httpBuilder.build().url());
      break;
      default:
        request.url(url);
      break;
    }

    FormBody.Builder body = new FormBody.Builder();
    if (parameters != null) {
      for (String key : parameters.keySet()) {
        String parameterValue = parameters.get(key) instanceof String != true
                ? String.valueOf(parameters.get(key))
                : (String) parameters.get(key);

        body.add(key, parameterValue);
      }
    }

    boolean requiresAuth = !(url.contains("sessions") && requestType == HttpMethods.RequestMethods.POST);

    if (requiresAuth) {
      if (options.containsKey("session_id")) {
        if (! (options.get("session_id") instanceof String)) {
          throw new InvalidParameterException("Bad option: session_id must be of type String");
        }
        request.header("X-FilesApi-Auth", (String) options.get("session_id"));
      } else if (options.containsKey("api_key")) {
        if (! (options.get("api_key") instanceof String)) {
          throw new InvalidParameterException("Bad option: api_key must be of type string");
        }
        request.header("X-FilesApi-Key", (String) options.get("api_key"));
      } else if (FilesClient.session != null && FilesClient.session.getId().length() > 0) {
        request.header("X-FilesApi-Auth", FilesClient.session.getId());
      } else if (FilesClient.apiKey != null && FilesClient.apiKey.length() > 0) {
        request.header("X-FilesApi-Key", FilesClient.apiKey);
      } else {
        throw new InvalidParameterException(String.format("Authentication required for API request: %s %s", url,requestType));
      }
    }
    updateRequestWithHttpMethod(request, body.build(), requestType);
    Response response =  FilesHttpClient.getHttpClient().newCall(request.build()).execute();
    String responseBody = response.body().string();
    response.body().close();
    return responseBody;
  }

  @Override
  public FilesInputStream getFileInputStream(String url, long start, long end) throws IOException {
    Request.Builder request = new Request.Builder();
    request.url(url);
    Response response = FilesHttpClient.getHttpClient().newCall(request.build()).execute();
    return new FilesOkHttpInputStream(response);
  }

  @Override
  public long putBufferedInputStream(String url, HttpMethods.RequestMethods requestType, String name, BufferedInputStream inputStream, long length) throws IOException {
    String uri = ModelUtils.forceMandatoryUriEncode(url);
    MediaType type = MediaType.parse("application/octet-stream");
    Request.Builder request = new Request.Builder();
    request.addHeader("Content-type", "application/octet-stream");
    request.url(uri);
    RequestBody body = create(type, inputStream, length);
    updateRequestWithHttpMethod(request, body, requestType);
    Response response = FilesHttpClient.getHttpClient().newCall(request.build()).execute();
    return 0;
  }

  public static void updateRequestWithHttpMethod(Request.Builder request, RequestBody body, HttpMethods.RequestMethods requestType) {
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
    }
  }

  public static RequestBody create(final MediaType contentType,
                                   final BufferedInputStream inputStream,
                                   final long length) {
    if (inputStream == null) throw new NullPointerException("inputStream == null");

    return new RequestBody() {
      @Override public MediaType contentType() {
        return contentType;
      }

      @Override public long contentLength() {
        return length;
      }

      @Override public void writeTo(BufferedSink sink) throws IOException {
          Source source = Okio.source(inputStream);
          sink.writeAll(source);
      }
    };
  }
}
