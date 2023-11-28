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
public class RemoteServerConfigurationFile {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();


  public RemoteServerConfigurationFile() {
    this(null, null);
  }

  public RemoteServerConfigurationFile(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public RemoteServerConfigurationFile(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
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
  * private key
  */
  @Getter
  @JsonProperty("private_key")
  public String privateKey;

  /**
  */
  @Getter
  @JsonProperty("subdomain")
  public String subdomain;

  /**
  * Agent local root path
  */
  @Getter
  @JsonProperty("root")
  public String root;

  /**
  * Files Agent API Token
  */
  @Getter
  @JsonProperty("api_token")
  public String apiToken;

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
  * either running or shutdown
  */
  @Getter
  @JsonProperty("status")
  public String status;

  /**
  */
  @Getter
  @JsonProperty("server_host_key")
  public String serverHostKey;

  /**
  * agent config version
  */
  @Getter
  @JsonProperty("config_version")
  public String configVersion;



}
