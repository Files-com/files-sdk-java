package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.HolidayCalendar;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * HolidayCalendar operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#holidayCalendars()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.HolidayCalendar} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class HolidayCalendarOperations extends ResourceOperations {
  public HolidayCalendarOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Holiday Calendars
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<HolidayCalendar> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Holiday Calendars
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<HolidayCalendar> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Holiday Calendars
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are .</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code HolidayCalendar} records, in a {@code ListIterator}
   */
  public ListIterator<HolidayCalendar> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = "/holiday_calendars";
    TypeReference<List<HolidayCalendar>> typeReference = new TypeReference<List<HolidayCalendar>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Holiday Calendars
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<HolidayCalendar> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Holiday Calendars
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<HolidayCalendar> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Holiday Calendar
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public HolidayCalendar find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Holiday Calendar
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public HolidayCalendar find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Holiday Calendar
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public HolidayCalendar find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Holiday Calendar
   *
   * @param id Holiday Calendar ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code HolidayCalendar}
   */
  public HolidayCalendar find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/holiday_calendars/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<HolidayCalendar> typeReference = new TypeReference<HolidayCalendar>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show Holiday Calendar
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public HolidayCalendar get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Holiday Calendar
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public HolidayCalendar get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Holiday Calendar
   *
   * @see #create(HashMap, HashMap)
   */
  public HolidayCalendar create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Holiday Calendar
   *
   * @see #create(HashMap, HashMap)
   */
  public HolidayCalendar create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create Holiday Calendar
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code definition} (Object): Required. Holiday rules for the calendar.</li>
   * <li>{@code name} (String): Required. Holiday Calendar name.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code HolidayCalendar}
   */
  public HolidayCalendar create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("definition") || parameters.get("definition") == null) {
      throw new NullPointerException("Parameter missing: definition parameters[\"definition\"]");
    }

    if (!parameters.containsKey("name") || parameters.get("name") == null) {
      throw new NullPointerException("Parameter missing: name parameters[\"name\"]");
    }

    if (parameters.containsKey("definition") && !(parameters.get("definition") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: definition must be of type Object parameters[\"definition\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    String apiPath = "/holiday_calendars";
    TypeReference<HolidayCalendar> typeReference = new TypeReference<HolidayCalendar>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Update Holiday Calendar
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public HolidayCalendar update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Holiday Calendar
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public HolidayCalendar update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Holiday Calendar
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public HolidayCalendar update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Holiday Calendar
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code definition} (Object): Holiday rules for the calendar.</li>
   * <li>{@code name} (String): Holiday Calendar name.</li>
   * </ul>
   *
   * @param id Holiday Calendar ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code HolidayCalendar}
   */
  public HolidayCalendar update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("definition") && !(parameters.get("definition") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: definition must be of type Object parameters[\"definition\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    String apiPath = String.format("/holiday_calendars/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<HolidayCalendar> typeReference = new TypeReference<HolidayCalendar>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete Holiday Calendar
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Holiday Calendar
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Holiday Calendar
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Holiday Calendar
   *
   * @param id Holiday Calendar ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/holiday_calendars/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete Holiday Calendar
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Holiday Calendar
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
