/*
+++++++++++++++++++++++++++++++++++++++++++++++
title     String Manipulation Public API Test +
project   icecore-strman                      +
file      StrmanTest.java                     +
version                                       +
author    Arctic Ice Studio                   +
email     development@arcticicestudio.com     +
website   http://arcticicestudio.com          +
copyright Copyright (C) 2016                  +
created   2016-06-18 14:37 UTC+0200           +
modified  2016-06-18 14:39 UTC+0200           +
+++++++++++++++++++++++++++++++++++++++++++++++

[Description]
Tests the "IceCore - String Manipulation" public API class "Strman".

[Copyright]
Copyright (C) 2016 Arctic Ice Studio <development@arcticicestudio.com>

[References]
Java 8 API Documentation
  (https://docs.oracle.com/javase/8/docs/api/)
Arctic Versioning Specification (ArcVer)
  (http://specs.arcticicestudio.com/arcver)
*/

package com.arcticicestudio.icecore.strman;

import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;

import static com.arcticicestudio.icecore.strman.Strman.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Tests the <a href="https://bitbucket.org/arcticicestudio/icecore-strman">IceCore - String Manipulation</a>
 * public API class {@link Strman}.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @see <a href="https://bitbucket.org/arcticicestudio/icecore-strman">IceCore - String Manipulation</a>
 */
public class StrmanTest {

  @Test
  public void append_shouldAppendStringsToEndOfString() throws Exception {
    assertThat(append("y", "o", "g", "u", "r", "t"), equalTo("yogurt"));
    assertThat(append("yogurt"), equalTo("yogurt"));
    assertThat(append("", "yogurt"), equalTo("yogurt"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void append_shouldThrowIllegalArgumentExceptionWhenStringIsNull() throws Exception {
    append(null);
  }

  @Test
  public void appendArray_shouldAppendStringArrayToEndOfString() throws Exception {
    assertThat(appendArray("y", new String[]{"o", "g", "u", "r", "t"}), equalTo("yogurt"));
    assertThat(appendArray("yogurt", new String[]{}), equalTo("yogurt"));
    assertThat(appendArray("", new String[]{"yogurt"}), equalTo("yogurt"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void appendArray_ShouldThrowIllegalArgumentExceptionWhenStringIsNull() throws Exception {
    appendArray(null, new String[]{});
  }

  @Test
  public void at_shouldFindCharacterAtIndex() throws Exception {
    assertThat(at("yogurt", 0), equalTo(Optional.of("y")));
    assertThat(at("yogurt", 1), equalTo(Optional.of("o")));
    assertThat(at("yogurt", -1), equalTo(Optional.of("t")));
    assertThat(at("yogurt", -2), equalTo(Optional.of("r")));
    assertThat(at("yogurt", 10), equalTo(Optional.empty()));
    assertThat(at("yogurt", -10), equalTo(Optional.empty()));
  }

  @Test
  public void between_shouldReturnArrayWithStringsBetweenStartAndEnd() throws Exception {
    assertThat(between("[abc][def]", "[", "]"), arrayContaining("abc", "def"));
    assertThat(between("<span>foo</span>", "<span>", "</span>"), arrayContaining("foo"));
    assertThat(between("<span>foo</span><span>bar</span>", "<span>", "</span>"), arrayContaining("foo", "bar"));
  }

  @Test
  public void between_shouldReturnFullStringWhenStartAndEndDoesNotExist() throws Exception {
    assertThat(between("[abc][def]", "{", "}"), arrayContaining("[abc][def]"));
    assertThat(between("", "{", "}"), arrayContaining(""));
  }

  @Test
  public void chars_shouldReturnAllCharactersInString() throws Exception {
    final String yogurt = "yogurt";
    assertThat(chars(yogurt), equalTo(new String[]{"y", "o", "g", "u", "r", "t"}));
  }

  @Test
  public void collapseWhitespace_shouldReplaceConsecutiveWhitespaceWithSingleSpace() throws Exception {
    String[] fixture = {
      "yo    gurt",
      "     yo     gurt    ",
      " yo     gurt   ",
      "    yo     gurt "
    };
    Arrays.stream(fixture).forEach(el -> assertThat(collapseWhitespace(el), equalTo("yo gurt")));
  }

  @Test
  public void collapseWhitespace_shouldReplaceConsecutiveWhitespaceBetweenMultipleStrings() throws Exception {
    String input = " yo      gurt      coco     nut    ";
    assertThat(collapseWhitespace(input), equalTo("yo gurt coco nut"));
  }

  @Test
  public void containsWithCaseSensitiveFalse_shouldReturnTrueWhenStringContainsNeedle() throws Exception {
    String[] fixture = {
      "yo gurt",
      "gurt yo",
      "yogurt",
      "yo"
    };

    Arrays.stream(fixture).forEach(el -> assertTrue(contains(el, "YO")));
  }

  @Test
  public void containsWithCaseSensitiveTrue_shouldReturnTrueWhenStringContainsNeedle() throws Exception {
    String[] fixture = {
      "yo gurt",
      "gurt yo",
      "yogurt",
      "yo"
    };

    Arrays.stream(fixture).forEach(el -> assertFalse(contains(el, "YO", true)));
  }

  @Test
  public void containsAll_shouldReturnTrueOnlyWhenAllNeedlesAreContainedInString() throws Exception {
    String[] fixture = {
      "yo gurt",
      "gurt yo",
      "yogurt"
    };

    Arrays.stream(fixture).forEach(el -> assertTrue(containsAll(el, new String[]{"yo", "gurt"})));
  }

  @Test
  public void containsAll_shouldReturnFalseOnlyWhenAllNeedlesAreNotContainedInString() throws Exception {
    String[] fixture = {
      "yo gurt",
      "gurt yo",
      "yogurt"
    };
    Arrays.stream(fixture).forEach(el -> assertFalse(containsAll(el, new String[]{"YO", "gurt"}, true)));
  }

  @Test
  public void containsAny_shouldReturnTrueWhenAnyOfSearchNeedleExistInInputString() throws Exception {
    String[] fixture = {
      "yo gurt",
      "gurt yo",
      "yogurt"
    };
    Arrays.stream(fixture).forEach(el -> assertTrue(containsAny(el, new String[]{"yo", "gurt", "coconut"})));
  }

  @Test
  public void containsAny_shouldReturnFalseWhenNoneOfSearchNeedleExistInInputString() throws Exception {
    String[] fixture = {
      "yo gurt",
      "gurt yo",
      "yogurt"
    };
    Arrays.stream(fixture).forEach(el -> assertFalse(containsAny(el, new String[]{"YO", "GURT", "Coconut"}, true)));
  }

  @Test
  public void countSubstr_shouldCountSubStrCountCaseInsensitiveWithoutOverlapInString() throws Exception {
    assertThat(countSubstr("aaaAAAaaa", "aaa", false, false), equalTo(3L));
  }

  @Test
  public void countSubstr_shouldCountSubStrCountCaseSensitiveWithoutOverlapInString() throws Exception {
    assertThat(countSubstr("aaaAAAaaa", "aaa"), equalTo(2L));
  }

  @Test
  public void countSubstr_shouldCountSubStrCountCaseInsensitiveWithOverlapInString() throws Exception {
    assertThat(countSubstr("aaaAAAaaa", "aaa", false, true), equalTo(7L));
  }

  @Test
  public void countSubstr_shouldCountSubStrCountCaseSensitiveWithOverlapInString() throws Exception {
    assertThat(countSubstr("aaaAAAaaa", "AAA", true, true), equalTo(1L));
  }

  @Test
  public void countSubstrTestFixture_caseSensitiveTrueAndOverlappingFalse() throws Exception {
    String[] fixture = {
      "aaaaaAaaAA",
      "faaaAAaaaaAA",
      "aaAAaaaaafA",
      "AAaaafaaaaAAAA"
    };
    Arrays.stream(fixture).forEach(el -> assertThat(countSubstr(el, "a", true, false), equalTo(7L)));
  }

  @Test
  public void countSubstrTestFixture_caseSensitiveFalseAndOverlappingFalse() throws Exception {
    String[] fixture = {
      "aaaaaaa",
      "faaaaaaa",
      "aaaaaaaf",
      "aaafaaaa"
    };
    Arrays.stream(fixture).forEach(el -> assertThat(countSubstr(el, "A", false, false), equalTo(7L)));
  }

  @Test
  public void countSubstrTestFixture_caseSensitiveTrueAndOverlappingTrue() throws Exception {
    assertThat(countSubstr("aaa", "aa", true, true), equalTo(2L));
  }

  @Test
  public void endsWith_caseSensitive_ShouldBeTrueWhenStringEndsWithSearchString() throws Exception {
    String[] fixture = {
      "yo gurt",
      "gurt"
    };
    Arrays.stream(fixture).forEach(el -> assertTrue(endsWith(el, "gurt")));
  }

  @Test
  public void endsWith_notCaseSensitive_ShouldBeTrueWhenStringEndsWithSearchString() throws Exception {
    String[] fixture = {
      "yo gurt",
      "gurt"
    };
    Arrays.stream(fixture).forEach(el -> assertTrue(endsWith(el, "GURT", false)));
  }

  @Test
  public void endsWith_caseSensitiveAtPosition_ShouldBeTrueWhenStringEndsWithSearchString() throws Exception {
    String[] fixture = {
      "yo gurtt",
      "gurtt"
    };
    Arrays.stream(fixture).forEach(el -> assertTrue(endsWith(el, "gurt", el.length() - 1, true)));
  }

  @Test
  public void endsWith_notCaseSensitiveAtPosition_ShouldBeTrueWhenStringEndsWithSearchString() throws Exception {
    String[] fixture = {
      "yo gurtt",
      "gurtt"
    };
    Arrays.stream(fixture).forEach(el -> assertTrue(endsWith(el, "GURT", el.length() - 1, false)));
  }

  @Test
  public void ensureLeft_shouldEnsureStringStartsWithFoo() throws Exception {
    String[] fixture = {
      "yogurt",
      "gurt"
    };

    Arrays.stream(fixture).forEach(el -> assertThat(ensureLeft(el, "yo"), equalTo("yogurt")));
  }

  @Test
  public void ensureLeft_notCaseSensitive_shouldEnsureStringStartsWithFoo() throws Exception {
    assertThat(ensureLeft("yogurt", "YO", false), equalTo("yogurt"));
    assertThat(ensureLeft("gurt", "YO", false), equalTo("YOgurt"));
  }

  @Test
  public void base64Decode_shouldDecodeABase64DecodedStringToString() throws Exception {
    assertThat(base64Decode("c3RybWFu"), equalTo("strman"));
    assertThat(base64Decode("eW8="), equalTo("yo"));
    assertThat(base64Decode("Z3VydA=="), equalTo("gurt"));
    assertThat(base64Decode("YsOhciE="), equalTo("bár!"));
    assertThat(base64Decode("5ryi"), equalTo("漢"));
  }

  @Test
  public void base64Encode_shouldEncodeAString() throws Exception {
    assertThat(base64Encode("strman"), equalTo("c3RybWFu"));
    assertThat(base64Encode("yo"), equalTo("eW8="));
    assertThat(base64Encode("gurt"), equalTo("Z3VydA=="));
    assertThat(base64Encode("bár!"), equalTo("YsOhciE="));
    assertThat(base64Encode("漢"), equalTo("5ryi"));
  }

  @Test
  public void binDecode_shouldDecodeABinaryStringToAString() throws Exception {
    assertThat(
      decodeBin("000000000111001100000000011101000000000001110010000000000110110100000000011000010000000001101110"),
      equalTo("strman"));

    assertThat(decodeBin("0110111100100010"), equalTo("漢"));
    assertThat(decodeBin("0000000001000001"), equalTo("A"));
    assertThat(decodeBin("0000000011000001"), equalTo("Á"));
    assertThat(decodeBin("00000000010000010000000001000001"), equalTo("AA"));
  }

  @Test
  public void binEncode_shouldEncodeAStringToBinaryFormat() throws Exception {
    assertThat(encodeBin("漢"), equalTo("0110111100100010"));
    assertThat(encodeBin("A"), equalTo("0000000001000001"));
    assertThat(encodeBin("Á"), equalTo("0000000011000001"));
    assertThat(encodeBin("AA"), equalTo("00000000010000010000000001000001"));
  }

  @Test
  public void decDecode_shouldDecodeDecimalStringToString() throws Exception {
    assertThat(decodeDec("28450"), equalTo("漢"));
    assertThat(decodeDec("00065"), equalTo("A"));
    assertThat(decodeDec("00193"), equalTo("Á"));
    assertThat(decodeDec("0006500065"), equalTo("AA"));
  }
}
