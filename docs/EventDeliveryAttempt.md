# Files.Models.EventDeliveryAttempt

## Example EventDeliveryAttempt Object

```
{
  "id": 1,
  "event_record_id": 1,
  "event_subscription_id": 1,
  "event_target_id": 1,
  "workspace_id": 1,
  "status": "example",
  "attempt_number": 1,
  "response_code": 1,
  "error_message": "example",
  "response_body": "example",
  "latency_ms": 1,
  "delivered_at": "2000-01-01T01:00:00Z",
  "last_attempted_at": "2000-01-01T01:00:00Z",
  "next_attempt_at": "2000-01-01T01:00:00Z",
  "created_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): Event Delivery Attempt ID
* `event_record_id` / `eventRecordId`  (int64): Event Record ID
* `event_subscription_id` / `eventSubscriptionId`  (int64): Event Subscription ID
* `event_target_id` / `eventTargetId`  (int64): Event Target ID
* `workspace_id` / `workspaceId`  (int64): Workspace ID. 0 means the default workspace or site-wide.
* `status` / `status`  (string): Delivery status.
* `attempt_number` / `attemptNumber`  (int64): Number of delivery attempts made.
* `response_code` / `responseCode`  (int64): HTTP response code, if applicable.
* `error_message` / `errorMessage`  (string): Delivery error message, if applicable.
* `response_body` / `responseBody`  (string): Delivery response body, if applicable.
* `latency_ms` / `latencyMs`  (int64): Delivery latency in milliseconds.
* `delivered_at` / `deliveredAt`  (date-time): Successful delivery date/time.
* `last_attempted_at` / `lastAttemptedAt`  (date-time): Most recent attempt date/time.
* `next_attempt_at` / `nextAttemptAt`  (date-time): Next scheduled attempt date/time.
* `created_at` / `createdAt`  (date-time): Delivery Attempt create date/time.


---

## List Event Delivery Attempts

```
ListIterator<EventDeliveryAttempt> eventDeliveryAttempt = EventDeliveryAttempt.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `status`, `event_record_id`, `event_target_id` or `workspace_id`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `status`, `workspace_id`, `event_record_id` or `event_target_id`. Valid field combinations are `[ workspace_id, status ]`, `[ workspace_id, event_record_id ]` or `[ workspace_id, event_target_id ]`.


---

## Show Event Delivery Attempt

```
EventDeliveryAttempt eventDeliveryAttempt = EventDeliveryAttempt.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Event Delivery Attempt ID.
