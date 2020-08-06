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

public class IpAddress {
  private HashMap<String, Object> attributes;
  private HashMap<String, Object> options;

  public IpAddress() {
    this(null, null);
  }

  public IpAddress(HashMap<String, Object> attributes) {
    this(attributes, null);
  }

  public IpAddress(HashMap<String, Object> attributes, HashMap<String, Object> options) {
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
  * Unique label for list; used by Zapier and other integrations.
  */
  @Getter
  @JsonProperty("id")
  public String id;

  /**
  * The object that this public IP address list is associated with.
  */
  @Getter
  @JsonProperty("associated_with")
  public String associatedWith;

  /**
  * Group ID
  */
  @Getter
  @JsonProperty("group_id")
  public Long groupId;

  /**
  * A list of IP addresses.
  */
  @Getter
  @JsonProperty("ip_addresses")
  public Object[] ipAddresses;



  /**
  * Parameters:
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  */
  public static List<IpAddress> list() throws IOException{
    return list(null,null);
  }
  public static List<IpAddress> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static List<IpAddress> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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

    String url = String.format("%s%s/ip_addresses", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<IpAddress>> typeReference = new TypeReference<List<IpAddress>>() {};
    return FilesClient.request(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<IpAddress> all() throws IOException {
    return all(null, null);
  }

  public static List<IpAddress> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  */
  public static List<IpAddress> getReserved() throws IOException{
    return getReserved(null,null);
  }
  public static List<IpAddress> getReserved( HashMap<String, Object> parameters) throws IOException {
    return getReserved(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static List<IpAddress> getReserved( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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

    String url = String.format("%s%s/ip_addresses/reserved", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<IpAddress>> typeReference = new TypeReference<List<IpAddress>>() {};
    return FilesClient.request(url, RequestMethods.GET, typeReference, parameters, options);
  }


}


