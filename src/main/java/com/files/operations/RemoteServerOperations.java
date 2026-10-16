package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.AgentNode;
import com.files.models.AgentPushUpdate;
import com.files.models.RemoteServer;
import com.files.models.RemoteServerConfigurationFile;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RemoteServer operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#remoteServers()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.RemoteServer} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class RemoteServerOperations extends ResourceOperations {
  public RemoteServerOperations(FilesClient client) {
    super(client);
  }

  /**
   * Parameters:
   *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `name`, `server_type`, `backblaze_b2_bucket`, `google_cloud_storage_bucket`, `wasabi_bucket`, `s3_bucket`, `azure_blob_storage_container`, `azure_files_storage_share_name`, `s3_compatible_bucket`, `filebase_bucket`, `cloudflare_bucket` or `linode_bucket`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `name`, `server_type`, `workspace_id`, `backblaze_b2_bucket`, `google_cloud_storage_bucket`, `wasabi_bucket`, `s3_bucket`, `azure_blob_storage_container`, `azure_files_storage_share_name`, `s3_compatible_bucket`, `filebase_bucket`, `cloudflare_bucket` or `linode_bucket`. Valid field combinations are `[ server_type, name ]`, `[ workspace_id, name ]`, `[ backblaze_b2_bucket, name ]`, `[ google_cloud_storage_bucket, name ]`, `[ wasabi_bucket, name ]`, `[ s3_bucket, name ]`, `[ azure_blob_storage_container, name ]`, `[ azure_files_storage_share_name, name ]`, `[ s3_compatible_bucket, name ]`, `[ filebase_bucket, name ]`, `[ cloudflare_bucket, name ]`, `[ linode_bucket, name ]`, `[ workspace_id, server_type ]` or `[ workspace_id, server_type, name ]`.
   *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `name`, `backblaze_b2_bucket`, `google_cloud_storage_bucket`, `wasabi_bucket`, `s3_bucket`, `azure_blob_storage_container`, `azure_files_storage_share_name`, `s3_compatible_bucket`, `filebase_bucket`, `cloudflare_bucket` or `linode_bucket`. Valid field combinations are `[ backblaze_b2_bucket, name ]`, `[ google_cloud_storage_bucket, name ]`, `[ wasabi_bucket, name ]`, `[ s3_bucket, name ]`, `[ azure_blob_storage_container, name ]`, `[ azure_files_storage_share_name, name ]`, `[ s3_compatible_bucket, name ]`, `[ filebase_bucket, name ]`, `[ cloudflare_bucket, name ]` or `[ linode_bucket, name ]`.
   */
  public ListIterator<RemoteServer> list() throws RuntimeException {
    return list(null, null);
  }

  public ListIterator<RemoteServer> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  public ListIterator<RemoteServer> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }

    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Object parameters[\"sort_by\"]");
    }

    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Object parameters[\"filter\"]");
    }

    if (parameters.containsKey("filter_prefix") && !(parameters.get("filter_prefix") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_prefix must be of type Object parameters[\"filter_prefix\"]");
    }

    String apiPath = "/remote_servers";
    TypeReference<List<RemoteServer>> typeReference = new TypeReference<List<RemoteServer>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public ListIterator<RemoteServer> all() throws RuntimeException {
    return all(null, null);
  }

  public ListIterator<RemoteServer> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Remote Server ID.
   */
  public RemoteServer find() throws RuntimeException {
    return find(null, null, null);
  }

  public RemoteServer find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public RemoteServer find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public RemoteServer find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }

    String apiPath = String.format("/remote_servers/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<RemoteServer> typeReference = new TypeReference<RemoteServer>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public RemoteServer get() throws RuntimeException {
    return get(null, null, null);
  }

  public RemoteServer get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * List Files.com Agent nodes
   */
  public AgentNode agentNodes() throws RuntimeException {
    return agentNodes(null, null, null);
  }

  public AgentNode agentNodes(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return agentNodes(id, parameters, null);
  }

  public AgentNode agentNodes(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return agentNodes(null, parameters, options);
  }

  public AgentNode agentNodes(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }

    String apiPath = String.format("/remote_servers/%s/agent_nodes", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<AgentNode> typeReference = new TypeReference<AgentNode>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Remote Server ID.
   */
  public RemoteServerConfigurationFile findConfigurationFile() throws RuntimeException {
    return findConfigurationFile(null, null, null);
  }

  public RemoteServerConfigurationFile findConfigurationFile(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return findConfigurationFile(id, parameters, null);
  }

  public RemoteServerConfigurationFile findConfigurationFile(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return findConfigurationFile(null, parameters, options);
  }

  public RemoteServerConfigurationFile findConfigurationFile(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }

    String apiPath = String.format("/remote_servers/%s/configuration_file", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<RemoteServerConfigurationFile> typeReference = new TypeReference<RemoteServerConfigurationFile>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Parameters:
   *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
   *   password - string - Password, if needed.
   *   private_key - string - Private key, if needed.
   *   private_key_passphrase - string - Passphrase for private key if needed.
   *   reset_authentication - boolean - Reset authenticated account?
   *   sharepoint_client_certificate - string - SharePoint: PEM-encoded certificate and unencrypted private key for app-only authentication.
   *   sharepoint_client_secret - string - SharePoint: Microsoft Entra application client secret for app-only authentication.
   *   ssl_certificate - string - SSL client certificate.
   *   aws_secret_key - string - AWS: secret key.
   *   azure_blob_storage_access_key - string - Azure Blob Storage: Access Key
   *   azure_blob_storage_sas_token - string - Azure Blob Storage: Shared Access Signature (SAS) token
   *   azure_files_storage_access_key - string - Azure File Storage: Access Key
   *   azure_files_storage_sas_token - string - Azure File Storage: Shared Access Signature (SAS) token
   *   backblaze_b2_application_key - string - Backblaze B2 Cloud Storage: applicationKey
   *   backblaze_b2_key_id - string - Backblaze B2 Cloud Storage: keyID
   *   cloudflare_secret_key - string - Cloudflare: Secret Key
   *   filebase_secret_key - string - Filebase: Secret Key
   *   google_cloud_storage_credentials_json - string - Google Cloud Storage: JSON file that contains the private key. To generate see https://cloud.google.com/storage/docs/json_api/v1/how-tos/authorizing#APIKey
   *   google_cloud_storage_s3_compatible_secret_key - string - Google Cloud Storage: S3-compatible secret key
   *   linode_secret_key - string - Linode: Secret Key
   *   s3_compatible_secret_key - string - S3-compatible: Secret Key
   *   wasabi_secret_key - string - Wasabi: Secret Key
   *   allow_relative_paths - boolean - Allow relative paths in SFTP. If true, paths will not be forced to be absolute, allowing operations relative to the user's home directory.
   *   aws_access_key - string - AWS Access Key.
   *   azure_blob_storage_account - string - Azure Blob Storage: Account name
   *   azure_blob_storage_container - string - Azure Blob Storage: Container name
   *   azure_blob_storage_dns_suffix - string - Azure Blob Storage: Custom DNS suffix
   *   azure_blob_storage_hierarchical_namespace - boolean - Azure Blob Storage: Does the storage account has hierarchical namespace feature enabled?
   *   azure_files_storage_account - string - Azure Files: Storage Account name
   *   azure_files_storage_dns_suffix - string - Azure Files: Custom DNS suffix
   *   azure_files_storage_share_name - string - Azure Files:  Storage Share name
   *   backblaze_b2_bucket - string - Backblaze B2 Cloud Storage: Bucket name
   *   backblaze_b2_s3_endpoint - string - Backblaze B2 Cloud Storage: S3 Endpoint
   *   buffer_uploads - string - If set to always, uploads to this server will be uploaded first to Files.com before being sent to the remote server. This can improve performance in certain access patterns, such as high-latency connections.  It will cause data to be temporarily stored in Files.com. If set to auto, we will perform this optimization if we believe it to be a benefit in a given situation.
   *   cloudflare_access_key - string - Cloudflare: Access Key.
   *   cloudflare_bucket - string - Cloudflare: Bucket name
   *   cloudflare_endpoint - string - Cloudflare: endpoint
   *   description - string - Internal description for your reference
   *   dropbox_teams - boolean - Dropbox: If true, list Team folders in root?
   *   enable_dedicated_ips - boolean - `true` if remote server only accepts connections from dedicated IPs
   *   filebase_access_key - string - Filebase: Access Key.
   *   filebase_bucket - string - Filebase: Bucket name
   *   files_api_key - string - Files.com direct link: API key used once to pair the remote server.
   *   files_agent_permission_set - string - Agent file permissions: `read_only`, `write_only`, or `read_write`. Files.com writes this value as `permission_set` in the configuration file generated for this Remote Server. Setting it on Create or Update changes the generated file, not a running Agent. The Agent enforces the permissions in its local configuration file, controlled by the host's system administrator, and reports them at registration, replacing the stored value.
   *   files_agent_root - string - Agent local root path. Files.com writes this value as `root` in the configuration file generated for this Remote Server. Setting it on Create or Update changes the generated file, not a running Agent. The Agent enforces the root in its local configuration file, controlled by the host's system administrator, and reports it at registration, replacing the stored value.
   *   files_agent_version - string - Files.com Agent version reported by the Agent at each registration, replacing the stored value. This field is not included in the generated Agent configuration file. Setting it on Create or Update does not update the running Agent.
   *   outbound_agent_id - int64 - Route traffic to outbound on a files-agent
   *   custom_domain_id - int64 - Custom Domain ID whose dedicated IP addresses are selected when this Remote Server uses dedicated IPs. Must be available to this Remote Server's workspace. Requires enable_dedicated_ips and cannot be combined with an outbound Agent. Set to null to use the site's default dedicated IPs.
   *   google_cloud_storage_authentication_method - string - Google Cloud Storage: Authentication method. Can be json, hmac, or oauth.
   *   google_cloud_storage_bucket - string - Google Cloud Storage: Bucket Name
   *   google_cloud_storage_oauth_scope - string - Google Cloud Storage: OAuth scope. Can be https://www.googleapis.com/auth/devstorage.read_only or https://www.googleapis.com/auth/devstorage.read_write.
   *   google_cloud_storage_project_id - string - Google Cloud Storage: Project ID
   *   google_cloud_storage_s3_compatible_access_key - string - Google Cloud Storage: S3-compatible Access Key.
   *   hostname - string - Hostname or IP address. For Agent Remote Servers, the Agent reports the hostname it connects from at each registration, replacing the stored value. This field is not included in the generated Agent configuration file. Setting it on Create or Update does not change the running Agent.
   *   linode_access_key - string - Linode: Access Key
   *   linode_bucket - string - Linode: Bucket name
   *   linode_region - string - Linode: region
   *   max_connections - int64 - Max number of parallel connections.  Ignored for S3 connections (we will parallelize these as much as possible).
   *   name - string - Internal name for your reference
   *   one_drive_account_type - string - OneDrive: Either personal or business_other account types
   *   pin_to_site_region - boolean - If true, we will ensure that all communications with this remote server are made through the primary region of the site.  This setting can also be overridden by a site-wide setting which will force it to true.
   *   port - int64 - Port for remote server. For Agent Remote Servers, the Agent reports its downstream proxy port at each registration, replacing the stored value. This field is not included in the generated Agent configuration file. Setting it on Create or Update does not change the running Agent.
   *   upload_staging_path - string - Upload staging path.  Applies to SFTP only.  If a path is provided here, files will first be uploaded to this path on the remote folder and the moved into the final correct path via an SFTP move command.  This is required by some remote MFT systems to emulate atomic uploads, which are otherwise not supoprted by SFTP.
   *   remote_server_credential_id - int64 - ID of Remote Server Credential, if applicable.
   *   s3_assume_role_arn - string - AWS IAM Role ARN for AssumeRole authentication.
   *   s3_assume_role_duration_seconds - int64 - Session duration in seconds for AssumeRole authentication (900-43200).
   *   s3_bucket - string - S3 bucket name
   *   s3_compatible_access_key - string - S3-compatible: Access Key
   *   s3_compatible_bucket - string - S3-compatible: Bucket name
   *   s3_compatible_endpoint - string - S3-compatible: endpoint
   *   s3_compatible_region - string - S3-compatible: region
   *   s3_compatible_virtual_hosted_style - boolean - S3-compatible: If true, use virtual-hosted-style URLs instead of path-style URLs
   *   s3_kms_key_id - string - ARN of the AWS KMS key that encrypts files written to the bucket (SSE-KMS). Leave blank unless your bucket requires a specific key.
   *   s3_region - string - S3 region
   *   server_certificate - string - Remote server certificate
   *   server_host_key - string - Pinned SSH host key or OpenSSH host certificate for SFTP. If omitted, Files.com detects and stores a host key, preferring plain keys over certificates. With `server_certificate=require_match` (the default), the server must present the exact pinned key or certificate and prove it holds the matching private key. A pinned certificate is compared in full, so renewal can require updating `server_host_key` even when its underlying key is unchanged. Files.com does not check certificate CA signatures, principals, or validity periods. Certificate expiration alone does not end the pin. Update `server_host_key` to replace the pin.
   *   server_type - string - Remote server type.
   *   sharepoint_client_id - string - SharePoint: Microsoft Entra application client ID for app-only authentication.
   *   sharepoint_site_url - string - SharePoint: Site URL to scope app-only authentication to a single site. Leave blank to browse all sites.
   *   sharepoint_tenant_id - string - SharePoint: Microsoft Entra tenant ID for app-only authentication.
   *   ssl - string - Should we require SSL?
   *   username - string - Remote server username.
   *   wasabi_access_key - string - Wasabi: Access Key.
   *   wasabi_bucket - string - Wasabi: Bucket name
   *   wasabi_region - string - Wasabi: Region
   *   workspace_id - int64 - Workspace ID (0 for default workspace)
   */
  public RemoteServer create() throws RuntimeException {
    return create(null, null);
  }

  public RemoteServer create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  public RemoteServer create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }

    if (parameters.containsKey("password") && !(parameters.get("password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: password must be of type String parameters[\"password\"]");
    }

    if (parameters.containsKey("private_key") && !(parameters.get("private_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: private_key must be of type String parameters[\"private_key\"]");
    }

    if (parameters.containsKey("private_key_passphrase") && !(parameters.get("private_key_passphrase") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: private_key_passphrase must be of type String parameters[\"private_key_passphrase\"]");
    }

    if (parameters.containsKey("reset_authentication") && !(parameters.get("reset_authentication") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: reset_authentication must be of type Boolean parameters[\"reset_authentication\"]");
    }

    if (parameters.containsKey("sharepoint_client_certificate") && !(parameters.get("sharepoint_client_certificate") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: sharepoint_client_certificate must be of type String parameters[\"sharepoint_client_certificate\"]");
    }

    if (parameters.containsKey("sharepoint_client_secret") && !(parameters.get("sharepoint_client_secret") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: sharepoint_client_secret must be of type String parameters[\"sharepoint_client_secret\"]");
    }

    if (parameters.containsKey("ssl_certificate") && !(parameters.get("ssl_certificate") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: ssl_certificate must be of type String parameters[\"ssl_certificate\"]");
    }

    if (parameters.containsKey("aws_secret_key") && !(parameters.get("aws_secret_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: aws_secret_key must be of type String parameters[\"aws_secret_key\"]");
    }

    if (parameters.containsKey("azure_blob_storage_access_key") && !(parameters.get("azure_blob_storage_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_blob_storage_access_key must be of type String parameters[\"azure_blob_storage_access_key\"]");
    }

    if (parameters.containsKey("azure_blob_storage_sas_token") && !(parameters.get("azure_blob_storage_sas_token") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_blob_storage_sas_token must be of type String parameters[\"azure_blob_storage_sas_token\"]");
    }

    if (parameters.containsKey("azure_files_storage_access_key") && !(parameters.get("azure_files_storage_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_files_storage_access_key must be of type String parameters[\"azure_files_storage_access_key\"]");
    }

    if (parameters.containsKey("azure_files_storage_sas_token") && !(parameters.get("azure_files_storage_sas_token") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_files_storage_sas_token must be of type String parameters[\"azure_files_storage_sas_token\"]");
    }

    if (parameters.containsKey("backblaze_b2_application_key") && !(parameters.get("backblaze_b2_application_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: backblaze_b2_application_key must be of type String parameters[\"backblaze_b2_application_key\"]");
    }

    if (parameters.containsKey("backblaze_b2_key_id") && !(parameters.get("backblaze_b2_key_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: backblaze_b2_key_id must be of type String parameters[\"backblaze_b2_key_id\"]");
    }

    if (parameters.containsKey("cloudflare_secret_key") && !(parameters.get("cloudflare_secret_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cloudflare_secret_key must be of type String parameters[\"cloudflare_secret_key\"]");
    }

    if (parameters.containsKey("filebase_secret_key") && !(parameters.get("filebase_secret_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: filebase_secret_key must be of type String parameters[\"filebase_secret_key\"]");
    }

    if (parameters.containsKey("google_cloud_storage_credentials_json") && !(parameters.get("google_cloud_storage_credentials_json") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: google_cloud_storage_credentials_json must be of type String parameters[\"google_cloud_storage_credentials_json\"]");
    }

    if (parameters.containsKey("google_cloud_storage_s3_compatible_secret_key") && !(parameters.get("google_cloud_storage_s3_compatible_secret_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: google_cloud_storage_s3_compatible_secret_key must be of type String parameters[\"google_cloud_storage_s3_compatible_secret_key\"]");
    }

    if (parameters.containsKey("linode_secret_key") && !(parameters.get("linode_secret_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: linode_secret_key must be of type String parameters[\"linode_secret_key\"]");
    }

    if (parameters.containsKey("s3_compatible_secret_key") && !(parameters.get("s3_compatible_secret_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: s3_compatible_secret_key must be of type String parameters[\"s3_compatible_secret_key\"]");
    }

    if (parameters.containsKey("wasabi_secret_key") && !(parameters.get("wasabi_secret_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: wasabi_secret_key must be of type String parameters[\"wasabi_secret_key\"]");
    }

    if (parameters.containsKey("allow_relative_paths") && !(parameters.get("allow_relative_paths") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: allow_relative_paths must be of type Boolean parameters[\"allow_relative_paths\"]");
    }

    if (parameters.containsKey("aws_access_key") && !(parameters.get("aws_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: aws_access_key must be of type String parameters[\"aws_access_key\"]");
    }

    if (parameters.containsKey("azure_blob_storage_account") && !(parameters.get("azure_blob_storage_account") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_blob_storage_account must be of type String parameters[\"azure_blob_storage_account\"]");
    }

    if (parameters.containsKey("azure_blob_storage_container") && !(parameters.get("azure_blob_storage_container") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_blob_storage_container must be of type String parameters[\"azure_blob_storage_container\"]");
    }

    if (parameters.containsKey("azure_blob_storage_dns_suffix") && !(parameters.get("azure_blob_storage_dns_suffix") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_blob_storage_dns_suffix must be of type String parameters[\"azure_blob_storage_dns_suffix\"]");
    }

    if (parameters.containsKey("azure_blob_storage_hierarchical_namespace") && !(parameters.get("azure_blob_storage_hierarchical_namespace") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: azure_blob_storage_hierarchical_namespace must be of type Boolean parameters[\"azure_blob_storage_hierarchical_namespace\"]");
    }

    if (parameters.containsKey("azure_files_storage_account") && !(parameters.get("azure_files_storage_account") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_files_storage_account must be of type String parameters[\"azure_files_storage_account\"]");
    }

    if (parameters.containsKey("azure_files_storage_dns_suffix") && !(parameters.get("azure_files_storage_dns_suffix") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_files_storage_dns_suffix must be of type String parameters[\"azure_files_storage_dns_suffix\"]");
    }

    if (parameters.containsKey("azure_files_storage_share_name") && !(parameters.get("azure_files_storage_share_name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_files_storage_share_name must be of type String parameters[\"azure_files_storage_share_name\"]");
    }

    if (parameters.containsKey("backblaze_b2_bucket") && !(parameters.get("backblaze_b2_bucket") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: backblaze_b2_bucket must be of type String parameters[\"backblaze_b2_bucket\"]");
    }

    if (parameters.containsKey("backblaze_b2_s3_endpoint") && !(parameters.get("backblaze_b2_s3_endpoint") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: backblaze_b2_s3_endpoint must be of type String parameters[\"backblaze_b2_s3_endpoint\"]");
    }

    if (parameters.containsKey("buffer_uploads") && !(parameters.get("buffer_uploads") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: buffer_uploads must be of type String parameters[\"buffer_uploads\"]");
    }

    if (parameters.containsKey("cloudflare_access_key") && !(parameters.get("cloudflare_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cloudflare_access_key must be of type String parameters[\"cloudflare_access_key\"]");
    }

    if (parameters.containsKey("cloudflare_bucket") && !(parameters.get("cloudflare_bucket") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cloudflare_bucket must be of type String parameters[\"cloudflare_bucket\"]");
    }

    if (parameters.containsKey("cloudflare_endpoint") && !(parameters.get("cloudflare_endpoint") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cloudflare_endpoint must be of type String parameters[\"cloudflare_endpoint\"]");
    }

    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }

    if (parameters.containsKey("dropbox_teams") && !(parameters.get("dropbox_teams") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: dropbox_teams must be of type Boolean parameters[\"dropbox_teams\"]");
    }

    if (parameters.containsKey("enable_dedicated_ips") && !(parameters.get("enable_dedicated_ips") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: enable_dedicated_ips must be of type Boolean parameters[\"enable_dedicated_ips\"]");
    }

    if (parameters.containsKey("filebase_access_key") && !(parameters.get("filebase_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: filebase_access_key must be of type String parameters[\"filebase_access_key\"]");
    }

    if (parameters.containsKey("filebase_bucket") && !(parameters.get("filebase_bucket") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: filebase_bucket must be of type String parameters[\"filebase_bucket\"]");
    }

    if (parameters.containsKey("files_api_key") && !(parameters.get("files_api_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: files_api_key must be of type String parameters[\"files_api_key\"]");
    }

    if (parameters.containsKey("files_agent_permission_set") && !(parameters.get("files_agent_permission_set") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: files_agent_permission_set must be of type String parameters[\"files_agent_permission_set\"]");
    }

    if (parameters.containsKey("files_agent_root") && !(parameters.get("files_agent_root") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: files_agent_root must be of type String parameters[\"files_agent_root\"]");
    }

    if (parameters.containsKey("files_agent_version") && !(parameters.get("files_agent_version") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: files_agent_version must be of type String parameters[\"files_agent_version\"]");
    }

    if (parameters.containsKey("outbound_agent_id") && !(parameters.get("outbound_agent_id") instanceof Long || parameters.get("outbound_agent_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: outbound_agent_id must be of type Long or Integer parameters[\"outbound_agent_id\"]");
    }

    if (parameters.containsKey("custom_domain_id") && !(parameters.get("custom_domain_id") instanceof Long || parameters.get("custom_domain_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: custom_domain_id must be of type Long or Integer parameters[\"custom_domain_id\"]");
    }

    if (parameters.containsKey("google_cloud_storage_authentication_method") && !(parameters.get("google_cloud_storage_authentication_method") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: google_cloud_storage_authentication_method must be of type String parameters[\"google_cloud_storage_authentication_method\"]");
    }

    if (parameters.containsKey("google_cloud_storage_bucket") && !(parameters.get("google_cloud_storage_bucket") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: google_cloud_storage_bucket must be of type String parameters[\"google_cloud_storage_bucket\"]");
    }

    if (parameters.containsKey("google_cloud_storage_oauth_scope") && !(parameters.get("google_cloud_storage_oauth_scope") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: google_cloud_storage_oauth_scope must be of type String parameters[\"google_cloud_storage_oauth_scope\"]");
    }

    if (parameters.containsKey("google_cloud_storage_project_id") && !(parameters.get("google_cloud_storage_project_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: google_cloud_storage_project_id must be of type String parameters[\"google_cloud_storage_project_id\"]");
    }

    if (parameters.containsKey("google_cloud_storage_s3_compatible_access_key") && !(parameters.get("google_cloud_storage_s3_compatible_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: google_cloud_storage_s3_compatible_access_key must be of type String parameters[\"google_cloud_storage_s3_compatible_access_key\"]");
    }

    if (parameters.containsKey("hostname") && !(parameters.get("hostname") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: hostname must be of type String parameters[\"hostname\"]");
    }

    if (parameters.containsKey("linode_access_key") && !(parameters.get("linode_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: linode_access_key must be of type String parameters[\"linode_access_key\"]");
    }

    if (parameters.containsKey("linode_bucket") && !(parameters.get("linode_bucket") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: linode_bucket must be of type String parameters[\"linode_bucket\"]");
    }

    if (parameters.containsKey("linode_region") && !(parameters.get("linode_region") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: linode_region must be of type String parameters[\"linode_region\"]");
    }

    if (parameters.containsKey("max_connections") && !(parameters.get("max_connections") instanceof Long || parameters.get("max_connections") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: max_connections must be of type Long or Integer parameters[\"max_connections\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("one_drive_account_type") && !(parameters.get("one_drive_account_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: one_drive_account_type must be of type String parameters[\"one_drive_account_type\"]");
    }

    if (parameters.containsKey("pin_to_site_region") && !(parameters.get("pin_to_site_region") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: pin_to_site_region must be of type Boolean parameters[\"pin_to_site_region\"]");
    }

    if (parameters.containsKey("port") && !(parameters.get("port") instanceof Long || parameters.get("port") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: port must be of type Long or Integer parameters[\"port\"]");
    }

    if (parameters.containsKey("upload_staging_path") && !(parameters.get("upload_staging_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: upload_staging_path must be of type String parameters[\"upload_staging_path\"]");
    }

    if (parameters.containsKey("remote_server_credential_id") && !(parameters.get("remote_server_credential_id") instanceof Long || parameters.get("remote_server_credential_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: remote_server_credential_id must be of type Long or Integer parameters[\"remote_server_credential_id\"]");
    }

    if (parameters.containsKey("s3_assume_role_arn") && !(parameters.get("s3_assume_role_arn") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: s3_assume_role_arn must be of type String parameters[\"s3_assume_role_arn\"]");
    }

    if (parameters.containsKey("s3_assume_role_duration_seconds") && !(parameters.get("s3_assume_role_duration_seconds") instanceof Long || parameters.get("s3_assume_role_duration_seconds") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: s3_assume_role_duration_seconds must be of type Long or Integer parameters[\"s3_assume_role_duration_seconds\"]");
    }

    if (parameters.containsKey("s3_bucket") && !(parameters.get("s3_bucket") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: s3_bucket must be of type String parameters[\"s3_bucket\"]");
    }

    if (parameters.containsKey("s3_compatible_access_key") && !(parameters.get("s3_compatible_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: s3_compatible_access_key must be of type String parameters[\"s3_compatible_access_key\"]");
    }

    if (parameters.containsKey("s3_compatible_bucket") && !(parameters.get("s3_compatible_bucket") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: s3_compatible_bucket must be of type String parameters[\"s3_compatible_bucket\"]");
    }

    if (parameters.containsKey("s3_compatible_endpoint") && !(parameters.get("s3_compatible_endpoint") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: s3_compatible_endpoint must be of type String parameters[\"s3_compatible_endpoint\"]");
    }

    if (parameters.containsKey("s3_compatible_region") && !(parameters.get("s3_compatible_region") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: s3_compatible_region must be of type String parameters[\"s3_compatible_region\"]");
    }

    if (parameters.containsKey("s3_compatible_virtual_hosted_style") && !(parameters.get("s3_compatible_virtual_hosted_style") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: s3_compatible_virtual_hosted_style must be of type Boolean parameters[\"s3_compatible_virtual_hosted_style\"]");
    }

    if (parameters.containsKey("s3_kms_key_id") && !(parameters.get("s3_kms_key_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: s3_kms_key_id must be of type String parameters[\"s3_kms_key_id\"]");
    }

    if (parameters.containsKey("s3_region") && !(parameters.get("s3_region") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: s3_region must be of type String parameters[\"s3_region\"]");
    }

    if (parameters.containsKey("server_certificate") && !(parameters.get("server_certificate") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: server_certificate must be of type String parameters[\"server_certificate\"]");
    }

    if (parameters.containsKey("server_host_key") && !(parameters.get("server_host_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: server_host_key must be of type String parameters[\"server_host_key\"]");
    }

    if (parameters.containsKey("server_type") && !(parameters.get("server_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: server_type must be of type String parameters[\"server_type\"]");
    }

    if (parameters.containsKey("sharepoint_client_id") && !(parameters.get("sharepoint_client_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: sharepoint_client_id must be of type String parameters[\"sharepoint_client_id\"]");
    }

    if (parameters.containsKey("sharepoint_site_url") && !(parameters.get("sharepoint_site_url") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: sharepoint_site_url must be of type String parameters[\"sharepoint_site_url\"]");
    }

    if (parameters.containsKey("sharepoint_tenant_id") && !(parameters.get("sharepoint_tenant_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: sharepoint_tenant_id must be of type String parameters[\"sharepoint_tenant_id\"]");
    }

    if (parameters.containsKey("ssl") && !(parameters.get("ssl") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: ssl must be of type String parameters[\"ssl\"]");
    }

    if (parameters.containsKey("username") && !(parameters.get("username") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: username must be of type String parameters[\"username\"]");
    }

    if (parameters.containsKey("wasabi_access_key") && !(parameters.get("wasabi_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: wasabi_access_key must be of type String parameters[\"wasabi_access_key\"]");
    }

    if (parameters.containsKey("wasabi_bucket") && !(parameters.get("wasabi_bucket") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: wasabi_bucket must be of type String parameters[\"wasabi_bucket\"]");
    }

    if (parameters.containsKey("wasabi_region") && !(parameters.get("wasabi_region") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: wasabi_region must be of type String parameters[\"wasabi_region\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    String apiPath = "/remote_servers";
    TypeReference<RemoteServer> typeReference = new TypeReference<RemoteServer>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Push update to Files Agent
   */
  public AgentPushUpdate agentPushUpdate() throws RuntimeException {
    return agentPushUpdate(null, null, null);
  }

  public AgentPushUpdate agentPushUpdate(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return agentPushUpdate(id, parameters, null);
  }

  public AgentPushUpdate agentPushUpdate(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return agentPushUpdate(null, parameters, options);
  }

  public AgentPushUpdate agentPushUpdate(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }

    String apiPath = String.format("/remote_servers/%s/agent_push_update", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<AgentPushUpdate> typeReference = new TypeReference<AgentPushUpdate>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Parameters:
   *   password - string - Password, if needed.
   *   private_key - string - Private key, if needed.
   *   private_key_passphrase - string - Passphrase for private key if needed.
   *   reset_authentication - boolean - Reset authenticated account?
   *   sharepoint_client_certificate - string - SharePoint: PEM-encoded certificate and unencrypted private key for app-only authentication.
   *   sharepoint_client_secret - string - SharePoint: Microsoft Entra application client secret for app-only authentication.
   *   ssl_certificate - string - SSL client certificate.
   *   aws_secret_key - string - AWS: secret key.
   *   azure_blob_storage_access_key - string - Azure Blob Storage: Access Key
   *   azure_blob_storage_sas_token - string - Azure Blob Storage: Shared Access Signature (SAS) token
   *   azure_files_storage_access_key - string - Azure File Storage: Access Key
   *   azure_files_storage_sas_token - string - Azure File Storage: Shared Access Signature (SAS) token
   *   backblaze_b2_application_key - string - Backblaze B2 Cloud Storage: applicationKey
   *   backblaze_b2_key_id - string - Backblaze B2 Cloud Storage: keyID
   *   cloudflare_secret_key - string - Cloudflare: Secret Key
   *   filebase_secret_key - string - Filebase: Secret Key
   *   google_cloud_storage_credentials_json - string - Google Cloud Storage: JSON file that contains the private key. To generate see https://cloud.google.com/storage/docs/json_api/v1/how-tos/authorizing#APIKey
   *   google_cloud_storage_s3_compatible_secret_key - string - Google Cloud Storage: S3-compatible secret key
   *   linode_secret_key - string - Linode: Secret Key
   *   s3_compatible_secret_key - string - S3-compatible: Secret Key
   *   wasabi_secret_key - string - Wasabi: Secret Key
   *   allow_relative_paths - boolean - Allow relative paths in SFTP. If true, paths will not be forced to be absolute, allowing operations relative to the user's home directory.
   *   aws_access_key - string - AWS Access Key.
   *   azure_blob_storage_account - string - Azure Blob Storage: Account name
   *   azure_blob_storage_container - string - Azure Blob Storage: Container name
   *   azure_blob_storage_dns_suffix - string - Azure Blob Storage: Custom DNS suffix
   *   azure_blob_storage_hierarchical_namespace - boolean - Azure Blob Storage: Does the storage account has hierarchical namespace feature enabled?
   *   azure_files_storage_account - string - Azure Files: Storage Account name
   *   azure_files_storage_dns_suffix - string - Azure Files: Custom DNS suffix
   *   azure_files_storage_share_name - string - Azure Files:  Storage Share name
   *   backblaze_b2_bucket - string - Backblaze B2 Cloud Storage: Bucket name
   *   backblaze_b2_s3_endpoint - string - Backblaze B2 Cloud Storage: S3 Endpoint
   *   buffer_uploads - string - If set to always, uploads to this server will be uploaded first to Files.com before being sent to the remote server. This can improve performance in certain access patterns, such as high-latency connections.  It will cause data to be temporarily stored in Files.com. If set to auto, we will perform this optimization if we believe it to be a benefit in a given situation.
   *   cloudflare_access_key - string - Cloudflare: Access Key.
   *   cloudflare_bucket - string - Cloudflare: Bucket name
   *   cloudflare_endpoint - string - Cloudflare: endpoint
   *   description - string - Internal description for your reference
   *   dropbox_teams - boolean - Dropbox: If true, list Team folders in root?
   *   enable_dedicated_ips - boolean - `true` if remote server only accepts connections from dedicated IPs
   *   filebase_access_key - string - Filebase: Access Key.
   *   filebase_bucket - string - Filebase: Bucket name
   *   files_api_key - string - Files.com direct link: API key used once to pair the remote server.
   *   files_agent_permission_set - string - Agent file permissions: `read_only`, `write_only`, or `read_write`. Files.com writes this value as `permission_set` in the configuration file generated for this Remote Server. Setting it on Create or Update changes the generated file, not a running Agent. The Agent enforces the permissions in its local configuration file, controlled by the host's system administrator, and reports them at registration, replacing the stored value.
   *   files_agent_root - string - Agent local root path. Files.com writes this value as `root` in the configuration file generated for this Remote Server. Setting it on Create or Update changes the generated file, not a running Agent. The Agent enforces the root in its local configuration file, controlled by the host's system administrator, and reports it at registration, replacing the stored value.
   *   files_agent_version - string - Files.com Agent version reported by the Agent at each registration, replacing the stored value. This field is not included in the generated Agent configuration file. Setting it on Create or Update does not update the running Agent.
   *   outbound_agent_id - int64 - Route traffic to outbound on a files-agent
   *   custom_domain_id - int64 - Custom Domain ID whose dedicated IP addresses are selected when this Remote Server uses dedicated IPs. Must be available to this Remote Server's workspace. Requires enable_dedicated_ips and cannot be combined with an outbound Agent. Set to null to use the site's default dedicated IPs.
   *   google_cloud_storage_authentication_method - string - Google Cloud Storage: Authentication method. Can be json, hmac, or oauth.
   *   google_cloud_storage_bucket - string - Google Cloud Storage: Bucket Name
   *   google_cloud_storage_oauth_scope - string - Google Cloud Storage: OAuth scope. Can be https://www.googleapis.com/auth/devstorage.read_only or https://www.googleapis.com/auth/devstorage.read_write.
   *   google_cloud_storage_project_id - string - Google Cloud Storage: Project ID
   *   google_cloud_storage_s3_compatible_access_key - string - Google Cloud Storage: S3-compatible Access Key.
   *   hostname - string - Hostname or IP address. For Agent Remote Servers, the Agent reports the hostname it connects from at each registration, replacing the stored value. This field is not included in the generated Agent configuration file. Setting it on Create or Update does not change the running Agent.
   *   linode_access_key - string - Linode: Access Key
   *   linode_bucket - string - Linode: Bucket name
   *   linode_region - string - Linode: region
   *   max_connections - int64 - Max number of parallel connections.  Ignored for S3 connections (we will parallelize these as much as possible).
   *   name - string - Internal name for your reference
   *   one_drive_account_type - string - OneDrive: Either personal or business_other account types
   *   pin_to_site_region - boolean - If true, we will ensure that all communications with this remote server are made through the primary region of the site.  This setting can also be overridden by a site-wide setting which will force it to true.
   *   port - int64 - Port for remote server. For Agent Remote Servers, the Agent reports its downstream proxy port at each registration, replacing the stored value. This field is not included in the generated Agent configuration file. Setting it on Create or Update does not change the running Agent.
   *   upload_staging_path - string - Upload staging path.  Applies to SFTP only.  If a path is provided here, files will first be uploaded to this path on the remote folder and the moved into the final correct path via an SFTP move command.  This is required by some remote MFT systems to emulate atomic uploads, which are otherwise not supoprted by SFTP.
   *   remote_server_credential_id - int64 - ID of Remote Server Credential, if applicable.
   *   s3_assume_role_arn - string - AWS IAM Role ARN for AssumeRole authentication.
   *   s3_assume_role_duration_seconds - int64 - Session duration in seconds for AssumeRole authentication (900-43200).
   *   s3_bucket - string - S3 bucket name
   *   s3_compatible_access_key - string - S3-compatible: Access Key
   *   s3_compatible_bucket - string - S3-compatible: Bucket name
   *   s3_compatible_endpoint - string - S3-compatible: endpoint
   *   s3_compatible_region - string - S3-compatible: region
   *   s3_compatible_virtual_hosted_style - boolean - S3-compatible: If true, use virtual-hosted-style URLs instead of path-style URLs
   *   s3_kms_key_id - string - ARN of the AWS KMS key that encrypts files written to the bucket (SSE-KMS). Leave blank unless your bucket requires a specific key.
   *   s3_region - string - S3 region
   *   server_certificate - string - Remote server certificate
   *   server_host_key - string - Pinned SSH host key or OpenSSH host certificate for SFTP. If omitted, Files.com detects and stores a host key, preferring plain keys over certificates. With `server_certificate=require_match` (the default), the server must present the exact pinned key or certificate and prove it holds the matching private key. A pinned certificate is compared in full, so renewal can require updating `server_host_key` even when its underlying key is unchanged. Files.com does not check certificate CA signatures, principals, or validity periods. Certificate expiration alone does not end the pin. Update `server_host_key` to replace the pin.
   *   server_type - string - Remote server type.
   *   sharepoint_client_id - string - SharePoint: Microsoft Entra application client ID for app-only authentication.
   *   sharepoint_site_url - string - SharePoint: Site URL to scope app-only authentication to a single site. Leave blank to browse all sites.
   *   sharepoint_tenant_id - string - SharePoint: Microsoft Entra tenant ID for app-only authentication.
   *   ssl - string - Should we require SSL?
   *   username - string - Remote server username.
   *   wasabi_access_key - string - Wasabi: Access Key.
   *   wasabi_bucket - string - Wasabi: Bucket name
   *   wasabi_region - string - Wasabi: Region
   */
  public RemoteServer update() throws RuntimeException {
    return update(null, null, null);
  }

  public RemoteServer update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public RemoteServer update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public RemoteServer update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }

    if (parameters.containsKey("password") && !(parameters.get("password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: password must be of type String parameters[\"password\"]");
    }

    if (parameters.containsKey("private_key") && !(parameters.get("private_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: private_key must be of type String parameters[\"private_key\"]");
    }

    if (parameters.containsKey("private_key_passphrase") && !(parameters.get("private_key_passphrase") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: private_key_passphrase must be of type String parameters[\"private_key_passphrase\"]");
    }

    if (parameters.containsKey("reset_authentication") && !(parameters.get("reset_authentication") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: reset_authentication must be of type Boolean parameters[\"reset_authentication\"]");
    }

    if (parameters.containsKey("sharepoint_client_certificate") && !(parameters.get("sharepoint_client_certificate") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: sharepoint_client_certificate must be of type String parameters[\"sharepoint_client_certificate\"]");
    }

    if (parameters.containsKey("sharepoint_client_secret") && !(parameters.get("sharepoint_client_secret") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: sharepoint_client_secret must be of type String parameters[\"sharepoint_client_secret\"]");
    }

    if (parameters.containsKey("ssl_certificate") && !(parameters.get("ssl_certificate") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: ssl_certificate must be of type String parameters[\"ssl_certificate\"]");
    }

    if (parameters.containsKey("aws_secret_key") && !(parameters.get("aws_secret_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: aws_secret_key must be of type String parameters[\"aws_secret_key\"]");
    }

    if (parameters.containsKey("azure_blob_storage_access_key") && !(parameters.get("azure_blob_storage_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_blob_storage_access_key must be of type String parameters[\"azure_blob_storage_access_key\"]");
    }

    if (parameters.containsKey("azure_blob_storage_sas_token") && !(parameters.get("azure_blob_storage_sas_token") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_blob_storage_sas_token must be of type String parameters[\"azure_blob_storage_sas_token\"]");
    }

    if (parameters.containsKey("azure_files_storage_access_key") && !(parameters.get("azure_files_storage_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_files_storage_access_key must be of type String parameters[\"azure_files_storage_access_key\"]");
    }

    if (parameters.containsKey("azure_files_storage_sas_token") && !(parameters.get("azure_files_storage_sas_token") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_files_storage_sas_token must be of type String parameters[\"azure_files_storage_sas_token\"]");
    }

    if (parameters.containsKey("backblaze_b2_application_key") && !(parameters.get("backblaze_b2_application_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: backblaze_b2_application_key must be of type String parameters[\"backblaze_b2_application_key\"]");
    }

    if (parameters.containsKey("backblaze_b2_key_id") && !(parameters.get("backblaze_b2_key_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: backblaze_b2_key_id must be of type String parameters[\"backblaze_b2_key_id\"]");
    }

    if (parameters.containsKey("cloudflare_secret_key") && !(parameters.get("cloudflare_secret_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cloudflare_secret_key must be of type String parameters[\"cloudflare_secret_key\"]");
    }

    if (parameters.containsKey("filebase_secret_key") && !(parameters.get("filebase_secret_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: filebase_secret_key must be of type String parameters[\"filebase_secret_key\"]");
    }

    if (parameters.containsKey("google_cloud_storage_credentials_json") && !(parameters.get("google_cloud_storage_credentials_json") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: google_cloud_storage_credentials_json must be of type String parameters[\"google_cloud_storage_credentials_json\"]");
    }

    if (parameters.containsKey("google_cloud_storage_s3_compatible_secret_key") && !(parameters.get("google_cloud_storage_s3_compatible_secret_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: google_cloud_storage_s3_compatible_secret_key must be of type String parameters[\"google_cloud_storage_s3_compatible_secret_key\"]");
    }

    if (parameters.containsKey("linode_secret_key") && !(parameters.get("linode_secret_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: linode_secret_key must be of type String parameters[\"linode_secret_key\"]");
    }

    if (parameters.containsKey("s3_compatible_secret_key") && !(parameters.get("s3_compatible_secret_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: s3_compatible_secret_key must be of type String parameters[\"s3_compatible_secret_key\"]");
    }

    if (parameters.containsKey("wasabi_secret_key") && !(parameters.get("wasabi_secret_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: wasabi_secret_key must be of type String parameters[\"wasabi_secret_key\"]");
    }

    if (parameters.containsKey("allow_relative_paths") && !(parameters.get("allow_relative_paths") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: allow_relative_paths must be of type Boolean parameters[\"allow_relative_paths\"]");
    }

    if (parameters.containsKey("aws_access_key") && !(parameters.get("aws_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: aws_access_key must be of type String parameters[\"aws_access_key\"]");
    }

    if (parameters.containsKey("azure_blob_storage_account") && !(parameters.get("azure_blob_storage_account") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_blob_storage_account must be of type String parameters[\"azure_blob_storage_account\"]");
    }

    if (parameters.containsKey("azure_blob_storage_container") && !(parameters.get("azure_blob_storage_container") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_blob_storage_container must be of type String parameters[\"azure_blob_storage_container\"]");
    }

    if (parameters.containsKey("azure_blob_storage_dns_suffix") && !(parameters.get("azure_blob_storage_dns_suffix") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_blob_storage_dns_suffix must be of type String parameters[\"azure_blob_storage_dns_suffix\"]");
    }

    if (parameters.containsKey("azure_blob_storage_hierarchical_namespace") && !(parameters.get("azure_blob_storage_hierarchical_namespace") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: azure_blob_storage_hierarchical_namespace must be of type Boolean parameters[\"azure_blob_storage_hierarchical_namespace\"]");
    }

    if (parameters.containsKey("azure_files_storage_account") && !(parameters.get("azure_files_storage_account") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_files_storage_account must be of type String parameters[\"azure_files_storage_account\"]");
    }

    if (parameters.containsKey("azure_files_storage_dns_suffix") && !(parameters.get("azure_files_storage_dns_suffix") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_files_storage_dns_suffix must be of type String parameters[\"azure_files_storage_dns_suffix\"]");
    }

    if (parameters.containsKey("azure_files_storage_share_name") && !(parameters.get("azure_files_storage_share_name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_files_storage_share_name must be of type String parameters[\"azure_files_storage_share_name\"]");
    }

    if (parameters.containsKey("backblaze_b2_bucket") && !(parameters.get("backblaze_b2_bucket") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: backblaze_b2_bucket must be of type String parameters[\"backblaze_b2_bucket\"]");
    }

    if (parameters.containsKey("backblaze_b2_s3_endpoint") && !(parameters.get("backblaze_b2_s3_endpoint") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: backblaze_b2_s3_endpoint must be of type String parameters[\"backblaze_b2_s3_endpoint\"]");
    }

    if (parameters.containsKey("buffer_uploads") && !(parameters.get("buffer_uploads") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: buffer_uploads must be of type String parameters[\"buffer_uploads\"]");
    }

    if (parameters.containsKey("cloudflare_access_key") && !(parameters.get("cloudflare_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cloudflare_access_key must be of type String parameters[\"cloudflare_access_key\"]");
    }

    if (parameters.containsKey("cloudflare_bucket") && !(parameters.get("cloudflare_bucket") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cloudflare_bucket must be of type String parameters[\"cloudflare_bucket\"]");
    }

    if (parameters.containsKey("cloudflare_endpoint") && !(parameters.get("cloudflare_endpoint") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cloudflare_endpoint must be of type String parameters[\"cloudflare_endpoint\"]");
    }

    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }

    if (parameters.containsKey("dropbox_teams") && !(parameters.get("dropbox_teams") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: dropbox_teams must be of type Boolean parameters[\"dropbox_teams\"]");
    }

    if (parameters.containsKey("enable_dedicated_ips") && !(parameters.get("enable_dedicated_ips") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: enable_dedicated_ips must be of type Boolean parameters[\"enable_dedicated_ips\"]");
    }

    if (parameters.containsKey("filebase_access_key") && !(parameters.get("filebase_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: filebase_access_key must be of type String parameters[\"filebase_access_key\"]");
    }

    if (parameters.containsKey("filebase_bucket") && !(parameters.get("filebase_bucket") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: filebase_bucket must be of type String parameters[\"filebase_bucket\"]");
    }

    if (parameters.containsKey("files_api_key") && !(parameters.get("files_api_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: files_api_key must be of type String parameters[\"files_api_key\"]");
    }

    if (parameters.containsKey("files_agent_permission_set") && !(parameters.get("files_agent_permission_set") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: files_agent_permission_set must be of type String parameters[\"files_agent_permission_set\"]");
    }

    if (parameters.containsKey("files_agent_root") && !(parameters.get("files_agent_root") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: files_agent_root must be of type String parameters[\"files_agent_root\"]");
    }

    if (parameters.containsKey("files_agent_version") && !(parameters.get("files_agent_version") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: files_agent_version must be of type String parameters[\"files_agent_version\"]");
    }

    if (parameters.containsKey("outbound_agent_id") && !(parameters.get("outbound_agent_id") instanceof Long || parameters.get("outbound_agent_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: outbound_agent_id must be of type Long or Integer parameters[\"outbound_agent_id\"]");
    }

    if (parameters.containsKey("custom_domain_id") && !(parameters.get("custom_domain_id") instanceof Long || parameters.get("custom_domain_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: custom_domain_id must be of type Long or Integer parameters[\"custom_domain_id\"]");
    }

    if (parameters.containsKey("google_cloud_storage_authentication_method") && !(parameters.get("google_cloud_storage_authentication_method") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: google_cloud_storage_authentication_method must be of type String parameters[\"google_cloud_storage_authentication_method\"]");
    }

    if (parameters.containsKey("google_cloud_storage_bucket") && !(parameters.get("google_cloud_storage_bucket") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: google_cloud_storage_bucket must be of type String parameters[\"google_cloud_storage_bucket\"]");
    }

    if (parameters.containsKey("google_cloud_storage_oauth_scope") && !(parameters.get("google_cloud_storage_oauth_scope") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: google_cloud_storage_oauth_scope must be of type String parameters[\"google_cloud_storage_oauth_scope\"]");
    }

    if (parameters.containsKey("google_cloud_storage_project_id") && !(parameters.get("google_cloud_storage_project_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: google_cloud_storage_project_id must be of type String parameters[\"google_cloud_storage_project_id\"]");
    }

    if (parameters.containsKey("google_cloud_storage_s3_compatible_access_key") && !(parameters.get("google_cloud_storage_s3_compatible_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: google_cloud_storage_s3_compatible_access_key must be of type String parameters[\"google_cloud_storage_s3_compatible_access_key\"]");
    }

    if (parameters.containsKey("hostname") && !(parameters.get("hostname") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: hostname must be of type String parameters[\"hostname\"]");
    }

    if (parameters.containsKey("linode_access_key") && !(parameters.get("linode_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: linode_access_key must be of type String parameters[\"linode_access_key\"]");
    }

    if (parameters.containsKey("linode_bucket") && !(parameters.get("linode_bucket") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: linode_bucket must be of type String parameters[\"linode_bucket\"]");
    }

    if (parameters.containsKey("linode_region") && !(parameters.get("linode_region") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: linode_region must be of type String parameters[\"linode_region\"]");
    }

    if (parameters.containsKey("max_connections") && !(parameters.get("max_connections") instanceof Long || parameters.get("max_connections") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: max_connections must be of type Long or Integer parameters[\"max_connections\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("one_drive_account_type") && !(parameters.get("one_drive_account_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: one_drive_account_type must be of type String parameters[\"one_drive_account_type\"]");
    }

    if (parameters.containsKey("pin_to_site_region") && !(parameters.get("pin_to_site_region") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: pin_to_site_region must be of type Boolean parameters[\"pin_to_site_region\"]");
    }

    if (parameters.containsKey("port") && !(parameters.get("port") instanceof Long || parameters.get("port") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: port must be of type Long or Integer parameters[\"port\"]");
    }

    if (parameters.containsKey("upload_staging_path") && !(parameters.get("upload_staging_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: upload_staging_path must be of type String parameters[\"upload_staging_path\"]");
    }

    if (parameters.containsKey("remote_server_credential_id") && !(parameters.get("remote_server_credential_id") instanceof Long || parameters.get("remote_server_credential_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: remote_server_credential_id must be of type Long or Integer parameters[\"remote_server_credential_id\"]");
    }

    if (parameters.containsKey("s3_assume_role_arn") && !(parameters.get("s3_assume_role_arn") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: s3_assume_role_arn must be of type String parameters[\"s3_assume_role_arn\"]");
    }

    if (parameters.containsKey("s3_assume_role_duration_seconds") && !(parameters.get("s3_assume_role_duration_seconds") instanceof Long || parameters.get("s3_assume_role_duration_seconds") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: s3_assume_role_duration_seconds must be of type Long or Integer parameters[\"s3_assume_role_duration_seconds\"]");
    }

    if (parameters.containsKey("s3_bucket") && !(parameters.get("s3_bucket") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: s3_bucket must be of type String parameters[\"s3_bucket\"]");
    }

    if (parameters.containsKey("s3_compatible_access_key") && !(parameters.get("s3_compatible_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: s3_compatible_access_key must be of type String parameters[\"s3_compatible_access_key\"]");
    }

    if (parameters.containsKey("s3_compatible_bucket") && !(parameters.get("s3_compatible_bucket") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: s3_compatible_bucket must be of type String parameters[\"s3_compatible_bucket\"]");
    }

    if (parameters.containsKey("s3_compatible_endpoint") && !(parameters.get("s3_compatible_endpoint") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: s3_compatible_endpoint must be of type String parameters[\"s3_compatible_endpoint\"]");
    }

    if (parameters.containsKey("s3_compatible_region") && !(parameters.get("s3_compatible_region") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: s3_compatible_region must be of type String parameters[\"s3_compatible_region\"]");
    }

    if (parameters.containsKey("s3_compatible_virtual_hosted_style") && !(parameters.get("s3_compatible_virtual_hosted_style") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: s3_compatible_virtual_hosted_style must be of type Boolean parameters[\"s3_compatible_virtual_hosted_style\"]");
    }

    if (parameters.containsKey("s3_kms_key_id") && !(parameters.get("s3_kms_key_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: s3_kms_key_id must be of type String parameters[\"s3_kms_key_id\"]");
    }

    if (parameters.containsKey("s3_region") && !(parameters.get("s3_region") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: s3_region must be of type String parameters[\"s3_region\"]");
    }

    if (parameters.containsKey("server_certificate") && !(parameters.get("server_certificate") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: server_certificate must be of type String parameters[\"server_certificate\"]");
    }

    if (parameters.containsKey("server_host_key") && !(parameters.get("server_host_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: server_host_key must be of type String parameters[\"server_host_key\"]");
    }

    if (parameters.containsKey("server_type") && !(parameters.get("server_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: server_type must be of type String parameters[\"server_type\"]");
    }

    if (parameters.containsKey("sharepoint_client_id") && !(parameters.get("sharepoint_client_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: sharepoint_client_id must be of type String parameters[\"sharepoint_client_id\"]");
    }

    if (parameters.containsKey("sharepoint_site_url") && !(parameters.get("sharepoint_site_url") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: sharepoint_site_url must be of type String parameters[\"sharepoint_site_url\"]");
    }

    if (parameters.containsKey("sharepoint_tenant_id") && !(parameters.get("sharepoint_tenant_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: sharepoint_tenant_id must be of type String parameters[\"sharepoint_tenant_id\"]");
    }

    if (parameters.containsKey("ssl") && !(parameters.get("ssl") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: ssl must be of type String parameters[\"ssl\"]");
    }

    if (parameters.containsKey("username") && !(parameters.get("username") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: username must be of type String parameters[\"username\"]");
    }

    if (parameters.containsKey("wasabi_access_key") && !(parameters.get("wasabi_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: wasabi_access_key must be of type String parameters[\"wasabi_access_key\"]");
    }

    if (parameters.containsKey("wasabi_bucket") && !(parameters.get("wasabi_bucket") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: wasabi_bucket must be of type String parameters[\"wasabi_bucket\"]");
    }

    if (parameters.containsKey("wasabi_region") && !(parameters.get("wasabi_region") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: wasabi_region must be of type String parameters[\"wasabi_region\"]");
    }

    String apiPath = String.format("/remote_servers/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<RemoteServer> typeReference = new TypeReference<RemoteServer>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  public void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }

    String apiPath = String.format("/remote_servers/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
