package com.github.spullara.java.util.regex;

import org.junit.Test;

import java.util.Random;

import static junit.framework.Assert.assertEquals;

public class BenchmarkTest {
    private static String LATIN_ACCENTS_CHARS = "\\u00c0-\\u00d6\\u00d8-\\u00f6\\u00f8-\\u00ff\\u015f";
    private static final String HASHTAG_ALPHA_CHARS = "a-z" + LATIN_ACCENTS_CHARS +
                                                     "\\u0400-\\u04ff\\u0500-\\u0527" +  // Cyrillic
                                                     "\\u2de0–\\u2dff\\ua640–\\ua69f" +  // Cyrillic Extended A/B
                                                     "\\u1100-\\u11ff\\u3130-\\u3185\\uA960-\\uA97F\\uAC00-\\uD7AF\\uD7B0-\\uD7FF" + // Hangul (Korean)
                                                     "\\p{InHiragana}\\p{InKatakana}" +  // Japanese Hiragana and Katakana
                                                     "\\p{InCJKUnifiedIdeographs}" +     // Japanese Kanji / Chinese Han
                                                     "\\u3005\\u303b" +                  // Kanji/Han iteration marks
                                                     "\\uff21-\\uff3a\\uff41-\\uff5a" +  // full width Alphabet
                                                     "\\uff66-\\uff9f" +                 // half width Katakana
                                                     "\\uffa1-\\uffdc";                  // half width Hangul (Korean)
    private static final int TIMES = 5000000;

    @Test
    public void testSpeed() {
        for (int i = 0; i < 3; i++) {
            System.out.print("Java Ascii: ");
            int base = test(java.util.regex.Pattern.compile("[a-z]"));
            System.out.print(" New Ascii: ");
            assertEquals(base, test(Pattern.compile("[a-z]")));
            System.out.print("Java Unicode: ");
            base = test(java.util.regex.Pattern.compile("[" + HASHTAG_ALPHA_CHARS + "]"));
            System.out.print(" New Unicode: ");
            assertEquals(base, test(Pattern.compile("[" + HASHTAG_ALPHA_CHARS + "]")));
        }
    }

    private int test(Pattern p) {
        Random random = new Random(239487234);
        String[] toMatch = new String[100];
        char[] chars = "abcdefghijklmnopqrstuvwxyz ".toCharArray();
        for (int i = 0; i < 100; i++) {
            StringBuilder sb = new StringBuilder();
            int len = random.nextInt(20);
            for (int j = 0; j < len; j++) {
                sb.append(chars[random.nextInt(chars.length)]);
            }
            toMatch[i] = sb.toString();
        }
        long start = System.currentTimeMillis();
        int total = 0;
        for (int i = 0; i < TIMES; i++) {
            Matcher matcher = p.matcher(toMatch[i % 100]);
            while (matcher.find()) {
                total++;
            }
        }
        long diff = System.currentTimeMillis() - start;
        System.out.println(TIMES /diff);
        return total;
    }

    private int test(java.util.regex.Pattern p) {
        Random random = new Random(239487234);
        String[] toMatch = new String[100];
        char[] chars = "abcdefghijklmnopqrstuvwxyz ".toCharArray();
        for (int i = 0; i < 100; i++) {
            StringBuilder sb = new StringBuilder();
            int len = random.nextInt(20);
            for (int j = 0; j < len; j++) {
                sb.append(chars[random.nextInt(chars.length)]);
            }
            toMatch[i] = sb.toString();
        }
        long start = System.currentTimeMillis();
        int total = 0;
        for (int i = 0; i < TIMES; i++) {
            java.util.regex.Matcher matcher = p.matcher(toMatch[i % 100]);
            while (matcher.find()) {
                total++;
            }
        }
        long diff = System.currentTimeMillis() - start;
        System.out.println(TIMES /diff);
        return total;
    }

}
