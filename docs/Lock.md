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
* `depth` / `depth`  (string): Deprecated WebDAV lock-depth field. Use recursive to determine whether the lock applies to subfolders.
* `recursive` / `recursive`  (boolean): Does lock apply to subfolders?
* `owner` / `owner`  (string): Arbitrary descriptive label for the lock. Does not change the lock creator or permissions.
* `scope` / `scope`  (string): Deprecated WebDAV lock-scope field. Use exclusive to determine whether the lock is exclusive.
* `exclusive` / `exclusive`  (boolean): Is lock exclusive?
* `token` / `token`  (string): Lock token.  Use to release lock.
* `type` / `type`  (string): Deprecated WebDAV lock-type field.
* `allow_access_by_any_user` / `allowAccessByAnyUser`  (boolean): Can lock be modified by users other than its creator?
* `user_id` / `userId`  (int64): Lock creator user ID
* `username` / `username`  (string): Lock creator username
* `expected_token` / `expectedToken`  (string): Require this existing, unexpired token before refreshing or replacing a lock. Set token to the same value to refresh, or a different value to replace.


---

## List Locks by Path

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("include_children", false);
ListIterator<Lock> locks = Lock.listFor("path", parameters, null);
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `path` (String): Required - Path to operate on.
* `include_children` (Boolean): Include locks from children objects?


---

## Create Lock

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("token", "17c54824e9931a4688ca032d03f6663c");
parameters.put("allow_access_by_any_user", false);
parameters.put("exclusive", false);
parameters.put("recursive", true);
parameters.put("owner", "user");
parameters.put("timeout", 1L);
Lock lock = Lock.create("locked_file", parameters, null);
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

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("token", "token");
Lock.delete("locked_file", parameters, null);
```

### Parameters

* `path` (String): Required - Path
* `token` (String): Required - Lock token


---

## Delete Lock

```java
HashMap<String, Object> attributes = new HashMap<>();
attributes.put("path", "locked_file");
Lock lock = new Lock(attributes);
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("token", "token");
lock.delete(parameters);
```

### Parameters

* `path` (String): Required - Path
* `token` (String): Required - Lock token
