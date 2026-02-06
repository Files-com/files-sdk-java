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
public class PartnerSiteRequest implements ModelInterface {
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


  public PartnerSiteRequest() {
    this(null, null);
  }

  public PartnerSiteRequest(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public PartnerSiteRequest(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Partner Site Request ID
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
  * Partner ID
  */
  @JsonProperty("partner_id")
  public Long partnerId;

  public Long getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(Long partnerId) {
    this.partnerId = partnerId;
  }

  /**
  * Linked Site ID
  */
  @JsonProperty("linked_site_id")
  public Long linkedSiteId;

  public Long getLinkedSiteId() {
    return linkedSiteId;
  }

  public void setLinkedSiteId(Long linkedSiteId) {
    this.linkedSiteId = linkedSiteId;
  }

  /**
  * Request status (pending, approved, rejected)
  */
  @JsonProperty("status")
  public String status;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  /**
  * Main Site Name
  */
  @JsonProperty("main_site_name")
  public String mainSiteName;

  public String getMainSiteName() {
    return mainSiteName;
  }

  public void setMainSiteName(String mainSiteName) {
    this.mainSiteName = mainSiteName;
  }

  /**
  * Pairing key used to approve this request on the target site
  */
  @JsonProperty("pairing_key")
  public String pairingKey;

  public String getPairingKey() {
    return pairingKey;
  }

  public void setPairingKey(String pairingKey) {
    this.pairingKey = pairingKey;
  }

  /**
  * Request creation date/time
  */
  @JsonProperty("created_at")
  public Date createdAt;

  public Date getCreatedAt() {
    return createdAt;
  }

  /**
  * Request last updated date/time
  */
  @JsonProperty("updated_at")
  public Date updatedAt;

  public Date getUpdatedAt() {
    return updatedAt;
  }

  /**
  * Site URL to link to
  */
  @JsonProperty("site_url")
  public String siteUrl;

  public String getSiteUrl() {
    return siteUrl;
  }

  public void setSiteUrl(String siteUrl) {
    this.siteUrl = siteUrl;
  }

  /**
  * Reject partner site request
  */
  public void reject(HashMap<String, Object> parameters) throws IOException {
    PartnerSiteRequest.reject(this.id, parameters, this.options);
  }

  /**
  * Approve partner site request
  */
  public void approve(HashMap<String, Object> parameters) throws IOException {
    PartnerSiteRequest.approve(this.id, parameters, this.options);
  }

  /**
  */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    PartnerSiteRequest.delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    PartnerSiteRequest.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  */
  public static ListIterator<PartnerSiteRequest> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<PartnerSiteRequest> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<PartnerSiteRequest> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }


    String url = String.format("%s%s/partner_site_requests", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<PartnerSiteRequest>> typeReference = new TypeReference<List<PartnerSiteRequest>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<PartnerSiteRequest> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<PartnerSiteRequest> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   pairing_key (required) - string - Pairing key for the partner site request
  */
  public static void findByPairingKey() throws RuntimeException {
    findByPairingKey(null, null);
  }

  public static void findByPairingKey(HashMap<String, Object> parameters) throws RuntimeException {
    findByPairingKey(parameters, null);
  }


  public static void findByPairingKey(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (!parameters.containsKey("pairing_key") || parameters.get("pairing_key") == null) {
      throw new NullPointerException("Parameter missing: pairing_key parameters[\"pairing_key\"]");
    }

    if (parameters.containsKey("pairing_key") && !(parameters.get("pairing_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: pairing_key must be of type String parameters[\"pairing_key\"]");
    }


    String url = String.format("%s%s/partner_site_requests/find_by_pairing_key", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    FilesClient.apiRequest(url, RequestMethods.GET, parameters, options);
  }


  /**
  * Parameters:
  *   partner_id (required) - int64 - Partner ID to link with
  *   site_url (required) - string - Site URL to link to
  */
  public static PartnerSiteRequest create() throws RuntimeException {
    return create(null, null);
  }

  public static PartnerSiteRequest create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static PartnerSiteRequest create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (!parameters.containsKey("partner_id") || parameters.get("partner_id") == null) {
      throw new NullPointerException("Parameter missing: partner_id parameters[\"partner_id\"]");
    }
    if (!parameters.containsKey("site_url") || parameters.get("site_url") == null) {
      throw new NullPointerException("Parameter missing: site_url parameters[\"site_url\"]");
    }

    if (parameters.containsKey("partner_id") && !(parameters.get("partner_id") instanceof Long || parameters.get("partner_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: partner_id must be of type Long or Integer parameters[\"partner_id\"]");
    }
    if (parameters.containsKey("site_url") && !(parameters.get("site_url") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: site_url must be of type String parameters[\"site_url\"]");
    }


    String url = String.format("%s%s/partner_site_requests", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<PartnerSiteRequest> typeReference = new TypeReference<PartnerSiteRequest>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Reject partner site request
  */
  public static void reject() throws RuntimeException {
    reject(null, null, null);
  }

  public static void reject(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    reject(id, parameters, null);
  }

  public static void reject(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    reject(null, parameters, options);
  }

  public static void reject(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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



    String url = String.format("%s%s/partner_site_requests/%s/reject", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.POST, parameters, options);
  }


  /**
  * Approve partner site request
  */
  public static void approve() throws RuntimeException {
    approve(null, null, null);
  }

  public static void approve(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    approve(id, parameters, null);
  }

  public static void approve(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    approve(null, parameters, options);
  }

  public static void approve(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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



    String url = String.format("%s%s/partner_site_requests/%s/approve", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.POST, parameters, options);
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



    String url = String.format("%s%s/partner_site_requests/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
