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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilesHttpClient {
  private static final Logger log = LoggerFactory.getLogger(FilesHttpClient.class);

  private static FilesHttpClient instance;
  protected CloseableHttpClient httpClient;
  protected FilesHttpExecutor httpExecutor;

  protected FilesHttpClient() {
    FilesConfig filesConfig = FilesConfig.getInstance();
    PoolingHttpClientConnectionManager connectionManager = FilesClient.connectionManager;

    RequestConfig requestConfig = RequestConfig.custom()
        .setConnectTimeout((int) TimeUnit.SECONDS.toMillis(10))// Connection timeout
        .setSocketTimeout((int) TimeUnit.SECONDS.toMillis(30))// Socket timeout
        .setConnectionRequestTimeout((int) TimeUnit.SECONDS.toMillis(10))// Request timeout
        .build();

    try {
      httpClient = HttpClients.custom()
          .setConnectionManager(connectionManager)
          .setDefaultRequestConfig(requestConfig)
          .build();
      httpExecutor = new FilesHttpExecutor(httpClient);
    } catch (Exception e) {
      log.error("Error initializing Apache HTTP Client", e);
    }
  }

  public static FilesHttpClient getInstance() {
    if (instance == null) {
      synchronized (FilesHttpClient.class) {
        if (instance == null) {
          instance = new FilesHttpClient();
        }
      }
    }
    return instance;
  }

  public FilesHttpExecutor getHttpExecutor() {
    return httpExecutor;
  }
}
