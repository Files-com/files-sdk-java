# Files.Models.PublicIpAddress

## Example PublicIpAddress Object

```
{
  "ip_address": "1.1.1.1",
  "server_name": "server-1",
  "ftp_enabled": true,
  "sftp_enabled": true
}
```

* `ip_address` / `ipAddress`  (string): The public IP address.
* `server_name` / `serverName`  (string): The name of the frontend server.
* `ftp_enabled` / `ftpEnabled`  (boolean): Whether FTP is enabled for the site associated with this IP address. Present only when the address has an associated site.
* `sftp_enabled` / `sftpEnabled`  (boolean): Whether SFTP is enabled for the site associated with this IP address. Present only when the address has an associated site.
