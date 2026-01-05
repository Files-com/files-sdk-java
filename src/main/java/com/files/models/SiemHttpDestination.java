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
public class SiemHttpDestination implements ModelInterface {
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
  @JsonProperty("id")
  public Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /**
  * Name for this Destination
  */
  @JsonProperty("name")
  public String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
  * Destination Type
  */
  @JsonProperty("destination_type")
  public String destinationType;

  public String getDestinationType() {
    return destinationType;
  }

  public void setDestinationType(String destinationType) {
    this.destinationType = destinationType;
  }

  /**
  * Destination Url
  */
  @JsonProperty("destination_url")
  public String destinationUrl;

  public String getDestinationUrl() {
    return destinationUrl;
  }

  public void setDestinationUrl(String destinationUrl) {
    this.destinationUrl = destinationUrl;
  }

  /**
  * Applicable only for destination type: file. Destination folder path on Files.com.
  */
  @JsonProperty("file_destination_path")
  public String fileDestinationPath;

  public String getFileDestinationPath() {
    return fileDestinationPath;
  }

  public void setFileDestinationPath(String fileDestinationPath) {
    this.fileDestinationPath = fileDestinationPath;
  }

  /**
  * Applicable only for destination type: file. Generated file format.
  */
  @JsonProperty("file_format")
  public String fileFormat;

  public String getFileFormat() {
    return fileFormat;
  }

  public void setFileFormat(String fileFormat) {
    this.fileFormat = fileFormat;
  }

  /**
  * Applicable only for destination type: file. Interval, in minutes, between file deliveries.
  */
  @JsonProperty("file_interval_minutes")
  public Long fileIntervalMinutes;

  public Long getFileIntervalMinutes() {
    return fileIntervalMinutes;
  }

  public void setFileIntervalMinutes(Long fileIntervalMinutes) {
    this.fileIntervalMinutes = fileIntervalMinutes;
  }

  /**
  * Additional HTTP Headers included in calls to the destination URL
  */
  @JsonProperty("additional_headers")
  public Object additionalHeaders;

  public Object getAdditionalHeaders() {
    return additionalHeaders;
  }

  public void setAdditionalHeaders(Object additionalHeaders) {
    this.additionalHeaders = additionalHeaders;
  }

  /**
  * Whether this SIEM HTTP Destination is currently being sent to or not
  */
  @JsonProperty("sending_active")
  public Boolean sendingActive;

  public Boolean getSendingActive() {
    return sendingActive;
  }

  public void setSendingActive(Boolean sendingActive) {
    this.sendingActive = sendingActive;
  }

  /**
  * Applicable only for destination type: generic. Indicates the type of HTTP body. Can be json_newline or json_array. json_newline is multiple log entries as JSON separated by newlines. json_array is a single JSON array containing multiple log entries as JSON.
  */
  @JsonProperty("generic_payload_type")
  public String genericPayloadType;

  public String getGenericPayloadType() {
    return genericPayloadType;
  }

  public void setGenericPayloadType(String genericPayloadType) {
    this.genericPayloadType = genericPayloadType;
  }

  /**
  * Applicable only for destination type: splunk. Authentication token provided by Splunk.
  */
  @JsonProperty("splunk_token_masked")
  public String splunkTokenMasked;

  public String getSplunkTokenMasked() {
    return splunkTokenMasked;
  }

  public void setSplunkTokenMasked(String splunkTokenMasked) {
    this.splunkTokenMasked = splunkTokenMasked;
  }

  /**
  * Applicable only for destination types: azure, azure_legacy. Immutable ID of the Data Collection Rule.
  */
  @JsonProperty("azure_dcr_immutable_id")
  public String azureDcrImmutableId;

  public String getAzureDcrImmutableId() {
    return azureDcrImmutableId;
  }

  public void setAzureDcrImmutableId(String azureDcrImmutableId) {
    this.azureDcrImmutableId = azureDcrImmutableId;
  }

  /**
  * Applicable only for destination type: azure. Name of the stream in the DCR that represents the destination table.
  */
  @JsonProperty("azure_stream_name")
  public String azureStreamName;

  public String getAzureStreamName() {
    return azureStreamName;
  }

  public void setAzureStreamName(String azureStreamName) {
    this.azureStreamName = azureStreamName;
  }

  /**
  * Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Tenant ID.
  */
  @JsonProperty("azure_oauth_client_credentials_tenant_id")
  public String azureOauthClientCredentialsTenantId;

  public String getAzureOauthClientCredentialsTenantId() {
    return azureOauthClientCredentialsTenantId;
  }

  public void setAzureOauthClientCredentialsTenantId(String azureOauthClientCredentialsTenantId) {
    this.azureOauthClientCredentialsTenantId = azureOauthClientCredentialsTenantId;
  }

  /**
  * Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Client ID.
  */
  @JsonProperty("azure_oauth_client_credentials_client_id")
  public String azureOauthClientCredentialsClientId;

  public String getAzureOauthClientCredentialsClientId() {
    return azureOauthClientCredentialsClientId;
  }

  public void setAzureOauthClientCredentialsClientId(String azureOauthClientCredentialsClientId) {
    this.azureOauthClientCredentialsClientId = azureOauthClientCredentialsClientId;
  }

  /**
  * Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Client Secret.
  */
  @JsonProperty("azure_oauth_client_credentials_client_secret_masked")
  public String azureOauthClientCredentialsClientSecretMasked;

  public String getAzureOauthClientCredentialsClientSecretMasked() {
    return azureOauthClientCredentialsClientSecretMasked;
  }

  public void setAzureOauthClientCredentialsClientSecretMasked(String azureOauthClientCredentialsClientSecretMasked) {
    this.azureOauthClientCredentialsClientSecretMasked = azureOauthClientCredentialsClientSecretMasked;
  }

  /**
  * Applicable only for destination type: qradar. Basic auth username provided by QRadar.
  */
  @JsonProperty("qradar_username")
  public String qradarUsername;

  public String getQradarUsername() {
    return qradarUsername;
  }

  public void setQradarUsername(String qradarUsername) {
    this.qradarUsername = qradarUsername;
  }

  /**
  * Applicable only for destination type: qradar. Basic auth password provided by QRadar.
  */
  @JsonProperty("qradar_password_masked")
  public String qradarPasswordMasked;

  public String getQradarPasswordMasked() {
    return qradarPasswordMasked;
  }

  public void setQradarPasswordMasked(String qradarPasswordMasked) {
    this.qradarPasswordMasked = qradarPasswordMasked;
  }

  /**
  * Applicable only for destination type: solar_winds. Authentication token provided by Solar Winds.
  */
  @JsonProperty("solar_winds_token_masked")
  public String solarWindsTokenMasked;

  public String getSolarWindsTokenMasked() {
    return solarWindsTokenMasked;
  }

  public void setSolarWindsTokenMasked(String solarWindsTokenMasked) {
    this.solarWindsTokenMasked = solarWindsTokenMasked;
  }

  /**
  * Applicable only for destination type: new_relic. API key provided by New Relic.
  */
  @JsonProperty("new_relic_api_key_masked")
  public String newRelicApiKeyMasked;

  public String getNewRelicApiKeyMasked() {
    return newRelicApiKeyMasked;
  }

  public void setNewRelicApiKeyMasked(String newRelicApiKeyMasked) {
    this.newRelicApiKeyMasked = newRelicApiKeyMasked;
  }

  /**
  * Applicable only for destination type: datadog. API key provided by Datadog.
  */
  @JsonProperty("datadog_api_key_masked")
  public String datadogApiKeyMasked;

  public String getDatadogApiKeyMasked() {
    return datadogApiKeyMasked;
  }

  public void setDatadogApiKeyMasked(String datadogApiKeyMasked) {
    this.datadogApiKeyMasked = datadogApiKeyMasked;
  }

  /**
  * Whether or not sending is enabled for sftp_action logs.
  */
  @JsonProperty("sftp_action_send_enabled")
  public Boolean sftpActionSendEnabled;

  public Boolean getSftpActionSendEnabled() {
    return sftpActionSendEnabled;
  }

  public void setSftpActionSendEnabled(Boolean sftpActionSendEnabled) {
    this.sftpActionSendEnabled = sftpActionSendEnabled;
  }

  /**
  * Number of log entries sent for the lifetime of this destination.
  */
  @JsonProperty("sftp_action_entries_sent")
  public Long sftpActionEntriesSent;

  public Long getSftpActionEntriesSent() {
    return sftpActionEntriesSent;
  }

  public void setSftpActionEntriesSent(Long sftpActionEntriesSent) {
    this.sftpActionEntriesSent = sftpActionEntriesSent;
  }

  /**
  * Whether or not sending is enabled for ftp_action logs.
  */
  @JsonProperty("ftp_action_send_enabled")
  public Boolean ftpActionSendEnabled;

  public Boolean getFtpActionSendEnabled() {
    return ftpActionSendEnabled;
  }

  public void setFtpActionSendEnabled(Boolean ftpActionSendEnabled) {
    this.ftpActionSendEnabled = ftpActionSendEnabled;
  }

  /**
  * Number of log entries sent for the lifetime of this destination.
  */
  @JsonProperty("ftp_action_entries_sent")
  public Long ftpActionEntriesSent;

  public Long getFtpActionEntriesSent() {
    return ftpActionEntriesSent;
  }

  public void setFtpActionEntriesSent(Long ftpActionEntriesSent) {
    this.ftpActionEntriesSent = ftpActionEntriesSent;
  }

  /**
  * Whether or not sending is enabled for web_dav_action logs.
  */
  @JsonProperty("web_dav_action_send_enabled")
  public Boolean webDavActionSendEnabled;

  public Boolean getWebDavActionSendEnabled() {
    return webDavActionSendEnabled;
  }

  public void setWebDavActionSendEnabled(Boolean webDavActionSendEnabled) {
    this.webDavActionSendEnabled = webDavActionSendEnabled;
  }

  /**
  * Number of log entries sent for the lifetime of this destination.
  */
  @JsonProperty("web_dav_action_entries_sent")
  public Long webDavActionEntriesSent;

  public Long getWebDavActionEntriesSent() {
    return webDavActionEntriesSent;
  }

  public void setWebDavActionEntriesSent(Long webDavActionEntriesSent) {
    this.webDavActionEntriesSent = webDavActionEntriesSent;
  }

  /**
  * Whether or not sending is enabled for sync logs.
  */
  @JsonProperty("sync_send_enabled")
  public Boolean syncSendEnabled;

  public Boolean getSyncSendEnabled() {
    return syncSendEnabled;
  }

  public void setSyncSendEnabled(Boolean syncSendEnabled) {
    this.syncSendEnabled = syncSendEnabled;
  }

  /**
  * Number of log entries sent for the lifetime of this destination.
  */
  @JsonProperty("sync_entries_sent")
  public Long syncEntriesSent;

  public Long getSyncEntriesSent() {
    return syncEntriesSent;
  }

  public void setSyncEntriesSent(Long syncEntriesSent) {
    this.syncEntriesSent = syncEntriesSent;
  }

  /**
  * Whether or not sending is enabled for outbound_connection logs.
  */
  @JsonProperty("outbound_connection_send_enabled")
  public Boolean outboundConnectionSendEnabled;

  public Boolean getOutboundConnectionSendEnabled() {
    return outboundConnectionSendEnabled;
  }

  public void setOutboundConnectionSendEnabled(Boolean outboundConnectionSendEnabled) {
    this.outboundConnectionSendEnabled = outboundConnectionSendEnabled;
  }

  /**
  * Number of log entries sent for the lifetime of this destination.
  */
  @JsonProperty("outbound_connection_entries_sent")
  public Long outboundConnectionEntriesSent;

  public Long getOutboundConnectionEntriesSent() {
    return outboundConnectionEntriesSent;
  }

  public void setOutboundConnectionEntriesSent(Long outboundConnectionEntriesSent) {
    this.outboundConnectionEntriesSent = outboundConnectionEntriesSent;
  }

  /**
  * Whether or not sending is enabled for automation logs.
  */
  @JsonProperty("automation_send_enabled")
  public Boolean automationSendEnabled;

  public Boolean getAutomationSendEnabled() {
    return automationSendEnabled;
  }

  public void setAutomationSendEnabled(Boolean automationSendEnabled) {
    this.automationSendEnabled = automationSendEnabled;
  }

  /**
  * Number of log entries sent for the lifetime of this destination.
  */
  @JsonProperty("automation_entries_sent")
  public Long automationEntriesSent;

  public Long getAutomationEntriesSent() {
    return automationEntriesSent;
  }

  public void setAutomationEntriesSent(Long automationEntriesSent) {
    this.automationEntriesSent = automationEntriesSent;
  }

  /**
  * Whether or not sending is enabled for api_request logs.
  */
  @JsonProperty("api_request_send_enabled")
  public Boolean apiRequestSendEnabled;

  public Boolean getApiRequestSendEnabled() {
    return apiRequestSendEnabled;
  }

  public void setApiRequestSendEnabled(Boolean apiRequestSendEnabled) {
    this.apiRequestSendEnabled = apiRequestSendEnabled;
  }

  /**
  * Number of log entries sent for the lifetime of this destination.
  */
  @JsonProperty("api_request_entries_sent")
  public Long apiRequestEntriesSent;

  public Long getApiRequestEntriesSent() {
    return apiRequestEntriesSent;
  }

  public void setApiRequestEntriesSent(Long apiRequestEntriesSent) {
    this.apiRequestEntriesSent = apiRequestEntriesSent;
  }

  /**
  * Whether or not sending is enabled for public_hosting_request logs.
  */
  @JsonProperty("public_hosting_request_send_enabled")
  public Boolean publicHostingRequestSendEnabled;

  public Boolean getPublicHostingRequestSendEnabled() {
    return publicHostingRequestSendEnabled;
  }

  public void setPublicHostingRequestSendEnabled(Boolean publicHostingRequestSendEnabled) {
    this.publicHostingRequestSendEnabled = publicHostingRequestSendEnabled;
  }

  /**
  * Number of log entries sent for the lifetime of this destination.
  */
  @JsonProperty("public_hosting_request_entries_sent")
  public Long publicHostingRequestEntriesSent;

  public Long getPublicHostingRequestEntriesSent() {
    return publicHostingRequestEntriesSent;
  }

  public void setPublicHostingRequestEntriesSent(Long publicHostingRequestEntriesSent) {
    this.publicHostingRequestEntriesSent = publicHostingRequestEntriesSent;
  }

  /**
  * Whether or not sending is enabled for email logs.
  */
  @JsonProperty("email_send_enabled")
  public Boolean emailSendEnabled;

  public Boolean getEmailSendEnabled() {
    return emailSendEnabled;
  }

  public void setEmailSendEnabled(Boolean emailSendEnabled) {
    this.emailSendEnabled = emailSendEnabled;
  }

  /**
  * Number of log entries sent for the lifetime of this destination.
  */
  @JsonProperty("email_entries_sent")
  public Long emailEntriesSent;

  public Long getEmailEntriesSent() {
    return emailEntriesSent;
  }

  public void setEmailEntriesSent(Long emailEntriesSent) {
    this.emailEntriesSent = emailEntriesSent;
  }

  /**
  * Whether or not sending is enabled for exavault_api_request logs.
  */
  @JsonProperty("exavault_api_request_send_enabled")
  public Boolean exavaultApiRequestSendEnabled;

  public Boolean getExavaultApiRequestSendEnabled() {
    return exavaultApiRequestSendEnabled;
  }

  public void setExavaultApiRequestSendEnabled(Boolean exavaultApiRequestSendEnabled) {
    this.exavaultApiRequestSendEnabled = exavaultApiRequestSendEnabled;
  }

  /**
  * Number of log entries sent for the lifetime of this destination.
  */
  @JsonProperty("exavault_api_request_entries_sent")
  public Long exavaultApiRequestEntriesSent;

  public Long getExavaultApiRequestEntriesSent() {
    return exavaultApiRequestEntriesSent;
  }

  public void setExavaultApiRequestEntriesSent(Long exavaultApiRequestEntriesSent) {
    this.exavaultApiRequestEntriesSent = exavaultApiRequestEntriesSent;
  }

  /**
  * Whether or not sending is enabled for settings_change logs.
  */
  @JsonProperty("settings_change_send_enabled")
  public Boolean settingsChangeSendEnabled;

  public Boolean getSettingsChangeSendEnabled() {
    return settingsChangeSendEnabled;
  }

  public void setSettingsChangeSendEnabled(Boolean settingsChangeSendEnabled) {
    this.settingsChangeSendEnabled = settingsChangeSendEnabled;
  }

  /**
  * Number of log entries sent for the lifetime of this destination.
  */
  @JsonProperty("settings_change_entries_sent")
  public Long settingsChangeEntriesSent;

  public Long getSettingsChangeEntriesSent() {
    return settingsChangeEntriesSent;
  }

  public void setSettingsChangeEntriesSent(Long settingsChangeEntriesSent) {
    this.settingsChangeEntriesSent = settingsChangeEntriesSent;
  }

  /**
  * Type of URL that was last called. Can be `destination_url` or `azure_oauth_client_credentials_url`
  */
  @JsonProperty("last_http_call_target_type")
  public String lastHttpCallTargetType;

  public String getLastHttpCallTargetType() {
    return lastHttpCallTargetType;
  }

  public void setLastHttpCallTargetType(String lastHttpCallTargetType) {
    this.lastHttpCallTargetType = lastHttpCallTargetType;
  }

  /**
  * Was the last HTTP call made successful?
  */
  @JsonProperty("last_http_call_success")
  public Boolean lastHttpCallSuccess;

  public Boolean getLastHttpCallSuccess() {
    return lastHttpCallSuccess;
  }

  public void setLastHttpCallSuccess(Boolean lastHttpCallSuccess) {
    this.lastHttpCallSuccess = lastHttpCallSuccess;
  }

  /**
  * Last HTTP Call Response Code
  */
  @JsonProperty("last_http_call_response_code")
  public Long lastHttpCallResponseCode;

  public Long getLastHttpCallResponseCode() {
    return lastHttpCallResponseCode;
  }

  public void setLastHttpCallResponseCode(Long lastHttpCallResponseCode) {
    this.lastHttpCallResponseCode = lastHttpCallResponseCode;
  }

  /**
  * Last HTTP Call Response Body. Large responses are truncated.
  */
  @JsonProperty("last_http_call_response_body")
  public String lastHttpCallResponseBody;

  public String getLastHttpCallResponseBody() {
    return lastHttpCallResponseBody;
  }

  public void setLastHttpCallResponseBody(String lastHttpCallResponseBody) {
    this.lastHttpCallResponseBody = lastHttpCallResponseBody;
  }

  /**
  * Last HTTP Call Error Message if applicable
  */
  @JsonProperty("last_http_call_error_message")
  public String lastHttpCallErrorMessage;

  public String getLastHttpCallErrorMessage() {
    return lastHttpCallErrorMessage;
  }

  public void setLastHttpCallErrorMessage(String lastHttpCallErrorMessage) {
    this.lastHttpCallErrorMessage = lastHttpCallErrorMessage;
  }

  /**
  * Time of Last HTTP Call
  */
  @JsonProperty("last_http_call_time")
  public String lastHttpCallTime;

  public String getLastHttpCallTime() {
    return lastHttpCallTime;
  }

  public void setLastHttpCallTime(String lastHttpCallTime) {
    this.lastHttpCallTime = lastHttpCallTime;
  }

  /**
  * Duration of the last HTTP Call in milliseconds
  */
  @JsonProperty("last_http_call_duration_ms")
  public Long lastHttpCallDurationMs;

  public Long getLastHttpCallDurationMs() {
    return lastHttpCallDurationMs;
  }

  public void setLastHttpCallDurationMs(Long lastHttpCallDurationMs) {
    this.lastHttpCallDurationMs = lastHttpCallDurationMs;
  }

  /**
  * Time of Most Recent Successful HTTP Call
  */
  @JsonProperty("most_recent_http_call_success_time")
  public String mostRecentHttpCallSuccessTime;

  public String getMostRecentHttpCallSuccessTime() {
    return mostRecentHttpCallSuccessTime;
  }

  public void setMostRecentHttpCallSuccessTime(String mostRecentHttpCallSuccessTime) {
    this.mostRecentHttpCallSuccessTime = mostRecentHttpCallSuccessTime;
  }

  /**
  * Connection Test Entry
  */
  @JsonProperty("connection_test_entry")
  public String connectionTestEntry;

  public String getConnectionTestEntry() {
    return connectionTestEntry;
  }

  public void setConnectionTestEntry(String connectionTestEntry) {
    this.connectionTestEntry = connectionTestEntry;
  }

  /**
  * Applicable only for destination type: splunk. Authentication token provided by Splunk.
  */
  @JsonProperty("splunk_token")
  public String splunkToken;

  public String getSplunkToken() {
    return splunkToken;
  }

  public void setSplunkToken(String splunkToken) {
    this.splunkToken = splunkToken;
  }

  /**
  * Applicable only for destination type: azure. Client Credentials OAuth Client Secret.
  */
  @JsonProperty("azure_oauth_client_credentials_client_secret")
  public String azureOauthClientCredentialsClientSecret;

  public String getAzureOauthClientCredentialsClientSecret() {
    return azureOauthClientCredentialsClientSecret;
  }

  public void setAzureOauthClientCredentialsClientSecret(String azureOauthClientCredentialsClientSecret) {
    this.azureOauthClientCredentialsClientSecret = azureOauthClientCredentialsClientSecret;
  }

  /**
  * Applicable only for destination type: qradar. Basic auth password provided by QRadar.
  */
  @JsonProperty("qradar_password")
  public String qradarPassword;

  public String getQradarPassword() {
    return qradarPassword;
  }

  public void setQradarPassword(String qradarPassword) {
    this.qradarPassword = qradarPassword;
  }

  /**
  * Applicable only for destination type: solar_winds. Authentication token provided by Solar Winds.
  */
  @JsonProperty("solar_winds_token")
  public String solarWindsToken;

  public String getSolarWindsToken() {
    return solarWindsToken;
  }

  public void setSolarWindsToken(String solarWindsToken) {
    this.solarWindsToken = solarWindsToken;
  }

  /**
  * Applicable only for destination type: new_relic. API key provided by New Relic.
  */
  @JsonProperty("new_relic_api_key")
  public String newRelicApiKey;

  public String getNewRelicApiKey() {
    return newRelicApiKey;
  }

  public void setNewRelicApiKey(String newRelicApiKey) {
    this.newRelicApiKey = newRelicApiKey;
  }

  /**
  * Applicable only for destination type: datadog. API key provided by Datadog.
  */
  @JsonProperty("datadog_api_key")
  public String datadogApiKey;

  public String getDatadogApiKey() {
    return datadogApiKey;
  }

  public void setDatadogApiKey(String datadogApiKey) {
    this.datadogApiKey = datadogApiKey;
  }

  /**
  * Parameters:
  *   name - string - Name for this Destination
  *   additional_headers - object - Additional HTTP Headers included in calls to the destination URL
  *   sending_active - boolean - Whether this SIEM HTTP Destination is currently being sent to or not
  *   generic_payload_type - string - Applicable only for destination type: generic. Indicates the type of HTTP body. Can be json_newline or json_array. json_newline is multiple log entries as JSON separated by newlines. json_array is a single JSON array containing multiple log entries as JSON.
  *   file_destination_path - string - Applicable only for destination type: file. Destination folder path on Files.com.
  *   file_format - string - Applicable only for destination type: file. Generated file format.
  *   file_interval_minutes - int64 - Applicable only for destination type: file. Interval, in minutes, between file deliveries. Valid values are 5, 10, 15, 20, 30, 60, 90, 180, 240, 360.
  *   splunk_token - string - Applicable only for destination type: splunk. Authentication token provided by Splunk.
  *   azure_dcr_immutable_id - string - Applicable only for destination types: azure, azure_legacy. Immutable ID of the Data Collection Rule.
  *   azure_stream_name - string - Applicable only for destination type: azure. Name of the stream in the DCR that represents the destination table.
  *   azure_oauth_client_credentials_tenant_id - string - Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Tenant ID.
  *   azure_oauth_client_credentials_client_id - string - Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Client ID.
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
  *   settings_change_send_enabled - boolean - Whether or not sending is enabled for settings_change logs.
  *   destination_type - string - Destination Type
  *   destination_url - string - Destination Url
  */
  public SiemHttpDestination update(HashMap<String, Object> parameters) throws IOException {
    return SiemHttpDestination.update(this.id, parameters, this.options);
  }

  /**
  */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    SiemHttpDestination.delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
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
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
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
  public static SiemHttpDestination find() throws RuntimeException {
    return find(null, null, null);
  }

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

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }



    String url = String.format("%s%s/siem_http_destinations/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

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
  *   file_destination_path - string - Applicable only for destination type: file. Destination folder path on Files.com.
  *   file_format - string - Applicable only for destination type: file. Generated file format.
  *   file_interval_minutes - int64 - Applicable only for destination type: file. Interval, in minutes, between file deliveries. Valid values are 5, 10, 15, 20, 30, 60, 90, 180, 240, 360.
  *   splunk_token - string - Applicable only for destination type: splunk. Authentication token provided by Splunk.
  *   azure_dcr_immutable_id - string - Applicable only for destination types: azure, azure_legacy. Immutable ID of the Data Collection Rule.
  *   azure_stream_name - string - Applicable only for destination type: azure. Name of the stream in the DCR that represents the destination table.
  *   azure_oauth_client_credentials_tenant_id - string - Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Tenant ID.
  *   azure_oauth_client_credentials_client_id - string - Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Client ID.
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
  *   settings_change_send_enabled - boolean - Whether or not sending is enabled for settings_change logs.
  *   destination_type (required) - string - Destination Type
  *   destination_url - string - Destination Url
  */
  public static SiemHttpDestination create() throws RuntimeException {
    return create(null, null);
  }

  public static SiemHttpDestination create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static SiemHttpDestination create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (!parameters.containsKey("destination_type") || parameters.get("destination_type") == null) {
      throw new NullPointerException("Parameter missing: destination_type parameters[\"destination_type\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("additional_headers") && !(parameters.get("additional_headers") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: additional_headers must be of type Object parameters[\"additional_headers\"]");
    }
    if (parameters.containsKey("sending_active") && !(parameters.get("sending_active") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: sending_active must be of type Boolean parameters[\"sending_active\"]");
    }
    if (parameters.containsKey("generic_payload_type") && !(parameters.get("generic_payload_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: generic_payload_type must be of type String parameters[\"generic_payload_type\"]");
    }
    if (parameters.containsKey("file_destination_path") && !(parameters.get("file_destination_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: file_destination_path must be of type String parameters[\"file_destination_path\"]");
    }
    if (parameters.containsKey("file_format") && !(parameters.get("file_format") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: file_format must be of type String parameters[\"file_format\"]");
    }
    if (parameters.containsKey("file_interval_minutes") && !(parameters.get("file_interval_minutes") instanceof Long || parameters.get("file_interval_minutes") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: file_interval_minutes must be of type Long or Integer parameters[\"file_interval_minutes\"]");
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
    if (parameters.containsKey("settings_change_send_enabled") && !(parameters.get("settings_change_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: settings_change_send_enabled must be of type Boolean parameters[\"settings_change_send_enabled\"]");
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
  *   file_destination_path - string - Applicable only for destination type: file. Destination folder path on Files.com.
  *   file_format - string - Applicable only for destination type: file. Generated file format.
  *   file_interval_minutes - int64 - Applicable only for destination type: file. Interval, in minutes, between file deliveries. Valid values are 5, 10, 15, 20, 30, 60, 90, 180, 240, 360.
  *   splunk_token - string - Applicable only for destination type: splunk. Authentication token provided by Splunk.
  *   azure_dcr_immutable_id - string - Applicable only for destination types: azure, azure_legacy. Immutable ID of the Data Collection Rule.
  *   azure_stream_name - string - Applicable only for destination type: azure. Name of the stream in the DCR that represents the destination table.
  *   azure_oauth_client_credentials_tenant_id - string - Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Tenant ID.
  *   azure_oauth_client_credentials_client_id - string - Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Client ID.
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
  *   settings_change_send_enabled - boolean - Whether or not sending is enabled for settings_change logs.
  */
  public static void sendTestEntry() throws RuntimeException {
    sendTestEntry(null, null);
  }

  public static void sendTestEntry(HashMap<String, Object> parameters) throws RuntimeException {
    sendTestEntry(parameters, null);
  }


  public static void sendTestEntry(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("siem_http_destination_id") && !(parameters.get("siem_http_destination_id") instanceof Long || parameters.get("siem_http_destination_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: siem_http_destination_id must be of type Long or Integer parameters[\"siem_http_destination_id\"]");
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
    if (parameters.containsKey("additional_headers") && !(parameters.get("additional_headers") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: additional_headers must be of type Object parameters[\"additional_headers\"]");
    }
    if (parameters.containsKey("sending_active") && !(parameters.get("sending_active") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: sending_active must be of type Boolean parameters[\"sending_active\"]");
    }
    if (parameters.containsKey("generic_payload_type") && !(parameters.get("generic_payload_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: generic_payload_type must be of type String parameters[\"generic_payload_type\"]");
    }
    if (parameters.containsKey("file_destination_path") && !(parameters.get("file_destination_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: file_destination_path must be of type String parameters[\"file_destination_path\"]");
    }
    if (parameters.containsKey("file_format") && !(parameters.get("file_format") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: file_format must be of type String parameters[\"file_format\"]");
    }
    if (parameters.containsKey("file_interval_minutes") && !(parameters.get("file_interval_minutes") instanceof Long || parameters.get("file_interval_minutes") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: file_interval_minutes must be of type Long or Integer parameters[\"file_interval_minutes\"]");
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
    if (parameters.containsKey("settings_change_send_enabled") && !(parameters.get("settings_change_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: settings_change_send_enabled must be of type Boolean parameters[\"settings_change_send_enabled\"]");
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
  *   file_destination_path - string - Applicable only for destination type: file. Destination folder path on Files.com.
  *   file_format - string - Applicable only for destination type: file. Generated file format.
  *   file_interval_minutes - int64 - Applicable only for destination type: file. Interval, in minutes, between file deliveries. Valid values are 5, 10, 15, 20, 30, 60, 90, 180, 240, 360.
  *   splunk_token - string - Applicable only for destination type: splunk. Authentication token provided by Splunk.
  *   azure_dcr_immutable_id - string - Applicable only for destination types: azure, azure_legacy. Immutable ID of the Data Collection Rule.
  *   azure_stream_name - string - Applicable only for destination type: azure. Name of the stream in the DCR that represents the destination table.
  *   azure_oauth_client_credentials_tenant_id - string - Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Tenant ID.
  *   azure_oauth_client_credentials_client_id - string - Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Client ID.
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
  *   settings_change_send_enabled - boolean - Whether or not sending is enabled for settings_change logs.
  *   destination_type - string - Destination Type
  *   destination_url - string - Destination Url
  */
  public static SiemHttpDestination update() throws RuntimeException {
    return update(null, null, null);
  }

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

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("additional_headers") && !(parameters.get("additional_headers") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: additional_headers must be of type Object parameters[\"additional_headers\"]");
    }
    if (parameters.containsKey("sending_active") && !(parameters.get("sending_active") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: sending_active must be of type Boolean parameters[\"sending_active\"]");
    }
    if (parameters.containsKey("generic_payload_type") && !(parameters.get("generic_payload_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: generic_payload_type must be of type String parameters[\"generic_payload_type\"]");
    }
    if (parameters.containsKey("file_destination_path") && !(parameters.get("file_destination_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: file_destination_path must be of type String parameters[\"file_destination_path\"]");
    }
    if (parameters.containsKey("file_format") && !(parameters.get("file_format") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: file_format must be of type String parameters[\"file_format\"]");
    }
    if (parameters.containsKey("file_interval_minutes") && !(parameters.get("file_interval_minutes") instanceof Long || parameters.get("file_interval_minutes") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: file_interval_minutes must be of type Long or Integer parameters[\"file_interval_minutes\"]");
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
    if (parameters.containsKey("settings_change_send_enabled") && !(parameters.get("settings_change_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: settings_change_send_enabled must be of type Boolean parameters[\"settings_change_send_enabled\"]");
    }
    if (parameters.containsKey("destination_type") && !(parameters.get("destination_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: destination_type must be of type String parameters[\"destination_type\"]");
    }
    if (parameters.containsKey("destination_url") && !(parameters.get("destination_url") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: destination_url must be of type String parameters[\"destination_url\"]");
    }



    String url = String.format("%s%s/siem_http_destinations/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<SiemHttpDestination> typeReference = new TypeReference<SiemHttpDestination>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

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

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }



    String url = String.format("%s%s/siem_http_destinations/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
