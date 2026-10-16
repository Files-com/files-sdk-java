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
public class AutomationRun implements ModelInterface {
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

  // One mapper for every AutomationRun, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
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
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
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
   * ID of the immutable Automation version pinned by this run.
   */
  @JsonProperty("automation_version_id")
  public Long automationVersionId;

  public Long getAutomationVersionId() {
    return automationVersionId;
  }

  /**
   * Pinned Automation v2 definition version.
   */
  @JsonProperty("version")
  public Long version;

  public Long getVersion() {
    return version;
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
   * Date/time at which cancellation was requested.
   */
  @JsonProperty("cancel_requested_at")
  public Date cancelRequestedAt;

  public Date getCancelRequestedAt() {
    return cancelRequestedAt;
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
   * ID of the run whose persisted node outputs this run reused.
   */
  @JsonProperty("rerun_of_run_id")
  public Long rerunOfRunId;

  public Long getRerunOfRunId() {
    return rerunOfRunId;
  }

  /**
   * Node at which this run resumed execution.
   */
  @JsonProperty("rerun_from_node_id")
  public String rerunFromNodeId;

  public String getRerunFromNodeId() {
    return rerunFromNodeId;
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
   * The status of the AutomationRun. One of `queued`, `running`, `success`, `partial_failure`, `failure`, `skipped`, or `canceled`.
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
   * Automation definition snapshot pinned by this run. For performance reasons, this is not provided when listing Automation runs.
   */
  @JsonProperty("definition")
  public Object definition;

  public Object getDefinition() {
    return definition;
  }

  /**
   * Status and execution stage for each node in this run. For performance reasons, this is not provided when listing Automation runs.
   */
  @JsonProperty("node_states")
  public Object nodeStates;

  public Object getNodeStates() {
    return nodeStates;
  }

  /**
   * Execution status, timing, and bounded output summaries for each node. For performance reasons, this is not provided when listing Automation runs.
   */
  @JsonProperty("execution_nodes")
  public AutomationExecutionNode[] executionNodes;

  public AutomationExecutionNode[] getExecutionNodes() {
    return executionNodes;
  }

  /**
   * Link to the run journal artifact.
   */
  @JsonProperty("journal_url")
  public String journalUrl;

  public String getJournalUrl() {
    return journalUrl;
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
   * Cancel Automation Run
   */
  public AutomationRun cancel(HashMap<String, Object> parameters) throws IOException {
    return filesClient().automationRuns().cancel(this.id, parameters, this.options);
  }

  /**
   * Re-run Automation from Node
   *
   * Parameters:
   *   node_id (required) - string - Node ID at which execution should resume.
   */
  public AutomationRun rerun(HashMap<String, Object> parameters) throws IOException {
    return filesClient().automationRuns().rerun(this.id, parameters, this.options);
  }


  /**
   * Parameters:
   *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
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
    return FilesClient.defaultClient().automationRuns().list(parameters, options);
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
    return FilesClient.defaultClient().automationRuns().find(id, parameters, options);
  }

  public static AutomationRun get() throws RuntimeException {
    return get(null, null, null);
  }

  public static AutomationRun get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Automation Run ID.
   *   node_id (required) - string - Node ID from the pinned Automation definition.
   */
  public static AutomationExecutionNode findNode() throws RuntimeException {
    return findNode(null, null, null);
  }

  public static AutomationExecutionNode findNode(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return findNode(id, parameters, null);
  }

  public static AutomationExecutionNode findNode(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return findNode(null, parameters, options);
  }

  public static AutomationExecutionNode findNode(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().automationRuns().findNode(id, parameters, options);
  }


  /**
   * Cancel Automation Run
   */
  public static AutomationRun cancel() throws RuntimeException {
    return cancel(null, null, null);
  }

  public static AutomationRun cancel(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return cancel(id, parameters, null);
  }

  public static AutomationRun cancel(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return cancel(null, parameters, options);
  }

  public static AutomationRun cancel(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().automationRuns().cancel(id, parameters, options);
  }


  /**
   * Re-run Automation from Node
   *
   * Parameters:
   *   node_id (required) - string - Node ID at which execution should resume.
   */
  public static AutomationRun rerun() throws RuntimeException {
    return rerun(null, null, null);
  }

  public static AutomationRun rerun(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return rerun(id, parameters, null);
  }

  public static AutomationRun rerun(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return rerun(null, parameters, options);
  }

  public static AutomationRun rerun(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().automationRuns().rerun(id, parameters, options);
  }


}
