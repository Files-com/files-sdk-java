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

public class MessageReaction {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public MessageReaction() {
    this(null, null);
  }

  public MessageReaction(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public MessageReaction(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
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
  private Long id;

  /**
  * Emoji used in the reaction.
  */
  @Getter
  @Setter
  @JsonProperty("emoji")
  private String emoji;

  /**
  * User ID.  Provide a value of `0` to operate the current session's user.
  */
  @Getter
  @Setter
  @JsonProperty("user_id")
  private Long userId;

  /**
  */
  public MessageReaction delete(HashMap<String, Object> parameters) {
    return delete(parameters);
  }

  public void destroy(HashMap<String, Object> parameters) {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    if (parameters.containsKey("id") && parameters.get("id") != null) {
      throw new UnsupportedOperationException("The MessageReaction Object doesn't support updates.");
    } else {
      MessageReaction newObject = MessageReaction.create(parameters, this.options);
    }
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   cursor - string - Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   message_id (required) - int64 - Message to return reactions for.
  */
  public static List<MessageReaction> list() throws IOException{
    return list(null,null);
  }
  public static List<MessageReaction> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  public static List<MessageReaction> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }

    if (parameters.containsKey("message_id") && !(parameters.get("message_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: message_id must be of type Long parameters[\"message_id\"]");
    }

    if (!parameters.containsKey("message_id") || parameters.get("message_id") == null) {
      throw new NullPointerException("Parameter missing: message_id parameters[\"message_id\"]");
    }
    String url = String.format("%s%s/message_reactions", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<MessageReaction>> typeReference = new TypeReference<List<MessageReaction>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<MessageReaction> all() throws IOException {
    return all(null, null);
  }

  public static List<MessageReaction> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Message Reaction ID.
  */
  public static List<MessageReaction> find() throws IOException{
    return find(null, null,null);
  }
  public static List<MessageReaction> find(Long id,  HashMap<String, Object> parameters) throws IOException {
    return find(id, parameters, null);
  }

  public static List<MessageReaction> find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(null, parameters, options);
  }

  public static List<MessageReaction> find(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
    String url = String.format("%s%s/message_reactions/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<List<MessageReaction>> typeReference = new TypeReference<List<MessageReaction>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<MessageReaction> get() throws IOException {
    return get(null, null, null);
  }

  public static List<MessageReaction> get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   emoji (required) - string - Emoji to react with.
  */
  public static MessageReaction create() throws IOException{
    return create(null,null);
  }
  public static MessageReaction create( HashMap<String, Object> parameters) throws IOException {
    return create(parameters, null);
  }


  public static MessageReaction create( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }

    if (parameters.containsKey("emoji") && !(parameters.get("emoji") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: emoji must be of type String parameters[\"emoji\"]");
    }

    if (!parameters.containsKey("emoji") || parameters.get("emoji") == null) {
      throw new NullPointerException("Parameter missing: emoji parameters[\"emoji\"]");
    }
    String url = String.format("%s%s/message_reactions", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<MessageReaction> typeReference = new TypeReference<MessageReaction>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  */
  public static MessageReaction delete() throws IOException{
    return delete(null, null,null);
  }
  public static MessageReaction delete(Long id,  HashMap<String, Object> parameters) throws IOException {
    return delete(id, parameters, null);
  }

  public static MessageReaction delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(null, parameters, options);
  }

  public static MessageReaction delete(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
    String url = String.format("%s%s/message_reactions/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<MessageReaction> typeReference = new TypeReference<MessageReaction>() {};
    return FilesClient.requestItem(url, RequestMethods.DELETE, typeReference, parameters, options);
  }

  public static MessageReaction destroy() throws IOException {
    return destroy(null, null, null);
  }

  public static MessageReaction destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(id, parameters, options);
  }

}


