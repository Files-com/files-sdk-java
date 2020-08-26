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

public class FileUploadPart {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public FileUploadPart() {
    this(null, null);
  }

  public FileUploadPart(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public FileUploadPart(HashMap<String, Object> parameters, HashMap<String, Object> options) {
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
  * If `true`, this file exists and you may wish to ask the user for overwrite confirmation
  */
  @Getter
  @JsonProperty("ask_about_overwrites")
  private Boolean askAboutOverwrites;

  /**
  * Number of parts in the upload
  */
  @Getter
  @JsonProperty("available_parts")
  private Long availableParts;

  /**
  * Date/time of when this Upload part expires and the URL cannot be used any more
  */
  @Getter
  @JsonProperty("expires")
  private String expires;

  /**
  * Additional upload headers to provide as part of the upload
  */
  @Getter
  @JsonProperty("headers")
  private Object headers;

  /**
  * HTTP Method to use for uploading the part, usually `PUT`
  */
  @Getter
  @JsonProperty("http_method")
  private String httpMethod;

  /**
  * Size in bytes for this part
  */
  @Getter
  @JsonProperty("next_partsize")
  private Long nextPartsize;

  /**
  * If `true`, multiple parts may be uploaded in parallel.  If `false`, be sure to only upload one part at a time, in order.
  */
  @Getter
  @JsonProperty("parallel_parts")
  private Boolean parallelParts;

  /**
  * Additional HTTP parameters to send with the upload
  */
  @Getter
  @JsonProperty("parameters")
  private Object parameters;

  /**
  * Number of this upload part
  */
  @Getter
  @JsonProperty("part_number")
  private Long partNumber;

  /**
  * Size in bytes for the next upload part
  */
  @Getter
  @JsonProperty("partsize")
  private Long partsize;

  /**
  * New file path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
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


