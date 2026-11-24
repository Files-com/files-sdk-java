package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.GroupUser;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * GroupUser operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#groupUsers()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.GroupUser} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class GroupUserOperations extends ResourceOperations {
  public GroupUserOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Group Users
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<GroupUser> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Group Users
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<GroupUser> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Group Users
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code group_id} (Long): Group ID. If provided, returns memberships of this group. Requires a Site Administrator, a Read-only Administrator, a Workspace Administrator for the group's workspace, or a Group Administrator of this group.</li>
   * <li>{@code user_id} (Long): User ID.  If provided, will return group_users of this user.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code GroupUser} records, in a {@code ListIterator}
   */
  public ListIterator<GroupUser> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }

    if (parameters.containsKey("group_id") && !(parameters.get("group_id") instanceof Long || parameters.get("group_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: group_id must be of type Long or Integer parameters[\"group_id\"]");
    }

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }

    String apiPath = "/group_users";
    TypeReference<List<GroupUser>> typeReference = new TypeReference<List<GroupUser>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Group Users
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<GroupUser> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Group Users
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<GroupUser> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Create Group User
   *
   * @see #create(HashMap, HashMap)
   */
  public GroupUser create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Group User
   *
   * @see #create(HashMap, HashMap)
   */
  public GroupUser create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create Group User
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code group_id} (Long): Required. Group ID to add user to.</li>
   * <li>{@code user_id} (Long): Required. User ID to add to group.</li>
   * <li>{@code admin} (Boolean): Is the user a group administrator?</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code GroupUser}
   */
  public GroupUser create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("group_id") || parameters.get("group_id") == null) {
      throw new NullPointerException("Parameter missing: group_id parameters[\"group_id\"]");
    }

    if (!parameters.containsKey("user_id") || parameters.get("user_id") == null) {
      throw new NullPointerException("Parameter missing: user_id parameters[\"user_id\"]");
    }

    if (parameters.containsKey("group_id") && !(parameters.get("group_id") instanceof Long || parameters.get("group_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: group_id must be of type Long or Integer parameters[\"group_id\"]");
    }

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }

    if (parameters.containsKey("admin") && !(parameters.get("admin") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: admin must be of type Boolean parameters[\"admin\"]");
    }

    String apiPath = "/group_users";
    TypeReference<GroupUser> typeReference = new TypeReference<GroupUser>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Update Group User
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public GroupUser update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Group User
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public GroupUser update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Group User
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public GroupUser update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Group User
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code group_id} (Long): Required. Group ID to add user to.</li>
   * <li>{@code user_id} (Long): Required. User ID to add to group.</li>
   * <li>{@code admin} (Boolean): Is the user a group administrator?</li>
   * </ul>
   *
   * @param id Group User ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code GroupUser}
   */
  public GroupUser update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!parameters.containsKey("group_id") || parameters.get("group_id") == null) {
      throw new NullPointerException("Parameter missing: group_id parameters[\"group_id\"]");
    }

    if (!parameters.containsKey("user_id") || parameters.get("user_id") == null) {
      throw new NullPointerException("Parameter missing: user_id parameters[\"user_id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }

    if (parameters.containsKey("group_id") && !(parameters.get("group_id") instanceof Long || parameters.get("group_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: group_id must be of type Long or Integer parameters[\"group_id\"]");
    }

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }

    if (parameters.containsKey("admin") && !(parameters.get("admin") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: admin must be of type Boolean parameters[\"admin\"]");
    }

    String apiPath = String.format("/group_users/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<GroupUser> typeReference = new TypeReference<GroupUser>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete Group User
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Group User
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Group User
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Group User
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code group_id} (Long): Required. Group ID from which to remove user.</li>
   * <li>{@code user_id} (Long): Required. User ID to remove from group.</li>
   * </ul>
   *
   * @param id Group User ID.
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

    if (!parameters.containsKey("group_id") || parameters.get("group_id") == null) {
      throw new NullPointerException("Parameter missing: group_id parameters[\"group_id\"]");
    }

    if (!parameters.containsKey("user_id") || parameters.get("user_id") == null) {
      throw new NullPointerException("Parameter missing: user_id parameters[\"user_id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }

    if (parameters.containsKey("group_id") && !(parameters.get("group_id") instanceof Long || parameters.get("group_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: group_id must be of type Long or Integer parameters[\"group_id\"]");
    }

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }

    String apiPath = String.format("/group_users/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete Group User
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Group User
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
