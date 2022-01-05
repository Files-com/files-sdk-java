# Files.Models.As2IncomingMessage

## Example As2IncomingMessage Object

```
{
  "id": 1,
  "as2_partner_id": 1,
  "uuid": "",
  "content_type": "",
  "http_headers": "",
  "activity_log": "",
  "processing_result": "",
  "as2_to": "",
  "as2_from": "",
  "message_id": "",
  "subject": "",
  "body_size": "",
  "attachment_filename": "",
  "created_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): Id of the AS2 Partner.
* `as2_partner_id` / `as2PartnerId`  (int64): Id of the AS2 Partner associated with this message.
* `uuid` / `uuid`  (string): UUID assigned to this message.
* `content_type` / `contentType`  (string): Content Type header of the incoming message.
* `http_headers` / `httpHeaders`  (object): HTTP Headers sent with this message.
* `activity_log` / `activityLog`  (string): JSON Structure of the activity log.
* `processing_result` / `processingResult`  (string): Result of processing. Valid values: `unable_to_find_station`, `unable_to_find_partner`, `unable_to_validate_signature`, `decrypt_fail`, `file_save_fail`, `success`
* `as2_to` / `as2To`  (string): AS2 TO header of message
* `as2_from` / `as2From`  (string): AS2 FROM header of message
* `message_id` / `messageId`  (string): AS2 Message Id
* `subject` / `subject`  (string): AS2 Subject Header
* `body_size` / `bodySize`  (string): Encrypted Payload Body Size
* `attachment_filename` / `attachmentFilename`  (string): Filename of the file being received.
* `created_at` / `createdAt`  (date-time): Message creation date/time


---

## List As2 Incoming Messages

```
List<As2IncomingMessage> as2IncomingMessage = As2IncomingMessage.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via either the X-Files-Cursor-Next header or the X-Files-Cursor-Prev header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `as2_partner_id` (Long): As2 Partner ID.  If provided, will return message specific to that partner.
