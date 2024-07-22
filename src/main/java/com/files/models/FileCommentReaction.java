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
public class FileCommentReaction {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();


  public FileCommentReaction() {
    this(null, null);
  }

  public FileCommentReaction(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public FileCommentReaction(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
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
  public void delete() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    FileCommentReaction.delete(this.id, parameters);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete();
  }


  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    FileCommentReaction.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   file_comment_id (required) - int64 - ID of file comment to attach reaction to.
  *   emoji (required) - string - Emoji to react with.
  */

  public static FileCommentReaction create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static FileCommentReaction create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (!parameters.containsKey("file_comment_id") || parameters.get("file_comment_id") == null) {
      throw new NullPointerException("Parameter missing: file_comment_id parameters[\"file_comment_id\"]");
    }
    if (!parameters.containsKey("emoji") || parameters.get("emoji") == null) {
      throw new NullPointerException("Parameter missing: emoji parameters[\"emoji\"]");
    }

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }
    if (parameters.containsKey("file_comment_id") && !(parameters.get("file_comment_id") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: file_comment_id must be of type Long parameters[\"file_comment_id\"]");
    }
    if (parameters.containsKey("emoji") && !(parameters.get("emoji") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: emoji must be of type String parameters[\"emoji\"]");
    }


    String url = String.format("%s%s/file_comment_reactions", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<FileCommentReaction> typeReference = new TypeReference<FileCommentReaction>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  */

  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/file_comment_reactions/%s", urlParts);

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
