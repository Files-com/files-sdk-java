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
public class AutomationRun implements ModelInterface {
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


  public AutomationRun() {
    this(null, null);
  }

  public AutomationRun(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public AutomationRun(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * ID.
  */
  @JsonProperty("id")
  public Long id;

  public Long getId() {
    return id;
  }

  /**
  * ID of the associated Automation.
  */
  @JsonProperty("automation_id")
  public Long automationId;

  public Long getAutomationId() {
    return automationId;
  }

  /**
  * Workspace ID.
  */
  @JsonProperty("workspace_id")
  public Long workspaceId;

  public Long getWorkspaceId() {
    return workspaceId;
  }

  /**
  * Automation run completion/failure date/time.
  */
  @JsonProperty("completed_at")
  public Date completedAt;

  public Date getCompletedAt() {
    return completedAt;
  }

  /**
  * Automation run start date/time.
  */
  @JsonProperty("created_at")
  public Date createdAt;

  public Date getCreatedAt() {
    return createdAt;
  }

  /**
  * If set, this automation will be retried at this date/time due to `failure` or `partial_failure`.
  */
  @JsonProperty("retry_at")
  public Date retryAt;

  public Date getRetryAt() {
    return retryAt;
  }

  /**
  * If set, this Automation run was retried due to `failure` or `partial_failure`.
  */
  @JsonProperty("retried_at")
  public Date retriedAt;

  public Date getRetriedAt() {
    return retriedAt;
  }

  /**
  * ID of the run that is or will be retrying this run.
  */
  @JsonProperty("retried_in_run_id")
  public Long retriedInRunId;

  public Long getRetriedInRunId() {
    return retriedInRunId;
  }

  /**
  * ID of the original run that this run is retrying.
  */
  @JsonProperty("retry_of_run_id")
  public Long retryOfRunId;

  public Long getRetryOfRunId() {
    return retryOfRunId;
  }

  /**
  * Automation run runtime.
  */
  @JsonProperty("runtime")
  public Double runtime;

  public Double getRuntime() {
    return runtime;
  }

  /**
  * The success status of the AutomationRun. One of `running`, `success`, `partial_failure`, or `failure`.
  */
  @JsonProperty("status")
  public String status;

  public String getStatus() {
    return status;
  }

  /**
  * Count of successful operations.
  */
  @JsonProperty("successful_operations")
  public Long successfulOperations;

  public Long getSuccessfulOperations() {
    return successfulOperations;
  }

  /**
  * Count of failed operations.
  */
  @JsonProperty("failed_operations")
  public Long failedOperations;

  public Long getFailedOperations() {
    return failedOperations;
  }

  /**
  * Link to status messages log file.
  */
  @JsonProperty("status_messages_url")
  public String statusMessagesUrl;

  public String getStatusMessagesUrl() {
    return statusMessagesUrl;
  }


  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `automation_id`, `created_at` or `status`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `status`, `workspace_id` or `automation_id`. Valid field combinations are `[ workspace_id, status ]`, `[ automation_id, status ]`, `[ workspace_id, automation_id ]` or `[ workspace_id, automation_id, status ]`.
  *   automation_id (required) - int64 - ID of the associated Automation.
  */
  public static ListIterator<AutomationRun> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<AutomationRun> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<AutomationRun> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (!parameters.containsKey("automation_id") || parameters.get("automation_id") == null) {
      throw new NullPointerException("Parameter missing: automation_id parameters[\"automation_id\"]");
    }

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }
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
    if (parameters.containsKey("automation_id") && !(parameters.get("automation_id") instanceof Long || parameters.get("automation_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: automation_id must be of type Long or Integer parameters[\"automation_id\"]");
    }


    String url = String.format("%s%s/automation_runs", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<AutomationRun>> typeReference = new TypeReference<List<AutomationRun>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<AutomationRun> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<AutomationRun> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Automation Run ID.
  */
  public static AutomationRun find() throws RuntimeException {
    return find(null, null, null);
  }

  public static AutomationRun find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static AutomationRun find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static AutomationRun find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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



    String url = String.format("%s%s/automation_runs/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<AutomationRun> typeReference = new TypeReference<AutomationRun>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static AutomationRun get() throws RuntimeException {
    return get(null, null, null);
  }

  public static AutomationRun get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

}
