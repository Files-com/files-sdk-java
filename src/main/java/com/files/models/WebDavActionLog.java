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
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebDavActionLog {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();


  public WebDavActionLog() {
    this(null, null);
  }

  public WebDavActionLog(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public WebDavActionLog(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Start Time of Action
  */
  @Getter
  @JsonProperty("timestamp")
  public Date timestamp;

  /**
  * IP Address of WebDAV Client
  */
  @Getter
  @JsonProperty("remote_ip")
  public String remoteIp;

  /**
  * IP Address of WebDAV Server
  */
  @Getter
  @JsonProperty("server_ip")
  public String serverIp;

  /**
  * Username
  */
  @Getter
  @JsonProperty("username")
  public String username;

  /**
  * Authentication Ciphers
  */
  @Getter
  @JsonProperty("auth_ciphers")
  public String authCiphers;

  /**
  * Action Type
  */
  @Getter
  @JsonProperty("action_type")
  public String actionType;

  /**
  * Path as sent by the Client (may not match Files.com path due to user root folders for WebDAV). This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @Getter
  @JsonProperty("path")
  public String path;

  /**
  * Path on Files.com
  */
  @Getter
  @JsonProperty("true_path")
  public String truePath;

  /**
  * Name of File
  */
  @Getter
  @JsonProperty("name")
  public String name;

  /**
  * Method of the HTTP call.
  */
  @Getter
  @JsonProperty("http_method")
  public String httpMethod;

  /**
  * Path of the HTTP call.
  */
  @Getter
  @JsonProperty("http_path")
  public String httpPath;

  /**
  * HTTP Response Code returned to the WebDAV Client.
  */
  @Getter
  @JsonProperty("http_response_code")
  public Long httpResponseCode;

  /**
  * Size of File That was Uploaded or Downloaded.
  */
  @Getter
  @JsonProperty("size")
  public Long size;

  /**
  * Number of entries returned when listing files and folders
  */
  @Getter
  @JsonProperty("entries_returned")
  public Long entriesReturned;

  /**
  * Whether WebDAV Action was successful.
  */
  @Getter
  @JsonProperty("success")
  public Boolean success;

  /**
  * Duration (in milliseconds)
  */
  @Getter
  @JsonProperty("duration_ms")
  public Long durationMs;



  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string
  *   page - int64
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `start_date`, `end_date`, `path`, `true_path`, `remote_ip`, `success`, `action_type` or `username`. Valid field combinations are `[ start_date ]`, `[ end_date ]`, `[ path ]`, `[ true_path ]`, `[ remote_ip ]`, `[ success ]`, `[ action_type ]`, `[ username ]`, `[ start_date, end_date ]`, `[ start_date, path ]`, `[ start_date, true_path ]`, `[ start_date, remote_ip ]`, `[ start_date, success ]`, `[ start_date, action_type ]`, `[ start_date, username ]`, `[ end_date, path ]`, `[ end_date, true_path ]`, `[ end_date, remote_ip ]`, `[ end_date, success ]`, `[ end_date, action_type ]`, `[ end_date, username ]`, `[ path, true_path ]`, `[ path, remote_ip ]`, `[ path, success ]`, `[ path, action_type ]`, `[ path, username ]`, `[ true_path, remote_ip ]`, `[ true_path, success ]`, `[ true_path, action_type ]`, `[ true_path, username ]`, `[ remote_ip, success ]`, `[ remote_ip, action_type ]`, `[ remote_ip, username ]`, `[ success, action_type ]`, `[ success, username ]`, `[ action_type, username ]`, `[ start_date, end_date, path ]`, `[ start_date, end_date, true_path ]`, `[ start_date, end_date, remote_ip ]`, `[ start_date, end_date, success ]`, `[ start_date, end_date, action_type ]`, `[ start_date, end_date, username ]`, `[ start_date, path, true_path ]`, `[ start_date, path, remote_ip ]`, `[ start_date, path, success ]`, `[ start_date, path, action_type ]`, `[ start_date, path, username ]`, `[ start_date, true_path, remote_ip ]`, `[ start_date, true_path, success ]`, `[ start_date, true_path, action_type ]`, `[ start_date, true_path, username ]`, `[ start_date, remote_ip, success ]`, `[ start_date, remote_ip, action_type ]`, `[ start_date, remote_ip, username ]`, `[ start_date, success, action_type ]`, `[ start_date, success, username ]`, `[ start_date, action_type, username ]`, `[ end_date, path, true_path ]`, `[ end_date, path, remote_ip ]`, `[ end_date, path, success ]`, `[ end_date, path, action_type ]`, `[ end_date, path, username ]`, `[ end_date, true_path, remote_ip ]`, `[ end_date, true_path, success ]`, `[ end_date, true_path, action_type ]`, `[ end_date, true_path, username ]`, `[ end_date, remote_ip, success ]`, `[ end_date, remote_ip, action_type ]`, `[ end_date, remote_ip, username ]`, `[ end_date, success, action_type ]`, `[ end_date, success, username ]`, `[ end_date, action_type, username ]`, `[ path, true_path, remote_ip ]`, `[ path, true_path, success ]`, `[ path, true_path, action_type ]`, `[ path, true_path, username ]`, `[ path, remote_ip, success ]`, `[ path, remote_ip, action_type ]`, `[ path, remote_ip, username ]`, `[ path, success, action_type ]`, `[ path, success, username ]`, `[ path, action_type, username ]`, `[ true_path, remote_ip, success ]`, `[ true_path, remote_ip, action_type ]`, `[ true_path, remote_ip, username ]`, `[ true_path, success, action_type ]`, `[ true_path, success, username ]`, `[ true_path, action_type, username ]`, `[ remote_ip, success, action_type ]`, `[ remote_ip, success, username ]`, `[ remote_ip, action_type, username ]`, `[ success, action_type, username ]`, `[ start_date, end_date, path, true_path ]`, `[ start_date, end_date, path, remote_ip ]`, `[ start_date, end_date, path, success ]`, `[ start_date, end_date, path, action_type ]`, `[ start_date, end_date, path, username ]`, `[ start_date, end_date, true_path, remote_ip ]`, `[ start_date, end_date, true_path, success ]`, `[ start_date, end_date, true_path, action_type ]`, `[ start_date, end_date, true_path, username ]`, `[ start_date, end_date, remote_ip, success ]`, `[ start_date, end_date, remote_ip, action_type ]`, `[ start_date, end_date, remote_ip, username ]`, `[ start_date, end_date, success, action_type ]`, `[ start_date, end_date, success, username ]`, `[ start_date, end_date, action_type, username ]`, `[ start_date, path, true_path, remote_ip ]`, `[ start_date, path, true_path, success ]`, `[ start_date, path, true_path, action_type ]`, `[ start_date, path, true_path, username ]`, `[ start_date, path, remote_ip, success ]`, `[ start_date, path, remote_ip, action_type ]`, `[ start_date, path, remote_ip, username ]`, `[ start_date, path, success, action_type ]`, `[ start_date, path, success, username ]`, `[ start_date, path, action_type, username ]`, `[ start_date, true_path, remote_ip, success ]`, `[ start_date, true_path, remote_ip, action_type ]`, `[ start_date, true_path, remote_ip, username ]`, `[ start_date, true_path, success, action_type ]`, `[ start_date, true_path, success, username ]`, `[ start_date, true_path, action_type, username ]`, `[ start_date, remote_ip, success, action_type ]`, `[ start_date, remote_ip, success, username ]`, `[ start_date, remote_ip, action_type, username ]`, `[ start_date, success, action_type, username ]`, `[ end_date, path, true_path, remote_ip ]`, `[ end_date, path, true_path, success ]`, `[ end_date, path, true_path, action_type ]`, `[ end_date, path, true_path, username ]`, `[ end_date, path, remote_ip, success ]`, `[ end_date, path, remote_ip, action_type ]`, `[ end_date, path, remote_ip, username ]`, `[ end_date, path, success, action_type ]`, `[ end_date, path, success, username ]`, `[ end_date, path, action_type, username ]`, `[ end_date, true_path, remote_ip, success ]`, `[ end_date, true_path, remote_ip, action_type ]`, `[ end_date, true_path, remote_ip, username ]`, `[ end_date, true_path, success, action_type ]`, `[ end_date, true_path, success, username ]`, `[ end_date, true_path, action_type, username ]`, `[ end_date, remote_ip, success, action_type ]`, `[ end_date, remote_ip, success, username ]`, `[ end_date, remote_ip, action_type, username ]`, `[ end_date, success, action_type, username ]`, `[ path, true_path, remote_ip, success ]`, `[ path, true_path, remote_ip, action_type ]`, `[ path, true_path, remote_ip, username ]`, `[ path, true_path, success, action_type ]`, `[ path, true_path, success, username ]`, `[ path, true_path, action_type, username ]`, `[ path, remote_ip, success, action_type ]`, `[ path, remote_ip, success, username ]`, `[ path, remote_ip, action_type, username ]`, `[ path, success, action_type, username ]`, `[ true_path, remote_ip, success, action_type ]`, `[ true_path, remote_ip, success, username ]`, `[ true_path, remote_ip, action_type, username ]`, `[ true_path, success, action_type, username ]`, `[ remote_ip, success, action_type, username ]`, `[ start_date, end_date, path, true_path, remote_ip ]`, `[ start_date, end_date, path, true_path, success ]`, `[ start_date, end_date, path, true_path, action_type ]`, `[ start_date, end_date, path, true_path, username ]`, `[ start_date, end_date, path, remote_ip, success ]`, `[ start_date, end_date, path, remote_ip, action_type ]`, `[ start_date, end_date, path, remote_ip, username ]`, `[ start_date, end_date, path, success, action_type ]`, `[ start_date, end_date, path, success, username ]`, `[ start_date, end_date, path, action_type, username ]`, `[ start_date, end_date, true_path, remote_ip, success ]`, `[ start_date, end_date, true_path, remote_ip, action_type ]`, `[ start_date, end_date, true_path, remote_ip, username ]`, `[ start_date, end_date, true_path, success, action_type ]`, `[ start_date, end_date, true_path, success, username ]`, `[ start_date, end_date, true_path, action_type, username ]`, `[ start_date, end_date, remote_ip, success, action_type ]`, `[ start_date, end_date, remote_ip, success, username ]`, `[ start_date, end_date, remote_ip, action_type, username ]`, `[ start_date, end_date, success, action_type, username ]`, `[ start_date, path, true_path, remote_ip, success ]`, `[ start_date, path, true_path, remote_ip, action_type ]`, `[ start_date, path, true_path, remote_ip, username ]`, `[ start_date, path, true_path, success, action_type ]`, `[ start_date, path, true_path, success, username ]`, `[ start_date, path, true_path, action_type, username ]`, `[ start_date, path, remote_ip, success, action_type ]`, `[ start_date, path, remote_ip, success, username ]`, `[ start_date, path, remote_ip, action_type, username ]`, `[ start_date, path, success, action_type, username ]`, `[ start_date, true_path, remote_ip, success, action_type ]`, `[ start_date, true_path, remote_ip, success, username ]`, `[ start_date, true_path, remote_ip, action_type, username ]`, `[ start_date, true_path, success, action_type, username ]`, `[ start_date, remote_ip, success, action_type, username ]`, `[ end_date, path, true_path, remote_ip, success ]`, `[ end_date, path, true_path, remote_ip, action_type ]`, `[ end_date, path, true_path, remote_ip, username ]`, `[ end_date, path, true_path, success, action_type ]`, `[ end_date, path, true_path, success, username ]`, `[ end_date, path, true_path, action_type, username ]`, `[ end_date, path, remote_ip, success, action_type ]`, `[ end_date, path, remote_ip, success, username ]`, `[ end_date, path, remote_ip, action_type, username ]`, `[ end_date, path, success, action_type, username ]`, `[ end_date, true_path, remote_ip, success, action_type ]`, `[ end_date, true_path, remote_ip, success, username ]`, `[ end_date, true_path, remote_ip, action_type, username ]`, `[ end_date, true_path, success, action_type, username ]`, `[ end_date, remote_ip, success, action_type, username ]`, `[ path, true_path, remote_ip, success, action_type ]`, `[ path, true_path, remote_ip, success, username ]`, `[ path, true_path, remote_ip, action_type, username ]`, `[ path, true_path, success, action_type, username ]`, `[ path, remote_ip, success, action_type, username ]`, `[ true_path, remote_ip, success, action_type, username ]`, `[ start_date, end_date, path, true_path, remote_ip, success ]`, `[ start_date, end_date, path, true_path, remote_ip, action_type ]`, `[ start_date, end_date, path, true_path, remote_ip, username ]`, `[ start_date, end_date, path, true_path, success, action_type ]`, `[ start_date, end_date, path, true_path, success, username ]`, `[ start_date, end_date, path, true_path, action_type, username ]`, `[ start_date, end_date, path, remote_ip, success, action_type ]`, `[ start_date, end_date, path, remote_ip, success, username ]`, `[ start_date, end_date, path, remote_ip, action_type, username ]`, `[ start_date, end_date, path, success, action_type, username ]`, `[ start_date, end_date, true_path, remote_ip, success, action_type ]`, `[ start_date, end_date, true_path, remote_ip, success, username ]`, `[ start_date, end_date, true_path, remote_ip, action_type, username ]`, `[ start_date, end_date, true_path, success, action_type, username ]`, `[ start_date, end_date, remote_ip, success, action_type, username ]`, `[ start_date, path, true_path, remote_ip, success, action_type ]`, `[ start_date, path, true_path, remote_ip, success, username ]`, `[ start_date, path, true_path, remote_ip, action_type, username ]`, `[ start_date, path, true_path, success, action_type, username ]`, `[ start_date, path, remote_ip, success, action_type, username ]`, `[ start_date, true_path, remote_ip, success, action_type, username ]`, `[ end_date, path, true_path, remote_ip, success, action_type ]`, `[ end_date, path, true_path, remote_ip, success, username ]`, `[ end_date, path, true_path, remote_ip, action_type, username ]`, `[ end_date, path, true_path, success, action_type, username ]`, `[ end_date, path, remote_ip, success, action_type, username ]`, `[ end_date, true_path, remote_ip, success, action_type, username ]`, `[ path, true_path, remote_ip, success, action_type, username ]`, `[ start_date, end_date, path, true_path, remote_ip, success, action_type ]`, `[ start_date, end_date, path, true_path, remote_ip, success, username ]`, `[ start_date, end_date, path, true_path, remote_ip, action_type, username ]`, `[ start_date, end_date, path, true_path, success, action_type, username ]`, `[ start_date, end_date, path, remote_ip, success, action_type, username ]`, `[ start_date, end_date, true_path, remote_ip, success, action_type, username ]`, `[ start_date, path, true_path, remote_ip, success, action_type, username ]` or `[ end_date, path, true_path, remote_ip, success, action_type, username ]`.
  *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `path`, `true_path`, `action_type` or `username`. Valid field combinations are `[ start_date ]`, `[ end_date ]`, `[ path ]`, `[ true_path ]`, `[ remote_ip ]`, `[ success ]`, `[ action_type ]`, `[ username ]`, `[ start_date, end_date ]`, `[ start_date, path ]`, `[ start_date, true_path ]`, `[ start_date, remote_ip ]`, `[ start_date, success ]`, `[ start_date, action_type ]`, `[ start_date, username ]`, `[ end_date, path ]`, `[ end_date, true_path ]`, `[ end_date, remote_ip ]`, `[ end_date, success ]`, `[ end_date, action_type ]`, `[ end_date, username ]`, `[ path, true_path ]`, `[ path, remote_ip ]`, `[ path, success ]`, `[ path, action_type ]`, `[ path, username ]`, `[ true_path, remote_ip ]`, `[ true_path, success ]`, `[ true_path, action_type ]`, `[ true_path, username ]`, `[ remote_ip, success ]`, `[ remote_ip, action_type ]`, `[ remote_ip, username ]`, `[ success, action_type ]`, `[ success, username ]`, `[ action_type, username ]`, `[ start_date, end_date, path ]`, `[ start_date, end_date, true_path ]`, `[ start_date, end_date, remote_ip ]`, `[ start_date, end_date, success ]`, `[ start_date, end_date, action_type ]`, `[ start_date, end_date, username ]`, `[ start_date, path, true_path ]`, `[ start_date, path, remote_ip ]`, `[ start_date, path, success ]`, `[ start_date, path, action_type ]`, `[ start_date, path, username ]`, `[ start_date, true_path, remote_ip ]`, `[ start_date, true_path, success ]`, `[ start_date, true_path, action_type ]`, `[ start_date, true_path, username ]`, `[ start_date, remote_ip, success ]`, `[ start_date, remote_ip, action_type ]`, `[ start_date, remote_ip, username ]`, `[ start_date, success, action_type ]`, `[ start_date, success, username ]`, `[ start_date, action_type, username ]`, `[ end_date, path, true_path ]`, `[ end_date, path, remote_ip ]`, `[ end_date, path, success ]`, `[ end_date, path, action_type ]`, `[ end_date, path, username ]`, `[ end_date, true_path, remote_ip ]`, `[ end_date, true_path, success ]`, `[ end_date, true_path, action_type ]`, `[ end_date, true_path, username ]`, `[ end_date, remote_ip, success ]`, `[ end_date, remote_ip, action_type ]`, `[ end_date, remote_ip, username ]`, `[ end_date, success, action_type ]`, `[ end_date, success, username ]`, `[ end_date, action_type, username ]`, `[ path, true_path, remote_ip ]`, `[ path, true_path, success ]`, `[ path, true_path, action_type ]`, `[ path, true_path, username ]`, `[ path, remote_ip, success ]`, `[ path, remote_ip, action_type ]`, `[ path, remote_ip, username ]`, `[ path, success, action_type ]`, `[ path, success, username ]`, `[ path, action_type, username ]`, `[ true_path, remote_ip, success ]`, `[ true_path, remote_ip, action_type ]`, `[ true_path, remote_ip, username ]`, `[ true_path, success, action_type ]`, `[ true_path, success, username ]`, `[ true_path, action_type, username ]`, `[ remote_ip, success, action_type ]`, `[ remote_ip, success, username ]`, `[ remote_ip, action_type, username ]`, `[ success, action_type, username ]`, `[ start_date, end_date, path, true_path ]`, `[ start_date, end_date, path, remote_ip ]`, `[ start_date, end_date, path, success ]`, `[ start_date, end_date, path, action_type ]`, `[ start_date, end_date, path, username ]`, `[ start_date, end_date, true_path, remote_ip ]`, `[ start_date, end_date, true_path, success ]`, `[ start_date, end_date, true_path, action_type ]`, `[ start_date, end_date, true_path, username ]`, `[ start_date, end_date, remote_ip, success ]`, `[ start_date, end_date, remote_ip, action_type ]`, `[ start_date, end_date, remote_ip, username ]`, `[ start_date, end_date, success, action_type ]`, `[ start_date, end_date, success, username ]`, `[ start_date, end_date, action_type, username ]`, `[ start_date, path, true_path, remote_ip ]`, `[ start_date, path, true_path, success ]`, `[ start_date, path, true_path, action_type ]`, `[ start_date, path, true_path, username ]`, `[ start_date, path, remote_ip, success ]`, `[ start_date, path, remote_ip, action_type ]`, `[ start_date, path, remote_ip, username ]`, `[ start_date, path, success, action_type ]`, `[ start_date, path, success, username ]`, `[ start_date, path, action_type, username ]`, `[ start_date, true_path, remote_ip, success ]`, `[ start_date, true_path, remote_ip, action_type ]`, `[ start_date, true_path, remote_ip, username ]`, `[ start_date, true_path, success, action_type ]`, `[ start_date, true_path, success, username ]`, `[ start_date, true_path, action_type, username ]`, `[ start_date, remote_ip, success, action_type ]`, `[ start_date, remote_ip, success, username ]`, `[ start_date, remote_ip, action_type, username ]`, `[ start_date, success, action_type, username ]`, `[ end_date, path, true_path, remote_ip ]`, `[ end_date, path, true_path, success ]`, `[ end_date, path, true_path, action_type ]`, `[ end_date, path, true_path, username ]`, `[ end_date, path, remote_ip, success ]`, `[ end_date, path, remote_ip, action_type ]`, `[ end_date, path, remote_ip, username ]`, `[ end_date, path, success, action_type ]`, `[ end_date, path, success, username ]`, `[ end_date, path, action_type, username ]`, `[ end_date, true_path, remote_ip, success ]`, `[ end_date, true_path, remote_ip, action_type ]`, `[ end_date, true_path, remote_ip, username ]`, `[ end_date, true_path, success, action_type ]`, `[ end_date, true_path, success, username ]`, `[ end_date, true_path, action_type, username ]`, `[ end_date, remote_ip, success, action_type ]`, `[ end_date, remote_ip, success, username ]`, `[ end_date, remote_ip, action_type, username ]`, `[ end_date, success, action_type, username ]`, `[ path, true_path, remote_ip, success ]`, `[ path, true_path, remote_ip, action_type ]`, `[ path, true_path, remote_ip, username ]`, `[ path, true_path, success, action_type ]`, `[ path, true_path, success, username ]`, `[ path, true_path, action_type, username ]`, `[ path, remote_ip, success, action_type ]`, `[ path, remote_ip, success, username ]`, `[ path, remote_ip, action_type, username ]`, `[ path, success, action_type, username ]`, `[ true_path, remote_ip, success, action_type ]`, `[ true_path, remote_ip, success, username ]`, `[ true_path, remote_ip, action_type, username ]`, `[ true_path, success, action_type, username ]`, `[ remote_ip, success, action_type, username ]`, `[ start_date, end_date, path, true_path, remote_ip ]`, `[ start_date, end_date, path, true_path, success ]`, `[ start_date, end_date, path, true_path, action_type ]`, `[ start_date, end_date, path, true_path, username ]`, `[ start_date, end_date, path, remote_ip, success ]`, `[ start_date, end_date, path, remote_ip, action_type ]`, `[ start_date, end_date, path, remote_ip, username ]`, `[ start_date, end_date, path, success, action_type ]`, `[ start_date, end_date, path, success, username ]`, `[ start_date, end_date, path, action_type, username ]`, `[ start_date, end_date, true_path, remote_ip, success ]`, `[ start_date, end_date, true_path, remote_ip, action_type ]`, `[ start_date, end_date, true_path, remote_ip, username ]`, `[ start_date, end_date, true_path, success, action_type ]`, `[ start_date, end_date, true_path, success, username ]`, `[ start_date, end_date, true_path, action_type, username ]`, `[ start_date, end_date, remote_ip, success, action_type ]`, `[ start_date, end_date, remote_ip, success, username ]`, `[ start_date, end_date, remote_ip, action_type, username ]`, `[ start_date, end_date, success, action_type, username ]`, `[ start_date, path, true_path, remote_ip, success ]`, `[ start_date, path, true_path, remote_ip, action_type ]`, `[ start_date, path, true_path, remote_ip, username ]`, `[ start_date, path, true_path, success, action_type ]`, `[ start_date, path, true_path, success, username ]`, `[ start_date, path, true_path, action_type, username ]`, `[ start_date, path, remote_ip, success, action_type ]`, `[ start_date, path, remote_ip, success, username ]`, `[ start_date, path, remote_ip, action_type, username ]`, `[ start_date, path, success, action_type, username ]`, `[ start_date, true_path, remote_ip, success, action_type ]`, `[ start_date, true_path, remote_ip, success, username ]`, `[ start_date, true_path, remote_ip, action_type, username ]`, `[ start_date, true_path, success, action_type, username ]`, `[ start_date, remote_ip, success, action_type, username ]`, `[ end_date, path, true_path, remote_ip, success ]`, `[ end_date, path, true_path, remote_ip, action_type ]`, `[ end_date, path, true_path, remote_ip, username ]`, `[ end_date, path, true_path, success, action_type ]`, `[ end_date, path, true_path, success, username ]`, `[ end_date, path, true_path, action_type, username ]`, `[ end_date, path, remote_ip, success, action_type ]`, `[ end_date, path, remote_ip, success, username ]`, `[ end_date, path, remote_ip, action_type, username ]`, `[ end_date, path, success, action_type, username ]`, `[ end_date, true_path, remote_ip, success, action_type ]`, `[ end_date, true_path, remote_ip, success, username ]`, `[ end_date, true_path, remote_ip, action_type, username ]`, `[ end_date, true_path, success, action_type, username ]`, `[ end_date, remote_ip, success, action_type, username ]`, `[ path, true_path, remote_ip, success, action_type ]`, `[ path, true_path, remote_ip, success, username ]`, `[ path, true_path, remote_ip, action_type, username ]`, `[ path, true_path, success, action_type, username ]`, `[ path, remote_ip, success, action_type, username ]`, `[ true_path, remote_ip, success, action_type, username ]`, `[ start_date, end_date, path, true_path, remote_ip, success ]`, `[ start_date, end_date, path, true_path, remote_ip, action_type ]`, `[ start_date, end_date, path, true_path, remote_ip, username ]`, `[ start_date, end_date, path, true_path, success, action_type ]`, `[ start_date, end_date, path, true_path, success, username ]`, `[ start_date, end_date, path, true_path, action_type, username ]`, `[ start_date, end_date, path, remote_ip, success, action_type ]`, `[ start_date, end_date, path, remote_ip, success, username ]`, `[ start_date, end_date, path, remote_ip, action_type, username ]`, `[ start_date, end_date, path, success, action_type, username ]`, `[ start_date, end_date, true_path, remote_ip, success, action_type ]`, `[ start_date, end_date, true_path, remote_ip, success, username ]`, `[ start_date, end_date, true_path, remote_ip, action_type, username ]`, `[ start_date, end_date, true_path, success, action_type, username ]`, `[ start_date, end_date, remote_ip, success, action_type, username ]`, `[ start_date, path, true_path, remote_ip, success, action_type ]`, `[ start_date, path, true_path, remote_ip, success, username ]`, `[ start_date, path, true_path, remote_ip, action_type, username ]`, `[ start_date, path, true_path, success, action_type, username ]`, `[ start_date, path, remote_ip, success, action_type, username ]`, `[ start_date, true_path, remote_ip, success, action_type, username ]`, `[ end_date, path, true_path, remote_ip, success, action_type ]`, `[ end_date, path, true_path, remote_ip, success, username ]`, `[ end_date, path, true_path, remote_ip, action_type, username ]`, `[ end_date, path, true_path, success, action_type, username ]`, `[ end_date, path, remote_ip, success, action_type, username ]`, `[ end_date, true_path, remote_ip, success, action_type, username ]`, `[ path, true_path, remote_ip, success, action_type, username ]`, `[ start_date, end_date, path, true_path, remote_ip, success, action_type ]`, `[ start_date, end_date, path, true_path, remote_ip, success, username ]`, `[ start_date, end_date, path, true_path, remote_ip, action_type, username ]`, `[ start_date, end_date, path, true_path, success, action_type, username ]`, `[ start_date, end_date, path, remote_ip, success, action_type, username ]`, `[ start_date, end_date, true_path, remote_ip, success, action_type, username ]`, `[ start_date, path, true_path, remote_ip, success, action_type, username ]` or `[ end_date, path, true_path, remote_ip, success, action_type, username ]`.
  */
  public static ListIterator<WebDavActionLog> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<WebDavActionLog> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<WebDavActionLog> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }
    if (parameters.containsKey("action") && !(parameters.get("action") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: action must be of type String parameters[\"action\"]");
    }
    if (parameters.containsKey("page") && !(parameters.get("page") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: page must be of type Long parameters[\"page\"]");
    }
    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Map<String, String> parameters[\"filter\"]");
    }
    if (parameters.containsKey("filter_prefix") && !(parameters.get("filter_prefix") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_prefix must be of type Map<String, String> parameters[\"filter_prefix\"]");
    }


    String url = String.format("%s%s/web_dav_action_logs", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<WebDavActionLog>> typeReference = new TypeReference<List<WebDavActionLog>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<WebDavActionLog> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<WebDavActionLog> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
