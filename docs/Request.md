# Files.Models.Request

## Example Request Object

```
{
  "id": 1,
  "path": "example",
  "source": "example",
  "destination": "example",
  "automation_id": 1,
  "user_display_name": "example"
}
```

* `id` / `id`  (int64): Request ID
* `path` / `path`  (string): Folder path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `source` / `source`  (string): Source filename, if applicable
* `destination` / `destination`  (string): Destination filename
* `automation_id` / `automationId`  (int64): ID of automation that created request
* `user_display_name` / `userDisplayName`  (string): User making the request (if applicable)
* `user_ids` / `userIds`  (string): A list of user IDs to request the file from. If sent as a string, it should be comma-delimited.
* `group_ids` / `groupIds`  (string): A list of group IDs to request the file from. If sent as a string, it should be comma-delimited.


---

## List Requests

```
ListIterator<Request> request = Request.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are .
* `mine` (Boolean): Only show requests of the current user?  (Defaults to true if current user is not a site admin.)
* `path` (String): Path to show requests for.  If omitted, shows all paths. Send `/` to represent the root directory.


---

## List Requests

```
Request request = Request.getFolder(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are .
* `mine` (Boolean): Only show requests of the current user?  (Defaults to true if current user is not a site admin.)
* `path` (String): Required - Path to show requests for.  If omitted, shows all paths. Send `/` to represent the root directory.


---

## Create Request

```
Request request = Request.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `path` (String): Required - Folder path on which to request the file.
* `destination` (String): Required - Destination filename (without extension) to request.
* `user_ids` (String): A list of user IDs to request the file from. If sent as a string, it should be comma-delimited.
* `group_ids` (String): A list of group IDs to request the file from. If sent as a string, it should be comma-delimited.


---

## Delete Request

```
void request = Request.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Request ID.


---

## Delete Request

```
Request request = Request.list()[0];

HashMap<String, Object> parameters = new HashMap<>();

request.delete(parameters);
```

### Parameters

* `id` (Long): Required - Request ID.
