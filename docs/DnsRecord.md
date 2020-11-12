# Files.Models.DnsRecord

## Example DnsRecord Object

```
{
  "id": "customdomain.com-CNAME-site.files.com",
  "domain": "my-custom-domain.com",
  "rrtype": "CNAME",
  "value": "mysite.files.com"
}
```

* `id` / `id`  (string): Unique label for DNS record; used by Zapier and other integrations.
* `domain` / `domain`  (string): DNS record domain name
* `rrtype` / `rrtype`  (string): DNS record type
* `value` / `value`  (string): DNS record value


---

## Show site DNS configuration

```
List<DnsRecord> dnsRecord = DnsRecord.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
