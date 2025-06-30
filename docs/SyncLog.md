# Files.Models.SyncLog

## Example SyncLog Object

```
{
  "timestamp": "2000-01-01T01:00:00Z",
  "sync_id": 1,
  "external_event_id": 1,
  "error_type": "example",
  "message": "example",
  "operation": "example",
  "path": "example",
  "size": 1,
  "file_type": "example",
  "status": "example",
  "created_at": "2000-01-01T01:00:00Z"
}
```

* `timestamp` / `timestamp`  (date-time): Start Time of Action. Deprecrated: Use created_at.
* `sync_id` / `syncId`  (int64): Sync ID
* `external_event_id` / `externalEventId`  (int64): External Event ID
* `error_type` / `errorType`  (string): Error type, if applicable
* `message` / `message`  (string): Message
* `operation` / `operation`  (string): Operation type
* `path` / `path`  (string): File path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `size` / `size`  (int64): File size
* `file_type` / `fileType`  (string): File type
* `status` / `status`  (string): Status
* `created_at` / `createdAt`  (date-time): Start Time of Action


---

## List Sync Logs

```
ListIterator<SyncLog> syncLog = SyncLog.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `external_event_id`, `operation`, `status`, `sync_id` or `created_at`. Valid field combinations are `[ external_event_id ]`, `[ operation ]`, `[ status ]`, `[ sync_id ]`, `[ created_at ]`, `[ external_event_id, operation ]`, `[ external_event_id, status ]`, `[ external_event_id, sync_id ]`, `[ external_event_id, created_at ]`, `[ operation, status ]`, `[ operation, sync_id ]`, `[ operation, created_at ]`, `[ status, sync_id ]`, `[ status, created_at ]`, `[ sync_id, created_at ]`, `[ external_event_id, operation, status ]`, `[ external_event_id, operation, sync_id ]`, `[ external_event_id, operation, created_at ]`, `[ external_event_id, status, sync_id ]`, `[ external_event_id, status, created_at ]`, `[ external_event_id, sync_id, created_at ]`, `[ operation, status, sync_id ]`, `[ operation, status, created_at ]`, `[ operation, sync_id, created_at ]`, `[ status, sync_id, created_at ]`, `[ external_event_id, operation, status, sync_id ]`, `[ external_event_id, operation, status, created_at ]`, `[ external_event_id, operation, sync_id, created_at ]`, `[ external_event_id, status, sync_id, created_at ]`, `[ operation, status, sync_id, created_at ]` or `[ external_event_id, operation, status, sync_id, created_at ]`.
* `filter_gt` (Map<String, String>): If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`. Valid field combinations are `[ external_event_id ]`, `[ operation ]`, `[ status ]`, `[ sync_id ]`, `[ created_at ]`, `[ external_event_id, operation ]`, `[ external_event_id, status ]`, `[ external_event_id, sync_id ]`, `[ external_event_id, created_at ]`, `[ operation, status ]`, `[ operation, sync_id ]`, `[ operation, created_at ]`, `[ status, sync_id ]`, `[ status, created_at ]`, `[ sync_id, created_at ]`, `[ external_event_id, operation, status ]`, `[ external_event_id, operation, sync_id ]`, `[ external_event_id, operation, created_at ]`, `[ external_event_id, status, sync_id ]`, `[ external_event_id, status, created_at ]`, `[ external_event_id, sync_id, created_at ]`, `[ operation, status, sync_id ]`, `[ operation, status, created_at ]`, `[ operation, sync_id, created_at ]`, `[ status, sync_id, created_at ]`, `[ external_event_id, operation, status, sync_id ]`, `[ external_event_id, operation, status, created_at ]`, `[ external_event_id, operation, sync_id, created_at ]`, `[ external_event_id, status, sync_id, created_at ]`, `[ operation, status, sync_id, created_at ]` or `[ external_event_id, operation, status, sync_id, created_at ]`.
* `filter_gteq` (Map<String, String>): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`. Valid field combinations are `[ external_event_id ]`, `[ operation ]`, `[ status ]`, `[ sync_id ]`, `[ created_at ]`, `[ external_event_id, operation ]`, `[ external_event_id, status ]`, `[ external_event_id, sync_id ]`, `[ external_event_id, created_at ]`, `[ operation, status ]`, `[ operation, sync_id ]`, `[ operation, created_at ]`, `[ status, sync_id ]`, `[ status, created_at ]`, `[ sync_id, created_at ]`, `[ external_event_id, operation, status ]`, `[ external_event_id, operation, sync_id ]`, `[ external_event_id, operation, created_at ]`, `[ external_event_id, status, sync_id ]`, `[ external_event_id, status, created_at ]`, `[ external_event_id, sync_id, created_at ]`, `[ operation, status, sync_id ]`, `[ operation, status, created_at ]`, `[ operation, sync_id, created_at ]`, `[ status, sync_id, created_at ]`, `[ external_event_id, operation, status, sync_id ]`, `[ external_event_id, operation, status, created_at ]`, `[ external_event_id, operation, sync_id, created_at ]`, `[ external_event_id, status, sync_id, created_at ]`, `[ operation, status, sync_id, created_at ]` or `[ external_event_id, operation, status, sync_id, created_at ]`.
* `filter_prefix` (Map<String, String>): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `operation` and `status`. Valid field combinations are `[ external_event_id ]`, `[ operation ]`, `[ status ]`, `[ sync_id ]`, `[ created_at ]`, `[ external_event_id, operation ]`, `[ external_event_id, status ]`, `[ external_event_id, sync_id ]`, `[ external_event_id, created_at ]`, `[ operation, status ]`, `[ operation, sync_id ]`, `[ operation, created_at ]`, `[ status, sync_id ]`, `[ status, created_at ]`, `[ sync_id, created_at ]`, `[ external_event_id, operation, status ]`, `[ external_event_id, operation, sync_id ]`, `[ external_event_id, operation, created_at ]`, `[ external_event_id, status, sync_id ]`, `[ external_event_id, status, created_at ]`, `[ external_event_id, sync_id, created_at ]`, `[ operation, status, sync_id ]`, `[ operation, status, created_at ]`, `[ operation, sync_id, created_at ]`, `[ status, sync_id, created_at ]`, `[ external_event_id, operation, status, sync_id ]`, `[ external_event_id, operation, status, created_at ]`, `[ external_event_id, operation, sync_id, created_at ]`, `[ external_event_id, status, sync_id, created_at ]`, `[ operation, status, sync_id, created_at ]` or `[ external_event_id, operation, status, sync_id, created_at ]`.
* `filter_lt` (Map<String, String>): If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`. Valid field combinations are `[ external_event_id ]`, `[ operation ]`, `[ status ]`, `[ sync_id ]`, `[ created_at ]`, `[ external_event_id, operation ]`, `[ external_event_id, status ]`, `[ external_event_id, sync_id ]`, `[ external_event_id, created_at ]`, `[ operation, status ]`, `[ operation, sync_id ]`, `[ operation, created_at ]`, `[ status, sync_id ]`, `[ status, created_at ]`, `[ sync_id, created_at ]`, `[ external_event_id, operation, status ]`, `[ external_event_id, operation, sync_id ]`, `[ external_event_id, operation, created_at ]`, `[ external_event_id, status, sync_id ]`, `[ external_event_id, status, created_at ]`, `[ external_event_id, sync_id, created_at ]`, `[ operation, status, sync_id ]`, `[ operation, status, created_at ]`, `[ operation, sync_id, created_at ]`, `[ status, sync_id, created_at ]`, `[ external_event_id, operation, status, sync_id ]`, `[ external_event_id, operation, status, created_at ]`, `[ external_event_id, operation, sync_id, created_at ]`, `[ external_event_id, status, sync_id, created_at ]`, `[ operation, status, sync_id, created_at ]` or `[ external_event_id, operation, status, sync_id, created_at ]`.
* `filter_lteq` (Map<String, String>): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`. Valid field combinations are `[ external_event_id ]`, `[ operation ]`, `[ status ]`, `[ sync_id ]`, `[ created_at ]`, `[ external_event_id, operation ]`, `[ external_event_id, status ]`, `[ external_event_id, sync_id ]`, `[ external_event_id, created_at ]`, `[ operation, status ]`, `[ operation, sync_id ]`, `[ operation, created_at ]`, `[ status, sync_id ]`, `[ status, created_at ]`, `[ sync_id, created_at ]`, `[ external_event_id, operation, status ]`, `[ external_event_id, operation, sync_id ]`, `[ external_event_id, operation, created_at ]`, `[ external_event_id, status, sync_id ]`, `[ external_event_id, status, created_at ]`, `[ external_event_id, sync_id, created_at ]`, `[ operation, status, sync_id ]`, `[ operation, status, created_at ]`, `[ operation, sync_id, created_at ]`, `[ status, sync_id, created_at ]`, `[ external_event_id, operation, status, sync_id ]`, `[ external_event_id, operation, status, created_at ]`, `[ external_event_id, operation, sync_id, created_at ]`, `[ external_event_id, status, sync_id, created_at ]`, `[ operation, status, sync_id, created_at ]` or `[ external_event_id, operation, status, sync_id, created_at ]`.
