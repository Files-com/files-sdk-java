# Files.Models.BundleNotification

## Example BundleNotification Object

```
{
  "bundle_id": 1,
  "id": 1,
  "notify_on_registration": true,
  "notify_on_upload": true,
  "user_id": 1
}
```

* `bundle_id` / `bundleId`  (int64): Bundle ID to notify on
* `id` / `id`  (int64): Bundle Notification ID
* `notify_on_registration` / `notifyOnRegistration`  (boolean): Triggers bundle notification when a registration action occurs for it.
* `notify_on_upload` / `notifyOnUpload`  (boolean): Triggers bundle notification when a upload action occurs for it.
* `user_id` / `userId`  (int64): The id of the user to notify.


---

## List Bundle Notifications

```
List<BundleNotification> bundleNotification = BundleNotification.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via either the X-Files-Cursor-Next header or the X-Files-Cursor-Prev header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `bundle_id` (Long): Bundle ID to notify on


---

## Show Bundle Notification

```
List<BundleNotification> bundleNotification = BundleNotification.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Bundle Notification ID.


---

## Create Bundle Notification

```
BundleNotification bundleNotification = BundleNotification.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): Required - The id of the user to notify.
* `notify_on_registration` (Boolean): Triggers bundle notification when a registration action occurs for it.
* `notify_on_upload` (Boolean): Triggers bundle notification when a upload action occurs for it.
* `bundle_id` (Long): Required - Bundle ID to notify on


---

## Update Bundle Notification

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

## Delete Bundle Notification

```
BundleNotification bundleNotification = BundleNotification.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Bundle Notification ID.


---

## Update Bundle Notification

```
BundleNotification bundleNotification = BundleNotification.List()[0];

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("notify_on_registration", true);
parameters.put("notify_on_upload", true);

BundleNotification.Update(parameters);
```

### Parameters

* `id` (Long): Required - Bundle Notification ID.
* `notify_on_registration` (Boolean): Triggers bundle notification when a registration action occurs for it.
* `notify_on_upload` (Boolean): Triggers bundle notification when a upload action occurs for it.


---

## Delete Bundle Notification

```
BundleNotification bundleNotification = BundleNotification.List()[0];

HashMap<String, Object> parameters = new HashMap<>();


BundleNotification.Delete
```

### Parameters

* `id` (Long): Required - Bundle Notification ID.
