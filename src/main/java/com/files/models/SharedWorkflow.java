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
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SharedWorkflow implements ModelInterface {
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

  // One mapper for every SharedWorkflow, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code SharedWorkflow} with no attributes set.
   */
  public SharedWorkflow() {
    this(null, null);
  }

  /**
   * Creates a {@code SharedWorkflow} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public SharedWorkflow(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code SharedWorkflow} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public SharedWorkflow(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Shared Workflow ID.
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
   * Name displayed to recipients.
   */
  @JsonProperty("name")
  public String name;

  /**
   * Gets {@link #name}.
   *
   * @return the value of {@link #name}
   */
  public String getName() {
    return name;
  }

  /**
   * Description displayed to recipients.
   */
  @JsonProperty("description")
  public String description;

  /**
   * Gets {@link #description}.
   *
   * @return the value of {@link #description}
   */
  public String getDescription() {
    return description;
  }

  /**
   * Revision required when submitting a Shared Workflow run.
   */
  @JsonProperty("submission_revision")
  public String submissionRevision;

  /**
   * Gets {@link #submissionRevision}.
   *
   * @return the value of {@link #submissionRevision}
   */
  public String getSubmissionRevision() {
    return submissionRevision;
  }

  /**
   * Current custom questions in display order.
   */
  @JsonProperty("form_fields")
  public FormField[] formFields;

  /**
   * Gets {@link #formFields}.
   *
   * @return the value of {@link #formFields}
   */
  public FormField[] getFormFields() {
    return formFields;
  }

  /**
   * Run a Shared Workflow
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code submission_revision} (String): Required. Revision returned when opening the Shared Workflow.</li>
   * <li>{@code submission_id} (String): Required. Client-generated UUID used to retry the same submission without starting another run.</li>
   * <li>{@code file_path} (String): Required. Path of a file the recipient can read, including a file uploaded to a Snapshot created for this workflow.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code AutomationRun}
   */
  public AutomationRun runWorkflow(HashMap<String, Object> parameters) throws IOException {
    return filesClient().sharedWorkflows().runWorkflow(this.id, parameters, this.options);
  }


  /**
   * List Shared Workflows
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<SharedWorkflow> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Shared Workflows
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<SharedWorkflow> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Shared Workflows
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are .</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code SharedWorkflow} records, in a {@code ListIterator}
   */
  public static ListIterator<SharedWorkflow> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().sharedWorkflows().list(parameters, options);
  }

  /**
   * List Shared Workflows
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<SharedWorkflow> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Shared Workflows
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<SharedWorkflow> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Shared Workflow
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SharedWorkflow find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Shared Workflow
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SharedWorkflow find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Shared Workflow
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SharedWorkflow find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Shared Workflow
   *
   * @param id Shared Workflow ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code SharedWorkflow}
   */
  public static SharedWorkflow find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().sharedWorkflows().find(id, parameters, options);
  }

  /**
   * Show Shared Workflow
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SharedWorkflow get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Shared Workflow
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SharedWorkflow get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Show a Shared Workflow run started by the recipient
   *
   * @see #findRun(Long, Long, HashMap, HashMap)
   */
  public static AutomationRun findRun() throws RuntimeException {
    return findRun(null, null, null, null);
  }

  /**
   * Show a Shared Workflow run started by the recipient
   *
   * @see #findRun(Long, Long, HashMap, HashMap)
   */
  public static AutomationRun findRun(Long id, Long runId, HashMap<String, Object> parameters) throws RuntimeException {
    return findRun(id, runId, parameters, null);
  }

  /**
   * Show a Shared Workflow run started by the recipient
   *
   * @see #findRun(Long, Long, HashMap, HashMap)
   */
  public static AutomationRun findRun(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return findRun(null, null, parameters, options);
  }

  /**
   * Show a Shared Workflow run started by the recipient
   *
   * @param id Shared Workflow ID.
   * @param runId ID of the run returned by the Shared Workflow submission.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code AutomationRun}
   */
  public static AutomationRun findRun(Long id, Long runId, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().sharedWorkflows().findRun(id, runId, parameters, options);
  }


  /**
   * Run a Shared Workflow
   *
   * @see #runWorkflow(Long, HashMap, HashMap)
   */
  public static AutomationRun runWorkflow() throws RuntimeException {
    return runWorkflow(null, null, null);
  }

  /**
   * Run a Shared Workflow
   *
   * @see #runWorkflow(Long, HashMap, HashMap)
   */
  public static AutomationRun runWorkflow(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return runWorkflow(id, parameters, null);
  }

  /**
   * Run a Shared Workflow
   *
   * @see #runWorkflow(Long, HashMap, HashMap)
   */
  public static AutomationRun runWorkflow(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return runWorkflow(null, parameters, options);
  }

  /**
   * Run a Shared Workflow
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code submission_revision} (String): Required. Revision returned when opening the Shared Workflow.</li>
   * <li>{@code submission_id} (String): Required. Client-generated UUID used to retry the same submission without starting another run.</li>
   * <li>{@code file_path} (String): Required. Path of a file the recipient can read, including a file uploaded to a Snapshot created for this workflow.</li>
   * </ul>
   *
   * @param id Shared Workflow ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code AutomationRun}
   */
  public static AutomationRun runWorkflow(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().sharedWorkflows().runWorkflow(id, parameters, options);
  }


}
