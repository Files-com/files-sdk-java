# Files.Models.History

## Example History Object

```
{
  "id": 1,
  "path": "",
  "when": "2000-01-01T01:00:00Z",
  "destination": "/to_path",
  "display": "Actual text of the action here.",
  "ip": "192.283.128.182",
  "source": "/from_path",
  "targets": [

  ],
  "user_id": 1,
  "username": "user",
  "user_is_from_parent_site": true,
  "action": "create",
  "failure_type": "none",
  "interface": "web"
}
```

* `id` / `id`  (int64): Action ID
* `path` / `path`  (string): Path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `when` / `when`  (date-time): Action occurrence date/time
* `destination` / `destination`  (string): The destination path for this action, if applicable
* `display` / `display`  (string): Friendly displayed output
* `ip` / `ip`  (string): IP Address that performed this action
* `source` / `source`  (string): The source path for this action, if applicable
* `targets` / `targets`  (array(object)): Targets
* `user_id` / `userId`  (int64): User ID
* `username` / `username`  (string): Username
* `user_is_from_parent_site` / `userIsFromParentSite`  (boolean): true if this change was performed by a user on a parent site.
* `action` / `action`  (string): Type of action
* `failure_type` / `failureType`  (string): Failure type.  If action was a user login or session failure, why did it fail?
* `interface` / `interfaceName`  (string): Interface on which this action occurred.


---

## List history for specific file

```
ListIterator<Action> history = History.listForFile(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `start_at` (String): Leave blank or set to a date/time to filter earlier entries.
* `end_at` (String): Leave blank or set to a date/time to filter later entries.
* `display` (String): Display format. Leave blank or set to `full` or `parent`.
* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `path` and `created_at`.
* `path` (String): Required - Path to operate on.


---

## List history for specific folder

```
ListIterator<Action> history = History.listForFolder(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `start_at` (String): Leave blank or set to a date/time to filter earlier entries.
* `end_at` (String): Leave blank or set to a date/time to filter later entries.
* `display` (String): Display format. Leave blank or set to `full` or `parent`.
* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`.
* `path` (String): Required - Path to operate on.


---

## List history for specific user

```
ListIterator<Action> history = History.listForUser(
    Long user_id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `start_at` (String): Leave blank or set to a date/time to filter earlier entries.
* `end_at` (String): Leave blank or set to a date/time to filter later entries.
* `display` (String): Display format. Leave blank or set to `full` or `parent`.
* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `user_id` and `created_at`.
* `user_id` (Long): Required - User ID.


---

## List site login history

```
ListIterator<Action> history = History.listLogins(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `start_at` (String): Leave blank or set to a date/time to filter earlier entries.
* `end_at` (String): Leave blank or set to a date/time to filter later entries.
* `display` (String): Display format. Leave blank or set to `full` or `parent`.
* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`.


---

## List site full action history

```
ListIterator<Action> history = History.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `start_at` (String): Leave blank or set to a date/time to filter earlier entries.
* `end_at` (String): Leave blank or set to a date/time to filter later entries.
* `display` (String): Display format. Leave blank or set to `full` or `parent`.
* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `path`, `created_at` or `user_id`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `user_id`, `folder` or `path`. Valid field combinations are `[ user_id, folder ]`, `[ user_id, path ]`, `[ folder, path ]` or `[ user_id, folder, path ]`.
* `filter_prefix` (Map<String, String>): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `path`.
