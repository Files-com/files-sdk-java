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
public class KeyLifecycleRule implements ModelInterface {
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


  public KeyLifecycleRule() {
    this(null, null);
  }

  public KeyLifecycleRule(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public KeyLifecycleRule(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Key Lifecycle Rule ID
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
  * Key type for which the rule will apply (gpg or ssh).
  */
  @JsonProperty("key_type")
  public String keyType;

  public String getKeyType() {
    return keyType;
  }

  public void setKeyType(String keyType) {
    this.keyType = keyType;
  }

  /**
  * Number of days of inactivity before the rule applies.
  */
  @JsonProperty("inactivity_days")
  public Long inactivityDays;

  public Long getInactivityDays() {
    return inactivityDays;
  }

  public void setInactivityDays(Long inactivityDays) {
    this.inactivityDays = inactivityDays;
  }

  /**
  * Key Lifecycle Rule name
  */
  @JsonProperty("name")
  public String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
  * Parameters:
  *   key_type - string - Key type for which the rule will apply (gpg or ssh).
  *   inactivity_days - int64 - Number of days of inactivity before the rule applies.
  *   name - string - Key Lifecycle Rule name
  */
  public KeyLifecycleRule update(HashMap<String, Object> parameters) throws IOException {
    return KeyLifecycleRule.update(this.id, parameters, this.options);
  }

  /**
  */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    KeyLifecycleRule.delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    KeyLifecycleRule.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are .
  */
  public static ListIterator<KeyLifecycleRule> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<KeyLifecycleRule> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<KeyLifecycleRule> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }
    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Map<String, String> parameters[\"sort_by\"]");
    }


    String url = String.format("%s%s/key_lifecycle_rules", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<KeyLifecycleRule>> typeReference = new TypeReference<List<KeyLifecycleRule>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<KeyLifecycleRule> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<KeyLifecycleRule> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Key Lifecycle Rule ID.
  */
  public static KeyLifecycleRule find() throws RuntimeException {
    return find(null, null, null);
  }

  public static KeyLifecycleRule find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static KeyLifecycleRule find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static KeyLifecycleRule find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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



    String url = String.format("%s%s/key_lifecycle_rules/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<KeyLifecycleRule> typeReference = new TypeReference<KeyLifecycleRule>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static KeyLifecycleRule get() throws RuntimeException {
    return get(null, null, null);
  }

  public static KeyLifecycleRule get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   key_type - string - Key type for which the rule will apply (gpg or ssh).
  *   inactivity_days - int64 - Number of days of inactivity before the rule applies.
  *   name - string - Key Lifecycle Rule name
  */
  public static KeyLifecycleRule create() throws RuntimeException {
    return create(null, null);
  }

  public static KeyLifecycleRule create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static KeyLifecycleRule create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("key_type") && !(parameters.get("key_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: key_type must be of type String parameters[\"key_type\"]");
    }
    if (parameters.containsKey("inactivity_days") && !(parameters.get("inactivity_days") instanceof Long || parameters.get("inactivity_days") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: inactivity_days must be of type Long or Integer parameters[\"inactivity_days\"]");
    }
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }


    String url = String.format("%s%s/key_lifecycle_rules", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<KeyLifecycleRule> typeReference = new TypeReference<KeyLifecycleRule>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   key_type - string - Key type for which the rule will apply (gpg or ssh).
  *   inactivity_days - int64 - Number of days of inactivity before the rule applies.
  *   name - string - Key Lifecycle Rule name
  */
  public static KeyLifecycleRule update() throws RuntimeException {
    return update(null, null, null);
  }

  public static KeyLifecycleRule update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static KeyLifecycleRule update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static KeyLifecycleRule update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
    if (parameters.containsKey("key_type") && !(parameters.get("key_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: key_type must be of type String parameters[\"key_type\"]");
    }
    if (parameters.containsKey("inactivity_days") && !(parameters.get("inactivity_days") instanceof Long || parameters.get("inactivity_days") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: inactivity_days must be of type Long or Integer parameters[\"inactivity_days\"]");
    }
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }



    String url = String.format("%s%s/key_lifecycle_rules/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<KeyLifecycleRule> typeReference = new TypeReference<KeyLifecycleRule>() {};
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



    String url = String.format("%s%s/key_lifecycle_rules/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
