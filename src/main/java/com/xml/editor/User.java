package com.xml.editor;

import java.util.*;

/**
 * The {@code User} class represents a social media user with a unique ID, name,
 * posts, followers, and the number of users they are following.
 */
class User {
    int id; // Unique identifier for the user
    String name; // Name of the user
    List<String> posts; // List of posts created by the user
    Set<Integer> followers; // Set of IDs of the user's followers
    int numberOfFollowing = 0; // Number of users this user is following

    /**
     * Constructs a {@code User} object with the specified ID and name.
     *
     * @param id   the unique ID of the user.
     * @param name the name of the user.
     */
    User(int id, String name) {
        this.id = id;
        this.name = name;
        this.posts = new ArrayList<>();
        this.followers = new HashSet<>();
    }

    /**
     * Returns a string representation of the user, including their ID, name,
     * and followers.
     *
     * @return a string representation of the user.
     */
    @Override
    public String toString() {
        return String.format("User ID: %d, Name: %s\n" +
                        "Followers: %s\n",
                id, name, followers);
    }

    /**
     * Gets the unique ID of the user.
     *
     * @return the user's unique ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the number of users this user is following.
     *
     * @param n the number of users this user is following.
     */
    public void setFollowing(int n) {
        numberOfFollowing = n;
    }

    /**
     * Gets the number of users this user is following.
     *
     * @return the number of users this user is following.
     */
    public int getFollowing() {
        return numberOfFollowing;
    }
}
