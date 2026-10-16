package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.ExpectationIncident;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * ExpectationIncident operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#expectationIncidents()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.ExpectationIncident} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class ExpectationIncidentOperations extends ResourceOperations {
  public ExpectationIncidentOperations(FilesClient client) {
    super(client);
  }

  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `created_at` or `expectation_id`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `expectation_id` and `workspace_id`. Valid field combinations are `[ workspace_id, expectation_id ]`.
   */
  public ListIterator<ExpectationIncident> list() throws RuntimeException {
    return list(null, null);
  }

  public ListIterator<ExpectationIncident> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  public ListIterator<ExpectationIncident> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = "/expectation_incidents";
    TypeReference<List<ExpectationIncident>> typeReference = new TypeReference<List<ExpectationIncident>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public ListIterator<ExpectationIncident> all() throws RuntimeException {
    return all(null, null);
  }

  public ListIterator<ExpectationIncident> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Expectation Incident ID.
   */
  public ExpectationIncident find() throws RuntimeException {
    return find(null, null, null);
  }

  public ExpectationIncident find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public ExpectationIncident find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public ExpectationIncident find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/expectation_incidents/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<ExpectationIncident> typeReference = new TypeReference<ExpectationIncident>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public ExpectationIncident get() throws RuntimeException {
    return get(null, null, null);
  }

  public ExpectationIncident get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Resolve an expectation incident
   */
  public ExpectationIncident resolve() throws RuntimeException {
    return resolve(null, null, null);
  }

  public ExpectationIncident resolve(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return resolve(id, parameters, null);
  }

  public ExpectationIncident resolve(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return resolve(null, parameters, options);
  }

  public ExpectationIncident resolve(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/expectation_incidents/%s/resolve", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<ExpectationIncident> typeReference = new TypeReference<ExpectationIncident>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Snooze an expectation incident until a specified time
   *
   * Parameters:
   *   snoozed_until (required) - string - Time until which the incident should remain snoozed.
   */
  public ExpectationIncident snooze() throws RuntimeException {
    return snooze(null, null, null);
  }

  public ExpectationIncident snooze(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return snooze(id, parameters, null);
  }

  public ExpectationIncident snooze(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return snooze(null, parameters, options);
  }

  public ExpectationIncident snooze(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

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

    String apiPath = String.format("/expectation_incidents/%s/snooze", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<ExpectationIncident> typeReference = new TypeReference<ExpectationIncident>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Acknowledge an expectation incident
   */
  public ExpectationIncident acknowledge() throws RuntimeException {
    return acknowledge(null, null, null);
  }

  public ExpectationIncident acknowledge(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return acknowledge(id, parameters, null);
  }

  public ExpectationIncident acknowledge(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return acknowledge(null, parameters, options);
  }

  public ExpectationIncident acknowledge(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/expectation_incidents/%s/acknowledge", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<ExpectationIncident> typeReference = new TypeReference<ExpectationIncident>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }
}
