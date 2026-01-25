# Files.Models.ZipListEntry

## Example ZipListEntry Object

```
{
  "path": "example",
  "size": 1
}
```

* `path` / `path`  (string): Entry path inside the ZIP. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `size` / `size`  (int64): Uncompressed size in bytes.
