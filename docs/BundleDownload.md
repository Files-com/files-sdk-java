# Files.Models.BundleDownload

## Example BundleDownload Object

```
{
  "bundle_registration": "",
  "download_method": "file",
  "path": "a/b/test.txt",
  "created_at": "2020-01-01 00:00:00"
}
```

* `bundle_registration` / `bundleRegistration` : 
* `download_method` / `downloadMethod`  (string): Download method (file or full_zip)
* `path` / `path`  (string): Download path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `created_at` / `createdAt`  (date-time): Download date/time


---

## List Bundle Downloads

```
List<BundleDownload> bundleDownload = BundleDownload.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `created_at`.
* `filter` (Map<String, String>): If set, return records where the specifiied field is equal to the supplied value. Valid fields are `created_at`.
* `filter_gt` (Map<String, String>): If set, return records where the specifiied field is greater than the supplied value. Valid fields are `created_at`.
* `filter_gteq` (Map<String, String>): If set, return records where the specifiied field is greater than or equal to the supplied value. Valid fields are `created_at`.
* `filter_like` (Map<String, String>): If set, return records where the specifiied field is equal to the supplied value. Valid fields are `created_at`.
* `filter_lt` (Map<String, String>): If set, return records where the specifiied field is less than the supplied value. Valid fields are `created_at`.
* `filter_lteq` (Map<String, String>): If set, return records where the specifiied field is less than or equal to the supplied value. Valid fields are `created_at`.
* `bundle_id` (Long): Bundle ID
* `bundle_registration_id` (Long): BundleRegistration ID
