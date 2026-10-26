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
import java.util.Date;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiKey implements ModelInterface {
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

  // One mapper for every ApiKey, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code ApiKey} with no attributes set.
   */
  public ApiKey() {
    this(null, null);
  }

  /**
   * Creates a {@code ApiKey} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public ApiKey(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code ApiKey} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public ApiKey(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * API Key ID
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
   * Unique label that describes this API key.  Useful for external systems where you may have API keys from multiple accounts and want a human-readable label for each key.
   */
  @JsonProperty("descriptive_label")
  public String descriptiveLabel;

  /**
   * Gets {@link #descriptiveLabel}.
   *
   * @return the value of {@link #descriptiveLabel}
   */
  public String getDescriptiveLabel() {
    return descriptiveLabel;
  }

  /**
   * Sets {@link #descriptiveLabel}.
   *
   * @param descriptiveLabel the new value
   */
  public void setDescriptiveLabel(String descriptiveLabel) {
    this.descriptiveLabel = descriptiveLabel;
  }

  /**
   * User-supplied description of API key.
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
   * Time which API Key was created
   */
  @JsonProperty("created_at")
  public Date createdAt;

  /**
   * Gets {@link #createdAt}.
   *
   * @return the value of {@link #createdAt}
   */
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * API Key expiration date
   */
  @JsonProperty("expires_at")
  public Date expiresAt;

  /**
   * Gets {@link #expiresAt}.
   *
   * @return the value of {@link #expiresAt}
   */
  public Date getExpiresAt() {
    return expiresAt;
  }

  /**
   * Sets {@link #expiresAt}.
   *
   * @param expiresAt the new value
   */
  public void setExpiresAt(Date expiresAt) {
    this.expiresAt = expiresAt;
  }

  /**
   * API Key actual key string
   */
  @JsonProperty("key")
  public String key;

  /**
   * Gets {@link #key}.
   *
   * @return the value of {@link #key}
   */
  public String getKey() {
    return key;
  }

  /**
   * Sets {@link #key}.
   *
   * @param key the new value
   */
  public void setKey(String key) {
    this.key = key;
  }

  /**
   * If `true`, this API key will be usable with AWS-compatible endpoints, such as our Inbound S3-compatible endpoint.
   */
  @JsonProperty("aws_style_credentials")
  public Boolean awsStyleCredentials;

  /**
   * Gets {@link #awsStyleCredentials}.
   *
   * @return the value of {@link #awsStyleCredentials}
   */
  public Boolean getAwsStyleCredentials() {
    return awsStyleCredentials;
  }

  /**
   * Sets {@link #awsStyleCredentials}.
   *
   * @param awsStyleCredentials the new value
   */
  public void setAwsStyleCredentials(Boolean awsStyleCredentials) {
    this.awsStyleCredentials = awsStyleCredentials;
  }

  /**
   * AWS Access Key ID to use with AWS-compatible endpoints, such as our Inbound S3-compatible endpoint.
   */
  @JsonProperty("aws_access_key_id")
  public String awsAccessKeyId;

  /**
   * Gets {@link #awsAccessKeyId}.
   *
   * @return the value of {@link #awsAccessKeyId}
   */
  public String getAwsAccessKeyId() {
    return awsAccessKeyId;
  }

  /**
   * Sets {@link #awsAccessKeyId}.
   *
   * @param awsAccessKeyId the new value
   */
  public void setAwsAccessKeyId(String awsAccessKeyId) {
    this.awsAccessKeyId = awsAccessKeyId;
  }

  /**
   * AWS Secret Key to use with AWS-compatible endpoints, such as our Inbound S3-compatible endpoint.
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
   * API Key last used - note this value is only updated once per 3 hour period, so the 'actual' time of last use may be up to 3 hours later than this timestamp.
   */
  @JsonProperty("last_use_at")
  public Date lastUseAt;

  /**
   * Gets {@link #lastUseAt}.
   *
   * @return the value of {@link #lastUseAt}
   */
  public Date getLastUseAt() {
    return lastUseAt;
  }

  /**
   * Sets {@link #lastUseAt}.
   *
   * @param lastUseAt the new value
   */
  public void setLastUseAt(Date lastUseAt) {
    this.lastUseAt = lastUseAt;
  }

  /**
   * Internal name for the API Key.  For your use.
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
   * Permissions for this API Key. Keys with the `desktop_app` permission set only have the ability to do the functions provided in our Desktop App (File and Share Link operations). Keys with the `mobile_app` permission set can only perform the functions provided by our Mobile App (File and Share Link operations). Keys with the `office_integration` permission set are auto generated, and automatically expire, to allow users to interact with office integration platforms. Keys with the `files_only` permission set can use only the files, folders, and file_actions endpoints, excluding the GPG Encrypt and GPG Decrypt file actions, where they perform file operations as a full-access file user in the key's workspace scope, along with `GET /file_migrations/{id}` and `GET /api_key`. On the migration lookup, any key with a `path` can read only migrations it started. Without a `path`, user-owned keys retain user-scoped access; site-wide Workspace-bound keys can read only migrations whose source and destination are both in their Workspace. Every `files_only` key is Workspace-bound, including Workspace `0`, as is a full-access key in a named Workspace. A full-access site-wide key in the default Workspace retains Site-wide migration access. Migrations outside the caller's visibility return `not-found`. Keys with `files_only` cannot use site admin, workspace admin, folder admin, group admin, partner admin, or billing privileges from the owning user. Both GPG file actions and every other endpoint deny them with `not-authorized/api-key-only-for-file-operations`. Folder-configured GPG encryption and decryption still apply to files uploaded with a `files_only` key.
   */
  @JsonProperty("permission_set")
  public String permissionSet;

  /**
   * Gets {@link #permissionSet}.
   *
   * @return the value of {@link #permissionSet}
   */
  public String getPermissionSet() {
    return permissionSet;
  }

  /**
   * Sets {@link #permissionSet}.
   *
   * @param permissionSet the new value
   */
  public void setPermissionSet(String permissionSet) {
    this.permissionSet = permissionSet;
  }

  /**
   * If this API key represents a Desktop app, what platform was it created on?
   */
  @JsonProperty("platform")
  public String platform;

  /**
   * Gets {@link #platform}.
   *
   * @return the value of {@link #platform}
   */
  public String getPlatform() {
    return platform;
  }

  /**
   * Sets {@link #platform}.
   *
   * @param platform the new value
   */
  public void setPlatform(String platform) {
    this.platform = platform;
  }

  /**
   * Site ID
   */
  @JsonProperty("site_id")
  public Long siteId;

  /**
   * Gets {@link #siteId}.
   *
   * @return the value of {@link #siteId}
   */
  public Long getSiteId() {
    return siteId;
  }

  /**
   * Sets {@link #siteId}.
   *
   * @param siteId the new value
   */
  public void setSiteId(Long siteId) {
    this.siteId = siteId;
  }

  /**
   * Site Name
   */
  @JsonProperty("site_name")
  public String siteName;

  /**
   * Gets {@link #siteName}.
   *
   * @return the value of {@link #siteName}
   */
  public String getSiteName() {
    return siteName;
  }

  /**
   * Sets {@link #siteName}.
   *
   * @param siteName the new value
   */
  public void setSiteName(String siteName) {
    this.siteName = siteName;
  }

  /**
   * URL for API host.
   */
  @JsonProperty("url")
  public String url;

  /**
   * Gets {@link #url}.
   *
   * @return the value of {@link #url}
   */
  public String getUrl() {
    return url;
  }

  /**
   * Sets {@link #url}.
   *
   * @param url the new value
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * User ID for the owner of this API Key.  May be blank for Site-wide API Keys.
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
   * Workspace ID for this API Key. `0` means the default workspace.
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
   * Restricts the file and folder operations made with this key, meaning the files, folders, and file_actions endpoints, to the specified folder and its descendants, including copy and move destinations. For `GET /file_migrations/{id}`, a key with a path can read only migrations it started. Does not grant access beyond the owning user's permissions. Optional for `files_only` keys and required for `office_integration` keys, which need a path the owning user can read. Keys with any other permission set cannot have a path.
   */
  @JsonProperty("path")
  public String path;

  /**
   * Gets {@link #path}.
   *
   * @return the value of {@link #path}
   */
  public String getPath() {
    return path;
  }

  /**
   * Sets {@link #path}.
   *
   * @param path the new value
   */
  public void setPath(String path) {
    this.path = path;
  }

  /**
   * Updates this ApiKey. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update API Key
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code description} (String): User-supplied description of API key.</li>
   * <li>{@code expires_at} (String): API Key expiration date</li>
   * <li>{@code name} (String): Internal name for the API Key.  For your use.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code ApiKey}
   */
  public ApiKey update(HashMap<String, Object> parameters) throws IOException {
    ApiKey updated = filesClient().apiKeys().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete API Key
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().apiKeys().delete(this.id, parameters, this.options);
  }

  /**
   * Delete API Key
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this ApiKey: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().apiKeys().create(parameters, this.options));
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
  private void refreshFrom(ApiKey canonical) {
    this.id = canonical.id;
    this.descriptiveLabel = canonical.descriptiveLabel;
    this.description = canonical.description;
    this.createdAt = canonical.createdAt;
    this.expiresAt = canonical.expiresAt;
    this.key = canonical.key;
    this.awsStyleCredentials = canonical.awsStyleCredentials;
    this.awsAccessKeyId = canonical.awsAccessKeyId;
    this.awsSecretKey = canonical.awsSecretKey;
    this.lastUseAt = canonical.lastUseAt;
    this.name = canonical.name;
    this.permissionSet = canonical.permissionSet;
    this.platform = canonical.platform;
    this.siteId = canonical.siteId;
    this.siteName = canonical.siteName;
    this.url = canonical.url;
    this.userId = canonical.userId;
    this.workspaceId = canonical.workspaceId;
    this.path = canonical.path;
  }

  /**
   * List API Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ApiKey> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List API Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ApiKey> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List API Keys
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `site_id` and `workspace_id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `aws_style_credentials` and `expires_at`.</li>
   * <li>{@code filter_gt} (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `expires_at`.</li>
   * <li>{@code filter_gteq} (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `expires_at`.</li>
   * <li>{@code filter_lt} (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `expires_at`.</li>
   * <li>{@code filter_lteq} (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `expires_at`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ApiKey} records, in a {@code ListIterator}
   */
  public static ListIterator<ApiKey> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().apiKeys().list(parameters, options);
  }

  /**
   * List API Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ApiKey> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List API Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ApiKey> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show information about current API key.  (Requires current API connection to be using an API key.)
   *
   * @see #findCurrent(HashMap, HashMap)
   */
  public static ApiKey findCurrent() throws RuntimeException {
    return findCurrent(null, null);
  }

  /**
   * Show information about current API key.  (Requires current API connection to be using an API key.)
   *
   * @see #findCurrent(HashMap, HashMap)
   */
  public static ApiKey findCurrent(HashMap<String, Object> parameters) throws RuntimeException {
    return findCurrent(parameters, null);
  }


  /**
   * Show information about current API key.  (Requires current API connection to be using an API key.)
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ApiKey}
   */
  public static ApiKey findCurrent(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().apiKeys().findCurrent(parameters, options);
  }


  /**
   * Show API Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ApiKey find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show API Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ApiKey find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show API Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ApiKey find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show API Key
   *
   * @param id Api Key ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ApiKey}
   */
  public static ApiKey find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().apiKeys().find(id, parameters, options);
  }

  /**
   * Show API Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ApiKey get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show API Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ApiKey get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create API Key
   *
   * @see #create(HashMap, HashMap)
   */
  public static ApiKey create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create API Key
   *
   * @see #create(HashMap, HashMap)
   */
  public static ApiKey create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create API Key
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code description} (String): User-supplied description of API key.</li>
   * <li>{@code expires_at} (String): API Key expiration date</li>
   * <li>{@code name} (String): Required. Internal name for the API Key.  For your use.</li>
   * <li>{@code aws_style_credentials} (Boolean): If `true`, this API key will be usable with AWS-compatible endpoints, such as our Inbound S3-compatible endpoint.</li>
   * <li>{@code path} (String): Restricts the file and folder operations made with this key, meaning the files, folders, and file_actions endpoints, to the specified folder and its descendants, including copy and move destinations. For `GET /file_migrations/{id}`, a key with a path can read only migrations it started. Does not grant access beyond the owning user's permissions. Optional for `files_only` keys and required for `office_integration` keys, which need a path the owning user can read. Keys with any other permission set cannot have a path.</li>
   * <li>{@code permission_set} (String): Permissions for this API Key. Keys with the `desktop_app` permission set only have the ability to do the functions provided in our Desktop App (File and Share Link operations). Keys with the `mobile_app` permission set can only perform the functions provided by our Mobile App (File and Share Link operations). Keys with the `office_integration` permission set are auto generated, and automatically expire, to allow users to interact with office integration platforms. Keys with the `files_only` permission set can use only the files, folders, and file_actions endpoints, excluding the GPG Encrypt and GPG Decrypt file actions, where they perform file operations as a full-access file user in the key's workspace scope, along with `GET /file_migrations/{id}` and `GET /api_key`. On the migration lookup, any key with a `path` can read only migrations it started. Without a `path`, user-owned keys retain user-scoped access; site-wide Workspace-bound keys can read only migrations whose source and destination are both in their Workspace. Every `files_only` key is Workspace-bound, including Workspace `0`, as is a full-access key in a named Workspace. A full-access site-wide key in the default Workspace retains Site-wide migration access. Migrations outside the caller's visibility return `not-found`. Keys with `files_only` cannot use site admin, workspace admin, folder admin, group admin, partner admin, or billing privileges from the owning user. Both GPG file actions and every other endpoint deny them with `not-authorized/api-key-only-for-file-operations`. Folder-configured GPG encryption and decryption still apply to files uploaded with a `files_only` key.</li>
   * <li>{@code workspace_id} (Long): Workspace ID for this API Key. `0` means the default workspace.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ApiKey}
   */
  public static ApiKey create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().apiKeys().create(parameters, options);
  }


  /**
   * Update current API key.  (Requires current API connection to be using an API key.)
   *
   * @see #updateCurrent(HashMap, HashMap)
   */
  public static ApiKey updateCurrent() throws RuntimeException {
    return updateCurrent(null, null);
  }

  /**
   * Update current API key.  (Requires current API connection to be using an API key.)
   *
   * @see #updateCurrent(HashMap, HashMap)
   */
  public static ApiKey updateCurrent(HashMap<String, Object> parameters) throws RuntimeException {
    return updateCurrent(parameters, null);
  }


  /**
   * Update current API key.  (Requires current API connection to be using an API key.)
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code expires_at} (String): API Key expiration date</li>
   * <li>{@code name} (String): Internal name for the API Key.  For your use.</li>
   * <li>{@code permission_set} (String): Permissions for this API Key. Keys with the `desktop_app` permission set only have the ability to do the functions provided in our Desktop App (File and Share Link operations). Keys with the `mobile_app` permission set can only perform the functions provided by our Mobile App (File and Share Link operations). Keys with the `office_integration` permission set are auto generated, and automatically expire, to allow users to interact with office integration platforms. Keys with the `files_only` permission set can use only the files, folders, and file_actions endpoints, excluding the GPG Encrypt and GPG Decrypt file actions, where they perform file operations as a full-access file user in the key's workspace scope, along with `GET /file_migrations/{id}` and `GET /api_key`. On the migration lookup, any key with a `path` can read only migrations it started. Without a `path`, user-owned keys retain user-scoped access; site-wide Workspace-bound keys can read only migrations whose source and destination are both in their Workspace. Every `files_only` key is Workspace-bound, including Workspace `0`, as is a full-access key in a named Workspace. A full-access site-wide key in the default Workspace retains Site-wide migration access. Migrations outside the caller's visibility return `not-found`. Keys with `files_only` cannot use site admin, workspace admin, folder admin, group admin, partner admin, or billing privileges from the owning user. Both GPG file actions and every other endpoint deny them with `not-authorized/api-key-only-for-file-operations`. Folder-configured GPG encryption and decryption still apply to files uploaded with a `files_only` key.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ApiKey}
   */
  public static ApiKey updateCurrent(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().apiKeys().updateCurrent(parameters, options);
  }


  /**
   * Update API Key
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static ApiKey update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update API Key
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static ApiKey update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update API Key
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static ApiKey update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update API Key
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code description} (String): User-supplied description of API key.</li>
   * <li>{@code expires_at} (String): API Key expiration date</li>
   * <li>{@code name} (String): Internal name for the API Key.  For your use.</li>
   * </ul>
   *
   * @param id Api Key ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ApiKey}
   */
  public static ApiKey update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().apiKeys().update(id, parameters, options);
  }


  /**
   * Delete current API key.  (Requires current API connection to be using an API key.)
   *
   * @see #deleteCurrent(HashMap, HashMap)
   */
  public static void deleteCurrent() throws RuntimeException {
    deleteCurrent(null, null);
  }

  /**
   * Delete current API key.  (Requires current API connection to be using an API key.)
   *
   * @see #deleteCurrent(HashMap, HashMap)
   */
  public static void deleteCurrent(HashMap<String, Object> parameters) throws RuntimeException {
    deleteCurrent(parameters, null);
  }


  /**
   * Delete current API key.  (Requires current API connection to be using an API key.)
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void deleteCurrent(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().apiKeys().deleteCurrent(parameters, options);
  }


  /**
   * Delete API Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete API Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete API Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete API Key
   *
   * @param id Api Key ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().apiKeys().delete(id, parameters, options);
  }

  /**
   * Delete API Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete API Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
