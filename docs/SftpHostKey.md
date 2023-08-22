# Files.Models.SftpHostKey

## Example SftpHostKey Object

```
{
  "id": 1,
  "name": "example",
  "fingerprint_md5": "example",
  "fingerprint_sha256": "example"
}
```

* `id` / `id`  (int64): Sftp Host Key ID
* `name` / `name`  (string): The friendly name of this SFTP Host Key.
* `fingerprint_md5` / `fingerprintMd5`  (string): MD5 Fingerpint of the public key
* `fingerprint_sha256` / `fingerprintSha256`  (string): SHA256 Fingerpint of the public key
* `private_key` / `privateKey`  (string): The private key data.


---

## List Sftp Host Keys

```
ListIterator<SftpHostKey> sftpHostKey = SftpHostKey.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).


---

## Show Sftp Host Key

```
ListIterator<SftpHostKey> sftpHostKey = SftpHostKey.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Sftp Host Key ID.


---

## Create Sftp Host Key

```
SftpHostKey sftpHostKey = SftpHostKey.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `name` (String): The friendly name of this SFTP Host Key.
* `private_key` (String): The private key data.


---

## Update Sftp Host Key

```
SftpHostKey sftpHostKey = SftpHostKey.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Sftp Host Key ID.
* `name` (String): The friendly name of this SFTP Host Key.
* `private_key` (String): The private key data.


---

## Delete Sftp Host Key

```
SftpHostKey sftpHostKey = SftpHostKey.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Sftp Host Key ID.


---

## Update Sftp Host Key

```
SftpHostKey sftpHostKey = SftpHostKey.List()[0];

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("name", "example");

SftpHostKey.Update(parameters);
```

### Parameters

* `id` (Long): Required - Sftp Host Key ID.
* `name` (String): The friendly name of this SFTP Host Key.
* `private_key` (String): The private key data.


---

## Delete Sftp Host Key

```
SftpHostKey sftpHostKey = SftpHostKey.List()[0];

HashMap<String, Object> parameters = new HashMap<>();


SftpHostKey.Delete
```

### Parameters

* `id` (Long): Required - Sftp Host Key ID.
