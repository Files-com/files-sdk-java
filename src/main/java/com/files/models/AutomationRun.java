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
  * ID of the associated Automation.
  */
  @Getter
  @JsonProperty("automation_id")
  private Long automationId;

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

}


