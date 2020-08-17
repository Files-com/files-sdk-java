# Files.Models.GroupUser

## Example GroupUser Object

```
{
  "group_name": "My Group",
  "group_id": 1,
  "user_id": 1,
  "admin": true,
  "usernames": [

  ]
}
```

* `group_name` / `groupName`  (string): Group name
* `group_id` / `groupId`  (int64): Group ID
* `user_id` / `userId`  (int64): User ID
* `admin` / `admin`  (boolean): Is this user an administrator of this group?
* `usernames` / `usernames`  (array): A list of usernames for users in this group
* `id` / `id`  (int64): Group User ID.


---

## List Group Users

```
List<GroupUser> groupUser = GroupUser.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  If provided, will return group_users of this user.
* `page` (Long): Current page number.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `action` (String): Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
* `cursor` (String): Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `group_id` (Long): Group ID.  If provided, will return group_users of this group.


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
GroupUser groupUser = GroupUser.delete(
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
GroupUser groupUser = GroupUser.ListFor(path)[0];

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("group_id", 1);
parameters.put("user_id", 1);
parameters.put("admin", true);

GroupUser.Update(parameters);
```

### Parameters

* `id` (Long): Required - Group User ID.
* `group_id` (Long): Required - Group ID to add user to.
* `user_id` (Long): Required - User ID to add to group.
* `admin` (Boolean): Is the user a group administrator?


---

## Delete Group User

```
GroupUser groupUser = GroupUser.ListFor(path)[0];

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("group_id", 1);
parameters.put("user_id", 1);

GroupUser.Delete(parameters);
```

### Parameters

* `id` (Long): Required - Group User ID.
* `group_id` (Long): Required - Group ID from which to remove user.
* `user_id` (Long): Required - User ID to remove from group.
