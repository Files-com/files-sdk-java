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
public class Behavior {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .build();


  public Behavior() {
    this(null, null);
  }

  public Behavior(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Behavior(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Folder behavior ID
  */
  @Getter
  @Setter
  @JsonProperty("id")
  public Long id;

  /**
  * Folder path.  Note that Behavior paths cannot be updated once initially set.  You will need to remove and re-create the behavior on the new path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @Getter
  @Setter
  @JsonProperty("path")
  public String path;

  /**
  * URL for attached file
  */
  @Getter
  @Setter
  @JsonProperty("attachment_url")
  public String attachmentUrl;

  /**
  * Behavior type.
  */
  @Getter
  @Setter
  @JsonProperty("behavior")
  public String behavior;

  /**
  * Name for this behavior.
  */
  @Getter
  @Setter
  @JsonProperty("name")
  public String name;

  /**
  * Description for this behavior.
  */
  @Getter
  @Setter
  @JsonProperty("description")
  public String description;

  /**
  * Settings for this behavior.  See the section above for an example value to provide here.  Formatting is different for each Behavior type.  May be sent as nested JSON or a single JSON-encoded string.  If using XML encoding for the API call, this data must be sent as a JSON-encoded string.
  */
  @Getter
  @Setter
  @JsonProperty("value")
  public Map<String, String> value;

  /**
  * Certain behaviors may require a file, for instance, the "watermark" behavior requires a watermark image
  */
  @Getter
  @Setter
  @JsonProperty("attachment_file")
  public byte[] attachmentFile;

  /**
  * If true, will delete the file stored in attachment
  */
  @Getter
  @Setter
  @JsonProperty("attachment_delete")
  public Boolean attachmentDelete;

  /**
  * Parameters:
  *   value - string - The value of the folder behavior.  Can be a integer, array, or hash depending on the type of folder behavior. See The Behavior Types section for example values for each type of behavior.
  *   attachment_file - file - Certain behaviors may require a file, for instance, the "watermark" behavior requires a watermark image
  *   name - string - Name for this behavior.
  *   description - string - Description for this behavior.
  *   behavior - string - Behavior type.
  *   path - string - Folder behaviors path.
  *   attachment_delete - boolean - If true, will delete the file stored in attachment
  */
  public Behavior update(HashMap<String, Object> parameters) {
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
      Behavior newObject = Behavior.create(parameters, this.options);
    }
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction (e.g. `sort_by[behavior]=desc`). Valid fields are `behavior`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `behavior`.
  *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `behavior`.
  */
  public static ListIterator<Behavior> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<Behavior> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<Behavior> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }
    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Map<String, String> parameters[\"sort_by\"]");
    }
    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Map<String, String> parameters[\"filter\"]");
    }
    if (parameters.containsKey("filter_prefix") && !(parameters.get("filter_prefix") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_prefix must be of type Map<String, String> parameters[\"filter_prefix\"]");
    }



    String url = String.format("%s%s/behaviors", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<Behavior>> typeReference = new TypeReference<List<Behavior>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<Behavior> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<Behavior> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Behavior ID.
  */
  public static Behavior find() throws RuntimeException {
    return find(null, null, null);
  }

  public static Behavior find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static Behavior find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static Behavior find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String url = String.format("%s%s/behaviors/%s", urlParts);

    TypeReference<Behavior> typeReference = new TypeReference<Behavior>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static Behavior get() throws RuntimeException {
    return get(null, null, null);
  }

  public static Behavior get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction (e.g. `sort_by[behavior]=desc`). Valid fields are `behavior`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `behavior`.
  *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `behavior`.
  *   path (required) - string - Path to operate on.
  *   recursive - string - Show behaviors above this path?
  *   behavior - string - DEPRECATED: If set only shows folder behaviors matching this behavior type. Use `filter[behavior]` instead.
  */
  public static ListIterator<Behavior> listFor() throws RuntimeException {
    return listFor(null, null, null);
  }

  public static ListIterator<Behavior> listFor(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return listFor(path, parameters, null);
  }

  public static ListIterator<Behavior> listFor(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return listFor(null, parameters, options);
  }

  public static ListIterator<Behavior> listFor(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }


    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }
    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Map<String, String> parameters[\"sort_by\"]");
    }
    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Map<String, String> parameters[\"filter\"]");
    }
    if (parameters.containsKey("filter_prefix") && !(parameters.get("filter_prefix") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_prefix must be of type Map<String, String> parameters[\"filter_prefix\"]");
    }
    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }
    if (parameters.containsKey("recursive") && !(parameters.get("recursive") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: recursive must be of type String parameters[\"recursive\"]");
    }
    if (parameters.containsKey("behavior") && !(parameters.get("behavior") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: behavior must be of type String parameters[\"behavior\"]");
    }

    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), path};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/behaviors/folders/%s", urlParts);

    TypeReference<List<Behavior>> typeReference = new TypeReference<List<Behavior>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   value - string - The value of the folder behavior.  Can be a integer, array, or hash depending on the type of folder behavior. See The Behavior Types section for example values for each type of behavior.
  *   attachment_file - file - Certain behaviors may require a file, for instance, the "watermark" behavior requires a watermark image
  *   name - string - Name for this behavior.
  *   description - string - Description for this behavior.
  *   path (required) - string - Folder behaviors path.
  *   behavior (required) - string - Behavior type.
  */
  public static Behavior create() throws RuntimeException {
    return create(null, null);
  }

  public static Behavior create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static Behavior create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (parameters.containsKey("value") && !(parameters.get("value") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: value must be of type String parameters[\"value\"]");
    }
    if (parameters.containsKey("attachment_file") && !(parameters.get("attachment_file") instanceof byte[])) {
      throw new IllegalArgumentException("Bad parameter: attachment_file must be of type byte[] parameters[\"attachment_file\"]");
    }
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }
    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }
    if (parameters.containsKey("behavior") && !(parameters.get("behavior") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: behavior must be of type String parameters[\"behavior\"]");
    }

    if (!parameters.containsKey("path") || parameters.get("path") == null) {
      throw new NullPointerException("Parameter missing: path parameters[\"path\"]");
    }
    if (!parameters.containsKey("behavior") || parameters.get("behavior") == null) {
      throw new NullPointerException("Parameter missing: behavior parameters[\"behavior\"]");
    }


    String url = String.format("%s%s/behaviors", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<Behavior> typeReference = new TypeReference<Behavior>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   url (required) - string - URL for testing the webhook.
  *   method - string - HTTP method(GET or POST).
  *   encoding - string - HTTP encoding method.  Can be JSON, XML, or RAW (form data).
  *   headers - object - Additional request headers.
  *   body - object - Additional body parameters.
  *   action - string - action for test body
  */
  public static Behavior webhookTest() throws RuntimeException {
    return webhookTest(null, null);
  }

  public static Behavior webhookTest(HashMap<String, Object> parameters) throws RuntimeException {
    return webhookTest(parameters, null);
  }


  public static Behavior webhookTest(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (parameters.containsKey("url") && !(parameters.get("url") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: url must be of type String parameters[\"url\"]");
    }
    if (parameters.containsKey("method") && !(parameters.get("method") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: method must be of type String parameters[\"method\"]");
    }
    if (parameters.containsKey("encoding") && !(parameters.get("encoding") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: encoding must be of type String parameters[\"encoding\"]");
    }
    if (parameters.containsKey("headers") && !(parameters.get("headers") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: headers must be of type Map<String, String> parameters[\"headers\"]");
    }
    if (parameters.containsKey("body") && !(parameters.get("body") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: body must be of type Map<String, String> parameters[\"body\"]");
    }
    if (parameters.containsKey("action") && !(parameters.get("action") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: action must be of type String parameters[\"action\"]");
    }

    if (!parameters.containsKey("url") || parameters.get("url") == null) {
      throw new NullPointerException("Parameter missing: url parameters[\"url\"]");
    }


    String url = String.format("%s%s/behaviors/webhook/test", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<Behavior> typeReference = new TypeReference<Behavior>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   value - string - The value of the folder behavior.  Can be a integer, array, or hash depending on the type of folder behavior. See The Behavior Types section for example values for each type of behavior.
  *   attachment_file - file - Certain behaviors may require a file, for instance, the "watermark" behavior requires a watermark image
  *   name - string - Name for this behavior.
  *   description - string - Description for this behavior.
  *   behavior - string - Behavior type.
  *   path - string - Folder behaviors path.
  *   attachment_delete - boolean - If true, will delete the file stored in attachment
  */
  public static Behavior update() throws RuntimeException {
    return update(null, null, null);
  }

  public static Behavior update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static Behavior update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static Behavior update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (!(id instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }
    if (parameters.containsKey("value") && !(parameters.get("value") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: value must be of type String parameters[\"value\"]");
    }
    if (parameters.containsKey("attachment_file") && !(parameters.get("attachment_file") instanceof byte[])) {
      throw new IllegalArgumentException("Bad parameter: attachment_file must be of type byte[] parameters[\"attachment_file\"]");
    }
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }
    if (parameters.containsKey("behavior") && !(parameters.get("behavior") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: behavior must be of type String parameters[\"behavior\"]");
    }
    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }
    if (parameters.containsKey("attachment_delete") && !(parameters.get("attachment_delete") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: attachment_delete must be of type Boolean parameters[\"attachment_delete\"]");
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

    String url = String.format("%s%s/behaviors/%s", urlParts);

    TypeReference<Behavior> typeReference = new TypeReference<Behavior>() {};
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

    String url = String.format("%s%s/behaviors/%s", urlParts);

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
