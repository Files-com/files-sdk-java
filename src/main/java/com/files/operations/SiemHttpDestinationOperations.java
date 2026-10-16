package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.SiemHttpDestination;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * SiemHttpDestination operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#siemHttpDestinations()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.SiemHttpDestination} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class SiemHttpDestinationOperations extends ResourceOperations {
  public SiemHttpDestinationOperations(FilesClient client) {
    super(client);
  }

  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   */
  public ListIterator<SiemHttpDestination> list() throws RuntimeException {
    return list(null, null);
  }

  public ListIterator<SiemHttpDestination> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  public ListIterator<SiemHttpDestination> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }

    String apiPath = "/siem_http_destinations";
    TypeReference<List<SiemHttpDestination>> typeReference = new TypeReference<List<SiemHttpDestination>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public ListIterator<SiemHttpDestination> all() throws RuntimeException {
    return all(null, null);
  }

  public ListIterator<SiemHttpDestination> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Siem Http Destination ID.
   */
  public SiemHttpDestination find() throws RuntimeException {
    return find(null, null, null);
  }

  public SiemHttpDestination find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public SiemHttpDestination find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public SiemHttpDestination find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }

    String apiPath = String.format("/siem_http_destinations/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<SiemHttpDestination> typeReference = new TypeReference<SiemHttpDestination>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public SiemHttpDestination get() throws RuntimeException {
    return get(null, null, null);
  }

  public SiemHttpDestination get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public SiemHttpDestination create() throws RuntimeException {
    return create(null, null);
  }

  public SiemHttpDestination create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  public SiemHttpDestination create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

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

    if (parameters.containsKey("crowdstrike_token") && !(parameters.get("crowdstrike_token") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: crowdstrike_token must be of type String parameters[\"crowdstrike_token\"]");
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

    if (parameters.containsKey("action_send_enabled") && !(parameters.get("action_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: action_send_enabled must be of type Boolean parameters[\"action_send_enabled\"]");
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

    String apiPath = "/siem_http_destinations";
    TypeReference<SiemHttpDestination> typeReference = new TypeReference<SiemHttpDestination>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
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
  public void sendTestEntry() throws RuntimeException {
    sendTestEntry(null, null);
  }

  public void sendTestEntry(HashMap<String, Object> parameters) throws RuntimeException {
    sendTestEntry(parameters, null);
  }

  public void sendTestEntry(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

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

    if (parameters.containsKey("crowdstrike_token") && !(parameters.get("crowdstrike_token") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: crowdstrike_token must be of type String parameters[\"crowdstrike_token\"]");
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

    if (parameters.containsKey("action_send_enabled") && !(parameters.get("action_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: action_send_enabled must be of type Boolean parameters[\"action_send_enabled\"]");
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

    String apiPath = "/siem_http_destinations/send_test_entry";
    request(RequestMethods.POST, apiPath, parameters, options);
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
  public SiemHttpDestination update() throws RuntimeException {
    return update(null, null, null);
  }

  public SiemHttpDestination update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public SiemHttpDestination update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public SiemHttpDestination update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

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

    if (parameters.containsKey("crowdstrike_token") && !(parameters.get("crowdstrike_token") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: crowdstrike_token must be of type String parameters[\"crowdstrike_token\"]");
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

    if (parameters.containsKey("action_send_enabled") && !(parameters.get("action_send_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: action_send_enabled must be of type Boolean parameters[\"action_send_enabled\"]");
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

    String apiPath = String.format("/siem_http_destinations/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<SiemHttpDestination> typeReference = new TypeReference<SiemHttpDestination>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  public void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }

    String apiPath = String.format("/siem_http_destinations/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
