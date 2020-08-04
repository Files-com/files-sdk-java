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

public class Automation {
  private HashMap<String, Object> attributes;
  private HashMap<String, Object> options;

  public Automation(HashMap<String, Object> attributes, HashMap<String, Object> options) {
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
  * Automation ID
  */
  @Getter
  @Setter
  @JsonProperty("id")
  public Long id;

  /**
  * Automation type
  */
  @Getter
  @Setter
  @JsonProperty("automation")
  public String automation;

  /**
  * Source Path
  */
  @Getter
  @Setter
  @JsonProperty("source")
  public String source;

  /**
  * Destination Path
  */
  @Getter
  @Setter
  @JsonProperty("destination")
  public String destination;

  /**
  * If set, this string in the destination path will be replaced with the value in `destination_replace_to`.
  */
  @Getter
  @Setter
  @JsonProperty("destination_replace_from")
  public String destinationReplaceFrom;

  /**
  * If set, this string will replace the value `destination_replace_from` in the destination filename. You can use special patterns here.
  */
  @Getter
  @Setter
  @JsonProperty("destination_replace_to")
  public String destinationReplaceTo;

  /**
  * How often to run this automation?  One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
  */
  @Getter
  @Setter
  @JsonProperty("interval")
  public String interval;

  /**
  * Date this automation will next run.
  */
  @Getter
  @Setter
  @JsonProperty("next_process_on")
  public String nextProcessOn;

  /**
  * Path on which this Automation runs.  Supports globs. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @Getter
  @Setter
  @JsonProperty("path")
  public String path;

  /**
  * Does this automation run in real time?  This is a read-only property based on automation type.
  */
  @Getter
  @Setter
  @JsonProperty("realtime")
  public Boolean realtime;

  /**
  * User ID of the Automation's creator.
  */
  @Getter
  @Setter
  @JsonProperty("user_id")
  public Long userId;

  /**
  * IDs of Users for the Automation (i.e. who to Request File from)
  */
  @Getter
  @Setter
  @JsonProperty("user_ids")
  public Object[] userIds;

  /**
  * IDs of Groups for the Automation (i.e. who to Request File from)
  */
  @Getter
  @Setter
  @JsonProperty("group_ids")
  public Object[] groupIds;

  /**
  * Parameters:
  *   automation (required) - string - Type of automation.  One of: `create_folder`, `request_file`, `request_move`
  *   source - string - Source Path
  *   destination - string - Destination Path
  *   destination_replace_from - string - If set, this string in the destination path will be replaced with the value in `destination_replace_to`.
  *   destination_replace_to - string - If set, this string will replace the value `destination_replace_from` in the destination filename. You can use special patterns here.
  *   interval - string - How often to run this automation? One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
  *   path - string - Path on which this Automation runs.  Supports globs.
  *   user_ids - string - A list of user IDs the automation is associated with. If sent as a string, it should be comma-delimited.
  *   group_ids - string - A list of group IDs the automation is associated with. If sent as a string, it should be comma-delimited.
  */
  public Automation update(HashMap<String, Object> parameters) {
    // TODO: Fill in operation implementation
    return (Automation) null;
  }

  /**
  */
  public Automation delete(HashMap<String, Object> parameters) {
    // TODO: Fill in operation implementation
    return (Automation) null;
  }

  public void destroy(HashMap<String, Object> parameters) {
    delete(parameters);
  }

  public void save() {
    if (this.attributes.get("id") != null) {
      update(this.attributes);
    } else {
      Automation newObj = Automation.create(this.attributes, this.options);
      this.attributes = newObj.attributes;
    }
  }

  /**
  * Parameters:
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  *   cursor - string - Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   sort_by - object - If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `site_id` and `automation`.
  *   filter - object - If set, return records where the specifiied field is equal to the supplied value. Valid fields are `automation`.
  *   filter_gt - object - If set, return records where the specifiied field is greater than the supplied value. Valid fields are `automation`.
  *   filter_gteq - object - If set, return records where the specifiied field is greater than or equal to the supplied value. Valid fields are `automation`.
  *   filter_like - object - If set, return records where the specifiied field is equal to the supplied value. Valid fields are `automation`.
  *   filter_lt - object - If set, return records where the specifiied field is less than the supplied value. Valid fields are `automation`.
  *   filter_lteq - object - If set, return records where the specifiied field is less than or equal to the supplied value. Valid fields are `automation`.
  *   automation - string - DEPRECATED: Type of automation to filter by. Use `filter[automation]` instead.
  */
  public static Automation list( HashMap<String, Object> parameters) {
    return list(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static Automation list( HashMap<String, Object> parameters, HashMap<String, Object> options) {
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

    if (parameters.containsKey("automation") && !(parameters.get("automation") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: automation must be of type String parameters[\"automation\"]");
    }

    // TODO: Send request
    return (Automation) null;
  }

  public static Automation all(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Automation ID.
  */
  public static Automation find(Long id,  HashMap<String, Object> parameters) {
    return find(id, parameters, null);
  }

  public static Automation find(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return find(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static Automation find(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) {
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
    return (Automation) null;
  }

  public static Automation get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   automation (required) - string - Type of automation.  One of: `create_folder`, `request_file`, `request_move`
  *   source - string - Source Path
  *   destination - string - Destination Path
  *   destination_replace_from - string - If set, this string in the destination path will be replaced with the value in `destination_replace_to`.
  *   destination_replace_to - string - If set, this string will replace the value `destination_replace_from` in the destination filename. You can use special patterns here.
  *   interval - string - How often to run this automation? One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
  *   path - string - Path on which this Automation runs.  Supports globs.
  *   user_ids - string - A list of user IDs the automation is associated with. If sent as a string, it should be comma-delimited.
  *   group_ids - string - A list of group IDs the automation is associated with. If sent as a string, it should be comma-delimited.
  */
  public static Automation create( HashMap<String, Object> parameters) {
    return create(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static Automation create( HashMap<String, Object> parameters, HashMap<String, Object> options) {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("automation") && !(parameters.get("automation") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: automation must be of type String parameters[\"automation\"]");
    }

    if (parameters.containsKey("source") && !(parameters.get("source") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: source must be of type String parameters[\"source\"]");
    }

    if (parameters.containsKey("destination") && !(parameters.get("destination") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: destination must be of type String parameters[\"destination\"]");
    }

    if (parameters.containsKey("destination_replace_from") && !(parameters.get("destination_replace_from") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: destination_replace_from must be of type String parameters[\"destination_replace_from\"]");
    }

    if (parameters.containsKey("destination_replace_to") && !(parameters.get("destination_replace_to") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: destination_replace_to must be of type String parameters[\"destination_replace_to\"]");
    }

    if (parameters.containsKey("interval") && !(parameters.get("interval") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: interval must be of type String parameters[\"interval\"]");
    }

    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("user_ids") && !(parameters.get("user_ids") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: user_ids must be of type String parameters[\"user_ids\"]");
    }

    if (parameters.containsKey("group_ids") && !(parameters.get("group_ids") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: group_ids must be of type String parameters[\"group_ids\"]");
    }

    if (!parameters.containsKey("automation") || parameters.get("automation") == null) {
      throw new NullPointerException("Parameter missing: automation parameters[\"automation\"]");
    }
    // TODO: Send request
    return (Automation) null;
  }


  /**
  * Parameters:
  *   automation (required) - string - Type of automation.  One of: `create_folder`, `request_file`, `request_move`
  *   source - string - Source Path
  *   destination - string - Destination Path
  *   destination_replace_from - string - If set, this string in the destination path will be replaced with the value in `destination_replace_to`.
  *   destination_replace_to - string - If set, this string will replace the value `destination_replace_from` in the destination filename. You can use special patterns here.
  *   interval - string - How often to run this automation? One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
  *   path - string - Path on which this Automation runs.  Supports globs.
  *   user_ids - string - A list of user IDs the automation is associated with. If sent as a string, it should be comma-delimited.
  *   group_ids - string - A list of group IDs the automation is associated with. If sent as a string, it should be comma-delimited.
  */
  public static Automation update(Long id,  HashMap<String, Object> parameters) {
    return update(id, parameters, null);
  }

  public static Automation update(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return update(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static Automation update(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id != null){
      parameters.put("id",id);
    }
    if (parameters.containsKey("id") && !(parameters.get("id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (parameters.containsKey("automation") && !(parameters.get("automation") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: automation must be of type String parameters[\"automation\"]");
    }

    if (parameters.containsKey("source") && !(parameters.get("source") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: source must be of type String parameters[\"source\"]");
    }

    if (parameters.containsKey("destination") && !(parameters.get("destination") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: destination must be of type String parameters[\"destination\"]");
    }

    if (parameters.containsKey("destination_replace_from") && !(parameters.get("destination_replace_from") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: destination_replace_from must be of type String parameters[\"destination_replace_from\"]");
    }

    if (parameters.containsKey("destination_replace_to") && !(parameters.get("destination_replace_to") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: destination_replace_to must be of type String parameters[\"destination_replace_to\"]");
    }

    if (parameters.containsKey("interval") && !(parameters.get("interval") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: interval must be of type String parameters[\"interval\"]");
    }

    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("user_ids") && !(parameters.get("user_ids") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: user_ids must be of type String parameters[\"user_ids\"]");
    }

    if (parameters.containsKey("group_ids") && !(parameters.get("group_ids") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: group_ids must be of type String parameters[\"group_ids\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    if (!parameters.containsKey("automation") || parameters.get("automation") == null) {
      throw new NullPointerException("Parameter missing: automation parameters[\"automation\"]");
    }
    // TODO: Send request
    return (Automation) null;
  }


  /**
  */
  public static Automation delete(Long id,  HashMap<String, Object> parameters) {
    return delete(id, parameters, null);
  }

  public static Automation delete(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return delete(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static Automation delete(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) {
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
    return (Automation) null;
  }

  public static Automation destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return delete(id, parameters, options);
  }

}


