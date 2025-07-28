# Files.Models.ChildSiteManagementPolicy

## Example ChildSiteManagementPolicy Object

```
{
  "id": 1,
  "site_id": 1,
  "site_setting_name": "color2_left",
  "managed_value": "#FF0000",
  "skip_child_site_ids": [
    1,
    5
  ]
}
```

* `id` / `id`  (int64): ChildSiteManagementPolicy ID
* `site_id` / `siteId`  (int64): ID of the Site managing the policy
* `site_setting_name` / `siteSettingName`  (string): The name of the setting that is managed by the policy
* `managed_value` / `managedValue`  (string): The value for the setting that will be enforced for all child sites that are not exempt
* `skip_child_site_ids` / `skipChildSiteIds`  (array(int64)): The list of child site IDs that are exempt from this policy


---

## List Child Site Management Policies

```
ListIterator<ChildSiteManagementPolicy> childSiteManagementPolicy = ChildSiteManagementPolicy.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).


---

## Show Child Site Management Policy

```
ChildSiteManagementPolicy childSiteManagementPolicy = ChildSiteManagementPolicy.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Child Site Management Policy ID.


---

## Create Child Site Management Policy

```
ChildSiteManagementPolicy childSiteManagementPolicy = ChildSiteManagementPolicy.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `site_setting_name` (String): Required - The name of the setting that is managed by the policy
* `managed_value` (String): Required - The value for the setting that will be enforced for all child sites that are not exempt
* `skip_child_site_ids` (Long[]): The list of child site IDs that are exempt from this policy


---

## Update Child Site Management Policy

```
ChildSiteManagementPolicy childSiteManagementPolicy = ChildSiteManagementPolicy.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Child Site Management Policy ID.
* `site_setting_name` (String): Required - The name of the setting that is managed by the policy
* `managed_value` (String): Required - The value for the setting that will be enforced for all child sites that are not exempt
* `skip_child_site_ids` (Long[]): The list of child site IDs that are exempt from this policy


---

## Delete Child Site Management Policy

```
void childSiteManagementPolicy = ChildSiteManagementPolicy.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Child Site Management Policy ID.


---

## Update Child Site Management Policy

```
ChildSiteManagementPolicy childSiteManagementPolicy = ChildSiteManagementPolicy.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("site_setting_name", "color2_left");
parameters.put("managed_value", "#FF0000");
parameters.put("skip_child_site_ids", [1,5]);

childSiteManagementPolicy.update(parameters);
```

### Parameters

* `id` (Long): Required - Child Site Management Policy ID.
* `site_setting_name` (String): Required - The name of the setting that is managed by the policy
* `managed_value` (String): Required - The value for the setting that will be enforced for all child sites that are not exempt
* `skip_child_site_ids` (Long[]): The list of child site IDs that are exempt from this policy


---

## Delete Child Site Management Policy

```
ChildSiteManagementPolicy childSiteManagementPolicy = ChildSiteManagementPolicy.find(id);

HashMap<String, Object> parameters = new HashMap<>();

childSiteManagementPolicy.delete(parameters);
```

### Parameters

* `id` (Long): Required - Child Site Management Policy ID.
