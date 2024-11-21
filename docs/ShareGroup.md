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
      "email": "johndoe@gmail.com"
    }
  ]
}
```

* `id` / `id`  (int64): Share Group ID
* `name` / `name`  (string): Name of the share group
* `notes` / `notes`  (string): Additional notes of the share group
* `user_id` / `userId`  (int64): Owner User ID
* `members` / `members`  (array(object)): A list of share group members


---

## List Share Groups

```
ListIterator<ShareGroup> shareGroup = ShareGroup.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).


---

## Show Share Group

```
ShareGroup shareGroup = ShareGroup.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Share Group ID.


---

## Create Share Group

```
ShareGroup shareGroup = ShareGroup.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `notes` (String): Additional notes of the share group
* `name` (String): Required - Name of the share group
* `members` (Object[]): Required - A list of share group members.


---

## Create Export Share Group

```
Export shareGroup = ShareGroup.createExport(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.


---

## Update Share Group

```
ShareGroup shareGroup = ShareGroup.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Share Group ID.
* `notes` (String): Additional notes of the share group
* `name` (String): Name of the share group
* `members` (Object[]): A list of share group members.


---

## Delete Share Group

```
void shareGroup = ShareGroup.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Share Group ID.


---

## Update Share Group

```
ShareGroup shareGroup = ShareGroup.Find(id);

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("notes", "This group is defined for testing purposes");
parameters.put("name", "Test group 1");
parameters.put("members", [{"name":"John Doe","company":"Acme Ltd","email":"johndoe@gmail.com"}]);

ShareGroup.Update(parameters);
```

### Parameters

* `id` (Long): Required - Share Group ID.
* `notes` (String): Additional notes of the share group
* `name` (String): Name of the share group
* `members` (Object[]): A list of share group members.


---

## Delete Share Group

```
ShareGroup shareGroup = ShareGroup.Find(id);

HashMap<String, Object> parameters = new HashMap<>();


ShareGroup.Delete
```

### Parameters

* `id` (Long): Required - Share Group ID.
