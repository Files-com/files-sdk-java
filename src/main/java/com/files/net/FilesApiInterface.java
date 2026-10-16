package com.files.net;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.ListIterator;
import com.files.util.FilesInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * The static request methods of {@link com.files.FilesClient} as an interface. The SDK's own
 * implementation, {@link FilesApacheHttpApi}, sends each request with
 * {@link com.files.FilesClient#defaultClient()}, as the static method of the same purpose does.
 * Each method below says what it is for and, where an implementation could differ, what
 * {@code FilesApacheHttpApi} does.
 */
public interface FilesApiInterface {
  /**
   * Sends one API request and returns its response.
   *
   * <p>{@link FilesApacheHttpApi} sends it as {@link com.files.FilesClient#apiRequest} does:
   * {@code parameters} as the query string for {@code GET}, {@code HEAD} and {@code DELETE}, and as
   * the body for {@code POST}, {@code PUT} and {@code PATCH}; it throws
   * {@code UnsupportedOperationException} for {@code OPTIONS} and {@code TRACE}. It reads the body
   * as UTF-8 text, or as an empty string when there is none.
   *
   * @param url a path under the API root, such as {@code /users}, or an absolute URL
   * @param requestType the HTTP method
   * @param parameters the request parameters, or {@code null} for none
   * @param options request options, as {@link com.files.FilesClient#apiRequest} describes
   * @return the response's status code, headers and body
   * @throws RuntimeException if the request fails, as {@link com.files.FilesClient#apiRequest}
   *     describes
   */
  public FilesResponse apiRequest(String url, HttpMethods.RequestMethods requestType, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException;

  /**
   * Returns a {@link ListIterator} for a listing request. {@link FilesApacheHttpApi} only creates
   * the iterator and sends nothing; each page is requested, as {@link #apiRequest} sends it, when
   * the iterator loads that page.
   *
   * @param <T> the type of each listed item
   * @param url a path under the API root, as for {@link #apiRequest}
   * @param requestType the HTTP method
   * @param className the type each page's JSON array is read as
   * @param parameters the request parameters; {@code FilesApacheHttpApi}'s iterator keeps a copy
   * @param options request options, sent with each page request
   * @return an iterator that has not loaded any page
   * @throws RuntimeException if the iterator cannot be created; {@code FilesApacheHttpApi} sends
   *     no request here, so request failures surface later, when the iterator loads a page
   */
  public <T> ListIterator<T> apiRequestList(String url, HttpMethods.RequestMethods requestType, TypeReference<List<T>> className,
      HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException;

  /**
   * Sends one API request, as {@link #apiRequest} does, and reads the response body as a
   * {@code T}. {@link FilesApacheHttpApi} returns it as {@link com.files.FilesClient#requestItem}
   * does: a model keeps the default client and the request options.
   *
   * @param <T> the type the response body is read as
   * @param url a path under the API root, as for {@link #apiRequest}
   * @param requestType the HTTP method
   * @param className the type the response body is read as
   * @param parameters the request parameters, or {@code null} for none
   * @param options request options, as for {@link #apiRequest}
   * @return the response body read as a {@code T}
   * @throws RuntimeException if the request fails, as for {@link #apiRequest}, or the body cannot
   *     be read as a {@code T}
   */
  public <T> T apiRequestItem(String url, HttpMethods.RequestMethods requestType, TypeReference<T> className,
      HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException;

  /**
   * Requests file content from {@code url} and returns a stream of the response body.
   *
   * <p>{@link FilesApacheHttpApi} sends a GET to {@code url}, as
   * {@link com.files.FilesClient#getFileInputStream} does: without credentials, workspace or
   * language, and without using {@code start} or {@code end}, so it requests the whole resource.
   * The caller closes the returned stream.
   *
   * @param url the full URL of the content, such as a download URL the API returned
   * @param start a start position; not used by {@code FilesApacheHttpApi}
   * @param end an end position; not used by {@code FilesApacheHttpApi}
   * @return a stream of the response body
   * @throws IOException if the request fails
   */
  public FilesInputStream getFileInputStream(String url, long start, long end) throws IOException;

  /**
   * Uploads the start of a buffer to {@code url}.
   *
   * <p>{@link FilesApacheHttpApi} uploads the first {@code length} bytes of {@code buffer} as
   * {@link com.files.FilesClient#putBuffer} does: with {@code POST} or {@code PATCH} when
   * {@code requestType} is one of those and {@code PUT} otherwise, without credentials, workspace
   * or language, and retrying a failure that may be temporary. It does not use {@code name}. It
   * returns the {@code length} argument as given, not a count of bytes it measured. It expects
   * {@code length} to be from 0 to {@code buffer.length} and promises nothing about what is
   * uploaded for other values.
   *
   * @param url the full URL to upload to, such as an upload URL the API returned
   * @param requestType the HTTP method: {@code POST}, {@code PATCH}, or otherwise {@code PUT}
   * @param name a name for the upload; not used by {@code FilesApacheHttpApi}
   * @param buffer the bytes to upload
   * @param length how many bytes from the start of {@code buffer} to upload, from 0 to
   *     {@code buffer.length}
   * @return the {@code length} argument, as {@code FilesApacheHttpApi} returns it
   * @throws IOException if the request fails
   */
  public long putBuffer(String url, HttpMethods.RequestMethods requestType, String name, byte[] buffer, long length) throws IOException;

  /**
   * Uploads bytes read from a stream to {@code url}.
   *
   * <p>{@link FilesApacheHttpApi} uploads {@code inputStream} as the body of one request with the
   * declared {@code length}, as {@link com.files.FilesClient#putBufferedInputStream} does: with
   * {@code POST} or {@code PATCH} when {@code requestType} is one of those and {@code PUT}
   * otherwise, without credentials, workspace or language, and never sent again. It does not use
   * {@code name}, and returns 0 rather than the number of bytes sent. It does not itself check that
   * {@code inputStream} holds {@code length} bytes, and it does not promise whether
   * {@code inputStream} is still open afterwards.
   *
   * @param url the full URL to upload to, such as an upload URL the API returned
   * @param requestType the HTTP method: {@code POST}, {@code PATCH}, or otherwise {@code PUT}
   * @param name a name for the upload; not used by {@code FilesApacheHttpApi}
   * @param inputStream the stream the request body is read from
   * @param length the declared length of the body, in bytes
   * @return the number of bytes sent, for an implementation that reports it;
   *     {@code FilesApacheHttpApi} returns 0
   * @throws IOException if reading {@code inputStream} or the request fails
   */
  public long putBufferedInputStream(String url, HttpMethods.RequestMethods requestType, String name, BufferedInputStream inputStream, long length) throws IOException;
}
