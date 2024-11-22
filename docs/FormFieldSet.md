# Files.Models.FormFieldSet

## Example FormFieldSet Object

```
{
  "id": 1,
  "title": "Sample Form Title",
  "form_layout": [
    1,
    2,
    3,
    4
  ],
  "form_fields": [
    {
      "id": 1,
      "label": "Sample Label",
      "required": true,
      "help_text": "Help Text",
      "field_type": "text",
      "options_for_select": [
        "red",
        "green",
        "blue"
      ],
      "default_option": "red",
      "form_field_set_id": 1
    }
  ],
  "skip_name": true,
  "skip_email": true,
  "skip_company": true
}
```

* `id` / `id`  (int64): Form field set id
* `title` / `title`  (string): Title to be displayed
* `form_layout` / `formLayout`  (array(int64)): Layout of the form
* `form_fields` / `formFields`  (array(object)): Associated form fields
* `skip_name` / `skipName`  (boolean): Any associated InboxRegistrations or BundleRegistrations can be saved without providing name
* `skip_email` / `skipEmail`  (boolean): Any associated InboxRegistrations or BundleRegistrations can be saved without providing email
* `skip_company` / `skipCompany`  (boolean): Any associated InboxRegistrations or BundleRegistrations can be saved without providing company
* `user_id` / `userId`  (int64): User ID.  Provide a value of `0` to operate the current session's user.


---

## List Form Field Sets

```
ListIterator<FormFieldSet> formFieldSet = FormFieldSet.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).


---

## Show Form Field Set

```
FormFieldSet formFieldSet = FormFieldSet.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Form Field Set ID.


---

## Create Form Field Set

```
FormFieldSet formFieldSet = FormFieldSet.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `user_id` (Long): User ID.  Provide a value of `0` to operate the current session's user.
* `title` (String): Title to be displayed
* `skip_email` (Boolean): Skip validating form email
* `skip_name` (Boolean): Skip validating form name
* `skip_company` (Boolean): Skip validating company
* `form_fields` (Object[]): 


---

## Update Form Field Set

```
FormFieldSet formFieldSet = FormFieldSet.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Form Field Set ID.
* `title` (String): Title to be displayed
* `skip_email` (Boolean): Skip validating form email
* `skip_name` (Boolean): Skip validating form name
* `skip_company` (Boolean): Skip validating company
* `form_fields` (Object[]): 


---

## Delete Form Field Set

```
void formFieldSet = FormFieldSet.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Form Field Set ID.


---

## Update Form Field Set

```
FormFieldSet formFieldSet = FormFieldSet.Find(id);

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("title", "Sample Form Title");
parameters.put("skip_email", true);
parameters.put("skip_name", true);
parameters.put("skip_company", true);
parameters.put("form_fields", [{"id":1,"label":"Sample Label","required":true,"help_text":"Help Text","field_type":"text","options_for_select":["red","green","blue"],"default_option":"red","form_field_set_id":1}]);

FormFieldSet.Update(parameters);
```

### Parameters

* `id` (Long): Required - Form Field Set ID.
* `title` (String): Title to be displayed
* `skip_email` (Boolean): Skip validating form email
* `skip_name` (Boolean): Skip validating form name
* `skip_company` (Boolean): Skip validating company
* `form_fields` (Object[]): 


---

## Delete Form Field Set

```
FormFieldSet formFieldSet = FormFieldSet.Find(id);

HashMap<String, Object> parameters = new HashMap<>();


FormFieldSet.Delete
```

### Parameters

* `id` (Long): Required - Form Field Set ID.
