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
public class EmailLog {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();


  public EmailLog() {
    this(null, null);
  }

  public EmailLog(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public EmailLog(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Start Time of Action
  */
  @Getter
  @JsonProperty("timestamp")
  public Date timestamp;

  /**
  * Log Message
  */
  @Getter
  @JsonProperty("message")
  public String message;

  /**
  * Status of E-Mail delivery
  */
  @Getter
  @JsonProperty("status")
  public String status;

  /**
  * Subject line of E-Mail
  */
  @Getter
  @JsonProperty("subject")
  public String subject;

  /**
  * To field of E-Mail
  */
  @Getter
  @JsonProperty("to")
  public String to;

  /**
  * CC field of E-Mail
  */
  @Getter
  @JsonProperty("cc")
  public String cc;

  /**
  * How was email deliered?  `customer_smtp` or `files.com`
  */
  @Getter
  @JsonProperty("delivery_method")
  public String deliveryMethod;

  /**
  * Customer SMTP Hostname used.
  */
  @Getter
  @JsonProperty("smtp_hostname")
  public String smtpHostname;

  /**
  * Customer SMTP IP address as resolved for use (useful for troubleshooting DNS issues with customer SMTP).
  */
  @Getter
  @JsonProperty("smtp_ip")
  public String smtpIp;



  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `start_date`, `end_date` or `status`. Valid field combinations are `[ start_date ]`, `[ end_date ]`, `[ status ]`, `[ start_date, end_date ]`, `[ start_date, status ]` or `[ end_date, status ]`.
  *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `status`. Valid field combinations are `[ start_date ]`, `[ end_date ]`, `[ status ]`, `[ start_date, end_date ]`, `[ start_date, status ]` or `[ end_date, status ]`.
  */
  public static ListIterator<EmailLog> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<EmailLog> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<EmailLog> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }
    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Map<String, String> parameters[\"filter\"]");
    }
    if (parameters.containsKey("filter_prefix") && !(parameters.get("filter_prefix") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_prefix must be of type Map<String, String> parameters[\"filter_prefix\"]");
    }


    String url = String.format("%s%s/email_logs", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<EmailLog>> typeReference = new TypeReference<List<EmailLog>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<EmailLog> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<EmailLog> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
