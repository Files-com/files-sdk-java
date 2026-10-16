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
public class RemoteServerCredential implements ModelInterface {
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

  // One mapper for every RemoteServerCredential, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  public RemoteServerCredential() {
    this(null, null);
  }

  public RemoteServerCredential(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public RemoteServerCredential(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Remote Server Credential ID
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
   * Workspace ID (0 for default workspace)
   */
  @JsonProperty("workspace_id")
  public Long workspaceId;

  public Long getWorkspaceId() {
    return workspaceId;
  }

  public void setWorkspaceId(Long workspaceId) {
    this.workspaceId = workspaceId;
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
   * Internal description for your reference
   */
  @JsonProperty("description")
  public String description;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Remote server type.  Remote Server Credentials are only valid for a single type of Remote Server.
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
   * AWS IAM Role ARN for AssumeRole authentication.
   */
  @JsonProperty("s3_assume_role_arn")
  public String s3AssumeRoleArn;

  public String getS3AssumeRoleArn() {
    return s3AssumeRoleArn;
  }

  public void setS3AssumeRoleArn(String s3AssumeRoleArn) {
    this.s3AssumeRoleArn = s3AssumeRoleArn;
  }

  /**
   * Session duration in seconds for AssumeRole authentication (900-43200).
   */
  @JsonProperty("s3_assume_role_duration_seconds")
  public Long s3AssumeRoleDurationSeconds;

  public Long getS3AssumeRoleDurationSeconds() {
    return s3AssumeRoleDurationSeconds;
  }

  public void setS3AssumeRoleDurationSeconds(Long s3AssumeRoleDurationSeconds) {
    this.s3AssumeRoleDurationSeconds = s3AssumeRoleDurationSeconds;
  }

  /**
   * External ID for AssumeRole authentication.
   */
  @JsonProperty("s3_assume_role_external_id")
  public String s3AssumeRoleExternalId;

  public String getS3AssumeRoleExternalId() {
    return s3AssumeRoleExternalId;
  }

  public void setS3AssumeRoleExternalId(String s3AssumeRoleExternalId) {
    this.s3AssumeRoleExternalId = s3AssumeRoleExternalId;
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
   * SharePoint: Microsoft Entra tenant ID for app-only authentication.
   */
  @JsonProperty("sharepoint_tenant_id")
  public String sharepointTenantId;

  public String getSharepointTenantId() {
    return sharepointTenantId;
  }

  public void setSharepointTenantId(String sharepointTenantId) {
    this.sharepointTenantId = sharepointTenantId;
  }

  /**
   * SharePoint: Microsoft Entra application client ID for app-only authentication.
   */
  @JsonProperty("sharepoint_client_id")
  public String sharepointClientId;

  public String getSharepointClientId() {
    return sharepointClientId;
  }

  public void setSharepointClientId(String sharepointClientId) {
    this.sharepointClientId = sharepointClientId;
  }

  /**
   * SharePoint: App-only credential type. Either secret or certificate.
   */
  @JsonProperty("sharepoint_app_credential_type")
  public String sharepointAppCredentialType;

  public String getSharepointAppCredentialType() {
    return sharepointAppCredentialType;
  }

  public void setSharepointAppCredentialType(String sharepointAppCredentialType) {
    this.sharepointAppCredentialType = sharepointAppCredentialType;
  }

  /**
   * Remote server username.
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
   * SharePoint: PEM-encoded certificate and unencrypted private key for app-only authentication.
   */
  @JsonProperty("sharepoint_client_certificate")
  public String sharepointClientCertificate;

  public String getSharepointClientCertificate() {
    return sharepointClientCertificate;
  }

  public void setSharepointClientCertificate(String sharepointClientCertificate) {
    this.sharepointClientCertificate = sharepointClientCertificate;
  }

  /**
   * SharePoint: Microsoft Entra application client secret for app-only authentication.
   */
  @JsonProperty("sharepoint_client_secret")
  public String sharepointClientSecret;

  public String getSharepointClientSecret() {
    return sharepointClientSecret;
  }

  public void setSharepointClientSecret(String sharepointClientSecret) {
    this.sharepointClientSecret = sharepointClientSecret;
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
   * ID of Remote Server Credential to copy omitted values from.
   */
  @JsonProperty("copy_values_from_credential_id")
  public Long copyValuesFromCredentialId;

  public Long getCopyValuesFromCredentialId() {
    return copyValuesFromCredentialId;
  }

  public void setCopyValuesFromCredentialId(Long copyValuesFromCredentialId) {
    this.copyValuesFromCredentialId = copyValuesFromCredentialId;
  }

  /**
   * Updates this RemoteServerCredential. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Parameters:
   *   name - string - Internal name for your reference
   *   description - string - Internal description for your reference
   *   server_type - string - Remote server type.  Remote Server Credentials are only valid for a single type of Remote Server.
   *   aws_access_key - string - AWS Access Key.
   *   s3_assume_role_arn - string - AWS IAM Role ARN for AssumeRole authentication.
   *   s3_assume_role_duration_seconds - int64 - Session duration in seconds for AssumeRole authentication (900-43200).
   *   cloudflare_access_key - string - Cloudflare: Access Key.
   *   filebase_access_key - string - Filebase: Access Key.
   *   google_cloud_storage_s3_compatible_access_key - string - Google Cloud Storage: S3-compatible Access Key.
   *   linode_access_key - string - Linode: Access Key
   *   s3_compatible_access_key - string - S3-compatible: Access Key
   *   sharepoint_client_id - string - SharePoint: Microsoft Entra application client ID for app-only authentication.
   *   sharepoint_tenant_id - string - SharePoint: Microsoft Entra tenant ID for app-only authentication.
   *   username - string - Remote server username.
   *   wasabi_access_key - string - Wasabi: Access Key.
   *   password - string - Password, if needed.
   *   private_key - string - Private key, if needed.
   *   private_key_passphrase - string - Passphrase for private key if needed.
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
   *   sharepoint_client_certificate - string - SharePoint: PEM-encoded certificate and unencrypted private key for app-only authentication.
   *   sharepoint_client_secret - string - SharePoint: Microsoft Entra application client secret for app-only authentication.
   *   wasabi_secret_key - string - Wasabi: Secret Key
   */
  public RemoteServerCredential update(HashMap<String, Object> parameters) throws IOException {
    RemoteServerCredential updated = filesClient().remoteServerCredentials().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().remoteServerCredentials().delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this RemoteServerCredential: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().remoteServerCredentials().create(parameters, this.options));
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
  private void refreshFrom(RemoteServerCredential canonical) {
    this.id = canonical.id;
    this.workspaceId = canonical.workspaceId;
    this.name = canonical.name;
    this.description = canonical.description;
    this.serverType = canonical.serverType;
    this.awsAccessKey = canonical.awsAccessKey;
    this.s3AssumeRoleArn = canonical.s3AssumeRoleArn;
    this.s3AssumeRoleDurationSeconds = canonical.s3AssumeRoleDurationSeconds;
    this.s3AssumeRoleExternalId = canonical.s3AssumeRoleExternalId;
    this.googleCloudStorageS3CompatibleAccessKey = canonical.googleCloudStorageS3CompatibleAccessKey;
    this.wasabiAccessKey = canonical.wasabiAccessKey;
    this.s3CompatibleAccessKey = canonical.s3CompatibleAccessKey;
    this.filebaseAccessKey = canonical.filebaseAccessKey;
    this.cloudflareAccessKey = canonical.cloudflareAccessKey;
    this.linodeAccessKey = canonical.linodeAccessKey;
    this.sharepointTenantId = canonical.sharepointTenantId;
    this.sharepointClientId = canonical.sharepointClientId;
    this.sharepointAppCredentialType = canonical.sharepointAppCredentialType;
    this.username = canonical.username;
    this.password = canonical.password;
    this.privateKey = canonical.privateKey;
    this.privateKeyPassphrase = canonical.privateKeyPassphrase;
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
    this.sharepointClientCertificate = canonical.sharepointClientCertificate;
    this.sharepointClientSecret = canonical.sharepointClientSecret;
    this.wasabiSecretKey = canonical.wasabiSecretKey;
    this.copyValuesFromCredentialId = canonical.copyValuesFromCredentialId;
  }

  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id` and `name`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id` and `name`. Valid field combinations are `[ workspace_id, name ]`.
   *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `name`.
   */
  public static ListIterator<RemoteServerCredential> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<RemoteServerCredential> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<RemoteServerCredential> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().remoteServerCredentials().list(parameters, options);
  }

  public static ListIterator<RemoteServerCredential> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<RemoteServerCredential> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Remote Server Credential ID.
   */
  public static RemoteServerCredential find() throws RuntimeException {
    return find(null, null, null);
  }

  public static RemoteServerCredential find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static RemoteServerCredential find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static RemoteServerCredential find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().remoteServerCredentials().find(id, parameters, options);
  }

  public static RemoteServerCredential get() throws RuntimeException {
    return get(null, null, null);
  }

  public static RemoteServerCredential get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   name - string - Internal name for your reference
   *   description - string - Internal description for your reference
   *   server_type - string - Remote server type.  Remote Server Credentials are only valid for a single type of Remote Server.
   *   aws_access_key - string - AWS Access Key.
   *   s3_assume_role_arn - string - AWS IAM Role ARN for AssumeRole authentication.
   *   s3_assume_role_duration_seconds - int64 - Session duration in seconds for AssumeRole authentication (900-43200).
   *   cloudflare_access_key - string - Cloudflare: Access Key.
   *   filebase_access_key - string - Filebase: Access Key.
   *   google_cloud_storage_s3_compatible_access_key - string - Google Cloud Storage: S3-compatible Access Key.
   *   linode_access_key - string - Linode: Access Key
   *   s3_compatible_access_key - string - S3-compatible: Access Key
   *   sharepoint_client_id - string - SharePoint: Microsoft Entra application client ID for app-only authentication.
   *   sharepoint_tenant_id - string - SharePoint: Microsoft Entra tenant ID for app-only authentication.
   *   username - string - Remote server username.
   *   wasabi_access_key - string - Wasabi: Access Key.
   *   password - string - Password, if needed.
   *   private_key - string - Private key, if needed.
   *   private_key_passphrase - string - Passphrase for private key if needed.
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
   *   sharepoint_client_certificate - string - SharePoint: PEM-encoded certificate and unencrypted private key for app-only authentication.
   *   sharepoint_client_secret - string - SharePoint: Microsoft Entra application client secret for app-only authentication.
   *   wasabi_secret_key - string - Wasabi: Secret Key
   *   workspace_id - int64 - Workspace ID (0 for default workspace)
   *   copy_values_from_credential_id - int64 - ID of Remote Server Credential to copy omitted values from.
   */
  public static RemoteServerCredential create() throws RuntimeException {
    return create(null, null);
  }

  public static RemoteServerCredential create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static RemoteServerCredential create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().remoteServerCredentials().create(parameters, options);
  }


  /**
   * Parameters:
   *   name - string - Internal name for your reference
   *   description - string - Internal description for your reference
   *   server_type - string - Remote server type.  Remote Server Credentials are only valid for a single type of Remote Server.
   *   aws_access_key - string - AWS Access Key.
   *   s3_assume_role_arn - string - AWS IAM Role ARN for AssumeRole authentication.
   *   s3_assume_role_duration_seconds - int64 - Session duration in seconds for AssumeRole authentication (900-43200).
   *   cloudflare_access_key - string - Cloudflare: Access Key.
   *   filebase_access_key - string - Filebase: Access Key.
   *   google_cloud_storage_s3_compatible_access_key - string - Google Cloud Storage: S3-compatible Access Key.
   *   linode_access_key - string - Linode: Access Key
   *   s3_compatible_access_key - string - S3-compatible: Access Key
   *   sharepoint_client_id - string - SharePoint: Microsoft Entra application client ID for app-only authentication.
   *   sharepoint_tenant_id - string - SharePoint: Microsoft Entra tenant ID for app-only authentication.
   *   username - string - Remote server username.
   *   wasabi_access_key - string - Wasabi: Access Key.
   *   password - string - Password, if needed.
   *   private_key - string - Private key, if needed.
   *   private_key_passphrase - string - Passphrase for private key if needed.
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
   *   sharepoint_client_certificate - string - SharePoint: PEM-encoded certificate and unencrypted private key for app-only authentication.
   *   sharepoint_client_secret - string - SharePoint: Microsoft Entra application client secret for app-only authentication.
   *   wasabi_secret_key - string - Wasabi: Secret Key
   */
  public static RemoteServerCredential update() throws RuntimeException {
    return update(null, null, null);
  }

  public static RemoteServerCredential update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static RemoteServerCredential update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static RemoteServerCredential update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().remoteServerCredentials().update(id, parameters, options);
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
    FilesClient.defaultClient().remoteServerCredentials().delete(id, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
