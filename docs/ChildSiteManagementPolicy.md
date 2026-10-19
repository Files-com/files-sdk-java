# Files.Models.ChildSiteManagementPolicy

## Example ChildSiteManagementPolicy Object

```
{
  "id": 1,
  "policy_type": "settings",
  "name": "example",
  "description": "example",
  "value": "{ \"color2_left\": \"#000000\", \"folder_behaviors\": [{ \"behavior\": \"file_expiration\", \"value\": { \"days_to_retain\": 30 } }] }",
  "applied_child_site_ids": [
    1,
    2
  ],
  "skip_child_site_ids": [
    1,
    2
  ],
  "child_site_ids": [
    1,
    2
  ],
  "default_policy": true,
  "created_at": "2000-01-01T01:00:00Z",
  "updated_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): Policy ID.
* `policy_type` / `policyType`  (string): Type of policy.  Valid values: `settings`.
* `name` / `name`  (string): Name for this policy.
* `description` / `description`  (string): Description for this policy.
* `value` / `value`  (object): Policy configuration data. On a Child Site, contains only managed setting names with null values. Settings policies accept site settings plus an optional `folder_behaviors` array for parent-managed root behaviors on child sites. For more information, refer to the Value Hash section of the developer documentation.
* `applied_child_site_ids` / `appliedChildSiteIds`  (array(int64)): IDs of child sites that this policy has been applied to. This field is read-only.
* `skip_child_site_ids` / `skipChildSiteIds`  (array(int64)): IDs of child sites excluded from this default policy.
* `child_site_ids` / `childSiteIds`  (array(int64)): IDs of child sites explicitly assigned to this non-default policy.
* `default_policy` / `defaultPolicy`  (boolean): Whether this policy applies to child sites not explicitly assigned to another policy.
* `created_at` / `createdAt`  (date-time): When this policy was created.
* `updated_at` / `updatedAt`  (date-time): When this policy was last updated.


---

## List Child Site Management Policies. On a Child Site, returns only the policy covering that site. Its value contains managed setting names with null values

```java
HashMap<String, Object> parameters = new HashMap<>();
ListIterator<ChildSiteManagementPolicy> childSiteManagementPolicies = ChildSiteManagementPolicy.list(parameters, null);
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).


---

## Show Child Site Management Policy

```java
HashMap<String, Object> parameters = new HashMap<>();
ChildSiteManagementPolicy childSiteManagementPolicy = ChildSiteManagementPolicy.find(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Child Site Management Policy ID.


---

## Create Child Site Management Policy

```java
HashMap<String, Object> parameters = new HashMap<>();
HashMap<String, Object> map1 = new HashMap<>();
map1.put("color2_left", "#000000");
parameters.put("value", map1);
parameters.put("skip_child_site_ids", new Long[] {1L, 2L});
parameters.put("child_site_ids", new Long[] {1L, 2L});
parameters.put("default_policy", true);
parameters.put("policy_type", "settings");
parameters.put("name", "example");
parameters.put("description", "example");
ChildSiteManagementPolicy childSiteManagementPolicy = ChildSiteManagementPolicy.create(parameters, null);
```

### Parameters

* `value` (Object): Policy configuration data. Attributes differ by policy type. For more information, refer to the Value Hash section of the developer documentation.
* `skip_child_site_ids` (Long[]): IDs of child sites excluded from this default policy.
* `child_site_ids` (Long[]): IDs of child sites explicitly assigned to this non-default policy.
* `default_policy` (Boolean): Whether this policy applies to child sites not explicitly assigned to another policy.
* `policy_type` (String): Required - Type of policy.  Valid values: `settings`.
* `name` (String): Name for this policy.
* `description` (String): Description for this policy.


---

## Update Child Site Management Policy

```java
HashMap<String, Object> parameters = new HashMap<>();
HashMap<String, Object> map1 = new HashMap<>();
map1.put("color2_left", "#000000");
parameters.put("value", map1);
parameters.put("skip_child_site_ids", new Long[] {1L, 2L});
parameters.put("child_site_ids", new Long[] {1L, 2L});
parameters.put("default_policy", true);
parameters.put("policy_type", "settings");
parameters.put("name", "example");
parameters.put("description", "example");
ChildSiteManagementPolicy childSiteManagementPolicy = ChildSiteManagementPolicy.update(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Child Site Management Policy ID.
* `value` (Object): Policy configuration data. Attributes differ by policy type. For more information, refer to the Value Hash section of the developer documentation.
* `skip_child_site_ids` (Long[]): IDs of child sites excluded from this default policy.
* `child_site_ids` (Long[]): IDs of child sites explicitly assigned to this non-default policy.
* `default_policy` (Boolean): Whether this policy applies to child sites not explicitly assigned to another policy.
* `policy_type` (String): Type of policy.  Valid values: `settings`.
* `name` (String): Name for this policy.
* `description` (String): Description for this policy.


---

## Delete Child Site Management Policy

```java
HashMap<String, Object> parameters = new HashMap<>();
ChildSiteManagementPolicy.delete(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Child Site Management Policy ID.


---

## Update Child Site Management Policy

```java
ChildSiteManagementPolicy childSiteManagementPolicy = ChildSiteManagementPolicy.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
HashMap<String, Object> map1 = new HashMap<>();
map1.put("color2_left", "#000000");
parameters.put("value", map1);
parameters.put("skip_child_site_ids", new Long[] {1L, 2L});
parameters.put("child_site_ids", new Long[] {1L, 2L});
parameters.put("default_policy", true);
parameters.put("policy_type", "settings");
parameters.put("name", "example");
parameters.put("description", "example");
ChildSiteManagementPolicy result = childSiteManagementPolicy.update(parameters);
```

### Parameters

* `id` (Long): Required - Child Site Management Policy ID.
* `value` (Object): Policy configuration data. Attributes differ by policy type. For more information, refer to the Value Hash section of the developer documentation.
* `skip_child_site_ids` (Long[]): IDs of child sites excluded from this default policy.
* `child_site_ids` (Long[]): IDs of child sites explicitly assigned to this non-default policy.
* `default_policy` (Boolean): Whether this policy applies to child sites not explicitly assigned to another policy.
* `policy_type` (String): Type of policy.  Valid values: `settings`.
* `name` (String): Name for this policy.
* `description` (String): Description for this policy.


---

## Delete Child Site Management Policy

```java
ChildSiteManagementPolicy childSiteManagementPolicy = ChildSiteManagementPolicy.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
childSiteManagementPolicy.delete(parameters);
```

### Parameters

* `id` (Long): Required - Child Site Management Policy ID.
