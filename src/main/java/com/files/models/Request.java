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
import com.files.util.UrlUtils;
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
public class Request implements ModelInterface {
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


  public Request() {
    this(null, null);
  }

  public Request(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Request(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Request ID
  */
  @JsonProperty("id")
  public Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /**
  * Folder path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @JsonProperty("path")
  public String path;

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  /**
  * Source filename, if applicable
  */
  @JsonProperty("source")
  public String source;

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  /**
  * Destination filename
  */
  @JsonProperty("destination")
  public String destination;

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  /**
  * ID of automation that created request
  */
  @JsonProperty("automation_id")
  public Long automationId;

  public Long getAutomationId() {
    return automationId;
  }

  public void setAutomationId(Long automationId) {
    this.automationId = automationId;
  }

  /**
  * User making the request (if applicable)
  */
  @JsonProperty("user_display_name")
  public String userDisplayName;

  public String getUserDisplayName() {
    return userDisplayName;
  }

  public void setUserDisplayName(String userDisplayName) {
    this.userDisplayName = userDisplayName;
  }

  /**
  * A list of user IDs to request the file from. If sent as a string, it should be comma-delimited.
  */
  @JsonProperty("user_ids")
  public String userIds;

  public String getUserIds() {
    return userIds;
  }

  public void setUserIds(String userIds) {
    this.userIds = userIds;
  }

  /**
  * A list of group IDs to request the file from. If sent as a string, it should be comma-delimited.
  */
  @JsonProperty("group_ids")
  public String groupIds;

  public String getGroupIds() {
    return groupIds;
  }

  public void setGroupIds(String groupIds) {
    this.groupIds = groupIds;
  }

  /**
  */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    Request.delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    Request.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are .
  *   mine - boolean - Only show requests of the current user?  (Defaults to true if current user is not a site admin.)
  *   path - string - Path to show requests for.  If omitted, shows all paths. Send `/` to represent the root directory.
  */
  public static ListIterator<Request> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<Request> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<Request> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }
    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Object parameters[\"sort_by\"]");
    }
    if (parameters.containsKey("mine") && !(parameters.get("mine") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: mine must be of type Boolean parameters[\"mine\"]");
    }
    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }


    String url = String.format("%s%s/requests", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<Request>> typeReference = new TypeReference<List<Request>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<Request> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<Request> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are .
  *   mine - boolean - Only show requests of the current user?  (Defaults to true if current user is not a site admin.)
  *   path (required) - string - Path to show requests for.  If omitted, shows all paths. Send `/` to represent the root directory.
  */
  public static Request getFolder() throws RuntimeException {
    return getFolder(null, null, null);
  }

  public static Request getFolder(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return getFolder(path, parameters, null);
  }

  public static Request getFolder(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return getFolder(null, parameters, options);
  }

  public static Request getFolder(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }


    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }
    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Object parameters[\"sort_by\"]");
    }
    if (parameters.containsKey("mine") && !(parameters.get("mine") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: mine must be of type Boolean parameters[\"mine\"]");
    }
    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }



    String url = String.format("%s%s/requests/folders/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(path));

    TypeReference<Request> typeReference = new TypeReference<Request>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   path (required) - string - Folder path on which to request the file.
  *   destination (required) - string - Destination filename (without extension) to request.
  *   user_ids - string - A list of user IDs to request the file from. If sent as a string, it should be comma-delimited.
  *   group_ids - string - A list of group IDs to request the file from. If sent as a string, it should be comma-delimited.
  */
  public static Request create() throws RuntimeException {
    return create(null, null);
  }

  public static Request create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static Request create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (!parameters.containsKey("path") || parameters.get("path") == null) {
      throw new NullPointerException("Parameter missing: path parameters[\"path\"]");
    }
    if (!parameters.containsKey("destination") || parameters.get("destination") == null) {
      throw new NullPointerException("Parameter missing: destination parameters[\"destination\"]");
    }

    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }
    if (parameters.containsKey("destination") && !(parameters.get("destination") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: destination must be of type String parameters[\"destination\"]");
    }
    if (parameters.containsKey("user_ids") && !(parameters.get("user_ids") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: user_ids must be of type String parameters[\"user_ids\"]");
    }
    if (parameters.containsKey("group_ids") && !(parameters.get("group_ids") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: group_ids must be of type String parameters[\"group_ids\"]");
    }


    String url = String.format("%s%s/requests", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<Request> typeReference = new TypeReference<Request>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }



    String url = String.format("%s%s/requests/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
