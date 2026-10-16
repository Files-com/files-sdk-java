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
import com.files.util.ModelUtils;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SiemHttpDestination implements ModelInterface {
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

  // One mapper for every SiemHttpDestination, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  public SiemHttpDestination() {
    this(null, null);
  }

  public SiemHttpDestination(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public SiemHttpDestination(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
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
   * Applicable only for destination types: splunk, splunk_compatible. Authentication token for the destination.
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
   * Applicable only for destination type: crowdstrike. Authentication token provided by Crowdstrike.
   */
  @JsonProperty("crowdstrike_token_masked")
  public String crowdstrikeTokenMasked;

  public String getCrowdstrikeTokenMasked() {
    return crowdstrikeTokenMasked;
  }

  public void setCrowdstrikeTokenMasked(String crowdstrikeTokenMasked) {
    this.crowdstrikeTokenMasked = crowdstrikeTokenMasked;
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
   * Whether or not sending is enabled for action logs.
   */
  @JsonProperty("action_send_enabled")
  public Boolean actionSendEnabled;

  public Boolean getActionSendEnabled() {
    return actionSendEnabled;
  }

  public void setActionSendEnabled(Boolean actionSendEnabled) {
    this.actionSendEnabled = actionSendEnabled;
  }

  /**
   * Number of log entries sent for the lifetime of this destination.
   */
  @JsonProperty("action_entries_sent")
  public Long actionEntriesSent;

  public Long getActionEntriesSent() {
    return actionEntriesSent;
  }

  public void setActionEntriesSent(Long actionEntriesSent) {
    this.actionEntriesSent = actionEntriesSent;
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
   * Applicable only for destination types: splunk, splunk_compatible. Authentication token for the destination.
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
   * Applicable only for destination type: crowdstrike. Authentication token provided by Crowdstrike.
   */
  @JsonProperty("crowdstrike_token")
  public String crowdstrikeToken;

  public String getCrowdstrikeToken() {
    return crowdstrikeToken;
  }

  public void setCrowdstrikeToken(String crowdstrikeToken) {
    this.crowdstrikeToken = crowdstrikeToken;
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
   * Updates this SiemHttpDestination. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Parameters:
   *   name - string - Name for this Destination
   *   additional_headers - object - Additional HTTP Headers included in calls to the destination URL
   *   sending_active - boolean - Whether this SIEM HTTP Destination is currently being sent to or not
   *   generic_payload_type - string - Applicable only for destination type: generic. Indicates the type of HTTP body. Can be json_newline or json_array. json_newline is multiple log entries as JSON separated by newlines. json_array is a single JSON array containing multiple log entries as JSON.
   *   file_destination_path - string - Applicable only for destination type: file. Destination folder path on Files.com.
   *   file_format - string - Applicable only for destination type: file. Generated file format.
   *   file_interval_minutes - int64 - Applicable only for destination type: file. Interval, in minutes, between file deliveries. Valid values are 5, 10, 15, 20, 30, 60, 90, 180, 240, 360.
   *   splunk_token - string - Applicable only for destination types: splunk, splunk_compatible. Authentication token for the destination.
   *   crowdstrike_token - string - Applicable only for destination type: crowdstrike. Authentication token provided by Crowdstrike.
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
   *   action_send_enabled - boolean - Whether or not sending is enabled for action logs.
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
    SiemHttpDestination updated = filesClient().siemHttpDestinations().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().siemHttpDestinations().delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this SiemHttpDestination: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().siemHttpDestinations().create(parameters, this.options));
    }
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    return ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(SiemHttpDestination canonical) {
    this.id = canonical.id;
    this.name = canonical.name;
    this.destinationType = canonical.destinationType;
    this.destinationUrl = canonical.destinationUrl;
    this.fileDestinationPath = canonical.fileDestinationPath;
    this.fileFormat = canonical.fileFormat;
    this.fileIntervalMinutes = canonical.fileIntervalMinutes;
    this.additionalHeaders = canonical.additionalHeaders;
    this.sendingActive = canonical.sendingActive;
    this.genericPayloadType = canonical.genericPayloadType;
    this.splunkTokenMasked = canonical.splunkTokenMasked;
    this.crowdstrikeTokenMasked = canonical.crowdstrikeTokenMasked;
    this.azureDcrImmutableId = canonical.azureDcrImmutableId;
    this.azureStreamName = canonical.azureStreamName;
    this.azureOauthClientCredentialsTenantId = canonical.azureOauthClientCredentialsTenantId;
    this.azureOauthClientCredentialsClientId = canonical.azureOauthClientCredentialsClientId;
    this.azureOauthClientCredentialsClientSecretMasked = canonical.azureOauthClientCredentialsClientSecretMasked;
    this.qradarUsername = canonical.qradarUsername;
    this.qradarPasswordMasked = canonical.qradarPasswordMasked;
    this.solarWindsTokenMasked = canonical.solarWindsTokenMasked;
    this.newRelicApiKeyMasked = canonical.newRelicApiKeyMasked;
    this.datadogApiKeyMasked = canonical.datadogApiKeyMasked;
    this.actionSendEnabled = canonical.actionSendEnabled;
    this.actionEntriesSent = canonical.actionEntriesSent;
    this.sftpActionSendEnabled = canonical.sftpActionSendEnabled;
    this.sftpActionEntriesSent = canonical.sftpActionEntriesSent;
    this.ftpActionSendEnabled = canonical.ftpActionSendEnabled;
    this.ftpActionEntriesSent = canonical.ftpActionEntriesSent;
    this.webDavActionSendEnabled = canonical.webDavActionSendEnabled;
    this.webDavActionEntriesSent = canonical.webDavActionEntriesSent;
    this.syncSendEnabled = canonical.syncSendEnabled;
    this.syncEntriesSent = canonical.syncEntriesSent;
    this.outboundConnectionSendEnabled = canonical.outboundConnectionSendEnabled;
    this.outboundConnectionEntriesSent = canonical.outboundConnectionEntriesSent;
    this.automationSendEnabled = canonical.automationSendEnabled;
    this.automationEntriesSent = canonical.automationEntriesSent;
    this.apiRequestSendEnabled = canonical.apiRequestSendEnabled;
    this.apiRequestEntriesSent = canonical.apiRequestEntriesSent;
    this.publicHostingRequestSendEnabled = canonical.publicHostingRequestSendEnabled;
    this.publicHostingRequestEntriesSent = canonical.publicHostingRequestEntriesSent;
    this.emailSendEnabled = canonical.emailSendEnabled;
    this.emailEntriesSent = canonical.emailEntriesSent;
    this.exavaultApiRequestSendEnabled = canonical.exavaultApiRequestSendEnabled;
    this.exavaultApiRequestEntriesSent = canonical.exavaultApiRequestEntriesSent;
    this.settingsChangeSendEnabled = canonical.settingsChangeSendEnabled;
    this.settingsChangeEntriesSent = canonical.settingsChangeEntriesSent;
    this.lastHttpCallTargetType = canonical.lastHttpCallTargetType;
    this.lastHttpCallSuccess = canonical.lastHttpCallSuccess;
    this.lastHttpCallResponseCode = canonical.lastHttpCallResponseCode;
    this.lastHttpCallResponseBody = canonical.lastHttpCallResponseBody;
    this.lastHttpCallErrorMessage = canonical.lastHttpCallErrorMessage;
    this.lastHttpCallTime = canonical.lastHttpCallTime;
    this.lastHttpCallDurationMs = canonical.lastHttpCallDurationMs;
    this.mostRecentHttpCallSuccessTime = canonical.mostRecentHttpCallSuccessTime;
    this.connectionTestEntry = canonical.connectionTestEntry;
    this.splunkToken = canonical.splunkToken;
    this.crowdstrikeToken = canonical.crowdstrikeToken;
    this.azureOauthClientCredentialsClientSecret = canonical.azureOauthClientCredentialsClientSecret;
    this.qradarPassword = canonical.qradarPassword;
    this.solarWindsToken = canonical.solarWindsToken;
    this.newRelicApiKey = canonical.newRelicApiKey;
    this.datadogApiKey = canonical.datadogApiKey;
  }

  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   */
  public static ListIterator<SiemHttpDestination> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<SiemHttpDestination> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<SiemHttpDestination> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().siemHttpDestinations().list(parameters, options);
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
    return FilesClient.defaultClient().siemHttpDestinations().find(id, parameters, options);
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
   *   splunk_token - string - Applicable only for destination types: splunk, splunk_compatible. Authentication token for the destination.
   *   crowdstrike_token - string - Applicable only for destination type: crowdstrike. Authentication token provided by Crowdstrike.
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
   *   action_send_enabled - boolean - Whether or not sending is enabled for action logs.
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
    return FilesClient.defaultClient().siemHttpDestinations().create(parameters, options);
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
   *   splunk_token - string - Applicable only for destination types: splunk, splunk_compatible. Authentication token for the destination.
   *   crowdstrike_token - string - Applicable only for destination type: crowdstrike. Authentication token provided by Crowdstrike.
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
   *   action_send_enabled - boolean - Whether or not sending is enabled for action logs.
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
    FilesClient.defaultClient().siemHttpDestinations().sendTestEntry(parameters, options);
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
   *   splunk_token - string - Applicable only for destination types: splunk, splunk_compatible. Authentication token for the destination.
   *   crowdstrike_token - string - Applicable only for destination type: crowdstrike. Authentication token provided by Crowdstrike.
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
   *   action_send_enabled - boolean - Whether or not sending is enabled for action logs.
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
    return FilesClient.defaultClient().siemHttpDestinations().update(id, parameters, options);
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
    FilesClient.defaultClient().siemHttpDestinations().delete(id, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
