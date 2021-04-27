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

public class ActionNotificationExport {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public ActionNotificationExport() {
    this(null, null);
  }

  public ActionNotificationExport(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public ActionNotificationExport(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * History Export ID
  */
  @Getter
  @Setter
  @JsonProperty("id")
  private Long id;

  /**
  * Version of the underlying records for the export.
  */
  @Getter
  @Setter
  @JsonProperty("export_version")
  private String exportVersion;

  /**
  * Start date/time of export range.
  */
  @Getter
  @Setter
  @JsonProperty("start_at")
  private Date startAt;

  /**
  * End date/time of export range.
  */
  @Getter
  @Setter
  @JsonProperty("end_at")
  private Date endAt;

  /**
  * Status of export.  Valid values: `building`, `ready`, or `failed`
  */
  @Getter
  @Setter
  @JsonProperty("status")
  private String status;

  /**
  * Return notifications that were triggered by actions on this specific path.
  */
  @Getter
  @Setter
  @JsonProperty("query_path")
  private String queryPath;

  /**
  * Return notifications that were triggered by actions in this folder.
  */
  @Getter
  @Setter
  @JsonProperty("query_folder")
  private String queryFolder;

  /**
  * Error message associated with the request, if any.
  */
  @Getter
  @Setter
  @JsonProperty("query_message")
  private String queryMessage;

  /**
  * The HTTP request method used by the webhook.
  */
  @Getter
  @Setter
  @JsonProperty("query_request_method")
  private String queryRequestMethod;

  /**
  * The target webhook URL.
  */
  @Getter
  @Setter
  @JsonProperty("query_request_url")
  private String queryRequestUrl;

  /**
  * The HTTP status returned from the server in response to the webhook request.
  */
  @Getter
  @Setter
  @JsonProperty("query_status")
  private String queryStatus;

  /**
  * true if the webhook request succeeded (i.e. returned a 200 or 204 response status). false otherwise.
  */
  @Getter
  @Setter
  @JsonProperty("query_success")
  private Boolean querySuccess;

  /**
  * If `status` is `ready`, this will be a URL where all the results can be downloaded at once as a CSV.
  */
  @Getter
  @Setter
  @JsonProperty("results_url")
  private String resultsUrl;

  /**
  * User ID.  Provide a value of `0` to operate the current session's user.
  */
  @Getter
  @Setter
  @JsonProperty("user_id")
  private Long userId;


  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    if (parameters.containsKey("id") && parameters.get("id") != null) {
      throw new UnsupportedOperationException("The ActionNotificationExport Object doesn't support updates.");
    } else {
      ActionNotificationExport newObject = ActionNotificationExport.create(parameters, this.options);
    }
  }

  /**
  * Parameters:
  *   id (required) - int64 - Action Notification Export ID.
  */
  public static List<ActionNotificationExport> find() throws IOException{
    return find(null, null,null);
  }
  public static List<ActionNotificationExport> find(Long id,  HashMap<String, Object> parameters) throws IOException {
    return find(id, parameters, null);
  }

  public static List<ActionNotificationExport> find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(null, parameters, options);
  }

  public static List<ActionNotificationExport> find(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id != null){
      parameters.put("id",id);
    }
    if (parameters.containsKey("id") && !(parameters.get("id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    String url = String.format("%s%s/action_notification_exports/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<List<ActionNotificationExport>> typeReference = new TypeReference<List<ActionNotificationExport>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<ActionNotificationExport> get() throws IOException {
    return get(null, null, null);
  }

  public static List<ActionNotificationExport> get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   start_at - string - Start date/time of export range.
  *   end_at - string - End date/time of export range.
  *   query_message - string - Error message associated with the request, if any.
  *   query_request_method - string - The HTTP request method used by the webhook.
  *   query_request_url - string - The target webhook URL.
  *   query_status - string - The HTTP status returned from the server in response to the webhook request.
  *   query_success - boolean - true if the webhook request succeeded (i.e. returned a 200 or 204 response status). false otherwise.
  *   query_path - string - Return notifications that were triggered by actions on this specific path.
  *   query_folder - string - Return notifications that were triggered by actions in this folder.
  */
  public static ActionNotificationExport create() throws IOException{
    return create(null,null);
  }
  public static ActionNotificationExport create( HashMap<String, Object> parameters) throws IOException {
    return create(parameters, null);
  }


  public static ActionNotificationExport create( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }

    if (parameters.containsKey("start_at") && !(parameters.get("start_at") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: start_at must be of type String parameters[\"start_at\"]");
    }

    if (parameters.containsKey("end_at") && !(parameters.get("end_at") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: end_at must be of type String parameters[\"end_at\"]");
    }

    if (parameters.containsKey("query_message") && !(parameters.get("query_message") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: query_message must be of type String parameters[\"query_message\"]");
    }

    if (parameters.containsKey("query_request_method") && !(parameters.get("query_request_method") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: query_request_method must be of type String parameters[\"query_request_method\"]");
    }

    if (parameters.containsKey("query_request_url") && !(parameters.get("query_request_url") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: query_request_url must be of type String parameters[\"query_request_url\"]");
    }

    if (parameters.containsKey("query_status") && !(parameters.get("query_status") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: query_status must be of type String parameters[\"query_status\"]");
    }

    if (parameters.containsKey("query_success") && !(parameters.get("query_success") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: query_success must be of type Boolean parameters[\"query_success\"]");
    }

    if (parameters.containsKey("query_path") && !(parameters.get("query_path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: query_path must be of type String parameters[\"query_path\"]");
    }

    if (parameters.containsKey("query_folder") && !(parameters.get("query_folder") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: query_folder must be of type String parameters[\"query_folder\"]");
    }

    String url = String.format("%s%s/action_notification_exports", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<ActionNotificationExport> typeReference = new TypeReference<ActionNotificationExport>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


}


