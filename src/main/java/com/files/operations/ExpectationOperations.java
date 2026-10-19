package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.Expectation;
import com.files.models.ExpectationEvaluation;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * Expectation operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#expectations()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.Expectation} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class ExpectationOperations extends ResourceOperations {
  public ExpectationOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Expectations
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Expectation> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Expectations
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Expectation> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Expectations
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `name` or `disabled`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `disabled` and `workspace_id`. Valid field combinations are `[ workspace_id, disabled ]`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Expectation} records, in a {@code ListIterator}
   */
  public ListIterator<Expectation> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = "/expectations";
    TypeReference<List<Expectation>> typeReference = new TypeReference<List<Expectation>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Expectations
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Expectation> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Expectations
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Expectation> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Expectation
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Expectation find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Expectation
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Expectation find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Expectation
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Expectation find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Expectation
   *
   * @param id Expectation ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Expectation}
   */
  public Expectation find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/expectations/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<Expectation> typeReference = new TypeReference<Expectation>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show Expectation
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Expectation get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Expectation
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Expectation get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Expectation
   *
   * @see #create(HashMap, HashMap)
   */
  public Expectation create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Expectation
   *
   * @see #create(HashMap, HashMap)
   */
  public Expectation create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create Expectation
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Expectation name.</li>
   * <li>{@code description} (String): Expectation description.</li>
   * <li>{@code path} (String): Path scope for the expectation. Supports workspace-relative presentation.</li>
   * <li>{@code source} (String): Source glob used to select candidate files.</li>
   * <li>{@code exclude_pattern} (String): Optional source exclusion glob.</li>
   * <li>{@code disabled} (Boolean): If true, the expectation is disabled.</li>
   * <li>{@code trigger} (String): How this expectation opens windows.</li>
   * <li>{@code interval} (String): If trigger is `daily`, this specifies how often to run the expectation.</li>
   * <li>{@code recurring_day} (Long): If trigger is `daily`, this selects the day number inside the chosen interval.</li>
   * <li>{@code recurring_days} (Long[]): If trigger is `daily`, this selects one or more day numbers inside a `week`, `month`, `quarter`, or `year` interval.</li>
   * <li>{@code schedule_id} (Long): If trigger is `custom_schedule`, the reusable Schedule used instead of the Expectation's schedule fields.</li>
   * <li>{@code schedule_days_of_week} (Long[]): If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.</li>
   * <li>{@code schedule_times_of_day} (String[]): Times of day in HH:MM format for the Expectation schedule.</li>
   * <li>{@code schedule_time_zone} (String): Time zone used by the Expectation schedule.</li>
   * <li>{@code holiday_region} (String): Optional holiday region used by the Expectation schedule.</li>
   * <li>{@code lookback_interval} (Long): How many seconds before the due boundary the window starts.</li>
   * <li>{@code late_acceptance_interval} (Long): How many seconds a schedule-driven window may remain eligible to close as late.</li>
   * <li>{@code inactivity_interval} (Long): How many quiet seconds are required before final closure.</li>
   * <li>{@code max_open_interval} (Long): Hard-stop duration in seconds for unscheduled expectations.</li>
   * <li>{@code criteria} (Object): Versioned success criteria definition for the expectation, including optional Files Transform Script content validation in criteria v2.</li>
   * <li>{@code workspace_id} (Long): Workspace ID. `0` means the default workspace.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Expectation}
   */
  public Expectation create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }

    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("source") && !(parameters.get("source") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: source must be of type String parameters[\"source\"]");
    }

    if (parameters.containsKey("exclude_pattern") && !(parameters.get("exclude_pattern") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: exclude_pattern must be of type String parameters[\"exclude_pattern\"]");
    }

    if (parameters.containsKey("disabled") && !(parameters.get("disabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: disabled must be of type Boolean parameters[\"disabled\"]");
    }

    if (parameters.containsKey("trigger") && !(parameters.get("trigger") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: trigger must be of type String parameters[\"trigger\"]");
    }

    if (parameters.containsKey("interval") && !(parameters.get("interval") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: interval must be of type String parameters[\"interval\"]");
    }

    if (parameters.containsKey("recurring_day") && !(parameters.get("recurring_day") instanceof Long || parameters.get("recurring_day") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: recurring_day must be of type Long or Integer parameters[\"recurring_day\"]");
    }

    if (parameters.containsKey("recurring_days") && !(parameters.get("recurring_days") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: recurring_days must be of type Long[] parameters[\"recurring_days\"]");
    }

    if (parameters.containsKey("schedule_id") && !(parameters.get("schedule_id") instanceof Long || parameters.get("schedule_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: schedule_id must be of type Long or Integer parameters[\"schedule_id\"]");
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

    if (parameters.containsKey("lookback_interval") && !(parameters.get("lookback_interval") instanceof Long || parameters.get("lookback_interval") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: lookback_interval must be of type Long or Integer parameters[\"lookback_interval\"]");
    }

    if (parameters.containsKey("late_acceptance_interval") && !(parameters.get("late_acceptance_interval") instanceof Long || parameters.get("late_acceptance_interval") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: late_acceptance_interval must be of type Long or Integer parameters[\"late_acceptance_interval\"]");
    }

    if (parameters.containsKey("inactivity_interval") && !(parameters.get("inactivity_interval") instanceof Long || parameters.get("inactivity_interval") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: inactivity_interval must be of type Long or Integer parameters[\"inactivity_interval\"]");
    }

    if (parameters.containsKey("max_open_interval") && !(parameters.get("max_open_interval") instanceof Long || parameters.get("max_open_interval") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: max_open_interval must be of type Long or Integer parameters[\"max_open_interval\"]");
    }

    if (parameters.containsKey("criteria") && !(parameters.get("criteria") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: criteria must be of type Object parameters[\"criteria\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    String apiPath = "/expectations";
    TypeReference<Expectation> typeReference = new TypeReference<Expectation>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Manually open an Expectation window
   *
   * @see #triggerEvaluation(Long, HashMap, HashMap)
   */
  public ExpectationEvaluation triggerEvaluation() throws RuntimeException {
    return triggerEvaluation(null, null, null);
  }

  /**
   * Manually open an Expectation window
   *
   * @see #triggerEvaluation(Long, HashMap, HashMap)
   */
  public ExpectationEvaluation triggerEvaluation(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return triggerEvaluation(id, parameters, null);
  }

  /**
   * Manually open an Expectation window
   *
   * @see #triggerEvaluation(Long, HashMap, HashMap)
   */
  public ExpectationEvaluation triggerEvaluation(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return triggerEvaluation(null, parameters, options);
  }

  /**
   * Manually open an Expectation window
   *
   * @param id Expectation ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ExpectationEvaluation}
   */
  public ExpectationEvaluation triggerEvaluation(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/expectations/%s/trigger_evaluation", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<ExpectationEvaluation> typeReference = new TypeReference<ExpectationEvaluation>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Update Expectation
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public Expectation update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Expectation
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public Expectation update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Expectation
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public Expectation update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Expectation
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Expectation name.</li>
   * <li>{@code description} (String): Expectation description.</li>
   * <li>{@code path} (String): Path scope for the expectation. Supports workspace-relative presentation.</li>
   * <li>{@code source} (String): Source glob used to select candidate files.</li>
   * <li>{@code exclude_pattern} (String): Optional source exclusion glob.</li>
   * <li>{@code disabled} (Boolean): If true, the expectation is disabled.</li>
   * <li>{@code trigger} (String): How this expectation opens windows.</li>
   * <li>{@code interval} (String): If trigger is `daily`, this specifies how often to run the expectation.</li>
   * <li>{@code recurring_day} (Long): If trigger is `daily`, this selects the day number inside the chosen interval.</li>
   * <li>{@code recurring_days} (Long[]): If trigger is `daily`, this selects one or more day numbers inside a `week`, `month`, `quarter`, or `year` interval.</li>
   * <li>{@code schedule_id} (Long): If trigger is `custom_schedule`, the reusable Schedule used instead of the Expectation's schedule fields.</li>
   * <li>{@code schedule_days_of_week} (Long[]): If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.</li>
   * <li>{@code schedule_times_of_day} (String[]): Times of day in HH:MM format for the Expectation schedule.</li>
   * <li>{@code schedule_time_zone} (String): Time zone used by the Expectation schedule.</li>
   * <li>{@code holiday_region} (String): Optional holiday region used by the Expectation schedule.</li>
   * <li>{@code lookback_interval} (Long): How many seconds before the due boundary the window starts.</li>
   * <li>{@code late_acceptance_interval} (Long): How many seconds a schedule-driven window may remain eligible to close as late.</li>
   * <li>{@code inactivity_interval} (Long): How many quiet seconds are required before final closure.</li>
   * <li>{@code max_open_interval} (Long): Hard-stop duration in seconds for unscheduled expectations.</li>
   * <li>{@code criteria} (Object): Versioned success criteria definition for the expectation, including optional Files Transform Script content validation in criteria v2.</li>
   * <li>{@code workspace_id} (Long): Workspace ID. `0` means the default workspace.</li>
   * </ul>
   *
   * @param id Expectation ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Expectation}
   */
  public Expectation update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }

    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("source") && !(parameters.get("source") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: source must be of type String parameters[\"source\"]");
    }

    if (parameters.containsKey("exclude_pattern") && !(parameters.get("exclude_pattern") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: exclude_pattern must be of type String parameters[\"exclude_pattern\"]");
    }

    if (parameters.containsKey("disabled") && !(parameters.get("disabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: disabled must be of type Boolean parameters[\"disabled\"]");
    }

    if (parameters.containsKey("trigger") && !(parameters.get("trigger") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: trigger must be of type String parameters[\"trigger\"]");
    }

    if (parameters.containsKey("interval") && !(parameters.get("interval") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: interval must be of type String parameters[\"interval\"]");
    }

    if (parameters.containsKey("recurring_day") && !(parameters.get("recurring_day") instanceof Long || parameters.get("recurring_day") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: recurring_day must be of type Long or Integer parameters[\"recurring_day\"]");
    }

    if (parameters.containsKey("recurring_days") && !(parameters.get("recurring_days") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: recurring_days must be of type Long[] parameters[\"recurring_days\"]");
    }

    if (parameters.containsKey("schedule_id") && !(parameters.get("schedule_id") instanceof Long || parameters.get("schedule_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: schedule_id must be of type Long or Integer parameters[\"schedule_id\"]");
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

    if (parameters.containsKey("lookback_interval") && !(parameters.get("lookback_interval") instanceof Long || parameters.get("lookback_interval") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: lookback_interval must be of type Long or Integer parameters[\"lookback_interval\"]");
    }

    if (parameters.containsKey("late_acceptance_interval") && !(parameters.get("late_acceptance_interval") instanceof Long || parameters.get("late_acceptance_interval") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: late_acceptance_interval must be of type Long or Integer parameters[\"late_acceptance_interval\"]");
    }

    if (parameters.containsKey("inactivity_interval") && !(parameters.get("inactivity_interval") instanceof Long || parameters.get("inactivity_interval") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: inactivity_interval must be of type Long or Integer parameters[\"inactivity_interval\"]");
    }

    if (parameters.containsKey("max_open_interval") && !(parameters.get("max_open_interval") instanceof Long || parameters.get("max_open_interval") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: max_open_interval must be of type Long or Integer parameters[\"max_open_interval\"]");
    }

    if (parameters.containsKey("criteria") && !(parameters.get("criteria") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: criteria must be of type Object parameters[\"criteria\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    String apiPath = String.format("/expectations/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<Expectation> typeReference = new TypeReference<Expectation>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete Expectation
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Expectation
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Expectation
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Expectation
   *
   * @param id Expectation ID.
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

    String apiPath = String.format("/expectations/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete Expectation
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Expectation
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
