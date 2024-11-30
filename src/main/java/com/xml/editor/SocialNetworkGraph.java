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
        xml = xml.replaceAll("\\s*\n\\s*", ""); // Clean up input XML

        Pattern userPattern = Pattern.compile("<user>(.*?)</user>");
        Pattern idPattern = Pattern.compile("<id>(\\d+)</id>"); // Match numeric IDs
        Pattern namePattern = Pattern.compile("<name>(.*?)</name>");
        Pattern postPattern = Pattern.compile("<body>(.*?)</body>");
        Pattern followerIdPattern = Pattern.compile("<follower>\\s*<id>(\\d+)</id>\\s*</follower>"); // Match <id> inside <follower>

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

            Matcher followerMatcher = followerIdPattern.matcher(userBlock);
            while (followerMatcher.find()) {
                int followerId = Integer.parseInt(followerMatcher.group(1));
                user.followers.add(followerId);
                graph.addEdge(followerId, userId); // Add to graph as a directed edge
            }

            users.put(userId, user);
            graph.addVertex(userId); // Add the user as a vertex
        }
    }

    public String printUsers() {
        if (users.isEmpty()) {
           return ("No users found in the graph.");

        }
StringBuilder temp= new StringBuilder();

        for (User user : users.values()) {
            temp.append(user);
            temp.append("--------------------");
        }
        return ("Social Network Users:"+ temp);
    }

    public String printGraph() {
       return graph.printGraph();
    }

    public User getUserById(int id) {
        return users.get(id);
    }

    public int getTotalUsers() {
        return users.size();
    }
}
