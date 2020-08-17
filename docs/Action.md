# Files.Models.Action

## Example Action Object

```
{
  "id": 1,
  "path": "",
  "when": "2000-01-01T01:00:00Z",
  "destination": "/to_path",
  "display": "Actual text of the action here.",
  "ip": "192.283.128.182",
  "source": "/from_path",
  "targets": [

  ],
  "user_id": 1,
  "username": "user",
  "action": "create",
  "failure_type": "none",
  "interface": "web"
}
```

* `id` / `id`  (int64): Action ID
* `path` / `path`  (string): Path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `when` / `when`  (date-time): Action occurrence date/time
* `destination` / `destination`  (string): The destination path for this action, if applicable
* `display` / `display`  (string): Friendly displayed output
* `ip` / `ip`  (string): IP Address that performed this action
* `source` / `source`  (string): The source path for this action, if applicable
* `targets` / `targets`  (array): Targets
* `user_id` / `userId`  (int64): User ID
* `username` / `username`  (string): Username
* `action` / `action`  (string): Type of action
* `failure_type` / `failureType`  (string): Failure type.  If action was a user login or session failure, why did it fail?
* `interface` / `interfaceName`  (string): Interface on which this action occurred.
