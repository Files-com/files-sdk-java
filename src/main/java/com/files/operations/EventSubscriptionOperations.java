package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.EventSubscription;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * EventSubscription operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#eventSubscriptions()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.EventSubscription} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class EventSubscriptionOperations extends ResourceOperations {
  public EventSubscriptionOperations(FilesClient client) {
    super(client);
  }

  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `name`, `enabled`, `event_channel_id` or `workspace_id`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `enabled`, `event_channel_id` or `workspace_id`. Valid field combinations are `[ enabled, event_channel_id ]`, `[ workspace_id, enabled ]`, `[ workspace_id, event_channel_id ]` or `[ workspace_id, enabled, event_channel_id ]`.
   */
  public ListIterator<EventSubscription> list() throws RuntimeException {
    return list(null, null);
  }

  public ListIterator<EventSubscription> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  public ListIterator<EventSubscription> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }

    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Object parameters[\"sort_by\"]");
    }

    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Object parameters[\"filter\"]");
    }

    String apiPath = "/event_subscriptions";
    TypeReference<List<EventSubscription>> typeReference = new TypeReference<List<EventSubscription>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public ListIterator<EventSubscription> all() throws RuntimeException {
    return all(null, null);
  }

  public ListIterator<EventSubscription> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Event Subscription ID.
   */
  public EventSubscription find() throws RuntimeException {
    return find(null, null, null);
  }

  public EventSubscription find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public EventSubscription find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public EventSubscription find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/event_subscriptions/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<EventSubscription> typeReference = new TypeReference<EventSubscription>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public EventSubscription get() throws RuntimeException {
    return get(null, null, null);
  }

  public EventSubscription get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   event_channel_id - int64 - Event Channel ID
   *   workspace_id - int64 - Workspace ID. 0 means the default workspace or site-wide.
   *   apply_to_all_workspaces - boolean - If true, this default-workspace subscription applies to events from all workspaces.
   *   name (required) - string - Event Subscription name.
   *   subject - string - Custom subject line to use for notification emails.
   *   message - string - Custom message to include in notification emails.
   *   message_only - boolean - If true, notification email bodies contain only the custom message, omitting event details and the review button. Requires a custom message, defaults to false, and does not affect non-email targets.
   *   enabled - boolean - Whether this Event Subscription can dispatch events.
   *   event_types - array(string) - Event type strings matched by this subscription. Blank means all event types. Valid values: `automation_run.canceled.v1`, `automation_run.failure.v1`, `automation_run.failure_will_retry.v1`, `automation_run.partial_failure.v1`, `automation_run.partial_failure_will_retry.v1`, `automation_run.skipped.v1`, `automation_run.success.v1`, `expectation_evaluation.invalid.v1`, `expectation_evaluation.late.v1`, `expectation_evaluation.missing.v1`, `expectation_evaluation.success.v1`, `expectation_incident.acknowledged.v1`, `expectation_incident.open.v1`, `expectation_incident.resolved.v1`, `expectation_incident.snoozed.v1`, `external_event.client_log.failure.v1`, `external_event.client_log.partial_failure.v1`, `external_event.client_log.skipped.v1`, `external_event.client_log.success.v1`, `pending_work_event.failure.v1`, `pending_work_event.partial_failure.v1`, `pending_work_event.skipped.v1`, `pending_work_event.success.v1`, `siem_http_destination_event.failure.v1`, `siem_http_destination_event.partial_failure.v1`, `siem_http_destination_event.skipped.v1`, `siem_http_destination_event.success.v1`, `sso_event.ldap_login.failure.v1`, `sso_event.ldap_login.partial_failure.v1`, `sso_event.ldap_login.skipped.v1`, `sso_event.ldap_login.success.v1`, `sso_event.ldap_sync.failure.v1`, `sso_event.ldap_sync.partial_failure.v1`, `sso_event.ldap_sync.skipped.v1`, `sso_event.ldap_sync.success.v1`, `sso_event.saml_login.failure.v1`, `sso_event.saml_login.partial_failure.v1`, `sso_event.saml_login.skipped.v1`, `sso_event.saml_login.success.v1`, `sync_run.failure.v1`, `sync_run.partial_failure.v1`, `sync_run.skipped.v1`, `sync_run.success.v1`, `user_security_event.lockout.v1`
   *   filter - object - Conditions on event fields that an event must meet to be delivered, keyed by dot-notation field path. All conditions must match.
   *   delivery_policy - object - Event Subscription delivery policy.
   *   event_target_ids - array(int64) - Event Target IDs this subscription sends to.
   */
  public EventSubscription create() throws RuntimeException {
    return create(null, null);
  }

  public EventSubscription create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  public EventSubscription create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("name") || parameters.get("name") == null) {
      throw new NullPointerException("Parameter missing: name parameters[\"name\"]");
    }

    if (parameters.containsKey("event_channel_id") && !(parameters.get("event_channel_id") instanceof Long || parameters.get("event_channel_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: event_channel_id must be of type Long or Integer parameters[\"event_channel_id\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    if (parameters.containsKey("apply_to_all_workspaces") && !(parameters.get("apply_to_all_workspaces") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: apply_to_all_workspaces must be of type Boolean parameters[\"apply_to_all_workspaces\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("subject") && !(parameters.get("subject") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: subject must be of type String parameters[\"subject\"]");
    }

    if (parameters.containsKey("message") && !(parameters.get("message") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: message must be of type String parameters[\"message\"]");
    }

    if (parameters.containsKey("message_only") && !(parameters.get("message_only") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: message_only must be of type Boolean parameters[\"message_only\"]");
    }

    if (parameters.containsKey("enabled") && !(parameters.get("enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: enabled must be of type Boolean parameters[\"enabled\"]");
    }

    if (parameters.containsKey("event_types") && !(parameters.get("event_types") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: event_types must be of type String[] parameters[\"event_types\"]");
    }

    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Object parameters[\"filter\"]");
    }

    if (parameters.containsKey("delivery_policy") && !(parameters.get("delivery_policy") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: delivery_policy must be of type Object parameters[\"delivery_policy\"]");
    }

    if (parameters.containsKey("event_target_ids") && !(parameters.get("event_target_ids") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: event_target_ids must be of type Long[] parameters[\"event_target_ids\"]");
    }

    String apiPath = "/event_subscriptions";
    TypeReference<EventSubscription> typeReference = new TypeReference<EventSubscription>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Parameters:
   *   event_channel_id - int64 - Event Channel ID
   *   workspace_id - int64 - Workspace ID. 0 means the default workspace or site-wide.
   *   apply_to_all_workspaces - boolean - If true, this default-workspace subscription applies to events from all workspaces.
   *   name - string - Event Subscription name.
   *   subject - string - Custom subject line to use for notification emails.
   *   message - string - Custom message to include in notification emails.
   *   message_only - boolean - If true, notification email bodies contain only the custom message, omitting event details and the review button. Requires a custom message, defaults to false, and does not affect non-email targets.
   *   enabled - boolean - Whether this Event Subscription can dispatch events.
   *   event_types - array(string) - Event type strings matched by this subscription. Blank means all event types. Valid values: `automation_run.canceled.v1`, `automation_run.failure.v1`, `automation_run.failure_will_retry.v1`, `automation_run.partial_failure.v1`, `automation_run.partial_failure_will_retry.v1`, `automation_run.skipped.v1`, `automation_run.success.v1`, `expectation_evaluation.invalid.v1`, `expectation_evaluation.late.v1`, `expectation_evaluation.missing.v1`, `expectation_evaluation.success.v1`, `expectation_incident.acknowledged.v1`, `expectation_incident.open.v1`, `expectation_incident.resolved.v1`, `expectation_incident.snoozed.v1`, `external_event.client_log.failure.v1`, `external_event.client_log.partial_failure.v1`, `external_event.client_log.skipped.v1`, `external_event.client_log.success.v1`, `pending_work_event.failure.v1`, `pending_work_event.partial_failure.v1`, `pending_work_event.skipped.v1`, `pending_work_event.success.v1`, `siem_http_destination_event.failure.v1`, `siem_http_destination_event.partial_failure.v1`, `siem_http_destination_event.skipped.v1`, `siem_http_destination_event.success.v1`, `sso_event.ldap_login.failure.v1`, `sso_event.ldap_login.partial_failure.v1`, `sso_event.ldap_login.skipped.v1`, `sso_event.ldap_login.success.v1`, `sso_event.ldap_sync.failure.v1`, `sso_event.ldap_sync.partial_failure.v1`, `sso_event.ldap_sync.skipped.v1`, `sso_event.ldap_sync.success.v1`, `sso_event.saml_login.failure.v1`, `sso_event.saml_login.partial_failure.v1`, `sso_event.saml_login.skipped.v1`, `sso_event.saml_login.success.v1`, `sync_run.failure.v1`, `sync_run.partial_failure.v1`, `sync_run.skipped.v1`, `sync_run.success.v1`, `user_security_event.lockout.v1`
   *   filter - object - Conditions on event fields that an event must meet to be delivered, keyed by dot-notation field path. All conditions must match.
   *   delivery_policy - object - Event Subscription delivery policy.
   *   event_target_ids - array(int64) - Event Target IDs this subscription sends to.
   */
  public EventSubscription update() throws RuntimeException {
    return update(null, null, null);
  }

  public EventSubscription update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public EventSubscription update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public EventSubscription update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("event_channel_id") && !(parameters.get("event_channel_id") instanceof Long || parameters.get("event_channel_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: event_channel_id must be of type Long or Integer parameters[\"event_channel_id\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    if (parameters.containsKey("apply_to_all_workspaces") && !(parameters.get("apply_to_all_workspaces") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: apply_to_all_workspaces must be of type Boolean parameters[\"apply_to_all_workspaces\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("subject") && !(parameters.get("subject") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: subject must be of type String parameters[\"subject\"]");
    }

    if (parameters.containsKey("message") && !(parameters.get("message") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: message must be of type String parameters[\"message\"]");
    }

    if (parameters.containsKey("message_only") && !(parameters.get("message_only") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: message_only must be of type Boolean parameters[\"message_only\"]");
    }

    if (parameters.containsKey("enabled") && !(parameters.get("enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: enabled must be of type Boolean parameters[\"enabled\"]");
    }

    if (parameters.containsKey("event_types") && !(parameters.get("event_types") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: event_types must be of type String[] parameters[\"event_types\"]");
    }

    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Object parameters[\"filter\"]");
    }

    if (parameters.containsKey("delivery_policy") && !(parameters.get("delivery_policy") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: delivery_policy must be of type Object parameters[\"delivery_policy\"]");
    }

    if (parameters.containsKey("event_target_ids") && !(parameters.get("event_target_ids") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: event_target_ids must be of type Long[] parameters[\"event_target_ids\"]");
    }

    String apiPath = String.format("/event_subscriptions/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<EventSubscription> typeReference = new TypeReference<EventSubscription>() {};
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

    String apiPath = String.format("/event_subscriptions/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
