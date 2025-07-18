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
public class Sync implements ModelInterface {
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


  public Sync() {
    this(null, null);
  }

  public Sync(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Sync(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
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
  * If trigger is `custom_schedule`, Custom schedule description for when the sync should be run. Times of day in HH:MM format.
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
  * If trigger is `custom_schedule`, Custom schedule Time Zone for when the sync should be run.
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
  * If trigger is `custom_schedule`, the sync will check if there is a formal, observed holiday for the region, and if so, it will not run.
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
  * Manually Run Sync
  */
  public void manualRun(HashMap<String, Object> parameters) throws IOException {
    Sync.manualRun(this.id, parameters, this.options);
  }

  /**
  * Parameters:
  *   name - string - Name for this sync job
  *   description - string - Description for this sync job
  *   src_path - string - Absolute source path
  *   dest_path - string - Absolute destination path
  *   src_remote_server_id - int64 - Remote server ID for the source
  *   dest_remote_server_id - int64 - Remote server ID for the destination
  *   two_way - boolean - Is this a two-way sync?
  *   keep_after_copy - boolean - Keep files after copying?
  *   delete_empty_folders - boolean - Delete empty folders after sync?
  *   disabled - boolean - Is this sync disabled?
  *   interval - string - If trigger is `daily`, this specifies how often to run this sync.  One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
  *   trigger - string - Trigger type: daily, custom_schedule, or manual
  *   trigger_file - string - Some MFT services request an empty file (known as a trigger file) to signal the sync is complete and they can begin further processing. If trigger_file is set, a zero-byte file will be sent at the end of the sync.
  *   holiday_region - string - If trigger is `custom_schedule`, the sync will check if there is a formal, observed holiday for the region, and if so, it will not run.
  *   sync_interval_minutes - int64 - Frequency in minutes between syncs. If set, this value must be greater than or equal to the `remote_sync_interval` value for the site's plan. If left blank, the plan's `remote_sync_interval` will be used. This setting is only used if `trigger` is empty.
  *   recurring_day - int64 - If trigger type is `daily`, this specifies a day number to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.
  *   schedule_time_zone - string - If trigger is `custom_schedule`, Custom schedule Time Zone for when the sync should be run.
  *   schedule_days_of_week - array(int64) - If trigger is `custom_schedule`, Custom schedule description for when the sync should be run. 0-based days of the week. 0 is Sunday, 1 is Monday, etc.
  *   schedule_times_of_day - array(string) - If trigger is `custom_schedule`, Custom schedule description for when the sync should be run. Times of day in HH:MM format.
  */
  public Sync update(HashMap<String, Object> parameters) throws IOException {
    return Sync.update(this.id, parameters, this.options);
  }

  /**
  */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    Sync.delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    Sync.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  */
  public static ListIterator<Sync> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<Sync> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<Sync> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }


    String url = String.format("%s%s/syncs", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<Sync>> typeReference = new TypeReference<List<Sync>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
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



    String url = String.format("%s%s/syncs/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<Sync> typeReference = new TypeReference<Sync>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static Sync get() throws RuntimeException {
    return get(null, null, null);
  }

  public static Sync get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   name - string - Name for this sync job
  *   description - string - Description for this sync job
  *   src_path - string - Absolute source path
  *   dest_path - string - Absolute destination path
  *   src_remote_server_id - int64 - Remote server ID for the source
  *   dest_remote_server_id - int64 - Remote server ID for the destination
  *   two_way - boolean - Is this a two-way sync?
  *   keep_after_copy - boolean - Keep files after copying?
  *   delete_empty_folders - boolean - Delete empty folders after sync?
  *   disabled - boolean - Is this sync disabled?
  *   interval - string - If trigger is `daily`, this specifies how often to run this sync.  One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
  *   trigger - string - Trigger type: daily, custom_schedule, or manual
  *   trigger_file - string - Some MFT services request an empty file (known as a trigger file) to signal the sync is complete and they can begin further processing. If trigger_file is set, a zero-byte file will be sent at the end of the sync.
  *   holiday_region - string - If trigger is `custom_schedule`, the sync will check if there is a formal, observed holiday for the region, and if so, it will not run.
  *   sync_interval_minutes - int64 - Frequency in minutes between syncs. If set, this value must be greater than or equal to the `remote_sync_interval` value for the site's plan. If left blank, the plan's `remote_sync_interval` will be used. This setting is only used if `trigger` is empty.
  *   recurring_day - int64 - If trigger type is `daily`, this specifies a day number to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.
  *   schedule_time_zone - string - If trigger is `custom_schedule`, Custom schedule Time Zone for when the sync should be run.
  *   schedule_days_of_week - array(int64) - If trigger is `custom_schedule`, Custom schedule description for when the sync should be run. 0-based days of the week. 0 is Sunday, 1 is Monday, etc.
  *   schedule_times_of_day - array(string) - If trigger is `custom_schedule`, Custom schedule description for when the sync should be run. Times of day in HH:MM format.
  */
  public static Sync create() throws RuntimeException {
    return create(null, null);
  }

  public static Sync create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static Sync create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }
    if (parameters.containsKey("src_path") && !(parameters.get("src_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: src_path must be of type String parameters[\"src_path\"]");
    }
    if (parameters.containsKey("dest_path") && !(parameters.get("dest_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: dest_path must be of type String parameters[\"dest_path\"]");
    }
    if (parameters.containsKey("src_remote_server_id") && !(parameters.get("src_remote_server_id") instanceof Long || parameters.get("src_remote_server_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: src_remote_server_id must be of type Long or Integer parameters[\"src_remote_server_id\"]");
    }
    if (parameters.containsKey("dest_remote_server_id") && !(parameters.get("dest_remote_server_id") instanceof Long || parameters.get("dest_remote_server_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: dest_remote_server_id must be of type Long or Integer parameters[\"dest_remote_server_id\"]");
    }
    if (parameters.containsKey("two_way") && !(parameters.get("two_way") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: two_way must be of type Boolean parameters[\"two_way\"]");
    }
    if (parameters.containsKey("keep_after_copy") && !(parameters.get("keep_after_copy") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: keep_after_copy must be of type Boolean parameters[\"keep_after_copy\"]");
    }
    if (parameters.containsKey("delete_empty_folders") && !(parameters.get("delete_empty_folders") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: delete_empty_folders must be of type Boolean parameters[\"delete_empty_folders\"]");
    }
    if (parameters.containsKey("disabled") && !(parameters.get("disabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: disabled must be of type Boolean parameters[\"disabled\"]");
    }
    if (parameters.containsKey("interval") && !(parameters.get("interval") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: interval must be of type String parameters[\"interval\"]");
    }
    if (parameters.containsKey("trigger") && !(parameters.get("trigger") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: trigger must be of type String parameters[\"trigger\"]");
    }
    if (parameters.containsKey("trigger_file") && !(parameters.get("trigger_file") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: trigger_file must be of type String parameters[\"trigger_file\"]");
    }
    if (parameters.containsKey("holiday_region") && !(parameters.get("holiday_region") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: holiday_region must be of type String parameters[\"holiday_region\"]");
    }
    if (parameters.containsKey("sync_interval_minutes") && !(parameters.get("sync_interval_minutes") instanceof Long || parameters.get("sync_interval_minutes") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: sync_interval_minutes must be of type Long or Integer parameters[\"sync_interval_minutes\"]");
    }
    if (parameters.containsKey("recurring_day") && !(parameters.get("recurring_day") instanceof Long || parameters.get("recurring_day") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: recurring_day must be of type Long or Integer parameters[\"recurring_day\"]");
    }
    if (parameters.containsKey("schedule_time_zone") && !(parameters.get("schedule_time_zone") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: schedule_time_zone must be of type String parameters[\"schedule_time_zone\"]");
    }
    if (parameters.containsKey("schedule_days_of_week") && !(parameters.get("schedule_days_of_week") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: schedule_days_of_week must be of type Long[] parameters[\"schedule_days_of_week\"]");
    }
    if (parameters.containsKey("schedule_times_of_day") && !(parameters.get("schedule_times_of_day") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: schedule_times_of_day must be of type String[] parameters[\"schedule_times_of_day\"]");
    }


    String url = String.format("%s%s/syncs", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<Sync> typeReference = new TypeReference<Sync>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
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



    String url = String.format("%s%s/syncs/%s/manual_run", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.POST, parameters, options);
  }


  /**
  * Parameters:
  *   name - string - Name for this sync job
  *   description - string - Description for this sync job
  *   src_path - string - Absolute source path
  *   dest_path - string - Absolute destination path
  *   src_remote_server_id - int64 - Remote server ID for the source
  *   dest_remote_server_id - int64 - Remote server ID for the destination
  *   two_way - boolean - Is this a two-way sync?
  *   keep_after_copy - boolean - Keep files after copying?
  *   delete_empty_folders - boolean - Delete empty folders after sync?
  *   disabled - boolean - Is this sync disabled?
  *   interval - string - If trigger is `daily`, this specifies how often to run this sync.  One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
  *   trigger - string - Trigger type: daily, custom_schedule, or manual
  *   trigger_file - string - Some MFT services request an empty file (known as a trigger file) to signal the sync is complete and they can begin further processing. If trigger_file is set, a zero-byte file will be sent at the end of the sync.
  *   holiday_region - string - If trigger is `custom_schedule`, the sync will check if there is a formal, observed holiday for the region, and if so, it will not run.
  *   sync_interval_minutes - int64 - Frequency in minutes between syncs. If set, this value must be greater than or equal to the `remote_sync_interval` value for the site's plan. If left blank, the plan's `remote_sync_interval` will be used. This setting is only used if `trigger` is empty.
  *   recurring_day - int64 - If trigger type is `daily`, this specifies a day number to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.
  *   schedule_time_zone - string - If trigger is `custom_schedule`, Custom schedule Time Zone for when the sync should be run.
  *   schedule_days_of_week - array(int64) - If trigger is `custom_schedule`, Custom schedule description for when the sync should be run. 0-based days of the week. 0 is Sunday, 1 is Monday, etc.
  *   schedule_times_of_day - array(string) - If trigger is `custom_schedule`, Custom schedule description for when the sync should be run. Times of day in HH:MM format.
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
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }
    if (parameters.containsKey("src_path") && !(parameters.get("src_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: src_path must be of type String parameters[\"src_path\"]");
    }
    if (parameters.containsKey("dest_path") && !(parameters.get("dest_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: dest_path must be of type String parameters[\"dest_path\"]");
    }
    if (parameters.containsKey("src_remote_server_id") && !(parameters.get("src_remote_server_id") instanceof Long || parameters.get("src_remote_server_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: src_remote_server_id must be of type Long or Integer parameters[\"src_remote_server_id\"]");
    }
    if (parameters.containsKey("dest_remote_server_id") && !(parameters.get("dest_remote_server_id") instanceof Long || parameters.get("dest_remote_server_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: dest_remote_server_id must be of type Long or Integer parameters[\"dest_remote_server_id\"]");
    }
    if (parameters.containsKey("two_way") && !(parameters.get("two_way") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: two_way must be of type Boolean parameters[\"two_way\"]");
    }
    if (parameters.containsKey("keep_after_copy") && !(parameters.get("keep_after_copy") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: keep_after_copy must be of type Boolean parameters[\"keep_after_copy\"]");
    }
    if (parameters.containsKey("delete_empty_folders") && !(parameters.get("delete_empty_folders") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: delete_empty_folders must be of type Boolean parameters[\"delete_empty_folders\"]");
    }
    if (parameters.containsKey("disabled") && !(parameters.get("disabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: disabled must be of type Boolean parameters[\"disabled\"]");
    }
    if (parameters.containsKey("interval") && !(parameters.get("interval") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: interval must be of type String parameters[\"interval\"]");
    }
    if (parameters.containsKey("trigger") && !(parameters.get("trigger") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: trigger must be of type String parameters[\"trigger\"]");
    }
    if (parameters.containsKey("trigger_file") && !(parameters.get("trigger_file") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: trigger_file must be of type String parameters[\"trigger_file\"]");
    }
    if (parameters.containsKey("holiday_region") && !(parameters.get("holiday_region") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: holiday_region must be of type String parameters[\"holiday_region\"]");
    }
    if (parameters.containsKey("sync_interval_minutes") && !(parameters.get("sync_interval_minutes") instanceof Long || parameters.get("sync_interval_minutes") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: sync_interval_minutes must be of type Long or Integer parameters[\"sync_interval_minutes\"]");
    }
    if (parameters.containsKey("recurring_day") && !(parameters.get("recurring_day") instanceof Long || parameters.get("recurring_day") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: recurring_day must be of type Long or Integer parameters[\"recurring_day\"]");
    }
    if (parameters.containsKey("schedule_time_zone") && !(parameters.get("schedule_time_zone") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: schedule_time_zone must be of type String parameters[\"schedule_time_zone\"]");
    }
    if (parameters.containsKey("schedule_days_of_week") && !(parameters.get("schedule_days_of_week") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: schedule_days_of_week must be of type Long[] parameters[\"schedule_days_of_week\"]");
    }
    if (parameters.containsKey("schedule_times_of_day") && !(parameters.get("schedule_times_of_day") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: schedule_times_of_day must be of type String[] parameters[\"schedule_times_of_day\"]");
    }



    String url = String.format("%s%s/syncs/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<Sync> typeReference = new TypeReference<Sync>() {};
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



    String url = String.format("%s%s/syncs/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
