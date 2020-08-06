package com.files.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.net.HttpMethods.RequestMethods;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class FileCommentReaction {
  private HashMap<String, Object> attributes;
  private HashMap<String, Object> options;

  public FileCommentReaction() {
    this(null, null);
  }

  public FileCommentReaction(HashMap<String, Object> attributes) {
    this(attributes, null);
  }

  public FileCommentReaction(HashMap<String, Object> attributes, HashMap<String, Object> options) {
    this.attributes = attributes;
    this.options = options;
    try{
      ObjectMapper objectMapper = new ObjectMapper();
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(attributes));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Reaction ID
  */
  @Getter
  @Setter
  @JsonProperty("id")
  public Long id;

  /**
  * Emoji used in the reaction.
  */
  @Getter
  @Setter
  @JsonProperty("emoji")
  public String emoji;

  /**
  * User ID.  Provide a value of `0` to operate the current session's user.
  */
  @Getter
  @Setter
  @JsonProperty("user_id")
  public Long userId;

  /**
  * ID of file comment to attach reaction to.
  */
  @Getter
  @Setter
  @JsonProperty("file_comment_id")
  public Long fileCommentId;

  /**
  */
  public FileCommentReaction delete(HashMap<String, Object> parameters) {
    // TODO: Fill in operation implementation
    return (FileCommentReaction) null;
  }

  public void destroy(HashMap<String, Object> parameters) {
    delete(parameters);
  }

  public void save() throws IOException {
    if (this.attributes.get("id") != null) {
      throw new UnsupportedOperationException("The FileCommentReaction Object doesn't support updates.");
    } else {
      FileCommentReaction.create(this.attributes, this.options);
      // TODO save this.attributes = newObj.attributes;
    }
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   file_comment_id (required) - int64 - ID of file comment to attach reaction to.
  *   emoji (required) - string - Emoji to react with.
  */
  public static List<FileCommentReaction> create() throws IOException{
    return create(null,null);
  }
  public static List<FileCommentReaction> create( HashMap<String, Object> parameters) throws IOException {
    return create(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static List<FileCommentReaction> create( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }

    if (parameters.containsKey("file_comment_id") && !(parameters.get("file_comment_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: file_comment_id must be of type Long parameters[\"file_comment_id\"]");
    }

    if (parameters.containsKey("emoji") && !(parameters.get("emoji") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: emoji must be of type String parameters[\"emoji\"]");
    }

    if (!parameters.containsKey("file_comment_id") || parameters.get("file_comment_id") == null) {
      throw new NullPointerException("Parameter missing: file_comment_id parameters[\"file_comment_id\"]");
    }
    if (!parameters.containsKey("emoji") || parameters.get("emoji") == null) {
      throw new NullPointerException("Parameter missing: emoji parameters[\"emoji\"]");
    }
    String url = String.format("%s%s/file_comment_reactions", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<FileCommentReaction>> typeReference = new TypeReference<List<FileCommentReaction>>() {};
    return FilesClient.request(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  */
  public static List<FileCommentReaction> delete() throws IOException{
    return delete(null, null,null);
  }
  public static List<FileCommentReaction> delete(Long id,  HashMap<String, Object> parameters) throws IOException {
    return delete(id, parameters, null);
  }

  public static List<FileCommentReaction> delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static List<FileCommentReaction> delete(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
    String url = String.format("%s%s/file_comment_reactions/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<List<FileCommentReaction>> typeReference = new TypeReference<List<FileCommentReaction>>() {};
    return FilesClient.request(url, RequestMethods.DELETE, typeReference, parameters, options);
  }

  public static List<FileCommentReaction> destroy() throws IOException {
    return destroy(null, null, null);
  }

  public static List<FileCommentReaction> destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(id, parameters, options);
  }

}


