# Files.Models.Group

## Example Group Object

```
{
  "id": 1,
  "name": "owners",
  "allowed_ips": "10.0.0.0/8\n127.0.0.1",
  "admin_ids": "1",
  "notes": "example",
  "user_ids": "1",
  "usernames": "example",
  "ftp_permission": true,
  "sftp_permission": true,
  "dav_permission": true,
  "restapi_permission": true
}
```

* `id` / `id`  (int64): Group ID
* `name` / `name`  (string): Group name
* `allowed_ips` / `allowedIps`  (string): A list of allowed IPs if applicable.  Newline delimited
* `admin_ids` / `adminIds`  (string): Comma-delimited list of user IDs who are group administrators (separated by commas)
* `notes` / `notes`  (string): Notes about this group
* `user_ids` / `userIds`  (string): Comma-delimited list of user IDs who belong to this group (separated by commas)
* `usernames` / `usernames`  (string): Comma-delimited list of usernames who belong to this group (separated by commas)
* `ftp_permission` / `ftpPermission`  (boolean): If true, users in this group can use FTP to login.  This will override a false value of `ftp_permission` on the user level.
* `sftp_permission` / `sftpPermission`  (boolean): If true, users in this group can use SFTP to login.  This will override a false value of `sftp_permission` on the user level.
* `dav_permission` / `davPermission`  (boolean): If true, users in this group can use WebDAV to login.  This will override a false value of `dav_permission` on the user level.
* `restapi_permission` / `restapiPermission`  (boolean): If true, users in this group can use the REST API to login.  This will override a false value of `restapi_permission` on the user level.


---

## List Groups

```
ListIterator<Group> group = Group.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction (e.g. `sort_by[name]=desc`). Valid fields are `name`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `name`.
* `filter_prefix` (Map<String, String>): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `name`.
* `ids` (String): Comma-separated list of group ids to include in results.


---

## Show Group

```
Group group = Group.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Group ID.


---

## Create Group

```
Group group = Group.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `notes` (String): Group notes.
* `user_ids` (String): A list of user ids. If sent as a string, should be comma-delimited.
* `admin_ids` (String): A list of group admin user ids. If sent as a string, should be comma-delimited.
* `ftp_permission` (Boolean): If true, users in this group can use FTP to login.  This will override a false value of `ftp_permission` on the user level.
* `sftp_permission` (Boolean): If true, users in this group can use SFTP to login.  This will override a false value of `sftp_permission` on the user level.
* `dav_permission` (Boolean): If true, users in this group can use WebDAV to login.  This will override a false value of `dav_permission` on the user level.
* `restapi_permission` (Boolean): If true, users in this group can use the REST API to login.  This will override a false value of `restapi_permission` on the user level.
* `allowed_ips` (String): A list of allowed IPs if applicable.  Newline delimited
* `name` (String): Required - Group name.


---

## Update Group

```
Group group = Group.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Group ID.
* `notes` (String): Group notes.
* `user_ids` (String): A list of user ids. If sent as a string, should be comma-delimited.
* `admin_ids` (String): A list of group admin user ids. If sent as a string, should be comma-delimited.
* `ftp_permission` (Boolean): If true, users in this group can use FTP to login.  This will override a false value of `ftp_permission` on the user level.
* `sftp_permission` (Boolean): If true, users in this group can use SFTP to login.  This will override a false value of `sftp_permission` on the user level.
* `dav_permission` (Boolean): If true, users in this group can use WebDAV to login.  This will override a false value of `dav_permission` on the user level.
* `restapi_permission` (Boolean): If true, users in this group can use the REST API to login.  This will override a false value of `restapi_permission` on the user level.
* `allowed_ips` (String): A list of allowed IPs if applicable.  Newline delimited
* `name` (String): Group name.


---

## Delete Group

```
void group = Group.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Group ID.


---

## Update Group

```
Group group = Group.List()[0];

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("notes", "example");
parameters.put("user_ids", "1");
parameters.put("admin_ids", "1");
parameters.put("ftp_permission", true);
parameters.put("sftp_permission", true);
parameters.put("dav_permission", true);
parameters.put("restapi_permission", true);
parameters.put("allowed_ips", "10.0.0.0/8\n127.0.0.1");
parameters.put("name", "owners");

Group.Update(parameters);
```

### Parameters

* `id` (Long): Required - Group ID.
* `notes` (String): Group notes.
* `user_ids` (String): A list of user ids. If sent as a string, should be comma-delimited.
* `admin_ids` (String): A list of group admin user ids. If sent as a string, should be comma-delimited.
* `ftp_permission` (Boolean): If true, users in this group can use FTP to login.  This will override a false value of `ftp_permission` on the user level.
* `sftp_permission` (Boolean): If true, users in this group can use SFTP to login.  This will override a false value of `sftp_permission` on the user level.
* `dav_permission` (Boolean): If true, users in this group can use WebDAV to login.  This will override a false value of `dav_permission` on the user level.
* `restapi_permission` (Boolean): If true, users in this group can use the REST API to login.  This will override a false value of `restapi_permission` on the user level.
* `allowed_ips` (String): A list of allowed IPs if applicable.  Newline delimited
* `name` (String): Group name.


---

## Delete Group

```
Group group = Group.List()[0];

HashMap<String, Object> parameters = new HashMap<>();


Group.Delete
```

### Parameters

* `id` (Long): Required - Group ID.
