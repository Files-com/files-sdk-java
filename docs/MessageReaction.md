# Files.Models.MessageReaction

## Example MessageReaction Object

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

## List Message Reactions

```
List<MessageReaction> messageReaction = MessageReaction.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `page` (Long): Current page number.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `action` (String): Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
* `cursor` (String): Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `message_id` (Long): Required - Message to return reactions for.


---

## Show Message Reaction

```
List<MessageReaction> messageReaction = MessageReaction.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Message Reaction ID.


---

## Create Message Reaction

```
MessageReaction messageReaction = MessageReaction.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `emoji` (String): Required - Emoji to react with.


---

## Delete Message Reaction

```
MessageReaction messageReaction = MessageReaction.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Message Reaction ID.


---

## Delete Message Reaction

```
MessageReaction messageReaction = MessageReaction.ListFor(path)[0];

HashMap<String, Object> parameters = new HashMap<>();


MessageReaction.Delete
```

### Parameters

* `id` (Long): Required - Message Reaction ID.
