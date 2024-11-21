# Files.Models.App

## Example App Object

```
{
  "app_type": "example",
  "documentation_links": {
    "Important Info": "http://files.test/learn-more"
  },
  "extended_description": "example",
  "extended_description_for_marketing_site": "example",
  "external_homepage_url": "example",
  "featured": true,
  "folder_behavior_type": "example",
  "icon_url": "example",
  "logo_thumbnail_url": "example",
  "logo_url": "example",
  "marketing_intro": "example",
  "marketing_youtube_url": "example",
  "name": "example",
  "package_manager_install_command": "example",
  "remote_server_type": "example",
  "screenshot_list_urls": [
    "example"
  ],
  "sdk_installation_instructions_link": "example",
  "short_description": "example",
  "sso_strategy_type": "example",
  "siem_type": "example",
  "tutorial_youtube_url": "example"
}
```

* `app_type` / `appType`  (string): The type of the App
* `documentation_links` / `documentationLinks`  (object): Collection of named links to documentation
* `extended_description` / `extendedDescription`  (string): Long description for the in-App landing page
* `extended_description_for_marketing_site` / `extendedDescriptionForMarketingSite`  (string): Long form description of the App
* `external_homepage_url` / `externalHomepageUrl`  (string): Link to external homepage
* `featured` / `featured`  (boolean): Is featured on the App listing?
* `folder_behavior_type` / `folderBehaviorType`  (string): Associated Folder Behavior type, if any
* `icon_url` / `iconUrl`  (string): App icon
* `logo_thumbnail_url` / `logoThumbnailUrl`  (string): Logo thumbnail for the App
* `logo_url` / `logoUrl`  (string): Full size logo for the App
* `marketing_intro` / `marketingIntro`  (string): Marketing introdution of the App
* `marketing_youtube_url` / `marketingYoutubeUrl`  (string): Marketing video page
* `name` / `name`  (string): Name of the App
* `package_manager_install_command` / `packageManagerInstallCommand`  (string): Package manager install command
* `remote_server_type` / `remoteServerType`  (string): Associated Remote Server type, if any
* `screenshot_list_urls` / `screenshotListUrls`  (array(string)): Screenshots of the App
* `sdk_installation_instructions_link` / `sdkInstallationInstructionsLink`  (string): Link to SDK installation instructions
* `short_description` / `shortDescription`  (string): Short description of the App
* `sso_strategy_type` / `ssoStrategyType`  (string): Associated SSO Strategy type, if any
* `siem_type` / `siemType`  (string): Associated SIEM type, if any
* `tutorial_youtube_url` / `tutorialYoutubeUrl`  (string): Tutorial video page


---

## List Apps

```
ListIterator<App> app = App.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `name` and `app_type`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `name` and `app_type`. Valid field combinations are `[ name, app_type ]`.
* `filter_prefix` (Map<String, String>): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `name`.


---

## Create Export App

```
Export app = App.createExport(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `name` and `app_type`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `name` and `app_type`. Valid field combinations are `[ name, app_type ]`.
* `filter_prefix` (Map<String, String>): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `name`.
