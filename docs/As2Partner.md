# Files.Models.As2Partner

## Example As2Partner Object

```
{
  "id": 1,
  "as2_station_id": 1,
  "name": "AS2 Partner Name",
  "uri": "",
  "public_certificate_md5": ""
}
```

* `id` / `id`  (int64): Id of the AS2 Partner.
* `as2_station_id` / `as2StationId`  (int64): Id of the AS2 Station associated with this partner.
* `name` / `name`  (string): The partner's formal AS2 name.
* `uri` / `uri`  (string): Public URI for sending AS2 message to.
* `public_certificate_md5` / `publicCertificateMd5`  (string): MD5 hash of public certificate used for message security.
* `public_certificate` / `publicCertificate`  (string): 


---

## List As2 Partners

```
List<As2Partner> as2Partner = As2Partner.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via either the X-Files-Cursor-Next header or the X-Files-Cursor-Prev header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).


---

## Show As2 Partner

```
List<As2Partner> as2Partner = As2Partner.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - As2 Partner ID.


---

## Create As2 Partner

```
As2Partner as2Partner = As2Partner.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `name` (String): Required - AS2 Name
* `uri` (String): Required - URL base for AS2 responses
* `public_certificate` (String): Required - 
* `as2_station_id` (Long): Required - Id of As2Station for this partner


---

## Update As2 Partner

```
As2Partner as2Partner = As2Partner.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - As2 Partner ID.
* `name` (String): AS2 Name
* `uri` (String): URL base for AS2 responses
* `public_certificate` (String): 


---

## Delete As2 Partner

```
As2Partner as2Partner = As2Partner.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - As2 Partner ID.


---

## Update As2 Partner

```
As2Partner as2Partner = As2Partner.List()[0];

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("name", "AS2 Partner Name");

As2Partner.Update(parameters);
```

### Parameters

* `id` (Long): Required - As2 Partner ID.
* `name` (String): AS2 Name
* `uri` (String): URL base for AS2 responses
* `public_certificate` (String): 


---

## Delete As2 Partner

```
As2Partner as2Partner = As2Partner.List()[0];

HashMap<String, Object> parameters = new HashMap<>();


As2Partner.Delete
```

### Parameters

* `id` (Long): Required - As2 Partner ID.
