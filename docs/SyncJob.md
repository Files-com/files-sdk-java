# Files.Models.SyncJob

## Example SyncJob Object

```
{
  "queued_at": "2000-01-01T01:00:00Z",
  "updated_at": "2000-01-01T01:00:00Z",
  "status": "",
  "regional_worker_status": "",
  "uuid": "",
  "folder_behavior_id": 1
}
```

* `queued_at` / `queuedAt`  (date-time): Job enqueued at
* `updated_at` / `updatedAt`  (date-time): Job updated at
* `status` / `status`  (string): Status of the job
* `regional_worker_status` / `regionalWorkerStatus`  (string): Most recent reported status of sync worker
* `uuid` / `uuid`  (string): 
* `folder_behavior_id` / `folderBehaviorId`  (int64): 


---

## List Sync Jobs

```
List<SyncJob> syncJob = SyncJob.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
