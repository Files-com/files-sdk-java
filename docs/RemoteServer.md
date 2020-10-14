# Files.Models.RemoteServer

## Example RemoteServer Object

```
{
  "id": 1,
  "authentication_method": "password",
  "hostname": "remote-server.com",
  "name": "My Remote server",
  "port": 1,
  "max_connections": 1,
  "s3_bucket": "my-bucket",
  "s3_region": "us-east-1",
  "server_certificate": "require_match",
  "server_host_key": "[public key]",
  "server_type": "s3",
  "ssl": "if_available",
  "username": "user",
  "google_cloud_storage_bucket": "my-bucket",
  "google_cloud_storage_project_id": "my-project",
  "backblaze_b2_s3_endpoint": "s3.us-west-001.backblazeb2.com",
  "backblaze_b2_bucket": "my-bucket",
  "wasabi_bucket": "us-west-1",
  "wasabi_region": "my-bucket",
  "rackspace_username": "rackspaceuser",
  "rackspace_region": "dfw",
  "rackspace_container": "my-container",
  "auth_setup_link": "auth/:provider",
  "auth_status": "in_setup",
  "auth_account_name": "me@example.com",
  "one_drive_account_type": "personal",
  "azure_blob_storage_account": "storage-account-name",
  "azure_blob_storage_container": "container-name"
}
```

* `id` / `id`  (int64): Remote server ID
* `authentication_method` / `authenticationMethod`  (string): Type of authentication method
* `hostname` / `hostname`  (string): Hostname or IP address
* `name` / `name`  (string): Internal name for your reference
* `port` / `port`  (int64): Port for remote server.  Not needed for S3.
* `max_connections` / `maxConnections`  (int64): Max number of parallel connections.  Ignored for S3 connections (we will parallelize these as much as possible).
* `s3_bucket` / `s3Bucket`  (string): S3 bucket name
* `s3_region` / `s3Region`  (string): S3 region
* `server_certificate` / `serverCertificate`  (string): Remote server certificate
* `server_host_key` / `serverHostKey`  (string): Remote server SSH Host Key. If provided, we will require that the server host key matches the provided key. Uses OpenSSH format similar to what would go into ~/.ssh/known_hosts
* `server_type` / `serverType`  (string): Remote server type.
* `ssl` / `ssl`  (string): Should we require SSL?
* `username` / `username`  (string): Remote server username.  Not needed for S3 buckets.
* `google_cloud_storage_bucket` / `googleCloudStorageBucket`  (string): Google Cloud Storage bucket name
* `google_cloud_storage_project_id` / `googleCloudStorageProjectId`  (string): Google Cloud Project ID
* `backblaze_b2_s3_endpoint` / `backblazeB2S3Endpoint`  (string): Backblaze B2 Cloud Storage S3 Endpoint
* `backblaze_b2_bucket` / `backblazeB2Bucket`  (string): Backblaze B2 Cloud Storage Bucket name
* `wasabi_bucket` / `wasabiBucket`  (string): Wasabi region
* `wasabi_region` / `wasabiRegion`  (string): Wasabi Bucket name
* `rackspace_username` / `rackspaceUsername`  (string): Rackspace username used to login to the Rackspace Cloud Control Panel.
* `rackspace_region` / `rackspaceRegion`  (string): Three letter airport code for Rackspace region. See https://support.rackspace.com/how-to/about-regions/
* `rackspace_container` / `rackspaceContainer`  (string): The name of the container (top level directory) where files will sync.
* `auth_setup_link` / `authSetupLink`  (string): Returns link to login with an Oauth provider
* `auth_status` / `authStatus`  (string): Either `in_setup` or `complete`
* `auth_account_name` / `authAccountName`  (string): Describes the authorized account
* `one_drive_account_type` / `oneDriveAccountType`  (string): Either personal or business_other account types
* `azure_blob_storage_account` / `azureBlobStorageAccount`  (string): Azure Blob Storage Account name
* `azure_blob_storage_container` / `azureBlobStorageContainer`  (string): Azure Blob Storage Container name
* `aws_access_key` / `awsAccessKey`  (string): AWS Access Key.
* `aws_secret_key` / `awsSecretKey`  (string): AWS secret key.
* `password` / `password`  (string): Password if needed.
* `private_key` / `privateKey`  (string): Private key if needed.
* `google_cloud_storage_credentials_json` / `googleCloudStorageCredentialsJson`  (string): A JSON file that contains the private key. To generate see https://cloud.google.com/storage/docs/json_api/v1/how-tos/authorizing#APIKey
* `wasabi_access_key` / `wasabiAccessKey`  (string): Wasabi access key.
* `wasabi_secret_key` / `wasabiSecretKey`  (string): Wasabi secret key.
* `backblaze_b2_key_id` / `backblazeB2KeyId`  (string): Backblaze B2 Cloud Storage keyID.
* `backblaze_b2_application_key` / `backblazeB2ApplicationKey`  (string): Backblaze B2 Cloud Storage applicationKey.
* `rackspace_api_key` / `rackspaceApiKey`  (string): Rackspace API key from the Rackspace Cloud Control Panel.
* `reset_authentication` / `resetAuthentication`  (boolean): Reset authenticated account
* `azure_blob_storage_access_key` / `azureBlobStorageAccessKey`  (string): Azure Blob Storage secret key.


---

## List Remote Servers

```
List<RemoteServer> remoteServer = RemoteServer.list(
    
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

## Show Remote Server

```
List<RemoteServer> remoteServer = RemoteServer.find(
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

* `aws_access_key` (String): AWS Access Key.
* `aws_secret_key` (String): AWS secret key.
* `password` (String): Password if needed.
* `private_key` (String): Private key if needed.
* `google_cloud_storage_credentials_json` (String): A JSON file that contains the private key. To generate see https://cloud.google.com/storage/docs/json_api/v1/how-tos/authorizing#APIKey
* `wasabi_access_key` (String): Wasabi access key.
* `wasabi_secret_key` (String): Wasabi secret key.
* `backblaze_b2_key_id` (String): Backblaze B2 Cloud Storage keyID.
* `backblaze_b2_application_key` (String): Backblaze B2 Cloud Storage applicationKey.
* `rackspace_api_key` (String): Rackspace API key from the Rackspace Cloud Control Panel.
* `reset_authentication` (Boolean): Reset authenticated account
* `azure_blob_storage_access_key` (String): Azure Blob Storage secret key.
* `hostname` (String): Hostname or IP address
* `name` (String): Internal name for your reference
* `max_connections` (Long): Max number of parallel connections.  Ignored for S3 connections (we will parallelize these as much as possible).
* `port` (Long): Port for remote server.  Not needed for S3.
* `s3_bucket` (String): S3 bucket name
* `s3_region` (String): S3 region
* `server_certificate` (String): Remote server certificate
* `server_host_key` (String): Remote server SSH Host Key. If provided, we will require that the server host key matches the provided key. Uses OpenSSH format similar to what would go into ~/.ssh/known_hosts
* `server_type` (String): Remote server type.
* `ssl` (String): Should we require SSL?
* `username` (String): Remote server username.  Not needed for S3 buckets.
* `google_cloud_storage_bucket` (String): Google Cloud Storage bucket name
* `google_cloud_storage_project_id` (String): Google Cloud Project ID
* `backblaze_b2_bucket` (String): Backblaze B2 Cloud Storage Bucket name
* `backblaze_b2_s3_endpoint` (String): Backblaze B2 Cloud Storage S3 Endpoint
* `wasabi_bucket` (String): Wasabi region
* `wasabi_region` (String): Wasabi Bucket name
* `rackspace_username` (String): Rackspace username used to login to the Rackspace Cloud Control Panel.
* `rackspace_region` (String): Three letter airport code for Rackspace region. See https://support.rackspace.com/how-to/about-regions/
* `rackspace_container` (String): The name of the container (top level directory) where files will sync.
* `one_drive_account_type` (String): Either personal or business_other account types
* `azure_blob_storage_account` (String): Azure Blob Storage Account name
* `azure_blob_storage_container` (String): Azure Blob Storage Container name


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
* `aws_access_key` (String): AWS Access Key.
* `aws_secret_key` (String): AWS secret key.
* `password` (String): Password if needed.
* `private_key` (String): Private key if needed.
* `google_cloud_storage_credentials_json` (String): A JSON file that contains the private key. To generate see https://cloud.google.com/storage/docs/json_api/v1/how-tos/authorizing#APIKey
* `wasabi_access_key` (String): Wasabi access key.
* `wasabi_secret_key` (String): Wasabi secret key.
* `backblaze_b2_key_id` (String): Backblaze B2 Cloud Storage keyID.
* `backblaze_b2_application_key` (String): Backblaze B2 Cloud Storage applicationKey.
* `rackspace_api_key` (String): Rackspace API key from the Rackspace Cloud Control Panel.
* `reset_authentication` (Boolean): Reset authenticated account
* `azure_blob_storage_access_key` (String): Azure Blob Storage secret key.
* `hostname` (String): Hostname or IP address
* `name` (String): Internal name for your reference
* `max_connections` (Long): Max number of parallel connections.  Ignored for S3 connections (we will parallelize these as much as possible).
* `port` (Long): Port for remote server.  Not needed for S3.
* `s3_bucket` (String): S3 bucket name
* `s3_region` (String): S3 region
* `server_certificate` (String): Remote server certificate
* `server_host_key` (String): Remote server SSH Host Key. If provided, we will require that the server host key matches the provided key. Uses OpenSSH format similar to what would go into ~/.ssh/known_hosts
* `server_type` (String): Remote server type.
* `ssl` (String): Should we require SSL?
* `username` (String): Remote server username.  Not needed for S3 buckets.
* `google_cloud_storage_bucket` (String): Google Cloud Storage bucket name
* `google_cloud_storage_project_id` (String): Google Cloud Project ID
* `backblaze_b2_bucket` (String): Backblaze B2 Cloud Storage Bucket name
* `backblaze_b2_s3_endpoint` (String): Backblaze B2 Cloud Storage S3 Endpoint
* `wasabi_bucket` (String): Wasabi region
* `wasabi_region` (String): Wasabi Bucket name
* `rackspace_username` (String): Rackspace username used to login to the Rackspace Cloud Control Panel.
* `rackspace_region` (String): Three letter airport code for Rackspace region. See https://support.rackspace.com/how-to/about-regions/
* `rackspace_container` (String): The name of the container (top level directory) where files will sync.
* `one_drive_account_type` (String): Either personal or business_other account types
* `azure_blob_storage_account` (String): Azure Blob Storage Account name
* `azure_blob_storage_container` (String): Azure Blob Storage Container name


---

## Delete Remote Server

```
RemoteServer remoteServer = RemoteServer.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Remote Server ID.


---

## Update Remote Server

```
RemoteServer remoteServer = RemoteServer.ListFor(path)[0];

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("reset_authentication", true);
parameters.put("hostname", "remote-server.com");
parameters.put("name", "My Remote server");
parameters.put("max_connections", 1);
parameters.put("port", 1);
parameters.put("s3_bucket", "my-bucket");
parameters.put("s3_region", "us-east-1");
parameters.put("server_certificate", "require_match");
parameters.put("server_host_key", "[public key]");
parameters.put("server_type", "s3");
parameters.put("ssl", "if_available");
parameters.put("username", "user");
parameters.put("google_cloud_storage_bucket", "my-bucket");
parameters.put("google_cloud_storage_project_id", "my-project");
parameters.put("backblaze_b2_bucket", "my-bucket");
parameters.put("backblaze_b2_s3_endpoint", "s3.us-west-001.backblazeb2.com");
parameters.put("wasabi_bucket", "us-west-1");
parameters.put("wasabi_region", "my-bucket");
parameters.put("rackspace_username", "rackspaceuser");
parameters.put("rackspace_region", "dfw");
parameters.put("rackspace_container", "my-container");
parameters.put("one_drive_account_type", "personal");
parameters.put("azure_blob_storage_account", "storage-account-name");
parameters.put("azure_blob_storage_container", "container-name");

RemoteServer.Update(parameters);
```

### Parameters

* `id` (Long): Required - Remote Server ID.
* `aws_access_key` (String): AWS Access Key.
* `aws_secret_key` (String): AWS secret key.
* `password` (String): Password if needed.
* `private_key` (String): Private key if needed.
* `google_cloud_storage_credentials_json` (String): A JSON file that contains the private key. To generate see https://cloud.google.com/storage/docs/json_api/v1/how-tos/authorizing#APIKey
* `wasabi_access_key` (String): Wasabi access key.
* `wasabi_secret_key` (String): Wasabi secret key.
* `backblaze_b2_key_id` (String): Backblaze B2 Cloud Storage keyID.
* `backblaze_b2_application_key` (String): Backblaze B2 Cloud Storage applicationKey.
* `rackspace_api_key` (String): Rackspace API key from the Rackspace Cloud Control Panel.
* `reset_authentication` (Boolean): Reset authenticated account
* `azure_blob_storage_access_key` (String): Azure Blob Storage secret key.
* `hostname` (String): Hostname or IP address
* `name` (String): Internal name for your reference
* `max_connections` (Long): Max number of parallel connections.  Ignored for S3 connections (we will parallelize these as much as possible).
* `port` (Long): Port for remote server.  Not needed for S3.
* `s3_bucket` (String): S3 bucket name
* `s3_region` (String): S3 region
* `server_certificate` (String): Remote server certificate
* `server_host_key` (String): Remote server SSH Host Key. If provided, we will require that the server host key matches the provided key. Uses OpenSSH format similar to what would go into ~/.ssh/known_hosts
* `server_type` (String): Remote server type.
* `ssl` (String): Should we require SSL?
* `username` (String): Remote server username.  Not needed for S3 buckets.
* `google_cloud_storage_bucket` (String): Google Cloud Storage bucket name
* `google_cloud_storage_project_id` (String): Google Cloud Project ID
* `backblaze_b2_bucket` (String): Backblaze B2 Cloud Storage Bucket name
* `backblaze_b2_s3_endpoint` (String): Backblaze B2 Cloud Storage S3 Endpoint
* `wasabi_bucket` (String): Wasabi region
* `wasabi_region` (String): Wasabi Bucket name
* `rackspace_username` (String): Rackspace username used to login to the Rackspace Cloud Control Panel.
* `rackspace_region` (String): Three letter airport code for Rackspace region. See https://support.rackspace.com/how-to/about-regions/
* `rackspace_container` (String): The name of the container (top level directory) where files will sync.
* `one_drive_account_type` (String): Either personal or business_other account types
* `azure_blob_storage_account` (String): Azure Blob Storage Account name
* `azure_blob_storage_container` (String): Azure Blob Storage Container name


---

## Delete Remote Server

```
RemoteServer remoteServer = RemoteServer.ListFor(path)[0];

HashMap<String, Object> parameters = new HashMap<>();


RemoteServer.Delete
```

### Parameters

* `id` (Long): Required - Remote Server ID.
