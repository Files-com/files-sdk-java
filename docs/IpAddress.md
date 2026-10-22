# Files.Models.IpAddress

## Example IpAddress Object

```
{
  "id": "Site",
  "associated_with": "Site",
  "group_id": 1,
  "ip_addresses": [
    "127.0.0.1"
  ]
}
```

* `id` / `id`  (string): Unique label for list; used by Zapier and other integrations.
* `associated_with` / `associatedWith`  (string): The object that this public IP address list is associated with.
* `group_id` / `groupId`  (int64): Group ID
* `ip_addresses` / `ipAddresses`  (array(string)): A list of IP addresses.


---

## List IP Addresses associated with the current site

```java
HashMap<String, Object> parameters = new HashMap<>();
ListIterator<IpAddress> ipAddresses = IpAddress.list(parameters, null);
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).


---

## List all possible public SmartFile IP addresses

```java
HashMap<String, Object> parameters = new HashMap<>();
List<PublicIpAddress> publicIpAddresses = IpAddress.getSmartfileReserved(parameters, null);
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).


---

## List all possible public ExaVault IP addresses

```java
HashMap<String, Object> parameters = new HashMap<>();
List<PublicIpAddress> publicIpAddresses = IpAddress.getExavaultReserved(parameters, null);
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).


---

## List all possible public IP addresses

```java
HashMap<String, Object> parameters = new HashMap<>();
List<PublicIpAddress> publicIpAddresses = IpAddress.getReserved(parameters, null);
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
