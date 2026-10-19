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
public class PartnerSiteRequest implements ModelInterface {
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

  // One mapper for every PartnerSiteRequest, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code PartnerSiteRequest} with no attributes set.
   */
  public PartnerSiteRequest() {
    this(null, null);
  }

  /**
   * Creates a {@code PartnerSiteRequest} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public PartnerSiteRequest(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code PartnerSiteRequest} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public PartnerSiteRequest(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Partner Site Request ID
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
   * Host Partner ID
   */
  @JsonProperty("host_partner_id")
  public Long hostPartnerId;

  /**
   * Gets {@link #hostPartnerId}.
   *
   * @return the value of {@link #hostPartnerId}
   */
  public Long getHostPartnerId() {
    return hostPartnerId;
  }

  /**
   * Sets {@link #hostPartnerId}.
   *
   * @param hostPartnerId the new value
   */
  public void setHostPartnerId(Long hostPartnerId) {
    this.hostPartnerId = hostPartnerId;
  }

  /**
   * Guest Site URL
   */
  @JsonProperty("guest_site_url")
  public String guestSiteUrl;

  /**
   * Gets {@link #guestSiteUrl}.
   *
   * @return the value of {@link #guestSiteUrl}
   */
  public String getGuestSiteUrl() {
    return guestSiteUrl;
  }

  /**
   * Sets {@link #guestSiteUrl}.
   *
   * @param guestSiteUrl the new value
   */
  public void setGuestSiteUrl(String guestSiteUrl) {
    this.guestSiteUrl = guestSiteUrl;
  }

  /**
   * Request status (pending, approved, rejected)
   */
  @JsonProperty("status")
  public String status;

  /**
   * Gets {@link #status}.
   *
   * @return the value of {@link #status}
   */
  public String getStatus() {
    return status;
  }

  /**
   * Sets {@link #status}.
   *
   * @param status the new value
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * Host Site Name
   */
  @JsonProperty("host_site_name")
  public String hostSiteName;

  /**
   * Gets {@link #hostSiteName}.
   *
   * @return the value of {@link #hostSiteName}
   */
  public String getHostSiteName() {
    return hostSiteName;
  }

  /**
   * Sets {@link #hostSiteName}.
   *
   * @param hostSiteName the new value
   */
  public void setHostSiteName(String hostSiteName) {
    this.hostSiteName = hostSiteName;
  }

  /**
   * Pairing key used to approve this request on the Guest Site
   */
  @JsonProperty("pairing_key")
  public String pairingKey;

  /**
   * Gets {@link #pairingKey}.
   *
   * @return the value of {@link #pairingKey}
   */
  public String getPairingKey() {
    return pairingKey;
  }

  /**
   * Sets {@link #pairingKey}.
   *
   * @param pairingKey the new value
   */
  public void setPairingKey(String pairingKey) {
    this.pairingKey = pairingKey;
  }

  /**
   * Request creation date/time
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
   * Request last updated date/time
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
   * Delete Partner Site Request
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().partnerSiteRequests().delete(this.id, parameters, this.options);
  }

  /**
   * Delete Partner Site Request
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Creates this PartnerSiteRequest.
   * On success, this object's attributes are replaced by the server's response.
   * PartnerSiteRequests cannot be updated, so saving one that already has an id throws
   * {@link UnsupportedOperationException}.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      throw new UnsupportedOperationException("The PartnerSiteRequest Object doesn't support updates.");
    }
    refreshFrom(filesClient().partnerSiteRequests().create(parameters, this.options));
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    return ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(PartnerSiteRequest canonical) {
    this.id = canonical.id;
    this.hostPartnerId = canonical.hostPartnerId;
    this.guestSiteUrl = canonical.guestSiteUrl;
    this.status = canonical.status;
    this.hostSiteName = canonical.hostSiteName;
    this.pairingKey = canonical.pairingKey;
    this.createdAt = canonical.createdAt;
    this.updatedAt = canonical.updatedAt;
  }

  /**
   * List Partner Site Requests
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<PartnerSiteRequest> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Partner Site Requests
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<PartnerSiteRequest> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Partner Site Requests
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `host_partner_id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `host_partner_id`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code PartnerSiteRequest} records, in a {@code ListIterator}
   */
  public static ListIterator<PartnerSiteRequest> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().partnerSiteRequests().list(parameters, options);
  }

  /**
   * List Partner Site Requests
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<PartnerSiteRequest> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Partner Site Requests
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<PartnerSiteRequest> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Find partner site request by pairing key
   *
   * @see #findByPairingKey(HashMap, HashMap)
   */
  public static void findByPairingKey() throws RuntimeException {
    findByPairingKey(null, null);
  }

  /**
   * Find partner site request by pairing key
   *
   * @see #findByPairingKey(HashMap, HashMap)
   */
  public static void findByPairingKey(HashMap<String, Object> parameters) throws RuntimeException {
    findByPairingKey(parameters, null);
  }


  /**
   * Find partner site request by pairing key
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code pairing_key} (String): Required. Pairing key for the partner site request</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void findByPairingKey(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().partnerSiteRequests().findByPairingKey(parameters, options);
  }


  /**
   * Create Partner Site Request
   *
   * @see #create(HashMap, HashMap)
   */
  public static PartnerSiteRequest create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Partner Site Request
   *
   * @see #create(HashMap, HashMap)
   */
  public static PartnerSiteRequest create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create Partner Site Request
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code host_partner_id} (Long): Required. Host Partner ID to link with</li>
   * <li>{@code guest_site_url} (String): Required. Guest Site URL to link to</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code PartnerSiteRequest}
   */
  public static PartnerSiteRequest create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().partnerSiteRequests().create(parameters, options);
  }


  /**
   * Reject partner site request
   *
   * @see #reject(HashMap, HashMap)
   */
  public static void reject() throws RuntimeException {
    reject(null, null);
  }

  /**
   * Reject partner site request
   *
   * @see #reject(HashMap, HashMap)
   */
  public static void reject(HashMap<String, Object> parameters) throws RuntimeException {
    reject(parameters, null);
  }


  /**
   * Reject partner site request
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code pairing_key} (String): Required. Pairing key for the partner site request</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void reject(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().partnerSiteRequests().reject(parameters, options);
  }


  /**
   * Approve partner site request
   *
   * @see #approve(HashMap, HashMap)
   */
  public static void approve() throws RuntimeException {
    approve(null, null);
  }

  /**
   * Approve partner site request
   *
   * @see #approve(HashMap, HashMap)
   */
  public static void approve(HashMap<String, Object> parameters) throws RuntimeException {
    approve(parameters, null);
  }


  /**
   * Approve partner site request
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code pairing_key} (String): Required. Pairing key for the partner site request</li>
   * <li>{@code partner_id} (Long): ID of an existing Partner on this site, with the host role, that represents the requesting organization. The connection binds to that Partner and makes it host_and_guest. When omitted, a guest Partner named after the host site is created.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void approve(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().partnerSiteRequests().approve(parameters, options);
  }


  /**
   * Delete Partner Site Request
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Partner Site Request
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Partner Site Request
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Partner Site Request
   *
   * @param id Partner Site Request ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().partnerSiteRequests().delete(id, parameters, options);
  }

  /**
   * Delete Partner Site Request
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Partner Site Request
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
