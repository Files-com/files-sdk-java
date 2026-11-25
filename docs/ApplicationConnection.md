# Files.Models.ApplicationConnection

## Example ApplicationConnection Object

```
{
  "id": 1,
  "workspace_id": 1,
  "name": "example",
  "application_key": "example",
  "origin": "example",
  "secret_id": 1,
  "authentication": "example",
  "created_at": "2000-01-01T01:00:00Z",
  "updated_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): Connection ID.
* `workspace_id` / `workspaceId`  (int64): Workspace that owns this connection.
* `name` / `name`  (string): Connection display name.
* `application_key` / `applicationKey`  (string): Application key from the Automation applications list.
* `origin` / `origin`  (string): HTTPS origin for this connection.
* `secret_id` / `secretId`  (int64): ID of the credential in the Secret Store, if the connection uses a credential.
* `authentication` / `authentication`  (object): Current credential type and placement, without the secret value.
* `created_at` / `createdAt`  (date-time): Time the connection was created.
* `updated_at` / `updatedAt`  (date-time): Time the connection was updated.


---

## List Application Connections

```java
HashMap<String, Object> parameters = new HashMap<>();
ListIterator<ApplicationConnection> applicationConnections = ApplicationConnection.list(parameters, null);
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `name` or `application_key`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id` and `application_key`. Valid field combinations are `[ workspace_id, application_key ]`.
* `filter_prefix` (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `application_key`.


---

## Show Application Connection

```java
HashMap<String, Object> parameters = new HashMap<>();
ApplicationConnection applicationConnection = ApplicationConnection.find(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Application Connection ID.


---

## Create Application Connection

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "example");
parameters.put("secret_id", 1L);
parameters.put("application_key", "example");
parameters.put("origin", "example");
parameters.put("workspace_id", 1L);
ApplicationConnection applicationConnection = ApplicationConnection.create(parameters, null);
```

### Parameters

* `name` (String): Required - Connection display name.
* `secret_id` (Long): ID of the credential in the Secret Store, if the connection uses a credential.
* `application_key` (String): Required - Application key from the Automation applications list.
* `origin` (String): Required - HTTPS origin for this connection.
* `workspace_id` (Long): Workspace that owns this connection.


---

## Update Application Connection

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "example");
parameters.put("secret_id", 1L);
ApplicationConnection applicationConnection = ApplicationConnection.update(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Application Connection ID.
* `name` (String): Connection display name.
* `secret_id` (Long): ID of the credential in the Secret Store, if the connection uses a credential.


---

## Delete Application Connection

```java
HashMap<String, Object> parameters = new HashMap<>();
ApplicationConnection.delete(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Application Connection ID.


---

## Update Application Connection

```java
ApplicationConnection applicationConnection = ApplicationConnection.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "example");
parameters.put("secret_id", 1L);
ApplicationConnection result = applicationConnection.update(parameters);
```

### Parameters

* `id` (Long): Required - Application Connection ID.
* `name` (String): Connection display name.
* `secret_id` (Long): ID of the credential in the Secret Store, if the connection uses a credential.


---

## Delete Application Connection

```java
ApplicationConnection applicationConnection = ApplicationConnection.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
applicationConnection.delete(parameters);
```

### Parameters

* `id` (Long): Required - Application Connection ID.
