# Files.Models.GroupUser

## Example GroupUser Object

```
{
  "group_name": "My Group",
  "group_id": 1,
  "user_id": 1,
  "admin": true,
  "username": "example"
}
```

* `group_name` / `groupName`  (string): Group name
* `group_id` / `groupId`  (int64): Group ID
* `user_id` / `userId`  (int64): User ID
* `admin` / `admin`  (boolean): Is this user an administrator of this group?
* `username` / `username`  (string): Username of the user
* `id` / `id`  (int64): Group User ID.


---

## List Group Users

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("group_id", 1L);
parameters.put("user_id", 1L);
ListIterator<GroupUser> groupUsers = GroupUser.list(parameters, null);
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `group_id` (Long): Group ID. If provided, returns memberships of this group. Requires a Site Administrator, a Read-only Administrator, a Workspace Administrator for the group's workspace, or a Group Administrator of this group.
* `user_id` (Long): User ID.  If provided, will return group_users of this user.


---

## Create Group User

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("group_id", 1L);
parameters.put("user_id", 1L);
parameters.put("admin", false);
GroupUser groupUser = GroupUser.create(parameters, null);
```

### Parameters

* `group_id` (Long): Required - Group ID to add user to.
* `user_id` (Long): Required - User ID to add to group.
* `admin` (Boolean): Is the user a group administrator?


---

## Update Group User

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("group_id", 1L);
parameters.put("user_id", 1L);
parameters.put("admin", false);
GroupUser groupUser = GroupUser.update(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Group User ID.
* `group_id` (Long): Required - Group ID to add user to.
* `user_id` (Long): Required - User ID to add to group.
* `admin` (Boolean): Is the user a group administrator?


---

## Delete Group User

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("group_id", 1L);
parameters.put("user_id", 1L);
GroupUser.delete(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Group User ID.
* `group_id` (Long): Required - Group ID from which to remove user.
* `user_id` (Long): Required - User ID to remove from group.


---

## Update Group User

```java
// The first GroupUser listed: the list must not be empty.
GroupUser groupUser = GroupUser.list(null, null).iterator().next();
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("group_id", 1L);
parameters.put("user_id", 1L);
parameters.put("admin", false);
GroupUser result = groupUser.update(parameters);
```

### Parameters

* `id` (Long): Required - Group User ID.
* `group_id` (Long): Required - Group ID to add user to.
* `user_id` (Long): Required - User ID to add to group.
* `admin` (Boolean): Is the user a group administrator?


---

## Delete Group User

```java
// The first GroupUser listed: the list must not be empty.
GroupUser groupUser = GroupUser.list(null, null).iterator().next();
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("group_id", 1L);
parameters.put("user_id", 1L);
groupUser.delete(parameters);
```

### Parameters

* `id` (Long): Required - Group User ID.
* `group_id` (Long): Required - Group ID from which to remove user.
* `user_id` (Long): Required - User ID to remove from group.
