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
public class As2Station implements ModelInterface {
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

  // One mapper for every As2Station, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code As2Station} with no attributes set.
   */
  public As2Station() {
    this(null, null);
  }

  /**
   * Creates a {@code As2Station} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public As2Station(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code As2Station} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public As2Station(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Id of the AS2 Station.
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
   * ID of the Workspace associated with this AS2 Station.
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
   * The station's formal AS2 name, unique across all Workspaces on the Site.
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
   * Public URI for sending AS2 message to.
   */
  @JsonProperty("uri")
  public String uri;

  /**
   * Gets {@link #uri}.
   *
   * @return the value of {@link #uri}
   */
  public String getUri() {
    return uri;
  }

  /**
   * Sets {@link #uri}.
   *
   * @param uri the new value
   */
  public void setUri(String uri) {
    this.uri = uri;
  }

  /**
   * The station's AS2 domain name.
   */
  @JsonProperty("domain")
  public String domain;

  /**
   * Gets {@link #domain}.
   *
   * @return the value of {@link #domain}
   */
  public String getDomain() {
    return domain;
  }

  /**
   * Sets {@link #domain}.
   *
   * @param domain the new value
   */
  public void setDomain(String domain) {
    this.domain = domain;
  }

  /**
   * Serial of public certificate used for message security in hex format.
   */
  @JsonProperty("hex_public_certificate_serial")
  public String hexPublicCertificateSerial;

  /**
   * Gets {@link #hexPublicCertificateSerial}.
   *
   * @return the value of {@link #hexPublicCertificateSerial}
   */
  public String getHexPublicCertificateSerial() {
    return hexPublicCertificateSerial;
  }

  /**
   * Sets {@link #hexPublicCertificateSerial}.
   *
   * @param hexPublicCertificateSerial the new value
   */
  public void setHexPublicCertificateSerial(String hexPublicCertificateSerial) {
    this.hexPublicCertificateSerial = hexPublicCertificateSerial;
  }

  /**
   * MD5 hash of public certificate used for message security.
   */
  @JsonProperty("public_certificate_md5")
  public String publicCertificateMd5;

  /**
   * Gets {@link #publicCertificateMd5}.
   *
   * @return the value of {@link #publicCertificateMd5}
   */
  public String getPublicCertificateMd5() {
    return publicCertificateMd5;
  }

  /**
   * Sets {@link #publicCertificateMd5}.
   *
   * @param publicCertificateMd5 the new value
   */
  public void setPublicCertificateMd5(String publicCertificateMd5) {
    this.publicCertificateMd5 = publicCertificateMd5;
  }

  /**
   * Public certificate used for message security.
   */
  @JsonProperty("public_certificate")
  public String publicCertificate;

  /**
   * Gets {@link #publicCertificate}.
   *
   * @return the value of {@link #publicCertificate}
   */
  public String getPublicCertificate() {
    return publicCertificate;
  }

  /**
   * Sets {@link #publicCertificate}.
   *
   * @param publicCertificate the new value
   */
  public void setPublicCertificate(String publicCertificate) {
    this.publicCertificate = publicCertificate;
  }

  /**
   * MD5 hash of private key used for message security.
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
   * Subject of public certificate used for message security.
   */
  @JsonProperty("public_certificate_subject")
  public String publicCertificateSubject;

  /**
   * Gets {@link #publicCertificateSubject}.
   *
   * @return the value of {@link #publicCertificateSubject}
   */
  public String getPublicCertificateSubject() {
    return publicCertificateSubject;
  }

  /**
   * Sets {@link #publicCertificateSubject}.
   *
   * @param publicCertificateSubject the new value
   */
  public void setPublicCertificateSubject(String publicCertificateSubject) {
    this.publicCertificateSubject = publicCertificateSubject;
  }

  /**
   * Issuer of public certificate used for message security.
   */
  @JsonProperty("public_certificate_issuer")
  public String publicCertificateIssuer;

  /**
   * Gets {@link #publicCertificateIssuer}.
   *
   * @return the value of {@link #publicCertificateIssuer}
   */
  public String getPublicCertificateIssuer() {
    return publicCertificateIssuer;
  }

  /**
   * Sets {@link #publicCertificateIssuer}.
   *
   * @param publicCertificateIssuer the new value
   */
  public void setPublicCertificateIssuer(String publicCertificateIssuer) {
    this.publicCertificateIssuer = publicCertificateIssuer;
  }

  /**
   * Serial of public certificate used for message security.
   */
  @JsonProperty("public_certificate_serial")
  public String publicCertificateSerial;

  /**
   * Gets {@link #publicCertificateSerial}.
   *
   * @return the value of {@link #publicCertificateSerial}
   */
  public String getPublicCertificateSerial() {
    return publicCertificateSerial;
  }

  /**
   * Sets {@link #publicCertificateSerial}.
   *
   * @param publicCertificateSerial the new value
   */
  public void setPublicCertificateSerial(String publicCertificateSerial) {
    this.publicCertificateSerial = publicCertificateSerial;
  }

  /**
   * Not before value of public certificate used for message security.
   */
  @JsonProperty("public_certificate_not_before")
  public String publicCertificateNotBefore;

  /**
   * Gets {@link #publicCertificateNotBefore}.
   *
   * @return the value of {@link #publicCertificateNotBefore}
   */
  public String getPublicCertificateNotBefore() {
    return publicCertificateNotBefore;
  }

  /**
   * Sets {@link #publicCertificateNotBefore}.
   *
   * @param publicCertificateNotBefore the new value
   */
  public void setPublicCertificateNotBefore(String publicCertificateNotBefore) {
    this.publicCertificateNotBefore = publicCertificateNotBefore;
  }

  /**
   * Not after value of public certificate used for message security.
   */
  @JsonProperty("public_certificate_not_after")
  public String publicCertificateNotAfter;

  /**
   * Gets {@link #publicCertificateNotAfter}.
   *
   * @return the value of {@link #publicCertificateNotAfter}
   */
  public String getPublicCertificateNotAfter() {
    return publicCertificateNotAfter;
  }

  /**
   * Sets {@link #publicCertificateNotAfter}.
   *
   * @param publicCertificateNotAfter the new value
   */
  public void setPublicCertificateNotAfter(String publicCertificateNotAfter) {
    this.publicCertificateNotAfter = publicCertificateNotAfter;
  }

  /**
   * MD5 hash of private key password used for message security.
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
   * PEM-encoded private key matching public_certificate.
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
   * Password for the PEM-encoded private key.
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
   * Base64-encoded PKCS#12 (.pfx or .p12) file containing the identity's certificate and private key. Provide this instead of public_certificate and private_key.
   */
  @JsonProperty("pkcs12")
  public String pkcs12;

  /**
   * Gets {@link #pkcs12}.
   *
   * @return the value of {@link #pkcs12}
   */
  public String getPkcs12() {
    return pkcs12;
  }

  /**
   * Sets {@link #pkcs12}.
   *
   * @param pkcs12 the new value
   */
  public void setPkcs12(String pkcs12) {
    this.pkcs12 = pkcs12;
  }

  /**
   * Password for pkcs12. The file and password are used only for import; the extracted certificate and private key are stored as PEM.
   */
  @JsonProperty("pkcs12_password")
  public String pkcs12Password;

  /**
   * Gets {@link #pkcs12Password}.
   *
   * @return the value of {@link #pkcs12Password}
   */
  public String getPkcs12Password() {
    return pkcs12Password;
  }

  /**
   * Sets {@link #pkcs12Password}.
   *
   * @param pkcs12Password the new value
   */
  public void setPkcs12Password(String pkcs12Password) {
    this.pkcs12Password = pkcs12Password;
  }

  /**
   * Updates this As2Station. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update AS2 Station
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): The station's formal AS2 name, unique across all Workspaces on the Site.</li>
   * <li>{@code public_certificate} (String): Public certificate used for message security.</li>
   * <li>{@code private_key} (String): PEM-encoded private key matching public_certificate.</li>
   * <li>{@code private_key_password} (String): Password for the PEM-encoded private key.</li>
   * <li>{@code pkcs12} (String): Base64-encoded PKCS#12 (.pfx or .p12) file containing the identity's replacement certificate and private key. Provide this instead of public_certificate and private_key.</li>
   * <li>{@code pkcs12_password} (String): Password for pkcs12. The file and password are used only for import; the extracted certificate and private key are stored as PEM.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code As2Station}
   */
  public As2Station update(HashMap<String, Object> parameters) throws IOException {
    As2Station updated = filesClient().as2Stations().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete AS2 Station
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().as2Stations().delete(this.id, parameters, this.options);
  }

  /**
   * Delete AS2 Station
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this As2Station: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().as2Stations().create(parameters, this.options));
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
  private void refreshFrom(As2Station canonical) {
    this.id = canonical.id;
    this.workspaceId = canonical.workspaceId;
    this.name = canonical.name;
    this.uri = canonical.uri;
    this.domain = canonical.domain;
    this.hexPublicCertificateSerial = canonical.hexPublicCertificateSerial;
    this.publicCertificateMd5 = canonical.publicCertificateMd5;
    this.publicCertificate = canonical.publicCertificate;
    this.privateKeyMd5 = canonical.privateKeyMd5;
    this.publicCertificateSubject = canonical.publicCertificateSubject;
    this.publicCertificateIssuer = canonical.publicCertificateIssuer;
    this.publicCertificateSerial = canonical.publicCertificateSerial;
    this.publicCertificateNotBefore = canonical.publicCertificateNotBefore;
    this.publicCertificateNotAfter = canonical.publicCertificateNotAfter;
    this.privateKeyPasswordMd5 = canonical.privateKeyPasswordMd5;
    this.privateKey = canonical.privateKey;
    this.privateKeyPassword = canonical.privateKeyPassword;
    this.pkcs12 = canonical.pkcs12;
    this.pkcs12Password = canonical.pkcs12Password;
  }

  /**
   * List AS2 Stations
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<As2Station> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List AS2 Stations
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<As2Station> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List AS2 Stations
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id` and `name`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code As2Station} records, in a {@code ListIterator}
   */
  public static ListIterator<As2Station> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().as2Stations().list(parameters, options);
  }

  /**
   * List AS2 Stations
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<As2Station> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List AS2 Stations
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<As2Station> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show AS2 Station
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static As2Station find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show AS2 Station
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static As2Station find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show AS2 Station
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static As2Station find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show AS2 Station
   *
   * @param id As2 Station ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code As2Station}
   */
  public static As2Station find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().as2Stations().find(id, parameters, options);
  }

  /**
   * Show AS2 Station
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static As2Station get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show AS2 Station
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static As2Station get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create AS2 Station
   *
   * @see #create(HashMap, HashMap)
   */
  public static As2Station create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create AS2 Station
   *
   * @see #create(HashMap, HashMap)
   */
  public static As2Station create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create AS2 Station
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Required. The station's formal AS2 name, unique across all Workspaces on the Site.</li>
   * <li>{@code workspace_id} (Long): ID of the Workspace associated with this AS2 Station.</li>
   * <li>{@code public_certificate} (String): Public certificate used for message security.</li>
   * <li>{@code private_key} (String): PEM-encoded private key matching public_certificate.</li>
   * <li>{@code private_key_password} (String): Password for the PEM-encoded private key.</li>
   * <li>{@code pkcs12} (String): Base64-encoded PKCS#12 (.pfx or .p12) file containing the identity's certificate and private key. Provide this instead of public_certificate and private_key.</li>
   * <li>{@code pkcs12_password} (String): Password for pkcs12. The file and password are used only for import; the extracted certificate and private key are stored as PEM.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code As2Station}
   */
  public static As2Station create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().as2Stations().create(parameters, options);
  }


  /**
   * Update AS2 Station
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static As2Station update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update AS2 Station
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static As2Station update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update AS2 Station
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static As2Station update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update AS2 Station
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): The station's formal AS2 name, unique across all Workspaces on the Site.</li>
   * <li>{@code public_certificate} (String): Public certificate used for message security.</li>
   * <li>{@code private_key} (String): PEM-encoded private key matching public_certificate.</li>
   * <li>{@code private_key_password} (String): Password for the PEM-encoded private key.</li>
   * <li>{@code pkcs12} (String): Base64-encoded PKCS#12 (.pfx or .p12) file containing the identity's replacement certificate and private key. Provide this instead of public_certificate and private_key.</li>
   * <li>{@code pkcs12_password} (String): Password for pkcs12. The file and password are used only for import; the extracted certificate and private key are stored as PEM.</li>
   * </ul>
   *
   * @param id As2 Station ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code As2Station}
   */
  public static As2Station update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().as2Stations().update(id, parameters, options);
  }


  /**
   * Delete AS2 Station
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete AS2 Station
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete AS2 Station
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete AS2 Station
   *
   * @param id As2 Station ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().as2Stations().delete(id, parameters, options);
  }

  /**
   * Delete AS2 Station
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete AS2 Station
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
