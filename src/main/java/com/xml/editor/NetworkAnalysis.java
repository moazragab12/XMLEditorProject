package com.xml.editor;

import java.util.ArrayList;

public class NetworkAnalysis {
    static User [] users;

    public User[] getUsers(){
        return users;
    }

    //get info from xml file and save users in User array
    public static void ReadFile(String s){
            // implement this to save users in User array
    }

    public static User mostInfluencer(String s){
        ReadFile(s);
        int index = 0;
        int mostfollowed = users[0].followers.size();
        for(int i = 0; i < users.length; i++){
            if(mostfollowed < users[i].followers.size()){
                mostfollowed = users[i].followers.size();
                index = i;
            }
        }

        return users[index];
    }

    public static User mostActive(String s){
        ReadFile(s);
        int index = 0;
        return users[index];
    }

    public static ArrayList<User> mutualFollowers(String s,int[] ids){
        ReadFile(s);
        ArrayList<User> mutualF = new ArrayList<>();
        return mutualF;
    }

    public static ArrayList<User> suggestedFollowers(String s,int id){
        ReadFile(s);
        ArrayList<User> suggest = new ArrayList<>();
        return suggest;
    }
    public static String printAllUsres(ArrayList<User> u){
        // implement this
        return "";
    }
}
