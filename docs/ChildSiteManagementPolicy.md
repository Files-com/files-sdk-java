# Files.Models.ChildSiteManagementPolicy

## Example ChildSiteManagementPolicy Object

```
{
  "id": 1,
  "policy_type": "settings",
  "name": "example",
  "description": "example",
  "value": "{ \"color2_left\": \"#000000\" }",
  "applied_child_site_ids": [
    1,
    2
  ],
  "skip_child_site_ids": [
    1,
    2
  ],
  "created_at": "2000-01-01T01:00:00Z",
  "updated_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): Policy ID.
* `policy_type` / `policyType`  (string): Type of policy.  Valid values: `settings`.
* `name` / `name`  (string): Name for this policy.
* `description` / `description`  (string): Description for this policy.
* `value` / `value`  (object): Policy configuration data. Attributes differ by policy type. For more information, refer to the Value Hash section of the developer documentation.
* `applied_child_site_ids` / `appliedChildSiteIds`  (array(int64)): IDs of child sites that this policy has been applied to. This field is read-only.
* `skip_child_site_ids` / `skipChildSiteIds`  (array(int64)): IDs of child sites that this policy has been exempted from. If `skip_child_site_ids` is empty, the policy will be applied to all child sites. To apply a policy to a child site that has been exempted, remove it from `skip_child_site_ids` or set it to an empty array (`[]`).
* `created_at` / `createdAt`  (date-time): When this policy was created.
* `updated_at` / `updatedAt`  (date-time): When this policy was last updated.


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

* `value` (String): 
* `skip_child_site_ids` (Long[]): IDs of child sites that this policy has been exempted from. If `skip_child_site_ids` is empty, the policy will be applied to all child sites. To apply a policy to a child site that has been exempted, remove it from `skip_child_site_ids` or set it to an empty array (`[]`).
* `policy_type` (String): Required - Type of policy.  Valid values: `settings`.
* `name` (String): Name for this policy.
* `description` (String): Description for this policy.


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
* `value` (String): 
* `skip_child_site_ids` (Long[]): IDs of child sites that this policy has been exempted from. If `skip_child_site_ids` is empty, the policy will be applied to all child sites. To apply a policy to a child site that has been exempted, remove it from `skip_child_site_ids` or set it to an empty array (`[]`).
* `policy_type` (String): Type of policy.  Valid values: `settings`.
* `name` (String): Name for this policy.
* `description` (String): Description for this policy.


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
parameters.put("value", "{ \"color2_left\": \"#000000\" }");
parameters.put("skip_child_site_ids", [1,2]);
parameters.put("policy_type", "settings");
parameters.put("name", "example");
parameters.put("description", "example");

childSiteManagementPolicy.update(parameters);
```

### Parameters

* `id` (Long): Required - Child Site Management Policy ID.
* `value` (String): 
* `skip_child_site_ids` (Long[]): IDs of child sites that this policy has been exempted from. If `skip_child_site_ids` is empty, the policy will be applied to all child sites. To apply a policy to a child site that has been exempted, remove it from `skip_child_site_ids` or set it to an empty array (`[]`).
* `policy_type` (String): Type of policy.  Valid values: `settings`.
* `name` (String): Name for this policy.
* `description` (String): Description for this policy.


---

## Delete Child Site Management Policy

```
ChildSiteManagementPolicy childSiteManagementPolicy = ChildSiteManagementPolicy.find(id);

HashMap<String, Object> parameters = new HashMap<>();

childSiteManagementPolicy.delete(parameters);
```

### Parameters

* `id` (Long): Required - Child Site Management Policy ID.
