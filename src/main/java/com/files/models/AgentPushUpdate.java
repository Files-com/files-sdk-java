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
import com.files.util.UrlUtils;
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

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AgentPushUpdate implements ModelInterface {
  private HashMap<String, Object> options;

  public void setOptions(HashMap<String, Object> options) {
    this.options = options;
  }

  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();


  public AgentPushUpdate() {
    this(null, null);
  }

  public AgentPushUpdate(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public AgentPushUpdate(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Pushed agent version
  */
  @JsonProperty("version")
  public String version;

  public String getVersion() {
    return version;
  }

  /**
  * Update accepted or reason
  */
  @JsonProperty("message")
  public String message;

  public String getMessage() {
    return message;
  }

  /**
  * Installed agent version
  */
  @JsonProperty("current_version")
  public String currentVersion;

  public String getCurrentVersion() {
    return currentVersion;
  }

  /**
  * Pending agent version or null
  */
  @JsonProperty("pending_version")
  public String pendingVersion;

  public String getPendingVersion() {
    return pendingVersion;
  }

  /**
  * Last error message or null
  */
  @JsonProperty("last_error")
  public String lastError;

  public String getLastError() {
    return lastError;
  }

  /**
  * Error code
  */
  @JsonProperty("error")
  public String error;

  public String getError() {
    return error;
  }


}
