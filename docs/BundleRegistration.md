# Files.Models.BundleRegistration

## Example BundleRegistration Object

```
{
  "code": "abc123",
  "name": "account",
  "company": "Action Verb",
  "email": "john.doe@files.com",
  "inbox_code": "abc123",
  "form_field_set_id": 1,
  "form_field_data": ""
}
```

* `code` / `code`  (string): Registration cookie code
* `name` / `name`  (string): Registrant name
* `company` / `company`  (string): Registrant company name
* `email` / `email`  (string): Registrant email address
* `inbox_code` / `inboxCode`  (string): InboxRegistration cookie code, if there is an associated InboxRegistration
* `form_field_set_id` / `formFieldSetId`  (int64): Id of associated form field set
* `form_field_data` / `formFieldData`  (string): Data for form field set with form field ids as keys and user data as values
