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
import com.files.net.ClientRuntime;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DirectConnectionInfo implements ModelInterface {
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

  // One mapper for every DirectConnectionInfo, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code DirectConnectionInfo} with no attributes set.
   */
  public DirectConnectionInfo() {
    this(null, null);
  }

  /**
   * Creates a {@code DirectConnectionInfo} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public DirectConnectionInfo(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code DirectConnectionInfo} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public DirectConnectionInfo(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Direct connection information schema version.
   */
  @JsonProperty("version")
  public Long version;

  /**
   * Gets {@link #version}.
   *
   * @return the value of {@link #version}
   */
  public Long getVersion() {
    return version;
  }

  /**
   * TLS server name (SNI and Host header) for the Agent's direct transfer listener.
   */
  @JsonProperty("server_name")
  public String serverName;

  /**
   * Gets {@link #serverName}.
   *
   * @return the value of {@link #serverName}
   */
  public String getServerName() {
    return serverName;
  }

  /**
   * Validated ip:port candidates that may be dialed over TCP+TLS for this transfer.
   */
  @JsonProperty("addresses")
  public String[] addresses;

  /**
   * Gets {@link #addresses}.
   *
   * @return the value of {@link #addresses}
   */
  public String[] getAddresses() {
    return addresses;
  }

  /**
   * Signed HTTPS URI for direct Agent transfer traffic.
   */
  @JsonProperty("direct_uri")
  public String directUri;

  /**
   * Gets {@link #directUri}.
   *
   * @return the value of {@link #directUri}
   */
  public String getDirectUri() {
    return directUri;
  }

  /**
   * PEM-encoded CA certificate used to verify the Agent's direct transfer TLS certificate.
   */
  @JsonProperty("ca_pem")
  public String caPem;

  /**
   * Gets {@link #caPem}.
   *
   * @return the value of {@link #caPem}
   */
  public String getCaPem() {
    return caPem;
  }


}
