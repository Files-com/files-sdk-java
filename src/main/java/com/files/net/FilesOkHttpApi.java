package com.files.net;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;

public class FilesOkHttpApi implements FilesApiInterface {
  private final ObjectMapper objectMapper = new ObjectMapper();
  protected static final Logger log = LogManager.getLogger(FilesOkHttpApi.class);

  public <T> List<T> apiRequestList(String url, HttpMethods.RequestMethods requestType, TypeReference<List<T>> clazz, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    String response = apiRequest(url, requestType, parameters, options);
    return objectMapper.readValue(response, clazz);
  }

  public <T> T apiRequestItem(String url, HttpMethods.RequestMethods requestType, TypeReference<T> clazz,
                              HashMap<String, Object> parameters, HashMap<String, Object> options) throws IllegalArgumentException, IOException {
    String response = apiRequest(url, requestType, parameters, options);
    return objectMapper.readValue(response, clazz);
  }

  private String apiRequest(String url, HttpMethods.RequestMethods requestType,
                              HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    if (log.isDebugEnabled()) {
      log.debug(String.format("Sending a %s request to %s with parameters: %s and options %s", requestType, url,
          parameters, options));
    }
    Request.Builder request = new Request.Builder();
    request.url(url);
    FormBody.Builder body = new FormBody.Builder();
    if (parameters != null) {
      for (String key : parameters.keySet()) {
        body.add(key, (String) parameters.get(key));
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
  public long putBufferedInputStream(String url, HttpMethods.RequestMethods requestType, String name, BufferedInputStream inputStream) throws IOException {
    String uri = ModelUtils.forceMandatoryUriEncode(url);
    MediaType type = MediaType.parse("application/octet-stream");
    Request.Builder request = new Request.Builder();
    request.addHeader("Content-type", "application/octet-stream");
    request.url(uri);
    RequestBody body = create(type, inputStream);
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
                                   final BufferedInputStream inputStream) {
    if (inputStream == null) throw new NullPointerException("inputStream == null");

    return new RequestBody() {
      @Override public MediaType contentType() {
        return contentType;
      }

      @Override public long contentLength() {
        try {
          return inputStream.available();
        } catch (IOException e) {
          //a IOException is sent if the input stream has been closed, i.e. no content to read.
          return 0;
        }
      }

      @Override public void writeTo(BufferedSink sink) throws IOException {
        try {
          inputStream.mark(0);
          Source source = Okio.source(inputStream);
          sink.writeAll(source);
        } finally {
          inputStream.reset();
        }
      }
    };
  }
}
