package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.Automation;
import com.files.models.AutomationAuthoringSchema;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * Automation operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#automations()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.Automation} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class AutomationOperations extends ResourceOperations {
  public AutomationOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Automations
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Automation> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Automations
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Automation> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Automations
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `name`, `automation`, `last_modified_at` or `disabled`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `disabled`, `last_modified_at`, `workspace_id` or `automation`. Valid field combinations are `[ disabled, last_modified_at ]`, `[ workspace_id, disabled ]`, `[ disabled, automation ]`, `[ workspace_id, last_modified_at ]`, `[ automation, last_modified_at ]`, `[ workspace_id, automation ]`, `[ workspace_id, disabled, last_modified_at ]`, `[ disabled, automation, last_modified_at ]`, `[ workspace_id, disabled, automation ]`, `[ workspace_id, automation, last_modified_at ]` or `[ workspace_id, disabled, automation, last_modified_at ]`.</li>
   * <li>{@code filter_gt} (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `last_modified_at`.</li>
   * <li>{@code filter_gteq} (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `last_modified_at`.</li>
   * <li>{@code filter_lt} (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `last_modified_at`.</li>
   * <li>{@code filter_lteq} (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `last_modified_at`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Automation} records, in a {@code ListIterator}
   */
  public ListIterator<Automation> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = "/automations";
    TypeReference<List<Automation>> typeReference = new TypeReference<List<Automation>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Automations
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Automation> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Automations
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Automation> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Automation
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Automation find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Automation
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Automation find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Automation
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Automation find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Automation
   *
   * @param id Automation ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Automation}
   */
  public Automation find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/automations/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<Automation> typeReference = new TypeReference<Automation>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show Automation
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Automation get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Automation
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Automation get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Show the Automation v2 authoring schema and active node catalog
   *
   * @see #getAuthoringSchema(HashMap, HashMap)
   */
  public AutomationAuthoringSchema getAuthoringSchema() throws RuntimeException {
    return getAuthoringSchema(null, null);
  }

  /**
   * Show the Automation v2 authoring schema and active node catalog
   *
   * @see #getAuthoringSchema(HashMap, HashMap)
   */
  public AutomationAuthoringSchema getAuthoringSchema(HashMap<String, Object> parameters) throws RuntimeException {
    return getAuthoringSchema(parameters, null);
  }

  /**
   * Show the Automation v2 authoring schema and active node catalog
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code AutomationAuthoringSchema}
   */
  public AutomationAuthoringSchema getAuthoringSchema(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    String apiPath = "/automations/authoring_schema";
    TypeReference<AutomationAuthoringSchema> typeReference = new TypeReference<AutomationAuthoringSchema>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Create Automation
   *
   * @see #create(HashMap, HashMap)
   */
  public Automation create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Automation
   *
   * @see #create(HashMap, HashMap)
   */
  public Automation create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create Automation
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code source} (String): Source path/glob.  See Automation docs for exact description, but this is used to filter for files in the `path` to find files to operate on. Supports globs, except on remote mounts.</li>
   * <li>{@code destinations} (String[]): A list of destination paths. Use a trailing slash for folder destinations and omit it for file destinations.</li>
   * <li>{@code destination_replace_from} (String): If set, this string in the destination path will be replaced with the value in `destination_replace_to`.</li>
   * <li>{@code destination_replace_to} (String): If set, this string will replace the value `destination_replace_from` in the destination filename. You can use special patterns here.</li>
   * <li>{@code interval} (String): How often to run this automation? One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`</li>
   * <li>{@code path} (String): Path on which this Automation runs.  Supports globs, except on remote mounts.</li>
   * <li>{@code sync_ids} (String): A list of sync IDs the automation is associated with. If sent as a string, it should be comma-delimited.</li>
   * <li>{@code user_ids} (String): A list of user IDs the automation is associated with. If sent as a string, it should be comma-delimited.</li>
   * <li>{@code group_ids} (String): A list of group IDs the automation is associated with. If sent as a string, it should be comma-delimited.</li>
   * <li>{@code schedule_id} (Long): If trigger is `custom_schedule`, the reusable Schedule used instead of the automation's schedule fields.</li>
   * <li>{@code schedule_days_of_week} (Long[]): If trigger is `custom_schedule` without `schedule_id`, a list of days of the week to run this automation. 0 is Sunday, 1 is Monday, etc.</li>
   * <li>{@code schedule_times_of_day} (String[]): Times of day to run in HH:MM format (24-hour). Required for `custom_schedule` without `schedule_id`. Optional for `daily` triggers; if not set, runs at midnight UTC.</li>
   * <li>{@code schedule_time_zone} (String): Time zone for schedule fields. Optional for `custom_schedule` without `schedule_id` and for `daily`. If not set, times are interpreted as UTC.</li>
   * <li>{@code holiday_region} (String): Skip automation on holidays in this region. Optional for `custom_schedule` without `schedule_id` and for `daily`.</li>
   * <li>{@code always_overwrite_size_matching_files} (Boolean): Ordinarily, files with identical size in the source and destination will be skipped from copy operations to prevent wasted transfer.  If this flag is `true` we will overwrite the destination file always.  Note that this may cause large amounts of wasted transfer usage.  This setting has no effect unless `overwrite_files` is also set to `true`.</li>
   * <li>{@code always_serialize_jobs} (Boolean): Ordinarily, we will allow automation runs to run in parallel for non-scheduled automations. If this flag is `true` we will force automation runs to be serialized (run one at a time, one after another). This can resolve some issues with race conditions on remote systems at the cost of some performance.</li>
   * <li>{@code description} (String): Description for the this Automation.</li>
   * <li>{@code definition} (Object): Automation v2 graph definition.</li>
   * <li>{@code disabled} (Boolean): If true, this automation will not run.</li>
   * <li>{@code exclude_pattern} (String): If set, this glob pattern will exclude files from the automation. Supports globs, except on remote mounts.</li>
   * <li>{@code import_urls} (Object[]): List of URLs to be imported and names to be used.</li>
   * <li>{@code flatten_destination_structure} (Boolean): Normally copy and move automations that use globs will implicitly preserve the source folder structure in the destination.  If this flag is `true`, the source folder structure will be flattened in the destination.  This is useful for copying or moving files from multiple folders into a single destination folder.</li>
   * <li>{@code ignore_locked_folders} (Boolean): If true, the Lock Folders behavior will be disregarded for automated actions.</li>
   * <li>{@code legacy_folder_matching} (Boolean): DEPRECATED: If `true`, use the legacy behavior for this automation, where it can operate on folders in addition to just files.  This behavior no longer works and should not be used.</li>
   * <li>{@code name} (String): Name for this automation.</li>
   * <li>{@code overwrite_files} (Boolean): If true, existing files will be overwritten with new files on Move/Copy automations.  Note: by default files will not be overwritten on Copy automations if they appear to be the same file size as the newly incoming file.  Use the `always_overwrite_size_matching_files` option in conjunction with `overwrite_files` to override this behavior and overwrite files no matter what.</li>
   * <li>{@code path_time_zone} (String): Timezone to use when rendering timestamps in paths.</li>
   * <li>{@code retry_on_failure_interval_in_minutes} (Long): If the Automation fails, retry at this interval (in minutes).  Acceptable values are 5 through 1440 (one day).  Set to null to disable.</li>
   * <li>{@code retry_on_failure_number_of_attempts} (Long): If the Automation fails, retry at most this many times.  Maximum allowed value: 10.  Set to null to disable.</li>
   * <li>{@code trigger} (String): How this automation is triggered to run.</li>
   * <li>{@code trigger_actions} (String[]): If trigger is `action`, this is the list of action types on which to trigger the automation. Valid actions are create, copy, move, archived_delete, update, read, destroy</li>
   * <li>{@code value} (Object): A Hash of attributes specific to the automation type.</li>
   * <li>{@code recurring_day} (Long): If trigger type is `daily`, this specifies a day number to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.</li>
   * <li>{@code recurring_days} (Long[]): If trigger type is `daily`, this specifies one or more day numbers to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.</li>
   * <li>{@code automation} (String): Required. Automation type</li>
   * <li>{@code workspace_id} (Long): Workspace ID</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Automation}
   */
  public Automation create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("automation") || parameters.get("automation") == null) {
      throw new NullPointerException("Parameter missing: automation parameters[\"automation\"]");
    }

    if (parameters.containsKey("source") && !(parameters.get("source") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: source must be of type String parameters[\"source\"]");
    }

    if (parameters.containsKey("destinations") && !(parameters.get("destinations") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: destinations must be of type String[] parameters[\"destinations\"]");
    }

    if (parameters.containsKey("destination_replace_from") && !(parameters.get("destination_replace_from") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: destination_replace_from must be of type String parameters[\"destination_replace_from\"]");
    }

    if (parameters.containsKey("destination_replace_to") && !(parameters.get("destination_replace_to") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: destination_replace_to must be of type String parameters[\"destination_replace_to\"]");
    }

    if (parameters.containsKey("interval") && !(parameters.get("interval") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: interval must be of type String parameters[\"interval\"]");
    }

    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("sync_ids") && !(parameters.get("sync_ids") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: sync_ids must be of type String parameters[\"sync_ids\"]");
    }

    if (parameters.containsKey("user_ids") && !(parameters.get("user_ids") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: user_ids must be of type String parameters[\"user_ids\"]");
    }

    if (parameters.containsKey("group_ids") && !(parameters.get("group_ids") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: group_ids must be of type String parameters[\"group_ids\"]");
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

    if (parameters.containsKey("always_overwrite_size_matching_files") && !(parameters.get("always_overwrite_size_matching_files") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: always_overwrite_size_matching_files must be of type Boolean parameters[\"always_overwrite_size_matching_files\"]");
    }

    if (parameters.containsKey("always_serialize_jobs") && !(parameters.get("always_serialize_jobs") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: always_serialize_jobs must be of type Boolean parameters[\"always_serialize_jobs\"]");
    }

    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }

    if (parameters.containsKey("definition") && !(parameters.get("definition") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: definition must be of type Object parameters[\"definition\"]");
    }

    if (parameters.containsKey("disabled") && !(parameters.get("disabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: disabled must be of type Boolean parameters[\"disabled\"]");
    }

    if (parameters.containsKey("exclude_pattern") && !(parameters.get("exclude_pattern") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: exclude_pattern must be of type String parameters[\"exclude_pattern\"]");
    }

    if (parameters.containsKey("import_urls") && !(parameters.get("import_urls") instanceof Object[])) {
      throw new IllegalArgumentException("Bad parameter: import_urls must be of type Object[] parameters[\"import_urls\"]");
    }

    if (parameters.containsKey("flatten_destination_structure") && !(parameters.get("flatten_destination_structure") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: flatten_destination_structure must be of type Boolean parameters[\"flatten_destination_structure\"]");
    }

    if (parameters.containsKey("ignore_locked_folders") && !(parameters.get("ignore_locked_folders") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: ignore_locked_folders must be of type Boolean parameters[\"ignore_locked_folders\"]");
    }

    if (parameters.containsKey("legacy_folder_matching") && !(parameters.get("legacy_folder_matching") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: legacy_folder_matching must be of type Boolean parameters[\"legacy_folder_matching\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("overwrite_files") && !(parameters.get("overwrite_files") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: overwrite_files must be of type Boolean parameters[\"overwrite_files\"]");
    }

    if (parameters.containsKey("path_time_zone") && !(parameters.get("path_time_zone") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path_time_zone must be of type String parameters[\"path_time_zone\"]");
    }

    if (parameters.containsKey("retry_on_failure_interval_in_minutes") && !(parameters.get("retry_on_failure_interval_in_minutes") instanceof Long || parameters.get("retry_on_failure_interval_in_minutes") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: retry_on_failure_interval_in_minutes must be of type Long or Integer parameters[\"retry_on_failure_interval_in_minutes\"]");
    }

    if (parameters.containsKey("retry_on_failure_number_of_attempts") && !(parameters.get("retry_on_failure_number_of_attempts") instanceof Long || parameters.get("retry_on_failure_number_of_attempts") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: retry_on_failure_number_of_attempts must be of type Long or Integer parameters[\"retry_on_failure_number_of_attempts\"]");
    }

    if (parameters.containsKey("trigger") && !(parameters.get("trigger") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: trigger must be of type String parameters[\"trigger\"]");
    }

    if (parameters.containsKey("trigger_actions") && !(parameters.get("trigger_actions") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: trigger_actions must be of type String[] parameters[\"trigger_actions\"]");
    }

    if (parameters.containsKey("value") && !(parameters.get("value") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: value must be of type Object parameters[\"value\"]");
    }

    if (parameters.containsKey("recurring_day") && !(parameters.get("recurring_day") instanceof Long || parameters.get("recurring_day") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: recurring_day must be of type Long or Integer parameters[\"recurring_day\"]");
    }

    if (parameters.containsKey("recurring_days") && !(parameters.get("recurring_days") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: recurring_days must be of type Long[] parameters[\"recurring_days\"]");
    }

    if (parameters.containsKey("automation") && !(parameters.get("automation") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: automation must be of type String parameters[\"automation\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    String apiPath = "/automations";
    TypeReference<Automation> typeReference = new TypeReference<Automation>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Upgrade a legacy Automation to Automation v2
   *
   * @see #upgrade(Long, HashMap, HashMap)
   */
  public Automation upgrade() throws RuntimeException {
    return upgrade(null, null, null);
  }

  /**
   * Upgrade a legacy Automation to Automation v2
   *
   * @see #upgrade(Long, HashMap, HashMap)
   */
  public Automation upgrade(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return upgrade(id, parameters, null);
  }

  /**
   * Upgrade a legacy Automation to Automation v2
   *
   * @see #upgrade(Long, HashMap, HashMap)
   */
  public Automation upgrade(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return upgrade(null, parameters, options);
  }

  /**
   * Upgrade a legacy Automation to Automation v2
   *
   * @param id Automation ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Automation}
   */
  public Automation upgrade(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/automations/%s/upgrade", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<Automation> typeReference = new TypeReference<Automation>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Manually Run Automation
   *
   * @see #manualRun(Long, HashMap, HashMap)
   */
  public void manualRun() throws RuntimeException {
    manualRun(null, null, null);
  }

  /**
   * Manually Run Automation
   *
   * @see #manualRun(Long, HashMap, HashMap)
   */
  public void manualRun(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    manualRun(id, parameters, null);
  }

  /**
   * Manually Run Automation
   *
   * @see #manualRun(Long, HashMap, HashMap)
   */
  public void manualRun(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    manualRun(null, parameters, options);
  }

  /**
   * Manually Run Automation
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code items} (Object[]): Initial items for a v2 manual trigger. Each item contains exactly one `file` path or `data` object.</li>
   * </ul>
   *
   * @param id Automation ID.
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

    if (parameters.containsKey("items") && !(parameters.get("items") instanceof Object[])) {
      throw new IllegalArgumentException("Bad parameter: items must be of type Object[] parameters[\"items\"]");
    }

    String apiPath = String.format("/automations/%s/manual_run", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.POST, apiPath, parameters, options);
  }

  /**
   * Update Automation
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public Automation update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Automation
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public Automation update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Automation
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public Automation update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Automation
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code source} (String): Source path/glob.  See Automation docs for exact description, but this is used to filter for files in the `path` to find files to operate on. Supports globs, except on remote mounts.</li>
   * <li>{@code destinations} (String[]): A list of destination paths. Use a trailing slash for folder destinations and omit it for file destinations.</li>
   * <li>{@code destination_replace_from} (String): If set, this string in the destination path will be replaced with the value in `destination_replace_to`.</li>
   * <li>{@code destination_replace_to} (String): If set, this string will replace the value `destination_replace_from` in the destination filename. You can use special patterns here.</li>
   * <li>{@code interval} (String): How often to run this automation? One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`</li>
   * <li>{@code path} (String): Path on which this Automation runs.  Supports globs, except on remote mounts.</li>
   * <li>{@code sync_ids} (String): A list of sync IDs the automation is associated with. If sent as a string, it should be comma-delimited.</li>
   * <li>{@code user_ids} (String): A list of user IDs the automation is associated with. If sent as a string, it should be comma-delimited.</li>
   * <li>{@code group_ids} (String): A list of group IDs the automation is associated with. If sent as a string, it should be comma-delimited.</li>
   * <li>{@code schedule_id} (Long): If trigger is `custom_schedule`, the reusable Schedule used instead of the automation's schedule fields.</li>
   * <li>{@code schedule_days_of_week} (Long[]): If trigger is `custom_schedule` without `schedule_id`, a list of days of the week to run this automation. 0 is Sunday, 1 is Monday, etc.</li>
   * <li>{@code schedule_times_of_day} (String[]): Times of day to run in HH:MM format (24-hour). Required for `custom_schedule` without `schedule_id`. Optional for `daily` triggers; if not set, runs at midnight UTC.</li>
   * <li>{@code schedule_time_zone} (String): Time zone for schedule fields. Optional for `custom_schedule` without `schedule_id` and for `daily`. If not set, times are interpreted as UTC.</li>
   * <li>{@code holiday_region} (String): Skip automation on holidays in this region. Optional for `custom_schedule` without `schedule_id` and for `daily`.</li>
   * <li>{@code always_overwrite_size_matching_files} (Boolean): Ordinarily, files with identical size in the source and destination will be skipped from copy operations to prevent wasted transfer.  If this flag is `true` we will overwrite the destination file always.  Note that this may cause large amounts of wasted transfer usage.  This setting has no effect unless `overwrite_files` is also set to `true`.</li>
   * <li>{@code always_serialize_jobs} (Boolean): Ordinarily, we will allow automation runs to run in parallel for non-scheduled automations. If this flag is `true` we will force automation runs to be serialized (run one at a time, one after another). This can resolve some issues with race conditions on remote systems at the cost of some performance.</li>
   * <li>{@code description} (String): Description for the this Automation.</li>
   * <li>{@code definition} (Object): Automation v2 graph definition.</li>
   * <li>{@code disabled} (Boolean): If true, this automation will not run.</li>
   * <li>{@code exclude_pattern} (String): If set, this glob pattern will exclude files from the automation. Supports globs, except on remote mounts.</li>
   * <li>{@code import_urls} (Object[]): List of URLs to be imported and names to be used.</li>
   * <li>{@code flatten_destination_structure} (Boolean): Normally copy and move automations that use globs will implicitly preserve the source folder structure in the destination.  If this flag is `true`, the source folder structure will be flattened in the destination.  This is useful for copying or moving files from multiple folders into a single destination folder.</li>
   * <li>{@code ignore_locked_folders} (Boolean): If true, the Lock Folders behavior will be disregarded for automated actions.</li>
   * <li>{@code legacy_folder_matching} (Boolean): DEPRECATED: If `true`, use the legacy behavior for this automation, where it can operate on folders in addition to just files.  This behavior no longer works and should not be used.</li>
   * <li>{@code name} (String): Name for this automation.</li>
   * <li>{@code overwrite_files} (Boolean): If true, existing files will be overwritten with new files on Move/Copy automations.  Note: by default files will not be overwritten on Copy automations if they appear to be the same file size as the newly incoming file.  Use the `always_overwrite_size_matching_files` option in conjunction with `overwrite_files` to override this behavior and overwrite files no matter what.</li>
   * <li>{@code path_time_zone} (String): Timezone to use when rendering timestamps in paths.</li>
   * <li>{@code retry_on_failure_interval_in_minutes} (Long): If the Automation fails, retry at this interval (in minutes).  Acceptable values are 5 through 1440 (one day).  Set to null to disable.</li>
   * <li>{@code retry_on_failure_number_of_attempts} (Long): If the Automation fails, retry at most this many times.  Maximum allowed value: 10.  Set to null to disable.</li>
   * <li>{@code trigger} (String): How this automation is triggered to run.</li>
   * <li>{@code trigger_actions} (String[]): If trigger is `action`, this is the list of action types on which to trigger the automation. Valid actions are create, copy, move, archived_delete, update, read, destroy</li>
   * <li>{@code value} (Object): A Hash of attributes specific to the automation type.</li>
   * <li>{@code recurring_day} (Long): If trigger type is `daily`, this specifies a day number to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.</li>
   * <li>{@code recurring_days} (Long[]): If trigger type is `daily`, this specifies one or more day numbers to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.</li>
   * <li>{@code automation} (String): Automation type</li>
   * </ul>
   *
   * @param id Automation ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Automation}
   */
  public Automation update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("source") && !(parameters.get("source") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: source must be of type String parameters[\"source\"]");
    }

    if (parameters.containsKey("destinations") && !(parameters.get("destinations") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: destinations must be of type String[] parameters[\"destinations\"]");
    }

    if (parameters.containsKey("destination_replace_from") && !(parameters.get("destination_replace_from") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: destination_replace_from must be of type String parameters[\"destination_replace_from\"]");
    }

    if (parameters.containsKey("destination_replace_to") && !(parameters.get("destination_replace_to") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: destination_replace_to must be of type String parameters[\"destination_replace_to\"]");
    }

    if (parameters.containsKey("interval") && !(parameters.get("interval") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: interval must be of type String parameters[\"interval\"]");
    }

    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("sync_ids") && !(parameters.get("sync_ids") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: sync_ids must be of type String parameters[\"sync_ids\"]");
    }

    if (parameters.containsKey("user_ids") && !(parameters.get("user_ids") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: user_ids must be of type String parameters[\"user_ids\"]");
    }

    if (parameters.containsKey("group_ids") && !(parameters.get("group_ids") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: group_ids must be of type String parameters[\"group_ids\"]");
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

    if (parameters.containsKey("always_overwrite_size_matching_files") && !(parameters.get("always_overwrite_size_matching_files") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: always_overwrite_size_matching_files must be of type Boolean parameters[\"always_overwrite_size_matching_files\"]");
    }

    if (parameters.containsKey("always_serialize_jobs") && !(parameters.get("always_serialize_jobs") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: always_serialize_jobs must be of type Boolean parameters[\"always_serialize_jobs\"]");
    }

    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }

    if (parameters.containsKey("definition") && !(parameters.get("definition") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: definition must be of type Object parameters[\"definition\"]");
    }

    if (parameters.containsKey("disabled") && !(parameters.get("disabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: disabled must be of type Boolean parameters[\"disabled\"]");
    }

    if (parameters.containsKey("exclude_pattern") && !(parameters.get("exclude_pattern") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: exclude_pattern must be of type String parameters[\"exclude_pattern\"]");
    }

    if (parameters.containsKey("import_urls") && !(parameters.get("import_urls") instanceof Object[])) {
      throw new IllegalArgumentException("Bad parameter: import_urls must be of type Object[] parameters[\"import_urls\"]");
    }

    if (parameters.containsKey("flatten_destination_structure") && !(parameters.get("flatten_destination_structure") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: flatten_destination_structure must be of type Boolean parameters[\"flatten_destination_structure\"]");
    }

    if (parameters.containsKey("ignore_locked_folders") && !(parameters.get("ignore_locked_folders") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: ignore_locked_folders must be of type Boolean parameters[\"ignore_locked_folders\"]");
    }

    if (parameters.containsKey("legacy_folder_matching") && !(parameters.get("legacy_folder_matching") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: legacy_folder_matching must be of type Boolean parameters[\"legacy_folder_matching\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("overwrite_files") && !(parameters.get("overwrite_files") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: overwrite_files must be of type Boolean parameters[\"overwrite_files\"]");
    }

    if (parameters.containsKey("path_time_zone") && !(parameters.get("path_time_zone") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path_time_zone must be of type String parameters[\"path_time_zone\"]");
    }

    if (parameters.containsKey("retry_on_failure_interval_in_minutes") && !(parameters.get("retry_on_failure_interval_in_minutes") instanceof Long || parameters.get("retry_on_failure_interval_in_minutes") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: retry_on_failure_interval_in_minutes must be of type Long or Integer parameters[\"retry_on_failure_interval_in_minutes\"]");
    }

    if (parameters.containsKey("retry_on_failure_number_of_attempts") && !(parameters.get("retry_on_failure_number_of_attempts") instanceof Long || parameters.get("retry_on_failure_number_of_attempts") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: retry_on_failure_number_of_attempts must be of type Long or Integer parameters[\"retry_on_failure_number_of_attempts\"]");
    }

    if (parameters.containsKey("trigger") && !(parameters.get("trigger") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: trigger must be of type String parameters[\"trigger\"]");
    }

    if (parameters.containsKey("trigger_actions") && !(parameters.get("trigger_actions") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: trigger_actions must be of type String[] parameters[\"trigger_actions\"]");
    }

    if (parameters.containsKey("value") && !(parameters.get("value") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: value must be of type Object parameters[\"value\"]");
    }

    if (parameters.containsKey("recurring_day") && !(parameters.get("recurring_day") instanceof Long || parameters.get("recurring_day") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: recurring_day must be of type Long or Integer parameters[\"recurring_day\"]");
    }

    if (parameters.containsKey("recurring_days") && !(parameters.get("recurring_days") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: recurring_days must be of type Long[] parameters[\"recurring_days\"]");
    }

    if (parameters.containsKey("automation") && !(parameters.get("automation") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: automation must be of type String parameters[\"automation\"]");
    }

    String apiPath = String.format("/automations/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<Automation> typeReference = new TypeReference<Automation>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete Automation
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Automation
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Automation
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Automation
   *
   * @param id Automation ID.
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

    String apiPath = String.format("/automations/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete Automation
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Automation
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
