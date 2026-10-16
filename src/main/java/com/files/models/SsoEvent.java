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


  public SsoEvent() {
    this(null, null);
  }

  public SsoEvent(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

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

  public Long getId() {
    return id;
  }

  /**
   * Type of SSO event being recorded.
   */
  @JsonProperty("event_type")
  public String eventType;

  public String getEventType() {
    return eventType;
  }

  /**
   * Status of event.
   */
  @JsonProperty("status")
  public String status;

  public String getStatus() {
    return status;
  }

  /**
   * Event body.
   */
  @JsonProperty("body")
  public String body;

  public String getBody() {
    return body;
  }

  /**
   * Event errors.
   */
  @JsonProperty("event_errors")
  public String[] eventErrors;

  public String[] getEventErrors() {
    return eventErrors;
  }

  /**
   * Event create date/time.
   */
  @JsonProperty("created_at")
  public Date createdAt;

  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * Link to log file.
   */
  @JsonProperty("body_url")
  public String bodyUrl;

  public String getBodyUrl() {
    return bodyUrl;
  }

  /**
   * User ID.
   */
  @JsonProperty("user_id")
  public Long userId;

  public Long getUserId() {
    return userId;
  }

  /**
   * Username on Files.com for the SSO login attempt.
   */
  @JsonProperty("username")
  public String username;

  public String getUsername() {
    return username;
  }

  /**
   * Identity Provider UID for the SSO login attempt.
   */
  @JsonProperty("idp_uid")
  public String idpUid;

  public String getIdpUid() {
    return idpUid;
  }

  /**
   * SSO provider for the SSO login attempt.
   */
  @JsonProperty("provider")
  public String provider;

  public String getProvider() {
    return provider;
  }

  /**
   * SSO provider label for the SSO login attempt.
   */
  @JsonProperty("provider_label")
  public String providerLabel;

  public String getProviderLabel() {
    return providerLabel;
  }

  /**
   * IP address for the SSO login attempt.
   */
  @JsonProperty("ip")
  public String ip;

  public String getIp() {
    return ip;
  }

  /**
   * Region for the SSO login attempt.
   */
  @JsonProperty("region")
  public String region;

  public String getRegion() {
    return region;
  }


  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`, `event_type`, `status` or `user_id`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`, `event_type`, `idp_uid`, `ip`, `provider`, `status`, `user_id` or `username`. Valid field combinations are `[ event_type, created_at ]`, `[ idp_uid, created_at ]`, `[ ip, created_at ]`, `[ provider, created_at ]`, `[ status, created_at ]`, `[ user_id, created_at ]`, `[ username, created_at ]`, `[ event_type, status ]`, `[ idp_uid, status ]`, `[ ip, status ]`, `[ provider, status ]`, `[ user_id, status ]`, `[ username, status ]`, `[ event_type, status, created_at ]`, `[ idp_uid, status, created_at ]`, `[ ip, status, created_at ]`, `[ provider, status, created_at ]`, `[ user_id, status, created_at ]` or `[ username, status, created_at ]`.
   *   filter_gt - object - If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`.
   *   filter_gteq - object - If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`.
   *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `idp_uid`, `ip`, `provider` or `username`.
   *   filter_lt - object - If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`.
   *   filter_lteq - object - If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`.
   */
  public static ListIterator<SsoEvent> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<SsoEvent> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<SsoEvent> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().ssoEvents().list(parameters, options);
  }

  public static ListIterator<SsoEvent> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<SsoEvent> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Sso Event ID.
   */
  public static SsoEvent find() throws RuntimeException {
    return find(null, null, null);
  }

  public static SsoEvent find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static SsoEvent find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static SsoEvent find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().ssoEvents().find(id, parameters, options);
  }

  public static SsoEvent get() throws RuntimeException {
    return get(null, null, null);
  }

  public static SsoEvent get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

}
