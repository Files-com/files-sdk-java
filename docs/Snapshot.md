# Files.Models.Snapshot

## Example Snapshot Object

```
{
  "expires_at": "2000-01-01T01:00:00Z",
  "finalized_at": "2000-01-01T01:00:00Z",
  "name": "My Snapshot",
  "user_id": 1,
  "bundle_id": 1
}
```

* `expires_at` / `expiresAt`  (date-time): When the snapshot expires.
* `finalized_at` / `finalizedAt`  (date-time): When the snapshot was finalized.
* `name` / `name`  (string): A name for the snapshot.
* `user_id` / `userId`  (int64): The user that created this snapshot, if applicable.
* `bundle_id` / `bundleId`  (int64): The bundle using this snapshot, if applicable.
* `id` / `id`  (int64): Snapshot ID.


---

## List Snapshots

```
List<Snapshot> snapshot = Snapshot.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).


---

## Show Snapshot

```
List<Snapshot> snapshot = Snapshot.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Snapshot ID.


---

## Create Snapshot

```
Snapshot snapshot = Snapshot.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```


---

## Update Snapshot

```
Snapshot snapshot = Snapshot.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Snapshot ID.


---

## Delete Snapshot

```
Snapshot snapshot = Snapshot.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Snapshot ID.


---

## Update Snapshot

```
Snapshot snapshot = Snapshot.List()[0];

HashMap<String, Object> parameters = new HashMap<>();


Snapshot.Update
```

### Parameters

* `id` (Long): Required - Snapshot ID.


---

## Delete Snapshot

```
Snapshot snapshot = Snapshot.List()[0];

HashMap<String, Object> parameters = new HashMap<>();


Snapshot.Delete
```

### Parameters

* `id` (Long): Required - Snapshot ID.
