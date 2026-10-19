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
public class Bundle implements ModelInterface {
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

  // One mapper for every Bundle, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code Bundle} with no attributes set.
   */
  public Bundle() {
    this(null, null);
  }

  /**
   * Creates a {@code Bundle} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public Bundle(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code Bundle} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public Bundle(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Bundle code.  This code forms the end part of the Public URL.
   */
  @JsonProperty("code")
  public String code;

  /**
   * Gets {@link #code}.
   *
   * @return the value of {@link #code}
   */
  public String getCode() {
    return code;
  }

  /**
   * Sets {@link #code}.
   *
   * @param code the new value
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * Page link and button color
   */
  @JsonProperty("color_left")
  public String colorLeft;

  /**
   * Gets {@link #colorLeft}.
   *
   * @return the value of {@link #colorLeft}
   */
  public String getColorLeft() {
    return colorLeft;
  }

  /**
   * Sets {@link #colorLeft}.
   *
   * @param colorLeft the new value
   */
  public void setColorLeft(String colorLeft) {
    this.colorLeft = colorLeft;
  }

  /**
   * Top bar link color
   */
  @JsonProperty("color_link")
  public String colorLink;

  /**
   * Gets {@link #colorLink}.
   *
   * @return the value of {@link #colorLink}
   */
  public String getColorLink() {
    return colorLink;
  }

  /**
   * Sets {@link #colorLink}.
   *
   * @param colorLink the new value
   */
  public void setColorLink(String colorLink) {
    this.colorLink = colorLink;
  }

  /**
   * Page link and button color
   */
  @JsonProperty("color_text")
  public String colorText;

  /**
   * Gets {@link #colorText}.
   *
   * @return the value of {@link #colorText}
   */
  public String getColorText() {
    return colorText;
  }

  /**
   * Sets {@link #colorText}.
   *
   * @param colorText the new value
   */
  public void setColorText(String colorText) {
    this.colorText = colorText;
  }

  /**
   * Top bar background color
   */
  @JsonProperty("color_top")
  public String colorTop;

  /**
   * Gets {@link #colorTop}.
   *
   * @return the value of {@link #colorTop}
   */
  public String getColorTop() {
    return colorTop;
  }

  /**
   * Sets {@link #colorTop}.
   *
   * @param colorTop the new value
   */
  public void setColorTop(String colorTop) {
    this.colorTop = colorTop;
  }

  /**
   * Top bar text color
   */
  @JsonProperty("color_top_text")
  public String colorTopText;

  /**
   * Gets {@link #colorTopText}.
   *
   * @return the value of {@link #colorTopText}
   */
  public String getColorTopText() {
    return colorTopText;
  }

  /**
   * Sets {@link #colorTopText}.
   *
   * @param colorTopText the new value
   */
  public void setColorTopText(String colorTopText) {
    this.colorTopText = colorTopText;
  }

  /**
   * Public URL of Share Link
   */
  @JsonProperty("url")
  public String url;

  /**
   * Gets {@link #url}.
   *
   * @return the value of {@link #url}
   */
  public String getUrl() {
    return url;
  }

  /**
   * Sets {@link #url}.
   *
   * @param url the new value
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * Public description
   */
  @JsonProperty("description")
  public String description;

  /**
   * Gets {@link #description}.
   *
   * @return the value of {@link #description}
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets {@link #description}.
   *
   * @param description the new value
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Explicit Bundle expiration date/time. If not set, the site-wide expiration setting may apply.
   */
  @JsonProperty("expires_at")
  public Date expiresAt;

  /**
   * Gets {@link #expiresAt}.
   *
   * @return the value of {@link #expiresAt}
   */
  public Date getExpiresAt() {
    return expiresAt;
  }

  /**
   * Sets {@link #expiresAt}.
   *
   * @param expiresAt the new value
   */
  public void setExpiresAt(Date expiresAt) {
    this.expiresAt = expiresAt;
  }

  /**
   * Is this bundle password protected?
   */
  @JsonProperty("password_protected")
  public Boolean passwordProtected;

  /**
   * Gets {@link #passwordProtected}.
   *
   * @return the value of {@link #passwordProtected}
   */
  public Boolean getPasswordProtected() {
    return passwordProtected;
  }

  /**
   * Sets {@link #passwordProtected}.
   *
   * @param passwordProtected the new value
   */
  public void setPasswordProtected(Boolean passwordProtected) {
    this.passwordProtected = passwordProtected;
  }

  /**
   * Permissions that apply to Folders in this Share Link.
   */
  @JsonProperty("permissions")
  public String permissions;

  /**
   * Gets {@link #permissions}.
   *
   * @return the value of {@link #permissions}
   */
  public String getPermissions() {
    return permissions;
  }

  /**
   * Sets {@link #permissions}.
   *
   * @param permissions the new value
   */
  public void setPermissions(String permissions) {
    this.permissions = permissions;
  }

  /**
   * Deprecated preview-only Share Link setting. It mirrors whether permissions is preview_only; use permissions for the Share Link permission mode.
   */
  @JsonProperty("preview_only")
  public Boolean previewOnly;

  /**
   * Gets {@link #previewOnly}.
   *
   * @return the value of {@link #previewOnly}
   */
  public Boolean getPreviewOnly() {
    return previewOnly;
  }

  /**
   * Sets {@link #previewOnly}.
   *
   * @param previewOnly the new value
   */
  public void setPreviewOnly(Boolean previewOnly) {
    this.previewOnly = previewOnly;
  }

  /**
   * Show a registration page that captures the downloader's name and email address?
   */
  @JsonProperty("require_registration")
  public Boolean requireRegistration;

  /**
   * Gets {@link #requireRegistration}.
   *
   * @return the value of {@link #requireRegistration}
   */
  public Boolean getRequireRegistration() {
    return requireRegistration;
  }

  /**
   * Sets {@link #requireRegistration}.
   *
   * @param requireRegistration the new value
   */
  public void setRequireRegistration(Boolean requireRegistration) {
    this.requireRegistration = requireRegistration;
  }

  /**
   * Only allow access to recipients who have explicitly received the share via an email sent through the Files.com UI?
   */
  @JsonProperty("require_share_recipient")
  public Boolean requireShareRecipient;

  /**
   * Gets {@link #requireShareRecipient}.
   *
   * @return the value of {@link #requireShareRecipient}
   */
  public Boolean getRequireShareRecipient() {
    return requireShareRecipient;
  }

  /**
   * Sets {@link #requireShareRecipient}.
   *
   * @param requireShareRecipient the new value
   */
  public void setRequireShareRecipient(Boolean requireShareRecipient) {
    this.requireShareRecipient = requireShareRecipient;
  }

  /**
   * If true, we will hide the 'Remember Me' box on the Bundle registration page, requiring that the user logout and log back in every time they visit the page.
   */
  @JsonProperty("require_logout")
  public Boolean requireLogout;

  /**
   * Gets {@link #requireLogout}.
   *
   * @return the value of {@link #requireLogout}
   */
  public Boolean getRequireLogout() {
    return requireLogout;
  }

  /**
   * Sets {@link #requireLogout}.
   *
   * @param requireLogout the new value
   */
  public void setRequireLogout(Boolean requireLogout) {
    this.requireLogout = requireLogout;
  }

  /**
   * Legal text that must be agreed to prior to accessing Bundle.
   */
  @JsonProperty("clickwrap_body")
  public String clickwrapBody;

  /**
   * Gets {@link #clickwrapBody}.
   *
   * @return the value of {@link #clickwrapBody}
   */
  public String getClickwrapBody() {
    return clickwrapBody;
  }

  /**
   * Sets {@link #clickwrapBody}.
   *
   * @param clickwrapBody the new value
   */
  public void setClickwrapBody(String clickwrapBody) {
    this.clickwrapBody = clickwrapBody;
  }

  /**
   * Custom Form to use
   */
  @JsonProperty("form_field_set")
  public FormFieldSet formFieldSet;

  /**
   * Gets {@link #formFieldSet}.
   *
   * @return the value of {@link #formFieldSet}
   */
  public FormFieldSet getFormFieldSet() {
    return formFieldSet;
  }

  /**
   * Sets {@link #formFieldSet}.
   *
   * @param formFieldSet the new value
   */
  public void setFormFieldSet(FormFieldSet formFieldSet) {
    this.formFieldSet = formFieldSet;
  }

  /**
   * BundleRegistrations can be saved without providing name?
   */
  @JsonProperty("skip_name")
  public Boolean skipName;

  /**
   * Gets {@link #skipName}.
   *
   * @return the value of {@link #skipName}
   */
  public Boolean getSkipName() {
    return skipName;
  }

  /**
   * Sets {@link #skipName}.
   *
   * @param skipName the new value
   */
  public void setSkipName(Boolean skipName) {
    this.skipName = skipName;
  }

  /**
   * BundleRegistrations can be saved without providing email?
   */
  @JsonProperty("skip_email")
  public Boolean skipEmail;

  /**
   * Gets {@link #skipEmail}.
   *
   * @return the value of {@link #skipEmail}
   */
  public Boolean getSkipEmail() {
    return skipEmail;
  }

  /**
   * Sets {@link #skipEmail}.
   *
   * @param skipEmail the new value
   */
  public void setSkipEmail(Boolean skipEmail) {
    this.skipEmail = skipEmail;
  }

  /**
   * Date when share will start to be accessible. If `nil` access granted right after create.
   */
  @JsonProperty("start_access_on_date")
  public Date startAccessOnDate;

  /**
   * Gets {@link #startAccessOnDate}.
   *
   * @return the value of {@link #startAccessOnDate}
   */
  public Date getStartAccessOnDate() {
    return startAccessOnDate;
  }

  /**
   * Sets {@link #startAccessOnDate}.
   *
   * @param startAccessOnDate the new value
   */
  public void setStartAccessOnDate(Date startAccessOnDate) {
    this.startAccessOnDate = startAccessOnDate;
  }

  /**
   * BundleRegistrations can be saved without providing company?
   */
  @JsonProperty("skip_company")
  public Boolean skipCompany;

  /**
   * Gets {@link #skipCompany}.
   *
   * @return the value of {@link #skipCompany}
   */
  public Boolean getSkipCompany() {
    return skipCompany;
  }

  /**
   * Sets {@link #skipCompany}.
   *
   * @param skipCompany the new value
   */
  public void setSkipCompany(Boolean skipCompany) {
    this.skipCompany = skipCompany;
  }

  /**
   * Bundle ID
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
   * If true, this Share Link bypasses site-wide expiration rules. Only site admins may set this.
   */
  @JsonProperty("bypasses_site_expiration_rules")
  public Boolean bypassesSiteExpirationRules;

  /**
   * Gets {@link #bypassesSiteExpirationRules}.
   *
   * @return the value of {@link #bypassesSiteExpirationRules}
   */
  public Boolean getBypassesSiteExpirationRules() {
    return bypassesSiteExpirationRules;
  }

  /**
   * Sets {@link #bypassesSiteExpirationRules}.
   *
   * @param bypassesSiteExpirationRules the new value
   */
  public void setBypassesSiteExpirationRules(Boolean bypassesSiteExpirationRules) {
    this.bypassesSiteExpirationRules = bypassesSiteExpirationRules;
  }

  /**
   * Bundle created at date/time
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
   * Indicates if the bundle has been deleted.
   */
  @JsonProperty("deleted")
  public Boolean deleted;

  /**
   * Gets {@link #deleted}.
   *
   * @return the value of {@link #deleted}
   */
  public Boolean getDeleted() {
    return deleted;
  }

  /**
   * Sets {@link #deleted}.
   *
   * @param deleted the new value
   */
  public void setDeleted(Boolean deleted) {
    this.deleted = deleted;
  }

  /**
   * Bundle deleted at date/time
   */
  @JsonProperty("deleted_at")
  public Date deletedAt;

  /**
   * Gets {@link #deletedAt}.
   *
   * @return the value of {@link #deletedAt}
   */
  public Date getDeletedAt() {
    return deletedAt;
  }

  /**
   * Sets {@link #deletedAt}.
   *
   * @param deletedAt the new value
   */
  public void setDeletedAt(Date deletedAt) {
    this.deletedAt = deletedAt;
  }

  /**
   * Do not create subfolders for files uploaded to this share. Note: there are subtle security pitfalls with allowing anonymous uploads from multiple users to live in the same folder. We strongly discourage use of this option unless absolutely required.
   */
  @JsonProperty("dont_separate_submissions_by_folder")
  public Boolean dontSeparateSubmissionsByFolder;

  /**
   * Gets {@link #dontSeparateSubmissionsByFolder}.
   *
   * @return the value of {@link #dontSeparateSubmissionsByFolder}
   */
  public Boolean getDontSeparateSubmissionsByFolder() {
    return dontSeparateSubmissionsByFolder;
  }

  /**
   * Sets {@link #dontSeparateSubmissionsByFolder}.
   *
   * @param dontSeparateSubmissionsByFolder the new value
   */
  public void setDontSeparateSubmissionsByFolder(Boolean dontSeparateSubmissionsByFolder) {
    this.dontSeparateSubmissionsByFolder = dontSeparateSubmissionsByFolder;
  }

  /**
   * Read-only expiration date/time, using the explicit expiration or the site-wide setting when applicable. Null when the Share Link does not expire.
   */
  @JsonProperty("effective_expires_at")
  public Date effectiveExpiresAt;

  /**
   * Gets {@link #effectiveExpiresAt}.
   *
   * @return the value of {@link #effectiveExpiresAt}
   */
  public Date getEffectiveExpiresAt() {
    return effectiveExpiresAt;
  }

  /**
   * Sets {@link #effectiveExpiresAt}.
   *
   * @param effectiveExpiresAt the new value
   */
  public void setEffectiveExpiresAt(Date effectiveExpiresAt) {
    this.effectiveExpiresAt = effectiveExpiresAt;
  }

  /**
   * Maximum number of times bundle can be accessed
   */
  @JsonProperty("max_uses")
  public Long maxUses;

  /**
   * Gets {@link #maxUses}.
   *
   * @return the value of {@link #maxUses}
   */
  public Long getMaxUses() {
    return maxUses;
  }

  /**
   * Sets {@link #maxUses}.
   *
   * @param maxUses the new value
   */
  public void setMaxUses(Long maxUses) {
    this.maxUses = maxUses;
  }

  /**
   * Internal name for identifying this Share Link.
   */
  @JsonProperty("internal_name")
  public String internalName;

  /**
   * Gets {@link #internalName}.
   *
   * @return the value of {@link #internalName}
   */
  public String getInternalName() {
    return internalName;
  }

  /**
   * Sets {@link #internalName}.
   *
   * @param internalName the new value
   */
  public void setInternalName(String internalName) {
    this.internalName = internalName;
  }

  /**
   * Bundle internal note
   */
  @JsonProperty("note")
  public String note;

  /**
   * Gets {@link #note}.
   *
   * @return the value of {@link #note}
   */
  public String getNote() {
    return note;
  }

  /**
   * Sets {@link #note}.
   *
   * @param note the new value
   */
  public void setNote(String note) {
    this.note = note;
  }

  /**
   * Template for creating submission subfolders. Can use the uploader's name, email address, ip, company, `strftime` directives, and any custom form data.
   */
  @JsonProperty("path_template")
  public String pathTemplate;

  /**
   * Gets {@link #pathTemplate}.
   *
   * @return the value of {@link #pathTemplate}
   */
  public String getPathTemplate() {
    return pathTemplate;
  }

  /**
   * Sets {@link #pathTemplate}.
   *
   * @param pathTemplate the new value
   */
  public void setPathTemplate(String pathTemplate) {
    this.pathTemplate = pathTemplate;
  }

  /**
   * Timezone to use when rendering timestamps in path templates.
   */
  @JsonProperty("path_template_time_zone")
  public String pathTemplateTimeZone;

  /**
   * Gets {@link #pathTemplateTimeZone}.
   *
   * @return the value of {@link #pathTemplateTimeZone}
   */
  public String getPathTemplateTimeZone() {
    return pathTemplateTimeZone;
  }

  /**
   * Sets {@link #pathTemplateTimeZone}.
   *
   * @param pathTemplateTimeZone the new value
   */
  public void setPathTemplateTimeZone(String pathTemplateTimeZone) {
    this.pathTemplateTimeZone = pathTemplateTimeZone;
  }

  /**
   * Send delivery receipt to the uploader. Note: For writable share only
   */
  @JsonProperty("send_email_receipt_to_uploader")
  public Boolean sendEmailReceiptToUploader;

  /**
   * Gets {@link #sendEmailReceiptToUploader}.
   *
   * @return the value of {@link #sendEmailReceiptToUploader}
   */
  public Boolean getSendEmailReceiptToUploader() {
    return sendEmailReceiptToUploader;
  }

  /**
   * Sets {@link #sendEmailReceiptToUploader}.
   *
   * @param sendEmailReceiptToUploader the new value
   */
  public void setSendEmailReceiptToUploader(Boolean sendEmailReceiptToUploader) {
    this.sendEmailReceiptToUploader = sendEmailReceiptToUploader;
  }

  /**
   * ID of the snapshot containing this bundle's contents.
   */
  @JsonProperty("snapshot_id")
  public Long snapshotId;

  /**
   * Gets {@link #snapshotId}.
   *
   * @return the value of {@link #snapshotId}
   */
  public Long getSnapshotId() {
    return snapshotId;
  }

  /**
   * Sets {@link #snapshotId}.
   *
   * @param snapshotId the new value
   */
  public void setSnapshotId(Long snapshotId) {
    this.snapshotId = snapshotId;
  }

  /**
   * Bundle creator user ID
   */
  @JsonProperty("user_id")
  public Long userId;

  /**
   * Gets {@link #userId}.
   *
   * @return the value of {@link #userId}
   */
  public Long getUserId() {
    return userId;
  }

  /**
   * Sets {@link #userId}.
   *
   * @param userId the new value
   */
  public void setUserId(Long userId) {
    this.userId = userId;
  }

  /**
   * Bundle creator username
   */
  @JsonProperty("username")
  public String username;

  /**
   * Gets {@link #username}.
   *
   * @return the value of {@link #username}
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets {@link #username}.
   *
   * @param username the new value
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Owning group ID. If set, members of this group can view, edit, and share this Share Link.
   */
  @JsonProperty("group_id")
  public Long groupId;

  /**
   * Gets {@link #groupId}.
   *
   * @return the value of {@link #groupId}
   */
  public Long getGroupId() {
    return groupId;
  }

  /**
   * Sets {@link #groupId}.
   *
   * @param groupId the new value
   */
  public void setGroupId(Long groupId) {
    this.groupId = groupId;
  }

  /**
   * ID of the clickwrap to use with this bundle.
   */
  @JsonProperty("clickwrap_id")
  public Long clickwrapId;

  /**
   * Gets {@link #clickwrapId}.
   *
   * @return the value of {@link #clickwrapId}
   */
  public Long getClickwrapId() {
    return clickwrapId;
  }

  /**
   * Sets {@link #clickwrapId}.
   *
   * @param clickwrapId the new value
   */
  public void setClickwrapId(Long clickwrapId) {
    this.clickwrapId = clickwrapId;
  }

  /**
   * ID of the associated inbox, if available.
   */
  @JsonProperty("inbox_id")
  public Long inboxId;

  /**
   * Gets {@link #inboxId}.
   *
   * @return the value of {@link #inboxId}
   */
  public Long getInboxId() {
    return inboxId;
  }

  /**
   * Sets {@link #inboxId}.
   *
   * @param inboxId the new value
   */
  public void setInboxId(Long inboxId) {
    this.inboxId = inboxId;
  }

  /**
   * Preview watermark image applied to all bundle items.
   */
  @JsonProperty("watermark_attachment")
  public Image watermarkAttachment;

  /**
   * Gets {@link #watermarkAttachment}.
   *
   * @return the value of {@link #watermarkAttachment}
   */
  public Image getWatermarkAttachment() {
    return watermarkAttachment;
  }

  /**
   * Sets {@link #watermarkAttachment}.
   *
   * @param watermarkAttachment the new value
   */
  public void setWatermarkAttachment(Image watermarkAttachment) {
    this.watermarkAttachment = watermarkAttachment;
  }

  /**
   * Preview watermark settings applied to all bundle items. Uses the same keys as Behavior.value
   */
  @JsonProperty("watermark_value")
  public Object watermarkValue;

  /**
   * Gets {@link #watermarkValue}.
   *
   * @return the value of {@link #watermarkValue}
   */
  public Object getWatermarkValue() {
    return watermarkValue;
  }

  /**
   * Sets {@link #watermarkValue}.
   *
   * @param watermarkValue the new value
   */
  public void setWatermarkValue(Object watermarkValue) {
    this.watermarkValue = watermarkValue;
  }

  /**
   * If true, require_share_recipient bundles will send a one-time password to the recipient when they register. Cannot be enabled if the bundle has a password set.
   */
  @JsonProperty("send_one_time_password_to_recipient_at_registration")
  public Boolean sendOneTimePasswordToRecipientAtRegistration;

  /**
   * Gets {@link #sendOneTimePasswordToRecipientAtRegistration}.
   *
   * @return the value of {@link #sendOneTimePasswordToRecipientAtRegistration}
   */
  public Boolean getSendOneTimePasswordToRecipientAtRegistration() {
    return sendOneTimePasswordToRecipientAtRegistration;
  }

  /**
   * Sets {@link #sendOneTimePasswordToRecipientAtRegistration}.
   *
   * @param sendOneTimePasswordToRecipientAtRegistration the new value
   */
  public void setSendOneTimePasswordToRecipientAtRegistration(Boolean sendOneTimePasswordToRecipientAtRegistration) {
    this.sendOneTimePasswordToRecipientAtRegistration = sendOneTimePasswordToRecipientAtRegistration;
  }

  /**
   * Workspace ID. `0` means the default workspace.
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
   * Does this bundle have an associated inbox?
   */
  @JsonProperty("has_inbox")
  public Boolean hasInbox;

  /**
   * Gets {@link #hasInbox}.
   *
   * @return the value of {@link #hasInbox}
   */
  public Boolean getHasInbox() {
    return hasInbox;
  }

  /**
   * Sets {@link #hasInbox}.
   *
   * @param hasInbox the new value
   */
  public void setHasInbox(Boolean hasInbox) {
    this.hasInbox = hasInbox;
  }

  /**
   * Should folder uploads be prevented?
   */
  @JsonProperty("dont_allow_folders_in_uploads")
  public Boolean dontAllowFoldersInUploads;

  /**
   * Gets {@link #dontAllowFoldersInUploads}.
   *
   * @return the value of {@link #dontAllowFoldersInUploads}
   */
  public Boolean getDontAllowFoldersInUploads() {
    return dontAllowFoldersInUploads;
  }

  /**
   * Sets {@link #dontAllowFoldersInUploads}.
   *
   * @param dontAllowFoldersInUploads the new value
   */
  public void setDontAllowFoldersInUploads(Boolean dontAllowFoldersInUploads) {
    this.dontAllowFoldersInUploads = dontAllowFoldersInUploads;
  }

  /**
   * Upload slots requested by the associated Inbox. Each slot contains a name used as its label and destination subfolder name.
   */
  @JsonProperty("requested_upload_slots")
  public Object[] requestedUploadSlots;

  /**
   * Gets {@link #requestedUploadSlots}.
   *
   * @return the value of {@link #requestedUploadSlots}
   */
  public Object[] getRequestedUploadSlots() {
    return requestedUploadSlots;
  }

  /**
   * Sets {@link #requestedUploadSlots}.
   *
   * @param requestedUploadSlots the new value
   */
  public void setRequestedUploadSlots(Object[] requestedUploadSlots) {
    this.requestedUploadSlots = requestedUploadSlots;
  }

  /**
   * A list of paths in this bundle.  For performance reasons, this is not provided when listing bundles.
   */
  @JsonProperty("paths")
  public String[] paths;

  /**
   * Gets {@link #paths}.
   *
   * @return the value of {@link #paths}
   */
  public String[] getPaths() {
    return paths;
  }

  /**
   * Sets {@link #paths}.
   *
   * @param paths the new value
   */
  public void setPaths(String[] paths) {
    this.paths = paths;
  }

  /**
   * A list of bundlepaths in this bundle.  For performance reasons, this is not provided when listing bundles.
   */
  @JsonProperty("bundlepaths")
  public BundlePath[] bundlepaths;

  /**
   * Gets {@link #bundlepaths}.
   *
   * @return the value of {@link #bundlepaths}
   */
  public BundlePath[] getBundlepaths() {
    return bundlepaths;
  }

  /**
   * Sets {@link #bundlepaths}.
   *
   * @param bundlepaths the new value
   */
  public void setBundlepaths(BundlePath[] bundlepaths) {
    this.bundlepaths = bundlepaths;
  }

  /**
   * Password for this bundle.
   */
  @JsonProperty("password")
  public String password;

  /**
   * Gets {@link #password}.
   *
   * @return the value of {@link #password}
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets {@link #password}.
   *
   * @param password the new value
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Id of Form Field Set to use with this bundle
   */
  @JsonProperty("form_field_set_id")
  public Long formFieldSetId;

  /**
   * Gets {@link #formFieldSetId}.
   *
   * @return the value of {@link #formFieldSetId}
   */
  public Long getFormFieldSetId() {
    return formFieldSetId;
  }

  /**
   * Sets {@link #formFieldSetId}.
   *
   * @param formFieldSetId the new value
   */
  public void setFormFieldSetId(Long formFieldSetId) {
    this.formFieldSetId = formFieldSetId;
  }

  /**
   * If true, create a snapshot of this bundle's contents.
   */
  @JsonProperty("create_snapshot")
  public Boolean createSnapshot;

  /**
   * Gets {@link #createSnapshot}.
   *
   * @return the value of {@link #createSnapshot}
   */
  public Boolean getCreateSnapshot() {
    return createSnapshot;
  }

  /**
   * Sets {@link #createSnapshot}.
   *
   * @param createSnapshot the new value
   */
  public void setCreateSnapshot(Boolean createSnapshot) {
    this.createSnapshot = createSnapshot;
  }

  /**
   * If true, finalize the snapshot of this bundle's contents. Note that `create_snapshot` must also be true.
   */
  @JsonProperty("finalize_snapshot")
  public Boolean finalizeSnapshot;

  /**
   * Gets {@link #finalizeSnapshot}.
   *
   * @return the value of {@link #finalizeSnapshot}
   */
  public Boolean getFinalizeSnapshot() {
    return finalizeSnapshot;
  }

  /**
   * Sets {@link #finalizeSnapshot}.
   *
   * @param finalizeSnapshot the new value
   */
  public void setFinalizeSnapshot(Boolean finalizeSnapshot) {
    this.finalizeSnapshot = finalizeSnapshot;
  }

  /**
   * Preview watermark image applied to all bundle items.
   */
  @JsonProperty("watermark_attachment_file")
  public byte[] watermarkAttachmentFile;

  /**
   * Gets {@link #watermarkAttachmentFile}.
   *
   * @return the value of {@link #watermarkAttachmentFile}
   */
  public byte[] getWatermarkAttachmentFile() {
    return watermarkAttachmentFile;
  }

  /**
   * Sets {@link #watermarkAttachmentFile}.
   *
   * @param watermarkAttachmentFile the new value
   */
  public void setWatermarkAttachmentFile(byte[] watermarkAttachmentFile) {
    this.watermarkAttachmentFile = watermarkAttachmentFile;
  }

  /**
   * If true, will delete the file stored in watermark_attachment
   */
  @JsonProperty("watermark_attachment_delete")
  public Boolean watermarkAttachmentDelete;

  /**
   * Gets {@link #watermarkAttachmentDelete}.
   *
   * @return the value of {@link #watermarkAttachmentDelete}
   */
  public Boolean getWatermarkAttachmentDelete() {
    return watermarkAttachmentDelete;
  }

  /**
   * Sets {@link #watermarkAttachmentDelete}.
   *
   * @param watermarkAttachmentDelete the new value
   */
  public void setWatermarkAttachmentDelete(Boolean watermarkAttachmentDelete) {
    this.watermarkAttachmentDelete = watermarkAttachmentDelete;
  }

  /**
   * Send email(s) with a link to bundle
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code to} (String[]): A list of email addresses to share this bundle with. Required unless `recipients` is used.</li>
   * <li>{@code note} (String): Note to include in email.</li>
   * <li>{@code recipients} (Object[]): A list of recipients to share this bundle with. Required unless `to` is used.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   */
  public void share(HashMap<String, Object> parameters) throws IOException {
    filesClient().bundles().share(this.id, parameters, this.options);
  }

  /**
   * Updates this Bundle. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update Share Link
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code paths} (String[]): A list of paths to include in this bundle.</li>
   * <li>{@code password} (String): Password for this bundle.</li>
   * <li>{@code bypasses_site_expiration_rules} (Boolean): If true, this Share Link bypasses site-wide expiration rules. Only site admins may set this.</li>
   * <li>{@code form_field_set_id} (Long): Id of Form Field Set to use with this bundle</li>
   * <li>{@code clickwrap_id} (Long): ID of the clickwrap to use with this bundle.</li>
   * <li>{@code code} (String): Bundle code.  This code forms the end part of the Public URL.</li>
   * <li>{@code create_snapshot} (Boolean): If true, create a snapshot of this bundle's contents.</li>
   * <li>{@code description} (String): Public description</li>
   * <li>{@code dont_separate_submissions_by_folder} (Boolean): Do not create subfolders for files uploaded to this share. Note: there are subtle security pitfalls with allowing anonymous uploads from multiple users to live in the same folder. We strongly discourage use of this option unless absolutely required.</li>
   * <li>{@code expires_at} (String): Explicit Bundle expiration date/time. If not set, the site-wide expiration setting may apply.</li>
   * <li>{@code finalize_snapshot} (Boolean): If true, finalize the snapshot of this bundle's contents. Note that `create_snapshot` must also be true.</li>
   * <li>{@code inbox_id} (Long): ID of the associated inbox, if available.</li>
   * <li>{@code max_uses} (Long): Maximum number of times bundle can be accessed</li>
   * <li>{@code group_id} (Long): Owning group ID. If set, members of this group can view, edit, and share this Share Link.</li>
   * <li>{@code internal_name} (String): Internal name for identifying this Share Link.</li>
   * <li>{@code note} (String): Bundle internal note</li>
   * <li>{@code path_template} (String): Template for creating submission subfolders. Can use the uploader's name, email address, ip, company, `strftime` directives, and any custom form data.</li>
   * <li>{@code path_template_time_zone} (String): Timezone to use when rendering timestamps in path templates.</li>
   * <li>{@code permissions} (String): Permissions that apply to Folders in this Share Link.</li>
   * <li>{@code require_registration} (Boolean): Show a registration page that captures the downloader's name and email address?</li>
   * <li>{@code require_share_recipient} (Boolean): Only allow access to recipients who have explicitly received the share via an email sent through the Files.com UI?</li>
   * <li>{@code send_one_time_password_to_recipient_at_registration} (Boolean): If true, require_share_recipient bundles will send a one-time password to the recipient when they register. Cannot be enabled if the bundle has a password set.</li>
   * <li>{@code send_email_receipt_to_uploader} (Boolean): Send delivery receipt to the uploader. Note: For writable share only</li>
   * <li>{@code skip_company} (Boolean): BundleRegistrations can be saved without providing company?</li>
   * <li>{@code start_access_on_date} (String): Date when share will start to be accessible. If `nil` access granted right after create.</li>
   * <li>{@code skip_email} (Boolean): BundleRegistrations can be saved without providing email?</li>
   * <li>{@code skip_name} (Boolean): BundleRegistrations can be saved without providing name?</li>
   * <li>{@code user_id} (Long): The owning user id. Only site admins can set this.</li>
   * <li>{@code watermark_attachment_delete} (Boolean): If true, will delete the file stored in watermark_attachment</li>
   * <li>{@code watermark_attachment_file} (byte[] or Attachment): Preview watermark image applied to all bundle items.</li>
   * <li>{@code watermark_value} (Object): Preview watermark settings applied to all bundle items. Uses the same keys as Behavior.value</li>
   * <li>{@code workspace_id} (Long): Workspace ID. `0` means the default workspace.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code Bundle}
   */
  public Bundle update(HashMap<String, Object> parameters) throws IOException {
    Bundle updated = filesClient().bundles().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete Share Link
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().bundles().delete(this.id, parameters, this.options);
  }

  /**
   * Delete Share Link
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this Bundle: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().bundles().create(parameters, this.options));
    }
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
    if (this.paths != null) {
      parameters.put("paths", this.paths);
    }
    if (this.watermarkAttachmentFile != null) {
      parameters.put("watermark_attachment_file", this.watermarkAttachmentFile);
    }
    return parameters;
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(Bundle canonical) {
    this.code = canonical.code;
    this.colorLeft = canonical.colorLeft;
    this.colorLink = canonical.colorLink;
    this.colorText = canonical.colorText;
    this.colorTop = canonical.colorTop;
    this.colorTopText = canonical.colorTopText;
    this.url = canonical.url;
    this.description = canonical.description;
    this.expiresAt = canonical.expiresAt;
    this.passwordProtected = canonical.passwordProtected;
    this.permissions = canonical.permissions;
    this.previewOnly = canonical.previewOnly;
    this.requireRegistration = canonical.requireRegistration;
    this.requireShareRecipient = canonical.requireShareRecipient;
    this.requireLogout = canonical.requireLogout;
    this.clickwrapBody = canonical.clickwrapBody;
    this.formFieldSet = canonical.formFieldSet;
    this.skipName = canonical.skipName;
    this.skipEmail = canonical.skipEmail;
    this.startAccessOnDate = canonical.startAccessOnDate;
    this.skipCompany = canonical.skipCompany;
    this.id = canonical.id;
    this.bypassesSiteExpirationRules = canonical.bypassesSiteExpirationRules;
    this.createdAt = canonical.createdAt;
    this.deleted = canonical.deleted;
    this.deletedAt = canonical.deletedAt;
    this.dontSeparateSubmissionsByFolder = canonical.dontSeparateSubmissionsByFolder;
    this.effectiveExpiresAt = canonical.effectiveExpiresAt;
    this.maxUses = canonical.maxUses;
    this.internalName = canonical.internalName;
    this.note = canonical.note;
    this.pathTemplate = canonical.pathTemplate;
    this.pathTemplateTimeZone = canonical.pathTemplateTimeZone;
    this.sendEmailReceiptToUploader = canonical.sendEmailReceiptToUploader;
    this.snapshotId = canonical.snapshotId;
    this.userId = canonical.userId;
    this.username = canonical.username;
    this.groupId = canonical.groupId;
    this.clickwrapId = canonical.clickwrapId;
    this.inboxId = canonical.inboxId;
    this.watermarkAttachment = canonical.watermarkAttachment;
    this.watermarkValue = canonical.watermarkValue;
    this.sendOneTimePasswordToRecipientAtRegistration = canonical.sendOneTimePasswordToRecipientAtRegistration;
    this.workspaceId = canonical.workspaceId;
    this.hasInbox = canonical.hasInbox;
    this.dontAllowFoldersInUploads = canonical.dontAllowFoldersInUploads;
    this.requestedUploadSlots = canonical.requestedUploadSlots;
    this.paths = canonical.paths;
    this.bundlepaths = canonical.bundlepaths;
    this.password = canonical.password;
    this.formFieldSetId = canonical.formFieldSetId;
    this.createSnapshot = canonical.createSnapshot;
    this.finalizeSnapshot = canonical.finalizeSnapshot;
    this.watermarkAttachmentFile = canonical.watermarkAttachmentFile;
    this.watermarkAttachmentDelete = canonical.watermarkAttachmentDelete;
  }

  /**
   * List Share Links
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Bundle> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Share Links
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Bundle> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Share Links
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `expires_at`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`, `expires_at`, `code`, `group_id`, `user_id` or `bypasses_site_expiration_rules`. Valid field combinations are `[ group_id, expires_at ]` and `[ user_id, expires_at ]`.</li>
   * <li>{@code filter_gt} (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at` and `expires_at`.</li>
   * <li>{@code filter_gteq} (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at` and `expires_at`.</li>
   * <li>{@code filter_prefix} (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `code`.</li>
   * <li>{@code filter_lt} (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `created_at` and `expires_at`.</li>
   * <li>{@code filter_lteq} (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at` and `expires_at`.</li>
   * <li>{@code deleted} (Boolean): If true, only list deleted Share Links.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Bundle} records, in a {@code ListIterator}
   */
  public static ListIterator<Bundle> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().bundles().list(parameters, options);
  }

  /**
   * List Share Links
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Bundle> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Share Links
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Bundle> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Share Link
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Bundle find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Share Link
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Bundle find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Share Link
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Bundle find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Share Link
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code deleted} (Boolean): If true, show a deleted Share Link.</li>
   * </ul>
   *
   * @param id Bundle ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Bundle}
   */
  public static Bundle find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().bundles().find(id, parameters, options);
  }

  /**
   * Show Share Link
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Bundle get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Share Link
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Bundle get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Share Link
   *
   * @see #create(HashMap, HashMap)
   */
  public static Bundle create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Share Link
   *
   * @see #create(HashMap, HashMap)
   */
  public static Bundle create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create Share Link
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code paths} (String[]): Required. A list of paths to include in this bundle.</li>
   * <li>{@code password} (String): Password for this bundle.</li>
   * <li>{@code bypasses_site_expiration_rules} (Boolean): If true, this Share Link bypasses site-wide expiration rules. Only site admins may set this.</li>
   * <li>{@code form_field_set_id} (Long): Id of Form Field Set to use with this bundle</li>
   * <li>{@code create_snapshot} (Boolean): If true, create a snapshot of this bundle's contents.</li>
   * <li>{@code dont_separate_submissions_by_folder} (Boolean): Do not create subfolders for files uploaded to this share. Note: there are subtle security pitfalls with allowing anonymous uploads from multiple users to live in the same folder. We strongly discourage use of this option unless absolutely required.</li>
   * <li>{@code expires_at} (String): Explicit Bundle expiration date/time. If not set, the site-wide expiration setting may apply.</li>
   * <li>{@code finalize_snapshot} (Boolean): If true, finalize the snapshot of this bundle's contents. Note that `create_snapshot` must also be true.</li>
   * <li>{@code max_uses} (Long): Maximum number of times bundle can be accessed</li>
   * <li>{@code group_id} (Long): Owning group ID. If set, members of this group can view, edit, and share this Share Link.</li>
   * <li>{@code internal_name} (String): Internal name for identifying this Share Link.</li>
   * <li>{@code description} (String): Public description</li>
   * <li>{@code note} (String): Bundle internal note</li>
   * <li>{@code code} (String): Bundle code.  This code forms the end part of the Public URL.</li>
   * <li>{@code path_template} (String): Template for creating submission subfolders. Can use the uploader's name, email address, ip, company, `strftime` directives, and any custom form data.</li>
   * <li>{@code path_template_time_zone} (String): Timezone to use when rendering timestamps in path templates.</li>
   * <li>{@code permissions} (String): Permissions that apply to Folders in this Share Link.</li>
   * <li>{@code require_registration} (Boolean): Show a registration page that captures the downloader's name and email address?</li>
   * <li>{@code clickwrap_id} (Long): ID of the clickwrap to use with this bundle.</li>
   * <li>{@code inbox_id} (Long): ID of the associated inbox, if available.</li>
   * <li>{@code require_share_recipient} (Boolean): Only allow access to recipients who have explicitly received the share via an email sent through the Files.com UI?</li>
   * <li>{@code send_one_time_password_to_recipient_at_registration} (Boolean): If true, require_share_recipient bundles will send a one-time password to the recipient when they register. Cannot be enabled if the bundle has a password set.</li>
   * <li>{@code send_email_receipt_to_uploader} (Boolean): Send delivery receipt to the uploader. Note: For writable share only</li>
   * <li>{@code skip_email} (Boolean): BundleRegistrations can be saved without providing email?</li>
   * <li>{@code skip_name} (Boolean): BundleRegistrations can be saved without providing name?</li>
   * <li>{@code skip_company} (Boolean): BundleRegistrations can be saved without providing company?</li>
   * <li>{@code start_access_on_date} (String): Date when share will start to be accessible. If `nil` access granted right after create.</li>
   * <li>{@code snapshot_id} (Long): ID of the snapshot containing this bundle's contents.</li>
   * <li>{@code workspace_id} (Long): Workspace ID. `0` means the default workspace.</li>
   * <li>{@code watermark_attachment_file} (byte[] or Attachment): Preview watermark image applied to all bundle items.</li>
   * <li>{@code watermark_value} (Object): Preview watermark settings applied to all bundle items. Uses the same keys as Behavior.value</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Bundle}
   */
  public static Bundle create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().bundles().create(parameters, options);
  }


  /**
   * Send email(s) with a link to bundle
   *
   * @see #share(Long, HashMap, HashMap)
   */
  public static void share() throws RuntimeException {
    share(null, null, null);
  }

  /**
   * Send email(s) with a link to bundle
   *
   * @see #share(Long, HashMap, HashMap)
   */
  public static void share(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    share(id, parameters, null);
  }

  /**
   * Send email(s) with a link to bundle
   *
   * @see #share(Long, HashMap, HashMap)
   */
  public static void share(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    share(null, parameters, options);
  }

  /**
   * Send email(s) with a link to bundle
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code to} (String[]): A list of email addresses to share this bundle with. Required unless `recipients` is used.</li>
   * <li>{@code note} (String): Note to include in email.</li>
   * <li>{@code recipients} (Object[]): A list of recipients to share this bundle with. Required unless `to` is used.</li>
   * </ul>
   *
   * @param id Bundle ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void share(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().bundles().share(id, parameters, options);
  }


  /**
   * Update Share Link
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static Bundle update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Share Link
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static Bundle update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Share Link
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static Bundle update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Share Link
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code paths} (String[]): A list of paths to include in this bundle.</li>
   * <li>{@code password} (String): Password for this bundle.</li>
   * <li>{@code bypasses_site_expiration_rules} (Boolean): If true, this Share Link bypasses site-wide expiration rules. Only site admins may set this.</li>
   * <li>{@code form_field_set_id} (Long): Id of Form Field Set to use with this bundle</li>
   * <li>{@code clickwrap_id} (Long): ID of the clickwrap to use with this bundle.</li>
   * <li>{@code code} (String): Bundle code.  This code forms the end part of the Public URL.</li>
   * <li>{@code create_snapshot} (Boolean): If true, create a snapshot of this bundle's contents.</li>
   * <li>{@code description} (String): Public description</li>
   * <li>{@code dont_separate_submissions_by_folder} (Boolean): Do not create subfolders for files uploaded to this share. Note: there are subtle security pitfalls with allowing anonymous uploads from multiple users to live in the same folder. We strongly discourage use of this option unless absolutely required.</li>
   * <li>{@code expires_at} (String): Explicit Bundle expiration date/time. If not set, the site-wide expiration setting may apply.</li>
   * <li>{@code finalize_snapshot} (Boolean): If true, finalize the snapshot of this bundle's contents. Note that `create_snapshot` must also be true.</li>
   * <li>{@code inbox_id} (Long): ID of the associated inbox, if available.</li>
   * <li>{@code max_uses} (Long): Maximum number of times bundle can be accessed</li>
   * <li>{@code group_id} (Long): Owning group ID. If set, members of this group can view, edit, and share this Share Link.</li>
   * <li>{@code internal_name} (String): Internal name for identifying this Share Link.</li>
   * <li>{@code note} (String): Bundle internal note</li>
   * <li>{@code path_template} (String): Template for creating submission subfolders. Can use the uploader's name, email address, ip, company, `strftime` directives, and any custom form data.</li>
   * <li>{@code path_template_time_zone} (String): Timezone to use when rendering timestamps in path templates.</li>
   * <li>{@code permissions} (String): Permissions that apply to Folders in this Share Link.</li>
   * <li>{@code require_registration} (Boolean): Show a registration page that captures the downloader's name and email address?</li>
   * <li>{@code require_share_recipient} (Boolean): Only allow access to recipients who have explicitly received the share via an email sent through the Files.com UI?</li>
   * <li>{@code send_one_time_password_to_recipient_at_registration} (Boolean): If true, require_share_recipient bundles will send a one-time password to the recipient when they register. Cannot be enabled if the bundle has a password set.</li>
   * <li>{@code send_email_receipt_to_uploader} (Boolean): Send delivery receipt to the uploader. Note: For writable share only</li>
   * <li>{@code skip_company} (Boolean): BundleRegistrations can be saved without providing company?</li>
   * <li>{@code start_access_on_date} (String): Date when share will start to be accessible. If `nil` access granted right after create.</li>
   * <li>{@code skip_email} (Boolean): BundleRegistrations can be saved without providing email?</li>
   * <li>{@code skip_name} (Boolean): BundleRegistrations can be saved without providing name?</li>
   * <li>{@code user_id} (Long): The owning user id. Only site admins can set this.</li>
   * <li>{@code watermark_attachment_delete} (Boolean): If true, will delete the file stored in watermark_attachment</li>
   * <li>{@code watermark_attachment_file} (byte[] or Attachment): Preview watermark image applied to all bundle items.</li>
   * <li>{@code watermark_value} (Object): Preview watermark settings applied to all bundle items. Uses the same keys as Behavior.value</li>
   * <li>{@code workspace_id} (Long): Workspace ID. `0` means the default workspace.</li>
   * </ul>
   *
   * @param id Bundle ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Bundle}
   */
  public static Bundle update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().bundles().update(id, parameters, options);
  }


  /**
   * Delete Share Link
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Share Link
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Share Link
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Share Link
   *
   * @param id Bundle ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().bundles().delete(id, parameters, options);
  }

  /**
   * Delete Share Link
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Share Link
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
