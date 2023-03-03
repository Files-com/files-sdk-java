# Files.Models.MessageComment

## Example MessageComment Object

```
{
  "id": 1,
  "body": "What a great idea, thank you!",
  "reactions": [
    null
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
* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
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
MessageComment messageComment = MessageComment.List()[0];

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
MessageComment messageComment = MessageComment.List()[0];

HashMap<String, Object> parameters = new HashMap<>();


MessageComment.Delete
```

### Parameters

* `id` (Long): Required - Message Comment ID.
