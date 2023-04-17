# Files.Models.File

## Example File Object

```
{
  "path": "path/file.txt",
  "display_name": "file.txt",
  "type": "file",
  "size": 1024,
  "created_at": "2000-01-01T01:00:00Z",
  "mtime": "2000-01-01T01:00:00Z",
  "provided_mtime": "2000-01-01T01:00:00Z",
  "crc32": "70976923",
  "md5": "17c54824e9931a4688ca032d03f6663c",
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

* `path` / `path`  (string): File/Folder path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `display_name` / `displayName`  (string): File/Folder display name
* `type` / `type`  (string): Type: `directory` or `file`.
* `size` / `size`  (int64): File/Folder size
* `created_at` / `createdAt`  (date-time): File created date/time
* `mtime` / `mtime`  (date-time): File last modified date/time, according to the server.  This is the timestamp of the last Files.com operation of the file, regardless of what modified timestamp was sent.
* `provided_mtime` / `providedMtime`  (date-time): File last modified date/time, according to the client who set it.  Files.com allows desktop, FTP, SFTP, and WebDAV clients to set modified at times.  This allows Desktop<->Cloud syncing to preserve modified at times.
* `crc32` / `crc32`  (string): File CRC32 checksum. This is sometimes delayed, so if you get a blank response, wait and try again.
* `md5` / `md5`  (string): File MD5 checksum. This is sometimes delayed, so if you get a blank response, wait and try again.
* `mime_type` / `mimeType`  (string): MIME Type.  This is determined by the filename extension and is not stored separately internally.
* `region` / `region`  (string): Region location
* `permissions` / `permissions`  (string): A short string representing the current user's permissions.  Can be `r`,`w`,`d`, `l` or any combination
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


---

## Download file

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

## Upload file

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


---

## Update file/folder metadata

```
File file = File.update(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `path` (String): Required - Path to operate on.
* `provided_mtime` (String): Modified time of file.
* `priority_color` (String): Priority/Bookmark color of file.


---

## Delete file/folder

```
File file = File.delete(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `path` (String): Required - Path to operate on.
* `recursive` (Boolean): If true, will recursively delete folers.  Otherwise, will error on non-empty folders.


---

## Find file/folder by path

```
List<File> file = File.find(
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

## Copy file/folder

```
File file = File.copy(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `path` (String): Required - Path to operate on.
* `destination` (String): Required - Copy destination path.
* `structure` (Boolean): Copy structure only?


---

## Move file/folder

```
File file = File.move(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `path` (String): Required - Path to operate on.
* `destination` (String): Required - Move destination path.


---

## Begin file upload

```
File file = File.beginUpload(
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

## Download file

```
HashMap<Object, String> attributes = new HashMap<>();
File file = new File(attributes);

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("with_previews", true);
parameters.put("with_priority_color", true);

File.Download(parameters);
```

### Parameters

* `path` (String): Required - Path to operate on.
* `action` (String): Can be blank, `redirect` or `stat`.  If set to `stat`, we will return file information but without a download URL, and without logging a download.  If set to `redirect` we will serve a 302 redirect directly to the file.  This is used for integrations with Zapier, and is not recommended for most integrations.
* `preview_size` (String): Request a preview size.  Can be `small` (default), `large`, `xlarge`, or `pdf`.
* `with_previews` (Boolean): Include file preview information?
* `with_priority_color` (Boolean): Include file priority color information?


---

## Update file/folder metadata

```
HashMap<Object, String> attributes = new HashMap<>();
File file = new File(attributes);

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("provided_mtime", "2000-01-01T01:00:00Z");
parameters.put("priority_color", "red");

File.Update(parameters);
```

### Parameters

* `path` (String): Required - Path to operate on.
* `provided_mtime` (String): Modified time of file.
* `priority_color` (String): Priority/Bookmark color of file.


---

## Delete file/folder

```
HashMap<Object, String> attributes = new HashMap<>();
File file = new File(attributes);

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("recursive", true);

File.Delete(parameters);
```

### Parameters

* `path` (String): Required - Path to operate on.
* `recursive` (Boolean): If true, will recursively delete folers.  Otherwise, will error on non-empty folders.


---

## Copy file/folder

```
HashMap<Object, String> attributes = new HashMap<>();
File file = new File(attributes);

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("destination", "destination");
parameters.put("structure", true);

File.Copy(parameters);
```

### Parameters

* `path` (String): Required - Path to operate on.
* `destination` (String): Required - Copy destination path.
* `structure` (Boolean): Copy structure only?


---

## Move file/folder

```
HashMap<Object, String> attributes = new HashMap<>();
File file = new File(attributes);

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("destination", "destination");

File.Move(parameters);
```

### Parameters

* `path` (String): Required - Path to operate on.
* `destination` (String): Required - Move destination path.


---

## Begin file upload

```
HashMap<Object, String> attributes = new HashMap<>();
File file = new File(attributes);

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("mkdir_parents", true);
parameters.put("part", 1);
parameters.put("parts", 1);
parameters.put("ref", "upload-1");
parameters.put("restart", 1);
parameters.put("size", 1);
parameters.put("with_rename", true);

File.BeginUpload(parameters);
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
