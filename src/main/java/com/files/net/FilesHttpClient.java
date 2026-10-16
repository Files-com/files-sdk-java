package com.files.net;

/**
 * The executor of the default client's connections. Kept for compatibility; use a
 * {@link com.files.FilesClient}, and {@link HttpTransport} for its connections.
 */
public class FilesHttpClient {
  private static final FilesHttpClient instance = new FilesHttpClient();
  protected FilesHttpExecutor httpExecutor = new FilesHttpExecutor();

  protected FilesHttpClient() {
  }

  public static FilesHttpClient getInstance() {
    return instance;
  }

  public FilesHttpExecutor getHttpExecutor() {
    return httpExecutor;
  }
}
