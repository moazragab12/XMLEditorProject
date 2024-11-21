package com.xml.editor;

public interface Functions {
    static  boolean check(String[] s){
        return false;
    }

    static  String[] repair(String[] s){
        return s;
    }
    static  String[] format(String[] s){
        return s;
    }
    static  String[] xmlToJson(String[] s){
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
    static  String[] decompress(String[] s){
        return s;
    }
}
