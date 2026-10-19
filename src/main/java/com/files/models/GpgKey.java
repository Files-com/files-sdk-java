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
public class GpgKey implements ModelInterface {
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

  // One mapper for every GpgKey, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code GpgKey} with no attributes set.
   */
  public GpgKey() {
    this(null, null);
  }

  /**
   * Creates a {@code GpgKey} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public GpgKey(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code GpgKey} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public GpgKey(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * GPG key ID.
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
   * GPG key expiration date.
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
   * GPG key name.
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
   * Partner ID who owns this GPG Key, if applicable.
   */
  @JsonProperty("partner_id")
  public Long partnerId;

  /**
   * Gets {@link #partnerId}.
   *
   * @return the value of {@link #partnerId}
   */
  public Long getPartnerId() {
    return partnerId;
  }

  /**
   * Sets {@link #partnerId}.
   *
   * @param partnerId the new value
   */
  public void setPartnerId(Long partnerId) {
    this.partnerId = partnerId;
  }

  /**
   * Name of the Partner who owns this GPG Key, if applicable.
   */
  @JsonProperty("partner_name")
  public String partnerName;

  /**
   * Gets {@link #partnerName}.
   *
   * @return the value of {@link #partnerName}
   */
  public String getPartnerName() {
    return partnerName;
  }

  /**
   * Sets {@link #partnerName}.
   *
   * @param partnerName the new value
   */
  public void setPartnerName(String partnerName) {
    this.partnerName = partnerName;
  }

  /**
   * User ID who owns this GPG Key, if applicable.
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
   * MD5 hash of the GPG public key
   */
  @JsonProperty("public_key_md5")
  public String publicKeyMd5;

  /**
   * Gets {@link #publicKeyMd5}.
   *
   * @return the value of {@link #publicKeyMd5}
   */
  public String getPublicKeyMd5() {
    return publicKeyMd5;
  }

  /**
   * Sets {@link #publicKeyMd5}.
   *
   * @param publicKeyMd5 the new value
   */
  public void setPublicKeyMd5(String publicKeyMd5) {
    this.publicKeyMd5 = publicKeyMd5;
  }

  /**
   * MD5 hash of the GPG private key.
   */
  @JsonProperty("private_key_md5")
  public String privateKeyMd5;

  /**
   * Gets {@link #privateKeyMd5}.
   *
   * @return the value of {@link #privateKeyMd5}
   */
  public String getPrivateKeyMd5() {
    return privateKeyMd5;
  }

  /**
   * Sets {@link #privateKeyMd5}.
   *
   * @param privateKeyMd5 the new value
   */
  public void setPrivateKeyMd5(String privateKeyMd5) {
    this.privateKeyMd5 = privateKeyMd5;
  }

  /**
   * GPG public key
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
   * GPG private key.
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
   * GPG private key password. Only required for password protected keys.
   */
  @JsonProperty("private_key_password_md5")
  public String privateKeyPasswordMd5;

  /**
   * Gets {@link #privateKeyPasswordMd5}.
   *
   * @return the value of {@link #privateKeyPasswordMd5}
   */
  public String getPrivateKeyPasswordMd5() {
    return privateKeyPasswordMd5;
  }

  /**
   * Sets {@link #privateKeyPasswordMd5}.
   *
   * @param privateKeyPasswordMd5 the new value
   */
  public void setPrivateKeyPasswordMd5(String privateKeyPasswordMd5) {
    this.privateKeyPasswordMd5 = privateKeyPasswordMd5;
  }

  /**
   * The GPG public key
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
   * The GPG private key
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
   * The GPG private key password
   */
  @JsonProperty("private_key_password")
  public String privateKeyPassword;

  /**
   * Gets {@link #privateKeyPassword}.
   *
   * @return the value of {@link #privateKeyPassword}
   */
  public String getPrivateKeyPassword() {
    return privateKeyPassword;
  }

  /**
   * Sets {@link #privateKeyPassword}.
   *
   * @param privateKeyPassword the new value
   */
  public void setPrivateKeyPassword(String privateKeyPassword) {
    this.privateKeyPassword = privateKeyPassword;
  }

  /**
   * Expiration date of the key. Used for the generation of the key. Will be ignored if `generate_keypair` is false.
   */
  @JsonProperty("generate_expires_at")
  public String generateExpiresAt;

  /**
   * Gets {@link #generateExpiresAt}.
   *
   * @return the value of {@link #generateExpiresAt}
   */
  public String getGenerateExpiresAt() {
    return generateExpiresAt;
  }

  /**
   * Sets {@link #generateExpiresAt}.
   *
   * @param generateExpiresAt the new value
   */
  public void setGenerateExpiresAt(String generateExpiresAt) {
    this.generateExpiresAt = generateExpiresAt;
  }

  /**
   * If true, generate a new GPG key pair. Can not be used with `public_key`/`private_key`
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
   * Full name of the key owner. Used for the generation of the key. Will be ignored if `generate_keypair` is false.
   */
  @JsonProperty("generate_full_name")
  public String generateFullName;

  /**
   * Gets {@link #generateFullName}.
   *
   * @return the value of {@link #generateFullName}
   */
  public String getGenerateFullName() {
    return generateFullName;
  }

  /**
   * Sets {@link #generateFullName}.
   *
   * @param generateFullName the new value
   */
  public void setGenerateFullName(String generateFullName) {
    this.generateFullName = generateFullName;
  }

  /**
   * Email address of the key owner. Used for the generation of the key. Will be ignored if `generate_keypair` is false.
   */
  @JsonProperty("generate_email")
  public String generateEmail;

  /**
   * Gets {@link #generateEmail}.
   *
   * @return the value of {@link #generateEmail}
   */
  public String getGenerateEmail() {
    return generateEmail;
  }

  /**
   * Sets {@link #generateEmail}.
   *
   * @param generateEmail the new value
   */
  public void setGenerateEmail(String generateEmail) {
    this.generateEmail = generateEmail;
  }

  /**
   * Updates this GpgKey. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update GPG Key
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code partner_id} (Long): Partner ID who owns this GPG Key, if applicable.</li>
   * <li>{@code public_key} (String): The GPG public key</li>
   * <li>{@code private_key} (String): The GPG private key</li>
   * <li>{@code private_key_password} (String): The GPG private key password</li>
   * <li>{@code name} (String): GPG key name.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code GpgKey}
   */
  public GpgKey update(HashMap<String, Object> parameters) throws IOException {
    GpgKey updated = filesClient().gpgKeys().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete GPG Key
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().gpgKeys().delete(this.id, parameters, this.options);
  }

  /**
   * Delete GPG Key
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this GpgKey: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().gpgKeys().create(parameters, this.options));
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
  private void refreshFrom(GpgKey canonical) {
    this.id = canonical.id;
    this.workspaceId = canonical.workspaceId;
    this.expiresAt = canonical.expiresAt;
    this.name = canonical.name;
    this.partnerId = canonical.partnerId;
    this.partnerName = canonical.partnerName;
    this.userId = canonical.userId;
    this.publicKeyMd5 = canonical.publicKeyMd5;
    this.privateKeyMd5 = canonical.privateKeyMd5;
    this.generatedPublicKey = canonical.generatedPublicKey;
    this.generatedPrivateKey = canonical.generatedPrivateKey;
    this.privateKeyPasswordMd5 = canonical.privateKeyPasswordMd5;
    this.publicKey = canonical.publicKey;
    this.privateKey = canonical.privateKey;
    this.privateKeyPassword = canonical.privateKeyPassword;
    this.generateExpiresAt = canonical.generateExpiresAt;
    this.generateKeypair = canonical.generateKeypair;
    this.generateFullName = canonical.generateFullName;
    this.generateEmail = canonical.generateEmail;
  }

  /**
   * List GPG Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<GpgKey> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List GPG Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<GpgKey> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List GPG Keys
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `name` or `expires_at`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id`, `partner_id` or `expires_at`. Valid field combinations are `[ workspace_id, expires_at ]`.</li>
   * <li>{@code filter_gt} (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `expires_at`.</li>
   * <li>{@code filter_gteq} (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `expires_at`.</li>
   * <li>{@code filter_lt} (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `expires_at`.</li>
   * <li>{@code filter_lteq} (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `expires_at`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code GpgKey} records, in a {@code ListIterator}
   */
  public static ListIterator<GpgKey> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().gpgKeys().list(parameters, options);
  }

  /**
   * List GPG Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<GpgKey> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List GPG Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<GpgKey> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show GPG Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static GpgKey find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show GPG Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static GpgKey find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show GPG Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static GpgKey find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show GPG Key
   *
   * @param id Gpg Key ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code GpgKey}
   */
  public static GpgKey find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().gpgKeys().find(id, parameters, options);
  }

  /**
   * Show GPG Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static GpgKey get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show GPG Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static GpgKey get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create GPG Key
   *
   * @see #create(HashMap, HashMap)
   */
  public static GpgKey create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create GPG Key
   *
   * @see #create(HashMap, HashMap)
   */
  public static GpgKey create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create GPG Key
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code partner_id} (Long): Partner ID who owns this GPG Key, if applicable.</li>
   * <li>{@code public_key} (String): The GPG public key</li>
   * <li>{@code private_key} (String): The GPG private key</li>
   * <li>{@code private_key_password} (String): The GPG private key password</li>
   * <li>{@code name} (String): Required. GPG key name.</li>
   * <li>{@code workspace_id} (Long): Workspace ID (0 for default workspace).</li>
   * <li>{@code generate_expires_at} (String): Expiration date of the key. Used for the generation of the key. Will be ignored if `generate_keypair` is false.</li>
   * <li>{@code generate_keypair} (Boolean): If true, generate a new GPG key pair. Can not be used with `public_key`/`private_key`</li>
   * <li>{@code generate_full_name} (String): Full name of the key owner. Used for the generation of the key. Will be ignored if `generate_keypair` is false.</li>
   * <li>{@code generate_email} (String): Email address of the key owner. Used for the generation of the key. Will be ignored if `generate_keypair` is false.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code GpgKey}
   */
  public static GpgKey create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().gpgKeys().create(parameters, options);
  }


  /**
   * Update GPG Key
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static GpgKey update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update GPG Key
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static GpgKey update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update GPG Key
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static GpgKey update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update GPG Key
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code partner_id} (Long): Partner ID who owns this GPG Key, if applicable.</li>
   * <li>{@code public_key} (String): The GPG public key</li>
   * <li>{@code private_key} (String): The GPG private key</li>
   * <li>{@code private_key_password} (String): The GPG private key password</li>
   * <li>{@code name} (String): GPG key name.</li>
   * </ul>
   *
   * @param id Gpg Key ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code GpgKey}
   */
  public static GpgKey update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().gpgKeys().update(id, parameters, options);
  }


  /**
   * Delete GPG Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete GPG Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete GPG Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete GPG Key
   *
   * @param id Gpg Key ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().gpgKeys().delete(id, parameters, options);
  }

  /**
   * Delete GPG Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete GPG Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
