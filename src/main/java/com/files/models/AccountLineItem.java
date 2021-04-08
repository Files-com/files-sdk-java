package com.files.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.ModelUtils;
import com.files.util.FilesInputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class AccountLineItem {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public AccountLineItem() {
    this(null, null);
  }

  public AccountLineItem(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public AccountLineItem(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Line item Id
  */
  @Getter
  @JsonProperty("id")
  private Long id;

  /**
  * Line item amount
  */
  @Getter
  @JsonProperty("amount")
  private Double amount;

  /**
  * Line item balance
  */
  @Getter
  @JsonProperty("balance")
  private Double balance;

  /**
  * Line item created at
  */
  @Getter
  @JsonProperty("created_at")
  private Date createdAt;

  /**
  * Line item currency
  */
  @Getter
  @JsonProperty("currency")
  private String currency;

  /**
  * Line item download uri
  */
  @Getter
  @JsonProperty("download_uri")
  private String downloadUri;

  /**
  * Associated invoice line items
  */
  @Getter
  @JsonProperty("invoice_line_items")
  private Map<String, String> invoiceLineItems;

  /**
  * Line item payment method
  */
  @Getter
  @JsonProperty("method")
  private String method;

  /**
  * Associated payment line items
  */
  @Getter
  @JsonProperty("payment_line_items")
  private Map<String, String> paymentLineItems;

  /**
  * Date/time payment was reversed if applicable
  */
  @Getter
  @JsonProperty("payment_reversed_at")
  private Date paymentReversedAt;

  /**
  * Type of payment if applicable
  */
  @Getter
  @JsonProperty("payment_type")
  private String paymentType;

  /**
  * Site name this line item is for
  */
  @Getter
  @JsonProperty("site_name")
  private String siteName;

  /**
  * Type of line item, either payment or invoice
  */
  @Getter
  @JsonProperty("type")
  private String type;

  /**
  * Line item updated at
  */
  @Getter
  @JsonProperty("updated_at")
  private Date updatedAt;



}


