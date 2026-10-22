package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.Request;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * Request operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#requests()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.Request} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class RequestOperations extends ResourceOperations {
  public RequestOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Requests
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Request> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Requests
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Request> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Requests
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are .</li>
   * <li>{@code mine} (Boolean): Only show requests of the current user?  (Defaults to true if current user is not a site admin.)</li>
   * <li>{@code path} (String): Path to show requests for.  If omitted, shows all paths. Send `/` to represent the root directory.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Request} records, in a {@code ListIterator}
   */
  public ListIterator<Request> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }

    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Object parameters[\"sort_by\"]");
    }

    if (parameters.containsKey("mine") && !(parameters.get("mine") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: mine must be of type Boolean parameters[\"mine\"]");
    }

    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    String apiPath = "/requests";
    TypeReference<List<Request>> typeReference = new TypeReference<List<Request>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Requests
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Request> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Requests
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Request> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * List Requests
   *
   * @see #getFolder(String, HashMap, HashMap)
   */
  public List<Request> getFolder() throws RuntimeException {
    return getFolder(null, null, null);
  }

  /**
   * List Requests
   *
   * @see #getFolder(String, HashMap, HashMap)
   */
  public List<Request> getFolder(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return getFolder(path, parameters, null);
  }

  /**
   * List Requests
   *
   * @see #getFolder(String, HashMap, HashMap)
   */
  public List<Request> getFolder(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return getFolder(null, parameters, options);
  }

  /**
   * List Requests
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are .</li>
   * <li>{@code mine} (Boolean): Only show requests of the current user?  (Defaults to true if current user is not a site admin.)</li>
   * </ul>
   *
   * @param path Path to show requests for.  If omitted, shows all paths. Send `/` to represent the root directory.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code List<Request>}
   */
  public List<Request> getFolder(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }

    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }

    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Object parameters[\"sort_by\"]");
    }

    if (parameters.containsKey("mine") && !(parameters.get("mine") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: mine must be of type Boolean parameters[\"mine\"]");
    }

    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    String apiPath = String.format("/requests/folders/%s", UrlUtils.encodeUrlPath(path));
    TypeReference<List<Request>> typeReference = new TypeReference<List<Request>>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Create Request
   *
   * @see #create(HashMap, HashMap)
   */
  public Request create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Request
   *
   * @see #create(HashMap, HashMap)
   */
  public Request create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create Request
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code path} (String): Required. Folder path on which to request the file.</li>
   * <li>{@code destination} (String): Required. Destination filename (without extension) to request.</li>
   * <li>{@code user_ids} (String): A list of user IDs to request the file from. If sent as a string, it should be comma-delimited.</li>
   * <li>{@code group_ids} (String): A list of group IDs to request the file from. If sent as a string, it should be comma-delimited.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Request}
   */
  public Request create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("path") || parameters.get("path") == null) {
      throw new NullPointerException("Parameter missing: path parameters[\"path\"]");
    }

    if (!parameters.containsKey("destination") || parameters.get("destination") == null) {
      throw new NullPointerException("Parameter missing: destination parameters[\"destination\"]");
    }

    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("destination") && !(parameters.get("destination") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: destination must be of type String parameters[\"destination\"]");
    }

    if (parameters.containsKey("user_ids") && !(parameters.get("user_ids") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: user_ids must be of type String parameters[\"user_ids\"]");
    }

    if (parameters.containsKey("group_ids") && !(parameters.get("group_ids") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: group_ids must be of type String parameters[\"group_ids\"]");
    }

    String apiPath = "/requests";
    TypeReference<Request> typeReference = new TypeReference<Request>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete Request
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Request
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Request
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Request
   *
   * @param id Request ID.
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

    String apiPath = String.format("/requests/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete Request
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Request
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
