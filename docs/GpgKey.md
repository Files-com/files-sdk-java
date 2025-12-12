# Files.Models.GpgKey

## Example GpgKey Object

```
{
  "id": 1,
  "expires_at": "2000-01-01T01:00:00Z",
  "name": "key name",
  "partner_id": 1,
  "partner_name": "example",
  "user_id": 1,
  "public_key_md5": "7f8bc1210b09b9ddf469e6b6b8920e76",
  "private_key_md5": "ab236cfe4a195f0226bc2e674afdd6b0",
  "generated_public_key": "7f8bc1210b09b9ddf469e6b6b8920e76",
  "generated_private_key": "ab236cfe4a195f0226bc2e674afdd6b0",
  "private_key_password_md5": "[the GPG private key password]"
}
```

* `id` / `id`  (int64): GPG key ID.
* `expires_at` / `expiresAt`  (date-time): GPG key expiration date.
* `name` / `name`  (string): GPG key name.
* `partner_id` / `partnerId`  (int64): Partner ID who owns this GPG Key, if applicable.
* `partner_name` / `partnerName`  (string): Name of the Partner who owns this GPG Key, if applicable.
* `user_id` / `userId`  (int64): User ID who owns this GPG Key, if applicable.
* `public_key_md5` / `publicKeyMd5`  (string): MD5 hash of the GPG public key
* `private_key_md5` / `privateKeyMd5`  (string): MD5 hash of the GPG private key.
* `generated_public_key` / `generatedPublicKey`  (string): GPG public key
* `generated_private_key` / `generatedPrivateKey`  (string): GPG private key.
* `private_key_password_md5` / `privateKeyPasswordMd5`  (string): GPG private key password. Only required for password protected keys.
* `public_key` / `publicKey`  (string): The GPG public key
* `private_key` / `privateKey`  (string): The GPG private key
* `private_key_password` / `privateKeyPassword`  (string): The GPG private key password
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
* `partner_id` (Long): Partner ID who owns this GPG Key, if applicable.
* `public_key` (String): The GPG public key
* `private_key` (String): The GPG private key
* `private_key_password` (String): The GPG private key password
* `name` (String): Required - GPG key name.
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
* `partner_id` (Long): Partner ID who owns this GPG Key, if applicable.
* `public_key` (String): The GPG public key
* `private_key` (String): The GPG private key
* `private_key_password` (String): The GPG private key password
* `name` (String): GPG key name.


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
parameters.put("partner_id", 1);
parameters.put("name", "key name");

gpgKey.update(parameters);
```

### Parameters

* `id` (Long): Required - Gpg Key ID.
* `partner_id` (Long): Partner ID who owns this GPG Key, if applicable.
* `public_key` (String): The GPG public key
* `private_key` (String): The GPG private key
* `private_key_password` (String): The GPG private key password
* `name` (String): GPG key name.


---

## Delete GPG Key

```
GpgKey gpgKey = GpgKey.find(id);

HashMap<String, Object> parameters = new HashMap<>();

gpgKey.delete(parameters);
```

### Parameters

* `id` (Long): Required - Gpg Key ID.
