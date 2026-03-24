# Files.Models.ExpectationIncident

## Example ExpectationIncident Object

```
{
  "id": 1,
  "workspace_id": 1,
  "expectation_id": 1,
  "status": "open",
  "opened_at": "2000-01-01T01:00:00Z",
  "last_failed_at": "2000-01-01T01:00:00Z",
  "acknowledged_at": "2000-01-01T01:00:00Z",
  "snoozed_until": "2000-01-01T01:00:00Z",
  "resolved_at": "2000-01-01T01:00:00Z",
  "opened_by_evaluation_id": 1,
  "last_evaluation_id": 2,
  "resolved_by_evaluation_id": 3,
  "summary": null,
  "created_at": "2000-01-01T01:00:00Z",
  "updated_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): Expectation Incident ID
* `workspace_id` / `workspaceId`  (int64): Workspace ID. `0` means the default workspace.
* `expectation_id` / `expectationId`  (int64): Expectation ID.
* `status` / `status`  (string): Incident status.
* `opened_at` / `openedAt`  (date-time): When the incident was opened.
* `last_failed_at` / `lastFailedAt`  (date-time): When the most recent failing evaluation contributing to the incident occurred.
* `acknowledged_at` / `acknowledgedAt`  (date-time): When the incident was acknowledged.
* `snoozed_until` / `snoozedUntil`  (date-time): When the current snooze expires.
* `resolved_at` / `resolvedAt`  (date-time): When the incident was resolved.
* `opened_by_evaluation_id` / `openedByEvaluationId`  (int64): Evaluation that first opened the incident.
* `last_evaluation_id` / `lastEvaluationId`  (int64): Most recent evaluation linked to the incident.
* `resolved_by_evaluation_id` / `resolvedByEvaluationId`  (int64): Evaluation that resolved the incident.
* `summary` / `summary`  (object): Compact incident summary payload.
* `created_at` / `createdAt`  (date-time): Creation time.
* `updated_at` / `updatedAt`  (date-time): Last update time.


---

## List Expectation Incidents

```
ListIterator<ExpectationIncident> expectationIncident = ExpectationIncident.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `created_at` or `expectation_id`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `expectation_id` and `workspace_id`. Valid field combinations are `[ workspace_id, expectation_id ]`.


---

## Show Expectation Incident

```
ExpectationIncident expectationIncident = ExpectationIncident.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Expectation Incident ID.


---

## Resolve an expectation incident

```
ExpectationIncident expectationIncident = ExpectationIncident.resolve(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Expectation Incident ID.


---

## Snooze an expectation incident until a specified time

```
ExpectationIncident expectationIncident = ExpectationIncident.snooze(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Expectation Incident ID.
* `snoozed_until` (String): Required - Time until which the incident should remain snoozed.


---

## Acknowledge an expectation incident

```
ExpectationIncident expectationIncident = ExpectationIncident.acknowledge(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Expectation Incident ID.


---

## Resolve an expectation incident

```
ExpectationIncident expectationIncident = ExpectationIncident.find(id);

HashMap<String, Object> parameters = new HashMap<>();

expectationIncident.resolve(parameters);
```

### Parameters

* `id` (Long): Required - Expectation Incident ID.


---

## Snooze an expectation incident until a specified time

```
ExpectationIncident expectationIncident = ExpectationIncident.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("snoozed_until", "snoozed_until");

expectationIncident.snooze(parameters);
```

### Parameters

* `id` (Long): Required - Expectation Incident ID.
* `snoozed_until` (String): Required - Time until which the incident should remain snoozed.


---

## Acknowledge an expectation incident

```
ExpectationIncident expectationIncident = ExpectationIncident.find(id);

HashMap<String, Object> parameters = new HashMap<>();

expectationIncident.acknowledge(parameters);
```

### Parameters

* `id` (Long): Required - Expectation Incident ID.
