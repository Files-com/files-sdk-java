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

public class Message {
  private HashMap<String, Object> attributes;
  private HashMap<String, Object> options;

  public Message(HashMap<String, Object> attributes, HashMap<String, Object> options) {
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

  public void save() {
    if (this.attributes.get("id") != null) {
      update(this.attributes);
    } else {
      Message newObj = Message.create(this.attributes, this.options);
      this.attributes = newObj.attributes;
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
  public static Message list( HashMap<String, Object> parameters) {
    return list(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static Message list( HashMap<String, Object> parameters, HashMap<String, Object> options) {
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
    // TODO: Send request
    return (Message) null;
  }

  public static Message all(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Message ID.
  */
  public static Message find(Long id,  HashMap<String, Object> parameters) {
    return find(id, parameters, null);
  }

  public static Message find(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return find(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static Message find(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) {
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
    return (Message) null;
  }

  public static Message get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   project_id (required) - int64 - Project to which the message should be attached.
  *   subject (required) - string - Message subject.
  *   body (required) - string - Message body.
  */
  public static Message create( HashMap<String, Object> parameters) {
    return create(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static Message create( HashMap<String, Object> parameters, HashMap<String, Object> options) {
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
    // TODO: Send request
    return (Message) null;
  }


  /**
  * Parameters:
  *   project_id (required) - int64 - Project to which the message should be attached.
  *   subject (required) - string - Message subject.
  *   body (required) - string - Message body.
  */
  public static Message update(Long id,  HashMap<String, Object> parameters) {
    return update(id, parameters, null);
  }

  public static Message update(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return update(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static Message update(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) {
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
    // TODO: Send request
    return (Message) null;
  }


  /**
  */
  public static Message delete(Long id,  HashMap<String, Object> parameters) {
    return delete(id, parameters, null);
  }

  public static Message delete(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return delete(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static Message delete(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) {
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
    return (Message) null;
  }

  public static Message destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return delete(id, parameters, options);
  }

}


