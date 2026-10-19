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

```java
HashMap<String, Object> parameters = new HashMap<>();
ListIterator<HolidayCalendar> holidayCalendars = HolidayCalendar.list(parameters, null);
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are .


---

## Show Holiday Calendar

```java
HashMap<String, Object> parameters = new HashMap<>();
HolidayCalendar holidayCalendar = HolidayCalendar.find(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Holiday Calendar ID.


---

## Create Holiday Calendar

```java
HashMap<String, Object> parameters = new HashMap<>();
HashMap<String, Object> map1 = new HashMap<>();
HashMap<String, Object> map2 = new HashMap<>();
HashMap<String, Object> map3 = new HashMap<>();
map3.put("name", "Good Friday");
map3.put("function", "easter(year)");
map3.put("function_modifier", -2);
map2.put("0", new Object[] {map3});
HashMap<String, Object> map4 = new HashMap<>();
map4.put("name", "New Year's Day");
map4.put("mday", 1);
map4.put("observed", "to_weekday_if_weekend(date)");
HashMap<String, Object> map5 = new HashMap<>();
map5.put("name", "Third Monday");
map5.put("week", 3);
map5.put("wday", 1);
map2.put("1", new Object[] {map4, map5});
HashMap<String, Object> map6 = new HashMap<>();
map6.put("name", "Thanksgiving");
map6.put("week", 4);
map6.put("wday", 4);
map2.put("11", new Object[] {map6});
HashMap<String, Object> map7 = new HashMap<>();
map7.put("name", "Christmas Eve Early Close");
map7.put("mday", 24);
map7.put("start_time", "13:00");
map7.put("end_time", "17:00");
HashMap<String, Object> map8 = new HashMap<>();
map8.put("from", 2026);
map7.put("year_ranges", map8);
map2.put("12", new Object[] {map7});
map1.put("months", map2);
parameters.put("definition", map1);
parameters.put("name", "Company Holidays");
HolidayCalendar holidayCalendar = HolidayCalendar.create(parameters, null);
```

### Parameters

* `definition` (Object): Required - Holiday rules for the calendar.
* `name` (String): Required - Holiday Calendar name.


---

## Update Holiday Calendar

```java
HashMap<String, Object> parameters = new HashMap<>();
HashMap<String, Object> map1 = new HashMap<>();
HashMap<String, Object> map2 = new HashMap<>();
HashMap<String, Object> map3 = new HashMap<>();
map3.put("name", "Good Friday");
map3.put("function", "easter(year)");
map3.put("function_modifier", -2);
map2.put("0", new Object[] {map3});
HashMap<String, Object> map4 = new HashMap<>();
map4.put("name", "New Year's Day");
map4.put("mday", 1);
map4.put("observed", "to_weekday_if_weekend(date)");
HashMap<String, Object> map5 = new HashMap<>();
map5.put("name", "Third Monday");
map5.put("week", 3);
map5.put("wday", 1);
map2.put("1", new Object[] {map4, map5});
HashMap<String, Object> map6 = new HashMap<>();
map6.put("name", "Thanksgiving");
map6.put("week", 4);
map6.put("wday", 4);
map2.put("11", new Object[] {map6});
HashMap<String, Object> map7 = new HashMap<>();
map7.put("name", "Christmas Eve Early Close");
map7.put("mday", 24);
map7.put("start_time", "13:00");
map7.put("end_time", "17:00");
HashMap<String, Object> map8 = new HashMap<>();
map8.put("from", 2026);
map7.put("year_ranges", map8);
map2.put("12", new Object[] {map7});
map1.put("months", map2);
parameters.put("definition", map1);
parameters.put("name", "Company Holidays");
HolidayCalendar holidayCalendar = HolidayCalendar.update(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Holiday Calendar ID.
* `definition` (Object): Holiday rules for the calendar.
* `name` (String): Holiday Calendar name.


---

## Delete Holiday Calendar

```java
HashMap<String, Object> parameters = new HashMap<>();
HolidayCalendar.delete(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Holiday Calendar ID.


---

## Update Holiday Calendar

```java
HolidayCalendar holidayCalendar = HolidayCalendar.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
HashMap<String, Object> map1 = new HashMap<>();
HashMap<String, Object> map2 = new HashMap<>();
HashMap<String, Object> map3 = new HashMap<>();
map3.put("name", "Good Friday");
map3.put("function", "easter(year)");
map3.put("function_modifier", -2);
map2.put("0", new Object[] {map3});
HashMap<String, Object> map4 = new HashMap<>();
map4.put("name", "New Year's Day");
map4.put("mday", 1);
map4.put("observed", "to_weekday_if_weekend(date)");
HashMap<String, Object> map5 = new HashMap<>();
map5.put("name", "Third Monday");
map5.put("week", 3);
map5.put("wday", 1);
map2.put("1", new Object[] {map4, map5});
HashMap<String, Object> map6 = new HashMap<>();
map6.put("name", "Thanksgiving");
map6.put("week", 4);
map6.put("wday", 4);
map2.put("11", new Object[] {map6});
HashMap<String, Object> map7 = new HashMap<>();
map7.put("name", "Christmas Eve Early Close");
map7.put("mday", 24);
map7.put("start_time", "13:00");
map7.put("end_time", "17:00");
HashMap<String, Object> map8 = new HashMap<>();
map8.put("from", 2026);
map7.put("year_ranges", map8);
map2.put("12", new Object[] {map7});
map1.put("months", map2);
parameters.put("definition", map1);
parameters.put("name", "Company Holidays");
HolidayCalendar result = holidayCalendar.update(parameters);
```

### Parameters

* `id` (Long): Required - Holiday Calendar ID.
* `definition` (Object): Holiday rules for the calendar.
* `name` (String): Holiday Calendar name.


---

## Delete Holiday Calendar

```java
HolidayCalendar holidayCalendar = HolidayCalendar.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
holidayCalendar.delete(parameters);
```

### Parameters

* `id` (Long): Required - Holiday Calendar ID.
