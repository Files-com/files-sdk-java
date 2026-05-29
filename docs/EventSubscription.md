# Files.Models.EventSubscription

## Example EventSubscription Object

```
{
  "id": 1,
  "event_channel_id": 1,
  "workspace_id": 1,
  "apply_to_all_workspaces": true,
  "name": "example",
  "enabled": true,
  "event_types": [
    "example"
  ],
  "filter": "example",
  "delivery_policy": "example",
  "event_target_ids": [
    1
  ],
  "created_at": "2000-01-01T01:00:00Z",
  "updated_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): Event Subscription ID
* `event_channel_id` / `eventChannelId`  (int64): Event Channel ID
* `workspace_id` / `workspaceId`  (int64): Workspace ID. 0 means the default workspace or site-wide.
* `apply_to_all_workspaces` / `applyToAllWorkspaces`  (boolean): If true, this default-workspace subscription applies to events from all workspaces.
* `name` / `name`  (string): Event Subscription name.
* `enabled` / `enabled`  (boolean): Whether this Event Subscription can dispatch events.
* `event_types` / `eventTypes`  (array(string)): Event type strings matched by this subscription. Blank means all event types.
* `filter` / `filter`  (object): Structured event payload filter.
* `delivery_policy` / `deliveryPolicy`  (object): Event Subscription delivery policy.
* `event_target_ids` / `eventTargetIds`  (array(int64)): Event Target IDs this subscription sends to.
* `created_at` / `createdAt`  (date-time): Event Subscription create date/time.
* `updated_at` / `updatedAt`  (date-time): Event Subscription update date/time.


---

## List Event Subscriptions

```
ListIterator<EventSubscription> eventSubscription = EventSubscription.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `enabled`, `event_channel_id` or `workspace_id`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `enabled`, `event_channel_id` or `workspace_id`. Valid field combinations are `[ enabled, event_channel_id ]`, `[ workspace_id, enabled ]` or `[ workspace_id, enabled, event_channel_id ]`.


---

## Show Event Subscription

```
EventSubscription eventSubscription = EventSubscription.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Event Subscription ID.


---

## Create Event Subscription

```
EventSubscription eventSubscription = EventSubscription.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `event_channel_id` (Long): Event Channel ID
* `workspace_id` (Long): Workspace ID. 0 means the default workspace or site-wide.
* `apply_to_all_workspaces` (Boolean): If true, this default-workspace subscription applies to events from all workspaces.
* `name` (String): Required - Event Subscription name.
* `enabled` (Boolean): Whether this Event Subscription can dispatch events.
* `event_types` (String[]): Event type strings matched by this subscription. Blank means all event types.
* `filter` (Object): Structured event payload filter.
* `delivery_policy` (Object): Event Subscription delivery policy.
* `event_target_ids` (Long[]): Event Target IDs this subscription sends to.


---

## Update Event Subscription

```
EventSubscription eventSubscription = EventSubscription.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Event Subscription ID.
* `event_channel_id` (Long): Event Channel ID
* `workspace_id` (Long): Workspace ID. 0 means the default workspace or site-wide.
* `apply_to_all_workspaces` (Boolean): If true, this default-workspace subscription applies to events from all workspaces.
* `name` (String): Event Subscription name.
* `enabled` (Boolean): Whether this Event Subscription can dispatch events.
* `event_types` (String[]): Event type strings matched by this subscription. Blank means all event types.
* `filter` (Object): Structured event payload filter.
* `delivery_policy` (Object): Event Subscription delivery policy.
* `event_target_ids` (Long[]): Event Target IDs this subscription sends to.


---

## Delete Event Subscription

```
void eventSubscription = EventSubscription.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Event Subscription ID.


---

## Update Event Subscription

```
EventSubscription eventSubscription = EventSubscription.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("event_channel_id", 1);
parameters.put("workspace_id", 1);
parameters.put("apply_to_all_workspaces", true);
parameters.put("name", "example");
parameters.put("enabled", true);
parameters.put("event_types", ["example"]);
parameters.put("delivery_policy", "example");
parameters.put("event_target_ids", [1]);

eventSubscription.update(parameters);
```

### Parameters

* `id` (Long): Required - Event Subscription ID.
* `event_channel_id` (Long): Event Channel ID
* `workspace_id` (Long): Workspace ID. 0 means the default workspace or site-wide.
* `apply_to_all_workspaces` (Boolean): If true, this default-workspace subscription applies to events from all workspaces.
* `name` (String): Event Subscription name.
* `enabled` (Boolean): Whether this Event Subscription can dispatch events.
* `event_types` (String[]): Event type strings matched by this subscription. Blank means all event types.
* `filter` (Object): Structured event payload filter.
* `delivery_policy` (Object): Event Subscription delivery policy.
* `event_target_ids` (Long[]): Event Target IDs this subscription sends to.


---

## Delete Event Subscription

```
EventSubscription eventSubscription = EventSubscription.find(id);

HashMap<String, Object> parameters = new HashMap<>();

eventSubscription.delete(parameters);
```

### Parameters

* `id` (Long): Required - Event Subscription ID.
