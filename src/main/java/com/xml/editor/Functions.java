package com.xml.editor;

import javafx.scene.image.ImageView;
import org.json.JSONObject;
import org.json.XML;

import java.util.Arrays;

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
        String temp=String.join("",s);
        temp=temp.replace(" ","");
        String[] s_minified=new String[1];
        s_minified[0]=temp;
        return s_minified;
    }
    static  String[] comp(String[] s){
        return s;
    }
    static  String[] decomp(String[] s){
        return s;
    }
    static ImageView draw(String[] s){
        return new ImageView();
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
