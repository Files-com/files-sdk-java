package com.files.net;

/**
 * Holds {@link RequestMethods}, the HTTP method selector the SDK's request methods take.
 */
public class HttpMethods {
  /**
   * The HTTP method of a request. {@link FilesApacheHttpApi#apiRequest} sends {@code GET},
   * {@code HEAD} and {@code DELETE} with parameters in the query string, and {@code POST},
   * {@code PUT} and {@code PATCH} with a JSON body; it throws
   * {@code UnsupportedOperationException} for {@code OPTIONS} and {@code TRACE}. The upload methods
   * of {@link FilesApacheHttpApi} always send a PUT, whatever method they are given.
   */
  public enum RequestMethods {
    GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;
  }
}
