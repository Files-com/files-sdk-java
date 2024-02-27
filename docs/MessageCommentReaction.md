# Files.Models.MessageCommentReaction

## Example MessageCommentReaction Object

```
{
  "id": 1,
  "emoji": "👍"
}
```

* `id` / `id`  (int64): Reaction ID
* `emoji` / `emoji`  (string): Emoji used in the reaction.
* `user_id` / `userId`  (int64): User ID.  Provide a value of `0` to operate the current session's user.


---

## List Message Comment Reactions

```
ListIterator<MessageCommentReaction> messageCommentReaction = MessageCommentReaction.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `message_comment_id` (Long): Required - Message comment to return reactions for.


---

## Show Message Comment Reaction

```
MessageCommentReaction messageCommentReaction = MessageCommentReaction.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Message Comment Reaction ID.


---

## Create Message Comment Reaction

```
MessageCommentReaction messageCommentReaction = MessageCommentReaction.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `emoji` (String): Required - Emoji to react with.


---

## Delete Message Comment Reaction

```
void messageCommentReaction = MessageCommentReaction.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Message Comment Reaction ID.


---

## Delete Message Comment Reaction

```
MessageCommentReaction messageCommentReaction = MessageCommentReaction.List()[0];

HashMap<String, Object> parameters = new HashMap<>();


MessageCommentReaction.Delete
```

### Parameters

* `id` (Long): Required - Message Comment Reaction ID.
