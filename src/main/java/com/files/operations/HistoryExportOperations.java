package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ResourceOperations;
import com.files.models.HistoryExport;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;

/**
 * HistoryExport operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#historyExports()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.HistoryExport} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class HistoryExportOperations extends ResourceOperations {
  public HistoryExportOperations(FilesClient client) {
    super(client);
  }

  /**
   * Show History Export
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public HistoryExport find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show History Export
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public HistoryExport find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show History Export
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public HistoryExport find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show History Export
   *
   * @param id History Export ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code HistoryExport}
   */
  public HistoryExport find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/history_exports/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<HistoryExport> typeReference = new TypeReference<HistoryExport>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show History Export
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public HistoryExport get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show History Export
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public HistoryExport get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create History Export
   *
   * @see #create(HashMap, HashMap)
   */
  public HistoryExport create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create History Export
   *
   * @see #create(HashMap, HashMap)
   */
  public HistoryExport create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create History Export
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code start_at} (String): Start date/time of export range.</li>
   * <li>{@code end_at} (String): End date/time of export range.</li>
   * <li>{@code query_action} (String): Filter results by this this action type. Valid values: `create`, `read`, `update`, `destroy`, `move`, `login`, `failedlogin`, `copy`, `user_create`, `user_update`, `user_destroy`, `group_create`, `group_update`, `group_destroy`, `permission_create`, `permission_destroy`, `api_key_create`, `api_key_update`, `api_key_destroy`, `archived_delete`</li>
   * <li>{@code query_interface} (String): Filter results by this this interface type. Valid values: `web`, `ftp`, `robot`, `jsapi`, `webdesktopapi`, `sftp`, `dav`, `desktop`, `restapi`, `scim`, `office`, `mobile`, `as2`, `inbound_email`, `remote`, `inbound_s3`</li>
   * <li>{@code query_user_id} (String): Return results that are actions performed by the user indicated by this User ID</li>
   * <li>{@code query_file_id} (String): Return results that are file actions related to the file indicated by this File ID</li>
   * <li>{@code query_parent_id} (String): Return results that are file actions inside the parent folder specified by this folder ID</li>
   * <li>{@code query_path} (String): Return results that are file actions related to paths matching this pattern.</li>
   * <li>{@code query_folder} (String): Return results that are file actions related to files or folders inside folder paths matching this pattern.</li>
   * <li>{@code query_src} (String): Return results that are file moves originating from paths matching this pattern.</li>
   * <li>{@code query_destination} (String): Return results that are file moves with paths matching this pattern as destination.</li>
   * <li>{@code query_ip} (String): Filter results by this IP address.</li>
   * <li>{@code query_username} (String): Filter results by this username.</li>
   * <li>{@code query_failure_type} (String): If searching for Histories about login failures, this parameter restricts results to failures of this specific type.  Valid values: `expired_trial`, `account_overdue`, `locked_out`, `ip_mismatch`, `password_mismatch`, `site_mismatch`, `username_not_found`, `none`, `no_ftp_permission`, `no_web_permission`, `no_directory`, `errno_enoent`, `no_sftp_permission`, `no_dav_permission`, `no_restapi_permission`, `key_mismatch`, `region_mismatch`, `expired_access`, `desktop_ip_mismatch`, `desktop_api_key_not_used_quickly_enough`, `disabled`, `country_mismatch`, `insecure_ftp`, `insecure_cipher`, `rate_limited`, `no_s3_compatible_endpoint_permission`, `mobile_ip_mismatch`, `mobile_api_key_not_used_quickly_enough`, `desktop_app_disabled`, `mobile_app_disabled`</li>
   * <li>{@code query_target_id} (String): If searching for Histories about specific objects (such as Users, or API Keys), this parameter restricts results to objects that match this ID.</li>
   * <li>{@code query_target_name} (String): If searching for Histories about Users, Groups or other objects with names, this parameter restricts results to objects with this name/username.</li>
   * <li>{@code query_target_permission} (String): If searching for Histories about Permissions, this parameter restricts results to permissions of this level.</li>
   * <li>{@code query_target_user_id} (String): If searching for Histories about API keys, this parameter restricts results to API keys created by/for this user ID.</li>
   * <li>{@code query_target_username} (String): If searching for Histories about API keys, this parameter restricts results to API keys created by/for this username.</li>
   * <li>{@code query_target_platform} (String): If searching for Histories about API keys, this parameter restricts results to API keys associated with this platform.</li>
   * <li>{@code query_target_permission_set} (String): If searching for Histories about API keys, this parameter restricts results to API keys with this permission set.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code HistoryExport}
   */
  public HistoryExport create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }

    if (parameters.containsKey("start_at") && !(parameters.get("start_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: start_at must be of type String parameters[\"start_at\"]");
    }

    if (parameters.containsKey("end_at") && !(parameters.get("end_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: end_at must be of type String parameters[\"end_at\"]");
    }

    if (parameters.containsKey("query_action") && !(parameters.get("query_action") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: query_action must be of type String parameters[\"query_action\"]");
    }

    if (parameters.containsKey("query_interface") && !(parameters.get("query_interface") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: query_interface must be of type String parameters[\"query_interface\"]");
    }

    if (parameters.containsKey("query_user_id") && !(parameters.get("query_user_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: query_user_id must be of type String parameters[\"query_user_id\"]");
    }

    if (parameters.containsKey("query_file_id") && !(parameters.get("query_file_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: query_file_id must be of type String parameters[\"query_file_id\"]");
    }

    if (parameters.containsKey("query_parent_id") && !(parameters.get("query_parent_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: query_parent_id must be of type String parameters[\"query_parent_id\"]");
    }

    if (parameters.containsKey("query_path") && !(parameters.get("query_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: query_path must be of type String parameters[\"query_path\"]");
    }

    if (parameters.containsKey("query_folder") && !(parameters.get("query_folder") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: query_folder must be of type String parameters[\"query_folder\"]");
    }

    if (parameters.containsKey("query_src") && !(parameters.get("query_src") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: query_src must be of type String parameters[\"query_src\"]");
    }

    if (parameters.containsKey("query_destination") && !(parameters.get("query_destination") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: query_destination must be of type String parameters[\"query_destination\"]");
    }

    if (parameters.containsKey("query_ip") && !(parameters.get("query_ip") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: query_ip must be of type String parameters[\"query_ip\"]");
    }

    if (parameters.containsKey("query_username") && !(parameters.get("query_username") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: query_username must be of type String parameters[\"query_username\"]");
    }

    if (parameters.containsKey("query_failure_type") && !(parameters.get("query_failure_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: query_failure_type must be of type String parameters[\"query_failure_type\"]");
    }

    if (parameters.containsKey("query_target_id") && !(parameters.get("query_target_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: query_target_id must be of type String parameters[\"query_target_id\"]");
    }

    if (parameters.containsKey("query_target_name") && !(parameters.get("query_target_name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: query_target_name must be of type String parameters[\"query_target_name\"]");
    }

    if (parameters.containsKey("query_target_permission") && !(parameters.get("query_target_permission") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: query_target_permission must be of type String parameters[\"query_target_permission\"]");
    }

    if (parameters.containsKey("query_target_user_id") && !(parameters.get("query_target_user_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: query_target_user_id must be of type String parameters[\"query_target_user_id\"]");
    }

    if (parameters.containsKey("query_target_username") && !(parameters.get("query_target_username") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: query_target_username must be of type String parameters[\"query_target_username\"]");
    }

    if (parameters.containsKey("query_target_platform") && !(parameters.get("query_target_platform") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: query_target_platform must be of type String parameters[\"query_target_platform\"]");
    }

    if (parameters.containsKey("query_target_permission_set") && !(parameters.get("query_target_permission_set") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: query_target_permission_set must be of type String parameters[\"query_target_permission_set\"]");
    }

    String apiPath = "/history_exports";
    TypeReference<HistoryExport> typeReference = new TypeReference<HistoryExport>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }
}
