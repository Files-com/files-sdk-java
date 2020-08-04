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

public class Group {
  private HashMap<String, Object> attributes;
  private HashMap<String, Object> options;

  public Group(HashMap<String, Object> attributes, HashMap<String, Object> options) {
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
  * Group ID
  */
  @Getter
  @Setter
  @JsonProperty("id")
  public Long id;

  /**
  * Group name
  */
  @Getter
  @Setter
  @JsonProperty("name")
  public String name;

  /**
  * List of user IDs who are group administrators (separated by commas)
  */
  @Getter
  @Setter
  @JsonProperty("admin_ids")
  public Object[] adminIds;

  /**
  * Notes about this group
  */
  @Getter
  @Setter
  @JsonProperty("notes")
  public String notes;

  /**
  * List of user IDs who belong to this group (separated by commas)
  */
  @Getter
  @Setter
  @JsonProperty("user_ids")
  public Object[] userIds;

  /**
  * List of usernames who belong to this group (separated by commas)
  */
  @Getter
  @Setter
  @JsonProperty("usernames")
  public Object[] usernames;

  /**
  * Parameters:
  *   name - string - Group name.
  *   notes - string - Group notes.
  *   user_ids - string - A list of user ids. If sent as a string, should be comma-delimited.
  *   admin_ids - string - A list of group admin user ids. If sent as a string, should be comma-delimited.
  */
  public Group update(HashMap<String, Object> parameters) {
    // TODO: Fill in operation implementation
    return (Group) null;
  }

  /**
  */
  public Group delete(HashMap<String, Object> parameters) {
    // TODO: Fill in operation implementation
    return (Group) null;
  }

  public void destroy(HashMap<String, Object> parameters) {
    delete(parameters);
  }

  public void save() {
    if (this.attributes.get("id") != null) {
      update(this.attributes);
    } else {
      Group newObj = Group.create(this.attributes, this.options);
      this.attributes = newObj.attributes;
    }
  }

  /**
  * Parameters:
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  *   cursor - string - Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   sort_by - object - If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `active`, `site_id` or `name`.
  *   filter - object - If set, return records where the specifiied field is equal to the supplied value. Valid fields are `name`.
  *   filter_gt - object - If set, return records where the specifiied field is greater than the supplied value. Valid fields are `name`.
  *   filter_gteq - object - If set, return records where the specifiied field is greater than or equal to the supplied value. Valid fields are `name`.
  *   filter_like - object - If set, return records where the specifiied field is equal to the supplied value. Valid fields are `name`.
  *   filter_lt - object - If set, return records where the specifiied field is less than the supplied value. Valid fields are `name`.
  *   filter_lteq - object - If set, return records where the specifiied field is less than or equal to the supplied value. Valid fields are `name`.
  *   ids - string - Comma-separated list of group ids to include in results.
  */
  public static Group list( HashMap<String, Object> parameters) {
    return list(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static Group list( HashMap<String, Object> parameters, HashMap<String, Object> options) {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

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

    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Object parameters[\"filter\"]");
    }

    if (parameters.containsKey("filter_gt") && !(parameters.get("filter_gt") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: filter_gt must be of type Object parameters[\"filter_gt\"]");
    }

    if (parameters.containsKey("filter_gteq") && !(parameters.get("filter_gteq") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: filter_gteq must be of type Object parameters[\"filter_gteq\"]");
    }

    if (parameters.containsKey("filter_like") && !(parameters.get("filter_like") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: filter_like must be of type Object parameters[\"filter_like\"]");
    }

    if (parameters.containsKey("filter_lt") && !(parameters.get("filter_lt") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: filter_lt must be of type Object parameters[\"filter_lt\"]");
    }

    if (parameters.containsKey("filter_lteq") && !(parameters.get("filter_lteq") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: filter_lteq must be of type Object parameters[\"filter_lteq\"]");
    }

    if (parameters.containsKey("ids") && !(parameters.get("ids") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: ids must be of type String parameters[\"ids\"]");
    }

    // TODO: Send request
    return (Group) null;
  }

  public static Group all(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Group ID.
  */
  public static Group find(Long id,  HashMap<String, Object> parameters) {
    return find(id, parameters, null);
  }

  public static Group find(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return find(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static Group find(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) {
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
    return (Group) null;
  }

  public static Group get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   name - string - Group name.
  *   notes - string - Group notes.
  *   user_ids - string - A list of user ids. If sent as a string, should be comma-delimited.
  *   admin_ids - string - A list of group admin user ids. If sent as a string, should be comma-delimited.
  */
  public static Group create( HashMap<String, Object> parameters) {
    return create(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static Group create( HashMap<String, Object> parameters, HashMap<String, Object> options) {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("notes") && !(parameters.get("notes") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: notes must be of type String parameters[\"notes\"]");
    }

    if (parameters.containsKey("user_ids") && !(parameters.get("user_ids") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: user_ids must be of type String parameters[\"user_ids\"]");
    }

    if (parameters.containsKey("admin_ids") && !(parameters.get("admin_ids") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: admin_ids must be of type String parameters[\"admin_ids\"]");
    }

    // TODO: Send request
    return (Group) null;
  }


  /**
  * Parameters:
  *   name - string - Group name.
  *   notes - string - Group notes.
  *   user_ids - string - A list of user ids. If sent as a string, should be comma-delimited.
  *   admin_ids - string - A list of group admin user ids. If sent as a string, should be comma-delimited.
  */
  public static Group update(Long id,  HashMap<String, Object> parameters) {
    return update(id, parameters, null);
  }

  public static Group update(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return update(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static Group update(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id != null){
      parameters.put("id",id);
    }
    if (parameters.containsKey("id") && !(parameters.get("id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("notes") && !(parameters.get("notes") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: notes must be of type String parameters[\"notes\"]");
    }

    if (parameters.containsKey("user_ids") && !(parameters.get("user_ids") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: user_ids must be of type String parameters[\"user_ids\"]");
    }

    if (parameters.containsKey("admin_ids") && !(parameters.get("admin_ids") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: admin_ids must be of type String parameters[\"admin_ids\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    // TODO: Send request
    return (Group) null;
  }


  /**
  */
  public static Group delete(Long id,  HashMap<String, Object> parameters) {
    return delete(id, parameters, null);
  }

  public static Group delete(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return delete(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static Group delete(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) {
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
    return (Group) null;
  }

  public static Group destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return delete(id, parameters, options);
  }

}


