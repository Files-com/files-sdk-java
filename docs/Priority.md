# Files.Models.Priority

## Example Priority Object

```
{
  "path": "foo/bar",
  "color": "pink"
}
```

* `path` / `path`  (string): The path corresponding to the priority color This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `color` / `color`  (string): The priority color


---

## List Priorities

```
List<Priority> priority = Priority.list(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `path` (String): Required - The path to query for priorities