package com.files.net;

import java.util.List;
import org.apache.http.Header;

/**
 * An HTTP response with its body read as text, as {@link FilesApiInterface#apiRequest} returns
 * it. It holds the values it was created with, without checking or copying them.
 */
public class FilesResponse {
  private final int statusCode;
  private final List<Header> headers;
  private final String body;

  /**
   * Creates a response that holds these values as given.
   *
   * @param statusCode the HTTP status code
   * @param headers the response headers; the list is kept, not copied
   * @param body the response body as text
   */
  public FilesResponse(int statusCode, List<Header> headers, String body) {
    this.statusCode = statusCode;
    this.headers = headers;
    this.body = body;
  }

  /**
   * Returns the HTTP status code.
   *
   * @return the status code this response was created with
   */
  public int getStatusCode() {
    return this.statusCode;
  }

  /**
   * Returns the response headers.
   *
   * @return the list this response was created with, not a copy
   */
  public List<Header> getHeaders() {
    return this.headers;
  }

  /**
   * Returns the response body as text. In a response from {@link FilesApacheHttpApi}, it is the
   * body decoded as UTF-8, or an empty string when the response had none.
   *
   * @return the body this response was created with
   */
  public String getBody() {
    return this.body;
  }
}
