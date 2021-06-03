# Files.Models.ExternalEvent

## Example ExternalEvent Object

```
{
  "id": 1,
  "event_type": "",
  "status": "",
  "body": "",
  "created_at": "2000-01-01T01:00:00Z",
  "body_url": ""
}
```

* `id` / `id`  (int64): Event ID
* `event_type` / `eventType`  (string): Type of event being recorded.
* `status` / `status`  (string): Status of event.
* `body` / `body`  (string): Event body
* `created_at` / `createdAt`  (date-time): External event create date/time
* `body_url` / `bodyUrl`  (string): Link to log file.


---

## List External Events

```
List<ExternalEvent> externalEvent = ExternalEvent.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `remote_server_type`, `event_type`, `created_at` or `status`.
* `filter` (Map<String, String>): If set, return records where the specifiied field is equal to the supplied value. Valid fields are `created_at`, `event_type`, `remote_server_type` or `status`.
* `filter_gt` (Map<String, String>): If set, return records where the specifiied field is greater than the supplied value. Valid fields are `created_at`, `event_type`, `remote_server_type` or `status`.
* `filter_gteq` (Map<String, String>): If set, return records where the specifiied field is greater than or equal to the supplied value. Valid fields are `created_at`, `event_type`, `remote_server_type` or `status`.
* `filter_like` (Map<String, String>): If set, return records where the specifiied field is equal to the supplied value. Valid fields are `created_at`, `event_type`, `remote_server_type` or `status`.
* `filter_lt` (Map<String, String>): If set, return records where the specifiied field is less than the supplied value. Valid fields are `created_at`, `event_type`, `remote_server_type` or `status`.
* `filter_lteq` (Map<String, String>): If set, return records where the specifiied field is less than or equal to the supplied value. Valid fields are `created_at`, `event_type`, `remote_server_type` or `status`.


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
