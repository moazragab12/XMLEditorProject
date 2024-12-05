package com.xml.editor;

import java.util.*;

class User {
    int id;
    String name;
    List<String> posts;
    Set<Integer> followers;

    User(int id, String name) {
        this.id = id;
        this.name = name;
        this.posts = new ArrayList<>();
        this.followers = new HashSet<>();
    }

    @Override
    public String toString() {
        return String.format("User ID: %d, Name: %s\n" +
                        "Posts: %s\n" +
                        "Followers: %s\n",
                id, name, posts, followers);
    }

    public String printUsers() {
        return String.format("User ID: %d, Name: %s\n",
                id, name);
    }

    public int getId(){
        return id;
    }
}