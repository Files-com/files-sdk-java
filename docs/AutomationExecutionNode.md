# Files.Models.AutomationExecutionNode

## Example AutomationExecutionNode Object

```
{
  "node_id": "example",
  "node_type": "example",
  "status": "example",
  "run_stage": "example",
  "reused": true,
  "successful_operations": 1,
  "failed_operations": 1,
  "started_at": "2000-01-01T01:00:00Z",
  "completed_at": "2000-01-01T01:00:00Z",
  "duration_ms": 1,
  "inputs": [
    "example"
  ],
  "outputs": "example",
  "input_items": "example",
  "output_items": "example"
}
```

* `node_id` / `nodeId`  (string): Node ID from the pinned Automation definition.
* `node_type` / `nodeType`  (string): Node type.
* `status` / `status`  (string): Node status.
* `run_stage` / `runStage`  (string): Current node execution stage.
* `reused` / `reused`  (boolean): Whether this node reused persisted output from an earlier run.
* `successful_operations` / `successfulOperations`  (int64): Count of successful operations in this node.
* `failed_operations` / `failedOperations`  (int64): Count of failed operations in this node.
* `started_at` / `startedAt`  (date-time): When this node started.
* `completed_at` / `completedAt`  (date-time): When this node completed.
* `duration_ms` / `durationMs`  (int64): Node runtime in milliseconds.
* `inputs` / `inputs`  (array(object)): Ordered inbound edge references.
* `outputs` / `outputs`  (object): Output counts, item kinds, and typed-error summaries by outlet.
* `input_items` / `inputItems`  (object): Materialized input items currently available, grouped by inlet.
* `output_items` / `outputItems`  (object): Materialized output items grouped by outlet.
