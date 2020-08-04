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

public class App {
  private HashMap<String, Object> attributes;
  private HashMap<String, Object> options;

  public App(HashMap<String, Object> attributes, HashMap<String, Object> options) {
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
  * Name of the App
  */
  @Getter
  @JsonProperty("name")
  public String name;

  /**
  * Long form description of the App
  */
  @Getter
  @JsonProperty("extended_description")
  public String extendedDescription;

  /**
  * Collection of named links to documentation
  */
  @Getter
  @JsonProperty("documentation_links")
  public String documentationLinks;

  /**
  * App icon
  */
  @Getter
  @JsonProperty("icon_url")
  public String iconUrl;

  /**
  * Full size logo for the App
  */
  @Getter
  @JsonProperty("logo_url")
  public String logoUrl;

  /**
  * Logo thumbnail for the App
  */
  @Getter
  @JsonProperty("logo_thumbnail_url")
  public String logoThumbnailUrl;

  /**
  * Associated SSO Strategy type, if any
  */
  @Getter
  @JsonProperty("sso_strategy_type")
  public String ssoStrategyType;

  /**
  * Associated Remote Server type, if any
  */
  @Getter
  @JsonProperty("remote_server_type")
  public String remoteServerType;

  /**
  * Associated Folder Behavior type, if any
  */
  @Getter
  @JsonProperty("folder_behavior_type")
  public String folderBehaviorType;

  /**
  * Link to external homepage
  */
  @Getter
  @JsonProperty("external_homepage_url")
  public String externalHomepageUrl;

  /**
  * The type of the App
  */
  @Getter
  @JsonProperty("app_type")
  public String appType;

  /**
  * Is featured on the App listing?
  */
  @Getter
  @JsonProperty("featured")
  public Boolean featured;



  /**
  * Parameters:
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  *   cursor - string - Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   sort_by - object - If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `name` and `app_type`.
  *   filter - object - If set, return records where the specifiied field is equal to the supplied value. Valid fields are `name` and `app_type`.
  *   filter_gt - object - If set, return records where the specifiied field is greater than the supplied value. Valid fields are `name` and `app_type`.
  *   filter_gteq - object - If set, return records where the specifiied field is greater than or equal to the supplied value. Valid fields are `name` and `app_type`.
  *   filter_like - object - If set, return records where the specifiied field is equal to the supplied value. Valid fields are `name` and `app_type`.
  *   filter_lt - object - If set, return records where the specifiied field is less than the supplied value. Valid fields are `name` and `app_type`.
  *   filter_lteq - object - If set, return records where the specifiied field is less than or equal to the supplied value. Valid fields are `name` and `app_type`.
  */
  public static App list( HashMap<String, Object> parameters) {
    return list(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static App list( HashMap<String, Object> parameters, HashMap<String, Object> options) {
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

    // TODO: Send request
    return (App) null;
  }

  public static App all(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return list(parameters, options);
  }

}


