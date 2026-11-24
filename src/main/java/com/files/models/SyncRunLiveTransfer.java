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
public class SyncRunLiveTransfer implements ModelInterface {
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

  // One mapper for every SyncRunLiveTransfer, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code SyncRunLiveTransfer} with no attributes set.
   */
  public SyncRunLiveTransfer() {
    this(null, null);
  }

  /**
   * Creates a {@code SyncRunLiveTransfer} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public SyncRunLiveTransfer(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code SyncRunLiveTransfer} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public SyncRunLiveTransfer(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * The file path being transferred. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
   */
  @JsonProperty("path")
  public String path;

  /**
   * Gets {@link #path}.
   *
   * @return the value of {@link #path}
   */
  public String getPath() {
    return path;
  }

  /**
   * Status of this individual transfer
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
   * Bytes transferred so far
   */
  @JsonProperty("bytes_copied")
  public Long bytesCopied;

  /**
   * Gets {@link #bytesCopied}.
   *
   * @return the value of {@link #bytesCopied}
   */
  public Long getBytesCopied() {
    return bytesCopied;
  }

  /**
   * Total bytes of the file being transferred
   */
  @JsonProperty("bytes_total")
  public Long bytesTotal;

  /**
   * Gets {@link #bytesTotal}.
   *
   * @return the value of {@link #bytesTotal}
   */
  public Long getBytesTotal() {
    return bytesTotal;
  }

  /**
   * Transfer progress from 0.0 to 1.0
   */
  @JsonProperty("percentage")
  public Double percentage;

  /**
   * Gets {@link #percentage}.
   *
   * @return the value of {@link #percentage}
   */
  public Double getPercentage() {
    return percentage;
  }

  /**
   * Estimated time remaining (human-readable)
   */
  @JsonProperty("eta")
  public String eta;

  /**
   * Gets {@link #eta}.
   *
   * @return the value of {@link #eta}
   */
  public String getEta() {
    return eta;
  }

  /**
   * When this individual transfer started
   */
  @JsonProperty("started_at")
  public String startedAt;

  /**
   * Gets {@link #startedAt}.
   *
   * @return the value of {@link #startedAt}
   */
  public String getStartedAt() {
    return startedAt;
  }


}
