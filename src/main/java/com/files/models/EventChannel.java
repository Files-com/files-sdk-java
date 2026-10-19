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
public class EventChannel implements ModelInterface {
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

  // One mapper for every EventChannel, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code EventChannel} with no attributes set.
   */
  public EventChannel() {
    this(null, null);
  }

  /**
   * Creates a {@code EventChannel} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public EventChannel(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code EventChannel} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public EventChannel(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Event Channel ID
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
   * Event Channel name.
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
   * Workspace ID. 0 means the default workspace.
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
   * Event Channel description.
   */
  @JsonProperty("description")
  public String description;

  /**
   * Gets {@link #description}.
   *
   * @return the value of {@link #description}
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets {@link #description}.
   *
   * @param description the new value
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Whether this Event Channel can dispatch events.
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
   * Whether this Event Channel is the default destination for newly published events.
   */
  @JsonProperty("default_channel")
  public Boolean defaultChannel;

  /**
   * Gets {@link #defaultChannel}.
   *
   * @return the value of {@link #defaultChannel}
   */
  public Boolean getDefaultChannel() {
    return defaultChannel;
  }

  /**
   * Sets {@link #defaultChannel}.
   *
   * @param defaultChannel the new value
   */
  public void setDefaultChannel(Boolean defaultChannel) {
    this.defaultChannel = defaultChannel;
  }

  /**
   * Event Channel create date/time.
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
   * Event Channel update date/time.
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
   * Updates this EventChannel. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update Event Channel
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Event Channel name.</li>
   * <li>{@code workspace_id} (Long): Workspace ID. 0 means the default workspace.</li>
   * <li>{@code description} (String): Event Channel description.</li>
   * <li>{@code enabled} (Boolean): Whether this Event Channel can dispatch events.</li>
   * <li>{@code default_channel} (Boolean): Whether this Event Channel is the default destination for newly published events.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code EventChannel}
   */
  public EventChannel update(HashMap<String, Object> parameters) throws IOException {
    EventChannel updated = filesClient().eventChannels().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete Event Channel
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().eventChannels().delete(this.id, parameters, this.options);
  }

  /**
   * Delete Event Channel
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this EventChannel: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().eventChannels().create(parameters, this.options));
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
  private void refreshFrom(EventChannel canonical) {
    this.id = canonical.id;
    this.name = canonical.name;
    this.workspaceId = canonical.workspaceId;
    this.description = canonical.description;
    this.enabled = canonical.enabled;
    this.defaultChannel = canonical.defaultChannel;
    this.createdAt = canonical.createdAt;
    this.updatedAt = canonical.updatedAt;
  }

  /**
   * List Event Channels
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<EventChannel> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Event Channels
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<EventChannel> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Event Channels
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `name`, `enabled`, `default_channel` or `workspace_id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `enabled`, `default_channel` or `workspace_id`. Valid field combinations are `[ workspace_id, enabled ]` and `[ workspace_id, default_channel ]`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code EventChannel} records, in a {@code ListIterator}
   */
  public static ListIterator<EventChannel> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().eventChannels().list(parameters, options);
  }

  /**
   * List Event Channels
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<EventChannel> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Event Channels
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<EventChannel> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Event Channel
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static EventChannel find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Event Channel
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static EventChannel find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Event Channel
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static EventChannel find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Event Channel
   *
   * @param id Event Channel ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code EventChannel}
   */
  public static EventChannel find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().eventChannels().find(id, parameters, options);
  }

  /**
   * Show Event Channel
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static EventChannel get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Event Channel
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static EventChannel get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Event Channel
   *
   * @see #create(HashMap, HashMap)
   */
  public static EventChannel create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Event Channel
   *
   * @see #create(HashMap, HashMap)
   */
  public static EventChannel create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create Event Channel
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Required. Event Channel name.</li>
   * <li>{@code workspace_id} (Long): Workspace ID. 0 means the default workspace.</li>
   * <li>{@code description} (String): Event Channel description.</li>
   * <li>{@code enabled} (Boolean): Whether this Event Channel can dispatch events.</li>
   * <li>{@code default_channel} (Boolean): Whether this Event Channel is the default destination for newly published events.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code EventChannel}
   */
  public static EventChannel create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().eventChannels().create(parameters, options);
  }


  /**
   * Update Event Channel
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static EventChannel update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Event Channel
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static EventChannel update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Event Channel
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static EventChannel update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Event Channel
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Event Channel name.</li>
   * <li>{@code workspace_id} (Long): Workspace ID. 0 means the default workspace.</li>
   * <li>{@code description} (String): Event Channel description.</li>
   * <li>{@code enabled} (Boolean): Whether this Event Channel can dispatch events.</li>
   * <li>{@code default_channel} (Boolean): Whether this Event Channel is the default destination for newly published events.</li>
   * </ul>
   *
   * @param id Event Channel ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code EventChannel}
   */
  public static EventChannel update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().eventChannels().update(id, parameters, options);
  }


  /**
   * Delete Event Channel
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Event Channel
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Event Channel
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Event Channel
   *
   * @param id Event Channel ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().eventChannels().delete(id, parameters, options);
  }

  /**
   * Delete Event Channel
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Event Channel
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
