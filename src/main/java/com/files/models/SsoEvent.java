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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SsoEvent implements ModelInterface {
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

  // One mapper for every SsoEvent, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code SsoEvent} with no attributes set.
   */
  public SsoEvent() {
    this(null, null);
  }

  /**
   * Creates a {@code SsoEvent} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public SsoEvent(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code SsoEvent} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public SsoEvent(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Event ID
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
   * Type of SSO event being recorded.
   */
  @JsonProperty("event_type")
  public String eventType;

  /**
   * Gets {@link #eventType}.
   *
   * @return the value of {@link #eventType}
   */
  public String getEventType() {
    return eventType;
  }

  /**
   * Status of event.
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
   * Event body.
   */
  @JsonProperty("body")
  public String body;

  /**
   * Gets {@link #body}.
   *
   * @return the value of {@link #body}
   */
  public String getBody() {
    return body;
  }

  /**
   * Event errors.
   */
  @JsonProperty("event_errors")
  public String[] eventErrors;

  /**
   * Gets {@link #eventErrors}.
   *
   * @return the value of {@link #eventErrors}
   */
  public String[] getEventErrors() {
    return eventErrors;
  }

  /**
   * Event create date/time.
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
   * Link to log file.
   */
  @JsonProperty("body_url")
  public String bodyUrl;

  /**
   * Gets {@link #bodyUrl}.
   *
   * @return the value of {@link #bodyUrl}
   */
  public String getBodyUrl() {
    return bodyUrl;
  }

  /**
   * User ID.
   */
  @JsonProperty("user_id")
  public Long userId;

  /**
   * Gets {@link #userId}.
   *
   * @return the value of {@link #userId}
   */
  public Long getUserId() {
    return userId;
  }

  /**
   * Username on Files.com for the SSO login attempt.
   */
  @JsonProperty("username")
  public String username;

  /**
   * Gets {@link #username}.
   *
   * @return the value of {@link #username}
   */
  public String getUsername() {
    return username;
  }

  /**
   * Identity Provider UID for the SSO login attempt.
   */
  @JsonProperty("idp_uid")
  public String idpUid;

  /**
   * Gets {@link #idpUid}.
   *
   * @return the value of {@link #idpUid}
   */
  public String getIdpUid() {
    return idpUid;
  }

  /**
   * SSO provider for the SSO login attempt.
   */
  @JsonProperty("provider")
  public String provider;

  /**
   * Gets {@link #provider}.
   *
   * @return the value of {@link #provider}
   */
  public String getProvider() {
    return provider;
  }

  /**
   * SSO provider label for the SSO login attempt.
   */
  @JsonProperty("provider_label")
  public String providerLabel;

  /**
   * Gets {@link #providerLabel}.
   *
   * @return the value of {@link #providerLabel}
   */
  public String getProviderLabel() {
    return providerLabel;
  }

  /**
   * IP address for the SSO login attempt.
   */
  @JsonProperty("ip")
  public String ip;

  /**
   * Gets {@link #ip}.
   *
   * @return the value of {@link #ip}
   */
  public String getIp() {
    return ip;
  }

  /**
   * Region for the SSO login attempt.
   */
  @JsonProperty("region")
  public String region;

  /**
   * Gets {@link #region}.
   *
   * @return the value of {@link #region}
   */
  public String getRegion() {
    return region;
  }


  /**
   * List SSO Events
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<SsoEvent> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List SSO Events
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<SsoEvent> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List SSO Events
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`, `event_type`, `status` or `user_id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`, `event_type`, `idp_uid`, `ip`, `provider`, `status`, `user_id` or `username`. Valid field combinations are `[ event_type, created_at ]`, `[ idp_uid, created_at ]`, `[ ip, created_at ]`, `[ provider, created_at ]`, `[ status, created_at ]`, `[ user_id, created_at ]`, `[ username, created_at ]`, `[ event_type, status ]`, `[ idp_uid, status ]`, `[ ip, status ]`, `[ provider, status ]`, `[ user_id, status ]`, `[ username, status ]`, `[ event_type, status, created_at ]`, `[ idp_uid, status, created_at ]`, `[ ip, status, created_at ]`, `[ provider, status, created_at ]`, `[ user_id, status, created_at ]` or `[ username, status, created_at ]`.</li>
   * <li>{@code filter_gt} (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_gteq} (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_prefix} (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `idp_uid`, `ip`, `provider` or `username`.</li>
   * <li>{@code filter_lt} (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_lteq} (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code SsoEvent} records, in a {@code ListIterator}
   */
  public static ListIterator<SsoEvent> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().ssoEvents().list(parameters, options);
  }

  /**
   * List SSO Events
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<SsoEvent> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List SSO Events
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<SsoEvent> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show SSO Event
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SsoEvent find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show SSO Event
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SsoEvent find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show SSO Event
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SsoEvent find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show SSO Event
   *
   * @param id Sso Event ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code SsoEvent}
   */
  public static SsoEvent find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().ssoEvents().find(id, parameters, options);
  }

  /**
   * Show SSO Event
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SsoEvent get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show SSO Event
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SsoEvent get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

}
