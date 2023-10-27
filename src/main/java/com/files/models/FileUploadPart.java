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
public class FileUploadPart {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .build();

  public File putBufferedInputStream(BufferedInputStream inputStream, long length, Date date) throws IOException {
    RequestMethods requestMethod;
    requestMethod = RequestMethods.PUT;
    if (httpMethod == "post") {
      requestMethod = RequestMethods.POST;
    }
    FilesClient.putBufferedInputStream(this.uploadUri, requestMethod, this.path, inputStream, length);
    HashMap<String, Object> parameters = new HashMap<>();
    parameters.put("action", "end");
    parameters.put("ref", ref);
    return File.completeUpload(this.path, parameters);
  }

  public File putLocalFile(String source) throws IOException {
    RequestMethods requestMethod;
    requestMethod = RequestMethods.PUT;
    if (httpMethod == "post") {
      requestMethod = RequestMethods.POST;
    }

    java.io.File file = new java.io.File(source);
    long fileLength = file.length();
    FileInputStream fileInputStream = null;
    BufferedInputStream bufferedInputStream = null;
    try {
      bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
      FilesClient.putBufferedInputStream(this.uploadUri, requestMethod, this.path, bufferedInputStream, fileLength);
      HashMap<String, Object> parameters = new HashMap<>();
      parameters.put("action", "end");
      parameters.put("ref", ref);
      return File.completeUpload(this.path, parameters);
    } finally {
      if (fileInputStream != null) { 
        fileInputStream.close();
      }
      if (bufferedInputStream != null) {
        bufferedInputStream.close();
      }
    }
  }

  public FileUploadPart() {
    this(null, null);
  }

  public FileUploadPart(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public FileUploadPart(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Content-Type and File to send
  */
  @Getter
  @JsonProperty("send")
  public Map<String, String> send;

  /**
  * Type of upload
  */
  @Getter
  @JsonProperty("action")
  public String action;

  /**
  * If `true`, this file exists and you may wish to ask the user for overwrite confirmation
  */
  @Getter
  @JsonProperty("ask_about_overwrites")
  public Boolean askAboutOverwrites;

  /**
  * Number of parts in the upload
  */
  @Getter
  @JsonProperty("available_parts")
  public Long availableParts;

  /**
  * Date/time of when this Upload part expires and the URL cannot be used any more
  */
  @Getter
  @JsonProperty("expires")
  public String expires;

  /**
  * Additional upload headers to provide as part of the upload
  */
  @Getter
  @JsonProperty("headers")
  public Map<String, String> headers;

  /**
  * HTTP Method to use for uploading the part, usually `PUT`
  */
  @Getter
  @JsonProperty("http_method")
  public String httpMethod;

  /**
  * Size in bytes for this part
  */
  @Getter
  @JsonProperty("next_partsize")
  public Long nextPartsize;

  /**
  * If `true`, multiple parts may be uploaded in parallel.  If `false`, be sure to only upload one part at a time, in order.
  */
  @Getter
  @JsonProperty("parallel_parts")
  public Boolean parallelParts;

  /**
  * If `true`, parts may be retried. If `false`, a part cannot be retried and the upload should be restarted.
  */
  @Getter
  @JsonProperty("retry_parts")
  public Boolean retryParts;

  /**
  * Additional HTTP parameters to send with the upload
  */
  @Getter
  @JsonProperty("parameters")
  public Map<String, String> parameters;

  /**
  * Number of this upload part
  */
  @Getter
  @JsonProperty("part_number")
  public Long partNumber;

  /**
  * Size in bytes for the next upload part
  */
  @Getter
  @JsonProperty("partsize")
  public Long partsize;

  /**
  * New file path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
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
