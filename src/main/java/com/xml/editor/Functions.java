package com.xml.editor;

import javafx.scene.image.ImageView;

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
    static  String[] xmltoJson(String[] s){
        return s;
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
