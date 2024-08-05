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
public class FileMigrationLog {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();


  public FileMigrationLog() {
    this(null, null);
  }

  public FileMigrationLog(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public FileMigrationLog(HashMap<String, Object> parameters, HashMap<String, Object> options) {
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
  * File Migration ID
  */
  @Getter
  @JsonProperty("file_migration_id")
  public Long fileMigrationId;

  /**
  * Destination path, for moves and copies
  */
  @Getter
  @JsonProperty("dest_path")
  public String destPath;

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
  * Status
  */
  @Getter
  @JsonProperty("status")
  public String status;



  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `start_date`, `end_date`, `file_migration_id`, `operation`, `status` or `type`. Valid field combinations are `[ start_date ]`, `[ end_date ]`, `[ file_migration_id ]`, `[ operation ]`, `[ status ]`, `[ type ]`, `[ start_date, end_date ]`, `[ start_date, file_migration_id ]`, `[ start_date, operation ]`, `[ start_date, status ]`, `[ start_date, type ]`, `[ end_date, file_migration_id ]`, `[ end_date, operation ]`, `[ end_date, status ]`, `[ end_date, type ]`, `[ file_migration_id, operation ]`, `[ file_migration_id, status ]`, `[ file_migration_id, type ]`, `[ operation, status ]`, `[ operation, type ]`, `[ status, type ]`, `[ start_date, end_date, file_migration_id ]`, `[ start_date, end_date, operation ]`, `[ start_date, end_date, status ]`, `[ start_date, end_date, type ]`, `[ start_date, file_migration_id, operation ]`, `[ start_date, file_migration_id, status ]`, `[ start_date, file_migration_id, type ]`, `[ start_date, operation, status ]`, `[ start_date, operation, type ]`, `[ start_date, status, type ]`, `[ end_date, file_migration_id, operation ]`, `[ end_date, file_migration_id, status ]`, `[ end_date, file_migration_id, type ]`, `[ end_date, operation, status ]`, `[ end_date, operation, type ]`, `[ end_date, status, type ]`, `[ file_migration_id, operation, status ]`, `[ file_migration_id, operation, type ]`, `[ file_migration_id, status, type ]`, `[ operation, status, type ]`, `[ start_date, end_date, file_migration_id, operation ]`, `[ start_date, end_date, file_migration_id, status ]`, `[ start_date, end_date, file_migration_id, type ]`, `[ start_date, end_date, operation, status ]`, `[ start_date, end_date, operation, type ]`, `[ start_date, end_date, status, type ]`, `[ start_date, file_migration_id, operation, status ]`, `[ start_date, file_migration_id, operation, type ]`, `[ start_date, file_migration_id, status, type ]`, `[ start_date, operation, status, type ]`, `[ end_date, file_migration_id, operation, status ]`, `[ end_date, file_migration_id, operation, type ]`, `[ end_date, file_migration_id, status, type ]`, `[ end_date, operation, status, type ]`, `[ file_migration_id, operation, status, type ]`, `[ start_date, end_date, file_migration_id, operation, status ]`, `[ start_date, end_date, file_migration_id, operation, type ]`, `[ start_date, end_date, file_migration_id, status, type ]`, `[ start_date, end_date, operation, status, type ]`, `[ start_date, file_migration_id, operation, status, type ]` or `[ end_date, file_migration_id, operation, status, type ]`.
  *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `operation` and `status`. Valid field combinations are `[ start_date ]`, `[ end_date ]`, `[ file_migration_id ]`, `[ operation ]`, `[ status ]`, `[ type ]`, `[ start_date, end_date ]`, `[ start_date, file_migration_id ]`, `[ start_date, operation ]`, `[ start_date, status ]`, `[ start_date, type ]`, `[ end_date, file_migration_id ]`, `[ end_date, operation ]`, `[ end_date, status ]`, `[ end_date, type ]`, `[ file_migration_id, operation ]`, `[ file_migration_id, status ]`, `[ file_migration_id, type ]`, `[ operation, status ]`, `[ operation, type ]`, `[ status, type ]`, `[ start_date, end_date, file_migration_id ]`, `[ start_date, end_date, operation ]`, `[ start_date, end_date, status ]`, `[ start_date, end_date, type ]`, `[ start_date, file_migration_id, operation ]`, `[ start_date, file_migration_id, status ]`, `[ start_date, file_migration_id, type ]`, `[ start_date, operation, status ]`, `[ start_date, operation, type ]`, `[ start_date, status, type ]`, `[ end_date, file_migration_id, operation ]`, `[ end_date, file_migration_id, status ]`, `[ end_date, file_migration_id, type ]`, `[ end_date, operation, status ]`, `[ end_date, operation, type ]`, `[ end_date, status, type ]`, `[ file_migration_id, operation, status ]`, `[ file_migration_id, operation, type ]`, `[ file_migration_id, status, type ]`, `[ operation, status, type ]`, `[ start_date, end_date, file_migration_id, operation ]`, `[ start_date, end_date, file_migration_id, status ]`, `[ start_date, end_date, file_migration_id, type ]`, `[ start_date, end_date, operation, status ]`, `[ start_date, end_date, operation, type ]`, `[ start_date, end_date, status, type ]`, `[ start_date, file_migration_id, operation, status ]`, `[ start_date, file_migration_id, operation, type ]`, `[ start_date, file_migration_id, status, type ]`, `[ start_date, operation, status, type ]`, `[ end_date, file_migration_id, operation, status ]`, `[ end_date, file_migration_id, operation, type ]`, `[ end_date, file_migration_id, status, type ]`, `[ end_date, operation, status, type ]`, `[ file_migration_id, operation, status, type ]`, `[ start_date, end_date, file_migration_id, operation, status ]`, `[ start_date, end_date, file_migration_id, operation, type ]`, `[ start_date, end_date, file_migration_id, status, type ]`, `[ start_date, end_date, operation, status, type ]`, `[ start_date, file_migration_id, operation, status, type ]` or `[ end_date, file_migration_id, operation, status, type ]`.
  */
  public static ListIterator<FileMigrationLog> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<FileMigrationLog> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<FileMigrationLog> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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


    String url = String.format("%s%s/file_migration_logs", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<FileMigrationLog>> typeReference = new TypeReference<List<FileMigrationLog>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<FileMigrationLog> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<FileMigrationLog> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
