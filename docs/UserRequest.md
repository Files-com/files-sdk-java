# Files.Models.UserRequest

## Example UserRequest Object

```
{
  "id": 1,
  "name": "John Doe",
  "email": "example",
  "details": "Changed Departments",
  "company": "Acme Inc."
}
```

* `id` / `id`  (int64): ID
* `name` / `name`  (string): User's full name
* `email` / `email`  (email): User email address
* `details` / `details`  (string): Details of the user's request
* `company` / `company`  (string): User's company name


---

## List User Requests

```
ListIterator<UserRequest> userRequest = UserRequest.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).


---

## Show User Request

```
UserRequest userRequest = UserRequest.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - User Request ID.


---

## Create User Request

```
UserRequest userRequest = UserRequest.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `name` (String): Required - Name of user requested
* `email` (String): Required - Email of user requested
* `details` (String): Required - Details of the user request
* `company` (String): Company of the user requested


---

## Create an export CSV of User Request resources

```
Export userRequest = UserRequest.createExport(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```


---

## Delete User Request

```
void userRequest = UserRequest.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - User Request ID.


---

## Delete User Request

```
UserRequest userRequest = UserRequest.Find(id);

HashMap<String, Object> parameters = new HashMap<>();


UserRequest.Delete
```

### Parameters

* `id` (Long): Required - User Request ID.
