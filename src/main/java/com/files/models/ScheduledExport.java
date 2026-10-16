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
public class ScheduledExport implements ModelInterface {
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

  // One mapper for every ScheduledExport, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  public ScheduledExport() {
    this(null, null);
  }

  public ScheduledExport(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public ScheduledExport(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Scheduled Export ID
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
   * Name for this scheduled export.
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
   * Export report type. Valid values: folder_size_audit, group_membership_audit, permission_audit, share_link_audit
   */
  @JsonProperty("export_type")
  public String exportType;

  public String getExportType() {
    return exportType;
  }

  public void setExportType(String exportType) {
    this.exportType = exportType;
  }

  /**
   * Human-readable report name.
   */
  @JsonProperty("report_name")
  public String reportName;

  public String getReportName() {
    return reportName;
  }

  public void setReportName(String reportName) {
    this.reportName = reportName;
  }

  /**
   * Report-specific options. `permission_audit` supports `group_by` with `user` or `path`.
   */
  @JsonProperty("export_options")
  public Object exportOptions;

  public Object getExportOptions() {
    return exportOptions;
  }

  public void setExportOptions(Object exportOptions) {
    this.exportOptions = exportOptions;
  }

  /**
   * Site Admin user who receives the completed export e-mail.
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
   * If true, this scheduled export will not run.
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
   * Schedule trigger type: `daily` or `custom_schedule`.
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
   * If trigger is `daily`, this specifies how often to run the scheduled export.
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
   * If trigger is `daily`, this selects the day number inside the chosen interval.
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
   * If trigger is `daily`, this selects one or more day numbers inside a `week`, `month`, `quarter`, or `year` interval.
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
   * If trigger is `custom_schedule`, the reusable Schedule used instead of the scheduled export's schedule fields.
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
   * If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.
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
   * Times of day in HH:MM format for the scheduled export schedule.
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
   * Time zone used by the scheduled export schedule.
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
   * Optional holiday region used by the scheduled export schedule.
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
   * Human-readable schedule description.
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
   * Most recent scheduled attempt time, including attempts that failed validation.
   */
  @JsonProperty("last_run_at")
  public Date lastRunAt;

  public Date getLastRunAt() {
    return lastRunAt;
  }

  public void setLastRunAt(Date lastRunAt) {
    this.lastRunAt = lastRunAt;
  }

  /**
   * Most recent Export ID created by this schedule.
   */
  @JsonProperty("last_export_id")
  public Long lastExportId;

  public Long getLastExportId() {
    return lastExportId;
  }

  public void setLastExportId(Long lastExportId) {
    this.lastExportId = lastExportId;
  }

  /**
   * Validation error from the most recent scheduled attempt. The schedule remains enabled and retries at its next scheduled time. Cleared when an export is successfully created; does not describe errors during export generation.
   */
  @JsonProperty("last_error")
  public String lastError;

  public String getLastError() {
    return lastError;
  }

  public void setLastError(String lastError) {
    this.lastError = lastError;
  }

  /**
   * Creation time.
   */
  @JsonProperty("created_at")
  public Date createdAt;

  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * Last update time.
   */
  @JsonProperty("updated_at")
  public Date updatedAt;

  public Date getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Updates this ScheduledExport. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Parameters:
   *   name - string - Name for this scheduled export.
   *   export_type - string - Export report type. Valid values: folder_size_audit, group_membership_audit, permission_audit, share_link_audit
   *   export_options - object - Report-specific options. `permission_audit` supports `group_by` with `user` or `path`.
   *   user_id - int64 - Site Admin user who receives the completed export e-mail.
   *   disabled - boolean - If true, this scheduled export will not run.
   *   trigger - string - Schedule trigger type: `daily` or `custom_schedule`.
   *   interval - string - If trigger is `daily`, this specifies how often to run the scheduled export.
   *   recurring_day - int64 - If trigger is `daily`, this selects the day number inside the chosen interval.
   *   recurring_days - array(int64) - If trigger is `daily`, this selects one or more day numbers inside a `week`, `month`, `quarter`, or `year` interval.
   *   schedule_id - int64 - If trigger is `custom_schedule`, the reusable Schedule used instead of the scheduled export's schedule fields.
   *   schedule_days_of_week - array(int64) - If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.
   *   schedule_times_of_day - array(string) - Times of day in HH:MM format for the scheduled export schedule.
   *   schedule_time_zone - string - Time zone used by the scheduled export schedule.
   *   holiday_region - string - Optional holiday region used by the scheduled export schedule.
   */
  public ScheduledExport update(HashMap<String, Object> parameters) throws IOException {
    ScheduledExport updated = filesClient().scheduledExports().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().scheduledExports().delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this ScheduledExport: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().scheduledExports().create(parameters, this.options));
    }
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
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
  private void refreshFrom(ScheduledExport canonical) {
    this.id = canonical.id;
    this.name = canonical.name;
    this.exportType = canonical.exportType;
    this.reportName = canonical.reportName;
    this.exportOptions = canonical.exportOptions;
    this.userId = canonical.userId;
    this.disabled = canonical.disabled;
    this.trigger = canonical.trigger;
    this.interval = canonical.interval;
    this.recurringDay = canonical.recurringDay;
    this.recurringDays = canonical.recurringDays;
    this.scheduleId = canonical.scheduleId;
    this.scheduleDaysOfWeek = canonical.scheduleDaysOfWeek;
    this.scheduleTimesOfDay = canonical.scheduleTimesOfDay;
    this.scheduleTimeZone = canonical.scheduleTimeZone;
    this.holidayRegion = canonical.holidayRegion;
    this.humanReadableSchedule = canonical.humanReadableSchedule;
    this.lastRunAt = canonical.lastRunAt;
    this.lastExportId = canonical.lastExportId;
    this.lastError = canonical.lastError;
    this.createdAt = canonical.createdAt;
    this.updatedAt = canonical.updatedAt;
  }

  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `name`, `export_type` or `disabled`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `disabled` and `export_type`.
   *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `export_type`.
   */
  public static ListIterator<ScheduledExport> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<ScheduledExport> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<ScheduledExport> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().scheduledExports().list(parameters, options);
  }

  public static ListIterator<ScheduledExport> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<ScheduledExport> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Scheduled Export ID.
   */
  public static ScheduledExport find() throws RuntimeException {
    return find(null, null, null);
  }

  public static ScheduledExport find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static ScheduledExport find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static ScheduledExport find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().scheduledExports().find(id, parameters, options);
  }

  public static ScheduledExport get() throws RuntimeException {
    return get(null, null, null);
  }

  public static ScheduledExport get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   name (required) - string - Name for this scheduled export.
   *   export_type (required) - string - Export report type. Valid values: folder_size_audit, group_membership_audit, permission_audit, share_link_audit
   *   export_options - object - Report-specific options. `permission_audit` supports `group_by` with `user` or `path`.
   *   user_id - int64 - Site Admin user who receives the completed export e-mail.
   *   disabled - boolean - If true, this scheduled export will not run.
   *   trigger - string - Schedule trigger type: `daily` or `custom_schedule`.
   *   interval - string - If trigger is `daily`, this specifies how often to run the scheduled export.
   *   recurring_day - int64 - If trigger is `daily`, this selects the day number inside the chosen interval.
   *   recurring_days - array(int64) - If trigger is `daily`, this selects one or more day numbers inside a `week`, `month`, `quarter`, or `year` interval.
   *   schedule_id - int64 - If trigger is `custom_schedule`, the reusable Schedule used instead of the scheduled export's schedule fields.
   *   schedule_days_of_week - array(int64) - If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.
   *   schedule_times_of_day - array(string) - Times of day in HH:MM format for the scheduled export schedule.
   *   schedule_time_zone - string - Time zone used by the scheduled export schedule.
   *   holiday_region - string - Optional holiday region used by the scheduled export schedule.
   */
  public static ScheduledExport create() throws RuntimeException {
    return create(null, null);
  }

  public static ScheduledExport create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static ScheduledExport create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().scheduledExports().create(parameters, options);
  }


  /**
   * Parameters:
   *   name - string - Name for this scheduled export.
   *   export_type - string - Export report type. Valid values: folder_size_audit, group_membership_audit, permission_audit, share_link_audit
   *   export_options - object - Report-specific options. `permission_audit` supports `group_by` with `user` or `path`.
   *   user_id - int64 - Site Admin user who receives the completed export e-mail.
   *   disabled - boolean - If true, this scheduled export will not run.
   *   trigger - string - Schedule trigger type: `daily` or `custom_schedule`.
   *   interval - string - If trigger is `daily`, this specifies how often to run the scheduled export.
   *   recurring_day - int64 - If trigger is `daily`, this selects the day number inside the chosen interval.
   *   recurring_days - array(int64) - If trigger is `daily`, this selects one or more day numbers inside a `week`, `month`, `quarter`, or `year` interval.
   *   schedule_id - int64 - If trigger is `custom_schedule`, the reusable Schedule used instead of the scheduled export's schedule fields.
   *   schedule_days_of_week - array(int64) - If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.
   *   schedule_times_of_day - array(string) - Times of day in HH:MM format for the scheduled export schedule.
   *   schedule_time_zone - string - Time zone used by the scheduled export schedule.
   *   holiday_region - string - Optional holiday region used by the scheduled export schedule.
   */
  public static ScheduledExport update() throws RuntimeException {
    return update(null, null, null);
  }

  public static ScheduledExport update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static ScheduledExport update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static ScheduledExport update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().scheduledExports().update(id, parameters, options);
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
    FilesClient.defaultClient().scheduledExports().delete(id, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
