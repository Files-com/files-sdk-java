# Files.Models.EventRecord

## Example EventRecord Object

```
{
  "id": 1,
  "workspace_id": 1,
  "event_uuid": "example",
  "event_type": "example",
  "severity": "example",
  "source_type": "example",
  "source_id": 1,
  "occurred_at": "2000-01-01T01:00:00Z",
  "human_title": "example",
  "human_summary": "example",
  "human_fields": [
    "example"
  ],
  "actor": "example",
  "resources": [
    "example"
  ],
  "payload": "example",
  "created_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): Event Record ID
* `workspace_id` / `workspaceId`  (int64): Workspace ID. 0 means the default workspace or site-wide.
* `event_uuid` / `eventUuid`  (string): Stable event UUID.
* `event_type` / `eventType`  (string): Versioned event type string.
* `severity` / `severity`  (string): Event severity.
* `source_type` / `sourceType`  (string): Source record type.
* `source_id` / `sourceId`  (int64): Source record ID.
* `occurred_at` / `occurredAt`  (date-time): Event occurrence date/time.
* `human_title` / `humanTitle`  (string): Human-readable event title.
* `human_summary` / `humanSummary`  (string): Human-readable event summary.
* `human_fields` / `humanFields`  (array(object)): Human-readable event detail fields.
* `actor` / `actor`  (object): Actor associated with the event.
* `resources` / `resources`  (array(object)): Resources associated with the event.
* `payload` / `payload`  (object): Event payload.
* `created_at` / `createdAt`  (date-time): Event Record create date/time.


---

## List Event Records

```
ListIterator<EventRecord> eventRecord = EventRecord.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `event_type`, `created_at` or `workspace_id`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`, `event_type` or `workspace_id`. Valid field combinations are `[ event_type, created_at ]`, `[ workspace_id, created_at ]`, `[ workspace_id, event_type ]` or `[ workspace_id, event_type, created_at ]`.
* `filter_gt` (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`.
* `filter_gteq` (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`.
* `filter_prefix` (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `event_type`.
* `filter_lt` (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`.
* `filter_lteq` (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`.


---

## Show Event Record

```
EventRecord eventRecord = EventRecord.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Event Record ID.
