# Files.Models.GroupUser

## Example GroupUser Object

```
{
  "group_name": "My Group",
  "group_id": 1,
  "user_id": 1,
  "admin": true,
  "usernames": "user"
}
```

* `group_name` / `groupName`  (string): Group name
* `group_id` / `groupId`  (int64): Group ID
* `user_id` / `userId`  (int64): User ID
* `admin` / `admin`  (boolean): Is this user an administrator of this group?
* `usernames` / `usernames`  (string): Comma-delimited list of usernames who belong to this group (separated by commas).
* `id` / `id`  (int64): Group User ID.


---

## List Group Users

```
ListIterator<GroupUser> groupUser = GroupUser.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  If provided, will return group_users of this user.
* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `group_id` (Long): Group ID.  If provided, will return group_users of this group.


---

## Create Group User

```
GroupUser groupUser = GroupUser.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `group_id` (Long): Required - Group ID to add user to.
* `user_id` (Long): Required - User ID to add to group.
* `admin` (Boolean): Is the user a group administrator?


---

## Update Group User

```
GroupUser groupUser = GroupUser.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Group User ID.
* `group_id` (Long): Required - Group ID to add user to.
* `user_id` (Long): Required - User ID to add to group.
* `admin` (Boolean): Is the user a group administrator?


---

## Delete Group User

```
void groupUser = GroupUser.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Group User ID.
* `group_id` (Long): Required - Group ID from which to remove user.
* `user_id` (Long): Required - User ID to remove from group.


---

## Update Group User

```
GroupUser groupUser = GroupUser.list()[0];

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("group_id", 1);
parameters.put("user_id", 1);
parameters.put("admin", false);

groupUser.update(parameters);
```

### Parameters

* `id` (Long): Required - Group User ID.
* `group_id` (Long): Required - Group ID to add user to.
* `user_id` (Long): Required - User ID to add to group.
* `admin` (Boolean): Is the user a group administrator?


---

## Delete Group User

```
GroupUser groupUser = GroupUser.list()[0];

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("group_id", 1);
parameters.put("user_id", 1);

groupUser.delete(parameters);
```

### Parameters

* `id` (Long): Required - Group User ID.
* `group_id` (Long): Required - Group ID from which to remove user.
* `user_id` (Long): Required - User ID to remove from group.
