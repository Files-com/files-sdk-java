package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.Notification;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * Notification operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#notifications()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.Notification} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class NotificationOperations extends ResourceOperations {
  public NotificationOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Notifications
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Notification> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Notifications
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Notification> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Notifications
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `path`, `user_id` or `group_id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `path`, `user_id`, `workspace_id` or `group_id`. Valid field combinations are `[ workspace_id, path ]`, `[ workspace_id, user_id ]`, `[ workspace_id, group_id ]` or `[ workspace_id, user_id, path ]`.</li>
   * <li>{@code filter_prefix} (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `path`.</li>
   * <li>{@code path} (String): Show notifications for this Path.</li>
   * <li>{@code include_ancestors} (Boolean): If `include_ancestors` is `true` and `path` is specified, include notifications for any parent paths. Ignored if `path` is not specified.</li>
   * <li>{@code group_id} (String): </li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Notification} records, in a {@code ListIterator}
   */
  public ListIterator<Notification> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("include_ancestors") && !(parameters.get("include_ancestors") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: include_ancestors must be of type Boolean parameters[\"include_ancestors\"]");
    }

    if (parameters.containsKey("group_id") && !(parameters.get("group_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: group_id must be of type String parameters[\"group_id\"]");
    }

    String apiPath = "/notifications";
    TypeReference<List<Notification>> typeReference = new TypeReference<List<Notification>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Notifications
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Notification> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Notifications
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Notification> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Notification
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Notification find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Notification
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Notification find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Notification
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Notification find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Notification
   *
   * @param id Notification ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Notification}
   */
  public Notification find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/notifications/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<Notification> typeReference = new TypeReference<Notification>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show Notification
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Notification get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Notification
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Notification get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Notification
   *
   * @see #create(HashMap, HashMap)
   */
  public Notification create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Notification
   *
   * @see #create(HashMap, HashMap)
   */
  public Notification create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create Notification
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): The id of the user to notify. Provide `user_id`, `username` or `group_id`.</li>
   * <li>{@code notify_on_copy} (Boolean): If `true`, copying or moving resources into this path will trigger a notification, in addition to just uploads.</li>
   * <li>{@code notify_on_delete} (Boolean): Trigger on files deleted in this path?</li>
   * <li>{@code notify_on_download} (Boolean): Trigger on files downloaded in this path?</li>
   * <li>{@code notify_on_move} (Boolean): Trigger on files moved to this path?</li>
   * <li>{@code notify_on_upload} (Boolean): Trigger on files created/uploaded/updated/changed in this path?</li>
   * <li>{@code notify_user_actions} (Boolean): If `true` actions initiated by the user will still result in a notification</li>
   * <li>{@code recursive} (Boolean): If `true`, enable notifications for each subfolder in this path</li>
   * <li>{@code send_interval} (String): The time interval that notifications are aggregated by.  Can be `five_minutes`, `fifteen_minutes`, `hourly`, or `daily`.</li>
   * <li>{@code subject} (String): Custom subject line to use for notification emails</li>
   * <li>{@code message} (String): Custom message to include in notification emails</li>
   * <li>{@code triggering_filenames} (String[]): Array of filenames (possibly with wildcards) to scope trigger</li>
   * <li>{@code triggering_group_ids} (Long[]): If set, will only notify on actions made by a member of one of the specified groups</li>
   * <li>{@code triggering_user_ids} (Long[]): If set, will only notify on actions made one of the specified users</li>
   * <li>{@code trigger_by_share_recipients} (Boolean): Notify when actions are performed by a share recipient?</li>
   * <li>{@code workspace_id} (Long): Workspace ID. `0` means the default workspace.</li>
   * <li>{@code group_id} (Long): The ID of the group to notify.  Provide `user_id`, `username` or `group_id`.</li>
   * <li>{@code group_ids} (String): Group IDs when the notification requires multiple groups. If sent as a string, it should be comma-delimited.</li>
   * <li>{@code path} (String): Path</li>
   * <li>{@code username} (String): The username of the user to notify.  Provide `user_id`, `username` or `group_id`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Notification}
   */
  public Notification create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }

    if (parameters.containsKey("notify_on_copy") && !(parameters.get("notify_on_copy") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_copy must be of type Boolean parameters[\"notify_on_copy\"]");
    }

    if (parameters.containsKey("notify_on_delete") && !(parameters.get("notify_on_delete") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_delete must be of type Boolean parameters[\"notify_on_delete\"]");
    }

    if (parameters.containsKey("notify_on_download") && !(parameters.get("notify_on_download") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_download must be of type Boolean parameters[\"notify_on_download\"]");
    }

    if (parameters.containsKey("notify_on_move") && !(parameters.get("notify_on_move") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_move must be of type Boolean parameters[\"notify_on_move\"]");
    }

    if (parameters.containsKey("notify_on_upload") && !(parameters.get("notify_on_upload") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_upload must be of type Boolean parameters[\"notify_on_upload\"]");
    }

    if (parameters.containsKey("notify_user_actions") && !(parameters.get("notify_user_actions") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_user_actions must be of type Boolean parameters[\"notify_user_actions\"]");
    }

    if (parameters.containsKey("recursive") && !(parameters.get("recursive") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: recursive must be of type Boolean parameters[\"recursive\"]");
    }

    if (parameters.containsKey("send_interval") && !(parameters.get("send_interval") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: send_interval must be of type String parameters[\"send_interval\"]");
    }

    if (parameters.containsKey("subject") && !(parameters.get("subject") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: subject must be of type String parameters[\"subject\"]");
    }

    if (parameters.containsKey("message") && !(parameters.get("message") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: message must be of type String parameters[\"message\"]");
    }

    if (parameters.containsKey("triggering_filenames") && !(parameters.get("triggering_filenames") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: triggering_filenames must be of type String[] parameters[\"triggering_filenames\"]");
    }

    if (parameters.containsKey("triggering_group_ids") && !(parameters.get("triggering_group_ids") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: triggering_group_ids must be of type Long[] parameters[\"triggering_group_ids\"]");
    }

    if (parameters.containsKey("triggering_user_ids") && !(parameters.get("triggering_user_ids") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: triggering_user_ids must be of type Long[] parameters[\"triggering_user_ids\"]");
    }

    if (parameters.containsKey("trigger_by_share_recipients") && !(parameters.get("trigger_by_share_recipients") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: trigger_by_share_recipients must be of type Boolean parameters[\"trigger_by_share_recipients\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    if (parameters.containsKey("group_id") && !(parameters.get("group_id") instanceof Long || parameters.get("group_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: group_id must be of type Long or Integer parameters[\"group_id\"]");
    }

    if (parameters.containsKey("group_ids") && !(parameters.get("group_ids") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: group_ids must be of type String parameters[\"group_ids\"]");
    }

    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("username") && !(parameters.get("username") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: username must be of type String parameters[\"username\"]");
    }

    String apiPath = "/notifications";
    TypeReference<Notification> typeReference = new TypeReference<Notification>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Update Notification
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public Notification update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Notification
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public Notification update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Notification
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public Notification update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Notification
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code notify_on_copy} (Boolean): If `true`, copying or moving resources into this path will trigger a notification, in addition to just uploads.</li>
   * <li>{@code notify_on_delete} (Boolean): Trigger on files deleted in this path?</li>
   * <li>{@code notify_on_download} (Boolean): Trigger on files downloaded in this path?</li>
   * <li>{@code notify_on_move} (Boolean): Trigger on files moved to this path?</li>
   * <li>{@code notify_on_upload} (Boolean): Trigger on files created/uploaded/updated/changed in this path?</li>
   * <li>{@code notify_user_actions} (Boolean): If `true` actions initiated by the user will still result in a notification</li>
   * <li>{@code recursive} (Boolean): If `true`, enable notifications for each subfolder in this path</li>
   * <li>{@code send_interval} (String): The time interval that notifications are aggregated by.  Can be `five_minutes`, `fifteen_minutes`, `hourly`, or `daily`.</li>
   * <li>{@code subject} (String): Custom subject line to use for notification emails</li>
   * <li>{@code message} (String): Custom message to include in notification emails</li>
   * <li>{@code triggering_filenames} (String[]): Array of filenames (possibly with wildcards) to scope trigger</li>
   * <li>{@code triggering_group_ids} (Long[]): If set, will only notify on actions made by a member of one of the specified groups</li>
   * <li>{@code triggering_user_ids} (Long[]): If set, will only notify on actions made one of the specified users</li>
   * <li>{@code trigger_by_share_recipients} (Boolean): Notify when actions are performed by a share recipient?</li>
   * <li>{@code workspace_id} (Long): Workspace ID. `0` means the default workspace.</li>
   * </ul>
   *
   * @param id Notification ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Notification}
   */
  public Notification update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("notify_on_copy") && !(parameters.get("notify_on_copy") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_copy must be of type Boolean parameters[\"notify_on_copy\"]");
    }

    if (parameters.containsKey("notify_on_delete") && !(parameters.get("notify_on_delete") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_delete must be of type Boolean parameters[\"notify_on_delete\"]");
    }

    if (parameters.containsKey("notify_on_download") && !(parameters.get("notify_on_download") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_download must be of type Boolean parameters[\"notify_on_download\"]");
    }

    if (parameters.containsKey("notify_on_move") && !(parameters.get("notify_on_move") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_move must be of type Boolean parameters[\"notify_on_move\"]");
    }

    if (parameters.containsKey("notify_on_upload") && !(parameters.get("notify_on_upload") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_upload must be of type Boolean parameters[\"notify_on_upload\"]");
    }

    if (parameters.containsKey("notify_user_actions") && !(parameters.get("notify_user_actions") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_user_actions must be of type Boolean parameters[\"notify_user_actions\"]");
    }

    if (parameters.containsKey("recursive") && !(parameters.get("recursive") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: recursive must be of type Boolean parameters[\"recursive\"]");
    }

    if (parameters.containsKey("send_interval") && !(parameters.get("send_interval") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: send_interval must be of type String parameters[\"send_interval\"]");
    }

    if (parameters.containsKey("subject") && !(parameters.get("subject") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: subject must be of type String parameters[\"subject\"]");
    }

    if (parameters.containsKey("message") && !(parameters.get("message") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: message must be of type String parameters[\"message\"]");
    }

    if (parameters.containsKey("triggering_filenames") && !(parameters.get("triggering_filenames") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: triggering_filenames must be of type String[] parameters[\"triggering_filenames\"]");
    }

    if (parameters.containsKey("triggering_group_ids") && !(parameters.get("triggering_group_ids") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: triggering_group_ids must be of type Long[] parameters[\"triggering_group_ids\"]");
    }

    if (parameters.containsKey("triggering_user_ids") && !(parameters.get("triggering_user_ids") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: triggering_user_ids must be of type Long[] parameters[\"triggering_user_ids\"]");
    }

    if (parameters.containsKey("trigger_by_share_recipients") && !(parameters.get("trigger_by_share_recipients") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: trigger_by_share_recipients must be of type Boolean parameters[\"trigger_by_share_recipients\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    String apiPath = String.format("/notifications/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<Notification> typeReference = new TypeReference<Notification>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete Notification
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Notification
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Notification
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Notification
   *
   * @param id Notification ID.
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

    String apiPath = String.format("/notifications/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete Notification
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Notification
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
