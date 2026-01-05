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
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Restore implements ModelInterface {
  private HashMap<String, Object> options;

  public void setOptions(HashMap<String, Object> options) {
    this.options = options;
  }

  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();


  public Restore() {
    this(null, null);
  }

  public Restore(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Restore(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Restore all files deleted after this date/time. Don't set this earlier than you need. Can not be greater than 365 days prior to the restore request.
  */
  @JsonProperty("earliest_date")
  public Date earliestDate;

  public Date getEarliestDate() {
    return earliestDate;
  }

  public void setEarliestDate(Date earliestDate) {
    this.earliestDate = earliestDate;
  }

  /**
  * Restore Record ID.
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
  * Number of directories that were successfully restored.
  */
  @JsonProperty("dirs_restored")
  public Long dirsRestored;

  public Long getDirsRestored() {
    return dirsRestored;
  }

  public void setDirsRestored(Long dirsRestored) {
    this.dirsRestored = dirsRestored;
  }

  /**
  * Number of directories that were not able to be restored.
  */
  @JsonProperty("dirs_errored")
  public Long dirsErrored;

  public Long getDirsErrored() {
    return dirsErrored;
  }

  public void setDirsErrored(Long dirsErrored) {
    this.dirsErrored = dirsErrored;
  }

  /**
  * Total number of directories processed.
  */
  @JsonProperty("dirs_total")
  public Long dirsTotal;

  public Long getDirsTotal() {
    return dirsTotal;
  }

  public void setDirsTotal(Long dirsTotal) {
    this.dirsTotal = dirsTotal;
  }

  /**
  * Number of files successfully restored.
  */
  @JsonProperty("files_restored")
  public Long filesRestored;

  public Long getFilesRestored() {
    return filesRestored;
  }

  public void setFilesRestored(Long filesRestored) {
    this.filesRestored = filesRestored;
  }

  /**
  * Number of files that were not able to be restored.
  */
  @JsonProperty("files_errored")
  public Long filesErrored;

  public Long getFilesErrored() {
    return filesErrored;
  }

  public void setFilesErrored(Long filesErrored) {
    this.filesErrored = filesErrored;
  }

  /**
  * Total number of files processed.
  */
  @JsonProperty("files_total")
  public Long filesTotal;

  public Long getFilesTotal() {
    return filesTotal;
  }

  public void setFilesTotal(Long filesTotal) {
    this.filesTotal = filesTotal;
  }

  /**
  * Prefix of the files/folders to restore. To restore a folder, add a trailing slash to the folder name. Do not use a leading slash. To restore all deleted items, specify an empty string (`''`) in the prefix field or omit the field from the request.
  */
  @JsonProperty("prefix")
  public String prefix;

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  /**
  * Type of restoration to perform. `files` restores deleted filesystem items. `users` restores deleted users and associated access/authentication records.
  */
  @JsonProperty("restoration_type")
  public String restorationType;

  public String getRestorationType() {
    return restorationType;
  }

  public void setRestorationType(String restorationType) {
    this.restorationType = restorationType;
  }

  /**
  * If true, we will restore the files in place (into their original paths). If false, we will create a new restoration folder in the root and restore files there.
  */
  @JsonProperty("restore_in_place")
  public Boolean restoreInPlace;

  public Boolean getRestoreInPlace() {
    return restoreInPlace;
  }

  public void setRestoreInPlace(Boolean restoreInPlace) {
    this.restoreInPlace = restoreInPlace;
  }

  /**
  * If true, we will also restore any Permissions that match the same path prefix from the same dates.
  */
  @JsonProperty("restore_deleted_permissions")
  public Boolean restoreDeletedPermissions;

  public Boolean getRestoreDeletedPermissions() {
    return restoreDeletedPermissions;
  }

  public void setRestoreDeletedPermissions(Boolean restoreDeletedPermissions) {
    this.restoreDeletedPermissions = restoreDeletedPermissions;
  }

  /**
  * Number of users successfully restored (only present for `restoration_type=users`).
  */
  @JsonProperty("users_restored")
  public Long usersRestored;

  public Long getUsersRestored() {
    return usersRestored;
  }

  public void setUsersRestored(Long usersRestored) {
    this.usersRestored = usersRestored;
  }

  /**
  * Number of users that failed to restore (only present for `restoration_type=users`).
  */
  @JsonProperty("users_errored")
  public Long usersErrored;

  public Long getUsersErrored() {
    return usersErrored;
  }

  public void setUsersErrored(Long usersErrored) {
    this.usersErrored = usersErrored;
  }

  /**
  * Total number of users processed (only present for `restoration_type=users`).
  */
  @JsonProperty("users_total")
  public Long usersTotal;

  public Long getUsersTotal() {
    return usersTotal;
  }

  public void setUsersTotal(Long usersTotal) {
    this.usersTotal = usersTotal;
  }

  /**
  * Number of API keys restored (only present for `restoration_type=users`).
  */
  @JsonProperty("api_keys_restored")
  public Long apiKeysRestored;

  public Long getApiKeysRestored() {
    return apiKeysRestored;
  }

  public void setApiKeysRestored(Long apiKeysRestored) {
    this.apiKeysRestored = apiKeysRestored;
  }

  /**
  * Number of public keys restored (only present for `restoration_type=users`).
  */
  @JsonProperty("public_keys_restored")
  public Long publicKeysRestored;

  public Long getPublicKeysRestored() {
    return publicKeysRestored;
  }

  public void setPublicKeysRestored(Long publicKeysRestored) {
    this.publicKeysRestored = publicKeysRestored;
  }

  /**
  * Number of two factor authentication methods restored (only present for `restoration_type=users`).
  */
  @JsonProperty("two_factor_authentication_methods_restored")
  public Long twoFactorAuthenticationMethodsRestored;

  public Long getTwoFactorAuthenticationMethodsRestored() {
    return twoFactorAuthenticationMethodsRestored;
  }

  public void setTwoFactorAuthenticationMethodsRestored(Long twoFactorAuthenticationMethodsRestored) {
    this.twoFactorAuthenticationMethodsRestored = twoFactorAuthenticationMethodsRestored;
  }

  /**
  * Status of the restoration process.
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
  * If true, we will update the last modified timestamp of restored files to today's date. If false, we might trigger File Expiration to delete the file again.
  */
  @JsonProperty("update_timestamps")
  public Boolean updateTimestamps;

  public Boolean getUpdateTimestamps() {
    return updateTimestamps;
  }

  public void setUpdateTimestamps(Boolean updateTimestamps) {
    this.updateTimestamps = updateTimestamps;
  }

  /**
  * Error messages received while restoring files and/or directories. Only present if there were errors.
  */
  @JsonProperty("error_messages")
  public String[] errorMessages;

  public String[] getErrorMessages() {
    return errorMessages;
  }

  public void setErrorMessages(String[] errorMessages) {
    this.errorMessages = errorMessages;
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    Restore.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are .
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `restoration_type`.
  */
  public static ListIterator<Restore> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<Restore> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<Restore> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



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


    String url = String.format("%s%s/restores", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<Restore>> typeReference = new TypeReference<List<Restore>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<Restore> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<Restore> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   earliest_date (required) - string - Restore all files deleted after this date/time. Don't set this earlier than you need. Can not be greater than 365 days prior to the restore request.
  *   prefix - string - Prefix of the files/folders to restore. To restore a folder, add a trailing slash to the folder name. Do not use a leading slash. To restore all deleted items, specify an empty string (`''`) in the prefix field or omit the field from the request.
  *   restoration_type - string - Type of restoration to perform. `files` restores deleted filesystem items. `users` restores deleted users and associated access/authentication records.
  *   restore_deleted_permissions - boolean - If true, we will also restore any Permissions that match the same path prefix from the same dates.
  *   restore_in_place - boolean - If true, we will restore the files in place (into their original paths). If false, we will create a new restoration folder in the root and restore files there.
  *   update_timestamps - boolean - If true, we will update the last modified timestamp of restored files to today's date. If false, we might trigger File Expiration to delete the file again.
  */
  public static Restore create() throws RuntimeException {
    return create(null, null);
  }

  public static Restore create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static Restore create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (!parameters.containsKey("earliest_date") || parameters.get("earliest_date") == null) {
      throw new NullPointerException("Parameter missing: earliest_date parameters[\"earliest_date\"]");
    }

    if (parameters.containsKey("earliest_date") && !(parameters.get("earliest_date") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: earliest_date must be of type String parameters[\"earliest_date\"]");
    }
    if (parameters.containsKey("prefix") && !(parameters.get("prefix") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: prefix must be of type String parameters[\"prefix\"]");
    }
    if (parameters.containsKey("restoration_type") && !(parameters.get("restoration_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: restoration_type must be of type String parameters[\"restoration_type\"]");
    }
    if (parameters.containsKey("restore_deleted_permissions") && !(parameters.get("restore_deleted_permissions") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: restore_deleted_permissions must be of type Boolean parameters[\"restore_deleted_permissions\"]");
    }
    if (parameters.containsKey("restore_in_place") && !(parameters.get("restore_in_place") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: restore_in_place must be of type Boolean parameters[\"restore_in_place\"]");
    }
    if (parameters.containsKey("update_timestamps") && !(parameters.get("update_timestamps") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: update_timestamps must be of type Boolean parameters[\"update_timestamps\"]");
    }


    String url = String.format("%s%s/restores", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<Restore> typeReference = new TypeReference<Restore>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


}
