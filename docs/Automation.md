# Files.Models.Automation

## Example Automation Object

```
{
  "id": 1,
  "automation": "create_folder",
  "trigger": "realtime",
  "interval": "week",
  "name": "",
  "schedule": {
    "days_of_week": [
      0,
      2,
      4
    ],
    "times_of_day": [
      "6:30",
      "14:30"
    ],
    "time_zone": "Eastern Time (US & Canada)"
  },
  "source": "",
  "destinations": [
    "destination"
  ],
  "destination_replace_from": "",
  "destination_replace_to": "",
  "description": "",
  "path": "",
  "user_id": 1,
  "user_ids": [
    1,
    2
  ],
  "group_ids": [
    1,
    2
  ],
  "webhook_url": "https://app.files.com/api/webhooks/abc123",
  "trigger_actions": "[ \"create\" ]",
  "trigger_action_path": "path/to/file/or/folder",
  "value": "{\"limit\": \"1\"}"
}
```

* `id` / `id`  (int64): Automation ID
* `automation` / `automation`  (string): Automation type
* `trigger` / `trigger`  (string): How this automation is triggered to run. One of: `realtime`, `daily`, `custom_schedule`, `webhook`, `email`, or `action`.
* `interval` / `interval`  (string): If trigger is `daily`, this specifies how often to run this automation.  One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
* `name` / `name`  (string): Name for this automation.
* `schedule` / `schedule`  (object): If trigger is `custom_schedule`, Custom schedule description for when the automation should be run.
* `source` / `source`  (string): Source Path
* `destinations` / `destinations`  (string): Destination Path
* `destination_replace_from` / `destinationReplaceFrom`  (string): If set, this string in the destination path will be replaced with the value in `destination_replace_to`.
* `destination_replace_to` / `destinationReplaceTo`  (string): If set, this string will replace the value `destination_replace_from` in the destination filename. You can use special patterns here.
* `description` / `description`  (string): Description for the this Automation.
* `path` / `path`  (string): Path on which this Automation runs.  Supports globs. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `user_id` / `userId`  (int64): User ID of the Automation's creator.
* `user_ids` / `userIds`  (array): IDs of Users for the Automation (i.e. who to Request File from)
* `group_ids` / `groupIds`  (array): IDs of Groups for the Automation (i.e. who to Request File from)
* `webhook_url` / `webhookUrl`  (string): If trigger is `webhook`, this is the URL of the webhook to trigger the Automation.
* `trigger_actions` / `triggerActions`  (string): If trigger is `action`, this is the list of action types on which to trigger the automation. Valid actions are create, read, update, destroy, move, copy
* `trigger_action_path` / `triggerActionPath`  (string): If trigger is `action`, this is the path to watch for the specified trigger actions.
* `value` / `value`  (object): A Hash of attributes specific to the automation type.
* `destination` / `destination`  (string): DEPRECATED: Destination Path. Use `destinations` instead.


---

## List Automations

```
List<Automation> automation = Automation.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `automation`.
* `filter` (Map<String, String>): If set, return records where the specifiied field is equal to the supplied value. Valid fields are `automation`.
* `filter_gt` (Map<String, String>): If set, return records where the specifiied field is greater than the supplied value. Valid fields are `automation`.
* `filter_gteq` (Map<String, String>): If set, return records where the specifiied field is greater than or equal to the supplied value. Valid fields are `automation`.
* `filter_like` (Map<String, String>): If set, return records where the specifiied field is equal to the supplied value. Valid fields are `automation`.
* `filter_lt` (Map<String, String>): If set, return records where the specifiied field is less than the supplied value. Valid fields are `automation`.
* `filter_lteq` (Map<String, String>): If set, return records where the specifiied field is less than or equal to the supplied value. Valid fields are `automation`.
* `automation` (String): DEPRECATED: Type of automation to filter by. Use `filter[automation]` instead.


---

## Show Automation

```
List<Automation> automation = Automation.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Automation ID.


---

## Create Automation

```
Automation automation = Automation.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `automation` (String): Required - Automation type
* `source` (String): Source Path
* `destination` (String): DEPRECATED: Destination Path. Use `destinations` instead.
* `destinations` (String[]): A list of String destination paths or Hash of folder_path and optional file_path.
* `destination_replace_from` (String): If set, this string in the destination path will be replaced with the value in `destination_replace_to`.
* `destination_replace_to` (String): If set, this string will replace the value `destination_replace_from` in the destination filename. You can use special patterns here.
* `interval` (String): How often to run this automation? One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
* `path` (String): Path on which this Automation runs.  Supports globs.
* `user_ids` (String): A list of user IDs the automation is associated with. If sent as a string, it should be comma-delimited.
* `group_ids` (String): A list of group IDs the automation is associated with. If sent as a string, it should be comma-delimited.
* `schedule` (Map<String, String>): Custom schedule for running this automation.
* `description` (String): Description for the this Automation.
* `name` (String): Name for this automation.
* `trigger` (String): How this automation is triggered to run. One of: `realtime`, `daily`, `custom_schedule`, `webhook`, `email`, or `action`.
* `trigger_actions` (String[]): If trigger is `action`, this is the list of action types on which to trigger the automation. Valid actions are create, read, update, destroy, move, copy
* `trigger_action_path` (String): If trigger is `action`, this is the path to watch for the specified trigger actions.
* `value` (Map<String, String>): A Hash of attributes specific to the automation type.


---

## Update Automation

```
Automation automation = Automation.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Automation ID.
* `automation` (String): Required - Automation type
* `source` (String): Source Path
* `destination` (String): DEPRECATED: Destination Path. Use `destinations` instead.
* `destinations` (String[]): A list of String destination paths or Hash of folder_path and optional file_path.
* `destination_replace_from` (String): If set, this string in the destination path will be replaced with the value in `destination_replace_to`.
* `destination_replace_to` (String): If set, this string will replace the value `destination_replace_from` in the destination filename. You can use special patterns here.
* `interval` (String): How often to run this automation? One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
* `path` (String): Path on which this Automation runs.  Supports globs.
* `user_ids` (String): A list of user IDs the automation is associated with. If sent as a string, it should be comma-delimited.
* `group_ids` (String): A list of group IDs the automation is associated with. If sent as a string, it should be comma-delimited.
* `schedule` (Map<String, String>): Custom schedule for running this automation.
* `description` (String): Description for the this Automation.
* `name` (String): Name for this automation.
* `trigger` (String): How this automation is triggered to run. One of: `realtime`, `daily`, `custom_schedule`, `webhook`, `email`, or `action`.
* `trigger_actions` (String[]): If trigger is `action`, this is the list of action types on which to trigger the automation. Valid actions are create, read, update, destroy, move, copy
* `trigger_action_path` (String): If trigger is `action`, this is the path to watch for the specified trigger actions.
* `value` (Map<String, String>): A Hash of attributes specific to the automation type.


---

## Delete Automation

```
Automation automation = Automation.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Automation ID.


---

## Update Automation

```
Automation automation = Automation.ListFor(path)[0];

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("automation", "create_folder");
parameters.put("source", "source");
parameters.put("destinations", "[\"folder_a/file_a.txt\", {\"folder_path\":\"folder_b\", \"file_path\":\"file_b.txt\"}, {\"folder_path\":\"folder_c\"}]");
parameters.put("interval", "year");
parameters.put("user_ids", [1,2]);
parameters.put("group_ids", [1,2]);
parameters.put("schedule", "{\"days_of_week\": [ 0, 1, 3 ], \"times_of_day\": [ \"7:30\", \"11:30\" ], \"time_zone\": \"Eastern Time (US & Canada)\"}");
parameters.put("trigger", "realtime");
parameters.put("trigger_actions", "[ \"create\" ]");
parameters.put("trigger_action_path", "path/to/file/or/folder");
parameters.put("value", "{\"limit\": \"1\"}");

Automation.Update(parameters);
```

### Parameters

* `id` (Long): Required - Automation ID.
* `automation` (String): Required - Automation type
* `source` (String): Source Path
* `destination` (String): DEPRECATED: Destination Path. Use `destinations` instead.
* `destinations` (String[]): A list of String destination paths or Hash of folder_path and optional file_path.
* `destination_replace_from` (String): If set, this string in the destination path will be replaced with the value in `destination_replace_to`.
* `destination_replace_to` (String): If set, this string will replace the value `destination_replace_from` in the destination filename. You can use special patterns here.
* `interval` (String): How often to run this automation? One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
* `path` (String): Path on which this Automation runs.  Supports globs.
* `user_ids` (String): A list of user IDs the automation is associated with. If sent as a string, it should be comma-delimited.
* `group_ids` (String): A list of group IDs the automation is associated with. If sent as a string, it should be comma-delimited.
* `schedule` (Map<String, String>): Custom schedule for running this automation.
* `description` (String): Description for the this Automation.
* `name` (String): Name for this automation.
* `trigger` (String): How this automation is triggered to run. One of: `realtime`, `daily`, `custom_schedule`, `webhook`, `email`, or `action`.
* `trigger_actions` (String[]): If trigger is `action`, this is the list of action types on which to trigger the automation. Valid actions are create, read, update, destroy, move, copy
* `trigger_action_path` (String): If trigger is `action`, this is the path to watch for the specified trigger actions.
* `value` (Map<String, String>): A Hash of attributes specific to the automation type.


---

## Delete Automation

```
Automation automation = Automation.ListFor(path)[0];

HashMap<String, Object> parameters = new HashMap<>();


Automation.Delete
```

### Parameters

* `id` (Long): Required - Automation ID.
