package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.Action;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * History operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#histories()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.History} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class HistoryOperations extends ResourceOperations {
  public HistoryOperations(FilesClient client) {
    super(client);
  }

  /**
   * List history for specific file
   *
   * @see #listForFile(String, HashMap, HashMap)
   */
  public ListIterator<Action> listForFile() throws RuntimeException {
    return listForFile(null, null, null);
  }

  /**
   * List history for specific file
   *
   * @see #listForFile(String, HashMap, HashMap)
   */
  public ListIterator<Action> listForFile(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return listForFile(path, parameters, null);
  }

  /**
   * List history for specific file
   *
   * @see #listForFile(String, HashMap, HashMap)
   */
  public ListIterator<Action> listForFile(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return listForFile(null, parameters, options);
  }

  /**
   * List history for specific file
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code start_at} (String): Leave blank or set to a date/time to filter earlier entries.</li>
   * <li>{@code end_at} (String): Leave blank or set to a date/time to filter later entries.</li>
   * <li>{@code display} (String): Display format. Leave blank or set to `full` or `parent`.</li>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`.</li>
   * </ul>
   *
   * @param path Path to operate on.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Action} records, in a {@code ListIterator}
   */
  public ListIterator<Action> listForFile(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }

    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (parameters.containsKey("start_at") && !(parameters.get("start_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: start_at must be of type String parameters[\"start_at\"]");
    }

    if (parameters.containsKey("end_at") && !(parameters.get("end_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: end_at must be of type String parameters[\"end_at\"]");
    }

    if (parameters.containsKey("display") && !(parameters.get("display") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: display must be of type String parameters[\"display\"]");
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

    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    String apiPath = String.format("/history/files/%s", UrlUtils.encodeUrlPath(path));
    TypeReference<List<Action>> typeReference = new TypeReference<List<Action>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List history for specific folder
   *
   * @see #listForFolder(String, HashMap, HashMap)
   */
  public ListIterator<Action> listForFolder() throws RuntimeException {
    return listForFolder(null, null, null);
  }

  /**
   * List history for specific folder
   *
   * @see #listForFolder(String, HashMap, HashMap)
   */
  public ListIterator<Action> listForFolder(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return listForFolder(path, parameters, null);
  }

  /**
   * List history for specific folder
   *
   * @see #listForFolder(String, HashMap, HashMap)
   */
  public ListIterator<Action> listForFolder(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return listForFolder(null, parameters, options);
  }

  /**
   * List history for specific folder
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code start_at} (String): Leave blank or set to a date/time to filter earlier entries.</li>
   * <li>{@code end_at} (String): Leave blank or set to a date/time to filter later entries.</li>
   * <li>{@code display} (String): Display format. Leave blank or set to `full` or `parent`.</li>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`.</li>
   * </ul>
   *
   * @param path Path to operate on.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Action} records, in a {@code ListIterator}
   */
  public ListIterator<Action> listForFolder(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }

    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (parameters.containsKey("start_at") && !(parameters.get("start_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: start_at must be of type String parameters[\"start_at\"]");
    }

    if (parameters.containsKey("end_at") && !(parameters.get("end_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: end_at must be of type String parameters[\"end_at\"]");
    }

    if (parameters.containsKey("display") && !(parameters.get("display") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: display must be of type String parameters[\"display\"]");
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

    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    String apiPath = String.format("/history/folders/%s", UrlUtils.encodeUrlPath(path));
    TypeReference<List<Action>> typeReference = new TypeReference<List<Action>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List history for specific user
   *
   * @see #listForUser(Long, HashMap, HashMap)
   */
  public ListIterator<Action> listForUser() throws RuntimeException {
    return listForUser(null, null, null);
  }

  /**
   * List history for specific user
   *
   * @see #listForUser(Long, HashMap, HashMap)
   */
  public ListIterator<Action> listForUser(Long user_id, HashMap<String, Object> parameters) throws RuntimeException {
    return listForUser(user_id, parameters, null);
  }

  /**
   * List history for specific user
   *
   * @see #listForUser(Long, HashMap, HashMap)
   */
  public ListIterator<Action> listForUser(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return listForUser(null, parameters, options);
  }

  /**
   * List history for specific user
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code start_at} (String): Leave blank or set to a date/time to filter earlier entries.</li>
   * <li>{@code end_at} (String): Leave blank or set to a date/time to filter later entries.</li>
   * <li>{@code display} (String): Display format. Leave blank or set to `full` or `parent`.</li>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`.</li>
   * </ul>
   *
   * @param user_id User ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Action} records, in a {@code ListIterator}
   */
  public ListIterator<Action> listForUser(Long user_id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (user_id == null && parameters.containsKey("user_id") && parameters.get("user_id") != null) {
      user_id = (Long) parameters.get("user_id");
    }

    if (user_id == null) {
      throw new NullPointerException("Argument or Parameter missing: user_id parameters[\"user_id\"]");
    }

    if (parameters.containsKey("start_at") && !(parameters.get("start_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: start_at must be of type String parameters[\"start_at\"]");
    }

    if (parameters.containsKey("end_at") && !(parameters.get("end_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: end_at must be of type String parameters[\"end_at\"]");
    }

    if (parameters.containsKey("display") && !(parameters.get("display") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: display must be of type String parameters[\"display\"]");
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

    if (!(user_id instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }

    String apiPath = String.format("/history/users/%s", UrlUtils.encodeUrlPath(String.valueOf(user_id)));
    TypeReference<List<Action>> typeReference = new TypeReference<List<Action>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List site login history
   *
   * @see #listLogins(HashMap, HashMap)
   */
  public ListIterator<Action> listLogins() throws RuntimeException {
    return listLogins(null, null);
  }

  /**
   * List site login history
   *
   * @see #listLogins(HashMap, HashMap)
   */
  public ListIterator<Action> listLogins(HashMap<String, Object> parameters) throws RuntimeException {
    return listLogins(parameters, null);
  }

  /**
   * List site login history
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code start_at} (String): Leave blank or set to a date/time to filter earlier entries.</li>
   * <li>{@code end_at} (String): Leave blank or set to a date/time to filter later entries.</li>
   * <li>{@code display} (String): Display format. Leave blank or set to `full` or `parent`.</li>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Action} records, in a {@code ListIterator}
   */
  public ListIterator<Action> listLogins(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("start_at") && !(parameters.get("start_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: start_at must be of type String parameters[\"start_at\"]");
    }

    if (parameters.containsKey("end_at") && !(parameters.get("end_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: end_at must be of type String parameters[\"end_at\"]");
    }

    if (parameters.containsKey("display") && !(parameters.get("display") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: display must be of type String parameters[\"display\"]");
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

    String apiPath = "/history/login";
    TypeReference<List<Action>> typeReference = new TypeReference<List<Action>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List site full action history
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Action> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List site full action history
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Action> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List site full action history
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code start_at} (String): Leave blank or set to a date/time to filter earlier entries.</li>
   * <li>{@code end_at} (String): Leave blank or set to a date/time to filter later entries.</li>
   * <li>{@code display} (String): Display format. Leave blank or set to `full` or `parent`.</li>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `user_id`, `folder` or `path`. Valid field combinations are `[  ]`, `[ path ]`, `[ path ]` or `[ path ]`.</li>
   * <li>{@code filter_prefix} (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `path`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Action} records, in a {@code ListIterator}
   */
  public ListIterator<Action> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("start_at") && !(parameters.get("start_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: start_at must be of type String parameters[\"start_at\"]");
    }

    if (parameters.containsKey("end_at") && !(parameters.get("end_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: end_at must be of type String parameters[\"end_at\"]");
    }

    if (parameters.containsKey("display") && !(parameters.get("display") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: display must be of type String parameters[\"display\"]");
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

    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Object parameters[\"filter\"]");
    }

    if (parameters.containsKey("filter_prefix") && !(parameters.get("filter_prefix") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_prefix must be of type Object parameters[\"filter_prefix\"]");
    }

    String apiPath = "/history";
    TypeReference<List<Action>> typeReference = new TypeReference<List<Action>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List site full action history
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Action> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List site full action history
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Action> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }
}
