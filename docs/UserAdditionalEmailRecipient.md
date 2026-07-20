# Files.Models.UserAdditionalEmailRecipient

## Example UserAdditionalEmailRecipient Object

```
{
  "id": 1,
  "user_id": 1,
  "workspace_id": 1,
  "email": "user-copy@example.com",
  "created_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): User additional email recipient ID
* `user_id` / `userId`  (int64): User ID
* `workspace_id` / `workspaceId`  (int64): Workspace ID (0 for default workspace).
* `email` / `email`  (string): Additional email recipient address
* `created_at` / `createdAt`  (date-time): Created at date/time


---

## List User Additional Email Recipients

```
ListIterator<UserAdditionalEmailRecipient> userAdditionalEmailRecipient = UserAdditionalEmailRecipient.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `email`, `user_id` or `workspace_id`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `email` and `workspace_id`. Valid field combinations are `[ workspace_id, email ]`.
* `filter_prefix` (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `email`.


---

## Show User Additional Email Recipient

```
UserAdditionalEmailRecipient userAdditionalEmailRecipient = UserAdditionalEmailRecipient.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - User Additional Email Recipient ID.


---

## Create User Additional Email Recipient

```
UserAdditionalEmailRecipient userAdditionalEmailRecipient = UserAdditionalEmailRecipient.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `email` (String): Required - Additional email recipient address


---

## Update User Additional Email Recipient

```
UserAdditionalEmailRecipient userAdditionalEmailRecipient = UserAdditionalEmailRecipient.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - User Additional Email Recipient ID.
* `email` (String): Additional email recipient address


---

## Delete User Additional Email Recipient

```
void userAdditionalEmailRecipient = UserAdditionalEmailRecipient.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - User Additional Email Recipient ID.


---

## Update User Additional Email Recipient

```
UserAdditionalEmailRecipient userAdditionalEmailRecipient = UserAdditionalEmailRecipient.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("email", "user-copy@example.com");

userAdditionalEmailRecipient.update(parameters);
```

### Parameters

* `id` (Long): Required - User Additional Email Recipient ID.
* `email` (String): Additional email recipient address


---

## Delete User Additional Email Recipient

```
UserAdditionalEmailRecipient userAdditionalEmailRecipient = UserAdditionalEmailRecipient.find(id);

HashMap<String, Object> parameters = new HashMap<>();

userAdditionalEmailRecipient.delete(parameters);
```

### Parameters

* `id` (Long): Required - User Additional Email Recipient ID.
