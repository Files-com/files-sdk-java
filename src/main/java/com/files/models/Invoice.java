package com.files.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Date;
import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;

public class Invoice {
  private HashMap<String, Object> attributes;
  private HashMap<String, Object> options;

  public Invoice(HashMap<String, Object> attributes, HashMap<String, Object> options) {
    this.attributes = attributes;
    this.options = options;
    try{
      ObjectMapper objectMapper=new ObjectMapper();
      ObjectReader objectReader=objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(attributes));
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
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  */
  public static Invoice list( HashMap<String, Object> parameters) {
    return list(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static Invoice list( HashMap<String, Object> parameters, HashMap<String, Object> options) {
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

    // TODO: Send request
    return (Invoice) null;
  }

  public static Invoice all(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Invoice ID.
  */
  public static Invoice find(Long id,  HashMap<String, Object> parameters) {
    return find(id, parameters, null);
  }

  public static Invoice find(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return find(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static Invoice find(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) {
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
    // TODO: Send request
    return (Invoice) null;
  }

  public static Invoice get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return find(id, parameters, options);
  }

}


