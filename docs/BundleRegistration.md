# Files.Models.BundleRegistration

## Example BundleRegistration Object

```
{
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
}
```

* `code` / `code`  (string): Registration cookie code
* `name` / `name`  (string): Registrant name
* `company` / `company`  (string): Registrant company name
* `email` / `email`  (string): Registrant email address
* `ip` / `ip`  (string): Registrant IP Address
* `inbox_code` / `inboxCode`  (string): InboxRegistration cookie code, if there is an associated InboxRegistration
* `clickwrap_body` / `clickwrapBody`  (string): Clickwrap text that was shown to the registrant
* `form_field_set_id` / `formFieldSetId`  (int64): Id of associated form field set
* `form_field_data` / `formFieldData`  (object): Data for form field set with form field ids as keys and user data as values
* `bundle_code` / `bundleCode`  (string): Bundle URL code
* `bundle_id` / `bundleId`  (int64): Id of associated bundle
* `bundle_recipient_id` / `bundleRecipientId`  (int64): Id of associated bundle recipient
* `created_at` / `createdAt`  (date-time): Registration creation date/time


---

## List Bundle Registrations

```
ListIterator<BundleRegistration> bundleRegistration = BundleRegistration.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `bundle_id`.
* `bundle_id` (Long): ID of the associated Bundle
