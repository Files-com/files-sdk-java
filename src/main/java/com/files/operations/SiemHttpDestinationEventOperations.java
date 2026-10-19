package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.SiemHttpDestinationEvent;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * SiemHttpDestinationEvent operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#siemHttpDestinationEvents()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.SiemHttpDestinationEvent} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class SiemHttpDestinationEventOperations extends ResourceOperations {
  public SiemHttpDestinationEventOperations(FilesClient client) {
    super(client);
  }

  /**
   * List SIEM HTTP Destination Events
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<SiemHttpDestinationEvent> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List SIEM HTTP Destination Events
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<SiemHttpDestinationEvent> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List SIEM HTTP Destination Events
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`, `status` or `siem_http_destination_id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`, `siem_http_destination_id` or `status`. Valid field combinations are `[ siem_http_destination_id, created_at ]`, `[ status, created_at ]`, `[ siem_http_destination_id, status ]` or `[ siem_http_destination_id, status, created_at ]`.</li>
   * <li>{@code filter_gt} (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_gteq} (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_lt} (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_lteq} (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code SiemHttpDestinationEvent} records, in a {@code ListIterator}
   */
  public ListIterator<SiemHttpDestinationEvent> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = "/siem_http_destination_events";
    TypeReference<List<SiemHttpDestinationEvent>> typeReference = new TypeReference<List<SiemHttpDestinationEvent>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List SIEM HTTP Destination Events
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<SiemHttpDestinationEvent> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List SIEM HTTP Destination Events
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<SiemHttpDestinationEvent> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show SIEM HTTP Destination Event
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public SiemHttpDestinationEvent find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show SIEM HTTP Destination Event
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public SiemHttpDestinationEvent find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show SIEM HTTP Destination Event
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public SiemHttpDestinationEvent find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show SIEM HTTP Destination Event
   *
   * @param id Siem Http Destination Event ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code SiemHttpDestinationEvent}
   */
  public SiemHttpDestinationEvent find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/siem_http_destination_events/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<SiemHttpDestinationEvent> typeReference = new TypeReference<SiemHttpDestinationEvent>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show SIEM HTTP Destination Event
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public SiemHttpDestinationEvent get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show SIEM HTTP Destination Event
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public SiemHttpDestinationEvent get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }
}
