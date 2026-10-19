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

  // One mapper for every Expectation, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code Expectation} with no attributes set.
   */
  public Expectation() {
    this(null, null);
  }

  /**
   * Creates a {@code Expectation} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public Expectation(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code Expectation} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
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
   * Workspace ID. `0` means the default workspace.
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
   * Expectation name.
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
   * Expectation description.
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
   * Path scope for the expectation. Supports workspace-relative presentation. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
   */
  @JsonProperty("path")
  public String path;

  /**
   * Gets {@link #path}.
   *
   * @return the value of {@link #path}
   */
  public String getPath() {
    return path;
  }

  /**
   * Sets {@link #path}.
   *
   * @param path the new value
   */
  public void setPath(String path) {
    this.path = path;
  }

  /**
   * Source glob used to select candidate files.
   */
  @JsonProperty("source")
  public String source;

  /**
   * Gets {@link #source}.
   *
   * @return the value of {@link #source}
   */
  public String getSource() {
    return source;
  }

  /**
   * Sets {@link #source}.
   *
   * @param source the new value
   */
  public void setSource(String source) {
    this.source = source;
  }

  /**
   * Optional source exclusion glob.
   */
  @JsonProperty("exclude_pattern")
  public String excludePattern;

  /**
   * Gets {@link #excludePattern}.
   *
   * @return the value of {@link #excludePattern}
   */
  public String getExcludePattern() {
    return excludePattern;
  }

  /**
   * Sets {@link #excludePattern}.
   *
   * @param excludePattern the new value
   */
  public void setExcludePattern(String excludePattern) {
    this.excludePattern = excludePattern;
  }

  /**
   * If true, the expectation is disabled.
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
   * Criteria schema version for this expectation.
   */
  @JsonProperty("expectations_version")
  public Long expectationsVersion;

  /**
   * Gets {@link #expectationsVersion}.
   *
   * @return the value of {@link #expectationsVersion}
   */
  public Long getExpectationsVersion() {
    return expectationsVersion;
  }

  /**
   * Sets {@link #expectationsVersion}.
   *
   * @param expectationsVersion the new value
   */
  public void setExpectationsVersion(Long expectationsVersion) {
    this.expectationsVersion = expectationsVersion;
  }

  /**
   * How this expectation opens windows.
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
   * If trigger is `daily`, this specifies how often to run the expectation.
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
   * If trigger is `custom_schedule`, the reusable Schedule used instead of the Expectation's schedule fields.
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
   * Times of day in HH:MM format for the Expectation schedule.
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
   * Time zone used by the Expectation schedule.
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
   * Optional holiday region used by the Expectation schedule.
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
   * How many seconds before the due boundary the window starts.
   */
  @JsonProperty("lookback_interval")
  public Long lookbackInterval;

  /**
   * Gets {@link #lookbackInterval}.
   *
   * @return the value of {@link #lookbackInterval}
   */
  public Long getLookbackInterval() {
    return lookbackInterval;
  }

  /**
   * Sets {@link #lookbackInterval}.
   *
   * @param lookbackInterval the new value
   */
  public void setLookbackInterval(Long lookbackInterval) {
    this.lookbackInterval = lookbackInterval;
  }

  /**
   * How many seconds a schedule-driven window may remain eligible to close as late.
   */
  @JsonProperty("late_acceptance_interval")
  public Long lateAcceptanceInterval;

  /**
   * Gets {@link #lateAcceptanceInterval}.
   *
   * @return the value of {@link #lateAcceptanceInterval}
   */
  public Long getLateAcceptanceInterval() {
    return lateAcceptanceInterval;
  }

  /**
   * Sets {@link #lateAcceptanceInterval}.
   *
   * @param lateAcceptanceInterval the new value
   */
  public void setLateAcceptanceInterval(Long lateAcceptanceInterval) {
    this.lateAcceptanceInterval = lateAcceptanceInterval;
  }

  /**
   * How many quiet seconds are required before final closure.
   */
  @JsonProperty("inactivity_interval")
  public Long inactivityInterval;

  /**
   * Gets {@link #inactivityInterval}.
   *
   * @return the value of {@link #inactivityInterval}
   */
  public Long getInactivityInterval() {
    return inactivityInterval;
  }

  /**
   * Sets {@link #inactivityInterval}.
   *
   * @param inactivityInterval the new value
   */
  public void setInactivityInterval(Long inactivityInterval) {
    this.inactivityInterval = inactivityInterval;
  }

  /**
   * Hard-stop duration in seconds for unscheduled expectations.
   */
  @JsonProperty("max_open_interval")
  public Long maxOpenInterval;

  /**
   * Gets {@link #maxOpenInterval}.
   *
   * @return the value of {@link #maxOpenInterval}
   */
  public Long getMaxOpenInterval() {
    return maxOpenInterval;
  }

  /**
   * Sets {@link #maxOpenInterval}.
   *
   * @param maxOpenInterval the new value
   */
  public void setMaxOpenInterval(Long maxOpenInterval) {
    this.maxOpenInterval = maxOpenInterval;
  }

  /**
   * Versioned success criteria definition for the expectation. Criteria v2 supports optional FTS content validation.
   */
  @JsonProperty("criteria")
  public Object criteria;

  /**
   * Gets {@link #criteria}.
   *
   * @return the value of {@link #criteria}
   */
  public Object getCriteria() {
    return criteria;
  }

  /**
   * Sets {@link #criteria}.
   *
   * @param criteria the new value
   */
  public void setCriteria(Object criteria) {
    this.criteria = criteria;
  }

  /**
   * Last time this expectation was evaluated.
   */
  @JsonProperty("last_evaluated_at")
  public Date lastEvaluatedAt;

  /**
   * Gets {@link #lastEvaluatedAt}.
   *
   * @return the value of {@link #lastEvaluatedAt}
   */
  public Date getLastEvaluatedAt() {
    return lastEvaluatedAt;
  }

  /**
   * Sets {@link #lastEvaluatedAt}.
   *
   * @param lastEvaluatedAt the new value
   */
  public void setLastEvaluatedAt(Date lastEvaluatedAt) {
    this.lastEvaluatedAt = lastEvaluatedAt;
  }

  /**
   * Last time this expectation closed successfully.
   */
  @JsonProperty("last_success_at")
  public Date lastSuccessAt;

  /**
   * Gets {@link #lastSuccessAt}.
   *
   * @return the value of {@link #lastSuccessAt}
   */
  public Date getLastSuccessAt() {
    return lastSuccessAt;
  }

  /**
   * Sets {@link #lastSuccessAt}.
   *
   * @param lastSuccessAt the new value
   */
  public void setLastSuccessAt(Date lastSuccessAt) {
    this.lastSuccessAt = lastSuccessAt;
  }

  /**
   * Last time this expectation closed with a failure result.
   */
  @JsonProperty("last_failure_at")
  public Date lastFailureAt;

  /**
   * Gets {@link #lastFailureAt}.
   *
   * @return the value of {@link #lastFailureAt}
   */
  public Date getLastFailureAt() {
    return lastFailureAt;
  }

  /**
   * Sets {@link #lastFailureAt}.
   *
   * @param lastFailureAt the new value
   */
  public void setLastFailureAt(Date lastFailureAt) {
    this.lastFailureAt = lastFailureAt;
  }

  /**
   * Most recent terminal result for this expectation.
   */
  @JsonProperty("last_result")
  public String lastResult;

  /**
   * Gets {@link #lastResult}.
   *
   * @return the value of {@link #lastResult}
   */
  public String getLastResult() {
    return lastResult;
  }

  /**
   * Sets {@link #lastResult}.
   *
   * @param lastResult the new value
   */
  public void setLastResult(String lastResult) {
    this.lastResult = lastResult;
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
   * Manually open an Expectation window
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code ExpectationEvaluation}
   */
  public ExpectationEvaluation triggerEvaluation(HashMap<String, Object> parameters) throws IOException {
    return filesClient().expectations().triggerEvaluation(this.id, parameters, this.options);
  }

  /**
   * Updates this Expectation. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update Expectation
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Expectation name.</li>
   * <li>{@code description} (String): Expectation description.</li>
   * <li>{@code path} (String): Path scope for the expectation. Supports workspace-relative presentation.</li>
   * <li>{@code source} (String): Source glob used to select candidate files.</li>
   * <li>{@code exclude_pattern} (String): Optional source exclusion glob.</li>
   * <li>{@code disabled} (Boolean): If true, the expectation is disabled.</li>
   * <li>{@code trigger} (String): How this expectation opens windows.</li>
   * <li>{@code interval} (String): If trigger is `daily`, this specifies how often to run the expectation.</li>
   * <li>{@code recurring_day} (Long): If trigger is `daily`, this selects the day number inside the chosen interval.</li>
   * <li>{@code recurring_days} (Long[]): If trigger is `daily`, this selects one or more day numbers inside a `week`, `month`, `quarter`, or `year` interval.</li>
   * <li>{@code schedule_id} (Long): If trigger is `custom_schedule`, the reusable Schedule used instead of the Expectation's schedule fields.</li>
   * <li>{@code schedule_days_of_week} (Long[]): If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.</li>
   * <li>{@code schedule_times_of_day} (String[]): Times of day in HH:MM format for the Expectation schedule.</li>
   * <li>{@code schedule_time_zone} (String): Time zone used by the Expectation schedule.</li>
   * <li>{@code holiday_region} (String): Optional holiday region used by the Expectation schedule.</li>
   * <li>{@code lookback_interval} (Long): How many seconds before the due boundary the window starts.</li>
   * <li>{@code late_acceptance_interval} (Long): How many seconds a schedule-driven window may remain eligible to close as late.</li>
   * <li>{@code inactivity_interval} (Long): How many quiet seconds are required before final closure.</li>
   * <li>{@code max_open_interval} (Long): Hard-stop duration in seconds for unscheduled expectations.</li>
   * <li>{@code criteria} (Object): Versioned success criteria definition for the expectation, including optional Files Transform Script content validation in criteria v2.</li>
   * <li>{@code workspace_id} (Long): Workspace ID. `0` means the default workspace.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code Expectation}
   */
  public Expectation update(HashMap<String, Object> parameters) throws IOException {
    Expectation updated = filesClient().expectations().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete Expectation
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().expectations().delete(this.id, parameters, this.options);
  }

  /**
   * Delete Expectation
   *
   * @see #delete(HashMap)
   */
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
   * List Expectations
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Expectation> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Expectations
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Expectation> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Expectations
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `name` or `disabled`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `disabled` and `workspace_id`. Valid field combinations are `[ workspace_id, disabled ]`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Expectation} records, in a {@code ListIterator}
   */
  public static ListIterator<Expectation> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().expectations().list(parameters, options);
  }

  /**
   * List Expectations
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Expectation> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Expectations
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Expectation> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Expectation
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Expectation find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Expectation
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Expectation find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Expectation
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Expectation find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Expectation
   *
   * @param id Expectation ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Expectation}
   */
  public static Expectation find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().expectations().find(id, parameters, options);
  }

  /**
   * Show Expectation
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Expectation get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Expectation
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Expectation get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Expectation
   *
   * @see #create(HashMap, HashMap)
   */
  public static Expectation create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Expectation
   *
   * @see #create(HashMap, HashMap)
   */
  public static Expectation create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create Expectation
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Expectation name.</li>
   * <li>{@code description} (String): Expectation description.</li>
   * <li>{@code path} (String): Path scope for the expectation. Supports workspace-relative presentation.</li>
   * <li>{@code source} (String): Source glob used to select candidate files.</li>
   * <li>{@code exclude_pattern} (String): Optional source exclusion glob.</li>
   * <li>{@code disabled} (Boolean): If true, the expectation is disabled.</li>
   * <li>{@code trigger} (String): How this expectation opens windows.</li>
   * <li>{@code interval} (String): If trigger is `daily`, this specifies how often to run the expectation.</li>
   * <li>{@code recurring_day} (Long): If trigger is `daily`, this selects the day number inside the chosen interval.</li>
   * <li>{@code recurring_days} (Long[]): If trigger is `daily`, this selects one or more day numbers inside a `week`, `month`, `quarter`, or `year` interval.</li>
   * <li>{@code schedule_id} (Long): If trigger is `custom_schedule`, the reusable Schedule used instead of the Expectation's schedule fields.</li>
   * <li>{@code schedule_days_of_week} (Long[]): If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.</li>
   * <li>{@code schedule_times_of_day} (String[]): Times of day in HH:MM format for the Expectation schedule.</li>
   * <li>{@code schedule_time_zone} (String): Time zone used by the Expectation schedule.</li>
   * <li>{@code holiday_region} (String): Optional holiday region used by the Expectation schedule.</li>
   * <li>{@code lookback_interval} (Long): How many seconds before the due boundary the window starts.</li>
   * <li>{@code late_acceptance_interval} (Long): How many seconds a schedule-driven window may remain eligible to close as late.</li>
   * <li>{@code inactivity_interval} (Long): How many quiet seconds are required before final closure.</li>
   * <li>{@code max_open_interval} (Long): Hard-stop duration in seconds for unscheduled expectations.</li>
   * <li>{@code criteria} (Object): Versioned success criteria definition for the expectation, including optional Files Transform Script content validation in criteria v2.</li>
   * <li>{@code workspace_id} (Long): Workspace ID. `0` means the default workspace.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Expectation}
   */
  public static Expectation create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().expectations().create(parameters, options);
  }


  /**
   * Manually open an Expectation window
   *
   * @see #triggerEvaluation(Long, HashMap, HashMap)
   */
  public static ExpectationEvaluation triggerEvaluation() throws RuntimeException {
    return triggerEvaluation(null, null, null);
  }

  /**
   * Manually open an Expectation window
   *
   * @see #triggerEvaluation(Long, HashMap, HashMap)
   */
  public static ExpectationEvaluation triggerEvaluation(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return triggerEvaluation(id, parameters, null);
  }

  /**
   * Manually open an Expectation window
   *
   * @see #triggerEvaluation(Long, HashMap, HashMap)
   */
  public static ExpectationEvaluation triggerEvaluation(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return triggerEvaluation(null, parameters, options);
  }

  /**
   * Manually open an Expectation window
   *
   * @param id Expectation ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ExpectationEvaluation}
   */
  public static ExpectationEvaluation triggerEvaluation(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().expectations().triggerEvaluation(id, parameters, options);
  }


  /**
   * Update Expectation
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static Expectation update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Expectation
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static Expectation update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Expectation
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static Expectation update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Expectation
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Expectation name.</li>
   * <li>{@code description} (String): Expectation description.</li>
   * <li>{@code path} (String): Path scope for the expectation. Supports workspace-relative presentation.</li>
   * <li>{@code source} (String): Source glob used to select candidate files.</li>
   * <li>{@code exclude_pattern} (String): Optional source exclusion glob.</li>
   * <li>{@code disabled} (Boolean): If true, the expectation is disabled.</li>
   * <li>{@code trigger} (String): How this expectation opens windows.</li>
   * <li>{@code interval} (String): If trigger is `daily`, this specifies how often to run the expectation.</li>
   * <li>{@code recurring_day} (Long): If trigger is `daily`, this selects the day number inside the chosen interval.</li>
   * <li>{@code recurring_days} (Long[]): If trigger is `daily`, this selects one or more day numbers inside a `week`, `month`, `quarter`, or `year` interval.</li>
   * <li>{@code schedule_id} (Long): If trigger is `custom_schedule`, the reusable Schedule used instead of the Expectation's schedule fields.</li>
   * <li>{@code schedule_days_of_week} (Long[]): If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.</li>
   * <li>{@code schedule_times_of_day} (String[]): Times of day in HH:MM format for the Expectation schedule.</li>
   * <li>{@code schedule_time_zone} (String): Time zone used by the Expectation schedule.</li>
   * <li>{@code holiday_region} (String): Optional holiday region used by the Expectation schedule.</li>
   * <li>{@code lookback_interval} (Long): How many seconds before the due boundary the window starts.</li>
   * <li>{@code late_acceptance_interval} (Long): How many seconds a schedule-driven window may remain eligible to close as late.</li>
   * <li>{@code inactivity_interval} (Long): How many quiet seconds are required before final closure.</li>
   * <li>{@code max_open_interval} (Long): Hard-stop duration in seconds for unscheduled expectations.</li>
   * <li>{@code criteria} (Object): Versioned success criteria definition for the expectation, including optional Files Transform Script content validation in criteria v2.</li>
   * <li>{@code workspace_id} (Long): Workspace ID. `0` means the default workspace.</li>
   * </ul>
   *
   * @param id Expectation ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Expectation}
   */
  public static Expectation update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().expectations().update(id, parameters, options);
  }


  /**
   * Delete Expectation
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Expectation
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Expectation
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Expectation
   *
   * @param id Expectation ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().expectations().delete(id, parameters, options);
  }

  /**
   * Delete Expectation
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Expectation
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
