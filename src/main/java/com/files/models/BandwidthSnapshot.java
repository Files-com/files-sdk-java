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

public class BandwidthSnapshot {
  private HashMap<String, Object> attributes;
  private HashMap<String, Object> options;

  public BandwidthSnapshot() {
    this(null, null);
  }

  public BandwidthSnapshot(HashMap<String, Object> attributes) {
    this(attributes, null);
  }

  public BandwidthSnapshot(HashMap<String, Object> attributes, HashMap<String, Object> options) {
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
  * Site bandwidth ID
  */
  @Getter
  @JsonProperty("id")
  public Long id;

  /**
  * Site bandwidth report bytes received
  */
  @Getter
  @JsonProperty("bytes_received")
  public Double bytesReceived;

  /**
  * Site bandwidth report bytes sent
  */
  @Getter
  @JsonProperty("bytes_sent")
  public Double bytesSent;

  /**
  * Site bandwidth report get requests
  */
  @Getter
  @JsonProperty("requests_get")
  public Double requestsGet;

  /**
  * Site bandwidth report put requests
  */
  @Getter
  @JsonProperty("requests_put")
  public Double requestsPut;

  /**
  * Site bandwidth report other requests
  */
  @Getter
  @JsonProperty("requests_other")
  public Double requestsOther;

  /**
  * Time the site bandwidth report was logged
  */
  @Getter
  @JsonProperty("logged_at")
  public Date loggedAt;

  /**
  * Site bandwidth report created at date/time
  */
  @Getter
  @JsonProperty("created_at")
  public Date createdAt;

  /**
  * The last time this site bandwidth report was updated
  */
  @Getter
  @JsonProperty("updated_at")
  public Date updatedAt;



  /**
  * Parameters:
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  */
  public static List<BandwidthSnapshot> list() throws IOException{
    return list(null,null);
  }
  public static List<BandwidthSnapshot> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static List<BandwidthSnapshot> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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

    String url = String.format("%s%s/bandwidth_snapshots", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<BandwidthSnapshot>> typeReference = new TypeReference<List<BandwidthSnapshot>>() {};
    return FilesClient.request(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<BandwidthSnapshot> all() throws IOException {
    return all(null, null);
  }

  public static List<BandwidthSnapshot> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

}


