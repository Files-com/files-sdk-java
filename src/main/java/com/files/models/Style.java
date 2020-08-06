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

public class Style {
  private HashMap<String, Object> attributes;
  private HashMap<String, Object> options;

  public Style() {
    this(null, null);
  }

  public Style(HashMap<String, Object> attributes) {
    this(attributes, null);
  }

  public Style(HashMap<String, Object> attributes, HashMap<String, Object> options) {
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
  * Style ID
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
  * Logo
  */
  @Getter
  @Setter
  @JsonProperty("logo")
  public Object logo;

  /**
  * Logo thumbnail
  */
  @Getter
  @Setter
  @JsonProperty("thumbnail")
  public Object thumbnail;

  /**
  * Logo for custom branding.
  */
  @Getter
  @Setter
  @JsonProperty("file")
  public byte[] file;

  /**
  * Parameters:
  *   file (required) - file - Logo for custom branding.
  */
  public Style update(HashMap<String, Object> parameters) {
    // TODO: Fill in operation implementation
    return (Style) null;
  }

  /**
  */
  public Style delete(HashMap<String, Object> parameters) {
    // TODO: Fill in operation implementation
    return (Style) null;
  }

  public void destroy(HashMap<String, Object> parameters) {
    delete(parameters);
  }

  public void save() throws IOException {
    update(this.attributes);
  }


  /**
  * Parameters:
  *   path (required) - string - Style path.
  */
  public static List<Style> find() throws IOException{
    return find(null, null,null);
  }
  public static List<Style> find(String path,  HashMap<String, Object> parameters) throws IOException {
    return find(path, parameters, null);
  }

  public static List<Style> find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static List<Style> find(String path,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path != null){
      parameters.put("path",path);
    }
    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (!parameters.containsKey("path") || parameters.get("path") == null) {
      throw new NullPointerException("Parameter missing: path parameters[\"path\"]");
    }
    String url = String.format("%s%s/styles/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), path);
    TypeReference<List<Style>> typeReference = new TypeReference<List<Style>>() {};
    return FilesClient.request(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<Style> get() throws IOException {
    return get(null, null, null);
  }

  public static List<Style> get(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(path, parameters, options);
  }

  /**
  * Parameters:
  *   file (required) - file - Logo for custom branding.
  */
  public static List<Style> update() throws IOException{
    return update(null, null,null);
  }
  public static List<Style> update(String path,  HashMap<String, Object> parameters) throws IOException {
    return update(path, parameters, null);
  }

  public static List<Style> update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return update(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static List<Style> update(String path,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path != null){
      parameters.put("path",path);
    }
    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("file") && !(parameters.get("file") instanceof byte[] )) {
      throw new IllegalArgumentException("Bad parameter: file must be of type byte[] parameters[\"file\"]");
    }

    if (!parameters.containsKey("path") || parameters.get("path") == null) {
      throw new NullPointerException("Parameter missing: path parameters[\"path\"]");
    }
    if (!parameters.containsKey("file") || parameters.get("file") == null) {
      throw new NullPointerException("Parameter missing: file parameters[\"file\"]");
    }
    String url = String.format("%s%s/styles/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), path);
    TypeReference<List<Style>> typeReference = new TypeReference<List<Style>>() {};
    return FilesClient.request(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  */
  public static List<Style> delete() throws IOException{
    return delete(null, null,null);
  }
  public static List<Style> delete(String path,  HashMap<String, Object> parameters) throws IOException {
    return delete(path, parameters, null);
  }

  public static List<Style> delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static List<Style> delete(String path,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path != null){
      parameters.put("path",path);
    }
    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (!parameters.containsKey("path") || parameters.get("path") == null) {
      throw new NullPointerException("Parameter missing: path parameters[\"path\"]");
    }
    String url = String.format("%s%s/styles/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), path);
    TypeReference<List<Style>> typeReference = new TypeReference<List<Style>>() {};
    return FilesClient.request(url, RequestMethods.DELETE, typeReference, parameters, options);
  }

  public static List<Style> destroy() throws IOException {
    return destroy(null, null, null);
  }

  public static List<Style> destroy(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(path, parameters, options);
  }

}


