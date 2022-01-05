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

public class As2Partner {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public As2Partner() {
    this(null, null);
  }

  public As2Partner(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public As2Partner(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Id of the AS2 Partner.
  */
  @Getter
  @Setter
  @JsonProperty("id")
  private Long id;

  /**
  * Id of the AS2 Station associated with this partner.
  */
  @Getter
  @Setter
  @JsonProperty("as2_station_id")
  private Long as2StationId;

  /**
  * The partner's formal AS2 name.
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
  * MD5 hash of public certificate used for message security.
  */
  @Getter
  @Setter
  @JsonProperty("public_certificate_md5")
  private String publicCertificateMd5;

  /**
  */
  @Getter
  @Setter
  @JsonProperty("public_certificate")
  private String publicCertificate;

  /**
  * Parameters:
  *   name - string - AS2 Name
  *   uri - string - URL base for AS2 responses
  *   public_certificate - string
  */
  public As2Partner update(HashMap<String, Object> parameters) {
    return update(parameters);
  }

  /**
  */
  public As2Partner delete(HashMap<String, Object> parameters) {
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
      As2Partner newObject = As2Partner.create(parameters, this.options);
    }
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via either the X-Files-Cursor-Next header or the X-Files-Cursor-Prev header.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  */
  public static List<As2Partner> list() throws IOException{
    return list(null,null);
  }
  public static List<As2Partner> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  public static List<As2Partner> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }

    String url = String.format("%s%s/as2_partners", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<As2Partner>> typeReference = new TypeReference<List<As2Partner>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<As2Partner> all() throws IOException {
    return all(null, null);
  }

  public static List<As2Partner> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - As2 Partner ID.
  */
  public static List<As2Partner> find() throws IOException{
    return find(null, null,null);
  }
  public static List<As2Partner> find(Long id,  HashMap<String, Object> parameters) throws IOException {
    return find(id, parameters, null);
  }

  public static List<As2Partner> find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(null, parameters, options);
  }

  public static List<As2Partner> find(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
    String url = String.format("%s%s/as2_partners/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<List<As2Partner>> typeReference = new TypeReference<List<As2Partner>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<As2Partner> get() throws IOException {
    return get(null, null, null);
  }

  public static List<As2Partner> get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   name (required) - string - AS2 Name
  *   uri (required) - string - URL base for AS2 responses
  *   public_certificate (required) - string
  *   as2_station_id (required) - int64 - Id of As2Station for this partner
  */
  public static As2Partner create() throws IOException{
    return create(null,null);
  }
  public static As2Partner create( HashMap<String, Object> parameters) throws IOException {
    return create(parameters, null);
  }


  public static As2Partner create( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("uri") && !(parameters.get("uri") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: uri must be of type String parameters[\"uri\"]");
    }

    if (parameters.containsKey("public_certificate") && !(parameters.get("public_certificate") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: public_certificate must be of type String parameters[\"public_certificate\"]");
    }

    if (parameters.containsKey("as2_station_id") && !(parameters.get("as2_station_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: as2_station_id must be of type Long parameters[\"as2_station_id\"]");
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
    if (!parameters.containsKey("as2_station_id") || parameters.get("as2_station_id") == null) {
      throw new NullPointerException("Parameter missing: as2_station_id parameters[\"as2_station_id\"]");
    }
    String url = String.format("%s%s/as2_partners", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<As2Partner> typeReference = new TypeReference<As2Partner>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   name - string - AS2 Name
  *   uri - string - URL base for AS2 responses
  *   public_certificate - string
  */
  public static As2Partner update() throws IOException{
    return update(null, null,null);
  }
  public static As2Partner update(Long id,  HashMap<String, Object> parameters) throws IOException {
    return update(id, parameters, null);
  }

  public static As2Partner update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return update(null, parameters, options);
  }

  public static As2Partner update(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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

    if (parameters.containsKey("uri") && !(parameters.get("uri") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: uri must be of type String parameters[\"uri\"]");
    }

    if (parameters.containsKey("public_certificate") && !(parameters.get("public_certificate") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: public_certificate must be of type String parameters[\"public_certificate\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    String url = String.format("%s%s/as2_partners/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<As2Partner> typeReference = new TypeReference<As2Partner>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  */
  public static As2Partner delete() throws IOException{
    return delete(null, null,null);
  }
  public static As2Partner delete(Long id,  HashMap<String, Object> parameters) throws IOException {
    return delete(id, parameters, null);
  }

  public static As2Partner delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(null, parameters, options);
  }

  public static As2Partner delete(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
    String url = String.format("%s%s/as2_partners/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<As2Partner> typeReference = new TypeReference<As2Partner>() {};
    return FilesClient.requestItem(url, RequestMethods.DELETE, typeReference, parameters, options);
  }

  public static As2Partner destroy() throws IOException {
    return destroy(null, null, null);
  }

  public static As2Partner destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(id, parameters, options);
  }

}


