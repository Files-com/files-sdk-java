package com.files.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.net.HttpMethods.RequestMethods;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class Clickwrap {
  private HashMap<String, Object> attributes;
  private HashMap<String, Object> options;

  public Clickwrap() {
    this(null, null);
  }

  public Clickwrap(HashMap<String, Object> attributes) {
    this(attributes, null);
  }

  public Clickwrap(HashMap<String, Object> attributes, HashMap<String, Object> options) {
    this.attributes = attributes;
    this.options = options;
    try{
      ObjectMapper objectMapper = new ObjectMapper();
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(attributes));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Name of the Clickwrap agreement (used when selecting from multiple Clickwrap agreements.)
  */
  @Getter
  @Setter
  @JsonProperty("name")
  public String name;

  /**
  * Body text of Clickwrap (supports Markdown formatting).
  */
  @Getter
  @Setter
  @JsonProperty("body")
  public String body;

  /**
  * Use this Clickwrap for User Registrations?  Note: This only applies to User Registrations where the User is invited to your Files.com site using an E-Mail invitation process where they then set their own password.
  */
  @Getter
  @Setter
  @JsonProperty("use_with_users")
  public String useWithUsers;

  /**
  * Use this Clickwrap for Bundles?
  */
  @Getter
  @Setter
  @JsonProperty("use_with_bundles")
  public String useWithBundles;

  /**
  * Use this Clickwrap for Inboxes?
  */
  @Getter
  @Setter
  @JsonProperty("use_with_inboxes")
  public String useWithInboxes;

  /**
  * Clickwrap ID.
  */
  @Getter
  @Setter
  @JsonProperty("id")
  public Long id;

  /**
  * Parameters:
  *   name - string - Name of the Clickwrap agreement (used when selecting from multiple Clickwrap agreements.)
  *   body - string - Body text of Clickwrap (supports Markdown formatting).
  *   use_with_bundles - string - Use this Clickwrap for Bundles?
  *   use_with_inboxes - string - Use this Clickwrap for Inboxes?
  *   use_with_users - string - Use this Clickwrap for User Registrations?  Note: This only applies to User Registrations where the User is invited to your Files.com site using an E-Mail invitation process where they then set their own password.
  */
  public Clickwrap update(HashMap<String, Object> parameters) {
    // TODO: Fill in operation implementation
    return (Clickwrap) null;
  }

  /**
  */
  public Clickwrap delete(HashMap<String, Object> parameters) {
    // TODO: Fill in operation implementation
    return (Clickwrap) null;
  }

  public void destroy(HashMap<String, Object> parameters) {
    delete(parameters);
  }

  public void save() throws IOException {
    if (this.attributes.get("id") != null) {
      update(this.attributes);
    } else {
      Clickwrap.create(this.attributes, this.options);
      // TODO save this.attributes = newObj.attributes;
    }
  }

  /**
  * Parameters:
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  */
  public static List<Clickwrap> list() throws IOException{
    return list(null,null);
  }
  public static List<Clickwrap> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static List<Clickwrap> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("page") && !(parameters.get("page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: page must be of type Long parameters[\"page\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }

    if (parameters.containsKey("action") && !(parameters.get("action") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: action must be of type String parameters[\"action\"]");
    }

    String url = String.format("%s%s/clickwraps", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<Clickwrap>> typeReference = new TypeReference<List<Clickwrap>>() {};
    return FilesClient.request(url, RequestMethods.GET, typeReference, parameters, options);
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

  // TODO: Use types for path_and_primary_params
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
    return FilesClient.request(url, RequestMethods.GET, typeReference, parameters, options);
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
  public static List<Clickwrap> create() throws IOException{
    return create(null,null);
  }
  public static List<Clickwrap> create( HashMap<String, Object> parameters) throws IOException {
    return create(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static List<Clickwrap> create( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
    TypeReference<List<Clickwrap>> typeReference = new TypeReference<List<Clickwrap>>() {};
    return FilesClient.request(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   name - string - Name of the Clickwrap agreement (used when selecting from multiple Clickwrap agreements.)
  *   body - string - Body text of Clickwrap (supports Markdown formatting).
  *   use_with_bundles - string - Use this Clickwrap for Bundles?
  *   use_with_inboxes - string - Use this Clickwrap for Inboxes?
  *   use_with_users - string - Use this Clickwrap for User Registrations?  Note: This only applies to User Registrations where the User is invited to your Files.com site using an E-Mail invitation process where they then set their own password.
  */
  public static List<Clickwrap> update() throws IOException{
    return update(null, null,null);
  }
  public static List<Clickwrap> update(Long id,  HashMap<String, Object> parameters) throws IOException {
    return update(id, parameters, null);
  }

  public static List<Clickwrap> update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return update(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static List<Clickwrap> update(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
    TypeReference<List<Clickwrap>> typeReference = new TypeReference<List<Clickwrap>>() {};
    return FilesClient.request(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  */
  public static List<Clickwrap> delete() throws IOException{
    return delete(null, null,null);
  }
  public static List<Clickwrap> delete(Long id,  HashMap<String, Object> parameters) throws IOException {
    return delete(id, parameters, null);
  }

  public static List<Clickwrap> delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static List<Clickwrap> delete(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
    return FilesClient.request(url, RequestMethods.DELETE, typeReference, parameters, options);
  }

  public static List<Clickwrap> destroy() throws IOException {
    return destroy(null, null, null);
  }

  public static List<Clickwrap> destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(id, parameters, options);
  }

}


