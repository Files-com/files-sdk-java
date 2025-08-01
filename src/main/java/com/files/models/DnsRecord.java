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
public class DnsRecord implements ModelInterface {
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


  public DnsRecord() {
    this(null, null);
  }

  public DnsRecord(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public DnsRecord(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Unique label for DNS record; used by Zapier and other integrations.
  */
  @JsonProperty("id")
  public String id;

  public String getId() {
    return id;
  }

  /**
  * DNS record domain name
  */
  @JsonProperty("domain")
  public String domain;

  public String getDomain() {
    return domain;
  }

  /**
  * DNS record type
  */
  @JsonProperty("rrtype")
  public String rrtype;

  public String getRrtype() {
    return rrtype;
  }

  /**
  * DNS record value
  */
  @JsonProperty("value")
  public String value;

  public String getValue() {
    return value;
  }


  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  */
  public static ListIterator<DnsRecord> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<DnsRecord> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<DnsRecord> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }


    String url = String.format("%s%s/dns_records", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<DnsRecord>> typeReference = new TypeReference<List<DnsRecord>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<DnsRecord> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<DnsRecord> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
