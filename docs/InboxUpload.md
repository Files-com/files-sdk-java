# Files.Models.InboxUpload

## Example InboxUpload Object

```
{
  "inbox_registration": "",
  "path": "a/b/test.txt",
  "created_at": "2020-01-01 00:00:00"
}
```

* `inbox_registration` / `inboxRegistration` : 
* `path` / `path`  (string): Upload path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `created_at` / `createdAt`  (date-time): Upload date/time


---

## List Inbox Uploads

```
List<InboxUpload> inboxUpload = InboxUpload.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `inbox_registration_id` (Long): InboxRegistration ID
* `inbox_id` (Long): Inbox ID
