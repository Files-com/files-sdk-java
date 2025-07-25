# Files.Models.SiemHttpDestination

## Example SiemHttpDestination Object

```
{
  "id": 1,
  "name": "example",
  "destination_type": "example",
  "destination_url": "example",
  "additional_headers": {
    "key": "example value"
  },
  "sending_active": true,
  "generic_payload_type": "example",
  "splunk_token_masked": "example",
  "azure_dcr_immutable_id": "example",
  "azure_stream_name": "example",
  "azure_oauth_client_credentials_tenant_id": "example",
  "azure_oauth_client_credentials_client_id": "example",
  "azure_oauth_client_credentials_client_secret_masked": "example",
  "qradar_username": "example",
  "qradar_password_masked": "example",
  "solar_winds_token_masked": "example",
  "new_relic_api_key_masked": "example",
  "datadog_api_key_masked": "example",
  "sftp_action_send_enabled": true,
  "sftp_action_entries_sent": 1,
  "ftp_action_send_enabled": true,
  "ftp_action_entries_sent": 1,
  "web_dav_action_send_enabled": true,
  "web_dav_action_entries_sent": 1,
  "sync_send_enabled": true,
  "sync_entries_sent": 1,
  "outbound_connection_send_enabled": true,
  "outbound_connection_entries_sent": 1,
  "automation_send_enabled": true,
  "automation_entries_sent": 1,
  "api_request_send_enabled": true,
  "api_request_entries_sent": 1,
  "public_hosting_request_send_enabled": true,
  "public_hosting_request_entries_sent": 1,
  "email_send_enabled": true,
  "email_entries_sent": 1,
  "exavault_api_request_send_enabled": true,
  "exavault_api_request_entries_sent": 1,
  "settings_change_send_enabled": true,
  "settings_change_entries_sent": 1,
  "last_http_call_target_type": "destination_url",
  "last_http_call_success": true,
  "last_http_call_response_code": 1,
  "last_http_call_response_body": "example",
  "last_http_call_error_message": "example",
  "last_http_call_time": "example",
  "last_http_call_duration_ms": 1,
  "most_recent_http_call_success_time": "example",
  "connection_test_entry": "example"
}
```

* `id` / `id`  (int64): SIEM HTTP Destination ID
* `name` / `name`  (string): Name for this Destination
* `destination_type` / `destinationType`  (string): Destination Type
* `destination_url` / `destinationUrl`  (string): Destination Url
* `additional_headers` / `additionalHeaders`  (object): Additional HTTP Headers included in calls to the destination URL
* `sending_active` / `sendingActive`  (boolean): Whether this SIEM HTTP Destination is currently being sent to or not
* `generic_payload_type` / `genericPayloadType`  (string): Applicable only for destination type: generic. Indicates the type of HTTP body. Can be json_newline or json_array. json_newline is multiple log entries as JSON separated by newlines. json_array is a single JSON array containing multiple log entries as JSON.
* `splunk_token_masked` / `splunkTokenMasked`  (string): Applicable only for destination type: splunk. Authentication token provided by Splunk.
* `azure_dcr_immutable_id` / `azureDcrImmutableId`  (string): Applicable only for destination types: azure, azure_legacy. Immutable ID of the Data Collection Rule.
* `azure_stream_name` / `azureStreamName`  (string): Applicable only for destination type: azure. Name of the stream in the DCR that represents the destination table.
* `azure_oauth_client_credentials_tenant_id` / `azureOauthClientCredentialsTenantId`  (string): Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Tenant ID.
* `azure_oauth_client_credentials_client_id` / `azureOauthClientCredentialsClientId`  (string): Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Client ID.
* `azure_oauth_client_credentials_client_secret_masked` / `azureOauthClientCredentialsClientSecretMasked`  (string): Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Client Secret.
* `qradar_username` / `qradarUsername`  (string): Applicable only for destination type: qradar. Basic auth username provided by QRadar.
* `qradar_password_masked` / `qradarPasswordMasked`  (string): Applicable only for destination type: qradar. Basic auth password provided by QRadar.
* `solar_winds_token_masked` / `solarWindsTokenMasked`  (string): Applicable only for destination type: solar_winds. Authentication token provided by Solar Winds.
* `new_relic_api_key_masked` / `newRelicApiKeyMasked`  (string): Applicable only for destination type: new_relic. API key provided by New Relic.
* `datadog_api_key_masked` / `datadogApiKeyMasked`  (string): Applicable only for destination type: datadog. API key provided by Datadog.
* `sftp_action_send_enabled` / `sftpActionSendEnabled`  (boolean): Whether or not sending is enabled for sftp_action logs.
* `sftp_action_entries_sent` / `sftpActionEntriesSent`  (int64): Number of log entries sent for the lifetime of this destination.
* `ftp_action_send_enabled` / `ftpActionSendEnabled`  (boolean): Whether or not sending is enabled for ftp_action logs.
* `ftp_action_entries_sent` / `ftpActionEntriesSent`  (int64): Number of log entries sent for the lifetime of this destination.
* `web_dav_action_send_enabled` / `webDavActionSendEnabled`  (boolean): Whether or not sending is enabled for web_dav_action logs.
* `web_dav_action_entries_sent` / `webDavActionEntriesSent`  (int64): Number of log entries sent for the lifetime of this destination.
* `sync_send_enabled` / `syncSendEnabled`  (boolean): Whether or not sending is enabled for sync logs.
* `sync_entries_sent` / `syncEntriesSent`  (int64): Number of log entries sent for the lifetime of this destination.
* `outbound_connection_send_enabled` / `outboundConnectionSendEnabled`  (boolean): Whether or not sending is enabled for outbound_connection logs.
* `outbound_connection_entries_sent` / `outboundConnectionEntriesSent`  (int64): Number of log entries sent for the lifetime of this destination.
* `automation_send_enabled` / `automationSendEnabled`  (boolean): Whether or not sending is enabled for automation logs.
* `automation_entries_sent` / `automationEntriesSent`  (int64): Number of log entries sent for the lifetime of this destination.
* `api_request_send_enabled` / `apiRequestSendEnabled`  (boolean): Whether or not sending is enabled for api_request logs.
* `api_request_entries_sent` / `apiRequestEntriesSent`  (int64): Number of log entries sent for the lifetime of this destination.
* `public_hosting_request_send_enabled` / `publicHostingRequestSendEnabled`  (boolean): Whether or not sending is enabled for public_hosting_request logs.
* `public_hosting_request_entries_sent` / `publicHostingRequestEntriesSent`  (int64): Number of log entries sent for the lifetime of this destination.
* `email_send_enabled` / `emailSendEnabled`  (boolean): Whether or not sending is enabled for email logs.
* `email_entries_sent` / `emailEntriesSent`  (int64): Number of log entries sent for the lifetime of this destination.
* `exavault_api_request_send_enabled` / `exavaultApiRequestSendEnabled`  (boolean): Whether or not sending is enabled for exavault_api_request logs.
* `exavault_api_request_entries_sent` / `exavaultApiRequestEntriesSent`  (int64): Number of log entries sent for the lifetime of this destination.
* `settings_change_send_enabled` / `settingsChangeSendEnabled`  (boolean): Whether or not sending is enabled for settings_change logs.
* `settings_change_entries_sent` / `settingsChangeEntriesSent`  (int64): Number of log entries sent for the lifetime of this destination.
* `last_http_call_target_type` / `lastHttpCallTargetType`  (string): Type of URL that was last called. Can be `destination_url` or `azure_oauth_client_credentials_url`
* `last_http_call_success` / `lastHttpCallSuccess`  (boolean): Was the last HTTP call made successful?
* `last_http_call_response_code` / `lastHttpCallResponseCode`  (int64): Last HTTP Call Response Code
* `last_http_call_response_body` / `lastHttpCallResponseBody`  (string): Last HTTP Call Response Body. Large responses are truncated.
* `last_http_call_error_message` / `lastHttpCallErrorMessage`  (string): Last HTTP Call Error Message if applicable
* `last_http_call_time` / `lastHttpCallTime`  (string): Time of Last HTTP Call
* `last_http_call_duration_ms` / `lastHttpCallDurationMs`  (int64): Duration of the last HTTP Call in milliseconds
* `most_recent_http_call_success_time` / `mostRecentHttpCallSuccessTime`  (string): Time of Most Recent Successful HTTP Call
* `connection_test_entry` / `connectionTestEntry`  (string): Connection Test Entry
* `splunk_token` / `splunkToken`  (string): Applicable only for destination type: splunk. Authentication token provided by Splunk.
* `azure_oauth_client_credentials_client_secret` / `azureOauthClientCredentialsClientSecret`  (string): Applicable only for destination type: azure. Client Credentials OAuth Client Secret.
* `qradar_password` / `qradarPassword`  (string): Applicable only for destination type: qradar. Basic auth password provided by QRadar.
* `solar_winds_token` / `solarWindsToken`  (string): Applicable only for destination type: solar_winds. Authentication token provided by Solar Winds.
* `new_relic_api_key` / `newRelicApiKey`  (string): Applicable only for destination type: new_relic. API key provided by New Relic.
* `datadog_api_key` / `datadogApiKey`  (string): Applicable only for destination type: datadog. API key provided by Datadog.


---

## List SIEM HTTP Destinations

```
ListIterator<SiemHttpDestination> siemHttpDestination = SiemHttpDestination.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).


---

## Show SIEM HTTP Destination

```
SiemHttpDestination siemHttpDestination = SiemHttpDestination.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Siem Http Destination ID.


---

## Create SIEM HTTP Destination

```
SiemHttpDestination siemHttpDestination = SiemHttpDestination.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `name` (String): Name for this Destination
* `additional_headers` (Map<String, String>): Additional HTTP Headers included in calls to the destination URL
* `sending_active` (Boolean): Whether this SIEM HTTP Destination is currently being sent to or not
* `generic_payload_type` (String): Applicable only for destination type: generic. Indicates the type of HTTP body. Can be json_newline or json_array. json_newline is multiple log entries as JSON separated by newlines. json_array is a single JSON array containing multiple log entries as JSON.
* `splunk_token` (String): Applicable only for destination type: splunk. Authentication token provided by Splunk.
* `azure_dcr_immutable_id` (String): Applicable only for destination types: azure, azure_legacy. Immutable ID of the Data Collection Rule.
* `azure_stream_name` (String): Applicable only for destination type: azure. Name of the stream in the DCR that represents the destination table.
* `azure_oauth_client_credentials_tenant_id` (String): Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Tenant ID.
* `azure_oauth_client_credentials_client_id` (String): Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Client ID.
* `azure_oauth_client_credentials_client_secret` (String): Applicable only for destination type: azure. Client Credentials OAuth Client Secret.
* `qradar_username` (String): Applicable only for destination type: qradar. Basic auth username provided by QRadar.
* `qradar_password` (String): Applicable only for destination type: qradar. Basic auth password provided by QRadar.
* `solar_winds_token` (String): Applicable only for destination type: solar_winds. Authentication token provided by Solar Winds.
* `new_relic_api_key` (String): Applicable only for destination type: new_relic. API key provided by New Relic.
* `datadog_api_key` (String): Applicable only for destination type: datadog. API key provided by Datadog.
* `sftp_action_send_enabled` (Boolean): Whether or not sending is enabled for sftp_action logs.
* `ftp_action_send_enabled` (Boolean): Whether or not sending is enabled for ftp_action logs.
* `web_dav_action_send_enabled` (Boolean): Whether or not sending is enabled for web_dav_action logs.
* `sync_send_enabled` (Boolean): Whether or not sending is enabled for sync logs.
* `outbound_connection_send_enabled` (Boolean): Whether or not sending is enabled for outbound_connection logs.
* `automation_send_enabled` (Boolean): Whether or not sending is enabled for automation logs.
* `api_request_send_enabled` (Boolean): Whether or not sending is enabled for api_request logs.
* `public_hosting_request_send_enabled` (Boolean): Whether or not sending is enabled for public_hosting_request logs.
* `email_send_enabled` (Boolean): Whether or not sending is enabled for email logs.
* `exavault_api_request_send_enabled` (Boolean): Whether or not sending is enabled for exavault_api_request logs.
* `settings_change_send_enabled` (Boolean): Whether or not sending is enabled for settings_change logs.
* `destination_type` (String): Required - Destination Type
* `destination_url` (String): Required - Destination Url


---

## send_test_entry SIEM HTTP Destination

```
void siemHttpDestination = SiemHttpDestination.sendTestEntry(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `siem_http_destination_id` (Long): SIEM HTTP Destination ID
* `destination_type` (String): Destination Type
* `destination_url` (String): Destination Url
* `name` (String): Name for this Destination
* `additional_headers` (Map<String, String>): Additional HTTP Headers included in calls to the destination URL
* `sending_active` (Boolean): Whether this SIEM HTTP Destination is currently being sent to or not
* `generic_payload_type` (String): Applicable only for destination type: generic. Indicates the type of HTTP body. Can be json_newline or json_array. json_newline is multiple log entries as JSON separated by newlines. json_array is a single JSON array containing multiple log entries as JSON.
* `splunk_token` (String): Applicable only for destination type: splunk. Authentication token provided by Splunk.
* `azure_dcr_immutable_id` (String): Applicable only for destination types: azure, azure_legacy. Immutable ID of the Data Collection Rule.
* `azure_stream_name` (String): Applicable only for destination type: azure. Name of the stream in the DCR that represents the destination table.
* `azure_oauth_client_credentials_tenant_id` (String): Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Tenant ID.
* `azure_oauth_client_credentials_client_id` (String): Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Client ID.
* `azure_oauth_client_credentials_client_secret` (String): Applicable only for destination type: azure. Client Credentials OAuth Client Secret.
* `qradar_username` (String): Applicable only for destination type: qradar. Basic auth username provided by QRadar.
* `qradar_password` (String): Applicable only for destination type: qradar. Basic auth password provided by QRadar.
* `solar_winds_token` (String): Applicable only for destination type: solar_winds. Authentication token provided by Solar Winds.
* `new_relic_api_key` (String): Applicable only for destination type: new_relic. API key provided by New Relic.
* `datadog_api_key` (String): Applicable only for destination type: datadog. API key provided by Datadog.
* `sftp_action_send_enabled` (Boolean): Whether or not sending is enabled for sftp_action logs.
* `ftp_action_send_enabled` (Boolean): Whether or not sending is enabled for ftp_action logs.
* `web_dav_action_send_enabled` (Boolean): Whether or not sending is enabled for web_dav_action logs.
* `sync_send_enabled` (Boolean): Whether or not sending is enabled for sync logs.
* `outbound_connection_send_enabled` (Boolean): Whether or not sending is enabled for outbound_connection logs.
* `automation_send_enabled` (Boolean): Whether or not sending is enabled for automation logs.
* `api_request_send_enabled` (Boolean): Whether or not sending is enabled for api_request logs.
* `public_hosting_request_send_enabled` (Boolean): Whether or not sending is enabled for public_hosting_request logs.
* `email_send_enabled` (Boolean): Whether or not sending is enabled for email logs.
* `exavault_api_request_send_enabled` (Boolean): Whether or not sending is enabled for exavault_api_request logs.
* `settings_change_send_enabled` (Boolean): Whether or not sending is enabled for settings_change logs.


---

## Update SIEM HTTP Destination

```
SiemHttpDestination siemHttpDestination = SiemHttpDestination.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Siem Http Destination ID.
* `name` (String): Name for this Destination
* `additional_headers` (Map<String, String>): Additional HTTP Headers included in calls to the destination URL
* `sending_active` (Boolean): Whether this SIEM HTTP Destination is currently being sent to or not
* `generic_payload_type` (String): Applicable only for destination type: generic. Indicates the type of HTTP body. Can be json_newline or json_array. json_newline is multiple log entries as JSON separated by newlines. json_array is a single JSON array containing multiple log entries as JSON.
* `splunk_token` (String): Applicable only for destination type: splunk. Authentication token provided by Splunk.
* `azure_dcr_immutable_id` (String): Applicable only for destination types: azure, azure_legacy. Immutable ID of the Data Collection Rule.
* `azure_stream_name` (String): Applicable only for destination type: azure. Name of the stream in the DCR that represents the destination table.
* `azure_oauth_client_credentials_tenant_id` (String): Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Tenant ID.
* `azure_oauth_client_credentials_client_id` (String): Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Client ID.
* `azure_oauth_client_credentials_client_secret` (String): Applicable only for destination type: azure. Client Credentials OAuth Client Secret.
* `qradar_username` (String): Applicable only for destination type: qradar. Basic auth username provided by QRadar.
* `qradar_password` (String): Applicable only for destination type: qradar. Basic auth password provided by QRadar.
* `solar_winds_token` (String): Applicable only for destination type: solar_winds. Authentication token provided by Solar Winds.
* `new_relic_api_key` (String): Applicable only for destination type: new_relic. API key provided by New Relic.
* `datadog_api_key` (String): Applicable only for destination type: datadog. API key provided by Datadog.
* `sftp_action_send_enabled` (Boolean): Whether or not sending is enabled for sftp_action logs.
* `ftp_action_send_enabled` (Boolean): Whether or not sending is enabled for ftp_action logs.
* `web_dav_action_send_enabled` (Boolean): Whether or not sending is enabled for web_dav_action logs.
* `sync_send_enabled` (Boolean): Whether or not sending is enabled for sync logs.
* `outbound_connection_send_enabled` (Boolean): Whether or not sending is enabled for outbound_connection logs.
* `automation_send_enabled` (Boolean): Whether or not sending is enabled for automation logs.
* `api_request_send_enabled` (Boolean): Whether or not sending is enabled for api_request logs.
* `public_hosting_request_send_enabled` (Boolean): Whether or not sending is enabled for public_hosting_request logs.
* `email_send_enabled` (Boolean): Whether or not sending is enabled for email logs.
* `exavault_api_request_send_enabled` (Boolean): Whether or not sending is enabled for exavault_api_request logs.
* `settings_change_send_enabled` (Boolean): Whether or not sending is enabled for settings_change logs.
* `destination_type` (String): Destination Type
* `destination_url` (String): Destination Url


---

## Delete SIEM HTTP Destination

```
void siemHttpDestination = SiemHttpDestination.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Siem Http Destination ID.


---

## Update SIEM HTTP Destination

```
SiemHttpDestination siemHttpDestination = SiemHttpDestination.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("name", "example");
parameters.put("additional_headers", {"key":"example value"});
parameters.put("sending_active", true);
parameters.put("generic_payload_type", "example");
parameters.put("azure_dcr_immutable_id", "example");
parameters.put("azure_stream_name", "example");
parameters.put("azure_oauth_client_credentials_tenant_id", "example");
parameters.put("azure_oauth_client_credentials_client_id", "example");
parameters.put("qradar_username", "example");
parameters.put("sftp_action_send_enabled", true);
parameters.put("ftp_action_send_enabled", true);
parameters.put("web_dav_action_send_enabled", true);
parameters.put("sync_send_enabled", true);
parameters.put("outbound_connection_send_enabled", true);
parameters.put("automation_send_enabled", true);
parameters.put("api_request_send_enabled", true);
parameters.put("public_hosting_request_send_enabled", true);
parameters.put("email_send_enabled", true);
parameters.put("exavault_api_request_send_enabled", true);
parameters.put("settings_change_send_enabled", true);
parameters.put("destination_type", "example");
parameters.put("destination_url", "example");

siemHttpDestination.update(parameters);
```

### Parameters

* `id` (Long): Required - Siem Http Destination ID.
* `name` (String): Name for this Destination
* `additional_headers` (Map<String, String>): Additional HTTP Headers included in calls to the destination URL
* `sending_active` (Boolean): Whether this SIEM HTTP Destination is currently being sent to or not
* `generic_payload_type` (String): Applicable only for destination type: generic. Indicates the type of HTTP body. Can be json_newline or json_array. json_newline is multiple log entries as JSON separated by newlines. json_array is a single JSON array containing multiple log entries as JSON.
* `splunk_token` (String): Applicable only for destination type: splunk. Authentication token provided by Splunk.
* `azure_dcr_immutable_id` (String): Applicable only for destination types: azure, azure_legacy. Immutable ID of the Data Collection Rule.
* `azure_stream_name` (String): Applicable only for destination type: azure. Name of the stream in the DCR that represents the destination table.
* `azure_oauth_client_credentials_tenant_id` (String): Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Tenant ID.
* `azure_oauth_client_credentials_client_id` (String): Applicable only for destination types: azure, azure_legacy. Client Credentials OAuth Client ID.
* `azure_oauth_client_credentials_client_secret` (String): Applicable only for destination type: azure. Client Credentials OAuth Client Secret.
* `qradar_username` (String): Applicable only for destination type: qradar. Basic auth username provided by QRadar.
* `qradar_password` (String): Applicable only for destination type: qradar. Basic auth password provided by QRadar.
* `solar_winds_token` (String): Applicable only for destination type: solar_winds. Authentication token provided by Solar Winds.
* `new_relic_api_key` (String): Applicable only for destination type: new_relic. API key provided by New Relic.
* `datadog_api_key` (String): Applicable only for destination type: datadog. API key provided by Datadog.
* `sftp_action_send_enabled` (Boolean): Whether or not sending is enabled for sftp_action logs.
* `ftp_action_send_enabled` (Boolean): Whether or not sending is enabled for ftp_action logs.
* `web_dav_action_send_enabled` (Boolean): Whether or not sending is enabled for web_dav_action logs.
* `sync_send_enabled` (Boolean): Whether or not sending is enabled for sync logs.
* `outbound_connection_send_enabled` (Boolean): Whether or not sending is enabled for outbound_connection logs.
* `automation_send_enabled` (Boolean): Whether or not sending is enabled for automation logs.
* `api_request_send_enabled` (Boolean): Whether or not sending is enabled for api_request logs.
* `public_hosting_request_send_enabled` (Boolean): Whether or not sending is enabled for public_hosting_request logs.
* `email_send_enabled` (Boolean): Whether or not sending is enabled for email logs.
* `exavault_api_request_send_enabled` (Boolean): Whether or not sending is enabled for exavault_api_request logs.
* `settings_change_send_enabled` (Boolean): Whether or not sending is enabled for settings_change logs.
* `destination_type` (String): Destination Type
* `destination_url` (String): Destination Url


---

## Delete SIEM HTTP Destination

```
SiemHttpDestination siemHttpDestination = SiemHttpDestination.find(id);

HashMap<String, Object> parameters = new HashMap<>();

siemHttpDestination.delete(parameters);
```

### Parameters

* `id` (Long): Required - Siem Http Destination ID.
