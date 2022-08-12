# Files.Models.Bundle

## Example Bundle Object

```
{
  "code": "abc123",
  "url": "https://subdomain.files.com/f/12345678",
  "description": "The public description of the bundle.",
  "password_protected": true,
  "permissions": "read",
  "preview_only": true,
  "require_registration": true,
  "require_share_recipient": true,
  "clickwrap_body": "[Legal text]",
  "form_field_set": {
    "id": 1,
    "title": "Sample Form Title",
    "form_layout": [
      1,
      2,
      3,
      4
    ],
    "form_fields": [
      {
        "id": 1,
        "label": "Sample Label",
        "required": true,
        "help_text": "Help Text",
        "field_type": "text",
        "options_for_select": [
          "red",
          "green",
          "blue"
        ],
        "default_option": "red",
        "form_field_set_id": 1
      }
    ],
    "skip_name": true,
    "skip_email": true,
    "skip_company": true
  },
  "skip_name": true,
  "skip_email": true,
  "skip_company": true,
  "id": 1,
  "created_at": "2000-01-01T01:00:00Z",
  "expires_at": "2000-01-01T01:00:00Z",
  "max_uses": 1,
  "note": "The internal note on the bundle.",
  "user_id": 1,
  "username": "user",
  "clickwrap_id": 1,
  "inbox_id": 1,
  "watermark_attachment": {
    "name": "My logo",
    "uri": "https://mysite.files.com/.../my_image.png"
  },
  "watermark_value": "",
  "has_inbox": true,
  "paths": [
    "file.txt"
  ]
}
```

* `code` / `code`  (string): Bundle code.  This code forms the end part of the Public URL.
* `url` / `url`  (string): Public URL of Share Link
* `description` / `description`  (string): Public description
* `password_protected` / `passwordProtected`  (boolean): Is this bundle password protected?
* `permissions` / `permissions`  (string): Permissions that apply to Folders in this Share Link.
* `preview_only` / `previewOnly`  (boolean): Restrict users to previewing files only?
* `require_registration` / `requireRegistration`  (boolean): Show a registration page that captures the downloader's name and email address?
* `require_share_recipient` / `requireShareRecipient`  (boolean): Only allow access to recipients who have explicitly received the share via an email sent through the Files.com UI?
* `clickwrap_body` / `clickwrapBody`  (string): Legal text that must be agreed to prior to accessing Bundle.
* `form_field_set` / `formFieldSet`  (formFieldSet): Custom Form to use
* `skip_name` / `skipName`  (boolean): BundleRegistrations can be saved without providing name?
* `skip_email` / `skipEmail`  (boolean): BundleRegistrations can be saved without providing email?
* `skip_company` / `skipCompany`  (boolean): BundleRegistrations can be saved without providing company?
* `id` / `id`  (int64): Bundle ID
* `created_at` / `createdAt`  (date-time): Bundle created at date/time
* `expires_at` / `expiresAt`  (date-time): Bundle expiration date/time
* `max_uses` / `maxUses`  (int64): Maximum number of times bundle can be accessed
* `note` / `note`  (string): Bundle internal note
* `user_id` / `userId`  (int64): Bundle creator user ID
* `username` / `username`  (string): Bundle creator username
* `clickwrap_id` / `clickwrapId`  (int64): ID of the clickwrap to use with this bundle.
* `inbox_id` / `inboxId`  (int64): ID of the associated inbox, if available.
* `watermark_attachment` / `watermarkAttachment`  (image): Preview watermark image applied to all bundle items.
* `watermark_value` / `watermarkValue`  (object): Preview watermark settings applied to all bundle items. Uses the same keys as Behavior.value
* `has_inbox` / `hasInbox`  (boolean): Does this bundle have an associated inbox?
* `paths` / `paths`  (array): A list of paths in this bundle
* `password` / `password`  (string): Password for this bundle.
* `form_field_set_id` / `formFieldSetId`  (int64): Id of Form Field Set to use with this bundle
* `watermark_attachment_file` / `watermarkAttachmentFile`  (file): Preview watermark image applied to all bundle items.
* `watermark_attachment_delete` / `watermarkAttachmentDelete`  (boolean): If true, will delete the file stored in watermark_attachment


---

## List Bundles

```
List<Bundle> bundle = Bundle.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via either the X-Files-Cursor-Next header or the X-Files-Cursor-Prev header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `created_at` and `code`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`.
* `filter_gt` (Map<String, String>): If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`.
* `filter_gteq` (Map<String, String>): If set, return records where the specified field is greater than or equal to the supplied value. Valid fields are `created_at`.
* `filter_like` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`.
* `filter_lt` (Map<String, String>): If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`.
* `filter_lteq` (Map<String, String>): If set, return records where the specified field is less than or equal to the supplied value. Valid fields are `created_at`.


---

## Show Bundle

```
List<Bundle> bundle = Bundle.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Bundle ID.


---

## Create Bundle

```
Bundle bundle = Bundle.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `paths` (String[]): Required - A list of paths to include in this bundle.
* `password` (String): Password for this bundle.
* `form_field_set_id` (Long): Id of Form Field Set to use with this bundle
* `expires_at` (String): Bundle expiration date/time
* `max_uses` (Long): Maximum number of times bundle can be accessed
* `description` (String): Public description
* `note` (String): Bundle internal note
* `code` (String): Bundle code.  This code forms the end part of the Public URL.
* `permissions` (String): Permissions that apply to Folders in this Share Link.
* `preview_only` (Boolean): Restrict users to previewing files only?
* `require_registration` (Boolean): Show a registration page that captures the downloader's name and email address?
* `clickwrap_id` (Long): ID of the clickwrap to use with this bundle.
* `inbox_id` (Long): ID of the associated inbox, if available.
* `require_share_recipient` (Boolean): Only allow access to recipients who have explicitly received the share via an email sent through the Files.com UI?
* `skip_email` (Boolean): BundleRegistrations can be saved without providing email?
* `skip_name` (Boolean): BundleRegistrations can be saved without providing name?
* `skip_company` (Boolean): BundleRegistrations can be saved without providing company?
* `watermark_attachment_file` (byte[]): Preview watermark image applied to all bundle items.


---

## Send email(s) with a link to bundle

```
Bundle bundle = Bundle.share(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Bundle ID.
* `to` (String[]): A list of email addresses to share this bundle with. Required unless `recipients` is used.
* `note` (String): Note to include in email.
* `recipients` (Object[]): A list of recipients to share this bundle with. Required unless `to` is used.


---

## Update Bundle

```
Bundle bundle = Bundle.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Bundle ID.
* `paths` (String[]): A list of paths to include in this bundle.
* `password` (String): Password for this bundle.
* `form_field_set_id` (Long): Id of Form Field Set to use with this bundle
* `clickwrap_id` (Long): ID of the clickwrap to use with this bundle.
* `code` (String): Bundle code.  This code forms the end part of the Public URL.
* `description` (String): Public description
* `expires_at` (String): Bundle expiration date/time
* `inbox_id` (Long): ID of the associated inbox, if available.
* `max_uses` (Long): Maximum number of times bundle can be accessed
* `note` (String): Bundle internal note
* `permissions` (String): Permissions that apply to Folders in this Share Link.
* `preview_only` (Boolean): Restrict users to previewing files only?
* `require_registration` (Boolean): Show a registration page that captures the downloader's name and email address?
* `require_share_recipient` (Boolean): Only allow access to recipients who have explicitly received the share via an email sent through the Files.com UI?
* `skip_email` (Boolean): BundleRegistrations can be saved without providing email?
* `skip_name` (Boolean): BundleRegistrations can be saved without providing name?
* `skip_company` (Boolean): BundleRegistrations can be saved without providing company?
* `watermark_attachment_delete` (Boolean): If true, will delete the file stored in watermark_attachment
* `watermark_attachment_file` (byte[]): Preview watermark image applied to all bundle items.


---

## Delete Bundle

```
Bundle bundle = Bundle.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Bundle ID.


---

## Send email(s) with a link to bundle

```
Bundle bundle = Bundle.List()[0];

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("to", ["johndoe@gmail.com"]);
parameters.put("note", "Just a note.");
parameters.put("recipients", [{"name":"John Doe","company":"Acme Ltd","recipient":"johndoe@gmail.com"}]);

Bundle.Share(parameters);
```

### Parameters

* `id` (Long): Required - Bundle ID.
* `to` (String[]): A list of email addresses to share this bundle with. Required unless `recipients` is used.
* `note` (String): Note to include in email.
* `recipients` (Object[]): A list of recipients to share this bundle with. Required unless `to` is used.


---

## Update Bundle

```
Bundle bundle = Bundle.List()[0];

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("paths", ["file.txt"]);
parameters.put("password", "Password");
parameters.put("form_field_set_id", 1);
parameters.put("clickwrap_id", 1);
parameters.put("code", "abc123");
parameters.put("description", "The public description of the bundle.");
parameters.put("expires_at", "2000-01-01T01:00:00Z");
parameters.put("inbox_id", 1);
parameters.put("max_uses", 1);
parameters.put("note", "The internal note on the bundle.");
parameters.put("permissions", "read");
parameters.put("preview_only", true);
parameters.put("require_registration", true);
parameters.put("require_share_recipient", true);
parameters.put("skip_email", true);
parameters.put("skip_name", true);
parameters.put("skip_company", true);
parameters.put("watermark_attachment_delete", true);

Bundle.Update(parameters);
```

### Parameters

* `id` (Long): Required - Bundle ID.
* `paths` (String[]): A list of paths to include in this bundle.
* `password` (String): Password for this bundle.
* `form_field_set_id` (Long): Id of Form Field Set to use with this bundle
* `clickwrap_id` (Long): ID of the clickwrap to use with this bundle.
* `code` (String): Bundle code.  This code forms the end part of the Public URL.
* `description` (String): Public description
* `expires_at` (String): Bundle expiration date/time
* `inbox_id` (Long): ID of the associated inbox, if available.
* `max_uses` (Long): Maximum number of times bundle can be accessed
* `note` (String): Bundle internal note
* `permissions` (String): Permissions that apply to Folders in this Share Link.
* `preview_only` (Boolean): Restrict users to previewing files only?
* `require_registration` (Boolean): Show a registration page that captures the downloader's name and email address?
* `require_share_recipient` (Boolean): Only allow access to recipients who have explicitly received the share via an email sent through the Files.com UI?
* `skip_email` (Boolean): BundleRegistrations can be saved without providing email?
* `skip_name` (Boolean): BundleRegistrations can be saved without providing name?
* `skip_company` (Boolean): BundleRegistrations can be saved without providing company?
* `watermark_attachment_delete` (Boolean): If true, will delete the file stored in watermark_attachment
* `watermark_attachment_file` (byte[]): Preview watermark image applied to all bundle items.


---

## Delete Bundle

```
Bundle bundle = Bundle.List()[0];

HashMap<String, Object> parameters = new HashMap<>();


Bundle.Delete
```

### Parameters

* `id` (Long): Required - Bundle ID.
