# Files.Models.UserCipherUse

## Example UserCipherUse Object

```
{
  "id": 1,
  "protocol_cipher": "TLSv1.2; ECDHE-RSA-AES256-GCM-SHA384",
  "created_at": "2000-01-01T01:00:00Z",
  "interface": "restapi",
  "updated_at": "2000-01-01T01:00:00Z",
  "user_id": 1
}
```

* `id` / `id`  (int64): UserCipherUse ID
* `protocol_cipher` / `protocolCipher`  (string): The protocol and cipher employed
* `created_at` / `createdAt`  (date-time): The earliest recorded use of this combination of interface and protocol and cipher (for this user)
* `interface` / `interfaceName`  (string): The interface accessed
* `updated_at` / `updatedAt`  (date-time): The most recent use of this combination of interface and protocol and cipher (for this user)
* `user_id` / `userId`  (int64): ID of the user who performed this access


---

## List User Cipher Uses

```
List<UserCipherUse> userCipherUse = UserCipherUse.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `page` (Long): Current page number.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `action` (String): Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
* `cursor` (String): Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
