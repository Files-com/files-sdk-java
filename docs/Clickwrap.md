# Files.Models.Clickwrap

## Example Clickwrap Object

```
{
  "id": 1,
  "name": "Example Site NDA for Files.com Use",
  "body": "[Legal body text]",
  "use_with_users": "example",
  "use_with_bundles": "example",
  "use_with_inboxes": "example"
}
```

* `id` / `id`  (int64): Clickwrap ID
* `name` / `name`  (string): Name of the Clickwrap agreement (used when selecting from multiple Clickwrap agreements.)
* `body` / `body`  (string): Body text of Clickwrap (supports Markdown formatting).
* `use_with_users` / `useWithUsers`  (string): Use this Clickwrap for User Registrations?  Note: This only applies to User Registrations where the User is invited to your Files.com site using an E-Mail invitation process where they then set their own password.
* `use_with_bundles` / `useWithBundles`  (string): Use this Clickwrap for Bundles?
* `use_with_inboxes` / `useWithInboxes`  (string): Use this Clickwrap for Inboxes?


---

## List Clickwraps

```
ListIterator<Clickwrap> clickwrap = Clickwrap.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).


---

## Show Clickwrap

```
ListIterator<Clickwrap> clickwrap = Clickwrap.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Clickwrap ID.


---

## Create Clickwrap

```
Clickwrap clickwrap = Clickwrap.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `name` (String): Name of the Clickwrap agreement (used when selecting from multiple Clickwrap agreements.)
* `body` (String): Body text of Clickwrap (supports Markdown formatting).
* `use_with_bundles` (String): Use this Clickwrap for Bundles?
* `use_with_inboxes` (String): Use this Clickwrap for Inboxes?
* `use_with_users` (String): Use this Clickwrap for User Registrations?  Note: This only applies to User Registrations where the User is invited to your Files.com site using an E-Mail invitation process where they then set their own password.


---

## Update Clickwrap

```
Clickwrap clickwrap = Clickwrap.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Clickwrap ID.
* `name` (String): Name of the Clickwrap agreement (used when selecting from multiple Clickwrap agreements.)
* `body` (String): Body text of Clickwrap (supports Markdown formatting).
* `use_with_bundles` (String): Use this Clickwrap for Bundles?
* `use_with_inboxes` (String): Use this Clickwrap for Inboxes?
* `use_with_users` (String): Use this Clickwrap for User Registrations?  Note: This only applies to User Registrations where the User is invited to your Files.com site using an E-Mail invitation process where they then set their own password.


---

## Delete Clickwrap

```
Clickwrap clickwrap = Clickwrap.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Clickwrap ID.


---

## Update Clickwrap

```
Clickwrap clickwrap = Clickwrap.List()[0];

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("name", "Example Site NDA for Files.com Use");
parameters.put("body", "[Legal body text]");
parameters.put("use_with_bundles", "example");
parameters.put("use_with_inboxes", "example");
parameters.put("use_with_users", "example");

Clickwrap.Update(parameters);
```

### Parameters

* `id` (Long): Required - Clickwrap ID.
* `name` (String): Name of the Clickwrap agreement (used when selecting from multiple Clickwrap agreements.)
* `body` (String): Body text of Clickwrap (supports Markdown formatting).
* `use_with_bundles` (String): Use this Clickwrap for Bundles?
* `use_with_inboxes` (String): Use this Clickwrap for Inboxes?
* `use_with_users` (String): Use this Clickwrap for User Registrations?  Note: This only applies to User Registrations where the User is invited to your Files.com site using an E-Mail invitation process where they then set their own password.


---

## Delete Clickwrap

```
Clickwrap clickwrap = Clickwrap.List()[0];

HashMap<String, Object> parameters = new HashMap<>();


Clickwrap.Delete
```

### Parameters

* `id` (Long): Required - Clickwrap ID.
