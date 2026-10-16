package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.Partner;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Partner operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#partners()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.Partner} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class PartnerOperations extends ResourceOperations {
  public PartnerOperations(FilesClient client) {
    super(client);
  }

  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id` and `name`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id`.
   */
  public ListIterator<Partner> list() throws RuntimeException {
    return list(null, null);
  }

  public ListIterator<Partner> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  public ListIterator<Partner> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = "/partners";
    TypeReference<List<Partner>> typeReference = new TypeReference<List<Partner>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public ListIterator<Partner> all() throws RuntimeException {
    return all(null, null);
  }

  public ListIterator<Partner> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Partner ID.
   */
  public Partner find() throws RuntimeException {
    return find(null, null, null);
  }

  public Partner find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public Partner find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public Partner find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/partners/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<Partner> typeReference = new TypeReference<Partner>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public Partner get() throws RuntimeException {
    return get(null, null, null);
  }

  public Partner get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   ai_assistant_personality_id - int64 - AI Assistant Personality ID assigned to this Partner, if any. Users in the Partner inherit it unless a direct per-user assignment overrides it.
   *   allowed_ips - string - A list of allowed IPs for this Partner. Newline delimited. Partner User IP access is allowed when the IP matches the Partner, User, or Site allowed IP lists.
   *   allow_bypassing_2fa_policies - boolean - Allow Partner Admins to change Two-Factor Authentication requirements for Partner Users.
   *   allow_credential_changes - boolean - Allow Partner Admins to change or reset credentials for users belonging to this Partner.
   *   allow_providing_gpg_keys - boolean - Allow Partner Admins to provide GPG keys.
   *   allow_user_creation - boolean - Allow Partner Admins to create users.
   *   cc_emails_to_responsible_party - boolean - When `true`, emails sent to Partner users are copied to the responsible User or Group.
   *   notes - string - Notes about this Partner.
   *   partner_channel_template_id - int64 - ID of the Partner Channel Template assigned to this Partner.
   *   responsible_group_id - int64 - ID of the Group responsible for this Partner.
   *   responsible_user_id - int64 - ID of the User responsible for this Partner.
   *   show_partner_channel_home_page - boolean - Show Partner users a simplified home page built from this Partner's Channels.
   *   tags - string - Comma-separated list of Tags for this Partner. Tags are used for other features, such as UserLifecycleRules, which can target specific tags.  Tags must only contain lowercase letters, numbers, and hyphens.
   *   name (required) - string - The name of the Partner.
   *   root_folder (required) - string - The root folder path for this Partner.
   *   workspace_id - int64 - ID of the Workspace associated with this Partner.
   */
  public Partner create() throws RuntimeException {
    return create(null, null);
  }

  public Partner create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  public Partner create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("name") || parameters.get("name") == null) {
      throw new NullPointerException("Parameter missing: name parameters[\"name\"]");
    }

    if (!parameters.containsKey("root_folder") || parameters.get("root_folder") == null) {
      throw new NullPointerException("Parameter missing: root_folder parameters[\"root_folder\"]");
    }

    if (parameters.containsKey("ai_assistant_personality_id") && !(parameters.get("ai_assistant_personality_id") instanceof Long || parameters.get("ai_assistant_personality_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: ai_assistant_personality_id must be of type Long or Integer parameters[\"ai_assistant_personality_id\"]");
    }

    if (parameters.containsKey("allowed_ips") && !(parameters.get("allowed_ips") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: allowed_ips must be of type String parameters[\"allowed_ips\"]");
    }

    if (parameters.containsKey("allow_bypassing_2fa_policies") && !(parameters.get("allow_bypassing_2fa_policies") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: allow_bypassing_2fa_policies must be of type Boolean parameters[\"allow_bypassing_2fa_policies\"]");
    }

    if (parameters.containsKey("allow_credential_changes") && !(parameters.get("allow_credential_changes") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: allow_credential_changes must be of type Boolean parameters[\"allow_credential_changes\"]");
    }

    if (parameters.containsKey("allow_providing_gpg_keys") && !(parameters.get("allow_providing_gpg_keys") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: allow_providing_gpg_keys must be of type Boolean parameters[\"allow_providing_gpg_keys\"]");
    }

    if (parameters.containsKey("allow_user_creation") && !(parameters.get("allow_user_creation") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: allow_user_creation must be of type Boolean parameters[\"allow_user_creation\"]");
    }

    if (parameters.containsKey("cc_emails_to_responsible_party") && !(parameters.get("cc_emails_to_responsible_party") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: cc_emails_to_responsible_party must be of type Boolean parameters[\"cc_emails_to_responsible_party\"]");
    }

    if (parameters.containsKey("notes") && !(parameters.get("notes") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: notes must be of type String parameters[\"notes\"]");
    }

    if (parameters.containsKey("partner_channel_template_id") && !(parameters.get("partner_channel_template_id") instanceof Long || parameters.get("partner_channel_template_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: partner_channel_template_id must be of type Long or Integer parameters[\"partner_channel_template_id\"]");
    }

    if (parameters.containsKey("responsible_group_id") && !(parameters.get("responsible_group_id") instanceof Long || parameters.get("responsible_group_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: responsible_group_id must be of type Long or Integer parameters[\"responsible_group_id\"]");
    }

    if (parameters.containsKey("responsible_user_id") && !(parameters.get("responsible_user_id") instanceof Long || parameters.get("responsible_user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: responsible_user_id must be of type Long or Integer parameters[\"responsible_user_id\"]");
    }

    if (parameters.containsKey("show_partner_channel_home_page") && !(parameters.get("show_partner_channel_home_page") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: show_partner_channel_home_page must be of type Boolean parameters[\"show_partner_channel_home_page\"]");
    }

    if (parameters.containsKey("tags") && !(parameters.get("tags") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: tags must be of type String parameters[\"tags\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("root_folder") && !(parameters.get("root_folder") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: root_folder must be of type String parameters[\"root_folder\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    String apiPath = "/partners";
    TypeReference<Partner> typeReference = new TypeReference<Partner>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Parameters:
   *   ai_assistant_personality_id - int64 - AI Assistant Personality ID assigned to this Partner, if any. Users in the Partner inherit it unless a direct per-user assignment overrides it.
   *   allowed_ips - string - A list of allowed IPs for this Partner. Newline delimited. Partner User IP access is allowed when the IP matches the Partner, User, or Site allowed IP lists.
   *   allow_bypassing_2fa_policies - boolean - Allow Partner Admins to change Two-Factor Authentication requirements for Partner Users.
   *   allow_credential_changes - boolean - Allow Partner Admins to change or reset credentials for users belonging to this Partner.
   *   allow_providing_gpg_keys - boolean - Allow Partner Admins to provide GPG keys.
   *   allow_user_creation - boolean - Allow Partner Admins to create users.
   *   cc_emails_to_responsible_party - boolean - When `true`, emails sent to Partner users are copied to the responsible User or Group.
   *   notes - string - Notes about this Partner.
   *   partner_channel_template_id - int64 - ID of the Partner Channel Template assigned to this Partner.
   *   responsible_group_id - int64 - ID of the Group responsible for this Partner.
   *   responsible_user_id - int64 - ID of the User responsible for this Partner.
   *   show_partner_channel_home_page - boolean - Show Partner users a simplified home page built from this Partner's Channels.
   *   tags - string - Comma-separated list of Tags for this Partner. Tags are used for other features, such as UserLifecycleRules, which can target specific tags.  Tags must only contain lowercase letters, numbers, and hyphens.
   *   name - string - The name of the Partner.
   *   partnership_role - string - This site's role for this Partner in Connected Sites relationships. `host` is a Partner this site configured. `guest` is a Partner created by approving another site's connection request; it has no root folder and cannot hold users, permissions, or Partner Channels, or host a connection. `host_and_guest` is a configured Partner that is also the guest side of a connection. Promote a `guest` Partner by setting this to `host_and_guest` together with a `root_folder`.
   *   root_folder - string - The root folder path for this Partner.
   */
  public Partner update() throws RuntimeException {
    return update(null, null, null);
  }

  public Partner update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public Partner update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public Partner update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("ai_assistant_personality_id") && !(parameters.get("ai_assistant_personality_id") instanceof Long || parameters.get("ai_assistant_personality_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: ai_assistant_personality_id must be of type Long or Integer parameters[\"ai_assistant_personality_id\"]");
    }

    if (parameters.containsKey("allowed_ips") && !(parameters.get("allowed_ips") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: allowed_ips must be of type String parameters[\"allowed_ips\"]");
    }

    if (parameters.containsKey("allow_bypassing_2fa_policies") && !(parameters.get("allow_bypassing_2fa_policies") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: allow_bypassing_2fa_policies must be of type Boolean parameters[\"allow_bypassing_2fa_policies\"]");
    }

    if (parameters.containsKey("allow_credential_changes") && !(parameters.get("allow_credential_changes") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: allow_credential_changes must be of type Boolean parameters[\"allow_credential_changes\"]");
    }

    if (parameters.containsKey("allow_providing_gpg_keys") && !(parameters.get("allow_providing_gpg_keys") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: allow_providing_gpg_keys must be of type Boolean parameters[\"allow_providing_gpg_keys\"]");
    }

    if (parameters.containsKey("allow_user_creation") && !(parameters.get("allow_user_creation") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: allow_user_creation must be of type Boolean parameters[\"allow_user_creation\"]");
    }

    if (parameters.containsKey("cc_emails_to_responsible_party") && !(parameters.get("cc_emails_to_responsible_party") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: cc_emails_to_responsible_party must be of type Boolean parameters[\"cc_emails_to_responsible_party\"]");
    }

    if (parameters.containsKey("notes") && !(parameters.get("notes") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: notes must be of type String parameters[\"notes\"]");
    }

    if (parameters.containsKey("partner_channel_template_id") && !(parameters.get("partner_channel_template_id") instanceof Long || parameters.get("partner_channel_template_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: partner_channel_template_id must be of type Long or Integer parameters[\"partner_channel_template_id\"]");
    }

    if (parameters.containsKey("responsible_group_id") && !(parameters.get("responsible_group_id") instanceof Long || parameters.get("responsible_group_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: responsible_group_id must be of type Long or Integer parameters[\"responsible_group_id\"]");
    }

    if (parameters.containsKey("responsible_user_id") && !(parameters.get("responsible_user_id") instanceof Long || parameters.get("responsible_user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: responsible_user_id must be of type Long or Integer parameters[\"responsible_user_id\"]");
    }

    if (parameters.containsKey("show_partner_channel_home_page") && !(parameters.get("show_partner_channel_home_page") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: show_partner_channel_home_page must be of type Boolean parameters[\"show_partner_channel_home_page\"]");
    }

    if (parameters.containsKey("tags") && !(parameters.get("tags") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: tags must be of type String parameters[\"tags\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("partnership_role") && !(parameters.get("partnership_role") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: partnership_role must be of type String parameters[\"partnership_role\"]");
    }

    if (parameters.containsKey("root_folder") && !(parameters.get("root_folder") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: root_folder must be of type String parameters[\"root_folder\"]");
    }

    String apiPath = String.format("/partners/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<Partner> typeReference = new TypeReference<Partner>() {};
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

    String apiPath = String.format("/partners/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
