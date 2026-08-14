# Files.Models.HolidayCalendar

## Example HolidayCalendar Object

```
{
  "id": 1,
  "name": "Company Holidays",
  "definition": {
    "months": {
      "0": [
        {
          "name": "Good Friday",
          "function": "easter(year)",
          "function_modifier": -2
        }
      ],
      "1": [
        {
          "name": "New Year's Day",
          "mday": 1,
          "observed": "to_weekday_if_weekend(date)"
        },
        {
          "name": "Third Monday",
          "week": 3,
          "wday": 1
        }
      ],
      "11": [
        {
          "name": "Thanksgiving",
          "week": 4,
          "wday": 4
        }
      ],
      "12": [
        {
          "name": "Christmas Eve Early Close",
          "mday": 24,
          "start_time": "13:00",
          "end_time": "17:00",
          "year_ranges": {
            "from": 2026
          }
        }
      ]
    }
  },
  "created_at": "2000-01-01T01:00:00Z",
  "updated_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): Holiday Calendar ID. Set a scheduled resource's `holiday_region` to `custom_` followed by this ID to make it skip the days in this calendar.
* `name` / `name`  (string): Holiday Calendar name.
* `definition` / `definition`  (object): Holiday rules for the calendar.
* `created_at` / `createdAt`  (date-time): Creation time.
* `updated_at` / `updatedAt`  (date-time): Last update time.


---

## List Holiday Calendars

```
ListIterator<HolidayCalendar> holidayCalendar = HolidayCalendar.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are .


---

## Show Holiday Calendar

```
HolidayCalendar holidayCalendar = HolidayCalendar.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Holiday Calendar ID.


---

## Create Holiday Calendar

```
HolidayCalendar holidayCalendar = HolidayCalendar.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `definition` (Object): Required - Holiday rules for the calendar.
* `name` (String): Required - Holiday Calendar name.


---

## Update Holiday Calendar

```
HolidayCalendar holidayCalendar = HolidayCalendar.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Holiday Calendar ID.
* `definition` (Object): Holiday rules for the calendar.
* `name` (String): Holiday Calendar name.


---

## Delete Holiday Calendar

```
void holidayCalendar = HolidayCalendar.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Holiday Calendar ID.


---

## Update Holiday Calendar

```
HolidayCalendar holidayCalendar = HolidayCalendar.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("definition", {"months":{"0":[{"name":"Good Friday","function":"easter(year)","function_modifier":-2}],"1":[{"name":"New Year's Day","mday":1,"observed":"to_weekday_if_weekend(date)"},{"name":"Third Monday","week":3,"wday":1}],"11":[{"name":"Thanksgiving","week":4,"wday":4}],"12":[{"name":"Christmas Eve Early Close","mday":24,"start_time":"13:00","end_time":"17:00","year_ranges":{"from":2026}}]}});
parameters.put("name", "Company Holidays");

holidayCalendar.update(parameters);
```

### Parameters

* `id` (Long): Required - Holiday Calendar ID.
* `definition` (Object): Holiday rules for the calendar.
* `name` (String): Holiday Calendar name.


---

## Delete Holiday Calendar

```
HolidayCalendar holidayCalendar = HolidayCalendar.find(id);

HashMap<String, Object> parameters = new HashMap<>();

holidayCalendar.delete(parameters);
```

### Parameters

* `id` (Long): Required - Holiday Calendar ID.
