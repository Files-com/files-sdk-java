# Files.Models.BundleNotification

## Example BundleNotification Object

```
{
  "bundle_id": 1,
  "id": 1,
  "notify_on_registration": true,
  "notify_on_upload": true,
  "notify_user_id": 1
}
```

* `bundle_id` / `bundleId`  (int64): Bundle ID to notify on
* `id` / `id`  (int64): Bundle Notification ID
* `notify_on_registration` / `notifyOnRegistration`  (boolean): Triggers bundle notification when a registration action occurs for it.
* `notify_on_upload` / `notifyOnUpload`  (boolean): Triggers bundle notification when a upload action occurs for it.
* `notify_user_id` / `notifyUserId`  (int64): The id of the user to notify.
* `user_id` / `userId`  (int64): User ID.  Provide a value of `0` to operate the current session's user.


---

## List Share Link Notifications

```
ListIterator<BundleNotification> bundleNotification = BundleNotification.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `bundle_id`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `bundle_id`.


---

## Show Share Link Notification

```
BundleNotification bundleNotification = BundleNotification.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Bundle Notification ID.


---

## Create Share Link Notification

```
BundleNotification bundleNotification = BundleNotification.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `bundle_id` (Long): Required - Bundle ID to notify on
* `notify_user_id` (Long): The id of the user to notify.
* `notify_on_registration` (Boolean): Triggers bundle notification when a registration action occurs for it.
* `notify_on_upload` (Boolean): Triggers bundle notification when a upload action occurs for it.


---

## Update Share Link Notification

```
BundleNotification bundleNotification = BundleNotification.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Bundle Notification ID.
* `notify_on_registration` (Boolean): Triggers bundle notification when a registration action occurs for it.
* `notify_on_upload` (Boolean): Triggers bundle notification when a upload action occurs for it.


---

## Delete Share Link Notification

```
void bundleNotification = BundleNotification.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Bundle Notification ID.


---

## Update Share Link Notification

```
BundleNotification bundleNotification = BundleNotification.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("notify_on_registration", true);
parameters.put("notify_on_upload", true);

bundleNotification.update(parameters);
```

### Parameters

* `id` (Long): Required - Bundle Notification ID.
* `notify_on_registration` (Boolean): Triggers bundle notification when a registration action occurs for it.
* `notify_on_upload` (Boolean): Triggers bundle notification when a upload action occurs for it.


---

## Delete Share Link Notification

```
BundleNotification bundleNotification = BundleNotification.find(id);

HashMap<String, Object> parameters = new HashMap<>();

bundleNotification.delete(parameters);
```

### Parameters

* `id` (Long): Required - Bundle Notification ID.
