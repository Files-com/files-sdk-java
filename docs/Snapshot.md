# Files.Models.Snapshot

## Example Snapshot Object

```
{
  "id": 1,
  "expires_at": "2000-01-01T01:00:00Z",
  "finalized_at": "2000-01-01T01:00:00Z",
  "name": "My Snapshot",
  "user_id": 1,
  "bundle_id": 1,
  "workspace_id": 1
}
```

* `id` / `id`  (int64): The snapshot's unique ID.
* `expires_at` / `expiresAt`  (date-time): When the snapshot expires.
* `finalized_at` / `finalizedAt`  (date-time): When the snapshot was finalized.
* `name` / `name`  (string): A name for the snapshot.
* `user_id` / `userId`  (int64): The user that created this snapshot, if applicable.
* `bundle_id` / `bundleId`  (int64): The bundle using this snapshot, if applicable.
* `workspace_id` / `workspaceId`  (int64): Workspace ID. `0` means the default workspace.
* `paths` / `paths`  (array(string)): An array of paths to add to the snapshot.


---

## List Snapshots

```java
HashMap<String, Object> parameters = new HashMap<>();
ListIterator<Snapshot> snapshots = Snapshot.list(parameters, null);
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).


---

## Show Snapshot

```java
HashMap<String, Object> parameters = new HashMap<>();
Snapshot snapshot = Snapshot.find(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Snapshot ID.


---

## Create Snapshot

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("expires_at", "2000-01-01T01:00:00Z");
parameters.put("name", "My Snapshot");
parameters.put("workspace_id", 0L);
Snapshot snapshot = Snapshot.create(parameters, null);
```

### Parameters

* `expires_at` (String): When the snapshot expires.
* `name` (String): A name for the snapshot.
* `paths` (String[]): An array of paths to add to the snapshot.
* `workspace_id` (Long): Workspace ID. `0` means the default workspace.


---

## Finalize Snapshot

```java
HashMap<String, Object> parameters = new HashMap<>();
Snapshot.performFinalize(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Snapshot ID.


---

## Update Snapshot

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("expires_at", "2000-01-01T01:00:00Z");
parameters.put("name", "My Snapshot");
Snapshot snapshot = Snapshot.update(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Snapshot ID.
* `expires_at` (String): When the snapshot expires.
* `name` (String): A name for the snapshot.
* `paths` (String[]): An array of paths to add to the snapshot.


---

## Delete Snapshot

```java
HashMap<String, Object> parameters = new HashMap<>();
Snapshot.delete(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Snapshot ID.


---

## Finalize Snapshot

```java
Snapshot snapshot = Snapshot.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
snapshot.performFinalize(parameters);
```

### Parameters

* `id` (Long): Required - Snapshot ID.


---

## Update Snapshot

```java
Snapshot snapshot = Snapshot.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("expires_at", "2000-01-01T01:00:00Z");
parameters.put("name", "My Snapshot");
Snapshot result = snapshot.update(parameters);
```

### Parameters

* `id` (Long): Required - Snapshot ID.
* `expires_at` (String): When the snapshot expires.
* `name` (String): A name for the snapshot.
* `paths` (String[]): An array of paths to add to the snapshot.


---

## Delete Snapshot

```java
Snapshot snapshot = Snapshot.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
snapshot.delete(parameters);
```

### Parameters

* `id` (Long): Required - Snapshot ID.
