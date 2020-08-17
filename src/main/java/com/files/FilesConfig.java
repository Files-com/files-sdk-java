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
  private static volatile FilesConfig instance = null;
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

  public String getApiBase() {
    return properties.getProperty("apiBase", String.format("/api/rest/v1"));
  }

  public String getApiRoot() {
    return properties.getProperty("apiRoot", "https://app.files.com");
  }

  public String getApiKey() {
    return properties.getProperty("apiKey", null);
  }

  public int getCachedBufferTinySize() {
    return intProperty("cachedBufferTinySize", 1024 * 16);
  }

  public int getCachedBufferSmallSize() {
    return intProperty("cachedBufferSmallSize", 1024 * 1024 /* 1 MB */);
  }

  public int getCachedBufferMediumSize() {
    return intProperty("cachedBufferMediumSize", 16777216 /* 16 MB */);
  }

  public int getCachedBufferLargeSize() {
    // must be at least the size of the largest requested upload page
    return intProperty("cachedBufferLargeSize", 50331648 /* 48 MB */);
  }
  public int getCachedBufferMaxBytes() {
    return intProperty("cachedBufferMaxBytes", (int) Math.min(Runtime.getRuntime().maxMemory() / 5,
            1610612736 /* 1.5 GB */));
  }

  public boolean getHttpLoggingEnabled() {
    return boolProperty("httpLoggingEnabled", true);
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

  public void setProperty(String property, String value) {
    properties.setProperty(property, value);
  }

}
