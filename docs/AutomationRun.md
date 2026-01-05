# Files.Models.AutomationRun

## Example AutomationRun Object

```
{
  "id": 1,
  "automation_id": 1,
  "workspace_id": 1,
  "completed_at": "2000-01-01T01:00:00Z",
  "created_at": "2000-01-01T01:00:00Z",
  "retry_at": "2000-01-01T01:00:00Z",
  "retried_at": "2000-01-01T01:00:00Z",
  "retried_in_run_id": 1,
  "retry_of_run_id": 1,
  "runtime": 1.0,
  "status": "success",
  "successful_operations": 1,
  "failed_operations": 1,
  "status_messages_url": "https://www.example.com/log_file.txt"
}
```

* `id` / `id`  (int64): ID.
* `automation_id` / `automationId`  (int64): ID of the associated Automation.
* `workspace_id` / `workspaceId`  (int64): Workspace ID.
* `completed_at` / `completedAt`  (date-time): Automation run completion/failure date/time.
* `created_at` / `createdAt`  (date-time): Automation run start date/time.
* `retry_at` / `retryAt`  (date-time): If set, this automation will be retried at this date/time due to `failure` or `partial_failure`.
* `retried_at` / `retriedAt`  (date-time): If set, this Automation run was retried due to `failure` or `partial_failure`.
* `retried_in_run_id` / `retriedInRunId`  (int64): ID of the run that is or will be retrying this run.
* `retry_of_run_id` / `retryOfRunId`  (int64): ID of the original run that this run is retrying.
* `runtime` / `runtime`  (double): Automation run runtime.
* `status` / `status`  (string): The success status of the AutomationRun. One of `running`, `success`, `partial_failure`, or `failure`.
* `successful_operations` / `successfulOperations`  (int64): Count of successful operations.
* `failed_operations` / `failedOperations`  (int64): Count of failed operations.
* `status_messages_url` / `statusMessagesUrl`  (string): Link to status messages log file.


---

## List Automation Runs

```
ListIterator<AutomationRun> automationRun = AutomationRun.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `automation_id`, `created_at` or `status`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `status`, `workspace_id` or `automation_id`. Valid field combinations are `[ workspace_id, status ]`, `[ automation_id, status ]`, `[ workspace_id, automation_id ]` or `[ workspace_id, automation_id, status ]`.
* `automation_id` (Long): Required - ID of the associated Automation.


---

## Show Automation Run

```
AutomationRun automationRun = AutomationRun.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Automation Run ID.
