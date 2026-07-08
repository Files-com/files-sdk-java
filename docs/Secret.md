# Files.Models.Secret

## Example Secret Object

```
{
  "id": 1,
  "workspace_id": 1,
  "name": "Production API token",
  "description": "Used by production API integrations.",
  "secret_type": "token",
  "metadata": {
    "key": "example value"
  },
  "value_field_names": [
    "example"
  ],
  "created_at": "2000-01-01T01:00:00Z",
  "updated_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): Secret ID.
* `workspace_id` / `workspaceId`  (int64): Workspace ID. 0 means the default workspace.
* `name` / `name`  (string): Secret name.
* `description` / `description`  (string): Internal description for your reference.
* `secret_type` / `secretType`  (string): Secret type.
* `metadata` / `metadata`  (object): Non-secret metadata for the Secret type.
* `value_field_names` / `valueFieldNames`  (array(string)): Names of configured secret value fields. Secret values are never returned.
* `created_at` / `createdAt`  (date-time): Secret create date/time.
* `updated_at` / `updatedAt`  (date-time): Secret update date/time.


---

## List Secrets

```
ListIterator<Secret> secret = Secret.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `name` or `secret_type`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id`, `name` or `secret_type`. Valid field combinations are `[ workspace_id, name ]`, `[ workspace_id, secret_type ]`, `[ secret_type, name ]` or `[ workspace_id, secret_type, name ]`.
* `filter_prefix` (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `name`.


---

## Show Secret

```
Secret secret = Secret.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Secret ID.


---

## Create Secret

```
Secret secret = Secret.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `name` (String): Required - Secret name.
* `description` (String): Internal description for your reference.
* `secret_type` (String): Required - Secret type.
* `metadata` (Object): Non-secret metadata for the Secret type.
* `workspace_id` (Long): Workspace ID. 0 means the default workspace.


---

## Update Secret

```
Secret secret = Secret.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Secret ID.
* `name` (String): Secret name.
* `description` (String): Internal description for your reference.
* `secret_type` (String): Secret type.
* `metadata` (Object): Non-secret metadata for the Secret type.


---

## Delete Secret

```
void secret = Secret.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Secret ID.


---

## Update Secret

```
Secret secret = Secret.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "Production API token");
parameters.put("description", "Used by production API integrations.");
parameters.put("secret_type", "token");
parameters.put("metadata", {"key":"example value"});

secret.update(parameters);
```

### Parameters

* `id` (Long): Required - Secret ID.
* `name` (String): Secret name.
* `description` (String): Internal description for your reference.
* `secret_type` (String): Secret type.
* `metadata` (Object): Non-secret metadata for the Secret type.


---

## Delete Secret

```
Secret secret = Secret.find(id);

HashMap<String, Object> parameters = new HashMap<>();

secret.delete(parameters);
```

### Parameters

* `id` (Long): Required - Secret ID.
