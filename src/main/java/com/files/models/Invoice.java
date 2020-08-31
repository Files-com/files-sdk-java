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

public class Invoice {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public Invoice() {
    this(null, null);
  }

  public Invoice(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Invoice(HashMap<String, Object> parameters, HashMap<String, Object> options) {
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
  private Object[] invoiceLineItems;

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
  private Object[] paymentLineItems;

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



  /**
  * Parameters:
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  *   cursor - string - Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  */
  public static List<Invoice> list() throws IOException{
    return list(null,null);
  }
  public static List<Invoice> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  public static List<Invoice> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("page") && !(parameters.get("page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: page must be of type Long parameters[\"page\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }

    if (parameters.containsKey("action") && !(parameters.get("action") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: action must be of type String parameters[\"action\"]");
    }

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    String url = String.format("%s%s/invoices", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<Invoice>> typeReference = new TypeReference<List<Invoice>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<Invoice> all() throws IOException {
    return all(null, null);
  }

  public static List<Invoice> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Invoice ID.
  */
  public static List<Invoice> find() throws IOException{
    return find(null, null,null);
  }
  public static List<Invoice> find(Long id,  HashMap<String, Object> parameters) throws IOException {
    return find(id, parameters, null);
  }

  public static List<Invoice> find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(null, parameters, options);
  }

  public static List<Invoice> find(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id != null){
      parameters.put("id",id);
    }
    if (parameters.containsKey("id") && !(parameters.get("id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    String url = String.format("%s%s/invoices/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<List<Invoice>> typeReference = new TypeReference<List<Invoice>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<Invoice> get() throws IOException {
    return get(null, null, null);
  }

  public static List<Invoice> get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(id, parameters, options);
  }

}


