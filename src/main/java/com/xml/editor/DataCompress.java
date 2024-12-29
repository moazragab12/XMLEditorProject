package com.xml.editor;

import java.util.*;

/**
 * The `DataCompress` class provides methods for performing compression and decompression using the
 * Byte Pair Encoding (BPE) algorithm, as well as utilities for minification and map serialization.
 * The class also provides methods to handle encoding and decoding of byte pairs, as well as
 * converting maps to and from string representations.
 */
public class DataCompress {
    static Map<String, String> pairs = new LinkedHashMap<>();

    /**
     * Performs Byte Pair Encoding (BPE) on the input string.
     * <p>
     * This method repeatedly finds the most common pair of adjacent characters and replaces
     * them with a new symbol, creating a compressed version of the input string.
     * The process continues until no common pair is found that appears more than once.
     *
     * @param input the input string to be compressed
     * @return the compressed string after applying Byte Pair Encoding
     */
    public static String bytePairEncode(String input) {
        Map<String, String> pairs = new LinkedHashMap<>();
        StringBuilder result = new StringBuilder(input);

        while (true) {
            Map<String, Integer> pairCount = new HashMap<>();

            // Count frequency of each pair
            for (int i = 0; i < result.length() - 1; i++) {
                String pair = result.substring(i, i + 2);
                pairCount.put(pair, pairCount.getOrDefault(pair, 0) + 1);
            }

            // Find the most common pair
            String mostCommonPair = null;
            int maxCount = 0;
            for (Map.Entry<String, Integer> entry : pairCount.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostCommonPair = entry.getKey();
                }
            }

            // If no common pair is found or maxCount is less than 2, break
            if (mostCommonPair == null || maxCount < 2) {
                break;
            }

            // Create a new symbol for the most common pair
            String newSymbol = String.valueOf((char) (257 + pairs.size()));
            pairs.put(mostCommonPair, newSymbol);

            // Replace all occurrences of the most common pair with the new symbol
            String encoded = result.toString().replace(mostCommonPair, newSymbol);
            result = new StringBuilder(encoded);
        }

        DataCompress.pairs = pairs;
        return result.toString();
    }

    /**
     * Decodes a string that was encoded using Byte Pair Encoding (BPE).
     * <p>
     * This method reverses the Byte Pair Encoding process, replacing the new symbols with the
     * original character pairs using the provided mapping of pairs.
     *
     * @param encoded the encoded string to be decoded
     * @param pairs the mapping of new symbols to the original pairs used during encoding
     * @return the decoded string
     */
    public static String bytePairDecode(String encoded, Map<String, String> pairs) {
        String result = encoded;
        List<String> keys = new ArrayList<>(pairs.keySet());

        // Decode the encoded string
        for (int i = keys.size() - 1; i >= 0; i--) {
            result = result.replace(pairs.get(keys.get(i)), keys.get(i));
        }

        return result;
    }

    /**
     * Converts a map of key-value pairs to a string representation.
     * <p>
     * The map is serialized into a string format where each entry is represented as
     * `key:value`, and entries are separated by commas, with the entire list enclosed
     * in curly braces.
     *
     * @param map the map to be converted to a string
     * @return a string representation of the map
     */
    public static String hashMapToString(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("{"); // Start with an opening brace

        // Iterate over the entries of the HashMap
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
        }

        // Remove the last comma if the map is not empty
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 1); // Remove the last ","
        }

        sb.append("}"); // End with a closing brace
        return sb.toString();
    }

    /**
     * Converts a string representation of pairs into a map of key-value pairs.
     * <p>
     * This method parses a string of the form `{key1:value1,key2:value2,...}` and returns
     * a corresponding map of key-value pairs.
     *
     * @param pairs the string representation of pairs
     * @return a map representing the pairs
     */
    public static Map<String, String> stringToHashMap(String pairs) {
        Map<String, String> pairsHashed = new LinkedHashMap<>();
        for (int i = 0; i < pairs.length(); i = i + 5) {
            if (pairs.charAt(i) == '{' || pairs.charAt(i) == ',')
                pairsHashed.put((pairs.substring(i + 1, i + 3)), pairs.substring(i + 4, i + 5));
        }

        return pairsHashed;
    }

    /**
     * Minifies a string by removing all whitespace characters.
     * <p>
     * This method is a simple compression technique that removes spaces from the input string.
     *
     * @param s the string to be minified
     * @return the minified string with all spaces removed
     */
    public static String minify(String s) {
        String s_minified = s;
        s_minified = s_minified.replace(" ", "");

        return s_minified;
    }
}
