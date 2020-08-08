package com.files.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.ModelUtils;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class File {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public File() {
    this(null, null);
  }

  public File(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public File(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
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
  private Long id;

  /**
  * File/Folder path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @Getter
  @Setter
  @JsonProperty("path")
  private String path;

  /**
  * File/Folder display name
  */
  @Getter
  @Setter
  @JsonProperty("display_name")
  private String displayName;

  /**
  * Type: `directory` or `file`.
  */
  @Getter
  @Setter
  @JsonProperty("type")
  private String type;

  /**
  * File/Folder size
  */
  @Getter
  @Setter
  @JsonProperty("size")
  private Long size;

  /**
  * File last modified date/time, according to the server.  This is the timestamp of the last Files.com operation of the file, regardless of what modified timestamp was sent.
  */
  @Getter
  @Setter
  @JsonProperty("mtime")
  private Date mtime;

  /**
  * File last modified date/time, according to the client who set it.  Files.com allows desktop, FTP, SFTP, and WebDAV clients to set modified at times.  This allows Desktop<->Cloud syncing to preserve modified at times.
  */
  @Getter
  @Setter
  @JsonProperty("provided_mtime")
  private Date providedMtime;

  /**
  * File CRC32 checksum. This is sometimes delayed, so if you get a blank response, wait and try again.
  */
  @Getter
  @Setter
  @JsonProperty("crc32")
  private String crc32;

  /**
  * File MD5 checksum. This is sometimes delayed, so if you get a blank response, wait and try again.
  */
  @Getter
  @Setter
  @JsonProperty("md5")
  private String md5;

  /**
  * MIME Type.  This is determined by the filename extension and is not stored separately internally.
  */
  @Getter
  @Setter
  @JsonProperty("mime_type")
  private String mimeType;

  /**
  * Region location
  */
  @Getter
  @Setter
  @JsonProperty("region")
  private String region;

  /**
  * A short string representing the current user's permissions.  Can be `r`,`w`,`p`, or any combination
  */
  @Getter
  @Setter
  @JsonProperty("permissions")
  private String permissions;

  /**
  * Are subfolders locked and unable to be modified?
  */
  @Getter
  @Setter
  @JsonProperty("subfolders_locked?")
  private Boolean subfoldersLocked;

  /**
  * Link to download file. Provided only in response to a download request.
  */
  @Getter
  @Setter
  @JsonProperty("download_uri")
  private String downloadUri;

  /**
  * Bookmark/priority color of file/folder
  */
  @Getter
  @Setter
  @JsonProperty("priority_color")
  private String priorityColor;

  /**
  * File preview ID
  */
  @Getter
  @Setter
  @JsonProperty("preview_id")
  private Long previewId;

  /**
  * File preview
  */
  @Getter
  @Setter
  @JsonProperty("preview")
  private Object preview;

  /**
  * The action to perform.  Can be `append`, `attachment`, `end`, `upload`, `put`, or may not exist
  */
  @Getter
  @Setter
  @JsonProperty("action")
  private String action;

  /**
  * Length of file.
  */
  @Getter
  @Setter
  @JsonProperty("length")
  private Long length;

  /**
  * Create parent directories if they do not exist?
  */
  @Getter
  @Setter
  @JsonProperty("mkdir_parents")
  private Boolean mkdirParents;

  /**
  * Part if uploading a part.
  */
  @Getter
  @Setter
  @JsonProperty("part")
  private Long part;

  /**
  * How many parts to fetch?
  */
  @Getter
  @Setter
  @JsonProperty("parts")
  private Long parts;

  /**
  */
  @Getter
  @Setter
  @JsonProperty("ref")
  private String ref;

  /**
  * File byte offset to restart from.
  */
  @Getter
  @Setter
  @JsonProperty("restart")
  private Long restart;

  /**
  * If copying folder, copy just the structure?
  */
  @Getter
  @Setter
  @JsonProperty("structure")
  private String structure;

  /**
  * Allow file rename instead of overwrite?
  */
  @Getter
  @Setter
  @JsonProperty("with_rename")
  private Boolean withRename;

  /**
  * Download file
  *
  * Parameters:
  *   action - string - Can be blank, `redirect` or `stat`.  If set to `stat`, we will return file information but without a download URL, and without logging a download.  If set to `redirect` we will serve a 302 redirect directly to the file.  This is used for integrations with Zapier, and is not recommended for most integrations.
  *   preview_size - string - Request a preview size.  Can be `small` (default), `large`, `xlarge`, or `pdf`.
  *   with_previews - boolean - Include file preview information?
  *   with_priority_color - boolean - Include file priority color information?
  */
  public File download(HashMap<String, Object> parameters) {
    return download(parameters);
  }

  /**
  * Parameters:
  *   provided_mtime - string - Modified time of file.
  *   priority_color - string - Priority/Bookmark color of file.
  */
  public File update(HashMap<String, Object> parameters) {
    return update(parameters);
  }

  /**
  * Parameters:
  *   recursive - boolean - If true, will recursively delete folers.  Otherwise, will error on non-empty folders.  For legacy reasons, this parameter may also be provided as the HTTP header `Depth: Infinity`
  */
  public File delete(HashMap<String, Object> parameters) {
    return delete(parameters);
  }

  public void destroy(HashMap<String, Object> parameters) {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    if (parameters.containsKey("id") && parameters.get("id") != null) {
      update(parameters);
    } else {
      File newObject = File.create(parameters, this.options).get(0);
    }
  }

  /**
  * Download file
  *
  * Parameters:
  *   action - string - Can be blank, `redirect` or `stat`.  If set to `stat`, we will return file information but without a download URL, and without logging a download.  If set to `redirect` we will serve a 302 redirect directly to the file.  This is used for integrations with Zapier, and is not recommended for most integrations.
  *   preview_size - string - Request a preview size.  Can be `small` (default), `large`, `xlarge`, or `pdf`.
  *   with_previews - boolean - Include file preview information?
  *   with_priority_color - boolean - Include file priority color information?
  */
  public static List<File> download() throws IOException{
    return download(null, null,null);
  }
  public static List<File> download(String path,  HashMap<String, Object> parameters) throws IOException {
    return download(path, parameters, null);
  }

  public static List<File> download(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return download(null, parameters, options);
  }

  public static List<File> download(String path,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path != null){
      parameters.put("path",path);
    }
    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("action") && !(parameters.get("action") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: action must be of type String parameters[\"action\"]");
    }

    if (parameters.containsKey("preview_size") && !(parameters.get("preview_size") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: preview_size must be of type String parameters[\"preview_size\"]");
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
    String url = String.format("%s%s/files/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), path);
    TypeReference<List<File>> typeReference = new TypeReference<List<File>>() {};
    return FilesClient.request(url, RequestMethods.GET, typeReference, parameters, options);
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
  public static List<File> create() throws IOException{
    return create(null, null,null);
  }
  public static List<File> create(String path,  HashMap<String, Object> parameters) throws IOException {
    return create(path, parameters, null);
  }

  public static List<File> create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return create(null, parameters, options);
  }

  public static List<File> create(String path,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path != null){
      parameters.put("path",path);
    }
    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("action") && !(parameters.get("action") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: action must be of type String parameters[\"action\"]");
    }



    if (parameters.containsKey("length") && !(parameters.get("length") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: length must be of type Long parameters[\"length\"]");
    }

    if (parameters.containsKey("mkdir_parents") && !(parameters.get("mkdir_parents") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: mkdir_parents must be of type Boolean parameters[\"mkdir_parents\"]");
    }

    if (parameters.containsKey("part") && !(parameters.get("part") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: part must be of type Long parameters[\"part\"]");
    }

    if (parameters.containsKey("parts") && !(parameters.get("parts") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: parts must be of type Long parameters[\"parts\"]");
    }

    if (parameters.containsKey("provided_mtime") && !(parameters.get("provided_mtime") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: provided_mtime must be of type String parameters[\"provided_mtime\"]");
    }

    if (parameters.containsKey("ref") && !(parameters.get("ref") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: ref must be of type String parameters[\"ref\"]");
    }

    if (parameters.containsKey("restart") && !(parameters.get("restart") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: restart must be of type Long parameters[\"restart\"]");
    }

    if (parameters.containsKey("size") && !(parameters.get("size") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: size must be of type Long parameters[\"size\"]");
    }

    if (parameters.containsKey("structure") && !(parameters.get("structure") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: structure must be of type String parameters[\"structure\"]");
    }

    if (parameters.containsKey("with_rename") && !(parameters.get("with_rename") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: with_rename must be of type Boolean parameters[\"with_rename\"]");
    }

    if (!parameters.containsKey("path") || parameters.get("path") == null) {
      throw new NullPointerException("Parameter missing: path parameters[\"path\"]");
    }
    String url = String.format("%s%s/files/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), path);
    TypeReference<List<File>> typeReference = new TypeReference<List<File>>() {};
    return FilesClient.request(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   provided_mtime - string - Modified time of file.
  *   priority_color - string - Priority/Bookmark color of file.
  */
  public static List<File> update() throws IOException{
    return update(null, null,null);
  }
  public static List<File> update(String path,  HashMap<String, Object> parameters) throws IOException {
    return update(path, parameters, null);
  }

  public static List<File> update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return update(null, parameters, options);
  }

  public static List<File> update(String path,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path != null){
      parameters.put("path",path);
    }
    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("provided_mtime") && !(parameters.get("provided_mtime") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: provided_mtime must be of type String parameters[\"provided_mtime\"]");
    }

    if (parameters.containsKey("priority_color") && !(parameters.get("priority_color") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: priority_color must be of type String parameters[\"priority_color\"]");
    }

    if (!parameters.containsKey("path") || parameters.get("path") == null) {
      throw new NullPointerException("Parameter missing: path parameters[\"path\"]");
    }
    String url = String.format("%s%s/files/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), path);
    TypeReference<List<File>> typeReference = new TypeReference<List<File>>() {};
    return FilesClient.request(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   recursive - boolean - If true, will recursively delete folers.  Otherwise, will error on non-empty folders.  For legacy reasons, this parameter may also be provided as the HTTP header `Depth: Infinity`
  */
  public static List<File> delete() throws IOException{
    return delete(null, null,null);
  }
  public static List<File> delete(String path,  HashMap<String, Object> parameters) throws IOException {
    return delete(path, parameters, null);
  }

  public static List<File> delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(null, parameters, options);
  }

  public static List<File> delete(String path,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path != null){
      parameters.put("path",path);
    }
    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("recursive") && !(parameters.get("recursive") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: recursive must be of type Boolean parameters[\"recursive\"]");
    }

    if (!parameters.containsKey("path") || parameters.get("path") == null) {
      throw new NullPointerException("Parameter missing: path parameters[\"path\"]");
    }
    String url = String.format("%s%s/files/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), path);
    TypeReference<List<File>> typeReference = new TypeReference<List<File>>() {};
    return FilesClient.request(url, RequestMethods.DELETE, typeReference, parameters, options);
  }

  public static List<File> destroy() throws IOException {
    return destroy(null, null, null);
  }

  public static List<File> destroy(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(path, parameters, options);
  }

}


