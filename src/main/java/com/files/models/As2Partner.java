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
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class As2Partner implements ModelInterface {
  @Setter
  private HashMap<String, Object> options;
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
  @Getter
  @Setter
  @JsonProperty("id")
  public Long id;

  /**
  * ID of the AS2 Station associated with this partner.
  */
  @Getter
  @Setter
  @JsonProperty("as2_station_id")
  public Long as2StationId;

  /**
  * The partner's formal AS2 name.
  */
  @Getter
  @Setter
  @JsonProperty("name")
  public String name;

  /**
  * Public URI where we will send the AS2 messages (via HTTP/HTTPS).
  */
  @Getter
  @Setter
  @JsonProperty("uri")
  public String uri;

  /**
  * Should we require that the remote HTTP server have a valid SSL Certificate for HTTPS?
  */
  @Getter
  @Setter
  @JsonProperty("server_certificate")
  public String serverCertificate;

  /**
  * Username to send to server for HTTP Authentication.
  */
  @Getter
  @Setter
  @JsonProperty("http_auth_username")
  public String httpAuthUsername;

  /**
  * How should Files.com evaluate message transfer success based on a partner's MDN response?  This setting does not affect MDN storage; all MDNs received from a partner are always stored. `none`: MDN is stored for informational purposes only, a successful HTTPS transfer is a successful AS2 transfer. `weak`: Inspect the MDN for MIC and Disposition only. `normal`: `weak` plus validate MDN signature matches body, `strict`: `normal` but do not allow signatures from self-signed or incorrectly purposed certificates.
  */
  @Getter
  @Setter
  @JsonProperty("mdn_validation_level")
  public String mdnValidationLevel;

  /**
  * If `true`, we will use your site's dedicated IPs for all outbound connections to this AS2 PArtner.
  */
  @Getter
  @Setter
  @JsonProperty("enable_dedicated_ips")
  public Boolean enableDedicatedIps;

  /**
  * Serial of public certificate used for message security in hex format.
  */
  @Getter
  @Setter
  @JsonProperty("hex_public_certificate_serial")
  public String hexPublicCertificateSerial;

  /**
  * MD5 hash of public certificate used for message security.
  */
  @Getter
  @Setter
  @JsonProperty("public_certificate_md5")
  public String publicCertificateMd5;

  /**
  * Subject of public certificate used for message security.
  */
  @Getter
  @Setter
  @JsonProperty("public_certificate_subject")
  public String publicCertificateSubject;

  /**
  * Issuer of public certificate used for message security.
  */
  @Getter
  @Setter
  @JsonProperty("public_certificate_issuer")
  public String publicCertificateIssuer;

  /**
  * Serial of public certificate used for message security.
  */
  @Getter
  @Setter
  @JsonProperty("public_certificate_serial")
  public String publicCertificateSerial;

  /**
  * Not before value of public certificate used for message security.
  */
  @Getter
  @Setter
  @JsonProperty("public_certificate_not_before")
  public String publicCertificateNotBefore;

  /**
  * Not after value of public certificate used for message security.
  */
  @Getter
  @Setter
  @JsonProperty("public_certificate_not_after")
  public String publicCertificateNotAfter;

  /**
  * Password to send to server for HTTP Authentication.
  */
  @Getter
  @Setter
  @JsonProperty("http_auth_password")
  public String httpAuthPassword;

  /**
  * Public certificate for AS2 Partner.  Note: This is the certificate for AS2 message security, not a certificate used for HTTPS authentication.
  */
  @Getter
  @Setter
  @JsonProperty("public_certificate")
  public String publicCertificate;

  /**
  * Parameters:
  *   enable_dedicated_ips - boolean - If `true`, we will use your site's dedicated IPs for all outbound connections to this AS2 PArtner.
  *   http_auth_username - string - Username to send to server for HTTP Authentication.
  *   http_auth_password - string - Password to send to server for HTTP Authentication.
  *   mdn_validation_level - string - How should Files.com evaluate message transfer success based on a partner's MDN response?  This setting does not affect MDN storage; all MDNs received from a partner are always stored. `none`: MDN is stored for informational purposes only, a successful HTTPS transfer is a successful AS2 transfer. `weak`: Inspect the MDN for MIC and Disposition only. `normal`: `weak` plus validate MDN signature matches body, `strict`: `normal` but do not allow signatures from self-signed or incorrectly purposed certificates.
  *   server_certificate - string - Should we require that the remote HTTP server have a valid SSL Certificate for HTTPS?
  *   name - string - The partner's formal AS2 name.
  *   uri - string - Public URI where we will send the AS2 messages (via HTTP/HTTPS).
  *   public_certificate - string - Public certificate for AS2 Partner.  Note: This is the certificate for AS2 message security, not a certificate used for HTTPS authentication.
  */
  public As2Partner update() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    return As2Partner.update(this.id, parameters, this.options);
  }

  /**
  */
  public void delete() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    As2Partner.delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete();
  }


  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    As2Partner.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
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
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
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

    if (!(id instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/as2_partners/%s", urlParts);

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
  *   enable_dedicated_ips - boolean - If `true`, we will use your site's dedicated IPs for all outbound connections to this AS2 PArtner.
  *   http_auth_username - string - Username to send to server for HTTP Authentication.
  *   http_auth_password - string - Password to send to server for HTTP Authentication.
  *   mdn_validation_level - string - How should Files.com evaluate message transfer success based on a partner's MDN response?  This setting does not affect MDN storage; all MDNs received from a partner are always stored. `none`: MDN is stored for informational purposes only, a successful HTTPS transfer is a successful AS2 transfer. `weak`: Inspect the MDN for MIC and Disposition only. `normal`: `weak` plus validate MDN signature matches body, `strict`: `normal` but do not allow signatures from self-signed or incorrectly purposed certificates.
  *   server_certificate - string - Should we require that the remote HTTP server have a valid SSL Certificate for HTTPS?
  *   as2_station_id (required) - int64 - ID of the AS2 Station associated with this partner.
  *   name (required) - string - The partner's formal AS2 name.
  *   uri (required) - string - Public URI where we will send the AS2 messages (via HTTP/HTTPS).
  *   public_certificate (required) - string - Public certificate for AS2 Partner.  Note: This is the certificate for AS2 message security, not a certificate used for HTTPS authentication.
  */

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
    if (parameters.containsKey("server_certificate") && !(parameters.get("server_certificate") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: server_certificate must be of type String parameters[\"server_certificate\"]");
    }
    if (parameters.containsKey("as2_station_id") && !(parameters.get("as2_station_id") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: as2_station_id must be of type Long parameters[\"as2_station_id\"]");
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
  *   enable_dedicated_ips - boolean - If `true`, we will use your site's dedicated IPs for all outbound connections to this AS2 PArtner.
  *   http_auth_username - string - Username to send to server for HTTP Authentication.
  *   http_auth_password - string - Password to send to server for HTTP Authentication.
  *   mdn_validation_level - string - How should Files.com evaluate message transfer success based on a partner's MDN response?  This setting does not affect MDN storage; all MDNs received from a partner are always stored. `none`: MDN is stored for informational purposes only, a successful HTTPS transfer is a successful AS2 transfer. `weak`: Inspect the MDN for MIC and Disposition only. `normal`: `weak` plus validate MDN signature matches body, `strict`: `normal` but do not allow signatures from self-signed or incorrectly purposed certificates.
  *   server_certificate - string - Should we require that the remote HTTP server have a valid SSL Certificate for HTTPS?
  *   name - string - The partner's formal AS2 name.
  *   uri - string - Public URI where we will send the AS2 messages (via HTTP/HTTPS).
  *   public_certificate - string - Public certificate for AS2 Partner.  Note: This is the certificate for AS2 message security, not a certificate used for HTTPS authentication.
  */

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

    if (!(id instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
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
    if (parameters.containsKey("server_certificate") && !(parameters.get("server_certificate") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: server_certificate must be of type String parameters[\"server_certificate\"]");
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


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/as2_partners/%s", urlParts);

    TypeReference<As2Partner> typeReference = new TypeReference<As2Partner>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  */

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

    if (!(id instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/as2_partners/%s", urlParts);

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
