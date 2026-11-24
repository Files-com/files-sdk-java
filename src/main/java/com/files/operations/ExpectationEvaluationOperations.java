package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.ExpectationEvaluation;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * ExpectationEvaluation operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#expectationEvaluations()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.ExpectationEvaluation} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class ExpectationEvaluationOperations extends ResourceOperations {
  public ExpectationEvaluationOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Expectation Evaluations
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<ExpectationEvaluation> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Expectation Evaluations
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<ExpectationEvaluation> list(HashMap<String, Object> parameters) throws RuntimeException {
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
  public ListIterator<ExpectationEvaluation> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Object parameters[\"filter\"]");
    }

    String apiPath = "/expectation_evaluations";
    TypeReference<List<ExpectationEvaluation>> typeReference = new TypeReference<List<ExpectationEvaluation>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Expectation Evaluations
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<ExpectationEvaluation> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Expectation Evaluations
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<ExpectationEvaluation> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Expectation Evaluation
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public ExpectationEvaluation find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Expectation Evaluation
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public ExpectationEvaluation find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Expectation Evaluation
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public ExpectationEvaluation find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public ExpectationEvaluation find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/expectation_evaluations/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<ExpectationEvaluation> typeReference = new TypeReference<ExpectationEvaluation>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show Expectation Evaluation
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public ExpectationEvaluation get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Expectation Evaluation
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public ExpectationEvaluation get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }
}
