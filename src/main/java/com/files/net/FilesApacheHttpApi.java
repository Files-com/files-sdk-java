package com.files.net;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.ListIterator;
import com.files.exceptions.ApiErrorException;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilesApacheHttpApi implements FilesApiInterface {
  private static final Logger log = LoggerFactory.getLogger(FilesApacheHttpApi.class);

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

    HttpRequestBase request;
    switch (requestType) {
      case GET:
        request = new HttpGet(buildUrlWithParams(url, parameters));
        break;
      case DELETE:
        request = new HttpDelete(buildUrlWithParams(url, parameters));
        break;
      case HEAD:
        request = new HttpHead(buildUrlWithParams(url, parameters));
        break;
      case POST:
        HttpPost postRequest = new HttpPost(url);
        if (parameters != null) {
          StringEntity entity = new StringEntity(serializeParameters(parameters), ContentType.APPLICATION_JSON);
          postRequest.setEntity(entity);
        }
        request = postRequest;
        break;
      case PUT:
        HttpPut putRequest = new HttpPut(url);
        if (parameters != null) {
          StringEntity entity = new StringEntity(serializeParameters(parameters), ContentType.APPLICATION_JSON);
          putRequest.setEntity(entity);
        }
        request = putRequest;
        break;
      case PATCH:
        HttpPatch patchRequest = new HttpPatch(url);
        if (parameters != null) {
          StringEntity entity = new StringEntity(serializeParameters(parameters), ContentType.APPLICATION_JSON);
          patchRequest.setEntity(entity);
        }
        request = patchRequest;
        break;
      default:
        throw new UnsupportedOperationException("Unsupported request type: " + requestType);
    }

    addHeaders(request, options);

    try {
      HttpResponse httpResponse = FilesHttpClient.getInstance().getHttpExecutor().executeWithRetry(request);
      HttpEntity entity = httpResponse.getEntity();
      String responseBody = (entity != null) ? EntityUtils.toString(entity, StandardCharsets.UTF_8) : "";
      return new FilesResponse(httpResponse.getStatusLine().getStatusCode(), Arrays.asList(httpResponse.getAllHeaders()), responseBody);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public FilesInputStream getFileInputStream(String url, long start, long end) throws IOException {
    HttpGet request = new HttpGet(url);
    HttpResponse response = FilesHttpClient.getInstance().getHttpExecutor().executeWithRetry(request);
    return new FilesApacheHttpInputStream(response);
  }

  @Override
  public long putBuffer(String url, HttpMethods.RequestMethods requestType, String name, byte[] buffer, long length)
      throws IOException {
    HttpPut request = new HttpPut(url);
    request.setEntity(new ByteArrayEntity(buffer, 0, (int) length, ContentType.APPLICATION_OCTET_STREAM));
  
    FilesHttpClient.getInstance().getHttpExecutor().executeWithRetry(request);
    return length;
  }

  @Override
  public long putBufferedInputStream(String url, HttpMethods.RequestMethods requestType, String name,
      BufferedInputStream inputStream, long length) throws IOException {
    HttpPut request = new HttpPut(url);
    request.setEntity(new InputStreamEntity(inputStream, length, ContentType.APPLICATION_OCTET_STREAM));

    FilesHttpClient.getInstance().getHttpExecutor().executeWithRetry(request);
    return 0;
  }

  private void addHeaders(HttpRequestBase request, HashMap<String, Object> options) {
    request.addHeader("User-Agent", FilesConfig.getInstance().getUserAgent());
    if (FilesClient.language != null) {
      request.addHeader("Accept-Language", FilesClient.language);
    }

    String requestType = request.getMethod();
    String url = request.getURI().toString();
    boolean requiresAuth = !(url.contains("sessions") && "POST".equalsIgnoreCase(requestType));

    if (requiresAuth) {
      if (options.containsKey("session_id")) {
        if (!(options.get("session_id") instanceof String)) {
          throw new ApiErrorException.InvalidParameterException("Bad option: session_id must be of type String");
        }
        request.addHeader("X-FilesApi-Auth", (String) options.get("session_id"));
      } else if (options.containsKey("api_key")) {
        if (!(options.get("api_key") instanceof String)) {
          throw new ApiErrorException.InvalidParameterException("Bad option: api_key must be of type String");
        }
        request.addHeader("X-FilesApi-Key", (String) options.get("api_key"));
      } else if (FilesClient.session != null && FilesClient.session.getId().length() > 0) {
        request.addHeader("X-FilesApi-Auth", FilesClient.session.getId());
      } else if (FilesClient.apiKey != null && FilesClient.apiKey.length() > 0) {
        request.addHeader("X-FilesApi-Key", FilesClient.apiKey);
      } else {
        throw new ApiErrorException.AuthenticationException(
          String.format("Authentication required for API request: %s %s", url, requestType), null);
      }
    }
  }

  private String buildUrlWithParams(String baseUrl, HashMap<String, Object> parameters) {
    if (parameters == null || parameters.isEmpty()) {
      return baseUrl;
    }
  
    StringBuilder queryString = new StringBuilder();
    parameters.forEach((key, value) -> {
      if (value instanceof Map) {
        ((Map<?, ?>) value).forEach((k2, v2) -> {
          if (queryString.length() > 0) {
            queryString.append("&");
          }
          queryString.append(key).append("[").append(k2).append("]=").append(encode(v2.toString()));
        });
      } else {
        if (queryString.length() > 0) {
          queryString.append("&");
        }
        queryString.append(key).append("=").append(encode(value.toString()));
      }
    });
  
    return baseUrl + (baseUrl.contains("?") ? "&" : "?") + queryString;
  }
  
  private String encode(String value) {
    try {
      return java.net.URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    } catch (Exception e) {
      throw new RuntimeException("Failed to encode query parameter", e);
    }
  }

  private String serializeParameters(HashMap<String, Object> parameters) {
    try {
      return objectMapper.writeValueAsString(parameters);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to serialize parameters", e);
    }
  }
}
