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
public class RemoteServerCredential implements ModelInterface {
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


  public RemoteServerCredential() {
    this(null, null);
  }

  public RemoteServerCredential(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public RemoteServerCredential(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
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
  * Parameters:
  *   workspace_id - int64 - Workspace ID (0 for default workspace)
  *   name - string - Internal name for your reference
  *   description - string - Internal description for your reference
  *   server_type - string - Remote server type.  Remote Server Credentials are only valid for a single type of Remote Server.
  *   aws_access_key - string - AWS Access Key.
  *   azure_blob_storage_account - string - Azure Blob Storage: Account name
  *   azure_files_storage_account - string - Azure Files: Storage Account name
  *   cloudflare_access_key - string - Cloudflare: Access Key.
  *   filebase_access_key - string - Filebase: Access Key.
  *   google_cloud_storage_s3_compatible_access_key - string - Google Cloud Storage: S3-compatible Access Key.
  *   linode_access_key - string - Linode: Access Key
  *   s3_compatible_access_key - string - S3-compatible: Access Key
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
  *   wasabi_secret_key - string - Wasabi: Secret Key
  */
  public RemoteServerCredential update(HashMap<String, Object> parameters) throws IOException {
    return RemoteServerCredential.update(this.id, parameters, this.options);
  }

  /**
  */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    RemoteServerCredential.delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    RemoteServerCredential.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id` and `id`.
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


    String url = String.format("%s%s/remote_server_credentials", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<RemoteServerCredential>> typeReference = new TypeReference<List<RemoteServerCredential>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
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



    String url = String.format("%s%s/remote_server_credentials/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<RemoteServerCredential> typeReference = new TypeReference<RemoteServerCredential>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static RemoteServerCredential get() throws RuntimeException {
    return get(null, null, null);
  }

  public static RemoteServerCredential get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   workspace_id - int64 - Workspace ID (0 for default workspace)
  *   name - string - Internal name for your reference
  *   description - string - Internal description for your reference
  *   server_type - string - Remote server type.  Remote Server Credentials are only valid for a single type of Remote Server.
  *   aws_access_key - string - AWS Access Key.
  *   azure_blob_storage_account - string - Azure Blob Storage: Account name
  *   azure_files_storage_account - string - Azure Files: Storage Account name
  *   cloudflare_access_key - string - Cloudflare: Access Key.
  *   filebase_access_key - string - Filebase: Access Key.
  *   google_cloud_storage_s3_compatible_access_key - string - Google Cloud Storage: S3-compatible Access Key.
  *   linode_access_key - string - Linode: Access Key
  *   s3_compatible_access_key - string - S3-compatible: Access Key
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
  *   wasabi_secret_key - string - Wasabi: Secret Key
  */
  public static RemoteServerCredential create() throws RuntimeException {
    return create(null, null);
  }

  public static RemoteServerCredential create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static RemoteServerCredential create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }
    if (parameters.containsKey("server_type") && !(parameters.get("server_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: server_type must be of type String parameters[\"server_type\"]");
    }
    if (parameters.containsKey("aws_access_key") && !(parameters.get("aws_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: aws_access_key must be of type String parameters[\"aws_access_key\"]");
    }
    if (parameters.containsKey("azure_blob_storage_account") && !(parameters.get("azure_blob_storage_account") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_blob_storage_account must be of type String parameters[\"azure_blob_storage_account\"]");
    }
    if (parameters.containsKey("azure_files_storage_account") && !(parameters.get("azure_files_storage_account") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_files_storage_account must be of type String parameters[\"azure_files_storage_account\"]");
    }
    if (parameters.containsKey("cloudflare_access_key") && !(parameters.get("cloudflare_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cloudflare_access_key must be of type String parameters[\"cloudflare_access_key\"]");
    }
    if (parameters.containsKey("filebase_access_key") && !(parameters.get("filebase_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: filebase_access_key must be of type String parameters[\"filebase_access_key\"]");
    }
    if (parameters.containsKey("google_cloud_storage_s3_compatible_access_key") && !(parameters.get("google_cloud_storage_s3_compatible_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: google_cloud_storage_s3_compatible_access_key must be of type String parameters[\"google_cloud_storage_s3_compatible_access_key\"]");
    }
    if (parameters.containsKey("linode_access_key") && !(parameters.get("linode_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: linode_access_key must be of type String parameters[\"linode_access_key\"]");
    }
    if (parameters.containsKey("s3_compatible_access_key") && !(parameters.get("s3_compatible_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: s3_compatible_access_key must be of type String parameters[\"s3_compatible_access_key\"]");
    }
    if (parameters.containsKey("username") && !(parameters.get("username") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: username must be of type String parameters[\"username\"]");
    }
    if (parameters.containsKey("wasabi_access_key") && !(parameters.get("wasabi_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: wasabi_access_key must be of type String parameters[\"wasabi_access_key\"]");
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


    String url = String.format("%s%s/remote_server_credentials", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<RemoteServerCredential> typeReference = new TypeReference<RemoteServerCredential>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   workspace_id - int64 - Workspace ID (0 for default workspace)
  *   name - string - Internal name for your reference
  *   description - string - Internal description for your reference
  *   server_type - string - Remote server type.  Remote Server Credentials are only valid for a single type of Remote Server.
  *   aws_access_key - string - AWS Access Key.
  *   azure_blob_storage_account - string - Azure Blob Storage: Account name
  *   azure_files_storage_account - string - Azure Files: Storage Account name
  *   cloudflare_access_key - string - Cloudflare: Access Key.
  *   filebase_access_key - string - Filebase: Access Key.
  *   google_cloud_storage_s3_compatible_access_key - string - Google Cloud Storage: S3-compatible Access Key.
  *   linode_access_key - string - Linode: Access Key
  *   s3_compatible_access_key - string - S3-compatible: Access Key
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
    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }
    if (parameters.containsKey("server_type") && !(parameters.get("server_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: server_type must be of type String parameters[\"server_type\"]");
    }
    if (parameters.containsKey("aws_access_key") && !(parameters.get("aws_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: aws_access_key must be of type String parameters[\"aws_access_key\"]");
    }
    if (parameters.containsKey("azure_blob_storage_account") && !(parameters.get("azure_blob_storage_account") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_blob_storage_account must be of type String parameters[\"azure_blob_storage_account\"]");
    }
    if (parameters.containsKey("azure_files_storage_account") && !(parameters.get("azure_files_storage_account") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_files_storage_account must be of type String parameters[\"azure_files_storage_account\"]");
    }
    if (parameters.containsKey("cloudflare_access_key") && !(parameters.get("cloudflare_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cloudflare_access_key must be of type String parameters[\"cloudflare_access_key\"]");
    }
    if (parameters.containsKey("filebase_access_key") && !(parameters.get("filebase_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: filebase_access_key must be of type String parameters[\"filebase_access_key\"]");
    }
    if (parameters.containsKey("google_cloud_storage_s3_compatible_access_key") && !(parameters.get("google_cloud_storage_s3_compatible_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: google_cloud_storage_s3_compatible_access_key must be of type String parameters[\"google_cloud_storage_s3_compatible_access_key\"]");
    }
    if (parameters.containsKey("linode_access_key") && !(parameters.get("linode_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: linode_access_key must be of type String parameters[\"linode_access_key\"]");
    }
    if (parameters.containsKey("s3_compatible_access_key") && !(parameters.get("s3_compatible_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: s3_compatible_access_key must be of type String parameters[\"s3_compatible_access_key\"]");
    }
    if (parameters.containsKey("username") && !(parameters.get("username") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: username must be of type String parameters[\"username\"]");
    }
    if (parameters.containsKey("wasabi_access_key") && !(parameters.get("wasabi_access_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: wasabi_access_key must be of type String parameters[\"wasabi_access_key\"]");
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



    String url = String.format("%s%s/remote_server_credentials/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<RemoteServerCredential> typeReference = new TypeReference<RemoteServerCredential>() {};
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



    String url = String.format("%s%s/remote_server_credentials/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
