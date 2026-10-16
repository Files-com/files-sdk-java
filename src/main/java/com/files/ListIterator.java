package com.files;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.files.exceptions.ApiErrorException;
import com.files.net.FilesResponse;
import com.files.net.HttpMethods.RequestMethods;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.http.Header;

/**
 * A listing that the Files.com API returns one page at a time. Read it in whichever way suits the
 * task:
 *
 * <ul>
 *   <li>{@link #listAutoPaging()} iterates every remaining item, requesting pages as it goes and
 *       holding only one page in memory.</li>
 *   <li>{@link #all()} returns the complete listing as one {@code List}.</li>
 *   <li>{@link #loadNextPage()} requests one page at a time. {@link #iterator()} and {@link #data}
 *       hold that current page, and {@link #hasNextPage()} tells whether another page follows.</li>
 * </ul>
 *
 * <p>{@link #reset()} starts the listing over from its first page with the same filters and
 * options. Every page is requested with the client that made the listing and a copy of the
 * filters and request options it was made with, so later changes to the maps you passed, or to
 * static settings, do not change it. Its items use that client too. A listing is not safe for use
 * by several threads at once.
 *
 * @param <T> the type of the listed items
 */
public class ListIterator<T> implements Iterable<T> {
  private final FilesClient client;
  private final String url;
  private final RequestMethods requestType;
  private final TypeReference<List<T>> className;
  private final HashMap<String, Object> parameters;
  private final HashMap<String, Object> options;
  private final ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();
  private boolean started;
  private String cursor;
  // Counts page loads and resets so that auto-paging iterators can tell when the listing has moved
  // without them, like modCount in java.util collections.
  int modCount;

  /**
   * The items of the current page.
   */
  public List<T> data = new ArrayList<T>();

  /**
   * A listing requested with {@link FilesClient#defaultClient()} as it is now.
   */
  public ListIterator(String url, RequestMethods requestType, TypeReference<List<T>> className,
      HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this(FilesClient.defaultClient(), url, requestType, className, parameters, options);
  }

  // url is a path under the client's API root, or an absolute URL.
  ListIterator(FilesClient client, String url, RequestMethods requestType, TypeReference<List<T>> className,
      HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.client = client;
    this.url = url;
    this.requestType = requestType;
    this.className = className;
    this.parameters = parameters == null ? new HashMap<String, Object>() : new HashMap<String, Object>(parameters);
    this.options = options == null ? new HashMap<String, Object>() : new HashMap<String, Object>(options);
  }

  /**
   * Returns whether the current page says another page follows it: its response carried a cursor
   * in the {@code X-Files-Cursor-Next} header or, without one, the older {@code X-Files-Cursor}
   * header.
   */
  public Boolean hasNextPage() {
    return this.cursor != null;
  }

  /**
   * Iterates the items of the current page only. Use {@link #listAutoPaging()} to iterate every
   * item.
   */
  @Override
  public Iterator<T> iterator() {
    return this.data.iterator();
  }

  /**
   * Requests the next page, or the first page of a new or reset listing, and makes it the current
   * page. If the request fails, the listing keeps its current page and can be retried.
   *
   * @return this listing
   * @throws NoSuchElementException if the last page has already been loaded
   */
  public ListIterator<T> loadNextPage() throws RuntimeException {
    if (!hasMorePages()) {
      throw new NoSuchElementException("The listing has no more pages. Call reset() to list it again.");
    }

    HashMap<String, Object> request = new HashMap<String, Object>(this.parameters);
    if (this.cursor != null) {
      request.put("cursor", this.cursor);
    }

    FilesResponse response = client.runtime().request(this.requestType, this.url, request, this.options);
    List<T> page = readPage(response.getBody());
    String nextCursor = nextCursor(response);
    // Every page request with the same cursor returns the same empty page, so following it would never end.
    if (page.isEmpty() && nextCursor != null && nextCursor.equals(request.get("cursor"))) {
      throw new ApiErrorException.ApiConnectionException(
          "The Files.com API returned an empty page that repeats the requested cursor");
    }

    for (T item : page) {
      client.runtime().bind(item, this.options);
    }
    this.data = page;
    this.cursor = nextCursor;
    this.started = true;
    this.modCount++;
    return this;
  }

  /**
   * Returns every remaining item, requesting each page only when iteration reaches it. A new or
   * reset listing yields every item; after {@link #loadNextPage()}, iteration continues with the
   * pages that follow the current one.
   *
   * <p>Loading pages or calling {@link #reset()} outside the returned iterator makes that iterator
   * throw {@link java.util.ConcurrentModificationException}.
   */
  public ListIteratorIterable<T> listAutoPaging() {
    return new ListIteratorIterable<T>(this);
  }

  /**
   * Resets the listing and returns all of its items in one list.
   */
  public List<T> all() throws RuntimeException {
    List<T> items = new ArrayList<T>();
    for (T item : reset().listAutoPaging()) {
      items.add(item);
    }
    return items;
  }

  /**
   * Starts the listing over: the current page is cleared and the next request asks for the first
   * page with the original filters, page size, and options.
   *
   * @return this listing
   */
  public ListIterator<T> reset() {
    this.parameters.remove("cursor");
    this.data = new ArrayList<T>();
    this.cursor = null;
    this.started = false;
    this.modCount++;
    return this;
  }

  boolean hasMorePages() {
    return !this.started || this.cursor != null;
  }

  // The whole page is checked before it replaces the current one, so a bad page publishes nothing.
  private List<T> readPage(String body) {
    List<T> page;
    try {
      page = objectMapper.readValue(body, this.className);
    } catch (JsonProcessingException e) {
      page = null;
    }
    if (page == null || page.contains(null)) {
      throw new ApiErrorException.ApiConnectionException(
          "The Files.com API returned a list page that is not a JSON array of objects");
    }
    return page;
  }

  // The cursor of the next page: the response's X-Files-Cursor-Next header, the documented one, or
  // else the older X-Files-Cursor, which responses that send only it still carry. Header names are
  // matched ignoring case, and an empty value is no cursor.
  private static String nextCursor(FilesResponse response) {
    String cursor = header(response, "X-Files-Cursor-Next");
    return cursor != null ? cursor : header(response, "X-Files-Cursor");
  }

  private static String header(FilesResponse response, String name) {
    for (Header header : response.getHeaders()) {
      String value = header.getValue();
      if (name.equalsIgnoreCase(header.getName()) && value != null && !value.isEmpty()) {
        return value;
      }
    }
    return null;
  }
}
