# Files.Models.SiemHttpDestinationEvent

## Example SiemHttpDestinationEvent Object

```
{
  "id": 1,
  "event_type": "example",
  "status": "example",
  "body": "example",
  "event_errors": [
    "example"
  ],
  "created_at": "2000-01-01T01:00:00Z",
  "body_url": "example",
  "siem_http_destination_id": 1
}
```

* `id` / `id`  (int64): Event ID
* `event_type` / `eventType`  (string): Type of SIEM event being recorded.
* `status` / `status`  (string): Status of event.
* `body` / `body`  (string): Event body.
* `event_errors` / `eventErrors`  (array(string)): Event errors.
* `created_at` / `createdAt`  (date-time): Event create date/time.
* `body_url` / `bodyUrl`  (string): Link to log file.
* `siem_http_destination_id` / `siemHttpDestinationId`  (int64): SIEM ID.


---

## List SIEM HTTP Destination Events

```
ListIterator<SiemHttpDestinationEvent> siemHttpDestinationEvent = SiemHttpDestinationEvent.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`, `status` or `siem_http_destination_id`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`, `siem_http_destination_id` or `status`. Valid field combinations are `[ siem_http_destination_id, created_at ]`, `[ status, created_at ]`, `[ siem_http_destination_id, status ]` or `[ siem_http_destination_id, status, created_at ]`.
* `filter_gt` (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`.
* `filter_gteq` (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`.
* `filter_lt` (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`.
* `filter_lteq` (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`.


---

## Show SIEM HTTP Destination Event

```
SiemHttpDestinationEvent siemHttpDestinationEvent = SiemHttpDestinationEvent.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Siem Http Destination Event ID.
