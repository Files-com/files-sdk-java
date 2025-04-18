package com.files.models;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.ListIterator;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class File implements ModelInterface {
  @Setter
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();

  public FilesInputStream getInputStream() throws IOException {
    return getInputStream(0L, 0L);
  }

  public FilesInputStream getInputStream(long start) throws IOException {
    return getInputStream(start, 0L);
  }

  public FilesInputStream getInputStream(long start, long end) throws IOException {
    return FilesClient.getFileInputStream(this.downloadUri, start, end);
  }

  /**
   *  Save the File to disk.
   */
  public void saveAsLocalFile(String path) throws IOException {
    try (FileOutputStream outputStream = new FileOutputStream(path);
        FilesInputStream inputStream = getInputStream()) {
      inputStream.transferTo(outputStream);
    }
  }

  public static File completeUpload(String path, HashMap<String, Object> parameters) throws IOException {
    return completeUpload(path, parameters, null);
  }

  public static File completeUpload(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    if (parameters == null) {
      parameters = new HashMap<String, Object>();
    }
    if (options == null) {
      options = new HashMap<String, Object>();
    }


    if (path != null) {
      parameters.put("path", path);
    }
    if (!parameters.containsKey("path") || parameters.get("path") == null) {
      throw new NullPointerException("Parameter missing: path parameters[\"path\"]");
    }
    if (!parameters.containsKey("action")) {
      parameters.put("action", "put");
    }
    String url = String.format("%s%s/files/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), path);
    TypeReference<File> typeReference = new TypeReference<File>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }




  /**
  * File/Folder path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @Getter
  @Setter
  @JsonProperty("path")
  public String path;

  /**
  * User ID of the User who created the file/folder
  */
  @Getter
  @Setter
  @JsonProperty("created_by_id")
  public Long createdById;

  /**
  * ID of the API key that created the file/folder
  */
  @Getter
  @Setter
  @JsonProperty("created_by_api_key_id")
  public Long createdByApiKeyId;

  /**
  * ID of the AS2 Incoming Message that created the file/folder
  */
  @Getter
  @Setter
  @JsonProperty("created_by_as2_incoming_message_id")
  public Long createdByAs2IncomingMessageId;

  /**
  * ID of the Automation that created the file/folder
  */
  @Getter
  @Setter
  @JsonProperty("created_by_automation_id")
  public Long createdByAutomationId;

  /**
  * ID of the Bundle Registration that created the file/folder
  */
  @Getter
  @Setter
  @JsonProperty("created_by_bundle_registration_id")
  public Long createdByBundleRegistrationId;

  /**
  * ID of the Inbox that created the file/folder
  */
  @Getter
  @Setter
  @JsonProperty("created_by_inbox_id")
  public Long createdByInboxId;

  /**
  * ID of the Remote Server that created the file/folder
  */
  @Getter
  @Setter
  @JsonProperty("created_by_remote_server_id")
  public Long createdByRemoteServerId;

  /**
  * ID of the Remote Server Sync that created the file/folder
  */
  @Getter
  @Setter
  @JsonProperty("created_by_remote_server_sync_id")
  public Long createdByRemoteServerSyncId;

  /**
  * Custom metadata map of keys and values. Limited to 32 keys, 256 characters per key and 1024 characters per value.
  */
  @Getter
  @Setter
  @JsonProperty("custom_metadata")
  public Map<String, String> customMetadata;

  /**
  * File/Folder display name
  */
  @Getter
  @Setter
  @JsonProperty("display_name")
  public String displayName;

  /**
  * Type: `directory` or `file`.
  */
  @Getter
  @Setter
  @JsonProperty("type")
  public String type;

  /**
  * File/Folder size
  */
  @Getter
  @Setter
  @JsonProperty("size")
  public Long size;

  /**
  * File created date/time
  */
  @Getter
  @JsonProperty("created_at")
  public Date createdAt;

  /**
  * User ID of the User who last modified the file/folder
  */
  @Getter
  @Setter
  @JsonProperty("last_modified_by_id")
  public Long lastModifiedById;

  /**
  * ID of the API key that last modified the file/folder
  */
  @Getter
  @Setter
  @JsonProperty("last_modified_by_api_key_id")
  public Long lastModifiedByApiKeyId;

  /**
  * ID of the Automation that last modified the file/folder
  */
  @Getter
  @Setter
  @JsonProperty("last_modified_by_automation_id")
  public Long lastModifiedByAutomationId;

  /**
  * ID of the Bundle Registration that last modified the file/folder
  */
  @Getter
  @Setter
  @JsonProperty("last_modified_by_bundle_registration_id")
  public Long lastModifiedByBundleRegistrationId;

  /**
  * ID of the Remote Server that last modified the file/folder
  */
  @Getter
  @Setter
  @JsonProperty("last_modified_by_remote_server_id")
  public Long lastModifiedByRemoteServerId;

  /**
  * ID of the Remote Server Sync that last modified the file/folder
  */
  @Getter
  @Setter
  @JsonProperty("last_modified_by_remote_server_sync_id")
  public Long lastModifiedByRemoteServerSyncId;

  /**
  * File last modified date/time, according to the server.  This is the timestamp of the last Files.com operation of the file, regardless of what modified timestamp was sent.
  */
  @Getter
  @Setter
  @JsonProperty("mtime")
  public Date mtime;

  /**
  * File last modified date/time, according to the client who set it.  Files.com allows desktop, FTP, SFTP, and WebDAV clients to set modified at times.  This allows Desktop :Cloud syncing to preserve modified at times.
  */
  @Getter
  @Setter
  @JsonProperty("provided_mtime")
  public Date providedMtime;

  /**
  * File CRC32 checksum. This is sometimes delayed, so if you get a blank response, wait and try again.
  */
  @Getter
  @Setter
  @JsonProperty("crc32")
  public String crc32;

  /**
  * File MD5 checksum. This is sometimes delayed, so if you get a blank response, wait and try again.
  */
  @Getter
  @Setter
  @JsonProperty("md5")
  public String md5;

  /**
  * File SHA1 checksum. This is sometimes delayed, so if you get a blank response, wait and try again.
  */
  @Getter
  @Setter
  @JsonProperty("sha1")
  public String sha1;

  /**
  * File SHA256 checksum. This is sometimes delayed, so if you get a blank response, wait and try again.
  */
  @Getter
  @Setter
  @JsonProperty("sha256")
  public String sha256;

  /**
  * MIME Type.  This is determined by the filename extension and is not stored separately internally.
  */
  @Getter
  @Setter
  @JsonProperty("mime_type")
  public String mimeType;

  /**
  * Region location
  */
  @Getter
  @Setter
  @JsonProperty("region")
  public String region;

  /**
  * A short string representing the current user's permissions.  Can be `r` (Read),`w` (Write),`d` (Delete), `l` (List) or any combination
  */
  @Getter
  @Setter
  @JsonProperty("permissions")
  public String permissions;

  /**
  * Are subfolders locked and unable to be modified?
  */
  @Getter
  @Setter
  @JsonProperty("subfolders_locked?")
  public Boolean subfoldersLocked;

  /**
  * Is this folder locked and unable to be modified?
  */
  @Getter
  @Setter
  @JsonProperty("is_locked")
  public Boolean isLocked;

  /**
  * Link to download file. Provided only in response to a download request.
  */
  @Getter
  @Setter
  @JsonProperty("download_uri")
  public String downloadUri;

  /**
  * Bookmark/priority color of file/folder
  */
  @Getter
  @Setter
  @JsonProperty("priority_color")
  public String priorityColor;

  /**
  * File preview ID
  */
  @Getter
  @Setter
  @JsonProperty("preview_id")
  public Long previewId;

  /**
  * File preview
  */
  @Getter
  @Setter
  @JsonProperty("preview")
  public Preview preview;

  /**
  * The action to perform.  Can be `append`, `attachment`, `end`, `upload`, `put`, or may not exist
  */
  @Getter
  @Setter
  @JsonProperty("action")
  public String action;

  /**
  * Length of file.
  */
  @Getter
  @Setter
  @JsonProperty("length")
  public Long length;

  /**
  * Create parent directories if they do not exist?
  */
  @Getter
  @Setter
  @JsonProperty("mkdir_parents")
  public Boolean mkdirParents;

  /**
  * Part if uploading a part.
  */
  @Getter
  @Setter
  @JsonProperty("part")
  public Long part;

  /**
  * How many parts to fetch?
  */
  @Getter
  @Setter
  @JsonProperty("parts")
  public Long parts;

  /**
  */
  @Getter
  @Setter
  @JsonProperty("ref")
  public String ref;

  /**
  * File byte offset to restart from.
  */
  @Getter
  @Setter
  @JsonProperty("restart")
  public Long restart;

  /**
  * If copying folder, copy just the structure?
  */
  @Getter
  @Setter
  @JsonProperty("structure")
  public String structure;

  /**
  * Allow file rename instead of overwrite?
  */
  @Getter
  @Setter
  @JsonProperty("with_rename")
  public Boolean withRename;

  /**
  * Download File
  *
  * Parameters:
  *   action - string - Can be blank, `redirect` or `stat`.  If set to `stat`, we will return file information but without a download URL, and without logging a download.  If set to `redirect` we will serve a 302 redirect directly to the file.  This is used for integrations with Zapier, and is not recommended for most integrations.
  *   preview_size - string - Request a preview size.  Can be `small` (default), `large`, `xlarge`, or `pdf`.
  *   with_previews - boolean - Include file preview information?
  *   with_priority_color - boolean - Include file priority color information?
  */
  public File download(HashMap<String, Object> parameters) throws IOException {
    return File.download(this.path, parameters, this.options);
  }

  /**
  * Parameters:
  *   custom_metadata - object - Custom metadata map of keys and values. Limited to 32 keys, 256 characters per key and 1024 characters per value.
  *   provided_mtime - string - Modified time of file.
  *   priority_color - string - Priority/Bookmark color of file.
  */
  public File update(HashMap<String, Object> parameters) throws IOException {
    return File.update(this.path, parameters, this.options);
  }

  /**
  * Parameters:
  *   recursive - boolean - If true, will recursively delete folders.  Otherwise, will error on non-empty folders.
  */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    File.delete(this.path, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }

  /**
  * Copy File/Folder
  *
  * Parameters:
  *   destination (required) - string - Copy destination path.
  *   structure - boolean - Copy structure only?
  *   overwrite - boolean - Overwrite existing file(s) in the destination?
  */
  public FileAction copy(HashMap<String, Object> parameters) throws IOException {
    return File.copy(this.path, parameters, this.options);
  }

  /**
  * Move File/Folder
  *
  * Parameters:
  *   destination (required) - string - Move destination path.
  *   overwrite - boolean - Overwrite existing file(s) in the destination?
  */
  public FileAction move(HashMap<String, Object> parameters) throws IOException {
    return File.move(this.path, parameters, this.options);
  }

  /**
  * Begin File Upload
  *
  * Parameters:
  *   mkdir_parents - boolean - Create parent directories if they do not exist?
  *   part - int64 - Part if uploading a part.
  *   parts - int64 - How many parts to fetch?
  *   ref - string -
  *   restart - int64 - File byte offset to restart from.
  *   size - int64 - Total bytes of file being uploaded (include bytes being retained if appending/restarting).
  *   with_rename - boolean - Allow file rename instead of overwrite?
  */
  public FileUploadPart beginUpload(HashMap<String, Object> parameters) throws IOException {
    return File.beginUpload(this.path, parameters, this.options);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    File.create(parameters, this.options);
  }

  /**
  * Download File
  *
  * Parameters:
  *   action - string - Can be blank, `redirect` or `stat`.  If set to `stat`, we will return file information but without a download URL, and without logging a download.  If set to `redirect` we will serve a 302 redirect directly to the file.  This is used for integrations with Zapier, and is not recommended for most integrations.
  *   preview_size - string - Request a preview size.  Can be `small` (default), `large`, `xlarge`, or `pdf`.
  *   with_previews - boolean - Include file preview information?
  *   with_priority_color - boolean - Include file priority color information?
  */
  public static File download() throws RuntimeException {
    return download(null, null, null);
  }

  public static File download(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return download(path, parameters, null);
  }

  public static File download(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return download(null, parameters, options);
  }

  public static File download(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }


    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }
    if (parameters.containsKey("action") && !(parameters.get("action") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: action must be of type String parameters[\"action\"]");
    }
    if (parameters.containsKey("preview_size") && !(parameters.get("preview_size") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: preview_size must be of type String parameters[\"preview_size\"]");
    }
    if (parameters.containsKey("with_previews") && !(parameters.get("with_previews") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: with_previews must be of type Boolean parameters[\"with_previews\"]");
    }
    if (parameters.containsKey("with_priority_color") && !(parameters.get("with_priority_color") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: with_priority_color must be of type Boolean parameters[\"with_priority_color\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), path};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/files/%s", urlParts);

    TypeReference<File> typeReference = new TypeReference<File>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   path (required) - string - Path to operate on.
  *   action - string - The action to perform.  Can be `append`, `attachment`, `end`, `upload`, `put`, or may not exist
  *   etags[etag] (required) - array(string) - etag identifier.
  *   etags[part] (required) - array(int64) - Part number.
  *   length - int64 - Length of file.
  *   mkdir_parents - boolean - Create parent directories if they do not exist?
  *   part - int64 - Part if uploading a part.
  *   parts - int64 - How many parts to fetch?
  *   provided_mtime - string - User provided modification time.
  *   ref - string -
  *   restart - int64 - File byte offset to restart from.
  *   size - int64 - Size of file.
  *   structure - string - If copying folder, copy just the structure?
  *   with_rename - boolean - Allow file rename instead of overwrite?
  */
  public static FileUploadPart create() throws RuntimeException {
    return create(null, null, null);
  }

  public static FileUploadPart create(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return create(path, parameters, null);
  }

  public static FileUploadPart create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return create(null, parameters, options);
  }

  public static FileUploadPart create(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }

    if (path != null && parameters.get("path") == null) {
      parameters.put("path", path);
    }

    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }
    if (parameters.containsKey("action") && !(parameters.get("action") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: action must be of type String parameters[\"action\"]");
    }
    if (parameters.containsKey("length") && !(parameters.get("length") instanceof Long || parameters.get("length") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: length must be of type Long or Integer parameters[\"length\"]");
    }
    if (parameters.containsKey("mkdir_parents") && !(parameters.get("mkdir_parents") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: mkdir_parents must be of type Boolean parameters[\"mkdir_parents\"]");
    }
    if (parameters.containsKey("part") && !(parameters.get("part") instanceof Long || parameters.get("part") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: part must be of type Long or Integer parameters[\"part\"]");
    }
    if (parameters.containsKey("parts") && !(parameters.get("parts") instanceof Long || parameters.get("parts") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: parts must be of type Long or Integer parameters[\"parts\"]");
    }
    if (parameters.containsKey("provided_mtime") && !(parameters.get("provided_mtime") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: provided_mtime must be of type String parameters[\"provided_mtime\"]");
    }
    if (parameters.containsKey("ref") && !(parameters.get("ref") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: ref must be of type String parameters[\"ref\"]");
    }
    if (parameters.containsKey("restart") && !(parameters.get("restart") instanceof Long || parameters.get("restart") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: restart must be of type Long or Integer parameters[\"restart\"]");
    }
    if (parameters.containsKey("size") && !(parameters.get("size") instanceof Long || parameters.get("size") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: size must be of type Long or Integer parameters[\"size\"]");
    }
    if (parameters.containsKey("structure") && !(parameters.get("structure") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: structure must be of type String parameters[\"structure\"]");
    }
    if (parameters.containsKey("with_rename") && !(parameters.get("with_rename") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: with_rename must be of type Boolean parameters[\"with_rename\"]");
    }

    if (!parameters.containsKey("action")) {
      parameters.put("action", "put");
    }

    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), path};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/files/%s", urlParts);

    TypeReference<FileUploadPart> typeReference = new TypeReference<FileUploadPart>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   custom_metadata - object - Custom metadata map of keys and values. Limited to 32 keys, 256 characters per key and 1024 characters per value.
  *   provided_mtime - string - Modified time of file.
  *   priority_color - string - Priority/Bookmark color of file.
  */
  public static File update() throws RuntimeException {
    return update(null, null, null);
  }

  public static File update(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return update(path, parameters, null);
  }

  public static File update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static File update(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }


    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }
    if (parameters.containsKey("custom_metadata") && !(parameters.get("custom_metadata") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: custom_metadata must be of type Map<String, String> parameters[\"custom_metadata\"]");
    }
    if (parameters.containsKey("provided_mtime") && !(parameters.get("provided_mtime") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: provided_mtime must be of type String parameters[\"provided_mtime\"]");
    }
    if (parameters.containsKey("priority_color") && !(parameters.get("priority_color") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: priority_color must be of type String parameters[\"priority_color\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), path};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/files/%s", urlParts);

    TypeReference<File> typeReference = new TypeReference<File>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   recursive - boolean - If true, will recursively delete folders.  Otherwise, will error on non-empty folders.
  */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  public static void delete(String path, HashMap<String, Object> parameters) throws RuntimeException {
    delete(path, parameters, null);
  }

  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  public static void delete(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }


    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }
    if (parameters.containsKey("recursive") && !(parameters.get("recursive") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: recursive must be of type Boolean parameters[\"recursive\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), path};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/files/%s", urlParts);

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(path, parameters, options);
  }

  /**
  * Parameters:
  *   path (required) - string - Path to operate on.
  *   preview_size - string - Request a preview size.  Can be `small` (default), `large`, `xlarge`, or `pdf`.
  *   with_previews - boolean - Include file preview information?
  *   with_priority_color - boolean - Include file priority color information?
  */
  public static File find() throws RuntimeException {
    return find(null, null, null);
  }

  public static File find(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return find(path, parameters, null);
  }

  public static File find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static File find(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }


    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }
    if (parameters.containsKey("preview_size") && !(parameters.get("preview_size") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: preview_size must be of type String parameters[\"preview_size\"]");
    }
    if (parameters.containsKey("with_previews") && !(parameters.get("with_previews") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: with_previews must be of type Boolean parameters[\"with_previews\"]");
    }
    if (parameters.containsKey("with_priority_color") && !(parameters.get("with_priority_color") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: with_priority_color must be of type Boolean parameters[\"with_priority_color\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), path};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/file_actions/metadata/%s", urlParts);

    TypeReference<File> typeReference = new TypeReference<File>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static File get() throws RuntimeException {
    return get(null, null, null);
  }

  public static File get(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(path, parameters, options);
  }

  /**
  * Copy File/Folder
  *
  * Parameters:
  *   destination (required) - string - Copy destination path.
  *   structure - boolean - Copy structure only?
  *   overwrite - boolean - Overwrite existing file(s) in the destination?
  */
  public static FileAction copy() throws RuntimeException {
    return copy(null, null, null);
  }

  public static FileAction copy(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return copy(path, parameters, null);
  }

  public static FileAction copy(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return copy(null, parameters, options);
  }

  public static FileAction copy(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }


    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }
    if (!parameters.containsKey("destination") || parameters.get("destination") == null) {
      throw new NullPointerException("Parameter missing: destination parameters[\"destination\"]");
    }

    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }
    if (parameters.containsKey("destination") && !(parameters.get("destination") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: destination must be of type String parameters[\"destination\"]");
    }
    if (parameters.containsKey("structure") && !(parameters.get("structure") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: structure must be of type Boolean parameters[\"structure\"]");
    }
    if (parameters.containsKey("overwrite") && !(parameters.get("overwrite") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: overwrite must be of type Boolean parameters[\"overwrite\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), path};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/file_actions/copy/%s", urlParts);

    TypeReference<FileAction> typeReference = new TypeReference<FileAction>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Move File/Folder
  *
  * Parameters:
  *   destination (required) - string - Move destination path.
  *   overwrite - boolean - Overwrite existing file(s) in the destination?
  */
  public static FileAction move() throws RuntimeException {
    return move(null, null, null);
  }

  public static FileAction move(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return move(path, parameters, null);
  }

  public static FileAction move(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return move(null, parameters, options);
  }

  public static FileAction move(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }


    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }
    if (!parameters.containsKey("destination") || parameters.get("destination") == null) {
      throw new NullPointerException("Parameter missing: destination parameters[\"destination\"]");
    }

    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }
    if (parameters.containsKey("destination") && !(parameters.get("destination") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: destination must be of type String parameters[\"destination\"]");
    }
    if (parameters.containsKey("overwrite") && !(parameters.get("overwrite") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: overwrite must be of type Boolean parameters[\"overwrite\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), path};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/file_actions/move/%s", urlParts);

    TypeReference<FileAction> typeReference = new TypeReference<FileAction>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Begin File Upload
  *
  * Parameters:
  *   mkdir_parents - boolean - Create parent directories if they do not exist?
  *   part - int64 - Part if uploading a part.
  *   parts - int64 - How many parts to fetch?
  *   ref - string -
  *   restart - int64 - File byte offset to restart from.
  *   size - int64 - Total bytes of file being uploaded (include bytes being retained if appending/restarting).
  *   with_rename - boolean - Allow file rename instead of overwrite?
  */
  public static FileUploadPart beginUpload() throws RuntimeException {
    return beginUpload(null, null, null);
  }

  public static FileUploadPart beginUpload(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return beginUpload(path, parameters, null);
  }

  public static FileUploadPart beginUpload(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return beginUpload(null, parameters, options);
  }

  public static FileUploadPart beginUpload(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }


    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }
    if (parameters.containsKey("mkdir_parents") && !(parameters.get("mkdir_parents") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: mkdir_parents must be of type Boolean parameters[\"mkdir_parents\"]");
    }
    if (parameters.containsKey("part") && !(parameters.get("part") instanceof Long || parameters.get("part") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: part must be of type Long or Integer parameters[\"part\"]");
    }
    if (parameters.containsKey("parts") && !(parameters.get("parts") instanceof Long || parameters.get("parts") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: parts must be of type Long or Integer parameters[\"parts\"]");
    }
    if (parameters.containsKey("ref") && !(parameters.get("ref") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: ref must be of type String parameters[\"ref\"]");
    }
    if (parameters.containsKey("restart") && !(parameters.get("restart") instanceof Long || parameters.get("restart") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: restart must be of type Long or Integer parameters[\"restart\"]");
    }
    if (parameters.containsKey("size") && !(parameters.get("size") instanceof Long || parameters.get("size") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: size must be of type Long or Integer parameters[\"size\"]");
    }
    if (parameters.containsKey("with_rename") && !(parameters.get("with_rename") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: with_rename must be of type Boolean parameters[\"with_rename\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), path};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/file_actions/begin_upload/%s", urlParts);

    TypeReference<FileUploadPart> typeReference = new TypeReference<FileUploadPart>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


}
