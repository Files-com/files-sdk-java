# Files.Models.SftpHostKey

## Example SftpHostKey Object

```
{
  "active": true,
  "custom_domain_id": 1,
  "id": 1,
  "name": "My Key",
  "key_type": "ssh-rsa",
  "fingerprint_md5": "12:7e:f8:61:78:a4:b2:c2:ee:12:51:92:25:a7:42:cc",
  "fingerprint_sha256": "SHA256:5ANRkDpXWA+PgOquzZAG9RtQ1Bt8KXYAH2hecr7LQk8"
}
```

* `active` / `active`  (boolean): If true, use this SFTP Host Key.
* `custom_domain_id` / `customDomainId`  (int64): Custom Domain ID. If set, this key is used only for that Custom Domain.
* `id` / `id`  (int64): SFTP Host Key ID
* `name` / `name`  (string): The friendly name of this SFTP Host Key.
* `key_type` / `keyType`  (string): SSH key type
* `fingerprint_md5` / `fingerprintMd5`  (string): MD5 Fingerprint of the public key
* `fingerprint_sha256` / `fingerprintSha256`  (string): SHA256 Fingerprint of the public key
* `private_key` / `privateKey`  (string): The private key data.


---

## List SFTP Host Keys

```java
HashMap<String, Object> parameters = new HashMap<>();
ListIterator<SftpHostKey> sftpHostKeys = SftpHostKey.list(parameters, null);
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).


---

## Show SFTP Host Key

```java
HashMap<String, Object> parameters = new HashMap<>();
SftpHostKey sftpHostKey = SftpHostKey.find(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Sftp Host Key ID.


---

## Create SFTP Host Key

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("active", true);
parameters.put("custom_domain_id", 1L);
parameters.put("name", "My Key");
SftpHostKey sftpHostKey = SftpHostKey.create(parameters, null);
```

### Parameters

* `active` (Boolean): If true, use this SFTP Host Key.
* `custom_domain_id` (Long): Custom Domain ID. If set, this key is used only for that Custom Domain.
* `name` (String): The friendly name of this SFTP Host Key.
* `private_key` (String): The private key data.


---

## Update SFTP Host Key

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("active", true);
parameters.put("custom_domain_id", 1L);
parameters.put("name", "My Key");
SftpHostKey sftpHostKey = SftpHostKey.update(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Sftp Host Key ID.
* `active` (Boolean): If true, use this SFTP Host Key.
* `custom_domain_id` (Long): Custom Domain ID. If set, this key is used only for that Custom Domain.
* `name` (String): The friendly name of this SFTP Host Key.
* `private_key` (String): The private key data.


---

## Delete SFTP Host Key

```java
HashMap<String, Object> parameters = new HashMap<>();
SftpHostKey.delete(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Sftp Host Key ID.


---

## Update SFTP Host Key

```java
SftpHostKey sftpHostKey = SftpHostKey.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("active", true);
parameters.put("custom_domain_id", 1L);
parameters.put("name", "My Key");
SftpHostKey result = sftpHostKey.update(parameters);
```

### Parameters

* `id` (Long): Required - Sftp Host Key ID.
* `active` (Boolean): If true, use this SFTP Host Key.
* `custom_domain_id` (Long): Custom Domain ID. If set, this key is used only for that Custom Domain.
* `name` (String): The friendly name of this SFTP Host Key.
* `private_key` (String): The private key data.


---

## Delete SFTP Host Key

```java
SftpHostKey sftpHostKey = SftpHostKey.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
sftpHostKey.delete(parameters);
```

### Parameters

* `id` (Long): Required - Sftp Host Key ID.
