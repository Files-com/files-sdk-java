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

```java
HashMap<String, Object> parameters = new HashMap<>();
ListIterator<MetadataCategory> metadataCategories = MetadataCategory.list(parameters, null);
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are .


---

## Show Metadata Category

```java
HashMap<String, Object> parameters = new HashMap<>();
MetadataCategory metadataCategory = MetadataCategory.find(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Metadata Category ID.


---

## List Metadata Categories by Path

```java
HashMap<String, Object> parameters = new HashMap<>();
ListIterator<MetadataCategory> metadataCategories = MetadataCategory.listFor("path", parameters, null);
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `path` (String): Required - Path to operate on.


---

## Create Metadata Category

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "Approval Workflow");
parameters.put("default_columns", new String[] {"Approval Status"});
MetadataCategory metadataCategory = MetadataCategory.create(parameters, null);
```

### Parameters

* `name` (String): Required - Name of the metadata category.
* `default_columns` (String[]): Metadata keys that should appear as columns in the UI by default.


---

## Update Metadata Category

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "Approval Workflow");
parameters.put("default_columns", new String[] {"Approval Status"});
MetadataCategory metadataCategory = MetadataCategory.update(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Metadata Category ID.
* `name` (String): Name of the metadata category.
* `default_columns` (String[]): Metadata keys that should appear as columns in the UI by default.


---

## Delete Metadata Category

```java
HashMap<String, Object> parameters = new HashMap<>();
MetadataCategory.delete(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - Metadata Category ID.


---

## Update Metadata Category

```java
MetadataCategory metadataCategory = MetadataCategory.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "Approval Workflow");
parameters.put("default_columns", new String[] {"Approval Status"});
MetadataCategory result = metadataCategory.update(parameters);
```

### Parameters

* `id` (Long): Required - Metadata Category ID.
* `name` (String): Name of the metadata category.
* `default_columns` (String[]): Metadata keys that should appear as columns in the UI by default.


---

## Delete Metadata Category

```java
MetadataCategory metadataCategory = MetadataCategory.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
metadataCategory.delete(parameters);
```

### Parameters

* `id` (Long): Required - Metadata Category ID.
