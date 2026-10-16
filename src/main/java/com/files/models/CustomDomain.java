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
public class CustomDomain implements ModelInterface {
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

  // One mapper for every CustomDomain, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
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
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
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
   * Workspace ID (0 for the default workspace).
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
   * Allow all workspaces to use this default-workspace Custom Domain.
   */
  @JsonProperty("available_to_all_workspaces")
  public Boolean availableToAllWorkspaces;

  public Boolean getAvailableToAllWorkspaces() {
    return availableToAllWorkspaces;
  }

  public void setAvailableToAllWorkspaces(Boolean availableToAllWorkspaces) {
    this.availableToAllWorkspaces = availableToAllWorkspaces;
  }

  /**
   * Eligible public IP addresses for Remote Server outbound connections. Empty when this Custom Domain is not eligible for outbound selection.
   */
  @JsonProperty("outbound_ip_addresses")
  public String[] outboundIpAddresses;

  public String[] getOutboundIpAddresses() {
    return outboundIpAddresses;
  }

  public void setOutboundIpAddresses(String[] outboundIpAddresses) {
    this.outboundIpAddresses = outboundIpAddresses;
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
   * Where this custom domain routes. Can be `site_alias`, `public_hosting`, `s3_endpoint`, or `unassigned` (not routing traffic). Set to `unassigned` automatically when a bound `public_hosting` folder behavior is deleted, and can be set manually via the API for any reason.
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
   * Public Hosting behavior ID when this domain routes to a specific Public Hosting behavior.  Preserved as historical context when `destination` becomes `unassigned`.
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
   * Dedicated public IP addresses allocated to this Custom Domain.
   */
  @JsonProperty("ip_addresses")
  public String[] ipAddresses;

  public String[] getIpAddresses() {
    return ipAddresses;
  }

  public void setIpAddresses(String[] ipAddresses) {
    this.ipAddresses = ipAddresses;
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
   * Updates this CustomDomain. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Parameters:
   *   available_to_all_workspaces - boolean - Allow all workspaces to use this default-workspace Custom Domain.
   *   workspace_id - int64 - Workspace ID (0 for the default workspace).
   *   destination - string - Where this custom domain routes. Can be `site_alias`, `public_hosting`, `s3_endpoint`, or `unassigned` (not routing traffic). Set to `unassigned` automatically when a bound `public_hosting` folder behavior is deleted, and can be set manually via the API for any reason.
   *   folder_behavior_id - int64 - Public Hosting behavior ID when this domain routes to a specific Public Hosting behavior.  Preserved as historical context when `destination` becomes `unassigned`.
   *   ssl_certificate_id - int64 - Current SSL certificate ID.
   *   domain - string - Customer-owned domain name.
   */
  public CustomDomain update(HashMap<String, Object> parameters) throws IOException {
    CustomDomain updated = filesClient().customDomains().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().customDomains().delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this CustomDomain: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().customDomains().create(parameters, this.options));
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
  private void refreshFrom(CustomDomain canonical) {
    this.id = canonical.id;
    this.workspaceId = canonical.workspaceId;
    this.availableToAllWorkspaces = canonical.availableToAllWorkspaces;
    this.outboundIpAddresses = canonical.outboundIpAddresses;
    this.domain = canonical.domain;
    this.destination = canonical.destination;
    this.dnsStatus = canonical.dnsStatus;
    this.sslCertificateId = canonical.sslCertificateId;
    this.brickManaged = canonical.brickManaged;
    this.folderBehaviorId = canonical.folderBehaviorId;
    this.ipAddresses = canonical.ipAddresses;
    this.createdAt = canonical.createdAt;
    this.updatedAt = canonical.updatedAt;
  }

  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `id` or `available_to_all_workspaces`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id`.
   */
  public static ListIterator<CustomDomain> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<CustomDomain> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<CustomDomain> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().customDomains().list(parameters, options);
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
    return FilesClient.defaultClient().customDomains().find(id, parameters, options);
  }

  public static CustomDomain get() throws RuntimeException {
    return get(null, null, null);
  }

  public static CustomDomain get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Custom Domain ID.
   *   count (required) - int64 - Number of dedicated IP addresses to allocate.
   */
  public static CustomDomain createAllocateIp() throws RuntimeException {
    return createAllocateIp(null, null, null);
  }

  public static CustomDomain createAllocateIp(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return createAllocateIp(id, parameters, null);
  }

  public static CustomDomain createAllocateIp(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return createAllocateIp(null, parameters, options);
  }

  public static CustomDomain createAllocateIp(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().customDomains().createAllocateIp(id, parameters, options);
  }


  /**
   * Parameters:
   *   available_to_all_workspaces - boolean - Allow all workspaces to use this default-workspace Custom Domain.
   *   workspace_id - int64 - Workspace ID (0 for the default workspace).
   *   destination - string - Where this custom domain routes. Can be `site_alias`, `public_hosting`, `s3_endpoint`, or `unassigned` (not routing traffic). Set to `unassigned` automatically when a bound `public_hosting` folder behavior is deleted, and can be set manually via the API for any reason.
   *   folder_behavior_id - int64 - Public Hosting behavior ID when this domain routes to a specific Public Hosting behavior.  Preserved as historical context when `destination` becomes `unassigned`.
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
    return FilesClient.defaultClient().customDomains().create(parameters, options);
  }


  /**
   * Parameters:
   *   available_to_all_workspaces - boolean - Allow all workspaces to use this default-workspace Custom Domain.
   *   workspace_id - int64 - Workspace ID (0 for the default workspace).
   *   destination - string - Where this custom domain routes. Can be `site_alias`, `public_hosting`, `s3_endpoint`, or `unassigned` (not routing traffic). Set to `unassigned` automatically when a bound `public_hosting` folder behavior is deleted, and can be set manually via the API for any reason.
   *   folder_behavior_id - int64 - Public Hosting behavior ID when this domain routes to a specific Public Hosting behavior.  Preserved as historical context when `destination` becomes `unassigned`.
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
    return FilesClient.defaultClient().customDomains().update(id, parameters, options);
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
    FilesClient.defaultClient().customDomains().delete(id, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
