# Files.Models.ExternalEvent

## Example ExternalEvent Object

```
{
  "id": 1,
  "event_type": "example",
  "status": "example",
  "body": "example",
  "created_at": "2000-01-01T01:00:00Z",
  "body_url": "example",
  "folder_behavior_id": 1,
  "successful_files": 1,
  "errored_files": 1,
  "bytes_synced": 1,
  "remote_server_type": "example"
}
```

* `id` / `id`  (int64): Event ID
* `event_type` / `eventType`  (string): Type of event being recorded.
* `status` / `status`  (string): Status of event.
* `body` / `body`  (string): Event body
* `created_at` / `createdAt`  (date-time): External event create date/time
* `body_url` / `bodyUrl`  (string): Link to log file.
* `folder_behavior_id` / `folderBehaviorId`  (int64): Folder Behavior ID
* `successful_files` / `successfulFiles`  (int64): For sync events, the number of files handled successfully.
* `errored_files` / `erroredFiles`  (int64): For sync events, the number of files that encountered errors.
* `bytes_synced` / `bytesSynced`  (int64): For sync events, the total number of bytes synced.
* `remote_server_type` / `remoteServerType`  (string): Associated Remote Server type, if any


---

## List External Events

```
List<ExternalEvent> externalEvent = ExternalEvent.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction (e.g. `sort_by[remote_server_type]=desc`). Valid fields are `remote_server_type`, `site_id`, `folder_behavior_id`, `event_type`, `created_at` or `status`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`, `event_type`, `remote_server_type`, `status` or `folder_behavior_id`. Valid field combinations are `[ event_type, status, created_at ]`, `[ event_type, created_at ]` or `[ status, created_at ]`.
* `filter_gt` (Map<String, String>): If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`, `event_type`, `remote_server_type`, `status` or `folder_behavior_id`. Valid field combinations are `[ event_type, status, created_at ]`, `[ event_type, created_at ]` or `[ status, created_at ]`.
* `filter_gteq` (Map<String, String>): If set, return records where the specified field is greater than or equal to the supplied value. Valid fields are `created_at`, `event_type`, `remote_server_type`, `status` or `folder_behavior_id`. Valid field combinations are `[ event_type, status, created_at ]`, `[ event_type, created_at ]` or `[ status, created_at ]`.
* `filter_like` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`, `event_type`, `remote_server_type`, `status` or `folder_behavior_id`. Valid field combinations are `[ event_type, status, created_at ]`, `[ event_type, created_at ]` or `[ status, created_at ]`.
* `filter_lt` (Map<String, String>): If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`, `event_type`, `remote_server_type`, `status` or `folder_behavior_id`. Valid field combinations are `[ event_type, status, created_at ]`, `[ event_type, created_at ]` or `[ status, created_at ]`.
* `filter_lteq` (Map<String, String>): If set, return records where the specified field is less than or equal to the supplied value. Valid fields are `created_at`, `event_type`, `remote_server_type`, `status` or `folder_behavior_id`. Valid field combinations are `[ event_type, status, created_at ]`, `[ event_type, created_at ]` or `[ status, created_at ]`.


---

## Show External Event

```
List<ExternalEvent> externalEvent = ExternalEvent.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - External Event ID.


---

## Create External Event

```
ExternalEvent externalEvent = ExternalEvent.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `status` (String): Required - Status of event.
* `body` (String): Required - Event body
