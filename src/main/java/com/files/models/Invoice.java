package com.files.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class Invoice {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
    .builder()
    .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
    .build();

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
  public Long id;

  /**
  * Line item amount
  */
  @Getter
  @JsonProperty("amount")
  public Double amount;

  /**
  * Line item balance
  */
  @Getter
  @JsonProperty("balance")
  public Double balance;

  /**
  * Line item created at
  */
  @Getter
  @JsonProperty("created_at")
  public Date createdAt;

  /**
  * Line item currency
  */
  @Getter
  @JsonProperty("currency")
  public String currency;

  /**
  * Line item download uri
  */
  @Getter
  @JsonProperty("download_uri")
  public String downloadUri;

  /**
  * Associated invoice line items
  */
  @Getter
  @JsonProperty("invoice_line_items")
  public Object[] invoiceLineItems;

  /**
  * Line item payment method
  */
  @Getter
  @JsonProperty("method")
  public String method;

  /**
  * Associated payment line items
  */
  @Getter
  @JsonProperty("payment_line_items")
  public Object[] paymentLineItems;

  /**
  * Date/time payment was reversed if applicable
  */
  @Getter
  @JsonProperty("payment_reversed_at")
  public Date paymentReversedAt;

  /**
  * Type of payment if applicable
  */
  @Getter
  @JsonProperty("payment_type")
  public String paymentType;

  /**
  * Site name this line item is for
  */
  @Getter
  @JsonProperty("site_name")
  public String siteName;

  /**
  * Type of line item, either payment or invoice
  */
  @Getter
  @JsonProperty("type")
  public String type;

  /**
  * Line item updated at
  */
  @Getter
  @JsonProperty("updated_at")
  public Date updatedAt;



  /**
  * Parameters:
  *   cursor - string - Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via either the X-Files-Cursor-Next header or the X-Files-Cursor-Prev header.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  */
  public static List<Invoice> list() throws IOException {
    return list(null,null);
  }
  public static List<Invoice> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  public static List<Invoice> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
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
  public static List<Invoice> find() throws IOException {
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

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = ((Long) parameters.get("id"));
    }


    if (!(id instanceof Long) ) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex){
      }
    }

    String url = String.format("%s%s/invoices/%s", urlParts);

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


