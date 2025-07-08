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
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.ListIterator;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
import com.files.util.UrlUtils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountLineItem implements ModelInterface {
  private HashMap<String, Object> options;

  public void setOptions(HashMap<String, Object> options) {
    this.options = options;
  }

  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();


  public AccountLineItem() {
    this(null, null);
  }

  public AccountLineItem(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public AccountLineItem(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
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
  @JsonProperty("amount")
  public Double amount;

  public Double getAmount() {
    return amount;
  }

  /**
  * Line item balance
  */
  @JsonProperty("balance")
  public Double balance;

  public Double getBalance() {
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
  public Object[] invoiceLineItems;

  public Object[] getInvoiceLineItems() {
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
  public Object[] paymentLineItems;

  public Object[] getPaymentLineItems() {
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


}
