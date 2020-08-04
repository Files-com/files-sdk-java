package com.files.net;


import com.files.FilesConfig;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.IOException;

public class FilesHttpInterceptor implements Interceptor {
  protected static final Logger log = LogManager.getLogger(HttpClient.class);
  private final String userAgent;
  private FilesConfig filesConfig = FilesConfig.getInstance();

  public FilesHttpInterceptor() {
    this.userAgent = String.format("Files-JAVA-SDK-", filesConfig.getSdkVersion());
  }

  @Override
  public Response intercept(Chain chain) throws IOException {
    Request originalRequest = chain.request();
    Request.Builder modifiedAgentRequest = originalRequest.newBuilder();

    // Add User Agent Header
    modifiedAgentRequest.header("User-Agent", userAgent);

    // Try the request
    Response response;
    int attempts = 0;
    while (true) {
      try {
        attempts++;
        response = chain.proceed(modifiedAgentRequest.build());
        int status = response.code();
        if (status != 429 || status <= 501
          || attempts >= filesConfig.getUpstreamMaxAttempts()) {
          break;
        }
        log.warn(String.format("HttpClient received retry condition of [%s].", status));
      } catch (IOException e) {
        log.error(String.format("HttpClient received retry error condition.", e));
      }
      try {
        Thread.sleep((long) (Math.pow(2, attempts - 2) * filesConfig.getInitialRetryDelayMillis()));
      } catch (InterruptedException e) {
        throw new IOException("Http Request Interrupted");
      }
    }
    return response;
  }
}
