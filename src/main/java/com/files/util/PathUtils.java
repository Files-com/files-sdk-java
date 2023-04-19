package com.files.util;

import java.text.Normalizer;
import java.util.Collections;
import java.util.Map;
import java.util.StringJoiner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathUtils {
  // Precompile for Performance
  private static final Pattern NULL_BYTE = Pattern.compile("\0");
  private static final Pattern BACKSLASH = Pattern.compile("\\\\");
  private static final Pattern LEADING_AND_TRAILING_SLASHES = Pattern.compile("(/)*$|^(/)*");
  private static final Pattern TWO_OR_MORE_SLASHES = Pattern.compile("(/){2,}");
  
  public static String normalize_for_comparison(String str) {
    String newStr = str;
    newStr = NULL_BYTE.matcher(newStr).replaceAll("");
    newStr = BACKSLASH.matcher(newStr).replaceAll("/");
    newStr = LEADING_AND_TRAILING_SLASHES.matcher(newStr).replaceAll("");
    newStr = TWO_OR_MORE_SLASHES.matcher(newStr).replaceAll("/");

    StringJoiner joiner = new StringJoiner("/");
    for (String subStr: newStr.split("/")) {
      if (!".".equals(subStr) && !"..".equals(subStr)) {
        joiner.add(subStr);
      }
    } 
    newStr = joiner.toString();

    newStr = Normalizer.normalize(newStr, Normalizer.Form.NFKC);
    newStr = transliterate(newStr);
    newStr = newStr.toLowerCase();
    newStr = newStr.replaceFirst("\\s++$", ""); // .stripTrailing() for JDK before 11

    return newStr;
  }

  public static boolean isSame(String path1, String path2) {
    return normalize_for_comparison(path1).equals(normalize_for_comparison(path2));
  }

  private static String transliterate(String str) {
    StringBuilder sb = new StringBuilder();
    for (String substr: str.split("")) {
      sb.append(transliterationMap.getOrDefault(substr, substr));
    } 
    return sb.toString();
  }
 
  private static final Map<String, String> transliterationMap = Stream.of(new String[][]{ 
    {"À", "A"},
    {"Á", "A"},
    {"Â", "A"},
    {"Ã", "A"},
    {"Ä", "A"},
    {"Å", "A"},
    {"Æ", "AE"},
    {"Ç", "C"},
    {"È", "E"},
    {"É", "E"},
    {"Ê", "E"},
    {"Ë", "E"},
    {"Ì", "I"},
    {"Í", "I"},
    {"Î", "I"},
    {"Ï", "I"},
    {"Ð", "D"},
    {"Ñ", "N"},
    {"Ò", "O"},
    {"Ó", "O"},
    {"Ô", "O"},
    {"Õ", "O"},
    {"Ö", "O"},
    {"Ø", "O"},
    {"Ù", "U"},
    {"Ú", "U"},
    {"Û", "U"},
    {"Ü", "U"},
    {"Ý", "Y"},
    {"ß", "ss"},
    {"à", "a"},
    {"á", "a"},
    {"â", "a"},
    {"ã", "a"},
    {"ä", "a"},
    {"å", "a"},
    {"æ", "ae"},
    {"ç", "c"},
    {"è", "e"},
    {"é", "e"},
    {"ê", "e"},
    {"ë", "e"},
    {"ì", "i"},
    {"í", "i"},
    {"î", "i"},
    {"ï", "i"},
    {"ð", "d"},
    {"ñ", "n"},
    {"ò", "o"},
    {"ó", "o"},
    {"ô", "o"},
    {"õ", "o"},
    {"ö", "o"},
    {"ø", "o"},
    {"ù", "u"},
    {"ú", "u"},
    {"û", "u"},
    {"ü", "u"},
    {"ý", "y"},
    {"ÿ", "y"},
    {"Ā", "A"},
    {"ā", "a"},
    {"Ă", "A"},
    {"ă", "a"},
    {"Ą", "A"},
    {"ą", "a"},
    {"Ć", "C"},
    {"ć", "c"},
    {"Ĉ", "C"},
    {"ĉ", "c"},
    {"Ċ", "C"},
    {"ċ", "c"},
    {"Č", "C"},
    {"č", "c"},
    {"Ď", "D"},
    {"ď", "d"},
    {"Đ", "D"},
    {"đ", "d"},
    {"Ē", "E"},
    {"ē", "e"},
    {"Ĕ", "E"},
    {"ĕ", "e"},
    {"Ė", "E"},
    {"ė", "e"},
    {"Ę", "E"},
    {"ę", "e"},
    {"Ě", "E"},
    {"ě", "e"},
    {"Ĝ", "G"},
    {"ĝ", "g"},
    {"Ğ", "G"},
    {"ğ", "g"},
    {"Ġ", "G"},
    {"ġ", "g"},
    {"Ģ", "G"},
    {"ģ", "g"},
    {"Ĥ", "H"},
    {"ĥ", "h"},
    {"Ħ", "H"},
    {"ħ", "h"},
    {"Ĩ", "I"},
    {"ĩ", "i"},
    {"Ī", "I"},
    {"ī", "i"},
    {"Ĭ", "I"},
    {"ĭ", "i"},
    {"Į", "I"},
    {"į", "i"},
    {"İ", "I"},
    {"Ĳ", "IJ"},
    {"ĳ", "ij"},
    {"Ĵ", "J"},
    {"ĵ", "j"},
    {"Ķ", "K"},
    {"ķ", "k"},
    {"Ĺ", "L"},
    {"ĺ", "l"},
    {"Ļ", "L"},
    {"ļ", "l"},
    {"Ľ", "L"},
    {"ľ", "l"},
    {"Ł", "L"},
    {"ł", "l"},
    {"Ń", "N"},
    {"ń", "n"},
    {"Ņ", "N"},
    {"ņ", "n"},
    {"Ň", "N"},
    {"ň", "n"},
    {"ŉ", "'n"},
    {"Ō", "O"},
    {"ō", "o"},
    {"Ŏ", "O"},
    {"ŏ", "o"},
    {"Ő", "O"},
    {"ő", "o"},
    {"Œ", "OE"},
    {"œ", "oe"},
    {"Ŕ", "R"},
    {"ŕ", "r"},
    {"Ŗ", "R"},
    {"ŗ", "r"},
    {"Ř", "R"},
    {"ř", "r"},
    {"Ś", "S"},
    {"ś", "s"},
    {"Ŝ", "S"},
    {"ŝ", "s"},
    {"Ş", "S"},
    {"ş", "s"},
    {"Š", "S"},
    {"š", "s"},
    {"Ţ", "T"},
    {"ţ", "t"},
    {"Ť", "T"},
    {"ť", "t"},
    {"Ũ", "U"},
    {"ũ", "u"},
    {"Ū", "U"},
    {"ū", "u"},
    {"Ŭ", "U"},
    {"ŭ", "u"},
    {"Ů", "U"},
    {"ů", "u"},
    {"Ű", "U"},
    {"ű", "u"},
    {"Ų", "U"},
    {"ų", "u"},
    {"Ŵ", "W"},
    {"ŵ", "w"},
    {"Ŷ", "Y"},
    {"ŷ", "y"},
    {"Ÿ", "Y"},
    {"Ź", "Z"},
    {"ź", "z"},
    {"Ż", "Z"},
    {"ż", "z"},
    {"Ž", "Z"},
    {"ž", "z"},
  }).collect(Collectors.collectingAndThen(
      Collectors.toMap(data -> data[0], data -> data[1]), 
      Collections::<String, String>unmodifiableMap));

  private PathUtils() {
  }
}
