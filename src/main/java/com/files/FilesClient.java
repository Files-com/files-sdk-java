package com.files;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.ListIterator;
import com.files.models.ModelInterface;
import com.files.models.Session;
import com.files.net.ApacheHttpEvictor;
import com.files.net.FilesApacheHttpApi;
import com.files.net.FilesApiInterface;
import com.files.net.FilesResponse;
import com.files.net.HttpMethods;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class FilesClient {
  private static final Logger log = LoggerFactory.getLogger(FilesClient.class);

  private static final FilesApiInterface filesApi = new FilesApacheHttpApi();
  public static String apiKey;
  public static String language;
  public static Session session;
  public static PoolingHttpClientConnectionManager connectionManager;
  private static final ApacheHttpEvictor evictor;

  static {
    connectionManager = new PoolingHttpClientConnectionManager();
    connectionManager.setMaxTotal(FilesConfig.getInstance().getUpstreamMaxConnections());
    connectionManager.setDefaultMaxPerRoute(20);
    evictor = new ApacheHttpEvictor(connectionManager, FilesConfig.getInstance().getUpstreamTimeout());
  }

  public static FilesResponse apiRequest(String url, HttpMethods.RequestMethods requestType, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return filesApi.apiRequest(url, requestType, parameters, options);
  }

  public static <T> ListIterator<T> requestList(String url, RequestMethods requestType, TypeReference<List<T>> className, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return filesApi.apiRequestList(url, requestType, className, parameters, options);
  }

  public static <T> T requestItem(String url, RequestMethods requestType, TypeReference<T> className, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    final T item = filesApi.apiRequestItem(url, requestType, className, parameters, options);
    if (item instanceof ModelInterface) {
      ((ModelInterface) item).setOptions(options);
    }
    return item;
  }

  public static FilesInputStream getFileInputStream(String url, long start, long end) throws IOException {
    return filesApi.getFileInputStream(url, start, end);
  }

  public static long putBuffer(String url, RequestMethods requestType, String name, byte[] buffer, long length) throws IOException {
    return filesApi.putBuffer(url, requestType, name, buffer, length);
  }

  public static long putBufferedInputStream(String url, RequestMethods requestType, String name, BufferedInputStream inputStream, long length) throws IOException {
    return filesApi.putBufferedInputStream(url, requestType, name, inputStream, length);
  }

  public static void setProperty(String property, String value) {
    FilesConfig.getInstance().setProperty(property, value);
  }
}
