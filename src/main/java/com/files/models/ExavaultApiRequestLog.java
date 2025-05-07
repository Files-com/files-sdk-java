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

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExavaultApiRequestLog implements ModelInterface {
  private HashMap<String, Object> options;

  public void setOptions(HashMap<String, Object> options) {
    this.options = options;
  }

  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();


  public ExavaultApiRequestLog() {
    this(null, null);
  }

  public ExavaultApiRequestLog(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public ExavaultApiRequestLog(HashMap<String, Object> parameters, HashMap<String, Object> options) {
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
  @JsonProperty("timestamp")
  public Date timestamp;

  public Date getTimestamp() {
    return timestamp;
  }

  /**
  * Name of API Endpoint
  */
  @JsonProperty("endpoint")
  public String endpoint;

  public String getEndpoint() {
    return endpoint;
  }

  /**
  * Exavault API Version
  */
  @JsonProperty("version")
  public String version;

  public String getVersion() {
    return version;
  }

  /**
  * IP of requesting client
  */
  @JsonProperty("request_ip")
  public String requestIp;

  public String getRequestIp() {
    return requestIp;
  }

  /**
  * HTTP Method
  */
  @JsonProperty("request_method")
  public String requestMethod;

  public String getRequestMethod() {
    return requestMethod;
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
  * Error message, if applicable
  */
  @JsonProperty("error_message")
  public String errorMessage;

  public String getErrorMessage() {
    return errorMessage;
  }

  /**
  * User-Agent
  */
  @JsonProperty("user_agent")
  public String userAgent;

  public String getUserAgent() {
    return userAgent;
  }

  /**
  * HTTP Response Code
  */
  @JsonProperty("response_code")
  public Long responseCode;

  public Long getResponseCode() {
    return responseCode;
  }

  /**
  * `false` if HTTP Response Code is 4xx or 5xx
  */
  @JsonProperty("success")
  public Boolean success;

  public Boolean getSuccess() {
    return success;
  }

  /**
  * Duration (in milliseconds)
  */
  @JsonProperty("duration_ms")
  public Long durationMs;

  public Long getDurationMs() {
    return durationMs;
  }


  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `start_date`, `end_date`, `request_ip`, `request_method` or `success`. Valid field combinations are `[ start_date ]`, `[ end_date ]`, `[ request_ip ]`, `[ request_method ]`, `[ success ]`, `[ start_date, end_date ]`, `[ start_date, request_ip ]`, `[ start_date, request_method ]`, `[ start_date, success ]`, `[ end_date, request_ip ]`, `[ end_date, request_method ]`, `[ end_date, success ]`, `[ request_ip, request_method ]`, `[ request_ip, success ]`, `[ request_method, success ]`, `[ start_date, end_date, request_ip ]`, `[ start_date, end_date, request_method ]`, `[ start_date, end_date, success ]`, `[ start_date, request_ip, request_method ]`, `[ start_date, request_ip, success ]`, `[ start_date, request_method, success ]`, `[ end_date, request_ip, request_method ]`, `[ end_date, request_ip, success ]`, `[ end_date, request_method, success ]`, `[ request_ip, request_method, success ]`, `[ start_date, end_date, request_ip, request_method ]`, `[ start_date, end_date, request_ip, success ]`, `[ start_date, end_date, request_method, success ]`, `[ start_date, request_ip, request_method, success ]` or `[ end_date, request_ip, request_method, success ]`.
  *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `request_ip` and `request_method`. Valid field combinations are `[ start_date ]`, `[ end_date ]`, `[ request_ip ]`, `[ request_method ]`, `[ success ]`, `[ start_date, end_date ]`, `[ start_date, request_ip ]`, `[ start_date, request_method ]`, `[ start_date, success ]`, `[ end_date, request_ip ]`, `[ end_date, request_method ]`, `[ end_date, success ]`, `[ request_ip, request_method ]`, `[ request_ip, success ]`, `[ request_method, success ]`, `[ start_date, end_date, request_ip ]`, `[ start_date, end_date, request_method ]`, `[ start_date, end_date, success ]`, `[ start_date, request_ip, request_method ]`, `[ start_date, request_ip, success ]`, `[ start_date, request_method, success ]`, `[ end_date, request_ip, request_method ]`, `[ end_date, request_ip, success ]`, `[ end_date, request_method, success ]`, `[ request_ip, request_method, success ]`, `[ start_date, end_date, request_ip, request_method ]`, `[ start_date, end_date, request_ip, success ]`, `[ start_date, end_date, request_method, success ]`, `[ start_date, request_ip, request_method, success ]` or `[ end_date, request_ip, request_method, success ]`.
  */
  public static ListIterator<ExavaultApiRequestLog> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<ExavaultApiRequestLog> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<ExavaultApiRequestLog> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }
    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Map<String, String> parameters[\"filter\"]");
    }
    if (parameters.containsKey("filter_prefix") && !(parameters.get("filter_prefix") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_prefix must be of type Map<String, String> parameters[\"filter_prefix\"]");
    }


    String url = String.format("%s%s/exavault_api_request_logs", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<ExavaultApiRequestLog>> typeReference = new TypeReference<List<ExavaultApiRequestLog>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<ExavaultApiRequestLog> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<ExavaultApiRequestLog> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
