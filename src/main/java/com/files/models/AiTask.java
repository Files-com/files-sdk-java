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
public class AiTask implements ModelInterface {
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

  // One mapper for every AiTask, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code AiTask} with no attributes set.
   */
  public AiTask() {
    this(null, null);
  }

  /**
   * Creates a {@code AiTask} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public AiTask(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code AiTask} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public AiTask(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * AI Task ID.
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
   * AI Task name.
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
   * AI Task description.
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
   * Prompt sent when this AI Task is invoked.
   */
  @JsonProperty("prompt")
  public String prompt;

  /**
   * Gets {@link #prompt}.
   *
   * @return the value of {@link #prompt}
   */
  public String getPrompt() {
    return prompt;
  }

  /**
   * Sets {@link #prompt}.
   *
   * @param prompt the new value
   */
  public void setPrompt(String prompt) {
    this.prompt = prompt;
  }

  /**
   * Permissions used by the internal API key for this AI Task. Valid values are `full` and `files_only`.
   */
  @JsonProperty("permission_set")
  public String permissionSet;

  /**
   * Gets {@link #permissionSet}.
   *
   * @return the value of {@link #permissionSet}
   */
  public String getPermissionSet() {
    return permissionSet;
  }

  /**
   * Sets {@link #permissionSet}.
   *
   * @param permissionSet the new value
   */
  public void setPermissionSet(String permissionSet) {
    this.permissionSet = permissionSet;
  }

  /**
   * Path scope used for action-triggered AI Tasks. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
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
   * Source glob used with `path` for action-triggered AI Tasks.
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
   * If true, this AI Task will not run.
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
   * How this AI Task is triggered.
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
   * If trigger is `action`, the file action types that invoke this AI Task. Valid actions are create, copy, move, archived_delete, update, read, destroy.
   */
  @JsonProperty("trigger_actions")
  public String[] triggerActions;

  /**
   * Gets {@link #triggerActions}.
   *
   * @return the value of {@link #triggerActions}
   */
  public String[] getTriggerActions() {
    return triggerActions;
  }

  /**
   * Sets {@link #triggerActions}.
   *
   * @param triggerActions the new value
   */
  public void setTriggerActions(String[] triggerActions) {
    this.triggerActions = triggerActions;
  }

  /**
   * If trigger is `daily`, this specifies how often to run the AI Task.
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
   * If trigger is `custom_schedule`, the reusable Schedule used instead of the AI Task's schedule fields.
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
   * Times of day in HH:MM format for the AI Task schedule.
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
   * Time zone used by the AI Task schedule.
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
   * Optional holiday region used by the AI Task schedule.
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
   * Most recent successful invocation time.
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
   * Master User ID used for AI Task invocations.
   */
  @JsonProperty("master_admin_user_id")
  public Long masterAdminUserId;

  /**
   * Gets {@link #masterAdminUserId}.
   *
   * @return the value of {@link #masterAdminUserId}
   */
  public Long getMasterAdminUserId() {
    return masterAdminUserId;
  }

  /**
   * Sets {@link #masterAdminUserId}.
   *
   * @param masterAdminUserId the new value
   */
  public void setMasterAdminUserId(Long masterAdminUserId) {
    this.masterAdminUserId = masterAdminUserId;
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
   * Manually Run AI Task
   *
   * @param parameters the request's parameters, by their API names
   */
  public void manualRun(HashMap<String, Object> parameters) throws IOException {
    filesClient().aiTasks().manualRun(this.id, parameters, this.options);
  }

  /**
   * Updates this AiTask. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update Ai Task
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code description} (String): AI Task description.</li>
   * <li>{@code disabled} (Boolean): If true, this AI Task will not run.</li>
   * <li>{@code holiday_region} (String): Optional holiday region used by the AI Task schedule.</li>
   * <li>{@code interval} (String): If trigger is `daily`, this specifies how often to run the AI Task.</li>
   * <li>{@code name} (String): AI Task name.</li>
   * <li>{@code path} (String): Path scope used for action-triggered AI Tasks.</li>
   * <li>{@code permission_set} (String): Permissions used by the internal API key for this AI Task. Valid values are `full` and `files_only`.</li>
   * <li>{@code prompt} (String): Prompt sent when this AI Task is invoked.</li>
   * <li>{@code recurring_day} (Long): If trigger is `daily`, this selects the day number inside the chosen interval.</li>
   * <li>{@code recurring_days} (Long[]): If trigger is `daily`, this selects one or more day numbers inside a `week`, `month`, `quarter`, or `year` interval.</li>
   * <li>{@code schedule_id} (Long): If trigger is `custom_schedule`, the reusable Schedule used instead of the AI Task's schedule fields.</li>
   * <li>{@code schedule_days_of_week} (Long[]): If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.</li>
   * <li>{@code schedule_time_zone} (String): Time zone used by the AI Task schedule.</li>
   * <li>{@code schedule_times_of_day} (String[]): Times of day in HH:MM format for the AI Task schedule.</li>
   * <li>{@code source} (String): Source glob used with `path` for action-triggered AI Tasks.</li>
   * <li>{@code trigger} (String): How this AI Task is triggered.</li>
   * <li>{@code trigger_actions} (String[]): If trigger is `action`, the file action types that invoke this AI Task. Valid actions are create, copy, move, archived_delete, update, read, destroy.</li>
   * <li>{@code workspace_id} (Long): Workspace ID. `0` means the default workspace.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code AiTask}
   */
  public AiTask update(HashMap<String, Object> parameters) throws IOException {
    AiTask updated = filesClient().aiTasks().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete Ai Task
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().aiTasks().delete(this.id, parameters, this.options);
  }

  /**
   * Delete Ai Task
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this AiTask: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().aiTasks().create(parameters, this.options));
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
    if (this.triggerActions != null) {
      parameters.put("trigger_actions", this.triggerActions);
    }
    return parameters;
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(AiTask canonical) {
    this.id = canonical.id;
    this.workspaceId = canonical.workspaceId;
    this.name = canonical.name;
    this.description = canonical.description;
    this.prompt = canonical.prompt;
    this.permissionSet = canonical.permissionSet;
    this.path = canonical.path;
    this.source = canonical.source;
    this.disabled = canonical.disabled;
    this.trigger = canonical.trigger;
    this.triggerActions = canonical.triggerActions;
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
    this.masterAdminUserId = canonical.masterAdminUserId;
    this.createdAt = canonical.createdAt;
    this.updatedAt = canonical.updatedAt;
  }

  /**
   * List Ai Tasks
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<AiTask> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Ai Tasks
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<AiTask> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Ai Tasks
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `name`, `disabled` or `updated_at`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `disabled`, `trigger` or `workspace_id`. Valid field combinations are `[ workspace_id, disabled ]`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code AiTask} records, in a {@code ListIterator}
   */
  public static ListIterator<AiTask> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().aiTasks().list(parameters, options);
  }

  /**
   * List Ai Tasks
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<AiTask> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Ai Tasks
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<AiTask> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Ai Task
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static AiTask find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Ai Task
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static AiTask find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Ai Task
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static AiTask find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Ai Task
   *
   * @param id Ai Task ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code AiTask}
   */
  public static AiTask find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().aiTasks().find(id, parameters, options);
  }

  /**
   * Show Ai Task
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static AiTask get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Ai Task
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static AiTask get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Ai Task
   *
   * @see #create(HashMap, HashMap)
   */
  public static AiTask create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Ai Task
   *
   * @see #create(HashMap, HashMap)
   */
  public static AiTask create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create Ai Task
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code description} (String): AI Task description.</li>
   * <li>{@code disabled} (Boolean): If true, this AI Task will not run.</li>
   * <li>{@code holiday_region} (String): Optional holiday region used by the AI Task schedule.</li>
   * <li>{@code interval} (String): If trigger is `daily`, this specifies how often to run the AI Task.</li>
   * <li>{@code name} (String): Required. AI Task name.</li>
   * <li>{@code path} (String): Path scope used for action-triggered AI Tasks.</li>
   * <li>{@code permission_set} (String): Permissions used by the internal API key for this AI Task. Valid values are `full` and `files_only`.</li>
   * <li>{@code prompt} (String): Required. Prompt sent when this AI Task is invoked.</li>
   * <li>{@code recurring_day} (Long): If trigger is `daily`, this selects the day number inside the chosen interval.</li>
   * <li>{@code recurring_days} (Long[]): If trigger is `daily`, this selects one or more day numbers inside a `week`, `month`, `quarter`, or `year` interval.</li>
   * <li>{@code schedule_id} (Long): If trigger is `custom_schedule`, the reusable Schedule used instead of the AI Task's schedule fields.</li>
   * <li>{@code schedule_days_of_week} (Long[]): If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.</li>
   * <li>{@code schedule_time_zone} (String): Time zone used by the AI Task schedule.</li>
   * <li>{@code schedule_times_of_day} (String[]): Times of day in HH:MM format for the AI Task schedule.</li>
   * <li>{@code source} (String): Source glob used with `path` for action-triggered AI Tasks.</li>
   * <li>{@code trigger} (String): How this AI Task is triggered.</li>
   * <li>{@code trigger_actions} (String[]): If trigger is `action`, the file action types that invoke this AI Task. Valid actions are create, copy, move, archived_delete, update, read, destroy.</li>
   * <li>{@code workspace_id} (Long): Workspace ID. `0` means the default workspace.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code AiTask}
   */
  public static AiTask create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().aiTasks().create(parameters, options);
  }


  /**
   * Manually Run AI Task
   *
   * @see #manualRun(Long, HashMap, HashMap)
   */
  public static void manualRun() throws RuntimeException {
    manualRun(null, null, null);
  }

  /**
   * Manually Run AI Task
   *
   * @see #manualRun(Long, HashMap, HashMap)
   */
  public static void manualRun(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    manualRun(id, parameters, null);
  }

  /**
   * Manually Run AI Task
   *
   * @see #manualRun(Long, HashMap, HashMap)
   */
  public static void manualRun(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    manualRun(null, parameters, options);
  }

  /**
   * Manually Run AI Task
   *
   * @param id Ai Task ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void manualRun(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().aiTasks().manualRun(id, parameters, options);
  }


  /**
   * Update Ai Task
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static AiTask update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Ai Task
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static AiTask update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Ai Task
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static AiTask update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Ai Task
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code description} (String): AI Task description.</li>
   * <li>{@code disabled} (Boolean): If true, this AI Task will not run.</li>
   * <li>{@code holiday_region} (String): Optional holiday region used by the AI Task schedule.</li>
   * <li>{@code interval} (String): If trigger is `daily`, this specifies how often to run the AI Task.</li>
   * <li>{@code name} (String): AI Task name.</li>
   * <li>{@code path} (String): Path scope used for action-triggered AI Tasks.</li>
   * <li>{@code permission_set} (String): Permissions used by the internal API key for this AI Task. Valid values are `full` and `files_only`.</li>
   * <li>{@code prompt} (String): Prompt sent when this AI Task is invoked.</li>
   * <li>{@code recurring_day} (Long): If trigger is `daily`, this selects the day number inside the chosen interval.</li>
   * <li>{@code recurring_days} (Long[]): If trigger is `daily`, this selects one or more day numbers inside a `week`, `month`, `quarter`, or `year` interval.</li>
   * <li>{@code schedule_id} (Long): If trigger is `custom_schedule`, the reusable Schedule used instead of the AI Task's schedule fields.</li>
   * <li>{@code schedule_days_of_week} (Long[]): If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.</li>
   * <li>{@code schedule_time_zone} (String): Time zone used by the AI Task schedule.</li>
   * <li>{@code schedule_times_of_day} (String[]): Times of day in HH:MM format for the AI Task schedule.</li>
   * <li>{@code source} (String): Source glob used with `path` for action-triggered AI Tasks.</li>
   * <li>{@code trigger} (String): How this AI Task is triggered.</li>
   * <li>{@code trigger_actions} (String[]): If trigger is `action`, the file action types that invoke this AI Task. Valid actions are create, copy, move, archived_delete, update, read, destroy.</li>
   * <li>{@code workspace_id} (Long): Workspace ID. `0` means the default workspace.</li>
   * </ul>
   *
   * @param id Ai Task ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code AiTask}
   */
  public static AiTask update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().aiTasks().update(id, parameters, options);
  }


  /**
   * Delete Ai Task
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Ai Task
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Ai Task
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Ai Task
   *
   * @param id Ai Task ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().aiTasks().delete(id, parameters, options);
  }

  /**
   * Delete Ai Task
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Ai Task
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
