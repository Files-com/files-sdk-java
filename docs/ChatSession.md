# Files.Models.ChatSession

## Example ChatSession Object

```
{
  "id": "example",
  "user_id": 1,
  "workspace_id": 1,
  "last_active_at": "2000-01-01T01:00:00Z",
  "created_at": "2000-01-01T01:00:00Z",
  "messages": [
    {
      "id": 1,
      "role": "example",
      "content": "example",
      "created_at": "2000-01-01T01:00:00Z"
    }
  ]
}
```

* `id` / `id`  (string): Chat Session ID.
* `user_id` / `userId`  (int64): User ID.
* `workspace_id` / `workspaceId`  (int64): Workspace ID. `0` means the default workspace.
* `last_active_at` / `lastActiveAt`  (date-time): Most recent chat activity date/time.
* `created_at` / `createdAt`  (date-time): Chat session creation date/time.
* `messages` / `messages`  (ChatMessage[]): Visible conversation messages in this chat session. For performance reasons, this is not provided when listing chat sessions.


---

## List Chat Sessions

```
ListIterator<ChatSession> chatSession = ChatSession.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).


---

## Show Chat Session

```
ChatSession chatSession = ChatSession.find(
    String id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (String): Required - Chat Session ID.
