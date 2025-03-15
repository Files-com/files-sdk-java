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
public class History implements ModelInterface {
  @Setter
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();


  public History() {
    this(null, null);
  }

  public History(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public History(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Action ID
  */
  @Getter
  @JsonProperty("id")
  public Long id;

  /**
  * Path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @Getter
  @JsonProperty("path")
  public String path;

  /**
  * Action occurrence date/time
  */
  @Getter
  @JsonProperty("when")
  public Date when;

  /**
  * The destination path for this action, if applicable
  */
  @Getter
  @JsonProperty("destination")
  public String destination;

  /**
  * Friendly displayed output
  */
  @Getter
  @JsonProperty("display")
  public String display;

  /**
  * IP Address that performed this action
  */
  @Getter
  @JsonProperty("ip")
  public String ip;

  /**
  * The source path for this action, if applicable
  */
  @Getter
  @JsonProperty("source")
  public String source;

  /**
  * Targets
  */
  @Getter
  @JsonProperty("targets")
  public Map<String, String> targets;

  /**
  * User ID
  */
  @Getter
  @JsonProperty("user_id")
  public Long userId;

  /**
  * Username
  */
  @Getter
  @JsonProperty("username")
  public String username;

  /**
  * true if this change was performed by a user on a parent site.
  */
  @Getter
  @JsonProperty("user_is_from_parent_site")
  public Boolean userIsFromParentSite;

  /**
  * Type of action
  */
  @Getter
  @JsonProperty("action")
  public String action;

  /**
  * Failure type.  If action was a user login or session failure, why did it fail?
  */
  @Getter
  @JsonProperty("failure_type")
  public String failureType;

  /**
  * Interface on which this action occurred.
  */
  @Getter
  @JsonProperty("interface")
  public String interfaceName;



  /**
  * Parameters:
  *   start_at - string - Leave blank or set to a date/time to filter earlier entries.
  *   end_at - string - Leave blank or set to a date/time to filter later entries.
  *   display - string - Display format. Leave blank or set to `full` or `parent`.
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`.
  *   path (required) - string - Path to operate on.
  */
  public static ListIterator<Action> listForFile() throws RuntimeException {
    return listForFile(null, null, null);
  }

  public static ListIterator<Action> listForFile(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return listForFile(path, parameters, null);
  }

  public static ListIterator<Action> listForFile(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return listForFile(null, parameters, options);
  }

  public static ListIterator<Action> listForFile(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }


    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (parameters.containsKey("start_at") && !(parameters.get("start_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: start_at must be of type String parameters[\"start_at\"]");
    }
    if (parameters.containsKey("end_at") && !(parameters.get("end_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: end_at must be of type String parameters[\"end_at\"]");
    }
    if (parameters.containsKey("display") && !(parameters.get("display") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: display must be of type String parameters[\"display\"]");
    }
    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }
    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Map<String, String> parameters[\"sort_by\"]");
    }
    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), path};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/history/files/%s", urlParts);

    TypeReference<List<Action>> typeReference = new TypeReference<List<Action>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   start_at - string - Leave blank or set to a date/time to filter earlier entries.
  *   end_at - string - Leave blank or set to a date/time to filter later entries.
  *   display - string - Display format. Leave blank or set to `full` or `parent`.
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`.
  *   path (required) - string - Path to operate on.
  */
  public static ListIterator<Action> listForFolder() throws RuntimeException {
    return listForFolder(null, null, null);
  }

  public static ListIterator<Action> listForFolder(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return listForFolder(path, parameters, null);
  }

  public static ListIterator<Action> listForFolder(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return listForFolder(null, parameters, options);
  }

  public static ListIterator<Action> listForFolder(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }


    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (parameters.containsKey("start_at") && !(parameters.get("start_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: start_at must be of type String parameters[\"start_at\"]");
    }
    if (parameters.containsKey("end_at") && !(parameters.get("end_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: end_at must be of type String parameters[\"end_at\"]");
    }
    if (parameters.containsKey("display") && !(parameters.get("display") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: display must be of type String parameters[\"display\"]");
    }
    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }
    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Map<String, String> parameters[\"sort_by\"]");
    }
    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), path};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/history/folders/%s", urlParts);

    TypeReference<List<Action>> typeReference = new TypeReference<List<Action>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   start_at - string - Leave blank or set to a date/time to filter earlier entries.
  *   end_at - string - Leave blank or set to a date/time to filter later entries.
  *   display - string - Display format. Leave blank or set to `full` or `parent`.
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`.
  *   user_id (required) - int64 - User ID.
  */
  public static ListIterator<Action> listForUser() throws RuntimeException {
    return listForUser(null, null, null);
  }

  public static ListIterator<Action> listForUser(Long user_id, HashMap<String, Object> parameters) throws RuntimeException {
    return listForUser(user_id, parameters, null);
  }

  public static ListIterator<Action> listForUser(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return listForUser(null, parameters, options);
  }

  public static ListIterator<Action> listForUser(Long user_id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (user_id == null && parameters.containsKey("user_id") && parameters.get("user_id") != null) {
      user_id = (Long) parameters.get("user_id");
    }


    if (user_id == null) {
      throw new NullPointerException("Argument or Parameter missing: user_id parameters[\"user_id\"]");
    }

    if (parameters.containsKey("start_at") && !(parameters.get("start_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: start_at must be of type String parameters[\"start_at\"]");
    }
    if (parameters.containsKey("end_at") && !(parameters.get("end_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: end_at must be of type String parameters[\"end_at\"]");
    }
    if (parameters.containsKey("display") && !(parameters.get("display") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: display must be of type String parameters[\"display\"]");
    }
    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }
    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Map<String, String> parameters[\"sort_by\"]");
    }
    if (!(user_id instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(user_id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/history/users/%s", urlParts);

    TypeReference<List<Action>> typeReference = new TypeReference<List<Action>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   start_at - string - Leave blank or set to a date/time to filter earlier entries.
  *   end_at - string - Leave blank or set to a date/time to filter later entries.
  *   display - string - Display format. Leave blank or set to `full` or `parent`.
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`.
  */
  public static ListIterator<Action> listLogins() throws RuntimeException {
    return listLogins(null, null);
  }

  public static ListIterator<Action> listLogins(HashMap<String, Object> parameters) throws RuntimeException {
    return listLogins(parameters, null);
  }


  public static ListIterator<Action> listLogins(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("start_at") && !(parameters.get("start_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: start_at must be of type String parameters[\"start_at\"]");
    }
    if (parameters.containsKey("end_at") && !(parameters.get("end_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: end_at must be of type String parameters[\"end_at\"]");
    }
    if (parameters.containsKey("display") && !(parameters.get("display") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: display must be of type String parameters[\"display\"]");
    }
    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }
    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Map<String, String> parameters[\"sort_by\"]");
    }


    String url = String.format("%s%s/history/login", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<Action>> typeReference = new TypeReference<List<Action>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   start_at - string - Leave blank or set to a date/time to filter earlier entries.
  *   end_at - string - Leave blank or set to a date/time to filter later entries.
  *   display - string - Display format. Leave blank or set to `full` or `parent`.
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `user_id`, `folder` or `path`. Valid field combinations are `[  ]`, `[ path ]`, `[ path ]` or `[ path ]`.
  *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `path`.
  */
  public static ListIterator<Action> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<Action> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<Action> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("start_at") && !(parameters.get("start_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: start_at must be of type String parameters[\"start_at\"]");
    }
    if (parameters.containsKey("end_at") && !(parameters.get("end_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: end_at must be of type String parameters[\"end_at\"]");
    }
    if (parameters.containsKey("display") && !(parameters.get("display") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: display must be of type String parameters[\"display\"]");
    }
    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }
    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Map<String, String> parameters[\"sort_by\"]");
    }
    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Map<String, String> parameters[\"filter\"]");
    }
    if (parameters.containsKey("filter_prefix") && !(parameters.get("filter_prefix") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_prefix must be of type Map<String, String> parameters[\"filter_prefix\"]");
    }


    String url = String.format("%s%s/history", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<Action>> typeReference = new TypeReference<List<Action>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<Action> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<Action> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
