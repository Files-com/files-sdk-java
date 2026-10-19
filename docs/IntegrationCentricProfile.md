# Files.Models.IntegrationCentricProfile

## Example IntegrationCentricProfile Object

```
{
  "id": 1,
  "name": "Business Systems Onboarding",
  "workspace_id": 1,
  "use_for_all_users": false,
  "expected_remote_servers": [
    {
      "server_type": "dropbox",
      "name": "Dropbox"
    }
  ]
}
```

* `id` / `id`  (int64): Integration Centric Profile ID
* `name` / `name`  (string): Profile name
* `workspace_id` / `workspaceId`  (int64): Workspace ID
* `use_for_all_users` / `useForAllUsers`  (boolean): Whether this profile applies to all users in the Workspace by default
* `expected_remote_servers` / `expectedRemoteServers`  (array(object)): Remote Server integrations the user is expected to add and connect. Each entry requires `server_type` and may include a display `name`.


---

## List Integration Centric Profiles

```java
HashMap<String, Object> parameters = new HashMap<>();
ListIterator<IntegrationCentricProfile> integrationCentricProfiles = IntegrationCentricProfile.list(parameters, null);
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id` and `name`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id`.


---

## Show Integration Centric Profile

```java
HashMap<String, Object> parameters = new HashMap<>();
IntegrationCentricProfile integrationCentricProfile = IntegrationCentricProfile.find(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Integration Centric Profile ID.


---

## Create Integration Centric Profile

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "Business Systems Onboarding");
HashMap<String, Object> map1 = new HashMap<>();
map1.put("server_type", "dropbox");
map1.put("name", "Dropbox");
parameters.put("expected_remote_servers", new Object[] {map1});
parameters.put("workspace_id", 1L);
parameters.put("use_for_all_users", false);
IntegrationCentricProfile integrationCentricProfile = IntegrationCentricProfile.create(parameters, null);
```

### Parameters

* `name` (String): Required - Profile name
* `expected_remote_servers` (Object[]): Required - Remote Server integrations the user is expected to add and connect. Each entry requires `server_type` and may include a display `name`.
* `workspace_id` (Long): Workspace ID
* `use_for_all_users` (Boolean): Whether this profile applies to all users in the Workspace by default


---

## Update Integration Centric Profile

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "Business Systems Onboarding");
parameters.put("workspace_id", 1L);
HashMap<String, Object> map1 = new HashMap<>();
map1.put("server_type", "dropbox");
map1.put("name", "Dropbox");
parameters.put("expected_remote_servers", new Object[] {map1});
parameters.put("use_for_all_users", false);
IntegrationCentricProfile integrationCentricProfile = IntegrationCentricProfile.update(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Integration Centric Profile ID.
* `name` (String): Profile name
* `workspace_id` (Long): Workspace ID
* `expected_remote_servers` (Object[]): Remote Server integrations the user is expected to add and connect. Each entry requires `server_type` and may include a display `name`.
* `use_for_all_users` (Boolean): Whether this profile applies to all users in the Workspace by default


---

## Delete Integration Centric Profile

```java
HashMap<String, Object> parameters = new HashMap<>();
IntegrationCentricProfile.delete(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Integration Centric Profile ID.


---

## Update Integration Centric Profile

```java
IntegrationCentricProfile integrationCentricProfile = IntegrationCentricProfile.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "Business Systems Onboarding");
parameters.put("workspace_id", 1L);
HashMap<String, Object> map1 = new HashMap<>();
map1.put("server_type", "dropbox");
map1.put("name", "Dropbox");
parameters.put("expected_remote_servers", new Object[] {map1});
parameters.put("use_for_all_users", false);
IntegrationCentricProfile result = integrationCentricProfile.update(parameters);
```

### Parameters

* `id` (Long): Required - Integration Centric Profile ID.
* `name` (String): Profile name
* `workspace_id` (Long): Workspace ID
* `expected_remote_servers` (Object[]): Remote Server integrations the user is expected to add and connect. Each entry requires `server_type` and may include a display `name`.
* `use_for_all_users` (Boolean): Whether this profile applies to all users in the Workspace by default


---

## Delete Integration Centric Profile

```java
IntegrationCentricProfile integrationCentricProfile = IntegrationCentricProfile.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
integrationCentricProfile.delete(parameters);
```

### Parameters

* `id` (Long): Required - Integration Centric Profile ID.
