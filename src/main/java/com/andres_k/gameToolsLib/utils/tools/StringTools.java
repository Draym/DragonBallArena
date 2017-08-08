package com.andres_k.gameToolsLib.utils.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by com.andres_k on 18/03/2016.
 */
public class StringTools {
    public static List<String> cutEachWord(String value, String delim) {
        List<String> words = new ArrayList<>();
        int pos = 0;

        while ((pos = value.indexOf(delim)) != -1) {
            words.add(value.substring(0, pos));
            value = value.substring(pos + 1);
        }
        words.add(value);
        return words;
    }

    public static String getWord(String value, String start, String end, int posStart, int posEnd) {
        int realStart = 0;
        int realEnd = 0;

        if (posStart < 0) {
            realStart = value.lastIndexOf(start) + 1;
        } else {
            for (int i = 0; i < posStart; ++i) {
                if ((realStart = value.indexOf(start, realStart + start.length()) + 1) == -1) {
                    break;
                }
            }
        }
        if (posEnd < 0) {
            realEnd = value.lastIndexOf(end);
        } else {
            realEnd = realStart;
            for (int i = 0; i < posEnd; ++i) {
                if ((realEnd = value.indexOf(end, realEnd + end.length())) == -1) {
                    realEnd = value.length();
                    break;
                }
            }
        }
        realStart = (realStart < 0 ? 0 : realStart);
        realStart = (realStart > value.length() ? value.length() : realStart);
        realEnd = (realEnd < 0 ? 0 : realEnd);
        realEnd = (realEnd > value.length() ? value.length() : realEnd);
        return value.substring(realStart, realEnd);
    }

    public static String epur(String value, String[] targets) {
        for (String target : targets) {
            value = value.replaceAll(target, "");
        }
        return value;
    }

    public static String duplicateString(String value, int number) {
        String result = "";

        for (int i = 0; i < number; ++i) {
            result += value;
        }
        return result;
    }

    public static String addCharacterEach(String value, String character, int number) {
        StringBuilder result = new StringBuilder(value);
        int pos = result.length() - number;

        while (pos > 0) {
            result.insert(pos, character);
            pos -= number;
        }
        return result.toString();
    }

    public static String formatIt(String s1, int length1, String s2, int length2, String s3) {
        return (s1 + duplicateString(" ", length1 - s1.length()) + s2 + duplicateString(" ", length2 - s3.length()) + s3);
    }

}
