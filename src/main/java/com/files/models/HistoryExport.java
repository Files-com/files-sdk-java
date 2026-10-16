package com.files.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.ListIterator;
import com.files.net.ClientRuntime;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
import com.files.util.PathUtils;
import com.files.util.UrlUtils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HistoryExport implements ModelInterface {
  private HashMap<String, Object> options;
  private FilesClient filesClient;

  /**
   * Sets the request options, such as {@code api_key} or {@code workspace_id}, that this object's
   * own operations send. The map is copied; options that control a single call, such as a
   * {@code cancellation} token or a {@code timeout}, are not kept.
   */
  public void setOptions(HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
  }

  @JsonIgnore
  @Override
  public void setClient(FilesClient client) {
    this.filesClient = client;
  }

  // The client this object's own operations run with: the one that returned it, or one set with
  // setClient, and otherwise the default client as it is when the operation runs.
  private FilesClient filesClient() {
    return filesClient != null ? filesClient : FilesClient.defaultClient();
  }

  // One mapper for every HistoryExport, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  public HistoryExport() {
    this(null, null);
  }

  public HistoryExport(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public HistoryExport(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Workspace of the export. 0 represents the default workspace. A null value means a site-wide export.
   */
  @JsonProperty("workspace_id")
  public Long workspaceId;

  public Long getWorkspaceId() {
    return workspaceId;
  }

  public void setWorkspaceId(Long workspaceId) {
    this.workspaceId = workspaceId;
  }

  /**
   * History Export ID
   */
  @JsonProperty("id")
  public Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Version of the history for the export.
   */
  @JsonProperty("history_version")
  public String historyVersion;

  public String getHistoryVersion() {
    return historyVersion;
  }

  public void setHistoryVersion(String historyVersion) {
    this.historyVersion = historyVersion;
  }

  /**
   * Start date/time of export range.
   */
  @JsonProperty("start_at")
  public Date startAt;

  public Date getStartAt() {
    return startAt;
  }

  public void setStartAt(Date startAt) {
    this.startAt = startAt;
  }

  /**
   * End date/time of export range.
   */
  @JsonProperty("end_at")
  public Date endAt;

  public Date getEndAt() {
    return endAt;
  }

  public void setEndAt(Date endAt) {
    this.endAt = endAt;
  }

  /**
   * Status of export.  Will be: `building`, `ready`, or `failed`
   */
  @JsonProperty("status")
  public String status;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * Filter results by this this action type. Valid values: `create`, `read`, `update`, `destroy`, `move`, `login`, `failedlogin`, `copy`, `user_create`, `user_update`, `user_destroy`, `group_create`, `group_update`, `group_destroy`, `permission_create`, `permission_destroy`, `api_key_create`, `api_key_update`, `api_key_destroy`, `archived_delete`
   */
  @JsonProperty("query_action")
  public String queryAction;

  public String getQueryAction() {
    return queryAction;
  }

  public void setQueryAction(String queryAction) {
    this.queryAction = queryAction;
  }

  /**
   * Filter results by this this interface type. Valid values: `web`, `ftp`, `robot`, `jsapi`, `webdesktopapi`, `sftp`, `dav`, `desktop`, `restapi`, `scim`, `office`, `mobile`, `as2`, `inbound_email`, `remote`, `inbound_s3`
   */
  @JsonProperty("query_interface")
  public String queryInterface;

  public String getQueryInterface() {
    return queryInterface;
  }

  public void setQueryInterface(String queryInterface) {
    this.queryInterface = queryInterface;
  }

  /**
   * Return results that are actions performed by the user indicated by this User ID
   */
  @JsonProperty("query_user_id")
  public String queryUserId;

  public String getQueryUserId() {
    return queryUserId;
  }

  public void setQueryUserId(String queryUserId) {
    this.queryUserId = queryUserId;
  }

  /**
   * Return results that are file actions related to the file indicated by this File ID
   */
  @JsonProperty("query_file_id")
  public String queryFileId;

  public String getQueryFileId() {
    return queryFileId;
  }

  public void setQueryFileId(String queryFileId) {
    this.queryFileId = queryFileId;
  }

  /**
   * Return results that are file actions inside the parent folder specified by this folder ID
   */
  @JsonProperty("query_parent_id")
  public String queryParentId;

  public String getQueryParentId() {
    return queryParentId;
  }

  public void setQueryParentId(String queryParentId) {
    this.queryParentId = queryParentId;
  }

  /**
   * Return results that are file actions related to paths matching this pattern.
   */
  @JsonProperty("query_path")
  public String queryPath;

  public String getQueryPath() {
    return queryPath;
  }

  public void setQueryPath(String queryPath) {
    this.queryPath = queryPath;
  }

  /**
   * Return results that are file actions related to files or folders inside folder paths matching this pattern.
   */
  @JsonProperty("query_folder")
  public String queryFolder;

  public String getQueryFolder() {
    return queryFolder;
  }

  public void setQueryFolder(String queryFolder) {
    this.queryFolder = queryFolder;
  }

  /**
   * Return results that are file moves originating from paths matching this pattern.
   */
  @JsonProperty("query_src")
  public String querySrc;

  public String getQuerySrc() {
    return querySrc;
  }

  public void setQuerySrc(String querySrc) {
    this.querySrc = querySrc;
  }

  /**
   * Return results that are file moves with paths matching this pattern as destination.
   */
  @JsonProperty("query_destination")
  public String queryDestination;

  public String getQueryDestination() {
    return queryDestination;
  }

  public void setQueryDestination(String queryDestination) {
    this.queryDestination = queryDestination;
  }

  /**
   * Filter results by this IP address.
   */
  @JsonProperty("query_ip")
  public String queryIp;

  public String getQueryIp() {
    return queryIp;
  }

  public void setQueryIp(String queryIp) {
    this.queryIp = queryIp;
  }

  /**
   * Filter results by this username.
   */
  @JsonProperty("query_username")
  public String queryUsername;

  public String getQueryUsername() {
    return queryUsername;
  }

  public void setQueryUsername(String queryUsername) {
    this.queryUsername = queryUsername;
  }

  /**
   * If searching for Histories about login failures, this parameter restricts results to failures of this specific type.  Valid values: `expired_trial`, `account_overdue`, `locked_out`, `ip_mismatch`, `password_mismatch`, `site_mismatch`, `username_not_found`, `none`, `no_ftp_permission`, `no_web_permission`, `no_directory`, `errno_enoent`, `no_sftp_permission`, `no_dav_permission`, `no_restapi_permission`, `key_mismatch`, `region_mismatch`, `expired_access`, `desktop_ip_mismatch`, `desktop_api_key_not_used_quickly_enough`, `disabled`, `country_mismatch`, `insecure_ftp`, `insecure_cipher`, `rate_limited`, `no_s3_compatible_endpoint_permission`, `mobile_ip_mismatch`, `mobile_api_key_not_used_quickly_enough`, `desktop_app_disabled`, `mobile_app_disabled`
   */
  @JsonProperty("query_failure_type")
  public String queryFailureType;

  public String getQueryFailureType() {
    return queryFailureType;
  }

  public void setQueryFailureType(String queryFailureType) {
    this.queryFailureType = queryFailureType;
  }

  /**
   * If searching for Histories about specific objects (such as Users, or API Keys), this parameter restricts results to objects that match this ID.
   */
  @JsonProperty("query_target_id")
  public String queryTargetId;

  public String getQueryTargetId() {
    return queryTargetId;
  }

  public void setQueryTargetId(String queryTargetId) {
    this.queryTargetId = queryTargetId;
  }

  /**
   * If searching for Histories about Users, Groups or other objects with names, this parameter restricts results to objects with this name/username.
   */
  @JsonProperty("query_target_name")
  public String queryTargetName;

  public String getQueryTargetName() {
    return queryTargetName;
  }

  public void setQueryTargetName(String queryTargetName) {
    this.queryTargetName = queryTargetName;
  }

  /**
   * If searching for Histories about Permissions, this parameter restricts results to permissions of this level.
   */
  @JsonProperty("query_target_permission")
  public String queryTargetPermission;

  public String getQueryTargetPermission() {
    return queryTargetPermission;
  }

  public void setQueryTargetPermission(String queryTargetPermission) {
    this.queryTargetPermission = queryTargetPermission;
  }

  /**
   * If searching for Histories about API keys, this parameter restricts results to API keys created by/for this user ID.
   */
  @JsonProperty("query_target_user_id")
  public String queryTargetUserId;

  public String getQueryTargetUserId() {
    return queryTargetUserId;
  }

  public void setQueryTargetUserId(String queryTargetUserId) {
    this.queryTargetUserId = queryTargetUserId;
  }

  /**
   * If searching for Histories about API keys, this parameter restricts results to API keys created by/for this username.
   */
  @JsonProperty("query_target_username")
  public String queryTargetUsername;

  public String getQueryTargetUsername() {
    return queryTargetUsername;
  }

  public void setQueryTargetUsername(String queryTargetUsername) {
    this.queryTargetUsername = queryTargetUsername;
  }

  /**
   * If searching for Histories about API keys, this parameter restricts results to API keys associated with this platform.
   */
  @JsonProperty("query_target_platform")
  public String queryTargetPlatform;

  public String getQueryTargetPlatform() {
    return queryTargetPlatform;
  }

  public void setQueryTargetPlatform(String queryTargetPlatform) {
    this.queryTargetPlatform = queryTargetPlatform;
  }

  /**
   * If searching for Histories about API keys, this parameter restricts results to API keys with this permission set.
   */
  @JsonProperty("query_target_permission_set")
  public String queryTargetPermissionSet;

  public String getQueryTargetPermissionSet() {
    return queryTargetPermissionSet;
  }

  public void setQueryTargetPermissionSet(String queryTargetPermissionSet) {
    this.queryTargetPermissionSet = queryTargetPermissionSet;
  }

  /**
   * If `status` is `ready`, this will be a URL where all the results can be downloaded at once as a CSV.
   */
  @JsonProperty("results_url")
  public String resultsUrl;

  public String getResultsUrl() {
    return resultsUrl;
  }

  public void setResultsUrl(String resultsUrl) {
    this.resultsUrl = resultsUrl;
  }

  /**
   * User ID.  Provide a value of `0` to operate the current session's user.
   */
  @JsonProperty("user_id")
  public Long userId;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }


  /**
   * Creates this HistoryExport.
   * On success, this object's attributes are replaced by the server's response.
   * HistoryExports cannot be updated, so saving one that already has an id throws
   * {@link UnsupportedOperationException}.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      throw new UnsupportedOperationException("The HistoryExport Object doesn't support updates.");
    }
    refreshFrom(filesClient().historyExports().create(parameters, this.options));
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    return ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(HistoryExport canonical) {
    this.workspaceId = canonical.workspaceId;
    this.id = canonical.id;
    this.historyVersion = canonical.historyVersion;
    this.startAt = canonical.startAt;
    this.endAt = canonical.endAt;
    this.status = canonical.status;
    this.queryAction = canonical.queryAction;
    this.queryInterface = canonical.queryInterface;
    this.queryUserId = canonical.queryUserId;
    this.queryFileId = canonical.queryFileId;
    this.queryParentId = canonical.queryParentId;
    this.queryPath = canonical.queryPath;
    this.queryFolder = canonical.queryFolder;
    this.querySrc = canonical.querySrc;
    this.queryDestination = canonical.queryDestination;
    this.queryIp = canonical.queryIp;
    this.queryUsername = canonical.queryUsername;
    this.queryFailureType = canonical.queryFailureType;
    this.queryTargetId = canonical.queryTargetId;
    this.queryTargetName = canonical.queryTargetName;
    this.queryTargetPermission = canonical.queryTargetPermission;
    this.queryTargetUserId = canonical.queryTargetUserId;
    this.queryTargetUsername = canonical.queryTargetUsername;
    this.queryTargetPlatform = canonical.queryTargetPlatform;
    this.queryTargetPermissionSet = canonical.queryTargetPermissionSet;
    this.resultsUrl = canonical.resultsUrl;
    this.userId = canonical.userId;
  }

  /**
   * Parameters:
   *   id (required) - int64 - History Export ID.
   */
  public static HistoryExport find() throws RuntimeException {
    return find(null, null, null);
  }

  public static HistoryExport find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static HistoryExport find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static HistoryExport find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().historyExports().find(id, parameters, options);
  }

  public static HistoryExport get() throws RuntimeException {
    return get(null, null, null);
  }

  public static HistoryExport get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
   *   start_at - string - Start date/time of export range.
   *   end_at - string - End date/time of export range.
   *   query_action - string - Filter results by this this action type. Valid values: `create`, `read`, `update`, `destroy`, `move`, `login`, `failedlogin`, `copy`, `user_create`, `user_update`, `user_destroy`, `group_create`, `group_update`, `group_destroy`, `permission_create`, `permission_destroy`, `api_key_create`, `api_key_update`, `api_key_destroy`, `archived_delete`
   *   query_interface - string - Filter results by this this interface type. Valid values: `web`, `ftp`, `robot`, `jsapi`, `webdesktopapi`, `sftp`, `dav`, `desktop`, `restapi`, `scim`, `office`, `mobile`, `as2`, `inbound_email`, `remote`, `inbound_s3`
   *   query_user_id - string - Return results that are actions performed by the user indicated by this User ID
   *   query_file_id - string - Return results that are file actions related to the file indicated by this File ID
   *   query_parent_id - string - Return results that are file actions inside the parent folder specified by this folder ID
   *   query_path - string - Return results that are file actions related to paths matching this pattern.
   *   query_folder - string - Return results that are file actions related to files or folders inside folder paths matching this pattern.
   *   query_src - string - Return results that are file moves originating from paths matching this pattern.
   *   query_destination - string - Return results that are file moves with paths matching this pattern as destination.
   *   query_ip - string - Filter results by this IP address.
   *   query_username - string - Filter results by this username.
   *   query_failure_type - string - If searching for Histories about login failures, this parameter restricts results to failures of this specific type.  Valid values: `expired_trial`, `account_overdue`, `locked_out`, `ip_mismatch`, `password_mismatch`, `site_mismatch`, `username_not_found`, `none`, `no_ftp_permission`, `no_web_permission`, `no_directory`, `errno_enoent`, `no_sftp_permission`, `no_dav_permission`, `no_restapi_permission`, `key_mismatch`, `region_mismatch`, `expired_access`, `desktop_ip_mismatch`, `desktop_api_key_not_used_quickly_enough`, `disabled`, `country_mismatch`, `insecure_ftp`, `insecure_cipher`, `rate_limited`, `no_s3_compatible_endpoint_permission`, `mobile_ip_mismatch`, `mobile_api_key_not_used_quickly_enough`, `desktop_app_disabled`, `mobile_app_disabled`
   *   query_target_id - string - If searching for Histories about specific objects (such as Users, or API Keys), this parameter restricts results to objects that match this ID.
   *   query_target_name - string - If searching for Histories about Users, Groups or other objects with names, this parameter restricts results to objects with this name/username.
   *   query_target_permission - string - If searching for Histories about Permissions, this parameter restricts results to permissions of this level.
   *   query_target_user_id - string - If searching for Histories about API keys, this parameter restricts results to API keys created by/for this user ID.
   *   query_target_username - string - If searching for Histories about API keys, this parameter restricts results to API keys created by/for this username.
   *   query_target_platform - string - If searching for Histories about API keys, this parameter restricts results to API keys associated with this platform.
   *   query_target_permission_set - string - If searching for Histories about API keys, this parameter restricts results to API keys with this permission set.
   */
  public static HistoryExport create() throws RuntimeException {
    return create(null, null);
  }

  public static HistoryExport create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static HistoryExport create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().historyExports().create(parameters, options);
  }


}
