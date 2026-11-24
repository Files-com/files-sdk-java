package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.Schedule;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * Schedule operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#schedules()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.Schedule} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class ScheduleOperations extends ResourceOperations {
  public ScheduleOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Schedules
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Schedule> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Schedules
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Schedule> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Schedules
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `name`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Schedule} records, in a {@code ListIterator}
   */
  public ListIterator<Schedule> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = "/schedules";
    TypeReference<List<Schedule>> typeReference = new TypeReference<List<Schedule>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Schedules
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Schedule> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Schedules
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Schedule> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Schedule
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Schedule find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Schedule
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Schedule find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Schedule
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Schedule find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Schedule
   *
   * @param id Schedule ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Schedule}
   */
  public Schedule find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/schedules/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<Schedule> typeReference = new TypeReference<Schedule>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show Schedule
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Schedule get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Schedule
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Schedule get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Schedule
   *
   * @see #create(HashMap, HashMap)
   */
  public Schedule create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Schedule
   *
   * @see #create(HashMap, HashMap)
   */
  public Schedule create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create Schedule
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Required. Schedule name.</li>
   * <li>{@code schedule_days_of_week} (Long[]): Required. 0-based weekdays used by the Schedule. 0 is Sunday.</li>
   * <li>{@code schedule_times_of_day} (String[]): Required. Times of day in HH:MM format (24-hour).</li>
   * <li>{@code schedule_time_zone} (String): Time zone for scheduled times. If not set, times are interpreted as UTC.</li>
   * <li>{@code holiday_region} (String): Optional holiday region on which linked resources do not run.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Schedule}
   */
  public Schedule create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("name") || parameters.get("name") == null) {
      throw new NullPointerException("Parameter missing: name parameters[\"name\"]");
    }

    if (!parameters.containsKey("schedule_days_of_week") || parameters.get("schedule_days_of_week") == null) {
      throw new NullPointerException("Parameter missing: schedule_days_of_week parameters[\"schedule_days_of_week\"]");
    }

    if (!parameters.containsKey("schedule_times_of_day") || parameters.get("schedule_times_of_day") == null) {
      throw new NullPointerException("Parameter missing: schedule_times_of_day parameters[\"schedule_times_of_day\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("schedule_days_of_week") && !(parameters.get("schedule_days_of_week") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: schedule_days_of_week must be of type Long[] parameters[\"schedule_days_of_week\"]");
    }

    if (parameters.containsKey("schedule_times_of_day") && !(parameters.get("schedule_times_of_day") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: schedule_times_of_day must be of type String[] parameters[\"schedule_times_of_day\"]");
    }

    if (parameters.containsKey("schedule_time_zone") && !(parameters.get("schedule_time_zone") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: schedule_time_zone must be of type String parameters[\"schedule_time_zone\"]");
    }

    if (parameters.containsKey("holiday_region") && !(parameters.get("holiday_region") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: holiday_region must be of type String parameters[\"holiday_region\"]");
    }

    String apiPath = "/schedules";
    TypeReference<Schedule> typeReference = new TypeReference<Schedule>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Update Schedule
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public Schedule update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Schedule
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public Schedule update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Schedule
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public Schedule update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Schedule
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Schedule name.</li>
   * <li>{@code schedule_days_of_week} (Long[]): 0-based weekdays used by the Schedule. 0 is Sunday.</li>
   * <li>{@code schedule_times_of_day} (String[]): Times of day in HH:MM format (24-hour).</li>
   * <li>{@code schedule_time_zone} (String): Time zone for scheduled times. If not set, times are interpreted as UTC.</li>
   * <li>{@code holiday_region} (String): Optional holiday region on which linked resources do not run.</li>
   * </ul>
   *
   * @param id Schedule ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Schedule}
   */
  public Schedule update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("schedule_days_of_week") && !(parameters.get("schedule_days_of_week") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: schedule_days_of_week must be of type Long[] parameters[\"schedule_days_of_week\"]");
    }

    if (parameters.containsKey("schedule_times_of_day") && !(parameters.get("schedule_times_of_day") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: schedule_times_of_day must be of type String[] parameters[\"schedule_times_of_day\"]");
    }

    if (parameters.containsKey("schedule_time_zone") && !(parameters.get("schedule_time_zone") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: schedule_time_zone must be of type String parameters[\"schedule_time_zone\"]");
    }

    if (parameters.containsKey("holiday_region") && !(parameters.get("holiday_region") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: holiday_region must be of type String parameters[\"holiday_region\"]");
    }

    String apiPath = String.format("/schedules/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<Schedule> typeReference = new TypeReference<Schedule>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete Schedule
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Schedule
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Schedule
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Schedule
   *
   * @param id Schedule ID.
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

    String apiPath = String.format("/schedules/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete Schedule
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Schedule
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
