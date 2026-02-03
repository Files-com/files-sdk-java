package com.files.models;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
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

  public void setOptions(HashMap<String, Object> options) {
    this.options = options;
  }

  private ObjectMapper objectMapper = JsonMapper
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
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
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
  * If searching for Histories about login failures, this parameter restricts results to failures of this specific type.  Valid values: `expired_trial`, `account_overdue`, `locked_out`, `ip_mismatch`, `password_mismatch`, `site_mismatch`, `username_not_found`, `none`, `no_ftp_permission`, `no_web_permission`, `no_directory`, `errno_enoent`, `no_sftp_permission`, `no_dav_permission`, `no_restapi_permission`, `key_mismatch`, `region_mismatch`, `expired_access`, `desktop_ip_mismatch`, `desktop_api_key_not_used_quickly_enough`, `disabled`, `country_mismatch`, `insecure_ftp`, `insecure_cipher`, `rate_limited`
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

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    HistoryExport.create(parameters, this.options);
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
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }



    String url = String.format("%s%s/history_exports/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<HistoryExport> typeReference = new TypeReference<HistoryExport>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
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
  *   query_failure_type - string - If searching for Histories about login failures, this parameter restricts results to failures of this specific type.  Valid values: `expired_trial`, `account_overdue`, `locked_out`, `ip_mismatch`, `password_mismatch`, `site_mismatch`, `username_not_found`, `none`, `no_ftp_permission`, `no_web_permission`, `no_directory`, `errno_enoent`, `no_sftp_permission`, `no_dav_permission`, `no_restapi_permission`, `key_mismatch`, `region_mismatch`, `expired_access`, `desktop_ip_mismatch`, `desktop_api_key_not_used_quickly_enough`, `disabled`, `country_mismatch`, `insecure_ftp`, `insecure_cipher`, `rate_limited`
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
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



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


    String url = String.format("%s%s/history_exports", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<HistoryExport> typeReference = new TypeReference<HistoryExport>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


}
