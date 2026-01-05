# Files.Models.RemoteMountBackend

## Example RemoteMountBackend Object

```
{
  "canary_file_path": "backend1.txt",
  "enabled": true,
  "fall": 1,
  "health_check_enabled": true,
  "health_check_results": [
    {
      "timestamp": "2025-09-19T12:32:52+00:00",
      "status": "healthy",
      "canary_timestamp": "2025-09-19T12:32:52+00:00"
    },
    {
      "status": "failed",
      "reason": "Unable to connect",
      "timestamp": "2025-09-19T12:32:52+00:00"
    }
  ],
  "health_check_type": "active",
  "id": 1,
  "interval": 60,
  "min_free_cpu": 1.0,
  "min_free_mem": 1.0,
  "priority": 1,
  "remote_path": "/path/on/remote",
  "remote_server_id": 1,
  "remote_server_mount_id": 1,
  "rise": 1,
  "status": "healthy",
  "undergoing_maintenance": true
}
```

* `canary_file_path` / `canaryFilePath`  (string): Path to the canary file used for health checks.
* `enabled` / `enabled`  (boolean): True if this backend is enabled.
* `fall` / `fall`  (int64): Number of consecutive failures before considering the backend unhealthy.
* `health_check_enabled` / `healthCheckEnabled`  (boolean): True if health checks are enabled for this backend.
* `health_check_results` / `healthCheckResults`  (array(object)): Array of recent health check results.
* `health_check_type` / `healthCheckType`  (string): Type of health check to perform.
* `id` / `id`  (int64): Unique identifier for this backend.
* `interval` / `interval`  (int64): Interval in seconds between health checks.
* `min_free_cpu` / `minFreeCpu`  (double): Minimum free CPU percentage required for this backend to be considered healthy.
* `min_free_mem` / `minFreeMem`  (double): Minimum free memory percentage required for this backend to be considered healthy.
* `priority` / `priority`  (int64): Priority of this backend.
* `remote_path` / `remotePath`  (string): Path on the remote server to treat as the root of this mount.
* `remote_server_id` / `remoteServerId`  (int64): The remote server that this backend is associated with.
* `remote_server_mount_id` / `remoteServerMountId`  (int64): The mount ID of the Remote Server Mount that this backend is associated with.
* `rise` / `rise`  (int64): Number of consecutive successes before considering the backend healthy.
* `status` / `status`  (string): Status of this backend.
* `undergoing_maintenance` / `undergoingMaintenance`  (boolean): True if this backend is undergoing maintenance.


---

## List Remote Mount Backends

```
ListIterator<RemoteMountBackend> remoteMountBackend = RemoteMountBackend.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `remote_server_mount_id`.


---

## Show Remote Mount Backend

```
RemoteMountBackend remoteMountBackend = RemoteMountBackend.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Remote Mount Backend ID.


---

## Create Remote Mount Backend

```
RemoteMountBackend remoteMountBackend = RemoteMountBackend.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `enabled` (Boolean): True if this backend is enabled.
* `fall` (Long): Number of consecutive failures before considering the backend unhealthy.
* `health_check_enabled` (Boolean): True if health checks are enabled for this backend.
* `health_check_type` (String): Type of health check to perform.
* `interval` (Long): Interval in seconds between health checks.
* `min_free_cpu` (Double): Minimum free CPU percentage required for this backend to be considered healthy.
* `min_free_mem` (Double): Minimum free memory percentage required for this backend to be considered healthy.
* `priority` (Long): Priority of this backend.
* `remote_path` (String): Path on the remote server to treat as the root of this mount.
* `rise` (Long): Number of consecutive successes before considering the backend healthy.
* `canary_file_path` (String): Required - Path to the canary file used for health checks.
* `remote_server_mount_id` (Long): Required - The mount ID of the Remote Server Mount that this backend is associated with.
* `remote_server_id` (Long): Required - The remote server that this backend is associated with.


---

## Reset backend status to healthy

```
void remoteMountBackend = RemoteMountBackend.resetStatus(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Remote Mount Backend ID.


---

## Update Remote Mount Backend

```
RemoteMountBackend remoteMountBackend = RemoteMountBackend.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Remote Mount Backend ID.
* `enabled` (Boolean): True if this backend is enabled.
* `fall` (Long): Number of consecutive failures before considering the backend unhealthy.
* `health_check_enabled` (Boolean): True if health checks are enabled for this backend.
* `health_check_type` (String): Type of health check to perform.
* `interval` (Long): Interval in seconds between health checks.
* `min_free_cpu` (Double): Minimum free CPU percentage required for this backend to be considered healthy.
* `min_free_mem` (Double): Minimum free memory percentage required for this backend to be considered healthy.
* `priority` (Long): Priority of this backend.
* `remote_path` (String): Path on the remote server to treat as the root of this mount.
* `rise` (Long): Number of consecutive successes before considering the backend healthy.
* `canary_file_path` (String): Path to the canary file used for health checks.
* `remote_server_id` (Long): The remote server that this backend is associated with.


---

## Delete Remote Mount Backend

```
void remoteMountBackend = RemoteMountBackend.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Remote Mount Backend ID.


---

## Reset backend status to healthy

```
RemoteMountBackend remoteMountBackend = RemoteMountBackend.find(id);

HashMap<String, Object> parameters = new HashMap<>();

remoteMountBackend.resetStatus(parameters);
```

### Parameters

* `id` (Long): Required - Remote Mount Backend ID.


---

## Update Remote Mount Backend

```
RemoteMountBackend remoteMountBackend = RemoteMountBackend.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("enabled", true);
parameters.put("fall", 1);
parameters.put("health_check_enabled", true);
parameters.put("health_check_type", "active");
parameters.put("interval", 60);
parameters.put("min_free_cpu", 1.0);
parameters.put("min_free_mem", 1.0);
parameters.put("priority", 1);
parameters.put("remote_path", "/path/on/remote");
parameters.put("rise", 1);
parameters.put("canary_file_path", "backend1.txt");
parameters.put("remote_server_id", 1);

remoteMountBackend.update(parameters);
```

### Parameters

* `id` (Long): Required - Remote Mount Backend ID.
* `enabled` (Boolean): True if this backend is enabled.
* `fall` (Long): Number of consecutive failures before considering the backend unhealthy.
* `health_check_enabled` (Boolean): True if health checks are enabled for this backend.
* `health_check_type` (String): Type of health check to perform.
* `interval` (Long): Interval in seconds between health checks.
* `min_free_cpu` (Double): Minimum free CPU percentage required for this backend to be considered healthy.
* `min_free_mem` (Double): Minimum free memory percentage required for this backend to be considered healthy.
* `priority` (Long): Priority of this backend.
* `remote_path` (String): Path on the remote server to treat as the root of this mount.
* `rise` (Long): Number of consecutive successes before considering the backend healthy.
* `canary_file_path` (String): Path to the canary file used for health checks.
* `remote_server_id` (Long): The remote server that this backend is associated with.


---

## Delete Remote Mount Backend

```
RemoteMountBackend remoteMountBackend = RemoteMountBackend.find(id);

HashMap<String, Object> parameters = new HashMap<>();

remoteMountBackend.delete(parameters);
```

### Parameters

* `id` (Long): Required - Remote Mount Backend ID.
