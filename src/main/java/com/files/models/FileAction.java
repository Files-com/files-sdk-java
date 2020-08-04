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

public class FileAction {
  private HashMap<String, Object> attributes;
  private HashMap<String, Object> options;

  public FileAction(HashMap<String, Object> attributes, HashMap<String, Object> options) {
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
  * Copy file/folder
  *
  * Parameters:
  *   destination (required) - string - Copy destination path.
  *   structure - boolean - Copy structure only?
  */
  public FileAction copy(HashMap<String, Object> parameters) {
    // TODO: Fill in operation implementation
    return (FileAction) null;
  }

  /**
  * Move file/folder
  *
  * Parameters:
  *   destination (required) - string - Move destination path.
  */
  public FileAction move(HashMap<String, Object> parameters) {
    // TODO: Fill in operation implementation
    return (FileAction) null;
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
    // TODO: Fill in operation implementation
    return (FileAction) null;
  }



  /**
  * Copy file/folder
  *
  * Parameters:
  *   destination (required) - string - Copy destination path.
  *   structure - boolean - Copy structure only?
  */
  public static FileAction copy(String path,  HashMap<String, Object> parameters) {
    return copy(path, parameters, null);
  }

  public static FileAction copy(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return copy(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static FileAction copy(String path,  HashMap<String, Object> parameters, HashMap<String, Object> options) {
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
    // TODO: Send request
    return (FileAction) null;
  }


  /**
  * Move file/folder
  *
  * Parameters:
  *   destination (required) - string - Move destination path.
  */
  public static FileAction move(String path,  HashMap<String, Object> parameters) {
    return move(path, parameters, null);
  }

  public static FileAction move(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return move(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static FileAction move(String path,  HashMap<String, Object> parameters, HashMap<String, Object> options) {
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
    // TODO: Send request
    return (FileAction) null;
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
  public static FileAction beginUpload(String path,  HashMap<String, Object> parameters) {
    return beginUpload(path, parameters, null);
  }

  public static FileAction beginUpload(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return beginUpload(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static FileAction beginUpload(String path,  HashMap<String, Object> parameters, HashMap<String, Object> options) {
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
    // TODO: Send request
    return (FileAction) null;
  }


}


