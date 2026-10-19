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
import java.util.Date;
import java.util.HashMap;

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

  // One mapper for every CustomDomain, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code CustomDomain} with no attributes set.
   */
  public CustomDomain() {
    this(null, null);
  }

  /**
   * Creates a {@code CustomDomain} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public CustomDomain(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code CustomDomain} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
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
   * Workspace ID (0 for the default workspace).
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
   * Allow all workspaces to use this default-workspace Custom Domain.
   */
  @JsonProperty("available_to_all_workspaces")
  public Boolean availableToAllWorkspaces;

  /**
   * Gets {@link #availableToAllWorkspaces}.
   *
   * @return the value of {@link #availableToAllWorkspaces}
   */
  public Boolean getAvailableToAllWorkspaces() {
    return availableToAllWorkspaces;
  }

  /**
   * Sets {@link #availableToAllWorkspaces}.
   *
   * @param availableToAllWorkspaces the new value
   */
  public void setAvailableToAllWorkspaces(Boolean availableToAllWorkspaces) {
    this.availableToAllWorkspaces = availableToAllWorkspaces;
  }

  /**
   * Eligible public IP addresses for Remote Server outbound connections. Empty when this Custom Domain is not eligible for outbound selection.
   */
  @JsonProperty("outbound_ip_addresses")
  public String[] outboundIpAddresses;

  /**
   * Gets {@link #outboundIpAddresses}.
   *
   * @return the value of {@link #outboundIpAddresses}
   */
  public String[] getOutboundIpAddresses() {
    return outboundIpAddresses;
  }

  /**
   * Sets {@link #outboundIpAddresses}.
   *
   * @param outboundIpAddresses the new value
   */
  public void setOutboundIpAddresses(String[] outboundIpAddresses) {
    this.outboundIpAddresses = outboundIpAddresses;
  }

  /**
   * Customer-owned domain name.
   */
  @JsonProperty("domain")
  public String domain;

  /**
   * Gets {@link #domain}.
   *
   * @return the value of {@link #domain}
   */
  public String getDomain() {
    return domain;
  }

  /**
   * Sets {@link #domain}.
   *
   * @param domain the new value
   */
  public void setDomain(String domain) {
    this.domain = domain;
  }

  /**
   * Where this custom domain routes. Can be `site_alias`, `public_hosting`, `s3_endpoint`, or `unassigned` (not routing traffic). Set to `unassigned` automatically when a bound `public_hosting` folder behavior is deleted, and can be set manually via the API for any reason.
   */
  @JsonProperty("destination")
  public String destination;

  /**
   * Gets {@link #destination}.
   *
   * @return the value of {@link #destination}
   */
  public String getDestination() {
    return destination;
  }

  /**
   * Sets {@link #destination}.
   *
   * @param destination the new value
   */
  public void setDestination(String destination) {
    this.destination = destination;
  }

  /**
   * Current DNS verification status.
   */
  @JsonProperty("dns_status")
  public String dnsStatus;

  /**
   * Gets {@link #dnsStatus}.
   *
   * @return the value of {@link #dnsStatus}
   */
  public String getDnsStatus() {
    return dnsStatus;
  }

  /**
   * Sets {@link #dnsStatus}.
   *
   * @param dnsStatus the new value
   */
  public void setDnsStatus(String dnsStatus) {
    this.dnsStatus = dnsStatus;
  }

  /**
   * Current SSL certificate ID.
   */
  @JsonProperty("ssl_certificate_id")
  public Long sslCertificateId;

  /**
   * Gets {@link #sslCertificateId}.
   *
   * @return the value of {@link #sslCertificateId}
   */
  public Long getSslCertificateId() {
    return sslCertificateId;
  }

  /**
   * Sets {@link #sslCertificateId}.
   *
   * @param sslCertificateId the new value
   */
  public void setSslCertificateId(Long sslCertificateId) {
    this.sslCertificateId = sslCertificateId;
  }

  /**
   * Is this domain's SSL certificate automatically managed and renewed by Files.com?
   */
  @JsonProperty("brick_managed")
  public Boolean brickManaged;

  /**
   * Gets {@link #brickManaged}.
   *
   * @return the value of {@link #brickManaged}
   */
  public Boolean getBrickManaged() {
    return brickManaged;
  }

  /**
   * Sets {@link #brickManaged}.
   *
   * @param brickManaged the new value
   */
  public void setBrickManaged(Boolean brickManaged) {
    this.brickManaged = brickManaged;
  }

  /**
   * Public Hosting behavior ID when this domain routes to a specific Public Hosting behavior.  Preserved as historical context when `destination` becomes `unassigned`.
   */
  @JsonProperty("folder_behavior_id")
  public Long folderBehaviorId;

  /**
   * Gets {@link #folderBehaviorId}.
   *
   * @return the value of {@link #folderBehaviorId}
   */
  public Long getFolderBehaviorId() {
    return folderBehaviorId;
  }

  /**
   * Sets {@link #folderBehaviorId}.
   *
   * @param folderBehaviorId the new value
   */
  public void setFolderBehaviorId(Long folderBehaviorId) {
    this.folderBehaviorId = folderBehaviorId;
  }

  /**
   * Dedicated public IP addresses allocated to this Custom Domain.
   */
  @JsonProperty("ip_addresses")
  public String[] ipAddresses;

  /**
   * Gets {@link #ipAddresses}.
   *
   * @return the value of {@link #ipAddresses}
   */
  public String[] getIpAddresses() {
    return ipAddresses;
  }

  /**
   * Sets {@link #ipAddresses}.
   *
   * @param ipAddresses the new value
   */
  public void setIpAddresses(String[] ipAddresses) {
    this.ipAddresses = ipAddresses;
  }

  /**
   * When this Custom Domain was created.
   */
  @JsonProperty("created_at")
  public Date createdAt;

  /**
   * Gets {@link #createdAt}.
   *
   * @return the value of {@link #createdAt}
   */
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * When this Custom Domain was last updated.
   */
  @JsonProperty("updated_at")
  public Date updatedAt;

  /**
   * Gets {@link #updatedAt}.
   *
   * @return the value of {@link #updatedAt}
   */
  public Date getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Updates this CustomDomain. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update Custom Domain
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code available_to_all_workspaces} (Boolean): Allow all workspaces to use this default-workspace Custom Domain.</li>
   * <li>{@code workspace_id} (Long): Workspace ID (0 for the default workspace).</li>
   * <li>{@code destination} (String): Where this custom domain routes. Can be `site_alias`, `public_hosting`, `s3_endpoint`, or `unassigned` (not routing traffic). Set to `unassigned` automatically when a bound `public_hosting` folder behavior is deleted, and can be set manually via the API for any reason.</li>
   * <li>{@code folder_behavior_id} (Long): Public Hosting behavior ID when this domain routes to a specific Public Hosting behavior.  Preserved as historical context when `destination` becomes `unassigned`.</li>
   * <li>{@code ssl_certificate_id} (Long): Current SSL certificate ID.</li>
   * <li>{@code domain} (String): Customer-owned domain name.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code CustomDomain}
   */
  public CustomDomain update(HashMap<String, Object> parameters) throws IOException {
    CustomDomain updated = filesClient().customDomains().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete Custom Domain
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().customDomains().delete(this.id, parameters, this.options);
  }

  /**
   * Delete Custom Domain
   *
   * @see #delete(HashMap)
   */
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
   * List Custom Domains
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<CustomDomain> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Custom Domains
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<CustomDomain> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Custom Domains
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `id` or `available_to_all_workspaces`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code CustomDomain} records, in a {@code ListIterator}
   */
  public static ListIterator<CustomDomain> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().customDomains().list(parameters, options);
  }

  /**
   * List Custom Domains
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<CustomDomain> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Custom Domains
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<CustomDomain> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Custom Domain
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static CustomDomain find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Custom Domain
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static CustomDomain find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Custom Domain
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static CustomDomain find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Custom Domain
   *
   * @param id Custom Domain ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code CustomDomain}
   */
  public static CustomDomain find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().customDomains().find(id, parameters, options);
  }

  /**
   * Show Custom Domain
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static CustomDomain get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Custom Domain
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static CustomDomain get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Allocate dedicated IP addresses to this Custom Domain
   *
   * @see #createAllocateIp(Long, HashMap, HashMap)
   */
  public static CustomDomain createAllocateIp() throws RuntimeException {
    return createAllocateIp(null, null, null);
  }

  /**
   * Allocate dedicated IP addresses to this Custom Domain
   *
   * @see #createAllocateIp(Long, HashMap, HashMap)
   */
  public static CustomDomain createAllocateIp(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return createAllocateIp(id, parameters, null);
  }

  /**
   * Allocate dedicated IP addresses to this Custom Domain
   *
   * @see #createAllocateIp(Long, HashMap, HashMap)
   */
  public static CustomDomain createAllocateIp(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return createAllocateIp(null, parameters, options);
  }

  /**
   * Allocate dedicated IP addresses to this Custom Domain
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code count} (Long): Required. Number of dedicated IP addresses to allocate.</li>
   * </ul>
   *
   * @param id Custom Domain ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code CustomDomain}
   */
  public static CustomDomain createAllocateIp(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().customDomains().createAllocateIp(id, parameters, options);
  }


  /**
   * Create Custom Domain
   *
   * @see #create(HashMap, HashMap)
   */
  public static CustomDomain create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Custom Domain
   *
   * @see #create(HashMap, HashMap)
   */
  public static CustomDomain create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create Custom Domain
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code available_to_all_workspaces} (Boolean): Allow all workspaces to use this default-workspace Custom Domain.</li>
   * <li>{@code workspace_id} (Long): Workspace ID (0 for the default workspace).</li>
   * <li>{@code destination} (String): Where this custom domain routes. Can be `site_alias`, `public_hosting`, `s3_endpoint`, or `unassigned` (not routing traffic). Set to `unassigned` automatically when a bound `public_hosting` folder behavior is deleted, and can be set manually via the API for any reason.</li>
   * <li>{@code folder_behavior_id} (Long): Public Hosting behavior ID when this domain routes to a specific Public Hosting behavior.  Preserved as historical context when `destination` becomes `unassigned`.</li>
   * <li>{@code ssl_certificate_id} (Long): Current SSL certificate ID.</li>
   * <li>{@code domain} (String): Required. Customer-owned domain name.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code CustomDomain}
   */
  public static CustomDomain create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().customDomains().create(parameters, options);
  }


  /**
   * Update Custom Domain
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static CustomDomain update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Custom Domain
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static CustomDomain update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Custom Domain
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static CustomDomain update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Custom Domain
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code available_to_all_workspaces} (Boolean): Allow all workspaces to use this default-workspace Custom Domain.</li>
   * <li>{@code workspace_id} (Long): Workspace ID (0 for the default workspace).</li>
   * <li>{@code destination} (String): Where this custom domain routes. Can be `site_alias`, `public_hosting`, `s3_endpoint`, or `unassigned` (not routing traffic). Set to `unassigned` automatically when a bound `public_hosting` folder behavior is deleted, and can be set manually via the API for any reason.</li>
   * <li>{@code folder_behavior_id} (Long): Public Hosting behavior ID when this domain routes to a specific Public Hosting behavior.  Preserved as historical context when `destination` becomes `unassigned`.</li>
   * <li>{@code ssl_certificate_id} (Long): Current SSL certificate ID.</li>
   * <li>{@code domain} (String): Customer-owned domain name.</li>
   * </ul>
   *
   * @param id Custom Domain ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code CustomDomain}
   */
  public static CustomDomain update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().customDomains().update(id, parameters, options);
  }


  /**
   * Delete Custom Domain
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Custom Domain
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Custom Domain
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Custom Domain
   *
   * @param id Custom Domain ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().customDomains().delete(id, parameters, options);
  }

  /**
   * Delete Custom Domain
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Custom Domain
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
