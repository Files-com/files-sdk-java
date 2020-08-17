# Files.Models.FilePartUpload

## Example FilePartUpload Object

```
{
  "send": "",
  "action": "upload/direct",
  "ask_about_overwrites": true,
  "available_parts": "",
  "expires": "",
  "headers": "",
  "http_method": "POST",
  "next_partsize": "",
  "parallel_parts": true,
  "parameters": "",
  "part_number": "",
  "partsize": "",
  "path": "",
  "ref": "upload-1",
  "upload_uri": ""
}
```

* `send` / `send`  (object): Content-Type and File to send
* `action` / `action`  (string): Type of upload
* `ask_about_overwrites` / `askAboutOverwrites`  (boolean): If false, rename conflicting files instead of asking for overwrite confirmation
* `available_parts` / `availableParts`  (string): Currently unused
* `expires` / `expires`  (string): Currently unused
* `headers` / `headers`  (object): Additional upload headers
* `http_method` / `httpMethod`  (string): Upload method, usually POST
* `next_partsize` / `nextPartsize`  (string): Currently unused
* `parallel_parts` / `parallelParts`  (boolean): If true, parts may be uploaded in parallel
* `parameters` / `parameters`  (string): Additional upload parameters
* `part_number` / `partNumber`  (string): Currently unused
* `partsize` / `partsize`  (string): Currently unused
* `path` / `path`  (string): Upload path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `ref` / `ref`  (string): Reference name for this upload part
* `upload_uri` / `uploadUri`  (string): URI to upload this part to
