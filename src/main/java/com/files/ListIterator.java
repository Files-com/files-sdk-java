package com.files;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.files.models.ModelInterface;
import com.files.net.FilesResponse;
import com.files.net.HttpMethods.RequestMethods;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.apache.http.Header;

/**
 * The results of one listing request, read one page at a time using a cursor.
 *
 * <p>A {@code ListIterator} returned by a model's list method has not sent any request yet: its
 * {@link #data} is empty, and iterating it directly yields nothing. There are three ways to read
 * the results. {@link #loadNextPage()} loads one page at a time into {@link #data}, and
 * {@link #hasNextPage()} reports whether another page can be requested. {@link #listAutoPaging()}
 * iterates every item across all pages, loading each page when it is needed. {@link #all()} loads
 * every page and returns all items in a single list held in memory.
 *
 * <p>A {@code ListIterator} keeps mutable state: the most recently loaded page, the cursor for the
 * next page, and the request parameters map, into which each page load that uses a cursor puts a
 * {@code cursor} entry.
 *
 * @param <T> the type of each listed item, typically a model class
 */
public class ListIterator<T> implements Iterable<T> {
  private final String url;
  private final RequestMethods requestType;
  private final TypeReference<List<T>> className;
  private final HashMap<String, Object> parameters;
  private final HashMap<String, Object> options;
  private String cursor;
  private final ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();
  /**
   * The items of the most recently loaded page.
   *
   * <p>Empty until a page is loaded. Each page load replaces this list with the new page's items;
   * items are not appended to it.
   */
  public List<T> data = new ArrayList<T>();

  /**
   * Creates a {@code ListIterator} for one listing request. This only stores the arguments; it
   * does not send a request. Model list methods create instances through {@link FilesClient}.
   *
   * @param url the full URL of the listing request
   * @param requestType the HTTP method of the listing request
   * @param className the Jackson type reference used to read a page's JSON array into a
   *     {@code List} of {@code T}
   * @param parameters the request parameters sent with each page request. The map is kept, not
   *     copied, and {@link #loadNextPage()} adds a {@code cursor} entry to it when paging. A
   *     {@code cursor} entry it already has is kept and sent with the first page request.
   * @param options the request options, such as a per-request API key or session, passed to each
   *     page request and set on every loaded item that is a {@link ModelInterface}
   */
  public ListIterator(String url, RequestMethods requestType, TypeReference<List<T>> className,
      HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.url = url;
    this.requestType = requestType;
    this.className = className;
    this.parameters = parameters;
    this.options = options;
  }

  /**
   * Reports whether another page can be requested.
   *
   * <p>Returns {@code true} when the most recently loaded page's response carried an
   * {@code X-Files-Cursor} header (the header name is matched case-insensitively). Returns
   * {@code false} before any page has been loaded and after the last page has been loaded.
   *
   * @return {@code true} if another page can be loaded with {@link #loadNextPage()}, otherwise
   *     {@code false}; never {@code null}
   */
  public Boolean hasNextPage() {
    return this.cursor != null;
  }

  /**
   * Returns an iterator over the currently loaded page only, that is, the items in {@link #data}.
   *
   * <p>This sends no request and yields nothing before a page has been loaded. To iterate every
   * item across all pages, use {@link #listAutoPaging()}; to collect them into one list, use
   * {@link #all()}.
   *
   * @return an iterator over the items of the currently loaded page
   */
  @Override
  public Iterator<T> iterator() {
    return this.data.iterator();
  }

  /**
   * Sends one request and loads the resulting page into {@link #data}.
   *
   * <p>If a cursor is held from the previous page, it is first put into the parameters map under
   * the key {@code cursor}. The request is then sent through {@link FilesClient} with the
   * parameters and options. The page read from the response body replaces {@link #data}, and the
   * options are set on every loaded item that is a {@link ModelInterface}. Finally, the value of
   * the response's {@code X-Files-Cursor} header is held as the cursor for the next page; if the
   * header is absent, no cursor is held and {@link #hasNextPage()} returns {@code false}.
   *
   * <p>When no cursor is held, no {@code cursor} entry is added to or removed from the parameters
   * map, so the request is sent with the parameters as they are, including any {@code cursor}
   * entry the caller supplied. On a new {@code ListIterator} whose parameters have no
   * {@code cursor} entry, this loads the first page. After the last page has been loaded, calling
   * this again repeats the previous request.
   *
   * @return this {@code ListIterator}, for chaining
   * @throws RuntimeException if the response body cannot be read as a list of {@code T}; the
   *     exception wraps the Jackson {@code JsonProcessingException}. Failures of the request
   *     itself propagate from {@link FilesClient} as unchecked exceptions.
   */
  public ListIterator<T> loadNextPage() throws RuntimeException {
    if (this.cursor != null) {
      this.parameters.put("cursor", this.cursor);
    }

    FilesResponse response = FilesClient.apiRequest(this.url, this.requestType, this.parameters, this.options);
    try {
      this.data = objectMapper.readValue(response.getBody(), this.className);
      this.data.forEach(item -> {
        if (item instanceof ModelInterface) {
          ((ModelInterface) item).setOptions(this.options);
        }
      });
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    Optional<Header> cursorHeader = response.getHeaders().stream()
        .filter(header -> "X-Files-Cursor".equalsIgnoreCase(header.getName()))
        .findFirst();

    this.cursor = cursorHeader.map(Header::getValue).orElse(null);

    return this;
  }

  /**
   * Returns a {@link ListIteratorIterable} over every item across all pages, loading pages as
   * they are needed.
   *
   * <p>Calling this method sends a request immediately, because the returned
   * {@link ListIteratorIterable} calls {@link #loadNextPage()} when it is created. On a new
   * {@code ListIterator} whose parameters have no {@code cursor} entry, this loads the first page;
   * a {@code cursor} entry the caller supplied is sent as is. If this {@code ListIterator} already
   * holds a cursor, it loads the next page.
   *
   * <p>Its iterator returns the loaded page's items and, when they run out and a cursor is held,
   * calls {@link #loadNextPage()} to load the next page, so only one page is held in memory at a
   * time. Its {@code hasNext()} returns {@code true} while a cursor is held or items remain in
   * the loaded page. When no items remain and no cursor is held, its {@code next()} throws
   * {@code java.util.NoSuchElementException}. A page that carried a cursor can be followed by an
   * empty page: {@code hasNext()} then returns {@code true}, and {@code next()} loads the empty
   * page and throws {@code IndexOutOfBoundsException}.
   *
   * <p>The returned object shares this {@code ListIterator}'s state: iterating it replaces
   * {@link #data} and changes the held cursor, and iterating it a second time does not restart
   * from the first page. Page-loading failures, both from this call and during iteration, are
   * the unchecked exceptions that {@link #loadNextPage()} throws.
   *
   * @return a {@link ListIteratorIterable} over this listing's items, with one page already
   *     loaded
   */
  public ListIteratorIterable<T> listAutoPaging() {
    return new ListIteratorIterable<T>(this);
  }

  /**
   * Loads every page and returns all items in a single list.
   *
   * <p>This clears the held cursor, then calls {@link #loadNextPage()} until a response
   * carries no cursor, adding each page's items to a new list. The returned list holds every item
   * in memory at once, so memory use grows with the number of items. Afterwards, {@link #data}
   * holds the last page and {@link #hasNextPage()} returns {@code false}.
   *
   * <p>Clearing the held cursor does not remove a {@code cursor} entry from the parameters map,
   * whether the caller supplied it or an earlier page load put it there. The first request this
   * method sends carries that entry, so it starts from that cursor rather than the first page. To
   * read from the first page, call this method while the parameters map has no {@code cursor}
   * entry: on a {@code ListIterator} that has not paged and was created with parameters that have
   * none, or after removing the entry from the map that was passed to the list method.
   *
   * @return a new list containing the items of every loaded page
   * @throws RuntimeException if a page cannot be loaded, as described for {@link #loadNextPage()}
   */
  public List<T> all() throws RuntimeException {
    List<T> allData = new ArrayList<T>();

    // Force starting from the beginning
    this.cursor = null;

    do {
      this.loadNextPage();
      allData.addAll(data);
    } while (this.cursor != null);

    return allData;
  }
}
