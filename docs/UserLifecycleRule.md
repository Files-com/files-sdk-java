# Files.Models.UserLifecycleRule

## Example UserLifecycleRule Object

```
{
  "id": 1,
  "authentication_method": "password",
  "group_ids": [
    1,
    2,
    3
  ],
  "action": "disable",
  "inactivity_days": 12,
  "include_folder_admins": true,
  "include_site_admins": true,
  "name": "password specific rules",
  "partner_tag": "guest",
  "site_id": 1,
  "user_state": "inactive",
  "user_tag": "guest"
}
```

* `id` / `id`  (int64): User Lifecycle Rule ID
* `authentication_method` / `authenticationMethod`  (string): User authentication method for which the rule will apply.
* `group_ids` / `groupIds`  (array(int64)): Array of Group IDs to which the rule applies. If empty or not set, the rule applies to all users.
* `action` / `action`  (string): Action to take on inactive users (disable or delete)
* `inactivity_days` / `inactivityDays`  (int64): Number of days of inactivity before the rule applies
* `include_folder_admins` / `includeFolderAdmins`  (boolean): If true, the rule will apply to folder admins.
* `include_site_admins` / `includeSiteAdmins`  (boolean): If true, the rule will apply to site admins.
* `name` / `name`  (string): User Lifecycle Rule name
* `partner_tag` / `partnerTag`  (string): If provided, only users belonging to Partners with this tag at the Partner level will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.
* `site_id` / `siteId`  (int64): Site ID
* `user_state` / `userState`  (string): State of the users to apply the rule to (inactive or disabled)
* `user_tag` / `userTag`  (string): If provided, only users with this tag will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.


---

## List User Lifecycle Rules

```
ListIterator<UserLifecycleRule> userLifecycleRule = UserLifecycleRule.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `site_id`.


---

## Show User Lifecycle Rule

```
UserLifecycleRule userLifecycleRule = UserLifecycleRule.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - User Lifecycle Rule ID.


---

## Create User Lifecycle Rule

```
UserLifecycleRule userLifecycleRule = UserLifecycleRule.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `action` (String): Action to take on inactive users (disable or delete)
* `authentication_method` (String): User authentication method for which the rule will apply.
* `group_ids` (Long[]): Array of Group IDs to which the rule applies. If empty or not set, the rule applies to all users.
* `inactivity_days` (Long): Number of days of inactivity before the rule applies
* `include_site_admins` (Boolean): If true, the rule will apply to site admins.
* `include_folder_admins` (Boolean): If true, the rule will apply to folder admins.
* `name` (String): User Lifecycle Rule name
* `partner_tag` (String): If provided, only users belonging to Partners with this tag at the Partner level will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.
* `user_state` (String): State of the users to apply the rule to (inactive or disabled)
* `user_tag` (String): If provided, only users with this tag will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.


---

## Update User Lifecycle Rule

```
UserLifecycleRule userLifecycleRule = UserLifecycleRule.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - User Lifecycle Rule ID.
* `action` (String): Action to take on inactive users (disable or delete)
* `authentication_method` (String): User authentication method for which the rule will apply.
* `group_ids` (Long[]): Array of Group IDs to which the rule applies. If empty or not set, the rule applies to all users.
* `inactivity_days` (Long): Number of days of inactivity before the rule applies
* `include_site_admins` (Boolean): If true, the rule will apply to site admins.
* `include_folder_admins` (Boolean): If true, the rule will apply to folder admins.
* `name` (String): User Lifecycle Rule name
* `partner_tag` (String): If provided, only users belonging to Partners with this tag at the Partner level will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.
* `user_state` (String): State of the users to apply the rule to (inactive or disabled)
* `user_tag` (String): If provided, only users with this tag will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.


---

## Delete User Lifecycle Rule

```
void userLifecycleRule = UserLifecycleRule.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - User Lifecycle Rule ID.


---

## Update User Lifecycle Rule

```
UserLifecycleRule userLifecycleRule = UserLifecycleRule.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("authentication_method", "password");
parameters.put("group_ids", [1,2,3]);
parameters.put("inactivity_days", 12);
parameters.put("include_site_admins", true);
parameters.put("include_folder_admins", true);
parameters.put("name", "password specific rules");
parameters.put("partner_tag", "guest");
parameters.put("user_state", "inactive");
parameters.put("user_tag", "guest");

userLifecycleRule.update(parameters);
```

### Parameters

* `id` (Long): Required - User Lifecycle Rule ID.
* `action` (String): Action to take on inactive users (disable or delete)
* `authentication_method` (String): User authentication method for which the rule will apply.
* `group_ids` (Long[]): Array of Group IDs to which the rule applies. If empty or not set, the rule applies to all users.
* `inactivity_days` (Long): Number of days of inactivity before the rule applies
* `include_site_admins` (Boolean): If true, the rule will apply to site admins.
* `include_folder_admins` (Boolean): If true, the rule will apply to folder admins.
* `name` (String): User Lifecycle Rule name
* `partner_tag` (String): If provided, only users belonging to Partners with this tag at the Partner level will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.
* `user_state` (String): State of the users to apply the rule to (inactive or disabled)
* `user_tag` (String): If provided, only users with this tag will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.


---

## Delete User Lifecycle Rule

```
UserLifecycleRule userLifecycleRule = UserLifecycleRule.find(id);

HashMap<String, Object> parameters = new HashMap<>();

userLifecycleRule.delete(parameters);
```

### Parameters

* `id` (Long): Required - User Lifecycle Rule ID.
