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

public class As2OutgoingMessage {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public As2OutgoingMessage() {
    this(null, null);
  }

  public As2OutgoingMessage(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public As2OutgoingMessage(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Id of the AS2 Partner.
  */
  @Getter
  @JsonProperty("id")
  private Long id;

  /**
  * Id of the AS2 Partner associated with this message.
  */
  @Getter
  @JsonProperty("as2_partner_id")
  private Long as2PartnerId;

  /**
  * UUID assigned to this message.
  */
  @Getter
  @JsonProperty("uuid")
  private String uuid;

  /**
  * HTTP Headers sent with this message.
  */
  @Getter
  @JsonProperty("http_headers")
  private Map<String, String> httpHeaders;

  /**
  * JSON Structure of the activity log.
  */
  @Getter
  @JsonProperty("activity_log")
  private String activityLog;

  /**
  * Result of processing. Valid values: `send_failed`, `send_success`
  */
  @Getter
  @JsonProperty("processing_result")
  private String processingResult;

  /**
  * AS2 Message Integrity Check
  */
  @Getter
  @JsonProperty("mic")
  private String mic;

  /**
  * AS2 Message Id
  */
  @Getter
  @JsonProperty("message_id")
  private String messageId;

  /**
  * Encrypted Payload Body Size
  */
  @Getter
  @JsonProperty("body_size")
  private String bodySize;

  /**
  * Filename of the file being sent.
  */
  @Getter
  @JsonProperty("attachment_filename")
  private String attachmentFilename;

  /**
  * Message creation date/time
  */
  @Getter
  @JsonProperty("created_at")
  private Date createdAt;



  /**
  * Parameters:
  *   cursor - string - Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via either the X-Files-Cursor-Next header or the X-Files-Cursor-Prev header.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   as2_partner_id - int64 - As2 Partner ID.  If provided, will return message specific to that partner.
  */
  public static List<As2OutgoingMessage> list() throws IOException{
    return list(null,null);
  }
  public static List<As2OutgoingMessage> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  public static List<As2OutgoingMessage> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }

    if (parameters.containsKey("as2_partner_id") && !(parameters.get("as2_partner_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: as2_partner_id must be of type Long parameters[\"as2_partner_id\"]");
    }

    String url = String.format("%s%s/as2_outgoing_messages", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<As2OutgoingMessage>> typeReference = new TypeReference<List<As2OutgoingMessage>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<As2OutgoingMessage> all() throws IOException {
    return all(null, null);
  }

  public static List<As2OutgoingMessage> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

}


