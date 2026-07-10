# Files.Models.PartnerChannelTemplate

## Example PartnerChannelTemplate Object

```
{
  "id": 1,
  "workspace_id": 1,
  "name": "Claims Template",
  "path": "claims/medical",
  "to_partner_folder_name": "outgoing",
  "from_partner_folder_name": "incoming",
  "from_partner_route_path_pattern": "processing/{{partner_name}}/from-partner",
  "to_partner_route_path_pattern": "delivery/{{partner_name}}/to-partner",
  "to_partner_managed_folder_paths": [
    "reports/monthly"
  ],
  "from_partner_managed_folder_paths": [
    "claims/received"
  ],
  "effective_to_partner_folder_name": "outgoing",
  "effective_from_partner_folder_name": "incoming"
}
```

* `id` / `id`  (int64): The unique ID of the Partner Channel Template.
* `workspace_id` / `workspaceId`  (int64): ID of the Workspace associated with this Partner Channel Template.
* `name` / `name`  (string): The name of the Partner Channel Template.
* `path` / `path`  (string): Channel path relative to the Partner root folder. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `to_partner_folder_name` / `toPartnerFolderName`  (string): Optional Channel-level to-Partner folder name override.
* `from_partner_folder_name` / `fromPartnerFolderName`  (string): Optional Channel-level from-Partner folder name override.
* `from_partner_route_path_pattern` / `fromPartnerRoutePathPattern`  (string): Optional route path pattern for files uploaded by the Partner. Supports {{partner_name}}.
* `to_partner_route_path_pattern` / `toPartnerRoutePathPattern`  (string): Optional route path pattern for files delivered to the Partner. Supports {{partner_name}}.
* `to_partner_managed_folder_paths` / `toPartnerManagedFolderPaths`  (array(string)): Managed folder paths inside the to-Partner folder.
* `from_partner_managed_folder_paths` / `fromPartnerManagedFolderPaths`  (array(string)): Managed folder paths inside the from-Partner folder.
* `effective_to_partner_folder_name` / `effectiveToPartnerFolderName`  (string): Resolved to-Partner folder name after Template override and default.
* `effective_from_partner_folder_name` / `effectiveFromPartnerFolderName`  (string): Resolved from-Partner folder name after Template override and default.


---

## List Partner Channel Templates

```
ListIterator<PartnerChannelTemplate> partnerChannelTemplate = PartnerChannelTemplate.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id` and `name`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id`.


---

## Show Partner Channel Template

```
PartnerChannelTemplate partnerChannelTemplate = PartnerChannelTemplate.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Partner Channel Template ID.


---

## Create Partner Channel Template

```
PartnerChannelTemplate partnerChannelTemplate = PartnerChannelTemplate.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `from_partner_folder_name` (String): Optional Channel-level from-Partner folder name override.
* `from_partner_managed_folder_paths` (String[]): Managed folder paths inside the from-Partner folder.
* `from_partner_route_path_pattern` (String): Optional route path pattern for files uploaded by the Partner. Supports {{partner_name}}.
* `to_partner_folder_name` (String): Optional Channel-level to-Partner folder name override.
* `to_partner_managed_folder_paths` (String[]): Managed folder paths inside the to-Partner folder.
* `to_partner_route_path_pattern` (String): Optional route path pattern for files delivered to the Partner. Supports {{partner_name}}.
* `name` (String): Required - The name of the Partner Channel Template.
* `path` (String): Required - Channel path relative to the Partner root folder.
* `workspace_id` (Long): ID of the Workspace associated with this Partner Channel Template.


---

## Update Partner Channel Template

```
PartnerChannelTemplate partnerChannelTemplate = PartnerChannelTemplate.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Partner Channel Template ID.
* `from_partner_folder_name` (String): Optional Channel-level from-Partner folder name override.
* `from_partner_managed_folder_paths` (String[]): Managed folder paths inside the from-Partner folder.
* `from_partner_route_path_pattern` (String): Optional route path pattern for files uploaded by the Partner. Supports {{partner_name}}.
* `to_partner_folder_name` (String): Optional Channel-level to-Partner folder name override.
* `to_partner_managed_folder_paths` (String[]): Managed folder paths inside the to-Partner folder.
* `to_partner_route_path_pattern` (String): Optional route path pattern for files delivered to the Partner. Supports {{partner_name}}.
* `name` (String): The name of the Partner Channel Template.
* `path` (String): Channel path relative to the Partner root folder.


---

## Delete Partner Channel Template

```
void partnerChannelTemplate = PartnerChannelTemplate.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Partner Channel Template ID.


---

## Update Partner Channel Template

```
PartnerChannelTemplate partnerChannelTemplate = PartnerChannelTemplate.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("from_partner_folder_name", "incoming");
parameters.put("from_partner_managed_folder_paths", ["claims/received"]);
parameters.put("from_partner_route_path_pattern", "processing/{{partner_name}}/from-partner");
parameters.put("to_partner_folder_name", "outgoing");
parameters.put("to_partner_managed_folder_paths", ["reports/monthly"]);
parameters.put("to_partner_route_path_pattern", "delivery/{{partner_name}}/to-partner");
parameters.put("name", "Claims Template");
parameters.put("path", "claims/medical");

partnerChannelTemplate.update(parameters);
```

### Parameters

* `id` (Long): Required - Partner Channel Template ID.
* `from_partner_folder_name` (String): Optional Channel-level from-Partner folder name override.
* `from_partner_managed_folder_paths` (String[]): Managed folder paths inside the from-Partner folder.
* `from_partner_route_path_pattern` (String): Optional route path pattern for files uploaded by the Partner. Supports {{partner_name}}.
* `to_partner_folder_name` (String): Optional Channel-level to-Partner folder name override.
* `to_partner_managed_folder_paths` (String[]): Managed folder paths inside the to-Partner folder.
* `to_partner_route_path_pattern` (String): Optional route path pattern for files delivered to the Partner. Supports {{partner_name}}.
* `name` (String): The name of the Partner Channel Template.
* `path` (String): Channel path relative to the Partner root folder.


---

## Delete Partner Channel Template

```
PartnerChannelTemplate partnerChannelTemplate = PartnerChannelTemplate.find(id);

HashMap<String, Object> parameters = new HashMap<>();

partnerChannelTemplate.delete(parameters);
```

### Parameters

* `id` (Long): Required - Partner Channel Template ID.
