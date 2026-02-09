# Files.Models.RemoteServerCredential

## Example RemoteServerCredential Object

```
{
  "id": 1,
  "workspace_id": 1,
  "name": "My Credential",
  "description": "More information or notes about this credential.",
  "server_type": "s3",
  "aws_access_key": "example",
  "s3_assume_role_arn": "example",
  "s3_assume_role_duration_seconds": 1,
  "s3_assume_role_external_id": "example",
  "google_cloud_storage_s3_compatible_access_key": "example",
  "wasabi_access_key": "example",
  "s3_compatible_access_key": "example",
  "filebase_access_key": "example",
  "cloudflare_access_key": "example",
  "linode_access_key": "example",
  "username": "user"
}
```

* `id` / `id`  (int64): Remote Server Credential ID
* `workspace_id` / `workspaceId`  (int64): Workspace ID (0 for default workspace)
* `name` / `name`  (string): Internal name for your reference
* `description` / `description`  (string): Internal description for your reference
* `server_type` / `serverType`  (string): Remote server type.  Remote Server Credentials are only valid for a single type of Remote Server.
* `aws_access_key` / `awsAccessKey`  (string): AWS Access Key.
* `s3_assume_role_arn` / `s3AssumeRoleArn`  (string): AWS IAM Role ARN for AssumeRole authentication.
* `s3_assume_role_duration_seconds` / `s3AssumeRoleDurationSeconds`  (int64): Session duration in seconds for AssumeRole authentication (900-43200).
* `s3_assume_role_external_id` / `s3AssumeRoleExternalId`  (string): External ID for AssumeRole authentication.
* `google_cloud_storage_s3_compatible_access_key` / `googleCloudStorageS3CompatibleAccessKey`  (string): Google Cloud Storage: S3-compatible Access Key.
* `wasabi_access_key` / `wasabiAccessKey`  (string): Wasabi: Access Key.
* `s3_compatible_access_key` / `s3CompatibleAccessKey`  (string): S3-compatible: Access Key
* `filebase_access_key` / `filebaseAccessKey`  (string): Filebase: Access Key.
* `cloudflare_access_key` / `cloudflareAccessKey`  (string): Cloudflare: Access Key.
* `linode_access_key` / `linodeAccessKey`  (string): Linode: Access Key
* `username` / `username`  (string): Remote server username.
* `password` / `password`  (string): Password, if needed.
* `private_key` / `privateKey`  (string): Private key, if needed.
* `private_key_passphrase` / `privateKeyPassphrase`  (string): Passphrase for private key if needed.
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

## List Remote Server Credentials

```
ListIterator<RemoteServerCredential> remoteServerCredential = RemoteServerCredential.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id` and `id`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id` and `name`. Valid field combinations are `[ workspace_id, name ]`.
* `filter_prefix` (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `name`.


---

## Show Remote Server Credential

```
RemoteServerCredential remoteServerCredential = RemoteServerCredential.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Remote Server Credential ID.


---

## Create Remote Server Credential

```
RemoteServerCredential remoteServerCredential = RemoteServerCredential.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `name` (String): Internal name for your reference
* `description` (String): Internal description for your reference
* `server_type` (String): Remote server type.  Remote Server Credentials are only valid for a single type of Remote Server.
* `aws_access_key` (String): AWS Access Key.
* `s3_assume_role_arn` (String): AWS IAM Role ARN for AssumeRole authentication.
* `s3_assume_role_duration_seconds` (Long): Session duration in seconds for AssumeRole authentication (900-43200).
* `cloudflare_access_key` (String): Cloudflare: Access Key.
* `filebase_access_key` (String): Filebase: Access Key.
* `google_cloud_storage_s3_compatible_access_key` (String): Google Cloud Storage: S3-compatible Access Key.
* `linode_access_key` (String): Linode: Access Key
* `s3_compatible_access_key` (String): S3-compatible: Access Key
* `username` (String): Remote server username.
* `wasabi_access_key` (String): Wasabi: Access Key.
* `password` (String): Password, if needed.
* `private_key` (String): Private key, if needed.
* `private_key_passphrase` (String): Passphrase for private key if needed.
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
* `workspace_id` (Long): Workspace ID (0 for default workspace)


---

## Update Remote Server Credential

```
RemoteServerCredential remoteServerCredential = RemoteServerCredential.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Remote Server Credential ID.
* `name` (String): Internal name for your reference
* `description` (String): Internal description for your reference
* `server_type` (String): Remote server type.  Remote Server Credentials are only valid for a single type of Remote Server.
* `aws_access_key` (String): AWS Access Key.
* `s3_assume_role_arn` (String): AWS IAM Role ARN for AssumeRole authentication.
* `s3_assume_role_duration_seconds` (Long): Session duration in seconds for AssumeRole authentication (900-43200).
* `cloudflare_access_key` (String): Cloudflare: Access Key.
* `filebase_access_key` (String): Filebase: Access Key.
* `google_cloud_storage_s3_compatible_access_key` (String): Google Cloud Storage: S3-compatible Access Key.
* `linode_access_key` (String): Linode: Access Key
* `s3_compatible_access_key` (String): S3-compatible: Access Key
* `username` (String): Remote server username.
* `wasabi_access_key` (String): Wasabi: Access Key.
* `password` (String): Password, if needed.
* `private_key` (String): Private key, if needed.
* `private_key_passphrase` (String): Passphrase for private key if needed.
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


---

## Delete Remote Server Credential

```
void remoteServerCredential = RemoteServerCredential.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Remote Server Credential ID.


---

## Update Remote Server Credential

```
RemoteServerCredential remoteServerCredential = RemoteServerCredential.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "My Credential");
parameters.put("description", "More information or notes about this credential.");
parameters.put("server_type", "s3");
parameters.put("aws_access_key", "example");
parameters.put("s3_assume_role_arn", "example");
parameters.put("s3_assume_role_duration_seconds", 1);
parameters.put("cloudflare_access_key", "example");
parameters.put("filebase_access_key", "example");
parameters.put("google_cloud_storage_s3_compatible_access_key", "example");
parameters.put("linode_access_key", "example");
parameters.put("s3_compatible_access_key", "example");
parameters.put("username", "user");
parameters.put("wasabi_access_key", "example");

remoteServerCredential.update(parameters);
```

### Parameters

* `id` (Long): Required - Remote Server Credential ID.
* `name` (String): Internal name for your reference
* `description` (String): Internal description for your reference
* `server_type` (String): Remote server type.  Remote Server Credentials are only valid for a single type of Remote Server.
* `aws_access_key` (String): AWS Access Key.
* `s3_assume_role_arn` (String): AWS IAM Role ARN for AssumeRole authentication.
* `s3_assume_role_duration_seconds` (Long): Session duration in seconds for AssumeRole authentication (900-43200).
* `cloudflare_access_key` (String): Cloudflare: Access Key.
* `filebase_access_key` (String): Filebase: Access Key.
* `google_cloud_storage_s3_compatible_access_key` (String): Google Cloud Storage: S3-compatible Access Key.
* `linode_access_key` (String): Linode: Access Key
* `s3_compatible_access_key` (String): S3-compatible: Access Key
* `username` (String): Remote server username.
* `wasabi_access_key` (String): Wasabi: Access Key.
* `password` (String): Password, if needed.
* `private_key` (String): Private key, if needed.
* `private_key_passphrase` (String): Passphrase for private key if needed.
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


---

## Delete Remote Server Credential

```
RemoteServerCredential remoteServerCredential = RemoteServerCredential.find(id);

HashMap<String, Object> parameters = new HashMap<>();

remoteServerCredential.delete(parameters);
```

### Parameters

* `id` (Long): Required - Remote Server Credential ID.
