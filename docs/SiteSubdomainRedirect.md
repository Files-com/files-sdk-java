# Files.Models.SiteSubdomainRedirect

## Example SiteSubdomainRedirect Object

```
{
  "id": 1,
  "subdomain": "old-company",
  "created_at": "2000-01-01T01:00:00Z",
  "updated_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): Site subdomain redirect ID.
* `subdomain` / `subdomain`  (string): Files.com subdomain that continues to route to the current site subdomain.
* `created_at` / `createdAt`  (date-time): When this redirect was created.
* `updated_at` / `updatedAt`  (date-time): When this redirect was last updated.


---

## List Site Subdomain Redirects

```java
HashMap<String, Object> parameters = new HashMap<>();
ListIterator<SiteSubdomainRedirect> siteSubdomainRedirects = SiteSubdomainRedirect.list(parameters, null);
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `id`.


---

## Show Site Subdomain Redirect

```java
HashMap<String, Object> parameters = new HashMap<>();
SiteSubdomainRedirect siteSubdomainRedirect = SiteSubdomainRedirect.find(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Site Subdomain Redirect ID.


---

## Delete Site Subdomain Redirect

```java
HashMap<String, Object> parameters = new HashMap<>();
SiteSubdomainRedirect.delete(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Site Subdomain Redirect ID.


---

## Delete Site Subdomain Redirect

```java
SiteSubdomainRedirect siteSubdomainRedirect = SiteSubdomainRedirect.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
siteSubdomainRedirect.delete(parameters);
```

### Parameters

* `id` (Long): Required - Site Subdomain Redirect ID.
