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
public class AiTask implements ModelInterface {
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


  public AiTask() {
    this(null, null);
  }

  public AiTask(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public AiTask(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * AI Task ID.
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
  * AI Task name.
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
  * AI Task description.
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
  * Prompt sent when this AI Task is invoked.
  */
  @JsonProperty("prompt")
  public String prompt;

  public String getPrompt() {
    return prompt;
  }

  public void setPrompt(String prompt) {
    this.prompt = prompt;
  }

  /**
  * Path scope used for action-triggered AI Tasks. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
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
  * Source glob used with `path` for action-triggered AI Tasks.
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
  * If true, this AI Task will not run.
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
  * How this AI Task is triggered.
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
  * If trigger is `action`, the file action types that invoke this AI Task. Valid actions are create, copy, move, archived_delete, update, read, destroy.
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
  * If trigger is `daily`, this specifies how often to run the AI Task.
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
  * Times of day in HH:MM format for scheduled AI Tasks.
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
  * Time zone used by the AI Task schedule.
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
  * Optional holiday region used by scheduled AI Tasks.
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
  * Most recent successful invocation time.
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
  * Master User ID used for AI Task invocations.
  */
  @JsonProperty("master_admin_user_id")
  public Long masterAdminUserId;

  public Long getMasterAdminUserId() {
    return masterAdminUserId;
  }

  public void setMasterAdminUserId(Long masterAdminUserId) {
    this.masterAdminUserId = masterAdminUserId;
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
  * Manually Run AI Task
  */
  public void manualRun(HashMap<String, Object> parameters) throws IOException {
    AiTask.manualRun(this.id, parameters, this.options);
  }

  /**
  * Parameters:
  *   description - string - AI Task description.
  *   disabled - boolean - If true, this AI Task will not run.
  *   holiday_region - string - Optional holiday region used by scheduled AI Tasks.
  *   interval - string - If trigger is `daily`, this specifies how often to run the AI Task.
  *   name - string - AI Task name.
  *   path - string - Path scope used for action-triggered AI Tasks.
  *   prompt - string - Prompt sent when this AI Task is invoked.
  *   recurring_day - int64 - If trigger is `daily`, this selects the day number inside the chosen interval.
  *   schedule_days_of_week - array(int64) - If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.
  *   schedule_time_zone - string - Time zone used by the AI Task schedule.
  *   schedule_times_of_day - array(string) - Times of day in HH:MM format for scheduled AI Tasks.
  *   source - string - Source glob used with `path` for action-triggered AI Tasks.
  *   trigger - string - How this AI Task is triggered.
  *   trigger_actions - array(string) - If trigger is `action`, the file action types that invoke this AI Task. Valid actions are create, copy, move, archived_delete, update, read, destroy.
  *   workspace_id - int64 - Workspace ID. `0` means the default workspace.
  */
  public AiTask update(HashMap<String, Object> parameters) throws IOException {
    return AiTask.update(this.id, parameters, this.options);
  }

  /**
  */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    AiTask.delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    AiTask.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `id`, `disabled` or `updated_at`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `disabled`, `trigger` or `workspace_id`. Valid field combinations are `[ workspace_id, disabled ]`.
  */
  public static ListIterator<AiTask> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<AiTask> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<AiTask> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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


    String url = String.format("%s%s/ai_tasks", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<AiTask>> typeReference = new TypeReference<List<AiTask>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<AiTask> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<AiTask> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Ai Task ID.
  */
  public static AiTask find() throws RuntimeException {
    return find(null, null, null);
  }

  public static AiTask find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static AiTask find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static AiTask find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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



    String url = String.format("%s%s/ai_tasks/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<AiTask> typeReference = new TypeReference<AiTask>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static AiTask get() throws RuntimeException {
    return get(null, null, null);
  }

  public static AiTask get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   description - string - AI Task description.
  *   disabled - boolean - If true, this AI Task will not run.
  *   holiday_region - string - Optional holiday region used by scheduled AI Tasks.
  *   interval - string - If trigger is `daily`, this specifies how often to run the AI Task.
  *   name (required) - string - AI Task name.
  *   path - string - Path scope used for action-triggered AI Tasks.
  *   prompt (required) - string - Prompt sent when this AI Task is invoked.
  *   recurring_day - int64 - If trigger is `daily`, this selects the day number inside the chosen interval.
  *   schedule_days_of_week - array(int64) - If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.
  *   schedule_time_zone - string - Time zone used by the AI Task schedule.
  *   schedule_times_of_day - array(string) - Times of day in HH:MM format for scheduled AI Tasks.
  *   source - string - Source glob used with `path` for action-triggered AI Tasks.
  *   trigger - string - How this AI Task is triggered.
  *   trigger_actions - array(string) - If trigger is `action`, the file action types that invoke this AI Task. Valid actions are create, copy, move, archived_delete, update, read, destroy.
  *   workspace_id - int64 - Workspace ID. `0` means the default workspace.
  */
  public static AiTask create() throws RuntimeException {
    return create(null, null);
  }

  public static AiTask create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static AiTask create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (!parameters.containsKey("name") || parameters.get("name") == null) {
      throw new NullPointerException("Parameter missing: name parameters[\"name\"]");
    }
    if (!parameters.containsKey("prompt") || parameters.get("prompt") == null) {
      throw new NullPointerException("Parameter missing: prompt parameters[\"prompt\"]");
    }

    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }
    if (parameters.containsKey("disabled") && !(parameters.get("disabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: disabled must be of type Boolean parameters[\"disabled\"]");
    }
    if (parameters.containsKey("holiday_region") && !(parameters.get("holiday_region") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: holiday_region must be of type String parameters[\"holiday_region\"]");
    }
    if (parameters.containsKey("interval") && !(parameters.get("interval") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: interval must be of type String parameters[\"interval\"]");
    }
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }
    if (parameters.containsKey("prompt") && !(parameters.get("prompt") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: prompt must be of type String parameters[\"prompt\"]");
    }
    if (parameters.containsKey("recurring_day") && !(parameters.get("recurring_day") instanceof Long || parameters.get("recurring_day") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: recurring_day must be of type Long or Integer parameters[\"recurring_day\"]");
    }
    if (parameters.containsKey("schedule_days_of_week") && !(parameters.get("schedule_days_of_week") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: schedule_days_of_week must be of type Long[] parameters[\"schedule_days_of_week\"]");
    }
    if (parameters.containsKey("schedule_time_zone") && !(parameters.get("schedule_time_zone") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: schedule_time_zone must be of type String parameters[\"schedule_time_zone\"]");
    }
    if (parameters.containsKey("schedule_times_of_day") && !(parameters.get("schedule_times_of_day") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: schedule_times_of_day must be of type String[] parameters[\"schedule_times_of_day\"]");
    }
    if (parameters.containsKey("source") && !(parameters.get("source") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: source must be of type String parameters[\"source\"]");
    }
    if (parameters.containsKey("trigger") && !(parameters.get("trigger") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: trigger must be of type String parameters[\"trigger\"]");
    }
    if (parameters.containsKey("trigger_actions") && !(parameters.get("trigger_actions") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: trigger_actions must be of type String[] parameters[\"trigger_actions\"]");
    }
    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }


    String url = String.format("%s%s/ai_tasks", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<AiTask> typeReference = new TypeReference<AiTask>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Manually Run AI Task
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



    String url = String.format("%s%s/ai_tasks/%s/manual_run", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.POST, parameters, options);
  }


  /**
  * Parameters:
  *   description - string - AI Task description.
  *   disabled - boolean - If true, this AI Task will not run.
  *   holiday_region - string - Optional holiday region used by scheduled AI Tasks.
  *   interval - string - If trigger is `daily`, this specifies how often to run the AI Task.
  *   name - string - AI Task name.
  *   path - string - Path scope used for action-triggered AI Tasks.
  *   prompt - string - Prompt sent when this AI Task is invoked.
  *   recurring_day - int64 - If trigger is `daily`, this selects the day number inside the chosen interval.
  *   schedule_days_of_week - array(int64) - If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.
  *   schedule_time_zone - string - Time zone used by the AI Task schedule.
  *   schedule_times_of_day - array(string) - Times of day in HH:MM format for scheduled AI Tasks.
  *   source - string - Source glob used with `path` for action-triggered AI Tasks.
  *   trigger - string - How this AI Task is triggered.
  *   trigger_actions - array(string) - If trigger is `action`, the file action types that invoke this AI Task. Valid actions are create, copy, move, archived_delete, update, read, destroy.
  *   workspace_id - int64 - Workspace ID. `0` means the default workspace.
  */
  public static AiTask update() throws RuntimeException {
    return update(null, null, null);
  }

  public static AiTask update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static AiTask update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static AiTask update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }
    if (parameters.containsKey("disabled") && !(parameters.get("disabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: disabled must be of type Boolean parameters[\"disabled\"]");
    }
    if (parameters.containsKey("holiday_region") && !(parameters.get("holiday_region") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: holiday_region must be of type String parameters[\"holiday_region\"]");
    }
    if (parameters.containsKey("interval") && !(parameters.get("interval") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: interval must be of type String parameters[\"interval\"]");
    }
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }
    if (parameters.containsKey("prompt") && !(parameters.get("prompt") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: prompt must be of type String parameters[\"prompt\"]");
    }
    if (parameters.containsKey("recurring_day") && !(parameters.get("recurring_day") instanceof Long || parameters.get("recurring_day") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: recurring_day must be of type Long or Integer parameters[\"recurring_day\"]");
    }
    if (parameters.containsKey("schedule_days_of_week") && !(parameters.get("schedule_days_of_week") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: schedule_days_of_week must be of type Long[] parameters[\"schedule_days_of_week\"]");
    }
    if (parameters.containsKey("schedule_time_zone") && !(parameters.get("schedule_time_zone") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: schedule_time_zone must be of type String parameters[\"schedule_time_zone\"]");
    }
    if (parameters.containsKey("schedule_times_of_day") && !(parameters.get("schedule_times_of_day") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: schedule_times_of_day must be of type String[] parameters[\"schedule_times_of_day\"]");
    }
    if (parameters.containsKey("source") && !(parameters.get("source") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: source must be of type String parameters[\"source\"]");
    }
    if (parameters.containsKey("trigger") && !(parameters.get("trigger") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: trigger must be of type String parameters[\"trigger\"]");
    }
    if (parameters.containsKey("trigger_actions") && !(parameters.get("trigger_actions") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: trigger_actions must be of type String[] parameters[\"trigger_actions\"]");
    }
    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }



    String url = String.format("%s%s/ai_tasks/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<AiTask> typeReference = new TypeReference<AiTask>() {};
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



    String url = String.format("%s%s/ai_tasks/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
