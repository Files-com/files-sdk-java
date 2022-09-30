# Files.Models.UsageSnapshot

## Example UsageSnapshot Object

```
{
  "id": 1,
  "start_at": "2000-01-01T01:00:00Z",
  "end_at": "2000-01-01T01:00:00Z",
  "created_at": "2000-01-01T01:00:00Z",
  "high_water_user_count": 1.0,
  "current_storage": 1.0,
  "high_water_storage": 1.0,
  "total_downloads": 1,
  "total_uploads": 1,
  "updated_at": "2000-01-01T01:00:00Z",
  "usage_by_top_level_dir": {
    "key": "example value"
  },
  "root_storage": 1.0,
  "deleted_files_counted_in_minimum": 1.0,
  "deleted_files_storage": 1.0,
  "total_billable_usage": 1.0,
  "total_billable_transfer_usage": 1.0,
  "bytes_sent": 1.0,
  "sync_bytes_received": 1.0,
  "sync_bytes_sent": 1.0
}
```

* `id` / `id`  (int64): Usage snapshot ID
* `start_at` / `startAt`  (date-time): Usage snapshot start date/time
* `end_at` / `endAt`  (date-time): Usage snapshot end date/time
* `created_at` / `createdAt`  (date-time): DEPRECATED: Usage snapshot created at date/time
* `high_water_user_count` / `highWaterUserCount`  (double): Highest user count number in time period
* `current_storage` / `currentStorage`  (double): Current total Storage Usage GB as of end date (not necessarily high water mark, which is used for billing)
* `high_water_storage` / `highWaterStorage`  (double): Highest Storage Usage GB recorded in time period (used for billing)
* `total_downloads` / `totalDownloads`  (int64): DEPRECATED: Number of downloads in report time period
* `total_uploads` / `totalUploads`  (int64): DEPRECATED: Number of uploads in time period
* `updated_at` / `updatedAt`  (date-time): DEPRECATED: The last time this site usage report was updated
* `usage_by_top_level_dir` / `usageByTopLevelDir`  (object): Storage Usage - map of root folders to their usage as of end date (not necessarily high water mark, which is used for billing)
* `root_storage` / `rootStorage`  (double): Storage Usage for root folder as of end date (not necessarily high water mark, which is used for billing)
* `deleted_files_counted_in_minimum` / `deletedFilesCountedInMinimum`  (double): Storage Usage for files that are deleted but uploaded within last 30 days as of end date (not necessarily high water mark, which is used for billing)
* `deleted_files_storage` / `deletedFilesStorage`  (double): Storage Usage for files that are deleted but retained as backups as of end date (not necessarily high water mark, which is used for billing)
* `total_billable_usage` / `totalBillableUsage`  (double): Storage + Transfer Usage - Total Billable amount
* `total_billable_transfer_usage` / `totalBillableTransferUsage`  (double): Transfer usage for period - Total Billable amount
* `bytes_sent` / `bytesSent`  (double): Transfer Usage for period - Outbound GB from Files Native Storage
* `sync_bytes_received` / `syncBytesReceived`  (double): Transfer Usage for period - Inbound GB to Remote Servers (Sync/Mount)
* `sync_bytes_sent` / `syncBytesSent`  (double): Transfer Usage for period - Outbound GB from Remote Servers (Sync/Mount)


---

## List Usage Snapshots

```
List<UsageSnapshot> usageSnapshot = UsageSnapshot.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via either the X-Files-Cursor-Next header or the X-Files-Cursor-Prev header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
