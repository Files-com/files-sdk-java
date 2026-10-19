# Files.Models.Workspace

## Example Workspace Object

```
{
  "id": 1,
  "name": "Project Alpha"
}
```

* `id` / `id`  (int64): Workspace ID
* `name` / `name`  (string): Workspace name


---

## List Workspaces

```java
HashMap<String, Object> parameters = new HashMap<>();
ListIterator<Workspace> workspaces = Workspace.list(parameters, null);
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `name`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `name`.
* `filter_prefix` (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `name`.


---

## Show Workspace

```java
HashMap<String, Object> parameters = new HashMap<>();
Workspace workspace = Workspace.find(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Workspace ID.


---

## Create Workspace

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "Project Alpha");
Workspace workspace = Workspace.create(parameters, null);
```

### Parameters

* `name` (String): Workspace name


---

## Update Workspace

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "Project Alpha");
Workspace workspace = Workspace.update(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Workspace ID.
* `name` (String): Workspace name


---

## Delete Workspace

```java
HashMap<String, Object> parameters = new HashMap<>();
Workspace.delete(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Workspace ID.


---

## Update Workspace

```java
Workspace workspace = Workspace.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "Project Alpha");
Workspace result = workspace.update(parameters);
```

### Parameters

* `id` (Long): Required - Workspace ID.
* `name` (String): Workspace name


---

## Delete Workspace

```java
Workspace workspace = Workspace.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
workspace.delete(parameters);
```

### Parameters

* `id` (Long): Required - Workspace ID.
