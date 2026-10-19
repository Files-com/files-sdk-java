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

  // One mapper for every SiemHttpDestination, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code SiemHttpDestination} with no attributes set.
   */
  public SiemHttpDestination() {
    this(null, null);
  }

  /**
   * Creates a {@code SiemHttpDestination} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public SiemHttpDestination(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code SiemHttpDestination} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
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

  /**
   * Gets {@link #id}.
   *
   * @return the value of {@link #id}
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets {@link #id}.
   *
   * @param id the new value
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Name for this Destination
   */
  @JsonProperty("name")
  public String name;

  /**
   * Gets {@link #name}.
   *
   * @return the value of {@link #name}
   */
  public String getName() {
    return name;
  }

  /**
   * Sets {@link #name}.
   *
   * @param name the new value
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Destination Type
   */
  @JsonProperty("destination_type")
  public String destinationType;

  /**
   * Gets {@link #destinationType}.
   *
   * @return the value of {@link #destinationType}
   */
  public String getDestinationType() {
    return destinationType;
  }

  /**
   * Sets {@link #destinationType}.
   *
   * @param destinationType the new value
   */
  public void setDestinationType(String destinationType) {
    this.destinationType = destinationType;
  }

  /**
   * Destination Url
   */
  @JsonProperty("destination_url")
  public String destinationUrl;

  /**
   * Gets {@link #destinationUrl}.
   *
   * @return the value of {@link #destinationUrl}
   */
  public String getDestinationUrl() {
    return destinationUrl;
  }

  /**
   * Sets {@link #destinationUrl}.
   *
   * @param destinationUrl the new value
   */
  public void setDestinationUrl(String destinationUrl) {
    this.destinationUrl = destinationUrl;
  }

  /**
   * Applicable only for destination type: file. Destination folder path on Files.com.
   */
  @JsonProperty("file_destination_path")
  public String fileDestinationPath;

  /**
   * Gets {@link #fileDestinationPath}.
   *
   * @return the value of {@link #fileDestinationPath}
   */
  public String getFileDestinationPath() {
    return fileDestinationPath;
  }

  /**
   * Sets {@link #fileDestinationPath}.
   *
   * @param fileDestinationPath the new value
   */
  public void setFileDestinationPath(String fileDestinationPath) {
    this.fileDestinationPath = fileDestinationPath;
  }

  /**
   * Applicable only for destination type: file. Generated file format.
   */
  @JsonProperty("file_format")
  public String fileFormat;

  /**
   * Gets {@link #fileFormat}.
   *
   * @return the value of {@link #fileFormat}
   */
  public String getFileFormat() {
    return fileFormat;
  }

  /**
   * Sets {@link #fileFormat}.
   *
   * @param fileFormat the new value
   */
  public void setFileFormat(String fileFormat) {
    this.fileFormat = fileFormat;
  }

  /**
   * Applicable only for destination type: file. Interval, in minutes, between file deliveries.
   */
  @JsonProperty("file_interval_minutes")
  public Long fileIntervalMinutes;

  /**
   * Gets {@link #fileIntervalMinutes}.
   *
   * @return the value of {@link #fileIntervalMinutes}
   */
  public Long getFileIntervalMinutes() {
    return fileIntervalMinutes;
  }

  /**
   * Sets {@link #fileIntervalMinutes}.
   *
   * @param fileIntervalMinutes the new value
   */
  public void setFileIntervalMinutes(Long fileIntervalMinutes) {
    this.fileIntervalMinutes = fileIntervalMinutes;
  }

  /**
   * Additional HTTP Headers included in calls to the destination URL
   */
  @JsonProperty("additional_headers")
  public Object additionalHeaders;

  /**
   * Gets {@link #additionalHeaders}.
   *
   * @return the value of {@link #additionalHeaders}
   */
  public Object getAdditionalHeaders() {
    return additionalHeaders;
  }

  /**
   * Sets {@link #additionalHeaders}.
   *
   * @param additionalHeaders the new value
   */
  public void setAdditionalHeaders(Object additionalHeaders) {
    this.additionalHeaders = additionalHeaders;
  }

  /**
   * Whether this SIEM HTTP Destination is currently being sent to or not
   */
  @JsonProperty("sending_active")
  public Boolean sendingActive;

  /**
   * Gets {@link #sendingActive}.
   *
   * @return the value of {@link #sendingActive}
   */
  public Boolean getSendingActive() {
    return sendingActive;
  }

  /**
   * Sets {@link #sendingActive}.
   *
   * @param sendingActive the new value
   */
  public void setSendingActive(Boolean sendingActive) {
    this.sendingActive = sendingActive;
  }

  /**
   * Applicable only for destination type: generic. Indicates the type of HTTP body. Can be json_newline or json_array. json_newline is multiple log entries as JSON separated by newlines. json_array is a single JSON array containing multiple log entries as JSON.
   */
  @JsonProperty("generic_payload_type")
  public String genericPayloadType;

  /**
   * Gets {@link #genericPayloadType}.
   *
   * @return the value of {@link #genericPayloadType}
   */
  public String getGenericPayloadType() {
    return genericPayloadType;
  }

  /**
   * Sets {@link #genericPayloadType}.
   *
   * @param genericPayloadType the new value
   */
  public void setGenericPayloadType(String genericPayloadType) {
    this.genericPayloadType = genericPayloadType;
  }

  /**
   * Applicable only for destination types: splunk, splunk_compatible. Authentication token for the destination.
   */
  @JsonProperty("splunk_token_masked")
  public String splunkTokenMasked;

  /**
   * Gets {@link #splunkTokenMasked}.
   *
   * @return the value of {@link #splunkTokenMasked}
   */
  public String getSplunkTokenMasked() {
    return splunkTokenMasked;
  }

  /**
   * Sets {@link #splunkTokenMasked}.
   *
   * @param splunkTokenMasked the new value
   */
  public void setSplunkTokenMasked(String splunkTokenMasked) {
    this.splunkTokenMasked = splunkTokenMasked;
  }

  /**
   * Applicable only for destination type: crowdstrike. Authentication token provided by Crowdstrike.
   */
  @JsonProperty("crowdstrike_token_masked")
  public String crowdstrikeTokenMasked;

  /**
   * Gets {@link #crowdstrikeTokenMasked}.
   *
   * @return the value of {@link #crowdstrikeTokenMasked}
   */
  public String getCrowdstrikeTokenMasked() {
    return crowdstrikeTokenMasked;
  }

  /**
   * Sets {@link #crowdstrikeTokenMasked}.
   *
   * @param crowdstrikeTokenMasked the new value
   */
  public void setCrowdstrikeTokenMasked(String crowdstrikeTokenMasked) {
    this.crowdstrikeTokenMasked = crowdstrikeTokenMasked;
  }

  /**
   * Applicable only for destination types: azure, azure_legacy. Immutable ID of the Data Collection Rule.
   */
  @JsonProperty("azure_dcr_immutable_id")
  public String azureDcrImmutableId;

  /**
   * Gets {@link #azureDcrImmutableId}.
   *
   * @return the value of {@link #azureDcrImmutableId}
   */
  public String getAzureDcrImmutableId() {
    return azureDcrImmutableId;
  }

  /**
   * Sets {@link #azureDcrImmutableId}.
   *
   * @param azureDcrImmutableId the new value
   */
  public void setAzureDcrImmutableId(String azureDcrImmutableId) {
    this.azureDcrImmutableId = azureDcrImmutableId;
  }

  /**
   * Applicable only for destination type: azure. Name of the stream in the DCR that represents the destination table.
   */
  @JsonProperty("azure_stream_name")
  public String azureStreamName;

  /**
   * Gets {@link #azureStreamName}.
   *
   * @return the value of {@link #azureStreamName}
   */
  public String getAzureStreamName() {
    return azureStreamName;
  }

  /**
   * Sets {@link #azureStreamName}.
   *
   * @param azureStreamName the new value
   */
  public void setAzureStreamName(String azureStreamName) {
    this.azureStreamName = azureStreamName;
  }

  /**
   * Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Tenant ID.
   */
  @JsonProperty("azure_oauth_client_credentials_tenant_id")
  public String azureOauthClientCredentialsTenantId;

  /**
   * Gets {@link #azureOauthClientCredentialsTenantId}.
   *
   * @return the value of {@link #azureOauthClientCredentialsTenantId}
   */
  public String getAzureOauthClientCredentialsTenantId() {
    return azureOauthClientCredentialsTenantId;
  }

  /**
   * Sets {@link #azureOauthClientCredentialsTenantId}.
   *
   * @param azureOauthClientCredentialsTenantId the new value
   */
  public void setAzureOauthClientCredentialsTenantId(String azureOauthClientCredentialsTenantId) {
    this.azureOauthClientCredentialsTenantId = azureOauthClientCredentialsTenantId;
  }

  /**
   * Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Client ID.
   */
  @JsonProperty("azure_oauth_client_credentials_client_id")
  public String azureOauthClientCredentialsClientId;

  /**
   * Gets {@link #azureOauthClientCredentialsClientId}.
   *
   * @return the value of {@link #azureOauthClientCredentialsClientId}
   */
  public String getAzureOauthClientCredentialsClientId() {
    return azureOauthClientCredentialsClientId;
  }

  /**
   * Sets {@link #azureOauthClientCredentialsClientId}.
   *
   * @param azureOauthClientCredentialsClientId the new value
   */
  public void setAzureOauthClientCredentialsClientId(String azureOauthClientCredentialsClientId) {
    this.azureOauthClientCredentialsClientId = azureOauthClientCredentialsClientId;
  }

  /**
   * Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Client Secret.
   */
  @JsonProperty("azure_oauth_client_credentials_client_secret_masked")
  public String azureOauthClientCredentialsClientSecretMasked;

  /**
   * Gets {@link #azureOauthClientCredentialsClientSecretMasked}.
   *
   * @return the value of {@link #azureOauthClientCredentialsClientSecretMasked}
   */
  public String getAzureOauthClientCredentialsClientSecretMasked() {
    return azureOauthClientCredentialsClientSecretMasked;
  }

  /**
   * Sets {@link #azureOauthClientCredentialsClientSecretMasked}.
   *
   * @param azureOauthClientCredentialsClientSecretMasked the new value
   */
  public void setAzureOauthClientCredentialsClientSecretMasked(String azureOauthClientCredentialsClientSecretMasked) {
    this.azureOauthClientCredentialsClientSecretMasked = azureOauthClientCredentialsClientSecretMasked;
  }

  /**
   * Applicable only for destination type: qradar. Basic auth username provided by QRadar.
   */
  @JsonProperty("qradar_username")
  public String qradarUsername;

  /**
   * Gets {@link #qradarUsername}.
   *
   * @return the value of {@link #qradarUsername}
   */
  public String getQradarUsername() {
    return qradarUsername;
  }

  /**
   * Sets {@link #qradarUsername}.
   *
   * @param qradarUsername the new value
   */
  public void setQradarUsername(String qradarUsername) {
    this.qradarUsername = qradarUsername;
  }

  /**
   * Applicable only for destination type: qradar. Basic auth password provided by QRadar.
   */
  @JsonProperty("qradar_password_masked")
  public String qradarPasswordMasked;

  /**
   * Gets {@link #qradarPasswordMasked}.
   *
   * @return the value of {@link #qradarPasswordMasked}
   */
  public String getQradarPasswordMasked() {
    return qradarPasswordMasked;
  }

  /**
   * Sets {@link #qradarPasswordMasked}.
   *
   * @param qradarPasswordMasked the new value
   */
  public void setQradarPasswordMasked(String qradarPasswordMasked) {
    this.qradarPasswordMasked = qradarPasswordMasked;
  }

  /**
   * Applicable only for destination type: solar_winds. Authentication token provided by Solar Winds.
   */
  @JsonProperty("solar_winds_token_masked")
  public String solarWindsTokenMasked;

  /**
   * Gets {@link #solarWindsTokenMasked}.
   *
   * @return the value of {@link #solarWindsTokenMasked}
   */
  public String getSolarWindsTokenMasked() {
    return solarWindsTokenMasked;
  }

  /**
   * Sets {@link #solarWindsTokenMasked}.
   *
   * @param solarWindsTokenMasked the new value
   */
  public void setSolarWindsTokenMasked(String solarWindsTokenMasked) {
    this.solarWindsTokenMasked = solarWindsTokenMasked;
  }

  /**
   * Applicable only for destination type: new_relic. API key provided by New Relic.
   */
  @JsonProperty("new_relic_api_key_masked")
  public String newRelicApiKeyMasked;

  /**
   * Gets {@link #newRelicApiKeyMasked}.
   *
   * @return the value of {@link #newRelicApiKeyMasked}
   */
  public String getNewRelicApiKeyMasked() {
    return newRelicApiKeyMasked;
  }

  /**
   * Sets {@link #newRelicApiKeyMasked}.
   *
   * @param newRelicApiKeyMasked the new value
   */
  public void setNewRelicApiKeyMasked(String newRelicApiKeyMasked) {
    this.newRelicApiKeyMasked = newRelicApiKeyMasked;
  }

  /**
   * Applicable only for destination type: datadog. API key provided by Datadog.
   */
  @JsonProperty("datadog_api_key_masked")
  public String datadogApiKeyMasked;

  /**
   * Gets {@link #datadogApiKeyMasked}.
   *
   * @return the value of {@link #datadogApiKeyMasked}
   */
  public String getDatadogApiKeyMasked() {
    return datadogApiKeyMasked;
  }

  /**
   * Sets {@link #datadogApiKeyMasked}.
   *
   * @param datadogApiKeyMasked the new value
   */
  public void setDatadogApiKeyMasked(String datadogApiKeyMasked) {
    this.datadogApiKeyMasked = datadogApiKeyMasked;
  }

  /**
   * Whether or not sending is enabled for action logs.
   */
  @JsonProperty("action_send_enabled")
  public Boolean actionSendEnabled;

  /**
   * Gets {@link #actionSendEnabled}.
   *
   * @return the value of {@link #actionSendEnabled}
   */
  public Boolean getActionSendEnabled() {
    return actionSendEnabled;
  }

  /**
   * Sets {@link #actionSendEnabled}.
   *
   * @param actionSendEnabled the new value
   */
  public void setActionSendEnabled(Boolean actionSendEnabled) {
    this.actionSendEnabled = actionSendEnabled;
  }

  /**
   * Number of log entries sent for the lifetime of this destination.
   */
  @JsonProperty("action_entries_sent")
  public Long actionEntriesSent;

  /**
   * Gets {@link #actionEntriesSent}.
   *
   * @return the value of {@link #actionEntriesSent}
   */
  public Long getActionEntriesSent() {
    return actionEntriesSent;
  }

  /**
   * Sets {@link #actionEntriesSent}.
   *
   * @param actionEntriesSent the new value
   */
  public void setActionEntriesSent(Long actionEntriesSent) {
    this.actionEntriesSent = actionEntriesSent;
  }

  /**
   * Whether or not sending is enabled for sftp_action logs.
   */
  @JsonProperty("sftp_action_send_enabled")
  public Boolean sftpActionSendEnabled;

  /**
   * Gets {@link #sftpActionSendEnabled}.
   *
   * @return the value of {@link #sftpActionSendEnabled}
   */
  public Boolean getSftpActionSendEnabled() {
    return sftpActionSendEnabled;
  }

  /**
   * Sets {@link #sftpActionSendEnabled}.
   *
   * @param sftpActionSendEnabled the new value
   */
  public void setSftpActionSendEnabled(Boolean sftpActionSendEnabled) {
    this.sftpActionSendEnabled = sftpActionSendEnabled;
  }

  /**
   * Number of log entries sent for the lifetime of this destination.
   */
  @JsonProperty("sftp_action_entries_sent")
  public Long sftpActionEntriesSent;

  /**
   * Gets {@link #sftpActionEntriesSent}.
   *
   * @return the value of {@link #sftpActionEntriesSent}
   */
  public Long getSftpActionEntriesSent() {
    return sftpActionEntriesSent;
  }

  /**
   * Sets {@link #sftpActionEntriesSent}.
   *
   * @param sftpActionEntriesSent the new value
   */
  public void setSftpActionEntriesSent(Long sftpActionEntriesSent) {
    this.sftpActionEntriesSent = sftpActionEntriesSent;
  }

  /**
   * Whether or not sending is enabled for ftp_action logs.
   */
  @JsonProperty("ftp_action_send_enabled")
  public Boolean ftpActionSendEnabled;

  /**
   * Gets {@link #ftpActionSendEnabled}.
   *
   * @return the value of {@link #ftpActionSendEnabled}
   */
  public Boolean getFtpActionSendEnabled() {
    return ftpActionSendEnabled;
  }

  /**
   * Sets {@link #ftpActionSendEnabled}.
   *
   * @param ftpActionSendEnabled the new value
   */
  public void setFtpActionSendEnabled(Boolean ftpActionSendEnabled) {
    this.ftpActionSendEnabled = ftpActionSendEnabled;
  }

  /**
   * Number of log entries sent for the lifetime of this destination.
   */
  @JsonProperty("ftp_action_entries_sent")
  public Long ftpActionEntriesSent;

  /**
   * Gets {@link #ftpActionEntriesSent}.
   *
   * @return the value of {@link #ftpActionEntriesSent}
   */
  public Long getFtpActionEntriesSent() {
    return ftpActionEntriesSent;
  }

  /**
   * Sets {@link #ftpActionEntriesSent}.
   *
   * @param ftpActionEntriesSent the new value
   */
  public void setFtpActionEntriesSent(Long ftpActionEntriesSent) {
    this.ftpActionEntriesSent = ftpActionEntriesSent;
  }

  /**
   * Whether or not sending is enabled for web_dav_action logs.
   */
  @JsonProperty("web_dav_action_send_enabled")
  public Boolean webDavActionSendEnabled;

  /**
   * Gets {@link #webDavActionSendEnabled}.
   *
   * @return the value of {@link #webDavActionSendEnabled}
   */
  public Boolean getWebDavActionSendEnabled() {
    return webDavActionSendEnabled;
  }

  /**
   * Sets {@link #webDavActionSendEnabled}.
   *
   * @param webDavActionSendEnabled the new value
   */
  public void setWebDavActionSendEnabled(Boolean webDavActionSendEnabled) {
    this.webDavActionSendEnabled = webDavActionSendEnabled;
  }

  /**
   * Number of log entries sent for the lifetime of this destination.
   */
  @JsonProperty("web_dav_action_entries_sent")
  public Long webDavActionEntriesSent;

  /**
   * Gets {@link #webDavActionEntriesSent}.
   *
   * @return the value of {@link #webDavActionEntriesSent}
   */
  public Long getWebDavActionEntriesSent() {
    return webDavActionEntriesSent;
  }

  /**
   * Sets {@link #webDavActionEntriesSent}.
   *
   * @param webDavActionEntriesSent the new value
   */
  public void setWebDavActionEntriesSent(Long webDavActionEntriesSent) {
    this.webDavActionEntriesSent = webDavActionEntriesSent;
  }

  /**
   * Whether or not sending is enabled for sync logs.
   */
  @JsonProperty("sync_send_enabled")
  public Boolean syncSendEnabled;

  /**
   * Gets {@link #syncSendEnabled}.
   *
   * @return the value of {@link #syncSendEnabled}
   */
  public Boolean getSyncSendEnabled() {
    return syncSendEnabled;
  }

  /**
   * Sets {@link #syncSendEnabled}.
   *
   * @param syncSendEnabled the new value
   */
  public void setSyncSendEnabled(Boolean syncSendEnabled) {
    this.syncSendEnabled = syncSendEnabled;
  }

  /**
   * Number of log entries sent for the lifetime of this destination.
   */
  @JsonProperty("sync_entries_sent")
  public Long syncEntriesSent;

  /**
   * Gets {@link #syncEntriesSent}.
   *
   * @return the value of {@link #syncEntriesSent}
   */
  public Long getSyncEntriesSent() {
    return syncEntriesSent;
  }

  /**
   * Sets {@link #syncEntriesSent}.
   *
   * @param syncEntriesSent the new value
   */
  public void setSyncEntriesSent(Long syncEntriesSent) {
    this.syncEntriesSent = syncEntriesSent;
  }

  /**
   * Whether or not sending is enabled for outbound_connection logs.
   */
  @JsonProperty("outbound_connection_send_enabled")
  public Boolean outboundConnectionSendEnabled;

  /**
   * Gets {@link #outboundConnectionSendEnabled}.
   *
   * @return the value of {@link #outboundConnectionSendEnabled}
   */
  public Boolean getOutboundConnectionSendEnabled() {
    return outboundConnectionSendEnabled;
  }

  /**
   * Sets {@link #outboundConnectionSendEnabled}.
   *
   * @param outboundConnectionSendEnabled the new value
   */
  public void setOutboundConnectionSendEnabled(Boolean outboundConnectionSendEnabled) {
    this.outboundConnectionSendEnabled = outboundConnectionSendEnabled;
  }

  /**
   * Number of log entries sent for the lifetime of this destination.
   */
  @JsonProperty("outbound_connection_entries_sent")
  public Long outboundConnectionEntriesSent;

  /**
   * Gets {@link #outboundConnectionEntriesSent}.
   *
   * @return the value of {@link #outboundConnectionEntriesSent}
   */
  public Long getOutboundConnectionEntriesSent() {
    return outboundConnectionEntriesSent;
  }

  /**
   * Sets {@link #outboundConnectionEntriesSent}.
   *
   * @param outboundConnectionEntriesSent the new value
   */
  public void setOutboundConnectionEntriesSent(Long outboundConnectionEntriesSent) {
    this.outboundConnectionEntriesSent = outboundConnectionEntriesSent;
  }

  /**
   * Whether or not sending is enabled for automation logs.
   */
  @JsonProperty("automation_send_enabled")
  public Boolean automationSendEnabled;

  /**
   * Gets {@link #automationSendEnabled}.
   *
   * @return the value of {@link #automationSendEnabled}
   */
  public Boolean getAutomationSendEnabled() {
    return automationSendEnabled;
  }

  /**
   * Sets {@link #automationSendEnabled}.
   *
   * @param automationSendEnabled the new value
   */
  public void setAutomationSendEnabled(Boolean automationSendEnabled) {
    this.automationSendEnabled = automationSendEnabled;
  }

  /**
   * Number of log entries sent for the lifetime of this destination.
   */
  @JsonProperty("automation_entries_sent")
  public Long automationEntriesSent;

  /**
   * Gets {@link #automationEntriesSent}.
   *
   * @return the value of {@link #automationEntriesSent}
   */
  public Long getAutomationEntriesSent() {
    return automationEntriesSent;
  }

  /**
   * Sets {@link #automationEntriesSent}.
   *
   * @param automationEntriesSent the new value
   */
  public void setAutomationEntriesSent(Long automationEntriesSent) {
    this.automationEntriesSent = automationEntriesSent;
  }

  /**
   * Whether or not sending is enabled for api_request logs.
   */
  @JsonProperty("api_request_send_enabled")
  public Boolean apiRequestSendEnabled;

  /**
   * Gets {@link #apiRequestSendEnabled}.
   *
   * @return the value of {@link #apiRequestSendEnabled}
   */
  public Boolean getApiRequestSendEnabled() {
    return apiRequestSendEnabled;
  }

  /**
   * Sets {@link #apiRequestSendEnabled}.
   *
   * @param apiRequestSendEnabled the new value
   */
  public void setApiRequestSendEnabled(Boolean apiRequestSendEnabled) {
    this.apiRequestSendEnabled = apiRequestSendEnabled;
  }

  /**
   * Number of log entries sent for the lifetime of this destination.
   */
  @JsonProperty("api_request_entries_sent")
  public Long apiRequestEntriesSent;

  /**
   * Gets {@link #apiRequestEntriesSent}.
   *
   * @return the value of {@link #apiRequestEntriesSent}
   */
  public Long getApiRequestEntriesSent() {
    return apiRequestEntriesSent;
  }

  /**
   * Sets {@link #apiRequestEntriesSent}.
   *
   * @param apiRequestEntriesSent the new value
   */
  public void setApiRequestEntriesSent(Long apiRequestEntriesSent) {
    this.apiRequestEntriesSent = apiRequestEntriesSent;
  }

  /**
   * Whether or not sending is enabled for public_hosting_request logs.
   */
  @JsonProperty("public_hosting_request_send_enabled")
  public Boolean publicHostingRequestSendEnabled;

  /**
   * Gets {@link #publicHostingRequestSendEnabled}.
   *
   * @return the value of {@link #publicHostingRequestSendEnabled}
   */
  public Boolean getPublicHostingRequestSendEnabled() {
    return publicHostingRequestSendEnabled;
  }

  /**
   * Sets {@link #publicHostingRequestSendEnabled}.
   *
   * @param publicHostingRequestSendEnabled the new value
   */
  public void setPublicHostingRequestSendEnabled(Boolean publicHostingRequestSendEnabled) {
    this.publicHostingRequestSendEnabled = publicHostingRequestSendEnabled;
  }

  /**
   * Number of log entries sent for the lifetime of this destination.
   */
  @JsonProperty("public_hosting_request_entries_sent")
  public Long publicHostingRequestEntriesSent;

  /**
   * Gets {@link #publicHostingRequestEntriesSent}.
   *
   * @return the value of {@link #publicHostingRequestEntriesSent}
   */
  public Long getPublicHostingRequestEntriesSent() {
    return publicHostingRequestEntriesSent;
  }

  /**
   * Sets {@link #publicHostingRequestEntriesSent}.
   *
   * @param publicHostingRequestEntriesSent the new value
   */
  public void setPublicHostingRequestEntriesSent(Long publicHostingRequestEntriesSent) {
    this.publicHostingRequestEntriesSent = publicHostingRequestEntriesSent;
  }

  /**
   * Whether or not sending is enabled for email logs.
   */
  @JsonProperty("email_send_enabled")
  public Boolean emailSendEnabled;

  /**
   * Gets {@link #emailSendEnabled}.
   *
   * @return the value of {@link #emailSendEnabled}
   */
  public Boolean getEmailSendEnabled() {
    return emailSendEnabled;
  }

  /**
   * Sets {@link #emailSendEnabled}.
   *
   * @param emailSendEnabled the new value
   */
  public void setEmailSendEnabled(Boolean emailSendEnabled) {
    this.emailSendEnabled = emailSendEnabled;
  }

  /**
   * Number of log entries sent for the lifetime of this destination.
   */
  @JsonProperty("email_entries_sent")
  public Long emailEntriesSent;

  /**
   * Gets {@link #emailEntriesSent}.
   *
   * @return the value of {@link #emailEntriesSent}
   */
  public Long getEmailEntriesSent() {
    return emailEntriesSent;
  }

  /**
   * Sets {@link #emailEntriesSent}.
   *
   * @param emailEntriesSent the new value
   */
  public void setEmailEntriesSent(Long emailEntriesSent) {
    this.emailEntriesSent = emailEntriesSent;
  }

  /**
   * Whether or not sending is enabled for exavault_api_request logs.
   */
  @JsonProperty("exavault_api_request_send_enabled")
  public Boolean exavaultApiRequestSendEnabled;

  /**
   * Gets {@link #exavaultApiRequestSendEnabled}.
   *
   * @return the value of {@link #exavaultApiRequestSendEnabled}
   */
  public Boolean getExavaultApiRequestSendEnabled() {
    return exavaultApiRequestSendEnabled;
  }

  /**
   * Sets {@link #exavaultApiRequestSendEnabled}.
   *
   * @param exavaultApiRequestSendEnabled the new value
   */
  public void setExavaultApiRequestSendEnabled(Boolean exavaultApiRequestSendEnabled) {
    this.exavaultApiRequestSendEnabled = exavaultApiRequestSendEnabled;
  }

  /**
   * Number of log entries sent for the lifetime of this destination.
   */
  @JsonProperty("exavault_api_request_entries_sent")
  public Long exavaultApiRequestEntriesSent;

  /**
   * Gets {@link #exavaultApiRequestEntriesSent}.
   *
   * @return the value of {@link #exavaultApiRequestEntriesSent}
   */
  public Long getExavaultApiRequestEntriesSent() {
    return exavaultApiRequestEntriesSent;
  }

  /**
   * Sets {@link #exavaultApiRequestEntriesSent}.
   *
   * @param exavaultApiRequestEntriesSent the new value
   */
  public void setExavaultApiRequestEntriesSent(Long exavaultApiRequestEntriesSent) {
    this.exavaultApiRequestEntriesSent = exavaultApiRequestEntriesSent;
  }

  /**
   * Whether or not sending is enabled for settings_change logs.
   */
  @JsonProperty("settings_change_send_enabled")
  public Boolean settingsChangeSendEnabled;

  /**
   * Gets {@link #settingsChangeSendEnabled}.
   *
   * @return the value of {@link #settingsChangeSendEnabled}
   */
  public Boolean getSettingsChangeSendEnabled() {
    return settingsChangeSendEnabled;
  }

  /**
   * Sets {@link #settingsChangeSendEnabled}.
   *
   * @param settingsChangeSendEnabled the new value
   */
  public void setSettingsChangeSendEnabled(Boolean settingsChangeSendEnabled) {
    this.settingsChangeSendEnabled = settingsChangeSendEnabled;
  }

  /**
   * Number of log entries sent for the lifetime of this destination.
   */
  @JsonProperty("settings_change_entries_sent")
  public Long settingsChangeEntriesSent;

  /**
   * Gets {@link #settingsChangeEntriesSent}.
   *
   * @return the value of {@link #settingsChangeEntriesSent}
   */
  public Long getSettingsChangeEntriesSent() {
    return settingsChangeEntriesSent;
  }

  /**
   * Sets {@link #settingsChangeEntriesSent}.
   *
   * @param settingsChangeEntriesSent the new value
   */
  public void setSettingsChangeEntriesSent(Long settingsChangeEntriesSent) {
    this.settingsChangeEntriesSent = settingsChangeEntriesSent;
  }

  /**
   * Type of URL that was last called. Can be `destination_url` or `azure_oauth_client_credentials_url`
   */
  @JsonProperty("last_http_call_target_type")
  public String lastHttpCallTargetType;

  /**
   * Gets {@link #lastHttpCallTargetType}.
   *
   * @return the value of {@link #lastHttpCallTargetType}
   */
  public String getLastHttpCallTargetType() {
    return lastHttpCallTargetType;
  }

  /**
   * Sets {@link #lastHttpCallTargetType}.
   *
   * @param lastHttpCallTargetType the new value
   */
  public void setLastHttpCallTargetType(String lastHttpCallTargetType) {
    this.lastHttpCallTargetType = lastHttpCallTargetType;
  }

  /**
   * Was the last HTTP call made successful?
   */
  @JsonProperty("last_http_call_success")
  public Boolean lastHttpCallSuccess;

  /**
   * Gets {@link #lastHttpCallSuccess}.
   *
   * @return the value of {@link #lastHttpCallSuccess}
   */
  public Boolean getLastHttpCallSuccess() {
    return lastHttpCallSuccess;
  }

  /**
   * Sets {@link #lastHttpCallSuccess}.
   *
   * @param lastHttpCallSuccess the new value
   */
  public void setLastHttpCallSuccess(Boolean lastHttpCallSuccess) {
    this.lastHttpCallSuccess = lastHttpCallSuccess;
  }

  /**
   * Last HTTP Call Response Code
   */
  @JsonProperty("last_http_call_response_code")
  public Long lastHttpCallResponseCode;

  /**
   * Gets {@link #lastHttpCallResponseCode}.
   *
   * @return the value of {@link #lastHttpCallResponseCode}
   */
  public Long getLastHttpCallResponseCode() {
    return lastHttpCallResponseCode;
  }

  /**
   * Sets {@link #lastHttpCallResponseCode}.
   *
   * @param lastHttpCallResponseCode the new value
   */
  public void setLastHttpCallResponseCode(Long lastHttpCallResponseCode) {
    this.lastHttpCallResponseCode = lastHttpCallResponseCode;
  }

  /**
   * Last HTTP Call Response Body. Large responses are truncated.
   */
  @JsonProperty("last_http_call_response_body")
  public String lastHttpCallResponseBody;

  /**
   * Gets {@link #lastHttpCallResponseBody}.
   *
   * @return the value of {@link #lastHttpCallResponseBody}
   */
  public String getLastHttpCallResponseBody() {
    return lastHttpCallResponseBody;
  }

  /**
   * Sets {@link #lastHttpCallResponseBody}.
   *
   * @param lastHttpCallResponseBody the new value
   */
  public void setLastHttpCallResponseBody(String lastHttpCallResponseBody) {
    this.lastHttpCallResponseBody = lastHttpCallResponseBody;
  }

  /**
   * Last HTTP Call Error Message if applicable
   */
  @JsonProperty("last_http_call_error_message")
  public String lastHttpCallErrorMessage;

  /**
   * Gets {@link #lastHttpCallErrorMessage}.
   *
   * @return the value of {@link #lastHttpCallErrorMessage}
   */
  public String getLastHttpCallErrorMessage() {
    return lastHttpCallErrorMessage;
  }

  /**
   * Sets {@link #lastHttpCallErrorMessage}.
   *
   * @param lastHttpCallErrorMessage the new value
   */
  public void setLastHttpCallErrorMessage(String lastHttpCallErrorMessage) {
    this.lastHttpCallErrorMessage = lastHttpCallErrorMessage;
  }

  /**
   * Time of Last HTTP Call
   */
  @JsonProperty("last_http_call_time")
  public String lastHttpCallTime;

  /**
   * Gets {@link #lastHttpCallTime}.
   *
   * @return the value of {@link #lastHttpCallTime}
   */
  public String getLastHttpCallTime() {
    return lastHttpCallTime;
  }

  /**
   * Sets {@link #lastHttpCallTime}.
   *
   * @param lastHttpCallTime the new value
   */
  public void setLastHttpCallTime(String lastHttpCallTime) {
    this.lastHttpCallTime = lastHttpCallTime;
  }

  /**
   * Duration of the last HTTP Call in milliseconds
   */
  @JsonProperty("last_http_call_duration_ms")
  public Long lastHttpCallDurationMs;

  /**
   * Gets {@link #lastHttpCallDurationMs}.
   *
   * @return the value of {@link #lastHttpCallDurationMs}
   */
  public Long getLastHttpCallDurationMs() {
    return lastHttpCallDurationMs;
  }

  /**
   * Sets {@link #lastHttpCallDurationMs}.
   *
   * @param lastHttpCallDurationMs the new value
   */
  public void setLastHttpCallDurationMs(Long lastHttpCallDurationMs) {
    this.lastHttpCallDurationMs = lastHttpCallDurationMs;
  }

  /**
   * Time of Most Recent Successful HTTP Call
   */
  @JsonProperty("most_recent_http_call_success_time")
  public String mostRecentHttpCallSuccessTime;

  /**
   * Gets {@link #mostRecentHttpCallSuccessTime}.
   *
   * @return the value of {@link #mostRecentHttpCallSuccessTime}
   */
  public String getMostRecentHttpCallSuccessTime() {
    return mostRecentHttpCallSuccessTime;
  }

  /**
   * Sets {@link #mostRecentHttpCallSuccessTime}.
   *
   * @param mostRecentHttpCallSuccessTime the new value
   */
  public void setMostRecentHttpCallSuccessTime(String mostRecentHttpCallSuccessTime) {
    this.mostRecentHttpCallSuccessTime = mostRecentHttpCallSuccessTime;
  }

  /**
   * Connection Test Entry
   */
  @JsonProperty("connection_test_entry")
  public String connectionTestEntry;

  /**
   * Gets {@link #connectionTestEntry}.
   *
   * @return the value of {@link #connectionTestEntry}
   */
  public String getConnectionTestEntry() {
    return connectionTestEntry;
  }

  /**
   * Sets {@link #connectionTestEntry}.
   *
   * @param connectionTestEntry the new value
   */
  public void setConnectionTestEntry(String connectionTestEntry) {
    this.connectionTestEntry = connectionTestEntry;
  }

  /**
   * Applicable only for destination types: splunk, splunk_compatible. Authentication token for the destination.
   */
  @JsonProperty("splunk_token")
  public String splunkToken;

  /**
   * Gets {@link #splunkToken}.
   *
   * @return the value of {@link #splunkToken}
   */
  public String getSplunkToken() {
    return splunkToken;
  }

  /**
   * Sets {@link #splunkToken}.
   *
   * @param splunkToken the new value
   */
  public void setSplunkToken(String splunkToken) {
    this.splunkToken = splunkToken;
  }

  /**
   * Applicable only for destination type: crowdstrike. Authentication token provided by Crowdstrike.
   */
  @JsonProperty("crowdstrike_token")
  public String crowdstrikeToken;

  /**
   * Gets {@link #crowdstrikeToken}.
   *
   * @return the value of {@link #crowdstrikeToken}
   */
  public String getCrowdstrikeToken() {
    return crowdstrikeToken;
  }

  /**
   * Sets {@link #crowdstrikeToken}.
   *
   * @param crowdstrikeToken the new value
   */
  public void setCrowdstrikeToken(String crowdstrikeToken) {
    this.crowdstrikeToken = crowdstrikeToken;
  }

  /**
   * Applicable only for destination type: azure. Client Credentials OAuth Client Secret.
   */
  @JsonProperty("azure_oauth_client_credentials_client_secret")
  public String azureOauthClientCredentialsClientSecret;

  /**
   * Gets {@link #azureOauthClientCredentialsClientSecret}.
   *
   * @return the value of {@link #azureOauthClientCredentialsClientSecret}
   */
  public String getAzureOauthClientCredentialsClientSecret() {
    return azureOauthClientCredentialsClientSecret;
  }

  /**
   * Sets {@link #azureOauthClientCredentialsClientSecret}.
   *
   * @param azureOauthClientCredentialsClientSecret the new value
   */
  public void setAzureOauthClientCredentialsClientSecret(String azureOauthClientCredentialsClientSecret) {
    this.azureOauthClientCredentialsClientSecret = azureOauthClientCredentialsClientSecret;
  }

  /**
   * Applicable only for destination type: qradar. Basic auth password provided by QRadar.
   */
  @JsonProperty("qradar_password")
  public String qradarPassword;

  /**
   * Gets {@link #qradarPassword}.
   *
   * @return the value of {@link #qradarPassword}
   */
  public String getQradarPassword() {
    return qradarPassword;
  }

  /**
   * Sets {@link #qradarPassword}.
   *
   * @param qradarPassword the new value
   */
  public void setQradarPassword(String qradarPassword) {
    this.qradarPassword = qradarPassword;
  }

  /**
   * Applicable only for destination type: solar_winds. Authentication token provided by Solar Winds.
   */
  @JsonProperty("solar_winds_token")
  public String solarWindsToken;

  /**
   * Gets {@link #solarWindsToken}.
   *
   * @return the value of {@link #solarWindsToken}
   */
  public String getSolarWindsToken() {
    return solarWindsToken;
  }

  /**
   * Sets {@link #solarWindsToken}.
   *
   * @param solarWindsToken the new value
   */
  public void setSolarWindsToken(String solarWindsToken) {
    this.solarWindsToken = solarWindsToken;
  }

  /**
   * Applicable only for destination type: new_relic. API key provided by New Relic.
   */
  @JsonProperty("new_relic_api_key")
  public String newRelicApiKey;

  /**
   * Gets {@link #newRelicApiKey}.
   *
   * @return the value of {@link #newRelicApiKey}
   */
  public String getNewRelicApiKey() {
    return newRelicApiKey;
  }

  /**
   * Sets {@link #newRelicApiKey}.
   *
   * @param newRelicApiKey the new value
   */
  public void setNewRelicApiKey(String newRelicApiKey) {
    this.newRelicApiKey = newRelicApiKey;
  }

  /**
   * Applicable only for destination type: datadog. API key provided by Datadog.
   */
  @JsonProperty("datadog_api_key")
  public String datadogApiKey;

  /**
   * Gets {@link #datadogApiKey}.
   *
   * @return the value of {@link #datadogApiKey}
   */
  public String getDatadogApiKey() {
    return datadogApiKey;
  }

  /**
   * Sets {@link #datadogApiKey}.
   *
   * @param datadogApiKey the new value
   */
  public void setDatadogApiKey(String datadogApiKey) {
    this.datadogApiKey = datadogApiKey;
  }

  /**
   * Updates this SiemHttpDestination. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update SIEM HTTP Destination
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Name for this Destination</li>
   * <li>{@code additional_headers} (Object): Additional HTTP Headers included in calls to the destination URL</li>
   * <li>{@code sending_active} (Boolean): Whether this SIEM HTTP Destination is currently being sent to or not</li>
   * <li>{@code generic_payload_type} (String): Applicable only for destination type: generic. Indicates the type of HTTP body. Can be json_newline or json_array. json_newline is multiple log entries as JSON separated by newlines. json_array is a single JSON array containing multiple log entries as JSON.</li>
   * <li>{@code file_destination_path} (String): Applicable only for destination type: file. Destination folder path on Files.com.</li>
   * <li>{@code file_format} (String): Applicable only for destination type: file. Generated file format.</li>
   * <li>{@code file_interval_minutes} (Long): Applicable only for destination type: file. Interval, in minutes, between file deliveries. Valid values are 5, 10, 15, 20, 30, 60, 90, 180, 240, 360.</li>
   * <li>{@code splunk_token} (String): Applicable only for destination types: splunk, splunk_compatible. Authentication token for the destination.</li>
   * <li>{@code crowdstrike_token} (String): Applicable only for destination type: crowdstrike. Authentication token provided by Crowdstrike.</li>
   * <li>{@code azure_dcr_immutable_id} (String): Applicable only for destination types: azure, azure_legacy. Immutable ID of the Data Collection Rule.</li>
   * <li>{@code azure_stream_name} (String): Applicable only for destination type: azure. Name of the stream in the DCR that represents the destination table.</li>
   * <li>{@code azure_oauth_client_credentials_tenant_id} (String): Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Tenant ID.</li>
   * <li>{@code azure_oauth_client_credentials_client_id} (String): Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Client ID.</li>
   * <li>{@code azure_oauth_client_credentials_client_secret} (String): Applicable only for destination type: azure. Client Credentials OAuth Client Secret.</li>
   * <li>{@code qradar_username} (String): Applicable only for destination type: qradar. Basic auth username provided by QRadar.</li>
   * <li>{@code qradar_password} (String): Applicable only for destination type: qradar. Basic auth password provided by QRadar.</li>
   * <li>{@code solar_winds_token} (String): Applicable only for destination type: solar_winds. Authentication token provided by Solar Winds.</li>
   * <li>{@code new_relic_api_key} (String): Applicable only for destination type: new_relic. API key provided by New Relic.</li>
   * <li>{@code datadog_api_key} (String): Applicable only for destination type: datadog. API key provided by Datadog.</li>
   * <li>{@code action_send_enabled} (Boolean): Whether or not sending is enabled for action logs.</li>
   * <li>{@code sftp_action_send_enabled} (Boolean): Whether or not sending is enabled for sftp_action logs.</li>
   * <li>{@code ftp_action_send_enabled} (Boolean): Whether or not sending is enabled for ftp_action logs.</li>
   * <li>{@code web_dav_action_send_enabled} (Boolean): Whether or not sending is enabled for web_dav_action logs.</li>
   * <li>{@code sync_send_enabled} (Boolean): Whether or not sending is enabled for sync logs.</li>
   * <li>{@code outbound_connection_send_enabled} (Boolean): Whether or not sending is enabled for outbound_connection logs.</li>
   * <li>{@code automation_send_enabled} (Boolean): Whether or not sending is enabled for automation logs.</li>
   * <li>{@code api_request_send_enabled} (Boolean): Whether or not sending is enabled for api_request logs.</li>
   * <li>{@code public_hosting_request_send_enabled} (Boolean): Whether or not sending is enabled for public_hosting_request logs.</li>
   * <li>{@code email_send_enabled} (Boolean): Whether or not sending is enabled for email logs.</li>
   * <li>{@code exavault_api_request_send_enabled} (Boolean): Whether or not sending is enabled for exavault_api_request logs.</li>
   * <li>{@code settings_change_send_enabled} (Boolean): Whether or not sending is enabled for settings_change logs.</li>
   * <li>{@code destination_type} (String): Destination Type</li>
   * <li>{@code destination_url} (String): Destination Url</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code SiemHttpDestination}
   */
  public SiemHttpDestination update(HashMap<String, Object> parameters) throws IOException {
    SiemHttpDestination updated = filesClient().siemHttpDestinations().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete SIEM HTTP Destination
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().siemHttpDestinations().delete(this.id, parameters, this.options);
  }

  /**
   * Delete SIEM HTTP Destination
   *
   * @see #delete(HashMap)
   */
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
   * List SIEM HTTP Destinations
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<SiemHttpDestination> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List SIEM HTTP Destinations
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<SiemHttpDestination> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List SIEM HTTP Destinations
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code SiemHttpDestination} records, in a {@code ListIterator}
   */
  public static ListIterator<SiemHttpDestination> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().siemHttpDestinations().list(parameters, options);
  }

  /**
   * List SIEM HTTP Destinations
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<SiemHttpDestination> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List SIEM HTTP Destinations
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<SiemHttpDestination> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show SIEM HTTP Destination
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SiemHttpDestination find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show SIEM HTTP Destination
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SiemHttpDestination find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show SIEM HTTP Destination
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SiemHttpDestination find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show SIEM HTTP Destination
   *
   * @param id Siem Http Destination ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code SiemHttpDestination}
   */
  public static SiemHttpDestination find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().siemHttpDestinations().find(id, parameters, options);
  }

  /**
   * Show SIEM HTTP Destination
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SiemHttpDestination get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show SIEM HTTP Destination
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SiemHttpDestination get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create SIEM HTTP Destination
   *
   * @see #create(HashMap, HashMap)
   */
  public static SiemHttpDestination create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create SIEM HTTP Destination
   *
   * @see #create(HashMap, HashMap)
   */
  public static SiemHttpDestination create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create SIEM HTTP Destination
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Name for this Destination</li>
   * <li>{@code additional_headers} (Object): Additional HTTP Headers included in calls to the destination URL</li>
   * <li>{@code sending_active} (Boolean): Whether this SIEM HTTP Destination is currently being sent to or not</li>
   * <li>{@code generic_payload_type} (String): Applicable only for destination type: generic. Indicates the type of HTTP body. Can be json_newline or json_array. json_newline is multiple log entries as JSON separated by newlines. json_array is a single JSON array containing multiple log entries as JSON.</li>
   * <li>{@code file_destination_path} (String): Applicable only for destination type: file. Destination folder path on Files.com.</li>
   * <li>{@code file_format} (String): Applicable only for destination type: file. Generated file format.</li>
   * <li>{@code file_interval_minutes} (Long): Applicable only for destination type: file. Interval, in minutes, between file deliveries. Valid values are 5, 10, 15, 20, 30, 60, 90, 180, 240, 360.</li>
   * <li>{@code splunk_token} (String): Applicable only for destination types: splunk, splunk_compatible. Authentication token for the destination.</li>
   * <li>{@code crowdstrike_token} (String): Applicable only for destination type: crowdstrike. Authentication token provided by Crowdstrike.</li>
   * <li>{@code azure_dcr_immutable_id} (String): Applicable only for destination types: azure, azure_legacy. Immutable ID of the Data Collection Rule.</li>
   * <li>{@code azure_stream_name} (String): Applicable only for destination type: azure. Name of the stream in the DCR that represents the destination table.</li>
   * <li>{@code azure_oauth_client_credentials_tenant_id} (String): Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Tenant ID.</li>
   * <li>{@code azure_oauth_client_credentials_client_id} (String): Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Client ID.</li>
   * <li>{@code azure_oauth_client_credentials_client_secret} (String): Applicable only for destination type: azure. Client Credentials OAuth Client Secret.</li>
   * <li>{@code qradar_username} (String): Applicable only for destination type: qradar. Basic auth username provided by QRadar.</li>
   * <li>{@code qradar_password} (String): Applicable only for destination type: qradar. Basic auth password provided by QRadar.</li>
   * <li>{@code solar_winds_token} (String): Applicable only for destination type: solar_winds. Authentication token provided by Solar Winds.</li>
   * <li>{@code new_relic_api_key} (String): Applicable only for destination type: new_relic. API key provided by New Relic.</li>
   * <li>{@code datadog_api_key} (String): Applicable only for destination type: datadog. API key provided by Datadog.</li>
   * <li>{@code action_send_enabled} (Boolean): Whether or not sending is enabled for action logs.</li>
   * <li>{@code sftp_action_send_enabled} (Boolean): Whether or not sending is enabled for sftp_action logs.</li>
   * <li>{@code ftp_action_send_enabled} (Boolean): Whether or not sending is enabled for ftp_action logs.</li>
   * <li>{@code web_dav_action_send_enabled} (Boolean): Whether or not sending is enabled for web_dav_action logs.</li>
   * <li>{@code sync_send_enabled} (Boolean): Whether or not sending is enabled for sync logs.</li>
   * <li>{@code outbound_connection_send_enabled} (Boolean): Whether or not sending is enabled for outbound_connection logs.</li>
   * <li>{@code automation_send_enabled} (Boolean): Whether or not sending is enabled for automation logs.</li>
   * <li>{@code api_request_send_enabled} (Boolean): Whether or not sending is enabled for api_request logs.</li>
   * <li>{@code public_hosting_request_send_enabled} (Boolean): Whether or not sending is enabled for public_hosting_request logs.</li>
   * <li>{@code email_send_enabled} (Boolean): Whether or not sending is enabled for email logs.</li>
   * <li>{@code exavault_api_request_send_enabled} (Boolean): Whether or not sending is enabled for exavault_api_request logs.</li>
   * <li>{@code settings_change_send_enabled} (Boolean): Whether or not sending is enabled for settings_change logs.</li>
   * <li>{@code destination_type} (String): Required. Destination Type</li>
   * <li>{@code destination_url} (String): Destination Url</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code SiemHttpDestination}
   */
  public static SiemHttpDestination create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().siemHttpDestinations().create(parameters, options);
  }


  /**
   * send_test_entry SIEM HTTP Destination
   *
   * @see #sendTestEntry(HashMap, HashMap)
   */
  public static void sendTestEntry() throws RuntimeException {
    sendTestEntry(null, null);
  }

  /**
   * send_test_entry SIEM HTTP Destination
   *
   * @see #sendTestEntry(HashMap, HashMap)
   */
  public static void sendTestEntry(HashMap<String, Object> parameters) throws RuntimeException {
    sendTestEntry(parameters, null);
  }


  /**
   * send_test_entry SIEM HTTP Destination
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code siem_http_destination_id} (Long): SIEM HTTP Destination ID</li>
   * <li>{@code destination_type} (String): Destination Type</li>
   * <li>{@code destination_url} (String): Destination Url</li>
   * <li>{@code name} (String): Name for this Destination</li>
   * <li>{@code additional_headers} (Object): Additional HTTP Headers included in calls to the destination URL</li>
   * <li>{@code sending_active} (Boolean): Whether this SIEM HTTP Destination is currently being sent to or not</li>
   * <li>{@code generic_payload_type} (String): Applicable only for destination type: generic. Indicates the type of HTTP body. Can be json_newline or json_array. json_newline is multiple log entries as JSON separated by newlines. json_array is a single JSON array containing multiple log entries as JSON.</li>
   * <li>{@code file_destination_path} (String): Applicable only for destination type: file. Destination folder path on Files.com.</li>
   * <li>{@code file_format} (String): Applicable only for destination type: file. Generated file format.</li>
   * <li>{@code file_interval_minutes} (Long): Applicable only for destination type: file. Interval, in minutes, between file deliveries. Valid values are 5, 10, 15, 20, 30, 60, 90, 180, 240, 360.</li>
   * <li>{@code splunk_token} (String): Applicable only for destination types: splunk, splunk_compatible. Authentication token for the destination.</li>
   * <li>{@code crowdstrike_token} (String): Applicable only for destination type: crowdstrike. Authentication token provided by Crowdstrike.</li>
   * <li>{@code azure_dcr_immutable_id} (String): Applicable only for destination types: azure, azure_legacy. Immutable ID of the Data Collection Rule.</li>
   * <li>{@code azure_stream_name} (String): Applicable only for destination type: azure. Name of the stream in the DCR that represents the destination table.</li>
   * <li>{@code azure_oauth_client_credentials_tenant_id} (String): Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Tenant ID.</li>
   * <li>{@code azure_oauth_client_credentials_client_id} (String): Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Client ID.</li>
   * <li>{@code azure_oauth_client_credentials_client_secret} (String): Applicable only for destination type: azure. Client Credentials OAuth Client Secret.</li>
   * <li>{@code qradar_username} (String): Applicable only for destination type: qradar. Basic auth username provided by QRadar.</li>
   * <li>{@code qradar_password} (String): Applicable only for destination type: qradar. Basic auth password provided by QRadar.</li>
   * <li>{@code solar_winds_token} (String): Applicable only for destination type: solar_winds. Authentication token provided by Solar Winds.</li>
   * <li>{@code new_relic_api_key} (String): Applicable only for destination type: new_relic. API key provided by New Relic.</li>
   * <li>{@code datadog_api_key} (String): Applicable only for destination type: datadog. API key provided by Datadog.</li>
   * <li>{@code action_send_enabled} (Boolean): Whether or not sending is enabled for action logs.</li>
   * <li>{@code sftp_action_send_enabled} (Boolean): Whether or not sending is enabled for sftp_action logs.</li>
   * <li>{@code ftp_action_send_enabled} (Boolean): Whether or not sending is enabled for ftp_action logs.</li>
   * <li>{@code web_dav_action_send_enabled} (Boolean): Whether or not sending is enabled for web_dav_action logs.</li>
   * <li>{@code sync_send_enabled} (Boolean): Whether or not sending is enabled for sync logs.</li>
   * <li>{@code outbound_connection_send_enabled} (Boolean): Whether or not sending is enabled for outbound_connection logs.</li>
   * <li>{@code automation_send_enabled} (Boolean): Whether or not sending is enabled for automation logs.</li>
   * <li>{@code api_request_send_enabled} (Boolean): Whether or not sending is enabled for api_request logs.</li>
   * <li>{@code public_hosting_request_send_enabled} (Boolean): Whether or not sending is enabled for public_hosting_request logs.</li>
   * <li>{@code email_send_enabled} (Boolean): Whether or not sending is enabled for email logs.</li>
   * <li>{@code exavault_api_request_send_enabled} (Boolean): Whether or not sending is enabled for exavault_api_request logs.</li>
   * <li>{@code settings_change_send_enabled} (Boolean): Whether or not sending is enabled for settings_change logs.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void sendTestEntry(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().siemHttpDestinations().sendTestEntry(parameters, options);
  }


  /**
   * Update SIEM HTTP Destination
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static SiemHttpDestination update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update SIEM HTTP Destination
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static SiemHttpDestination update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update SIEM HTTP Destination
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static SiemHttpDestination update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update SIEM HTTP Destination
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Name for this Destination</li>
   * <li>{@code additional_headers} (Object): Additional HTTP Headers included in calls to the destination URL</li>
   * <li>{@code sending_active} (Boolean): Whether this SIEM HTTP Destination is currently being sent to or not</li>
   * <li>{@code generic_payload_type} (String): Applicable only for destination type: generic. Indicates the type of HTTP body. Can be json_newline or json_array. json_newline is multiple log entries as JSON separated by newlines. json_array is a single JSON array containing multiple log entries as JSON.</li>
   * <li>{@code file_destination_path} (String): Applicable only for destination type: file. Destination folder path on Files.com.</li>
   * <li>{@code file_format} (String): Applicable only for destination type: file. Generated file format.</li>
   * <li>{@code file_interval_minutes} (Long): Applicable only for destination type: file. Interval, in minutes, between file deliveries. Valid values are 5, 10, 15, 20, 30, 60, 90, 180, 240, 360.</li>
   * <li>{@code splunk_token} (String): Applicable only for destination types: splunk, splunk_compatible. Authentication token for the destination.</li>
   * <li>{@code crowdstrike_token} (String): Applicable only for destination type: crowdstrike. Authentication token provided by Crowdstrike.</li>
   * <li>{@code azure_dcr_immutable_id} (String): Applicable only for destination types: azure, azure_legacy. Immutable ID of the Data Collection Rule.</li>
   * <li>{@code azure_stream_name} (String): Applicable only for destination type: azure. Name of the stream in the DCR that represents the destination table.</li>
   * <li>{@code azure_oauth_client_credentials_tenant_id} (String): Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Tenant ID.</li>
   * <li>{@code azure_oauth_client_credentials_client_id} (String): Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Client ID.</li>
   * <li>{@code azure_oauth_client_credentials_client_secret} (String): Applicable only for destination type: azure. Client Credentials OAuth Client Secret.</li>
   * <li>{@code qradar_username} (String): Applicable only for destination type: qradar. Basic auth username provided by QRadar.</li>
   * <li>{@code qradar_password} (String): Applicable only for destination type: qradar. Basic auth password provided by QRadar.</li>
   * <li>{@code solar_winds_token} (String): Applicable only for destination type: solar_winds. Authentication token provided by Solar Winds.</li>
   * <li>{@code new_relic_api_key} (String): Applicable only for destination type: new_relic. API key provided by New Relic.</li>
   * <li>{@code datadog_api_key} (String): Applicable only for destination type: datadog. API key provided by Datadog.</li>
   * <li>{@code action_send_enabled} (Boolean): Whether or not sending is enabled for action logs.</li>
   * <li>{@code sftp_action_send_enabled} (Boolean): Whether or not sending is enabled for sftp_action logs.</li>
   * <li>{@code ftp_action_send_enabled} (Boolean): Whether or not sending is enabled for ftp_action logs.</li>
   * <li>{@code web_dav_action_send_enabled} (Boolean): Whether or not sending is enabled for web_dav_action logs.</li>
   * <li>{@code sync_send_enabled} (Boolean): Whether or not sending is enabled for sync logs.</li>
   * <li>{@code outbound_connection_send_enabled} (Boolean): Whether or not sending is enabled for outbound_connection logs.</li>
   * <li>{@code automation_send_enabled} (Boolean): Whether or not sending is enabled for automation logs.</li>
   * <li>{@code api_request_send_enabled} (Boolean): Whether or not sending is enabled for api_request logs.</li>
   * <li>{@code public_hosting_request_send_enabled} (Boolean): Whether or not sending is enabled for public_hosting_request logs.</li>
   * <li>{@code email_send_enabled} (Boolean): Whether or not sending is enabled for email logs.</li>
   * <li>{@code exavault_api_request_send_enabled} (Boolean): Whether or not sending is enabled for exavault_api_request logs.</li>
   * <li>{@code settings_change_send_enabled} (Boolean): Whether or not sending is enabled for settings_change logs.</li>
   * <li>{@code destination_type} (String): Destination Type</li>
   * <li>{@code destination_url} (String): Destination Url</li>
   * </ul>
   *
   * @param id Siem Http Destination ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code SiemHttpDestination}
   */
  public static SiemHttpDestination update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().siemHttpDestinations().update(id, parameters, options);
  }


  /**
   * Delete SIEM HTTP Destination
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete SIEM HTTP Destination
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete SIEM HTTP Destination
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete SIEM HTTP Destination
   *
   * @param id Siem Http Destination ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().siemHttpDestinations().delete(id, parameters, options);
  }

  /**
   * Delete SIEM HTTP Destination
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete SIEM HTTP Destination
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
