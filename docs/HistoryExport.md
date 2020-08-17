# Files.Models.HistoryExport

## Example HistoryExport Object

```
{
  "id": 1,
  "start_at": "2000-01-01T01:00:00Z",
  "end_at": "2000-01-01T01:00:00Z",
  "status": "ready",
  "query_action": "read",
  "query_interface": "ftp",
  "query_user_id": "1",
  "query_file_id": "1",
  "query_parent_id": "1",
  "query_path": "MyFile.txt",
  "query_folder": "Folder",
  "query_src": "SrcFolder",
  "query_destination": "DestFolder",
  "query_ip": "127.0.0.1",
  "query_username": "jerry",
  "query_failure_type": "bad_password",
  "query_target_id": "1",
  "query_target_name": "full",
  "query_target_permission": "full",
  "query_target_user_id": "1",
  "query_target_username": "jerry",
  "query_target_platform": "windows",
  "query_target_permission_set": "desktop_app",
  "results_url": "https://files.com/history_results.csv"
}
```

* `id` / `id`  (int64): History Export ID
* `start_at` / `startAt`  (date-time): Start date/time of export range.
* `end_at` / `endAt`  (date-time): End date/time of export range.
* `status` / `status`  (string): Status of export.  Will be: `building` or `ready`
* `query_action` / `queryAction`  (string): Filter results by this this action type. Valid values: `create`, `read`, `update`, `destroy`, `move`, `login`, `failedlogin`, `copy`, `user_create`, `user_update`, `user_destroy`, `group_create`, `group_update`, `group_destroy`, `permission_create`, `permission_destroy`, `api_key_create`, `api_key_update`, `api_key_destroy`
* `query_interface` / `queryInterface`  (string): Filter results by this this interface type. Valid values: `web`, `ftp`, `robot`, `jsapi`, `webdesktopapi`, `sftp`, `dav`, `desktop`, `restapi`, `scim`
* `query_user_id` / `queryUserId`  (string): Return results that are actions performed by the user indiciated by this User ID
* `query_file_id` / `queryFileId`  (string): Return results that are file actions related to the file indicated by this File ID
* `query_parent_id` / `queryParentId`  (string): Return results that are file actions inside the parent folder specified by this folder ID
* `query_path` / `queryPath`  (string): Return results that are file actions related to this path.
* `query_folder` / `queryFolder`  (string): Return results that are file actions related to files or folders inside this folder path.
* `query_src` / `querySrc`  (string): Return results that are file moves originating from this path.
* `query_destination` / `queryDestination`  (string): Return results that are file moves with this path as destination.
* `query_ip` / `queryIp`  (string): Filter results by this IP address.
* `query_username` / `queryUsername`  (string): Filter results by this username.
* `query_failure_type` / `queryFailureType`  (string): If searching for Histories about login failures, this parameter restricts results to failures of this specific type.  Valid values: `expired_trial`, `account_overdue`, `locked_out`, `ip_mismatch`, `password_mismatch`, `site_mismatch`, `username_not_found`, `none`, `no_ftp_permission`, `no_web_permission`, `no_directory`, `errno_enoent`, `no_sftp_permission`, `no_dav_permission`, `no_restapi_permission`, `key_mismatch`, `region_mismatch`, `expired_access`, `desktop_ip_mismatch`, `desktop_api_key_not_used_quickly_enough`, `disabled`
* `query_target_id` / `queryTargetId`  (string): If searching for Histories about specific objects (such as Users, or API Keys), this paremeter restricts results to objects that match this ID.
* `query_target_name` / `queryTargetName`  (string): If searching for Histories about Users, Groups or other objects with names, this parameter restricts results to objects with this name/username.
* `query_target_permission` / `queryTargetPermission`  (string): If searching for Histories about Permisisons, this parameter restricts results to permissions of this level.
* `query_target_user_id` / `queryTargetUserId`  (string): If searching for Histories about API keys, this parameter restricts results to API keys created by/for this user ID.
* `query_target_username` / `queryTargetUsername`  (string): If searching for Histories about API keys, this parameter restricts results to API keys created by/for this username.
* `query_target_platform` / `queryTargetPlatform`  (string): If searching for Histories about API keys, this parameter restricts results to API keys associated with this platform.
* `query_target_permission_set` / `queryTargetPermissionSet`  (string): If searching for Histories about API keys, this parameter restricts results to API keys with this permission set.
* `results_url` / `resultsUrl`  (string): If `status` is `ready` and the query succeeded, this will be a URL where all the results can be downloaded at once as a CSV.
* `user_id` / `userId`  (int64): User ID.  Provide a value of `0` to operate the current session's user.


---

## Show History Export

```
List<HistoryExport> historyExport = HistoryExport.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - History Export ID.


---

## Create History Export

```
HistoryExport historyExport = HistoryExport.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `start_at` (String): Start date/time of export range.
* `end_at` (String): End date/time of export range.
* `query_action` (String): Filter results by this this action type. Valid values: `create`, `read`, `update`, `destroy`, `move`, `login`, `failedlogin`, `copy`, `user_create`, `user_update`, `user_destroy`, `group_create`, `group_update`, `group_destroy`, `permission_create`, `permission_destroy`, `api_key_create`, `api_key_update`, `api_key_destroy`
* `query_interface` (String): Filter results by this this interface type. Valid values: `web`, `ftp`, `robot`, `jsapi`, `webdesktopapi`, `sftp`, `dav`, `desktop`, `restapi`, `scim`
* `query_user_id` (String): Return results that are actions performed by the user indiciated by this User ID
* `query_file_id` (String): Return results that are file actions related to the file indicated by this File ID
* `query_parent_id` (String): Return results that are file actions inside the parent folder specified by this folder ID
* `query_path` (String): Return results that are file actions related to this path.
* `query_folder` (String): Return results that are file actions related to files or folders inside this folder path.
* `query_src` (String): Return results that are file moves originating from this path.
* `query_destination` (String): Return results that are file moves with this path as destination.
* `query_ip` (String): Filter results by this IP address.
* `query_username` (String): Filter results by this username.
* `query_failure_type` (String): If searching for Histories about login failures, this parameter restricts results to failures of this specific type.  Valid values: `expired_trial`, `account_overdue`, `locked_out`, `ip_mismatch`, `password_mismatch`, `site_mismatch`, `username_not_found`, `none`, `no_ftp_permission`, `no_web_permission`, `no_directory`, `errno_enoent`, `no_sftp_permission`, `no_dav_permission`, `no_restapi_permission`, `key_mismatch`, `region_mismatch`, `expired_access`, `desktop_ip_mismatch`, `desktop_api_key_not_used_quickly_enough`, `disabled`
* `query_target_id` (String): If searching for Histories about specific objects (such as Users, or API Keys), this paremeter restricts results to objects that match this ID.
* `query_target_name` (String): If searching for Histories about Users, Groups or other objects with names, this parameter restricts results to objects with this name/username.
* `query_target_permission` (String): If searching for Histories about Permisisons, this parameter restricts results to permissions of this level.
* `query_target_user_id` (String): If searching for Histories about API keys, this parameter restricts results to API keys created by/for this user ID.
* `query_target_username` (String): If searching for Histories about API keys, this parameter restricts results to API keys created by/for this username.
* `query_target_platform` (String): If searching for Histories about API keys, this parameter restricts results to API keys associated with this platform.
* `query_target_permission_set` (String): If searching for Histories about API keys, this parameter restricts results to API keys with this permission set.
