# Files.Models.File

## Example File Object

```
{
  "path": "path/file.txt",
  "created_by_id": 1,
  "created_by_api_key_id": 1,
  "created_by_as2_incoming_message_id": 1,
  "created_by_automation_id": 1,
  "created_by_bundle_registration_id": 1,
  "created_by_inbox_id": 1,
  "created_by_remote_server_id": 1,
  "created_by_sync_id": 1,
  "custom_metadata": {
    "key": "value"
  },
  "display_name": "file.txt",
  "type": "file",
  "size": 1024,
  "created_at": "2000-01-01T01:00:00Z",
  "last_modified_by_id": 1,
  "last_modified_by_api_key_id": 1,
  "last_modified_by_automation_id": 1,
  "last_modified_by_bundle_registration_id": 1,
  "last_modified_by_remote_server_id": 1,
  "last_modified_by_sync_id": 1,
  "mtime": "2000-01-01T01:00:00Z",
  "provided_mtime": "2000-01-01T01:00:00Z",
  "crc32": "70976923",
  "md5": "17c54824e9931a4688ca032d03f6663c",
  "sha1": "a94a8fe5ccb19ba61c4c0873d391e987982fbbd3",
  "sha256": "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08",
  "mime_type": "application/octet-stream",
  "region": "us-east-1",
  "permissions": "rwd",
  "subfolders_locked?": true,
  "is_locked": true,
  "download_uri": "https://mysite.files.com/...",
  "priority_color": "red",
  "preview_id": 1,
  "preview": {
    "id": 1,
    "status": "complete",
    "download_uri": "https://mysite.files.com/...",
    "type": "image",
    "size": "large"
  }
}
```

* `path` / `path`  (string): File/Folder path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `created_by_id` / `createdById`  (int64): User ID of the User who created the file/folder
* `created_by_api_key_id` / `createdByApiKeyId`  (int64): ID of the API key that created the file/folder
* `created_by_as2_incoming_message_id` / `createdByAs2IncomingMessageId`  (int64): ID of the AS2 Incoming Message that created the file/folder
* `created_by_automation_id` / `createdByAutomationId`  (int64): ID of the Automation that created the file/folder
* `created_by_bundle_registration_id` / `createdByBundleRegistrationId`  (int64): ID of the Bundle Registration that created the file/folder
* `created_by_inbox_id` / `createdByInboxId`  (int64): ID of the Inbox that created the file/folder
* `created_by_remote_server_id` / `createdByRemoteServerId`  (int64): ID of the Remote Server that created the file/folder
* `created_by_sync_id` / `createdBySyncId`  (int64): ID of the Sync that created the file/folder
* `custom_metadata` / `customMetadata`  (object): Custom metadata map of keys and values. Limited to 32 keys, 256 characters per key and 1024 characters per value.
* `display_name` / `displayName`  (string): File/Folder display name
* `type` / `type`  (string): Type: `directory` or `file`.
* `size` / `size`  (int64): File/Folder size
* `created_at` / `createdAt`  (date-time): File created date/time
* `last_modified_by_id` / `lastModifiedById`  (int64): User ID of the User who last modified the file/folder
* `last_modified_by_api_key_id` / `lastModifiedByApiKeyId`  (int64): ID of the API key that last modified the file/folder
* `last_modified_by_automation_id` / `lastModifiedByAutomationId`  (int64): ID of the Automation that last modified the file/folder
* `last_modified_by_bundle_registration_id` / `lastModifiedByBundleRegistrationId`  (int64): ID of the Bundle Registration that last modified the file/folder
* `last_modified_by_remote_server_id` / `lastModifiedByRemoteServerId`  (int64): ID of the Remote Server that last modified the file/folder
* `last_modified_by_sync_id` / `lastModifiedBySyncId`  (int64): ID of the Sync that last modified the file/folder
* `mtime` / `mtime`  (date-time): File last modified date/time, according to the server.  This is the timestamp of the last Files.com operation of the file, regardless of what modified timestamp was sent.
* `provided_mtime` / `providedMtime`  (date-time): File last modified date/time, according to the client who set it.  Files.com allows desktop, FTP, SFTP, and WebDAV clients to set modified at times.  This allows Desktop<->Cloud syncing to preserve modified at times.
* `crc32` / `crc32`  (string): File CRC32 checksum. This is sometimes delayed, so if you get a blank response, wait and try again.
* `md5` / `md5`  (string): File MD5 checksum. This is sometimes delayed, so if you get a blank response, wait and try again.
* `sha1` / `sha1`  (string): File SHA1 checksum. This is sometimes delayed, so if you get a blank response, wait and try again.
* `sha256` / `sha256`  (string): File SHA256 checksum. This is sometimes delayed, so if you get a blank response, wait and try again.
* `mime_type` / `mimeType`  (string): MIME Type.  This is determined by the filename extension and is not stored separately internally.
* `region` / `region`  (string): Region location
* `permissions` / `permissions`  (string): A short string representing the current user's permissions.  Can be `r` (Read),`w` (Write),`d` (Delete), `l` (List) or any combination
* `subfolders_locked?` / `subfoldersLocked`  (boolean): Are subfolders locked and unable to be modified?
* `is_locked` / `isLocked`  (boolean): Is this folder locked and unable to be modified?
* `download_uri` / `downloadUri`  (string): Link to download file. Provided only in response to a download request.
* `priority_color` / `priorityColor`  (string): Bookmark/priority color of file/folder
* `preview_id` / `previewId`  (int64): File preview ID
* `preview` / `preview`  (preview): File preview
* `action` / `action`  (string): The action to perform.  Can be `append`, `attachment`, `end`, `upload`, `put`, or may not exist
* `length` / `length`  (int64): Length of file.
* `mkdir_parents` / `mkdirParents`  (boolean): Create parent directories if they do not exist?
* `part` / `part`  (int64): Part if uploading a part.
* `parts` / `parts`  (int64): How many parts to fetch?
* `ref` / `ref`  (string): 
* `restart` / `restart`  (int64): File byte offset to restart from.
* `structure` / `structure`  (string): If copying folder, copy just the structure?
* `with_rename` / `withRename`  (boolean): Allow file rename instead of overwrite?
* `buffered_upload` / `bufferedUpload`  (boolean): If true, and the path refers to a destination not stored on Files.com (such as a remote server mount), the upload will be uploaded first to Files.com before being sent to the remote server mount. This can allow clients to upload using parallel parts to a remote server destination that does not offer parallel parts support natively.


---

## Download File

```
File file = File.download(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `path` (String): Required - Path to operate on.
* `action` (String): Can be blank, `redirect` or `stat`.  If set to `stat`, we will return file information but without a download URL, and without logging a download.  If set to `redirect` we will serve a 302 redirect directly to the file.  This is used for integrations with Zapier, and is not recommended for most integrations.
* `preview_size` (String): Request a preview size.  Can be `small` (default), `large`, `xlarge`, or `pdf`.
* `with_previews` (Boolean): Include file preview information?
* `with_priority_color` (Boolean): Include file priority color information?


---

## Upload File

```
FileUploadPart file = File.create(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `path` (String): Required - Path to operate on.
* `action` (String): The action to perform.  Can be `append`, `attachment`, `end`, `upload`, `put`, or may not exist
* `etags[etag]` (String[]): etag identifier.
* `etags[part]` (Long[]): Part number.
* `length` (Long): Length of file.
* `mkdir_parents` (Boolean): Create parent directories if they do not exist?
* `part` (Long): Part if uploading a part.
* `parts` (Long): How many parts to fetch?
* `provided_mtime` (String): User provided modification time.
* `ref` (String): 
* `restart` (Long): File byte offset to restart from.
* `size` (Long): Size of file.
* `structure` (String): If copying folder, copy just the structure?
* `with_rename` (Boolean): Allow file rename instead of overwrite?
* `buffered_upload` (Boolean): If true, and the path refers to a destination not stored on Files.com (such as a remote server mount), the upload will be uploaded first to Files.com before being sent to the remote server mount. This can allow clients to upload using parallel parts to a remote server destination that does not offer parallel parts support natively.


---

## Update File/Folder Metadata

```
File file = File.update(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `path` (String): Required - Path to operate on.
* `custom_metadata` (Object): Custom metadata map of keys and values. Limited to 32 keys, 256 characters per key and 1024 characters per value.
* `provided_mtime` (String): Modified time of file.
* `priority_color` (String): Priority/Bookmark color of file.


---

## Delete File/Folder

```
void file = File.delete(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `path` (String): Required - Path to operate on.
* `recursive` (Boolean): If true, will recursively delete folders.  Otherwise, will error on non-empty folders.


---

## Find File/Folder by Path

```
File file = File.find(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `path` (String): Required - Path to operate on.
* `preview_size` (String): Request a preview size.  Can be `small` (default), `large`, `xlarge`, or `pdf`.
* `with_previews` (Boolean): Include file preview information?
* `with_priority_color` (Boolean): Include file priority color information?


---

## Copy File/Folder

```
FileAction file = File.copy(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `path` (String): Required - Path to operate on.
* `destination` (String): Required - Copy destination path.
* `structure` (Boolean): Copy structure only?
* `overwrite` (Boolean): Overwrite existing file(s) in the destination?


---

## Move File/Folder

```
FileAction file = File.move(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `path` (String): Required - Path to operate on.
* `destination` (String): Required - Move destination path.
* `overwrite` (Boolean): Overwrite existing file(s) in the destination?


---

## Begin File Upload

```
FileUploadPart file = File.beginUpload(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `path` (String): Required - Path to operate on.
* `mkdir_parents` (Boolean): Create parent directories if they do not exist?
* `part` (Long): Part if uploading a part.
* `parts` (Long): How many parts to fetch?
* `ref` (String): 
* `restart` (Long): File byte offset to restart from.
* `size` (Long): Total bytes of file being uploaded (include bytes being retained if appending/restarting).
* `with_rename` (Boolean): Allow file rename instead of overwrite?


---

## Download File

```
File file = File.find(path);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("with_previews", false);
parameters.put("with_priority_color", false);

file.download(parameters);
```

### Parameters

* `path` (String): Required - Path to operate on.
* `action` (String): Can be blank, `redirect` or `stat`.  If set to `stat`, we will return file information but without a download URL, and without logging a download.  If set to `redirect` we will serve a 302 redirect directly to the file.  This is used for integrations with Zapier, and is not recommended for most integrations.
* `preview_size` (String): Request a preview size.  Can be `small` (default), `large`, `xlarge`, or `pdf`.
* `with_previews` (Boolean): Include file preview information?
* `with_priority_color` (Boolean): Include file priority color information?


---

## Update File/Folder Metadata

```
File file = File.find(path);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("custom_metadata", {"key":"value"});
parameters.put("provided_mtime", "2000-01-01T01:00:00Z");
parameters.put("priority_color", "red");

file.update(parameters);
```

### Parameters

* `path` (String): Required - Path to operate on.
* `custom_metadata` (Object): Custom metadata map of keys and values. Limited to 32 keys, 256 characters per key and 1024 characters per value.
* `provided_mtime` (String): Modified time of file.
* `priority_color` (String): Priority/Bookmark color of file.


---

## Delete File/Folder

```
File file = File.find(path);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("recursive", false);

file.delete(parameters);
```

### Parameters

* `path` (String): Required - Path to operate on.
* `recursive` (Boolean): If true, will recursively delete folders.  Otherwise, will error on non-empty folders.


---

## Copy File/Folder

```
File file = File.find(path);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("destination", "destination");
parameters.put("structure", false);
parameters.put("overwrite", false);

file.copy(parameters);
```

### Parameters

* `path` (String): Required - Path to operate on.
* `destination` (String): Required - Copy destination path.
* `structure` (Boolean): Copy structure only?
* `overwrite` (Boolean): Overwrite existing file(s) in the destination?


---

## Move File/Folder

```
File file = File.find(path);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("destination", "destination");
parameters.put("overwrite", false);

file.move(parameters);
```

### Parameters

* `path` (String): Required - Path to operate on.
* `destination` (String): Required - Move destination path.
* `overwrite` (Boolean): Overwrite existing file(s) in the destination?


---

## Begin File Upload

```
File file = File.find(path);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("mkdir_parents", false);
parameters.put("part", 1);
parameters.put("parts", 1);
parameters.put("ref", "upload-1");
parameters.put("restart", 1);
parameters.put("size", 1);
parameters.put("with_rename", false);

file.beginUpload(parameters);
```

### Parameters

* `path` (String): Required - Path to operate on.
* `mkdir_parents` (Boolean): Create parent directories if they do not exist?
* `part` (Long): Part if uploading a part.
* `parts` (Long): How many parts to fetch?
* `ref` (String): 
* `restart` (Long): File byte offset to restart from.
* `size` (Long): Total bytes of file being uploaded (include bytes being retained if appending/restarting).
* `with_rename` (Boolean): Allow file rename instead of overwrite?
