# Files.Models.DatabaseDestination

## Example DatabaseDestination Object

```
{
  "id": 1,
  "workspace_id": 1,
  "name": "Orders",
  "provider": "snowflake",
  "config": "example",
  "secret_id": 1,
  "created_at": "2000-01-01T01:00:00Z",
  "updated_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): Database Destination ID.
* `workspace_id` / `workspaceId`  (int64): Workspace ID. 0 means the default workspace.
* `name` / `name`  (string): Name of the destination.
* `provider` / `provider`  (string): Database provider.
* `config` / `config`  (object): Provider settings for the destination. Use the schema matching provider. The connection address and table are fixed after creation.
* `secret_id` / `secretId`  (int64): ID of a Secret in the same Workspace. Use a Token Secret for Snowflake, a service account Key/Value Secret for BigQuery, or a Username/Password Secret for a database login.
* `created_at` / `createdAt`  (date-time): When the destination was created.
* `updated_at` / `updatedAt`  (date-time): When the destination was last updated.


---

## List Database Destinations

```java
HashMap<String, Object> parameters = new HashMap<>();
ListIterator<DatabaseDestination> databaseDestinations = DatabaseDestination.list(parameters, null);
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id` and `name`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id` and `name`. Valid field combinations are `[ workspace_id, name ]`.
* `filter_prefix` (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `name`.


---

## Show Database Destination

```java
HashMap<String, Object> parameters = new HashMap<>();
DatabaseDestination databaseDestination = DatabaseDestination.find(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Database Destination ID.


---

## Create Database Destination

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "Orders");
parameters.put("provider", "snowflake");
HashMap<String, Object> map1 = new HashMap<>();
map1.put("account_identifier", "value");
map1.put("database_name", "value");
map1.put("schema_name", "value");
map1.put("table_name", "value");
parameters.put("config", map1);
parameters.put("secret_id", 1L);
parameters.put("workspace_id", 1L);
DatabaseDestination databaseDestination = DatabaseDestination.create(parameters, null);
```

### Parameters

* `name` (String): Required - Name of the destination.
* `provider` (String): Required - Database provider.
* `config` (Object): Required - Provider settings for the destination. Use the schema matching provider. The connection address and table are fixed after creation.
* `secret_id` (Long): Required - ID of a Secret in the same Workspace. Use a Token Secret for Snowflake, a service account Key/Value Secret for BigQuery, or a Username/Password Secret for a database login.
* `workspace_id` (Long): Workspace ID. 0 means the default workspace.


---

## Update Database Destination

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "Orders");
parameters.put("secret_id", 1L);
HashMap<String, Object> map1 = new HashMap<>();
map1.put("account_identifier", "value");
map1.put("database_name", "value");
map1.put("schema_name", "value");
map1.put("table_name", "value");
parameters.put("config", map1);
DatabaseDestination databaseDestination = DatabaseDestination.update(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Database Destination ID.
* `name` (String): Name of the destination.
* `secret_id` (Long): ID of a Secret in the same Workspace. Use a Token Secret for Snowflake, a service account Key/Value Secret for BigQuery, or a Username/Password Secret for a database login.
* `config` (Object): Provider settings for the destination. Use the schema matching provider. The connection address and table are fixed after creation.


---

## Delete Database Destination

```java
HashMap<String, Object> parameters = new HashMap<>();
DatabaseDestination.delete(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Database Destination ID.


---

## Update Database Destination

```java
DatabaseDestination databaseDestination = DatabaseDestination.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "Orders");
parameters.put("secret_id", 1L);
HashMap<String, Object> map1 = new HashMap<>();
map1.put("account_identifier", "value");
map1.put("database_name", "value");
map1.put("schema_name", "value");
map1.put("table_name", "value");
parameters.put("config", map1);
DatabaseDestination result = databaseDestination.update(parameters);
```

### Parameters

* `id` (Long): Required - Database Destination ID.
* `name` (String): Name of the destination.
* `secret_id` (Long): ID of a Secret in the same Workspace. Use a Token Secret for Snowflake, a service account Key/Value Secret for BigQuery, or a Username/Password Secret for a database login.
* `config` (Object): Provider settings for the destination. Use the schema matching provider. The connection address and table are fixed after creation.


---

## Delete Database Destination

```java
DatabaseDestination databaseDestination = DatabaseDestination.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
databaseDestination.delete(parameters);
```

### Parameters

* `id` (Long): Required - Database Destination ID.
