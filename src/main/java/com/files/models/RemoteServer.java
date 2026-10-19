package com.files.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.net.ClientRuntime;
import com.files.util.ModelUtils;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RemoteServer implements ModelInterface {
  private HashMap<String, Object> options;
  private FilesClient filesClient;

  /**
   * Sets the request options, such as {@code api_key} or {@code workspace_id}, that this object's
   * own operations send. The map is copied; options that control a single call, such as a
   * {@code cancellation} token or a {@code timeout}, are not kept.
   */
  public void setOptions(HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
  }

  /**
   * Sets the client this object's own operations run with.
   *
   * @param client the client
   */
  @JsonIgnore
  @Override
  public void setClient(FilesClient client) {
    this.filesClient = client;
  }

  // The client this object's own operations run with: the one that returned it, or one set with
  // setClient, and otherwise the default client as it is when the operation runs.
  private FilesClient filesClient() {
    return filesClient != null ? filesClient : FilesClient.defaultClient();
  }

  // One mapper for every RemoteServer, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code RemoteServer} with no attributes set.
   */
  public RemoteServer() {
    this(null, null);
  }

  /**
   * Creates a {@code RemoteServer} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public RemoteServer(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code RemoteServer} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public RemoteServer(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Remote Server ID
   */
  @JsonProperty("id")
  public Long id;

  /**
   * Gets {@link #id}.
   *
   * @return the value of {@link #id}
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets {@link #id}.
   *
   * @param id the new value
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * If true, this Remote Server is disabled. Updating it clears this flag, except for retired Agent v1 records, which remain disabled.
   */
  @JsonProperty("disabled")
  public Boolean disabled;

  /**
   * Gets {@link #disabled}.
   *
   * @return the value of {@link #disabled}
   */
  public Boolean getDisabled() {
    return disabled;
  }

  /**
   * Sets {@link #disabled}.
   *
   * @param disabled the new value
   */
  public void setDisabled(Boolean disabled) {
    this.disabled = disabled;
  }

  /**
   * Type of authentication method to use
   */
  @JsonProperty("authentication_method")
  public String authenticationMethod;

  /**
   * Gets {@link #authenticationMethod}.
   *
   * @return the value of {@link #authenticationMethod}
   */
  public String getAuthenticationMethod() {
    return authenticationMethod;
  }

  /**
   * Sets {@link #authenticationMethod}.
   *
   * @param authenticationMethod the new value
   */
  public void setAuthenticationMethod(String authenticationMethod) {
    this.authenticationMethod = authenticationMethod;
  }

  /**
   * Hostname or IP address. For Agent Remote Servers, the Agent reports the hostname it connects from at each registration, replacing the stored value. This field is not included in the generated Agent configuration file. Setting it on Create or Update does not change the running Agent.
   */
  @JsonProperty("hostname")
  public String hostname;

  /**
   * Gets {@link #hostname}.
   *
   * @return the value of {@link #hostname}
   */
  public String getHostname() {
    return hostname;
  }

  /**
   * Sets {@link #hostname}.
   *
   * @param hostname the new value
   */
  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  /**
   * Initial home folder on remote server
   */
  @JsonProperty("remote_home_path")
  public String remoteHomePath;

  /**
   * Gets {@link #remoteHomePath}.
   *
   * @return the value of {@link #remoteHomePath}
   */
  public String getRemoteHomePath() {
    return remoteHomePath;
  }

  /**
   * Sets {@link #remoteHomePath}.
   *
   * @param remoteHomePath the new value
   */
  public void setRemoteHomePath(String remoteHomePath) {
    this.remoteHomePath = remoteHomePath;
  }

  /**
   * Upload staging path.  Applies to SFTP only.  If a path is provided here, files will first be uploaded to this path on the remote folder and the moved into the final correct path via an SFTP move command.  This is required by some remote MFT systems to emulate atomic uploads, which are otherwise not supoprted by SFTP.
   */
  @JsonProperty("upload_staging_path")
  public String uploadStagingPath;

  /**
   * Gets {@link #uploadStagingPath}.
   *
   * @return the value of {@link #uploadStagingPath}
   */
  public String getUploadStagingPath() {
    return uploadStagingPath;
  }

  /**
   * Sets {@link #uploadStagingPath}.
   *
   * @param uploadStagingPath the new value
   */
  public void setUploadStagingPath(String uploadStagingPath) {
    this.uploadStagingPath = uploadStagingPath;
  }

  /**
   * Allow relative paths in SFTP. If true, paths will not be forced to be absolute, allowing operations relative to the user's home directory.
   */
  @JsonProperty("allow_relative_paths")
  public Boolean allowRelativePaths;

  /**
   * Gets {@link #allowRelativePaths}.
   *
   * @return the value of {@link #allowRelativePaths}
   */
  public Boolean getAllowRelativePaths() {
    return allowRelativePaths;
  }

  /**
   * Sets {@link #allowRelativePaths}.
   *
   * @param allowRelativePaths the new value
   */
  public void setAllowRelativePaths(Boolean allowRelativePaths) {
    this.allowRelativePaths = allowRelativePaths;
  }

  /**
   * Internal name for your reference
   */
  @JsonProperty("name")
  public String name;

  /**
   * Gets {@link #name}.
   *
   * @return the value of {@link #name}
   */
  public String getName() {
    return name;
  }

  /**
   * Sets {@link #name}.
   *
   * @param name the new value
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Internal description for your reference
   */
  @JsonProperty("description")
  public String description;

  /**
   * Gets {@link #description}.
   *
   * @return the value of {@link #description}
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets {@link #description}.
   *
   * @param description the new value
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Port for remote server. For Agent Remote Servers, the Agent reports its downstream proxy port at each registration, replacing the stored value. This field is not included in the generated Agent configuration file. Setting it on Create or Update does not change the running Agent.
   */
  @JsonProperty("port")
  public Long port;

  /**
   * Gets {@link #port}.
   *
   * @return the value of {@link #port}
   */
  public Long getPort() {
    return port;
  }

  /**
   * Sets {@link #port}.
   *
   * @param port the new value
   */
  public void setPort(Long port) {
    this.port = port;
  }

  /**
   * If set to always, uploads to this server will be uploaded first to Files.com before being sent to the remote server. This can improve performance in certain access patterns, such as high-latency connections.  It will cause data to be temporarily stored in Files.com. If set to auto, we will perform this optimization if we believe it to be a benefit in a given situation.
   */
  @JsonProperty("buffer_uploads")
  public String bufferUploads;

  /**
   * Gets {@link #bufferUploads}.
   *
   * @return the value of {@link #bufferUploads}
   */
  public String getBufferUploads() {
    return bufferUploads;
  }

  /**
   * Sets {@link #bufferUploads}.
   *
   * @param bufferUploads the new value
   */
  public void setBufferUploads(String bufferUploads) {
    this.bufferUploads = bufferUploads;
  }

  /**
   * Max number of parallel connections.  Ignored for S3 connections (we will parallelize these as much as possible).
   */
  @JsonProperty("max_connections")
  public Long maxConnections;

  /**
   * Gets {@link #maxConnections}.
   *
   * @return the value of {@link #maxConnections}
   */
  public Long getMaxConnections() {
    return maxConnections;
  }

  /**
   * Sets {@link #maxConnections}.
   *
   * @param maxConnections the new value
   */
  public void setMaxConnections(Long maxConnections) {
    this.maxConnections = maxConnections;
  }

  /**
   * If true, we will ensure that all communications with this remote server are made through the primary region of the site.  This setting can also be overridden by a site-wide setting which will force it to true.
   */
  @JsonProperty("pin_to_site_region")
  public Boolean pinToSiteRegion;

  /**
   * Gets {@link #pinToSiteRegion}.
   *
   * @return the value of {@link #pinToSiteRegion}
   */
  public Boolean getPinToSiteRegion() {
    return pinToSiteRegion;
  }

  /**
   * Sets {@link #pinToSiteRegion}.
   *
   * @param pinToSiteRegion the new value
   */
  public void setPinToSiteRegion(Boolean pinToSiteRegion) {
    this.pinToSiteRegion = pinToSiteRegion;
  }

  /**
   * If set, all communications with this remote server are made through the provided region.
   */
  @JsonProperty("pinned_region")
  public String pinnedRegion;

  /**
   * Gets {@link #pinnedRegion}.
   *
   * @return the value of {@link #pinnedRegion}
   */
  public String getPinnedRegion() {
    return pinnedRegion;
  }

  /**
   * Sets {@link #pinnedRegion}.
   *
   * @param pinnedRegion the new value
   */
  public void setPinnedRegion(String pinnedRegion) {
    this.pinnedRegion = pinnedRegion;
  }

  /**
   * ID of Remote Server Credential, if applicable.
   */
  @JsonProperty("remote_server_credential_id")
  public Long remoteServerCredentialId;

  /**
   * Gets {@link #remoteServerCredentialId}.
   *
   * @return the value of {@link #remoteServerCredentialId}
   */
  public Long getRemoteServerCredentialId() {
    return remoteServerCredentialId;
  }

  /**
   * Sets {@link #remoteServerCredentialId}.
   *
   * @param remoteServerCredentialId the new value
   */
  public void setRemoteServerCredentialId(Long remoteServerCredentialId) {
    this.remoteServerCredentialId = remoteServerCredentialId;
  }

  /**
   * S3 bucket name
   */
  @JsonProperty("s3_bucket")
  public String s3Bucket;

  /**
   * Gets {@link #s3Bucket}.
   *
   * @return the value of {@link #s3Bucket}
   */
  public String getS3Bucket() {
    return s3Bucket;
  }

  /**
   * Sets {@link #s3Bucket}.
   *
   * @param s3Bucket the new value
   */
  public void setS3Bucket(String s3Bucket) {
    this.s3Bucket = s3Bucket;
  }

  /**
   * S3 region
   */
  @JsonProperty("s3_region")
  public String s3Region;

  /**
   * Gets {@link #s3Region}.
   *
   * @return the value of {@link #s3Region}
   */
  public String getS3Region() {
    return s3Region;
  }

  /**
   * Sets {@link #s3Region}.
   *
   * @param s3Region the new value
   */
  public void setS3Region(String s3Region) {
    this.s3Region = s3Region;
  }

  /**
   * ARN of the AWS KMS key that encrypts files written to the bucket (SSE-KMS). Leave blank unless your bucket requires a specific key.
   */
  @JsonProperty("s3_kms_key_id")
  public String s3KmsKeyId;

  /**
   * Gets {@link #s3KmsKeyId}.
   *
   * @return the value of {@link #s3KmsKeyId}
   */
  public String getS3KmsKeyId() {
    return s3KmsKeyId;
  }

  /**
   * Sets {@link #s3KmsKeyId}.
   *
   * @param s3KmsKeyId the new value
   */
  public void setS3KmsKeyId(String s3KmsKeyId) {
    this.s3KmsKeyId = s3KmsKeyId;
  }

  /**
   * AWS Access Key.
   */
  @JsonProperty("aws_access_key")
  public String awsAccessKey;

  /**
   * Gets {@link #awsAccessKey}.
   *
   * @return the value of {@link #awsAccessKey}
   */
  public String getAwsAccessKey() {
    return awsAccessKey;
  }

  /**
   * Sets {@link #awsAccessKey}.
   *
   * @param awsAccessKey the new value
   */
  public void setAwsAccessKey(String awsAccessKey) {
    this.awsAccessKey = awsAccessKey;
  }

  /**
   * AWS IAM Role ARN for AssumeRole authentication.
   */
  @JsonProperty("s3_assume_role_arn")
  public String s3AssumeRoleArn;

  /**
   * Gets {@link #s3AssumeRoleArn}.
   *
   * @return the value of {@link #s3AssumeRoleArn}
   */
  public String getS3AssumeRoleArn() {
    return s3AssumeRoleArn;
  }

  /**
   * Sets {@link #s3AssumeRoleArn}.
   *
   * @param s3AssumeRoleArn the new value
   */
  public void setS3AssumeRoleArn(String s3AssumeRoleArn) {
    this.s3AssumeRoleArn = s3AssumeRoleArn;
  }

  /**
   * Session duration in seconds for AssumeRole authentication (900-43200).
   */
  @JsonProperty("s3_assume_role_duration_seconds")
  public Long s3AssumeRoleDurationSeconds;

  /**
   * Gets {@link #s3AssumeRoleDurationSeconds}.
   *
   * @return the value of {@link #s3AssumeRoleDurationSeconds}
   */
  public Long getS3AssumeRoleDurationSeconds() {
    return s3AssumeRoleDurationSeconds;
  }

  /**
   * Sets {@link #s3AssumeRoleDurationSeconds}.
   *
   * @param s3AssumeRoleDurationSeconds the new value
   */
  public void setS3AssumeRoleDurationSeconds(Long s3AssumeRoleDurationSeconds) {
    this.s3AssumeRoleDurationSeconds = s3AssumeRoleDurationSeconds;
  }

  /**
   * External ID for AssumeRole authentication.
   */
  @JsonProperty("s3_assume_role_external_id")
  public String s3AssumeRoleExternalId;

  /**
   * Gets {@link #s3AssumeRoleExternalId}.
   *
   * @return the value of {@link #s3AssumeRoleExternalId}
   */
  public String getS3AssumeRoleExternalId() {
    return s3AssumeRoleExternalId;
  }

  /**
   * Sets {@link #s3AssumeRoleExternalId}.
   *
   * @param s3AssumeRoleExternalId the new value
   */
  public void setS3AssumeRoleExternalId(String s3AssumeRoleExternalId) {
    this.s3AssumeRoleExternalId = s3AssumeRoleExternalId;
  }

  /**
   * Remote server certificate
   */
  @JsonProperty("server_certificate")
  public String serverCertificate;

  /**
   * Gets {@link #serverCertificate}.
   *
   * @return the value of {@link #serverCertificate}
   */
  public String getServerCertificate() {
    return serverCertificate;
  }

  /**
   * Sets {@link #serverCertificate}.
   *
   * @param serverCertificate the new value
   */
  public void setServerCertificate(String serverCertificate) {
    this.serverCertificate = serverCertificate;
  }

  /**
   * Pinned SSH host key or OpenSSH host certificate for SFTP. If omitted, Files.com detects and stores a host key, preferring plain keys over certificates. With `server_certificate=require_match` (the default), the server must present the exact pinned key or certificate and prove it holds the matching private key. A pinned certificate is compared in full, so renewal can require updating `server_host_key` even when its underlying key is unchanged. Files.com does not check certificate CA signatures, principals, or validity periods. Certificate expiration alone does not end the pin. Update `server_host_key` to replace the pin.
   */
  @JsonProperty("server_host_key")
  public String serverHostKey;

  /**
   * Gets {@link #serverHostKey}.
   *
   * @return the value of {@link #serverHostKey}
   */
  public String getServerHostKey() {
    return serverHostKey;
  }

  /**
   * Sets {@link #serverHostKey}.
   *
   * @param serverHostKey the new value
   */
  public void setServerHostKey(String serverHostKey) {
    this.serverHostKey = serverHostKey;
  }

  /**
   * Remote server type.
   */
  @JsonProperty("server_type")
  public String serverType;

  /**
   * Gets {@link #serverType}.
   *
   * @return the value of {@link #serverType}
   */
  public String getServerType() {
    return serverType;
  }

  /**
   * Sets {@link #serverType}.
   *
   * @param serverType the new value
   */
  public void setServerType(String serverType) {
    this.serverType = serverType;
  }

  /**
   * Workspace ID (0 for default workspace)
   */
  @JsonProperty("workspace_id")
  public Long workspaceId;

  /**
   * Gets {@link #workspaceId}.
   *
   * @return the value of {@link #workspaceId}
   */
  public Long getWorkspaceId() {
    return workspaceId;
  }

  /**
   * Sets {@link #workspaceId}.
   *
   * @param workspaceId the new value
   */
  public void setWorkspaceId(Long workspaceId) {
    this.workspaceId = workspaceId;
  }

  /**
   * Should we require SSL?
   */
  @JsonProperty("ssl")
  public String ssl;

  /**
   * Gets {@link #ssl}.
   *
   * @return the value of {@link #ssl}
   */
  public String getSsl() {
    return ssl;
  }

  /**
   * Sets {@link #ssl}.
   *
   * @param ssl the new value
   */
  public void setSsl(String ssl) {
    this.ssl = ssl;
  }

  /**
   * Remote server username.
   */
  @JsonProperty("username")
  public String username;

  /**
   * Gets {@link #username}.
   *
   * @return the value of {@link #username}
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets {@link #username}.
   *
   * @param username the new value
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Google Cloud Storage: Bucket Name
   */
  @JsonProperty("google_cloud_storage_bucket")
  public String googleCloudStorageBucket;

  /**
   * Gets {@link #googleCloudStorageBucket}.
   *
   * @return the value of {@link #googleCloudStorageBucket}
   */
  public String getGoogleCloudStorageBucket() {
    return googleCloudStorageBucket;
  }

  /**
   * Sets {@link #googleCloudStorageBucket}.
   *
   * @param googleCloudStorageBucket the new value
   */
  public void setGoogleCloudStorageBucket(String googleCloudStorageBucket) {
    this.googleCloudStorageBucket = googleCloudStorageBucket;
  }

  /**
   * Google Cloud Storage: Authentication method. Can be json, hmac, or oauth.
   */
  @JsonProperty("google_cloud_storage_authentication_method")
  public String googleCloudStorageAuthenticationMethod;

  /**
   * Gets {@link #googleCloudStorageAuthenticationMethod}.
   *
   * @return the value of {@link #googleCloudStorageAuthenticationMethod}
   */
  public String getGoogleCloudStorageAuthenticationMethod() {
    return googleCloudStorageAuthenticationMethod;
  }

  /**
   * Sets {@link #googleCloudStorageAuthenticationMethod}.
   *
   * @param googleCloudStorageAuthenticationMethod the new value
   */
  public void setGoogleCloudStorageAuthenticationMethod(String googleCloudStorageAuthenticationMethod) {
    this.googleCloudStorageAuthenticationMethod = googleCloudStorageAuthenticationMethod;
  }

  /**
   * Google Cloud Storage: OAuth scope. Can be https://www.googleapis.com/auth/devstorage.read_only or https://www.googleapis.com/auth/devstorage.read_write.
   */
  @JsonProperty("google_cloud_storage_oauth_scope")
  public String googleCloudStorageOauthScope;

  /**
   * Gets {@link #googleCloudStorageOauthScope}.
   *
   * @return the value of {@link #googleCloudStorageOauthScope}
   */
  public String getGoogleCloudStorageOauthScope() {
    return googleCloudStorageOauthScope;
  }

  /**
   * Sets {@link #googleCloudStorageOauthScope}.
   *
   * @param googleCloudStorageOauthScope the new value
   */
  public void setGoogleCloudStorageOauthScope(String googleCloudStorageOauthScope) {
    this.googleCloudStorageOauthScope = googleCloudStorageOauthScope;
  }

  /**
   * Google Cloud Storage: Project ID
   */
  @JsonProperty("google_cloud_storage_project_id")
  public String googleCloudStorageProjectId;

  /**
   * Gets {@link #googleCloudStorageProjectId}.
   *
   * @return the value of {@link #googleCloudStorageProjectId}
   */
  public String getGoogleCloudStorageProjectId() {
    return googleCloudStorageProjectId;
  }

  /**
   * Sets {@link #googleCloudStorageProjectId}.
   *
   * @param googleCloudStorageProjectId the new value
   */
  public void setGoogleCloudStorageProjectId(String googleCloudStorageProjectId) {
    this.googleCloudStorageProjectId = googleCloudStorageProjectId;
  }

  /**
   * Google Cloud Storage: S3-compatible Access Key.
   */
  @JsonProperty("google_cloud_storage_s3_compatible_access_key")
  public String googleCloudStorageS3CompatibleAccessKey;

  /**
   * Gets {@link #googleCloudStorageS3CompatibleAccessKey}.
   *
   * @return the value of {@link #googleCloudStorageS3CompatibleAccessKey}
   */
  public String getGoogleCloudStorageS3CompatibleAccessKey() {
    return googleCloudStorageS3CompatibleAccessKey;
  }

  /**
   * Sets {@link #googleCloudStorageS3CompatibleAccessKey}.
   *
   * @param googleCloudStorageS3CompatibleAccessKey the new value
   */
  public void setGoogleCloudStorageS3CompatibleAccessKey(String googleCloudStorageS3CompatibleAccessKey) {
    this.googleCloudStorageS3CompatibleAccessKey = googleCloudStorageS3CompatibleAccessKey;
  }

  /**
   * Backblaze B2 Cloud Storage: S3 Endpoint
   */
  @JsonProperty("backblaze_b2_s3_endpoint")
  public String backblazeB2S3Endpoint;

  /**
   * Gets {@link #backblazeB2S3Endpoint}.
   *
   * @return the value of {@link #backblazeB2S3Endpoint}
   */
  public String getBackblazeB2S3Endpoint() {
    return backblazeB2S3Endpoint;
  }

  /**
   * Sets {@link #backblazeB2S3Endpoint}.
   *
   * @param backblazeB2S3Endpoint the new value
   */
  public void setBackblazeB2S3Endpoint(String backblazeB2S3Endpoint) {
    this.backblazeB2S3Endpoint = backblazeB2S3Endpoint;
  }

  /**
   * Backblaze B2 Cloud Storage: Bucket name
   */
  @JsonProperty("backblaze_b2_bucket")
  public String backblazeB2Bucket;

  /**
   * Gets {@link #backblazeB2Bucket}.
   *
   * @return the value of {@link #backblazeB2Bucket}
   */
  public String getBackblazeB2Bucket() {
    return backblazeB2Bucket;
  }

  /**
   * Sets {@link #backblazeB2Bucket}.
   *
   * @param backblazeB2Bucket the new value
   */
  public void setBackblazeB2Bucket(String backblazeB2Bucket) {
    this.backblazeB2Bucket = backblazeB2Bucket;
  }

  /**
   * Wasabi: Bucket name
   */
  @JsonProperty("wasabi_bucket")
  public String wasabiBucket;

  /**
   * Gets {@link #wasabiBucket}.
   *
   * @return the value of {@link #wasabiBucket}
   */
  public String getWasabiBucket() {
    return wasabiBucket;
  }

  /**
   * Sets {@link #wasabiBucket}.
   *
   * @param wasabiBucket the new value
   */
  public void setWasabiBucket(String wasabiBucket) {
    this.wasabiBucket = wasabiBucket;
  }

  /**
   * Wasabi: Region
   */
  @JsonProperty("wasabi_region")
  public String wasabiRegion;

  /**
   * Gets {@link #wasabiRegion}.
   *
   * @return the value of {@link #wasabiRegion}
   */
  public String getWasabiRegion() {
    return wasabiRegion;
  }

  /**
   * Sets {@link #wasabiRegion}.
   *
   * @param wasabiRegion the new value
   */
  public void setWasabiRegion(String wasabiRegion) {
    this.wasabiRegion = wasabiRegion;
  }

  /**
   * Wasabi: Access Key.
   */
  @JsonProperty("wasabi_access_key")
  public String wasabiAccessKey;

  /**
   * Gets {@link #wasabiAccessKey}.
   *
   * @return the value of {@link #wasabiAccessKey}
   */
  public String getWasabiAccessKey() {
    return wasabiAccessKey;
  }

  /**
   * Sets {@link #wasabiAccessKey}.
   *
   * @param wasabiAccessKey the new value
   */
  public void setWasabiAccessKey(String wasabiAccessKey) {
    this.wasabiAccessKey = wasabiAccessKey;
  }

  /**
   * Either `in_setup` or `complete`
   */
  @JsonProperty("auth_status")
  public String authStatus;

  /**
   * Gets {@link #authStatus}.
   *
   * @return the value of {@link #authStatus}
   */
  public String getAuthStatus() {
    return authStatus;
  }

  /**
   * Sets {@link #authStatus}.
   *
   * @param authStatus the new value
   */
  public void setAuthStatus(String authStatus) {
    this.authStatus = authStatus;
  }

  /**
   * Describes the authorized account
   */
  @JsonProperty("auth_account_name")
  public String authAccountName;

  /**
   * Gets {@link #authAccountName}.
   *
   * @return the value of {@link #authAccountName}
   */
  public String getAuthAccountName() {
    return authAccountName;
  }

  /**
   * Sets {@link #authAccountName}.
   *
   * @param authAccountName the new value
   */
  public void setAuthAccountName(String authAccountName) {
    this.authAccountName = authAccountName;
  }

  /**
   * OneDrive: Either personal or business_other account types
   */
  @JsonProperty("one_drive_account_type")
  public String oneDriveAccountType;

  /**
   * Gets {@link #oneDriveAccountType}.
   *
   * @return the value of {@link #oneDriveAccountType}
   */
  public String getOneDriveAccountType() {
    return oneDriveAccountType;
  }

  /**
   * Sets {@link #oneDriveAccountType}.
   *
   * @param oneDriveAccountType the new value
   */
  public void setOneDriveAccountType(String oneDriveAccountType) {
    this.oneDriveAccountType = oneDriveAccountType;
  }

  /**
   * SharePoint: Microsoft Entra tenant ID for app-only authentication.
   */
  @JsonProperty("sharepoint_tenant_id")
  public String sharepointTenantId;

  /**
   * Gets {@link #sharepointTenantId}.
   *
   * @return the value of {@link #sharepointTenantId}
   */
  public String getSharepointTenantId() {
    return sharepointTenantId;
  }

  /**
   * Sets {@link #sharepointTenantId}.
   *
   * @param sharepointTenantId the new value
   */
  public void setSharepointTenantId(String sharepointTenantId) {
    this.sharepointTenantId = sharepointTenantId;
  }

  /**
   * SharePoint: Microsoft Entra application client ID for app-only authentication.
   */
  @JsonProperty("sharepoint_client_id")
  public String sharepointClientId;

  /**
   * Gets {@link #sharepointClientId}.
   *
   * @return the value of {@link #sharepointClientId}
   */
  public String getSharepointClientId() {
    return sharepointClientId;
  }

  /**
   * Sets {@link #sharepointClientId}.
   *
   * @param sharepointClientId the new value
   */
  public void setSharepointClientId(String sharepointClientId) {
    this.sharepointClientId = sharepointClientId;
  }

  /**
   * SharePoint: If true, this remote server uses Microsoft Entra app-only authentication.
   */
  @JsonProperty("sharepoint_app_authentication")
  public Boolean sharepointAppAuthentication;

  /**
   * Gets {@link #sharepointAppAuthentication}.
   *
   * @return the value of {@link #sharepointAppAuthentication}
   */
  public Boolean getSharepointAppAuthentication() {
    return sharepointAppAuthentication;
  }

  /**
   * Sets {@link #sharepointAppAuthentication}.
   *
   * @param sharepointAppAuthentication the new value
   */
  public void setSharepointAppAuthentication(Boolean sharepointAppAuthentication) {
    this.sharepointAppAuthentication = sharepointAppAuthentication;
  }

  /**
   * SharePoint: App-only credential type. Either secret or certificate.
   */
  @JsonProperty("sharepoint_app_credential_type")
  public String sharepointAppCredentialType;

  /**
   * Gets {@link #sharepointAppCredentialType}.
   *
   * @return the value of {@link #sharepointAppCredentialType}
   */
  public String getSharepointAppCredentialType() {
    return sharepointAppCredentialType;
  }

  /**
   * Sets {@link #sharepointAppCredentialType}.
   *
   * @param sharepointAppCredentialType the new value
   */
  public void setSharepointAppCredentialType(String sharepointAppCredentialType) {
    this.sharepointAppCredentialType = sharepointAppCredentialType;
  }

  /**
   * SharePoint: Site URL to scope app-only authentication to a single site. Leave blank to browse all sites.
   */
  @JsonProperty("sharepoint_site_url")
  public String sharepointSiteUrl;

  /**
   * Gets {@link #sharepointSiteUrl}.
   *
   * @return the value of {@link #sharepointSiteUrl}
   */
  public String getSharepointSiteUrl() {
    return sharepointSiteUrl;
  }

  /**
   * Sets {@link #sharepointSiteUrl}.
   *
   * @param sharepointSiteUrl the new value
   */
  public void setSharepointSiteUrl(String sharepointSiteUrl) {
    this.sharepointSiteUrl = sharepointSiteUrl;
  }

  /**
   * Azure Blob Storage: Account name
   */
  @JsonProperty("azure_blob_storage_account")
  public String azureBlobStorageAccount;

  /**
   * Gets {@link #azureBlobStorageAccount}.
   *
   * @return the value of {@link #azureBlobStorageAccount}
   */
  public String getAzureBlobStorageAccount() {
    return azureBlobStorageAccount;
  }

  /**
   * Sets {@link #azureBlobStorageAccount}.
   *
   * @param azureBlobStorageAccount the new value
   */
  public void setAzureBlobStorageAccount(String azureBlobStorageAccount) {
    this.azureBlobStorageAccount = azureBlobStorageAccount;
  }

  /**
   * Azure Blob Storage: Container name
   */
  @JsonProperty("azure_blob_storage_container")
  public String azureBlobStorageContainer;

  /**
   * Gets {@link #azureBlobStorageContainer}.
   *
   * @return the value of {@link #azureBlobStorageContainer}
   */
  public String getAzureBlobStorageContainer() {
    return azureBlobStorageContainer;
  }

  /**
   * Sets {@link #azureBlobStorageContainer}.
   *
   * @param azureBlobStorageContainer the new value
   */
  public void setAzureBlobStorageContainer(String azureBlobStorageContainer) {
    this.azureBlobStorageContainer = azureBlobStorageContainer;
  }

  /**
   * Azure Blob Storage: Does the storage account has hierarchical namespace feature enabled?
   */
  @JsonProperty("azure_blob_storage_hierarchical_namespace")
  public Boolean azureBlobStorageHierarchicalNamespace;

  /**
   * Gets {@link #azureBlobStorageHierarchicalNamespace}.
   *
   * @return the value of {@link #azureBlobStorageHierarchicalNamespace}
   */
  public Boolean getAzureBlobStorageHierarchicalNamespace() {
    return azureBlobStorageHierarchicalNamespace;
  }

  /**
   * Sets {@link #azureBlobStorageHierarchicalNamespace}.
   *
   * @param azureBlobStorageHierarchicalNamespace the new value
   */
  public void setAzureBlobStorageHierarchicalNamespace(Boolean azureBlobStorageHierarchicalNamespace) {
    this.azureBlobStorageHierarchicalNamespace = azureBlobStorageHierarchicalNamespace;
  }

  /**
   * Azure Blob Storage: Custom DNS suffix
   */
  @JsonProperty("azure_blob_storage_dns_suffix")
  public String azureBlobStorageDnsSuffix;

  /**
   * Gets {@link #azureBlobStorageDnsSuffix}.
   *
   * @return the value of {@link #azureBlobStorageDnsSuffix}
   */
  public String getAzureBlobStorageDnsSuffix() {
    return azureBlobStorageDnsSuffix;
  }

  /**
   * Sets {@link #azureBlobStorageDnsSuffix}.
   *
   * @param azureBlobStorageDnsSuffix the new value
   */
  public void setAzureBlobStorageDnsSuffix(String azureBlobStorageDnsSuffix) {
    this.azureBlobStorageDnsSuffix = azureBlobStorageDnsSuffix;
  }

  /**
   * Azure Files: Storage Account name
   */
  @JsonProperty("azure_files_storage_account")
  public String azureFilesStorageAccount;

  /**
   * Gets {@link #azureFilesStorageAccount}.
   *
   * @return the value of {@link #azureFilesStorageAccount}
   */
  public String getAzureFilesStorageAccount() {
    return azureFilesStorageAccount;
  }

  /**
   * Sets {@link #azureFilesStorageAccount}.
   *
   * @param azureFilesStorageAccount the new value
   */
  public void setAzureFilesStorageAccount(String azureFilesStorageAccount) {
    this.azureFilesStorageAccount = azureFilesStorageAccount;
  }

  /**
   * Azure Files:  Storage Share name
   */
  @JsonProperty("azure_files_storage_share_name")
  public String azureFilesStorageShareName;

  /**
   * Gets {@link #azureFilesStorageShareName}.
   *
   * @return the value of {@link #azureFilesStorageShareName}
   */
  public String getAzureFilesStorageShareName() {
    return azureFilesStorageShareName;
  }

  /**
   * Sets {@link #azureFilesStorageShareName}.
   *
   * @param azureFilesStorageShareName the new value
   */
  public void setAzureFilesStorageShareName(String azureFilesStorageShareName) {
    this.azureFilesStorageShareName = azureFilesStorageShareName;
  }

  /**
   * Azure Files: Custom DNS suffix
   */
  @JsonProperty("azure_files_storage_dns_suffix")
  public String azureFilesStorageDnsSuffix;

  /**
   * Gets {@link #azureFilesStorageDnsSuffix}.
   *
   * @return the value of {@link #azureFilesStorageDnsSuffix}
   */
  public String getAzureFilesStorageDnsSuffix() {
    return azureFilesStorageDnsSuffix;
  }

  /**
   * Sets {@link #azureFilesStorageDnsSuffix}.
   *
   * @param azureFilesStorageDnsSuffix the new value
   */
  public void setAzureFilesStorageDnsSuffix(String azureFilesStorageDnsSuffix) {
    this.azureFilesStorageDnsSuffix = azureFilesStorageDnsSuffix;
  }

  /**
   * S3-compatible: Bucket name
   */
  @JsonProperty("s3_compatible_bucket")
  public String s3CompatibleBucket;

  /**
   * Gets {@link #s3CompatibleBucket}.
   *
   * @return the value of {@link #s3CompatibleBucket}
   */
  public String getS3CompatibleBucket() {
    return s3CompatibleBucket;
  }

  /**
   * Sets {@link #s3CompatibleBucket}.
   *
   * @param s3CompatibleBucket the new value
   */
  public void setS3CompatibleBucket(String s3CompatibleBucket) {
    this.s3CompatibleBucket = s3CompatibleBucket;
  }

  /**
   * S3-compatible: endpoint
   */
  @JsonProperty("s3_compatible_endpoint")
  public String s3CompatibleEndpoint;

  /**
   * Gets {@link #s3CompatibleEndpoint}.
   *
   * @return the value of {@link #s3CompatibleEndpoint}
   */
  public String getS3CompatibleEndpoint() {
    return s3CompatibleEndpoint;
  }

  /**
   * Sets {@link #s3CompatibleEndpoint}.
   *
   * @param s3CompatibleEndpoint the new value
   */
  public void setS3CompatibleEndpoint(String s3CompatibleEndpoint) {
    this.s3CompatibleEndpoint = s3CompatibleEndpoint;
  }

  /**
   * S3-compatible: region
   */
  @JsonProperty("s3_compatible_region")
  public String s3CompatibleRegion;

  /**
   * Gets {@link #s3CompatibleRegion}.
   *
   * @return the value of {@link #s3CompatibleRegion}
   */
  public String getS3CompatibleRegion() {
    return s3CompatibleRegion;
  }

  /**
   * Sets {@link #s3CompatibleRegion}.
   *
   * @param s3CompatibleRegion the new value
   */
  public void setS3CompatibleRegion(String s3CompatibleRegion) {
    this.s3CompatibleRegion = s3CompatibleRegion;
  }

  /**
   * S3-compatible: If true, use virtual-hosted-style URLs instead of path-style URLs
   */
  @JsonProperty("s3_compatible_virtual_hosted_style")
  public Boolean s3CompatibleVirtualHostedStyle;

  /**
   * Gets {@link #s3CompatibleVirtualHostedStyle}.
   *
   * @return the value of {@link #s3CompatibleVirtualHostedStyle}
   */
  public Boolean getS3CompatibleVirtualHostedStyle() {
    return s3CompatibleVirtualHostedStyle;
  }

  /**
   * Sets {@link #s3CompatibleVirtualHostedStyle}.
   *
   * @param s3CompatibleVirtualHostedStyle the new value
   */
  public void setS3CompatibleVirtualHostedStyle(Boolean s3CompatibleVirtualHostedStyle) {
    this.s3CompatibleVirtualHostedStyle = s3CompatibleVirtualHostedStyle;
  }

  /**
   * S3-compatible: Access Key
   */
  @JsonProperty("s3_compatible_access_key")
  public String s3CompatibleAccessKey;

  /**
   * Gets {@link #s3CompatibleAccessKey}.
   *
   * @return the value of {@link #s3CompatibleAccessKey}
   */
  public String getS3CompatibleAccessKey() {
    return s3CompatibleAccessKey;
  }

  /**
   * Sets {@link #s3CompatibleAccessKey}.
   *
   * @param s3CompatibleAccessKey the new value
   */
  public void setS3CompatibleAccessKey(String s3CompatibleAccessKey) {
    this.s3CompatibleAccessKey = s3CompatibleAccessKey;
  }

  /**
   * `true` if remote server only accepts connections from dedicated IPs
   */
  @JsonProperty("enable_dedicated_ips")
  public Boolean enableDedicatedIps;

  /**
   * Gets {@link #enableDedicatedIps}.
   *
   * @return the value of {@link #enableDedicatedIps}
   */
  public Boolean getEnableDedicatedIps() {
    return enableDedicatedIps;
  }

  /**
   * Sets {@link #enableDedicatedIps}.
   *
   * @param enableDedicatedIps the new value
   */
  public void setEnableDedicatedIps(Boolean enableDedicatedIps) {
    this.enableDedicatedIps = enableDedicatedIps;
  }

  /**
   * Custom Domain ID whose dedicated IP addresses are selected when this Remote Server uses dedicated IPs. Must be available to this Remote Server's workspace. Requires enable_dedicated_ips and cannot be combined with an outbound Agent. Set to null to use the site's default dedicated IPs.
   */
  @JsonProperty("custom_domain_id")
  public Long customDomainId;

  /**
   * Gets {@link #customDomainId}.
   *
   * @return the value of {@link #customDomainId}
   */
  public Long getCustomDomainId() {
    return customDomainId;
  }

  /**
   * Sets {@link #customDomainId}.
   *
   * @param customDomainId the new value
   */
  public void setCustomDomainId(Long customDomainId) {
    this.customDomainId = customDomainId;
  }

  /**
   * Current eligible public IP addresses for the selected Custom Domain. Any address in this list may originate a connection. Empty when no domain is selected or its configuration is unavailable. Only included in responses for a single Remote Server.
   */
  @JsonProperty("outbound_ip_addresses")
  public String[] outboundIpAddresses;

  /**
   * Gets {@link #outboundIpAddresses}.
   *
   * @return the value of {@link #outboundIpAddresses}
   */
  public String[] getOutboundIpAddresses() {
    return outboundIpAddresses;
  }

  /**
   * Sets {@link #outboundIpAddresses}.
   *
   * @param outboundIpAddresses the new value
   */
  public void setOutboundIpAddresses(String[] outboundIpAddresses) {
    this.outboundIpAddresses = outboundIpAddresses;
  }

  /**
   * Agent file permissions: `read_only`, `write_only`, or `read_write`. Files.com writes this value as `permission_set` in the configuration file generated for this Remote Server. Setting it on Create or Update changes the generated file, not a running Agent. The Agent enforces the permissions in its local configuration file, controlled by the host's system administrator, and reports them at registration, replacing the stored value.
   */
  @JsonProperty("files_agent_permission_set")
  public String filesAgentPermissionSet;

  /**
   * Gets {@link #filesAgentPermissionSet}.
   *
   * @return the value of {@link #filesAgentPermissionSet}
   */
  public String getFilesAgentPermissionSet() {
    return filesAgentPermissionSet;
  }

  /**
   * Sets {@link #filesAgentPermissionSet}.
   *
   * @param filesAgentPermissionSet the new value
   */
  public void setFilesAgentPermissionSet(String filesAgentPermissionSet) {
    this.filesAgentPermissionSet = filesAgentPermissionSet;
  }

  /**
   * Agent local root path. Files.com writes this value as `root` in the configuration file generated for this Remote Server. Setting it on Create or Update changes the generated file, not a running Agent. The Agent enforces the root in its local configuration file, controlled by the host's system administrator, and reports it at registration, replacing the stored value.
   */
  @JsonProperty("files_agent_root")
  public String filesAgentRoot;

  /**
   * Gets {@link #filesAgentRoot}.
   *
   * @return the value of {@link #filesAgentRoot}
   */
  public String getFilesAgentRoot() {
    return filesAgentRoot;
  }

  /**
   * Sets {@link #filesAgentRoot}.
   *
   * @param filesAgentRoot the new value
   */
  public void setFilesAgentRoot(String filesAgentRoot) {
    this.filesAgentRoot = filesAgentRoot;
  }

  /**
   * Files.com Agent version reported by the Agent at each registration, replacing the stored value. This field is not included in the generated Agent configuration file. Setting it on Create or Update does not update the running Agent.
   */
  @JsonProperty("files_agent_version")
  public String filesAgentVersion;

  /**
   * Gets {@link #filesAgentVersion}.
   *
   * @return the value of {@link #filesAgentVersion}
   */
  public String getFilesAgentVersion() {
    return filesAgentVersion;
  }

  /**
   * Sets {@link #filesAgentVersion}.
   *
   * @param filesAgentVersion the new value
   */
  public void setFilesAgentVersion(String filesAgentVersion) {
    this.filesAgentVersion = filesAgentVersion;
  }

  /**
   * If true, the Files Agent is up to date.
   */
  @JsonProperty("files_agent_up_to_date")
  public Boolean filesAgentUpToDate;

  /**
   * Gets {@link #filesAgentUpToDate}.
   *
   * @return the value of {@link #filesAgentUpToDate}
   */
  public Boolean getFilesAgentUpToDate() {
    return filesAgentUpToDate;
  }

  /**
   * Sets {@link #filesAgentUpToDate}.
   *
   * @param filesAgentUpToDate the new value
   */
  public void setFilesAgentUpToDate(Boolean filesAgentUpToDate) {
    this.filesAgentUpToDate = filesAgentUpToDate;
  }

  /**
   * Latest available Files Agent version
   */
  @JsonProperty("files_agent_latest_version")
  public String filesAgentLatestVersion;

  /**
   * Gets {@link #filesAgentLatestVersion}.
   *
   * @return the value of {@link #filesAgentLatestVersion}
   */
  public String getFilesAgentLatestVersion() {
    return filesAgentLatestVersion;
  }

  /**
   * Sets {@link #filesAgentLatestVersion}.
   *
   * @param filesAgentLatestVersion the new value
   */
  public void setFilesAgentLatestVersion(String filesAgentLatestVersion) {
    this.filesAgentLatestVersion = filesAgentLatestVersion;
  }

  /**
   * Files Agent supports receiving push updates
   */
  @JsonProperty("files_agent_supports_push_updates")
  public Boolean filesAgentSupportsPushUpdates;

  /**
   * Gets {@link #filesAgentSupportsPushUpdates}.
   *
   * @return the value of {@link #filesAgentSupportsPushUpdates}
   */
  public Boolean getFilesAgentSupportsPushUpdates() {
    return filesAgentSupportsPushUpdates;
  }

  /**
   * Sets {@link #filesAgentSupportsPushUpdates}.
   *
   * @param filesAgentSupportsPushUpdates the new value
   */
  public void setFilesAgentSupportsPushUpdates(Boolean filesAgentSupportsPushUpdates) {
    this.filesAgentSupportsPushUpdates = filesAgentSupportsPushUpdates;
  }

  /**
   * Whether the Files Agent Proxy recently validated a direct transfer connection. `true` means a direct connection was recently validated (actual availability can vary by client network), `false` means direct transfers are enabled but not currently available, and `null` means direct transfers are disabled or unsupported. Only provided for a connected Files Agent when showing a single Remote Server.
   */
  @JsonProperty("direct_transfer_available")
  public Boolean directTransferAvailable;

  /**
   * Gets {@link #directTransferAvailable}.
   *
   * @return the value of {@link #directTransferAvailable}
   */
  public Boolean getDirectTransferAvailable() {
    return directTransferAvailable;
  }

  /**
   * Sets {@link #directTransferAvailable}.
   *
   * @param directTransferAvailable the new value
   */
  public void setDirectTransferAvailable(Boolean directTransferAvailable) {
    this.directTransferAvailable = directTransferAvailable;
  }

  /**
   * Route traffic to outbound on a files-agent
   */
  @JsonProperty("outbound_agent_id")
  public Long outboundAgentId;

  /**
   * Gets {@link #outboundAgentId}.
   *
   * @return the value of {@link #outboundAgentId}
   */
  public Long getOutboundAgentId() {
    return outboundAgentId;
  }

  /**
   * Sets {@link #outboundAgentId}.
   *
   * @param outboundAgentId the new value
   */
  public void setOutboundAgentId(Long outboundAgentId) {
    this.outboundAgentId = outboundAgentId;
  }

  /**
   * Filebase: Bucket name
   */
  @JsonProperty("filebase_bucket")
  public String filebaseBucket;

  /**
   * Gets {@link #filebaseBucket}.
   *
   * @return the value of {@link #filebaseBucket}
   */
  public String getFilebaseBucket() {
    return filebaseBucket;
  }

  /**
   * Sets {@link #filebaseBucket}.
   *
   * @param filebaseBucket the new value
   */
  public void setFilebaseBucket(String filebaseBucket) {
    this.filebaseBucket = filebaseBucket;
  }

  /**
   * Filebase: Access Key.
   */
  @JsonProperty("filebase_access_key")
  public String filebaseAccessKey;

  /**
   * Gets {@link #filebaseAccessKey}.
   *
   * @return the value of {@link #filebaseAccessKey}
   */
  public String getFilebaseAccessKey() {
    return filebaseAccessKey;
  }

  /**
   * Sets {@link #filebaseAccessKey}.
   *
   * @param filebaseAccessKey the new value
   */
  public void setFilebaseAccessKey(String filebaseAccessKey) {
    this.filebaseAccessKey = filebaseAccessKey;
  }

  /**
   * Files.com direct link: paired API key prefix.
   */
  @JsonProperty("files_api_key_prefix")
  public String filesApiKeyPrefix;

  /**
   * Gets {@link #filesApiKeyPrefix}.
   *
   * @return the value of {@link #filesApiKeyPrefix}
   */
  public String getFilesApiKeyPrefix() {
    return filesApiKeyPrefix;
  }

  /**
   * Sets {@link #filesApiKeyPrefix}.
   *
   * @param filesApiKeyPrefix the new value
   */
  public void setFilesApiKeyPrefix(String filesApiKeyPrefix) {
    this.filesApiKeyPrefix = filesApiKeyPrefix;
  }

  /**
   * Cloudflare: Bucket name
   */
  @JsonProperty("cloudflare_bucket")
  public String cloudflareBucket;

  /**
   * Gets {@link #cloudflareBucket}.
   *
   * @return the value of {@link #cloudflareBucket}
   */
  public String getCloudflareBucket() {
    return cloudflareBucket;
  }

  /**
   * Sets {@link #cloudflareBucket}.
   *
   * @param cloudflareBucket the new value
   */
  public void setCloudflareBucket(String cloudflareBucket) {
    this.cloudflareBucket = cloudflareBucket;
  }

  /**
   * Cloudflare: Access Key.
   */
  @JsonProperty("cloudflare_access_key")
  public String cloudflareAccessKey;

  /**
   * Gets {@link #cloudflareAccessKey}.
   *
   * @return the value of {@link #cloudflareAccessKey}
   */
  public String getCloudflareAccessKey() {
    return cloudflareAccessKey;
  }

  /**
   * Sets {@link #cloudflareAccessKey}.
   *
   * @param cloudflareAccessKey the new value
   */
  public void setCloudflareAccessKey(String cloudflareAccessKey) {
    this.cloudflareAccessKey = cloudflareAccessKey;
  }

  /**
   * Cloudflare: endpoint
   */
  @JsonProperty("cloudflare_endpoint")
  public String cloudflareEndpoint;

  /**
   * Gets {@link #cloudflareEndpoint}.
   *
   * @return the value of {@link #cloudflareEndpoint}
   */
  public String getCloudflareEndpoint() {
    return cloudflareEndpoint;
  }

  /**
   * Sets {@link #cloudflareEndpoint}.
   *
   * @param cloudflareEndpoint the new value
   */
  public void setCloudflareEndpoint(String cloudflareEndpoint) {
    this.cloudflareEndpoint = cloudflareEndpoint;
  }

  /**
   * Dropbox: If true, list Team folders in root?
   */
  @JsonProperty("dropbox_teams")
  public Boolean dropboxTeams;

  /**
   * Gets {@link #dropboxTeams}.
   *
   * @return the value of {@link #dropboxTeams}
   */
  public Boolean getDropboxTeams() {
    return dropboxTeams;
  }

  /**
   * Sets {@link #dropboxTeams}.
   *
   * @param dropboxTeams the new value
   */
  public void setDropboxTeams(Boolean dropboxTeams) {
    this.dropboxTeams = dropboxTeams;
  }

  /**
   * Linode: Bucket name
   */
  @JsonProperty("linode_bucket")
  public String linodeBucket;

  /**
   * Gets {@link #linodeBucket}.
   *
   * @return the value of {@link #linodeBucket}
   */
  public String getLinodeBucket() {
    return linodeBucket;
  }

  /**
   * Sets {@link #linodeBucket}.
   *
   * @param linodeBucket the new value
   */
  public void setLinodeBucket(String linodeBucket) {
    this.linodeBucket = linodeBucket;
  }

  /**
   * Linode: Access Key
   */
  @JsonProperty("linode_access_key")
  public String linodeAccessKey;

  /**
   * Gets {@link #linodeAccessKey}.
   *
   * @return the value of {@link #linodeAccessKey}
   */
  public String getLinodeAccessKey() {
    return linodeAccessKey;
  }

  /**
   * Sets {@link #linodeAccessKey}.
   *
   * @param linodeAccessKey the new value
   */
  public void setLinodeAccessKey(String linodeAccessKey) {
    this.linodeAccessKey = linodeAccessKey;
  }

  /**
   * Linode: region
   */
  @JsonProperty("linode_region")
  public String linodeRegion;

  /**
   * Gets {@link #linodeRegion}.
   *
   * @return the value of {@link #linodeRegion}
   */
  public String getLinodeRegion() {
    return linodeRegion;
  }

  /**
   * Sets {@link #linodeRegion}.
   *
   * @param linodeRegion the new value
   */
  public void setLinodeRegion(String linodeRegion) {
    this.linodeRegion = linodeRegion;
  }

  /**
   * If true, this remote server supports file versioning. This value is determined automatically by Files.com.
   */
  @JsonProperty("supports_versioning")
  public Boolean supportsVersioning;

  /**
   * Gets {@link #supportsVersioning}.
   *
   * @return the value of {@link #supportsVersioning}
   */
  public Boolean getSupportsVersioning() {
    return supportsVersioning;
  }

  /**
   * Sets {@link #supportsVersioning}.
   *
   * @param supportsVersioning the new value
   */
  public void setSupportsVersioning(Boolean supportsVersioning) {
    this.supportsVersioning = supportsVersioning;
  }

  /**
   * User ID.  Provide a value of `0` to operate the current session's user.
   */
  @JsonProperty("user_id")
  public Long userId;

  /**
   * Gets {@link #userId}.
   *
   * @return the value of {@link #userId}
   */
  public Long getUserId() {
    return userId;
  }

  /**
   * Sets {@link #userId}.
   *
   * @param userId the new value
   */
  public void setUserId(Long userId) {
    this.userId = userId;
  }

  /**
   * Password, if needed.
   */
  @JsonProperty("password")
  public String password;

  /**
   * Gets {@link #password}.
   *
   * @return the value of {@link #password}
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets {@link #password}.
   *
   * @param password the new value
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Private key, if needed.
   */
  @JsonProperty("private_key")
  public String privateKey;

  /**
   * Gets {@link #privateKey}.
   *
   * @return the value of {@link #privateKey}
   */
  public String getPrivateKey() {
    return privateKey;
  }

  /**
   * Sets {@link #privateKey}.
   *
   * @param privateKey the new value
   */
  public void setPrivateKey(String privateKey) {
    this.privateKey = privateKey;
  }

  /**
   * Passphrase for private key if needed.
   */
  @JsonProperty("private_key_passphrase")
  public String privateKeyPassphrase;

  /**
   * Gets {@link #privateKeyPassphrase}.
   *
   * @return the value of {@link #privateKeyPassphrase}
   */
  public String getPrivateKeyPassphrase() {
    return privateKeyPassphrase;
  }

  /**
   * Sets {@link #privateKeyPassphrase}.
   *
   * @param privateKeyPassphrase the new value
   */
  public void setPrivateKeyPassphrase(String privateKeyPassphrase) {
    this.privateKeyPassphrase = privateKeyPassphrase;
  }

  /**
   * Reset authenticated account?
   */
  @JsonProperty("reset_authentication")
  public Boolean resetAuthentication;

  /**
   * Gets {@link #resetAuthentication}.
   *
   * @return the value of {@link #resetAuthentication}
   */
  public Boolean getResetAuthentication() {
    return resetAuthentication;
  }

  /**
   * Sets {@link #resetAuthentication}.
   *
   * @param resetAuthentication the new value
   */
  public void setResetAuthentication(Boolean resetAuthentication) {
    this.resetAuthentication = resetAuthentication;
  }

  /**
   * SharePoint: PEM-encoded certificate and unencrypted private key for app-only authentication.
   */
  @JsonProperty("sharepoint_client_certificate")
  public String sharepointClientCertificate;

  /**
   * Gets {@link #sharepointClientCertificate}.
   *
   * @return the value of {@link #sharepointClientCertificate}
   */
  public String getSharepointClientCertificate() {
    return sharepointClientCertificate;
  }

  /**
   * Sets {@link #sharepointClientCertificate}.
   *
   * @param sharepointClientCertificate the new value
   */
  public void setSharepointClientCertificate(String sharepointClientCertificate) {
    this.sharepointClientCertificate = sharepointClientCertificate;
  }

  /**
   * SharePoint: Microsoft Entra application client secret for app-only authentication.
   */
  @JsonProperty("sharepoint_client_secret")
  public String sharepointClientSecret;

  /**
   * Gets {@link #sharepointClientSecret}.
   *
   * @return the value of {@link #sharepointClientSecret}
   */
  public String getSharepointClientSecret() {
    return sharepointClientSecret;
  }

  /**
   * Sets {@link #sharepointClientSecret}.
   *
   * @param sharepointClientSecret the new value
   */
  public void setSharepointClientSecret(String sharepointClientSecret) {
    this.sharepointClientSecret = sharepointClientSecret;
  }

  /**
   * SSL client certificate.
   */
  @JsonProperty("ssl_certificate")
  public String sslCertificate;

  /**
   * Gets {@link #sslCertificate}.
   *
   * @return the value of {@link #sslCertificate}
   */
  public String getSslCertificate() {
    return sslCertificate;
  }

  /**
   * Sets {@link #sslCertificate}.
   *
   * @param sslCertificate the new value
   */
  public void setSslCertificate(String sslCertificate) {
    this.sslCertificate = sslCertificate;
  }

  /**
   * AWS: secret key.
   */
  @JsonProperty("aws_secret_key")
  public String awsSecretKey;

  /**
   * Gets {@link #awsSecretKey}.
   *
   * @return the value of {@link #awsSecretKey}
   */
  public String getAwsSecretKey() {
    return awsSecretKey;
  }

  /**
   * Sets {@link #awsSecretKey}.
   *
   * @param awsSecretKey the new value
   */
  public void setAwsSecretKey(String awsSecretKey) {
    this.awsSecretKey = awsSecretKey;
  }

  /**
   * Azure Blob Storage: Access Key
   */
  @JsonProperty("azure_blob_storage_access_key")
  public String azureBlobStorageAccessKey;

  /**
   * Gets {@link #azureBlobStorageAccessKey}.
   *
   * @return the value of {@link #azureBlobStorageAccessKey}
   */
  public String getAzureBlobStorageAccessKey() {
    return azureBlobStorageAccessKey;
  }

  /**
   * Sets {@link #azureBlobStorageAccessKey}.
   *
   * @param azureBlobStorageAccessKey the new value
   */
  public void setAzureBlobStorageAccessKey(String azureBlobStorageAccessKey) {
    this.azureBlobStorageAccessKey = azureBlobStorageAccessKey;
  }

  /**
   * Azure Blob Storage: Shared Access Signature (SAS) token
   */
  @JsonProperty("azure_blob_storage_sas_token")
  public String azureBlobStorageSasToken;

  /**
   * Gets {@link #azureBlobStorageSasToken}.
   *
   * @return the value of {@link #azureBlobStorageSasToken}
   */
  public String getAzureBlobStorageSasToken() {
    return azureBlobStorageSasToken;
  }

  /**
   * Sets {@link #azureBlobStorageSasToken}.
   *
   * @param azureBlobStorageSasToken the new value
   */
  public void setAzureBlobStorageSasToken(String azureBlobStorageSasToken) {
    this.azureBlobStorageSasToken = azureBlobStorageSasToken;
  }

  /**
   * Azure File Storage: Access Key
   */
  @JsonProperty("azure_files_storage_access_key")
  public String azureFilesStorageAccessKey;

  /**
   * Gets {@link #azureFilesStorageAccessKey}.
   *
   * @return the value of {@link #azureFilesStorageAccessKey}
   */
  public String getAzureFilesStorageAccessKey() {
    return azureFilesStorageAccessKey;
  }

  /**
   * Sets {@link #azureFilesStorageAccessKey}.
   *
   * @param azureFilesStorageAccessKey the new value
   */
  public void setAzureFilesStorageAccessKey(String azureFilesStorageAccessKey) {
    this.azureFilesStorageAccessKey = azureFilesStorageAccessKey;
  }

  /**
   * Azure File Storage: Shared Access Signature (SAS) token
   */
  @JsonProperty("azure_files_storage_sas_token")
  public String azureFilesStorageSasToken;

  /**
   * Gets {@link #azureFilesStorageSasToken}.
   *
   * @return the value of {@link #azureFilesStorageSasToken}
   */
  public String getAzureFilesStorageSasToken() {
    return azureFilesStorageSasToken;
  }

  /**
   * Sets {@link #azureFilesStorageSasToken}.
   *
   * @param azureFilesStorageSasToken the new value
   */
  public void setAzureFilesStorageSasToken(String azureFilesStorageSasToken) {
    this.azureFilesStorageSasToken = azureFilesStorageSasToken;
  }

  /**
   * Backblaze B2 Cloud Storage: applicationKey
   */
  @JsonProperty("backblaze_b2_application_key")
  public String backblazeB2ApplicationKey;

  /**
   * Gets {@link #backblazeB2ApplicationKey}.
   *
   * @return the value of {@link #backblazeB2ApplicationKey}
   */
  public String getBackblazeB2ApplicationKey() {
    return backblazeB2ApplicationKey;
  }

  /**
   * Sets {@link #backblazeB2ApplicationKey}.
   *
   * @param backblazeB2ApplicationKey the new value
   */
  public void setBackblazeB2ApplicationKey(String backblazeB2ApplicationKey) {
    this.backblazeB2ApplicationKey = backblazeB2ApplicationKey;
  }

  /**
   * Backblaze B2 Cloud Storage: keyID
   */
  @JsonProperty("backblaze_b2_key_id")
  public String backblazeB2KeyId;

  /**
   * Gets {@link #backblazeB2KeyId}.
   *
   * @return the value of {@link #backblazeB2KeyId}
   */
  public String getBackblazeB2KeyId() {
    return backblazeB2KeyId;
  }

  /**
   * Sets {@link #backblazeB2KeyId}.
   *
   * @param backblazeB2KeyId the new value
   */
  public void setBackblazeB2KeyId(String backblazeB2KeyId) {
    this.backblazeB2KeyId = backblazeB2KeyId;
  }

  /**
   * Cloudflare: Secret Key
   */
  @JsonProperty("cloudflare_secret_key")
  public String cloudflareSecretKey;

  /**
   * Gets {@link #cloudflareSecretKey}.
   *
   * @return the value of {@link #cloudflareSecretKey}
   */
  public String getCloudflareSecretKey() {
    return cloudflareSecretKey;
  }

  /**
   * Sets {@link #cloudflareSecretKey}.
   *
   * @param cloudflareSecretKey the new value
   */
  public void setCloudflareSecretKey(String cloudflareSecretKey) {
    this.cloudflareSecretKey = cloudflareSecretKey;
  }

  /**
   * Filebase: Secret Key
   */
  @JsonProperty("filebase_secret_key")
  public String filebaseSecretKey;

  /**
   * Gets {@link #filebaseSecretKey}.
   *
   * @return the value of {@link #filebaseSecretKey}
   */
  public String getFilebaseSecretKey() {
    return filebaseSecretKey;
  }

  /**
   * Sets {@link #filebaseSecretKey}.
   *
   * @param filebaseSecretKey the new value
   */
  public void setFilebaseSecretKey(String filebaseSecretKey) {
    this.filebaseSecretKey = filebaseSecretKey;
  }

  /**
   * Google Cloud Storage: JSON file that contains the private key. To generate see https://cloud.google.com/storage/docs/json_api/v1/how-tos/authorizing#APIKey
   */
  @JsonProperty("google_cloud_storage_credentials_json")
  public String googleCloudStorageCredentialsJson;

  /**
   * Gets {@link #googleCloudStorageCredentialsJson}.
   *
   * @return the value of {@link #googleCloudStorageCredentialsJson}
   */
  public String getGoogleCloudStorageCredentialsJson() {
    return googleCloudStorageCredentialsJson;
  }

  /**
   * Sets {@link #googleCloudStorageCredentialsJson}.
   *
   * @param googleCloudStorageCredentialsJson the new value
   */
  public void setGoogleCloudStorageCredentialsJson(String googleCloudStorageCredentialsJson) {
    this.googleCloudStorageCredentialsJson = googleCloudStorageCredentialsJson;
  }

  /**
   * Google Cloud Storage: S3-compatible secret key
   */
  @JsonProperty("google_cloud_storage_s3_compatible_secret_key")
  public String googleCloudStorageS3CompatibleSecretKey;

  /**
   * Gets {@link #googleCloudStorageS3CompatibleSecretKey}.
   *
   * @return the value of {@link #googleCloudStorageS3CompatibleSecretKey}
   */
  public String getGoogleCloudStorageS3CompatibleSecretKey() {
    return googleCloudStorageS3CompatibleSecretKey;
  }

  /**
   * Sets {@link #googleCloudStorageS3CompatibleSecretKey}.
   *
   * @param googleCloudStorageS3CompatibleSecretKey the new value
   */
  public void setGoogleCloudStorageS3CompatibleSecretKey(String googleCloudStorageS3CompatibleSecretKey) {
    this.googleCloudStorageS3CompatibleSecretKey = googleCloudStorageS3CompatibleSecretKey;
  }

  /**
   * Linode: Secret Key
   */
  @JsonProperty("linode_secret_key")
  public String linodeSecretKey;

  /**
   * Gets {@link #linodeSecretKey}.
   *
   * @return the value of {@link #linodeSecretKey}
   */
  public String getLinodeSecretKey() {
    return linodeSecretKey;
  }

  /**
   * Sets {@link #linodeSecretKey}.
   *
   * @param linodeSecretKey the new value
   */
  public void setLinodeSecretKey(String linodeSecretKey) {
    this.linodeSecretKey = linodeSecretKey;
  }

  /**
   * S3-compatible: Secret Key
   */
  @JsonProperty("s3_compatible_secret_key")
  public String s3CompatibleSecretKey;

  /**
   * Gets {@link #s3CompatibleSecretKey}.
   *
   * @return the value of {@link #s3CompatibleSecretKey}
   */
  public String getS3CompatibleSecretKey() {
    return s3CompatibleSecretKey;
  }

  /**
   * Sets {@link #s3CompatibleSecretKey}.
   *
   * @param s3CompatibleSecretKey the new value
   */
  public void setS3CompatibleSecretKey(String s3CompatibleSecretKey) {
    this.s3CompatibleSecretKey = s3CompatibleSecretKey;
  }

  /**
   * Wasabi: Secret Key
   */
  @JsonProperty("wasabi_secret_key")
  public String wasabiSecretKey;

  /**
   * Gets {@link #wasabiSecretKey}.
   *
   * @return the value of {@link #wasabiSecretKey}
   */
  public String getWasabiSecretKey() {
    return wasabiSecretKey;
  }

  /**
   * Sets {@link #wasabiSecretKey}.
   *
   * @param wasabiSecretKey the new value
   */
  public void setWasabiSecretKey(String wasabiSecretKey) {
    this.wasabiSecretKey = wasabiSecretKey;
  }

  /**
   * Files.com direct link: API key used once to pair the remote server.
   */
  @JsonProperty("files_api_key")
  public String filesApiKey;

  /**
   * Gets {@link #filesApiKey}.
   *
   * @return the value of {@link #filesApiKey}
   */
  public String getFilesApiKey() {
    return filesApiKey;
  }

  /**
   * Sets {@link #filesApiKey}.
   *
   * @param filesApiKey the new value
   */
  public void setFilesApiKey(String filesApiKey) {
    this.filesApiKey = filesApiKey;
  }

  /**
   * List Files.com Agent nodes
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code AgentNode}
   */
  public AgentNode agentNodes(HashMap<String, Object> parameters) throws IOException {
    return filesClient().remoteServers().agentNodes(this.id, parameters, this.options);
  }

  /**
   * Push update to Files Agent
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code AgentPushUpdate}
   */
  public AgentPushUpdate agentPushUpdate(HashMap<String, Object> parameters) throws IOException {
    return filesClient().remoteServers().agentPushUpdate(this.id, parameters, this.options);
  }

  /**
   * Updates this RemoteServer. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update Remote Server
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
   * @param parameters the request's parameters, by their API names
   * @return the {@code RemoteServer}
   */
  public RemoteServer update(HashMap<String, Object> parameters) throws IOException {
    RemoteServer updated = filesClient().remoteServers().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete Remote Server
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().remoteServers().delete(this.id, parameters, this.options);
  }

  /**
   * Delete Remote Server
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this RemoteServer: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().remoteServers().create(parameters, this.options));
    }
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    return ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(RemoteServer canonical) {
    this.id = canonical.id;
    this.disabled = canonical.disabled;
    this.authenticationMethod = canonical.authenticationMethod;
    this.hostname = canonical.hostname;
    this.remoteHomePath = canonical.remoteHomePath;
    this.uploadStagingPath = canonical.uploadStagingPath;
    this.allowRelativePaths = canonical.allowRelativePaths;
    this.name = canonical.name;
    this.description = canonical.description;
    this.port = canonical.port;
    this.bufferUploads = canonical.bufferUploads;
    this.maxConnections = canonical.maxConnections;
    this.pinToSiteRegion = canonical.pinToSiteRegion;
    this.pinnedRegion = canonical.pinnedRegion;
    this.remoteServerCredentialId = canonical.remoteServerCredentialId;
    this.s3Bucket = canonical.s3Bucket;
    this.s3Region = canonical.s3Region;
    this.s3KmsKeyId = canonical.s3KmsKeyId;
    this.awsAccessKey = canonical.awsAccessKey;
    this.s3AssumeRoleArn = canonical.s3AssumeRoleArn;
    this.s3AssumeRoleDurationSeconds = canonical.s3AssumeRoleDurationSeconds;
    this.s3AssumeRoleExternalId = canonical.s3AssumeRoleExternalId;
    this.serverCertificate = canonical.serverCertificate;
    this.serverHostKey = canonical.serverHostKey;
    this.serverType = canonical.serverType;
    this.workspaceId = canonical.workspaceId;
    this.ssl = canonical.ssl;
    this.username = canonical.username;
    this.googleCloudStorageBucket = canonical.googleCloudStorageBucket;
    this.googleCloudStorageAuthenticationMethod = canonical.googleCloudStorageAuthenticationMethod;
    this.googleCloudStorageOauthScope = canonical.googleCloudStorageOauthScope;
    this.googleCloudStorageProjectId = canonical.googleCloudStorageProjectId;
    this.googleCloudStorageS3CompatibleAccessKey = canonical.googleCloudStorageS3CompatibleAccessKey;
    this.backblazeB2S3Endpoint = canonical.backblazeB2S3Endpoint;
    this.backblazeB2Bucket = canonical.backblazeB2Bucket;
    this.wasabiBucket = canonical.wasabiBucket;
    this.wasabiRegion = canonical.wasabiRegion;
    this.wasabiAccessKey = canonical.wasabiAccessKey;
    this.authStatus = canonical.authStatus;
    this.authAccountName = canonical.authAccountName;
    this.oneDriveAccountType = canonical.oneDriveAccountType;
    this.sharepointTenantId = canonical.sharepointTenantId;
    this.sharepointClientId = canonical.sharepointClientId;
    this.sharepointAppAuthentication = canonical.sharepointAppAuthentication;
    this.sharepointAppCredentialType = canonical.sharepointAppCredentialType;
    this.sharepointSiteUrl = canonical.sharepointSiteUrl;
    this.azureBlobStorageAccount = canonical.azureBlobStorageAccount;
    this.azureBlobStorageContainer = canonical.azureBlobStorageContainer;
    this.azureBlobStorageHierarchicalNamespace = canonical.azureBlobStorageHierarchicalNamespace;
    this.azureBlobStorageDnsSuffix = canonical.azureBlobStorageDnsSuffix;
    this.azureFilesStorageAccount = canonical.azureFilesStorageAccount;
    this.azureFilesStorageShareName = canonical.azureFilesStorageShareName;
    this.azureFilesStorageDnsSuffix = canonical.azureFilesStorageDnsSuffix;
    this.s3CompatibleBucket = canonical.s3CompatibleBucket;
    this.s3CompatibleEndpoint = canonical.s3CompatibleEndpoint;
    this.s3CompatibleRegion = canonical.s3CompatibleRegion;
    this.s3CompatibleVirtualHostedStyle = canonical.s3CompatibleVirtualHostedStyle;
    this.s3CompatibleAccessKey = canonical.s3CompatibleAccessKey;
    this.enableDedicatedIps = canonical.enableDedicatedIps;
    this.customDomainId = canonical.customDomainId;
    this.outboundIpAddresses = canonical.outboundIpAddresses;
    this.filesAgentPermissionSet = canonical.filesAgentPermissionSet;
    this.filesAgentRoot = canonical.filesAgentRoot;
    this.filesAgentVersion = canonical.filesAgentVersion;
    this.filesAgentUpToDate = canonical.filesAgentUpToDate;
    this.filesAgentLatestVersion = canonical.filesAgentLatestVersion;
    this.filesAgentSupportsPushUpdates = canonical.filesAgentSupportsPushUpdates;
    this.directTransferAvailable = canonical.directTransferAvailable;
    this.outboundAgentId = canonical.outboundAgentId;
    this.filebaseBucket = canonical.filebaseBucket;
    this.filebaseAccessKey = canonical.filebaseAccessKey;
    this.filesApiKeyPrefix = canonical.filesApiKeyPrefix;
    this.cloudflareBucket = canonical.cloudflareBucket;
    this.cloudflareAccessKey = canonical.cloudflareAccessKey;
    this.cloudflareEndpoint = canonical.cloudflareEndpoint;
    this.dropboxTeams = canonical.dropboxTeams;
    this.linodeBucket = canonical.linodeBucket;
    this.linodeAccessKey = canonical.linodeAccessKey;
    this.linodeRegion = canonical.linodeRegion;
    this.supportsVersioning = canonical.supportsVersioning;
    this.userId = canonical.userId;
    this.password = canonical.password;
    this.privateKey = canonical.privateKey;
    this.privateKeyPassphrase = canonical.privateKeyPassphrase;
    this.resetAuthentication = canonical.resetAuthentication;
    this.sharepointClientCertificate = canonical.sharepointClientCertificate;
    this.sharepointClientSecret = canonical.sharepointClientSecret;
    this.sslCertificate = canonical.sslCertificate;
    this.awsSecretKey = canonical.awsSecretKey;
    this.azureBlobStorageAccessKey = canonical.azureBlobStorageAccessKey;
    this.azureBlobStorageSasToken = canonical.azureBlobStorageSasToken;
    this.azureFilesStorageAccessKey = canonical.azureFilesStorageAccessKey;
    this.azureFilesStorageSasToken = canonical.azureFilesStorageSasToken;
    this.backblazeB2ApplicationKey = canonical.backblazeB2ApplicationKey;
    this.backblazeB2KeyId = canonical.backblazeB2KeyId;
    this.cloudflareSecretKey = canonical.cloudflareSecretKey;
    this.filebaseSecretKey = canonical.filebaseSecretKey;
    this.googleCloudStorageCredentialsJson = canonical.googleCloudStorageCredentialsJson;
    this.googleCloudStorageS3CompatibleSecretKey = canonical.googleCloudStorageS3CompatibleSecretKey;
    this.linodeSecretKey = canonical.linodeSecretKey;
    this.s3CompatibleSecretKey = canonical.s3CompatibleSecretKey;
    this.wasabiSecretKey = canonical.wasabiSecretKey;
    this.filesApiKey = canonical.filesApiKey;
  }

  /**
   * List Remote Servers
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<RemoteServer> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Remote Servers
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<RemoteServer> list(HashMap<String, Object> parameters) throws RuntimeException {
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
  public static ListIterator<RemoteServer> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().remoteServers().list(parameters, options);
  }

  /**
   * List Remote Servers
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<RemoteServer> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Remote Servers
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<RemoteServer> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Remote Server
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static RemoteServer find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Remote Server
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static RemoteServer find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Remote Server
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static RemoteServer find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public static RemoteServer find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().remoteServers().find(id, parameters, options);
  }

  /**
   * Show Remote Server
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static RemoteServer get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Remote Server
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static RemoteServer get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * List Files.com Agent nodes
   *
   * @see #agentNodes(Long, HashMap, HashMap)
   */
  public static AgentNode agentNodes() throws RuntimeException {
    return agentNodes(null, null, null);
  }

  /**
   * List Files.com Agent nodes
   *
   * @see #agentNodes(Long, HashMap, HashMap)
   */
  public static AgentNode agentNodes(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return agentNodes(id, parameters, null);
  }

  /**
   * List Files.com Agent nodes
   *
   * @see #agentNodes(Long, HashMap, HashMap)
   */
  public static AgentNode agentNodes(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public static AgentNode agentNodes(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().remoteServers().agentNodes(id, parameters, options);
  }


  /**
   * Download configuration file (required for some Remote Server integrations, such as the Files.com Agent)
   *
   * @see #findConfigurationFile(Long, HashMap, HashMap)
   */
  public static RemoteServerConfigurationFile findConfigurationFile() throws RuntimeException {
    return findConfigurationFile(null, null, null);
  }

  /**
   * Download configuration file (required for some Remote Server integrations, such as the Files.com Agent)
   *
   * @see #findConfigurationFile(Long, HashMap, HashMap)
   */
  public static RemoteServerConfigurationFile findConfigurationFile(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return findConfigurationFile(id, parameters, null);
  }

  /**
   * Download configuration file (required for some Remote Server integrations, such as the Files.com Agent)
   *
   * @see #findConfigurationFile(Long, HashMap, HashMap)
   */
  public static RemoteServerConfigurationFile findConfigurationFile(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public static RemoteServerConfigurationFile findConfigurationFile(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().remoteServers().findConfigurationFile(id, parameters, options);
  }


  /**
   * Create Remote Server
   *
   * @see #create(HashMap, HashMap)
   */
  public static RemoteServer create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Remote Server
   *
   * @see #create(HashMap, HashMap)
   */
  public static RemoteServer create(HashMap<String, Object> parameters) throws RuntimeException {
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
  public static RemoteServer create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().remoteServers().create(parameters, options);
  }


  /**
   * Push update to Files Agent
   *
   * @see #agentPushUpdate(Long, HashMap, HashMap)
   */
  public static AgentPushUpdate agentPushUpdate() throws RuntimeException {
    return agentPushUpdate(null, null, null);
  }

  /**
   * Push update to Files Agent
   *
   * @see #agentPushUpdate(Long, HashMap, HashMap)
   */
  public static AgentPushUpdate agentPushUpdate(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return agentPushUpdate(id, parameters, null);
  }

  /**
   * Push update to Files Agent
   *
   * @see #agentPushUpdate(Long, HashMap, HashMap)
   */
  public static AgentPushUpdate agentPushUpdate(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public static AgentPushUpdate agentPushUpdate(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().remoteServers().agentPushUpdate(id, parameters, options);
  }


  /**
   * Update Remote Server
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static RemoteServer update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Remote Server
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static RemoteServer update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Remote Server
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static RemoteServer update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public static RemoteServer update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().remoteServers().update(id, parameters, options);
  }


  /**
   * Delete Remote Server
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Remote Server
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Remote Server
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Remote Server
   *
   * @param id Remote Server ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().remoteServers().delete(id, parameters, options);
  }

  /**
   * Delete Remote Server
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Remote Server
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
