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

public class BundleDownload {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public BundleDownload() {
    this(null, null);
  }

  public BundleDownload(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public BundleDownload(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  */
  @Getter
  @JsonProperty("bundle_registration")
  private Map<String, String> bundleRegistration;

  /**
  * Download method (file or full_zip)
  */
  @Getter
  @JsonProperty("download_method")
  private String downloadMethod;

  /**
  * Download path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @Getter
  @JsonProperty("path")
  private String path;

  /**
  * Download date/time
  */
  @Getter
  @JsonProperty("created_at")
  private Date createdAt;



  /**
  * Parameters:
  *   cursor - string - Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `created_at`.
  *   filter - object - If set, return records where the specifiied field is equal to the supplied value. Valid fields are `created_at`.
  *   filter_gt - object - If set, return records where the specifiied field is greater than the supplied value. Valid fields are `created_at`.
  *   filter_gteq - object - If set, return records where the specifiied field is greater than or equal to the supplied value. Valid fields are `created_at`.
  *   filter_like - object - If set, return records where the specifiied field is equal to the supplied value. Valid fields are `created_at`.
  *   filter_lt - object - If set, return records where the specifiied field is less than the supplied value. Valid fields are `created_at`.
  *   filter_lteq - object - If set, return records where the specifiied field is less than or equal to the supplied value. Valid fields are `created_at`.
  *   bundle_id - int64 - Bundle ID
  *   bundle_registration_id - int64 - BundleRegistration ID
  */
  public static List<BundleDownload> list() throws IOException{
    return list(null,null);
  }
  public static List<BundleDownload> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  public static List<BundleDownload> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }

    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Map<String, String> parameters[\"sort_by\"]");
    }

    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Map<String, String> parameters[\"filter\"]");
    }

    if (parameters.containsKey("filter_gt") && !(parameters.get("filter_gt") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter_gt must be of type Map<String, String> parameters[\"filter_gt\"]");
    }

    if (parameters.containsKey("filter_gteq") && !(parameters.get("filter_gteq") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter_gteq must be of type Map<String, String> parameters[\"filter_gteq\"]");
    }

    if (parameters.containsKey("filter_like") && !(parameters.get("filter_like") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter_like must be of type Map<String, String> parameters[\"filter_like\"]");
    }

    if (parameters.containsKey("filter_lt") && !(parameters.get("filter_lt") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter_lt must be of type Map<String, String> parameters[\"filter_lt\"]");
    }

    if (parameters.containsKey("filter_lteq") && !(parameters.get("filter_lteq") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter_lteq must be of type Map<String, String> parameters[\"filter_lteq\"]");
    }

    if (parameters.containsKey("bundle_id") && !(parameters.get("bundle_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: bundle_id must be of type Long parameters[\"bundle_id\"]");
    }

    if (parameters.containsKey("bundle_registration_id") && !(parameters.get("bundle_registration_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: bundle_registration_id must be of type Long parameters[\"bundle_registration_id\"]");
    }

    String url = String.format("%s%s/bundle_downloads", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<BundleDownload>> typeReference = new TypeReference<List<BundleDownload>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<BundleDownload> all() throws IOException {
    return all(null, null);
  }

  public static List<BundleDownload> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

}


