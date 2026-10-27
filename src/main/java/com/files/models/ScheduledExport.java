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

  // One mapper for every ScheduledExport, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code ScheduledExport} with no attributes set.
   */
  public ScheduledExport() {
    this(null, null);
  }

  /**
   * Creates a {@code ScheduledExport} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public ScheduledExport(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code ScheduledExport} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
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
   * Name for this scheduled export.
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
   * Export report type. Valid values: folder_size_audit, group_membership_audit, permission_audit, share_link_audit
   */
  @JsonProperty("export_type")
  public String exportType;

  /**
   * Gets {@link #exportType}.
   *
   * @return the value of {@link #exportType}
   */
  public String getExportType() {
    return exportType;
  }

  /**
   * Sets {@link #exportType}.
   *
   * @param exportType the new value
   */
  public void setExportType(String exportType) {
    this.exportType = exportType;
  }

  /**
   * Human-readable report name.
   */
  @JsonProperty("report_name")
  public String reportName;

  /**
   * Gets {@link #reportName}.
   *
   * @return the value of {@link #reportName}
   */
  public String getReportName() {
    return reportName;
  }

  /**
   * Sets {@link #reportName}.
   *
   * @param reportName the new value
   */
  public void setReportName(String reportName) {
    this.reportName = reportName;
  }

  /**
   * Options for the report selected by export_type.
   */
  @JsonProperty("export_options")
  public Object exportOptions;

  /**
   * Gets {@link #exportOptions}.
   *
   * @return the value of {@link #exportOptions}
   */
  public Object getExportOptions() {
    return exportOptions;
  }

  /**
   * Sets {@link #exportOptions}.
   *
   * @param exportOptions the new value
   */
  public void setExportOptions(Object exportOptions) {
    this.exportOptions = exportOptions;
  }

  /**
   * Site Admin user who receives the completed export e-mail.
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
   * If true, this scheduled export will not run.
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
   * Schedule trigger type: `daily` or `custom_schedule`.
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
   * If trigger is `daily`, this specifies how often to run the scheduled export.
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
   * If trigger is `daily`, this selects the day number inside the chosen interval.
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
   * If trigger is `daily`, this selects one or more day numbers inside a `week`, `month`, `quarter`, or `year` interval.
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
   * If trigger is `custom_schedule`, the reusable Schedule used instead of the scheduled export's schedule fields.
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
   * If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.
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
   * Times of day in HH:MM format for the scheduled export schedule.
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
   * Time zone used by the scheduled export schedule.
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
   * Optional holiday region used by the scheduled export schedule.
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
   * Human-readable schedule description.
   */
  @JsonProperty("human_readable_schedule")
  public String humanReadableSchedule;

  /**
   * Gets {@link #humanReadableSchedule}.
   *
   * @return the value of {@link #humanReadableSchedule}
   */
  public String getHumanReadableSchedule() {
    return humanReadableSchedule;
  }

  /**
   * Sets {@link #humanReadableSchedule}.
   *
   * @param humanReadableSchedule the new value
   */
  public void setHumanReadableSchedule(String humanReadableSchedule) {
    this.humanReadableSchedule = humanReadableSchedule;
  }

  /**
   * Most recent scheduled attempt time, including attempts that failed validation.
   */
  @JsonProperty("last_run_at")
  public Date lastRunAt;

  /**
   * Gets {@link #lastRunAt}.
   *
   * @return the value of {@link #lastRunAt}
   */
  public Date getLastRunAt() {
    return lastRunAt;
  }

  /**
   * Sets {@link #lastRunAt}.
   *
   * @param lastRunAt the new value
   */
  public void setLastRunAt(Date lastRunAt) {
    this.lastRunAt = lastRunAt;
  }

  /**
   * Most recent Export ID created by this schedule.
   */
  @JsonProperty("last_export_id")
  public Long lastExportId;

  /**
   * Gets {@link #lastExportId}.
   *
   * @return the value of {@link #lastExportId}
   */
  public Long getLastExportId() {
    return lastExportId;
  }

  /**
   * Sets {@link #lastExportId}.
   *
   * @param lastExportId the new value
   */
  public void setLastExportId(Long lastExportId) {
    this.lastExportId = lastExportId;
  }

  /**
   * Validation error from the most recent scheduled attempt. The schedule remains enabled and retries at its next scheduled time. Cleared when an export is successfully created; does not describe errors during export generation.
   */
  @JsonProperty("last_error")
  public String lastError;

  /**
   * Gets {@link #lastError}.
   *
   * @return the value of {@link #lastError}
   */
  public String getLastError() {
    return lastError;
  }

  /**
   * Sets {@link #lastError}.
   *
   * @param lastError the new value
   */
  public void setLastError(String lastError) {
    this.lastError = lastError;
  }

  /**
   * Creation time.
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
   * Last update time.
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
   * Updates this ScheduledExport. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update Scheduled Export
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Name for this scheduled export.</li>
   * <li>{@code export_type} (String): Export report type. Valid values: folder_size_audit, group_membership_audit, permission_audit, share_link_audit</li>
   * <li>{@code export_options} (Object): Options for the report selected by export_type.</li>
   * <li>{@code user_id} (Long): Site Admin user who receives the completed export e-mail.</li>
   * <li>{@code disabled} (Boolean): If true, this scheduled export will not run.</li>
   * <li>{@code trigger} (String): Schedule trigger type: `daily` or `custom_schedule`.</li>
   * <li>{@code interval} (String): If trigger is `daily`, this specifies how often to run the scheduled export.</li>
   * <li>{@code recurring_day} (Long): If trigger is `daily`, this selects the day number inside the chosen interval.</li>
   * <li>{@code recurring_days} (Long[]): If trigger is `daily`, this selects one or more day numbers inside a `week`, `month`, `quarter`, or `year` interval.</li>
   * <li>{@code schedule_id} (Long): If trigger is `custom_schedule`, the reusable Schedule used instead of the scheduled export's schedule fields.</li>
   * <li>{@code schedule_days_of_week} (Long[]): If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.</li>
   * <li>{@code schedule_times_of_day} (String[]): Times of day in HH:MM format for the scheduled export schedule.</li>
   * <li>{@code schedule_time_zone} (String): Time zone used by the scheduled export schedule.</li>
   * <li>{@code holiday_region} (String): Optional holiday region used by the scheduled export schedule.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code ScheduledExport}
   */
  public ScheduledExport update(HashMap<String, Object> parameters) throws IOException {
    ScheduledExport updated = filesClient().scheduledExports().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete Scheduled Export
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().scheduledExports().delete(this.id, parameters, this.options);
  }

  /**
   * Delete Scheduled Export
   *
   * @see #delete(HashMap)
   */
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
   * List Scheduled Exports
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ScheduledExport> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Scheduled Exports
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ScheduledExport> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Scheduled Exports
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `name`, `export_type` or `disabled`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `disabled` and `export_type`.</li>
   * <li>{@code filter_prefix} (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `export_type`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ScheduledExport} records, in a {@code ListIterator}
   */
  public static ListIterator<ScheduledExport> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().scheduledExports().list(parameters, options);
  }

  /**
   * List Scheduled Exports
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ScheduledExport> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Scheduled Exports
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ScheduledExport> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Scheduled Export
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ScheduledExport find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Scheduled Export
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ScheduledExport find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Scheduled Export
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ScheduledExport find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Scheduled Export
   *
   * @param id Scheduled Export ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ScheduledExport}
   */
  public static ScheduledExport find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().scheduledExports().find(id, parameters, options);
  }

  /**
   * Show Scheduled Export
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ScheduledExport get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Scheduled Export
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ScheduledExport get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Scheduled Export
   *
   * @see #create(HashMap, HashMap)
   */
  public static ScheduledExport create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Scheduled Export
   *
   * @see #create(HashMap, HashMap)
   */
  public static ScheduledExport create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create Scheduled Export
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Required. Name for this scheduled export.</li>
   * <li>{@code export_type} (String): Required. Export report type. Valid values: folder_size_audit, group_membership_audit, permission_audit, share_link_audit</li>
   * <li>{@code export_options} (Object): Options for the report selected by export_type.</li>
   * <li>{@code user_id} (Long): Site Admin user who receives the completed export e-mail.</li>
   * <li>{@code disabled} (Boolean): If true, this scheduled export will not run.</li>
   * <li>{@code trigger} (String): Schedule trigger type: `daily` or `custom_schedule`.</li>
   * <li>{@code interval} (String): If trigger is `daily`, this specifies how often to run the scheduled export.</li>
   * <li>{@code recurring_day} (Long): If trigger is `daily`, this selects the day number inside the chosen interval.</li>
   * <li>{@code recurring_days} (Long[]): If trigger is `daily`, this selects one or more day numbers inside a `week`, `month`, `quarter`, or `year` interval.</li>
   * <li>{@code schedule_id} (Long): If trigger is `custom_schedule`, the reusable Schedule used instead of the scheduled export's schedule fields.</li>
   * <li>{@code schedule_days_of_week} (Long[]): If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.</li>
   * <li>{@code schedule_times_of_day} (String[]): Times of day in HH:MM format for the scheduled export schedule.</li>
   * <li>{@code schedule_time_zone} (String): Time zone used by the scheduled export schedule.</li>
   * <li>{@code holiday_region} (String): Optional holiday region used by the scheduled export schedule.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ScheduledExport}
   */
  public static ScheduledExport create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().scheduledExports().create(parameters, options);
  }


  /**
   * Update Scheduled Export
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static ScheduledExport update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Scheduled Export
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static ScheduledExport update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Scheduled Export
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static ScheduledExport update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Scheduled Export
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Name for this scheduled export.</li>
   * <li>{@code export_type} (String): Export report type. Valid values: folder_size_audit, group_membership_audit, permission_audit, share_link_audit</li>
   * <li>{@code export_options} (Object): Options for the report selected by export_type.</li>
   * <li>{@code user_id} (Long): Site Admin user who receives the completed export e-mail.</li>
   * <li>{@code disabled} (Boolean): If true, this scheduled export will not run.</li>
   * <li>{@code trigger} (String): Schedule trigger type: `daily` or `custom_schedule`.</li>
   * <li>{@code interval} (String): If trigger is `daily`, this specifies how often to run the scheduled export.</li>
   * <li>{@code recurring_day} (Long): If trigger is `daily`, this selects the day number inside the chosen interval.</li>
   * <li>{@code recurring_days} (Long[]): If trigger is `daily`, this selects one or more day numbers inside a `week`, `month`, `quarter`, or `year` interval.</li>
   * <li>{@code schedule_id} (Long): If trigger is `custom_schedule`, the reusable Schedule used instead of the scheduled export's schedule fields.</li>
   * <li>{@code schedule_days_of_week} (Long[]): If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.</li>
   * <li>{@code schedule_times_of_day} (String[]): Times of day in HH:MM format for the scheduled export schedule.</li>
   * <li>{@code schedule_time_zone} (String): Time zone used by the scheduled export schedule.</li>
   * <li>{@code holiday_region} (String): Optional holiday region used by the scheduled export schedule.</li>
   * </ul>
   *
   * @param id Scheduled Export ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ScheduledExport}
   */
  public static ScheduledExport update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().scheduledExports().update(id, parameters, options);
  }


  /**
   * Delete Scheduled Export
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Scheduled Export
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Scheduled Export
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Scheduled Export
   *
   * @param id Scheduled Export ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().scheduledExports().delete(id, parameters, options);
  }

  /**
   * Delete Scheduled Export
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Scheduled Export
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
