# Files.Models.Bundle

## Example Bundle Object

```
{
  "code": "abc123",
  "color_left": "#0066a7",
  "color_link": "#d34f5d",
  "color_text": "#0066a7",
  "color_top": "#000000",
  "color_top_text": "#ffffff",
  "url": "https://subdomain.files.com/f/12345678",
  "description": "The public description of the bundle.",
  "expires_at": "2000-01-01T01:00:00Z",
  "password_protected": true,
  "permissions": "read",
  "preview_only": true,
  "require_registration": true,
  "require_share_recipient": true,
  "require_logout": true,
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
      null
    ],
    "skip_name": true,
    "skip_email": true,
    "skip_company": true
  },
  "skip_name": true,
  "skip_email": true,
  "start_access_on_date": "2000-01-01T01:00:00Z",
  "skip_company": true,
  "id": 1,
  "created_at": "2000-01-01T01:00:00Z",
  "dont_separate_submissions_by_folder": true,
  "max_uses": 1,
  "note": "The internal note on the bundle.",
  "path_template": "{{name}}_{{ip}}",
  "send_email_receipt_to_uploader": true,
  "snapshot_id": 1,
  "user_id": 1,
  "username": "user",
  "clickwrap_id": 1,
  "inbox_id": 1,
  "watermark_attachment": null,
  "watermark_value": {
    "key": "example value"
  },
  "has_inbox": true,
  "paths": [
    "file.txt"
  ]
}
```

* `code` / `code`  (string): Bundle code.  This code forms the end part of the Public URL.
* `color_left` / `colorLeft`  (string): Page link and button color
* `color_link` / `colorLink`  (string): Top bar link color
* `color_text` / `colorText`  (string): Page link and button color
* `color_top` / `colorTop`  (string): Top bar background color
* `color_top_text` / `colorTopText`  (string): Top bar text color
* `url` / `url`  (string): Public URL of Share Link
* `description` / `description`  (string): Public description
* `expires_at` / `expiresAt`  (date-time): Bundle expiration date/time
* `password_protected` / `passwordProtected`  (boolean): Is this bundle password protected?
* `permissions` / `permissions`  (string): Permissions that apply to Folders in this Share Link.
* `preview_only` / `previewOnly`  (boolean): DEPRECATED: Restrict users to previewing files only. Use `permissions` instead.
* `require_registration` / `requireRegistration`  (boolean): Show a registration page that captures the downloader's name and email address?
* `require_share_recipient` / `requireShareRecipient`  (boolean): Only allow access to recipients who have explicitly received the share via an email sent through the Files.com UI?
* `require_logout` / `requireLogout`  (boolean): If true, we will hide the 'Remember Me' box on the Bundle registration page, requiring that the user logout and log back in every time they visit the page.
* `clickwrap_body` / `clickwrapBody`  (string): Legal text that must be agreed to prior to accessing Bundle.
* `form_field_set` / `formFieldSet`  (formFieldSet): Custom Form to use
* `skip_name` / `skipName`  (boolean): BundleRegistrations can be saved without providing name?
* `skip_email` / `skipEmail`  (boolean): BundleRegistrations can be saved without providing email?
* `start_access_on_date` / `startAccessOnDate`  (date-time): Date when share will start to be accessible. If `nil` access granted right after create.
* `skip_company` / `skipCompany`  (boolean): BundleRegistrations can be saved without providing company?
* `id` / `id`  (int64): Bundle ID
* `created_at` / `createdAt`  (date-time): Bundle created at date/time
* `dont_separate_submissions_by_folder` / `dontSeparateSubmissionsByFolder`  (boolean): Do not create subfolders for files uploaded to this share. Note: there are subtle security pitfalls with allowing anonymous uploads from multiple users to live in the same folder. We strongly discourage use of this option unless absolutely required.
* `max_uses` / `maxUses`  (int64): Maximum number of times bundle can be accessed
* `note` / `note`  (string): Bundle internal note
* `path_template` / `pathTemplate`  (string): Template for creating submission subfolders. Can use the uploader's name, email address, ip, company, and any custom form data.
* `send_email_receipt_to_uploader` / `sendEmailReceiptToUploader`  (boolean): Send delivery receipt to the uploader. Note: For writable share only
* `snapshot_id` / `snapshotId`  (int64): ID of the snapshot containing this bundle's contents.
* `user_id` / `userId`  (int64): Bundle creator user ID
* `username` / `username`  (string): Bundle creator username
* `clickwrap_id` / `clickwrapId`  (int64): ID of the clickwrap to use with this bundle.
* `inbox_id` / `inboxId`  (int64): ID of the associated inbox, if available.
* `watermark_attachment` / `watermarkAttachment`  (image): Preview watermark image applied to all bundle items.
* `watermark_value` / `watermarkValue`  (object): Preview watermark settings applied to all bundle items. Uses the same keys as Behavior.value
* `has_inbox` / `hasInbox`  (boolean): Does this bundle have an associated inbox?
* `paths` / `paths`  (array): A list of paths in this bundle.  For performance reasons, this is not provided when listing bundles.
* `password` / `password`  (string): Password for this bundle.
* `form_field_set_id` / `formFieldSetId`  (int64): Id of Form Field Set to use with this bundle
* `create_snapshot` / `createSnapshot`  (boolean): If true, create a snapshot of this bundle's contents.
* `finalize_snapshot` / `finalizeSnapshot`  (boolean): If true, finalize the snapshot of this bundle's contents. Note that `create_snapshot` must also be true.
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
* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction (e.g. `sort_by[created_at]=desc`). Valid fields are `created_at` and `code`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`.
* `filter_gt` (Map<String, String>): If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`.
* `filter_gteq` (Map<String, String>): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`.
* `filter_lt` (Map<String, String>): If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`.
* `filter_lteq` (Map<String, String>): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`.


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
* `create_snapshot` (Boolean): If true, create a snapshot of this bundle's contents.
* `dont_separate_submissions_by_folder` (Boolean): Do not create subfolders for files uploaded to this share. Note: there are subtle security pitfalls with allowing anonymous uploads from multiple users to live in the same folder. We strongly discourage use of this option unless absolutely required.
* `expires_at` (String): Bundle expiration date/time
* `finalize_snapshot` (Boolean): If true, finalize the snapshot of this bundle's contents. Note that `create_snapshot` must also be true.
* `max_uses` (Long): Maximum number of times bundle can be accessed
* `description` (String): Public description
* `note` (String): Bundle internal note
* `code` (String): Bundle code.  This code forms the end part of the Public URL.
* `path_template` (String): Template for creating submission subfolders. Can use the uploader's name, email address, ip, company, and any custom form data.
* `permissions` (String): Permissions that apply to Folders in this Share Link.
* `preview_only` (Boolean): DEPRECATED: Restrict users to previewing files only. Use `permissions` instead.
* `require_registration` (Boolean): Show a registration page that captures the downloader's name and email address?
* `clickwrap_id` (Long): ID of the clickwrap to use with this bundle.
* `inbox_id` (Long): ID of the associated inbox, if available.
* `require_share_recipient` (Boolean): Only allow access to recipients who have explicitly received the share via an email sent through the Files.com UI?
* `send_email_receipt_to_uploader` (Boolean): Send delivery receipt to the uploader. Note: For writable share only
* `skip_email` (Boolean): BundleRegistrations can be saved without providing email?
* `skip_name` (Boolean): BundleRegistrations can be saved without providing name?
* `skip_company` (Boolean): BundleRegistrations can be saved without providing company?
* `start_access_on_date` (String): Date when share will start to be accessible. If `nil` access granted right after create.
* `snapshot_id` (Long): ID of the snapshot containing this bundle's contents.
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
* `create_snapshot` (Boolean): If true, create a snapshot of this bundle's contents.
* `description` (String): Public description
* `dont_separate_submissions_by_folder` (Boolean): Do not create subfolders for files uploaded to this share. Note: there are subtle security pitfalls with allowing anonymous uploads from multiple users to live in the same folder. We strongly discourage use of this option unless absolutely required.
* `expires_at` (String): Bundle expiration date/time
* `finalize_snapshot` (Boolean): If true, finalize the snapshot of this bundle's contents. Note that `create_snapshot` must also be true.
* `inbox_id` (Long): ID of the associated inbox, if available.
* `max_uses` (Long): Maximum number of times bundle can be accessed
* `note` (String): Bundle internal note
* `path_template` (String): Template for creating submission subfolders. Can use the uploader's name, email address, ip, company, and any custom form data.
* `permissions` (String): Permissions that apply to Folders in this Share Link.
* `preview_only` (Boolean): DEPRECATED: Restrict users to previewing files only. Use `permissions` instead.
* `require_registration` (Boolean): Show a registration page that captures the downloader's name and email address?
* `require_share_recipient` (Boolean): Only allow access to recipients who have explicitly received the share via an email sent through the Files.com UI?
* `send_email_receipt_to_uploader` (Boolean): Send delivery receipt to the uploader. Note: For writable share only
* `skip_company` (Boolean): BundleRegistrations can be saved without providing company?
* `start_access_on_date` (String): Date when share will start to be accessible. If `nil` access granted right after create.
* `skip_email` (Boolean): BundleRegistrations can be saved without providing email?
* `skip_name` (Boolean): BundleRegistrations can be saved without providing name?
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
parameters.put("create_snapshot", true);
parameters.put("description", "The public description of the bundle.");
parameters.put("dont_separate_submissions_by_folder", true);
parameters.put("expires_at", "2000-01-01T01:00:00Z");
parameters.put("finalize_snapshot", true);
parameters.put("inbox_id", 1);
parameters.put("max_uses", 1);
parameters.put("note", "The internal note on the bundle.");
parameters.put("path_template", "{{name}}_{{ip}}");
parameters.put("permissions", "read");
parameters.put("preview_only", true);
parameters.put("require_registration", true);
parameters.put("require_share_recipient", true);
parameters.put("send_email_receipt_to_uploader", true);
parameters.put("skip_company", true);
parameters.put("start_access_on_date", "2000-01-01T01:00:00Z");
parameters.put("skip_email", true);
parameters.put("skip_name", true);
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
* `create_snapshot` (Boolean): If true, create a snapshot of this bundle's contents.
* `description` (String): Public description
* `dont_separate_submissions_by_folder` (Boolean): Do not create subfolders for files uploaded to this share. Note: there are subtle security pitfalls with allowing anonymous uploads from multiple users to live in the same folder. We strongly discourage use of this option unless absolutely required.
* `expires_at` (String): Bundle expiration date/time
* `finalize_snapshot` (Boolean): If true, finalize the snapshot of this bundle's contents. Note that `create_snapshot` must also be true.
* `inbox_id` (Long): ID of the associated inbox, if available.
* `max_uses` (Long): Maximum number of times bundle can be accessed
* `note` (String): Bundle internal note
* `path_template` (String): Template for creating submission subfolders. Can use the uploader's name, email address, ip, company, and any custom form data.
* `permissions` (String): Permissions that apply to Folders in this Share Link.
* `preview_only` (Boolean): DEPRECATED: Restrict users to previewing files only. Use `permissions` instead.
* `require_registration` (Boolean): Show a registration page that captures the downloader's name and email address?
* `require_share_recipient` (Boolean): Only allow access to recipients who have explicitly received the share via an email sent through the Files.com UI?
* `send_email_receipt_to_uploader` (Boolean): Send delivery receipt to the uploader. Note: For writable share only
* `skip_company` (Boolean): BundleRegistrations can be saved without providing company?
* `start_access_on_date` (String): Date when share will start to be accessible. If `nil` access granted right after create.
* `skip_email` (Boolean): BundleRegistrations can be saved without providing email?
* `skip_name` (Boolean): BundleRegistrations can be saved without providing name?
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
