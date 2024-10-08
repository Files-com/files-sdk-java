package com.files.net;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.ListIterator;
import com.files.util.FilesInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface FilesApiInterface {
  public FilesResponse apiRequest(String url, HttpMethods.RequestMethods requestType, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException;

  public <T> ListIterator<T> apiRequestList(String url, HttpMethods.RequestMethods requestType, TypeReference<List<T>> className,
                       HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException;

  public <T> T apiRequestItem(String url, HttpMethods.RequestMethods requestType, TypeReference<T> className,
                             HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException;

  public FilesInputStream getFileInputStream(String url, long start, long end) throws IOException;

  public long putBuffer(String url, HttpMethods.RequestMethods requestType, String name, byte[] buffer, long length) throws IOException;

  public long putBufferedInputStream(String url, HttpMethods.RequestMethods requestType, String name, BufferedInputStream inputStream, long length) throws IOException;
}
