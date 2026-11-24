package com.files;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.net.FilesResponse;
import com.files.net.HttpMethods.RequestMethods;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The base of the generated operations classes, such as
 * {@link com.files.operations.UserOperations}, which run a resource's operations with one client.
 * Get them from the client, for example with {@link FilesClient#users()}.
 */
public abstract class ResourceOperations {
  private final FilesClient client;

  protected ResourceOperations(FilesClient client) {
    if (client == null) {
      throw new IllegalArgumentException("The client cannot be null");
    }
    this.client = client;
  }

  /**
   * The client these operations run with.
   */
  public final FilesClient client() {
    return client;
  }

  // A copy of a map the caller passed, so that the call never changes it and later changes to it
  // never change the call or what it returns.
  protected static HashMap<String, Object> copy(Map<String, Object> map) {
    return map == null ? new HashMap<String, Object>() : new HashMap<String, Object>(map);
  }

  protected final FilesResponse request(RequestMethods method, String path, HashMap<String, Object> parameters,
      HashMap<String, Object> options) {
    return client.runtime().request(method, path, parameters, options);
  }

  protected final <T> T requestItem(RequestMethods method, String path, TypeReference<T> type,
      HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return client.runtime().requestItem(method, path, type, parameters, options);
  }

  protected final <T> ListIterator<T> requestList(RequestMethods method, String path, TypeReference<List<T>> type,
      HashMap<String, Object> parameters, HashMap<String, Object> options) {
    client.runtime().ensureOpen();
    return new ListIterator<T>(client, path, method, type, parameters, options);
  }
}
