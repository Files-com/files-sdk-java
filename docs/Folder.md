# Files.Models.Folder

## Example Folder Object

```
{
  "path": "path/file.txt",
  "display_name": "file.txt",
  "type": "file",
  "size": 1024,
  "mtime": "2000-01-01T01:00:00Z",
  "provided_mtime": "2000-01-01T01:00:00Z",
  "crc32": "70976923",
  "md5": "17c54824e9931a4688ca032d03f6663c",
  "mime_type": "application/octet-stream",
  "region": "us-east-1",
  "permissions": "rpw",
  "subfolders_locked?": true,
  "download_uri": "https://mysite.files.com/...",
  "priority_color": "red",
  "preview_id": 1,
  "preview": {
    "id": 1,
    "status": "complete",
    "download_uri": "https://mysite.files.com/...",
    "type": "complete",
    "size": 1024
  }
}
```

* `path` / `path`  (string): File/Folder path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `display_name` / `displayName`  (string): File/Folder display name
* `type` / `type`  (string): Type: `directory` or `file`.
* `size` / `size`  (int64): File/Folder size
* `mtime` / `mtime`  (date-time): File last modified date/time, according to the server.  This is the timestamp of the last Files.com operation of the file, regardless of what modified timestamp was sent.
* `provided_mtime` / `providedMtime`  (date-time): File last modified date/time, according to the client who set it.  Files.com allows desktop, FTP, SFTP, and WebDAV clients to set modified at times.  This allows Desktop<->Cloud syncing to preserve modified at times.
* `crc32` / `crc32`  (string): File CRC32 checksum. This is sometimes delayed, so if you get a blank response, wait and try again.
* `md5` / `md5`  (string): File MD5 checksum. This is sometimes delayed, so if you get a blank response, wait and try again.
* `mime_type` / `mimeType`  (string): MIME Type.  This is determined by the filename extension and is not stored separately internally.
* `region` / `region`  (string): Region location
* `permissions` / `permissions`  (string): A short string representing the current user's permissions.  Can be `r`,`w`,`p`, or any combination
* `subfolders_locked?` / `subfoldersLocked`  (boolean): Are subfolders locked and unable to be modified?
* `download_uri` / `downloadUri`  (string): Link to download file. Provided only in response to a download request.
* `priority_color` / `priorityColor`  (string): Bookmark/priority color of file/folder
* `preview_id` / `previewId`  (int64): File preview ID
* `preview` / `preview` : File preview


---

## List Folders by path

```
List<Folder> folder = Folder.listFor(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `path` (String): Required - Path to operate on.
* `filter` (String): If specified, will filter folders/files list by this string.  Wildcards of `*` and `?` are acceptable here.
* `preview_size` (String): Request a preview size.  Can be `small` (default), `large`, `xlarge`, or `pdf`.
* `search` (String): If `search_all` is `true`, provide the search string here.  Otherwise, this parameter acts like an alias of `filter`.
* `search_all` (Boolean): Search entire site?
* `with_previews` (Boolean): Include file previews?
* `with_priority_color` (Boolean): Include file priority color information?


---

## Create folder

```
Folder folder = Folder.create(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `path` (String): Required - Path to operate on.
