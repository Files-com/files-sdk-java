package com.files.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

@JsonIgnoreProperties(ignoreUnknown = true)
public class Group {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
    .builder()
    .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
    .build();

  public Group() {
    this(null, null);
  }

  public Group(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Group(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Group ID
  */
  @Getter
  @Setter
  @JsonProperty("id")
  public Long id;

  /**
  * Group name
  */
  @Getter
  @Setter
  @JsonProperty("name")
  public String name;

  /**
  * Comma-delimited list of user IDs who are group administrators (separated by commas)
  */
  @Getter
  @Setter
  @JsonProperty("admin_ids")
  public String adminIds;

  /**
  * Notes about this group
  */
  @Getter
  @Setter
  @JsonProperty("notes")
  public String notes;

  /**
  * Comma-delimited list of user IDs who belong to this group (separated by commas)
  */
  @Getter
  @Setter
  @JsonProperty("user_ids")
  public String userIds;

  /**
  * Comma-delimited list of usernames who belong to this group (separated by commas)
  */
  @Getter
  @Setter
  @JsonProperty("usernames")
  public String usernames;

  /**
  * Parameters:
  *   name - string - Group name.
  *   notes - string - Group notes.
  *   user_ids - string - A list of user ids. If sent as a string, should be comma-delimited.
  *   admin_ids - string - A list of group admin user ids. If sent as a string, should be comma-delimited.
  */
  public Group update(HashMap<String, Object> parameters) {
    return update(parameters);
  }

  /**
  */
  public Group delete(HashMap<String, Object> parameters) {
    return delete(parameters);
  }

  public void destroy(HashMap<String, Object> parameters) {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    if (parameters.containsKey("id") && parameters.get("id") != null) {
      update(parameters);
    } else {
      Group newObject = Group.create(parameters, this.options);
    }
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction (e.g. `sort_by[name]=desc`). Valid fields are `name`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `name`.
  *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `name`.
  *   ids - string - Comma-separated list of group ids to include in results.
  */
  public static List<Group> list() throws IOException {
    return list(null,null);
  }
  public static List<Group> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  public static List<Group> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


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
    if (parameters.containsKey("filter_prefix") && !(parameters.get("filter_prefix") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter_prefix must be of type Map<String, String> parameters[\"filter_prefix\"]");
    }
    if (parameters.containsKey("ids") && !(parameters.get("ids") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: ids must be of type String parameters[\"ids\"]");
    }



    String url = String.format("%s%s/groups", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<Group>> typeReference = new TypeReference<List<Group>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<Group> all() throws IOException {
    return all(null, null);
  }

  public static List<Group> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Group ID.
  */
  public static List<Group> find() throws IOException {
    return find(null, null,null);
  }
  public static List<Group> find(Long id,  HashMap<String, Object> parameters) throws IOException {
    return find(id, parameters, null);
  }

  public static List<Group> find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(null, parameters, options);
  }

  public static List<Group> find(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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

    String url = String.format("%s%s/groups/%s", urlParts);

    TypeReference<List<Group>> typeReference = new TypeReference<List<Group>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<Group> get() throws IOException {
    return get(null, null, null);
  }

  public static List<Group> get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   name - string - Group name.
  *   notes - string - Group notes.
  *   user_ids - string - A list of user ids. If sent as a string, should be comma-delimited.
  *   admin_ids - string - A list of group admin user ids. If sent as a string, should be comma-delimited.
  */
  public static Group create() throws IOException {
    return create(null,null);
  }
  public static Group create( HashMap<String, Object> parameters) throws IOException {
    return create(parameters, null);
  }


  public static Group create( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("notes") && !(parameters.get("notes") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: notes must be of type String parameters[\"notes\"]");
    }
    if (parameters.containsKey("user_ids") && !(parameters.get("user_ids") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: user_ids must be of type String parameters[\"user_ids\"]");
    }
    if (parameters.containsKey("admin_ids") && !(parameters.get("admin_ids") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: admin_ids must be of type String parameters[\"admin_ids\"]");
    }



    String url = String.format("%s%s/groups", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<Group> typeReference = new TypeReference<Group>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   name - string - Group name.
  *   notes - string - Group notes.
  *   user_ids - string - A list of user ids. If sent as a string, should be comma-delimited.
  *   admin_ids - string - A list of group admin user ids. If sent as a string, should be comma-delimited.
  */
  public static Group update() throws IOException {
    return update(null, null,null);
  }
  public static Group update(Long id,  HashMap<String, Object> parameters) throws IOException {
    return update(id, parameters, null);
  }

  public static Group update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return update(null, parameters, options);
  }

  public static Group update(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = ((Long) parameters.get("id"));
    }


    if (!(id instanceof Long) ) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("notes") && !(parameters.get("notes") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: notes must be of type String parameters[\"notes\"]");
    }
    if (parameters.containsKey("user_ids") && !(parameters.get("user_ids") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: user_ids must be of type String parameters[\"user_ids\"]");
    }
    if (parameters.containsKey("admin_ids") && !(parameters.get("admin_ids") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: admin_ids must be of type String parameters[\"admin_ids\"]");
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

    String url = String.format("%s%s/groups/%s", urlParts);

    TypeReference<Group> typeReference = new TypeReference<Group>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  */
  public static Group delete() throws IOException {
    return delete(null, null,null);
  }
  public static Group delete(Long id,  HashMap<String, Object> parameters) throws IOException {
    return delete(id, parameters, null);
  }

  public static Group delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(null, parameters, options);
  }

  public static Group delete(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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

    String url = String.format("%s%s/groups/%s", urlParts);

    TypeReference<Group> typeReference = new TypeReference<Group>() {};
    return FilesClient.requestItem(url, RequestMethods.DELETE, typeReference, parameters, options);
  }

  public static Group destroy() throws IOException {
    return destroy(null, null, null);
  }

  public static Group destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(id, parameters, options);
  }

}


