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

public class Behavior {
  private HashMap<String, Object> attributes;
  private HashMap<String, Object> options;

  public Behavior() {
    this(null, null);
  }

  public Behavior(HashMap<String, Object> attributes) {
    this(attributes, null);
  }

  public Behavior(HashMap<String, Object> attributes, HashMap<String, Object> options) {
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
  * Folder behavior ID
  */
  @Getter
  @Setter
  @JsonProperty("id")
  public Long id;

  /**
  * Folder path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
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
  * Settings for this behavior.  See the section above for an example value to provide here.  Formatting is different for each Behavior type.  May be sent as nested JSON or a single JSON-encoded string.  If using XML encoding for the API call, this data must be sent as a JSON-encoded string.
  */
  @Getter
  @Setter
  @JsonProperty("value")
  public Object value;

  /**
  * Certain behaviors may require a file, for instance, the "watermark" behavior requires a watermark image
  */
  @Getter
  @Setter
  @JsonProperty("attachment_file")
  public byte[] attachmentFile;

  /**
  * Parameters:
  *   value - string - The value of the folder behavior.  Can be a integer, array, or hash depending on the type of folder behavior.
  *   attachment_file - file - Certain behaviors may require a file, for instance, the "watermark" behavior requires a watermark image
  *   behavior - string - Behavior type.
  *   path - string - Folder behaviors path.
  */
  public Behavior update(HashMap<String, Object> parameters) {
    // TODO: Fill in operation implementation
    return (Behavior) null;
  }

  /**
  */
  public Behavior delete(HashMap<String, Object> parameters) {
    // TODO: Fill in operation implementation
    return (Behavior) null;
  }

  public void destroy(HashMap<String, Object> parameters) {
    delete(parameters);
  }

  public void save() throws IOException {
    if (this.attributes.get("id") != null) {
      update(this.attributes);
    } else {
      Behavior.create(this.attributes, this.options);
      // TODO save this.attributes = newObj.attributes;
    }
  }

  /**
  * Parameters:
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  *   cursor - string - Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   sort_by - object - If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `site_id` and `behavior`.
  *   filter - object - If set, return records where the specifiied field is equal to the supplied value. Valid fields are `behavior`.
  *   filter_gt - object - If set, return records where the specifiied field is greater than the supplied value. Valid fields are `behavior`.
  *   filter_gteq - object - If set, return records where the specifiied field is greater than or equal to the supplied value. Valid fields are `behavior`.
  *   filter_like - object - If set, return records where the specifiied field is equal to the supplied value. Valid fields are `behavior`.
  *   filter_lt - object - If set, return records where the specifiied field is less than the supplied value. Valid fields are `behavior`.
  *   filter_lteq - object - If set, return records where the specifiied field is less than or equal to the supplied value. Valid fields are `behavior`.
  *   behavior - string - If set, only shows folder behaviors matching this behavior type.
  */
  public static List<Behavior> list() throws IOException{
    return list(null,null);
  }
  public static List<Behavior> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static List<Behavior> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Object parameters[\"sort_by\"]");
    }

    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Object parameters[\"filter\"]");
    }

    if (parameters.containsKey("filter_gt") && !(parameters.get("filter_gt") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: filter_gt must be of type Object parameters[\"filter_gt\"]");
    }

    if (parameters.containsKey("filter_gteq") && !(parameters.get("filter_gteq") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: filter_gteq must be of type Object parameters[\"filter_gteq\"]");
    }

    if (parameters.containsKey("filter_like") && !(parameters.get("filter_like") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: filter_like must be of type Object parameters[\"filter_like\"]");
    }

    if (parameters.containsKey("filter_lt") && !(parameters.get("filter_lt") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: filter_lt must be of type Object parameters[\"filter_lt\"]");
    }

    if (parameters.containsKey("filter_lteq") && !(parameters.get("filter_lteq") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: filter_lteq must be of type Object parameters[\"filter_lteq\"]");
    }

    if (parameters.containsKey("behavior") && !(parameters.get("behavior") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: behavior must be of type String parameters[\"behavior\"]");
    }

    String url = String.format("%s%s/behaviors", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<Behavior>> typeReference = new TypeReference<List<Behavior>>() {};
    return FilesClient.request(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<Behavior> all() throws IOException {
    return all(null, null);
  }

  public static List<Behavior> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Behavior ID.
  */
  public static List<Behavior> find() throws IOException{
    return find(null, null,null);
  }
  public static List<Behavior> find(Long id,  HashMap<String, Object> parameters) throws IOException {
    return find(id, parameters, null);
  }

  public static List<Behavior> find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static List<Behavior> find(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
    String url = String.format("%s%s/behaviors/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<List<Behavior>> typeReference = new TypeReference<List<Behavior>>() {};
    return FilesClient.request(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<Behavior> get() throws IOException {
    return get(null, null, null);
  }

  public static List<Behavior> get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  *   cursor - string - Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   sort_by - object - If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `site_id` and `behavior`.
  *   filter - object - If set, return records where the specifiied field is equal to the supplied value. Valid fields are `behavior`.
  *   filter_gt - object - If set, return records where the specifiied field is greater than the supplied value. Valid fields are `behavior`.
  *   filter_gteq - object - If set, return records where the specifiied field is greater than or equal to the supplied value. Valid fields are `behavior`.
  *   filter_like - object - If set, return records where the specifiied field is equal to the supplied value. Valid fields are `behavior`.
  *   filter_lt - object - If set, return records where the specifiied field is less than the supplied value. Valid fields are `behavior`.
  *   filter_lteq - object - If set, return records where the specifiied field is less than or equal to the supplied value. Valid fields are `behavior`.
  *   path (required) - string - Path to operate on.
  *   recursive - string - Show behaviors above this path?
  *   behavior - string - DEPRECATED: If set only shows folder behaviors matching this behavior type. Use `filter[behavior]` instead.
  */
  public static List<Behavior> listFor() throws IOException{
    return listFor(null, null,null);
  }
  public static List<Behavior> listFor(String path,  HashMap<String, Object> parameters) throws IOException {
    return listFor(path, parameters, null);
  }

  public static List<Behavior> listFor(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return listFor(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static List<Behavior> listFor(String path,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path != null){
      parameters.put("path",path);
    }
    if (parameters.containsKey("page") && !(parameters.get("page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: page must be of type Long parameters[\"page\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }

    if (parameters.containsKey("action") && !(parameters.get("action") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: action must be of type String parameters[\"action\"]");
    }

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Object parameters[\"sort_by\"]");
    }

    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Object parameters[\"filter\"]");
    }

    if (parameters.containsKey("filter_gt") && !(parameters.get("filter_gt") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: filter_gt must be of type Object parameters[\"filter_gt\"]");
    }

    if (parameters.containsKey("filter_gteq") && !(parameters.get("filter_gteq") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: filter_gteq must be of type Object parameters[\"filter_gteq\"]");
    }

    if (parameters.containsKey("filter_like") && !(parameters.get("filter_like") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: filter_like must be of type Object parameters[\"filter_like\"]");
    }

    if (parameters.containsKey("filter_lt") && !(parameters.get("filter_lt") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: filter_lt must be of type Object parameters[\"filter_lt\"]");
    }

    if (parameters.containsKey("filter_lteq") && !(parameters.get("filter_lteq") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: filter_lteq must be of type Object parameters[\"filter_lteq\"]");
    }

    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("recursive") && !(parameters.get("recursive") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: recursive must be of type String parameters[\"recursive\"]");
    }

    if (parameters.containsKey("behavior") && !(parameters.get("behavior") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: behavior must be of type String parameters[\"behavior\"]");
    }

    if (!parameters.containsKey("path") || parameters.get("path") == null) {
      throw new NullPointerException("Parameter missing: path parameters[\"path\"]");
    }
    String url = String.format("%s%s/behaviors/folders/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), path);
    TypeReference<List<Behavior>> typeReference = new TypeReference<List<Behavior>>() {};
    return FilesClient.request(url, RequestMethods.GET, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   value - string - The value of the folder behavior.  Can be a integer, array, or hash depending on the type of folder behavior.
  *   attachment_file - file - Certain behaviors may require a file, for instance, the "watermark" behavior requires a watermark image
  *   path (required) - string - Folder behaviors path.
  *   behavior (required) - string - Behavior type.
  */
  public static List<Behavior> create() throws IOException{
    return create(null,null);
  }
  public static List<Behavior> create( HashMap<String, Object> parameters) throws IOException {
    return create(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static List<Behavior> create( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("value") && !(parameters.get("value") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: value must be of type String parameters[\"value\"]");
    }

    if (parameters.containsKey("attachment_file") && !(parameters.get("attachment_file") instanceof byte[] )) {
      throw new IllegalArgumentException("Bad parameter: attachment_file must be of type byte[] parameters[\"attachment_file\"]");
    }

    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("behavior") && !(parameters.get("behavior") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: behavior must be of type String parameters[\"behavior\"]");
    }

    if (!parameters.containsKey("path") || parameters.get("path") == null) {
      throw new NullPointerException("Parameter missing: path parameters[\"path\"]");
    }
    if (!parameters.containsKey("behavior") || parameters.get("behavior") == null) {
      throw new NullPointerException("Parameter missing: behavior parameters[\"behavior\"]");
    }
    String url = String.format("%s%s/behaviors", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<Behavior>> typeReference = new TypeReference<List<Behavior>>() {};
    return FilesClient.request(url, RequestMethods.POST, typeReference, parameters, options);
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
  public static List<Behavior> webhookTest() throws IOException{
    return webhookTest(null,null);
  }
  public static List<Behavior> webhookTest( HashMap<String, Object> parameters) throws IOException {
    return webhookTest(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static List<Behavior> webhookTest( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("url") && !(parameters.get("url") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: url must be of type String parameters[\"url\"]");
    }

    if (parameters.containsKey("method") && !(parameters.get("method") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: method must be of type String parameters[\"method\"]");
    }

    if (parameters.containsKey("encoding") && !(parameters.get("encoding") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: encoding must be of type String parameters[\"encoding\"]");
    }

    if (parameters.containsKey("headers") && !(parameters.get("headers") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: headers must be of type Object parameters[\"headers\"]");
    }

    if (parameters.containsKey("body") && !(parameters.get("body") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: body must be of type Object parameters[\"body\"]");
    }

    if (parameters.containsKey("action") && !(parameters.get("action") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: action must be of type String parameters[\"action\"]");
    }

    if (!parameters.containsKey("url") || parameters.get("url") == null) {
      throw new NullPointerException("Parameter missing: url parameters[\"url\"]");
    }
    String url = String.format("%s%s/behaviors/webhook/test", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<Behavior>> typeReference = new TypeReference<List<Behavior>>() {};
    return FilesClient.request(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   value - string - The value of the folder behavior.  Can be a integer, array, or hash depending on the type of folder behavior.
  *   attachment_file - file - Certain behaviors may require a file, for instance, the "watermark" behavior requires a watermark image
  *   behavior - string - Behavior type.
  *   path - string - Folder behaviors path.
  */
  public static List<Behavior> update() throws IOException{
    return update(null, null,null);
  }
  public static List<Behavior> update(Long id,  HashMap<String, Object> parameters) throws IOException {
    return update(id, parameters, null);
  }

  public static List<Behavior> update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return update(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static List<Behavior> update(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id != null){
      parameters.put("id",id);
    }
    if (parameters.containsKey("id") && !(parameters.get("id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (parameters.containsKey("value") && !(parameters.get("value") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: value must be of type String parameters[\"value\"]");
    }

    if (parameters.containsKey("attachment_file") && !(parameters.get("attachment_file") instanceof byte[] )) {
      throw new IllegalArgumentException("Bad parameter: attachment_file must be of type byte[] parameters[\"attachment_file\"]");
    }

    if (parameters.containsKey("behavior") && !(parameters.get("behavior") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: behavior must be of type String parameters[\"behavior\"]");
    }

    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    String url = String.format("%s%s/behaviors/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<List<Behavior>> typeReference = new TypeReference<List<Behavior>>() {};
    return FilesClient.request(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  */
  public static List<Behavior> delete() throws IOException{
    return delete(null, null,null);
  }
  public static List<Behavior> delete(Long id,  HashMap<String, Object> parameters) throws IOException {
    return delete(id, parameters, null);
  }

  public static List<Behavior> delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static List<Behavior> delete(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
    String url = String.format("%s%s/behaviors/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<List<Behavior>> typeReference = new TypeReference<List<Behavior>>() {};
    return FilesClient.request(url, RequestMethods.DELETE, typeReference, parameters, options);
  }

  public static List<Behavior> destroy() throws IOException {
    return destroy(null, null, null);
  }

  public static List<Behavior> destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(id, parameters, options);
  }

}


