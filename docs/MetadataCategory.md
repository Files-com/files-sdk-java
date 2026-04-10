# Files.Models.MetadataCategory

## Example MetadataCategory Object

```
{
  "id": 1,
  "name": "Approval Workflow",
  "definitions": {
    "Approval Status": [
      "Under Review",
      "Approved",
      "Rejected"
    ],
    "Reviewer": [

    ]
  },
  "default_columns": [
    "Approval Status"
  ]
}
```

* `id` / `id`  (int64): Metadata Category ID
* `name` / `name`  (string): Name of the metadata category.
* `definitions` / `definitions`  (hash(string,array(string))): Map of key names to arrays of allowed values. An empty array means free-form text.
* `default_columns` / `defaultColumns`  (array(string)): Metadata keys that should appear as columns in the UI by default.


---

## List Metadata Categories

```
ListIterator<MetadataCategory> metadataCategory = MetadataCategory.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are .


---

## Show Metadata Category

```
MetadataCategory metadataCategory = MetadataCategory.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Metadata Category ID.


---

## Create Metadata Category

```
MetadataCategory metadataCategory = MetadataCategory.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `name` (String): Required - Name of the metadata category.
* `default_columns` (String[]): Metadata keys that should appear as columns in the UI by default.


---

## Update Metadata Category

```
MetadataCategory metadataCategory = MetadataCategory.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Metadata Category ID.
* `name` (String): Name of the metadata category.
* `default_columns` (String[]): Metadata keys that should appear as columns in the UI by default.


---

## Delete Metadata Category

```
void metadataCategory = MetadataCategory.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Metadata Category ID.


---

## Update Metadata Category

```
MetadataCategory metadataCategory = MetadataCategory.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "Approval Workflow");
parameters.put("default_columns", ["Approval Status"]);

metadataCategory.update(parameters);
```

### Parameters

* `id` (Long): Required - Metadata Category ID.
* `name` (String): Name of the metadata category.
* `default_columns` (String[]): Metadata keys that should appear as columns in the UI by default.


---

## Delete Metadata Category

```
MetadataCategory metadataCategory = MetadataCategory.find(id);

HashMap<String, Object> parameters = new HashMap<>();

metadataCategory.delete(parameters);
```

### Parameters

* `id` (Long): Required - Metadata Category ID.
