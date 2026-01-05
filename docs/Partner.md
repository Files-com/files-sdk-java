# Files.Models.Partner

## Example Partner Object

```
{
  "allow_bypassing_2fa_policies": true,
  "allow_credential_changes": true,
  "allow_providing_gpg_keys": true,
  "allow_user_creation": true,
  "id": 1,
  "workspace_id": 1,
  "name": "Acme Corp",
  "notes": "This is a note about the partner.",
  "partner_admin_ids": [
    1,
    2,
    3
  ],
  "root_folder": "/AcmeCorp",
  "tags": "example",
  "user_ids": [
    1,
    2,
    3
  ]
}
```

* `allow_bypassing_2fa_policies` / `allowBypassing2faPolicies`  (boolean): Allow Partner Admins to change Two-Factor Authentication requirements for Partner Users.
* `allow_credential_changes` / `allowCredentialChanges`  (boolean): Allow Partner Admins to change or reset credentials for users belonging to this Partner.
* `allow_providing_gpg_keys` / `allowProvidingGpgKeys`  (boolean): Allow Partner Admins to provide GPG keys.
* `allow_user_creation` / `allowUserCreation`  (boolean): Allow Partner Admins to create users.
* `id` / `id`  (int64): The unique ID of the Partner.
* `workspace_id` / `workspaceId`  (int64): ID of the Workspace associated with this Partner.
* `name` / `name`  (string): The name of the Partner.
* `notes` / `notes`  (string): Notes about this Partner.
* `partner_admin_ids` / `partnerAdminIds`  (array(int64)): Array of User IDs that are Partner Admins for this Partner.
* `root_folder` / `rootFolder`  (string): The root folder path for this Partner.
* `tags` / `tags`  (string): Comma-separated list of Tags for this Partner. Tags are used for other features, such as UserLifecycleRules, which can target specific tags.  Tags must only contain lowercase letters, numbers, and hyphens.
* `user_ids` / `userIds`  (array(int64)): Array of User IDs that belong to this Partner.


---

## List Partners

```
ListIterator<Partner> partner = Partner.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id` and `name`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id`.


---

## Show Partner

```
Partner partner = Partner.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Partner ID.


---

## Create Partner

```
Partner partner = Partner.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `allow_bypassing_2fa_policies` (Boolean): Allow Partner Admins to change Two-Factor Authentication requirements for Partner Users.
* `allow_credential_changes` (Boolean): Allow Partner Admins to change or reset credentials for users belonging to this Partner.
* `allow_providing_gpg_keys` (Boolean): Allow Partner Admins to provide GPG keys.
* `allow_user_creation` (Boolean): Allow Partner Admins to create users.
* `notes` (String): Notes about this Partner.
* `root_folder` (String): The root folder path for this Partner.
* `tags` (String): Comma-separated list of Tags for this Partner. Tags are used for other features, such as UserLifecycleRules, which can target specific tags.  Tags must only contain lowercase letters, numbers, and hyphens.
* `name` (String): Required - The name of the Partner.
* `workspace_id` (Long): ID of the Workspace associated with this Partner.


---

## Update Partner

```
Partner partner = Partner.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Partner ID.
* `allow_bypassing_2fa_policies` (Boolean): Allow Partner Admins to change Two-Factor Authentication requirements for Partner Users.
* `allow_credential_changes` (Boolean): Allow Partner Admins to change or reset credentials for users belonging to this Partner.
* `allow_providing_gpg_keys` (Boolean): Allow Partner Admins to provide GPG keys.
* `allow_user_creation` (Boolean): Allow Partner Admins to create users.
* `notes` (String): Notes about this Partner.
* `root_folder` (String): The root folder path for this Partner.
* `tags` (String): Comma-separated list of Tags for this Partner. Tags are used for other features, such as UserLifecycleRules, which can target specific tags.  Tags must only contain lowercase letters, numbers, and hyphens.
* `name` (String): The name of the Partner.


---

## Delete Partner

```
void partner = Partner.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Partner ID.


---

## Update Partner

```
Partner partner = Partner.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("allow_bypassing_2fa_policies", false);
parameters.put("allow_credential_changes", false);
parameters.put("allow_providing_gpg_keys", false);
parameters.put("allow_user_creation", false);
parameters.put("notes", "This is a note about the partner.");
parameters.put("root_folder", "/AcmeCorp");
parameters.put("tags", "example");
parameters.put("name", "Acme Corp");

partner.update(parameters);
```

### Parameters

* `id` (Long): Required - Partner ID.
* `allow_bypassing_2fa_policies` (Boolean): Allow Partner Admins to change Two-Factor Authentication requirements for Partner Users.
* `allow_credential_changes` (Boolean): Allow Partner Admins to change or reset credentials for users belonging to this Partner.
* `allow_providing_gpg_keys` (Boolean): Allow Partner Admins to provide GPG keys.
* `allow_user_creation` (Boolean): Allow Partner Admins to create users.
* `notes` (String): Notes about this Partner.
* `root_folder` (String): The root folder path for this Partner.
* `tags` (String): Comma-separated list of Tags for this Partner. Tags are used for other features, such as UserLifecycleRules, which can target specific tags.  Tags must only contain lowercase letters, numbers, and hyphens.
* `name` (String): The name of the Partner.


---

## Delete Partner

```
Partner partner = Partner.find(id);

HashMap<String, Object> parameters = new HashMap<>();

partner.delete(parameters);
```

### Parameters

* `id` (Long): Required - Partner ID.
