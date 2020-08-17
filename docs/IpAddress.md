# Files.Models.IpAddress

## Example IpAddress Object

```
{
  "id": "Site",
  "associated_with": "Site",
  "group_id": 1,
  "ip_addresses": [

  ]
}
```

* `id` / `id`  (string): Unique label for list; used by Zapier and other integrations.
* `associated_with` / `associatedWith`  (string): The object that this public IP address list is associated with.
* `group_id` / `groupId`  (int64): Group ID
* `ip_addresses` / `ipAddresses`  (array): A list of IP addresses.


---

## List IP Addresses associated with the current site

```
List<IpAddress> ipAddress = IpAddress.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `page` (Long): Current page number.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `action` (String): Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
* `cursor` (String): Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.


---

## List all possible public IP addresses

```
IpAddress ipAddress = IpAddress.getReserved(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `page` (Long): Current page number.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `action` (String): Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
* `cursor` (String): Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
