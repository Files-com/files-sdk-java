# Files.Models.UsageDailySnapshot

## Example UsageDailySnapshot Object

```
{
  "id": 1,
  "date": "2020-11-21",
  "current_storage": "65536",
  "usage_by_top_level_dir": [

  ]
}
```

* `id` / `id`  (int64): ID of the usage record
* `date` / `date`  (date): The date of this usage record
* `current_storage` / `currentStorage`  (int64): The quantity of storage held for this site
* `usage_by_top_level_dir` / `usageByTopLevelDir`  (array): Usage broken down by each top-level folder


---

## List Usage Daily Snapshots

```
List<UsageDailySnapshot> usageDailySnapshot = UsageDailySnapshot.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `site_id`, `date` or `usage_snapshot_id`.
* `filter` (Map<String, String>): If set, return records where the specifiied field is equal to the supplied value. Valid fields are `date` and `usage_snapshot_id`.
* `filter_gt` (Map<String, String>): If set, return records where the specifiied field is greater than the supplied value. Valid fields are `date` and `usage_snapshot_id`.
* `filter_gteq` (Map<String, String>): If set, return records where the specifiied field is greater than or equal to the supplied value. Valid fields are `date` and `usage_snapshot_id`.
* `filter_like` (Map<String, String>): If set, return records where the specifiied field is equal to the supplied value. Valid fields are `date` and `usage_snapshot_id`.
* `filter_lt` (Map<String, String>): If set, return records where the specifiied field is less than the supplied value. Valid fields are `date` and `usage_snapshot_id`.
* `filter_lteq` (Map<String, String>): If set, return records where the specifiied field is less than or equal to the supplied value. Valid fields are `date` and `usage_snapshot_id`.
