# Files.Models.BundleAction

## Example BundleAction Object

```
{
  "action": "create",
  "bundle_registration": {
    "code": "abc123",
    "name": "account",
    "company": "Action Verb",
    "email": "john.doe@files.com",
    "ip": "10.1.1.1",
    "inbox_code": "abc123",
    "clickwrap_body": "example",
    "form_field_set_id": 1,
    "form_field_data": {
      "key": "example value"
    },
    "bundle_code": "example",
    "bundle_id": 1,
    "bundle_recipient_id": 1,
    "created_at": "2000-01-01T01:00:00Z"
  },
  "created_at": "2000-01-01T01:00:00Z",
  "destination": "/to_path",
  "path": "",
  "source": "/from_path",
  "bundle_registration_id": 1,
  "bundle_registration_name": "John Doe",
  "bundle_registration_email": "john@example.com",
  "bundle_registration_ip": "127.0.0.1"
}
```

* `action` / `action`  (string): Type of action
* `bundle_registration` / `bundleRegistration`  (bundleRegistration): Object that contains bundle registration information
* `created_at` / `createdAt`  (date-time): Action occurrence date/time
* `destination` / `destination`  (string): The destination path for this bundle action, if applicable
* `path` / `path`  (string): Path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `source` / `source`  (string): The source path for this bundle action, if applicable
* `bundle_registration_id` / `bundleRegistrationId`  (int64): Identifier of the associated bundle registration
* `bundle_registration_name` / `bundleRegistrationName`  (string): Name of the registrant who performed the action
* `bundle_registration_email` / `bundleRegistrationEmail`  (string): Email of the registrant
* `bundle_registration_ip` / `bundleRegistrationIp`  (string): IP address of the registrant


---

## List Bundle Actions

```
ListIterator<BundleAction> bundleAction = BundleAction.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at` and `bundle_registration_id`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`, `bundle_id` or `bundle_registration_id`. Valid field combinations are `[ bundle_id, created_at ]`, `[ bundle_registration_id, created_at ]`, `[ bundle_id, bundle_registration_id ]` or `[ bundle_id, bundle_registration_id, created_at ]`.
* `filter_gt` (Map<String, String>): If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`.
* `filter_gteq` (Map<String, String>): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`.
* `filter_lt` (Map<String, String>): If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`.
* `filter_lteq` (Map<String, String>): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`.
