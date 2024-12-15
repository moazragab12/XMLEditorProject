package com.xml.editor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class search {
    public static List<String> wordSearch(String[] s, String word){
        //String[][] topicsArray = new String[s.length][s.length];
        List<String> topics =new ArrayList<String>();
        int counter=0,start=0,end=0;
        boolean found = false,ass=false;
        for(int i=0 ; i < s.length;i++ ){
            String[] line = s[i].split(" +");

            for(String w : line){
                if(w.equals("<body>")) {
                    start = i ;
                    break;
                }
                else if(w.equals("</body>")){
                    end=i;
                    ass=true;
                }
                else if(w.equals(word)){
                    found= true;
                }
            }
            if(found & ass){
                for(int j = start+1 ; j<end ; j++){
                    topics.add(s[j]);
                }
                found = false;ass=false;
            }
        }
        return topics;
    }
    public static List<String> topicSearch(String[] s,String topic){
        List<String> topics =new ArrayList<String>();
        StringBuilder xml =new StringBuilder();
        for(String line : s){
            xml.append(line).append(" ");
        }
        String xmlString = xml.toString();
        String[] posts=xmlString.split("<body>");
        for(String post : posts ){
            if(post.contains(topic)){
                String[] postprocessing = post.split(" +");
                StringBuilder postB =new StringBuilder();
                for(String word : postprocessing){
                    if(word.equals("</body>")) break;
                    postB.append(word).append(" ");

                }
                topics.add(String.valueOf(postB));
            }
        }

        return topics;
    }

    public static boolean test(String s, String word){
        return s.contains(word);
    }
}

