package com.files.net;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.files.FilesConfig;
import com.files.ResponseError;
import com.files.exceptions.ApiErrorException;
import com.files.exceptions.SdkException;
import java.io.IOException;
import java.util.Arrays;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilesHttpExecutor {

  private static final Logger log = LoggerFactory.getLogger(FilesHttpExecutor.class);

  private final CloseableHttpClient client;
  private final ObjectMapper objectMapper;
  private final FilesConfig config = FilesConfig.getInstance();

  public FilesHttpExecutor(CloseableHttpClient client) {
    this.client = client;
    this.objectMapper = JsonMapper.builder()
        .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
        .build();
  }

  public HttpResponse executeWithRetry(HttpRequestBase request) throws IOException {
    int attempts = 0;
    Exception lastException = null;

    while (attempts < config.getUpstreamMaxAttempts()) {
      attempts++;
      try {
        CloseableHttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 429 || statusCode >= 502) {
          log.warn(String.format("Retrying due to HTTP status %d (attempt %d)", statusCode, attempts));
          lastException = mapApiError(response, statusCode);
          backoff(attempts);
          continue;
        }

        if (statusCode >= 400) {
          throw mapApiError(response, statusCode);
        }

        return response;
      } catch (IOException e) {
        log.warn(String.format("IOException on attempt %d: %s", attempts, e.getMessage()));
        lastException = e;
        if (attempts >= config.getUpstreamMaxAttempts()) {
          break;
        }
        backoff(attempts);
      }
    }

    if (lastException instanceof SdkException) {
      throw (SdkException) lastException;
    }

    throw new ApiErrorException.ApiConnectionException(lastException.getMessage());
  }

  private void backoff(int attempt) {
    try {
      long delay = (long) Math.pow(2, attempt - 1) * config.getInitialRetryDelayMillis();
      Thread.sleep(delay);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new ApiErrorException.ApiConnectionException("Request interrupted during backoff");
    }
  }

  private SdkException mapApiError(HttpResponse response, int statusCode) throws IOException {
    String message = "HTTP returned status " + statusCode;
    String body = null;
    ResponseError responseError = new ResponseError();

    try {
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        body = EntityUtils.toString(entity);
        responseError = objectMapper.readValue(body, ResponseError.class);
      }
    } catch (JsonProcessingException e) {
      if (statusCode >= 500) {
        return new ApiErrorException.ServerErrorException(body);
      } else if (statusCode == 403) {
        return new ApiErrorException.AuthenticationException(body, Arrays.asList(response.getAllHeaders()));
      }
      return new ApiErrorException.InvalidResponseException(e.getMessage());
    } catch (IOException e) {
      return new ApiErrorException.InvalidResponseException(e.getMessage());
    } finally {
      consumeQuietly(response);
    }

    return ApiErrorException.forType(message, responseError, Arrays.asList(response.getAllHeaders()));
  }

  private void consumeQuietly(HttpResponse response) {
    try {
      if (response.getEntity() != null) {
        EntityUtils.consumeQuietly(response.getEntity());
      }
    } catch (Exception ignored) {
      // pass
    }
  }
}
