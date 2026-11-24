package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.DatabaseDestination;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * DatabaseDestination operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#databaseDestinations()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.DatabaseDestination} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class DatabaseDestinationOperations extends ResourceOperations {
  public DatabaseDestinationOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Database Destinations
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<DatabaseDestination> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Database Destinations
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<DatabaseDestination> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Database Destinations
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id` and `name`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id` and `name`. Valid field combinations are `[ workspace_id, name ]`.</li>
   * <li>{@code filter_prefix} (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `name`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code DatabaseDestination} records, in a {@code ListIterator}
   */
  public ListIterator<DatabaseDestination> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Object parameters[\"filter\"]");
    }

    if (parameters.containsKey("filter_prefix") && !(parameters.get("filter_prefix") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_prefix must be of type Object parameters[\"filter_prefix\"]");
    }

    String apiPath = "/database_destinations";
    TypeReference<List<DatabaseDestination>> typeReference = new TypeReference<List<DatabaseDestination>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Database Destinations
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<DatabaseDestination> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Database Destinations
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<DatabaseDestination> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Database Destination
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public DatabaseDestination find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Database Destination
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public DatabaseDestination find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Database Destination
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public DatabaseDestination find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Database Destination
   *
   * @param id Database Destination ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code DatabaseDestination}
   */
  public DatabaseDestination find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/database_destinations/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<DatabaseDestination> typeReference = new TypeReference<DatabaseDestination>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show Database Destination
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public DatabaseDestination get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Database Destination
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public DatabaseDestination get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Database Destination
   *
   * @see #create(HashMap, HashMap)
   */
  public DatabaseDestination create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Database Destination
   *
   * @see #create(HashMap, HashMap)
   */
  public DatabaseDestination create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create Database Destination
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Required. Name of the destination.</li>
   * <li>{@code provider} (String): Required. Database provider.</li>
   * <li>{@code config} (Object): Required. Provider settings for the destination. Use the schema matching provider. The connection address and table are fixed after creation.</li>
   * <li>{@code secret_id} (Long): Required. ID of a Secret in the same Workspace. Use a Token Secret for Snowflake, a service account Key/Value Secret for BigQuery, or a Username/Password Secret for a database login.</li>
   * <li>{@code workspace_id} (Long): Workspace ID. 0 means the default workspace.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code DatabaseDestination}
   */
  public DatabaseDestination create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("name") || parameters.get("name") == null) {
      throw new NullPointerException("Parameter missing: name parameters[\"name\"]");
    }

    if (!parameters.containsKey("provider") || parameters.get("provider") == null) {
      throw new NullPointerException("Parameter missing: provider parameters[\"provider\"]");
    }

    if (!parameters.containsKey("config") || parameters.get("config") == null) {
      throw new NullPointerException("Parameter missing: config parameters[\"config\"]");
    }

    if (!parameters.containsKey("secret_id") || parameters.get("secret_id") == null) {
      throw new NullPointerException("Parameter missing: secret_id parameters[\"secret_id\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("provider") && !(parameters.get("provider") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: provider must be of type String parameters[\"provider\"]");
    }

    if (parameters.containsKey("config") && !(parameters.get("config") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: config must be of type Object parameters[\"config\"]");
    }

    if (parameters.containsKey("secret_id") && !(parameters.get("secret_id") instanceof Long || parameters.get("secret_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: secret_id must be of type Long or Integer parameters[\"secret_id\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    String apiPath = "/database_destinations";
    TypeReference<DatabaseDestination> typeReference = new TypeReference<DatabaseDestination>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Update Database Destination
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public DatabaseDestination update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Database Destination
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public DatabaseDestination update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Database Destination
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public DatabaseDestination update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Database Destination
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Name of the destination.</li>
   * <li>{@code secret_id} (Long): ID of a Secret in the same Workspace. Use a Token Secret for Snowflake, a service account Key/Value Secret for BigQuery, or a Username/Password Secret for a database login.</li>
   * <li>{@code config} (Object): Provider settings for the destination. Use the schema matching provider. The connection address and table are fixed after creation.</li>
   * </ul>
   *
   * @param id Database Destination ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code DatabaseDestination}
   */
  public DatabaseDestination update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("secret_id") && !(parameters.get("secret_id") instanceof Long || parameters.get("secret_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: secret_id must be of type Long or Integer parameters[\"secret_id\"]");
    }

    if (parameters.containsKey("config") && !(parameters.get("config") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: config must be of type Object parameters[\"config\"]");
    }

    String apiPath = String.format("/database_destinations/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<DatabaseDestination> typeReference = new TypeReference<DatabaseDestination>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete Database Destination
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Database Destination
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Database Destination
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Database Destination
   *
   * @param id Database Destination ID.
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

    String apiPath = String.format("/database_destinations/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete Database Destination
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Database Destination
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
