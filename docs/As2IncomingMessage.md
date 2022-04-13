# Files.Models.As2IncomingMessage

## Example As2IncomingMessage Object

```
{
  "id": 1,
  "as2_partner_id": 1,
  "as2_station_id": 1,
  "uuid": "",
  "content_type": "",
  "http_headers": "",
  "activity_log": "",
  "processing_result": "",
  "mic": "",
  "mic_algo": "",
  "as2_to": "",
  "as2_from": "",
  "message_id": "",
  "subject": "",
  "body_size": "",
  "attachment_filename": "",
  "ip": "",
  "created_at": "2000-01-01T01:00:00Z",
  "http_response_code": "",
  "http_response_headers": "",
  "message_received": true,
  "message_decrypted": true,
  "message_signature_verified": true,
  "message_processing_success": true,
  "message_mdn_returned": true,
  "encrypted_uri": "",
  "smime_signed_uri": "",
  "smime_uri": "",
  "raw_uri": "",
  "mdn_response_uri": ""
}
```

* `id` / `id`  (int64): Id of the AS2 Partner.
* `as2_partner_id` / `as2PartnerId`  (int64): Id of the AS2 Partner associated with this message.
* `as2_station_id` / `as2StationId`  (int64): Id of the AS2 Station associated with this message.
* `uuid` / `uuid`  (string): UUID assigned to this message.
* `content_type` / `contentType`  (string): Content Type header of the incoming message.
* `http_headers` / `httpHeaders`  (object): HTTP Headers sent with this message.
* `activity_log` / `activityLog`  (string): JSON Structure of the activity log.
* `processing_result` / `processingResult`  (string): Result of processing.
* `mic` / `mic`  (string): AS2 Message Integrity Check
* `mic_algo` / `micAlgo`  (string): AS2 Message Integrity Check Algorithm Used
* `as2_to` / `as2To`  (string): AS2 TO header of message
* `as2_from` / `as2From`  (string): AS2 FROM header of message
* `message_id` / `messageId`  (string): AS2 Message Id
* `subject` / `subject`  (string): AS2 Subject Header
* `body_size` / `bodySize`  (string): Encrypted Payload Body Size
* `attachment_filename` / `attachmentFilename`  (string): Filename of the file being received.
* `ip` / `ip`  (string): IP Address of the Sender
* `created_at` / `createdAt`  (date-time): Message creation date/time
* `http_response_code` / `httpResponseCode`  (string): HTTP Response Code sent for this message
* `http_response_headers` / `httpResponseHeaders`  (object): HTTP Headers sent for this message.
* `message_received` / `messageReceived`  (boolean): Message body received?
* `message_decrypted` / `messageDecrypted`  (boolean): Message decrypted successfully?
* `message_signature_verified` / `messageSignatureVerified`  (boolean): Message signature verified?
* `message_processing_success` / `messageProcessingSuccess`  (boolean): Message processed successfully?
* `message_mdn_returned` / `messageMdnReturned`  (boolean): MDN returned?
* `encrypted_uri` / `encryptedUri`  (string): URL to download the encrypted signed smime that is to sent as AS2 body
* `smime_signed_uri` / `smimeSignedUri`  (string): URL to download the file contents as smime with signature
* `smime_uri` / `smimeUri`  (string): URL to download the file contents encoded as smime
* `raw_uri` / `rawUri`  (string): URL to download the original file contents
* `mdn_response_uri` / `mdnResponseUri`  (string): URL to download the http response body


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
