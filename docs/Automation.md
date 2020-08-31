# Files.Models.Automation

## Example Automation Object

```
{
  "id": 1,
  "automation": "create_folder",
  "source": "",
  "destination": "",
  "destination_replace_from": "",
  "destination_replace_to": "",
  "interval": "week",
  "next_process_on": "2020-01-01",
  "path": "",
  "realtime": true,
  "user_id": 1,
  "user_ids": [

  ],
  "group_ids": [

  ]
}
```

* `id` / `id`  (int64): Automation ID
* `automation` / `automation`  (string): Automation type
* `source` / `source`  (string): Source Path
* `destination` / `destination`  (string): Destination Path
* `destination_replace_from` / `destinationReplaceFrom`  (string): If set, this string in the destination path will be replaced with the value in `destination_replace_to`.
* `destination_replace_to` / `destinationReplaceTo`  (string): If set, this string will replace the value `destination_replace_from` in the destination filename. You can use special patterns here.
* `interval` / `interval`  (string): How often to run this automation?  One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
* `next_process_on` / `nextProcessOn`  (string): Date this automation will next run.
* `path` / `path`  (string): Path on which this Automation runs.  Supports globs. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `realtime` / `realtime`  (boolean): Does this automation run in real time?  This is a read-only property based on automation type.
* `user_id` / `userId`  (int64): User ID of the Automation's creator.
* `user_ids` / `userIds`  (array): IDs of Users for the Automation (i.e. who to Request File from)
* `group_ids` / `groupIds`  (array): IDs of Groups for the Automation (i.e. who to Request File from)


---

## List Automations

```
List<Automation> automation = Automation.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `page` (Long): Current page number.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `action` (String): Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
* `cursor` (String): Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `site_id` and `automation`.
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

* `automation` (String): Required - Type of automation.  One of: `create_folder`, `request_file`, `request_move`
* `source` (String): Source Path
* `destination` (String): Destination Path
* `destination_replace_from` (String): If set, this string in the destination path will be replaced with the value in `destination_replace_to`.
* `destination_replace_to` (String): If set, this string will replace the value `destination_replace_from` in the destination filename. You can use special patterns here.
* `interval` (String): How often to run this automation? One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
* `path` (String): Path on which this Automation runs.  Supports globs.
* `user_ids` (String): A list of user IDs the automation is associated with. If sent as a string, it should be comma-delimited.
* `group_ids` (String): A list of group IDs the automation is associated with. If sent as a string, it should be comma-delimited.


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
* `automation` (String): Required - Type of automation.  One of: `create_folder`, `request_file`, `request_move`
* `source` (String): Source Path
* `destination` (String): Destination Path
* `destination_replace_from` (String): If set, this string in the destination path will be replaced with the value in `destination_replace_to`.
* `destination_replace_to` (String): If set, this string will replace the value `destination_replace_from` in the destination filename. You can use special patterns here.
* `interval` (String): How often to run this automation? One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
* `path` (String): Path on which this Automation runs.  Supports globs.
* `user_ids` (String): A list of user IDs the automation is associated with. If sent as a string, it should be comma-delimited.
* `group_ids` (String): A list of group IDs the automation is associated with. If sent as a string, it should be comma-delimited.


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
parameters.put("destination", "destination");
parameters.put("interval", "year");

Automation.Update(parameters);
```

### Parameters

* `id` (Long): Required - Automation ID.
* `automation` (String): Required - Type of automation.  One of: `create_folder`, `request_file`, `request_move`
* `source` (String): Source Path
* `destination` (String): Destination Path
* `destination_replace_from` (String): If set, this string in the destination path will be replaced with the value in `destination_replace_to`.
* `destination_replace_to` (String): If set, this string will replace the value `destination_replace_from` in the destination filename. You can use special patterns here.
* `interval` (String): How often to run this automation? One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
* `path` (String): Path on which this Automation runs.  Supports globs.
* `user_ids` (String): A list of user IDs the automation is associated with. If sent as a string, it should be comma-delimited.
* `group_ids` (String): A list of group IDs the automation is associated with. If sent as a string, it should be comma-delimited.


---

## Delete Automation

```
Automation automation = Automation.ListFor(path)[0];

HashMap<String, Object> parameters = new HashMap<>();


Automation.Delete
```

### Parameters

* `id` (Long): Required - Automation ID.
