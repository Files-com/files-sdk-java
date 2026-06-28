# Files.Models.AiTask

## Example AiTask Object

```
{
  "id": 1,
  "workspace_id": 1,
  "name": "Summarize daily reports",
  "description": "Summarizes files uploaded by the accounting team.",
  "prompt": "Summarize the uploaded file and identify follow-up actions.",
  "permission_set": "files_only",
  "path": "incoming/reports",
  "source": "*.pdf",
  "disabled": true,
  "trigger": "daily",
  "trigger_actions": [
    "create"
  ],
  "interval": "day",
  "recurring_day": 1,
  "schedule_days_of_week": [
    1,
    3,
    5
  ],
  "schedule_times_of_day": [
    "06:30"
  ],
  "schedule_time_zone": "Eastern Time (US & Canada)",
  "holiday_region": "us",
  "human_readable_schedule": "Runs every day at 06:30 AM UTC TZ.",
  "last_run_at": "2000-01-01T01:00:00Z",
  "master_admin_user_id": 1,
  "created_at": "2000-01-01T01:00:00Z",
  "updated_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): AI Task ID.
* `workspace_id` / `workspaceId`  (int64): Workspace ID. `0` means the default workspace.
* `name` / `name`  (string): AI Task name.
* `description` / `description`  (string): AI Task description.
* `prompt` / `prompt`  (string): Prompt sent when this AI Task is invoked.
* `permission_set` / `permissionSet`  (string): Permissions used by the internal API key for this AI Task. Valid values are `full` and `files_only`.
* `path` / `path`  (string): Path scope used for action-triggered AI Tasks. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `source` / `source`  (string): Source glob used with `path` for action-triggered AI Tasks.
* `disabled` / `disabled`  (boolean): If true, this AI Task will not run.
* `trigger` / `trigger`  (string): How this AI Task is triggered.
* `trigger_actions` / `triggerActions`  (array(string)): If trigger is `action`, the file action types that invoke this AI Task. Valid actions are create, copy, move, archived_delete, update, read, destroy.
* `interval` / `interval`  (string): If trigger is `daily`, this specifies how often to run the AI Task.
* `recurring_day` / `recurringDay`  (int64): If trigger is `daily`, this selects the day number inside the chosen interval.
* `schedule_days_of_week` / `scheduleDaysOfWeek`  (array(int64)): If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.
* `schedule_times_of_day` / `scheduleTimesOfDay`  (array(string)): Times of day in HH:MM format for scheduled AI Tasks.
* `schedule_time_zone` / `scheduleTimeZone`  (string): Time zone used by the AI Task schedule.
* `holiday_region` / `holidayRegion`  (string): Optional holiday region used by scheduled AI Tasks.
* `human_readable_schedule` / `humanReadableSchedule`  (string): Human-readable schedule description.
* `last_run_at` / `lastRunAt`  (date-time): Most recent successful invocation time.
* `master_admin_user_id` / `masterAdminUserId`  (int64): Master User ID used for AI Task invocations.
* `created_at` / `createdAt`  (date-time): Creation time.
* `updated_at` / `updatedAt`  (date-time): Last update time.


---

## List Ai Tasks

```
ListIterator<AiTask> aiTask = AiTask.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `id`, `disabled` or `updated_at`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `disabled`, `trigger` or `workspace_id`. Valid field combinations are `[ workspace_id, disabled ]`.


---

## Show Ai Task

```
AiTask aiTask = AiTask.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Ai Task ID.


---

## Create Ai Task

```
AiTask aiTask = AiTask.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `description` (String): AI Task description.
* `disabled` (Boolean): If true, this AI Task will not run.
* `holiday_region` (String): Optional holiday region used by scheduled AI Tasks.
* `interval` (String): If trigger is `daily`, this specifies how often to run the AI Task.
* `name` (String): Required - AI Task name.
* `path` (String): Path scope used for action-triggered AI Tasks.
* `permission_set` (String): Permissions used by the internal API key for this AI Task. Valid values are `full` and `files_only`.
* `prompt` (String): Required - Prompt sent when this AI Task is invoked.
* `recurring_day` (Long): If trigger is `daily`, this selects the day number inside the chosen interval.
* `schedule_days_of_week` (Long[]): If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.
* `schedule_time_zone` (String): Time zone used by the AI Task schedule.
* `schedule_times_of_day` (String[]): Times of day in HH:MM format for scheduled AI Tasks.
* `source` (String): Source glob used with `path` for action-triggered AI Tasks.
* `trigger` (String): How this AI Task is triggered.
* `trigger_actions` (String[]): If trigger is `action`, the file action types that invoke this AI Task. Valid actions are create, copy, move, archived_delete, update, read, destroy.
* `workspace_id` (Long): Workspace ID. `0` means the default workspace.


---

## Manually Run AI Task

```
void aiTask = AiTask.manualRun(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Ai Task ID.


---

## Update Ai Task

```
AiTask aiTask = AiTask.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Ai Task ID.
* `description` (String): AI Task description.
* `disabled` (Boolean): If true, this AI Task will not run.
* `holiday_region` (String): Optional holiday region used by scheduled AI Tasks.
* `interval` (String): If trigger is `daily`, this specifies how often to run the AI Task.
* `name` (String): AI Task name.
* `path` (String): Path scope used for action-triggered AI Tasks.
* `permission_set` (String): Permissions used by the internal API key for this AI Task. Valid values are `full` and `files_only`.
* `prompt` (String): Prompt sent when this AI Task is invoked.
* `recurring_day` (Long): If trigger is `daily`, this selects the day number inside the chosen interval.
* `schedule_days_of_week` (Long[]): If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.
* `schedule_time_zone` (String): Time zone used by the AI Task schedule.
* `schedule_times_of_day` (String[]): Times of day in HH:MM format for scheduled AI Tasks.
* `source` (String): Source glob used with `path` for action-triggered AI Tasks.
* `trigger` (String): How this AI Task is triggered.
* `trigger_actions` (String[]): If trigger is `action`, the file action types that invoke this AI Task. Valid actions are create, copy, move, archived_delete, update, read, destroy.
* `workspace_id` (Long): Workspace ID. `0` means the default workspace.


---

## Delete Ai Task

```
void aiTask = AiTask.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Ai Task ID.


---

## Manually Run AI Task

```
AiTask aiTask = AiTask.find(id);

HashMap<String, Object> parameters = new HashMap<>();

aiTask.manualRun(parameters);
```

### Parameters

* `id` (Long): Required - Ai Task ID.


---

## Update Ai Task

```
AiTask aiTask = AiTask.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("description", "Summarizes files uploaded by the accounting team.");
parameters.put("disabled", true);
parameters.put("holiday_region", "us");
parameters.put("interval", "day");
parameters.put("name", "Summarize daily reports");
parameters.put("path", "incoming/reports");
parameters.put("permission_set", "files_only");
parameters.put("prompt", "Summarize the uploaded file and identify follow-up actions.");
parameters.put("recurring_day", 1);
parameters.put("schedule_days_of_week", [1,3,5]);
parameters.put("schedule_time_zone", "Eastern Time (US & Canada)");
parameters.put("schedule_times_of_day", ["06:30"]);
parameters.put("source", "*.pdf");
parameters.put("trigger", "daily");
parameters.put("trigger_actions", ["create"]);
parameters.put("workspace_id", 0);

aiTask.update(parameters);
```

### Parameters

* `id` (Long): Required - Ai Task ID.
* `description` (String): AI Task description.
* `disabled` (Boolean): If true, this AI Task will not run.
* `holiday_region` (String): Optional holiday region used by scheduled AI Tasks.
* `interval` (String): If trigger is `daily`, this specifies how often to run the AI Task.
* `name` (String): AI Task name.
* `path` (String): Path scope used for action-triggered AI Tasks.
* `permission_set` (String): Permissions used by the internal API key for this AI Task. Valid values are `full` and `files_only`.
* `prompt` (String): Prompt sent when this AI Task is invoked.
* `recurring_day` (Long): If trigger is `daily`, this selects the day number inside the chosen interval.
* `schedule_days_of_week` (Long[]): If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.
* `schedule_time_zone` (String): Time zone used by the AI Task schedule.
* `schedule_times_of_day` (String[]): Times of day in HH:MM format for scheduled AI Tasks.
* `source` (String): Source glob used with `path` for action-triggered AI Tasks.
* `trigger` (String): How this AI Task is triggered.
* `trigger_actions` (String[]): If trigger is `action`, the file action types that invoke this AI Task. Valid actions are create, copy, move, archived_delete, update, read, destroy.
* `workspace_id` (Long): Workspace ID. `0` means the default workspace.


---

## Delete Ai Task

```
AiTask aiTask = AiTask.find(id);

HashMap<String, Object> parameters = new HashMap<>();

aiTask.delete(parameters);
```

### Parameters

* `id` (Long): Required - Ai Task ID.
