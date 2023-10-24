# Files.Models.ActionNotificationExport

## Example ActionNotificationExport Object

```
{
  "id": 1,
  "export_version": "example",
  "start_at": "2000-01-01T01:00:00Z",
  "end_at": "2000-01-01T01:00:00Z",
  "status": "ready",
  "query_path": "MyFile.txt",
  "query_folder": "MyFolder",
  "query_message": "Connection Refused",
  "query_request_method": "GET",
  "query_request_url": "http://example.com/webhook",
  "query_status": "200",
  "query_success": true,
  "results_url": "https://files.com/action_notification_results.csv"
}
```

* `id` / `id`  (int64): History Export ID
* `export_version` / `exportVersion`  (string): Version of the underlying records for the export.
* `start_at` / `startAt`  (date-time): Start date/time of export range.
* `end_at` / `endAt`  (date-time): End date/time of export range.
* `status` / `status`  (string): Status of export.  Valid values: `building`, `ready`, or `failed`
* `query_path` / `queryPath`  (string): Return notifications that were triggered by actions on this specific path.
* `query_folder` / `queryFolder`  (string): Return notifications that were triggered by actions in this folder.
* `query_message` / `queryMessage`  (string): Error message associated with the request, if any.
* `query_request_method` / `queryRequestMethod`  (string): The HTTP request method used by the webhook.
* `query_request_url` / `queryRequestUrl`  (string): The target webhook URL.
* `query_status` / `queryStatus`  (string): The HTTP status returned from the server in response to the webhook request.
* `query_success` / `querySuccess`  (boolean): true if the webhook request succeeded (i.e. returned a 200 or 204 response status). false otherwise.
* `results_url` / `resultsUrl`  (string): If `status` is `ready`, this will be a URL where all the results can be downloaded at once as a CSV.
* `user_id` / `userId`  (int64): User ID.  Provide a value of `0` to operate the current session's user.


---

## Show Action Notification Export

```
ActionNotificationExport actionNotificationExport = ActionNotificationExport.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Action Notification Export ID.


---

## Create Action Notification Export

```
ActionNotificationExport actionNotificationExport = ActionNotificationExport.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `start_at` (String): Start date/time of export range.
* `end_at` (String): End date/time of export range.
* `query_message` (String): Error message associated with the request, if any.
* `query_request_method` (String): The HTTP request method used by the webhook.
* `query_request_url` (String): The target webhook URL.
* `query_status` (String): The HTTP status returned from the server in response to the webhook request.
* `query_success` (Boolean): true if the webhook request succeeded (i.e. returned a 200 or 204 response status). false otherwise.
* `query_path` (String): Return notifications that were triggered by actions on this specific path.
* `query_folder` (String): Return notifications that were triggered by actions in this folder.
