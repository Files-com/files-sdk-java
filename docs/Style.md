# Files.Models.Style

## Example Style Object

```
{
  "id": 1,
  "path": "example",
  "logo": "https://mysite.files.com/...",
  "thumbnail": "example"
}
```

* `id` / `id`  (int64): Style ID
* `path` / `path`  (string): Folder path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `logo` / `logo`  (image): Logo
* `thumbnail` / `thumbnail`  (image): Logo thumbnail
* `file` / `file`  (file): Logo for custom branding.


---

## Show Style

```
List<Style> style = Style.find(
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
* `file` (byte[]): Required - Logo for custom branding.


---

## Delete Style

```
Style style = Style.delete(
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
HashMap<Object, String> attributes = new HashMap<>();
Style style = new Style(attributes);

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("file", "file");

Style.Update(parameters);
```

### Parameters

* `path` (String): Required - Style path.
* `file` (byte[]): Required - Logo for custom branding.


---

## Delete Style

```
HashMap<Object, String> attributes = new HashMap<>();
Style style = new Style(attributes);

HashMap<String, Object> parameters = new HashMap<>();


Style.Delete
```

### Parameters

* `path` (String): Required - Style path.
