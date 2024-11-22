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
public class AutomationLog implements ModelInterface {
  @Setter
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();


  public AutomationLog() {
    this(null, null);
  }

  public AutomationLog(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public AutomationLog(HashMap<String, Object> parameters, HashMap<String, Object> options) {
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
  * Automation ID
  */
  @Getter
  @JsonProperty("automation_id")
  public Long automationId;

  /**
  * Automation Run ID
  */
  @Getter
  @JsonProperty("automation_run_id")
  public Long automationRunId;

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
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `start_date`, `end_date`, `automation_id`, `automation_run_id`, `operation`, `path` or `status`. Valid field combinations are `[ start_date ]`, `[ end_date ]`, `[ automation_id ]`, `[ automation_run_id ]`, `[ operation ]`, `[ path ]`, `[ status ]`, `[ start_date, end_date ]`, `[ start_date, automation_id ]`, `[ start_date, automation_run_id ]`, `[ start_date, operation ]`, `[ start_date, path ]`, `[ start_date, status ]`, `[ end_date, automation_id ]`, `[ end_date, automation_run_id ]`, `[ end_date, operation ]`, `[ end_date, path ]`, `[ end_date, status ]`, `[ automation_id, automation_run_id ]`, `[ automation_id, operation ]`, `[ automation_id, path ]`, `[ automation_id, status ]`, `[ automation_run_id, operation ]`, `[ automation_run_id, path ]`, `[ automation_run_id, status ]`, `[ operation, path ]`, `[ operation, status ]`, `[ path, status ]`, `[ start_date, end_date, automation_id ]`, `[ start_date, end_date, automation_run_id ]`, `[ start_date, end_date, operation ]`, `[ start_date, end_date, path ]`, `[ start_date, end_date, status ]`, `[ start_date, automation_id, automation_run_id ]`, `[ start_date, automation_id, operation ]`, `[ start_date, automation_id, path ]`, `[ start_date, automation_id, status ]`, `[ start_date, automation_run_id, operation ]`, `[ start_date, automation_run_id, path ]`, `[ start_date, automation_run_id, status ]`, `[ start_date, operation, path ]`, `[ start_date, operation, status ]`, `[ start_date, path, status ]`, `[ end_date, automation_id, automation_run_id ]`, `[ end_date, automation_id, operation ]`, `[ end_date, automation_id, path ]`, `[ end_date, automation_id, status ]`, `[ end_date, automation_run_id, operation ]`, `[ end_date, automation_run_id, path ]`, `[ end_date, automation_run_id, status ]`, `[ end_date, operation, path ]`, `[ end_date, operation, status ]`, `[ end_date, path, status ]`, `[ automation_id, automation_run_id, operation ]`, `[ automation_id, automation_run_id, path ]`, `[ automation_id, automation_run_id, status ]`, `[ automation_id, operation, path ]`, `[ automation_id, operation, status ]`, `[ automation_id, path, status ]`, `[ automation_run_id, operation, path ]`, `[ automation_run_id, operation, status ]`, `[ automation_run_id, path, status ]`, `[ operation, path, status ]`, `[ start_date, end_date, automation_id, automation_run_id ]`, `[ start_date, end_date, automation_id, operation ]`, `[ start_date, end_date, automation_id, path ]`, `[ start_date, end_date, automation_id, status ]`, `[ start_date, end_date, automation_run_id, operation ]`, `[ start_date, end_date, automation_run_id, path ]`, `[ start_date, end_date, automation_run_id, status ]`, `[ start_date, end_date, operation, path ]`, `[ start_date, end_date, operation, status ]`, `[ start_date, end_date, path, status ]`, `[ start_date, automation_id, automation_run_id, operation ]`, `[ start_date, automation_id, automation_run_id, path ]`, `[ start_date, automation_id, automation_run_id, status ]`, `[ start_date, automation_id, operation, path ]`, `[ start_date, automation_id, operation, status ]`, `[ start_date, automation_id, path, status ]`, `[ start_date, automation_run_id, operation, path ]`, `[ start_date, automation_run_id, operation, status ]`, `[ start_date, automation_run_id, path, status ]`, `[ start_date, operation, path, status ]`, `[ end_date, automation_id, automation_run_id, operation ]`, `[ end_date, automation_id, automation_run_id, path ]`, `[ end_date, automation_id, automation_run_id, status ]`, `[ end_date, automation_id, operation, path ]`, `[ end_date, automation_id, operation, status ]`, `[ end_date, automation_id, path, status ]`, `[ end_date, automation_run_id, operation, path ]`, `[ end_date, automation_run_id, operation, status ]`, `[ end_date, automation_run_id, path, status ]`, `[ end_date, operation, path, status ]`, `[ automation_id, automation_run_id, operation, path ]`, `[ automation_id, automation_run_id, operation, status ]`, `[ automation_id, automation_run_id, path, status ]`, `[ automation_id, operation, path, status ]`, `[ automation_run_id, operation, path, status ]`, `[ start_date, end_date, automation_id, automation_run_id, operation ]`, `[ start_date, end_date, automation_id, automation_run_id, path ]`, `[ start_date, end_date, automation_id, automation_run_id, status ]`, `[ start_date, end_date, automation_id, operation, path ]`, `[ start_date, end_date, automation_id, operation, status ]`, `[ start_date, end_date, automation_id, path, status ]`, `[ start_date, end_date, automation_run_id, operation, path ]`, `[ start_date, end_date, automation_run_id, operation, status ]`, `[ start_date, end_date, automation_run_id, path, status ]`, `[ start_date, end_date, operation, path, status ]`, `[ start_date, automation_id, automation_run_id, operation, path ]`, `[ start_date, automation_id, automation_run_id, operation, status ]`, `[ start_date, automation_id, automation_run_id, path, status ]`, `[ start_date, automation_id, operation, path, status ]`, `[ start_date, automation_run_id, operation, path, status ]`, `[ end_date, automation_id, automation_run_id, operation, path ]`, `[ end_date, automation_id, automation_run_id, operation, status ]`, `[ end_date, automation_id, automation_run_id, path, status ]`, `[ end_date, automation_id, operation, path, status ]`, `[ end_date, automation_run_id, operation, path, status ]`, `[ automation_id, automation_run_id, operation, path, status ]`, `[ start_date, end_date, automation_id, automation_run_id, operation, path ]`, `[ start_date, end_date, automation_id, automation_run_id, operation, status ]`, `[ start_date, end_date, automation_id, automation_run_id, path, status ]`, `[ start_date, end_date, automation_id, operation, path, status ]`, `[ start_date, end_date, automation_run_id, operation, path, status ]`, `[ start_date, automation_id, automation_run_id, operation, path, status ]` or `[ end_date, automation_id, automation_run_id, operation, path, status ]`.
  *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `operation`, `path` or `status`. Valid field combinations are `[ start_date ]`, `[ end_date ]`, `[ automation_id ]`, `[ automation_run_id ]`, `[ operation ]`, `[ path ]`, `[ status ]`, `[ start_date, end_date ]`, `[ start_date, automation_id ]`, `[ start_date, automation_run_id ]`, `[ start_date, operation ]`, `[ start_date, path ]`, `[ start_date, status ]`, `[ end_date, automation_id ]`, `[ end_date, automation_run_id ]`, `[ end_date, operation ]`, `[ end_date, path ]`, `[ end_date, status ]`, `[ automation_id, automation_run_id ]`, `[ automation_id, operation ]`, `[ automation_id, path ]`, `[ automation_id, status ]`, `[ automation_run_id, operation ]`, `[ automation_run_id, path ]`, `[ automation_run_id, status ]`, `[ operation, path ]`, `[ operation, status ]`, `[ path, status ]`, `[ start_date, end_date, automation_id ]`, `[ start_date, end_date, automation_run_id ]`, `[ start_date, end_date, operation ]`, `[ start_date, end_date, path ]`, `[ start_date, end_date, status ]`, `[ start_date, automation_id, automation_run_id ]`, `[ start_date, automation_id, operation ]`, `[ start_date, automation_id, path ]`, `[ start_date, automation_id, status ]`, `[ start_date, automation_run_id, operation ]`, `[ start_date, automation_run_id, path ]`, `[ start_date, automation_run_id, status ]`, `[ start_date, operation, path ]`, `[ start_date, operation, status ]`, `[ start_date, path, status ]`, `[ end_date, automation_id, automation_run_id ]`, `[ end_date, automation_id, operation ]`, `[ end_date, automation_id, path ]`, `[ end_date, automation_id, status ]`, `[ end_date, automation_run_id, operation ]`, `[ end_date, automation_run_id, path ]`, `[ end_date, automation_run_id, status ]`, `[ end_date, operation, path ]`, `[ end_date, operation, status ]`, `[ end_date, path, status ]`, `[ automation_id, automation_run_id, operation ]`, `[ automation_id, automation_run_id, path ]`, `[ automation_id, automation_run_id, status ]`, `[ automation_id, operation, path ]`, `[ automation_id, operation, status ]`, `[ automation_id, path, status ]`, `[ automation_run_id, operation, path ]`, `[ automation_run_id, operation, status ]`, `[ automation_run_id, path, status ]`, `[ operation, path, status ]`, `[ start_date, end_date, automation_id, automation_run_id ]`, `[ start_date, end_date, automation_id, operation ]`, `[ start_date, end_date, automation_id, path ]`, `[ start_date, end_date, automation_id, status ]`, `[ start_date, end_date, automation_run_id, operation ]`, `[ start_date, end_date, automation_run_id, path ]`, `[ start_date, end_date, automation_run_id, status ]`, `[ start_date, end_date, operation, path ]`, `[ start_date, end_date, operation, status ]`, `[ start_date, end_date, path, status ]`, `[ start_date, automation_id, automation_run_id, operation ]`, `[ start_date, automation_id, automation_run_id, path ]`, `[ start_date, automation_id, automation_run_id, status ]`, `[ start_date, automation_id, operation, path ]`, `[ start_date, automation_id, operation, status ]`, `[ start_date, automation_id, path, status ]`, `[ start_date, automation_run_id, operation, path ]`, `[ start_date, automation_run_id, operation, status ]`, `[ start_date, automation_run_id, path, status ]`, `[ start_date, operation, path, status ]`, `[ end_date, automation_id, automation_run_id, operation ]`, `[ end_date, automation_id, automation_run_id, path ]`, `[ end_date, automation_id, automation_run_id, status ]`, `[ end_date, automation_id, operation, path ]`, `[ end_date, automation_id, operation, status ]`, `[ end_date, automation_id, path, status ]`, `[ end_date, automation_run_id, operation, path ]`, `[ end_date, automation_run_id, operation, status ]`, `[ end_date, automation_run_id, path, status ]`, `[ end_date, operation, path, status ]`, `[ automation_id, automation_run_id, operation, path ]`, `[ automation_id, automation_run_id, operation, status ]`, `[ automation_id, automation_run_id, path, status ]`, `[ automation_id, operation, path, status ]`, `[ automation_run_id, operation, path, status ]`, `[ start_date, end_date, automation_id, automation_run_id, operation ]`, `[ start_date, end_date, automation_id, automation_run_id, path ]`, `[ start_date, end_date, automation_id, automation_run_id, status ]`, `[ start_date, end_date, automation_id, operation, path ]`, `[ start_date, end_date, automation_id, operation, status ]`, `[ start_date, end_date, automation_id, path, status ]`, `[ start_date, end_date, automation_run_id, operation, path ]`, `[ start_date, end_date, automation_run_id, operation, status ]`, `[ start_date, end_date, automation_run_id, path, status ]`, `[ start_date, end_date, operation, path, status ]`, `[ start_date, automation_id, automation_run_id, operation, path ]`, `[ start_date, automation_id, automation_run_id, operation, status ]`, `[ start_date, automation_id, automation_run_id, path, status ]`, `[ start_date, automation_id, operation, path, status ]`, `[ start_date, automation_run_id, operation, path, status ]`, `[ end_date, automation_id, automation_run_id, operation, path ]`, `[ end_date, automation_id, automation_run_id, operation, status ]`, `[ end_date, automation_id, automation_run_id, path, status ]`, `[ end_date, automation_id, operation, path, status ]`, `[ end_date, automation_run_id, operation, path, status ]`, `[ automation_id, automation_run_id, operation, path, status ]`, `[ start_date, end_date, automation_id, automation_run_id, operation, path ]`, `[ start_date, end_date, automation_id, automation_run_id, operation, status ]`, `[ start_date, end_date, automation_id, automation_run_id, path, status ]`, `[ start_date, end_date, automation_id, operation, path, status ]`, `[ start_date, end_date, automation_run_id, operation, path, status ]`, `[ start_date, automation_id, automation_run_id, operation, path, status ]` or `[ end_date, automation_id, automation_run_id, operation, path, status ]`.
  */
  public static ListIterator<AutomationLog> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<AutomationLog> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<AutomationLog> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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


    String url = String.format("%s%s/automation_logs", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<AutomationLog>> typeReference = new TypeReference<List<AutomationLog>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<AutomationLog> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<AutomationLog> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
