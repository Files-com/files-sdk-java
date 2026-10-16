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
public class Payment implements ModelInterface {
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

  // One mapper for every Payment, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  public Payment() {
    this(null, null);
  }

  public Payment(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Payment(HashMap<String, Object> parameters, HashMap<String, Object> options) {
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

  public Long getId() {
    return id;
  }

  /**
   * Line item amount
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("amount")
  public BigDecimal amount;

  public BigDecimal getAmount() {
    return amount;
  }

  /**
   * Line item balance
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("balance")
  public BigDecimal balance;

  public BigDecimal getBalance() {
    return balance;
  }

  /**
   * Line item created at
   */
  @JsonProperty("created_at")
  public Date createdAt;

  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * Line item currency
   */
  @JsonProperty("currency")
  public String currency;

  public String getCurrency() {
    return currency;
  }

  /**
   * Line item download uri
   */
  @JsonProperty("download_uri")
  public String downloadUri;

  public String getDownloadUri() {
    return downloadUri;
  }

  /**
   * Associated invoice line items
   */
  @JsonProperty("invoice_line_items")
  public InvoiceLineItem[] invoiceLineItems;

  public InvoiceLineItem[] getInvoiceLineItems() {
    return invoiceLineItems;
  }

  /**
   * Line item payment method
   */
  @JsonProperty("method")
  public String method;

  public String getMethod() {
    return method;
  }

  /**
   * Associated payment line items
   */
  @JsonProperty("payment_line_items")
  public PaymentLineItem[] paymentLineItems;

  public PaymentLineItem[] getPaymentLineItems() {
    return paymentLineItems;
  }

  /**
   * Date/time payment was reversed if applicable
   */
  @JsonProperty("payment_reversed_at")
  public Date paymentReversedAt;

  public Date getPaymentReversedAt() {
    return paymentReversedAt;
  }

  /**
   * Type of payment if applicable
   */
  @JsonProperty("payment_type")
  public String paymentType;

  public String getPaymentType() {
    return paymentType;
  }

  /**
   * Site name this line item is for
   */
  @JsonProperty("site_name")
  public String siteName;

  public String getSiteName() {
    return siteName;
  }

  /**
   * Type of line item, either payment or invoice
   */
  @JsonProperty("type")
  public String type;

  public String getType() {
    return type;
  }


  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   */
  public static ListIterator<AccountLineItem> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<AccountLineItem> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<AccountLineItem> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().payments().list(parameters, options);
  }

  public static ListIterator<AccountLineItem> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<AccountLineItem> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Payment ID.
   */
  public static AccountLineItem find() throws RuntimeException {
    return find(null, null, null);
  }

  public static AccountLineItem find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static AccountLineItem find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static AccountLineItem find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().payments().find(id, parameters, options);
  }

  public static AccountLineItem get() throws RuntimeException {
    return get(null, null, null);
  }

  public static AccountLineItem get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

}
