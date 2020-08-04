package com.files;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.threadly.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class FilesConfig {
  private static final Logger log = LogManager.getLogger(FilesConfig.class);
  private static FilesConfig instance = null;
  private String hostname = "unknown";
  private Properties properties;

  protected FilesConfig() {
    properties = new Properties();
    try (InputStream file = FilesConfig.class.getResourceAsStream("/files-sdk.properties");) {
      properties.load(file);
    } catch (IOException e) {
      log.warn("could not load configurator properties");
    }
  }

  public static FilesConfig getInstance() {
    if (instance == null) {
      synchronized (FilesConfig.class) {
        if (instance == null) {
          instance = new FilesConfig();
        }
      }
    }
    return instance;
  }

  private int intProperty(String key, int defaultValue) {
    String strValue = properties.getProperty(key);
    if (StringUtils.isNullOrEmpty(strValue)) {
      return defaultValue;
    } else {
      return Integer.valueOf(strValue);
    }
  }

  private boolean boolProperty(String key, boolean defaultValue) {
    String strValue = properties.getProperty(key);
    if (StringUtils.isNullOrEmpty(strValue)) {
      return defaultValue;
    } else {
      return Boolean.valueOf(strValue);
    }
  }

  public boolean getAllowInsecureBackend(String def) {
    return boolProperty("allowInsecureBackend", false);
  }

  public String getApiRoot() {
    return properties.getProperty("apiRoot", "https://app.files.com");
  }

  public String getApiKey() {
    return properties.getProperty("apiKey", null);
  }

  public int getInitialRetryDelayMillis() {
    return intProperty("initialRetryDelayMillis", 500);
  }

  public String getSdkVersion() {
    return properties.getProperty("sdkVersion", "0.0.1");
  }

  public boolean getUpstreamHttp2Enabled() {
    return boolProperty("upstreamHttp2Enabled", true);
  }

  public boolean getUpstreamInsecureAllowed() {
    return boolProperty("upstreamInsecureAllowed", false);
  }

  public int getUpstreamMaxAttempts() {
    return intProperty("upstreamMaxAttempts", 5);
  }

  public int getUpstreamMaxConnections() {
    return intProperty("upstreamMaxConnections", 500);
  }

  public int getUpstreamTimeout() {
    return intProperty("upstreamTimeout", 5000);
  }

  public void setApiKey(String apiKey) {
    properties.setProperty("apiKey", apiKey);
  }

  public void setProperty(String property, String value) {
    properties.setProperty(property, value);
  }

}
