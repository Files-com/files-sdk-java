# Files.Models.SharedWorkflow

## Example SharedWorkflow Object

```
{
  "id": 1,
  "name": "example",
  "description": "example",
  "submission_revision": "example",
  "form_fields": [
    {
      "id": 1,
      "label": "Sample Label",
      "required": true,
      "help_text": "Help Text",
      "field_type": "text",
      "options_for_select": [
        "red",
        "green",
        "blue"
      ],
      "default_option": "red",
      "form_field_set_id": 1
    }
  ]
}
```

* `id` / `id`  (int64): Shared Workflow ID.
* `name` / `name`  (string): Name displayed to recipients.
* `description` / `description`  (string): Description displayed to recipients.
* `submission_revision` / `submissionRevision`  (string): Revision required when submitting a Shared Workflow run.
* `form_fields` / `formFields`  (FormField[]): Current custom questions in display order.


---

## List Shared Workflows

```java
HashMap<String, Object> parameters = new HashMap<>();
ListIterator<SharedWorkflow> sharedWorkflows = SharedWorkflow.list(parameters, null);
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are .


---

## Show Shared Workflow

```java
HashMap<String, Object> parameters = new HashMap<>();
SharedWorkflow sharedWorkflow = SharedWorkflow.find(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Shared Workflow ID.


---

## Show a Shared Workflow run started by the recipient

```java
HashMap<String, Object> parameters = new HashMap<>();
AutomationRun automationRun = SharedWorkflow.findRun(1L, 1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Shared Workflow ID.
* `run_id` (Long): Required - ID of the run returned by the Shared Workflow submission.


---

## Run a Shared Workflow

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("submission_revision", "submission_revision");
parameters.put("submission_id", "submission_id");
parameters.put("file_path", "file_path");
AutomationRun automationRun = SharedWorkflow.runWorkflow(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Shared Workflow ID.
* `submission_revision` (String): Required - Revision returned when opening the Shared Workflow.
* `submission_id` (String): Required - Client-generated UUID used to retry the same submission without starting another run.
* `file_path` (String): Required - Path of a file the recipient can read, including a file uploaded to a Snapshot created for this workflow.


---

## Run a Shared Workflow

```java
SharedWorkflow sharedWorkflow = SharedWorkflow.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("submission_revision", "submission_revision");
parameters.put("submission_id", "submission_id");
parameters.put("file_path", "file_path");
AutomationRun result = sharedWorkflow.runWorkflow(parameters);
```

### Parameters

* `id` (Long): Required - Shared Workflow ID.
* `submission_revision` (String): Required - Revision returned when opening the Shared Workflow.
* `submission_id` (String): Required - Client-generated UUID used to retry the same submission without starting another run.
* `file_path` (String): Required - Path of a file the recipient can read, including a file uploaded to a Snapshot created for this workflow.
