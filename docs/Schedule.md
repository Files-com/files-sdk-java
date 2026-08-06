# Files.Models.Schedule

## Example Schedule Object

```
{
  "id": 1,
  "name": "Weekday overnight",
  "schedule_days_of_week": [
    1,
    2,
    3,
    4,
    5
  ],
  "schedule_times_of_day": [
    "01:00"
  ],
  "schedule_time_zone": "Eastern Time (US & Canada)",
  "holiday_region": "us",
  "human_readable_schedule": "Triggered every Monday, Tuesday, Wednesday, Thursday, Friday at 01:00 AM UTC TZ.",
  "created_at": "2000-01-01T01:00:00Z",
  "updated_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): Schedule ID.
* `name` / `name`  (string): Schedule name.
* `schedule_days_of_week` / `scheduleDaysOfWeek`  (array(int64)): 0-based weekdays used by the Schedule. 0 is Sunday.
* `schedule_times_of_day` / `scheduleTimesOfDay`  (array(string)): Times of day in HH:MM format (24-hour).
* `schedule_time_zone` / `scheduleTimeZone`  (string): Time zone for scheduled times. If not set, times are interpreted as UTC.
* `holiday_region` / `holidayRegion`  (string): Optional holiday region on which linked resources do not run.
* `human_readable_schedule` / `humanReadableSchedule`  (string): Human-readable Schedule description.
* `created_at` / `createdAt`  (date-time): Creation time.
* `updated_at` / `updatedAt`  (date-time): Last update time.


---

## List Schedules

```
ListIterator<Schedule> schedule = Schedule.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `name`.


---

## Show Schedule

```
Schedule schedule = Schedule.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Schedule ID.


---

## Create Schedule

```
Schedule schedule = Schedule.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `name` (String): Required - Schedule name.
* `schedule_days_of_week` (Long[]): Required - 0-based weekdays used by the Schedule. 0 is Sunday.
* `schedule_times_of_day` (String[]): Required - Times of day in HH:MM format (24-hour).
* `schedule_time_zone` (String): Time zone for scheduled times. If not set, times are interpreted as UTC.
* `holiday_region` (String): Optional holiday region on which linked resources do not run.


---

## Update Schedule

```
Schedule schedule = Schedule.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Schedule ID.
* `name` (String): Schedule name.
* `schedule_days_of_week` (Long[]): 0-based weekdays used by the Schedule. 0 is Sunday.
* `schedule_times_of_day` (String[]): Times of day in HH:MM format (24-hour).
* `schedule_time_zone` (String): Time zone for scheduled times. If not set, times are interpreted as UTC.
* `holiday_region` (String): Optional holiday region on which linked resources do not run.


---

## Delete Schedule

```
void schedule = Schedule.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Schedule ID.


---

## Update Schedule

```
Schedule schedule = Schedule.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "Weekday overnight");
parameters.put("schedule_days_of_week", [1,2,3,4,5]);
parameters.put("schedule_times_of_day", ["01:00"]);
parameters.put("schedule_time_zone", "Eastern Time (US & Canada)");
parameters.put("holiday_region", "us");

schedule.update(parameters);
```

### Parameters

* `id` (Long): Required - Schedule ID.
* `name` (String): Schedule name.
* `schedule_days_of_week` (Long[]): 0-based weekdays used by the Schedule. 0 is Sunday.
* `schedule_times_of_day` (String[]): Times of day in HH:MM format (24-hour).
* `schedule_time_zone` (String): Time zone for scheduled times. If not set, times are interpreted as UTC.
* `holiday_region` (String): Optional holiday region on which linked resources do not run.


---

## Delete Schedule

```
Schedule schedule = Schedule.find(id);

HashMap<String, Object> parameters = new HashMap<>();

schedule.delete(parameters);
```

### Parameters

* `id` (Long): Required - Schedule ID.
