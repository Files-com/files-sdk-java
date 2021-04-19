package com.files.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class RemoteServer {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

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
  private Long id;

  /**
  * Type of authentication method
  */
  @Getter
  @Setter
  @JsonProperty("authentication_method")
  private String authenticationMethod;

  /**
  * Hostname or IP address
  */
  @Getter
  @Setter
  @JsonProperty("hostname")
  private String hostname;

  /**
  * Initial home folder on remote server
  */
  @Getter
  @Setter
  @JsonProperty("remote_home_path")
  private String remoteHomePath;

  /**
  * Internal name for your reference
  */
  @Getter
  @Setter
  @JsonProperty("name")
  private String name;

  /**
  * Port for remote server.  Not needed for S3.
  */
  @Getter
  @Setter
  @JsonProperty("port")
  private Long port;

  /**
  * Max number of parallel connections.  Ignored for S3 connections (we will parallelize these as much as possible).
  */
  @Getter
  @Setter
  @JsonProperty("max_connections")
  private Long maxConnections;

  /**
  * S3 bucket name
  */
  @Getter
  @Setter
  @JsonProperty("s3_bucket")
  private String s3Bucket;

  /**
  * S3 region
  */
  @Getter
  @Setter
  @JsonProperty("s3_region")
  private String s3Region;

  /**
  * Remote server certificate
  */
  @Getter
  @Setter
  @JsonProperty("server_certificate")
  private String serverCertificate;

  /**
  * Remote server SSH Host Key. If provided, we will require that the server host key matches the provided key. Uses OpenSSH format similar to what would go into ~/.ssh/known_hosts
  */
  @Getter
  @Setter
  @JsonProperty("server_host_key")
  private String serverHostKey;

  /**
  * Remote server type.
  */
  @Getter
  @Setter
  @JsonProperty("server_type")
  private String serverType;

  /**
  * Should we require SSL?
  */
  @Getter
  @Setter
  @JsonProperty("ssl")
  private String ssl;

  /**
  * Remote server username.  Not needed for S3 buckets.
  */
  @Getter
  @Setter
  @JsonProperty("username")
  private String username;

  /**
  * Google Cloud Storage bucket name
  */
  @Getter
  @Setter
  @JsonProperty("google_cloud_storage_bucket")
  private String googleCloudStorageBucket;

  /**
  * Google Cloud Project ID
  */
  @Getter
  @Setter
  @JsonProperty("google_cloud_storage_project_id")
  private String googleCloudStorageProjectId;

  /**
  * Backblaze B2 Cloud Storage S3 Endpoint
  */
  @Getter
  @Setter
  @JsonProperty("backblaze_b2_s3_endpoint")
  private String backblazeB2S3Endpoint;

  /**
  * Backblaze B2 Cloud Storage Bucket name
  */
  @Getter
  @Setter
  @JsonProperty("backblaze_b2_bucket")
  private String backblazeB2Bucket;

  /**
  * Wasabi region
  */
  @Getter
  @Setter
  @JsonProperty("wasabi_bucket")
  private String wasabiBucket;

  /**
  * Wasabi Bucket name
  */
  @Getter
  @Setter
  @JsonProperty("wasabi_region")
  private String wasabiRegion;

  /**
  * Rackspace username used to login to the Rackspace Cloud Control Panel.
  */
  @Getter
  @Setter
  @JsonProperty("rackspace_username")
  private String rackspaceUsername;

  /**
  * Three letter airport code for Rackspace region. See https://support.rackspace.com/how-to/about-regions/
  */
  @Getter
  @Setter
  @JsonProperty("rackspace_region")
  private String rackspaceRegion;

  /**
  * The name of the container (top level directory) where files will sync.
  */
  @Getter
  @Setter
  @JsonProperty("rackspace_container")
  private String rackspaceContainer;

  /**
  * Returns link to login with an Oauth provider
  */
  @Getter
  @Setter
  @JsonProperty("auth_setup_link")
  private String authSetupLink;

  /**
  * Either `in_setup` or `complete`
  */
  @Getter
  @Setter
  @JsonProperty("auth_status")
  private String authStatus;

  /**
  * Describes the authorized account
  */
  @Getter
  @Setter
  @JsonProperty("auth_account_name")
  private String authAccountName;

  /**
  * Either personal or business_other account types
  */
  @Getter
  @Setter
  @JsonProperty("one_drive_account_type")
  private String oneDriveAccountType;

  /**
  * Cloud hosted sharepoint hostname
  */
  @Getter
  @Setter
  @JsonProperty("sharepoint_hostname")
  private String sharepointHostname;

  /**
  * Azure Blob Storage Account name
  */
  @Getter
  @Setter
  @JsonProperty("azure_blob_storage_account")
  private String azureBlobStorageAccount;

  /**
  * Azure Blob Storage Container name
  */
  @Getter
  @Setter
  @JsonProperty("azure_blob_storage_container")
  private String azureBlobStorageContainer;

  /**
  * AWS Access Key.
  */
  @Getter
  @Setter
  @JsonProperty("aws_access_key")
  private String awsAccessKey;

  /**
  * AWS secret key.
  */
  @Getter
  @Setter
  @JsonProperty("aws_secret_key")
  private String awsSecretKey;

  /**
  * Password if needed.
  */
  @Getter
  @Setter
  @JsonProperty("password")
  private String password;

  /**
  * Private key if needed.
  */
  @Getter
  @Setter
  @JsonProperty("private_key")
  private String privateKey;

  /**
  * SSL client certificate.
  */
  @Getter
  @Setter
  @JsonProperty("ssl_certificate")
  private String sslCertificate;

  /**
  * A JSON file that contains the private key. To generate see https://cloud.google.com/storage/docs/json_api/v1/how-tos/authorizing#APIKey
  */
  @Getter
  @Setter
  @JsonProperty("google_cloud_storage_credentials_json")
  private String googleCloudStorageCredentialsJson;

  /**
  * Wasabi access key.
  */
  @Getter
  @Setter
  @JsonProperty("wasabi_access_key")
  private String wasabiAccessKey;

  /**
  * Wasabi secret key.
  */
  @Getter
  @Setter
  @JsonProperty("wasabi_secret_key")
  private String wasabiSecretKey;

  /**
  * Backblaze B2 Cloud Storage keyID.
  */
  @Getter
  @Setter
  @JsonProperty("backblaze_b2_key_id")
  private String backblazeB2KeyId;

  /**
  * Backblaze B2 Cloud Storage applicationKey.
  */
  @Getter
  @Setter
  @JsonProperty("backblaze_b2_application_key")
  private String backblazeB2ApplicationKey;

  /**
  * Rackspace API key from the Rackspace Cloud Control Panel.
  */
  @Getter
  @Setter
  @JsonProperty("rackspace_api_key")
  private String rackspaceApiKey;

  /**
  * Reset authenticated account
  */
  @Getter
  @Setter
  @JsonProperty("reset_authentication")
  private Boolean resetAuthentication;

  /**
  * Azure Blob Storage secret key.
  */
  @Getter
  @Setter
  @JsonProperty("azure_blob_storage_access_key")
  private String azureBlobStorageAccessKey;

  /**
  * Parameters:
  *   aws_access_key - string - AWS Access Key.
  *   aws_secret_key - string - AWS secret key.
  *   password - string - Password if needed.
  *   private_key - string - Private key if needed.
  *   ssl_certificate - string - SSL client certificate.
  *   google_cloud_storage_credentials_json - string - A JSON file that contains the private key. To generate see https://cloud.google.com/storage/docs/json_api/v1/how-tos/authorizing#APIKey
  *   wasabi_access_key - string - Wasabi access key.
  *   wasabi_secret_key - string - Wasabi secret key.
  *   backblaze_b2_key_id - string - Backblaze B2 Cloud Storage keyID.
  *   backblaze_b2_application_key - string - Backblaze B2 Cloud Storage applicationKey.
  *   rackspace_api_key - string - Rackspace API key from the Rackspace Cloud Control Panel.
  *   reset_authentication - boolean - Reset authenticated account
  *   azure_blob_storage_access_key - string - Azure Blob Storage secret key.
  *   hostname - string - Hostname or IP address
  *   name - string - Internal name for your reference
  *   max_connections - int64 - Max number of parallel connections.  Ignored for S3 connections (we will parallelize these as much as possible).
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
  *   wasabi_bucket - string - Wasabi region
  *   wasabi_region - string - Wasabi Bucket name
  *   rackspace_username - string - Rackspace username used to login to the Rackspace Cloud Control Panel.
  *   rackspace_region - string - Three letter airport code for Rackspace region. See https://support.rackspace.com/how-to/about-regions/
  *   rackspace_container - string - The name of the container (top level directory) where files will sync.
  *   one_drive_account_type - string - Either personal or business_other account types
  *   sharepoint_hostname - string - Cloud hosted sharepoint hostname
  *   azure_blob_storage_account - string - Azure Blob Storage Account name
  *   azure_blob_storage_container - string - Azure Blob Storage Container name
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
  *   cursor - string - Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  */
  public static List<RemoteServer> list() throws IOException{
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
  public static List<RemoteServer> find() throws IOException{
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

    if (id != null){
      parameters.put("id",id);
    }
    if (parameters.containsKey("id") && !(parameters.get("id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    String url = String.format("%s%s/remote_servers/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
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
  *   aws_access_key - string - AWS Access Key.
  *   aws_secret_key - string - AWS secret key.
  *   password - string - Password if needed.
  *   private_key - string - Private key if needed.
  *   ssl_certificate - string - SSL client certificate.
  *   google_cloud_storage_credentials_json - string - A JSON file that contains the private key. To generate see https://cloud.google.com/storage/docs/json_api/v1/how-tos/authorizing#APIKey
  *   wasabi_access_key - string - Wasabi access key.
  *   wasabi_secret_key - string - Wasabi secret key.
  *   backblaze_b2_key_id - string - Backblaze B2 Cloud Storage keyID.
  *   backblaze_b2_application_key - string - Backblaze B2 Cloud Storage applicationKey.
  *   rackspace_api_key - string - Rackspace API key from the Rackspace Cloud Control Panel.
  *   reset_authentication - boolean - Reset authenticated account
  *   azure_blob_storage_access_key - string - Azure Blob Storage secret key.
  *   hostname - string - Hostname or IP address
  *   name - string - Internal name for your reference
  *   max_connections - int64 - Max number of parallel connections.  Ignored for S3 connections (we will parallelize these as much as possible).
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
  *   wasabi_bucket - string - Wasabi region
  *   wasabi_region - string - Wasabi Bucket name
  *   rackspace_username - string - Rackspace username used to login to the Rackspace Cloud Control Panel.
  *   rackspace_region - string - Three letter airport code for Rackspace region. See https://support.rackspace.com/how-to/about-regions/
  *   rackspace_container - string - The name of the container (top level directory) where files will sync.
  *   one_drive_account_type - string - Either personal or business_other account types
  *   sharepoint_hostname - string - Cloud hosted sharepoint hostname
  *   azure_blob_storage_account - string - Azure Blob Storage Account name
  *   azure_blob_storage_container - string - Azure Blob Storage Container name
  */
  public static RemoteServer create() throws IOException{
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

    if (parameters.containsKey("hostname") && !(parameters.get("hostname") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: hostname must be of type String parameters[\"hostname\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("max_connections") && !(parameters.get("max_connections") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: max_connections must be of type Long parameters[\"max_connections\"]");
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

    if (parameters.containsKey("sharepoint_hostname") && !(parameters.get("sharepoint_hostname") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: sharepoint_hostname must be of type String parameters[\"sharepoint_hostname\"]");
    }

    if (parameters.containsKey("azure_blob_storage_account") && !(parameters.get("azure_blob_storage_account") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: azure_blob_storage_account must be of type String parameters[\"azure_blob_storage_account\"]");
    }

    if (parameters.containsKey("azure_blob_storage_container") && !(parameters.get("azure_blob_storage_container") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: azure_blob_storage_container must be of type String parameters[\"azure_blob_storage_container\"]");
    }

    String url = String.format("%s%s/remote_servers", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<RemoteServer> typeReference = new TypeReference<RemoteServer>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   aws_access_key - string - AWS Access Key.
  *   aws_secret_key - string - AWS secret key.
  *   password - string - Password if needed.
  *   private_key - string - Private key if needed.
  *   ssl_certificate - string - SSL client certificate.
  *   google_cloud_storage_credentials_json - string - A JSON file that contains the private key. To generate see https://cloud.google.com/storage/docs/json_api/v1/how-tos/authorizing#APIKey
  *   wasabi_access_key - string - Wasabi access key.
  *   wasabi_secret_key - string - Wasabi secret key.
  *   backblaze_b2_key_id - string - Backblaze B2 Cloud Storage keyID.
  *   backblaze_b2_application_key - string - Backblaze B2 Cloud Storage applicationKey.
  *   rackspace_api_key - string - Rackspace API key from the Rackspace Cloud Control Panel.
  *   reset_authentication - boolean - Reset authenticated account
  *   azure_blob_storage_access_key - string - Azure Blob Storage secret key.
  *   hostname - string - Hostname or IP address
  *   name - string - Internal name for your reference
  *   max_connections - int64 - Max number of parallel connections.  Ignored for S3 connections (we will parallelize these as much as possible).
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
  *   wasabi_bucket - string - Wasabi region
  *   wasabi_region - string - Wasabi Bucket name
  *   rackspace_username - string - Rackspace username used to login to the Rackspace Cloud Control Panel.
  *   rackspace_region - string - Three letter airport code for Rackspace region. See https://support.rackspace.com/how-to/about-regions/
  *   rackspace_container - string - The name of the container (top level directory) where files will sync.
  *   one_drive_account_type - string - Either personal or business_other account types
  *   sharepoint_hostname - string - Cloud hosted sharepoint hostname
  *   azure_blob_storage_account - string - Azure Blob Storage Account name
  *   azure_blob_storage_container - string - Azure Blob Storage Container name
  */
  public static RemoteServer update() throws IOException{
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

    if (id != null){
      parameters.put("id",id);
    }
    if (parameters.containsKey("id") && !(parameters.get("id") instanceof Long )) {
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

    if (parameters.containsKey("hostname") && !(parameters.get("hostname") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: hostname must be of type String parameters[\"hostname\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("max_connections") && !(parameters.get("max_connections") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: max_connections must be of type Long parameters[\"max_connections\"]");
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

    if (parameters.containsKey("sharepoint_hostname") && !(parameters.get("sharepoint_hostname") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: sharepoint_hostname must be of type String parameters[\"sharepoint_hostname\"]");
    }

    if (parameters.containsKey("azure_blob_storage_account") && !(parameters.get("azure_blob_storage_account") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: azure_blob_storage_account must be of type String parameters[\"azure_blob_storage_account\"]");
    }

    if (parameters.containsKey("azure_blob_storage_container") && !(parameters.get("azure_blob_storage_container") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: azure_blob_storage_container must be of type String parameters[\"azure_blob_storage_container\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    String url = String.format("%s%s/remote_servers/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<RemoteServer> typeReference = new TypeReference<RemoteServer>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  */
  public static RemoteServer delete() throws IOException{
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

    if (id != null){
      parameters.put("id",id);
    }
    if (parameters.containsKey("id") && !(parameters.get("id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    String url = String.format("%s%s/remote_servers/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
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


