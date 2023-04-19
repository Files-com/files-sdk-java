package com.files;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.exceptions.ApiErrorException;
import com.files.models.Session;
import com.files.net.FilesApiInterface;
import com.files.net.FilesOkHttpApi;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import okhttp3.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class FilesClient {

  private static final Logger log = LoggerFactory.getLogger(FilesClient.class);
  private static final FilesApiInterface filesApi = new FilesOkHttpApi();
  public static String apiKey;
  public static Session session;
  public static ConnectionPool httpPool = new ConnectionPool(FilesConfig.getInstance().getUpstreamMaxConnections(), FilesConfig.getInstance().getUpstreamTimeout(), TimeUnit.MILLISECONDS);

  public static <T> List<T> requestList(String url, RequestMethods requestType, TypeReference<List<T>> className, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return filesApi.apiRequestList(url, requestType, className, parameters, options);
  }

  public static <T> T requestItem(String url, RequestMethods requestType, TypeReference<T> className, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return filesApi.apiRequestItem(url, requestType, className, parameters, options);
  }

  public static FilesInputStream getFileInputStream(String url, long start, long end) throws IOException {
    return filesApi.getFileInputStream(url, start, end);
  }

  public static long putBufferedInputStream(String url, RequestMethods requestType, String name, BufferedInputStream inputStream, long length) throws IOException {
    return filesApi.putBufferedInputStream(url, requestType, name, inputStream, length);
  }

  public static void setProperty(String property, String value) {
    FilesConfig.getInstance().setProperty(property, value);
  }
}
