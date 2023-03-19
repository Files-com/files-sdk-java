# Files.Models.FileUploadPart

## Example FileUploadPart Object

```
{
  "send": {
    "key": "example value"
  },
  "action": "multipart",
  "ask_about_overwrites": true,
  "available_parts": 1,
  "expires": "example",
  "headers": {
    "key": "example value"
  },
  "http_method": "PUT",
  "next_partsize": 1,
  "parallel_parts": true,
  "retry_parts": true,
  "parameters": {
    "key": "example value"
  },
  "part_number": 1,
  "partsize": 1,
  "path": "",
  "ref": "upload-1",
  "upload_uri": "example"
}
```

* `send` / `send`  (object): Content-Type and File to send
* `action` / `action`  (string): Type of upload
* `ask_about_overwrites` / `askAboutOverwrites`  (boolean): If `true`, this file exists and you may wish to ask the user for overwrite confirmation
* `available_parts` / `availableParts`  (int64): Number of parts in the upload
* `expires` / `expires`  (string): Date/time of when this Upload part expires and the URL cannot be used any more
* `headers` / `headers`  (object): Additional upload headers to provide as part of the upload
* `http_method` / `httpMethod`  (string): HTTP Method to use for uploading the part, usually `PUT`
* `next_partsize` / `nextPartsize`  (int64): Size in bytes for this part
* `parallel_parts` / `parallelParts`  (boolean): If `true`, multiple parts may be uploaded in parallel.  If `false`, be sure to only upload one part at a time, in order.
* `retry_parts` / `retryParts`  (boolean): If `true`, parts may be retried. If `false`, a part cannot be retried and the upload should be restarted.
* `parameters` / `parameters`  (object): Additional HTTP parameters to send with the upload
* `part_number` / `partNumber`  (int64): Number of this upload part
* `partsize` / `partsize`  (int64): Size in bytes for the next upload part
* `path` / `path`  (string): New file path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `ref` / `ref`  (string): Reference name for this upload part
* `upload_uri` / `uploadUri`  (string): URI to upload this part to
