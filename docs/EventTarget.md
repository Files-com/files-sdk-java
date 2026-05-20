# Files.Models.EventTarget

## Example EventTarget Object

```
{
  "id": 1,
  "name": "example",
  "target_type": "example",
  "workspace_id": 1,
  "apply_to_all_workspaces": true,
  "enabled": true,
  "config": "example",
  "delivery_policy": "example",
  "created_at": "2000-01-01T01:00:00Z",
  "updated_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): Event Target ID
* `name` / `name`  (string): Event Target name.
* `target_type` / `targetType`  (string): Event Target type.
* `workspace_id` / `workspaceId`  (int64): Workspace ID. 0 means the default workspace or site-wide.
* `apply_to_all_workspaces` / `applyToAllWorkspaces`  (boolean): If true, this default-workspace target can receive events from all workspaces.
* `enabled` / `enabled`  (boolean): Whether this Event Target can receive events.
* `config` / `config`  (object): Event Target configuration.
* `delivery_policy` / `deliveryPolicy`  (object): Event Target delivery policy. Email targets support batch_interval in seconds, between 600 and 86400.
* `created_at` / `createdAt`  (date-time): Event Target create date/time.
* `updated_at` / `updatedAt`  (date-time): Event Target update date/time.


---

## List Event Targets

```
ListIterator<EventTarget> eventTarget = EventTarget.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `enabled` and `workspace_id`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `enabled`, `target_type` or `workspace_id`. Valid field combinations are `[ enabled, target_type ]`, `[ workspace_id, enabled ]` or `[ workspace_id, enabled, target_type ]`.


---

## Show Event Target

```
EventTarget eventTarget = EventTarget.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Event Target ID.


---

## Create Event Target

```
EventTarget eventTarget = EventTarget.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `name` (String): Required - Event Target name.
* `workspace_id` (Long): Workspace ID. 0 means the default workspace or site-wide.
* `apply_to_all_workspaces` (Boolean): If true, this default-workspace target can receive events from all workspaces.
* `target_type` (String): Required - Event Target type.
* `enabled` (Boolean): Whether this Event Target can receive events.
* `config` (Object): Required - Event Target configuration.
* `delivery_policy` (Object): Event Target delivery policy. Email targets support batch_interval in seconds, between 600 and 86400.


---

## Update Event Target

```
EventTarget eventTarget = EventTarget.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Event Target ID.
* `name` (String): Event Target name.
* `workspace_id` (Long): Workspace ID. 0 means the default workspace or site-wide.
* `apply_to_all_workspaces` (Boolean): If true, this default-workspace target can receive events from all workspaces.
* `target_type` (String): Event Target type.
* `enabled` (Boolean): Whether this Event Target can receive events.
* `config` (Object): Event Target configuration.
* `delivery_policy` (Object): Event Target delivery policy. Email targets support batch_interval in seconds, between 600 and 86400.


---

## Delete Event Target

```
void eventTarget = EventTarget.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Event Target ID.


---

## Update Event Target

```
EventTarget eventTarget = EventTarget.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "example");
parameters.put("workspace_id", 1);
parameters.put("apply_to_all_workspaces", true);
parameters.put("target_type", "example");
parameters.put("enabled", true);
parameters.put("config", "example");
parameters.put("delivery_policy", "example");

eventTarget.update(parameters);
```

### Parameters

* `id` (Long): Required - Event Target ID.
* `name` (String): Event Target name.
* `workspace_id` (Long): Workspace ID. 0 means the default workspace or site-wide.
* `apply_to_all_workspaces` (Boolean): If true, this default-workspace target can receive events from all workspaces.
* `target_type` (String): Event Target type.
* `enabled` (Boolean): Whether this Event Target can receive events.
* `config` (Object): Event Target configuration.
* `delivery_policy` (Object): Event Target delivery policy. Email targets support batch_interval in seconds, between 600 and 86400.


---

## Delete Event Target

```
EventTarget eventTarget = EventTarget.find(id);

HashMap<String, Object> parameters = new HashMap<>();

eventTarget.delete(parameters);
```

### Parameters

* `id` (Long): Required - Event Target ID.
