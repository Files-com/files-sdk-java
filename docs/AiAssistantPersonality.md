# Files.Models.AiAssistantPersonality

## Example AiAssistantPersonality Object

```
{
  "id": 1,
  "workspace_id": 1,
  "system_prompt": "Respond as a concise operations assistant.",
  "use_by_default": true,
  "apply_to_all_workspaces": true,
  "created_at": "2000-01-01T01:00:00Z",
  "updated_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): AI Assistant Personality ID.
* `workspace_id` / `workspaceId`  (int64): Workspace ID. `0` means the default workspace.
* `system_prompt` / `systemPrompt`  (string): System prompt injected into the in-app AI Assistant.
* `use_by_default` / `useByDefault`  (boolean): Whether this personality is the default personality for the Workspace.
* `apply_to_all_workspaces` / `applyToAllWorkspaces`  (boolean): If true, this default-workspace personality can apply to users in all workspaces.
* `created_at` / `createdAt`  (date-time): Creation time.
* `updated_at` / `updatedAt`  (date-time): Last update time.


---

## List Ai Assistant Personalities

```
ListIterator<AiAssistantPersonality> aiAssistantPersonality = AiAssistantPersonality.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id` and `id`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id`.


---

## Show Ai Assistant Personality

```
AiAssistantPersonality aiAssistantPersonality = AiAssistantPersonality.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Ai Assistant Personality ID.


---

## Create Ai Assistant Personality

```
AiAssistantPersonality aiAssistantPersonality = AiAssistantPersonality.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `apply_to_all_workspaces` (Boolean): If true, this default-workspace personality can apply to users in all workspaces.
* `system_prompt` (String): Required - System prompt injected into the in-app AI Assistant.
* `use_by_default` (Boolean): Whether this personality is the default personality for the Workspace.
* `workspace_id` (Long): Workspace ID. `0` means the default workspace.


---

## Update Ai Assistant Personality

```
AiAssistantPersonality aiAssistantPersonality = AiAssistantPersonality.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Ai Assistant Personality ID.
* `apply_to_all_workspaces` (Boolean): If true, this default-workspace personality can apply to users in all workspaces.
* `system_prompt` (String): System prompt injected into the in-app AI Assistant.
* `use_by_default` (Boolean): Whether this personality is the default personality for the Workspace.
* `workspace_id` (Long): Workspace ID. `0` means the default workspace.


---

## Delete Ai Assistant Personality

```
void aiAssistantPersonality = AiAssistantPersonality.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Ai Assistant Personality ID.


---

## Update Ai Assistant Personality

```
AiAssistantPersonality aiAssistantPersonality = AiAssistantPersonality.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("apply_to_all_workspaces", false);
parameters.put("system_prompt", "Respond as a concise operations assistant.");
parameters.put("use_by_default", false);
parameters.put("workspace_id", 0);

aiAssistantPersonality.update(parameters);
```

### Parameters

* `id` (Long): Required - Ai Assistant Personality ID.
* `apply_to_all_workspaces` (Boolean): If true, this default-workspace personality can apply to users in all workspaces.
* `system_prompt` (String): System prompt injected into the in-app AI Assistant.
* `use_by_default` (Boolean): Whether this personality is the default personality for the Workspace.
* `workspace_id` (Long): Workspace ID. `0` means the default workspace.


---

## Delete Ai Assistant Personality

```
AiAssistantPersonality aiAssistantPersonality = AiAssistantPersonality.find(id);

HashMap<String, Object> parameters = new HashMap<>();

aiAssistantPersonality.delete(parameters);
```

### Parameters

* `id` (Long): Required - Ai Assistant Personality ID.
