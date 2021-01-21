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

public class Status {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public Status() {
    this(null, null);
  }

  public Status(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Status(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Status HTTP code
  */
  @Getter
  @JsonProperty("code")
  private Long code;

  /**
  * Error message
  */
  @Getter
  @JsonProperty("message")
  private String message;

  /**
  * Status message
  */
  @Getter
  @JsonProperty("status")
  private String status;

  /**
  * Additional data
  */
  @Getter
  @JsonProperty("data")
  private Map<String, String> data;

  /**
  * A list of api errors
  */
  @Getter
  @JsonProperty("errors")
  private Object[] errors;

  /**
  * Required Clickwrap id
  */
  @Getter
  @JsonProperty("clickwrap_id")
  private Long clickwrapId;

  /**
  * Required Clickwrap body
  */
  @Getter
  @JsonProperty("clickwrap_body")
  private String clickwrapBody;



}


