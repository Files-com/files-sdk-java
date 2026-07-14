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
public class AutomationExecutionNode implements ModelInterface {
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


  public AutomationExecutionNode() {
    this(null, null);
  }

  public AutomationExecutionNode(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public AutomationExecutionNode(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Node ID from the pinned Automation definition.
  */
  @JsonProperty("node_id")
  public String nodeId;

  public String getNodeId() {
    return nodeId;
  }

  /**
  * Node type.
  */
  @JsonProperty("node_type")
  public String nodeType;

  public String getNodeType() {
    return nodeType;
  }

  /**
  * Node status.
  */
  @JsonProperty("status")
  public String status;

  public String getStatus() {
    return status;
  }

  /**
  * Current node execution stage.
  */
  @JsonProperty("run_stage")
  public String runStage;

  public String getRunStage() {
    return runStage;
  }

  /**
  * Whether this node reused persisted output from an earlier run.
  */
  @JsonProperty("reused")
  public Boolean reused;

  public Boolean getReused() {
    return reused;
  }

  /**
  * Count of successful operations in this node.
  */
  @JsonProperty("successful_operations")
  public Long successfulOperations;

  public Long getSuccessfulOperations() {
    return successfulOperations;
  }

  /**
  * Count of failed operations in this node.
  */
  @JsonProperty("failed_operations")
  public Long failedOperations;

  public Long getFailedOperations() {
    return failedOperations;
  }

  /**
  * When this node started.
  */
  @JsonProperty("started_at")
  public Date startedAt;

  public Date getStartedAt() {
    return startedAt;
  }

  /**
  * When this node completed.
  */
  @JsonProperty("completed_at")
  public Date completedAt;

  public Date getCompletedAt() {
    return completedAt;
  }

  /**
  * Node runtime in milliseconds.
  */
  @JsonProperty("duration_ms")
  public Long durationMs;

  public Long getDurationMs() {
    return durationMs;
  }

  /**
  * Ordered inbound edge references.
  */
  @JsonProperty("inputs")
  public Object[] inputs;

  public Object[] getInputs() {
    return inputs;
  }

  /**
  * Output counts, item kinds, and typed-error summaries by outlet.
  */
  @JsonProperty("outputs")
  public Object outputs;

  public Object getOutputs() {
    return outputs;
  }

  /**
  * Materialized input items currently available, grouped by inlet.
  */
  @JsonProperty("input_items")
  public Object inputItems;

  public Object getInputItems() {
    return inputItems;
  }

  /**
  * Materialized output items grouped by outlet.
  */
  @JsonProperty("output_items")
  public Object outputItems;

  public Object getOutputItems() {
    return outputItems;
  }


}
