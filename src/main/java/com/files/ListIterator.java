package com.files;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.files.FilesClient;
import com.files.net.FilesResponse;
import com.files.net.HttpMethods.RequestMethods;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import okhttp3.Headers;
import okhttp3.Response;

public class ListIterator<T> implements Iterable<T> {
  private final String url;
  private final RequestMethods requestType;
  private final TypeReference<List<T>> className;
  private final HashMap<String, Object> parameters;
  private final HashMap<String, Object> options;
  private String cursor;
  private final ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .build();
  public List<T> data = new ArrayList<T>();

  public ListIterator(String url, RequestMethods requestType, TypeReference<List<T>> className,
      HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.url = url;
    this.requestType = requestType;
    this.className = className;
    this.parameters = parameters;
    this.options = options;
  }

  public Boolean hasNextPage() {
    return this.cursor != null;
  }

  @Override
  public Iterator<T> iterator() {
    return this.data.iterator();
  }

  public ListIterator<T> loadNextPage() throws IOException {
    if (this.cursor != null) {
      this.parameters.put("cursor", this.cursor);
    }

    FilesResponse response = FilesClient.apiRequest(this.url, this.requestType, this.parameters, this.options);
    this.data = objectMapper.readValue(response.getBody(), this.className);
    if (response.getHeaders().containsKey("X-Files-Cursor")) {
      this.cursor = response.getHeaders().get("X-Files-Cursor").get(0);
    } else {
      this.cursor = null;
    }

    return this;
  }

  public ListIteratorIterable<T> listAutoPaging() {
    return new ListIteratorIterable<T>(this);
  }

  public List<T> all() throws IOException {
    List<T> allData = new ArrayList<T>();

    // Force starting from the beginning
    this.cursor = null;

    do {
      this.loadNextPage();
      allData.addAll(data);
    } while (this.cursor != null);

    return allData;
  }
}
