# Files.Models.Style

## Example Style Object

```
{
  "id": 1,
  "path": "example",
  "logo": "https://mysite.files.com/...",
  "logo_click_href": "https://www.example.com",
  "thumbnail": {
    "name": "My logo",
    "uri": "https://mysite.files.com/.../my_image.png"
  }
}
```

* `id` / `id`  (int64): Style ID
* `path` / `path`  (string): Folder path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `logo` / `logo`  (image): Logo
* `logo_click_href` / `logoClickHref`  (string): URL to open when a public visitor clicks the logo
* `thumbnail` / `thumbnail`  (image): Logo thumbnail
* `file` / `file`  (file): Logo for custom branding. Required when creating a new style.


---

## Show Style

```java
HashMap<String, Object> parameters = new HashMap<>();
Style style = Style.find("path", parameters, null);
```

### Parameters

* `path` (String): Required - Style path.


---

## Update Style

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("logo_click_href", "https://www.example.com");
Style style = Style.update("path", parameters, null);
```

### Parameters

* `path` (String): Required - Style path.
* `file` (byte[] or Attachment): Logo for custom branding. Required when creating a new style.
* `logo_click_href` (String): URL to open when a public visitor clicks the logo.


---

## Delete Style

```java
HashMap<String, Object> parameters = new HashMap<>();
Style.delete("path", parameters, null);
```

### Parameters

* `path` (String): Required - Style path.


---

## Update Style

```java
Style style = Style.find("path", null, null);
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("logo_click_href", "https://www.example.com");
Style result = style.update(parameters);
```

### Parameters

* `path` (String): Required - Style path.
* `file` (byte[] or Attachment): Logo for custom branding. Required when creating a new style.
* `logo_click_href` (String): URL to open when a public visitor clicks the logo.


---

## Delete Style

```java
Style style = Style.find("path", null, null);
HashMap<String, Object> parameters = new HashMap<>();
style.delete(parameters);
```

### Parameters

* `path` (String): Required - Style path.
