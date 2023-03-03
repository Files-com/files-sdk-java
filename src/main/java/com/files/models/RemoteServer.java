package com.files.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.ModelUtils;
import com.files.util.FilesInputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoteServer {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
    .builder()
    .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
    .build();

  public RemoteServer() {
    this(null, null);
  }

  public RemoteServer(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public RemoteServer(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
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
  * If true, we will ensure that all communications with this remote server are made through the primary region of the site.  This setting can also be overridden by a sitewide setting which will force it to true.
  */
  @Getter
  @Setter
  @JsonProperty("pin_to_site_region")
  public Boolean pinToSiteRegion;

  /**
  * If set, all communciations with this remote server are made through the provided region.
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
  * Google Cloud Storage bucket name
  */
  @Getter
  @Setter
  @JsonProperty("google_cloud_storage_bucket")
  public String googleCloudStorageBucket;

  /**
  * Google Cloud Project ID
  */
  @Getter
  @Setter
  @JsonProperty("google_cloud_storage_project_id")
  public String googleCloudStorageProjectId;

  /**
  * Backblaze B2 Cloud Storage S3 Endpoint
  */
  @Getter
  @Setter
  @JsonProperty("backblaze_b2_s3_endpoint")
  public String backblazeB2S3Endpoint;

  /**
  * Backblaze B2 Cloud Storage Bucket name
  */
  @Getter
  @Setter
  @JsonProperty("backblaze_b2_bucket")
  public String backblazeB2Bucket;

  /**
  * Wasabi Bucket name
  */
  @Getter
  @Setter
  @JsonProperty("wasabi_bucket")
  public String wasabiBucket;

  /**
  * Wasabi region
  */
  @Getter
  @Setter
  @JsonProperty("wasabi_region")
  public String wasabiRegion;

  /**
  * Wasabi access key.
  */
  @Getter
  @Setter
  @JsonProperty("wasabi_access_key")
  public String wasabiAccessKey;

  /**
  * Rackspace username used to login to the Rackspace Cloud Control Panel.
  */
  @Getter
  @Setter
  @JsonProperty("rackspace_username")
  public String rackspaceUsername;

  /**
  * Three letter airport code for Rackspace region. See https://support.rackspace.com/how-to/about-regions/
  */
  @Getter
  @Setter
  @JsonProperty("rackspace_region")
  public String rackspaceRegion;

  /**
  * The name of the container (top level directory) where files will sync.
  */
  @Getter
  @Setter
  @JsonProperty("rackspace_container")
  public String rackspaceContainer;

  /**
  * Returns link to login with an Oauth provider
  */
  @Getter
  @Setter
  @JsonProperty("auth_setup_link")
  public String authSetupLink;

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
  * Either personal or business_other account types
  */
  @Getter
  @Setter
  @JsonProperty("one_drive_account_type")
  public String oneDriveAccountType;

  /**
  * Azure Blob Storage Account name
  */
  @Getter
  @Setter
  @JsonProperty("azure_blob_storage_account")
  public String azureBlobStorageAccount;

  /**
  * Shared Access Signature (SAS) token
  */
  @Getter
  @Setter
  @JsonProperty("azure_blob_storage_sas_token")
  public String azureBlobStorageSasToken;

  /**
  * Azure Blob Storage Container name
  */
  @Getter
  @Setter
  @JsonProperty("azure_blob_storage_container")
  public String azureBlobStorageContainer;

  /**
  * Azure File Storage Account name
  */
  @Getter
  @Setter
  @JsonProperty("azure_files_storage_account")
  public String azureFilesStorageAccount;

  /**
  * Shared Access Signature (SAS) token
  */
  @Getter
  @Setter
  @JsonProperty("azure_files_storage_sas_token")
  public String azureFilesStorageSasToken;

  /**
  * Azure File Storage Share name
  */
  @Getter
  @Setter
  @JsonProperty("azure_files_storage_share_name")
  public String azureFilesStorageShareName;

  /**
  * S3-compatible Bucket name
  */
  @Getter
  @Setter
  @JsonProperty("s3_compatible_bucket")
  public String s3CompatibleBucket;

  /**
  * S3-compatible endpoint
  */
  @Getter
  @Setter
  @JsonProperty("s3_compatible_endpoint")
  public String s3CompatibleEndpoint;

  /**
  * S3-compatible endpoint
  */
  @Getter
  @Setter
  @JsonProperty("s3_compatible_region")
  public String s3CompatibleRegion;

  /**
  * S3-compatible Access Key.
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
  * Filebase Bucket name
  */
  @Getter
  @Setter
  @JsonProperty("filebase_bucket")
  public String filebaseBucket;

  /**
  * Filebase Access Key.
  */
  @Getter
  @Setter
  @JsonProperty("filebase_access_key")
  public String filebaseAccessKey;

  /**
  * AWS secret key.
  */
  @Getter
  @Setter
  @JsonProperty("aws_secret_key")
  public String awsSecretKey;

  /**
  * Password if needed.
  */
  @Getter
  @Setter
  @JsonProperty("password")
  public String password;

  /**
  * Private key if needed.
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
  * SSL client certificate.
  */
  @Getter
  @Setter
  @JsonProperty("ssl_certificate")
  public String sslCertificate;

  /**
  * A JSON file that contains the private key. To generate see https://cloud.google.com/storage/docs/json_api/v1/how-tos/authorizing#APIKey
  */
  @Getter
  @Setter
  @JsonProperty("google_cloud_storage_credentials_json")
  public String googleCloudStorageCredentialsJson;

  /**
  * Wasabi secret key.
  */
  @Getter
  @Setter
  @JsonProperty("wasabi_secret_key")
  public String wasabiSecretKey;

  /**
  * Backblaze B2 Cloud Storage keyID.
  */
  @Getter
  @Setter
  @JsonProperty("backblaze_b2_key_id")
  public String backblazeB2KeyId;

  /**
  * Backblaze B2 Cloud Storage applicationKey.
  */
  @Getter
  @Setter
  @JsonProperty("backblaze_b2_application_key")
  public String backblazeB2ApplicationKey;

  /**
  * Rackspace API key from the Rackspace Cloud Control Panel.
  */
  @Getter
  @Setter
  @JsonProperty("rackspace_api_key")
  public String rackspaceApiKey;

  /**
  * Reset authenticated account
  */
  @Getter
  @Setter
  @JsonProperty("reset_authentication")
  public Boolean resetAuthentication;

  /**
  * Azure Blob Storage secret key.
  */
  @Getter
  @Setter
  @JsonProperty("azure_blob_storage_access_key")
  public String azureBlobStorageAccessKey;

  /**
  * Azure File Storage access key.
  */
  @Getter
  @Setter
  @JsonProperty("azure_files_storage_access_key")
  public String azureFilesStorageAccessKey;

  /**
  * S3-compatible secret key
  */
  @Getter
  @Setter
  @JsonProperty("s3_compatible_secret_key")
  public String s3CompatibleSecretKey;

  /**
  * Filebase secret key
  */
  @Getter
  @Setter
  @JsonProperty("filebase_secret_key")
  public String filebaseSecretKey;

  /**
  * Post local changes, check in, and download configuration file (used by some Remote Server integrations, such as the Files.com Agent)
  *
  * Parameters:
  *   api_token - string - Files Agent API Token
  *   permission_set - string -
  *   root - string - Agent local root path
  *   hostname - string
  *   port - int64 - Incoming port for files agent connections
  *   status - string - either running or shutdown
  *   config_version - string - agent config version
  *   private_key - string - private key
  *   public_key - string - public key
  *   server_host_key - string
  *   subdomain - string
  */
  public RemoteServer configurationFile(HashMap<String, Object> parameters) {
    return configurationFile(parameters);
  }

  /**
  * Parameters:
  *   aws_access_key - string - AWS Access Key.
  *   aws_secret_key - string - AWS secret key.
  *   password - string - Password if needed.
  *   private_key - string - Private key if needed.
  *   private_key_passphrase - string - Passphrase for private key if needed.
  *   ssl_certificate - string - SSL client certificate.
  *   google_cloud_storage_credentials_json - string - A JSON file that contains the private key. To generate see https://cloud.google.com/storage/docs/json_api/v1/how-tos/authorizing#APIKey
  *   wasabi_access_key - string - Wasabi access key.
  *   wasabi_secret_key - string - Wasabi secret key.
  *   backblaze_b2_key_id - string - Backblaze B2 Cloud Storage keyID.
  *   backblaze_b2_application_key - string - Backblaze B2 Cloud Storage applicationKey.
  *   rackspace_api_key - string - Rackspace API key from the Rackspace Cloud Control Panel.
  *   reset_authentication - boolean - Reset authenticated account
  *   azure_blob_storage_access_key - string - Azure Blob Storage secret key.
  *   azure_files_storage_access_key - string - Azure File Storage access key.
  *   hostname - string - Hostname or IP address
  *   name - string - Internal name for your reference
  *   max_connections - int64 - Max number of parallel connections.  Ignored for S3 connections (we will parallelize these as much as possible).
  *   pin_to_site_region - boolean - If true, we will ensure that all communications with this remote server are made through the primary region of the site.  This setting can also be overridden by a sitewide setting which will force it to true.
  *   port - int64 - Port for remote server.  Not needed for S3.
  *   s3_bucket - string - S3 bucket name
  *   s3_region - string - S3 region
  *   server_certificate - string - Remote server certificate
  *   server_host_key - string - Remote server SSH Host Key. If provided, we will require that the server host key matches the provided key. Uses OpenSSH format similar to what would go into ~/.ssh/known_hosts
  *   server_type - string - Remote server type.
  *   ssl - string - Should we require SSL?
  *   username - string - Remote server username.  Not needed for S3 buckets.
  *   google_cloud_storage_bucket - string - Google Cloud Storage bucket name
  *   google_cloud_storage_project_id - string - Google Cloud Project ID
  *   backblaze_b2_bucket - string - Backblaze B2 Cloud Storage Bucket name
  *   backblaze_b2_s3_endpoint - string - Backblaze B2 Cloud Storage S3 Endpoint
  *   wasabi_bucket - string - Wasabi Bucket name
  *   wasabi_region - string - Wasabi region
  *   rackspace_username - string - Rackspace username used to login to the Rackspace Cloud Control Panel.
  *   rackspace_region - string - Three letter airport code for Rackspace region. See https://support.rackspace.com/how-to/about-regions/
  *   rackspace_container - string - The name of the container (top level directory) where files will sync.
  *   one_drive_account_type - string - Either personal or business_other account types
  *   azure_blob_storage_account - string - Azure Blob Storage Account name
  *   azure_blob_storage_container - string - Azure Blob Storage Container name
  *   azure_blob_storage_sas_token - string - Shared Access Signature (SAS) token
  *   azure_files_storage_account - string - Azure File Storage Account name
  *   azure_files_storage_share_name - string - Azure File Storage Share name
  *   azure_files_storage_sas_token - string - Shared Access Signature (SAS) token
  *   s3_compatible_bucket - string - S3-compatible Bucket name
  *   s3_compatible_endpoint - string - S3-compatible endpoint
  *   s3_compatible_region - string - S3-compatible endpoint
  *   enable_dedicated_ips - boolean - `true` if remote server only accepts connections from dedicated IPs
  *   s3_compatible_access_key - string - S3-compatible Access Key.
  *   s3_compatible_secret_key - string - S3-compatible secret key
  *   files_agent_root - string - Agent local root path
  *   files_agent_permission_set - string - Local permissions for files agent. read_only, write_only, or read_write
  *   filebase_access_key - string - Filebase Access Key.
  *   filebase_secret_key - string - Filebase secret key
  *   filebase_bucket - string - Filebase Bucket name
  */
  public RemoteServer update(HashMap<String, Object> parameters) {
    return update(parameters);
  }

  /**
  */
  public RemoteServer delete(HashMap<String, Object> parameters) {
    return delete(parameters);
  }

  public void destroy(HashMap<String, Object> parameters) {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    if (parameters.containsKey("id") && parameters.get("id") != null) {
      update(parameters);
    } else {
      RemoteServer newObject = RemoteServer.create(parameters, this.options);
    }
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  */
  public static List<RemoteServer> list() throws IOException {
    return list(null,null);
  }
  public static List<RemoteServer> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  public static List<RemoteServer> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }



    String url = String.format("%s%s/remote_servers", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<RemoteServer>> typeReference = new TypeReference<List<RemoteServer>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<RemoteServer> all() throws IOException {
    return all(null, null);
  }

  public static List<RemoteServer> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Remote Server ID.
  */
  public static List<RemoteServer> find() throws IOException {
    return find(null, null,null);
  }
  public static List<RemoteServer> find(Long id,  HashMap<String, Object> parameters) throws IOException {
    return find(id, parameters, null);
  }

  public static List<RemoteServer> find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(null, parameters, options);
  }

  public static List<RemoteServer> find(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = ((Long) parameters.get("id"));
    }


    if (!(id instanceof Long) ) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex){
      }
    }

    String url = String.format("%s%s/remote_servers/%s", urlParts);

    TypeReference<List<RemoteServer>> typeReference = new TypeReference<List<RemoteServer>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<RemoteServer> get() throws IOException {
    return get(null, null, null);
  }

  public static List<RemoteServer> get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Remote Server ID.
  */
  public static List<RemoteServer> findConfigurationFile() throws IOException {
    return findConfigurationFile(null, null,null);
  }
  public static List<RemoteServer> findConfigurationFile(Long id,  HashMap<String, Object> parameters) throws IOException {
    return findConfigurationFile(id, parameters, null);
  }

  public static List<RemoteServer> findConfigurationFile(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return findConfigurationFile(null, parameters, options);
  }

  public static List<RemoteServer> findConfigurationFile(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = ((Long) parameters.get("id"));
    }


    if (!(id instanceof Long) ) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex){
      }
    }

    String url = String.format("%s%s/remote_servers/%s/configuration_file", urlParts);

    TypeReference<List<RemoteServer>> typeReference = new TypeReference<List<RemoteServer>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   aws_access_key - string - AWS Access Key.
  *   aws_secret_key - string - AWS secret key.
  *   password - string - Password if needed.
  *   private_key - string - Private key if needed.
  *   private_key_passphrase - string - Passphrase for private key if needed.
  *   ssl_certificate - string - SSL client certificate.
  *   google_cloud_storage_credentials_json - string - A JSON file that contains the private key. To generate see https://cloud.google.com/storage/docs/json_api/v1/how-tos/authorizing#APIKey
  *   wasabi_access_key - string - Wasabi access key.
  *   wasabi_secret_key - string - Wasabi secret key.
  *   backblaze_b2_key_id - string - Backblaze B2 Cloud Storage keyID.
  *   backblaze_b2_application_key - string - Backblaze B2 Cloud Storage applicationKey.
  *   rackspace_api_key - string - Rackspace API key from the Rackspace Cloud Control Panel.
  *   reset_authentication - boolean - Reset authenticated account
  *   azure_blob_storage_access_key - string - Azure Blob Storage secret key.
  *   azure_files_storage_access_key - string - Azure File Storage access key.
  *   hostname - string - Hostname or IP address
  *   name - string - Internal name for your reference
  *   max_connections - int64 - Max number of parallel connections.  Ignored for S3 connections (we will parallelize these as much as possible).
  *   pin_to_site_region - boolean - If true, we will ensure that all communications with this remote server are made through the primary region of the site.  This setting can also be overridden by a sitewide setting which will force it to true.
  *   port - int64 - Port for remote server.  Not needed for S3.
  *   s3_bucket - string - S3 bucket name
  *   s3_region - string - S3 region
  *   server_certificate - string - Remote server certificate
  *   server_host_key - string - Remote server SSH Host Key. If provided, we will require that the server host key matches the provided key. Uses OpenSSH format similar to what would go into ~/.ssh/known_hosts
  *   server_type - string - Remote server type.
  *   ssl - string - Should we require SSL?
  *   username - string - Remote server username.  Not needed for S3 buckets.
  *   google_cloud_storage_bucket - string - Google Cloud Storage bucket name
  *   google_cloud_storage_project_id - string - Google Cloud Project ID
  *   backblaze_b2_bucket - string - Backblaze B2 Cloud Storage Bucket name
  *   backblaze_b2_s3_endpoint - string - Backblaze B2 Cloud Storage S3 Endpoint
  *   wasabi_bucket - string - Wasabi Bucket name
  *   wasabi_region - string - Wasabi region
  *   rackspace_username - string - Rackspace username used to login to the Rackspace Cloud Control Panel.
  *   rackspace_region - string - Three letter airport code for Rackspace region. See https://support.rackspace.com/how-to/about-regions/
  *   rackspace_container - string - The name of the container (top level directory) where files will sync.
  *   one_drive_account_type - string - Either personal or business_other account types
  *   azure_blob_storage_account - string - Azure Blob Storage Account name
  *   azure_blob_storage_container - string - Azure Blob Storage Container name
  *   azure_blob_storage_sas_token - string - Shared Access Signature (SAS) token
  *   azure_files_storage_account - string - Azure File Storage Account name
  *   azure_files_storage_share_name - string - Azure File Storage Share name
  *   azure_files_storage_sas_token - string - Shared Access Signature (SAS) token
  *   s3_compatible_bucket - string - S3-compatible Bucket name
  *   s3_compatible_endpoint - string - S3-compatible endpoint
  *   s3_compatible_region - string - S3-compatible endpoint
  *   enable_dedicated_ips - boolean - `true` if remote server only accepts connections from dedicated IPs
  *   s3_compatible_access_key - string - S3-compatible Access Key.
  *   s3_compatible_secret_key - string - S3-compatible secret key
  *   files_agent_root - string - Agent local root path
  *   files_agent_permission_set - string - Local permissions for files agent. read_only, write_only, or read_write
  *   filebase_access_key - string - Filebase Access Key.
  *   filebase_secret_key - string - Filebase secret key
  *   filebase_bucket - string - Filebase Bucket name
  */
  public static RemoteServer create() throws IOException {
    return create(null,null);
  }
  public static RemoteServer create( HashMap<String, Object> parameters) throws IOException {
    return create(parameters, null);
  }


  public static RemoteServer create( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (parameters.containsKey("aws_access_key") && !(parameters.get("aws_access_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: aws_access_key must be of type String parameters[\"aws_access_key\"]");
    }
    if (parameters.containsKey("aws_secret_key") && !(parameters.get("aws_secret_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: aws_secret_key must be of type String parameters[\"aws_secret_key\"]");
    }
    if (parameters.containsKey("password") && !(parameters.get("password") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: password must be of type String parameters[\"password\"]");
    }
    if (parameters.containsKey("private_key") && !(parameters.get("private_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: private_key must be of type String parameters[\"private_key\"]");
    }
    if (parameters.containsKey("private_key_passphrase") && !(parameters.get("private_key_passphrase") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: private_key_passphrase must be of type String parameters[\"private_key_passphrase\"]");
    }
    if (parameters.containsKey("ssl_certificate") && !(parameters.get("ssl_certificate") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: ssl_certificate must be of type String parameters[\"ssl_certificate\"]");
    }
    if (parameters.containsKey("google_cloud_storage_credentials_json") && !(parameters.get("google_cloud_storage_credentials_json") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: google_cloud_storage_credentials_json must be of type String parameters[\"google_cloud_storage_credentials_json\"]");
    }
    if (parameters.containsKey("wasabi_access_key") && !(parameters.get("wasabi_access_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: wasabi_access_key must be of type String parameters[\"wasabi_access_key\"]");
    }
    if (parameters.containsKey("wasabi_secret_key") && !(parameters.get("wasabi_secret_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: wasabi_secret_key must be of type String parameters[\"wasabi_secret_key\"]");
    }
    if (parameters.containsKey("backblaze_b2_key_id") && !(parameters.get("backblaze_b2_key_id") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: backblaze_b2_key_id must be of type String parameters[\"backblaze_b2_key_id\"]");
    }
    if (parameters.containsKey("backblaze_b2_application_key") && !(parameters.get("backblaze_b2_application_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: backblaze_b2_application_key must be of type String parameters[\"backblaze_b2_application_key\"]");
    }
    if (parameters.containsKey("rackspace_api_key") && !(parameters.get("rackspace_api_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: rackspace_api_key must be of type String parameters[\"rackspace_api_key\"]");
    }
    if (parameters.containsKey("reset_authentication") && !(parameters.get("reset_authentication") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: reset_authentication must be of type Boolean parameters[\"reset_authentication\"]");
    }
    if (parameters.containsKey("azure_blob_storage_access_key") && !(parameters.get("azure_blob_storage_access_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: azure_blob_storage_access_key must be of type String parameters[\"azure_blob_storage_access_key\"]");
    }
    if (parameters.containsKey("azure_files_storage_access_key") && !(parameters.get("azure_files_storage_access_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: azure_files_storage_access_key must be of type String parameters[\"azure_files_storage_access_key\"]");
    }
    if (parameters.containsKey("hostname") && !(parameters.get("hostname") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: hostname must be of type String parameters[\"hostname\"]");
    }
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("max_connections") && !(parameters.get("max_connections") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: max_connections must be of type Long parameters[\"max_connections\"]");
    }
    if (parameters.containsKey("pin_to_site_region") && !(parameters.get("pin_to_site_region") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: pin_to_site_region must be of type Boolean parameters[\"pin_to_site_region\"]");
    }
    if (parameters.containsKey("port") && !(parameters.get("port") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: port must be of type Long parameters[\"port\"]");
    }
    if (parameters.containsKey("s3_bucket") && !(parameters.get("s3_bucket") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: s3_bucket must be of type String parameters[\"s3_bucket\"]");
    }
    if (parameters.containsKey("s3_region") && !(parameters.get("s3_region") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: s3_region must be of type String parameters[\"s3_region\"]");
    }
    if (parameters.containsKey("server_certificate") && !(parameters.get("server_certificate") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: server_certificate must be of type String parameters[\"server_certificate\"]");
    }
    if (parameters.containsKey("server_host_key") && !(parameters.get("server_host_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: server_host_key must be of type String parameters[\"server_host_key\"]");
    }
    if (parameters.containsKey("server_type") && !(parameters.get("server_type") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: server_type must be of type String parameters[\"server_type\"]");
    }
    if (parameters.containsKey("ssl") && !(parameters.get("ssl") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: ssl must be of type String parameters[\"ssl\"]");
    }
    if (parameters.containsKey("username") && !(parameters.get("username") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: username must be of type String parameters[\"username\"]");
    }
    if (parameters.containsKey("google_cloud_storage_bucket") && !(parameters.get("google_cloud_storage_bucket") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: google_cloud_storage_bucket must be of type String parameters[\"google_cloud_storage_bucket\"]");
    }
    if (parameters.containsKey("google_cloud_storage_project_id") && !(parameters.get("google_cloud_storage_project_id") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: google_cloud_storage_project_id must be of type String parameters[\"google_cloud_storage_project_id\"]");
    }
    if (parameters.containsKey("backblaze_b2_bucket") && !(parameters.get("backblaze_b2_bucket") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: backblaze_b2_bucket must be of type String parameters[\"backblaze_b2_bucket\"]");
    }
    if (parameters.containsKey("backblaze_b2_s3_endpoint") && !(parameters.get("backblaze_b2_s3_endpoint") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: backblaze_b2_s3_endpoint must be of type String parameters[\"backblaze_b2_s3_endpoint\"]");
    }
    if (parameters.containsKey("wasabi_bucket") && !(parameters.get("wasabi_bucket") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: wasabi_bucket must be of type String parameters[\"wasabi_bucket\"]");
    }
    if (parameters.containsKey("wasabi_region") && !(parameters.get("wasabi_region") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: wasabi_region must be of type String parameters[\"wasabi_region\"]");
    }
    if (parameters.containsKey("rackspace_username") && !(parameters.get("rackspace_username") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: rackspace_username must be of type String parameters[\"rackspace_username\"]");
    }
    if (parameters.containsKey("rackspace_region") && !(parameters.get("rackspace_region") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: rackspace_region must be of type String parameters[\"rackspace_region\"]");
    }
    if (parameters.containsKey("rackspace_container") && !(parameters.get("rackspace_container") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: rackspace_container must be of type String parameters[\"rackspace_container\"]");
    }
    if (parameters.containsKey("one_drive_account_type") && !(parameters.get("one_drive_account_type") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: one_drive_account_type must be of type String parameters[\"one_drive_account_type\"]");
    }
    if (parameters.containsKey("azure_blob_storage_account") && !(parameters.get("azure_blob_storage_account") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: azure_blob_storage_account must be of type String parameters[\"azure_blob_storage_account\"]");
    }
    if (parameters.containsKey("azure_blob_storage_container") && !(parameters.get("azure_blob_storage_container") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: azure_blob_storage_container must be of type String parameters[\"azure_blob_storage_container\"]");
    }
    if (parameters.containsKey("azure_blob_storage_sas_token") && !(parameters.get("azure_blob_storage_sas_token") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: azure_blob_storage_sas_token must be of type String parameters[\"azure_blob_storage_sas_token\"]");
    }
    if (parameters.containsKey("azure_files_storage_account") && !(parameters.get("azure_files_storage_account") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: azure_files_storage_account must be of type String parameters[\"azure_files_storage_account\"]");
    }
    if (parameters.containsKey("azure_files_storage_share_name") && !(parameters.get("azure_files_storage_share_name") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: azure_files_storage_share_name must be of type String parameters[\"azure_files_storage_share_name\"]");
    }
    if (parameters.containsKey("azure_files_storage_sas_token") && !(parameters.get("azure_files_storage_sas_token") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: azure_files_storage_sas_token must be of type String parameters[\"azure_files_storage_sas_token\"]");
    }
    if (parameters.containsKey("s3_compatible_bucket") && !(parameters.get("s3_compatible_bucket") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: s3_compatible_bucket must be of type String parameters[\"s3_compatible_bucket\"]");
    }
    if (parameters.containsKey("s3_compatible_endpoint") && !(parameters.get("s3_compatible_endpoint") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: s3_compatible_endpoint must be of type String parameters[\"s3_compatible_endpoint\"]");
    }
    if (parameters.containsKey("s3_compatible_region") && !(parameters.get("s3_compatible_region") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: s3_compatible_region must be of type String parameters[\"s3_compatible_region\"]");
    }
    if (parameters.containsKey("enable_dedicated_ips") && !(parameters.get("enable_dedicated_ips") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: enable_dedicated_ips must be of type Boolean parameters[\"enable_dedicated_ips\"]");
    }
    if (parameters.containsKey("s3_compatible_access_key") && !(parameters.get("s3_compatible_access_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: s3_compatible_access_key must be of type String parameters[\"s3_compatible_access_key\"]");
    }
    if (parameters.containsKey("s3_compatible_secret_key") && !(parameters.get("s3_compatible_secret_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: s3_compatible_secret_key must be of type String parameters[\"s3_compatible_secret_key\"]");
    }
    if (parameters.containsKey("files_agent_root") && !(parameters.get("files_agent_root") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: files_agent_root must be of type String parameters[\"files_agent_root\"]");
    }
    if (parameters.containsKey("files_agent_permission_set") && !(parameters.get("files_agent_permission_set") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: files_agent_permission_set must be of type String parameters[\"files_agent_permission_set\"]");
    }
    if (parameters.containsKey("filebase_access_key") && !(parameters.get("filebase_access_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: filebase_access_key must be of type String parameters[\"filebase_access_key\"]");
    }
    if (parameters.containsKey("filebase_secret_key") && !(parameters.get("filebase_secret_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: filebase_secret_key must be of type String parameters[\"filebase_secret_key\"]");
    }
    if (parameters.containsKey("filebase_bucket") && !(parameters.get("filebase_bucket") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: filebase_bucket must be of type String parameters[\"filebase_bucket\"]");
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
  *   permission_set - string -
  *   root - string - Agent local root path
  *   hostname - string
  *   port - int64 - Incoming port for files agent connections
  *   status - string - either running or shutdown
  *   config_version - string - agent config version
  *   private_key - string - private key
  *   public_key - string - public key
  *   server_host_key - string
  *   subdomain - string
  */
  public static RemoteServer configurationFile() throws IOException {
    return configurationFile(null, null,null);
  }
  public static RemoteServer configurationFile(Long id,  HashMap<String, Object> parameters) throws IOException {
    return configurationFile(id, parameters, null);
  }

  public static RemoteServer configurationFile(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return configurationFile(null, parameters, options);
  }

  public static RemoteServer configurationFile(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = ((Long) parameters.get("id"));
    }


    if (!(id instanceof Long) ) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }
    if (parameters.containsKey("api_token") && !(parameters.get("api_token") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: api_token must be of type String parameters[\"api_token\"]");
    }
    if (parameters.containsKey("permission_set") && !(parameters.get("permission_set") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: permission_set must be of type String parameters[\"permission_set\"]");
    }
    if (parameters.containsKey("root") && !(parameters.get("root") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: root must be of type String parameters[\"root\"]");
    }
    if (parameters.containsKey("hostname") && !(parameters.get("hostname") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: hostname must be of type String parameters[\"hostname\"]");
    }
    if (parameters.containsKey("port") && !(parameters.get("port") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: port must be of type Long parameters[\"port\"]");
    }
    if (parameters.containsKey("status") && !(parameters.get("status") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: status must be of type String parameters[\"status\"]");
    }
    if (parameters.containsKey("config_version") && !(parameters.get("config_version") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: config_version must be of type String parameters[\"config_version\"]");
    }
    if (parameters.containsKey("private_key") && !(parameters.get("private_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: private_key must be of type String parameters[\"private_key\"]");
    }
    if (parameters.containsKey("public_key") && !(parameters.get("public_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: public_key must be of type String parameters[\"public_key\"]");
    }
    if (parameters.containsKey("server_host_key") && !(parameters.get("server_host_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: server_host_key must be of type String parameters[\"server_host_key\"]");
    }
    if (parameters.containsKey("subdomain") && !(parameters.get("subdomain") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: subdomain must be of type String parameters[\"subdomain\"]");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex){
      }
    }

    String url = String.format("%s%s/remote_servers/%s/configuration_file", urlParts);

    TypeReference<RemoteServer> typeReference = new TypeReference<RemoteServer>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   aws_access_key - string - AWS Access Key.
  *   aws_secret_key - string - AWS secret key.
  *   password - string - Password if needed.
  *   private_key - string - Private key if needed.
  *   private_key_passphrase - string - Passphrase for private key if needed.
  *   ssl_certificate - string - SSL client certificate.
  *   google_cloud_storage_credentials_json - string - A JSON file that contains the private key. To generate see https://cloud.google.com/storage/docs/json_api/v1/how-tos/authorizing#APIKey
  *   wasabi_access_key - string - Wasabi access key.
  *   wasabi_secret_key - string - Wasabi secret key.
  *   backblaze_b2_key_id - string - Backblaze B2 Cloud Storage keyID.
  *   backblaze_b2_application_key - string - Backblaze B2 Cloud Storage applicationKey.
  *   rackspace_api_key - string - Rackspace API key from the Rackspace Cloud Control Panel.
  *   reset_authentication - boolean - Reset authenticated account
  *   azure_blob_storage_access_key - string - Azure Blob Storage secret key.
  *   azure_files_storage_access_key - string - Azure File Storage access key.
  *   hostname - string - Hostname or IP address
  *   name - string - Internal name for your reference
  *   max_connections - int64 - Max number of parallel connections.  Ignored for S3 connections (we will parallelize these as much as possible).
  *   pin_to_site_region - boolean - If true, we will ensure that all communications with this remote server are made through the primary region of the site.  This setting can also be overridden by a sitewide setting which will force it to true.
  *   port - int64 - Port for remote server.  Not needed for S3.
  *   s3_bucket - string - S3 bucket name
  *   s3_region - string - S3 region
  *   server_certificate - string - Remote server certificate
  *   server_host_key - string - Remote server SSH Host Key. If provided, we will require that the server host key matches the provided key. Uses OpenSSH format similar to what would go into ~/.ssh/known_hosts
  *   server_type - string - Remote server type.
  *   ssl - string - Should we require SSL?
  *   username - string - Remote server username.  Not needed for S3 buckets.
  *   google_cloud_storage_bucket - string - Google Cloud Storage bucket name
  *   google_cloud_storage_project_id - string - Google Cloud Project ID
  *   backblaze_b2_bucket - string - Backblaze B2 Cloud Storage Bucket name
  *   backblaze_b2_s3_endpoint - string - Backblaze B2 Cloud Storage S3 Endpoint
  *   wasabi_bucket - string - Wasabi Bucket name
  *   wasabi_region - string - Wasabi region
  *   rackspace_username - string - Rackspace username used to login to the Rackspace Cloud Control Panel.
  *   rackspace_region - string - Three letter airport code for Rackspace region. See https://support.rackspace.com/how-to/about-regions/
  *   rackspace_container - string - The name of the container (top level directory) where files will sync.
  *   one_drive_account_type - string - Either personal or business_other account types
  *   azure_blob_storage_account - string - Azure Blob Storage Account name
  *   azure_blob_storage_container - string - Azure Blob Storage Container name
  *   azure_blob_storage_sas_token - string - Shared Access Signature (SAS) token
  *   azure_files_storage_account - string - Azure File Storage Account name
  *   azure_files_storage_share_name - string - Azure File Storage Share name
  *   azure_files_storage_sas_token - string - Shared Access Signature (SAS) token
  *   s3_compatible_bucket - string - S3-compatible Bucket name
  *   s3_compatible_endpoint - string - S3-compatible endpoint
  *   s3_compatible_region - string - S3-compatible endpoint
  *   enable_dedicated_ips - boolean - `true` if remote server only accepts connections from dedicated IPs
  *   s3_compatible_access_key - string - S3-compatible Access Key.
  *   s3_compatible_secret_key - string - S3-compatible secret key
  *   files_agent_root - string - Agent local root path
  *   files_agent_permission_set - string - Local permissions for files agent. read_only, write_only, or read_write
  *   filebase_access_key - string - Filebase Access Key.
  *   filebase_secret_key - string - Filebase secret key
  *   filebase_bucket - string - Filebase Bucket name
  */
  public static RemoteServer update() throws IOException {
    return update(null, null,null);
  }
  public static RemoteServer update(Long id,  HashMap<String, Object> parameters) throws IOException {
    return update(id, parameters, null);
  }

  public static RemoteServer update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return update(null, parameters, options);
  }

  public static RemoteServer update(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = ((Long) parameters.get("id"));
    }


    if (!(id instanceof Long) ) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }
    if (parameters.containsKey("aws_access_key") && !(parameters.get("aws_access_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: aws_access_key must be of type String parameters[\"aws_access_key\"]");
    }
    if (parameters.containsKey("aws_secret_key") && !(parameters.get("aws_secret_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: aws_secret_key must be of type String parameters[\"aws_secret_key\"]");
    }
    if (parameters.containsKey("password") && !(parameters.get("password") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: password must be of type String parameters[\"password\"]");
    }
    if (parameters.containsKey("private_key") && !(parameters.get("private_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: private_key must be of type String parameters[\"private_key\"]");
    }
    if (parameters.containsKey("private_key_passphrase") && !(parameters.get("private_key_passphrase") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: private_key_passphrase must be of type String parameters[\"private_key_passphrase\"]");
    }
    if (parameters.containsKey("ssl_certificate") && !(parameters.get("ssl_certificate") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: ssl_certificate must be of type String parameters[\"ssl_certificate\"]");
    }
    if (parameters.containsKey("google_cloud_storage_credentials_json") && !(parameters.get("google_cloud_storage_credentials_json") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: google_cloud_storage_credentials_json must be of type String parameters[\"google_cloud_storage_credentials_json\"]");
    }
    if (parameters.containsKey("wasabi_access_key") && !(parameters.get("wasabi_access_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: wasabi_access_key must be of type String parameters[\"wasabi_access_key\"]");
    }
    if (parameters.containsKey("wasabi_secret_key") && !(parameters.get("wasabi_secret_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: wasabi_secret_key must be of type String parameters[\"wasabi_secret_key\"]");
    }
    if (parameters.containsKey("backblaze_b2_key_id") && !(parameters.get("backblaze_b2_key_id") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: backblaze_b2_key_id must be of type String parameters[\"backblaze_b2_key_id\"]");
    }
    if (parameters.containsKey("backblaze_b2_application_key") && !(parameters.get("backblaze_b2_application_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: backblaze_b2_application_key must be of type String parameters[\"backblaze_b2_application_key\"]");
    }
    if (parameters.containsKey("rackspace_api_key") && !(parameters.get("rackspace_api_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: rackspace_api_key must be of type String parameters[\"rackspace_api_key\"]");
    }
    if (parameters.containsKey("reset_authentication") && !(parameters.get("reset_authentication") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: reset_authentication must be of type Boolean parameters[\"reset_authentication\"]");
    }
    if (parameters.containsKey("azure_blob_storage_access_key") && !(parameters.get("azure_blob_storage_access_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: azure_blob_storage_access_key must be of type String parameters[\"azure_blob_storage_access_key\"]");
    }
    if (parameters.containsKey("azure_files_storage_access_key") && !(parameters.get("azure_files_storage_access_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: azure_files_storage_access_key must be of type String parameters[\"azure_files_storage_access_key\"]");
    }
    if (parameters.containsKey("hostname") && !(parameters.get("hostname") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: hostname must be of type String parameters[\"hostname\"]");
    }
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("max_connections") && !(parameters.get("max_connections") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: max_connections must be of type Long parameters[\"max_connections\"]");
    }
    if (parameters.containsKey("pin_to_site_region") && !(parameters.get("pin_to_site_region") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: pin_to_site_region must be of type Boolean parameters[\"pin_to_site_region\"]");
    }
    if (parameters.containsKey("port") && !(parameters.get("port") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: port must be of type Long parameters[\"port\"]");
    }
    if (parameters.containsKey("s3_bucket") && !(parameters.get("s3_bucket") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: s3_bucket must be of type String parameters[\"s3_bucket\"]");
    }
    if (parameters.containsKey("s3_region") && !(parameters.get("s3_region") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: s3_region must be of type String parameters[\"s3_region\"]");
    }
    if (parameters.containsKey("server_certificate") && !(parameters.get("server_certificate") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: server_certificate must be of type String parameters[\"server_certificate\"]");
    }
    if (parameters.containsKey("server_host_key") && !(parameters.get("server_host_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: server_host_key must be of type String parameters[\"server_host_key\"]");
    }
    if (parameters.containsKey("server_type") && !(parameters.get("server_type") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: server_type must be of type String parameters[\"server_type\"]");
    }
    if (parameters.containsKey("ssl") && !(parameters.get("ssl") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: ssl must be of type String parameters[\"ssl\"]");
    }
    if (parameters.containsKey("username") && !(parameters.get("username") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: username must be of type String parameters[\"username\"]");
    }
    if (parameters.containsKey("google_cloud_storage_bucket") && !(parameters.get("google_cloud_storage_bucket") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: google_cloud_storage_bucket must be of type String parameters[\"google_cloud_storage_bucket\"]");
    }
    if (parameters.containsKey("google_cloud_storage_project_id") && !(parameters.get("google_cloud_storage_project_id") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: google_cloud_storage_project_id must be of type String parameters[\"google_cloud_storage_project_id\"]");
    }
    if (parameters.containsKey("backblaze_b2_bucket") && !(parameters.get("backblaze_b2_bucket") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: backblaze_b2_bucket must be of type String parameters[\"backblaze_b2_bucket\"]");
    }
    if (parameters.containsKey("backblaze_b2_s3_endpoint") && !(parameters.get("backblaze_b2_s3_endpoint") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: backblaze_b2_s3_endpoint must be of type String parameters[\"backblaze_b2_s3_endpoint\"]");
    }
    if (parameters.containsKey("wasabi_bucket") && !(parameters.get("wasabi_bucket") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: wasabi_bucket must be of type String parameters[\"wasabi_bucket\"]");
    }
    if (parameters.containsKey("wasabi_region") && !(parameters.get("wasabi_region") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: wasabi_region must be of type String parameters[\"wasabi_region\"]");
    }
    if (parameters.containsKey("rackspace_username") && !(parameters.get("rackspace_username") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: rackspace_username must be of type String parameters[\"rackspace_username\"]");
    }
    if (parameters.containsKey("rackspace_region") && !(parameters.get("rackspace_region") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: rackspace_region must be of type String parameters[\"rackspace_region\"]");
    }
    if (parameters.containsKey("rackspace_container") && !(parameters.get("rackspace_container") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: rackspace_container must be of type String parameters[\"rackspace_container\"]");
    }
    if (parameters.containsKey("one_drive_account_type") && !(parameters.get("one_drive_account_type") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: one_drive_account_type must be of type String parameters[\"one_drive_account_type\"]");
    }
    if (parameters.containsKey("azure_blob_storage_account") && !(parameters.get("azure_blob_storage_account") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: azure_blob_storage_account must be of type String parameters[\"azure_blob_storage_account\"]");
    }
    if (parameters.containsKey("azure_blob_storage_container") && !(parameters.get("azure_blob_storage_container") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: azure_blob_storage_container must be of type String parameters[\"azure_blob_storage_container\"]");
    }
    if (parameters.containsKey("azure_blob_storage_sas_token") && !(parameters.get("azure_blob_storage_sas_token") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: azure_blob_storage_sas_token must be of type String parameters[\"azure_blob_storage_sas_token\"]");
    }
    if (parameters.containsKey("azure_files_storage_account") && !(parameters.get("azure_files_storage_account") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: azure_files_storage_account must be of type String parameters[\"azure_files_storage_account\"]");
    }
    if (parameters.containsKey("azure_files_storage_share_name") && !(parameters.get("azure_files_storage_share_name") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: azure_files_storage_share_name must be of type String parameters[\"azure_files_storage_share_name\"]");
    }
    if (parameters.containsKey("azure_files_storage_sas_token") && !(parameters.get("azure_files_storage_sas_token") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: azure_files_storage_sas_token must be of type String parameters[\"azure_files_storage_sas_token\"]");
    }
    if (parameters.containsKey("s3_compatible_bucket") && !(parameters.get("s3_compatible_bucket") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: s3_compatible_bucket must be of type String parameters[\"s3_compatible_bucket\"]");
    }
    if (parameters.containsKey("s3_compatible_endpoint") && !(parameters.get("s3_compatible_endpoint") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: s3_compatible_endpoint must be of type String parameters[\"s3_compatible_endpoint\"]");
    }
    if (parameters.containsKey("s3_compatible_region") && !(parameters.get("s3_compatible_region") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: s3_compatible_region must be of type String parameters[\"s3_compatible_region\"]");
    }
    if (parameters.containsKey("enable_dedicated_ips") && !(parameters.get("enable_dedicated_ips") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: enable_dedicated_ips must be of type Boolean parameters[\"enable_dedicated_ips\"]");
    }
    if (parameters.containsKey("s3_compatible_access_key") && !(parameters.get("s3_compatible_access_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: s3_compatible_access_key must be of type String parameters[\"s3_compatible_access_key\"]");
    }
    if (parameters.containsKey("s3_compatible_secret_key") && !(parameters.get("s3_compatible_secret_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: s3_compatible_secret_key must be of type String parameters[\"s3_compatible_secret_key\"]");
    }
    if (parameters.containsKey("files_agent_root") && !(parameters.get("files_agent_root") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: files_agent_root must be of type String parameters[\"files_agent_root\"]");
    }
    if (parameters.containsKey("files_agent_permission_set") && !(parameters.get("files_agent_permission_set") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: files_agent_permission_set must be of type String parameters[\"files_agent_permission_set\"]");
    }
    if (parameters.containsKey("filebase_access_key") && !(parameters.get("filebase_access_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: filebase_access_key must be of type String parameters[\"filebase_access_key\"]");
    }
    if (parameters.containsKey("filebase_secret_key") && !(parameters.get("filebase_secret_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: filebase_secret_key must be of type String parameters[\"filebase_secret_key\"]");
    }
    if (parameters.containsKey("filebase_bucket") && !(parameters.get("filebase_bucket") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: filebase_bucket must be of type String parameters[\"filebase_bucket\"]");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex){
      }
    }

    String url = String.format("%s%s/remote_servers/%s", urlParts);

    TypeReference<RemoteServer> typeReference = new TypeReference<RemoteServer>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  */
  public static RemoteServer delete() throws IOException {
    return delete(null, null,null);
  }
  public static RemoteServer delete(Long id,  HashMap<String, Object> parameters) throws IOException {
    return delete(id, parameters, null);
  }

  public static RemoteServer delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(null, parameters, options);
  }

  public static RemoteServer delete(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = ((Long) parameters.get("id"));
    }


    if (!(id instanceof Long) ) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex){
      }
    }

    String url = String.format("%s%s/remote_servers/%s", urlParts);

    TypeReference<RemoteServer> typeReference = new TypeReference<RemoteServer>() {};
    return FilesClient.requestItem(url, RequestMethods.DELETE, typeReference, parameters, options);
  }

  public static RemoteServer destroy() throws IOException {
    return destroy(null, null, null);
  }

  public static RemoteServer destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(id, parameters, options);
  }

}


