package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.ExternalEvent;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * ExternalEvent operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#externalEvents()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.ExternalEvent} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class ExternalEventOperations extends ResourceOperations {
  public ExternalEventOperations(FilesClient client) {
    super(client);
  }

  /**
   * List External Events
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<ExternalEvent> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List External Events
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<ExternalEvent> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List External Events
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`, `status`, `event_type` or `workspace_id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`, `status` or `workspace_id`. Valid field combinations are `[ status, created_at ]`.</li>
   * <li>{@code filter_gt} (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_gteq} (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_lt} (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_lteq} (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ExternalEvent} records, in a {@code ListIterator}
   */
  public ListIterator<ExternalEvent> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("filter_gt") && !(parameters.get("filter_gt") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_gt must be of type Object parameters[\"filter_gt\"]");
    }

    if (parameters.containsKey("filter_gteq") && !(parameters.get("filter_gteq") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_gteq must be of type Object parameters[\"filter_gteq\"]");
    }

    if (parameters.containsKey("filter_lt") && !(parameters.get("filter_lt") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_lt must be of type Object parameters[\"filter_lt\"]");
    }

    if (parameters.containsKey("filter_lteq") && !(parameters.get("filter_lteq") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_lteq must be of type Object parameters[\"filter_lteq\"]");
    }

    String apiPath = "/external_events";
    TypeReference<List<ExternalEvent>> typeReference = new TypeReference<List<ExternalEvent>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List External Events
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<ExternalEvent> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List External Events
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<ExternalEvent> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show External Event
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public ExternalEvent find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show External Event
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public ExternalEvent find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show External Event
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public ExternalEvent find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show External Event
   *
   * @param id External Event ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ExternalEvent}
   */
  public ExternalEvent find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/external_events/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<ExternalEvent> typeReference = new TypeReference<ExternalEvent>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show External Event
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public ExternalEvent get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show External Event
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public ExternalEvent get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create External Event
   *
   * @see #create(HashMap, HashMap)
   */
  public ExternalEvent create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create External Event
   *
   * @see #create(HashMap, HashMap)
   */
  public ExternalEvent create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create External Event
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code status} (String): Required. Status of event.</li>
   * <li>{@code body} (String): Required. Event body</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ExternalEvent}
   */
  public ExternalEvent create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("status") || parameters.get("status") == null) {
      throw new NullPointerException("Parameter missing: status parameters[\"status\"]");
    }

    if (!parameters.containsKey("body") || parameters.get("body") == null) {
      throw new NullPointerException("Parameter missing: body parameters[\"body\"]");
    }

    if (parameters.containsKey("status") && !(parameters.get("status") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: status must be of type String parameters[\"status\"]");
    }

    if (parameters.containsKey("body") && !(parameters.get("body") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: body must be of type String parameters[\"body\"]");
    }

    String apiPath = "/external_events";
    TypeReference<ExternalEvent> typeReference = new TypeReference<ExternalEvent>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }
}
