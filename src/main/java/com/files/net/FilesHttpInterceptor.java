package com.files.net;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.files.FilesConfig;
import com.files.ResponseError;
import com.files.exceptions.ApiErrorException;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilesHttpInterceptor implements Interceptor {
  private static final Logger log = LoggerFactory.getLogger(FilesHttpInterceptor.class);

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
        if (attempts >= filesConfig.getUpstreamMaxAttempts()) {
          throw new ApiErrorException.ApiConnectionException(e.getMessage());
        }
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
      ResponseError responseError = new ResponseError();
      String body = null;
      try {
        body = response.body().string();
        responseError = objectMapper.readValue(body, ResponseError.class);
      } catch (JsonProcessingException e) {
        if (status >= 500) {
          throw new ApiErrorException.ServerErrorException(body);
        } else if (status == 403) {
          throw new ApiErrorException.AuthenticationException(body, response.headers().toMultimap());
        }
        throw new ApiErrorException.InvalidResponseException(e.getMessage());
      } catch (IOException e) {
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
