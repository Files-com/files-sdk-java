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
List<Project> project = Project.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via either the X-Files-Cursor-Next header or the X-Files-Cursor-Prev header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).


---

## Show Project

```
List<Project> project = Project.find(
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
