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

```
ListIterator<Workspace> workspace = Workspace.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `name`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `name`.
* `filter_prefix` (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `name`.


---

## Show Workspace

```
Workspace workspace = Workspace.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Workspace ID.


---

## Create Workspace

```
Workspace workspace = Workspace.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `name` (String): Workspace name


---

## Update Workspace

```
Workspace workspace = Workspace.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Workspace ID.
* `name` (String): Workspace name


---

## Delete Workspace

```
void workspace = Workspace.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Workspace ID.


---

## Update Workspace

```
Workspace workspace = Workspace.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "Project Alpha");

workspace.update(parameters);
```

### Parameters

* `id` (Long): Required - Workspace ID.
* `name` (String): Workspace name


---

## Delete Workspace

```
Workspace workspace = Workspace.find(id);

HashMap<String, Object> parameters = new HashMap<>();

workspace.delete(parameters);
```

### Parameters

* `id` (Long): Required - Workspace ID.
