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
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IpAddress implements ModelInterface {
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

  // One mapper for every IpAddress, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code IpAddress} with no attributes set.
   */
  public IpAddress() {
    this(null, null);
  }

  /**
   * Creates a {@code IpAddress} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public IpAddress(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code IpAddress} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public IpAddress(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Unique label for list; used by Zapier and other integrations.
   */
  @JsonProperty("id")
  public String id;

  /**
   * Gets {@link #id}.
   *
   * @return the value of {@link #id}
   */
  public String getId() {
    return id;
  }

  /**
   * The object that this public IP address list is associated with.
   */
  @JsonProperty("associated_with")
  public String associatedWith;

  /**
   * Gets {@link #associatedWith}.
   *
   * @return the value of {@link #associatedWith}
   */
  public String getAssociatedWith() {
    return associatedWith;
  }

  /**
   * Group ID
   */
  @JsonProperty("group_id")
  public Long groupId;

  /**
   * Gets {@link #groupId}.
   *
   * @return the value of {@link #groupId}
   */
  public Long getGroupId() {
    return groupId;
  }

  /**
   * A list of IP addresses.
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
   * List IP Addresses associated with the current site
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<IpAddress> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List IP Addresses associated with the current site
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<IpAddress> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List IP Addresses associated with the current site
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code IpAddress} records, in a {@code ListIterator}
   */
  public static ListIterator<IpAddress> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().ipAddresses().list(parameters, options);
  }

  /**
   * List IP Addresses associated with the current site
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<IpAddress> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List IP Addresses associated with the current site
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<IpAddress> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * List all possible public SmartFile IP addresses
   *
   * @see #getSmartfileReserved(HashMap, HashMap)
   */
  public static PublicIpAddress getSmartfileReserved() throws RuntimeException {
    return getSmartfileReserved(null, null);
  }

  /**
   * List all possible public SmartFile IP addresses
   *
   * @see #getSmartfileReserved(HashMap, HashMap)
   */
  public static PublicIpAddress getSmartfileReserved(HashMap<String, Object> parameters) throws RuntimeException {
    return getSmartfileReserved(parameters, null);
  }


  /**
   * List all possible public SmartFile IP addresses
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code PublicIpAddress}
   */
  public static PublicIpAddress getSmartfileReserved(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().ipAddresses().getSmartfileReserved(parameters, options);
  }


  /**
   * List all possible public ExaVault IP addresses
   *
   * @see #getExavaultReserved(HashMap, HashMap)
   */
  public static PublicIpAddress getExavaultReserved() throws RuntimeException {
    return getExavaultReserved(null, null);
  }

  /**
   * List all possible public ExaVault IP addresses
   *
   * @see #getExavaultReserved(HashMap, HashMap)
   */
  public static PublicIpAddress getExavaultReserved(HashMap<String, Object> parameters) throws RuntimeException {
    return getExavaultReserved(parameters, null);
  }


  /**
   * List all possible public ExaVault IP addresses
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code PublicIpAddress}
   */
  public static PublicIpAddress getExavaultReserved(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().ipAddresses().getExavaultReserved(parameters, options);
  }


  /**
   * List all possible public IP addresses
   *
   * @see #getReserved(HashMap, HashMap)
   */
  public static PublicIpAddress getReserved() throws RuntimeException {
    return getReserved(null, null);
  }

  /**
   * List all possible public IP addresses
   *
   * @see #getReserved(HashMap, HashMap)
   */
  public static PublicIpAddress getReserved(HashMap<String, Object> parameters) throws RuntimeException {
    return getReserved(parameters, null);
  }


  /**
   * List all possible public IP addresses
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code PublicIpAddress}
   */
  public static PublicIpAddress getReserved(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().ipAddresses().getReserved(parameters, options);
  }


}
