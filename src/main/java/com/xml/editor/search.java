package com.xml.editor;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for searching specific words and topics within XML-like data represented as an array of strings.
 * It provides methods to search for a given word or topic, extracting relevant sections from the text.
 */
public class search {

    /**
     * Searches for the specified word in the given array of strings and returns the lines between
     * <body> and </body> tags that contain the specified word.
     *
     * @param s    The array of strings representing XML-like data.
     * @param word The word to search for in the text.
     * @return A list of strings representing the lines between <body> and </body> tags containing the word.
     */
    public static List<String> wordSearch(String[] s, String word){
        List<String> topics = new ArrayList<>();
        int start = 0, end = 0;
        boolean found = false, ass = false;

        // Iterate through each line in the array
        for(int i = 0; i < s.length; i++) {
            String[] line = s[i].split(" +");

            // Check if <body> or </body> tags are encountered
            for(String w : line){
                if(w.equals("<body>")) {
                    start = i;
                    break;
                } else if(w.equals("</body>")) {
                    end = i;
                    ass = true;
                } else if(w.equals(word)) {
                    found = true;
                }
            }

            // If word is found and <body> to </body> tags are identified, add lines to the result
            if(found && ass){
                for(int j = start + 1; j < end; j++){
                    topics.add(s[j]);
                }
                found = false;
                ass = false;
            }
        }
        return topics;
    }

    /**
     * Searches for the specified topic in the given array of strings and returns sections of text
     * that contain the topic within the <body> tags.
     *
     * @param s     The array of strings representing XML-like data.
     * @param topic The topic to search for in the text.
     * @return A list of strings representing the sections containing the specified topic within <body> tags.
     */
    public static List<String> topicSearch(String[] s, String topic){
        List<String> topics = new ArrayList<>();
        StringBuilder xml = new StringBuilder();

        // Concatenate all the lines into a single string
        for(String line : s){
            xml.append(line).append(" ");
        }

        // Split the text by <body> tags
        String[] posts = xml.toString().split("<body>");

        // Check each post for the specified topic
        for(String post : posts){
            if(post.contains(topic)){
                String[] postprocessing = post.split(" +");
                StringBuilder postB = new StringBuilder();
                for(String word : postprocessing){
                    if(word.equals("</body>")) break;
                    postB.append(word).append(" ");
                }
                topics.add(String.valueOf(postB));
            }
        }
        return topics;
    }

    /**
     * Tests if the specified word is present in the given string.
     *
     * @param s    The string to search within.
     * @param word The word to search for.
     * @return true if the word is found in the string, otherwise false.
     */
    public static boolean test(String s, String word){
        return s.contains(word);
    }
}
