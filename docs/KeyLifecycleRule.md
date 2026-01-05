# Files.Models.KeyLifecycleRule

## Example KeyLifecycleRule Object

```
{
  "id": 1,
  "key_type": "gpg",
  "inactivity_days": 12,
  "name": "inactive gpg keys"
}
```

* `id` / `id`  (int64): Key Lifecycle Rule ID
* `key_type` / `keyType`  (string): Key type for which the rule will apply (gpg or ssh).
* `inactivity_days` / `inactivityDays`  (int64): Number of days of inactivity before the rule applies.
* `name` / `name`  (string): Key Lifecycle Rule name


---

## List Key Lifecycle Rules

```
ListIterator<KeyLifecycleRule> keyLifecycleRule = KeyLifecycleRule.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `key_type`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `key_type`.


---

## Show Key Lifecycle Rule

```
KeyLifecycleRule keyLifecycleRule = KeyLifecycleRule.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Key Lifecycle Rule ID.


---

## Create Key Lifecycle Rule

```
KeyLifecycleRule keyLifecycleRule = KeyLifecycleRule.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `key_type` (String): Key type for which the rule will apply (gpg or ssh).
* `inactivity_days` (Long): Number of days of inactivity before the rule applies.
* `name` (String): Key Lifecycle Rule name


---

## Update Key Lifecycle Rule

```
KeyLifecycleRule keyLifecycleRule = KeyLifecycleRule.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Key Lifecycle Rule ID.
* `key_type` (String): Key type for which the rule will apply (gpg or ssh).
* `inactivity_days` (Long): Number of days of inactivity before the rule applies.
* `name` (String): Key Lifecycle Rule name


---

## Delete Key Lifecycle Rule

```
void keyLifecycleRule = KeyLifecycleRule.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Key Lifecycle Rule ID.


---

## Update Key Lifecycle Rule

```
KeyLifecycleRule keyLifecycleRule = KeyLifecycleRule.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("key_type", "gpg");
parameters.put("inactivity_days", 12);
parameters.put("name", "inactive gpg keys");

keyLifecycleRule.update(parameters);
```

### Parameters

* `id` (Long): Required - Key Lifecycle Rule ID.
* `key_type` (String): Key type for which the rule will apply (gpg or ssh).
* `inactivity_days` (Long): Number of days of inactivity before the rule applies.
* `name` (String): Key Lifecycle Rule name


---

## Delete Key Lifecycle Rule

```
KeyLifecycleRule keyLifecycleRule = KeyLifecycleRule.find(id);

HashMap<String, Object> parameters = new HashMap<>();

keyLifecycleRule.delete(parameters);
```

### Parameters

* `id` (Long): Required - Key Lifecycle Rule ID.
