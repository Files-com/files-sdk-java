# Files.Models.BundleNotification

## Example BundleNotification Object

```
{
  "bundle_id": 1,
  "id": 1,
  "notify_on_registration": true,
  "notify_on_upload": true,
  "notify_current_user": true,
  "notify_user_id": 1,
  "workspace_id": 1
}
```

* `bundle_id` / `bundleId`  (int64): Bundle ID to notify on
* `id` / `id`  (int64): Bundle Notification ID
* `notify_on_registration` / `notifyOnRegistration`  (boolean): Triggers bundle notification when a registration action occurs for it.
* `notify_on_upload` / `notifyOnUpload`  (boolean): Triggers bundle notification when a upload action occurs for it.
* `notify_current_user` / `notifyCurrentUser`  (boolean): Is the current user the user to notify?
* `notify_user_id` / `notifyUserId`  (int64): The id of the user to notify.
* `workspace_id` / `workspaceId`  (int64): Workspace ID. `0` means the default workspace.
* `user_id` / `userId`  (int64): User ID.  Provide a value of `0` to operate the current session's user.


---

## List Share Link Notifications

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("user_id", 1L);
parameters.put("bundle_id", 1L);
ListIterator<BundleNotification> bundleNotifications = BundleNotification.list(parameters, null);
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id` and `bundle_id`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `bundle_id`.
* `bundle_id` (Long): Bundle ID


---

## Show Share Link Notification

```java
HashMap<String, Object> parameters = new HashMap<>();
BundleNotification bundleNotification = BundleNotification.find(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Bundle Notification ID.


---

## Create Share Link Notification

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("user_id", 1L);
parameters.put("bundle_id", 1L);
parameters.put("notify_user_id", 1L);
parameters.put("notify_on_registration", true);
parameters.put("notify_on_upload", true);
BundleNotification bundleNotification = BundleNotification.create(parameters, null);
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `bundle_id` (Long): Required - Bundle ID to notify on
* `notify_user_id` (Long): The id of the user to notify.
* `notify_on_registration` (Boolean): Triggers bundle notification when a registration action occurs for it.
* `notify_on_upload` (Boolean): Triggers bundle notification when a upload action occurs for it.


---

## Update Share Link Notification

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("notify_on_registration", true);
parameters.put("notify_on_upload", true);
BundleNotification bundleNotification = BundleNotification.update(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Bundle Notification ID.
* `notify_on_registration` (Boolean): Triggers bundle notification when a registration action occurs for it.
* `notify_on_upload` (Boolean): Triggers bundle notification when a upload action occurs for it.


---

## Delete Share Link Notification

```java
HashMap<String, Object> parameters = new HashMap<>();
BundleNotification.delete(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Bundle Notification ID.


---

## Update Share Link Notification

```java
BundleNotification bundleNotification = BundleNotification.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("notify_on_registration", true);
parameters.put("notify_on_upload", true);
BundleNotification result = bundleNotification.update(parameters);
```

### Parameters

* `id` (Long): Required - Bundle Notification ID.
* `notify_on_registration` (Boolean): Triggers bundle notification when a registration action occurs for it.
* `notify_on_upload` (Boolean): Triggers bundle notification when a upload action occurs for it.


---

## Delete Share Link Notification

```java
BundleNotification bundleNotification = BundleNotification.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
bundleNotification.delete(parameters);
```

### Parameters

* `id` (Long): Required - Bundle Notification ID.
