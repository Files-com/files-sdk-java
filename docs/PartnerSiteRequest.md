# Files.Models.PartnerSiteRequest

## Example PartnerSiteRequest Object

```
{
  "id": 1,
  "host_partner_id": 1,
  "guest_site_url": "https://example.files.com",
  "status": "pending",
  "host_site_name": "Acme Site",
  "pairing_key": "abc123xyz",
  "created_at": "2000-01-01T01:00:00Z",
  "updated_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): Partner Site Request ID
* `host_partner_id` / `hostPartnerId`  (int64): Host Partner ID
* `guest_site_url` / `guestSiteUrl`  (string): Guest Site URL
* `status` / `status`  (string): Request status (pending, approved, rejected)
* `host_site_name` / `hostSiteName`  (string): Host Site Name
* `pairing_key` / `pairingKey`  (string): Pairing key used to approve this request on the Guest Site
* `created_at` / `createdAt`  (date-time): Request creation date/time
* `updated_at` / `updatedAt`  (date-time): Request last updated date/time


---

## List Partner Site Requests

```
ListIterator<PartnerSiteRequest> partnerSiteRequest = PartnerSiteRequest.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `host_partner_id`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `host_partner_id`.


---

## Find partner site request by pairing key

```
void partnerSiteRequest = PartnerSiteRequest.findByPairingKey(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `pairing_key` (String): Required - Pairing key for the partner site request


---

## Create Partner Site Request

```
PartnerSiteRequest partnerSiteRequest = PartnerSiteRequest.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `host_partner_id` (Long): Required - Host Partner ID to link with
* `guest_site_url` (String): Required - Guest Site URL to link to


---

## Reject partner site request

```
void partnerSiteRequest = PartnerSiteRequest.reject(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `pairing_key` (String): Required - Pairing key for the partner site request


---

## Approve partner site request

```
void partnerSiteRequest = PartnerSiteRequest.approve(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `pairing_key` (String): Required - Pairing key for the partner site request


---

## Delete Partner Site Request

```
void partnerSiteRequest = PartnerSiteRequest.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Partner Site Request ID.


---

## Delete Partner Site Request

```
PartnerSiteRequest partnerSiteRequest = PartnerSiteRequest.list()[0];

HashMap<String, Object> parameters = new HashMap<>();

partnerSiteRequest.delete(parameters);
```

### Parameters

* `id` (Long): Required - Partner Site Request ID.
