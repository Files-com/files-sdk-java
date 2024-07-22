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
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormFieldSet {
  private HashMap<String, Object> options;
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
  @Getter
  @Setter
  @JsonProperty("id")
  public Long id;

  /**
  * Title to be displayed
  */
  @Getter
  @Setter
  @JsonProperty("title")
  public String title;

  /**
  * Layout of the form
  */
  @Getter
  @Setter
  @JsonProperty("form_layout")
  public Long[] formLayout;

  /**
  * Associated form fields
  */
  @Getter
  @Setter
  @JsonProperty("form_fields")
  public Object[] formFields;

  /**
  * Any associated InboxRegistrations or BundleRegistrations can be saved without providing name
  */
  @Getter
  @Setter
  @JsonProperty("skip_name")
  public Boolean skipName;

  /**
  * Any associated InboxRegistrations or BundleRegistrations can be saved without providing email
  */
  @Getter
  @Setter
  @JsonProperty("skip_email")
  public Boolean skipEmail;

  /**
  * Any associated InboxRegistrations or BundleRegistrations can be saved without providing company
  */
  @Getter
  @Setter
  @JsonProperty("skip_company")
  public Boolean skipCompany;

  /**
  * User ID.  Provide a value of `0` to operate the current session's user.
  */
  @Getter
  @Setter
  @JsonProperty("user_id")
  public Long userId;

  /**
  * Parameters:
  *   title - string - Title to be displayed
  *   skip_email - boolean - Skip validating form email
  *   skip_name - boolean - Skip validating form name
  *   skip_company - boolean - Skip validating company
  *   form_fields - array(object)
  */
  public FormFieldSet update() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    return FormFieldSet.update(this.id, parameters);
  }

  /**
  */
  public void delete() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    FormFieldSet.delete(this.id, parameters);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete();
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
  *   action - string
  *   page - int64
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



    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }
    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }
    if (parameters.containsKey("action") && !(parameters.get("action") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: action must be of type String parameters[\"action\"]");
    }
    if (parameters.containsKey("page") && !(parameters.get("page") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: page must be of type Long parameters[\"page\"]");
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

    if (!(id instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/form_field_sets/%s", urlParts);

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

  public static FormFieldSet create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static FormFieldSet create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
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

    if (!(id instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
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


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/form_field_sets/%s", urlParts);

    TypeReference<FormFieldSet> typeReference = new TypeReference<FormFieldSet>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  */

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

    if (!(id instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/form_field_sets/%s", urlParts);

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
