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
public class ActionNotificationExportResult implements ModelInterface {
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


  public ActionNotificationExportResult() {
    this(null, null);
  }

  public ActionNotificationExportResult(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public ActionNotificationExportResult(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Notification ID
  */
  @JsonProperty("id")
  public Long id;

  public Long getId() {
    return id;
  }

  /**
  * When the notification was sent.
  */
  @JsonProperty("created_at")
  public Long createdAt;

  public Long getCreatedAt() {
    return createdAt;
  }

  /**
  * HTTP status code returned in the webhook response.
  */
  @JsonProperty("status")
  public Long status;

  public Long getStatus() {
    return status;
  }

  /**
  * A message indicating the overall status of the webhook notification.
  */
  @JsonProperty("message")
  public String message;

  public String getMessage() {
    return message;
  }

  /**
  * `true` if the webhook succeeded by receiving a 200 or 204 response.
  */
  @JsonProperty("success")
  public Boolean success;

  public Boolean getSuccess() {
    return success;
  }

  /**
  * A JSON-encoded string with headers that were sent with the webhook.
  */
  @JsonProperty("request_headers")
  public String requestHeaders;

  public String getRequestHeaders() {
    return requestHeaders;
  }

  /**
  * The HTTP verb used to perform the webhook.
  */
  @JsonProperty("request_method")
  public String requestMethod;

  public String getRequestMethod() {
    return requestMethod;
  }

  /**
  * The webhook request URL.
  */
  @JsonProperty("request_url")
  public String requestUrl;

  public String getRequestUrl() {
    return requestUrl;
  }

  /**
  * The path to the actual file that triggered this notification. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @JsonProperty("path")
  public String path;

  public String getPath() {
    return path;
  }

  /**
  * The folder associated with the triggering action for this notification.
  */
  @JsonProperty("folder")
  public String folder;

  public String getFolder() {
    return folder;
  }


  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action_notification_export_id (required) - int64 - ID of the associated action notification export.
  */
  public static ListIterator<ActionNotificationExportResult> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<ActionNotificationExportResult> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<ActionNotificationExportResult> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (!parameters.containsKey("action_notification_export_id") || parameters.get("action_notification_export_id") == null) {
      throw new NullPointerException("Parameter missing: action_notification_export_id parameters[\"action_notification_export_id\"]");
    }

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }
    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }
    if (parameters.containsKey("action_notification_export_id") && !(parameters.get("action_notification_export_id") instanceof Long || parameters.get("action_notification_export_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: action_notification_export_id must be of type Long or Integer parameters[\"action_notification_export_id\"]");
    }


    String url = String.format("%s%s/action_notification_export_results", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<ActionNotificationExportResult>> typeReference = new TypeReference<List<ActionNotificationExportResult>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<ActionNotificationExportResult> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<ActionNotificationExportResult> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
