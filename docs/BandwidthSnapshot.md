# Files.Models.BandwidthSnapshot

## Example BandwidthSnapshot Object

```
{
  "id": 1,
  "bytes_received": 1,
  "bytes_sent": 1,
  "sync_bytes_received": 1,
  "sync_bytes_sent": 1,
  "requests_get": 1,
  "requests_put": 1,
  "requests_other": 1,
  "logged_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): Site bandwidth ID
* `bytes_received` / `bytesReceived`  (int64): Site bandwidth report bytes received
* `bytes_sent` / `bytesSent`  (int64): Site bandwidth report bytes sent
* `sync_bytes_received` / `syncBytesReceived`  (int64): Site sync bandwidth report bytes received
* `sync_bytes_sent` / `syncBytesSent`  (int64): Site sync bandwidth report bytes sent
* `requests_get` / `requestsGet`  (int64): Site bandwidth report get requests
* `requests_put` / `requestsPut`  (int64): Site bandwidth report put requests
* `requests_other` / `requestsOther`  (int64): Site bandwidth report other requests
* `logged_at` / `loggedAt`  (date-time): Time the site bandwidth report was logged


---

## List Bandwidth Snapshots

```
ListIterator<BandwidthSnapshot> bandwidthSnapshot = BandwidthSnapshot.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `logged_at`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `logged_at`.
* `filter_gt` (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `logged_at`.
* `filter_gteq` (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `logged_at`.
* `filter_lt` (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `logged_at`.
* `filter_lteq` (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `logged_at`.
