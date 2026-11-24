package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.BundleNotification;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * BundleNotification operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#bundleNotifications()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.BundleNotification} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class BundleNotificationOperations extends ResourceOperations {
  public BundleNotificationOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Share Link Notifications
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<BundleNotification> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Share Link Notifications
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<BundleNotification> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Share Link Notifications
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id` and `bundle_id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `bundle_id`.</li>
   * <li>{@code bundle_id} (Long): Bundle ID</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code BundleNotification} records, in a {@code ListIterator}
   */
  public ListIterator<BundleNotification> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
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

    if (parameters.containsKey("bundle_id") && !(parameters.get("bundle_id") instanceof Long || parameters.get("bundle_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: bundle_id must be of type Long or Integer parameters[\"bundle_id\"]");
    }

    String apiPath = "/bundle_notifications";
    TypeReference<List<BundleNotification>> typeReference = new TypeReference<List<BundleNotification>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Share Link Notifications
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<BundleNotification> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Share Link Notifications
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<BundleNotification> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Share Link Notification
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public BundleNotification find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Share Link Notification
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public BundleNotification find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Share Link Notification
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public BundleNotification find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Share Link Notification
   *
   * @param id Bundle Notification ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code BundleNotification}
   */
  public BundleNotification find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/bundle_notifications/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<BundleNotification> typeReference = new TypeReference<BundleNotification>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show Share Link Notification
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public BundleNotification get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Share Link Notification
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public BundleNotification get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Share Link Notification
   *
   * @see #create(HashMap, HashMap)
   */
  public BundleNotification create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Share Link Notification
   *
   * @see #create(HashMap, HashMap)
   */
  public BundleNotification create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create Share Link Notification
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code bundle_id} (Long): Required. Bundle ID to notify on</li>
   * <li>{@code notify_user_id} (Long): The id of the user to notify.</li>
   * <li>{@code notify_on_registration} (Boolean): Triggers bundle notification when a registration action occurs for it.</li>
   * <li>{@code notify_on_upload} (Boolean): Triggers bundle notification when a upload action occurs for it.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code BundleNotification}
   */
  public BundleNotification create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("bundle_id") || parameters.get("bundle_id") == null) {
      throw new NullPointerException("Parameter missing: bundle_id parameters[\"bundle_id\"]");
    }

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }

    if (parameters.containsKey("bundle_id") && !(parameters.get("bundle_id") instanceof Long || parameters.get("bundle_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: bundle_id must be of type Long or Integer parameters[\"bundle_id\"]");
    }

    if (parameters.containsKey("notify_user_id") && !(parameters.get("notify_user_id") instanceof Long || parameters.get("notify_user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: notify_user_id must be of type Long or Integer parameters[\"notify_user_id\"]");
    }

    if (parameters.containsKey("notify_on_registration") && !(parameters.get("notify_on_registration") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_registration must be of type Boolean parameters[\"notify_on_registration\"]");
    }

    if (parameters.containsKey("notify_on_upload") && !(parameters.get("notify_on_upload") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_upload must be of type Boolean parameters[\"notify_on_upload\"]");
    }

    String apiPath = "/bundle_notifications";
    TypeReference<BundleNotification> typeReference = new TypeReference<BundleNotification>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Update Share Link Notification
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public BundleNotification update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Share Link Notification
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public BundleNotification update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Share Link Notification
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public BundleNotification update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Share Link Notification
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code notify_on_registration} (Boolean): Triggers bundle notification when a registration action occurs for it.</li>
   * <li>{@code notify_on_upload} (Boolean): Triggers bundle notification when a upload action occurs for it.</li>
   * </ul>
   *
   * @param id Bundle Notification ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code BundleNotification}
   */
  public BundleNotification update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("notify_on_registration") && !(parameters.get("notify_on_registration") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_registration must be of type Boolean parameters[\"notify_on_registration\"]");
    }

    if (parameters.containsKey("notify_on_upload") && !(parameters.get("notify_on_upload") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_upload must be of type Boolean parameters[\"notify_on_upload\"]");
    }

    String apiPath = String.format("/bundle_notifications/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<BundleNotification> typeReference = new TypeReference<BundleNotification>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete Share Link Notification
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Share Link Notification
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Share Link Notification
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Share Link Notification
   *
   * @param id Bundle Notification ID.
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

    String apiPath = String.format("/bundle_notifications/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete Share Link Notification
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Share Link Notification
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
