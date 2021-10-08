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

public class AutomationRun {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public AutomationRun() {
    this(null, null);
  }

  public AutomationRun(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public AutomationRun(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * ID.
  */
  @Getter
  @JsonProperty("id")
  private Long id;

  /**
  * ID of the associated Automation.
  */
  @Getter
  @JsonProperty("automation_id")
  private Long automationId;

  /**
  * Automation run completion/failure date/time.
  */
  @Getter
  @JsonProperty("completed_at")
  private Date completedAt;

  /**
  * Automation run start date/time.
  */
  @Getter
  @JsonProperty("created_at")
  private Date createdAt;

  /**
  * The success status of the AutomationRun. One of `running`, `success`, `partial_failure`, or `failure`.
  */
  @Getter
  @JsonProperty("status")
  private String status;

  /**
  * Link to status messages log file.
  */
  @Getter
  @JsonProperty("status_messages_url")
  private String statusMessagesUrl;



  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   cursor - string - Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `created_at` and `status`.
  *   filter - object - If set, return records where the specifiied field is equal to the supplied value. Valid fields are `status`.
  *   filter_gt - object - If set, return records where the specifiied field is greater than the supplied value. Valid fields are `status`.
  *   filter_gteq - object - If set, return records where the specifiied field is greater than or equal to the supplied value. Valid fields are `status`.
  *   filter_like - object - If set, return records where the specifiied field is equal to the supplied value. Valid fields are `status`.
  *   filter_lt - object - If set, return records where the specifiied field is less than the supplied value. Valid fields are `status`.
  *   filter_lteq - object - If set, return records where the specifiied field is less than or equal to the supplied value. Valid fields are `status`.
  *   automation_id (required) - int64 - ID of the associated Automation.
  */
  public static List<AutomationRun> list() throws IOException{
    return list(null,null);
  }
  public static List<AutomationRun> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  public static List<AutomationRun> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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

    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Map<String, String> parameters[\"sort_by\"]");
    }

    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Map<String, String> parameters[\"filter\"]");
    }

    if (parameters.containsKey("filter_gt") && !(parameters.get("filter_gt") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter_gt must be of type Map<String, String> parameters[\"filter_gt\"]");
    }

    if (parameters.containsKey("filter_gteq") && !(parameters.get("filter_gteq") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter_gteq must be of type Map<String, String> parameters[\"filter_gteq\"]");
    }

    if (parameters.containsKey("filter_like") && !(parameters.get("filter_like") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter_like must be of type Map<String, String> parameters[\"filter_like\"]");
    }

    if (parameters.containsKey("filter_lt") && !(parameters.get("filter_lt") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter_lt must be of type Map<String, String> parameters[\"filter_lt\"]");
    }

    if (parameters.containsKey("filter_lteq") && !(parameters.get("filter_lteq") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter_lteq must be of type Map<String, String> parameters[\"filter_lteq\"]");
    }

    if (parameters.containsKey("automation_id") && !(parameters.get("automation_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: automation_id must be of type Long parameters[\"automation_id\"]");
    }

    if (!parameters.containsKey("automation_id") || parameters.get("automation_id") == null) {
      throw new NullPointerException("Parameter missing: automation_id parameters[\"automation_id\"]");
    }
    String url = String.format("%s%s/automation_runs", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<AutomationRun>> typeReference = new TypeReference<List<AutomationRun>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<AutomationRun> all() throws IOException {
    return all(null, null);
  }

  public static List<AutomationRun> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Automation Run ID.
  */
  public static List<AutomationRun> find() throws IOException{
    return find(null, null,null);
  }
  public static List<AutomationRun> find(Long id,  HashMap<String, Object> parameters) throws IOException {
    return find(id, parameters, null);
  }

  public static List<AutomationRun> find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(null, parameters, options);
  }

  public static List<AutomationRun> find(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
    String url = String.format("%s%s/automation_runs/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<List<AutomationRun>> typeReference = new TypeReference<List<AutomationRun>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<AutomationRun> get() throws IOException {
    return get(null, null, null);
  }

  public static List<AutomationRun> get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(id, parameters, options);
  }

}


