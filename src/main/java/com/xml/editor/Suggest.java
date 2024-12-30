package com.xml.editor;

import java.util.HashSet;
import java.util.Set;

/**
 * The {@code Suggest} class provides functionality to suggest friends for users
 * in a social network based on their followers and their followers' connections.
 * It uses an underlying {@link SocialNetworkGraph} to represent the social network.
 */
public class Suggest {

    private SocialNetworkGraph socialNetworkGraph; // The graph representing the social network

    /**
     * Constructs a new {@code Suggest} object and initializes the social network graph.
     */
    public Suggest() {
        socialNetworkGraph = new SocialNetworkGraph();
    }

    /**
     * Builds the social network graph from the provided XML string.
     *
     * @param xml the XML string representing the social network.
     */
    public void buildGraphFromXML(String xml) {
        socialNetworkGraph.buildGraphFromXML(xml);
    }

    /**
     * Suggests friends for a given user based on the followers of their followers.
     * Suggested friends exclude the user themselves and their direct followers.
     *
     * @param userId the ID of the user for whom friends are to be suggested.
     * @return a set of {@code User} objects representing the suggested friends.
     * If the user is not found, an empty set is returned.
     */
    public Set<User> suggestFriends(int userId) {
        User user = socialNetworkGraph.getUserById(userId);

        if (user == null) {
            System.out.println("User not found in the graph.");
            return new HashSet<>();
        }

        Set<Integer> friendsOfFriends = new HashSet<>(); // To store suggested friends' IDs
        Set<Integer> userFollowers = new HashSet<>(user.followers); // The user's followers
        Set<Integer> alreadyFriends = new HashSet<>(userFollowers); // Direct friends of the user (followers)

        // For each follower of the user, check their followers (friends of friends)
        for (Integer followerId : userFollowers) {
            User follower = socialNetworkGraph.getUserById(followerId);

            if (follower != null) {
                // Add the followers of this follower (excluding the user and their direct friends)
                for (Integer followerOfFollowerId : follower.followers) {
                    // Skip if it's the user themselves or if it's already a direct friend
                    if (!followerOfFollowerId.equals(userId) && !alreadyFriends.contains(followerOfFollowerId)) {
                        friendsOfFriends.add(followerOfFollowerId);
                    }
                }
            }
        }

        // Convert suggested friend IDs into User objects and return
        Set<User> suggestedUsers = new HashSet<>();
        for (Integer friendId : friendsOfFriends) {
            User suggestedUser = socialNetworkGraph.getUserById(friendId);
            if (suggestedUser != null) {
                suggestedUsers.add(suggestedUser);
            }
        }

        return suggestedUsers;
    }
}
