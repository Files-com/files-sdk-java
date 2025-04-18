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
public class As2IncomingMessage implements ModelInterface {
  @Setter
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();


  public As2IncomingMessage() {
    this(null, null);
  }

  public As2IncomingMessage(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public As2IncomingMessage(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Id of the AS2 Partner.
  */
  @Getter
  @JsonProperty("id")
  public Long id;

  /**
  * Id of the AS2 Partner associated with this message.
  */
  @Getter
  @JsonProperty("as2_partner_id")
  public Long as2PartnerId;

  /**
  * Id of the AS2 Station associated with this message.
  */
  @Getter
  @JsonProperty("as2_station_id")
  public Long as2StationId;

  /**
  * UUID assigned to this message.
  */
  @Getter
  @JsonProperty("uuid")
  public String uuid;

  /**
  * Content Type header of the incoming message.
  */
  @Getter
  @JsonProperty("content_type")
  public String contentType;

  /**
  * HTTP Headers sent with this message.
  */
  @Getter
  @JsonProperty("http_headers")
  public Map<String, String> httpHeaders;

  /**
  * Result of processing.
  */
  @Getter
  @JsonProperty("processing_result")
  public String processingResult;

  /**
  * Result of processing description.
  */
  @Getter
  @JsonProperty("processing_result_description")
  public String processingResultDescription;

  /**
  * AS2 Message Integrity Check
  */
  @Getter
  @JsonProperty("mic")
  public String mic;

  /**
  * AS2 Message Integrity Check Algorithm Used
  */
  @Getter
  @JsonProperty("mic_algo")
  public String micAlgo;

  /**
  * AS2 TO header of message
  */
  @Getter
  @JsonProperty("as2_to")
  public String as2To;

  /**
  * AS2 FROM header of message
  */
  @Getter
  @JsonProperty("as2_from")
  public String as2From;

  /**
  * AS2 Message Id
  */
  @Getter
  @JsonProperty("message_id")
  public String messageId;

  /**
  * AS2 Subject Header
  */
  @Getter
  @JsonProperty("subject")
  public String subject;

  /**
  * Date Header
  */
  @Getter
  @JsonProperty("date")
  public String date;

  /**
  * Encrypted Payload Body Size
  */
  @Getter
  @JsonProperty("body_size")
  public String bodySize;

  /**
  * Filename of the file being received.
  */
  @Getter
  @JsonProperty("attachment_filename")
  public String attachmentFilename;

  /**
  * IP Address of the Sender
  */
  @Getter
  @JsonProperty("ip")
  public String ip;

  /**
  * Message creation date/time
  */
  @Getter
  @JsonProperty("created_at")
  public Date createdAt;

  /**
  * HTTP Response Code sent for this message
  */
  @Getter
  @JsonProperty("http_response_code")
  public String httpResponseCode;

  /**
  * HTTP Headers sent for this message.
  */
  @Getter
  @JsonProperty("http_response_headers")
  public Map<String, String> httpResponseHeaders;

  /**
  * Incoming Message Recipient(the Client Cert used to encrypt this message)'s serial
  */
  @Getter
  @JsonProperty("recipient_serial")
  public String recipientSerial;

  /**
  * Incoming Message Recipient(the Client Cert used to encrypt this message)'s serial in hex format.
  */
  @Getter
  @JsonProperty("hex_recipient_serial")
  public String hexRecipientSerial;

  /**
  * Incoming Message Recipient(the Client Cert used to encrypt this message)'s issuer
  */
  @Getter
  @JsonProperty("recipient_issuer")
  public String recipientIssuer;

  /**
  * Message body received?
  */
  @Getter
  @JsonProperty("message_received")
  public Boolean messageReceived;

  /**
  * Message decrypted successfully?
  */
  @Getter
  @JsonProperty("message_decrypted")
  public Boolean messageDecrypted;

  /**
  * Message signature verified?
  */
  @Getter
  @JsonProperty("message_signature_verified")
  public Boolean messageSignatureVerified;

  /**
  * Message processed successfully?
  */
  @Getter
  @JsonProperty("message_processing_success")
  public Boolean messageProcessingSuccess;

  /**
  * MDN returned?
  */
  @Getter
  @JsonProperty("message_mdn_returned")
  public Boolean messageMdnReturned;

  /**
  * URL to download the encrypted signed smime that is to sent as AS2 body
  */
  @Getter
  @JsonProperty("encrypted_uri")
  public String encryptedUri;

  /**
  * URL to download the file contents as smime with signature
  */
  @Getter
  @JsonProperty("smime_signed_uri")
  public String smimeSignedUri;

  /**
  * URL to download the file contents encoded as smime
  */
  @Getter
  @JsonProperty("smime_uri")
  public String smimeUri;

  /**
  * URL to download the original file contents
  */
  @Getter
  @JsonProperty("raw_uri")
  public String rawUri;

  /**
  * URL to download the http response body
  */
  @Getter
  @JsonProperty("mdn_response_uri")
  public String mdnResponseUri;


  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at` and `as2_partner_id`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`.
  *   filter_gt - object - If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`.
  *   filter_gteq - object - If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`.
  *   filter_lt - object - If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`.
  *   filter_lteq - object - If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`.
  *   as2_partner_id - int64 - As2 Partner ID.  If provided, will return message specific to that partner.
  */
  public static ListIterator<As2IncomingMessage> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<As2IncomingMessage> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<As2IncomingMessage> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
    if (parameters.containsKey("as2_partner_id") && !(parameters.get("as2_partner_id") instanceof Long || parameters.get("as2_partner_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: as2_partner_id must be of type Long or Integer parameters[\"as2_partner_id\"]");
    }


    String url = String.format("%s%s/as2_incoming_messages", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<As2IncomingMessage>> typeReference = new TypeReference<List<As2IncomingMessage>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<As2IncomingMessage> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<As2IncomingMessage> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
