package com.files.net;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilesResponse {
  private final int statusCode;
  private final Map<String, List<String>> headers;
  private final String body;

  public FilesResponse(int statusCode, Map<String, List<String>> headers, String body) {
    this.statusCode = statusCode;
    this.headers = headers;
    this.body = body;
  }

  public int getStatusCode() {
    return this.statusCode;
  }

  public Map<String, List<String>> getHeaders() {
    return this.headers;
  }

  public String getBody() {
    return this.body;
  }
}
