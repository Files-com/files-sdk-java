# Files.Models.As2Key

## Example As2Key Object

```
{
  "id": 1,
  "as2_partnership_name": "Test",
  "created_at": "2000-01-01T01:00:00Z",
  "fingerprint": "cf:cb:d3:26:52:6c:55:88:83:17:13:cf:e7:70:eb:1b:32:37:38:c0"
}
```

* `id` / `id`  (int64): AS2 Key ID
* `as2_partnership_name` / `as2PartnershipName`  (string): AS2 Partnership Name
* `created_at` / `createdAt`  (date-time): AS2 Key created at date/time
* `fingerprint` / `fingerprint`  (string): Public key fingerprint
* `user_id` / `userId`  (int64): User ID.  Provide a value of `0` to operate the current session's user.
* `public_key` / `publicKey`  (string): Actual contents of Public key.


---

## List As2 Keys

```
List<As2Key> as2Key = As2Key.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).


---

## Show As2 Key

```
List<As2Key> as2Key = As2Key.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - As2 Key ID.


---

## Create As2 Key

```
As2Key as2Key = As2Key.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `as2_partnership_name` (String): Required - AS2 Partnership Name
* `public_key` (String): Required - Actual contents of Public key.


---

## Update As2 Key

```
As2Key as2Key = As2Key.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - As2 Key ID.
* `as2_partnership_name` (String): Required - AS2 Partnership Name


---

## Delete As2 Key

```
As2Key as2Key = As2Key.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - As2 Key ID.


---

## Update As2 Key

```
As2Key as2Key = As2Key.ListFor(path)[0];

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("as2_partnership_name", "Test");

As2Key.Update(parameters);
```

### Parameters

* `id` (Long): Required - As2 Key ID.
* `as2_partnership_name` (String): Required - AS2 Partnership Name


---

## Delete As2 Key

```
As2Key as2Key = As2Key.ListFor(path)[0];

HashMap<String, Object> parameters = new HashMap<>();


As2Key.Delete
```

### Parameters

* `id` (Long): Required - As2 Key ID.
