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
public class Sync implements ModelInterface {
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

  // One mapper for every Sync, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  public Sync() {
    this(null, null);
  }

  public Sync(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Sync(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Sync ID
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
   * Name for this sync job
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
   * Description for this sync job
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
   * Site ID this sync belongs to
   */
  @JsonProperty("site_id")
  public Long siteId;

  public Long getSiteId() {
    return siteId;
  }

  public void setSiteId(Long siteId) {
    this.siteId = siteId;
  }

  /**
   * Workspace ID this sync belongs to
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
   * User who created or owns this sync
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
   * Absolute source path for the sync
   */
  @JsonProperty("src_path")
  public String srcPath;

  public String getSrcPath() {
    return srcPath;
  }

  public void setSrcPath(String srcPath) {
    this.srcPath = srcPath;
  }

  /**
   * Absolute destination path for the sync
   */
  @JsonProperty("dest_path")
  public String destPath;

  public String getDestPath() {
    return destPath;
  }

  public void setDestPath(String destPath) {
    this.destPath = destPath;
  }

  /**
   * Remote server ID for the source (if remote)
   */
  @JsonProperty("src_remote_server_id")
  public Long srcRemoteServerId;

  public Long getSrcRemoteServerId() {
    return srcRemoteServerId;
  }

  public void setSrcRemoteServerId(Long srcRemoteServerId) {
    this.srcRemoteServerId = srcRemoteServerId;
  }

  /**
   * Remote server ID for the destination (if remote)
   */
  @JsonProperty("dest_remote_server_id")
  public Long destRemoteServerId;

  public Long getDestRemoteServerId() {
    return destRemoteServerId;
  }

  public void setDestRemoteServerId(Long destRemoteServerId) {
    this.destRemoteServerId = destRemoteServerId;
  }

  /**
   * Source site ID if syncing from a child or partner site
   */
  @JsonProperty("src_site_id")
  public Long srcSiteId;

  public Long getSrcSiteId() {
    return srcSiteId;
  }

  public void setSrcSiteId(Long srcSiteId) {
    this.srcSiteId = srcSiteId;
  }

  /**
   * Destination site ID if syncing to a child or partner site
   */
  @JsonProperty("dest_site_id")
  public Long destSiteId;

  public Long getDestSiteId() {
    return destSiteId;
  }

  public void setDestSiteId(Long destSiteId) {
    this.destSiteId = destSiteId;
  }

  /**
   * Is this a two-way sync?
   */
  @JsonProperty("two_way")
  public Boolean twoWay;

  public Boolean getTwoWay() {
    return twoWay;
  }

  public void setTwoWay(Boolean twoWay) {
    this.twoWay = twoWay;
  }

  /**
   * Keep files after copying?
   */
  @JsonProperty("keep_after_copy")
  public Boolean keepAfterCopy;

  public Boolean getKeepAfterCopy() {
    return keepAfterCopy;
  }

  public void setKeepAfterCopy(Boolean keepAfterCopy) {
    this.keepAfterCopy = keepAfterCopy;
  }

  /**
   * Delete empty folders after sync?
   */
  @JsonProperty("delete_empty_folders")
  public Boolean deleteEmptyFolders;

  public Boolean getDeleteEmptyFolders() {
    return deleteEmptyFolders;
  }

  public void setDeleteEmptyFolders(Boolean deleteEmptyFolders) {
    this.deleteEmptyFolders = deleteEmptyFolders;
  }

  /**
   * Is this sync disabled?
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
   * Trigger type: daily, custom_schedule, or manual
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
   * Some MFT services request an empty file (known as a trigger file) to signal the sync is complete and they can begin further processing. If trigger_file is set, a zero-byte file will be sent at the end of the sync.
   */
  @JsonProperty("trigger_file")
  public String triggerFile;

  public String getTriggerFile() {
    return triggerFile;
  }

  public void setTriggerFile(String triggerFile) {
    this.triggerFile = triggerFile;
  }

  /**
   * If true, the trigger file will be sent at the end of a successful sync even when no files were transferred.
   */
  @JsonProperty("always_write_trigger_file")
  public Boolean alwaysWriteTriggerFile;

  public Boolean getAlwaysWriteTriggerFile() {
    return alwaysWriteTriggerFile;
  }

  public void setAlwaysWriteTriggerFile(Boolean alwaysWriteTriggerFile) {
    this.alwaysWriteTriggerFile = alwaysWriteTriggerFile;
  }

  /**
   * Array of glob patterns to include
   */
  @JsonProperty("include_patterns")
  public String[] includePatterns;

  public String[] getIncludePatterns() {
    return includePatterns;
  }

  public void setIncludePatterns(String[] includePatterns) {
    this.includePatterns = includePatterns;
  }

  /**
   * Array of glob patterns to exclude
   */
  @JsonProperty("exclude_patterns")
  public String[] excludePatterns;

  public String[] getExcludePatterns() {
    return excludePatterns;
  }

  public void setExcludePatterns(String[] excludePatterns) {
    this.excludePatterns = excludePatterns;
  }

  /**
   * When this sync was created
   */
  @JsonProperty("created_at")
  public Date createdAt;

  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * When this sync was last updated
   */
  @JsonProperty("updated_at")
  public Date updatedAt;

  public Date getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Frequency in minutes between syncs. If set, this value must be greater than or equal to the `remote_sync_interval` value for the site's plan. If left blank, the plan's `remote_sync_interval` will be used. This setting is only used if `trigger` is empty.
   */
  @JsonProperty("sync_interval_minutes")
  public Long syncIntervalMinutes;

  public Long getSyncIntervalMinutes() {
    return syncIntervalMinutes;
  }

  public void setSyncIntervalMinutes(Long syncIntervalMinutes) {
    this.syncIntervalMinutes = syncIntervalMinutes;
  }

  /**
   * If trigger is `daily`, this specifies how often to run this sync.  One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
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
   * If trigger is `custom_schedule`, the reusable Schedule used instead of the sync's schedule fields.
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
   * If trigger is `custom_schedule`, Custom schedule description for when the sync should be run. 0-based days of the week. 0 is Sunday, 1 is Monday, etc.
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
   * Times of day to run in HH:MM format. For `custom_schedule`, run at these times on specified days of week. For `daily`, run at these times on the scheduled interval date.
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
   * Skip the sync if there is a formal, observed holiday for this region.
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
   * The latest run of this sync
   */
  @JsonProperty("latest_sync_run")
  public SyncRun latestSyncRun;

  public SyncRun getLatestSyncRun() {
    return latestSyncRun;
  }

  public void setLatestSyncRun(SyncRun latestSyncRun) {
    this.latestSyncRun = latestSyncRun;
  }

  /**
   * Dry Run Sync
   */
  public void dryRun(HashMap<String, Object> parameters) throws IOException {
    filesClient().syncs().dryRun(this.id, parameters, this.options);
  }

  /**
   * Manually Run Sync
   */
  public void manualRun(HashMap<String, Object> parameters) throws IOException {
    filesClient().syncs().manualRun(this.id, parameters, this.options);
  }

  /**
   * Updates this Sync. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Parameters:
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
  public Sync update(HashMap<String, Object> parameters) throws IOException {
    Sync updated = filesClient().syncs().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().syncs().delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this Sync: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().syncs().create(parameters, this.options));
    }
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
    if (this.excludePatterns != null) {
      parameters.put("exclude_patterns", this.excludePatterns);
    }
    if (this.includePatterns != null) {
      parameters.put("include_patterns", this.includePatterns);
    }
    if (this.recurringDays != null) {
      parameters.put("recurring_days", this.recurringDays);
    }
    if (this.scheduleDaysOfWeek != null) {
      parameters.put("schedule_days_of_week", this.scheduleDaysOfWeek);
    }
    if (this.scheduleTimesOfDay != null) {
      parameters.put("schedule_times_of_day", this.scheduleTimesOfDay);
    }
    return parameters;
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(Sync canonical) {
    this.id = canonical.id;
    this.name = canonical.name;
    this.description = canonical.description;
    this.siteId = canonical.siteId;
    this.workspaceId = canonical.workspaceId;
    this.userId = canonical.userId;
    this.srcPath = canonical.srcPath;
    this.destPath = canonical.destPath;
    this.srcRemoteServerId = canonical.srcRemoteServerId;
    this.destRemoteServerId = canonical.destRemoteServerId;
    this.srcSiteId = canonical.srcSiteId;
    this.destSiteId = canonical.destSiteId;
    this.twoWay = canonical.twoWay;
    this.keepAfterCopy = canonical.keepAfterCopy;
    this.deleteEmptyFolders = canonical.deleteEmptyFolders;
    this.disabled = canonical.disabled;
    this.trigger = canonical.trigger;
    this.triggerFile = canonical.triggerFile;
    this.alwaysWriteTriggerFile = canonical.alwaysWriteTriggerFile;
    this.includePatterns = canonical.includePatterns;
    this.excludePatterns = canonical.excludePatterns;
    this.createdAt = canonical.createdAt;
    this.updatedAt = canonical.updatedAt;
    this.syncIntervalMinutes = canonical.syncIntervalMinutes;
    this.interval = canonical.interval;
    this.recurringDay = canonical.recurringDay;
    this.recurringDays = canonical.recurringDays;
    this.scheduleId = canonical.scheduleId;
    this.scheduleDaysOfWeek = canonical.scheduleDaysOfWeek;
    this.scheduleTimesOfDay = canonical.scheduleTimesOfDay;
    this.scheduleTimeZone = canonical.scheduleTimeZone;
    this.holidayRegion = canonical.holidayRegion;
    this.latestSyncRun = canonical.latestSyncRun;
  }

  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `site_id`, `workspace_id` or `name`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id`, `disabled`, `src_remote_server_id` or `dest_remote_server_id`. Valid field combinations are `[ workspace_id, disabled ]`, `[ workspace_id, src_remote_server_id ]`, `[ workspace_id, dest_remote_server_id ]`, `[ disabled, src_remote_server_id ]`, `[ disabled, dest_remote_server_id ]`, `[ workspace_id, disabled, src_remote_server_id ]` or `[ workspace_id, disabled, dest_remote_server_id ]`.
   */
  public static ListIterator<Sync> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<Sync> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<Sync> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().syncs().list(parameters, options);
  }

  public static ListIterator<Sync> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<Sync> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Sync ID.
   */
  public static Sync find() throws RuntimeException {
    return find(null, null, null);
  }

  public static Sync find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static Sync find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static Sync find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().syncs().find(id, parameters, options);
  }

  public static Sync get() throws RuntimeException {
    return get(null, null, null);
  }

  public static Sync get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public static Sync create() throws RuntimeException {
    return create(null, null);
  }

  public static Sync create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static Sync create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().syncs().create(parameters, options);
  }


  /**
   * Dry Run Sync
   */
  public static void dryRun() throws RuntimeException {
    dryRun(null, null, null);
  }

  public static void dryRun(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    dryRun(id, parameters, null);
  }

  public static void dryRun(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    dryRun(null, parameters, options);
  }

  public static void dryRun(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().syncs().dryRun(id, parameters, options);
  }


  /**
   * Manually Run Sync
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
    FilesClient.defaultClient().syncs().manualRun(id, parameters, options);
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
  public static Sync update() throws RuntimeException {
    return update(null, null, null);
  }

  public static Sync update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static Sync update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static Sync update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().syncs().update(id, parameters, options);
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
    FilesClient.defaultClient().syncs().delete(id, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
