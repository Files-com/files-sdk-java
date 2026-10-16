package com.files.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.ListIterator;
import com.files.net.ClientRuntime;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
import com.files.util.PathUtils;
import com.files.util.UrlUtils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Partner implements ModelInterface {
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

  // One mapper for every Partner, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  public Partner() {
    this(null, null);
  }

  public Partner(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Partner(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Allow Partner Admins to change Two-Factor Authentication requirements for Partner Users.
   */
  @JsonProperty("allow_bypassing_2fa_policies")
  public Boolean allowBypassing2faPolicies;

  public Boolean getAllowBypassing2faPolicies() {
    return allowBypassing2faPolicies;
  }

  public void setAllowBypassing2faPolicies(Boolean allowBypassing2faPolicies) {
    this.allowBypassing2faPolicies = allowBypassing2faPolicies;
  }

  /**
   * A list of allowed IPs for this Partner. Newline delimited. Partner User IP access is allowed when the IP matches the Partner, User, or Site allowed IP lists.
   */
  @JsonProperty("allowed_ips")
  public String allowedIps;

  public String getAllowedIps() {
    return allowedIps;
  }

  public void setAllowedIps(String allowedIps) {
    this.allowedIps = allowedIps;
  }

  /**
   * Allow Partner Admins to change or reset credentials for users belonging to this Partner.
   */
  @JsonProperty("allow_credential_changes")
  public Boolean allowCredentialChanges;

  public Boolean getAllowCredentialChanges() {
    return allowCredentialChanges;
  }

  public void setAllowCredentialChanges(Boolean allowCredentialChanges) {
    this.allowCredentialChanges = allowCredentialChanges;
  }

  /**
   * Allow Partner Admins to provide GPG keys.
   */
  @JsonProperty("allow_providing_gpg_keys")
  public Boolean allowProvidingGpgKeys;

  public Boolean getAllowProvidingGpgKeys() {
    return allowProvidingGpgKeys;
  }

  public void setAllowProvidingGpgKeys(Boolean allowProvidingGpgKeys) {
    this.allowProvidingGpgKeys = allowProvidingGpgKeys;
  }

  /**
   * Allow Partner Admins to create users.
   */
  @JsonProperty("allow_user_creation")
  public Boolean allowUserCreation;

  public Boolean getAllowUserCreation() {
    return allowUserCreation;
  }

  public void setAllowUserCreation(Boolean allowUserCreation) {
    this.allowUserCreation = allowUserCreation;
  }

  /**
   * When `true`, emails sent to Partner users are copied to the responsible User or Group.
   */
  @JsonProperty("cc_emails_to_responsible_party")
  public Boolean ccEmailsToResponsibleParty;

  public Boolean getCcEmailsToResponsibleParty() {
    return ccEmailsToResponsibleParty;
  }

  public void setCcEmailsToResponsibleParty(Boolean ccEmailsToResponsibleParty) {
    this.ccEmailsToResponsibleParty = ccEmailsToResponsibleParty;
  }

  /**
   * Approved Connected Sites relationships for this Partner, in both directions. Empty when this Partner has no connections. Read-only.
   */
  @JsonProperty("connections")
  public PartnerConnection[] connections;

  public PartnerConnection[] getConnections() {
    return connections;
  }

  public void setConnections(PartnerConnection[] connections) {
    this.connections = connections;
  }

  /**
   * The unique ID of the Partner.
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
   * AI Assistant Personality ID assigned to this Partner, if any. Users in the Partner inherit it unless a direct per-user assignment overrides it.
   */
  @JsonProperty("ai_assistant_personality_id")
  public Long aiAssistantPersonalityId;

  public Long getAiAssistantPersonalityId() {
    return aiAssistantPersonalityId;
  }

  public void setAiAssistantPersonalityId(Long aiAssistantPersonalityId) {
    this.aiAssistantPersonalityId = aiAssistantPersonalityId;
  }

  /**
   * ID of the Workspace associated with this Partner.
   */
  @JsonProperty("workspace_id")
  public Long workspaceId;

  public Long getWorkspaceId() {
    return workspaceId;
  }

  public void setWorkspaceId(Long workspaceId) {
    this.workspaceId = workspaceId;
  }

  /**
   * The name of the Partner.
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
   * Notes about this Partner.
   */
  @JsonProperty("notes")
  public String notes;

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  /**
   * Array of User IDs that are Partner Admins for this Partner.
   */
  @JsonProperty("partner_admin_ids")
  public Long[] partnerAdminIds;

  public Long[] getPartnerAdminIds() {
    return partnerAdminIds;
  }

  public void setPartnerAdminIds(Long[] partnerAdminIds) {
    this.partnerAdminIds = partnerAdminIds;
  }

  /**
   * ID of the Partner Channel Template assigned to this Partner.
   */
  @JsonProperty("partner_channel_template_id")
  public Long partnerChannelTemplateId;

  public Long getPartnerChannelTemplateId() {
    return partnerChannelTemplateId;
  }

  public void setPartnerChannelTemplateId(Long partnerChannelTemplateId) {
    this.partnerChannelTemplateId = partnerChannelTemplateId;
  }

  /**
   * This site's role for this Partner in Connected Sites relationships. `host` is a Partner this site configured. `guest` is a Partner created by approving another site's connection request; it has no root folder and cannot hold users, permissions, or Partner Channels, or host a connection. `host_and_guest` is a configured Partner that is also the guest side of a connection. Promote a `guest` Partner by setting this to `host_and_guest` together with a `root_folder`.
   */
  @JsonProperty("partnership_role")
  public String partnershipRole;

  public String getPartnershipRole() {
    return partnershipRole;
  }

  public void setPartnershipRole(String partnershipRole) {
    this.partnershipRole = partnershipRole;
  }

  /**
   * ID of the Group responsible for this Partner.
   */
  @JsonProperty("responsible_group_id")
  public Long responsibleGroupId;

  public Long getResponsibleGroupId() {
    return responsibleGroupId;
  }

  public void setResponsibleGroupId(Long responsibleGroupId) {
    this.responsibleGroupId = responsibleGroupId;
  }

  /**
   * ID of the User responsible for this Partner.
   */
  @JsonProperty("responsible_user_id")
  public Long responsibleUserId;

  public Long getResponsibleUserId() {
    return responsibleUserId;
  }

  public void setResponsibleUserId(Long responsibleUserId) {
    this.responsibleUserId = responsibleUserId;
  }

  /**
   * The root folder path for this Partner.
   */
  @JsonProperty("root_folder")
  public String rootFolder;

  public String getRootFolder() {
    return rootFolder;
  }

  public void setRootFolder(String rootFolder) {
    this.rootFolder = rootFolder;
  }

  /**
   * Show Partner users a simplified home page built from this Partner's Channels.
   */
  @JsonProperty("show_partner_channel_home_page")
  public Boolean showPartnerChannelHomePage;

  public Boolean getShowPartnerChannelHomePage() {
    return showPartnerChannelHomePage;
  }

  public void setShowPartnerChannelHomePage(Boolean showPartnerChannelHomePage) {
    this.showPartnerChannelHomePage = showPartnerChannelHomePage;
  }

  /**
   * Comma-separated list of Tags for this Partner. Tags are used for other features, such as UserLifecycleRules, which can target specific tags.  Tags must only contain lowercase letters, numbers, and hyphens.
   */
  @JsonProperty("tags")
  public String tags;

  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }

  /**
   * Array of User IDs that belong to this Partner.
   */
  @JsonProperty("user_ids")
  public Long[] userIds;

  public Long[] getUserIds() {
    return userIds;
  }

  public void setUserIds(Long[] userIds) {
    this.userIds = userIds;
  }

  /**
   * Updates this Partner. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Parameters:
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
  public Partner update(HashMap<String, Object> parameters) throws IOException {
    Partner updated = filesClient().partners().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().partners().delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this Partner: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().partners().create(parameters, this.options));
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
  private void refreshFrom(Partner canonical) {
    this.allowBypassing2faPolicies = canonical.allowBypassing2faPolicies;
    this.allowedIps = canonical.allowedIps;
    this.allowCredentialChanges = canonical.allowCredentialChanges;
    this.allowProvidingGpgKeys = canonical.allowProvidingGpgKeys;
    this.allowUserCreation = canonical.allowUserCreation;
    this.ccEmailsToResponsibleParty = canonical.ccEmailsToResponsibleParty;
    this.connections = canonical.connections;
    this.id = canonical.id;
    this.aiAssistantPersonalityId = canonical.aiAssistantPersonalityId;
    this.workspaceId = canonical.workspaceId;
    this.name = canonical.name;
    this.notes = canonical.notes;
    this.partnerAdminIds = canonical.partnerAdminIds;
    this.partnerChannelTemplateId = canonical.partnerChannelTemplateId;
    this.partnershipRole = canonical.partnershipRole;
    this.responsibleGroupId = canonical.responsibleGroupId;
    this.responsibleUserId = canonical.responsibleUserId;
    this.rootFolder = canonical.rootFolder;
    this.showPartnerChannelHomePage = canonical.showPartnerChannelHomePage;
    this.tags = canonical.tags;
    this.userIds = canonical.userIds;
  }

  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id` and `name`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id`.
   */
  public static ListIterator<Partner> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<Partner> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<Partner> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().partners().list(parameters, options);
  }

  public static ListIterator<Partner> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<Partner> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Partner ID.
   */
  public static Partner find() throws RuntimeException {
    return find(null, null, null);
  }

  public static Partner find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static Partner find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static Partner find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().partners().find(id, parameters, options);
  }

  public static Partner get() throws RuntimeException {
    return get(null, null, null);
  }

  public static Partner get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public static Partner create() throws RuntimeException {
    return create(null, null);
  }

  public static Partner create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static Partner create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().partners().create(parameters, options);
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
  public static Partner update() throws RuntimeException {
    return update(null, null, null);
  }

  public static Partner update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static Partner update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static Partner update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().partners().update(id, parameters, options);
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
    FilesClient.defaultClient().partners().delete(id, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
