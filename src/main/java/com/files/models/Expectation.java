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
public class Expectation implements ModelInterface {
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

  // One mapper for every Expectation, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  public Expectation() {
    this(null, null);
  }

  public Expectation(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Expectation(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Expectation ID
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
   * Workspace ID. `0` means the default workspace.
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
   * Expectation name.
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
   * Expectation description.
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
   * Path scope for the expectation. Supports workspace-relative presentation. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
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
   * Source glob used to select candidate files.
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
   * Optional source exclusion glob.
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
   * If true, the expectation is disabled.
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
   * Criteria schema version for this expectation.
   */
  @JsonProperty("expectations_version")
  public Long expectationsVersion;

  public Long getExpectationsVersion() {
    return expectationsVersion;
  }

  public void setExpectationsVersion(Long expectationsVersion) {
    this.expectationsVersion = expectationsVersion;
  }

  /**
   * How this expectation opens windows.
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
   * If trigger is `daily`, this specifies how often to run the expectation.
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
   * If trigger is `custom_schedule`, the reusable Schedule used instead of the Expectation's schedule fields.
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
   * Times of day in HH:MM format for the Expectation schedule.
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
   * Time zone used by the Expectation schedule.
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
   * Optional holiday region used by the Expectation schedule.
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
   * How many seconds before the due boundary the window starts.
   */
  @JsonProperty("lookback_interval")
  public Long lookbackInterval;

  public Long getLookbackInterval() {
    return lookbackInterval;
  }

  public void setLookbackInterval(Long lookbackInterval) {
    this.lookbackInterval = lookbackInterval;
  }

  /**
   * How many seconds a schedule-driven window may remain eligible to close as late.
   */
  @JsonProperty("late_acceptance_interval")
  public Long lateAcceptanceInterval;

  public Long getLateAcceptanceInterval() {
    return lateAcceptanceInterval;
  }

  public void setLateAcceptanceInterval(Long lateAcceptanceInterval) {
    this.lateAcceptanceInterval = lateAcceptanceInterval;
  }

  /**
   * How many quiet seconds are required before final closure.
   */
  @JsonProperty("inactivity_interval")
  public Long inactivityInterval;

  public Long getInactivityInterval() {
    return inactivityInterval;
  }

  public void setInactivityInterval(Long inactivityInterval) {
    this.inactivityInterval = inactivityInterval;
  }

  /**
   * Hard-stop duration in seconds for unscheduled expectations.
   */
  @JsonProperty("max_open_interval")
  public Long maxOpenInterval;

  public Long getMaxOpenInterval() {
    return maxOpenInterval;
  }

  public void setMaxOpenInterval(Long maxOpenInterval) {
    this.maxOpenInterval = maxOpenInterval;
  }

  /**
   * Versioned success criteria definition for the expectation. Criteria v2 supports optional FTS content validation.
   */
  @JsonProperty("criteria")
  public Object criteria;

  public Object getCriteria() {
    return criteria;
  }

  public void setCriteria(Object criteria) {
    this.criteria = criteria;
  }

  /**
   * Last time this expectation was evaluated.
   */
  @JsonProperty("last_evaluated_at")
  public Date lastEvaluatedAt;

  public Date getLastEvaluatedAt() {
    return lastEvaluatedAt;
  }

  public void setLastEvaluatedAt(Date lastEvaluatedAt) {
    this.lastEvaluatedAt = lastEvaluatedAt;
  }

  /**
   * Last time this expectation closed successfully.
   */
  @JsonProperty("last_success_at")
  public Date lastSuccessAt;

  public Date getLastSuccessAt() {
    return lastSuccessAt;
  }

  public void setLastSuccessAt(Date lastSuccessAt) {
    this.lastSuccessAt = lastSuccessAt;
  }

  /**
   * Last time this expectation closed with a failure result.
   */
  @JsonProperty("last_failure_at")
  public Date lastFailureAt;

  public Date getLastFailureAt() {
    return lastFailureAt;
  }

  public void setLastFailureAt(Date lastFailureAt) {
    this.lastFailureAt = lastFailureAt;
  }

  /**
   * Most recent terminal result for this expectation.
   */
  @JsonProperty("last_result")
  public String lastResult;

  public String getLastResult() {
    return lastResult;
  }

  public void setLastResult(String lastResult) {
    this.lastResult = lastResult;
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
   * Manually open an Expectation window
   */
  public ExpectationEvaluation triggerEvaluation(HashMap<String, Object> parameters) throws IOException {
    return filesClient().expectations().triggerEvaluation(this.id, parameters, this.options);
  }

  /**
   * Updates this Expectation. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Parameters:
   *   name - string - Expectation name.
   *   description - string - Expectation description.
   *   path - string - Path scope for the expectation. Supports workspace-relative presentation.
   *   source - string - Source glob used to select candidate files.
   *   exclude_pattern - string - Optional source exclusion glob.
   *   disabled - boolean - If true, the expectation is disabled.
   *   trigger - string - How this expectation opens windows.
   *   interval - string - If trigger is `daily`, this specifies how often to run the expectation.
   *   recurring_day - int64 - If trigger is `daily`, this selects the day number inside the chosen interval.
   *   recurring_days - array(int64) - If trigger is `daily`, this selects one or more day numbers inside a `week`, `month`, `quarter`, or `year` interval.
   *   schedule_id - int64 - If trigger is `custom_schedule`, the reusable Schedule used instead of the Expectation's schedule fields.
   *   schedule_days_of_week - array(int64) - If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.
   *   schedule_times_of_day - array(string) - Times of day in HH:MM format for the Expectation schedule.
   *   schedule_time_zone - string - Time zone used by the Expectation schedule.
   *   holiday_region - string - Optional holiday region used by the Expectation schedule.
   *   lookback_interval - int64 - How many seconds before the due boundary the window starts.
   *   late_acceptance_interval - int64 - How many seconds a schedule-driven window may remain eligible to close as late.
   *   inactivity_interval - int64 - How many quiet seconds are required before final closure.
   *   max_open_interval - int64 - Hard-stop duration in seconds for unscheduled expectations.
   *   criteria - object - Versioned success criteria definition for the expectation, including optional Files Transform Script content validation in criteria v2.
   *   workspace_id - int64 - Workspace ID. `0` means the default workspace.
   */
  public Expectation update(HashMap<String, Object> parameters) throws IOException {
    Expectation updated = filesClient().expectations().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().expectations().delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this Expectation: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().expectations().create(parameters, this.options));
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
  private void refreshFrom(Expectation canonical) {
    this.id = canonical.id;
    this.workspaceId = canonical.workspaceId;
    this.name = canonical.name;
    this.description = canonical.description;
    this.path = canonical.path;
    this.source = canonical.source;
    this.excludePattern = canonical.excludePattern;
    this.disabled = canonical.disabled;
    this.expectationsVersion = canonical.expectationsVersion;
    this.trigger = canonical.trigger;
    this.interval = canonical.interval;
    this.recurringDay = canonical.recurringDay;
    this.recurringDays = canonical.recurringDays;
    this.scheduleId = canonical.scheduleId;
    this.scheduleDaysOfWeek = canonical.scheduleDaysOfWeek;
    this.scheduleTimesOfDay = canonical.scheduleTimesOfDay;
    this.scheduleTimeZone = canonical.scheduleTimeZone;
    this.holidayRegion = canonical.holidayRegion;
    this.lookbackInterval = canonical.lookbackInterval;
    this.lateAcceptanceInterval = canonical.lateAcceptanceInterval;
    this.inactivityInterval = canonical.inactivityInterval;
    this.maxOpenInterval = canonical.maxOpenInterval;
    this.criteria = canonical.criteria;
    this.lastEvaluatedAt = canonical.lastEvaluatedAt;
    this.lastSuccessAt = canonical.lastSuccessAt;
    this.lastFailureAt = canonical.lastFailureAt;
    this.lastResult = canonical.lastResult;
    this.createdAt = canonical.createdAt;
    this.updatedAt = canonical.updatedAt;
  }

  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `name` or `disabled`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `disabled` and `workspace_id`. Valid field combinations are `[ workspace_id, disabled ]`.
   */
  public static ListIterator<Expectation> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<Expectation> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<Expectation> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().expectations().list(parameters, options);
  }

  public static ListIterator<Expectation> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<Expectation> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Expectation ID.
   */
  public static Expectation find() throws RuntimeException {
    return find(null, null, null);
  }

  public static Expectation find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static Expectation find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static Expectation find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().expectations().find(id, parameters, options);
  }

  public static Expectation get() throws RuntimeException {
    return get(null, null, null);
  }

  public static Expectation get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   name - string - Expectation name.
   *   description - string - Expectation description.
   *   path - string - Path scope for the expectation. Supports workspace-relative presentation.
   *   source - string - Source glob used to select candidate files.
   *   exclude_pattern - string - Optional source exclusion glob.
   *   disabled - boolean - If true, the expectation is disabled.
   *   trigger - string - How this expectation opens windows.
   *   interval - string - If trigger is `daily`, this specifies how often to run the expectation.
   *   recurring_day - int64 - If trigger is `daily`, this selects the day number inside the chosen interval.
   *   recurring_days - array(int64) - If trigger is `daily`, this selects one or more day numbers inside a `week`, `month`, `quarter`, or `year` interval.
   *   schedule_id - int64 - If trigger is `custom_schedule`, the reusable Schedule used instead of the Expectation's schedule fields.
   *   schedule_days_of_week - array(int64) - If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.
   *   schedule_times_of_day - array(string) - Times of day in HH:MM format for the Expectation schedule.
   *   schedule_time_zone - string - Time zone used by the Expectation schedule.
   *   holiday_region - string - Optional holiday region used by the Expectation schedule.
   *   lookback_interval - int64 - How many seconds before the due boundary the window starts.
   *   late_acceptance_interval - int64 - How many seconds a schedule-driven window may remain eligible to close as late.
   *   inactivity_interval - int64 - How many quiet seconds are required before final closure.
   *   max_open_interval - int64 - Hard-stop duration in seconds for unscheduled expectations.
   *   criteria - object - Versioned success criteria definition for the expectation, including optional Files Transform Script content validation in criteria v2.
   *   workspace_id - int64 - Workspace ID. `0` means the default workspace.
   */
  public static Expectation create() throws RuntimeException {
    return create(null, null);
  }

  public static Expectation create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static Expectation create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().expectations().create(parameters, options);
  }


  /**
   * Manually open an Expectation window
   */
  public static ExpectationEvaluation triggerEvaluation() throws RuntimeException {
    return triggerEvaluation(null, null, null);
  }

  public static ExpectationEvaluation triggerEvaluation(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return triggerEvaluation(id, parameters, null);
  }

  public static ExpectationEvaluation triggerEvaluation(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return triggerEvaluation(null, parameters, options);
  }

  public static ExpectationEvaluation triggerEvaluation(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().expectations().triggerEvaluation(id, parameters, options);
  }


  /**
   * Parameters:
   *   name - string - Expectation name.
   *   description - string - Expectation description.
   *   path - string - Path scope for the expectation. Supports workspace-relative presentation.
   *   source - string - Source glob used to select candidate files.
   *   exclude_pattern - string - Optional source exclusion glob.
   *   disabled - boolean - If true, the expectation is disabled.
   *   trigger - string - How this expectation opens windows.
   *   interval - string - If trigger is `daily`, this specifies how often to run the expectation.
   *   recurring_day - int64 - If trigger is `daily`, this selects the day number inside the chosen interval.
   *   recurring_days - array(int64) - If trigger is `daily`, this selects one or more day numbers inside a `week`, `month`, `quarter`, or `year` interval.
   *   schedule_id - int64 - If trigger is `custom_schedule`, the reusable Schedule used instead of the Expectation's schedule fields.
   *   schedule_days_of_week - array(int64) - If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.
   *   schedule_times_of_day - array(string) - Times of day in HH:MM format for the Expectation schedule.
   *   schedule_time_zone - string - Time zone used by the Expectation schedule.
   *   holiday_region - string - Optional holiday region used by the Expectation schedule.
   *   lookback_interval - int64 - How many seconds before the due boundary the window starts.
   *   late_acceptance_interval - int64 - How many seconds a schedule-driven window may remain eligible to close as late.
   *   inactivity_interval - int64 - How many quiet seconds are required before final closure.
   *   max_open_interval - int64 - Hard-stop duration in seconds for unscheduled expectations.
   *   criteria - object - Versioned success criteria definition for the expectation, including optional Files Transform Script content validation in criteria v2.
   *   workspace_id - int64 - Workspace ID. `0` means the default workspace.
   */
  public static Expectation update() throws RuntimeException {
    return update(null, null, null);
  }

  public static Expectation update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static Expectation update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static Expectation update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().expectations().update(id, parameters, options);
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
    FilesClient.defaultClient().expectations().delete(id, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
