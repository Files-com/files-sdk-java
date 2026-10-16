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
public class AgentNode implements ModelInterface {
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

  // One mapper for every AgentNode, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  public AgentNode() {
    this(null, null);
  }

  public AgentNode(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public AgentNode(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Stable Agent installation ID
   */
  @JsonProperty("node_id")
  public String nodeId;

  public String getNodeId() {
    return nodeId;
  }

  /**
   * Customer-configured Agent node name
   */
  @JsonProperty("name")
  public String name;

  public String getName() {
    return name;
  }

  /**
   * Hostname reported by the Agent
   */
  @JsonProperty("hostname")
  public String hostname;

  public String getHostname() {
    return hostname;
  }

  /**
   * Configured traffic preference
   */
  @JsonProperty("availability_role")
  public String availabilityRole;

  public String getAvailabilityRole() {
    return availabilityRole;
  }

  /**
   * Whether this node currently has an available Agent instance
   */
  @JsonProperty("status")
  public String status;

  public String getStatus() {
    return status;
  }

  /**
   * Whether this node is the current default route for new unscoped work
   */
  @JsonProperty("is_default")
  public Boolean isDefault;

  public Boolean getIsDefault() {
    return isDefault;
  }

  /**
   * Whether the proxy recently validated a direct connection to this Agent node. False means direct transfers are enabled but not currently available; null means disabled or unsupported.
   */
  @JsonProperty("direct_transfer_available")
  public Boolean directTransferAvailable;

  public Boolean getDirectTransferAvailable() {
    return directTransferAvailable;
  }

  /**
   * Most recent successful node observation
   */
  @JsonProperty("last_seen_at")
  public Date lastSeenAt;

  public Date getLastSeenAt() {
    return lastSeenAt;
  }

  /**
   * Current Agent processes for this node
   */
  @JsonProperty("instances")
  public AgentNodeInstance[] instances;

  public AgentNodeInstance[] getInstances() {
    return instances;
  }


}
