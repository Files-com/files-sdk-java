# Files.Models.Priority

## Example Priority Object

```
{
  "path": "foo/bar",
  "color": "pink"
}
```

* `path` / `path`  (string): The path corresponding to the priority color. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `color` / `color`  (string): The priority color


---

## List Priorities

```
ListIterator<Priority> priority = Priority.list(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `path` (String): Required - The path to query for priorities


---

## Create an export CSV of Priority resources

```
Export priority = Priority.createExport(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `path` (String): Required - The path to query for priorities
