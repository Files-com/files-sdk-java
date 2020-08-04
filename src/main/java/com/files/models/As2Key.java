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

public class As2Key {
  private HashMap<String, Object> attributes;
  private HashMap<String, Object> options;

  public As2Key(HashMap<String, Object> attributes, HashMap<String, Object> options) {
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
  * AS2 Key ID
  */
  @Getter
  @Setter
  @JsonProperty("id")
  public Long id;

  /**
  * AS2 Partnership Name
  */
  @Getter
  @Setter
  @JsonProperty("as2_partnership_name")
  public String as2PartnershipName;

  /**
  * AS2 Key created at date/time
  */
  @Getter
  @JsonProperty("created_at")
  public Date createdAt;

  /**
  * Public key fingerprint
  */
  @Getter
  @Setter
  @JsonProperty("fingerprint")
  public String fingerprint;

  /**
  * User ID.  Provide a value of `0` to operate the current session's user.
  */
  @Getter
  @Setter
  @JsonProperty("user_id")
  public Long userId;

  /**
  * Actual contents of Public key.
  */
  @Getter
  @Setter
  @JsonProperty("public_key")
  public String publicKey;

  /**
  * Parameters:
  *   as2_partnership_name (required) - string - AS2 Partnership Name
  */
  public As2Key update(HashMap<String, Object> parameters) {
    // TODO: Fill in operation implementation
    return (As2Key) null;
  }

  /**
  */
  public As2Key delete(HashMap<String, Object> parameters) {
    // TODO: Fill in operation implementation
    return (As2Key) null;
  }

  public void destroy(HashMap<String, Object> parameters) {
    delete(parameters);
  }

  public void save() {
    if (this.attributes.get("id") != null) {
      update(this.attributes);
    } else {
      As2Key newObj = As2Key.create(this.attributes, this.options);
      this.attributes = newObj.attributes;
    }
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  */
  public static As2Key list( HashMap<String, Object> parameters) {
    return list(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static As2Key list( HashMap<String, Object> parameters, HashMap<String, Object> options) {
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

    // TODO: Send request
    return (As2Key) null;
  }

  public static As2Key all(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - As2 Key ID.
  */
  public static As2Key find(Long id,  HashMap<String, Object> parameters) {
    return find(id, parameters, null);
  }

  public static As2Key find(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return find(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static As2Key find(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) {
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
    return (As2Key) null;
  }

  public static As2Key get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   as2_partnership_name (required) - string - AS2 Partnership Name
  *   public_key (required) - string - Actual contents of Public key.
  */
  public static As2Key create( HashMap<String, Object> parameters) {
    return create(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static As2Key create( HashMap<String, Object> parameters, HashMap<String, Object> options) {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }

    if (parameters.containsKey("as2_partnership_name") && !(parameters.get("as2_partnership_name") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: as2_partnership_name must be of type String parameters[\"as2_partnership_name\"]");
    }

    if (parameters.containsKey("public_key") && !(parameters.get("public_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: public_key must be of type String parameters[\"public_key\"]");
    }

    if (!parameters.containsKey("as2_partnership_name") || parameters.get("as2_partnership_name") == null) {
      throw new NullPointerException("Parameter missing: as2_partnership_name parameters[\"as2_partnership_name\"]");
    }
    if (!parameters.containsKey("public_key") || parameters.get("public_key") == null) {
      throw new NullPointerException("Parameter missing: public_key parameters[\"public_key\"]");
    }
    // TODO: Send request
    return (As2Key) null;
  }


  /**
  * Parameters:
  *   as2_partnership_name (required) - string - AS2 Partnership Name
  */
  public static As2Key update(Long id,  HashMap<String, Object> parameters) {
    return update(id, parameters, null);
  }

  public static As2Key update(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return update(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static As2Key update(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id != null){
      parameters.put("id",id);
    }
    if (parameters.containsKey("id") && !(parameters.get("id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (parameters.containsKey("as2_partnership_name") && !(parameters.get("as2_partnership_name") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: as2_partnership_name must be of type String parameters[\"as2_partnership_name\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    if (!parameters.containsKey("as2_partnership_name") || parameters.get("as2_partnership_name") == null) {
      throw new NullPointerException("Parameter missing: as2_partnership_name parameters[\"as2_partnership_name\"]");
    }
    // TODO: Send request
    return (As2Key) null;
  }


  /**
  */
  public static As2Key delete(Long id,  HashMap<String, Object> parameters) {
    return delete(id, parameters, null);
  }

  public static As2Key delete(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return delete(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static As2Key delete(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) {
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
    return (As2Key) null;
  }

  public static As2Key destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return delete(id, parameters, options);
  }

}


