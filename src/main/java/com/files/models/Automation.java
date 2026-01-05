package com.files.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.ListIterator;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
import com.files.util.UrlUtils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Automation implements ModelInterface {
  private HashMap<String, Object> options;

  public void setOptions(HashMap<String, Object> options) {
    this.options = options;
  }

  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();


  public Automation() {
    this(null, null);
  }

  public Automation(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Automation(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Automation ID
  */
  @JsonProperty("id")
  public Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /**
  * Workspace ID
  */
  @JsonProperty("workspace_id")
  public Long workspaceId;

  public Long getWorkspaceId() {
    return workspaceId;
  }

  public void setWorkspaceId(Long workspaceId) {
    this.workspaceId = workspaceId;
  }

  /**
  * Ordinarily, we will allow automation runs to run in parallel for non-scheduled automations. If this flag is `true` we will force automation runs to be serialized (run one at a time, one after another). This can resolve some issues with race conditions on remote systems at the cost of some performance.
  */
  @JsonProperty("always_serialize_jobs")
  public Boolean alwaysSerializeJobs;

  public Boolean getAlwaysSerializeJobs() {
    return alwaysSerializeJobs;
  }

  public void setAlwaysSerializeJobs(Boolean alwaysSerializeJobs) {
    this.alwaysSerializeJobs = alwaysSerializeJobs;
  }

  /**
  * Ordinarily, files with identical size in the source and destination will be skipped from copy operations to prevent wasted transfer.  If this flag is `true` we will overwrite the destination file always.  Note that this may cause large amounts of wasted transfer usage.  This setting has no effect unless `overwrite_files` is also set to `true`.
  */
  @JsonProperty("always_overwrite_size_matching_files")
  public Boolean alwaysOverwriteSizeMatchingFiles;

  public Boolean getAlwaysOverwriteSizeMatchingFiles() {
    return alwaysOverwriteSizeMatchingFiles;
  }

  public void setAlwaysOverwriteSizeMatchingFiles(Boolean alwaysOverwriteSizeMatchingFiles) {
    this.alwaysOverwriteSizeMatchingFiles = alwaysOverwriteSizeMatchingFiles;
  }

  /**
  * Automation type
  */
  @JsonProperty("automation")
  public String automation;

  public String getAutomation() {
    return automation;
  }

  public void setAutomation(String automation) {
    this.automation = automation;
  }

  /**
  * Indicates if the automation has been deleted.
  */
  @JsonProperty("deleted")
  public Boolean deleted;

  public Boolean getDeleted() {
    return deleted;
  }

  public void setDeleted(Boolean deleted) {
    this.deleted = deleted;
  }

  /**
  * Description for the this Automation.
  */
  @JsonProperty("description")
  public String description;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  /**
  * If set, this string in the destination path will be replaced with the value in `destination_replace_to`.
  */
  @JsonProperty("destination_replace_from")
  public String destinationReplaceFrom;

  public String getDestinationReplaceFrom() {
    return destinationReplaceFrom;
  }

  public void setDestinationReplaceFrom(String destinationReplaceFrom) {
    this.destinationReplaceFrom = destinationReplaceFrom;
  }

  /**
  * If set, this string will replace the value `destination_replace_from` in the destination filename. You can use special patterns here.
  */
  @JsonProperty("destination_replace_to")
  public String destinationReplaceTo;

  public String getDestinationReplaceTo() {
    return destinationReplaceTo;
  }

  public void setDestinationReplaceTo(String destinationReplaceTo) {
    this.destinationReplaceTo = destinationReplaceTo;
  }

  /**
  * Destination Paths
  */
  @JsonProperty("destinations")
  public String[] destinations;

  public String[] getDestinations() {
    return destinations;
  }

  public void setDestinations(String[] destinations) {
    this.destinations = destinations;
  }

  /**
  * If true, this automation will not run.
  */
  @JsonProperty("disabled")
  public Boolean disabled;

  public Boolean getDisabled() {
    return disabled;
  }

  public void setDisabled(Boolean disabled) {
    this.disabled = disabled;
  }

  /**
  * If set, this glob pattern will exclude files from the automation. Supports globs, except on remote mounts.
  */
  @JsonProperty("exclude_pattern")
  public String excludePattern;

  public String getExcludePattern() {
    return excludePattern;
  }

  public void setExcludePattern(String excludePattern) {
    this.excludePattern = excludePattern;
  }

  /**
  * List of URLs to be imported and names to be used.
  */
  @JsonProperty("import_urls")
  public Object[] importUrls;

  public Object[] getImportUrls() {
    return importUrls;
  }

  public void setImportUrls(Object[] importUrls) {
    this.importUrls = importUrls;
  }

  /**
  * Normally copy and move automations that use globs will implicitly preserve the source folder structure in the destination.  If this flag is `true`, the source folder structure will be flattened in the destination.  This is useful for copying or moving files from multiple folders into a single destination folder.
  */
  @JsonProperty("flatten_destination_structure")
  public Boolean flattenDestinationStructure;

  public Boolean getFlattenDestinationStructure() {
    return flattenDestinationStructure;
  }

  public void setFlattenDestinationStructure(Boolean flattenDestinationStructure) {
    this.flattenDestinationStructure = flattenDestinationStructure;
  }

  /**
  * IDs of Groups for the Automation (i.e. who to Request File from)
  */
  @JsonProperty("group_ids")
  public Long[] groupIds;

  public Long[] getGroupIds() {
    return groupIds;
  }

  public void setGroupIds(Long[] groupIds) {
    this.groupIds = groupIds;
  }

  /**
  * If true, the Lock Folders behavior will be disregarded for automated actions.
  */
  @JsonProperty("ignore_locked_folders")
  public Boolean ignoreLockedFolders;

  public Boolean getIgnoreLockedFolders() {
    return ignoreLockedFolders;
  }

  public void setIgnoreLockedFolders(Boolean ignoreLockedFolders) {
    this.ignoreLockedFolders = ignoreLockedFolders;
  }

  /**
  * If trigger is `daily`, this specifies how often to run this automation.  One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
  */
  @JsonProperty("interval")
  public String interval;

  public String getInterval() {
    return interval;
  }

  public void setInterval(String interval) {
    this.interval = interval;
  }

  /**
  * Time when automation was last modified. Does not change for name or description updates.
  */
  @JsonProperty("last_modified_at")
  public Date lastModifiedAt;

  public Date getLastModifiedAt() {
    return lastModifiedAt;
  }

  public void setLastModifiedAt(Date lastModifiedAt) {
    this.lastModifiedAt = lastModifiedAt;
  }

  /**
  * If `true`, use the legacy behavior for this automation, where it can operate on folders in addition to just files.  This behavior no longer works and should not be used.
  */
  @JsonProperty("legacy_folder_matching")
  public Boolean legacyFolderMatching;

  public Boolean getLegacyFolderMatching() {
    return legacyFolderMatching;
  }

  public void setLegacyFolderMatching(Boolean legacyFolderMatching) {
    this.legacyFolderMatching = legacyFolderMatching;
  }

  /**
  * Name for this automation.
  */
  @JsonProperty("name")
  public String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
  * If true, existing files will be overwritten with new files on Move/Copy automations.  Note: by default files will not be overwritten on Copy automations if they appear to be the same file size as the newly incoming file.  Use the `always_overwrite_size_matching_files` option in conjunction with `overwrite_files` to override this behavior and overwrite files no matter what.
  */
  @JsonProperty("overwrite_files")
  public Boolean overwriteFiles;

  public Boolean getOverwriteFiles() {
    return overwriteFiles;
  }

  public void setOverwriteFiles(Boolean overwriteFiles) {
    this.overwriteFiles = overwriteFiles;
  }

  /**
  * Path on which this Automation runs.  Supports globs, except on remote mounts. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @JsonProperty("path")
  public String path;

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  /**
  * Timezone to use when rendering timestamps in paths.
  */
  @JsonProperty("path_time_zone")
  public String pathTimeZone;

  public String getPathTimeZone() {
    return pathTimeZone;
  }

  public void setPathTimeZone(String pathTimeZone) {
    this.pathTimeZone = pathTimeZone;
  }

  /**
  * If trigger type is `daily`, this specifies a day number to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.
  */
  @JsonProperty("recurring_day")
  public Long recurringDay;

  public Long getRecurringDay() {
    return recurringDay;
  }

  public void setRecurringDay(Long recurringDay) {
    this.recurringDay = recurringDay;
  }

  /**
  * If the Automation fails, retry at this interval (in minutes).  Acceptable values are 5 through 1440 (one day).  Set to null to disable.
  */
  @JsonProperty("retry_on_failure_interval_in_minutes")
  public Long retryOnFailureIntervalInMinutes;

  public Long getRetryOnFailureIntervalInMinutes() {
    return retryOnFailureIntervalInMinutes;
  }

  public void setRetryOnFailureIntervalInMinutes(Long retryOnFailureIntervalInMinutes) {
    this.retryOnFailureIntervalInMinutes = retryOnFailureIntervalInMinutes;
  }

  /**
  * If the Automation fails, retry at most this many times.  Maximum allowed value: 10.  Set to null to disable.
  */
  @JsonProperty("retry_on_failure_number_of_attempts")
  public Long retryOnFailureNumberOfAttempts;

  public Long getRetryOnFailureNumberOfAttempts() {
    return retryOnFailureNumberOfAttempts;
  }

  public void setRetryOnFailureNumberOfAttempts(Long retryOnFailureNumberOfAttempts) {
    this.retryOnFailureNumberOfAttempts = retryOnFailureNumberOfAttempts;
  }

  /**
  * If trigger is `custom_schedule`, Custom schedule description for when the automation should be run in json format.
  */
  @JsonProperty("schedule")
  public Map<String, String> schedule;

  public Map<String, String> getSchedule() {
    return schedule;
  }

  public void setSchedule(Map<String, String> schedule) {
    this.schedule = schedule;
  }

  /**
  * If trigger is `custom_schedule`, Human readable Custom schedule description for when the automation should be run.
  */
  @JsonProperty("human_readable_schedule")
  public String humanReadableSchedule;

  public String getHumanReadableSchedule() {
    return humanReadableSchedule;
  }

  public void setHumanReadableSchedule(String humanReadableSchedule) {
    this.humanReadableSchedule = humanReadableSchedule;
  }

  /**
  * If trigger is `custom_schedule`, Custom schedule description for when the automation should be run. 0-based days of the week. 0 is Sunday, 1 is Monday, etc.
  */
  @JsonProperty("schedule_days_of_week")
  public Long[] scheduleDaysOfWeek;

  public Long[] getScheduleDaysOfWeek() {
    return scheduleDaysOfWeek;
  }

  public void setScheduleDaysOfWeek(Long[] scheduleDaysOfWeek) {
    this.scheduleDaysOfWeek = scheduleDaysOfWeek;
  }

  /**
  * If trigger is `custom_schedule`, Custom schedule description for when the automation should be run. Times of day in HH:MM format.
  */
  @JsonProperty("schedule_times_of_day")
  public String[] scheduleTimesOfDay;

  public String[] getScheduleTimesOfDay() {
    return scheduleTimesOfDay;
  }

  public void setScheduleTimesOfDay(String[] scheduleTimesOfDay) {
    this.scheduleTimesOfDay = scheduleTimesOfDay;
  }

  /**
  * If trigger is `custom_schedule`, Custom schedule Time Zone for when the automation should be run.
  */
  @JsonProperty("schedule_time_zone")
  public String scheduleTimeZone;

  public String getScheduleTimeZone() {
    return scheduleTimeZone;
  }

  public void setScheduleTimeZone(String scheduleTimeZone) {
    this.scheduleTimeZone = scheduleTimeZone;
  }

  /**
  * Source path/glob.  See Automation docs for exact description, but this is used to filter for files in the `path` to find files to operate on. Supports globs, except on remote mounts.
  */
  @JsonProperty("source")
  public String source;

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  /**
  * IDs of remote sync folder behaviors to run by this Automation
  */
  @JsonProperty("legacy_sync_ids")
  public Long[] legacySyncIds;

  public Long[] getLegacySyncIds() {
    return legacySyncIds;
  }

  public void setLegacySyncIds(Long[] legacySyncIds) {
    this.legacySyncIds = legacySyncIds;
  }

  /**
  * IDs of syncs to run by this Automation. This is the new way to specify syncs, and it is recommended to use this instead of `legacy_sync_ids`.
  */
  @JsonProperty("sync_ids")
  public Long[] syncIds;

  public Long[] getSyncIds() {
    return syncIds;
  }

  public void setSyncIds(Long[] syncIds) {
    this.syncIds = syncIds;
  }

  /**
  * If trigger is `action`, this is the list of action types on which to trigger the automation. Valid actions are create, read, update, destroy, move, archived_delete, copy
  */
  @JsonProperty("trigger_actions")
  public String[] triggerActions;

  public String[] getTriggerActions() {
    return triggerActions;
  }

  public void setTriggerActions(String[] triggerActions) {
    this.triggerActions = triggerActions;
  }

  /**
  * How this automation is triggered to run.
  */
  @JsonProperty("trigger")
  public String trigger;

  public String getTrigger() {
    return trigger;
  }

  public void setTrigger(String trigger) {
    this.trigger = trigger;
  }

  /**
  * User ID of the Automation's creator.
  */
  @JsonProperty("user_id")
  public Long userId;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  /**
  * IDs of Users for the Automation (i.e. who to Request File from)
  */
  @JsonProperty("user_ids")
  public Long[] userIds;

  public Long[] getUserIds() {
    return userIds;
  }

  public void setUserIds(Long[] userIds) {
    this.userIds = userIds;
  }

  /**
  * A Hash of attributes specific to the automation type.
  */
  @JsonProperty("value")
  public Map<String, String> value;

  public Map<String, String> getValue() {
    return value;
  }

  public void setValue(Map<String, String> value) {
    this.value = value;
  }

  /**
  * If trigger is `webhook`, this is the URL of the webhook to trigger the Automation.
  */
  @JsonProperty("webhook_url")
  public String webhookUrl;

  public String getWebhookUrl() {
    return webhookUrl;
  }

  public void setWebhookUrl(String webhookUrl) {
    this.webhookUrl = webhookUrl;
  }

  /**
  * If trigger is `custom_schedule`, the Automation will check if there is a formal, observed holiday for the region, and if so, it will not run.
  */
  @JsonProperty("holiday_region")
  public String holidayRegion;

  public String getHolidayRegion() {
    return holidayRegion;
  }

  public void setHolidayRegion(String holidayRegion) {
    this.holidayRegion = holidayRegion;
  }

  /**
  * Manually Run Automation
  */
  public void manualRun(HashMap<String, Object> parameters) throws IOException {
    Automation.manualRun(this.id, parameters, this.options);
  }

  /**
  * Parameters:
  *   source - string - Source path/glob.  See Automation docs for exact description, but this is used to filter for files in the `path` to find files to operate on. Supports globs, except on remote mounts.
  *   destinations - array(string) - A list of String destination paths or Hash of folder_path and optional file_path.
  *   destination_replace_from - string - If set, this string in the destination path will be replaced with the value in `destination_replace_to`.
  *   destination_replace_to - string - If set, this string will replace the value `destination_replace_from` in the destination filename. You can use special patterns here.
  *   interval - string - How often to run this automation? One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
  *   path - string - Path on which this Automation runs.  Supports globs, except on remote mounts.
  *   legacy_sync_ids - string - A list of legacy sync IDs the automation is associated with. If sent as a string, it should be comma-delimited.
  *   sync_ids - string - A list of sync IDs the automation is associated with. If sent as a string, it should be comma-delimited.
  *   user_ids - string - A list of user IDs the automation is associated with. If sent as a string, it should be comma-delimited.
  *   group_ids - string - A list of group IDs the automation is associated with. If sent as a string, it should be comma-delimited.
  *   schedule_days_of_week - array(int64) - If trigger is `custom_schedule`. A list of days of the week to run this automation. 0 is Sunday, 1 is Monday, etc.
  *   schedule_times_of_day - array(string) - If trigger is `custom_schedule`. A list of times of day to run this automation. 24-hour time format.
  *   schedule_time_zone - string - If trigger is `custom_schedule`. Time zone for the schedule.
  *   holiday_region - string - If trigger is `custom_schedule`, the Automation will check if there is a formal, observed holiday for the region, and if so, it will not run.
  *   always_overwrite_size_matching_files - boolean - Ordinarily, files with identical size in the source and destination will be skipped from copy operations to prevent wasted transfer.  If this flag is `true` we will overwrite the destination file always.  Note that this may cause large amounts of wasted transfer usage.  This setting has no effect unless `overwrite_files` is also set to `true`.
  *   always_serialize_jobs - boolean - Ordinarily, we will allow automation runs to run in parallel for non-scheduled automations. If this flag is `true` we will force automation runs to be serialized (run one at a time, one after another). This can resolve some issues with race conditions on remote systems at the cost of some performance.
  *   description - string - Description for the this Automation.
  *   disabled - boolean - If true, this automation will not run.
  *   exclude_pattern - string - If set, this glob pattern will exclude files from the automation. Supports globs, except on remote mounts.
  *   import_urls - array(object) - List of URLs to be imported and names to be used.
  *   flatten_destination_structure - boolean - Normally copy and move automations that use globs will implicitly preserve the source folder structure in the destination.  If this flag is `true`, the source folder structure will be flattened in the destination.  This is useful for copying or moving files from multiple folders into a single destination folder.
  *   ignore_locked_folders - boolean - If true, the Lock Folders behavior will be disregarded for automated actions.
  *   legacy_folder_matching - boolean - DEPRECATED: If `true`, use the legacy behavior for this automation, where it can operate on folders in addition to just files.  This behavior no longer works and should not be used.
  *   name - string - Name for this automation.
  *   overwrite_files - boolean - If true, existing files will be overwritten with new files on Move/Copy automations.  Note: by default files will not be overwritten on Copy automations if they appear to be the same file size as the newly incoming file.  Use the `always_overwrite_size_matching_files` option in conjunction with `overwrite_files` to override this behavior and overwrite files no matter what.
  *   path_time_zone - string - Timezone to use when rendering timestamps in paths.
  *   retry_on_failure_interval_in_minutes - int64 - If the Automation fails, retry at this interval (in minutes).  Acceptable values are 5 through 1440 (one day).  Set to null to disable.
  *   retry_on_failure_number_of_attempts - int64 - If the Automation fails, retry at most this many times.  Maximum allowed value: 10.  Set to null to disable.
  *   trigger - string - How this automation is triggered to run.
  *   trigger_actions - array(string) - If trigger is `action`, this is the list of action types on which to trigger the automation. Valid actions are create, read, update, destroy, move, archived_delete, copy
  *   value - object - A Hash of attributes specific to the automation type.
  *   recurring_day - int64 - If trigger type is `daily`, this specifies a day number to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.
  *   workspace_id - int64 - Workspace ID
  *   automation - string - Automation type
  */
  public Automation update(HashMap<String, Object> parameters) throws IOException {
    return Automation.update(this.id, parameters, this.options);
  }

  /**
  */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    Automation.delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    Automation.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `name`, `automation`, `last_modified_at` or `disabled`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `disabled`, `last_modified_at`, `workspace_id` or `automation`. Valid field combinations are `[ disabled, last_modified_at ]`, `[ workspace_id, disabled ]`, `[ disabled, automation ]`, `[ workspace_id, last_modified_at ]`, `[ automation, last_modified_at ]`, `[ workspace_id, automation ]`, `[ workspace_id, disabled, last_modified_at ]`, `[ disabled, automation, last_modified_at ]`, `[ workspace_id, disabled, automation ]`, `[ workspace_id, automation, last_modified_at ]` or `[ workspace_id, disabled, automation, last_modified_at ]`.
  *   filter_gt - object - If set, return records where the specified field is greater than the supplied value. Valid fields are `last_modified_at`.
  *   filter_gteq - object - If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `last_modified_at`.
  *   filter_lt - object - If set, return records where the specified field is less than the supplied value. Valid fields are `last_modified_at`.
  *   filter_lteq - object - If set, return records where the specified field is less than or equal the supplied value. Valid fields are `last_modified_at`.
  */
  public static ListIterator<Automation> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<Automation> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<Automation> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }
    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Map<String, String> parameters[\"sort_by\"]");
    }
    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Map<String, String> parameters[\"filter\"]");
    }
    if (parameters.containsKey("filter_gt") && !(parameters.get("filter_gt") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_gt must be of type Map<String, String> parameters[\"filter_gt\"]");
    }
    if (parameters.containsKey("filter_gteq") && !(parameters.get("filter_gteq") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_gteq must be of type Map<String, String> parameters[\"filter_gteq\"]");
    }
    if (parameters.containsKey("filter_lt") && !(parameters.get("filter_lt") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_lt must be of type Map<String, String> parameters[\"filter_lt\"]");
    }
    if (parameters.containsKey("filter_lteq") && !(parameters.get("filter_lteq") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_lteq must be of type Map<String, String> parameters[\"filter_lteq\"]");
    }


    String url = String.format("%s%s/automations", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<Automation>> typeReference = new TypeReference<List<Automation>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<Automation> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<Automation> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Automation ID.
  */
  public static Automation find() throws RuntimeException {
    return find(null, null, null);
  }

  public static Automation find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static Automation find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static Automation find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }



    String url = String.format("%s%s/automations/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<Automation> typeReference = new TypeReference<Automation>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static Automation get() throws RuntimeException {
    return get(null, null, null);
  }

  public static Automation get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   source - string - Source path/glob.  See Automation docs for exact description, but this is used to filter for files in the `path` to find files to operate on. Supports globs, except on remote mounts.
  *   destinations - array(string) - A list of String destination paths or Hash of folder_path and optional file_path.
  *   destination_replace_from - string - If set, this string in the destination path will be replaced with the value in `destination_replace_to`.
  *   destination_replace_to - string - If set, this string will replace the value `destination_replace_from` in the destination filename. You can use special patterns here.
  *   interval - string - How often to run this automation? One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
  *   path - string - Path on which this Automation runs.  Supports globs, except on remote mounts.
  *   legacy_sync_ids - string - A list of legacy sync IDs the automation is associated with. If sent as a string, it should be comma-delimited.
  *   sync_ids - string - A list of sync IDs the automation is associated with. If sent as a string, it should be comma-delimited.
  *   user_ids - string - A list of user IDs the automation is associated with. If sent as a string, it should be comma-delimited.
  *   group_ids - string - A list of group IDs the automation is associated with. If sent as a string, it should be comma-delimited.
  *   schedule_days_of_week - array(int64) - If trigger is `custom_schedule`. A list of days of the week to run this automation. 0 is Sunday, 1 is Monday, etc.
  *   schedule_times_of_day - array(string) - If trigger is `custom_schedule`. A list of times of day to run this automation. 24-hour time format.
  *   schedule_time_zone - string - If trigger is `custom_schedule`. Time zone for the schedule.
  *   holiday_region - string - If trigger is `custom_schedule`, the Automation will check if there is a formal, observed holiday for the region, and if so, it will not run.
  *   always_overwrite_size_matching_files - boolean - Ordinarily, files with identical size in the source and destination will be skipped from copy operations to prevent wasted transfer.  If this flag is `true` we will overwrite the destination file always.  Note that this may cause large amounts of wasted transfer usage.  This setting has no effect unless `overwrite_files` is also set to `true`.
  *   always_serialize_jobs - boolean - Ordinarily, we will allow automation runs to run in parallel for non-scheduled automations. If this flag is `true` we will force automation runs to be serialized (run one at a time, one after another). This can resolve some issues with race conditions on remote systems at the cost of some performance.
  *   description - string - Description for the this Automation.
  *   disabled - boolean - If true, this automation will not run.
  *   exclude_pattern - string - If set, this glob pattern will exclude files from the automation. Supports globs, except on remote mounts.
  *   import_urls - array(object) - List of URLs to be imported and names to be used.
  *   flatten_destination_structure - boolean - Normally copy and move automations that use globs will implicitly preserve the source folder structure in the destination.  If this flag is `true`, the source folder structure will be flattened in the destination.  This is useful for copying or moving files from multiple folders into a single destination folder.
  *   ignore_locked_folders - boolean - If true, the Lock Folders behavior will be disregarded for automated actions.
  *   legacy_folder_matching - boolean - DEPRECATED: If `true`, use the legacy behavior for this automation, where it can operate on folders in addition to just files.  This behavior no longer works and should not be used.
  *   name - string - Name for this automation.
  *   overwrite_files - boolean - If true, existing files will be overwritten with new files on Move/Copy automations.  Note: by default files will not be overwritten on Copy automations if they appear to be the same file size as the newly incoming file.  Use the `always_overwrite_size_matching_files` option in conjunction with `overwrite_files` to override this behavior and overwrite files no matter what.
  *   path_time_zone - string - Timezone to use when rendering timestamps in paths.
  *   retry_on_failure_interval_in_minutes - int64 - If the Automation fails, retry at this interval (in minutes).  Acceptable values are 5 through 1440 (one day).  Set to null to disable.
  *   retry_on_failure_number_of_attempts - int64 - If the Automation fails, retry at most this many times.  Maximum allowed value: 10.  Set to null to disable.
  *   trigger - string - How this automation is triggered to run.
  *   trigger_actions - array(string) - If trigger is `action`, this is the list of action types on which to trigger the automation. Valid actions are create, read, update, destroy, move, archived_delete, copy
  *   value - object - A Hash of attributes specific to the automation type.
  *   recurring_day - int64 - If trigger type is `daily`, this specifies a day number to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.
  *   workspace_id - int64 - Workspace ID
  *   automation (required) - string - Automation type
  */
  public static Automation create() throws RuntimeException {
    return create(null, null);
  }

  public static Automation create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static Automation create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


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
    if (parameters.containsKey("legacy_sync_ids") && !(parameters.get("legacy_sync_ids") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: legacy_sync_ids must be of type String parameters[\"legacy_sync_ids\"]");
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
    if (parameters.containsKey("value") && !(parameters.get("value") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: value must be of type Map<String, String> parameters[\"value\"]");
    }
    if (parameters.containsKey("recurring_day") && !(parameters.get("recurring_day") instanceof Long || parameters.get("recurring_day") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: recurring_day must be of type Long or Integer parameters[\"recurring_day\"]");
    }
    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }
    if (parameters.containsKey("automation") && !(parameters.get("automation") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: automation must be of type String parameters[\"automation\"]");
    }


    String url = String.format("%s%s/automations", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<Automation> typeReference = new TypeReference<Automation>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Manually Run Automation
  */
  public static void manualRun() throws RuntimeException {
    manualRun(null, null, null);
  }

  public static void manualRun(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    manualRun(id, parameters, null);
  }

  public static void manualRun(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    manualRun(null, parameters, options);
  }

  public static void manualRun(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }



    String url = String.format("%s%s/automations/%s/manual_run", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.POST, parameters, options);
  }


  /**
  * Parameters:
  *   source - string - Source path/glob.  See Automation docs for exact description, but this is used to filter for files in the `path` to find files to operate on. Supports globs, except on remote mounts.
  *   destinations - array(string) - A list of String destination paths or Hash of folder_path and optional file_path.
  *   destination_replace_from - string - If set, this string in the destination path will be replaced with the value in `destination_replace_to`.
  *   destination_replace_to - string - If set, this string will replace the value `destination_replace_from` in the destination filename. You can use special patterns here.
  *   interval - string - How often to run this automation? One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
  *   path - string - Path on which this Automation runs.  Supports globs, except on remote mounts.
  *   legacy_sync_ids - string - A list of legacy sync IDs the automation is associated with. If sent as a string, it should be comma-delimited.
  *   sync_ids - string - A list of sync IDs the automation is associated with. If sent as a string, it should be comma-delimited.
  *   user_ids - string - A list of user IDs the automation is associated with. If sent as a string, it should be comma-delimited.
  *   group_ids - string - A list of group IDs the automation is associated with. If sent as a string, it should be comma-delimited.
  *   schedule_days_of_week - array(int64) - If trigger is `custom_schedule`. A list of days of the week to run this automation. 0 is Sunday, 1 is Monday, etc.
  *   schedule_times_of_day - array(string) - If trigger is `custom_schedule`. A list of times of day to run this automation. 24-hour time format.
  *   schedule_time_zone - string - If trigger is `custom_schedule`. Time zone for the schedule.
  *   holiday_region - string - If trigger is `custom_schedule`, the Automation will check if there is a formal, observed holiday for the region, and if so, it will not run.
  *   always_overwrite_size_matching_files - boolean - Ordinarily, files with identical size in the source and destination will be skipped from copy operations to prevent wasted transfer.  If this flag is `true` we will overwrite the destination file always.  Note that this may cause large amounts of wasted transfer usage.  This setting has no effect unless `overwrite_files` is also set to `true`.
  *   always_serialize_jobs - boolean - Ordinarily, we will allow automation runs to run in parallel for non-scheduled automations. If this flag is `true` we will force automation runs to be serialized (run one at a time, one after another). This can resolve some issues with race conditions on remote systems at the cost of some performance.
  *   description - string - Description for the this Automation.
  *   disabled - boolean - If true, this automation will not run.
  *   exclude_pattern - string - If set, this glob pattern will exclude files from the automation. Supports globs, except on remote mounts.
  *   import_urls - array(object) - List of URLs to be imported and names to be used.
  *   flatten_destination_structure - boolean - Normally copy and move automations that use globs will implicitly preserve the source folder structure in the destination.  If this flag is `true`, the source folder structure will be flattened in the destination.  This is useful for copying or moving files from multiple folders into a single destination folder.
  *   ignore_locked_folders - boolean - If true, the Lock Folders behavior will be disregarded for automated actions.
  *   legacy_folder_matching - boolean - DEPRECATED: If `true`, use the legacy behavior for this automation, where it can operate on folders in addition to just files.  This behavior no longer works and should not be used.
  *   name - string - Name for this automation.
  *   overwrite_files - boolean - If true, existing files will be overwritten with new files on Move/Copy automations.  Note: by default files will not be overwritten on Copy automations if they appear to be the same file size as the newly incoming file.  Use the `always_overwrite_size_matching_files` option in conjunction with `overwrite_files` to override this behavior and overwrite files no matter what.
  *   path_time_zone - string - Timezone to use when rendering timestamps in paths.
  *   retry_on_failure_interval_in_minutes - int64 - If the Automation fails, retry at this interval (in minutes).  Acceptable values are 5 through 1440 (one day).  Set to null to disable.
  *   retry_on_failure_number_of_attempts - int64 - If the Automation fails, retry at most this many times.  Maximum allowed value: 10.  Set to null to disable.
  *   trigger - string - How this automation is triggered to run.
  *   trigger_actions - array(string) - If trigger is `action`, this is the list of action types on which to trigger the automation. Valid actions are create, read, update, destroy, move, archived_delete, copy
  *   value - object - A Hash of attributes specific to the automation type.
  *   recurring_day - int64 - If trigger type is `daily`, this specifies a day number to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.
  *   workspace_id - int64 - Workspace ID
  *   automation - string - Automation type
  */
  public static Automation update() throws RuntimeException {
    return update(null, null, null);
  }

  public static Automation update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static Automation update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static Automation update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

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
    if (parameters.containsKey("legacy_sync_ids") && !(parameters.get("legacy_sync_ids") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: legacy_sync_ids must be of type String parameters[\"legacy_sync_ids\"]");
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
    if (parameters.containsKey("value") && !(parameters.get("value") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: value must be of type Map<String, String> parameters[\"value\"]");
    }
    if (parameters.containsKey("recurring_day") && !(parameters.get("recurring_day") instanceof Long || parameters.get("recurring_day") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: recurring_day must be of type Long or Integer parameters[\"recurring_day\"]");
    }
    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }
    if (parameters.containsKey("automation") && !(parameters.get("automation") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: automation must be of type String parameters[\"automation\"]");
    }



    String url = String.format("%s%s/automations/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<Automation> typeReference = new TypeReference<Automation>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }



    String url = String.format("%s%s/automations/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
