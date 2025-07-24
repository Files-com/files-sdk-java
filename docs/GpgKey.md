# Files.Models.GpgKey

## Example GpgKey Object

```
{
  "id": 1,
  "expires_at": "2000-01-01T01:00:00Z",
  "name": "key name",
  "user_id": 1,
  "public_key_md5": "7f8bc1210b09b9ddf469e6b6b8920e76",
  "private_key_md5": "ab236cfe4a195f0226bc2e674afdd6b0",
  "public_key": "7f8bc1210b09b9ddf469e6b6b8920e76",
  "private_key": "ab236cfe4a195f0226bc2e674afdd6b0",
  "private_key_password": "[your GPG private key password]"
}
```

* `id` / `id`  (int64): Your GPG key ID.
* `expires_at` / `expiresAt`  (date-time): Your GPG key expiration date.
* `name` / `name`  (string): Your GPG key name.
* `user_id` / `userId`  (int64): GPG owner's user id
* `public_key_md5` / `publicKeyMd5`  (string): MD5 hash of your GPG public key
* `private_key_md5` / `privateKeyMd5`  (string): MD5 hash of your GPG private key.
* `public_key` / `publicKey`  (string): Your GPG public key
* `private_key` / `privateKey`  (string): Your GPG private key.
* `private_key_password` / `privateKeyPassword`  (string): Your GPG private key password. Only required for password protected keys.
* `generate_expires_at` / `generateExpiresAt`  (string): Expiration date of the key. Used for the generation of the key. Will be ignored if `generate_keypair` is false.
* `generate_keypair` / `generateKeypair`  (boolean): If true, generate a new GPG key pair. Can not be used with `public_key`/`private_key`
* `generate_full_name` / `generateFullName`  (string): Full name of the key owner. Used for the generation of the key. Will be ignored if `generate_keypair` is false.
* `generate_email` / `generateEmail`  (string): Email address of the key owner. Used for the generation of the key. Will be ignored if `generate_keypair` is false.


---

## List GPG Keys

```
ListIterator<GpgKey> gpgKey = GpgKey.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `name` and `expires_at`.


---

## Show GPG Key

```
GpgKey gpgKey = GpgKey.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Gpg Key ID.


---

## Create GPG Key

```
GpgKey gpgKey = GpgKey.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `public_key` (String): MD5 hash of your GPG public key
* `private_key` (String): MD5 hash of your GPG private key.
* `private_key_password` (String): Your GPG private key password. Only required for password protected keys.
* `name` (String): Required - Your GPG key name.
* `generate_expires_at` (String): Expiration date of the key. Used for the generation of the key. Will be ignored if `generate_keypair` is false.
* `generate_keypair` (Boolean): If true, generate a new GPG key pair. Can not be used with `public_key`/`private_key`
* `generate_full_name` (String): Full name of the key owner. Used for the generation of the key. Will be ignored if `generate_keypair` is false.
* `generate_email` (String): Email address of the key owner. Used for the generation of the key. Will be ignored if `generate_keypair` is false.


---

## Update GPG Key

```
GpgKey gpgKey = GpgKey.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Gpg Key ID.
* `public_key` (String): MD5 hash of your GPG public key
* `private_key` (String): MD5 hash of your GPG private key.
* `private_key_password` (String): Your GPG private key password. Only required for password protected keys.
* `name` (String): Your GPG key name.


---

## Delete GPG Key

```
void gpgKey = GpgKey.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Gpg Key ID.


---

## Update GPG Key

```
GpgKey gpgKey = GpgKey.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("public_key", "7f8bc1210b09b9ddf469e6b6b8920e76");
parameters.put("private_key", "ab236cfe4a195f0226bc2e674afdd6b0");
parameters.put("private_key_password", "[your GPG private key password]");
parameters.put("name", "key name");

gpgKey.update(parameters);
```

### Parameters

* `id` (Long): Required - Gpg Key ID.
* `public_key` (String): MD5 hash of your GPG public key
* `private_key` (String): MD5 hash of your GPG private key.
* `private_key_password` (String): Your GPG private key password. Only required for password protected keys.
* `name` (String): Your GPG key name.


---

## Delete GPG Key

```
GpgKey gpgKey = GpgKey.find(id);

HashMap<String, Object> parameters = new HashMap<>();

gpgKey.delete(parameters);
```

### Parameters

* `id` (Long): Required - Gpg Key ID.
