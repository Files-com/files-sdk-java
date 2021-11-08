# Files.Models.Behavior

## Example Behavior Object

```
{
  "id": 1,
  "path": "",
  "attachment_url": "",
  "behavior": "webhook",
  "name": "",
  "description": "",
  "value": {
    "method": "GET"
  }
}
```

* `id` / `id`  (int64): Folder behavior ID
* `path` / `path`  (string): Folder path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `attachment_url` / `attachmentUrl`  (string): URL for attached file
* `behavior` / `behavior`  (string): Behavior type.
* `name` / `name`  (string): Name for this behavior.
* `description` / `description`  (string): Description for this behavior.
* `value` / `value`  (object): Settings for this behavior.  See the section above for an example value to provide here.  Formatting is different for each Behavior type.  May be sent as nested JSON or a single JSON-encoded string.  If using XML encoding for the API call, this data must be sent as a JSON-encoded string.
* `attachment_file` / `attachmentFile`  (file): Certain behaviors may require a file, for instance, the "watermark" behavior requires a watermark image
* `attachment_delete` / `attachmentDelete`  (boolean): If true, will delete the file stored in attachment


---

## List Behaviors

```
List<Behavior> behavior = Behavior.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `behavior`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `behavior`.
* `filter_gt` (Map<String, String>): If set, return records where the specified field is greater than the supplied value. Valid fields are `behavior`.
* `filter_gteq` (Map<String, String>): If set, return records where the specified field is greater than or equal to the supplied value. Valid fields are `behavior`.
* `filter_like` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `behavior`.
* `filter_lt` (Map<String, String>): If set, return records where the specified field is less than the supplied value. Valid fields are `behavior`.
* `filter_lteq` (Map<String, String>): If set, return records where the specified field is less than or equal to the supplied value. Valid fields are `behavior`.
* `behavior` (String): If set, only shows folder behaviors matching this behavior type.


---

## Show Behavior

```
List<Behavior> behavior = Behavior.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Behavior ID.


---

## List Behaviors by path

```
List<Behavior> behavior = Behavior.listFor(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `behavior`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `behavior`.
* `filter_gt` (Map<String, String>): If set, return records where the specified field is greater than the supplied value. Valid fields are `behavior`.
* `filter_gteq` (Map<String, String>): If set, return records where the specified field is greater than or equal to the supplied value. Valid fields are `behavior`.
* `filter_like` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `behavior`.
* `filter_lt` (Map<String, String>): If set, return records where the specified field is less than the supplied value. Valid fields are `behavior`.
* `filter_lteq` (Map<String, String>): If set, return records where the specified field is less than or equal to the supplied value. Valid fields are `behavior`.
* `path` (String): Required - Path to operate on.
* `recursive` (String): Show behaviors above this path?
* `behavior` (String): DEPRECATED: If set only shows folder behaviors matching this behavior type. Use `filter[behavior]` instead.


---

## Create Behavior

```
Behavior behavior = Behavior.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `value` (String): The value of the folder behavior.  Can be a integer, array, or hash depending on the type of folder behavior. See The Behavior Types section for example values for each type of behavior.
* `attachment_file` (byte[]): Certain behaviors may require a file, for instance, the "watermark" behavior requires a watermark image
* `name` (String): Name for this behavior.
* `description` (String): Description for this behavior.
* `path` (String): Required - Folder behaviors path.
* `behavior` (String): Required - Behavior type.


---

## Test webhook

```
Behavior behavior = Behavior.webhookTest(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `url` (String): Required - URL for testing the webhook.
* `method` (String): HTTP method(GET or POST).
* `encoding` (String): HTTP encoding method.  Can be JSON, XML, or RAW (form data).
* `headers` (Map<String, String>): Additional request headers.
* `body` (Map<String, String>): Additional body parameters.
* `action` (String): action for test body


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
* `value` (String): The value of the folder behavior.  Can be a integer, array, or hash depending on the type of folder behavior. See The Behavior Types section for example values for each type of behavior.
* `attachment_file` (byte[]): Certain behaviors may require a file, for instance, the "watermark" behavior requires a watermark image
* `name` (String): Name for this behavior.
* `description` (String): Description for this behavior.
* `behavior` (String): Behavior type.
* `path` (String): Folder behaviors path.
* `attachment_delete` (Boolean): If true, will delete the file stored in attachment


---

## Delete Behavior

```
Behavior behavior = Behavior.delete(
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
Behavior behavior = Behavior.List()[0];

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("value", "{\"method\": \"GET\"}");
parameters.put("behavior", "webhook");
parameters.put("attachment_delete", true);

Behavior.Update(parameters);
```

### Parameters

* `id` (Long): Required - Behavior ID.
* `value` (String): The value of the folder behavior.  Can be a integer, array, or hash depending on the type of folder behavior. See The Behavior Types section for example values for each type of behavior.
* `attachment_file` (byte[]): Certain behaviors may require a file, for instance, the "watermark" behavior requires a watermark image
* `name` (String): Name for this behavior.
* `description` (String): Description for this behavior.
* `behavior` (String): Behavior type.
* `path` (String): Folder behaviors path.
* `attachment_delete` (Boolean): If true, will delete the file stored in attachment


---

## Delete Behavior

```
Behavior behavior = Behavior.List()[0];

HashMap<String, Object> parameters = new HashMap<>();


Behavior.Delete
```

### Parameters

* `id` (Long): Required - Behavior ID.
