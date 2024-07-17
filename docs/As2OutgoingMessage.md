# Files.Models.As2OutgoingMessage

## Example As2OutgoingMessage Object

```
{
  "id": 1,
  "as2_partner_id": 1,
  "as2_station_id": 1,
  "uuid": "example",
  "http_headers": {
    "key": "example value"
  },
  "activity_log": "example",
  "processing_result": "example",
  "processing_result_description": "example",
  "mic": "example",
  "mic_sha_256": "example",
  "as2_to": "example",
  "as2_from": "example",
  "date": "example",
  "message_id": "example",
  "body_size": "example",
  "attachment_filename": "example",
  "created_at": "2000-01-01T01:00:00Z",
  "http_response_code": "example",
  "http_response_headers": {
    "key": "example value"
  },
  "http_transmission_duration": 1.0,
  "mdn_received": true,
  "mdn_valid": true,
  "mdn_signature_verified": true,
  "mdn_message_id_matched": true,
  "mdn_mic_matched": true,
  "mdn_processing_success": true,
  "raw_uri": "example",
  "smime_uri": "example",
  "smime_signed_uri": "example",
  "encrypted_uri": "example",
  "mdn_response_uri": "example"
}
```

* `id` / `id`  (int64): Id of the AS2 Partner.
* `as2_partner_id` / `as2PartnerId`  (int64): Id of the AS2 Partner associated with this message.
* `as2_station_id` / `as2StationId`  (int64): Id of the AS2 Station associated with this message.
* `uuid` / `uuid`  (string): UUID assigned to this message.
* `http_headers` / `httpHeaders`  (object): HTTP Headers sent with this message.
* `activity_log` / `activityLog`  (string): JSON Structure of the activity log.
* `processing_result` / `processingResult`  (string): Result of processing.
* `processing_result_description` / `processingResultDescription`  (string): Result of processing description.
* `mic` / `mic`  (string): AS2 Message Integrity Check SHA1
* `mic_sha_256` / `micSha256`  (string): AS2 Message Integrity Check SHA256
* `as2_to` / `as2To`  (string): AS2 TO
* `as2_from` / `as2From`  (string): AS2 FROM
* `date` / `date`  (string): Date Header
* `message_id` / `messageId`  (string): AS2 Message Id
* `body_size` / `bodySize`  (string): Encrypted Payload Body Size
* `attachment_filename` / `attachmentFilename`  (string): Filename of the file being sent.
* `created_at` / `createdAt`  (date-time): Message creation date/time
* `http_response_code` / `httpResponseCode`  (string): HTTP Response Code received for this message
* `http_response_headers` / `httpResponseHeaders`  (object): HTTP Headers received for this message.
* `http_transmission_duration` / `httpTransmissionDuration`  (double): HTTP transmission duration in seceonds
* `mdn_received` / `mdnReceived`  (boolean): Did the partner give a response body?
* `mdn_valid` / `mdnValid`  (boolean): Is the response in MDN format?
* `mdn_signature_verified` / `mdnSignatureVerified`  (boolean): MDN signature verified?
* `mdn_message_id_matched` / `mdnMessageIdMatched`  (boolean): MDN message id matched?
* `mdn_mic_matched` / `mdnMicMatched`  (boolean): MDN MIC matched?
* `mdn_processing_success` / `mdnProcessingSuccess`  (boolean): MDN disposition indicate a successful processing?
* `raw_uri` / `rawUri`  (string): URL to download the original file contents
* `smime_uri` / `smimeUri`  (string): URL to download the file contents encoded as smime
* `smime_signed_uri` / `smimeSignedUri`  (string): URL to download the file contents as smime with signature
* `encrypted_uri` / `encryptedUri`  (string): URL to download the encrypted signed smime that is to sent as AS2 body
* `mdn_response_uri` / `mdnResponseUri`  (string): URL to download the http response body


---

## List AS2 Outgoing Messages

```
ListIterator<As2OutgoingMessage> as2OutgoingMessage = As2OutgoingMessage.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `action` (String): 
* `page` (Long): 
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction (e.g. `sort_by[created_at]=desc`). Valid fields are `created_at` and `as2_partner_id`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`.
* `filter_gt` (Map<String, String>): If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`.
* `filter_gteq` (Map<String, String>): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`.
* `filter_lt` (Map<String, String>): If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`.
* `filter_lteq` (Map<String, String>): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`.
* `as2_partner_id` (Long): As2 Partner ID.  If provided, will return message specific to that partner.
