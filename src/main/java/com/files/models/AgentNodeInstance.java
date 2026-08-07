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
public class AgentNodeInstance implements ModelInterface {
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


  public AgentNodeInstance() {
    this(null, null);
  }

  public AgentNodeInstance(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public AgentNodeInstance(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Ephemeral ID for this running Agent process
  */
  @JsonProperty("instance_id")
  public String instanceId;

  public String getInstanceId() {
    return instanceId;
  }

  /**
  * Role of this process during an Agent update
  */
  @JsonProperty("process_state")
  public String processState;

  public String getProcessState() {
    return processState;
  }

  /**
  * Whether this process has an available proxy connection
  */
  @JsonProperty("status")
  public String status;

  public String getStatus() {
    return status;
  }

  /**
  * Whether this process receives new unscoped work for its node
  */
  @JsonProperty("is_default")
  public Boolean isDefault;

  public Boolean getIsDefault() {
    return isDefault;
  }

  /**
  * Agent version reported by this process
  */
  @JsonProperty("agent_version")
  public String agentVersion;

  public String getAgentVersion() {
    return agentVersion;
  }

  /**
  * Most recent successful observation for this process
  */
  @JsonProperty("last_seen_at")
  public Date lastSeenAt;

  public Date getLastSeenAt() {
    return lastSeenAt;
  }

  /**
  * Proxy connections observed for this process
  */
  @JsonProperty("connections")
  public AgentNodeConnection[] connections;

  public AgentNodeConnection[] getConnections() {
    return connections;
  }


}
