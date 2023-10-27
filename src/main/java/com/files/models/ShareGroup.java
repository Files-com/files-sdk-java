package com.files.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.ListIterator;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShareGroup {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .build();


  public ShareGroup() {
    this(null, null);
  }

  public ShareGroup(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public ShareGroup(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Share Group ID
  */
  @Getter
  @Setter
  @JsonProperty("id")
  public Long id;

  /**
  * Name of the share group
  */
  @Getter
  @Setter
  @JsonProperty("name")
  public String name;

  /**
  * Additional notes of the share group
  */
  @Getter
  @Setter
  @JsonProperty("notes")
  public String notes;

  /**
  * Owner User ID
  */
  @Getter
  @Setter
  @JsonProperty("user_id")
  public Long userId;

  /**
  * A list of share group members
  */
  @Getter
  @Setter
  @JsonProperty("share_group_members")
  public Object[] shareGroupMembers;

  /**
  * A list of share group members.
  */
  @Getter
  @Setter
  @JsonProperty("members")
  public Object[] members;

  /**
  * Parameters:
  *   notes - string - Additional notes of the share group
  *   name - string - Name of the share group
  *   members - array(object) - A list of share group members.
  */
  public ShareGroup update(HashMap<String, Object> parameters) {
    return update(parameters);
  }

  /**
  */
  public void delete(HashMap<String, Object> parameters) {
    delete(parameters);
  }

  public void destroy(HashMap<String, Object> parameters) {
    delete(parameters);
  }
  

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    if (parameters.containsKey("id") && parameters.get("id") != null) {
      update(parameters);
    } else {
      ShareGroup newObject = ShareGroup.create(parameters, this.options);
    }
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  */
  public static ListIterator<ShareGroup> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<ShareGroup> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<ShareGroup> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }
    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }



    String url = String.format("%s%s/share_groups", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<ShareGroup>> typeReference = new TypeReference<List<ShareGroup>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<ShareGroup> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<ShareGroup> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Share Group ID.
  */
  public static ShareGroup find() throws RuntimeException {
    return find(null, null, null);
  }

  public static ShareGroup find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static ShareGroup find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static ShareGroup find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (!(id instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/share_groups/%s", urlParts);

    TypeReference<ShareGroup> typeReference = new TypeReference<ShareGroup>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ShareGroup get() throws RuntimeException {
    return get(null, null, null);
  }

  public static ShareGroup get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   notes - string - Additional notes of the share group
  *   name (required) - string - Name of the share group
  *   members (required) - array(object) - A list of share group members.
  */
  public static ShareGroup create() throws RuntimeException {
    return create(null, null);
  }

  public static ShareGroup create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static ShareGroup create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }
    if (parameters.containsKey("notes") && !(parameters.get("notes") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: notes must be of type String parameters[\"notes\"]");
    }
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("members") && !(parameters.get("members") instanceof Object[])) {
      throw new IllegalArgumentException("Bad parameter: members must be of type Object[] parameters[\"members\"]");
    }

    if (!parameters.containsKey("name") || parameters.get("name") == null) {
      throw new NullPointerException("Parameter missing: name parameters[\"name\"]");
    }
    if (!parameters.containsKey("members") || parameters.get("members") == null) {
      throw new NullPointerException("Parameter missing: members parameters[\"members\"]");
    }


    String url = String.format("%s%s/share_groups", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<ShareGroup> typeReference = new TypeReference<ShareGroup>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   notes - string - Additional notes of the share group
  *   name - string - Name of the share group
  *   members - array(object) - A list of share group members.
  */
  public static ShareGroup update() throws RuntimeException {
    return update(null, null, null);
  }

  public static ShareGroup update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static ShareGroup update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static ShareGroup update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (!(id instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }
    if (parameters.containsKey("notes") && !(parameters.get("notes") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: notes must be of type String parameters[\"notes\"]");
    }
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("members") && !(parameters.get("members") instanceof Object[])) {
      throw new IllegalArgumentException("Bad parameter: members must be of type Object[] parameters[\"members\"]");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/share_groups/%s", urlParts);

    TypeReference<ShareGroup> typeReference = new TypeReference<ShareGroup>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (!(id instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/share_groups/%s", urlParts);

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
