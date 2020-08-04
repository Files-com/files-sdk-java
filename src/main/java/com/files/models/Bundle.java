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

public class Bundle {
  private HashMap<String, Object> attributes;
  private HashMap<String, Object> options;

  public Bundle(HashMap<String, Object> attributes, HashMap<String, Object> options) {
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
  * Bundle code.  This code forms the end part of the Public URL.
  */
  @Getter
  @Setter
  @JsonProperty("code")
  public String code;

  /**
  * Public URL of Share Link
  */
  @Getter
  @Setter
  @JsonProperty("url")
  public String url;

  /**
  * Public description
  */
  @Getter
  @Setter
  @JsonProperty("description")
  public String description;

  /**
  * Is this bundle password protected?
  */
  @Getter
  @Setter
  @JsonProperty("password_protected")
  public Boolean passwordProtected;

  /**
  * Show a registration page that captures the downloader's name and email address?
  */
  @Getter
  @Setter
  @JsonProperty("require_registration")
  public Boolean requireRegistration;

  /**
  * Only allow access to recipients who have explicitly received the share via an email sent through the Files.com UI?
  */
  @Getter
  @Setter
  @JsonProperty("require_share_recipient")
  public Boolean requireShareRecipient;

  /**
  * Legal text that must be agreed to prior to accessing Bundle.
  */
  @Getter
  @Setter
  @JsonProperty("clickwrap_body")
  public String clickwrapBody;

  /**
  * Bundle ID
  */
  @Getter
  @Setter
  @JsonProperty("id")
  public Long id;

  /**
  * Bundle created at date/time
  */
  @Getter
  @JsonProperty("created_at")
  public Date createdAt;

  /**
  * Bundle expiration date/time
  */
  @Getter
  @Setter
  @JsonProperty("expires_at")
  public Date expiresAt;

  /**
  * Maximum number of times bundle can be accessed
  */
  @Getter
  @Setter
  @JsonProperty("max_uses")
  public Long maxUses;

  /**
  * Bundle internal note
  */
  @Getter
  @Setter
  @JsonProperty("note")
  public String note;

  /**
  * Bundle creator user ID
  */
  @Getter
  @Setter
  @JsonProperty("user_id")
  public Long userId;

  /**
  * Bundle creator username
  */
  @Getter
  @Setter
  @JsonProperty("username")
  public String username;

  /**
  * ID of the clickwrap to use with this bundle.
  */
  @Getter
  @Setter
  @JsonProperty("clickwrap_id")
  public Long clickwrapId;

  /**
  * ID of the associated inbox, if available.
  */
  @Getter
  @Setter
  @JsonProperty("inbox_id")
  public Long inboxId;

  /**
  * A list of paths in this bundle
  */
  @Getter
  @Setter
  @JsonProperty("paths")
  public Object[] paths;

  /**
  * Password for this bundle.
  */
  @Getter
  @Setter
  @JsonProperty("password")
  public String password;

  /**
  * Send email(s) with a link to bundle
  *
  * Parameters:
  *   to (required) - array(string) - A list of email addresses to share this bundle with.
  *   note - string - Note to include in email.
  */
  public Bundle share(HashMap<String, Object> parameters) {
    // TODO: Fill in operation implementation
    return (Bundle) null;
  }

  /**
  * Parameters:
  *   password - string - Password for this bundle.
  *   clickwrap_id - int64 - ID of the clickwrap to use with this bundle.
  *   code - string - Bundle code.  This code forms the end part of the Public URL.
  *   description - string - Public description
  *   expires_at - string - Bundle expiration date/time
  *   inbox_id - int64 - ID of the associated inbox, if available.
  *   max_uses - int64 - Maximum number of times bundle can be accessed
  *   note - string - Bundle internal note
  *   require_registration - boolean - Show a registration page that captures the downloader's name and email address?
  *   require_share_recipient - boolean - Only allow access to recipients who have explicitly received the share via an email sent through the Files.com UI?
  */
  public Bundle update(HashMap<String, Object> parameters) {
    // TODO: Fill in operation implementation
    return (Bundle) null;
  }

  /**
  */
  public Bundle delete(HashMap<String, Object> parameters) {
    // TODO: Fill in operation implementation
    return (Bundle) null;
  }

  public void destroy(HashMap<String, Object> parameters) {
    delete(parameters);
  }

  public void save() {
    if (this.attributes.get("id") != null) {
      update(this.attributes);
    } else {
      Bundle newObj = Bundle.create(this.attributes, this.options);
      this.attributes = newObj.attributes;
    }
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  *   cursor - string - Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   sort_by - object - If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `site_id`, `created_at` or `code`.
  *   filter - object - If set, return records where the specifiied field is equal to the supplied value. Valid fields are `created_at`.
  *   filter_gt - object - If set, return records where the specifiied field is greater than the supplied value. Valid fields are `created_at`.
  *   filter_gteq - object - If set, return records where the specifiied field is greater than or equal to the supplied value. Valid fields are `created_at`.
  *   filter_like - object - If set, return records where the specifiied field is equal to the supplied value. Valid fields are `created_at`.
  *   filter_lt - object - If set, return records where the specifiied field is less than the supplied value. Valid fields are `created_at`.
  *   filter_lteq - object - If set, return records where the specifiied field is less than or equal to the supplied value. Valid fields are `created_at`.
  */
  public static Bundle list( HashMap<String, Object> parameters) {
    return list(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static Bundle list( HashMap<String, Object> parameters, HashMap<String, Object> options) {
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
    return (Bundle) null;
  }

  public static Bundle all(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Bundle ID.
  */
  public static Bundle find(Long id,  HashMap<String, Object> parameters) {
    return find(id, parameters, null);
  }

  public static Bundle find(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return find(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static Bundle find(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) {
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
    return (Bundle) null;
  }

  public static Bundle get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   paths (required) - array(string) - A list of paths to include in this bundle.
  *   password - string - Password for this bundle.
  *   expires_at - string - Bundle expiration date/time
  *   max_uses - int64 - Maximum number of times bundle can be accessed
  *   description - string - Public description
  *   note - string - Bundle internal note
  *   code - string - Bundle code.  This code forms the end part of the Public URL.
  *   require_registration - boolean - Show a registration page that captures the downloader's name and email address?
  *   clickwrap_id - int64 - ID of the clickwrap to use with this bundle.
  *   inbox_id - int64 - ID of the associated inbox, if available.
  *   require_share_recipient - boolean - Only allow access to recipients who have explicitly received the share via an email sent through the Files.com UI?
  */
  public static Bundle create( HashMap<String, Object> parameters) {
    return create(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static Bundle create( HashMap<String, Object> parameters, HashMap<String, Object> options) {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }

    if (parameters.containsKey("paths") && !(parameters.get("paths") instanceof String[] )) {
      throw new IllegalArgumentException("Bad parameter: paths must be of type String[] parameters[\"paths\"]");
    }

    if (parameters.containsKey("password") && !(parameters.get("password") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: password must be of type String parameters[\"password\"]");
    }

    if (parameters.containsKey("expires_at") && !(parameters.get("expires_at") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: expires_at must be of type String parameters[\"expires_at\"]");
    }

    if (parameters.containsKey("max_uses") && !(parameters.get("max_uses") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: max_uses must be of type Long parameters[\"max_uses\"]");
    }

    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }

    if (parameters.containsKey("note") && !(parameters.get("note") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: note must be of type String parameters[\"note\"]");
    }

    if (parameters.containsKey("code") && !(parameters.get("code") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: code must be of type String parameters[\"code\"]");
    }

    if (parameters.containsKey("require_registration") && !(parameters.get("require_registration") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: require_registration must be of type Boolean parameters[\"require_registration\"]");
    }

    if (parameters.containsKey("clickwrap_id") && !(parameters.get("clickwrap_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: clickwrap_id must be of type Long parameters[\"clickwrap_id\"]");
    }

    if (parameters.containsKey("inbox_id") && !(parameters.get("inbox_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: inbox_id must be of type Long parameters[\"inbox_id\"]");
    }

    if (parameters.containsKey("require_share_recipient") && !(parameters.get("require_share_recipient") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: require_share_recipient must be of type Boolean parameters[\"require_share_recipient\"]");
    }

    if (!parameters.containsKey("paths") || parameters.get("paths") == null) {
      throw new NullPointerException("Parameter missing: paths parameters[\"paths\"]");
    }
    // TODO: Send request
    return (Bundle) null;
  }


  /**
  * Send email(s) with a link to bundle
  *
  * Parameters:
  *   to (required) - array(string) - A list of email addresses to share this bundle with.
  *   note - string - Note to include in email.
  */
  public static Bundle share(Long id,  HashMap<String, Object> parameters) {
    return share(id, parameters, null);
  }

  public static Bundle share(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return share(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static Bundle share(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id != null){
      parameters.put("id",id);
    }
    if (parameters.containsKey("id") && !(parameters.get("id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (parameters.containsKey("to") && !(parameters.get("to") instanceof String[] )) {
      throw new IllegalArgumentException("Bad parameter: to must be of type String[] parameters[\"to\"]");
    }

    if (parameters.containsKey("note") && !(parameters.get("note") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: note must be of type String parameters[\"note\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    if (!parameters.containsKey("to") || parameters.get("to") == null) {
      throw new NullPointerException("Parameter missing: to parameters[\"to\"]");
    }
    // TODO: Send request
    return (Bundle) null;
  }


  /**
  * Parameters:
  *   password - string - Password for this bundle.
  *   clickwrap_id - int64 - ID of the clickwrap to use with this bundle.
  *   code - string - Bundle code.  This code forms the end part of the Public URL.
  *   description - string - Public description
  *   expires_at - string - Bundle expiration date/time
  *   inbox_id - int64 - ID of the associated inbox, if available.
  *   max_uses - int64 - Maximum number of times bundle can be accessed
  *   note - string - Bundle internal note
  *   require_registration - boolean - Show a registration page that captures the downloader's name and email address?
  *   require_share_recipient - boolean - Only allow access to recipients who have explicitly received the share via an email sent through the Files.com UI?
  */
  public static Bundle update(Long id,  HashMap<String, Object> parameters) {
    return update(id, parameters, null);
  }

  public static Bundle update(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return update(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static Bundle update(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id != null){
      parameters.put("id",id);
    }
    if (parameters.containsKey("id") && !(parameters.get("id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (parameters.containsKey("password") && !(parameters.get("password") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: password must be of type String parameters[\"password\"]");
    }

    if (parameters.containsKey("clickwrap_id") && !(parameters.get("clickwrap_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: clickwrap_id must be of type Long parameters[\"clickwrap_id\"]");
    }

    if (parameters.containsKey("code") && !(parameters.get("code") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: code must be of type String parameters[\"code\"]");
    }

    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }

    if (parameters.containsKey("expires_at") && !(parameters.get("expires_at") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: expires_at must be of type String parameters[\"expires_at\"]");
    }

    if (parameters.containsKey("inbox_id") && !(parameters.get("inbox_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: inbox_id must be of type Long parameters[\"inbox_id\"]");
    }

    if (parameters.containsKey("max_uses") && !(parameters.get("max_uses") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: max_uses must be of type Long parameters[\"max_uses\"]");
    }

    if (parameters.containsKey("note") && !(parameters.get("note") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: note must be of type String parameters[\"note\"]");
    }

    if (parameters.containsKey("require_registration") && !(parameters.get("require_registration") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: require_registration must be of type Boolean parameters[\"require_registration\"]");
    }

    if (parameters.containsKey("require_share_recipient") && !(parameters.get("require_share_recipient") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: require_share_recipient must be of type Boolean parameters[\"require_share_recipient\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    // TODO: Send request
    return (Bundle) null;
  }


  /**
  */
  public static Bundle delete(Long id,  HashMap<String, Object> parameters) {
    return delete(id, parameters, null);
  }

  public static Bundle delete(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return delete(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static Bundle delete(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) {
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
    return (Bundle) null;
  }

  public static Bundle destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return delete(id, parameters, options);
  }

}


