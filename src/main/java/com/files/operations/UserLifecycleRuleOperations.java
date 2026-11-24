package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.UserLifecycleRule;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * UserLifecycleRule operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#userLifecycleRules()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.UserLifecycleRule} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class UserLifecycleRuleOperations extends ResourceOperations {
  public UserLifecycleRuleOperations(FilesClient client) {
    super(client);
  }

  /**
   * List User Lifecycle Rules
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<UserLifecycleRule> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List User Lifecycle Rules
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<UserLifecycleRule> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List User Lifecycle Rules
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `site_id` and `workspace_id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code UserLifecycleRule} records, in a {@code ListIterator}
   */
  public ListIterator<UserLifecycleRule> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = "/user_lifecycle_rules";
    TypeReference<List<UserLifecycleRule>> typeReference = new TypeReference<List<UserLifecycleRule>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List User Lifecycle Rules
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<UserLifecycleRule> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List User Lifecycle Rules
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<UserLifecycleRule> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show User Lifecycle Rule
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public UserLifecycleRule find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show User Lifecycle Rule
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public UserLifecycleRule find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show User Lifecycle Rule
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public UserLifecycleRule find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show User Lifecycle Rule
   *
   * @param id User Lifecycle Rule ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code UserLifecycleRule}
   */
  public UserLifecycleRule find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/user_lifecycle_rules/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<UserLifecycleRule> typeReference = new TypeReference<UserLifecycleRule>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show User Lifecycle Rule
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public UserLifecycleRule get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show User Lifecycle Rule
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public UserLifecycleRule get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create User Lifecycle Rule
   *
   * @see #create(HashMap, HashMap)
   */
  public UserLifecycleRule create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create User Lifecycle Rule
   *
   * @see #create(HashMap, HashMap)
   */
  public UserLifecycleRule create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create User Lifecycle Rule
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code action} (String): Action to take on inactive users (disable or delete)</li>
   * <li>{@code apply_to_all_workspaces} (Boolean): If true, a Default Workspace rule also applies to users in all Custom Workspaces. Can only be enabled when `workspace_id` is `0`.</li>
   * <li>{@code authentication_method} (String): User authentication method for which the rule will apply. Use `all_non_sso` to target every non-SSO authentication method with one rule.</li>
   * <li>{@code group_ids} (Long[]): Array of Group IDs to which the rule applies. If empty or not set, the rule applies to all users.</li>
   * <li>{@code inactivity_days} (Long): Number of days of inactivity before the rule applies</li>
   * <li>{@code include_site_admins} (Boolean): If true, the rule includes Site Administrators, who always belong to the Default Workspace. Can only be enabled when `workspace_id` is `0`.</li>
   * <li>{@code include_folder_admins} (Boolean): If true, the rule will apply to folder admins.</li>
   * <li>{@code name} (String): User Lifecycle Rule name</li>
   * <li>{@code notify_users} (Boolean): If true, users will be emailed before the rule disables or deletes them.</li>
   * <li>{@code partner_tag} (String): If provided, only users belonging to Partners with this tag at the Partner level will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.</li>
   * <li>{@code user_state} (String): State of the users to apply the rule to (inactive or disabled)</li>
   * <li>{@code user_tag} (String): If provided, only users with this tag will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.</li>
   * <li>{@code workspace_id} (Long): Workspace whose users the rule applies to. `0` means the Default Workspace. A Custom Workspace rule applies only to users who belong to that Workspace, regardless of access granted to other users.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code UserLifecycleRule}
   */
  public UserLifecycleRule create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("action") && !(parameters.get("action") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: action must be of type String parameters[\"action\"]");
    }

    if (parameters.containsKey("apply_to_all_workspaces") && !(parameters.get("apply_to_all_workspaces") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: apply_to_all_workspaces must be of type Boolean parameters[\"apply_to_all_workspaces\"]");
    }

    if (parameters.containsKey("authentication_method") && !(parameters.get("authentication_method") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: authentication_method must be of type String parameters[\"authentication_method\"]");
    }

    if (parameters.containsKey("group_ids") && !(parameters.get("group_ids") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: group_ids must be of type Long[] parameters[\"group_ids\"]");
    }

    if (parameters.containsKey("inactivity_days") && !(parameters.get("inactivity_days") instanceof Long || parameters.get("inactivity_days") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: inactivity_days must be of type Long or Integer parameters[\"inactivity_days\"]");
    }

    if (parameters.containsKey("include_site_admins") && !(parameters.get("include_site_admins") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: include_site_admins must be of type Boolean parameters[\"include_site_admins\"]");
    }

    if (parameters.containsKey("include_folder_admins") && !(parameters.get("include_folder_admins") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: include_folder_admins must be of type Boolean parameters[\"include_folder_admins\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("notify_users") && !(parameters.get("notify_users") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_users must be of type Boolean parameters[\"notify_users\"]");
    }

    if (parameters.containsKey("partner_tag") && !(parameters.get("partner_tag") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: partner_tag must be of type String parameters[\"partner_tag\"]");
    }

    if (parameters.containsKey("user_state") && !(parameters.get("user_state") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: user_state must be of type String parameters[\"user_state\"]");
    }

    if (parameters.containsKey("user_tag") && !(parameters.get("user_tag") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: user_tag must be of type String parameters[\"user_tag\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    String apiPath = "/user_lifecycle_rules";
    TypeReference<UserLifecycleRule> typeReference = new TypeReference<UserLifecycleRule>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Update User Lifecycle Rule
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public UserLifecycleRule update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update User Lifecycle Rule
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public UserLifecycleRule update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update User Lifecycle Rule
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public UserLifecycleRule update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update User Lifecycle Rule
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code action} (String): Action to take on inactive users (disable or delete)</li>
   * <li>{@code apply_to_all_workspaces} (Boolean): If true, a Default Workspace rule also applies to users in all Custom Workspaces. Can only be enabled when `workspace_id` is `0`.</li>
   * <li>{@code authentication_method} (String): User authentication method for which the rule will apply. Use `all_non_sso` to target every non-SSO authentication method with one rule.</li>
   * <li>{@code group_ids} (Long[]): Array of Group IDs to which the rule applies. If empty or not set, the rule applies to all users.</li>
   * <li>{@code inactivity_days} (Long): Number of days of inactivity before the rule applies</li>
   * <li>{@code include_site_admins} (Boolean): If true, the rule includes Site Administrators, who always belong to the Default Workspace. Can only be enabled when `workspace_id` is `0`.</li>
   * <li>{@code include_folder_admins} (Boolean): If true, the rule will apply to folder admins.</li>
   * <li>{@code name} (String): User Lifecycle Rule name</li>
   * <li>{@code notify_users} (Boolean): If true, users will be emailed before the rule disables or deletes them.</li>
   * <li>{@code partner_tag} (String): If provided, only users belonging to Partners with this tag at the Partner level will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.</li>
   * <li>{@code user_state} (String): State of the users to apply the rule to (inactive or disabled)</li>
   * <li>{@code user_tag} (String): If provided, only users with this tag will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.</li>
   * <li>{@code workspace_id} (Long): Workspace whose users the rule applies to. `0` means the Default Workspace. A Custom Workspace rule applies only to users who belong to that Workspace, regardless of access granted to other users.</li>
   * </ul>
   *
   * @param id User Lifecycle Rule ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code UserLifecycleRule}
   */
  public UserLifecycleRule update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("action") && !(parameters.get("action") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: action must be of type String parameters[\"action\"]");
    }

    if (parameters.containsKey("apply_to_all_workspaces") && !(parameters.get("apply_to_all_workspaces") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: apply_to_all_workspaces must be of type Boolean parameters[\"apply_to_all_workspaces\"]");
    }

    if (parameters.containsKey("authentication_method") && !(parameters.get("authentication_method") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: authentication_method must be of type String parameters[\"authentication_method\"]");
    }

    if (parameters.containsKey("group_ids") && !(parameters.get("group_ids") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: group_ids must be of type Long[] parameters[\"group_ids\"]");
    }

    if (parameters.containsKey("inactivity_days") && !(parameters.get("inactivity_days") instanceof Long || parameters.get("inactivity_days") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: inactivity_days must be of type Long or Integer parameters[\"inactivity_days\"]");
    }

    if (parameters.containsKey("include_site_admins") && !(parameters.get("include_site_admins") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: include_site_admins must be of type Boolean parameters[\"include_site_admins\"]");
    }

    if (parameters.containsKey("include_folder_admins") && !(parameters.get("include_folder_admins") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: include_folder_admins must be of type Boolean parameters[\"include_folder_admins\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("notify_users") && !(parameters.get("notify_users") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_users must be of type Boolean parameters[\"notify_users\"]");
    }

    if (parameters.containsKey("partner_tag") && !(parameters.get("partner_tag") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: partner_tag must be of type String parameters[\"partner_tag\"]");
    }

    if (parameters.containsKey("user_state") && !(parameters.get("user_state") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: user_state must be of type String parameters[\"user_state\"]");
    }

    if (parameters.containsKey("user_tag") && !(parameters.get("user_tag") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: user_tag must be of type String parameters[\"user_tag\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    String apiPath = String.format("/user_lifecycle_rules/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<UserLifecycleRule> typeReference = new TypeReference<UserLifecycleRule>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete User Lifecycle Rule
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete User Lifecycle Rule
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete User Lifecycle Rule
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete User Lifecycle Rule
   *
   * @param id User Lifecycle Rule ID.
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

    String apiPath = String.format("/user_lifecycle_rules/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete User Lifecycle Rule
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete User Lifecycle Rule
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
