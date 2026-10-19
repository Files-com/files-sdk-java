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
public class Folder implements ModelInterface {
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

  // One mapper for every Folder, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code Folder} with no attributes set.
   */
  public Folder() {
    this(null, null);
  }

  /**
   * Creates a {@code Folder} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public Folder(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code Folder} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public Folder(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * File/Folder path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
   */
  @JsonProperty("path")
  public String path;

  /**
   * Gets {@link #path}.
   *
   * @return the value of {@link #path}
   */
  public String getPath() {
    return path;
  }

  /**
   * Sets {@link #path}.
   *
   * @param path the new value
   */
  public void setPath(String path) {
    this.path = path;
  }

  /**
   * User ID of the User who created the file/folder
   */
  @JsonProperty("created_by_id")
  public Long createdById;

  /**
   * Gets {@link #createdById}.
   *
   * @return the value of {@link #createdById}
   */
  public Long getCreatedById() {
    return createdById;
  }

  /**
   * Sets {@link #createdById}.
   *
   * @param createdById the new value
   */
  public void setCreatedById(Long createdById) {
    this.createdById = createdById;
  }

  /**
   * ID of the API key that created the file/folder
   */
  @JsonProperty("created_by_api_key_id")
  public Long createdByApiKeyId;

  /**
   * Gets {@link #createdByApiKeyId}.
   *
   * @return the value of {@link #createdByApiKeyId}
   */
  public Long getCreatedByApiKeyId() {
    return createdByApiKeyId;
  }

  /**
   * Sets {@link #createdByApiKeyId}.
   *
   * @param createdByApiKeyId the new value
   */
  public void setCreatedByApiKeyId(Long createdByApiKeyId) {
    this.createdByApiKeyId = createdByApiKeyId;
  }

  /**
   * ID of the AS2 Incoming Message that created the file/folder
   */
  @JsonProperty("created_by_as2_incoming_message_id")
  public Long createdByAs2IncomingMessageId;

  /**
   * Gets {@link #createdByAs2IncomingMessageId}.
   *
   * @return the value of {@link #createdByAs2IncomingMessageId}
   */
  public Long getCreatedByAs2IncomingMessageId() {
    return createdByAs2IncomingMessageId;
  }

  /**
   * Sets {@link #createdByAs2IncomingMessageId}.
   *
   * @param createdByAs2IncomingMessageId the new value
   */
  public void setCreatedByAs2IncomingMessageId(Long createdByAs2IncomingMessageId) {
    this.createdByAs2IncomingMessageId = createdByAs2IncomingMessageId;
  }

  /**
   * ID of the Automation that created the file/folder
   */
  @JsonProperty("created_by_automation_id")
  public Long createdByAutomationId;

  /**
   * Gets {@link #createdByAutomationId}.
   *
   * @return the value of {@link #createdByAutomationId}
   */
  public Long getCreatedByAutomationId() {
    return createdByAutomationId;
  }

  /**
   * Sets {@link #createdByAutomationId}.
   *
   * @param createdByAutomationId the new value
   */
  public void setCreatedByAutomationId(Long createdByAutomationId) {
    this.createdByAutomationId = createdByAutomationId;
  }

  /**
   * ID of the Bundle Registration that created the file/folder
   */
  @JsonProperty("created_by_bundle_registration_id")
  public Long createdByBundleRegistrationId;

  /**
   * Gets {@link #createdByBundleRegistrationId}.
   *
   * @return the value of {@link #createdByBundleRegistrationId}
   */
  public Long getCreatedByBundleRegistrationId() {
    return createdByBundleRegistrationId;
  }

  /**
   * Sets {@link #createdByBundleRegistrationId}.
   *
   * @param createdByBundleRegistrationId the new value
   */
  public void setCreatedByBundleRegistrationId(Long createdByBundleRegistrationId) {
    this.createdByBundleRegistrationId = createdByBundleRegistrationId;
  }

  /**
   * ID of the Inbox that created the file/folder
   */
  @JsonProperty("created_by_inbox_id")
  public Long createdByInboxId;

  /**
   * Gets {@link #createdByInboxId}.
   *
   * @return the value of {@link #createdByInboxId}
   */
  public Long getCreatedByInboxId() {
    return createdByInboxId;
  }

  /**
   * Sets {@link #createdByInboxId}.
   *
   * @param createdByInboxId the new value
   */
  public void setCreatedByInboxId(Long createdByInboxId) {
    this.createdByInboxId = createdByInboxId;
  }

  /**
   * ID of the Remote Server that created the file/folder
   */
  @JsonProperty("created_by_remote_server_id")
  public Long createdByRemoteServerId;

  /**
   * Gets {@link #createdByRemoteServerId}.
   *
   * @return the value of {@link #createdByRemoteServerId}
   */
  public Long getCreatedByRemoteServerId() {
    return createdByRemoteServerId;
  }

  /**
   * Sets {@link #createdByRemoteServerId}.
   *
   * @param createdByRemoteServerId the new value
   */
  public void setCreatedByRemoteServerId(Long createdByRemoteServerId) {
    this.createdByRemoteServerId = createdByRemoteServerId;
  }

  /**
   * ID of the Sync that created the file/folder
   */
  @JsonProperty("created_by_sync_id")
  public Long createdBySyncId;

  /**
   * Gets {@link #createdBySyncId}.
   *
   * @return the value of {@link #createdBySyncId}
   */
  public Long getCreatedBySyncId() {
    return createdBySyncId;
  }

  /**
   * Sets {@link #createdBySyncId}.
   *
   * @param createdBySyncId the new value
   */
  public void setCreatedBySyncId(Long createdBySyncId) {
    this.createdBySyncId = createdBySyncId;
  }

  /**
   * Custom metadata map of keys and values. Limited to 32 keys, 256 characters per key and 1024 characters per value.
   */
  @JsonProperty("custom_metadata")
  public Object customMetadata;

  /**
   * Gets {@link #customMetadata}.
   *
   * @return the value of {@link #customMetadata}
   */
  public Object getCustomMetadata() {
    return customMetadata;
  }

  /**
   * Sets {@link #customMetadata}.
   *
   * @param customMetadata the new value
   */
  public void setCustomMetadata(Object customMetadata) {
    this.customMetadata = customMetadata;
  }

  /**
   * File/Folder display name
   */
  @JsonProperty("display_name")
  public String displayName;

  /**
   * Gets {@link #displayName}.
   *
   * @return the value of {@link #displayName}
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * Sets {@link #displayName}.
   *
   * @param displayName the new value
   */
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  /**
   * Type: `directory` or `file`.
   */
  @JsonProperty("type")
  public String type;

  /**
   * Gets {@link #type}.
   *
   * @return the value of {@link #type}
   */
  public String getType() {
    return type;
  }

  /**
   * Sets {@link #type}.
   *
   * @param type the new value
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * File/Folder size
   */
  @JsonProperty("size")
  public Long size;

  /**
   * Gets {@link #size}.
   *
   * @return the value of {@link #size}
   */
  public Long getSize() {
    return size;
  }

  /**
   * Sets {@link #size}.
   *
   * @param size the new value
   */
  public void setSize(Long size) {
    this.size = size;
  }

  /**
   * File created date/time
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
   * User ID of the User who last modified the file/folder
   */
  @JsonProperty("last_modified_by_id")
  public Long lastModifiedById;

  /**
   * Gets {@link #lastModifiedById}.
   *
   * @return the value of {@link #lastModifiedById}
   */
  public Long getLastModifiedById() {
    return lastModifiedById;
  }

  /**
   * Sets {@link #lastModifiedById}.
   *
   * @param lastModifiedById the new value
   */
  public void setLastModifiedById(Long lastModifiedById) {
    this.lastModifiedById = lastModifiedById;
  }

  /**
   * ID of the API key that last modified the file/folder
   */
  @JsonProperty("last_modified_by_api_key_id")
  public Long lastModifiedByApiKeyId;

  /**
   * Gets {@link #lastModifiedByApiKeyId}.
   *
   * @return the value of {@link #lastModifiedByApiKeyId}
   */
  public Long getLastModifiedByApiKeyId() {
    return lastModifiedByApiKeyId;
  }

  /**
   * Sets {@link #lastModifiedByApiKeyId}.
   *
   * @param lastModifiedByApiKeyId the new value
   */
  public void setLastModifiedByApiKeyId(Long lastModifiedByApiKeyId) {
    this.lastModifiedByApiKeyId = lastModifiedByApiKeyId;
  }

  /**
   * ID of the Automation that last modified the file/folder
   */
  @JsonProperty("last_modified_by_automation_id")
  public Long lastModifiedByAutomationId;

  /**
   * Gets {@link #lastModifiedByAutomationId}.
   *
   * @return the value of {@link #lastModifiedByAutomationId}
   */
  public Long getLastModifiedByAutomationId() {
    return lastModifiedByAutomationId;
  }

  /**
   * Sets {@link #lastModifiedByAutomationId}.
   *
   * @param lastModifiedByAutomationId the new value
   */
  public void setLastModifiedByAutomationId(Long lastModifiedByAutomationId) {
    this.lastModifiedByAutomationId = lastModifiedByAutomationId;
  }

  /**
   * ID of the Bundle Registration that last modified the file/folder
   */
  @JsonProperty("last_modified_by_bundle_registration_id")
  public Long lastModifiedByBundleRegistrationId;

  /**
   * Gets {@link #lastModifiedByBundleRegistrationId}.
   *
   * @return the value of {@link #lastModifiedByBundleRegistrationId}
   */
  public Long getLastModifiedByBundleRegistrationId() {
    return lastModifiedByBundleRegistrationId;
  }

  /**
   * Sets {@link #lastModifiedByBundleRegistrationId}.
   *
   * @param lastModifiedByBundleRegistrationId the new value
   */
  public void setLastModifiedByBundleRegistrationId(Long lastModifiedByBundleRegistrationId) {
    this.lastModifiedByBundleRegistrationId = lastModifiedByBundleRegistrationId;
  }

  /**
   * ID of the Remote Server that last modified the file/folder
   */
  @JsonProperty("last_modified_by_remote_server_id")
  public Long lastModifiedByRemoteServerId;

  /**
   * Gets {@link #lastModifiedByRemoteServerId}.
   *
   * @return the value of {@link #lastModifiedByRemoteServerId}
   */
  public Long getLastModifiedByRemoteServerId() {
    return lastModifiedByRemoteServerId;
  }

  /**
   * Sets {@link #lastModifiedByRemoteServerId}.
   *
   * @param lastModifiedByRemoteServerId the new value
   */
  public void setLastModifiedByRemoteServerId(Long lastModifiedByRemoteServerId) {
    this.lastModifiedByRemoteServerId = lastModifiedByRemoteServerId;
  }

  /**
   * ID of the Sync that last modified the file/folder
   */
  @JsonProperty("last_modified_by_sync_id")
  public Long lastModifiedBySyncId;

  /**
   * Gets {@link #lastModifiedBySyncId}.
   *
   * @return the value of {@link #lastModifiedBySyncId}
   */
  public Long getLastModifiedBySyncId() {
    return lastModifiedBySyncId;
  }

  /**
   * Sets {@link #lastModifiedBySyncId}.
   *
   * @param lastModifiedBySyncId the new value
   */
  public void setLastModifiedBySyncId(Long lastModifiedBySyncId) {
    this.lastModifiedBySyncId = lastModifiedBySyncId;
  }

  /**
   * File last modified date/time, according to the server.  This is the timestamp of the last Files.com operation of the file, regardless of what modified timestamp was sent.
   */
  @JsonProperty("mtime")
  public Date mtime;

  /**
   * Gets {@link #mtime}.
   *
   * @return the value of {@link #mtime}
   */
  public Date getMtime() {
    return mtime;
  }

  /**
   * Sets {@link #mtime}.
   *
   * @param mtime the new value
   */
  public void setMtime(Date mtime) {
    this.mtime = mtime;
  }

  /**
   * File last modified date/time, according to the client who set it.  Files.com allows desktop, FTP, SFTP, and WebDAV clients to set modified at times.  This allows Desktop&lt;-&gt;Cloud syncing to preserve modified at times.
   */
  @JsonProperty("provided_mtime")
  public Date providedMtime;

  /**
   * Gets {@link #providedMtime}.
   *
   * @return the value of {@link #providedMtime}
   */
  public Date getProvidedMtime() {
    return providedMtime;
  }

  /**
   * Sets {@link #providedMtime}.
   *
   * @param providedMtime the new value
   */
  public void setProvidedMtime(Date providedMtime) {
    this.providedMtime = providedMtime;
  }

  /**
   * File CRC32 checksum. This is sometimes delayed, so if you get a blank response, wait and try again.
   */
  @JsonProperty("crc32")
  public String crc32;

  /**
   * Gets {@link #crc32}.
   *
   * @return the value of {@link #crc32}
   */
  public String getCrc32() {
    return crc32;
  }

  /**
   * Sets {@link #crc32}.
   *
   * @param crc32 the new value
   */
  public void setCrc32(String crc32) {
    this.crc32 = crc32;
  }

  /**
   * File MD5 checksum. This is sometimes delayed, so if you get a blank response, wait and try again.
   */
  @JsonProperty("md5")
  public String md5;

  /**
   * Gets {@link #md5}.
   *
   * @return the value of {@link #md5}
   */
  public String getMd5() {
    return md5;
  }

  /**
   * Sets {@link #md5}.
   *
   * @param md5 the new value
   */
  public void setMd5(String md5) {
    this.md5 = md5;
  }

  /**
   * File SHA1 checksum. This is sometimes delayed, so if you get a blank response, wait and try again.
   */
  @JsonProperty("sha1")
  public String sha1;

  /**
   * Gets {@link #sha1}.
   *
   * @return the value of {@link #sha1}
   */
  public String getSha1() {
    return sha1;
  }

  /**
   * Sets {@link #sha1}.
   *
   * @param sha1 the new value
   */
  public void setSha1(String sha1) {
    this.sha1 = sha1;
  }

  /**
   * File SHA256 checksum. This is sometimes delayed, so if you get a blank response, wait and try again.
   */
  @JsonProperty("sha256")
  public String sha256;

  /**
   * Gets {@link #sha256}.
   *
   * @return the value of {@link #sha256}
   */
  public String getSha256() {
    return sha256;
  }

  /**
   * Sets {@link #sha256}.
   *
   * @param sha256 the new value
   */
  public void setSha256(String sha256) {
    this.sha256 = sha256;
  }

  /**
   * MIME Type.  This is determined by the filename extension and is not stored separately internally.
   */
  @JsonProperty("mime_type")
  public String mimeType;

  /**
   * Gets {@link #mimeType}.
   *
   * @return the value of {@link #mimeType}
   */
  public String getMimeType() {
    return mimeType;
  }

  /**
   * Sets {@link #mimeType}.
   *
   * @param mimeType the new value
   */
  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  /**
   * Region location
   */
  @JsonProperty("region")
  public String region;

  /**
   * Gets {@link #region}.
   *
   * @return the value of {@link #region}
   */
  public String getRegion() {
    return region;
  }

  /**
   * Sets {@link #region}.
   *
   * @param region the new value
   */
  public void setRegion(String region) {
    this.region = region;
  }

  /**
   * A short string representing the current user's permissions.  Can be `r` (Read),`w` (Write),`d` (Delete), `l` (List) or any combination
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
   * Are subfolders locked and unable to be modified?
   */
  @JsonProperty("subfolders_locked?")
  public Boolean subfoldersLocked;

  /**
   * Gets {@link #subfoldersLocked}.
   *
   * @return the value of {@link #subfoldersLocked}
   */
  public Boolean getSubfoldersLocked() {
    return subfoldersLocked;
  }

  /**
   * Sets {@link #subfoldersLocked}.
   *
   * @param subfoldersLocked the new value
   */
  public void setSubfoldersLocked(Boolean subfoldersLocked) {
    this.subfoldersLocked = subfoldersLocked;
  }

  /**
   * Is this folder locked and unable to be modified?
   */
  @JsonProperty("is_locked")
  public Boolean isLocked;

  /**
   * Gets {@link #isLocked}.
   *
   * @return the value of {@link #isLocked}
   */
  public Boolean getIsLocked() {
    return isLocked;
  }

  /**
   * Sets {@link #isLocked}.
   *
   * @param isLocked the new value
   */
  public void setIsLocked(Boolean isLocked) {
    this.isLocked = isLocked;
  }

  /**
   * Link to download file. Provided only in response to a download request.
   */
  @JsonProperty("download_uri")
  public String downloadUri;

  /**
   * Gets {@link #downloadUri}.
   *
   * @return the value of {@link #downloadUri}
   */
  public String getDownloadUri() {
    return downloadUri;
  }

  /**
   * Sets {@link #downloadUri}.
   *
   * @param downloadUri the new value
   */
  public void setDownloadUri(String downloadUri) {
    this.downloadUri = downloadUri;
  }

  /**
   * Optional direct connection information for direct Agent transfer attempts
   */
  @JsonProperty("direct_connection_info")
  public DirectConnectionInfo directConnectionInfo;

  /**
   * Gets {@link #directConnectionInfo}.
   *
   * @return the value of {@link #directConnectionInfo}
   */
  public DirectConnectionInfo getDirectConnectionInfo() {
    return directConnectionInfo;
  }

  /**
   * Sets {@link #directConnectionInfo}.
   *
   * @param directConnectionInfo the new value
   */
  public void setDirectConnectionInfo(DirectConnectionInfo directConnectionInfo) {
    this.directConnectionInfo = directConnectionInfo;
  }

  /**
   * Bookmark/priority color of file/folder
   */
  @JsonProperty("priority_color")
  public String priorityColor;

  /**
   * Gets {@link #priorityColor}.
   *
   * @return the value of {@link #priorityColor}
   */
  public String getPriorityColor() {
    return priorityColor;
  }

  /**
   * Sets {@link #priorityColor}.
   *
   * @param priorityColor the new value
   */
  public void setPriorityColor(String priorityColor) {
    this.priorityColor = priorityColor;
  }

  /**
   * File preview ID
   */
  @JsonProperty("preview_id")
  public Long previewId;

  /**
   * Gets {@link #previewId}.
   *
   * @return the value of {@link #previewId}
   */
  public Long getPreviewId() {
    return previewId;
  }

  /**
   * Sets {@link #previewId}.
   *
   * @param previewId the new value
   */
  public void setPreviewId(Long previewId) {
    this.previewId = previewId;
  }

  /**
   * File preview
   */
  @JsonProperty("preview")
  public Preview preview;

  /**
   * Gets {@link #preview}.
   *
   * @return the value of {@link #preview}
   */
  public Preview getPreview() {
    return preview;
  }

  /**
   * Sets {@link #preview}.
   *
   * @param preview the new value
   */
  public void setPreview(Preview preview) {
    this.preview = preview;
  }

  /**
   * Create parent directories if they do not exist?
   */
  @JsonProperty("mkdir_parents")
  public Boolean mkdirParents;

  /**
   * Gets {@link #mkdirParents}.
   *
   * @return the value of {@link #mkdirParents}
   */
  public Boolean getMkdirParents() {
    return mkdirParents;
  }

  /**
   * Sets {@link #mkdirParents}.
   *
   * @param mkdirParents the new value
   */
  public void setMkdirParents(Boolean mkdirParents) {
    this.mkdirParents = mkdirParents;
  }


  /**
   * Creates this Folder from its current attributes.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    refreshFrom(filesClient().folders().create(parameters, this.options));
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    return ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(Folder canonical) {
    this.path = canonical.path;
    this.createdById = canonical.createdById;
    this.createdByApiKeyId = canonical.createdByApiKeyId;
    this.createdByAs2IncomingMessageId = canonical.createdByAs2IncomingMessageId;
    this.createdByAutomationId = canonical.createdByAutomationId;
    this.createdByBundleRegistrationId = canonical.createdByBundleRegistrationId;
    this.createdByInboxId = canonical.createdByInboxId;
    this.createdByRemoteServerId = canonical.createdByRemoteServerId;
    this.createdBySyncId = canonical.createdBySyncId;
    this.customMetadata = canonical.customMetadata;
    this.displayName = canonical.displayName;
    this.type = canonical.type;
    this.size = canonical.size;
    this.createdAt = canonical.createdAt;
    this.lastModifiedById = canonical.lastModifiedById;
    this.lastModifiedByApiKeyId = canonical.lastModifiedByApiKeyId;
    this.lastModifiedByAutomationId = canonical.lastModifiedByAutomationId;
    this.lastModifiedByBundleRegistrationId = canonical.lastModifiedByBundleRegistrationId;
    this.lastModifiedByRemoteServerId = canonical.lastModifiedByRemoteServerId;
    this.lastModifiedBySyncId = canonical.lastModifiedBySyncId;
    this.mtime = canonical.mtime;
    this.providedMtime = canonical.providedMtime;
    this.crc32 = canonical.crc32;
    this.md5 = canonical.md5;
    this.sha1 = canonical.sha1;
    this.sha256 = canonical.sha256;
    this.mimeType = canonical.mimeType;
    this.region = canonical.region;
    this.permissions = canonical.permissions;
    this.subfoldersLocked = canonical.subfoldersLocked;
    this.isLocked = canonical.isLocked;
    this.downloadUri = canonical.downloadUri;
    this.directConnectionInfo = canonical.directConnectionInfo;
    this.priorityColor = canonical.priorityColor;
    this.previewId = canonical.previewId;
    this.preview = canonical.preview;
    this.mkdirParents = canonical.mkdirParents;
  }

  /**
   * List Folders by Path
   *
   * @see #listFor(String, HashMap, HashMap)
   */
  public static ListIterator<File> listFor() throws RuntimeException {
    return listFor(null, null, null);
  }

  /**
   * List Folders by Path
   *
   * @see #listFor(String, HashMap, HashMap)
   */
  public static ListIterator<File> listFor(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return listFor(path, parameters, null);
  }

  /**
   * List Folders by Path
   *
   * @see #listFor(String, HashMap, HashMap)
   */
  public static ListIterator<File> listFor(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return listFor(null, parameters, options);
  }

  /**
   * List Folders by Path
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header or the X-Files-Cursor-Prev header.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code preview_size} (String): Request a preview size.  Can be `small` (default), `large`, `xlarge`, or `pdf`.</li>
   * <li>{@code sort_by} (Object): Search by field and direction. Valid fields are `path`, `size`, `modified_at_datetime`, `provided_modified_at`.  Valid directions are `asc` and `desc`.  Defaults to `{"path":"asc"}`.</li>
   * <li>{@code search} (String): If specified, will search the folders/files list by name. Ignores text before last `/`. This is the same API used by the search bar in the web UI when running 'Search This Folder'.  Search results are a best effort, not real time, and not guaranteed to perfectly match the latest folder listing.  Results may be truncated if more than 1,000 possible matches exist.  This field should only be used for ad-hoc (human) searching, and not as part of an automated process.</li>
   * <li>{@code search_custom_metadata_key} (String): If provided, the search string in `search` will search for files where this custom metadata key matches the value sent in `search`.  Set this to `*` to allow any metadata key to match the value sent in `search`.</li>
   * <li>{@code search_all} (Boolean): Search entire site?  If true, we will search the entire site.  Do not provide a path when using this parameter.  This is the same API used by the search bar in the web UI when running 'Search All Files'.  Search results are a best effort, not real time, and not guaranteed to match every file.  This field should only be used for ad-hoc (human) searching, and not as part of an automated process.</li>
   * <li>{@code with_previews} (Boolean): Include file previews?</li>
   * <li>{@code with_priority_color} (Boolean): Include file priority color information?</li>
   * <li>{@code type} (String): Type of objects to return.  Can be `folder` or `file`.</li>
   * <li>{@code modified_at_datetime} (String): If provided, will only return files/folders modified after this time. Can be used only in combination with `type` filter.</li>
   * </ul>
   *
   * @param path Path to operate on.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code File} records, in a {@code ListIterator}
   */
  public static ListIterator<File> listFor(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().folders().listFor(path, parameters, options);
  }


  /**
   * Create Folder
   *
   * @see #create(String, HashMap, HashMap)
   */
  public static Folder create() throws RuntimeException {
    return create(null, null, null);
  }

  /**
   * Create Folder
   *
   * @see #create(String, HashMap, HashMap)
   */
  public static Folder create(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return create(path, parameters, null);
  }

  /**
   * Create Folder
   *
   * @see #create(String, HashMap, HashMap)
   */
  public static Folder create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return create(null, parameters, options);
  }

  /**
   * Create Folder
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code mkdir_parents} (Boolean): Create parent directories if they do not exist?</li>
   * <li>{@code provided_mtime} (String): User provided modification time.</li>
   * </ul>
   *
   * @param path Path to operate on.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Folder}
   */
  public static Folder create(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().folders().create(path, parameters, options);
  }


}
