package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.RemoteServerCredential;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * RemoteServerCredential operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#remoteServerCredentials()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.RemoteServerCredential} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class RemoteServerCredentialOperations extends ResourceOperations {
  public RemoteServerCredentialOperations(FilesClient client) {
    super(client);
  }

  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id` and `name`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id` and `name`. Valid field combinations are `[ workspace_id, name ]`.
   *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `name`.
   */
  public ListIterator<RemoteServerCredential> list() throws RuntimeException {
    return list(null, null);
  }

  public ListIterator<RemoteServerCredential> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  public ListIterator<RemoteServerCredential> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

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

    String apiPath = "/remote_server_credentials";
    TypeReference<List<RemoteServerCredential>> typeReference = new TypeReference<List<RemoteServerCredential>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public ListIterator<RemoteServerCredential> all() throws RuntimeException {
    return all(null, null);
  }

  public ListIterator<RemoteServerCredential> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Remote Server Credential ID.
   */
  public RemoteServerCredential find() throws RuntimeException {
    return find(null, null, null);
  }

  public RemoteServerCredential find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public RemoteServerCredential find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public RemoteServerCredential find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/remote_server_credentials/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<RemoteServerCredential> typeReference = new TypeReference<RemoteServerCredential>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public RemoteServerCredential get() throws RuntimeException {
    return get(null, null, null);
  }

  public RemoteServerCredential get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public RemoteServerCredential create() throws RuntimeException {
    return create(null, null);
  }

  public RemoteServerCredential create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  public RemoteServerCredential create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

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

    if (parameters.containsKey("s3_assume_role_arn") && !(parameters.get("s3_assume_role_arn") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: s3_assume_role_arn must be of type String parameters[\"s3_assume_role_arn\"]");
    }

    if (parameters.containsKey("s3_assume_role_duration_seconds") && !(parameters.get("s3_assume_role_duration_seconds") instanceof Long || parameters.get("s3_assume_role_duration_seconds") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: s3_assume_role_duration_seconds must be of type Long or Integer parameters[\"s3_assume_role_duration_seconds\"]");
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

    if (parameters.containsKey("sharepoint_client_id") && !(parameters.get("sharepoint_client_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: sharepoint_client_id must be of type String parameters[\"sharepoint_client_id\"]");
    }

    if (parameters.containsKey("sharepoint_tenant_id") && !(parameters.get("sharepoint_tenant_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: sharepoint_tenant_id must be of type String parameters[\"sharepoint_tenant_id\"]");
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

    if (parameters.containsKey("sharepoint_client_certificate") && !(parameters.get("sharepoint_client_certificate") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: sharepoint_client_certificate must be of type String parameters[\"sharepoint_client_certificate\"]");
    }

    if (parameters.containsKey("sharepoint_client_secret") && !(parameters.get("sharepoint_client_secret") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: sharepoint_client_secret must be of type String parameters[\"sharepoint_client_secret\"]");
    }

    if (parameters.containsKey("wasabi_secret_key") && !(parameters.get("wasabi_secret_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: wasabi_secret_key must be of type String parameters[\"wasabi_secret_key\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    if (parameters.containsKey("copy_values_from_credential_id") && !(parameters.get("copy_values_from_credential_id") instanceof Long || parameters.get("copy_values_from_credential_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: copy_values_from_credential_id must be of type Long or Integer parameters[\"copy_values_from_credential_id\"]");
    }

    String apiPath = "/remote_server_credentials";
    TypeReference<RemoteServerCredential> typeReference = new TypeReference<RemoteServerCredential>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
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
  public RemoteServerCredential update() throws RuntimeException {
    return update(null, null, null);
  }

  public RemoteServerCredential update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public RemoteServerCredential update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public RemoteServerCredential update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("s3_assume_role_arn") && !(parameters.get("s3_assume_role_arn") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: s3_assume_role_arn must be of type String parameters[\"s3_assume_role_arn\"]");
    }

    if (parameters.containsKey("s3_assume_role_duration_seconds") && !(parameters.get("s3_assume_role_duration_seconds") instanceof Long || parameters.get("s3_assume_role_duration_seconds") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: s3_assume_role_duration_seconds must be of type Long or Integer parameters[\"s3_assume_role_duration_seconds\"]");
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

    if (parameters.containsKey("sharepoint_client_id") && !(parameters.get("sharepoint_client_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: sharepoint_client_id must be of type String parameters[\"sharepoint_client_id\"]");
    }

    if (parameters.containsKey("sharepoint_tenant_id") && !(parameters.get("sharepoint_tenant_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: sharepoint_tenant_id must be of type String parameters[\"sharepoint_tenant_id\"]");
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

    if (parameters.containsKey("sharepoint_client_certificate") && !(parameters.get("sharepoint_client_certificate") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: sharepoint_client_certificate must be of type String parameters[\"sharepoint_client_certificate\"]");
    }

    if (parameters.containsKey("sharepoint_client_secret") && !(parameters.get("sharepoint_client_secret") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: sharepoint_client_secret must be of type String parameters[\"sharepoint_client_secret\"]");
    }

    if (parameters.containsKey("wasabi_secret_key") && !(parameters.get("wasabi_secret_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: wasabi_secret_key must be of type String parameters[\"wasabi_secret_key\"]");
    }

    String apiPath = String.format("/remote_server_credentials/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<RemoteServerCredential> typeReference = new TypeReference<RemoteServerCredential>() {};
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

    String apiPath = String.format("/remote_server_credentials/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
