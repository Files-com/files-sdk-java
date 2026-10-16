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


  public Bundle() {
    this(null, null);
  }

  public Bundle(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

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

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  /**
   * Page link and button color
   */
  @JsonProperty("color_left")
  public String colorLeft;

  public String getColorLeft() {
    return colorLeft;
  }

  public void setColorLeft(String colorLeft) {
    this.colorLeft = colorLeft;
  }

  /**
   * Top bar link color
   */
  @JsonProperty("color_link")
  public String colorLink;

  public String getColorLink() {
    return colorLink;
  }

  public void setColorLink(String colorLink) {
    this.colorLink = colorLink;
  }

  /**
   * Page link and button color
   */
  @JsonProperty("color_text")
  public String colorText;

  public String getColorText() {
    return colorText;
  }

  public void setColorText(String colorText) {
    this.colorText = colorText;
  }

  /**
   * Top bar background color
   */
  @JsonProperty("color_top")
  public String colorTop;

  public String getColorTop() {
    return colorTop;
  }

  public void setColorTop(String colorTop) {
    this.colorTop = colorTop;
  }

  /**
   * Top bar text color
   */
  @JsonProperty("color_top_text")
  public String colorTopText;

  public String getColorTopText() {
    return colorTopText;
  }

  public void setColorTopText(String colorTopText) {
    this.colorTopText = colorTopText;
  }

  /**
   * Public URL of Share Link
   */
  @JsonProperty("url")
  public String url;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * Public description
   */
  @JsonProperty("description")
  public String description;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Explicit Bundle expiration date/time. If not set, the site-wide expiration setting may apply.
   */
  @JsonProperty("expires_at")
  public Date expiresAt;

  public Date getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(Date expiresAt) {
    this.expiresAt = expiresAt;
  }

  /**
   * Is this bundle password protected?
   */
  @JsonProperty("password_protected")
  public Boolean passwordProtected;

  public Boolean getPasswordProtected() {
    return passwordProtected;
  }

  public void setPasswordProtected(Boolean passwordProtected) {
    this.passwordProtected = passwordProtected;
  }

  /**
   * Permissions that apply to Folders in this Share Link.
   */
  @JsonProperty("permissions")
  public String permissions;

  public String getPermissions() {
    return permissions;
  }

  public void setPermissions(String permissions) {
    this.permissions = permissions;
  }

  /**
   */
  @JsonProperty("preview_only")
  public Boolean previewOnly;

  public Boolean getPreviewOnly() {
    return previewOnly;
  }

  public void setPreviewOnly(Boolean previewOnly) {
    this.previewOnly = previewOnly;
  }

  /**
   * Show a registration page that captures the downloader's name and email address?
   */
  @JsonProperty("require_registration")
  public Boolean requireRegistration;

  public Boolean getRequireRegistration() {
    return requireRegistration;
  }

  public void setRequireRegistration(Boolean requireRegistration) {
    this.requireRegistration = requireRegistration;
  }

  /**
   * Only allow access to recipients who have explicitly received the share via an email sent through the Files.com UI?
   */
  @JsonProperty("require_share_recipient")
  public Boolean requireShareRecipient;

  public Boolean getRequireShareRecipient() {
    return requireShareRecipient;
  }

  public void setRequireShareRecipient(Boolean requireShareRecipient) {
    this.requireShareRecipient = requireShareRecipient;
  }

  /**
   * If true, we will hide the 'Remember Me' box on the Bundle registration page, requiring that the user logout and log back in every time they visit the page.
   */
  @JsonProperty("require_logout")
  public Boolean requireLogout;

  public Boolean getRequireLogout() {
    return requireLogout;
  }

  public void setRequireLogout(Boolean requireLogout) {
    this.requireLogout = requireLogout;
  }

  /**
   * Legal text that must be agreed to prior to accessing Bundle.
   */
  @JsonProperty("clickwrap_body")
  public String clickwrapBody;

  public String getClickwrapBody() {
    return clickwrapBody;
  }

  public void setClickwrapBody(String clickwrapBody) {
    this.clickwrapBody = clickwrapBody;
  }

  /**
   * Custom Form to use
   */
  @JsonProperty("form_field_set")
  public FormFieldSet formFieldSet;

  public FormFieldSet getFormFieldSet() {
    return formFieldSet;
  }

  public void setFormFieldSet(FormFieldSet formFieldSet) {
    this.formFieldSet = formFieldSet;
  }

  /**
   * BundleRegistrations can be saved without providing name?
   */
  @JsonProperty("skip_name")
  public Boolean skipName;

  public Boolean getSkipName() {
    return skipName;
  }

  public void setSkipName(Boolean skipName) {
    this.skipName = skipName;
  }

  /**
   * BundleRegistrations can be saved without providing email?
   */
  @JsonProperty("skip_email")
  public Boolean skipEmail;

  public Boolean getSkipEmail() {
    return skipEmail;
  }

  public void setSkipEmail(Boolean skipEmail) {
    this.skipEmail = skipEmail;
  }

  /**
   * Date when share will start to be accessible. If `nil` access granted right after create.
   */
  @JsonProperty("start_access_on_date")
  public Date startAccessOnDate;

  public Date getStartAccessOnDate() {
    return startAccessOnDate;
  }

  public void setStartAccessOnDate(Date startAccessOnDate) {
    this.startAccessOnDate = startAccessOnDate;
  }

  /**
   * BundleRegistrations can be saved without providing company?
   */
  @JsonProperty("skip_company")
  public Boolean skipCompany;

  public Boolean getSkipCompany() {
    return skipCompany;
  }

  public void setSkipCompany(Boolean skipCompany) {
    this.skipCompany = skipCompany;
  }

  /**
   * Bundle ID
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
   * If true, this Share Link bypasses site-wide expiration rules. Only site admins may set this.
   */
  @JsonProperty("bypasses_site_expiration_rules")
  public Boolean bypassesSiteExpirationRules;

  public Boolean getBypassesSiteExpirationRules() {
    return bypassesSiteExpirationRules;
  }

  public void setBypassesSiteExpirationRules(Boolean bypassesSiteExpirationRules) {
    this.bypassesSiteExpirationRules = bypassesSiteExpirationRules;
  }

  /**
   * Bundle created at date/time
   */
  @JsonProperty("created_at")
  public Date createdAt;

  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * Indicates if the bundle has been deleted.
   */
  @JsonProperty("deleted")
  public Boolean deleted;

  public Boolean getDeleted() {
    return deleted;
  }

  public void setDeleted(Boolean deleted) {
    this.deleted = deleted;
  }

  /**
   * Bundle deleted at date/time
   */
  @JsonProperty("deleted_at")
  public Date deletedAt;

  public Date getDeletedAt() {
    return deletedAt;
  }

  public void setDeletedAt(Date deletedAt) {
    this.deletedAt = deletedAt;
  }

  /**
   * Do not create subfolders for files uploaded to this share. Note: there are subtle security pitfalls with allowing anonymous uploads from multiple users to live in the same folder. We strongly discourage use of this option unless absolutely required.
   */
  @JsonProperty("dont_separate_submissions_by_folder")
  public Boolean dontSeparateSubmissionsByFolder;

  public Boolean getDontSeparateSubmissionsByFolder() {
    return dontSeparateSubmissionsByFolder;
  }

  public void setDontSeparateSubmissionsByFolder(Boolean dontSeparateSubmissionsByFolder) {
    this.dontSeparateSubmissionsByFolder = dontSeparateSubmissionsByFolder;
  }

  /**
   * Read-only expiration date/time, using the explicit expiration or the site-wide setting when applicable. Null when the Share Link does not expire.
   */
  @JsonProperty("effective_expires_at")
  public Date effectiveExpiresAt;

  public Date getEffectiveExpiresAt() {
    return effectiveExpiresAt;
  }

  public void setEffectiveExpiresAt(Date effectiveExpiresAt) {
    this.effectiveExpiresAt = effectiveExpiresAt;
  }

  /**
   * Maximum number of times bundle can be accessed
   */
  @JsonProperty("max_uses")
  public Long maxUses;

  public Long getMaxUses() {
    return maxUses;
  }

  public void setMaxUses(Long maxUses) {
    this.maxUses = maxUses;
  }

  /**
   * Internal name for identifying this Share Link.
   */
  @JsonProperty("internal_name")
  public String internalName;

  public String getInternalName() {
    return internalName;
  }

  public void setInternalName(String internalName) {
    this.internalName = internalName;
  }

  /**
   * Bundle internal note
   */
  @JsonProperty("note")
  public String note;

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  /**
   * Template for creating submission subfolders. Can use the uploader's name, email address, ip, company, `strftime` directives, and any custom form data.
   */
  @JsonProperty("path_template")
  public String pathTemplate;

  public String getPathTemplate() {
    return pathTemplate;
  }

  public void setPathTemplate(String pathTemplate) {
    this.pathTemplate = pathTemplate;
  }

  /**
   * Timezone to use when rendering timestamps in path templates.
   */
  @JsonProperty("path_template_time_zone")
  public String pathTemplateTimeZone;

  public String getPathTemplateTimeZone() {
    return pathTemplateTimeZone;
  }

  public void setPathTemplateTimeZone(String pathTemplateTimeZone) {
    this.pathTemplateTimeZone = pathTemplateTimeZone;
  }

  /**
   * Send delivery receipt to the uploader. Note: For writable share only
   */
  @JsonProperty("send_email_receipt_to_uploader")
  public Boolean sendEmailReceiptToUploader;

  public Boolean getSendEmailReceiptToUploader() {
    return sendEmailReceiptToUploader;
  }

  public void setSendEmailReceiptToUploader(Boolean sendEmailReceiptToUploader) {
    this.sendEmailReceiptToUploader = sendEmailReceiptToUploader;
  }

  /**
   * ID of the snapshot containing this bundle's contents.
   */
  @JsonProperty("snapshot_id")
  public Long snapshotId;

  public Long getSnapshotId() {
    return snapshotId;
  }

  public void setSnapshotId(Long snapshotId) {
    this.snapshotId = snapshotId;
  }

  /**
   * Bundle creator user ID
   */
  @JsonProperty("user_id")
  public Long userId;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  /**
   * Bundle creator username
   */
  @JsonProperty("username")
  public String username;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Owning group ID. If set, members of this group can view, edit, and share this Share Link.
   */
  @JsonProperty("group_id")
  public Long groupId;

  public Long getGroupId() {
    return groupId;
  }

  public void setGroupId(Long groupId) {
    this.groupId = groupId;
  }

  /**
   * ID of the clickwrap to use with this bundle.
   */
  @JsonProperty("clickwrap_id")
  public Long clickwrapId;

  public Long getClickwrapId() {
    return clickwrapId;
  }

  public void setClickwrapId(Long clickwrapId) {
    this.clickwrapId = clickwrapId;
  }

  /**
   * ID of the associated inbox, if available.
   */
  @JsonProperty("inbox_id")
  public Long inboxId;

  public Long getInboxId() {
    return inboxId;
  }

  public void setInboxId(Long inboxId) {
    this.inboxId = inboxId;
  }

  /**
   * Preview watermark image applied to all bundle items.
   */
  @JsonProperty("watermark_attachment")
  public Image watermarkAttachment;

  public Image getWatermarkAttachment() {
    return watermarkAttachment;
  }

  public void setWatermarkAttachment(Image watermarkAttachment) {
    this.watermarkAttachment = watermarkAttachment;
  }

  /**
   * Preview watermark settings applied to all bundle items. Uses the same keys as Behavior.value
   */
  @JsonProperty("watermark_value")
  public Object watermarkValue;

  public Object getWatermarkValue() {
    return watermarkValue;
  }

  public void setWatermarkValue(Object watermarkValue) {
    this.watermarkValue = watermarkValue;
  }

  /**
   * If true, require_share_recipient bundles will send a one-time password to the recipient when they register. Cannot be enabled if the bundle has a password set.
   */
  @JsonProperty("send_one_time_password_to_recipient_at_registration")
  public Boolean sendOneTimePasswordToRecipientAtRegistration;

  public Boolean getSendOneTimePasswordToRecipientAtRegistration() {
    return sendOneTimePasswordToRecipientAtRegistration;
  }

  public void setSendOneTimePasswordToRecipientAtRegistration(Boolean sendOneTimePasswordToRecipientAtRegistration) {
    this.sendOneTimePasswordToRecipientAtRegistration = sendOneTimePasswordToRecipientAtRegistration;
  }

  /**
   * Workspace ID. `0` means the default workspace.
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
   * Does this bundle have an associated inbox?
   */
  @JsonProperty("has_inbox")
  public Boolean hasInbox;

  public Boolean getHasInbox() {
    return hasInbox;
  }

  public void setHasInbox(Boolean hasInbox) {
    this.hasInbox = hasInbox;
  }

  /**
   * Should folder uploads be prevented?
   */
  @JsonProperty("dont_allow_folders_in_uploads")
  public Boolean dontAllowFoldersInUploads;

  public Boolean getDontAllowFoldersInUploads() {
    return dontAllowFoldersInUploads;
  }

  public void setDontAllowFoldersInUploads(Boolean dontAllowFoldersInUploads) {
    this.dontAllowFoldersInUploads = dontAllowFoldersInUploads;
  }

  /**
   * Upload slots requested by the associated Inbox. Each slot contains a name used as its label and destination subfolder name.
   */
  @JsonProperty("requested_upload_slots")
  public Object[] requestedUploadSlots;

  public Object[] getRequestedUploadSlots() {
    return requestedUploadSlots;
  }

  public void setRequestedUploadSlots(Object[] requestedUploadSlots) {
    this.requestedUploadSlots = requestedUploadSlots;
  }

  /**
   * A list of paths in this bundle.  For performance reasons, this is not provided when listing bundles.
   */
  @JsonProperty("paths")
  public String[] paths;

  public String[] getPaths() {
    return paths;
  }

  public void setPaths(String[] paths) {
    this.paths = paths;
  }

  /**
   * A list of bundlepaths in this bundle.  For performance reasons, this is not provided when listing bundles.
   */
  @JsonProperty("bundlepaths")
  public BundlePath[] bundlepaths;

  public BundlePath[] getBundlepaths() {
    return bundlepaths;
  }

  public void setBundlepaths(BundlePath[] bundlepaths) {
    this.bundlepaths = bundlepaths;
  }

  /**
   * Password for this bundle.
   */
  @JsonProperty("password")
  public String password;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Id of Form Field Set to use with this bundle
   */
  @JsonProperty("form_field_set_id")
  public Long formFieldSetId;

  public Long getFormFieldSetId() {
    return formFieldSetId;
  }

  public void setFormFieldSetId(Long formFieldSetId) {
    this.formFieldSetId = formFieldSetId;
  }

  /**
   * If true, create a snapshot of this bundle's contents.
   */
  @JsonProperty("create_snapshot")
  public Boolean createSnapshot;

  public Boolean getCreateSnapshot() {
    return createSnapshot;
  }

  public void setCreateSnapshot(Boolean createSnapshot) {
    this.createSnapshot = createSnapshot;
  }

  /**
   * If true, finalize the snapshot of this bundle's contents. Note that `create_snapshot` must also be true.
   */
  @JsonProperty("finalize_snapshot")
  public Boolean finalizeSnapshot;

  public Boolean getFinalizeSnapshot() {
    return finalizeSnapshot;
  }

  public void setFinalizeSnapshot(Boolean finalizeSnapshot) {
    this.finalizeSnapshot = finalizeSnapshot;
  }

  /**
   * Preview watermark image applied to all bundle items.
   */
  @JsonProperty("watermark_attachment_file")
  public byte[] watermarkAttachmentFile;

  public byte[] getWatermarkAttachmentFile() {
    return watermarkAttachmentFile;
  }

  public void setWatermarkAttachmentFile(byte[] watermarkAttachmentFile) {
    this.watermarkAttachmentFile = watermarkAttachmentFile;
  }

  /**
   * If true, will delete the file stored in watermark_attachment
   */
  @JsonProperty("watermark_attachment_delete")
  public Boolean watermarkAttachmentDelete;

  public Boolean getWatermarkAttachmentDelete() {
    return watermarkAttachmentDelete;
  }

  public void setWatermarkAttachmentDelete(Boolean watermarkAttachmentDelete) {
    this.watermarkAttachmentDelete = watermarkAttachmentDelete;
  }

  /**
   * Send email(s) with a link to bundle
   *
   * Parameters:
   *   to - array(string) - A list of email addresses to share this bundle with. Required unless `recipients` is used.
   *   note - string - Note to include in email.
   *   recipients - array(object) - A list of recipients to share this bundle with. Required unless `to` is used.
   */
  public void share(HashMap<String, Object> parameters) throws IOException {
    filesClient().bundles().share(this.id, parameters, this.options);
  }

  /**
   * Updates this Bundle. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Parameters:
   *   paths - array(string) - A list of paths to include in this bundle.
   *   password - string - Password for this bundle.
   *   bypasses_site_expiration_rules - boolean - If true, this Share Link bypasses site-wide expiration rules. Only site admins may set this.
   *   form_field_set_id - int64 - Id of Form Field Set to use with this bundle
   *   clickwrap_id - int64 - ID of the clickwrap to use with this bundle.
   *   code - string - Bundle code.  This code forms the end part of the Public URL.
   *   create_snapshot - boolean - If true, create a snapshot of this bundle's contents.
   *   description - string - Public description
   *   dont_separate_submissions_by_folder - boolean - Do not create subfolders for files uploaded to this share. Note: there are subtle security pitfalls with allowing anonymous uploads from multiple users to live in the same folder. We strongly discourage use of this option unless absolutely required.
   *   expires_at - string - Explicit Bundle expiration date/time. If not set, the site-wide expiration setting may apply.
   *   finalize_snapshot - boolean - If true, finalize the snapshot of this bundle's contents. Note that `create_snapshot` must also be true.
   *   inbox_id - int64 - ID of the associated inbox, if available.
   *   max_uses - int64 - Maximum number of times bundle can be accessed
   *   group_id - int64 - Owning group ID. If set, members of this group can view, edit, and share this Share Link.
   *   internal_name - string - Internal name for identifying this Share Link.
   *   note - string - Bundle internal note
   *   path_template - string - Template for creating submission subfolders. Can use the uploader's name, email address, ip, company, `strftime` directives, and any custom form data.
   *   path_template_time_zone - string - Timezone to use when rendering timestamps in path templates.
   *   permissions - string - Permissions that apply to Folders in this Share Link.
   *   require_registration - boolean - Show a registration page that captures the downloader's name and email address?
   *   require_share_recipient - boolean - Only allow access to recipients who have explicitly received the share via an email sent through the Files.com UI?
   *   send_one_time_password_to_recipient_at_registration - boolean - If true, require_share_recipient bundles will send a one-time password to the recipient when they register. Cannot be enabled if the bundle has a password set.
   *   send_email_receipt_to_uploader - boolean - Send delivery receipt to the uploader. Note: For writable share only
   *   skip_company - boolean - BundleRegistrations can be saved without providing company?
   *   start_access_on_date - string - Date when share will start to be accessible. If `nil` access granted right after create.
   *   skip_email - boolean - BundleRegistrations can be saved without providing email?
   *   skip_name - boolean - BundleRegistrations can be saved without providing name?
   *   user_id - int64 - The owning user id. Only site admins can set this.
   *   watermark_attachment_delete - boolean - If true, will delete the file stored in watermark_attachment
   *   watermark_attachment_file - file - Preview watermark image applied to all bundle items.
   *   watermark_value - object - Preview watermark settings applied to all bundle items. Uses the same keys as Behavior.value
   *   workspace_id - int64 - Workspace ID. `0` means the default workspace.
   */
  public Bundle update(HashMap<String, Object> parameters) throws IOException {
    Bundle updated = filesClient().bundles().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().bundles().delete(this.id, parameters, this.options);
  }

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
   * Parameters:
   *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `expires_at`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`, `expires_at`, `code`, `group_id`, `user_id` or `bypasses_site_expiration_rules`. Valid field combinations are `[ group_id, expires_at ]` and `[ user_id, expires_at ]`.
   *   filter_gt - object - If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at` and `expires_at`.
   *   filter_gteq - object - If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at` and `expires_at`.
   *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `code`.
   *   filter_lt - object - If set, return records where the specified field is less than the supplied value. Valid fields are `created_at` and `expires_at`.
   *   filter_lteq - object - If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at` and `expires_at`.
   *   deleted - boolean - If true, only list deleted Share Links.
   */
  public static ListIterator<Bundle> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<Bundle> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<Bundle> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().bundles().list(parameters, options);
  }

  public static ListIterator<Bundle> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<Bundle> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Bundle ID.
   *   deleted - boolean - If true, show a deleted Share Link.
   */
  public static Bundle find() throws RuntimeException {
    return find(null, null, null);
  }

  public static Bundle find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static Bundle find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static Bundle find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().bundles().find(id, parameters, options);
  }

  public static Bundle get() throws RuntimeException {
    return get(null, null, null);
  }

  public static Bundle get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
   *   paths (required) - array(string) - A list of paths to include in this bundle.
   *   password - string - Password for this bundle.
   *   bypasses_site_expiration_rules - boolean - If true, this Share Link bypasses site-wide expiration rules. Only site admins may set this.
   *   form_field_set_id - int64 - Id of Form Field Set to use with this bundle
   *   create_snapshot - boolean - If true, create a snapshot of this bundle's contents.
   *   dont_separate_submissions_by_folder - boolean - Do not create subfolders for files uploaded to this share. Note: there are subtle security pitfalls with allowing anonymous uploads from multiple users to live in the same folder. We strongly discourage use of this option unless absolutely required.
   *   expires_at - string - Explicit Bundle expiration date/time. If not set, the site-wide expiration setting may apply.
   *   finalize_snapshot - boolean - If true, finalize the snapshot of this bundle's contents. Note that `create_snapshot` must also be true.
   *   max_uses - int64 - Maximum number of times bundle can be accessed
   *   group_id - int64 - Owning group ID. If set, members of this group can view, edit, and share this Share Link.
   *   internal_name - string - Internal name for identifying this Share Link.
   *   description - string - Public description
   *   note - string - Bundle internal note
   *   code - string - Bundle code.  This code forms the end part of the Public URL.
   *   path_template - string - Template for creating submission subfolders. Can use the uploader's name, email address, ip, company, `strftime` directives, and any custom form data.
   *   path_template_time_zone - string - Timezone to use when rendering timestamps in path templates.
   *   permissions - string - Permissions that apply to Folders in this Share Link.
   *   require_registration - boolean - Show a registration page that captures the downloader's name and email address?
   *   clickwrap_id - int64 - ID of the clickwrap to use with this bundle.
   *   inbox_id - int64 - ID of the associated inbox, if available.
   *   require_share_recipient - boolean - Only allow access to recipients who have explicitly received the share via an email sent through the Files.com UI?
   *   send_one_time_password_to_recipient_at_registration - boolean - If true, require_share_recipient bundles will send a one-time password to the recipient when they register. Cannot be enabled if the bundle has a password set.
   *   send_email_receipt_to_uploader - boolean - Send delivery receipt to the uploader. Note: For writable share only
   *   skip_email - boolean - BundleRegistrations can be saved without providing email?
   *   skip_name - boolean - BundleRegistrations can be saved without providing name?
   *   skip_company - boolean - BundleRegistrations can be saved without providing company?
   *   start_access_on_date - string - Date when share will start to be accessible. If `nil` access granted right after create.
   *   snapshot_id - int64 - ID of the snapshot containing this bundle's contents.
   *   workspace_id - int64 - Workspace ID. `0` means the default workspace.
   *   watermark_attachment_file - file - Preview watermark image applied to all bundle items.
   *   watermark_value - object - Preview watermark settings applied to all bundle items. Uses the same keys as Behavior.value
   */
  public static Bundle create() throws RuntimeException {
    return create(null, null);
  }

  public static Bundle create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static Bundle create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().bundles().create(parameters, options);
  }


  /**
   * Send email(s) with a link to bundle
   *
   * Parameters:
   *   to - array(string) - A list of email addresses to share this bundle with. Required unless `recipients` is used.
   *   note - string - Note to include in email.
   *   recipients - array(object) - A list of recipients to share this bundle with. Required unless `to` is used.
   */
  public static void share() throws RuntimeException {
    share(null, null, null);
  }

  public static void share(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    share(id, parameters, null);
  }

  public static void share(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    share(null, parameters, options);
  }

  public static void share(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().bundles().share(id, parameters, options);
  }


  /**
   * Parameters:
   *   paths - array(string) - A list of paths to include in this bundle.
   *   password - string - Password for this bundle.
   *   bypasses_site_expiration_rules - boolean - If true, this Share Link bypasses site-wide expiration rules. Only site admins may set this.
   *   form_field_set_id - int64 - Id of Form Field Set to use with this bundle
   *   clickwrap_id - int64 - ID of the clickwrap to use with this bundle.
   *   code - string - Bundle code.  This code forms the end part of the Public URL.
   *   create_snapshot - boolean - If true, create a snapshot of this bundle's contents.
   *   description - string - Public description
   *   dont_separate_submissions_by_folder - boolean - Do not create subfolders for files uploaded to this share. Note: there are subtle security pitfalls with allowing anonymous uploads from multiple users to live in the same folder. We strongly discourage use of this option unless absolutely required.
   *   expires_at - string - Explicit Bundle expiration date/time. If not set, the site-wide expiration setting may apply.
   *   finalize_snapshot - boolean - If true, finalize the snapshot of this bundle's contents. Note that `create_snapshot` must also be true.
   *   inbox_id - int64 - ID of the associated inbox, if available.
   *   max_uses - int64 - Maximum number of times bundle can be accessed
   *   group_id - int64 - Owning group ID. If set, members of this group can view, edit, and share this Share Link.
   *   internal_name - string - Internal name for identifying this Share Link.
   *   note - string - Bundle internal note
   *   path_template - string - Template for creating submission subfolders. Can use the uploader's name, email address, ip, company, `strftime` directives, and any custom form data.
   *   path_template_time_zone - string - Timezone to use when rendering timestamps in path templates.
   *   permissions - string - Permissions that apply to Folders in this Share Link.
   *   require_registration - boolean - Show a registration page that captures the downloader's name and email address?
   *   require_share_recipient - boolean - Only allow access to recipients who have explicitly received the share via an email sent through the Files.com UI?
   *   send_one_time_password_to_recipient_at_registration - boolean - If true, require_share_recipient bundles will send a one-time password to the recipient when they register. Cannot be enabled if the bundle has a password set.
   *   send_email_receipt_to_uploader - boolean - Send delivery receipt to the uploader. Note: For writable share only
   *   skip_company - boolean - BundleRegistrations can be saved without providing company?
   *   start_access_on_date - string - Date when share will start to be accessible. If `nil` access granted right after create.
   *   skip_email - boolean - BundleRegistrations can be saved without providing email?
   *   skip_name - boolean - BundleRegistrations can be saved without providing name?
   *   user_id - int64 - The owning user id. Only site admins can set this.
   *   watermark_attachment_delete - boolean - If true, will delete the file stored in watermark_attachment
   *   watermark_attachment_file - file - Preview watermark image applied to all bundle items.
   *   watermark_value - object - Preview watermark settings applied to all bundle items. Uses the same keys as Behavior.value
   *   workspace_id - int64 - Workspace ID. `0` means the default workspace.
   */
  public static Bundle update() throws RuntimeException {
    return update(null, null, null);
  }

  public static Bundle update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static Bundle update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static Bundle update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().bundles().update(id, parameters, options);
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
    FilesClient.defaultClient().bundles().delete(id, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
