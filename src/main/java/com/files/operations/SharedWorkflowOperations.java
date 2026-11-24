package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.AutomationRun;
import com.files.models.SharedWorkflow;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * SharedWorkflow operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#sharedWorkflows()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.SharedWorkflow} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class SharedWorkflowOperations extends ResourceOperations {
  public SharedWorkflowOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Shared Workflows
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<SharedWorkflow> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Shared Workflows
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<SharedWorkflow> list(HashMap<String, Object> parameters) throws RuntimeException {
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
  public ListIterator<SharedWorkflow> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }

    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Object parameters[\"sort_by\"]");
    }

    String apiPath = "/shared_workflows";
    TypeReference<List<SharedWorkflow>> typeReference = new TypeReference<List<SharedWorkflow>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Shared Workflows
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<SharedWorkflow> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Shared Workflows
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<SharedWorkflow> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Shared Workflow
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public SharedWorkflow find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Shared Workflow
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public SharedWorkflow find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Shared Workflow
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public SharedWorkflow find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public SharedWorkflow find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }

    String apiPath = String.format("/shared_workflows/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<SharedWorkflow> typeReference = new TypeReference<SharedWorkflow>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show Shared Workflow
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public SharedWorkflow get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Shared Workflow
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public SharedWorkflow get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Show a Shared Workflow run started by the recipient
   *
   * @see #findRun(Long, Long, HashMap, HashMap)
   */
  public AutomationRun findRun() throws RuntimeException {
    return findRun(null, null, null, null);
  }

  /**
   * Show a Shared Workflow run started by the recipient
   *
   * @see #findRun(Long, Long, HashMap, HashMap)
   */
  public AutomationRun findRun(Long id, Long runId, HashMap<String, Object> parameters) throws RuntimeException {
    return findRun(id, runId, parameters, null);
  }

  /**
   * Show a Shared Workflow run started by the recipient
   *
   * @see #findRun(Long, Long, HashMap, HashMap)
   */
  public AutomationRun findRun(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public AutomationRun findRun(Long id, Long runId, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }

    if (runId == null && parameters.containsKey("run_id") && parameters.get("run_id") != null) {
      runId = (Long) parameters.get("run_id");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (runId == null) {
      throw new NullPointerException("Argument or Parameter missing: run_id parameters[\"run_id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }

    if (!(runId instanceof Long || parameters.get("run_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: run_id must be of type Long or Integer parameters[\"run_id\"]");
    }

    String apiPath = String.format("/shared_workflows/%s/runs/%s", UrlUtils.encodeUrlPath(String.valueOf(id)), UrlUtils.encodeUrlPath(String.valueOf(runId)));
    TypeReference<AutomationRun> typeReference = new TypeReference<AutomationRun>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Run a Shared Workflow
   *
   * @see #runWorkflow(Long, HashMap, HashMap)
   */
  public AutomationRun runWorkflow() throws RuntimeException {
    return runWorkflow(null, null, null);
  }

  /**
   * Run a Shared Workflow
   *
   * @see #runWorkflow(Long, HashMap, HashMap)
   */
  public AutomationRun runWorkflow(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return runWorkflow(id, parameters, null);
  }

  /**
   * Run a Shared Workflow
   *
   * @see #runWorkflow(Long, HashMap, HashMap)
   */
  public AutomationRun runWorkflow(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public AutomationRun runWorkflow(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!parameters.containsKey("submission_revision") || parameters.get("submission_revision") == null) {
      throw new NullPointerException("Parameter missing: submission_revision parameters[\"submission_revision\"]");
    }

    if (!parameters.containsKey("submission_id") || parameters.get("submission_id") == null) {
      throw new NullPointerException("Parameter missing: submission_id parameters[\"submission_id\"]");
    }

    if (!parameters.containsKey("file_path") || parameters.get("file_path") == null) {
      throw new NullPointerException("Parameter missing: file_path parameters[\"file_path\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }

    if (parameters.containsKey("submission_revision") && !(parameters.get("submission_revision") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: submission_revision must be of type String parameters[\"submission_revision\"]");
    }

    if (parameters.containsKey("submission_id") && !(parameters.get("submission_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: submission_id must be of type String parameters[\"submission_id\"]");
    }

    if (parameters.containsKey("file_path") && !(parameters.get("file_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: file_path must be of type String parameters[\"file_path\"]");
    }

    String apiPath = String.format("/shared_workflows/%s/run_workflow", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<AutomationRun> typeReference = new TypeReference<AutomationRun>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }
}
