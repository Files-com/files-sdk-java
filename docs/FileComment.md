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
* `reactions` / `reactions`  (array): Reactions to this comment.
* `path` / `path`  (string): File path.


---

## List File Comments by path

```
List<FileComment> fileComment = FileComment.listFor(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `path` (String): Required - Path to operate on.


---

## Create File Comment

```
FileComment fileComment = FileComment.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `body` (String): Required - Comment body.
* `path` (String): Required - File path.


---

## Update File Comment

```
FileComment fileComment = FileComment.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - File Comment ID.
* `body` (String): Required - Comment body.


---

## Delete File Comment

```
FileComment fileComment = FileComment.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - File Comment ID.


---

## Update File Comment

```
FileComment fileComment = FileComment.ListFor(path)[0];

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("body", "body");

FileComment.Update(parameters);
```

### Parameters

* `id` (Long): Required - File Comment ID.
* `body` (String): Required - Comment body.


---

## Delete File Comment

```
FileComment fileComment = FileComment.ListFor(path)[0];

HashMap<String, Object> parameters = new HashMap<>();


FileComment.Delete
```

### Parameters

* `id` (Long): Required - File Comment ID.
