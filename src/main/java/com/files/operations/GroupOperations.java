package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.Group;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * Group operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#groups()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.Group} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class GroupOperations extends ResourceOperations {
  public GroupOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Groups
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Group> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Groups
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Group> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Groups
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `site_id`, `workspace_id` or `name`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `name` and `workspace_id`. Valid field combinations are `[ workspace_id, name ]`.</li>
   * <li>{@code filter_prefix} (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `name`.</li>
   * <li>{@code ids} (String): Comma-separated list of group ids to include in results.</li>
   * <li>{@code include_parent_site_groups} (Boolean): Include groups from the parent site.</li>
   * <li>{@code include_default_workspace_groups} (Boolean): When scoped to a Custom Workspace, include Default Workspace groups that have permissions in that Workspace. Available to Site Administrators, and to Workspace Administrators and Folder Admins in that Workspace when the site shares its Default Workspace directory with Workspaces.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Group} records, in a {@code ListIterator}
   */
  public ListIterator<Group> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("ids") && !(parameters.get("ids") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: ids must be of type String parameters[\"ids\"]");
    }

    if (parameters.containsKey("include_parent_site_groups") && !(parameters.get("include_parent_site_groups") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: include_parent_site_groups must be of type Boolean parameters[\"include_parent_site_groups\"]");
    }

    if (parameters.containsKey("include_default_workspace_groups") && !(parameters.get("include_default_workspace_groups") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: include_default_workspace_groups must be of type Boolean parameters[\"include_default_workspace_groups\"]");
    }

    String apiPath = "/groups";
    TypeReference<List<Group>> typeReference = new TypeReference<List<Group>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Groups
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Group> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Groups
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Group> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Group
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Group find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Group
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Group find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Group
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Group find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Group
   *
   * @param id Group ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Group}
   */
  public Group find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/groups/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<Group> typeReference = new TypeReference<Group>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show Group
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Group get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Group
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Group get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Group
   *
   * @see #create(HashMap, HashMap)
   */
  public Group create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Group
   *
   * @see #create(HashMap, HashMap)
   */
  public Group create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create Group
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code notes} (String): Group notes.</li>
   * <li>{@code user_ids} (String): A list of user ids. If sent as a string, should be comma-delimited.</li>
   * <li>{@code admin_ids} (String): A list of group admin user ids. If sent as a string, should be comma-delimited.</li>
   * <li>{@code ai_assistant_personality_id} (Long): AI Assistant Personality ID assigned to this Group, if any. Users in the Group inherit it unless a direct per-user or Partner assignment overrides it.</li>
   * <li>{@code ftp_permission} (Boolean): If true, users in this group can use FTP to login.  This will override a false value of `ftp_permission` on the user level.</li>
   * <li>{@code sftp_permission} (Boolean): If true, users in this group can use SFTP to login.  This will override a false value of `sftp_permission` on the user level.</li>
   * <li>{@code dav_permission} (Boolean): If true, users in this group can use WebDAV to login.  This will override a false value of `dav_permission` on the user level.</li>
   * <li>{@code restapi_permission} (Boolean): If true, users in this group can use the REST API to login.  This will override a false value of `restapi_permission` on the user level.</li>
   * <li>{@code s3_compatible_endpoint_permission} (Boolean): If true, users in this group can access the S3-compatible endpoint. This will override a false value of `s3_compatible_endpoint_permission` on the user level. Defaults to false.</li>
   * <li>{@code desktop_configuration_profile_id} (Long): Desktop Configuration Profile ID assigned to this Group, if any. Users in the Group inherit it unless a direct per-user assignment overrides it.</li>
   * <li>{@code integration_centric_profile_id} (Long): Integration Centric Profile ID assigned to this Group, if any. Users in the Group inherit it unless a direct per-user assignment overrides it.</li>
   * <li>{@code allowed_ips} (String): A list of allowed IPs if applicable.  Newline delimited</li>
   * <li>{@code name} (String): Required. Group name.</li>
   * <li>{@code workspace_id} (Long): Workspace ID</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Group}
   */
  public Group create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("name") || parameters.get("name") == null) {
      throw new NullPointerException("Parameter missing: name parameters[\"name\"]");
    }

    if (parameters.containsKey("notes") && !(parameters.get("notes") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: notes must be of type String parameters[\"notes\"]");
    }

    if (parameters.containsKey("user_ids") && !(parameters.get("user_ids") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: user_ids must be of type String parameters[\"user_ids\"]");
    }

    if (parameters.containsKey("admin_ids") && !(parameters.get("admin_ids") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: admin_ids must be of type String parameters[\"admin_ids\"]");
    }

    if (parameters.containsKey("ai_assistant_personality_id") && !(parameters.get("ai_assistant_personality_id") instanceof Long || parameters.get("ai_assistant_personality_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: ai_assistant_personality_id must be of type Long or Integer parameters[\"ai_assistant_personality_id\"]");
    }

    if (parameters.containsKey("ftp_permission") && !(parameters.get("ftp_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: ftp_permission must be of type Boolean parameters[\"ftp_permission\"]");
    }

    if (parameters.containsKey("sftp_permission") && !(parameters.get("sftp_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: sftp_permission must be of type Boolean parameters[\"sftp_permission\"]");
    }

    if (parameters.containsKey("dav_permission") && !(parameters.get("dav_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: dav_permission must be of type Boolean parameters[\"dav_permission\"]");
    }

    if (parameters.containsKey("restapi_permission") && !(parameters.get("restapi_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: restapi_permission must be of type Boolean parameters[\"restapi_permission\"]");
    }

    if (parameters.containsKey("s3_compatible_endpoint_permission") && !(parameters.get("s3_compatible_endpoint_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: s3_compatible_endpoint_permission must be of type Boolean parameters[\"s3_compatible_endpoint_permission\"]");
    }

    if (parameters.containsKey("desktop_configuration_profile_id") && !(parameters.get("desktop_configuration_profile_id") instanceof Long || parameters.get("desktop_configuration_profile_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: desktop_configuration_profile_id must be of type Long or Integer parameters[\"desktop_configuration_profile_id\"]");
    }

    if (parameters.containsKey("integration_centric_profile_id") && !(parameters.get("integration_centric_profile_id") instanceof Long || parameters.get("integration_centric_profile_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: integration_centric_profile_id must be of type Long or Integer parameters[\"integration_centric_profile_id\"]");
    }

    if (parameters.containsKey("allowed_ips") && !(parameters.get("allowed_ips") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: allowed_ips must be of type String parameters[\"allowed_ips\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    String apiPath = "/groups";
    TypeReference<Group> typeReference = new TypeReference<Group>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Update Group
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public Group update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Group
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public Group update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Group
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public Group update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Group
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code notes} (String): Group notes.</li>
   * <li>{@code user_ids} (String): A list of user ids. If sent as a string, should be comma-delimited.</li>
   * <li>{@code admin_ids} (String): A list of group admin user ids. If sent as a string, should be comma-delimited.</li>
   * <li>{@code ai_assistant_personality_id} (Long): AI Assistant Personality ID assigned to this Group, if any. Users in the Group inherit it unless a direct per-user or Partner assignment overrides it.</li>
   * <li>{@code ftp_permission} (Boolean): If true, users in this group can use FTP to login.  This will override a false value of `ftp_permission` on the user level.</li>
   * <li>{@code sftp_permission} (Boolean): If true, users in this group can use SFTP to login.  This will override a false value of `sftp_permission` on the user level.</li>
   * <li>{@code dav_permission} (Boolean): If true, users in this group can use WebDAV to login.  This will override a false value of `dav_permission` on the user level.</li>
   * <li>{@code restapi_permission} (Boolean): If true, users in this group can use the REST API to login.  This will override a false value of `restapi_permission` on the user level.</li>
   * <li>{@code s3_compatible_endpoint_permission} (Boolean): If true, users in this group can access the S3-compatible endpoint. This will override a false value of `s3_compatible_endpoint_permission` on the user level. Defaults to false.</li>
   * <li>{@code desktop_configuration_profile_id} (Long): Desktop Configuration Profile ID assigned to this Group, if any. Users in the Group inherit it unless a direct per-user assignment overrides it.</li>
   * <li>{@code integration_centric_profile_id} (Long): Integration Centric Profile ID assigned to this Group, if any. Users in the Group inherit it unless a direct per-user assignment overrides it.</li>
   * <li>{@code allowed_ips} (String): A list of allowed IPs if applicable.  Newline delimited</li>
   * <li>{@code name} (String): Group name.</li>
   * </ul>
   *
   * @param id Group ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Group}
   */
  public Group update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("user_ids") && !(parameters.get("user_ids") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: user_ids must be of type String parameters[\"user_ids\"]");
    }

    if (parameters.containsKey("admin_ids") && !(parameters.get("admin_ids") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: admin_ids must be of type String parameters[\"admin_ids\"]");
    }

    if (parameters.containsKey("ai_assistant_personality_id") && !(parameters.get("ai_assistant_personality_id") instanceof Long || parameters.get("ai_assistant_personality_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: ai_assistant_personality_id must be of type Long or Integer parameters[\"ai_assistant_personality_id\"]");
    }

    if (parameters.containsKey("ftp_permission") && !(parameters.get("ftp_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: ftp_permission must be of type Boolean parameters[\"ftp_permission\"]");
    }

    if (parameters.containsKey("sftp_permission") && !(parameters.get("sftp_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: sftp_permission must be of type Boolean parameters[\"sftp_permission\"]");
    }

    if (parameters.containsKey("dav_permission") && !(parameters.get("dav_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: dav_permission must be of type Boolean parameters[\"dav_permission\"]");
    }

    if (parameters.containsKey("restapi_permission") && !(parameters.get("restapi_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: restapi_permission must be of type Boolean parameters[\"restapi_permission\"]");
    }

    if (parameters.containsKey("s3_compatible_endpoint_permission") && !(parameters.get("s3_compatible_endpoint_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: s3_compatible_endpoint_permission must be of type Boolean parameters[\"s3_compatible_endpoint_permission\"]");
    }

    if (parameters.containsKey("desktop_configuration_profile_id") && !(parameters.get("desktop_configuration_profile_id") instanceof Long || parameters.get("desktop_configuration_profile_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: desktop_configuration_profile_id must be of type Long or Integer parameters[\"desktop_configuration_profile_id\"]");
    }

    if (parameters.containsKey("integration_centric_profile_id") && !(parameters.get("integration_centric_profile_id") instanceof Long || parameters.get("integration_centric_profile_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: integration_centric_profile_id must be of type Long or Integer parameters[\"integration_centric_profile_id\"]");
    }

    if (parameters.containsKey("allowed_ips") && !(parameters.get("allowed_ips") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: allowed_ips must be of type String parameters[\"allowed_ips\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    String apiPath = String.format("/groups/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<Group> typeReference = new TypeReference<Group>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete Group
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Group
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Group
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Group
   *
   * @param id Group ID.
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

    String apiPath = String.format("/groups/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete Group
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Group
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
