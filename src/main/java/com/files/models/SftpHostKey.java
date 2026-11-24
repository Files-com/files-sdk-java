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
public class SftpHostKey implements ModelInterface {
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

  // One mapper for every SftpHostKey, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code SftpHostKey} with no attributes set.
   */
  public SftpHostKey() {
    this(null, null);
  }

  /**
   * Creates a {@code SftpHostKey} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public SftpHostKey(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code SftpHostKey} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public SftpHostKey(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * If true, use this SFTP Host Key.
   */
  @JsonProperty("active")
  public Boolean active;

  /**
   * Gets {@link #active}.
   *
   * @return the value of {@link #active}
   */
  public Boolean getActive() {
    return active;
  }

  /**
   * Sets {@link #active}.
   *
   * @param active the new value
   */
  public void setActive(Boolean active) {
    this.active = active;
  }

  /**
   * Custom Domain ID. If set, this key is used only for that Custom Domain.
   */
  @JsonProperty("custom_domain_id")
  public Long customDomainId;

  /**
   * Gets {@link #customDomainId}.
   *
   * @return the value of {@link #customDomainId}
   */
  public Long getCustomDomainId() {
    return customDomainId;
  }

  /**
   * Sets {@link #customDomainId}.
   *
   * @param customDomainId the new value
   */
  public void setCustomDomainId(Long customDomainId) {
    this.customDomainId = customDomainId;
  }

  /**
   * SFTP Host Key ID
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
   * The friendly name of this SFTP Host Key.
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
   * SSH key type
   */
  @JsonProperty("key_type")
  public String keyType;

  /**
   * Gets {@link #keyType}.
   *
   * @return the value of {@link #keyType}
   */
  public String getKeyType() {
    return keyType;
  }

  /**
   * Sets {@link #keyType}.
   *
   * @param keyType the new value
   */
  public void setKeyType(String keyType) {
    this.keyType = keyType;
  }

  /**
   * MD5 Fingerprint of the public key
   */
  @JsonProperty("fingerprint_md5")
  public String fingerprintMd5;

  /**
   * Gets {@link #fingerprintMd5}.
   *
   * @return the value of {@link #fingerprintMd5}
   */
  public String getFingerprintMd5() {
    return fingerprintMd5;
  }

  /**
   * Sets {@link #fingerprintMd5}.
   *
   * @param fingerprintMd5 the new value
   */
  public void setFingerprintMd5(String fingerprintMd5) {
    this.fingerprintMd5 = fingerprintMd5;
  }

  /**
   * SHA256 Fingerprint of the public key
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
   * The private key data.
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
   * Updates this SftpHostKey. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update SFTP Host Key
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code active} (Boolean): If true, use this SFTP Host Key.</li>
   * <li>{@code custom_domain_id} (Long): Custom Domain ID. If set, this key is used only for that Custom Domain.</li>
   * <li>{@code name} (String): The friendly name of this SFTP Host Key.</li>
   * <li>{@code private_key} (String): The private key data.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code SftpHostKey}
   */
  public SftpHostKey update(HashMap<String, Object> parameters) throws IOException {
    SftpHostKey updated = filesClient().sftpHostKeys().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete SFTP Host Key
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().sftpHostKeys().delete(this.id, parameters, this.options);
  }

  /**
   * Delete SFTP Host Key
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this SftpHostKey: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().sftpHostKeys().create(parameters, this.options));
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
  private void refreshFrom(SftpHostKey canonical) {
    this.active = canonical.active;
    this.customDomainId = canonical.customDomainId;
    this.id = canonical.id;
    this.name = canonical.name;
    this.keyType = canonical.keyType;
    this.fingerprintMd5 = canonical.fingerprintMd5;
    this.fingerprintSha256 = canonical.fingerprintSha256;
    this.privateKey = canonical.privateKey;
  }

  /**
   * List SFTP Host Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<SftpHostKey> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List SFTP Host Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<SftpHostKey> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List SFTP Host Keys
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code SftpHostKey} records, in a {@code ListIterator}
   */
  public static ListIterator<SftpHostKey> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().sftpHostKeys().list(parameters, options);
  }

  /**
   * List SFTP Host Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<SftpHostKey> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List SFTP Host Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<SftpHostKey> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show SFTP Host Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SftpHostKey find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show SFTP Host Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SftpHostKey find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show SFTP Host Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SftpHostKey find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show SFTP Host Key
   *
   * @param id Sftp Host Key ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code SftpHostKey}
   */
  public static SftpHostKey find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().sftpHostKeys().find(id, parameters, options);
  }

  /**
   * Show SFTP Host Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SftpHostKey get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show SFTP Host Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SftpHostKey get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create SFTP Host Key
   *
   * @see #create(HashMap, HashMap)
   */
  public static SftpHostKey create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create SFTP Host Key
   *
   * @see #create(HashMap, HashMap)
   */
  public static SftpHostKey create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create SFTP Host Key
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code active} (Boolean): If true, use this SFTP Host Key.</li>
   * <li>{@code custom_domain_id} (Long): Custom Domain ID. If set, this key is used only for that Custom Domain.</li>
   * <li>{@code name} (String): The friendly name of this SFTP Host Key.</li>
   * <li>{@code private_key} (String): The private key data.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code SftpHostKey}
   */
  public static SftpHostKey create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().sftpHostKeys().create(parameters, options);
  }


  /**
   * Update SFTP Host Key
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static SftpHostKey update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update SFTP Host Key
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static SftpHostKey update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update SFTP Host Key
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static SftpHostKey update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update SFTP Host Key
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code active} (Boolean): If true, use this SFTP Host Key.</li>
   * <li>{@code custom_domain_id} (Long): Custom Domain ID. If set, this key is used only for that Custom Domain.</li>
   * <li>{@code name} (String): The friendly name of this SFTP Host Key.</li>
   * <li>{@code private_key} (String): The private key data.</li>
   * </ul>
   *
   * @param id Sftp Host Key ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code SftpHostKey}
   */
  public static SftpHostKey update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().sftpHostKeys().update(id, parameters, options);
  }


  /**
   * Delete SFTP Host Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete SFTP Host Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete SFTP Host Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete SFTP Host Key
   *
   * @param id Sftp Host Key ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().sftpHostKeys().delete(id, parameters, options);
  }

  /**
   * Delete SFTP Host Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete SFTP Host Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
