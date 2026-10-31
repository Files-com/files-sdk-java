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
public class ExternalEvent implements ModelInterface {
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

  // One mapper for every ExternalEvent, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code ExternalEvent} with no attributes set.
   */
  public ExternalEvent() {
    this(null, null);
  }

  /**
   * Creates a {@code ExternalEvent} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public ExternalEvent(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code ExternalEvent} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public ExternalEvent(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Event ID
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
   * Workspace ID
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
   * Type of event being recorded.
   */
  @JsonProperty("event_type")
  public String eventType;

  /**
   * Gets {@link #eventType}.
   *
   * @return the value of {@link #eventType}
   */
  public String getEventType() {
    return eventType;
  }

  /**
   * Sets {@link #eventType}.
   *
   * @param eventType the new value
   */
  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  /**
   * Status of event.
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
   * Event body
   */
  @JsonProperty("body")
  public String body;

  /**
   * Gets {@link #body}.
   *
   * @return the value of {@link #body}
   */
  public String getBody() {
    return body;
  }

  /**
   * Sets {@link #body}.
   *
   * @param body the new value
   */
  public void setBody(String body) {
    this.body = body;
  }

  /**
   * External event create date/time
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
   * Link to log file.
   */
  @JsonProperty("body_url")
  public String bodyUrl;

  /**
   * Gets {@link #bodyUrl}.
   *
   * @return the value of {@link #bodyUrl}
   */
  public String getBodyUrl() {
    return bodyUrl;
  }

  /**
   * Sets {@link #bodyUrl}.
   *
   * @param bodyUrl the new value
   */
  public void setBodyUrl(String bodyUrl) {
    this.bodyUrl = bodyUrl;
  }


  /**
   * Creates this ExternalEvent.
   * On success, this object's attributes are replaced by the server's response.
   * ExternalEvents cannot be updated, so saving one that already has an id throws
   * {@link UnsupportedOperationException}.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      throw new UnsupportedOperationException("The ExternalEvent Object doesn't support updates.");
    }
    refreshFrom(filesClient().externalEvents().create(parameters, this.options));
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    return ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(ExternalEvent canonical) {
    this.id = canonical.id;
    this.workspaceId = canonical.workspaceId;
    this.eventType = canonical.eventType;
    this.status = canonical.status;
    this.body = canonical.body;
    this.createdAt = canonical.createdAt;
    this.bodyUrl = canonical.bodyUrl;
  }

  /**
   * List External Events
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ExternalEvent> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List External Events
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ExternalEvent> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List External Events
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`, `status`, `event_type` or `workspace_id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`, `status` or `workspace_id`. Valid field combinations are `[ status, created_at ]`.</li>
   * <li>{@code filter_gt} (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_gteq} (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_lt} (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_lteq} (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ExternalEvent} records, in a {@code ListIterator}
   */
  public static ListIterator<ExternalEvent> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().externalEvents().list(parameters, options);
  }

  /**
   * List External Events
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ExternalEvent> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List External Events
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ExternalEvent> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show External Event
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ExternalEvent find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show External Event
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ExternalEvent find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show External Event
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ExternalEvent find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show External Event
   *
   * @param id External Event ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ExternalEvent}
   */
  public static ExternalEvent find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().externalEvents().find(id, parameters, options);
  }

  /**
   * Show External Event
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ExternalEvent get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show External Event
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ExternalEvent get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create External Event
   *
   * @see #create(HashMap, HashMap)
   */
  public static ExternalEvent create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create External Event
   *
   * @see #create(HashMap, HashMap)
   */
  public static ExternalEvent create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create External Event
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code status} (String): Required. Status of event.</li>
   * <li>{@code body} (String): Required. Event body</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ExternalEvent}
   */
  public static ExternalEvent create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().externalEvents().create(parameters, options);
  }


}
