# Files.Models.Certificate

## Example Certificate Object

```
{
  "id": 1,
  "name": "My Certificate",
  "custom_domain_id": 1,
  "certificate": "[certificate]",
  "created_at": "2000-01-01T01:00:00Z",
  "display_status": "Available",
  "domains": [

  ],
  "expires_at": "2000-01-01T01:00:00Z",
  "brick_managed": true,
  "intermediates": "[certificates]",
  "ip_addresses": [

  ],
  "issuer": "example",
  "key_type": "RSA-4096",
  "request": "[CSR]",
  "status": "Available",
  "subject": "my-custom-domain.com"
}
```

* `id` / `id`  (int64): Certificate ID
* `name` / `name`  (string): Descriptive name of certificate
* `custom_domain_id` / `customDomainId`  (int64): Custom Domain that owns this certificate, including while the certificate is inactive.
* `certificate` / `certificate`  (string): Full text of SSL certificate
* `created_at` / `createdAt`  (date-time): Certificate created at
* `display_status` / `displayStatus`  (string): Certificate status. (One of `Request Generated`, `New`, `Active`, `Active/Expired`, `Expired`, or `Available`)
* `domains` / `domains`  (array(string)): Domains on this certificate other than files.com domains
* `expires_at` / `expiresAt`  (date-time): Certificate expiration date/time
* `brick_managed` / `brickManaged`  (boolean): Is this certificate automatically managed and renewed by Files.com?
* `intermediates` / `intermediates`  (string): Intermediate certificates
* `ip_addresses` / `ipAddresses`  (array(string)): A list of IP addresses associated with this SSL Certificate
* `issuer` / `issuer`  (string): X509 issuer
* `key_type` / `keyType`  (string): Type of key
* `request` / `request`  (string): Certificate signing request text
* `status` / `status`  (string): Certificate status (Request Generated, New, Active, Active/Expired, Expired, or Available)
* `subject` / `subject`  (string): X509 Subject name
* `certificate_domain` / `certificateDomain`  (string): Domain for certificate.
* `certificate_extra_domains` / `certificateExtraDomains`  (array(string)): Additional domains for the certificate CSR.
* `certificate_country` / `certificateCountry`  (string): Country.
* `certificate_state_or_province` / `certificateStateOrProvince`  (string): State or province.
* `certificate_city_or_locale` / `certificateCityOrLocale`  (string): City or locale.
* `certificate_company_name` / `certificateCompanyName`  (string): Company name.
* `csr_ou1` / `csrOu1`  (string): Department name or organization unit 1
* `csr_ou2` / `csrOu2`  (string): Department name or organization unit 2
* `csr_ou3` / `csrOu3`  (string): Department name or organization unit 3
* `certificate_email_address` / `certificateEmailAddress`  (string): Email address for the certificate owner.
* `private_key` / `privateKey`  (string): Certificate private key.
* `password` / `password`  (string): Certificate password.


---

## List Certificates

```java
HashMap<String, Object> parameters = new HashMap<>();
ListIterator<Certificate> certificates = Certificate.list(parameters, null);
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).


---

## Show Certificate

```java
HashMap<String, Object> parameters = new HashMap<>();
Certificate certificate = Certificate.find(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Certificate ID.


---

## Create CSR or import existing SSL Certificate

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "Name");
parameters.put("certificate_domain", "domain.com");
parameters.put("certificate_extra_domains", new String[] {"www.domain.com"});
parameters.put("key_type", "RSA-4096");
parameters.put("certificate", "[certificate]");
parameters.put("intermediates", "[certificates]");
Certificate certificate = Certificate.create(parameters, null);
```

### Parameters

* `name` (String): Required - Internal name of the SSL certificate.
* `certificate_domain` (String): Domain for certificate.
* `certificate_extra_domains` (String[]): Additional domains for the certificate CSR.
* `certificate_country` (String): Country.
* `certificate_state_or_province` (String): State or province.
* `certificate_city_or_locale` (String): City or locale.
* `certificate_company_name` (String): Company name.
* `csr_ou1` (String): Department name or organization unit 1
* `csr_ou2` (String): Department name or organization unit 2
* `csr_ou3` (String): Department name or organization unit 3
* `certificate_email_address` (String): Email address for the certificate owner.
* `key_type` (String): Any supported key type.  Defaults to `RSA-4096`.
* `certificate` (String): The certificate.  PEM or PKCS7 formats accepted.
* `private_key` (String): Certificate private key.
* `password` (String): Certificate password.
* `intermediates` (String): Intermediate certificates.  PEM or PKCS7 formats accepted.


---

## Activate SSL Certificate

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("replace_cert", "");
Certificate.activate(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Certificate ID.
* `replace_cert` (String): Leave blank or set to `any` to replace any currently active certificate, if applicable.  Set to `new_ips` to activate this certificate as an additional certificate on your Site by allocating a new set of Dedicated IPs (may require a Plan upgrade).  Set to the ID of a currently active certificate to replace that certificate on its set of dedicated IPs.  A certificate belonging to a Custom Domain activates only for that domain: leave blank, use `any`, or specify that domain's current certificate ID.


---

## Deactivate SSL Certificate

```java
HashMap<String, Object> parameters = new HashMap<>();
Certificate.deactivate(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Certificate ID.


---

## Replace this active custom SSL Certificate for the Primary Custom Domain with a Files.com SSL Certificate.  The Primary Custom Domain's CNAME record must point at the Site's subdomain.  The Files.com certificate is issued and activated in the background

```java
HashMap<String, Object> parameters = new HashMap<>();
Certificate.replaceWithFiles(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Certificate ID.


---

## Update Certificate

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "My Certificate");
parameters.put("intermediates", "[certificates]");
parameters.put("certificate", "[certificate]");
Certificate certificate = Certificate.update(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Certificate ID.
* `name` (String): Internal certificate name.
* `intermediates` (String): Any intermediate certificates.  PEM or PKCS7 formats accepted.
* `certificate` (String): The certificate.  PEM or PKCS7 formats accepted.


---

## Delete Certificate

```java
HashMap<String, Object> parameters = new HashMap<>();
Certificate.delete(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Certificate ID.


---

## Activate SSL Certificate

```java
Certificate certificate = Certificate.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("replace_cert", "");
certificate.activate(parameters);
```

### Parameters

* `id` (Long): Required - Certificate ID.
* `replace_cert` (String): Leave blank or set to `any` to replace any currently active certificate, if applicable.  Set to `new_ips` to activate this certificate as an additional certificate on your Site by allocating a new set of Dedicated IPs (may require a Plan upgrade).  Set to the ID of a currently active certificate to replace that certificate on its set of dedicated IPs.  A certificate belonging to a Custom Domain activates only for that domain: leave blank, use `any`, or specify that domain's current certificate ID.


---

## Deactivate SSL Certificate

```java
Certificate certificate = Certificate.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
certificate.deactivate(parameters);
```

### Parameters

* `id` (Long): Required - Certificate ID.


---

## Replace this active custom SSL Certificate for the Primary Custom Domain with a Files.com SSL Certificate.  The Primary Custom Domain's CNAME record must point at the Site's subdomain.  The Files.com certificate is issued and activated in the background

```java
Certificate certificate = Certificate.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
certificate.replaceWithFiles(parameters);
```

### Parameters

* `id` (Long): Required - Certificate ID.


---

## Update Certificate

```java
Certificate certificate = Certificate.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "My Certificate");
parameters.put("intermediates", "[certificates]");
parameters.put("certificate", "[certificate]");
Certificate result = certificate.update(parameters);
```

### Parameters

* `id` (Long): Required - Certificate ID.
* `name` (String): Internal certificate name.
* `intermediates` (String): Any intermediate certificates.  PEM or PKCS7 formats accepted.
* `certificate` (String): The certificate.  PEM or PKCS7 formats accepted.


---

## Delete Certificate

```java
Certificate certificate = Certificate.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
certificate.delete(parameters);
```

### Parameters

* `id` (Long): Required - Certificate ID.
