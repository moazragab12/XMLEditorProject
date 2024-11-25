package com.xml.editor;

import java.util.*;

public class DataCompress {
    static Map<String, String> pairs = new LinkedHashMap<>();
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


        DataCompress.pairs=pairs;
        return result.toString();
    }

    public static String bytePairDecode(String encoded, Map<String, String> pairs) {
        String result = encoded;
        List<String> keys = new ArrayList<>(pairs.keySet());

        // Decode the encoded string
        for (int i = keys.size()-1;i>=0;i--) {
            result=result.replace(pairs.get(keys.get(i)), keys.get(i));
        }

        return result;
    }

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

    public static Map<String, String> stringToHashMap(String pairs) {
        Map<String, String>pairsHashed= new LinkedHashMap<>();
        for (int i = 0 ; i < pairs.length() ; i=i+5){
            if (pairs.charAt(i)=='{' || pairs.charAt(i)==',')
                pairsHashed.put((pairs.substring(i+1, i + 3)), pairs.substring(i + 4, i + 5));
        }

        return pairsHashed;
    }


        public static  String minify(String s){
        String s_minified = s;
        s_minified=s_minified.replace(" ","");

        return s_minified;
    }
}


