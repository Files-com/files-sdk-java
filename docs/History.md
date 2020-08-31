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
  "action": "create",
  "failure_type": "none",
  "interface": "web"
}
```

* `id` / `id`  (int64): Action ID
* `path` / `path`  (string): Path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `when` / `when`  (date-time): Action occurrence date/time
* `destination` / `destination`  (string): The destination path for this action, if applicable
* `display` / `display`  (string): Friendly displayed output
* `ip` / `ip`  (string): IP Address that performed this action
* `source` / `source`  (string): The source path for this action, if applicable
* `targets` / `targets`  (array): Targets
* `user_id` / `userId`  (int64): User ID
* `username` / `username`  (string): Username
* `action` / `action`  (string): Type of action
* `failure_type` / `failureType`  (string): Failure type.  If action was a user login or session failure, why did it fail?
* `interface` / `interfaceName`  (string): Interface on which this action occurred.


---

## List history for specific file

```
List<History> history = History.listForFile(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `start_at` (String): Leave blank or set to a date/time to filter earlier entries.
* `end_at` (String): Leave blank or set to a date/time to filter later entries.
* `display` (String): Display format. Leave blank or set to `full` or `parent`.
* `page` (Long): Current page number.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `action` (String): Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
* `cursor` (String): Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `user_id` and `created_at`.
* `path` (String): Required - Path to operate on.


---

## List history for specific folder

```
List<History> history = History.listForFolder(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `start_at` (String): Leave blank or set to a date/time to filter earlier entries.
* `end_at` (String): Leave blank or set to a date/time to filter later entries.
* `display` (String): Display format. Leave blank or set to `full` or `parent`.
* `page` (Long): Current page number.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `action` (String): Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
* `cursor` (String): Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `user_id` and `created_at`.
* `path` (String): Required - Path to operate on.


---

## List history for specific user

```
List<History> history = History.listForUser(
    Long user_id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `start_at` (String): Leave blank or set to a date/time to filter earlier entries.
* `end_at` (String): Leave blank or set to a date/time to filter later entries.
* `display` (String): Display format. Leave blank or set to `full` or `parent`.
* `page` (Long): Current page number.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `action` (String): Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
* `cursor` (String): Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `user_id` and `created_at`.
* `user_id` (Long): Required - User ID.


---

## List site login history

```
List<History> history = History.listLogins(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `start_at` (String): Leave blank or set to a date/time to filter earlier entries.
* `end_at` (String): Leave blank or set to a date/time to filter later entries.
* `display` (String): Display format. Leave blank or set to `full` or `parent`.
* `page` (Long): Current page number.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `action` (String): Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
* `cursor` (String): Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `user_id` and `created_at`.


---

## List site full action history

```
List<History> history = History.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `start_at` (String): Leave blank or set to a date/time to filter earlier entries.
* `end_at` (String): Leave blank or set to a date/time to filter later entries.
* `display` (String): Display format. Leave blank or set to `full` or `parent`.
* `page` (Long): Current page number.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `action` (String): Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
* `cursor` (String): Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `site_id`, `path`, `created_at`, `folder` or `user_id`.
* `filter` (Map<String, String>): If set, return records where the specifiied field is equal to the supplied value. Valid fields are `user_id`, `folder` or `path`.
* `filter_gt` (Map<String, String>): If set, return records where the specifiied field is greater than the supplied value. Valid fields are `user_id`, `folder` or `path`.
* `filter_gteq` (Map<String, String>): If set, return records where the specifiied field is greater than or equal to the supplied value. Valid fields are `user_id`, `folder` or `path`.
* `filter_like` (Map<String, String>): If set, return records where the specifiied field is equal to the supplied value. Valid fields are `user_id`, `folder` or `path`.
* `filter_lt` (Map<String, String>): If set, return records where the specifiied field is less than the supplied value. Valid fields are `user_id`, `folder` or `path`.
* `filter_lteq` (Map<String, String>): If set, return records where the specifiied field is less than or equal to the supplied value. Valid fields are `user_id`, `folder` or `path`.
