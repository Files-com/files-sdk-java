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
public class As2Partner implements ModelInterface {
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

  // One mapper for every As2Partner, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code As2Partner} with no attributes set.
   */
  public As2Partner() {
    this(null, null);
  }

  /**
   * Creates a {@code As2Partner} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public As2Partner(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code As2Partner} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public As2Partner(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * ID of the AS2 Partner.
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
   * ID of the Workspace associated with this AS2 Partner.
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
   * ID of the AS2 Station associated with this partner.
   */
  @JsonProperty("as2_station_id")
  public Long as2StationId;

  /**
   * Gets {@link #as2StationId}.
   *
   * @return the value of {@link #as2StationId}
   */
  public Long getAs2StationId() {
    return as2StationId;
  }

  /**
   * Sets {@link #as2StationId}.
   *
   * @param as2StationId the new value
   */
  public void setAs2StationId(Long as2StationId) {
    this.as2StationId = as2StationId;
  }

  /**
   * The partner's formal AS2 name.
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
   * Public URI where we will send the AS2 messages (via HTTP/HTTPS).
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
   * Should we require that the remote HTTP server have a valid SSL Certificate for HTTPS? (This only applies to Outgoing AS2 message from Files.com to a Partner.)
   */
  @JsonProperty("server_certificate")
  public String serverCertificate;

  /**
   * Gets {@link #serverCertificate}.
   *
   * @return the value of {@link #serverCertificate}
   */
  public String getServerCertificate() {
    return serverCertificate;
  }

  /**
   * Sets {@link #serverCertificate}.
   *
   * @param serverCertificate the new value
   */
  public void setServerCertificate(String serverCertificate) {
    this.serverCertificate = serverCertificate;
  }

  /**
   * Username to send to server for HTTP Authentication.
   */
  @JsonProperty("http_auth_username")
  public String httpAuthUsername;

  /**
   * Gets {@link #httpAuthUsername}.
   *
   * @return the value of {@link #httpAuthUsername}
   */
  public String getHttpAuthUsername() {
    return httpAuthUsername;
  }

  /**
   * Sets {@link #httpAuthUsername}.
   *
   * @param httpAuthUsername the new value
   */
  public void setHttpAuthUsername(String httpAuthUsername) {
    this.httpAuthUsername = httpAuthUsername;
  }

  /**
   * Additional HTTP Headers for outgoing message sent to this partner.
   */
  @JsonProperty("additional_http_headers")
  public Object additionalHttpHeaders;

  /**
   * Gets {@link #additionalHttpHeaders}.
   *
   * @return the value of {@link #additionalHttpHeaders}
   */
  public Object getAdditionalHttpHeaders() {
    return additionalHttpHeaders;
  }

  /**
   * Sets {@link #additionalHttpHeaders}.
   *
   * @param additionalHttpHeaders the new value
   */
  public void setAdditionalHttpHeaders(Object additionalHttpHeaders) {
    this.additionalHttpHeaders = additionalHttpHeaders;
  }

  /**
   * Default mime type of the file attached to the encrypted message
   */
  @JsonProperty("default_mime_type")
  public String defaultMimeType;

  /**
   * Gets {@link #defaultMimeType}.
   *
   * @return the value of {@link #defaultMimeType}
   */
  public String getDefaultMimeType() {
    return defaultMimeType;
  }

  /**
   * Sets {@link #defaultMimeType}.
   *
   * @param defaultMimeType the new value
   */
  public void setDefaultMimeType(String defaultMimeType) {
    this.defaultMimeType = defaultMimeType;
  }

  /**
   * How should Files.com evaluate message transfer success based on a partner's MDN response?  This setting does not affect MDN storage; all MDNs received from a partner are always stored. `none`: MDN is stored for informational purposes only, a successful HTTPS transfer is a successful AS2 transfer. `weak`: Inspect the MDN for MIC and Disposition only. `normal`: `weak` plus validate MDN signature matches body, `strict`: `normal` but do not allow signatures from self-signed or incorrectly purposed certificates. `auto`: Automatically set the correct value for this setting based on next mdn received.
   */
  @JsonProperty("mdn_validation_level")
  public String mdnValidationLevel;

  /**
   * Gets {@link #mdnValidationLevel}.
   *
   * @return the value of {@link #mdnValidationLevel}
   */
  public String getMdnValidationLevel() {
    return mdnValidationLevel;
  }

  /**
   * Sets {@link #mdnValidationLevel}.
   *
   * @param mdnValidationLevel the new value
   */
  public void setMdnValidationLevel(String mdnValidationLevel) {
    this.mdnValidationLevel = mdnValidationLevel;
  }

  /**
   * Should Files.com require signatures on incoming AS2 messages?  `normal`: require that incoming messages are signed with a valid matching signature. `none`: Unsigned incoming messages are allowed. `auto`: A one-time starting state. The next message received is always accepted and switches this to `normal` if it carries a valid signature, or `none` if it does not.
   */
  @JsonProperty("signature_validation_level")
  public String signatureValidationLevel;

  /**
   * Gets {@link #signatureValidationLevel}.
   *
   * @return the value of {@link #signatureValidationLevel}
   */
  public String getSignatureValidationLevel() {
    return signatureValidationLevel;
  }

  /**
   * Sets {@link #signatureValidationLevel}.
   *
   * @param signatureValidationLevel the new value
   */
  public void setSignatureValidationLevel(String signatureValidationLevel) {
    this.signatureValidationLevel = signatureValidationLevel;
  }

  /**
   * If `true`, we will use your site's dedicated IPs for all outbound connections to this AS2 Partner.
   */
  @JsonProperty("enable_dedicated_ips")
  public Boolean enableDedicatedIps;

  /**
   * Gets {@link #enableDedicatedIps}.
   *
   * @return the value of {@link #enableDedicatedIps}
   */
  public Boolean getEnableDedicatedIps() {
    return enableDedicatedIps;
  }

  /**
   * Sets {@link #enableDedicatedIps}.
   *
   * @param enableDedicatedIps the new value
   */
  public void setEnableDedicatedIps(Boolean enableDedicatedIps) {
    this.enableDedicatedIps = enableDedicatedIps;
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
   * Password to send to server for HTTP Authentication.
   */
  @JsonProperty("http_auth_password")
  public String httpAuthPassword;

  /**
   * Gets {@link #httpAuthPassword}.
   *
   * @return the value of {@link #httpAuthPassword}
   */
  public String getHttpAuthPassword() {
    return httpAuthPassword;
  }

  /**
   * Sets {@link #httpAuthPassword}.
   *
   * @param httpAuthPassword the new value
   */
  public void setHttpAuthPassword(String httpAuthPassword) {
    this.httpAuthPassword = httpAuthPassword;
  }

  /**
   * Base64-encoded PKCS#12 (.pfx or .p12) file containing the partner's public certificate. Provide this instead of public_certificate. Any private key in the file is discarded. A public-only file must contain exactly one certificate.
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
   * Password for pkcs12. The file and password are used only for import; the extracted public certificate is stored as PEM.
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
   * Updates this As2Partner. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update AS2 Partner
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code enable_dedicated_ips} (Boolean): If `true`, we will use your site's dedicated IPs for all outbound connections to this AS2 Partner.</li>
   * <li>{@code http_auth_username} (String): Username to send to server for HTTP Authentication.</li>
   * <li>{@code http_auth_password} (String): Password to send to server for HTTP Authentication.</li>
   * <li>{@code mdn_validation_level} (String): How should Files.com evaluate message transfer success based on a partner's MDN response?  This setting does not affect MDN storage; all MDNs received from a partner are always stored. `none`: MDN is stored for informational purposes only, a successful HTTPS transfer is a successful AS2 transfer. `weak`: Inspect the MDN for MIC and Disposition only. `normal`: `weak` plus validate MDN signature matches body, `strict`: `normal` but do not allow signatures from self-signed or incorrectly purposed certificates. `auto`: Automatically set the correct value for this setting based on next mdn received.</li>
   * <li>{@code signature_validation_level} (String): Should Files.com require signatures on incoming AS2 messages?  `normal`: require that incoming messages are signed with a valid matching signature. `none`: Unsigned incoming messages are allowed. `auto`: A one-time starting state. The next message received is always accepted and switches this to `normal` if it carries a valid signature, or `none` if it does not.</li>
   * <li>{@code server_certificate} (String): Should we require that the remote HTTP server have a valid SSL Certificate for HTTPS? (This only applies to Outgoing AS2 message from Files.com to a Partner.)</li>
   * <li>{@code default_mime_type} (String): Default mime type of the file attached to the encrypted message</li>
   * <li>{@code additional_http_headers} (Object): Additional HTTP Headers for outgoing message sent to this partner.</li>
   * <li>{@code name} (String): The partner's formal AS2 name.</li>
   * <li>{@code uri} (String): Public URI where we will send the AS2 messages (via HTTP/HTTPS).</li>
   * <li>{@code public_certificate} (String): Public certificate for AS2 Partner.  Note: This is the certificate for AS2 message security, not a certificate used for HTTPS authentication.</li>
   * <li>{@code pkcs12} (String): Base64-encoded PKCS#12 (.pfx or .p12) file containing the partner's public certificate. Provide this instead of public_certificate. Any private key in the file is discarded. A public-only file must contain exactly one certificate.</li>
   * <li>{@code pkcs12_password} (String): Password for pkcs12. The file and password are used only for import; the extracted public certificate is stored as PEM.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code As2Partner}
   */
  public As2Partner update(HashMap<String, Object> parameters) throws IOException {
    As2Partner updated = filesClient().as2Partners().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete AS2 Partner
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().as2Partners().delete(this.id, parameters, this.options);
  }

  /**
   * Delete AS2 Partner
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this As2Partner: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().as2Partners().create(parameters, this.options));
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
  private void refreshFrom(As2Partner canonical) {
    this.id = canonical.id;
    this.workspaceId = canonical.workspaceId;
    this.as2StationId = canonical.as2StationId;
    this.name = canonical.name;
    this.uri = canonical.uri;
    this.serverCertificate = canonical.serverCertificate;
    this.httpAuthUsername = canonical.httpAuthUsername;
    this.additionalHttpHeaders = canonical.additionalHttpHeaders;
    this.defaultMimeType = canonical.defaultMimeType;
    this.mdnValidationLevel = canonical.mdnValidationLevel;
    this.signatureValidationLevel = canonical.signatureValidationLevel;
    this.enableDedicatedIps = canonical.enableDedicatedIps;
    this.hexPublicCertificateSerial = canonical.hexPublicCertificateSerial;
    this.publicCertificate = canonical.publicCertificate;
    this.publicCertificateMd5 = canonical.publicCertificateMd5;
    this.publicCertificateSubject = canonical.publicCertificateSubject;
    this.publicCertificateIssuer = canonical.publicCertificateIssuer;
    this.publicCertificateSerial = canonical.publicCertificateSerial;
    this.publicCertificateNotBefore = canonical.publicCertificateNotBefore;
    this.publicCertificateNotAfter = canonical.publicCertificateNotAfter;
    this.httpAuthPassword = canonical.httpAuthPassword;
    this.pkcs12 = canonical.pkcs12;
    this.pkcs12Password = canonical.pkcs12Password;
  }

  /**
   * List AS2 Partners
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<As2Partner> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List AS2 Partners
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<As2Partner> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List AS2 Partners
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `as2_station_id` or `name`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `as2_station_id` and `workspace_id`. Valid field combinations are `[ workspace_id, as2_station_id ]`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code As2Partner} records, in a {@code ListIterator}
   */
  public static ListIterator<As2Partner> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().as2Partners().list(parameters, options);
  }

  /**
   * List AS2 Partners
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<As2Partner> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List AS2 Partners
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<As2Partner> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show AS2 Partner
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static As2Partner find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show AS2 Partner
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static As2Partner find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show AS2 Partner
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static As2Partner find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show AS2 Partner
   *
   * @param id As2 Partner ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code As2Partner}
   */
  public static As2Partner find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().as2Partners().find(id, parameters, options);
  }

  /**
   * Show AS2 Partner
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static As2Partner get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show AS2 Partner
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static As2Partner get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create AS2 Partner
   *
   * @see #create(HashMap, HashMap)
   */
  public static As2Partner create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create AS2 Partner
   *
   * @see #create(HashMap, HashMap)
   */
  public static As2Partner create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create AS2 Partner
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code enable_dedicated_ips} (Boolean): If `true`, we will use your site's dedicated IPs for all outbound connections to this AS2 Partner.</li>
   * <li>{@code http_auth_username} (String): Username to send to server for HTTP Authentication.</li>
   * <li>{@code http_auth_password} (String): Password to send to server for HTTP Authentication.</li>
   * <li>{@code mdn_validation_level} (String): How should Files.com evaluate message transfer success based on a partner's MDN response?  This setting does not affect MDN storage; all MDNs received from a partner are always stored. `none`: MDN is stored for informational purposes only, a successful HTTPS transfer is a successful AS2 transfer. `weak`: Inspect the MDN for MIC and Disposition only. `normal`: `weak` plus validate MDN signature matches body, `strict`: `normal` but do not allow signatures from self-signed or incorrectly purposed certificates. `auto`: Automatically set the correct value for this setting based on next mdn received.</li>
   * <li>{@code signature_validation_level} (String): Should Files.com require signatures on incoming AS2 messages?  `normal`: require that incoming messages are signed with a valid matching signature. `none`: Unsigned incoming messages are allowed. `auto`: A one-time starting state. The next message received is always accepted and switches this to `normal` if it carries a valid signature, or `none` if it does not.</li>
   * <li>{@code server_certificate} (String): Should we require that the remote HTTP server have a valid SSL Certificate for HTTPS? (This only applies to Outgoing AS2 message from Files.com to a Partner.)</li>
   * <li>{@code default_mime_type} (String): Default mime type of the file attached to the encrypted message</li>
   * <li>{@code additional_http_headers} (Object): Additional HTTP Headers for outgoing message sent to this partner.</li>
   * <li>{@code as2_station_id} (Long): Required. ID of the AS2 Station associated with this partner.</li>
   * <li>{@code name} (String): Required. The partner's formal AS2 name.</li>
   * <li>{@code uri} (String): Required. Public URI where we will send the AS2 messages (via HTTP/HTTPS).</li>
   * <li>{@code public_certificate} (String): Public certificate for AS2 Partner.  Note: This is the certificate for AS2 message security, not a certificate used for HTTPS authentication.</li>
   * <li>{@code pkcs12} (String): Base64-encoded PKCS#12 (.pfx or .p12) file containing the partner's public certificate. Provide this instead of public_certificate. Any private key in the file is discarded. A public-only file must contain exactly one certificate.</li>
   * <li>{@code pkcs12_password} (String): Password for pkcs12. The file and password are used only for import; the extracted public certificate is stored as PEM.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code As2Partner}
   */
  public static As2Partner create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().as2Partners().create(parameters, options);
  }


  /**
   * Update AS2 Partner
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static As2Partner update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update AS2 Partner
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static As2Partner update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update AS2 Partner
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static As2Partner update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update AS2 Partner
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code enable_dedicated_ips} (Boolean): If `true`, we will use your site's dedicated IPs for all outbound connections to this AS2 Partner.</li>
   * <li>{@code http_auth_username} (String): Username to send to server for HTTP Authentication.</li>
   * <li>{@code http_auth_password} (String): Password to send to server for HTTP Authentication.</li>
   * <li>{@code mdn_validation_level} (String): How should Files.com evaluate message transfer success based on a partner's MDN response?  This setting does not affect MDN storage; all MDNs received from a partner are always stored. `none`: MDN is stored for informational purposes only, a successful HTTPS transfer is a successful AS2 transfer. `weak`: Inspect the MDN for MIC and Disposition only. `normal`: `weak` plus validate MDN signature matches body, `strict`: `normal` but do not allow signatures from self-signed or incorrectly purposed certificates. `auto`: Automatically set the correct value for this setting based on next mdn received.</li>
   * <li>{@code signature_validation_level} (String): Should Files.com require signatures on incoming AS2 messages?  `normal`: require that incoming messages are signed with a valid matching signature. `none`: Unsigned incoming messages are allowed. `auto`: A one-time starting state. The next message received is always accepted and switches this to `normal` if it carries a valid signature, or `none` if it does not.</li>
   * <li>{@code server_certificate} (String): Should we require that the remote HTTP server have a valid SSL Certificate for HTTPS? (This only applies to Outgoing AS2 message from Files.com to a Partner.)</li>
   * <li>{@code default_mime_type} (String): Default mime type of the file attached to the encrypted message</li>
   * <li>{@code additional_http_headers} (Object): Additional HTTP Headers for outgoing message sent to this partner.</li>
   * <li>{@code name} (String): The partner's formal AS2 name.</li>
   * <li>{@code uri} (String): Public URI where we will send the AS2 messages (via HTTP/HTTPS).</li>
   * <li>{@code public_certificate} (String): Public certificate for AS2 Partner.  Note: This is the certificate for AS2 message security, not a certificate used for HTTPS authentication.</li>
   * <li>{@code pkcs12} (String): Base64-encoded PKCS#12 (.pfx or .p12) file containing the partner's public certificate. Provide this instead of public_certificate. Any private key in the file is discarded. A public-only file must contain exactly one certificate.</li>
   * <li>{@code pkcs12_password} (String): Password for pkcs12. The file and password are used only for import; the extracted public certificate is stored as PEM.</li>
   * </ul>
   *
   * @param id As2 Partner ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code As2Partner}
   */
  public static As2Partner update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().as2Partners().update(id, parameters, options);
  }


  /**
   * Delete AS2 Partner
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete AS2 Partner
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete AS2 Partner
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete AS2 Partner
   *
   * @param id As2 Partner ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().as2Partners().delete(id, parameters, options);
  }

  /**
   * Delete AS2 Partner
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete AS2 Partner
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
