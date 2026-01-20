# Files.Models.PartnerSite

## Example PartnerSite Object

```
{
  "partner_id": 1,
  "partner_name": "Acme Corp",
  "linked_site_id": 1
}
```

* `partner_id` / `partnerId`  (int64): Partner ID
* `partner_name` / `partnerName`  (string): Partner Name
* `linked_site_id` / `linkedSiteId`  (int64): Linked Site ID


---

## List Partner Sites

```
ListIterator<PartnerSite> partnerSite = PartnerSite.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
