# Files.Models.MessageComment

## Example MessageComment Object

```
{
  "id": 1,
  "body": "What a great idea, thank you!",
  "reactions": [

  ]
}
```

* `id` / `id`  (int64): Message Comment ID
* `body` / `body`  (string): Comment body.
* `reactions` / `reactions`  (array): Reactions to this comment.
* `user_id` / `userId`  (int64): User ID.  Provide a value of `0` to operate the current session's user.


---

## List Message Comments

```
List<MessageComment> messageComment = MessageComment.list(
    
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
* `message_id` (Long): Required - Message comment to return comments for.


---

## Show Message Comment

```
List<MessageComment> messageComment = MessageComment.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Message Comment ID.


---

## Create Message Comment

```
MessageComment messageComment = MessageComment.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `body` (String): Required - Comment body.


---

## Update Message Comment

```
MessageComment messageComment = MessageComment.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Message Comment ID.
* `body` (String): Required - Comment body.


---

## Delete Message Comment

```
MessageComment messageComment = MessageComment.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Message Comment ID.


---

## Update Message Comment

```
MessageComment messageComment = MessageComment.ListFor(path)[0];

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("body", "body");

MessageComment.Update(parameters);
```

### Parameters

* `id` (Long): Required - Message Comment ID.
* `body` (String): Required - Comment body.


---

## Delete Message Comment

```
MessageComment messageComment = MessageComment.ListFor(path)[0];

HashMap<String, Object> parameters = new HashMap<>();


MessageComment.Delete
```

### Parameters

* `id` (Long): Required - Message Comment ID.