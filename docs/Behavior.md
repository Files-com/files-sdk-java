# Files.Models.Behavior

## Example Behavior Object

```
{
  "id": 1,
  "path": "example",
  "attachment_url": "example",
  "behavior": "webhook",
  "name": "example",
  "description": "example",
  "value": {
    "urls": [
      "https://example.com/webhook"
    ],
    "method": "POST",
    "encoding": "JSON"
  },
  "public_hosting_url": "example",
  "disable_parent_folder_behavior": true,
  "recursive": true,
  "inherited": true,
  "managed": true,
  "root_behavior_site_admin_only": true
}
```

* `id` / `id`  (int64): Folder behavior ID
* `path` / `path`  (string): Folder path.  Note that Behavior paths cannot be updated once initially set.  You will need to remove and re-create the behavior on the new path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `attachment_url` / `attachmentUrl`  (string): URL for attached file
* `behavior` / `behavior`  (string): Behavior type.
* `name` / `name`  (string): Name for this behavior.
* `description` / `description`  (string): Description for this behavior.
* `value` / `value`  (object): Settings for this behavior.  See the section above for an example value to provide here.  Formatting is different for each Behavior type.  May be sent as nested JSON or a single JSON-encoded string.  If using XML encoding for the API call, this data must be sent as a JSON-encoded string.
* `public_hosting_url` / `publicHostingUrl`  (string): Public URL for this publicly hosted folder when the `Serve Publicly` behavior has a key configured. When a Custom Domain with `public_hosting` destination is attached to this behavior, the URL uses that domain. Otherwise it uses the site's `subdomain.hosted-by-files.com` host, unless `custom_domain_only` is enabled, in which case no URL is returned.
* `disable_parent_folder_behavior` / `disableParentFolderBehavior`  (boolean): If true, disables the inherited behavior for this folder and its children. Valid only for behavior types that child folders may override, and requires recursive to be true. Rejected for all other behavior types.
* `recursive` / `recursive`  (boolean): Whether this behavior is recursive for this record. `always` behaviors are always `true`, `never` behaviors are always `false`, and `sometimes` behaviors may be either value.
* `inherited` / `inherited`  (boolean): If true, this behavior is inherited from a higher scope rather than owned by the requested workspace.
* `managed` / `managed`  (boolean): If true, this behavior is controlled by a parent-site policy and cannot be modified locally.
* `root_behavior_site_admin_only` / `rootBehaviorSiteAdminOnly`  (boolean): If true, this behavior may only be modified by a site admin because it is at the site root or disables a root behavior.
* `attachment_file` / `attachmentFile`  (file): Certain behaviors may require a file, for instance, the `watermark` behavior requires a watermark image. Attach that file here.
* `attachment_delete` / `attachmentDelete`  (boolean): If `true`, delete the file stored in `attachment`.


---

## List Behaviors

```java
HashMap<String, Object> parameters = new HashMap<>();
ListIterator<Behavior> behaviors = Behavior.list(parameters, null);
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `behavior`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `clickwrap_id`, `form_field_set_id`, `impacts_ui`, `remote_server_id` or `behavior`. Valid field combinations are `[ impacts_ui, behavior ]` and `[ behavior, remote_server_id ]`.


---

## Show Behavior

```java
HashMap<String, Object> parameters = new HashMap<>();
Behavior behavior = Behavior.find(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Behavior ID.


---

## List Behaviors by Path

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("ancestor_behaviors", false);
ListIterator<Behavior> behaviors = Behavior.listFor("path", parameters, null);
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `behavior`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `impacts_ui` and `behavior`. Valid field combinations are `[ impacts_ui, behavior ]`.
* `path` (String): Required - Path to operate on.
* `ancestor_behaviors` (Boolean): If `true`, behaviors above this path are shown.


---

## Create Behavior

```java
HashMap<String, Object> parameters = new HashMap<>();
HashMap<String, Object> map1 = new HashMap<>();
map1.put("urls", new Object[] {"https://example.com/webhook"});
map1.put("method", "POST");
map1.put("encoding", "JSON");
parameters.put("value", map1);
parameters.put("disable_parent_folder_behavior", true);
parameters.put("recursive", false);
parameters.put("name", "example");
parameters.put("description", "example");
parameters.put("path", "path");
parameters.put("behavior", "webhook");
Behavior behavior = Behavior.create(parameters, null);
```

### Parameters

* `value` (Object): This field stores data specific to the type of behavior. See The Behavior Types section for the accepted value for each type of behavior.
* `attachment_file` (byte[] or Attachment): Certain behaviors may require a file, for instance, the `watermark` behavior requires a watermark image. Attach that file here.
* `disable_parent_folder_behavior` (Boolean): If true, disables the inherited behavior for this folder and its children. Valid only for behavior types that child folders may override, and requires recursive to be true. Rejected for all other behavior types.
* `recursive` (Boolean): Whether the behavior should apply to child folders. This is only configurable for behavior types whose recursion mode is `sometimes`; `always` behaviors stay recursive and `never` behaviors stay non-recursive.
* `name` (String): Name for this behavior.
* `description` (String): Description for this behavior.
* `path` (String): Required - Path where this behavior should apply.
* `behavior` (String): Required - Behavior type.


---

## Test Webhook

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("url", "https://www.site.com/...");
parameters.put("method", "GET");
parameters.put("encoding", "RAW");
HashMap<String, Object> map1 = new HashMap<>();
map1.put("x-test-header", "testvalue");
parameters.put("headers", map1);
HashMap<String, Object> map2 = new HashMap<>();
map2.put("test-param", "testvalue");
parameters.put("body", map2);
Behavior.webhookTest(parameters, null);
```

### Parameters

* `url` (String): Required - URL for testing the webhook.
* `method` (String): HTTP request method (GET or POST).
* `encoding` (String): Encoding type for the webhook payload. Can be JSON, XML, or RAW (form data).
* `headers` (Object): Additional request headers to send via HTTP.
* `body` (Object): Additional body parameters to include in the webhook payload.
* `action` (String): Action for test body.


---

## Update Behavior

```java
HashMap<String, Object> parameters = new HashMap<>();
HashMap<String, Object> map1 = new HashMap<>();
map1.put("urls", new Object[] {"https://example.com/webhook"});
map1.put("method", "POST");
map1.put("encoding", "JSON");
parameters.put("value", map1);
parameters.put("disable_parent_folder_behavior", true);
parameters.put("recursive", false);
parameters.put("name", "example");
parameters.put("description", "example");
parameters.put("attachment_delete", false);
Behavior behavior = Behavior.update(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Behavior ID.
* `value` (Object): This field stores data specific to the type of behavior. See The Behavior Types section for the accepted value for each type of behavior.
* `attachment_file` (byte[] or Attachment): Certain behaviors may require a file, for instance, the `watermark` behavior requires a watermark image. Attach that file here.
* `disable_parent_folder_behavior` (Boolean): If true, disables the inherited behavior for this folder and its children. Valid only for behavior types that child folders may override, and requires recursive to be true. Rejected for all other behavior types.
* `recursive` (Boolean): Whether the behavior should apply to child folders. This is only configurable for behavior types whose recursion mode is `sometimes`; `always` behaviors stay recursive and `never` behaviors stay non-recursive.
* `name` (String): Name for this behavior.
* `description` (String): Description for this behavior.
* `attachment_delete` (Boolean): If `true`, delete the file stored in `attachment`.


---

## Delete Behavior

```java
HashMap<String, Object> parameters = new HashMap<>();
Behavior.delete(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Behavior ID.


---

## Update Behavior

```java
Behavior behavior = Behavior.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
HashMap<String, Object> map1 = new HashMap<>();
map1.put("urls", new Object[] {"https://example.com/webhook"});
map1.put("method", "POST");
map1.put("encoding", "JSON");
parameters.put("value", map1);
parameters.put("disable_parent_folder_behavior", true);
parameters.put("recursive", false);
parameters.put("name", "example");
parameters.put("description", "example");
parameters.put("attachment_delete", false);
Behavior result = behavior.update(parameters);
```

### Parameters

* `id` (Long): Required - Behavior ID.
* `value` (Object): This field stores data specific to the type of behavior. See The Behavior Types section for the accepted value for each type of behavior.
* `attachment_file` (byte[] or Attachment): Certain behaviors may require a file, for instance, the `watermark` behavior requires a watermark image. Attach that file here.
* `disable_parent_folder_behavior` (Boolean): If true, disables the inherited behavior for this folder and its children. Valid only for behavior types that child folders may override, and requires recursive to be true. Rejected for all other behavior types.
* `recursive` (Boolean): Whether the behavior should apply to child folders. This is only configurable for behavior types whose recursion mode is `sometimes`; `always` behaviors stay recursive and `never` behaviors stay non-recursive.
* `name` (String): Name for this behavior.
* `description` (String): Description for this behavior.
* `attachment_delete` (Boolean): If `true`, delete the file stored in `attachment`.


---

## Delete Behavior

```java
Behavior behavior = Behavior.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
behavior.delete(parameters);
```

### Parameters

* `id` (Long): Required - Behavior ID.
