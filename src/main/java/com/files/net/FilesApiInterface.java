package com.files.net;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.exceptions.ApiErrorException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface FilesApiInterface {
  public <T> List<T> request(String url, HttpMethods.RequestMethods requestType, TypeReference<List<T>> className,
                       HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException;
}
