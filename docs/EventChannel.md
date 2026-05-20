# Files.Models.EventChannel

## Example EventChannel Object

```
{
  "id": 1,
  "name": "example",
  "description": "example",
  "enabled": true,
  "default_channel": true,
  "created_at": "2000-01-01T01:00:00Z",
  "updated_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): Event Channel ID
* `name` / `name`  (string): Event Channel name.
* `description` / `description`  (string): Event Channel description.
* `enabled` / `enabled`  (boolean): Whether this Event Channel can dispatch events.
* `default_channel` / `defaultChannel`  (boolean): Whether this Event Channel is the default destination for newly published events.
* `created_at` / `createdAt`  (date-time): Event Channel create date/time.
* `updated_at` / `updatedAt`  (date-time): Event Channel update date/time.


---

## List Event Channels

```
ListIterator<EventChannel> eventChannel = EventChannel.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `enabled` and `default_channel`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `enabled` and `default_channel`.


---

## Show Event Channel

```
EventChannel eventChannel = EventChannel.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Event Channel ID.


---

## Create Event Channel

```
EventChannel eventChannel = EventChannel.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `name` (String): Required - Event Channel name.
* `description` (String): Event Channel description.
* `enabled` (Boolean): Whether this Event Channel can dispatch events.
* `default_channel` (Boolean): Whether this Event Channel is the default destination for newly published events.


---

## Update Event Channel

```
EventChannel eventChannel = EventChannel.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Event Channel ID.
* `name` (String): Event Channel name.
* `description` (String): Event Channel description.
* `enabled` (Boolean): Whether this Event Channel can dispatch events.
* `default_channel` (Boolean): Whether this Event Channel is the default destination for newly published events.


---

## Delete Event Channel

```
void eventChannel = EventChannel.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Event Channel ID.


---

## Update Event Channel

```
EventChannel eventChannel = EventChannel.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "example");
parameters.put("description", "example");
parameters.put("enabled", true);
parameters.put("default_channel", true);

eventChannel.update(parameters);
```

### Parameters

* `id` (Long): Required - Event Channel ID.
* `name` (String): Event Channel name.
* `description` (String): Event Channel description.
* `enabled` (Boolean): Whether this Event Channel can dispatch events.
* `default_channel` (Boolean): Whether this Event Channel is the default destination for newly published events.


---

## Delete Event Channel

```
EventChannel eventChannel = EventChannel.find(id);

HashMap<String, Object> parameters = new HashMap<>();

eventChannel.delete(parameters);
```

### Parameters

* `id` (Long): Required - Event Channel ID.
