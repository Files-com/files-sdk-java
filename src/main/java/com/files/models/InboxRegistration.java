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

public class InboxRegistration {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public InboxRegistration() {
    this(null, null);
  }

  public InboxRegistration(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public InboxRegistration(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Registration cookie code
  */
  @Getter
  @JsonProperty("code")
  private String code;

  /**
  * Registrant name
  */
  @Getter
  @JsonProperty("name")
  private String name;

  /**
  * Registrant company name
  */
  @Getter
  @JsonProperty("company")
  private String company;

  /**
  * Registrant email address
  */
  @Getter
  @JsonProperty("email")
  private String email;

  /**
  * Clickwrap text that was shown to the registrant
  */
  @Getter
  @JsonProperty("clickwrap_body")
  private String clickwrapBody;

  /**
  * Id of associated form field set
  */
  @Getter
  @JsonProperty("form_field_set_id")
  private Long formFieldSetId;

  /**
  * Data for form field set with form field ids as keys and user data as values
  */
  @Getter
  @JsonProperty("form_field_data")
  private String formFieldData;



  /**
  * Parameters:
  *   cursor - string - Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   folder_behavior_id (required) - int64 - ID of the associated Inbox.
  */
  public static List<InboxRegistration> list() throws IOException{
    return list(null,null);
  }
  public static List<InboxRegistration> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  public static List<InboxRegistration> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }

    if (parameters.containsKey("folder_behavior_id") && !(parameters.get("folder_behavior_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: folder_behavior_id must be of type Long parameters[\"folder_behavior_id\"]");
    }

    if (!parameters.containsKey("folder_behavior_id") || parameters.get("folder_behavior_id") == null) {
      throw new NullPointerException("Parameter missing: folder_behavior_id parameters[\"folder_behavior_id\"]");
    }
    String url = String.format("%s%s/inbox_registrations", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<InboxRegistration>> typeReference = new TypeReference<List<InboxRegistration>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<InboxRegistration> all() throws IOException {
    return all(null, null);
  }

  public static List<InboxRegistration> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

}


