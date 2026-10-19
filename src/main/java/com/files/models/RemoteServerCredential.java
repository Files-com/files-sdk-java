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

  // One mapper for every RemoteServerCredential, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code RemoteServerCredential} with no attributes set.
   */
  public RemoteServerCredential() {
    this(null, null);
  }

  /**
   * Creates a {@code RemoteServerCredential} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public RemoteServerCredential(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code RemoteServerCredential} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
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
   * Remote server type.  Remote Server Credentials are only valid for a single type of Remote Server.
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
   * ID of Remote Server Credential to copy omitted values from.
   */
  @JsonProperty("copy_values_from_credential_id")
  public Long copyValuesFromCredentialId;

  /**
   * Gets {@link #copyValuesFromCredentialId}.
   *
   * @return the value of {@link #copyValuesFromCredentialId}
   */
  public Long getCopyValuesFromCredentialId() {
    return copyValuesFromCredentialId;
  }

  /**
   * Sets {@link #copyValuesFromCredentialId}.
   *
   * @param copyValuesFromCredentialId the new value
   */
  public void setCopyValuesFromCredentialId(Long copyValuesFromCredentialId) {
    this.copyValuesFromCredentialId = copyValuesFromCredentialId;
  }

  /**
   * Updates this RemoteServerCredential. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update Remote Server Credential
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Internal name for your reference</li>
   * <li>{@code description} (String): Internal description for your reference</li>
   * <li>{@code server_type} (String): Remote server type.  Remote Server Credentials are only valid for a single type of Remote Server.</li>
   * <li>{@code aws_access_key} (String): AWS Access Key.</li>
   * <li>{@code s3_assume_role_arn} (String): AWS IAM Role ARN for AssumeRole authentication.</li>
   * <li>{@code s3_assume_role_duration_seconds} (Long): Session duration in seconds for AssumeRole authentication (900-43200).</li>
   * <li>{@code cloudflare_access_key} (String): Cloudflare: Access Key.</li>
   * <li>{@code filebase_access_key} (String): Filebase: Access Key.</li>
   * <li>{@code google_cloud_storage_s3_compatible_access_key} (String): Google Cloud Storage: S3-compatible Access Key.</li>
   * <li>{@code linode_access_key} (String): Linode: Access Key</li>
   * <li>{@code s3_compatible_access_key} (String): S3-compatible: Access Key</li>
   * <li>{@code sharepoint_client_id} (String): SharePoint: Microsoft Entra application client ID for app-only authentication.</li>
   * <li>{@code sharepoint_tenant_id} (String): SharePoint: Microsoft Entra tenant ID for app-only authentication.</li>
   * <li>{@code username} (String): Remote server username.</li>
   * <li>{@code wasabi_access_key} (String): Wasabi: Access Key.</li>
   * <li>{@code password} (String): Password, if needed.</li>
   * <li>{@code private_key} (String): Private key, if needed.</li>
   * <li>{@code private_key_passphrase} (String): Passphrase for private key if needed.</li>
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
   * <li>{@code sharepoint_client_certificate} (String): SharePoint: PEM-encoded certificate and unencrypted private key for app-only authentication.</li>
   * <li>{@code sharepoint_client_secret} (String): SharePoint: Microsoft Entra application client secret for app-only authentication.</li>
   * <li>{@code wasabi_secret_key} (String): Wasabi: Secret Key</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code RemoteServerCredential}
   */
  public RemoteServerCredential update(HashMap<String, Object> parameters) throws IOException {
    RemoteServerCredential updated = filesClient().remoteServerCredentials().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete Remote Server Credential
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().remoteServerCredentials().delete(this.id, parameters, this.options);
  }

  /**
   * Delete Remote Server Credential
   *
   * @see #delete(HashMap)
   */
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
   * List Remote Server Credentials
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<RemoteServerCredential> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Remote Server Credentials
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<RemoteServerCredential> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Remote Server Credentials
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id` and `name`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id` and `name`. Valid field combinations are `[ workspace_id, name ]`.</li>
   * <li>{@code filter_prefix} (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `name`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code RemoteServerCredential} records, in a {@code ListIterator}
   */
  public static ListIterator<RemoteServerCredential> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().remoteServerCredentials().list(parameters, options);
  }

  /**
   * List Remote Server Credentials
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<RemoteServerCredential> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Remote Server Credentials
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<RemoteServerCredential> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Remote Server Credential
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static RemoteServerCredential find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Remote Server Credential
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static RemoteServerCredential find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Remote Server Credential
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static RemoteServerCredential find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Remote Server Credential
   *
   * @param id Remote Server Credential ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code RemoteServerCredential}
   */
  public static RemoteServerCredential find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().remoteServerCredentials().find(id, parameters, options);
  }

  /**
   * Show Remote Server Credential
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static RemoteServerCredential get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Remote Server Credential
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static RemoteServerCredential get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Remote Server Credential
   *
   * @see #create(HashMap, HashMap)
   */
  public static RemoteServerCredential create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Remote Server Credential
   *
   * @see #create(HashMap, HashMap)
   */
  public static RemoteServerCredential create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create Remote Server Credential
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Internal name for your reference</li>
   * <li>{@code description} (String): Internal description for your reference</li>
   * <li>{@code server_type} (String): Remote server type.  Remote Server Credentials are only valid for a single type of Remote Server.</li>
   * <li>{@code aws_access_key} (String): AWS Access Key.</li>
   * <li>{@code s3_assume_role_arn} (String): AWS IAM Role ARN for AssumeRole authentication.</li>
   * <li>{@code s3_assume_role_duration_seconds} (Long): Session duration in seconds for AssumeRole authentication (900-43200).</li>
   * <li>{@code cloudflare_access_key} (String): Cloudflare: Access Key.</li>
   * <li>{@code filebase_access_key} (String): Filebase: Access Key.</li>
   * <li>{@code google_cloud_storage_s3_compatible_access_key} (String): Google Cloud Storage: S3-compatible Access Key.</li>
   * <li>{@code linode_access_key} (String): Linode: Access Key</li>
   * <li>{@code s3_compatible_access_key} (String): S3-compatible: Access Key</li>
   * <li>{@code sharepoint_client_id} (String): SharePoint: Microsoft Entra application client ID for app-only authentication.</li>
   * <li>{@code sharepoint_tenant_id} (String): SharePoint: Microsoft Entra tenant ID for app-only authentication.</li>
   * <li>{@code username} (String): Remote server username.</li>
   * <li>{@code wasabi_access_key} (String): Wasabi: Access Key.</li>
   * <li>{@code password} (String): Password, if needed.</li>
   * <li>{@code private_key} (String): Private key, if needed.</li>
   * <li>{@code private_key_passphrase} (String): Passphrase for private key if needed.</li>
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
   * <li>{@code sharepoint_client_certificate} (String): SharePoint: PEM-encoded certificate and unencrypted private key for app-only authentication.</li>
   * <li>{@code sharepoint_client_secret} (String): SharePoint: Microsoft Entra application client secret for app-only authentication.</li>
   * <li>{@code wasabi_secret_key} (String): Wasabi: Secret Key</li>
   * <li>{@code workspace_id} (Long): Workspace ID (0 for default workspace)</li>
   * <li>{@code copy_values_from_credential_id} (Long): ID of Remote Server Credential to copy omitted values from.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code RemoteServerCredential}
   */
  public static RemoteServerCredential create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().remoteServerCredentials().create(parameters, options);
  }


  /**
   * Update Remote Server Credential
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static RemoteServerCredential update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Remote Server Credential
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static RemoteServerCredential update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Remote Server Credential
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static RemoteServerCredential update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Remote Server Credential
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Internal name for your reference</li>
   * <li>{@code description} (String): Internal description for your reference</li>
   * <li>{@code server_type} (String): Remote server type.  Remote Server Credentials are only valid for a single type of Remote Server.</li>
   * <li>{@code aws_access_key} (String): AWS Access Key.</li>
   * <li>{@code s3_assume_role_arn} (String): AWS IAM Role ARN for AssumeRole authentication.</li>
   * <li>{@code s3_assume_role_duration_seconds} (Long): Session duration in seconds for AssumeRole authentication (900-43200).</li>
   * <li>{@code cloudflare_access_key} (String): Cloudflare: Access Key.</li>
   * <li>{@code filebase_access_key} (String): Filebase: Access Key.</li>
   * <li>{@code google_cloud_storage_s3_compatible_access_key} (String): Google Cloud Storage: S3-compatible Access Key.</li>
   * <li>{@code linode_access_key} (String): Linode: Access Key</li>
   * <li>{@code s3_compatible_access_key} (String): S3-compatible: Access Key</li>
   * <li>{@code sharepoint_client_id} (String): SharePoint: Microsoft Entra application client ID for app-only authentication.</li>
   * <li>{@code sharepoint_tenant_id} (String): SharePoint: Microsoft Entra tenant ID for app-only authentication.</li>
   * <li>{@code username} (String): Remote server username.</li>
   * <li>{@code wasabi_access_key} (String): Wasabi: Access Key.</li>
   * <li>{@code password} (String): Password, if needed.</li>
   * <li>{@code private_key} (String): Private key, if needed.</li>
   * <li>{@code private_key_passphrase} (String): Passphrase for private key if needed.</li>
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
   * <li>{@code sharepoint_client_certificate} (String): SharePoint: PEM-encoded certificate and unencrypted private key for app-only authentication.</li>
   * <li>{@code sharepoint_client_secret} (String): SharePoint: Microsoft Entra application client secret for app-only authentication.</li>
   * <li>{@code wasabi_secret_key} (String): Wasabi: Secret Key</li>
   * </ul>
   *
   * @param id Remote Server Credential ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code RemoteServerCredential}
   */
  public static RemoteServerCredential update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().remoteServerCredentials().update(id, parameters, options);
  }


  /**
   * Delete Remote Server Credential
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Remote Server Credential
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Remote Server Credential
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Remote Server Credential
   *
   * @param id Remote Server Credential ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().remoteServerCredentials().delete(id, parameters, options);
  }

  /**
   * Delete Remote Server Credential
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Remote Server Credential
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
