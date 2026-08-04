package com.files.models;

import com.fasterxml.jackson.annotation.JsonFormat;
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

  public void setOptions(HashMap<String, Object> options) {
    this.options = options;
  }

  private ObjectMapper objectMapper = JsonMapper
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
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
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
  * Whether this node is currently available for traffic
  */
  @JsonProperty("connection_status")
  public String connectionStatus;

  public String getConnectionStatus() {
    return connectionStatus;
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
  * Agent version reported by this node
  */
  @JsonProperty("agent_version")
  public String agentVersion;

  public String getAgentVersion() {
    return agentVersion;
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


}
