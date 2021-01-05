# Files.Models.FileAction

## Example FileAction Object

```
{
  "status": "enqueued",
  "file_migration_id": "123"
}
```

* `status` / `status`  (string): Status of file operation. Possible values: completed, enqueued.
* `file_migration_id` / `fileMigrationId`  (int64): If status is enqueued, this is the id of the FileMigration to check for status updates.


---

## Copy file/folder

```
FileAction fileAction = FileAction.copy(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `path` (String): Required - Path to operate on.
* `destination` (String): Required - Copy destination path.
* `structure` (Boolean): Copy structure only?


---

## Move file/folder

```
FileAction fileAction = FileAction.move(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `path` (String): Required - Path to operate on.
* `destination` (String): Required - Move destination path.


---

## Begin file upload

```
FileAction fileAction = FileAction.beginUpload(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `path` (String): Required - Path to operate on.
* `mkdir_parents` (Boolean): Create parent directories if they do not exist?
* `part` (Long): Part if uploading a part.
* `parts` (Long): How many parts to fetch?
* `ref` (String): 
* `restart` (Long): File byte offset to restart from.
* `with_rename` (Boolean): Allow file rename instead of overwrite?


---

## Copy file/folder

```
FileAction fileAction = FileAction.ListFor(path)[0];

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("destination", "destination");
parameters.put("structure", true);

FileAction.Copy(parameters);
```

### Parameters

* `path` (String): Required - Path to operate on.
* `destination` (String): Required - Copy destination path.
* `structure` (Boolean): Copy structure only?


---

## Move file/folder

```
FileAction fileAction = FileAction.ListFor(path)[0];

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("destination", "destination");

FileAction.Move(parameters);
```

### Parameters

* `path` (String): Required - Path to operate on.
* `destination` (String): Required - Move destination path.


---

## Begin file upload

```
FileAction fileAction = FileAction.ListFor(path)[0];

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("mkdir_parents", true);
parameters.put("part", 1);
parameters.put("parts", 1);
parameters.put("ref", "upload-1");
parameters.put("restart", 1);
parameters.put("with_rename", true);

FileAction.BeginUpload(parameters);
```

### Parameters

* `path` (String): Required - Path to operate on.
* `mkdir_parents` (Boolean): Create parent directories if they do not exist?
* `part` (Long): Part if uploading a part.
* `parts` (Long): How many parts to fetch?
* `ref` (String): 
* `restart` (Long): File byte offset to restart from.
* `with_rename` (Boolean): Allow file rename instead of overwrite?
