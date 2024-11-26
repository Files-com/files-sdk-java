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
    "key": "example value"
  },
  "disable_parent_folder_behavior": true,
  "recursive": true
}
```

* `id` / `id`  (int64): Folder behavior ID
* `path` / `path`  (string): Folder path.  Note that Behavior paths cannot be updated once initially set.  You will need to remove and re-create the behavior on the new path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `attachment_url` / `attachmentUrl`  (string): URL for attached file
* `behavior` / `behavior`  (string): Behavior type.
* `name` / `name`  (string): Name for this behavior.
* `description` / `description`  (string): Description for this behavior.
* `value` / `value`  (object): Settings for this behavior.  See the section above for an example value to provide here.  Formatting is different for each Behavior type.  May be sent as nested JSON or a single JSON-encoded string.  If using XML encoding for the API call, this data must be sent as a JSON-encoded string.
* `disable_parent_folder_behavior` / `disableParentFolderBehavior`  (boolean): If true, the parent folder's behavior will be disabled for this folder and its children.
* `recursive` / `recursive`  (boolean): Is behavior recursive?
* `attachment_file` / `attachmentFile`  (file): Certain behaviors may require a file, for instance, the `watermark` behavior requires a watermark image. Attach that file here.
* `attachment_delete` / `attachmentDelete`  (boolean): If `true`, delete the file stored in `attachment`.


---

## List Behaviors

```
ListIterator<Behavior> behavior = Behavior.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `behavior`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `impacts_ui` and `behavior`.
* `filter_prefix` (Map<String, String>): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `behavior`.


---

## Show Behavior

```
Behavior behavior = Behavior.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Behavior ID.


---

## List Behaviors by Path

```
ListIterator<Behavior> behavior = Behavior.listFor(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `behavior`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `impacts_ui` and `behavior`.
* `filter_prefix` (Map<String, String>): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `behavior`.
* `path` (String): Required - Path to operate on.
* `ancestor_behaviors` (Boolean): If `true`, behaviors above this path are shown.


---

## Create Behavior

```
Behavior behavior = Behavior.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `value` (String): This field stores a hash of data specific to the type of behavior. See The Behavior Types section for example values for each type of behavior.
* `attachment_file` (byte[]): Certain behaviors may require a file, for instance, the `watermark` behavior requires a watermark image. Attach that file here.
* `disable_parent_folder_behavior` (Boolean): If `true`, the parent folder's behavior will be disabled for this folder and its children. This is the main mechanism for canceling out a `recursive` behavior higher in the folder tree.
* `recursive` (Boolean): If `true`, behavior is treated as recursive, meaning that it impacts child folders as well.
* `name` (String): Name for this behavior.
* `description` (String): Description for this behavior.
* `path` (String): Required - Path where this behavior should apply.
* `behavior` (String): Required - Behavior type.


---

## Test Webhook

```
void behavior = Behavior.webhookTest(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `url` (String): Required - URL for testing the webhook.
* `method` (String): HTTP request method (GET or POST).
* `encoding` (String): Encoding type for the webhook payload. Can be JSON, XML, or RAW (form data).
* `headers` (Map<String, String>): Additional request headers to send via HTTP.
* `body` (Map<String, String>): Additional body parameters to include in the webhook payload.
* `action` (String): Action for test body.


---

## Update Behavior

```
Behavior behavior = Behavior.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Behavior ID.
* `value` (String): This field stores a hash of data specific to the type of behavior. See The Behavior Types section for example values for each type of behavior.
* `attachment_file` (byte[]): Certain behaviors may require a file, for instance, the `watermark` behavior requires a watermark image. Attach that file here.
* `disable_parent_folder_behavior` (Boolean): If `true`, the parent folder's behavior will be disabled for this folder and its children. This is the main mechanism for canceling out a `recursive` behavior higher in the folder tree.
* `recursive` (Boolean): If `true`, behavior is treated as recursive, meaning that it impacts child folders as well.
* `name` (String): Name for this behavior.
* `description` (String): Description for this behavior.
* `attachment_delete` (Boolean): If `true`, delete the file stored in `attachment`.


---

## Delete Behavior

```
void behavior = Behavior.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Behavior ID.


---

## Update Behavior

```
Behavior behavior = Behavior.Find(id);

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("value", "{\"method\": \"GET\"}");
parameters.put("disable_parent_folder_behavior", true);
parameters.put("recursive", true);
parameters.put("name", "example");
parameters.put("description", "example");
parameters.put("attachment_delete", true);

Behavior.Update(parameters);
```

### Parameters

* `id` (Long): Required - Behavior ID.
* `value` (String): This field stores a hash of data specific to the type of behavior. See The Behavior Types section for example values for each type of behavior.
* `attachment_file` (byte[]): Certain behaviors may require a file, for instance, the `watermark` behavior requires a watermark image. Attach that file here.
* `disable_parent_folder_behavior` (Boolean): If `true`, the parent folder's behavior will be disabled for this folder and its children. This is the main mechanism for canceling out a `recursive` behavior higher in the folder tree.
* `recursive` (Boolean): If `true`, behavior is treated as recursive, meaning that it impacts child folders as well.
* `name` (String): Name for this behavior.
* `description` (String): Description for this behavior.
* `attachment_delete` (Boolean): If `true`, delete the file stored in `attachment`.


---

## Delete Behavior

```
Behavior behavior = Behavior.Find(id);

HashMap<String, Object> parameters = new HashMap<>();


Behavior.Delete
```

### Parameters

* `id` (Long): Required - Behavior ID.
