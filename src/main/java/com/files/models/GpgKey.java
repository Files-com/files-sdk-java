package com.files.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.ListIterator;
import com.files.net.ClientRuntime;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
import com.files.util.PathUtils;
import com.files.util.UrlUtils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


  public GpgKey() {
    this(null, null);
  }

  public GpgKey(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Workspace ID (0 for default workspace).
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
   * GPG key expiration date.
   */
  @JsonProperty("expires_at")
  public Date expiresAt;

  public Date getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(Date expiresAt) {
    this.expiresAt = expiresAt;
  }

  /**
   * GPG key name.
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
   * Partner ID who owns this GPG Key, if applicable.
   */
  @JsonProperty("partner_id")
  public Long partnerId;

  public Long getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(Long partnerId) {
    this.partnerId = partnerId;
  }

  /**
   * Name of the Partner who owns this GPG Key, if applicable.
   */
  @JsonProperty("partner_name")
  public String partnerName;

  public String getPartnerName() {
    return partnerName;
  }

  public void setPartnerName(String partnerName) {
    this.partnerName = partnerName;
  }

  /**
   * User ID who owns this GPG Key, if applicable.
   */
  @JsonProperty("user_id")
  public Long userId;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  /**
   * MD5 hash of the GPG public key
   */
  @JsonProperty("public_key_md5")
  public String publicKeyMd5;

  public String getPublicKeyMd5() {
    return publicKeyMd5;
  }

  public void setPublicKeyMd5(String publicKeyMd5) {
    this.publicKeyMd5 = publicKeyMd5;
  }

  /**
   * MD5 hash of the GPG private key.
   */
  @JsonProperty("private_key_md5")
  public String privateKeyMd5;

  public String getPrivateKeyMd5() {
    return privateKeyMd5;
  }

  public void setPrivateKeyMd5(String privateKeyMd5) {
    this.privateKeyMd5 = privateKeyMd5;
  }

  /**
   * GPG public key
   */
  @JsonProperty("generated_public_key")
  public String generatedPublicKey;

  public String getGeneratedPublicKey() {
    return generatedPublicKey;
  }

  public void setGeneratedPublicKey(String generatedPublicKey) {
    this.generatedPublicKey = generatedPublicKey;
  }

  /**
   * GPG private key.
   */
  @JsonProperty("generated_private_key")
  public String generatedPrivateKey;

  public String getGeneratedPrivateKey() {
    return generatedPrivateKey;
  }

  public void setGeneratedPrivateKey(String generatedPrivateKey) {
    this.generatedPrivateKey = generatedPrivateKey;
  }

  /**
   * GPG private key password. Only required for password protected keys.
   */
  @JsonProperty("private_key_password_md5")
  public String privateKeyPasswordMd5;

  public String getPrivateKeyPasswordMd5() {
    return privateKeyPasswordMd5;
  }

  public void setPrivateKeyPasswordMd5(String privateKeyPasswordMd5) {
    this.privateKeyPasswordMd5 = privateKeyPasswordMd5;
  }

  /**
   * The GPG public key
   */
  @JsonProperty("public_key")
  public String publicKey;

  public String getPublicKey() {
    return publicKey;
  }

  public void setPublicKey(String publicKey) {
    this.publicKey = publicKey;
  }

  /**
   * The GPG private key
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
   * The GPG private key password
   */
  @JsonProperty("private_key_password")
  public String privateKeyPassword;

  public String getPrivateKeyPassword() {
    return privateKeyPassword;
  }

  public void setPrivateKeyPassword(String privateKeyPassword) {
    this.privateKeyPassword = privateKeyPassword;
  }

  /**
   * Expiration date of the key. Used for the generation of the key. Will be ignored if `generate_keypair` is false.
   */
  @JsonProperty("generate_expires_at")
  public String generateExpiresAt;

  public String getGenerateExpiresAt() {
    return generateExpiresAt;
  }

  public void setGenerateExpiresAt(String generateExpiresAt) {
    this.generateExpiresAt = generateExpiresAt;
  }

  /**
   * If true, generate a new GPG key pair. Can not be used with `public_key`/`private_key`
   */
  @JsonProperty("generate_keypair")
  public Boolean generateKeypair;

  public Boolean getGenerateKeypair() {
    return generateKeypair;
  }

  public void setGenerateKeypair(Boolean generateKeypair) {
    this.generateKeypair = generateKeypair;
  }

  /**
   * Full name of the key owner. Used for the generation of the key. Will be ignored if `generate_keypair` is false.
   */
  @JsonProperty("generate_full_name")
  public String generateFullName;

  public String getGenerateFullName() {
    return generateFullName;
  }

  public void setGenerateFullName(String generateFullName) {
    this.generateFullName = generateFullName;
  }

  /**
   * Email address of the key owner. Used for the generation of the key. Will be ignored if `generate_keypair` is false.
   */
  @JsonProperty("generate_email")
  public String generateEmail;

  public String getGenerateEmail() {
    return generateEmail;
  }

  public void setGenerateEmail(String generateEmail) {
    this.generateEmail = generateEmail;
  }

  /**
   * Updates this GpgKey. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Parameters:
   *   partner_id - int64 - Partner ID who owns this GPG Key, if applicable.
   *   public_key - string - The GPG public key
   *   private_key - string - The GPG private key
   *   private_key_password - string - The GPG private key password
   *   name - string - GPG key name.
   */
  public GpgKey update(HashMap<String, Object> parameters) throws IOException {
    GpgKey updated = filesClient().gpgKeys().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().gpgKeys().delete(this.id, parameters, this.options);
  }

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
   * Parameters:
   *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `name` or `expires_at`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id`, `partner_id` or `expires_at`. Valid field combinations are `[ workspace_id, expires_at ]`.
   *   filter_gt - object - If set, return records where the specified field is greater than the supplied value. Valid fields are `expires_at`.
   *   filter_gteq - object - If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `expires_at`.
   *   filter_lt - object - If set, return records where the specified field is less than the supplied value. Valid fields are `expires_at`.
   *   filter_lteq - object - If set, return records where the specified field is less than or equal the supplied value. Valid fields are `expires_at`.
   */
  public static ListIterator<GpgKey> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<GpgKey> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<GpgKey> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().gpgKeys().list(parameters, options);
  }

  public static ListIterator<GpgKey> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<GpgKey> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Gpg Key ID.
   */
  public static GpgKey find() throws RuntimeException {
    return find(null, null, null);
  }

  public static GpgKey find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static GpgKey find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static GpgKey find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().gpgKeys().find(id, parameters, options);
  }

  public static GpgKey get() throws RuntimeException {
    return get(null, null, null);
  }

  public static GpgKey get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
   *   partner_id - int64 - Partner ID who owns this GPG Key, if applicable.
   *   public_key - string - The GPG public key
   *   private_key - string - The GPG private key
   *   private_key_password - string - The GPG private key password
   *   name (required) - string - GPG key name.
   *   workspace_id - int64 - Workspace ID (0 for default workspace).
   *   generate_expires_at - string - Expiration date of the key. Used for the generation of the key. Will be ignored if `generate_keypair` is false.
   *   generate_keypair - boolean - If true, generate a new GPG key pair. Can not be used with `public_key`/`private_key`
   *   generate_full_name - string - Full name of the key owner. Used for the generation of the key. Will be ignored if `generate_keypair` is false.
   *   generate_email - string - Email address of the key owner. Used for the generation of the key. Will be ignored if `generate_keypair` is false.
   */
  public static GpgKey create() throws RuntimeException {
    return create(null, null);
  }

  public static GpgKey create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static GpgKey create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().gpgKeys().create(parameters, options);
  }


  /**
   * Parameters:
   *   partner_id - int64 - Partner ID who owns this GPG Key, if applicable.
   *   public_key - string - The GPG public key
   *   private_key - string - The GPG private key
   *   private_key_password - string - The GPG private key password
   *   name - string - GPG key name.
   */
  public static GpgKey update() throws RuntimeException {
    return update(null, null, null);
  }

  public static GpgKey update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static GpgKey update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static GpgKey update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().gpgKeys().update(id, parameters, options);
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
    FilesClient.defaultClient().gpgKeys().delete(id, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
