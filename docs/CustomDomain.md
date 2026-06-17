# Files.Models.CustomDomain

## Example CustomDomain Object

```
{
  "id": 1,
  "domain": "files.example.com",
  "destination": "site_alias",
  "dns_status": "correct",
  "ssl_certificate_id": 1,
  "brick_managed": true,
  "folder_behavior_id": 1,
  "created_at": "2000-01-01T01:00:00Z",
  "updated_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): Custom Domain ID.
* `domain` / `domain`  (string): Customer-owned domain name.
* `destination` / `destination`  (string): Where this custom domain routes. Can be `site_alias`, `public_hosting`, or `s3_endpoint`.
* `dns_status` / `dnsStatus`  (string): Current DNS verification status.
* `ssl_certificate_id` / `sslCertificateId`  (int64): Current SSL certificate ID.
* `brick_managed` / `brickManaged`  (boolean): Is this domain's SSL certificate automatically managed and renewed by Files.com?
* `folder_behavior_id` / `folderBehaviorId`  (int64): Public Hosting behavior ID when this domain routes to a specific Public Hosting behavior.
* `created_at` / `createdAt`  (date-time): When this Custom Domain was created.
* `updated_at` / `updatedAt`  (date-time): When this Custom Domain was last updated.


---

## List Custom Domains

```
ListIterator<CustomDomain> customDomain = CustomDomain.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `id`.


---

## Show Custom Domain

```
CustomDomain customDomain = CustomDomain.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Custom Domain ID.


---

## Create Custom Domain

```
CustomDomain customDomain = CustomDomain.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `destination` (String): Where this custom domain routes. Can be `site_alias`, `public_hosting`, or `s3_endpoint`.
* `folder_behavior_id` (Long): Public Hosting behavior ID when this domain routes to a specific Public Hosting behavior.
* `ssl_certificate_id` (Long): Current SSL certificate ID.
* `domain` (String): Required - Customer-owned domain name.


---

## Update Custom Domain

```
CustomDomain customDomain = CustomDomain.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Custom Domain ID.
* `destination` (String): Where this custom domain routes. Can be `site_alias`, `public_hosting`, or `s3_endpoint`.
* `folder_behavior_id` (Long): Public Hosting behavior ID when this domain routes to a specific Public Hosting behavior.
* `ssl_certificate_id` (Long): Current SSL certificate ID.
* `domain` (String): Customer-owned domain name.


---

## Delete Custom Domain

```
void customDomain = CustomDomain.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Custom Domain ID.


---

## Update Custom Domain

```
CustomDomain customDomain = CustomDomain.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("destination", "site_alias");
parameters.put("folder_behavior_id", 1);
parameters.put("ssl_certificate_id", 1);
parameters.put("domain", "files.example.com");

customDomain.update(parameters);
```

### Parameters

* `id` (Long): Required - Custom Domain ID.
* `destination` (String): Where this custom domain routes. Can be `site_alias`, `public_hosting`, or `s3_endpoint`.
* `folder_behavior_id` (Long): Public Hosting behavior ID when this domain routes to a specific Public Hosting behavior.
* `ssl_certificate_id` (Long): Current SSL certificate ID.
* `domain` (String): Customer-owned domain name.


---

## Delete Custom Domain

```
CustomDomain customDomain = CustomDomain.find(id);

HashMap<String, Object> parameters = new HashMap<>();

customDomain.delete(parameters);
```

### Parameters

* `id` (Long): Required - Custom Domain ID.
