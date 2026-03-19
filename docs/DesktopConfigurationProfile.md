# Files.Models.DesktopConfigurationProfile

## Example DesktopConfigurationProfile Object

```
{
  "id": 1,
  "name": "North America Desktop Profile",
  "workspace_id": 1,
  "use_for_all_users": true,
  "mount_mappings": {
    "key": "example value"
  }
}
```

* `id` / `id`  (int64): Desktop Configuration Profile ID
* `name` / `name`  (string): Profile name
* `workspace_id` / `workspaceId`  (int64): Workspace ID
* `use_for_all_users` / `useForAllUsers`  (boolean): Whether this profile applies to all users in the Workspace by default
* `mount_mappings` / `mountMappings`  (object): Mount point mappings for the desktop app. Keys are mount points (e.g. drive letters) and values are paths in Files.com that the mount points map to.


---

## List Desktop Configuration Profiles

```
ListIterator<DesktopConfigurationProfile> desktopConfigurationProfile = DesktopConfigurationProfile.list(
    
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

## Show Desktop Configuration Profile

```
DesktopConfigurationProfile desktopConfigurationProfile = DesktopConfigurationProfile.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Desktop Configuration Profile ID.


---

## Create Desktop Configuration Profile

```
DesktopConfigurationProfile desktopConfigurationProfile = DesktopConfigurationProfile.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `name` (String): Required - Profile name
* `mount_mappings` (Object): Required - Mount point mappings for the desktop app. Keys are mount points (e.g. drive letters) and values are paths in Files.com that the mount points map to.
* `workspace_id` (Long): Workspace ID
* `use_for_all_users` (Boolean): Whether this profile applies to all users in the Workspace by default


---

## Update Desktop Configuration Profile

```
DesktopConfigurationProfile desktopConfigurationProfile = DesktopConfigurationProfile.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Desktop Configuration Profile ID.
* `name` (String): Profile name
* `workspace_id` (Long): Workspace ID
* `mount_mappings` (Object): Mount point mappings for the desktop app. Keys are mount points (e.g. drive letters) and values are paths in Files.com that the mount points map to.
* `use_for_all_users` (Boolean): Whether this profile applies to all users in the Workspace by default


---

## Delete Desktop Configuration Profile

```
void desktopConfigurationProfile = DesktopConfigurationProfile.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Desktop Configuration Profile ID.


---

## Update Desktop Configuration Profile

```
DesktopConfigurationProfile desktopConfigurationProfile = DesktopConfigurationProfile.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "North America Desktop Profile");
parameters.put("workspace_id", 1);
parameters.put("mount_mappings", {"key":"example value"});
parameters.put("use_for_all_users", false);

desktopConfigurationProfile.update(parameters);
```

### Parameters

* `id` (Long): Required - Desktop Configuration Profile ID.
* `name` (String): Profile name
* `workspace_id` (Long): Workspace ID
* `mount_mappings` (Object): Mount point mappings for the desktop app. Keys are mount points (e.g. drive letters) and values are paths in Files.com that the mount points map to.
* `use_for_all_users` (Boolean): Whether this profile applies to all users in the Workspace by default


---

## Delete Desktop Configuration Profile

```
DesktopConfigurationProfile desktopConfigurationProfile = DesktopConfigurationProfile.find(id);

HashMap<String, Object> parameters = new HashMap<>();

desktopConfigurationProfile.delete(parameters);
```

### Parameters

* `id` (Long): Required - Desktop Configuration Profile ID.
