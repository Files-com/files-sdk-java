# Files.Models.Sync

## Example Sync Object

```
{
  "id": 1,
  "name": "example",
  "description": "example",
  "site_id": 1,
  "user_id": 1,
  "src_path": "example",
  "dest_path": "example",
  "src_remote_server_id": 1,
  "dest_remote_server_id": 1,
  "two_way": true,
  "keep_after_copy": true,
  "delete_empty_folders": true,
  "disabled": true,
  "trigger": "example",
  "trigger_file": "example",
  "include_patterns": [
    "example"
  ],
  "exclude_patterns": [
    "example"
  ],
  "created_at": "2000-01-01T01:00:00Z",
  "updated_at": "2000-01-01T01:00:00Z",
  "sync_interval_minutes": 1,
  "interval": "week",
  "recurring_day": 25,
  "schedule_days_of_week": [
    0,
    2,
    4
  ],
  "schedule_times_of_day": [
    "06:30",
    "14:30"
  ],
  "schedule_time_zone": "Eastern Time (US & Canada)",
  "holiday_region": "us_dc"
}
```

* `id` / `id`  (int64): Sync ID
* `name` / `name`  (string): Name for this sync job
* `description` / `description`  (string): Description for this sync job
* `site_id` / `siteId`  (int64): Site ID this sync belongs to
* `user_id` / `userId`  (int64): User who created or owns this sync
* `src_path` / `srcPath`  (string): Absolute source path for the sync
* `dest_path` / `destPath`  (string): Absolute destination path for the sync
* `src_remote_server_id` / `srcRemoteServerId`  (int64): Remote server ID for the source (if remote)
* `dest_remote_server_id` / `destRemoteServerId`  (int64): Remote server ID for the destination (if remote)
* `two_way` / `twoWay`  (boolean): Is this a two-way sync?
* `keep_after_copy` / `keepAfterCopy`  (boolean): Keep files after copying?
* `delete_empty_folders` / `deleteEmptyFolders`  (boolean): Delete empty folders after sync?
* `disabled` / `disabled`  (boolean): Is this sync disabled?
* `trigger` / `trigger`  (string): Trigger type: daily, custom_schedule, or manual
* `trigger_file` / `triggerFile`  (string): Some MFT services request an empty file (known as a trigger file) to signal the sync is complete and they can begin further processing. If trigger_file is set, a zero-byte file will be sent at the end of the sync.
* `include_patterns` / `includePatterns`  (array(string)): Array of glob patterns to include
* `exclude_patterns` / `excludePatterns`  (array(string)): Array of glob patterns to exclude
* `created_at` / `createdAt`  (date-time): When this sync was created
* `updated_at` / `updatedAt`  (date-time): When this sync was last updated
* `sync_interval_minutes` / `syncIntervalMinutes`  (int64): Frequency in minutes between syncs. If set, this value must be greater than or equal to the `remote_sync_interval` value for the site's plan. If left blank, the plan's `remote_sync_interval` will be used. This setting is only used if `trigger` is empty.
* `interval` / `interval`  (string): If trigger is `daily`, this specifies how often to run this sync.  One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
* `recurring_day` / `recurringDay`  (int64): If trigger type is `daily`, this specifies a day number to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.
* `schedule_days_of_week` / `scheduleDaysOfWeek`  (array(int64)): If trigger is `custom_schedule`, Custom schedule description for when the sync should be run. 0-based days of the week. 0 is Sunday, 1 is Monday, etc.
* `schedule_times_of_day` / `scheduleTimesOfDay`  (array(string)): If trigger is `custom_schedule`, Custom schedule description for when the sync should be run. Times of day in HH:MM format.
* `schedule_time_zone` / `scheduleTimeZone`  (string): If trigger is `custom_schedule`, Custom schedule Time Zone for when the sync should be run.
* `holiday_region` / `holidayRegion`  (string): If trigger is `custom_schedule`, the sync will check if there is a formal, observed holiday for the region, and if so, it will not run.


---

## List Syncs

```
ListIterator<Sync> sync = Sync.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).


---

## Show Sync

```
Sync sync = Sync.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Sync ID.


---

## Create Sync

```
Sync sync = Sync.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `name` (String): Name for this sync job
* `description` (String): Description for this sync job
* `src_path` (String): Absolute source path
* `dest_path` (String): Absolute destination path
* `src_remote_server_id` (Long): Remote server ID for the source
* `dest_remote_server_id` (Long): Remote server ID for the destination
* `two_way` (Boolean): Is this a two-way sync?
* `keep_after_copy` (Boolean): Keep files after copying?
* `delete_empty_folders` (Boolean): Delete empty folders after sync?
* `disabled` (Boolean): Is this sync disabled?
* `interval` (String): If trigger is `daily`, this specifies how often to run this sync.  One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
* `trigger` (String): Trigger type: daily, custom_schedule, or manual
* `trigger_file` (String): Some MFT services request an empty file (known as a trigger file) to signal the sync is complete and they can begin further processing. If trigger_file is set, a zero-byte file will be sent at the end of the sync.
* `holiday_region` (String): If trigger is `custom_schedule`, the sync will check if there is a formal, observed holiday for the region, and if so, it will not run.
* `sync_interval_minutes` (Long): Frequency in minutes between syncs. If set, this value must be greater than or equal to the `remote_sync_interval` value for the site's plan. If left blank, the plan's `remote_sync_interval` will be used. This setting is only used if `trigger` is empty.
* `recurring_day` (Long): If trigger type is `daily`, this specifies a day number to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.
* `schedule_time_zone` (String): If trigger is `custom_schedule`, Custom schedule Time Zone for when the sync should be run.
* `schedule_days_of_week` (Long[]): If trigger is `custom_schedule`, Custom schedule description for when the sync should be run. 0-based days of the week. 0 is Sunday, 1 is Monday, etc.
* `schedule_times_of_day` (String[]): If trigger is `custom_schedule`, Custom schedule description for when the sync should be run. Times of day in HH:MM format.


---

## Manually Run Sync

```
void sync = Sync.manualRun(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Sync ID.


---

## Update Sync

```
Sync sync = Sync.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Sync ID.
* `name` (String): Name for this sync job
* `description` (String): Description for this sync job
* `src_path` (String): Absolute source path
* `dest_path` (String): Absolute destination path
* `src_remote_server_id` (Long): Remote server ID for the source
* `dest_remote_server_id` (Long): Remote server ID for the destination
* `two_way` (Boolean): Is this a two-way sync?
* `keep_after_copy` (Boolean): Keep files after copying?
* `delete_empty_folders` (Boolean): Delete empty folders after sync?
* `disabled` (Boolean): Is this sync disabled?
* `interval` (String): If trigger is `daily`, this specifies how often to run this sync.  One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
* `trigger` (String): Trigger type: daily, custom_schedule, or manual
* `trigger_file` (String): Some MFT services request an empty file (known as a trigger file) to signal the sync is complete and they can begin further processing. If trigger_file is set, a zero-byte file will be sent at the end of the sync.
* `holiday_region` (String): If trigger is `custom_schedule`, the sync will check if there is a formal, observed holiday for the region, and if so, it will not run.
* `sync_interval_minutes` (Long): Frequency in minutes between syncs. If set, this value must be greater than or equal to the `remote_sync_interval` value for the site's plan. If left blank, the plan's `remote_sync_interval` will be used. This setting is only used if `trigger` is empty.
* `recurring_day` (Long): If trigger type is `daily`, this specifies a day number to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.
* `schedule_time_zone` (String): If trigger is `custom_schedule`, Custom schedule Time Zone for when the sync should be run.
* `schedule_days_of_week` (Long[]): If trigger is `custom_schedule`, Custom schedule description for when the sync should be run. 0-based days of the week. 0 is Sunday, 1 is Monday, etc.
* `schedule_times_of_day` (String[]): If trigger is `custom_schedule`, Custom schedule description for when the sync should be run. Times of day in HH:MM format.


---

## Delete Sync

```
void sync = Sync.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Sync ID.


---

## Manually Run Sync

```
Sync sync = Sync.find(id);

HashMap<String, Object> parameters = new HashMap<>();

sync.manualRun(parameters);
```

### Parameters

* `id` (Long): Required - Sync ID.


---

## Update Sync

```
Sync sync = Sync.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "example");
parameters.put("description", "example");
parameters.put("src_path", "example");
parameters.put("dest_path", "example");
parameters.put("src_remote_server_id", 1);
parameters.put("dest_remote_server_id", 1);
parameters.put("two_way", false);
parameters.put("keep_after_copy", false);
parameters.put("delete_empty_folders", false);
parameters.put("disabled", false);
parameters.put("interval", "week");
parameters.put("trigger", "example");
parameters.put("trigger_file", "example");
parameters.put("holiday_region", "us_dc");
parameters.put("sync_interval_minutes", 1);
parameters.put("recurring_day", 25);
parameters.put("schedule_time_zone", "Eastern Time (US & Canada)");
parameters.put("schedule_days_of_week", [0,2,4]);
parameters.put("schedule_times_of_day", ["06:30","14:30"]);

sync.update(parameters);
```

### Parameters

* `id` (Long): Required - Sync ID.
* `name` (String): Name for this sync job
* `description` (String): Description for this sync job
* `src_path` (String): Absolute source path
* `dest_path` (String): Absolute destination path
* `src_remote_server_id` (Long): Remote server ID for the source
* `dest_remote_server_id` (Long): Remote server ID for the destination
* `two_way` (Boolean): Is this a two-way sync?
* `keep_after_copy` (Boolean): Keep files after copying?
* `delete_empty_folders` (Boolean): Delete empty folders after sync?
* `disabled` (Boolean): Is this sync disabled?
* `interval` (String): If trigger is `daily`, this specifies how often to run this sync.  One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
* `trigger` (String): Trigger type: daily, custom_schedule, or manual
* `trigger_file` (String): Some MFT services request an empty file (known as a trigger file) to signal the sync is complete and they can begin further processing. If trigger_file is set, a zero-byte file will be sent at the end of the sync.
* `holiday_region` (String): If trigger is `custom_schedule`, the sync will check if there is a formal, observed holiday for the region, and if so, it will not run.
* `sync_interval_minutes` (Long): Frequency in minutes between syncs. If set, this value must be greater than or equal to the `remote_sync_interval` value for the site's plan. If left blank, the plan's `remote_sync_interval` will be used. This setting is only used if `trigger` is empty.
* `recurring_day` (Long): If trigger type is `daily`, this specifies a day number to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.
* `schedule_time_zone` (String): If trigger is `custom_schedule`, Custom schedule Time Zone for when the sync should be run.
* `schedule_days_of_week` (Long[]): If trigger is `custom_schedule`, Custom schedule description for when the sync should be run. 0-based days of the week. 0 is Sunday, 1 is Monday, etc.
* `schedule_times_of_day` (String[]): If trigger is `custom_schedule`, Custom schedule description for when the sync should be run. Times of day in HH:MM format.


---

## Delete Sync

```
Sync sync = Sync.find(id);

HashMap<String, Object> parameters = new HashMap<>();

sync.delete(parameters);
```

### Parameters

* `id` (Long): Required - Sync ID.
