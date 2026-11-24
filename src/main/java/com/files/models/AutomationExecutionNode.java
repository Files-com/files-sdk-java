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
import java.util.Date;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AutomationExecutionNode implements ModelInterface {
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

  // One mapper for every AutomationExecutionNode, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code AutomationExecutionNode} with no attributes set.
   */
  public AutomationExecutionNode() {
    this(null, null);
  }

  /**
   * Creates a {@code AutomationExecutionNode} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public AutomationExecutionNode(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code AutomationExecutionNode} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public AutomationExecutionNode(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Node ID from the pinned Automation definition.
   */
  @JsonProperty("node_id")
  public String nodeId;

  /**
   * Gets {@link #nodeId}.
   *
   * @return the value of {@link #nodeId}
   */
  public String getNodeId() {
    return nodeId;
  }

  /**
   * Node type.
   */
  @JsonProperty("node_type")
  public String nodeType;

  /**
   * Gets {@link #nodeType}.
   *
   * @return the value of {@link #nodeType}
   */
  public String getNodeType() {
    return nodeType;
  }

  /**
   * Node status.
   */
  @JsonProperty("status")
  public String status;

  /**
   * Gets {@link #status}.
   *
   * @return the value of {@link #status}
   */
  public String getStatus() {
    return status;
  }

  /**
   * Current node execution stage.
   */
  @JsonProperty("run_stage")
  public String runStage;

  /**
   * Gets {@link #runStage}.
   *
   * @return the value of {@link #runStage}
   */
  public String getRunStage() {
    return runStage;
  }

  /**
   * Whether this node reused persisted output from an earlier run.
   */
  @JsonProperty("reused")
  public Boolean reused;

  /**
   * Gets {@link #reused}.
   *
   * @return the value of {@link #reused}
   */
  public Boolean getReused() {
    return reused;
  }

  /**
   * Count of successful operations in this node.
   */
  @JsonProperty("successful_operations")
  public Long successfulOperations;

  /**
   * Gets {@link #successfulOperations}.
   *
   * @return the value of {@link #successfulOperations}
   */
  public Long getSuccessfulOperations() {
    return successfulOperations;
  }

  /**
   * Count of failed operations in this node.
   */
  @JsonProperty("failed_operations")
  public Long failedOperations;

  /**
   * Gets {@link #failedOperations}.
   *
   * @return the value of {@link #failedOperations}
   */
  public Long getFailedOperations() {
    return failedOperations;
  }

  /**
   * When this node started.
   */
  @JsonProperty("started_at")
  public Date startedAt;

  /**
   * Gets {@link #startedAt}.
   *
   * @return the value of {@link #startedAt}
   */
  public Date getStartedAt() {
    return startedAt;
  }

  /**
   * When this node completed.
   */
  @JsonProperty("completed_at")
  public Date completedAt;

  /**
   * Gets {@link #completedAt}.
   *
   * @return the value of {@link #completedAt}
   */
  public Date getCompletedAt() {
    return completedAt;
  }

  /**
   * Node runtime in milliseconds.
   */
  @JsonProperty("duration_ms")
  public Long durationMs;

  /**
   * Gets {@link #durationMs}.
   *
   * @return the value of {@link #durationMs}
   */
  public Long getDurationMs() {
    return durationMs;
  }

  /**
   * Ordered inbound edge references.
   */
  @JsonProperty("inputs")
  public Object[] inputs;

  /**
   * Gets {@link #inputs}.
   *
   * @return the value of {@link #inputs}
   */
  public Object[] getInputs() {
    return inputs;
  }

  /**
   * Output counts, item kinds, and typed-error summaries by outlet.
   */
  @JsonProperty("outputs")
  public Object outputs;

  /**
   * Gets {@link #outputs}.
   *
   * @return the value of {@link #outputs}
   */
  public Object getOutputs() {
    return outputs;
  }

  /**
   * Materialized input items currently available, grouped by inlet.
   */
  @JsonProperty("input_items")
  public Object inputItems;

  /**
   * Gets {@link #inputItems}.
   *
   * @return the value of {@link #inputItems}
   */
  public Object getInputItems() {
    return inputItems;
  }

  /**
   * Materialized output items grouped by outlet.
   */
  @JsonProperty("output_items")
  public Object outputItems;

  /**
   * Gets {@link #outputItems}.
   *
   * @return the value of {@link #outputItems}
   */
  public Object getOutputItems() {
    return outputItems;
  }


}
