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
public class Folder implements ModelInterface {
  @Setter
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();


  public Folder() {
    this(null, null);
  }

  public Folder(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Folder(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
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
  * Create parent directories if they do not exist?
  */
  @Getter
  @Setter
  @JsonProperty("mkdir_parents")
  public Boolean mkdirParents;

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    Folder.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   cursor - string - Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header or the X-Files-Cursor-Prev header.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   path (required) - string - Path to operate on.
  *   preview_size - string - Request a preview size.  Can be `small` (default), `large`, `xlarge`, or `pdf`.
  *   sort_by - object - Search by field and direction. Valid fields are `path`, `size`, `modified_at_datetime`, `provided_modified_at`.  Valid directions are `asc` and `desc`.  Defaults to `{"path":"asc"}`.
  *   search - string - If specified, will search the folders/files list by name. Ignores text before last `/`. This is the same API used by the search bar in the web UI when running 'Search This Folder'.  Search results are a best effort, not real time, and not guaranteed to perfectly match the latest folder listing.  Results may be truncated if more than 1,000 possible matches exist.  This field should only be used for ad-hoc (human) searching, and not as part of an automated process.
  *   search_custom_metadata_key - string - If provided, the search string in `search` will search for files where this custom metadata key matches the value sent in `search`.  Set this to `*` to allow any metadata key to match the value sent in `search`.
  *   search_all - boolean - Search entire site?  If set, we will ignore the folder path provided and search the entire site.  This is the same API used by the search bar in the web UI when running 'Search All Files'.  Search results are a best effort, not real time, and not guaranteed to match every file.  This field should only be used for ad-hoc (human) searching, and not as part of an automated process.
  *   with_previews - boolean - Include file previews?
  *   with_priority_color - boolean - Include file priority color information?
  */
  public static ListIterator<File> listFor() throws RuntimeException {
    return listFor(null, null, null);
  }

  public static ListIterator<File> listFor(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return listFor(path, parameters, null);
  }

  public static ListIterator<File> listFor(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return listFor(null, parameters, options);
  }

  public static ListIterator<File> listFor(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }


    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }
    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }
    if (parameters.containsKey("preview_size") && !(parameters.get("preview_size") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: preview_size must be of type String parameters[\"preview_size\"]");
    }
    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Map<String, String> parameters[\"sort_by\"]");
    }
    if (parameters.containsKey("search") && !(parameters.get("search") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: search must be of type String parameters[\"search\"]");
    }
    if (parameters.containsKey("search_custom_metadata_key") && !(parameters.get("search_custom_metadata_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: search_custom_metadata_key must be of type String parameters[\"search_custom_metadata_key\"]");
    }
    if (parameters.containsKey("search_all") && !(parameters.get("search_all") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: search_all must be of type Boolean parameters[\"search_all\"]");
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

    String url = String.format("%s%s/folders/%s", urlParts);

    TypeReference<List<File>> typeReference = new TypeReference<List<File>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   path (required) - string - Path to operate on.
  *   mkdir_parents - boolean - Create parent directories if they do not exist?
  *   provided_mtime - string - User provided modification time.
  */
  public static Folder create() throws RuntimeException {
    return create(null, null, null);
  }

  public static Folder create(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return create(path, parameters, null);
  }

  public static Folder create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return create(null, parameters, options);
  }

  public static Folder create(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
    if (parameters.containsKey("provided_mtime") && !(parameters.get("provided_mtime") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: provided_mtime must be of type String parameters[\"provided_mtime\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), path};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/folders/%s", urlParts);

    TypeReference<Folder> typeReference = new TypeReference<Folder>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


}
