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

public class BundleRecipient {
  private HashMap<String, Object> attributes;
  private HashMap<String, Object> options;

  public BundleRecipient(HashMap<String, Object> attributes, HashMap<String, Object> options) {
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
  * The recipient's company.
  */
  @Getter
  @JsonProperty("company")
  public String company;

  /**
  * The recipient's name.
  */
  @Getter
  @JsonProperty("name")
  public String name;

  /**
  * A note sent to the recipient with the bundle.
  */
  @Getter
  @JsonProperty("note")
  public String note;

  /**
  * The recipient's email address.
  */
  @Getter
  @JsonProperty("recipient")
  public String recipient;

  /**
  * When the Bundle was shared with this recipient.
  */
  @Getter
  @JsonProperty("sent_at")
  public Date sentAt;



  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  *   bundle_id (required) - int64 - List recipients for the bundle with this ID.
  */
  public static BundleRecipient list( HashMap<String, Object> parameters) {
    return list(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static BundleRecipient list( HashMap<String, Object> parameters, HashMap<String, Object> options) {
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

    if (parameters.containsKey("bundle_id") && !(parameters.get("bundle_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: bundle_id must be of type Long parameters[\"bundle_id\"]");
    }

    if (!parameters.containsKey("bundle_id") || parameters.get("bundle_id") == null) {
      throw new NullPointerException("Parameter missing: bundle_id parameters[\"bundle_id\"]");
    }
    // TODO: Send request
    return (BundleRecipient) null;
  }

  public static BundleRecipient all(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return list(parameters, options);
  }

}


