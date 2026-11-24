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
import com.files.ListIterator;
import com.files.net.ClientRuntime;
import com.files.util.ModelUtils;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Restore implements ModelInterface {
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

  // One mapper for every Restore, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code Restore} with no attributes set.
   */
  public Restore() {
    this(null, null);
  }

  /**
   * Creates a {@code Restore} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public Restore(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code Restore} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public Restore(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Restore files or users deleted on or after this date/time. Don't set this earlier than you need. Can not be greater than 365 days prior to the restore request.
   */
  @JsonProperty("earliest_date")
  public Date earliestDate;

  /**
   * Gets {@link #earliestDate}.
   *
   * @return the value of {@link #earliestDate}
   */
  public Date getEarliestDate() {
    return earliestDate;
  }

  /**
   * Sets {@link #earliestDate}.
   *
   * @param earliestDate the new value
   */
  public void setEarliestDate(Date earliestDate) {
    this.earliestDate = earliestDate;
  }

  /**
   * Restore Record ID.
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
   * Number of directories that were successfully restored.
   */
  @JsonProperty("dirs_restored")
  public Long dirsRestored;

  /**
   * Gets {@link #dirsRestored}.
   *
   * @return the value of {@link #dirsRestored}
   */
  public Long getDirsRestored() {
    return dirsRestored;
  }

  /**
   * Sets {@link #dirsRestored}.
   *
   * @param dirsRestored the new value
   */
  public void setDirsRestored(Long dirsRestored) {
    this.dirsRestored = dirsRestored;
  }

  /**
   * Number of directories that were not able to be restored.
   */
  @JsonProperty("dirs_errored")
  public Long dirsErrored;

  /**
   * Gets {@link #dirsErrored}.
   *
   * @return the value of {@link #dirsErrored}
   */
  public Long getDirsErrored() {
    return dirsErrored;
  }

  /**
   * Sets {@link #dirsErrored}.
   *
   * @param dirsErrored the new value
   */
  public void setDirsErrored(Long dirsErrored) {
    this.dirsErrored = dirsErrored;
  }

  /**
   * Total number of directories processed.
   */
  @JsonProperty("dirs_total")
  public Long dirsTotal;

  /**
   * Gets {@link #dirsTotal}.
   *
   * @return the value of {@link #dirsTotal}
   */
  public Long getDirsTotal() {
    return dirsTotal;
  }

  /**
   * Sets {@link #dirsTotal}.
   *
   * @param dirsTotal the new value
   */
  public void setDirsTotal(Long dirsTotal) {
    this.dirsTotal = dirsTotal;
  }

  /**
   * Number of files successfully restored.
   */
  @JsonProperty("files_restored")
  public Long filesRestored;

  /**
   * Gets {@link #filesRestored}.
   *
   * @return the value of {@link #filesRestored}
   */
  public Long getFilesRestored() {
    return filesRestored;
  }

  /**
   * Sets {@link #filesRestored}.
   *
   * @param filesRestored the new value
   */
  public void setFilesRestored(Long filesRestored) {
    this.filesRestored = filesRestored;
  }

  /**
   * Number of files that were not able to be restored.
   */
  @JsonProperty("files_errored")
  public Long filesErrored;

  /**
   * Gets {@link #filesErrored}.
   *
   * @return the value of {@link #filesErrored}
   */
  public Long getFilesErrored() {
    return filesErrored;
  }

  /**
   * Sets {@link #filesErrored}.
   *
   * @param filesErrored the new value
   */
  public void setFilesErrored(Long filesErrored) {
    this.filesErrored = filesErrored;
  }

  /**
   * Total number of files processed.
   */
  @JsonProperty("files_total")
  public Long filesTotal;

  /**
   * Gets {@link #filesTotal}.
   *
   * @return the value of {@link #filesTotal}
   */
  public Long getFilesTotal() {
    return filesTotal;
  }

  /**
   * Sets {@link #filesTotal}.
   *
   * @param filesTotal the new value
   */
  public void setFilesTotal(Long filesTotal) {
    this.filesTotal = filesTotal;
  }

  /**
   * Prefix of the files/folders to restore, or a case-insensitive username prefix for a user restore. A trailing slash selects that folder and its contents only; without it, the prefix matches any path beginning with that text. Do not use a leading slash. To restore all deleted items of the selected restoration type within the selected site or workspace scope, specify an empty string (`''`) or omit the field.
   */
  @JsonProperty("prefix")
  public String prefix;

  /**
   * Gets {@link #prefix}.
   *
   * @return the value of {@link #prefix}
   */
  public String getPrefix() {
    return prefix;
  }

  /**
   * Sets {@link #prefix}.
   *
   * @param prefix the new value
   */
  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  /**
   * Type of restoration to perform. `files` restores deleted filesystem items. `users` restores deleted users and associated authentication records, plus permissions when `restore_deleted_permissions=true`. A 5-minute grace period allows for batch deletion processes: associated records deleted within the 5 minutes before the user are included, even if removed independently. Records deleted earlier, or before `earliest_date`, remain deleted.
   */
  @JsonProperty("restoration_type")
  public String restorationType;

  /**
   * Gets {@link #restorationType}.
   *
   * @return the value of {@link #restorationType}
   */
  public String getRestorationType() {
    return restorationType;
  }

  /**
   * Sets {@link #restorationType}.
   *
   * @param restorationType the new value
   */
  public void setRestorationType(String restorationType) {
    this.restorationType = restorationType;
  }

  /**
   * If true, we will restore the files in place (into their original paths). If false, we will create a new restoration folder in the root and restore files there.
   */
  @JsonProperty("restore_in_place")
  public Boolean restoreInPlace;

  /**
   * Gets {@link #restoreInPlace}.
   *
   * @return the value of {@link #restoreInPlace}
   */
  public Boolean getRestoreInPlace() {
    return restoreInPlace;
  }

  /**
   * Sets {@link #restoreInPlace}.
   *
   * @param restoreInPlace the new value
   */
  public void setRestoreInPlace(Boolean restoreInPlace) {
    this.restoreInPlace = restoreInPlace;
  }

  /**
   * If true, a user restore restores permissions deleted along with the selected users, including permissions removed independently within the 5 minutes before user deletion. Permissions deleted earlier, or before `earliest_date`, remain deleted. An in-place file restore restores user and group permissions on returned folders and their subfolders that were deleted from the time of the folder's deletion through 24 hours afterward, including permissions removed independently within that window. File restores into a new folder do not restore permissions.
   */
  @JsonProperty("restore_deleted_permissions")
  public Boolean restoreDeletedPermissions;

  /**
   * Gets {@link #restoreDeletedPermissions}.
   *
   * @return the value of {@link #restoreDeletedPermissions}
   */
  public Boolean getRestoreDeletedPermissions() {
    return restoreDeletedPermissions;
  }

  /**
   * Sets {@link #restoreDeletedPermissions}.
   *
   * @param restoreDeletedPermissions the new value
   */
  public void setRestoreDeletedPermissions(Boolean restoreDeletedPermissions) {
    this.restoreDeletedPermissions = restoreDeletedPermissions;
  }

  /**
   * Number of users successfully restored (only present for `restoration_type=users`).
   */
  @JsonProperty("users_restored")
  public Long usersRestored;

  /**
   * Gets {@link #usersRestored}.
   *
   * @return the value of {@link #usersRestored}
   */
  public Long getUsersRestored() {
    return usersRestored;
  }

  /**
   * Sets {@link #usersRestored}.
   *
   * @param usersRestored the new value
   */
  public void setUsersRestored(Long usersRestored) {
    this.usersRestored = usersRestored;
  }

  /**
   * Number of users that failed to restore (only present for `restoration_type=users`).
   */
  @JsonProperty("users_errored")
  public Long usersErrored;

  /**
   * Gets {@link #usersErrored}.
   *
   * @return the value of {@link #usersErrored}
   */
  public Long getUsersErrored() {
    return usersErrored;
  }

  /**
   * Sets {@link #usersErrored}.
   *
   * @param usersErrored the new value
   */
  public void setUsersErrored(Long usersErrored) {
    this.usersErrored = usersErrored;
  }

  /**
   * Total number of users processed (only present for `restoration_type=users`).
   */
  @JsonProperty("users_total")
  public Long usersTotal;

  /**
   * Gets {@link #usersTotal}.
   *
   * @return the value of {@link #usersTotal}
   */
  public Long getUsersTotal() {
    return usersTotal;
  }

  /**
   * Sets {@link #usersTotal}.
   *
   * @param usersTotal the new value
   */
  public void setUsersTotal(Long usersTotal) {
    this.usersTotal = usersTotal;
  }

  /**
   * Number of API keys restored (only present for `restoration_type=users`).
   */
  @JsonProperty("api_keys_restored")
  public Long apiKeysRestored;

  /**
   * Gets {@link #apiKeysRestored}.
   *
   * @return the value of {@link #apiKeysRestored}
   */
  public Long getApiKeysRestored() {
    return apiKeysRestored;
  }

  /**
   * Sets {@link #apiKeysRestored}.
   *
   * @param apiKeysRestored the new value
   */
  public void setApiKeysRestored(Long apiKeysRestored) {
    this.apiKeysRestored = apiKeysRestored;
  }

  /**
   * Number of public keys restored (only present for `restoration_type=users`).
   */
  @JsonProperty("public_keys_restored")
  public Long publicKeysRestored;

  /**
   * Gets {@link #publicKeysRestored}.
   *
   * @return the value of {@link #publicKeysRestored}
   */
  public Long getPublicKeysRestored() {
    return publicKeysRestored;
  }

  /**
   * Sets {@link #publicKeysRestored}.
   *
   * @param publicKeysRestored the new value
   */
  public void setPublicKeysRestored(Long publicKeysRestored) {
    this.publicKeysRestored = publicKeysRestored;
  }

  /**
   * Number of two factor authentication methods restored (only present for `restoration_type=users`).
   */
  @JsonProperty("two_factor_authentication_methods_restored")
  public Long twoFactorAuthenticationMethodsRestored;

  /**
   * Gets {@link #twoFactorAuthenticationMethodsRestored}.
   *
   * @return the value of {@link #twoFactorAuthenticationMethodsRestored}
   */
  public Long getTwoFactorAuthenticationMethodsRestored() {
    return twoFactorAuthenticationMethodsRestored;
  }

  /**
   * Sets {@link #twoFactorAuthenticationMethodsRestored}.
   *
   * @param twoFactorAuthenticationMethodsRestored the new value
   */
  public void setTwoFactorAuthenticationMethodsRestored(Long twoFactorAuthenticationMethodsRestored) {
    this.twoFactorAuthenticationMethodsRestored = twoFactorAuthenticationMethodsRestored;
  }

  /**
   * Status of the restoration process.
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
   * If true, we will update the last modified timestamp of restored files to today's date. If false, we might trigger File Expiration to delete the file again.
   */
  @JsonProperty("update_timestamps")
  public Boolean updateTimestamps;

  /**
   * Gets {@link #updateTimestamps}.
   *
   * @return the value of {@link #updateTimestamps}
   */
  public Boolean getUpdateTimestamps() {
    return updateTimestamps;
  }

  /**
   * Sets {@link #updateTimestamps}.
   *
   * @param updateTimestamps the new value
   */
  public void setUpdateTimestamps(Boolean updateTimestamps) {
    this.updateTimestamps = updateTimestamps;
  }

  /**
   * Workspace ID for a workspace-scoped restore. `0` means the default site-wide scope.
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
   * Error messages received while restoring files and/or directories. Only present if there were errors.
   */
  @JsonProperty("error_messages")
  public String[] errorMessages;

  /**
   * Gets {@link #errorMessages}.
   *
   * @return the value of {@link #errorMessages}
   */
  public String[] getErrorMessages() {
    return errorMessages;
  }

  /**
   * Sets {@link #errorMessages}.
   *
   * @param errorMessages the new value
   */
  public void setErrorMessages(String[] errorMessages) {
    this.errorMessages = errorMessages;
  }


  /**
   * Creates this Restore from its current attributes.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    refreshFrom(filesClient().restores().create(parameters, this.options));
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    return ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(Restore canonical) {
    this.earliestDate = canonical.earliestDate;
    this.id = canonical.id;
    this.dirsRestored = canonical.dirsRestored;
    this.dirsErrored = canonical.dirsErrored;
    this.dirsTotal = canonical.dirsTotal;
    this.filesRestored = canonical.filesRestored;
    this.filesErrored = canonical.filesErrored;
    this.filesTotal = canonical.filesTotal;
    this.prefix = canonical.prefix;
    this.restorationType = canonical.restorationType;
    this.restoreInPlace = canonical.restoreInPlace;
    this.restoreDeletedPermissions = canonical.restoreDeletedPermissions;
    this.usersRestored = canonical.usersRestored;
    this.usersErrored = canonical.usersErrored;
    this.usersTotal = canonical.usersTotal;
    this.apiKeysRestored = canonical.apiKeysRestored;
    this.publicKeysRestored = canonical.publicKeysRestored;
    this.twoFactorAuthenticationMethodsRestored = canonical.twoFactorAuthenticationMethodsRestored;
    this.status = canonical.status;
    this.updateTimestamps = canonical.updateTimestamps;
    this.workspaceId = canonical.workspaceId;
    this.errorMessages = canonical.errorMessages;
  }

  /**
   * List Restores
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Restore> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Restores
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Restore> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Restores
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `restoration_type`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Restore} records, in a {@code ListIterator}
   */
  public static ListIterator<Restore> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().restores().list(parameters, options);
  }

  /**
   * List Restores
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Restore> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Restores
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Restore> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Create Restore
   *
   * @see #create(HashMap, HashMap)
   */
  public static Restore create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Restore
   *
   * @see #create(HashMap, HashMap)
   */
  public static Restore create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create Restore
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code earliest_date} (String): Required. Restore files or users deleted on or after this date/time. Don't set this earlier than you need. Can not be greater than 365 days prior to the restore request.</li>
   * <li>{@code prefix} (String): Prefix of the files/folders to restore, or a case-insensitive username prefix for a user restore. A trailing slash selects that folder and its contents only; without it, the prefix matches any path beginning with that text. Do not use a leading slash. To restore all deleted items of the selected restoration type within the selected site or workspace scope, specify an empty string (`''`) or omit the field.</li>
   * <li>{@code restoration_type} (String): Type of restoration to perform. `files` restores deleted filesystem items. `users` restores deleted users and associated authentication records, plus permissions when `restore_deleted_permissions=true`. A 5-minute grace period allows for batch deletion processes: associated records deleted within the 5 minutes before the user are included, even if removed independently. Records deleted earlier, or before `earliest_date`, remain deleted.</li>
   * <li>{@code restore_deleted_permissions} (Boolean): If true, a user restore restores permissions deleted along with the selected users, including permissions removed independently within the 5 minutes before user deletion. Permissions deleted earlier, or before `earliest_date`, remain deleted. An in-place file restore restores user and group permissions on returned folders and their subfolders that were deleted from the time of the folder's deletion through 24 hours afterward, including permissions removed independently within that window. File restores into a new folder do not restore permissions.</li>
   * <li>{@code restore_in_place} (Boolean): If true, we will restore the files in place (into their original paths). If false, we will create a new restoration folder in the root and restore files there.</li>
   * <li>{@code update_timestamps} (Boolean): If true, we will update the last modified timestamp of restored files to today's date. If false, we might trigger File Expiration to delete the file again.</li>
   * <li>{@code workspace_id} (Long): Workspace ID for a workspace-scoped restore. `0` means the default site-wide scope.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Restore}
   */
  public static Restore create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().restores().create(parameters, options);
  }


}
