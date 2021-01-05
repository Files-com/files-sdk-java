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

public class FormFieldSet {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public FormFieldSet() {
    this(null, null);
  }

  public FormFieldSet(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public FormFieldSet(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Form field set id
  */
  @Getter
  @Setter
  @JsonProperty("id")
  private Long id;

  /**
  * Title to be displayed
  */
  @Getter
  @Setter
  @JsonProperty("title")
  private String title;

  /**
  * Layout of the form
  */
  @Getter
  @Setter
  @JsonProperty("form_layout")
  private Long formLayout;

  /**
  * Associated form fields
  */
  @Getter
  @Setter
  @JsonProperty("form_fields")
  private Object[] formFields;

  /**
  * User ID.  Provide a value of `0` to operate the current session's user.
  */
  @Getter
  @Setter
  @JsonProperty("user_id")
  private Long userId;

  /**
  * Parameters:
  *   title - string - Title to be displayed
  *   form_fields - array(object)
  */
  public FormFieldSet update(HashMap<String, Object> parameters) {
    return update(parameters);
  }

  /**
  */
  public FormFieldSet delete(HashMap<String, Object> parameters) {
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
      FormFieldSet newObject = FormFieldSet.create(parameters, this.options);
    }
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   cursor - string - Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  */
  public static List<FormFieldSet> list() throws IOException{
    return list(null,null);
  }
  public static List<FormFieldSet> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  public static List<FormFieldSet> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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

    String url = String.format("%s%s/form_field_sets", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<FormFieldSet>> typeReference = new TypeReference<List<FormFieldSet>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<FormFieldSet> all() throws IOException {
    return all(null, null);
  }

  public static List<FormFieldSet> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Form Field Set ID.
  */
  public static List<FormFieldSet> find() throws IOException{
    return find(null, null,null);
  }
  public static List<FormFieldSet> find(Long id,  HashMap<String, Object> parameters) throws IOException {
    return find(id, parameters, null);
  }

  public static List<FormFieldSet> find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(null, parameters, options);
  }

  public static List<FormFieldSet> find(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
    String url = String.format("%s%s/form_field_sets/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<List<FormFieldSet>> typeReference = new TypeReference<List<FormFieldSet>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<FormFieldSet> get() throws IOException {
    return get(null, null, null);
  }

  public static List<FormFieldSet> get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   title - string - Title to be displayed
  *   form_fields - array(object)
  */
  public static FormFieldSet create() throws IOException{
    return create(null,null);
  }
  public static FormFieldSet create( HashMap<String, Object> parameters) throws IOException {
    return create(parameters, null);
  }


  public static FormFieldSet create( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }

    if (parameters.containsKey("title") && !(parameters.get("title") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: title must be of type String parameters[\"title\"]");
    }

    if (parameters.containsKey("form_fields") && !(parameters.get("form_fields") instanceof Object[] )) {
      throw new IllegalArgumentException("Bad parameter: form_fields must be of type Object[] parameters[\"form_fields\"]");
    }

    String url = String.format("%s%s/form_field_sets", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<FormFieldSet> typeReference = new TypeReference<FormFieldSet>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   title - string - Title to be displayed
  *   form_fields - array(object)
  */
  public static FormFieldSet update() throws IOException{
    return update(null, null,null);
  }
  public static FormFieldSet update(Long id,  HashMap<String, Object> parameters) throws IOException {
    return update(id, parameters, null);
  }

  public static FormFieldSet update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return update(null, parameters, options);
  }

  public static FormFieldSet update(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id != null){
      parameters.put("id",id);
    }
    if (parameters.containsKey("id") && !(parameters.get("id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (parameters.containsKey("title") && !(parameters.get("title") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: title must be of type String parameters[\"title\"]");
    }

    if (parameters.containsKey("form_fields") && !(parameters.get("form_fields") instanceof Object[] )) {
      throw new IllegalArgumentException("Bad parameter: form_fields must be of type Object[] parameters[\"form_fields\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    String url = String.format("%s%s/form_field_sets/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<FormFieldSet> typeReference = new TypeReference<FormFieldSet>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  */
  public static FormFieldSet delete() throws IOException{
    return delete(null, null,null);
  }
  public static FormFieldSet delete(Long id,  HashMap<String, Object> parameters) throws IOException {
    return delete(id, parameters, null);
  }

  public static FormFieldSet delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(null, parameters, options);
  }

  public static FormFieldSet delete(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
    String url = String.format("%s%s/form_field_sets/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<FormFieldSet> typeReference = new TypeReference<FormFieldSet>() {};
    return FilesClient.requestItem(url, RequestMethods.DELETE, typeReference, parameters, options);
  }

  public static FormFieldSet destroy() throws IOException {
    return destroy(null, null, null);
  }

  public static FormFieldSet destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(id, parameters, options);
  }

}


