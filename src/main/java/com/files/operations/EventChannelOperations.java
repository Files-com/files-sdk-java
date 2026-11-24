package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.EventChannel;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * EventChannel operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#eventChannels()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.EventChannel} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class EventChannelOperations extends ResourceOperations {
  public EventChannelOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Event Channels
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<EventChannel> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Event Channels
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<EventChannel> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Event Channels
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `name`, `enabled`, `default_channel` or `workspace_id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `enabled`, `default_channel` or `workspace_id`. Valid field combinations are `[ workspace_id, enabled ]` and `[ workspace_id, default_channel ]`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code EventChannel} records, in a {@code ListIterator}
   */
  public ListIterator<EventChannel> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = "/event_channels";
    TypeReference<List<EventChannel>> typeReference = new TypeReference<List<EventChannel>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Event Channels
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<EventChannel> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Event Channels
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<EventChannel> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Event Channel
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public EventChannel find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Event Channel
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public EventChannel find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Event Channel
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public EventChannel find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Event Channel
   *
   * @param id Event Channel ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code EventChannel}
   */
  public EventChannel find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/event_channels/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<EventChannel> typeReference = new TypeReference<EventChannel>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show Event Channel
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public EventChannel get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Event Channel
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public EventChannel get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Event Channel
   *
   * @see #create(HashMap, HashMap)
   */
  public EventChannel create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Event Channel
   *
   * @see #create(HashMap, HashMap)
   */
  public EventChannel create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create Event Channel
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Required. Event Channel name.</li>
   * <li>{@code workspace_id} (Long): Workspace ID. 0 means the default workspace.</li>
   * <li>{@code description} (String): Event Channel description.</li>
   * <li>{@code enabled} (Boolean): Whether this Event Channel can dispatch events.</li>
   * <li>{@code default_channel} (Boolean): Whether this Event Channel is the default destination for newly published events.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code EventChannel}
   */
  public EventChannel create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("name") || parameters.get("name") == null) {
      throw new NullPointerException("Parameter missing: name parameters[\"name\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }

    if (parameters.containsKey("enabled") && !(parameters.get("enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: enabled must be of type Boolean parameters[\"enabled\"]");
    }

    if (parameters.containsKey("default_channel") && !(parameters.get("default_channel") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: default_channel must be of type Boolean parameters[\"default_channel\"]");
    }

    String apiPath = "/event_channels";
    TypeReference<EventChannel> typeReference = new TypeReference<EventChannel>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Update Event Channel
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public EventChannel update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Event Channel
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public EventChannel update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Event Channel
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public EventChannel update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Event Channel
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Event Channel name.</li>
   * <li>{@code workspace_id} (Long): Workspace ID. 0 means the default workspace.</li>
   * <li>{@code description} (String): Event Channel description.</li>
   * <li>{@code enabled} (Boolean): Whether this Event Channel can dispatch events.</li>
   * <li>{@code default_channel} (Boolean): Whether this Event Channel is the default destination for newly published events.</li>
   * </ul>
   *
   * @param id Event Channel ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code EventChannel}
   */
  public EventChannel update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }

    if (parameters.containsKey("enabled") && !(parameters.get("enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: enabled must be of type Boolean parameters[\"enabled\"]");
    }

    if (parameters.containsKey("default_channel") && !(parameters.get("default_channel") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: default_channel must be of type Boolean parameters[\"default_channel\"]");
    }

    String apiPath = String.format("/event_channels/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<EventChannel> typeReference = new TypeReference<EventChannel>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete Event Channel
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Event Channel
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Event Channel
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Event Channel
   *
   * @param id Event Channel ID.
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

    String apiPath = String.format("/event_channels/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete Event Channel
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Event Channel
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
