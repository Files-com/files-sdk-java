package com.files.net;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.util.FilesInputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

public interface FilesApiInterface {
  public <T> List<T> apiRequestList(String url, HttpMethods.RequestMethods requestType, TypeReference<List<T>> className,
                       HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException;

  public <T> T apiRequestItem(String url, HttpMethods.RequestMethods requestType,TypeReference<T> className,
                             HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException;

  public FilesInputStream getFileInputStream(String url, long start, long end) throws IOException;

  public void putFileOutputStream(String url, String name, OutputStream outputStream);
}
