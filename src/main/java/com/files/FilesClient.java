package com.files;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.exceptions.ApiErrorException;
import com.files.models.Session;
import com.files.net.FilesApiInterface;
import com.files.net.FilesOkHttpApi;
import com.files.net.HttpMethods.RequestMethods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public abstract class FilesClient {

  private static final Logger log = LogManager.getLogger(FilesClient.class);
  private static FilesApiInterface filesApi = new FilesOkHttpApi();
  public static String apiKey;
  public static Session session;

  public static <T> List<T> request(String url, RequestMethods requestType, TypeReference<List<T>> className, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return filesApi.request(url, requestType, className, parameters, options);
  }

  public static void setProperty(String property, String value) {
    FilesConfig.getInstance().setProperty(property, value);
  }
}
