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

public class InboxUpload {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public InboxUpload() {
    this(null, null);
  }

  public InboxUpload(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public InboxUpload(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  */
  @Getter
  @JsonProperty("inbox_registration")
  private Map<String, String> inboxRegistration;

  /**
  * Upload path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @Getter
  @JsonProperty("path")
  private String path;

  /**
  * Upload date/time
  */
  @Getter
  @JsonProperty("created_at")
  private Date createdAt;



  /**
  * Parameters:
  *   cursor - string - Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   inbox_registration_id - int64 - InboxRegistration ID
  *   inbox_id - int64 - Inbox ID
  */
  public static List<InboxUpload> list() throws IOException{
    return list(null,null);
  }
  public static List<InboxUpload> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  public static List<InboxUpload> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }

    if (parameters.containsKey("inbox_registration_id") && !(parameters.get("inbox_registration_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: inbox_registration_id must be of type Long parameters[\"inbox_registration_id\"]");
    }

    if (parameters.containsKey("inbox_id") && !(parameters.get("inbox_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: inbox_id must be of type Long parameters[\"inbox_id\"]");
    }

    String url = String.format("%s%s/inbox_uploads", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<InboxUpload>> typeReference = new TypeReference<List<InboxUpload>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<InboxUpload> all() throws IOException {
    return all(null, null);
  }

  public static List<InboxUpload> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

}


