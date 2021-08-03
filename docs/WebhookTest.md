# Files.Models.WebhookTest

## Example WebhookTest Object

```
{
  "code": 200,
  "message": "",
  "status": "",
  "data": "",
  "success": true
}
```

* `code` / `code`  (int64): Status HTTP code
* `message` / `message`  (string): Error message
* `status` / `status`  (string): Status message
* `data` / `data` : Additional data
* `success` / `success`  (boolean): The success status of the webhook test
* `url` / `url`  (string): URL for testing the webhook.
* `method` / `method`  (string): HTTP method(GET or POST).
* `encoding` / `encoding`  (string): HTTP encoding method.  Can be JSON, XML, or RAW (form data).
* `headers` / `headers`  (object): Additional request headers.
* `body` / `body`  (object): Additional body parameters.
* `raw_body` / `rawBody`  (string): raw body text
* `action` / `action`  (string): action for test body


---

## Create Webhook Test

```
WebhookTest webhookTest = WebhookTest.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `url` (String): Required - URL for testing the webhook.
* `method` (String): HTTP method(GET or POST).
* `encoding` (String): HTTP encoding method.  Can be JSON, XML, or RAW (form data).
* `headers` (Map<String, String>): Additional request headers.
* `body` (Map<String, String>): Additional body parameters.
* `raw_body` (String): raw body text
* `action` (String): action for test body
