package com.files.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.net.HttpMethods.RequestMethods;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class Message {
  private HashMap<String, Object> attributes;
  private HashMap<String, Object> options;

  public Message() {
    this(null, null);
  }

  public Message(HashMap<String, Object> attributes) {
    this(attributes, null);
  }

  public Message(HashMap<String, Object> attributes, HashMap<String, Object> options) {
    this.attributes = attributes;
    this.options = options;
    try{
      ObjectMapper objectMapper = new ObjectMapper();
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(attributes));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Message ID
  */
  @Getter
  @Setter
  @JsonProperty("id")
  public Long id;

  /**
  * Message subject.
  */
  @Getter
  @Setter
  @JsonProperty("subject")
  public String subject;

  /**
  * Message body.
  */
  @Getter
  @Setter
  @JsonProperty("body")
  public String body;

  /**
  * Comments.
  */
  @Getter
  @Setter
  @JsonProperty("comments")
  public Object[] comments;

  /**
  * User ID.  Provide a value of `0` to operate the current session's user.
  */
  @Getter
  @Setter
  @JsonProperty("user_id")
  public Long userId;

  /**
  * Project to which the message should be attached.
  */
  @Getter
  @Setter
  @JsonProperty("project_id")
  public Long projectId;

  /**
  * Parameters:
  *   project_id (required) - int64 - Project to which the message should be attached.
  *   subject (required) - string - Message subject.
  *   body (required) - string - Message body.
  */
  public Message update(HashMap<String, Object> parameters) {
    // TODO: Fill in operation implementation
    return (Message) null;
  }

  /**
  */
  public Message delete(HashMap<String, Object> parameters) {
    // TODO: Fill in operation implementation
    return (Message) null;
  }

  public void destroy(HashMap<String, Object> parameters) {
    delete(parameters);
  }

  public void save() throws IOException {
    if (this.attributes.get("id") != null) {
      update(this.attributes);
    } else {
      Message.create(this.attributes, this.options);
      // TODO save this.attributes = newObj.attributes;
    }
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  *   project_id (required) - int64 - Project for which to return messages.
  */
  public static List<Message> list() throws IOException{
    return list(null,null);
  }
  public static List<Message> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static List<Message> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
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

    if (parameters.containsKey("project_id") && !(parameters.get("project_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: project_id must be of type Long parameters[\"project_id\"]");
    }

    if (!parameters.containsKey("project_id") || parameters.get("project_id") == null) {
      throw new NullPointerException("Parameter missing: project_id parameters[\"project_id\"]");
    }
    String url = String.format("%s%s/messages", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<Message>> typeReference = new TypeReference<List<Message>>() {};
    return FilesClient.request(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<Message> all() throws IOException {
    return all(null, null);
  }

  public static List<Message> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Message ID.
  */
  public static List<Message> find() throws IOException{
    return find(null, null,null);
  }
  public static List<Message> find(Long id,  HashMap<String, Object> parameters) throws IOException {
    return find(id, parameters, null);
  }

  public static List<Message> find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static List<Message> find(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
    String url = String.format("%s%s/messages/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<List<Message>> typeReference = new TypeReference<List<Message>>() {};
    return FilesClient.request(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<Message> get() throws IOException {
    return get(null, null, null);
  }

  public static List<Message> get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   project_id (required) - int64 - Project to which the message should be attached.
  *   subject (required) - string - Message subject.
  *   body (required) - string - Message body.
  */
  public static List<Message> create() throws IOException{
    return create(null,null);
  }
  public static List<Message> create( HashMap<String, Object> parameters) throws IOException {
    return create(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static List<Message> create( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }

    if (parameters.containsKey("project_id") && !(parameters.get("project_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: project_id must be of type Long parameters[\"project_id\"]");
    }

    if (parameters.containsKey("subject") && !(parameters.get("subject") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: subject must be of type String parameters[\"subject\"]");
    }

    if (parameters.containsKey("body") && !(parameters.get("body") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: body must be of type String parameters[\"body\"]");
    }

    if (!parameters.containsKey("project_id") || parameters.get("project_id") == null) {
      throw new NullPointerException("Parameter missing: project_id parameters[\"project_id\"]");
    }
    if (!parameters.containsKey("subject") || parameters.get("subject") == null) {
      throw new NullPointerException("Parameter missing: subject parameters[\"subject\"]");
    }
    if (!parameters.containsKey("body") || parameters.get("body") == null) {
      throw new NullPointerException("Parameter missing: body parameters[\"body\"]");
    }
    String url = String.format("%s%s/messages", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<Message>> typeReference = new TypeReference<List<Message>>() {};
    return FilesClient.request(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   project_id (required) - int64 - Project to which the message should be attached.
  *   subject (required) - string - Message subject.
  *   body (required) - string - Message body.
  */
  public static List<Message> update() throws IOException{
    return update(null, null,null);
  }
  public static List<Message> update(Long id,  HashMap<String, Object> parameters) throws IOException {
    return update(id, parameters, null);
  }

  public static List<Message> update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return update(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static List<Message> update(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id != null){
      parameters.put("id",id);
    }
    if (parameters.containsKey("id") && !(parameters.get("id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (parameters.containsKey("project_id") && !(parameters.get("project_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: project_id must be of type Long parameters[\"project_id\"]");
    }

    if (parameters.containsKey("subject") && !(parameters.get("subject") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: subject must be of type String parameters[\"subject\"]");
    }

    if (parameters.containsKey("body") && !(parameters.get("body") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: body must be of type String parameters[\"body\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    if (!parameters.containsKey("project_id") || parameters.get("project_id") == null) {
      throw new NullPointerException("Parameter missing: project_id parameters[\"project_id\"]");
    }
    if (!parameters.containsKey("subject") || parameters.get("subject") == null) {
      throw new NullPointerException("Parameter missing: subject parameters[\"subject\"]");
    }
    if (!parameters.containsKey("body") || parameters.get("body") == null) {
      throw new NullPointerException("Parameter missing: body parameters[\"body\"]");
    }
    String url = String.format("%s%s/messages/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<List<Message>> typeReference = new TypeReference<List<Message>>() {};
    return FilesClient.request(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  */
  public static List<Message> delete() throws IOException{
    return delete(null, null,null);
  }
  public static List<Message> delete(Long id,  HashMap<String, Object> parameters) throws IOException {
    return delete(id, parameters, null);
  }

  public static List<Message> delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static List<Message> delete(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
    String url = String.format("%s%s/messages/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<List<Message>> typeReference = new TypeReference<List<Message>>() {};
    return FilesClient.request(url, RequestMethods.DELETE, typeReference, parameters, options);
  }

  public static List<Message> destroy() throws IOException {
    return destroy(null, null, null);
  }

  public static List<Message> destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(id, parameters, options);
  }

}


