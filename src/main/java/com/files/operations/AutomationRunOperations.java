package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.AutomationExecutionNode;
import com.files.models.AutomationRun;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * AutomationRun operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#automationRuns()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.AutomationRun} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class AutomationRunOperations extends ResourceOperations {
  public AutomationRunOperations(FilesClient client) {
    super(client);
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
  public ListIterator<AutomationRun> list() throws RuntimeException {
    return list(null, null);
  }

  public ListIterator<AutomationRun> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  public ListIterator<AutomationRun> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

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

    String apiPath = "/automation_runs";
    TypeReference<List<AutomationRun>> typeReference = new TypeReference<List<AutomationRun>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public ListIterator<AutomationRun> all() throws RuntimeException {
    return all(null, null);
  }

  public ListIterator<AutomationRun> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Automation Run ID.
   */
  public AutomationRun find() throws RuntimeException {
    return find(null, null, null);
  }

  public AutomationRun find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public AutomationRun find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public AutomationRun find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/automation_runs/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<AutomationRun> typeReference = new TypeReference<AutomationRun>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public AutomationRun get() throws RuntimeException {
    return get(null, null, null);
  }

  public AutomationRun get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Automation Run ID.
   *   node_id (required) - string - Node ID from the pinned Automation definition.
   */
  public AutomationExecutionNode findNode() throws RuntimeException {
    return findNode(null, null, null);
  }

  public AutomationExecutionNode findNode(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return findNode(id, parameters, null);
  }

  public AutomationExecutionNode findNode(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return findNode(null, parameters, options);
  }

  public AutomationExecutionNode findNode(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!parameters.containsKey("node_id") || parameters.get("node_id") == null) {
      throw new NullPointerException("Parameter missing: node_id parameters[\"node_id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }

    if (parameters.containsKey("node_id") && !(parameters.get("node_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: node_id must be of type String parameters[\"node_id\"]");
    }

    String apiPath = String.format("/automation_runs/%s/node", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<AutomationExecutionNode> typeReference = new TypeReference<AutomationExecutionNode>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Cancel Automation Run
   */
  public AutomationRun cancel() throws RuntimeException {
    return cancel(null, null, null);
  }

  public AutomationRun cancel(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return cancel(id, parameters, null);
  }

  public AutomationRun cancel(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return cancel(null, parameters, options);
  }

  public AutomationRun cancel(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/automation_runs/%s/cancel", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<AutomationRun> typeReference = new TypeReference<AutomationRun>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Re-run Automation from Node
   *
   * Parameters:
   *   node_id (required) - string - Node ID at which execution should resume.
   */
  public AutomationRun rerun() throws RuntimeException {
    return rerun(null, null, null);
  }

  public AutomationRun rerun(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return rerun(id, parameters, null);
  }

  public AutomationRun rerun(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return rerun(null, parameters, options);
  }

  public AutomationRun rerun(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!parameters.containsKey("node_id") || parameters.get("node_id") == null) {
      throw new NullPointerException("Parameter missing: node_id parameters[\"node_id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }

    if (parameters.containsKey("node_id") && !(parameters.get("node_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: node_id must be of type String parameters[\"node_id\"]");
    }

    String apiPath = String.format("/automation_runs/%s/rerun", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<AutomationRun> typeReference = new TypeReference<AutomationRun>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }
}
