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

```
FileCommentReaction fileCommentReaction = FileCommentReaction.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `file_comment_id` (Long): Required - ID of file comment to attach reaction to.
* `emoji` (String): Required - Emoji to react with.


---

## Delete File Comment Reaction

```
void fileCommentReaction = FileCommentReaction.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - File Comment Reaction ID.


---

## Delete File Comment Reaction

```
HashMap<Object, String> attributes = new HashMap<>();
void fileCommentReaction = new FileCommentReaction(attributes);

HashMap<String, Object> parameters = new HashMap<>();


FileCommentReaction.Delete
```

### Parameters

* `id` (Long): Required - File Comment Reaction ID.
