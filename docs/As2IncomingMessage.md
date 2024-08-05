# Files.Models.As2IncomingMessage

## Example As2IncomingMessage Object

```
{
  "id": 1,
  "as2_partner_id": 1,
  "as2_station_id": 1,
  "uuid": "example",
  "content_type": "example",
  "http_headers": {
    "key": "example value"
  },
  "activity_log": "example",
  "processing_result": "example",
  "processing_result_description": "example",
  "mic": "example",
  "mic_algo": "example",
  "as2_to": "example",
  "as2_from": "example",
  "message_id": "example",
  "subject": "example",
  "date": "example",
  "body_size": "example",
  "attachment_filename": "example",
  "ip": "example",
  "created_at": "2000-01-01T01:00:00Z",
  "http_response_code": "example",
  "http_response_headers": {
    "key": "example value"
  },
  "recipient_serial": "example",
  "hex_recipient_serial": "A5:EB:C1:95:DC:D8:2B:E7",
  "recipient_issuer": "example",
  "message_received": true,
  "message_decrypted": true,
  "message_signature_verified": true,
  "message_processing_success": true,
  "message_mdn_returned": true,
  "encrypted_uri": "example",
  "smime_signed_uri": "example",
  "smime_uri": "example",
  "raw_uri": "example",
  "mdn_response_uri": "example"
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
* `processing_result_description` / `processingResultDescription`  (string): Result of processing description.
* `mic` / `mic`  (string): AS2 Message Integrity Check
* `mic_algo` / `micAlgo`  (string): AS2 Message Integrity Check Algorithm Used
* `as2_to` / `as2To`  (string): AS2 TO header of message
* `as2_from` / `as2From`  (string): AS2 FROM header of message
* `message_id` / `messageId`  (string): AS2 Message Id
* `subject` / `subject`  (string): AS2 Subject Header
* `date` / `date`  (string): Date Header
* `body_size` / `bodySize`  (string): Encrypted Payload Body Size
* `attachment_filename` / `attachmentFilename`  (string): Filename of the file being received.
* `ip` / `ip`  (string): IP Address of the Sender
* `created_at` / `createdAt`  (date-time): Message creation date/time
* `http_response_code` / `httpResponseCode`  (string): HTTP Response Code sent for this message
* `http_response_headers` / `httpResponseHeaders`  (object): HTTP Headers sent for this message.
* `recipient_serial` / `recipientSerial`  (string): Incoming Message Recipient(the Client Cert used to encrypt this message)'s serial
* `hex_recipient_serial` / `hexRecipientSerial`  (string): Incoming Message Recipient(the Client Cert used to encrypt this message)'s serial in hex format.
* `recipient_issuer` / `recipientIssuer`  (string): Incoming Message Recipient(the Client Cert used to encrypt this message)'s issuer
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

## List AS2 Incoming Messages

```
ListIterator<As2IncomingMessage> as2IncomingMessage = As2IncomingMessage.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at` and `as2_partner_id`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`.
* `filter_gt` (Map<String, String>): If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`.
* `filter_gteq` (Map<String, String>): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`.
* `filter_lt` (Map<String, String>): If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`.
* `filter_lteq` (Map<String, String>): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`.
* `as2_partner_id` (Long): As2 Partner ID.  If provided, will return message specific to that partner.
