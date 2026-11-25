# Files.Models.AutomationVersion

## Example AutomationVersion Object

```
{
  "id": 1,
  "automation_id": 1,
  "workspace_id": 1,
  "version": 1,
  "created_at": "2000-01-01T01:00:00Z",
  "user_id": 1,
  "username": "example",
  "api_key_id": 1,
  "definition": {
    "schema_version": 1,
    "nodes": [
      {
        "id": "trigger",
        "type": "trigger_manual"
      },
      {
        "id": "create_reports",
        "type": "create_folder",
        "config": {
          "destinations": [
            "reports/"
          ]
        }
      }
    ],
    "edges": [
      {
        "from": "trigger",
        "to": "create_reports"
      }
    ]
  },
  "application_actions": "example"
}
```

* `id` / `id`  (int64): Automation revision ID.
* `automation_id` / `automationId`  (int64): ID of the Automation that owns this revision.
* `workspace_id` / `workspaceId`  (int64): Workspace that owns the Automation. Zero is the Default Workspace.
* `version` / `version`  (int64): Revision number within the Automation.
* `created_at` / `createdAt`  (date-time): Time the revision was saved.
* `user_id` / `userId`  (int64): ID of the user who saved the revision, when available.
* `username` / `username`  (string): Username recorded when the revision was saved, when available.
* `api_key_id` / `apiKeyId`  (int64): ID of the API key used to save the revision, when available.
* `definition` / `definition`  (object): Saved Automation v2 graph definition. Included when retrieving a single revision.
* `application_actions` / `applicationActions`  (object): Normalized application action metadata keyed by pinned action fingerprint.


---

## List Automation Versions

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("automation_id", 1L);
ListIterator<AutomationVersion> automationVersions = AutomationVersion.list(parameters, null);
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are .
* `automation_id` (Long): Required - ID of the Automation that owns this revision.


---

## Show Automation Version

```java
HashMap<String, Object> parameters = new HashMap<>();
AutomationVersion automationVersion = AutomationVersion.find(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Automation Version ID.


---

## Restore an Automation v2 definition as a new current version

```java
HashMap<String, Object> parameters = new HashMap<>();
Automation automation = AutomationVersion.restore(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Automation Version ID.


---

## Restore an Automation v2 definition as a new current version

```java
AutomationVersion automationVersion = AutomationVersion.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
Automation result = automationVersion.restore(parameters);
```

### Parameters

* `id` (Long): Required - Automation Version ID.
