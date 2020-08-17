# Files.Models.InvoiceLineItem

## Example InvoiceLineItem Object

```
{
  "amount": 1.0,
  "created_at": "2000-01-01T01:00:00Z",
  "description": "Service from 2019-01-01 through 2019-12-31",
  "type": "invoice",
  "service_end_at": "2000-01-01T01:00:00Z",
  "service_start_at": "2000-01-01T01:00:00Z",
  "updated_at": "2000-01-01T01:00:00Z",
  "plan": "Enterprise",
  "site": "My site"
}
```

* `amount` / `amount`  (double): Invoice line item amount
* `created_at` / `createdAt`  (date-time): Invoice line item created at date/time
* `description` / `description`  (string): Invoice line item description
* `type` / `type`  (string): Invoice line item type
* `service_end_at` / `serviceEndAt`  (date-time): Invoice line item service end date/time
* `service_start_at` / `serviceStartAt`  (date-time): Invoice line item service start date/time
* `updated_at` / `updatedAt`  (date-time): Invoice line item updated date/time
* `plan` / `plan`  (string): Plan name
* `site` / `site`  (string): Site name
