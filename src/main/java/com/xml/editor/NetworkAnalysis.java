package com.xml.editor;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class NetworkAnalysis {
    static User [] users;
    static Graph graph;

    public User[] getUsers(){
        return users;
    }

    //get info from xml file and save users in User array
    public static void ReadFile(String s){
        SocialNetworkGraph networkGraph = new SocialNetworkGraph();
        networkGraph.buildGraphFromXML(s);
        Map<Integer, User> users1 = networkGraph.users;
        users = users1.values().toArray(new User[0]);  //copy users in graph to Users array
        graph = networkGraph.graph;
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
        int maxFollowing = 0; //no. of people a user is following

        for(int i = 0; i< graph.getVertices().size();i++ ){
            int followCount = 0;
            for(int j=0; j< graph.getVertices().size(); j++){
                if(graph.areConnected(users[i].getId(),users[j].getId())){
                    followCount++;
                }
            }
            if(maxFollowing<followCount){
                maxFollowing = followCount;
                index = i;
            }
        }
        return users[index];
    }

    public static ArrayList<User> mutualFollowers(String s,int[] ids){
        ReadFile(s);
        int user1 = ids[0];
        int user2 = ids[1];
        ArrayList<User> mutualF = new ArrayList<>();
        for(int i=0; i< users.length; i++){
            if(graph.areConnected(users[i].getId(),user1) && graph.areConnected(users[i].getId(),user2)){
                mutualF.add(users[i]);
            }
        }
        return mutualF;
    }

    public static String printAllUsres(ArrayList<User> u){
        String s = "";
        for(int i=0; i< u.size(); i++){
            s += u.get(i).printUsers();
        }
        return s;
    }

}
