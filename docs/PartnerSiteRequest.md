# Files.Models.PartnerSiteRequest

## Example PartnerSiteRequest Object

```
{
  "id": 1,
  "partner_id": 1,
  "linked_site_id": 1,
  "status": "pending",
  "pairing_key": "abc123xyz",
  "created_at": "2000-01-01T01:00:00Z",
  "updated_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): Partner Site Request ID
* `partner_id` / `partnerId`  (int64): Partner ID
* `linked_site_id` / `linkedSiteId`  (int64): Linked Site ID
* `status` / `status`  (string): Request status (pending, approved, rejected)
* `pairing_key` / `pairingKey`  (string): Pairing key used to approve this request on the target site
* `created_at` / `createdAt`  (date-time): Request creation date/time
* `updated_at` / `updatedAt`  (date-time): Request last updated date/time
* `site_url` / `siteUrl`  (string): Site URL to link to


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
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).


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

* `partner_id` (Long): Required - Partner ID to link with
* `site_url` (String): Required - Site URL to link to


---

## Reject partner site request

```
void partnerSiteRequest = PartnerSiteRequest.reject(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Partner Site Request ID.


---

## Approve partner site request

```
void partnerSiteRequest = PartnerSiteRequest.approve(
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
void partnerSiteRequest = PartnerSiteRequest.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Partner Site Request ID.


---

## Reject partner site request

```
PartnerSiteRequest partnerSiteRequest = PartnerSiteRequest.list()[0];

HashMap<String, Object> parameters = new HashMap<>();

partnerSiteRequest.reject(parameters);
```

### Parameters

* `id` (Long): Required - Partner Site Request ID.


---

## Approve partner site request

```
PartnerSiteRequest partnerSiteRequest = PartnerSiteRequest.list()[0];

HashMap<String, Object> parameters = new HashMap<>();

partnerSiteRequest.approve(parameters);
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
