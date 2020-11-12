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

public class UserRequest {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public UserRequest() {
    this(null, null);
  }

  public UserRequest(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public UserRequest(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * ID
  */
  @Getter
  @Setter
  @JsonProperty("id")
  private Long id;

  /**
  * User's full name
  */
  @Getter
  @Setter
  @JsonProperty("name")
  private String name;

  /**
  * User email address
  */
  @Getter
  @Setter
  @JsonProperty("email")
  private String email;

  /**
  * Details of the user's request
  */
  @Getter
  @Setter
  @JsonProperty("details")
  private String details;

  /**
  */
  public UserRequest delete(HashMap<String, Object> parameters) {
    return delete(parameters);
  }

  public void destroy(HashMap<String, Object> parameters) {
    delete(parameters);
  }


  /**
  * Parameters:
  *   cursor - string - Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  */
  public static List<UserRequest> list() throws IOException{
    return list(null,null);
  }
  public static List<UserRequest> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  public static List<UserRequest> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }

    String url = String.format("%s%s/user_requests", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<UserRequest>> typeReference = new TypeReference<List<UserRequest>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<UserRequest> all() throws IOException {
    return all(null, null);
  }

  public static List<UserRequest> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - User Request ID.
  */
  public static List<UserRequest> find() throws IOException{
    return find(null, null,null);
  }
  public static List<UserRequest> find(Long id,  HashMap<String, Object> parameters) throws IOException {
    return find(id, parameters, null);
  }

  public static List<UserRequest> find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(null, parameters, options);
  }

  public static List<UserRequest> find(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
    String url = String.format("%s%s/user_requests/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<List<UserRequest>> typeReference = new TypeReference<List<UserRequest>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<UserRequest> get() throws IOException {
    return get(null, null, null);
  }

  public static List<UserRequest> get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   name (required) - string - Name of user requested
  *   email (required) - string - Email of user requested
  *   details (required) - string - Details of the user request
  */
  public static UserRequest create() throws IOException{
    return create(null,null);
  }
  public static UserRequest create( HashMap<String, Object> parameters) throws IOException {
    return create(parameters, null);
  }


  public static UserRequest create( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("email") && !(parameters.get("email") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: email must be of type String parameters[\"email\"]");
    }

    if (parameters.containsKey("details") && !(parameters.get("details") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: details must be of type String parameters[\"details\"]");
    }

    if (!parameters.containsKey("name") || parameters.get("name") == null) {
      throw new NullPointerException("Parameter missing: name parameters[\"name\"]");
    }
    if (!parameters.containsKey("email") || parameters.get("email") == null) {
      throw new NullPointerException("Parameter missing: email parameters[\"email\"]");
    }
    if (!parameters.containsKey("details") || parameters.get("details") == null) {
      throw new NullPointerException("Parameter missing: details parameters[\"details\"]");
    }
    String url = String.format("%s%s/user_requests", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<UserRequest> typeReference = new TypeReference<UserRequest>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  */
  public static UserRequest delete() throws IOException{
    return delete(null, null,null);
  }
  public static UserRequest delete(Long id,  HashMap<String, Object> parameters) throws IOException {
    return delete(id, parameters, null);
  }

  public static UserRequest delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(null, parameters, options);
  }

  public static UserRequest delete(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
    String url = String.format("%s%s/user_requests/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<UserRequest> typeReference = new TypeReference<UserRequest>() {};
    return FilesClient.requestItem(url, RequestMethods.DELETE, typeReference, parameters, options);
  }

  public static UserRequest destroy() throws IOException {
    return destroy(null, null, null);
  }

  public static UserRequest destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(id, parameters, options);
  }

}


