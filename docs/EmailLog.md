# Files.Models.EmailLog

## Example EmailLog Object

```
{
  "timestamp": "2000-01-01T01:00:00Z",
  "message": "example",
  "status": "example",
  "subject": "example",
  "to": "example",
  "cc": "example"
}
```

* `timestamp` / `timestamp`  (date-time): Start Time of Action
* `message` / `message`  (string): Log Message
* `status` / `status`  (string): Status of E-Mail delivery
* `subject` / `subject`  (string): Subject line of E-Mail
* `to` / `to`  (string): To field of E-Mail
* `cc` / `cc`  (string): CC field of E-Mail


---

## List Email Logs

```
ListIterator<EmailLog> emailLog = EmailLog.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `start_date`, `end_date` or `status`. Valid field combinations are `[ start_date ]`, `[ end_date ]`, `[ status ]`, `[ start_date, end_date ]`, `[ start_date, status ]` or `[ end_date, status ]`.
* `filter_prefix` (Map<String, String>): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `status`. Valid field combinations are `[ start_date ]`, `[ end_date ]`, `[ status ]`, `[ start_date, end_date ]`, `[ start_date, status ]` or `[ end_date, status ]`.
