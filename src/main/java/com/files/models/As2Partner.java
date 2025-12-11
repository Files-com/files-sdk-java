package com.files.models;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.ListIterator;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
import com.files.util.UrlUtils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

  public void setOptions(HashMap<String, Object> options) {
    this.options = options;
  }

  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();


  public As2Partner() {
    this(null, null);
  }

  public As2Partner(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public As2Partner(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
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
  public Map<String, String> additionalHttpHeaders;

  public Map<String, String> getAdditionalHttpHeaders() {
    return additionalHttpHeaders;
  }

  public void setAdditionalHttpHeaders(Map<String, String> additionalHttpHeaders) {
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
  * Should Files.com require signatures on incoming AS2 messages?  `normal`: require that incoming messages are signed with a valid matching signature. `none`: Unsigned incoming messages are allowed. `auto`: Automatically set the correct value for this setting based on next message received.
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
  * Parameters:
  *   enable_dedicated_ips - boolean - If `true`, we will use your site's dedicated IPs for all outbound connections to this AS2 Partner.
  *   http_auth_username - string - Username to send to server for HTTP Authentication.
  *   http_auth_password - string - Password to send to server for HTTP Authentication.
  *   mdn_validation_level - string - How should Files.com evaluate message transfer success based on a partner's MDN response?  This setting does not affect MDN storage; all MDNs received from a partner are always stored. `none`: MDN is stored for informational purposes only, a successful HTTPS transfer is a successful AS2 transfer. `weak`: Inspect the MDN for MIC and Disposition only. `normal`: `weak` plus validate MDN signature matches body, `strict`: `normal` but do not allow signatures from self-signed or incorrectly purposed certificates. `auto`: Automatically set the correct value for this setting based on next mdn received.
  *   signature_validation_level - string - Should Files.com require signatures on incoming AS2 messages?  `normal`: require that incoming messages are signed with a valid matching signature. `none`: Unsigned incoming messages are allowed. `auto`: Automatically set the correct value for this setting based on next message received.
  *   server_certificate - string - Should we require that the remote HTTP server have a valid SSL Certificate for HTTPS? (This only applies to Outgoing AS2 message from Files.com to a Partner.)
  *   default_mime_type - string - Default mime type of the file attached to the encrypted message
  *   additional_http_headers - object - Additional HTTP Headers for outgoing message sent to this partner.
  *   name - string - The partner's formal AS2 name.
  *   uri - string - Public URI where we will send the AS2 messages (via HTTP/HTTPS).
  *   public_certificate - string - Public certificate for AS2 Partner.  Note: This is the certificate for AS2 message security, not a certificate used for HTTPS authentication.
  */
  public As2Partner update(HashMap<String, Object> parameters) throws IOException {
    return As2Partner.update(this.id, parameters, this.options);
  }

  /**
  */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    As2Partner.delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    As2Partner.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `as2_station_id` and `name`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `as2_station_id`.
  */
  public static ListIterator<As2Partner> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<As2Partner> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<As2Partner> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }
    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Map<String, String> parameters[\"sort_by\"]");
    }
    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Map<String, String> parameters[\"filter\"]");
    }


    String url = String.format("%s%s/as2_partners", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<As2Partner>> typeReference = new TypeReference<List<As2Partner>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
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
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }



    String url = String.format("%s%s/as2_partners/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<As2Partner> typeReference = new TypeReference<As2Partner>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
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
  *   signature_validation_level - string - Should Files.com require signatures on incoming AS2 messages?  `normal`: require that incoming messages are signed with a valid matching signature. `none`: Unsigned incoming messages are allowed. `auto`: Automatically set the correct value for this setting based on next message received.
  *   server_certificate - string - Should we require that the remote HTTP server have a valid SSL Certificate for HTTPS? (This only applies to Outgoing AS2 message from Files.com to a Partner.)
  *   default_mime_type - string - Default mime type of the file attached to the encrypted message
  *   additional_http_headers - object - Additional HTTP Headers for outgoing message sent to this partner.
  *   as2_station_id (required) - int64 - ID of the AS2 Station associated with this partner.
  *   name (required) - string - The partner's formal AS2 name.
  *   uri (required) - string - Public URI where we will send the AS2 messages (via HTTP/HTTPS).
  *   public_certificate (required) - string - Public certificate for AS2 Partner.  Note: This is the certificate for AS2 message security, not a certificate used for HTTPS authentication.
  */
  public static As2Partner create() throws RuntimeException {
    return create(null, null);
  }

  public static As2Partner create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static As2Partner create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (!parameters.containsKey("as2_station_id") || parameters.get("as2_station_id") == null) {
      throw new NullPointerException("Parameter missing: as2_station_id parameters[\"as2_station_id\"]");
    }
    if (!parameters.containsKey("name") || parameters.get("name") == null) {
      throw new NullPointerException("Parameter missing: name parameters[\"name\"]");
    }
    if (!parameters.containsKey("uri") || parameters.get("uri") == null) {
      throw new NullPointerException("Parameter missing: uri parameters[\"uri\"]");
    }
    if (!parameters.containsKey("public_certificate") || parameters.get("public_certificate") == null) {
      throw new NullPointerException("Parameter missing: public_certificate parameters[\"public_certificate\"]");
    }

    if (parameters.containsKey("enable_dedicated_ips") && !(parameters.get("enable_dedicated_ips") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: enable_dedicated_ips must be of type Boolean parameters[\"enable_dedicated_ips\"]");
    }
    if (parameters.containsKey("http_auth_username") && !(parameters.get("http_auth_username") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: http_auth_username must be of type String parameters[\"http_auth_username\"]");
    }
    if (parameters.containsKey("http_auth_password") && !(parameters.get("http_auth_password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: http_auth_password must be of type String parameters[\"http_auth_password\"]");
    }
    if (parameters.containsKey("mdn_validation_level") && !(parameters.get("mdn_validation_level") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: mdn_validation_level must be of type String parameters[\"mdn_validation_level\"]");
    }
    if (parameters.containsKey("signature_validation_level") && !(parameters.get("signature_validation_level") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: signature_validation_level must be of type String parameters[\"signature_validation_level\"]");
    }
    if (parameters.containsKey("server_certificate") && !(parameters.get("server_certificate") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: server_certificate must be of type String parameters[\"server_certificate\"]");
    }
    if (parameters.containsKey("default_mime_type") && !(parameters.get("default_mime_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: default_mime_type must be of type String parameters[\"default_mime_type\"]");
    }
    if (parameters.containsKey("additional_http_headers") && !(parameters.get("additional_http_headers") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: additional_http_headers must be of type Map<String, String> parameters[\"additional_http_headers\"]");
    }
    if (parameters.containsKey("as2_station_id") && !(parameters.get("as2_station_id") instanceof Long || parameters.get("as2_station_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: as2_station_id must be of type Long or Integer parameters[\"as2_station_id\"]");
    }
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("uri") && !(parameters.get("uri") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: uri must be of type String parameters[\"uri\"]");
    }
    if (parameters.containsKey("public_certificate") && !(parameters.get("public_certificate") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: public_certificate must be of type String parameters[\"public_certificate\"]");
    }


    String url = String.format("%s%s/as2_partners", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<As2Partner> typeReference = new TypeReference<As2Partner>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   enable_dedicated_ips - boolean - If `true`, we will use your site's dedicated IPs for all outbound connections to this AS2 Partner.
  *   http_auth_username - string - Username to send to server for HTTP Authentication.
  *   http_auth_password - string - Password to send to server for HTTP Authentication.
  *   mdn_validation_level - string - How should Files.com evaluate message transfer success based on a partner's MDN response?  This setting does not affect MDN storage; all MDNs received from a partner are always stored. `none`: MDN is stored for informational purposes only, a successful HTTPS transfer is a successful AS2 transfer. `weak`: Inspect the MDN for MIC and Disposition only. `normal`: `weak` plus validate MDN signature matches body, `strict`: `normal` but do not allow signatures from self-signed or incorrectly purposed certificates. `auto`: Automatically set the correct value for this setting based on next mdn received.
  *   signature_validation_level - string - Should Files.com require signatures on incoming AS2 messages?  `normal`: require that incoming messages are signed with a valid matching signature. `none`: Unsigned incoming messages are allowed. `auto`: Automatically set the correct value for this setting based on next message received.
  *   server_certificate - string - Should we require that the remote HTTP server have a valid SSL Certificate for HTTPS? (This only applies to Outgoing AS2 message from Files.com to a Partner.)
  *   default_mime_type - string - Default mime type of the file attached to the encrypted message
  *   additional_http_headers - object - Additional HTTP Headers for outgoing message sent to this partner.
  *   name - string - The partner's formal AS2 name.
  *   uri - string - Public URI where we will send the AS2 messages (via HTTP/HTTPS).
  *   public_certificate - string - Public certificate for AS2 Partner.  Note: This is the certificate for AS2 message security, not a certificate used for HTTPS authentication.
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
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }
    if (parameters.containsKey("enable_dedicated_ips") && !(parameters.get("enable_dedicated_ips") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: enable_dedicated_ips must be of type Boolean parameters[\"enable_dedicated_ips\"]");
    }
    if (parameters.containsKey("http_auth_username") && !(parameters.get("http_auth_username") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: http_auth_username must be of type String parameters[\"http_auth_username\"]");
    }
    if (parameters.containsKey("http_auth_password") && !(parameters.get("http_auth_password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: http_auth_password must be of type String parameters[\"http_auth_password\"]");
    }
    if (parameters.containsKey("mdn_validation_level") && !(parameters.get("mdn_validation_level") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: mdn_validation_level must be of type String parameters[\"mdn_validation_level\"]");
    }
    if (parameters.containsKey("signature_validation_level") && !(parameters.get("signature_validation_level") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: signature_validation_level must be of type String parameters[\"signature_validation_level\"]");
    }
    if (parameters.containsKey("server_certificate") && !(parameters.get("server_certificate") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: server_certificate must be of type String parameters[\"server_certificate\"]");
    }
    if (parameters.containsKey("default_mime_type") && !(parameters.get("default_mime_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: default_mime_type must be of type String parameters[\"default_mime_type\"]");
    }
    if (parameters.containsKey("additional_http_headers") && !(parameters.get("additional_http_headers") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: additional_http_headers must be of type Map<String, String> parameters[\"additional_http_headers\"]");
    }
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("uri") && !(parameters.get("uri") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: uri must be of type String parameters[\"uri\"]");
    }
    if (parameters.containsKey("public_certificate") && !(parameters.get("public_certificate") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: public_certificate must be of type String parameters[\"public_certificate\"]");
    }



    String url = String.format("%s%s/as2_partners/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<As2Partner> typeReference = new TypeReference<As2Partner>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
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
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }



    String url = String.format("%s%s/as2_partners/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
