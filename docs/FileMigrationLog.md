# Files.Models.FileMigrationLog

## Example FileMigrationLog Object

```
{
  "timestamp": "2000-01-01T01:00:00Z",
  "file_migration_id": 1,
  "dest_path": "example",
  "error_type": "example",
  "message": "example",
  "operation": "example",
  "path": "example",
  "status": "example"
}
```

* `timestamp` / `timestamp`  (date-time): Start Time of Action
* `file_migration_id` / `fileMigrationId`  (int64): File Migration ID
* `dest_path` / `destPath`  (string): Destination path, for moves and copies
* `error_type` / `errorType`  (string): Error type, if applicable
* `message` / `message`  (string): Message
* `operation` / `operation`  (string): Operation type
* `path` / `path`  (string): File path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `status` / `status`  (string): Status


---

## List File Migration Logs

```
ListIterator<FileMigrationLog> fileMigrationLog = FileMigrationLog.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `start_date`, `end_date`, `file_migration_id`, `operation`, `status` or `type`. Valid field combinations are `[ start_date ]`, `[ end_date ]`, `[ file_migration_id ]`, `[ operation ]`, `[ status ]`, `[ type ]`, `[ start_date, end_date ]`, `[ start_date, file_migration_id ]`, `[ start_date, operation ]`, `[ start_date, status ]`, `[ start_date, type ]`, `[ end_date, file_migration_id ]`, `[ end_date, operation ]`, `[ end_date, status ]`, `[ end_date, type ]`, `[ file_migration_id, operation ]`, `[ file_migration_id, status ]`, `[ file_migration_id, type ]`, `[ operation, status ]`, `[ operation, type ]`, `[ status, type ]`, `[ start_date, end_date, file_migration_id ]`, `[ start_date, end_date, operation ]`, `[ start_date, end_date, status ]`, `[ start_date, end_date, type ]`, `[ start_date, file_migration_id, operation ]`, `[ start_date, file_migration_id, status ]`, `[ start_date, file_migration_id, type ]`, `[ start_date, operation, status ]`, `[ start_date, operation, type ]`, `[ start_date, status, type ]`, `[ end_date, file_migration_id, operation ]`, `[ end_date, file_migration_id, status ]`, `[ end_date, file_migration_id, type ]`, `[ end_date, operation, status ]`, `[ end_date, operation, type ]`, `[ end_date, status, type ]`, `[ file_migration_id, operation, status ]`, `[ file_migration_id, operation, type ]`, `[ file_migration_id, status, type ]`, `[ operation, status, type ]`, `[ start_date, end_date, file_migration_id, operation ]`, `[ start_date, end_date, file_migration_id, status ]`, `[ start_date, end_date, file_migration_id, type ]`, `[ start_date, end_date, operation, status ]`, `[ start_date, end_date, operation, type ]`, `[ start_date, end_date, status, type ]`, `[ start_date, file_migration_id, operation, status ]`, `[ start_date, file_migration_id, operation, type ]`, `[ start_date, file_migration_id, status, type ]`, `[ start_date, operation, status, type ]`, `[ end_date, file_migration_id, operation, status ]`, `[ end_date, file_migration_id, operation, type ]`, `[ end_date, file_migration_id, status, type ]`, `[ end_date, operation, status, type ]`, `[ file_migration_id, operation, status, type ]`, `[ start_date, end_date, file_migration_id, operation, status ]`, `[ start_date, end_date, file_migration_id, operation, type ]`, `[ start_date, end_date, file_migration_id, status, type ]`, `[ start_date, end_date, operation, status, type ]`, `[ start_date, file_migration_id, operation, status, type ]` or `[ end_date, file_migration_id, operation, status, type ]`.
* `filter_prefix` (Map<String, String>): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `operation` and `status`. Valid field combinations are `[ start_date ]`, `[ end_date ]`, `[ file_migration_id ]`, `[ operation ]`, `[ status ]`, `[ type ]`, `[ start_date, end_date ]`, `[ start_date, file_migration_id ]`, `[ start_date, operation ]`, `[ start_date, status ]`, `[ start_date, type ]`, `[ end_date, file_migration_id ]`, `[ end_date, operation ]`, `[ end_date, status ]`, `[ end_date, type ]`, `[ file_migration_id, operation ]`, `[ file_migration_id, status ]`, `[ file_migration_id, type ]`, `[ operation, status ]`, `[ operation, type ]`, `[ status, type ]`, `[ start_date, end_date, file_migration_id ]`, `[ start_date, end_date, operation ]`, `[ start_date, end_date, status ]`, `[ start_date, end_date, type ]`, `[ start_date, file_migration_id, operation ]`, `[ start_date, file_migration_id, status ]`, `[ start_date, file_migration_id, type ]`, `[ start_date, operation, status ]`, `[ start_date, operation, type ]`, `[ start_date, status, type ]`, `[ end_date, file_migration_id, operation ]`, `[ end_date, file_migration_id, status ]`, `[ end_date, file_migration_id, type ]`, `[ end_date, operation, status ]`, `[ end_date, operation, type ]`, `[ end_date, status, type ]`, `[ file_migration_id, operation, status ]`, `[ file_migration_id, operation, type ]`, `[ file_migration_id, status, type ]`, `[ operation, status, type ]`, `[ start_date, end_date, file_migration_id, operation ]`, `[ start_date, end_date, file_migration_id, status ]`, `[ start_date, end_date, file_migration_id, type ]`, `[ start_date, end_date, operation, status ]`, `[ start_date, end_date, operation, type ]`, `[ start_date, end_date, status, type ]`, `[ start_date, file_migration_id, operation, status ]`, `[ start_date, file_migration_id, operation, type ]`, `[ start_date, file_migration_id, status, type ]`, `[ start_date, operation, status, type ]`, `[ end_date, file_migration_id, operation, status ]`, `[ end_date, file_migration_id, operation, type ]`, `[ end_date, file_migration_id, status, type ]`, `[ end_date, operation, status, type ]`, `[ file_migration_id, operation, status, type ]`, `[ start_date, end_date, file_migration_id, operation, status ]`, `[ start_date, end_date, file_migration_id, operation, type ]`, `[ start_date, end_date, file_migration_id, status, type ]`, `[ start_date, end_date, operation, status, type ]`, `[ start_date, file_migration_id, operation, status, type ]` or `[ end_date, file_migration_id, operation, status, type ]`.


---

## Create Export File Migration Log

```
Export fileMigrationLog = FileMigrationLog.createExport(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `start_date`, `end_date`, `file_migration_id`, `operation`, `status` or `type`. Valid field combinations are `[ start_date ]`, `[ end_date ]`, `[ file_migration_id ]`, `[ operation ]`, `[ status ]`, `[ type ]`, `[ start_date, end_date ]`, `[ start_date, file_migration_id ]`, `[ start_date, operation ]`, `[ start_date, status ]`, `[ start_date, type ]`, `[ end_date, file_migration_id ]`, `[ end_date, operation ]`, `[ end_date, status ]`, `[ end_date, type ]`, `[ file_migration_id, operation ]`, `[ file_migration_id, status ]`, `[ file_migration_id, type ]`, `[ operation, status ]`, `[ operation, type ]`, `[ status, type ]`, `[ start_date, end_date, file_migration_id ]`, `[ start_date, end_date, operation ]`, `[ start_date, end_date, status ]`, `[ start_date, end_date, type ]`, `[ start_date, file_migration_id, operation ]`, `[ start_date, file_migration_id, status ]`, `[ start_date, file_migration_id, type ]`, `[ start_date, operation, status ]`, `[ start_date, operation, type ]`, `[ start_date, status, type ]`, `[ end_date, file_migration_id, operation ]`, `[ end_date, file_migration_id, status ]`, `[ end_date, file_migration_id, type ]`, `[ end_date, operation, status ]`, `[ end_date, operation, type ]`, `[ end_date, status, type ]`, `[ file_migration_id, operation, status ]`, `[ file_migration_id, operation, type ]`, `[ file_migration_id, status, type ]`, `[ operation, status, type ]`, `[ start_date, end_date, file_migration_id, operation ]`, `[ start_date, end_date, file_migration_id, status ]`, `[ start_date, end_date, file_migration_id, type ]`, `[ start_date, end_date, operation, status ]`, `[ start_date, end_date, operation, type ]`, `[ start_date, end_date, status, type ]`, `[ start_date, file_migration_id, operation, status ]`, `[ start_date, file_migration_id, operation, type ]`, `[ start_date, file_migration_id, status, type ]`, `[ start_date, operation, status, type ]`, `[ end_date, file_migration_id, operation, status ]`, `[ end_date, file_migration_id, operation, type ]`, `[ end_date, file_migration_id, status, type ]`, `[ end_date, operation, status, type ]`, `[ file_migration_id, operation, status, type ]`, `[ start_date, end_date, file_migration_id, operation, status ]`, `[ start_date, end_date, file_migration_id, operation, type ]`, `[ start_date, end_date, file_migration_id, status, type ]`, `[ start_date, end_date, operation, status, type ]`, `[ start_date, file_migration_id, operation, status, type ]` or `[ end_date, file_migration_id, operation, status, type ]`.
* `filter_prefix` (Map<String, String>): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `operation` and `status`. Valid field combinations are `[ start_date ]`, `[ end_date ]`, `[ file_migration_id ]`, `[ operation ]`, `[ status ]`, `[ type ]`, `[ start_date, end_date ]`, `[ start_date, file_migration_id ]`, `[ start_date, operation ]`, `[ start_date, status ]`, `[ start_date, type ]`, `[ end_date, file_migration_id ]`, `[ end_date, operation ]`, `[ end_date, status ]`, `[ end_date, type ]`, `[ file_migration_id, operation ]`, `[ file_migration_id, status ]`, `[ file_migration_id, type ]`, `[ operation, status ]`, `[ operation, type ]`, `[ status, type ]`, `[ start_date, end_date, file_migration_id ]`, `[ start_date, end_date, operation ]`, `[ start_date, end_date, status ]`, `[ start_date, end_date, type ]`, `[ start_date, file_migration_id, operation ]`, `[ start_date, file_migration_id, status ]`, `[ start_date, file_migration_id, type ]`, `[ start_date, operation, status ]`, `[ start_date, operation, type ]`, `[ start_date, status, type ]`, `[ end_date, file_migration_id, operation ]`, `[ end_date, file_migration_id, status ]`, `[ end_date, file_migration_id, type ]`, `[ end_date, operation, status ]`, `[ end_date, operation, type ]`, `[ end_date, status, type ]`, `[ file_migration_id, operation, status ]`, `[ file_migration_id, operation, type ]`, `[ file_migration_id, status, type ]`, `[ operation, status, type ]`, `[ start_date, end_date, file_migration_id, operation ]`, `[ start_date, end_date, file_migration_id, status ]`, `[ start_date, end_date, file_migration_id, type ]`, `[ start_date, end_date, operation, status ]`, `[ start_date, end_date, operation, type ]`, `[ start_date, end_date, status, type ]`, `[ start_date, file_migration_id, operation, status ]`, `[ start_date, file_migration_id, operation, type ]`, `[ start_date, file_migration_id, status, type ]`, `[ start_date, operation, status, type ]`, `[ end_date, file_migration_id, operation, status ]`, `[ end_date, file_migration_id, operation, type ]`, `[ end_date, file_migration_id, status, type ]`, `[ end_date, operation, status, type ]`, `[ file_migration_id, operation, status, type ]`, `[ start_date, end_date, file_migration_id, operation, status ]`, `[ start_date, end_date, file_migration_id, operation, type ]`, `[ start_date, end_date, file_migration_id, status, type ]`, `[ start_date, end_date, operation, status, type ]`, `[ start_date, file_migration_id, operation, status, type ]` or `[ end_date, file_migration_id, operation, status, type ]`.
