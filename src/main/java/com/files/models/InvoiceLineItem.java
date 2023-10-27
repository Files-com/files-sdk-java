package com.files.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.ListIterator;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceLineItem {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .build();


  public InvoiceLineItem() {
    this(null, null);
  }

  public InvoiceLineItem(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public InvoiceLineItem(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
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
