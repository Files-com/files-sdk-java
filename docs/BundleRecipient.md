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


---

## List Bundle Recipients

```
List<BundleRecipient> bundleRecipient = BundleRecipient.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `page` (Long): Current page number.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `action` (String): Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
* `cursor` (String): Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `bundle_id` (Long): Required - List recipients for the bundle with this ID.
