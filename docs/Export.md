# Files.Models.Export

## Example Export Object

```
{
  "id": 1,
  "export_status": "example",
  "export_type": "example",
  "export_rows": 1,
  "download_uri": "example",
  "message": "example"
}
```

* `id` / `id`  (int64): ID for this Export
* `export_status` / `exportStatus`  (string): Status of the Export
* `export_type` / `exportType`  (string): Type of data being exported
* `export_rows` / `exportRows`  (int64): Number of rows exported
* `download_uri` / `downloadUri`  (string): Link to download Export file.
* `message` / `message`  (string): Export message
* `user_id` / `userId`  (int64): User ID.  Provide a value of `0` to operate the current session's user.
* `sort_by` / `sortBy`  (object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `export_status` and `export_type`.
* `filter` / `filter`  (object): If set, return records where the specified field is equal to the supplied value. Valid fields are `export_status` and `export_type`.
* `filter_prefix` / `filterPrefix`  (object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `export_type`.


---

## List Exports

```
ListIterator<Export> export = Export.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `export_status` and `export_type`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `export_status` and `export_type`.
* `filter_prefix` (Map<String, String>): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `export_type`.


---

## Show Export

```
Export export = Export.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Export ID.


---

## Create Export Export

```
Export export = Export.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `export_status` and `export_type`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `export_status` and `export_type`.
* `filter_prefix` (Map<String, String>): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `export_type`.
