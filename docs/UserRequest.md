# Files.Models.UserRequest

## Example UserRequest Object

```
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@files.com",
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

```java
HashMap<String, Object> parameters = new HashMap<>();
ListIterator<UserRequest> userRequests = UserRequest.list(parameters, null);
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).


---

## Show User Request

```java
HashMap<String, Object> parameters = new HashMap<>();
UserRequest userRequest = UserRequest.find(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - User Request ID.


---

## Create User Request

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "name");
parameters.put("email", "email");
parameters.put("details", "details");
parameters.put("company", "Acme Inc.");
UserRequest userRequest = UserRequest.create(parameters, null);
```

### Parameters

* `name` (String): Required - Name of user requested
* `email` (String): Required - Email of user requested
* `details` (String): Required - Details of the user request
* `company` (String): Company of the user requested


---

## Delete User Request

```java
HashMap<String, Object> parameters = new HashMap<>();
UserRequest.delete(1L, parameters, null);
```

### Parameters

* `id` (Long): Required - User Request ID.


---

## Delete User Request

```java
UserRequest userRequest = UserRequest.find(1L, null, null);
HashMap<String, Object> parameters = new HashMap<>();
userRequest.delete(parameters);
```

### Parameters

* `id` (Long): Required - User Request ID.
