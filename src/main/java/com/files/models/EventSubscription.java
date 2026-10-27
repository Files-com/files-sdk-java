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
import java.util.Date;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventSubscription implements ModelInterface {
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

  // One mapper for every EventSubscription, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code EventSubscription} with no attributes set.
   */
  public EventSubscription() {
    this(null, null);
  }

  /**
   * Creates a {@code EventSubscription} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public EventSubscription(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code EventSubscription} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public EventSubscription(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Event Subscription ID
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
   * Event Channel ID
   */
  @JsonProperty("event_channel_id")
  public Long eventChannelId;

  /**
   * Gets {@link #eventChannelId}.
   *
   * @return the value of {@link #eventChannelId}
   */
  public Long getEventChannelId() {
    return eventChannelId;
  }

  /**
   * Sets {@link #eventChannelId}.
   *
   * @param eventChannelId the new value
   */
  public void setEventChannelId(Long eventChannelId) {
    this.eventChannelId = eventChannelId;
  }

  /**
   * Workspace ID. 0 means the default workspace or site-wide.
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
   * Sets {@link #workspaceId}.
   *
   * @param workspaceId the new value
   */
  public void setWorkspaceId(Long workspaceId) {
    this.workspaceId = workspaceId;
  }

  /**
   * If true, this default-workspace subscription applies to events from all workspaces.
   */
  @JsonProperty("apply_to_all_workspaces")
  public Boolean applyToAllWorkspaces;

  /**
   * Gets {@link #applyToAllWorkspaces}.
   *
   * @return the value of {@link #applyToAllWorkspaces}
   */
  public Boolean getApplyToAllWorkspaces() {
    return applyToAllWorkspaces;
  }

  /**
   * Sets {@link #applyToAllWorkspaces}.
   *
   * @param applyToAllWorkspaces the new value
   */
  public void setApplyToAllWorkspaces(Boolean applyToAllWorkspaces) {
    this.applyToAllWorkspaces = applyToAllWorkspaces;
  }

  /**
   * Event Subscription name.
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
   * Custom subject line to use for notification emails.
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
   * Sets {@link #subject}.
   *
   * @param subject the new value
   */
  public void setSubject(String subject) {
    this.subject = subject;
  }

  /**
   * Custom message to include in notification emails.
   */
  @JsonProperty("message")
  public String message;

  /**
   * Gets {@link #message}.
   *
   * @return the value of {@link #message}
   */
  public String getMessage() {
    return message;
  }

  /**
   * Sets {@link #message}.
   *
   * @param message the new value
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * If true, notification email bodies contain only the custom message, omitting event details and the review button. Requires a custom message, defaults to false, and does not affect non-email targets.
   */
  @JsonProperty("message_only")
  public Boolean messageOnly;

  /**
   * Gets {@link #messageOnly}.
   *
   * @return the value of {@link #messageOnly}
   */
  public Boolean getMessageOnly() {
    return messageOnly;
  }

  /**
   * Sets {@link #messageOnly}.
   *
   * @param messageOnly the new value
   */
  public void setMessageOnly(Boolean messageOnly) {
    this.messageOnly = messageOnly;
  }

  /**
   * Whether this Event Subscription can dispatch events.
   */
  @JsonProperty("enabled")
  public Boolean enabled;

  /**
   * Gets {@link #enabled}.
   *
   * @return the value of {@link #enabled}
   */
  public Boolean getEnabled() {
    return enabled;
  }

  /**
   * Sets {@link #enabled}.
   *
   * @param enabled the new value
   */
  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  /**
   * Event type strings matched by this subscription. Blank means all event types. Valid values: `automation_run.canceled.v1`, `automation_run.failure.v1`, `automation_run.failure_will_retry.v1`, `automation_run.partial_failure.v1`, `automation_run.partial_failure_will_retry.v1`, `automation_run.skipped.v1`, `automation_run.success.v1`, `expectation_evaluation.invalid.v1`, `expectation_evaluation.late.v1`, `expectation_evaluation.missing.v1`, `expectation_evaluation.success.v1`, `expectation_incident.acknowledged.v1`, `expectation_incident.open.v1`, `expectation_incident.resolved.v1`, `expectation_incident.snoozed.v1`, `external_event.client_log.failure.v1`, `external_event.client_log.partial_failure.v1`, `external_event.client_log.skipped.v1`, `external_event.client_log.success.v1`, `pending_work_event.failure.v1`, `pending_work_event.partial_failure.v1`, `pending_work_event.skipped.v1`, `pending_work_event.success.v1`, `siem_http_destination_event.failure.v1`, `siem_http_destination_event.partial_failure.v1`, `siem_http_destination_event.skipped.v1`, `siem_http_destination_event.success.v1`, `sso_event.ldap_login.failure.v1`, `sso_event.ldap_login.partial_failure.v1`, `sso_event.ldap_login.skipped.v1`, `sso_event.ldap_login.success.v1`, `sso_event.ldap_sync.failure.v1`, `sso_event.ldap_sync.partial_failure.v1`, `sso_event.ldap_sync.skipped.v1`, `sso_event.ldap_sync.success.v1`, `sso_event.saml_login.failure.v1`, `sso_event.saml_login.partial_failure.v1`, `sso_event.saml_login.skipped.v1`, `sso_event.saml_login.success.v1`, `sync_run.failure.v1`, `sync_run.partial_failure.v1`, `sync_run.skipped.v1`, `sync_run.success.v1`, `user_security_event.lockout.v1`
   */
  @JsonProperty("event_types")
  public String[] eventTypes;

  /**
   * Gets {@link #eventTypes}.
   *
   * @return the value of {@link #eventTypes}
   */
  public String[] getEventTypes() {
    return eventTypes;
  }

  /**
   * Sets {@link #eventTypes}.
   *
   * @param eventTypes the new value
   */
  public void setEventTypes(String[] eventTypes) {
    this.eventTypes = eventTypes;
  }

  /**
   * Conditions on event fields that an event must meet to be delivered, keyed by dot-notation field path. All conditions must match.
   */
  @JsonProperty("filter")
  public Object filter;

  /**
   * Gets {@link #filter}.
   *
   * @return the value of {@link #filter}
   */
  public Object getFilter() {
    return filter;
  }

  /**
   * Sets {@link #filter}.
   *
   * @param filter the new value
   */
  public void setFilter(Object filter) {
    this.filter = filter;
  }

  /**
   * Event Subscription delivery policy. Overrides retry settings from each Event Target's delivery policy.
   */
  @JsonProperty("delivery_policy")
  public Object deliveryPolicy;

  /**
   * Gets {@link #deliveryPolicy}.
   *
   * @return the value of {@link #deliveryPolicy}
   */
  public Object getDeliveryPolicy() {
    return deliveryPolicy;
  }

  /**
   * Sets {@link #deliveryPolicy}.
   *
   * @param deliveryPolicy the new value
   */
  public void setDeliveryPolicy(Object deliveryPolicy) {
    this.deliveryPolicy = deliveryPolicy;
  }

  /**
   * Event Target IDs this subscription sends to.
   */
  @JsonProperty("event_target_ids")
  public Long[] eventTargetIds;

  /**
   * Gets {@link #eventTargetIds}.
   *
   * @return the value of {@link #eventTargetIds}
   */
  public Long[] getEventTargetIds() {
    return eventTargetIds;
  }

  /**
   * Sets {@link #eventTargetIds}.
   *
   * @param eventTargetIds the new value
   */
  public void setEventTargetIds(Long[] eventTargetIds) {
    this.eventTargetIds = eventTargetIds;
  }

  /**
   * Event Subscription create date/time.
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
   * Event Subscription update date/time.
   */
  @JsonProperty("updated_at")
  public Date updatedAt;

  /**
   * Gets {@link #updatedAt}.
   *
   * @return the value of {@link #updatedAt}
   */
  public Date getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Updates this EventSubscription. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update Event Subscription
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code event_channel_id} (Long): Event Channel ID</li>
   * <li>{@code workspace_id} (Long): Workspace ID. 0 means the default workspace or site-wide.</li>
   * <li>{@code apply_to_all_workspaces} (Boolean): If true, this default-workspace subscription applies to events from all workspaces.</li>
   * <li>{@code name} (String): Event Subscription name.</li>
   * <li>{@code subject} (String): Custom subject line to use for notification emails.</li>
   * <li>{@code message} (String): Custom message to include in notification emails.</li>
   * <li>{@code message_only} (Boolean): If true, notification email bodies contain only the custom message, omitting event details and the review button. Requires a custom message, defaults to false, and does not affect non-email targets.</li>
   * <li>{@code enabled} (Boolean): Whether this Event Subscription can dispatch events.</li>
   * <li>{@code event_types} (String[]): Event type strings matched by this subscription. Blank means all event types. Valid values: `automation_run.canceled.v1`, `automation_run.failure.v1`, `automation_run.failure_will_retry.v1`, `automation_run.partial_failure.v1`, `automation_run.partial_failure_will_retry.v1`, `automation_run.skipped.v1`, `automation_run.success.v1`, `expectation_evaluation.invalid.v1`, `expectation_evaluation.late.v1`, `expectation_evaluation.missing.v1`, `expectation_evaluation.success.v1`, `expectation_incident.acknowledged.v1`, `expectation_incident.open.v1`, `expectation_incident.resolved.v1`, `expectation_incident.snoozed.v1`, `external_event.client_log.failure.v1`, `external_event.client_log.partial_failure.v1`, `external_event.client_log.skipped.v1`, `external_event.client_log.success.v1`, `pending_work_event.failure.v1`, `pending_work_event.partial_failure.v1`, `pending_work_event.skipped.v1`, `pending_work_event.success.v1`, `siem_http_destination_event.failure.v1`, `siem_http_destination_event.partial_failure.v1`, `siem_http_destination_event.skipped.v1`, `siem_http_destination_event.success.v1`, `sso_event.ldap_login.failure.v1`, `sso_event.ldap_login.partial_failure.v1`, `sso_event.ldap_login.skipped.v1`, `sso_event.ldap_login.success.v1`, `sso_event.ldap_sync.failure.v1`, `sso_event.ldap_sync.partial_failure.v1`, `sso_event.ldap_sync.skipped.v1`, `sso_event.ldap_sync.success.v1`, `sso_event.saml_login.failure.v1`, `sso_event.saml_login.partial_failure.v1`, `sso_event.saml_login.skipped.v1`, `sso_event.saml_login.success.v1`, `sync_run.failure.v1`, `sync_run.partial_failure.v1`, `sync_run.skipped.v1`, `sync_run.success.v1`, `user_security_event.lockout.v1`</li>
   * <li>{@code filter} (Object): Conditions on event fields that an event must meet to be delivered, keyed by dot-notation field path. All conditions must match.</li>
   * <li>{@code delivery_policy} (Object): Event Subscription delivery policy. Overrides retry settings from each Event Target's delivery policy.</li>
   * <li>{@code event_target_ids} (Long[]): Event Target IDs this subscription sends to.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code EventSubscription}
   */
  public EventSubscription update(HashMap<String, Object> parameters) throws IOException {
    EventSubscription updated = filesClient().eventSubscriptions().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete Event Subscription
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().eventSubscriptions().delete(this.id, parameters, this.options);
  }

  /**
   * Delete Event Subscription
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this EventSubscription: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().eventSubscriptions().create(parameters, this.options));
    }
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
    if (this.eventTypes != null) {
      parameters.put("event_types", this.eventTypes);
    }
    if (this.eventTargetIds != null) {
      parameters.put("event_target_ids", this.eventTargetIds);
    }
    return parameters;
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(EventSubscription canonical) {
    this.id = canonical.id;
    this.eventChannelId = canonical.eventChannelId;
    this.workspaceId = canonical.workspaceId;
    this.applyToAllWorkspaces = canonical.applyToAllWorkspaces;
    this.name = canonical.name;
    this.subject = canonical.subject;
    this.message = canonical.message;
    this.messageOnly = canonical.messageOnly;
    this.enabled = canonical.enabled;
    this.eventTypes = canonical.eventTypes;
    this.filter = canonical.filter;
    this.deliveryPolicy = canonical.deliveryPolicy;
    this.eventTargetIds = canonical.eventTargetIds;
    this.createdAt = canonical.createdAt;
    this.updatedAt = canonical.updatedAt;
  }

  /**
   * List Event Subscriptions
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<EventSubscription> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Event Subscriptions
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<EventSubscription> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Event Subscriptions
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `name`, `enabled`, `event_channel_id` or `workspace_id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `enabled`, `event_channel_id` or `workspace_id`. Valid field combinations are `[ enabled, event_channel_id ]`, `[ workspace_id, enabled ]`, `[ workspace_id, event_channel_id ]` or `[ workspace_id, enabled, event_channel_id ]`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code EventSubscription} records, in a {@code ListIterator}
   */
  public static ListIterator<EventSubscription> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().eventSubscriptions().list(parameters, options);
  }

  /**
   * List Event Subscriptions
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<EventSubscription> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Event Subscriptions
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<EventSubscription> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Event Subscription
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static EventSubscription find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Event Subscription
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static EventSubscription find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Event Subscription
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static EventSubscription find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Event Subscription
   *
   * @param id Event Subscription ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code EventSubscription}
   */
  public static EventSubscription find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().eventSubscriptions().find(id, parameters, options);
  }

  /**
   * Show Event Subscription
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static EventSubscription get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Event Subscription
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static EventSubscription get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Event Subscription
   *
   * @see #create(HashMap, HashMap)
   */
  public static EventSubscription create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Event Subscription
   *
   * @see #create(HashMap, HashMap)
   */
  public static EventSubscription create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create Event Subscription
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code event_channel_id} (Long): Event Channel ID</li>
   * <li>{@code workspace_id} (Long): Workspace ID. 0 means the default workspace or site-wide.</li>
   * <li>{@code apply_to_all_workspaces} (Boolean): If true, this default-workspace subscription applies to events from all workspaces.</li>
   * <li>{@code name} (String): Required. Event Subscription name.</li>
   * <li>{@code subject} (String): Custom subject line to use for notification emails.</li>
   * <li>{@code message} (String): Custom message to include in notification emails.</li>
   * <li>{@code message_only} (Boolean): If true, notification email bodies contain only the custom message, omitting event details and the review button. Requires a custom message, defaults to false, and does not affect non-email targets.</li>
   * <li>{@code enabled} (Boolean): Whether this Event Subscription can dispatch events.</li>
   * <li>{@code event_types} (String[]): Event type strings matched by this subscription. Blank means all event types. Valid values: `automation_run.canceled.v1`, `automation_run.failure.v1`, `automation_run.failure_will_retry.v1`, `automation_run.partial_failure.v1`, `automation_run.partial_failure_will_retry.v1`, `automation_run.skipped.v1`, `automation_run.success.v1`, `expectation_evaluation.invalid.v1`, `expectation_evaluation.late.v1`, `expectation_evaluation.missing.v1`, `expectation_evaluation.success.v1`, `expectation_incident.acknowledged.v1`, `expectation_incident.open.v1`, `expectation_incident.resolved.v1`, `expectation_incident.snoozed.v1`, `external_event.client_log.failure.v1`, `external_event.client_log.partial_failure.v1`, `external_event.client_log.skipped.v1`, `external_event.client_log.success.v1`, `pending_work_event.failure.v1`, `pending_work_event.partial_failure.v1`, `pending_work_event.skipped.v1`, `pending_work_event.success.v1`, `siem_http_destination_event.failure.v1`, `siem_http_destination_event.partial_failure.v1`, `siem_http_destination_event.skipped.v1`, `siem_http_destination_event.success.v1`, `sso_event.ldap_login.failure.v1`, `sso_event.ldap_login.partial_failure.v1`, `sso_event.ldap_login.skipped.v1`, `sso_event.ldap_login.success.v1`, `sso_event.ldap_sync.failure.v1`, `sso_event.ldap_sync.partial_failure.v1`, `sso_event.ldap_sync.skipped.v1`, `sso_event.ldap_sync.success.v1`, `sso_event.saml_login.failure.v1`, `sso_event.saml_login.partial_failure.v1`, `sso_event.saml_login.skipped.v1`, `sso_event.saml_login.success.v1`, `sync_run.failure.v1`, `sync_run.partial_failure.v1`, `sync_run.skipped.v1`, `sync_run.success.v1`, `user_security_event.lockout.v1`</li>
   * <li>{@code filter} (Object): Conditions on event fields that an event must meet to be delivered, keyed by dot-notation field path. All conditions must match.</li>
   * <li>{@code delivery_policy} (Object): Event Subscription delivery policy. Overrides retry settings from each Event Target's delivery policy.</li>
   * <li>{@code event_target_ids} (Long[]): Event Target IDs this subscription sends to.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code EventSubscription}
   */
  public static EventSubscription create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().eventSubscriptions().create(parameters, options);
  }


  /**
   * Update Event Subscription
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static EventSubscription update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Event Subscription
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static EventSubscription update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Event Subscription
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static EventSubscription update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Event Subscription
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code event_channel_id} (Long): Event Channel ID</li>
   * <li>{@code workspace_id} (Long): Workspace ID. 0 means the default workspace or site-wide.</li>
   * <li>{@code apply_to_all_workspaces} (Boolean): If true, this default-workspace subscription applies to events from all workspaces.</li>
   * <li>{@code name} (String): Event Subscription name.</li>
   * <li>{@code subject} (String): Custom subject line to use for notification emails.</li>
   * <li>{@code message} (String): Custom message to include in notification emails.</li>
   * <li>{@code message_only} (Boolean): If true, notification email bodies contain only the custom message, omitting event details and the review button. Requires a custom message, defaults to false, and does not affect non-email targets.</li>
   * <li>{@code enabled} (Boolean): Whether this Event Subscription can dispatch events.</li>
   * <li>{@code event_types} (String[]): Event type strings matched by this subscription. Blank means all event types. Valid values: `automation_run.canceled.v1`, `automation_run.failure.v1`, `automation_run.failure_will_retry.v1`, `automation_run.partial_failure.v1`, `automation_run.partial_failure_will_retry.v1`, `automation_run.skipped.v1`, `automation_run.success.v1`, `expectation_evaluation.invalid.v1`, `expectation_evaluation.late.v1`, `expectation_evaluation.missing.v1`, `expectation_evaluation.success.v1`, `expectation_incident.acknowledged.v1`, `expectation_incident.open.v1`, `expectation_incident.resolved.v1`, `expectation_incident.snoozed.v1`, `external_event.client_log.failure.v1`, `external_event.client_log.partial_failure.v1`, `external_event.client_log.skipped.v1`, `external_event.client_log.success.v1`, `pending_work_event.failure.v1`, `pending_work_event.partial_failure.v1`, `pending_work_event.skipped.v1`, `pending_work_event.success.v1`, `siem_http_destination_event.failure.v1`, `siem_http_destination_event.partial_failure.v1`, `siem_http_destination_event.skipped.v1`, `siem_http_destination_event.success.v1`, `sso_event.ldap_login.failure.v1`, `sso_event.ldap_login.partial_failure.v1`, `sso_event.ldap_login.skipped.v1`, `sso_event.ldap_login.success.v1`, `sso_event.ldap_sync.failure.v1`, `sso_event.ldap_sync.partial_failure.v1`, `sso_event.ldap_sync.skipped.v1`, `sso_event.ldap_sync.success.v1`, `sso_event.saml_login.failure.v1`, `sso_event.saml_login.partial_failure.v1`, `sso_event.saml_login.skipped.v1`, `sso_event.saml_login.success.v1`, `sync_run.failure.v1`, `sync_run.partial_failure.v1`, `sync_run.skipped.v1`, `sync_run.success.v1`, `user_security_event.lockout.v1`</li>
   * <li>{@code filter} (Object): Conditions on event fields that an event must meet to be delivered, keyed by dot-notation field path. All conditions must match.</li>
   * <li>{@code delivery_policy} (Object): Event Subscription delivery policy. Overrides retry settings from each Event Target's delivery policy.</li>
   * <li>{@code event_target_ids} (Long[]): Event Target IDs this subscription sends to.</li>
   * </ul>
   *
   * @param id Event Subscription ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code EventSubscription}
   */
  public static EventSubscription update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().eventSubscriptions().update(id, parameters, options);
  }


  /**
   * Delete Event Subscription
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Event Subscription
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Event Subscription
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Event Subscription
   *
   * @param id Event Subscription ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().eventSubscriptions().delete(id, parameters, options);
  }

  /**
   * Delete Event Subscription
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Event Subscription
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
