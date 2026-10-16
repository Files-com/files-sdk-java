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
public class BandwidthSnapshot implements ModelInterface {
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

  // One mapper for every BandwidthSnapshot, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  public BandwidthSnapshot() {
    this(null, null);
  }

  public BandwidthSnapshot(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public BandwidthSnapshot(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Site bandwidth ID
   */
  @JsonProperty("id")
  public Long id;

  public Long getId() {
    return id;
  }

  /**
   * Site bandwidth report bytes received
   */
  @JsonProperty("bytes_received")
  public Long bytesReceived;

  public Long getBytesReceived() {
    return bytesReceived;
  }

  /**
   * Site bandwidth report bytes sent
   */
  @JsonProperty("bytes_sent")
  public Long bytesSent;

  public Long getBytesSent() {
    return bytesSent;
  }

  /**
   * Site sync bandwidth report bytes received
   */
  @JsonProperty("sync_bytes_received")
  public Long syncBytesReceived;

  public Long getSyncBytesReceived() {
    return syncBytesReceived;
  }

  /**
   * Site sync bandwidth report bytes sent
   */
  @JsonProperty("sync_bytes_sent")
  public Long syncBytesSent;

  public Long getSyncBytesSent() {
    return syncBytesSent;
  }

  /**
   * Site bandwidth report get requests
   */
  @JsonProperty("requests_get")
  public Long requestsGet;

  public Long getRequestsGet() {
    return requestsGet;
  }

  /**
   * Site bandwidth report put requests
   */
  @JsonProperty("requests_put")
  public Long requestsPut;

  public Long getRequestsPut() {
    return requestsPut;
  }

  /**
   * Site bandwidth report other requests
   */
  @JsonProperty("requests_other")
  public Long requestsOther;

  public Long getRequestsOther() {
    return requestsOther;
  }

  /**
   * Time the site bandwidth report was logged
   */
  @JsonProperty("logged_at")
  public Date loggedAt;

  public Date getLoggedAt() {
    return loggedAt;
  }


  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `logged_at`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `logged_at`.
   *   filter_gt - object - If set, return records where the specified field is greater than the supplied value. Valid fields are `logged_at`.
   *   filter_gteq - object - If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `logged_at`.
   *   filter_lt - object - If set, return records where the specified field is less than the supplied value. Valid fields are `logged_at`.
   *   filter_lteq - object - If set, return records where the specified field is less than or equal the supplied value. Valid fields are `logged_at`.
   */
  public static ListIterator<BandwidthSnapshot> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<BandwidthSnapshot> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<BandwidthSnapshot> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().bandwidthSnapshots().list(parameters, options);
  }

  public static ListIterator<BandwidthSnapshot> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<BandwidthSnapshot> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
