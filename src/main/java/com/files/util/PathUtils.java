package com.files.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.regex.Pattern;

/**
 * Helpers for Files.com path strings. {@link #normalize(String)} tidies a path's syntax, and
 * {@link #normalize_for_comparison(String)} and {@link #isSame(String, String)} tell whether two
 * paths name the same file or folder under Files.com's comparison rules.
 *
 * <p>Use comparison results only for matching. Send the original path spelling in API requests and
 * keep it for display and local file names; a comparison key can differ from it in spelling and
 * length.
 *
 * <p>These methods work on strings alone: they do not check that a path exists, confine it to a
 * folder or URL-encode it, and they throw {@code NullPointerException} for a {@code null} path.
 */
public class PathUtils {
  // Precompile for Performance
  private static final Pattern NULL_BYTE = Pattern.compile("\0");
  private static final Pattern BACKSLASH = Pattern.compile("\\\\");
  private static final Pattern LEADING_AND_TRAILING_SLASHES = Pattern.compile("(/)*$|^(/)*");
  private static final Pattern TWO_OR_MORE_SLASHES = Pattern.compile("(/){2,}");

  /**
   * For subclasses; every method is static, so no instance is needed.
   */
  protected PathUtils() {
  }

  /**
   * Returns {@code str} in the path syntax the Files.com API uses: components separated by single
   * {@code /} characters, with no leading or trailing {@code /}. It removes NUL characters, turns
   * each backslash into {@code /}, removes leading and trailing slashes, collapses repeated
   * slashes, and drops every component that is exactly {@code .} or {@code ..}. Dropping
   * {@code ..} does not remove the folder before it, so {@code folder/../file.txt} becomes
   * {@code folder/file.txt}. Letter case, whitespace (trailing spaces included) and all other
   * characters are kept.
   *
   * @param str the path to normalize; must not be {@code null}
   * @return the normalized path; empty when {@code str} has only slashes and {@code .} or
   *     {@code ..} components
   */
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

  /**
   * Returns the comparison key of {@code str}: paths whose keys are equal name the same Files.com
   * file or folder under the comparison map this SDK bundles. The key is the
   * {@link #normalize(String)} result with each character mapped once: ASCII letters {@code A} to
   * {@code Z} become lowercase, other printable ASCII characters are kept, and every other
   * character is replaced as the bundled map says, or kept when the map has no entry for it. The
   * map, shared with the Files.com server and the other SDKs, treats case, many accent differences
   * and some other differences, such as Hiragana and Katakana, as equivalent; a replacement can be
   * empty or several characters long, and is not mapped again.
   *
   * <p>The key is for comparing paths only, not a path to send to the API or a new spelling of the
   * name.
   *
   * @param str the path to compare; must not be {@code null}
   * @return the comparison key, which can differ from {@code str} in spelling and length
   */
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

  /**
   * Returns whether two paths name the same Files.com file or folder under the bundled comparison
   * map, that is, whether their {@link #normalize_for_comparison(String)} keys are equal. Use it to
   * match a local or remembered path against one the API returned, keeping both spellings.
   *
   * @param path1 a path; must not be {@code null}
   * @param path2 another path; must not be {@code null}
   * @return {@code true} if the two paths have equal comparison keys
   */
  public static boolean isSame(String path1, String path2) {
    return normalize_for_comparison(path1).equals(normalize_for_comparison(path2));
  }

  private static final Map<Integer, String> comparisonMap = loadComparisonMap();

  private static Map<Integer, String> loadComparisonMap() {
    try (InputStream stream = PathUtils.class.getResourceAsStream("path_comparison.json")) {
      JsonNode mapping = new ObjectMapper().readTree(stream).get("mapping");
      Map<Integer, String> result = new HashMap<>();
      for (Map.Entry<String, JsonNode> entry : mapping.properties()) {
        result.put(Integer.parseInt(entry.getKey(), 16), entry.getValue().asText());
      }
      return Collections.unmodifiableMap(result);
    } catch (IOException exception) {
      throw new ExceptionInInitializerError(exception);
    }
  }
}
