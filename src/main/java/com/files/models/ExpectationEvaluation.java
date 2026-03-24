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
public class ExpectationEvaluation implements ModelInterface {
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


  public ExpectationEvaluation() {
    this(null, null);
  }

  public ExpectationEvaluation(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public ExpectationEvaluation(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * ExpectationEvaluation ID
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
  * Evaluation status.
  */
  @JsonProperty("status")
  public String status;

  public String getStatus() {
    return status;
  }

  /**
  * How the evaluation window was opened.
  */
  @JsonProperty("opened_via")
  public String openedVia;

  public String getOpenedVia() {
    return openedVia;
  }

  /**
  * When the evaluation row was opened.
  */
  @JsonProperty("opened_at")
  public Date openedAt;

  public Date getOpenedAt() {
    return openedAt;
  }

  /**
  * Logical start of the candidate window.
  */
  @JsonProperty("window_start_at")
  public Date windowStartAt;

  public Date getWindowStartAt() {
    return windowStartAt;
  }

  /**
  * Actual candidate cutoff boundary for the window.
  */
  @JsonProperty("window_end_at")
  public Date windowEndAt;

  public Date getWindowEndAt() {
    return windowEndAt;
  }

  /**
  * Logical due boundary for schedule-driven windows.
  */
  @JsonProperty("deadline_at")
  public Date deadlineAt;

  public Date getDeadlineAt() {
    return deadlineAt;
  }

  /**
  * Logical cutoff for late acceptance, when present.
  */
  @JsonProperty("late_acceptance_cutoff_at")
  public Date lateAcceptanceCutoffAt;

  public Date getLateAcceptanceCutoffAt() {
    return lateAcceptanceCutoffAt;
  }

  /**
  * Hard stop after which the window may not remain open.
  */
  @JsonProperty("hard_close_at")
  public Date hardCloseAt;

  public Date getHardCloseAt() {
    return hardCloseAt;
  }

  /**
  * When the evaluation row was finalized.
  */
  @JsonProperty("closed_at")
  public Date closedAt;

  public Date getClosedAt() {
    return closedAt;
  }

  /**
  * Captured evidence for files that matched the window.
  */
  @JsonProperty("matched_files")
  public Object[] matchedFiles;

  public Object[] getMatchedFiles() {
    return matchedFiles;
  }

  /**
  * Captured evidence for required files that were missing.
  */
  @JsonProperty("missing_files")
  public Object[] missingFiles;

  public Object[] getMissingFiles() {
    return missingFiles;
  }

  /**
  * Captured criteria failures for the window.
  */
  @JsonProperty("criteria_errors")
  public Object[] criteriaErrors;

  public Object[] getCriteriaErrors() {
    return criteriaErrors;
  }

  /**
  * Compact evaluator summary payload.
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
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `created_at` or `expectation_id`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `expectation_id` and `workspace_id`. Valid field combinations are `[ workspace_id, expectation_id ]`.
  */
  public static ListIterator<ExpectationEvaluation> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<ExpectationEvaluation> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<ExpectationEvaluation> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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


    String url = String.format("%s%s/expectation_evaluations", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<ExpectationEvaluation>> typeReference = new TypeReference<List<ExpectationEvaluation>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<ExpectationEvaluation> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<ExpectationEvaluation> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Expectation Evaluation ID.
  */
  public static ExpectationEvaluation find() throws RuntimeException {
    return find(null, null, null);
  }

  public static ExpectationEvaluation find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static ExpectationEvaluation find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static ExpectationEvaluation find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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



    String url = String.format("%s%s/expectation_evaluations/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<ExpectationEvaluation> typeReference = new TypeReference<ExpectationEvaluation>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ExpectationEvaluation get() throws RuntimeException {
    return get(null, null, null);
  }

  public static ExpectationEvaluation get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

}
