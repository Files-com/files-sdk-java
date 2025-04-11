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
public class RemoteServerConfigurationFile implements ModelInterface {
  @Setter
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
  * The remote server ID of the agent
  */
  @Getter
  @JsonProperty("id")
  public Long id;

  /**
  * The permission set for the agent ['read_write', 'read_only', 'write_only']
  */
  @Getter
  @JsonProperty("permission_set")
  public String permissionSet;

  /**
  * The private key for the agent
  */
  @Getter
  @JsonProperty("private_key")
  public String privateKey;

  /**
  * Files.com subdomain site name
  */
  @Getter
  @JsonProperty("subdomain")
  public String subdomain;

  /**
  * The root directory for the agent
  */
  @Getter
  @JsonProperty("root")
  public String root;

  /**
  * Follow symlinks when traversing directories
  */
  @Getter
  @JsonProperty("follow_links")
  public Boolean followLinks;

  /**
  * Preferred network protocol ['udp', 'tcp'] (default udp)
  */
  @Getter
  @JsonProperty("prefer_protocol")
  public String preferProtocol;

  /**
  * DNS lookup method ['auto','doh','system'] (default auto)
  */
  @Getter
  @JsonProperty("dns")
  public String dns;

  /**
  * Proxy all outbound traffic through files.com proxy server
  */
  @Getter
  @JsonProperty("proxy_all_outbound")
  public Boolean proxyAllOutbound;

  /**
  * Custom site endpoint URL
  */
  @Getter
  @JsonProperty("endpoint_override")
  public String endpointOverride;

  /**
  * Log file name and location
  */
  @Getter
  @JsonProperty("log_file")
  public String logFile;

  /**
  * Log level for the agent logs ['debug', 'info', 'warn', 'error', 'fatal'] (default info)
  */
  @Getter
  @JsonProperty("log_level")
  public String logLevel;

  /**
  * Log route for agent logs. (default 5)
  */
  @Getter
  @JsonProperty("log_rotate_num")
  public Long logRotateNum;

  /**
  * Log route size in MB for agent logs. (default 20)
  */
  @Getter
  @JsonProperty("log_rotate_size")
  public Long logRotateSize;

  /**
  * Maximum number of concurrent jobs (default 500)
  */
  @Getter
  @JsonProperty("override_max_concurrent_jobs")
  public Long overrideMaxConcurrentJobs;

  /**
  * Graceful shutdown timeout in seconds (default 15)
  */
  @Getter
  @JsonProperty("graceful_shutdown_timeout")
  public Long gracefulShutdownTimeout;

  /**
  * File transfer (upload/download) rate limit
  *  ` limit - period `, with the given periods:
  * * 'S': second
  * * 'M': minute
  * * 'H': hour
  * * 'D': day
  * Examples:
  * * 5 requests/second: '5-S'
  * * 10 requests/minute: '10-M'
  * * 1000 requests/hour: '1000-H'
  * * 2000 requests/day: '2000-D'
  */
  @Getter
  @JsonProperty("transfer_rate_limit")
  public String transferRateLimit;

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
