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
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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

  public void setOptions(HashMap<String, Object> options) {
    this.options = options;
  }

  private ObjectMapper objectMapper = JsonMapper
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
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
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
  * Times of day in HH:MM format for schedule-driven expectations.
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
  * Time zone used by the expectation schedule.
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
  * Optional holiday region used by schedule-driven expectations.
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
  * Structured criteria v1 definition for the expectation.
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
  public ExpectationEvaluation trigger(HashMap<String, Object> parameters) throws IOException {
    return Expectation.trigger(this.id, parameters, this.options);
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
  *   schedule_days_of_week - array(int64) - If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.
  *   schedule_times_of_day - array(string) - Times of day in HH:MM format for schedule-driven expectations.
  *   schedule_time_zone - string - Time zone used by the expectation schedule.
  *   holiday_region - string - Optional holiday region used by schedule-driven expectations.
  *   lookback_interval - int64 - How many seconds before the due boundary the window starts.
  *   late_acceptance_interval - int64 - How many seconds a schedule-driven window may remain eligible to close as late.
  *   inactivity_interval - int64 - How many quiet seconds are required before final closure.
  *   max_open_interval - int64 - Hard-stop duration in seconds for unscheduled expectations.
  *   criteria - object - Structured criteria v1 definition for the expectation.
  *   workspace_id - int64 - Workspace ID. `0` means the default workspace.
  */
  public Expectation update(HashMap<String, Object> parameters) throws IOException {
    return Expectation.update(this.id, parameters, this.options);
  }

  /**
  */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    Expectation.delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    Expectation.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
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
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



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


    String url = String.format("%s%s/expectations", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<Expectation>> typeReference = new TypeReference<List<Expectation>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
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



    String url = String.format("%s%s/expectations/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<Expectation> typeReference = new TypeReference<Expectation>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
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
  *   schedule_days_of_week - array(int64) - If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.
  *   schedule_times_of_day - array(string) - Times of day in HH:MM format for schedule-driven expectations.
  *   schedule_time_zone - string - Time zone used by the expectation schedule.
  *   holiday_region - string - Optional holiday region used by schedule-driven expectations.
  *   lookback_interval - int64 - How many seconds before the due boundary the window starts.
  *   late_acceptance_interval - int64 - How many seconds a schedule-driven window may remain eligible to close as late.
  *   inactivity_interval - int64 - How many quiet seconds are required before final closure.
  *   max_open_interval - int64 - Hard-stop duration in seconds for unscheduled expectations.
  *   criteria - object - Structured criteria v1 definition for the expectation.
  *   workspace_id - int64 - Workspace ID. `0` means the default workspace.
  */
  public static Expectation create() throws RuntimeException {
    return create(null, null);
  }

  public static Expectation create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static Expectation create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }
    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }
    if (parameters.containsKey("source") && !(parameters.get("source") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: source must be of type String parameters[\"source\"]");
    }
    if (parameters.containsKey("exclude_pattern") && !(parameters.get("exclude_pattern") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: exclude_pattern must be of type String parameters[\"exclude_pattern\"]");
    }
    if (parameters.containsKey("disabled") && !(parameters.get("disabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: disabled must be of type Boolean parameters[\"disabled\"]");
    }
    if (parameters.containsKey("trigger") && !(parameters.get("trigger") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: trigger must be of type String parameters[\"trigger\"]");
    }
    if (parameters.containsKey("interval") && !(parameters.get("interval") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: interval must be of type String parameters[\"interval\"]");
    }
    if (parameters.containsKey("recurring_day") && !(parameters.get("recurring_day") instanceof Long || parameters.get("recurring_day") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: recurring_day must be of type Long or Integer parameters[\"recurring_day\"]");
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
    if (parameters.containsKey("lookback_interval") && !(parameters.get("lookback_interval") instanceof Long || parameters.get("lookback_interval") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: lookback_interval must be of type Long or Integer parameters[\"lookback_interval\"]");
    }
    if (parameters.containsKey("late_acceptance_interval") && !(parameters.get("late_acceptance_interval") instanceof Long || parameters.get("late_acceptance_interval") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: late_acceptance_interval must be of type Long or Integer parameters[\"late_acceptance_interval\"]");
    }
    if (parameters.containsKey("inactivity_interval") && !(parameters.get("inactivity_interval") instanceof Long || parameters.get("inactivity_interval") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: inactivity_interval must be of type Long or Integer parameters[\"inactivity_interval\"]");
    }
    if (parameters.containsKey("max_open_interval") && !(parameters.get("max_open_interval") instanceof Long || parameters.get("max_open_interval") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: max_open_interval must be of type Long or Integer parameters[\"max_open_interval\"]");
    }
    if (parameters.containsKey("criteria") && !(parameters.get("criteria") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: criteria must be of type Object parameters[\"criteria\"]");
    }
    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }


    String url = String.format("%s%s/expectations", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<Expectation> typeReference = new TypeReference<Expectation>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Manually open an Expectation window
  */
  public static ExpectationEvaluation trigger() throws RuntimeException {
    return trigger(null, null, null);
  }

  public static ExpectationEvaluation trigger(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return trigger(id, parameters, null);
  }

  public static ExpectationEvaluation trigger(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return trigger(null, parameters, options);
  }

  public static ExpectationEvaluation trigger(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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



    String url = String.format("%s%s/expectations/%s/trigger", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<ExpectationEvaluation> typeReference = new TypeReference<ExpectationEvaluation>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
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
  *   schedule_days_of_week - array(int64) - If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.
  *   schedule_times_of_day - array(string) - Times of day in HH:MM format for schedule-driven expectations.
  *   schedule_time_zone - string - Time zone used by the expectation schedule.
  *   holiday_region - string - Optional holiday region used by schedule-driven expectations.
  *   lookback_interval - int64 - How many seconds before the due boundary the window starts.
  *   late_acceptance_interval - int64 - How many seconds a schedule-driven window may remain eligible to close as late.
  *   inactivity_interval - int64 - How many quiet seconds are required before final closure.
  *   max_open_interval - int64 - Hard-stop duration in seconds for unscheduled expectations.
  *   criteria - object - Structured criteria v1 definition for the expectation.
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
    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }
    if (parameters.containsKey("source") && !(parameters.get("source") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: source must be of type String parameters[\"source\"]");
    }
    if (parameters.containsKey("exclude_pattern") && !(parameters.get("exclude_pattern") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: exclude_pattern must be of type String parameters[\"exclude_pattern\"]");
    }
    if (parameters.containsKey("disabled") && !(parameters.get("disabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: disabled must be of type Boolean parameters[\"disabled\"]");
    }
    if (parameters.containsKey("trigger") && !(parameters.get("trigger") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: trigger must be of type String parameters[\"trigger\"]");
    }
    if (parameters.containsKey("interval") && !(parameters.get("interval") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: interval must be of type String parameters[\"interval\"]");
    }
    if (parameters.containsKey("recurring_day") && !(parameters.get("recurring_day") instanceof Long || parameters.get("recurring_day") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: recurring_day must be of type Long or Integer parameters[\"recurring_day\"]");
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
    if (parameters.containsKey("lookback_interval") && !(parameters.get("lookback_interval") instanceof Long || parameters.get("lookback_interval") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: lookback_interval must be of type Long or Integer parameters[\"lookback_interval\"]");
    }
    if (parameters.containsKey("late_acceptance_interval") && !(parameters.get("late_acceptance_interval") instanceof Long || parameters.get("late_acceptance_interval") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: late_acceptance_interval must be of type Long or Integer parameters[\"late_acceptance_interval\"]");
    }
    if (parameters.containsKey("inactivity_interval") && !(parameters.get("inactivity_interval") instanceof Long || parameters.get("inactivity_interval") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: inactivity_interval must be of type Long or Integer parameters[\"inactivity_interval\"]");
    }
    if (parameters.containsKey("max_open_interval") && !(parameters.get("max_open_interval") instanceof Long || parameters.get("max_open_interval") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: max_open_interval must be of type Long or Integer parameters[\"max_open_interval\"]");
    }
    if (parameters.containsKey("criteria") && !(parameters.get("criteria") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: criteria must be of type Object parameters[\"criteria\"]");
    }
    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }



    String url = String.format("%s%s/expectations/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<Expectation> typeReference = new TypeReference<Expectation>() {};
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



    String url = String.format("%s%s/expectations/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
