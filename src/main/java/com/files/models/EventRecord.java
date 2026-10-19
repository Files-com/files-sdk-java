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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventRecord implements ModelInterface {
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

  // One mapper for every EventRecord, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code EventRecord} with no attributes set.
   */
  public EventRecord() {
    this(null, null);
  }

  /**
   * Creates a {@code EventRecord} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public EventRecord(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code EventRecord} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public EventRecord(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Event Record ID
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
   * Stable event UUID.
   */
  @JsonProperty("event_uuid")
  public String eventUuid;

  /**
   * Gets {@link #eventUuid}.
   *
   * @return the value of {@link #eventUuid}
   */
  public String getEventUuid() {
    return eventUuid;
  }

  /**
   * Versioned event type string. Valid values: `automation_run.canceled.v1`, `automation_run.failure.v1`, `automation_run.failure_will_retry.v1`, `automation_run.partial_failure.v1`, `automation_run.partial_failure_will_retry.v1`, `automation_run.skipped.v1`, `automation_run.success.v1`, `expectation_evaluation.invalid.v1`, `expectation_evaluation.late.v1`, `expectation_evaluation.missing.v1`, `expectation_evaluation.success.v1`, `expectation_incident.acknowledged.v1`, `expectation_incident.open.v1`, `expectation_incident.resolved.v1`, `expectation_incident.snoozed.v1`, `external_event.client_log.failure.v1`, `external_event.client_log.partial_failure.v1`, `external_event.client_log.skipped.v1`, `external_event.client_log.success.v1`, `pending_work_event.failure.v1`, `pending_work_event.partial_failure.v1`, `pending_work_event.skipped.v1`, `pending_work_event.success.v1`, `siem_http_destination_event.failure.v1`, `siem_http_destination_event.partial_failure.v1`, `siem_http_destination_event.skipped.v1`, `siem_http_destination_event.success.v1`, `sso_event.ldap_login.failure.v1`, `sso_event.ldap_login.partial_failure.v1`, `sso_event.ldap_login.skipped.v1`, `sso_event.ldap_login.success.v1`, `sso_event.ldap_sync.failure.v1`, `sso_event.ldap_sync.partial_failure.v1`, `sso_event.ldap_sync.skipped.v1`, `sso_event.ldap_sync.success.v1`, `sso_event.saml_login.failure.v1`, `sso_event.saml_login.partial_failure.v1`, `sso_event.saml_login.skipped.v1`, `sso_event.saml_login.success.v1`, `sync_run.failure.v1`, `sync_run.partial_failure.v1`, `sync_run.skipped.v1`, `sync_run.success.v1`, `user_security_event.lockout.v1`
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
   * Event severity.
   */
  @JsonProperty("severity")
  public String severity;

  /**
   * Gets {@link #severity}.
   *
   * @return the value of {@link #severity}
   */
  public String getSeverity() {
    return severity;
  }

  /**
   * Source record type.
   */
  @JsonProperty("source_type")
  public String sourceType;

  /**
   * Gets {@link #sourceType}.
   *
   * @return the value of {@link #sourceType}
   */
  public String getSourceType() {
    return sourceType;
  }

  /**
   * Source record ID.
   */
  @JsonProperty("source_id")
  public Long sourceId;

  /**
   * Gets {@link #sourceId}.
   *
   * @return the value of {@link #sourceId}
   */
  public Long getSourceId() {
    return sourceId;
  }

  /**
   * Event occurrence date/time.
   */
  @JsonProperty("occurred_at")
  public Date occurredAt;

  /**
   * Gets {@link #occurredAt}.
   *
   * @return the value of {@link #occurredAt}
   */
  public Date getOccurredAt() {
    return occurredAt;
  }

  /**
   * Human-readable event title.
   */
  @JsonProperty("human_title")
  public String humanTitle;

  /**
   * Gets {@link #humanTitle}.
   *
   * @return the value of {@link #humanTitle}
   */
  public String getHumanTitle() {
    return humanTitle;
  }

  /**
   * Human-readable event summary.
   */
  @JsonProperty("human_summary")
  public String humanSummary;

  /**
   * Gets {@link #humanSummary}.
   *
   * @return the value of {@link #humanSummary}
   */
  public String getHumanSummary() {
    return humanSummary;
  }

  /**
   * Human-readable event detail fields.
   */
  @JsonProperty("human_fields")
  public Object[] humanFields;

  /**
   * Gets {@link #humanFields}.
   *
   * @return the value of {@link #humanFields}
   */
  public Object[] getHumanFields() {
    return humanFields;
  }

  /**
   * Actor associated with the event.
   */
  @JsonProperty("actor")
  public Object actor;

  /**
   * Gets {@link #actor}.
   *
   * @return the value of {@link #actor}
   */
  public Object getActor() {
    return actor;
  }

  /**
   * Resources associated with the event.
   */
  @JsonProperty("resources")
  public Object[] resources;

  /**
   * Gets {@link #resources}.
   *
   * @return the value of {@link #resources}
   */
  public Object[] getResources() {
    return resources;
  }

  /**
   * Event payload.
   */
  @JsonProperty("payload")
  public Object payload;

  /**
   * Gets {@link #payload}.
   *
   * @return the value of {@link #payload}
   */
  public Object getPayload() {
    return payload;
  }

  /**
   * Event Record create date/time.
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
   * List Event Records
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<EventRecord> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Event Records
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<EventRecord> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Event Records
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `event_type`, `created_at` or `workspace_id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`, `event_type` or `workspace_id`. Valid field combinations are `[ event_type, created_at ]`, `[ workspace_id, created_at ]`, `[ workspace_id, event_type ]` or `[ workspace_id, event_type, created_at ]`.</li>
   * <li>{@code filter_gt} (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_gteq} (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_prefix} (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `event_type`.</li>
   * <li>{@code filter_lt} (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_lteq} (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code EventRecord} records, in a {@code ListIterator}
   */
  public static ListIterator<EventRecord> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().eventRecords().list(parameters, options);
  }

  /**
   * List Event Records
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<EventRecord> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Event Records
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<EventRecord> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Event Record
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static EventRecord find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Event Record
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static EventRecord find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Event Record
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static EventRecord find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Event Record
   *
   * @param id Event Record ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code EventRecord}
   */
  public static EventRecord find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().eventRecords().find(id, parameters, options);
  }

  /**
   * Show Event Record
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static EventRecord get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Event Record
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static EventRecord get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

}
