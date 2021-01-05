# Files.Models.Request

## Example Request Object

```
{
  "id": 1,
  "path": "",
  "source": "",
  "destination": "",
  "automation_id": "",
  "user_display_name": ""
}
```

* `id` / `id`  (int64): Request ID
* `path` / `path`  (string): Folder path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `source` / `source`  (string): Source filename, if applicable
* `destination` / `destination`  (string): Destination filename
* `automation_id` / `automationId`  (string): ID of automation that created request
* `user_display_name` / `userDisplayName`  (string): User making the request (if applicable)
* `user_ids` / `userIds`  (string): A list of user IDs to request the file from. If sent as a string, it should be comma-delimited.
* `group_ids` / `groupIds`  (string): A list of group IDs to request the file from. If sent as a string, it should be comma-delimited.


---

## List Requests

```
List<Request> request = Request.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `destination`.
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

* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `destination`.
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
Request request = Request.delete(
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
Request request = Request.ListFor(path)[0];

HashMap<String, Object> parameters = new HashMap<>();


Request.Delete
```

### Parameters

* `id` (Long): Required - Request ID.
