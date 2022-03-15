# Files.Models.As2Station

## Example As2Station Object

```
{
  "id": 1,
  "name": "AS2 Station Name",
  "uri": "",
  "domain": "domain.test",
  "public_certificate_md5": "",
  "private_key_md5": "",
  "public_certificate_subject": "",
  "public_certificate_issuer": "",
  "public_certificate_serial": "",
  "public_certificate_not_before": "",
  "public_certificate_not_after": ""
}
```

* `id` / `id`  (int64): Id of the AS2 Station.
* `name` / `name`  (string): The station's formal AS2 name.
* `uri` / `uri`  (string): Public URI for sending AS2 message to.
* `domain` / `domain`  (string): The station's AS2 domain name.
* `public_certificate_md5` / `publicCertificateMd5`  (string): MD5 hash of public certificate used for message security.
* `private_key_md5` / `privateKeyMd5`  (string): MD5 hash of private key used for message security.
* `public_certificate_subject` / `publicCertificateSubject`  (string): Subject of public certificate used for message security.
* `public_certificate_issuer` / `publicCertificateIssuer`  (string): Issuer of public certificate used for message security.
* `public_certificate_serial` / `publicCertificateSerial`  (string): Serial of public certificate used for message security.
* `public_certificate_not_before` / `publicCertificateNotBefore`  (string): Not before value of public certificate used for message security.
* `public_certificate_not_after` / `publicCertificateNotAfter`  (string): Not after value of public certificate used for message security.
* `public_certificate` / `publicCertificate`  (string): 
* `private_key` / `privateKey`  (string): 


---

## List As2 Stations

```
List<As2Station> as2Station = As2Station.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via either the X-Files-Cursor-Next header or the X-Files-Cursor-Prev header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).


---

## Show As2 Station

```
List<As2Station> as2Station = As2Station.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - As2 Station ID.


---

## Create As2 Station

```
As2Station as2Station = As2Station.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `name` (String): Required - AS2 Name
* `public_certificate` (String): Required - 
* `private_key` (String): Required - 


---

## Update As2 Station

```
As2Station as2Station = As2Station.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - As2 Station ID.
* `name` (String): AS2 Name
* `public_certificate` (String): 
* `private_key` (String): 


---

## Delete As2 Station

```
As2Station as2Station = As2Station.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - As2 Station ID.


---

## Update As2 Station

```
As2Station as2Station = As2Station.List()[0];

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("name", "AS2 Station Name");

As2Station.Update(parameters);
```

### Parameters

* `id` (Long): Required - As2 Station ID.
* `name` (String): AS2 Name
* `public_certificate` (String): 
* `private_key` (String): 


---

## Delete As2 Station

```
As2Station as2Station = As2Station.List()[0];

HashMap<String, Object> parameters = new HashMap<>();


As2Station.Delete
```

### Parameters

* `id` (Long): Required - As2 Station ID.
