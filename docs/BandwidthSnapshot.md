# Files.Models.BandwidthSnapshot

## Example BandwidthSnapshot Object

```
{
  "id": 1,
  "bytes_received": 1.0,
  "bytes_sent": 1.0,
  "sync_bytes_received": 1.0,
  "sync_bytes_sent": 1.0,
  "requests_get": 1.0,
  "requests_put": 1.0,
  "requests_other": 1.0,
  "logged_at": "2000-01-01T01:00:00Z",
  "created_at": "2000-01-01T01:00:00Z",
  "updated_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): Site bandwidth ID
* `bytes_received` / `bytesReceived`  (double): Site bandwidth report bytes received
* `bytes_sent` / `bytesSent`  (double): Site bandwidth report bytes sent
* `sync_bytes_received` / `syncBytesReceived`  (double): Site sync bandwidth report bytes received
* `sync_bytes_sent` / `syncBytesSent`  (double): Site sync bandwidth report bytes sent
* `requests_get` / `requestsGet`  (double): Site bandwidth report get requests
* `requests_put` / `requestsPut`  (double): Site bandwidth report put requests
* `requests_other` / `requestsOther`  (double): Site bandwidth report other requests
* `logged_at` / `loggedAt`  (date-time): Time the site bandwidth report was logged
* `created_at` / `createdAt`  (date-time): Site bandwidth report created at date/time
* `updated_at` / `updatedAt`  (date-time): The last time this site bandwidth report was updated


---

## List Bandwidth Snapshots

```
List<BandwidthSnapshot> bandwidthSnapshot = BandwidthSnapshot.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via either the X-Files-Cursor-Next header or the X-Files-Cursor-Prev header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `logged_at`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `logged_at`.
* `filter_gt` (Map<String, String>): If set, return records where the specified field is greater than the supplied value. Valid fields are `logged_at`.
* `filter_gteq` (Map<String, String>): If set, return records where the specified field is greater than or equal to the supplied value. Valid fields are `logged_at`.
* `filter_like` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `logged_at`.
* `filter_lt` (Map<String, String>): If set, return records where the specified field is less than the supplied value. Valid fields are `logged_at`.
* `filter_lteq` (Map<String, String>): If set, return records where the specified field is less than or equal to the supplied value. Valid fields are `logged_at`.
