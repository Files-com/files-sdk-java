# Files.Models.App

## Example App Object

```
{
  "name": "",
  "extended_description": "",
  "short_description": "",
  "documentation_links": "Important Info => http://files.test/learn-more",
  "icon_url": "",
  "logo_url": "",
  "screenshot_list_urls": [
    ""
  ],
  "logo_thumbnail_url": "",
  "sso_strategy_type": "",
  "remote_server_type": "",
  "folder_behavior_type": "",
  "external_homepage_url": "",
  "marketing_youtube_url": "",
  "tutorial_youtube_url": "",
  "app_type": "",
  "featured": true
}
```

* `name` / `name`  (string): Name of the App
* `extended_description` / `extendedDescription`  (string): Long form description of the App
* `short_description` / `shortDescription`  (string): Short description of the App
* `documentation_links` / `documentationLinks`  (object): Collection of named links to documentation
* `icon_url` / `iconUrl`  (string): App icon
* `logo_url` / `logoUrl`  (string): Full size logo for the App
* `screenshot_list_urls` / `screenshotListUrls`  (string): Screenshots of the App
* `logo_thumbnail_url` / `logoThumbnailUrl`  (string): Logo thumbnail for the App
* `sso_strategy_type` / `ssoStrategyType`  (string): Associated SSO Strategy type, if any
* `remote_server_type` / `remoteServerType`  (string): Associated Remote Server type, if any
* `folder_behavior_type` / `folderBehaviorType`  (string): Associated Folder Behavior type, if any
* `external_homepage_url` / `externalHomepageUrl`  (string): Link to external homepage
* `marketing_youtube_url` / `marketingYoutubeUrl`  (string): Marketing video page
* `tutorial_youtube_url` / `tutorialYoutubeUrl`  (string): Tutorial video page
* `app_type` / `appType`  (string): The type of the App
* `featured` / `featured`  (boolean): Is featured on the App listing?


---

## List Apps

```
List<App> app = App.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `name` and `app_type`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `name` and `app_type`.
* `filter_gt` (Map<String, String>): If set, return records where the specified field is greater than the supplied value. Valid fields are `name` and `app_type`.
* `filter_gteq` (Map<String, String>): If set, return records where the specified field is greater than or equal to the supplied value. Valid fields are `name` and `app_type`.
* `filter_like` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `name` and `app_type`.
* `filter_lt` (Map<String, String>): If set, return records where the specified field is less than the supplied value. Valid fields are `name` and `app_type`.
* `filter_lteq` (Map<String, String>): If set, return records where the specified field is less than or equal to the supplied value. Valid fields are `name` and `app_type`.
