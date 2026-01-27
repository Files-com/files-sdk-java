# Files.Models.InvoiceLineItem

## Example InvoiceLineItem Object

```
{
  "id": 1,
  "amount": "1.0",
  "created_at": "2000-01-01T01:00:00Z",
  "description": "Service from 2019-01-01 through 2019-12-31",
  "type": "invoice",
  "service_end_at": "2000-01-01T01:00:00Z",
  "service_start_at": "2000-01-01T01:00:00Z",
  "plan": "Premier",
  "site": "My site",
  "prepaid_bytes": 1,
  "prepaid_bytes_expire_at": "2000-01-01T01:00:00Z",
  "prepaid_bytes_used": 1,
  "prepaid_bytes_available": 1
}
```

* `id` / `id`  (int64): Invoice Line item Id
* `amount` / `amount`  (double): Invoice line item amount
* `created_at` / `createdAt`  (date-time): Invoice line item created at date/time
* `description` / `description`  (string): Invoice line item description
* `type` / `type`  (string): Invoice line item type
* `service_end_at` / `serviceEndAt`  (date-time): Invoice line item service end date/time
* `service_start_at` / `serviceStartAt`  (date-time): Invoice line item service start date/time
* `plan` / `plan`  (string): Plan name
* `site` / `site`  (string): Site name
* `prepaid_bytes` / `prepaidBytes`  (int64): Prepaid bytes purchased for this invoice line item
* `prepaid_bytes_expire_at` / `prepaidBytesExpireAt`  (date-time): When the prepaid bytes expire
* `prepaid_bytes_used` / `prepaidBytesUsed`  (int64): Total prepaid bytes used for this invoice line item
* `prepaid_bytes_available` / `prepaidBytesAvailable`  (int64): Available prepaid bytes for this invoice line item
