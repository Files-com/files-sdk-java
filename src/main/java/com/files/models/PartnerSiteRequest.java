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


  public PartnerSiteRequest() {
    this(null, null);
  }

  public PartnerSiteRequest(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Host Partner ID
   */
  @JsonProperty("host_partner_id")
  public Long hostPartnerId;

  public Long getHostPartnerId() {
    return hostPartnerId;
  }

  public void setHostPartnerId(Long hostPartnerId) {
    this.hostPartnerId = hostPartnerId;
  }

  /**
   * Guest Site URL
   */
  @JsonProperty("guest_site_url")
  public String guestSiteUrl;

  public String getGuestSiteUrl() {
    return guestSiteUrl;
  }

  public void setGuestSiteUrl(String guestSiteUrl) {
    this.guestSiteUrl = guestSiteUrl;
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
   * Host Site Name
   */
  @JsonProperty("host_site_name")
  public String hostSiteName;

  public String getHostSiteName() {
    return hostSiteName;
  }

  public void setHostSiteName(String hostSiteName) {
    this.hostSiteName = hostSiteName;
  }

  /**
   * Pairing key used to approve this request on the Guest Site
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
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().partnerSiteRequests().delete(this.id, parameters, this.options);
  }

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
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `host_partner_id`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `host_partner_id`.
   */
  public static ListIterator<PartnerSiteRequest> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<PartnerSiteRequest> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<PartnerSiteRequest> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().partnerSiteRequests().list(parameters, options);
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
    FilesClient.defaultClient().partnerSiteRequests().findByPairingKey(parameters, options);
  }


  /**
   * Parameters:
   *   host_partner_id (required) - int64 - Host Partner ID to link with
   *   guest_site_url (required) - string - Guest Site URL to link to
   */
  public static PartnerSiteRequest create() throws RuntimeException {
    return create(null, null);
  }

  public static PartnerSiteRequest create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static PartnerSiteRequest create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().partnerSiteRequests().create(parameters, options);
  }


  /**
   * Parameters:
   *   pairing_key (required) - string - Pairing key for the partner site request
   */
  public static void reject() throws RuntimeException {
    reject(null, null);
  }

  public static void reject(HashMap<String, Object> parameters) throws RuntimeException {
    reject(parameters, null);
  }


  public static void reject(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().partnerSiteRequests().reject(parameters, options);
  }


  /**
   * Parameters:
   *   pairing_key (required) - string - Pairing key for the partner site request
   *   partner_id - int64 - ID of an existing Partner on this site, with the host role, that represents the requesting organization. The connection binds to that Partner and makes it host_and_guest. When omitted, a guest Partner named after the host site is created.
   */
  public static void approve() throws RuntimeException {
    approve(null, null);
  }

  public static void approve(HashMap<String, Object> parameters) throws RuntimeException {
    approve(parameters, null);
  }


  public static void approve(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().partnerSiteRequests().approve(parameters, options);
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
    FilesClient.defaultClient().partnerSiteRequests().delete(id, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
