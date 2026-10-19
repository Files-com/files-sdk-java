# Files.Models.WebhookTest

## Example WebhookTest Object

```
{
  "code": 200,
  "message": "",
  "status": "",
  "data": "example",
  "success": true
}
```

* `code` / `code`  (int64): Status HTTP code
* `message` / `message`  (string): Error message
* `status` / `status`  (string): Status message
* `data` / `data`  (auto): Additional data
* `success` / `success`  (boolean): The success status of the webhook test
* `url` / `url`  (string): URL for testing the webhook.
* `method` / `method`  (string): HTTP method(GET or POST).
* `encoding` / `encoding`  (string): HTTP encoding method.  Can be JSON, XML, or RAW (form data).
* `headers` / `headers`  (object): Additional request headers.
* `body` / `body`  (object): Additional body parameters.
* `raw_body` / `rawBody`  (string): raw body text
* `file_as_body` / `fileAsBody`  (boolean): Send the file data as the request body?
* `file_form_field` / `fileFormField`  (string): Send the file data as a named parameter in the request POST body
* `action` / `action`  (string): action for test body
* `use_dedicated_ips` / `useDedicatedIps`  (boolean): Use dedicated IPs for sending the webhook?


---

## Create Webhook Test

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("url", "https://www.site.com/...");
parameters.put("method", "GET");
parameters.put("encoding", "RAW");
HashMap<String, Object> map1 = new HashMap<>();
map1.put("x-test-header", "testvalue");
parameters.put("headers", map1);
HashMap<String, Object> map2 = new HashMap<>();
map2.put("test-param", "testvalue");
parameters.put("body", map2);
parameters.put("raw_body", "test body");
parameters.put("file_as_body", false);
parameters.put("file_form_field", "upload_file_data");
parameters.put("use_dedicated_ips", false);
WebhookTest webhookTest = WebhookTest.create(parameters, null);
```

### Parameters

* `url` (String): Required - URL for testing the webhook.
* `method` (String): HTTP method(GET or POST).
* `encoding` (String): HTTP encoding method.  Can be JSON, XML, or RAW (form data).
* `headers` (Object): Additional request headers.
* `body` (Object): Additional body parameters.
* `raw_body` (String): raw body text
* `file_as_body` (Boolean): Send the file data as the request body?
* `file_form_field` (String): Send the file data as a named parameter in the request POST body
* `action` (String): action for test body
* `use_dedicated_ips` (Boolean): Use dedicated IPs for sending the webhook?
