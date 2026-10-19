package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.Snapshot;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * Snapshot operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#snapshots()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.Snapshot} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class SnapshotOperations extends ResourceOperations {
  public SnapshotOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Snapshots
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Snapshot> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Snapshots
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Snapshot> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Snapshots
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Snapshot} records, in a {@code ListIterator}
   */
  public ListIterator<Snapshot> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }

    String apiPath = "/snapshots";
    TypeReference<List<Snapshot>> typeReference = new TypeReference<List<Snapshot>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Snapshots
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Snapshot> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Snapshots
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Snapshot> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Snapshot
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Snapshot find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Snapshot
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Snapshot find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Snapshot
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Snapshot find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Snapshot
   *
   * @param id Snapshot ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Snapshot}
   */
  public Snapshot find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/snapshots/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<Snapshot> typeReference = new TypeReference<Snapshot>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show Snapshot
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Snapshot get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Snapshot
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Snapshot get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Snapshot
   *
   * @see #create(HashMap, HashMap)
   */
  public Snapshot create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Snapshot
   *
   * @see #create(HashMap, HashMap)
   */
  public Snapshot create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create Snapshot
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code expires_at} (String): When the snapshot expires.</li>
   * <li>{@code name} (String): A name for the snapshot.</li>
   * <li>{@code paths} (String[]): An array of paths to add to the snapshot.</li>
   * <li>{@code workspace_id} (Long): Workspace ID. `0` means the default workspace.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Snapshot}
   */
  public Snapshot create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("expires_at") && !(parameters.get("expires_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: expires_at must be of type String parameters[\"expires_at\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("paths") && !(parameters.get("paths") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: paths must be of type String[] parameters[\"paths\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    String apiPath = "/snapshots";
    TypeReference<Snapshot> typeReference = new TypeReference<Snapshot>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Finalize Snapshot
   *
   * @see #performFinalize(Long, HashMap, HashMap)
   */
  public void performFinalize() throws RuntimeException {
    performFinalize(null, null, null);
  }

  /**
   * Finalize Snapshot
   *
   * @see #performFinalize(Long, HashMap, HashMap)
   */
  public void performFinalize(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    performFinalize(id, parameters, null);
  }

  /**
   * Finalize Snapshot
   *
   * @see #performFinalize(Long, HashMap, HashMap)
   */
  public void performFinalize(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    performFinalize(null, parameters, options);
  }

  /**
   * Finalize Snapshot
   *
   * @param id Snapshot ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public void performFinalize(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/snapshots/%s/finalize", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.POST, apiPath, parameters, options);
  }

  /**
   * Update Snapshot
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public Snapshot update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Snapshot
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public Snapshot update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Snapshot
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public Snapshot update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Snapshot
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code expires_at} (String): When the snapshot expires.</li>
   * <li>{@code name} (String): A name for the snapshot.</li>
   * <li>{@code paths} (String[]): An array of paths to add to the snapshot.</li>
   * </ul>
   *
   * @param id Snapshot ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Snapshot}
   */
  public Snapshot update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("expires_at") && !(parameters.get("expires_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: expires_at must be of type String parameters[\"expires_at\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("paths") && !(parameters.get("paths") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: paths must be of type String[] parameters[\"paths\"]");
    }

    String apiPath = String.format("/snapshots/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<Snapshot> typeReference = new TypeReference<Snapshot>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete Snapshot
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Snapshot
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Snapshot
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Snapshot
   *
   * @param id Snapshot ID.
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

    String apiPath = String.format("/snapshots/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete Snapshot
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Snapshot
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
