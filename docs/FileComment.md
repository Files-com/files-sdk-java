# Files.Models.FileComment

## Example FileComment Object

```
{
  "id": 1,
  "body": "What a great file!",
  "reactions": [
    {
      "id": 1,
      "emoji": "👍"
    }
  ]
}
```

* `id` / `id`  (int64): File Comment ID
* `body` / `body`  (string): Comment body.
* `reactions` / `reactions`  (FileCommentReaction[]): Reactions to this comment.
* `path` / `path`  (string): File path.


---

## List File Comments by Path

```java
HashMap<String, Object> parameters = new HashMap<>();
ListIterator<FileComment> fileComments = FileComment.listFor("path", parameters, null);
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `path` (String): Required - Path to operate on.


---

## Create File Comment

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("body", "What a great file!");
parameters.put("path", "path");
FileComment fileComment = FileComment.create(parameters, null);
```

### Parameters

* `body` (String): Required - Comment body.
* `path` (String): Required - File path.


---

## Update File Comment

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("body", "What a great file!");
FileComment fileComment = FileComment.update(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - File Comment ID.
* `body` (String): Required - Comment body.


---

## Delete File Comment

```java
HashMap<String, Object> parameters = new HashMap<>();
FileComment.delete(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - File Comment ID.


---

## Update File Comment

```java
HashMap<String, Object> attributes = new HashMap<>();
attributes.put("id", 1L);
FileComment fileComment = new FileComment(attributes);
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("body", "What a great file!");
FileComment result = fileComment.update(parameters);
```

### Parameters

* `id` (Long): Required - File Comment ID.
* `body` (String): Required - Comment body.


---

## Delete File Comment

```java
HashMap<String, Object> attributes = new HashMap<>();
attributes.put("id", 1L);
FileComment fileComment = new FileComment(attributes);
HashMap<String, Object> parameters = new HashMap<>();
fileComment.delete(parameters);
```

### Parameters

* `id` (Long): Required - File Comment ID.
