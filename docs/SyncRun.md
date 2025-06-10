# Files.Models.SyncRun

## Example SyncRun Object

```
{
  "id": 1,
  "sync_id": 1,
  "site_id": 1,
  "status": "example",
  "remote_server_type": "example",
  "body": "example",
  "event_errors": "example",
  "bytes_synced": 1,
  "compared_files": 1,
  "compared_folders": 1,
  "errored_files": 1,
  "successful_files": 1,
  "runtime": "example",
  "s3_body_path": "example",
  "s3_internal_body_path": "example",
  "completed_at": "2000-01-01T01:00:00Z",
  "notified": true,
  "created_at": "2000-01-01T01:00:00Z",
  "updated_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): SyncRun ID
* `sync_id` / `syncId`  (int64): ID of the Sync this run belongs to
* `site_id` / `siteId`  (int64): Site ID
* `status` / `status`  (string): Status of the sync run (success, failure, partial_failure, in_progress, skipped)
* `remote_server_type` / `remoteServerType`  (string): Type of remote server used, if any
* `body` / `body`  (string): Log or summary body for this run
* `event_errors` / `eventErrors`  (array(array)): Array of errors encountered during the run
* `bytes_synced` / `bytesSynced`  (int64): Total bytes synced in this run
* `compared_files` / `comparedFiles`  (int64): Number of files compared
* `compared_folders` / `comparedFolders`  (int64): Number of folders compared
* `errored_files` / `erroredFiles`  (int64): Number of files that errored
* `successful_files` / `successfulFiles`  (int64): Number of files successfully synced
* `runtime` / `runtime`  (float): Total runtime in seconds
* `s3_body_path` / `s3BodyPath`  (string): S3 path to the main log file
* `s3_internal_body_path` / `s3InternalBodyPath`  (string): S3 path to the internal log file
* `completed_at` / `completedAt`  (date-time): When this run was completed
* `notified` / `notified`  (boolean): Whether notifications were sent for this run
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
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `sync_id`, `created_at` or `status`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `status` and `sync_id`. Valid field combinations are `[ sync_id, status ]`.
* `sync_id` (Long): Required - ID of the Sync this run belongs to


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
