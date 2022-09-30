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
  "bundle_recipient_id": 1
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


---

## List Bundle Registrations

```
List<BundleRegistration> bundleRegistration = BundleRegistration.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via either the X-Files-Cursor-Next header or the X-Files-Cursor-Prev header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `bundle_id` (Long): ID of the associated Bundle
