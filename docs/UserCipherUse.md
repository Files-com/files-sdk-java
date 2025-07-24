# Files.Models.UserCipherUse

## Example UserCipherUse Object

```
{
  "id": 1,
  "user_id": 1,
  "username": "example",
  "protocol_cipher": "TLSv1.2; ECDHE-RSA-AES256-GCM-SHA384",
  "created_at": "2000-01-01T01:00:00Z",
  "insecure": true,
  "interface": "restapi",
  "updated_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): UserCipherUse ID
* `user_id` / `userId`  (int64): ID of the user who performed this access
* `username` / `username`  (string): Username of the user who performed this access
* `protocol_cipher` / `protocolCipher`  (string): The protocol and cipher employed
* `created_at` / `createdAt`  (date-time): The earliest recorded use of this combination of interface and protocol and cipher (for this user)
* `insecure` / `insecure`  (boolean): Is this cipher considered insecure?
* `interface` / `interfaceName`  (string): The interface accessed
* `updated_at` / `updatedAt`  (date-time): The most recent use of this combination of interface and protocol and cipher (for this user)


---

## List User Cipher Uses

```
ListIterator<UserCipherUse> userCipherUse = UserCipherUse.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID. If provided, will return uses for this user.
* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `updated_at`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `insecure` and `updated_at`. Valid field combinations are `[ insecure, updated_at ]`.
* `filter_gt` (Map<String, String>): If set, return records where the specified field is greater than the supplied value. Valid fields are `updated_at`.
* `filter_gteq` (Map<String, String>): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `updated_at`.
* `filter_lt` (Map<String, String>): If set, return records where the specified field is less than the supplied value. Valid fields are `updated_at`.
* `filter_lteq` (Map<String, String>): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `updated_at`.
