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
public class RemoteServerConfigurationFile implements ModelInterface {
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

  // One mapper for every RemoteServerConfigurationFile, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code RemoteServerConfigurationFile} with no attributes set.
   */
  public RemoteServerConfigurationFile() {
    this(null, null);
  }

  /**
   * Creates a {@code RemoteServerConfigurationFile} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public RemoteServerConfigurationFile(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code RemoteServerConfigurationFile} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public RemoteServerConfigurationFile(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * The remote server ID of the agent
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
   * The permission set for the agent ['read_write', 'read_only', 'write_only']
   */
  @JsonProperty("permission_set")
  public String permissionSet;

  /**
   * Gets {@link #permissionSet}.
   *
   * @return the value of {@link #permissionSet}
   */
  public String getPermissionSet() {
    return permissionSet;
  }

  /**
   * The private key for the agent
   */
  @JsonProperty("private_key")
  public String privateKey;

  /**
   * Gets {@link #privateKey}.
   *
   * @return the value of {@link #privateKey}
   */
  public String getPrivateKey() {
    return privateKey;
  }

  /**
   * Files.com subdomain site name
   */
  @JsonProperty("subdomain")
  public String subdomain;

  /**
   * Gets {@link #subdomain}.
   *
   * @return the value of {@link #subdomain}
   */
  public String getSubdomain() {
    return subdomain;
  }

  /**
   * The root directory for the agent
   */
  @JsonProperty("root")
  public String root;

  /**
   * Gets {@link #root}.
   *
   * @return the value of {@link #root}
   */
  public String getRoot() {
    return root;
  }

  /**
   * Optional customer-facing name for this Agent installation
   */
  @JsonProperty("node_name")
  public String nodeName;

  /**
   * Gets {@link #nodeName}.
   *
   * @return the value of {@link #nodeName}
   */
  public String getNodeName() {
    return nodeName;
  }

  /**
   * Follow symlinks when traversing directories
   */
  @JsonProperty("follow_links")
  public Boolean followLinks;

  /**
   * Gets {@link #followLinks}.
   *
   * @return the value of {@link #followLinks}
   */
  public Boolean getFollowLinks() {
    return followLinks;
  }

  /**
   * Preferred network protocol ['udp', 'tcp'] (default udp)
   */
  @JsonProperty("prefer_protocol")
  public String preferProtocol;

  /**
   * Gets {@link #preferProtocol}.
   *
   * @return the value of {@link #preferProtocol}
   */
  public String getPreferProtocol() {
    return preferProtocol;
  }

  /**
   * DNS lookup method ['auto','doh','system'] (default auto)
   */
  @JsonProperty("dns")
  public String dns;

  /**
   * Gets {@link #dns}.
   *
   * @return the value of {@link #dns}
   */
  public String getDns() {
    return dns;
  }

  /**
   * Proxy all outbound traffic through files.com proxy server
   */
  @JsonProperty("proxy_all_outbound")
  public Boolean proxyAllOutbound;

  /**
   * Gets {@link #proxyAllOutbound}.
   *
   * @return the value of {@link #proxyAllOutbound}
   */
  public Boolean getProxyAllOutbound() {
    return proxyAllOutbound;
  }

  /**
   * Custom site endpoint URL
   */
  @JsonProperty("endpoint_override")
  public String endpointOverride;

  /**
   * Gets {@link #endpointOverride}.
   *
   * @return the value of {@link #endpointOverride}
   */
  public String getEndpointOverride() {
    return endpointOverride;
  }

  /**
   * Log file name and location
   */
  @JsonProperty("log_file")
  public String logFile;

  /**
   * Gets {@link #logFile}.
   *
   * @return the value of {@link #logFile}
   */
  public String getLogFile() {
    return logFile;
  }

  /**
   * Log level for the agent logs ['debug', 'info', 'warn', 'error', 'fatal'] (default info)
   */
  @JsonProperty("log_level")
  public String logLevel;

  /**
   * Gets {@link #logLevel}.
   *
   * @return the value of {@link #logLevel}
   */
  public String getLogLevel() {
    return logLevel;
  }

  /**
   * Log route for agent logs. (default 5)
   */
  @JsonProperty("log_rotate_num")
  public Long logRotateNum;

  /**
   * Gets {@link #logRotateNum}.
   *
   * @return the value of {@link #logRotateNum}
   */
  public Long getLogRotateNum() {
    return logRotateNum;
  }

  /**
   * Log route size in MB for agent logs. (default 20)
   */
  @JsonProperty("log_rotate_size")
  public Long logRotateSize;

  /**
   * Gets {@link #logRotateSize}.
   *
   * @return the value of {@link #logRotateSize}
   */
  public Long getLogRotateSize() {
    return logRotateSize;
  }

  /**
   * Maximum number of concurrent jobs (default 500)
   */
  @JsonProperty("override_max_concurrent_jobs")
  public Long overrideMaxConcurrentJobs;

  /**
   * Gets {@link #overrideMaxConcurrentJobs}.
   *
   * @return the value of {@link #overrideMaxConcurrentJobs}
   */
  public Long getOverrideMaxConcurrentJobs() {
    return overrideMaxConcurrentJobs;
  }

  /**
   * Graceful shutdown timeout in seconds (default 15)
   */
  @JsonProperty("graceful_shutdown_timeout")
  public Long gracefulShutdownTimeout;

  /**
   * Gets {@link #gracefulShutdownTimeout}.
   *
   * @return the value of {@link #gracefulShutdownTimeout}
   */
  public Long getGracefulShutdownTimeout() {
    return gracefulShutdownTimeout;
  }

  /**
   * File transfer (upload/download) rate limit
   *  `&lt;limit&gt;-&lt;period&gt;`, with the given periods:
   * * 'S': second
   * * 'M': minute
   * * 'H': hour
   * * 'D': day
   * Examples:
   * * 5 requests/second: '5-S'
   * * 10 requests/minute: '10-M'
   * * 1000 requests/hour: '1000-H'
   * * 2000 requests/day: '2000-D'
   */
  @JsonProperty("transfer_rate_limit")
  public String transferRateLimit;

  /**
   * Gets {@link #transferRateLimit}.
   *
   * @return the value of {@link #transferRateLimit}
   */
  public String getTransferRateLimit() {
    return transferRateLimit;
  }

  /**
   * Auto update policy ['manual_trigger', 'critical_only', 'always', 'never'] (default always)
   */
  @JsonProperty("auto_update_policy")
  public String autoUpdatePolicy;

  /**
   * Gets {@link #autoUpdatePolicy}.
   *
   * @return the value of {@link #autoUpdatePolicy}
   */
  public String getAutoUpdatePolicy() {
    return autoUpdatePolicy;
  }


}
