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
public class SiemHttpDestination implements ModelInterface {
  @Setter
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();


  public SiemHttpDestination() {
    this(null, null);
  }

  public SiemHttpDestination(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public SiemHttpDestination(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * SIEM HTTP Destination ID
  */
  @Getter
  @Setter
  @JsonProperty("id")
  public Long id;

  /**
  * Name for this Destination
  */
  @Getter
  @Setter
  @JsonProperty("name")
  public String name;

  /**
  * Destination Type
  */
  @Getter
  @Setter
  @JsonProperty("destination_type")
  public String destinationType;

  /**
  * Destination Url
  */
  @Getter
  @Setter
  @JsonProperty("destination_url")
  public String destinationUrl;

  /**
  * Additional HTTP Headers included in calls to the destination URL
  */
  @Getter
  @Setter
  @JsonProperty("additional_headers")
  public Map<String, String> additionalHeaders;

  /**
  * Whether this SIEM HTTP Destination is currently being sent to or not
  */
  @Getter
  @Setter
  @JsonProperty("sending_active")
  public Boolean sendingActive;

  /**
  * Applicable only for destination type: generic. Indicates the type of HTTP body. Can be json_newline or json_array. json_newline is multiple log entries as JSON separated by newlines. json_array is a single JSON array containing multiple log entries as JSON.
  */
  @Getter
  @Setter
  @JsonProperty("generic_payload_type")
  public String genericPayloadType;

  /**
  * Applicable only for destination type: splunk. Authentication token provided by Splunk.
  */
  @Getter
  @Setter
  @JsonProperty("splunk_token_masked")
  public String splunkTokenMasked;

  /**
  * Applicable only for destination type: azure. Immutable ID of the Data Collection Rule.
  */
  @Getter
  @Setter
  @JsonProperty("azure_dcr_immutable_id")
  public String azureDcrImmutableId;

  /**
  * Applicable only for destination type: azure. Name of the stream in the DCR that represents the destination table.
  */
  @Getter
  @Setter
  @JsonProperty("azure_stream_name")
  public String azureStreamName;

  /**
  * Applicable only for destination type: azure. Client Credentials OAuth Tenant ID.
  */
  @Getter
  @Setter
  @JsonProperty("azure_oauth_client_credentials_tenant_id")
  public String azureOauthClientCredentialsTenantId;

  /**
  * Applicable only for destination type: azure. Client Credentials OAuth Client ID.
  */
  @Getter
  @Setter
  @JsonProperty("azure_oauth_client_credentials_client_id")
  public String azureOauthClientCredentialsClientId;

  /**
  * Applicable only for destination type: azure. Client Credentials OAuth Client Secret.
  */
  @Getter
  @Setter
  @JsonProperty("azure_oauth_client_credentials_client_secret_masked")
  public String azureOauthClientCredentialsClientSecretMasked;

  /**
  * Applicable only for destination type: qradar. Basic auth username provided by QRadar.
  */
  @Getter
  @Setter
  @JsonProperty("qradar_username")
  public String qradarUsername;

  /**
  * Applicable only for destination type: qradar. Basic auth password provided by QRadar.
  */
  @Getter
  @Setter
  @JsonProperty("qradar_password_masked")
  public String qradarPasswordMasked;

  /**
  * Applicable only for destination type: solar_winds. Authentication token provided by Solar Winds.
  */
  @Getter
  @Setter
  @JsonProperty("solar_winds_token_masked")
  public String solarWindsTokenMasked;

  /**
  * Applicable only for destination type: new_relic. API key provided by New Relic.
  */
  @Getter
  @Setter
  @JsonProperty("new_relic_api_key_masked")
  public String newRelicApiKeyMasked;

  /**
  * Applicable only for destination type: datadog. API key provided by Datadog.
  */
  @Getter
  @Setter
  @JsonProperty("datadog_api_key_masked")
  public String datadogApiKeyMasked;

  /**
  * Whether or not sending is enabled for sftp_action logs.
  */
  @Getter
  @Setter
  @JsonProperty("sftp_action_send_enabled")
  public Boolean sftpActionSendEnabled;

  /**
  * Number of log entries sent for the lifetime of this destination.
  */
  @Getter
  @Setter
  @JsonProperty("sftp_action_records_sent_entries_sent")
  public Long sftpActionRecordsSentEntriesSent;

  /**
  * Whether or not sending is enabled for ftp_action logs.
  */
  @Getter
  @Setter
  @JsonProperty("ftp_action_send_enabled")
  public Boolean ftpActionSendEnabled;

  /**
  * Number of log entries sent for the lifetime of this destination.
  */
  @Getter
  @Setter
  @JsonProperty("ftp_action_records_sent_entries_sent")
  public Long ftpActionRecordsSentEntriesSent;

  /**
  * Whether or not sending is enabled for web_dav_action logs.
  */
  @Getter
  @Setter
  @JsonProperty("web_dav_action_send_enabled")
  public Boolean webDavActionSendEnabled;

  /**
  * Number of log entries sent for the lifetime of this destination.
  */
  @Getter
  @Setter
  @JsonProperty("web_dav_action_records_sent_entries_sent")
  public Long webDavActionRecordsSentEntriesSent;

  /**
  * Whether or not sending is enabled for sync logs.
  */
  @Getter
  @Setter
  @JsonProperty("sync_send_enabled")
  public Boolean syncSendEnabled;

  /**
  * Number of log entries sent for the lifetime of this destination.
  */
  @Getter
  @Setter
  @JsonProperty("sync_records_sent_entries_sent")
  public Long syncRecordsSentEntriesSent;

  /**
  * Whether or not sending is enabled for outbound_connection logs.
  */
  @Getter
  @Setter
  @JsonProperty("outbound_connection_send_enabled")
  public Boolean outboundConnectionSendEnabled;

  /**
  * Number of log entries sent for the lifetime of this destination.
  */
  @Getter
  @Setter
  @JsonProperty("outbound_connection_records_sent_entries_sent")
  public Long outboundConnectionRecordsSentEntriesSent;

  /**
  * Whether or not sending is enabled for automation logs.
  */
  @Getter
  @Setter
  @JsonProperty("automation_send_enabled")
  public Boolean automationSendEnabled;

  /**
  * Number of log entries sent for the lifetime of this destination.
  */
  @Getter
  @Setter
  @JsonProperty("automation_records_sent_entries_sent")
  public Long automationRecordsSentEntriesSent;

  /**
  * Whether or not sending is enabled for api_request logs.
  */
  @Getter
  @Setter
  @JsonProperty("api_request_send_enabled")
  public Boolean apiRequestSendEnabled;

  /**
  * Number of log entries sent for the lifetime of this destination.
  */
  @Getter
  @Setter
  @JsonProperty("api_request_records_sent_entries_sent")
  public Long apiRequestRecordsSentEntriesSent;

  /**
  * Whether or not sending is enabled for public_hosting_request logs.
  */
  @Getter
  @Setter
  @JsonProperty("public_hosting_request_send_enabled")
  public Boolean publicHostingRequestSendEnabled;

  /**
  * Number of log entries sent for the lifetime of this destination.
  */
  @Getter
  @Setter
  @JsonProperty("public_hosting_request_records_sent_entries_sent")
  public Long publicHostingRequestRecordsSentEntriesSent;

  /**
  * Whether or not sending is enabled for email logs.
  */
  @Getter
  @Setter
  @JsonProperty("email_send_enabled")
  public Boolean emailSendEnabled;

  /**
  * Number of log entries sent for the lifetime of this destination.
  */
  @Getter
  @Setter
  @JsonProperty("email_records_sent_entries_sent")
  public Long emailRecordsSentEntriesSent;

  /**
  * Whether or not sending is enabled for exavault_api_request logs.
  */
  @Getter
  @Setter
  @JsonProperty("exavault_api_request_send_enabled")
  public Boolean exavaultApiRequestSendEnabled;

  /**
  * Number of log entries sent for the lifetime of this destination.
  */
  @Getter
  @Setter
  @JsonProperty("exavault_api_request_records_sent_entries_sent")
  public Long exavaultApiRequestRecordsSentEntriesSent;

  /**
  * Type of URL that was last called. Can be `destination_url` or `azure_oauth_client_credentials_url`
  */
  @Getter
  @Setter
  @JsonProperty("last_http_call_target_type")
  public String lastHttpCallTargetType;

  /**
  * Was the last HTTP call made successful?
  */
  @Getter
  @Setter
  @JsonProperty("last_http_call_success")
  public Boolean lastHttpCallSuccess;

  /**
  * Last HTTP Call Response Code
  */
  @Getter
  @Setter
  @JsonProperty("last_http_call_response_code")
  public Long lastHttpCallResponseCode;

  /**
  * Last HTTP Call Response Body. Large responses are truncated.
  */
  @Getter
  @Setter
  @JsonProperty("last_http_call_response_body")
  public String lastHttpCallResponseBody;

  /**
  * Last HTTP Call Error Message if applicable
  */
  @Getter
  @Setter
  @JsonProperty("last_http_call_error_message")
  public String lastHttpCallErrorMessage;

  /**
  * Time of Last HTTP Call
  */
  @Getter
  @Setter
  @JsonProperty("last_http_call_time")
  public String lastHttpCallTime;

  /**
  * Duration of the last HTTP Call in milliseconds
  */
  @Getter
  @Setter
  @JsonProperty("last_http_call_duration_ms")
  public Long lastHttpCallDurationMs;

  /**
  * Time of Most Recent Successful HTTP Call
  */
  @Getter
  @Setter
  @JsonProperty("most_recent_http_call_success_time")
  public String mostRecentHttpCallSuccessTime;

  /**
  * Connection Test Entry
  */
  @Getter
  @Setter
  @JsonProperty("connection_test_entry")
  public String connectionTestEntry;

  /**
  * Applicable only for destination type: splunk. Authentication token provided by Splunk.
  */
  @Getter
  @Setter
  @JsonProperty("splunk_token")
  public String splunkToken;

  /**
  * Applicable only for destination type: azure. Client Credentials OAuth Client Secret.
  */
  @Getter
  @Setter
  @JsonProperty("azure_oauth_client_credentials_client_secret")
  public String azureOauthClientCredentialsClientSecret;

  /**
  * Applicable only for destination type: qradar. Basic auth password provided by QRadar.
  */
  @Getter
  @Setter
  @JsonProperty("qradar_password")
  public String qradarPassword;

  /**
  * Applicable only for destination type: solar_winds. Authentication token provided by Solar Winds.
  */
  @Getter
  @Setter
  @JsonProperty("solar_winds_token")
  public String solarWindsToken;

  /**
  * Applicable only for destination type: new_relic. API key provided by New Relic.
  */
  @Getter
  @Setter
  @JsonProperty("new_relic_api_key")
  public String newRelicApiKey;

  /**
  * Applicable only for destination type: datadog. API key provided by Datadog.
  */
  @Getter
  @Setter
  @JsonProperty("datadog_api_key")
  public String datadogApiKey;

  /**
  * Parameters:
  *   name - string - Name for this Destination
  *   additional_headers - object - Additional HTTP Headers included in calls to the destination URL
  *   sending_active - boolean - Whether this SIEM HTTP Destination is currently being sent to or not
  *   generic_payload_type - string - Applicable only for destination type: generic. Indicates the type of HTTP body. Can be json_newline or json_array. json_newline is multiple log entries as JSON separated by newlines. json_array is a single JSON array containing multiple log entries as JSON.
  *   splunk_token - string - Applicable only for destination type: splunk. Authentication token provided by Splunk.
  *   azure_dcr_immutable_id - string - Applicable only for destination type: azure. Immutable ID of the Data Collection Rule.
  *   azure_stream_name - string - Applicable only for destination type: azure. Name of the stream in the DCR that represents the destination table.
  *   azure_oauth_client_credentials_tenant_id - string - Applicable only for destination type: azure. Client Credentials OAuth Tenant ID.
  *   azure_oauth_client_credentials_client_id - string - Applicable only for destination type: azure. Client Credentials OAuth Client ID.
  *   azure_oauth_client_credentials_client_secret - string - Applicable only for destination type: azure. Client Credentials OAuth Client Secret.
  *   qradar_username - string - Applicable only for destination type: qradar. Basic auth username provided by QRadar.
  *   qradar_password - string - Applicable only for destination type: qradar. Basic auth password provided by QRadar.
  *   solar_winds_token - string - Applicable only for destination type: solar_winds. Authentication token provided by Solar Winds.
  *   new_relic_api_key - string - Applicable only for destination type: new_relic. API key provided by New Relic.
  *   datadog_api_key - string - Applicable only for destination type: datadog. API key provided by Datadog.
  *   sftp_action_send_enabled - boolean - Whether or not sending is enabled for sftp_action logs.
  *   ftp_action_send_enabled - boolean - Whether or not sending is enabled for ftp_action logs.
  *   web_dav_action_send_enabled - boolean - Whether or not sending is enabled for web_dav_action logs.
  *   sync_send_enabled - boolean - Whether or not sending is enabled for sync logs.
  *   outbound_connection_send_enabled - boolean - Whether or not sending is enabled for outbound_connection logs.
  *   automation_send_enabled - boolean - Whether or not sending is enabled for automation logs.
  *   api_request_send_enabled - boolean - Whether or not sending is enabled for api_request logs.
  *   public_hosting_request_send_enabled - boolean - Whether or not sending is enabled for public_hosting_request logs.
  *   email_send_enabled - boolean - Whether or not sending is enabled for email logs.
  *   exavault_api_request_send_enabled - boolean - Whether or not sending is enabled for exavault_api_request logs.
  *   destination_type - string - Destination Type
  *   destination_url - string - Destination Url
  */
  public SiemHttpDestination update() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    return SiemHttpDestination.update(this.id, parameters, this.options);
  }

  /**
  */
  public void delete() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    SiemHttpDestination.delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete();
  }


  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    SiemHttpDestination.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  */
  public static ListIterator<SiemHttpDestination> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<SiemHttpDestination> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<SiemHttpDestination> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }


    String url = String.format("%s%s/siem_http_destinations", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<SiemHttpDestination>> typeReference = new TypeReference<List<SiemHttpDestination>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<SiemHttpDestination> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<SiemHttpDestination> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Siem Http Destination ID.
  */

  public static SiemHttpDestination find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static SiemHttpDestination find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static SiemHttpDestination find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/siem_http_destinations/%s", urlParts);

    TypeReference<SiemHttpDestination> typeReference = new TypeReference<SiemHttpDestination>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static SiemHttpDestination get() throws RuntimeException {
    return get(null, null, null);
  }

  public static SiemHttpDestination get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   name - string - Name for this Destination
  *   additional_headers - object - Additional HTTP Headers included in calls to the destination URL
  *   sending_active - boolean - Whether this SIEM HTTP Destination is currently being sent to or not
  *   generic_payload_type - string - Applicable only for destination type: generic. Indicates the type of HTTP body. Can be json_newline or json_array. json_newline is multiple log entries as JSON separated by newlines. json_array is a single JSON array containing multiple log entries as JSON.
  *   splunk_token - string - Applicable only for destination type: splunk. Authentication token provided by Splunk.
  *   azure_dcr_immutable_id - string - Applicable only for destination type: azure. Immutable ID of the Data Collection Rule.
  *   azure_stream_name - string - Applicable only for destination type: azure. Name of the stream in the DCR that represents the destination table.
  *   azure_oauth_client_credentials_tenant_id - string - Applicable only for destination type: azure. Client Credentials OAuth Tenant ID.
  *   azure_oauth_client_credentials_client_id - string - Applicable only for destination type: azure. Client Credentials OAuth Client ID.
  *   azure_oauth_client_credentials_client_secret - string - Applicable only for destination type: azure. Client Credentials OAuth Client Secret.
  *   qradar_username - string - Applicable only for destination type: qradar. Basic auth username provided by QRadar.
  *   qradar_password - string - Applicable only for destination type: qradar. Basic auth password provided by QRadar.
  *   solar_winds_token - string - Applicable only for destination type: solar_winds. Authentication token provided by Solar Winds.
  *   new_relic_api_key - string - Applicable only for destination type: new_relic. API key provided by New Relic.
  *   datadog_api_key - string - Applicable only for destination type: datadog. API key provided by Datadog.
  *   sftp_action_send_enabled - boolean - Whether or not sending is enabled for sftp_action logs.
  *   ftp_action_send_enabled - boolean - Whether or not sending is enabled for ftp_action logs.
  *   web_dav_action_send_enabled - boolean - Whether or not sending is enabled for web_dav_action logs.
  *   sync_send_enabled - boolean - Whether or not sending is enabled for sync logs.
  *   outbound_connection_send_enabled - boolean - Whether or not sending is enabled for outbound_connection logs.
  *   automation_send_enabled - boolean - Whether or not sending is enabled for automation logs.
  *   api_request_send_enabled - boolean - Whether or not sending is enabled for api_request logs.
  *   public_hosting_request_send_enabled - boolean - Whether or not sending is enabled for public_hosting_request logs.
  *   email_send_enabled - boolean - Whether or not sending is enabled for email logs.
  *   exavault_api_request_send_enabled - boolean - Whether or not sending is enabled for exavault_api_request logs.
  *   destination_type (required) - string - Destination Type
  *   destination_url (required) - string - Destination Url
  */

  public static SiemHttpDestination create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static SiemHttpDestination create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (!parameters.containsKey("destination_type") || parameters.get("destination_type") == null) {
      throw new NullPointerException("Parameter missing: destination_type parameters[\"destination_type\"]");
    }
    if (!parameters.containsKey("destination_url") || parameters.get("destination_url") == null) {
      throw new NullPointerException("Parameter missing: destination_url parameters[\"destination_url\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("additional_headers") && !(parameters.get("additional_headers") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: additional_headers must be of type Map<String, String> parameters[\"additional_headers\"]");
    }
    if (parameters.containsKey("sending_active") && !(parameters.get("sending_active") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: sending_active must be of type Boolean parameters[\"sending_active\"]");
    }
    if (parameters.containsKey("generic_payload_type") && !(parameters.get("generic_payload_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: generic_payload_type must be of type String parameters[\"generic_payload_type\"]");
    }
    if (parameters.containsKey("splunk_token") && !(parameters.get("splunk_token") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: splunk_token must be of type String parameters[\"splunk_token\"]");
    }
    if (parameters.containsKey("azure_dcr_immutable_id") && !(parameters.get("azure_dcr_immutable_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_dcr_immutable_id must be of type String parameters[\"azure_dcr_immutable_id\"]");
    }
    if (parameters.containsKey("azure_stream_name") && !(parameters.get("azure_stream_name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_stream_name must be of type String parameters[\"azure_stream_name\"]");
    }
    if (parameters.containsKey("azure_oauth_client_credentials_tenant_id") && !(parameters.get("azure_oauth_client_credentials_tenant_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_oauth_client_credentials_tenant_id must be of type String parameters[\"azure_oauth_client_credentials_tenant_id\"]");
    }
    if (parameters.containsKey("azure_oauth_client_credentials_client_id") && !(parameters.get("azure_oauth_client_credentials_client_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_oauth_client_credentials_client_id must be of type String parameters[\"azure_oauth_client_credentials_client_id\"]");
    }
    if (parameters.containsKey("azure_oauth_client_credentials_client_secret") && !(parameters.get("azure_oauth_client_credentials_client_secret") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_oauth_client_credentials_client_secret must be of type String parameters[\"azure_oauth_client_credentials_client_secret\"]");
    }
    if (parameters.containsKey("qradar_username") && !(parameters.get("qradar_username") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: qradar_username must be of type String parameters[\"qradar_username\"]");
    }
    if (parameters.containsKey("qradar_password") && !(parameters.get("qradar_password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: qradar_password must be of type String parameters[\"qradar_password\"]");
    }
    if (parameters.containsKey("solar_winds_token") && !(parameters.get("solar_winds_token") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: solar_winds_token must be of type String parameters[\"solar_winds_token\"]");
    }
    if (parameters.containsKey("new_relic_api_key") && !(parameters.get("new_relic_api_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: new_relic_api_key must be of type String parameters[\"new_relic_api_key\"]");
    }
    if (parameters.containsKey("datadog_api_key") && !(parameters.get("datadog_api_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: datadog_api_key must be of type String parameters[\"datadog_api_key\"]");
    }
    if (parameters.containsKey("sftp_action_send_enabled") && !(parameters.get("sftp_action_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: sftp_action_send_enabled must be of type Boolean parameters[\"sftp_action_send_enabled\"]");
    }
    if (parameters.containsKey("ftp_action_send_enabled") && !(parameters.get("ftp_action_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: ftp_action_send_enabled must be of type Boolean parameters[\"ftp_action_send_enabled\"]");
    }
    if (parameters.containsKey("web_dav_action_send_enabled") && !(parameters.get("web_dav_action_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: web_dav_action_send_enabled must be of type Boolean parameters[\"web_dav_action_send_enabled\"]");
    }
    if (parameters.containsKey("sync_send_enabled") && !(parameters.get("sync_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: sync_send_enabled must be of type Boolean parameters[\"sync_send_enabled\"]");
    }
    if (parameters.containsKey("outbound_connection_send_enabled") && !(parameters.get("outbound_connection_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: outbound_connection_send_enabled must be of type Boolean parameters[\"outbound_connection_send_enabled\"]");
    }
    if (parameters.containsKey("automation_send_enabled") && !(parameters.get("automation_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: automation_send_enabled must be of type Boolean parameters[\"automation_send_enabled\"]");
    }
    if (parameters.containsKey("api_request_send_enabled") && !(parameters.get("api_request_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: api_request_send_enabled must be of type Boolean parameters[\"api_request_send_enabled\"]");
    }
    if (parameters.containsKey("public_hosting_request_send_enabled") && !(parameters.get("public_hosting_request_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: public_hosting_request_send_enabled must be of type Boolean parameters[\"public_hosting_request_send_enabled\"]");
    }
    if (parameters.containsKey("email_send_enabled") && !(parameters.get("email_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: email_send_enabled must be of type Boolean parameters[\"email_send_enabled\"]");
    }
    if (parameters.containsKey("exavault_api_request_send_enabled") && !(parameters.get("exavault_api_request_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: exavault_api_request_send_enabled must be of type Boolean parameters[\"exavault_api_request_send_enabled\"]");
    }
    if (parameters.containsKey("destination_type") && !(parameters.get("destination_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: destination_type must be of type String parameters[\"destination_type\"]");
    }
    if (parameters.containsKey("destination_url") && !(parameters.get("destination_url") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: destination_url must be of type String parameters[\"destination_url\"]");
    }


    String url = String.format("%s%s/siem_http_destinations", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<SiemHttpDestination> typeReference = new TypeReference<SiemHttpDestination>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   siem_http_destination_id - int64 - SIEM HTTP Destination ID
  *   destination_type - string - Destination Type
  *   destination_url - string - Destination Url
  *   name - string - Name for this Destination
  *   additional_headers - object - Additional HTTP Headers included in calls to the destination URL
  *   sending_active - boolean - Whether this SIEM HTTP Destination is currently being sent to or not
  *   generic_payload_type - string - Applicable only for destination type: generic. Indicates the type of HTTP body. Can be json_newline or json_array. json_newline is multiple log entries as JSON separated by newlines. json_array is a single JSON array containing multiple log entries as JSON.
  *   splunk_token - string - Applicable only for destination type: splunk. Authentication token provided by Splunk.
  *   azure_dcr_immutable_id - string - Applicable only for destination type: azure. Immutable ID of the Data Collection Rule.
  *   azure_stream_name - string - Applicable only for destination type: azure. Name of the stream in the DCR that represents the destination table.
  *   azure_oauth_client_credentials_tenant_id - string - Applicable only for destination type: azure. Client Credentials OAuth Tenant ID.
  *   azure_oauth_client_credentials_client_id - string - Applicable only for destination type: azure. Client Credentials OAuth Client ID.
  *   azure_oauth_client_credentials_client_secret - string - Applicable only for destination type: azure. Client Credentials OAuth Client Secret.
  *   qradar_username - string - Applicable only for destination type: qradar. Basic auth username provided by QRadar.
  *   qradar_password - string - Applicable only for destination type: qradar. Basic auth password provided by QRadar.
  *   solar_winds_token - string - Applicable only for destination type: solar_winds. Authentication token provided by Solar Winds.
  *   new_relic_api_key - string - Applicable only for destination type: new_relic. API key provided by New Relic.
  *   datadog_api_key - string - Applicable only for destination type: datadog. API key provided by Datadog.
  *   sftp_action_send_enabled - boolean - Whether or not sending is enabled for sftp_action logs.
  *   ftp_action_send_enabled - boolean - Whether or not sending is enabled for ftp_action logs.
  *   web_dav_action_send_enabled - boolean - Whether or not sending is enabled for web_dav_action logs.
  *   sync_send_enabled - boolean - Whether or not sending is enabled for sync logs.
  *   outbound_connection_send_enabled - boolean - Whether or not sending is enabled for outbound_connection logs.
  *   automation_send_enabled - boolean - Whether or not sending is enabled for automation logs.
  *   api_request_send_enabled - boolean - Whether or not sending is enabled for api_request logs.
  *   public_hosting_request_send_enabled - boolean - Whether or not sending is enabled for public_hosting_request logs.
  *   email_send_enabled - boolean - Whether or not sending is enabled for email logs.
  *   exavault_api_request_send_enabled - boolean - Whether or not sending is enabled for exavault_api_request logs.
  */

  public static void sendTestEntry(HashMap<String, Object> parameters) throws RuntimeException {
    sendTestEntry(parameters, null);
  }


  public static void sendTestEntry(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("siem_http_destination_id") && !(parameters.get("siem_http_destination_id") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: siem_http_destination_id must be of type Long parameters[\"siem_http_destination_id\"]");
    }
    if (parameters.containsKey("destination_type") && !(parameters.get("destination_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: destination_type must be of type String parameters[\"destination_type\"]");
    }
    if (parameters.containsKey("destination_url") && !(parameters.get("destination_url") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: destination_url must be of type String parameters[\"destination_url\"]");
    }
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("additional_headers") && !(parameters.get("additional_headers") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: additional_headers must be of type Map<String, String> parameters[\"additional_headers\"]");
    }
    if (parameters.containsKey("sending_active") && !(parameters.get("sending_active") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: sending_active must be of type Boolean parameters[\"sending_active\"]");
    }
    if (parameters.containsKey("generic_payload_type") && !(parameters.get("generic_payload_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: generic_payload_type must be of type String parameters[\"generic_payload_type\"]");
    }
    if (parameters.containsKey("splunk_token") && !(parameters.get("splunk_token") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: splunk_token must be of type String parameters[\"splunk_token\"]");
    }
    if (parameters.containsKey("azure_dcr_immutable_id") && !(parameters.get("azure_dcr_immutable_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_dcr_immutable_id must be of type String parameters[\"azure_dcr_immutable_id\"]");
    }
    if (parameters.containsKey("azure_stream_name") && !(parameters.get("azure_stream_name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_stream_name must be of type String parameters[\"azure_stream_name\"]");
    }
    if (parameters.containsKey("azure_oauth_client_credentials_tenant_id") && !(parameters.get("azure_oauth_client_credentials_tenant_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_oauth_client_credentials_tenant_id must be of type String parameters[\"azure_oauth_client_credentials_tenant_id\"]");
    }
    if (parameters.containsKey("azure_oauth_client_credentials_client_id") && !(parameters.get("azure_oauth_client_credentials_client_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_oauth_client_credentials_client_id must be of type String parameters[\"azure_oauth_client_credentials_client_id\"]");
    }
    if (parameters.containsKey("azure_oauth_client_credentials_client_secret") && !(parameters.get("azure_oauth_client_credentials_client_secret") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_oauth_client_credentials_client_secret must be of type String parameters[\"azure_oauth_client_credentials_client_secret\"]");
    }
    if (parameters.containsKey("qradar_username") && !(parameters.get("qradar_username") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: qradar_username must be of type String parameters[\"qradar_username\"]");
    }
    if (parameters.containsKey("qradar_password") && !(parameters.get("qradar_password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: qradar_password must be of type String parameters[\"qradar_password\"]");
    }
    if (parameters.containsKey("solar_winds_token") && !(parameters.get("solar_winds_token") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: solar_winds_token must be of type String parameters[\"solar_winds_token\"]");
    }
    if (parameters.containsKey("new_relic_api_key") && !(parameters.get("new_relic_api_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: new_relic_api_key must be of type String parameters[\"new_relic_api_key\"]");
    }
    if (parameters.containsKey("datadog_api_key") && !(parameters.get("datadog_api_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: datadog_api_key must be of type String parameters[\"datadog_api_key\"]");
    }
    if (parameters.containsKey("sftp_action_send_enabled") && !(parameters.get("sftp_action_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: sftp_action_send_enabled must be of type Boolean parameters[\"sftp_action_send_enabled\"]");
    }
    if (parameters.containsKey("ftp_action_send_enabled") && !(parameters.get("ftp_action_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: ftp_action_send_enabled must be of type Boolean parameters[\"ftp_action_send_enabled\"]");
    }
    if (parameters.containsKey("web_dav_action_send_enabled") && !(parameters.get("web_dav_action_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: web_dav_action_send_enabled must be of type Boolean parameters[\"web_dav_action_send_enabled\"]");
    }
    if (parameters.containsKey("sync_send_enabled") && !(parameters.get("sync_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: sync_send_enabled must be of type Boolean parameters[\"sync_send_enabled\"]");
    }
    if (parameters.containsKey("outbound_connection_send_enabled") && !(parameters.get("outbound_connection_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: outbound_connection_send_enabled must be of type Boolean parameters[\"outbound_connection_send_enabled\"]");
    }
    if (parameters.containsKey("automation_send_enabled") && !(parameters.get("automation_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: automation_send_enabled must be of type Boolean parameters[\"automation_send_enabled\"]");
    }
    if (parameters.containsKey("api_request_send_enabled") && !(parameters.get("api_request_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: api_request_send_enabled must be of type Boolean parameters[\"api_request_send_enabled\"]");
    }
    if (parameters.containsKey("public_hosting_request_send_enabled") && !(parameters.get("public_hosting_request_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: public_hosting_request_send_enabled must be of type Boolean parameters[\"public_hosting_request_send_enabled\"]");
    }
    if (parameters.containsKey("email_send_enabled") && !(parameters.get("email_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: email_send_enabled must be of type Boolean parameters[\"email_send_enabled\"]");
    }
    if (parameters.containsKey("exavault_api_request_send_enabled") && !(parameters.get("exavault_api_request_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: exavault_api_request_send_enabled must be of type Boolean parameters[\"exavault_api_request_send_enabled\"]");
    }


    String url = String.format("%s%s/siem_http_destinations/send_test_entry", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    FilesClient.apiRequest(url, RequestMethods.POST, parameters, options);
  }


  /**
  * Parameters:
  *   name - string - Name for this Destination
  *   additional_headers - object - Additional HTTP Headers included in calls to the destination URL
  *   sending_active - boolean - Whether this SIEM HTTP Destination is currently being sent to or not
  *   generic_payload_type - string - Applicable only for destination type: generic. Indicates the type of HTTP body. Can be json_newline or json_array. json_newline is multiple log entries as JSON separated by newlines. json_array is a single JSON array containing multiple log entries as JSON.
  *   splunk_token - string - Applicable only for destination type: splunk. Authentication token provided by Splunk.
  *   azure_dcr_immutable_id - string - Applicable only for destination type: azure. Immutable ID of the Data Collection Rule.
  *   azure_stream_name - string - Applicable only for destination type: azure. Name of the stream in the DCR that represents the destination table.
  *   azure_oauth_client_credentials_tenant_id - string - Applicable only for destination type: azure. Client Credentials OAuth Tenant ID.
  *   azure_oauth_client_credentials_client_id - string - Applicable only for destination type: azure. Client Credentials OAuth Client ID.
  *   azure_oauth_client_credentials_client_secret - string - Applicable only for destination type: azure. Client Credentials OAuth Client Secret.
  *   qradar_username - string - Applicable only for destination type: qradar. Basic auth username provided by QRadar.
  *   qradar_password - string - Applicable only for destination type: qradar. Basic auth password provided by QRadar.
  *   solar_winds_token - string - Applicable only for destination type: solar_winds. Authentication token provided by Solar Winds.
  *   new_relic_api_key - string - Applicable only for destination type: new_relic. API key provided by New Relic.
  *   datadog_api_key - string - Applicable only for destination type: datadog. API key provided by Datadog.
  *   sftp_action_send_enabled - boolean - Whether or not sending is enabled for sftp_action logs.
  *   ftp_action_send_enabled - boolean - Whether or not sending is enabled for ftp_action logs.
  *   web_dav_action_send_enabled - boolean - Whether or not sending is enabled for web_dav_action logs.
  *   sync_send_enabled - boolean - Whether or not sending is enabled for sync logs.
  *   outbound_connection_send_enabled - boolean - Whether or not sending is enabled for outbound_connection logs.
  *   automation_send_enabled - boolean - Whether or not sending is enabled for automation logs.
  *   api_request_send_enabled - boolean - Whether or not sending is enabled for api_request logs.
  *   public_hosting_request_send_enabled - boolean - Whether or not sending is enabled for public_hosting_request logs.
  *   email_send_enabled - boolean - Whether or not sending is enabled for email logs.
  *   exavault_api_request_send_enabled - boolean - Whether or not sending is enabled for exavault_api_request logs.
  *   destination_type - string - Destination Type
  *   destination_url - string - Destination Url
  */

  public static SiemHttpDestination update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static SiemHttpDestination update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static SiemHttpDestination update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("additional_headers") && !(parameters.get("additional_headers") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: additional_headers must be of type Map<String, String> parameters[\"additional_headers\"]");
    }
    if (parameters.containsKey("sending_active") && !(parameters.get("sending_active") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: sending_active must be of type Boolean parameters[\"sending_active\"]");
    }
    if (parameters.containsKey("generic_payload_type") && !(parameters.get("generic_payload_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: generic_payload_type must be of type String parameters[\"generic_payload_type\"]");
    }
    if (parameters.containsKey("splunk_token") && !(parameters.get("splunk_token") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: splunk_token must be of type String parameters[\"splunk_token\"]");
    }
    if (parameters.containsKey("azure_dcr_immutable_id") && !(parameters.get("azure_dcr_immutable_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_dcr_immutable_id must be of type String parameters[\"azure_dcr_immutable_id\"]");
    }
    if (parameters.containsKey("azure_stream_name") && !(parameters.get("azure_stream_name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_stream_name must be of type String parameters[\"azure_stream_name\"]");
    }
    if (parameters.containsKey("azure_oauth_client_credentials_tenant_id") && !(parameters.get("azure_oauth_client_credentials_tenant_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_oauth_client_credentials_tenant_id must be of type String parameters[\"azure_oauth_client_credentials_tenant_id\"]");
    }
    if (parameters.containsKey("azure_oauth_client_credentials_client_id") && !(parameters.get("azure_oauth_client_credentials_client_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_oauth_client_credentials_client_id must be of type String parameters[\"azure_oauth_client_credentials_client_id\"]");
    }
    if (parameters.containsKey("azure_oauth_client_credentials_client_secret") && !(parameters.get("azure_oauth_client_credentials_client_secret") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: azure_oauth_client_credentials_client_secret must be of type String parameters[\"azure_oauth_client_credentials_client_secret\"]");
    }
    if (parameters.containsKey("qradar_username") && !(parameters.get("qradar_username") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: qradar_username must be of type String parameters[\"qradar_username\"]");
    }
    if (parameters.containsKey("qradar_password") && !(parameters.get("qradar_password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: qradar_password must be of type String parameters[\"qradar_password\"]");
    }
    if (parameters.containsKey("solar_winds_token") && !(parameters.get("solar_winds_token") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: solar_winds_token must be of type String parameters[\"solar_winds_token\"]");
    }
    if (parameters.containsKey("new_relic_api_key") && !(parameters.get("new_relic_api_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: new_relic_api_key must be of type String parameters[\"new_relic_api_key\"]");
    }
    if (parameters.containsKey("datadog_api_key") && !(parameters.get("datadog_api_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: datadog_api_key must be of type String parameters[\"datadog_api_key\"]");
    }
    if (parameters.containsKey("sftp_action_send_enabled") && !(parameters.get("sftp_action_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: sftp_action_send_enabled must be of type Boolean parameters[\"sftp_action_send_enabled\"]");
    }
    if (parameters.containsKey("ftp_action_send_enabled") && !(parameters.get("ftp_action_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: ftp_action_send_enabled must be of type Boolean parameters[\"ftp_action_send_enabled\"]");
    }
    if (parameters.containsKey("web_dav_action_send_enabled") && !(parameters.get("web_dav_action_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: web_dav_action_send_enabled must be of type Boolean parameters[\"web_dav_action_send_enabled\"]");
    }
    if (parameters.containsKey("sync_send_enabled") && !(parameters.get("sync_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: sync_send_enabled must be of type Boolean parameters[\"sync_send_enabled\"]");
    }
    if (parameters.containsKey("outbound_connection_send_enabled") && !(parameters.get("outbound_connection_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: outbound_connection_send_enabled must be of type Boolean parameters[\"outbound_connection_send_enabled\"]");
    }
    if (parameters.containsKey("automation_send_enabled") && !(parameters.get("automation_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: automation_send_enabled must be of type Boolean parameters[\"automation_send_enabled\"]");
    }
    if (parameters.containsKey("api_request_send_enabled") && !(parameters.get("api_request_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: api_request_send_enabled must be of type Boolean parameters[\"api_request_send_enabled\"]");
    }
    if (parameters.containsKey("public_hosting_request_send_enabled") && !(parameters.get("public_hosting_request_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: public_hosting_request_send_enabled must be of type Boolean parameters[\"public_hosting_request_send_enabled\"]");
    }
    if (parameters.containsKey("email_send_enabled") && !(parameters.get("email_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: email_send_enabled must be of type Boolean parameters[\"email_send_enabled\"]");
    }
    if (parameters.containsKey("exavault_api_request_send_enabled") && !(parameters.get("exavault_api_request_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: exavault_api_request_send_enabled must be of type Boolean parameters[\"exavault_api_request_send_enabled\"]");
    }
    if (parameters.containsKey("destination_type") && !(parameters.get("destination_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: destination_type must be of type String parameters[\"destination_type\"]");
    }
    if (parameters.containsKey("destination_url") && !(parameters.get("destination_url") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: destination_url must be of type String parameters[\"destination_url\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/siem_http_destinations/%s", urlParts);

    TypeReference<SiemHttpDestination> typeReference = new TypeReference<SiemHttpDestination>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  */

  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/siem_http_destinations/%s", urlParts);

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
