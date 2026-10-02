# Files.Models.UserLifecycleRule

## Example UserLifecycleRule Object

```
{
  "id": 1,
  "authentication_method": "all_non_sso",
  "group_ids": [
    1,
    2,
    3
  ],
  "action": "disable",
  "inactivity_days": 12,
  "include_folder_admins": true,
  "include_site_admins": true,
  "apply_to_all_workspaces": true,
  "name": "password specific rules",
  "notify_users": true,
  "partner_tag": "guest",
  "site_id": 1,
  "workspace_id": 1,
  "user_state": "inactive",
  "user_tag": "guest"
}
```

* `id` / `id`  (int64): User Lifecycle Rule ID
* `authentication_method` / `authenticationMethod`  (string): User authentication method for which the rule will apply. Use `all_non_sso` to target every non-SSO authentication method with one rule.
* `group_ids` / `groupIds`  (array(int64)): Array of Group IDs to which the rule applies. If empty or not set, the rule applies to all users.
* `action` / `action`  (string): Action to take on inactive users (disable or delete)
* `inactivity_days` / `inactivityDays`  (int64): Number of days of inactivity before the rule applies
* `include_folder_admins` / `includeFolderAdmins`  (boolean): If true, the rule will apply to folder admins.
* `include_site_admins` / `includeSiteAdmins`  (boolean): If true, the rule includes Site Administrators, who always belong to the Default Workspace. Can only be enabled when `workspace_id` is `0`.
* `apply_to_all_workspaces` / `applyToAllWorkspaces`  (boolean): If true, a Default Workspace rule also applies to users in all Custom Workspaces. Can only be enabled when `workspace_id` is `0`.
* `name` / `name`  (string): User Lifecycle Rule name
* `notify_users` / `notifyUsers`  (boolean): If true, users will be emailed before the rule disables or deletes them.
* `partner_tag` / `partnerTag`  (string): If provided, only users belonging to Partners with this tag at the Partner level will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.
* `site_id` / `siteId`  (int64): Site ID
* `workspace_id` / `workspaceId`  (int64): Workspace whose users the rule applies to. `0` means the Default Workspace. A Custom Workspace rule applies only to users who belong to that Workspace, regardless of access granted to other users.
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
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `site_id` and `workspace_id`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id`.


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
* `apply_to_all_workspaces` (Boolean): If true, a Default Workspace rule also applies to users in all Custom Workspaces. Can only be enabled when `workspace_id` is `0`.
* `authentication_method` (String): User authentication method for which the rule will apply. Use `all_non_sso` to target every non-SSO authentication method with one rule.
* `group_ids` (Long[]): Array of Group IDs to which the rule applies. If empty or not set, the rule applies to all users.
* `inactivity_days` (Long): Number of days of inactivity before the rule applies
* `include_site_admins` (Boolean): If true, the rule includes Site Administrators, who always belong to the Default Workspace. Can only be enabled when `workspace_id` is `0`.
* `include_folder_admins` (Boolean): If true, the rule will apply to folder admins.
* `name` (String): User Lifecycle Rule name
* `notify_users` (Boolean): If true, users will be emailed before the rule disables or deletes them.
* `partner_tag` (String): If provided, only users belonging to Partners with this tag at the Partner level will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.
* `user_state` (String): State of the users to apply the rule to (inactive or disabled)
* `user_tag` (String): If provided, only users with this tag will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.
* `workspace_id` (Long): Workspace whose users the rule applies to. `0` means the Default Workspace. A Custom Workspace rule applies only to users who belong to that Workspace, regardless of access granted to other users.


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
* `apply_to_all_workspaces` (Boolean): If true, a Default Workspace rule also applies to users in all Custom Workspaces. Can only be enabled when `workspace_id` is `0`.
* `authentication_method` (String): User authentication method for which the rule will apply. Use `all_non_sso` to target every non-SSO authentication method with one rule.
* `group_ids` (Long[]): Array of Group IDs to which the rule applies. If empty or not set, the rule applies to all users.
* `inactivity_days` (Long): Number of days of inactivity before the rule applies
* `include_site_admins` (Boolean): If true, the rule includes Site Administrators, who always belong to the Default Workspace. Can only be enabled when `workspace_id` is `0`.
* `include_folder_admins` (Boolean): If true, the rule will apply to folder admins.
* `name` (String): User Lifecycle Rule name
* `notify_users` (Boolean): If true, users will be emailed before the rule disables or deletes them.
* `partner_tag` (String): If provided, only users belonging to Partners with this tag at the Partner level will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.
* `user_state` (String): State of the users to apply the rule to (inactive or disabled)
* `user_tag` (String): If provided, only users with this tag will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.
* `workspace_id` (Long): Workspace whose users the rule applies to. `0` means the Default Workspace. A Custom Workspace rule applies only to users who belong to that Workspace, regardless of access granted to other users.


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
parameters.put("apply_to_all_workspaces", true);
parameters.put("authentication_method", "all_non_sso");
parameters.put("group_ids", [1,2,3]);
parameters.put("inactivity_days", 12);
parameters.put("include_site_admins", true);
parameters.put("include_folder_admins", true);
parameters.put("name", "password specific rules");
parameters.put("notify_users", true);
parameters.put("partner_tag", "guest");
parameters.put("user_state", "inactive");
parameters.put("user_tag", "guest");
parameters.put("workspace_id", 0);

userLifecycleRule.update(parameters);
```

### Parameters

* `id` (Long): Required - User Lifecycle Rule ID.
* `action` (String): Action to take on inactive users (disable or delete)
* `apply_to_all_workspaces` (Boolean): If true, a Default Workspace rule also applies to users in all Custom Workspaces. Can only be enabled when `workspace_id` is `0`.
* `authentication_method` (String): User authentication method for which the rule will apply. Use `all_non_sso` to target every non-SSO authentication method with one rule.
* `group_ids` (Long[]): Array of Group IDs to which the rule applies. If empty or not set, the rule applies to all users.
* `inactivity_days` (Long): Number of days of inactivity before the rule applies
* `include_site_admins` (Boolean): If true, the rule includes Site Administrators, who always belong to the Default Workspace. Can only be enabled when `workspace_id` is `0`.
* `include_folder_admins` (Boolean): If true, the rule will apply to folder admins.
* `name` (String): User Lifecycle Rule name
* `notify_users` (Boolean): If true, users will be emailed before the rule disables or deletes them.
* `partner_tag` (String): If provided, only users belonging to Partners with this tag at the Partner level will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.
* `user_state` (String): State of the users to apply the rule to (inactive or disabled)
* `user_tag` (String): If provided, only users with this tag will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.
* `workspace_id` (Long): Workspace whose users the rule applies to. `0` means the Default Workspace. A Custom Workspace rule applies only to users who belong to that Workspace, regardless of access granted to other users.


---

## Delete User Lifecycle Rule

```
UserLifecycleRule userLifecycleRule = UserLifecycleRule.find(id);

HashMap<String, Object> parameters = new HashMap<>();

userLifecycleRule.delete(parameters);
```

### Parameters

* `id` (Long): Required - User Lifecycle Rule ID.
