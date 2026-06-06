# Files.Models.ScheduledExport

## Example ScheduledExport Object

```
{
  "id": 1,
  "name": "Monthly access review",
  "export_type": "permission_audit",
  "report_name": "Permission audit",
  "export_options": {
    "group_by": "user"
  },
  "user_id": 1,
  "disabled": true,
  "trigger": "daily",
  "interval": "month",
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
  "human_readable_schedule": "Runs every month at 06:30 AM UTC TZ.",
  "last_run_at": "2000-01-01T01:00:00Z",
  "last_export_id": 1,
  "created_at": "2000-01-01T01:00:00Z",
  "updated_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): Scheduled Export ID
* `name` / `name`  (string): Name for this scheduled export.
* `export_type` / `exportType`  (string): Export report type. Valid values: folder_size_audit, group_membership_audit, permission_audit, share_link_audit
* `report_name` / `reportName`  (string): Human-readable report name.
* `export_options` / `exportOptions`  (object): Report-specific options. `permission_audit` supports `group_by` with `user` or `path`.
* `user_id` / `userId`  (int64): Site Admin user who receives the completed export e-mail.
* `disabled` / `disabled`  (boolean): If true, this scheduled export will not run.
* `trigger` / `trigger`  (string): Schedule trigger type: `daily` or `custom_schedule`.
* `interval` / `interval`  (string): If trigger is `daily`, this specifies how often to run the scheduled export.
* `recurring_day` / `recurringDay`  (int64): If trigger is `daily`, this selects the day number inside the chosen interval.
* `schedule_days_of_week` / `scheduleDaysOfWeek`  (array(int64)): If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.
* `schedule_times_of_day` / `scheduleTimesOfDay`  (array(string)): Times of day in HH:MM format for schedule-driven exports.
* `schedule_time_zone` / `scheduleTimeZone`  (string): Time zone used by the scheduled export.
* `holiday_region` / `holidayRegion`  (string): Optional holiday region used by schedule-driven exports.
* `human_readable_schedule` / `humanReadableSchedule`  (string): Human-readable schedule description.
* `last_run_at` / `lastRunAt`  (date-time): Most recent scheduled run time.
* `last_export_id` / `lastExportId`  (int64): Most recent Export ID created by this schedule.
* `created_at` / `createdAt`  (date-time): Creation time.
* `updated_at` / `updatedAt`  (date-time): Last update time.


---

## List Scheduled Exports

```
ListIterator<ScheduledExport> scheduledExport = ScheduledExport.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `name`, `export_type` or `disabled`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `disabled` and `export_type`.
* `filter_prefix` (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `export_type`.


---

## Show Scheduled Export

```
ScheduledExport scheduledExport = ScheduledExport.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Scheduled Export ID.


---

## Create Scheduled Export

```
ScheduledExport scheduledExport = ScheduledExport.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `name` (String): Required - Name for this scheduled export.
* `export_type` (String): Required - Export report type. Valid values: folder_size_audit, group_membership_audit, permission_audit, share_link_audit
* `export_options` (Object): Report-specific options. `permission_audit` supports `group_by` with `user` or `path`.
* `user_id` (Long): Site Admin user who receives the completed export e-mail.
* `disabled` (Boolean): If true, this scheduled export will not run.
* `trigger` (String): Schedule trigger type: `daily` or `custom_schedule`.
* `interval` (String): If trigger is `daily`, this specifies how often to run the scheduled export.
* `recurring_day` (Long): If trigger is `daily`, this selects the day number inside the chosen interval.
* `schedule_days_of_week` (Long[]): If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.
* `schedule_times_of_day` (String[]): Times of day in HH:MM format for schedule-driven exports.
* `schedule_time_zone` (String): Time zone used by the scheduled export.
* `holiday_region` (String): Optional holiday region used by schedule-driven exports.


---

## Update Scheduled Export

```
ScheduledExport scheduledExport = ScheduledExport.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Scheduled Export ID.
* `name` (String): Name for this scheduled export.
* `export_type` (String): Export report type. Valid values: folder_size_audit, group_membership_audit, permission_audit, share_link_audit
* `export_options` (Object): Report-specific options. `permission_audit` supports `group_by` with `user` or `path`.
* `user_id` (Long): Site Admin user who receives the completed export e-mail.
* `disabled` (Boolean): If true, this scheduled export will not run.
* `trigger` (String): Schedule trigger type: `daily` or `custom_schedule`.
* `interval` (String): If trigger is `daily`, this specifies how often to run the scheduled export.
* `recurring_day` (Long): If trigger is `daily`, this selects the day number inside the chosen interval.
* `schedule_days_of_week` (Long[]): If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.
* `schedule_times_of_day` (String[]): Times of day in HH:MM format for schedule-driven exports.
* `schedule_time_zone` (String): Time zone used by the scheduled export.
* `holiday_region` (String): Optional holiday region used by schedule-driven exports.


---

## Delete Scheduled Export

```
void scheduledExport = ScheduledExport.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Scheduled Export ID.


---

## Update Scheduled Export

```
ScheduledExport scheduledExport = ScheduledExport.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "Monthly access review");
parameters.put("export_type", "permission_audit");
parameters.put("export_options", {"group_by":"user"});
parameters.put("user_id", 1);
parameters.put("disabled", true);
parameters.put("trigger", "daily");
parameters.put("interval", "month");
parameters.put("recurring_day", 1);
parameters.put("schedule_days_of_week", [1,3,5]);
parameters.put("schedule_times_of_day", ["06:30"]);
parameters.put("schedule_time_zone", "Eastern Time (US & Canada)");
parameters.put("holiday_region", "us");

scheduledExport.update(parameters);
```

### Parameters

* `id` (Long): Required - Scheduled Export ID.
* `name` (String): Name for this scheduled export.
* `export_type` (String): Export report type. Valid values: folder_size_audit, group_membership_audit, permission_audit, share_link_audit
* `export_options` (Object): Report-specific options. `permission_audit` supports `group_by` with `user` or `path`.
* `user_id` (Long): Site Admin user who receives the completed export e-mail.
* `disabled` (Boolean): If true, this scheduled export will not run.
* `trigger` (String): Schedule trigger type: `daily` or `custom_schedule`.
* `interval` (String): If trigger is `daily`, this specifies how often to run the scheduled export.
* `recurring_day` (Long): If trigger is `daily`, this selects the day number inside the chosen interval.
* `schedule_days_of_week` (Long[]): If trigger is `custom_schedule`, the 0-based weekdays used by the schedule.
* `schedule_times_of_day` (String[]): Times of day in HH:MM format for schedule-driven exports.
* `schedule_time_zone` (String): Time zone used by the scheduled export.
* `holiday_region` (String): Optional holiday region used by schedule-driven exports.


---

## Delete Scheduled Export

```
ScheduledExport scheduledExport = ScheduledExport.find(id);

HashMap<String, Object> parameters = new HashMap<>();

scheduledExport.delete(parameters);
```

### Parameters

* `id` (Long): Required - Scheduled Export ID.
