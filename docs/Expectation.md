# Files.Models.Expectation

## Example Expectation Object

```
{
  "id": 1,
  "workspace_id": 1,
  "name": "Daily Vendor Feed",
  "description": "Wait for the vendor CSV every morning.",
  "path": "incoming/vendor_a",
  "source": "*.csv",
  "exclude_pattern": "*.tmp",
  "disabled": true,
  "expectations_version": 1,
  "trigger": "manual",
  "interval": "day",
  "recurring_day": 3,
  "schedule_days_of_week": [
    1,
    3,
    5
  ],
  "schedule_times_of_day": [
    "06:00"
  ],
  "schedule_time_zone": "UTC",
  "holiday_region": "us",
  "lookback_interval": 3600,
  "late_acceptance_interval": 900,
  "inactivity_interval": 300,
  "max_open_interval": 43200,
  "criteria": {
    "count": {
      "exact": 1
    },
    "extensions": [
      "csv"
    ]
  },
  "last_evaluated_at": "2000-01-01T01:00:00Z",
  "last_success_at": "2000-01-01T01:00:00Z",
  "last_failure_at": "2000-01-01T01:00:00Z",
  "last_result": "success",
  "created_at": "2000-01-01T01:00:00Z",
  "updated_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): Expectation ID
* `workspace_id` / `workspaceId`  (int64): Workspace ID. `0` means the default workspace.
* `name` / `name`  (string): Expectation name.
* `description` / `description`  (string): Expectation description.
* `path` / `path`  (string): Path scope for the expectation. Supports workspace-relative presentation. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `source` / `source`  (string): Source glob used to select candidate files.
* `exclude_pattern` / `excludePattern`  (string): Optional source exclusion glob.
* `disabled` / `disabled`  (boolean): If true, the expectation is disabled.
* `expectations_version` / `expectationsVersion`  (int64): Criteria schema version for this expectation.
* `trigger` / `trigger`  (string): How this expectation opens windows.
* `interval` / `interval`  (string): If trigger is `daily`, this specifies how often to run the expectation.
* `recurring_day` / `recurringDay`  (int64): If trigger is `daily`, this selects the day number inside the chosen interval.
* `schedule_days_of_week` / `scheduleDaysOfWeek`  (array(int64)): If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.
* `schedule_times_of_day` / `scheduleTimesOfDay`  (array(string)): Times of day in HH:MM format for schedule-driven expectations.
* `schedule_time_zone` / `scheduleTimeZone`  (string): Time zone used by the expectation schedule.
* `holiday_region` / `holidayRegion`  (string): Optional holiday region used by schedule-driven expectations.
* `lookback_interval` / `lookbackInterval`  (int64): How many seconds before the due boundary the window starts.
* `late_acceptance_interval` / `lateAcceptanceInterval`  (int64): How many seconds a schedule-driven window may remain eligible to close as late.
* `inactivity_interval` / `inactivityInterval`  (int64): How many quiet seconds are required before final closure.
* `max_open_interval` / `maxOpenInterval`  (int64): Hard-stop duration in seconds for unscheduled expectations.
* `criteria` / `criteria`  (object): Structured criteria v1 definition for the expectation.
* `last_evaluated_at` / `lastEvaluatedAt`  (date-time): Last time this expectation was evaluated.
* `last_success_at` / `lastSuccessAt`  (date-time): Last time this expectation closed successfully.
* `last_failure_at` / `lastFailureAt`  (date-time): Last time this expectation closed with a failure result.
* `last_result` / `lastResult`  (string): Most recent terminal result for this expectation.
* `created_at` / `createdAt`  (date-time): Creation time.
* `updated_at` / `updatedAt`  (date-time): Last update time.


---

## List Expectations

```
ListIterator<Expectation> expectation = Expectation.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `name` or `disabled`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `disabled` and `workspace_id`. Valid field combinations are `[ workspace_id, disabled ]`.


---

## Show Expectation

```
Expectation expectation = Expectation.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Expectation ID.


---

## Create Expectation

```
Expectation expectation = Expectation.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `name` (String): Expectation name.
* `description` (String): Expectation description.
* `path` (String): Path scope for the expectation. Supports workspace-relative presentation.
* `source` (String): Source glob used to select candidate files.
* `exclude_pattern` (String): Optional source exclusion glob.
* `disabled` (Boolean): If true, the expectation is disabled.
* `trigger` (String): How this expectation opens windows.
* `interval` (String): If trigger is `daily`, this specifies how often to run the expectation.
* `recurring_day` (Long): If trigger is `daily`, this selects the day number inside the chosen interval.
* `schedule_days_of_week` (Long[]): If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.
* `schedule_times_of_day` (String[]): Times of day in HH:MM format for schedule-driven expectations.
* `schedule_time_zone` (String): Time zone used by the expectation schedule.
* `holiday_region` (String): Optional holiday region used by schedule-driven expectations.
* `lookback_interval` (Long): How many seconds before the due boundary the window starts.
* `late_acceptance_interval` (Long): How many seconds a schedule-driven window may remain eligible to close as late.
* `inactivity_interval` (Long): How many quiet seconds are required before final closure.
* `max_open_interval` (Long): Hard-stop duration in seconds for unscheduled expectations.
* `criteria` (Object): Structured criteria v1 definition for the expectation.
* `workspace_id` (Long): Workspace ID. `0` means the default workspace.


---

## Manually open an Expectation window

```
ExpectationEvaluation expectation = Expectation.trigger(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Expectation ID.


---

## Update Expectation

```
Expectation expectation = Expectation.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Expectation ID.
* `name` (String): Expectation name.
* `description` (String): Expectation description.
* `path` (String): Path scope for the expectation. Supports workspace-relative presentation.
* `source` (String): Source glob used to select candidate files.
* `exclude_pattern` (String): Optional source exclusion glob.
* `disabled` (Boolean): If true, the expectation is disabled.
* `trigger` (String): How this expectation opens windows.
* `interval` (String): If trigger is `daily`, this specifies how often to run the expectation.
* `recurring_day` (Long): If trigger is `daily`, this selects the day number inside the chosen interval.
* `schedule_days_of_week` (Long[]): If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.
* `schedule_times_of_day` (String[]): Times of day in HH:MM format for schedule-driven expectations.
* `schedule_time_zone` (String): Time zone used by the expectation schedule.
* `holiday_region` (String): Optional holiday region used by schedule-driven expectations.
* `lookback_interval` (Long): How many seconds before the due boundary the window starts.
* `late_acceptance_interval` (Long): How many seconds a schedule-driven window may remain eligible to close as late.
* `inactivity_interval` (Long): How many quiet seconds are required before final closure.
* `max_open_interval` (Long): Hard-stop duration in seconds for unscheduled expectations.
* `criteria` (Object): Structured criteria v1 definition for the expectation.
* `workspace_id` (Long): Workspace ID. `0` means the default workspace.


---

## Delete Expectation

```
void expectation = Expectation.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Expectation ID.


---

## Manually open an Expectation window

```
Expectation expectation = Expectation.find(id);

HashMap<String, Object> parameters = new HashMap<>();

expectation.trigger(parameters);
```

### Parameters

* `id` (Long): Required - Expectation ID.


---

## Update Expectation

```
Expectation expectation = Expectation.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "Daily Vendor Feed");
parameters.put("description", "Wait for the vendor CSV every morning.");
parameters.put("path", "incoming/vendor_a");
parameters.put("source", "*.csv");
parameters.put("exclude_pattern", "*.tmp");
parameters.put("disabled", true);
parameters.put("trigger", "manual");
parameters.put("interval", "day");
parameters.put("recurring_day", 3);
parameters.put("schedule_days_of_week", [1,3,5]);
parameters.put("schedule_times_of_day", ["06:00"]);
parameters.put("schedule_time_zone", "UTC");
parameters.put("holiday_region", "us");
parameters.put("lookback_interval", 3600);
parameters.put("late_acceptance_interval", 900);
parameters.put("inactivity_interval", 300);
parameters.put("max_open_interval", 43200);
parameters.put("criteria", {"count":{"exact":1},"extensions":["csv"]});
parameters.put("workspace_id", 0);

expectation.update(parameters);
```

### Parameters

* `id` (Long): Required - Expectation ID.
* `name` (String): Expectation name.
* `description` (String): Expectation description.
* `path` (String): Path scope for the expectation. Supports workspace-relative presentation.
* `source` (String): Source glob used to select candidate files.
* `exclude_pattern` (String): Optional source exclusion glob.
* `disabled` (Boolean): If true, the expectation is disabled.
* `trigger` (String): How this expectation opens windows.
* `interval` (String): If trigger is `daily`, this specifies how often to run the expectation.
* `recurring_day` (Long): If trigger is `daily`, this selects the day number inside the chosen interval.
* `schedule_days_of_week` (Long[]): If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.
* `schedule_times_of_day` (String[]): Times of day in HH:MM format for schedule-driven expectations.
* `schedule_time_zone` (String): Time zone used by the expectation schedule.
* `holiday_region` (String): Optional holiday region used by schedule-driven expectations.
* `lookback_interval` (Long): How many seconds before the due boundary the window starts.
* `late_acceptance_interval` (Long): How many seconds a schedule-driven window may remain eligible to close as late.
* `inactivity_interval` (Long): How many quiet seconds are required before final closure.
* `max_open_interval` (Long): Hard-stop duration in seconds for unscheduled expectations.
* `criteria` (Object): Structured criteria v1 definition for the expectation.
* `workspace_id` (Long): Workspace ID. `0` means the default workspace.


---

## Delete Expectation

```
Expectation expectation = Expectation.find(id);

HashMap<String, Object> parameters = new HashMap<>();

expectation.delete(parameters);
```

### Parameters

* `id` (Long): Required - Expectation ID.
