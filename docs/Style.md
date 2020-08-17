# Files.Models.Style

## Example Style Object

```
{
  "id": 1,
  "path": "",
  "logo": "",
  "thumbnail": ""
}
```

* `id` / `id`  (int64): Style ID
* `path` / `path`  (string): Folder path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `logo` / `logo` : Logo
* `thumbnail` / `thumbnail` : Logo thumbnail
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
Style style = Style.ListFor(path)[0];

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
Style style = Style.ListFor(path)[0];

HashMap<String, Object> parameters = new HashMap<>();


Style.Delete
```

### Parameters

* `path` (String): Required - Style path.
