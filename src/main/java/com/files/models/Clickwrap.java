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

public class Clickwrap {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public Clickwrap() {
    this(null, null);
  }

  public Clickwrap(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Clickwrap(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Clickwrap ID
  */
  @Getter
  @Setter
  @JsonProperty("id")
  private Long id;

  /**
  * Name of the Clickwrap agreement (used when selecting from multiple Clickwrap agreements.)
  */
  @Getter
  @Setter
  @JsonProperty("name")
  private String name;

  /**
  * Body text of Clickwrap (supports Markdown formatting).
  */
  @Getter
  @Setter
  @JsonProperty("body")
  private String body;

  /**
  * Use this Clickwrap for User Registrations?  Note: This only applies to User Registrations where the User is invited to your Files.com site using an E-Mail invitation process where they then set their own password.
  */
  @Getter
  @Setter
  @JsonProperty("use_with_users")
  private String useWithUsers;

  /**
  * Use this Clickwrap for Bundles?
  */
  @Getter
  @Setter
  @JsonProperty("use_with_bundles")
  private String useWithBundles;

  /**
  * Use this Clickwrap for Inboxes?
  */
  @Getter
  @Setter
  @JsonProperty("use_with_inboxes")
  private String useWithInboxes;

  /**
  * Parameters:
  *   name - string - Name of the Clickwrap agreement (used when selecting from multiple Clickwrap agreements.)
  *   body - string - Body text of Clickwrap (supports Markdown formatting).
  *   use_with_bundles - string - Use this Clickwrap for Bundles?
  *   use_with_inboxes - string - Use this Clickwrap for Inboxes?
  *   use_with_users - string - Use this Clickwrap for User Registrations?  Note: This only applies to User Registrations where the User is invited to your Files.com site using an E-Mail invitation process where they then set their own password.
  */
  public Clickwrap update(HashMap<String, Object> parameters) {
    return update(parameters);
  }

  /**
  */
  public Clickwrap delete(HashMap<String, Object> parameters) {
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
      Clickwrap newObject = Clickwrap.create(parameters, this.options);
    }
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  */
  public static List<Clickwrap> list() throws IOException{
    return list(null,null);
  }
  public static List<Clickwrap> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  public static List<Clickwrap> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }

    String url = String.format("%s%s/clickwraps", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<Clickwrap>> typeReference = new TypeReference<List<Clickwrap>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<Clickwrap> all() throws IOException {
    return all(null, null);
  }

  public static List<Clickwrap> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Clickwrap ID.
  */
  public static List<Clickwrap> find() throws IOException{
    return find(null, null,null);
  }
  public static List<Clickwrap> find(Long id,  HashMap<String, Object> parameters) throws IOException {
    return find(id, parameters, null);
  }

  public static List<Clickwrap> find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(null, parameters, options);
  }

  public static List<Clickwrap> find(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
    String url = String.format("%s%s/clickwraps/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<List<Clickwrap>> typeReference = new TypeReference<List<Clickwrap>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<Clickwrap> get() throws IOException {
    return get(null, null, null);
  }

  public static List<Clickwrap> get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   name - string - Name of the Clickwrap agreement (used when selecting from multiple Clickwrap agreements.)
  *   body - string - Body text of Clickwrap (supports Markdown formatting).
  *   use_with_bundles - string - Use this Clickwrap for Bundles?
  *   use_with_inboxes - string - Use this Clickwrap for Inboxes?
  *   use_with_users - string - Use this Clickwrap for User Registrations?  Note: This only applies to User Registrations where the User is invited to your Files.com site using an E-Mail invitation process where they then set their own password.
  */
  public static Clickwrap create() throws IOException{
    return create(null,null);
  }
  public static Clickwrap create( HashMap<String, Object> parameters) throws IOException {
    return create(parameters, null);
  }


  public static Clickwrap create( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("body") && !(parameters.get("body") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: body must be of type String parameters[\"body\"]");
    }

    if (parameters.containsKey("use_with_bundles") && !(parameters.get("use_with_bundles") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: use_with_bundles must be of type String parameters[\"use_with_bundles\"]");
    }

    if (parameters.containsKey("use_with_inboxes") && !(parameters.get("use_with_inboxes") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: use_with_inboxes must be of type String parameters[\"use_with_inboxes\"]");
    }

    if (parameters.containsKey("use_with_users") && !(parameters.get("use_with_users") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: use_with_users must be of type String parameters[\"use_with_users\"]");
    }

    String url = String.format("%s%s/clickwraps", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<Clickwrap> typeReference = new TypeReference<Clickwrap>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   name - string - Name of the Clickwrap agreement (used when selecting from multiple Clickwrap agreements.)
  *   body - string - Body text of Clickwrap (supports Markdown formatting).
  *   use_with_bundles - string - Use this Clickwrap for Bundles?
  *   use_with_inboxes - string - Use this Clickwrap for Inboxes?
  *   use_with_users - string - Use this Clickwrap for User Registrations?  Note: This only applies to User Registrations where the User is invited to your Files.com site using an E-Mail invitation process where they then set their own password.
  */
  public static Clickwrap update() throws IOException{
    return update(null, null,null);
  }
  public static Clickwrap update(Long id,  HashMap<String, Object> parameters) throws IOException {
    return update(id, parameters, null);
  }

  public static Clickwrap update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return update(null, parameters, options);
  }

  public static Clickwrap update(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id != null){
      parameters.put("id",id);
    }
    if (parameters.containsKey("id") && !(parameters.get("id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("body") && !(parameters.get("body") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: body must be of type String parameters[\"body\"]");
    }

    if (parameters.containsKey("use_with_bundles") && !(parameters.get("use_with_bundles") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: use_with_bundles must be of type String parameters[\"use_with_bundles\"]");
    }

    if (parameters.containsKey("use_with_inboxes") && !(parameters.get("use_with_inboxes") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: use_with_inboxes must be of type String parameters[\"use_with_inboxes\"]");
    }

    if (parameters.containsKey("use_with_users") && !(parameters.get("use_with_users") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: use_with_users must be of type String parameters[\"use_with_users\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    String url = String.format("%s%s/clickwraps/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<Clickwrap> typeReference = new TypeReference<Clickwrap>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  */
  public static Clickwrap delete() throws IOException{
    return delete(null, null,null);
  }
  public static Clickwrap delete(Long id,  HashMap<String, Object> parameters) throws IOException {
    return delete(id, parameters, null);
  }

  public static Clickwrap delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(null, parameters, options);
  }

  public static Clickwrap delete(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
    String url = String.format("%s%s/clickwraps/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<Clickwrap> typeReference = new TypeReference<Clickwrap>() {};
    return FilesClient.requestItem(url, RequestMethods.DELETE, typeReference, parameters, options);
  }

  public static Clickwrap destroy() throws IOException {
    return destroy(null, null, null);
  }

  public static Clickwrap destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(id, parameters, options);
  }

}


