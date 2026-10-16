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
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Notification implements ModelInterface {
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

  // One mapper for every Notification, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  public Notification() {
    this(null, null);
  }

  public Notification(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Notification(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Notification ID
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
   * Folder path to notify on. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
   */
  @JsonProperty("path")
  public String path;

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  /**
   * ID of Group to receive notifications
   */
  @JsonProperty("group_id")
  public Long groupId;

  public Long getGroupId() {
    return groupId;
  }

  public void setGroupId(Long groupId) {
    this.groupId = groupId;
  }

  /**
   * Group name, if a Group ID is set
   */
  @JsonProperty("group_name")
  public String groupName;

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  /**
   * Group IDs when the notification requires multiple groups
   */
  @JsonProperty("group_ids")
  public Long[] groupIds;

  public Long[] getGroupIds() {
    return groupIds;
  }

  public void setGroupIds(Long[] groupIds) {
    this.groupIds = groupIds;
  }

  /**
   * Group names when the notification requires multiple groups
   */
  @JsonProperty("group_names")
  public String[] groupNames;

  public String[] getGroupNames() {
    return groupNames;
  }

  public void setGroupNames(String[] groupNames) {
    this.groupNames = groupNames;
  }

  /**
   * If set, will only notify on actions made by a member of one of the specified groups
   */
  @JsonProperty("triggering_group_ids")
  public Long[] triggeringGroupIds;

  public Long[] getTriggeringGroupIds() {
    return triggeringGroupIds;
  }

  public void setTriggeringGroupIds(Long[] triggeringGroupIds) {
    this.triggeringGroupIds = triggeringGroupIds;
  }

  /**
   * If set, will only notify on actions made one of the specified users
   */
  @JsonProperty("triggering_user_ids")
  public Long[] triggeringUserIds;

  public Long[] getTriggeringUserIds() {
    return triggeringUserIds;
  }

  public void setTriggeringUserIds(Long[] triggeringUserIds) {
    this.triggeringUserIds = triggeringUserIds;
  }

  /**
   * Notify when actions are performed by a share recipient?
   */
  @JsonProperty("trigger_by_share_recipients")
  public Boolean triggerByShareRecipients;

  public Boolean getTriggerByShareRecipients() {
    return triggerByShareRecipients;
  }

  public void setTriggerByShareRecipients(Boolean triggerByShareRecipients) {
    this.triggerByShareRecipients = triggerByShareRecipients;
  }

  /**
   * If true, will send notifications about a user's own activity to that user.  If false, only activity performed by other users (or anonymous users) will be sent in notifications.
   */
  @JsonProperty("notify_user_actions")
  public Boolean notifyUserActions;

  public Boolean getNotifyUserActions() {
    return notifyUserActions;
  }

  public void setNotifyUserActions(Boolean notifyUserActions) {
    this.notifyUserActions = notifyUserActions;
  }

  /**
   * Trigger on files copied to this path?
   */
  @JsonProperty("notify_on_copy")
  public Boolean notifyOnCopy;

  public Boolean getNotifyOnCopy() {
    return notifyOnCopy;
  }

  public void setNotifyOnCopy(Boolean notifyOnCopy) {
    this.notifyOnCopy = notifyOnCopy;
  }

  /**
   * Trigger on files deleted in this path?
   */
  @JsonProperty("notify_on_delete")
  public Boolean notifyOnDelete;

  public Boolean getNotifyOnDelete() {
    return notifyOnDelete;
  }

  public void setNotifyOnDelete(Boolean notifyOnDelete) {
    this.notifyOnDelete = notifyOnDelete;
  }

  /**
   * Trigger on files downloaded in this path?
   */
  @JsonProperty("notify_on_download")
  public Boolean notifyOnDownload;

  public Boolean getNotifyOnDownload() {
    return notifyOnDownload;
  }

  public void setNotifyOnDownload(Boolean notifyOnDownload) {
    this.notifyOnDownload = notifyOnDownload;
  }

  /**
   * Trigger on files moved to this path?
   */
  @JsonProperty("notify_on_move")
  public Boolean notifyOnMove;

  public Boolean getNotifyOnMove() {
    return notifyOnMove;
  }

  public void setNotifyOnMove(Boolean notifyOnMove) {
    this.notifyOnMove = notifyOnMove;
  }

  /**
   * Trigger on files created/uploaded/updated/changed in this path?
   */
  @JsonProperty("notify_on_upload")
  public Boolean notifyOnUpload;

  public Boolean getNotifyOnUpload() {
    return notifyOnUpload;
  }

  public void setNotifyOnUpload(Boolean notifyOnUpload) {
    this.notifyOnUpload = notifyOnUpload;
  }

  /**
   * Apply notification recursively?  This will enable notifications for each subfolder.
   */
  @JsonProperty("recursive")
  public Boolean recursive;

  public Boolean getRecursive() {
    return recursive;
  }

  public void setRecursive(Boolean recursive) {
    this.recursive = recursive;
  }

  /**
   * The time interval that notifications are aggregated to
   */
  @JsonProperty("send_interval")
  public String sendInterval;

  public String getSendInterval() {
    return sendInterval;
  }

  public void setSendInterval(String sendInterval) {
    this.sendInterval = sendInterval;
  }

  /**
   * Custom subject line to use for notification emails
   */
  @JsonProperty("subject")
  public String subject;

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  /**
   * Custom message to include in notification emails
   */
  @JsonProperty("message")
  public String message;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * Array of filenames (possibly with wildcards) to scope trigger
   */
  @JsonProperty("triggering_filenames")
  public String[] triggeringFilenames;

  public String[] getTriggeringFilenames() {
    return triggeringFilenames;
  }

  public void setTriggeringFilenames(String[] triggeringFilenames) {
    this.triggeringFilenames = triggeringFilenames;
  }

  /**
   * Workspace ID. `0` means the default workspace.
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
   * Is the user unsubscribed from this notification?
   */
  @JsonProperty("unsubscribed")
  public Boolean unsubscribed;

  public Boolean getUnsubscribed() {
    return unsubscribed;
  }

  public void setUnsubscribed(Boolean unsubscribed) {
    this.unsubscribed = unsubscribed;
  }

  /**
   * The reason that the user unsubscribed
   */
  @JsonProperty("unsubscribed_reason")
  public String unsubscribedReason;

  public String getUnsubscribedReason() {
    return unsubscribedReason;
  }

  public void setUnsubscribedReason(String unsubscribedReason) {
    this.unsubscribedReason = unsubscribedReason;
  }

  /**
   * Notification user ID
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
   * Notification username
   */
  @JsonProperty("username")
  public String username;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * If true, it means that the recipient at this user's email address has manually unsubscribed from all emails, or had their email "hard bounce", which means that we are unable to send mail to this user's current email address. Notifications will resume if the user changes their email address.
   */
  @JsonProperty("suppressed_email")
  public Boolean suppressedEmail;

  public Boolean getSuppressedEmail() {
    return suppressedEmail;
  }

  public void setSuppressedEmail(Boolean suppressedEmail) {
    this.suppressedEmail = suppressedEmail;
  }

  /**
   * Updates this Notification. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Parameters:
   *   notify_on_copy - boolean - If `true`, copying or moving resources into this path will trigger a notification, in addition to just uploads.
   *   notify_on_delete - boolean - Trigger on files deleted in this path?
   *   notify_on_download - boolean - Trigger on files downloaded in this path?
   *   notify_on_move - boolean - Trigger on files moved to this path?
   *   notify_on_upload - boolean - Trigger on files created/uploaded/updated/changed in this path?
   *   notify_user_actions - boolean - If `true` actions initiated by the user will still result in a notification
   *   recursive - boolean - If `true`, enable notifications for each subfolder in this path
   *   send_interval - string - The time interval that notifications are aggregated by.  Can be `five_minutes`, `fifteen_minutes`, `hourly`, or `daily`.
   *   subject - string - Custom subject line to use for notification emails
   *   message - string - Custom message to include in notification emails
   *   triggering_filenames - array(string) - Array of filenames (possibly with wildcards) to scope trigger
   *   triggering_group_ids - array(int64) - If set, will only notify on actions made by a member of one of the specified groups
   *   triggering_user_ids - array(int64) - If set, will only notify on actions made one of the specified users
   *   trigger_by_share_recipients - boolean - Notify when actions are performed by a share recipient?
   *   workspace_id - int64 - Workspace ID. `0` means the default workspace.
   */
  public Notification update(HashMap<String, Object> parameters) throws IOException {
    Notification updated = filesClient().notifications().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().notifications().delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this Notification: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().notifications().create(parameters, this.options));
    }
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
    if (this.triggeringFilenames != null) {
      parameters.put("triggering_filenames", this.triggeringFilenames);
    }
    if (this.triggeringGroupIds != null) {
      parameters.put("triggering_group_ids", this.triggeringGroupIds);
    }
    if (this.triggeringUserIds != null) {
      parameters.put("triggering_user_ids", this.triggeringUserIds);
    }
    if (this.groupIds != null) {
      parameters.put("group_ids", ModelUtils.commaDelimited(this.groupIds));
    }
    return parameters;
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(Notification canonical) {
    this.id = canonical.id;
    this.path = canonical.path;
    this.groupId = canonical.groupId;
    this.groupName = canonical.groupName;
    this.groupIds = canonical.groupIds;
    this.groupNames = canonical.groupNames;
    this.triggeringGroupIds = canonical.triggeringGroupIds;
    this.triggeringUserIds = canonical.triggeringUserIds;
    this.triggerByShareRecipients = canonical.triggerByShareRecipients;
    this.notifyUserActions = canonical.notifyUserActions;
    this.notifyOnCopy = canonical.notifyOnCopy;
    this.notifyOnDelete = canonical.notifyOnDelete;
    this.notifyOnDownload = canonical.notifyOnDownload;
    this.notifyOnMove = canonical.notifyOnMove;
    this.notifyOnUpload = canonical.notifyOnUpload;
    this.recursive = canonical.recursive;
    this.sendInterval = canonical.sendInterval;
    this.subject = canonical.subject;
    this.message = canonical.message;
    this.triggeringFilenames = canonical.triggeringFilenames;
    this.workspaceId = canonical.workspaceId;
    this.unsubscribed = canonical.unsubscribed;
    this.unsubscribedReason = canonical.unsubscribedReason;
    this.userId = canonical.userId;
    this.username = canonical.username;
    this.suppressedEmail = canonical.suppressedEmail;
  }

  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `path`, `user_id` or `group_id`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `path`, `user_id`, `workspace_id` or `group_id`. Valid field combinations are `[ workspace_id, path ]`, `[ workspace_id, user_id ]`, `[ workspace_id, group_id ]` or `[ workspace_id, user_id, path ]`.
   *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `path`.
   *   path - string - Show notifications for this Path.
   *   include_ancestors - boolean - If `include_ancestors` is `true` and `path` is specified, include notifications for any parent paths. Ignored if `path` is not specified.
   *   group_id - string
   */
  public static ListIterator<Notification> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<Notification> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<Notification> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().notifications().list(parameters, options);
  }

  public static ListIterator<Notification> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<Notification> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Notification ID.
   */
  public static Notification find() throws RuntimeException {
    return find(null, null, null);
  }

  public static Notification find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static Notification find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static Notification find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().notifications().find(id, parameters, options);
  }

  public static Notification get() throws RuntimeException {
    return get(null, null, null);
  }

  public static Notification get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   user_id - int64 - The id of the user to notify. Provide `user_id`, `username` or `group_id`.
   *   notify_on_copy - boolean - If `true`, copying or moving resources into this path will trigger a notification, in addition to just uploads.
   *   notify_on_delete - boolean - Trigger on files deleted in this path?
   *   notify_on_download - boolean - Trigger on files downloaded in this path?
   *   notify_on_move - boolean - Trigger on files moved to this path?
   *   notify_on_upload - boolean - Trigger on files created/uploaded/updated/changed in this path?
   *   notify_user_actions - boolean - If `true` actions initiated by the user will still result in a notification
   *   recursive - boolean - If `true`, enable notifications for each subfolder in this path
   *   send_interval - string - The time interval that notifications are aggregated by.  Can be `five_minutes`, `fifteen_minutes`, `hourly`, or `daily`.
   *   subject - string - Custom subject line to use for notification emails
   *   message - string - Custom message to include in notification emails
   *   triggering_filenames - array(string) - Array of filenames (possibly with wildcards) to scope trigger
   *   triggering_group_ids - array(int64) - If set, will only notify on actions made by a member of one of the specified groups
   *   triggering_user_ids - array(int64) - If set, will only notify on actions made one of the specified users
   *   trigger_by_share_recipients - boolean - Notify when actions are performed by a share recipient?
   *   workspace_id - int64 - Workspace ID. `0` means the default workspace.
   *   group_id - int64 - The ID of the group to notify.  Provide `user_id`, `username` or `group_id`.
   *   group_ids - string - Group IDs when the notification requires multiple groups. If sent as a string, it should be comma-delimited.
   *   path - string - Path
   *   username - string - The username of the user to notify.  Provide `user_id`, `username` or `group_id`.
   */
  public static Notification create() throws RuntimeException {
    return create(null, null);
  }

  public static Notification create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static Notification create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().notifications().create(parameters, options);
  }


  /**
   * Parameters:
   *   notify_on_copy - boolean - If `true`, copying or moving resources into this path will trigger a notification, in addition to just uploads.
   *   notify_on_delete - boolean - Trigger on files deleted in this path?
   *   notify_on_download - boolean - Trigger on files downloaded in this path?
   *   notify_on_move - boolean - Trigger on files moved to this path?
   *   notify_on_upload - boolean - Trigger on files created/uploaded/updated/changed in this path?
   *   notify_user_actions - boolean - If `true` actions initiated by the user will still result in a notification
   *   recursive - boolean - If `true`, enable notifications for each subfolder in this path
   *   send_interval - string - The time interval that notifications are aggregated by.  Can be `five_minutes`, `fifteen_minutes`, `hourly`, or `daily`.
   *   subject - string - Custom subject line to use for notification emails
   *   message - string - Custom message to include in notification emails
   *   triggering_filenames - array(string) - Array of filenames (possibly with wildcards) to scope trigger
   *   triggering_group_ids - array(int64) - If set, will only notify on actions made by a member of one of the specified groups
   *   triggering_user_ids - array(int64) - If set, will only notify on actions made one of the specified users
   *   trigger_by_share_recipients - boolean - Notify when actions are performed by a share recipient?
   *   workspace_id - int64 - Workspace ID. `0` means the default workspace.
   */
  public static Notification update() throws RuntimeException {
    return update(null, null, null);
  }

  public static Notification update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static Notification update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static Notification update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().notifications().update(id, parameters, options);
  }


  /**
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().notifications().delete(id, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
