package com.files.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringJoiner;
import java.util.regex.Pattern;

public class PathUtils {
  // Precompile for Performance
  private static final Pattern NULL_BYTE = Pattern.compile("\0");
  private static final Pattern BACKSLASH = Pattern.compile("\\\\");
  private static final Pattern LEADING_AND_TRAILING_SLASHES = Pattern.compile("(/)*$|^(/)*");
  private static final Pattern TWO_OR_MORE_SLASHES = Pattern.compile("(/){2,}");

  protected PathUtils() {
  }

  public static String normalize(String str) {
    String newStr = str;
    newStr = NULL_BYTE.matcher(newStr).replaceAll("");
    newStr = BACKSLASH.matcher(newStr).replaceAll("/");
    newStr = LEADING_AND_TRAILING_SLASHES.matcher(newStr).replaceAll("");
    newStr = TWO_OR_MORE_SLASHES.matcher(newStr).replaceAll("/");

    StringJoiner joiner = new StringJoiner("/");
    for (String subStr : newStr.split("/")) {
      if (!".".equals(subStr) && !"..".equals(subStr)) {
        joiner.add(subStr);
      }
    }
    newStr = joiner.toString();

    return newStr;
  }

  public static String normalize_for_comparison(String str) {
    String path = normalize(str);
    StringBuilder result = new StringBuilder(path.length());
    for (int offset = 0; offset < path.length(); ) {
      int scalar = path.codePointAt(offset);
      offset += Character.charCount(scalar);
      if (scalar >= ' ' && scalar <= '~') {
        result.append((char) (scalar >= 'A' && scalar <= 'Z' ? scalar + 'a' - 'A' : scalar));
      } else {
        String replacement = comparisonMap.get(scalar);
        if (replacement == null) {
          result.appendCodePoint(scalar);
        } else {
          result.append(replacement);
        }
      }
    }
    return result.toString();
  }

  public static boolean isSame(String path1, String path2) {
    return normalize_for_comparison(path1).equals(normalize_for_comparison(path2));
  }

  private static final Map<Integer, String> comparisonMap = loadComparisonMap();

  private static Map<Integer, String> loadComparisonMap() {
    try (InputStream stream = PathUtils.class.getResourceAsStream("path_comparison.json")) {
      JsonNode mapping = new ObjectMapper().readTree(stream).get("mapping");
      Map<Integer, String> result = new HashMap<>();
      Iterator<Map.Entry<String, JsonNode>> entries = mapping.fields();
      while (entries.hasNext()) {
        Map.Entry<String, JsonNode> entry = entries.next();
        result.put(Integer.parseInt(entry.getKey(), 16), entry.getValue().asText());
      }
      return Collections.unmodifiableMap(result);
    } catch (IOException exception) {
      throw new ExceptionInInitializerError(exception);
    }
  }
}
