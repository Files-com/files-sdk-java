# Files.Models.ApiKey

## Example ApiKey Object

```
{
  "id": 1,
  "descriptive_label": "Site-wide API key for https://site.files.com/ (key ID #1)",
  "description": "example",
  "created_at": "2000-01-01T01:00:00Z",
  "expires_at": "2000-01-01T01:00:00Z",
  "key": "[key]",
  "aws_style_credentials": true,
  "aws_access_key_id": "[aws_access_key_id]",
  "aws_secret_key": "[aws_secret_key]",
  "last_use_at": "2000-01-01T01:00:00Z",
  "name": "My Main API Key",
  "permission_set": "full",
  "platform": "win32",
  "url": "example",
  "user_id": 1
}
```

* `id` / `id`  (int64): API Key ID
* `descriptive_label` / `descriptiveLabel`  (string): Unique label that describes this API key.  Useful for external systems where you may have API keys from multiple accounts and want a human-readable label for each key.
* `description` / `description`  (string): User-supplied description of API key.
* `created_at` / `createdAt`  (date-time): Time which API Key was created
* `expires_at` / `expiresAt`  (date-time): API Key expiration date
* `key` / `key`  (string): API Key actual key string
* `aws_style_credentials` / `awsStyleCredentials`  (boolean): If `true`, this API key will be usable with AWS-compatible endpoints, such as our Inbound S3-compatible endpoint.
* `aws_access_key_id` / `awsAccessKeyId`  (string): AWS Access Key ID to use with AWS-compatible endpoints, such as our Inbound S3-compatible endpoint.
* `aws_secret_key` / `awsSecretKey`  (string): AWS Secret Key to use with AWS-compatible endpoints, such as our Inbound S3-compatible endpoint.
* `last_use_at` / `lastUseAt`  (date-time): API Key last used - note this value is only updated once per 3 hour period, so the 'actual' time of last use may be up to 3 hours later than this timestamp.
* `name` / `name`  (string): Internal name for the API Key.  For your use.
* `permission_set` / `permissionSet`  (string): Permissions for this API Key. It must be full for site-wide API Keys.  Keys with the `desktop_app` permission set only have the ability to do the functions provided in our Desktop App (File and Share Link operations). Keys with the `office_integration` permission set are auto generated, and automatically expire, to allow users to interact with office integration platforms. Additional permission sets may become available in the future, such as for a Site Admin to give a key with no administrator privileges.  If you have ideas for permission sets, please let us know.
* `platform` / `platform`  (string): If this API key represents a Desktop app, what platform was it created on?
* `url` / `url`  (string): URL for API host.
* `user_id` / `userId`  (int64): User ID for the owner of this API Key.  May be blank for Site-wide API Keys.
* `path` / `path`  (string): Folder path restriction for `office_integration` permission set API keys.


---

## List API Keys

```
ListIterator<ApiKey> apiKey = ApiKey.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `expires_at`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `expires_at`.
* `filter_gt` (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `expires_at`.
* `filter_gteq` (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `expires_at`.
* `filter_lt` (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `expires_at`.
* `filter_lteq` (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `expires_at`.


---

## Show information about current API key.  (Requires current API connection to be using an API key.)

```
ApiKey apiKey = ApiKey.findCurrent(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```


---

## Show API Key

```
ApiKey apiKey = ApiKey.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Api Key ID.


---

## Create API Key

```
ApiKey apiKey = ApiKey.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `description` (String): User-supplied description of API key.
* `expires_at` (String): API Key expiration date
* `permission_set` (String): Permissions for this API Key. It must be full for site-wide API Keys.  Keys with the `desktop_app` permission set only have the ability to do the functions provided in our Desktop App (File and Share Link operations). Keys with the `office_integration` permission set are auto generated, and automatically expire, to allow users to interact with office integration platforms. Additional permission sets may become available in the future, such as for a Site Admin to give a key with no administrator privileges.  If you have ideas for permission sets, please let us know.
* `name` (String): Required - Internal name for the API Key.  For your use.
* `aws_style_credentials` (Boolean): If `true`, this API key will be usable with AWS-compatible endpoints, such as our Inbound S3-compatible endpoint.
* `path` (String): Folder path restriction for `office_integration` permission set API keys.


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
* `permission_set` (String): Permissions for this API Key. It must be full for site-wide API Keys.  Keys with the `desktop_app` permission set only have the ability to do the functions provided in our Desktop App (File and Share Link operations). Keys with the `office_integration` permission set are auto generated, and automatically expire, to allow users to interact with office integration platforms. Additional permission sets may become available in the future, such as for a Site Admin to give a key with no administrator privileges.  If you have ideas for permission sets, please let us know.


---

## Update API Key

```
ApiKey apiKey = ApiKey.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Api Key ID.
* `description` (String): User-supplied description of API key.
* `expires_at` (String): API Key expiration date
* `permission_set` (String): Permissions for this API Key. It must be full for site-wide API Keys.  Keys with the `desktop_app` permission set only have the ability to do the functions provided in our Desktop App (File and Share Link operations). Keys with the `office_integration` permission set are auto generated, and automatically expire, to allow users to interact with office integration platforms. Additional permission sets may become available in the future, such as for a Site Admin to give a key with no administrator privileges.  If you have ideas for permission sets, please let us know.
* `name` (String): Internal name for the API Key.  For your use.


---

## Delete current API key.  (Requires current API connection to be using an API key.)

```
void apiKey = ApiKey.deleteCurrent(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```


---

## Delete API Key

```
void apiKey = ApiKey.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Api Key ID.


---

## Update API Key

```
ApiKey apiKey = ApiKey.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("description", "example");
parameters.put("expires_at", "2000-01-01T01:00:00Z");
parameters.put("permission_set", "full");
parameters.put("name", "My Main API Key");

apiKey.update(parameters);
```

### Parameters

* `id` (Long): Required - Api Key ID.
* `description` (String): User-supplied description of API key.
* `expires_at` (String): API Key expiration date
* `permission_set` (String): Permissions for this API Key. It must be full for site-wide API Keys.  Keys with the `desktop_app` permission set only have the ability to do the functions provided in our Desktop App (File and Share Link operations). Keys with the `office_integration` permission set are auto generated, and automatically expire, to allow users to interact with office integration platforms. Additional permission sets may become available in the future, such as for a Site Admin to give a key with no administrator privileges.  If you have ideas for permission sets, please let us know.
* `name` (String): Internal name for the API Key.  For your use.


---

## Delete API Key

```
ApiKey apiKey = ApiKey.find(id);

HashMap<String, Object> parameters = new HashMap<>();

apiKey.delete(parameters);
```

### Parameters

* `id` (Long): Required - Api Key ID.
