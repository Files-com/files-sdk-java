# Files.Models.Notification

## Example Notification Object

```
{
  "id": 1,
  "path": "",
  "group_id": 1,
  "group_name": "example",
  "triggering_group_ids": [
    1
  ],
  "triggering_user_ids": [
    1
  ],
  "trigger_by_share_recipients": true,
  "notify_user_actions": true,
  "notify_on_copy": true,
  "notify_on_delete": true,
  "notify_on_download": true,
  "notify_on_move": true,
  "notify_on_upload": true,
  "recursive": true,
  "send_interval": "fifteen_minutes",
  "message": "custom notification email message",
  "triggering_filenames": [
    "*.jpg",
    "notify_file.txt"
  ],
  "unsubscribed": true,
  "unsubscribed_reason": "example",
  "user_id": 1,
  "username": "User",
  "suppressed_email": true
}
```

* `id` / `id`  (int64): Notification ID
* `path` / `path`  (string): Folder path to notify on This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `group_id` / `groupId`  (int64): ID of Group to receive notifications
* `group_name` / `groupName`  (string): Group name, if a Group ID is set
* `triggering_group_ids` / `triggeringGroupIds`  (array(int64)): If set, will only notify on actions made by a member of one of the specified groups
* `triggering_user_ids` / `triggeringUserIds`  (array(int64)): If set, will onlynotify on actions made one of the specified users
* `trigger_by_share_recipients` / `triggerByShareRecipients`  (boolean): Notify when actions are performed by a share recipient?
* `notify_user_actions` / `notifyUserActions`  (boolean): If true, will send notifications about a user's own activity to that user.  If false, only activity performed by other users (or anonymous users) will be sent in notifications.
* `notify_on_copy` / `notifyOnCopy`  (boolean): Trigger on files copied to this path?
* `notify_on_delete` / `notifyOnDelete`  (boolean): Trigger on files deleted in this path?
* `notify_on_download` / `notifyOnDownload`  (boolean): Trigger on files downloaded in this path?
* `notify_on_move` / `notifyOnMove`  (boolean): Trigger on files moved to this path?
* `notify_on_upload` / `notifyOnUpload`  (boolean): Trigger on files created/uploaded/updated/changed in this path?
* `recursive` / `recursive`  (boolean): Apply notification recursively?  This will enable notifications for each subfolder.
* `send_interval` / `sendInterval`  (string): The time interval that notifications are aggregated to
* `message` / `message`  (string): Custom message to include in notification emails
* `triggering_filenames` / `triggeringFilenames`  (array(string)): Array of filenames (possibly with wildcards) to scope trigger
* `unsubscribed` / `unsubscribed`  (boolean): Is the user unsubscribed from this notification?
* `unsubscribed_reason` / `unsubscribedReason`  (string): The reason that the user unsubscribed
* `user_id` / `userId`  (int64): Notification user ID
* `username` / `username`  (string): Notification username
* `suppressed_email` / `suppressedEmail`  (boolean): If true, it means that the recipient at this user's email address has manually unsubscribed from all emails, or had their email "hard bounce", which means that we are unable to send mail to this user's current email address. Notifications will resume if the user changes their email address.


---

## List Notifications

```
ListIterator<Notification> notification = Notification.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): DEPRECATED: Show notifications for this User ID. Use `filter[user_id]` instead.
* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction (e.g. `sort_by[path]=desc`). Valid fields are `path`, `user_id` or `group_id`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `path`, `user_id` or `group_id`.
* `filter_prefix` (Map<String, String>): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `path`.
* `path` (String): Show notifications for this Path.
* `include_ancestors` (Boolean): If `include_ancestors` is `true` and `path` is specified, include notifications for any parent paths. Ignored if `path` is not specified.
* `group_id` (String): 


---

## Show Notification

```
Notification notification = Notification.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Notification ID.


---

## Create Notification

```
Notification notification = Notification.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): The id of the user to notify. Provide `user_id`, `username` or `group_id`.
* `notify_on_copy` (Boolean): If `true`, copying or moving resources into this path will trigger a notification, in addition to just uploads.
* `notify_on_delete` (Boolean): Trigger on files deleted in this path?
* `notify_on_download` (Boolean): Trigger on files downloaded in this path?
* `notify_on_move` (Boolean): Trigger on files moved to this path?
* `notify_on_upload` (Boolean): Trigger on files created/uploaded/updated/changed in this path?
* `notify_user_actions` (Boolean): If `true` actions initiated by the user will still result in a notification
* `recursive` (Boolean): If `true`, enable notifications for each subfolder in this path
* `send_interval` (String): The time interval that notifications are aggregated by.  Can be `five_minutes`, `fifteen_minutes`, `hourly`, or `daily`.
* `message` (String): Custom message to include in notification emails
* `triggering_filenames` (String[]): Array of filenames (possibly with wildcards) to scope trigger
* `triggering_group_ids` (Long[]): If set, will only notify on actions made by a member of one of the specified groups
* `triggering_user_ids` (Long[]): If set, will onlynotify on actions made one of the specified users
* `trigger_by_share_recipients` (Boolean): Notify when actions are performed by a share recipient?
* `group_id` (Long): The ID of the group to notify.  Provide `user_id`, `username` or `group_id`.
* `path` (String): Path
* `username` (String): The username of the user to notify.  Provide `user_id`, `username` or `group_id`.


---

## Update Notification

```
Notification notification = Notification.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Notification ID.
* `notify_on_copy` (Boolean): If `true`, copying or moving resources into this path will trigger a notification, in addition to just uploads.
* `notify_on_delete` (Boolean): Trigger on files deleted in this path?
* `notify_on_download` (Boolean): Trigger on files downloaded in this path?
* `notify_on_move` (Boolean): Trigger on files moved to this path?
* `notify_on_upload` (Boolean): Trigger on files created/uploaded/updated/changed in this path?
* `notify_user_actions` (Boolean): If `true` actions initiated by the user will still result in a notification
* `recursive` (Boolean): If `true`, enable notifications for each subfolder in this path
* `send_interval` (String): The time interval that notifications are aggregated by.  Can be `five_minutes`, `fifteen_minutes`, `hourly`, or `daily`.
* `message` (String): Custom message to include in notification emails
* `triggering_filenames` (String[]): Array of filenames (possibly with wildcards) to scope trigger
* `triggering_group_ids` (Long[]): If set, will only notify on actions made by a member of one of the specified groups
* `triggering_user_ids` (Long[]): If set, will onlynotify on actions made one of the specified users
* `trigger_by_share_recipients` (Boolean): Notify when actions are performed by a share recipient?


---

## Delete Notification

```
void notification = Notification.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Notification ID.


---

## Update Notification

```
Notification notification = Notification.Find(id);

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("notify_on_copy", true);
parameters.put("notify_on_delete", true);
parameters.put("notify_on_download", true);
parameters.put("notify_on_move", true);
parameters.put("notify_on_upload", true);
parameters.put("notify_user_actions", true);
parameters.put("recursive", true);
parameters.put("send_interval", "daily");
parameters.put("message", "custom notification email message");
parameters.put("triggering_filenames", ["*.jpg","notify_file.txt"]);
parameters.put("triggering_group_ids", [1]);
parameters.put("triggering_user_ids", [1]);
parameters.put("trigger_by_share_recipients", true);

Notification.Update(parameters);
```

### Parameters

* `id` (Long): Required - Notification ID.
* `notify_on_copy` (Boolean): If `true`, copying or moving resources into this path will trigger a notification, in addition to just uploads.
* `notify_on_delete` (Boolean): Trigger on files deleted in this path?
* `notify_on_download` (Boolean): Trigger on files downloaded in this path?
* `notify_on_move` (Boolean): Trigger on files moved to this path?
* `notify_on_upload` (Boolean): Trigger on files created/uploaded/updated/changed in this path?
* `notify_user_actions` (Boolean): If `true` actions initiated by the user will still result in a notification
* `recursive` (Boolean): If `true`, enable notifications for each subfolder in this path
* `send_interval` (String): The time interval that notifications are aggregated by.  Can be `five_minutes`, `fifteen_minutes`, `hourly`, or `daily`.
* `message` (String): Custom message to include in notification emails
* `triggering_filenames` (String[]): Array of filenames (possibly with wildcards) to scope trigger
* `triggering_group_ids` (Long[]): If set, will only notify on actions made by a member of one of the specified groups
* `triggering_user_ids` (Long[]): If set, will onlynotify on actions made one of the specified users
* `trigger_by_share_recipients` (Boolean): Notify when actions are performed by a share recipient?


---

## Delete Notification

```
Notification notification = Notification.Find(id);

HashMap<String, Object> parameters = new HashMap<>();


Notification.Delete
```

### Parameters

* `id` (Long): Required - Notification ID.
