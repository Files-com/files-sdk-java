package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.UserRequest;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * UserRequest operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#userRequests()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.UserRequest} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class UserRequestOperations extends ResourceOperations {
  public UserRequestOperations(FilesClient client) {
    super(client);
  }

  /**
   * List User Requests
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<UserRequest> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List User Requests
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<UserRequest> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List User Requests
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code UserRequest} records, in a {@code ListIterator}
   */
  public ListIterator<UserRequest> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }

    String apiPath = "/user_requests";
    TypeReference<List<UserRequest>> typeReference = new TypeReference<List<UserRequest>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List User Requests
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<UserRequest> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List User Requests
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<UserRequest> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show User Request
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public UserRequest find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show User Request
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public UserRequest find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show User Request
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public UserRequest find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show User Request
   *
   * @param id User Request ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code UserRequest}
   */
  public UserRequest find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }

    String apiPath = String.format("/user_requests/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<UserRequest> typeReference = new TypeReference<UserRequest>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show User Request
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public UserRequest get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show User Request
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public UserRequest get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create User Request
   *
   * @see #create(HashMap, HashMap)
   */
  public UserRequest create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create User Request
   *
   * @see #create(HashMap, HashMap)
   */
  public UserRequest create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create User Request
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Required. Name of user requested</li>
   * <li>{@code email} (String): Required. Email of user requested</li>
   * <li>{@code details} (String): Required. Details of the user request</li>
   * <li>{@code company} (String): Company of the user requested</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code UserRequest}
   */
  public UserRequest create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("name") || parameters.get("name") == null) {
      throw new NullPointerException("Parameter missing: name parameters[\"name\"]");
    }

    if (!parameters.containsKey("email") || parameters.get("email") == null) {
      throw new NullPointerException("Parameter missing: email parameters[\"email\"]");
    }

    if (!parameters.containsKey("details") || parameters.get("details") == null) {
      throw new NullPointerException("Parameter missing: details parameters[\"details\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("email") && !(parameters.get("email") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: email must be of type String parameters[\"email\"]");
    }

    if (parameters.containsKey("details") && !(parameters.get("details") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: details must be of type String parameters[\"details\"]");
    }

    if (parameters.containsKey("company") && !(parameters.get("company") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: company must be of type String parameters[\"company\"]");
    }

    String apiPath = "/user_requests";
    TypeReference<UserRequest> typeReference = new TypeReference<UserRequest>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete User Request
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete User Request
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete User Request
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete User Request
   *
   * @param id User Request ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }

    String apiPath = String.format("/user_requests/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete User Request
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete User Request
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
