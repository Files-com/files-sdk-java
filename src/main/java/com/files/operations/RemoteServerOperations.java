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
import java.util.HashMap;
import java.util.List;

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
   * List Remote Servers
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<RemoteServer> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Remote Servers
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<RemoteServer> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Remote Servers
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `name`, `server_type`, `backblaze_b2_bucket`, `google_cloud_storage_bucket`, `wasabi_bucket`, `s3_bucket`, `azure_blob_storage_container`, `azure_files_storage_share_name`, `s3_compatible_bucket`, `filebase_bucket`, `cloudflare_bucket` or `linode_bucket`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `name`, `server_type`, `workspace_id`, `backblaze_b2_bucket`, `google_cloud_storage_bucket`, `wasabi_bucket`, `s3_bucket`, `azure_blob_storage_container`, `azure_files_storage_share_name`, `s3_compatible_bucket`, `filebase_bucket`, `cloudflare_bucket` or `linode_bucket`. Valid field combinations are `[ server_type, name ]`, `[ workspace_id, name ]`, `[ backblaze_b2_bucket, name ]`, `[ google_cloud_storage_bucket, name ]`, `[ wasabi_bucket, name ]`, `[ s3_bucket, name ]`, `[ azure_blob_storage_container, name ]`, `[ azure_files_storage_share_name, name ]`, `[ s3_compatible_bucket, name ]`, `[ filebase_bucket, name ]`, `[ cloudflare_bucket, name ]`, `[ linode_bucket, name ]`, `[ workspace_id, server_type ]` or `[ workspace_id, server_type, name ]`.</li>
   * <li>{@code filter_prefix} (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `name`, `backblaze_b2_bucket`, `google_cloud_storage_bucket`, `wasabi_bucket`, `s3_bucket`, `azure_blob_storage_container`, `azure_files_storage_share_name`, `s3_compatible_bucket`, `filebase_bucket`, `cloudflare_bucket` or `linode_bucket`. Valid field combinations are `[ backblaze_b2_bucket, name ]`, `[ google_cloud_storage_bucket, name ]`, `[ wasabi_bucket, name ]`, `[ s3_bucket, name ]`, `[ azure_blob_storage_container, name ]`, `[ azure_files_storage_share_name, name ]`, `[ s3_compatible_bucket, name ]`, `[ filebase_bucket, name ]`, `[ cloudflare_bucket, name ]` or `[ linode_bucket, name ]`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code RemoteServer} records, in a {@code ListIterator}
   */
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

  /**
   * List Remote Servers
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<RemoteServer> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Remote Servers
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<RemoteServer> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Remote Server
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public RemoteServer find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Remote Server
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public RemoteServer find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Remote Server
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public RemoteServer find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Remote Server
   *
   * @param id Remote Server ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code RemoteServer}
   */
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

  /**
   * Show Remote Server
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public RemoteServer get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Remote Server
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public RemoteServer get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * List Files.com Agent nodes
   *
   * @see #agentNodes(Long, HashMap, HashMap)
   */
  public AgentNode agentNodes() throws RuntimeException {
    return agentNodes(null, null, null);
  }

  /**
   * List Files.com Agent nodes
   *
   * @see #agentNodes(Long, HashMap, HashMap)
   */
  public AgentNode agentNodes(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return agentNodes(id, parameters, null);
  }

  /**
   * List Files.com Agent nodes
   *
   * @see #agentNodes(Long, HashMap, HashMap)
   */
  public AgentNode agentNodes(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return agentNodes(null, parameters, options);
  }

  /**
   * List Files.com Agent nodes
   *
   * @param id Remote Server ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code AgentNode}
   */
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
   * Download configuration file (required for some Remote Server integrations, such as the Files.com Agent)
   *
   * @see #findConfigurationFile(Long, HashMap, HashMap)
   */
  public RemoteServerConfigurationFile findConfigurationFile() throws RuntimeException {
    return findConfigurationFile(null, null, null);
  }

  /**
   * Download configuration file (required for some Remote Server integrations, such as the Files.com Agent)
   *
   * @see #findConfigurationFile(Long, HashMap, HashMap)
   */
  public RemoteServerConfigurationFile findConfigurationFile(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return findConfigurationFile(id, parameters, null);
  }

  /**
   * Download configuration file (required for some Remote Server integrations, such as the Files.com Agent)
   *
   * @see #findConfigurationFile(Long, HashMap, HashMap)
   */
  public RemoteServerConfigurationFile findConfigurationFile(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return findConfigurationFile(null, parameters, options);
  }

  /**
   * Download configuration file (required for some Remote Server integrations, such as the Files.com Agent)
   *
   * @param id Remote Server ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code RemoteServerConfigurationFile}
   */
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
   * Create Remote Server
   *
   * @see #create(HashMap, HashMap)
   */
  public RemoteServer create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Remote Server
   *
   * @see #create(HashMap, HashMap)
   */
  public RemoteServer create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create Remote Server
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code password} (String): Password, if needed.</li>
   * <li>{@code private_key} (String): Private key, if needed.</li>
   * <li>{@code private_key_passphrase} (String): Passphrase for private key if needed.</li>
   * <li>{@code reset_authentication} (Boolean): Reset authenticated account?</li>
   * <li>{@code sharepoint_client_certificate} (String): SharePoint: PEM-encoded certificate and unencrypted private key for app-only authentication.</li>
   * <li>{@code sharepoint_client_secret} (String): SharePoint: Microsoft Entra application client secret for app-only authentication.</li>
   * <li>{@code ssl_certificate} (String): SSL client certificate.</li>
   * <li>{@code aws_secret_key} (String): AWS: secret key.</li>
   * <li>{@code azure_blob_storage_access_key} (String): Azure Blob Storage: Access Key</li>
   * <li>{@code azure_blob_storage_sas_token} (String): Azure Blob Storage: Shared Access Signature (SAS) token</li>
   * <li>{@code azure_files_storage_access_key} (String): Azure File Storage: Access Key</li>
   * <li>{@code azure_files_storage_sas_token} (String): Azure File Storage: Shared Access Signature (SAS) token</li>
   * <li>{@code backblaze_b2_application_key} (String): Backblaze B2 Cloud Storage: applicationKey</li>
   * <li>{@code backblaze_b2_key_id} (String): Backblaze B2 Cloud Storage: keyID</li>
   * <li>{@code cloudflare_secret_key} (String): Cloudflare: Secret Key</li>
   * <li>{@code filebase_secret_key} (String): Filebase: Secret Key</li>
   * <li>{@code google_cloud_storage_credentials_json} (String): Google Cloud Storage: JSON file that contains the private key. To generate see https://cloud.google.com/storage/docs/json_api/v1/how-tos/authorizing#APIKey</li>
   * <li>{@code google_cloud_storage_s3_compatible_secret_key} (String): Google Cloud Storage: S3-compatible secret key</li>
   * <li>{@code linode_secret_key} (String): Linode: Secret Key</li>
   * <li>{@code s3_compatible_secret_key} (String): S3-compatible: Secret Key</li>
   * <li>{@code wasabi_secret_key} (String): Wasabi: Secret Key</li>
   * <li>{@code allow_relative_paths} (Boolean): Allow relative paths in SFTP. If true, paths will not be forced to be absolute, allowing operations relative to the user's home directory.</li>
   * <li>{@code aws_access_key} (String): AWS Access Key.</li>
   * <li>{@code azure_blob_storage_account} (String): Azure Blob Storage: Account name</li>
   * <li>{@code azure_blob_storage_container} (String): Azure Blob Storage: Container name</li>
   * <li>{@code azure_blob_storage_dns_suffix} (String): Azure Blob Storage: Custom DNS suffix</li>
   * <li>{@code azure_blob_storage_hierarchical_namespace} (Boolean): Azure Blob Storage: Does the storage account has hierarchical namespace feature enabled?</li>
   * <li>{@code azure_files_storage_account} (String): Azure Files: Storage Account name</li>
   * <li>{@code azure_files_storage_dns_suffix} (String): Azure Files: Custom DNS suffix</li>
   * <li>{@code azure_files_storage_share_name} (String): Azure Files:  Storage Share name</li>
   * <li>{@code backblaze_b2_bucket} (String): Backblaze B2 Cloud Storage: Bucket name</li>
   * <li>{@code backblaze_b2_s3_endpoint} (String): Backblaze B2 Cloud Storage: S3 Endpoint</li>
   * <li>{@code buffer_uploads} (String): If set to always, uploads to this server will be uploaded first to Files.com before being sent to the remote server. This can improve performance in certain access patterns, such as high-latency connections.  It will cause data to be temporarily stored in Files.com. If set to auto, we will perform this optimization if we believe it to be a benefit in a given situation.</li>
   * <li>{@code cloudflare_access_key} (String): Cloudflare: Access Key.</li>
   * <li>{@code cloudflare_bucket} (String): Cloudflare: Bucket name</li>
   * <li>{@code cloudflare_endpoint} (String): Cloudflare: endpoint</li>
   * <li>{@code description} (String): Internal description for your reference</li>
   * <li>{@code dropbox_teams} (Boolean): Dropbox: If true, list Team folders in root?</li>
   * <li>{@code enable_dedicated_ips} (Boolean): `true` if remote server only accepts connections from dedicated IPs</li>
   * <li>{@code filebase_access_key} (String): Filebase: Access Key.</li>
   * <li>{@code filebase_bucket} (String): Filebase: Bucket name</li>
   * <li>{@code files_api_key} (String): Files.com direct link: API key used once to pair the remote server.</li>
   * <li>{@code files_agent_permission_set} (String): Agent file permissions: `read_only`, `write_only`, or `read_write`. Files.com writes this value as `permission_set` in the configuration file generated for this Remote Server. Setting it on Create or Update changes the generated file, not a running Agent. The Agent enforces the permissions in its local configuration file, controlled by the host's system administrator, and reports them at registration, replacing the stored value.</li>
   * <li>{@code files_agent_root} (String): Agent local root path. Files.com writes this value as `root` in the configuration file generated for this Remote Server. Setting it on Create or Update changes the generated file, not a running Agent. The Agent enforces the root in its local configuration file, controlled by the host's system administrator, and reports it at registration, replacing the stored value.</li>
   * <li>{@code files_agent_version} (String): Files.com Agent version reported by the Agent at each registration, replacing the stored value. This field is not included in the generated Agent configuration file. Setting it on Create or Update does not update the running Agent.</li>
   * <li>{@code outbound_agent_id} (Long): Route traffic to outbound on a files-agent</li>
   * <li>{@code custom_domain_id} (Long): Custom Domain ID whose dedicated IP addresses are selected when this Remote Server uses dedicated IPs. Must be available to this Remote Server's workspace. Requires enable_dedicated_ips and cannot be combined with an outbound Agent. Set to null to use the site's default dedicated IPs.</li>
   * <li>{@code google_cloud_storage_authentication_method} (String): Google Cloud Storage: Authentication method. Can be json, hmac, or oauth.</li>
   * <li>{@code google_cloud_storage_bucket} (String): Google Cloud Storage: Bucket Name</li>
   * <li>{@code google_cloud_storage_oauth_scope} (String): Google Cloud Storage: OAuth scope. Can be https://www.googleapis.com/auth/devstorage.read_only or https://www.googleapis.com/auth/devstorage.read_write.</li>
   * <li>{@code google_cloud_storage_project_id} (String): Google Cloud Storage: Project ID</li>
   * <li>{@code google_cloud_storage_s3_compatible_access_key} (String): Google Cloud Storage: S3-compatible Access Key.</li>
   * <li>{@code hostname} (String): Hostname or IP address. For Agent Remote Servers, the Agent reports the hostname it connects from at each registration, replacing the stored value. This field is not included in the generated Agent configuration file. Setting it on Create or Update does not change the running Agent.</li>
   * <li>{@code linode_access_key} (String): Linode: Access Key</li>
   * <li>{@code linode_bucket} (String): Linode: Bucket name</li>
   * <li>{@code linode_region} (String): Linode: region</li>
   * <li>{@code max_connections} (Long): Max number of parallel connections.  Ignored for S3 connections (we will parallelize these as much as possible).</li>
   * <li>{@code name} (String): Internal name for your reference</li>
   * <li>{@code one_drive_account_type} (String): OneDrive: Either personal or business_other account types</li>
   * <li>{@code pin_to_site_region} (Boolean): If true, we will ensure that all communications with this remote server are made through the primary region of the site.  This setting can also be overridden by a site-wide setting which will force it to true.</li>
   * <li>{@code port} (Long): Port for remote server. For Agent Remote Servers, the Agent reports its downstream proxy port at each registration, replacing the stored value. This field is not included in the generated Agent configuration file. Setting it on Create or Update does not change the running Agent.</li>
   * <li>{@code upload_staging_path} (String): Upload staging path.  Applies to SFTP only.  If a path is provided here, files will first be uploaded to this path on the remote folder and the moved into the final correct path via an SFTP move command.  This is required by some remote MFT systems to emulate atomic uploads, which are otherwise not supoprted by SFTP.</li>
   * <li>{@code remote_server_credential_id} (Long): ID of Remote Server Credential, if applicable.</li>
   * <li>{@code s3_assume_role_arn} (String): AWS IAM Role ARN for AssumeRole authentication.</li>
   * <li>{@code s3_assume_role_duration_seconds} (Long): Session duration in seconds for AssumeRole authentication (900-43200).</li>
   * <li>{@code s3_bucket} (String): S3 bucket name</li>
   * <li>{@code s3_compatible_access_key} (String): S3-compatible: Access Key</li>
   * <li>{@code s3_compatible_bucket} (String): S3-compatible: Bucket name</li>
   * <li>{@code s3_compatible_endpoint} (String): S3-compatible: endpoint</li>
   * <li>{@code s3_compatible_region} (String): S3-compatible: region</li>
   * <li>{@code s3_compatible_virtual_hosted_style} (Boolean): S3-compatible: If true, use virtual-hosted-style URLs instead of path-style URLs</li>
   * <li>{@code s3_kms_key_id} (String): ARN of the AWS KMS key that encrypts files written to the bucket (SSE-KMS). Leave blank unless your bucket requires a specific key.</li>
   * <li>{@code s3_region} (String): S3 region</li>
   * <li>{@code server_certificate} (String): Remote server certificate</li>
   * <li>{@code server_host_key} (String): Pinned SSH host key or OpenSSH host certificate for SFTP. If omitted, Files.com detects and stores a host key, preferring plain keys over certificates. With `server_certificate=require_match` (the default), the server must present the exact pinned key or certificate and prove it holds the matching private key. A pinned certificate is compared in full, so renewal can require updating `server_host_key` even when its underlying key is unchanged. Files.com does not check certificate CA signatures, principals, or validity periods. Certificate expiration alone does not end the pin. Update `server_host_key` to replace the pin.</li>
   * <li>{@code server_type} (String): Remote server type.</li>
   * <li>{@code sharepoint_client_id} (String): SharePoint: Microsoft Entra application client ID for app-only authentication.</li>
   * <li>{@code sharepoint_site_url} (String): SharePoint: Site URL to scope app-only authentication to a single site. Leave blank to browse all sites.</li>
   * <li>{@code sharepoint_tenant_id} (String): SharePoint: Microsoft Entra tenant ID for app-only authentication.</li>
   * <li>{@code ssl} (String): Should we require SSL?</li>
   * <li>{@code username} (String): Remote server username.</li>
   * <li>{@code wasabi_access_key} (String): Wasabi: Access Key.</li>
   * <li>{@code wasabi_bucket} (String): Wasabi: Bucket name</li>
   * <li>{@code wasabi_region} (String): Wasabi: Region</li>
   * <li>{@code workspace_id} (Long): Workspace ID (0 for default workspace)</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code RemoteServer}
   */
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
   *
   * @see #agentPushUpdate(Long, HashMap, HashMap)
   */
  public AgentPushUpdate agentPushUpdate() throws RuntimeException {
    return agentPushUpdate(null, null, null);
  }

  /**
   * Push update to Files Agent
   *
   * @see #agentPushUpdate(Long, HashMap, HashMap)
   */
  public AgentPushUpdate agentPushUpdate(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return agentPushUpdate(id, parameters, null);
  }

  /**
   * Push update to Files Agent
   *
   * @see #agentPushUpdate(Long, HashMap, HashMap)
   */
  public AgentPushUpdate agentPushUpdate(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return agentPushUpdate(null, parameters, options);
  }

  /**
   * Push update to Files Agent
   *
   * @param id Remote Server ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code AgentPushUpdate}
   */
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
   * Update Remote Server
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public RemoteServer update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Remote Server
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public RemoteServer update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Remote Server
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public RemoteServer update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Remote Server
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code password} (String): Password, if needed.</li>
   * <li>{@code private_key} (String): Private key, if needed.</li>
   * <li>{@code private_key_passphrase} (String): Passphrase for private key if needed.</li>
   * <li>{@code reset_authentication} (Boolean): Reset authenticated account?</li>
   * <li>{@code sharepoint_client_certificate} (String): SharePoint: PEM-encoded certificate and unencrypted private key for app-only authentication.</li>
   * <li>{@code sharepoint_client_secret} (String): SharePoint: Microsoft Entra application client secret for app-only authentication.</li>
   * <li>{@code ssl_certificate} (String): SSL client certificate.</li>
   * <li>{@code aws_secret_key} (String): AWS: secret key.</li>
   * <li>{@code azure_blob_storage_access_key} (String): Azure Blob Storage: Access Key</li>
   * <li>{@code azure_blob_storage_sas_token} (String): Azure Blob Storage: Shared Access Signature (SAS) token</li>
   * <li>{@code azure_files_storage_access_key} (String): Azure File Storage: Access Key</li>
   * <li>{@code azure_files_storage_sas_token} (String): Azure File Storage: Shared Access Signature (SAS) token</li>
   * <li>{@code backblaze_b2_application_key} (String): Backblaze B2 Cloud Storage: applicationKey</li>
   * <li>{@code backblaze_b2_key_id} (String): Backblaze B2 Cloud Storage: keyID</li>
   * <li>{@code cloudflare_secret_key} (String): Cloudflare: Secret Key</li>
   * <li>{@code filebase_secret_key} (String): Filebase: Secret Key</li>
   * <li>{@code google_cloud_storage_credentials_json} (String): Google Cloud Storage: JSON file that contains the private key. To generate see https://cloud.google.com/storage/docs/json_api/v1/how-tos/authorizing#APIKey</li>
   * <li>{@code google_cloud_storage_s3_compatible_secret_key} (String): Google Cloud Storage: S3-compatible secret key</li>
   * <li>{@code linode_secret_key} (String): Linode: Secret Key</li>
   * <li>{@code s3_compatible_secret_key} (String): S3-compatible: Secret Key</li>
   * <li>{@code wasabi_secret_key} (String): Wasabi: Secret Key</li>
   * <li>{@code allow_relative_paths} (Boolean): Allow relative paths in SFTP. If true, paths will not be forced to be absolute, allowing operations relative to the user's home directory.</li>
   * <li>{@code aws_access_key} (String): AWS Access Key.</li>
   * <li>{@code azure_blob_storage_account} (String): Azure Blob Storage: Account name</li>
   * <li>{@code azure_blob_storage_container} (String): Azure Blob Storage: Container name</li>
   * <li>{@code azure_blob_storage_dns_suffix} (String): Azure Blob Storage: Custom DNS suffix</li>
   * <li>{@code azure_blob_storage_hierarchical_namespace} (Boolean): Azure Blob Storage: Does the storage account has hierarchical namespace feature enabled?</li>
   * <li>{@code azure_files_storage_account} (String): Azure Files: Storage Account name</li>
   * <li>{@code azure_files_storage_dns_suffix} (String): Azure Files: Custom DNS suffix</li>
   * <li>{@code azure_files_storage_share_name} (String): Azure Files:  Storage Share name</li>
   * <li>{@code backblaze_b2_bucket} (String): Backblaze B2 Cloud Storage: Bucket name</li>
   * <li>{@code backblaze_b2_s3_endpoint} (String): Backblaze B2 Cloud Storage: S3 Endpoint</li>
   * <li>{@code buffer_uploads} (String): If set to always, uploads to this server will be uploaded first to Files.com before being sent to the remote server. This can improve performance in certain access patterns, such as high-latency connections.  It will cause data to be temporarily stored in Files.com. If set to auto, we will perform this optimization if we believe it to be a benefit in a given situation.</li>
   * <li>{@code cloudflare_access_key} (String): Cloudflare: Access Key.</li>
   * <li>{@code cloudflare_bucket} (String): Cloudflare: Bucket name</li>
   * <li>{@code cloudflare_endpoint} (String): Cloudflare: endpoint</li>
   * <li>{@code description} (String): Internal description for your reference</li>
   * <li>{@code dropbox_teams} (Boolean): Dropbox: If true, list Team folders in root?</li>
   * <li>{@code enable_dedicated_ips} (Boolean): `true` if remote server only accepts connections from dedicated IPs</li>
   * <li>{@code filebase_access_key} (String): Filebase: Access Key.</li>
   * <li>{@code filebase_bucket} (String): Filebase: Bucket name</li>
   * <li>{@code files_api_key} (String): Files.com direct link: API key used once to pair the remote server.</li>
   * <li>{@code files_agent_permission_set} (String): Agent file permissions: `read_only`, `write_only`, or `read_write`. Files.com writes this value as `permission_set` in the configuration file generated for this Remote Server. Setting it on Create or Update changes the generated file, not a running Agent. The Agent enforces the permissions in its local configuration file, controlled by the host's system administrator, and reports them at registration, replacing the stored value.</li>
   * <li>{@code files_agent_root} (String): Agent local root path. Files.com writes this value as `root` in the configuration file generated for this Remote Server. Setting it on Create or Update changes the generated file, not a running Agent. The Agent enforces the root in its local configuration file, controlled by the host's system administrator, and reports it at registration, replacing the stored value.</li>
   * <li>{@code files_agent_version} (String): Files.com Agent version reported by the Agent at each registration, replacing the stored value. This field is not included in the generated Agent configuration file. Setting it on Create or Update does not update the running Agent.</li>
   * <li>{@code outbound_agent_id} (Long): Route traffic to outbound on a files-agent</li>
   * <li>{@code custom_domain_id} (Long): Custom Domain ID whose dedicated IP addresses are selected when this Remote Server uses dedicated IPs. Must be available to this Remote Server's workspace. Requires enable_dedicated_ips and cannot be combined with an outbound Agent. Set to null to use the site's default dedicated IPs.</li>
   * <li>{@code google_cloud_storage_authentication_method} (String): Google Cloud Storage: Authentication method. Can be json, hmac, or oauth.</li>
   * <li>{@code google_cloud_storage_bucket} (String): Google Cloud Storage: Bucket Name</li>
   * <li>{@code google_cloud_storage_oauth_scope} (String): Google Cloud Storage: OAuth scope. Can be https://www.googleapis.com/auth/devstorage.read_only or https://www.googleapis.com/auth/devstorage.read_write.</li>
   * <li>{@code google_cloud_storage_project_id} (String): Google Cloud Storage: Project ID</li>
   * <li>{@code google_cloud_storage_s3_compatible_access_key} (String): Google Cloud Storage: S3-compatible Access Key.</li>
   * <li>{@code hostname} (String): Hostname or IP address. For Agent Remote Servers, the Agent reports the hostname it connects from at each registration, replacing the stored value. This field is not included in the generated Agent configuration file. Setting it on Create or Update does not change the running Agent.</li>
   * <li>{@code linode_access_key} (String): Linode: Access Key</li>
   * <li>{@code linode_bucket} (String): Linode: Bucket name</li>
   * <li>{@code linode_region} (String): Linode: region</li>
   * <li>{@code max_connections} (Long): Max number of parallel connections.  Ignored for S3 connections (we will parallelize these as much as possible).</li>
   * <li>{@code name} (String): Internal name for your reference</li>
   * <li>{@code one_drive_account_type} (String): OneDrive: Either personal or business_other account types</li>
   * <li>{@code pin_to_site_region} (Boolean): If true, we will ensure that all communications with this remote server are made through the primary region of the site.  This setting can also be overridden by a site-wide setting which will force it to true.</li>
   * <li>{@code port} (Long): Port for remote server. For Agent Remote Servers, the Agent reports its downstream proxy port at each registration, replacing the stored value. This field is not included in the generated Agent configuration file. Setting it on Create or Update does not change the running Agent.</li>
   * <li>{@code upload_staging_path} (String): Upload staging path.  Applies to SFTP only.  If a path is provided here, files will first be uploaded to this path on the remote folder and the moved into the final correct path via an SFTP move command.  This is required by some remote MFT systems to emulate atomic uploads, which are otherwise not supoprted by SFTP.</li>
   * <li>{@code remote_server_credential_id} (Long): ID of Remote Server Credential, if applicable.</li>
   * <li>{@code s3_assume_role_arn} (String): AWS IAM Role ARN for AssumeRole authentication.</li>
   * <li>{@code s3_assume_role_duration_seconds} (Long): Session duration in seconds for AssumeRole authentication (900-43200).</li>
   * <li>{@code s3_bucket} (String): S3 bucket name</li>
   * <li>{@code s3_compatible_access_key} (String): S3-compatible: Access Key</li>
   * <li>{@code s3_compatible_bucket} (String): S3-compatible: Bucket name</li>
   * <li>{@code s3_compatible_endpoint} (String): S3-compatible: endpoint</li>
   * <li>{@code s3_compatible_region} (String): S3-compatible: region</li>
   * <li>{@code s3_compatible_virtual_hosted_style} (Boolean): S3-compatible: If true, use virtual-hosted-style URLs instead of path-style URLs</li>
   * <li>{@code s3_kms_key_id} (String): ARN of the AWS KMS key that encrypts files written to the bucket (SSE-KMS). Leave blank unless your bucket requires a specific key.</li>
   * <li>{@code s3_region} (String): S3 region</li>
   * <li>{@code server_certificate} (String): Remote server certificate</li>
   * <li>{@code server_host_key} (String): Pinned SSH host key or OpenSSH host certificate for SFTP. If omitted, Files.com detects and stores a host key, preferring plain keys over certificates. With `server_certificate=require_match` (the default), the server must present the exact pinned key or certificate and prove it holds the matching private key. A pinned certificate is compared in full, so renewal can require updating `server_host_key` even when its underlying key is unchanged. Files.com does not check certificate CA signatures, principals, or validity periods. Certificate expiration alone does not end the pin. Update `server_host_key` to replace the pin.</li>
   * <li>{@code server_type} (String): Remote server type.</li>
   * <li>{@code sharepoint_client_id} (String): SharePoint: Microsoft Entra application client ID for app-only authentication.</li>
   * <li>{@code sharepoint_site_url} (String): SharePoint: Site URL to scope app-only authentication to a single site. Leave blank to browse all sites.</li>
   * <li>{@code sharepoint_tenant_id} (String): SharePoint: Microsoft Entra tenant ID for app-only authentication.</li>
   * <li>{@code ssl} (String): Should we require SSL?</li>
   * <li>{@code username} (String): Remote server username.</li>
   * <li>{@code wasabi_access_key} (String): Wasabi: Access Key.</li>
   * <li>{@code wasabi_bucket} (String): Wasabi: Bucket name</li>
   * <li>{@code wasabi_region} (String): Wasabi: Region</li>
   * </ul>
   *
   * @param id Remote Server ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code RemoteServer}
   */
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
   * Delete Remote Server
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Remote Server
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Remote Server
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Remote Server
   *
   * @param id Remote Server ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
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

  /**
   * Delete Remote Server
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Remote Server
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
