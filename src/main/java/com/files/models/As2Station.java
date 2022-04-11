package com.files.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.ModelUtils;
import com.files.util.FilesInputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class As2Station {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public As2Station() {
    this(null, null);
  }

  public As2Station(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public As2Station(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Id of the AS2 Station.
  */
  @Getter
  @Setter
  @JsonProperty("id")
  private Long id;

  /**
  * The station's formal AS2 name.
  */
  @Getter
  @Setter
  @JsonProperty("name")
  private String name;

  /**
  * Public URI for sending AS2 message to.
  */
  @Getter
  @Setter
  @JsonProperty("uri")
  private String uri;

  /**
  * The station's AS2 domain name.
  */
  @Getter
  @Setter
  @JsonProperty("domain")
  private String domain;

  /**
  * Serial of public certificate used for message security in hex format.
  */
  @Getter
  @Setter
  @JsonProperty("hex_public_certificate_serial")
  private String hexPublicCertificateSerial;

  /**
  * MD5 hash of public certificate used for message security.
  */
  @Getter
  @Setter
  @JsonProperty("public_certificate_md5")
  private String publicCertificateMd5;

  /**
  * MD5 hash of private key used for message security.
  */
  @Getter
  @Setter
  @JsonProperty("private_key_md5")
  private String privateKeyMd5;

  /**
  * Subject of public certificate used for message security.
  */
  @Getter
  @Setter
  @JsonProperty("public_certificate_subject")
  private String publicCertificateSubject;

  /**
  * Issuer of public certificate used for message security.
  */
  @Getter
  @Setter
  @JsonProperty("public_certificate_issuer")
  private String publicCertificateIssuer;

  /**
  * Serial of public certificate used for message security.
  */
  @Getter
  @Setter
  @JsonProperty("public_certificate_serial")
  private String publicCertificateSerial;

  /**
  * Not before value of public certificate used for message security.
  */
  @Getter
  @Setter
  @JsonProperty("public_certificate_not_before")
  private String publicCertificateNotBefore;

  /**
  * Not after value of public certificate used for message security.
  */
  @Getter
  @Setter
  @JsonProperty("public_certificate_not_after")
  private String publicCertificateNotAfter;

  /**
  * MD5 hash of private key password used for message security.
  */
  @Getter
  @Setter
  @JsonProperty("private_key_password_md5")
  private String privateKeyPasswordMd5;

  /**
  */
  @Getter
  @Setter
  @JsonProperty("public_certificate")
  private String publicCertificate;

  /**
  */
  @Getter
  @Setter
  @JsonProperty("private_key")
  private String privateKey;

  /**
  */
  @Getter
  @Setter
  @JsonProperty("private_key_password")
  private String privateKeyPassword;

  /**
  * Parameters:
  *   name - string - AS2 Name
  *   public_certificate - string
  *   private_key - string
  *   private_key_password - string
  */
  public As2Station update(HashMap<String, Object> parameters) {
    return update(parameters);
  }

  /**
  */
  public As2Station delete(HashMap<String, Object> parameters) {
    return delete(parameters);
  }

  public void destroy(HashMap<String, Object> parameters) {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    if (parameters.containsKey("id") && parameters.get("id") != null) {
      update(parameters);
    } else {
      As2Station newObject = As2Station.create(parameters, this.options);
    }
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via either the X-Files-Cursor-Next header or the X-Files-Cursor-Prev header.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  */
  public static List<As2Station> list() throws IOException{
    return list(null,null);
  }
  public static List<As2Station> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  public static List<As2Station> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }

    String url = String.format("%s%s/as2_stations", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<As2Station>> typeReference = new TypeReference<List<As2Station>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<As2Station> all() throws IOException {
    return all(null, null);
  }

  public static List<As2Station> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - As2 Station ID.
  */
  public static List<As2Station> find() throws IOException{
    return find(null, null,null);
  }
  public static List<As2Station> find(Long id,  HashMap<String, Object> parameters) throws IOException {
    return find(id, parameters, null);
  }

  public static List<As2Station> find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(null, parameters, options);
  }

  public static List<As2Station> find(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id != null){
      parameters.put("id",id);
    }
    if (parameters.containsKey("id") && !(parameters.get("id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    String url = String.format("%s%s/as2_stations/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<List<As2Station>> typeReference = new TypeReference<List<As2Station>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<As2Station> get() throws IOException {
    return get(null, null, null);
  }

  public static List<As2Station> get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   name (required) - string - AS2 Name
  *   public_certificate (required) - string
  *   private_key (required) - string
  *   private_key_password - string
  */
  public static As2Station create() throws IOException{
    return create(null,null);
  }
  public static As2Station create( HashMap<String, Object> parameters) throws IOException {
    return create(parameters, null);
  }


  public static As2Station create( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("public_certificate") && !(parameters.get("public_certificate") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: public_certificate must be of type String parameters[\"public_certificate\"]");
    }

    if (parameters.containsKey("private_key") && !(parameters.get("private_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: private_key must be of type String parameters[\"private_key\"]");
    }

    if (parameters.containsKey("private_key_password") && !(parameters.get("private_key_password") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: private_key_password must be of type String parameters[\"private_key_password\"]");
    }

    if (!parameters.containsKey("name") || parameters.get("name") == null) {
      throw new NullPointerException("Parameter missing: name parameters[\"name\"]");
    }
    if (!parameters.containsKey("public_certificate") || parameters.get("public_certificate") == null) {
      throw new NullPointerException("Parameter missing: public_certificate parameters[\"public_certificate\"]");
    }
    if (!parameters.containsKey("private_key") || parameters.get("private_key") == null) {
      throw new NullPointerException("Parameter missing: private_key parameters[\"private_key\"]");
    }
    String url = String.format("%s%s/as2_stations", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<As2Station> typeReference = new TypeReference<As2Station>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   name - string - AS2 Name
  *   public_certificate - string
  *   private_key - string
  *   private_key_password - string
  */
  public static As2Station update() throws IOException{
    return update(null, null,null);
  }
  public static As2Station update(Long id,  HashMap<String, Object> parameters) throws IOException {
    return update(id, parameters, null);
  }

  public static As2Station update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return update(null, parameters, options);
  }

  public static As2Station update(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id != null){
      parameters.put("id",id);
    }
    if (parameters.containsKey("id") && !(parameters.get("id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("public_certificate") && !(parameters.get("public_certificate") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: public_certificate must be of type String parameters[\"public_certificate\"]");
    }

    if (parameters.containsKey("private_key") && !(parameters.get("private_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: private_key must be of type String parameters[\"private_key\"]");
    }

    if (parameters.containsKey("private_key_password") && !(parameters.get("private_key_password") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: private_key_password must be of type String parameters[\"private_key_password\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    String url = String.format("%s%s/as2_stations/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<As2Station> typeReference = new TypeReference<As2Station>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  */
  public static As2Station delete() throws IOException{
    return delete(null, null,null);
  }
  public static As2Station delete(Long id,  HashMap<String, Object> parameters) throws IOException {
    return delete(id, parameters, null);
  }

  public static As2Station delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(null, parameters, options);
  }

  public static As2Station delete(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id != null){
      parameters.put("id",id);
    }
    if (parameters.containsKey("id") && !(parameters.get("id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    String url = String.format("%s%s/as2_stations/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<As2Station> typeReference = new TypeReference<As2Station>() {};
    return FilesClient.requestItem(url, RequestMethods.DELETE, typeReference, parameters, options);
  }

  public static As2Station destroy() throws IOException {
    return destroy(null, null, null);
  }

  public static As2Station destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(id, parameters, options);
  }

}


