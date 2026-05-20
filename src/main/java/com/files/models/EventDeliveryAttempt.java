package com.files.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.ListIterator;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
import com.files.util.UrlUtils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventDeliveryAttempt implements ModelInterface {
  private HashMap<String, Object> options;

  public void setOptions(HashMap<String, Object> options) {
    this.options = options;
  }

  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  public EventDeliveryAttempt() {
    this(null, null);
  }

  public EventDeliveryAttempt(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public EventDeliveryAttempt(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Event Delivery Attempt ID
  */
  @JsonProperty("id")
  public Long id;

  public Long getId() {
    return id;
  }

  /**
  * Event Record ID
  */
  @JsonProperty("event_record_id")
  public Long eventRecordId;

  public Long getEventRecordId() {
    return eventRecordId;
  }

  /**
  * Event Subscription ID
  */
  @JsonProperty("event_subscription_id")
  public Long eventSubscriptionId;

  public Long getEventSubscriptionId() {
    return eventSubscriptionId;
  }

  /**
  * Event Target ID
  */
  @JsonProperty("event_target_id")
  public Long eventTargetId;

  public Long getEventTargetId() {
    return eventTargetId;
  }

  /**
  * Workspace ID. 0 means the default workspace or site-wide.
  */
  @JsonProperty("workspace_id")
  public Long workspaceId;

  public Long getWorkspaceId() {
    return workspaceId;
  }

  /**
  * Delivery status.
  */
  @JsonProperty("status")
  public String status;

  public String getStatus() {
    return status;
  }

  /**
  * Number of delivery attempts made.
  */
  @JsonProperty("attempt_number")
  public Long attemptNumber;

  public Long getAttemptNumber() {
    return attemptNumber;
  }

  /**
  * HTTP response code, if applicable.
  */
  @JsonProperty("response_code")
  public Long responseCode;

  public Long getResponseCode() {
    return responseCode;
  }

  /**
  * Delivery error message, if applicable.
  */
  @JsonProperty("error_message")
  public String errorMessage;

  public String getErrorMessage() {
    return errorMessage;
  }

  /**
  * Delivery response body, if applicable.
  */
  @JsonProperty("response_body")
  public String responseBody;

  public String getResponseBody() {
    return responseBody;
  }

  /**
  * Delivery latency in milliseconds.
  */
  @JsonProperty("latency_ms")
  public Long latencyMs;

  public Long getLatencyMs() {
    return latencyMs;
  }

  /**
  * Successful delivery date/time.
  */
  @JsonProperty("delivered_at")
  public Date deliveredAt;

  public Date getDeliveredAt() {
    return deliveredAt;
  }

  /**
  * Most recent attempt date/time.
  */
  @JsonProperty("last_attempted_at")
  public Date lastAttemptedAt;

  public Date getLastAttemptedAt() {
    return lastAttemptedAt;
  }

  /**
  * Next scheduled attempt date/time.
  */
  @JsonProperty("next_attempt_at")
  public Date nextAttemptAt;

  public Date getNextAttemptAt() {
    return nextAttemptAt;
  }

  /**
  * Delivery Attempt create date/time.
  */
  @JsonProperty("created_at")
  public Date createdAt;

  public Date getCreatedAt() {
    return createdAt;
  }


  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `status`, `event_record_id`, `event_target_id` or `workspace_id`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `status`, `workspace_id`, `event_record_id` or `event_target_id`. Valid field combinations are `[ workspace_id, status ]`, `[ workspace_id, event_record_id ]` or `[ workspace_id, event_target_id ]`.
  */
  public static ListIterator<EventDeliveryAttempt> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<EventDeliveryAttempt> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<EventDeliveryAttempt> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }
    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Object parameters[\"sort_by\"]");
    }
    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Object parameters[\"filter\"]");
    }


    String url = String.format("%s%s/event_delivery_attempts", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<EventDeliveryAttempt>> typeReference = new TypeReference<List<EventDeliveryAttempt>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<EventDeliveryAttempt> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<EventDeliveryAttempt> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Event Delivery Attempt ID.
  */
  public static EventDeliveryAttempt find() throws RuntimeException {
    return find(null, null, null);
  }

  public static EventDeliveryAttempt find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static EventDeliveryAttempt find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static EventDeliveryAttempt find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }



    String url = String.format("%s%s/event_delivery_attempts/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<EventDeliveryAttempt> typeReference = new TypeReference<EventDeliveryAttempt>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static EventDeliveryAttempt get() throws RuntimeException {
    return get(null, null, null);
  }

  public static EventDeliveryAttempt get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

}
