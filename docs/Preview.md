# Files.Models.Preview

## Example Preview Object

```
{
  "id": 1,
  "status": "complete",
  "download_uri": "https://mysite.files.com/...",
  "type": "complete",
  "size": 1024,
  "status_message": ""
}
```

* `id` / `id`  (int64): Preview ID
* `status` / `status`  (string): Preview status.  Can be invalid, not_generated, generating, complete, or file_too_large
* `download_uri` / `downloadUri`  (string): Link to download preview
* `type` / `type`  (string): Preview status.  Can be invalid, not_generated, generating, complete, or file_too_large
* `size` / `size`  (int64): Preview size
* `status_message` / `statusMessage`  (string): Preview status message. Addtional context from the preview generation process about the status
