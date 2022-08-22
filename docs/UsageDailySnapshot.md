# Files.Models.UsageDailySnapshot

## Example UsageDailySnapshot Object

```
{
  "id": 1,
  "date": "2020-11-21",
  "api_usage_available": true,
  "read_api_usage": "100",
  "write_api_usage": "50",
  "user_count": "25",
  "current_storage": "65536",
  "deleted_files_storage": "65536",
  "deleted_files_counted_in_minimum": "65536",
  "root_storage": "65536",
  "usage_by_top_level_dir": ""
}
```

* `id` / `id`  (int64): ID of the usage record
* `date` / `date`  (date): The date of this usage record
* `api_usage_available` / `apiUsageAvailable`  (boolean): True if the API usage fields `read_api_usage` and `write_api_usage` can be relied upon.  If this is false, we suggest hiding that value from any UI.
* `read_api_usage` / `readApiUsage`  (int64): Read API Calls used on this day. Note: only updated for days before the current day.
* `write_api_usage` / `writeApiUsage`  (int64): Write API Calls used on this day. Note: only updated for days before the current day.
* `user_count` / `userCount`  (int64): Number of billable users as of this day.
* `current_storage` / `currentStorage`  (int64): GB of Files Native Storage used on this day.
* `deleted_files_storage` / `deletedFilesStorage`  (int64): GB of Files Native Storage used on this day for files that have been deleted and are stored as backups.
* `deleted_files_counted_in_minimum` / `deletedFilesCountedInMinimum`  (int64): GB of Files Native Storage used on this day for files that have been permanently deleted but were uploaded less than 30 days ago, and are still billable.
* `root_storage` / `rootStorage`  (int64): GB of Files Native Storage used for the root folder.  Included here because this value will not be part of `usage_by_top_level_dir`
* `usage_by_top_level_dir` / `usageByTopLevelDir`  (object): Usage broken down by each top-level folder


---

## List Usage Daily Snapshots

```
List<UsageDailySnapshot> usageDailySnapshot = UsageDailySnapshot.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via either the X-Files-Cursor-Next header or the X-Files-Cursor-Prev header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `date` and `usage_snapshot_id`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `date` and `usage_snapshot_id`. Valid field combinations are `[ usage_snapshot_id, date ]`.
* `filter_gt` (Map<String, String>): If set, return records where the specified field is greater than the supplied value. Valid fields are `date` and `usage_snapshot_id`. Valid field combinations are `[ usage_snapshot_id, date ]`.
* `filter_gteq` (Map<String, String>): If set, return records where the specified field is greater than or equal to the supplied value. Valid fields are `date` and `usage_snapshot_id`. Valid field combinations are `[ usage_snapshot_id, date ]`.
* `filter_like` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `date` and `usage_snapshot_id`. Valid field combinations are `[ usage_snapshot_id, date ]`.
* `filter_lt` (Map<String, String>): If set, return records where the specified field is less than the supplied value. Valid fields are `date` and `usage_snapshot_id`. Valid field combinations are `[ usage_snapshot_id, date ]`.
* `filter_lteq` (Map<String, String>): If set, return records where the specified field is less than or equal to the supplied value. Valid fields are `date` and `usage_snapshot_id`. Valid field combinations are `[ usage_snapshot_id, date ]`.
