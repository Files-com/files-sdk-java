# Files.Models.BundleRecipient

## Example BundleRecipient Object

```
{
  "company": "Acme Inc.",
  "name": "John Doe",
  "note": "Some note.",
  "recipient": "john.doe@example.com",
  "sent_at": "2000-01-01T01:00:00Z"
}
```

* `company` / `company`  (string): The recipient's company.
* `name` / `name`  (string): The recipient's name.
* `note` / `note`  (string): A note sent to the recipient with the bundle.
* `recipient` / `recipient`  (string): The recipient's email address.
* `sent_at` / `sentAt`  (date-time): When the Bundle was shared with this recipient.
* `user_id` / `userId`  (int64): User ID.  Provide a value of `0` to operate the current session's user.
* `bundle_id` / `bundleId`  (int64): Bundle to share.
* `share_after_create` / `shareAfterCreate`  (boolean): Set to true to share the link with the recipient upon creation.


---

## List Share Link Recipients

```
ListIterator<BundleRecipient> bundleRecipient = BundleRecipient.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are .
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `has_registrations`.
* `bundle_id` (Long): Required - List recipients for the bundle with this ID.


---

## Create Share Link Recipient

```
BundleRecipient bundleRecipient = BundleRecipient.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `bundle_id` (Long): Required - Bundle to share.
* `recipient` (String): Required - Email addresses to share this bundle with.
* `name` (String): Name of recipient.
* `company` (String): Company of recipient.
* `note` (String): Note to include in email.
* `share_after_create` (Boolean): Set to true to share the link with the recipient upon creation.
