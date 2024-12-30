package com.xml.editor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The `NetworkAnalysis` class provides methods to analyze and retrieve information about a social network.
 * It reads data from an XML file and processes users and their relationships, such as identifying the most influential users,
 * most active users, and finding mutual followers between given user IDs.
 */
public class NetworkAnalysis {
    static User[] users;      // Array to store users
    static Graph graph;       // Graph representing relationships between users

    /**
     * Gets the list of users.
     *
     * @return An array of all users.
     */
    public User[] getUsers() {
        return users;
    }

    /**
     * Reads the XML file to build a social network graph and saves the users in the `users` array.
     *
     * @param s The path to the XML file containing the social network data.
     */
    public static void ReadFile(String s) {
        SocialNetworkGraph networkGraph = new SocialNetworkGraph();
        networkGraph.buildGraphFromXML(s);
        Map<Integer, User> users1 = networkGraph.users;
        users = users1.values().toArray(new User[0]);  // Copy users in graph to the users array
        graph = networkGraph.graph;
    }

    /**
     * Finds the most influential users in the network based on the number of followers.
     *
     * @param s The path to the XML file containing the social network data.
     * @return A list of users with the highest number of followers.
     */
    public static ArrayList<User> mostInfluencer(String s) {
        ReadFile(s);
        ArrayList<User> mostInf = new ArrayList<>();
        int index = 0;
        int mostfollowed = users[0].followers.size();

        // Find the user with the most followers
        for (int i = 0; i < users.length; i++) {
            if (mostfollowed < users[i].followers.size()) {
                mostfollowed = users[i].followers.size();
                index = i;
            }
        }

        // Add all users with the same number of followers to the list
        for (int i = 0; i < users.length; i++) {
            if (users[i].followers.size() == users[index].followers.size()) {
                mostInf.add(users[i]);
            }
        }

        return mostInf;
    }

    /**
     * Finds the most active users in the network based on the number of people they follow.
     *
     * @param s The path to the XML file containing the social network data.
     * @return A list of users who follow the most number of people.
     */
    public static ArrayList<User> mostActive(String s) {
        ReadFile(s);
        ArrayList<User> mostActive = new ArrayList<>();
        int index = 0;
        int maxFollowing = 0; // Number of people a user is following

        // Calculate the number of people each user follows
        for (int i = 0; i < graph.getVertices().size(); i++) {
            int followCount = 0;
            for (int j = 0; j < graph.getVertices().size(); j++) {
                if (graph.areConnected(users[i].getId(), users[j].getId())) {
                    followCount++;
                }
            }
            users[i].setFollowing(followCount);

            // Track the user with the most followers
            if (maxFollowing < followCount) {
                maxFollowing = followCount;
                index = i;
            }
        }

        // Add all users with the same number of followers to the list
        for (int i = 0; i < users.length; i++) {
            if (users[i].getFollowing() == users[index].getFollowing()) {
                mostActive.add(users[i]);
            }
        }

        return mostActive;
    }

    /**
     * Finds mutual followers among users specified by the given IDs.
     *
     * @param s The path to the XML file containing the social network data.
     * @param ids The array of user IDs to check for mutual followers.
     * @return A list of users who follow all the users in the provided ID list.
     */
    public static ArrayList<User> mutualFollowers(String s, int[] ids) {
        ReadFile(s);
        ArrayList<User> mutualF = new ArrayList<>();

        // Iterate through all users to check if they follow all the users in 'ids'
        for (User user : users) {
            boolean follows = true;

            for (int id : ids) {
                if (!graph.isFollowing(user.getId(), id)) {
                    follows = false;
                    break;
                }
            }

            // If the user follows all the specified users, add them to the mutual followers list
            if (follows) {
                mutualF.add(user);
            }
        }

        return mutualF;
    }

    /**
     * Prints the details of all users in the given list.
     *
     * @param u The list of users to print.
     * @return A string containing the details of all users in the list.
     */
    public static String printAllUsres(ArrayList<User> u) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < u.size(); i++) {
            s.append(u.get(i).toString());
        }
        return s.toString();
    }
}
