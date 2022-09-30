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
* `group_id` / `groupId`  (int64): Notification group id
* `group_name` / `groupName`  (string): Group name if applicable
* `triggering_group_ids` / `triggeringGroupIds`  (array): Only notify on actions made by a member of one of the specified groups
* `triggering_user_ids` / `triggeringUserIds`  (array): Only notify on actions made one of the specified users
* `trigger_by_share_recipients` / `triggerByShareRecipients`  (boolean): Notify when actions are performed by a share recipient?
* `notify_user_actions` / `notifyUserActions`  (boolean): Trigger notification on notification user actions?
* `notify_on_copy` / `notifyOnCopy`  (boolean): Triggers notification when copying files to this path
* `notify_on_delete` / `notifyOnDelete`  (boolean): Triggers notification when deleting files from this path
* `notify_on_download` / `notifyOnDownload`  (boolean): Triggers notification when downloading files from this path
* `notify_on_move` / `notifyOnMove`  (boolean): Triggers notification when moving files to this path
* `notify_on_upload` / `notifyOnUpload`  (boolean): Triggers notification when uploading new files to this path
* `recursive` / `recursive`  (boolean): Enable notifications for each subfolder in this path
* `send_interval` / `sendInterval`  (string): The time interval that notifications are aggregated to
* `message` / `message`  (string): Custom message to include in notification emails.
* `triggering_filenames` / `triggeringFilenames`  (array): Array of filenames (possibly with wildcards) to match for action path
* `unsubscribed` / `unsubscribed`  (boolean): Is the user unsubscribed from this notification?
* `unsubscribed_reason` / `unsubscribedReason`  (string): The reason that the user unsubscribed
* `user_id` / `userId`  (int64): Notification user ID
* `username` / `username`  (string): Notification username
* `suppressed_email` / `suppressedEmail`  (boolean): If true, it means that the recipient at this user's email address has manually unsubscribed from all emails, or had their email "hard bounce", which means that we are unable to send mail to this user's current email address. Notifications will resume if the user changes their email address.


---

## List Notifications

```
List<Notification> notification = Notification.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): DEPRECATED: Show notifications for this User ID. Use `filter[user_id]` instead.
* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via either the X-Files-Cursor-Next header or the X-Files-Cursor-Prev header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `path`, `user_id` or `group_id`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `user_id`, `group_id` or `path`.
* `filter_gt` (Map<String, String>): If set, return records where the specified field is greater than the supplied value. Valid fields are `user_id`, `group_id` or `path`.
* `filter_gteq` (Map<String, String>): If set, return records where the specified field is greater than or equal to the supplied value. Valid fields are `user_id`, `group_id` or `path`.
* `filter_like` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `user_id`, `group_id` or `path`.
* `filter_lt` (Map<String, String>): If set, return records where the specified field is less than the supplied value. Valid fields are `user_id`, `group_id` or `path`.
* `filter_lteq` (Map<String, String>): If set, return records where the specified field is less than or equal to the supplied value. Valid fields are `user_id`, `group_id` or `path`.
* `group_id` (Long): DEPRECATED: Show notifications for this Group ID. Use `filter[group_id]` instead.
* `path` (String): Show notifications for this Path.
* `include_ancestors` (Boolean): If `include_ancestors` is `true` and `path` is specified, include notifications for any parent paths. Ignored if `path` is not specified.


---

## Show Notification

```
List<Notification> notification = Notification.find(
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
* `notify_on_delete` (Boolean): Triggers notification when deleting files from this path
* `notify_on_download` (Boolean): Triggers notification when downloading files from this path
* `notify_on_move` (Boolean): Triggers notification when moving files to this path
* `notify_on_upload` (Boolean): Triggers notification when uploading new files to this path
* `notify_user_actions` (Boolean): If `true` actions initiated by the user will still result in a notification
* `recursive` (Boolean): If `true`, enable notifications for each subfolder in this path
* `send_interval` (String): The time interval that notifications are aggregated by.  Can be `five_minutes`, `fifteen_minutes`, `hourly`, or `daily`.
* `message` (String): Custom message to include in notification emails.
* `triggering_filenames` (String[]): Array of filenames (possibly with wildcards) to match for action path
* `triggering_group_ids` (Long[]): Only notify on actions made by a member of one of the specified groups
* `triggering_user_ids` (Long[]): Only notify on actions made one of the specified users
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
* `notify_on_delete` (Boolean): Triggers notification when deleting files from this path
* `notify_on_download` (Boolean): Triggers notification when downloading files from this path
* `notify_on_move` (Boolean): Triggers notification when moving files to this path
* `notify_on_upload` (Boolean): Triggers notification when uploading new files to this path
* `notify_user_actions` (Boolean): If `true` actions initiated by the user will still result in a notification
* `recursive` (Boolean): If `true`, enable notifications for each subfolder in this path
* `send_interval` (String): The time interval that notifications are aggregated by.  Can be `five_minutes`, `fifteen_minutes`, `hourly`, or `daily`.
* `message` (String): Custom message to include in notification emails.
* `triggering_filenames` (String[]): Array of filenames (possibly with wildcards) to match for action path
* `triggering_group_ids` (Long[]): Only notify on actions made by a member of one of the specified groups
* `triggering_user_ids` (Long[]): Only notify on actions made one of the specified users
* `trigger_by_share_recipients` (Boolean): Notify when actions are performed by a share recipient?


---

## Delete Notification

```
Notification notification = Notification.delete(
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
Notification notification = Notification.List()[0];

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
* `notify_on_delete` (Boolean): Triggers notification when deleting files from this path
* `notify_on_download` (Boolean): Triggers notification when downloading files from this path
* `notify_on_move` (Boolean): Triggers notification when moving files to this path
* `notify_on_upload` (Boolean): Triggers notification when uploading new files to this path
* `notify_user_actions` (Boolean): If `true` actions initiated by the user will still result in a notification
* `recursive` (Boolean): If `true`, enable notifications for each subfolder in this path
* `send_interval` (String): The time interval that notifications are aggregated by.  Can be `five_minutes`, `fifteen_minutes`, `hourly`, or `daily`.
* `message` (String): Custom message to include in notification emails.
* `triggering_filenames` (String[]): Array of filenames (possibly with wildcards) to match for action path
* `triggering_group_ids` (Long[]): Only notify on actions made by a member of one of the specified groups
* `triggering_user_ids` (Long[]): Only notify on actions made one of the specified users
* `trigger_by_share_recipients` (Boolean): Notify when actions are performed by a share recipient?


---

## Delete Notification

```
Notification notification = Notification.List()[0];

HashMap<String, Object> parameters = new HashMap<>();


Notification.Delete
```

### Parameters

* `id` (Long): Required - Notification ID.
