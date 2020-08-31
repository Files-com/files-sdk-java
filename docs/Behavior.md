# Files.Models.Behavior

## Example Behavior Object

```
{
  "id": 1,
  "path": "",
  "attachment_url": "",
  "behavior": "webhook",
  "value": {
    "method": "GET"
  }
}
```

* `id` / `id`  (int64): Folder behavior ID
* `path` / `path`  (string): Folder path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `attachment_url` / `attachmentUrl`  (string): URL for attached file
* `behavior` / `behavior`  (string): Behavior type.
* `value` / `value`  (object): Settings for this behavior.  See the section above for an example value to provide here.  Formatting is different for each Behavior type.  May be sent as nested JSON or a single JSON-encoded string.  If using XML encoding for the API call, this data must be sent as a JSON-encoded string.
* `attachment_file` / `attachmentFile`  (file): Certain behaviors may require a file, for instance, the "watermark" behavior requires a watermark image


---

## List Behaviors

```
List<Behavior> behavior = Behavior.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `page` (Long): Current page number.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `action` (String): Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
* `cursor` (String): Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `site_id` and `behavior`.
* `filter` (Map<String, String>): If set, return records where the specifiied field is equal to the supplied value. Valid fields are `behavior`.
* `filter_gt` (Map<String, String>): If set, return records where the specifiied field is greater than the supplied value. Valid fields are `behavior`.
* `filter_gteq` (Map<String, String>): If set, return records where the specifiied field is greater than or equal to the supplied value. Valid fields are `behavior`.
* `filter_like` (Map<String, String>): If set, return records where the specifiied field is equal to the supplied value. Valid fields are `behavior`.
* `filter_lt` (Map<String, String>): If set, return records where the specifiied field is less than the supplied value. Valid fields are `behavior`.
* `filter_lteq` (Map<String, String>): If set, return records where the specifiied field is less than or equal to the supplied value. Valid fields are `behavior`.
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

* `page` (Long): Current page number.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `action` (String): Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
* `cursor` (String): Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `site_id` and `behavior`.
* `filter` (Map<String, String>): If set, return records where the specifiied field is equal to the supplied value. Valid fields are `behavior`.
* `filter_gt` (Map<String, String>): If set, return records where the specifiied field is greater than the supplied value. Valid fields are `behavior`.
* `filter_gteq` (Map<String, String>): If set, return records where the specifiied field is greater than or equal to the supplied value. Valid fields are `behavior`.
* `filter_like` (Map<String, String>): If set, return records where the specifiied field is equal to the supplied value. Valid fields are `behavior`.
* `filter_lt` (Map<String, String>): If set, return records where the specifiied field is less than the supplied value. Valid fields are `behavior`.
* `filter_lteq` (Map<String, String>): If set, return records where the specifiied field is less than or equal to the supplied value. Valid fields are `behavior`.
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

* `value` (String): The value of the folder behavior.  Can be a integer, array, or hash depending on the type of folder behavior.
* `attachment_file` (byte[]): Certain behaviors may require a file, for instance, the "watermark" behavior requires a watermark image
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
* `value` (String): The value of the folder behavior.  Can be a integer, array, or hash depending on the type of folder behavior.
* `attachment_file` (byte[]): Certain behaviors may require a file, for instance, the "watermark" behavior requires a watermark image
* `behavior` (String): Behavior type.
* `path` (String): Folder behaviors path.


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
Behavior behavior = Behavior.ListFor(path)[0];

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("value", "{\"method\": \"GET\"}");
parameters.put("behavior", "webhook");

Behavior.Update(parameters);
```

### Parameters

* `id` (Long): Required - Behavior ID.
* `value` (String): The value of the folder behavior.  Can be a integer, array, or hash depending on the type of folder behavior.
* `attachment_file` (byte[]): Certain behaviors may require a file, for instance, the "watermark" behavior requires a watermark image
* `behavior` (String): Behavior type.
* `path` (String): Folder behaviors path.


---

## Delete Behavior

```
Behavior behavior = Behavior.ListFor(path)[0];

HashMap<String, Object> parameters = new HashMap<>();


Behavior.Delete
```

### Parameters

* `id` (Long): Required - Behavior ID.
