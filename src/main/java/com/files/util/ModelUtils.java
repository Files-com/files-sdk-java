package com.files.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Helpers for building Files.com request data: one turns JSON text into a parameter map, one joins
 * values into comma-delimited text, and one encodes the semicolons in a URI. Every method is
 * static.
 */
public class ModelUtils {
  /**
   * Creates a {@code ModelUtils}. Every method is static, so no instance is needed.
   */
  public ModelUtils() {
  }

  /**
   * Parses a JSON object into a map of request parameters. Each call parses with a new Jackson
   * mapper, and values follow Jackson's default untyped mapping: nested objects become maps,
   * arrays become lists, and numbers become {@code Integer}, {@code Long}, {@code BigInteger} or
   * {@code Double}.
   *
   * <p>It returns {@code null}, rather than throwing, when Jackson cannot read {@code object} as a
   * JSON object, for example malformed JSON, empty input or a JSON array, and also when
   * {@code object} is the JSON literal {@code null}. A {@code null} argument is not accepted:
   * Jackson rejects it with an unchecked exception that this method does not catch, and other
   * unchecked failures propagate in the same way.
   *
   * @param object the JSON text to parse
   * @return the parsed parameters, or {@code null} as described above
   */
  public static HashMap<String, Object> toParameterMap(String object) {
    ObjectMapper mapper = JsonMapper
        .builder()
        .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
        .build();

    TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {};
    HashMap<String, Object> parameters;
    try {
      parameters = mapper.readValue(object, typeRef);
    } catch (JsonProcessingException e) {
      parameters = null;
    }
    return parameters;
  }

  /**
   * Joins values into the comma-delimited text that parameters documented as comma-delimited
   * strings, such as lists of IDs, take. Each value that is not {@code null} is written with
   * {@link String#valueOf(Object)}, in order; {@code null} values are skipped.
   *
   * @param values the values to join; must not be {@code null}
   * @return the values separated by commas, or an empty string when there are none
   */
  public static String commaDelimited(Object[] values) {
    return Arrays.stream(values)
        .filter(Objects::nonNull)
        .map(String::valueOf)
        .collect(Collectors.joining(","));
  }

  /**
   * Returns {@code uri} with every semicolon ({@code ;}) replaced by {@code %3B}, and every other
   * character left as it is. It is not a general URL encoder: it does not encode spaces,
   * {@code %}, {@code ?}, {@code #}, non-ASCII characters or anything else, and it does not check
   * that {@code uri} is valid.
   *
   * @param uri the URI, or part of one, to change; must not be {@code null}
   * @return {@code uri} with its semicolons encoded
   */
  public static String forceMandatoryUriEncode(String uri) {
    return uri.replaceAll(";", "%3B");
  }
}
