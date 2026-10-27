# Files.Models.ShareGroup

## Example ShareGroup Object

```
{
  "id": 1,
  "name": "Test group 1",
  "notes": "This group is defined for testing purposes",
  "user_id": 1,
  "members": [
    {
      "name": "John Doe",
      "company": "Acme Ltd",
      "email": "johndoe@example.com"
    }
  ]
}
```

* `id` / `id`  (int64): Share Group ID
* `name` / `name`  (string): Name of the share group
* `notes` / `notes`  (string): Additional notes of the share group
* `user_id` / `userId`  (int64): Owner User ID
* `members` / `members`  (ShareGroupMember[]): A list of share group members


---

## List Share Groups

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("user_id", 1L);
ListIterator<ShareGroup> shareGroups = ShareGroup.list(parameters, null);
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).


---

## Show Share Group

```java
HashMap<String, Object> parameters = new HashMap<>();
ShareGroup shareGroup = ShareGroup.find(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Share Group ID.


---

## Create Share Group

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("user_id", 1L);
parameters.put("notes", "This group is defined for testing purposes");
parameters.put("name", "Test group 1");
HashMap<String, Object> map1 = new HashMap<>();
map1.put("name", "John Doe");
map1.put("company", "Acme Ltd");
map1.put("email", "johndoe@example.com");
parameters.put("members", new Object[] {map1});
ShareGroup shareGroup = ShareGroup.create(parameters, null);
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `notes` (String): Additional notes of the share group
* `name` (String): Required - Name of the share group
* `members` (Object[]): Required - A list of share group members.


---

## Update Share Group

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("notes", "This group is defined for testing purposes");
parameters.put("name", "Test group 1");
HashMap<String, Object> map1 = new HashMap<>();
map1.put("name", "John Doe");
map1.put("company", "Acme Ltd");
map1.put("email", "johndoe@example.com");
parameters.put("members", new Object[] {map1});
ShareGroup shareGroup = ShareGroup.update(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Share Group ID.
* `notes` (String): Additional notes of the share group
* `name` (String): Name of the share group
* `members` (Object[]): A list of share group members.


---

## Delete Share Group

```java
HashMap<String, Object> parameters = new HashMap<>();
ShareGroup.delete(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Share Group ID.


---

## Update Share Group

```java
ShareGroup shareGroup = ShareGroup.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("notes", "This group is defined for testing purposes");
parameters.put("name", "Test group 1");
HashMap<String, Object> map1 = new HashMap<>();
map1.put("name", "John Doe");
map1.put("company", "Acme Ltd");
map1.put("email", "johndoe@example.com");
parameters.put("members", new Object[] {map1});
ShareGroup result = shareGroup.update(parameters);
```

### Parameters

* `id` (Long): Required - Share Group ID.
* `notes` (String): Additional notes of the share group
* `name` (String): Name of the share group
* `members` (Object[]): A list of share group members.


---

## Delete Share Group

```java
ShareGroup shareGroup = ShareGroup.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
shareGroup.delete(parameters);
```

### Parameters

* `id` (Long): Required - Share Group ID.
