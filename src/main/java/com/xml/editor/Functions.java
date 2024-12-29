package com.xml.editor;

import javafx.scene.image.ImageView;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The `Functions` interface provides static methods for manipulating and analyzing XML data,
 * converting XML to JSON, compressing and decompressing data, drawing social network graphs,
 * performing network analysis, and conducting search operations on the data.
 */
public interface Functions {

    /**
     * Checks the validity of XML data provided as an array of strings.
     *
     * @param s The array of strings representing XML data.
     * @return A string array containing the results of the check operation.
     */
    static String[] check(String[] s) {
        return XMLHandler.check(List.of(s)).toArray(new String[0]);
    }

    /**
     * Repairs the XML data provided as an array of strings.
     *
     * @param s The array of strings representing XML data.
     * @return A string array containing the repaired XML data.
     */
    static String[] repair(String[] s) {
        return XMLHandler.fix(List.of(s)).toArray(new String[0]);
    }

    /**
     * Formats the XML data provided as an array of strings for better readability.
     *
     * @param s The array of strings representing XML data.
     * @return A string array containing the formatted XML data.
     */
    static String[] format(String[] s) {
        return XMLHandler.format(List.of(s)).toArray(new String[0]);
    }

    /**
     * Converts an array of XML strings to a formatted JSON string array.
     *
     * @param inputxml The array of XML strings.
     * @return A string array containing the JSON representation of the XML data.
     */
    static String[] xmltoJson(String[] inputxml) {
        // Join the array into a single XML string
        String xmlString = String.join("", inputxml);

        // Convert XML to JSONObject using the org.json.XML utility
        JSONObject json = XML.toJSONObject(xmlString);

        // Correct the JSON structure
        json = correctJsonStructure(json);

        // Convert JSONObject to a formatted string (with 5 spaces for indentation)
        String jsonString = json.toString(5);

        // Split the JSON string by newlines and return it as an array of strings
        return jsonString.split("\n");
    }

    /**
     * Corrects the structure of the converted JSON object to ensure proper formatting of users and their attributes.
     *
     * @param json The original JSON object representing the converted XML data.
     * @return The corrected JSON object.
     */
    private static JSONObject correctJsonStructure(JSONObject json) {
        // Check if 'socialNetwork' and 'users' elements exist
        if (json.has("socialNetwork")) {
            JSONObject socialNetwork = json.getJSONObject("socialNetwork");

            // Ensure 'users' is an array (if it's not, convert it to one)
            if (socialNetwork.has("users")) {
                Object users = socialNetwork.get("users");

                if (users instanceof JSONObject) {
                    // Convert single user object to an array
                    JSONArray usersArray = new JSONArray();
                    usersArray.put(socialNetwork.getJSONObject("users"));
                    socialNetwork.put("users", usersArray);
                }

                // Process each user and ensure 'posts' and 'followers' are arrays
                JSONArray usersArray = socialNetwork.getJSONArray("users");
                for (int i = 0; i < usersArray.length(); i++) {
                    JSONObject user = usersArray.getJSONObject(i);

                    // Ensure 'posts' is an array
                    if (user.has("posts") && user.getJSONObject("posts").has("post")) {
                        JSONArray postsArray = new JSONArray();
                        if (user.getJSONObject("posts").has("post")) {
                            postsArray.put(user.getJSONObject("posts").getJSONObject("post"));
                        }
                        user.put("posts", postsArray);
                    }

                    // Ensure 'followers' is an array
                    if (user.has("followers") && user.getJSONObject("followers").has("follower")) {
                        JSONArray followersArray = new JSONArray();
                        if (user.getJSONObject("followers").has("follower")) {
                            followersArray.put(user.getJSONObject("followers").getInt("follower"));
                        }
                        user.put("followers", followersArray);
                    }
                }
            }
        }
        return json;
    }

    /**
     * Minifies the provided XML data by reducing unnecessary whitespace.
     *
     * @param s The array of strings representing XML data.
     * @return A string array containing the minified XML data.
     */
    static String[] minify(String[] s) {
        String temp = DataCompress.minify(String.join("", s));
        String[] s_minified = new String[1];
        s_minified[0] = temp;
        return s_minified;
    }

    /**
     * Compresses the provided XML data using byte pair encoding.
     *
     * @param s The array of strings representing XML data.
     * @return A string array containing the compressed XML data.
     */
    static String[] compress(String[] s) {
        String data = String.join(String.valueOf((char) (256)), s);
        data = DataCompress.bytePairEncode(data);
        String pairs = DataCompress.hashMapToString(DataCompress.pairs);

        String[] file_compressed = new String[2];
        file_compressed[0] = pairs;
        file_compressed[1] = data;

        return file_compressed;
    }

    /**
     * Decompresses the provided compressed XML data.
     *
     * @param s The array containing the compressed data (pairs and encoded data).
     * @return A string array containing the decompressed XML data.
     */
    static String[] decompress(String[] s) {
        Map<String, String> pairs = DataCompress.stringToHashMap(s[0]);
        String data = s[1];

        String originalData = DataCompress.bytePairDecode(data, pairs);
        s = originalData.split(String.valueOf((char) (256)));
        return s;
    }

    /**
     * Draws a social network graph from the provided XML data.
     *
     * @param s The array of strings representing the XML data.
     * @return A `SocialNetworkGraph` object representing the social network graph.
     */
    static SocialNetworkGraph draw(String[] s) {
        String data = String.join(" ", s);
        SocialNetworkGraph graph = new SocialNetworkGraph();
        graph.buildGraphFromXML(data);
        return graph;
    }

    /**
     * Analyzes the social network data and returns insights about the most influential and most active users.
     *
     * @param s The array of strings representing the social network data.
     * @return A string array containing the analysis results.
     */
    static String[] networkAnalysis(String[] s) {
        StringBuilder user = new StringBuilder();
        user.append("mos influencer:");
        user.append(NetworkAnalysis.mostInfluencer(String.join("\n", s)).toString());
        user.append("\n");
        user.append("most active:");
        user.append(NetworkAnalysis.mostActive(String.join("\n", s)).toString());
        return user.toString().split("\n");
    }

    /**
     * Suggests friends for a given user based on the social network data.
     *
     * @param s  The array of strings representing the social network data.
     * @param id The user ID for whom the friends should be suggested.
     * @return A string array containing suggested friends.
     */
    static String[] suggest(String[] s, int id) {
        Suggest Suggest = new Suggest();
        Suggest.buildGraphFromXML(String.join("\n", s));
        Set<User> suggestedFriends = Suggest.suggestFriends(id);
        String[] out = new String[suggestedFriends.size()];
        int i = 0;
        for (User user : suggestedFriends) {
            out[i] = user.id + ": " + user.name;
            i = i + 1;
        }
        return out;
    }

    /**
     * Finds mutual followers between two users based on the social network data.
     *
     * @param s  The array of strings representing the social network data.
     * @param id The array of user IDs for which mutual followers are to be found.
     * @return A string array containing the mutual followers.
     */
    static String[] mutualFollowers(String[] s, int[] id) {
        return NetworkAnalysis.printAllUsres(NetworkAnalysis.mutualFollowers(String.join("\n", s), id)).split("\n");
    }

    /**
     * Searches for a word in the social network data.
     *
     * @param s    The array of strings representing the social network data.
     * @param word The word to search for.
     * @return A string array containing the search results.
     */
    static String[] wordSearch(String[] s, String word) {
        return search.wordSearch(s, word).toArray(new String[0]);
    }

    /**
     * Searches for a topic in the social network data.
     *
     * @param s     The array of strings representing the social network data.
     * @param topic The topic to search for.
     * @return A string array containing the topic search results.
     */
    static String[] topicSearch(String[] s, String topic) {
        return search.topicSearch(s, topic).toArray(new String[0]);
    }
}
