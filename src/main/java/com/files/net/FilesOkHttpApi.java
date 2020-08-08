package com.files.net;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.exceptions.ApiErrorException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;

public class FilesOkHttpApi implements FilesApiInterface {
  @Override
  public <T> List<T> request(String url, HttpMethods.RequestMethods requestType, TypeReference<List<T>> clazz,
                       HashMap<String, Object> parameters, HashMap<String, Object> options) throws IllegalArgumentException, IOException {
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

    switch (requestType) {
      case DELETE:
        request.delete(body.build());
      case GET:
        break;
      case HEAD:
        request.head();
      case PATCH:
        request.patch(body.build());
      case POST:
        request.post(body.build());
      case PUT:
        request.put(body.build());
    }
    Response response = HttpClient.getHttpClient().newCall(request.build()).execute();

    String responseBody = response.body().string();
    response.body().close();

    ObjectMapper objectMapper = new ObjectMapper();

    return objectMapper.readValue(responseBody, clazz);
  }
}
