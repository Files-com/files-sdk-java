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
public class RemoteBandwidthSnapshot implements ModelInterface {
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

  // One mapper for every RemoteBandwidthSnapshot, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code RemoteBandwidthSnapshot} with no attributes set.
   */
  public RemoteBandwidthSnapshot() {
    this(null, null);
  }

  /**
   * Creates a {@code RemoteBandwidthSnapshot} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public RemoteBandwidthSnapshot(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code RemoteBandwidthSnapshot} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public RemoteBandwidthSnapshot(HashMap<String, Object> parameters, HashMap<String, Object> options) {
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

  /**
   * Gets {@link #id}.
   *
   * @return the value of {@link #id}
   */
  public Long getId() {
    return id;
  }

  /**
   * Site sync bandwidth report bytes received
   */
  @JsonProperty("sync_bytes_received")
  public Long syncBytesReceived;

  /**
   * Gets {@link #syncBytesReceived}.
   *
   * @return the value of {@link #syncBytesReceived}
   */
  public Long getSyncBytesReceived() {
    return syncBytesReceived;
  }

  /**
   * Site sync bandwidth report bytes sent
   */
  @JsonProperty("sync_bytes_sent")
  public Long syncBytesSent;

  /**
   * Gets {@link #syncBytesSent}.
   *
   * @return the value of {@link #syncBytesSent}
   */
  public Long getSyncBytesSent() {
    return syncBytesSent;
  }

  /**
   * Time the site bandwidth report was logged
   */
  @JsonProperty("logged_at")
  public Date loggedAt;

  /**
   * Gets {@link #loggedAt}.
   *
   * @return the value of {@link #loggedAt}
   */
  public Date getLoggedAt() {
    return loggedAt;
  }

  /**
   * ID of related Remote Server
   */
  @JsonProperty("remote_server_id")
  public Long remoteServerId;

  /**
   * Gets {@link #remoteServerId}.
   *
   * @return the value of {@link #remoteServerId}
   */
  public Long getRemoteServerId() {
    return remoteServerId;
  }


  /**
   * List Remote Bandwidth Snapshots
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<RemoteBandwidthSnapshot> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Remote Bandwidth Snapshots
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<RemoteBandwidthSnapshot> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Remote Bandwidth Snapshots
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `logged_at`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `logged_at`.</li>
   * <li>{@code filter_gt} (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `logged_at`.</li>
   * <li>{@code filter_gteq} (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `logged_at`.</li>
   * <li>{@code filter_lt} (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `logged_at`.</li>
   * <li>{@code filter_lteq} (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `logged_at`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code RemoteBandwidthSnapshot} records, in a {@code ListIterator}
   */
  public static ListIterator<RemoteBandwidthSnapshot> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().remoteBandwidthSnapshots().list(parameters, options);
  }

  /**
   * List Remote Bandwidth Snapshots
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<RemoteBandwidthSnapshot> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Remote Bandwidth Snapshots
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<RemoteBandwidthSnapshot> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
