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
import com.files.util.FilesInputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class FileMigration {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public FileMigration() {
    this(null, null);
  }

  public FileMigration(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public FileMigration(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * File migration ID
  */
  @Getter
  @JsonProperty("id")
  private Long id;

  /**
  * Source path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @Getter
  @JsonProperty("path")
  private String path;

  /**
  * Destination path
  */
  @Getter
  @JsonProperty("dest_path")
  private String destPath;

  /**
  * Number of files processed
  */
  @Getter
  @JsonProperty("files_moved")
  private Long filesMoved;

  /**
  * Total number of files to process
  */
  @Getter
  @JsonProperty("files_total")
  private Long filesTotal;

  /**
  * The type of operation
  */
  @Getter
  @JsonProperty("operation")
  private String operation;

  /**
  * Region
  */
  @Getter
  @JsonProperty("region")
  private String region;

  /**
  * Status
  */
  @Getter
  @JsonProperty("status")
  private String status;



  /**
  * Parameters:
  *   id (required) - int64 - File Migration ID.
  */
  public static List<FileMigration> find() throws IOException{
    return find(null, null,null);
  }
  public static List<FileMigration> find(Long id,  HashMap<String, Object> parameters) throws IOException {
    return find(id, parameters, null);
  }

  public static List<FileMigration> find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(null, parameters, options);
  }

  public static List<FileMigration> find(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id != null){
      parameters.put("id",id);
    }
    if (parameters.containsKey("id") && !(parameters.get("id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    String url = String.format("%s%s/file_migrations/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<List<FileMigration>> typeReference = new TypeReference<List<FileMigration>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<FileMigration> get() throws IOException {
    return get(null, null, null);
  }

  public static List<FileMigration> get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(id, parameters, options);
  }

}


