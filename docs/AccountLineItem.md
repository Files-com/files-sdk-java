# Files.Models.AccountLineItem

## Example AccountLineItem Object

```
{
  "id": 1,
  "amount": 1.0,
  "balance": 1.0,
  "created_at": "2000-01-01T01:00:00Z",
  "currency": "USD",
  "download_uri": "https://url...",
  "invoice_line_items": [

  ],
  "method": "paypal",
  "payment_line_items": [

  ],
  "payment_reversed_at": "2000-01-01T01:00:00Z",
  "payment_type": "",
  "site_name": "My Site",
  "type": "invoice",
  "updated_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): Line item Id
* `amount` / `amount`  (double): Line item amount
* `balance` / `balance`  (double): Line item balance
* `created_at` / `createdAt`  (date-time): Line item created at
* `currency` / `currency`  (string): Line item currency
* `download_uri` / `downloadUri`  (string): Line item download uri
* `invoice_line_items` / `invoiceLineItems`  (array): Associated invoice line items
* `method` / `method`  (string): Line item payment method
* `payment_line_items` / `paymentLineItems`  (array): Associated payment line items
* `payment_reversed_at` / `paymentReversedAt`  (date-time): Date/time payment was reversed if applicable
* `payment_type` / `paymentType`  (string): Type of payment if applicable
* `site_name` / `siteName`  (string): Site name this line item is for
* `type` / `type`  (string): Type of line item, either payment or invoice
* `updated_at` / `updatedAt`  (date-time): Line item updated at