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

public class History {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public History() {
    this(null, null);
  }

  public History(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public History(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Action ID
  */
  @Getter
  @JsonProperty("id")
  private Long id;

  /**
  * Path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @Getter
  @JsonProperty("path")
  private String path;

  /**
  * Action occurrence date/time
  */
  @Getter
  @JsonProperty("when")
  private Date when;

  /**
  * The destination path for this action, if applicable
  */
  @Getter
  @JsonProperty("destination")
  private String destination;

  /**
  * Friendly displayed output
  */
  @Getter
  @JsonProperty("display")
  private String display;

  /**
  * IP Address that performed this action
  */
  @Getter
  @JsonProperty("ip")
  private String ip;

  /**
  * The source path for this action, if applicable
  */
  @Getter
  @JsonProperty("source")
  private String source;

  /**
  * Targets
  */
  @Getter
  @JsonProperty("targets")
  private Object[] targets;

  /**
  * User ID
  */
  @Getter
  @JsonProperty("user_id")
  private Long userId;

  /**
  * Username
  */
  @Getter
  @JsonProperty("username")
  private String username;

  /**
  * Type of action
  */
  @Getter
  @JsonProperty("action")
  private String action;

  /**
  * Failure type.  If action was a user login or session failure, why did it fail?
  */
  @Getter
  @JsonProperty("failure_type")
  private String failureType;

  /**
  * Interface on which this action occurred.
  */
  @Getter
  @JsonProperty("interface")
  private String interfaceName;



  /**
  * Parameters:
  *   start_at - string - Leave blank or set to a date/time to filter earlier entries.
  *   end_at - string - Leave blank or set to a date/time to filter later entries.
  *   display - string - Display format. Leave blank or set to `full` or `parent`.
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  *   cursor - string - Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   sort_by - object - If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `user_id` and `created_at`.
  *   path (required) - string - Path to operate on.
  */
  public static List<History> listForFile() throws IOException{
    return listForFile(null, null,null);
  }
  public static List<History> listForFile(String path,  HashMap<String, Object> parameters) throws IOException {
    return listForFile(path, parameters, null);
  }

  public static List<History> listForFile(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return listForFile(null, parameters, options);
  }

  public static List<History> listForFile(String path,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path != null){
      parameters.put("path",path);
    }
    if (parameters.containsKey("start_at") && !(parameters.get("start_at") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: start_at must be of type String parameters[\"start_at\"]");
    }

    if (parameters.containsKey("end_at") && !(parameters.get("end_at") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: end_at must be of type String parameters[\"end_at\"]");
    }

    if (parameters.containsKey("display") && !(parameters.get("display") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: display must be of type String parameters[\"display\"]");
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

    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Map<String, String> parameters[\"sort_by\"]");
    }

    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (!parameters.containsKey("path") || parameters.get("path") == null) {
      throw new NullPointerException("Parameter missing: path parameters[\"path\"]");
    }
    String url = String.format("%s%s/history/files/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), path);
    TypeReference<List<History>> typeReference = new TypeReference<List<History>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   start_at - string - Leave blank or set to a date/time to filter earlier entries.
  *   end_at - string - Leave blank or set to a date/time to filter later entries.
  *   display - string - Display format. Leave blank or set to `full` or `parent`.
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  *   cursor - string - Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   sort_by - object - If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `user_id` and `created_at`.
  *   path (required) - string - Path to operate on.
  */
  public static List<History> listForFolder() throws IOException{
    return listForFolder(null, null,null);
  }
  public static List<History> listForFolder(String path,  HashMap<String, Object> parameters) throws IOException {
    return listForFolder(path, parameters, null);
  }

  public static List<History> listForFolder(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return listForFolder(null, parameters, options);
  }

  public static List<History> listForFolder(String path,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path != null){
      parameters.put("path",path);
    }
    if (parameters.containsKey("start_at") && !(parameters.get("start_at") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: start_at must be of type String parameters[\"start_at\"]");
    }

    if (parameters.containsKey("end_at") && !(parameters.get("end_at") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: end_at must be of type String parameters[\"end_at\"]");
    }

    if (parameters.containsKey("display") && !(parameters.get("display") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: display must be of type String parameters[\"display\"]");
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

    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Map<String, String> parameters[\"sort_by\"]");
    }

    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (!parameters.containsKey("path") || parameters.get("path") == null) {
      throw new NullPointerException("Parameter missing: path parameters[\"path\"]");
    }
    String url = String.format("%s%s/history/folders/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), path);
    TypeReference<List<History>> typeReference = new TypeReference<List<History>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   start_at - string - Leave blank or set to a date/time to filter earlier entries.
  *   end_at - string - Leave blank or set to a date/time to filter later entries.
  *   display - string - Display format. Leave blank or set to `full` or `parent`.
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  *   cursor - string - Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   sort_by - object - If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `user_id` and `created_at`.
  *   user_id (required) - int64 - User ID.
  */
  public static List<History> listForUser() throws IOException{
    return listForUser(null, null,null);
  }
  public static List<History> listForUser(Long user_id,  HashMap<String, Object> parameters) throws IOException {
    return listForUser(user_id, parameters, null);
  }

  public static List<History> listForUser(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return listForUser(null, parameters, options);
  }

  public static List<History> listForUser(Long user_id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (user_id != null){
      parameters.put("user_id",user_id);
    }
    if (parameters.containsKey("start_at") && !(parameters.get("start_at") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: start_at must be of type String parameters[\"start_at\"]");
    }

    if (parameters.containsKey("end_at") && !(parameters.get("end_at") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: end_at must be of type String parameters[\"end_at\"]");
    }

    if (parameters.containsKey("display") && !(parameters.get("display") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: display must be of type String parameters[\"display\"]");
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

    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Map<String, String> parameters[\"sort_by\"]");
    }

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }

    if (!parameters.containsKey("user_id") || parameters.get("user_id") == null) {
      throw new NullPointerException("Parameter missing: user_id parameters[\"user_id\"]");
    }
    String url = String.format("%s%s/history/users/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), user_id);
    TypeReference<List<History>> typeReference = new TypeReference<List<History>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   start_at - string - Leave blank or set to a date/time to filter earlier entries.
  *   end_at - string - Leave blank or set to a date/time to filter later entries.
  *   display - string - Display format. Leave blank or set to `full` or `parent`.
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  *   cursor - string - Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   sort_by - object - If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `user_id` and `created_at`.
  */
  public static List<History> listLogins() throws IOException{
    return listLogins(null,null);
  }
  public static List<History> listLogins( HashMap<String, Object> parameters) throws IOException {
    return listLogins(parameters, null);
  }


  public static List<History> listLogins( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("start_at") && !(parameters.get("start_at") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: start_at must be of type String parameters[\"start_at\"]");
    }

    if (parameters.containsKey("end_at") && !(parameters.get("end_at") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: end_at must be of type String parameters[\"end_at\"]");
    }

    if (parameters.containsKey("display") && !(parameters.get("display") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: display must be of type String parameters[\"display\"]");
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

    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Map<String, String> parameters[\"sort_by\"]");
    }

    String url = String.format("%s%s/history/login", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<History>> typeReference = new TypeReference<List<History>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   start_at - string - Leave blank or set to a date/time to filter earlier entries.
  *   end_at - string - Leave blank or set to a date/time to filter later entries.
  *   display - string - Display format. Leave blank or set to `full` or `parent`.
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  *   cursor - string - Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   sort_by - object - If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `site_id`, `path`, `created_at`, `folder` or `user_id`.
  *   filter - object - If set, return records where the specifiied field is equal to the supplied value. Valid fields are `user_id`, `folder` or `path`.
  *   filter_gt - object - If set, return records where the specifiied field is greater than the supplied value. Valid fields are `user_id`, `folder` or `path`.
  *   filter_gteq - object - If set, return records where the specifiied field is greater than or equal to the supplied value. Valid fields are `user_id`, `folder` or `path`.
  *   filter_like - object - If set, return records where the specifiied field is equal to the supplied value. Valid fields are `user_id`, `folder` or `path`.
  *   filter_lt - object - If set, return records where the specifiied field is less than the supplied value. Valid fields are `user_id`, `folder` or `path`.
  *   filter_lteq - object - If set, return records where the specifiied field is less than or equal to the supplied value. Valid fields are `user_id`, `folder` or `path`.
  */
  public static List<History> list() throws IOException{
    return list(null,null);
  }
  public static List<History> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  public static List<History> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("start_at") && !(parameters.get("start_at") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: start_at must be of type String parameters[\"start_at\"]");
    }

    if (parameters.containsKey("end_at") && !(parameters.get("end_at") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: end_at must be of type String parameters[\"end_at\"]");
    }

    if (parameters.containsKey("display") && !(parameters.get("display") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: display must be of type String parameters[\"display\"]");
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

    String url = String.format("%s%s/history", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<History>> typeReference = new TypeReference<List<History>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<History> all() throws IOException {
    return all(null, null);
  }

  public static List<History> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

}


