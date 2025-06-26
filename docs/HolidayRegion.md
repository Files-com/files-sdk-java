# Files.Models.HolidayRegion

## Example HolidayRegion Object

```
{
  "code": "us_dc",
  "name": "United States - District of Columbia"
}
```

* `code` / `code`  (string): The code representing a region
* `name` / `name`  (string): The name of the region


---

## List all possible holiday regions

```
HolidayRegion holidayRegion = HolidayRegion.getSupported(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
