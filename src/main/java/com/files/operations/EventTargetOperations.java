package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.EventTarget;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * EventTarget operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#eventTargets()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.EventTarget} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class EventTargetOperations extends ResourceOperations {
  public EventTargetOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Event Targets
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<EventTarget> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Event Targets
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<EventTarget> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Event Targets
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `name`, `enabled` or `workspace_id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `enabled`, `target_type` or `workspace_id`. Valid field combinations are `[ enabled, target_type ]`, `[ workspace_id, enabled ]` or `[ workspace_id, enabled, target_type ]`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code EventTarget} records, in a {@code ListIterator}
   */
  public ListIterator<EventTarget> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = "/event_targets";
    TypeReference<List<EventTarget>> typeReference = new TypeReference<List<EventTarget>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Event Targets
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<EventTarget> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Event Targets
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<EventTarget> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Event Target
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public EventTarget find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Event Target
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public EventTarget find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Event Target
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public EventTarget find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Event Target
   *
   * @param id Event Target ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code EventTarget}
   */
  public EventTarget find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/event_targets/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<EventTarget> typeReference = new TypeReference<EventTarget>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show Event Target
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public EventTarget get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Event Target
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public EventTarget get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Event Target
   *
   * @see #create(HashMap, HashMap)
   */
  public EventTarget create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Event Target
   *
   * @see #create(HashMap, HashMap)
   */
  public EventTarget create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create Event Target
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Required. Event Target name.</li>
   * <li>{@code workspace_id} (Long): Workspace ID. 0 means the default workspace or site-wide.</li>
   * <li>{@code apply_to_all_workspaces} (Boolean): If true, this default-workspace target can receive events from all workspaces.</li>
   * <li>{@code enabled} (Boolean): Whether this Event Target can receive events.</li>
   * <li>{@code config} (Object): Required. Event Target configuration. Fields depend on target_type.</li>
   * <li>{@code delivery_policy} (Object): Event Target delivery policy. Email and folder targets support batch_interval in seconds, between 600 and 86400. Slack and Teams support delivery_mode (immediate or subfolder_summary), summary_path (empty string means root), and summary_delay (15–300 seconds, default 60). The summary applies to matching file activity under summary_path; other matching events retain immediate delivery.</li>
   * <li>{@code target_type} (String): Required. Event Target type.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code EventTarget}
   */
  public EventTarget create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("name") || parameters.get("name") == null) {
      throw new NullPointerException("Parameter missing: name parameters[\"name\"]");
    }

    if (!parameters.containsKey("config") || parameters.get("config") == null) {
      throw new NullPointerException("Parameter missing: config parameters[\"config\"]");
    }

    if (!parameters.containsKey("target_type") || parameters.get("target_type") == null) {
      throw new NullPointerException("Parameter missing: target_type parameters[\"target_type\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    if (parameters.containsKey("apply_to_all_workspaces") && !(parameters.get("apply_to_all_workspaces") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: apply_to_all_workspaces must be of type Boolean parameters[\"apply_to_all_workspaces\"]");
    }

    if (parameters.containsKey("enabled") && !(parameters.get("enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: enabled must be of type Boolean parameters[\"enabled\"]");
    }

    if (parameters.containsKey("config") && !(parameters.get("config") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: config must be of type Object parameters[\"config\"]");
    }

    if (parameters.containsKey("delivery_policy") && !(parameters.get("delivery_policy") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: delivery_policy must be of type Object parameters[\"delivery_policy\"]");
    }

    if (parameters.containsKey("target_type") && !(parameters.get("target_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: target_type must be of type String parameters[\"target_type\"]");
    }

    String apiPath = "/event_targets";
    TypeReference<EventTarget> typeReference = new TypeReference<EventTarget>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Update Event Target
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public EventTarget update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Event Target
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public EventTarget update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Event Target
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public EventTarget update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Event Target
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Event Target name.</li>
   * <li>{@code workspace_id} (Long): Workspace ID. 0 means the default workspace or site-wide.</li>
   * <li>{@code apply_to_all_workspaces} (Boolean): If true, this default-workspace target can receive events from all workspaces.</li>
   * <li>{@code enabled} (Boolean): Whether this Event Target can receive events.</li>
   * <li>{@code config} (Object): Event Target configuration. Fields depend on target_type.</li>
   * <li>{@code delivery_policy} (Object): Event Target delivery policy. Email and folder targets support batch_interval in seconds, between 600 and 86400. Slack and Teams support delivery_mode (immediate or subfolder_summary), summary_path (empty string means root), and summary_delay (15–300 seconds, default 60). The summary applies to matching file activity under summary_path; other matching events retain immediate delivery.</li>
   * </ul>
   *
   * @param id Event Target ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code EventTarget}
   */
  public EventTarget update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    if (parameters.containsKey("apply_to_all_workspaces") && !(parameters.get("apply_to_all_workspaces") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: apply_to_all_workspaces must be of type Boolean parameters[\"apply_to_all_workspaces\"]");
    }

    if (parameters.containsKey("enabled") && !(parameters.get("enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: enabled must be of type Boolean parameters[\"enabled\"]");
    }

    if (parameters.containsKey("config") && !(parameters.get("config") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: config must be of type Object parameters[\"config\"]");
    }

    if (parameters.containsKey("delivery_policy") && !(parameters.get("delivery_policy") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: delivery_policy must be of type Object parameters[\"delivery_policy\"]");
    }

    String apiPath = String.format("/event_targets/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<EventTarget> typeReference = new TypeReference<EventTarget>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete Event Target
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Event Target
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Event Target
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Event Target
   *
   * @param id Event Target ID.
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

    String apiPath = String.format("/event_targets/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete Event Target
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Event Target
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
