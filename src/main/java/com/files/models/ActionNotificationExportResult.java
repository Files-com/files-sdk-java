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
public class ActionNotificationExportResult implements ModelInterface {
  @Setter
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
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
  @Getter
  @JsonProperty("id")
  public Long id;

  /**
  * When the notification was sent.
  */
  @Getter
  @JsonProperty("created_at")
  public Long createdAt;

  /**
  * HTTP status code returned in the webhook response.
  */
  @Getter
  @JsonProperty("status")
  public Long status;

  /**
  * A message indicating the overall status of the webhook notification.
  */
  @Getter
  @JsonProperty("message")
  public String message;

  /**
  * `true` if the webhook succeeded by receiving a 200 or 204 response.
  */
  @Getter
  @JsonProperty("success")
  public Boolean success;

  /**
  * A JSON-encoded string with headers that were sent with the webhook.
  */
  @Getter
  @JsonProperty("request_headers")
  public String requestHeaders;

  /**
  * The HTTP verb used to perform the webhook.
  */
  @Getter
  @JsonProperty("request_method")
  public String requestMethod;

  /**
  * The webhook request URL.
  */
  @Getter
  @JsonProperty("request_url")
  public String requestUrl;

  /**
  * The path to the actual file that triggered this notification. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @Getter
  @JsonProperty("path")
  public String path;

  /**
  * The folder associated with the triggering action for this notification.
  */
  @Getter
  @JsonProperty("folder")
  public String folder;



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

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }
    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }
    if (parameters.containsKey("action_notification_export_id") && !(parameters.get("action_notification_export_id") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: action_notification_export_id must be of type Long parameters[\"action_notification_export_id\"]");
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

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   action_notification_export_id (required) - int64 - ID of the associated action notification export.
  */
  public static Export createExport() throws RuntimeException {
    return createExport(null, null);
  }

  public static Export createExport(HashMap<String, Object> parameters) throws RuntimeException {
    return createExport(parameters, null);
  }


  public static Export createExport(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (!parameters.containsKey("action_notification_export_id") || parameters.get("action_notification_export_id") == null) {
      throw new NullPointerException("Parameter missing: action_notification_export_id parameters[\"action_notification_export_id\"]");
    }

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }
    if (parameters.containsKey("action_notification_export_id") && !(parameters.get("action_notification_export_id") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: action_notification_export_id must be of type Long parameters[\"action_notification_export_id\"]");
    }


    String url = String.format("%s%s/action_notification_export_results/create_export", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<Export> typeReference = new TypeReference<Export>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


}
