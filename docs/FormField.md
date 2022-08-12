# Files.Models.FormField

## Example FormField Object

```
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
```

* `id` / `id`  (int64): Form field id
* `label` / `label`  (string): Label to be displayed
* `required` / `required`  (boolean): Is this a required field?
* `help_text` / `helpText`  (string): Help text to be displayed
* `field_type` / `fieldType`  (string): Type of Field
* `options_for_select` / `optionsForSelect`  (array): Options to display for radio and dropdown
* `default_option` / `defaultOption`  (string): Default option for radio and dropdown
* `form_field_set_id` / `formFieldSetId`  (int64): Form field set id
