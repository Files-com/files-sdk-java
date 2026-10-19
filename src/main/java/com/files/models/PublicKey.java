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
public class PublicKey implements ModelInterface {
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

  // One mapper for every PublicKey, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code PublicKey} with no attributes set.
   */
  public PublicKey() {
    this(null, null);
  }

  /**
   * Creates a {@code PublicKey} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public PublicKey(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code PublicKey} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public PublicKey(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Public key ID
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
   * Workspace ID (0 for default workspace).
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
   * Public key title
   */
  @JsonProperty("title")
  public String title;

  /**
   * Gets {@link #title}.
   *
   * @return the value of {@link #title}
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets {@link #title}.
   *
   * @param title the new value
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Public key created at date/time
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
   * Public key expiration date/time
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
   * Is this public key expired?
   */
  @JsonProperty("expired")
  public Boolean expired;

  /**
   * Gets {@link #expired}.
   *
   * @return the value of {@link #expired}
   */
  public Boolean getExpired() {
    return expired;
  }

  /**
   * Sets {@link #expired}.
   *
   * @param expired the new value
   */
  public void setExpired(Boolean expired) {
    this.expired = expired;
  }

  /**
   * Public key fingerprint (MD5)
   */
  @JsonProperty("fingerprint")
  public String fingerprint;

  /**
   * Gets {@link #fingerprint}.
   *
   * @return the value of {@link #fingerprint}
   */
  public String getFingerprint() {
    return fingerprint;
  }

  /**
   * Sets {@link #fingerprint}.
   *
   * @param fingerprint the new value
   */
  public void setFingerprint(String fingerprint) {
    this.fingerprint = fingerprint;
  }

  /**
   * Public key fingerprint (SHA256)
   */
  @JsonProperty("fingerprint_sha256")
  public String fingerprintSha256;

  /**
   * Gets {@link #fingerprintSha256}.
   *
   * @return the value of {@link #fingerprintSha256}
   */
  public String getFingerprintSha256() {
    return fingerprintSha256;
  }

  /**
   * Sets {@link #fingerprintSha256}.
   *
   * @param fingerprintSha256 the new value
   */
  public void setFingerprintSha256(String fingerprintSha256) {
    this.fingerprintSha256 = fingerprintSha256;
  }

  /**
   * Only returned when generating keys. Can be invalid, not_generated, generating, complete
   */
  @JsonProperty("status")
  public String status;

  /**
   * Gets {@link #status}.
   *
   * @return the value of {@link #status}
   */
  public String getStatus() {
    return status;
  }

  /**
   * Sets {@link #status}.
   *
   * @param status the new value
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * Key's most recent login time via SFTP
   */
  @JsonProperty("last_login_at")
  public Date lastLoginAt;

  /**
   * Gets {@link #lastLoginAt}.
   *
   * @return the value of {@link #lastLoginAt}
   */
  public Date getLastLoginAt() {
    return lastLoginAt;
  }

  /**
   * Sets {@link #lastLoginAt}.
   *
   * @param lastLoginAt the new value
   */
  public void setLastLoginAt(Date lastLoginAt) {
    this.lastLoginAt = lastLoginAt;
  }

  /**
   * Only returned when generating keys. Private key generated for the user.
   */
  @JsonProperty("generated_private_key")
  public String generatedPrivateKey;

  /**
   * Gets {@link #generatedPrivateKey}.
   *
   * @return the value of {@link #generatedPrivateKey}
   */
  public String getGeneratedPrivateKey() {
    return generatedPrivateKey;
  }

  /**
   * Sets {@link #generatedPrivateKey}.
   *
   * @param generatedPrivateKey the new value
   */
  public void setGeneratedPrivateKey(String generatedPrivateKey) {
    this.generatedPrivateKey = generatedPrivateKey;
  }

  /**
   * Only returned when generating keys. Public key generated for the user.
   */
  @JsonProperty("generated_public_key")
  public String generatedPublicKey;

  /**
   * Gets {@link #generatedPublicKey}.
   *
   * @return the value of {@link #generatedPublicKey}
   */
  public String getGeneratedPublicKey() {
    return generatedPublicKey;
  }

  /**
   * Sets {@link #generatedPublicKey}.
   *
   * @param generatedPublicKey the new value
   */
  public void setGeneratedPublicKey(String generatedPublicKey) {
    this.generatedPublicKey = generatedPublicKey;
  }

  /**
   * Username of the user this public key is associated with
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
   * User ID this public key is associated with
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
   * Actual contents of SSH key.
   */
  @JsonProperty("public_key")
  public String publicKey;

  /**
   * Gets {@link #publicKey}.
   *
   * @return the value of {@link #publicKey}
   */
  public String getPublicKey() {
    return publicKey;
  }

  /**
   * Sets {@link #publicKey}.
   *
   * @param publicKey the new value
   */
  public void setPublicKey(String publicKey) {
    this.publicKey = publicKey;
  }

  /**
   * If true, generate a new SSH key pair. Can not be used with `public_key`
   */
  @JsonProperty("generate_keypair")
  public Boolean generateKeypair;

  /**
   * Gets {@link #generateKeypair}.
   *
   * @return the value of {@link #generateKeypair}
   */
  public Boolean getGenerateKeypair() {
    return generateKeypair;
  }

  /**
   * Sets {@link #generateKeypair}.
   *
   * @param generateKeypair the new value
   */
  public void setGenerateKeypair(Boolean generateKeypair) {
    this.generateKeypair = generateKeypair;
  }

  /**
   * Password for the private key. Used for the generation of the key. Will be ignored if `generate_keypair` is false.
   */
  @JsonProperty("generate_private_key_password")
  public String generatePrivateKeyPassword;

  /**
   * Gets {@link #generatePrivateKeyPassword}.
   *
   * @return the value of {@link #generatePrivateKeyPassword}
   */
  public String getGeneratePrivateKeyPassword() {
    return generatePrivateKeyPassword;
  }

  /**
   * Sets {@link #generatePrivateKeyPassword}.
   *
   * @param generatePrivateKeyPassword the new value
   */
  public void setGeneratePrivateKeyPassword(String generatePrivateKeyPassword) {
    this.generatePrivateKeyPassword = generatePrivateKeyPassword;
  }

  /**
   * Type of key to generate.  One of rsa, dsa, ecdsa, ed25519. Used for the generation of the key. Will be ignored if `generate_keypair` is false.
   */
  @JsonProperty("generate_algorithm")
  public String generateAlgorithm;

  /**
   * Gets {@link #generateAlgorithm}.
   *
   * @return the value of {@link #generateAlgorithm}
   */
  public String getGenerateAlgorithm() {
    return generateAlgorithm;
  }

  /**
   * Sets {@link #generateAlgorithm}.
   *
   * @param generateAlgorithm the new value
   */
  public void setGenerateAlgorithm(String generateAlgorithm) {
    this.generateAlgorithm = generateAlgorithm;
  }

  /**
   * Length of key to generate. If algorithm is ecdsa, this is the signature size. Used for the generation of the key. Will be ignored if `generate_keypair` is false.
   */
  @JsonProperty("generate_length")
  public Long generateLength;

  /**
   * Gets {@link #generateLength}.
   *
   * @return the value of {@link #generateLength}
   */
  public Long getGenerateLength() {
    return generateLength;
  }

  /**
   * Sets {@link #generateLength}.
   *
   * @param generateLength the new value
   */
  public void setGenerateLength(Long generateLength) {
    this.generateLength = generateLength;
  }

  /**
   * Updates this PublicKey. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update Public Key
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code title} (String): Required. Internal reference for key.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code PublicKey}
   */
  public PublicKey update(HashMap<String, Object> parameters) throws IOException {
    PublicKey updated = filesClient().publicKeys().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete Public Key
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().publicKeys().delete(this.id, parameters, this.options);
  }

  /**
   * Delete Public Key
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this PublicKey: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().publicKeys().create(parameters, this.options));
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
  private void refreshFrom(PublicKey canonical) {
    this.id = canonical.id;
    this.workspaceId = canonical.workspaceId;
    this.title = canonical.title;
    this.createdAt = canonical.createdAt;
    this.expiresAt = canonical.expiresAt;
    this.expired = canonical.expired;
    this.fingerprint = canonical.fingerprint;
    this.fingerprintSha256 = canonical.fingerprintSha256;
    this.status = canonical.status;
    this.lastLoginAt = canonical.lastLoginAt;
    this.generatedPrivateKey = canonical.generatedPrivateKey;
    this.generatedPublicKey = canonical.generatedPublicKey;
    this.username = canonical.username;
    this.userId = canonical.userId;
    this.publicKey = canonical.publicKey;
    this.generateKeypair = canonical.generateKeypair;
    this.generatePrivateKeyPassword = canonical.generatePrivateKeyPassword;
    this.generateAlgorithm = canonical.generateAlgorithm;
    this.generateLength = canonical.generateLength;
  }

  /**
   * List Public Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<PublicKey> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Public Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<PublicKey> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Public Keys
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `user_id`, `title` or `created_at`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at` and `workspace_id`. Valid field combinations are `[ workspace_id, created_at ]`.</li>
   * <li>{@code filter_gt} (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_gteq} (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_lt} (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_lteq} (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code PublicKey} records, in a {@code ListIterator}
   */
  public static ListIterator<PublicKey> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().publicKeys().list(parameters, options);
  }

  /**
   * List Public Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<PublicKey> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Public Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<PublicKey> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Public Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static PublicKey find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Public Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static PublicKey find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Public Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static PublicKey find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Public Key
   *
   * @param id Public Key ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code PublicKey}
   */
  public static PublicKey find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().publicKeys().find(id, parameters, options);
  }

  /**
   * Show Public Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static PublicKey get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Public Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static PublicKey get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Public Key
   *
   * @see #create(HashMap, HashMap)
   */
  public static PublicKey create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Public Key
   *
   * @see #create(HashMap, HashMap)
   */
  public static PublicKey create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create Public Key
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code title} (String): Required. Internal reference for key.</li>
   * <li>{@code public_key} (String): Actual contents of SSH key.</li>
   * <li>{@code generate_keypair} (Boolean): If true, generate a new SSH key pair. Can not be used with `public_key`</li>
   * <li>{@code generate_private_key_password} (String): Password for the private key. Used for the generation of the key. Will be ignored if `generate_keypair` is false.</li>
   * <li>{@code generate_algorithm} (String): Type of key to generate.  One of rsa, dsa, ecdsa, ed25519. Used for the generation of the key. Will be ignored if `generate_keypair` is false.</li>
   * <li>{@code generate_length} (Long): Length of key to generate. If algorithm is ecdsa, this is the signature size. Used for the generation of the key. Will be ignored if `generate_keypair` is false.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code PublicKey}
   */
  public static PublicKey create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().publicKeys().create(parameters, options);
  }


  /**
   * Update Public Key
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static PublicKey update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Public Key
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static PublicKey update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Public Key
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static PublicKey update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Public Key
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code title} (String): Required. Internal reference for key.</li>
   * </ul>
   *
   * @param id Public Key ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code PublicKey}
   */
  public static PublicKey update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().publicKeys().update(id, parameters, options);
  }


  /**
   * Delete Public Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Public Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Public Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Public Key
   *
   * @param id Public Key ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().publicKeys().delete(id, parameters, options);
  }

  /**
   * Delete Public Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Public Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
