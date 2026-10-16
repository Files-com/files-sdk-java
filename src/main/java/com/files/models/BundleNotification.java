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
public class BundleNotification implements ModelInterface {
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

  // One mapper for every BundleNotification, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  public BundleNotification() {
    this(null, null);
  }

  public BundleNotification(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public BundleNotification(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Bundle ID to notify on
   */
  @JsonProperty("bundle_id")
  public Long bundleId;

  public Long getBundleId() {
    return bundleId;
  }

  public void setBundleId(Long bundleId) {
    this.bundleId = bundleId;
  }

  /**
   * Bundle Notification ID
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
   * Triggers bundle notification when a registration action occurs for it.
   */
  @JsonProperty("notify_on_registration")
  public Boolean notifyOnRegistration;

  public Boolean getNotifyOnRegistration() {
    return notifyOnRegistration;
  }

  public void setNotifyOnRegistration(Boolean notifyOnRegistration) {
    this.notifyOnRegistration = notifyOnRegistration;
  }

  /**
   * Triggers bundle notification when a upload action occurs for it.
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
   * Is the current user the user to notify?
   */
  @JsonProperty("notify_current_user")
  public Boolean notifyCurrentUser;

  public Boolean getNotifyCurrentUser() {
    return notifyCurrentUser;
  }

  public void setNotifyCurrentUser(Boolean notifyCurrentUser) {
    this.notifyCurrentUser = notifyCurrentUser;
  }

  /**
   * The id of the user to notify.
   */
  @JsonProperty("notify_user_id")
  public Long notifyUserId;

  public Long getNotifyUserId() {
    return notifyUserId;
  }

  public void setNotifyUserId(Long notifyUserId) {
    this.notifyUserId = notifyUserId;
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
   * Updates this BundleNotification. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Parameters:
   *   notify_on_registration - boolean - Triggers bundle notification when a registration action occurs for it.
   *   notify_on_upload - boolean - Triggers bundle notification when a upload action occurs for it.
   */
  public BundleNotification update(HashMap<String, Object> parameters) throws IOException {
    BundleNotification updated = filesClient().bundleNotifications().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().bundleNotifications().delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this BundleNotification: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().bundleNotifications().create(parameters, this.options));
    }
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    return ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(BundleNotification canonical) {
    this.bundleId = canonical.bundleId;
    this.id = canonical.id;
    this.notifyOnRegistration = canonical.notifyOnRegistration;
    this.notifyOnUpload = canonical.notifyOnUpload;
    this.notifyCurrentUser = canonical.notifyCurrentUser;
    this.notifyUserId = canonical.notifyUserId;
    this.workspaceId = canonical.workspaceId;
    this.userId = canonical.userId;
  }

  /**
   * Parameters:
   *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id` and `bundle_id`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `bundle_id`.
   *   bundle_id - int64 - Bundle ID
   */
  public static ListIterator<BundleNotification> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<BundleNotification> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<BundleNotification> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().bundleNotifications().list(parameters, options);
  }

  public static ListIterator<BundleNotification> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<BundleNotification> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Bundle Notification ID.
   */
  public static BundleNotification find() throws RuntimeException {
    return find(null, null, null);
  }

  public static BundleNotification find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static BundleNotification find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static BundleNotification find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().bundleNotifications().find(id, parameters, options);
  }

  public static BundleNotification get() throws RuntimeException {
    return get(null, null, null);
  }

  public static BundleNotification get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
   *   bundle_id (required) - int64 - Bundle ID to notify on
   *   notify_user_id - int64 - The id of the user to notify.
   *   notify_on_registration - boolean - Triggers bundle notification when a registration action occurs for it.
   *   notify_on_upload - boolean - Triggers bundle notification when a upload action occurs for it.
   */
  public static BundleNotification create() throws RuntimeException {
    return create(null, null);
  }

  public static BundleNotification create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static BundleNotification create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().bundleNotifications().create(parameters, options);
  }


  /**
   * Parameters:
   *   notify_on_registration - boolean - Triggers bundle notification when a registration action occurs for it.
   *   notify_on_upload - boolean - Triggers bundle notification when a upload action occurs for it.
   */
  public static BundleNotification update() throws RuntimeException {
    return update(null, null, null);
  }

  public static BundleNotification update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static BundleNotification update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static BundleNotification update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().bundleNotifications().update(id, parameters, options);
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
    FilesClient.defaultClient().bundleNotifications().delete(id, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
