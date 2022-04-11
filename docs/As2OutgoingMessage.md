# Files.Models.As2OutgoingMessage

## Example As2OutgoingMessage Object

```
{
  "id": 1,
  "as2_partner_id": 1,
  "as2_station_id": 1,
  "uuid": "",
  "http_headers": "",
  "activity_log": "",
  "processing_result": "",
  "mic": "",
  "mic_sha_256": "",
  "as2_to": "",
  "as2_from": "",
  "message_id": "",
  "body_size": "",
  "attachment_filename": "",
  "created_at": "2000-01-01T01:00:00Z",
  "http_response_code": "",
  "http_response_headers": "",
  "mdn_received": true,
  "mdn_valid": true,
  "mdn_signature_verified": true,
  "mdn_message_id_matched": true,
  "mdn_mic_matched": true,
  "mdn_processing_success": true,
  "raw_uri": "",
  "smime_uri": "",
  "smime_signed_uri": "",
  "encrypted_uri": "",
  "mdn_response_uri": ""
}
```

* `id` / `id`  (int64): Id of the AS2 Partner.
* `as2_partner_id` / `as2PartnerId`  (int64): Id of the AS2 Partner associated with this message.
* `as2_station_id` / `as2StationId`  (int64): Id of the AS2 Station associated with this message.
* `uuid` / `uuid`  (string): UUID assigned to this message.
* `http_headers` / `httpHeaders`  (object): HTTP Headers sent with this message.
* `activity_log` / `activityLog`  (string): JSON Structure of the activity log.
* `processing_result` / `processingResult`  (string): Result of processing.
* `mic` / `mic`  (string): AS2 Message Integrity Check SHA1
* `mic_sha_256` / `micSha256`  (string): AS2 Message Integrity Check SHA256
* `as2_to` / `as2To`  (string): AS2 TO
* `as2_from` / `as2From`  (string): AS2 FROM
* `message_id` / `messageId`  (string): AS2 Message Id
* `body_size` / `bodySize`  (string): Encrypted Payload Body Size
* `attachment_filename` / `attachmentFilename`  (string): Filename of the file being sent.
* `created_at` / `createdAt`  (date-time): Message creation date/time
* `http_response_code` / `httpResponseCode`  (string): HTTP Response Code received for this message
* `http_response_headers` / `httpResponseHeaders`  (object): HTTP Headers received for this message.
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
