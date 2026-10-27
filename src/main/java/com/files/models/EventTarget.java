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
public class EventTarget implements ModelInterface {
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

  // One mapper for every EventTarget, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code EventTarget} with no attributes set.
   */
  public EventTarget() {
    this(null, null);
  }

  /**
   * Creates a {@code EventTarget} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public EventTarget(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code EventTarget} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public EventTarget(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Event Target ID
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
   * Event Target name.
   */
  @JsonProperty("name")
  public String name;

  /**
   * Gets {@link #name}.
   *
   * @return the value of {@link #name}
   */
  public String getName() {
    return name;
  }

  /**
   * Sets {@link #name}.
   *
   * @param name the new value
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Event Target type.
   */
  @JsonProperty("target_type")
  public String targetType;

  /**
   * Gets {@link #targetType}.
   *
   * @return the value of {@link #targetType}
   */
  public String getTargetType() {
    return targetType;
  }

  /**
   * Sets {@link #targetType}.
   *
   * @param targetType the new value
   */
  public void setTargetType(String targetType) {
    this.targetType = targetType;
  }

  /**
   * Workspace ID. 0 means the default workspace or site-wide.
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
   * If true, this default-workspace target can receive events from all workspaces.
   */
  @JsonProperty("apply_to_all_workspaces")
  public Boolean applyToAllWorkspaces;

  /**
   * Gets {@link #applyToAllWorkspaces}.
   *
   * @return the value of {@link #applyToAllWorkspaces}
   */
  public Boolean getApplyToAllWorkspaces() {
    return applyToAllWorkspaces;
  }

  /**
   * Sets {@link #applyToAllWorkspaces}.
   *
   * @param applyToAllWorkspaces the new value
   */
  public void setApplyToAllWorkspaces(Boolean applyToAllWorkspaces) {
    this.applyToAllWorkspaces = applyToAllWorkspaces;
  }

  /**
   * Whether this Event Target can receive events.
   */
  @JsonProperty("enabled")
  public Boolean enabled;

  /**
   * Gets {@link #enabled}.
   *
   * @return the value of {@link #enabled}
   */
  public Boolean getEnabled() {
    return enabled;
  }

  /**
   * Sets {@link #enabled}.
   *
   * @param enabled the new value
   */
  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  /**
   * Event Target configuration. Fields depend on target_type.
   */
  @JsonProperty("config")
  public Object config;

  /**
   * Gets {@link #config}.
   *
   * @return the value of {@link #config}
   */
  public Object getConfig() {
    return config;
  }

  /**
   * Sets {@link #config}.
   *
   * @param config the new value
   */
  public void setConfig(Object config) {
    this.config = config;
  }

  /**
   * Event Target delivery policy. Email and folder targets support batch_interval in seconds, between 600 and 86400. Slack and Teams support delivery_mode (immediate or subfolder_summary), summary_path (empty string means root), and summary_delay (15–300 seconds, default 60). The summary applies to matching file activity under summary_path; other matching events retain immediate delivery.
   */
  @JsonProperty("delivery_policy")
  public Object deliveryPolicy;

  /**
   * Gets {@link #deliveryPolicy}.
   *
   * @return the value of {@link #deliveryPolicy}
   */
  public Object getDeliveryPolicy() {
    return deliveryPolicy;
  }

  /**
   * Sets {@link #deliveryPolicy}.
   *
   * @param deliveryPolicy the new value
   */
  public void setDeliveryPolicy(Object deliveryPolicy) {
    this.deliveryPolicy = deliveryPolicy;
  }

  /**
   * Event Target create date/time.
   */
  @JsonProperty("created_at")
  public Date createdAt;

  /**
   * Gets {@link #createdAt}.
   *
   * @return the value of {@link #createdAt}
   */
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * Event Target update date/time.
   */
  @JsonProperty("updated_at")
  public Date updatedAt;

  /**
   * Gets {@link #updatedAt}.
   *
   * @return the value of {@link #updatedAt}
   */
  public Date getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Updates this EventTarget. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update Event Target
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Event Target name.</li>
   * <li>{@code workspace_id} (Long): Workspace ID. 0 means the default workspace or site-wide.</li>
   * <li>{@code apply_to_all_workspaces} (Boolean): If true, this default-workspace target can receive events from all workspaces.</li>
   * <li>{@code enabled} (Boolean): Whether this Event Target can receive events.</li>
   * <li>{@code config} (Object): Event Target configuration. Fields depend on target_type.</li>
   * <li>{@code delivery_policy} (Object): Event Target delivery policy. Email and folder targets support batch_interval in seconds, between 600 and 86400. Slack and Teams support delivery_mode (immediate or subfolder_summary), summary_path (empty string means root), and summary_delay (15–300 seconds, default 60). The summary applies to matching file activity under summary_path; other matching events retain immediate delivery.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code EventTarget}
   */
  public EventTarget update(HashMap<String, Object> parameters) throws IOException {
    EventTarget updated = filesClient().eventTargets().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete Event Target
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().eventTargets().delete(this.id, parameters, this.options);
  }

  /**
   * Delete Event Target
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this EventTarget: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().eventTargets().create(parameters, this.options));
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
  private void refreshFrom(EventTarget canonical) {
    this.id = canonical.id;
    this.name = canonical.name;
    this.targetType = canonical.targetType;
    this.workspaceId = canonical.workspaceId;
    this.applyToAllWorkspaces = canonical.applyToAllWorkspaces;
    this.enabled = canonical.enabled;
    this.config = canonical.config;
    this.deliveryPolicy = canonical.deliveryPolicy;
    this.createdAt = canonical.createdAt;
    this.updatedAt = canonical.updatedAt;
  }

  /**
   * List Event Targets
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<EventTarget> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Event Targets
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<EventTarget> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Event Targets
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `name`, `enabled` or `workspace_id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `enabled`, `target_type` or `workspace_id`. Valid field combinations are `[ enabled, target_type ]`, `[ workspace_id, enabled ]` or `[ workspace_id, enabled, target_type ]`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code EventTarget} records, in a {@code ListIterator}
   */
  public static ListIterator<EventTarget> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().eventTargets().list(parameters, options);
  }

  /**
   * List Event Targets
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<EventTarget> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Event Targets
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<EventTarget> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Event Target
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static EventTarget find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Event Target
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static EventTarget find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Event Target
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static EventTarget find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Event Target
   *
   * @param id Event Target ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code EventTarget}
   */
  public static EventTarget find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().eventTargets().find(id, parameters, options);
  }

  /**
   * Show Event Target
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static EventTarget get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Event Target
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static EventTarget get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Event Target
   *
   * @see #create(HashMap, HashMap)
   */
  public static EventTarget create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Event Target
   *
   * @see #create(HashMap, HashMap)
   */
  public static EventTarget create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create Event Target
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Required. Event Target name.</li>
   * <li>{@code workspace_id} (Long): Workspace ID. 0 means the default workspace or site-wide.</li>
   * <li>{@code apply_to_all_workspaces} (Boolean): If true, this default-workspace target can receive events from all workspaces.</li>
   * <li>{@code enabled} (Boolean): Whether this Event Target can receive events.</li>
   * <li>{@code config} (Object): Required. Event Target configuration. Fields depend on target_type.</li>
   * <li>{@code delivery_policy} (Object): Event Target delivery policy. Email and folder targets support batch_interval in seconds, between 600 and 86400. Slack and Teams support delivery_mode (immediate or subfolder_summary), summary_path (empty string means root), and summary_delay (15–300 seconds, default 60). The summary applies to matching file activity under summary_path; other matching events retain immediate delivery.</li>
   * <li>{@code target_type} (String): Required. Event Target type.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code EventTarget}
   */
  public static EventTarget create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().eventTargets().create(parameters, options);
  }


  /**
   * Update Event Target
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static EventTarget update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Event Target
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static EventTarget update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Event Target
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static EventTarget update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Event Target
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Event Target name.</li>
   * <li>{@code workspace_id} (Long): Workspace ID. 0 means the default workspace or site-wide.</li>
   * <li>{@code apply_to_all_workspaces} (Boolean): If true, this default-workspace target can receive events from all workspaces.</li>
   * <li>{@code enabled} (Boolean): Whether this Event Target can receive events.</li>
   * <li>{@code config} (Object): Event Target configuration. Fields depend on target_type.</li>
   * <li>{@code delivery_policy} (Object): Event Target delivery policy. Email and folder targets support batch_interval in seconds, between 600 and 86400. Slack and Teams support delivery_mode (immediate or subfolder_summary), summary_path (empty string means root), and summary_delay (15–300 seconds, default 60). The summary applies to matching file activity under summary_path; other matching events retain immediate delivery.</li>
   * </ul>
   *
   * @param id Event Target ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code EventTarget}
   */
  public static EventTarget update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().eventTargets().update(id, parameters, options);
  }


  /**
   * Delete Event Target
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Event Target
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Event Target
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Event Target
   *
   * @param id Event Target ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().eventTargets().delete(id, parameters, options);
  }

  /**
   * Delete Event Target
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Event Target
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
