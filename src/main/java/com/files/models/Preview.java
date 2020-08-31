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

public class Preview {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public Preview() {
    this(null, null);
  }

  public Preview(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Preview(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Preview ID
  */
  @Getter
  @JsonProperty("id")
  private Long id;

  /**
  * Preview status.  Can be invalid, not_generated, generating, complete, or file_too_large
  */
  @Getter
  @JsonProperty("status")
  private String status;

  /**
  * Link to download preview
  */
  @Getter
  @JsonProperty("download_uri")
  private String downloadUri;

  /**
  * Preview status.  Can be invalid, not_generated, generating, complete, or file_too_large
  */
  @Getter
  @JsonProperty("type")
  private String type;

  /**
  * Preview size
  */
  @Getter
  @JsonProperty("size")
  private Long size;



}


