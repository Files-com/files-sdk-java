package com.files.net;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.Header;

public class FilesResponse {
  private final int statusCode;
  private final List<Header> headers;
  private final String body;

  public FilesResponse(int statusCode, List<Header> headers, String body) {
    this.statusCode = statusCode;
    this.headers = headers;
    this.body = body;
  }

  public int getStatusCode() {
    return this.statusCode;
  }

  public List<Header> getHeaders() {
    return this.headers;
  }

  public String getBody() {
    return this.body;
  }
}
