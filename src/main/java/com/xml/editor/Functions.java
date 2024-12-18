package com.xml.editor;

import javafx.scene.image.ImageView;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Functions {
    static  String[] check(String[] s){
        return XMLHandler.check(List.of(s)).toArray(new String[0]);
    }

    static  String[] repair(String[] s){
        return XMLHandler.fix(List.of(s)).toArray(new String[0]);
    }

    static  String[] format(String[] s){
        return XMLHandler.format(List.of(s)).toArray(new String[0]);
    }

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

    static  String[] minify(String[] s){
        String temp=DataCompress.minify(String.join("",s));
        String[] s_minified=new String[1];
        s_minified[0]=temp;
        return s_minified;
    }

    static  String[] compress(String[] s){
        String data = String.join(String.valueOf((char) (256)),s);
        data = DataCompress.bytePairEncode(data);
        String pairs = DataCompress.hashMapToString(DataCompress.pairs);

        String[] file_compressed=new String[2];
        file_compressed[0]=pairs;
        file_compressed[1]=data;

        return file_compressed;
    }

    static  String[] decompress(String[] s){
        Map<String,String> pairs = DataCompress.stringToHashMap(s[0]);
        String data = s[1];

        String originalData = DataCompress.bytePairDecode(data,pairs);
        s=originalData.split(String.valueOf((char) (256)));
        return s;
    }
    static SocialNetworkGraph draw(String[] s){
        String data = String.join(" ", s);
        SocialNetworkGraph graph = new SocialNetworkGraph();
        graph.buildGraphFromXML(data);
        return graph;
    }
    static String[] networkAnalysis(String[] s){
        StringBuilder user=new StringBuilder();
        user.append("mos influencer:");
        user.append(NetworkAnalysis.mostInfluencer(String.join("\n",s)).toString());
        user.append("\n");
        user.append("most active:");
        user.append(NetworkAnalysis.mostActive(String.join("\n",s)).toString());
        return user.toString().split("\n");
    }
    static String[] suggest(String[] s,int id){
        Suggest Suggest = new Suggest();
        Suggest.buildGraphFromXML(String.join("\n",s));
        Set<User> suggestedFriends = Suggest.suggestFriends(id);
        String [] out= new String[suggestedFriends.size()] ;
        int i=0;
            for (User user : suggestedFriends) {
                out[i] = user.id + ": " +user.name ;
                i=i+1;
}
        return out;
    }
    static String[] mutualFollowers(String[] s,int[] id){
        return  NetworkAnalysis.printAllUsres(NetworkAnalysis.mutualFollowers(String.join("\n",s),id)).split("\n");
    }
    static String[] wordSearch(String[] s,String word){
        return search.wordSearch(s,word).toArray(new String[0]);
    }
    static String[] topicSearch(String[] s,String topic){
        return search.topicSearch(s,topic).toArray(new String[0]);
    }


}
