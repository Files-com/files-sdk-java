package com.files.net;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.util.FilesInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * The static API of {@link FilesClient} as an object: each method sends its request with
 * {@link FilesClient#defaultClient()}. Kept for compatibility; use a {@link FilesClient}.
 */
public class FilesApacheHttpApi implements FilesApiInterface {
  public <T> ListIterator<T> apiRequestList(String url, HttpMethods.RequestMethods requestType,
      TypeReference<List<T>> clazz,
      HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.requestList(url, requestType, clazz, parameters, options);
  }

  public <T> T apiRequestItem(String url, HttpMethods.RequestMethods requestType, TypeReference<T> clazz,
      HashMap<String, Object> parameters, HashMap<String, Object> options)
      throws IllegalArgumentException, RuntimeException {
    return FilesClient.requestItem(url, requestType, clazz, parameters, options);
  }

  public FilesResponse apiRequest(String url, HttpMethods.RequestMethods requestType,
      HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.apiRequest(url, requestType, parameters, options);
  }

  @Override
  public FilesInputStream getFileInputStream(String url, long start, long end) throws IOException {
    return FilesClient.getFileInputStream(url, start, end);
  }

  @Override
  public long putBuffer(String url, HttpMethods.RequestMethods requestType, String name, byte[] buffer, long length)
      throws IOException {
    return FilesClient.putBuffer(url, requestType, name, buffer, length);
  }

  @Override
  public long putBufferedInputStream(String url, HttpMethods.RequestMethods requestType, String name,
      BufferedInputStream inputStream, long length) throws IOException {
    return FilesClient.putBufferedInputStream(url, requestType, name, inputStream, length);
  }
}
