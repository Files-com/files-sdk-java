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

public class DnsRecord {
  private HashMap<String, Object> attributes;
  private HashMap<String, Object> options;

  public DnsRecord() {
    this(null, null);
  }

  public DnsRecord(HashMap<String, Object> attributes) {
    this(attributes, null);
  }

  public DnsRecord(HashMap<String, Object> attributes, HashMap<String, Object> options) {
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
  * Unique label for DNS record; used by Zapier and other integrations.
  */
  @Getter
  @JsonProperty("id")
  public String id;

  /**
  * DNS record domain name
  */
  @Getter
  @JsonProperty("domain")
  public String domain;

  /**
  * DNS record type
  */
  @Getter
  @JsonProperty("rrtype")
  public String rrtype;

  /**
  * DNS record value
  */
  @Getter
  @JsonProperty("value")
  public String value;



  /**
  * Parameters:
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  */
  public static List<DnsRecord> list() throws IOException{
    return list(null,null);
  }
  public static List<DnsRecord> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static List<DnsRecord> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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

    String url = String.format("%s%s/dns_records", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<DnsRecord>> typeReference = new TypeReference<List<DnsRecord>>() {};
    return FilesClient.request(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<DnsRecord> all() throws IOException {
    return all(null, null);
  }

  public static List<DnsRecord> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

}


