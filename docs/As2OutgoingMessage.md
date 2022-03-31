# Files.Models.As2OutgoingMessage

## Example As2OutgoingMessage Object

```
{
  "id": 1,
  "as2_partner_id": 1,
  "uuid": "",
  "http_headers": "",
  "activity_log": "",
  "processing_result": "",
  "mic": "",
  "message_id": "",
  "body_size": "",
  "attachment_filename": "",
  "created_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): Id of the AS2 Partner.
* `as2_partner_id` / `as2PartnerId`  (int64): Id of the AS2 Partner associated with this message.
* `uuid` / `uuid`  (string): UUID assigned to this message.
* `http_headers` / `httpHeaders`  (object): HTTP Headers sent with this message.
* `activity_log` / `activityLog`  (string): JSON Structure of the activity log.
* `processing_result` / `processingResult`  (string): Result of processing.
* `mic` / `mic`  (string): AS2 Message Integrity Check
* `message_id` / `messageId`  (string): AS2 Message Id
* `body_size` / `bodySize`  (string): Encrypted Payload Body Size
* `attachment_filename` / `attachmentFilename`  (string): Filename of the file being sent.
* `created_at` / `createdAt`  (date-time): Message creation date/time


---

## List As2 Outgoing Messages

```
List<As2OutgoingMessage> as2OutgoingMessage = As2OutgoingMessage.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via either the X-Files-Cursor-Next header or the X-Files-Cursor-Prev header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `as2_partner_id` (Long): As2 Partner ID.  If provided, will return message specific to that partner.
