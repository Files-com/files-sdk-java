# Files.Models.As2Partner

## Example As2Partner Object

```
{
  "id": 1,
  "as2_station_id": 1,
  "name": "AS2 Partner Name",
  "uri": "example",
  "server_certificate": "require_match",
  "hex_public_certificate_serial": "A5:EB:C1:95:DC:D8:2B:E7",
  "public_certificate_md5": "example",
  "public_certificate_subject": "example",
  "public_certificate_issuer": "example",
  "public_certificate_serial": "example",
  "public_certificate_not_before": "example",
  "public_certificate_not_after": "example"
}
```

* `id` / `id`  (int64): Id of the AS2 Partner.
* `as2_station_id` / `as2StationId`  (int64): Id of the AS2 Station associated with this partner.
* `name` / `name`  (string): The partner's formal AS2 name.
* `uri` / `uri`  (string): Public URI for sending AS2 message to.
* `server_certificate` / `serverCertificate`  (string): Remote server certificate security setting
* `hex_public_certificate_serial` / `hexPublicCertificateSerial`  (string): Serial of public certificate used for message security in hex format.
* `public_certificate_md5` / `publicCertificateMd5`  (string): MD5 hash of public certificate used for message security.
* `public_certificate_subject` / `publicCertificateSubject`  (string): Subject of public certificate used for message security.
* `public_certificate_issuer` / `publicCertificateIssuer`  (string): Issuer of public certificate used for message security.
* `public_certificate_serial` / `publicCertificateSerial`  (string): Serial of public certificate used for message security.
* `public_certificate_not_before` / `publicCertificateNotBefore`  (string): Not before value of public certificate used for message security.
* `public_certificate_not_after` / `publicCertificateNotAfter`  (string): Not after value of public certificate used for message security.
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

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
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
* `server_certificate` (String): Remote server certificate security setting


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
* `server_certificate` (String): Remote server certificate security setting
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
parameters.put("uri", "example");
parameters.put("server_certificate", "require_match");

As2Partner.Update(parameters);
```

### Parameters

* `id` (Long): Required - As2 Partner ID.
* `name` (String): AS2 Name
* `uri` (String): URL base for AS2 responses
* `server_certificate` (String): Remote server certificate security setting
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
