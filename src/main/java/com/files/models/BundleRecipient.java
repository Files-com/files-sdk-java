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

public class BundleRecipient {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public BundleRecipient() {
    this(null, null);
  }

  public BundleRecipient(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public BundleRecipient(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * The recipient's company.
  */
  @Getter
  @Setter
  @JsonProperty("company")
  private String company;

  /**
  * The recipient's name.
  */
  @Getter
  @Setter
  @JsonProperty("name")
  private String name;

  /**
  * A note sent to the recipient with the bundle.
  */
  @Getter
  @Setter
  @JsonProperty("note")
  private String note;

  /**
  * The recipient's email address.
  */
  @Getter
  @Setter
  @JsonProperty("recipient")
  private String recipient;

  /**
  * When the Bundle was shared with this recipient.
  */
  @Getter
  @Setter
  @JsonProperty("sent_at")
  private Date sentAt;

  /**
  * User ID.  Provide a value of `0` to operate the current session's user.
  */
  @Getter
  @Setter
  @JsonProperty("user_id")
  private Long userId;

  /**
  * Bundle to share.
  */
  @Getter
  @Setter
  @JsonProperty("bundle_id")
  private Long bundleId;

  /**
  * Set to true to share the link with the recipient upon creation.
  */
  @Getter
  @Setter
  @JsonProperty("share_after_create")
  private Boolean shareAfterCreate;


  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    if (parameters.containsKey("id") && parameters.get("id") != null) {
      throw new UnsupportedOperationException("The BundleRecipient Object doesn't support updates.");
    } else {
      BundleRecipient newObject = BundleRecipient.create(parameters, this.options);
    }
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   cursor - string - Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `has_registrations`.
  *   filter - object - If set, return records where the specifiied field is equal to the supplied value. Valid fields are `has_registrations`.
  *   filter_gt - object - If set, return records where the specifiied field is greater than the supplied value. Valid fields are `has_registrations`.
  *   filter_gteq - object - If set, return records where the specifiied field is greater than or equal to the supplied value. Valid fields are `has_registrations`.
  *   filter_like - object - If set, return records where the specifiied field is equal to the supplied value. Valid fields are `has_registrations`.
  *   filter_lt - object - If set, return records where the specifiied field is less than the supplied value. Valid fields are `has_registrations`.
  *   filter_lteq - object - If set, return records where the specifiied field is less than or equal to the supplied value. Valid fields are `has_registrations`.
  *   bundle_id (required) - int64 - List recipients for the bundle with this ID.
  */
  public static List<BundleRecipient> list() throws IOException{
    return list(null,null);
  }
  public static List<BundleRecipient> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  public static List<BundleRecipient> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }

    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Map<String, String> parameters[\"sort_by\"]");
    }

    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Map<String, String> parameters[\"filter\"]");
    }

    if (parameters.containsKey("filter_gt") && !(parameters.get("filter_gt") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter_gt must be of type Map<String, String> parameters[\"filter_gt\"]");
    }

    if (parameters.containsKey("filter_gteq") && !(parameters.get("filter_gteq") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter_gteq must be of type Map<String, String> parameters[\"filter_gteq\"]");
    }

    if (parameters.containsKey("filter_like") && !(parameters.get("filter_like") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter_like must be of type Map<String, String> parameters[\"filter_like\"]");
    }

    if (parameters.containsKey("filter_lt") && !(parameters.get("filter_lt") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter_lt must be of type Map<String, String> parameters[\"filter_lt\"]");
    }

    if (parameters.containsKey("filter_lteq") && !(parameters.get("filter_lteq") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter_lteq must be of type Map<String, String> parameters[\"filter_lteq\"]");
    }

    if (parameters.containsKey("bundle_id") && !(parameters.get("bundle_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: bundle_id must be of type Long parameters[\"bundle_id\"]");
    }

    if (!parameters.containsKey("bundle_id") || parameters.get("bundle_id") == null) {
      throw new NullPointerException("Parameter missing: bundle_id parameters[\"bundle_id\"]");
    }
    String url = String.format("%s%s/bundle_recipients", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<BundleRecipient>> typeReference = new TypeReference<List<BundleRecipient>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<BundleRecipient> all() throws IOException {
    return all(null, null);
  }

  public static List<BundleRecipient> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   bundle_id (required) - int64 - Bundle to share.
  *   recipient (required) - string - Email addresses to share this bundle with.
  *   name - string - Name of recipient.
  *   company - string - Company of recipient.
  *   note - string - Note to include in email.
  *   share_after_create - boolean - Set to true to share the link with the recipient upon creation.
  */
  public static BundleRecipient create() throws IOException{
    return create(null,null);
  }
  public static BundleRecipient create( HashMap<String, Object> parameters) throws IOException {
    return create(parameters, null);
  }


  public static BundleRecipient create( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }

    if (parameters.containsKey("bundle_id") && !(parameters.get("bundle_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: bundle_id must be of type Long parameters[\"bundle_id\"]");
    }

    if (parameters.containsKey("recipient") && !(parameters.get("recipient") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: recipient must be of type String parameters[\"recipient\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("company") && !(parameters.get("company") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: company must be of type String parameters[\"company\"]");
    }

    if (parameters.containsKey("note") && !(parameters.get("note") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: note must be of type String parameters[\"note\"]");
    }

    if (parameters.containsKey("share_after_create") && !(parameters.get("share_after_create") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: share_after_create must be of type Boolean parameters[\"share_after_create\"]");
    }

    if (!parameters.containsKey("bundle_id") || parameters.get("bundle_id") == null) {
      throw new NullPointerException("Parameter missing: bundle_id parameters[\"bundle_id\"]");
    }
    if (!parameters.containsKey("recipient") || parameters.get("recipient") == null) {
      throw new NullPointerException("Parameter missing: recipient parameters[\"recipient\"]");
    }
    String url = String.format("%s%s/bundle_recipients", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<BundleRecipient> typeReference = new TypeReference<BundleRecipient>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


}


