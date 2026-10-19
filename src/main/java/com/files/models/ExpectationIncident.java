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
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExpectationIncident implements ModelInterface {
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

  // One mapper for every ExpectationIncident, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code ExpectationIncident} with no attributes set.
   */
  public ExpectationIncident() {
    this(null, null);
  }

  /**
   * Creates a {@code ExpectationIncident} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public ExpectationIncident(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code ExpectationIncident} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public ExpectationIncident(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Expectation Incident ID
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
   * Expectation ID.
   */
  @JsonProperty("expectation_id")
  public Long expectationId;

  /**
   * Gets {@link #expectationId}.
   *
   * @return the value of {@link #expectationId}
   */
  public Long getExpectationId() {
    return expectationId;
  }

  /**
   * Incident status.
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
   * When the incident was opened.
   */
  @JsonProperty("opened_at")
  public Date openedAt;

  /**
   * Gets {@link #openedAt}.
   *
   * @return the value of {@link #openedAt}
   */
  public Date getOpenedAt() {
    return openedAt;
  }

  /**
   * When the most recent failing evaluation contributing to the incident occurred.
   */
  @JsonProperty("last_failed_at")
  public Date lastFailedAt;

  /**
   * Gets {@link #lastFailedAt}.
   *
   * @return the value of {@link #lastFailedAt}
   */
  public Date getLastFailedAt() {
    return lastFailedAt;
  }

  /**
   * When the incident was acknowledged.
   */
  @JsonProperty("acknowledged_at")
  public Date acknowledgedAt;

  /**
   * Gets {@link #acknowledgedAt}.
   *
   * @return the value of {@link #acknowledgedAt}
   */
  public Date getAcknowledgedAt() {
    return acknowledgedAt;
  }

  /**
   * When the current snooze expires.
   */
  @JsonProperty("snoozed_until")
  public Date snoozedUntil;

  /**
   * Gets {@link #snoozedUntil}.
   *
   * @return the value of {@link #snoozedUntil}
   */
  public Date getSnoozedUntil() {
    return snoozedUntil;
  }

  /**
   * When the incident was resolved.
   */
  @JsonProperty("resolved_at")
  public Date resolvedAt;

  /**
   * Gets {@link #resolvedAt}.
   *
   * @return the value of {@link #resolvedAt}
   */
  public Date getResolvedAt() {
    return resolvedAt;
  }

  /**
   * Evaluation that first opened the incident.
   */
  @JsonProperty("opened_by_evaluation_id")
  public Long openedByEvaluationId;

  /**
   * Gets {@link #openedByEvaluationId}.
   *
   * @return the value of {@link #openedByEvaluationId}
   */
  public Long getOpenedByEvaluationId() {
    return openedByEvaluationId;
  }

  /**
   * Most recent evaluation linked to the incident.
   */
  @JsonProperty("last_evaluation_id")
  public Long lastEvaluationId;

  /**
   * Gets {@link #lastEvaluationId}.
   *
   * @return the value of {@link #lastEvaluationId}
   */
  public Long getLastEvaluationId() {
    return lastEvaluationId;
  }

  /**
   * Evaluation that resolved the incident.
   */
  @JsonProperty("resolved_by_evaluation_id")
  public Long resolvedByEvaluationId;

  /**
   * Gets {@link #resolvedByEvaluationId}.
   *
   * @return the value of {@link #resolvedByEvaluationId}
   */
  public Long getResolvedByEvaluationId() {
    return resolvedByEvaluationId;
  }

  /**
   * Compact incident summary payload.
   */
  @JsonProperty("summary")
  public Object summary;

  /**
   * Gets {@link #summary}.
   *
   * @return the value of {@link #summary}
   */
  public Object getSummary() {
    return summary;
  }

  /**
   * Creation time.
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
   * Last update time.
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
   * Resolve an expectation incident
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code ExpectationIncident}
   */
  public ExpectationIncident resolve(HashMap<String, Object> parameters) throws IOException {
    return filesClient().expectationIncidents().resolve(this.id, parameters, this.options);
  }

  /**
   * Snooze an expectation incident until a specified time
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code snoozed_until} (String): Required. Time until which the incident should remain snoozed.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code ExpectationIncident}
   */
  public ExpectationIncident snooze(HashMap<String, Object> parameters) throws IOException {
    return filesClient().expectationIncidents().snooze(this.id, parameters, this.options);
  }

  /**
   * Acknowledge an expectation incident
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code ExpectationIncident}
   */
  public ExpectationIncident acknowledge(HashMap<String, Object> parameters) throws IOException {
    return filesClient().expectationIncidents().acknowledge(this.id, parameters, this.options);
  }


  /**
   * List Expectation Incidents
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ExpectationIncident> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Expectation Incidents
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ExpectationIncident> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Expectation Incidents
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `created_at` or `expectation_id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `expectation_id` and `workspace_id`. Valid field combinations are `[ workspace_id, expectation_id ]`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ExpectationIncident} records, in a {@code ListIterator}
   */
  public static ListIterator<ExpectationIncident> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().expectationIncidents().list(parameters, options);
  }

  /**
   * List Expectation Incidents
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ExpectationIncident> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Expectation Incidents
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ExpectationIncident> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Expectation Incident
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ExpectationIncident find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Expectation Incident
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ExpectationIncident find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Expectation Incident
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ExpectationIncident find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Expectation Incident
   *
   * @param id Expectation Incident ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ExpectationIncident}
   */
  public static ExpectationIncident find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().expectationIncidents().find(id, parameters, options);
  }

  /**
   * Show Expectation Incident
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ExpectationIncident get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Expectation Incident
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ExpectationIncident get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Resolve an expectation incident
   *
   * @see #resolve(Long, HashMap, HashMap)
   */
  public static ExpectationIncident resolve() throws RuntimeException {
    return resolve(null, null, null);
  }

  /**
   * Resolve an expectation incident
   *
   * @see #resolve(Long, HashMap, HashMap)
   */
  public static ExpectationIncident resolve(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return resolve(id, parameters, null);
  }

  /**
   * Resolve an expectation incident
   *
   * @see #resolve(Long, HashMap, HashMap)
   */
  public static ExpectationIncident resolve(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return resolve(null, parameters, options);
  }

  /**
   * Resolve an expectation incident
   *
   * @param id Expectation Incident ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ExpectationIncident}
   */
  public static ExpectationIncident resolve(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().expectationIncidents().resolve(id, parameters, options);
  }


  /**
   * Snooze an expectation incident until a specified time
   *
   * @see #snooze(Long, HashMap, HashMap)
   */
  public static ExpectationIncident snooze() throws RuntimeException {
    return snooze(null, null, null);
  }

  /**
   * Snooze an expectation incident until a specified time
   *
   * @see #snooze(Long, HashMap, HashMap)
   */
  public static ExpectationIncident snooze(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return snooze(id, parameters, null);
  }

  /**
   * Snooze an expectation incident until a specified time
   *
   * @see #snooze(Long, HashMap, HashMap)
   */
  public static ExpectationIncident snooze(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return snooze(null, parameters, options);
  }

  /**
   * Snooze an expectation incident until a specified time
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code snoozed_until} (String): Required. Time until which the incident should remain snoozed.</li>
   * </ul>
   *
   * @param id Expectation Incident ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ExpectationIncident}
   */
  public static ExpectationIncident snooze(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().expectationIncidents().snooze(id, parameters, options);
  }


  /**
   * Acknowledge an expectation incident
   *
   * @see #acknowledge(Long, HashMap, HashMap)
   */
  public static ExpectationIncident acknowledge() throws RuntimeException {
    return acknowledge(null, null, null);
  }

  /**
   * Acknowledge an expectation incident
   *
   * @see #acknowledge(Long, HashMap, HashMap)
   */
  public static ExpectationIncident acknowledge(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return acknowledge(id, parameters, null);
  }

  /**
   * Acknowledge an expectation incident
   *
   * @see #acknowledge(Long, HashMap, HashMap)
   */
  public static ExpectationIncident acknowledge(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return acknowledge(null, parameters, options);
  }

  /**
   * Acknowledge an expectation incident
   *
   * @param id Expectation Incident ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ExpectationIncident}
   */
  public static ExpectationIncident acknowledge(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().expectationIncidents().acknowledge(id, parameters, options);
  }


}
