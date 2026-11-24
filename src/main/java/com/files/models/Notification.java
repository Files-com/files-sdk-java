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

  // One mapper for every Notification, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code Notification} with no attributes set.
   */
  public Notification() {
    this(null, null);
  }

  /**
   * Creates a {@code Notification} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public Notification(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code Notification} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
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
   * Folder path to notify on. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
   */
  @JsonProperty("path")
  public String path;

  /**
   * Gets {@link #path}.
   *
   * @return the value of {@link #path}
   */
  public String getPath() {
    return path;
  }

  /**
   * Sets {@link #path}.
   *
   * @param path the new value
   */
  public void setPath(String path) {
    this.path = path;
  }

  /**
   * ID of Group to receive notifications
   */
  @JsonProperty("group_id")
  public Long groupId;

  /**
   * Gets {@link #groupId}.
   *
   * @return the value of {@link #groupId}
   */
  public Long getGroupId() {
    return groupId;
  }

  /**
   * Sets {@link #groupId}.
   *
   * @param groupId the new value
   */
  public void setGroupId(Long groupId) {
    this.groupId = groupId;
  }

  /**
   * Group name, if a Group ID is set
   */
  @JsonProperty("group_name")
  public String groupName;

  /**
   * Gets {@link #groupName}.
   *
   * @return the value of {@link #groupName}
   */
  public String getGroupName() {
    return groupName;
  }

  /**
   * Sets {@link #groupName}.
   *
   * @param groupName the new value
   */
  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  /**
   * Group IDs when the notification requires multiple groups
   */
  @JsonProperty("group_ids")
  public Long[] groupIds;

  /**
   * Gets {@link #groupIds}.
   *
   * @return the value of {@link #groupIds}
   */
  public Long[] getGroupIds() {
    return groupIds;
  }

  /**
   * Sets {@link #groupIds}.
   *
   * @param groupIds the new value
   */
  public void setGroupIds(Long[] groupIds) {
    this.groupIds = groupIds;
  }

  /**
   * Group names when the notification requires multiple groups
   */
  @JsonProperty("group_names")
  public String[] groupNames;

  /**
   * Gets {@link #groupNames}.
   *
   * @return the value of {@link #groupNames}
   */
  public String[] getGroupNames() {
    return groupNames;
  }

  /**
   * Sets {@link #groupNames}.
   *
   * @param groupNames the new value
   */
  public void setGroupNames(String[] groupNames) {
    this.groupNames = groupNames;
  }

  /**
   * If set, will only notify on actions made by a member of one of the specified groups
   */
  @JsonProperty("triggering_group_ids")
  public Long[] triggeringGroupIds;

  /**
   * Gets {@link #triggeringGroupIds}.
   *
   * @return the value of {@link #triggeringGroupIds}
   */
  public Long[] getTriggeringGroupIds() {
    return triggeringGroupIds;
  }

  /**
   * Sets {@link #triggeringGroupIds}.
   *
   * @param triggeringGroupIds the new value
   */
  public void setTriggeringGroupIds(Long[] triggeringGroupIds) {
    this.triggeringGroupIds = triggeringGroupIds;
  }

  /**
   * If set, will only notify on actions made one of the specified users
   */
  @JsonProperty("triggering_user_ids")
  public Long[] triggeringUserIds;

  /**
   * Gets {@link #triggeringUserIds}.
   *
   * @return the value of {@link #triggeringUserIds}
   */
  public Long[] getTriggeringUserIds() {
    return triggeringUserIds;
  }

  /**
   * Sets {@link #triggeringUserIds}.
   *
   * @param triggeringUserIds the new value
   */
  public void setTriggeringUserIds(Long[] triggeringUserIds) {
    this.triggeringUserIds = triggeringUserIds;
  }

  /**
   * Notify when actions are performed by a share recipient?
   */
  @JsonProperty("trigger_by_share_recipients")
  public Boolean triggerByShareRecipients;

  /**
   * Gets {@link #triggerByShareRecipients}.
   *
   * @return the value of {@link #triggerByShareRecipients}
   */
  public Boolean getTriggerByShareRecipients() {
    return triggerByShareRecipients;
  }

  /**
   * Sets {@link #triggerByShareRecipients}.
   *
   * @param triggerByShareRecipients the new value
   */
  public void setTriggerByShareRecipients(Boolean triggerByShareRecipients) {
    this.triggerByShareRecipients = triggerByShareRecipients;
  }

  /**
   * If true, will send notifications about a user's own activity to that user.  If false, only activity performed by other users (or anonymous users) will be sent in notifications.
   */
  @JsonProperty("notify_user_actions")
  public Boolean notifyUserActions;

  /**
   * Gets {@link #notifyUserActions}.
   *
   * @return the value of {@link #notifyUserActions}
   */
  public Boolean getNotifyUserActions() {
    return notifyUserActions;
  }

  /**
   * Sets {@link #notifyUserActions}.
   *
   * @param notifyUserActions the new value
   */
  public void setNotifyUserActions(Boolean notifyUserActions) {
    this.notifyUserActions = notifyUserActions;
  }

  /**
   * Trigger on files copied to this path?
   */
  @JsonProperty("notify_on_copy")
  public Boolean notifyOnCopy;

  /**
   * Gets {@link #notifyOnCopy}.
   *
   * @return the value of {@link #notifyOnCopy}
   */
  public Boolean getNotifyOnCopy() {
    return notifyOnCopy;
  }

  /**
   * Sets {@link #notifyOnCopy}.
   *
   * @param notifyOnCopy the new value
   */
  public void setNotifyOnCopy(Boolean notifyOnCopy) {
    this.notifyOnCopy = notifyOnCopy;
  }

  /**
   * Trigger on files deleted in this path?
   */
  @JsonProperty("notify_on_delete")
  public Boolean notifyOnDelete;

  /**
   * Gets {@link #notifyOnDelete}.
   *
   * @return the value of {@link #notifyOnDelete}
   */
  public Boolean getNotifyOnDelete() {
    return notifyOnDelete;
  }

  /**
   * Sets {@link #notifyOnDelete}.
   *
   * @param notifyOnDelete the new value
   */
  public void setNotifyOnDelete(Boolean notifyOnDelete) {
    this.notifyOnDelete = notifyOnDelete;
  }

  /**
   * Trigger on files downloaded in this path?
   */
  @JsonProperty("notify_on_download")
  public Boolean notifyOnDownload;

  /**
   * Gets {@link #notifyOnDownload}.
   *
   * @return the value of {@link #notifyOnDownload}
   */
  public Boolean getNotifyOnDownload() {
    return notifyOnDownload;
  }

  /**
   * Sets {@link #notifyOnDownload}.
   *
   * @param notifyOnDownload the new value
   */
  public void setNotifyOnDownload(Boolean notifyOnDownload) {
    this.notifyOnDownload = notifyOnDownload;
  }

  /**
   * Trigger on files moved to this path?
   */
  @JsonProperty("notify_on_move")
  public Boolean notifyOnMove;

  /**
   * Gets {@link #notifyOnMove}.
   *
   * @return the value of {@link #notifyOnMove}
   */
  public Boolean getNotifyOnMove() {
    return notifyOnMove;
  }

  /**
   * Sets {@link #notifyOnMove}.
   *
   * @param notifyOnMove the new value
   */
  public void setNotifyOnMove(Boolean notifyOnMove) {
    this.notifyOnMove = notifyOnMove;
  }

  /**
   * Trigger on files created/uploaded/updated/changed in this path?
   */
  @JsonProperty("notify_on_upload")
  public Boolean notifyOnUpload;

  /**
   * Gets {@link #notifyOnUpload}.
   *
   * @return the value of {@link #notifyOnUpload}
   */
  public Boolean getNotifyOnUpload() {
    return notifyOnUpload;
  }

  /**
   * Sets {@link #notifyOnUpload}.
   *
   * @param notifyOnUpload the new value
   */
  public void setNotifyOnUpload(Boolean notifyOnUpload) {
    this.notifyOnUpload = notifyOnUpload;
  }

  /**
   * Apply notification recursively?  This will enable notifications for each subfolder.
   */
  @JsonProperty("recursive")
  public Boolean recursive;

  /**
   * Gets {@link #recursive}.
   *
   * @return the value of {@link #recursive}
   */
  public Boolean getRecursive() {
    return recursive;
  }

  /**
   * Sets {@link #recursive}.
   *
   * @param recursive the new value
   */
  public void setRecursive(Boolean recursive) {
    this.recursive = recursive;
  }

  /**
   * The time interval that notifications are aggregated to
   */
  @JsonProperty("send_interval")
  public String sendInterval;

  /**
   * Gets {@link #sendInterval}.
   *
   * @return the value of {@link #sendInterval}
   */
  public String getSendInterval() {
    return sendInterval;
  }

  /**
   * Sets {@link #sendInterval}.
   *
   * @param sendInterval the new value
   */
  public void setSendInterval(String sendInterval) {
    this.sendInterval = sendInterval;
  }

  /**
   * Custom subject line to use for notification emails
   */
  @JsonProperty("subject")
  public String subject;

  /**
   * Gets {@link #subject}.
   *
   * @return the value of {@link #subject}
   */
  public String getSubject() {
    return subject;
  }

  /**
   * Sets {@link #subject}.
   *
   * @param subject the new value
   */
  public void setSubject(String subject) {
    this.subject = subject;
  }

  /**
   * Custom message to include in notification emails
   */
  @JsonProperty("message")
  public String message;

  /**
   * Gets {@link #message}.
   *
   * @return the value of {@link #message}
   */
  public String getMessage() {
    return message;
  }

  /**
   * Sets {@link #message}.
   *
   * @param message the new value
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * Array of filenames (possibly with wildcards) to scope trigger
   */
  @JsonProperty("triggering_filenames")
  public String[] triggeringFilenames;

  /**
   * Gets {@link #triggeringFilenames}.
   *
   * @return the value of {@link #triggeringFilenames}
   */
  public String[] getTriggeringFilenames() {
    return triggeringFilenames;
  }

  /**
   * Sets {@link #triggeringFilenames}.
   *
   * @param triggeringFilenames the new value
   */
  public void setTriggeringFilenames(String[] triggeringFilenames) {
    this.triggeringFilenames = triggeringFilenames;
  }

  /**
   * Workspace ID. `0` means the default workspace.
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
   * Is the user unsubscribed from this notification?
   */
  @JsonProperty("unsubscribed")
  public Boolean unsubscribed;

  /**
   * Gets {@link #unsubscribed}.
   *
   * @return the value of {@link #unsubscribed}
   */
  public Boolean getUnsubscribed() {
    return unsubscribed;
  }

  /**
   * Sets {@link #unsubscribed}.
   *
   * @param unsubscribed the new value
   */
  public void setUnsubscribed(Boolean unsubscribed) {
    this.unsubscribed = unsubscribed;
  }

  /**
   * The reason that the user unsubscribed
   */
  @JsonProperty("unsubscribed_reason")
  public String unsubscribedReason;

  /**
   * Gets {@link #unsubscribedReason}.
   *
   * @return the value of {@link #unsubscribedReason}
   */
  public String getUnsubscribedReason() {
    return unsubscribedReason;
  }

  /**
   * Sets {@link #unsubscribedReason}.
   *
   * @param unsubscribedReason the new value
   */
  public void setUnsubscribedReason(String unsubscribedReason) {
    this.unsubscribedReason = unsubscribedReason;
  }

  /**
   * Notification user ID
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
   * Notification username
   */
  @JsonProperty("username")
  public String username;

  /**
   * Gets {@link #username}.
   *
   * @return the value of {@link #username}
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets {@link #username}.
   *
   * @param username the new value
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * If true, it means that the recipient at this user's email address has manually unsubscribed from all emails, or had their email "hard bounce", which means that we are unable to send mail to this user's current email address. Notifications will resume if the user changes their email address.
   */
  @JsonProperty("suppressed_email")
  public Boolean suppressedEmail;

  /**
   * Gets {@link #suppressedEmail}.
   *
   * @return the value of {@link #suppressedEmail}
   */
  public Boolean getSuppressedEmail() {
    return suppressedEmail;
  }

  /**
   * Sets {@link #suppressedEmail}.
   *
   * @param suppressedEmail the new value
   */
  public void setSuppressedEmail(Boolean suppressedEmail) {
    this.suppressedEmail = suppressedEmail;
  }

  /**
   * Updates this Notification. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update Notification
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code notify_on_copy} (Boolean): If `true`, copying or moving resources into this path will trigger a notification, in addition to just uploads.</li>
   * <li>{@code notify_on_delete} (Boolean): Trigger on files deleted in this path?</li>
   * <li>{@code notify_on_download} (Boolean): Trigger on files downloaded in this path?</li>
   * <li>{@code notify_on_move} (Boolean): Trigger on files moved to this path?</li>
   * <li>{@code notify_on_upload} (Boolean): Trigger on files created/uploaded/updated/changed in this path?</li>
   * <li>{@code notify_user_actions} (Boolean): If `true` actions initiated by the user will still result in a notification</li>
   * <li>{@code recursive} (Boolean): If `true`, enable notifications for each subfolder in this path</li>
   * <li>{@code send_interval} (String): The time interval that notifications are aggregated by.  Can be `five_minutes`, `fifteen_minutes`, `hourly`, or `daily`.</li>
   * <li>{@code subject} (String): Custom subject line to use for notification emails</li>
   * <li>{@code message} (String): Custom message to include in notification emails</li>
   * <li>{@code triggering_filenames} (String[]): Array of filenames (possibly with wildcards) to scope trigger</li>
   * <li>{@code triggering_group_ids} (Long[]): If set, will only notify on actions made by a member of one of the specified groups</li>
   * <li>{@code triggering_user_ids} (Long[]): If set, will only notify on actions made one of the specified users</li>
   * <li>{@code trigger_by_share_recipients} (Boolean): Notify when actions are performed by a share recipient?</li>
   * <li>{@code workspace_id} (Long): Workspace ID. `0` means the default workspace.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code Notification}
   */
  public Notification update(HashMap<String, Object> parameters) throws IOException {
    Notification updated = filesClient().notifications().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete Notification
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().notifications().delete(this.id, parameters, this.options);
  }

  /**
   * Delete Notification
   *
   * @see #delete(HashMap)
   */
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
   * List Notifications
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Notification> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Notifications
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Notification> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Notifications
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `path`, `user_id` or `group_id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `path`, `user_id`, `workspace_id` or `group_id`. Valid field combinations are `[ workspace_id, path ]`, `[ workspace_id, user_id ]`, `[ workspace_id, group_id ]` or `[ workspace_id, user_id, path ]`.</li>
   * <li>{@code filter_prefix} (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `path`.</li>
   * <li>{@code path} (String): Show notifications for this Path.</li>
   * <li>{@code include_ancestors} (Boolean): If `include_ancestors` is `true` and `path` is specified, include notifications for any parent paths. Ignored if `path` is not specified.</li>
   * <li>{@code group_id} (String): </li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Notification} records, in a {@code ListIterator}
   */
  public static ListIterator<Notification> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().notifications().list(parameters, options);
  }

  /**
   * List Notifications
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Notification> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Notifications
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Notification> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Notification
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Notification find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Notification
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Notification find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Notification
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Notification find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Notification
   *
   * @param id Notification ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Notification}
   */
  public static Notification find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().notifications().find(id, parameters, options);
  }

  /**
   * Show Notification
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Notification get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Notification
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Notification get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Notification
   *
   * @see #create(HashMap, HashMap)
   */
  public static Notification create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Notification
   *
   * @see #create(HashMap, HashMap)
   */
  public static Notification create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create Notification
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): The id of the user to notify. Provide `user_id`, `username` or `group_id`.</li>
   * <li>{@code notify_on_copy} (Boolean): If `true`, copying or moving resources into this path will trigger a notification, in addition to just uploads.</li>
   * <li>{@code notify_on_delete} (Boolean): Trigger on files deleted in this path?</li>
   * <li>{@code notify_on_download} (Boolean): Trigger on files downloaded in this path?</li>
   * <li>{@code notify_on_move} (Boolean): Trigger on files moved to this path?</li>
   * <li>{@code notify_on_upload} (Boolean): Trigger on files created/uploaded/updated/changed in this path?</li>
   * <li>{@code notify_user_actions} (Boolean): If `true` actions initiated by the user will still result in a notification</li>
   * <li>{@code recursive} (Boolean): If `true`, enable notifications for each subfolder in this path</li>
   * <li>{@code send_interval} (String): The time interval that notifications are aggregated by.  Can be `five_minutes`, `fifteen_minutes`, `hourly`, or `daily`.</li>
   * <li>{@code subject} (String): Custom subject line to use for notification emails</li>
   * <li>{@code message} (String): Custom message to include in notification emails</li>
   * <li>{@code triggering_filenames} (String[]): Array of filenames (possibly with wildcards) to scope trigger</li>
   * <li>{@code triggering_group_ids} (Long[]): If set, will only notify on actions made by a member of one of the specified groups</li>
   * <li>{@code triggering_user_ids} (Long[]): If set, will only notify on actions made one of the specified users</li>
   * <li>{@code trigger_by_share_recipients} (Boolean): Notify when actions are performed by a share recipient?</li>
   * <li>{@code workspace_id} (Long): Workspace ID. `0` means the default workspace.</li>
   * <li>{@code group_id} (Long): The ID of the group to notify.  Provide `user_id`, `username` or `group_id`.</li>
   * <li>{@code group_ids} (String): Group IDs when the notification requires multiple groups. If sent as a string, it should be comma-delimited.</li>
   * <li>{@code path} (String): Path</li>
   * <li>{@code username} (String): The username of the user to notify.  Provide `user_id`, `username` or `group_id`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Notification}
   */
  public static Notification create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().notifications().create(parameters, options);
  }


  /**
   * Update Notification
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static Notification update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Notification
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static Notification update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Notification
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static Notification update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Notification
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code notify_on_copy} (Boolean): If `true`, copying or moving resources into this path will trigger a notification, in addition to just uploads.</li>
   * <li>{@code notify_on_delete} (Boolean): Trigger on files deleted in this path?</li>
   * <li>{@code notify_on_download} (Boolean): Trigger on files downloaded in this path?</li>
   * <li>{@code notify_on_move} (Boolean): Trigger on files moved to this path?</li>
   * <li>{@code notify_on_upload} (Boolean): Trigger on files created/uploaded/updated/changed in this path?</li>
   * <li>{@code notify_user_actions} (Boolean): If `true` actions initiated by the user will still result in a notification</li>
   * <li>{@code recursive} (Boolean): If `true`, enable notifications for each subfolder in this path</li>
   * <li>{@code send_interval} (String): The time interval that notifications are aggregated by.  Can be `five_minutes`, `fifteen_minutes`, `hourly`, or `daily`.</li>
   * <li>{@code subject} (String): Custom subject line to use for notification emails</li>
   * <li>{@code message} (String): Custom message to include in notification emails</li>
   * <li>{@code triggering_filenames} (String[]): Array of filenames (possibly with wildcards) to scope trigger</li>
   * <li>{@code triggering_group_ids} (Long[]): If set, will only notify on actions made by a member of one of the specified groups</li>
   * <li>{@code triggering_user_ids} (Long[]): If set, will only notify on actions made one of the specified users</li>
   * <li>{@code trigger_by_share_recipients} (Boolean): Notify when actions are performed by a share recipient?</li>
   * <li>{@code workspace_id} (Long): Workspace ID. `0` means the default workspace.</li>
   * </ul>
   *
   * @param id Notification ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Notification}
   */
  public static Notification update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().notifications().update(id, parameters, options);
  }


  /**
   * Delete Notification
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Notification
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Notification
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Notification
   *
   * @param id Notification ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().notifications().delete(id, parameters, options);
  }

  /**
   * Delete Notification
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Notification
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
