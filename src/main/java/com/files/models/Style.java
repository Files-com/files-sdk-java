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
public class Style {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .build();


  public Style() {
    this(null, null);
  }

  public Style(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Style(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
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
  public Image logo;

  /**
  * Logo thumbnail
  */
  @Getter
  @Setter
  @JsonProperty("thumbnail")
  public Image thumbnail;

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
    update();
  }


  /**
  * Parameters:
  *   path (required) - string - Style path.
  */
  public static Style find() throws RuntimeException {
    return find(null, null, null);
  }

  public static Style find(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return find(path, parameters, null);
  }

  public static Style find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static Style find(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }


    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
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

    String url = String.format("%s%s/styles/%s", urlParts);

    TypeReference<Style> typeReference = new TypeReference<Style>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static Style get() throws RuntimeException {
    return get(null, null, null);
  }

  public static Style get(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(path, parameters, options);
  }

  /**
  * Parameters:
  *   file (required) - file - Logo for custom branding.
  */
  public static Style update() throws RuntimeException {
    return update(null, null, null);
  }

  public static Style update(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return update(path, parameters, null);
  }

  public static Style update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static Style update(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }


    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }
    if (parameters.containsKey("file") && !(parameters.get("file") instanceof byte[])) {
      throw new IllegalArgumentException("Bad parameter: file must be of type byte[] parameters[\"file\"]");
    }

    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }
    if (!parameters.containsKey("file") || parameters.get("file") == null) {
      throw new NullPointerException("Parameter missing: file parameters[\"file\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), path};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/styles/%s", urlParts);

    TypeReference<Style> typeReference = new TypeReference<Style>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  public static void delete(String path, HashMap<String, Object> parameters) throws RuntimeException {
    delete(path, parameters, null);
  }

  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  public static void delete(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }


    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
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

    String url = String.format("%s%s/styles/%s", urlParts);

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(path, parameters, options);
  }

}
