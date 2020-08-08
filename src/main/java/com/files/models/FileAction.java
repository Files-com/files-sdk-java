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

public class FileAction {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public FileAction() {
    this(null, null);
  }

  public FileAction(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public FileAction(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Copy file/folder
  *
  * Parameters:
  *   destination (required) - string - Copy destination path.
  *   structure - boolean - Copy structure only?
  */
  public FileAction copy(HashMap<String, Object> parameters) {
    return copy(parameters);
  }

  /**
  * Move file/folder
  *
  * Parameters:
  *   destination (required) - string - Move destination path.
  */
  public FileAction move(HashMap<String, Object> parameters) {
    return move(parameters);
  }

  /**
  * Begin file upload
  *
  * Parameters:
  *   mkdir_parents - boolean - Create parent directories if they do not exist?
  *   part - int64 - Part if uploading a part.
  *   parts - int64 - How many parts to fetch?
  *   ref - string -
  *   restart - int64 - File byte offset to restart from.
  *   with_rename - boolean - Allow file rename instead of overwrite?
  */
  public FileAction beginUpload(HashMap<String, Object> parameters) {
    return beginUpload(parameters);
  }



  /**
  * Copy file/folder
  *
  * Parameters:
  *   destination (required) - string - Copy destination path.
  *   structure - boolean - Copy structure only?
  */
  public static List<FileAction> copy() throws IOException{
    return copy(null, null,null);
  }
  public static List<FileAction> copy(String path,  HashMap<String, Object> parameters) throws IOException {
    return copy(path, parameters, null);
  }

  public static List<FileAction> copy(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return copy(null, parameters, options);
  }

  public static List<FileAction> copy(String path,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path != null){
      parameters.put("path",path);
    }
    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("destination") && !(parameters.get("destination") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: destination must be of type String parameters[\"destination\"]");
    }

    if (parameters.containsKey("structure") && !(parameters.get("structure") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: structure must be of type Boolean parameters[\"structure\"]");
    }

    if (!parameters.containsKey("path") || parameters.get("path") == null) {
      throw new NullPointerException("Parameter missing: path parameters[\"path\"]");
    }
    if (!parameters.containsKey("destination") || parameters.get("destination") == null) {
      throw new NullPointerException("Parameter missing: destination parameters[\"destination\"]");
    }
    String url = String.format("%s%s/file_actions/copy/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), path);
    TypeReference<List<FileAction>> typeReference = new TypeReference<List<FileAction>>() {};
    return FilesClient.request(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Move file/folder
  *
  * Parameters:
  *   destination (required) - string - Move destination path.
  */
  public static List<FileAction> move() throws IOException{
    return move(null, null,null);
  }
  public static List<FileAction> move(String path,  HashMap<String, Object> parameters) throws IOException {
    return move(path, parameters, null);
  }

  public static List<FileAction> move(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return move(null, parameters, options);
  }

  public static List<FileAction> move(String path,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path != null){
      parameters.put("path",path);
    }
    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("destination") && !(parameters.get("destination") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: destination must be of type String parameters[\"destination\"]");
    }

    if (!parameters.containsKey("path") || parameters.get("path") == null) {
      throw new NullPointerException("Parameter missing: path parameters[\"path\"]");
    }
    if (!parameters.containsKey("destination") || parameters.get("destination") == null) {
      throw new NullPointerException("Parameter missing: destination parameters[\"destination\"]");
    }
    String url = String.format("%s%s/file_actions/move/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), path);
    TypeReference<List<FileAction>> typeReference = new TypeReference<List<FileAction>>() {};
    return FilesClient.request(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Begin file upload
  *
  * Parameters:
  *   mkdir_parents - boolean - Create parent directories if they do not exist?
  *   part - int64 - Part if uploading a part.
  *   parts - int64 - How many parts to fetch?
  *   ref - string -
  *   restart - int64 - File byte offset to restart from.
  *   with_rename - boolean - Allow file rename instead of overwrite?
  */
  public static List<FileAction> beginUpload() throws IOException{
    return beginUpload(null, null,null);
  }
  public static List<FileAction> beginUpload(String path,  HashMap<String, Object> parameters) throws IOException {
    return beginUpload(path, parameters, null);
  }

  public static List<FileAction> beginUpload(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return beginUpload(null, parameters, options);
  }

  public static List<FileAction> beginUpload(String path,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path != null){
      parameters.put("path",path);
    }
    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
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

    if (parameters.containsKey("ref") && !(parameters.get("ref") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: ref must be of type String parameters[\"ref\"]");
    }

    if (parameters.containsKey("restart") && !(parameters.get("restart") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: restart must be of type Long parameters[\"restart\"]");
    }

    if (parameters.containsKey("with_rename") && !(parameters.get("with_rename") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: with_rename must be of type Boolean parameters[\"with_rename\"]");
    }

    if (!parameters.containsKey("path") || parameters.get("path") == null) {
      throw new NullPointerException("Parameter missing: path parameters[\"path\"]");
    }
    String url = String.format("%s%s/file_actions/begin_upload/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), path);
    TypeReference<List<FileAction>> typeReference = new TypeReference<List<FileAction>>() {};
    return FilesClient.request(url, RequestMethods.POST, typeReference, parameters, options);
  }


}


