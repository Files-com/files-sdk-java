# Files.Models.Group

## Example Group Object

```
{
  "id": 1,
  "name": "owners",
  "admin_ids": [

  ],
  "notes": "",
  "user_ids": [

  ],
  "usernames": [

  ]
}
```

* `id` / `id`  (int64): Group ID
* `name` / `name`  (string): Group name
* `admin_ids` / `adminIds`  (array): List of user IDs who are group administrators (separated by commas)
* `notes` / `notes`  (string): Notes about this group
* `user_ids` / `userIds`  (array): List of user IDs who belong to this group (separated by commas)
* `usernames` / `usernames`  (array): List of usernames who belong to this group (separated by commas)


---

## List Groups

```
List<Group> group = Group.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `page` (Long): Current page number.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `action` (String): Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
* `cursor` (String): Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `sort_by` (Object): If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `active`, `site_id` or `name`.
* `filter` (Object): If set, return records where the specifiied field is equal to the supplied value. Valid fields are `name`.
* `filter_gt` (Object): If set, return records where the specifiied field is greater than the supplied value. Valid fields are `name`.
* `filter_gteq` (Object): If set, return records where the specifiied field is greater than or equal to the supplied value. Valid fields are `name`.
* `filter_like` (Object): If set, return records where the specifiied field is equal to the supplied value. Valid fields are `name`.
* `filter_lt` (Object): If set, return records where the specifiied field is less than the supplied value. Valid fields are `name`.
* `filter_lteq` (Object): If set, return records where the specifiied field is less than or equal to the supplied value. Valid fields are `name`.
* `ids` (String): Comma-separated list of group ids to include in results.


---

## Show Group

```
List<Group> group = Group.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Group ID.


---

## Create Group

```
Group group = Group.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `name` (String): Group name.
* `notes` (String): Group notes.
* `user_ids` (String): A list of user ids. If sent as a string, should be comma-delimited.
* `admin_ids` (String): A list of group admin user ids. If sent as a string, should be comma-delimited.


---

## Update Group

```
Group group = Group.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Group ID.
* `name` (String): Group name.
* `notes` (String): Group notes.
* `user_ids` (String): A list of user ids. If sent as a string, should be comma-delimited.
* `admin_ids` (String): A list of group admin user ids. If sent as a string, should be comma-delimited.


---

## Delete Group

```
Group group = Group.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Group ID.


---

## Update Group

```
Group group = Group.ListFor(path)[0];

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("name", "owners");

Group.Update(parameters);
```

### Parameters

* `id` (Long): Required - Group ID.
* `name` (String): Group name.
* `notes` (String): Group notes.
* `user_ids` (String): A list of user ids. If sent as a string, should be comma-delimited.
* `admin_ids` (String): A list of group admin user ids. If sent as a string, should be comma-delimited.


---

## Delete Group

```
Group group = Group.ListFor(path)[0];

HashMap<String, Object> parameters = new HashMap<>();


Group.Delete
```

### Parameters

* `id` (Long): Required - Group ID.
