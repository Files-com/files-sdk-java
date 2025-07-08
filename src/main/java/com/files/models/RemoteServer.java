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
import com.files.util.UrlUtils;
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

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RemoteServer implements ModelInterface {
  private HashMap<String, Object> options;

  public void setOptions(HashMap<String, Object> options) {
    this.options = options;
  }

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
  @JsonProperty("id")
  public Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /**
  * If true, this server has been disabled due to failures.  Make any change or set disabled to false to clear this flag.
  */
  @JsonProperty("disabled")
  public Boolean disabled;

  public Boolean getDisabled() {
    return disabled;
  }

  public void setDisabled(Boolean disabled) {
    this.disabled = disabled;
  }

  /**
  * Type of authentication method
  */
  @JsonProperty("authentication_method")
  public String authenticationMethod;

  public String getAuthenticationMethod() {
    return authenticationMethod;
  }

  public void setAuthenticationMethod(String authenticationMethod) {
    this.authenticationMethod = authenticationMethod;
  }

  /**
  * Hostname or IP address
  */
  @JsonProperty("hostname")
  public String hostname;

  public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  /**
  * Initial home folder on remote server
  */
  @JsonProperty("remote_home_path")
  public String remoteHomePath;

  public String getRemoteHomePath() {
    return remoteHomePath;
  }

  public void setRemoteHomePath(String remoteHomePath) {
    this.remoteHomePath = remoteHomePath;
  }

  /**
  * Internal name for your reference
  */
  @JsonProperty("name")
  public String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
  * Port for remote server.  Not needed for S3.
  */
  @JsonProperty("port")
  public Long port;

  public Long getPort() {
    return port;
  }

  public void setPort(Long port) {
    this.port = port;
  }

  /**
  * Max number of parallel connections.  Ignored for S3 connections (we will parallelize these as much as possible).
  */
  @JsonProperty("max_connections")
  public Long maxConnections;

  public Long getMaxConnections() {
    return maxConnections;
  }

  public void setMaxConnections(Long maxConnections) {
    this.maxConnections = maxConnections;
  }

  /**
  * If true, we will ensure that all communications with this remote server are made through the primary region of the site.  This setting can also be overridden by a site-wide setting which will force it to true.
  */
  @JsonProperty("pin_to_site_region")
  public Boolean pinToSiteRegion;

  public Boolean getPinToSiteRegion() {
    return pinToSiteRegion;
  }

  public void setPinToSiteRegion(Boolean pinToSiteRegion) {
    this.pinToSiteRegion = pinToSiteRegion;
  }

  /**
  * If set, all communications with this remote server are made through the provided region.
  */
  @JsonProperty("pinned_region")
  public String pinnedRegion;

  public String getPinnedRegion() {
    return pinnedRegion;
  }

  public void setPinnedRegion(String pinnedRegion) {
    this.pinnedRegion = pinnedRegion;
  }

  /**
  * S3 bucket name
  */
  @JsonProperty("s3_bucket")
  public String s3Bucket;

  public String getS3Bucket() {
    return s3Bucket;
  }

  public void setS3Bucket(String s3Bucket) {
    this.s3Bucket = s3Bucket;
  }

  /**
  * S3 region
  */
  @JsonProperty("s3_region")
  public String s3Region;

  public String getS3Region() {
    return s3Region;
  }

  public void setS3Region(String s3Region) {
    this.s3Region = s3Region;
  }

  /**
  * AWS Access Key.
  */
  @JsonProperty("aws_access_key")
  public String awsAccessKey;

  public String getAwsAccessKey() {
    return awsAccessKey;
  }

  public void setAwsAccessKey(String awsAccessKey) {
    this.awsAccessKey = awsAccessKey;
  }

  /**
  * Remote server certificate
  */
  @JsonProperty("server_certificate")
  public String serverCertificate;

  public String getServerCertificate() {
    return serverCertificate;
  }

  public void setServerCertificate(String serverCertificate) {
    this.serverCertificate = serverCertificate;
  }

  /**
  * Remote server SSH Host Key. If provided, we will require that the server host key matches the provided key. Uses OpenSSH format similar to what would go into ~/.ssh/known_hosts
  */
  @JsonProperty("server_host_key")
  public String serverHostKey;

  public String getServerHostKey() {
    return serverHostKey;
  }

  public void setServerHostKey(String serverHostKey) {
    this.serverHostKey = serverHostKey;
  }

  /**
  * Remote server type.
  */
  @JsonProperty("server_type")
  public String serverType;

  public String getServerType() {
    return serverType;
  }

  public void setServerType(String serverType) {
    this.serverType = serverType;
  }

  /**
  * Should we require SSL?
  */
  @JsonProperty("ssl")
  public String ssl;

  public String getSsl() {
    return ssl;
  }

  public void setSsl(String ssl) {
    this.ssl = ssl;
  }

  /**
  * Remote server username.  Not needed for S3 buckets.
  */
  @JsonProperty("username")
  public String username;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  /**
  * Google Cloud Storage: Bucket Name
  */
  @JsonProperty("google_cloud_storage_bucket")
  public String googleCloudStorageBucket;

  public String getGoogleCloudStorageBucket() {
    return googleCloudStorageBucket;
  }

  public void setGoogleCloudStorageBucket(String googleCloudStorageBucket) {
    this.googleCloudStorageBucket = googleCloudStorageBucket;
  }

  /**
  * Google Cloud Storage: Project ID
  */
  @JsonProperty("google_cloud_storage_project_id")
  public String googleCloudStorageProjectId;

  public String getGoogleCloudStorageProjectId() {
    return googleCloudStorageProjectId;
  }

  public void setGoogleCloudStorageProjectId(String googleCloudStorageProjectId) {
    this.googleCloudStorageProjectId = googleCloudStorageProjectId;
  }

  /**
  * Google Cloud Storage: S3-compatible Access Key.
  */
  @JsonProperty("google_cloud_storage_s3_compatible_access_key")
  public String googleCloudStorageS3CompatibleAccessKey;

  public String getGoogleCloudStorageS3CompatibleAccessKey() {
    return googleCloudStorageS3CompatibleAccessKey;
  }

  public void setGoogleCloudStorageS3CompatibleAccessKey(String googleCloudStorageS3CompatibleAccessKey) {
    this.googleCloudStorageS3CompatibleAccessKey = googleCloudStorageS3CompatibleAccessKey;
  }

  /**
  * Backblaze B2 Cloud Storage: S3 Endpoint
  */
  @JsonProperty("backblaze_b2_s3_endpoint")
  public String backblazeB2S3Endpoint;

  public String getBackblazeB2S3Endpoint() {
    return backblazeB2S3Endpoint;
  }

  public void setBackblazeB2S3Endpoint(String backblazeB2S3Endpoint) {
    this.backblazeB2S3Endpoint = backblazeB2S3Endpoint;
  }

  /**
  * Backblaze B2 Cloud Storage: Bucket name
  */
  @JsonProperty("backblaze_b2_bucket")
  public String backblazeB2Bucket;

  public String getBackblazeB2Bucket() {
    return backblazeB2Bucket;
  }

  public void setBackblazeB2Bucket(String backblazeB2Bucket) {
    this.backblazeB2Bucket = backblazeB2Bucket;
  }

  /**
  * Wasabi: Bucket name
  */
  @JsonProperty("wasabi_bucket")
  public String wasabiBucket;

  public String getWasabiBucket() {
    return wasabiBucket;
  }

  public void setWasabiBucket(String wasabiBucket) {
    this.wasabiBucket = wasabiBucket;
  }

  /**
  * Wasabi: Region
  */
  @JsonProperty("wasabi_region")
  public String wasabiRegion;

  public String getWasabiRegion() {
    return wasabiRegion;
  }

  public void setWasabiRegion(String wasabiRegion) {
    this.wasabiRegion = wasabiRegion;
  }

  /**
  * Wasabi: Access Key.
  */
  @JsonProperty("wasabi_access_key")
  public String wasabiAccessKey;

  public String getWasabiAccessKey() {
    return wasabiAccessKey;
  }

  public void setWasabiAccessKey(String wasabiAccessKey) {
    this.wasabiAccessKey = wasabiAccessKey;
  }

  /**
  * Rackspace: username used to login to the Rackspace Cloud Control Panel.
  */
  @JsonProperty("rackspace_username")
  public String rackspaceUsername;

  public String getRackspaceUsername() {
    return rackspaceUsername;
  }

  public void setRackspaceUsername(String rackspaceUsername) {
    this.rackspaceUsername = rackspaceUsername;
  }

  /**
  * Rackspace: Three letter code for Rackspace region. See https://support.rackspace.com/how-to/about-regions/
  */
  @JsonProperty("rackspace_region")
  public String rackspaceRegion;

  public String getRackspaceRegion() {
    return rackspaceRegion;
  }

  public void setRackspaceRegion(String rackspaceRegion) {
    this.rackspaceRegion = rackspaceRegion;
  }

  /**
  * Rackspace: The name of the container (top level directory) where files will sync.
  */
  @JsonProperty("rackspace_container")
  public String rackspaceContainer;

  public String getRackspaceContainer() {
    return rackspaceContainer;
  }

  public void setRackspaceContainer(String rackspaceContainer) {
    this.rackspaceContainer = rackspaceContainer;
  }

  /**
  * Either `in_setup` or `complete`
  */
  @JsonProperty("auth_status")
  public String authStatus;

  public String getAuthStatus() {
    return authStatus;
  }

  public void setAuthStatus(String authStatus) {
    this.authStatus = authStatus;
  }

  /**
  * Describes the authorized account
  */
  @JsonProperty("auth_account_name")
  public String authAccountName;

  public String getAuthAccountName() {
    return authAccountName;
  }

  public void setAuthAccountName(String authAccountName) {
    this.authAccountName = authAccountName;
  }

  /**
  * OneDrive: Either personal or business_other account types
  */
  @JsonProperty("one_drive_account_type")
  public String oneDriveAccountType;

  public String getOneDriveAccountType() {
    return oneDriveAccountType;
  }

  public void setOneDriveAccountType(String oneDriveAccountType) {
    this.oneDriveAccountType = oneDriveAccountType;
  }

  /**
  * Azure Blob Storage: Account name
  */
  @JsonProperty("azure_blob_storage_account")
  public String azureBlobStorageAccount;

  public String getAzureBlobStorageAccount() {
    return azureBlobStorageAccount;
  }

  public void setAzureBlobStorageAccount(String azureBlobStorageAccount) {
    this.azureBlobStorageAccount = azureBlobStorageAccount;
  }

  /**
  * Azure Blob Storage: Container name
  */
  @JsonProperty("azure_blob_storage_container")
  public String azureBlobStorageContainer;

  public String getAzureBlobStorageContainer() {
    return azureBlobStorageContainer;
  }

  public void setAzureBlobStorageContainer(String azureBlobStorageContainer) {
    this.azureBlobStorageContainer = azureBlobStorageContainer;
  }

  /**
  * Azure Blob Storage: Does the storage account has hierarchical namespace feature enabled?
  */
  @JsonProperty("azure_blob_storage_hierarchical_namespace")
  public Boolean azureBlobStorageHierarchicalNamespace;

  public Boolean getAzureBlobStorageHierarchicalNamespace() {
    return azureBlobStorageHierarchicalNamespace;
  }

  public void setAzureBlobStorageHierarchicalNamespace(Boolean azureBlobStorageHierarchicalNamespace) {
    this.azureBlobStorageHierarchicalNamespace = azureBlobStorageHierarchicalNamespace;
  }

  /**
  * Azure Blob Storage: Custom DNS suffix
  */
  @JsonProperty("azure_blob_storage_dns_suffix")
  public String azureBlobStorageDnsSuffix;

  public String getAzureBlobStorageDnsSuffix() {
    return azureBlobStorageDnsSuffix;
  }

  public void setAzureBlobStorageDnsSuffix(String azureBlobStorageDnsSuffix) {
    this.azureBlobStorageDnsSuffix = azureBlobStorageDnsSuffix;
  }

  /**
  * Azure Files: Storage Account name
  */
  @JsonProperty("azure_files_storage_account")
  public String azureFilesStorageAccount;

  public String getAzureFilesStorageAccount() {
    return azureFilesStorageAccount;
  }

  public void setAzureFilesStorageAccount(String azureFilesStorageAccount) {
    this.azureFilesStorageAccount = azureFilesStorageAccount;
  }

  /**
  * Azure Files:  Storage Share name
  */
  @JsonProperty("azure_files_storage_share_name")
  public String azureFilesStorageShareName;

  public String getAzureFilesStorageShareName() {
    return azureFilesStorageShareName;
  }

  public void setAzureFilesStorageShareName(String azureFilesStorageShareName) {
    this.azureFilesStorageShareName = azureFilesStorageShareName;
  }

  /**
  * Azure Files: Custom DNS suffix
  */
  @JsonProperty("azure_files_storage_dns_suffix")
  public String azureFilesStorageDnsSuffix;

  public String getAzureFilesStorageDnsSuffix() {
    return azureFilesStorageDnsSuffix;
  }

  public void setAzureFilesStorageDnsSuffix(String azureFilesStorageDnsSuffix) {
    this.azureFilesStorageDnsSuffix = azureFilesStorageDnsSuffix;
  }

  /**
  * S3-compatible: Bucket name
  */
  @JsonProperty("s3_compatible_bucket")
  public String s3CompatibleBucket;

  public String getS3CompatibleBucket() {
    return s3CompatibleBucket;
  }

  public void setS3CompatibleBucket(String s3CompatibleBucket) {
    this.s3CompatibleBucket = s3CompatibleBucket;
  }

  /**
  * S3-compatible: endpoint
  */
  @JsonProperty("s3_compatible_endpoint")
  public String s3CompatibleEndpoint;

  public String getS3CompatibleEndpoint() {
    return s3CompatibleEndpoint;
  }

  public void setS3CompatibleEndpoint(String s3CompatibleEndpoint) {
    this.s3CompatibleEndpoint = s3CompatibleEndpoint;
  }

  /**
  * S3-compatible: region
  */
  @JsonProperty("s3_compatible_region")
  public String s3CompatibleRegion;

  public String getS3CompatibleRegion() {
    return s3CompatibleRegion;
  }

  public void setS3CompatibleRegion(String s3CompatibleRegion) {
    this.s3CompatibleRegion = s3CompatibleRegion;
  }

  /**
  * S3-compatible: Access Key
  */
  @JsonProperty("s3_compatible_access_key")
  public String s3CompatibleAccessKey;

  public String getS3CompatibleAccessKey() {
    return s3CompatibleAccessKey;
  }

  public void setS3CompatibleAccessKey(String s3CompatibleAccessKey) {
    this.s3CompatibleAccessKey = s3CompatibleAccessKey;
  }

  /**
  * `true` if remote server only accepts connections from dedicated IPs
  */
  @JsonProperty("enable_dedicated_ips")
  public Boolean enableDedicatedIps;

  public Boolean getEnableDedicatedIps() {
    return enableDedicatedIps;
  }

  public void setEnableDedicatedIps(Boolean enableDedicatedIps) {
    this.enableDedicatedIps = enableDedicatedIps;
  }

  /**
  * Local permissions for files agent. read_only, write_only, or read_write
  */
  @JsonProperty("files_agent_permission_set")
  public String filesAgentPermissionSet;

  public String getFilesAgentPermissionSet() {
    return filesAgentPermissionSet;
  }

  public void setFilesAgentPermissionSet(String filesAgentPermissionSet) {
    this.filesAgentPermissionSet = filesAgentPermissionSet;
  }

  /**
  * Agent local root path
  */
  @JsonProperty("files_agent_root")
  public String filesAgentRoot;

  public String getFilesAgentRoot() {
    return filesAgentRoot;
  }

  public void setFilesAgentRoot(String filesAgentRoot) {
    this.filesAgentRoot = filesAgentRoot;
  }

  /**
  * Files Agent API Token
  */
  @JsonProperty("files_agent_api_token")
  public String filesAgentApiToken;

  public String getFilesAgentApiToken() {
    return filesAgentApiToken;
  }

  public void setFilesAgentApiToken(String filesAgentApiToken) {
    this.filesAgentApiToken = filesAgentApiToken;
  }

  /**
  * Files Agent version
  */
  @JsonProperty("files_agent_version")
  public String filesAgentVersion;

  public String getFilesAgentVersion() {
    return filesAgentVersion;
  }

  public void setFilesAgentVersion(String filesAgentVersion) {
    this.filesAgentVersion = filesAgentVersion;
  }

  /**
  * Filebase: Bucket name
  */
  @JsonProperty("filebase_bucket")
  public String filebaseBucket;

  public String getFilebaseBucket() {
    return filebaseBucket;
  }

  public void setFilebaseBucket(String filebaseBucket) {
    this.filebaseBucket = filebaseBucket;
  }

  /**
  * Filebase: Access Key.
  */
  @JsonProperty("filebase_access_key")
  public String filebaseAccessKey;

  public String getFilebaseAccessKey() {
    return filebaseAccessKey;
  }

  public void setFilebaseAccessKey(String filebaseAccessKey) {
    this.filebaseAccessKey = filebaseAccessKey;
  }

  /**
  * Cloudflare: Bucket name
  */
  @JsonProperty("cloudflare_bucket")
  public String cloudflareBucket;

  public String getCloudflareBucket() {
    return cloudflareBucket;
  }

  public void setCloudflareBucket(String cloudflareBucket) {
    this.cloudflareBucket = cloudflareBucket;
  }

  /**
  * Cloudflare: Access Key.
  */
  @JsonProperty("cloudflare_access_key")
  public String cloudflareAccessKey;

  public String getCloudflareAccessKey() {
    return cloudflareAccessKey;
  }

  public void setCloudflareAccessKey(String cloudflareAccessKey) {
    this.cloudflareAccessKey = cloudflareAccessKey;
  }

  /**
  * Cloudflare: endpoint
  */
  @JsonProperty("cloudflare_endpoint")
  public String cloudflareEndpoint;

  public String getCloudflareEndpoint() {
    return cloudflareEndpoint;
  }

  public void setCloudflareEndpoint(String cloudflareEndpoint) {
    this.cloudflareEndpoint = cloudflareEndpoint;
  }

  /**
  * Dropbox: If true, list Team folders in root?
  */
  @JsonProperty("dropbox_teams")
  public Boolean dropboxTeams;

  public Boolean getDropboxTeams() {
    return dropboxTeams;
  }

  public void setDropboxTeams(Boolean dropboxTeams) {
    this.dropboxTeams = dropboxTeams;
  }

  /**
  * Linode: Bucket name
  */
  @JsonProperty("linode_bucket")
  public String linodeBucket;

  public String getLinodeBucket() {
    return linodeBucket;
  }

  public void setLinodeBucket(String linodeBucket) {
    this.linodeBucket = linodeBucket;
  }

  /**
  * Linode: Access Key
  */
  @JsonProperty("linode_access_key")
  public String linodeAccessKey;

  public String getLinodeAccessKey() {
    return linodeAccessKey;
  }

  public void setLinodeAccessKey(String linodeAccessKey) {
    this.linodeAccessKey = linodeAccessKey;
  }

  /**
  * Linode: region
  */
  @JsonProperty("linode_region")
  public String linodeRegion;

  public String getLinodeRegion() {
    return linodeRegion;
  }

  public void setLinodeRegion(String linodeRegion) {
    this.linodeRegion = linodeRegion;
  }

  /**
  * If true, this remote server supports file versioning. This value is determined automatically by Files.com.
  */
  @JsonProperty("supports_versioning")
  public Boolean supportsVersioning;

  public Boolean getSupportsVersioning() {
    return supportsVersioning;
  }

  public void setSupportsVersioning(Boolean supportsVersioning) {
    this.supportsVersioning = supportsVersioning;
  }

  /**
  * Password, if needed.
  */
  @JsonProperty("password")
  public String password;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  /**
  * Private key, if needed.
  */
  @JsonProperty("private_key")
  public String privateKey;

  public String getPrivateKey() {
    return privateKey;
  }

  public void setPrivateKey(String privateKey) {
    this.privateKey = privateKey;
  }

  /**
  * Passphrase for private key if needed.
  */
  @JsonProperty("private_key_passphrase")
  public String privateKeyPassphrase;

  public String getPrivateKeyPassphrase() {
    return privateKeyPassphrase;
  }

  public void setPrivateKeyPassphrase(String privateKeyPassphrase) {
    this.privateKeyPassphrase = privateKeyPassphrase;
  }

  /**
  * Reset authenticated account?
  */
  @JsonProperty("reset_authentication")
  public Boolean resetAuthentication;

  public Boolean getResetAuthentication() {
    return resetAuthentication;
  }

  public void setResetAuthentication(Boolean resetAuthentication) {
    this.resetAuthentication = resetAuthentication;
  }

  /**
  * SSL client certificate.
  */
  @JsonProperty("ssl_certificate")
  public String sslCertificate;

  public String getSslCertificate() {
    return sslCertificate;
  }

  public void setSslCertificate(String sslCertificate) {
    this.sslCertificate = sslCertificate;
  }

  /**
  * AWS: secret key.
  */
  @JsonProperty("aws_secret_key")
  public String awsSecretKey;

  public String getAwsSecretKey() {
    return awsSecretKey;
  }

  public void setAwsSecretKey(String awsSecretKey) {
    this.awsSecretKey = awsSecretKey;
  }

  /**
  * Azure Blob Storage: Access Key
  */
  @JsonProperty("azure_blob_storage_access_key")
  public String azureBlobStorageAccessKey;

  public String getAzureBlobStorageAccessKey() {
    return azureBlobStorageAccessKey;
  }

  public void setAzureBlobStorageAccessKey(String azureBlobStorageAccessKey) {
    this.azureBlobStorageAccessKey = azureBlobStorageAccessKey;
  }

  /**
  * Azure Blob Storage: Shared Access Signature (SAS) token
  */
  @JsonProperty("azure_blob_storage_sas_token")
  public String azureBlobStorageSasToken;

  public String getAzureBlobStorageSasToken() {
    return azureBlobStorageSasToken;
  }

  public void setAzureBlobStorageSasToken(String azureBlobStorageSasToken) {
    this.azureBlobStorageSasToken = azureBlobStorageSasToken;
  }

  /**
  * Azure File Storage: Access Key
  */
  @JsonProperty("azure_files_storage_access_key")
  public String azureFilesStorageAccessKey;

  public String getAzureFilesStorageAccessKey() {
    return azureFilesStorageAccessKey;
  }

  public void setAzureFilesStorageAccessKey(String azureFilesStorageAccessKey) {
    this.azureFilesStorageAccessKey = azureFilesStorageAccessKey;
  }

  /**
  * Azure File Storage: Shared Access Signature (SAS) token
  */
  @JsonProperty("azure_files_storage_sas_token")
  public String azureFilesStorageSasToken;

  public String getAzureFilesStorageSasToken() {
    return azureFilesStorageSasToken;
  }

  public void setAzureFilesStorageSasToken(String azureFilesStorageSasToken) {
    this.azureFilesStorageSasToken = azureFilesStorageSasToken;
  }

  /**
  * Backblaze B2 Cloud Storage: applicationKey
  */
  @JsonProperty("backblaze_b2_application_key")
  public String backblazeB2ApplicationKey;

  public String getBackblazeB2ApplicationKey() {
    return backblazeB2ApplicationKey;
  }

  public void setBackblazeB2ApplicationKey(String backblazeB2ApplicationKey) {
    this.backblazeB2ApplicationKey = backblazeB2ApplicationKey;
  }

  /**
  * Backblaze B2 Cloud Storage: keyID
  */
  @JsonProperty("backblaze_b2_key_id")
  public String backblazeB2KeyId;

  public String getBackblazeB2KeyId() {
    return backblazeB2KeyId;
  }

  public void setBackblazeB2KeyId(String backblazeB2KeyId) {
    this.backblazeB2KeyId = backblazeB2KeyId;
  }

  /**
  * Cloudflare: Secret Key
  */
  @JsonProperty("cloudflare_secret_key")
  public String cloudflareSecretKey;

  public String getCloudflareSecretKey() {
    return cloudflareSecretKey;
  }

  public void setCloudflareSecretKey(String cloudflareSecretKey) {
    this.cloudflareSecretKey = cloudflareSecretKey;
  }

  /**
  * Filebase: Secret Key
  */
  @JsonProperty("filebase_secret_key")
  public String filebaseSecretKey;

  public String getFilebaseSecretKey() {
    return filebaseSecretKey;
  }

  public void setFilebaseSecretKey(String filebaseSecretKey) {
    this.filebaseSecretKey = filebaseSecretKey;
  }

  /**
  * Google Cloud Storage: JSON file that contains the private key. To generate see https://cloud.google.com/storage/docs/json_api/v1/how-tos/authorizing#APIKey
  */
  @JsonProperty("google_cloud_storage_credentials_json")
  public String googleCloudStorageCredentialsJson;

  public String getGoogleCloudStorageCredentialsJson() {
    return googleCloudStorageCredentialsJson;
  }

  public void setGoogleCloudStorageCredentialsJson(String googleCloudStorageCredentialsJson) {
    this.googleCloudStorageCredentialsJson = googleCloudStorageCredentialsJson;
  }

  /**
  * Google Cloud Storage: S3-compatible secret key
  */
  @JsonProperty("google_cloud_storage_s3_compatible_secret_key")
  public String googleCloudStorageS3CompatibleSecretKey;

  public String getGoogleCloudStorageS3CompatibleSecretKey() {
    return googleCloudStorageS3CompatibleSecretKey;
  }

  public void setGoogleCloudStorageS3CompatibleSecretKey(String googleCloudStorageS3CompatibleSecretKey) {
    this.googleCloudStorageS3CompatibleSecretKey = googleCloudStorageS3CompatibleSecretKey;
  }

  /**
  * Linode: Secret Key
  */
  @JsonProperty("linode_secret_key")
  public String linodeSecretKey;

  public String getLinodeSecretKey() {
    return linodeSecretKey;
  }

  public void setLinodeSecretKey(String linodeSecretKey) {
    this.linodeSecretKey = linodeSecretKey;
  }

  /**
  * Rackspace: API key from the Rackspace Cloud Control Panel
  */
  @JsonProperty("rackspace_api_key")
  public String rackspaceApiKey;

  public String getRackspaceApiKey() {
    return rackspaceApiKey;
  }

  public void setRackspaceApiKey(String rackspaceApiKey) {
    this.rackspaceApiKey = rackspaceApiKey;
  }

  /**
  * S3-compatible: Secret Key
  */
  @JsonProperty("s3_compatible_secret_key")
  public String s3CompatibleSecretKey;

  public String getS3CompatibleSecretKey() {
    return s3CompatibleSecretKey;
  }

  public void setS3CompatibleSecretKey(String s3CompatibleSecretKey) {
    this.s3CompatibleSecretKey = s3CompatibleSecretKey;
  }

  /**
  * Wasabi: Secret Key
  */
  @JsonProperty("wasabi_secret_key")
  public String wasabiSecretKey;

  public String getWasabiSecretKey() {
    return wasabiSecretKey;
  }

  public void setWasabiSecretKey(String wasabiSecretKey) {
    this.wasabiSecretKey = wasabiSecretKey;
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



    String url = String.format("%s%s/remote_servers/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

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



    String url = String.format("%s%s/remote_servers/%s/configuration_file", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

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



    String url = String.format("%s%s/remote_servers/%s/configuration_file", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

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



    String url = String.format("%s%s/remote_servers/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

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



    String url = String.format("%s%s/remote_servers/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
