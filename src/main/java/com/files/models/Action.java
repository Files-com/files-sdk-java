package com.files.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.files.FilesClient;
import com.files.FilesConfig;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Action {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
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
  @Getter
  @JsonProperty("id")
  public Long id;

  /**
  * Path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @Getter
  @JsonProperty("path")
  public String path;

  /**
  * Action occurrence date/time
  */
  @Getter
  @JsonProperty("when")
  public Date when;

  /**
  * The destination path for this action, if applicable
  */
  @Getter
  @JsonProperty("destination")
  public String destination;

  /**
  * Friendly displayed output
  */
  @Getter
  @JsonProperty("display")
  public String display;

  /**
  * IP Address that performed this action
  */
  @Getter
  @JsonProperty("ip")
  public String ip;

  /**
  * The source path for this action, if applicable
  */
  @Getter
  @JsonProperty("source")
  public String source;

  /**
  * Targets
  */
  @Getter
  @JsonProperty("targets")
  public Object[] targets;

  /**
  * User ID
  */
  @Getter
  @JsonProperty("user_id")
  public Long userId;

  /**
  * Username
  */
  @Getter
  @JsonProperty("username")
  public String username;

  /**
  * Type of action
  */
  @Getter
  @JsonProperty("action")
  public String action;

  /**
  * Failure type.  If action was a user login or session failure, why did it fail?
  */
  @Getter
  @JsonProperty("failure_type")
  public String failureType;

  /**
  * Interface on which this action occurred.
  */
  @Getter
  @JsonProperty("interface")
  public String interfaceName;



}
