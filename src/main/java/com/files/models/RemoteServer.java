package com.files.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.ListIterator;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RemoteServer implements ModelInterface {
  @Setter
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();


  public RemoteServer() {
    this(null, null);
  }

  public RemoteServer(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public RemoteServer(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Remote server ID
  */
  @Getter
  @Setter
  @JsonProperty("id")
  public Long id;

  /**
  * If true, this server has been disabled due to failures.  Make any change or set disabled to false to clear this flag.
  */
  @Getter
  @Setter
  @JsonProperty("disabled")
  public Boolean disabled;

  /**
  * Type of authentication method
  */
  @Getter
  @Setter
  @JsonProperty("authentication_method")
  public String authenticationMethod;

  /**
  * Hostname or IP address
  */
  @Getter
  @Setter
  @JsonProperty("hostname")
  public String hostname;

  /**
  * Initial home folder on remote server
  */
  @Getter
  @Setter
  @JsonProperty("remote_home_path")
  public String remoteHomePath;

  /**
  * Internal name for your reference
  */
  @Getter
  @Setter
  @JsonProperty("name")
  public String name;

  /**
  * Port for remote server.  Not needed for S3.
  */
  @Getter
  @Setter
  @JsonProperty("port")
  public Long port;

  /**
  * Max number of parallel connections.  Ignored for S3 connections (we will parallelize these as much as possible).
  */
  @Getter
  @Setter
  @JsonProperty("max_connections")
  public Long maxConnections;

  /**
  * If true, we will ensure that all communications with this remote server are made through the primary region of the site.  This setting can also be overridden by a site-wide setting which will force it to true.
  */
  @Getter
  @Setter
  @JsonProperty("pin_to_site_region")
  public Boolean pinToSiteRegion;

  /**
  * If set, all communications with this remote server are made through the provided region.
  */
  @Getter
  @Setter
  @JsonProperty("pinned_region")
  public String pinnedRegion;

  /**
  * S3 bucket name
  */
  @Getter
  @Setter
  @JsonProperty("s3_bucket")
  public String s3Bucket;

  /**
  * S3 region
  */
  @Getter
  @Setter
  @JsonProperty("s3_region")
  public String s3Region;

  /**
  * AWS Access Key.
  */
  @Getter
  @Setter
  @JsonProperty("aws_access_key")
  public String awsAccessKey;

  /**
  * Remote server certificate
  */
  @Getter
  @Setter
  @JsonProperty("server_certificate")
  public String serverCertificate;

  /**
  * Remote server SSH Host Key. If provided, we will require that the server host key matches the provided key. Uses OpenSSH format similar to what would go into ~/.ssh/known_hosts
  */
  @Getter
  @Setter
  @JsonProperty("server_host_key")
  public String serverHostKey;

  /**
  * Remote server type.
  */
  @Getter
  @Setter
  @JsonProperty("server_type")
  public String serverType;

  /**
  * Should we require SSL?
  */
  @Getter
  @Setter
  @JsonProperty("ssl")
  public String ssl;

  /**
  * Remote server username.  Not needed for S3 buckets.
  */
  @Getter
  @Setter
  @JsonProperty("username")
  public String username;

  /**
  * Google Cloud Storage: Bucket Name
  */
  @Getter
  @Setter
  @JsonProperty("google_cloud_storage_bucket")
  public String googleCloudStorageBucket;

  /**
  * Google Cloud Storage: Project ID
  */
  @Getter
  @Setter
  @JsonProperty("google_cloud_storage_project_id")
  public String googleCloudStorageProjectId;

  /**
  * Google Cloud Storage: Region
  */
  @Getter
  @Setter
  @JsonProperty("google_cloud_storage_region")
  public String googleCloudStorageRegion;

  /**
  * Google Cloud Storage: S3-compatible Access Key.
  */
  @Getter
  @Setter
  @JsonProperty("google_cloud_storage_s3_compatible_access_key")
  public String googleCloudStorageS3CompatibleAccessKey;

  /**
  * Backblaze B2 Cloud Storage: S3 Endpoint
  */
  @Getter
  @Setter
  @JsonProperty("backblaze_b2_s3_endpoint")
  public String backblazeB2S3Endpoint;

  /**
  * Backblaze B2 Cloud Storage: Bucket name
  */
  @Getter
  @Setter
  @JsonProperty("backblaze_b2_bucket")
  public String backblazeB2Bucket;

  /**
  * Wasabi: Bucket name
  */
  @Getter
  @Setter
  @JsonProperty("wasabi_bucket")
  public String wasabiBucket;

  /**
  * Wasabi: Region
  */
  @Getter
  @Setter
  @JsonProperty("wasabi_region")
  public String wasabiRegion;

  /**
  * Wasabi: Access Key.
  */
  @Getter
  @Setter
  @JsonProperty("wasabi_access_key")
  public String wasabiAccessKey;

  /**
  * Rackspace: username used to login to the Rackspace Cloud Control Panel.
  */
  @Getter
  @Setter
  @JsonProperty("rackspace_username")
  public String rackspaceUsername;

  /**
  * Rackspace: Three letter code for Rackspace region. See https://support.rackspace.com/how-to/about-regions/
  */
  @Getter
  @Setter
  @JsonProperty("rackspace_region")
  public String rackspaceRegion;

  /**
  * Rackspace: The name of the container (top level directory) where files will sync.
  */
  @Getter
  @Setter
  @JsonProperty("rackspace_container")
  public String rackspaceContainer;

  /**
  * Either `in_setup` or `complete`
  */
  @Getter
  @Setter
  @JsonProperty("auth_status")
  public String authStatus;

  /**
  * Describes the authorized account
  */
  @Getter
  @Setter
  @JsonProperty("auth_account_name")
  public String authAccountName;

  /**
  * OneDrive: Either personal or business_other account types
  */
  @Getter
  @Setter
  @JsonProperty("one_drive_account_type")
  public String oneDriveAccountType;

  /**
  * Azure Blob Storage: Account name
  */
  @Getter
  @Setter
  @JsonProperty("azure_blob_storage_account")
  public String azureBlobStorageAccount;

  /**
  * Azure Blob Storage: Container name
  */
  @Getter
  @Setter
  @JsonProperty("azure_blob_storage_container")
  public String azureBlobStorageContainer;

  /**
  * Azure Blob Storage: Does the storage account has hierarchical namespace feature enabled?
  */
  @Getter
  @Setter
  @JsonProperty("azure_blob_storage_hierarchical_namespace")
  public Boolean azureBlobStorageHierarchicalNamespace;

  /**
  * Azure Blob Storage: Custom DNS suffix
  */
  @Getter
  @Setter
  @JsonProperty("azure_blob_storage_dns_suffix")
  public String azureBlobStorageDnsSuffix;

  /**
  * Azure Files: Storage Account name
  */
  @Getter
  @Setter
  @JsonProperty("azure_files_storage_account")
  public String azureFilesStorageAccount;

  /**
  * Azure Files:  Storage Share name
  */
  @Getter
  @Setter
  @JsonProperty("azure_files_storage_share_name")
  public String azureFilesStorageShareName;

  /**
  * Azure Files: Custom DNS suffix
  */
  @Getter
  @Setter
  @JsonProperty("azure_files_storage_dns_suffix")
  public String azureFilesStorageDnsSuffix;

  /**
  * S3-compatible: Bucket name
  */
  @Getter
  @Setter
  @JsonProperty("s3_compatible_bucket")
  public String s3CompatibleBucket;

  /**
  * S3-compatible: endpoint
  */
  @Getter
  @Setter
  @JsonProperty("s3_compatible_endpoint")
  public String s3CompatibleEndpoint;

  /**
  * S3-compatible: region
  */
  @Getter
  @Setter
  @JsonProperty("s3_compatible_region")
  public String s3CompatibleRegion;

  /**
  * S3-compatible: Access Key
  */
  @Getter
  @Setter
  @JsonProperty("s3_compatible_access_key")
  public String s3CompatibleAccessKey;

  /**
  * `true` if remote server only accepts connections from dedicated IPs
  */
  @Getter
  @Setter
  @JsonProperty("enable_dedicated_ips")
  public Boolean enableDedicatedIps;

  /**
  * Local permissions for files agent. read_only, write_only, or read_write
  */
  @Getter
  @Setter
  @JsonProperty("files_agent_permission_set")
  public String filesAgentPermissionSet;

  /**
  * Agent local root path
  */
  @Getter
  @Setter
  @JsonProperty("files_agent_root")
  public String filesAgentRoot;

  /**
  * Files Agent API Token
  */
  @Getter
  @Setter
  @JsonProperty("files_agent_api_token")
  public String filesAgentApiToken;

  /**
  * Files Agent version
  */
  @Getter
  @Setter
  @JsonProperty("files_agent_version")
  public String filesAgentVersion;

  /**
  * Filebase: Bucket name
  */
  @Getter
  @Setter
  @JsonProperty("filebase_bucket")
  public String filebaseBucket;

  /**
  * Filebase: Access Key.
  */
  @Getter
  @Setter
  @JsonProperty("filebase_access_key")
  public String filebaseAccessKey;

  /**
  * Cloudflare: Bucket name
  */
  @Getter
  @Setter
  @JsonProperty("cloudflare_bucket")
  public String cloudflareBucket;

  /**
  * Cloudflare: Access Key.
  */
  @Getter
  @Setter
  @JsonProperty("cloudflare_access_key")
  public String cloudflareAccessKey;

  /**
  * Cloudflare: endpoint
  */
  @Getter
  @Setter
  @JsonProperty("cloudflare_endpoint")
  public String cloudflareEndpoint;

  /**
  * Dropbox: If true, list Team folders in root?
  */
  @Getter
  @Setter
  @JsonProperty("dropbox_teams")
  public Boolean dropboxTeams;

  /**
  * Linode: Bucket name
  */
  @Getter
  @Setter
  @JsonProperty("linode_bucket")
  public String linodeBucket;

  /**
  * Linode: Access Key
  */
  @Getter
  @Setter
  @JsonProperty("linode_access_key")
  public String linodeAccessKey;

  /**
  * Linode: region
  */
  @Getter
  @Setter
  @JsonProperty("linode_region")
  public String linodeRegion;

  /**
  * If true, this remote server supports file versioning. This value is determined automatically by Files.com.
  */
  @Getter
  @Setter
  @JsonProperty("supports_versioning")
  public Boolean supportsVersioning;

  /**
  * Password, if needed.
  */
  @Getter
  @Setter
  @JsonProperty("password")
  public String password;

  /**
  * Private key, if needed.
  */
  @Getter
  @Setter
  @JsonProperty("private_key")
  public String privateKey;

  /**
  * Passphrase for private key if needed.
  */
  @Getter
  @Setter
  @JsonProperty("private_key_passphrase")
  public String privateKeyPassphrase;

  /**
  * Reset authenticated account?
  */
  @Getter
  @Setter
  @JsonProperty("reset_authentication")
  public Boolean resetAuthentication;

  /**
  * SSL client certificate.
  */
  @Getter
  @Setter
  @JsonProperty("ssl_certificate")
  public String sslCertificate;

  /**
  * AWS: secret key.
  */
  @Getter
  @Setter
  @JsonProperty("aws_secret_key")
  public String awsSecretKey;

  /**
  * Azure Blob Storage: Access Key
  */
  @Getter
  @Setter
  @JsonProperty("azure_blob_storage_access_key")
  public String azureBlobStorageAccessKey;

  /**
  * Azure Blob Storage: Shared Access Signature (SAS) token
  */
  @Getter
  @Setter
  @JsonProperty("azure_blob_storage_sas_token")
  public String azureBlobStorageSasToken;

  /**
  * Azure File Storage: Access Key
  */
  @Getter
  @Setter
  @JsonProperty("azure_files_storage_access_key")
  public String azureFilesStorageAccessKey;

  /**
  * Azure File Storage: Shared Access Signature (SAS) token
  */
  @Getter
  @Setter
  @JsonProperty("azure_files_storage_sas_token")
  public String azureFilesStorageSasToken;

  /**
  * Backblaze B2 Cloud Storage: applicationKey
  */
  @Getter
  @Setter
  @JsonProperty("backblaze_b2_application_key")
  public String backblazeB2ApplicationKey;

  /**
  * Backblaze B2 Cloud Storage: keyID
  */
  @Getter
  @Setter
  @JsonProperty("backblaze_b2_key_id")
  public String backblazeB2KeyId;

  /**
  * Cloudflare: Secret Key
  */
  @Getter
  @Setter
  @JsonProperty("cloudflare_secret_key")
  public String cloudflareSecretKey;

  /**
  * Filebase: Secret Key
  */
  @Getter
  @Setter
  @JsonProperty("filebase_secret_key")
  public String filebaseSecretKey;

  /**
  * Google Cloud Storage: JSON file that contains the private key. To generate see https://cloud.google.com/storage/docs/json_api/v1/how-tos/authorizing#APIKey
  */
  @Getter
  @Setter
  @JsonProperty("google_cloud_storage_credentials_json")
  public String googleCloudStorageCredentialsJson;

  /**
  * Google Cloud Storage: S3-compatible secret key
  */
  @Getter
  @Setter
  @JsonProperty("google_cloud_storage_s3_compatible_secret_key")
  public String googleCloudStorageS3CompatibleSecretKey;

  /**
  * Linode: Secret Key
  */
  @Getter
  @Setter
  @JsonProperty("linode_secret_key")
  public String linodeSecretKey;

  /**
  * Rackspace: API key from the Rackspace Cloud Control Panel
  */
  @Getter
  @Setter
  @JsonProperty("rackspace_api_key")
  public String rackspaceApiKey;

  /**
  * S3-compatible: Secret Key
  */
  @Getter
  @Setter
  @JsonProperty("s3_compatible_secret_key")
  public String s3CompatibleSecretKey;

  /**
  * Wasabi: Secret Key
  */
  @Getter
  @Setter
  @JsonProperty("wasabi_secret_key")
  public String wasabiSecretKey;

  /**
  * Post local changes, check in, and download configuration file (used by some Remote Server integrations, such as the Files.com Agent)
  *
  * Parameters:
  *   api_token - string - Files Agent API Token
  *   permission_set - string - The permission set for the agent ['read_write', 'read_only', 'write_only']
  *   root - string - The root directory for the agent
  *   hostname - string
  *   port - int64 - Incoming port for files agent connections
  *   status - string - either running or shutdown
  *   config_version - string - agent config version
  *   private_key - string - The private key for the agent
  *   public_key - string - public key
  *   server_host_key - string
  *   subdomain - string - Files.com subdomain site name
  */
  public RemoteServerConfigurationFile configurationFile(HashMap<String, Object> parameters) throws IOException {
    return RemoteServer.configurationFile(this.id, parameters, this.options);
  }

  /**
  * Parameters:
  *   password - string - Password, if needed.
  *   private_key - string - Private key, if needed.
  *   private_key_passphrase - string - Passphrase for private key if needed.
  *   reset_authentication - boolean - Reset authenticated account?
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
  *   rackspace_api_key - string - Rackspace: API key from the Rackspace Cloud Control Panel
  *   s3_compatible_secret_key - string - S3-compatible: Secret Key
  *   wasabi_secret_key - string - Wasabi: Secret Key
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
  *   cloudflare_access_key - string - Cloudflare: Access Key.
  *   cloudflare_bucket - string - Cloudflare: Bucket name
  *   cloudflare_endpoint - string - Cloudflare: endpoint
  *   dropbox_teams - boolean - Dropbox: If true, list Team folders in root?
  *   enable_dedicated_ips - boolean - `true` if remote server only accepts connections from dedicated IPs
  *   filebase_access_key - string - Filebase: Access Key.
  *   filebase_bucket - string - Filebase: Bucket name
  *   files_agent_permission_set - string - Local permissions for files agent. read_only, write_only, or read_write
  *   files_agent_root - string - Agent local root path
  *   files_agent_version - string - Files Agent version
  *   google_cloud_storage_bucket - string - Google Cloud Storage: Bucket Name
  *   google_cloud_storage_project_id - string - Google Cloud Storage: Project ID
  *   google_cloud_storage_region - string - Google Cloud Storage: Region
  *   google_cloud_storage_s3_compatible_access_key - string - Google Cloud Storage: S3-compatible Access Key.
  *   hostname - string - Hostname or IP address
  *   linode_access_key - string - Linode: Access Key
  *   linode_bucket - string - Linode: Bucket name
  *   linode_region - string - Linode: region
  *   max_connections - int64 - Max number of parallel connections.  Ignored for S3 connections (we will parallelize these as much as possible).
  *   name - string - Internal name for your reference
  *   one_drive_account_type - string - OneDrive: Either personal or business_other account types
  *   pin_to_site_region - boolean - If true, we will ensure that all communications with this remote server are made through the primary region of the site.  This setting can also be overridden by a site-wide setting which will force it to true.
  *   port - int64 - Port for remote server.  Not needed for S3.
  *   rackspace_container - string - Rackspace: The name of the container (top level directory) where files will sync.
  *   rackspace_region - string - Rackspace: Three letter code for Rackspace region. See https://support.rackspace.com/how-to/about-regions/
  *   rackspace_username - string - Rackspace: username used to login to the Rackspace Cloud Control Panel.
  *   s3_bucket - string - S3 bucket name
  *   s3_compatible_access_key - string - S3-compatible: Access Key
  *   s3_compatible_bucket - string - S3-compatible: Bucket name
  *   s3_compatible_endpoint - string - S3-compatible: endpoint
  *   s3_compatible_region - string - S3-compatible: region
  *   s3_region - string - S3 region
  *   server_certificate - string - Remote server certificate
  *   server_host_key - string - Remote server SSH Host Key. If provided, we will require that the server host key matches the provided key. Uses OpenSSH format similar to what would go into ~/.ssh/known_hosts
  *   server_type - string - Remote server type.
  *   ssl - string - Should we require SSL?
  *   username - string - Remote server username.  Not needed for S3 buckets.
  *   wasabi_access_key - string - Wasabi: Access Key.
  *   wasabi_bucket - string - Wasabi: Bucket name
  *   wasabi_region - string - Wasabi: Region
  */
  public RemoteServer update(HashMap<String, Object> parameters) throws IOException {
    return RemoteServer.update(this.id, parameters, this.options);
  }

  /**
  */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    RemoteServer.delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    RemoteServer.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `name`, `server_type`, `backblaze_b2_bucket`, `google_cloud_storage_bucket`, `wasabi_bucket`, `s3_bucket`, `rackspace_container`, `azure_blob_storage_container`, `azure_files_storage_share_name`, `s3_compatible_bucket`, `filebase_bucket`, `cloudflare_bucket` or `linode_bucket`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `name`, `server_type`, `backblaze_b2_bucket`, `google_cloud_storage_bucket`, `wasabi_bucket`, `s3_bucket`, `rackspace_container`, `azure_blob_storage_container`, `azure_files_storage_share_name`, `s3_compatible_bucket`, `filebase_bucket`, `cloudflare_bucket` or `linode_bucket`. Valid field combinations are `[ server_type, name ]`, `[ backblaze_b2_bucket, name ]`, `[ google_cloud_storage_bucket, name ]`, `[ wasabi_bucket, name ]`, `[ s3_bucket, name ]`, `[ rackspace_container, name ]`, `[ azure_blob_storage_container, name ]`, `[ azure_files_storage_share_name, name ]`, `[ s3_compatible_bucket, name ]`, `[ filebase_bucket, name ]`, `[ cloudflare_bucket, name ]` or `[ linode_bucket, name ]`.
  *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `name`, `backblaze_b2_bucket`, `google_cloud_storage_bucket`, `wasabi_bucket`, `s3_bucket`, `rackspace_container`, `azure_blob_storage_container`, `azure_files_storage_share_name`, `s3_compatible_bucket`, `filebase_bucket`, `cloudflare_bucket` or `linode_bucket`. Valid field combinations are `[ backblaze_b2_bucket, name ]`, `[ google_cloud_storage_bucket, name ]`, `[ wasabi_bucket, name ]`, `[ s3_bucket, name ]`, `[ rackspace_container, name ]`, `[ azure_blob_storage_container, name ]`, `[ azure_files_storage_share_name, name ]`, `[ s3_compatible_bucket, name ]`, `[ filebase_bucket, name ]`, `[ cloudflare_bucket, name ]` or `[ linode_bucket, name ]`.
  */
  public static ListIterator<RemoteServer> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<RemoteServer> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<RemoteServer> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }
    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Map<String, String> parameters[\"sort_by\"]");
    }
    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Map<String, String> parameters[\"filter\"]");
    }
    if (parameters.containsKey("filter_prefix") && !(parameters.get("filter_prefix") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_prefix must be of type Map<String, String> parameters[\"filter_prefix\"]");
    }


    String url = String.format("%s%s/remote_servers", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<RemoteServer>> typeReference = new TypeReference<List<RemoteServer>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<RemoteServer> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<RemoteServer> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Remote Server ID.
  */
  public static RemoteServer find() throws RuntimeException {
    return find(null, null, null);
  }

  public static RemoteServer find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static RemoteServer find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static RemoteServer find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/remote_servers/%s", urlParts);

    TypeReference<RemoteServer> typeReference = new TypeReference<RemoteServer>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static RemoteServer get() throws RuntimeException {
    return get(null, null, null);
  }

  public static RemoteServer get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Remote Server ID.
  */
  public static RemoteServerConfigurationFile findConfigurationFile() throws RuntimeException {
    return findConfigurationFile(null, null, null);
  }

  public static RemoteServerConfigurationFile findConfigurationFile(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return findConfigurationFile(id, parameters, null);
  }

  public static RemoteServerConfigurationFile findConfigurationFile(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return findConfigurationFile(null, parameters, options);
  }

  public static RemoteServerConfigurationFile findConfigurationFile(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/remote_servers/%s/configuration_file", urlParts);

    TypeReference<RemoteServerConfigurationFile> typeReference = new TypeReference<RemoteServerConfigurationFile>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   password - string - Password, if needed.
  *   private_key - string - Private key, if needed.
  *   private_key_passphrase - string - Passphrase for private key if needed.
  *   reset_authentication - boolean - Reset authenticated account?
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
  *   rackspace_api_key - string - Rackspace: API key from the Rackspace Cloud Control Panel
  *   s3_compatible_secret_key - string - S3-compatible: Secret Key
  *   wasabi_secret_key - string - Wasabi: Secret Key
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
  *   cloudflare_access_key - string - Cloudflare: Access Key.
  *   cloudflare_bucket - string - Cloudflare: Bucket name
  *   cloudflare_endpoint - string - Cloudflare: endpoint
  *   dropbox_teams - boolean - Dropbox: If true, list Team folders in root?
  *   enable_dedicated_ips - boolean - `true` if remote server only accepts connections from dedicated IPs
  *   filebase_access_key - string - Filebase: Access Key.
  *   filebase_bucket - string - Filebase: Bucket name
  *   files_agent_permission_set - string - Local permissions for files agent. read_only, write_only, or read_write
  *   files_agent_root - string - Agent local root path
  *   files_agent_version - string - Files Agent version
  *   google_cloud_storage_bucket - string - Google Cloud Storage: Bucket Name
  *   google_cloud_storage_project_id - string - Google Cloud Storage: Project ID
  *   google_cloud_storage_region - string - Google Cloud Storage: Region
  *   google_cloud_storage_s3_compatible_access_key - string - Google Cloud Storage: S3-compatible Access Key.
  *   hostname - string - Hostname or IP address
  *   linode_access_key - string - Linode: Access Key
  *   linode_bucket - string - Linode: Bucket name
  *   linode_region - string - Linode: region
  *   max_connections - int64 - Max number of parallel connections.  Ignored for S3 connections (we will parallelize these as much as possible).
  *   name - string - Internal name for your reference
  *   one_drive_account_type - string - OneDrive: Either personal or business_other account types
  *   pin_to_site_region - boolean - If true, we will ensure that all communications with this remote server are made through the primary region of the site.  This setting can also be overridden by a site-wide setting which will force it to true.
  *   port - int64 - Port for remote server.  Not needed for S3.
  *   rackspace_container - string - Rackspace: The name of the container (top level directory) where files will sync.
  *   rackspace_region - string - Rackspace: Three letter code for Rackspace region. See https://support.rackspace.com/how-to/about-regions/
  *   rackspace_username - string - Rackspace: username used to login to the Rackspace Cloud Control Panel.
  *   s3_bucket - string - S3 bucket name
  *   s3_compatible_access_key - string - S3-compatible: Access Key
  *   s3_compatible_bucket - string - S3-compatible: Bucket name
  *   s3_compatible_endpoint - string - S3-compatible: endpoint
  *   s3_compatible_region - string - S3-compatible: region
  *   s3_region - string - S3 region
  *   server_certificate - string - Remote server certificate
  *   server_host_key - string - Remote server SSH Host Key. If provided, we will require that the server host key matches the provided key. Uses OpenSSH format similar to what would go into ~/.ssh/known_hosts
  *   server_type - string - Remote server type.
  *   ssl - string - Should we require SSL?
  *   username - string - Remote server username.  Not needed for S3 buckets.
  *   wasabi_access_key - string - Wasabi: Access Key.
  *   wasabi_bucket - string - Wasabi: Bucket name
  *   wasabi_region - string - Wasabi: Region
  */
  public static RemoteServer create() throws RuntimeException {
    return create(null, null);
  }

  public static RemoteServer create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static RemoteServer create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



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
    if (parameters.containsKey("rackspace_api_key") && !(parameters.get("rackspace_api_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: rackspace_api_key must be of type String parameters[\"rackspace_api_key\"]");
    }
    if (parameters.containsKey("s3_compatible_secret_key") && !(parameters.get("s3_compatible_secret_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: s3_compatible_secret_key must be of type String parameters[\"s3_compatible_secret_key\"]");
    }
    if (parameters.containsKey("wasabi_secret_key") && !(parameters.get("wasabi_secret_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: wasabi_secret_key must be of type String parameters[\"wasabi_secret_key\"]");
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
    if (parameters.containsKey("cloudflare_access_key") && !(parameters.get("cloudflare_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cloudflare_access_key must be of type String parameters[\"cloudflare_access_key\"]");
    }
    if (parameters.containsKey("cloudflare_bucket") && !(parameters.get("cloudflare_bucket") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cloudflare_bucket must be of type String parameters[\"cloudflare_bucket\"]");
    }
    if (parameters.containsKey("cloudflare_endpoint") && !(parameters.get("cloudflare_endpoint") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cloudflare_endpoint must be of type String parameters[\"cloudflare_endpoint\"]");
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
    if (parameters.containsKey("files_agent_permission_set") && !(parameters.get("files_agent_permission_set") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: files_agent_permission_set must be of type String parameters[\"files_agent_permission_set\"]");
    }
    if (parameters.containsKey("files_agent_root") && !(parameters.get("files_agent_root") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: files_agent_root must be of type String parameters[\"files_agent_root\"]");
    }
    if (parameters.containsKey("files_agent_version") && !(parameters.get("files_agent_version") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: files_agent_version must be of type String parameters[\"files_agent_version\"]");
    }
    if (parameters.containsKey("google_cloud_storage_bucket") && !(parameters.get("google_cloud_storage_bucket") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: google_cloud_storage_bucket must be of type String parameters[\"google_cloud_storage_bucket\"]");
    }
    if (parameters.containsKey("google_cloud_storage_project_id") && !(parameters.get("google_cloud_storage_project_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: google_cloud_storage_project_id must be of type String parameters[\"google_cloud_storage_project_id\"]");
    }
    if (parameters.containsKey("google_cloud_storage_region") && !(parameters.get("google_cloud_storage_region") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: google_cloud_storage_region must be of type String parameters[\"google_cloud_storage_region\"]");
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
    if (parameters.containsKey("rackspace_container") && !(parameters.get("rackspace_container") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: rackspace_container must be of type String parameters[\"rackspace_container\"]");
    }
    if (parameters.containsKey("rackspace_region") && !(parameters.get("rackspace_region") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: rackspace_region must be of type String parameters[\"rackspace_region\"]");
    }
    if (parameters.containsKey("rackspace_username") && !(parameters.get("rackspace_username") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: rackspace_username must be of type String parameters[\"rackspace_username\"]");
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


    String url = String.format("%s%s/remote_servers", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<RemoteServer> typeReference = new TypeReference<RemoteServer>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Post local changes, check in, and download configuration file (used by some Remote Server integrations, such as the Files.com Agent)
  *
  * Parameters:
  *   api_token - string - Files Agent API Token
  *   permission_set - string - The permission set for the agent ['read_write', 'read_only', 'write_only']
  *   root - string - The root directory for the agent
  *   hostname - string
  *   port - int64 - Incoming port for files agent connections
  *   status - string - either running or shutdown
  *   config_version - string - agent config version
  *   private_key - string - The private key for the agent
  *   public_key - string - public key
  *   server_host_key - string
  *   subdomain - string - Files.com subdomain site name
  */
  public static RemoteServerConfigurationFile configurationFile() throws RuntimeException {
    return configurationFile(null, null, null);
  }

  public static RemoteServerConfigurationFile configurationFile(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return configurationFile(id, parameters, null);
  }

  public static RemoteServerConfigurationFile configurationFile(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return configurationFile(null, parameters, options);
  }

  public static RemoteServerConfigurationFile configurationFile(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }
    if (parameters.containsKey("api_token") && !(parameters.get("api_token") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: api_token must be of type String parameters[\"api_token\"]");
    }
    if (parameters.containsKey("permission_set") && !(parameters.get("permission_set") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: permission_set must be of type String parameters[\"permission_set\"]");
    }
    if (parameters.containsKey("root") && !(parameters.get("root") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: root must be of type String parameters[\"root\"]");
    }
    if (parameters.containsKey("hostname") && !(parameters.get("hostname") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: hostname must be of type String parameters[\"hostname\"]");
    }
    if (parameters.containsKey("port") && !(parameters.get("port") instanceof Long || parameters.get("port") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: port must be of type Long or Integer parameters[\"port\"]");
    }
    if (parameters.containsKey("status") && !(parameters.get("status") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: status must be of type String parameters[\"status\"]");
    }
    if (parameters.containsKey("config_version") && !(parameters.get("config_version") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: config_version must be of type String parameters[\"config_version\"]");
    }
    if (parameters.containsKey("private_key") && !(parameters.get("private_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: private_key must be of type String parameters[\"private_key\"]");
    }
    if (parameters.containsKey("public_key") && !(parameters.get("public_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: public_key must be of type String parameters[\"public_key\"]");
    }
    if (parameters.containsKey("server_host_key") && !(parameters.get("server_host_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: server_host_key must be of type String parameters[\"server_host_key\"]");
    }
    if (parameters.containsKey("subdomain") && !(parameters.get("subdomain") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: subdomain must be of type String parameters[\"subdomain\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/remote_servers/%s/configuration_file", urlParts);

    TypeReference<RemoteServerConfigurationFile> typeReference = new TypeReference<RemoteServerConfigurationFile>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   password - string - Password, if needed.
  *   private_key - string - Private key, if needed.
  *   private_key_passphrase - string - Passphrase for private key if needed.
  *   reset_authentication - boolean - Reset authenticated account?
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
  *   rackspace_api_key - string - Rackspace: API key from the Rackspace Cloud Control Panel
  *   s3_compatible_secret_key - string - S3-compatible: Secret Key
  *   wasabi_secret_key - string - Wasabi: Secret Key
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
  *   cloudflare_access_key - string - Cloudflare: Access Key.
  *   cloudflare_bucket - string - Cloudflare: Bucket name
  *   cloudflare_endpoint - string - Cloudflare: endpoint
  *   dropbox_teams - boolean - Dropbox: If true, list Team folders in root?
  *   enable_dedicated_ips - boolean - `true` if remote server only accepts connections from dedicated IPs
  *   filebase_access_key - string - Filebase: Access Key.
  *   filebase_bucket - string - Filebase: Bucket name
  *   files_agent_permission_set - string - Local permissions for files agent. read_only, write_only, or read_write
  *   files_agent_root - string - Agent local root path
  *   files_agent_version - string - Files Agent version
  *   google_cloud_storage_bucket - string - Google Cloud Storage: Bucket Name
  *   google_cloud_storage_project_id - string - Google Cloud Storage: Project ID
  *   google_cloud_storage_region - string - Google Cloud Storage: Region
  *   google_cloud_storage_s3_compatible_access_key - string - Google Cloud Storage: S3-compatible Access Key.
  *   hostname - string - Hostname or IP address
  *   linode_access_key - string - Linode: Access Key
  *   linode_bucket - string - Linode: Bucket name
  *   linode_region - string - Linode: region
  *   max_connections - int64 - Max number of parallel connections.  Ignored for S3 connections (we will parallelize these as much as possible).
  *   name - string - Internal name for your reference
  *   one_drive_account_type - string - OneDrive: Either personal or business_other account types
  *   pin_to_site_region - boolean - If true, we will ensure that all communications with this remote server are made through the primary region of the site.  This setting can also be overridden by a site-wide setting which will force it to true.
  *   port - int64 - Port for remote server.  Not needed for S3.
  *   rackspace_container - string - Rackspace: The name of the container (top level directory) where files will sync.
  *   rackspace_region - string - Rackspace: Three letter code for Rackspace region. See https://support.rackspace.com/how-to/about-regions/
  *   rackspace_username - string - Rackspace: username used to login to the Rackspace Cloud Control Panel.
  *   s3_bucket - string - S3 bucket name
  *   s3_compatible_access_key - string - S3-compatible: Access Key
  *   s3_compatible_bucket - string - S3-compatible: Bucket name
  *   s3_compatible_endpoint - string - S3-compatible: endpoint
  *   s3_compatible_region - string - S3-compatible: region
  *   s3_region - string - S3 region
  *   server_certificate - string - Remote server certificate
  *   server_host_key - string - Remote server SSH Host Key. If provided, we will require that the server host key matches the provided key. Uses OpenSSH format similar to what would go into ~/.ssh/known_hosts
  *   server_type - string - Remote server type.
  *   ssl - string - Should we require SSL?
  *   username - string - Remote server username.  Not needed for S3 buckets.
  *   wasabi_access_key - string - Wasabi: Access Key.
  *   wasabi_bucket - string - Wasabi: Bucket name
  *   wasabi_region - string - Wasabi: Region
  */
  public static RemoteServer update() throws RuntimeException {
    return update(null, null, null);
  }

  public static RemoteServer update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static RemoteServer update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static RemoteServer update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

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
    if (parameters.containsKey("rackspace_api_key") && !(parameters.get("rackspace_api_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: rackspace_api_key must be of type String parameters[\"rackspace_api_key\"]");
    }
    if (parameters.containsKey("s3_compatible_secret_key") && !(parameters.get("s3_compatible_secret_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: s3_compatible_secret_key must be of type String parameters[\"s3_compatible_secret_key\"]");
    }
    if (parameters.containsKey("wasabi_secret_key") && !(parameters.get("wasabi_secret_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: wasabi_secret_key must be of type String parameters[\"wasabi_secret_key\"]");
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
    if (parameters.containsKey("cloudflare_access_key") && !(parameters.get("cloudflare_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cloudflare_access_key must be of type String parameters[\"cloudflare_access_key\"]");
    }
    if (parameters.containsKey("cloudflare_bucket") && !(parameters.get("cloudflare_bucket") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cloudflare_bucket must be of type String parameters[\"cloudflare_bucket\"]");
    }
    if (parameters.containsKey("cloudflare_endpoint") && !(parameters.get("cloudflare_endpoint") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cloudflare_endpoint must be of type String parameters[\"cloudflare_endpoint\"]");
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
    if (parameters.containsKey("files_agent_permission_set") && !(parameters.get("files_agent_permission_set") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: files_agent_permission_set must be of type String parameters[\"files_agent_permission_set\"]");
    }
    if (parameters.containsKey("files_agent_root") && !(parameters.get("files_agent_root") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: files_agent_root must be of type String parameters[\"files_agent_root\"]");
    }
    if (parameters.containsKey("files_agent_version") && !(parameters.get("files_agent_version") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: files_agent_version must be of type String parameters[\"files_agent_version\"]");
    }
    if (parameters.containsKey("google_cloud_storage_bucket") && !(parameters.get("google_cloud_storage_bucket") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: google_cloud_storage_bucket must be of type String parameters[\"google_cloud_storage_bucket\"]");
    }
    if (parameters.containsKey("google_cloud_storage_project_id") && !(parameters.get("google_cloud_storage_project_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: google_cloud_storage_project_id must be of type String parameters[\"google_cloud_storage_project_id\"]");
    }
    if (parameters.containsKey("google_cloud_storage_region") && !(parameters.get("google_cloud_storage_region") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: google_cloud_storage_region must be of type String parameters[\"google_cloud_storage_region\"]");
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
    if (parameters.containsKey("rackspace_container") && !(parameters.get("rackspace_container") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: rackspace_container must be of type String parameters[\"rackspace_container\"]");
    }
    if (parameters.containsKey("rackspace_region") && !(parameters.get("rackspace_region") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: rackspace_region must be of type String parameters[\"rackspace_region\"]");
    }
    if (parameters.containsKey("rackspace_username") && !(parameters.get("rackspace_username") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: rackspace_username must be of type String parameters[\"rackspace_username\"]");
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


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/remote_servers/%s", urlParts);

    TypeReference<RemoteServer> typeReference = new TypeReference<RemoteServer>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/remote_servers/%s", urlParts);

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
