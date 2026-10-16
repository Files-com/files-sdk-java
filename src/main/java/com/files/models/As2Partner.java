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


  public As2Partner() {
    this(null, null);
  }

  public As2Partner(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /**
   * ID of the Workspace associated with this AS2 Partner.
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
   * ID of the AS2 Station associated with this partner.
   */
  @JsonProperty("as2_station_id")
  public Long as2StationId;

  public Long getAs2StationId() {
    return as2StationId;
  }

  public void setAs2StationId(Long as2StationId) {
    this.as2StationId = as2StationId;
  }

  /**
   * The partner's formal AS2 name.
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
   * Public URI where we will send the AS2 messages (via HTTP/HTTPS).
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
   * Should we require that the remote HTTP server have a valid SSL Certificate for HTTPS? (This only applies to Outgoing AS2 message from Files.com to a Partner.)
   */
  @JsonProperty("server_certificate")
  public String serverCertificate;

  public String getServerCertificate() {
    return serverCertificate;
  }

  public void setServerCertificate(String serverCertificate) {
    this.serverCertificate = serverCertificate;
  }

  /**
   * Username to send to server for HTTP Authentication.
   */
  @JsonProperty("http_auth_username")
  public String httpAuthUsername;

  public String getHttpAuthUsername() {
    return httpAuthUsername;
  }

  public void setHttpAuthUsername(String httpAuthUsername) {
    this.httpAuthUsername = httpAuthUsername;
  }

  /**
   * Additional HTTP Headers for outgoing message sent to this partner.
   */
  @JsonProperty("additional_http_headers")
  public Object additionalHttpHeaders;

  public Object getAdditionalHttpHeaders() {
    return additionalHttpHeaders;
  }

  public void setAdditionalHttpHeaders(Object additionalHttpHeaders) {
    this.additionalHttpHeaders = additionalHttpHeaders;
  }

  /**
   * Default mime type of the file attached to the encrypted message
   */
  @JsonProperty("default_mime_type")
  public String defaultMimeType;

  public String getDefaultMimeType() {
    return defaultMimeType;
  }

  public void setDefaultMimeType(String defaultMimeType) {
    this.defaultMimeType = defaultMimeType;
  }

  /**
   * How should Files.com evaluate message transfer success based on a partner's MDN response?  This setting does not affect MDN storage; all MDNs received from a partner are always stored. `none`: MDN is stored for informational purposes only, a successful HTTPS transfer is a successful AS2 transfer. `weak`: Inspect the MDN for MIC and Disposition only. `normal`: `weak` plus validate MDN signature matches body, `strict`: `normal` but do not allow signatures from self-signed or incorrectly purposed certificates. `auto`: Automatically set the correct value for this setting based on next mdn received.
   */
  @JsonProperty("mdn_validation_level")
  public String mdnValidationLevel;

  public String getMdnValidationLevel() {
    return mdnValidationLevel;
  }

  public void setMdnValidationLevel(String mdnValidationLevel) {
    this.mdnValidationLevel = mdnValidationLevel;
  }

  /**
   * Should Files.com require signatures on incoming AS2 messages?  `normal`: require that incoming messages are signed with a valid matching signature. `none`: Unsigned incoming messages are allowed. `auto`: A one-time starting state. The next message received is always accepted and switches this to `normal` if it carries a valid signature, or `none` if it does not.
   */
  @JsonProperty("signature_validation_level")
  public String signatureValidationLevel;

  public String getSignatureValidationLevel() {
    return signatureValidationLevel;
  }

  public void setSignatureValidationLevel(String signatureValidationLevel) {
    this.signatureValidationLevel = signatureValidationLevel;
  }

  /**
   * If `true`, we will use your site's dedicated IPs for all outbound connections to this AS2 Partner.
   */
  @JsonProperty("enable_dedicated_ips")
  public Boolean enableDedicatedIps;

  public Boolean getEnableDedicatedIps() {
    return enableDedicatedIps;
  }

  public void setEnableDedicatedIps(Boolean enableDedicatedIps) {
    this.enableDedicatedIps = enableDedicatedIps;
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
   * Password to send to server for HTTP Authentication.
   */
  @JsonProperty("http_auth_password")
  public String httpAuthPassword;

  public String getHttpAuthPassword() {
    return httpAuthPassword;
  }

  public void setHttpAuthPassword(String httpAuthPassword) {
    this.httpAuthPassword = httpAuthPassword;
  }

  /**
   * Base64-encoded PKCS#12 (.pfx or .p12) file containing the partner's public certificate. Provide this instead of public_certificate. Any private key in the file is discarded. A public-only file must contain exactly one certificate.
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
   * Password for pkcs12. The file and password are used only for import; the extracted public certificate is stored as PEM.
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
   * Updates this As2Partner. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Parameters:
   *   enable_dedicated_ips - boolean - If `true`, we will use your site's dedicated IPs for all outbound connections to this AS2 Partner.
   *   http_auth_username - string - Username to send to server for HTTP Authentication.
   *   http_auth_password - string - Password to send to server for HTTP Authentication.
   *   mdn_validation_level - string - How should Files.com evaluate message transfer success based on a partner's MDN response?  This setting does not affect MDN storage; all MDNs received from a partner are always stored. `none`: MDN is stored for informational purposes only, a successful HTTPS transfer is a successful AS2 transfer. `weak`: Inspect the MDN for MIC and Disposition only. `normal`: `weak` plus validate MDN signature matches body, `strict`: `normal` but do not allow signatures from self-signed or incorrectly purposed certificates. `auto`: Automatically set the correct value for this setting based on next mdn received.
   *   signature_validation_level - string - Should Files.com require signatures on incoming AS2 messages?  `normal`: require that incoming messages are signed with a valid matching signature. `none`: Unsigned incoming messages are allowed. `auto`: A one-time starting state. The next message received is always accepted and switches this to `normal` if it carries a valid signature, or `none` if it does not.
   *   server_certificate - string - Should we require that the remote HTTP server have a valid SSL Certificate for HTTPS? (This only applies to Outgoing AS2 message from Files.com to a Partner.)
   *   default_mime_type - string - Default mime type of the file attached to the encrypted message
   *   additional_http_headers - object - Additional HTTP Headers for outgoing message sent to this partner.
   *   name - string - The partner's formal AS2 name.
   *   uri - string - Public URI where we will send the AS2 messages (via HTTP/HTTPS).
   *   public_certificate - string - Public certificate for AS2 Partner.  Note: This is the certificate for AS2 message security, not a certificate used for HTTPS authentication.
   *   pkcs12 - string - Base64-encoded PKCS#12 (.pfx or .p12) file containing the partner's public certificate. Provide this instead of public_certificate. Any private key in the file is discarded. A public-only file must contain exactly one certificate.
   *   pkcs12_password - string - Password for pkcs12. The file and password are used only for import; the extracted public certificate is stored as PEM.
   */
  public As2Partner update(HashMap<String, Object> parameters) throws IOException {
    As2Partner updated = filesClient().as2Partners().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().as2Partners().delete(this.id, parameters, this.options);
  }

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
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `as2_station_id` or `name`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `as2_station_id` and `workspace_id`. Valid field combinations are `[ workspace_id, as2_station_id ]`.
   */
  public static ListIterator<As2Partner> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<As2Partner> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<As2Partner> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().as2Partners().list(parameters, options);
  }

  public static ListIterator<As2Partner> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<As2Partner> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - As2 Partner ID.
   */
  public static As2Partner find() throws RuntimeException {
    return find(null, null, null);
  }

  public static As2Partner find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static As2Partner find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static As2Partner find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().as2Partners().find(id, parameters, options);
  }

  public static As2Partner get() throws RuntimeException {
    return get(null, null, null);
  }

  public static As2Partner get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   enable_dedicated_ips - boolean - If `true`, we will use your site's dedicated IPs for all outbound connections to this AS2 Partner.
   *   http_auth_username - string - Username to send to server for HTTP Authentication.
   *   http_auth_password - string - Password to send to server for HTTP Authentication.
   *   mdn_validation_level - string - How should Files.com evaluate message transfer success based on a partner's MDN response?  This setting does not affect MDN storage; all MDNs received from a partner are always stored. `none`: MDN is stored for informational purposes only, a successful HTTPS transfer is a successful AS2 transfer. `weak`: Inspect the MDN for MIC and Disposition only. `normal`: `weak` plus validate MDN signature matches body, `strict`: `normal` but do not allow signatures from self-signed or incorrectly purposed certificates. `auto`: Automatically set the correct value for this setting based on next mdn received.
   *   signature_validation_level - string - Should Files.com require signatures on incoming AS2 messages?  `normal`: require that incoming messages are signed with a valid matching signature. `none`: Unsigned incoming messages are allowed. `auto`: A one-time starting state. The next message received is always accepted and switches this to `normal` if it carries a valid signature, or `none` if it does not.
   *   server_certificate - string - Should we require that the remote HTTP server have a valid SSL Certificate for HTTPS? (This only applies to Outgoing AS2 message from Files.com to a Partner.)
   *   default_mime_type - string - Default mime type of the file attached to the encrypted message
   *   additional_http_headers - object - Additional HTTP Headers for outgoing message sent to this partner.
   *   as2_station_id (required) - int64 - ID of the AS2 Station associated with this partner.
   *   name (required) - string - The partner's formal AS2 name.
   *   uri (required) - string - Public URI where we will send the AS2 messages (via HTTP/HTTPS).
   *   public_certificate - string - Public certificate for AS2 Partner.  Note: This is the certificate for AS2 message security, not a certificate used for HTTPS authentication.
   *   pkcs12 - string - Base64-encoded PKCS#12 (.pfx or .p12) file containing the partner's public certificate. Provide this instead of public_certificate. Any private key in the file is discarded. A public-only file must contain exactly one certificate.
   *   pkcs12_password - string - Password for pkcs12. The file and password are used only for import; the extracted public certificate is stored as PEM.
   */
  public static As2Partner create() throws RuntimeException {
    return create(null, null);
  }

  public static As2Partner create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static As2Partner create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().as2Partners().create(parameters, options);
  }


  /**
   * Parameters:
   *   enable_dedicated_ips - boolean - If `true`, we will use your site's dedicated IPs for all outbound connections to this AS2 Partner.
   *   http_auth_username - string - Username to send to server for HTTP Authentication.
   *   http_auth_password - string - Password to send to server for HTTP Authentication.
   *   mdn_validation_level - string - How should Files.com evaluate message transfer success based on a partner's MDN response?  This setting does not affect MDN storage; all MDNs received from a partner are always stored. `none`: MDN is stored for informational purposes only, a successful HTTPS transfer is a successful AS2 transfer. `weak`: Inspect the MDN for MIC and Disposition only. `normal`: `weak` plus validate MDN signature matches body, `strict`: `normal` but do not allow signatures from self-signed or incorrectly purposed certificates. `auto`: Automatically set the correct value for this setting based on next mdn received.
   *   signature_validation_level - string - Should Files.com require signatures on incoming AS2 messages?  `normal`: require that incoming messages are signed with a valid matching signature. `none`: Unsigned incoming messages are allowed. `auto`: A one-time starting state. The next message received is always accepted and switches this to `normal` if it carries a valid signature, or `none` if it does not.
   *   server_certificate - string - Should we require that the remote HTTP server have a valid SSL Certificate for HTTPS? (This only applies to Outgoing AS2 message from Files.com to a Partner.)
   *   default_mime_type - string - Default mime type of the file attached to the encrypted message
   *   additional_http_headers - object - Additional HTTP Headers for outgoing message sent to this partner.
   *   name - string - The partner's formal AS2 name.
   *   uri - string - Public URI where we will send the AS2 messages (via HTTP/HTTPS).
   *   public_certificate - string - Public certificate for AS2 Partner.  Note: This is the certificate for AS2 message security, not a certificate used for HTTPS authentication.
   *   pkcs12 - string - Base64-encoded PKCS#12 (.pfx or .p12) file containing the partner's public certificate. Provide this instead of public_certificate. Any private key in the file is discarded. A public-only file must contain exactly one certificate.
   *   pkcs12_password - string - Password for pkcs12. The file and password are used only for import; the extracted public certificate is stored as PEM.
   */
  public static As2Partner update() throws RuntimeException {
    return update(null, null, null);
  }

  public static As2Partner update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static As2Partner update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static As2Partner update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().as2Partners().update(id, parameters, options);
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
    FilesClient.defaultClient().as2Partners().delete(id, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
