package com.files.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.ListIterator;
import com.files.net.ClientRuntime;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
import com.files.util.PathUtils;
import com.files.util.UrlUtils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
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
  private FilesClient filesClient;

  /**
   * Sets the request options, such as {@code api_key} or {@code workspace_id}, that this object's
   * own operations send. The map is copied; options that control a single call, such as a
   * {@code cancellation} token or a {@code timeout}, are not kept.
   */
  public void setOptions(HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
  }

  @JsonIgnore
  @Override
  public void setClient(FilesClient client) {
    this.filesClient = client;
  }

  // The client this object's own operations run with: the one that returned it, or one set with
  // setClient, and otherwise the default client as it is when the operation runs.
  private FilesClient filesClient() {
    return filesClient != null ? filesClient : FilesClient.defaultClient();
  }

  // One mapper for every Automation, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  public Automation() {
    this(null, null);
  }

  public Automation(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Automation(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
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
   * Automation v2 graph definition.
   */
  @JsonProperty("definition")
  public Object definition;

  public Object getDefinition() {
    return definition;
  }

  public void setDefinition(Object definition) {
    this.definition = definition;
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
   * If trigger is `email`, this is the address that triggers the Automation.
   */
  @JsonProperty("inbound_email_address")
  public String inboundEmailAddress;

  public String getInboundEmailAddress() {
    return inboundEmailAddress;
  }

  public void setInboundEmailAddress(String inboundEmailAddress) {
    this.inboundEmailAddress = inboundEmailAddress;
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
   * Current Automation v2 definition version.
   */
  @JsonProperty("version")
  public Long version;

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
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
   * If trigger type is `daily`, this specifies one or more day numbers to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.
   */
  @JsonProperty("recurring_days")
  public Long[] recurringDays;

  public Long[] getRecurringDays() {
    return recurringDays;
  }

  public void setRecurringDays(Long[] recurringDays) {
    this.recurringDays = recurringDays;
  }

  /**
   * If trigger is `custom_schedule`, the reusable Schedule used instead of the automation's schedule fields.
   */
  @JsonProperty("schedule_id")
  public Long scheduleId;

  public Long getScheduleId() {
    return scheduleId;
  }

  public void setScheduleId(Long scheduleId) {
    this.scheduleId = scheduleId;
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
  public Object schedule;

  public Object getSchedule() {
    return schedule;
  }

  public void setSchedule(Object schedule) {
    this.schedule = schedule;
  }

  /**
   * If trigger is `custom_schedule` or `daily` with times, Human readable schedule description for when the automation should be run.
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
   * If trigger is `custom_schedule`, Custom schedule description for when the automation should be run. 0 is Sunday, 1 is Monday, etc.
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
   * Times of day to run in HH:MM format (24-hour). For `custom_schedule`, run at these times on specified days of week. For `daily`, run at these times on the scheduled interval date.
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
   * Time zone for the schedule. If not set, times are interpreted as UTC.
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
   * IDs of Syncs to run by this Automation.
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
   * If trigger is `action`, this is the list of action types on which to trigger the automation. Valid actions are create, copy, move, archived_delete, update, read, destroy
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
  public Object value;

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
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
   * Skip the automation if there is a formal, observed holiday for this region.
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
   * Upgrade a legacy Automation to Automation v2
   */
  public Automation upgrade(HashMap<String, Object> parameters) throws IOException {
    return filesClient().automations().upgrade(this.id, parameters, this.options);
  }

  /**
   * Manually Run Automation
   *
   * Parameters:
   *   items - array(object) - Initial items for a v2 manual trigger. Each item contains exactly one `file` path or `data` object.
   */
  public void manualRun(HashMap<String, Object> parameters) throws IOException {
    filesClient().automations().manualRun(this.id, parameters, this.options);
  }

  /**
   * Updates this Automation. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Parameters:
   *   source - string - Source path/glob.  See Automation docs for exact description, but this is used to filter for files in the `path` to find files to operate on. Supports globs, except on remote mounts.
   *   destinations - array(string) - A list of destination paths. Use a trailing slash for folder destinations and omit it for file destinations.
   *   destination_replace_from - string - If set, this string in the destination path will be replaced with the value in `destination_replace_to`.
   *   destination_replace_to - string - If set, this string will replace the value `destination_replace_from` in the destination filename. You can use special patterns here.
   *   interval - string - How often to run this automation? One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
   *   path - string - Path on which this Automation runs.  Supports globs, except on remote mounts.
   *   sync_ids - string - A list of sync IDs the automation is associated with. If sent as a string, it should be comma-delimited.
   *   user_ids - string - A list of user IDs the automation is associated with. If sent as a string, it should be comma-delimited.
   *   group_ids - string - A list of group IDs the automation is associated with. If sent as a string, it should be comma-delimited.
   *   schedule_id - int64 - If trigger is `custom_schedule`, the reusable Schedule used instead of the automation's schedule fields.
   *   schedule_days_of_week - array(int64) - If trigger is `custom_schedule` without `schedule_id`, a list of days of the week to run this automation. 0 is Sunday, 1 is Monday, etc.
   *   schedule_times_of_day - array(string) - Times of day to run in HH:MM format (24-hour). Required for `custom_schedule` without `schedule_id`. Optional for `daily` triggers; if not set, runs at midnight UTC.
   *   schedule_time_zone - string - Time zone for schedule fields. Optional for `custom_schedule` without `schedule_id` and for `daily`. If not set, times are interpreted as UTC.
   *   holiday_region - string - Skip automation on holidays in this region. Optional for `custom_schedule` without `schedule_id` and for `daily`.
   *   always_overwrite_size_matching_files - boolean - Ordinarily, files with identical size in the source and destination will be skipped from copy operations to prevent wasted transfer.  If this flag is `true` we will overwrite the destination file always.  Note that this may cause large amounts of wasted transfer usage.  This setting has no effect unless `overwrite_files` is also set to `true`.
   *   always_serialize_jobs - boolean - Ordinarily, we will allow automation runs to run in parallel for non-scheduled automations. If this flag is `true` we will force automation runs to be serialized (run one at a time, one after another). This can resolve some issues with race conditions on remote systems at the cost of some performance.
   *   description - string - Description for the this Automation.
   *   definition - object - Automation v2 graph definition.
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
   *   trigger_actions - array(string) - If trigger is `action`, this is the list of action types on which to trigger the automation. Valid actions are create, copy, move, archived_delete, update, read, destroy
   *   value - object - A Hash of attributes specific to the automation type.
   *   recurring_day - int64 - If trigger type is `daily`, this specifies a day number to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.
   *   recurring_days - array(int64) - If trigger type is `daily`, this specifies one or more day numbers to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.
   *   automation - string - Automation type
   */
  public Automation update(HashMap<String, Object> parameters) throws IOException {
    Automation updated = filesClient().automations().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().automations().delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this Automation: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().automations().create(parameters, this.options));
    }
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
    if (this.destinations != null) {
      parameters.put("destinations", this.destinations);
    }
    if (this.syncIds != null) {
      parameters.put("sync_ids", ModelUtils.commaDelimited(this.syncIds));
    }
    if (this.userIds != null) {
      parameters.put("user_ids", ModelUtils.commaDelimited(this.userIds));
    }
    if (this.groupIds != null) {
      parameters.put("group_ids", ModelUtils.commaDelimited(this.groupIds));
    }
    if (this.scheduleDaysOfWeek != null) {
      parameters.put("schedule_days_of_week", this.scheduleDaysOfWeek);
    }
    if (this.scheduleTimesOfDay != null) {
      parameters.put("schedule_times_of_day", this.scheduleTimesOfDay);
    }
    if (this.importUrls != null) {
      parameters.put("import_urls", this.importUrls);
    }
    if (this.triggerActions != null) {
      parameters.put("trigger_actions", this.triggerActions);
    }
    if (this.recurringDays != null) {
      parameters.put("recurring_days", this.recurringDays);
    }
    return parameters;
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(Automation canonical) {
    this.id = canonical.id;
    this.workspaceId = canonical.workspaceId;
    this.alwaysSerializeJobs = canonical.alwaysSerializeJobs;
    this.alwaysOverwriteSizeMatchingFiles = canonical.alwaysOverwriteSizeMatchingFiles;
    this.automation = canonical.automation;
    this.deleted = canonical.deleted;
    this.description = canonical.description;
    this.definition = canonical.definition;
    this.destinationReplaceFrom = canonical.destinationReplaceFrom;
    this.destinationReplaceTo = canonical.destinationReplaceTo;
    this.destinations = canonical.destinations;
    this.disabled = canonical.disabled;
    this.excludePattern = canonical.excludePattern;
    this.importUrls = canonical.importUrls;
    this.inboundEmailAddress = canonical.inboundEmailAddress;
    this.flattenDestinationStructure = canonical.flattenDestinationStructure;
    this.groupIds = canonical.groupIds;
    this.ignoreLockedFolders = canonical.ignoreLockedFolders;
    this.interval = canonical.interval;
    this.lastModifiedAt = canonical.lastModifiedAt;
    this.legacyFolderMatching = canonical.legacyFolderMatching;
    this.name = canonical.name;
    this.overwriteFiles = canonical.overwriteFiles;
    this.path = canonical.path;
    this.pathTimeZone = canonical.pathTimeZone;
    this.version = canonical.version;
    this.recurringDay = canonical.recurringDay;
    this.recurringDays = canonical.recurringDays;
    this.scheduleId = canonical.scheduleId;
    this.retryOnFailureIntervalInMinutes = canonical.retryOnFailureIntervalInMinutes;
    this.retryOnFailureNumberOfAttempts = canonical.retryOnFailureNumberOfAttempts;
    this.schedule = canonical.schedule;
    this.humanReadableSchedule = canonical.humanReadableSchedule;
    this.scheduleDaysOfWeek = canonical.scheduleDaysOfWeek;
    this.scheduleTimesOfDay = canonical.scheduleTimesOfDay;
    this.scheduleTimeZone = canonical.scheduleTimeZone;
    this.source = canonical.source;
    this.syncIds = canonical.syncIds;
    this.triggerActions = canonical.triggerActions;
    this.trigger = canonical.trigger;
    this.userId = canonical.userId;
    this.userIds = canonical.userIds;
    this.value = canonical.value;
    this.webhookUrl = canonical.webhookUrl;
    this.holidayRegion = canonical.holidayRegion;
  }

  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
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
    return FilesClient.defaultClient().automations().list(parameters, options);
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
    return FilesClient.defaultClient().automations().find(id, parameters, options);
  }

  public static Automation get() throws RuntimeException {
    return get(null, null, null);
  }

  public static Automation get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   */
  public static AutomationAuthoringSchema getAuthoringSchema() throws RuntimeException {
    return getAuthoringSchema(null, null);
  }

  public static AutomationAuthoringSchema getAuthoringSchema(HashMap<String, Object> parameters) throws RuntimeException {
    return getAuthoringSchema(parameters, null);
  }


  public static AutomationAuthoringSchema getAuthoringSchema(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().automations().getAuthoringSchema(parameters, options);
  }


  /**
   * Parameters:
   *   source - string - Source path/glob.  See Automation docs for exact description, but this is used to filter for files in the `path` to find files to operate on. Supports globs, except on remote mounts.
   *   destinations - array(string) - A list of destination paths. Use a trailing slash for folder destinations and omit it for file destinations.
   *   destination_replace_from - string - If set, this string in the destination path will be replaced with the value in `destination_replace_to`.
   *   destination_replace_to - string - If set, this string will replace the value `destination_replace_from` in the destination filename. You can use special patterns here.
   *   interval - string - How often to run this automation? One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
   *   path - string - Path on which this Automation runs.  Supports globs, except on remote mounts.
   *   sync_ids - string - A list of sync IDs the automation is associated with. If sent as a string, it should be comma-delimited.
   *   user_ids - string - A list of user IDs the automation is associated with. If sent as a string, it should be comma-delimited.
   *   group_ids - string - A list of group IDs the automation is associated with. If sent as a string, it should be comma-delimited.
   *   schedule_id - int64 - If trigger is `custom_schedule`, the reusable Schedule used instead of the automation's schedule fields.
   *   schedule_days_of_week - array(int64) - If trigger is `custom_schedule` without `schedule_id`, a list of days of the week to run this automation. 0 is Sunday, 1 is Monday, etc.
   *   schedule_times_of_day - array(string) - Times of day to run in HH:MM format (24-hour). Required for `custom_schedule` without `schedule_id`. Optional for `daily` triggers; if not set, runs at midnight UTC.
   *   schedule_time_zone - string - Time zone for schedule fields. Optional for `custom_schedule` without `schedule_id` and for `daily`. If not set, times are interpreted as UTC.
   *   holiday_region - string - Skip automation on holidays in this region. Optional for `custom_schedule` without `schedule_id` and for `daily`.
   *   always_overwrite_size_matching_files - boolean - Ordinarily, files with identical size in the source and destination will be skipped from copy operations to prevent wasted transfer.  If this flag is `true` we will overwrite the destination file always.  Note that this may cause large amounts of wasted transfer usage.  This setting has no effect unless `overwrite_files` is also set to `true`.
   *   always_serialize_jobs - boolean - Ordinarily, we will allow automation runs to run in parallel for non-scheduled automations. If this flag is `true` we will force automation runs to be serialized (run one at a time, one after another). This can resolve some issues with race conditions on remote systems at the cost of some performance.
   *   description - string - Description for the this Automation.
   *   definition - object - Automation v2 graph definition.
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
   *   trigger_actions - array(string) - If trigger is `action`, this is the list of action types on which to trigger the automation. Valid actions are create, copy, move, archived_delete, update, read, destroy
   *   value - object - A Hash of attributes specific to the automation type.
   *   recurring_day - int64 - If trigger type is `daily`, this specifies a day number to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.
   *   recurring_days - array(int64) - If trigger type is `daily`, this specifies one or more day numbers to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.
   *   automation (required) - string - Automation type
   *   workspace_id - int64 - Workspace ID
   */
  public static Automation create() throws RuntimeException {
    return create(null, null);
  }

  public static Automation create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static Automation create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().automations().create(parameters, options);
  }


  /**
   * Upgrade a legacy Automation to Automation v2
   */
  public static Automation upgrade() throws RuntimeException {
    return upgrade(null, null, null);
  }

  public static Automation upgrade(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return upgrade(id, parameters, null);
  }

  public static Automation upgrade(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return upgrade(null, parameters, options);
  }

  public static Automation upgrade(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().automations().upgrade(id, parameters, options);
  }


  /**
   * Manually Run Automation
   *
   * Parameters:
   *   items - array(object) - Initial items for a v2 manual trigger. Each item contains exactly one `file` path or `data` object.
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
    FilesClient.defaultClient().automations().manualRun(id, parameters, options);
  }


  /**
   * Parameters:
   *   source - string - Source path/glob.  See Automation docs for exact description, but this is used to filter for files in the `path` to find files to operate on. Supports globs, except on remote mounts.
   *   destinations - array(string) - A list of destination paths. Use a trailing slash for folder destinations and omit it for file destinations.
   *   destination_replace_from - string - If set, this string in the destination path will be replaced with the value in `destination_replace_to`.
   *   destination_replace_to - string - If set, this string will replace the value `destination_replace_from` in the destination filename. You can use special patterns here.
   *   interval - string - How often to run this automation? One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
   *   path - string - Path on which this Automation runs.  Supports globs, except on remote mounts.
   *   sync_ids - string - A list of sync IDs the automation is associated with. If sent as a string, it should be comma-delimited.
   *   user_ids - string - A list of user IDs the automation is associated with. If sent as a string, it should be comma-delimited.
   *   group_ids - string - A list of group IDs the automation is associated with. If sent as a string, it should be comma-delimited.
   *   schedule_id - int64 - If trigger is `custom_schedule`, the reusable Schedule used instead of the automation's schedule fields.
   *   schedule_days_of_week - array(int64) - If trigger is `custom_schedule` without `schedule_id`, a list of days of the week to run this automation. 0 is Sunday, 1 is Monday, etc.
   *   schedule_times_of_day - array(string) - Times of day to run in HH:MM format (24-hour). Required for `custom_schedule` without `schedule_id`. Optional for `daily` triggers; if not set, runs at midnight UTC.
   *   schedule_time_zone - string - Time zone for schedule fields. Optional for `custom_schedule` without `schedule_id` and for `daily`. If not set, times are interpreted as UTC.
   *   holiday_region - string - Skip automation on holidays in this region. Optional for `custom_schedule` without `schedule_id` and for `daily`.
   *   always_overwrite_size_matching_files - boolean - Ordinarily, files with identical size in the source and destination will be skipped from copy operations to prevent wasted transfer.  If this flag is `true` we will overwrite the destination file always.  Note that this may cause large amounts of wasted transfer usage.  This setting has no effect unless `overwrite_files` is also set to `true`.
   *   always_serialize_jobs - boolean - Ordinarily, we will allow automation runs to run in parallel for non-scheduled automations. If this flag is `true` we will force automation runs to be serialized (run one at a time, one after another). This can resolve some issues with race conditions on remote systems at the cost of some performance.
   *   description - string - Description for the this Automation.
   *   definition - object - Automation v2 graph definition.
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
   *   trigger_actions - array(string) - If trigger is `action`, this is the list of action types on which to trigger the automation. Valid actions are create, copy, move, archived_delete, update, read, destroy
   *   value - object - A Hash of attributes specific to the automation type.
   *   recurring_day - int64 - If trigger type is `daily`, this specifies a day number to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.
   *   recurring_days - array(int64) - If trigger type is `daily`, this specifies one or more day numbers to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.
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
    return FilesClient.defaultClient().automations().update(id, parameters, options);
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
    FilesClient.defaultClient().automations().delete(id, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
