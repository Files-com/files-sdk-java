# Files.Models.Permission

## Example Permission Object

```
{
  "id": 1,
  "path": "example",
  "user_id": 1,
  "username": "user",
  "group_id": 1,
  "group_name": "example",
  "group_ids": [
    1
  ],
  "group_names": [
    "example"
  ],
  "partner_id": 1,
  "partner_name": "Acme Corp.",
  "permission": "full",
  "recursive": true,
  "site_id": 1
}
```

* `id` / `id`  (int64): Permission ID
* `path` / `path`  (string): Folder path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `user_id` / `userId`  (int64): User ID
* `username` / `username`  (string): Username (if applicable)
* `group_id` / `groupId`  (int64): Group ID
* `group_name` / `groupName`  (string): Group name (if applicable)
* `group_ids` / `groupIds`  (array(int64)): Group IDs when this Permission requires membership in every listed group.
* `group_names` / `groupNames`  (array(string)): Group names when this permission requires multiple groups
* `partner_id` / `partnerId`  (int64): Partner ID (if applicable)
* `partner_name` / `partnerName`  (string): Partner name (if applicable)
* `permission` / `permission`  (string): Permission type.  See the table referenced in the documentation for an explanation of each permission.
* `recursive` / `recursive`  (boolean): Whether this Permission applies to subfolders. Must be true for `admin` Permissions.
* `site_id` / `siteId`  (int64): Site ID


---

## List Permissions

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("path", "example");
parameters.put("include_groups", false);
parameters.put("group_id", "1");
parameters.put("partner_id", "1");
parameters.put("user_id", "1");
ListIterator<Permission> permissions = Permission.list(parameters, null);
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `site_id`, `group_id`, `path`, `user_id`, `partner_id` or `id`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `path`, `group_id`, `partner_id` or `user_id`. Valid field combinations are `[ group_id, path ]`, `[ partner_id, path ]`, `[ user_id, path ]`, `[ user_id, group_id ]`, `[ user_id, group_id, path ]`, `[ user_id, group_id, partner_id ]` or `[ user_id, group_id, partner_id, path ]`.
* `filter_prefix` (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `path`.
* `path` (String): Permission path.  If provided, will scope all permissions(including upward) to this path.
* `include_groups` (Boolean): If searching by user or group, also include user's permissions that are inherited from its groups?
* `group_id` (String): 
* `partner_id` (String): 
* `user_id` (String): 


---

## Create Permission

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("path", "path");
parameters.put("group_id", 1L);
parameters.put("group_ids", new Object[] {1});
parameters.put("permission", "full");
parameters.put("recursive", false);
parameters.put("partner_id", 1L);
parameters.put("user_id", 1L);
parameters.put("username", "user");
parameters.put("group_name", "example");
parameters.put("site_id", 1L);
Permission permission = Permission.create(parameters, null);
```

### Parameters

* `path` (String): Required - Folder path
* `group_id` (Long): Group ID. Provide `group_name` or `group_id`
* `group_ids` (String): Group IDs when the Permission requires membership in every listed group. If sent as a string, it should be comma-delimited.
* `permission` (String): Permission type.  Can be `admin`, `full`, `readonly`, `writeonly`, `list`, or `history`
* `recursive` (Boolean): Apply to subfolders recursively? Must be true for `admin` Permissions.
* `partner_id` (Long): Partner ID if this Permission belongs to a partner.
* `user_id` (Long): User ID.  Provide `username` or `user_id`
* `username` (String): User username.  Provide `username` or `user_id`
* `group_name` (String): Group name.  Provide `group_name` or `group_id`
* `site_id` (Long): Site ID. If not provided, will default to current site. Used when creating a permission for a child site.


---

## Delete Permission

```java
HashMap<String, Object> parameters = new HashMap<>();
Permission.delete(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Permission ID.


---

## Delete Permission

```java
// The first Permission listed: the list must not be empty.
Permission permission = Permission.list(null, null).iterator().next();
HashMap<String, Object> parameters = new HashMap<>();
permission.delete(parameters);
```

### Parameters

* `id` (Long): Required - Permission ID.
