# Files.Models.PublicKey

## Example PublicKey Object

```
{
  "id": 1,
  "title": "My public key",
  "created_at": "2000-01-01T01:00:00Z",
  "fingerprint": "43:51:43:a1:b5:fc:8b:b7:0a:3a:a9:b1:0f:66:73:a8"
}
```

* `id` / `id`  (int64): Public key ID
* `title` / `title`  (string): Public key title
* `created_at` / `createdAt`  (date-time): Public key created at date/time
* `fingerprint` / `fingerprint`  (string): Public key fingerprint
* `user_id` / `userId`  (int64): User ID.  Provide a value of `0` to operate the current session's user.
* `public_key` / `publicKey`  (string): Actual contents of SSH key.


---

## List Public Keys

```
List<PublicKey> publicKey = PublicKey.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).


---

## Show Public Key

```
List<PublicKey> publicKey = PublicKey.find(
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
PublicKey publicKey = PublicKey.delete(
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
PublicKey publicKey = PublicKey.List()[0];

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("title", "My Main Key");

PublicKey.Update(parameters);
```

### Parameters

* `id` (Long): Required - Public Key ID.
* `title` (String): Required - Internal reference for key.


---

## Delete Public Key

```
PublicKey publicKey = PublicKey.List()[0];

HashMap<String, Object> parameters = new HashMap<>();


PublicKey.Delete
```

### Parameters

* `id` (Long): Required - Public Key ID.
