# Files.Models.AutomationApplicationAction

## Example AutomationApplicationAction Object

```
{
  "application": {
    "key": "example",
    "name": "example",
    "brand": "example"
  },
  "key": "example",
  "name": "example",
  "description": "example",
  "fingerprint": "example",
  "fields": [
    "example"
  ],
  "output": "example",
  "origins": [
    "example"
  ],
  "authentication": [
    "example"
  ]
}
```

* `application` / `application`  (automationApplication): Application that provides the action.
* `key` / `key`  (string): Stable action key within the application.
* `name` / `name`  (string): Action display name.
* `description` / `description`  (string): What the action does.
* `fingerprint` / `fingerprint`  (string): Fingerprint of the action contract shown to the author.
* `fields` / `fields`  (array(object)): Normalized input fields.
* `output` / `output`  (object): Normalized output schema.
* `origins` / `origins`  (array(string)): HTTPS origins supported by this action.
* `authentication` / `authentication`  (array(object)): Authentication types accepted by this action.
