package com.files.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.net.ClientRuntime;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class As2IncomingMessage implements ModelInterface {
  private HashMap<String, Object> options;
  private FilesClient filesClient;

  /**
   * Sets the request options, such as {@code api_key} or {@code workspace_id}, that this object's
   * own operations send. The map is copied; options that control a single call, such as a
   * {@code cancellation} token or a {@code timeout}, are not kept.
   */
  public void setOptions(HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
  }

  /**
   * Sets the client this object's own operations run with.
   *
   * @param client the client
   */
  @JsonIgnore
  @Override
  public void setClient(FilesClient client) {
    this.filesClient = client;
  }

  // The client this object's own operations run with: the one that returned it, or one set with
  // setClient, and otherwise the default client as it is when the operation runs.
  private FilesClient filesClient() {
    return filesClient != null ? filesClient : FilesClient.defaultClient();
  }

  // One mapper for every As2IncomingMessage, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code As2IncomingMessage} with no attributes set.
   */
  public As2IncomingMessage() {
    this(null, null);
  }

  /**
   * Creates a {@code As2IncomingMessage} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public As2IncomingMessage(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code As2IncomingMessage} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public As2IncomingMessage(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Id of the AS2 Partner.
   */
  @JsonProperty("id")
  public Long id;

  /**
   * Gets {@link #id}.
   *
   * @return the value of {@link #id}
   */
  public Long getId() {
    return id;
  }

  /**
   * ID of the Workspace associated with this AS2 Incoming Message.
   */
  @JsonProperty("workspace_id")
  public Long workspaceId;

  /**
   * Gets {@link #workspaceId}.
   *
   * @return the value of {@link #workspaceId}
   */
  public Long getWorkspaceId() {
    return workspaceId;
  }

  /**
   * Id of the AS2 Partner associated with this message.
   */
  @JsonProperty("as2_partner_id")
  public Long as2PartnerId;

  /**
   * Gets {@link #as2PartnerId}.
   *
   * @return the value of {@link #as2PartnerId}
   */
  public Long getAs2PartnerId() {
    return as2PartnerId;
  }

  /**
   * Id of the AS2 Station associated with this message.
   */
  @JsonProperty("as2_station_id")
  public Long as2StationId;

  /**
   * Gets {@link #as2StationId}.
   *
   * @return the value of {@link #as2StationId}
   */
  public Long getAs2StationId() {
    return as2StationId;
  }

  /**
   * UUID assigned to this message.
   */
  @JsonProperty("uuid")
  public String uuid;

  /**
   * Gets {@link #uuid}.
   *
   * @return the value of {@link #uuid}
   */
  public String getUuid() {
    return uuid;
  }

  /**
   * Content Type header of the incoming message.
   */
  @JsonProperty("content_type")
  public String contentType;

  /**
   * Gets {@link #contentType}.
   *
   * @return the value of {@link #contentType}
   */
  public String getContentType() {
    return contentType;
  }

  /**
   * HTTP Headers sent with this message.
   */
  @JsonProperty("http_headers")
  public Object httpHeaders;

  /**
   * Gets {@link #httpHeaders}.
   *
   * @return the value of {@link #httpHeaders}
   */
  public Object getHttpHeaders() {
    return httpHeaders;
  }

  /**
   * Result of processing.
   */
  @JsonProperty("processing_result")
  public String processingResult;

  /**
   * Gets {@link #processingResult}.
   *
   * @return the value of {@link #processingResult}
   */
  public String getProcessingResult() {
    return processingResult;
  }

  /**
   * Result of processing description.
   */
  @JsonProperty("processing_result_description")
  public String processingResultDescription;

  /**
   * Gets {@link #processingResultDescription}.
   *
   * @return the value of {@link #processingResultDescription}
   */
  public String getProcessingResultDescription() {
    return processingResultDescription;
  }

  /**
   * AS2 Message Integrity Check
   */
  @JsonProperty("mic")
  public String mic;

  /**
   * Gets {@link #mic}.
   *
   * @return the value of {@link #mic}
   */
  public String getMic() {
    return mic;
  }

  /**
   * AS2 Message Integrity Check Algorithm Used
   */
  @JsonProperty("mic_algo")
  public String micAlgo;

  /**
   * Gets {@link #micAlgo}.
   *
   * @return the value of {@link #micAlgo}
   */
  public String getMicAlgo() {
    return micAlgo;
  }

  /**
   * AS2 TO header of message
   */
  @JsonProperty("as2_to")
  public String as2To;

  /**
   * Gets {@link #as2To}.
   *
   * @return the value of {@link #as2To}
   */
  public String getAs2To() {
    return as2To;
  }

  /**
   * AS2 FROM header of message
   */
  @JsonProperty("as2_from")
  public String as2From;

  /**
   * Gets {@link #as2From}.
   *
   * @return the value of {@link #as2From}
   */
  public String getAs2From() {
    return as2From;
  }

  /**
   * AS2 Message Id
   */
  @JsonProperty("message_id")
  public String messageId;

  /**
   * Gets {@link #messageId}.
   *
   * @return the value of {@link #messageId}
   */
  public String getMessageId() {
    return messageId;
  }

  /**
   * AS2 Subject Header
   */
  @JsonProperty("subject")
  public String subject;

  /**
   * Gets {@link #subject}.
   *
   * @return the value of {@link #subject}
   */
  public String getSubject() {
    return subject;
  }

  /**
   * Date Header
   */
  @JsonProperty("date")
  public String date;

  /**
   * Gets {@link #date}.
   *
   * @return the value of {@link #date}
   */
  public String getDate() {
    return date;
  }

  /**
   * Encrypted Payload Body Size
   */
  @JsonProperty("body_size")
  public String bodySize;

  /**
   * Gets {@link #bodySize}.
   *
   * @return the value of {@link #bodySize}
   */
  public String getBodySize() {
    return bodySize;
  }

  /**
   * Filename of the file being received.
   */
  @JsonProperty("attachment_filename")
  public String attachmentFilename;

  /**
   * Gets {@link #attachmentFilename}.
   *
   * @return the value of {@link #attachmentFilename}
   */
  public String getAttachmentFilename() {
    return attachmentFilename;
  }

  /**
   * IP Address of the Sender
   */
  @JsonProperty("ip")
  public String ip;

  /**
   * Gets {@link #ip}.
   *
   * @return the value of {@link #ip}
   */
  public String getIp() {
    return ip;
  }

  /**
   * Message creation date/time
   */
  @JsonProperty("created_at")
  public Date createdAt;

  /**
   * Gets {@link #createdAt}.
   *
   * @return the value of {@link #createdAt}
   */
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * HTTP Response Code sent for this message
   */
  @JsonProperty("http_response_code")
  public String httpResponseCode;

  /**
   * Gets {@link #httpResponseCode}.
   *
   * @return the value of {@link #httpResponseCode}
   */
  public String getHttpResponseCode() {
    return httpResponseCode;
  }

  /**
   * HTTP Headers sent for this message.
   */
  @JsonProperty("http_response_headers")
  public Object httpResponseHeaders;

  /**
   * Gets {@link #httpResponseHeaders}.
   *
   * @return the value of {@link #httpResponseHeaders}
   */
  public Object getHttpResponseHeaders() {
    return httpResponseHeaders;
  }

  /**
   * Incoming Message Recipient(the Client Cert used to encrypt this message)'s serial
   */
  @JsonProperty("recipient_serial")
  public String recipientSerial;

  /**
   * Gets {@link #recipientSerial}.
   *
   * @return the value of {@link #recipientSerial}
   */
  public String getRecipientSerial() {
    return recipientSerial;
  }

  /**
   * Incoming Message Recipient(the Client Cert used to encrypt this message)'s serial in hex format.
   */
  @JsonProperty("hex_recipient_serial")
  public String hexRecipientSerial;

  /**
   * Gets {@link #hexRecipientSerial}.
   *
   * @return the value of {@link #hexRecipientSerial}
   */
  public String getHexRecipientSerial() {
    return hexRecipientSerial;
  }

  /**
   * Incoming Message Recipient(the Client Cert used to encrypt this message)'s issuer
   */
  @JsonProperty("recipient_issuer")
  public String recipientIssuer;

  /**
   * Gets {@link #recipientIssuer}.
   *
   * @return the value of {@link #recipientIssuer}
   */
  public String getRecipientIssuer() {
    return recipientIssuer;
  }

  /**
   * Message body received?
   */
  @JsonProperty("message_received")
  public Boolean messageReceived;

  /**
   * Gets {@link #messageReceived}.
   *
   * @return the value of {@link #messageReceived}
   */
  public Boolean getMessageReceived() {
    return messageReceived;
  }

  /**
   * Message decrypted successfully?
   */
  @JsonProperty("message_decrypted")
  public Boolean messageDecrypted;

  /**
   * Gets {@link #messageDecrypted}.
   *
   * @return the value of {@link #messageDecrypted}
   */
  public Boolean getMessageDecrypted() {
    return messageDecrypted;
  }

  /**
   * Message signature verified?
   */
  @JsonProperty("message_signature_verified")
  public Boolean messageSignatureVerified;

  /**
   * Gets {@link #messageSignatureVerified}.
   *
   * @return the value of {@link #messageSignatureVerified}
   */
  public Boolean getMessageSignatureVerified() {
    return messageSignatureVerified;
  }

  /**
   * Message processed successfully?
   */
  @JsonProperty("message_processing_success")
  public Boolean messageProcessingSuccess;

  /**
   * Gets {@link #messageProcessingSuccess}.
   *
   * @return the value of {@link #messageProcessingSuccess}
   */
  public Boolean getMessageProcessingSuccess() {
    return messageProcessingSuccess;
  }

  /**
   * MDN returned?
   */
  @JsonProperty("message_mdn_returned")
  public Boolean messageMdnReturned;

  /**
   * Gets {@link #messageMdnReturned}.
   *
   * @return the value of {@link #messageMdnReturned}
   */
  public Boolean getMessageMdnReturned() {
    return messageMdnReturned;
  }

  /**
   * URL to download the encrypted signed smime that is to sent as AS2 body
   */
  @JsonProperty("encrypted_uri")
  public String encryptedUri;

  /**
   * Gets {@link #encryptedUri}.
   *
   * @return the value of {@link #encryptedUri}
   */
  public String getEncryptedUri() {
    return encryptedUri;
  }

  /**
   * URL to download the file contents as smime with signature
   */
  @JsonProperty("smime_signed_uri")
  public String smimeSignedUri;

  /**
   * Gets {@link #smimeSignedUri}.
   *
   * @return the value of {@link #smimeSignedUri}
   */
  public String getSmimeSignedUri() {
    return smimeSignedUri;
  }

  /**
   * URL to download the file contents encoded as smime
   */
  @JsonProperty("smime_uri")
  public String smimeUri;

  /**
   * Gets {@link #smimeUri}.
   *
   * @return the value of {@link #smimeUri}
   */
  public String getSmimeUri() {
    return smimeUri;
  }

  /**
   * URL to download the original file contents
   */
  @JsonProperty("raw_uri")
  public String rawUri;

  /**
   * Gets {@link #rawUri}.
   *
   * @return the value of {@link #rawUri}
   */
  public String getRawUri() {
    return rawUri;
  }

  /**
   * URL to download the http response body
   */
  @JsonProperty("mdn_response_uri")
  public String mdnResponseUri;

  /**
   * Gets {@link #mdnResponseUri}.
   *
   * @return the value of {@link #mdnResponseUri}
   */
  public String getMdnResponseUri() {
    return mdnResponseUri;
  }


  /**
   * List AS2 Incoming Messages
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<As2IncomingMessage> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List AS2 Incoming Messages
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<As2IncomingMessage> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List AS2 Incoming Messages
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `created_at` or `as2_partner_id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`, `as2_station_id`, `workspace_id` or `as2_partner_id`. Valid field combinations are `[ as2_station_id, created_at ]`, `[ workspace_id, created_at ]`, `[ as2_partner_id, created_at ]`, `[ workspace_id, as2_station_id ]`, `[ workspace_id, as2_partner_id ]`, `[ workspace_id, as2_station_id, created_at ]` or `[ workspace_id, as2_partner_id, created_at ]`.</li>
   * <li>{@code filter_gt} (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_gteq} (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_lt} (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_lteq} (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code As2IncomingMessage} records, in a {@code ListIterator}
   */
  public static ListIterator<As2IncomingMessage> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().as2IncomingMessages().list(parameters, options);
  }

  /**
   * List AS2 Incoming Messages
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<As2IncomingMessage> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List AS2 Incoming Messages
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<As2IncomingMessage> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
