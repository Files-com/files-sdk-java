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
public class BandwidthSnapshot implements ModelInterface {
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


  public BandwidthSnapshot() {
    this(null, null);
  }

  public BandwidthSnapshot(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public BandwidthSnapshot(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Site bandwidth ID
  */
  @JsonProperty("id")
  public Long id;

  public Long getId() {
    return id;
  }

  /**
  * Site bandwidth report bytes received
  */
  @JsonProperty("bytes_received")
  public Double bytesReceived;

  public Double getBytesReceived() {
    return bytesReceived;
  }

  /**
  * Site bandwidth report bytes sent
  */
  @JsonProperty("bytes_sent")
  public Double bytesSent;

  public Double getBytesSent() {
    return bytesSent;
  }

  /**
  * Site sync bandwidth report bytes received
  */
  @JsonProperty("sync_bytes_received")
  public Double syncBytesReceived;

  public Double getSyncBytesReceived() {
    return syncBytesReceived;
  }

  /**
  * Site sync bandwidth report bytes sent
  */
  @JsonProperty("sync_bytes_sent")
  public Double syncBytesSent;

  public Double getSyncBytesSent() {
    return syncBytesSent;
  }

  /**
  * Site bandwidth report get requests
  */
  @JsonProperty("requests_get")
  public Double requestsGet;

  public Double getRequestsGet() {
    return requestsGet;
  }

  /**
  * Site bandwidth report put requests
  */
  @JsonProperty("requests_put")
  public Double requestsPut;

  public Double getRequestsPut() {
    return requestsPut;
  }

  /**
  * Site bandwidth report other requests
  */
  @JsonProperty("requests_other")
  public Double requestsOther;

  public Double getRequestsOther() {
    return requestsOther;
  }

  /**
  * Time the site bandwidth report was logged
  */
  @JsonProperty("logged_at")
  public Date loggedAt;

  public Date getLoggedAt() {
    return loggedAt;
  }


  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `logged_at`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `logged_at`.
  *   filter_gt - object - If set, return records where the specified field is greater than the supplied value. Valid fields are `logged_at`.
  *   filter_gteq - object - If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `logged_at`.
  *   filter_lt - object - If set, return records where the specified field is less than the supplied value. Valid fields are `logged_at`.
  *   filter_lteq - object - If set, return records where the specified field is less than or equal the supplied value. Valid fields are `logged_at`.
  */
  public static ListIterator<BandwidthSnapshot> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<BandwidthSnapshot> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<BandwidthSnapshot> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Map<String, String> parameters[\"filter\"]");
    }
    if (parameters.containsKey("filter_gt") && !(parameters.get("filter_gt") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_gt must be of type Map<String, String> parameters[\"filter_gt\"]");
    }
    if (parameters.containsKey("filter_gteq") && !(parameters.get("filter_gteq") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_gteq must be of type Map<String, String> parameters[\"filter_gteq\"]");
    }
    if (parameters.containsKey("filter_lt") && !(parameters.get("filter_lt") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_lt must be of type Map<String, String> parameters[\"filter_lt\"]");
    }
    if (parameters.containsKey("filter_lteq") && !(parameters.get("filter_lteq") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_lteq must be of type Map<String, String> parameters[\"filter_lteq\"]");
    }


    String url = String.format("%s%s/bandwidth_snapshots", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<BandwidthSnapshot>> typeReference = new TypeReference<List<BandwidthSnapshot>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<BandwidthSnapshot> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<BandwidthSnapshot> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
