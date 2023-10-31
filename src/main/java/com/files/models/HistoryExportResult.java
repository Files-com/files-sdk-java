package com.files.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.ListIterator;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HistoryExportResult {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
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
  @Getter
  @JsonProperty("id")
  public Long id;

  /**
  * When the action happened
  */
  @Getter
  @JsonProperty("created_at")
  public Long createdAt;

  /**
  * When the action happened, in ISO8601 format.
  */
  @Getter
  @JsonProperty("created_at_iso8601")
  public String createdAtIso8601;

  /**
  * User ID
  */
  @Getter
  @JsonProperty("user_id")
  public Long userId;

  /**
  * File ID related to the action
  */
  @Getter
  @JsonProperty("file_id")
  public Long fileId;

  /**
  * ID of the parent folder
  */
  @Getter
  @JsonProperty("parent_id")
  public Long parentId;

  /**
  * Path of the related action This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @Getter
  @JsonProperty("path")
  public String path;

  /**
  * Folder in which the action occurred
  */
  @Getter
  @JsonProperty("folder")
  public String folder;

  /**
  * File move originated from this path
  */
  @Getter
  @JsonProperty("src")
  public String src;

  /**
  * File moved to this destination folder
  */
  @Getter
  @JsonProperty("destination")
  public String destination;

  /**
  * Client IP that performed the action
  */
  @Getter
  @JsonProperty("ip")
  public String ip;

  /**
  * Username of the user that performed the action
  */
  @Getter
  @JsonProperty("username")
  public String username;

  /**
  * What action was taken. Valid values: `create`, `read`, `update`, `destroy`, `move`, `login`, `failedlogin`, `copy`, `user_create`, `user_update`, `user_destroy`, `group_create`, `group_update`, `group_destroy`, `permission_create`, `permission_destroy`, `api_key_create`, `api_key_update`, `api_key_destroy`
  */
  @Getter
  @JsonProperty("action")
  public String action;

  /**
  * The type of login failure, if applicable.  Valid values: `expired_trial`, `account_overdue`, `locked_out`, `ip_mismatch`, `password_mismatch`, `site_mismatch`, `username_not_found`, `none`, `no_ftp_permission`, `no_web_permission`, `no_directory`, `errno_enoent`, `no_sftp_permission`, `no_dav_permission`, `no_restapi_permission`, `key_mismatch`, `region_mismatch`, `expired_access`, `desktop_ip_mismatch`, `desktop_api_key_not_used_quickly_enough`, `disabled`, `country_mismatch`
  */
  @Getter
  @JsonProperty("failure_type")
  public String failureType;

  /**
  * Inteface through which the action was taken. Valid values: `web`, `ftp`, `robot`, `jsapi`, `webdesktopapi`, `sftp`, `dav`, `desktop`, `restapi`, `scim`, `office`, `mobile`, `as2`, `inbound_email`, `remote`
  */
  @Getter
  @JsonProperty("interface")
  public String interfaceName;

  /**
  * ID of the object (such as Users, or API Keys) on which the action was taken
  */
  @Getter
  @JsonProperty("target_id")
  public Long targetId;

  /**
  * Name of the User, Group or other object with a name related to this action
  */
  @Getter
  @JsonProperty("target_name")
  public String targetName;

  /**
  * Permission level of the action
  */
  @Getter
  @JsonProperty("target_permission")
  public String targetPermission;

  /**
  * Whether or not the action was recursive
  */
  @Getter
  @JsonProperty("target_recursive")
  public Boolean targetRecursive;

  /**
  * If searching for Histories about API keys, this is when the API key will expire. Represented as a Unix timestamp.
  */
  @Getter
  @JsonProperty("target_expires_at")
  public Long targetExpiresAt;

  /**
  * If searching for Histories about API keys, this is when the API key will expire. Represented in ISO8601 format.
  */
  @Getter
  @JsonProperty("target_expires_at_iso8601")
  public String targetExpiresAtIso8601;

  /**
  * If searching for Histories about API keys, this represents the permission set of the associated  API key
  */
  @Getter
  @JsonProperty("target_permission_set")
  public String targetPermissionSet;

  /**
  * If searching for Histories about API keys, this is the platform on which the action was taken
  */
  @Getter
  @JsonProperty("target_platform")
  public String targetPlatform;

  /**
  * If searching for Histories about API keys, this is the username on which the action was taken
  */
  @Getter
  @JsonProperty("target_username")
  public String targetUsername;

  /**
  * If searching for Histories about API keys, this is the User ID on which the action was taken
  */
  @Getter
  @JsonProperty("target_user_id")
  public Long targetUserId;



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

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }
    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }
    if (parameters.containsKey("history_export_id") && !(parameters.get("history_export_id") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: history_export_id must be of type Long parameters[\"history_export_id\"]");
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
