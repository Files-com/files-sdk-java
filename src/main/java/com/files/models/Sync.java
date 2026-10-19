package com.files.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.net.ClientRuntime;
import com.files.util.ModelUtils;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

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

  /**
   * Sets the client this object's own operations run with.
   *
   * @param client the client
   */
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


  /**
   * Creates a {@code Sync} with no attributes set.
   */
  public Sync() {
    this(null, null);
  }

  /**
   * Creates a {@code Sync} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public Sync(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code Sync} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
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

  /**
   * Gets {@link #id}.
   *
   * @return the value of {@link #id}
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets {@link #id}.
   *
   * @param id the new value
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Name for this sync job
   */
  @JsonProperty("name")
  public String name;

  /**
   * Gets {@link #name}.
   *
   * @return the value of {@link #name}
   */
  public String getName() {
    return name;
  }

  /**
   * Sets {@link #name}.
   *
   * @param name the new value
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Description for this sync job
   */
  @JsonProperty("description")
  public String description;

  /**
   * Gets {@link #description}.
   *
   * @return the value of {@link #description}
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets {@link #description}.
   *
   * @param description the new value
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Site ID this sync belongs to
   */
  @JsonProperty("site_id")
  public Long siteId;

  /**
   * Gets {@link #siteId}.
   *
   * @return the value of {@link #siteId}
   */
  public Long getSiteId() {
    return siteId;
  }

  /**
   * Sets {@link #siteId}.
   *
   * @param siteId the new value
   */
  public void setSiteId(Long siteId) {
    this.siteId = siteId;
  }

  /**
   * Workspace ID this sync belongs to
   */
  @JsonProperty("workspace_id")
  public Long workspaceId;

  /**
   * Gets {@link #workspaceId}.
   *
   * @return the value of {@link #workspaceId}
   */
  public Long getWorkspaceId() {
    return workspaceId;
  }

  /**
   * Sets {@link #workspaceId}.
   *
   * @param workspaceId the new value
   */
  public void setWorkspaceId(Long workspaceId) {
    this.workspaceId = workspaceId;
  }

  /**
   * User who created or owns this sync
   */
  @JsonProperty("user_id")
  public Long userId;

  /**
   * Gets {@link #userId}.
   *
   * @return the value of {@link #userId}
   */
  public Long getUserId() {
    return userId;
  }

  /**
   * Sets {@link #userId}.
   *
   * @param userId the new value
   */
  public void setUserId(Long userId) {
    this.userId = userId;
  }

  /**
   * Absolute source path for the sync
   */
  @JsonProperty("src_path")
  public String srcPath;

  /**
   * Gets {@link #srcPath}.
   *
   * @return the value of {@link #srcPath}
   */
  public String getSrcPath() {
    return srcPath;
  }

  /**
   * Sets {@link #srcPath}.
   *
   * @param srcPath the new value
   */
  public void setSrcPath(String srcPath) {
    this.srcPath = srcPath;
  }

  /**
   * Absolute destination path for the sync
   */
  @JsonProperty("dest_path")
  public String destPath;

  /**
   * Gets {@link #destPath}.
   *
   * @return the value of {@link #destPath}
   */
  public String getDestPath() {
    return destPath;
  }

  /**
   * Sets {@link #destPath}.
   *
   * @param destPath the new value
   */
  public void setDestPath(String destPath) {
    this.destPath = destPath;
  }

  /**
   * Remote server ID for the source (if remote)
   */
  @JsonProperty("src_remote_server_id")
  public Long srcRemoteServerId;

  /**
   * Gets {@link #srcRemoteServerId}.
   *
   * @return the value of {@link #srcRemoteServerId}
   */
  public Long getSrcRemoteServerId() {
    return srcRemoteServerId;
  }

  /**
   * Sets {@link #srcRemoteServerId}.
   *
   * @param srcRemoteServerId the new value
   */
  public void setSrcRemoteServerId(Long srcRemoteServerId) {
    this.srcRemoteServerId = srcRemoteServerId;
  }

  /**
   * Remote server ID for the destination (if remote)
   */
  @JsonProperty("dest_remote_server_id")
  public Long destRemoteServerId;

  /**
   * Gets {@link #destRemoteServerId}.
   *
   * @return the value of {@link #destRemoteServerId}
   */
  public Long getDestRemoteServerId() {
    return destRemoteServerId;
  }

  /**
   * Sets {@link #destRemoteServerId}.
   *
   * @param destRemoteServerId the new value
   */
  public void setDestRemoteServerId(Long destRemoteServerId) {
    this.destRemoteServerId = destRemoteServerId;
  }

  /**
   * Source site ID if syncing from a child or partner site
   */
  @JsonProperty("src_site_id")
  public Long srcSiteId;

  /**
   * Gets {@link #srcSiteId}.
   *
   * @return the value of {@link #srcSiteId}
   */
  public Long getSrcSiteId() {
    return srcSiteId;
  }

  /**
   * Sets {@link #srcSiteId}.
   *
   * @param srcSiteId the new value
   */
  public void setSrcSiteId(Long srcSiteId) {
    this.srcSiteId = srcSiteId;
  }

  /**
   * Destination site ID if syncing to a child or partner site
   */
  @JsonProperty("dest_site_id")
  public Long destSiteId;

  /**
   * Gets {@link #destSiteId}.
   *
   * @return the value of {@link #destSiteId}
   */
  public Long getDestSiteId() {
    return destSiteId;
  }

  /**
   * Sets {@link #destSiteId}.
   *
   * @param destSiteId the new value
   */
  public void setDestSiteId(Long destSiteId) {
    this.destSiteId = destSiteId;
  }

  /**
   * Is this a two-way sync?
   */
  @JsonProperty("two_way")
  public Boolean twoWay;

  /**
   * Gets {@link #twoWay}.
   *
   * @return the value of {@link #twoWay}
   */
  public Boolean getTwoWay() {
    return twoWay;
  }

  /**
   * Sets {@link #twoWay}.
   *
   * @param twoWay the new value
   */
  public void setTwoWay(Boolean twoWay) {
    this.twoWay = twoWay;
  }

  /**
   * Keep files after copying?
   */
  @JsonProperty("keep_after_copy")
  public Boolean keepAfterCopy;

  /**
   * Gets {@link #keepAfterCopy}.
   *
   * @return the value of {@link #keepAfterCopy}
   */
  public Boolean getKeepAfterCopy() {
    return keepAfterCopy;
  }

  /**
   * Sets {@link #keepAfterCopy}.
   *
   * @param keepAfterCopy the new value
   */
  public void setKeepAfterCopy(Boolean keepAfterCopy) {
    this.keepAfterCopy = keepAfterCopy;
  }

  /**
   * Delete empty folders after sync?
   */
  @JsonProperty("delete_empty_folders")
  public Boolean deleteEmptyFolders;

  /**
   * Gets {@link #deleteEmptyFolders}.
   *
   * @return the value of {@link #deleteEmptyFolders}
   */
  public Boolean getDeleteEmptyFolders() {
    return deleteEmptyFolders;
  }

  /**
   * Sets {@link #deleteEmptyFolders}.
   *
   * @param deleteEmptyFolders the new value
   */
  public void setDeleteEmptyFolders(Boolean deleteEmptyFolders) {
    this.deleteEmptyFolders = deleteEmptyFolders;
  }

  /**
   * Is this sync disabled?
   */
  @JsonProperty("disabled")
  public Boolean disabled;

  /**
   * Gets {@link #disabled}.
   *
   * @return the value of {@link #disabled}
   */
  public Boolean getDisabled() {
    return disabled;
  }

  /**
   * Sets {@link #disabled}.
   *
   * @param disabled the new value
   */
  public void setDisabled(Boolean disabled) {
    this.disabled = disabled;
  }

  /**
   * Trigger type: daily, custom_schedule, or manual
   */
  @JsonProperty("trigger")
  public String trigger;

  /**
   * Gets {@link #trigger}.
   *
   * @return the value of {@link #trigger}
   */
  public String getTrigger() {
    return trigger;
  }

  /**
   * Sets {@link #trigger}.
   *
   * @param trigger the new value
   */
  public void setTrigger(String trigger) {
    this.trigger = trigger;
  }

  /**
   * Some MFT services request an empty file (known as a trigger file) to signal the sync is complete and they can begin further processing. If trigger_file is set, a zero-byte file will be sent at the end of the sync.
   */
  @JsonProperty("trigger_file")
  public String triggerFile;

  /**
   * Gets {@link #triggerFile}.
   *
   * @return the value of {@link #triggerFile}
   */
  public String getTriggerFile() {
    return triggerFile;
  }

  /**
   * Sets {@link #triggerFile}.
   *
   * @param triggerFile the new value
   */
  public void setTriggerFile(String triggerFile) {
    this.triggerFile = triggerFile;
  }

  /**
   * If true, the trigger file will be sent at the end of a successful sync even when no files were transferred.
   */
  @JsonProperty("always_write_trigger_file")
  public Boolean alwaysWriteTriggerFile;

  /**
   * Gets {@link #alwaysWriteTriggerFile}.
   *
   * @return the value of {@link #alwaysWriteTriggerFile}
   */
  public Boolean getAlwaysWriteTriggerFile() {
    return alwaysWriteTriggerFile;
  }

  /**
   * Sets {@link #alwaysWriteTriggerFile}.
   *
   * @param alwaysWriteTriggerFile the new value
   */
  public void setAlwaysWriteTriggerFile(Boolean alwaysWriteTriggerFile) {
    this.alwaysWriteTriggerFile = alwaysWriteTriggerFile;
  }

  /**
   * Array of glob patterns to include
   */
  @JsonProperty("include_patterns")
  public String[] includePatterns;

  /**
   * Gets {@link #includePatterns}.
   *
   * @return the value of {@link #includePatterns}
   */
  public String[] getIncludePatterns() {
    return includePatterns;
  }

  /**
   * Sets {@link #includePatterns}.
   *
   * @param includePatterns the new value
   */
  public void setIncludePatterns(String[] includePatterns) {
    this.includePatterns = includePatterns;
  }

  /**
   * Array of glob patterns to exclude
   */
  @JsonProperty("exclude_patterns")
  public String[] excludePatterns;

  /**
   * Gets {@link #excludePatterns}.
   *
   * @return the value of {@link #excludePatterns}
   */
  public String[] getExcludePatterns() {
    return excludePatterns;
  }

  /**
   * Sets {@link #excludePatterns}.
   *
   * @param excludePatterns the new value
   */
  public void setExcludePatterns(String[] excludePatterns) {
    this.excludePatterns = excludePatterns;
  }

  /**
   * When this sync was created
   */
  @JsonProperty("created_at")
  public Date createdAt;

  /**
   * Gets {@link #createdAt}.
   *
   * @return the value of {@link #createdAt}
   */
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * When this sync was last updated
   */
  @JsonProperty("updated_at")
  public Date updatedAt;

  /**
   * Gets {@link #updatedAt}.
   *
   * @return the value of {@link #updatedAt}
   */
  public Date getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Frequency in minutes between syncs. If set, this value must be greater than or equal to the `remote_sync_interval` value for the site's plan. If left blank, the plan's `remote_sync_interval` will be used. This setting is only used if `trigger` is empty.
   */
  @JsonProperty("sync_interval_minutes")
  public Long syncIntervalMinutes;

  /**
   * Gets {@link #syncIntervalMinutes}.
   *
   * @return the value of {@link #syncIntervalMinutes}
   */
  public Long getSyncIntervalMinutes() {
    return syncIntervalMinutes;
  }

  /**
   * Sets {@link #syncIntervalMinutes}.
   *
   * @param syncIntervalMinutes the new value
   */
  public void setSyncIntervalMinutes(Long syncIntervalMinutes) {
    this.syncIntervalMinutes = syncIntervalMinutes;
  }

  /**
   * If trigger is `daily`, this specifies how often to run this sync.  One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
   */
  @JsonProperty("interval")
  public String interval;

  /**
   * Gets {@link #interval}.
   *
   * @return the value of {@link #interval}
   */
  public String getInterval() {
    return interval;
  }

  /**
   * Sets {@link #interval}.
   *
   * @param interval the new value
   */
  public void setInterval(String interval) {
    this.interval = interval;
  }

  /**
   * If trigger type is `daily`, this specifies a day number to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.
   */
  @JsonProperty("recurring_day")
  public Long recurringDay;

  /**
   * Gets {@link #recurringDay}.
   *
   * @return the value of {@link #recurringDay}
   */
  public Long getRecurringDay() {
    return recurringDay;
  }

  /**
   * Sets {@link #recurringDay}.
   *
   * @param recurringDay the new value
   */
  public void setRecurringDay(Long recurringDay) {
    this.recurringDay = recurringDay;
  }

  /**
   * If trigger type is `daily`, this specifies one or more day numbers to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.
   */
  @JsonProperty("recurring_days")
  public Long[] recurringDays;

  /**
   * Gets {@link #recurringDays}.
   *
   * @return the value of {@link #recurringDays}
   */
  public Long[] getRecurringDays() {
    return recurringDays;
  }

  /**
   * Sets {@link #recurringDays}.
   *
   * @param recurringDays the new value
   */
  public void setRecurringDays(Long[] recurringDays) {
    this.recurringDays = recurringDays;
  }

  /**
   * If trigger is `custom_schedule`, the reusable Schedule used instead of the sync's schedule fields.
   */
  @JsonProperty("schedule_id")
  public Long scheduleId;

  /**
   * Gets {@link #scheduleId}.
   *
   * @return the value of {@link #scheduleId}
   */
  public Long getScheduleId() {
    return scheduleId;
  }

  /**
   * Sets {@link #scheduleId}.
   *
   * @param scheduleId the new value
   */
  public void setScheduleId(Long scheduleId) {
    this.scheduleId = scheduleId;
  }

  /**
   * If trigger is `custom_schedule`, Custom schedule description for when the sync should be run. 0-based days of the week. 0 is Sunday, 1 is Monday, etc.
   */
  @JsonProperty("schedule_days_of_week")
  public Long[] scheduleDaysOfWeek;

  /**
   * Gets {@link #scheduleDaysOfWeek}.
   *
   * @return the value of {@link #scheduleDaysOfWeek}
   */
  public Long[] getScheduleDaysOfWeek() {
    return scheduleDaysOfWeek;
  }

  /**
   * Sets {@link #scheduleDaysOfWeek}.
   *
   * @param scheduleDaysOfWeek the new value
   */
  public void setScheduleDaysOfWeek(Long[] scheduleDaysOfWeek) {
    this.scheduleDaysOfWeek = scheduleDaysOfWeek;
  }

  /**
   * Times of day to run in HH:MM format. For `custom_schedule`, run at these times on specified days of week. For `daily`, run at these times on the scheduled interval date.
   */
  @JsonProperty("schedule_times_of_day")
  public String[] scheduleTimesOfDay;

  /**
   * Gets {@link #scheduleTimesOfDay}.
   *
   * @return the value of {@link #scheduleTimesOfDay}
   */
  public String[] getScheduleTimesOfDay() {
    return scheduleTimesOfDay;
  }

  /**
   * Sets {@link #scheduleTimesOfDay}.
   *
   * @param scheduleTimesOfDay the new value
   */
  public void setScheduleTimesOfDay(String[] scheduleTimesOfDay) {
    this.scheduleTimesOfDay = scheduleTimesOfDay;
  }

  /**
   * Time zone for the schedule. If not set, times are interpreted as UTC.
   */
  @JsonProperty("schedule_time_zone")
  public String scheduleTimeZone;

  /**
   * Gets {@link #scheduleTimeZone}.
   *
   * @return the value of {@link #scheduleTimeZone}
   */
  public String getScheduleTimeZone() {
    return scheduleTimeZone;
  }

  /**
   * Sets {@link #scheduleTimeZone}.
   *
   * @param scheduleTimeZone the new value
   */
  public void setScheduleTimeZone(String scheduleTimeZone) {
    this.scheduleTimeZone = scheduleTimeZone;
  }

  /**
   * Skip the sync if there is a formal, observed holiday for this region.
   */
  @JsonProperty("holiday_region")
  public String holidayRegion;

  /**
   * Gets {@link #holidayRegion}.
   *
   * @return the value of {@link #holidayRegion}
   */
  public String getHolidayRegion() {
    return holidayRegion;
  }

  /**
   * Sets {@link #holidayRegion}.
   *
   * @param holidayRegion the new value
   */
  public void setHolidayRegion(String holidayRegion) {
    this.holidayRegion = holidayRegion;
  }

  /**
   * The latest run of this sync
   */
  @JsonProperty("latest_sync_run")
  public SyncRun latestSyncRun;

  /**
   * Gets {@link #latestSyncRun}.
   *
   * @return the value of {@link #latestSyncRun}
   */
  public SyncRun getLatestSyncRun() {
    return latestSyncRun;
  }

  /**
   * Sets {@link #latestSyncRun}.
   *
   * @param latestSyncRun the new value
   */
  public void setLatestSyncRun(SyncRun latestSyncRun) {
    this.latestSyncRun = latestSyncRun;
  }

  /**
   * Dry Run Sync
   *
   * @param parameters the request's parameters, by their API names
   */
  public void dryRun(HashMap<String, Object> parameters) throws IOException {
    filesClient().syncs().dryRun(this.id, parameters, this.options);
  }

  /**
   * Manually Run Sync
   *
   * @param parameters the request's parameters, by their API names
   */
  public void manualRun(HashMap<String, Object> parameters) throws IOException {
    filesClient().syncs().manualRun(this.id, parameters, this.options);
  }

  /**
   * Updates this Sync. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update Sync
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
   * @param parameters the request's parameters, by their API names
   * @return the {@code Sync}
   */
  public Sync update(HashMap<String, Object> parameters) throws IOException {
    Sync updated = filesClient().syncs().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete Sync
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().syncs().delete(this.id, parameters, this.options);
  }

  /**
   * Delete Sync
   *
   * @see #delete(HashMap)
   */
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
   * List Syncs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Sync> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Syncs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Sync> list(HashMap<String, Object> parameters) throws RuntimeException {
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
  public static ListIterator<Sync> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().syncs().list(parameters, options);
  }

  /**
   * List Syncs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Sync> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Syncs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Sync> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Sync
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Sync find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Sync
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Sync find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Sync
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Sync find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public static Sync find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().syncs().find(id, parameters, options);
  }

  /**
   * Show Sync
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Sync get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Sync
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Sync get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Sync
   *
   * @see #create(HashMap, HashMap)
   */
  public static Sync create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Sync
   *
   * @see #create(HashMap, HashMap)
   */
  public static Sync create(HashMap<String, Object> parameters) throws RuntimeException {
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
  public static Sync create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().syncs().create(parameters, options);
  }


  /**
   * Dry Run Sync
   *
   * @see #dryRun(Long, HashMap, HashMap)
   */
  public static void dryRun() throws RuntimeException {
    dryRun(null, null, null);
  }

  /**
   * Dry Run Sync
   *
   * @see #dryRun(Long, HashMap, HashMap)
   */
  public static void dryRun(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    dryRun(id, parameters, null);
  }

  /**
   * Dry Run Sync
   *
   * @see #dryRun(Long, HashMap, HashMap)
   */
  public static void dryRun(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    dryRun(null, parameters, options);
  }

  /**
   * Dry Run Sync
   *
   * @param id Sync ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void dryRun(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().syncs().dryRun(id, parameters, options);
  }


  /**
   * Manually Run Sync
   *
   * @see #manualRun(Long, HashMap, HashMap)
   */
  public static void manualRun() throws RuntimeException {
    manualRun(null, null, null);
  }

  /**
   * Manually Run Sync
   *
   * @see #manualRun(Long, HashMap, HashMap)
   */
  public static void manualRun(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    manualRun(id, parameters, null);
  }

  /**
   * Manually Run Sync
   *
   * @see #manualRun(Long, HashMap, HashMap)
   */
  public static void manualRun(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    manualRun(null, parameters, options);
  }

  /**
   * Manually Run Sync
   *
   * @param id Sync ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void manualRun(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().syncs().manualRun(id, parameters, options);
  }


  /**
   * Update Sync
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static Sync update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Sync
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static Sync update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Sync
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static Sync update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public static Sync update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().syncs().update(id, parameters, options);
  }


  /**
   * Delete Sync
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Sync
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Sync
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Sync
   *
   * @param id Sync ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().syncs().delete(id, parameters, options);
  }

  /**
   * Delete Sync
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Sync
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
