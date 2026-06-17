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
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
public class CustomDomain implements ModelInterface {
  private HashMap<String, Object> options;

  public void setOptions(HashMap<String, Object> options) {
    this.options = options;
  }

  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  public CustomDomain() {
    this(null, null);
  }

  public CustomDomain(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public CustomDomain(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Custom Domain ID.
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
  * Customer-owned domain name.
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
  * Where this custom domain routes. Can be `site_alias`, `public_hosting`, or `s3_endpoint`.
  */
  @JsonProperty("destination")
  public String destination;

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  /**
  * Current DNS verification status.
  */
  @JsonProperty("dns_status")
  public String dnsStatus;

  public String getDnsStatus() {
    return dnsStatus;
  }

  public void setDnsStatus(String dnsStatus) {
    this.dnsStatus = dnsStatus;
  }

  /**
  * Current SSL certificate ID.
  */
  @JsonProperty("ssl_certificate_id")
  public Long sslCertificateId;

  public Long getSslCertificateId() {
    return sslCertificateId;
  }

  public void setSslCertificateId(Long sslCertificateId) {
    this.sslCertificateId = sslCertificateId;
  }

  /**
  * Is this domain's SSL certificate automatically managed and renewed by Files.com?
  */
  @JsonProperty("brick_managed")
  public Boolean brickManaged;

  public Boolean getBrickManaged() {
    return brickManaged;
  }

  public void setBrickManaged(Boolean brickManaged) {
    this.brickManaged = brickManaged;
  }

  /**
  * Public Hosting behavior ID when this domain routes to a specific Public Hosting behavior.
  */
  @JsonProperty("folder_behavior_id")
  public Long folderBehaviorId;

  public Long getFolderBehaviorId() {
    return folderBehaviorId;
  }

  public void setFolderBehaviorId(Long folderBehaviorId) {
    this.folderBehaviorId = folderBehaviorId;
  }

  /**
  * When this Custom Domain was created.
  */
  @JsonProperty("created_at")
  public Date createdAt;

  public Date getCreatedAt() {
    return createdAt;
  }

  /**
  * When this Custom Domain was last updated.
  */
  @JsonProperty("updated_at")
  public Date updatedAt;

  public Date getUpdatedAt() {
    return updatedAt;
  }

  /**
  * Parameters:
  *   destination - string - Where this custom domain routes. Can be `site_alias`, `public_hosting`, or `s3_endpoint`.
  *   folder_behavior_id - int64 - Public Hosting behavior ID when this domain routes to a specific Public Hosting behavior.
  *   ssl_certificate_id - int64 - Current SSL certificate ID.
  *   domain - string - Customer-owned domain name.
  */
  public CustomDomain update(HashMap<String, Object> parameters) throws IOException {
    return CustomDomain.update(this.id, parameters, this.options);
  }

  /**
  */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    CustomDomain.delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    CustomDomain.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `id`.
  */
  public static ListIterator<CustomDomain> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<CustomDomain> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<CustomDomain> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }
    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Object parameters[\"sort_by\"]");
    }


    String url = String.format("%s%s/custom_domains", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<CustomDomain>> typeReference = new TypeReference<List<CustomDomain>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<CustomDomain> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<CustomDomain> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Custom Domain ID.
  */
  public static CustomDomain find() throws RuntimeException {
    return find(null, null, null);
  }

  public static CustomDomain find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static CustomDomain find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static CustomDomain find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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



    String url = String.format("%s%s/custom_domains/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<CustomDomain> typeReference = new TypeReference<CustomDomain>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static CustomDomain get() throws RuntimeException {
    return get(null, null, null);
  }

  public static CustomDomain get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   destination - string - Where this custom domain routes. Can be `site_alias`, `public_hosting`, or `s3_endpoint`.
  *   folder_behavior_id - int64 - Public Hosting behavior ID when this domain routes to a specific Public Hosting behavior.
  *   ssl_certificate_id - int64 - Current SSL certificate ID.
  *   domain (required) - string - Customer-owned domain name.
  */
  public static CustomDomain create() throws RuntimeException {
    return create(null, null);
  }

  public static CustomDomain create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static CustomDomain create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (!parameters.containsKey("domain") || parameters.get("domain") == null) {
      throw new NullPointerException("Parameter missing: domain parameters[\"domain\"]");
    }

    if (parameters.containsKey("destination") && !(parameters.get("destination") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: destination must be of type String parameters[\"destination\"]");
    }
    if (parameters.containsKey("folder_behavior_id") && !(parameters.get("folder_behavior_id") instanceof Long || parameters.get("folder_behavior_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: folder_behavior_id must be of type Long or Integer parameters[\"folder_behavior_id\"]");
    }
    if (parameters.containsKey("ssl_certificate_id") && !(parameters.get("ssl_certificate_id") instanceof Long || parameters.get("ssl_certificate_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: ssl_certificate_id must be of type Long or Integer parameters[\"ssl_certificate_id\"]");
    }
    if (parameters.containsKey("domain") && !(parameters.get("domain") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: domain must be of type String parameters[\"domain\"]");
    }


    String url = String.format("%s%s/custom_domains", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<CustomDomain> typeReference = new TypeReference<CustomDomain>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   destination - string - Where this custom domain routes. Can be `site_alias`, `public_hosting`, or `s3_endpoint`.
  *   folder_behavior_id - int64 - Public Hosting behavior ID when this domain routes to a specific Public Hosting behavior.
  *   ssl_certificate_id - int64 - Current SSL certificate ID.
  *   domain - string - Customer-owned domain name.
  */
  public static CustomDomain update() throws RuntimeException {
    return update(null, null, null);
  }

  public static CustomDomain update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static CustomDomain update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static CustomDomain update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
    if (parameters.containsKey("destination") && !(parameters.get("destination") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: destination must be of type String parameters[\"destination\"]");
    }
    if (parameters.containsKey("folder_behavior_id") && !(parameters.get("folder_behavior_id") instanceof Long || parameters.get("folder_behavior_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: folder_behavior_id must be of type Long or Integer parameters[\"folder_behavior_id\"]");
    }
    if (parameters.containsKey("ssl_certificate_id") && !(parameters.get("ssl_certificate_id") instanceof Long || parameters.get("ssl_certificate_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: ssl_certificate_id must be of type Long or Integer parameters[\"ssl_certificate_id\"]");
    }
    if (parameters.containsKey("domain") && !(parameters.get("domain") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: domain must be of type String parameters[\"domain\"]");
    }



    String url = String.format("%s%s/custom_domains/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<CustomDomain> typeReference = new TypeReference<CustomDomain>() {};
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



    String url = String.format("%s%s/custom_domains/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
