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

public class FilePartUpload {
  private HashMap<String, Object> attributes;
  private HashMap<String, Object> options;

  public FilePartUpload() {
    this(null, null);
  }

  public FilePartUpload(HashMap<String, Object> attributes) {
    this(attributes, null);
  }

  public FilePartUpload(HashMap<String, Object> attributes, HashMap<String, Object> options) {
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
  * Content-Type and File to send
  */
  @Getter
  @JsonProperty("send")
  public Object send;

  /**
  * Type of upload
  */
  @Getter
  @JsonProperty("action")
  public String action;

  /**
  * If false, rename conflicting files instead of asking for overwrite confirmation
  */
  @Getter
  @JsonProperty("ask_about_overwrites")
  public Boolean askAboutOverwrites;

  /**
  * Currently unused
  */
  @Getter
  @JsonProperty("available_parts")
  public String availableParts;

  /**
  * Currently unused
  */
  @Getter
  @JsonProperty("expires")
  public String expires;

  /**
  * Additional upload headers
  */
  @Getter
  @JsonProperty("headers")
  public Object headers;

  /**
  * Upload method, usually POST
  */
  @Getter
  @JsonProperty("http_method")
  public String httpMethod;

  /**
  * Currently unused
  */
  @Getter
  @JsonProperty("next_partsize")
  public String nextPartsize;

  /**
  * Additional upload parameters
  */
  @Getter
  @JsonProperty("parameters")
  public String parameters;

  /**
  * Currently unused
  */
  @Getter
  @JsonProperty("part_number")
  public String partNumber;

  /**
  * Currently unused
  */
  @Getter
  @JsonProperty("partsize")
  public String partsize;

  /**
  * Upload path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @Getter
  @JsonProperty("path")
  public String path;

  /**
  * Reference name for this upload part
  */
  @Getter
  @JsonProperty("ref")
  public String ref;

  /**
  * URI to upload this part to
  */
  @Getter
  @JsonProperty("upload_uri")
  public String uploadUri;



}


