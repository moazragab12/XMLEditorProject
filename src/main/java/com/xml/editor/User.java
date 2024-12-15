package com.xml.editor;

import java.util.*;

class User {
    int id;
    String name;
    List<String> posts;
    Set<Integer> followers;
    int numberOfFollowing = 0;

    User(int id, String name) {
        this.id = id;
        this.name = name;
        this.posts = new ArrayList<>();
        this.followers = new HashSet<>();
    }

    @Override
    public String toString() {
        return String.format("User ID: %d, Name: %s\n" +
                        "Followers: %s\n",
                id, name, followers);
    }

    public int getId(){
        return id;
    }

    public void setFollowing(int n){
        numberOfFollowing = n;
    }
    public int getFollowing(){
        return numberOfFollowing;
    }
}