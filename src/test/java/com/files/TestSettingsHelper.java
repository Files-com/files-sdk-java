package com.files;

import java.util.Optional;

public class TestSettingsHelper {
  private static TestSettingsHelper instance = null;
  private String hostName;
  private String port;

  protected TestSettingsHelper() {
    hostName = Optional.ofNullable(System.getenv("FILES_MOCK_SERVER_HOST")).orElse("").toLowerCase();
    port = Optional.ofNullable(System.getenv("FILES_MOCK_SERVER_PORT")).orElse("").toLowerCase();
    if (hostName == null || hostName.equals("null") || hostName.isEmpty()) {
      hostName = "localhost";
    }
    if (port == null || port.equals("0") || port.equals("null") || port.isEmpty()) {
      port = "4041";
    }
  }

  public static TestSettingsHelper getInstance() {
    if (instance == null) {
      synchronized (TestSettingsHelper.class) {
        if (instance == null) {
          instance = new TestSettingsHelper();
        }
      }
    }
    return instance;
  }

  public String getMockServerApiRoot() {
    return "http://" + hostName + ":" + port;
  }
}
