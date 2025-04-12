# Files.Models.Message

## Example Message Object

```
{
  "id": 1,
  "subject": "Files.com Account Upgrade",
  "body": "We should upgrade our Files.com account!",
  "comments": [
    {
      "id": 1,
      "body": "What a great idea, thank you!",
      "reactions": [
        {
          "id": 1,
          "emoji": "👍"
        }
      ]
    }
  ]
}
```

* `id` / `id`  (int64): Message ID
* `subject` / `subject`  (string): Message subject.
* `body` / `body`  (string): Message body.
* `comments` / `comments`  (array(object)): Comments.
* `user_id` / `userId`  (int64): User ID.  Provide a value of `0` to operate the current session's user.
* `project_id` / `projectId`  (int64): Project to which the message should be attached.


---

## List Messages

```
ListIterator<Message> message = Message.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `project_id` (Long): Required - Project for which to return messages.


---

## Show Message

```
Message message = Message.find(
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
void message = Message.delete(
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
Message message = Message.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("project_id", 1);
parameters.put("subject", "Files.com Account Upgrade");
parameters.put("body", "We should upgrade our Files.com account!");

message.update(parameters);
```

### Parameters

* `id` (Long): Required - Message ID.
* `project_id` (Long): Required - Project to which the message should be attached.
* `subject` (String): Required - Message subject.
* `body` (String): Required - Message body.


---

## Delete Message

```
Message message = Message.find(id);

HashMap<String, Object> parameters = new HashMap<>();

message.delete(parameters);
```

### Parameters

* `id` (Long): Required - Message ID.
