# Files.Models.FileMigration

## Example FileMigration Object

```
{
  "id": 1,
  "path": "MyFolder",
  "dest_path": "MyFolder",
  "files_moved": 1,
  "files_total": 1,
  "operation": "move",
  "region": "USA",
  "status": "complete",
  "log_url": "https://www.example.com/log_file"
}
```

* `id` / `id`  (int64): File migration ID
* `path` / `path`  (string): Source path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `dest_path` / `destPath`  (string): Destination path
* `files_moved` / `filesMoved`  (int64): Number of files processed
* `files_total` / `filesTotal`  (int64): Deprecated: used to return a count of the applicable files.  Currently returns 0 always.  On remote servers, it is not possible to reliably determine the number of affected files for every migration operation.
* `operation` / `operation`  (string): The type of operation
* `region` / `region`  (string): Region
* `status` / `status`  (string): Status
* `log_url` / `logUrl`  (string): Link to download the log file for this migration.


---

## Show File Migration

```
List<FileMigration> fileMigration = FileMigration.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - File Migration ID.
