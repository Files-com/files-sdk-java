package com.files.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Date;
import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;

public class Request {
  private HashMap<String, Object> attributes;
  private HashMap<String, Object> options;

  public Request(HashMap<String, Object> attributes, HashMap<String, Object> options) {
    this.attributes = attributes;
    this.options = options;
    try{
      ObjectMapper objectMapper=new ObjectMapper();
      ObjectReader objectReader=objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(attributes));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Request ID
  */
  @Getter
  @Setter
  @JsonProperty("id")
  public Long id;

  /**
  * Folder path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @Getter
  @Setter
  @JsonProperty("path")
  public String path;

  /**
  * Source filename, if applicable
  */
  @Getter
  @Setter
  @JsonProperty("source")
  public String source;

  /**
  * Destination filename
  */
  @Getter
  @Setter
  @JsonProperty("destination")
  public String destination;

  /**
  * ID of automation that created request
  */
  @Getter
  @Setter
  @JsonProperty("automation_id")
  public String automationId;

  /**
  * User making the request (if applicable)
  */
  @Getter
  @Setter
  @JsonProperty("user_display_name")
  public String userDisplayName;

  /**
  * A list of user IDs to request the file from. If sent as a string, it should be comma-delimited.
  */
  @Getter
  @Setter
  @JsonProperty("user_ids")
  public String userIds;

  /**
  * A list of group IDs to request the file from. If sent as a string, it should be comma-delimited.
  */
  @Getter
  @Setter
  @JsonProperty("group_ids")
  public String groupIds;


  public void save() {
    if (this.attributes.get("path") != null) {
      throw new UnsupportedOperationException("The Request Object doesn't support updates.");
    } else {
      Request newObj = Request.create(this.attributes, this.options);
      this.attributes = newObj.attributes;
    }
  }

  /**
  * Parameters:
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  *   cursor - string - Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   sort_by - object - If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `site_id`, `folder_id` or `destination`.
  *   mine - boolean - Only show requests of the current user?  (Defaults to true if current user is not a site admin.)
  *   path - string - Path to show requests for.  If omitted, shows all paths. Send `/` to represent the root directory.
  */
  public static Request list(String path,  HashMap<String, Object> parameters) {
    return list(path, parameters, null);
  }

  public static Request list(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return list(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static Request list(String path,  HashMap<String, Object> parameters, HashMap<String, Object> options) {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path != null){
      parameters.put("path",path);
    }
    if (parameters.containsKey("page") && !(parameters.get("page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: page must be of type Long parameters[\"page\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }

    if (parameters.containsKey("action") && !(parameters.get("action") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: action must be of type String parameters[\"action\"]");
    }

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Object parameters[\"sort_by\"]");
    }

    if (parameters.containsKey("mine") && !(parameters.get("mine") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: mine must be of type Boolean parameters[\"mine\"]");
    }

    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    // TODO: Send request
    return (Request) null;
  }

  public static Request all(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return list(path, parameters, options);
  }

  /**
  * Parameters:
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  *   cursor - string - Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   sort_by - object - If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `site_id`, `folder_id` or `destination`.
  *   mine - boolean - Only show requests of the current user?  (Defaults to true if current user is not a site admin.)
  *   path (required) - string - Path to show requests for.  If omitted, shows all paths. Send `/` to represent the root directory.
  */
  public static Request findFolder(String path,  HashMap<String, Object> parameters) {
    return findFolder(path, parameters, null);
  }

  public static Request findFolder(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return findFolder(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static Request findFolder(String path,  HashMap<String, Object> parameters, HashMap<String, Object> options) {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path != null){
      parameters.put("path",path);
    }
    if (parameters.containsKey("page") && !(parameters.get("page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: page must be of type Long parameters[\"page\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }

    if (parameters.containsKey("action") && !(parameters.get("action") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: action must be of type String parameters[\"action\"]");
    }

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Object parameters[\"sort_by\"]");
    }

    if (parameters.containsKey("mine") && !(parameters.get("mine") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: mine must be of type Boolean parameters[\"mine\"]");
    }

    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (!parameters.containsKey("path") || parameters.get("path") == null) {
      throw new NullPointerException("Parameter missing: path parameters[\"path\"]");
    }
    // TODO: Send request
    return (Request) null;
  }


  /**
  * Parameters:
  *   path (required) - string - Folder path on which to request the file.
  *   destination (required) - string - Destination filename (without extension) to request.
  *   user_ids - string - A list of user IDs to request the file from. If sent as a string, it should be comma-delimited.
  *   group_ids - string - A list of group IDs to request the file from. If sent as a string, it should be comma-delimited.
  */
  public static Request create(String path,  HashMap<String, Object> parameters) {
    return create(path, parameters, null);
  }

  public static Request create(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return create(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static Request create(String path,  HashMap<String, Object> parameters, HashMap<String, Object> options) {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path != null){
      parameters.put("path",path);
    }
    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("destination") && !(parameters.get("destination") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: destination must be of type String parameters[\"destination\"]");
    }

    if (parameters.containsKey("user_ids") && !(parameters.get("user_ids") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: user_ids must be of type String parameters[\"user_ids\"]");
    }

    if (parameters.containsKey("group_ids") && !(parameters.get("group_ids") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: group_ids must be of type String parameters[\"group_ids\"]");
    }

    if (!parameters.containsKey("path") || parameters.get("path") == null) {
      throw new NullPointerException("Parameter missing: path parameters[\"path\"]");
    }
    if (!parameters.containsKey("destination") || parameters.get("destination") == null) {
      throw new NullPointerException("Parameter missing: destination parameters[\"destination\"]");
    }
    // TODO: Send request
    return (Request) null;
  }


  /**
  * Parameters:
  *   id (required) - int64 - Request ID.
  */
  public static Request delete(Long id,  HashMap<String, Object> parameters) {
    return delete(id, parameters, null);
  }

  public static Request delete(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return delete(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static Request delete(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) {
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
    // TODO: Send request
    return (Request) null;
  }

  public static Request destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return delete(id, parameters, options);
  }

}


