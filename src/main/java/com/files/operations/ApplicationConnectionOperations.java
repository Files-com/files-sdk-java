package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.ApplicationConnection;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ApplicationConnection operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#applicationConnections()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.ApplicationConnection} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class ApplicationConnectionOperations extends ResourceOperations {
  public ApplicationConnectionOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Application Connections
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<ApplicationConnection> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Application Connections
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<ApplicationConnection> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Application Connections
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `name` or `application_key`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id` and `application_key`. Valid field combinations are `[ workspace_id, application_key ]`.</li>
   * <li>{@code filter_prefix} (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `application_key`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ApplicationConnection} records, in a {@code ListIterator}
   */
  public ListIterator<ApplicationConnection> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = "/application_connections";
    TypeReference<List<ApplicationConnection>> typeReference = new TypeReference<List<ApplicationConnection>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Application Connections
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<ApplicationConnection> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Application Connections
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<ApplicationConnection> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Application Connection
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public ApplicationConnection find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Application Connection
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public ApplicationConnection find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Application Connection
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public ApplicationConnection find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Application Connection
   *
   * @param id Application Connection ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ApplicationConnection}
   */
  public ApplicationConnection find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/application_connections/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<ApplicationConnection> typeReference = new TypeReference<ApplicationConnection>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show Application Connection
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public ApplicationConnection get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Application Connection
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public ApplicationConnection get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Application Connection
   *
   * @see #create(HashMap, HashMap)
   */
  public ApplicationConnection create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Application Connection
   *
   * @see #create(HashMap, HashMap)
   */
  public ApplicationConnection create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create Application Connection
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Required. Connection display name.</li>
   * <li>{@code secret_id} (Long): ID of the credential in the Secret Store, if the connection uses a credential.</li>
   * <li>{@code application_key} (String): Required. Application key from the Automation applications list.</li>
   * <li>{@code origin} (String): Required. HTTPS origin for this connection.</li>
   * <li>{@code workspace_id} (Long): Workspace that owns this connection.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ApplicationConnection}
   */
  public ApplicationConnection create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("name") || parameters.get("name") == null) {
      throw new NullPointerException("Parameter missing: name parameters[\"name\"]");
    }

    if (!parameters.containsKey("application_key") || parameters.get("application_key") == null) {
      throw new NullPointerException("Parameter missing: application_key parameters[\"application_key\"]");
    }

    if (!parameters.containsKey("origin") || parameters.get("origin") == null) {
      throw new NullPointerException("Parameter missing: origin parameters[\"origin\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("secret_id") && !(parameters.get("secret_id") instanceof Long || parameters.get("secret_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: secret_id must be of type Long or Integer parameters[\"secret_id\"]");
    }

    if (parameters.containsKey("application_key") && !(parameters.get("application_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: application_key must be of type String parameters[\"application_key\"]");
    }

    if (parameters.containsKey("origin") && !(parameters.get("origin") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: origin must be of type String parameters[\"origin\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    String apiPath = "/application_connections";
    TypeReference<ApplicationConnection> typeReference = new TypeReference<ApplicationConnection>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Update Application Connection
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public ApplicationConnection update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Application Connection
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public ApplicationConnection update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Application Connection
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public ApplicationConnection update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Application Connection
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Connection display name.</li>
   * <li>{@code secret_id} (Long): ID of the credential in the Secret Store, if the connection uses a credential.</li>
   * </ul>
   *
   * @param id Application Connection ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ApplicationConnection}
   */
  public ApplicationConnection update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/application_connections/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<ApplicationConnection> typeReference = new TypeReference<ApplicationConnection>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete Application Connection
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Application Connection
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Application Connection
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Application Connection
   *
   * @param id Application Connection ID.
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

    String apiPath = String.format("/application_connections/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete Application Connection
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Application Connection
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
