# Files.Models.SettingsChange

## Example SettingsChange Object

```
{
  "changes": [
    "example"
  ],
  "created_at": "2000-01-01T01:00:00Z",
  "user_id": 1,
  "api_key_id": 1,
  "user_is_files_support": true,
  "user_is_from_parent_site": true,
  "username": "some_user"
}
```

* `changes` / `changes`  (array(string)): Markdown-formatted change messages.
* `created_at` / `createdAt`  (date-time): The time this change was made
* `user_id` / `userId`  (int64): The user id responsible for this change
* `api_key_id` / `apiKeyId`  (int64): The API key id responsible for this change
* `user_is_files_support` / `userIsFilesSupport`  (boolean): true if this change was performed by Files.com support.
* `user_is_from_parent_site` / `userIsFromParentSite`  (boolean): true if this change was performed by a user on a parent site.
* `username` / `username`  (string): The username of the user responsible for this change


---

## List Settings Changes

```
ListIterator<SettingsChange> settingsChange = SettingsChange.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`, `api_key_id` or `user_id`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `api_key_id` and `user_id`.


---

## Create an export CSV of Settings Change resources

```
Export settingsChange = SettingsChange.createExport(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`, `api_key_id` or `user_id`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `api_key_id` and `user_id`.
