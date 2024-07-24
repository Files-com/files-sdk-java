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
public class OutboundConnectionLog {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();


  public OutboundConnectionLog() {
    this(null, null);
  }

  public OutboundConnectionLog(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public OutboundConnectionLog(HashMap<String, Object> parameters, HashMap<String, Object> options) {
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
  * Remote Path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @Getter
  @JsonProperty("path")
  public String path;

  /**
  * End User IP
  */
  @Getter
  @JsonProperty("client_ip")
  public String clientIp;

  /**
  * Source Remote Server ID
  */
  @Getter
  @JsonProperty("src_remote_server_id")
  public String srcRemoteServerId;

  /**
  * Destination Remote Server ID
  */
  @Getter
  @JsonProperty("dest_remote_server_id")
  public String destRemoteServerId;

  /**
  * Operation Type
  */
  @Getter
  @JsonProperty("operation")
  public String operation;

  /**
  * Error message, if applicable
  */
  @Getter
  @JsonProperty("error_message")
  public String errorMessage;

  /**
  * Error operation, if applicable
  */
  @Getter
  @JsonProperty("error_operation")
  public String errorOperation;

  /**
  * Error type, if applicable
  */
  @Getter
  @JsonProperty("error_type")
  public String errorType;

  /**
  * Status
  */
  @Getter
  @JsonProperty("status")
  public String status;

  /**
  * Duration (in milliseconds)
  */
  @Getter
  @JsonProperty("duration_ms")
  public Long durationMs;

  /**
  * Data Length in Bytes. Present for upload actions that transfer data.
  */
  @Getter
  @JsonProperty("bytes_uploaded")
  public Long bytesUploaded;

  /**
  * Data Length in Bytes. Present for download actions that transfer data.
  */
  @Getter
  @JsonProperty("bytes_downloaded")
  public Long bytesDownloaded;

  /**
  * Number of entries returned for a folder list action.
  */
  @Getter
  @JsonProperty("list_count")
  public Long listCount;



  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string
  *   page - int64
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `start_date`, `end_date`, `operation`, `status`, `src_remote_server_id`, `dest_remote_server_id`, `path` or `client_ip`. Valid field combinations are `[ start_date ]`, `[ end_date ]`, `[ operation ]`, `[ status ]`, `[ src_remote_server_id ]`, `[ dest_remote_server_id ]`, `[ path ]`, `[ client_ip ]`, `[ start_date, end_date ]`, `[ start_date, operation ]`, `[ start_date, status ]`, `[ start_date, src_remote_server_id ]`, `[ start_date, dest_remote_server_id ]`, `[ start_date, path ]`, `[ start_date, client_ip ]`, `[ end_date, operation ]`, `[ end_date, status ]`, `[ end_date, src_remote_server_id ]`, `[ end_date, dest_remote_server_id ]`, `[ end_date, path ]`, `[ end_date, client_ip ]`, `[ operation, status ]`, `[ operation, src_remote_server_id ]`, `[ operation, dest_remote_server_id ]`, `[ operation, path ]`, `[ operation, client_ip ]`, `[ status, src_remote_server_id ]`, `[ status, dest_remote_server_id ]`, `[ status, path ]`, `[ status, client_ip ]`, `[ src_remote_server_id, dest_remote_server_id ]`, `[ src_remote_server_id, path ]`, `[ src_remote_server_id, client_ip ]`, `[ dest_remote_server_id, path ]`, `[ dest_remote_server_id, client_ip ]`, `[ path, client_ip ]`, `[ start_date, end_date, operation ]`, `[ start_date, end_date, status ]`, `[ start_date, end_date, src_remote_server_id ]`, `[ start_date, end_date, dest_remote_server_id ]`, `[ start_date, end_date, path ]`, `[ start_date, end_date, client_ip ]`, `[ start_date, operation, status ]`, `[ start_date, operation, src_remote_server_id ]`, `[ start_date, operation, dest_remote_server_id ]`, `[ start_date, operation, path ]`, `[ start_date, operation, client_ip ]`, `[ start_date, status, src_remote_server_id ]`, `[ start_date, status, dest_remote_server_id ]`, `[ start_date, status, path ]`, `[ start_date, status, client_ip ]`, `[ start_date, src_remote_server_id, dest_remote_server_id ]`, `[ start_date, src_remote_server_id, path ]`, `[ start_date, src_remote_server_id, client_ip ]`, `[ start_date, dest_remote_server_id, path ]`, `[ start_date, dest_remote_server_id, client_ip ]`, `[ start_date, path, client_ip ]`, `[ end_date, operation, status ]`, `[ end_date, operation, src_remote_server_id ]`, `[ end_date, operation, dest_remote_server_id ]`, `[ end_date, operation, path ]`, `[ end_date, operation, client_ip ]`, `[ end_date, status, src_remote_server_id ]`, `[ end_date, status, dest_remote_server_id ]`, `[ end_date, status, path ]`, `[ end_date, status, client_ip ]`, `[ end_date, src_remote_server_id, dest_remote_server_id ]`, `[ end_date, src_remote_server_id, path ]`, `[ end_date, src_remote_server_id, client_ip ]`, `[ end_date, dest_remote_server_id, path ]`, `[ end_date, dest_remote_server_id, client_ip ]`, `[ end_date, path, client_ip ]`, `[ operation, status, src_remote_server_id ]`, `[ operation, status, dest_remote_server_id ]`, `[ operation, status, path ]`, `[ operation, status, client_ip ]`, `[ operation, src_remote_server_id, dest_remote_server_id ]`, `[ operation, src_remote_server_id, path ]`, `[ operation, src_remote_server_id, client_ip ]`, `[ operation, dest_remote_server_id, path ]`, `[ operation, dest_remote_server_id, client_ip ]`, `[ operation, path, client_ip ]`, `[ status, src_remote_server_id, dest_remote_server_id ]`, `[ status, src_remote_server_id, path ]`, `[ status, src_remote_server_id, client_ip ]`, `[ status, dest_remote_server_id, path ]`, `[ status, dest_remote_server_id, client_ip ]`, `[ status, path, client_ip ]`, `[ src_remote_server_id, dest_remote_server_id, path ]`, `[ src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ src_remote_server_id, path, client_ip ]`, `[ dest_remote_server_id, path, client_ip ]`, `[ start_date, end_date, operation, status ]`, `[ start_date, end_date, operation, src_remote_server_id ]`, `[ start_date, end_date, operation, dest_remote_server_id ]`, `[ start_date, end_date, operation, path ]`, `[ start_date, end_date, operation, client_ip ]`, `[ start_date, end_date, status, src_remote_server_id ]`, `[ start_date, end_date, status, dest_remote_server_id ]`, `[ start_date, end_date, status, path ]`, `[ start_date, end_date, status, client_ip ]`, `[ start_date, end_date, src_remote_server_id, dest_remote_server_id ]`, `[ start_date, end_date, src_remote_server_id, path ]`, `[ start_date, end_date, src_remote_server_id, client_ip ]`, `[ start_date, end_date, dest_remote_server_id, path ]`, `[ start_date, end_date, dest_remote_server_id, client_ip ]`, `[ start_date, end_date, path, client_ip ]`, `[ start_date, operation, status, src_remote_server_id ]`, `[ start_date, operation, status, dest_remote_server_id ]`, `[ start_date, operation, status, path ]`, `[ start_date, operation, status, client_ip ]`, `[ start_date, operation, src_remote_server_id, dest_remote_server_id ]`, `[ start_date, operation, src_remote_server_id, path ]`, `[ start_date, operation, src_remote_server_id, client_ip ]`, `[ start_date, operation, dest_remote_server_id, path ]`, `[ start_date, operation, dest_remote_server_id, client_ip ]`, `[ start_date, operation, path, client_ip ]`, `[ start_date, status, src_remote_server_id, dest_remote_server_id ]`, `[ start_date, status, src_remote_server_id, path ]`, `[ start_date, status, src_remote_server_id, client_ip ]`, `[ start_date, status, dest_remote_server_id, path ]`, `[ start_date, status, dest_remote_server_id, client_ip ]`, `[ start_date, status, path, client_ip ]`, `[ start_date, src_remote_server_id, dest_remote_server_id, path ]`, `[ start_date, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ start_date, src_remote_server_id, path, client_ip ]`, `[ start_date, dest_remote_server_id, path, client_ip ]`, `[ end_date, operation, status, src_remote_server_id ]`, `[ end_date, operation, status, dest_remote_server_id ]`, `[ end_date, operation, status, path ]`, `[ end_date, operation, status, client_ip ]`, `[ end_date, operation, src_remote_server_id, dest_remote_server_id ]`, `[ end_date, operation, src_remote_server_id, path ]`, `[ end_date, operation, src_remote_server_id, client_ip ]`, `[ end_date, operation, dest_remote_server_id, path ]`, `[ end_date, operation, dest_remote_server_id, client_ip ]`, `[ end_date, operation, path, client_ip ]`, `[ end_date, status, src_remote_server_id, dest_remote_server_id ]`, `[ end_date, status, src_remote_server_id, path ]`, `[ end_date, status, src_remote_server_id, client_ip ]`, `[ end_date, status, dest_remote_server_id, path ]`, `[ end_date, status, dest_remote_server_id, client_ip ]`, `[ end_date, status, path, client_ip ]`, `[ end_date, src_remote_server_id, dest_remote_server_id, path ]`, `[ end_date, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ end_date, src_remote_server_id, path, client_ip ]`, `[ end_date, dest_remote_server_id, path, client_ip ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id ]`, `[ operation, status, src_remote_server_id, path ]`, `[ operation, status, src_remote_server_id, client_ip ]`, `[ operation, status, dest_remote_server_id, path ]`, `[ operation, status, dest_remote_server_id, client_ip ]`, `[ operation, status, path, client_ip ]`, `[ operation, src_remote_server_id, dest_remote_server_id, path ]`, `[ operation, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ operation, src_remote_server_id, path, client_ip ]`, `[ operation, dest_remote_server_id, path, client_ip ]`, `[ status, src_remote_server_id, dest_remote_server_id, path ]`, `[ status, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ status, src_remote_server_id, path, client_ip ]`, `[ status, dest_remote_server_id, path, client_ip ]`, `[ src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ start_date, end_date, operation, status, src_remote_server_id ]`, `[ start_date, end_date, operation, status, dest_remote_server_id ]`, `[ start_date, end_date, operation, status, path ]`, `[ start_date, end_date, operation, status, client_ip ]`, `[ start_date, end_date, operation, src_remote_server_id, dest_remote_server_id ]`, `[ start_date, end_date, operation, src_remote_server_id, path ]`, `[ start_date, end_date, operation, src_remote_server_id, client_ip ]`, `[ start_date, end_date, operation, dest_remote_server_id, path ]`, `[ start_date, end_date, operation, dest_remote_server_id, client_ip ]`, `[ start_date, end_date, operation, path, client_ip ]`, `[ start_date, end_date, status, src_remote_server_id, dest_remote_server_id ]`, `[ start_date, end_date, status, src_remote_server_id, path ]`, `[ start_date, end_date, status, src_remote_server_id, client_ip ]`, `[ start_date, end_date, status, dest_remote_server_id, path ]`, `[ start_date, end_date, status, dest_remote_server_id, client_ip ]`, `[ start_date, end_date, status, path, client_ip ]`, `[ start_date, end_date, src_remote_server_id, dest_remote_server_id, path ]`, `[ start_date, end_date, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ start_date, end_date, src_remote_server_id, path, client_ip ]`, `[ start_date, end_date, dest_remote_server_id, path, client_ip ]`, `[ start_date, operation, status, src_remote_server_id, dest_remote_server_id ]`, `[ start_date, operation, status, src_remote_server_id, path ]`, `[ start_date, operation, status, src_remote_server_id, client_ip ]`, `[ start_date, operation, status, dest_remote_server_id, path ]`, `[ start_date, operation, status, dest_remote_server_id, client_ip ]`, `[ start_date, operation, status, path, client_ip ]`, `[ start_date, operation, src_remote_server_id, dest_remote_server_id, path ]`, `[ start_date, operation, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ start_date, operation, src_remote_server_id, path, client_ip ]`, `[ start_date, operation, dest_remote_server_id, path, client_ip ]`, `[ start_date, status, src_remote_server_id, dest_remote_server_id, path ]`, `[ start_date, status, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ start_date, status, src_remote_server_id, path, client_ip ]`, `[ start_date, status, dest_remote_server_id, path, client_ip ]`, `[ start_date, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ end_date, operation, status, src_remote_server_id, dest_remote_server_id ]`, `[ end_date, operation, status, src_remote_server_id, path ]`, `[ end_date, operation, status, src_remote_server_id, client_ip ]`, `[ end_date, operation, status, dest_remote_server_id, path ]`, `[ end_date, operation, status, dest_remote_server_id, client_ip ]`, `[ end_date, operation, status, path, client_ip ]`, `[ end_date, operation, src_remote_server_id, dest_remote_server_id, path ]`, `[ end_date, operation, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ end_date, operation, src_remote_server_id, path, client_ip ]`, `[ end_date, operation, dest_remote_server_id, path, client_ip ]`, `[ end_date, status, src_remote_server_id, dest_remote_server_id, path ]`, `[ end_date, status, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ end_date, status, src_remote_server_id, path, client_ip ]`, `[ end_date, status, dest_remote_server_id, path, client_ip ]`, `[ end_date, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, path ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ operation, status, src_remote_server_id, path, client_ip ]`, `[ operation, status, dest_remote_server_id, path, client_ip ]`, `[ operation, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ status, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ start_date, end_date, operation, status, src_remote_server_id, dest_remote_server_id ]`, `[ start_date, end_date, operation, status, src_remote_server_id, path ]`, `[ start_date, end_date, operation, status, src_remote_server_id, client_ip ]`, `[ start_date, end_date, operation, status, dest_remote_server_id, path ]`, `[ start_date, end_date, operation, status, dest_remote_server_id, client_ip ]`, `[ start_date, end_date, operation, status, path, client_ip ]`, `[ start_date, end_date, operation, src_remote_server_id, dest_remote_server_id, path ]`, `[ start_date, end_date, operation, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ start_date, end_date, operation, src_remote_server_id, path, client_ip ]`, `[ start_date, end_date, operation, dest_remote_server_id, path, client_ip ]`, `[ start_date, end_date, status, src_remote_server_id, dest_remote_server_id, path ]`, `[ start_date, end_date, status, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ start_date, end_date, status, src_remote_server_id, path, client_ip ]`, `[ start_date, end_date, status, dest_remote_server_id, path, client_ip ]`, `[ start_date, end_date, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ start_date, operation, status, src_remote_server_id, dest_remote_server_id, path ]`, `[ start_date, operation, status, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ start_date, operation, status, src_remote_server_id, path, client_ip ]`, `[ start_date, operation, status, dest_remote_server_id, path, client_ip ]`, `[ start_date, operation, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ start_date, status, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ end_date, operation, status, src_remote_server_id, dest_remote_server_id, path ]`, `[ end_date, operation, status, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ end_date, operation, status, src_remote_server_id, path, client_ip ]`, `[ end_date, operation, status, dest_remote_server_id, path, client_ip ]`, `[ end_date, operation, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ end_date, status, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ start_date, end_date, operation, status, src_remote_server_id, dest_remote_server_id, path ]`, `[ start_date, end_date, operation, status, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ start_date, end_date, operation, status, src_remote_server_id, path, client_ip ]`, `[ start_date, end_date, operation, status, dest_remote_server_id, path, client_ip ]`, `[ start_date, end_date, operation, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ start_date, end_date, status, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ start_date, operation, status, src_remote_server_id, dest_remote_server_id, path, client_ip ]` or `[ end_date, operation, status, src_remote_server_id, dest_remote_server_id, path, client_ip ]`.
  *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `operation`, `status`, `src_remote_server_id`, `dest_remote_server_id` or `path`. Valid field combinations are `[ start_date ]`, `[ end_date ]`, `[ operation ]`, `[ status ]`, `[ src_remote_server_id ]`, `[ dest_remote_server_id ]`, `[ path ]`, `[ client_ip ]`, `[ start_date, end_date ]`, `[ start_date, operation ]`, `[ start_date, status ]`, `[ start_date, src_remote_server_id ]`, `[ start_date, dest_remote_server_id ]`, `[ start_date, path ]`, `[ start_date, client_ip ]`, `[ end_date, operation ]`, `[ end_date, status ]`, `[ end_date, src_remote_server_id ]`, `[ end_date, dest_remote_server_id ]`, `[ end_date, path ]`, `[ end_date, client_ip ]`, `[ operation, status ]`, `[ operation, src_remote_server_id ]`, `[ operation, dest_remote_server_id ]`, `[ operation, path ]`, `[ operation, client_ip ]`, `[ status, src_remote_server_id ]`, `[ status, dest_remote_server_id ]`, `[ status, path ]`, `[ status, client_ip ]`, `[ src_remote_server_id, dest_remote_server_id ]`, `[ src_remote_server_id, path ]`, `[ src_remote_server_id, client_ip ]`, `[ dest_remote_server_id, path ]`, `[ dest_remote_server_id, client_ip ]`, `[ path, client_ip ]`, `[ start_date, end_date, operation ]`, `[ start_date, end_date, status ]`, `[ start_date, end_date, src_remote_server_id ]`, `[ start_date, end_date, dest_remote_server_id ]`, `[ start_date, end_date, path ]`, `[ start_date, end_date, client_ip ]`, `[ start_date, operation, status ]`, `[ start_date, operation, src_remote_server_id ]`, `[ start_date, operation, dest_remote_server_id ]`, `[ start_date, operation, path ]`, `[ start_date, operation, client_ip ]`, `[ start_date, status, src_remote_server_id ]`, `[ start_date, status, dest_remote_server_id ]`, `[ start_date, status, path ]`, `[ start_date, status, client_ip ]`, `[ start_date, src_remote_server_id, dest_remote_server_id ]`, `[ start_date, src_remote_server_id, path ]`, `[ start_date, src_remote_server_id, client_ip ]`, `[ start_date, dest_remote_server_id, path ]`, `[ start_date, dest_remote_server_id, client_ip ]`, `[ start_date, path, client_ip ]`, `[ end_date, operation, status ]`, `[ end_date, operation, src_remote_server_id ]`, `[ end_date, operation, dest_remote_server_id ]`, `[ end_date, operation, path ]`, `[ end_date, operation, client_ip ]`, `[ end_date, status, src_remote_server_id ]`, `[ end_date, status, dest_remote_server_id ]`, `[ end_date, status, path ]`, `[ end_date, status, client_ip ]`, `[ end_date, src_remote_server_id, dest_remote_server_id ]`, `[ end_date, src_remote_server_id, path ]`, `[ end_date, src_remote_server_id, client_ip ]`, `[ end_date, dest_remote_server_id, path ]`, `[ end_date, dest_remote_server_id, client_ip ]`, `[ end_date, path, client_ip ]`, `[ operation, status, src_remote_server_id ]`, `[ operation, status, dest_remote_server_id ]`, `[ operation, status, path ]`, `[ operation, status, client_ip ]`, `[ operation, src_remote_server_id, dest_remote_server_id ]`, `[ operation, src_remote_server_id, path ]`, `[ operation, src_remote_server_id, client_ip ]`, `[ operation, dest_remote_server_id, path ]`, `[ operation, dest_remote_server_id, client_ip ]`, `[ operation, path, client_ip ]`, `[ status, src_remote_server_id, dest_remote_server_id ]`, `[ status, src_remote_server_id, path ]`, `[ status, src_remote_server_id, client_ip ]`, `[ status, dest_remote_server_id, path ]`, `[ status, dest_remote_server_id, client_ip ]`, `[ status, path, client_ip ]`, `[ src_remote_server_id, dest_remote_server_id, path ]`, `[ src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ src_remote_server_id, path, client_ip ]`, `[ dest_remote_server_id, path, client_ip ]`, `[ start_date, end_date, operation, status ]`, `[ start_date, end_date, operation, src_remote_server_id ]`, `[ start_date, end_date, operation, dest_remote_server_id ]`, `[ start_date, end_date, operation, path ]`, `[ start_date, end_date, operation, client_ip ]`, `[ start_date, end_date, status, src_remote_server_id ]`, `[ start_date, end_date, status, dest_remote_server_id ]`, `[ start_date, end_date, status, path ]`, `[ start_date, end_date, status, client_ip ]`, `[ start_date, end_date, src_remote_server_id, dest_remote_server_id ]`, `[ start_date, end_date, src_remote_server_id, path ]`, `[ start_date, end_date, src_remote_server_id, client_ip ]`, `[ start_date, end_date, dest_remote_server_id, path ]`, `[ start_date, end_date, dest_remote_server_id, client_ip ]`, `[ start_date, end_date, path, client_ip ]`, `[ start_date, operation, status, src_remote_server_id ]`, `[ start_date, operation, status, dest_remote_server_id ]`, `[ start_date, operation, status, path ]`, `[ start_date, operation, status, client_ip ]`, `[ start_date, operation, src_remote_server_id, dest_remote_server_id ]`, `[ start_date, operation, src_remote_server_id, path ]`, `[ start_date, operation, src_remote_server_id, client_ip ]`, `[ start_date, operation, dest_remote_server_id, path ]`, `[ start_date, operation, dest_remote_server_id, client_ip ]`, `[ start_date, operation, path, client_ip ]`, `[ start_date, status, src_remote_server_id, dest_remote_server_id ]`, `[ start_date, status, src_remote_server_id, path ]`, `[ start_date, status, src_remote_server_id, client_ip ]`, `[ start_date, status, dest_remote_server_id, path ]`, `[ start_date, status, dest_remote_server_id, client_ip ]`, `[ start_date, status, path, client_ip ]`, `[ start_date, src_remote_server_id, dest_remote_server_id, path ]`, `[ start_date, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ start_date, src_remote_server_id, path, client_ip ]`, `[ start_date, dest_remote_server_id, path, client_ip ]`, `[ end_date, operation, status, src_remote_server_id ]`, `[ end_date, operation, status, dest_remote_server_id ]`, `[ end_date, operation, status, path ]`, `[ end_date, operation, status, client_ip ]`, `[ end_date, operation, src_remote_server_id, dest_remote_server_id ]`, `[ end_date, operation, src_remote_server_id, path ]`, `[ end_date, operation, src_remote_server_id, client_ip ]`, `[ end_date, operation, dest_remote_server_id, path ]`, `[ end_date, operation, dest_remote_server_id, client_ip ]`, `[ end_date, operation, path, client_ip ]`, `[ end_date, status, src_remote_server_id, dest_remote_server_id ]`, `[ end_date, status, src_remote_server_id, path ]`, `[ end_date, status, src_remote_server_id, client_ip ]`, `[ end_date, status, dest_remote_server_id, path ]`, `[ end_date, status, dest_remote_server_id, client_ip ]`, `[ end_date, status, path, client_ip ]`, `[ end_date, src_remote_server_id, dest_remote_server_id, path ]`, `[ end_date, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ end_date, src_remote_server_id, path, client_ip ]`, `[ end_date, dest_remote_server_id, path, client_ip ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id ]`, `[ operation, status, src_remote_server_id, path ]`, `[ operation, status, src_remote_server_id, client_ip ]`, `[ operation, status, dest_remote_server_id, path ]`, `[ operation, status, dest_remote_server_id, client_ip ]`, `[ operation, status, path, client_ip ]`, `[ operation, src_remote_server_id, dest_remote_server_id, path ]`, `[ operation, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ operation, src_remote_server_id, path, client_ip ]`, `[ operation, dest_remote_server_id, path, client_ip ]`, `[ status, src_remote_server_id, dest_remote_server_id, path ]`, `[ status, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ status, src_remote_server_id, path, client_ip ]`, `[ status, dest_remote_server_id, path, client_ip ]`, `[ src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ start_date, end_date, operation, status, src_remote_server_id ]`, `[ start_date, end_date, operation, status, dest_remote_server_id ]`, `[ start_date, end_date, operation, status, path ]`, `[ start_date, end_date, operation, status, client_ip ]`, `[ start_date, end_date, operation, src_remote_server_id, dest_remote_server_id ]`, `[ start_date, end_date, operation, src_remote_server_id, path ]`, `[ start_date, end_date, operation, src_remote_server_id, client_ip ]`, `[ start_date, end_date, operation, dest_remote_server_id, path ]`, `[ start_date, end_date, operation, dest_remote_server_id, client_ip ]`, `[ start_date, end_date, operation, path, client_ip ]`, `[ start_date, end_date, status, src_remote_server_id, dest_remote_server_id ]`, `[ start_date, end_date, status, src_remote_server_id, path ]`, `[ start_date, end_date, status, src_remote_server_id, client_ip ]`, `[ start_date, end_date, status, dest_remote_server_id, path ]`, `[ start_date, end_date, status, dest_remote_server_id, client_ip ]`, `[ start_date, end_date, status, path, client_ip ]`, `[ start_date, end_date, src_remote_server_id, dest_remote_server_id, path ]`, `[ start_date, end_date, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ start_date, end_date, src_remote_server_id, path, client_ip ]`, `[ start_date, end_date, dest_remote_server_id, path, client_ip ]`, `[ start_date, operation, status, src_remote_server_id, dest_remote_server_id ]`, `[ start_date, operation, status, src_remote_server_id, path ]`, `[ start_date, operation, status, src_remote_server_id, client_ip ]`, `[ start_date, operation, status, dest_remote_server_id, path ]`, `[ start_date, operation, status, dest_remote_server_id, client_ip ]`, `[ start_date, operation, status, path, client_ip ]`, `[ start_date, operation, src_remote_server_id, dest_remote_server_id, path ]`, `[ start_date, operation, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ start_date, operation, src_remote_server_id, path, client_ip ]`, `[ start_date, operation, dest_remote_server_id, path, client_ip ]`, `[ start_date, status, src_remote_server_id, dest_remote_server_id, path ]`, `[ start_date, status, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ start_date, status, src_remote_server_id, path, client_ip ]`, `[ start_date, status, dest_remote_server_id, path, client_ip ]`, `[ start_date, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ end_date, operation, status, src_remote_server_id, dest_remote_server_id ]`, `[ end_date, operation, status, src_remote_server_id, path ]`, `[ end_date, operation, status, src_remote_server_id, client_ip ]`, `[ end_date, operation, status, dest_remote_server_id, path ]`, `[ end_date, operation, status, dest_remote_server_id, client_ip ]`, `[ end_date, operation, status, path, client_ip ]`, `[ end_date, operation, src_remote_server_id, dest_remote_server_id, path ]`, `[ end_date, operation, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ end_date, operation, src_remote_server_id, path, client_ip ]`, `[ end_date, operation, dest_remote_server_id, path, client_ip ]`, `[ end_date, status, src_remote_server_id, dest_remote_server_id, path ]`, `[ end_date, status, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ end_date, status, src_remote_server_id, path, client_ip ]`, `[ end_date, status, dest_remote_server_id, path, client_ip ]`, `[ end_date, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, path ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ operation, status, src_remote_server_id, path, client_ip ]`, `[ operation, status, dest_remote_server_id, path, client_ip ]`, `[ operation, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ status, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ start_date, end_date, operation, status, src_remote_server_id, dest_remote_server_id ]`, `[ start_date, end_date, operation, status, src_remote_server_id, path ]`, `[ start_date, end_date, operation, status, src_remote_server_id, client_ip ]`, `[ start_date, end_date, operation, status, dest_remote_server_id, path ]`, `[ start_date, end_date, operation, status, dest_remote_server_id, client_ip ]`, `[ start_date, end_date, operation, status, path, client_ip ]`, `[ start_date, end_date, operation, src_remote_server_id, dest_remote_server_id, path ]`, `[ start_date, end_date, operation, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ start_date, end_date, operation, src_remote_server_id, path, client_ip ]`, `[ start_date, end_date, operation, dest_remote_server_id, path, client_ip ]`, `[ start_date, end_date, status, src_remote_server_id, dest_remote_server_id, path ]`, `[ start_date, end_date, status, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ start_date, end_date, status, src_remote_server_id, path, client_ip ]`, `[ start_date, end_date, status, dest_remote_server_id, path, client_ip ]`, `[ start_date, end_date, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ start_date, operation, status, src_remote_server_id, dest_remote_server_id, path ]`, `[ start_date, operation, status, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ start_date, operation, status, src_remote_server_id, path, client_ip ]`, `[ start_date, operation, status, dest_remote_server_id, path, client_ip ]`, `[ start_date, operation, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ start_date, status, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ end_date, operation, status, src_remote_server_id, dest_remote_server_id, path ]`, `[ end_date, operation, status, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ end_date, operation, status, src_remote_server_id, path, client_ip ]`, `[ end_date, operation, status, dest_remote_server_id, path, client_ip ]`, `[ end_date, operation, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ end_date, status, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ start_date, end_date, operation, status, src_remote_server_id, dest_remote_server_id, path ]`, `[ start_date, end_date, operation, status, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ start_date, end_date, operation, status, src_remote_server_id, path, client_ip ]`, `[ start_date, end_date, operation, status, dest_remote_server_id, path, client_ip ]`, `[ start_date, end_date, operation, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ start_date, end_date, status, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ start_date, operation, status, src_remote_server_id, dest_remote_server_id, path, client_ip ]` or `[ end_date, operation, status, src_remote_server_id, dest_remote_server_id, path, client_ip ]`.
  */
  public static ListIterator<OutboundConnectionLog> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<OutboundConnectionLog> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<OutboundConnectionLog> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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


    String url = String.format("%s%s/outbound_connection_logs", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<OutboundConnectionLog>> typeReference = new TypeReference<List<OutboundConnectionLog>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<OutboundConnectionLog> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<OutboundConnectionLog> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
