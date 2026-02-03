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
public class HistoryExportResult implements ModelInterface {
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


  public HistoryExportResult() {
    this(null, null);
  }

  public HistoryExportResult(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public HistoryExportResult(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Action ID
  */
  @JsonProperty("id")
  public Long id;

  public Long getId() {
    return id;
  }

  /**
  * When the action happened
  */
  @JsonProperty("created_at")
  public Long createdAt;

  public Long getCreatedAt() {
    return createdAt;
  }

  /**
  * When the action happened, in ISO8601 format.
  */
  @JsonProperty("created_at_iso8601")
  public String createdAtIso8601;

  public String getCreatedAtIso8601() {
    return createdAtIso8601;
  }

  /**
  * User ID
  */
  @JsonProperty("user_id")
  public Long userId;

  public Long getUserId() {
    return userId;
  }

  /**
  * File ID related to the action
  */
  @JsonProperty("file_id")
  public Long fileId;

  public Long getFileId() {
    return fileId;
  }

  /**
  * ID of the parent folder
  */
  @JsonProperty("parent_id")
  public Long parentId;

  public Long getParentId() {
    return parentId;
  }

  /**
  * Path of the related action. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @JsonProperty("path")
  public String path;

  public String getPath() {
    return path;
  }

  /**
  * Folder in which the action occurred
  */
  @JsonProperty("folder")
  public String folder;

  public String getFolder() {
    return folder;
  }

  /**
  * File move originated from this path
  */
  @JsonProperty("src")
  public String src;

  public String getSrc() {
    return src;
  }

  /**
  * File moved to this destination folder
  */
  @JsonProperty("destination")
  public String destination;

  public String getDestination() {
    return destination;
  }

  /**
  * Client IP that performed the action
  */
  @JsonProperty("ip")
  public String ip;

  public String getIp() {
    return ip;
  }

  /**
  * Username of the user that performed the action
  */
  @JsonProperty("username")
  public String username;

  public String getUsername() {
    return username;
  }

  /**
  * true if this change was performed by a user on a parent site.
  */
  @JsonProperty("user_is_from_parent_site")
  public Boolean userIsFromParentSite;

  public Boolean getUserIsFromParentSite() {
    return userIsFromParentSite;
  }

  /**
  * What action was taken. Valid values: `create`, `read`, `update`, `destroy`, `move`, `login`, `failedlogin`, `copy`, `user_create`, `user_update`, `user_destroy`, `group_create`, `group_update`, `group_destroy`, `permission_create`, `permission_destroy`, `api_key_create`, `api_key_update`, `api_key_destroy`, `archived_delete`
  */
  @JsonProperty("action")
  public String action;

  public String getAction() {
    return action;
  }

  /**
  * The type of login failure, if applicable.  Valid values: `expired_trial`, `account_overdue`, `locked_out`, `ip_mismatch`, `password_mismatch`, `site_mismatch`, `username_not_found`, `none`, `no_ftp_permission`, `no_web_permission`, `no_directory`, `errno_enoent`, `no_sftp_permission`, `no_dav_permission`, `no_restapi_permission`, `key_mismatch`, `region_mismatch`, `expired_access`, `desktop_ip_mismatch`, `desktop_api_key_not_used_quickly_enough`, `disabled`, `country_mismatch`, `insecure_ftp`, `insecure_cipher`, `rate_limited`
  */
  @JsonProperty("failure_type")
  public String failureType;

  public String getFailureType() {
    return failureType;
  }

  /**
  * Interface through which the action was taken. Valid values: `web`, `ftp`, `robot`, `jsapi`, `webdesktopapi`, `sftp`, `dav`, `desktop`, `restapi`, `scim`, `office`, `mobile`, `as2`, `inbound_email`, `remote`, `inbound_s3`
  */
  @JsonProperty("interface")
  public String interfaceName;

  public String getInterfaceName() {
    return interfaceName;
  }

  /**
  * ID of the object (such as Users, or API Keys) on which the action was taken
  */
  @JsonProperty("target_id")
  public Long targetId;

  public Long getTargetId() {
    return targetId;
  }

  /**
  * Name of the User, Group or other object with a name related to this action
  */
  @JsonProperty("target_name")
  public String targetName;

  public String getTargetName() {
    return targetName;
  }

  /**
  * Permission level of the action
  */
  @JsonProperty("target_permission")
  public String targetPermission;

  public String getTargetPermission() {
    return targetPermission;
  }

  /**
  * Whether or not the action was recursive
  */
  @JsonProperty("target_recursive")
  public Boolean targetRecursive;

  public Boolean getTargetRecursive() {
    return targetRecursive;
  }

  /**
  * If searching for Histories about API keys, this is when the API key will expire. Represented as a Unix timestamp.
  */
  @JsonProperty("target_expires_at")
  public Long targetExpiresAt;

  public Long getTargetExpiresAt() {
    return targetExpiresAt;
  }

  /**
  * If searching for Histories about API keys, this is when the API key will expire. Represented in ISO8601 format.
  */
  @JsonProperty("target_expires_at_iso8601")
  public String targetExpiresAtIso8601;

  public String getTargetExpiresAtIso8601() {
    return targetExpiresAtIso8601;
  }

  /**
  * If searching for Histories about API keys, this represents the permission set of the associated  API key
  */
  @JsonProperty("target_permission_set")
  public String targetPermissionSet;

  public String getTargetPermissionSet() {
    return targetPermissionSet;
  }

  /**
  * If searching for Histories about API keys, this is the platform on which the action was taken
  */
  @JsonProperty("target_platform")
  public String targetPlatform;

  public String getTargetPlatform() {
    return targetPlatform;
  }

  /**
  * If searching for Histories about API keys, this is the username on which the action was taken
  */
  @JsonProperty("target_username")
  public String targetUsername;

  public String getTargetUsername() {
    return targetUsername;
  }

  /**
  * If searching for Histories about API keys, this is the User ID on which the action was taken
  */
  @JsonProperty("target_user_id")
  public Long targetUserId;

  public Long getTargetUserId() {
    return targetUserId;
  }


  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   history_export_id (required) - int64 - ID of the associated history export.
  */
  public static ListIterator<HistoryExportResult> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<HistoryExportResult> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<HistoryExportResult> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (!parameters.containsKey("history_export_id") || parameters.get("history_export_id") == null) {
      throw new NullPointerException("Parameter missing: history_export_id parameters[\"history_export_id\"]");
    }

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }
    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }
    if (parameters.containsKey("history_export_id") && !(parameters.get("history_export_id") instanceof Long || parameters.get("history_export_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: history_export_id must be of type Long or Integer parameters[\"history_export_id\"]");
    }


    String url = String.format("%s%s/history_export_results", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<HistoryExportResult>> typeReference = new TypeReference<List<HistoryExportResult>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<HistoryExportResult> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<HistoryExportResult> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
