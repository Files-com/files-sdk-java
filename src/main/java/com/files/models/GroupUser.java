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

public class GroupUser {
  private HashMap<String, Object> attributes;
  private HashMap<String, Object> options;

  public GroupUser(HashMap<String, Object> attributes, HashMap<String, Object> options) {
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
  * Group name
  */
  @Getter
  @Setter
  @JsonProperty("group_name")
  public String groupName;

  /**
  * Group ID
  */
  @Getter
  @Setter
  @JsonProperty("group_id")
  public Long groupId;

  /**
  * User ID
  */
  @Getter
  @Setter
  @JsonProperty("user_id")
  public Long userId;

  /**
  * Is this user an administrator of this group?
  */
  @Getter
  @Setter
  @JsonProperty("admin")
  public Boolean admin;

  /**
  * A list of usernames for users in this group
  */
  @Getter
  @Setter
  @JsonProperty("usernames")
  public Object[] usernames;

  /**
  * Group User ID.
  */
  @Getter
  @Setter
  @JsonProperty("id")
  public Long id;

  /**
  * Parameters:
  *   group_id (required) - int64 - Group ID to add user to.
  *   user_id (required) - int64 - User ID to add to group.
  *   admin - boolean - Is the user a group administrator?
  */
  public GroupUser update(HashMap<String, Object> parameters) {
    // TODO: Fill in operation implementation
    return (GroupUser) null;
  }

  /**
  * Parameters:
  *   group_id (required) - int64 - Group ID from which to remove user.
  *   user_id (required) - int64 - User ID to remove from group.
  */
  public GroupUser delete(HashMap<String, Object> parameters) {
    // TODO: Fill in operation implementation
    return (GroupUser) null;
  }

  public void destroy(HashMap<String, Object> parameters) {
    delete(parameters);
  }

  public void save() {
    update(this.attributes);
  }


  /**
  * Parameters:
  *   user_id - int64 - User ID.  If provided, will return group_users of this user.
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  *   group_id - int64 - Group ID.  If provided, will return group_users of this group.
  */
  public static GroupUser list( HashMap<String, Object> parameters) {
    return list(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static GroupUser list( HashMap<String, Object> parameters, HashMap<String, Object> options) {
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

    if (parameters.containsKey("group_id") && !(parameters.get("group_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: group_id must be of type Long parameters[\"group_id\"]");
    }

    // TODO: Send request
    return (GroupUser) null;
  }

  public static GroupUser all(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   group_id (required) - int64 - Group ID to add user to.
  *   user_id (required) - int64 - User ID to add to group.
  *   admin - boolean - Is the user a group administrator?
  */
  public static GroupUser update(Long id,  HashMap<String, Object> parameters) {
    return update(id, parameters, null);
  }

  public static GroupUser update(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return update(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static GroupUser update(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id != null){
      parameters.put("id",id);
    }
    if (parameters.containsKey("id") && !(parameters.get("id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (parameters.containsKey("group_id") && !(parameters.get("group_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: group_id must be of type Long parameters[\"group_id\"]");
    }

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }

    if (parameters.containsKey("admin") && !(parameters.get("admin") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: admin must be of type Boolean parameters[\"admin\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    if (!parameters.containsKey("group_id") || parameters.get("group_id") == null) {
      throw new NullPointerException("Parameter missing: group_id parameters[\"group_id\"]");
    }
    if (!parameters.containsKey("user_id") || parameters.get("user_id") == null) {
      throw new NullPointerException("Parameter missing: user_id parameters[\"user_id\"]");
    }
    // TODO: Send request
    return (GroupUser) null;
  }


  /**
  * Parameters:
  *   group_id (required) - int64 - Group ID from which to remove user.
  *   user_id (required) - int64 - User ID to remove from group.
  */
  public static GroupUser delete(Long id,  HashMap<String, Object> parameters) {
    return delete(id, parameters, null);
  }

  public static GroupUser delete(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return delete(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static GroupUser delete(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id != null){
      parameters.put("id",id);
    }
    if (parameters.containsKey("id") && !(parameters.get("id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (parameters.containsKey("group_id") && !(parameters.get("group_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: group_id must be of type Long parameters[\"group_id\"]");
    }

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    if (!parameters.containsKey("group_id") || parameters.get("group_id") == null) {
      throw new NullPointerException("Parameter missing: group_id parameters[\"group_id\"]");
    }
    if (!parameters.containsKey("user_id") || parameters.get("user_id") == null) {
      throw new NullPointerException("Parameter missing: user_id parameters[\"user_id\"]");
    }
    // TODO: Send request
    return (GroupUser) null;
  }

  public static GroupUser destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return delete(id, parameters, options);
  }

}


