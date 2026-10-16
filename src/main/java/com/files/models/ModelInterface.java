package com.files.models;

import com.files.FilesClient;
import java.util.HashMap;

/**
 * A model that keeps the client and the request options it came with. The SDK calls
 * {@link #setClient(FilesClient)} and {@link #setOptions(HashMap)} on each model a request
 * returns, such as the item from {@link com.files.FilesClient#requestItem} or each item of a page
 * that {@link com.files.ListIterator#loadNextPage()} loads. It passes the client that sent the
 * request and the options it was sent with, for example a per-request API key, session, workspace
 * or API root.
 *
 * <p>The SDK's generated models implement it by keeping a copy of the map, without the options
 * that controlled only one call ({@code "cancellation"}, {@code "timeout"} and
 * {@code "max_retries"}), replacing any options given to their constructor, and they send their
 * own later requests, such as updates or deletes, with it. Another implementation decides what to
 * do with the map: the interface itself promises nothing about copying, thread safety or
 * {@code null}.
 */
public interface ModelInterface {
  /**
   * Gives the model the request options to use for its own later requests. The SDK passes the
   * options of the request that returned the model, and a caller can also pass its own.
   *
   * @param options the request options, such as {@code "api_key"}, {@code "session_id"},
   *     {@code "workspace_id"} or {@code "api_root"}; the generated models keep a copy, and an
   *     empty map for {@code null}
   */
  public void setOptions(HashMap<String, Object> options);

  /**
   * Makes this object's own operations, such as {@code save()} or {@code update()}, run with the
   * given client. An object a client returns already uses that client; one you construct uses
   * {@link FilesClient#defaultClient()} until this is called.
   *
   * @param client the client to run this object's operations with
   */
  default void setClient(FilesClient client) {
  }
}
