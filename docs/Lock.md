# Files.Models.Lock

## Example Lock Object

```
{
  "path": "locked_file",
  "timeout": 43200,
  "depth": "infinity",
  "owner": "user",
  "scope": "shared",
  "token": "17c54824e9931a4688ca032d03f6663c",
  "type": "write",
  "user_id": 1,
  "username": ""
}
```

* `path` / `path`  (string): Path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `timeout` / `timeout`  (int64): Lock timeout
* `depth` / `depth`  (string): Lock depth (0 or infinity)
* `owner` / `owner`  (string): Owner of lock.  This can be any arbitrary string.
* `scope` / `scope`  (string): Lock scope(shared or exclusive)
* `token` / `token`  (string): Lock token.  Use to release lock.
* `type` / `type`  (string): Lock type
* `user_id` / `userId`  (int64): Lock creator user ID
* `username` / `username`  (string): Lock creator username


---

## List Locks by path

```
List<Lock> lock = Lock.listFor(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `page` (Long): Current page number.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `action` (String): Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
* `cursor` (String): Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `path` (String): Required - Path to operate on.
* `include_children` (Boolean): Include locks from children objects?


---

## Create Lock

```
Lock lock = Lock.create(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `path` (String): Required - Path
* `timeout` (Long): Lock timeout length


---

## Delete Lock

```
Lock lock = Lock.delete(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `path` (String): Required - Path
* `token` (String): Required - Lock token


---

## Delete Lock

```
Lock lock = Lock.ListFor(path)[0];

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("token", "token");

Lock.Delete(parameters);
```

### Parameters

* `path` (String): Required - Path
* `token` (String): Required - Lock token
