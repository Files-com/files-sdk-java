package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.Permission;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * Permission operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#permissions()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.Permission} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class PermissionOperations extends ResourceOperations {
  public PermissionOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Permissions
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Permission> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Permissions
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Permission> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Permissions
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `site_id`, `group_id`, `path`, `user_id`, `partner_id` or `id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `path`, `group_id`, `partner_id` or `user_id`. Valid field combinations are `[ group_id, path ]`, `[ partner_id, path ]`, `[ user_id, path ]`, `[ user_id, group_id ]`, `[ user_id, group_id, path ]`, `[ user_id, group_id, partner_id ]` or `[ user_id, group_id, partner_id, path ]`.</li>
   * <li>{@code filter_prefix} (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `path`.</li>
   * <li>{@code path} (String): Permission path.  If provided, will scope all permissions(including upward) to this path.</li>
   * <li>{@code include_groups} (Boolean): If searching by user or group, also include user's permissions that are inherited from its groups?</li>
   * <li>{@code group_id} (String): </li>
   * <li>{@code partner_id} (String): </li>
   * <li>{@code user_id} (String): </li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Permission} records, in a {@code ListIterator}
   */
  public ListIterator<Permission> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("include_groups") && !(parameters.get("include_groups") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: include_groups must be of type Boolean parameters[\"include_groups\"]");
    }

    if (parameters.containsKey("group_id") && !(parameters.get("group_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: group_id must be of type String parameters[\"group_id\"]");
    }

    if (parameters.containsKey("partner_id") && !(parameters.get("partner_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: partner_id must be of type String parameters[\"partner_id\"]");
    }

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type String parameters[\"user_id\"]");
    }

    String apiPath = "/permissions";
    TypeReference<List<Permission>> typeReference = new TypeReference<List<Permission>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Permissions
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Permission> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Permissions
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Permission> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Create Permission
   *
   * @see #create(HashMap, HashMap)
   */
  public Permission create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Permission
   *
   * @see #create(HashMap, HashMap)
   */
  public Permission create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create Permission
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code path} (String): Required. Folder path</li>
   * <li>{@code group_id} (Long): Group ID. Provide `group_name` or `group_id`</li>
   * <li>{@code group_ids} (String): Group IDs when the Permission requires membership in every listed group. If sent as a string, it should be comma-delimited.</li>
   * <li>{@code permission} (String): Permission type.  Can be `admin`, `full`, `readonly`, `writeonly`, `list`, or `history`</li>
   * <li>{@code recursive} (Boolean): Apply to subfolders recursively? Must be true for `admin` Permissions.</li>
   * <li>{@code partner_id} (Long): Partner ID if this Permission belongs to a partner.</li>
   * <li>{@code user_id} (Long): User ID.  Provide `username` or `user_id`</li>
   * <li>{@code username} (String): User username.  Provide `username` or `user_id`</li>
   * <li>{@code group_name} (String): Group name.  Provide `group_name` or `group_id`</li>
   * <li>{@code site_id} (Long): Site ID. If not provided, will default to current site. Used when creating a permission for a child site.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Permission}
   */
  public Permission create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("path") || parameters.get("path") == null) {
      throw new NullPointerException("Parameter missing: path parameters[\"path\"]");
    }

    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("group_id") && !(parameters.get("group_id") instanceof Long || parameters.get("group_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: group_id must be of type Long or Integer parameters[\"group_id\"]");
    }

    if (parameters.containsKey("group_ids") && !(parameters.get("group_ids") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: group_ids must be of type String parameters[\"group_ids\"]");
    }

    if (parameters.containsKey("permission") && !(parameters.get("permission") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: permission must be of type String parameters[\"permission\"]");
    }

    if (parameters.containsKey("recursive") && !(parameters.get("recursive") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: recursive must be of type Boolean parameters[\"recursive\"]");
    }

    if (parameters.containsKey("partner_id") && !(parameters.get("partner_id") instanceof Long || parameters.get("partner_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: partner_id must be of type Long or Integer parameters[\"partner_id\"]");
    }

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }

    if (parameters.containsKey("username") && !(parameters.get("username") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: username must be of type String parameters[\"username\"]");
    }

    if (parameters.containsKey("group_name") && !(parameters.get("group_name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: group_name must be of type String parameters[\"group_name\"]");
    }

    if (parameters.containsKey("site_id") && !(parameters.get("site_id") instanceof Long || parameters.get("site_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: site_id must be of type Long or Integer parameters[\"site_id\"]");
    }

    String apiPath = "/permissions";
    TypeReference<Permission> typeReference = new TypeReference<Permission>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete Permission
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Permission
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Permission
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Permission
   *
   * @param id Permission ID.
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

    String apiPath = String.format("/permissions/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete Permission
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Permission
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
