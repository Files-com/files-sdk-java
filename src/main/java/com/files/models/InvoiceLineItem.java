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
public class InvoiceLineItem implements ModelInterface {
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
  * Invoice Line item Id
  */
  @JsonProperty("id")
  public Long id;

  public Long getId() {
    return id;
  }

  /**
  * Invoice line item amount
  */
  @JsonProperty("amount")
  public Double amount;

  public Double getAmount() {
    return amount;
  }

  /**
  * Invoice line item created at date/time
  */
  @JsonProperty("created_at")
  public Date createdAt;

  public Date getCreatedAt() {
    return createdAt;
  }

  /**
  * Invoice line item description
  */
  @JsonProperty("description")
  public String description;

  public String getDescription() {
    return description;
  }

  /**
  * Invoice line item type
  */
  @JsonProperty("type")
  public String type;

  public String getType() {
    return type;
  }

  /**
  * Invoice line item service end date/time
  */
  @JsonProperty("service_end_at")
  public Date serviceEndAt;

  public Date getServiceEndAt() {
    return serviceEndAt;
  }

  /**
  * Invoice line item service start date/time
  */
  @JsonProperty("service_start_at")
  public Date serviceStartAt;

  public Date getServiceStartAt() {
    return serviceStartAt;
  }

  /**
  * Plan name
  */
  @JsonProperty("plan")
  public String plan;

  public String getPlan() {
    return plan;
  }

  /**
  * Site name
  */
  @JsonProperty("site")
  public String site;

  public String getSite() {
    return site;
  }

  /**
  * Prepaid bytes purchased for this invoice line item
  */
  @JsonProperty("prepaid_bytes")
  public Long prepaidBytes;

  public Long getPrepaidBytes() {
    return prepaidBytes;
  }

  /**
  * When the prepaid bytes expire
  */
  @JsonProperty("prepaid_bytes_expire_at")
  public Date prepaidBytesExpireAt;

  public Date getPrepaidBytesExpireAt() {
    return prepaidBytesExpireAt;
  }

  /**
  * Total prepaid bytes used for this invoice line item
  */
  @JsonProperty("prepaid_bytes_used")
  public Long prepaidBytesUsed;

  public Long getPrepaidBytesUsed() {
    return prepaidBytesUsed;
  }

  /**
  * Available prepaid bytes for this invoice line item
  */
  @JsonProperty("prepaid_bytes_available")
  public Long prepaidBytesAvailable;

  public Long getPrepaidBytesAvailable() {
    return prepaidBytesAvailable;
  }


}
