package com.xml.editor;

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

    static String[] xmltoJson(String[] inputXml) {
        // Combine the array of XML lines into a single string
        String xmlString = String.join("", inputXml);

        // Convert XML to JSONObject using the org.json library
        JSONObject json = XML.toJSONObject(xmlString);

        // Extract and normalize the JSON structure
        Object normalizedJson = normalizeJson(json);

        // Convert the normalized JSON structure to a formatted string
        String jsonString;
        if (normalizedJson instanceof JSONObject) {
            jsonString = ((JSONObject) normalizedJson).toString(2);
        } else if (normalizedJson instanceof JSONArray) {
            jsonString = ((JSONArray) normalizedJson).toString(2);
        } else {
            // Handle unexpected structure
            jsonString = normalizedJson.toString();
        }

        // Split the JSON string into an array of lines and return
        return jsonString.split("\n");
    }

    private static Object normalizeJson(Object json) {
        if (json instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) json;
            JSONObject result = new JSONObject();

            for (String key : jsonObject.keySet()) {
                Object value = jsonObject.get(key);

                // If the value is a JSONObject or JSONArray, recurse into it
                if (value instanceof JSONObject || value instanceof JSONArray) {
                    result.put(key, normalizeJson(value));
                } else {
                    result.put(key, value);
                }
            }

            return result;
        } else if (json instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) json;
            JSONArray result = new JSONArray();

            // Normalize each element in the array
            for (int i = 0; i < jsonArray.length(); i++) {
                Object element = jsonArray.get(i);
                if (element instanceof JSONObject || element instanceof JSONArray) {
                    result.put(normalizeJson(element));
                } else {
                    result.put(element);
                }
            }

            return result; // Return the array directly without wrapping in an additional structure
        }

        // Return primitive values as-is
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
