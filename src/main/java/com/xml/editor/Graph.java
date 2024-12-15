package com.xml.editor;

import java.util.*;

public class Graph {
    private Map<Integer, List<Integer>> adjacencyList;

    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    // Add a vertex (user) to the graph
    public void addVertex(int id) {
        adjacencyList.putIfAbsent(id, new ArrayList<>());
    }

    // Add a directed edge (follower relationship) to the graph
    public void addEdge(int from, int to) {
        adjacencyList.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
    }

    // Get the adjacency list for a specific user
    public List<Integer> getAdjacencyList(int id) {
        return adjacencyList.getOrDefault(id, Collections.emptyList());
    }

    // Get all vertices in the graph
    public Set<Integer> getVertices() {
        return adjacencyList.keySet();
    }

    // Print the graph
    public String printGraph() {
        StringBuilder temp=new StringBuilder();
        temp.append("Graph Representation (Adjacency List):");
        for (Map.Entry<Integer, List<Integer>> entry : adjacencyList.entrySet()) {
            temp.append("User ").append(entry.getKey()).append(" -> ").append(entry.getValue());
        }
        return String.valueOf(temp);
    }

    // Check if two nodes are directly connected
    public boolean areConnected(int from, int to) {
        return adjacencyList.getOrDefault(from, Collections.emptyList()).contains(to) || adjacencyList.getOrDefault(to, Collections.emptyList()).contains(from);
    }

    public boolean isFollowing(int from, int to) {
        // Check if the 'from' vertex has an adjacency list, and verify if 'to' exists in it
        return adjacencyList.get(to).contains(from);
    }

}

