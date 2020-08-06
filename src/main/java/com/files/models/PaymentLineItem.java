package com.files.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.net.HttpMethods.RequestMethods;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class PaymentLineItem {
  private HashMap<String, Object> attributes;
  private HashMap<String, Object> options;

  public PaymentLineItem() {
    this(null, null);
  }

  public PaymentLineItem(HashMap<String, Object> attributes) {
    this(attributes, null);
  }

  public PaymentLineItem(HashMap<String, Object> attributes, HashMap<String, Object> options) {
    this.attributes = attributes;
    this.options = options;
    try{
      ObjectMapper objectMapper = new ObjectMapper();
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(attributes));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Payment line item amount
  */
  @Getter
  @JsonProperty("amount")
  public Double amount;

  /**
  * Payment line item created at date/time
  */
  @Getter
  @JsonProperty("created_at")
  public Date createdAt;

  /**
  * Invoice ID
  */
  @Getter
  @JsonProperty("invoice_id")
  public Long invoiceId;

  /**
  * Payment ID
  */
  @Getter
  @JsonProperty("payment_id")
  public Long paymentId;

  /**
  * Payment line item updated at date/time
  */
  @Getter
  @JsonProperty("updated_at")
  public Date updatedAt;



}


