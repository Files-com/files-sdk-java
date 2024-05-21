# Files.Models.RemoteServerConfigurationFile

## Example RemoteServerConfigurationFile Object

```
{
  "id": 1,
  "permission_set": "example",
  "private_key": "example",
  "subdomain": "example",
  "root": "C:\\Users\\",
  "follow_links": true,
  "prefer_protocol": "example",
  "dns": "example",
  "proxy_all_outbound": true,
  "endpoint_override": "example",
  "log_file": "example",
  "log_level": "example",
  "log_rotate_num": 1,
  "log_rotate_size": 1,
  "max_concurrent_jobs": 1,
  "graceful_shutdown_timeout": 1,
  "transfer_rate_limit": "example",
  "api_token": "example",
  "port": 1,
  "hostname": "example",
  "public_key": "example",
  "status": "example",
  "server_host_key": "example",
  "config_version": "example"
}
```

* `id` / `id`  (int64): The remote server ID of the agent
* `permission_set` / `permissionSet`  (string): The permission set for the agent ['read_write', 'read_only', 'write_only']
* `private_key` / `privateKey`  (string): The private key for the agent
* `subdomain` / `subdomain`  (string): Files.com subdomain site name
* `root` / `root`  (string): The root directory for the agent
* `follow_links` / `followLinks`  (boolean): Follow symlinks when traversing directories
* `prefer_protocol` / `preferProtocol`  (string): Preferred network protocol ['udp', 'tcp']
* `dns` / `dns`  (string): DNS lookup method ['auto','doh','system']
* `proxy_all_outbound` / `proxyAllOutbound`  (boolean): Proxy all outbound traffic through files.com proxy server
* `endpoint_override` / `endpointOverride`  (string): Custom site endpoint URL
* `log_file` / `logFile`  (string): Log file name and location
* `log_level` / `logLevel`  (string): Log level for the agent logs ['debug', 'info', 'warn', 'error', 'fatal']
* `log_rotate_num` / `logRotateNum`  (int64): Log route for agent logs. (default 5)
* `log_rotate_size` / `logRotateSize`  (int64): Log route size in MB for agent logs. (default 20MB)
* `max_concurrent_jobs` / `maxConcurrentJobs`  (int64): Maximum number of concurrent jobs (default CPU Count * 4)
* `graceful_shutdown_timeout` / `gracefulShutdownTimeout`  (int64): Graceful shutdown timeout in seconds
* `transfer_rate_limit` / `transferRateLimit`  (string): File transfer (upload/download) rate limit
 `<limit>-<period>`, with the given periods:
* 'S': second
* 'M': minute
* 'H': hour
* 'D': day
Examples:
* 5 requests/second: '5-S'
* 10 requests/minute: '10-M'
* 1000 requests/hour: '1000-H'
* 2000 requests/day: '2000-D'
* `api_token` / `apiToken`  (string): Files Agent API Token
* `port` / `port`  (int64): Incoming port for files agent connections
* `hostname` / `hostname`  (string): 
* `public_key` / `publicKey`  (string): public key
* `status` / `status`  (string): either running or shutdown
* `server_host_key` / `serverHostKey`  (string): 
* `config_version` / `configVersion`  (string): agent config version
