# Files.Models.Lock

## Example Lock Object

```
{
  "path": "locked_file",
  "timeout": 1,
  "depth": "infinity",
  "recursive": true,
  "owner": "user",
  "scope": "shared",
  "exclusive": true,
  "token": "17c54824e9931a4688ca032d03f6663c",
  "type": "write",
  "allow_access_by_any_user": true,
  "user_id": 1,
  "username": ""
}
```

* `path` / `path`  (string): Path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `timeout` / `timeout`  (int64): Lock timeout in seconds
* `depth` / `depth`  (string): DEPRECATED: Lock depth
* `recursive` / `recursive`  (boolean): Does lock apply to subfolders?
* `owner` / `owner`  (string): Owner of the lock.  This can be any arbitrary string.
* `scope` / `scope`  (string): DEPRECATED: Lock scope
* `exclusive` / `exclusive`  (boolean): Is lock exclusive?
* `token` / `token`  (string): Lock token.  Use to release lock.
* `type` / `type`  (string): DEPRECATED: Lock type
* `allow_access_by_any_user` / `allowAccessByAnyUser`  (boolean): Can lock be modified by users other than its creator?
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

* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via either the X-Files-Cursor-Next header or the X-Files-Cursor-Prev header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
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
* `allow_access_by_any_user` (Boolean): Allow lock to be updated by any user?
* `exclusive` (Boolean): Is lock exclusive?
* `recursive` (String): Does lock apply to subfolders?
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
Lock lock = Lock.List()[0];

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("token", "token");

Lock.Delete(parameters);
```

### Parameters

* `path` (String): Required - Path
* `token` (String): Required - Lock token
