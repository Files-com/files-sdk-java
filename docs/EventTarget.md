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
* `config` / `config`  (object): Event Target configuration. Folder targets accept path and format (json or csv).
* `delivery_policy` / `deliveryPolicy`  (object): Event Target delivery policy. Email and folder targets support batch_interval in seconds, between 600 and 86400. Slack and Teams support delivery_mode (immediate or subfolder_summary), summary_path (empty string means root), and summary_delay (15–300 seconds, default 60). The summary applies to matching file activity under summary_path; other matching events retain immediate delivery.
* `created_at` / `createdAt`  (date-time): Event Target create date/time.
* `updated_at` / `updatedAt`  (date-time): Event Target update date/time.


---

## List Event Targets

```java
HashMap<String, Object> parameters = new HashMap<>();
ListIterator<EventTarget> eventTargets = EventTarget.list(parameters, null);
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `name`, `enabled` or `workspace_id`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `enabled`, `target_type` or `workspace_id`. Valid field combinations are `[ enabled, target_type ]`, `[ workspace_id, enabled ]` or `[ workspace_id, enabled, target_type ]`.


---

## Show Event Target

```java
HashMap<String, Object> parameters = new HashMap<>();
EventTarget eventTarget = EventTarget.find(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Event Target ID.


---

## Create Event Target

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "example");
parameters.put("workspace_id", 1L);
parameters.put("apply_to_all_workspaces", true);
parameters.put("enabled", true);
parameters.put("config", "example");
parameters.put("delivery_policy", "example");
parameters.put("target_type", "example");
EventTarget eventTarget = EventTarget.create(parameters, null);
```

### Parameters

* `name` (String): Required - Event Target name.
* `workspace_id` (Long): Workspace ID. 0 means the default workspace or site-wide.
* `apply_to_all_workspaces` (Boolean): If true, this default-workspace target can receive events from all workspaces.
* `enabled` (Boolean): Whether this Event Target can receive events.
* `config` (Object): Required - Event Target configuration. Folder targets accept path and format (json or csv).
* `delivery_policy` (Object): Event Target delivery policy. Email and folder targets support batch_interval in seconds, between 600 and 86400. Slack and Teams support delivery_mode (immediate or subfolder_summary), summary_path (empty string means root), and summary_delay (15–300 seconds, default 60). The summary applies to matching file activity under summary_path; other matching events retain immediate delivery.
* `target_type` (String): Required - Event Target type.


---

## Update Event Target

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "example");
parameters.put("workspace_id", 1L);
parameters.put("apply_to_all_workspaces", true);
parameters.put("enabled", true);
parameters.put("config", "example");
parameters.put("delivery_policy", "example");
EventTarget eventTarget = EventTarget.update(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Event Target ID.
* `name` (String): Event Target name.
* `workspace_id` (Long): Workspace ID. 0 means the default workspace or site-wide.
* `apply_to_all_workspaces` (Boolean): If true, this default-workspace target can receive events from all workspaces.
* `enabled` (Boolean): Whether this Event Target can receive events.
* `config` (Object): Event Target configuration. Folder targets accept path and format (json or csv).
* `delivery_policy` (Object): Event Target delivery policy. Email and folder targets support batch_interval in seconds, between 600 and 86400. Slack and Teams support delivery_mode (immediate or subfolder_summary), summary_path (empty string means root), and summary_delay (15–300 seconds, default 60). The summary applies to matching file activity under summary_path; other matching events retain immediate delivery.


---

## Delete Event Target

```java
HashMap<String, Object> parameters = new HashMap<>();
EventTarget.delete(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Event Target ID.


---

## Update Event Target

```java
EventTarget eventTarget = EventTarget.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "example");
parameters.put("workspace_id", 1L);
parameters.put("apply_to_all_workspaces", true);
parameters.put("enabled", true);
parameters.put("config", "example");
parameters.put("delivery_policy", "example");
EventTarget result = eventTarget.update(parameters);
```

### Parameters

* `id` (Long): Required - Event Target ID.
* `name` (String): Event Target name.
* `workspace_id` (Long): Workspace ID. 0 means the default workspace or site-wide.
* `apply_to_all_workspaces` (Boolean): If true, this default-workspace target can receive events from all workspaces.
* `enabled` (Boolean): Whether this Event Target can receive events.
* `config` (Object): Event Target configuration. Folder targets accept path and format (json or csv).
* `delivery_policy` (Object): Event Target delivery policy. Email and folder targets support batch_interval in seconds, between 600 and 86400. Slack and Teams support delivery_mode (immediate or subfolder_summary), summary_path (empty string means root), and summary_delay (15–300 seconds, default 60). The summary applies to matching file activity under summary_path; other matching events retain immediate delivery.


---

## Delete Event Target

```java
EventTarget eventTarget = EventTarget.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
eventTarget.delete(parameters);
```

### Parameters

* `id` (Long): Required - Event Target ID.
