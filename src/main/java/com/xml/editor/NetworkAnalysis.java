package com.xml.editor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    public static ArrayList<User> mostInfluencer(String s){
        ReadFile(s);
        ArrayList<User> mostInf = new ArrayList<>();
        int index = 0;
        int mostfollowed = users[0].followers.size();
        for(int i = 0; i < users.length; i++){
            if(mostfollowed < users[i].followers.size()){
                mostfollowed = users[i].followers.size();
                index = i;
            }
        }
        for(int i = 0; i < users.length; i++){
            if(users[i].followers.size() == users[index].followers.size()){
                mostInf.add(users[i]);
            }
        }

        return mostInf;
    }

    public static ArrayList<User> mostActive(String s){
        ReadFile(s);
        ArrayList<User> mostActive = new ArrayList<>();
        int index = 0;
        int maxFollowing = 0; //no. of people a user is following

        for(int i = 0; i< graph.getVertices().size();i++ ){
            int followCount = 0;
            for(int j=0; j< graph.getVertices().size(); j++){
                if(graph.areConnected(users[i].getId(),users[j].getId())){
                    followCount++;
                }
            }
            users[i].setFollowing(followCount);
            if(maxFollowing<followCount){
                maxFollowing = followCount;
                index = i;
            }
        }
        for(int i = 0; i < users.length; i++){
            if(users[i].getFollowing() == users[index].getFollowing()){
                mostActive.add(users[i]);
            }
        }
        return mostActive;
    }

    public static ArrayList<User> mutualFollowers(String s, int[] ids) {
        ReadFile(s);
        ArrayList<User> mutualF = new ArrayList<>();

        // Iterate through all users
        for (User user : users) {
            boolean follows = true;

            // Check if the current user follows all the users in 'ids'
            for (int id : ids) {
                if (!graph.isFollowing(user.getId(), id)) {
                    follows = false;
                    break;
                }
            }

            // If the user follows all, add them to the mutual followers list
            if (follows) {
                mutualF.add(user);
            }
        }

        return mutualF;
    }


    public static String printAllUsres(ArrayList<User> u){
        String s = "";
        for(int i=0; i< u.size(); i++){
            s += u.get(i).toString();
        }
        return s;
    }

}
