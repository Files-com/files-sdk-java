# Files.Models.UserLifecycleRule

## Example UserLifecycleRule Object

```
{
  "id": 1,
  "authentication_method": "password",
  "inactivity_days": 12,
  "include_folder_admins": true,
  "include_site_admins": true,
  "action": "disable",
  "user_state": "inactive",
  "name": "password specific rules",
  "site_id": 1
}
```

* `id` / `id`  (int64): User Lifecycle Rule ID
* `authentication_method` / `authenticationMethod`  (string): User authentication method for the rule
* `inactivity_days` / `inactivityDays`  (int64): Number of days of inactivity before the rule applies
* `include_folder_admins` / `includeFolderAdmins`  (boolean): Include folder admins in the rule
* `include_site_admins` / `includeSiteAdmins`  (boolean): Include site admins in the rule
* `action` / `action`  (string): Action to take on inactive users (disable or delete)
* `user_state` / `userState`  (string): State of the users to apply the rule to (inactive or disabled)
* `name` / `name`  (string): User Lifecycle Rule name
* `site_id` / `siteId`  (int64): Site ID


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
* `authentication_method` (String): User authentication method for the rule
* `inactivity_days` (Long): Number of days of inactivity before the rule applies
* `include_site_admins` (Boolean): Include site admins in the rule
* `include_folder_admins` (Boolean): Include folder admins in the rule
* `user_state` (String): State of the users to apply the rule to (inactive or disabled)
* `name` (String): User Lifecycle Rule name


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
* `authentication_method` (String): User authentication method for the rule
* `inactivity_days` (Long): Number of days of inactivity before the rule applies
* `include_site_admins` (Boolean): Include site admins in the rule
* `include_folder_admins` (Boolean): Include folder admins in the rule
* `user_state` (String): State of the users to apply the rule to (inactive or disabled)
* `name` (String): User Lifecycle Rule name


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
parameters.put("inactivity_days", 12);
parameters.put("include_site_admins", true);
parameters.put("include_folder_admins", true);
parameters.put("user_state", "inactive");
parameters.put("name", "password specific rules");

userLifecycleRule.update(parameters);
```

### Parameters

* `id` (Long): Required - User Lifecycle Rule ID.
* `action` (String): Action to take on inactive users (disable or delete)
* `authentication_method` (String): User authentication method for the rule
* `inactivity_days` (Long): Number of days of inactivity before the rule applies
* `include_site_admins` (Boolean): Include site admins in the rule
* `include_folder_admins` (Boolean): Include folder admins in the rule
* `user_state` (String): State of the users to apply the rule to (inactive or disabled)
* `name` (String): User Lifecycle Rule name


---

## Delete User Lifecycle Rule

```
UserLifecycleRule userLifecycleRule = UserLifecycleRule.find(id);

HashMap<String, Object> parameters = new HashMap<>();

userLifecycleRule.delete(parameters);
```

### Parameters

* `id` (Long): Required - User Lifecycle Rule ID.
