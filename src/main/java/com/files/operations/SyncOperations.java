package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.Sync;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `site_id`, `workspace_id` or `name`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id`, `disabled`, `src_remote_server_id` or `dest_remote_server_id`. Valid field combinations are `[ workspace_id, disabled ]`, `[ workspace_id, src_remote_server_id ]`, `[ workspace_id, dest_remote_server_id ]`, `[ disabled, src_remote_server_id ]`, `[ disabled, dest_remote_server_id ]`, `[ workspace_id, disabled, src_remote_server_id ]` or `[ workspace_id, disabled, dest_remote_server_id ]`.
   */
  public ListIterator<Sync> list() throws RuntimeException {
    return list(null, null);
  }

  public ListIterator<Sync> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

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

  public ListIterator<Sync> all() throws RuntimeException {
    return all(null, null);
  }

  public ListIterator<Sync> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Sync ID.
   */
  public Sync find() throws RuntimeException {
    return find(null, null, null);
  }

  public Sync find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public Sync find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

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

  public Sync get() throws RuntimeException {
    return get(null, null, null);
  }

  public Sync get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   delete_empty_folders - boolean - Delete empty folders after sync?
   *   description - string - Description for this sync job
   *   dest_path - string - Absolute destination path for the sync
   *   dest_remote_server_id - int64 - Remote server ID for the destination (if remote)
   *   disabled - boolean - Is this sync disabled?
   *   exclude_patterns - array(string) - Array of glob patterns to exclude
   *   holiday_region - string - Skip the sync if there is a formal, observed holiday for this region.
   *   include_patterns - array(string) - Array of glob patterns to include
   *   interval - string - If trigger is `daily`, this specifies how often to run this sync.  One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
   *   keep_after_copy - boolean - Keep files after copying?
   *   name - string - Name for this sync job
   *   recurring_day - int64 - If trigger type is `daily`, this specifies a day number to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.
   *   recurring_days - array(int64) - If trigger type is `daily`, this specifies one or more day numbers to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.
   *   schedule_id - int64 - If trigger is `custom_schedule`, the reusable Schedule used instead of the sync's schedule fields.
   *   schedule_days_of_week - array(int64) - If trigger is `custom_schedule`, Custom schedule description for when the sync should be run. 0-based days of the week. 0 is Sunday, 1 is Monday, etc.
   *   schedule_time_zone - string - Time zone for the schedule. If not set, times are interpreted as UTC.
   *   schedule_times_of_day - array(string) - Times of day to run in HH:MM format. For `custom_schedule`, run at these times on specified days of week. For `daily`, run at these times on the scheduled interval date.
   *   src_path - string - Absolute source path for the sync
   *   src_remote_server_id - int64 - Remote server ID for the source (if remote)
   *   sync_interval_minutes - int64 - Frequency in minutes between syncs. If set, this value must be greater than or equal to the `remote_sync_interval` value for the site's plan. If left blank, the plan's `remote_sync_interval` will be used. This setting is only used if `trigger` is empty.
   *   trigger - string - Trigger type: daily, custom_schedule, or manual
   *   trigger_file - string - Some MFT services request an empty file (known as a trigger file) to signal the sync is complete and they can begin further processing. If trigger_file is set, a zero-byte file will be sent at the end of the sync.
   *   always_write_trigger_file - boolean - If true, the trigger file will be sent at the end of a successful sync even when no files were transferred.
   *   workspace_id - int64 - Workspace ID this sync belongs to
   */
  public Sync create() throws RuntimeException {
    return create(null, null);
  }

  public Sync create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

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
   */
  public void dryRun() throws RuntimeException {
    dryRun(null, null, null);
  }

  public void dryRun(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    dryRun(id, parameters, null);
  }

  public void dryRun(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    dryRun(null, parameters, options);
  }

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
   */
  public void manualRun() throws RuntimeException {
    manualRun(null, null, null);
  }

  public void manualRun(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    manualRun(id, parameters, null);
  }

  public void manualRun(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    manualRun(null, parameters, options);
  }

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
   * Parameters:
   *   delete_empty_folders - boolean - Delete empty folders after sync?
   *   description - string - Description for this sync job
   *   dest_path - string - Absolute destination path for the sync
   *   dest_remote_server_id - int64 - Remote server ID for the destination (if remote)
   *   disabled - boolean - Is this sync disabled?
   *   exclude_patterns - array(string) - Array of glob patterns to exclude
   *   holiday_region - string - Skip the sync if there is a formal, observed holiday for this region.
   *   include_patterns - array(string) - Array of glob patterns to include
   *   interval - string - If trigger is `daily`, this specifies how often to run this sync.  One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
   *   keep_after_copy - boolean - Keep files after copying?
   *   name - string - Name for this sync job
   *   recurring_day - int64 - If trigger type is `daily`, this specifies a day number to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.
   *   recurring_days - array(int64) - If trigger type is `daily`, this specifies one or more day numbers to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.
   *   schedule_id - int64 - If trigger is `custom_schedule`, the reusable Schedule used instead of the sync's schedule fields.
   *   schedule_days_of_week - array(int64) - If trigger is `custom_schedule`, Custom schedule description for when the sync should be run. 0-based days of the week. 0 is Sunday, 1 is Monday, etc.
   *   schedule_time_zone - string - Time zone for the schedule. If not set, times are interpreted as UTC.
   *   schedule_times_of_day - array(string) - Times of day to run in HH:MM format. For `custom_schedule`, run at these times on specified days of week. For `daily`, run at these times on the scheduled interval date.
   *   src_path - string - Absolute source path for the sync
   *   src_remote_server_id - int64 - Remote server ID for the source (if remote)
   *   sync_interval_minutes - int64 - Frequency in minutes between syncs. If set, this value must be greater than or equal to the `remote_sync_interval` value for the site's plan. If left blank, the plan's `remote_sync_interval` will be used. This setting is only used if `trigger` is empty.
   *   trigger - string - Trigger type: daily, custom_schedule, or manual
   *   trigger_file - string - Some MFT services request an empty file (known as a trigger file) to signal the sync is complete and they can begin further processing. If trigger_file is set, a zero-byte file will be sent at the end of the sync.
   *   always_write_trigger_file - boolean - If true, the trigger file will be sent at the end of a successful sync even when no files were transferred.
   */
  public Sync update() throws RuntimeException {
    return update(null, null, null);
  }

  public Sync update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public Sync update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

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
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

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

  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
