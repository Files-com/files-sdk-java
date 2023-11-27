package com.files.net;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.files.FilesConfig;
import com.files.ResponseError;
import com.files.exceptions.ApiErrorException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilesHttpInterceptor implements Interceptor {
  protected static final Logger log = LoggerFactory.getLogger(FilesHttpInterceptor.class);
  private final ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .build();
  private final String userAgent;
  private final FilesConfig filesConfig = FilesConfig.getInstance();

  public FilesHttpInterceptor() {
    this.userAgent = filesConfig.getUserAgent();
  }

  @Override
  public Response intercept(Chain chain) throws RuntimeException {
    Request originalRequest = chain.request();
    Request.Builder modifiedAgentRequest = originalRequest.newBuilder();

    // Add User Agent Header
    modifiedAgentRequest.header("User-Agent", userAgent);

    // Try the request
    Response response;
    int attempts = 0;
    Integer status = null;
    while (true) {
      try {
        attempts++;
        response = chain.proceed(modifiedAgentRequest.build());
        status = response.code();
        if (status != 429 || status <= 501 || attempts >= filesConfig.getUpstreamMaxAttempts()) {
          break;
        }
        log.warn(String.format("HttpClient received retry condition of [%s].", status));
      } catch (IOException e) {
        log.error(String.format("HttpClient received retry error condition due to IOException of [%s]", e.getMessage()));
      }
      try {
        Thread.sleep((long) (Math.pow(2, attempts - 2) * filesConfig.getInitialRetryDelayMillis()));
      } catch (InterruptedException e) {
        throw new ApiErrorException.ApiConnectionException("Http Request Interrupted");
      }
    }

    if ((status >= 400 && status <= 600) || status == null) {
      String message = String.format("Http Returned Status %d", status);
      log.error(message);
      if (status >= 500) {
        try {
          throw new ApiErrorException.ServerErrorException(response.body().string());
        } catch (IOException e) {
          throw new ApiErrorException.ServerErrorException("Server responded with an error");
        } finally {
          response.body().close();
        }
      } else if (status == 403) {
        Map<String, List<String>> headers = response.headers().toMultimap();
        try {
          String body = response.body().string();
          if (body.contains("You have connected to a URL")) {
            ResponseError responseError = new ResponseError();
            HashMap<String, Object> data = new HashMap<>();

            data.put("host", headers.get("x-files-host").get(0));
            responseError.title = "Lockout Region Mismatch";
            responseError.httpCode = 401;
            responseError.error = "Your account must login using a different server, " + data.get("host") + ".";
            responseError.type = "not-authenticated/lockout-region-mismatch";
            responseError.data = data;
            throw new ApiErrorException.LockoutRegionMismatchException(responseError.title, responseError, headers);
          } else {
            throw new ApiErrorException.AuthenticationException(body, headers);
          }
        } catch (IOException e) {
          throw new ApiErrorException.AuthenticationException("Unable to access this resource", headers);
        } finally {
          response.body().close();
        }
      }
      ResponseError responseError = new ResponseError();
      try {
        responseError = objectMapper.readValue(response.body().string(), ResponseError.class);
      } catch (JsonProcessingException e) {
        throw new ApiErrorException.InvalidResponseException(e.getMessage());
      } catch (Exception e) {
        // INOP to catch any connection closed errors.
      } finally {
        response.body().close();
      }
      throw ApiErrorException.forType(message, responseError, response.headers().toMultimap());
    }

    return response;
  }
}
