package com.files;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;

/**
 * The JSON body of an error response from the Files.com API, which
 * {@link com.files.exceptions.ApiErrorException#getResponseError()} returns.
 *
 * <p>Each field holds the body value with the matching JSON name. Fields are optional: one the
 * body does not include is {@code null}, and {@link #httpCode} is 0. The SDK fills these fields
 * from the response body without validating them, and they are plain public fields.
 */
public class ResponseError {
  /** Further detail about the error, when the body includes it. */
  public String detail;
  /** The error message, for example {@code You do not have read permission for this path}. */
  public String error;
  /**
   * Readable error messages, in the site's default language, which the API includes when creating
   * or updating a record fails.
   */
  public String[] errors;
  /**
   * The body's {@code http-code} value: the HTTP status the API reports for the error. It is read
   * from the body, not from the response's status line, and is 0 when the body has none.
   */
  @JsonProperty("http-code")
  public int httpCode;
  /** An identifier the API gives this occurrence of the error. */
  public String instance;
  /**
   * The body's {@code model_errors}: the messages in {@link #errors}, keyed by the name of the
   * parameter that caused each, when creating or updating a record fails.
   */
  @JsonProperty("model_errors")
  public HashMap<String, String[]> modelErrors;
  /**
   * The body's {@code model_error_keys}: for each parameter in {@link #modelErrors}, the
   * translation keys its messages were made from, for error handling that does not depend on
   * language. An error without a translation key has its English message here instead.
   */
  @JsonProperty("model_error_keys")
  public HashMap<String, String[]> modelErrorKeys;
  /** A short title for the error, for example {@code Read Permission Required}. */
  public String title;
  /**
   * The error type, for example {@code not-authorized/read-permission-required}: a family and a
   * specific error separated by {@code /}, or a single error name. Different errors can share an
   * HTTP status, so handle errors by their type.
   * {@link com.files.exceptions.ApiErrorException#forType} chooses the exception class from it.
   */
  public String type;
  /** Additional data about the error, when the body includes it. */
  public HashMap<String, Object> data;
}
