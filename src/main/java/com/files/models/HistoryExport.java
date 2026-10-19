package com.files.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.files.FilesClient;
import com.files.net.ClientRuntime;
import com.files.util.ModelUtils;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

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

  /**
   * Sets the client this object's own operations run with.
   *
   * @param client the client
   */
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


  /**
   * Creates a {@code HistoryExport} with no attributes set.
   */
  public HistoryExport() {
    this(null, null);
  }

  /**
   * Creates a {@code HistoryExport} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public HistoryExport(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code HistoryExport} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
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

  /**
   * Gets {@link #workspaceId}.
   *
   * @return the value of {@link #workspaceId}
   */
  public Long getWorkspaceId() {
    return workspaceId;
  }

  /**
   * Sets {@link #workspaceId}.
   *
   * @param workspaceId the new value
   */
  public void setWorkspaceId(Long workspaceId) {
    this.workspaceId = workspaceId;
  }

  /**
   * History Export ID
   */
  @JsonProperty("id")
  public Long id;

  /**
   * Gets {@link #id}.
   *
   * @return the value of {@link #id}
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets {@link #id}.
   *
   * @param id the new value
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Version of the history for the export.
   */
  @JsonProperty("history_version")
  public String historyVersion;

  /**
   * Gets {@link #historyVersion}.
   *
   * @return the value of {@link #historyVersion}
   */
  public String getHistoryVersion() {
    return historyVersion;
  }

  /**
   * Sets {@link #historyVersion}.
   *
   * @param historyVersion the new value
   */
  public void setHistoryVersion(String historyVersion) {
    this.historyVersion = historyVersion;
  }

  /**
   * Start date/time of export range.
   */
  @JsonProperty("start_at")
  public Date startAt;

  /**
   * Gets {@link #startAt}.
   *
   * @return the value of {@link #startAt}
   */
  public Date getStartAt() {
    return startAt;
  }

  /**
   * Sets {@link #startAt}.
   *
   * @param startAt the new value
   */
  public void setStartAt(Date startAt) {
    this.startAt = startAt;
  }

  /**
   * End date/time of export range.
   */
  @JsonProperty("end_at")
  public Date endAt;

  /**
   * Gets {@link #endAt}.
   *
   * @return the value of {@link #endAt}
   */
  public Date getEndAt() {
    return endAt;
  }

  /**
   * Sets {@link #endAt}.
   *
   * @param endAt the new value
   */
  public void setEndAt(Date endAt) {
    this.endAt = endAt;
  }

  /**
   * Status of export.  Will be: `building`, `ready`, or `failed`
   */
  @JsonProperty("status")
  public String status;

  /**
   * Gets {@link #status}.
   *
   * @return the value of {@link #status}
   */
  public String getStatus() {
    return status;
  }

  /**
   * Sets {@link #status}.
   *
   * @param status the new value
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * Filter results by this this action type. Valid values: `create`, `read`, `update`, `destroy`, `move`, `login`, `failedlogin`, `copy`, `user_create`, `user_update`, `user_destroy`, `group_create`, `group_update`, `group_destroy`, `permission_create`, `permission_destroy`, `api_key_create`, `api_key_update`, `api_key_destroy`, `archived_delete`
   */
  @JsonProperty("query_action")
  public String queryAction;

  /**
   * Gets {@link #queryAction}.
   *
   * @return the value of {@link #queryAction}
   */
  public String getQueryAction() {
    return queryAction;
  }

  /**
   * Sets {@link #queryAction}.
   *
   * @param queryAction the new value
   */
  public void setQueryAction(String queryAction) {
    this.queryAction = queryAction;
  }

  /**
   * Filter results by this this interface type. Valid values: `web`, `ftp`, `robot`, `jsapi`, `webdesktopapi`, `sftp`, `dav`, `desktop`, `restapi`, `scim`, `office`, `mobile`, `as2`, `inbound_email`, `remote`, `inbound_s3`
   */
  @JsonProperty("query_interface")
  public String queryInterface;

  /**
   * Gets {@link #queryInterface}.
   *
   * @return the value of {@link #queryInterface}
   */
  public String getQueryInterface() {
    return queryInterface;
  }

  /**
   * Sets {@link #queryInterface}.
   *
   * @param queryInterface the new value
   */
  public void setQueryInterface(String queryInterface) {
    this.queryInterface = queryInterface;
  }

  /**
   * Return results that are actions performed by the user indicated by this User ID
   */
  @JsonProperty("query_user_id")
  public String queryUserId;

  /**
   * Gets {@link #queryUserId}.
   *
   * @return the value of {@link #queryUserId}
   */
  public String getQueryUserId() {
    return queryUserId;
  }

  /**
   * Sets {@link #queryUserId}.
   *
   * @param queryUserId the new value
   */
  public void setQueryUserId(String queryUserId) {
    this.queryUserId = queryUserId;
  }

  /**
   * Return results that are file actions related to the file indicated by this File ID
   */
  @JsonProperty("query_file_id")
  public String queryFileId;

  /**
   * Gets {@link #queryFileId}.
   *
   * @return the value of {@link #queryFileId}
   */
  public String getQueryFileId() {
    return queryFileId;
  }

  /**
   * Sets {@link #queryFileId}.
   *
   * @param queryFileId the new value
   */
  public void setQueryFileId(String queryFileId) {
    this.queryFileId = queryFileId;
  }

  /**
   * Return results that are file actions inside the parent folder specified by this folder ID
   */
  @JsonProperty("query_parent_id")
  public String queryParentId;

  /**
   * Gets {@link #queryParentId}.
   *
   * @return the value of {@link #queryParentId}
   */
  public String getQueryParentId() {
    return queryParentId;
  }

  /**
   * Sets {@link #queryParentId}.
   *
   * @param queryParentId the new value
   */
  public void setQueryParentId(String queryParentId) {
    this.queryParentId = queryParentId;
  }

  /**
   * Return results that are file actions related to paths matching this pattern.
   */
  @JsonProperty("query_path")
  public String queryPath;

  /**
   * Gets {@link #queryPath}.
   *
   * @return the value of {@link #queryPath}
   */
  public String getQueryPath() {
    return queryPath;
  }

  /**
   * Sets {@link #queryPath}.
   *
   * @param queryPath the new value
   */
  public void setQueryPath(String queryPath) {
    this.queryPath = queryPath;
  }

  /**
   * Return results that are file actions related to files or folders inside folder paths matching this pattern.
   */
  @JsonProperty("query_folder")
  public String queryFolder;

  /**
   * Gets {@link #queryFolder}.
   *
   * @return the value of {@link #queryFolder}
   */
  public String getQueryFolder() {
    return queryFolder;
  }

  /**
   * Sets {@link #queryFolder}.
   *
   * @param queryFolder the new value
   */
  public void setQueryFolder(String queryFolder) {
    this.queryFolder = queryFolder;
  }

  /**
   * Return results that are file moves originating from paths matching this pattern.
   */
  @JsonProperty("query_src")
  public String querySrc;

  /**
   * Gets {@link #querySrc}.
   *
   * @return the value of {@link #querySrc}
   */
  public String getQuerySrc() {
    return querySrc;
  }

  /**
   * Sets {@link #querySrc}.
   *
   * @param querySrc the new value
   */
  public void setQuerySrc(String querySrc) {
    this.querySrc = querySrc;
  }

  /**
   * Return results that are file moves with paths matching this pattern as destination.
   */
  @JsonProperty("query_destination")
  public String queryDestination;

  /**
   * Gets {@link #queryDestination}.
   *
   * @return the value of {@link #queryDestination}
   */
  public String getQueryDestination() {
    return queryDestination;
  }

  /**
   * Sets {@link #queryDestination}.
   *
   * @param queryDestination the new value
   */
  public void setQueryDestination(String queryDestination) {
    this.queryDestination = queryDestination;
  }

  /**
   * Filter results by this IP address.
   */
  @JsonProperty("query_ip")
  public String queryIp;

  /**
   * Gets {@link #queryIp}.
   *
   * @return the value of {@link #queryIp}
   */
  public String getQueryIp() {
    return queryIp;
  }

  /**
   * Sets {@link #queryIp}.
   *
   * @param queryIp the new value
   */
  public void setQueryIp(String queryIp) {
    this.queryIp = queryIp;
  }

  /**
   * Filter results by this username.
   */
  @JsonProperty("query_username")
  public String queryUsername;

  /**
   * Gets {@link #queryUsername}.
   *
   * @return the value of {@link #queryUsername}
   */
  public String getQueryUsername() {
    return queryUsername;
  }

  /**
   * Sets {@link #queryUsername}.
   *
   * @param queryUsername the new value
   */
  public void setQueryUsername(String queryUsername) {
    this.queryUsername = queryUsername;
  }

  /**
   * If searching for Histories about login failures, this parameter restricts results to failures of this specific type.  Valid values: `expired_trial`, `account_overdue`, `locked_out`, `ip_mismatch`, `password_mismatch`, `site_mismatch`, `username_not_found`, `none`, `no_ftp_permission`, `no_web_permission`, `no_directory`, `errno_enoent`, `no_sftp_permission`, `no_dav_permission`, `no_restapi_permission`, `key_mismatch`, `region_mismatch`, `expired_access`, `desktop_ip_mismatch`, `desktop_api_key_not_used_quickly_enough`, `disabled`, `country_mismatch`, `insecure_ftp`, `insecure_cipher`, `rate_limited`, `no_s3_compatible_endpoint_permission`, `mobile_ip_mismatch`, `mobile_api_key_not_used_quickly_enough`, `desktop_app_disabled`, `mobile_app_disabled`
   */
  @JsonProperty("query_failure_type")
  public String queryFailureType;

  /**
   * Gets {@link #queryFailureType}.
   *
   * @return the value of {@link #queryFailureType}
   */
  public String getQueryFailureType() {
    return queryFailureType;
  }

  /**
   * Sets {@link #queryFailureType}.
   *
   * @param queryFailureType the new value
   */
  public void setQueryFailureType(String queryFailureType) {
    this.queryFailureType = queryFailureType;
  }

  /**
   * If searching for Histories about specific objects (such as Users, or API Keys), this parameter restricts results to objects that match this ID.
   */
  @JsonProperty("query_target_id")
  public String queryTargetId;

  /**
   * Gets {@link #queryTargetId}.
   *
   * @return the value of {@link #queryTargetId}
   */
  public String getQueryTargetId() {
    return queryTargetId;
  }

  /**
   * Sets {@link #queryTargetId}.
   *
   * @param queryTargetId the new value
   */
  public void setQueryTargetId(String queryTargetId) {
    this.queryTargetId = queryTargetId;
  }

  /**
   * If searching for Histories about Users, Groups or other objects with names, this parameter restricts results to objects with this name/username.
   */
  @JsonProperty("query_target_name")
  public String queryTargetName;

  /**
   * Gets {@link #queryTargetName}.
   *
   * @return the value of {@link #queryTargetName}
   */
  public String getQueryTargetName() {
    return queryTargetName;
  }

  /**
   * Sets {@link #queryTargetName}.
   *
   * @param queryTargetName the new value
   */
  public void setQueryTargetName(String queryTargetName) {
    this.queryTargetName = queryTargetName;
  }

  /**
   * If searching for Histories about Permissions, this parameter restricts results to permissions of this level.
   */
  @JsonProperty("query_target_permission")
  public String queryTargetPermission;

  /**
   * Gets {@link #queryTargetPermission}.
   *
   * @return the value of {@link #queryTargetPermission}
   */
  public String getQueryTargetPermission() {
    return queryTargetPermission;
  }

  /**
   * Sets {@link #queryTargetPermission}.
   *
   * @param queryTargetPermission the new value
   */
  public void setQueryTargetPermission(String queryTargetPermission) {
    this.queryTargetPermission = queryTargetPermission;
  }

  /**
   * If searching for Histories about API keys, this parameter restricts results to API keys created by/for this user ID.
   */
  @JsonProperty("query_target_user_id")
  public String queryTargetUserId;

  /**
   * Gets {@link #queryTargetUserId}.
   *
   * @return the value of {@link #queryTargetUserId}
   */
  public String getQueryTargetUserId() {
    return queryTargetUserId;
  }

  /**
   * Sets {@link #queryTargetUserId}.
   *
   * @param queryTargetUserId the new value
   */
  public void setQueryTargetUserId(String queryTargetUserId) {
    this.queryTargetUserId = queryTargetUserId;
  }

  /**
   * If searching for Histories about API keys, this parameter restricts results to API keys created by/for this username.
   */
  @JsonProperty("query_target_username")
  public String queryTargetUsername;

  /**
   * Gets {@link #queryTargetUsername}.
   *
   * @return the value of {@link #queryTargetUsername}
   */
  public String getQueryTargetUsername() {
    return queryTargetUsername;
  }

  /**
   * Sets {@link #queryTargetUsername}.
   *
   * @param queryTargetUsername the new value
   */
  public void setQueryTargetUsername(String queryTargetUsername) {
    this.queryTargetUsername = queryTargetUsername;
  }

  /**
   * If searching for Histories about API keys, this parameter restricts results to API keys associated with this platform.
   */
  @JsonProperty("query_target_platform")
  public String queryTargetPlatform;

  /**
   * Gets {@link #queryTargetPlatform}.
   *
   * @return the value of {@link #queryTargetPlatform}
   */
  public String getQueryTargetPlatform() {
    return queryTargetPlatform;
  }

  /**
   * Sets {@link #queryTargetPlatform}.
   *
   * @param queryTargetPlatform the new value
   */
  public void setQueryTargetPlatform(String queryTargetPlatform) {
    this.queryTargetPlatform = queryTargetPlatform;
  }

  /**
   * If searching for Histories about API keys, this parameter restricts results to API keys with this permission set.
   */
  @JsonProperty("query_target_permission_set")
  public String queryTargetPermissionSet;

  /**
   * Gets {@link #queryTargetPermissionSet}.
   *
   * @return the value of {@link #queryTargetPermissionSet}
   */
  public String getQueryTargetPermissionSet() {
    return queryTargetPermissionSet;
  }

  /**
   * Sets {@link #queryTargetPermissionSet}.
   *
   * @param queryTargetPermissionSet the new value
   */
  public void setQueryTargetPermissionSet(String queryTargetPermissionSet) {
    this.queryTargetPermissionSet = queryTargetPermissionSet;
  }

  /**
   * If `status` is `ready`, this will be a URL where all the results can be downloaded at once as a CSV.
   */
  @JsonProperty("results_url")
  public String resultsUrl;

  /**
   * Gets {@link #resultsUrl}.
   *
   * @return the value of {@link #resultsUrl}
   */
  public String getResultsUrl() {
    return resultsUrl;
  }

  /**
   * Sets {@link #resultsUrl}.
   *
   * @param resultsUrl the new value
   */
  public void setResultsUrl(String resultsUrl) {
    this.resultsUrl = resultsUrl;
  }

  /**
   * User ID.  Provide a value of `0` to operate the current session's user.
   */
  @JsonProperty("user_id")
  public Long userId;

  /**
   * Gets {@link #userId}.
   *
   * @return the value of {@link #userId}
   */
  public Long getUserId() {
    return userId;
  }

  /**
   * Sets {@link #userId}.
   *
   * @param userId the new value
   */
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
   * Show History Export
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static HistoryExport find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show History Export
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static HistoryExport find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show History Export
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static HistoryExport find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public static HistoryExport find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().historyExports().find(id, parameters, options);
  }

  /**
   * Show History Export
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static HistoryExport get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show History Export
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static HistoryExport get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create History Export
   *
   * @see #create(HashMap, HashMap)
   */
  public static HistoryExport create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create History Export
   *
   * @see #create(HashMap, HashMap)
   */
  public static HistoryExport create(HashMap<String, Object> parameters) throws RuntimeException {
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
  public static HistoryExport create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().historyExports().create(parameters, options);
  }


}
