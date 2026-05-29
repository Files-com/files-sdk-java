# Files.Models.ExternalEvent

## Example ExternalEvent Object

```
{
  "id": 1,
  "event_type": "example",
  "status": "example",
  "body": "example",
  "created_at": "2000-01-01T01:00:00Z",
  "body_url": "example"
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
ListIterator<ExternalEvent> externalEvent = ExternalEvent.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`, `status` or `event_type`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at` and `status`. Valid field combinations are `[ status, created_at ]`.
* `filter_gt` (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`.
* `filter_gteq` (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`.
* `filter_lt` (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`.
* `filter_lteq` (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`.


---

## Show External Event

```
ExternalEvent externalEvent = ExternalEvent.find(
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
