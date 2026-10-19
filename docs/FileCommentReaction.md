# Files.Models.FileCommentReaction

## Example FileCommentReaction Object

```
{
  "id": 1,
  "emoji": "👍"
}
```

* `id` / `id`  (int64): Reaction ID
* `emoji` / `emoji`  (string): Emoji used in the reaction.
* `user_id` / `userId`  (int64): User ID.  Provide a value of `0` to operate the current session's user.
* `file_comment_id` / `fileCommentId`  (int64): ID of file comment to attach reaction to.


---

## Create File Comment Reaction

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("user_id", 1L);
parameters.put("file_comment_id", 1L);
parameters.put("emoji", "emoji");
FileCommentReaction fileCommentReaction = FileCommentReaction.create(parameters, null);
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `file_comment_id` (Long): Required - ID of file comment to attach reaction to.
* `emoji` (String): Required - Emoji to react with.


---

## Delete File Comment Reaction

```java
HashMap<String, Object> parameters = new HashMap<>();
FileCommentReaction.delete(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - File Comment Reaction ID.


---

## Delete File Comment Reaction

```java
HashMap<String, Object> attributes = new HashMap<>();
attributes.put("id", 1L);
FileCommentReaction fileCommentReaction = new FileCommentReaction(attributes);
HashMap<String, Object> parameters = new HashMap<>();
fileCommentReaction.delete(parameters);
```

### Parameters

* `id` (Long): Required - File Comment Reaction ID.
