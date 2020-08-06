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

public class InvoiceLineItem {
  private HashMap<String, Object> attributes;
  private HashMap<String, Object> options;

  public InvoiceLineItem() {
    this(null, null);
  }

  public InvoiceLineItem(HashMap<String, Object> attributes) {
    this(attributes, null);
  }

  public InvoiceLineItem(HashMap<String, Object> attributes, HashMap<String, Object> options) {
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
  * Invoice line item amount
  */
  @Getter
  @JsonProperty("amount")
  public Double amount;

  /**
  * Invoice line item created at date/time
  */
  @Getter
  @JsonProperty("created_at")
  public Date createdAt;

  /**
  * Invoice line item description
  */
  @Getter
  @JsonProperty("description")
  public String description;

  /**
  * Invoice line item type
  */
  @Getter
  @JsonProperty("type")
  public String type;

  /**
  * Invoice line item service end date/time
  */
  @Getter
  @JsonProperty("service_end_at")
  public Date serviceEndAt;

  /**
  * Invoice line item service start date/time
  */
  @Getter
  @JsonProperty("service_start_at")
  public Date serviceStartAt;

  /**
  * Invoice line item updated date/time
  */
  @Getter
  @JsonProperty("updated_at")
  public Date updatedAt;

  /**
  * Plan name
  */
  @Getter
  @JsonProperty("plan")
  public String plan;

  /**
  * Site name
  */
  @Getter
  @JsonProperty("site")
  public String site;



}


