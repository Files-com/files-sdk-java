package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.ShareGroup;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * ShareGroup operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#shareGroups()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.ShareGroup} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class ShareGroupOperations extends ResourceOperations {
  public ShareGroupOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Share Groups
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<ShareGroup> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Share Groups
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<ShareGroup> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Share Groups
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ShareGroup} records, in a {@code ListIterator}
   */
  public ListIterator<ShareGroup> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = "/share_groups";
    TypeReference<List<ShareGroup>> typeReference = new TypeReference<List<ShareGroup>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Share Groups
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<ShareGroup> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Share Groups
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<ShareGroup> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Share Group
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public ShareGroup find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Share Group
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public ShareGroup find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Share Group
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public ShareGroup find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Share Group
   *
   * @param id Share Group ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ShareGroup}
   */
  public ShareGroup find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/share_groups/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<ShareGroup> typeReference = new TypeReference<ShareGroup>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show Share Group
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public ShareGroup get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Share Group
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public ShareGroup get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Share Group
   *
   * @see #create(HashMap, HashMap)
   */
  public ShareGroup create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Share Group
   *
   * @see #create(HashMap, HashMap)
   */
  public ShareGroup create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create Share Group
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code notes} (String): Additional notes of the share group</li>
   * <li>{@code name} (String): Required. Name of the share group</li>
   * <li>{@code members} (Object[]): Required. A list of share group members.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ShareGroup}
   */
  public ShareGroup create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("name") || parameters.get("name") == null) {
      throw new NullPointerException("Parameter missing: name parameters[\"name\"]");
    }

    if (!parameters.containsKey("members") || parameters.get("members") == null) {
      throw new NullPointerException("Parameter missing: members parameters[\"members\"]");
    }

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }

    if (parameters.containsKey("notes") && !(parameters.get("notes") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: notes must be of type String parameters[\"notes\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("members") && !(parameters.get("members") instanceof Object[])) {
      throw new IllegalArgumentException("Bad parameter: members must be of type Object[] parameters[\"members\"]");
    }

    String apiPath = "/share_groups";
    TypeReference<ShareGroup> typeReference = new TypeReference<ShareGroup>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Update Share Group
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public ShareGroup update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Share Group
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public ShareGroup update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Share Group
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public ShareGroup update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Share Group
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code notes} (String): Additional notes of the share group</li>
   * <li>{@code name} (String): Name of the share group</li>
   * <li>{@code members} (Object[]): A list of share group members.</li>
   * </ul>
   *
   * @param id Share Group ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ShareGroup}
   */
  public ShareGroup update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("notes") && !(parameters.get("notes") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: notes must be of type String parameters[\"notes\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("members") && !(parameters.get("members") instanceof Object[])) {
      throw new IllegalArgumentException("Bad parameter: members must be of type Object[] parameters[\"members\"]");
    }

    String apiPath = String.format("/share_groups/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<ShareGroup> typeReference = new TypeReference<ShareGroup>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete Share Group
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Share Group
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Share Group
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Share Group
   *
   * @param id Share Group ID.
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

    String apiPath = String.format("/share_groups/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete Share Group
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Share Group
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
