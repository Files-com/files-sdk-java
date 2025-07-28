# Files.Models.RemoteServer

## Example RemoteServer Object

```
{
  "id": 1,
  "disabled": true,
  "authentication_method": "password",
  "hostname": "remote-server.com",
  "remote_home_path": "/home/user1",
  "name": "My Remote server",
  "port": 1,
  "max_connections": 1,
  "pin_to_site_region": true,
  "pinned_region": "us-east-1",
  "s3_bucket": "my-bucket",
  "s3_region": "us-east-1",
  "aws_access_key": "example",
  "server_certificate": "require_match",
  "server_host_key": "[public key]",
  "server_type": "s3",
  "ssl": "if_available",
  "username": "user",
  "google_cloud_storage_bucket": "my-bucket",
  "google_cloud_storage_project_id": "my-project",
  "google_cloud_storage_s3_compatible_access_key": "example",
  "backblaze_b2_s3_endpoint": "s3.us-west-001.backblazeb2.com",
  "backblaze_b2_bucket": "my-bucket",
  "wasabi_bucket": "my-bucket",
  "wasabi_region": "us-west-1",
  "wasabi_access_key": "example",
  "auth_status": "in_setup",
  "auth_account_name": "me@example.com",
  "one_drive_account_type": "personal",
  "azure_blob_storage_account": "storage-account-name",
  "azure_blob_storage_container": "container-name",
  "azure_blob_storage_hierarchical_namespace": true,
  "azure_blob_storage_dns_suffix": "usgovcloudapi.net",
  "azure_files_storage_account": "storage-account-name",
  "azure_files_storage_share_name": "share-name",
  "azure_files_storage_dns_suffix": "file.core.windows.net",
  "s3_compatible_bucket": "my-bucket",
  "s3_compatible_endpoint": "mys3platform.com",
  "s3_compatible_region": "us-east-1",
  "s3_compatible_access_key": "example",
  "enable_dedicated_ips": true,
  "files_agent_permission_set": "read_write",
  "files_agent_root": "example",
  "files_agent_api_token": "example",
  "files_agent_version": "example",
  "filebase_bucket": "my-bucket",
  "filebase_access_key": "example",
  "cloudflare_bucket": "my-bucket",
  "cloudflare_access_key": "example",
  "cloudflare_endpoint": "https://<ACCOUNT_ID>.r2.cloudflarestorage.com",
  "dropbox_teams": true,
  "linode_bucket": "my-bucket",
  "linode_access_key": "example",
  "linode_region": "us-east-1",
  "supports_versioning": true
}
```

* `id` / `id`  (int64): Remote server ID
* `disabled` / `disabled`  (boolean): If true, this server has been disabled due to failures.  Make any change or set disabled to false to clear this flag.
* `authentication_method` / `authenticationMethod`  (string): Type of authentication method
* `hostname` / `hostname`  (string): Hostname or IP address
* `remote_home_path` / `remoteHomePath`  (string): Initial home folder on remote server
* `name` / `name`  (string): Internal name for your reference
* `port` / `port`  (int64): Port for remote server.  Not needed for S3.
* `max_connections` / `maxConnections`  (int64): Max number of parallel connections.  Ignored for S3 connections (we will parallelize these as much as possible).
* `pin_to_site_region` / `pinToSiteRegion`  (boolean): If true, we will ensure that all communications with this remote server are made through the primary region of the site.  This setting can also be overridden by a site-wide setting which will force it to true.
* `pinned_region` / `pinnedRegion`  (string): If set, all communications with this remote server are made through the provided region.
* `s3_bucket` / `s3Bucket`  (string): S3 bucket name
* `s3_region` / `s3Region`  (string): S3 region
* `aws_access_key` / `awsAccessKey`  (string): AWS Access Key.
* `server_certificate` / `serverCertificate`  (string): Remote server certificate
* `server_host_key` / `serverHostKey`  (string): Remote server SSH Host Key. If provided, we will require that the server host key matches the provided key. Uses OpenSSH format similar to what would go into ~/.ssh/known_hosts
* `server_type` / `serverType`  (string): Remote server type.
* `ssl` / `ssl`  (string): Should we require SSL?
* `username` / `username`  (string): Remote server username.  Not needed for S3 buckets.
* `google_cloud_storage_bucket` / `googleCloudStorageBucket`  (string): Google Cloud Storage: Bucket Name
* `google_cloud_storage_project_id` / `googleCloudStorageProjectId`  (string): Google Cloud Storage: Project ID
* `google_cloud_storage_s3_compatible_access_key` / `googleCloudStorageS3CompatibleAccessKey`  (string): Google Cloud Storage: S3-compatible Access Key.
* `backblaze_b2_s3_endpoint` / `backblazeB2S3Endpoint`  (string): Backblaze B2 Cloud Storage: S3 Endpoint
* `backblaze_b2_bucket` / `backblazeB2Bucket`  (string): Backblaze B2 Cloud Storage: Bucket name
* `wasabi_bucket` / `wasabiBucket`  (string): Wasabi: Bucket name
* `wasabi_region` / `wasabiRegion`  (string): Wasabi: Region
* `wasabi_access_key` / `wasabiAccessKey`  (string): Wasabi: Access Key.
* `auth_status` / `authStatus`  (string): Either `in_setup` or `complete`
* `auth_account_name` / `authAccountName`  (string): Describes the authorized account
* `one_drive_account_type` / `oneDriveAccountType`  (string): OneDrive: Either personal or business_other account types
* `azure_blob_storage_account` / `azureBlobStorageAccount`  (string): Azure Blob Storage: Account name
* `azure_blob_storage_container` / `azureBlobStorageContainer`  (string): Azure Blob Storage: Container name
* `azure_blob_storage_hierarchical_namespace` / `azureBlobStorageHierarchicalNamespace`  (boolean): Azure Blob Storage: Does the storage account has hierarchical namespace feature enabled?
* `azure_blob_storage_dns_suffix` / `azureBlobStorageDnsSuffix`  (string): Azure Blob Storage: Custom DNS suffix
* `azure_files_storage_account` / `azureFilesStorageAccount`  (string): Azure Files: Storage Account name
* `azure_files_storage_share_name` / `azureFilesStorageShareName`  (string): Azure Files:  Storage Share name
* `azure_files_storage_dns_suffix` / `azureFilesStorageDnsSuffix`  (string): Azure Files: Custom DNS suffix
* `s3_compatible_bucket` / `s3CompatibleBucket`  (string): S3-compatible: Bucket name
* `s3_compatible_endpoint` / `s3CompatibleEndpoint`  (string): S3-compatible: endpoint
* `s3_compatible_region` / `s3CompatibleRegion`  (string): S3-compatible: region
* `s3_compatible_access_key` / `s3CompatibleAccessKey`  (string): S3-compatible: Access Key
* `enable_dedicated_ips` / `enableDedicatedIps`  (boolean): `true` if remote server only accepts connections from dedicated IPs
* `files_agent_permission_set` / `filesAgentPermissionSet`  (string): Local permissions for files agent. read_only, write_only, or read_write
* `files_agent_root` / `filesAgentRoot`  (string): Agent local root path
* `files_agent_api_token` / `filesAgentApiToken`  (string): Files Agent API Token
* `files_agent_version` / `filesAgentVersion`  (string): Files Agent version
* `filebase_bucket` / `filebaseBucket`  (string): Filebase: Bucket name
* `filebase_access_key` / `filebaseAccessKey`  (string): Filebase: Access Key.
* `cloudflare_bucket` / `cloudflareBucket`  (string): Cloudflare: Bucket name
* `cloudflare_access_key` / `cloudflareAccessKey`  (string): Cloudflare: Access Key.
* `cloudflare_endpoint` / `cloudflareEndpoint`  (string): Cloudflare: endpoint
* `dropbox_teams` / `dropboxTeams`  (boolean): Dropbox: If true, list Team folders in root?
* `linode_bucket` / `linodeBucket`  (string): Linode: Bucket name
* `linode_access_key` / `linodeAccessKey`  (string): Linode: Access Key
* `linode_region` / `linodeRegion`  (string): Linode: region
* `supports_versioning` / `supportsVersioning`  (boolean): If true, this remote server supports file versioning. This value is determined automatically by Files.com.
* `password` / `password`  (string): Password, if needed.
* `private_key` / `privateKey`  (string): Private key, if needed.
* `private_key_passphrase` / `privateKeyPassphrase`  (string): Passphrase for private key if needed.
* `reset_authentication` / `resetAuthentication`  (boolean): Reset authenticated account?
* `ssl_certificate` / `sslCertificate`  (string): SSL client certificate.
* `aws_secret_key` / `awsSecretKey`  (string): AWS: secret key.
* `azure_blob_storage_access_key` / `azureBlobStorageAccessKey`  (string): Azure Blob Storage: Access Key
* `azure_blob_storage_sas_token` / `azureBlobStorageSasToken`  (string): Azure Blob Storage: Shared Access Signature (SAS) token
* `azure_files_storage_access_key` / `azureFilesStorageAccessKey`  (string): Azure File Storage: Access Key
* `azure_files_storage_sas_token` / `azureFilesStorageSasToken`  (string): Azure File Storage: Shared Access Signature (SAS) token
* `backblaze_b2_application_key` / `backblazeB2ApplicationKey`  (string): Backblaze B2 Cloud Storage: applicationKey
* `backblaze_b2_key_id` / `backblazeB2KeyId`  (string): Backblaze B2 Cloud Storage: keyID
* `cloudflare_secret_key` / `cloudflareSecretKey`  (string): Cloudflare: Secret Key
* `filebase_secret_key` / `filebaseSecretKey`  (string): Filebase: Secret Key
* `google_cloud_storage_credentials_json` / `googleCloudStorageCredentialsJson`  (string): Google Cloud Storage: JSON file that contains the private key. To generate see https://cloud.google.com/storage/docs/json_api/v1/how-tos/authorizing#APIKey
* `google_cloud_storage_s3_compatible_secret_key` / `googleCloudStorageS3CompatibleSecretKey`  (string): Google Cloud Storage: S3-compatible secret key
* `linode_secret_key` / `linodeSecretKey`  (string): Linode: Secret Key
* `s3_compatible_secret_key` / `s3CompatibleSecretKey`  (string): S3-compatible: Secret Key
* `wasabi_secret_key` / `wasabiSecretKey`  (string): Wasabi: Secret Key


---

## List Remote Servers

```
ListIterator<RemoteServer> remoteServer = RemoteServer.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `name`, `server_type`, `backblaze_b2_bucket`, `google_cloud_storage_bucket`, `wasabi_bucket`, `s3_bucket`, `azure_blob_storage_container`, `azure_files_storage_share_name`, `s3_compatible_bucket`, `filebase_bucket`, `cloudflare_bucket` or `linode_bucket`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `name`, `server_type`, `backblaze_b2_bucket`, `google_cloud_storage_bucket`, `wasabi_bucket`, `s3_bucket`, `azure_blob_storage_container`, `azure_files_storage_share_name`, `s3_compatible_bucket`, `filebase_bucket`, `cloudflare_bucket` or `linode_bucket`. Valid field combinations are `[ server_type, name ]`, `[ backblaze_b2_bucket, name ]`, `[ google_cloud_storage_bucket, name ]`, `[ wasabi_bucket, name ]`, `[ s3_bucket, name ]`, `[ azure_blob_storage_container, name ]`, `[ azure_files_storage_share_name, name ]`, `[ s3_compatible_bucket, name ]`, `[ filebase_bucket, name ]`, `[ cloudflare_bucket, name ]` or `[ linode_bucket, name ]`.
* `filter_prefix` (Map<String, String>): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `name`, `backblaze_b2_bucket`, `google_cloud_storage_bucket`, `wasabi_bucket`, `s3_bucket`, `azure_blob_storage_container`, `azure_files_storage_share_name`, `s3_compatible_bucket`, `filebase_bucket`, `cloudflare_bucket` or `linode_bucket`. Valid field combinations are `[ backblaze_b2_bucket, name ]`, `[ google_cloud_storage_bucket, name ]`, `[ wasabi_bucket, name ]`, `[ s3_bucket, name ]`, `[ azure_blob_storage_container, name ]`, `[ azure_files_storage_share_name, name ]`, `[ s3_compatible_bucket, name ]`, `[ filebase_bucket, name ]`, `[ cloudflare_bucket, name ]` or `[ linode_bucket, name ]`.


---

## Show Remote Server

```
RemoteServer remoteServer = RemoteServer.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Remote Server ID.


---

## Download configuration file (required for some Remote Server integrations, such as the Files.com Agent)

```
RemoteServerConfigurationFile remoteServer = RemoteServer.findConfigurationFile(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Remote Server ID.


---

## Create Remote Server

```
RemoteServer remoteServer = RemoteServer.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `password` (String): Password, if needed.
* `private_key` (String): Private key, if needed.
* `private_key_passphrase` (String): Passphrase for private key if needed.
* `reset_authentication` (Boolean): Reset authenticated account?
* `ssl_certificate` (String): SSL client certificate.
* `aws_secret_key` (String): AWS: secret key.
* `azure_blob_storage_access_key` (String): Azure Blob Storage: Access Key
* `azure_blob_storage_sas_token` (String): Azure Blob Storage: Shared Access Signature (SAS) token
* `azure_files_storage_access_key` (String): Azure File Storage: Access Key
* `azure_files_storage_sas_token` (String): Azure File Storage: Shared Access Signature (SAS) token
* `backblaze_b2_application_key` (String): Backblaze B2 Cloud Storage: applicationKey
* `backblaze_b2_key_id` (String): Backblaze B2 Cloud Storage: keyID
* `cloudflare_secret_key` (String): Cloudflare: Secret Key
* `filebase_secret_key` (String): Filebase: Secret Key
* `google_cloud_storage_credentials_json` (String): Google Cloud Storage: JSON file that contains the private key. To generate see https://cloud.google.com/storage/docs/json_api/v1/how-tos/authorizing#APIKey
* `google_cloud_storage_s3_compatible_secret_key` (String): Google Cloud Storage: S3-compatible secret key
* `linode_secret_key` (String): Linode: Secret Key
* `s3_compatible_secret_key` (String): S3-compatible: Secret Key
* `wasabi_secret_key` (String): Wasabi: Secret Key
* `aws_access_key` (String): AWS Access Key.
* `azure_blob_storage_account` (String): Azure Blob Storage: Account name
* `azure_blob_storage_container` (String): Azure Blob Storage: Container name
* `azure_blob_storage_dns_suffix` (String): Azure Blob Storage: Custom DNS suffix
* `azure_blob_storage_hierarchical_namespace` (Boolean): Azure Blob Storage: Does the storage account has hierarchical namespace feature enabled?
* `azure_files_storage_account` (String): Azure Files: Storage Account name
* `azure_files_storage_dns_suffix` (String): Azure Files: Custom DNS suffix
* `azure_files_storage_share_name` (String): Azure Files:  Storage Share name
* `backblaze_b2_bucket` (String): Backblaze B2 Cloud Storage: Bucket name
* `backblaze_b2_s3_endpoint` (String): Backblaze B2 Cloud Storage: S3 Endpoint
* `cloudflare_access_key` (String): Cloudflare: Access Key.
* `cloudflare_bucket` (String): Cloudflare: Bucket name
* `cloudflare_endpoint` (String): Cloudflare: endpoint
* `dropbox_teams` (Boolean): Dropbox: If true, list Team folders in root?
* `enable_dedicated_ips` (Boolean): `true` if remote server only accepts connections from dedicated IPs
* `filebase_access_key` (String): Filebase: Access Key.
* `filebase_bucket` (String): Filebase: Bucket name
* `files_agent_permission_set` (String): Local permissions for files agent. read_only, write_only, or read_write
* `files_agent_root` (String): Agent local root path
* `files_agent_version` (String): Files Agent version
* `google_cloud_storage_bucket` (String): Google Cloud Storage: Bucket Name
* `google_cloud_storage_project_id` (String): Google Cloud Storage: Project ID
* `google_cloud_storage_s3_compatible_access_key` (String): Google Cloud Storage: S3-compatible Access Key.
* `hostname` (String): Hostname or IP address
* `linode_access_key` (String): Linode: Access Key
* `linode_bucket` (String): Linode: Bucket name
* `linode_region` (String): Linode: region
* `max_connections` (Long): Max number of parallel connections.  Ignored for S3 connections (we will parallelize these as much as possible).
* `name` (String): Internal name for your reference
* `one_drive_account_type` (String): OneDrive: Either personal or business_other account types
* `pin_to_site_region` (Boolean): If true, we will ensure that all communications with this remote server are made through the primary region of the site.  This setting can also be overridden by a site-wide setting which will force it to true.
* `port` (Long): Port for remote server.  Not needed for S3.
* `s3_bucket` (String): S3 bucket name
* `s3_compatible_access_key` (String): S3-compatible: Access Key
* `s3_compatible_bucket` (String): S3-compatible: Bucket name
* `s3_compatible_endpoint` (String): S3-compatible: endpoint
* `s3_compatible_region` (String): S3-compatible: region
* `s3_region` (String): S3 region
* `server_certificate` (String): Remote server certificate
* `server_host_key` (String): Remote server SSH Host Key. If provided, we will require that the server host key matches the provided key. Uses OpenSSH format similar to what would go into ~/.ssh/known_hosts
* `server_type` (String): Remote server type.
* `ssl` (String): Should we require SSL?
* `username` (String): Remote server username.  Not needed for S3 buckets.
* `wasabi_access_key` (String): Wasabi: Access Key.
* `wasabi_bucket` (String): Wasabi: Bucket name
* `wasabi_region` (String): Wasabi: Region


---

## Post local changes, check in, and download configuration file (used by some Remote Server integrations, such as the Files.com Agent)

```
RemoteServerConfigurationFile remoteServer = RemoteServer.configurationFile(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Remote Server ID.
* `api_token` (String): Files Agent API Token
* `permission_set` (String): The permission set for the agent ['read_write', 'read_only', 'write_only']
* `root` (String): The root directory for the agent
* `hostname` (String): 
* `port` (Long): Incoming port for files agent connections
* `status` (String): either running or shutdown
* `config_version` (String): agent config version
* `private_key` (String): The private key for the agent
* `public_key` (String): public key
* `server_host_key` (String): 
* `subdomain` (String): Files.com subdomain site name


---

## Update Remote Server

```
RemoteServer remoteServer = RemoteServer.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Remote Server ID.
* `password` (String): Password, if needed.
* `private_key` (String): Private key, if needed.
* `private_key_passphrase` (String): Passphrase for private key if needed.
* `reset_authentication` (Boolean): Reset authenticated account?
* `ssl_certificate` (String): SSL client certificate.
* `aws_secret_key` (String): AWS: secret key.
* `azure_blob_storage_access_key` (String): Azure Blob Storage: Access Key
* `azure_blob_storage_sas_token` (String): Azure Blob Storage: Shared Access Signature (SAS) token
* `azure_files_storage_access_key` (String): Azure File Storage: Access Key
* `azure_files_storage_sas_token` (String): Azure File Storage: Shared Access Signature (SAS) token
* `backblaze_b2_application_key` (String): Backblaze B2 Cloud Storage: applicationKey
* `backblaze_b2_key_id` (String): Backblaze B2 Cloud Storage: keyID
* `cloudflare_secret_key` (String): Cloudflare: Secret Key
* `filebase_secret_key` (String): Filebase: Secret Key
* `google_cloud_storage_credentials_json` (String): Google Cloud Storage: JSON file that contains the private key. To generate see https://cloud.google.com/storage/docs/json_api/v1/how-tos/authorizing#APIKey
* `google_cloud_storage_s3_compatible_secret_key` (String): Google Cloud Storage: S3-compatible secret key
* `linode_secret_key` (String): Linode: Secret Key
* `s3_compatible_secret_key` (String): S3-compatible: Secret Key
* `wasabi_secret_key` (String): Wasabi: Secret Key
* `aws_access_key` (String): AWS Access Key.
* `azure_blob_storage_account` (String): Azure Blob Storage: Account name
* `azure_blob_storage_container` (String): Azure Blob Storage: Container name
* `azure_blob_storage_dns_suffix` (String): Azure Blob Storage: Custom DNS suffix
* `azure_blob_storage_hierarchical_namespace` (Boolean): Azure Blob Storage: Does the storage account has hierarchical namespace feature enabled?
* `azure_files_storage_account` (String): Azure Files: Storage Account name
* `azure_files_storage_dns_suffix` (String): Azure Files: Custom DNS suffix
* `azure_files_storage_share_name` (String): Azure Files:  Storage Share name
* `backblaze_b2_bucket` (String): Backblaze B2 Cloud Storage: Bucket name
* `backblaze_b2_s3_endpoint` (String): Backblaze B2 Cloud Storage: S3 Endpoint
* `cloudflare_access_key` (String): Cloudflare: Access Key.
* `cloudflare_bucket` (String): Cloudflare: Bucket name
* `cloudflare_endpoint` (String): Cloudflare: endpoint
* `dropbox_teams` (Boolean): Dropbox: If true, list Team folders in root?
* `enable_dedicated_ips` (Boolean): `true` if remote server only accepts connections from dedicated IPs
* `filebase_access_key` (String): Filebase: Access Key.
* `filebase_bucket` (String): Filebase: Bucket name
* `files_agent_permission_set` (String): Local permissions for files agent. read_only, write_only, or read_write
* `files_agent_root` (String): Agent local root path
* `files_agent_version` (String): Files Agent version
* `google_cloud_storage_bucket` (String): Google Cloud Storage: Bucket Name
* `google_cloud_storage_project_id` (String): Google Cloud Storage: Project ID
* `google_cloud_storage_s3_compatible_access_key` (String): Google Cloud Storage: S3-compatible Access Key.
* `hostname` (String): Hostname or IP address
* `linode_access_key` (String): Linode: Access Key
* `linode_bucket` (String): Linode: Bucket name
* `linode_region` (String): Linode: region
* `max_connections` (Long): Max number of parallel connections.  Ignored for S3 connections (we will parallelize these as much as possible).
* `name` (String): Internal name for your reference
* `one_drive_account_type` (String): OneDrive: Either personal or business_other account types
* `pin_to_site_region` (Boolean): If true, we will ensure that all communications with this remote server are made through the primary region of the site.  This setting can also be overridden by a site-wide setting which will force it to true.
* `port` (Long): Port for remote server.  Not needed for S3.
* `s3_bucket` (String): S3 bucket name
* `s3_compatible_access_key` (String): S3-compatible: Access Key
* `s3_compatible_bucket` (String): S3-compatible: Bucket name
* `s3_compatible_endpoint` (String): S3-compatible: endpoint
* `s3_compatible_region` (String): S3-compatible: region
* `s3_region` (String): S3 region
* `server_certificate` (String): Remote server certificate
* `server_host_key` (String): Remote server SSH Host Key. If provided, we will require that the server host key matches the provided key. Uses OpenSSH format similar to what would go into ~/.ssh/known_hosts
* `server_type` (String): Remote server type.
* `ssl` (String): Should we require SSL?
* `username` (String): Remote server username.  Not needed for S3 buckets.
* `wasabi_access_key` (String): Wasabi: Access Key.
* `wasabi_bucket` (String): Wasabi: Bucket name
* `wasabi_region` (String): Wasabi: Region


---

## Delete Remote Server

```
void remoteServer = RemoteServer.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Remote Server ID.


---

## Post local changes, check in, and download configuration file (used by some Remote Server integrations, such as the Files.com Agent)

```
RemoteServer remoteServer = RemoteServer.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("api_token", "example");
parameters.put("permission_set", "example");
parameters.put("root", "C:\\Users\\");
parameters.put("hostname", "example");
parameters.put("port", 1);
parameters.put("status", "example");
parameters.put("config_version", "example");
parameters.put("private_key", "example");
parameters.put("public_key", "example");
parameters.put("server_host_key", "example");
parameters.put("subdomain", "example");

remoteServer.configurationFile(parameters);
```

### Parameters

* `id` (Long): Required - Remote Server ID.
* `api_token` (String): Files Agent API Token
* `permission_set` (String): The permission set for the agent ['read_write', 'read_only', 'write_only']
* `root` (String): The root directory for the agent
* `hostname` (String): 
* `port` (Long): Incoming port for files agent connections
* `status` (String): either running or shutdown
* `config_version` (String): agent config version
* `private_key` (String): The private key for the agent
* `public_key` (String): public key
* `server_host_key` (String): 
* `subdomain` (String): Files.com subdomain site name


---

## Update Remote Server

```
RemoteServer remoteServer = RemoteServer.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("reset_authentication", false);
parameters.put("aws_access_key", "example");
parameters.put("azure_blob_storage_account", "storage-account-name");
parameters.put("azure_blob_storage_container", "container-name");
parameters.put("azure_blob_storage_dns_suffix", "usgovcloudapi.net");
parameters.put("azure_blob_storage_hierarchical_namespace", true);
parameters.put("azure_files_storage_account", "storage-account-name");
parameters.put("azure_files_storage_dns_suffix", "file.core.windows.net");
parameters.put("azure_files_storage_share_name", "share-name");
parameters.put("backblaze_b2_bucket", "my-bucket");
parameters.put("backblaze_b2_s3_endpoint", "s3.us-west-001.backblazeb2.com");
parameters.put("cloudflare_access_key", "example");
parameters.put("cloudflare_bucket", "my-bucket");
parameters.put("cloudflare_endpoint", "https://<ACCOUNT_ID>.r2.cloudflarestorage.com");
parameters.put("dropbox_teams", true);
parameters.put("enable_dedicated_ips", true);
parameters.put("filebase_access_key", "example");
parameters.put("filebase_bucket", "my-bucket");
parameters.put("files_agent_permission_set", "read_write");
parameters.put("files_agent_root", "example");
parameters.put("files_agent_version", "example");
parameters.put("google_cloud_storage_bucket", "my-bucket");
parameters.put("google_cloud_storage_project_id", "my-project");
parameters.put("google_cloud_storage_s3_compatible_access_key", "example");
parameters.put("hostname", "remote-server.com");
parameters.put("linode_access_key", "example");
parameters.put("linode_bucket", "my-bucket");
parameters.put("linode_region", "us-east-1");
parameters.put("max_connections", 1);
parameters.put("name", "My Remote server");
parameters.put("one_drive_account_type", "personal");
parameters.put("pin_to_site_region", true);
parameters.put("port", 1);
parameters.put("s3_bucket", "my-bucket");
parameters.put("s3_compatible_access_key", "example");
parameters.put("s3_compatible_bucket", "my-bucket");
parameters.put("s3_compatible_endpoint", "mys3platform.com");
parameters.put("s3_compatible_region", "us-east-1");
parameters.put("s3_region", "us-east-1");
parameters.put("server_certificate", "require_match");
parameters.put("server_host_key", "[public key]");
parameters.put("server_type", "s3");
parameters.put("ssl", "if_available");
parameters.put("username", "user");
parameters.put("wasabi_access_key", "example");
parameters.put("wasabi_bucket", "my-bucket");
parameters.put("wasabi_region", "us-west-1");

remoteServer.update(parameters);
```

### Parameters

* `id` (Long): Required - Remote Server ID.
* `password` (String): Password, if needed.
* `private_key` (String): Private key, if needed.
* `private_key_passphrase` (String): Passphrase for private key if needed.
* `reset_authentication` (Boolean): Reset authenticated account?
* `ssl_certificate` (String): SSL client certificate.
* `aws_secret_key` (String): AWS: secret key.
* `azure_blob_storage_access_key` (String): Azure Blob Storage: Access Key
* `azure_blob_storage_sas_token` (String): Azure Blob Storage: Shared Access Signature (SAS) token
* `azure_files_storage_access_key` (String): Azure File Storage: Access Key
* `azure_files_storage_sas_token` (String): Azure File Storage: Shared Access Signature (SAS) token
* `backblaze_b2_application_key` (String): Backblaze B2 Cloud Storage: applicationKey
* `backblaze_b2_key_id` (String): Backblaze B2 Cloud Storage: keyID
* `cloudflare_secret_key` (String): Cloudflare: Secret Key
* `filebase_secret_key` (String): Filebase: Secret Key
* `google_cloud_storage_credentials_json` (String): Google Cloud Storage: JSON file that contains the private key. To generate see https://cloud.google.com/storage/docs/json_api/v1/how-tos/authorizing#APIKey
* `google_cloud_storage_s3_compatible_secret_key` (String): Google Cloud Storage: S3-compatible secret key
* `linode_secret_key` (String): Linode: Secret Key
* `s3_compatible_secret_key` (String): S3-compatible: Secret Key
* `wasabi_secret_key` (String): Wasabi: Secret Key
* `aws_access_key` (String): AWS Access Key.
* `azure_blob_storage_account` (String): Azure Blob Storage: Account name
* `azure_blob_storage_container` (String): Azure Blob Storage: Container name
* `azure_blob_storage_dns_suffix` (String): Azure Blob Storage: Custom DNS suffix
* `azure_blob_storage_hierarchical_namespace` (Boolean): Azure Blob Storage: Does the storage account has hierarchical namespace feature enabled?
* `azure_files_storage_account` (String): Azure Files: Storage Account name
* `azure_files_storage_dns_suffix` (String): Azure Files: Custom DNS suffix
* `azure_files_storage_share_name` (String): Azure Files:  Storage Share name
* `backblaze_b2_bucket` (String): Backblaze B2 Cloud Storage: Bucket name
* `backblaze_b2_s3_endpoint` (String): Backblaze B2 Cloud Storage: S3 Endpoint
* `cloudflare_access_key` (String): Cloudflare: Access Key.
* `cloudflare_bucket` (String): Cloudflare: Bucket name
* `cloudflare_endpoint` (String): Cloudflare: endpoint
* `dropbox_teams` (Boolean): Dropbox: If true, list Team folders in root?
* `enable_dedicated_ips` (Boolean): `true` if remote server only accepts connections from dedicated IPs
* `filebase_access_key` (String): Filebase: Access Key.
* `filebase_bucket` (String): Filebase: Bucket name
* `files_agent_permission_set` (String): Local permissions for files agent. read_only, write_only, or read_write
* `files_agent_root` (String): Agent local root path
* `files_agent_version` (String): Files Agent version
* `google_cloud_storage_bucket` (String): Google Cloud Storage: Bucket Name
* `google_cloud_storage_project_id` (String): Google Cloud Storage: Project ID
* `google_cloud_storage_s3_compatible_access_key` (String): Google Cloud Storage: S3-compatible Access Key.
* `hostname` (String): Hostname or IP address
* `linode_access_key` (String): Linode: Access Key
* `linode_bucket` (String): Linode: Bucket name
* `linode_region` (String): Linode: region
* `max_connections` (Long): Max number of parallel connections.  Ignored for S3 connections (we will parallelize these as much as possible).
* `name` (String): Internal name for your reference
* `one_drive_account_type` (String): OneDrive: Either personal or business_other account types
* `pin_to_site_region` (Boolean): If true, we will ensure that all communications with this remote server are made through the primary region of the site.  This setting can also be overridden by a site-wide setting which will force it to true.
* `port` (Long): Port for remote server.  Not needed for S3.
* `s3_bucket` (String): S3 bucket name
* `s3_compatible_access_key` (String): S3-compatible: Access Key
* `s3_compatible_bucket` (String): S3-compatible: Bucket name
* `s3_compatible_endpoint` (String): S3-compatible: endpoint
* `s3_compatible_region` (String): S3-compatible: region
* `s3_region` (String): S3 region
* `server_certificate` (String): Remote server certificate
* `server_host_key` (String): Remote server SSH Host Key. If provided, we will require that the server host key matches the provided key. Uses OpenSSH format similar to what would go into ~/.ssh/known_hosts
* `server_type` (String): Remote server type.
* `ssl` (String): Should we require SSL?
* `username` (String): Remote server username.  Not needed for S3 buckets.
* `wasabi_access_key` (String): Wasabi: Access Key.
* `wasabi_bucket` (String): Wasabi: Bucket name
* `wasabi_region` (String): Wasabi: Region


---

## Delete Remote Server

```
RemoteServer remoteServer = RemoteServer.find(id);

HashMap<String, Object> parameters = new HashMap<>();

remoteServer.delete(parameters);
```

### Parameters

* `id` (Long): Required - Remote Server ID.
