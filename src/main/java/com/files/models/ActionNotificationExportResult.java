package com.files.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.ModelUtils;
import com.files.util.FilesInputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class ActionNotificationExportResult {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public ActionNotificationExportResult() {
    this(null, null);
  }

  public ActionNotificationExportResult(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public ActionNotificationExportResult(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Notification ID
  */
  @Getter
  @JsonProperty("id")
  private Long id;

  /**
  * When the notification was sent.
  */
  @Getter
  @JsonProperty("created_at")
  private Long createdAt;

  /**
  * HTTP status code returned in the webhook response.
  */
  @Getter
  @JsonProperty("status")
  private Long status;

  /**
  * A message indicating the overall status of the webhook notification.
  */
  @Getter
  @JsonProperty("message")
  private String message;

  /**
  * `true` if the webhook succeeded by receiving a 200 or 204 response.
  */
  @Getter
  @JsonProperty("success")
  private Boolean success;

  /**
  * A JSON-encoded string with headers that were sent with the webhook.
  */
  @Getter
  @JsonProperty("request_headers")
  private String requestHeaders;

  /**
  * The HTTP verb used to perform the webhook.
  */
  @Getter
  @JsonProperty("request_method")
  private String requestMethod;

  /**
  * The webhook request URL.
  */
  @Getter
  @JsonProperty("request_url")
  private String requestUrl;

  /**
  * The path to the actual file that triggered this notification. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @Getter
  @JsonProperty("path")
  private String path;

  /**
  * The folder associated with the triggering action for this notification.
  */
  @Getter
  @JsonProperty("folder")
  private String folder;



  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   cursor - string - Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action_notification_export_id (required) - int64 - ID of the associated action notification export.
  */
  public static List<ActionNotificationExportResult> list() throws IOException{
    return list(null,null);
  }
  public static List<ActionNotificationExportResult> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  public static List<ActionNotificationExportResult> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }

    if (parameters.containsKey("action_notification_export_id") && !(parameters.get("action_notification_export_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: action_notification_export_id must be of type Long parameters[\"action_notification_export_id\"]");
    }

    if (!parameters.containsKey("action_notification_export_id") || parameters.get("action_notification_export_id") == null) {
      throw new NullPointerException("Parameter missing: action_notification_export_id parameters[\"action_notification_export_id\"]");
    }
    String url = String.format("%s%s/action_notification_export_results", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<ActionNotificationExportResult>> typeReference = new TypeReference<List<ActionNotificationExportResult>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<ActionNotificationExportResult> all() throws IOException {
    return all(null, null);
  }

  public static List<ActionNotificationExportResult> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

}


