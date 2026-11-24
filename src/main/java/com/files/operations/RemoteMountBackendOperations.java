package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.RemoteMountBackend;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * RemoteMountBackend operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#remoteMountBackends()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.RemoteMountBackend} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class RemoteMountBackendOperations extends ResourceOperations {
  public RemoteMountBackendOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Remote Mount Backends
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<RemoteMountBackend> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Remote Mount Backends
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<RemoteMountBackend> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Remote Mount Backends
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `remote_server_mount_id`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code RemoteMountBackend} records, in a {@code ListIterator}
   */
  public ListIterator<RemoteMountBackend> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }

    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Object parameters[\"filter\"]");
    }

    String apiPath = "/remote_mount_backends";
    TypeReference<List<RemoteMountBackend>> typeReference = new TypeReference<List<RemoteMountBackend>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Remote Mount Backends
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<RemoteMountBackend> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Remote Mount Backends
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<RemoteMountBackend> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Remote Mount Backend
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public RemoteMountBackend find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Remote Mount Backend
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public RemoteMountBackend find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Remote Mount Backend
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public RemoteMountBackend find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Remote Mount Backend
   *
   * @param id Remote Mount Backend ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code RemoteMountBackend}
   */
  public RemoteMountBackend find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/remote_mount_backends/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<RemoteMountBackend> typeReference = new TypeReference<RemoteMountBackend>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show Remote Mount Backend
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public RemoteMountBackend get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Remote Mount Backend
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public RemoteMountBackend get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Remote Mount Backend
   *
   * @see #create(HashMap, HashMap)
   */
  public RemoteMountBackend create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Remote Mount Backend
   *
   * @see #create(HashMap, HashMap)
   */
  public RemoteMountBackend create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create Remote Mount Backend
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code enabled} (Boolean): True if this backend is enabled.</li>
   * <li>{@code fall} (Long): Number of consecutive failures before considering the backend unhealthy.</li>
   * <li>{@code health_check_enabled} (Boolean): True if health checks are enabled for this backend.</li>
   * <li>{@code health_check_type} (String): Type of health check to perform.</li>
   * <li>{@code interval} (Long): Interval in seconds between health checks.</li>
   * <li>{@code min_free_cpu} (Double, BigDecimal or String): Minimum free CPU percentage required for this backend to be considered healthy.</li>
   * <li>{@code min_free_mem} (Double, BigDecimal or String): Minimum free memory percentage required for this backend to be considered healthy.</li>
   * <li>{@code priority} (Long): Priority of this backend.</li>
   * <li>{@code remote_path} (String): Path on the remote server to treat as the root of this mount.</li>
   * <li>{@code rise} (Long): Number of consecutive successes before considering the backend healthy.</li>
   * <li>{@code canary_file_path} (String): Required. Path to the canary file used for health checks.</li>
   * <li>{@code remote_server_mount_id} (Long): Required. The mount ID of the Remote Server Mount that this backend is associated with.</li>
   * <li>{@code remote_server_id} (Long): Required. The remote server that this backend is associated with.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code RemoteMountBackend}
   */
  public RemoteMountBackend create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("canary_file_path") || parameters.get("canary_file_path") == null) {
      throw new NullPointerException("Parameter missing: canary_file_path parameters[\"canary_file_path\"]");
    }

    if (!parameters.containsKey("remote_server_mount_id") || parameters.get("remote_server_mount_id") == null) {
      throw new NullPointerException("Parameter missing: remote_server_mount_id parameters[\"remote_server_mount_id\"]");
    }

    if (!parameters.containsKey("remote_server_id") || parameters.get("remote_server_id") == null) {
      throw new NullPointerException("Parameter missing: remote_server_id parameters[\"remote_server_id\"]");
    }

    if (parameters.containsKey("enabled") && !(parameters.get("enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: enabled must be of type Boolean parameters[\"enabled\"]");
    }

    if (parameters.containsKey("fall") && !(parameters.get("fall") instanceof Long || parameters.get("fall") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: fall must be of type Long or Integer parameters[\"fall\"]");
    }

    if (parameters.containsKey("health_check_enabled") && !(parameters.get("health_check_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: health_check_enabled must be of type Boolean parameters[\"health_check_enabled\"]");
    }

    if (parameters.containsKey("health_check_type") && !(parameters.get("health_check_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: health_check_type must be of type String parameters[\"health_check_type\"]");
    }

    if (parameters.containsKey("interval") && !(parameters.get("interval") instanceof Long || parameters.get("interval") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: interval must be of type Long or Integer parameters[\"interval\"]");
    }

    if (parameters.containsKey("min_free_cpu") && !(parameters.get("min_free_cpu") instanceof Double || parameters.get("min_free_cpu") instanceof BigDecimal || parameters.get("min_free_cpu") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: min_free_cpu must be of type Double, BigDecimal or String parameters[\"min_free_cpu\"]");
    }

    if (parameters.containsKey("min_free_mem") && !(parameters.get("min_free_mem") instanceof Double || parameters.get("min_free_mem") instanceof BigDecimal || parameters.get("min_free_mem") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: min_free_mem must be of type Double, BigDecimal or String parameters[\"min_free_mem\"]");
    }

    if (parameters.containsKey("priority") && !(parameters.get("priority") instanceof Long || parameters.get("priority") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: priority must be of type Long or Integer parameters[\"priority\"]");
    }

    if (parameters.containsKey("remote_path") && !(parameters.get("remote_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: remote_path must be of type String parameters[\"remote_path\"]");
    }

    if (parameters.containsKey("rise") && !(parameters.get("rise") instanceof Long || parameters.get("rise") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: rise must be of type Long or Integer parameters[\"rise\"]");
    }

    if (parameters.containsKey("canary_file_path") && !(parameters.get("canary_file_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: canary_file_path must be of type String parameters[\"canary_file_path\"]");
    }

    if (parameters.containsKey("remote_server_mount_id") && !(parameters.get("remote_server_mount_id") instanceof Long || parameters.get("remote_server_mount_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: remote_server_mount_id must be of type Long or Integer parameters[\"remote_server_mount_id\"]");
    }

    if (parameters.containsKey("remote_server_id") && !(parameters.get("remote_server_id") instanceof Long || parameters.get("remote_server_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: remote_server_id must be of type Long or Integer parameters[\"remote_server_id\"]");
    }

    String apiPath = "/remote_mount_backends";
    TypeReference<RemoteMountBackend> typeReference = new TypeReference<RemoteMountBackend>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Reset backend status to healthy
   *
   * @see #resetStatus(Long, HashMap, HashMap)
   */
  public void resetStatus() throws RuntimeException {
    resetStatus(null, null, null);
  }

  /**
   * Reset backend status to healthy
   *
   * @see #resetStatus(Long, HashMap, HashMap)
   */
  public void resetStatus(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    resetStatus(id, parameters, null);
  }

  /**
   * Reset backend status to healthy
   *
   * @see #resetStatus(Long, HashMap, HashMap)
   */
  public void resetStatus(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    resetStatus(null, parameters, options);
  }

  /**
   * Reset backend status to healthy
   *
   * @param id Remote Mount Backend ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public void resetStatus(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/remote_mount_backends/%s/reset_status", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.POST, apiPath, parameters, options);
  }

  /**
   * Update Remote Mount Backend
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public RemoteMountBackend update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Remote Mount Backend
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public RemoteMountBackend update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Remote Mount Backend
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public RemoteMountBackend update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Remote Mount Backend
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code enabled} (Boolean): True if this backend is enabled.</li>
   * <li>{@code fall} (Long): Number of consecutive failures before considering the backend unhealthy.</li>
   * <li>{@code health_check_enabled} (Boolean): True if health checks are enabled for this backend.</li>
   * <li>{@code health_check_type} (String): Type of health check to perform.</li>
   * <li>{@code interval} (Long): Interval in seconds between health checks.</li>
   * <li>{@code min_free_cpu} (Double, BigDecimal or String): Minimum free CPU percentage required for this backend to be considered healthy.</li>
   * <li>{@code min_free_mem} (Double, BigDecimal or String): Minimum free memory percentage required for this backend to be considered healthy.</li>
   * <li>{@code priority} (Long): Priority of this backend.</li>
   * <li>{@code remote_path} (String): Path on the remote server to treat as the root of this mount.</li>
   * <li>{@code rise} (Long): Number of consecutive successes before considering the backend healthy.</li>
   * <li>{@code canary_file_path} (String): Path to the canary file used for health checks.</li>
   * <li>{@code remote_server_id} (Long): The remote server that this backend is associated with.</li>
   * </ul>
   *
   * @param id Remote Mount Backend ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code RemoteMountBackend}
   */
  public RemoteMountBackend update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("enabled") && !(parameters.get("enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: enabled must be of type Boolean parameters[\"enabled\"]");
    }

    if (parameters.containsKey("fall") && !(parameters.get("fall") instanceof Long || parameters.get("fall") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: fall must be of type Long or Integer parameters[\"fall\"]");
    }

    if (parameters.containsKey("health_check_enabled") && !(parameters.get("health_check_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: health_check_enabled must be of type Boolean parameters[\"health_check_enabled\"]");
    }

    if (parameters.containsKey("health_check_type") && !(parameters.get("health_check_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: health_check_type must be of type String parameters[\"health_check_type\"]");
    }

    if (parameters.containsKey("interval") && !(parameters.get("interval") instanceof Long || parameters.get("interval") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: interval must be of type Long or Integer parameters[\"interval\"]");
    }

    if (parameters.containsKey("min_free_cpu") && !(parameters.get("min_free_cpu") instanceof Double || parameters.get("min_free_cpu") instanceof BigDecimal || parameters.get("min_free_cpu") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: min_free_cpu must be of type Double, BigDecimal or String parameters[\"min_free_cpu\"]");
    }

    if (parameters.containsKey("min_free_mem") && !(parameters.get("min_free_mem") instanceof Double || parameters.get("min_free_mem") instanceof BigDecimal || parameters.get("min_free_mem") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: min_free_mem must be of type Double, BigDecimal or String parameters[\"min_free_mem\"]");
    }

    if (parameters.containsKey("priority") && !(parameters.get("priority") instanceof Long || parameters.get("priority") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: priority must be of type Long or Integer parameters[\"priority\"]");
    }

    if (parameters.containsKey("remote_path") && !(parameters.get("remote_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: remote_path must be of type String parameters[\"remote_path\"]");
    }

    if (parameters.containsKey("rise") && !(parameters.get("rise") instanceof Long || parameters.get("rise") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: rise must be of type Long or Integer parameters[\"rise\"]");
    }

    if (parameters.containsKey("canary_file_path") && !(parameters.get("canary_file_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: canary_file_path must be of type String parameters[\"canary_file_path\"]");
    }

    if (parameters.containsKey("remote_server_id") && !(parameters.get("remote_server_id") instanceof Long || parameters.get("remote_server_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: remote_server_id must be of type Long or Integer parameters[\"remote_server_id\"]");
    }

    String apiPath = String.format("/remote_mount_backends/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<RemoteMountBackend> typeReference = new TypeReference<RemoteMountBackend>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete Remote Mount Backend
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Remote Mount Backend
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Remote Mount Backend
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Remote Mount Backend
   *
   * @param id Remote Mount Backend ID.
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

    String apiPath = String.format("/remote_mount_backends/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete Remote Mount Backend
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Remote Mount Backend
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
