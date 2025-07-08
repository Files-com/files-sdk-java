package com.files.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.ListIterator;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
import com.files.util.UrlUtils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormFieldSet implements ModelInterface {
  private HashMap<String, Object> options;

  public void setOptions(HashMap<String, Object> options) {
    this.options = options;
  }

  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();


  public FormFieldSet() {
    this(null, null);
  }

  public FormFieldSet(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public FormFieldSet(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Form field set id
  */
  @JsonProperty("id")
  public Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /**
  * Title to be displayed
  */
  @JsonProperty("title")
  public String title;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  /**
  * Layout of the form
  */
  @JsonProperty("form_layout")
  public Long[] formLayout;

  public Long[] getFormLayout() {
    return formLayout;
  }

  public void setFormLayout(Long[] formLayout) {
    this.formLayout = formLayout;
  }

  /**
  * Associated form fields
  */
  @JsonProperty("form_fields")
  public Object[] formFields;

  public Object[] getFormFields() {
    return formFields;
  }

  public void setFormFields(Object[] formFields) {
    this.formFields = formFields;
  }

  /**
  * Any associated InboxRegistrations or BundleRegistrations can be saved without providing name
  */
  @JsonProperty("skip_name")
  public Boolean skipName;

  public Boolean getSkipName() {
    return skipName;
  }

  public void setSkipName(Boolean skipName) {
    this.skipName = skipName;
  }

  /**
  * Any associated InboxRegistrations or BundleRegistrations can be saved without providing email
  */
  @JsonProperty("skip_email")
  public Boolean skipEmail;

  public Boolean getSkipEmail() {
    return skipEmail;
  }

  public void setSkipEmail(Boolean skipEmail) {
    this.skipEmail = skipEmail;
  }

  /**
  * Any associated InboxRegistrations or BundleRegistrations can be saved without providing company
  */
  @JsonProperty("skip_company")
  public Boolean skipCompany;

  public Boolean getSkipCompany() {
    return skipCompany;
  }

  public void setSkipCompany(Boolean skipCompany) {
    this.skipCompany = skipCompany;
  }

  /**
  * Form Field Set is in use by an active Inbox / Bundle / Inbox Registration / Bundle Registration
  */
  @JsonProperty("in_use")
  public Boolean inUse;

  public Boolean getInUse() {
    return inUse;
  }

  public void setInUse(Boolean inUse) {
    this.inUse = inUse;
  }

  /**
  * User ID.  Provide a value of `0` to operate the current session's user.
  */
  @JsonProperty("user_id")
  public Long userId;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  /**
  * Parameters:
  *   title - string - Title to be displayed
  *   skip_email - boolean - Skip validating form email
  *   skip_name - boolean - Skip validating form name
  *   skip_company - boolean - Skip validating company
  *   form_fields - array(object)
  */
  public FormFieldSet update(HashMap<String, Object> parameters) throws IOException {
    return FormFieldSet.update(this.id, parameters, this.options);
  }

  /**
  */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    FormFieldSet.delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    FormFieldSet.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  */
  public static ListIterator<FormFieldSet> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<FormFieldSet> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<FormFieldSet> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }
    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }


    String url = String.format("%s%s/form_field_sets", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<FormFieldSet>> typeReference = new TypeReference<List<FormFieldSet>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<FormFieldSet> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<FormFieldSet> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Form Field Set ID.
  */
  public static FormFieldSet find() throws RuntimeException {
    return find(null, null, null);
  }

  public static FormFieldSet find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static FormFieldSet find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static FormFieldSet find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }



    String url = String.format("%s%s/form_field_sets/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<FormFieldSet> typeReference = new TypeReference<FormFieldSet>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static FormFieldSet get() throws RuntimeException {
    return get(null, null, null);
  }

  public static FormFieldSet get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   title - string - Title to be displayed
  *   skip_email - boolean - Skip validating form email
  *   skip_name - boolean - Skip validating form name
  *   skip_company - boolean - Skip validating company
  *   form_fields - array(object)
  */
  public static FormFieldSet create() throws RuntimeException {
    return create(null, null);
  }

  public static FormFieldSet create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static FormFieldSet create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }
    if (parameters.containsKey("title") && !(parameters.get("title") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: title must be of type String parameters[\"title\"]");
    }
    if (parameters.containsKey("skip_email") && !(parameters.get("skip_email") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: skip_email must be of type Boolean parameters[\"skip_email\"]");
    }
    if (parameters.containsKey("skip_name") && !(parameters.get("skip_name") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: skip_name must be of type Boolean parameters[\"skip_name\"]");
    }
    if (parameters.containsKey("skip_company") && !(parameters.get("skip_company") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: skip_company must be of type Boolean parameters[\"skip_company\"]");
    }
    if (parameters.containsKey("form_fields") && !(parameters.get("form_fields") instanceof Object[])) {
      throw new IllegalArgumentException("Bad parameter: form_fields must be of type Object[] parameters[\"form_fields\"]");
    }


    String url = String.format("%s%s/form_field_sets", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<FormFieldSet> typeReference = new TypeReference<FormFieldSet>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   title - string - Title to be displayed
  *   skip_email - boolean - Skip validating form email
  *   skip_name - boolean - Skip validating form name
  *   skip_company - boolean - Skip validating company
  *   form_fields - array(object)
  */
  public static FormFieldSet update() throws RuntimeException {
    return update(null, null, null);
  }

  public static FormFieldSet update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static FormFieldSet update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static FormFieldSet update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }
    if (parameters.containsKey("title") && !(parameters.get("title") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: title must be of type String parameters[\"title\"]");
    }
    if (parameters.containsKey("skip_email") && !(parameters.get("skip_email") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: skip_email must be of type Boolean parameters[\"skip_email\"]");
    }
    if (parameters.containsKey("skip_name") && !(parameters.get("skip_name") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: skip_name must be of type Boolean parameters[\"skip_name\"]");
    }
    if (parameters.containsKey("skip_company") && !(parameters.get("skip_company") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: skip_company must be of type Boolean parameters[\"skip_company\"]");
    }
    if (parameters.containsKey("form_fields") && !(parameters.get("form_fields") instanceof Object[])) {
      throw new IllegalArgumentException("Bad parameter: form_fields must be of type Object[] parameters[\"form_fields\"]");
    }



    String url = String.format("%s%s/form_field_sets/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<FormFieldSet> typeReference = new TypeReference<FormFieldSet>() {};
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


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }



    String url = String.format("%s%s/form_field_sets/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
