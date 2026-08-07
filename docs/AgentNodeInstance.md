# Files.Models.AgentNodeInstance

## Example AgentNodeInstance Object

```
{
  "instance_id": "example",
  "process_state": "example",
  "status": "example",
  "is_default": true,
  "agent_version": "example",
  "last_seen_at": "2000-01-01T01:00:00Z",
  "connections": [
    {
      "mode": "example",
      "status": "example",
      "last_seen_at": "2000-01-01T01:00:00Z"
    }
  ]
}
```

* `instance_id` / `instanceId`  (string): Ephemeral ID for this running Agent process
* `process_state` / `processState`  (string): Role of this process during an Agent update
* `status` / `status`  (string): Whether this process has an available proxy connection
* `is_default` / `isDefault`  (boolean): Whether this process receives new unscoped work for its node
* `agent_version` / `agentVersion`  (string): Agent version reported by this process
* `last_seen_at` / `lastSeenAt`  (date-time): Most recent successful observation for this process
* `connections` / `connections`  (AgentNodeConnection[]): Proxy connections observed for this process
