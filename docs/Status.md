# Files.Models.Status

## Example Status Object

```
{
  "code": 200,
  "message": "",
  "status": "",
  "data": "",
  "errors": [
    {
      "fields": [

      ],
      "messages": [

      ]
    }
  ],
  "clickwrap_id": 1,
  "clickwrap_body": ""
}
```

* `code` / `code`  (int64): Status HTTP code
* `message` / `message`  (string): Error message
* `status` / `status`  (string): Status message
* `data` / `data`  (auto): Additional data
* `errors` / `errors`  (array): A list of api errors
* `clickwrap_id` / `clickwrapId`  (int64): Required Clickwrap id
* `clickwrap_body` / `clickwrapBody`  (string): Required Clickwrap body
