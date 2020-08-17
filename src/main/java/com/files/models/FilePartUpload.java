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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class FilePartUpload {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public FilePartUpload() {
    this(null, null);
  }

  public FilePartUpload(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public FilePartUpload(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Content-Type and File to send
  */
  @Getter
  @JsonProperty("send")
  private Object send;

  /**
  * Type of upload
  */
  @Getter
  @JsonProperty("action")
  private String action;

  /**
  * If false, rename conflicting files instead of asking for overwrite confirmation
  */
  @Getter
  @JsonProperty("ask_about_overwrites")
  private Boolean askAboutOverwrites;

  /**
  * Currently unused
  */
  @Getter
  @JsonProperty("available_parts")
  private String availableParts;

  /**
  * Currently unused
  */
  @Getter
  @JsonProperty("expires")
  private String expires;

  /**
  * Additional upload headers
  */
  @Getter
  @JsonProperty("headers")
  private Object headers;

  /**
  * Upload method, usually POST
  */
  @Getter
  @JsonProperty("http_method")
  private String httpMethod;

  /**
  * Currently unused
  */
  @Getter
  @JsonProperty("next_partsize")
  private String nextPartsize;

  /**
  * If true, parts may be uploaded in parallel
  */
  @Getter
  @JsonProperty("parallel_parts")
  private Boolean parallelParts;

  /**
  * Additional upload parameters
  */
  @Getter
  @JsonProperty("parameters")
  private String parameters;

  /**
  * Currently unused
  */
  @Getter
  @JsonProperty("part_number")
  private String partNumber;

  /**
  * Currently unused
  */
  @Getter
  @JsonProperty("partsize")
  private String partsize;

  /**
  * Upload path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @Getter
  @JsonProperty("path")
  private String path;

  /**
  * Reference name for this upload part
  */
  @Getter
  @JsonProperty("ref")
  private String ref;

  /**
  * URI to upload this part to
  */
  @Getter
  @JsonProperty("upload_uri")
  private String uploadUri;



}


