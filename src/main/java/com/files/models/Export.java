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
public class Export implements ModelInterface {
  @Setter
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();


  public Export() {
    this(null, null);
  }

  public Export(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Export(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * ID for this Export
  */
  @Getter
  @Setter
  @JsonProperty("id")
  public Long id;

  /**
  * Status of the Export
  */
  @Getter
  @Setter
  @JsonProperty("export_status")
  public String exportStatus;

  /**
  * Type of data being exported
  */
  @Getter
  @Setter
  @JsonProperty("export_type")
  public String exportType;

  /**
  * Number of rows exported
  */
  @Getter
  @Setter
  @JsonProperty("export_rows")
  public Long exportRows;

  /**
  * Link to download Export file.
  */
  @Getter
  @Setter
  @JsonProperty("download_uri")
  public String downloadUri;

  /**
  * Export message
  */
  @Getter
  @Setter
  @JsonProperty("message")
  public String message;

  /**
  * User ID.  Provide a value of `0` to operate the current session's user.
  */
  @Getter
  @Setter
  @JsonProperty("user_id")
  public Long userId;

  /**
  * If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `export_status` and `export_type`.
  */
  @Getter
  @Setter
  @JsonProperty("sort_by")
  public Map<String, String> sortBy;

  /**
  * If set, return records where the specified field is equal to the supplied value. Valid fields are `export_status` and `export_type`.
  */
  @Getter
  @Setter
  @JsonProperty("filter")
  public Map<String, String> filter;

  /**
  * If set, return records where the specified field is prefixed by the supplied value. Valid fields are `export_type`.
  */
  @Getter
  @Setter
  @JsonProperty("filter_prefix")
  public Map<String, String> filterPrefix;


  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    Export.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `export_status` and `export_type`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `export_status` and `export_type`.
  *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `export_type`.
  */
  public static ListIterator<Export> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<Export> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<Export> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Map<String, String> parameters[\"sort_by\"]");
    }
    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Map<String, String> parameters[\"filter\"]");
    }
    if (parameters.containsKey("filter_prefix") && !(parameters.get("filter_prefix") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_prefix must be of type Map<String, String> parameters[\"filter_prefix\"]");
    }


    String url = String.format("%s%s/exports", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<Export>> typeReference = new TypeReference<List<Export>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<Export> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<Export> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Export ID.
  */

  public static Export find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static Export find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static Export find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String url = String.format("%s%s/exports/%s", urlParts);

    TypeReference<Export> typeReference = new TypeReference<Export>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static Export get() throws RuntimeException {
    return get(null, null, null);
  }

  public static Export get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `export_status` and `export_type`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `export_status` and `export_type`.
  *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `export_type`.
  */
  public static Export create() throws RuntimeException {
    return create(null, null);
  }

  public static Export create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static Export create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
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


    String url = String.format("%s%s/exports/create_export", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<Export> typeReference = new TypeReference<Export>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


}
