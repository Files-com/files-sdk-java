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
public class EventRecord implements ModelInterface {
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


  public EventRecord() {
    this(null, null);
  }

  public EventRecord(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public EventRecord(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Event Record ID
  */
  @JsonProperty("id")
  public Long id;

  public Long getId() {
    return id;
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
  * Stable event UUID.
  */
  @JsonProperty("event_uuid")
  public String eventUuid;

  public String getEventUuid() {
    return eventUuid;
  }

  /**
  * Versioned event type string.
  */
  @JsonProperty("event_type")
  public String eventType;

  public String getEventType() {
    return eventType;
  }

  /**
  * Event severity.
  */
  @JsonProperty("severity")
  public String severity;

  public String getSeverity() {
    return severity;
  }

  /**
  * Source record type.
  */
  @JsonProperty("source_type")
  public String sourceType;

  public String getSourceType() {
    return sourceType;
  }

  /**
  * Source record ID.
  */
  @JsonProperty("source_id")
  public Long sourceId;

  public Long getSourceId() {
    return sourceId;
  }

  /**
  * Event occurrence date/time.
  */
  @JsonProperty("occurred_at")
  public Date occurredAt;

  public Date getOccurredAt() {
    return occurredAt;
  }

  /**
  * Human-readable event title.
  */
  @JsonProperty("human_title")
  public String humanTitle;

  public String getHumanTitle() {
    return humanTitle;
  }

  /**
  * Human-readable event summary.
  */
  @JsonProperty("human_summary")
  public String humanSummary;

  public String getHumanSummary() {
    return humanSummary;
  }

  /**
  * Human-readable event detail fields.
  */
  @JsonProperty("human_fields")
  public Object[] humanFields;

  public Object[] getHumanFields() {
    return humanFields;
  }

  /**
  * Actor associated with the event.
  */
  @JsonProperty("actor")
  public Object actor;

  public Object getActor() {
    return actor;
  }

  /**
  * Resources associated with the event.
  */
  @JsonProperty("resources")
  public Object[] resources;

  public Object[] getResources() {
    return resources;
  }

  /**
  * Event payload.
  */
  @JsonProperty("payload")
  public Object payload;

  public Object getPayload() {
    return payload;
  }

  /**
  * Event Record create date/time.
  */
  @JsonProperty("created_at")
  public Date createdAt;

  public Date getCreatedAt() {
    return createdAt;
  }


  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `event_type`, `created_at` or `workspace_id`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`, `event_type` or `workspace_id`. Valid field combinations are `[ event_type, created_at ]`, `[ workspace_id, created_at ]`, `[ workspace_id, event_type ]` or `[ workspace_id, event_type, created_at ]`.
  *   filter_gt - object - If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`.
  *   filter_gteq - object - If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`.
  *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `event_type`.
  *   filter_lt - object - If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`.
  *   filter_lteq - object - If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`.
  */
  public static ListIterator<EventRecord> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<EventRecord> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<EventRecord> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
    if (parameters.containsKey("filter_gt") && !(parameters.get("filter_gt") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_gt must be of type Object parameters[\"filter_gt\"]");
    }
    if (parameters.containsKey("filter_gteq") && !(parameters.get("filter_gteq") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_gteq must be of type Object parameters[\"filter_gteq\"]");
    }
    if (parameters.containsKey("filter_prefix") && !(parameters.get("filter_prefix") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_prefix must be of type Object parameters[\"filter_prefix\"]");
    }
    if (parameters.containsKey("filter_lt") && !(parameters.get("filter_lt") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_lt must be of type Object parameters[\"filter_lt\"]");
    }
    if (parameters.containsKey("filter_lteq") && !(parameters.get("filter_lteq") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_lteq must be of type Object parameters[\"filter_lteq\"]");
    }


    String url = String.format("%s%s/event_records", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<EventRecord>> typeReference = new TypeReference<List<EventRecord>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<EventRecord> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<EventRecord> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Event Record ID.
  */
  public static EventRecord find() throws RuntimeException {
    return find(null, null, null);
  }

  public static EventRecord find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static EventRecord find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static EventRecord find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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



    String url = String.format("%s%s/event_records/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<EventRecord> typeReference = new TypeReference<EventRecord>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static EventRecord get() throws RuntimeException {
    return get(null, null, null);
  }

  public static EventRecord get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

}
