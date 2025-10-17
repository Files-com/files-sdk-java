# Files.Models.ScimLog

## Example ScimLog Object

```
{
  "id": 1,
  "created_at": "2023-01-01T12:00:00Z",
  "request_path": "/api/scim/Users",
  "request_method": "POST",
  "http_response_code": "200",
  "user_agent": "Okta",
  "request_json": "example",
  "response_json": "example"
}
```

* `id` / `id`  (int64): The unique ID of this SCIM request.
* `created_at` / `createdAt`  (string): The date and time when this SCIM request occurred.
* `request_path` / `requestPath`  (string): The path portion of the URL requested.
* `request_method` / `requestMethod`  (string): The HTTP method used for this request.
* `http_response_code` / `httpResponseCode`  (string): The HTTP response code returned for this request.
* `user_agent` / `userAgent`  (string): The User-Agent header sent with the request.
* `request_json` / `requestJson`  (string): The JSON payload sent with the request.
* `response_json` / `responseJson`  (string): The JSON payload returned in the response.


---

## List Scim Logs

```
ListIterator<ScimLog> scimLog = ScimLog.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`.


---

## Show Scim Log

```
ScimLog scimLog = ScimLog.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Scim Log ID.
