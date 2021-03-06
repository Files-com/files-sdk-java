# Files.Models.Permission

## Example Permission Object

```
{
  "id": 1,
  "path": "",
  "user_id": 1,
  "username": "Sser",
  "group_id": 1,
  "group_name": "",
  "permission": "full",
  "recursive": true
}
```

* `id` / `id`  (int64): Permission ID
* `path` / `path`  (string): Folder path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `user_id` / `userId`  (int64): User ID
* `username` / `username`  (string): User's username
* `group_id` / `groupId`  (int64): Group ID
* `group_name` / `groupName`  (string): Group name if applicable
* `permission` / `permission`  (string): Permission type
* `recursive` / `recursive`  (boolean): Does this permission apply to subfolders?


---

## List Permissions

```
List<Permission> permission = Permission.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `group_id`, `path`, `user_id` or `permission`.
* `filter` (Map<String, String>): If set, return records where the specifiied field is equal to the supplied value. Valid fields are `group_id`, `user_id` or `path`.
* `filter_gt` (Map<String, String>): If set, return records where the specifiied field is greater than the supplied value. Valid fields are `group_id`, `user_id` or `path`.
* `filter_gteq` (Map<String, String>): If set, return records where the specifiied field is greater than or equal to the supplied value. Valid fields are `group_id`, `user_id` or `path`.
* `filter_like` (Map<String, String>): If set, return records where the specifiied field is equal to the supplied value. Valid fields are `group_id`, `user_id` or `path`.
* `filter_lt` (Map<String, String>): If set, return records where the specifiied field is less than the supplied value. Valid fields are `group_id`, `user_id` or `path`.
* `filter_lteq` (Map<String, String>): If set, return records where the specifiied field is less than or equal to the supplied value. Valid fields are `group_id`, `user_id` or `path`.
* `path` (String): DEPRECATED: Permission path.  If provided, will scope permissions to this path. Use `filter[path]` instead.
* `group_id` (String): DEPRECATED: Group ID.  If provided, will scope permissions to this group. Use `filter[group_id]` instead.`
* `user_id` (String): DEPRECATED: User ID.  If provided, will scope permissions to this user. Use `filter[user_id]` instead.`
* `include_groups` (Boolean): If searching by user or group, also include user's permissions that are inherited from its groups?


---

## Create Permission

```
Permission permission = Permission.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `group_id` (Long): Group ID
* `path` (String): Folder path
* `permission` (String):  Permission type.  Can be `admin`, `full`, `readonly`, `writeonly`, `list`, or `history`
* `recursive` (Boolean): Apply to subfolders recursively?
* `user_id` (Long): User ID.  Provide `username` or `user_id`
* `username` (String): User username.  Provide `username` or `user_id`


---

## Delete Permission

```
Permission permission = Permission.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Permission ID.


---

## Delete Permission

```
Permission permission = Permission.ListFor(path)[0];

HashMap<String, Object> parameters = new HashMap<>();


Permission.Delete
```

### Parameters

* `id` (Long): Required - Permission ID.
