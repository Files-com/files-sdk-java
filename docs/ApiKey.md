# Files.Models.ApiKey

## Example ApiKey Object

```
{
  "id": 1,
  "descriptive_label": "Site-wide API key for https://site.files.com/ (key ID #1)",
  "created_at": "2000-01-01T01:00:00Z",
  "expires_at": "2000-01-01T01:00:00Z",
  "key": "[key]",
  "last_use_at": "2000-01-01T01:00:00Z",
  "name": "My Main API Key",
  "path": "shared/docs",
  "permission_set": "full",
  "platform": "win32",
  "user_id": 1
}
```

* `id` / `id`  (int64): API Key ID
* `descriptive_label` / `descriptiveLabel`  (string): Unique label that describes this API key.  Useful for external systems where you may have API keys from multiple accounts and want a human-readable label for each key.
* `created_at` / `createdAt`  (date-time): Time which API Key was created
* `expires_at` / `expiresAt`  (date-time): API Key expiration date
* `key` / `key`  (string): API Key actual key string
* `last_use_at` / `lastUseAt`  (date-time): API Key last used - note this value is only updated once per 3 hour period, so the 'actual' time of last use may be up to 3 hours later than this timestamp.
* `name` / `name`  (string): Internal name for the API Key.  For your use.
* `path` / `path`  (string): Folder path restriction for this api key. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `permission_set` / `permissionSet`  (string): Permissions for this API Key.  Keys with the `desktop_app` permission set only have the ability to do the functions provided in our Desktop App (File and Share Link operations).  Additional permission sets may become available in the future, such as for a Site Admin to give a key with no administrator privileges.  If you have ideas for permission sets, please let us know.
* `platform` / `platform`  (string): If this API key represents a Desktop app, what platform was it created on?
* `user_id` / `userId`  (int64): User ID for the owner of this API Key.  May be blank for Site-wide API Keys.


---

## List Api Keys

```
List<ApiKey> apiKey = ApiKey.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `expires_at`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `expires_at`.
* `filter_gt` (Map<String, String>): If set, return records where the specified field is greater than the supplied value. Valid fields are `expires_at`.
* `filter_gteq` (Map<String, String>): If set, return records where the specified field is greater than or equal to the supplied value. Valid fields are `expires_at`.
* `filter_like` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `expires_at`.
* `filter_lt` (Map<String, String>): If set, return records where the specified field is less than the supplied value. Valid fields are `expires_at`.
* `filter_lteq` (Map<String, String>): If set, return records where the specified field is less than or equal to the supplied value. Valid fields are `expires_at`.


---

## Show information about current API key.  (Requires current API connection to be using an API key.)

```
List<ApiKey> apiKey = ApiKey.findCurrent(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```


---

## Show Api Key

```
List<ApiKey> apiKey = ApiKey.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Api Key ID.


---

## Create Api Key

```
ApiKey apiKey = ApiKey.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `name` (String): Internal name for the API Key.  For your use.
* `expires_at` (String): API Key expiration date
* `permission_set` (String): Permissions for this API Key.  Keys with the `desktop_app` permission set only have the ability to do the functions provided in our Desktop App (File and Share Link operations).  Additional permission sets may become available in the future, such as for a Site Admin to give a key with no administrator privileges.  If you have ideas for permission sets, please let us know.
* `path` (String): Folder path restriction for this api key.


---

## Update current API key.  (Requires current API connection to be using an API key.)

```
ApiKey apiKey = ApiKey.updateCurrent(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `expires_at` (String): API Key expiration date
* `name` (String): Internal name for the API Key.  For your use.
* `permission_set` (String): Permissions for this API Key.  Keys with the `desktop_app` permission set only have the ability to do the functions provided in our Desktop App (File and Share Link operations).  Additional permission sets may become available in the future, such as for a Site Admin to give a key with no administrator privileges.  If you have ideas for permission sets, please let us know.


---

## Update Api Key

```
ApiKey apiKey = ApiKey.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Api Key ID.
* `name` (String): Internal name for the API Key.  For your use.
* `expires_at` (String): API Key expiration date
* `permission_set` (String): Permissions for this API Key.  Keys with the `desktop_app` permission set only have the ability to do the functions provided in our Desktop App (File and Share Link operations).  Additional permission sets may become available in the future, such as for a Site Admin to give a key with no administrator privileges.  If you have ideas for permission sets, please let us know.


---

## Delete current API key.  (Requires current API connection to be using an API key.)

```
ApiKey apiKey = ApiKey.deleteCurrent(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```


---

## Delete Api Key

```
ApiKey apiKey = ApiKey.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Api Key ID.


---

## Update Api Key

```
ApiKey apiKey = ApiKey.List()[0];

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("name", "My Main API Key");
parameters.put("expires_at", "2000-01-01T01:00:00Z");
parameters.put("permission_set", "full");

ApiKey.Update(parameters);
```

### Parameters

* `id` (Long): Required - Api Key ID.
* `name` (String): Internal name for the API Key.  For your use.
* `expires_at` (String): API Key expiration date
* `permission_set` (String): Permissions for this API Key.  Keys with the `desktop_app` permission set only have the ability to do the functions provided in our Desktop App (File and Share Link operations).  Additional permission sets may become available in the future, such as for a Site Admin to give a key with no administrator privileges.  If you have ideas for permission sets, please let us know.


---

## Delete Api Key

```
ApiKey apiKey = ApiKey.List()[0];

HashMap<String, Object> parameters = new HashMap<>();


ApiKey.Delete
```

### Parameters

* `id` (Long): Required - Api Key ID.
