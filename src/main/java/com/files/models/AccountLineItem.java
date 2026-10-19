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
public class AccountLineItem implements ModelInterface {
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

  // One mapper for every AccountLineItem, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code AccountLineItem} with no attributes set.
   */
  public AccountLineItem() {
    this(null, null);
  }

  /**
   * Creates a {@code AccountLineItem} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public AccountLineItem(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code AccountLineItem} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public AccountLineItem(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Line item Id
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
   * Line item amount
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
   * Line item balance
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("balance")
  public BigDecimal balance;

  /**
   * Gets {@link #balance}.
   *
   * @return the value of {@link #balance}
   */
  public BigDecimal getBalance() {
    return balance;
  }

  /**
   * Line item created at
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
   * Line item currency
   */
  @JsonProperty("currency")
  public String currency;

  /**
   * Gets {@link #currency}.
   *
   * @return the value of {@link #currency}
   */
  public String getCurrency() {
    return currency;
  }

  /**
   * Line item download uri
   */
  @JsonProperty("download_uri")
  public String downloadUri;

  /**
   * Gets {@link #downloadUri}.
   *
   * @return the value of {@link #downloadUri}
   */
  public String getDownloadUri() {
    return downloadUri;
  }

  /**
   * Associated invoice line items
   */
  @JsonProperty("invoice_line_items")
  public InvoiceLineItem[] invoiceLineItems;

  /**
   * Gets {@link #invoiceLineItems}.
   *
   * @return the value of {@link #invoiceLineItems}
   */
  public InvoiceLineItem[] getInvoiceLineItems() {
    return invoiceLineItems;
  }

  /**
   * Line item payment method
   */
  @JsonProperty("method")
  public String method;

  /**
   * Gets {@link #method}.
   *
   * @return the value of {@link #method}
   */
  public String getMethod() {
    return method;
  }

  /**
   * Associated payment line items
   */
  @JsonProperty("payment_line_items")
  public PaymentLineItem[] paymentLineItems;

  /**
   * Gets {@link #paymentLineItems}.
   *
   * @return the value of {@link #paymentLineItems}
   */
  public PaymentLineItem[] getPaymentLineItems() {
    return paymentLineItems;
  }

  /**
   * Date/time payment was reversed if applicable
   */
  @JsonProperty("payment_reversed_at")
  public Date paymentReversedAt;

  /**
   * Gets {@link #paymentReversedAt}.
   *
   * @return the value of {@link #paymentReversedAt}
   */
  public Date getPaymentReversedAt() {
    return paymentReversedAt;
  }

  /**
   * Type of payment if applicable
   */
  @JsonProperty("payment_type")
  public String paymentType;

  /**
   * Gets {@link #paymentType}.
   *
   * @return the value of {@link #paymentType}
   */
  public String getPaymentType() {
    return paymentType;
  }

  /**
   * Site name this line item is for
   */
  @JsonProperty("site_name")
  public String siteName;

  /**
   * Gets {@link #siteName}.
   *
   * @return the value of {@link #siteName}
   */
  public String getSiteName() {
    return siteName;
  }

  /**
   * Type of line item, either payment or invoice
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


}
