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


  public As2Station() {
    this(null, null);
  }

  public As2Station(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /**
   * ID of the Workspace associated with this AS2 Station.
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
   * The station's formal AS2 name.
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
   * Public URI for sending AS2 message to.
   */
  @JsonProperty("uri")
  public String uri;

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  /**
   * The station's AS2 domain name.
   */
  @JsonProperty("domain")
  public String domain;

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  /**
   * Serial of public certificate used for message security in hex format.
   */
  @JsonProperty("hex_public_certificate_serial")
  public String hexPublicCertificateSerial;

  public String getHexPublicCertificateSerial() {
    return hexPublicCertificateSerial;
  }

  public void setHexPublicCertificateSerial(String hexPublicCertificateSerial) {
    this.hexPublicCertificateSerial = hexPublicCertificateSerial;
  }

  /**
   * MD5 hash of public certificate used for message security.
   */
  @JsonProperty("public_certificate_md5")
  public String publicCertificateMd5;

  public String getPublicCertificateMd5() {
    return publicCertificateMd5;
  }

  public void setPublicCertificateMd5(String publicCertificateMd5) {
    this.publicCertificateMd5 = publicCertificateMd5;
  }

  /**
   * Public certificate used for message security.
   */
  @JsonProperty("public_certificate")
  public String publicCertificate;

  public String getPublicCertificate() {
    return publicCertificate;
  }

  public void setPublicCertificate(String publicCertificate) {
    this.publicCertificate = publicCertificate;
  }

  /**
   * MD5 hash of private key used for message security.
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
   * Subject of public certificate used for message security.
   */
  @JsonProperty("public_certificate_subject")
  public String publicCertificateSubject;

  public String getPublicCertificateSubject() {
    return publicCertificateSubject;
  }

  public void setPublicCertificateSubject(String publicCertificateSubject) {
    this.publicCertificateSubject = publicCertificateSubject;
  }

  /**
   * Issuer of public certificate used for message security.
   */
  @JsonProperty("public_certificate_issuer")
  public String publicCertificateIssuer;

  public String getPublicCertificateIssuer() {
    return publicCertificateIssuer;
  }

  public void setPublicCertificateIssuer(String publicCertificateIssuer) {
    this.publicCertificateIssuer = publicCertificateIssuer;
  }

  /**
   * Serial of public certificate used for message security.
   */
  @JsonProperty("public_certificate_serial")
  public String publicCertificateSerial;

  public String getPublicCertificateSerial() {
    return publicCertificateSerial;
  }

  public void setPublicCertificateSerial(String publicCertificateSerial) {
    this.publicCertificateSerial = publicCertificateSerial;
  }

  /**
   * Not before value of public certificate used for message security.
   */
  @JsonProperty("public_certificate_not_before")
  public String publicCertificateNotBefore;

  public String getPublicCertificateNotBefore() {
    return publicCertificateNotBefore;
  }

  public void setPublicCertificateNotBefore(String publicCertificateNotBefore) {
    this.publicCertificateNotBefore = publicCertificateNotBefore;
  }

  /**
   * Not after value of public certificate used for message security.
   */
  @JsonProperty("public_certificate_not_after")
  public String publicCertificateNotAfter;

  public String getPublicCertificateNotAfter() {
    return publicCertificateNotAfter;
  }

  public void setPublicCertificateNotAfter(String publicCertificateNotAfter) {
    this.publicCertificateNotAfter = publicCertificateNotAfter;
  }

  /**
   * MD5 hash of private key password used for message security.
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
   * PEM-encoded private key matching public_certificate.
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
   * Password for the PEM-encoded private key.
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
   * Base64-encoded PKCS#12 (.pfx or .p12) file containing the identity's certificate and private key. Provide this instead of public_certificate and private_key.
   */
  @JsonProperty("pkcs12")
  public String pkcs12;

  public String getPkcs12() {
    return pkcs12;
  }

  public void setPkcs12(String pkcs12) {
    this.pkcs12 = pkcs12;
  }

  /**
   * Password for pkcs12. The file and password are used only for import; the extracted certificate and private key are stored as PEM.
   */
  @JsonProperty("pkcs12_password")
  public String pkcs12Password;

  public String getPkcs12Password() {
    return pkcs12Password;
  }

  public void setPkcs12Password(String pkcs12Password) {
    this.pkcs12Password = pkcs12Password;
  }

  /**
   * Updates this As2Station. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Parameters:
   *   name - string - The station's formal AS2 name.
   *   public_certificate - string - Public certificate used for message security.
   *   private_key - string - PEM-encoded private key matching public_certificate.
   *   private_key_password - string - Password for the PEM-encoded private key.
   *   pkcs12 - string - Base64-encoded PKCS#12 (.pfx or .p12) file containing the identity's replacement certificate and private key. Provide this instead of public_certificate and private_key.
   *   pkcs12_password - string - Password for pkcs12. The file and password are used only for import; the extracted certificate and private key are stored as PEM.
   */
  public As2Station update(HashMap<String, Object> parameters) throws IOException {
    As2Station updated = filesClient().as2Stations().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().as2Stations().delete(this.id, parameters, this.options);
  }

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
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id` and `name`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id`.
   */
  public static ListIterator<As2Station> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<As2Station> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<As2Station> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().as2Stations().list(parameters, options);
  }

  public static ListIterator<As2Station> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<As2Station> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - As2 Station ID.
   */
  public static As2Station find() throws RuntimeException {
    return find(null, null, null);
  }

  public static As2Station find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static As2Station find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static As2Station find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().as2Stations().find(id, parameters, options);
  }

  public static As2Station get() throws RuntimeException {
    return get(null, null, null);
  }

  public static As2Station get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   name (required) - string - The station's formal AS2 name.
   *   workspace_id - int64 - ID of the Workspace associated with this AS2 Station.
   *   public_certificate - string - Public certificate used for message security.
   *   private_key - string - PEM-encoded private key matching public_certificate.
   *   private_key_password - string - Password for the PEM-encoded private key.
   *   pkcs12 - string - Base64-encoded PKCS#12 (.pfx or .p12) file containing the identity's certificate and private key. Provide this instead of public_certificate and private_key.
   *   pkcs12_password - string - Password for pkcs12. The file and password are used only for import; the extracted certificate and private key are stored as PEM.
   */
  public static As2Station create() throws RuntimeException {
    return create(null, null);
  }

  public static As2Station create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static As2Station create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().as2Stations().create(parameters, options);
  }


  /**
   * Parameters:
   *   name - string - The station's formal AS2 name.
   *   public_certificate - string - Public certificate used for message security.
   *   private_key - string - PEM-encoded private key matching public_certificate.
   *   private_key_password - string - Password for the PEM-encoded private key.
   *   pkcs12 - string - Base64-encoded PKCS#12 (.pfx or .p12) file containing the identity's replacement certificate and private key. Provide this instead of public_certificate and private_key.
   *   pkcs12_password - string - Password for pkcs12. The file and password are used only for import; the extracted certificate and private key are stored as PEM.
   */
  public static As2Station update() throws RuntimeException {
    return update(null, null, null);
  }

  public static As2Station update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static As2Station update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static As2Station update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().as2Stations().update(id, parameters, options);
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
    FilesClient.defaultClient().as2Stations().delete(id, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
