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

public class As2IncomingMessage {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public As2IncomingMessage() {
    this(null, null);
  }

  public As2IncomingMessage(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public As2IncomingMessage(HashMap<String, Object> parameters, HashMap<String, Object> options) {
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
  * Id of the AS2 Station associated with this message.
  */
  @Getter
  @JsonProperty("as2_station_id")
  private Long as2StationId;

  /**
  * UUID assigned to this message.
  */
  @Getter
  @JsonProperty("uuid")
  private String uuid;

  /**
  * Content Type header of the incoming message.
  */
  @Getter
  @JsonProperty("content_type")
  private String contentType;

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
  * Result of processing.
  */
  @Getter
  @JsonProperty("processing_result")
  private String processingResult;

  /**
  * Result of processing description.
  */
  @Getter
  @JsonProperty("processing_result_description")
  private String processingResultDescription;

  /**
  * AS2 Message Integrity Check
  */
  @Getter
  @JsonProperty("mic")
  private String mic;

  /**
  * AS2 Message Integrity Check Algorithm Used
  */
  @Getter
  @JsonProperty("mic_algo")
  private String micAlgo;

  /**
  * AS2 TO header of message
  */
  @Getter
  @JsonProperty("as2_to")
  private String as2To;

  /**
  * AS2 FROM header of message
  */
  @Getter
  @JsonProperty("as2_from")
  private String as2From;

  /**
  * AS2 Message Id
  */
  @Getter
  @JsonProperty("message_id")
  private String messageId;

  /**
  * AS2 Subject Header
  */
  @Getter
  @JsonProperty("subject")
  private String subject;

  /**
  * Date Header
  */
  @Getter
  @JsonProperty("date")
  private String date;

  /**
  * Encrypted Payload Body Size
  */
  @Getter
  @JsonProperty("body_size")
  private String bodySize;

  /**
  * Filename of the file being received.
  */
  @Getter
  @JsonProperty("attachment_filename")
  private String attachmentFilename;

  /**
  * IP Address of the Sender
  */
  @Getter
  @JsonProperty("ip")
  private String ip;

  /**
  * Message creation date/time
  */
  @Getter
  @JsonProperty("created_at")
  private Date createdAt;

  /**
  * HTTP Response Code sent for this message
  */
  @Getter
  @JsonProperty("http_response_code")
  private String httpResponseCode;

  /**
  * HTTP Headers sent for this message.
  */
  @Getter
  @JsonProperty("http_response_headers")
  private Map<String, String> httpResponseHeaders;

  /**
  * Incoming Message Recipient(the Client Cert used to encrypt this message)'s serial
  */
  @Getter
  @JsonProperty("recipient_serial")
  private String recipientSerial;

  /**
  * Incoming Message Recipient(the Client Cert used to encrypt this message)'s serial in hex format.
  */
  @Getter
  @JsonProperty("hex_recipient_serial")
  private String hexRecipientSerial;

  /**
  * Incoming Message Recipient(the Client Cert used to encrypt this message)'s issuer
  */
  @Getter
  @JsonProperty("recipient_issuer")
  private String recipientIssuer;

  /**
  * Message body received?
  */
  @Getter
  @JsonProperty("message_received")
  private Boolean messageReceived;

  /**
  * Message decrypted successfully?
  */
  @Getter
  @JsonProperty("message_decrypted")
  private Boolean messageDecrypted;

  /**
  * Message signature verified?
  */
  @Getter
  @JsonProperty("message_signature_verified")
  private Boolean messageSignatureVerified;

  /**
  * Message processed successfully?
  */
  @Getter
  @JsonProperty("message_processing_success")
  private Boolean messageProcessingSuccess;

  /**
  * MDN returned?
  */
  @Getter
  @JsonProperty("message_mdn_returned")
  private Boolean messageMdnReturned;

  /**
  * URL to download the encrypted signed smime that is to sent as AS2 body
  */
  @Getter
  @JsonProperty("encrypted_uri")
  private String encryptedUri;

  /**
  * URL to download the file contents as smime with signature
  */
  @Getter
  @JsonProperty("smime_signed_uri")
  private String smimeSignedUri;

  /**
  * URL to download the file contents encoded as smime
  */
  @Getter
  @JsonProperty("smime_uri")
  private String smimeUri;

  /**
  * URL to download the original file contents
  */
  @Getter
  @JsonProperty("raw_uri")
  private String rawUri;

  /**
  * URL to download the http response body
  */
  @Getter
  @JsonProperty("mdn_response_uri")
  private String mdnResponseUri;



  /**
  * Parameters:
  *   cursor - string - Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via either the X-Files-Cursor-Next header or the X-Files-Cursor-Prev header.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `created_at` and `as2_partner_id`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`.
  *   filter_gt - object - If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`.
  *   filter_gteq - object - If set, return records where the specified field is greater than or equal to the supplied value. Valid fields are `created_at`.
  *   filter_like - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`.
  *   filter_lt - object - If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`.
  *   filter_lteq - object - If set, return records where the specified field is less than or equal to the supplied value. Valid fields are `created_at`.
  *   as2_partner_id - int64 - As2 Partner ID.  If provided, will return message specific to that partner.
  */
  public static List<As2IncomingMessage> list() throws IOException{
    return list(null,null);
  }
  public static List<As2IncomingMessage> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  public static List<As2IncomingMessage> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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

    if (parameters.containsKey("as2_partner_id") && !(parameters.get("as2_partner_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: as2_partner_id must be of type Long parameters[\"as2_partner_id\"]");
    }

    String url = String.format("%s%s/as2_incoming_messages", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<As2IncomingMessage>> typeReference = new TypeReference<List<As2IncomingMessage>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<As2IncomingMessage> all() throws IOException {
    return all(null, null);
  }

  public static List<As2IncomingMessage> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

}


