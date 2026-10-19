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

```java
HashMap<String, Object> parameters = new HashMap<>();
ListIterator<PartnerSiteRequest> partnerSiteRequests = PartnerSiteRequest.list(parameters, null);
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `host_partner_id`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `host_partner_id`.


---

## Find partner site request by pairing key

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("pairing_key", "pairing_key");
PartnerSiteRequest.findByPairingKey(parameters, null);
```

### Parameters

* `pairing_key` (String): Required - Pairing key for the partner site request


---

## Create Partner Site Request

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("host_partner_id", 1L);
parameters.put("guest_site_url", "guest_site_url");
PartnerSiteRequest partnerSiteRequest = PartnerSiteRequest.create(parameters, null);
```

### Parameters

* `host_partner_id` (Long): Required - Host Partner ID to link with
* `guest_site_url` (String): Required - Guest Site URL to link to


---

## Reject partner site request

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("pairing_key", "pairing_key");
PartnerSiteRequest.reject(parameters, null);
```

### Parameters

* `pairing_key` (String): Required - Pairing key for the partner site request


---

## Approve partner site request

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("pairing_key", "pairing_key");
parameters.put("partner_id", 1L);
PartnerSiteRequest.approve(parameters, null);
```

### Parameters

* `pairing_key` (String): Required - Pairing key for the partner site request
* `partner_id` (Long): ID of an existing Partner on this site, with the host role, that represents the requesting organization. The connection binds to that Partner and makes it host_and_guest. When omitted, a guest Partner named after the host site is created.


---

## Delete Partner Site Request

```java
HashMap<String, Object> parameters = new HashMap<>();
PartnerSiteRequest.delete(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Partner Site Request ID.


---

## Delete Partner Site Request

```java
// The first PartnerSiteRequest listed: the list must not be empty.
PartnerSiteRequest partnerSiteRequest = PartnerSiteRequest.list(null, null).iterator().next();
HashMap<String, Object> parameters = new HashMap<>();
partnerSiteRequest.delete(parameters);
```

### Parameters

* `id` (Long): Required - Partner Site Request ID.
