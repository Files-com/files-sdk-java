# Files.Models.ExpectationEvaluation

## Example ExpectationEvaluation Object

```
{
  "id": 1,
  "workspace_id": 1,
  "expectation_id": 1,
  "status": "open",
  "opened_via": "manual",
  "opened_at": "2000-01-01T01:00:00Z",
  "window_start_at": "2000-01-01T01:00:00Z",
  "window_end_at": "2000-01-01T01:00:00Z",
  "deadline_at": "2000-01-01T01:00:00Z",
  "late_acceptance_cutoff_at": "2000-01-01T01:00:00Z",
  "hard_close_at": "2000-01-01T01:00:00Z",
  "closed_at": "2000-01-01T01:00:00Z",
  "matched_files": [

  ],
  "missing_files": [

  ],
  "criteria_errors": [

  ],
  "summary": null,
  "created_at": "2000-01-01T01:00:00Z",
  "updated_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): ExpectationEvaluation ID
* `workspace_id` / `workspaceId`  (int64): Workspace ID. `0` means the default workspace.
* `expectation_id` / `expectationId`  (int64): Expectation ID.
* `status` / `status`  (string): Evaluation status.
* `opened_via` / `openedVia`  (string): How the evaluation window was opened.
* `opened_at` / `openedAt`  (date-time): When the evaluation row was opened.
* `window_start_at` / `windowStartAt`  (date-time): Logical start of the candidate window.
* `window_end_at` / `windowEndAt`  (date-time): Actual candidate cutoff boundary for the window.
* `deadline_at` / `deadlineAt`  (date-time): Logical due boundary for schedule-driven windows.
* `late_acceptance_cutoff_at` / `lateAcceptanceCutoffAt`  (date-time): Logical cutoff for late acceptance, when present.
* `hard_close_at` / `hardCloseAt`  (date-time): Hard stop after which the window may not remain open.
* `closed_at` / `closedAt`  (date-time): When the evaluation row was finalized.
* `matched_files` / `matchedFiles`  (array(object)): Captured evidence for files that matched the window.
* `missing_files` / `missingFiles`  (array(object)): Captured evidence for required files that were missing.
* `criteria_errors` / `criteriaErrors`  (array(object)): Captured criteria failures for the window.
* `summary` / `summary`  (object): Compact evaluator summary payload.
* `created_at` / `createdAt`  (date-time): Creation time.
* `updated_at` / `updatedAt`  (date-time): Last update time.


---

## List Expectation Evaluations

```
ListIterator<ExpectationEvaluation> expectationEvaluation = ExpectationEvaluation.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `created_at` or `expectation_id`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `expectation_id` and `workspace_id`. Valid field combinations are `[ workspace_id, expectation_id ]`.


---

## Show Expectation Evaluation

```
ExpectationEvaluation expectationEvaluation = ExpectationEvaluation.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Expectation Evaluation ID.
