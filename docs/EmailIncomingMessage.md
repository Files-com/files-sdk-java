# Files.Models.EmailIncomingMessage

## Example EmailIncomingMessage Object

```
{
  "id": 1,
  "inbox_id": 1,
  "sender": "example",
  "sender_name": "example",
  "status": "success",
  "body": "example",
  "message": "example",
  "created_at": "2000-01-01T01:00:00Z",
  "inbox_title": "Inbox Title"
}
```

* `id` / `id`  (int64): Id of the Email Incoming Message
* `inbox_id` / `inboxId`  (int64): Id of the Inbox associated with this message
* `sender` / `sender`  (string): Sender of the email
* `sender_name` / `senderName`  (string): Sender name
* `status` / `status`  (string): Status of the message
* `body` / `body`  (string): Body of the email
* `message` / `message`  (string): Message describing the failure
* `created_at` / `createdAt`  (date-time): Message creation date/time
* `inbox_title` / `inboxTitle`  (string): Title of the Inbox associated with this message


---

## List Email Incoming Messages

```
ListIterator<EmailIncomingMessage> emailIncomingMessage = EmailIncomingMessage.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`, `sender`, `status` or `inbox_id`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`, `inbox_id`, `sender` or `status`. Valid field combinations are `[ created_at, sender ]`, `[ created_at, status ]`, `[ created_at, inbox_id ]`, `[ created_at, inbox_id, status ]` or `[ created_at, inbox_id, sender, status ]`.
* `filter_gt` (Map<String, String>): If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`.
* `filter_gteq` (Map<String, String>): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`.
* `filter_prefix` (Map<String, String>): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `sender`.
* `filter_lt` (Map<String, String>): If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`.
* `filter_lteq` (Map<String, String>): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`.
