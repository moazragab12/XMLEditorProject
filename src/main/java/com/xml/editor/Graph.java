package com.xml.editor;

import java.util.*;

/**
 * The `Graph` class represents a directed graph where vertices (users) are connected by edges (follower relationships).
 * This class allows adding vertices and edges, checking if two vertices are connected, and getting adjacency lists for vertices.
 */
public class Graph {
    private Map<Integer, List<Integer>> adjacencyList;

    /**
     * Constructs an empty graph with no vertices or edges.
     */
    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    /**
     * Adds a vertex (user) to the graph.
     * If the vertex already exists, it will not be added again.
     *
     * @param id The unique identifier for the user (vertex).
     */
    public void addVertex(int id) {
        adjacencyList.putIfAbsent(id, new ArrayList<>());
    }

    /**
     * Adds a directed edge (follower relationship) from one vertex to another.
     *
     * @param from The user who is following.
     * @param to   The user being followed.
     */
    public void addEdge(int from, int to) {
        adjacencyList.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
    }

    /**
     * Gets the adjacency list for a specific user (vertex).
     * This list contains the users that the given user follows.
     *
     * @param id The unique identifier for the user (vertex).
     * @return A list of user IDs that the given user follows, or an empty list if the user has no followers.
     */
    public List<Integer> getAdjacencyList(int id) {
        return adjacencyList.getOrDefault(id, Collections.emptyList());
    }

    /**
     * Gets all vertices (users) in the graph.
     *
     * @return A set of user IDs representing all vertices in the graph.
     */
    public Set<Integer> getVertices() {
        return adjacencyList.keySet();
    }

    /**
     * Prints the graph in an adjacency list format.
     *
     * @return A string representing the graph's adjacency list.
     */
    public String printGraph() {
        StringBuilder temp = new StringBuilder();
        temp.append("Graph Representation (Adjacency List):");
        for (Map.Entry<Integer, List<Integer>> entry : adjacencyList.entrySet()) {
            temp.append("User ").append(entry.getKey()).append(" -> ").append(entry.getValue());
        }
        return String.valueOf(temp);
    }

    /**
     * Checks if two vertices are directly connected (i.e., if there is a directed edge between them).
     * This method checks if either vertex is following the other.
     *
     * @param from The user who may be following the other user.
     * @param to   The user who may be followed by the other user.
     * @return {@code true} if there is a directed edge between the two vertices, {@code false} otherwise.
     */
    public boolean areConnected(int from, int to) {
        return adjacencyList.getOrDefault(from, Collections.emptyList()).contains(to) || adjacencyList.getOrDefault(to, Collections.emptyList()).contains(from);
    }

    /**
     * Checks if the first user is following the second user.
     *
     * @param from The user who may be following another user.
     * @param to   The user who may be followed by the first user.
     * @return {@code true} if the first user is following the second user, {@code false} otherwise.
     */
    public boolean isFollowing(int from, int to) {
        // Check if the 'from' vertex has an adjacency list, and verify if 'to' exists in it
        return adjacencyList.get(to).contains(from);
    }

}
