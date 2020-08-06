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

public class Status {
  private HashMap<String, Object> attributes;
  private HashMap<String, Object> options;

  public Status() {
    this(null, null);
  }

  public Status(HashMap<String, Object> attributes) {
    this(attributes, null);
  }

  public Status(HashMap<String, Object> attributes, HashMap<String, Object> options) {
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
  * Status http code
  */
  @Getter
  @JsonProperty("code")
  public Long code;

  /**
  * Error message
  */
  @Getter
  @JsonProperty("message")
  public String message;

  /**
  * Status message
  */
  @Getter
  @JsonProperty("status")
  public String status;

  /**
  * Additional data
  */
  @Getter
  @JsonProperty("data")
  public Object data;

  /**
  * A list of api errors
  */
  @Getter
  @JsonProperty("errors")
  public Object[] errors;



}


