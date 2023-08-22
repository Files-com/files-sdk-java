# Files.Models.Project

## Example Project Object

```
{
  "id": 1,
  "global_access": "none"
}
```

* `id` / `id`  (int64): Project ID
* `global_access` / `globalAccess`  (string): Global access settings


---

## List Projects

```
ListIterator<Project> project = Project.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).


---

## Show Project

```
ListIterator<Project> project = Project.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Project ID.


---

## Create Project

```
Project project = Project.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `global_access` (String): Required - Global permissions.  Can be: `none`, `anyone_with_read`, `anyone_with_full`.


---

## Update Project

```
Project project = Project.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Project ID.
* `global_access` (String): Required - Global permissions.  Can be: `none`, `anyone_with_read`, `anyone_with_full`.


---

## Delete Project

```
Project project = Project.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Project ID.


---

## Update Project

```
Project project = Project.List()[0];

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("global_access", "global_access");

Project.Update(parameters);
```

### Parameters

* `id` (Long): Required - Project ID.
* `global_access` (String): Required - Global permissions.  Can be: `none`, `anyone_with_read`, `anyone_with_full`.


---

## Delete Project

```
Project project = Project.List()[0];

HashMap<String, Object> parameters = new HashMap<>();


Project.Delete
```

### Parameters

* `id` (Long): Required - Project ID.
