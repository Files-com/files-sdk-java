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

```
Style style = Style.find(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `path` (String): Required - Style path.


---

## Update Style

```
Style style = Style.update(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `path` (String): Required - Style path.
* `file` (byte[]): Logo for custom branding. Required when creating a new style.
* `logo_click_href` (String): URL to open when a public visitor clicks the logo.


---

## Delete Style

```
void style = Style.delete(
    String path, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `path` (String): Required - Style path.


---

## Update Style

```
Style style = Style.find(path);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("logo_click_href", "https://www.example.com");

style.update(parameters);
```

### Parameters

* `path` (String): Required - Style path.
* `file` (byte[]): Logo for custom branding. Required when creating a new style.
* `logo_click_href` (String): URL to open when a public visitor clicks the logo.


---

## Delete Style

```
Style style = Style.find(path);

HashMap<String, Object> parameters = new HashMap<>();

style.delete(parameters);
```

### Parameters

* `path` (String): Required - Style path.
