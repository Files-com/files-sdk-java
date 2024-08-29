package com.files;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.files.models.ModelInterface;
import com.files.net.FilesResponse;
import com.files.net.HttpMethods.RequestMethods;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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

  public ListIterator<T> loadNextPage() throws RuntimeException {
    if (this.cursor != null) {
      this.parameters.put("cursor", this.cursor);
    }

    FilesResponse response = FilesClient.apiRequest(this.url, this.requestType, this.parameters, this.options);
    try {
      this.data = objectMapper.readValue(response.getBody(), this.className);
      this.data.forEach(item -> {
        if (item instanceof ModelInterface) {
          ((ModelInterface) item).setOptions(this.options);
        }
      });
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
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

  public List<T> all() throws RuntimeException {
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
