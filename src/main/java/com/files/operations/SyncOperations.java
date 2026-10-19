package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.Sync;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * Sync operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#syncs()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.Sync} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class SyncOperations extends ResourceOperations {
  public SyncOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Syncs
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Sync> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Syncs
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Sync> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Syncs
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `site_id`, `workspace_id` or `name`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id`, `disabled`, `src_remote_server_id` or `dest_remote_server_id`. Valid field combinations are `[ workspace_id, disabled ]`, `[ workspace_id, src_remote_server_id ]`, `[ workspace_id, dest_remote_server_id ]`, `[ disabled, src_remote_server_id ]`, `[ disabled, dest_remote_server_id ]`, `[ workspace_id, disabled, src_remote_server_id ]` or `[ workspace_id, disabled, dest_remote_server_id ]`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Sync} records, in a {@code ListIterator}
   */
  public ListIterator<Sync> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = "/syncs";
    TypeReference<List<Sync>> typeReference = new TypeReference<List<Sync>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Syncs
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Sync> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Syncs
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Sync> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Sync
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Sync find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Sync
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Sync find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Sync
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Sync find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Sync
   *
   * @param id Sync ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Sync}
   */
  public Sync find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/syncs/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<Sync> typeReference = new TypeReference<Sync>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show Sync
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Sync get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Sync
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Sync get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Sync
   *
   * @see #create(HashMap, HashMap)
   */
  public Sync create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Sync
   *
   * @see #create(HashMap, HashMap)
   */
  public Sync create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create Sync
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code delete_empty_folders} (Boolean): Delete empty folders after sync?</li>
   * <li>{@code description} (String): Description for this sync job</li>
   * <li>{@code dest_path} (String): Absolute destination path for the sync</li>
   * <li>{@code dest_remote_server_id} (Long): Remote server ID for the destination (if remote)</li>
   * <li>{@code disabled} (Boolean): Is this sync disabled?</li>
   * <li>{@code exclude_patterns} (String[]): Array of glob patterns to exclude</li>
   * <li>{@code holiday_region} (String): Skip the sync if there is a formal, observed holiday for this region.</li>
   * <li>{@code include_patterns} (String[]): Array of glob patterns to include</li>
   * <li>{@code interval} (String): If trigger is `daily`, this specifies how often to run this sync.  One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`</li>
   * <li>{@code keep_after_copy} (Boolean): Keep files after copying?</li>
   * <li>{@code name} (String): Name for this sync job</li>
   * <li>{@code recurring_day} (Long): If trigger type is `daily`, this specifies a day number to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.</li>
   * <li>{@code recurring_days} (Long[]): If trigger type is `daily`, this specifies one or more day numbers to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.</li>
   * <li>{@code schedule_id} (Long): If trigger is `custom_schedule`, the reusable Schedule used instead of the sync's schedule fields.</li>
   * <li>{@code schedule_days_of_week} (Long[]): If trigger is `custom_schedule`, Custom schedule description for when the sync should be run. 0-based days of the week. 0 is Sunday, 1 is Monday, etc.</li>
   * <li>{@code schedule_time_zone} (String): Time zone for the schedule. If not set, times are interpreted as UTC.</li>
   * <li>{@code schedule_times_of_day} (String[]): Times of day to run in HH:MM format. For `custom_schedule`, run at these times on specified days of week. For `daily`, run at these times on the scheduled interval date.</li>
   * <li>{@code src_path} (String): Absolute source path for the sync</li>
   * <li>{@code src_remote_server_id} (Long): Remote server ID for the source (if remote)</li>
   * <li>{@code sync_interval_minutes} (Long): Frequency in minutes between syncs. If set, this value must be greater than or equal to the `remote_sync_interval` value for the site's plan. If left blank, the plan's `remote_sync_interval` will be used. This setting is only used if `trigger` is empty.</li>
   * <li>{@code trigger} (String): Trigger type: daily, custom_schedule, or manual</li>
   * <li>{@code trigger_file} (String): Some MFT services request an empty file (known as a trigger file) to signal the sync is complete and they can begin further processing. If trigger_file is set, a zero-byte file will be sent at the end of the sync.</li>
   * <li>{@code always_write_trigger_file} (Boolean): If true, the trigger file will be sent at the end of a successful sync even when no files were transferred.</li>
   * <li>{@code workspace_id} (Long): Workspace ID this sync belongs to</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Sync}
   */
  public Sync create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("delete_empty_folders") && !(parameters.get("delete_empty_folders") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: delete_empty_folders must be of type Boolean parameters[\"delete_empty_folders\"]");
    }

    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }

    if (parameters.containsKey("dest_path") && !(parameters.get("dest_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: dest_path must be of type String parameters[\"dest_path\"]");
    }

    if (parameters.containsKey("dest_remote_server_id") && !(parameters.get("dest_remote_server_id") instanceof Long || parameters.get("dest_remote_server_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: dest_remote_server_id must be of type Long or Integer parameters[\"dest_remote_server_id\"]");
    }

    if (parameters.containsKey("disabled") && !(parameters.get("disabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: disabled must be of type Boolean parameters[\"disabled\"]");
    }

    if (parameters.containsKey("exclude_patterns") && !(parameters.get("exclude_patterns") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: exclude_patterns must be of type String[] parameters[\"exclude_patterns\"]");
    }

    if (parameters.containsKey("holiday_region") && !(parameters.get("holiday_region") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: holiday_region must be of type String parameters[\"holiday_region\"]");
    }

    if (parameters.containsKey("include_patterns") && !(parameters.get("include_patterns") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: include_patterns must be of type String[] parameters[\"include_patterns\"]");
    }

    if (parameters.containsKey("interval") && !(parameters.get("interval") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: interval must be of type String parameters[\"interval\"]");
    }

    if (parameters.containsKey("keep_after_copy") && !(parameters.get("keep_after_copy") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: keep_after_copy must be of type Boolean parameters[\"keep_after_copy\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
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

    if (parameters.containsKey("schedule_time_zone") && !(parameters.get("schedule_time_zone") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: schedule_time_zone must be of type String parameters[\"schedule_time_zone\"]");
    }

    if (parameters.containsKey("schedule_times_of_day") && !(parameters.get("schedule_times_of_day") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: schedule_times_of_day must be of type String[] parameters[\"schedule_times_of_day\"]");
    }

    if (parameters.containsKey("src_path") && !(parameters.get("src_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: src_path must be of type String parameters[\"src_path\"]");
    }

    if (parameters.containsKey("src_remote_server_id") && !(parameters.get("src_remote_server_id") instanceof Long || parameters.get("src_remote_server_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: src_remote_server_id must be of type Long or Integer parameters[\"src_remote_server_id\"]");
    }

    if (parameters.containsKey("sync_interval_minutes") && !(parameters.get("sync_interval_minutes") instanceof Long || parameters.get("sync_interval_minutes") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: sync_interval_minutes must be of type Long or Integer parameters[\"sync_interval_minutes\"]");
    }

    if (parameters.containsKey("trigger") && !(parameters.get("trigger") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: trigger must be of type String parameters[\"trigger\"]");
    }

    if (parameters.containsKey("trigger_file") && !(parameters.get("trigger_file") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: trigger_file must be of type String parameters[\"trigger_file\"]");
    }

    if (parameters.containsKey("always_write_trigger_file") && !(parameters.get("always_write_trigger_file") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: always_write_trigger_file must be of type Boolean parameters[\"always_write_trigger_file\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    String apiPath = "/syncs";
    TypeReference<Sync> typeReference = new TypeReference<Sync>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Dry Run Sync
   *
   * @see #dryRun(Long, HashMap, HashMap)
   */
  public void dryRun() throws RuntimeException {
    dryRun(null, null, null);
  }

  /**
   * Dry Run Sync
   *
   * @see #dryRun(Long, HashMap, HashMap)
   */
  public void dryRun(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    dryRun(id, parameters, null);
  }

  /**
   * Dry Run Sync
   *
   * @see #dryRun(Long, HashMap, HashMap)
   */
  public void dryRun(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    dryRun(null, parameters, options);
  }

  /**
   * Dry Run Sync
   *
   * @param id Sync ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public void dryRun(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/syncs/%s/dry_run", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.POST, apiPath, parameters, options);
  }

  /**
   * Manually Run Sync
   *
   * @see #manualRun(Long, HashMap, HashMap)
   */
  public void manualRun() throws RuntimeException {
    manualRun(null, null, null);
  }

  /**
   * Manually Run Sync
   *
   * @see #manualRun(Long, HashMap, HashMap)
   */
  public void manualRun(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    manualRun(id, parameters, null);
  }

  /**
   * Manually Run Sync
   *
   * @see #manualRun(Long, HashMap, HashMap)
   */
  public void manualRun(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    manualRun(null, parameters, options);
  }

  /**
   * Manually Run Sync
   *
   * @param id Sync ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public void manualRun(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/syncs/%s/manual_run", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.POST, apiPath, parameters, options);
  }

  /**
   * Update Sync
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public Sync update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Sync
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public Sync update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Sync
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public Sync update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Sync
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code delete_empty_folders} (Boolean): Delete empty folders after sync?</li>
   * <li>{@code description} (String): Description for this sync job</li>
   * <li>{@code dest_path} (String): Absolute destination path for the sync</li>
   * <li>{@code dest_remote_server_id} (Long): Remote server ID for the destination (if remote)</li>
   * <li>{@code disabled} (Boolean): Is this sync disabled?</li>
   * <li>{@code exclude_patterns} (String[]): Array of glob patterns to exclude</li>
   * <li>{@code holiday_region} (String): Skip the sync if there is a formal, observed holiday for this region.</li>
   * <li>{@code include_patterns} (String[]): Array of glob patterns to include</li>
   * <li>{@code interval} (String): If trigger is `daily`, this specifies how often to run this sync.  One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`</li>
   * <li>{@code keep_after_copy} (Boolean): Keep files after copying?</li>
   * <li>{@code name} (String): Name for this sync job</li>
   * <li>{@code recurring_day} (Long): If trigger type is `daily`, this specifies a day number to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.</li>
   * <li>{@code recurring_days} (Long[]): If trigger type is `daily`, this specifies one or more day numbers to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.</li>
   * <li>{@code schedule_id} (Long): If trigger is `custom_schedule`, the reusable Schedule used instead of the sync's schedule fields.</li>
   * <li>{@code schedule_days_of_week} (Long[]): If trigger is `custom_schedule`, Custom schedule description for when the sync should be run. 0-based days of the week. 0 is Sunday, 1 is Monday, etc.</li>
   * <li>{@code schedule_time_zone} (String): Time zone for the schedule. If not set, times are interpreted as UTC.</li>
   * <li>{@code schedule_times_of_day} (String[]): Times of day to run in HH:MM format. For `custom_schedule`, run at these times on specified days of week. For `daily`, run at these times on the scheduled interval date.</li>
   * <li>{@code src_path} (String): Absolute source path for the sync</li>
   * <li>{@code src_remote_server_id} (Long): Remote server ID for the source (if remote)</li>
   * <li>{@code sync_interval_minutes} (Long): Frequency in minutes between syncs. If set, this value must be greater than or equal to the `remote_sync_interval` value for the site's plan. If left blank, the plan's `remote_sync_interval` will be used. This setting is only used if `trigger` is empty.</li>
   * <li>{@code trigger} (String): Trigger type: daily, custom_schedule, or manual</li>
   * <li>{@code trigger_file} (String): Some MFT services request an empty file (known as a trigger file) to signal the sync is complete and they can begin further processing. If trigger_file is set, a zero-byte file will be sent at the end of the sync.</li>
   * <li>{@code always_write_trigger_file} (Boolean): If true, the trigger file will be sent at the end of a successful sync even when no files were transferred.</li>
   * </ul>
   *
   * @param id Sync ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Sync}
   */
  public Sync update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("delete_empty_folders") && !(parameters.get("delete_empty_folders") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: delete_empty_folders must be of type Boolean parameters[\"delete_empty_folders\"]");
    }

    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }

    if (parameters.containsKey("dest_path") && !(parameters.get("dest_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: dest_path must be of type String parameters[\"dest_path\"]");
    }

    if (parameters.containsKey("dest_remote_server_id") && !(parameters.get("dest_remote_server_id") instanceof Long || parameters.get("dest_remote_server_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: dest_remote_server_id must be of type Long or Integer parameters[\"dest_remote_server_id\"]");
    }

    if (parameters.containsKey("disabled") && !(parameters.get("disabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: disabled must be of type Boolean parameters[\"disabled\"]");
    }

    if (parameters.containsKey("exclude_patterns") && !(parameters.get("exclude_patterns") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: exclude_patterns must be of type String[] parameters[\"exclude_patterns\"]");
    }

    if (parameters.containsKey("holiday_region") && !(parameters.get("holiday_region") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: holiday_region must be of type String parameters[\"holiday_region\"]");
    }

    if (parameters.containsKey("include_patterns") && !(parameters.get("include_patterns") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: include_patterns must be of type String[] parameters[\"include_patterns\"]");
    }

    if (parameters.containsKey("interval") && !(parameters.get("interval") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: interval must be of type String parameters[\"interval\"]");
    }

    if (parameters.containsKey("keep_after_copy") && !(parameters.get("keep_after_copy") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: keep_after_copy must be of type Boolean parameters[\"keep_after_copy\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
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

    if (parameters.containsKey("schedule_time_zone") && !(parameters.get("schedule_time_zone") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: schedule_time_zone must be of type String parameters[\"schedule_time_zone\"]");
    }

    if (parameters.containsKey("schedule_times_of_day") && !(parameters.get("schedule_times_of_day") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: schedule_times_of_day must be of type String[] parameters[\"schedule_times_of_day\"]");
    }

    if (parameters.containsKey("src_path") && !(parameters.get("src_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: src_path must be of type String parameters[\"src_path\"]");
    }

    if (parameters.containsKey("src_remote_server_id") && !(parameters.get("src_remote_server_id") instanceof Long || parameters.get("src_remote_server_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: src_remote_server_id must be of type Long or Integer parameters[\"src_remote_server_id\"]");
    }

    if (parameters.containsKey("sync_interval_minutes") && !(parameters.get("sync_interval_minutes") instanceof Long || parameters.get("sync_interval_minutes") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: sync_interval_minutes must be of type Long or Integer parameters[\"sync_interval_minutes\"]");
    }

    if (parameters.containsKey("trigger") && !(parameters.get("trigger") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: trigger must be of type String parameters[\"trigger\"]");
    }

    if (parameters.containsKey("trigger_file") && !(parameters.get("trigger_file") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: trigger_file must be of type String parameters[\"trigger_file\"]");
    }

    if (parameters.containsKey("always_write_trigger_file") && !(parameters.get("always_write_trigger_file") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: always_write_trigger_file must be of type Boolean parameters[\"always_write_trigger_file\"]");
    }

    String apiPath = String.format("/syncs/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<Sync> typeReference = new TypeReference<Sync>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete Sync
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Sync
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Sync
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Sync
   *
   * @param id Sync ID.
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

    String apiPath = String.format("/syncs/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete Sync
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Sync
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
