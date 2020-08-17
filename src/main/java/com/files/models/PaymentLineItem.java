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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class PaymentLineItem {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public PaymentLineItem() {
    this(null, null);
  }

  public PaymentLineItem(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public PaymentLineItem(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Payment line item amount
  */
  @Getter
  @JsonProperty("amount")
  private Double amount;

  /**
  * Payment line item created at date/time
  */
  @Getter
  @JsonProperty("created_at")
  private Date createdAt;

  /**
  * Invoice ID
  */
  @Getter
  @JsonProperty("invoice_id")
  private Long invoiceId;

  /**
  * Payment ID
  */
  @Getter
  @JsonProperty("payment_id")
  private Long paymentId;

  /**
  * Payment line item updated at date/time
  */
  @Getter
  @JsonProperty("updated_at")
  private Date updatedAt;



}


