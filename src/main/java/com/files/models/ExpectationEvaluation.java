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
public class ExpectationEvaluation implements ModelInterface {
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

  // One mapper for every ExpectationEvaluation, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code ExpectationEvaluation} with no attributes set.
   */
  public ExpectationEvaluation() {
    this(null, null);
  }

  /**
   * Creates a {@code ExpectationEvaluation} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public ExpectationEvaluation(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code ExpectationEvaluation} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public ExpectationEvaluation(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * ExpectationEvaluation ID
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
   * Evaluation status.
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
   * How the evaluation window was opened.
   */
  @JsonProperty("opened_via")
  public String openedVia;

  /**
   * Gets {@link #openedVia}.
   *
   * @return the value of {@link #openedVia}
   */
  public String getOpenedVia() {
    return openedVia;
  }

  /**
   * When the evaluation row was opened.
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
   * Logical start of the candidate window.
   */
  @JsonProperty("window_start_at")
  public Date windowStartAt;

  /**
   * Gets {@link #windowStartAt}.
   *
   * @return the value of {@link #windowStartAt}
   */
  public Date getWindowStartAt() {
    return windowStartAt;
  }

  /**
   * Actual candidate cutoff boundary for the window.
   */
  @JsonProperty("window_end_at")
  public Date windowEndAt;

  /**
   * Gets {@link #windowEndAt}.
   *
   * @return the value of {@link #windowEndAt}
   */
  public Date getWindowEndAt() {
    return windowEndAt;
  }

  /**
   * Logical due boundary for schedule-driven windows.
   */
  @JsonProperty("deadline_at")
  public Date deadlineAt;

  /**
   * Gets {@link #deadlineAt}.
   *
   * @return the value of {@link #deadlineAt}
   */
  public Date getDeadlineAt() {
    return deadlineAt;
  }

  /**
   * Logical cutoff for late acceptance, when present.
   */
  @JsonProperty("late_acceptance_cutoff_at")
  public Date lateAcceptanceCutoffAt;

  /**
   * Gets {@link #lateAcceptanceCutoffAt}.
   *
   * @return the value of {@link #lateAcceptanceCutoffAt}
   */
  public Date getLateAcceptanceCutoffAt() {
    return lateAcceptanceCutoffAt;
  }

  /**
   * Hard stop after which the window may not remain open.
   */
  @JsonProperty("hard_close_at")
  public Date hardCloseAt;

  /**
   * Gets {@link #hardCloseAt}.
   *
   * @return the value of {@link #hardCloseAt}
   */
  public Date getHardCloseAt() {
    return hardCloseAt;
  }

  /**
   * When the evaluation row was finalized.
   */
  @JsonProperty("closed_at")
  public Date closedAt;

  /**
   * Gets {@link #closedAt}.
   *
   * @return the value of {@link #closedAt}
   */
  public Date getClosedAt() {
    return closedAt;
  }

  /**
   * Captured evidence for files that matched the window.
   */
  @JsonProperty("matched_files")
  public Object[] matchedFiles;

  /**
   * Gets {@link #matchedFiles}.
   *
   * @return the value of {@link #matchedFiles}
   */
  public Object[] getMatchedFiles() {
    return matchedFiles;
  }

  /**
   * Captured evidence for required files that were missing.
   */
  @JsonProperty("missing_files")
  public Object[] missingFiles;

  /**
   * Gets {@link #missingFiles}.
   *
   * @return the value of {@link #missingFiles}
   */
  public Object[] getMissingFiles() {
    return missingFiles;
  }

  /**
   * Captured criteria failures for the window, including file-scoped FTS errors and returned validation details.
   */
  @JsonProperty("criteria_errors")
  public String[] criteriaErrors;

  /**
   * Gets {@link #criteriaErrors}.
   *
   * @return the value of {@link #criteriaErrors}
   */
  public String[] getCriteriaErrors() {
    return criteriaErrors;
  }

  /**
   * Compact evaluator summary payload.
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
   * List Expectation Evaluations
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ExpectationEvaluation> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Expectation Evaluations
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ExpectationEvaluation> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Expectation Evaluations
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
   * @return the {@code ExpectationEvaluation} records, in a {@code ListIterator}
   */
  public static ListIterator<ExpectationEvaluation> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().expectationEvaluations().list(parameters, options);
  }

  /**
   * List Expectation Evaluations
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ExpectationEvaluation> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Expectation Evaluations
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ExpectationEvaluation> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Expectation Evaluation
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ExpectationEvaluation find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Expectation Evaluation
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ExpectationEvaluation find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Expectation Evaluation
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ExpectationEvaluation find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Expectation Evaluation
   *
   * @param id Expectation Evaluation ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ExpectationEvaluation}
   */
  public static ExpectationEvaluation find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().expectationEvaluations().find(id, parameters, options);
  }

  /**
   * Show Expectation Evaluation
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ExpectationEvaluation get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Expectation Evaluation
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ExpectationEvaluation get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

}
