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
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class InvoiceLineItem {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public InvoiceLineItem() {
    this(null, null);
  }

  public InvoiceLineItem(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public InvoiceLineItem(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Invoice line item amount
  */
  @Getter
  @JsonProperty("amount")
  private Double amount;

  /**
  * Invoice line item created at date/time
  */
  @Getter
  @JsonProperty("created_at")
  private Date createdAt;

  /**
  * Invoice line item description
  */
  @Getter
  @JsonProperty("description")
  private String description;

  /**
  * Invoice line item type
  */
  @Getter
  @JsonProperty("type")
  private String type;

  /**
  * Invoice line item service end date/time
  */
  @Getter
  @JsonProperty("service_end_at")
  private Date serviceEndAt;

  /**
  * Invoice line item service start date/time
  */
  @Getter
  @JsonProperty("service_start_at")
  private Date serviceStartAt;

  /**
  * Invoice line item updated date/time
  */
  @Getter
  @JsonProperty("updated_at")
  private Date updatedAt;

  /**
  * Plan name
  */
  @Getter
  @JsonProperty("plan")
  private String plan;

  /**
  * Site name
  */
  @Getter
  @JsonProperty("site")
  private String site;



}


