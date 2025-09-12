# Files.Models.SyncRun

## Example SyncRun Object

```
{
  "id": 1,
  "sync_id": 1,
  "site_id": 1,
  "status": "example",
  "src_remote_server_type": "example",
  "dest_remote_server_type": "example",
  "body": "example",
  "event_errors": [
    "example"
  ],
  "compared_files": 1,
  "compared_folders": 1,
  "errored_files": 1,
  "successful_files": 1,
  "runtime": 1.0,
  "log_url": "https://www.example.com/log_file.txt",
  "completed_at": "2000-01-01T01:00:00Z",
  "notified": true,
  "dry_run": true,
  "bytes_synced": 1,
  "estimated_bytes_count": 1,
  "created_at": "2000-01-01T01:00:00Z",
  "updated_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): SyncRun ID
* `sync_id` / `syncId`  (int64): ID of the Sync this run belongs to
* `site_id` / `siteId`  (int64): Site ID
* `status` / `status`  (string): Status of the sync run (success, failure, partial_failure, in_progress, skipped)
* `src_remote_server_type` / `srcRemoteServerType`  (string): Source remote server type, if any
* `dest_remote_server_type` / `destRemoteServerType`  (string): Destination remote server type, if any
* `body` / `body`  (string): Log or summary body for this run
* `event_errors` / `eventErrors`  (array(string)): Array of errors encountered during the run
* `compared_files` / `comparedFiles`  (int64): Number of files compared
* `compared_folders` / `comparedFolders`  (int64): Number of folders compared
* `errored_files` / `erroredFiles`  (int64): Number of files that errored
* `successful_files` / `successfulFiles`  (int64): Number of files successfully synced
* `runtime` / `runtime`  (double): Total runtime in seconds
* `log_url` / `logUrl`  (string): Link to external log file.
* `completed_at` / `completedAt`  (date-time): When this run was completed
* `notified` / `notified`  (boolean): Whether notifications were sent for this run
* `dry_run` / `dryRun`  (boolean): Whether this run was a dry run (no actual changes made)
* `bytes_synced` / `bytesSynced`  (int64): Total bytes synced in this run
* `estimated_bytes_count` / `estimatedBytesCount`  (int64): Estimated bytes count for this run
* `created_at` / `createdAt`  (date-time): When this run was created
* `updated_at` / `updatedAt`  (date-time): When this run was last updated


---

## List Sync Runs

```
ListIterator<SyncRun> syncRun = SyncRun.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `site_id`, `sync_id`, `created_at` or `status`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`, `status`, `dry_run`, `src_remote_server_type`, `dest_remote_server_type` or `sync_id`. Valid field combinations are `[ status, created_at ]`, `[ src_remote_server_type, created_at ]`, `[ dest_remote_server_type, created_at ]`, `[ sync_id, created_at ]`, `[ src_remote_server_type, status ]`, `[ dest_remote_server_type, status ]`, `[ sync_id, status ]`, `[ src_remote_server_type, status, created_at ]`, `[ dest_remote_server_type, status, created_at ]` or `[ sync_id, status, created_at ]`.
* `filter_gt` (Map<String, String>): If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`.
* `filter_gteq` (Map<String, String>): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`.
* `filter_lt` (Map<String, String>): If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`.
* `filter_lteq` (Map<String, String>): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`.


---

## Show Sync Run

```
SyncRun syncRun = SyncRun.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Sync Run ID.
