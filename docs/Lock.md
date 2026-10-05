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
  "exclusive": false,
  "token": "17c54824e9931a4688ca032d03f6663c",
  "type": "write",
  "allow_access_by_any_user": false,
  "user_id": 1,
  "username": ""
}
```

* `path` / `path`  (string): Path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `timeout` / `timeout`  (int64): Lock timeout in seconds
* `depth` / `depth`  (string): 
* `recursive` / `recursive`  (boolean): Does lock apply to subfolders?
* `owner` / `owner`  (string): Arbitrary descriptive label for the lock. Does not change the lock creator or permissions.
* `scope` / `scope`  (string): 
* `exclusive` / `exclusive`  (boolean): Is lock exclusive?
* `token` / `token`  (string): Lock token.  Use to release lock.
* `type` / `type`  (string): 
* `allow_access_by_any_user` / `allowAccessByAnyUser`  (boolean): Can lock be modified by users other than its creator?
* `user_id` / `userId`  (int64): Lock creator user ID
* `username` / `username`  (string): Lock creator username
* `expected_token` / `expectedToken`  (string): Require this existing, unexpired token before refreshing or replacing a lock. Set token to the same value to refresh, or a different value to replace.


---

## List Locks by Path

```
ListIterator<Lock> lock = Lock.listFor(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
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
* `token` (String): Lock token. With expected_token, use the same value to refresh or a different value to replace the existing token.
* `expected_token` (String): Require this existing, unexpired token before refreshing or replacing a lock. Set token to the same value to refresh, or a different value to replace.
* `allow_access_by_any_user` (Boolean): Can lock be modified by users other than its creator?
* `exclusive` (Boolean): Is lock exclusive?
* `recursive` (Boolean): Does lock apply to subfolders?
* `owner` (String): Arbitrary descriptive label for the lock. Does not change the lock creator or permissions.
* `timeout` (Long): Lock timeout in seconds


---

## Delete Lock

```
void lock = Lock.delete(
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
Lock lock = Lock.list()[0];

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("token", "token");

lock.delete(parameters);
```

### Parameters

* `path` (String): Required - Path
* `token` (String): Required - Lock token
