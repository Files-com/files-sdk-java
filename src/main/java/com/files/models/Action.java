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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class Action {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public Action() {
    this(null, null);
  }

  public Action(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Action(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Action ID
  */
  @Getter
  @JsonProperty("id")
  private Long id;

  /**
  * Path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @Getter
  @JsonProperty("path")
  private String path;

  /**
  * Action occurrence date/time
  */
  @Getter
  @JsonProperty("when")
  private Date when;

  /**
  * The destination path for this action, if applicable
  */
  @Getter
  @JsonProperty("destination")
  private String destination;

  /**
  * Friendly displayed output
  */
  @Getter
  @JsonProperty("display")
  private String display;

  /**
  * IP Address that performed this action
  */
  @Getter
  @JsonProperty("ip")
  private String ip;

  /**
  * The source path for this action, if applicable
  */
  @Getter
  @JsonProperty("source")
  private String source;

  /**
  * Targets
  */
  @Getter
  @JsonProperty("targets")
  private Object[] targets;

  /**
  * User ID
  */
  @Getter
  @JsonProperty("user_id")
  private Long userId;

  /**
  * Username
  */
  @Getter
  @JsonProperty("username")
  private String username;

  /**
  * Type of action
  */
  @Getter
  @JsonProperty("action")
  private String action;

  /**
  * Failure type.  If action was a user login or session failure, why did it fail?
  */
  @Getter
  @JsonProperty("failure_type")
  private String failureType;

  /**
  * Interface on which this action occurred.
  */
  @Getter
  @JsonProperty("interface")
  private String interfaceName;



}


