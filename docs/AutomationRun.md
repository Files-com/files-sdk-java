# Files.Models.AutomationRun

## Example AutomationRun Object

```
{
  "automation_id": 1,
  "status": "success",
  "status_messages_url": "https://www.example.com/log_file.txt"
}
```

* `automation_id` / `automationId`  (int64): ID of the associated Automation.
* `status` / `status`  (string): The success status of the AutomationRun. One of `running`, `success`, `partial_failure`, or `failure`.
* `status_messages_url` / `statusMessagesUrl`  (string): Link to status messages log file.


---

## List Automation Runs

```
List<AutomationRun> automationRun = AutomationRun.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `automation_id` (Long): Required - ID of the associated Automation.
