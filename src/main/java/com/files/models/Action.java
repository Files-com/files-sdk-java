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
public class Action implements ModelInterface {
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


  public Action() {
    this(null, null);
  }

  public Action(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Action(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Action ID
  */
  @JsonProperty("id")
  public Long id;

  public Long getId() {
    return id;
  }

  /**
  * Path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @JsonProperty("path")
  public String path;

  public String getPath() {
    return path;
  }

  /**
  * Action occurrence date/time
  */
  @JsonProperty("when")
  public Date when;

  public Date getWhen() {
    return when;
  }

  /**
  * The destination path for this action, if applicable
  */
  @JsonProperty("destination")
  public String destination;

  public String getDestination() {
    return destination;
  }

  /**
  * Friendly displayed output
  */
  @JsonProperty("display")
  public String display;

  public String getDisplay() {
    return display;
  }

  /**
  * IP Address that performed this action
  */
  @JsonProperty("ip")
  public String ip;

  public String getIp() {
    return ip;
  }

  /**
  * The source path for this action, if applicable
  */
  @JsonProperty("source")
  public String source;

  public String getSource() {
    return source;
  }

  /**
  * Targets
  */
  @JsonProperty("targets")
  public Map<String, String> targets;

  public Map<String, String> getTargets() {
    return targets;
  }

  /**
  * User ID
  */
  @JsonProperty("user_id")
  public Long userId;

  public Long getUserId() {
    return userId;
  }

  /**
  * Username
  */
  @JsonProperty("username")
  public String username;

  public String getUsername() {
    return username;
  }

  /**
  * true if this change was performed by a user on a parent site.
  */
  @JsonProperty("user_is_from_parent_site")
  public Boolean userIsFromParentSite;

  public Boolean getUserIsFromParentSite() {
    return userIsFromParentSite;
  }

  /**
  * Type of action
  */
  @JsonProperty("action")
  public String action;

  public String getAction() {
    return action;
  }

  /**
  * Failure type.  If action was a user login or session failure, why did it fail?
  */
  @JsonProperty("failure_type")
  public String failureType;

  public String getFailureType() {
    return failureType;
  }

  /**
  * Interface on which this action occurred.
  */
  @JsonProperty("interface")
  public String interfaceName;

  public String getInterfaceName() {
    return interfaceName;
  }


}
