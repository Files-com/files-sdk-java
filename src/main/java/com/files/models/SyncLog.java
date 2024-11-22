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
public class SyncLog implements ModelInterface {
  @Setter
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();


  public SyncLog() {
    this(null, null);
  }

  public SyncLog(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public SyncLog(HashMap<String, Object> parameters, HashMap<String, Object> options) {
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
  * Sync ID
  */
  @Getter
  @JsonProperty("sync_id")
  public Long syncId;

  /**
  * External Event ID
  */
  @Getter
  @JsonProperty("external_event_id")
  public Long externalEventId;

  /**
  * Error type, if applicable
  */
  @Getter
  @JsonProperty("error_type")
  public String errorType;

  /**
  * Message
  */
  @Getter
  @JsonProperty("message")
  public String message;

  /**
  * Operation type
  */
  @Getter
  @JsonProperty("operation")
  public String operation;

  /**
  * File path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @Getter
  @JsonProperty("path")
  public String path;

  /**
  * File size
  */
  @Getter
  @JsonProperty("size")
  public String size;

  /**
  * File type
  */
  @Getter
  @JsonProperty("file_type")
  public String fileType;

  /**
  * Status
  */
  @Getter
  @JsonProperty("status")
  public String status;



  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `start_date`, `end_date`, `external_event_id`, `operation`, `status` or `sync_id`. Valid field combinations are `[ start_date ]`, `[ end_date ]`, `[ external_event_id ]`, `[ operation ]`, `[ status ]`, `[ sync_id ]`, `[ start_date, end_date ]`, `[ start_date, external_event_id ]`, `[ start_date, operation ]`, `[ start_date, status ]`, `[ start_date, sync_id ]`, `[ end_date, external_event_id ]`, `[ end_date, operation ]`, `[ end_date, status ]`, `[ end_date, sync_id ]`, `[ external_event_id, operation ]`, `[ external_event_id, status ]`, `[ external_event_id, sync_id ]`, `[ operation, status ]`, `[ operation, sync_id ]`, `[ status, sync_id ]`, `[ start_date, end_date, external_event_id ]`, `[ start_date, end_date, operation ]`, `[ start_date, end_date, status ]`, `[ start_date, end_date, sync_id ]`, `[ start_date, external_event_id, operation ]`, `[ start_date, external_event_id, status ]`, `[ start_date, external_event_id, sync_id ]`, `[ start_date, operation, status ]`, `[ start_date, operation, sync_id ]`, `[ start_date, status, sync_id ]`, `[ end_date, external_event_id, operation ]`, `[ end_date, external_event_id, status ]`, `[ end_date, external_event_id, sync_id ]`, `[ end_date, operation, status ]`, `[ end_date, operation, sync_id ]`, `[ end_date, status, sync_id ]`, `[ external_event_id, operation, status ]`, `[ external_event_id, operation, sync_id ]`, `[ external_event_id, status, sync_id ]`, `[ operation, status, sync_id ]`, `[ start_date, end_date, external_event_id, operation ]`, `[ start_date, end_date, external_event_id, status ]`, `[ start_date, end_date, external_event_id, sync_id ]`, `[ start_date, end_date, operation, status ]`, `[ start_date, end_date, operation, sync_id ]`, `[ start_date, end_date, status, sync_id ]`, `[ start_date, external_event_id, operation, status ]`, `[ start_date, external_event_id, operation, sync_id ]`, `[ start_date, external_event_id, status, sync_id ]`, `[ start_date, operation, status, sync_id ]`, `[ end_date, external_event_id, operation, status ]`, `[ end_date, external_event_id, operation, sync_id ]`, `[ end_date, external_event_id, status, sync_id ]`, `[ end_date, operation, status, sync_id ]`, `[ external_event_id, operation, status, sync_id ]`, `[ start_date, end_date, external_event_id, operation, status ]`, `[ start_date, end_date, external_event_id, operation, sync_id ]`, `[ start_date, end_date, external_event_id, status, sync_id ]`, `[ start_date, end_date, operation, status, sync_id ]`, `[ start_date, external_event_id, operation, status, sync_id ]` or `[ end_date, external_event_id, operation, status, sync_id ]`.
  *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `operation` and `status`. Valid field combinations are `[ start_date ]`, `[ end_date ]`, `[ external_event_id ]`, `[ operation ]`, `[ status ]`, `[ sync_id ]`, `[ start_date, end_date ]`, `[ start_date, external_event_id ]`, `[ start_date, operation ]`, `[ start_date, status ]`, `[ start_date, sync_id ]`, `[ end_date, external_event_id ]`, `[ end_date, operation ]`, `[ end_date, status ]`, `[ end_date, sync_id ]`, `[ external_event_id, operation ]`, `[ external_event_id, status ]`, `[ external_event_id, sync_id ]`, `[ operation, status ]`, `[ operation, sync_id ]`, `[ status, sync_id ]`, `[ start_date, end_date, external_event_id ]`, `[ start_date, end_date, operation ]`, `[ start_date, end_date, status ]`, `[ start_date, end_date, sync_id ]`, `[ start_date, external_event_id, operation ]`, `[ start_date, external_event_id, status ]`, `[ start_date, external_event_id, sync_id ]`, `[ start_date, operation, status ]`, `[ start_date, operation, sync_id ]`, `[ start_date, status, sync_id ]`, `[ end_date, external_event_id, operation ]`, `[ end_date, external_event_id, status ]`, `[ end_date, external_event_id, sync_id ]`, `[ end_date, operation, status ]`, `[ end_date, operation, sync_id ]`, `[ end_date, status, sync_id ]`, `[ external_event_id, operation, status ]`, `[ external_event_id, operation, sync_id ]`, `[ external_event_id, status, sync_id ]`, `[ operation, status, sync_id ]`, `[ start_date, end_date, external_event_id, operation ]`, `[ start_date, end_date, external_event_id, status ]`, `[ start_date, end_date, external_event_id, sync_id ]`, `[ start_date, end_date, operation, status ]`, `[ start_date, end_date, operation, sync_id ]`, `[ start_date, end_date, status, sync_id ]`, `[ start_date, external_event_id, operation, status ]`, `[ start_date, external_event_id, operation, sync_id ]`, `[ start_date, external_event_id, status, sync_id ]`, `[ start_date, operation, status, sync_id ]`, `[ end_date, external_event_id, operation, status ]`, `[ end_date, external_event_id, operation, sync_id ]`, `[ end_date, external_event_id, status, sync_id ]`, `[ end_date, operation, status, sync_id ]`, `[ external_event_id, operation, status, sync_id ]`, `[ start_date, end_date, external_event_id, operation, status ]`, `[ start_date, end_date, external_event_id, operation, sync_id ]`, `[ start_date, end_date, external_event_id, status, sync_id ]`, `[ start_date, end_date, operation, status, sync_id ]`, `[ start_date, external_event_id, operation, status, sync_id ]` or `[ end_date, external_event_id, operation, status, sync_id ]`.
  */
  public static ListIterator<SyncLog> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<SyncLog> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<SyncLog> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }
    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Map<String, String> parameters[\"filter\"]");
    }
    if (parameters.containsKey("filter_prefix") && !(parameters.get("filter_prefix") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_prefix must be of type Map<String, String> parameters[\"filter_prefix\"]");
    }


    String url = String.format("%s%s/sync_logs", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<SyncLog>> typeReference = new TypeReference<List<SyncLog>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<SyncLog> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<SyncLog> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
