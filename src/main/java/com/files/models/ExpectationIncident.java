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
public class ExpectationIncident implements ModelInterface {
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


  public ExpectationIncident() {
    this(null, null);
  }

  public ExpectationIncident(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public ExpectationIncident(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Expectation Incident ID
  */
  @JsonProperty("id")
  public Long id;

  public Long getId() {
    return id;
  }

  /**
  * Workspace ID. `0` means the default workspace.
  */
  @JsonProperty("workspace_id")
  public Long workspaceId;

  public Long getWorkspaceId() {
    return workspaceId;
  }

  /**
  * Expectation ID.
  */
  @JsonProperty("expectation_id")
  public Long expectationId;

  public Long getExpectationId() {
    return expectationId;
  }

  /**
  * Incident status.
  */
  @JsonProperty("status")
  public String status;

  public String getStatus() {
    return status;
  }

  /**
  * When the incident was opened.
  */
  @JsonProperty("opened_at")
  public Date openedAt;

  public Date getOpenedAt() {
    return openedAt;
  }

  /**
  * When the most recent failing evaluation contributing to the incident occurred.
  */
  @JsonProperty("last_failed_at")
  public Date lastFailedAt;

  public Date getLastFailedAt() {
    return lastFailedAt;
  }

  /**
  * When the incident was acknowledged.
  */
  @JsonProperty("acknowledged_at")
  public Date acknowledgedAt;

  public Date getAcknowledgedAt() {
    return acknowledgedAt;
  }

  /**
  * When the current snooze expires.
  */
  @JsonProperty("snoozed_until")
  public Date snoozedUntil;

  public Date getSnoozedUntil() {
    return snoozedUntil;
  }

  /**
  * When the incident was resolved.
  */
  @JsonProperty("resolved_at")
  public Date resolvedAt;

  public Date getResolvedAt() {
    return resolvedAt;
  }

  /**
  * Evaluation that first opened the incident.
  */
  @JsonProperty("opened_by_evaluation_id")
  public Long openedByEvaluationId;

  public Long getOpenedByEvaluationId() {
    return openedByEvaluationId;
  }

  /**
  * Most recent evaluation linked to the incident.
  */
  @JsonProperty("last_evaluation_id")
  public Long lastEvaluationId;

  public Long getLastEvaluationId() {
    return lastEvaluationId;
  }

  /**
  * Evaluation that resolved the incident.
  */
  @JsonProperty("resolved_by_evaluation_id")
  public Long resolvedByEvaluationId;

  public Long getResolvedByEvaluationId() {
    return resolvedByEvaluationId;
  }

  /**
  * Compact incident summary payload.
  */
  @JsonProperty("summary")
  public Object summary;

  public Object getSummary() {
    return summary;
  }

  /**
  * Creation time.
  */
  @JsonProperty("created_at")
  public Date createdAt;

  public Date getCreatedAt() {
    return createdAt;
  }

  /**
  * Last update time.
  */
  @JsonProperty("updated_at")
  public Date updatedAt;

  public Date getUpdatedAt() {
    return updatedAt;
  }

  /**
  * Resolve an expectation incident
  */
  public ExpectationIncident resolve(HashMap<String, Object> parameters) throws IOException {
    return ExpectationIncident.resolve(this.id, parameters, this.options);
  }

  /**
  * Snooze an expectation incident until a specified time
  *
  * Parameters:
  *   snoozed_until (required) - string - Time until which the incident should remain snoozed.
  */
  public ExpectationIncident snooze(HashMap<String, Object> parameters) throws IOException {
    return ExpectationIncident.snooze(this.id, parameters, this.options);
  }

  /**
  * Acknowledge an expectation incident
  */
  public ExpectationIncident acknowledge(HashMap<String, Object> parameters) throws IOException {
    return ExpectationIncident.acknowledge(this.id, parameters, this.options);
  }


  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `created_at` or `expectation_id`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `expectation_id` and `workspace_id`. Valid field combinations are `[ workspace_id, expectation_id ]`.
  */
  public static ListIterator<ExpectationIncident> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<ExpectationIncident> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<ExpectationIncident> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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


    String url = String.format("%s%s/expectation_incidents", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<ExpectationIncident>> typeReference = new TypeReference<List<ExpectationIncident>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<ExpectationIncident> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<ExpectationIncident> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Expectation Incident ID.
  */
  public static ExpectationIncident find() throws RuntimeException {
    return find(null, null, null);
  }

  public static ExpectationIncident find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static ExpectationIncident find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static ExpectationIncident find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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



    String url = String.format("%s%s/expectation_incidents/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<ExpectationIncident> typeReference = new TypeReference<ExpectationIncident>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ExpectationIncident get() throws RuntimeException {
    return get(null, null, null);
  }

  public static ExpectationIncident get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
  * Resolve an expectation incident
  */
  public static ExpectationIncident resolve() throws RuntimeException {
    return resolve(null, null, null);
  }

  public static ExpectationIncident resolve(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return resolve(id, parameters, null);
  }

  public static ExpectationIncident resolve(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return resolve(null, parameters, options);
  }

  public static ExpectationIncident resolve(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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



    String url = String.format("%s%s/expectation_incidents/%s/resolve", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<ExpectationIncident> typeReference = new TypeReference<ExpectationIncident>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Snooze an expectation incident until a specified time
  *
  * Parameters:
  *   snoozed_until (required) - string - Time until which the incident should remain snoozed.
  */
  public static ExpectationIncident snooze() throws RuntimeException {
    return snooze(null, null, null);
  }

  public static ExpectationIncident snooze(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return snooze(id, parameters, null);
  }

  public static ExpectationIncident snooze(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return snooze(null, parameters, options);
  }

  public static ExpectationIncident snooze(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }
    if (!parameters.containsKey("snoozed_until") || parameters.get("snoozed_until") == null) {
      throw new NullPointerException("Parameter missing: snoozed_until parameters[\"snoozed_until\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }
    if (parameters.containsKey("snoozed_until") && !(parameters.get("snoozed_until") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: snoozed_until must be of type String parameters[\"snoozed_until\"]");
    }



    String url = String.format("%s%s/expectation_incidents/%s/snooze", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<ExpectationIncident> typeReference = new TypeReference<ExpectationIncident>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Acknowledge an expectation incident
  */
  public static ExpectationIncident acknowledge() throws RuntimeException {
    return acknowledge(null, null, null);
  }

  public static ExpectationIncident acknowledge(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return acknowledge(id, parameters, null);
  }

  public static ExpectationIncident acknowledge(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return acknowledge(null, parameters, options);
  }

  public static ExpectationIncident acknowledge(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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



    String url = String.format("%s%s/expectation_incidents/%s/acknowledge", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<ExpectationIncident> typeReference = new TypeReference<ExpectationIncident>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


}
