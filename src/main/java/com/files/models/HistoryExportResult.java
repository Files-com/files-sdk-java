package com.files.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.ModelUtils;
import com.files.util.FilesInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class HistoryExportResult {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public HistoryExportResult() {
    this(null, null);
  }

  public HistoryExportResult(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public HistoryExportResult(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Action ID
  */
  @Getter
  @JsonProperty("id")
  private Long id;

  /**
  * When the action happened
  */
  @Getter
  @JsonProperty("created_at")
  private Long createdAt;

  /**
  * User ID
  */
  @Getter
  @JsonProperty("user_id")
  private Long userId;

  /**
  * File ID related to the action
  */
  @Getter
  @JsonProperty("file_id")
  private Long fileId;

  /**
  * ID of the parent folder
  */
  @Getter
  @JsonProperty("parent_id")
  private Long parentId;

  /**
  * Path of the related action This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @Getter
  @JsonProperty("path")
  private String path;

  /**
  * Folder in which the action occurred
  */
  @Getter
  @JsonProperty("folder")
  private String folder;

  /**
  * File move originated from this path
  */
  @Getter
  @JsonProperty("src")
  private String src;

  /**
  * File moved to this destination folder
  */
  @Getter
  @JsonProperty("destination")
  private String destination;

  /**
  * Client IP that performed the action
  */
  @Getter
  @JsonProperty("ip")
  private String ip;

  /**
  * Username of the user that performed the action
  */
  @Getter
  @JsonProperty("username")
  private String username;

  /**
  * What action was taken. Valid values: `create`, `read`, `update`, `destroy`, `move`, `login`, `failedlogin`, `copy`, `user_create`, `user_update`, `user_destroy`, `group_create`, `group_update`, `group_destroy`, `permission_create`, `permission_destroy`, `api_key_create`, `api_key_update`, `api_key_destroy`
  */
  @Getter
  @JsonProperty("action")
  private String action;

  /**
  * The type of login failure, if applicable.  Valid values: `expired_trial`, `account_overdue`, `locked_out`, `ip_mismatch`, `password_mismatch`, `site_mismatch`, `username_not_found`, `none`, `no_ftp_permission`, `no_web_permission`, `no_directory`, `errno_enoent`, `no_sftp_permission`, `no_dav_permission`, `no_restapi_permission`, `key_mismatch`, `region_mismatch`, `expired_access`, `desktop_ip_mismatch`, `desktop_api_key_not_used_quickly_enough`, `disabled`
  */
  @Getter
  @JsonProperty("failure_type")
  private String failureType;

  /**
  * Inteface through which the action was taken. Valid values: `web`, `ftp`, `robot`, `jsapi`, `webdesktopapi`, `sftp`, `dav`, `desktop`, `restapi`, `scim`
  */
  @Getter
  @JsonProperty("interface")
  private String interfaceName;

  /**
  * ID of the object (such as Users, or API Keys) on which the action was taken
  */
  @Getter
  @JsonProperty("target_id")
  private Long targetId;

  /**
  * Name of the User, Group or other object with a name related to this action
  */
  @Getter
  @JsonProperty("target_name")
  private String targetName;

  /**
  * Permission level of the action
  */
  @Getter
  @JsonProperty("target_permission")
  private String targetPermission;

  /**
  * Whether or not the action was recursive
  */
  @Getter
  @JsonProperty("target_recursive")
  private Boolean targetRecursive;

  /**
  * If searching for Histories about API keys, this is when the API key will expire
  */
  @Getter
  @JsonProperty("target_expires_at")
  private Long targetExpiresAt;

  /**
  * If searching for Histories about API keys, this represents the permission set of the associated  API key
  */
  @Getter
  @JsonProperty("target_permission_set")
  private String targetPermissionSet;

  /**
  * If searching for Histories about API keys, this is the platform on which the action was taken
  */
  @Getter
  @JsonProperty("target_platform")
  private String targetPlatform;

  /**
  * If searching for Histories about API keys, this is the username on which the action was taken
  */
  @Getter
  @JsonProperty("target_username")
  private String targetUsername;

  /**
  * If searching for Histories about API keys, this is the User ID on which the action was taken
  */
  @Getter
  @JsonProperty("target_user_id")
  private Long targetUserId;



  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  *   cursor - string - Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   history_export_id (required) - int64 - ID of the associated history export.
  */
  public static List<HistoryExportResult> list() throws IOException{
    return list(null,null);
  }
  public static List<HistoryExportResult> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  public static List<HistoryExportResult> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }

    if (parameters.containsKey("page") && !(parameters.get("page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: page must be of type Long parameters[\"page\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }

    if (parameters.containsKey("action") && !(parameters.get("action") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: action must be of type String parameters[\"action\"]");
    }

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("history_export_id") && !(parameters.get("history_export_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: history_export_id must be of type Long parameters[\"history_export_id\"]");
    }

    if (!parameters.containsKey("history_export_id") || parameters.get("history_export_id") == null) {
      throw new NullPointerException("Parameter missing: history_export_id parameters[\"history_export_id\"]");
    }
    String url = String.format("%s%s/history_export_results", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<HistoryExportResult>> typeReference = new TypeReference<List<HistoryExportResult>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<HistoryExportResult> all() throws IOException {
    return all(null, null);
  }

  public static List<HistoryExportResult> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

}


