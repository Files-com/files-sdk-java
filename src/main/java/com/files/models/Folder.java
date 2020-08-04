package com.files.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Date;
import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;

public class Folder {
  private HashMap<String, Object> attributes;
  private HashMap<String, Object> options;

  public Folder(HashMap<String, Object> attributes, HashMap<String, Object> options) {
    this.attributes = attributes;
    this.options = options;
    try{
      ObjectMapper objectMapper=new ObjectMapper();
      ObjectReader objectReader=objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(attributes));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * File/Folder ID
  */
  @Getter
  @Setter
  @JsonProperty("id")
  public Long id;

  /**
  * File/Folder path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @Getter
  @Setter
  @JsonProperty("path")
  public String path;

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
  * File last modified date/time, according to the server.  This is the timestamp of the last Files.com operation of the file, regardless of what modified timestamp was sent.
  */
  @Getter
  @Setter
  @JsonProperty("mtime")
  public Date mtime;

  /**
  * File last modified date/time, according to the client who set it.  Files.com allows desktop, FTP, SFTP, and WebDAV clients to set modified at times.  This allows Desktop<->Cloud syncing to preserve modified at times.
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
  * A short string representing the current user's permissions.  Can be `r`,`w`,`p`, or any combination
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
  public Object preview;


  public void save() {
    if (this.attributes.get("path") != null) {
      throw new UnsupportedOperationException("The Folder Object doesn't support updates.");
    } else {
      Folder newObj = Folder.create(this.attributes, this.options);
      this.attributes = newObj.attributes;
    }
  }

  /**
  * Parameters:
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Action to take.  Can be `count`, `count_nrs` (non recursive), `size`, `permissions`, or blank.
  *   path (required) - string - Path to operate on.
  *   cursor - string - Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor header.
  *   filter - string - If specified, will to filter folders/files list by this string.  Wildcards of `*` and `?` are acceptable here.
  *   preview_size - string - Request a preview size.  Can be `small` (default), `large`, `xlarge`, or `pdf`.
  *   search - string - If `search_all` is `true`, provide the search string here.  Otherwise, this parameter acts like an alias of `filter`.
  *   search_all - boolean - Search entire site?
  *   with_previews - boolean - Include file previews?
  *   with_priority_color - boolean - Include file priority color information?
  */
  public static Folder listFor(String path,  HashMap<String, Object> parameters) {
    return listFor(path, parameters, null);
  }

  public static Folder listFor(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return listFor(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static Folder listFor(String path,  HashMap<String, Object> parameters, HashMap<String, Object> options) {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path != null){
      parameters.put("path",path);
    }
    if (parameters.containsKey("page") && !(parameters.get("page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: page must be of type Long parameters[\"page\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }

    if (parameters.containsKey("action") && !(parameters.get("action") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: action must be of type String parameters[\"action\"]");
    }

    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type String parameters[\"filter\"]");
    }

    if (parameters.containsKey("preview_size") && !(parameters.get("preview_size") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: preview_size must be of type String parameters[\"preview_size\"]");
    }

    if (parameters.containsKey("search") && !(parameters.get("search") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: search must be of type String parameters[\"search\"]");
    }

    if (parameters.containsKey("search_all") && !(parameters.get("search_all") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: search_all must be of type Boolean parameters[\"search_all\"]");
    }

    if (parameters.containsKey("with_previews") && !(parameters.get("with_previews") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: with_previews must be of type Boolean parameters[\"with_previews\"]");
    }

    if (parameters.containsKey("with_priority_color") && !(parameters.get("with_priority_color") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: with_priority_color must be of type Boolean parameters[\"with_priority_color\"]");
    }

    if (!parameters.containsKey("path") || parameters.get("path") == null) {
      throw new NullPointerException("Parameter missing: path parameters[\"path\"]");
    }
    // TODO: Send request
    return (Folder) null;
  }


  /**
  * Parameters:
  *   path (required) - string - Path to operate on.
  */
  public static Folder create(String path,  HashMap<String, Object> parameters) {
    return create(path, parameters, null);
  }

  public static Folder create(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return create(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static Folder create(String path,  HashMap<String, Object> parameters, HashMap<String, Object> options) {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path != null){
      parameters.put("path",path);
    }
    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (!parameters.containsKey("path") || parameters.get("path") == null) {
      throw new NullPointerException("Parameter missing: path parameters[\"path\"]");
    }
    // TODO: Send request
    return (Folder) null;
  }


}


