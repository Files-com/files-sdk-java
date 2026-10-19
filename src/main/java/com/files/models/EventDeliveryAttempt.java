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
public class EventDeliveryAttempt implements ModelInterface {
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

  // One mapper for every EventDeliveryAttempt, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code EventDeliveryAttempt} with no attributes set.
   */
  public EventDeliveryAttempt() {
    this(null, null);
  }

  /**
   * Creates a {@code EventDeliveryAttempt} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public EventDeliveryAttempt(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code EventDeliveryAttempt} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public EventDeliveryAttempt(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Event Delivery Attempt ID
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
   * Event Record ID
   */
  @JsonProperty("event_record_id")
  public Long eventRecordId;

  /**
   * Gets {@link #eventRecordId}.
   *
   * @return the value of {@link #eventRecordId}
   */
  public Long getEventRecordId() {
    return eventRecordId;
  }

  /**
   * Event Subscription ID
   */
  @JsonProperty("event_subscription_id")
  public Long eventSubscriptionId;

  /**
   * Gets {@link #eventSubscriptionId}.
   *
   * @return the value of {@link #eventSubscriptionId}
   */
  public Long getEventSubscriptionId() {
    return eventSubscriptionId;
  }

  /**
   * Event Target ID
   */
  @JsonProperty("event_target_id")
  public Long eventTargetId;

  /**
   * Gets {@link #eventTargetId}.
   *
   * @return the value of {@link #eventTargetId}
   */
  public Long getEventTargetId() {
    return eventTargetId;
  }

  /**
   * Shared summary ID when this event is delivered in a Slack or Teams subfolder summary. All events in a summary share its delivery outcome.
   */
  @JsonProperty("activity_notification_batch_id")
  public Long activityNotificationBatchId;

  /**
   * Gets {@link #activityNotificationBatchId}.
   *
   * @return the value of {@link #activityNotificationBatchId}
   */
  public Long getActivityNotificationBatchId() {
    return activityNotificationBatchId;
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
   * Delivery status.
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
   * Number of delivery attempts made.
   */
  @JsonProperty("attempt_number")
  public Long attemptNumber;

  /**
   * Gets {@link #attemptNumber}.
   *
   * @return the value of {@link #attemptNumber}
   */
  public Long getAttemptNumber() {
    return attemptNumber;
  }

  /**
   * HTTP response code, if applicable.
   */
  @JsonProperty("response_code")
  public Long responseCode;

  /**
   * Gets {@link #responseCode}.
   *
   * @return the value of {@link #responseCode}
   */
  public Long getResponseCode() {
    return responseCode;
  }

  /**
   * Delivery error message, if applicable.
   */
  @JsonProperty("error_message")
  public String errorMessage;

  /**
   * Gets {@link #errorMessage}.
   *
   * @return the value of {@link #errorMessage}
   */
  public String getErrorMessage() {
    return errorMessage;
  }

  /**
   * Delivery response body, if applicable.
   */
  @JsonProperty("response_body")
  public String responseBody;

  /**
   * Gets {@link #responseBody}.
   *
   * @return the value of {@link #responseBody}
   */
  public String getResponseBody() {
    return responseBody;
  }

  /**
   * Delivery latency in milliseconds.
   */
  @JsonProperty("latency_ms")
  public Long latencyMs;

  /**
   * Gets {@link #latencyMs}.
   *
   * @return the value of {@link #latencyMs}
   */
  public Long getLatencyMs() {
    return latencyMs;
  }

  /**
   * Successful delivery date/time.
   */
  @JsonProperty("delivered_at")
  public Date deliveredAt;

  /**
   * Gets {@link #deliveredAt}.
   *
   * @return the value of {@link #deliveredAt}
   */
  public Date getDeliveredAt() {
    return deliveredAt;
  }

  /**
   * Most recent attempt date/time.
   */
  @JsonProperty("last_attempted_at")
  public Date lastAttemptedAt;

  /**
   * Gets {@link #lastAttemptedAt}.
   *
   * @return the value of {@link #lastAttemptedAt}
   */
  public Date getLastAttemptedAt() {
    return lastAttemptedAt;
  }

  /**
   * Next scheduled attempt date/time.
   */
  @JsonProperty("next_attempt_at")
  public Date nextAttemptAt;

  /**
   * Gets {@link #nextAttemptAt}.
   *
   * @return the value of {@link #nextAttemptAt}
   */
  public Date getNextAttemptAt() {
    return nextAttemptAt;
  }

  /**
   * Delivery Attempt create date/time.
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
   * List Event Delivery Attempts
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<EventDeliveryAttempt> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Event Delivery Attempts
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<EventDeliveryAttempt> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Event Delivery Attempts
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`, `status`, `event_record_id`, `event_target_id` or `workspace_id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `status`, `workspace_id`, `event_record_id` or `event_target_id`. Valid field combinations are `[ workspace_id, status ]`, `[ workspace_id, event_record_id ]` or `[ workspace_id, event_target_id ]`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code EventDeliveryAttempt} records, in a {@code ListIterator}
   */
  public static ListIterator<EventDeliveryAttempt> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().eventDeliveryAttempts().list(parameters, options);
  }

  /**
   * List Event Delivery Attempts
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<EventDeliveryAttempt> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Event Delivery Attempts
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<EventDeliveryAttempt> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Event Delivery Attempt
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static EventDeliveryAttempt find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Event Delivery Attempt
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static EventDeliveryAttempt find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Event Delivery Attempt
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static EventDeliveryAttempt find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Event Delivery Attempt
   *
   * @param id Event Delivery Attempt ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code EventDeliveryAttempt}
   */
  public static EventDeliveryAttempt find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().eventDeliveryAttempts().find(id, parameters, options);
  }

  /**
   * Show Event Delivery Attempt
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static EventDeliveryAttempt get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Event Delivery Attempt
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static EventDeliveryAttempt get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

}
