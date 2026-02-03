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
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.ListIterator;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
import com.files.util.UrlUtils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AutomationLog implements ModelInterface {
  private HashMap<String, Object> options;

  public void setOptions(HashMap<String, Object> options) {
    this.options = options;
  }

  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
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
  * Start Time of Action. Deprecrated: Use created_at.
  */
  @JsonProperty("timestamp")
  public Date timestamp;

  public Date getTimestamp() {
    return timestamp;
  }

  /**
  * Automation ID
  */
  @JsonProperty("automation_id")
  public Long automationId;

  public Long getAutomationId() {
    return automationId;
  }

  /**
  * Automation Run ID
  */
  @JsonProperty("automation_run_id")
  public Long automationRunId;

  public Long getAutomationRunId() {
    return automationRunId;
  }

  /**
  * Destination path, for moves and copies
  */
  @JsonProperty("dest_path")
  public String destPath;

  public String getDestPath() {
    return destPath;
  }

  /**
  * Error type, if applicable
  */
  @JsonProperty("error_type")
  public String errorType;

  public String getErrorType() {
    return errorType;
  }

  /**
  * Message
  */
  @JsonProperty("message")
  public String message;

  public String getMessage() {
    return message;
  }

  /**
  * Operation type
  */
  @JsonProperty("operation")
  public String operation;

  public String getOperation() {
    return operation;
  }

  /**
  * File path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @JsonProperty("path")
  public String path;

  public String getPath() {
    return path;
  }

  /**
  * Status
  */
  @JsonProperty("status")
  public String status;

  public String getStatus() {
    return status;
  }

  /**
  * Start Time of Action
  */
  @JsonProperty("created_at")
  public Date createdAt;

  public Date getCreatedAt() {
    return createdAt;
  }


  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `automation_id`, `automation_run_id`, `operation`, `path`, `status` or `created_at`. Valid field combinations are `[ automation_id ]`, `[ automation_run_id ]`, `[ operation ]`, `[ path ]`, `[ status ]`, `[ created_at ]`, `[ automation_id, automation_run_id ]`, `[ automation_id, operation ]`, `[ automation_id, path ]`, `[ automation_id, status ]`, `[ automation_id, created_at ]`, `[ automation_run_id, operation ]`, `[ automation_run_id, path ]`, `[ automation_run_id, status ]`, `[ automation_run_id, created_at ]`, `[ operation, path ]`, `[ operation, status ]`, `[ operation, created_at ]`, `[ path, status ]`, `[ path, created_at ]`, `[ status, created_at ]`, `[ automation_id, automation_run_id, operation ]`, `[ automation_id, automation_run_id, path ]`, `[ automation_id, automation_run_id, status ]`, `[ automation_id, automation_run_id, created_at ]`, `[ automation_id, operation, path ]`, `[ automation_id, operation, status ]`, `[ automation_id, operation, created_at ]`, `[ automation_id, path, status ]`, `[ automation_id, path, created_at ]`, `[ automation_id, status, created_at ]`, `[ automation_run_id, operation, path ]`, `[ automation_run_id, operation, status ]`, `[ automation_run_id, operation, created_at ]`, `[ automation_run_id, path, status ]`, `[ automation_run_id, path, created_at ]`, `[ automation_run_id, status, created_at ]`, `[ operation, path, status ]`, `[ operation, path, created_at ]`, `[ operation, status, created_at ]`, `[ path, status, created_at ]`, `[ automation_id, automation_run_id, operation, path ]`, `[ automation_id, automation_run_id, operation, status ]`, `[ automation_id, automation_run_id, operation, created_at ]`, `[ automation_id, automation_run_id, path, status ]`, `[ automation_id, automation_run_id, path, created_at ]`, `[ automation_id, automation_run_id, status, created_at ]`, `[ automation_id, operation, path, status ]`, `[ automation_id, operation, path, created_at ]`, `[ automation_id, operation, status, created_at ]`, `[ automation_id, path, status, created_at ]`, `[ automation_run_id, operation, path, status ]`, `[ automation_run_id, operation, path, created_at ]`, `[ automation_run_id, operation, status, created_at ]`, `[ automation_run_id, path, status, created_at ]`, `[ operation, path, status, created_at ]`, `[ automation_id, automation_run_id, operation, path, status ]`, `[ automation_id, automation_run_id, operation, path, created_at ]`, `[ automation_id, automation_run_id, operation, status, created_at ]`, `[ automation_id, automation_run_id, path, status, created_at ]`, `[ automation_id, operation, path, status, created_at ]`, `[ automation_run_id, operation, path, status, created_at ]` or `[ automation_id, automation_run_id, operation, path, status, created_at ]`.
  *   filter_gt - object - If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`. Valid field combinations are `[ automation_id ]`, `[ automation_run_id ]`, `[ operation ]`, `[ path ]`, `[ status ]`, `[ created_at ]`, `[ automation_id, automation_run_id ]`, `[ automation_id, operation ]`, `[ automation_id, path ]`, `[ automation_id, status ]`, `[ automation_id, created_at ]`, `[ automation_run_id, operation ]`, `[ automation_run_id, path ]`, `[ automation_run_id, status ]`, `[ automation_run_id, created_at ]`, `[ operation, path ]`, `[ operation, status ]`, `[ operation, created_at ]`, `[ path, status ]`, `[ path, created_at ]`, `[ status, created_at ]`, `[ automation_id, automation_run_id, operation ]`, `[ automation_id, automation_run_id, path ]`, `[ automation_id, automation_run_id, status ]`, `[ automation_id, automation_run_id, created_at ]`, `[ automation_id, operation, path ]`, `[ automation_id, operation, status ]`, `[ automation_id, operation, created_at ]`, `[ automation_id, path, status ]`, `[ automation_id, path, created_at ]`, `[ automation_id, status, created_at ]`, `[ automation_run_id, operation, path ]`, `[ automation_run_id, operation, status ]`, `[ automation_run_id, operation, created_at ]`, `[ automation_run_id, path, status ]`, `[ automation_run_id, path, created_at ]`, `[ automation_run_id, status, created_at ]`, `[ operation, path, status ]`, `[ operation, path, created_at ]`, `[ operation, status, created_at ]`, `[ path, status, created_at ]`, `[ automation_id, automation_run_id, operation, path ]`, `[ automation_id, automation_run_id, operation, status ]`, `[ automation_id, automation_run_id, operation, created_at ]`, `[ automation_id, automation_run_id, path, status ]`, `[ automation_id, automation_run_id, path, created_at ]`, `[ automation_id, automation_run_id, status, created_at ]`, `[ automation_id, operation, path, status ]`, `[ automation_id, operation, path, created_at ]`, `[ automation_id, operation, status, created_at ]`, `[ automation_id, path, status, created_at ]`, `[ automation_run_id, operation, path, status ]`, `[ automation_run_id, operation, path, created_at ]`, `[ automation_run_id, operation, status, created_at ]`, `[ automation_run_id, path, status, created_at ]`, `[ operation, path, status, created_at ]`, `[ automation_id, automation_run_id, operation, path, status ]`, `[ automation_id, automation_run_id, operation, path, created_at ]`, `[ automation_id, automation_run_id, operation, status, created_at ]`, `[ automation_id, automation_run_id, path, status, created_at ]`, `[ automation_id, operation, path, status, created_at ]`, `[ automation_run_id, operation, path, status, created_at ]` or `[ automation_id, automation_run_id, operation, path, status, created_at ]`.
  *   filter_gteq - object - If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`. Valid field combinations are `[ automation_id ]`, `[ automation_run_id ]`, `[ operation ]`, `[ path ]`, `[ status ]`, `[ created_at ]`, `[ automation_id, automation_run_id ]`, `[ automation_id, operation ]`, `[ automation_id, path ]`, `[ automation_id, status ]`, `[ automation_id, created_at ]`, `[ automation_run_id, operation ]`, `[ automation_run_id, path ]`, `[ automation_run_id, status ]`, `[ automation_run_id, created_at ]`, `[ operation, path ]`, `[ operation, status ]`, `[ operation, created_at ]`, `[ path, status ]`, `[ path, created_at ]`, `[ status, created_at ]`, `[ automation_id, automation_run_id, operation ]`, `[ automation_id, automation_run_id, path ]`, `[ automation_id, automation_run_id, status ]`, `[ automation_id, automation_run_id, created_at ]`, `[ automation_id, operation, path ]`, `[ automation_id, operation, status ]`, `[ automation_id, operation, created_at ]`, `[ automation_id, path, status ]`, `[ automation_id, path, created_at ]`, `[ automation_id, status, created_at ]`, `[ automation_run_id, operation, path ]`, `[ automation_run_id, operation, status ]`, `[ automation_run_id, operation, created_at ]`, `[ automation_run_id, path, status ]`, `[ automation_run_id, path, created_at ]`, `[ automation_run_id, status, created_at ]`, `[ operation, path, status ]`, `[ operation, path, created_at ]`, `[ operation, status, created_at ]`, `[ path, status, created_at ]`, `[ automation_id, automation_run_id, operation, path ]`, `[ automation_id, automation_run_id, operation, status ]`, `[ automation_id, automation_run_id, operation, created_at ]`, `[ automation_id, automation_run_id, path, status ]`, `[ automation_id, automation_run_id, path, created_at ]`, `[ automation_id, automation_run_id, status, created_at ]`, `[ automation_id, operation, path, status ]`, `[ automation_id, operation, path, created_at ]`, `[ automation_id, operation, status, created_at ]`, `[ automation_id, path, status, created_at ]`, `[ automation_run_id, operation, path, status ]`, `[ automation_run_id, operation, path, created_at ]`, `[ automation_run_id, operation, status, created_at ]`, `[ automation_run_id, path, status, created_at ]`, `[ operation, path, status, created_at ]`, `[ automation_id, automation_run_id, operation, path, status ]`, `[ automation_id, automation_run_id, operation, path, created_at ]`, `[ automation_id, automation_run_id, operation, status, created_at ]`, `[ automation_id, automation_run_id, path, status, created_at ]`, `[ automation_id, operation, path, status, created_at ]`, `[ automation_run_id, operation, path, status, created_at ]` or `[ automation_id, automation_run_id, operation, path, status, created_at ]`.
  *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `path`. Valid field combinations are `[ automation_id ]`, `[ automation_run_id ]`, `[ operation ]`, `[ path ]`, `[ status ]`, `[ created_at ]`, `[ automation_id, automation_run_id ]`, `[ automation_id, operation ]`, `[ automation_id, path ]`, `[ automation_id, status ]`, `[ automation_id, created_at ]`, `[ automation_run_id, operation ]`, `[ automation_run_id, path ]`, `[ automation_run_id, status ]`, `[ automation_run_id, created_at ]`, `[ operation, path ]`, `[ operation, status ]`, `[ operation, created_at ]`, `[ path, status ]`, `[ path, created_at ]`, `[ status, created_at ]`, `[ automation_id, automation_run_id, operation ]`, `[ automation_id, automation_run_id, path ]`, `[ automation_id, automation_run_id, status ]`, `[ automation_id, automation_run_id, created_at ]`, `[ automation_id, operation, path ]`, `[ automation_id, operation, status ]`, `[ automation_id, operation, created_at ]`, `[ automation_id, path, status ]`, `[ automation_id, path, created_at ]`, `[ automation_id, status, created_at ]`, `[ automation_run_id, operation, path ]`, `[ automation_run_id, operation, status ]`, `[ automation_run_id, operation, created_at ]`, `[ automation_run_id, path, status ]`, `[ automation_run_id, path, created_at ]`, `[ automation_run_id, status, created_at ]`, `[ operation, path, status ]`, `[ operation, path, created_at ]`, `[ operation, status, created_at ]`, `[ path, status, created_at ]`, `[ automation_id, automation_run_id, operation, path ]`, `[ automation_id, automation_run_id, operation, status ]`, `[ automation_id, automation_run_id, operation, created_at ]`, `[ automation_id, automation_run_id, path, status ]`, `[ automation_id, automation_run_id, path, created_at ]`, `[ automation_id, automation_run_id, status, created_at ]`, `[ automation_id, operation, path, status ]`, `[ automation_id, operation, path, created_at ]`, `[ automation_id, operation, status, created_at ]`, `[ automation_id, path, status, created_at ]`, `[ automation_run_id, operation, path, status ]`, `[ automation_run_id, operation, path, created_at ]`, `[ automation_run_id, operation, status, created_at ]`, `[ automation_run_id, path, status, created_at ]`, `[ operation, path, status, created_at ]`, `[ automation_id, automation_run_id, operation, path, status ]`, `[ automation_id, automation_run_id, operation, path, created_at ]`, `[ automation_id, automation_run_id, operation, status, created_at ]`, `[ automation_id, automation_run_id, path, status, created_at ]`, `[ automation_id, operation, path, status, created_at ]`, `[ automation_run_id, operation, path, status, created_at ]` or `[ automation_id, automation_run_id, operation, path, status, created_at ]`.
  *   filter_lt - object - If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`. Valid field combinations are `[ automation_id ]`, `[ automation_run_id ]`, `[ operation ]`, `[ path ]`, `[ status ]`, `[ created_at ]`, `[ automation_id, automation_run_id ]`, `[ automation_id, operation ]`, `[ automation_id, path ]`, `[ automation_id, status ]`, `[ automation_id, created_at ]`, `[ automation_run_id, operation ]`, `[ automation_run_id, path ]`, `[ automation_run_id, status ]`, `[ automation_run_id, created_at ]`, `[ operation, path ]`, `[ operation, status ]`, `[ operation, created_at ]`, `[ path, status ]`, `[ path, created_at ]`, `[ status, created_at ]`, `[ automation_id, automation_run_id, operation ]`, `[ automation_id, automation_run_id, path ]`, `[ automation_id, automation_run_id, status ]`, `[ automation_id, automation_run_id, created_at ]`, `[ automation_id, operation, path ]`, `[ automation_id, operation, status ]`, `[ automation_id, operation, created_at ]`, `[ automation_id, path, status ]`, `[ automation_id, path, created_at ]`, `[ automation_id, status, created_at ]`, `[ automation_run_id, operation, path ]`, `[ automation_run_id, operation, status ]`, `[ automation_run_id, operation, created_at ]`, `[ automation_run_id, path, status ]`, `[ automation_run_id, path, created_at ]`, `[ automation_run_id, status, created_at ]`, `[ operation, path, status ]`, `[ operation, path, created_at ]`, `[ operation, status, created_at ]`, `[ path, status, created_at ]`, `[ automation_id, automation_run_id, operation, path ]`, `[ automation_id, automation_run_id, operation, status ]`, `[ automation_id, automation_run_id, operation, created_at ]`, `[ automation_id, automation_run_id, path, status ]`, `[ automation_id, automation_run_id, path, created_at ]`, `[ automation_id, automation_run_id, status, created_at ]`, `[ automation_id, operation, path, status ]`, `[ automation_id, operation, path, created_at ]`, `[ automation_id, operation, status, created_at ]`, `[ automation_id, path, status, created_at ]`, `[ automation_run_id, operation, path, status ]`, `[ automation_run_id, operation, path, created_at ]`, `[ automation_run_id, operation, status, created_at ]`, `[ automation_run_id, path, status, created_at ]`, `[ operation, path, status, created_at ]`, `[ automation_id, automation_run_id, operation, path, status ]`, `[ automation_id, automation_run_id, operation, path, created_at ]`, `[ automation_id, automation_run_id, operation, status, created_at ]`, `[ automation_id, automation_run_id, path, status, created_at ]`, `[ automation_id, operation, path, status, created_at ]`, `[ automation_run_id, operation, path, status, created_at ]` or `[ automation_id, automation_run_id, operation, path, status, created_at ]`.
  *   filter_lteq - object - If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`. Valid field combinations are `[ automation_id ]`, `[ automation_run_id ]`, `[ operation ]`, `[ path ]`, `[ status ]`, `[ created_at ]`, `[ automation_id, automation_run_id ]`, `[ automation_id, operation ]`, `[ automation_id, path ]`, `[ automation_id, status ]`, `[ automation_id, created_at ]`, `[ automation_run_id, operation ]`, `[ automation_run_id, path ]`, `[ automation_run_id, status ]`, `[ automation_run_id, created_at ]`, `[ operation, path ]`, `[ operation, status ]`, `[ operation, created_at ]`, `[ path, status ]`, `[ path, created_at ]`, `[ status, created_at ]`, `[ automation_id, automation_run_id, operation ]`, `[ automation_id, automation_run_id, path ]`, `[ automation_id, automation_run_id, status ]`, `[ automation_id, automation_run_id, created_at ]`, `[ automation_id, operation, path ]`, `[ automation_id, operation, status ]`, `[ automation_id, operation, created_at ]`, `[ automation_id, path, status ]`, `[ automation_id, path, created_at ]`, `[ automation_id, status, created_at ]`, `[ automation_run_id, operation, path ]`, `[ automation_run_id, operation, status ]`, `[ automation_run_id, operation, created_at ]`, `[ automation_run_id, path, status ]`, `[ automation_run_id, path, created_at ]`, `[ automation_run_id, status, created_at ]`, `[ operation, path, status ]`, `[ operation, path, created_at ]`, `[ operation, status, created_at ]`, `[ path, status, created_at ]`, `[ automation_id, automation_run_id, operation, path ]`, `[ automation_id, automation_run_id, operation, status ]`, `[ automation_id, automation_run_id, operation, created_at ]`, `[ automation_id, automation_run_id, path, status ]`, `[ automation_id, automation_run_id, path, created_at ]`, `[ automation_id, automation_run_id, status, created_at ]`, `[ automation_id, operation, path, status ]`, `[ automation_id, operation, path, created_at ]`, `[ automation_id, operation, status, created_at ]`, `[ automation_id, path, status, created_at ]`, `[ automation_run_id, operation, path, status ]`, `[ automation_run_id, operation, path, created_at ]`, `[ automation_run_id, operation, status, created_at ]`, `[ automation_run_id, path, status, created_at ]`, `[ operation, path, status, created_at ]`, `[ automation_id, automation_run_id, operation, path, status ]`, `[ automation_id, automation_run_id, operation, path, created_at ]`, `[ automation_id, automation_run_id, operation, status, created_at ]`, `[ automation_id, automation_run_id, path, status, created_at ]`, `[ automation_id, operation, path, status, created_at ]`, `[ automation_run_id, operation, path, status, created_at ]` or `[ automation_id, automation_run_id, operation, path, status, created_at ]`.
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
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }
    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Object parameters[\"filter\"]");
    }
    if (parameters.containsKey("filter_gt") && !(parameters.get("filter_gt") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_gt must be of type Object parameters[\"filter_gt\"]");
    }
    if (parameters.containsKey("filter_gteq") && !(parameters.get("filter_gteq") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_gteq must be of type Object parameters[\"filter_gteq\"]");
    }
    if (parameters.containsKey("filter_prefix") && !(parameters.get("filter_prefix") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_prefix must be of type Object parameters[\"filter_prefix\"]");
    }
    if (parameters.containsKey("filter_lt") && !(parameters.get("filter_lt") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_lt must be of type Object parameters[\"filter_lt\"]");
    }
    if (parameters.containsKey("filter_lteq") && !(parameters.get("filter_lteq") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_lteq must be of type Object parameters[\"filter_lteq\"]");
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
