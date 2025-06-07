# Files.Models.PublicKey

## Example PublicKey Object

```
{
  "id": 1,
  "title": "My public key",
  "created_at": "2000-01-01T01:00:00Z",
  "fingerprint": "43:51:43:a1:b5:fc:8b:b7:0a:3a:a9:b1:0f:66:73:a8",
  "fingerprint_sha256": "V5Q5t/ghT3R8Tol5GX9385bzmpygWVRnLuI9EXNrjCX",
  "last_login_at": "2000-01-01T01:00:00Z",
  "username": "User",
  "user_id": 1
}
```

* `id` / `id`  (int64): Public key ID
* `title` / `title`  (string): Public key title
* `created_at` / `createdAt`  (date-time): Public key created at date/time
* `fingerprint` / `fingerprint`  (string): Public key fingerprint (MD5)
* `fingerprint_sha256` / `fingerprintSha256`  (string): Public key fingerprint (SHA256)
* `last_login_at` / `lastLoginAt`  (date-time): Key's most recent login time via SFTP
* `username` / `username`  (string): Username of the user this public key is associated with
* `user_id` / `userId`  (int64): User ID this public key is associated with
* `public_key` / `publicKey`  (string): Actual contents of SSH key.


---

## List Public Keys

```
ListIterator<PublicKey> publicKey = PublicKey.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`.
* `filter_gt` (Map<String, String>): If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`.
* `filter_gteq` (Map<String, String>): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`.
* `filter_lt` (Map<String, String>): If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`.
* `filter_lteq` (Map<String, String>): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`.


---

## Show Public Key

```
PublicKey publicKey = PublicKey.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Public Key ID.


---

## Create Public Key

```
PublicKey publicKey = PublicKey.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `title` (String): Required - Internal reference for key.
* `public_key` (String): Required - Actual contents of SSH key.


---

## Update Public Key

```
PublicKey publicKey = PublicKey.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Public Key ID.
* `title` (String): Required - Internal reference for key.


---

## Delete Public Key

```
void publicKey = PublicKey.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Public Key ID.


---

## Update Public Key

```
PublicKey publicKey = PublicKey.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("title", "My Main Key");

publicKey.update(parameters);
```

### Parameters

* `id` (Long): Required - Public Key ID.
* `title` (String): Required - Internal reference for key.


---

## Delete Public Key

```
PublicKey publicKey = PublicKey.find(id);

HashMap<String, Object> parameters = new HashMap<>();

publicKey.delete(parameters);
```

### Parameters

* `id` (Long): Required - Public Key ID.
