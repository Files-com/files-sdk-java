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

public class PublicIpAddress {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public PublicIpAddress() {
    this(null, null);
  }

  public PublicIpAddress(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public PublicIpAddress(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * The public IP address.
  */
  @Getter
  @JsonProperty("ip_address")
  private String ipAddress;

  /**
  * The name of the frontend server.
  */
  @Getter
  @JsonProperty("server_name")
  private String serverName;



}


