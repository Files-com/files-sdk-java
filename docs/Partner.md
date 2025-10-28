# Files.Models.Partner

## Example Partner Object

```
{
  "allow_bypassing_2fa_policies": true,
  "allow_credential_changes": true,
  "allow_providing_gpg_keys": true,
  "allow_user_creation": true,
  "id": 1,
  "name": "Acme Corp",
  "notes": "This is a note about the partner.",
  "root_folder": "/AcmeCorp",
  "tags": "example"
}
```

* `allow_bypassing_2fa_policies` / `allowBypassing2faPolicies`  (boolean): Allow users created under this Partner to bypass Two-Factor Authentication policies.
* `allow_credential_changes` / `allowCredentialChanges`  (boolean): Allow Partner Admins to change or reset credentials for users belonging to this Partner.
* `allow_providing_gpg_keys` / `allowProvidingGpgKeys`  (boolean): Allow Partner Admins to provide GPG keys.
* `allow_user_creation` / `allowUserCreation`  (boolean): Allow Partner Admins to create users.
* `id` / `id`  (int64): The unique ID of the Partner.
* `name` / `name`  (string): The name of the Partner.
* `notes` / `notes`  (string): Notes about this Partner.
* `root_folder` / `rootFolder`  (string): The root folder path for this Partner.
* `tags` / `tags`  (string): Comma-separated list of Tags for this Partner. Tags are used for other features, such as UserLifecycleRules, which can target specific tags.  Tags must only contain lowercase letters, numbers, and hyphens.


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
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `name`.


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

* `name` (String): The name of the Partner.
* `allow_bypassing_2fa_policies` (Boolean): Allow users created under this Partner to bypass Two-Factor Authentication policies.
* `allow_credential_changes` (Boolean): Allow Partner Admins to change or reset credentials for users belonging to this Partner.
* `allow_providing_gpg_keys` (Boolean): Allow Partner Admins to provide GPG keys.
* `allow_user_creation` (Boolean): Allow Partner Admins to create users.
* `notes` (String): Notes about this Partner.
* `root_folder` (String): The root folder path for this Partner.
* `tags` (String): Comma-separated list of Tags for this Partner. Tags are used for other features, such as UserLifecycleRules, which can target specific tags.  Tags must only contain lowercase letters, numbers, and hyphens.


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
* `name` (String): The name of the Partner.
* `allow_bypassing_2fa_policies` (Boolean): Allow users created under this Partner to bypass Two-Factor Authentication policies.
* `allow_credential_changes` (Boolean): Allow Partner Admins to change or reset credentials for users belonging to this Partner.
* `allow_providing_gpg_keys` (Boolean): Allow Partner Admins to provide GPG keys.
* `allow_user_creation` (Boolean): Allow Partner Admins to create users.
* `notes` (String): Notes about this Partner.
* `root_folder` (String): The root folder path for this Partner.
* `tags` (String): Comma-separated list of Tags for this Partner. Tags are used for other features, such as UserLifecycleRules, which can target specific tags.  Tags must only contain lowercase letters, numbers, and hyphens.


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
parameters.put("name", "Acme Corp");
parameters.put("allow_bypassing_2fa_policies", false);
parameters.put("allow_credential_changes", false);
parameters.put("allow_providing_gpg_keys", false);
parameters.put("allow_user_creation", false);
parameters.put("notes", "This is a note about the partner.");
parameters.put("root_folder", "/AcmeCorp");
parameters.put("tags", "example");

partner.update(parameters);
```

### Parameters

* `id` (Long): Required - Partner ID.
* `name` (String): The name of the Partner.
* `allow_bypassing_2fa_policies` (Boolean): Allow users created under this Partner to bypass Two-Factor Authentication policies.
* `allow_credential_changes` (Boolean): Allow Partner Admins to change or reset credentials for users belonging to this Partner.
* `allow_providing_gpg_keys` (Boolean): Allow Partner Admins to provide GPG keys.
* `allow_user_creation` (Boolean): Allow Partner Admins to create users.
* `notes` (String): Notes about this Partner.
* `root_folder` (String): The root folder path for this Partner.
* `tags` (String): Comma-separated list of Tags for this Partner. Tags are used for other features, such as UserLifecycleRules, which can target specific tags.  Tags must only contain lowercase letters, numbers, and hyphens.


---

## Delete Partner

```
Partner partner = Partner.find(id);

HashMap<String, Object> parameters = new HashMap<>();

partner.delete(parameters);
```

### Parameters

* `id` (Long): Required - Partner ID.
