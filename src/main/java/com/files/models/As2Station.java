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
public class As2Station implements ModelInterface {
  @Setter
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();


  public As2Station() {
    this(null, null);
  }

  public As2Station(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public As2Station(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Id of the AS2 Station.
  */
  @Getter
  @Setter
  @JsonProperty("id")
  public Long id;

  /**
  * The station's formal AS2 name.
  */
  @Getter
  @Setter
  @JsonProperty("name")
  public String name;

  /**
  * Public URI for sending AS2 message to.
  */
  @Getter
  @Setter
  @JsonProperty("uri")
  public String uri;

  /**
  * The station's AS2 domain name.
  */
  @Getter
  @Setter
  @JsonProperty("domain")
  public String domain;

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
  * MD5 hash of private key used for message security.
  */
  @Getter
  @Setter
  @JsonProperty("private_key_md5")
  public String privateKeyMd5;

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
  * MD5 hash of private key password used for message security.
  */
  @Getter
  @Setter
  @JsonProperty("private_key_password_md5")
  public String privateKeyPasswordMd5;

  /**
  */
  @Getter
  @Setter
  @JsonProperty("public_certificate")
  public String publicCertificate;

  /**
  */
  @Getter
  @Setter
  @JsonProperty("private_key")
  public String privateKey;

  /**
  */
  @Getter
  @Setter
  @JsonProperty("private_key_password")
  public String privateKeyPassword;

  /**
  * Parameters:
  *   name - string - AS2 Name
  *   public_certificate - string
  *   private_key - string
  *   private_key_password - string
  */
  public As2Station update() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    return As2Station.update(this.id, parameters, this.options);
  }

  /**
  */
  public void delete() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    As2Station.delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete();
  }


  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    As2Station.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  */
  public static ListIterator<As2Station> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<As2Station> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<As2Station> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }


    String url = String.format("%s%s/as2_stations", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<As2Station>> typeReference = new TypeReference<List<As2Station>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
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

  public static As2Station find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static As2Station find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static As2Station find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String url = String.format("%s%s/as2_stations/%s", urlParts);

    TypeReference<As2Station> typeReference = new TypeReference<As2Station>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static As2Station get() throws RuntimeException {
    return get(null, null, null);
  }

  public static As2Station get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   name (required) - string - AS2 Name
  *   public_certificate (required) - string
  *   private_key (required) - string
  *   private_key_password - string
  */

  public static As2Station create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static As2Station create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (!parameters.containsKey("name") || parameters.get("name") == null) {
      throw new NullPointerException("Parameter missing: name parameters[\"name\"]");
    }
    if (!parameters.containsKey("public_certificate") || parameters.get("public_certificate") == null) {
      throw new NullPointerException("Parameter missing: public_certificate parameters[\"public_certificate\"]");
    }
    if (!parameters.containsKey("private_key") || parameters.get("private_key") == null) {
      throw new NullPointerException("Parameter missing: private_key parameters[\"private_key\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("public_certificate") && !(parameters.get("public_certificate") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: public_certificate must be of type String parameters[\"public_certificate\"]");
    }
    if (parameters.containsKey("private_key") && !(parameters.get("private_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: private_key must be of type String parameters[\"private_key\"]");
    }
    if (parameters.containsKey("private_key_password") && !(parameters.get("private_key_password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: private_key_password must be of type String parameters[\"private_key_password\"]");
    }


    String url = String.format("%s%s/as2_stations", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<As2Station> typeReference = new TypeReference<As2Station>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  */
  public static Export createExport() throws RuntimeException {
    return createExport(null, null);
  }

  public static Export createExport(HashMap<String, Object> parameters) throws RuntimeException {
    return createExport(parameters, null);
  }


  public static Export createExport(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();





    String url = String.format("%s%s/as2_stations/create_export", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<Export> typeReference = new TypeReference<Export>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   name - string - AS2 Name
  *   public_certificate - string
  *   private_key - string
  *   private_key_password - string
  */

  public static As2Station update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static As2Station update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static As2Station update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("public_certificate") && !(parameters.get("public_certificate") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: public_certificate must be of type String parameters[\"public_certificate\"]");
    }
    if (parameters.containsKey("private_key") && !(parameters.get("private_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: private_key must be of type String parameters[\"private_key\"]");
    }
    if (parameters.containsKey("private_key_password") && !(parameters.get("private_key_password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: private_key_password must be of type String parameters[\"private_key_password\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/as2_stations/%s", urlParts);

    TypeReference<As2Station> typeReference = new TypeReference<As2Station>() {};
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

    String url = String.format("%s%s/as2_stations/%s", urlParts);

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
