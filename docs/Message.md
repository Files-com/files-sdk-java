# Files.Models.Message

## Example Message Object

```
{
  "id": 1,
  "subject": "Files.com Account Upgrade",
  "body": "We should upgrade our Files.com account!",
  "comments": {
    "id": 1,
    "body": "What a great idea, thank you!",
    "reactions": {
      "id": 1,
      "emoji": "👍"
    }
  }
}
```

* `id` / `id`  (int64): Message ID
* `subject` / `subject`  (string): Message subject.
* `body` / `body`  (string): Message body.
* `comments` / `comments` : Comments.
* `user_id` / `userId`  (int64): User ID.  Provide a value of `0` to operate the current session's user.
* `project_id` / `projectId`  (int64): Project to which the message should be attached.


---

## List Messages

```
List<Message> message = Message.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via either the X-Files-Cursor-Next header or the X-Files-Cursor-Prev header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `project_id` (Long): Required - Project for which to return messages.


---

## Show Message

```
List<Message> message = Message.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Message ID.


---

## Create Message

```
Message message = Message.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `project_id` (Long): Required - Project to which the message should be attached.
* `subject` (String): Required - Message subject.
* `body` (String): Required - Message body.


---

## Update Message

```
Message message = Message.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Message ID.
* `project_id` (Long): Required - Project to which the message should be attached.
* `subject` (String): Required - Message subject.
* `body` (String): Required - Message body.


---

## Delete Message

```
Message message = Message.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Message ID.


---

## Update Message

```
Message message = Message.List()[0];

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("project_id", 1);
parameters.put("subject", "Files.com Account Upgrade");
parameters.put("body", "We should upgrade our Files.com account!");

Message.Update(parameters);
```

### Parameters

* `id` (Long): Required - Message ID.
* `project_id` (Long): Required - Project to which the message should be attached.
* `subject` (String): Required - Message subject.
* `body` (String): Required - Message body.


---

## Delete Message

```
Message message = Message.List()[0];

HashMap<String, Object> parameters = new HashMap<>();


Message.Delete
```

### Parameters

* `id` (Long): Required - Message ID.
