package com.files.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class BundleNotification {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
    .builder()
    .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
    .build();

  public BundleNotification() {
    this(null, null);
  }

  public BundleNotification(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public BundleNotification(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Bundle ID to notify on
  */
  @Getter
  @Setter
  @JsonProperty("bundle_id")
  public Long bundleId;

  /**
  * Bundle Notification ID
  */
  @Getter
  @Setter
  @JsonProperty("id")
  public Long id;

  /**
  * Triggers bundle notification when a registration action occurs for it.
  */
  @Getter
  @Setter
  @JsonProperty("notify_on_registration")
  public Boolean notifyOnRegistration;

  /**
  * The id of the user to notify.
  */
  @Getter
  @Setter
  @JsonProperty("user_id")
  public Long userId;

  /**
  */
  public BundleNotification delete(HashMap<String, Object> parameters) {
    return delete(parameters);
  }

  public void destroy(HashMap<String, Object> parameters) {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    if (parameters.containsKey("id") && parameters.get("id") != null) {
      throw new UnsupportedOperationException("The BundleNotification Object doesn't support updates.");
    } else {
      BundleNotification newObject = BundleNotification.create(parameters, this.options);
    }
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   cursor - string - Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via either the X-Files-Cursor-Next header or the X-Files-Cursor-Prev header.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   bundle_id - int64 - Bundle ID to notify on
  */
  public static List<BundleNotification> list() throws IOException {
    return list(null,null);
  }
  public static List<BundleNotification> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  public static List<BundleNotification> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
    if (parameters.containsKey("bundle_id") && !(parameters.get("bundle_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: bundle_id must be of type Long parameters[\"bundle_id\"]");
    }



    String url = String.format("%s%s/bundle_notifications", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<BundleNotification>> typeReference = new TypeReference<List<BundleNotification>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<BundleNotification> all() throws IOException {
    return all(null, null);
  }

  public static List<BundleNotification> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Bundle Notification ID.
  */
  public static List<BundleNotification> find() throws IOException {
    return find(null, null,null);
  }
  public static List<BundleNotification> find(Long id,  HashMap<String, Object> parameters) throws IOException {
    return find(id, parameters, null);
  }

  public static List<BundleNotification> find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(null, parameters, options);
  }

  public static List<BundleNotification> find(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = ((Long) parameters.get("id"));
    }


    if (!(id instanceof Long) ) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex){
      }
    }

    String url = String.format("%s%s/bundle_notifications/%s", urlParts);

    TypeReference<List<BundleNotification>> typeReference = new TypeReference<List<BundleNotification>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<BundleNotification> get() throws IOException {
    return get(null, null, null);
  }

  public static List<BundleNotification> get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   user_id (required) - int64 - The id of the user to notify.
  *   notify_on_registration - boolean - Triggers bundle notification when a registration action occurs for it.
  *   bundle_id (required) - int64 - Bundle ID to notify on
  */
  public static BundleNotification create() throws IOException {
    return create(null,null);
  }
  public static BundleNotification create( HashMap<String, Object> parameters) throws IOException {
    return create(parameters, null);
  }


  public static BundleNotification create( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }
    if (parameters.containsKey("notify_on_registration") && !(parameters.get("notify_on_registration") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: notify_on_registration must be of type Boolean parameters[\"notify_on_registration\"]");
    }
    if (parameters.containsKey("bundle_id") && !(parameters.get("bundle_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: bundle_id must be of type Long parameters[\"bundle_id\"]");
    }

    if (!parameters.containsKey("user_id") || parameters.get("user_id") == null) {
      throw new NullPointerException("Parameter missing: user_id parameters[\"user_id\"]");
    }
    if (!parameters.containsKey("bundle_id") || parameters.get("bundle_id") == null) {
      throw new NullPointerException("Parameter missing: bundle_id parameters[\"bundle_id\"]");
    }


    String url = String.format("%s%s/bundle_notifications", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<BundleNotification> typeReference = new TypeReference<BundleNotification>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  */
  public static BundleNotification delete() throws IOException {
    return delete(null, null,null);
  }
  public static BundleNotification delete(Long id,  HashMap<String, Object> parameters) throws IOException {
    return delete(id, parameters, null);
  }

  public static BundleNotification delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(null, parameters, options);
  }

  public static BundleNotification delete(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = ((Long) parameters.get("id"));
    }


    if (!(id instanceof Long) ) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex){
      }
    }

    String url = String.format("%s%s/bundle_notifications/%s", urlParts);

    TypeReference<BundleNotification> typeReference = new TypeReference<BundleNotification>() {};
    return FilesClient.requestItem(url, RequestMethods.DELETE, typeReference, parameters, options);
  }

  public static BundleNotification destroy() throws IOException {
    return destroy(null, null, null);
  }

  public static BundleNotification destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(id, parameters, options);
  }

}

