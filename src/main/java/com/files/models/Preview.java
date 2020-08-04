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

public class Preview {
  private HashMap<String, Object> attributes;
  private HashMap<String, Object> options;

  public Preview(HashMap<String, Object> attributes, HashMap<String, Object> options) {
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
  * Preview ID
  */
  @Getter
  @JsonProperty("id")
  public Long id;

  /**
  * Preview status.  Can be invalid, not_generated, generating, complete, or file_too_large
  */
  @Getter
  @JsonProperty("status")
  public String status;

  /**
  * Link to download preview
  */
  @Getter
  @JsonProperty("download_uri")
  public String downloadUri;

  /**
  * Preview status.  Can be invalid, not_generated, generating, complete, or file_too_large
  */
  @Getter
  @JsonProperty("type")
  public String type;

  /**
  * Preview size
  */
  @Getter
  @JsonProperty("size")
  public Long size;



}


