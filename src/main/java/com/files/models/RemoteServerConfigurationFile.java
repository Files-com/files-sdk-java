package com.files.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class RemoteServerConfigurationFile {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
    .builder()
    .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
    .build();

  public RemoteServerConfigurationFile() {
    this(null, null);
  }

  public RemoteServerConfigurationFile(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public RemoteServerConfigurationFile(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Agent ID
  */
  @Getter
  @JsonProperty("id")
  public Long id;

  /**
  */
  @Getter
  @JsonProperty("permission_set")
  public String permissionSet;

  /**
  * Files Agent API Token
  */
  @Getter
  @JsonProperty("api_token")
  public String apiToken;

  /**
  * Agent local root path
  */
  @Getter
  @JsonProperty("root")
  public String root;

  /**
  * Incoming port for files agent connections
  */
  @Getter
  @JsonProperty("port")
  public Long port;

  /**
  */
  @Getter
  @JsonProperty("hostname")
  public String hostname;

  /**
  * public key
  */
  @Getter
  @JsonProperty("public_key")
  public String publicKey;

  /**
  * private key
  */
  @Getter
  @JsonProperty("private_key")
  public String privateKey;

  /**
  * either running or shutdown
  */
  @Getter
  @JsonProperty("status")
  public String status;

  /**
  * agent config version
  */
  @Getter
  @JsonProperty("config_version")
  public String configVersion;



}


