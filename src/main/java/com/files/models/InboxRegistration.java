package com.files.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InboxRegistration {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .build();


  public InboxRegistration() {
    this(null, null);
  }

  public InboxRegistration(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public InboxRegistration(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Registration cookie code
  */
  @Getter
  @JsonProperty("code")
  public String code;

  /**
  * Registrant name
  */
  @Getter
  @JsonProperty("name")
  public String name;

  /**
  * Registrant company name
  */
  @Getter
  @JsonProperty("company")
  public String company;

  /**
  * Registrant email address
  */
  @Getter
  @JsonProperty("email")
  public String email;

  /**
  * Clickwrap text that was shown to the registrant
  */
  @Getter
  @JsonProperty("clickwrap_body")
  public String clickwrapBody;

  /**
  * Id of associated form field set
  */
  @Getter
  @JsonProperty("form_field_set_id")
  public Long formFieldSetId;

  /**
  * Data for form field set with form field ids as keys and user data as values
  */
  @Getter
  @JsonProperty("form_field_data")
  public Map<String, String> formFieldData;

  /**
  * Id of associated inbox
  */
  @Getter
  @JsonProperty("inbox_id")
  public Long inboxId;

  /**
  * Id of associated inbox recipient
  */
  @Getter
  @JsonProperty("inbox_recipient_id")
  public Long inboxRecipientId;

  /**
  * Title of associated inbox
  */
  @Getter
  @JsonProperty("inbox_title")
  public String inboxTitle;

  /**
  * Registration creation date/time
  */
  @Getter
  @JsonProperty("created_at")
  public Date createdAt;



  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   folder_behavior_id - int64 - ID of the associated Inbox.
  */
  public static List<InboxRegistration> list() throws IOException {
    return list(null, null);
  }

  public static List<InboxRegistration> list(HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  public static List<InboxRegistration> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }
    if (parameters.containsKey("folder_behavior_id") && !(parameters.get("folder_behavior_id") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: folder_behavior_id must be of type Long parameters[\"folder_behavior_id\"]");
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
