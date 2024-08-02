# Files.Models.HistoryExportResult

## Example HistoryExportResult Object

```
{
  "id": 1,
  "created_at": 1,
  "created_at_iso8601": "example",
  "user_id": 1,
  "file_id": 1,
  "parent_id": 1,
  "path": "MyFile.txt",
  "folder": "Folder",
  "src": "SrcFolder",
  "destination": "DestFolder",
  "ip": "127.0.0.1",
  "username": "jerry",
  "user_is_from_parent_site": true,
  "action": "read",
  "failure_type": "bad_password",
  "interface": "ftp",
  "target_id": 1,
  "target_name": "full",
  "target_permission": "full",
  "target_recursive": true,
  "target_expires_at": 1,
  "target_expires_at_iso8601": "example",
  "target_permission_set": "desktop_app",
  "target_platform": "windows",
  "target_username": "jerry",
  "target_user_id": 1
}
```

* `id` / `id`  (int64): Action ID
* `created_at` / `createdAt`  (int64): When the action happened
* `created_at_iso8601` / `createdAtIso8601`  (string): When the action happened, in ISO8601 format.
* `user_id` / `userId`  (int64): User ID
* `file_id` / `fileId`  (int64): File ID related to the action
* `parent_id` / `parentId`  (int64): ID of the parent folder
* `path` / `path`  (string): Path of the related action. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `folder` / `folder`  (string): Folder in which the action occurred
* `src` / `src`  (string): File move originated from this path
* `destination` / `destination`  (string): File moved to this destination folder
* `ip` / `ip`  (string): Client IP that performed the action
* `username` / `username`  (string): Username of the user that performed the action
* `user_is_from_parent_site` / `userIsFromParentSite`  (boolean): true if this change was performed by a user on a parent site.
* `action` / `action`  (string): What action was taken. Valid values: `create`, `read`, `update`, `destroy`, `move`, `login`, `failedlogin`, `copy`, `user_create`, `user_update`, `user_destroy`, `group_create`, `group_update`, `group_destroy`, `permission_create`, `permission_destroy`, `api_key_create`, `api_key_update`, `api_key_destroy`
* `failure_type` / `failureType`  (string): The type of login failure, if applicable.  Valid values: `expired_trial`, `account_overdue`, `locked_out`, `ip_mismatch`, `password_mismatch`, `site_mismatch`, `username_not_found`, `none`, `no_ftp_permission`, `no_web_permission`, `no_directory`, `errno_enoent`, `no_sftp_permission`, `no_dav_permission`, `no_restapi_permission`, `key_mismatch`, `region_mismatch`, `expired_access`, `desktop_ip_mismatch`, `desktop_api_key_not_used_quickly_enough`, `disabled`, `country_mismatch`, `insecure_ftp`, `insecure_cipher`, `rate_limited`
* `interface` / `interfaceName`  (string): Inteface through which the action was taken. Valid values: `web`, `ftp`, `robot`, `jsapi`, `webdesktopapi`, `sftp`, `dav`, `desktop`, `restapi`, `scim`, `office`, `mobile`, `as2`, `inbound_email`, `remote`
* `target_id` / `targetId`  (int64): ID of the object (such as Users, or API Keys) on which the action was taken
* `target_name` / `targetName`  (string): Name of the User, Group or other object with a name related to this action
* `target_permission` / `targetPermission`  (string): Permission level of the action
* `target_recursive` / `targetRecursive`  (boolean): Whether or not the action was recursive
* `target_expires_at` / `targetExpiresAt`  (int64): If searching for Histories about API keys, this is when the API key will expire. Represented as a Unix timestamp.
* `target_expires_at_iso8601` / `targetExpiresAtIso8601`  (string): If searching for Histories about API keys, this is when the API key will expire. Represented in ISO8601 format.
* `target_permission_set` / `targetPermissionSet`  (string): If searching for Histories about API keys, this represents the permission set of the associated  API key
* `target_platform` / `targetPlatform`  (string): If searching for Histories about API keys, this is the platform on which the action was taken
* `target_username` / `targetUsername`  (string): If searching for Histories about API keys, this is the username on which the action was taken
* `target_user_id` / `targetUserId`  (int64): If searching for Histories about API keys, this is the User ID on which the action was taken


---

## List History Export Results

```
ListIterator<HistoryExportResult> historyExportResult = HistoryExportResult.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `action` (String): 
* `page` (Long): 
* `history_export_id` (Long): Required - ID of the associated history export.
