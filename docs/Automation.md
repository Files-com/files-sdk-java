# Files.Models.Automation

## Example Automation Object

```
{
  "id": 1,
  "always_serialize_jobs": true,
  "always_overwrite_size_matching_files": true,
  "automation": "create_folder",
  "deleted": true,
  "description": "example",
  "destination_replace_from": "example",
  "destination_replace_to": "example",
  "destinations": [
    "destination"
  ],
  "disabled": true,
  "exclude_pattern": "path/to/exclude/*",
  "import_urls": [
    {
      "name": "users.json",
      "url": "http://example.com/users",
      "method": "POST",
      "headers": {
        "Content-Type": "application/json"
      },
      "content": {
        "group": "support"
      }
    }
  ],
  "flatten_destination_structure": true,
  "group_ids": [
    1,
    2
  ],
  "ignore_locked_folders": true,
  "interval": "week",
  "last_modified_at": "2000-01-01T01:00:00Z",
  "legacy_folder_matching": true,
  "name": "example",
  "overwrite_files": true,
  "path": "example",
  "path_time_zone": "Eastern Time (US & Canada)",
  "recurring_day": 25,
  "retry_on_failure_interval_in_minutes": 60,
  "retry_on_failure_number_of_attempts": 10,
  "schedule": {
    "days_of_week": [
      0,
      2,
      4
    ],
    "times_of_day": [
      "06:30",
      "14:30"
    ],
    "time_zone": "Eastern Time (US & Canada)"
  },
  "human_readable_schedule": "Triggered every Monday, Wednesday at 6:30 AM,\n  2:30 PM Eastern Time (US & Canada) TZ",
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
  "source": "example",
  "legacy_sync_ids": [
    1,
    2
  ],
  "sync_ids": [
    1,
    2
  ],
  "trigger_actions": [
    "create"
  ],
  "trigger": "daily",
  "user_id": 1,
  "user_ids": [
    1,
    2
  ],
  "value": {
    "limit": "1"
  },
  "webhook_url": "https://app.files.com/api/webhooks/abc123",
  "holiday_region": "us_dc"
}
```

* `id` / `id`  (int64): Automation ID
* `always_serialize_jobs` / `alwaysSerializeJobs`  (boolean): Ordinarily, we will allow automation runs to run in parallel for non-scheduled automations. If this flag is `true` we will force automation runs to be serialized (run one at a time, one after another). This can resolve some issues with race conditions on remote systems at the cost of some performance.
* `always_overwrite_size_matching_files` / `alwaysOverwriteSizeMatchingFiles`  (boolean): Ordinarily, files with identical size in the source and destination will be skipped from copy operations to prevent wasted transfer.  If this flag is `true` we will overwrite the destination file always.  Note that this may cause large amounts of wasted transfer usage.  This setting has no effect unless `overwrite_files` is also set to `true`.
* `automation` / `automation`  (string): Automation type
* `deleted` / `deleted`  (boolean): Indicates if the automation has been deleted.
* `description` / `description`  (string): Description for the this Automation.
* `destination_replace_from` / `destinationReplaceFrom`  (string): If set, this string in the destination path will be replaced with the value in `destination_replace_to`.
* `destination_replace_to` / `destinationReplaceTo`  (string): If set, this string will replace the value `destination_replace_from` in the destination filename. You can use special patterns here.
* `destinations` / `destinations`  (array(string)): Destination Paths
* `disabled` / `disabled`  (boolean): If true, this automation will not run.
* `exclude_pattern` / `excludePattern`  (string): If set, this glob pattern will exclude files from the automation. Supports globs, except on remote mounts.
* `import_urls` / `importUrls`  (array(object)): List of URLs to be imported and names to be used.
* `flatten_destination_structure` / `flattenDestinationStructure`  (boolean): Normally copy and move automations that use globs will implicitly preserve the source folder structure in the destination.  If this flag is `true`, the source folder structure will be flattened in the destination.  This is useful for copying or moving files from multiple folders into a single destination folder.
* `group_ids` / `groupIds`  (array(int64)): IDs of Groups for the Automation (i.e. who to Request File from)
* `ignore_locked_folders` / `ignoreLockedFolders`  (boolean): If true, the Lock Folders behavior will be disregarded for automated actions.
* `interval` / `interval`  (string): If trigger is `daily`, this specifies how often to run this automation.  One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
* `last_modified_at` / `lastModifiedAt`  (date-time): Time when automation was last modified. Does not change for name or description updates.
* `legacy_folder_matching` / `legacyFolderMatching`  (boolean): If `true`, use the legacy behavior for this automation, where it can operate on folders in addition to just files.  This behavior no longer works and should not be used.
* `name` / `name`  (string): Name for this automation.
* `overwrite_files` / `overwriteFiles`  (boolean): If true, existing files will be overwritten with new files on Move/Copy automations.  Note: by default files will not be overwritten on Copy automations if they appear to be the same file size as the newly incoming file.  Use the `always_overwrite_size_matching_files` option in conjunction with `overwrite_files` to override this behavior and overwrite files no matter what.
* `path` / `path`  (string): Path on which this Automation runs.  Supports globs, except on remote mounts. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `path_time_zone` / `pathTimeZone`  (string): Timezone to use when rendering timestamps in paths.
* `recurring_day` / `recurringDay`  (int64): If trigger type is `daily`, this specifies a day number to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.
* `retry_on_failure_interval_in_minutes` / `retryOnFailureIntervalInMinutes`  (int64): If the Automation fails, retry at this interval (in minutes).  Acceptable values are 5 through 1440 (one day).  Set to null to disable.
* `retry_on_failure_number_of_attempts` / `retryOnFailureNumberOfAttempts`  (int64): If the Automation fails, retry at most this many times.  Maximum allowed value: 10.  Set to null to disable.
* `schedule` / `schedule`  (object): If trigger is `custom_schedule`, Custom schedule description for when the automation should be run in json format.
* `human_readable_schedule` / `humanReadableSchedule`  (string): If trigger is `custom_schedule`, Human readable Custom schedule description for when the automation should be run.
* `schedule_days_of_week` / `scheduleDaysOfWeek`  (array(int64)): If trigger is `custom_schedule`, Custom schedule description for when the automation should be run. 0-based days of the week. 0 is Sunday, 1 is Monday, etc.
* `schedule_times_of_day` / `scheduleTimesOfDay`  (array(string)): If trigger is `custom_schedule`, Custom schedule description for when the automation should be run. Times of day in HH:MM format.
* `schedule_time_zone` / `scheduleTimeZone`  (string): If trigger is `custom_schedule`, Custom schedule Time Zone for when the automation should be run.
* `source` / `source`  (string): Source path/glob.  See Automation docs for exact description, but this is used to filter for files in the `path` to find files to operate on. Supports globs, except on remote mounts.
* `legacy_sync_ids` / `legacySyncIds`  (array(int64)): IDs of remote sync folder behaviors to run by this Automation
* `sync_ids` / `syncIds`  (array(int64)): IDs of syncs to run by this Automation. This is the new way to specify syncs, and it is recommended to use this instead of `legacy_sync_ids`.
* `trigger_actions` / `triggerActions`  (array(string)): If trigger is `action`, this is the list of action types on which to trigger the automation. Valid actions are create, read, update, destroy, move, copy
* `trigger` / `trigger`  (string): How this automation is triggered to run.
* `user_id` / `userId`  (int64): User ID of the Automation's creator.
* `user_ids` / `userIds`  (array(int64)): IDs of Users for the Automation (i.e. who to Request File from)
* `value` / `value`  (object): A Hash of attributes specific to the automation type.
* `webhook_url` / `webhookUrl`  (string): If trigger is `webhook`, this is the URL of the webhook to trigger the Automation.
* `holiday_region` / `holidayRegion`  (string): If trigger is `custom_schedule`, the Automation will check if there is a formal, observed holiday for the region, and if so, it will not run.


---

## List Automations

```
ListIterator<Automation> automation = Automation.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `automation`, `disabled`, `last_modified_at` or `name`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `disabled`, `last_modified_at` or `automation`. Valid field combinations are `[ disabled, last_modified_at ]`, `[ automation, disabled ]`, `[ automation, last_modified_at ]` or `[ automation, disabled, last_modified_at ]`.
* `filter_gt` (Map<String, String>): If set, return records where the specified field is greater than the supplied value. Valid fields are `last_modified_at`.
* `filter_gteq` (Map<String, String>): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `last_modified_at`.
* `filter_lt` (Map<String, String>): If set, return records where the specified field is less than the supplied value. Valid fields are `last_modified_at`.
* `filter_lteq` (Map<String, String>): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `last_modified_at`.


---

## Show Automation

```
Automation automation = Automation.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Automation ID.


---

## Create Automation

```
Automation automation = Automation.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `source` (String): Source path/glob.  See Automation docs for exact description, but this is used to filter for files in the `path` to find files to operate on. Supports globs, except on remote mounts.
* `destinations` (String[]): A list of String destination paths or Hash of folder_path and optional file_path.
* `destination_replace_from` (String): If set, this string in the destination path will be replaced with the value in `destination_replace_to`.
* `destination_replace_to` (String): If set, this string will replace the value `destination_replace_from` in the destination filename. You can use special patterns here.
* `interval` (String): How often to run this automation? One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
* `path` (String): Path on which this Automation runs.  Supports globs, except on remote mounts.
* `legacy_sync_ids` (String): A list of legacy sync IDs the automation is associated with. If sent as a string, it should be comma-delimited.
* `sync_ids` (String): A list of sync IDs the automation is associated with. If sent as a string, it should be comma-delimited.
* `user_ids` (String): A list of user IDs the automation is associated with. If sent as a string, it should be comma-delimited.
* `group_ids` (String): A list of group IDs the automation is associated with. If sent as a string, it should be comma-delimited.
* `schedule_days_of_week` (Long[]): If trigger is `custom_schedule`. A list of days of the week to run this automation. 0 is Sunday, 1 is Monday, etc.
* `schedule_times_of_day` (String[]): If trigger is `custom_schedule`. A list of times of day to run this automation. 24-hour time format.
* `schedule_time_zone` (String): If trigger is `custom_schedule`. Time zone for the schedule.
* `holiday_region` (String): If trigger is `custom_schedule`, the Automation will check if there is a formal, observed holiday for the region, and if so, it will not run.
* `always_overwrite_size_matching_files` (Boolean): Ordinarily, files with identical size in the source and destination will be skipped from copy operations to prevent wasted transfer.  If this flag is `true` we will overwrite the destination file always.  Note that this may cause large amounts of wasted transfer usage.  This setting has no effect unless `overwrite_files` is also set to `true`.
* `always_serialize_jobs` (Boolean): Ordinarily, we will allow automation runs to run in parallel for non-scheduled automations. If this flag is `true` we will force automation runs to be serialized (run one at a time, one after another). This can resolve some issues with race conditions on remote systems at the cost of some performance.
* `description` (String): Description for the this Automation.
* `disabled` (Boolean): If true, this automation will not run.
* `exclude_pattern` (String): If set, this glob pattern will exclude files from the automation. Supports globs, except on remote mounts.
* `import_urls` (Object[]): List of URLs to be imported and names to be used.
* `flatten_destination_structure` (Boolean): Normally copy and move automations that use globs will implicitly preserve the source folder structure in the destination.  If this flag is `true`, the source folder structure will be flattened in the destination.  This is useful for copying or moving files from multiple folders into a single destination folder.
* `ignore_locked_folders` (Boolean): If true, the Lock Folders behavior will be disregarded for automated actions.
* `legacy_folder_matching` (Boolean): DEPRECATED: If `true`, use the legacy behavior for this automation, where it can operate on folders in addition to just files.  This behavior no longer works and should not be used.
* `name` (String): Name for this automation.
* `overwrite_files` (Boolean): If true, existing files will be overwritten with new files on Move/Copy automations.  Note: by default files will not be overwritten on Copy automations if they appear to be the same file size as the newly incoming file.  Use the `always_overwrite_size_matching_files` option in conjunction with `overwrite_files` to override this behavior and overwrite files no matter what.
* `path_time_zone` (String): Timezone to use when rendering timestamps in paths.
* `retry_on_failure_interval_in_minutes` (Long): If the Automation fails, retry at this interval (in minutes).  Acceptable values are 5 through 1440 (one day).  Set to null to disable.
* `retry_on_failure_number_of_attempts` (Long): If the Automation fails, retry at most this many times.  Maximum allowed value: 10.  Set to null to disable.
* `trigger` (String): How this automation is triggered to run.
* `trigger_actions` (String[]): If trigger is `action`, this is the list of action types on which to trigger the automation. Valid actions are create, read, update, destroy, move, copy
* `value` (Map<String, String>): A Hash of attributes specific to the automation type.
* `recurring_day` (Long): If trigger type is `daily`, this specifies a day number to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.
* `automation` (String): Required - Automation type


---

## Manually Run Automation

```
void automation = Automation.manualRun(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Automation ID.


---

## Update Automation

```
Automation automation = Automation.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Automation ID.
* `source` (String): Source path/glob.  See Automation docs for exact description, but this is used to filter for files in the `path` to find files to operate on. Supports globs, except on remote mounts.
* `destinations` (String[]): A list of String destination paths or Hash of folder_path and optional file_path.
* `destination_replace_from` (String): If set, this string in the destination path will be replaced with the value in `destination_replace_to`.
* `destination_replace_to` (String): If set, this string will replace the value `destination_replace_from` in the destination filename. You can use special patterns here.
* `interval` (String): How often to run this automation? One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
* `path` (String): Path on which this Automation runs.  Supports globs, except on remote mounts.
* `legacy_sync_ids` (String): A list of legacy sync IDs the automation is associated with. If sent as a string, it should be comma-delimited.
* `sync_ids` (String): A list of sync IDs the automation is associated with. If sent as a string, it should be comma-delimited.
* `user_ids` (String): A list of user IDs the automation is associated with. If sent as a string, it should be comma-delimited.
* `group_ids` (String): A list of group IDs the automation is associated with. If sent as a string, it should be comma-delimited.
* `schedule_days_of_week` (Long[]): If trigger is `custom_schedule`. A list of days of the week to run this automation. 0 is Sunday, 1 is Monday, etc.
* `schedule_times_of_day` (String[]): If trigger is `custom_schedule`. A list of times of day to run this automation. 24-hour time format.
* `schedule_time_zone` (String): If trigger is `custom_schedule`. Time zone for the schedule.
* `holiday_region` (String): If trigger is `custom_schedule`, the Automation will check if there is a formal, observed holiday for the region, and if so, it will not run.
* `always_overwrite_size_matching_files` (Boolean): Ordinarily, files with identical size in the source and destination will be skipped from copy operations to prevent wasted transfer.  If this flag is `true` we will overwrite the destination file always.  Note that this may cause large amounts of wasted transfer usage.  This setting has no effect unless `overwrite_files` is also set to `true`.
* `always_serialize_jobs` (Boolean): Ordinarily, we will allow automation runs to run in parallel for non-scheduled automations. If this flag is `true` we will force automation runs to be serialized (run one at a time, one after another). This can resolve some issues with race conditions on remote systems at the cost of some performance.
* `description` (String): Description for the this Automation.
* `disabled` (Boolean): If true, this automation will not run.
* `exclude_pattern` (String): If set, this glob pattern will exclude files from the automation. Supports globs, except on remote mounts.
* `import_urls` (Object[]): List of URLs to be imported and names to be used.
* `flatten_destination_structure` (Boolean): Normally copy and move automations that use globs will implicitly preserve the source folder structure in the destination.  If this flag is `true`, the source folder structure will be flattened in the destination.  This is useful for copying or moving files from multiple folders into a single destination folder.
* `ignore_locked_folders` (Boolean): If true, the Lock Folders behavior will be disregarded for automated actions.
* `legacy_folder_matching` (Boolean): DEPRECATED: If `true`, use the legacy behavior for this automation, where it can operate on folders in addition to just files.  This behavior no longer works and should not be used.
* `name` (String): Name for this automation.
* `overwrite_files` (Boolean): If true, existing files will be overwritten with new files on Move/Copy automations.  Note: by default files will not be overwritten on Copy automations if they appear to be the same file size as the newly incoming file.  Use the `always_overwrite_size_matching_files` option in conjunction with `overwrite_files` to override this behavior and overwrite files no matter what.
* `path_time_zone` (String): Timezone to use when rendering timestamps in paths.
* `retry_on_failure_interval_in_minutes` (Long): If the Automation fails, retry at this interval (in minutes).  Acceptable values are 5 through 1440 (one day).  Set to null to disable.
* `retry_on_failure_number_of_attempts` (Long): If the Automation fails, retry at most this many times.  Maximum allowed value: 10.  Set to null to disable.
* `trigger` (String): How this automation is triggered to run.
* `trigger_actions` (String[]): If trigger is `action`, this is the list of action types on which to trigger the automation. Valid actions are create, read, update, destroy, move, copy
* `value` (Map<String, String>): A Hash of attributes specific to the automation type.
* `recurring_day` (Long): If trigger type is `daily`, this specifies a day number to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.
* `automation` (String): Automation type


---

## Delete Automation

```
void automation = Automation.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Automation ID.


---

## Manually Run Automation

```
Automation automation = Automation.find(id);

HashMap<String, Object> parameters = new HashMap<>();

automation.manualRun(parameters);
```

### Parameters

* `id` (Long): Required - Automation ID.


---

## Update Automation

```
Automation automation = Automation.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("source", "example");
parameters.put("destinations", ["folder_a/file_a.txt",{"folder_path":"folder_b","file_path":"file_b.txt"},{"folder_path":"folder_c"}]);
parameters.put("destination_replace_from", "example");
parameters.put("destination_replace_to", "example");
parameters.put("interval", "year");
parameters.put("path", "example");
parameters.put("legacy_sync_ids", [1,2]);
parameters.put("sync_ids", [1,2]);
parameters.put("user_ids", [1,2]);
parameters.put("group_ids", [1,2]);
parameters.put("schedule_days_of_week", [0,1,3]);
parameters.put("schedule_times_of_day", ["7:30","11:30"]);
parameters.put("schedule_time_zone", "Eastern Time (US & Canada)");
parameters.put("holiday_region", "us_dc");
parameters.put("always_overwrite_size_matching_files", true);
parameters.put("always_serialize_jobs", true);
parameters.put("description", "example");
parameters.put("disabled", true);
parameters.put("exclude_pattern", "path/to/exclude/*");
parameters.put("import_urls", [{"name":"users.json","url":"http://example.com/users","method":"POST","headers":{"Content-Type":"application/json"},"content":{"group":"support"}}]);
parameters.put("flatten_destination_structure", true);
parameters.put("ignore_locked_folders", true);
parameters.put("legacy_folder_matching", false);
parameters.put("name", "example");
parameters.put("overwrite_files", true);
parameters.put("path_time_zone", "Eastern Time (US & Canada)");
parameters.put("retry_on_failure_interval_in_minutes", 60);
parameters.put("retry_on_failure_number_of_attempts", 10);
parameters.put("trigger", "daily");
parameters.put("trigger_actions", ["create"]);
parameters.put("value", {"limit":"1"});
parameters.put("recurring_day", 25);
parameters.put("automation", "create_folder");

automation.update(parameters);
```

### Parameters

* `id` (Long): Required - Automation ID.
* `source` (String): Source path/glob.  See Automation docs for exact description, but this is used to filter for files in the `path` to find files to operate on. Supports globs, except on remote mounts.
* `destinations` (String[]): A list of String destination paths or Hash of folder_path and optional file_path.
* `destination_replace_from` (String): If set, this string in the destination path will be replaced with the value in `destination_replace_to`.
* `destination_replace_to` (String): If set, this string will replace the value `destination_replace_from` in the destination filename. You can use special patterns here.
* `interval` (String): How often to run this automation? One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
* `path` (String): Path on which this Automation runs.  Supports globs, except on remote mounts.
* `legacy_sync_ids` (String): A list of legacy sync IDs the automation is associated with. If sent as a string, it should be comma-delimited.
* `sync_ids` (String): A list of sync IDs the automation is associated with. If sent as a string, it should be comma-delimited.
* `user_ids` (String): A list of user IDs the automation is associated with. If sent as a string, it should be comma-delimited.
* `group_ids` (String): A list of group IDs the automation is associated with. If sent as a string, it should be comma-delimited.
* `schedule_days_of_week` (Long[]): If trigger is `custom_schedule`. A list of days of the week to run this automation. 0 is Sunday, 1 is Monday, etc.
* `schedule_times_of_day` (String[]): If trigger is `custom_schedule`. A list of times of day to run this automation. 24-hour time format.
* `schedule_time_zone` (String): If trigger is `custom_schedule`. Time zone for the schedule.
* `holiday_region` (String): If trigger is `custom_schedule`, the Automation will check if there is a formal, observed holiday for the region, and if so, it will not run.
* `always_overwrite_size_matching_files` (Boolean): Ordinarily, files with identical size in the source and destination will be skipped from copy operations to prevent wasted transfer.  If this flag is `true` we will overwrite the destination file always.  Note that this may cause large amounts of wasted transfer usage.  This setting has no effect unless `overwrite_files` is also set to `true`.
* `always_serialize_jobs` (Boolean): Ordinarily, we will allow automation runs to run in parallel for non-scheduled automations. If this flag is `true` we will force automation runs to be serialized (run one at a time, one after another). This can resolve some issues with race conditions on remote systems at the cost of some performance.
* `description` (String): Description for the this Automation.
* `disabled` (Boolean): If true, this automation will not run.
* `exclude_pattern` (String): If set, this glob pattern will exclude files from the automation. Supports globs, except on remote mounts.
* `import_urls` (Object[]): List of URLs to be imported and names to be used.
* `flatten_destination_structure` (Boolean): Normally copy and move automations that use globs will implicitly preserve the source folder structure in the destination.  If this flag is `true`, the source folder structure will be flattened in the destination.  This is useful for copying or moving files from multiple folders into a single destination folder.
* `ignore_locked_folders` (Boolean): If true, the Lock Folders behavior will be disregarded for automated actions.
* `legacy_folder_matching` (Boolean): DEPRECATED: If `true`, use the legacy behavior for this automation, where it can operate on folders in addition to just files.  This behavior no longer works and should not be used.
* `name` (String): Name for this automation.
* `overwrite_files` (Boolean): If true, existing files will be overwritten with new files on Move/Copy automations.  Note: by default files will not be overwritten on Copy automations if they appear to be the same file size as the newly incoming file.  Use the `always_overwrite_size_matching_files` option in conjunction with `overwrite_files` to override this behavior and overwrite files no matter what.
* `path_time_zone` (String): Timezone to use when rendering timestamps in paths.
* `retry_on_failure_interval_in_minutes` (Long): If the Automation fails, retry at this interval (in minutes).  Acceptable values are 5 through 1440 (one day).  Set to null to disable.
* `retry_on_failure_number_of_attempts` (Long): If the Automation fails, retry at most this many times.  Maximum allowed value: 10.  Set to null to disable.
* `trigger` (String): How this automation is triggered to run.
* `trigger_actions` (String[]): If trigger is `action`, this is the list of action types on which to trigger the automation. Valid actions are create, read, update, destroy, move, copy
* `value` (Map<String, String>): A Hash of attributes specific to the automation type.
* `recurring_day` (Long): If trigger type is `daily`, this specifies a day number to run in one of the supported intervals: `week`, `month`, `quarter`, `year`.
* `automation` (String): Automation type


---

## Delete Automation

```
Automation automation = Automation.find(id);

HashMap<String, Object> parameters = new HashMap<>();

automation.delete(parameters);
```

### Parameters

* `id` (Long): Required - Automation ID.
