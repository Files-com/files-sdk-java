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

public class Lock {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public Lock() {
    this(null, null);
  }

  public Lock(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Lock(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @Getter
  @Setter
  @JsonProperty("path")
  private String path;

  /**
  * Lock timeout
  */
  @Getter
  @Setter
  @JsonProperty("timeout")
  private Long timeout;

  /**
  * Lock depth (0 or infinity)
  */
  @Getter
  @Setter
  @JsonProperty("depth")
  private String depth;

  /**
  * Owner of lock.  This can be any arbitrary string.
  */
  @Getter
  @Setter
  @JsonProperty("owner")
  private String owner;

  /**
  * Lock scope(shared or exclusive)
  */
  @Getter
  @Setter
  @JsonProperty("scope")
  private String scope;

  /**
  * Lock token.  Use to release lock.
  */
  @Getter
  @Setter
  @JsonProperty("token")
  private String token;

  /**
  * Lock type
  */
  @Getter
  @Setter
  @JsonProperty("type")
  private String type;

  /**
  * Lock creator user ID
  */
  @Getter
  @Setter
  @JsonProperty("user_id")
  private Long userId;

  /**
  * Lock creator username
  */
  @Getter
  @Setter
  @JsonProperty("username")
  private String username;

  /**
  * Parameters:
  *   token (required) - string - Lock token
  */
  public Lock delete(HashMap<String, Object> parameters) {
    return delete(parameters);
  }

  public void destroy(HashMap<String, Object> parameters) {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    if (parameters.containsKey("id") && parameters.get("id") != null) {
      throw new UnsupportedOperationException("The Lock Object doesn't support updates.");
    } else {
      Lock newObject = Lock.create(parameters, this.options);
    }
  }

  /**
  * Parameters:
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  *   cursor - string - Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   path (required) - string - Path to operate on.
  *   include_children - boolean - Include locks from children objects?
  */
  public static List<Lock> listFor() throws IOException{
    return listFor(null, null,null);
  }
  public static List<Lock> listFor(String path,  HashMap<String, Object> parameters) throws IOException {
    return listFor(path, parameters, null);
  }

  public static List<Lock> listFor(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return listFor(null, parameters, options);
  }

  public static List<Lock> listFor(String path,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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

    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("include_children") && !(parameters.get("include_children") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: include_children must be of type Boolean parameters[\"include_children\"]");
    }

    if (!parameters.containsKey("path") || parameters.get("path") == null) {
      throw new NullPointerException("Parameter missing: path parameters[\"path\"]");
    }
    String url = String.format("%s%s/locks/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), path);
    TypeReference<List<Lock>> typeReference = new TypeReference<List<Lock>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   path (required) - string - Path
  *   timeout - int64 - Lock timeout length
  */
  public static Lock create() throws IOException{
    return create(null, null,null);
  }
  public static Lock create(String path,  HashMap<String, Object> parameters) throws IOException {
    return create(path, parameters, null);
  }

  public static Lock create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return create(null, parameters, options);
  }

  public static Lock create(String path,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path != null){
      parameters.put("path",path);
    }
    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("timeout") && !(parameters.get("timeout") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: timeout must be of type Long parameters[\"timeout\"]");
    }

    if (!parameters.containsKey("path") || parameters.get("path") == null) {
      throw new NullPointerException("Parameter missing: path parameters[\"path\"]");
    }
    String url = String.format("%s%s/locks/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), path);
    TypeReference<Lock> typeReference = new TypeReference<Lock>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   token (required) - string - Lock token
  */
  public static Lock delete() throws IOException{
    return delete(null, null,null);
  }
  public static Lock delete(String path,  HashMap<String, Object> parameters) throws IOException {
    return delete(path, parameters, null);
  }

  public static Lock delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(null, parameters, options);
  }

  public static Lock delete(String path,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path != null){
      parameters.put("path",path);
    }
    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("token") && !(parameters.get("token") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: token must be of type String parameters[\"token\"]");
    }

    if (!parameters.containsKey("path") || parameters.get("path") == null) {
      throw new NullPointerException("Parameter missing: path parameters[\"path\"]");
    }
    if (!parameters.containsKey("token") || parameters.get("token") == null) {
      throw new NullPointerException("Parameter missing: token parameters[\"token\"]");
    }
    String url = String.format("%s%s/locks/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), path);
    TypeReference<Lock> typeReference = new TypeReference<Lock>() {};
    return FilesClient.requestItem(url, RequestMethods.DELETE, typeReference, parameters, options);
  }

  public static Lock destroy() throws IOException {
    return destroy(null, null, null);
  }

  public static Lock destroy(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(path, parameters, options);
  }

}


