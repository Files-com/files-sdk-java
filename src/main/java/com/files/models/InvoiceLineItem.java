package com.files.models;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class InvoiceLineItem implements ModelInterface {
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

  // One mapper for every InvoiceLineItem, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code InvoiceLineItem} with no attributes set.
   */
  public InvoiceLineItem() {
    this(null, null);
  }

  /**
   * Creates a {@code InvoiceLineItem} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public InvoiceLineItem(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code InvoiceLineItem} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public InvoiceLineItem(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Invoice Line item Id
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
   * Invoice line item amount
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("amount")
  public BigDecimal amount;

  /**
   * Gets {@link #amount}.
   *
   * @return the value of {@link #amount}
   */
  public BigDecimal getAmount() {
    return amount;
  }

  /**
   * Invoice line item created at date/time
   */
  @JsonProperty("created_at")
  public Date createdAt;

  /**
   * Gets {@link #createdAt}.
   *
   * @return the value of {@link #createdAt}
   */
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * Invoice line item description
   */
  @JsonProperty("description")
  public String description;

  /**
   * Gets {@link #description}.
   *
   * @return the value of {@link #description}
   */
  public String getDescription() {
    return description;
  }

  /**
   * Invoice line item type
   */
  @JsonProperty("type")
  public String type;

  /**
   * Gets {@link #type}.
   *
   * @return the value of {@link #type}
   */
  public String getType() {
    return type;
  }

  /**
   * Invoice line item service end date/time
   */
  @JsonProperty("service_end_at")
  public Date serviceEndAt;

  /**
   * Gets {@link #serviceEndAt}.
   *
   * @return the value of {@link #serviceEndAt}
   */
  public Date getServiceEndAt() {
    return serviceEndAt;
  }

  /**
   * Invoice line item service start date/time
   */
  @JsonProperty("service_start_at")
  public Date serviceStartAt;

  /**
   * Gets {@link #serviceStartAt}.
   *
   * @return the value of {@link #serviceStartAt}
   */
  public Date getServiceStartAt() {
    return serviceStartAt;
  }

  /**
   * Plan name
   */
  @JsonProperty("plan")
  public String plan;

  /**
   * Gets {@link #plan}.
   *
   * @return the value of {@link #plan}
   */
  public String getPlan() {
    return plan;
  }

  /**
   * Site name
   */
  @JsonProperty("site")
  public String site;

  /**
   * Gets {@link #site}.
   *
   * @return the value of {@link #site}
   */
  public String getSite() {
    return site;
  }

  /**
   * Prepaid bytes purchased for this invoice line item
   */
  @JsonProperty("prepaid_bytes")
  public Long prepaidBytes;

  /**
   * Gets {@link #prepaidBytes}.
   *
   * @return the value of {@link #prepaidBytes}
   */
  public Long getPrepaidBytes() {
    return prepaidBytes;
  }

  /**
   * When the prepaid bytes expire
   */
  @JsonProperty("prepaid_bytes_expire_at")
  public Date prepaidBytesExpireAt;

  /**
   * Gets {@link #prepaidBytesExpireAt}.
   *
   * @return the value of {@link #prepaidBytesExpireAt}
   */
  public Date getPrepaidBytesExpireAt() {
    return prepaidBytesExpireAt;
  }

  /**
   * Total prepaid bytes used for this invoice line item
   */
  @JsonProperty("prepaid_bytes_used")
  public Long prepaidBytesUsed;

  /**
   * Gets {@link #prepaidBytesUsed}.
   *
   * @return the value of {@link #prepaidBytesUsed}
   */
  public Long getPrepaidBytesUsed() {
    return prepaidBytesUsed;
  }

  /**
   * Available prepaid bytes for this invoice line item
   */
  @JsonProperty("prepaid_bytes_available")
  public Long prepaidBytesAvailable;

  /**
   * Gets {@link #prepaidBytesAvailable}.
   *
   * @return the value of {@link #prepaidBytesAvailable}
   */
  public Long getPrepaidBytesAvailable() {
    return prepaidBytesAvailable;
  }


}
