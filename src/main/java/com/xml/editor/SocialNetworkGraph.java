package com.xml.editor;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class SocialNetworkGraph {
    Map<Integer, User> users;
    Graph graph;

    SocialNetworkGraph() {
        this.users = new HashMap<>();
        this.graph = new Graph();
    }

    public void buildGraphFromXML(String xml) {
        xml = xml.replaceAll("\\s*\n\\s*", "");

        Pattern userPattern = Pattern.compile("<user>(.*?)</user>");
        Pattern idPattern = Pattern.compile("<id>(.*?)</id>");
        Pattern namePattern = Pattern.compile("<name>(.*?)</name>");
        Pattern postPattern = Pattern.compile("<body>(.*?)</body>");
        Pattern followerPattern = Pattern.compile("<follower>(.*?)</follower>");

        Matcher userMatcher = userPattern.matcher(xml);

        while (userMatcher.find()) {
            String userBlock = userMatcher.group(1);

            Matcher idMatcher = idPattern.matcher(userBlock);
            int userId = idMatcher.find() ? Integer.parseInt(idMatcher.group(1)) : -1;

            Matcher nameMatcher = namePattern.matcher(userBlock);
            String userName = nameMatcher.find() ? nameMatcher.group(1) : "Unknown";

            User user = new User(userId, userName);

            Matcher postMatcher = postPattern.matcher(userBlock);
            while (postMatcher.find()) {
                user.posts.add(postMatcher.group(1));
            }

            Matcher followerMatcher = followerPattern.matcher(userBlock);
            while (followerMatcher.find()) {
                int followerId = Integer.parseInt(followerMatcher.group(1));
                user.followers.add(followerId);
                graph.addEdge(followerId, userId); // Add to graph as a directed edge
            }

            users.put(userId, user);
            graph.addVertex(userId); // Add the user as a vertex
        }
    }

    public void printUsers() {
        if (users.isEmpty()) {
            System.out.println("No users found in the graph.");
            return;
        }

        System.out.println("Social Network Users:");
        for (User user : users.values()) {
            System.out.println(user);
            System.out.println("--------------------");
        }
    }

    public void printGraph() {
        graph.printGraph();
    }

    public User getUserById(int id) {
        return users.get(id);
    }

    public int getTotalUsers() {
        return users.size();
    }
}