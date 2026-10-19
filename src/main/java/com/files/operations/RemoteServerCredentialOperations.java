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
   * List Remote Server Credentials
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<RemoteServerCredential> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Remote Server Credentials
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<RemoteServerCredential> list(HashMap<String, Object> parameters) throws RuntimeException {
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

  /**
   * List Remote Server Credentials
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<RemoteServerCredential> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Remote Server Credentials
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<RemoteServerCredential> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Remote Server Credential
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public RemoteServerCredential find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Remote Server Credential
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public RemoteServerCredential find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Remote Server Credential
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public RemoteServerCredential find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

  /**
   * Show Remote Server Credential
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public RemoteServerCredential get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Remote Server Credential
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public RemoteServerCredential get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Remote Server Credential
   *
   * @see #create(HashMap, HashMap)
   */
  public RemoteServerCredential create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Remote Server Credential
   *
   * @see #create(HashMap, HashMap)
   */
  public RemoteServerCredential create(HashMap<String, Object> parameters) throws RuntimeException {
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
   * Update Remote Server Credential
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public RemoteServerCredential update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Remote Server Credential
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public RemoteServerCredential update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Remote Server Credential
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public RemoteServerCredential update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
   * Delete Remote Server Credential
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Remote Server Credential
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Remote Server Credential
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Remote Server Credential
   *
   * @param id Remote Server Credential ID.
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

    String apiPath = String.format("/remote_server_credentials/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete Remote Server Credential
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Remote Server Credential
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
