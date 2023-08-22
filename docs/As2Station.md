# Files.Models.As2Station

## Example As2Station Object

```
{
  "id": 1,
  "name": "AS2 Station Name",
  "uri": "example",
  "domain": "domain.test",
  "hex_public_certificate_serial": "A5:EB:C1:95:DC:D8:2B:E7",
  "public_certificate_md5": "example",
  "private_key_md5": "example",
  "public_certificate_subject": "example",
  "public_certificate_issuer": "example",
  "public_certificate_serial": "example",
  "public_certificate_not_before": "example",
  "public_certificate_not_after": "example",
  "private_key_password_md5": "example"
}
```

* `id` / `id`  (int64): Id of the AS2 Station.
* `name` / `name`  (string): The station's formal AS2 name.
* `uri` / `uri`  (string): Public URI for sending AS2 message to.
* `domain` / `domain`  (string): The station's AS2 domain name.
* `hex_public_certificate_serial` / `hexPublicCertificateSerial`  (string): Serial of public certificate used for message security in hex format.
* `public_certificate_md5` / `publicCertificateMd5`  (string): MD5 hash of public certificate used for message security.
* `private_key_md5` / `privateKeyMd5`  (string): MD5 hash of private key used for message security.
* `public_certificate_subject` / `publicCertificateSubject`  (string): Subject of public certificate used for message security.
* `public_certificate_issuer` / `publicCertificateIssuer`  (string): Issuer of public certificate used for message security.
* `public_certificate_serial` / `publicCertificateSerial`  (string): Serial of public certificate used for message security.
* `public_certificate_not_before` / `publicCertificateNotBefore`  (string): Not before value of public certificate used for message security.
* `public_certificate_not_after` / `publicCertificateNotAfter`  (string): Not after value of public certificate used for message security.
* `private_key_password_md5` / `privateKeyPasswordMd5`  (string): MD5 hash of private key password used for message security.
* `public_certificate` / `publicCertificate`  (string): 
* `private_key` / `privateKey`  (string): 
* `private_key_password` / `privateKeyPassword`  (string): 


---

## List As2 Stations

```
ListIterator<As2Station> as2Station = As2Station.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).


---

## Show As2 Station

```
ListIterator<As2Station> as2Station = As2Station.find(
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
* `private_key_password` (String): 


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
* `private_key_password` (String): 


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
* `private_key_password` (String): 


---

## Delete As2 Station

```
As2Station as2Station = As2Station.List()[0];

HashMap<String, Object> parameters = new HashMap<>();


As2Station.Delete
```

### Parameters

* `id` (Long): Required - As2 Station ID.
