package com.files.exceptions;

import com.files.ResponseError;
import com.files.util.UrlUtils;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.http.Header;

/**
 * An error response from the Files.com API. When a request fails with an HTTP error status, and
 * its body parses as a JSON error whose type {@link #forType(String, ResponseError, List)} can
 * read, the SDK throws the {@code ApiErrorException} that {@code forType} chooses for that type.
 * Its message is {@code HTTP returned status} followed by the status code; the API's own
 * explanation is in {@link #getError()}, {@link #getTitle()} and the rest of
 * {@link #getResponseError()}.
 *
 * <p>Errors are classified by the error type in the response, never by the HTTP status alone. An
 * error response without a structured error, one whose body is not a JSON error object or cannot
 * be read, raises {@link ApiConnectionException} instead, whatever its status. A structured error
 * whose type names no class of the SDK's, or is missing or malformed, raises a plain
 * {@code ApiErrorException} that still carries the response's details and headers.
 *
 * <p>The SDK generates a nested subclass for each error type the API declares, so callers can
 * catch one error or a family of them. A type with a family, such as
 * {@code not-authorized/read-permission-required}, has a family class
 * ({@code NotAuthorizedException}) and a subclass of it named for the last segment
 * ({@code ReadPermissionRequiredException}); a type without a family has a class of its own.
 * Catch the specific class, then its family class, then {@code ApiErrorException} or
 * {@link SdkException}.
 *
 * <p>The other nested classes, {@code ApiConnectionException}, {@code AuthenticationException} and
 * {@code InvalidParameterException}, extend {@link SdkException} directly rather than this class:
 * the SDK throws them for failures without a structured API error, as each describes.
 * {@code InvalidResponseException} and {@code ServerErrorException} are no longer thrown.
 */
public class ApiErrorException extends SdkException {
  private static final long serialVersionUID = -7605776674523623420L;

  // The last segment of an error type such as "processing-failure/model-save-error".
  private static final Pattern ERROR_NAME = Pattern.compile("[a-z0-9]+(-[a-z0-9]+)*");

  /**
   * Builds the exception for a structured error response. The response's type selects the matching
   * exception class; an unrecognized, missing, or malformed type produces a plain
   * ApiErrorException that still carries the response's details and headers.
   */
  public static ApiErrorException forType(String msg, ResponseError responseError, List<Header> headers) {
    Class<? extends ApiErrorException> errorClass = errorClassFor(responseError.type);
    if (errorClass != null) {
      try {
        return errorClass.getConstructor(String.class, ResponseError.class, List.class)
            .newInstance(msg, responseError, headers);
      } catch (ReflectiveOperationException e) {
        // Fall back to the generic API error below.
      }
    }
    return new ApiErrorException(msg, responseError, headers);
  }

  private static Class<? extends ApiErrorException> errorClassFor(String type) {
    if (type == null) {
      return null;
    }
    String[] segments = type.split("/");
    String errorName = segments.length == 0 ? "" : segments[segments.length - 1].toLowerCase(Locale.ROOT);
    if (!ERROR_NAME.matcher(errorName).matches()) {
      return null;
    }

    StringBuilder className = new StringBuilder(ApiErrorException.class.getName()).append('$');
    for (String word : errorName.split("-")) {
      className.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
    }
    className.append("Exception");

    try {
      Class<?> errorClass = Class.forName(className.toString(), false, ApiErrorException.class.getClassLoader());
      return ApiErrorException.class.isAssignableFrom(errorClass) ? errorClass.asSubclass(ApiErrorException.class) : null;
    } catch (ClassNotFoundException | NoClassDefFoundError e) {
      // NoClassDefFoundError: a class directory on a case-insensitive file system can match a name
      // that differs only in case.
      return null;
    }
  }

  private final ResponseError responseError;
  private final List<Header> headers;

  private ApiErrorException(String message, ResponseError responseError, List<Header> headers) {
    super(message);
    this.responseError = responseError;
    this.headers = headers;
  }

  /**
   * Returns the parsed error body, the object this exception was created with rather than a copy.
   * It also holds {@link ResponseError#modelErrorKeys}, which has no accessor here.
   *
   * @return the error body
   */
  public ResponseError getResponseError() {
    return responseError;
  }

  /**
   * Returns the headers of the HTTP response that carried the error, the list this exception was
   * created with rather than a copy.
   *
   * @return the response headers
   */
  public List<Header> getHeaders() {
    return headers;
  }

  /**
   * Returns the error body's {@link ResponseError#detail}.
   *
   * @return further detail about the error, or {@code null} if the body has none
   */
  public String getDetail() {
    return responseError.detail;
  }

  /**
   * Returns the error body's {@link ResponseError#error}.
   *
   * @return the API's error message, or {@code null} if the body has none
   */
  public String getError() {
    return responseError.error;
  }

  /**
   * Returns the error body's {@link ResponseError#errors}.
   *
   * @return readable messages about a failed create or update, or {@code null} if the body has none
   */
  public String[] getErrors() {
    return responseError.errors;
  }

  /**
   * Returns the error body's {@link ResponseError#httpCode}, the status the body reports.
   *
   * @return the body's {@code http-code} value, or 0 if the body has none
   */
  public int getHttpCode() {
    return responseError.httpCode;
  }

  /**
   * Returns the error body's {@link ResponseError#instance}.
   *
   * @return the identifier of this occurrence of the error, or {@code null} if the body has none
   */
  public String instance() {
    return responseError.instance;
  }

  /**
   * Returns the error body's {@link ResponseError#modelErrors}, the body's own map rather than a
   * copy.
   *
   * @return messages keyed by the parameter that caused them, or {@code null} if the body has none
   */
  public Map<String, String[]> modelErrors() {
    return responseError.modelErrors;
  }

  /**
   * Returns the error body's {@link ResponseError#title}.
   *
   * @return a short title for the error, or {@code null} if the body has none
   */
  public String getTitle() {
    return responseError.title;
  }

  /**
   * Returns the error body's {@link ResponseError#type}, which chose this exception's class.
   *
   * @return the error type, such as {@code not-authorized/read-permission-required}
   */
  public String getType() {
    return responseError.type;
  }

  /**
   * Returns the error body's {@link ResponseError#data}, the body's own map rather than a copy.
   *
   * @return additional data about the error, or {@code null} if the body has none
   */
  public Map<String, Object> getData() {
    return responseError.data;
  }

  /**
   * Describes the error by its type and the server's error text, with credentials, queries, and
   * fragments removed from any URL in it. {@link #getError()} returns the text unchanged.
   */
  @Override
  public String toString() {
    return UrlUtils.redactUrls("[" + getType() + "] " + getError());
  }

  /**
   * No reliable response came back from the Files.com API: the request could not be completed, or
   * the response had no structured error or could not be decoded. When a request's last attempt
   * fails, or its response cannot be read, the message names the kind of failure, never the
   * failure's own text, followed by the request's method and path, as in
   * {@code (response to GET /api/rest/v1/groups)}, without its query or host.
   */
  public static class ApiConnectionException extends SdkException {
    /**
     * Creates the exception.
     *
     * @param message the reason
     */
    public ApiConnectionException(String message) {
      super(message);
    }
  }

  /**
   * Thrown when a request has no credentials to send (see {@link com.files.FilesClient}). A 403
   * response raises the exception for its error type, or {@link ApiConnectionException} without a
   * structured error.
   */
  public static class AuthenticationException extends SdkException {
    private final List<Header> headers;

    /**
     * Creates the exception.
     *
     * @param message the reason
     * @param headers the response headers, or {@code null} when no request was sent
     */
    public AuthenticationException(String message, List<Header> headers) {
      super(message);
      this.headers = headers;
    }

    /**
     * Returns the response headers this exception was created with.
     *
     * @return the response headers, or {@code null} when no request was sent
     */
    public List<Header> getHeaders() {
      return headers;
    }
  }

  /**
   * Thrown before a request is sent when one of its options has the wrong type, such as an
   * {@code api_key}, {@code session_id} or {@code api_root} option that is not a {@code String}.
   */
  public static class InvalidParameterException extends SdkException {
    /**
     * Creates the exception.
     *
     * @param message which option is wrong and why
     */
    public InvalidParameterException(String message) {
      super(message);
    }
  }

  /**
   * No longer thrown. A response without a structured error, or one that cannot be decoded, raises
   * {@link ApiConnectionException}.
   */
  @Deprecated
  public static class InvalidResponseException extends SdkException {
    /**
     * Creates the exception.
     *
     * @param message the reason
     */
    public InvalidResponseException(String message) {
      super(message);
    }
  }

  /**
   * No longer thrown. A 5xx response with a structured error raises the exception for its error
   * type, like any other status; one without a structured error raises
   * {@link ApiConnectionException}.
   */
  @Deprecated
  public static class ServerErrorException extends SdkException {
    /**
     * Creates the exception.
     *
     * @param message the reason
     */
    public ServerErrorException(String message) {
      super(message);
    }
  }

  /**
   * The plain-text answer older Files.com servers give, with HTTP 403, when a site must be reached
   * through another server: a body beginning {@code You have connected to a URL}, with one
   * {@code X-Files-Host} header naming the site's host. The client follows it as its
   * {@link com.files.RegionRelocationPolicy} allows, and throws this when it does not, such as when
   * the policy declines or no retry is left. Only such an answer from the API root the request was
   * sent to raises it; from any other server, the answer raises a plain
   * {@link ApiConnectionException}. Its message is the one any response without a structured error
   * has, and code that catches {@link ApiConnectionException} still catches it.
   */
  public static class LegacyRegionMismatchException extends ApiConnectionException {
    private final String host;
    private final List<Header> headers;

    /**
     * Creates the exception.
     *
     * @param message the exception message
     * @param host the host the response's {@code X-Files-Host} header names
     * @param headers the HTTP response headers
     */
    public LegacyRegionMismatchException(String message, String host, List<Header> headers) {
      super(message);
      this.host = host;
      this.headers = headers;
    }

    /**
     * Returns the host the response's {@code X-Files-Host} header named for the site, exactly as
     * the server sent it.
     *
     * @return the site's host, such as {@code mysite.files.com}
     */
    public String getHost() {
      return host;
    }

    /**
     * Returns the headers of the HTTP response, the list this exception was created with rather
     * than a copy.
     *
     * @return the response headers
     */
    public List<Header> getHeaders() {
      return headers;
    }
  }

  /**
   * A family of API error types. Each type in the family has a subclass of this class, so catching
   * it handles any of them.
   */
  public static class BadRequestException extends ApiErrorException {
    /**
     * Creates the exception.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public BadRequestException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class AgentUpgradeRequiredException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public AgentUpgradeRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class AttachmentTooLargeException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public AttachmentTooLargeException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class CannotDownloadDirectoryException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public CannotDownloadDirectoryException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class CantMoveWithMultipleLocationsException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public CantMoveWithMultipleLocationsException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class DatetimeParseException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public DatetimeParseException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class DestinationSameException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public DestinationSameException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class DestinationSiteMismatchException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public DestinationSiteMismatchException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class DoesNotSupportSortingException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public DoesNotSupportSortingException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class FolderMustNotBeAFileException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public FolderMustNotBeAFileException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class FoldersNotAllowedException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public FoldersNotAllowedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InternalGeneralErrorException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InternalGeneralErrorException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidBodyException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidBodyException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidCursorException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidCursorException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidCursorTypeForSortException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidCursorTypeForSortException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidEtagsException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidEtagsException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidFilterAliasCombinationException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidFilterAliasCombinationException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidFilterFieldException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidFilterFieldException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidFilterParamException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidFilterParamException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidFilterParamFormatException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidFilterParamFormatException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidFilterParamValueException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidFilterParamValueException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidInputEncodingException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidInputEncodingException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidInterfaceException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidInterfaceException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidOauthProviderException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidOauthProviderException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidPathException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidPathException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidReturnToUrlException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidReturnToUrlException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidSearchQueryException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidSearchQueryException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidSortFieldException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidSortFieldException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidSortFilterCombinationException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidSortFilterCombinationException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidUploadOffsetException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidUploadOffsetException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidUploadPartGapException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidUploadPartGapException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidUploadPartSizeException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidUploadPartSizeException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidWorkspaceIdHeaderException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidWorkspaceIdHeaderException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class MethodNotAllowedException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public MethodNotAllowedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class MultipleSortParamsNotAllowedException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public MultipleSortParamsNotAllowedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class NoValidInputParamsException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public NoValidInputParamsException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class OffsetUploadNotAllowedWithMalwareScanningException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public OffsetUploadNotAllowedWithMalwareScanningException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class PartNumberTooLargeException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public PartNumberTooLargeException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class PathCannotHaveTrailingWhitespaceException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public PathCannotHaveTrailingWhitespaceException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class ReauthenticationNeededFieldsException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public ReauthenticationNeededFieldsException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class RequestBodyTooLargeException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public RequestBodyTooLargeException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class RequestParamsContainInvalidCharacterException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public RequestParamsContainInvalidCharacterException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class RequestParamsInvalidException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public RequestParamsInvalidException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class RequestParamsRequiredException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public RequestParamsRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class SearchAllOnChildPathException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public SearchAllOnChildPathException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class UnrecognizedSortIndexException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public UnrecognizedSortIndexException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class UnsupportedCurrencyException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public UnsupportedCurrencyException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class UnsupportedHttpResponseFormatException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public UnsupportedHttpResponseFormatException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class UnsupportedMediaTypeException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public UnsupportedMediaTypeException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class UserIdInvalidException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public UserIdInvalidException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class UserIdOnUserEndpointException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public UserIdOnUserEndpointException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class UserRequiredException extends BadRequestException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public UserRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * A family of API error types. Each type in the family has a subclass of this class, so catching
   * it handles any of them.
   */
  public static class NotAuthenticatedException extends ApiErrorException {
    /**
     * Creates the exception.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public NotAuthenticatedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class AdditionalAuthenticationRequiredException extends NotAuthenticatedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public AdditionalAuthenticationRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class ApiKeySessionsNotSupportedException extends NotAuthenticatedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public ApiKeySessionsNotSupportedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class AuthenticationRequiredException extends NotAuthenticatedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public AuthenticationRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class BundleRegistrationCodeFailedException extends NotAuthenticatedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public BundleRegistrationCodeFailedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InboxRegistrationCodeFailedException extends NotAuthenticatedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InboxRegistrationCodeFailedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidCredentialsException extends NotAuthenticatedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidCredentialsException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidOauthException extends NotAuthenticatedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidOauthException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidOrExpiredCodeException extends NotAuthenticatedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidOrExpiredCodeException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidSessionException extends NotAuthenticatedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidSessionException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidUsernameOrPasswordException extends NotAuthenticatedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidUsernameOrPasswordException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class LockedOutException extends NotAuthenticatedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public LockedOutException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class LockoutRegionMismatchException extends NotAuthenticatedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public LockoutRegionMismatchException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class OneTimePasswordIncorrectException extends NotAuthenticatedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public OneTimePasswordIncorrectException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class TwoFactorAuthenticationErrorException extends NotAuthenticatedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public TwoFactorAuthenticationErrorException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class TwoFactorAuthenticationSetupExpiredException extends NotAuthenticatedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public TwoFactorAuthenticationSetupExpiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * A family of API error types. Each type in the family has a subclass of this class, so catching
   * it handles any of them.
   */
  public static class NotAuthorizedException extends ApiErrorException {
    /**
     * Creates the exception.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public NotAuthorizedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class ApiKeyIsDisabledException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public ApiKeyIsDisabledException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class ApiKeyIsPathRestrictedException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public ApiKeyIsPathRestrictedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class ApiKeyOnlyForDesktopAppException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public ApiKeyOnlyForDesktopAppException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class ApiKeyOnlyForFileOperationsException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public ApiKeyOnlyForFileOperationsException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class ApiKeyOnlyForMobileAppException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public ApiKeyOnlyForMobileAppException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class ApiKeyOnlyForOfficeIntegrationException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public ApiKeyOnlyForOfficeIntegrationException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class BillingInformationHiddenException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public BillingInformationHiddenException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class BillingPermissionRequiredException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public BillingPermissionRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class BundleMaximumUsesReachedException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public BundleMaximumUsesReachedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class BundlePermissionRequiredException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public BundlePermissionRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class CannotAdministerHigherLevelUserException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public CannotAdministerHigherLevelUserException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class CannotLoginWhileUsingKeyException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public CannotLoginWhileUsingKeyException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class CantActForOtherUserException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public CantActForOtherUserException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class ContactAdminForPasswordChangeHelpException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public ContactAdminForPasswordChangeHelpException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class FilesAgentFailedAuthorizationException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public FilesAgentFailedAuthorizationException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class FolderAdminOrBillingPermissionRequiredException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public FolderAdminOrBillingPermissionRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class FolderAdminPermissionRequiredException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public FolderAdminPermissionRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class FullPermissionRequiredException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public FullPermissionRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class HistoryPermissionRequiredException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public HistoryPermissionRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InAppAiAssistantUnavailableException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InAppAiAssistantUnavailableException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InsufficientPermissionForParamsException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InsufficientPermissionForParamsException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InsufficientPermissionForSiteException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InsufficientPermissionForSiteException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class MoverAccessDeniedException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public MoverAccessDeniedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class MoverPackageRequiredException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public MoverPackageRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class MustAuthenticateWithApiKeyException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public MustAuthenticateWithApiKeyException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class NeedAdminPermissionForInboxException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public NeedAdminPermissionForInboxException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class NonAdminsMustQueryByFolderOrPathException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public NonAdminsMustQueryByFolderOrPathException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class NotAllowedToCreateBundleException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public NotAllowedToCreateBundleException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class NotEnqueuableSyncException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public NotEnqueuableSyncException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class PasswordChangeNotRequiredException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public PasswordChangeNotRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class PasswordChangeRequiredException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public PasswordChangeRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class PaymentMethodErrorException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public PaymentMethodErrorException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class PreviewOnlyPermissionCannotDownloadException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public PreviewOnlyPermissionCannotDownloadException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class ReadOnlySessionException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public ReadOnlySessionException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class ReadPermissionRequiredException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public ReadPermissionRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class ReauthenticationFailedException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public ReauthenticationFailedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class ReauthenticationFailedFinalException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public ReauthenticationFailedFinalException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class ReauthenticationNeededActionException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public ReauthenticationNeededActionException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class RecaptchaFailedException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public RecaptchaFailedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class RemoteDesktopDebugLoggingDisabledException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public RemoteDesktopDebugLoggingDisabledException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class RootFolderBehaviorSiteAdminRequiredException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public RootFolderBehaviorSiteAdminRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class RootFolderBehaviorSkipSiteAdminRequiredException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public RootFolderBehaviorSkipSiteAdminRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class SelfManagedRequiredException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public SelfManagedRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class SiteAdminOrPartnerAdminPermissionRequiredException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public SiteAdminOrPartnerAdminPermissionRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class SiteAdminOrWorkspaceAdminOrFolderAdminPermissionRequiredException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public SiteAdminOrWorkspaceAdminOrFolderAdminPermissionRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class SiteAdminOrWorkspaceAdminOrPartnerAdminOrFolderAdminPermissionRequiredException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public SiteAdminOrWorkspaceAdminOrPartnerAdminOrFolderAdminPermissionRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class SiteAdminOrWorkspaceAdminOrPartnerAdminPermissionRequiredException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public SiteAdminOrWorkspaceAdminOrPartnerAdminPermissionRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class SiteAdminOrWorkspaceAdminPermissionRequiredException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public SiteAdminOrWorkspaceAdminPermissionRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class SiteAdminRequiredException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public SiteAdminRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class SiteFilesAreImmutableException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public SiteFilesAreImmutableException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class TwoFactorAuthenticationRequiredException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public TwoFactorAuthenticationRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class UserIdWithoutSiteAdminException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public UserIdWithoutSiteAdminException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class WriteAndBundlePermissionRequiredException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public WriteAndBundlePermissionRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class WritePermissionRequiredException extends NotAuthorizedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public WritePermissionRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * A family of API error types. Each type in the family has a subclass of this class, so catching
   * it handles any of them.
   */
  public static class NotFoundException extends ApiErrorException {
    /**
     * Creates the exception.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public NotFoundException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class ApiKeyNotFoundException extends NotFoundException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public ApiKeyNotFoundException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class BundlePathNotFoundException extends NotFoundException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public BundlePathNotFoundException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class BundleRegistrationNotFoundException extends NotFoundException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public BundleRegistrationNotFoundException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class CodeNotFoundException extends NotFoundException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public CodeNotFoundException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class FileNotFoundException extends NotFoundException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public FileNotFoundException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class FileUploadNotFoundException extends NotFoundException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public FileUploadNotFoundException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class GroupNotFoundException extends NotFoundException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public GroupNotFoundException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InboxNotFoundException extends NotFoundException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InboxNotFoundException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class NestedNotFoundException extends NotFoundException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public NestedNotFoundException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class PlanNotFoundException extends NotFoundException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public PlanNotFoundException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class SiteNotFoundException extends NotFoundException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public SiteNotFoundException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class UserNotFoundException extends NotFoundException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public UserNotFoundException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * A family of API error types. Each type in the family has a subclass of this class, so catching
   * it handles any of them.
   */
  public static class ProcessingFailureException extends ApiErrorException {
    /**
     * Creates the exception.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public ProcessingFailureException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class AgentPushUpdateBlockedException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public AgentPushUpdateBlockedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class AgentUnavailableException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public AgentUnavailableException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class AiTaskCannotBeRunManuallyException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public AiTaskCannotBeRunManuallyException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class AlreadyCompletedException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public AlreadyCompletedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class AutomationCannotBeRunManuallyException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public AutomationCannotBeRunManuallyException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class BehaviorNotAllowedOnRemoteServerException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public BehaviorNotAllowedOnRemoteServerException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class BufferedUploadDisabledForThisDestinationException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public BufferedUploadDisabledForThisDestinationException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class BundleOnlyAllowsPreviewsException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public BundleOnlyAllowsPreviewsException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class BundleOperationRequiresSubfolderException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public BundleOperationRequiresSubfolderException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class ConfigurationLockedPathException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public ConfigurationLockedPathException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class CouldNotCreateParentException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public CouldNotCreateParentException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class DestinationExistsException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public DestinationExistsException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class DestinationFolderLimitedException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public DestinationFolderLimitedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class DestinationParentConflictException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public DestinationParentConflictException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class DestinationParentDoesNotExistException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public DestinationParentDoesNotExistException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class ExceededRuntimeLimitException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public ExceededRuntimeLimitException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class ExpectationAlreadyHasOpenWindowException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public ExpectationAlreadyHasOpenWindowException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class ExpectationNotManualTriggerException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public ExpectationNotManualTriggerException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class ExpiredPrivateKeyException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public ExpiredPrivateKeyException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class ExpiredPublicKeyException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public ExpiredPublicKeyException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class ExportFailureException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public ExportFailureException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class ExportNotReadyException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public ExportNotReadyException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class FailedToChangePasswordException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public FailedToChangePasswordException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class FileLockedException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public FileLockedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class FileNotUploadedException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public FileNotUploadedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class FilePendingProcessingException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public FilePendingProcessingException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class FileProcessingErrorException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public FileProcessingErrorException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class FileTooBigToDecryptException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public FileTooBigToDecryptException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class FileTooBigToEncryptException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public FileTooBigToEncryptException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class FileUploadedToWrongRegionException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public FileUploadedToWrongRegionException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class FilenameTooLongException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public FilenameTooLongException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class FolderLockedException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public FolderLockedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class FolderNotEmptyException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public FolderNotEmptyException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class HistoryUnavailableException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public HistoryUnavailableException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidBundleCodeException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidBundleCodeException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidFileTypeException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidFileTypeException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidFilenameException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidFilenameException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidPriorityColorException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidPriorityColorException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidRangeException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidRangeException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidSiteException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidSiteException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class InvalidZipFileException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public InvalidZipFileException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class MetadataNotSupportedOnRemotesException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public MetadataNotSupportedOnRemotesException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class ModelSaveErrorException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public ModelSaveErrorException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class MultipleProcessingErrorsException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public MultipleProcessingErrorsException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class PathTooLongException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public PathTooLongException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class RecipientAlreadySharedException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public RecipientAlreadySharedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class RemoteEntryReadOnlyException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public RemoteEntryReadOnlyException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class RemoteServerErrorException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public RemoteServerErrorException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class ResourceBelongsToParentSiteException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public ResourceBelongsToParentSiteException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class ResourceLockedException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public ResourceLockedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class SubfolderLockedException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public SubfolderLockedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class SyncInProgressException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public SyncInProgressException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class TwoFactorAuthenticationCodeAlreadySentException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public TwoFactorAuthenticationCodeAlreadySentException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class TwoFactorAuthenticationCountryBlacklistedException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public TwoFactorAuthenticationCountryBlacklistedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class TwoFactorAuthenticationGeneralErrorException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public TwoFactorAuthenticationGeneralErrorException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class TwoFactorAuthenticationMethodUnsupportedErrorException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public TwoFactorAuthenticationMethodUnsupportedErrorException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class TwoFactorAuthenticationUnsubscribedRecipientException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public TwoFactorAuthenticationUnsubscribedRecipientException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class UpdatesNotAllowedForRemotesException extends ProcessingFailureException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public UpdatesNotAllowedForRemotesException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * A family of API error types. Each type in the family has a subclass of this class, so catching
   * it handles any of them.
   */
  public static class RateLimitedException extends ApiErrorException {
    /**
     * Creates the exception.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public RateLimitedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class DuplicateShareRecipientException extends RateLimitedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public DuplicateShareRecipientException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class ReauthenticationRateLimitedException extends RateLimitedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public ReauthenticationRateLimitedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class TooManyConcurrentLoginsException extends RateLimitedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public TooManyConcurrentLoginsException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class TooManyConcurrentRequestsException extends RateLimitedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public TooManyConcurrentRequestsException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class TooManyLoginAttemptsException extends RateLimitedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public TooManyLoginAttemptsException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class TooManyRequestsException extends RateLimitedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public TooManyRequestsException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class TooManySharesException extends RateLimitedException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public TooManySharesException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * A family of API error types. Each type in the family has a subclass of this class, so catching
   * it handles any of them.
   */
  public static class ServiceUnavailableException extends ApiErrorException {
    /**
     * Creates the exception.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public ServiceUnavailableException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class AutomationsUnavailableException extends ServiceUnavailableException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public AutomationsUnavailableException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class LockOperationBusyException extends ServiceUnavailableException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public LockOperationBusyException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class MigrationInProgressException extends ServiceUnavailableException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public MigrationInProgressException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class SearchUnavailableException extends ServiceUnavailableException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public SearchUnavailableException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class SiteDisabledException extends ServiceUnavailableException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public SiteDisabledException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class UploadsUnavailableException extends ServiceUnavailableException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public UploadsUnavailableException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * A family of API error types. Each type in the family has a subclass of this class, so catching
   * it handles any of them.
   */
  public static class SiteConfigurationException extends ApiErrorException {
    /**
     * Creates the exception.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public SiteConfigurationException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class AccountAlreadyExistsException extends SiteConfigurationException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public AccountAlreadyExistsException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class AccountOverdueException extends SiteConfigurationException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public AccountOverdueException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class NoAccountForSiteException extends SiteConfigurationException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public NoAccountForSiteException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class SiteWasRemovedException extends SiteConfigurationException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public SiteWasRemovedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class TrialExpiredException extends SiteConfigurationException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public TrialExpiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class TrialLockedException extends SiteConfigurationException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public TrialLockedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  /**
   * One API error type within the family its superclass represents.
   * {@link ApiErrorException#forType} creates it.
   */
  public static class UserRequestsEnabledRequiredException extends SiteConfigurationException {
    /**
     * Creates the exception; {@link ApiErrorException#forType} calls this.
     *
     * @param message the exception message
     * @param responseError the parsed error body
     * @param headers the HTTP response headers
     */
    public UserRequestsEnabledRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

}
