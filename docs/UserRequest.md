# Files.Models.UserRequest

## Example UserRequest Object

```
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@files.com",
  "details": "Changed Departments"
}
```

* `id` / `id`  (int64): ID
* `name` / `name`  (string): User's full name
* `email` / `email`  (email): User email address
* `details` / `details`  (string): Details of the user's request


---

## List User Requests

```
List<UserRequest> userRequest = UserRequest.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).


---

## Show User Request

```
List<UserRequest> userRequest = UserRequest.find(
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


---

## Delete User Request

```
UserRequest userRequest = UserRequest.delete(
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
UserRequest userRequest = UserRequest.List()[0];

HashMap<String, Object> parameters = new HashMap<>();


UserRequest.Delete
```

### Parameters

* `id` (Long): Required - User Request ID.
