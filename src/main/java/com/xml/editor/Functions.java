package com.xml.editor;

import javafx.scene.image.ImageView;
import org.json.JSONObject;
import org.json.XML;

import java.util.Map;

public interface Functions {
    static  String[] check(String[] s){
        return s;
    }

    static  String[] repair(String[] s){
        return s;
    }

    static  String[] format(String[] s){
        return s;
    }

    static  String[] xmltoJson(String[] inputxml){
        // Join the array into a single XML string
        String xmlString = String.join("", inputxml);

        // Convert XML to JSONObject
        JSONObject json = XML.toJSONObject(xmlString);

        // Convert JSONObject to a formatted string (with 5 spaces for indentation)
        String jsonString = json.toString(5);

        // Split the JSON string by newlines and return it as an array of strings
        return jsonString.split("\n");
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
        return  s;
    }
    static String[] suggest(String[] s,int id){
        return  s;
    }
    static String[] wordSearch(String[] s,String word){
        return  s;
    }
    static String[] topicSearch(String[] s,String topic){
        return  s;
    }


}
